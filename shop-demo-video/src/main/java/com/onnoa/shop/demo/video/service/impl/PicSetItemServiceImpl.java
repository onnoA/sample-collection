package com.onnoa.shop.demo.video.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import com.onnoa.shop.demo.video.constant.PicInfoConstant;
import com.onnoa.shop.demo.video.domain.PicSetInfo;
import com.onnoa.shop.demo.video.domain.PicSetItem;
import com.onnoa.shop.demo.video.dto.ImgDto;
import com.onnoa.shop.demo.video.mapper.PicSetItemMapper;
import com.onnoa.shop.demo.video.service.PicInfoService;
import com.onnoa.shop.demo.video.service.PicSetItemService;
import com.onnoa.shop.demo.video.service.QiniuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class PicSetItemServiceImpl extends ServiceImpl<PicSetItemMapper, PicSetItem> implements PicSetItemService {
    @Value("${videostream.pic-width.small}")
    private Integer SMALL;
    @Value("${videostream.pic-width.medium}")
    private Integer MEDIUM;
    @Value("${videostream.pic-width.advanced}")
    private Integer LARGE;
    @Autowired
    private PicSetItemMapper picSetItemMapper;
    @Autowired
    private QiniuService qiniuService;
    @Autowired
    private PicInfoService picInfoService;
    @Autowired
    private PicSetItemService picSetItemService;

    @Override
    public PicSetItem selectSourceTypeByPicsId(String picsId) {
        return picSetItemMapper.selectSourceTypeByPicsId(picsId);
    }

    @Override
    public void insertByType(String qiniuKey, PicSetItem insertJpgInstance, Integer width, PicSetItem insertWebPInstance) {

        if (insertJpgInstance.getPicWidth() > width) {

            //插入jpgUrl数据
            insertJpgInstance.setPicHeight(insertJpgInstance.getPicHeight() * width / insertJpgInstance.getPicWidth());
            insertJpgInstance.setPicWidth(width);

            //七牛云jpgkey:
            String GeneralUrl = qiniuService.getDownloadUrl(qiniuKey, width, null);
            insertJpgInstance.setFormatType(PicInfoConstant.GENERAL_FORMAT);
            insertJpgInstance.setFileUrl(GeneralUrl);
            picSetItemMapper.insert(insertJpgInstance);


            //七牛云webpkey
            String webpUrl = qiniuService.getWebPUrlByOriginKey(qiniuKey, width);
            //插入webPUrl数据
            insertWebPInstance.setPicSuffix(PicInfoConstant.FILE_PICSUFFIX_WEBP);
            insertWebPInstance.setFormatType(PicInfoConstant.NOGENERAL_FORMAT);
            insertWebPInstance.setFileUrl(webpUrl);
            picSetItemMapper.insert(insertWebPInstance);
            addPicSetItemWebpUrlPropertiesAndInsertDB(webpUrl, insertWebPInstance);
        }
    }

    /**
      * 将resultMap的url赋值给实例并批量插入数据库
      * @author johnho
      * @date 2020/5/20
      * @param resultMap
      * @return void
            */
    public void addPicSetItemPropertiesAndInsertDB(Map<String, String> resultMap){
        PicSetItem picSetItem = new PicSetItem();
    }
    /**
     * 把七牛云webpkey赋值到PicSetItem实例
     *
     * @param
     * @return PicSetItem
     * @author johnho
     * @date 2020/5/20
     */
    public void addPicSetItemWebpUrlPropertiesAndInsertDB(String webpUrl, PicSetItem insertWebPInstance) {
        //插入webPUrl数据
        insertWebPInstance.setPicSuffix(PicInfoConstant.FILE_PICSUFFIX_WEBP);
        insertWebPInstance.setFormatType(PicInfoConstant.NOGENERAL_FORMAT);
        insertWebPInstance.setFileUrl(webpUrl);
        picSetItemMapper.insert(insertWebPInstance);
    }

    /**
     * 把拼接方式调七牛的url返回的key赋值到PicSetItem的实例
     *
     * @param GeneralUrl
     * @param picSetItem
     * @param width
     * @return PicSetItem
     * @author johnho
     * @date 2020/5/20
     */
    public PicSetItem addPicSetItemJpgKeyProperties(String GeneralUrl, PicSetItem picSetItem, Integer width) {

        picSetItem.setPicHeight(picSetItem.getPicHeight() * width / picSetItem.getPicWidth());
        picSetItem.setPicWidth(width);
        picSetItem.setId(null);
        picSetItem.setFormatType(PicInfoConstant.GENERAL_FORMAT);
        picSetItem.setFileUrl(GeneralUrl);
        return picSetItem;
    }

    /**
     * 通过拼接方式调七牛的url
     *
     * @param
     * @return java.lang.String
     * @author johnho
     * @date 2020/5/20
     */
    @Override
    public String getJpgUrlFromQiNiuKey(String qiniuKey, Integer width) {
        String GeneralUrl = qiniuService.getDownloadUrl(qiniuKey, width, null);
        return GeneralUrl;
    }

    /**
     * 获取七牛云webpkey
     *
     * @param qiniuKey
     * @param width
     * @return java.lang.String
     * @author johnho
     * @date 2020/5/20
     */
    @Override
    public String getUrlBySuffixWebp(String qiniuKey, Integer width) {
        String webpUrl = qiniuService.getWebPUrlByOriginKey(qiniuKey, width);
        return webpUrl;
    }

    @Override
    public void ImgDispose(PicSetInfo picSetInfo) {
        //处理图片转换
        PicSetItem picSetItem = picSetItemService.selectSourceTypeByPicsId(picSetInfo.getId());
        PicSetItem insertJpgInstance = new PicSetItem();
        PicSetItem insertWebPInstance = new PicSetItem();
        BeanUtils.copyProperties(picSetItem, insertJpgInstance);
        insertJpgInstance.setId(null);
        // 高等规格
        if (picSetItem.getPicWidth() > LARGE) {

            insertJpgInstance.setSizeType(PicInfoConstant.LARGE_TYPE);
            BeanUtils.copyProperties(insertJpgInstance, insertWebPInstance);
            picSetItemService.insertByType(picSetInfo.getQiniuPicKey(), insertJpgInstance, LARGE, insertWebPInstance);
        }
        //中等规格
        if (picSetItem.getPicWidth() > MEDIUM) {

            insertJpgInstance.setSizeType(PicInfoConstant.MEDIUM_TYPE);
            BeanUtils.copyProperties(insertJpgInstance, insertWebPInstance);
            picSetItemService.insertByType(picSetInfo.getQiniuPicKey(), insertJpgInstance, MEDIUM, insertWebPInstance);
        }
        //低等规格
        if (picSetItem.getPicWidth() > SMALL) {

            insertJpgInstance.setSizeType(PicInfoConstant.SMALL_TYPE);
            BeanUtils.copyProperties(insertJpgInstance, insertWebPInstance);
            picSetItemService.insertByType(picSetInfo.getQiniuPicKey(), insertJpgInstance, SMALL, insertWebPInstance);
        }
        //转换完成后修改状态为已完成
        picSetInfo.setStatus(PicInfoConstant.AVAILABLE_STATUS);
        picInfoService.updateById(picSetInfo);
    }

    @Override
    public Map<String, String> generatePicSetUrls(ImgDto imgDto) {
        Map<String, String> resultMap = new HashMap<>();
        // 高等规格
        if (imgDto.getWidth() >= LARGE) {


            //七牛云jpgkey:
            String jpgUrl = qiniuService.getDownloadUrl(imgDto.getQiniuKey(), LARGE, null);
            //七牛云webpkey
            String webpUrl = qiniuService.getWebPUrlByOriginKey(imgDto.getQiniuKey(), LARGE);

            resultMap.put("large-jpg",jpgUrl);
            resultMap.put("large-webp",webpUrl);

        }
        //中等规格
        if (imgDto.getWidth() >= MEDIUM) {
            //七牛云jpgkey:
            String jpgUrl = qiniuService.getDownloadUrl(imgDto.getQiniuKey(),MEDIUM , null);
            //七牛云webpkey
            String webpUrl = qiniuService.getWebPUrlByOriginKey(imgDto.getQiniuKey(), MEDIUM);
            resultMap.put("medium-jpg",jpgUrl);
            resultMap.put("medium-webp",webpUrl);
        }
        //低等规格
//        if (imgDto.getWidth() >= SMALL) {
//
//            //七牛云jpgkey:
//            String jpgUrl = qiniuService.getDownloadUrl(imgDto.getQiniuKey(),SMALL , null);
//            //七牛云webpkey
//            String webpUrl = qiniuService.getWebPUrlByOriginKey(imgDto.getQiniuKey(), SMALL);
//            resultMap.put("small-jpg",jpgUrl);
//            resultMap.put("small-webp",webpUrl);
//        }
        return resultMap;
    }
}
