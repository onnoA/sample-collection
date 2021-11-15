package com.onnoa.shop.demo.upload.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.onnoa.shop.demo.upload.dto.OcrMaintenanceAudit;
import com.onnoa.shop.demo.upload.dto.OcrMaintenanceOrder;
import com.onnoa.shop.demo.upload.service.AbilityOpenApiService;
import com.onnoa.shop.demo.upload.service.OcsFuBaoApiService;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class OcsFuBaoApiServiceImpl implements OcsFuBaoApiService {

    @Autowired
    private AbilityOpenApiService abilityOpenApiService;

    public static final Integer STATUS_0 = 0;
    public static final Integer STATUS_1 = 1;

    public static final String YES_NUM = "1";
    public static final String NO_NUM = "0";

    @Override
    public List<String> getBase64ListForUrl(List<String> attrUrlList) {
        ArrayList<String> base64List = Lists.newArrayList();
//        attrUrlList.parallelStream().forEach(url -> {
//            InputStream stream = fileSystemService.downloadFile(url);
//            String base64 = null;
//            try {
//                base64 = ImageUtil.getBase64(stream);
//            } catch (IOException e) {
//                throw new OcrRunTimeException(ErrorCodeEnum.FTP_DOWNLOAD_ERROR, "获取图片失败", e);
//            }
//            base64List.add(base64);
//        });
        return base64List;
    }

    @Override
    public Map<String, Object> writeBackPicResult(List<OcrMaintenanceAudit> auditInsertList, OcrMaintenanceOrder request) {
        HashMap<String, Object> params = Maps.newHashMap();
        String url = "http://134.176.102.33:8081/api/rest";
        String configStr = "{\"method\":\"ord.operwork.aiPicResult\", \"access_token\":\"OTBkMjJiM2Y0NmVjNzdmOTc0NWFkZWMyZGU1MThhMmI=\", \"reqSystem\":\"YWWB\", \"reqPwd\":\"HNYWWB\",\"status\":\"0\" }";
        Map configParams = JSON.parseObject(configStr, Map.class);
        String method = MapUtils.getString(configParams, "method");
        Map<String, Object> contentMap = buildWithContent(request, auditInsertList, configParams);
        params.put("content", contentMap);
        params.put("status", MapUtils.getString(configParams, "status"));
        params.put("access_token", MapUtils.getString(configParams, "access_token"));
        params.put("method", method);
        return abilityOpenApiService.postAbility(params, url);
    }

    private Map<String, Object> buildWithContent(OcrMaintenanceOrder request, List<OcrMaintenanceAudit> auditInsertList, Map configParams) {
        Map<String, Object> contentMap = new HashMap<>();
        contentMap.put("reqSystem", MapUtils.getString(configParams, "reqSystem"));
        contentMap.put("reqPwd", MapUtils.getString(configParams, "reqPwd"));
        contentMap.put("mainSn", request.getMainSn());
        contentMap.put("subApplyNo", request.getSubApplyNo());
        contentMap.put("orderId", request.getOrderId());
        contentMap.put("checkResult", STATUS_0.equals(request.getPassFlag()) ? "N" : "Y");
        List<Map<String, String>> picResultList = Lists.newArrayList();
        auditInsertList.stream().forEach(audit -> {
            Map<String, String> map = Maps.newHashMap();
            map.put("PicResult", STATUS_1.equals(audit.getIsPass()) ? "Y" : "N");
            map.put("picId", audit.getAttrId());
            map.put("turnShot", YES_NUM.equals(audit.getIsSpoof()) ? "Y" : "N");
            map.put("barCodePaste", YES_NUM.equals(audit.getIsPaste()) ? "Y" : "N");
            map.put("isPort", YES_NUM.equals(audit.getHasPort()) ? "Y" : "N");
            map.put("isBarCode", YES_NUM.equals(audit.getExistBarcode()) ? "Y" : "N");
            map.put("invalidPic", (NO_NUM.equals(audit.getExistBarcode()) && NO_NUM.equals(audit.getHasPort())) ? "Y" : "N");
            //map.put("portUsedInfo",null);
            picResultList.add(map);
        });
        contentMap.put("PicResultList", picResultList);
        return contentMap;
    }
}
