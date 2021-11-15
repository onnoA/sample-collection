package com.onnoa.shop.demo.video.constant;

/**
 * 视频信息常量
 */
public class VideoInfoConstant {
    /**
     * 视频未发布
     */
    public static final Integer UNPUBLISHED_STATUS=0;

    /**
     * 视频已发布可用
     */
    public static final Integer AVAILABLE_STATUS=1;

    /**
     * 视频已发布禁用
     */
    public static final Integer PROHIBIT_STATUS=2;

    /**
     * 数据库非删除状态
     */
    public static final Integer NOT_DEL_STATUS=0;

    /**
     * 数据库删除状态
     */
    public static final Integer DEL_STATUS=1;

    /**
     * 视频格式
     */
    public static final String FILE_FORMAT_ONE= ".mp4";

    /**
     * 视频格式
     */
    public static final String FILE_FORMAT_TWO= ".mov";

    /**
     * 宽屏标清
     */
    public static final String LANDSCAPE_STANDARD_DEFINITION= "640x360p";

    /**
     * 竖屏标清
     */
    public static final String PORTRAIT_STANDARD_DEFINITION= "360x640p";

    /**
     * 宽屏高清
     */
    public static final String LANDSCAPE_HIGH_DEFINITION= "854x480p";

    /**
     * 竖屏高清
     */
    public static final String PORTRAIT_HIGH_DEFINITION= "480x854p";


    /**
     * 宽屏超清
     */
    public static final String LANDSCAPE_ULTRA_DEFINITION= "1280x720p";

    /**
     * 竖屏超清
     */
    public static final String PORTRAIT_ULTRA_DEFINITION= "720x1280p";


    /**
     * 宽屏蓝光
     */
    public static final String LANDSCAPE_BLURAY_DEFINITION= "1920x1080p";

    /**
     * 竖屏蓝光
     */
    public static final String PORTRAIT_BLURAY_DEFINITION= "1080x1920p";


    /**
     *  消息处理map
     */
    public static final String VS_VIDEO_PROCESSING_VIDEO_PROC_UNFINISHED_HISTORY_MAP = "VS:VIDEO_PROCESSING:VIDEO_PROC_UNFINISHED_HISTORY_MAP";

    /**
     * 消息处理队列名
     */
    public static final String VS_VIDEO_PROCESSING_VIDEO_PROC_UNFINISHED_QUEUE = "VS:VIDEO_PROCESSING:VIDEO_PROC_UNFINISHED_QUEUE";

    /**
     * 视频播放路径后缀
     */
    public static final String PLAURL_SUFFIX = ".urlset/master.m3u8";

    public static final String ORIGINAL = "original";
    /**
     * 请求接口时携带的token认证信息存放map(电商和crm的token)
     */
    public static final String AUTHORIZATION = "VS:VIDEO_PROCESSING:VIDEO_ID_USER_TOKEN_MAPPING_MAP";


    /**
     * 任务队列同步锁
     */
    public static final String TASK_QUEUE_SYNC_LOCK = "VS:VIDEO_PROCESSING:TASK_QUEUE_SYNC_LOCK";
    /**
     * 处理视频后回调CRM视频服务锁
     */
    public static final String TASK_FINISHED_CALLBACK_LOCK = "VS:VIDEO_PROCESSING:TASK_FINISHED_CALLBACK_LOCK";
}
