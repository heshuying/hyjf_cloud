/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.service.message;

import com.hyjf.cs.message.bean.mc.SmsOntime;

import java.util.List;

/**
 * @author fuqiang
 * @version MessageServiceImpl, v0.1 2018/6/22 11:14
 */
public interface MessageService {
	/**
	 * 取得定时发送API任务表
	 * 
	 * @param statusWait
	 * @return
	 */
	List<SmsOntime> getOntimeList(Integer statusWait);

	/**
	 * 发送短信
	 * 
	 * @param apicron
	 */
	void sendMessage(SmsOntime apicron) throws Exception;

	/**
	 * 群发生日祝福短信
	 */
	void sendBirthdayBlessSms() throws Exception ;
}
