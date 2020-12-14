package com.onnoa.shop.demo.upload.service;


import com.onnoa.shop.demo.upload.dto.OcrMaintenanceAudit;
import com.onnoa.shop.demo.upload.dto.OcrMaintenanceOrder;

import java.util.List;
import java.util.Map;

public interface OcsFuBaoApiService {

    /**
     *  根据图片url获取图片base64集合(预留的现场接口)
     * @param attrUrlList
     * @return
     */
    List<String> getBase64ListForUrl(List<String> attrUrlList);


    /**
     *  AI图片质检结果回写服保(预留的现场接口 ocr_maintenance_order)
     *  mainSn(服保工单工单号)、subApplyNo(大订单号 ocr_maintenance_order)、orderId(小订单号 ocr_maintenance_order)
     *  checkResult(整体质检结果对应ocr_maintenance_order表的 pass_flag 字段(1:及格；0：不及格))、
     *  picId(图片唯一id =>ocr_maintenance_audit表的 attr_id)
     *  picRestult(图片质检结果  =>ocr_maintenance_audit表的 is_pass)
     *  turnShot(是否翻拍图片 =>ocr_maintenance_audit表的 is_spoof)
     *  barCodePaste(条形码未粘贴图片 => ocr_maintenance_audit表的 is_paste)
     *  isPort(可识别端口图片 => ocr_maintenance_audit表的 has_port)
     *  isBarCode(可识别条形码图片 => ocr_maintenance_audit表的 exist_barcode)
     *  invalidPic(无有效信息图片 => 无条形码、无可识别端口图片无效 由ocr_maintenance_audit表的 exist_barcode 与has_port来判断 1：是；0：否)
     *  portUsedInfo(端口占用情况 => 暂时无法获取，不传)
     *
     * @param auditInsertList
     * @param request
     * @return
     */
    Map<String, Object> writeBackPicResult(List<OcrMaintenanceAudit> auditInsertList, OcrMaintenanceOrder request);

}
