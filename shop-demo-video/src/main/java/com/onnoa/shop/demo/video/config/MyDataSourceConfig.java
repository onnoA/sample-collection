package com.onnoa.shop.demo.video.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.onnoa.shop.demo.video.utils.SpringContextUtil;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * @className: DataSourceConfig
 * @description:
 * @author: onnoA
 * @date: 2021/9/18
 **/
@Configuration
@MapperScan(basePackages = {"com.onnoa.shop.demo.video.mapper"}, sqlSessionFactoryRef = "sqlSessionFactoryDataSource")
public class MyDataSourceConfig {

    @Autowired
    private SpringContextUtil springContextUtil;

    @Autowired
    private DataSourceProperties properties;

    @Autowired
    private DataSource dataSource;

//    @Bean(name = "mysqlProperties")
//    @ConfigurationProperties("spring.datasource")
//    public DataSourceProperties mysqlProperties() {
//        return new DataSourceProperties();
//    }

    /**
     * 添加@Primary注解，设置默认数据源，事务管理器
     * @return
     */
//    @Primary
//    @Bean(name = "mysqlDataSource")
//    public DataSource mysqlDataSource() {
//        if (StringUtils.isEmpty(properties.getDriverClassName())) {
//            properties = (DataSourceProperties) springContextUtil.getBean2("pgProperties");
//            if (StringUtils.isEmpty(properties.getDriverClassName())) {
//                properties = (DataSourceProperties) springContextUtil.getBean2("oracleProperties");
//            }
//        }
//        DataSource dataSource = properties.initializeDataSourceBuilder()
//                .type(DruidDataSource.class).build();
//        return dataSource;
//    }

//    @Primary
    @Bean(name = "sqlSessionFactoryDataSource")
    public SqlSessionFactory sqlSessionFactoryDataSource(
            DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        factoryBean.setConfiguration(configuration);

        // 使用mysqlDataSource数据源, 连接mysqlDataSource库
        factoryBean.setDataSource(dataSource);

        //下边两句仅仅用于*.xml文件，如果整个持久层操作不需要使用到xml文件的话（只用注解就可以搞定），则不加
        //指定entity和mapper。xml的路径
        factoryBean.setTypeAliasesPackage("com.onnoa.shop.demo.video.domain");
//        com.onnoa.shop.demo.video.mapper
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*Mapper.xml"));
        return factoryBean.getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
//        DataSource prodDataSource = (DataSource) springContextUtil.getBean2("mysqlDataSource");
        return new DataSourceTransactionManager(dataSource);
    }

//    @Primary
    @Bean(name = "jdbcTemplate")
    JdbcTemplate jdbcTemplate(DataSource dsOne) {
        return new JdbcTemplate(dsOne);
    }
}
