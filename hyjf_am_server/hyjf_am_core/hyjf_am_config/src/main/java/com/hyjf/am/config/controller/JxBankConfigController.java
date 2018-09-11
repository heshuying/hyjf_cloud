/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.controller;

import com.hyjf.am.config.dao.model.auto.JxBankConfig;
import com.hyjf.am.config.service.JxBankConfigService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.JxBankConfigResponse;
import com.hyjf.am.vo.trade.JxBankConfigVO;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author wangjun
 * @version JxBankConfigController, v0.1 2018/7/25 15:55
 */
@RestController
@RequestMapping("/am-config/config")
public class JxBankConfigController {
    @Autowired
    JxBankConfigService jxBankConfigService;
    /**
     * 获取江西银行配置（快捷支付）
     */
    @GetMapping("/getQuickPaymentJxBankConfig")
    public JxBankConfigResponse getQuickPaymentJxBankConfig(){
        JxBankConfigResponse response = new JxBankConfigResponse();
        List<JxBankConfig> list = jxBankConfigService.getQuickPaymentJxBankConfig();
        if(!CollectionUtils.isEmpty(list)){
            List<JxBankConfigVO> voList = CommonUtils.convertBeanList(list, JxBankConfigVO.class);
            response.setResultList(voList);
        }
        return response;
    }

    /**
     * 获取银行卡配置信息
     * @auth sunpeikai
     * @param bankId 主键id
     * @return
     */
    @GetMapping("/getJxBankConfigByBankId1/{bankId}")
    public JxBankConfigResponse getBankConfigByBankId(@PathVariable Integer bankId){
        JxBankConfigResponse response = new JxBankConfigResponse();
        List<JxBankConfig> jxBankConfigList = jxBankConfigService.getJxBankConfigByBankId(bankId);
        if(!CollectionUtils.isEmpty(jxBankConfigList)){
            JxBankConfigVO jxBankConfigVO = CommonUtils.convertBean(jxBankConfigList.get(0),JxBankConfigVO.class);
            response.setResult(jxBankConfigVO);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }
}
