package cn.quellanan.pojo;

/**
 * @className: MybatisXmlConfig
 * @description:
 * @author: onnoA
 * @date: 2021/11/1
 **/
public class MybatisXmlConfig {

    private long id;

    private String namespace;

    private String sqlIdName;

    private String resultType;

    private String paramType;

    private String sql;

    private String sqlTagType;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getSqlIdName() {
        return sqlIdName;
    }

    public void setSqlIdName(String sqlIdName) {
        this.sqlIdName = sqlIdName;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getParamType() {
        return paramType;
    }

    public void setParamType(String paramType) {
        this.paramType = paramType;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getSqlTagType() {
        return sqlTagType;
    }

    public void setSqlTagType(String sqlTagType) {
        this.sqlTagType = sqlTagType;
    }

    @Override
    public String toString() {
        return "MybatisXmlConfig{" +
                "id=" + id +
                ", namespace='" + namespace + '\'' +
                ", sqlIdName='" + sqlIdName + '\'' +
                ", resultType='" + resultType + '\'' +
                ", paramType='" + paramType + '\'' +
                ", sql='" + sql + '\'' +
                ", sqlTagType='" + sqlTagType + '\'' +
                '}';
    }
}
