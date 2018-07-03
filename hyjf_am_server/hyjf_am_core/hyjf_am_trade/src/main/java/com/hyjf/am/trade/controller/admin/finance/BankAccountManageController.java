/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.admin.finance;

import com.hyjf.am.trade.service.admin.finance.BankAccountManageService;
import com.hyjf.am.vo.admin.AdminBankAccountCheckCustomizeVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author PC-LIUSHOUYI
 * @version BankAccountManageController, v0.1 2018/6/29 13:55
 */

@Api(value = "银行账户管理")
@RestController
@RequestMapping("/am-trade/bankAccountManage")
public class BankAccountManageController {

    @Autowired
    BankAccountManageService bankAccountManageService;

    /**
     * @Author: liushouyi
     * @Desc :查询总件数
     */
    @ApiOperation(value = "银行账户管理查询总件数")
    @PostMapping("/updateAccount")
    public Integer updateAccount(AccountVO accountVO) {
        Integer count = bankAccountManageService.updateAccount(accountVO);
        return count;
    }
    /**
     * @Author: liushouyi
     * @Desc :查询总件数
     */
    @ApiOperation(value = "银行账户管理查询总件数")
    @PostMapping("/updateaccountcheck")
    public String updateAccountCheck(AdminBankAccountCheckCustomizeVO adminBankAccountCheckCustomizeVO) {
        String result = bankAccountManageService.updateAccountCheck(adminBankAccountCheckCustomizeVO);
        return result;
    }
}
