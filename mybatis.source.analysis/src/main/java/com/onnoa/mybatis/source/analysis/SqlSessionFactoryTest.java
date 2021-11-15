package com.onnoa.mybatis.source.analysis;

import com.onnoa.mybatis.source.analysis.dao.UserMapper;
import com.onnoa.mybatis.source.analysis.entity.User;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.beans.BeanUtils;
import org.xml.sax.InputSource;

import java.io.InputStream;
import java.io.StringReader;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @className: SqlSesstionFactoryTest
 * @description:
 * @author: onnoA
 * @date: 2021/9/28
 **/
public class SqlSessionFactoryTest {

    public static void main(String[] args) throws Exception {
//        Map<String, Object> map1 = new HashMap<>();
//        map1.put("name", "zh");
//        parseSQL("select * from user where id = #{name}", map1);
        String resouce = "mybatis-config.xml";
        InputStream is = Resources.getResourceAsStream(resouce);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
//        System.out.println(sqlSessionFactory.getConfiguration());
//        SqlSession sqlSession = sqlSessionFactory.openSession();
//        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
//        List<User> users = mapper.getUserList("1", "zh");

        UserMapper userMapper = (UserMapper) Proxy.newProxyInstance(UserMapper.class.getClassLoader(), new Class[]{UserMapper.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object... args) throws Throwable {
                Select annotation = method.getAnnotation(Select.class);
                Object obj = null;
                // 方法上存在 @Select 注解
                if (annotation != null) {
                    String[] value = annotation.value();
                    String sql = value[0];
                    // 构建sql的参数与参数值
                    Map<String, Object> argNameMap = buildMethodArgsNameMap(method, args);
                    // sql 解析
                    String parsedSQL = parseSQL(sql, argNameMap);
                    System.out.println("已经解析后的sql : " + parsedSQL);
                    SqlSession sqlSession = sqlSessionFactory.openSession();
                    Class<?> returnType = method.getReturnType();
                    System.out.println(returnType);
                    Type genericReturnType = method.getGenericReturnType();
//                    User user = (User) returnType.newInstance();
                    System.out.println(genericReturnType);
//                    List<User> result = sqlSession.selectList(parsedSQL, List.class);
//                    System.out.println(result);

                    // 通过mybatis 解析sql语句
                    XMLConfigBuilder parser = new XMLConfigBuilder(new StringReader(sql));
                    Configuration parse = parser.parse();
                    SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
                    SqlSessionFactory factory = builder.build(parse);
                    SqlSession session = factory.openSession();
                    Map<String, Object> params = new HashMap<>();
                    params.put("userId", "1");
                    params.put("userName", "zh");
                    List<Object> objects = session.selectList("com.onnoa.mybatis.source.analysis.dao.UserMapper.getUserList");
                    System.out.println(objects);
                }

                return obj;
            }
        });

        List<User> userList = userMapper.getUserList("1", "zh");
//        Map<String, Object> params = new HashMap<>();
//        params.put("userId", "1");
//        params.put("userName", "zh");
//        List<User> userList = userMapper.getUserList(params);


    }

    /***
     * @description: sql 解析
     * @param: sql
     * @param: nameArgs
     * @return: java.lang.String
     * @author: onnoA
     * @date: 2021/10/9 9:56
     */
    public static String parseSQL(String sql, Map<String, Object> nameArgs) {
        StringBuilder sqlBuilder = new StringBuilder();
        for (int i = 0; i < sql.length(); i++) {
            if ('#' == sql.charAt(i)) {
                // 下一位索引
                char nextChar = sql.charAt(i + 1);
                if (nextChar != '{') {
                    throw new RuntimeException(String.format("sql 格式错误,sql格式应为 #{, sql 为 %s", sql));
                }
                StringBuilder argsSB = new StringBuilder();
                i = parseSQLArg(argsSB, sql, i + 1);
                String argName = argsSB.toString();
//                Object argValue = nameArgs.get(argName);
//                Object argValue = null;
                if (nameArgs instanceof Map) {
                    for (Map.Entry<String, Object> entry : nameArgs.entrySet()) {
                        Map<String, Object> params = (Map<String, Object>) entry.getValue();
//                        argValue = params.get(argName);
                        sqlBuilder.append("'").append(params.get(argName).toString()).append("'");
                    }
                } else {
                    Object argValue = nameArgs.get(argName);
                    sqlBuilder.append("'").append(argValue.toString()).append("'");
                }
                continue;
            }
            sqlBuilder.append(sql.charAt(i));
        }
        return sqlBuilder.toString();
    }

    /***
     * @description: 解析剩余的sql语句
     * @param: argsSB
     * @param: sql
     * @param: nextIndex
     * @return: int
     * @author: onnoA
     * @date: 2021/10/9 10:16
     */
    private static int parseSQLArg(StringBuilder argsSB, String sql, int nextIndex) {
        nextIndex++;
        for (; nextIndex < sql.length(); nextIndex++) {
            char nextChar = sql.charAt(nextIndex);
            if ('}' != nextChar) {
                argsSB.append(nextChar);
                continue;
            }
//            if('}' == nextChar){
            return nextIndex;
//            }
//            throw new RuntimeException(String.format("sql 格式错误, 此处 %s 应为 }, sql 为 %s , sql 为 %s", nextIndex, sql));
        }
        return nextIndex;
    }


    /***
     * @description: 构建sql的参数名与参数值
     * @param: method
     * @param: args
     * @return: java.util.Map<java.lang.String, java.lang.Object>
     * @author: onnoA
     * @date: 2021/10/8 17:35
     */
    public static Map<String, Object> buildMethodArgsNameMap(Method method, Object[] args) {
        Map<String, Object> nameArgMap = new HashMap<>();
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            String paramName = parameters[i].getName();
            nameArgMap.put(paramName, args[i]);
        }
        return nameArgMap;
    }
}

