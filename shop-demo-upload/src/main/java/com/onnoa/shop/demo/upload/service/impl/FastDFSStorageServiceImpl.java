package com.onnoa.shop.demo.upload.service.impl;

import com.onnoa.shop.common.exception.ServiceException;
import com.onnoa.shop.demo.upload.service.UploadService;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerGroup;
import org.csource.fastdfs.TrackerServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FastDFSStorageServiceImpl implements UploadService, InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(FastDFSStorageServiceImpl.class);

    @Value("${storage.fastdfs.tracker_server}")
    private String trackerServer;

    private TrackerClient trackerClient;

    @Override
    public String upload(byte[] data, String extName) {
        TrackerServer trackerServer = null;
        StorageServer storageServer = null;
        try {
            NameValuePair[] meta_list = null; // new NameValuePair[0];
            trackerServer = trackerClient.getTrackerServer();//.getConnection();
            if (trackerServer == null) {
                logger.error("getConnection return null");
                throw ServiceException.FILE_TO_GET_CONNECTION.format("fastdfs");
            }
            storageServer = trackerClient.getStoreStorage(trackerServer);
            StorageClient1 storageClient1 = new StorageClient1(trackerServer, storageServer);
            return storageClient1.upload_file1(data, extName, meta_list);
        } catch (Exception ex) {
            logger.error("Upload fail , exception == > ", ex);
            throw ServiceException.FILE_UPLOAD_FAILED.format(ex.getMessage());
        } finally {
            if (storageServer != null) {
                try {
                    storageServer.getConnection().close();
                } catch (Exception e) {
                    throw ServiceException.DATA_INVALID.format("关闭流失败。");
                }
            }
            if (trackerServer != null) {
                try {
                    trackerServer.getConnection().close();
                } catch (Exception e) {
                    throw ServiceException.DATA_INVALID.format("关闭流失败。");
                }
            }
        }
    }

    @Override
    public int delete(String fileId) {
        TrackerServer trackerServer = null;
        StorageServer storageServer = null;
        StorageClient1 storageClient1 = null;
        int index = fileId.indexOf('/');
        String groupName = fileId.substring(0, index);
        try {
            trackerServer = trackerClient.getTrackerServer();//.getConnection();
            if (trackerServer == null) {
                logger.error("getConnection return null");
            }
            storageServer = trackerClient.getStoreStorage(trackerServer, groupName);
            storageClient1 = new StorageClient1(trackerServer, storageServer);
            int result = storageClient1.delete_file1(fileId);
            return result;
        } catch (Exception ex) {
            logger.error("Delete fail", ex);
            return 1;
        } finally {
            if (storageServer != null) {
                try {
                    storageServer.getConnection().close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (MyException e) {
                    e.printStackTrace();
                }
            }
            if (trackerServer != null) {
                try {
                    trackerServer.getConnection().close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (MyException e) {
                    e.printStackTrace();
                }
            }
            storageClient1 = null;
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        File confFile = File.createTempFile("fastdfs", ".conf");
        PrintWriter confWriter = new PrintWriter(new FileWriter(confFile));
        confWriter.println("tracker_server=" + trackerServer);
        confWriter.close();
        ClientGlobal.init(confFile.getAbsolutePath());
        confFile.delete();
        TrackerGroup trackerGroup = ClientGlobal.g_tracker_group;
        trackerClient = new TrackerClient(trackerGroup);

        logger.info("Init FastDFS with tracker_server : {}", trackerServer);
    }
}
