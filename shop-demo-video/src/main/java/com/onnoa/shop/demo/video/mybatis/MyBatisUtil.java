package com.onnoa.shop.demo.video.mybatis;

import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.xml.internal.resolver.helpers.PublicId;
import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.builder.xml.XMLStatementBuilder;
import org.apache.ibatis.datasource.pooled.PooledDataSourceFactory;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.parsing.XPathParser;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.scripting.xmltags.DynamicSqlSource;
import org.apache.ibatis.scripting.xmltags.XMLScriptBuilder;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeException;
import org.apache.ibatis.type.TypeHandler;
import org.jcp.xml.dsig.internal.dom.DOMUtils;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.sql.DataSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.security.DomainLoadStoreParameter;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;


/**
 * @className: MyBatisUtil
 * @description:
 * @author: onnoA
 * @date: 2021/9/27
 **/
public class MyBatisUtil  {

    public static void main(String[] args) {

        JSONObject object = new JSONObject();
        //.put("condition","3");
        HashMap<String, Object> map2 = new HashMap();
        map2.put("condition", "3");
        //map2.put("condition",object);
        Long[] k = new Long[3];
        k[0] = 1l;
        k[1] = 2l;
        k[2] = 3l;
        object .put("array",k);
        map2.put("array",object);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("test",1);
//        map2.put("obj",jsonObject);
//        String officalSql = getOfficalSql(changeDynamicSql2StaticSql(
//                "<select id=\"selectList\" parameterType=\"java.util.List\" resultType=\"java.lang.Integer\">\n" +
//                        "        SELECT COUNT(1) FROM article\n" +
//                        "        <where> id = 6 and id IN\n" +
//                        "        <foreach collection=\"array.array\" index=\"index\" item=\"item\"\n" +
//                        "            open=\"(\" separator=\",\" close=\")\">\n" +
//                        "            #{item}\n" +
//                        "        </foreach>" +
////                        "and test = #{obj.test}"+
//                        "</where>\n" +
//                        "    </select>", map2
//        ));
        String officalSql = getOfficalSql(changeDynamicSql2StaticSql("<select id=\"selectList\" parameterType=\"java.util.List\" resultType=\"java.lang.Integer\">\n" +
                "SELECT COUNT(1) FROM article <where> id = 6 and id IN \n" +
                "<foreach collection=\"array.array\" index=\"index\" item=\"item\"" +
                        " open=\"(\" separator=\",\" close=\")\"> \n " +
//                        "        <foreach collection=\"array.array\" index=\"index\" item=\"item\"\n" +
//                        "            open=\"(\" separator=\",\" close=\")\">\n" +
                "#{item} \n " +
                "</foreach> " +
                "</where> \n" +
                " </select >", map2
        ));

        System.out.println(officalSql);

        // sqlSessionFactory是一个复杂对象，通常创建一个复杂对象会使用建造器来构建，这里首先创建建造器
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();

        // configuration对象对应mybatis的config文件，为了测试简便，我这里直接创建Configuration对象而不通过xml解析获得
        Configuration configuration = new Configuration();
        configuration.setEnvironment(buildEnvironment());

        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(configuration);
        // 创建一个sqlSession,这里使用的是简单工厂设计模式
        SqlSession sqlSession = sqlSessionFactory.openSession();
//        sqlSession.selectMap()

//        JdbcTemplate jdbcTemplate = new JdbcTemplate();
//        Map map = jdbcTemplate.queryForObject(officalSql, Map.class);
//        System.out.println(map);
//        jdbcTemplate.execute(officalSql);

    }

