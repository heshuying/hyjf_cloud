package com.hyjf.am.config.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.am.config.service.BankInterfaceService;
import com.hyjf.am.response.trade.BankInterfaceResponse;

/**
 * @author pangchengchao
 * @version BankInterfaceController, v0.1 2018/6/22 14:28
 */
@RestController
@RequestMapping("/am-config/bankInterface")
public class BankInterfaceController extends BaseConfigController{

    @Autowired
    private BankInterfaceService bankInterfaceService;
    /**
     * 获取接口切换标识
     * @param type
     * @return
     */
    @GetMapping("/getBankInterfaceFlagByType/{type}")
    public BankInterfaceResponse getBanksConfigByBankId(@PathVariable String type){
        BankInterfaceResponse response = new BankInterfaceResponse();
        Integer flag = bankInterfaceService.getBanksConfigByBankId(type);
        response.setFlag(flag);
        return response;
    }
}
