package com.onnoa.shop.demo.video.xml;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @className: XmlHandler
 * @description:
 * @author: onnoA
 * @date: 2021/9/23
 **/
public class XmlHandler {

    //整个xml的节点数据（把设置的每个层级XmlFormat都添加到这个集合中）
    public List<XmlFormat> documentElements = new ArrayList<XmlFormat>();

    /** 生成Dom树
     *  返回Document(整个Dom Tree)
     * */
    private Document createDom(){
        DocumentBuilderFactory factory = null;
        DocumentBuilder builder = null;
        Document document = null;
        try {
            factory = DocumentBuilderFactory.newInstance();  //创建DocumentBuilderFactory工厂对象
            builder = factory.newDocumentBuilder();          //通过工厂对象, 创建DocumentBuilder制作对象
            document = builder.newDocument();                //通过制作对象, 创建一个Document对象,该对象代表一个XML文件
            document.setXmlStandalone(true);                 //设置XMLstandalone, true为没有dtd和schema作为该XML的说明文档

            //创建根节点
            Element root = document.createElement(documentElements.get(0).getRootName());
            document.appendChild(root);

            //循环创建整个DOM树
            for(int i = 0; i < documentElements.size(); i++){
                XmlFormat format = documentElements.get(i);   //获取xml的一个完整层级
                //循环创建层级节点
                for(Entry<String, Object> entryMultiply : format.getMultiplyNames().entrySet()){
                    if(entryMultiply.getValue() != null && !entryMultiply.getValue().toString().equalsIgnoreCase("")){ // 层级节点键/值(没有属性节点)
                        Element multiplyNode = document.createElement(entryMultiply.getKey());      // 创建一个层级节点
                        multiplyNode.setTextContent(entryMultiply.getValue().toString());           // 设置该层级节点的值
                        root.appendChild(multiplyNode);
                    } else {                                                                        // 层级节点键(带有属性节点)
                        Element multiplyNode = document.createElement(entryMultiply.getKey());      // 创建一个层级节点
                        root.appendChild(multiplyNode);
                        //循环创建属性节点
                        for(Entry<String, Object> entryProperty : format.getPropertyNames().entrySet()){
                            Element propertyNode = document.createElement(entryProperty.getKey());  // 创建一个属性节点
                            propertyNode.setTextContent(entryProperty.getValue().toString());       // 设置该属性节点的值
                            multiplyNode.appendChild(propertyNode);
                        }
                        root.appendChild(multiplyNode);         //设置层级节点到根节点
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return document;
    }


    /** 生成xml字符串
     *  参数: Document树对象
     *  返回String: 整个xml字符串
     * */
    private String createXmlToString(Document document){
        String xmlString = null;
        try {
            // 创建TransformerFactory工厂对象
            TransformerFactory transFactory = TransformerFactory.newInstance();
            // 通过工厂对象, 创建Transformer对象
            Transformer transformer = transFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            //使Xml自动换行, 并自动缩进
            transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");  //中间的参数网址固定写法(这里还没搞懂)
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");                          //是否设置缩进（indent: yes|no）
            // 创建DOMSource对象并将Document加载到其中
            DOMSource domSource = new DOMSource(document);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            // 使用Transformer的transform()方法将DOM树转换成XML
            transformer.transform(domSource, new StreamResult(bos));
            xmlString = bos.toString();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        return xmlString;
    }


    /** 生成xml文件
     *  参数: url存放文件路径, Document树对象
     *  返回String: 反馈信息
     * */
    private String createXmlToFile(String url, Document document){
        String message = null;
        try{
            // 创建TransformerFactory对象
            TransformerFactory transFactory = TransformerFactory.newInstance();
            // 创建Transformer对象
            Transformer transformer = transFactory.newTransformer();
            //使Xml自动换行, 并自动缩进
            transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");  //中间的参数网址固定写法(这里还没搞懂)
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");                          //是否设置缩进（indent: yes|no）
            // 建DOMSource对象并将Document加载到其中
            DOMSource domSource = new DOMSource(document);
            //生成xml文件
            File file = new File(url);
            if (!file.exists()) {       //判断文件是否存在
                file.createNewFile();   //不存在生存文件
            }
            FileOutputStream out = new FileOutputStream(file);     //文件输出流
            StreamResult xmlResult = new StreamResult(out);        //设置输入源
            // 使用Transformer的transform()方法将DOM树转换成XML(参数:DOMSource, 输入源)
            transformer.transform(domSource, xmlResult);
            message = "生成本地XML成功!";
        }catch(Exception e){
            e.printStackTrace();
            message = "生成本地XML失败!";
        }
        return message;
    }

    /** 接口方法(重载)
     *  创建Xml字符串
     *  返回String: xml字符串
     * */
    public String createXml(){
        String xmlString = null;
        if(documentElements != null && documentElements.size() > 0){    //判断是否存在xml格式和内容
            Document document = createDom();                //调用 生成Dom树 的方法
            xmlString = createXmlToString(document);        //调用 生成xml字符串 的方法
        }
        return xmlString;
    }

    /** 接口方法(重载)
     *  创建Xml本地文件(参数: 路径)
     *  返回String: 成功/失败消息
     * */
    public String createXml(String url){
        String xmlMessage = null;
        if(documentElements != null && documentElements.size() > 0){    //判断是否存在xml格式和内容
            Document document = createDom();                //调用 生成Dom树 的方法
            xmlMessage =  createXmlToFile(url, document);   //调用 生成xml文件 的方法
        }
        return xmlMessage;
    }
}