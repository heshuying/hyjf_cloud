/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.bean.mc;

import java.io.Serializable;

/**
 * @author fq
 * @version MessagePushMsgHistoryNew, v0.1 2018/7/25 10:46
 */
public class MessagePushMsgHistoryNew implements Serializable {
	private Integer id;

	// private Integer tagId;

	private String title;
	private String introduction;

	private String time;

	private static final long serialVersionUID = 1L;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
}
