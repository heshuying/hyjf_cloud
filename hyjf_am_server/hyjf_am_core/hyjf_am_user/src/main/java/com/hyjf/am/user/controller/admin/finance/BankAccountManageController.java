/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.controller.admin.finance;

import com.hyjf.am.response.user.BankOpenAccountResponse;
import com.hyjf.am.user.controller.BaseController;
import com.hyjf.am.user.dao.model.auto.BankOpenAccount;
import com.hyjf.am.user.service.admin.finance.BankAccountManageService;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.common.validator.Validator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author PC-LIUSHOUYI
 * @version BankAccountManageController, v0.1 2018/6/29 13:55
 */

@Api(value = "银行账户管理")
@RestController
@RequestMapping("/am-user/bankAccountManage")
public class BankAccountManageController extends BaseController {

    @Autowired
    BankAccountManageService bankAccountManageService;

    /**
     * @Author: liushouyi
     * @Desc :查询列表数据
     */
    @ApiOperation(value = "银行账户管理查询列表")
    @PostMapping("/getbankopenaccount")
    public BankOpenAccountResponse getBankOpenAccount(@PathVariable Integer userId) {
        BankOpenAccountResponse response = new BankOpenAccountResponse();
        BankOpenAccount bankOpenAccount = bankAccountManageService.getBankOpenAccount(userId);
        if (null != bankOpenAccount) {
            BankOpenAccountVO bankOpenAccountVO = new BankOpenAccountVO();
            BeanUtils.copyProperties(bankOpenAccountVO, bankOpenAccount);
            response.setResult(bankOpenAccountVO);
        }
        return response;
    }
}
