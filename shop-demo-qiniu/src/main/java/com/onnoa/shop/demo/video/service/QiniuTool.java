package com.onnoa.shop.demo.video.service;

import com.onnoa.shop.common.utils.HttpUtils;
import com.onnoa.shop.common.utils.JsonUtil;
import com.onnoa.shop.demo.video.cache.QiniuCache;
import com.onnoa.shop.demo.video.qiniu.dto.ImageInfo;
import com.onnoa.shop.demo.video.qiniu.dto.QiniuRep;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.qiniu.util.UrlSafeBase64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 功能描述: 七牛工具类
 *
 * @date 2020/6/12 16:06
 */
public class QiniuTool {

    private static final Logger logger = LoggerFactory.getLogger(QiniuTool.class);

    private static final UploadManager UPLOAD_MANAGER = new UploadManager(new Configuration(Zone.autoZone()));

    /**
     * 12*30*24*60*60 即12个月
     */
    private static final long DEFAULT_EXPIRES = 31104000;

    /**
     * 比url过期时间少1000秒,防止缓存中的url比缓存先失效
     */
    private static final int DEFAULT_KEY_EXPIRES = 31103000;

    /**
     * 七牛上传token的默认失效时间
     */
    private static final long DEFAULT_TOKEN_EXPIRES = 3600;

    /**
     * 默认bucket
     */
    private String bucketName;

    /**
     * 域名
     */
    private String domain;

    /**
     * 认证凭证
     */
    private Auth auth;

    public QiniuTool(String bucketName, String domain, String accesskey, String secretKey) {
        this.bucketName = bucketName;
        this.domain = domain;
        this.auth = Auth.create(accesskey, secretKey);
    }

    /**
     * 获取上传token,默认有效时长DEFAULT_TOKEN_EXPIRES秒
     *
     * @return
     */
    public String getUpToken() {
        return getUpToken(bucketName, null, DEFAULT_TOKEN_EXPIRES);
    }

    /**
     * 获取指定bucket的覆盖key上传token,默认有效时长DEFAULT_TOKEN_EXPIRES秒
     *
     * @param bucket
     * @param key
     * @return
     */
    public String getUpToken(String bucket, String key) {
        return getUpToken(bucket, key, DEFAULT_TOKEN_EXPIRES);
    }

    /**
     * 获取指定bucket的覆盖key上传token，指定有效时长
     *
     * @param bucket
     * @param key
     * @param expires
     * @return
     */
    public String getUpToken(String bucket, String key, long expires) {
        return auth.uploadToken(StringUtils.isEmpty(bucket) ? bucketName : bucket, key, expires, new StringMap().putNotEmpty("returnBody",
                "{\"key\": $(key), \"hash\": $(etag), \"width\": $(imageInfo.width), \"height\": $(imageInfo.height),\"fsize\":$(fsize)}"));
    }

