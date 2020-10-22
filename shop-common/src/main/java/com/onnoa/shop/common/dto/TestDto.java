package com.onnoa.shop.common.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Params")
//@XmlType(propOrder = {"DIS_SEQ"})
public class TestDto implements Serializable {

    @XmlElement(name = "DIS_SEQ")
    private String dis_seq;



    public TestDto() {
    }

    public TestDto(String dis_seq) {
        this.dis_seq = dis_seq;
    }

    public String getDis_seq() {
        return dis_seq;
    }

    public void setDis_seq(String dis_seq) {
        this.dis_seq = dis_seq;
    }

    @Override
    public String toString() {
        return "TestDto{" +
                "dis_seq='" + dis_seq + '\'' +
                '}';
    }
}
