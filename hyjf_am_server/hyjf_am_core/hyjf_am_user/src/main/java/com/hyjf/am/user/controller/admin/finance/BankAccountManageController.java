/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.controller.admin.finance;

import com.hyjf.am.response.admin.BankAccountManageCustomizeResponse;
import com.hyjf.am.resquest.admin.BankAccountManageRequest;
import com.hyjf.am.user.controller.BaseController;
import com.hyjf.am.user.dao.model.customize.admin.finance.BankAccountManageCustomize;
import com.hyjf.am.user.service.admin.finance.BankAccountManageService;
import com.hyjf.am.vo.admin.BankAccountManageCustomizeVO;
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
@RequestMapping("/am-user/bankAccountManage")
public class BankAccountManageController extends BaseController {

    @Autowired
    BankAccountManageService bankAccountManageService;

    /**
     * @Author: liushouyi
     * @Desc :查询总件数
     */
    @ApiOperation(value = "银行账户管理查询总件数")
    @PostMapping("/queryAccountCount")
    public Integer queryAccountCount(BankAccountManageRequest bankAccountManageRequest) {
        Integer count = bankAccountManageService.queryAccountCount(bankAccountManageRequest);
        return count;
    }

    /**
     * @Author: liushouyi
     * @Desc :查询列表数据
     */
    @ApiOperation(value = "银行账户管理查询列表")
    @PostMapping("/queryAccountInfos")
    public BankAccountManageCustomizeResponse queryAccountInfos(BankAccountManageRequest bankAccountManageRequest) {
        BankAccountManageCustomizeResponse response = new BankAccountManageCustomizeResponse();
        List<BankAccountManageCustomize> bankAccountManageCustomizes = bankAccountManageService.queryAccountInfos(bankAccountManageRequest);
        if (null!=bankAccountManageCustomizes&&bankAccountManageCustomizes.size()>0) {
            List<BankAccountManageCustomizeVO> bankAccountManageCustomizeVOS = CommonUtils.convertBeanList(bankAccountManageCustomizes,BankAccountManageCustomizeVO.class);
            response.setResultList(bankAccountManageCustomizeVOS);
        }
        return response;
    }
}
