package com.onnoa.shop.demo.upload.dto;

import java.util.Date;
import java.util.List;

public class OcrMaintenanceOrder {
    private Long instId;

    private Integer monthId;

    private Long interfaceLogId;
    private String mainSn;
    private String orderId;
    private String subApplyNo;

    private String billType;

    private String nativenetId;
    private String regionId;
    private String clogCode;
    private String productType;
    private String instAddress;
    private Date constructionTime;
    private String repairUnit;

    private String repairPost;
    private String repairOper;

    private String attrId;

    private String attrUrl;

    private String acceptDate;
    private Integer imageNum;

    private Integer imagePassNum;

    private Integer invalidImageNum;
    private Integer existCodeNum;

    private Integer notExistCodeNum;
    private Integer reversalNum;
    private Integer unpasteNum;
    private Integer existPortNum;
    private Integer onlyTagNum;

    private Integer checkFlag;

    private Integer passFlag;

    private Integer execStatus;

    private Integer status;

    private Date gmtCreate;

    private Date gmtUpdate;

    private String resultMsg;

    private List<OcrMaintenanceAudit> maintenanceAuditList;

    public Long getInstId() {
        return instId;
    }

    public void setInstId(Long instId) {
        this.instId = instId;
    }

    public Integer getMonthId() {
        return monthId;
    }

    public void setMonthId(Integer monthId) {
        this.monthId = monthId;
    }

    public Long getInterfaceLogId() {
        return interfaceLogId;
    }

    public void setInterfaceLogId(Long interfaceLogId) {
        this.interfaceLogId = interfaceLogId;
    }

    public String getMainSn() {
        return mainSn;
    }

    public void setMainSn(String mainSn) {
        this.mainSn = mainSn == null ? null : mainSn.trim();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getSubApplyNo() {
        return subApplyNo;
    }

    public void setSubApplyNo(String subApplyNo) {
        this.subApplyNo = subApplyNo;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType == null ? null : billType.trim();
    }

    public String getNativenetId() {
        return nativenetId;
    }

    public void setNativenetId(String nativenetId) {
        this.nativenetId = nativenetId == null ? null : nativenetId.trim();
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId == null ? null : regionId.trim();
    }

    public String getClogCode() {
        return clogCode;
    }

    public void setClogCode(String clogCode) {
        this.clogCode = clogCode == null ? null : clogCode.trim();
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType == null ? null : productType.trim();
    }

    public String getInstAddress() {
        return instAddress;
    }

    public void setInstAddress(String instAddress) {
        this.instAddress = instAddress == null ? null : instAddress.trim();
    }

    public Date getConstructionTime() {
        return constructionTime;
    }

    public void setConstructionTime(Date constructionTime) {
        this.constructionTime = constructionTime;
    }

    public String getRepairUnit() {
        return repairUnit;
    }

    public void setRepairUnit(String repairUnit) {
        this.repairUnit = repairUnit == null ? null : repairUnit.trim();
    }

    public String getRepairPost() {
        return repairPost;
    }

    public void setRepairPost(String repairPost) {
        this.repairPost = repairPost == null ? null : repairPost.trim();
    }

    public String getRepairOper() {
        return repairOper;
    }

    public void setRepairOper(String repairOper) {
        this.repairOper = repairOper == null ? null : repairOper.trim();
    }

    public String getAttrId() {
        return attrId;
    }

    public void setAttrId(String attrId) {
        this.attrId = attrId == null ? null : attrId.trim();
    }

    public String getAttrUrl() {
        return attrUrl;
    }

    public void setAttrUrl(String attrUrl) {
        this.attrUrl = attrUrl == null ? null : attrUrl.trim();
    }

    public String getAcceptDate() {
        return acceptDate;
    }

    public void setAcceptDate(String acceptDate) {
        this.acceptDate = acceptDate == null ? null : acceptDate.trim();
    }

    public Integer getImageNum() {
        return imageNum;
    }

    public void setImageNum(Integer imageNum) {
        this.imageNum = imageNum;
    }

    public Integer getImagePassNum() {
        return imagePassNum;
    }

    public void setImagePassNum(Integer imagePassNum) {
        this.imagePassNum = imagePassNum;
    }

    public Integer getInvalidImageNum() {
        return invalidImageNum;
    }

    public void setInvalidImageNum(Integer invalidImageNum) {
        this.invalidImageNum = invalidImageNum;
    }

    public Integer getExistCodeNum() {
        return existCodeNum;
    }

    public void setExistCodeNum(Integer existCodeNum) {
        this.existCodeNum = existCodeNum;
    }

    public Integer getNotExistCodeNum() {
        return notExistCodeNum;
    }

    public void setNotExistCodeNum(Integer notExistCodeNum) {
        this.notExistCodeNum = notExistCodeNum;
    }

    public Integer getReversalNum() {
        return reversalNum;
    }

    public void setReversalNum(Integer reversalNum) {
        this.reversalNum = reversalNum;
    }

    public Integer getUnpasteNum() {
        return unpasteNum;
    }

    public void setUnpasteNum(Integer unpasteNum) {
        this.unpasteNum = unpasteNum;
    }

    public Integer getExistPortNum() {
        return existPortNum;
    }

    public void setExistPortNum(Integer existPortNum) {
        this.existPortNum = existPortNum;
    }

    public Integer getOnlyTagNum() {
        return onlyTagNum;
    }

    public void setOnlyTagNum(Integer onlyTagNum) {
        this.onlyTagNum = onlyTagNum;
    }

    public Integer getCheckFlag() {
        return checkFlag;
    }

    public void setCheckFlag(Integer checkFlag) {
        this.checkFlag = checkFlag;
    }

    public Integer getPassFlag() {
        return passFlag;
    }

    public void setPassFlag(Integer passFlag) {
        this.passFlag = passFlag;
    }

    public Integer getExecStatus() {
        return execStatus;
    }

    public void setExecStatus(Integer execStatus) {
        this.execStatus = execStatus;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtUpdate() {
        return gmtUpdate;
    }

    public void setGmtUpdate(Date gmtUpdate) {
        this.gmtUpdate = gmtUpdate;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg == null ? null : resultMsg.trim();
    }

    public List<OcrMaintenanceAudit> getMaintenanceAuditList() {
        return maintenanceAuditList;
    }

    public void setMaintenanceAuditList(List<OcrMaintenanceAudit> maintenanceAuditList) {
        this.maintenanceAuditList = maintenanceAuditList;
    }


}