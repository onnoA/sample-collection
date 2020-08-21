package com.onnoa.shop.common.pattern.chain;

import java.math.BigDecimal;

/**
 * @Description:  具体处理者角色：承建公司大老板
 * @Author: onnoA
 * @Date: 2020/8/14 17:29
 */
public class BossHandler extends DealWorkInjuryRequestHandler{
    @Override
    public void dealWorkInjury(BigDecimal compensationPayments) {
        if(compensationPayments.compareTo(BigDecimal.valueOf(100000))>0){
            System.out.println("大老板"+compensationPayments+"赔偿款，先拖拖，找人也不行。");
        }else {
            System.out.println("大老板"+compensationPayments+"赔偿款，才这点钱，让项目经理去处理。");
            successor.dealWorkInjury(compensationPayments);
        }
    }
}
