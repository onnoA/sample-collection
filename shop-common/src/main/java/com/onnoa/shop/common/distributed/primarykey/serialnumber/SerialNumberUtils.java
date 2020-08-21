package com.onnoa.shop.common.distributed.primarykey.serialnumber;

import java.util.HashMap;
import java.util.List;

/**
 * 序列号产生工具。<br>
 * 注意：序列号模板和备注仅在第一创建时有效，用户初始化数据配置<br>
 * 在第二次执行时，模板使用数据库中已有的，备注也不会被更新到数据库。
 *
 */
public abstract class SerialNumberUtils {

	private static ISerialNumberGenerator snGenerator = new DefaultSerialNumberGenerator();

	/** 添加单据系统默认产生规则 */
	static {
		snRuleTable = new HashMap<>();

		/*** 支付结算平台(1) ****/
		addRule("pay_order_no", "string:101,date:yyyyMMdd,sequence:10|day,rnd:4", "支付流水号.支付平台对外流水单据号");
		/*addRule("pay_balance_trade_no", "string:102,date:yyyyMMdd,sequence:10|day,rnd:4", "余额交易流水号");
		addRule("pay_refund_apply_no", "string:103,date:yyyyMMdd,sequence:8|day,rnd:4", "退款流水号");
		addRule("pay_statement_no", "string:104,date:yyyyMMdd,sequence:2|day,rnd:4", "对账单号");
		addRule("pay_settlement_no", "string:105,date:yyyyMMdd,sequence:2|day,rnd:4", "结算单号");

		*//*** 基础平台(2) ***//*
		addRule("base_coupon_batch_code", "string:201,date:yyyyMMdd,sequence:6|year,rnd:4", "优惠券批次编码");
		addRule("base_coupon_instance_code", "date:yy,sequence:7|year,rnd:3", "优惠券实例编码");
		addRule("base_push_template_code", "string:203,date:yyyyMMdd,sequence:6|year,rnd:4", "推送模板编码");
		addRule("base_deposit_card_id", "string:204,date:yyyyMMdd,sequence:6|year,rnd:4", "储蓄卡编码");
		addRule("base_deposit_trade_no", "string:205,date:yyyyMMdd,sequence:6|year,rnd:4", "储蓄卡交易流水号");
		addRule("base_deposit_refund_no", "string:206,date:yyyyMMdd,sequence:6|year,rnd:4", "储蓄卡退款流水号");
		addRule("base_card_batch_code", "string:207,date:yyyyMMdd,sequence:6|year,rnd:4", "卡批次编码");
		addRule("base_card_instance_code", "date:yy,sequence:7|year,rnd:3", "卡实例编码");

		*//*** 电商(3) ***//*
		addRule("eshop_order_no", "string:301,date:yyyyMMdd,sequence:10|year,rnd:4", "电商通用订单流水号");
		addRule("eshop_after_sale_no", "string:302,date:yyyyMMdd,sequence:10|year,rnd:4", "电商售后流水号");
		addRule("eshop_saling_order_no", "string:303,date:yyyyMMdd,sequence:10|year,rnd:4", "电商售中流水号");
		addRule("eshop_order_parent_no", "string:3011,date:yyyyMMdd,sequence:10|year,rnd:4", "电商主订单流水号");
		addRule("eshop_order_child_no", "string:3012,date:yyyyMMdd,sequence:10|year,rnd:4", "电商次订单流水号");
		addRule("eshop_order_self_pickup_code", "date:yyyyMMdd,sequence:2|year,rnd:2", "电商商品自提码");
		addRule("eshop_coffee_order_code", "string:3013,date:yyyyMMdd,sequence:5|day,rnd:3", "无人咖啡机订单流水号");

		*//*** 智能硬件(4) ***//*
		addRule("parking_order_no", "string:401,date:yyyyMMdd,sequence:10|year,rnd:4", "停车场订单流水号");
		addRule("parking_ajbcoupon_no", "string:402,date:yyyyMMdd,sequence:10|year,rnd:4", "安居宝停车场优惠券流水号");
        addRule("parking_yuebao_order_no", "string:403,date:yyyyMMdd,sequence:10|day,rnd:4", "续月保订单流水号");

		*//*** 微信 (5) ***//*

		*//*** 运营平台(6) ***//*

		*//*** 小Q平台(7)  ***//*
		addRule("xiaoq_order_no", "string:701,date:yyyyMMdd,sequence:10|year,rnd:4", "小Q通用订单号");
		addRule("xiaoq_trade_no", "string:702,date:yyyyMMdd,sequence:10|year,rnd:4", "小Q通用交易流水号");
		addRule("xiaoq_bar_code_no", "string:77,sequence:6|day,rnd:10", "小Q BarCode流水号");
		addRule("xiaoq_refund_apply_no", "string:703,date:yyyyMMdd,sequence:6|year,rnd:4", "xiaoq业务退款申请号");
		addRule("xiaoq_card_batch_no", "string:704,date:yyyyMMdd,sequence:6|year,rnd:4", "小Q card批次号");
		addRule("xiaoq_card_instance_no", "string:705,date:yy,sequence:7|year,rnd:3", "小Q card实例号");
		addRule("xiaoq_point_goods_no", "string:706,date:yy,sequence:7|year,rnd:3", "小Q 积分商品实例号");
		addRule("xiaoq_id_code_no", "string:707,date:yy,sequence:7|year,rnd:3", "小Q 积分商品表的id");
		addRule("xiaoq_pointrecord_trade_no", "string:708,date:yy,sequence:7|year,rnd:3", "小Q 兑换积分的记录表的流水号");

		*//*** U星币支付平台(8) ****//*
		addRule("ucoin_order_no", "string:801,date:yyyyMMdd,sequence:10|day,rnd:4", "U星币支付订单号");
		addRule("ucoin_trade_no", "string:802,date:yyyyMMdd,sequence:10|day,rnd:4", "U星币交易流水号");
		addRule("ucoin_statement_no", "string:803,date:yyyyMMdd,sequence:2|day,rnd:4", "对账单号");
		addRule("ucoin_settlement_no", "string:804,date:yyyyMMdd,sequence:2|day,rnd:4", "结算单号");

		*//*** 溜冰场平台(9) ***//*
		addRule("ice_order_no", "string:901,date:yyyyMMdd,sequence:10|year,rnd:4", "溜冰场通用订单号");
		addRule("ice_trade_no", "string:902,date:yyyyMMdd,sequence:10|year,rnd:4", "溜冰场通用交易流水号");
		addRule("ice_bar_code_no", "string:99,sequence:6|day,rnd:10", "溜冰场 BarCode流水号");
		addRule("ice_refund_apply_no", "string:903,date:yyyyMMdd,sequence:6|year,rnd:4", "溜冰场业务退款申请号");
		addRule("ice_card_batch_no", "string:904,date:yyyyMMdd,sequence:6|year,rnd:4", "溜冰场 card批次号");
		addRule("ice_card_instance_no", "string:905,date:yy,sequence:7|year,rnd:3", "溜冰场 card实例号");
		addRule("ice_deposit_card_no", "string:906,date:yyyyMMdd,sequence:6|year,rnd:4", "溜冰场储蓄卡编码");
		addRule("ice_deposit_card_trade_no", "string:907,date:yyyyMMdd,sequence:6|year,rnd:4", "溜冰场储蓄卡交易流水号");
		addRule("ice_pay_order_no", "string:908,date:yyyyMMdd,sequence:10|year,rnd:4", "溜冰场通用支付订单号");

		*//*** 进销存平台(10) ***//*
		addRule("erp_vendor_order_no", "string:CGD,date:yyyyMMdd,sequence:8|day,rnd:0", "进销存系统通用采购单号");
		addRule("erp_inventory_in_no", "string:RK,date:yyyyMMdd,sequence:8|day,rnd:0", "进销存系统通用入库单号");
		addRule("erp_inventory_out_no", "string:CK,date:yyyyMMdd,sequence:8|day,rnd:0", "进销存系统通用出库单号");
		addRule("erp_transfer_no", "string:DB,date:yyyyMMdd,sequence:8|day,rnd:0", "进销存系统通用调拨单号");
		addRule("erp_breakage_no", "string:BS,date:yyyyMMdd,sequence:8|day,rnd:0", "进销存系统通用报损单号");
		addRule("erp_stocktake_no", "string:PD,date:yyyyMMdd,sequence:8|day,rnd:0", "进销存系统通用盘点单号");
		addRule("erp_stocktake_different_no", "string:PDCY,date:yyyyMMdd,sequence:8|day,rnd:0", "进销存系统通用盘点差异单号");*/

		/*** 其他、第三方(11) ***/

	}

