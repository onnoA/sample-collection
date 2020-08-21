package com.onnoa.shop.common.pattern.chain;

import java.math.BigDecimal;

/**
 * @Description:  请求发送者角色：老李
 * @Author: onnoA
 * @Date: 2020/8/14 17:30
 */
public class Laoli {

    public static void main(String[] args) {
        DealWorkInjuryRequestHandler lzHandler = new LaoZhaoHandler();
        DealWorkInjuryRequestHandler bossHandler = new BossHandler();
        DealWorkInjuryRequestHandler manageHandler = new ManageHandler();
        DealWorkInjuryRequestHandler contractorHandler = new ContractorHandler();
        lzHandler.setSuccessor(bossHandler);
        bossHandler.setSuccessor(manageHandler);
        manageHandler.setSuccessor(contractorHandler);
        lzHandler.dealWorkInjury(BigDecimal.valueOf(120000));
        System.out.println("-------------------------------------------------\n");
        lzHandler.dealWorkInjury(BigDecimal.valueOf(80000));
        System.out.println("-------------------------------------------------\n");
        lzHandler.dealWorkInjury(BigDecimal.valueOf(20000));
    }
}
