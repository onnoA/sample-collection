//package com.onnoa.shop.demo.video.utils;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.onnoa.shop.demo.video.exception.FileUploadException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//import java.io.BufferedInputStream;
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @Description: 视频解析工具类，使用ffmpeg对视频文件进行解析
// * @Author: onnoA
// * @Date: 2020/6/6 11:27
// */
////@Component
//public class VideoResolveUtil {
//
//    private static Logger LOGGER = LoggerFactory.getLogger(VideoResolveUtil.class);
//
//    /**
//     * 功能描述: 读取视频文件
//     *
//     * @param ffProbePath     ffprobe 解析视频安装路径
//     * @param sourceVideoPath 源视频文件路径
//     * @return
//     * @date 2020/6/6 11:32
//     */
//    public static String readVideoFile(String ffProbePath, String sourceVideoPath) {
//        // -v quiet:   静默工作,不输出版本、工作信息 ；
//        //-print_format  json: 表示输出为JSON格式
//        //-show_streams 参数的意义是输出每一个流的详细信息。
//        //-show_format: 表示输出文件的格式信息，比如封装格式、码率、流数目、文件时长，文件大小等；
//        // 解析后参数说明 https://blog.csdn.net/zhoubotong2012/article/details/102872950
//        String ffprodcomand = ffProbePath + " -v quiet -print_format json -show_streams -show_format " + sourceVideoPath;
//        LOGGER.info("读取视频参数ffprod命令: {}", ffprodcomand);
//        StringBuffer sb = new StringBuffer();
//        BufferedReader inBr = null;
//        BufferedInputStream in = null;
//        try {
//            Runtime run = Runtime.getRuntime();
//            Process p2 = run.exec(ffprodcomand);
//            in = new BufferedInputStream(p2.getInputStream());
//            inBr = new BufferedReader(new InputStreamReader(in));
//            String lineStr;
//            while ((lineStr = inBr.readLine()) != null) {
//                sb.append(lineStr);
//            }
//            return sb.toString();
//        } catch (Exception e) {
//            LOGGER.error("读取视频参数失败: ", e);
//            throw FileUploadException.FAILED_TO_READ_VIDEO_FILE;
//        } finally {
//            try {
//                if (inBr != null) {
//                    inBr.close();
//                }
//                if (in != null) {
//                    in.close();
//                }
//            } catch (IOException e) {
//                throw new RuntimeException("关闭失败");
//            }
//        }
//    }
//
//
//    /**
//     * 功能描述: 通过ffmpeg命令解析视频文件
//     *
//     * @param result ffmpeg命令
//     * @return
//     * @date 2020/6/8 0:29
//     */
//    public static VideoResolveResult videoResolve(String result) {
//        VideoResolveResult videoResult = new VideoResolveResult();
//        try {
//            Map<String, Object> jsonMap = JSON.parseObject(result, HashMap.class);
//            JSONArray streams = (JSONArray) jsonMap.get("streams");
//            JSONObject json0 = (JSONObject) streams.get(0);
//            JSONObject json1 = (JSONObject) streams.get(1);
//            //视频json串
//            JSONObject videoJson = json0.size() > json1.size() ? json0 : json1;
//            //视频码率
//            videoResult.setVdBitrate(Long.parseLong(videoJson.getString("bit_rate")));
//            JSONObject tagsJson = (JSONObject) videoJson.get("tags");
//            videoResult.setRotate(tagsJson.getString("rotate"));
//            if ("90".equals(tagsJson.getString("rotate"))) {
//                //宽
//                videoResult.setVideoWidth(videoJson.getInteger("height"));
//                //高
//                videoResult.setVideoHeight(videoJson.getInteger("width"));
//            } else {
//                //宽
//                videoResult.setVideoWidth(videoJson.getInteger("width"));
//                //高
//                videoResult.setVideoHeight(videoJson.getInteger("height"));
//            }
//
//            //音频json串
//            JSONObject audioJson = (JSONObject) streams.get(1);
//            //音频码率
//            videoResult.setAdBitrate(Long.parseLong(audioJson.getString("bit_rate")));
//
//            JSONObject format = (JSONObject) jsonMap.get("format");
//            //文件大小
//            videoResult.setFileSize(Long.parseLong(format.getString("size")));
//            //总码率
//            videoResult.setTlBitrate(Long.parseLong(format.getString("bit_rate")));
//            //总时长
//            String durationStr = format.getString("duration");
//            videoResult.setDuration(Double.valueOf(durationStr));
//        } catch (Exception e) {
//            LOGGER.error("解析视频信息失败:{}", e);
//            throw FileUploadException.FAILED_TO_RESOLVE_VIDEO;
//        }
//        return videoResult;
//
//    }
//
//
//}
