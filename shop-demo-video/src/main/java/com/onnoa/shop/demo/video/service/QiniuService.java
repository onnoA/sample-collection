package com.onnoa.shop.demo.video.service;

import com.onnoa.shop.demo.video.config.QiniuConfig;
import com.onnoa.shop.demo.video.dto.ImageInfo;
import com.onnoa.shop.demo.video.dto.QiniuFileDto;
import com.onnoa.shop.demo.video.dto.QiniuTokenDto;
import com.onnoa.shop.demo.video.utils.HttpClientUtils;
import com.onnoa.shop.demo.video.utils.JsonUtil;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.qiniu.util.UrlSafeBase64;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


/**
 * @Description 七牛工具类
 * @author onnoA
 * @date 2021/7/6 23:17
 */
@Slf4j
@Component
public class QiniuService {


    private final UploadManager uploadManager = new UploadManager(new Configuration(Zone.autoZone()));

    private final long DEFAULT_EXPIRES = 311040000;// 12*30*24*60*60*10 即10年

    private final int DEFAULT_KEY_EXPIRES = 31103000;// 比url过期时间少1000秒,防止缓存中的url比缓存先失效

    private final long DEFAULT_TOKEN_EXPIRES = 3600;// 七牛上传token的默认失效时间

    /**
     * 获取上传token,默认有效时长DEFAULT_TOKEN_EXPIRES秒
     *
     * @return
     * @author 陈章伟
     * @time 2017年10月9日上午11:54:23
     */
    public QiniuTokenDto getUpToken() {
        return getUpToken(QiniuConfig.bucketname, null, DEFAULT_TOKEN_EXPIRES);
    }

    /**
     * 获取指定bucket的覆盖key上传token,默认有效时长DEFAULT_TOKEN_EXPIRES秒
     *
     * @param bucket
     * @param key
     * @return
     * @author 陈章伟
     * @time 2017年10月9日上午11:56:25
     */
    private QiniuTokenDto getUpToken(String bucket, String key) {
        return getUpToken(bucket, key, DEFAULT_TOKEN_EXPIRES);
    }

    /**
     * 获取指定bucket的覆盖key上传token，指定有效时长
     *
     * @param bucket
     * @param key
     * @param expires
     * @return
     * @author 陈章伟
     * @time 2017年10月9日下午5:24:20
     */
    private QiniuTokenDto getUpToken(String bucket, String key, long expires) {
        QiniuTokenDto qiniuTokenDto = new QiniuTokenDto();
        /**
         * 流媒体处理相关(视频截图)，未开通权限
         StringMap putPolicy = new StringMap();
         String vframeJpgFop = String.format("vframe/jpg/offset/1");
         //将多个数据处理指令拼接起来
         String persistentOpfs = StringUtils.join(new String[]{
         vframeJpgFop
         }, ";");
         putPolicy.put("persistentOps", persistentOpfs);
         //数据处理队列名称，必填
         putPolicy.put("persistentPipeline", "devtest");
         //数据处理完成结果通知地址
         putPolicy.put("persistentNotifyUrl", "http://fake.com/qiniu/notify");
         qiniuTokenDto.setCommandToken(auth.uploadToken(StringUtils.isEmpty(bucket) ? defaultBucket : bucket, key, expires, putPolicy));
         */
//        qiniuTokenDto.setUpToken(auth().uploadToken(StringUtils.isEmpty(bucket) ? QiniuConfig.bucketname : bucket, key, expires, new StringMap().putNotEmpty("returnBody",
//                "{\"key\": $(key), \"hash\": $(etag), \"width\": $(imageInfo.width), \"height\": $(imageInfo.height),\"fsize\":$(fsize)}")));
//        qiniuTokenDto.setAccessKey(QiniuConfig.accessKey);
        return qiniuTokenDto;
    }

    /**
     * 文件上传，并获取上传key
     *
     * @param data
     * @return
     */
    public String uploadAndGetKey(byte[] data) {
        Map<String, Object> qiniuResult = upload(data, null, null);
        return getUploadBackKey(qiniuResult);
    }

    /**
     * 文件上传，并获取上传key
     *
     * @param data
     * @param bucket
     * @param key
     * @return
     */
    public String uploadAndGetKey(byte[] data, String bucket, String key) {
        Map<String, Object> qiniuResult = upload(data, bucket, key);
        return getUploadBackKey(qiniuResult);
    }

