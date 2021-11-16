package cn.quellanan.sqlsession;

import cn.quellanan.cache.CacheKey;
import cn.quellanan.cache.PerpetualCache;
import cn.quellanan.config.BoundSql;
import cn.quellanan.pojo.Configuration;
import cn.quellanan.pojo.Mapper;
import cn.quellanan.util.GenericTokenParser;
import cn.quellanan.util.ParameterMapping;
import cn.quellanan.util.ParameterMappingTokenHandler;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SimpleExecuter implements Executer{

    private Connection connection;
    protected PerpetualCache localCache;

    public SimpleExecuter(){
        this.localCache = new PerpetualCache("LocalCache");
    }

    public <E> List<E> query(Configuration configuration, Mapper mapper, Object... parameter) throws Exception {
        BoundSql boundSql=getBoundSql(mapper.getSql());

        CacheKey key = createCacheKey(mapper,boundSql,parameter);
        // 先从缓存中获取
        List<E> list=(List<E>) localCache.getObject(key);
        if (list != null) {
            return list;
        } else {
            // 获得不到，则从数据库中查询
            list = queryFromDatabase(configuration,mapper, key, boundSql,parameter);
        }
        return list;
    }

    // 从数据库中读取操作
    private <E> List<E> queryFromDatabase(Configuration configuration,Mapper mapper,CacheKey key, BoundSql boundSql,Object... parameter) throws Exception {
        List<E> list;
        try {
            // 执行读操作
            list = doQuery(configuration,mapper, boundSql,parameter);
        } finally {
            localCache.removeObject(key);
        }
        // 添加到缓存中
        localCache.putObject(key, list);
        return list;
    }

    public <E> List<E> doQuery(Configuration configuration, Mapper mapper,BoundSql boundSql, Object... parameter) throws Exception{
        PreparedStatement preparedStatement=preHandle(configuration,mapper,boundSql,parameter);
        //执行sql
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultHandle(mapper,resultSet);
    }

    @Override
    public int update(Configuration configuration, Mapper mapper, Object... parameter) throws Exception {
        // 清空本地缓存
        clearLocalCache();

        BoundSql boundSql=getBoundSql(mapper.getSql());
        // 执行写操作
        PreparedStatement preparedStatement=preHandle(configuration,mapper,boundSql,parameter);
        return preparedStatement.executeUpdate();
    }

    public void clearLocalCache() {
        // 清理 localCache
        localCache.clear();
    }


    /**
     * 创建cacheKey
     * @param mapper
     * @param boundSql
     * @param parameter
     * @return
     * @throws Exception
     */
    public CacheKey createCacheKey(Mapper mapper, BoundSql boundSql, Object... parameter) throws Exception{
        // 创建 CacheKey 对象
        CacheKey cacheKey = new CacheKey();
        // 设置 id
        cacheKey.update(mapper.getId());
        // 设置 ParameterMapping 数组的元素对应的每个 value 到 CacheKey 对象中
        List<ParameterMapping> parameterMappingList = boundSql.getParameterMappingList();
        Class<?> parmType = mapper.getParmType();
        for (int i = 0; i < parameterMappingList.size(); i++) {
            Object value=getValue(parmType,parameterMappingList.get(i),parameter);
            cacheKey.update(value);
        }
        return cacheKey;
    }


    /**
     * 准备工作
     * 连接数数据库
     * 解析sql
     * 替换占位符
     * @param configuration
     * @param mapper
     * @param parameter
     * @return
     * @throws Exception
     */
    private PreparedStatement preHandle(Configuration configuration, Mapper mapper,BoundSql boundSql, Object... parameter)throws Exception{
        String sql=boundSql.getSqlText();
        List<ParameterMapping> parameterMappingList = boundSql.getParameterMappingList();

        //获取连接
        connection=configuration.getDataSource().getConnection();
        //获取preparedStatement，并传递参数值
        PreparedStatement preparedStatement=connection.prepareStatement(sql);
        Class<?> parmType = mapper.getParmType();
        for (int i = 0; i < parameterMappingList.size(); i++) {
            Object value=getValue(parmType,parameterMappingList.get(i),parameter);
            preparedStatement.setObject(i+1,value);
        }
        System.out.println(sql);
        return preparedStatement;
    }


    /**
     * 获取参数对应的值
     * @param parmType
     * @param parameterMapping
     * @param parameter
     * @return
     * @throws Exception
     */
    private Object getValue(Class<?> parmType,ParameterMapping parameterMapping, Object... parameter) throws Exception {
        Object value=null;
        String content = parameterMapping.getContent();
        if (isJavaType(parmType)) {
            value=parameter[0];
        }else {
            Field declaredField = parmType.getDeclaredField(content);
            declaredField.setAccessible(true);
            Object o = parameter[0];
            value = declaredField.get(o);
        }
        return value;
    }



    private boolean isJavaType(Class<?> parmType){
        String s = parmType.getSimpleName().toUpperCase();
        return s.equals("INTEGER")|| s.equals("STRING")|| s.equals("LONG");
    }


    /**
     * 封装结果集
     * @param mapper
     * @param resultSet
     * @param <E>
     * @return
     * @throws Exception
     */
    private <E> List<E> resultHandle(Mapper mapper,ResultSet resultSet) throws Exception{
        ArrayList<E> list=new ArrayList<>();
        //封装结果集
        Class<?> resultType = mapper.getResultType();
        while (resultSet.next()) {
            ResultSetMetaData metaData = resultSet.getMetaData();
            Object o = resultType.newInstance();
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                //属性名
                String columnName = metaData.getColumnName(i);
                //属性值
                Object value = resultSet.getObject(columnName);
                //创建属性描述器,为属性生成读写方法
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName,resultType);
                Method writeMethod = propertyDescriptor.getWriteMethod();
                writeMethod.invoke(o,value);
            }
            list.add((E) o);
        }
        return list;
    }

    /**
     * 解析自定义占位符
     * @param sql
     * @return
     */
    private BoundSql getBoundSql(String sql){
        ParameterMappingTokenHandler parameterMappingTokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{","}",parameterMappingTokenHandler);
        String parse = genericTokenParser.parse(sql);
        return new BoundSql(parse,parameterMappingTokenHandler.getParameterMappings());

    }


    public void close() throws Exception {
        connection.close();
    }

    @Override
    public void commit() throws Exception{
        connection.commit();
    }
}