    /***
     * @description: xml类型的 sql 解析返回可在服务端执行的sql语句
     * @param: content
     * @param: param
     * @return: java.lang.String
     * @author: onnoA
     * @date: 2021/10/9 14:16
     */
    public static String changeDynamicSql2StaticSql(String content, Map<String, Object> param) {
        //解析配置 -----但是因为我们手动输入字符串 所以跳过步骤
        Configuration configuration = new Configuration();
        //解析成xml
        Document doc = parseXMLDocument(content);
        //走mybatis 流程 parse成Xnode
        XPathParser xPathParser = new XPathParser(doc, false);
        Node node = doc.getFirstChild();
        XNode xNode = new XNode(xPathParser, node, null);
        XMLScriptBuilder xmlScriptBuilder = new XMLScriptBuilder(configuration, xNode);
        SqlSource sqlSource = xmlScriptBuilder.parseScriptNode();
        MappedStatement.Builder builder = new MappedStatement.Builder(configuration, content, sqlSource, null);
        List<ResultMap> resultMaps = new ArrayList<>();
        List<ResultMapping> resultMappings = new ArrayList<>();
        ResultMap.Builder resultMapBuilder = new ResultMap.Builder(configuration, content, Map.class, resultMappings, true);
        resultMaps.add(resultMapBuilder.build());
        MappedStatement ms = builder.resultMaps(resultMaps).build();
        // MappedStatement类的 sql 拼接方法
        BoundSql boundSql = ms.getBoundSql(param);
        System.out.println("预编译前Sql------" + boundSql.getSql() + "-----");
        System.out.println("***********************************************************");
        //sql中的参数映射，只是映射，没有包含实际的值
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        // 客户端执行sql时传入的参数
        Map<String, Object> objectMap = (Map) boundSql.getParameterObject();
        String resultSql = boundSql.getSql();
        if(!CollectionUtils.isEmpty(parameterMappings)){
            for (ParameterMapping mapping : parameterMappings) {
                if (boundSql.getAdditionalParameter(mapping.getProperty()) == null) {
                    String placeHolder = mapping.getProperty() ;
                    String[] props = placeHolder.split("\\.");
                    Object obj = objectMap.get(props[0]);
                    String res = null;
                    JSONObject json = null;
                    if(obj instanceof String){
                        res = String.valueOf(obj);
                    } else {
                        json = JSONObject.parseObject(JSONObject.toJSONString(obj));
                    }

                    if(props.length > 1){
                        for(int i = 1 ; i < props.length ; i++){
                            String key = props[i];
                            if(props.length == i + 1){
                                res = String.valueOf(json.get(key));
                                break;
                            }
                            json = JSONObject.parseObject(JSONObject.toJSONString(json.get(key)));
                        }
                    } else {
                        res = String.valueOf(obj);
                    }
                    resultSql = resultSql.
                            replaceFirst("[?]", "\"" + res +"\"");
                    continue;
                }
                resultSql = resultSql.replaceFirst("[?]", boundSql.getAdditionalParameter(mapping.getProperty()).toString());
            }
        }

        System.out.println("最终sql为: " + resultSql);
        return resultSql;
    }


