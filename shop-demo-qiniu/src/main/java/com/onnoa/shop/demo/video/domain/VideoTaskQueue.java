package com.onnoa.shop.demo.video.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 视频处理任务队列表
 * @Author: jeecg-boot
 * @Date: 2020-03-25
 * @Version: V1.0
 */
@Data
@TableName("utopa_videostream_svc_video_task_queue")
public class VideoTaskQueue implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(type = IdType.UUID)
    private String id;
    /**
     * 任务ID（如果是视频处理类型就是视频ID）
     */
    private String taskId;
    /**
     * 任务类型（1：视频处理）
     */
    private Integer taskType;
    /**
     * 处理状态(0：待处理 10: 处理中 11: 失败待重试   6：成功处理完成  30：处理失败并超过重试次数)
     */
    private Integer status;
    /**
     * 重试次数
     */
    private Integer retryCount;
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
    @TableField(value = "update_time", update = "now()")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
    /**
     * 是否已删除（0:否 1：是）
     */
    private Integer deleted;
}
