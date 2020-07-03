package com.onnoa.shop.demo.comment.converter;

import com.onnoa.shop.demo.comment.domain.ContentComments;
import com.onnoa.shop.demo.comment.dto.ContentCommentsAddDto;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * CommentsInfo 与 CommentsInfoDTO 相互转换的工具类
 */
public class CommentsConverter {

    public static ContentCommentsAddDto info2DTO(ContentComments info) {
        ContentCommentsAddDto dto = new ContentCommentsAddDto();
        BeanUtils.copyProperties(info, dto);
        return dto;
    }

    public static ContentComments DTO2Info(ContentCommentsAddDto dto) {
        ContentComments info = new ContentComments();
        BeanUtils.copyProperties(dto, info);
        return info;
    }

    public static List<ContentCommentsAddDto> infos2DTOList(List<ContentComments> infos) {
        return infos.stream().map(info -> info2DTO(info)).collect(Collectors.toList());
    }
}
