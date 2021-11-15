package com.onnoa.shop.demo.video.domain;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 图片集（同一张图片多种尺寸和格式等）的项，一项是一张图片(UtopaVideostreamSvcPicSetItem)实体类
 *
 * @author makejava
 * @since 2020-04-29 18:52:45
 */
@Data
@TableName("videostream_svc_pic_set_item")
public class PicSetItem implements Serializable {
    private static final long serialVersionUID = -10769133367576427L;

    @TableId(type = IdType.UUID)
    private String id;
    /**
    * 图片集ID（视频流服务提取一组图片）
    */
    private String picsId;
    /**
    * 图片大小类型（1：小 2：中 3：大  10 : 原图）
    */
    private Integer sizeType;
    /**
    * 图片后缀（小写）——图片类型
    */
    private String picSuffix;
    /**
    * 图片格式类型（1：通用格式,jpg gif 等， 2：非通用格式，webp格式）
    */
    private Integer formatType;
    /**
    * 图片文件url路径
    */
    private String fileUrl;
    /**
    * 源图片文件大小
    */
    private Double fileSize;
    /**
    * 图片宽
    */
    private Integer picWidth;
    /**
    * 图片高
    */
    private Integer picHeight;
    /**
    * 状态（0：待处理 1：正常完成   2：异常）
    */
    private Integer status;
    /**
    * 描述
    */
    private String descr;
    /**
    * 备注
    */
    private String memo;
    /**
    * 创建者
    */
    private String createBy;
    /**
    * 创建时间
    */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    /**
    * 编辑者
    */
    private String updateBy;
    /**
    * 编辑时间
    */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
    /**
    * 是否已删除（0:否 1：是）
    */
    private Integer deleted;

}