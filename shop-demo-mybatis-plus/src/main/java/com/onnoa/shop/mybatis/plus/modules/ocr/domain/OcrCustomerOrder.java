package com.onnoa.shop.mybatis.plus.modules.ocr.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author onnoA
 * @since 2020-10-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ocr_customer_order")
public class OcrCustomerOrder implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "inst_id", type = IdType.AUTO)
    private Long instId;

    private Integer monthId;

    /**
     * 场景编码
     */
    private String scenarioCode;

    /**
     * 客户订单id
     */
    private String custOrderId;

    /**
     * 本地网
     */
    private String lanId;

    /**
     * 证件号码
     */
    private String certiNumber;

    /**
     * 证件类型
     */
    private String certiType;

    /**
     * 附件id（拍照流程）
     */
    private String fileId;

    /**
     * 证件类型
     */
    private String fileType;

    /**
     * 受理渠道
     */
    private String acceptMode;

    /**
     * 本地回执id
     */
    private String receiptFileId;

    /**
     * 收单时间
     */
    private Date receptTime;

    /**
     * 同步数据账期字段
     */
    private Long acceptDate;

    /**
     * -1=执行出错, 1=已执行，0=未执行, 2=排队中, 3=正在执行
     */
    private Integer execStatus;

    private Date gmtCreate;

    private Date gmtUpdate;

    /**
     * 是否切图
     */
    private Integer isPass;

    /**
     * 活体照片数量
     */
    private Integer livingImageNum;

    /**
     * 证件正面照数量
     */
    private Integer certImageFrontNum;

    /**
     * 证件反面照数量
     */
    private Integer certImageBackNum;

    /**
     * 芯片照片数量
     */
    private Integer certImageFileNum;

    /**
     * 其他照片数量
     */
    private Integer otherImageNum;

    /**
     * pdf文件数
     */
    private Integer pdfNum;

    private String resultMsg;


}
