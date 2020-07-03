package com.onnoa.shop.demo.comment.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.onnoa.shop.common.dto.PageDto;
import com.onnoa.shop.common.dto.PageRequestDto;
import com.onnoa.shop.common.utils.BeanUtils;
import com.onnoa.shop.demo.comment.converter.CommentsConverter;
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
import java.util.stream.Collectors;

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
    public List<ContentCommentsAddDto> findByOwnerId(String ownerId) {
        List<ContentComments> infoList = commentsInfoMapper.findByOwnerId(ownerId);
        List<ContentCommentsAddDto> list = CommentsConverter.infos2DTOList(infoList)
                .stream()
                .map(dto -> {
                    //从用户服务取评论者头像
                    //UserInfoForComments fromUser = userClient.getAvatarByUserId(dto.getFromId());
                    /*if (fromUser != null) {
                        dto.setFromAvatar(fromUser.getAvatar());
                    }*/

                    //从用户服务取被评论者头像
                    //String toId = dto.getToId();
                   /* if (!StringUtils.isEmpty(toId)) {
                        UserInfoForComments toUser = userClient.getAvatarByUserId(toId);
                        if (toUser != null) {
                            dto.setToAvatar(toUser.getAvatar());
                        }
                    }*/
                    return dto;
                }).collect(Collectors.toList());
        return sortData(list);
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

    /**
     * 将无序的数据整理成有层级关系的数据
     *
     * @param dtos
     * @return
     */
    private List<ContentCommentsAddDto> sortData(List<ContentCommentsAddDto> dtos) {
        List<ContentCommentsAddDto> list = new ArrayList<>();
        for (int i = 0; i < dtos.size(); i++) {
            ContentCommentsAddDto dto1 = dtos.get(i);
            List<ContentCommentsAddDto> children = new ArrayList<>();
            for (int j = 0; j < dtos.size(); j++) {
                ContentCommentsAddDto dto2 = dtos.get(j);
                if (dto2.getPid() == null) {
                    continue;
                }
                /*if (dto1.getId().equals(dto2.getPid())) {
                    children.add(dto2);
                }*/
            }
            //dto1.setChildren(children);
            //最外层的数据只添加 pid 为空的评论，其他评论在父评论的 children 下
           /* if (dto1.getPid() == null || StringUtils.isEmpty(dto1.getPid())) {
                list.add(dto1);
            }*/
        }
        return list;
    }

}
