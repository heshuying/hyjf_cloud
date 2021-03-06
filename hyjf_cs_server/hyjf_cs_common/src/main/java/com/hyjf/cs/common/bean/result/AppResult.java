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
import com.hyjf.am.bean.result.BaseResult;
import com.hyjf.common.constants.MsgCode;
import com.hyjf.cs.common.util.Page;

import java.io.Serializable;

/**
 * 返回APP前端结果类
 * 成功返回“0”
 * @author liubin
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppResult<T> extends BaseResult<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	public AppResult() {
	}

	public AppResult(T data) {
		super(data);
	}

	public AppResult(Throwable e) {
		super(e);
	}

	public AppResult(String status, String statusDesc) {
		super(status, statusDesc);
	}

	public AppResult(MsgCode msgCode, Object... params) {
		super(msgCode, params);
	}

	public static final String SUCCESS = "0";
	{
		// 成功码变更 "000" → "0"
		if (super.getStatus() == BaseResult.SUCCESS) {
			super.setStatus(SUCCESS);
		}
	}
    // 分页参数
	private Page page;
	// 跳转前端地址
	private String callBackAction;

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public String getCallBackAction() {
		return callBackAction;
	}

	public void setCallBackAction(String callBackAction) {
		this.callBackAction = callBackAction;
	}
}
