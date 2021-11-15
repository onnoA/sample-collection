package cn.quellanan.pojo;

import javax.sql.DataSource;
import java.util.HashMap;

public class Configuration {

    private DataSource dataSource;

    HashMap <String,Mapper> mapperMap=new HashMap<>();

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public HashMap<String, Mapper> getMapperMap() {
        return mapperMap;
    }

    public void setMapperMap(HashMap<String, Mapper> mapperMap) {
        this.mapperMap = mapperMap;
    }
}
