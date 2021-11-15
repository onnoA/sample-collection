//package cn.quellanan.test;
//
//import cn.quellanan.io.Resources;
//import cn.quellanan.sqlsession.SqlSession;
//
//import java.io.InputStream;
//
///**
// * @className: DemoTessst
// * @description:
// * @author: onnoA
// * @date: 2021/10/28
// **/
//public class DemoTest {
//    public void test() throws Exception{
//        InputStream inputStream= Resources.getResources("SqlMapperConfig.xml");
//        SqlSession sqlSession = new SqlSessionFactoryBuilder().build(inputStream).openSqlSession();
//        List<User> list = sqlSession.selectList("cn.quellanan.dao.UserDao.selectAll");
//
//        for (User parm : list) {
//            System.out.println(parm.toString());
//        }
//        System.out.println();
//
//        User user=new User();
//        user.setUsername("张三");
//        List<User> list1 = sqlSession.selectList("cn.quellanan.dao.UserDao.selectByName", user);
//        for (User user1 : list1) {
//            System.out.println(user1);
//        }
//
//    }
//
//    @org.junit.Test
//    public void test2() throws Exception{
//        InputStream inputStream= Resources.getResources("SqlMapperConfig.xml");
//        SqlSession sqlSession = new SqlSessionFactoryBuilder().build(inputStream).openSqlSession();
//
//        UserDao mapper = sqlSession.getMapper(UserDao.class);
//        List<User> users = mapper.selectAll();
//        for (User user1 : users) {
//            System.out.println(user1);
//        }
//
//        User user=new User();
//        user.setUsername("张三");
//        List<User> users1 = mapper.selectByName(user);
//        for (User user1 : users1) {
//            System.out.println(user1);
//        }
//
//    }
//
//}
