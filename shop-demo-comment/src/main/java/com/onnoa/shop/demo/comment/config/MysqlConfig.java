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
//import org.springframework.context.annotation.Primary;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//
//import javax.sql.DataSource;
//
//
//@Configuration
////用来映射Dao层
//@MapperScan(basePackages = "com.onnoa.shop.demo.comment.mapper.db1", sqlSessionFactoryRef = "db1SqlSessionFactory")
//@Log4j2
//@EnableConfigurationProperties({DataSourceProperties.class})
//public class MysqlConfig {
//
//    @Autowired
//    private DataSourceProperties dataSourceProperties;
//
//
//    // 设置为主数据源
//    @Primary
//    @Bean("db1DataSource")
//    public DataSource getDb1DataSource() {
//        DruidDataSource datasource = new DruidDataSource();
//        //配置数据源属性
//        datasource.setUrl(dataSourceProperties.getMysqlMain().get("jdbcUrl"));
//        datasource.setUsername(dataSourceProperties.getMysqlMain().get("userName"));
//        datasource.setPassword(dataSourceProperties.getMysqlMain().get("passWord"));
//        datasource.setDriverClassName(dataSourceProperties.getMysqlMain().get("driverClassName"));
//
//        return datasource;
//    }
//
//    // 创建工厂bean对象
//    @Primary
//    //唯一标识  在一个项目中的所有bean不能重复
//    @Bean("db1SqlSessionFactory")
//    public SqlSessionFactory db1SqlSessionFactory(@Qualifier("db1DataSource") DataSource dataSource) throws Exception {
//        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
//        bean.setDataSource(dataSource);
//        // mapper的xml形式文件位置必须要配置，不然将报错：no statement （这种错误也可能是mapper的xml中，namespace与项目的路径不一致导致）
//        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/db1/*.xml"));
//        return bean.getObject();
//    }
//
//    // 创建模板bean
//    @Primary
//    @Bean("db1SqlSessionTemplate")
//    public SqlSessionTemplate db1SqlSessionTemplate(@Qualifier("db1SqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
//        return new SqlSessionTemplate(sqlSessionFactory);
//    }
//
//}
