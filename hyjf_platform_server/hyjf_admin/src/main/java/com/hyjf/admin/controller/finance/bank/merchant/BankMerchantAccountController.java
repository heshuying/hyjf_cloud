/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.finance.bank.merchant;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.BankMerchantAccountService;
import com.hyjf.am.response.admin.BankMerchantAccountResponse;
import com.hyjf.am.resquest.admin.BankMerchantAccountListRequest;
import com.hyjf.am.vo.admin.BankMerchantAccountVO;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.validator.CheckUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author zhangqingqing
 * @version BankMerchantAccountController, v0.1 2018/7/9 16:07
 */
@Api(value = "江西银行商户子账户")
@RestController
@RequestMapping("/hyjf-admin/bank/merchant/account")
public class BankMerchantAccountController extends BaseController {

    @Autowired
    private BankMerchantAccountService bankMerchantAccountService;
    /**
     * 商户子账户列表
     *
     * @param request
     * @param form
     * @return
     */
    @RequestMapping(value = "init")
    public AdminResult init(HttpServletRequest request, @RequestBody BankMerchantAccountListRequest form) {
        AdminSystemVO adminSystem = getUser(request);
        CheckUtil.check(adminSystem!=null, MsgEnum.ERR_USER_NOT_LOGIN);
        // 账户余额总计
        BigDecimal accountBalanceSum = BigDecimal.ZERO;
        // 可用余额总计
        BigDecimal availableBalanceSum = BigDecimal.ZERO;
        // 冻结金额总计
        BigDecimal frostSum = BigDecimal.ZERO;
        form.setUserId(Integer.parseInt(adminSystem.getId()));
        BankMerchantAccountResponse response = bankMerchantAccountService.selectBankMerchantAccount(form);
        if(response == null||response.getRecordTotal()==0) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        List<BankMerchantAccountVO> recordList = response.getResultList();
        // 算统计数据
        for (int i = 0; i < recordList.size(); i++) {
            BankMerchantAccountVO account = recordList.get(i);
            accountBalanceSum = accountBalanceSum.add(account.getAccountBalance());
            availableBalanceSum = availableBalanceSum.add(account.getAvailableBalance());
            frostSum = frostSum.add(account.getFrost());
        }
        form.setRecordList(recordList);
        form.setAccountBalanceSum(String.valueOf(accountBalanceSum));
        form.setAvailableBalanceSum(String.valueOf(availableBalanceSum));
        form.setFrostSum(String.valueOf(frostSum));
        return new AdminResult(form);
    }
}
