package com.onnoa.shop.demo.upload.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
// XML文件中的根标识
@XmlRootElement(name = "Data")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class XMLResponseDto implements Serializable {

    @XmlElement(name = "IntfCode")
    private String intfCode;
    @XmlElement(name = "Return")
    private Response response;


    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlRootElement(name = "Return")
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        @XmlElement(name = "RETURN_CODE")
        private int returnCode;
        @XmlElement(name = "PORT_NUM")
        private int portNum;
        @XmlElement(name = "PORT_NO")
        private String portNo;
        @XmlElement(name = "CODE_BAR")
        private String codeBar;
        @XmlElement(name = "MESSAGE")
        private String message;
        @XmlElement(name = "PORT_LIST")
        private List<PortNum> portList;

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlRootElement(name = "PORT_LIST")
        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        public static class PortNum{
            @XmlElement(name = "PORT_NUM")
            private Integer portNum;

        }



    }
}
