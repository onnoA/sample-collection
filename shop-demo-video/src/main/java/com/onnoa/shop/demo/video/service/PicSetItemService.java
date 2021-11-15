package com.onnoa.shop.demo.video.service;

import com.baomidou.mybatisplus.service.IService;
import com.onnoa.shop.demo.video.domain.PicSetInfo;
import com.onnoa.shop.demo.video.domain.PicSetItem;
import com.onnoa.shop.demo.video.dto.ImgDto;


import java.util.Map;

public interface PicSetItemService extends IService<PicSetItem> {

    PicSetItem selectSourceTypeByPicsId(String picsId);

    void insertByType(String qiniuKey, PicSetItem insertJpgInstance, Integer width, PicSetItem insertWebPInstance);

    void ImgDispose(PicSetInfo picSetInfo);

    String getUrlBySuffixWebp(String qiniuKey, Integer width);

    String getJpgUrlFromQiNiuKey(String qiniuKey, Integer width);

    Map<String, String> generatePicSetUrls(ImgDto imgDto);
}
