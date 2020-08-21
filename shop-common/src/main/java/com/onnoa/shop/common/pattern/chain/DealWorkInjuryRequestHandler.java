package com.onnoa.shop.common.pattern.chain;

import java.math.BigDecimal;

/**
 * @Description:抽象处理者角色
 * @Author: onnoA
 * @Date: 2020/8/14 17:28
 */
public abstract class DealWorkInjuryRequestHandler {

    /**
     * 如果处理不了，设置后继处理者
     */
    protected DealWorkInjuryRequestHandler successor;

    /**
     * 处理请求的方法
     *
     * @param compensationPayments
     */
    public abstract void dealWorkInjury(BigDecimal compensationPayments);

    public void setSuccessor(DealWorkInjuryRequestHandler successor) {
        this.successor = successor;
    }
}
