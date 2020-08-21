package com.onnoa.shop.common.pattern.chain;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * @Description: 具体处理者角色：承建公司包工头
 * @Author: onnoA
 * @Date: 2020/8/14 17:30
 */
public class ContractorHandler extends DealWorkInjuryRequestHandler{
    @Override
    public void dealWorkInjury(BigDecimal compensationPayments) {
        if(compensationPayments.compareTo(BigDecimal.valueOf(30000))>0){
            System.out.println("包工头：上面压我，倒霉玩意，"+compensationPayments+"赔偿款，我要赔"+compensationPayments.multiply(BigDecimal.valueOf(0.3), MathContext.DECIMAL32));
        }else {
            System.out.println("包工头：上面压我，倒霉玩意，我要自己掏"+compensationPayments+"赔偿款。");
        }
    }
}
