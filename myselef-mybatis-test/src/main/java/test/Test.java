package test;

import cn.quellanan.dao.MybatisXmlConfigMapper;
import cn.quellanan.dao.UserDao;
import cn.quellanan.enums.MapperMappingEnum;
import cn.quellanan.io.Resources;
import cn.quellanan.pojo.Configuration;
import cn.quellanan.pojo.Mapper;
import cn.quellanan.pojo.MybatisXmlConfig;
import cn.quellanan.pojo.SqlCommandType;
import cn.quellanan.pojo.User;
import cn.quellanan.sqlsession.SqlSession;
import cn.quellanan.sqlsession.SqlSessionFactory;
import cn.quellanan.sqlsession.SqlSessionFactoryBuilder;
import cn.quellanan.util.MapperXmlUtil;
import com.google.common.collect.Lists;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.beans.PropertyVetoException;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;


public class Test {

    @org.junit.Test
    public void userDefXmlTest() throws DocumentException, PropertyVetoException, ClassNotFoundException {
        InputStream inputStream= Resources.getResources("SqlMapperConfig.xml");
        SqlSessionFactoryBuilder factoryBuilder = new SqlSessionFactoryBuilder();
        SqlSession sqlSession = factoryBuilder.build(inputStream).openSqlSession();
//        SqlSession sqlSession = new SqlSessionFactoryBuilder().build(inputStream).openSqlSession();
        MybatisXmlConfigMapper xmlConfigMapper = sqlSession.getMapper(MybatisXmlConfigMapper.class);
        for (MapperMappingEnum value : MapperMappingEnum.values()) {
            MybatisXmlConfig xmlConfig = new MybatisXmlConfig();
            xmlConfig.setNamespace(value.getNamespace());
            xmlConfig.setMapperiname(value.getMapperI());
            List<MybatisXmlConfig> configList = xmlConfigMapper.selectByMappingRel(xmlConfig);
            String mapperXml = MapperXmlUtil.createMapperXml(configList, value.getNamespace());
            System.out.println("xml : " + "\n" + mapperXml);
            // 加载xml文件进 configuration
//            factoryBuilder.getConfiguration().setMapperMap();

        }



    }


    @org.junit.Test
    public void xmlTest() throws DocumentException, ClassNotFoundException {
        List<MybatisXmlConfig> configList = Lists.newArrayList();
        MybatisXmlConfig dto = new MybatisXmlConfig();
        MybatisXmlConfig dto1 = new MybatisXmlConfig();
        dto.setId(1);
        dto.setNamespace("cn.quellanan.dao.UserDao");
        dto.setParamtype("cn.quellanan.pojo.User");
        dto.setSqlstatement(" select * from user where id=#{id}");
        dto.setSqlidname("selectById");
        dto.setSqltagtype("select");
        dto.setResulttype("cn.quellanan.pojo.User");
        configList.add(dto);
        dto1.setId(2);
        dto1.setNamespace("cn.quellanan.dao.UserDao");
//        dto1.setParamType("cn.quellanan.pojo.User");
        dto1.setSqlstatement(" select * from user");
        dto1.setSqlidname("selectAll");
        dto1.setSqltagtype("select");
        dto1.setResulttype("cn.quellanan.pojo.User");
        configList.add(dto1);
        String mapperXml = MapperXmlUtil.createMapperXml(configList, "cn.quellanan.dao.UserDao");
        System.out.println("生成的mapper xml ：" + mapperXml);


        Document doc = DocumentHelper.parseText(mapperXml);



        ByteArrayInputStream inputStream = new ByteArrayInputStream(mapperXml.getBytes());

//        InputStream resourceAsStream = Test.class.getClassLoader().getResourceAsStream(String.valueOf(mapperXml));
        Document document = new SAXReader().read(inputStream);
        Element rootElement = document.getRootElement();
        String namespace = rootElement.attributeValue("namespace");
        //SqlCommandType sqlCommandType = SqlCommandType.valueOf(nodeName.toUpperCase(Locale.ENGLISH));
        handle(namespace, rootElement.selectNodes("//select"));
        handle(namespace, rootElement.selectNodes("//insert"));
        handle(namespace, rootElement.selectNodes("//update"));
        handle(namespace, rootElement.selectNodes("//delete"));
    }

    private void handle(String namespace, List<Node> list) throws ClassNotFoundException {
        Configuration configuration = new Configuration();
        for (int i = 0; i < list.size(); i++) {
            Mapper mapper = new Mapper();
            Element element = (Element) list.get(i);
            SqlCommandType sqlCommandType = SqlCommandType.valueOf(element.getName().toUpperCase());
            String id = element.attributeValue("id");
            mapper.setId(id);
            String paramType = element.attributeValue("paramType");
            if (paramType != null && !paramType.isEmpty()) {
                mapper.setParmType(Class.forName(paramType));
            }
            String resultType = element.attributeValue("resultType");
            if (resultType != null && !resultType.isEmpty()) {
                mapper.setResultType(Class.forName(resultType));
            }
            mapper.setSql(element.getTextTrim());
            mapper.setSqlCommandType(sqlCommandType);
            String key = namespace + "." + id;
            configuration.getMapperMap().put(key, mapper);
        }

        System.out.println(configuration.toString());
    }



