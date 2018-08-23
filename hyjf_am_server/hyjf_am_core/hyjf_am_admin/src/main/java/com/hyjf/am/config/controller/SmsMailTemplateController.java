/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.controller;

import com.hyjf.am.config.dao.model.auto.SmsMailTemplate;
import com.hyjf.am.config.service.SmsMailTemplateService;
import com.hyjf.am.response.config.SmsMailTemplateResponse;
import com.hyjf.am.resquest.config.MailTemplateRequest;
import com.hyjf.am.vo.config.SmsMailTemplateVO;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author fuqiang
 * @version SmsMailTemplateController, v0.1 2018/5/8 16:10
 */
@RestController
@RequestMapping("/am-config/smsMailTemplate")
public class SmsMailTemplateController extends BaseConfigController{
    
    @Autowired
    private SmsMailTemplateService smsMailTemplateService;

    @RequestMapping("/findSmsMailByCode/{mailCode}")
    public SmsMailTemplateResponse findSmsMailTemplateByCode(@PathVariable String mailCode) {
        logger.info("查询邮件模板开始...");
        SmsMailTemplateResponse response = new SmsMailTemplateResponse();
        SmsMailTemplateVO smsMailTemplateVO = null;
        SmsMailTemplate smsMailTemplate = smsMailTemplateService.findSmsMailTemplateByCode(mailCode);
        if (smsMailTemplate != null) {
            smsMailTemplateVO = new SmsMailTemplateVO();
            BeanUtils.copyProperties(smsMailTemplate, smsMailTemplateVO);
        }
        logger.info("smsMailTemplateVO is :{}", smsMailTemplateVO);
        response.setResult(smsMailTemplateVO);
        return response;
    }

    /**
     * 查询所有邮件模板
     *
     * @return
     */
    @RequestMapping("/findAll")
    public SmsMailTemplateResponse findAll() {
        SmsMailTemplateResponse response = new SmsMailTemplateResponse();
        List<SmsMailTemplate> list = smsMailTemplateService.findAll();
        if (!CollectionUtils.isEmpty(list)) {
            List<SmsMailTemplateVO> voList = CommonUtils.convertBeanList(list, SmsMailTemplateVO.class);
            response.setResultList(voList);
        }
        return response;
    }

    /**
     * 根据条件查询邮件模板
     *
     * @param request
     * @return
     */
    @RequestMapping("/findMailTemplate")
    public SmsMailTemplateResponse findMailTemplate(@RequestBody MailTemplateRequest request) {
        SmsMailTemplateResponse response = new SmsMailTemplateResponse();
        List<SmsMailTemplate> list = smsMailTemplateService.findSmsTemplate(request);
        if (!CollectionUtils.isEmpty(list)) {
            List<SmsMailTemplateVO> voList = CommonUtils.convertBeanList(list, SmsMailTemplateVO.class);
            response.setResultList(voList);
        }
        return response;
    }

    /**
     * 添加短信模板
     *
     * @param request
     */
    @RequestMapping("/insertMailTemplate")
    public void insertMailTemplate(@RequestBody MailTemplateRequest request) {
        smsMailTemplateService.insertMailTemplate(request);
    }

    /**
     * 修改短信模板
     *
     * @param request
     */
    @RequestMapping("/update_mail_template")
    public void updateMailTemplate(@RequestBody MailTemplateRequest request) {
        smsMailTemplateService.updateMailTemplate(request);
    }

    /**
     * 关闭短信模板
     *
     * @param request
     */
    @RequestMapping("/close_action")
    public void closeMailTemplate(@RequestBody MailTemplateRequest request) {
        smsMailTemplateService.closeMailTemplate(request);
    }

    /**
     * 开启短信模板
     * @param request
     */
    @RequestMapping("/open_action")
    public void openMailTemplate(@RequestBody MailTemplateRequest request) {
        smsMailTemplateService.openMailTemplate(request);
    }

}
