package com.onnoa.shop.demo.comment.vo;

import com.google.common.collect.Lists;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Description: c端评论列表返回对象
 * @Author: onnoA
 * @Date: 2020/5/28 09:28
 */
@Data
public class ContentCommentsListVo implements Serializable {

    private Integer id;
    //该条评论的父评论id
    private Integer pid;

    //被评论的资源id，可以是项目、资源、内容等
    private String resourceId;

    //评论类型：1：对内容评论，2：回复评论
    private Integer type;

    //评论者id
    private String userId;

    //被评论者id
    private String beReviewerId;

    //点赞数
    private long likeNum;

    //评论内容
    private String commentContent;

    List<ContentCommentsListVo> childCommentList = Lists.newArrayList();



}
