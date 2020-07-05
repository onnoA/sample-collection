package com.onnoa.shop.demo.authority.system.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Description: 新增评论实体
 * @Author: onnoA
 * @Date: 2020/5/28 09:28
 */
@Data
public class ContentCommentsAddDto implements Serializable {

    private static final long serialVersionUID = -6788130126931979110L;

    //该条评论的父评论id
    private Integer pid;

    //被评论的资源id，可以是人、项目、资源、内容等
    @NotBlank(message = "被评论的资源id不能为空。。")
    private String resourceId;

    //评论类型：1：对内容评论，2：回复评论
    @NotNull(message = "评论类型不能为空。。")
    private Integer type;

    //评论者id
    @NotBlank(message = "评论者id不能为空。。")
    private String userId;

    //被评论者id
    private String beReviewerId;

    //评论内容
    @NotBlank(message = "评论内容不能为空。。")
    private String commentContent;

    // 创建者
    @NotBlank(message = "创建者不能为空。")
    private String createBy;

}
