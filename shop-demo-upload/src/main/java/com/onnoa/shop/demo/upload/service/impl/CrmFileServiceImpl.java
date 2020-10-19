package com.onnoa.shop.demo.upload.service.impl;


import com.onnoa.shop.demo.upload.service.CrmFileService;
import com.ztesoft.eda.bdp.bean.TenantParams;
import com.ztesoft.eda.bdp.request.DownloadFileParam;
import com.ztesoft.eda.bdp.request.FilesParam;
import com.ztesoft.eda.bdp.request.InfoParam;
import com.ztesoft.eda.bdp.request.UploadFilesParam;
import com.ztesoft.eda.bdp.request.VarThreadLocal;
import com.ztesoft.eda.bdp.response.DownloadFileResponse;
import com.ztesoft.eda.bdp.response.UploadFilesResponse;
import com.ztesoft.eda.bdp.server.BDPServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Slf4j
public class CrmFileServiceImpl implements CrmFileService {


    @Value("${crm.bdp.server.ip}")
    private String ip;
    @Value("${crm.bdp.server.port}")
    private String port;
    @Value("${crm.bdp.server.contextPath}")
    private String contextPath;
    @Value("${crm.bdp.server.schema}")
    private String schema;

    @Value("${crm.bdp.tenantCode}")
    private String tenantCode;
    @Value("${crm.bdp.tenantPasswd}")
    private String tenantPasswd;

    /**
     * 上传文件
     *
     * @param uploadBytes 上传的字节数组
     * @param fileType    文件类型
     * @return
     * @throws Exception
     */
    @Override
    public String upload(byte[] uploadBytes, String fileType) {
        String fileId = null;
        UploadFilesParam param = new UploadFilesParam();

        List<FilesParam> files = new ArrayList();
        FilesParam e = new FilesParam();
        e.setContent(uploadBytes);
        String fileName = System.currentTimeMillis() + String.valueOf(Math.random()).substring(2, 5);
        e.setFileName(fileName);
        e.setFileType(fileType);

        files.add(e);
        param.setFiles(files);

        InfoParam info = new InfoParam();
        info.setAuthor("feng");
        info.setBelogDepart("crm");
        info.setCreatedTime(System.currentTimeMillis());
        info.setIndex(false);
        info.setSysCode("test");
        param.setInfo(info);

        Map<String, String> ext = new HashMap();
        ext.put("testK", "testV");
        param.setExt(ext);
        try {
            //如果要使用postman测试上传接口，放开下面的注释
            VarThreadLocal.setTenantParam(new TenantParams(tenantCode, tenantPasswd));
            BDPServer server = new BDPServer(ip, port, contextPath, schema);
            UploadFilesResponse uploadResponse = server.uploadFiles(param);
            if (uploadResponse != null && uploadResponse.getDoc() != null) {
                fileId = uploadResponse.getDoc().getFileIds().get(0);
            }
        } catch (Exception e1) {
            log.info(e1.getMessage(), e1);
            throw new RuntimeException("crm文件上传失败");
        }
        return fileId;
    }


    /**
     * 文件下载服务
     *
     * @param fileId 文件唯一标识
     * @return
     * @throws Exception
     */
    @Override
    public byte[] download(String fileId) {
        byte[] tempBytes = null;
        DownloadFileParam downloadFileParam = new DownloadFileParam();
        downloadFileParam.setFileId(fileId);
        downloadFileParam.setPriv(" ");
        downloadFileParam.setSysCode("test");
        try {
            BDPServer server = new BDPServer(ip, port, contextPath, schema);
            VarThreadLocal.setTenantParam(new TenantParams(tenantCode, tenantPasswd));
            DownloadFileResponse downloadFileResponse = server.downloadFile(downloadFileParam);
            if (downloadFileResponse != null && downloadFileResponse.getDoc() != null) {
                tempBytes = downloadFileResponse.getDoc().getContent();
            }
        } catch (Exception e) {
            log.info(e.getMessage(), e);
            throw new RuntimeException( "crm文件下载失败");
        }
        return tempBytes;
    }
}

