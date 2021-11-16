package cn.quellanan.config;

import cn.quellanan.io.Resources;
import cn.quellanan.pojo.Configuration;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class XmlConfigBuilder {


    private Configuration configuration;

    public XmlConfigBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    public Configuration loadXmlConfig(InputStream in) throws DocumentException, PropertyVetoException, ClassNotFoundException {

        Document document = new SAXReader().read(in);

        Element rootElement = document.getRootElement();

        //获取连接信息
        List<Node> propertyList = rootElement.selectNodes("//property");
        Properties properties = new Properties();

        for (int i = 0; i < propertyList.size(); i++) {
            Element element = (Element) propertyList.get(i);
            properties.setProperty(element.attributeValue("name"), element.attributeValue("value"));
        }

        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass(properties.getProperty("driverClass"));
        dataSource.setJdbcUrl(properties.getProperty("jdbcUrl"));
        dataSource.setUser(properties.getProperty("userName"));
        dataSource.setPassword(properties.getProperty("password"));
        configuration.setDataSource(dataSource);


        //获取mapper 信息
        List<Node> mapperList = rootElement.selectNodes("//mapper");
        for (int i = 0; i < mapperList.size(); i++) {
            Element element = (Element) mapperList.get(i);

            String mapperPath = element.attributeValue("resource");
            System.out.println("mapperPath: " + mapperPath);
            XmlMapperBuilder xmlMapperBuilder = new XmlMapperBuilder(configuration);
            configuration = xmlMapperBuilder.loadXmlMapper(Resources.getResources(mapperPath));
        }

        return configuration;
    }


}
