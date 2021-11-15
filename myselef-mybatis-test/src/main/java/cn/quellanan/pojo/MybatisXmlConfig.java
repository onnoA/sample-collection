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

    private String sqlidname;

    private String resulttype;

    private String paramtype;

    private String sqlstatement;

    private String sqltagtype;

    private String mapperiname;

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

    public String getSqlidname() {
        return sqlidname;
    }

    public void setSqlidname(String sqlidname) {
        this.sqlidname = sqlidname;
    }

    public String getResulttype() {
        return resulttype;
    }

    public void setResulttype(String resulttype) {
        this.resulttype = resulttype;
    }

    public String getParamtype() {
        return paramtype;
    }

    public void setParamtype(String paramtype) {
        this.paramtype = paramtype;
    }

    public String getSqlstatement() {
        return sqlstatement;
    }

    public void setSqlstatement(String sqlstatement) {
        this.sqlstatement = sqlstatement;
    }

    public String getSqltagtype() {
        return sqltagtype;
    }

    public void setSqltagtype(String sqltagtype) {
        this.sqltagtype = sqltagtype;
    }

    public String getMapperiname() {
        return mapperiname;
    }

    public void setMapperiname(String mapperiname) {
        this.mapperiname = mapperiname;
    }

    @Override
    public String toString() {
        return "MybatisXmlConfig{" +
                "id=" + id +
                ", namespace='" + namespace + '\'' +
                ", sqlidname='" + sqlidname + '\'' +
                ", resulttype='" + resulttype + '\'' +
                ", paramtype='" + paramtype + '\'' +
                ", sqlstatement='" + sqlstatement + '\'' +
                ", sqltagtype='" + sqltagtype + '\'' +
                ", mapperiname='" + mapperiname + '\'' +
                '}';
    }
}
