/**
 * Description:项目详情查询所用vo
 * Copyright: Copyright (HYJF Corporation) 2015
 * Company: HYJF Corporation
 * @author: 王坤
 * @version: 1.0
 * Created at: 2015年11月11日 下午2:17:31
 * Modification History:
 * Modified by : 
 */
package com.hyjf.cs.trade.bean;

import java.io.Serializable;
import java.util.List;

public class BorrowProjectDetailBean implements Serializable {

	/** 序列化id */
	private static final long serialVersionUID = -2913028255458205989L;

	/**
	 * 列表字段id
	 * string
	 * example: basicMsg
	 */
	private String id;
	
	/**
	 * 基础信息
	 * string 
	 * example: 列表字段名第一级
	 */
	private String title;
	
	private List<BorrowMsgBean> msg;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<BorrowMsgBean> getMsg() {
		return msg;
	}

	public void setMsg(List<BorrowMsgBean> msg) {
		this.msg = msg;
	}

}
