package com.hyjf.am.po;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xiasq
 * @version ProducerTransactionMessagePO, v0.1 2018/6/27 14:48 生产端事务消息表
 */
public class ProducerTransactionMessagePO implements Serializable {
	private Long id;
	private MessageStatus messageStatus;
	private Date updateTime;
	private Date createTime;
	private Integer sendTimes;
	private String topic;
	private byte[] body;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public MessageStatus getMessageStatus() {
		return messageStatus;
	}

	public void setMessageStatus(MessageStatus messageStatus) {
		this.messageStatus = messageStatus;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getSendTimes() {
		return sendTimes;
	}

	public void setSendTimes(Integer sendTimes) {
		this.sendTimes = sendTimes;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public byte[] getBody() {
		return body;
	}

	public void setBody(byte[] body) {
		this.body = body;
	}
}
