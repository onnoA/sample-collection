package com.onnoa.shop.demo.upload.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.onnoa.shop.demo.upload.dto.PortInfoResponse;
import com.onnoa.shop.demo.upload.dto.ResponseDto;
import com.onnoa.shop.demo.upload.service.OcsRmApiService;
import com.onnoa.shop.demo.upload.service.UploadService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Slf4j
public class UploadController {

    @Value("${fastdfs.base.url}")
    private String FASTDFS_BASE_URL;

    @Autowired
    private UploadService storageService;



    /**
     * 文件上传
     *
     * @param dropFile    Dropzone
     * @param editorFiles wangEditor
     * @return
     */
    @RequestMapping(value = "upload", method = RequestMethod.POST)
    public Map<String, Object> upload(MultipartFile dropFile, MultipartFile[] editorFiles) {
        Map<String, Object> result = new HashMap<>();

        // Dropzone 上传
        if (dropFile != null) {
            result.put("fileName", writeFile(dropFile));
        }

        // wangEditor 上传
        if (editorFiles != null && editorFiles.length > 0) {
            List<String> fileNames = new ArrayList<>();

            for (MultipartFile editorFile : editorFiles) {
                fileNames.add(writeFile(editorFile));
            }

            result.put("errno", 0);
            result.put("data", fileNames);
        }

        return result;
    }

    /**
     * 将图片写入指定目录
     *
     * @param multipartFile
     * @return 返回文件完整路径
     */
    private String writeFile(MultipartFile multipartFile) {
        // 获取文件后缀
        String oName = multipartFile.getOriginalFilename();
        String extName = oName.substring(oName.lastIndexOf(".") + 1);

        // 文件存放路径
        String url = null;
        try {
            String uploadUrl = storageService.upload(multipartFile.getBytes(), extName);
            url = FASTDFS_BASE_URL + uploadUrl;
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 返回文件完整路径
        return url;
    }

    public static void main(String[] args) {
        String jsonString = JSONObject.toJSONString("{\n" +
                "\t\"Return\": {\n" +
                "\t\t\"MESSAGE\": \"\",\n" +
                "\t\t\"RETURN_CODE\": \"0\",\n" +
                "\t\t\"PORT_LIST\": {\n" +
                "\t\t\t\"PORT_NO\": [\n" +
                "\t\t\t\t\"732YTQ.ZDKXM/GF001/OBD01/02\",\n" +
                "\t\t\t\t\"732YTQ.ZDKXM/GF001/OBD01/03\",\n" +
                "\t\t\t\t\"732YTQ.ZDKXM/GF001/OBD01/04\",\n" +
                "\t\t\t\t\"732YTQ.ZDKXM/GF001/OBD01/05\",\n" +
                "\t\t\t\t\"732YTQ.ZDKXM/GF001/OBD01/06\",\n" +
                "\t\t\t\t\"732YTQ.ZDKXM/GF001/OBD01/07\",\n" +
                "\t\t\t\t\"732YTQ.ZDKXM/GF001/OBD01/08\",\n" +
                "\t\t\t\t\"732YTQ.ZDKXM/GF001/OBD01/00\"\n" +
                "\t\t\t],\n" +
                "\t\t\t\"PORT_NUM\": [\n" +
                "\t\t\t\t\"2\",\n" +
                "\t\t\t\t\"3\",\n" +
                "\t\t\t\t\"4\",\n" +
                "\t\t\t\t\"5\",\n" +
                "\t\t\t\t\"6\",\n" +
                "\t\t\t\t\"7\",\n" +
                "\t\t\t\t\"8\",\n" +
                "\t\t\t\t\"0\"\n" +
                "\t\t\t]\n" +
                "\t\t}\n" +
                "\t},\n" +
                "\t\"IntfCode\": \" qryCodeBarPortInfo\"\n" +
                "}");
//        XMLResponseDto.Response response = new XMLResponseDto.Response();
//        BeanUtils.copyToBean(o, response);
//        log.info("返回对象：{}",response);

        ResponseDto response1 = JSON.parseObject(jsonString, ResponseDto.class);
        log.info("json转实体后输出:{}", response1);

    }


}