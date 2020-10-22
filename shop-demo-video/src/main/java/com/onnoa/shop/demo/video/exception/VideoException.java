package com.onnoa.shop.demo.video.exception;

import com.onnoa.shop.common.exception.ServiceException;

/**
 * @Description:
 * @Author: onnoA
 * @Date: 2020/6/4 15:12
 */
public class VideoException extends ServiceException {

    public static final ServiceException VIDEO_FORMAT_ABNORMAL = new VideoException(300010001, "暂不支持:{}格式的视频。");

    public static VideoException FAILED_TO_READ_VIDEO_FILE = new VideoException(300010002, "解析视频信息失败");

    public static VideoException FAILED_TO_RESOLVE_VIDEO = new VideoException(300010002, "解析视频信息失败");

    public static VideoException VIDEO_UPLOAD_FAILED = new VideoException(700010002, "视频上传失败");

    public static VideoException FTPFILE_NOT_EXIST = new VideoException(700010008, "FTP服务器中当前文件不存在");

    public static VideoException FTP_CONNECT_VERIFY = new VideoException(700010012, "未连接到FTP，用户名或密码错误。");

    public static VideoException FTPFILE_READ = new VideoException(700010016, "FTP创建文件夹时异常。");

    public static VideoException FTP_CONNECT = new VideoException(700010013, "未连接到FTP,未知错误");

    public static VideoException FTP_CHANGE_DIRECTORY = new VideoException(700010014, "FTP进入文件夹时异常。");

    public static VideoException FTP_MAKE_DIRECTORY = new VideoException(700010015, "FTP创建文件夹时异常。");

    protected int code;
    protected String msg;

    protected VideoException(Integer code, String msg) {
        super(code, msg);
    }


    @Override
    public String toString() {
        return String.format("业务异常=%s, code=%s, msg=%s", this.getClass().getSimpleName(), this.code,
                this.msg);
    }
}
