/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.finance.merchant.account;

import com.hyjf.admin.beans.request.MerchantAccountListBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.MerchantAccountService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.MerchantAccountResponse;
import com.hyjf.am.resquest.admin.MerchantAccountListRequest;
import com.hyjf.am.vo.admin.AdminMerchantAccountSumCustomizeVO;
import com.hyjf.am.vo.admin.MerchantAccountVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * @author zhangqingqing
 * @version MerchantAccountController, v0.1 2018/7/5 10:01
 */
@Api(value = "资金中心-平台账户-账户信息",tags ="资金中心-平台账户-账户信息")
@RestController
@RequestMapping("/hyjf-admin/merchant/account")
public class MerchantAccountController extends BaseController {

    public static final String PERMISSIONS = "merchantaccountlist";

    @Autowired
    MerchantAccountService merchantAccountService;

    /**
     * 商户子账户列表
     * @param form
     * @return
     */
    @ApiOperation(value = "账户信息",notes = "账户信息")
    @PostMapping(value = "/accountList")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult init(@RequestBody MerchantAccountListBean form) {
        MerchantAccountListRequest request = new MerchantAccountListRequest();
        BeanUtils.copyProperties(form, request);
        //查询商户配置表相应的账户配置
        MerchantAccountResponse merchantAccounts = merchantAccountService.selectRecordList(request);
        if(merchantAccounts == null||merchantAccounts.getRecordTotal()==0) {
            return new AdminResult<>(ListResult.build(null,0));
        }
        //更新商户子账户的金额信息
        /*boolean flag = this.merchantAccountService.updateMerchantAccount(merchantAccounts.getResultList());
        if(!flag){
            return new AdminResult<>(ListResult.build(null,0));
        }*/
        if (!Response.isSuccess(merchantAccounts)) {
            return new AdminResult<>(FAIL, merchantAccounts.getMessage());
        }
        AdminMerchantAccountSumCustomizeVO accoutSum = merchantAccountService.searchAccountSum();
        MerchantAccountVO merchantAccountVO = new MerchantAccountVO();
        merchantAccountVO.setAccountBalance(new BigDecimal(accoutSum.getAccountBalanceSum()));
        merchantAccountVO.setAvailableBalance(new BigDecimal(accoutSum.getAvailableBalanceSum()));
        merchantAccountVO.setFrost(new BigDecimal(accoutSum.getFrostSum()));
        return new AdminResult<>(ListResult.build2(merchantAccounts.getResultList(), merchantAccounts.getRecordTotal(),merchantAccountVO)) ;
    }
}
