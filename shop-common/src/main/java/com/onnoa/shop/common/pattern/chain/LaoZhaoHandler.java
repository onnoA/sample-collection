package com.onnoa.shop.common.pattern.chain;

import java.math.BigDecimal;

/**
 * @Description: 具体处理者角色：老赵
 * @Author: onnoA
 * @Date: 2020/8/14 17:29
 */
public class LaoZhaoHandler extends DealWorkInjuryRequestHandler {
    @Override
    public void dealWorkInjury(BigDecimal compensationPayments) {
        System.out.println("老赵：老李别担心，赔偿款一共"+compensationPayments+"元，我已经找了他们大老板去处理了。");
        successor.dealWorkInjury(compensationPayments);
    }
}
