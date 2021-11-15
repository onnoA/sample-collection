package com.onnoa.shop.demo.video.xml;

import com.sun.org.apache.xerces.internal.dom.CoreDocumentImpl;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.w3c.dom.Document;

import java.util.HashMap;

/**
 * @className: Client
 * @description:
 * @author: onnoA
 * @date: 2021/9/23
 **/
public class Client {

    //方法使用1（该方法为第一张结果截图,每个层级节点下，带有属性节点）
    public static void main(String[] args) {
        //创建数据(这里大家可以从数据库读出)
        String rootName = "mapper";                    //根节点名称

        String multiplyOne = "select";                //层级节点名称
        HashMap<String, Object> propertyOnes = new HashMap<String, Object>();   //属性节点集合
        propertyOnes.put("name", "chuck");
        propertyOnes.put("age", 20);

        String multiplyTwo = "select";               //层级节点名称
        HashMap<String, Object> propertyTwos = new HashMap<String, Object>();   //属性节点集合
        propertyTwos.put("name", "Jack");
        propertyTwos.put("age", 50);

        //创建Xmlformat格式类: 一个Xmlformat为一个层级(以下有两个层级,大家可以回到上面看结果截图)
        XmlFormat xmlformatOne = new XmlFormat(rootName);   //创建xml格式,并传入 根节点名称
        xmlformatOne.setElement(multiplyOne, propertyOnes); //设置层级节点名称 和 属性节点的集合 (setElement为重载方法)

        XmlFormat xmlformatTwo = new XmlFormat(rootName);   //创建xml格式,并传入 根节点名称
        xmlformatTwo.setElement(multiplyTwo, propertyTwos); //设置层级节点名称 和 属性节点的集合(setElement为重载方法)

        //创建XmlHandler
        XmlHandler xmlhandler = new XmlHandler();
        xmlhandler.documentElements.add(xmlformatOne);      //把格式XmlFormat, 设置xmlhandler里面的集合
        xmlhandler.documentElements.add(xmlformatTwo);      //把格式XmlFormat, 设置xmlhandler里面的集合

        String xmlString = xmlhandler.createXml();  //调用xmlHandler的接口方法, 获取xml字符串
        System.out.println(xmlString);


//        new XMLMapperBuilder()
    }


}
