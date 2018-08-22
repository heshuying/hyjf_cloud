/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service;

import com.hyjf.am.config.dao.model.auto.SmsTemplate;
import com.hyjf.am.resquest.config.SmsTemplateRequest;

import java.util.List;

/**
 * @author fuqiang
 * @version SmsTemplateService, v0.1 2018/5/8 10:23
 */
public interface SmsTemplateService {
    /**
     * 根据tplCode查询短信模板
     *
     * @param tplCode
     * @return
     */
    SmsTemplate findSmsTemplateByCode(String tplCode);

    /**
     * 查询所有短信模板
     *
     * @return
     */
    List<SmsTemplate> findAll();

    /**
     * 根据条件查询所有短信模板
     *
     * @param request
     * @return
     */
    List<SmsTemplate> findSmsTemplate(SmsTemplateRequest request);

    /**
     * 新增短信模板
     *
     * @param request
     */
    void insertSmsTemplate(SmsTemplateRequest request);

    /**
     * 开启短信模板
     * @param request
     */
    void openSmsTemplate(SmsTemplateRequest request);

    /**
     * 关闭短信模板
     * @param request
     */
    void closeSmsTemplate(SmsTemplateRequest request);

    /**
     * 修改短信模版
     * @param request
     */
    void updateSmsTemplate(SmsTemplateRequest request);

    /**
     * 查询总条数
     * @return
     */
    int selectCount(SmsTemplateRequest request);
}
