package com.onnoa.shop.demo.comment.service;

import com.onnoa.shop.demo.comment.dto.CommentsInfoDTO;

import java.util.List;

/**
 * @Description:
 * @Author: onnoA
 * @Date: 2020/5/28 09:32
 */
public interface CommentsInfoService {
    /**
     * 保存评论
     *
     * @param dto
     * @return
     */
    long save(CommentsInfoDTO dto);

    /**
     * 根据被评论的资源id查询评论列表
     *
     * @param ownerId
     * @return
     */
    List<CommentsInfoDTO> findByOwnerId(String ownerId);


}
