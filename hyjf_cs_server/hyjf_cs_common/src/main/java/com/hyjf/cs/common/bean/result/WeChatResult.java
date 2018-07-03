/**
 * Description:（类功能描述-必填） 需要在每个方法前添加业务描述，方便业务业务行为的BI工作
 * Copyright: Copyright (HYJF Corporation)2017
 * Company: HYJF Corporation
 * @author: liubin
 * @version: 1.0
 * Created at: 2017年9月11日 下午4:48:51
 * Modification History:
 * Modified by : 
 */
	
package com.hyjf.cs.common.bean.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hyjf.common.constants.MsgCode;
import com.hyjf.common.enums.MsgEnum;

import java.io.Serializable;

/**
 * 返回Wechat前端结果类
 * 成功返回“000”
 * @author liubin
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WeChatResult<T> extends BaseResult<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	public WeChatResult() {
	}

	public WeChatResult(T data) {
		super(data);
	}

	public WeChatResult(Throwable e) {
		super(e);
	}

	public WeChatResult(String status, String statusDesc) {
		super(status, statusDesc);
	}

	public WeChatResult(MsgCode msgCode, Object... params) {
		super(msgCode, params);
	}

	public  WeChatResult  buildErrorResponse(MsgEnum msgEnum){
		WeChatResult weChatResult = new WeChatResult();
		weChatResult.setStatusDesc(FAIL);
		weChatResult.setStatusDesc(FAIL_DESC);
		return weChatResult;
	}
}
