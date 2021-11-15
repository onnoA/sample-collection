package com.onnoa.shop.demo.video.mybatis.demo1;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/**
 * @className: SqlSessionFactoryConfig 创建SQL连接工厂类
 * @description:
 * @author: onnoA
 * @date: 2021/10/9
 **/
@Configuration
public class SqlSessionFactoryConfig {

    @javax.annotation.Resource
    DruidDataSource dataSource;

//    @javax.annotation.Resource
//    DruidDataSource dataSource;

    /**
     * @Autowired SqlSessionFactory sqlSessionFactory;
     * SqlSession session = sqlSessionFactory.openSession();
     * //创建sqlMapper
     * SqlMapper sqlMapper = new SqlMapper(session);
     */

    @Bean
    @Primary
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);//更多参数请自行注入
        bean.setPlugins(new Interceptor[]{new SqlInterceptor()});
        Resource[] resources = new PathMatchingResourcePatternResolver()
                .getResources("classpath*:mapper/*.xml");
        bean.setMapperLocations(resources);
        return bean.getObject();
    }
}
