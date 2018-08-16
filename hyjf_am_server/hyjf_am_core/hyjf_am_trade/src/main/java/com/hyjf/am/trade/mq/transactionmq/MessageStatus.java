package com.hyjf.am.trade.mq.transactionmq;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xiasq
 * @version MessageStatus, v0.1 2018/6/27 14:54
 */
@Deprecated
public enum MessageStatus {
	UNKKOWN(0, "未确认"),
	COMMIT(1, "已确认"),
	ROLLBACK(2, "回滚");

	private int code;
	private String desc;

	MessageStatus(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	private static Map<Integer, MessageStatus> map = new HashMap<Integer, MessageStatus>();

	static {
		for (MessageStatus status : values()) {
			map.put(status.code, status);
		}
	}

	public static MessageStatus getByCode(int code) {
		return map.get(code);
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public static Map<Integer, MessageStatus> getMap() {
		return map;
	}

	public static void setMap(Map<Integer, MessageStatus> map) {
		MessageStatus.map = map;
	}
}
