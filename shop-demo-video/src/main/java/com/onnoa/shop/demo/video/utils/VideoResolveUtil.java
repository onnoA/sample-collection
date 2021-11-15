package com.onnoa.shop.demo.video.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.onnoa.shop.demo.video.component.ExtCommandExecutor;
import com.onnoa.shop.demo.video.config.TranscodeParameterConfig;
import com.onnoa.shop.demo.video.constant.TranscodeConstant;
import com.onnoa.shop.demo.video.domain.VideoResult;
import com.onnoa.shop.demo.video.domain.VideoTranscodeParam;
import com.onnoa.shop.demo.video.dto.PicResult;
import com.onnoa.shop.demo.video.dto.VideoScreenShotParam;
import com.onnoa.shop.demo.video.exception.VideoException;
import com.onnoa.shop.demo.video.thread.DealProcessSream;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Description: 视频解析工具类，使用ffmpeg对视频文件进行解析
 * @Author: onnoA
 * @Date: 2020/6/6 11:27
 */
@Component
public class VideoResolveUtil {

    private static Logger LOGGER = LoggerFactory.getLogger(VideoResolveUtil.class);

    @Autowired
    private TranscodeParameterConfig transcodeParameterConfig;
    @Value("${videostream.speed}")
    private String speed;
    @Value("${videostream.screenshot-format.frames}")
    private String frames;
    @Value("${videostream.screenshot-format.maxWidth}")
    private String maxWidth;
    @Value("${videostream.screenshot-format.gifStartTime}")
    private String gifStartTime;
    @Value("${videostream.screenshot-format.jpgStartTime}")
    private String jpgStartTime;
    @Value("${videostream.screenshot-format.burningTime}")
    private String burningTime;
    @Value("${videostream.screenshot-format.resultRate}")
    private String resultRate;
    @Autowired
    private ExtCommandExecutor extCommandExecutor;

