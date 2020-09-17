package com.onnoa.shop.demo.upload.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.onnoa.shop.common.component.TestComponent;
import com.onnoa.shop.common.result.ResultBean;
import com.onnoa.shop.demo.upload.dto.UploadResultDto;
import com.onnoa.shop.demo.upload.service.FileService;

@RestController
@RequestMapping(value = "/file")
public class FileUploadController {

    @Autowired
    private FileService fileService;

    @PostMapping("/uploadFile")
    public ResultBean uploadFile(@RequestParam("file") MultipartFile file, @RequestParam String username) {
        System.out.println(username);
        String fileName = fileService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
            .path(fileName).toUriString();
        UploadResultDto resultDto = new UploadResultDto();
        resultDto.setFileName(fileName);
        resultDto.setFileDownloadUri(fileDownloadUri);
        return ResultBean.success(resultDto);
    }

    @PostMapping("/uploadMultipleFiles")
    public List<ResultBean> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        // return Arrays.stream(files).map(this::uploadFile).collect(Collectors.toList());
        return null;
    }

}
