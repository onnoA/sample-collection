package com.onnoa.shop.demo.video.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import com.onnoa.shop.demo.video.config.FTPClientConfig;
import com.onnoa.shop.demo.video.config.ShopProperties;
import com.onnoa.shop.demo.video.constant.PicInfoConstant;
import com.onnoa.shop.demo.video.constant.VideoTaskQueueConstant;
import com.onnoa.shop.demo.video.domain.PicSetInfo;
import com.onnoa.shop.demo.video.dto.ImgDto;
import com.onnoa.shop.demo.video.constant.VideoConstants;
import com.onnoa.shop.demo.video.constant.VideoFileFormatEnum;
import com.onnoa.shop.demo.video.constant.VideoInfoConstant;
import com.onnoa.shop.demo.video.domain.VideoInfo;
import com.onnoa.shop.demo.video.domain.VideoResult;
import com.onnoa.shop.demo.video.domain.VideoTaskQueue;
import com.onnoa.shop.demo.video.domain.VideoTranscode;
import com.onnoa.shop.demo.video.domain.VideoTranscodeParam;
import com.onnoa.shop.demo.video.dto.PicResult;
import com.onnoa.shop.demo.video.dto.VideoScreenShotParam;
import com.onnoa.shop.demo.video.exception.VideoException;
import com.onnoa.shop.demo.video.mapper.VideoInfoMapper;
import com.onnoa.shop.demo.video.mapper.VideoTaskQueueMapper;
import com.onnoa.shop.demo.video.properties.FTPPathConfig;
import com.onnoa.shop.demo.video.service.PicInfoService;
import com.onnoa.shop.demo.video.service.PicSetItemService;
import com.onnoa.shop.demo.video.service.QiniuService;
import com.onnoa.shop.demo.video.service.VideoInfoService;
import com.onnoa.shop.demo.video.service.VideoTaskQueueService;
import com.onnoa.shop.demo.video.service.VideoTranscodeService;
import com.onnoa.shop.demo.video.utils.BeanUtils;
import com.onnoa.shop.demo.video.utils.FtpUtil;
import com.onnoa.shop.demo.video.utils.VideoResolveResult;
import com.onnoa.shop.demo.video.utils.VideoResolveUtil;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * @Description:
 * @Author: onnoA
 * @Date: 2020/6/5 23:34
 */
@Service
public class VideoInfoServiceImpl extends ServiceImpl<VideoInfoMapper, VideoInfo> implements VideoInfoService {

    private static Logger LOGGER = LoggerFactory.getLogger(VideoInfoServiceImpl.class);
    @Autowired
    private ShopProperties shopProperties;
    @Autowired
    private FTPPathConfig ftpPathConfig;
    @Autowired
    private FTPClient ftpClient;
    @Autowired
    private VideoInfoMapper videoInfoMapper;
    @Autowired
    private VideoTaskQueueMapper taskQueueMapper;
    @Autowired
    private FTPClientConfig ftpClientConfig;
    @Autowired
    private VideoResolveUtil videoResolveUtil;
    @Value("${videostream.ffprobePath}")
    private String ffprobePath;
    @Value("${videostream.transcodeOutputPath}")
    private String transcodeOutputPath;
    @Value("${videostream.ffmpegPath}")
    private String ffmpegPath;
    @Value("${ftp.playUrl}")
    private String playUrl;
    @Value("${videostream.screenShotPath}")
    private String screenShotPath;
    @Autowired
    private QiniuService qiniuService;
    @Autowired
    private PicSetItemService picSetItemService;
    @Autowired
    private VideoTranscodeService videoTranscodeService;
    @Autowired
    private PicInfoService picInfoService;
    @Autowired
    private VideoTaskQueueService videoTaskQueueService;

    public static final String ORIGINAL = "original";


