package com.onnoa.shop.common.distributed.primarykey.serialnumber;

import java.util.List;

/***
 * 流水号生成器接口描述。<br>
 * 注意：序列号模板和备注仅在第一创建时有效，用户初始化数据配置<br>
 * 在第二次执行时，模板使用数据库中已有的，备注也不会被更新到数据库。
 * @author 丁伟
 * @date 2016年3月31日
 * @version 1.0
 */
public interface ISerialNumberGenerator {

	/**
	 * 生成一个流水号
	 *
	 * @param key 单据号产生标识号.每个流水号类型的key不能和其他的流水号的key相同。<br>
	 *            建议命名规则：{模块}_{系统}_{单据类型}
	 * @param template 序列号生成模板.格式如下：<br>
	 *            {string:原样字符串} 说明：原样输出字符串 <br>
	 *            {date:日期格式} 说明：格式化日期输出 <br>
	 *            {sequence:宽度|流水号重置周期} 说明：输出流水号.宽度：流水号宽度。 流水号重置周期包括：day - 按日重置,
	 *            month - 按月重置, year - 按年重置, no - 不重置 <br>
	 *            {rnd:宽度} 说明： 产生随机数字，位数等于宽度<br>
	 *            例如：string:201,date:yyyyMMdd,sequence:6|no,rnd:4
	 *            生成的流水号可能是：2012015101200000012563
	 * @param remark 序列号说明
	 * @return
	 */
	public String generate(String key, String template, String remark);

	/**
	 * 批量生成流水号
	 *
	 * @param key 单据号产生标识号.每个流水号类型的key不能和其他的流水号的key相同。<br>
	 *            建议命名规则：{模块}_{系统}_{单据类型}
	 * @param template 序列号生成模板.格式如下：<br>
	 *            {string:原样字符串} 说明：原样输出字符串 <br>
	 *            {date:日期格式} 说明：格式化日期输出 <br>
	 *            {sequence:宽度|流水号重置周期} 说明：输出流水号.宽度：流水号宽度。 流水号重置周期包括：day - 按日重置,
	 *            month - 按月重置, year - 按年重置, no - 不重置 <br>
	 *            {rnd:宽度} 说明： 产生随机数字，位数等于宽度<br>
	 *            例如：string:201,date:yyyyMMdd,sequence:6|no,rnd:4
	 *            生成的流水号可能是：2012015101200000012563
	 * @param remark 序列号说明
	 * @param count 生成流水号的数量
	 * @return
	 */
	public List<String> generateBatch(String key, String template, String remark, int count);
}
