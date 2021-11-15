package com.onnoa.shop.demo.video.mapper;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.onnoa.shop.demo.video.domain.PicSetItem;
import org.springframework.stereotype.Repository;


/**
 * @Description: 视频信息表
 * @Author: jeecg-boot
 * @Date:   2020-03-25
 * @Version: V1.0
 */

public interface PicSetItemMapper extends BaseMapper<PicSetItem> {

    PicSetItem selectSourceTypeByPicsId(String picsId);
}
