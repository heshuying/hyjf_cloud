/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.controller.admin.message;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.am.config.controller.BaseConfigController;
import com.hyjf.am.config.dao.model.auto.SmsMailTemplate;
import com.hyjf.am.config.service.SmsMailTemplateService;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.config.SmsMailTemplateResponse;
import com.hyjf.am.resquest.config.MailTemplateRequest;
import com.hyjf.am.vo.config.SmsMailTemplateVO;
import com.hyjf.common.util.CommonUtils;

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
            // 查询总条数
            int count = smsMailTemplateService.selectCount(request);
            response.setResultList(voList);
            response.setCount(count);
        }
        return response;
    }

    /**
     * 添加短信模板
     *
     * @param request
     */
    @RequestMapping("/insertMailTemplate")
    public IntegerResponse insertMailTemplate(@RequestBody MailTemplateRequest request) {
        int num = smsMailTemplateService.insertMailTemplate(request);
        return new IntegerResponse(num);
    }

    /**
     * 修改短信模板
     *
     * @param request
     */
    @RequestMapping("/update_mail_template")
    public IntegerResponse updateMailTemplate(@RequestBody MailTemplateRequest request) {
        int num = smsMailTemplateService.updateMailTemplate(request);
        return new IntegerResponse(num);
    }

    /**
     * 关闭短信模板
     *
     * @param request
     */
    @RequestMapping("/update_status")
    public IntegerResponse updateStatus(@RequestBody MailTemplateRequest request) {
        int num = smsMailTemplateService.updateStatus(request);
        return new IntegerResponse(num);
    }

    @RequestMapping("/find_by_id/{id}")
    public SmsMailTemplateResponse findById(@PathVariable Integer id) {
        SmsMailTemplateResponse response = new SmsMailTemplateResponse();
        SmsMailTemplate smsMailTemplate = smsMailTemplateService.findByid(id);
        if (smsMailTemplate != null) {
            SmsMailTemplateVO vo = new SmsMailTemplateVO();
            BeanUtils.copyProperties(smsMailTemplate, vo);
            response.setResult(vo);
        }
        return response;
    }
}