    /**
     * 上传文件到七牛
     *
     * @param bucket 可以为null
     * @param key    可以为null
     * @return
     * @author 陈章伟
     * @time 2017年9月30日下午4:57:22
     */
    public Map<String, Object> upload(byte[] data, String bucket, String key) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            QiniuTokenDto qiniuTokenDto = getUpToken(bucket, key);
            String upToken = qiniuTokenDto.getUpToken();
            Response res = uploadManager.put(data, key, upToken);
            QiniuFileDto ret = res.jsonToObject(QiniuFileDto.class);
            if (res.isOK()) {
                buildSuccessInfo(ret, result);
                return result;
            } else {
                buildErrorInfo(res, result);
                return result;
            }
        } catch (QiniuException e) {
            Response r = e.response;
            buildErrorInfo(r, result);
            //请求失败时简单状态信息
            log.error(r.toString());
            try {
                //响应的文本信息
                log.error(r.bodyString());
            } catch (QiniuException e1) {
            }
            return result;
        }
    }

    /**
     * 获取下载路径
     *
     * @author 陈章伟
     * @time 2017年9月30日下午4:44:48
     */
    public String getDownloadUrl(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        return getUrlBySuffix(key, null);
    }


    /**
     * 获取下载路径，等比例压缩图片,如果传入的宽或高大于原图，则不会扩大，只会按原图压缩
     * 如果传入的宽为空 高不为空，则压缩后的图片高为传入的高的值，宽按比例压缩
     * 如果传入的高为空 宽不为空，则压缩后的图片宽为传入的宽的值，高按比例压缩
     * 如果传入的宽和高都不为空， 则按宽高中值更小的那个压缩，另一边等比例压缩
     * 如果传入的宽和高都为空，则返回原图
     *
     * @return
     * @author 陈章伟
     * @time 2017年9月30日下午5:44:48
     */
    public String getDownloadUrl(String key, Integer width, Integer height) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        if (width == null && height == null) {
            return getDownloadUrl(key);
        }
        StringBuffer suffixBuf = new StringBuffer("?imageView2/2");
        if (width != null) {
            suffixBuf.append("/w/" + width.intValue());
        }
        if (height != null) {
            suffixBuf.append("/h/" + height.intValue());
        }
        String suffix = suffixBuf.toString();
        return getUrlBySuffix(key, suffix);
    }

    /**
     * 获取图片url，带水印
     *
     * @param key          要加水印的图片key
     * @param watermarkUrl 水印图片的url，传空则返回原图url
     * @param dissolve     水印不透明度，传值范围 1--100，传空则默认为100
     * @param gravity      水印的位置，传空则默认为SouthEast（右下角），参数选择如下
     *                     NorthWest     |     North      |     NorthEast
     *                     --------------+----------------+--------------
     *                     West          |     Center     |          East
     *                     --------------+----------------+--------------
     *                     SouthWest     |     South      |     SouthEast
     * @param scale        水印图片自适应原图的短边比例
     * @author 陈章伟
     * @time 2018年2月4日下午5:28:01
     */
    public String getUrlWithWatermark(String key, String watermarkUrl, String gravity, Integer dissolve, Double scale) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        if (StringUtils.isBlank(watermarkUrl)) {
            return getDownloadUrl(key);
        }
        if (dissolve == null || dissolve <= 0 || dissolve > 100) {
            dissolve = 100;
        }
        if (StringUtils.isBlank(gravity)) {
            gravity = "SouthEast";
        }
        String suffix = "?imageView2/0|watermark/1/image/" + UrlSafeBase64.encodeToString(watermarkUrl) + "/dissolve/" + dissolve + "/gravity/" + gravity;
        if (scale != null && scale > 0 && scale <= 1) {
            suffix += "/ws/" + scale;
        }
        return getUrlBySuffix(key, suffix);
    }

    /**
     * 获取压缩后的图片url，带水印
     *
     * @param key          要加水印的图片key
     * @param watermarkUrl 水印图片的url，传空则返回原图url
     * @param dissolve     水印不透明度，传值范围 1--100，传空则默认为100
     * @param gravity      水印的位置，传空则默认为SouthEast（右下角）
     * @param scale        水印图片自适应原图的短边比例，ws的取值范围为0-1。具体是指水印图片保持原比例，并短边缩放到原图短边＊ws。例如：原图大小为250x250，水印图片大小为91x61，如果ws=1，那么最终水印图片的大小为：372x250
     * @param width        压缩后图片的宽
     * @param height       压缩后图片的高
     * @author 陈章伟
     * @time 2018年2月4日下午5:28:01
     */
    public String getUrlWithWatermark(String key, String watermarkUrl, String gravity, Integer dissolve, Double scale, Integer width, Integer height) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        if (StringUtils.isBlank(watermarkUrl)) {
            return getDownloadUrl(key);
        }
        if (width == null && height == null) {
            return getUrlWithWatermark(key, watermarkUrl, gravity, dissolve, scale);
        }
        if (dissolve == null || dissolve <= 0 || dissolve > 100) {
            dissolve = 100;
        }
        if (StringUtils.isBlank(gravity)) {
            gravity = "SouthEast";
        }
        StringBuffer suffixBuf = new StringBuffer("?imageView2/2");
        if (width != null) {
            suffixBuf.append("/w/" + width.intValue());
        }
        if (height != null) {
            suffixBuf.append("/h/" + height.intValue());
        }
        suffixBuf.append("|watermark/1/image/" + UrlSafeBase64.encodeToString(watermarkUrl) + "/dissolve/" + dissolve + "/gravity/" + gravity);
        if (scale != null && scale > 0 && scale <= 1) {
            suffixBuf.append("/ws/" + scale);
        }
        String suffix = suffixBuf.toString();
        return getUrlBySuffix(key, suffix);
    }

    /**
     * 获取图片详细信息(url、格式、宽、高、大小、色彩模型)
     */
    public ImageInfo getImageInfo(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }

        ImageInfo imageInfo = null;
        String url = getDownloadUrl(key);
        try {
            String jsonResult = HttpClientUtils.doGet(getImageDownloadUrl(key, null, null), null, null);
            imageInfo = JsonUtil.json2Obj(jsonResult, ImageInfo.class);
            if (imageInfo != null) {
                imageInfo.setUrl(url);
            } else {
                throw new RuntimeException("获取图片信息失败");
            }
        } catch (Exception e) {
            log.error("获取图片信息失败,图片key为：{}", key);
        }
        return imageInfo;
    }

    /**
     * 私有方法，类内部使用
     * 获取带 imageInfo 的下载路径，和getDownloadUrl的区别 就是他会在返回的url中带上 imageInfo参数
     *
     * @return
     * @author 陈章伟
     * @time 2017年9月30日下午5:44:48
     */
    private String getImageDownloadUrl(String key, Integer width, Integer height) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        if (width == null && height == null) {
            return getDownloadUrl(key + "?imageInfo");
        }
        StringBuffer suffixBuf = new StringBuffer("?imageView2/2");
        if (width != null) {
            suffixBuf.append("/w/" + width.intValue());
        }
        if (height != null) {
            suffixBuf.append("/h/" + height.intValue());
        }
        String suffix = suffixBuf.append("&imageInfo").toString();
        return getUrlBySuffix(key, suffix);
    }

    /**
     * 私有方法，类内部使用
     * 根据后缀获取最终的url
     *
     * @author 陈章伟
     * @time 2018年2月5日下午3:31:06
     */
    private String getUrlBySuffix(String key, String suffix) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        String redisKey = key;
        if (StringUtils.isNotBlank(suffix)) {
            redisKey = key + suffix;
        }
