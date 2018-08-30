/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.admin.trade;

import com.hyjf.am.response.admin.AdminMerchantAccountSumCustomizeResponse;
import com.hyjf.am.trade.dao.model.customize.AdminMerchantAccountSumCustomize;
import com.hyjf.am.trade.service.admin.account.AdminMerchantAccountService;
import com.hyjf.am.vo.admin.AdminMerchantAccountSumCustomizeVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangqingqing
 * @version MerchantAccountController, v0.1 2018/8/29 21:12
 */

@RestController
@RequestMapping("/am-trade/merchantAccount")
public class MerchantAccountController  {

    @Autowired
    AdminMerchantAccountService merchantAccountService;

    @GetMapping(value = "/searchAccountSum")
    public AdminMerchantAccountSumCustomizeResponse searchAccountSum() {
        AdminMerchantAccountSumCustomizeResponse response = new AdminMerchantAccountSumCustomizeResponse();
        AdminMerchantAccountSumCustomize adminMerchantAccountSumCustomize = merchantAccountService.searchAccountSum();
        AdminMerchantAccountSumCustomizeVO adminMerchantAccountSumCustomizeVO = new AdminMerchantAccountSumCustomizeVO();
        BeanUtils.copyProperties(adminMerchantAccountSumCustomize, adminMerchantAccountSumCustomizeVO);
        response.setResult(adminMerchantAccountSumCustomizeVO);
        return response;
    }
}
