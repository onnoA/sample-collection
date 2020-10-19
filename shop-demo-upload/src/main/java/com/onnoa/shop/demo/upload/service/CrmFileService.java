package com.onnoa.shop.demo.upload.service;


public interface CrmFileService {

    String upload(byte[] uploadBytes, String fileType) ;

    byte[] download(String fileId);

}
