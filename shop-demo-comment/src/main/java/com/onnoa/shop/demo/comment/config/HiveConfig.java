//package com.onnoa.shop.demo.comment.config;
//
//import com.alibaba.druid.pool.DruidDataSource;
//import lombok.extern.log4j.Log4j2;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//
//import javax.sql.DataSource;
//
//
//@Configuration
//@MapperScan(basePackages = "com.onnoa.shop.demo.comment.mapper.db2", sqlSessionFactoryRef = "db2SqlSessionFactory")
//@Log4j2
//@EnableConfigurationProperties({DataSourceProperties.class})
//public class HiveConfig {
//
//    @Autowired
//    private DataSourceProperties dataSourceProperties;
//
//    @Bean("db2DataSource")
//    public DataSource getDb2DataSource() {
//        DruidDataSource datasource = new DruidDataSource();
//
//        //配置数据源属性
//        datasource.setUrl(dataSourceProperties.getHive().get("jdbcUrl"));
//        datasource.setUsername(dataSourceProperties.getHive().get("userName"));
//        datasource.setPassword(dataSourceProperties.getHive().get("passWord"));
//
//        datasource.setDriverClassName(dataSourceProperties.getHive().get("driverClassName"));
//
//        return datasource;
//    }
//
//    @Bean("db2SqlSessionFactory")
//    public SqlSessionFactory db2SqlSessionFactory(@Qualifier("db2DataSource") DataSource dataSource) throws Exception {
//        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
//        bean.setDataSource(dataSource);
//        // mapper的xml形式文件位置必须要配置，不然将报错：no statement （这种错误也可能是mapper的xml中，namespace与项目的路径不一致导致）
//        // 设置mapper.xml路径，classpath不能有空格
//        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/db2/*.xml"));
//        return bean.getObject();
//    }
//
//    @Bean("db2SqlSessionTemplate")
//    public SqlSessionTemplate db2SqlSessionTemplate(@Qualifier("db2SqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
//        return new SqlSessionTemplate(sqlSessionFactory);
//    }
//
//}
