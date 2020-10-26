package com.onnoa.shop.demo.upload.dto;

import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@ToString
public class CodeBarResponse implements Serializable {

    /**
     * Body : {"otherWSInterfaceResponse":{"out":{"Data":{"Return":{"MESSAGE":"","RETURN_CODE":"0","PORT_LIST":{"PORT_NO":["732YTQ.ZDKXM/GF001/OBD01/02","732YTQ.ZDKXM/GF001/OBD01/03","732YTQ.ZDKXM/GF001/OBD01/04","732YTQ.ZDKXM/GF001/OBD01/05","732YTQ.ZDKXM/GF001/OBD01/06","732YTQ.ZDKXM/GF001/OBD01/07","732YTQ.ZDKXM/GF001/OBD01/08","732YTQ.ZDKXM/GF001/OBD01/00"],"PORT_NUM":["2","3","4","5","6","7","8","0"]}},"IntfCode":" qryCodeBarPortInfo"}}}}
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
         * otherWSInterfaceResponse : {"out":{"Data":{"Return":{"MESSAGE":"","RETURN_CODE":"0","PORT_LIST":{"PORT_NO":["732YTQ.ZDKXM/GF001/OBD01/02","732YTQ.ZDKXM/GF001/OBD01/03","732YTQ.ZDKXM/GF001/OBD01/04","732YTQ.ZDKXM/GF001/OBD01/05","732YTQ.ZDKXM/GF001/OBD01/06","732YTQ.ZDKXM/GF001/OBD01/07","732YTQ.ZDKXM/GF001/OBD01/08","732YTQ.ZDKXM/GF001/OBD01/00"],"PORT_NUM":["2","3","4","5","6","7","8","0"]}},"IntfCode":" qryCodeBarPortInfo"}}}
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
             * out : {"Data":{"Return":{"MESSAGE":"","RETURN_CODE":"0","PORT_LIST":{"PORT_NO":["732YTQ.ZDKXM/GF001/OBD01/02","732YTQ.ZDKXM/GF001/OBD01/03","732YTQ.ZDKXM/GF001/OBD01/04","732YTQ.ZDKXM/GF001/OBD01/05","732YTQ.ZDKXM/GF001/OBD01/06","732YTQ.ZDKXM/GF001/OBD01/07","732YTQ.ZDKXM/GF001/OBD01/08","732YTQ.ZDKXM/GF001/OBD01/00"],"PORT_NUM":["2","3","4","5","6","7","8","0"]}},"IntfCode":" qryCodeBarPortInfo"}}
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
                 * Data : {"Return":{"MESSAGE":"","RETURN_CODE":"0","PORT_LIST":{"PORT_NO":["732YTQ.ZDKXM/GF001/OBD01/02","732YTQ.ZDKXM/GF001/OBD01/03","732YTQ.ZDKXM/GF001/OBD01/04","732YTQ.ZDKXM/GF001/OBD01/05","732YTQ.ZDKXM/GF001/OBD01/06","732YTQ.ZDKXM/GF001/OBD01/07","732YTQ.ZDKXM/GF001/OBD01/08","732YTQ.ZDKXM/GF001/OBD01/00"],"PORT_NUM":["2","3","4","5","6","7","8","0"]}},"IntfCode":" qryCodeBarPortInfo"}
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
                     * Return : {"MESSAGE":"","RETURN_CODE":"0","PORT_LIST":{"PORT_NO":["732YTQ.ZDKXM/GF001/OBD01/02","732YTQ.ZDKXM/GF001/OBD01/03","732YTQ.ZDKXM/GF001/OBD01/04","732YTQ.ZDKXM/GF001/OBD01/05","732YTQ.ZDKXM/GF001/OBD01/06","732YTQ.ZDKXM/GF001/OBD01/07","732YTQ.ZDKXM/GF001/OBD01/08","732YTQ.ZDKXM/GF001/OBD01/00"],"PORT_NUM":["2","3","4","5","6","7","8","0"]}}
                     * IntfCode :  qryCodeBarPortInfo
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
                         * RETURN_CODE : 0
                         * PORT_LIST : {"PORT_NO":["732YTQ.ZDKXM/GF001/OBD01/02","732YTQ.ZDKXM/GF001/OBD01/03","732YTQ.ZDKXM/GF001/OBD01/04","732YTQ.ZDKXM/GF001/OBD01/05","732YTQ.ZDKXM/GF001/OBD01/06","732YTQ.ZDKXM/GF001/OBD01/07","732YTQ.ZDKXM/GF001/OBD01/08","732YTQ.ZDKXM/GF001/OBD01/00"],"PORT_NUM":["2","3","4","5","6","7","8","0"]}
                         */

                        private String MESSAGE;
                        private String RETURN_CODE;
                        private PORTLISTBean PORT_LIST;

                        public String getMESSAGE() {
                            return MESSAGE;
                        }

                        public void setMESSAGE(String MESSAGE) {
                            this.MESSAGE = MESSAGE;
                        }

                        public String getRETURN_CODE() {
                            return RETURN_CODE;
                        }

                        public void setRETURN_CODE(String RETURN_CODE) {
                            this.RETURN_CODE = RETURN_CODE;
                        }

                        public PORTLISTBean getPORT_LIST() {
                            return PORT_LIST;
                        }

                        public void setPORT_LIST(PORTLISTBean PORT_LIST) {
                            this.PORT_LIST = PORT_LIST;
                        }

                        @ToString
                        public static class PORTLISTBean {
                            private List<String> PORT_NO;
                            private List<String> PORT_NUM;

                            public List<String> getPORT_NO() {
                                return PORT_NO;
                            }

                            public void setPORT_NO(List<String> PORT_NO) {
                                this.PORT_NO = PORT_NO;
                            }

                            public List<String> getPORT_NUM() {
                                return PORT_NUM;
                            }

                            public void setPORT_NUM(List<String> PORT_NUM) {
                                this.PORT_NUM = PORT_NUM;
                            }
                        }
                    }
                }
            }
        }
    }
}
