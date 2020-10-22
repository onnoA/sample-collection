package com.onnoa.shop.demo.comment.service;

import com.onnoa.shop.common.dto.PageDto;
import com.onnoa.shop.demo.comment.dto.ContentCommentsAddDto;
import com.onnoa.shop.demo.comment.dto.ContentCommentsListDto;
import com.onnoa.shop.demo.comment.vo.ContentCommentsListVo;

/**
 * @Description:
 * @Author: onnoA
 * @Date: 2020/5/28 09:32
 */
public interface ContentCommentService {
    /**
     * 保存评论
     *
     * @param dto
     * @return
     */
    long save(ContentCommentsAddDto dto);

    Boolean comment(ContentCommentsAddDto addDto);

    PageDto<ContentCommentsListVo> commentList(ContentCommentsListDto listDto);

    Boolean deleted(int commentId);

    Boolean shield(int commentId);
}
