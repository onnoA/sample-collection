package com.onnoa.shop.common.distributed.primarykey.serialnumber;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * 默认的流水号生成器
 * @author 丁伟
 * @date 2016年3月31日
 * @version 1.0
 */
public class DefaultSerialNumberGenerator implements ISerialNumberGenerator {

	@Override
	public String generate(String key, String template, String remark) {
		Map<String,Object> requestBizData = new HashMap<String, Object>();
		requestBizData.put("key", key);
		requestBizData.put("template", template);
		requestBizData.put("remark", remark);

		/*IRemoteServiceAccessor accessor = RemoteCallFactory.createAccessor();
		CooperatorContext result = accessor.remoteCall("common.serialnumber.generate", requestBizData);
		if (result.getCode() == 0) {
			return result.getData().get("serialNo").toString();
		} else {
			throw BizException.RemoteCallBizException(result);
		}*/
		return null;
	}

	@Override
	public List<String> generateBatch(String key, String template, String remark, int count) {
		Map<String, Object> requestBizData = new HashMap<String, Object>();
		requestBizData.put("key", key);
		requestBizData.put("template", template);
		requestBizData.put("remark", remark);
		requestBizData.put("count", count);

		/*IRemoteServiceAccessor accessor = RemoteCallFactory.createAccessor();
		CooperatorContext result = accessor.remoteCall("common.serialnumber.generateBatch", requestBizData);

		if(result.getCode() == 0) {
			@SuppressWarnings("unchecked")
			List<String> list = (List<String>)result.getData().get("serialNos");
			return list;
		} else {
			throw BizException.RemoteCallBizException(result);
		}*/
		return null;
	}

}
