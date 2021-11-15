package com.onnoa.shop.demo.upload.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * ocr_customer_order_attr
 *
 * @author
 */
@Accessors(chain = true)
@Data
public class OcrCustomerOrderAttrDTO {
    private Long instAttrId;

    private Long instId;

    /**
     * 场景编码
     */
    private String scenarioCode;

    /**
     * 客户订单id
     */
    private String custOrderId;

    /**
     * 本地网
     */
    private String lanId;

    /**
     * 证件号码
     */
    private String certiNumber;

    /**
     * 证件类型
     */
    private String certiType;

    /**
     * 附件id（拍照流程）
     */
    private String fileId;

    /**
     * 是否安真通
     */
    private Integer anZhengTong;

    /**
     * 证件类型
     */
    private String fileType;

    /**
     * 受理渠道
     */
    private String acceptMode;

    /**
     * 本地回执id
     */
    private String receiptFileId;

    /**
     * 收单时间
     */
    private Date receptTime;

    /**
     * 同步数据账期字段
     */
    private Integer acceptDate;

    private Date gmtCreate;

    private Date gmtUpdate;

    /**
     * 活体照片
     */
    private String livingImage;

    /**
     * 证件正面照
     */
    private String certImageFront;

    /**
     * 证件反面照
     */
    private String certImageBack;

    /**
     * 其他照片
     */
    private String otherImage;

    /**
     * 芯片照片
     */
    private String certImageFile;

    /**
     * 证件号码(算法识别)
     */
    private String aiCertiNumber;

    /**
     * 算法分类接口返回报文
     */
    private String resultMsg;

    private static final long serialVersionUID = 1L;

//    public Long getInstAttrId() {
//        return instAttrId;
//    }
//
//    public void setInstAttrId(Long instAttrId) {
//        this.instAttrId = instAttrId;
//    }
//
//    public Long getInstId() {
//        return instId;
//    }
//
//    public void setInstId(Long instId) {
//        this.instId = instId;
//    }
//
//    public String getScenarioCode() {
//        return scenarioCode;
//    }
//
//    public void setScenarioCode(String scenarioCode) {
//        this.scenarioCode = scenarioCode;
//    }
//
//    public String getCustOrderId() {
//        return custOrderId;
//    }
//
//    public void setCustOrderId(String custOrderId) {
//        this.custOrderId = custOrderId;
//    }
//
//    public String getLanId() {
//        return lanId;
//    }
//
//    public void setLanId(String lanId) {
//        this.lanId = lanId;
//    }
//
//    public String getCertiNumber() {
//        return certiNumber;
//    }
//
//    public void setCertiNumber(String certiNumber) {
//        this.certiNumber = certiNumber;
//    }
//
//    public String getCertiType() {
//        return certiType;
//    }
//
//    public void setCertiType(String certiType) {
//        this.certiType = certiType;
//    }
//
//    public String getFileId() {
//        return fileId;
//    }
//
//    public void setFileId(String fileId) {
//        this.fileId = fileId;
//    }
//
//    public Integer getAnZhengTong() {
//        return anZhengTong;
//    }
//
//    public void setAnZhengTong(Integer anZhengTong) {
//        this.anZhengTong = anZhengTong;
//    }
//
//    public String getFileType() {
//        return fileType;
//    }
//
//    public void setFileType(String fileType) {
//        this.fileType = fileType;
//    }
//
//    public String getAcceptMode() {
//        return acceptMode;
//    }
//
//    public void setAcceptMode(String acceptMode) {
//        this.acceptMode = acceptMode;
//    }
//
//    public String getReceiptFileId() {
//        return receiptFileId;
//    }
//
//    public void setReceiptFileId(String receiptFileId) {
//        this.receiptFileId = receiptFileId;
//    }
//
//    public Date getReceptTime() {
//        return receptTime;
//    }
//
//    public void setReceptTime(Date receptTime) {
//        this.receptTime = receptTime;
//    }
//
//    public Integer getAcceptDate() {
//        return acceptDate;
//    }
//
//    public void setAcceptDate(Integer acceptDate) {
//        this.acceptDate = acceptDate;
//    }
//
//    public Date getGmtCreate() {
//        return gmtCreate;
//    }
//
//    public void setGmtCreate(Date gmtCreate) {
//        this.gmtCreate = gmtCreate;
//    }
//
//    public Date getGmtUpdate() {
//        return gmtUpdate;
//    }
//
//    public void setGmtUpdate(Date gmtUpdate) {
//        this.gmtUpdate = gmtUpdate;
//    }
//
//    public String getLivingImage() {
//        return livingImage;
//    }
//
//    public void setLivingImage(String livingImage) {
//        this.livingImage = livingImage;
//    }
//
//    public String getCertImageFront() {
//        return certImageFront;
//    }
//
//    public void setCertImageFront(String certImageFront) {
//        this.certImageFront = certImageFront;
//    }
//
//    public String getCertImageBack() {
//        return certImageBack;
//    }
//
//    public void setCertImageBack(String certImageBack) {
//        this.certImageBack = certImageBack;
//    }
//
//    public String getOtherImage() {
//        return otherImage;
//    }
//
//    public void setOtherImage(String otherImage) {
//        this.otherImage = otherImage;
//    }
//
//    public String getCertImageFile() {
//        return certImageFile;
//    }
//
//    public void setCertImageFile(String certImageFile) {
//        this.certImageFile = certImageFile;
//    }
//
//    public String getAiCertiNumber() {
//        return aiCertiNumber;
//    }
//
//    public void setAiCertiNumber(String aiCertiNumber) {
//        this.aiCertiNumber = aiCertiNumber;
//    }
//
//    public String getResultMsg() {
//        return resultMsg;
//    }
//
//    public void setResultMsg(String resultMsg) {
//        this.resultMsg = resultMsg;
//    }
}