/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.controller;

import com.hyjf.am.config.dao.model.auto.SmsTemplate;
import com.hyjf.am.config.service.SmsTemplateService;
import com.hyjf.am.response.config.SmsTemplateResponse;
import com.hyjf.am.resquest.config.SmsTemplateRequest;
import com.hyjf.am.vo.config.SmsTemplateVO;
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
 * 短信模板
 *
 * @author fuqiang
 * @version SmsTemplateController, v0.1 2018/5/8 10:18
 */
@RestController
@RequestMapping("/am-config/smsTemplate")
public class SmsTemplateController extends BaseConfigController{

    @Autowired
    private SmsTemplateService smsTemplateService;

    /**
     * 根据tplCode查询短信模板
     *
     * @param tplCode
     * @return
     */
    @RequestMapping("/findSmsTemplateByCode/{tplCode}")
    public SmsTemplateResponse findSmsTemplateByCode(@PathVariable String tplCode) {
        logger.info("查询短信模板开始... tplCode is :{}", tplCode);
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

    /**
     * 查询所有短信模板
     *
     * @return
     */
	@RequestMapping("/findAll")
	public SmsTemplateResponse findAll() {
		SmsTemplateResponse response = new SmsTemplateResponse();
		List<SmsTemplate> smsTemplateList = smsTemplateService.findAll();
		if (!CollectionUtils.isEmpty(smsTemplateList)) {
			List<SmsTemplateVO> voList = CommonUtils.convertBeanList(smsTemplateList, SmsTemplateVO.class);
			response.setResultList(voList);
		}
		return response;
	}

    /**
     * 根据条件查询短信模板
     *
     * @param request
     * @return
     */
	@RequestMapping("/findSmsTemplate")
	public SmsTemplateResponse findSmsTemplate(@RequestBody SmsTemplateRequest request) {
		SmsTemplateResponse response = new SmsTemplateResponse();
		List<SmsTemplate> smsTemplateList = smsTemplateService.findSmsTemplate(request);
		if (!CollectionUtils.isEmpty(smsTemplateList)) {
			List<SmsTemplateVO> voList = CommonUtils.convertBeanList(smsTemplateList, SmsTemplateVO.class);
			response.setResultList(voList);
		}
		// 查询总条数
        int count = smsTemplateService.selectCount(request);
		response.setCount(count);
		return response;
	}

    /**
     * 添加短信模板
     *
     * @param request
     */
    @RequestMapping("/insertTemplate")
    public void insertSmsTemplate(@RequestBody SmsTemplateRequest request) {
        smsTemplateService.insertSmsTemplate(request);
    }

    /**
     * 开启短信模板
     *
     * @param request
     */
    @RequestMapping("/open_sms_template")
    public void openSmsTemplate(@RequestBody SmsTemplateRequest request) {
        smsTemplateService.openSmsTemplate(request);
    }

    /**
     * 关闭短信模板
     *
     * @param request
     */
    @RequestMapping("/close_sms_template")
    public void closeSmsTemplate(@RequestBody SmsTemplateRequest request) {
        smsTemplateService.closeSmsTemplate(request);
    }

    /**
     * 关闭短信模板
     *
     * @param request
     */
    @RequestMapping("/update_sms_template")
    public void updateSmsTemplate(@RequestBody SmsTemplateRequest request) {
        smsTemplateService.updateSmsTemplate(request);
    }
}