    /**
     * 功能描述: 读取视频文件
     *
     * @param ffProbePath     ffprobe 解析视频安装路径
     * @param sourceVideoPath 源视频文件路径
     * @return
     * @date 2020/6/6 11:32
     */
    public static String readVideoFile(String ffProbePath, String sourceVideoPath) {
        // -v quiet:   静默工作,不输出版本、工作信息 ；
        //-print_format  json: 表示输出为JSON格式
        //-show_streams 参数的意义是输出每一个流的详细信息。
        //-show_format: 表示输出文件的格式信息，比如封装格式、码率、流数目、文件时长，文件大小等；
        // 解析后参数说明 https://blog.csdn.net/zhoubotong2012/article/details/102872950
        String ffprodcomand = ffProbePath + " -v quiet -print_format json -show_streams -show_format " + sourceVideoPath;
        LOGGER.info("读取视频参数ffprod命令: {}", ffprodcomand);
        StringBuffer sb = new StringBuffer();
        BufferedReader inBr = null;
        BufferedInputStream in = null;
        try {
            Runtime run = Runtime.getRuntime();
            Process p2 = run.exec(ffprodcomand);
            in = new BufferedInputStream(p2.getInputStream());
            inBr = new BufferedReader(new InputStreamReader(in));
            String lineStr;
            while ((lineStr = inBr.readLine()) != null) {
                sb.append(lineStr);
            }
            return sb.toString();
        } catch (Exception e) {
            LOGGER.error("读取视频参数失败: ", e);
            throw VideoException.FAILED_TO_READ_VIDEO_FILE;
        } finally {
            try {
                if (inBr != null) {
                    inBr.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                throw new RuntimeException("关闭失败");
            }
        }
    }

    /**
     * 获取七牛上传所需图片数据
     * pic 包括GIF图和jpg图 webp图等
     * @param picPath : 本地图片路径示例:/data/videostream/tmpfiles/img/451fc159d33210388f39395e1a855125/451fc159d33210388f39395e1a855125_jpg.jpg
     * @return
     */
    public byte[] getPicData(String picPath) {
        try {
            InputStream in = new FileInputStream(picPath);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
            byte[] b = new byte[1024];
            int n;
            while ((n = in.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            in.close();
            byte[] data = bos.toByteArray();
            bos.close();
            return data;
        } catch (Exception e) {
            LOGGER.error("图片上传七牛异常！:{}", e);
            throw VideoException.PIC_TRANSTODATA_FAILED;
        }
    }

    /**
     * 截图(截取gif图)
     *
     * @param screenShotParam
     * @return
     */
    public String getScreenShotGif(VideoScreenShotParam screenShotParam) {
        String palletPath = this.pallet(screenShotParam);

        List<String> commend = palettePreprocessing(screenShotParam);
        //调色板的路径
        commend.add("-i");
        commend.add(palletPath);
        //定义分辨率（默认按视频格式走）
        String width = "";
        if (screenShotParam.getVideoWidth() > Integer.valueOf(maxWidth)) {
            width = maxWidth;
        } else {
            width = screenShotParam.getVideoWidth().toString();
        }
        commend.add("-lavfi scale=" + width + ":-1:flags=lanczos[x];[x][1:v]paletteuse");
        //帧数
        commend.add("-r");
        commend.add(frames);
        //是否覆盖存在的文件
        commend.add("-y");
        //生成截图xxx.gif
        commend.add(screenShotParam.getScreenShotPath() + screenShotParam.getScreenShotName() + "/" + screenShotParam.getScreenShotName() + ".gif");
        StringBuffer order = new StringBuffer();
        for (int i = 0; i < commend.size(); i++) {
            order.append(commend.get(i) + " ");
        }

        //调用线程处理命令
        executeFFmpegCommand(order.toString());
        return screenShotParam.getScreenShotPath() + screenShotParam.getScreenShotName() + "/" + screenShotParam.getScreenShotName() + ".gif";
    }

    /**
     * 生成调色板
     *
     * @param screenShotParam
     * @return
     */
    public String pallet(VideoScreenShotParam screenShotParam) {

        List<String> commend = palettePreprocessing(screenShotParam);

        //定义分辨率宽度（默认按视频格式走）
        String width = "";
        if (screenShotParam.getVideoWidth() > Integer.valueOf(maxWidth)) {
            width = maxWidth;
        } else {
            width = screenShotParam.getVideoWidth().toString();
        }
        commend.add("-vf scale=" + width + ":-1:flags=lanczos,palettegen");
        //是否覆盖存在的文件
        commend.add("-y");
        //帧数
        commend.add("-r");
        commend.add(frames);
        //生成调色板
        commend.add(screenShotParam.getScreenShotPath() + screenShotParam.getScreenShotName() + "/" + screenShotParam.getScreenShotName() + "_pallet" + ".png");
        StringBuffer order = new StringBuffer();
        for (int i = 0; i < commend.size(); i++) {
            order.append(commend.get(i) + " ");
        }
        LOGGER.info("生成调色板命令:{}", order);
        //调用线程处理命令
        executeFFmpegCommand(order.toString());
        return screenShotParam.getScreenShotPath() + screenShotParam.getScreenShotName() + "/" + screenShotParam.getScreenShotName() + "_pallet" + ".png";
    }

    public List<String> palettePreprocessing(VideoScreenShotParam screenShotParam) {
        List<String> commend = new ArrayList<>();

        //指定ffmpeg工具的路径
        commend.add(screenShotParam.getFfmpegPath());
        //从xx秒开始
        commend.add("-ss");
        //1是代表第1秒的时候截图
        //前端传入起始时间，格式为"XX:XX:XX"
        if (null != screenShotParam.getStartTime()) {
            commend.add(screenShotParam.getStartTime());
        } else {
            commend.add(gifStartTime);
        }
        //截图时长
        commend.add("-t");
        //前端传入时长
        if (null != screenShotParam.getLength()) {
            commend.add(screenShotParam.getLength());
        } else {
            commend.add(burningTime);
        }
        //输入的原始文件
        commend.add("-i");
        //截图的视频路径
        commend.add(screenShotParam.getSourceVideoPath());

        return commend;
    }


    /**
     * 功能描述: 通过ffmpeg命令解析视频文件
     *
     * @param result ffmpeg命令
     * @return
     * @date 2020/6/8 0:29
     */
    public static VideoResolveResult videoResolve(String result) {
        VideoResolveResult videoResult = new VideoResolveResult();
        try {
            Map<String, Object> jsonMap = JSON.parseObject(result, HashMap.class);
            JSONArray streams = (JSONArray) jsonMap.get("streams");
            JSONObject json0 = (JSONObject) streams.get(0);
            JSONObject json1 = (JSONObject) streams.get(1);
            //视频json串
            JSONObject videoJson = json0.size() > json1.size() ? json0 : json1;
            //视频码率
            videoResult.setVdBitrate(Long.parseLong(videoJson.getString("bit_rate")));
            JSONObject tagsJson = (JSONObject) videoJson.get("tags");
//            videoResult.setRotate(tagsJson.getString("rotate"));
            if (Objects.nonNull(tagsJson) && "90".equals(tagsJson.getString("rotate"))) {
                //宽
                videoResult.setVideoWidth(videoJson.getInteger("height"));
                //高
                videoResult.setVideoHeight(videoJson.getInteger("width"));
            } else {
                //宽
                videoResult.setVideoWidth(videoJson.getInteger("width"));
                //高
                videoResult.setVideoHeight(videoJson.getInteger("height"));
            }

            //音频json串
            JSONObject audioJson = (JSONObject) streams.get(1);
            //音频码率
            videoResult.setAdBitrate(Long.parseLong(audioJson.getString("bit_rate")));

            JSONObject format = (JSONObject) jsonMap.get("format");
            //文件大小
            videoResult.setFileSize(Long.parseLong(format.getString("size")));
            //总码率
            videoResult.setTlBitrate(Long.parseLong(format.getString("bit_rate")));
            //总时长
            String durationStr = format.getString("duration");
            videoResult.setDuration(Double.valueOf(durationStr));
        } catch (Exception e) {
            LOGGER.error("解析视频信息失败:{}", e);
            throw VideoException.FAILED_TO_RESOLVE_VIDEO;
        }
        return videoResult;

    }

    public VideoResult analysisJson(String result) {
        LOGGER.info("视频参数解析结果result: {}", result);
        VideoResult videoResult = new VideoResult();
        try {
            Map<String, Object> jsonMap = JSON.parseObject(result, HashMap.class);
            JSONArray streams = (JSONArray) jsonMap.get("streams");
            JSONObject json0 = (JSONObject) streams.get(0);
            JSONObject json1 = (JSONObject) streams.get(1);
            //视频json串
            JSONObject videoJson = json0.size() > json1.size() ? json0 : json1;
            //视频码率
            videoResult.setVdBitrate(Long.parseLong(videoJson.getString("bit_rate")));
            JSONObject tagsJson = (JSONObject) videoJson.get("tags");
//            videoResult.setRotate(tagsJson.getString("rotate"));
            if (Objects.nonNull(tagsJson) && "90".equals(tagsJson.getString("rotate"))) {
                //宽
                videoResult.setVideoWidth(videoJson.getInteger("height"));
                //高
                videoResult.setVideoHeight(videoJson.getInteger("width"));
            } else {
                //宽
                videoResult.setVideoWidth(videoJson.getInteger("width"));
                //高
                videoResult.setVideoHeight(videoJson.getInteger("height"));
            }

            //音频json串
            JSONObject audioJson = (JSONObject) streams.get(1);
            //音频码率
            videoResult.setAdBitrate(Long.parseLong(audioJson.getString("bit_rate")));

            JSONObject format = (JSONObject) jsonMap.get("format");
            //文件大小
            videoResult.setFileSize(Long.parseLong(format.getString("size")));
            //总码率
            videoResult.setTlBitrate(Long.parseLong(format.getString("bit_rate")));
            //总时长
            String durationStr = format.getString("duration");
            videoResult.setDuration(Double.valueOf(durationStr));
        } catch (Exception e) {
            LOGGER.error("解析视频信息失败:{}", e);
            throw VideoException.FAILED_TO_PARSE_INFORMATION;
        }
        return videoResult;
    }

    public String getFfprod(String ffprobePath, String sourceVideoPath) {
        String ffprodcomand = ffprobePath + " -v quiet -print_format json -show_streams -show_format " + sourceVideoPath;
        LOGGER.info("读取视频参数ffprod命令: {}", ffprodcomand);
        StringBuffer sb = new StringBuffer();
        try {
            Runtime run = Runtime.getRuntime();
            Process p2 = run.exec(ffprodcomand);
            BufferedInputStream in = new BufferedInputStream(p2.getInputStream());
            BufferedReader inBr = new BufferedReader(new InputStreamReader(in));
            String lineStr;
            while ((lineStr = inBr.readLine()) != null) {
                sb.append(lineStr);
            }
            inBr.close();
            in.close();
            LOGGER.info("视频解析参数result：{}", sb.toString());
        } catch (Exception e) {
            LOGGER.error("读取视频参数失败！", e);
            throw VideoException.FAILED_TO_READ_INFORMATION;
        }
        return sb.toString();
    }

    /**
     * 解析图片json
     *
     * @param result
     * @return
     */
    public PicResult PicAnalysisJson(String result) {
        PicResult picResult = new PicResult();
        try {
            Map<String, Object> jsonMap = JSON.parseObject(result, HashMap.class);
            JSONArray streams = (JSONArray) jsonMap.get("streams");
            JSONObject picJson = (JSONObject) streams.get(0);

            //高
            picResult.setHeight(picJson.getInteger("height"));
            //宽
            picResult.setWidth(picJson.getInteger("width"));

            JSONObject format = (JSONObject) jsonMap.get("format");
            //文件大小
            picResult.setFileSize(Double.parseDouble(format.getString("size")) / 1024 / 1024);
        } catch (Exception e) {
            LOGGER.error("解析视频信息失败:{}", e);
            throw VideoException.FAILED_TO_PARSE_INFORMATION;
        }
        return picResult;
    }

    /**
     * @Description 视频转码
     * @author onnoA
     * @date 2021/7/6 16:39
     * @param videoTranscodeParam
     * @param videoResult
     * @return java.util.Map<java.lang.String,java.lang.StringBuilder>
     */
    public Map<String, StringBuilder> getDifferDefinitionType(VideoTranscodeParam videoTranscodeParam, VideoResult videoResult) {
        Map<String, StringBuilder> map = new HashMap<>();
        Integer videoHeight = videoResult.getVideoHeight();
        Integer videoWidth = videoResult.getVideoWidth();
        if (videoHeight > videoWidth || (videoHeight < videoWidth && "90".equals(videoResult.getRotate()))) {
            /**
             * 竖屏处理
             * 1.高大于宽
             * 2.高小于宽并且旋转角度==90
             */
            if (videoWidth >= 1080) {
                map.putAll(this.transTo1080x1920(videoTranscodeParam));
                map.putAll(this.transTo720x1280(videoTranscodeParam));
                map.putAll(this.transTo540x960(videoTranscodeParam));
                map.putAll(this.transTo360x640(videoTranscodeParam));
            } else if (videoWidth >= 720) {
                map.putAll(this.transTo720x1280(videoTranscodeParam));
                map.putAll(this.transTo540x960(videoTranscodeParam));
                map.putAll(this.transTo360x640(videoTranscodeParam));
            } else if (videoWidth >= 540) {
                map.putAll(this.transTo540x960(videoTranscodeParam));
                map.putAll(this.transTo360x640(videoTranscodeParam));
            } else {
                map.putAll(this.transTo360x640(videoTranscodeParam));
            }
        } else {
            //宽频处理
            if (videoHeight >= 1080) {
                map.putAll(this.transTo1920x1080(videoTranscodeParam));
                map.putAll(this.transTo1280x720(videoTranscodeParam));
                map.putAll(this.transTo960x540(videoTranscodeParam));
                map.putAll(this.transTo640x360(videoTranscodeParam));
            } else if (videoHeight >= 720) {
                map.putAll(this.transTo1280x720(videoTranscodeParam));
                map.putAll(this.transTo960x540(videoTranscodeParam));
                map.putAll(this.transTo640x360(videoTranscodeParam));
            } else if (videoHeight >= 540) {
                map.putAll(this.transTo960x540(videoTranscodeParam));
                map.putAll(this.transTo640x360(videoTranscodeParam));
            } else {
                map.putAll(this.transTo640x360(videoTranscodeParam));
            }
        }
        return map;
    }

    /**
     * 转成1920x1080P
     *
     * @param videoTranscodeParam
     * @return
     */
    private Map<String, StringBuilder> transTo1920x1080(VideoTranscodeParam videoTranscodeParam) {
        return this.transCode(videoTranscodeParam, 1080, 1920);
    }

    /**
     * 转成1080x1920P
     *
     * @param videoTranscodeParam
     * @return
     */
    private Map<String, StringBuilder> transTo1080x1920(VideoTranscodeParam videoTranscodeParam) {
        return this.transCode(videoTranscodeParam, 1920, 1080);
    }

    /**
     * 转成1280x720P
     *
     * @param videoTranscodeParam
     * @return
     */
    private Map<String, StringBuilder> transTo1280x720(VideoTranscodeParam videoTranscodeParam) {
        return this.transCode(videoTranscodeParam, 720, 1280);
    }

    /**
     * 转成720Px1280
     *
     * @param videoTranscodeParam
     * @return
     */
    private Map<String, StringBuilder> transTo720x1280(VideoTranscodeParam videoTranscodeParam) {
        return this.transCode(videoTranscodeParam, 1280, 720);
    }

    /**
     * 转成854x480
     *
     * @param videoTranscodeParam
     * @return
     */
    private Map<String, StringBuilder> transTo960x540(VideoTranscodeParam videoTranscodeParam) {
        return this.transCode(videoTranscodeParam, 480, 854);
    }

    /**
     * 转成480x854
     *
     * @param videoTranscodeParam
     * @return
     */
    private Map<String, StringBuilder> transTo540x960(VideoTranscodeParam videoTranscodeParam) {
        return this.transCode(videoTranscodeParam, 854, 480);
    }

    /**
     * 转成640x360P
     *
     * @param videoTranscodeParam
     * @return
     */
    private Map<String, StringBuilder> transTo640x360(VideoTranscodeParam videoTranscodeParam) {
        return this.transCode(videoTranscodeParam, 360, 640);
    }

    /**
     * 转成360x640P
     *
     * @param videoTranscodeParam
     * @return
     */
    private Map<String, StringBuilder> transTo360x640(VideoTranscodeParam videoTranscodeParam) {
        return this.transCode(videoTranscodeParam, 640, 360);
    }


    /**
     * @Description 视频转码
     * @author onnoA
     * @date 2021/7/6 16:45
     * @param videoTranscodeParam
     * @param height
     * @param width
     * @return java.util.Map<java.lang.String,java.lang.StringBuilder>
     */
    private Map<String, StringBuilder> transCode(VideoTranscodeParam videoTranscodeParam, Integer height, Integer width) {
        /**
         * -vsync：用于控制改变帧率时，丢帧的方法；
         * -c:v：设置视频编码器
         * -b:v：设置视频码率
         * -r：设置帧率
         * -s：设置分辨率
         * -aspect： 设置画面比例
         * -c:a：设置音频编码器
         * -b:a：设置音频码率
         * -ar：设置音频采样率
         * -t：设置输出文件播放时长
         * -map：映射码流，如果后面的参数是视频，就是视频码流；如果后面的参数是音频，就是音频码流
         * -ac：设置音频声道数
         * -y：默认覆盖输出文件
         * -qdiff：编码质量差（根据经验调）
         * -qcomp：量化曲线压缩参数（根据经验调）
         * -subq：Sub-pixel 运动估算方法
         * -preset：编码预设值（根据经验调）
         * -me_range：运动搜索最大范围（根据经验调）
         * -coder：设置熵编码器
         * -me_method：设置运动估算方法
         * -refs：设置运动补偿的参考帧数量
         * -bf：设置非 B 帧之间的最大 B 帧数量  cmd.exe /c
         * -movflags faststart：将头信息挪到文件头
         */
        Map<String, Map<String, String>> map = transcodeParameterConfig.getParamMap();
        String key = width + "x" + height + "p";
        Map<String, String> paramMap = map.get(key);
        //完整版命令
        StringBuilder order = new StringBuilder();
        order.append(videoTranscodeParam.getFfmpegPath() + " ");
        order.append("-analyzeduration 100000000" + " ");
        order.append("-i" + " " + videoTranscodeParam.getSourceVideoPath() + " ");
        order.append("-map 0:0 -vsync 1 -c:v libx264 -r 25 -force_key_frames " + "expr:gte(t,n_forced*2)" + " -b:v " + paramMap.get(TranscodeConstant.BV) + " ");
        order.append("-s" + " " + paramMap.get(TranscodeConstant.S) + " " + "-aspect " + paramMap.get(TranscodeConstant.ASPECT) + " ");
        String partOrder = "-map 0:1 -c:a aac -strict -2 -b:a " + paramMap.get(TranscodeConstant.BA) + " -ar 44100 -ac 1 -qdiff 4 -qcomp 0.6 -subq 9 -preset " + speed + " -me_range 32 -coder ac -me_method umh -pix_fmt yuv420p -keyint_min 15 -refs 4 -bf 4 -movflags +faststart -y ";
        order.append(partOrder);
        //缓存视频转码输出的临时视频文件存储路径
        String output = videoTranscodeParam.getOutputVideoPath() + videoTranscodeParam.getUuid() + "/" + videoTranscodeParam.getUuid() + "_" + width.toString() + "x" + height.toString() + "p" + ".mp4";
        order.append(output);
        LOGGER.info("转码命令:{}", order);

        Process p = null;
        //调用线程处理命令
        try {
            LOGGER.info("-------------转码执行前------------");
            Runtime run = Runtime.getRuntime();
            p = run.exec(order.toString());
            new DealProcessSream(p.getInputStream()).start();
            new DealProcessSream(p.getErrorStream()).start();
            p.waitFor();
            LOGGER.info("-------------转码完成------------");
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
            throw VideoException.VIDEO_TRANSCODE_FAILED;
        } finally {
            p.destroy();
        }
        Map<String, StringBuilder> resultMap = new HashMap<>();
        resultMap.put(output, order);
        return resultMap;
    }

    public String getScreenShotJpg(VideoScreenShotParam screenShotParam) {

        List<String> command = new ArrayList<String>();

        //指定ffmpeg工具的路径
        command.add(screenShotParam.getFfmpegPath());

        //输入的原始文件
        command.add("-i");
        //截图的视频路径
        command.add(screenShotParam.getSourceVideoPath());
        //设置输出格式
        command.add("-f image2");
        //从xx秒开始
        command.add("-ss");
        //1是代表第1秒的时候截图
        //前端传入起始时间，格式为"XX:XX:XX"
        if (null != screenShotParam.getStartTime()) {
            command.add(screenShotParam.getStartTime());
        } else {
            command.add(jpgStartTime);
        }
        //开始时间后的第一帧
        command.add("-vframes 1");
        //定义分辨率（默认按视频格式走）
        String width = "";
        if (screenShotParam.getVideoWidth() > Integer.valueOf(maxWidth)) {
            width = maxWidth;
        } else {
            width = screenShotParam.getVideoWidth().toString();
        }
        command.add("-vf scale=" + width + ":-1 -q:v");
        command.add(resultRate);
        //是否覆盖存在的文件
        command.add("-y");
        //生成截图xxx.jpg
        command.add(screenShotParam.getScreenShotPath() + screenShotParam.getScreenShotName() + "/" + screenShotParam.getScreenShotName() + "_jpg.jpg");
        StringBuffer order = new StringBuffer();
        for (int i = 0; i < command.size(); i++) {
            order.append(command.get(i) + " ");
        }
        LOGGER.info("截图jpg命令:{}", order);

        //调用线程处理命令
        executeFFmpegCommand(order.toString());

        return screenShotParam.getScreenShotPath() + screenShotParam.getScreenShotName() + "/" + screenShotParam.getScreenShotName() + "_jpg.jpg";
    }

    public void executeFFmpegCommand(String commandStr) {
        //调用线程处理命令
        try {
            LOGGER.debug("准备执行的ffmpeg命令:{}", commandStr);
            LOGGER.debug("-------------执行ffmpeg命令前------------");
            ExtCommandExecutor.ExecResult execResult = extCommandExecutor.execCmd(commandStr);
            LOGGER.debug("----------------------execCode is :{}---------------------", execResult.getExitCode());
            if (execResult.getExitCode() != 0) {
                LOGGER.error("执行结果打印:{}-----------------------异常命令:{}",  execResult.getPrintOutLines(),commandStr);
                throw VideoException.VIDEO_GETSCREENTSHOT_FAILED;
            } else {
                LOGGER.debug("命令执行结果打印:{}", execResult.getPrintOutLines());
            }
            LOGGER.debug("-------------执行ffmpeg命令完成------------");

        } catch (Exception e) {
            LOGGER.error("执行ffmpeg命令未知异常！", e);
            throw VideoException.VIDEO_GETSCREENTSHOT_FAILED;
        }
    }


}
