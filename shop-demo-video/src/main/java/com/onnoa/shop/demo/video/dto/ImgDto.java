package com.onnoa.shop.demo.video.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 图片信息
 */
@Data
public class ImgDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 图片对应的id
     */
    @NotBlank(message = "缺少图片id")
    private String picId;
    /**
     * 图片高
     */
    @NotNull(message = "缺少图片高")
    private Integer height;

    /**
     * 图片宽
     */
    @NotNull(message = "缺少图片宽")
    private Integer width;

    /**
     * 图片大小
     */
    @NotNull(message = "缺少图片大小")
    private Double fileSize;

    /**
     * 图片后缀
     */
    @NotNull(message = "缺少图片后缀")
    private String suffix;

    /**
     * 七牛key
     */
    private String qiniuKey;

    /**
     * 文件名
     */
    @NotNull(message = "缺少图片文件名")
    private String fileName;
    /**
     * 图片文件
     */
    private MultipartFile picFile;

    /**
     * 七牛的访问url
     */
    private String qiniuUrl;

    /**
    * 状态（0：待处理 1：正常完成   2：异常）
    * 该字段目前只使用在图片补偿机制中的异常数据处理,后续若使用消息队列则剔除
    */
    private Integer status;

}
