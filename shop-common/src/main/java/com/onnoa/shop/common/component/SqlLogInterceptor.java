package com.onnoa.shop.common.component;

import java.lang.reflect.Method;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.onnoa.shop.common.constant.GlobalConstant;
import com.onnoa.shop.common.message.IMessageSendTemplate;
import com.onnoa.shop.common.utils.MapConvertUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 慢sql日志切面，对慢sql发送mq进行记录
 */
@Intercepts({
        @Signature(type = org.apache.ibatis.executor.Executor.class, method = "update", args = {
                MappedStatement.class, Object.class
        }), @Signature(type = Executor.class, method = "query", args = {
        MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class
})
})
@Order(1)
@Slf4j
public class SqlLogInterceptor implements Interceptor {

    @Value("${shop.enableSqlLogInterceptor}")
    private boolean enableSqlLogInterceptor;

    /**
     * 关注时间 单位秒，默认值 5,若有配置slowSqlTime参数，则按配置指定的时间记录慢sql 如果 执行SQL 超过时间 就会打印error 日志
     */
    @Value("${shop.slowSqlTime:5}")
    private long overTime;

    @Autowired
    private IMessageSendTemplate messageSendTemplate;

    public SqlLogInterceptor() {
    }

    /**
     * 异步记录慢sql日志
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        long beginTime = System.currentTimeMillis();
        Method method = invocation.getMethod();
        String namespace = method.getDeclaringClass().getName();
        String methodName = method.getName();
        log.info("namespace :{},methodName :{}", namespace, methodName);
        MappedStatement statement = (MappedStatement) invocation.getArgs()[0];
        Object arg = null;
        if (invocation.getArgs().length > 1) {
            arg = invocation.getArgs()[1];
        }
        BoundSql boundSql = statement.getBoundSql(arg);
        Configuration configuration = statement.getConfiguration();
        // 一个空格替换多个空格 表示空格 " \\s"， "[ ]"， "[\\s]" 表示多个空格 "\\s+"， "[ ]+"， "[\\s]+"
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
        Map<String, Object> tempMap = getParamList(configuration, boundSql, sql);
        List<String> paramList = (List<String>) tempMap.get("paramList");
        String wholeSql = MapConvertUtil.getStringValue(tempMap, "sql");
        // 执行sql
        Object proceed = invocation.proceed();
        long endTime = System.currentTimeMillis();
        long executeTime = TimeUnit.MILLISECONDS.toSeconds(endTime - beginTime);
        log.info("record slow sql, executed sql : {}, execute time :{},execute result:{} , sql params list :{}",
                wholeSql, executeTime, proceed, paramList);
        // sql 执行时间超过定义的慢sql时间 ,异步记录慢sql相关日志信息
        if (true /*enableSqlLogInterceptor && executeTime > overTime*/) {
            sendSlowSql(proceed, boundSql, executeTime, paramList, wholeSql);
        }
        return proceed;
    }

    /**
     * 发送慢sql入kafka队列
     *
     * @param result      执行sql后的结果集
     * @param boundSql
     * @param executeTime sql 执行时间
     * @param paramList   参数集合
     */
    @Async
    protected void sendSlowSql(Object result, BoundSql boundSql, long executeTime, List<String> paramList,
                               String wholeSql) {
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
        Map<String, Object> slowSqlMap = Maps.newHashMap();
        slowSqlMap.put("executed sql", wholeSql);
        slowSqlMap.put("params list", paramList);
        slowSqlMap.put("execute time(s)", executeTime);
        slowSqlMap.put("execute result", result);
        // log.info("record slow sql, executed sql : {}, execute time :{},execute result:{} , sql params list :{}", sql,
        // executeTime, result, paramList);
        messageSendTemplate.sendMessage(GlobalConstant.SLOW_SQL_LOG, JSONObject.toJSONString(slowSqlMap));

    }

    /**
     * 获取参数集合
     */
    private Map<String, Object> getParamList(Configuration configuration, BoundSql boundSql, String sql) {
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        List<String> params = new ArrayList<>();
        Map<String, Object> map = Maps.newHashMap();
        if (parameterMappings.size() > 0 && parameterObject != null) {
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                params.add(getParameterValue(parameterObject));
                sql = sql.replaceFirst("\\?", getParameterValue(parameterObject));

            } else {
                MetaObject metaObject = configuration.newMetaObject(parameterObject);
                for (ParameterMapping parameterMapping : parameterMappings) {
                    String propertyName = parameterMapping.getProperty();
                    if (metaObject.hasGetter(propertyName)) {
                        Object obj = metaObject.getValue(propertyName);
                        params.add(getParameterValue(obj));
                        sql = sql.replaceFirst("\\?", getParameterValue(obj));
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        Object obj = boundSql.getAdditionalParameter(propertyName);
                        params.add(getParameterValue(obj));
                        sql = sql.replaceFirst("\\?", getParameterValue(obj));
                    }

                }
            }
        }
        map.put("paramList", params);
        map.put("sql", sql);
        return map;
    }

    /**
     * 获取参数值
     */
    private String getParameterValue(Object obj) {
        String value;
        if (obj instanceof String) {
            value = "'" + obj.toString() + "'";
        } else if (obj instanceof Date) {
            DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
            value = "'" + formatter.format(obj) + "'";
        } else {
            if (obj != null) {
                value = obj.toString();
            } else {
                value = "";
            }

        }
        return value;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

    public void test() {
        /*
         * Method method1 = invocation.getMethod(); Map<String, Object> map = Maps.newHashMap(); Annotation[][]
         * annotations = method1.getParameterAnnotations(); Object[] args = invocation.getArgs(); for (int i = 0; i <
         * annotations.length; i++) { Object arg = args[i]; // 没有注解，说明是实体或map if (annotations[i].length == 0) { if (arg
         * instanceof Map) { map.putAll((Map<? extends String, ?>) arg); } else { map.putAll(BeanUtils.beanToMap(arg));
         * } } // 存在@Param注解 else { Param param = annotations[i].getClass().getAnnotation(Param.class); if (param !=
         * null) { map.put(param.value(), arg); } for (Annotation annotation : annotations[i]) { if (annotation
         * instanceof Param) { log.info("有 param 注解 : {} ,参数值 ：{}", ((Param) annotation).value(), arg); } } } }
         */
    }
}
