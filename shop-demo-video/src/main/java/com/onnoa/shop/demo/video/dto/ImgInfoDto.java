package com.onnoa.shop.demo.video.dto;



import com.onnoa.shop.demo.video.domain.PicSetInfo;
import com.onnoa.shop.demo.video.domain.PicSetItem;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ImgInfoDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private PicSetInfo picSetInfo;
    private List<PicSetItem> picSetItemList;
}
