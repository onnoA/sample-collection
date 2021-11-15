package com.onnoa.shop.demo.video.exception;


/**
 * @author onnoA
 * @Description
 * @date 2021年07月06日 23:47
 */
public class PicException extends ServiceException {

    public static PicException FAILED_TO_UPLOAD_PICISEMPTY = new PicException(700020001, "上传图片为空,请选择正确图片");
    public static PicException FAILED_TO_UPLOAD_PICIDISEMPTY = new PicException(700020002, "上传图片ID为空,请带上图片id");
    public static PicException FAILED_TO_UPLOAD_PICREAD = new PicException(700020003, "上传图片读取失败,请选择正确图片");

    protected int code;
    protected String msg;

    protected PicException(Integer code, String msg) {
        super(code, msg);
    }


    @Override
    public String toString() {
        return String.format("业务异常=%s, code=%s, msg=%s", this.getClass().getSimpleName(), this.code,
                this.msg);
    }
}
