package com.onnoa.shop.common.utils;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

/**
 * @className: XmlTest
 * @description:
 * @author: onnoA
 * @date: 2021/11/1
 **/
public class XmlTest {


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
    public static void main(String[] args) {
        XmlTest test = new XmlTest();

        String xmlStr = test.createXML(4);
        System.out.println(xmlStr);
    }
}
