package com.onnoa.shop.demo.authority.system.interceptor;

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

import lombok.extern.slf4j.Slf4j;

//@Intercepts({
//    @Signature(type = org.apache.ibatis.executor.Executor.class, method = "update", args = {
//        MappedStatement.class, Object.class
//    }), @Signature(type = Executor.class, method = "query", args = {
//        MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class
//    })
//})
//@Order(1)
//@Slf4j
//public class SqlLogInterceptor implements Interceptor {
//
//    @Value("${shop.enableSqlLogInterceptor}")
//    private boolean enableSqlLogInterceptor;
//
//    /**
//     * 关注时间 单位秒，默认值 5,若有配置slowSqlTime参数，则按配置指定的时间记录慢sql 如果 执行SQL 超过时间 就会打印error 日志
//     */
//    @Value("${shop.slowSqlTime:5}")
//    private long overTime;
//
//    @Autowired
//    private IMessageSendTemplate messageSendTemplate;
//
//    public SqlLogInterceptor() {
//    }
//
//    /**
//     * 异步记录慢sql日志
//     */
//    @Override
//    public Object intercept(Invocation invocation) throws Throwable {
//        long beginTime = System.currentTimeMillis();
//        MappedStatement statement = (MappedStatement) invocation.getArgs()[0];
//        Object arg = null;
//        if (invocation.getArgs().length > 1) {
//            arg = invocation.getArgs()[1];
//        }
//        BoundSql boundSql = statement.getBoundSql(arg);
//        Configuration configuration = statement.getConfiguration();
//        List<String> paramList = getParamList(configuration, boundSql);
//        // 一个空格替换多个空格 表示空格 " \\s"， "[ ]"， "[\\s]" 表示多个空格 "\\s+"， "[ ]+"， "[\\s]+"
//        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
//        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
//        log.info("to execute sql : {} ,sql in parameter : {}", sql, paramList);
//        // 执行sql
//        Object proceed = invocation.proceed();
//        log.info("execute result :{}", proceed);
//        long endTime = System.currentTimeMillis();
//        long executeTime = TimeUnit.MILLISECONDS.toSeconds(endTime - beginTime);
//        // sql 执行时间超过定义的慢sql时间 ,异步记录慢sql相关日志信息
//        if (/* enableSqlLogInterceptor && executeTime > overTime */ true) {
//            sendSlowSql(proceed, boundSql, executeTime, paramList);
//        }
//        return proceed;
//    }
//
//    @Async
//    protected void sendSlowSql(Object result, BoundSql boundSql, long executeTime, List<String> paramList) {
//        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
//        Map<String, Object> slowSqlMap = Maps.newHashMap();
//        slowSqlMap.put("executed sql", sql);
//        slowSqlMap.put("params", paramList);
//        slowSqlMap.put("executeTime(s)", executeTime);
//        slowSqlMap.put("executeResult", result);
//        log.info("record slow sql, executed sql : {}, execute time :{},execute result:{}", sql, executeTime, result);
//        messageSendTemplate.sendMessage(GlobalConstant.SLOW_SQL_LOG, JSONObject.toJSONString(slowSqlMap));
//
//    }
//
//    private List<String> getParamList(Configuration configuration, BoundSql boundSql) {
//        Object parameterObject = boundSql.getParameterObject();
//        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
//        List<String> params = new ArrayList<>();
//        if (parameterMappings.size() > 0 && parameterObject != null) {
//            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
//            if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
//                params.add(getParameterValue(parameterObject));
//            }
//            else {
//                MetaObject metaObject = configuration.newMetaObject(parameterObject);
//                for (ParameterMapping parameterMapping : parameterMappings) {
//                    String propertyName = parameterMapping.getProperty();
//                    if (metaObject.hasGetter(propertyName)) {
//                        Object obj = metaObject.getValue(propertyName);
//                        params.add(getParameterValue(obj));
//                    }
//                    else if (boundSql.hasAdditionalParameter(propertyName)) {
//                        Object obj = boundSql.getAdditionalParameter(propertyName);
//                        params.add(getParameterValue(obj));
//                    }
//                }
//            }
//        }
//        return params;
//    }
//
//    private String getParameterValue(Object obj) {
//        String value;
//        if (obj instanceof String) {
//            value = "'" + obj.toString() + "'";
//        }
//        else if (obj instanceof Date) {
//            DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
//            value = "'" + formatter.format(obj) + "'";
//        }
//        else {
//            if (obj != null) {
//                value = obj.toString();
//            }
//            else {
//                value = "";
//            }
//
//        }
//        return value;
//    }
//
//    @Override
//    public Object plugin(Object target) {
//        return Plugin.wrap(target, this);
//    }
//
//    @Override
//    public void setProperties(Properties properties) {
//
//    }
//}