//        return auth().privateDownloadUrl(QiniuConfig.domain + "/" + redisKey, DEFAULT_EXPIRES);
        return "";

    }

    /**
     * 保持原图宽高比例缩小，宽和高缩至完全覆盖指定宽高区域的最小图片，然后居中裁剪
     * 2018.6.22 新增
     *
     * @return
     */
    public String getTailoredDownloadUrl(String key, Integer width, Integer height) {
        return getTailoredDownloadUrl(key, width, height, true);
    }

    /**
     * 获取裁剪后的下载路径，从图片中心向四周裁剪，如果传入的宽或高大于原图，则不会扩大，只会按原图裁剪
     * 如果宽和高都为空，则返回原图
     * 如果传入的宽和高有一个不为空，则裁剪后的图片 宽和高都是 不为空那个入参的值
     * 如果传入的宽和高都不为空， 则裁剪后的图片 宽和高 就是 对应的入参的值
     *
     * @param isCompress 是否压缩
     * @return
     * @author 陈章伟
     * @time 2017年9月30日下午5:44:48
     */
    public String getTailoredDownloadUrl(String key, Integer width, Integer height, boolean isCompress) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        if (width == null && height == null) {
            return getDownloadUrl(key);
        }
        StringBuffer suffixBuf = new StringBuffer("?imageView2/1");
        if (width != null) {
            suffixBuf.append("/w/" + width.intValue());
        }
        if (height != null) {
            suffixBuf.append("/h/" + height.intValue());
        }
        if (isCompress) {
            suffixBuf.append("/q/75|imageslim");
        }
        String suffix = suffixBuf.toString();
        return getUrlBySuffix(key, suffix);
    }

    /**
     * 根据七牛返回值，获取返回key
     *
     * @param qiniuResult
     * @return
     */
    public String getUploadBackKey(Map<String, Object> qiniuResult) {
        int code = (Integer) qiniuResult.get("code");
        if (code != 0) {
            return null;
        }
        return (String) qiniuResult.get("key");
    }

    private void buildSuccessInfo(QiniuFileDto ret, Map<String, Object> result) {
        result.put("code", 0);
        result.put("msg", "");
        result.put("fsize", ret.getFsize());
        result.put("key", ret.getKey());
        result.put("hash", ret.getHash());
        result.put("width", ret.getWidth());
        result.put("height", ret.getHeight());
    }

    private void buildErrorInfo(Response res, Map<String, Object> result) {
        result.put("code", 1);
        try {
            result.put("msg", StringUtils.isBlank(res.bodyString()) ? "文件上传下载失败，错误码:" + res.statusCode : res.error);
        } catch (QiniuException e) {
            result.put("msg", "系统错误");
        }
    }

    /**
     * 获取用户对象
     *
     * @return
     */
    private Auth auth() {
        return Auth.create(QiniuConfig.accessKey, QiniuConfig.secretKey);
    }

    /**
     * 根据Key 设置转换为webp格式 同时设置宽度
     *
     * @param key
     * @param width
     * @return
     */
    public String getWebPUrlByOriginKey(String key, Integer width) {
        return this.getUrlBySuffix(key, "?imageMogr2/format/webp/thumbnail/" + width);
    }

}
