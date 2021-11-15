package com.onnoa.shop.demo.video.dto;

import lombok.Data;

/**
 * @Auther: liaokai
 * @Date: 2020/3/17 14:41
 * @Description:
 */
@Data
public class PicResult {
    /**
     * 文件大小
     */
    private Double fileSize;


    /**
     * 宽
     */
    private Integer width;

    /**
     * 高
     */
    private Integer height;

}
