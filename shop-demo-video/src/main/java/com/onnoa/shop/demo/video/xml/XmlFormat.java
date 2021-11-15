package com.onnoa.shop.demo.video.xml;

import java.util.HashMap;

/**
 * @className: XmlFormat
 * @description:
 * @author: onnoA
 * @date: 2021/9/23
 **/
public class XmlFormat {

    //根节点名称
    private String rootName;
    //层级节点 名称/值 (值可为空。当值为空时，代表节点下带有属性节点。 当值不等于空时，代表该节点直接包含内容，没有属性节点)
    private HashMap<String, Object> multiplyNames;
    //属性节点 名称/值
    private HashMap<String, Object> propertyNames;

    /**构造函数
     * 参数: 根节点名称
     * */
    public XmlFormat(String rootName){
        this.rootName = rootName;
        this.multiplyNames = new HashMap<String, Object>();
        this.propertyNames = new HashMap<String, Object>();
    }

    /**（重载）用于xml层级节点键/值(没有属性节点)
     * 参数: 层级节点名称, 层级节点的值
     * */
    public void setElement(String multiplyName, String multiplyValue) {
        this.multiplyNames.put(multiplyName, multiplyValue);
    }

    /**（重载）用于xml层级节点,属性节点
     * 参数: 层级节点名称, 属性节点键/值
     * */
    public void setElement(String multiplyName, HashMap<String, Object> propertyNames) {
        this.multiplyNames.put(multiplyName, null);
        this.propertyNames = propertyNames;
    }

    public String getRootName() {
        return rootName;
    }

    public HashMap<String, Object> getMultiplyNames() {
        return multiplyNames;
    }

    public HashMap<String, Object> getPropertyNames() {
        return propertyNames;
    }

    public void setRootName(String rootName) {
        this.rootName = rootName;
    }

    public void setMultiplyNames(HashMap<String, Object> multiplyNames) {
        this.multiplyNames = multiplyNames;
    }

    public void setPropertyNames(HashMap<String, Object> propertyNames) {
        this.propertyNames = propertyNames;
    }
}
