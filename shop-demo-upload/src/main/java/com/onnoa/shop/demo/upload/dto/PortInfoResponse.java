package com.onnoa.shop.demo.upload.dto;

import lombok.ToString;

import java.io.Serializable;

@ToString
public class PortInfoResponse implements Serializable {


    /**
     * Body : {"otherWSInterfaceResponse":{"out":{"Data":{"Return":{"MESSAGE":"","PORT_NO":"732YTQ.CJXFH/GF023/OBD01/007","RETURN_CODE":"0","PORT_NUM":"1","CODE_BAR":"1003213040000076"},"IntfCode":" qryOLTResInfo "}}}}
     */

    private BodyBean Body;

    public BodyBean getBody() {
        return Body;
    }

    public void setBody(BodyBean Body) {
        this.Body = Body;
    }

    @ToString
    public static class BodyBean {
        /**
         * otherWSInterfaceResponse : {"out":{"Data":{"Return":{"MESSAGE":"","PORT_NO":"732YTQ.CJXFH/GF023/OBD01/007","RETURN_CODE":"0","PORT_NUM":"1","CODE_BAR":"1003213040000076"},"IntfCode":" qryOLTResInfo "}}}
         */

        private OtherWSInterfaceResponseBean otherWSInterfaceResponse;

        public OtherWSInterfaceResponseBean getOtherWSInterfaceResponse() {
            return otherWSInterfaceResponse;
        }

        public void setOtherWSInterfaceResponse(OtherWSInterfaceResponseBean otherWSInterfaceResponse) {
            this.otherWSInterfaceResponse = otherWSInterfaceResponse;
        }

        @ToString
        public static class OtherWSInterfaceResponseBean {
            /**
             * out : {"Data":{"Return":{"MESSAGE":"","PORT_NO":"732YTQ.CJXFH/GF023/OBD01/007","RETURN_CODE":"0","PORT_NUM":"1","CODE_BAR":"1003213040000076"},"IntfCode":" qryOLTResInfo "}}
             */

            private OutBean out;

            public OutBean getOut() {
                return out;
            }

            public void setOut(OutBean out) {
                this.out = out;
            }

            @ToString
            public static class OutBean {
                /**
                 * Data : {"Return":{"MESSAGE":"","PORT_NO":"732YTQ.CJXFH/GF023/OBD01/007","RETURN_CODE":"0","PORT_NUM":"1","CODE_BAR":"1003213040000076"},"IntfCode":" qryOLTResInfo "}
                 */

                private DataBean Data;

                public DataBean getData() {
                    return Data;
                }

                public void setData(DataBean Data) {
                    this.Data = Data;
                }

                @ToString
                public static class DataBean {
                    /**
                     * Return : {"MESSAGE":"","PORT_NO":"732YTQ.CJXFH/GF023/OBD01/007","RETURN_CODE":"0","PORT_NUM":"1","CODE_BAR":"1003213040000076"}
                     * IntfCode :  qryOLTResInfo
                     */

                    private ReturnBean Return;
                    private String IntfCode;

                    public ReturnBean getReturn() {
                        return Return;
                    }

                    public void setReturn(ReturnBean Return) {
                        this.Return = Return;
                    }

                    public String getIntfCode() {
                        return IntfCode;
                    }

                    public void setIntfCode(String IntfCode) {
                        this.IntfCode = IntfCode;
                    }

                    @ToString
                    public static class ReturnBean {
                        /**
                         * MESSAGE :
                         * PORT_NO : 732YTQ.CJXFH/GF023/OBD01/007
                         * RETURN_CODE : 0
                         * PORT_NUM : 1
                         * CODE_BAR : 1003213040000076
                         */

                        private String MESSAGE;
                        private String PORT_NO;
                        private String RETURN_CODE;
                        private String PORT_NUM;
                        private String CODE_BAR;

                        public String getMESSAGE() {
                            return MESSAGE;
                        }

                        public void setMESSAGE(String MESSAGE) {
                            this.MESSAGE = MESSAGE;
                        }

                        public String getPORT_NO() {
                            return PORT_NO;
                        }

                        public void setPORT_NO(String PORT_NO) {
                            this.PORT_NO = PORT_NO;
                        }

                        public String getRETURN_CODE() {
                            return RETURN_CODE;
                        }

                        public void setRETURN_CODE(String RETURN_CODE) {
                            this.RETURN_CODE = RETURN_CODE;
                        }

                        public String getPORT_NUM() {
                            return PORT_NUM;
                        }

                        public void setPORT_NUM(String PORT_NUM) {
                            this.PORT_NUM = PORT_NUM;
                        }

                        public String getCODE_BAR() {
                            return CODE_BAR;
                        }

                        public void setCODE_BAR(String CODE_BAR) {
                            this.CODE_BAR = CODE_BAR;
                        }
                    }
                }
            }
        }
    }
}
