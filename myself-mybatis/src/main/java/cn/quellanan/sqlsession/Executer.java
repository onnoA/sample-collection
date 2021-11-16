package cn.quellanan.sqlsession;

import cn.quellanan.pojo.Configuration;
import cn.quellanan.pojo.Mapper;

import java.sql.SQLException;
import java.util.List;

public interface Executer {

    <E> List<E> query(Configuration configuration,Mapper mapper,Object...parm) throws Exception;

    int update(Configuration configuration, Mapper mapper, Object...parm)throws Exception;
    void close() throws Exception;

    void commit() throws Exception;
}
