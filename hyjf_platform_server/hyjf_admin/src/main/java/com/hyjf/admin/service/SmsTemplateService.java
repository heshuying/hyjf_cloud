/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.am.response.config.SmsTemplateResponse;
import com.hyjf.am.resquest.config.SmsTemplateRequest;
import com.hyjf.am.vo.config.SmsTemplateVO;

import java.util.List;

/**
 * @author fuqiang
 * @version SmsTemplateService, v0.1 2018/6/25 10:13
 */
public interface SmsTemplateService {
	/**
	 * 查询所有短信模版
	 * 
	 * @return
	 */
	List<SmsTemplateVO> findAll();

	/**
	 * 根据条件查询所有短信模版
	 *
	 * @param request
	 * @return
	 */
	SmsTemplateResponse findSmsTemplate(SmsTemplateRequest request);

	/**
	 * 新增短信模版
	 *
	 * @param request
	 * @return
	 */
    void insertSmsTemplate(SmsTemplateRequest request);

	/**
	 * 开启短信模板
	 * @param request
	 */
	void openAction(SmsTemplateRequest request);

	/**
	 * 关闭短信模板
	 * @param request
	 */
	void closeAction(SmsTemplateRequest request);

	/**
	 * 修改短信模版
	 * @param request
	 */
    void updateSmsTemplate(SmsTemplateRequest request);
}
