package cn.quellanan.sqlsession;

import java.util.List;

public interface SqlSession {

    /**
     * 条件查找
     * @param statementid  唯一标识，namespace.selectid
     * @param parm  传参，可以不传也可以一个，也可以多个
     * @param <E>
     * @return
     */
    public <E> List<E> selectList(String statementid,Object...parm) throws Exception;

    public <T> T selectOne(String statementid, Object...parm) throws Exception;

    public int insert(String statementid, Object...parm) throws Exception;
    public int update(String statementid, Object...parm) throws Exception;
    public int delete(String statementid, Object...parm) throws Exception;
    public void commit() throws Exception;


    /**
     * 使用代理模式来创建接口的代理对象
     * @param mapperClass
     * @param <T>
     * @return
     */
    public <T> T getMapper(Class<T> mapperClass);
}
