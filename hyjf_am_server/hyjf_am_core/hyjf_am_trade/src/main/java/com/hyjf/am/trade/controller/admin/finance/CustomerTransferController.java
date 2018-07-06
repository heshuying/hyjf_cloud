/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.admin.finance;

import com.hyjf.am.response.trade.AccountResponse;
import com.hyjf.am.resquest.admin.CustomerTransferRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.Account;
import com.hyjf.am.trade.service.admin.finance.CustomerTransferService;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: CustomerTransferController, v0.1 2018/7/6 10:12
 */
@Api(value = "资金中心-转账管理-用户转账")
@RestController
@RequestMapping("/am-trade/customertransfer")
public class CustomerTransferController extends BaseController {

    @Autowired
    private CustomerTransferService customerTransferService;

    /**
     * 根据userId查询Account列表，按理说只能取出来一个Account，但是service需要做个数判断，填写不同的msg，所以返回List
     * @auth sunpeikai
     * @param userId 用户id
     * @return
     */
    @ApiOperation(value = "根据userId查询Account列表",notes = "根据userId查询Account列表")
    @PostMapping(value = "/searchaccountbyuserid")
    public AccountResponse searchAccountByUserId(@RequestBody Integer userId){
        AccountResponse response = new AccountResponse();
        List<Account> accountList = customerTransferService.searchAccountByUserId(userId);
        if(!CollectionUtils.isEmpty(accountList)){
            List<AccountVO> accountVOList = CommonUtils.convertBeanList(accountList,AccountVO.class);
            response.setResultList(accountVOList);
            response.setRtn("00");
        }
        return response;
    }
    /**
     * 向ht_user_transfer表中插入数据
     * @auth sunpeikai
     * @param request 发起转账的参数
     * @return
     */
    @ApiOperation(value = "向ht_user_transfer表中插入数据",notes = "向ht_user_transfer表中插入数据")
    @PostMapping(value = "/insertusertransfer")
    public Boolean insertUserTransfer(@RequestBody CustomerTransferRequest request){
        Boolean success = customerTransferService.insertUserTransfer(request);
        return success;
    }

}
