/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.am.response.admin.SmsLogResponse;
import com.hyjf.am.response.admin.SmsOntimeResponse;
import com.hyjf.am.resquest.message.SmsLogRequest;
import com.hyjf.am.vo.admin.SmsLogVO;

import java.util.List;

/**
 * @author fuqiang
 * @version SmsLogClient, v0.1 2018/6/23 15:24
 */
public interface SmsLogService {
	/**
	 * 查询消息中心短信发送记录
	 * 
	 * @return
	 */
	SmsLogResponse smsLogList();

	/**
	 * 根据条件查询消息中心短信发送记录
	 * 
	 * @param request
	 * @return
	 */
	SmsLogResponse findSmsLog(SmsLogRequest request);

	/**
	 *查询定时发送短信列表
	 * @param request
	 * @return
	 */
    SmsOntimeResponse queryTime(SmsLogRequest request);

	List<SmsLogVO> findSmsLogList(SmsLogRequest request);
}
