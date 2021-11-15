package cn.quellanan.pojo;

public class Mapper {

    //id,resultType,parmType,sql

    private String id;
    private Class<?> resultType;
    private Class<?> parmType;
    private String sql;
    private SqlCommandType sqlCommandType;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSql() {
        return sql;
    }

    public Class<?> getResultType() {
        return resultType;
    }

    public void setResultType(Class<?> resultType) {
        this.resultType = resultType;
    }

    public Class<?> getParmType() {
        return parmType;
    }

    public void setParmType(Class<?> parmType) {
        this.parmType = parmType;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public SqlCommandType getSqlCommandType() {
        return sqlCommandType;
    }

    public void setSqlCommandType(SqlCommandType sqlCommandType) {
        this.sqlCommandType = sqlCommandType;
    }
}
