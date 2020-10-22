package com.onnoa.shop.demo.comment.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Description: 评论列表查询dto
 * @Author: onnoA
 * @Date: 2020/5/28 09:28
 */
@Data
public class ContentCommentsListDto extends PageRequestDto {

    // 资源id (内容id)
    @NotBlank(message = "资源id不能为空。。")
    private String resourceId;

}
