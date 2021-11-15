package cn.quellanan.enums;

/**
 * @className: MapperMappingEnum 自定义mapper xml 与mapper层接口映射关系
 * @description:
 * @author: onnoA
 * @date: 2021/11/2
 **/
public enum MapperMappingEnum {

    USER_DAO("UserDao", "cn.quellanan.dao.UserDao","UserDao接口与xml的映射关系");


    private String mapperI;

    private String namespace;

    private String desc;

    public String getMapperI() {
        return mapperI;
    }

    public void setMapperI(String mapperI) {
        this.mapperI = mapperI;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    MapperMappingEnum(String mapperI, String namespace, String desc) {
        this.mapperI = mapperI;
        this.namespace = namespace;
        this.desc = desc;
    }
}
