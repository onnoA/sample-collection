package com.onnoa.shop.demo.comment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.onnoa.shop.demo.comment.domain.CommentsInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description: 内容管理mapper接口
 * @Author: onnoA
 * @Date: 2020/5/28 10:35
 */
@Mapper
public interface CommentsInfoMapper extends BaseMapper<CommentsInfo> {

    List<CommentsInfo> findByOwnerId(@Param("ownerId") String ownerId);
}
