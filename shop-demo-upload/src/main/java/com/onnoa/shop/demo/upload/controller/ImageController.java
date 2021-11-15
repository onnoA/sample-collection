package com.onnoa.shop.demo.upload.controller;

import com.alibaba.fastjson.JSON;
import com.onnoa.shop.common.result.ResultBean;
import com.onnoa.shop.common.utils.PdfUtil;
import com.onnoa.shop.demo.upload.exception.FileException;
import com.onnoa.shop.demo.upload.service.CrmFileService;
import org.apache.commons.io.IOUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping(value = "/image")
@RestController
public class ImageController {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ImageController.class);


    @Autowired
    CrmFileService crmFileService;
    @GetMapping("/getFile1")
    public void getFile(@Param("fileId") String fileId, @Param("type") String type, HttpServletResponse response) {
        InputStream in = null;
        OutputStream outputStream = null;
        try {
            byte[] tempBytes = crmFileService.download(fileId);
            in = new ByteArrayInputStream(tempBytes);
            // 输出图片流
            // 设置返回类型
            if ("1".equals(type)) {
                response.setContentType("application/pdf");
            }
            else {
                response.setContentType("image/jpeg");
            }
            outputStream = response.getOutputStream();
            byte[] arr = new byte[1024]; // 该数组用来存入从输入文件中读取到的数据
            int len; // 变量len用来存储每次读取数据后的返回值
            while ((len = in.read(arr)) != -1) {
                outputStream.write(arr, 0, len);
            }
            outputStream.flush();
            outputStream.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(outputStream);
        }
    }

    /**
     * 从crm获取文件流
     *
     * @param fileId
     * @param response
     * @throws Exception
     */
    @GetMapping("/getCrmFile")
    public void getCrmFile(@Param("fileId") String fileId, HttpServletResponse response) {
        if (StringUtils.isEmpty(fileId)) {
            return;
        }
        OutputStream outputStream = null;
        BufferedInputStream bin = null;
        FileOutputStream fout = null;
        BufferedOutputStream bout = null;
        try {
            byte[] buf = crmFileService.download(fileId);
            ByteArrayInputStream bais = new ByteArrayInputStream(buf);
            bin = new BufferedInputStream(bais);
            File file = new File("D:\\test2.pdf");
            fout = new FileOutputStream(file);
            bout = new BufferedOutputStream(fout);
            byte[] buffers = new byte[1024];
            int len = bin.read(buffers);
            while (len != -1) {
                bout.write(buffers, 0, len);
                len = bin.read(buffers);
            }
             //刷新此输出流并强制写出所有缓冲的输出字节，必须这行代码，否则有可能有问题
            bout.flush();
            List<String> baseList = new ArrayList<>();
            InputStream is = new ByteArrayInputStream(buf);
            List<Object> imageList = PdfUtil.readImage(is, true);
            if (imageList != null && !imageList.isEmpty()) {
                for (Object image : imageList) {
                    baseList.add(image.toString());
                }
            }
            //logger.info(JSON.toJSONString(baseList));
            outputStream = response.getOutputStream();
            outputStream.write(buf, 0, buf.length);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            logger.error("输出文件流出错", e);
        } finally {
            IOUtils.closeQuietly(outputStream);
            try {
                bin.close();
                fout.close();
                bout.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return;
    }

    /**
     * 上传文件到crm
     *
     * @param file
     * @throws Exception
     */
    @RequestMapping(path = "/upLoadCrmFile", method = RequestMethod.POST)
    public ResultBean upLoadCrmFile(@RequestParam("fileType") String fileType, @RequestParam(value = "file", required = false) MultipartFile file) {
        byte[] bytes = null;
        String fileId = "";
        try {
            bytes = file.getBytes();
            fileId = crmFileService.upload(bytes, fileType);

        } catch (IOException e) {
            logger.info(e.getMessage(), e);
            throw FileException.FILE_UPLOAD_FAILED.format(file.getOriginalFilename());
        }
        return ResultBean.success(fileId);
    }


}