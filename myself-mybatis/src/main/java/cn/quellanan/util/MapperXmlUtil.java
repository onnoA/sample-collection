package cn.quellanan.util;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

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

//    public static List<String> createMapperXml(List<MybatisXmlConfig> xmlConfigList, String namespace){
//        List<String> sqlXmlList = Lists.newArrayList();
//        if(!CollectionUtils.isEmpty(xmlConfigList)){
//            Document document = DocumentHelper.createDocument();
//            Element root = document.addElement("mapper");
//            root.addAttribute("namespace", namespace);
//            for (MybatisXmlConfig xmlConfig : xmlConfigList) {
////                selectNodes() 方法用一个 XPath 查询选择节点。
////                List<Node> select = root.selectNodes("select");
//                Element nodeElement = root.addElement(xmlConfig.getSqlTagType());
//                nodeElement.addAttribute("id", xmlConfig.getSqlIdName());
//                if(StringUtils.hasText(xmlConfig.getResultType())){
//                    nodeElement.addAttribute("resultType",  xmlConfig.getResultType());
//                }
//                if(StringUtils.hasText(xmlConfig.getParamType())){
//                    nodeElement.addAttribute("paramType", xmlConfig.getParamType());
//                }
//                nodeElement.setText(xmlConfig.getSql());
//            }
//            StringWriter strWtr = new StringWriter();
//            OutputFormat format = OutputFormat.createPrettyPrint();//Format格式！！
//            format.setEncoding("UTF-8");
//            XMLWriter xmlWriter =new XMLWriter(strWtr, format);
//            try {
//                xmlWriter.write(document);
//                sqlXmlList.add(strWtr.toString());
//            } catch (IOException e1) {
//                // TODO Auto-generated catch block
//                e1.printStackTrace();
//            }
//
//        }
//        return sqlXmlList;
//    }

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


