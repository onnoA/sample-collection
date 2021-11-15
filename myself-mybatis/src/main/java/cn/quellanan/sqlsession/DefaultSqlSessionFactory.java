package cn.quellanan.sqlsession;

import cn.quellanan.pojo.Configuration;

public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    public SqlSession openSqlSession() {
        return new DefaultSqlSeeion(configuration);
    }
}
