package com.onnoa.shop.demo.video.xml.demo;

import com.onnoa.shop.demo.video.mapper.TransactionRollbackMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.w3c.dom.Document;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @className: Client
 * @description:
 * @author: onnoA
 * @date: 2021/9/23
 **/
public class Client {

    public static void main(String[] args) throws IOException {


        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        //创建SqlSessionFacory
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        /******************************分割线******************************/
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //获取Mapper
        TransactionRollbackMapper mapper = sqlSession.getMapper(TransactionRollbackMapper.class);
        Map<String,Object> map = new HashMap<>();
        map.put("name","zzh");
        System.out.println(mapper.queryTest(map));
//        sqlSession.close();
//        sqlSession.commit();
    }
}
