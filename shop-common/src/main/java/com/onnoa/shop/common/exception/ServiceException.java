package com.onnoa.shop.common.exception;

/**
 * @Description: 服务异常
 *         参考 https://www.kancloud.cn/onebase/ob/484204 文章
 *   10 位状态码，分成四段
 *   第一段，1 位，类型
 *         1 - 业务级别异常
 *         2 - 系统级别异常
 *   第二段，3 位，系统类型
 *         000 - 通用模块
 *         001 - 用户系统
 *         002 - 商品系统
 *         003 - 订单系统
 *         004 - 支付系统
 *         005 - 优惠劵系统
 *         ... - ...
 *   第三段，3 位，模块
 *         不限制规则。
 *         一般建议，每个系统里面，可能有多个模块，可以再去做分段。以用户系统为例子：
 *         000 - 通用检验模块
 *         001 - OAuth2 模块
 *         002 - User 模块
 *         003 - MobileCode 模块
 *   第四段，3 位，错误码
 *         不限制规则。
 *         一般建议，每个模块自增。
 * @Author: onnoA
 * @Date: 2020/4/21 10:37
 */
public final class ServiceException extends RuntimeException {

    private final Integer code;

    public ServiceException(Integer code, String msg) {
        super(msg);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }


}
