/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.admin.finance;

import com.hyjf.am.response.admin.BankAccountManageCustomizeResponse;
import com.hyjf.am.resquest.admin.BankAccountManageRequest;
import com.hyjf.am.trade.dao.model.customize.BankAccountManageCustomize;
import com.hyjf.am.trade.service.admin.finance.BankAccountManageService;
import com.hyjf.am.vo.admin.AdminBankAccountCheckCustomizeVO;
import com.hyjf.am.vo.admin.BankAccountManageCustomizeVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version BankAccountManageController, v0.1 2018/6/29 13:55
 */

@Api(value = "银行账户管理")
@RestController
@RequestMapping("/am-trade/bank_account_manage")
public class BankAccountManageController {

    @Autowired
    BankAccountManageService bankAccountManageService;

    /**
     * @Author: liushouyi
     * @Desc :查询总件数
     */
    @ApiOperation(value = "银行账户管理查询总件数")
    @PostMapping("/update_account")
    public Integer updateAccount(AccountVO accountVO) {
        Integer count = bankAccountManageService.updateAccount(accountVO);
        return count;
    }

    /**
     * @Author: liushouyi
     * @Desc :查询总件数
     */
    @ApiOperation(value = "银行账户管理查询总件数")
    @PostMapping("/update_account_check")
    public String updateAccountCheck(AdminBankAccountCheckCustomizeVO adminBankAccountCheckCustomizeVO) {
        String result = bankAccountManageService.updateAccountCheck(adminBankAccountCheckCustomizeVO);
        return result;
    }

    /**
     * @Author: liushouyi
     * @Desc :查询总件数
     */
    @ApiOperation(value = "银行账户管理查询总件数")
    @PostMapping("/query_account_count")
    public Integer queryAccountCount(BankAccountManageRequest bankAccountManageRequest) {
        Integer count = bankAccountManageService.queryAccountCount(bankAccountManageRequest);
        return count;
    }

    /**
     * @Author: liushouyi
     * @Desc :查询列表数据
     */
    @ApiOperation(value = "银行账户管理查询列表")
    @PostMapping("/query_account_infos")
    public BankAccountManageCustomizeResponse queryAccountInfos(BankAccountManageRequest bankAccountManageRequest) {
        BankAccountManageCustomizeResponse response = new BankAccountManageCustomizeResponse();
        List<BankAccountManageCustomize> bankAccountManageCustomizes = bankAccountManageService.queryAccountInfos(bankAccountManageRequest);
        if (null != bankAccountManageCustomizes && bankAccountManageCustomizes.size() > 0) {
            List<BankAccountManageCustomizeVO> bankAccountManageCustomizeVOS = CommonUtils.convertBeanList(bankAccountManageCustomizes, BankAccountManageCustomizeVO.class);
            response.setResultList(bankAccountManageCustomizeVOS);
        }
        return response;
    }
}
