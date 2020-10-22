package com.onnoa.shop.demo.upload.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
// XML文件中的根标识
@XmlRootElement(name = "Data")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QryBusPortInfoDto implements Serializable {

    @XmlElement(name = "IntfCode")
    private String intfCode;
    //@XmlElement(name = "Params")
    private OrderDto Params;
    @XmlElement(name = "Params")
    private CodeDto codeParams;

    @XmlAccessorType(XmlAccessType.FIELD)
    //@XmlRootElement(name = "Params")
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderDto {
        @XmlElement(name = "DIS_SEQ")
        private String disSeq;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlRootElement(name = "Params")
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CodeDto {
        @XmlElement(name = "CODE_BAR")
        private String codeBar;
    }

}
