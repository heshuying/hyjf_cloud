/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.finance.merchant.account;

import com.hyjf.admin.beans.request.MerchantAccountListBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.MerchantAccountService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.MerchantAccountResponse;
import com.hyjf.am.resquest.admin.MerchantAccountListRequest;
import com.hyjf.am.vo.admin.MerchantAccountVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangqingqing
 * @version MerchantAccountController, v0.1 2018/7/5 10:01
 */
@Api(value = "商户子账户信息")
@RestController
@RequestMapping("/admin/merchant/account")
public class MerchantAccountController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(MerchantAccountController.class);
    @Autowired
    MerchantAccountService merchantAccountService;

    /**
     * 商户子账户列表
     * @param form
     * @return
     */
    @ApiOperation(value = "商户子账户列表",notes = "商户子账户列表")
    @PostMapping(value = "/accountList")
    public AdminResult init(@RequestBody MerchantAccountListBean form) {
        MerchantAccountListRequest request = new MerchantAccountListRequest();
        BeanUtils.copyProperties(form, request);
        //查询商户配置表相应的账户配置
        MerchantAccountResponse merchantAccounts = merchantAccountService.selectRecordList(request);
        if(merchantAccounts == null||merchantAccounts.getRecordTotal()==0) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        //更新商户子账户的金额信息
        boolean flag = this.merchantAccountService.updateMerchantAccount(merchantAccounts.getResultList());
        if(!flag){
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(merchantAccounts)) {
            return new AdminResult<>(FAIL, merchantAccounts.getMessage());
        }
        return new AdminResult<ListResult<MerchantAccountVO>>(ListResult.build(merchantAccounts.getResultList(), merchantAccounts.getRecordTotal())) ;
    }
}
