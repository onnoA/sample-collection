package com.onnoa.shop.common.pattern.chain;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * @Description:
 * @Author: onnoA
 * @Date: 2020/8/14 17:31
 */
public class ManageHandler extends DealWorkInjuryRequestHandler  {
    @Override
    public void dealWorkInjury(BigDecimal compensationPayments) {
        if(compensationPayments.compareTo(BigDecimal.valueOf(80000))>0){
            System.out.println("项目经理："+compensationPayments+"赔偿款，按照合同，超过"+compensationPayments.multiply(BigDecimal.valueOf(0.3), MathContext.DECIMAL32)+"公司只负责7成，剩下的由包工头承担。");
        }else {
            System.out.println("项目经理："+compensationPayments+"赔偿款,按照合同，全部由包工头赔偿");
        }
    }
}
