package com.onnoa.shop.demo.comment.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotations.TableLogic;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 评论数据库映射实体
 * @Author: onnoA
 * @Date: 2020/5/26 15:37
 */

@Data
@TableName("content_comments")
public class ContentComments implements Serializable {
    private static final long serialVersionUID = -4568928073579442976L;

    //评论主键id
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    //该条评论的父评论id
    private Integer pid;

    //被评论的资源id，可以是项目、资源、内容等
    private String resourceId;

    //评论类型：1：对内容评论，2：回复评论
    private Integer type;

    // 状态:状态：1：正常;2：屏蔽
    private Integer status;

    //评论者id
    private String userId;

    //被评论者id
    private String beReviewerId;

    //点赞数
    private long likeNum;

    //评论内容
    private String commentContent;

    //创建时间
    private Date createTime;

    //更新时间
    private Date updateTime;

    // 创建者
    private String createBy;

    // 修改者
    private String updateBy;

    // 是否逻辑删除 0：未删除；1：删除
    @TableLogic
    private Integer deleted;


}