	public static final String getPayOrderNo() {
		return genSn("pay_order_no");
	}

	public static void main(String[] args) {
		String payOrderNo = getPayOrderNo();
		System.out.println(payOrderNo);
	}
	/*public static final String getEshopOrderNo() {
		return genSn("eshop_order_no");
	}

	public static final String getCouponBatchCode() {
		return genSn("base_coupon_batch_code");
	}

	public static final List<String> getCouponInstanceCodeByBatch(int batchNum) {
        return genBatchSn("base_coupon_instance_code", batchNum);
	}

	public static final String getCouponInstanceCode() {
		return genSn("base_coupon_instance_code");
	}

	public static final String getCardBatchCode() {
		return genSn("base_card_batch_code");
	}

	public static final String getCardInstanceCode() {
		return genSn("base_card_instance_code");
	}

	public static final String getPushTemplateCode() {
		return genSn("base_push_template_code");
	}

	public static final String getDepositCardId(){
		return genSn("base_deposit_card_id");
	}

	public static final String getDepositCardTradeNo(){
		return genSn("base_deposit_trade_no");
	}

	public static final String getDepositCardRefundNo(){
		return genSn("base_deposit_refund_no");
	}

	public static final String getXiaoqRefundApplyNo(){
		return genSn("xiaoq_refund_apply_no");
	}
	public static final String getXiaoqgoodsPointNo(){
		return genSn("xiaoq_point_goods_no");
	}
	public static final String getXiaoqPointRecordTradeNo(){
		return genSn("xiaoq_pointrecord_trade_no");
	}
	public static final String getXiaoqidCodeNo(){
		return genSn("xiaoq_id_code_no");
	}

	public static final String getXiaoqOrderNo() {
		return genSn("xiaoq_order_no");
	}

	public static final String getXiaoqTradeNo() {
		return genSn("xiaoq_trade_no");
	}
	public static final String getXiaoqbarCodeNo() {
		return genSn("xiaoq_bar_code_no");
	}

	public static final String getEshopAfterSaleNo() {
		return genSn("eshop_after_sale_no");
	}

	public static final String getEshopSalingOrderNo() {
		return genSn("eshop_saling_order_no");
	}

	public static final String getEshopOrderParentNo() {
		return genSn("eshop_order_parent_no");
	}

	public static final String getEshopOrderChildNo() {
		return genSn("eshop_order_child_no");
	}

	public static final String getEshopOrderSelfPickupCode() {
		return genSn("eshop_order_self_pickup_code");
	}

	public static final String getUcoinOrderNo() {
		return genSn("ucoin_order_no");
	}

	public static final String getUcoinTradeNo() {
		return genSn("ucoin_trade_no");
	}

	public static final String getIceOrderNo() {
		return genSn("ice_order_no");
	}

	public static final String getIceRefundApplyNo(){
		return genSn("ice_refund_apply_no");
	}

	public static final String getIcePayOrderNo() {
		return genSn("ice_pay_order_no");
	}

	public static final String getIceDepositCardNo() {
		return genSn("ice_deposit_card_no");
	}

	public static final String getIceDepositCardTradeNo(){
		return genSn("ice_deposit_card_trade_no");
	}

	public static final String getIceTradeOrderNo(){
		return genSn("ice_trade_no");
	}

	public static final String getIcebarCodeNo() {
		return genSn("ice_bar_code_no");
	}

	public static final String getErpVendorOrderNo() {
		return genSn("erp_vendor_order_no");
	}

	public static final String getErpInventoryInNo() {
		return genSn("erp_inventory_in_no");
	}

	public static final String getErpInventoryOutNo() {
		return genSn("erp_inventory_out_no");
	}

	public static final String getErpTransferNo() {
		return genSn("erp_transfer_no");
	}

	public static final String getErpBreakageNo() {
		return genSn("erp_breakage_no");
	}

	public static final String getErpStocktakeNo() {
		return genSn("erp_stocktake_no");
	}

	public static final String getErpStocktakeDifferentNo() {
		return genSn("erp_stocktake_different_no");
	}

    *//**
     * 生成无人咖啡机订单流水号
     * @return
     *//*
	public static final String getCoffeeOrderNo() {
		return genSn("eshop_coffee_order_code");
	}

    *//**
     * 生成续月保订单流水号
     *//*
    public static String getYueBaoOrderNo() {
        return genSn("parking_yuebao_order_no");
    }*/
	/***
	 * 根据规则key 产生一个序列号
	 *
	 * @param key
	 * @return
	 */
	public static String genSn(String key) {
		SnRule rule = snRuleTable.get(key);
		if (rule == null) {
			throw new RuntimeException("流水表编号不存在");
		}
		return snGenerator.generate(rule.getKey(), rule.getRule(), rule.getRemark());
	}

	/***
	 * 根据 规则key ，批量产生序列号
	 *
	 * @param key
	 * @param count
	 *            产生的数量
	 * @return
	 */
	public static List<String> genBatchSn(String key, Integer count) {
		count = (count == null || count <= 0) ? 1 : count;
		SnRule rule = snRuleTable.get(key);
		if (rule == null) {
			throw new RuntimeException("流水表编号不存在");
		}
		return snGenerator.generateBatch(rule.getKey(), rule.getRule(), rule.getRemark(), count);
	}

	/** 序列号规则 */
	private static class SnRule {
		private String key;
		private String rule;
		private String remark;

		public SnRule(String key, String rule, String remark) {
			super();
			this.key = key;
			this.rule = rule;
			this.remark = remark;
		}

		public String getKey() {
			return key;
		}

		public String getRule() {
			return rule;
		}

		public String getRemark() {
			return remark;
		}
	}

	/***
	 * 序列号生成规则表
	 */
	private static HashMap<String, SnRule> snRuleTable;

	/***
	 * 添加规则
	 *
	 *            前缀
	 * @param key
	 * @param rule
	 * @param remark
	 */
	private static void addRule(String key, String rule, String remark) {
		snRuleTable.put(key, new SnRule(key, rule, remark));
	}
}
