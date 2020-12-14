package com.onnoa.shop.demo.upload.dto;

import java.util.Date;

public class OcrMaintenanceAudit {
    private Long id;

    private Integer monthId;

    private Long instId;

    private String existBarcode;

    private String isSpoof;

    private String isPaste;

    private String isOnlyTag;

    private String hasPort;

    private String isFake;

    private Long abilityId;

    private Long abilityLogId;

    private Integer status;

    private Date gmtCreate;

    private String manualExistBarcode;

    private String manualIsSpoof;

    private String manualIsPaste;

    private String manualIsOnlyTag;

    private String manualHasPort;

    private String manualIsFake;

    private String remark;

    private String resultMsg;

    private String logImage;

    private Date abilityTime;

    private Integer isPass;

    private String attrId;

    private String isValid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMonthId() {
        return monthId;
    }

    public void setMonthId(Integer monthId) {
        this.monthId = monthId;
    }

    public Long getInstId() {
        return instId;
    }

    public void setInstId(Long instId) {
        this.instId = instId;
    }

    public String getExistBarcode() {
        return existBarcode;
    }

    public void setExistBarcode(String existBarcode) {
        this.existBarcode = existBarcode == null ? null : existBarcode.trim();
    }

    public String getIsSpoof() {
        return isSpoof;
    }

    public void setIsSpoof(String isSpoof) {
        this.isSpoof = isSpoof == null ? null : isSpoof.trim();
    }

    public String getIsPaste() {
        return isPaste;
    }

    public void setIsPaste(String isPaste) {
        this.isPaste = isPaste == null ? null : isPaste.trim();
    }

    public String getIsOnlyTag() {
        return isOnlyTag;
    }

    public void setIsOnlyTag(String isOnlyTag) {
        this.isOnlyTag = isOnlyTag == null ? null : isOnlyTag.trim();
    }

    public String getHasPort() {
        return hasPort;
    }

    public void setHasPort(String hasPort) {
        this.hasPort = hasPort == null ? null : hasPort.trim();
    }

    public String getIsFake() {
        return isFake;
    }

    public void setIsFake(String isFake) {
        this.isFake = isFake == null ? null : isFake.trim();
    }

    public Long getAbilityId() {
        return abilityId;
    }

    public void setAbilityId(Long abilityId) {
        this.abilityId = abilityId;
    }

    public Long getAbilityLogId() {
        return abilityLogId;
    }

    public void setAbilityLogId(Long abilityLogId) {
        this.abilityLogId = abilityLogId;
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

    public String getManualExistBarcode() {
        return manualExistBarcode;
    }

    public void setManualExistBarcode(String manualExistBarcode) {
        this.manualExistBarcode = manualExistBarcode == null ? null : manualExistBarcode.trim();
    }

    public String getManualIsSpoof() {
        return manualIsSpoof;
    }

    public void setManualIsSpoof(String manualIsSpoof) {
        this.manualIsSpoof = manualIsSpoof == null ? null : manualIsSpoof.trim();
    }

    public String getManualIsPaste() {
        return manualIsPaste;
    }

    public void setManualIsPaste(String manualIsPaste) {
        this.manualIsPaste = manualIsPaste == null ? null : manualIsPaste.trim();
    }

    public String getManualIsOnlyTag() {
        return manualIsOnlyTag;
    }

    public void setManualIsOnlyTag(String manualIsOnlyTag) {
        this.manualIsOnlyTag = manualIsOnlyTag == null ? null : manualIsOnlyTag.trim();
    }

    public String getManualHasPort() {
        return manualHasPort;
    }

    public void setManualHasPort(String manualHasPort) {
        this.manualHasPort = manualHasPort == null ? null : manualHasPort.trim();
    }

    public String getManualIsFake() {
        return manualIsFake;
    }

    public void setManualIsFake(String manualIsFake) {
        this.manualIsFake = manualIsFake == null ? null : manualIsFake.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg == null ? null : resultMsg.trim();
    }

    public String getLogImage() {
        return logImage;
    }

    public void setLogImage(String logImage) {
        this.logImage = logImage;
    }

    public Date getAbilityTime() {
        return abilityTime;
    }

    public void setAbilityTime(Date abilityTime) {
        this.abilityTime = abilityTime;
    }

    public Integer getIsPass() {
        return isPass;
    }

    public void setIsPass(Integer isPass) {
        this.isPass = isPass;
    }

    public String getAttrId() {
        return attrId;
    }

    public void setAttrId(String attrId) {
        this.attrId = attrId;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }
}