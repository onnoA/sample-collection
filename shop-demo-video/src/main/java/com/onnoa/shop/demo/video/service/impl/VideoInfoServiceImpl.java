package com.onnoa.shop.demo.video.service.impl;

import com.onnoa.shop.common.properties.base.ShopProperties;
import com.onnoa.shop.common.utils.BeanUtils;
import com.onnoa.shop.demo.video.constant.VideoConstants;
import com.onnoa.shop.demo.video.constant.VideoFileFormatEnum;
import com.onnoa.shop.demo.video.domain.VideoInfo;
import com.onnoa.shop.demo.video.domain.VideoTaskQueue;
import com.onnoa.shop.demo.video.exception.VideoException;
import com.onnoa.shop.demo.video.mapper.VideoInfoMapper;
import com.onnoa.shop.demo.video.mapper.VideoTaskQueueMapper;
import com.onnoa.shop.demo.video.properties.FTPPathConfig;
import com.onnoa.shop.demo.video.service.VideoInfoService;
import com.onnoa.shop.demo.video.utils.FtpUtil;
import com.onnoa.shop.demo.video.utils.VideoResolveResult;
import com.onnoa.shop.demo.video.utils.VideoResolveUtil;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.UUID;

/**
 * @Description:
 * @Author: onnoA
 * @Date: 2020/6/5 23:34
 */
@Service
public class VideoInfoServiceImpl implements VideoInfoService {

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


    @Override
    public boolean uploadVideo(String videoId, MultipartFile file, HttpServletRequest request) {
        try {
            long start = System.currentTimeMillis();
            // 获取文件后缀，并判断是否符合视频文件格式
            String originalFilename = file.getOriginalFilename();
            String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            for (VideoFileFormatEnum videoFileFormatEnum : VideoFileFormatEnum.values()) {
                if (!suffix.equalsIgnoreCase(videoFileFormatEnum.getFormat())) {
                    throw VideoException.VIDEO_FORMAT_ABNORMAL.format(suffix);
                }
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
            BeanUtils.copyToBean(request, videoInfo);
            videoInfo.setId(videoId);
            videoInfo.setVideoName(videoName);
            videoInfo.setVideoTitle(videoName);
            videoInfo.setOriginFileName(file.getOriginalFilename());
            videoInfo.setFromSys("postMan上传");
            videoInfo.setFileUrl(fileUrl);
            Integer formatType = VideoFileFormatEnum.MP4_VIDEO_FORMAT.getFormat().equals(suffix) ? 1 : 0;
            videoInfo.setFormatType(formatType);

            //todo 播放地址
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


    // 对ffmpeg无法解析的文件格式(wmv9，rm，rmvb等), 可以先用别的工具（mencoder）转换为avi(ffmpeg能解析的)格式.
}
