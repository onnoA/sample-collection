//package com.onnoa.shop.common.utils;
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.InetSocketAddress;
//
//public class FastFDSUtil {
//    private static final Logger LOGGER = LoggerFactory.getLogger(FastFDSUtil.class);
//
//    private static Gson gson = new GsonBuilder().create();
//
//    /**
//     * @param file
//     * @param uploadFileName
//     * @return
//     * @throws Exception
//     * @Description 上传文件
//     * @Author 陈浩良
//     * @date 2018年5月7日 下午2:03:41
//     */
//    public String uploadFile(FastFDSConfig FastFDSConfig, File file, String uploadFileName) {
//        if (file == null) {
//            return null;
//        }
//        //byte[] fileBuff = FileUtils.readFileToByteArray(file);
//        long fileLength = file.length();
//        InputStream inputStream = null;
//        byte[] fileBuff = null;
//
//        try {
//            inputStream = new FileInputStream(file);
//            fileBuff = getFileBuffer(inputStream, fileLength);
//        }
//        catch (Exception e) {
//            LOGGER.error("文件上传失败", e);
//            return null;
//        }
//        finally {
//            if (null != inputStream) {
//                try {
//                    inputStream.close();
//                }
//                catch (IOException e) {
//                    LOGGER.error("文件流关闭失败", e);
//                }
//            }
//        }
//        String result = "";
//        try {
//            result = send(FastFDSConfig, uploadFileName, fileBuff);
//        }
//        catch (Exception ex) {
//            LOGGER.error("fastDFS发送失败", ex);
//        }
//        finally {
//            return result;
//        }
//    }
//
//    /**
//     * @param file
//     * @param uploadFileName
//     * @return
//     */
//    public String uploadFile(MultipartFile file, String uploadFileName, String fastdfs) {
//
//        if (file == null) {
//            return null;
//        }
//        long fileLength = file.getSize();
//        InputStream inputStream = null;
//        byte[] fileBuff = null;
//
//        try {
//            inputStream = file.getInputStream();
//            fileBuff = getFileBuffer(inputStream, fileLength);
//            //fileBuff = FileCopyUtils.copyToByteArray(inputStream);
//        }
//        catch (Exception e) {
//            LOGGER.error("文件上传失败", e);
//            return null;
//        }
//        finally {
//            if (null != inputStream) {
//                try {
//                    inputStream.close();
//                }
//                catch (IOException e) {
//                    LOGGER.error("文件流关闭失败", e);
//                }
//            }
//        }
//        return send(uploadFileName, fileBuff, fastdfs);
//    }
//    /**
//     * @param uploadFileName
//     * @param fileBuff
//     * @return
//     */
//    public String send(String uploadFileName, byte[] fileBuff, String fastdfs) {
//        if (fileBuff == null) {
//            return null;
//        }
//
//        String fileExtName;
//        if (uploadFileName.contains(".")) {
//            fileExtName = uploadFileName.substring(uploadFileName.lastIndexOf(".") + 1);
//        }
//        else {
//            LOGGER.info("文件命名不合法！");
//            return null;
//        }
//
//        FastFDSConfig FastFDSConfig = gson.fromJson(fastdfs, FastFDSConfig.class);
//        String serverIP = FastFDSConfig.getServerIP();
//        int portUpload = Integer.parseInt(FastFDSConfig.getPortUpload());
//
//        LOGGER.debug("fastDFS-->connect--fastDFS连接及上传开始>>>>>>host={}>>>port={}", FastFDSConfig.getServerIP(), FastFDSConfig.getPortUpload());
//
//        initClientGlobal(serverIP, portUpload);
//
//        try {
//            TrackerClient tracker = new TrackerClient();
//            TrackerServer trackerServer = tracker.getConnection();
//            // StorageServer storageServer = tracker.getStoreStorage(trackerServer);
//            StorageServer storageServer = null;
//            StorageClient1 client = new StorageClient1(trackerServer, storageServer);
//            String upPath = null;
//            upPath = client.upload_file1(fileBuff, fileExtName, null);
//            if ((upPath.indexOf("group1") > -1) && !"1".equals(FastFDSConfig.getHasGroup1())) {
//                upPath = upPath.substring(upPath.indexOf("/") + 1);
////                upPath = upPath.replace("group1/M00", "M00");
//            }
//
//
//            trackerServer.close();
//
//            String serverOutIP = FastFDSConfig.getServerOutIP();
//            int portDown = Integer.parseInt(FastFDSConfig.getPortDown());
//            if (StringUtils.isNotEmpty(serverOutIP)) {
//                upPath = "http://" + serverOutIP + ":" + portDown + "/" + upPath;
//            }
//            return upPath;
//        }
//        catch (Exception e) {
//            LOGGER.error("文件上传失败", e);
//            return null;
//        }
//    }
//
//    /**
//     * @param file
//     * @param uploadFileName
//     * @return
//     * @throws Exception
//     * @Description 上传文件
//     * @Author 陈浩良
//     * @date 2018年5月7日 下午7:20:51
//     */
//    public String uploadFile(FastFDSConfig FastFDSConfig, MultipartFile file, String uploadFileName) {
//
//        if (file == null) {
//            return null;
//        }
//        long fileLength = file.getSize();
//        InputStream inputStream = null;
//        byte[] fileBuff = null;
//
//        try {
//            inputStream = file.getInputStream();
//            fileBuff = getFileBuffer(inputStream, fileLength);
//            //fileBuff = FileCopyUtils.copyToByteArray(inputStream);
//        }
//        catch (Exception e) {
//            LOGGER.error("文件上传失败", e);
//            return null;
//        }
//        finally {
//            if (null != inputStream) {
//                try {
//                    inputStream.close();
//                }
//                catch (IOException e) {
//                    LOGGER.error("文件流关闭失败", e);
//                }
//            }
//        }
//        String result = "";
//        try {
//            result = send(FastFDSConfig, uploadFileName, fileBuff);
//        }
//        catch (Exception ex) {
//            LOGGER.error("fastDFS发送失败", ex);
//        }
//        finally {
//            return result;
//        }
//    }
//
//    /**
//     * @param uploadFileName
//     * @param fileBuff
//     * @return
//     * @throws Exception
//     * @Description 上传方法体
//     * @Author 陈浩良
//     * @date 2018年5月7日 下午7:20:27
//     */
//    public String send(FastFDSConfig FastFDSConfig, String uploadFileName, byte[] fileBuff) {
//        if (fileBuff == null) {
//            return null;
//        }
//
//        String fileExtName = "";
//        if (uploadFileName.contains(".")) {
//            fileExtName = uploadFileName.substring(uploadFileName.lastIndexOf(".") + 1);
//        }
//        else {
//            LOGGER.info("文件命名不合法！");
//            return null;
//        }
//
//        String serverIP = FastFDSConfig.getServerIP();
//        int portUpload = Integer.parseInt(FastFDSConfig.getPortUpload());
//
//        initClientGlobal(serverIP, portUpload, FastFDSConfig.getSecretKey());
//
//        try {
//            TrackerClient tracker = new TrackerClient();
//            TrackerServer trackerServer = tracker.getConnection();
//            StorageClient1 client = getClient(tracker, trackerServer);
//            String upPath = null;
//            upPath = client.upload_file1(fileBuff, fileExtName, null);
//            upPath = subGroup1String(upPath, FastFDSConfig);
//
//            trackerServer.close();
//
//            String serverOutIP = FastFDSConfig.getServerOutIP();
//            int portDown = Integer.parseInt(FastFDSConfig.getPortDown());
//            upPath = genServerOutPath(upPath, serverOutIP, portDown);
//            return upPath;
//        }
//        catch (Exception e) {
//            LOGGER.error("文件上传失败", e);
//            return null;
//        }
//    }
//
//    private StorageClient1 getClient(TrackerClient tracker, TrackerServer trackerServer) throws IOException {
//        StorageServer storageServer = tracker.getStoreStorage(trackerServer);
//        if (storageServer == null) {
//            throw new IllegalStateException("getStoreStorage return null");
//        }
//        return new StorageClient1(trackerServer, storageServer);
//    }
//
//    private String subGroup1String(String upPath, FastFDSConfig FastFDSConfig) {
//        if ((upPath.indexOf("group1") > -1) && !"1".equals(FastFDSConfig.getHasGroup1())) {
//            upPath = upPath.substring(upPath.indexOf("/") + 1);
////                upPath = upPath.replace("group1/M00", "M00");
//        }
//        return upPath;
//    }
//
//    private String genServerOutPath(String upPath, String serverOutIP, int portDown) {
//        if (!StringUtils.isEmpty(serverOutIP)) {
//            upPath = "http://" + serverOutIP + ":" + portDown + "/" + upPath;
//        }
//        return upPath;
//    }
//
//    /**
//     * @param path
//     * @return
//     */
//    public byte[] downloadFile(String path, String fastdfs) throws IOException {
//        if (StringUtils.isEmpty(path)) {
//            return null;
//        }
//        FastFDSConfig FastFDSConfig = gson.fromJson(fastdfs, FastFDSConfig.class);
//
//        if ((path.indexOf("group1") < 0) && !"1".equals(FastFDSConfig.getHasGroup1())) {
//            path = path.replace("/M00", "/group1/M00");
//        }
//        // 读取FastDfs配置信息
//        String serverOutIP = FastFDSConfig.getServerOutIP();
//        int portDown = Integer.parseInt(FastFDSConfig.getPortDown());
//        int portUpload = Integer.parseInt(FastFDSConfig.getPortUpload());
//
//        LOGGER.debug("fastDFS-->connect--fastDFS连接及下载开始>>>>>>host={}>>>port={}", FastFDSConfig.getServerIP(), FastFDSConfig.getPortUpload());
//
//        String head = "http://" + serverOutIP + ":" + portDown + "/";
//        path = path.replace(head, "");
//        String group = path.substring(0, path.indexOf("/"));
//        path = path.substring(path.indexOf("/") + 1);
//
//        // 初始化，这里注意不能传递下载的端口，因为fastdfs服务配置文件已做映射
//        initClientGlobal(FastFDSConfig.getServerIP(), portUpload);
//        TrackerClient tracker = new TrackerClient();
//        TrackerServer trackerServer = tracker.getConnection();
//        StorageServer storageServer = tracker.getStoreStorage(trackerServer);
//        StorageClient client = new StorageClient(trackerServer, storageServer);
//        byte[] fileByte = new byte[0];
//        try {
//            fileByte = client.download_file(group, path);
//        }
//        catch (Exception e) {
//            LOGGER.error("文件下载失败", e);
//        }
//        trackerServer.close();
//        return fileByte;
//
//    }
//
//    /**
//     * @param path
//     * @return
//     * @throws Exception
//     * @Description 下载文件
//     * @Author 陈浩良
//     * @date 2018年5月7日 下午3:06:46
//     */
//    public byte[] downloadFile(FastFDSConfig FastFDSConfig, String path) {
//        LOGGER.info("downloadFile start...path:{}", path);
//        if (StringUtils.isEmpty(path)) {
//            return null;
//        }
//
//        if ((path.indexOf("group1") < 0) && !"1".equals(FastFDSConfig.getHasGroup1())) {
//            path = path.replace("/M00", "/group1/M00");
//        }
//        // 读取FastDfs配置信息
//        String serverOutIP = FastFDSConfig.getServerOutIP();
//        int portDown = Integer.parseInt(FastFDSConfig.getPortDown());
//        int portUpload = Integer.parseInt(FastFDSConfig.getPortUpload());
//
//        try {
//            String head = "http://" + serverOutIP + ":" + portDown + "/";
//            path = path.replace(head, "");
//            String group = path.substring(0, path.indexOf("/"));
//            path = path.substring(path.indexOf("/") + 1);
//
//            // 初始化，这里注意不能传递下载的端口，因为fastdfs服务配置文件已做映射
//            initClientGlobal(FastFDSConfig.getServerIP(), portUpload, FastFDSConfig.getSecretKey());
//            TrackerClient tracker = new TrackerClient();
//            TrackerServer trackerServer = tracker.getConnection();
//            StorageServer storageServer = tracker.getStoreStorage(trackerServer);
//            LOGGER.info("trackerServer:{}***storageServer:{}", trackerServer, storageServer);
//            StorageClient client = new StorageClient(trackerServer, storageServer);
//            LOGGER.info("group:{}, path:{}", group, path);
//            byte[] fileByte = client.download_file(group, path);
//            if(null == fileByte) {
//                fileByte = client.download_file(group, path);
//            }
//            trackerServer.close();
//            if (fileByte == null) {
//                LOGGER.info("fileByte is null");
//            }
//            else {
//                LOGGER.info("******fileByte size:{}*******", fileByte.length);
//
//            }
//            return fileByte;
//        }
//        catch (Exception ex) {
//            LOGGER.error("文件下载失败", ex);
//            return null;
//        }
//    }
//
//    public static void main(String[] args) {
//        String path = "http://10.22.22.1:8080/a/b/c";
//
//    }
//
//    /**
//     * @param serverIP
//     * @param port
//     * @Description 初始化ClientGlobal
//     * @Author 陈浩良
//     * @date 2018年5月7日 下午2:56:01
//     */
//    private void initClientGlobal(String serverIP, int port, String secretKey) {
//        String[] serverIpArr = serverIP.split(",");
//
//        InetSocketAddress[] trackerServers = null;
//        if (serverIpArr != null && serverIpArr.length > 1) {
//            trackerServers = new InetSocketAddress[serverIpArr.length];
//            int i = 0;
//            for (String subServerIp : serverIpArr) {
//                trackerServers[i] = new InetSocketAddress(subServerIp.trim(), port);
//                i++;
//            }
//        }
//        else {
//            trackerServers = new InetSocketAddress[1];
//            trackerServers[0] = new InetSocketAddress(serverIP.trim(), port);
//        }
////        InetSocketAddress[] trackerServers = new InetSocketAddress[2];
////        trackerServers[0] = new InetSocketAddress("172.23.8.2", 22122);
////        trackerServers[1] = new InetSocketAddress("172.23.8.3", 22122);
//        ClientGlobal.setG_tracker_group(new TrackerGroup(trackerServers));
//        ClientGlobal.setG_connect_timeout(20000);
//        ClientGlobal.setG_network_timeout(30000);
//        ClientGlobal.setG_anti_steal_token(false);
//        ClientGlobal.setG_charset("ISO-8859-1");
//        if (StringUtils.isEmpty(secretKey)) {
//            ClientGlobal.setG_secret_key(null);
//        }
//        else {
//            ClientGlobal.setG_secret_key(secretKey);
//        }
//    }
//
//
//    /**
//     * @param serverIP
//     * @param port
//     * @Description 初始化ClientGlobal
//     * @Author 陈浩良
//     * @date 2018年5月7日 下午2:56:01
//     */
//    private void initClientGlobal(String serverIP, int port) {
//        String[] serverIpArr = serverIP.split(",");
//
//        InetSocketAddress[] trackerServers = null;
//        if (serverIpArr != null && serverIpArr.length > 1) {
//            trackerServers = new InetSocketAddress[serverIpArr.length];
//            int i = 0;
//            for (String subServerIp: serverIpArr) {
//                trackerServers[i] = new InetSocketAddress(subServerIp.trim(), port);
//                i++;
//            }
//        }
//        else {
//            trackerServers = new InetSocketAddress[1];
//            trackerServers[0] = new InetSocketAddress(serverIP.trim(), port);
//        }
//        ClientGlobal.setG_tracker_group(new TrackerGroup(trackerServers));
//        ClientGlobal.setG_connect_timeout(20000);
//        ClientGlobal.setG_network_timeout(30000);
//        ClientGlobal.setG_anti_steal_token(false);
//        ClientGlobal.setG_charset("ISO-8859-1");
//        ClientGlobal.setG_secret_key(null);
//    }
//
//    /**
//     * @param inStream
//     * @param fileLength
//     * @return
//     * @throws IOException
//     * @Description 读取文件流，返回字节数组  ,大文件处理有问题(byte[] fileBuffer = new byte[(int) fileLength])
//     * @Author 陈浩良
//     * @date 2018年5月7日 下午2:36:26
//     */
//    private byte[] getFileBuffer(InputStream inStream, long fileLength) throws IOException {
//
//        byte[] buffer = new byte[256 * 1024];
//        byte[] fileBuffer = new byte[(int) fileLength];
//        int count = 0;
//        int length = 0;
//
//        while ((length = inStream.read(buffer)) != -1) {
//            for (int i = 0; i < length; ++i) {
//                fileBuffer[count + i] = buffer[i];
//            }
//            count += length;
//        }
//        return fileBuffer;
//    }
//    public Boolean deleteFile(FastFDSConfig FastFDSConfig, String path) {
//        TrackerServer trackerServer = null;
//        StorageServer storageServer = null;
//        StorageClient storageClient = null;
//        try {
//            LOGGER.info("=======要删除的fastdfs地址：" + path);
//            if (StringUtils.isEmpty(path)) {
//                return false;
//            }
//
//            if ((path.indexOf("group1") < 0) && !"1".equals(FastFDSConfig.getHasGroup1())) {
//                path = path.replace("/M00", "/group1/M00");
//            }
//            // 读取FastDfs配置信息
//            String serverOutIP = FastFDSConfig.getServerOutIP();
//            int portDown = Integer.parseInt(FastFDSConfig.getPortDown());
//            int portUpload = Integer.parseInt(FastFDSConfig.getPortUpload());
//
//            String head = "http://" + serverOutIP + ":" + portDown + "/";
//            path = path.replace(head, "");
//            String group = path.substring(0, path.indexOf("/"));
//            path = path.substring(path.indexOf("/") + 1);
//            String filePath = path;
//            LOGGER.info("=======group：{},path:{}", group, filePath);
//            // 初始化，这里注意不能传递下载的端口，因为fastdfs服务配置文件已做映射
//            initClientGlobal(FastFDSConfig.getServerIP(), portUpload, FastFDSConfig.getSecretKey());
//            TrackerClient tracker = new TrackerClient();
//            trackerServer = tracker.getConnection();
//            storageServer = tracker.getStoreStorage(trackerServer);
//
//            storageClient = new StorageClient(trackerServer, storageServer);
//            int i = storageClient.delete_file(group, path);
//            storageServer.close();
//            trackerServer.close();
//            return i == 0;
//
//        }
//        catch (Exception e) {
//            LOGGER.info("删除fastdfs图片失败:" + e.getMessage());
//            return false;
//        }
//    }
//
//    public long getFileSize(FastFDSConfig FastFDSConfig, String path) {
//        TrackerServer trackerServer = null;
//        StorageServer storageServer = null;
//        StorageClient storageClient = null;
//        try {
//            LOGGER.info("=======要查询的fastdfs地址：" + path);
//            if (StringUtils.isEmpty(path)) {
//                return 0;
//            }
//
//            if ((path.indexOf("group1") < 0) && !"1".equals(FastFDSConfig.getHasGroup1())) {
//                path = path.replace("/M00", "/group1/M00");
//            }
//            // 读取FastDfs配置信息
//            String serverOutIP = FastFDSConfig.getServerOutIP();
//            int portDown = Integer.parseInt(FastFDSConfig.getPortDown());
//            int portUpload = Integer.parseInt(FastFDSConfig.getPortUpload());
//
//            String head = "http://" + serverOutIP + ":" + portDown + "/";
//            path = path.replace(head, "");
//            String group = path.substring(0, path.indexOf("/"));
//            path = path.substring(path.indexOf("/") + 1);
//            String filePath = path;
//            LOGGER.info("=======group：{},path:{}", group, filePath);
//            // 初始化，这里注意不能传递下载的端口，因为fastdfs服务配置文件已做映射
//            initClientGlobal(FastFDSConfig.getServerIP(), portUpload, FastFDSConfig.getSecretKey());
//            TrackerClient tracker = new TrackerClient();
//            trackerServer = tracker.getConnection();
//            storageServer = tracker.getStoreStorage(trackerServer);
//
//            storageClient = new StorageClient(trackerServer, storageServer);
//            FileInfo fileInfo = storageClient.get_file_info(group, path);
//            long fileSize = fileInfo != null ? fileInfo.getFileSize() : 0;
//            storageServer.close();
//            trackerServer.close();
//            return fileSize;
//
//        }
//        catch (Exception e) {
//            LOGGER.info("获取fastdfs文件大小失败:" + e.getMessage());
//            return 0;
//        }
//    }
//
//    /**
//     * @param path
//     * @return
//     * @throws Exception
//     * @Description 下载文件
//     * @Author 陈浩良
//     * @date 2018年5月7日 下午3:06:46
//     */
//    public byte[] downloadFile(FastFDSConfig FastFDSConfig, String path, long position) {
//        if (StringUtils.isEmpty(path)) {
//            return null;
//        }
//
//        if ((path.indexOf("group1") < 0) && !"1".equals(FastFDSConfig.getHasGroup1())) {
//            path = path.replace("/M00", "/group1/M00");
//        }
//        // 读取FastDfs配置信息
//        String serverOutIP = FastFDSConfig.getServerOutIP();
//        int portDown = Integer.parseInt(FastFDSConfig.getPortDown());
//        int portUpload = Integer.parseInt(FastFDSConfig.getPortUpload());
//
//        try {
//            String head = "http://" + serverOutIP + ":" + portDown + "/";
//            path = path.replace(head, "");
//            String group = path.substring(0, path.indexOf("/"));
//            path = path.substring(path.indexOf("/") + 1);
//
//            // 初始化，这里注意不能传递下载的端口，因为fastdfs服务配置文件已做映射
//            initClientGlobal(FastFDSConfig.getServerIP(), portUpload, FastFDSConfig.getSecretKey());
//            TrackerClient tracker = new TrackerClient();
//            TrackerServer trackerServer = tracker.getConnection();
//            StorageServer storageServer = tracker.getStoreStorage(trackerServer);
//            StorageClient client = new StorageClient(trackerServer, storageServer);
//            FileInfo fileInfo = client.get_file_info(group, path);
//            byte[] fileByte = null;
//            if (fileInfo != null) {
//                long downloadBytes = fileInfo.getFileSize() - position;
//                fileByte = client.download_file(group, path, position, downloadBytes);
//            }
//            trackerServer.close();
//
//            return fileByte;
//        }
//        catch (Exception ex) {
//
//            return null;
//        }
//    }
//}
