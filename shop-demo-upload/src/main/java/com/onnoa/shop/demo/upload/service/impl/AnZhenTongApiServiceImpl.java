package com.onnoa.shop.demo.upload.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.onnoa.shop.common.utils.FileUtils;
import com.onnoa.shop.common.utils.GetBase64Utils;
import com.onnoa.shop.demo.upload.dto.OcsRmInterfaceResponse;
import com.onnoa.shop.demo.upload.service.AnZhenTongApiService;
import com.onnoa.shop.demo.upload.service.DcoosApiService;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.onnoa.shop.demo.upload.service.impl.OrderCutImageServiceImpl.RESULT_CODE;
import static com.onnoa.shop.demo.upload.service.impl.OrderCutImageServiceImpl.RESULT_CODE_FAIL;
import static com.onnoa.shop.demo.upload.service.impl.OrderCutImageServiceImpl.RESULT_CODE_SUCCESS;
import static com.onnoa.shop.demo.upload.service.impl.OrderCutImageServiceImpl.RESULT_DATA;
import static com.onnoa.shop.demo.upload.service.impl.OrderCutImageServiceImpl.RESULT_MSG;


@Service
public class AnZhenTongApiServiceImpl implements AnZhenTongApiService {
    private static final Logger logger = LoggerFactory.getLogger(AnZhenTongApiServiceImpl.class);

    @Autowired
    DcoosApiService dcoosApiService;
//    @Autowired
//    DcSystemConfService dcSystemConfService;

    /**
     * 安装通接口调用
     *
     * @param custOrderId 用户订单id
     * @return code为是否调用成功为1失败为-1、message提示信息, 报错为报错信息 或为接口返回信息、data 为图片base64列表（List<String>）
     */
    @Override
    public OcsRmInterfaceResponse<List<String>> getImageList(String custOrderId) {
        OcsRmInterfaceResponse response = new OcsRmInterfaceResponse();
        Map<String, Object> callbackResult = callAnZhenTong(custOrderId);
        response.setCode(-1);
        response.setMessage(MapUtils.getString(callbackResult, RESULT_MSG));
        if (RESULT_CODE_SUCCESS.equals(MapUtils.getString(callbackResult, RESULT_CODE))) {
            String url = MapUtils.getString(callbackResult, RESULT_DATA);
            int last = url.indexOf(":");
            url.replace("http://134.175.22.219",url.substring(0,last));
            try {
                List<String> list = getPhotosZip(url, custOrderId);
                response.setData(list);
                response.setCode(1);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                if (!StringUtils.isEmpty(response.getMessage())) {
                    response.setMessage(response.getMessage() + " \n " + getStackMsg(e));
                } else {
                    response.setMessage(getStackMsg(e));
                }
            }
        }
//        response.setMessage("{\"reuslt\":0,\"msg\":\"返回成功\",\"data\":{\"url\":\"http://IP:port/portal/\"}}");
//        List<String> list = new ArrayList<>();
//        response.setData(list);
        return response;
    }

