package cn.quellanan.sqlsession;

import cn.quellanan.pojo.Configuration;
import cn.quellanan.pojo.Mapper;
import cn.quellanan.pojo.SqlCommandType;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

public class DefaultSqlSeeion implements SqlSession {
    private Configuration configuration;

    private Executer executer = new SimpleExecuter();

    public DefaultSqlSeeion(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <E> List<E> selectList(String statementid, Object... parm) throws Exception {
        Mapper mapper = configuration.getMapperMap().get(statementid);
        List<E> query = executer.query(configuration, mapper, parm);
        return query;
    }

    @Override
    public <T> T selectOne(String statementid, Object... parm) throws Exception {
        List<Object> list = selectList(statementid, parm);
        if (list.size() == 1) {
            return (T) list.get(0);
        } else {
            throw new RuntimeException("返回结果过多");
        }
    }

    @Override
    public int insert(String statementid, Object... parm) throws Exception {
        return update(statementid, parm);
    }

    @Override
    public int update(String statementid, Object... parm) throws Exception {
        Mapper mapper = configuration.getMapperMap().get(statementid);
        int update = executer.update(configuration, mapper, parm);
        return update;
    }

    @Override
    public int delete(String statementid, Object... parm) throws Exception {
        return update(statementid, parm);
    }

    @Override
    public void commit() throws Exception {
        executer.commit();
    }


    @Override
    public <T> T getMapper(Class<T> mapperClass) {

        Object proxyInstance = Proxy.newProxyInstance(DefaultSqlSeeion.class.getClassLoader(), new Class[]{mapperClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                //获取到方法名
                String name = method.getName();
                //全类名
                String className = method.getDeclaringClass().getName();
                String statementid = className + "." + name;

                Mapper mapper = configuration.getMapperMap().get(statementid);
                SqlCommandType sqlType = mapper.getSqlCommandType();
                Type genericReturnType = method.getGenericReturnType();


                switch (sqlType) {
                    case SELECT:
                        //判断是否实现泛型类型参数化
                        if (genericReturnType instanceof ParameterizedType) {
                            return selectList(statementid, args);
                        } else {
                            return selectOne(statementid, args);
                        }
                    case INSERT:
                        return insert(statementid, args);
                    case DELETE:
                        return delete(statementid, args);
                    case UPDATE:
                        return update(statementid, args);
                    default:
                        break;
                }
                return null;
            }
        });


        return (T) proxyInstance;
    }
}
