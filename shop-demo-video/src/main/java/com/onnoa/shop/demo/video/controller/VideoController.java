package com.onnoa.shop.demo.video.controller;

import com.onnoa.shop.common.result.ResultBean;
import com.onnoa.shop.demo.video.constant.VideoErrorCodeEnum;
import com.onnoa.shop.demo.video.service.VideoInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


/**
 * @Description:
 * @Author: onnoA
 * @Date: 2020/5/28 09:31
 */
@Slf4j
@Api(tags = "视频相关接口", description = "提供视频相关的 Rest API")
@RequestMapping(value = "/video")
@RestController
public class VideoController {

    @Autowired
    private VideoInfoService videoInfoService;

    @PostMapping(value = "/add")
    @ApiOperation(value = "上传视频接口", notes = "上传视频")
    public ResultBean uploadVideo(@RequestParam String videoId, MultipartFile file, HttpServletRequest request) {
        boolean isSuccess = videoInfoService.uploadVideo(videoId, file, request);
        return isSuccess ? ResultBean.success(isSuccess) : ResultBean.error(VideoErrorCodeEnum.VIDEO_FAIL_TO_UPLOAD);
    }


    @GetMapping("/list")
    @ApiOperation(value = "视频列表接口", notes = "视频列表")
    public ResultBean getAvatarByUserId(@RequestParam("ownerId") String ownerId) {

        return ResultBean.success(null);
    }

    public static void main(String[] args) {
        try {
            boolean flag = transform("D:\\IDEATools\\开发工具\\Java开发工具\\ffmpeg-20200303-60b1f85-win64-static\\ffmpeg-20200303-60b1f85-win64-static\\bin\\ffmpeg.exe", "F:\\work\\视频业务流程\\视频素材\\3333.mp4", "F:\\work\\视频业务流程\\视频素材\\3333.avi", "720x1080");
            System.out.println((flag == true) ? "成功" : "失败");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 视频转换
     *
     * @param ffmpegPath ffmpeg路径
     * @param oldPath    原视频地址
     * @param newPath    新视频存放地址(包含视频格式)
     * @param resolution 分辨率
     * @return
     * @throws Exception
     */
    public static Boolean transform(String ffmpegPath, String oldPath, String newPath, String resolution) throws Exception {
        List<String> command = getFfmpegCommand(ffmpegPath, oldPath, newPath, resolution);
        if (null != command && command.size() > 0) {
            return process(command);
        }
        return false;
    }

    private static boolean process(List<String> command) throws Exception {
        try {
            if (null == command || command.size() == 0)
                return false;
            Process videoProcess = new ProcessBuilder(command).redirectErrorStream(true).start();
            videoProcess.getInputStream().close();
            int exitcode = videoProcess.waitFor();
            if (exitcode == 1)
                return false;
            return true;
        } catch (Exception e) {
            throw new Exception("file transfer failed", e);
        }
    }

    private static List<String> getFfmpegCommand(String ffmpegPath, String oldfilepath, String outputPath, String resolution) throws Exception {
        List<String> command = new ArrayList<String>();
        command.add(ffmpegPath); // 添加转换工具路径
        command.add("-i"); // 添加参数＂-i＂，该参数指定要转换的文件
        command.add(oldfilepath); // 添加要转换格式的视频文件的路径
        command.add("-qscale"); // 指定转换的质量
        command.add("4");

        /*command.add("-ab"); //设置音频码率
        command.add("64");
        command.add("-ac"); //设置声道数
        command.add("2");
        command.add("-ar"); //设置声音的采样频率
        command.add("22050");*/

        command.add("-r"); // 设置帧速率
        command.add("24");
        command.add("-s"); // 设置分辨率
        command.add(resolution);
        command.add("-y"); // 添加参数＂-y＂，该参数指定将覆盖已存在的文件
        command.add(outputPath);
        return command;
    }
}
