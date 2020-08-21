package com.onnoa.shop.demo.comment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.onnoa.shop.demo.comment.domain.ContentComments;
import com.onnoa.shop.demo.comment.dto.ContentCommentsListDto;
import com.onnoa.shop.demo.comment.vo.ContentCommentsListVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Description: 内容管理mapper接口
 * @Author: onnoA
 * @Date: 2020/5/28 10:35
 */
@Mapper
public interface ContentCommentMapper extends BaseMapper<ContentComments> {

    List<ContentComments> findByOwnerId(@Param("ownerId") String ownerId);

    List<ContentCommentsListVo> getTopCommentsPageByResourceId(@Param("listDto") ContentCommentsListDto listDto, Page page);

    List<ContentCommentsListVo> getChildCommentListById(Long id);

}
