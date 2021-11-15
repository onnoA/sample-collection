package com.onnoa.shop.demo.upload.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.onnoa.shop.demo.upload.config.FileProperties;
import com.onnoa.shop.demo.upload.exception.FileException;

@Service
public class FileService {

    private final Path fileStorageLocation; // 文件在本地存储的地址

    @Autowired
    public FileService(FileProperties fileProperties) {
        this.fileStorageLocation = Paths.get(fileProperties.getUploadDir()).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        }
        catch (Exception ex) {
            throw FileException.FILE_UPLOAD_FAILED.format();
        }
    }

    /**
     * 存储文件到系统
     *
     * @param file 文件
     * @return 文件名
     */
    public String storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        }
        catch (IOException ex) {
            throw FileException.FILE_UPLOAD_FAILED;
        }
    }

    /**
     * 加载文件
     * 
     * @param fileName 文件名
     * @return 文件
     */
    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            }
            else {
                throw new FileException("File not found " + fileName);
            }
        }
        catch (MalformedURLException ex) {
            throw FileException.FILE_NOT_EXIST.format(fileName);
        }
    }
}
