package cn.quellanan.util;

import cn.quellanan.pojo.MybatisXmlConfig;
import com.google.common.collect.Lists;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

/**
 * @className: utils
 * @description:
 * @author: onnoA
 * @date: 2021/11/1
 **/
public class MapperXmlUtil {

    public static String createMapperXml(List<MybatisXmlConfig> xmlConfigList, String namespace){
        StringWriter strWtr = null;
        if(!CollectionUtils.isEmpty(xmlConfigList)){
            Document document = DocumentHelper.createDocument();
            Element root = document.addElement("mapper");
//            document.addDocType("mapper","-//mybatis.org//DTD Mapper 3.0//EN","http://mybatis.org/dtd/mybatis-3-mapper.dtd");
            root.addAttribute("namespace", namespace);
            for (MybatisXmlConfig xmlConfig : xmlConfigList) {
//                selectNodes() 方法用一个 XPath 查询选择节点。
//                List<Node> select = root.selectNodes("select");
                Element nodeElement = root.addElement(xmlConfig.getSqltagtype());
                nodeElement.addAttribute("id", xmlConfig.getSqlidname());
                if(StringUtils.hasText(xmlConfig.getResulttype())){
                    nodeElement.addAttribute("resultType",  xmlConfig.getResulttype());
                }
                if(StringUtils.hasText(xmlConfig.getParamtype())){
                    nodeElement.addAttribute("paramType", xmlConfig.getParamtype());
                }
                nodeElement.setText(xmlConfig.getSqlstatement());
            }
            strWtr = new StringWriter();
            OutputFormat format = OutputFormat.createPrettyPrint();//Format格式！！
            format.setEncoding("UTF-8");
            XMLWriter xmlWriter = new XMLWriter(strWtr, format);
            try {
                xmlWriter.write(document);
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
        return String.valueOf(strWtr);
    }

    public String createXML(int i){
        String strXML = null;
        Document document = DocumentHelper.createDocument();
        // 创建根节点并添加进文档
        Element root = document.addElement("persons");
        for(int j=0;j<i;j++){
            Element person = root.addElement("person");
            person.addAttribute("id", "100"+i).addAttribute("location", "中原"+i+"区");
            Element name = person.addElement("name");
            name.setText("小明"+i);
            Element age = person.addElement("age");
            age.addText("1"+i); //值！！
        }
        //--------
        StringWriter strWtr = new StringWriter();
        OutputFormat format = OutputFormat.createPrettyPrint();//Format格式！！
        format.setEncoding("UTF-8");
        XMLWriter xmlWriter =new XMLWriter(strWtr, format);
        try {
            xmlWriter.write(document);
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        strXML = strWtr.toString();
        return strXML;
    }
}


