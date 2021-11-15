package com.onnoa.shop.demo.video.test;


import com.google.common.collect.Maps;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @className: MybatisXmlCreate
 * @description:
 * @author: onnoA
 * @date: 2021/9/23
 **/
public class MybatisXmlCreate {

    public static void main(String[] args) {
//        Map<String, String> map = Maps.newHashMap();
//        map.put("select", "select");

//        Document document = getDocument(map);
        Document document = getDocument();
        String str = document.getFormattedContent(0);
        System.out.println(str);
//        createXml();
    }

    public static Document getDocument() {
        Document document = new Document("-//mybatis.org//DTD Mapper 3.0//EN", "http://mybatis.org/dtd/mybatis-3-mapper.dtd");
        XmlElement answer = new XmlElement("mapper");
        String namespace = "com.github.codegenerator.Mapper";
        answer.addAttribute(new Attribute("namespace", namespace));
        answer.addElement(answer);
//        if(!CollectionUtils.isEmpty(attr)){
//            for (Map.Entry<String, String> entry : attr.entrySet()) {
//                answer.addAttribute(new Attribute(entry.getKey(), entry.getValue()));
//                answer.addElement(answer);
//            }
//        }
        document.setRootElement(answer);
        return document;
    }


//    public static void createXml() {
//        try {
//            // 创建解析器工厂
//            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//            DocumentBuilder db = factory.newDocumentBuilder();
//            Document document = db.newDocument();
////            document.setTextContent("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\" >");
//            // 不显示standalone="no"
//            document.setXmlStandalone(true);
//            Element bookstore = document.createElement("bookstore");
//            // 向bookstore根节点中添加子节点book
//            Element book = document.createElement("book");
//
//            Element name = document.createElement("name");
//            // 不显示内容 name.setNodeValue("不好使");
//            name.setTextContent("雷神");
//            book.appendChild(name);
//            // 为book节点添加属性
//            book.setAttribute("id", "1");
//            // 将book节点添加到bookstore根节点中
//            bookstore.appendChild(book);
//            // 将bookstore节点（已包含book）添加到dom树中
//            document.appendChild(bookstore);
//
//
//            TransformerFactory tff = TransformerFactory.newInstance();
//            // 创建 Transformer对象
//            Transformer tf = tff.newTransformer();
//
//            // 输出内容是否使用换行
//            tf.setOutputProperty(OutputKeys.INDENT, "yes");
//            // 创建xml文件并写入内容
//            tf.transform(new DOMSource(document), new StreamResult(new File("mybatis.xml")));
//            System.out.println("生成book1.xml成功" + new DOMSource(document));
//        } catch (
//                Exception e) {
//            e.printStackTrace();
//            System.out.println("生成book1.xml失败");
//        }
//    }
}
