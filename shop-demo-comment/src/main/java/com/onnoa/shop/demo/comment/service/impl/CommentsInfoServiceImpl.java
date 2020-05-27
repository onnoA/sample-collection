package com.onnoa.shop.demo.comment.service.impl;

import com.onnoa.shop.common.utils.BeanUtils;
import com.onnoa.shop.demo.comment.converter.CommentsConverter;
import com.onnoa.shop.demo.comment.domain.CommentsInfo;
import com.onnoa.shop.demo.comment.dto.CommentsInfoDTO;
import com.onnoa.shop.demo.comment.mapper.CommentsInfoMapper;
import com.onnoa.shop.demo.comment.service.CommentsInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Author: onnoA
 * @Date: 2020/5/28 09:33
 */
@Service
public class CommentsInfoServiceImpl implements CommentsInfoService {

    @Autowired
    private CommentsInfoMapper commentsInfoMapper;

    @Override
    public long save(CommentsInfoDTO dto) {
        CommentsInfo entity = new CommentsInfo();
        BeanUtils.copyToBean(dto,entity);
        return commentsInfoMapper.insert(entity);
        //return CommentsConverter.info2DTO(result);
    }

    @Override
    public List<CommentsInfoDTO> findByOwnerId(String ownerId) {
        List<CommentsInfo> infoList = commentsInfoMapper.findByOwnerId(ownerId);
        List<CommentsInfoDTO> list = CommentsConverter.infos2DTOList(infoList)
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

    /**
     * 将无序的数据整理成有层级关系的数据
     *
     * @param dtos
     * @return
     */
    private List<CommentsInfoDTO> sortData(List<CommentsInfoDTO> dtos) {
        List<CommentsInfoDTO> list = new ArrayList<>();
        for (int i = 0; i < dtos.size(); i++) {
            CommentsInfoDTO dto1 = dtos.get(i);
            List<CommentsInfoDTO> children = new ArrayList<>();
            for (int j = 0; j < dtos.size(); j++) {
                CommentsInfoDTO dto2 = dtos.get(j);
                if (dto2.getPid() == null) {
                    continue;
                }
                if (dto1.getId().equals(dto2.getPid())) {
                    children.add(dto2);
                }
            }
            dto1.setChildren(children);
            //最外层的数据只添加 pid 为空的评论，其他评论在父评论的 children 下
            if (dto1.getPid() == null || StringUtils.isEmpty(dto1.getPid())) {
                list.add(dto1);
            }
        }
        return list;
    }

}
