package com.onnoa.shop.demo.video.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.onnoa.shop.demo.video.domain.PicSetInfo;
import org.springframework.stereotype.Repository;

import java.util.List;



/**
 * @Description 视频信息表
 * @author onnoA
 * @date 2021/7/6 23:46
 */

public interface PicSetInfoMapper extends BaseMapper<PicSetInfo> {

    List<PicSetInfo> queryByAwaitStatus(Integer countTimingjob);

    PicSetInfo selectByPicId(String picId);
}
