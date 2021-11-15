package cn.quellanan.sqlsession;

import cn.quellanan.config.XmlConfigBuilder;
import cn.quellanan.config.XmlMapperBuilder;
import cn.quellanan.pojo.Configuration;
import cn.quellanan.pojo.Mapper;
import org.dom4j.DocumentException;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.util.HashMap;

public class SqlSessionFactoryBuilder {

    private Configuration configuration;


    public SqlSessionFactoryBuilder(){
        configuration = new Configuration();
    }

    public SqlSessionFactory build(InputStream in) throws DocumentException, PropertyVetoException, ClassNotFoundException {
        XmlConfigBuilder xmlConfigBuilder = new XmlConfigBuilder(configuration);
        configuration=xmlConfigBuilder.loadXmlConfig(in);
        System.out.println("configuration 信息: " + configuration.toString());
        return new DefaultSqlSessionFactory(this.configuration);
    }

//    public void build(InputStream in) {
//
//    }

   /* public SqlSessionFactory build(InputStream in) throws DocumentException, PropertyVetoException, ClassNotFoundException {
        XmlConfigBuilder xmlConfigBuilder = new XmlConfigBuilder(configuration);
        configuration=xmlConfigBuilder.loadXmlConfig(in);
        String testStrSql = "<mapper namespace=\"cn.quellanan.dao.UserDao\">\n" +
                " <select id=\"selectById\" resultType=\"cn.quellanan.pojo.User\" paramType=\"cn.quellanan.pojo.User\">\n" +
                "        select * from user where id=#{id}\n" +
                "    </select>\n" +
                "</mapper>";
        XmlMapperBuilder xmlMapperBuilder = new XmlMapperBuilder(configuration);
        configuration = xmlMapperBuilder.loadXmlMapper(new StringBufferInputStream(testStrSql));


        return new DefaultSqlSessionFactory(this.configuration);
    }*/

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }
}
