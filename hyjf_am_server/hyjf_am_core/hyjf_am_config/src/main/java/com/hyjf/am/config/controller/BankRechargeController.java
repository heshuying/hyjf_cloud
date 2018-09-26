package com.hyjf.am.config.controller;

import com.hyjf.am.config.dao.model.auto.BankRechargeConfig;
import com.hyjf.am.config.service.BankRechargeService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.config.BankRechargeConfigResponse;
import com.hyjf.am.vo.config.BankRechargeConfigVo;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author by xiehuili on 2018/7/19.
 */
@RestController
@RequestMapping("/am-config/config/bankrecharge")
public class BankRechargeController  extends BaseConfigController{
    @Autowired
    private BankRechargeService bankRechargeService;

    /**
     * 根据bankId查询BankRechargeConfig
     * @auth sunpeikai
     * @param bankId
     * @return
     */
    @GetMapping(value = "/getBankRechargeConfigByBankId/{bankId}")
    public BankRechargeConfigResponse getBankRechargeConfigByBankId(@PathVariable Integer bankId){
        BankRechargeConfigResponse response = new BankRechargeConfigResponse();
        List<BankRechargeConfig> bankRechargeConfigs = this.bankRechargeService.getBankRechargeConfigByBankId(bankId);
        if(!CollectionUtils.isEmpty(bankRechargeConfigs)){
            BankRechargeConfig bankRechargeConfig = bankRechargeConfigs.get(0);
            BankRechargeConfigVo bankRechargeConfigVo = CommonUtils.convertBean(bankRechargeConfig,BankRechargeConfigVo.class);
            response.setResult(bankRechargeConfigVo);
            response.setRtn(Response.SUCCESS);
            return response;
        }
        return null;
    }
}
