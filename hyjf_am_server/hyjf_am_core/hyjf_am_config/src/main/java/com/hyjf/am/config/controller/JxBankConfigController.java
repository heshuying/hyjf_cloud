/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.controller;

import com.hyjf.am.config.dao.model.auto.BankConfig;
import com.hyjf.am.config.dao.model.auto.JxBankConfig;
import com.hyjf.am.config.service.BankConfigService;
import com.hyjf.am.config.service.JxBankConfigService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AdminBankConfigResponse;
import com.hyjf.am.response.admin.JxBankConfigResponse;
import com.hyjf.am.vo.trade.BankConfigVO;
import com.hyjf.am.vo.trade.JxBankConfigVO;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    BankConfigService bankConfigService;
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
    @GetMapping("/getJxBankConfigByBankId/{bankId}")
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

    /**
     * 获取充值银行列表
     * @Author : huanghui
     */
    @RequestMapping(value = "/JxBank/selectBankConfigList", method = RequestMethod.GET)
    public JxBankConfigResponse selectBankConfigList(){
        JxBankConfigResponse response = new JxBankConfigResponse();
        List<JxBankConfig> listBankConfig = jxBankConfigService.selectBankConfigList();
        if(null!=listBankConfig&&listBankConfig.size()>0){
            List<JxBankConfigVO> listBanksConfig = CommonUtils.convertBeanList(listBankConfig, JxBankConfigVO.class);
            response.setResultList(listBanksConfig);
            //代表成功
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }
    /**
     * 根据bankName查询银行配置
     * @param bankName
     * @return
     */
    @GetMapping("/selectBankConfigByBankName/{bankName}")
    public AdminBankConfigResponse selectBankConfigByBankName(@PathVariable String bankName){
        AdminBankConfigResponse response = new AdminBankConfigResponse();
        BankConfigVO bankConfig = new BankConfigVO();
        bankConfig.setName(bankName);
        List<BankConfig> list = bankConfigService.selectBankConfigByBankName(bankConfig,-1,-1);
        if(!CollectionUtils.isEmpty(list)){
            List<BankConfigVO> res=CommonUtils.convertBeanList(list,BankConfigVO.class);
            response.setResultList(res);
            response.setRecordTotal(res.size());
            return response;
        }
        return null;
    }
}
