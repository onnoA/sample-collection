package com.onnoa.shop.demo.video.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.onnoa.shop.demo.video.constant.PicInfoConstant;
import com.onnoa.shop.demo.video.domain.PicSetInfo;
import com.onnoa.shop.demo.video.domain.PicSetItem;
import com.onnoa.shop.demo.video.dto.ImageInfo;
import com.onnoa.shop.demo.video.dto.ImgDto;
import com.onnoa.shop.demo.video.dto.ImgInfoDto;
import com.onnoa.shop.demo.video.exception.PicException;
import com.onnoa.shop.demo.video.mapper.PicSetInfoMapper;
import com.onnoa.shop.demo.video.service.PicInfoService;
import com.onnoa.shop.demo.video.service.PicSetItemService;
import com.onnoa.shop.demo.video.service.QiniuService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class PicInfoServiceImpl extends ServiceImpl<PicSetInfoMapper, PicSetInfo> implements PicInfoService {

    @Autowired
    private QiniuService qiniuService;
    @Autowired
    private PicSetInfoMapper picSetInfoMapper;
    @Autowired
    private PicSetItemService picSetItemService;


    @Value("${videostream.pic-width.small}")
    private Integer SMALL;
    @Value("${videostream.pic-width.medium}")
    private Integer MEDIUM;
    @Value("${videostream.pic-width.advanced}")
    private Integer LARGE;

    /**
     * 通过七牛云的key(支持定时任务生成gif的七牛key和前端传过来的七牛key),设置ImgDto实例的setQiniuUrl属性
     *
     * @param imgDto
     * @return void
     * @author johnho
     * @date 2020/5/20
     */
    @Override
    public void setUrlByQiNiuKey(ImgDto imgDto) {
        //判断是否传回picId
        if (imgDto.getPicId().isEmpty()) {
            throw PicException.FAILED_TO_UPLOAD_PICIDISEMPTY;
        }
        //判断是否传回key
        if (imgDto.getQiniuKey() != null && !imgDto.getQiniuKey().equals("")) {
            //先访问七牛 获得路径
            imgDto.setQiniuUrl(qiniuService.getDownloadUrl(imgDto.getQiniuKey()));
        } else {
            //判断是否传回文件
            if (imgDto.getPicFile().isEmpty()) {
                throw PicException.FAILED_TO_UPLOAD_PICISEMPTY;
            }
            String qiniukey = null;
            try {
                qiniukey = qiniuService.uploadAndGetKey(imgDto.getPicFile().getBytes());
                imgDto.setQiniuKey(qiniukey);
            } catch (IOException e) {
                log.error("图片解析失败,图片id:" + imgDto.getPicId(), e);
                throw PicException.FAILED_TO_UPLOAD_PICREAD;
            }
            imgDto.setQiniuUrl(qiniuService.getDownloadUrl(qiniukey));
        }
    }

    /**
     * 上传图片
     * 此时存储的是图片集ID与,原图的key, 图片各种转换处理 在定时器中完成
     *
     * @param imgDto
     * @return
     */
    //TODO:拆分
    @Override
    public ImgDto saveUrlsToDB(ImgDto imgDto) {

        Map<String, String> picSetUrlMap = picSetItemService.generatePicSetUrls(imgDto);
        savePicSet(imgDto, picSetUrlMap);

//        //写入图片集 info表 暂时还缺少创建者信息(无法读取商户名)
//        PicSetInfo picSetInfo = addPicSetInfoPropertiesAndInsertDB(imgDto);
//        //写入图片具体信息
//        addPicSetItemPropertiesAndInsertDB(picSetInfo, imgDto);
//        //进行图片处理
//        //TODO:拆到controller
//        picSetItemService.ImgDispose(picSetInfo);
        return imgDto;
    }

    @Override
    public PicSetInfo savePicSet(ImgDto imgDto, Map<String, String> picSetUrlMap) {
        //写入图片集 info表 暂时还缺少创建者信息(无法读取商户名)
        PicSetInfo picSetInfo = addPicSetInfoPropertiesAndInsertDB(imgDto);
        //写入图片集
        addPicSetItemPropertiesAndInsertDB(picSetInfo, imgDto, picSetUrlMap);
        //转换完成后修改状态为已完成
        picSetInfo.setStatus(PicInfoConstant.AVAILABLE_STATUS);
        picSetInfoMapper.updateById(picSetInfo);
        return picSetInfo;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addPicSetItemPropertiesAndInsertDB(PicSetInfo picSetInfo, ImgDto imgDto, Map<String, String> picSetUrlMap) {
        List<PicSetItem> picSetItemList = new ArrayList<>();
        //1.先插入原图
        PicSetItem picSetItem = new PicSetItem();
        picSetItem.setPicsId(picSetInfo.getId());
        picSetItem.setFileSize(imgDto.getFileSize());
        picSetItem.setSizeType(PicInfoConstant.ORIGIN_TYPE);
        picSetItem.setPicSuffix(imgDto.getSuffix());
        picSetItem.setFileUrl(qiniuService.getDownloadUrl(imgDto.getQiniuKey()));
        log.info("picSetItem is :{}", picSetItem.toString());
        if ("webp".equals(imgDto.getSuffix())) {
            picSetItem.setFormatType(PicInfoConstant.NOGENERAL_FORMAT);
        } else {
            picSetItem.setFormatType(PicInfoConstant.GENERAL_FORMAT);
        }

        picSetItem.setPicWidth(imgDto.getWidth());
        picSetItem.setPicHeight(imgDto.getHeight());
        picSetItem.setStatus(imgDto.getStatus() != null ? imgDto.getStatus(): PicInfoConstant.AVAILABLE_STATUS);
        //imgDto.setPicFile(null);
        picSetItemList.add(picSetItem);

        if (!CollectionUtils.isEmpty(picSetUrlMap)) {
            //2.插入large尺寸的图片
            if (picSetUrlMap.get("large-jpg") != null) {
                //2.1jpg或者gif 实例
                picSetItem = new PicSetItem();
                picSetItem.setPicsId(picSetInfo.getId());
                picSetItem.setFileSize(imgDto.getFileSize());
                picSetItem.setSizeType(PicInfoConstant.LARGE_TYPE);
                picSetItem.setPicSuffix(imgDto.getSuffix());
                picSetItem.setFormatType(PicInfoConstant.GENERAL_FORMAT);
                picSetItem.setFileUrl(picSetUrlMap.get("large-jpg"));
                picSetItem.setPicWidth(LARGE);
                picSetItem.setPicHeight(imgDto.getHeight() * LARGE / imgDto.getWidth());
                picSetItem.setStatus(PicInfoConstant.AVAILABLE_STATUS);
                picSetItemList.add(picSetItem);
            }
            if (picSetUrlMap.get("large-webp") != null) {
                //2.2webP 实例
                picSetItem = new PicSetItem();
                picSetItem.setPicsId(picSetInfo.getId());
                picSetItem.setFileSize(imgDto.getFileSize());
                picSetItem.setSizeType(PicInfoConstant.LARGE_TYPE);
                picSetItem.setPicSuffix(PicInfoConstant.FILE_PICSUFFIX_WEBP);
                picSetItem.setFormatType(PicInfoConstant.NOGENERAL_FORMAT);
                picSetItem.setFileUrl(picSetUrlMap.get("large-webp"));
                picSetItem.setPicWidth(LARGE);
                picSetItem.setPicHeight(imgDto.getHeight() * LARGE / imgDto.getWidth());
                picSetItem.setStatus(PicInfoConstant.AVAILABLE_STATUS);
                picSetItemList.add(picSetItem);

            }


            //3.插入medium尺寸的图片
            //3.1jpg或者gif 实例
            if (picSetUrlMap.get("medium-jpg") != null) {
                picSetItem = new PicSetItem();
                picSetItem.setPicsId(picSetInfo.getId());
                picSetItem.setFileSize(imgDto.getFileSize());
                picSetItem.setSizeType(PicInfoConstant.MEDIUM_TYPE);
                picSetItem.setPicSuffix(imgDto.getSuffix());
                picSetItem.setFormatType(PicInfoConstant.GENERAL_FORMAT);
                picSetItem.setFileUrl(picSetUrlMap.get("medium-jpg"));
                picSetItem.setPicWidth(MEDIUM);
                picSetItem.setPicHeight(imgDto.getHeight() * MEDIUM / imgDto.getWidth());
                picSetItem.setStatus(PicInfoConstant.AVAILABLE_STATUS);
                picSetItemList.add(picSetItem);
            }
            if (picSetUrlMap.get("medium-webp") != null) {
                //3.2webP 实例
                picSetItem = new PicSetItem();
                picSetItem.setPicsId(picSetInfo.getId());
                picSetItem.setFileSize(imgDto.getFileSize());
                picSetItem.setSizeType(PicInfoConstant.MEDIUM_TYPE);
                picSetItem.setPicSuffix(PicInfoConstant.FILE_PICSUFFIX_WEBP);
                picSetItem.setFormatType(PicInfoConstant.NOGENERAL_FORMAT);
                picSetItem.setFileUrl(picSetUrlMap.get("medium-webp"));
                picSetItem.setPicWidth(MEDIUM);
                picSetItem.setPicHeight(imgDto.getHeight() * MEDIUM / imgDto.getWidth());
                picSetItem.setStatus(PicInfoConstant.AVAILABLE_STATUS);
                picSetItemList.add(picSetItem);
            }


            //4.插入small尺寸的图片
            //4.1jpg或者gif 实例
            if (picSetUrlMap.get("small-jpg") != null) {
                picSetItem = new PicSetItem();
                picSetItem.setPicsId(picSetInfo.getId());
                picSetItem.setFileSize(imgDto.getFileSize());
                picSetItem.setSizeType(PicInfoConstant.SMALL_TYPE);
                picSetItem.setPicSuffix(imgDto.getSuffix());
                picSetItem.setFormatType(PicInfoConstant.GENERAL_FORMAT);
                picSetItem.setFileUrl(picSetUrlMap.get("small-jpg"));
                picSetItem.setPicWidth(SMALL);
                picSetItem.setPicHeight(imgDto.getHeight() * SMALL / imgDto.getWidth());
                picSetItem.setStatus(PicInfoConstant.AVAILABLE_STATUS);
                picSetItemList.add(picSetItem);
            }
            if (picSetUrlMap.get("small-webp") != null) {
                //2.2webP 实例
                picSetItem = new PicSetItem();
                picSetItem.setPicsId(picSetInfo.getId());
                picSetItem.setFileSize(imgDto.getFileSize());
                picSetItem.setSizeType(PicInfoConstant.SMALL_TYPE);
                picSetItem.setPicSuffix(PicInfoConstant.FILE_PICSUFFIX_WEBP);
                picSetItem.setFormatType(PicInfoConstant.NOGENERAL_FORMAT);
                picSetItem.setFileUrl(picSetUrlMap.get("small-webp"));
                picSetItem.setPicWidth(SMALL);
                picSetItem.setPicHeight(imgDto.getHeight() * SMALL / imgDto.getWidth());
                picSetItem.setStatus(PicInfoConstant.AVAILABLE_STATUS);
                picSetItemList.add(picSetItem);
            }
        }
        //批量插入picSetItem表,最多7个图片的数据
        picSetItemService.insertBatch(picSetItemList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PicSetInfo addPicSetInfoPropertiesAndInsertDB(ImgDto imgDto) {
        log.info("开始保存图片集info picId：{}", imgDto.getPicId());
        //写入图片集 info表 暂时还缺少创建者信息(无法读取商户名)
        PicSetInfo picSetInfo = new PicSetInfo();
        picSetInfo.setReferPicId(imgDto.getPicId());
        picSetInfo.setOriginFileName(imgDto.getFileName());
        picSetInfo.setStatus(imgDto.getStatus() != null ? imgDto.getStatus() : PicInfoConstant.AWAIT_STATUS);
        picSetInfo.setQiniuPicKey(imgDto.getQiniuKey());
        picSetInfo.setCreateTime(new Date());
        picSetInfo.setUpdateTime(new Date());
        picSetInfoMapper.insert(picSetInfo);
        log.info("保存图片集info结束 picId：{}", imgDto.getPicId());
        return picSetInfo;
    }

    @Override
    public List<PicSetInfo> queryByAwaitStatus(Integer countTimingjob) {
        return picSetInfoMapper.queryByAwaitStatus(countTimingjob);
    }

    @Override
    public ImgInfoDto queryByPicId(String picId) {
        ImgInfoDto imgInfoDto = new ImgInfoDto();
        PicSetInfo picSetInfo = picSetInfoMapper.selectByPicId(picId);
        if (picSetInfo != null) {
            List<PicSetItem> picSetItems = picSetItemService.selectList(
                    new EntityWrapper<PicSetItem>()
                            .eq("pics_id", picSetInfo.getId())
                            .in("status", 1, 2)     //处理完成以及处理异常的信息
                            .eq("deleted", 0));
            imgInfoDto.setPicSetInfo(picSetInfo);
            imgInfoDto.setPicSetItemList(picSetItems);
        }
        return imgInfoDto;
    }

    @Override
    public ImgInfoDto queryByPicsId(String picsId) {
        ImgInfoDto imgInfoDto = new ImgInfoDto();
        PicSetInfo picSetInfo = picSetInfoMapper.selectById(picsId);
        if (picSetInfo != null) {
            List<PicSetItem> picSetItems = picSetItemService.selectList(
                    new EntityWrapper<PicSetItem>()
                            .eq("pics_id", picsId)
                            .in("status", 1, 2)     //处理完成以及处理异常的信息
                            .eq("deleted", 0));
            imgInfoDto.setPicSetInfo(picSetInfo);
            imgInfoDto.setPicSetItemList(picSetItems);
        }
        return imgInfoDto;
    }

    /**
     * 补偿机制：当图片回调为空时根据七牛图片key重新添加图片入库
     *
     * @param picId  图片id
     * @param picKey 图片上传到七牛获取到的key
     */
    @Override
    public void appendPicInfo(String picId, String picKey) {
        log.info(">>>>>>>>>>>>>开始图片补偿,图片id:【{}】,图片key:【{}】", picId, picKey);
        ImageInfo imageInfo = null;
        if (StringUtils.isNotBlank(picKey)) {
            //从七牛获取图片基本信息
            imageInfo = qiniuService.getImageInfo(picKey);
        }

        ImgDto imgDto = new ImgDto();
        if (imageInfo != null) {
            imgDto.setPicId(picId);
            imgDto.setQiniuKey(picKey);
            imgDto.setHeight(imageInfo.getHeight());
            imgDto.setWidth(imageInfo.getWidth());
            imgDto.setFileSize(Double.valueOf(imageInfo.getSize()));
            imgDto.setSuffix(imageInfo.getFormat().toLowerCase());
            imgDto.setFileName(UUID.randomUUID().toString());

            PicSetInfo picSetInfo = picSetInfoMapper.selectByPicId(picId);
            if (picSetInfo == null) {
                //获取qiniuUrl字段值
                setUrlByQiNiuKey(imgDto);
                //保存图片
                saveUrlsToDB(imgDto);
            } else {
                //获取不同格式图片
                Map<String, String> picSetUrlMap = picSetItemService.generatePicSetUrls(imgDto);
                //写入图片集
                addPicSetItemPropertiesAndInsertDB(picSetInfo, imgDto, picSetUrlMap);
            }
            log.info(">>>>>>>>>>>>>图片补偿成功,图片id:【{}】,图片key:【{}】", picId, picKey);
        } else {
            imgDto.setPicId(picId);
            imgDto.setQiniuKey(picKey);
            imgDto.setHeight(1);
            imgDto.setWidth(1);
            imgDto.setFileSize(0D);
            imgDto.setSuffix("jpg");
            imgDto.setFileName(UUID.randomUUID().toString());
            imgDto.setStatus(PicInfoConstant.PICEXCEPTION_STATUS);
            saveUrlsToDB(imgDto);
            log.info(">>>>>>>>>>>>>图片补偿成功,但图片异常!图片id:【{}】,图片key:【{}】", picId, picKey);
        }
    }
}