    /***
     * @description: 为sql变量赋值，即替换sql中的 ?
     * @param: ps
     * @return: void
     * @author: onnoA
     * @date: 2021/9/28
     */
//    public void setParameters(PreparedStatement ps) throws SQLException {
//        ErrorContext.instance().activity("setting parameters").object(this.mappedStatement.getParameterMap().getId());
//        List<ParameterMapping> parameterMappings = this.boundSql.getParameterMappings();
//        if (parameterMappings != null) {
//            MetaObject metaObject = this.parameterObject == null ? null : this.configuration.newMetaObject(this.parameterObject);
//
//            for(int i = 0; i < parameterMappings.size(); ++i) {
//                ParameterMapping parameterMapping = (ParameterMapping)parameterMappings.get(i);
//                if (parameterMapping.getMode() != ParameterMode.OUT) {
//                    String propertyName = parameterMapping.getProperty();
//                    Object value;
//                    if (this.boundSql.hasAdditionalParameter(propertyName)) {
//                        value = this.boundSql.getAdditionalParameter(propertyName);
//                    } else if (this.parameterObject == null) {
//                        value = null;
//                    } else if (this.typeHandlerRegistry.hasTypeHandler(this.parameterObject.getClass())) {
//                        value = this.parameterObject;
//                    } else {
//                        value = metaObject == null ? null : metaObject.getValue(propertyName);
//                    }
//
//                    TypeHandler typeHandler = parameterMapping.getTypeHandler();
//                    if (typeHandler == null) {
//                        throw new ExecutorException("There was no TypeHandler found for parameter " + propertyName + " of statement " + this.mappedStatement.getId());
//                    }
//
//                    JdbcType jdbcType = parameterMapping.getJdbcType();
//                    if (value == null && jdbcType == null) {
//                        jdbcType = this.configuration.getJdbcTypeForNull();
//                    }
//
//                    typeHandler.setParameter(ps, i + 1, value, jdbcType);
//                }
//            }
//        }
//
//    }

//    @Override
//    public void setParameters(PreparedStatement ps) {
//        ErrorContext.instance().activity("setting parameters").object(mappedStatement.getParameterMap().getId());
//        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
//        if (parameterMappings != null) {
//            for (int i = 0; i < parameterMappings.size(); i++) {
//                ParameterMapping parameterMapping = parameterMappings.get(i);
//                if (parameterMapping.getMode() != ParameterMode.OUT) {
//                    Object value;
//                    String propertyName = parameterMapping.getProperty();
//                    if (boundSql.hasAdditionalParameter(propertyName)) { // issue #448 ask first for additional params
//                        value = boundSql.getAdditionalParameter(propertyName);
//                    } else if (parameterObject == null) {
//                        value = null;
//                    } else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
//                        value = parameterObject;
//                    } else {
//                        MetaObject metaObject = configuration.newMetaObject(parameterObject);
//                        value = metaObject.getValue(propertyName);
//                    }
//                    TypeHandler typeHandler = parameterMapping.getTypeHandler();
//                    JdbcType jdbcType = parameterMapping.getJdbcType();
//                    if (value == null && jdbcType == null) {
//                        jdbcType = configuration.getJdbcTypeForNull();
//                    }
//                    try {
//                        typeHandler.setParameter(ps, i + 1, value, jdbcType);
//                    } catch (TypeException e) {
//                        throw new TypeException("Could not set parameters for mapping: " + parameterMapping + ". Cause: " + e, e);
//                    } catch (SQLException e) {
//                        throw new TypeException("Could not set parameters for mapping: " + parameterMapping + ". Cause: " + e, e);
//                    }
//                }
//            }
//        }
//    }

    /***
     * @description:  MappedStatement类的 sql 拼接方法
     * @param: parameterObject
     * @return: org.apache.ibatis.mapping.BoundSql
     * @author: onnoA
     * @date: 2021/9/27
     */
//    public BoundSql getBoundSql(Object parameterObject) {
//        BoundSql boundSql = sqlSource.getBoundSql(parameterObject);
//        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
//        if (parameterMappings == null || parameterMappings.isEmpty()) {
//            boundSql = new BoundSql(configuration, boundSql.getSql(), parameterMap.getParameterMappings(), parameterObject);
//        }
//
//        // check for nested result maps in parameter mappings (issue #30)
//        for (ParameterMapping pm : boundSql.getParameterMappings()) {
//            String rmId = pm.getResultMapId();
//            if (rmId != null) {
//                ResultMap rm = configuration.getResultMap(rmId);
//                if (rm != null) {
//                    hasNestedResultMaps |= rm.hasNestedResultMaps();
//                }
//            }
//        }
//
//        return boundSql;
//    }


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

//    public static String getInsertSql(String sql) {
//        return "<insert>" + sql + "</insert>";
//    }
//
//    public static String getSelectSql(String sql) {
//        return "<select>" + sql + "</select>";
//    }
//
//    public static String getUpdateSql(String sql) {
//        return "<update>" + sql + "</update>";
//    }
//
//    public static String parse(String sql) {
//
//        return null;
//    }

    public static Document parseXMLDocument(String xmlString) {
        if (xmlString == null) {
            throw new IllegalArgumentException();
        }
        try {
            return newDocumentBuilder().parse(new InputSource(new StringReader(xmlString)));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());

        }
    }

    public static DocumentBuilder newDocumentBuilder() throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        return dbf.newDocumentBuilder();
    }

    public static Map<String, String> processRoot(Element root) {
        NodeList nl = root.getChildNodes();
        int len = nl.getLength();
        Map<String, String> m = new HashMap<String, String>();
        for (int i = 0; i < len; i++) {
            Node item = nl.item(i);
            if (item instanceof Element) {
                // 加载MSG选项
                Element el = (Element) item;
                String nodeName = el.getNodeName();
                String content = el.getTextContent();
                m.put(nodeName, content);
            }
        }
        return m;

    }
    public static String getOfficalSql(String sql){
        return sql.replaceAll("\\n","").replaceAll("[ ]+"," ");
    }

}
