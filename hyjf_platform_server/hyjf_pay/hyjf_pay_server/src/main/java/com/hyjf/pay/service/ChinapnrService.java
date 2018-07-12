package com.hyjf.pay.service;

import com.hyjf.pay.entity.ChinapnrExclusiveLog;
import com.hyjf.pay.entity.ChinapnrLog;
import com.hyjf.pay.lib.PnrApiBean;
import com.hyjf.pay.lib.chinapnr.ChinapnrBean;

/**
 * <p>
 * BaseService
 * </p>
 *
 * @author gogtz
 * @version 1.0.0
 */
public interface ChinapnrService {

	/**
	 * 插入中间日志
	 * 
	 * @param bean
	 * @param methodName
	 * @return
	 */
	 Long insertChinapnrExclusiveLog(ChinapnrBean bean, String methodName);

     void insertChinapnrSendLog(ChinapnrBean bean, PnrApiBean pnrApiBean);

	ChinapnrExclusiveLog selectChinapnrExclusiveLog(long l);

	void insertChinapnrLog(ChinapnrLog chinapnrLog);

	void updateChinapnrExclusiveLog(ChinapnrExclusiveLog record);

	void updateChinapnrExclusiveLog(ChinapnrBean bean, ChinapnrExclusiveLog record, String methodName, String status, String remark);

	void insertChinapnrLog(ChinapnrBean bean, int i);

	ChinapnrExclusiveLog selectChinapnrExclusiveLogByOrderId(String ordId);
}
