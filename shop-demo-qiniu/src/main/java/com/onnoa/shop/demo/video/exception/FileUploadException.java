package com.onnoa.shop.demo.video.exception;

import com.onnoa.shop.common.exception.ServiceException;

/**
 * @Description:
 * @Author: onnoA
 * @Date: 2020/6/4 15:12
 */
public class FileUploadException extends ServiceException {

    public static final ServiceException FILE_NOT_ALLOW_NULL = new FileUploadException(300010001, "图片文件不能为空。。");

    public static FileUploadException FAILED_TO_READ_VIDEO_FILE = new FileUploadException(300010002, "解析视频信息失败");

    public static FileUploadException FAILED_TO_RESOLVE_VIDEO = new FileUploadException(300010002, "解析视频信息失败");

    public static FileUploadException VIDEO_UPLOAD_FAILED = new FileUploadException(700010002, "视频上传失败");

    public static FileUploadException FTPFILE_NOT_EXIST = new FileUploadException(700010008, "FTP服务器中当前文件不存在");

    public static FileUploadException FTP_CONNECT_VERIFY = new FileUploadException(700010012, "未连接到FTP，用户名或密码错误。");

    public static FileUploadException FTPFILE_READ = new FileUploadException(700010016, "FTP创建文件夹时异常。");

    public static FileUploadException FTP_CONNECT = new FileUploadException(700010013, "未连接到FTP,未知错误");

    public static FileUploadException FTP_CHANGE_DIRECTORY = new FileUploadException(700010014, "FTP进入文件夹时异常。");

    public static FileUploadException FTP_MAKE_DIRECTORY = new FileUploadException(700010015, "FTP创建文件夹时异常。");

    protected int code;
    protected String msg;

    protected FileUploadException(Integer code, String msg) {
        super(code, msg);
    }


    @Override
    public String toString() {
        return String.format("业务异常=%s, code=%s, msg=%s", this.getClass().getSimpleName(), this.code,
                this.msg);
    }
}
