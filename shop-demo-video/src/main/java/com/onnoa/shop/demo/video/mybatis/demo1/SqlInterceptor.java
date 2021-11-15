package com.onnoa.shop.demo.video.mybatis.demo1;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.ResultHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Statement;
import java.util.Properties;

/**
 * @className: SqlInterceptor
 * @description:
 * @author: onnoA
 * @date: 2021/10/9
 **/
@Intercepts({@Signature(type = StatementHandler.class, method = "query", args = {Statement.class, ResultHandler.class}),
        @Signature(type = StatementHandler.class, method = "update", args = {Statement.class}),
        @Signature(type = StatementHandler.class, method = "batch", args = {Statement.class})})
@Component
@Configuration
public class SqlInterceptor implements Interceptor {

    private Logger logger = LoggerFactory.getLogger(SqlInterceptor.class);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        MetaObject metaObject = MetaObject.forObject(statementHandler, SystemMetaObject.DEFAULT_OBJECT_FACTORY, SystemMetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY, new DefaultReflectorFactory());
        //先拦截到RoutingStatementHandler，里面有个StatementHandler类型的delegate变量，其实现类是BaseStatementHandler，然后就到BaseStatementHandler的成员变量mappedStatement
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        //id为执行的mapper方法的全路径名，如com.uv.dao.UserMapper.insertUser
        String id = mappedStatement.getId();
        logger.info("拦截到当前请求方法的全路径名为--->:  " + id);
        //sql语句类型 select、delete、insert、update
        String sqlCommandType = mappedStatement.getSqlCommandType().toString();
        BoundSql boundSql = statementHandler.getBoundSql();

        //获取到原始sql语句
        String sql = boundSql.getSql();
        String mSql = sql;

        //获取参数
        Object parameter = statementHandler.getParameterHandler().getParameterObject();
        logger.info("拦截到当前请求SQL为--->: " + sql + "<------------>请求类型为:  " + sqlCommandType);
        logger.info("拦截到当前请求参数为--->: " + parameter);

        //TODO 修改位置
        //注解逻辑判断  添加注解了才拦截//InterceptAnnotation
        Class<?> classType = Class.forName(mappedStatement.getId().substring(0, mappedStatement.getId().lastIndexOf(".")));
        String mName = mappedStatement.getId().substring(mappedStatement.getId().lastIndexOf(".") + 1, mappedStatement.getId().length());
//        for (Method method : classType.getDeclaredMethods()) {
//            if (method.isAnnotationPresent(InterceptAnnotation.class) && mName.equals(method.getName())) {
//                InterceptAnnotation interceptorAnnotation = method.getAnnotation(InterceptAnnotation.class);
////                if (interceptorAnnotation.flag()) {
////                    mSql = sql + " limit 2";
////                }
//            }
//        }

        //通过反射修改sql语句
        Field field = boundSql.getClass().getDeclaredField("sql");
        field.setAccessible(true);
        field.set(boundSql, mSql);
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, new SqlInterceptor());
    }

    @Override
    public void setProperties(Properties properties) {
//        this.setProperties(properties);
    }
}
