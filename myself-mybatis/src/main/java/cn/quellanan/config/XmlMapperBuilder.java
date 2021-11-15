package cn.quellanan.config;

import cn.quellanan.pojo.Configuration;
import cn.quellanan.pojo.Mapper;
import cn.quellanan.pojo.SqlCommandType;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;
import java.util.Locale;

public class XmlMapperBuilder {


    private Configuration configuration;

    public XmlMapperBuilder(Configuration configuration) {
        this.configuration = configuration;
    }


    public Configuration loadXmlMapper(InputStream in) throws DocumentException, ClassNotFoundException {
        Document document = new SAXReader().read(in);

        Element rootElement = document.getRootElement();
        String namespace = rootElement.attributeValue("namespace");
        //SqlCommandType sqlCommandType = SqlCommandType.valueOf(nodeName.toUpperCase(Locale.ENGLISH));
        handle(namespace, rootElement.selectNodes("//select"));
        handle(namespace, rootElement.selectNodes("//insert"));
        handle(namespace, rootElement.selectNodes("//update"));
        handle(namespace, rootElement.selectNodes("//delete"));
        return configuration;
    }

    private void handle(String namespace, List<Node> list) throws ClassNotFoundException {
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
    }

}
