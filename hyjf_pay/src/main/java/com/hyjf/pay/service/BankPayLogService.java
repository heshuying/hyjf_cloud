package com.hyjf.pay.service;

import com.hyjf.pay.entity.ChinapnrExclusiveLog;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallPnrApiBean;

import java.util.Map;

public interface BankPayLogService {
	
	/**
	 * 保存发送日志
	 * @param chinapnrSendlog
	 */
	void saveChinapnrSendLog(BankCallPnrApiBean pnrApiBean, BankCallBean bean);
	
	/**
	 * 保存接受日志
	 * @param chinapnrLog
	 */
	void saveChinapnrLog(BankCallBean bean, int returnType);

	void saveChinapnrLog(BankCallBean bean, Map<String, String> mapMsg, int returnType);

	/**
	 * 
	 * 查询检证日志
	 * 
	 * @param orderId
	 * @return
	 */
	ChinapnrExclusiveLog selectChinapnrExclusiveLogByOrderId(String orderId);

	String insertChinapnrSendLog(BankCallPnrApiBean pnrApiBean, BankCallBean bean);
	
	String insertChinapnrExclusiveLog(BankCallBean bean);
	
	String insertChinapnrLog(BankCallBean bean, int returnType);

	void updateChinapnrExclusiveLog(String orderId, BankCallBean bean, int nowTime);

	void updateChinapnrExclusiveLog(String orderId, int status);

	void insertChinapnrLog(BankCallBean backBean, Map<String, String> mapParam, int i);
}
