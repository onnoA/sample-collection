package com.onnoa.shop.demo.comment.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.onnoa.shop.common.constant.IsShieldEnums;
import com.onnoa.shop.common.dto.PageDto;
import com.onnoa.shop.common.exception.ServiceException;
import com.onnoa.shop.common.utils.BeanUtils;
import com.onnoa.shop.demo.comment.domain.ContentComments;
import com.onnoa.shop.demo.comment.dto.ContentCommentsAddDto;
import com.onnoa.shop.demo.comment.dto.ContentCommentsListDto;
import com.onnoa.shop.demo.comment.mapper.ContentCommentMapper;
import com.onnoa.shop.demo.comment.service.ContentCommentService;
import com.onnoa.shop.demo.comment.vo.ContentCommentsListVo;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Author: onnoA
 * @Date: 2020/5/28 09:33
 */
@Service
public class ContentCommentServiceImpl implements ContentCommentService {

    @Autowired
    private ContentCommentMapper commentsInfoMapper;

    @Override
    public long save(ContentCommentsAddDto dto) {
        ContentComments entity = new ContentComments();
        BeanUtils.copyToBean(dto, entity);
        return commentsInfoMapper.insert(entity);
        //return CommentsConverter.info2DTO(result);
    }

    @Override
    public Boolean comment(ContentCommentsAddDto addDto) {
        ContentComments entity = new ContentComments();
        BeanUtils.copyToBean(addDto, entity);
        entity.setCreateTime(new Date());
        return commentsInfoMapper.insert(entity) > 0;
    }

    @Override
    public PageDto<ContentCommentsListVo> commentList(ContentCommentsListDto listDto) {
        Page<ContentCommentsListVo> dtoPage = new Page<>(listDto.getPageNo(), listDto.getPageSize());
        List<ContentCommentsListVo> resultList = commentsInfoMapper.getTopCommentsPageByResourceId(listDto, dtoPage);
        // 递归获取子评论
        findChildCommentList(resultList);
        dtoPage.setRecords(resultList);
        return BeanUtils.copyToNewBean(dtoPage, PageDto.class);
    }

    @Override
    public Boolean deleted(int commentId) {
        int i = commentsInfoMapper.deleteById(commentId);
        return i > 0;
    }

    @Override
    public Boolean shield(int commentId) {
        ContentComments entity = commentsInfoMapper.selectById(commentId);
        if (entity == null) {
            throw ServiceException.OBJECT_IS_NOT_EXIST.format("评论id为:" + commentId);
        }
        entity.setStatus(IsShieldEnums.SHIELDED.getCode());
        int update = commentsInfoMapper.updateById(entity);
        return update > 0;
    }

    private void findChildCommentList(List<ContentCommentsListVo> resultList) {
        if (CollectionUtils.isNotEmpty(resultList)) {
            for (ContentCommentsListVo commentVo : resultList) {
                List<ContentCommentsListVo> childCommentList = commentsInfoMapper.getChildCommentListById(commentVo.getId());
                commentVo.setChildCommentList(childCommentList);
                if (CollectionUtils.isNotEmpty(childCommentList)) {
                    findChildCommentList(childCommentList);
                }
            }
        }
    }

}
