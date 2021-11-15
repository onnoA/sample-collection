package com.onnoa.shop.common.dto;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
// XML文件中的根标识
@XmlRootElement(name = "Data")
// 控制JAXB 绑定类中属性和字段的排序
//@XmlType(propOrder = {
//        "IntfCode",
//        "Params"
//})
public class User implements Serializable {

    @XmlElement(name = "IntfCode")
    private String intfCode;
    @XmlElement(name = "Params")
    private List<TestDto> params;

    public User() {
    }

    public String getIntfCode() {
        return intfCode;
    }

    public void setIntfCode(String intfCode) {
        this.intfCode = intfCode;
    }

    public List<TestDto> getParams() {
        return params;
    }

    public void setParams(List<TestDto> params) {
        this.params = params;
    }

    public User(String intfCode, List<TestDto> params) {
        this.intfCode = intfCode;
        this.params = params;
    }

    @Override
    public String toString() {
        return "User{" +
                "intfCode='" + intfCode + '\'' +
                ", params=" + params +
                '}';
    }
}