    /**
     * 文件上传，并获取上传key
     *
     * @param data 二进制数据
     * @return 键
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
     */
    public Map<String, Object> upload(byte[] data, String bucket, String key) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            String upToken = getUpToken(bucket, key);
            Response res = UPLOAD_MANAGER.put(data, key, upToken);
            QiniuRep ret = res.jsonToObject(QiniuRep.class);
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
            logger.error(r.toString());
            try {
                //响应的文本信息
                logger.error(r.bodyString());
            } catch (QiniuException e1) {
            }
            return result;
        }
    }

    /**
     * 获取下载路径
     */
    public String getDownloadUrl(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        return getUrlBySuffix(key, null);
    }

    /**
     * 获取图片详细信息(url、格式、宽、高、大小、色彩模型)
     */
    public ImageInfo getImageInfo(String key) {
        if (StringUtils.isBlank(key)) {
            return new ImageInfo(null, 1, 1);
        }
        ImageInfo imageInfo = (ImageInfo) QiniuCache.QINIU_IMAGEINFO_SESSION.get(key);
        if (imageInfo != null) {
            return imageInfo;
        }
        String url = getDownloadUrl(key);
        try {
            String jsonResult = HttpUtils.doGet(getImageDownloadUrl(key, null, null), null);
            imageInfo = JsonUtil.json2Obj(jsonResult, ImageInfo.class);
            if (imageInfo != null) {
                imageInfo.setUrl(url);
                QiniuCache.QINIU_IMAGEINFO_SESSION.set(key, imageInfo, DEFAULT_KEY_EXPIRES);
            } else {
                throw new RuntimeException("获取图片信息失败");
            }
        } catch (Exception e) {
            logger.error("获取图片信息失败", e);
            imageInfo = new ImageInfo(url, 1, 1);
        }
        return imageInfo;
    }

    /**
     * 获取等比例压缩后的图片详细信息(url、格式、宽、高、大小、色彩模型)，宽高信息只能拿到原图的宽高
     */
    public ImageInfo getImageInfo(String key, Integer width, Integer height) {
        if (StringUtils.isBlank(key)) {
            return new ImageInfo(null, 1, 1);
        }
        String redisKey = key + ":width" + width + "height" + height;
        ImageInfo imageInfo = (ImageInfo) QiniuCache.QINIU_IMAGEINFO_SESSION.get(redisKey);
        if (imageInfo != null) {
            return imageInfo;
        }
        String url = getDownloadUrl(key, width, height);
        try {
            String jsonResult = HttpUtils.doGet(getImageDownloadUrl(key, width, height), null);
            imageInfo = JsonUtil.json2Obj(jsonResult, ImageInfo.class);
            if (imageInfo != null) {
                imageInfo.setUrl(url);
                QiniuCache.QINIU_IMAGEINFO_SESSION.set(redisKey, imageInfo, DEFAULT_KEY_EXPIRES);
            } else {
                throw new RuntimeException("获取图片信息失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取图片信息失败", e);
            imageInfo = new ImageInfo(url, 1, 1);
        }
        return imageInfo;
    }

    /**
     * 获取下载路径，等比例压缩图片,如果传入的宽或高大于原图，则不会扩大，只会按原图压缩
     * 如果传入的宽为空 高不为空，则压缩后的图片高为传入的高的值，宽按比例压缩
     * 如果传入的高为空 宽不为空，则压缩后的图片宽为传入的宽的值，高按比例压缩
     * 如果传入的宽和高都不为空， 则按宽高中值更小的那个压缩，另一边等比例压缩
     * 如果传入的宽和高都为空，则返回原图
     *
     * @return
     */
    public String getDownloadUrl(String key, Integer width, Integer height) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        if (width == null && height == null) {
            return getDownloadUrl(key);
        }
        StringBuilder suffixBuf = new StringBuilder("?imageView2/2");
        if (width != null) {
            suffixBuf.append("/w/").append(width.intValue());
        }
        if (height != null) {
            suffixBuf.append("/h/").append(height.intValue());
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
        StringBuilder suffixBuf = new StringBuilder("?imageView2/2");
        if (width != null) {
            suffixBuf.append("/w/").append(width.intValue());
        }
        if (height != null) {
            suffixBuf.append("/h/").append(height.intValue());
        }
        suffixBuf.append("|watermark/1/image/").append(UrlSafeBase64.encodeToString(watermarkUrl)).append("/dissolve/").append(dissolve).append("/gravity/").append(gravity);
        if (scale != null && scale > 0 && scale <= 1) {
            suffixBuf.append("/ws/").append(scale);
        }
        String suffix = suffixBuf.toString();
        return getUrlBySuffix(key, suffix);
    }

    /**
     * 私有方法，类内部使用
     * 获取带 imageInfo 的下载路径，和getDownloadUrl的区别 就是他会在返回的url中带上 imageInfo参数
     *
     * @return
     */
    private String getImageDownloadUrl(String key, Integer width, Integer height) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        if (width == null && height == null) {
            return getDownloadUrl(key + "?imageInfo");
        }
        StringBuilder suffixBuf = new StringBuilder("?imageView2/2");
        if (width != null) {
            suffixBuf.append("/w/").append(width.intValue());
        }
        if (height != null) {
            suffixBuf.append("/h/").append(height.intValue());
        }
        String suffix = suffixBuf.append("&imageInfo").toString();
        return getUrlBySuffix(key, suffix);
    }

    /**
     * 私有方法，类内部使用
     * 根据后缀获取最终的url
     */
    private String getUrlBySuffix(String key, String suffix) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        String redisKey = key;
        if (StringUtils.isNotBlank(suffix)) {
            redisKey = key + suffix;
        }
        String url = (String) QiniuCache.QINIU_URL_SESSION.get(redisKey);
        if (StringUtils.isNotBlank(url)) {
            return url;
        }
        url = auth.privateDownloadUrl(domain + "/" + redisKey, DEFAULT_EXPIRES);
        QiniuCache.QINIU_URL_SESSION.set(redisKey, url, DEFAULT_KEY_EXPIRES);
        return url;
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
     */
    public String getTailoredDownloadUrl(String key, Integer width, Integer height, boolean isCompress) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        if (width == null && height == null) {
            return getDownloadUrl(key);
        }
        StringBuilder suffixBuf = new StringBuilder("?imageView2/1");
        if (width != null) {
            suffixBuf.append("/w/").append(width.intValue());
        }
        if (height != null) {
            suffixBuf.append("/h/").append(height.intValue());
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

    private void buildSuccessInfo(QiniuRep ret, Map<String, Object> result) {
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

}
