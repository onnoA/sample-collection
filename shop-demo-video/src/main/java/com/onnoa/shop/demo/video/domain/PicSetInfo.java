package com.onnoa.shop.demo.video.domain;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * @Description 图片集（同一张图片多种尺寸和格式等）基本信息表(VideostreamSvcPicSetInfo)实体类
 * @author onnoA
 * @date 2021/7/6 23:25
 */
@Data
@TableName("videostream_svc_pic_set_info")
public class PicSetInfo implements Serializable {
    private static final long serialVersionUID = -29941209027637652L;
    @TableId(type = IdType.UUID)
    private String id;

    /**
     * 原图id
     */
    private String referPicId;
    
    private String qiniuPicKey;
    /**
    * 图片文件原来的名字
    */
    private String originFileName;
    /**
    * 状态（0：待处理 1：正常完成   2：异常）全部pic set中图片的总的状态
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