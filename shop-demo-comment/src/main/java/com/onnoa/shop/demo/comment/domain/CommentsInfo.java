package com.onnoa.shop.demo.comment.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description:
 * @Author: onnoA
 * @Date: 2020/5/26 15:37
 */

@Data
public class CommentsInfo implements Serializable {
    private static final long serialVersionUID = -4568928073579442976L;

    //评论主键id
    @TableId(type = IdType.AUTO)
    private String id;

    //该条评论的父评论id
    private String pid;

    //评论的资源id。标记这条评论是属于哪个资源的。资源可以是人、项目、设计资源
    private String ownerId;

    //评论类型。1用户评论，2项目评论，3资源评论
    private Integer type;

    //评论者id
    private String fromId;

    //评论者名字
    private String fromName;

    //被评论者id
    private String toId;

    //被评论者名字
    private String toName;

    //获得点赞的数量
    private Integer likeNum;

    //评论内容
    private String content;

    //创建时间
    private Date createTime;

    //更新时间
    private Date updateTime;


}
