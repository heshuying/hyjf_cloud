/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.am.config.dao.model.auto.SmsNoticeConfig;
import com.hyjf.am.config.service.SmsNoticeConfigService;
import com.hyjf.am.response.config.SmsNoticeConfigResponse;
import com.hyjf.am.vo.config.SmsNoticeConfigVO;

/**
 * @author fuqiang
 * @version SmsNoticeController, v0.1 2018/5/8 9:30
 */
@RestController
@RequestMapping("/am-config/smsNoticeConfig")
public class SmsNoticeConfigController extends BaseConfigController{
    
    @Autowired
    private SmsNoticeConfigService smsNoticeConfigService;

    /**
     * 根据tplCode查询短信通知配置
     *
     * @param tplCode
     * @return
     */
    @RequestMapping("/findSmsNoticeByCode/{tplCode}")
    public SmsNoticeConfigResponse findSmsNoticeByCode(@PathVariable String tplCode) {
        logger.info("findSmsNoticeByCode start... tplCode is :{}", tplCode);
        SmsNoticeConfigResponse response = new SmsNoticeConfigResponse();
        SmsNoticeConfigVO smsNoticeConfigVO = null;
        SmsNoticeConfig smsNoticeConfig = smsNoticeConfigService.findSmsNoticeByCode(tplCode);
        if (smsNoticeConfig != null) {
            smsNoticeConfigVO = new SmsNoticeConfigVO();
            BeanUtils.copyProperties(smsNoticeConfig, smsNoticeConfigVO);
        }
        logger.info("smsNoticeConfigVO is :{}", smsNoticeConfigVO);
        response.setResult(smsNoticeConfigVO);
        return response;
    }
}
