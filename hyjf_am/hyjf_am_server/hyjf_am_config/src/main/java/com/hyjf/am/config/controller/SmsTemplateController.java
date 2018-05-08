/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.controller;

import com.hyjf.am.config.dao.model.auto.SmsTemplate;
import com.hyjf.am.config.service.SmsTemplateService;
import com.hyjf.am.response.config.SmsTemplateResponse;
import com.hyjf.am.vo.config.SmsTemplateVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 短信模板
 *
 * @author fuqiang
 * @version SmsTemplateController, v0.1 2018/5/8 10:18
 */
@RestController
@RequestMapping("/am-config/smstemplate")
public class SmsTemplateController {

    Logger logger = LoggerFactory.getLogger(SmsTemplateController.class);

    @Autowired
    private SmsTemplateService smsTemplateService;

    /**
     * 根据tplCode查询短信模板
     *
     * @param tplCode
     * @return
     */
    @RequestMapping("/findSmsTemplateByCode")
    public SmsTemplateResponse findSmsTemplateByCode(@PathVariable String tplCode) {
        logger.info("查询短信模板开始...");
        SmsTemplateResponse response = new SmsTemplateResponse();
        SmsTemplateVO smsTemplateVO = null;
        SmsTemplate smsTemplate = smsTemplateService.findSmsTemplateByCode(tplCode);
        if (smsTemplate != null) {
            smsTemplateVO = new SmsTemplateVO();
            BeanUtils.copyProperties(smsTemplate, smsTemplateVO);
        }
        logger.info("smsTemplate is :{}", smsTemplateVO);
        response.setResult(smsTemplateVO);
        return response;
    }
}
