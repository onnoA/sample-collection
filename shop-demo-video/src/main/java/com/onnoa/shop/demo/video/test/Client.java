package com.onnoa.shop.demo.video.test;

import com.google.common.collect.Maps;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.datasource.pooled.PooledDataSourceFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.parsing.XPathParser;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @className: Client
 * @description:
 * @author: onnoA
 * @date: 2021/9/23
 **/
public class Client {

    public static void main(String[] args) throws IOException {



        // sqlSessionFactory是一个复杂对象，通常创建一个复杂对象会使用建造器来构建，这里首先创建建造器
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();

        // configuration对象对应mybatis的config文件，为了测试简便，我这里直接创建Configuration对象而不通过xml解析获得
        Configuration configuration = new Configuration();
        configuration.setEnvironment(buildEnvironment());

        // 解析一个mapper.xml为MappedStatement并加入到configuration中
        InputStream inputStream = Resources.getResourceAsStream("mapper/Article.xml");

        XMLMapperBuilder mapperParser = new XMLMapperBuilder(inputStream, configuration, "mapper/Article.xml", configuration.getSqlFragments());
        mapperParser.parse();

        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(configuration);

        // 创建一个sqlSession,这里使用的是简单工厂设计模式
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // 执行最终的sql，查询文章id为1的文章
        Article article = sqlSession.selectOne("com.onnoa.shop.demo.video.mapper.ArticleMapper.selectById", 1L);
        Map<String, Object> map = Maps.newHashMap();
        map.put("id", 1);
        List<Article> result = sqlSession.selectList("com.onnoa.shop.demo.video.mapper.ArticleMapper.selectArticleList", map);

        System.out.println(result);
        // 打印文件的标题
        System.out.println(article.getTitle());
        // sqlSession默认不会自动关闭，我们需要手动关闭
        sqlSession.close();
    }

    private static Environment buildEnvironment() {
        return new Environment.Builder("test")
                .transactionFactory(getTransactionFactory())
                .dataSource(getDataSource()).build();
    }

    private static DataSource getDataSource() {
        String url = "jdbc:mysql://localhost:3306/sample_collections?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true";
        String user = "root";
        String password = "123456";

        Properties properties = new Properties();
        properties.setProperty("url", url);
        properties.setProperty("username", user);
        properties.setProperty("password", password);
        properties.setProperty("driver", "com.mysql.jdbc.Driver");
        properties.setProperty("driver.encoding", "UTF-8");

        PooledDataSourceFactory factory = new PooledDataSourceFactory();
        factory.setProperties(properties);
        DataSource dataSource = factory.getDataSource();
        return dataSource;
    }

    private static TransactionFactory getTransactionFactory() {
        return new JdbcTransactionFactory();
    }
}
