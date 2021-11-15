package com.onnoa.shop.demo.video.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description:
 * @Author: onnoA
 * @Date: 2020/6/5 23:30
 */
@Data
@TableName(value = "videostream_svc_video_info")
public class VideoInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**id*/
    @TableId(type = IdType.UUID)
    private String id;
    /**视频名称*/
    private String videoName;
    /**视频标题（展示给C端用户的名称，可与视频名称一致）*/
    private String videoTitle;
    /**视频文件原来的名字*/
    private String originFileName;
    /**视频来源系统*/
    private String fromSys;
    /**图片集ID（视频流服务提取一组图片）——视频动态封面图*/
    private String picsId;
    /**视频文件存放在服务器上的URL（或key），完整URL或部分URL(未包含域名等部分)；视频文件夹及文件名都与ID一致（规则）*/
    private String fileUrl;
    /**视频播放地址*/
    private String playUrl;
    /**视频封面图（gif等）url*/
    private String coverPicUrl;
    /**视频封面截取的时间区间，竖线分割，格式 [开始时间]|[结束时间]，如：1:25|1:32*/
    private String coverPeriodStart;
    /**视频封面截取的时间区间，竖线分割，格式 [开始时间]|[结束时间]，如：1:25|1:32*/
    private String coverPeriodEnd;
    /**(自动)视频文件大小，MB为单位*/
    private Long fileSize;
    /**视频封装格式类型 0:其他  1:MP4*/
    private Integer formatType;
    /**(自动)视频分辨率宽*/
    private Integer videoWidth;
    /**(自动)视频分辨率高*/
    private Integer videoHeight;
    /**(自动)视频码率*/
    @TableField(value="vd_bitrate")
    private Long vdBitrate;
    /**(自动)音频码率*/
    @TableField(value="ad_bitrate")
    private Long adBitrate;
    /**(自动)总码率*/
    @TableField(value="tl_bitrate")
    private Long tlBitrate;
    /**状态（0：未发布 1：已发布可用   2：已发布禁用）*/
    private Integer status;
    /**描述*/
    private String descr;
    /**备注*/
    private String memo;
    /**创建者*/
    private String createBy;
    /**创建时间*/
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;
    /**编辑者*/
    private String updateBy;
    /**编辑时间*/
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;
    /**是否已删除（0:否 1：是）*/
    private Integer deleted;
}
