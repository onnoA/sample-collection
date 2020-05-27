package com.onnoa.shop.demo.video.service;

import com.onnoa.shop.demo.video.qiniu.dto.ImageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class QiniuService {

    @Autowired
    private QiniuTool qiniuTool;

    /**
     * 功能描述: 获取七牛的上传token
     *
     * @param bucket  空间名称
     * @param key     键
     * @param expires 过期时间
     * @return
     * @date 2020/6/14 13:03
     */
    public String getUpToken(String bucket, String key, String expires) {
        bucket = StringUtils.isBlank(bucket) ? null : bucket;
        key = StringUtils.isBlank(key) ? null : key;
        expires = StringUtils.isBlank(expires) ? null : expires;
        if (StringUtils.isNotBlank(expires)) {
            return qiniuTool.getUpToken(bucket, key, Long.parseLong(expires));
        }
        return qiniuTool.getUpToken(bucket, key);
    }

    /**
     * 功能描述: 根据key获取文件url
     *
     * @param key    键
     * @param width  图片的宽
     * @param height 图片的高
     * @return 图片url
     * @date 2020/6/14 13:02
     */
    public String getUrlByKey(String key, Integer width, Integer height) {
        return qiniuTool.getDownloadUrl(key, width, height);
    }

    /**
     * 功能描述: 批量获取Urls
     *
     * @param keys   键集合
     * @param width  图片宽
     * @param height 图片高
     * @return
     * @date 2020/6/14 13:02
     */
    public List<String> getUrlsByKeys(List<String> keys, Integer width, Integer height) {
        List<String> Urls = new ArrayList<String>();
        keys.stream().forEach(k -> Urls.add(qiniuTool.getDownloadUrl(k, width, height)));
        return Urls;
    }

    /**
     * 功能描述: 根据key获取带水印的文件url
     *
     * @param key          键
     * @param watermarkUrl 水印url
     * @param gravity      水印的位置，传空则默认为SouthEast（右下角）
     * @param dissolve     水印不透明度，传值范围 1--100，传空则默认为100
     * @param scale        水印图片自适应原图的短边比例，ws的取值范围为0-1。具体是指水印图片保持原比例，并短边缩放到原图短边＊ws。例如：原图大小为250x250，水印图片大小为91x61，如果ws=1，那么最终水印图片的大小为：372x250
     * @param width        压缩后图片的宽
     * @param height       压缩后图片的高
     * @return 图片url
     * @date 2020/6/14 13:00
     */
    public String getUrlWithWatermark(String key, String watermarkUrl, String gravity, Integer dissolve, Double scale, Integer width, Integer height) {
        return qiniuTool.getUrlWithWatermark(key, watermarkUrl, gravity, dissolve, scale, width, height);
    }

    /**
     * 功能描述: 根据key获取裁剪后的图片的url
     *
     * @param key    键
     * @param width  图片宽
     * @param height 图片高
     * @return 图片url
     * @date 2020/6/14 12:59
     */
    public String getTailoredUrlByKey(String key, Integer width, Integer height) {
        return qiniuTool.getTailoredDownloadUrl(key, width, height);
    }


    /**
     * 功能描述: 根据key获取文件url
     *
     * @param key    键
     * @param width  图片宽
     * @param height 图片高
     * @return 图片对象
     * @date 2020/6/14 12:59
     */
    public ImageInfo getImageInfo(String key, Integer width, Integer height) {
        if (width == null && height == null) {
            return qiniuTool.getImageInfo(key);
        }
        return qiniuTool.getImageInfo(key, width, height);
    }

    /**
     * 功能描述: 根据key获取文件url
     *
     * @param key 键
     * @return 图片url
     * @date 2020/6/14 12:58
     */
    public String getImageInfo(String key) {
        return qiniuTool.getDownloadUrl(key);
    }

    /**
     * 功能描述:
     *
     * @param filebase
     * @param bucket   空间名称
     * @param key      键
     * @return 键
     * @date 2020/6/14 13:13
     */
    public String uploadAndGetKey(String filebase, String bucket, String key) {
        if (StringUtils.isBlank(filebase)) {
            return null;
        }
        return qiniuTool.uploadAndGetKey(Base64.getDecoder().decode(filebase), bucket, key);
    }

    /**
     * 功能描述: 上传文件到七牛并返回七牛的key
     *
     * @param data 二进制数组
     * @return 键
     * @date 2020/6/14 13:13
     */
    public String uploadAndGetKey(byte[] data) {
        return qiniuTool.uploadAndGetKey(data);
    }
}
