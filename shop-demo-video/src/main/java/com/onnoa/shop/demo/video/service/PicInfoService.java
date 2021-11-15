package com.onnoa.shop.demo.video.service;

import com.baomidou.mybatisplus.service.IService;
import com.onnoa.shop.demo.video.domain.PicSetInfo;
import com.onnoa.shop.demo.video.dto.ImgDto;
import com.onnoa.shop.demo.video.dto.ImgInfoDto;


import java.util.List;
import java.util.Map;

public interface PicInfoService extends IService<PicSetInfo>{

    ImgDto saveUrlsToDB(ImgDto imgDto);

    void setUrlByQiNiuKey(ImgDto imgDto);

    List<PicSetInfo> queryByAwaitStatus(Integer countTimingjob);

    ImgInfoDto queryByPicId(String picId);

    ImgInfoDto queryByPicsId(String picId);

    PicSetInfo addPicSetInfoPropertiesAndInsertDB(ImgDto imgDto);

    void addPicSetItemPropertiesAndInsertDB(PicSetInfo picSetInfo, ImgDto imgDto, Map<String, String> picSetUrlMap);

    PicSetInfo savePicSet(ImgDto imgDto, Map<String, String> picSetUrlMap);

    void appendPicInfo(String picId, String picKey);
}