    /**
     * 调用安真通的 获取工单照片信息接口
     *
     * @param custOrderId
     * @return
     */
    public Map callAnZhenTong(String custOrderId) {
        Map<String, Object> returnObj = new HashMap<>();
        try {
            String configParamsStr = "{\"basicUrl\":\"http://134.176.102.33:9080/api/openapi\", \"XAPPID\":\"8f2782be113374f3d9b9d49cdd050427\", \"XAPPKEY\":\"75776fb5f128928dfefec6697b285e1e\", \"clientId\":\"AI_APR\"}";//dcSystemConfService.getSystemConf(DCOOS_CRM_API_CONFIG).getDcValue();
            JSONObject method = JSON.parseObject("{\"method\":\"getPhotosZip/getPhotosZip/getPhotosZip\", \"hashKey\":\"!@##@!\"}");//JSON.parseObject(dcSystemConfService.getSystemConf(AN_ZHEN_TONG_API_METHOD).getDcValue());
            String strTimestamp = new Date(System.currentTimeMillis()).getTime() + "";
            String strHashCode = DigestUtils.md5Hex(custOrderId + strTimestamp + method.getString("hashKey"));

            Map<String, Object> params = new HashMap<>();
            Map inputMap = new HashMap();
            inputMap.put("orderId", custOrderId);
            inputMap.put("strTimestamp", strTimestamp);
            inputMap.put("strHashCode", strHashCode);
            params.put("params", inputMap);
            params.put("method", method.getString("method"));

            Map configParams = JSON.parseObject(configParamsStr, Map.class);
            Map resultMap = dcoosApiService.post(params, configParams);

            if (MapUtils.getBoolean(resultMap, "flag")) {
                JSONObject res = (JSONObject) resultMap.get("result");
                if ("0".equals(res.getString("result"))) {
                    returnObj.put(RESULT_CODE, RESULT_CODE_SUCCESS);
                    returnObj.put(RESULT_DATA, res.get("fileUrl"));
                } else {
                    returnObj.put(RESULT_CODE, RESULT_CODE_FAIL);
                }
                returnObj.put(RESULT_MSG, JSON.toJSONString(resultMap));
            } else {
                returnObj.put(RESULT_MSG, JSON.toJSONString(resultMap));
                returnObj.put(RESULT_CODE, RESULT_CODE_FAIL);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            returnObj.put(RESULT_MSG, "安真通接口 - 调用接口出错" + getStackMsg(e));
            returnObj.put(RESULT_CODE, RESULT_CODE_FAIL);
        }

        return returnObj;
    }

    public List<String> getPhotosZip(String url, String custOrderId) throws ZipException, IOException {
        List<String> list = new ArrayList<>();
        File file = null;
        try {
            file = downloadFromUrl(url, custOrderId);
            unZipFile(file);
            list = getImageBase64(file.getParentFile());
        } finally {
            // 删除处理后的zip及文件夹
            if (file != null) {
                File parentFile = file.getParentFile();
                FileUtils.deleteFolder(parentFile);
            }
        }
        return list;
    }

    public List<String> getImageBase64(File file) {
        List<String> list = new ArrayList<>();
        if (file == null && !file.exists() && file.isFile()) {
            return list;
        }
        File[] imageFileList = file.listFiles();
        if (imageFileList != null && imageFileList.length > 0) {
            for (File imageFile : imageFileList) {
                if (imageFile.getName().toUpperCase().endsWith(".JPG")) {
                    try {
                        list.add(GetBase64Utils.getBase64(imageFile));
                    } catch (Exception e) {
                    }
                }
            }
        }
        return list;
    }

    /**
     * 解压zip文件
     */
    public void unZipFile(File file) throws ZipException {
        if (file == null) {
            return;
        }
        ZipFile zipFile = new ZipFile(file);
        zipFile.extractAll(file.getParent());
    }

    /**
     * 通过url下载zip文件到新建目录
     */
    private File downloadFromUrl(String urlStr, String custOrderId) {
        String localPath = "localData";//dcSystemConfService.getSystemConf(LOCAL_FILE_PATH).getDcValue();
        String filePathStr = localPath.endsWith("/") ? localPath + custOrderId : localPath + "/" + custOrderId;
        File filePath = new File(filePathStr);
        File file = new File(filePathStr + "/" + custOrderId + ".zip");

        InputStream inputStream = null;
        try {
            // 新建文件目录
            if (filePath.exists() || filePath.isFile()) {
                FileUtils.deleteFolder(filePath);
            }
            FileUtils.createFilePath(filePath);

            // 下载zip文件
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //设置超时间为3秒
            conn.setConnectTimeout(30 * 1000);
            //防止屏蔽程序抓取而返回403错误
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

            //得到输入流
            inputStream = conn.getInputStream();
            if (inputStream == null) {
                throw new IOException();
            }
            org.apache.commons.io.FileUtils.copyInputStreamToFile(inputStream, file);
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
            throw new RuntimeException("URL下载文件报错", e);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
        return file;
    }

    private String getStackMsg(Exception e) {
        StringBuffer sb = new StringBuffer();
        StackTraceElement[] stackArray = e.getStackTrace();
        for (int i = 0; i < stackArray.length; i++) {
            StackTraceElement element = stackArray[i];
            sb.append(element.toString() + " \n ");
        }
        return sb.toString();
    }
}