    @org.junit.Test
    public void test() throws Exception{
        InputStream inputStream= Resources.getResources("SqlMapperConfig.xml");
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory build = builder.build(inputStream);
        SqlSession sqlSession1 = build.openSqlSession();
        SqlSession sqlSession = new SqlSessionFactoryBuilder().build(inputStream).openSqlSession();

        UserDao mapper = sqlSession.getMapper(UserDao.class);

        User user=new User();
        user.setId(2);
        User user2 = mapper.selectById(user);
        System.out.println(user2);

        List<MybatisXmlConfig> configList = Lists.newArrayList();
        MybatisXmlConfig dto = new MybatisXmlConfig();
        dto.setId(1);
        dto.setNamespace("cn.quellanan.dao.UserDao");
        dto.setParamtype("cn.quellanan.pojo.User");
        dto.setSqlstatement(" select * from user where id=#{id}");
        dto.setSqlidname("selectById");
        dto.setSqltagtype("select");
        dto.setParamtype("cn.quellanan.pojo.User");
        configList.add(dto);
        String mapperXml = MapperXmlUtil.createMapperXml(configList, "cn.quellanan.dao.UserDao");


//        List<User> list = sqlSession.selectList("cn.quellanan.dao.UserDao.selectAll");
//
//        //查询
//        for (User parm : list) {
//            System.out.println(parm.toString());
//        }
//        System.out.println();
//
//        User user=new User();
//        user.setUsername("zh");
//        List<User> list1 = sqlSession.selectList("cn.quellanan.dao.UserDao.selectByName", user);
//        for (User user1 : list1) {
//            System.out.println(user1);
//        }

    }

    /**
     *
     * @throws Exception
     */
    @org.junit.Test
    public void test2() throws Exception{
        InputStream inputStream= Resources.getResources("SqlMapperConfig.xml");
        SqlSession sqlSession = new SqlSessionFactoryBuilder().build(inputStream).openSqlSession();

        UserDao mapper = sqlSession.getMapper(UserDao.class);

        List<User> users = mapper.selectAll();
        System.out.println("selectAll 方法：");
        for (User user1 : users) {
            System.out.println(user1);
        }

        User user=new User();
        user.setId(2);
        user.setUsername("张三");
        System.out.println("selectByName 方法：");
        List<User> users1 = mapper.selectByName(user);
        for (User user1 : users1) {
            System.out.println(user1);
        }
        System.out.println("selectById 方法：");
        System.out.println(mapper.selectById(user));

    }

    @org.junit.Test
    public void test3() throws Exception{
        InputStream inputStream= Resources.getResources("SqlMapperConfig.xml");
        SqlSession sqlSession = new SqlSessionFactoryBuilder().build(inputStream).openSqlSession();
        UserDao mapper = sqlSession.getMapper(UserDao.class);

        User user=new User();
        user.setId(2);
        User user2 = mapper.selectById(user);
        System.out.println(user2);
    }

    @org.junit.Test
    public void test4() throws Exception{
        InputStream inputStream= Resources.getResources("SqlMapperConfig.xml");
        SqlSession sqlSession = new SqlSessionFactoryBuilder().build(inputStream).openSqlSession();
        UserDao mapper = sqlSession.getMapper(UserDao.class);

        //增加操作
        User user=new User();
        user.setUsername("赵六");
        user.setPassword("123");
        int add = mapper.add(user);
        System.out.println(add);
    }

    @org.junit.Test
    public void test5() throws Exception{
        InputStream inputStream= Resources.getResources("SqlMapperConfig.xml");
        SqlSession sqlSession = new SqlSessionFactoryBuilder().build(inputStream).openSqlSession();
        UserDao mapper = sqlSession.getMapper(UserDao.class);

        //修改
        User user=new User();
        user.setId(11);
        user.setUsername("程序员爱酸奶");
        user.setPassword("123456");
        user.setBirthday("1993-12-12");
        int b = mapper.update(user);
        System.out.println(b==1);
    }

    @org.junit.Test
    public void test6() throws Exception{
        InputStream inputStream= Resources.getResources("SqlMapperConfig.xml");
        SqlSession sqlSession = new SqlSessionFactoryBuilder().build(inputStream).openSqlSession();
        UserDao mapper = sqlSession.getMapper(UserDao.class);

        //删除
        int b = mapper.delete(11);
        System.out.println(b==1);
    }


    @org.junit.Test
    public void Test11() throws Exception{
        InputStream inputStream= Resources.getResources("SqlMapperConfig.xml");
        SqlSession sqlSession = new SqlSessionFactoryBuilder().build(inputStream).openSqlSession();
        UserDao mapper = sqlSession.getMapper(UserDao.class);
        System.out.println("第一次：");
        List<User> users = mapper.selectAll();
        System.out.println(users.get(0));
        System.out.println("修改操作");
        User user=new User();
        user.setUsername("张三");
        user.setId(1);
        mapper.update(user);
        System.out.println("第二次：");
        List<User> users2 = mapper.selectAll();
        System.out.println(users2.get(0));

        System.out.println("两对象是否一致");
        System.out.println(users.equals(users2));
    }




    @org.junit.Test
    public void Test12() throws Exception{

        InputStream inputStream= Resources.getResources("SqlMapperConfig.xml");

        SqlSessionFactory build = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = build.openSqlSession();
        SqlSession sqlSession1 = build.openSqlSession();

        UserDao mapper = sqlSession.getMapper(UserDao.class);
        UserDao mapper1 = sqlSession1.getMapper(UserDao.class);

        System.out.println("第一次：");
        List<User> users = mapper.selectAll();
        System.out.println(users.get(0));

        System.out.println("第二次：");
        List<User> users2 = mapper1.selectAll();
        System.out.println(users2.get(0));

        System.out.println("两对象是否一致");
        System.out.println(users.equals(users2));
    }




}
