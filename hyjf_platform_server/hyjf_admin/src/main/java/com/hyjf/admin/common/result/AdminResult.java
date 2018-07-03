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
	
package com.hyjf.admin.common.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hyjf.common.constants.MsgCode;

import java.io.Serializable;

/**
 * 返回WEB前端结果类
 * 成功返回“000”
 * @author liubinWE
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdminResult<T> extends BaseResult<T> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AdminResult() {
		super();
	}

	public AdminResult(MsgCode msgCode, Object... params) {
		super(msgCode, params);
	}

	public AdminResult(String status, String statusDesc) {
		super(status, statusDesc);
	}

	public AdminResult(T data) {
		super(data);
	}

	public AdminResult(Throwable e) {
		super(e);
	}

}
