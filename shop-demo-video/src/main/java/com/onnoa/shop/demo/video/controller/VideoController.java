package com.onnoa.shop.demo.video.controller;

import com.alibaba.fastjson.JSON;
import com.onnoa.shop.demo.video.common.result.ResultBean;
import com.onnoa.shop.demo.video.constant.VideoErrorCodeEnum;
import com.onnoa.shop.demo.video.domain.VideoInfo;
import com.onnoa.shop.demo.video.service.VideoInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


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

    @PostMapping(value = "process")
    @ApiOperation(value = "视频处理接口", notes = "视频处理")
    public ResultBean videoProcess(@RequestBody @Valid VideoInfo videoInfo){
        videoInfoService.videoProcess(videoInfo);
        return ResultBean.success();
    }



    @GetMapping("/list")
    @ApiOperation(value = "视频列表接口", notes = "视频列表")
    public ResultBean getAvatarByUserId(@RequestParam("ownerId") String ownerId) {

        return ResultBean.success(null);
    }

    @PostMapping("/test")
    @ApiOperation(value = "视频列表接口", notes = "视频列表")
    public ResultBean test(@RequestBody VideoInfo videoInfo) {
        videoInfoService.test(videoInfo);
        return ResultBean.success(null);
    }

    public static void main(String[] args) {
//        try {
//            boolean flag = transform("D:\\IDEATools\\开发工具\\Java开发工具\\ffmpeg-20200303-60b1f85-win64-static\\ffmpeg-20200303-60b1f85-win64-static\\bin\\ffmpeg.exe", "F:\\work\\视频业务流程\\视频素材\\3333.mp4", "F:\\work\\视频业务流程\\视频素材\\12344.avi", "720x1080");
//            System.out.println((flag == true) ? "成功" : "失败");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        int skip = 100;
//        int limit = 10;
//        List<String> names = new ArrayList<>();
//        for (int i = 0; i < 200; i++) {
//            names.add(String.valueOf(i));
//        }
//        List<String> result = names.stream().skip(skip).limit(limit).collect(Collectors.toList());
//        System.out.println(JSON.toJSON(result));
//        Date fullDate = getFullDate(new Date());
//        System.out.println(fullDate);
//        System.out.println(new Date());

//        String str = "http://172.16.25.133/portal-web/image/755786673_1626162897353.png";
//        String replace = str.replace("http://172.16.25.133/portal-web", "www.test.com");
//        System.out.println(replace);
        int i = 0;
        while (true){
            i ++;
            if(i == 3 ){
                System.out.println("break..");
                break;
            }
            System.out.println("break..");
        }
        System.out.println("================");


    }

    public static Date getFullDate(Date input) {
        // 每天的毫秒数。(这种算法有时区的问题。)
        // final long dayMilSeconds = 1000*60*60*24;
        //
        // long milSeconds = input.getTime();
        //
        // long dateInMilSeconds = (milSeconds/dayMilSeconds)*dayMilSeconds;
        // System.out.println(dateInMilSeconds);
        // return new java.sql.Date(dateInMilSeconds);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(input);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        // System.out.println(calendar.getTimeInMillis());

        return new Date(calendar.getTimeInMillis());

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
