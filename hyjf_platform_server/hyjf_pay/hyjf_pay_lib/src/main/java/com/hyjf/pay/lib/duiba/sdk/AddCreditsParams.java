package com.hyjf.pay.lib.duiba.sdk;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class AddCreditsParams {

	private String appKey;
	private Date timestamp;//时间戳
	private Long credits;//增加积分值
	private String orderNum="";//兑吧订单号
	private String description="";
	private String type="";//积分活类型，活动和签到
	private String uid="";//用户唯一id
	private String ip="";//用户兑换时使用的ip地址，有可能为空
	private String transfer="";//非必须参数

	public Map<String, String> toRequestMap(String appSecret){
		Map<String, String> map=new HashMap<String, String>();
		map.put("credits", credits+"");
		map.put("description", description);
		map.put("uid", uid);
		map.put("appKey", appKey);
		map.put("appSecret", appSecret);
		map.put("timestamp",  System.currentTimeMillis()+"");
		map.put("orderNum", orderNum);
		map.put("type", type);
		map.put("ip", ip);
		putIfNotEmpty(map, "transfer", transfer);
		String sign=SignTool.sign(map);
		map.remove("appSecret");
		map.put("sign", sign);
		return map;
	}

	private void putIfNotEmpty(Map<String, String> map,String key,String value){
		if(value==null || value.length()==0){
			return;
		}
		map.put(key, value);
	}

	public Long getCredits() {
		return credits;
	}
	public void setCredits(Long credits) {
		this.credits = credits;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAppKey() {
		return appKey;
	}
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getTransfer() {
		return transfer;
	}
	public void setTransfer(String transfer) {
		this.transfer = transfer;
	}
}
