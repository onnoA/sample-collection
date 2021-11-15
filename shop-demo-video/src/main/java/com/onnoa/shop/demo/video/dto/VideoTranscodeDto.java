package com.onnoa.shop.demo.video.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Description 视频转码信息
 * @author onnoA
 * @date 2021/7/6 23:33
 */
@Data
public class VideoTranscodeDto {
	
	/**id*/
	private String id;
	/**视频UUID*/
	private String videoId;
	/**视频文件存放在服务器上的URL（或key），完整URL或部分URL(未包含域名等部分)；视频文件夹及文件名都与ID一致（规则）*/
	private String fileUrl;
	/**视频播放地址*/
	private String playUrl;
	/**(自动)视频文件大小，MB为单位*/
	private long fileSize;
	/**视频封装格式类型 0:其他  1:MP4*/
	private Integer formatType;
	/**(自动)源视频清晰度类型，目前只限定这些分辨率（0: 其他 11:标清640x360P 12:高清848x480P 13:超清1280x720P 14:蓝光1920x1080P ）*/
	private Integer definitionType;
	/**(自动)视频分辨率宽*/
	private Integer videoWidth;
	/**(自动)视频分辨率高*/
	private Integer videoHeight;
	/**(自动)视频码率*/
	private long vBitrate;
	/**(自动)音频码率*/
	private long aBitrate;
	/**(自动)总码率*/
	private long tBitrate;
	/**状态（0：未发布 1：已发布可用   2：已发布禁用）*/
	private Integer status;
	/**描述*/
	private String descr;
	/**备注*/
	private String memo;
	/**创建者*/
	private String createBy;
	/**创建时间*/
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date createTime;
	/**编辑者*/
	private String updateBy;
	/**编辑时间*/
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date updateTime;
	/**是否已删除（0:否 1：是）*/
	private Integer deleted;
	
	
}