    @Override
    public boolean uploadVideo(String videoId, MultipartFile file, HttpServletRequest request) {
        try {
            long start = System.currentTimeMillis();
            // 获取文件后缀，并判断是否符合视频文件格式
            String originalFilename = file.getOriginalFilename();
            String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            /*List<String> formatList = Lists.newArrayList();
            for (VideoFileFormatEnum videoFileFormatEnum : VideoFileFormatEnum.values()) {
                LOGGER.info("后缀:{},枚举:{}", suffix, videoFileFormatEnum.getFormat());
                formatList.add(videoFileFormatEnum.getFormat());
            }*/
            List<String> formatList = VideoFileFormatEnum.getAllFormat();
            if (!formatList.contains(suffix)) {
                throw VideoException.VIDEO_FORMAT_ABNORMAL.format(suffix);
            }

            CommonsMultipartFile cf = (CommonsMultipartFile) file;
            DiskFileItem dfi = (DiskFileItem) cf.getFileItem();
            // 通过ffProbe读取视频文件信息
            String fFMpegCommand = VideoResolveUtil.readVideoFile(shopProperties.getVideo().getFfprobePath(), dfi.getStoreLocation().getPath());
            VideoResolveResult result = VideoResolveUtil.videoResolve(fFMpegCommand);

            String rp = result.getVideoWidth() + "x" + result.getVideoHeight() + "p";
            String ftpPath = videoId + "/";
            String filename = videoId + "_" + rp + "_" + VideoConstants.ORIGINAL + VideoFileFormatEnum.MP4_VIDEO_FORMAT.getFormat();
            String fileUrl = ftpPathConfig.getFtpPrefixPath() + ftpPath + filename;
            InputStream ins = file.getInputStream();
            FtpUtil.uploadFile(ftpPathConfig, ftpPath, filename, ins, ftpClient);

            //保存视频信息
            String videoName = file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf(".")) + VideoFileFormatEnum.MP4_VIDEO_FORMAT.getFormat();
            VideoInfo videoInfo = new VideoInfo();
            BeanUtils.copyToBean(result, videoInfo);
            videoInfo.setId(videoId);
            videoInfo.setVideoName(videoName);
            videoInfo.setVideoTitle(videoName);
            videoInfo.setOriginFileName(file.getOriginalFilename());
            videoInfo.setFromSys("postMan上传");
            videoInfo.setFileUrl(fileUrl);
            Integer formatType = VideoFileFormatEnum.MP4_VIDEO_FORMAT.getFormat().equals(suffix) ? 1 : 0;
            videoInfo.setFormatType(formatType);

            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            videoInfo.setCreateBy(uuid);
            videoInfo.setStatus(VideoConstants.UNPUBLISHED_STATUS);
            videoInfoMapper.insert(videoInfo);

            //保存任务队列数据
            VideoTaskQueue videoTaskQueue = new VideoTaskQueue();
            videoTaskQueue.setTaskId(videoId);
            videoTaskQueue.setStatus(0);
            videoTaskQueue.setCreateBy(uuid);
            taskQueueMapper.insert(videoTaskQueue);
            LOGGER.info("{}上传视频总耗时:{}s", videoId, (System.currentTimeMillis() - start) / 1000);
            return true;
        } catch (Exception e) {
            LOGGER.error("视频上传失败", e);
            throw VideoException.VIDEO_UPLOAD_FAILED;
        }
    }

    @Override
    public void videoProcess(VideoInfo videoInfo) {
        long start = System.currentTimeMillis();
        String videoInfoId = videoInfo.getId();
        videoInfo = videoInfoMapper.selectById(videoInfoId);
        LOGGER.info("{}--------------------- 1:processingVideoConsumerJob开始处理视频, 当前时间{} >>> 处理的视频videoId:{}---------------------", Thread.currentThread().getName(), new Date(), videoInfoId);
        //从ftp服务器下载到本地
        ftpPathConfig.setReadLocal(0);
        String displayRate = videoInfo.getVideoWidth() + "x" + videoInfo.getVideoHeight() + "p";
        FTPClient ftpClient = ftpClientConfig.getFTPClient();
        LOGGER.info("{}--------------------- 2:processingVideoConsumerJob处理视频操作开始从FTP下载视频到本地! >>> 处理的视频videoId:{}---------------------", Thread.currentThread().getName(), videoInfoId);
        String url = FtpUtil.downloadFile(ftpPathConfig, videoInfoId, videoInfoId + "_" + displayRate + "_" + ORIGINAL + ".mp4", ftpClient);
        LOGGER.info("{}--------------------- 3:processingVideoConsumerJob处理视频操作从FTP下载到本地成功,下载到本地的对应的路径:{} >>> 处理的视频videoId:{}---------------------", Thread.currentThread().getName(), url, videoInfoId);
        //读取视频
        VideoResult videoResult = videoResolveUtil.analysisJson(videoResolveUtil.getFfprod(ffprobePath, url));
        LOGGER.info("{}--------------------- 4:processingVideoConsumerJob处理视频操作读取视频信息数据成功, 对应的视频数据:{} >>> 处理的视频videoId:{}---------------------", Thread.currentThread().getName(), videoResult.toString(), videoInfoId);

        List<VideoTranscode> videoTranscodes = this.startTranscode(videoInfo, videoResult, url);
        System.out.println("视频信息："+ JSON.toJSON(videoTranscodes));
        LOGGER.info("{}--------------------- 5:processingVideoConsumerJob处理视频开始进行视频转码操作成功! >>> 处理的视频videoId:{}---------------------", Thread.currentThread().getName(), videoInfoId);

        //原视频数据保存到转码表
        ImgDto imgDto = handleVideoCoverPic(videoInfo, url);
        LOGGER.info("{}--------------------- 6:processingVideoConsumerJob生成处理视频的封面图完成,处理的视频videoId:{}---------------------", Thread.currentThread().getName(), videoInfoId);

        Map<String, String> picSetUrlMap = picSetItemService.generatePicSetUrls(imgDto);
        LOGGER.info("{}--------------------- 7:processingVideoConsumerJob执行获取jpg/webpUrls成功 >>> 处理的视频videoId:{}---------------------", Thread.currentThread().getName(), videoInfoId);

        excuteTransCodeVideoDB(videoInfo, videoTranscodes, imgDto, null, picSetUrlMap);
        LOGGER.info("{}--------------------- 8:processingVideoConsumerJob处理视频转码并写库成功，本次处理视频结束{}s! >>> 处理的视频videoId:{}---------------------", Thread.currentThread().getName(), (System.currentTimeMillis() - start) / 1000, videoInfoId);



    }

    @Override
    public void test(VideoInfo videoInfo) {
        if(videoInfo.getId().contains("1")){
            while (true){
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("出来.......................");
    }

    /**
     * 视频转码
     *
     * @param videoInfo
     * @param videoResult
     * @param url         视频ftp路径
     * @return
     */
    private List<VideoTranscode> startTranscode(VideoInfo videoInfo, VideoResult videoResult, String url) {
        //根据uuid生成新的输出文件夹
        this.createFile(transcodeOutputPath, videoInfo.getId());
        //转码获取转码成功的地址，若无则为空
        VideoTranscodeParam videoTranscodeParam = new VideoTranscodeParam();
        videoTranscodeParam.setFfmpegPath(ffmpegPath);
        videoTranscodeParam.setSourceVideoPath(url);
        videoTranscodeParam.setOutputVideoPath(transcodeOutputPath);
        videoTranscodeParam.setUuid(videoInfo.getId());
        //转码
        Map<String, StringBuilder> resultMap = videoResolveUtil.getDifferDefinitionType(videoTranscodeParam, videoResult);
        List<VideoTranscode> results = new ArrayList<>();
        if (!resultMap.isEmpty()) {
            //生成转码文件实体
            Set<String> addressList = resultMap.keySet();
            for (String paht : addressList) {
                File localFile = new File(paht);
                try (InputStream ins = new FileInputStream(localFile)) {
                    String localFileName = localFile.getName();
                    this.uploadFilesToFTP(ins, localFileName, localFileName.substring(0, localFileName.lastIndexOf("_")) + "/");
                    VideoResult transResult = videoResolveUtil.analysisJson(videoResolveUtil.getFfprod(ffprobePath, paht));
                    VideoTranscode vt = new VideoTranscode();
                    BeanUtils.copyToBean(transResult, vt);
                    vt.setFileUrl(ftpPathConfig.getFtpPrefixPath() + localFileName.substring(0, localFileName.lastIndexOf("_")) + "/" + localFileName);
                    //播放路径
                    String purl = videoInfo.getId() + "_," + transResult.getVideoWidth() + "x" + transResult.getVideoHeight() + ",p" + VideoInfoConstant.FILE_FORMAT_ONE;
                    vt.setPlayUrl(playUrl + localFileName.substring(0, localFileName.lastIndexOf("_")) + "/" + purl + VideoInfoConstant.PLAURL_SUFFIX);
                    //每个转码视频另外生成uuid
                    String differId = UUID.randomUUID().toString().replace("-", "");
                    vt.setId(differId);
                    vt.setVideoId(videoInfo.getId());
                    vt.setDefinitionType(this.getDefinitionType(transResult.getVideoWidth() + "x" + transResult.getVideoHeight() + "p"));
                    vt.setFormatType(VideoInfoConstant.FILE_FORMAT_ONE.equals(url.substring(url.indexOf("."))) ? 1 : 0);
                    vt.setStatus(VideoInfoConstant.UNPUBLISHED_STATUS);
                    vt.setTranscodeCmd(resultMap.get(paht).toString());
                    vt.setCreateBy(videoInfo.getCreateBy());
                    results.add(vt);
                } catch (Exception e) {
                    LOGGER.info("转码失败", e);
                    throw VideoException.VIDEO_TRANSCODE_FAILED;
                }
            }
        }
        return results;
    }

    private void createFile(String path, String uuid) {

        File screenShotFile = new File(path + uuid);
        if (!screenShotFile.exists()) {
            screenShotFile.mkdir();
        }
    }

    /**
     * @Description 上传ftp服务器
     * @author onnoA
     * @date 2021/7/6 23:01
     * @param inputStream
     * @param filename
     * @param ftppath
     */
    private void uploadFilesToFTP(InputStream inputStream, String filename, String ftppath) {
        FTPClient ftpClient = ftpClientConfig.getFTPClient();
        FtpUtil.uploadFile(ftpPathConfig, ftppath, filename, inputStream, ftpClient);
    }

    /**
     * @Description 获取视频分辨率
     * @author onnoA
     * @date 2021/7/6 23:03
     * @param rp
     * @return java.lang.Integer
     */
    private Integer getDefinitionType(String rp) {
        int type = 0;
        if (rp.equals(VideoInfoConstant.LANDSCAPE_STANDARD_DEFINITION) || rp.equals(VideoInfoConstant.PORTRAIT_STANDARD_DEFINITION)) {
            type = 11;
        } else if (rp.equals(VideoInfoConstant.LANDSCAPE_HIGH_DEFINITION) || rp.equals(VideoInfoConstant.PORTRAIT_HIGH_DEFINITION)) {
            type = 12;
        } else if (rp.equals(VideoInfoConstant.LANDSCAPE_ULTRA_DEFINITION) || rp.equals(VideoInfoConstant.PORTRAIT_ULTRA_DEFINITION)) {
            type = 13;
        } else if (rp.equals(VideoInfoConstant.LANDSCAPE_BLURAY_DEFINITION) || rp.equals(VideoInfoConstant.PORTRAIT_BLURAY_DEFINITION)) {
            type = 14;
        }
        return type;
    }

    public ImgDto handleVideoCoverPic(VideoInfo videoInfo, String url) {

        LOGGER.debug("{}--------------------- 6.1:processingVideoConsumerJob执行读取视频流相关配置文件数据>>> 处理的视频videoId:{} ---------------------", Thread.currentThread().getName(), videoInfo.getId());
        VideoScreenShotParam screenShotParam = getScreenShotParam(url,
                ffmpegPath, screenShotPath, videoInfo.getId(), null, null,
                videoInfo.getVideoWidth());

        LOGGER.debug("{}--------------------- 6.2:processingVideoConsumerJob执行创建视频id:{}文件夹 ---------------------", Thread.currentThread().getName(), videoInfo.getId());


        //创建文件夹 (ffmpeg不支持创建文件夹 需提前创建)
        LOGGER.debug("{}--------------------- 6.3:processingVideoConsumerJob执行创建截图的文件夹>>> 处理的视频videoId:{} ---------------------", Thread.currentThread().getName(), videoInfo.getId());
        File dir = new File(screenShotParam.getScreenShotPath() + screenShotParam.getScreenShotName() + "/");
        if (!dir.exists()) {
            dir.mkdirs();
            LOGGER.debug("6.4:创建图片目录:{}", screenShotParam.getScreenShotPath() + screenShotParam.getScreenShotName() + "/");
        }
        //获取jpgUrl,之后存数据库
        String jpgUrl = getJpgUrlFromQiNiu(videoInfo, screenShotParam);
        //上传前预处理
        ImgDto imgDto = uploadPreprocessing(screenShotParam, videoInfo);

        videoInfo.setCoverPicUrl(jpgUrl);

        return imgDto;
    }

    private VideoScreenShotParam getScreenShotParam(String path, String ffmpegPath, String screenShotPath, String uuid, String startTime, String length, Integer videoWidth) {

        VideoScreenShotParam screenShotParam = new VideoScreenShotParam();
        screenShotParam.setFfmpegPath(ffmpegPath);
        screenShotParam.setSourceVideoPath(path);
        screenShotParam.setScreenShotPath(screenShotPath);
        screenShotParam.setScreenShotName(uuid);
        screenShotParam.setStartTime(startTime);
        screenShotParam.setLength(length);
        screenShotParam.setVideoWidth(videoWidth);
        return screenShotParam;
    }

    public ImgDto uploadPreprocessing(VideoScreenShotParam screenShotParam, VideoInfo videoInfo) {
        //截取GIF
        LOGGER.debug(Thread.currentThread().getName() + "--------------------- 6.8:processingVideoConsumerJob执行获取gif路径>>> 处理的视频videoId:{} ---------------------", videoInfo.getId());
        String gifPath = videoResolveUtil.getScreenShotGif(screenShotParam);
        // 屏蔽七牛云
        LOGGER.debug(Thread.currentThread().getName() + "--------------------- 6.9:processingVideoConsumerJob执行上传gif到七牛,并获取key>>> 处理的视频videoId:{} ---------------------", videoInfo.getId());
//        String gifKey = qiniuService.uploadAndGetKey(videoResolveUtil.getPicData(gifPath));
        String gifKey = gifPath;
        //读取图片信息获得图片的宽高,大小
        LOGGER.debug(Thread.currentThread().getName() + "--------------------- 6.10:processingVideoConsumerJob执行读取gif信息获得gif的宽高,大小>>> 处理的视频videoId:{} ---------------------", videoInfo.getId());
        PicResult gifMessage = videoResolveUtil.PicAnalysisJson(videoResolveUtil.getFfprod(ffprobePath, gifPath));
        //在这要调用上传图片接口.
        ImgDto imgDto = addImgDtoProperties(gifKey, gifMessage, screenShotParam);
        return imgDto;
    }

    public String getJpgUrlFromQiNiu(VideoInfo videoInfo, VideoScreenShotParam screenShotParam) {
        //截取JPG
        LOGGER.debug(Thread.currentThread().getName() + "--------------------- 6.5:processingVideoConsumerJob执行截图>>> 处理的视频videoId:{} ---------------------", videoInfo.getId());
        String jpgPicPath = videoResolveUtil.getScreenShotJpg(screenShotParam);

        //将截图上传到七牛,根据七牛返回值，获取返回key
        LOGGER.debug(Thread.currentThread().getName() + "--------------------- 6.6:processingVideoConsumerJob执行截图上传七牛>>> 处理的视频videoId:{} ---------------------", videoInfo.getId());
// 屏蔽上传七牛
        //        String jpgKey = qiniuService.uploadAndGetKey(videoResolveUtil.getPicData(jpgPicPath));

        //根据截图的key获取url
        LOGGER.debug(Thread.currentThread().getName() + "--------------------- 6.7:processingVideoConsumerJob执行根据截图的key从七牛获取url>>> 处理的视频videoId:{} ---------------------", videoInfo.getId());
// 屏蔽上传七牛
        //        String jpgUrl = qiniuService.getDownloadUrl(jpgKey);
        return jpgPicPath;
    }


    @Transactional(rollbackFor = Exception.class)
    public void excuteTransCodeVideoDB(VideoInfo videoInfo, List<VideoTranscode> videoTranscodes, ImgDto imgDto, VideoTaskQueue videoTaskQueue, Map<String, String> picSetUrlMap) {

        imgDto.setPicId(UUID.randomUUID().toString().replaceAll("-", ""));
        VideoTranscode videoTranscode = addTranscodeProperties(videoInfo);
        videoTranscodes.add(videoTranscode);
        //先删掉转码表中数据再插入
        LOGGER.debug("{}--------------------- 8.1:processingVideoConsumerJob执行先删掉转码表中数据再插入>>> 处理的视频videoId:{} ---------------------", Thread.currentThread().getName(), videoInfo.getId());
        overwriteTranscodeDataAndInsertTranscodeDB(videoInfo, videoTranscodes);


        LOGGER.debug("{}--------------------- 8.2:processingVideoConsumerJob执行图片插入数据库操作, 处理的视频videoId:{} ---------------------", Thread.currentThread().getName(), videoInfo.getId());
        PicSetInfo picSetInfo = picInfoService.savePicSet(imgDto, picSetUrlMap);

        LOGGER.debug("{}--------------------- 8.3:processingVideoConsumerJob更新VideoInfo表状态>>> 处理的视频videoId:{} ---------------------", Thread.currentThread().getName(), videoInfo.getId());
        setVideoInfoPropertiesAndUpdateDB(videoInfo, videoTranscodes, imgDto, picSetInfo);
        //任务处理成功
        LOGGER.debug("{}--------------------- 8.4:processingVideoConsumerJob更新VideoTaskQueue表状态,任务处理成功>>> 处理的视频videoId:{} ---------------------", Thread.currentThread().getName(), videoInfo.getId());
//        updateTaskQueueDB(videoTaskQueue);


    }

    public ImgDto addImgDtoProperties(String gifKey, PicResult gifMessage, VideoScreenShotParam screenShotParam) {
        ImgDto imgDto = new ImgDto();
        imgDto.setQiniuKey(gifKey);
        imgDto.setWidth(gifMessage.getWidth());
        imgDto.setHeight(gifMessage.getHeight());
        imgDto.setFileSize(gifMessage.getFileSize());
        imgDto.setSuffix(PicInfoConstant.FILE_PICSUFFIX_GIF);
        imgDto.setFileName(screenShotParam.getScreenShotName());
        imgDto.setPicId(UUID.randomUUID().toString().replaceAll("-", ""));
        return imgDto;
    }

    public VideoTranscode addTranscodeProperties(VideoInfo videoInfo) {
        VideoTranscode videoTranscode = new VideoTranscode();
        BeanUtils.copyToBean(videoInfo, videoTranscode);
        videoTranscode.setId(null);
        videoTranscode.setVideoId(videoInfo.getId());
        videoTranscode.setDefinitionType(0);
        return videoTranscode;

    }

    public void updateTaskQueueDB(VideoTaskQueue videoTaskQueue) {
        videoTaskQueue.setStatus(VideoTaskQueueConstant.STATUS_SUCCESS);
        videoTaskQueueService.updateById(videoTaskQueue);
    }

    public void setVideoInfoPropertiesAndUpdateDB(VideoInfo videoInfo, List<VideoTranscode> results, ImgDto imgDto, PicSetInfo picSetInfo) {
        //视频播放默认360p,获取集合中的360P视频对象
        VideoTranscode videoTranscode = results.stream()
                .filter(result -> this.getDefinitionType(VideoInfoConstant.LANDSCAPE_STANDARD_DEFINITION).equals(result.getDefinitionType()))
                .findFirst()
                .get();
        videoInfo.setPlayUrl(videoTranscode != null ? videoTranscode.getPlayUrl() : results.get(0).getPlayUrl());
        videoInfo.setStatus(VideoInfoConstant.AVAILABLE_STATUS);
        videoInfo.setPicsId(picSetInfo.getId());
        //更新视频为发布状态
        this.updateById(videoInfo);
    }

    /**
     * @Description 把之前转码成功的记录覆盖和重新插入数据
     * @author onnoA
     * @date 2021/7/6 23:32
     * @param videoInfo
     * @param results
     */
    public void overwriteTranscodeDataAndInsertTranscodeDB(VideoInfo videoInfo, List<VideoTranscode> results) {
        //先删掉转码表中数据再插入
        LOGGER.debug(Thread.currentThread().getName() + "--------------------- 8.1.1:processingVideoConsumerJob执行先删掉转码表中数据! >>> 处理的视频videoId:{}---------------------", videoInfo.getId());
        deleteTranscodeData(videoInfo);

        LOGGER.debug(Thread.currentThread().getName() + "--------------------- 8.1.2:processingVideoConsumerJob执行批量插入转码表数据! >>> 处理的视频videoId:{}---------------------", videoInfo.getId());
        videoTranscodeService.insertBatch(results);
    }

    /**
     * @Description 删除转码表(utopa_videostream_svc_video_transcode)中的数据再插入
     * @author onnoA
     * @date 2021/7/6 23:32
     * @param videoInfo 
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteTranscodeData(VideoInfo videoInfo) {


        VideoTranscode code = new VideoTranscode();
        code.setDeleted(1);
        videoTranscodeService.update(code, new EntityWrapper<VideoTranscode>()
                .eq("video_id", videoInfo.getId())
                .eq("deleted", 0));
    }




    // 对ffmpeg无法解析的文件格式(wmv9，rm，rmvb等), 可以先用别的工具（mencoder）转换为avi(ffmpeg能解析的)格式.
}
