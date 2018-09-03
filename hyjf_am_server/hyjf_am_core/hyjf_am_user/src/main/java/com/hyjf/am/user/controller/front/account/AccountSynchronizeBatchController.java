/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.controller.front.account;

import com.hyjf.am.response.user.AccountMobileSynchResponse;
import com.hyjf.am.resquest.user.AccountMobileSynchRequest;
import com.hyjf.am.user.controller.BaseController;
import com.hyjf.am.user.dao.model.auto.AccountMobileSynch;
import com.hyjf.am.user.service.front.account.MobileSynchronizeService;
import com.hyjf.am.vo.user.AccountMobileSynchVO;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @author wangjun
 * @version AccountSynchronizeBatchController, v0.1 2018/6/22 10:11
 */
@RestController
@RequestMapping("/am-user/batch")
public class AccountSynchronizeBatchController extends BaseController {

    @Autowired
    MobileSynchronizeService mobileSynchronizeService;

    @RequestMapping("/searchAccountMobileSynch/{flag}")
    public AccountMobileSynchResponse updateActivityEndStatus(@PathVariable String flag) {
        AccountMobileSynchResponse response = new AccountMobileSynchResponse();
        List<AccountMobileSynch> accountMobileSynches = mobileSynchronizeService.searchAccountMobileSynch(flag);
        if (!CollectionUtils.isEmpty(accountMobileSynches)) {
            List<AccountMobileSynchVO> list = CommonUtils.convertBeanList(accountMobileSynches, AccountMobileSynchVO.class);
            response.setResultList(list);
        }
        return response;
    }

    @RequestMapping("/updateAccountMobileSynch")
    public AccountMobileSynchResponse updateAccountMobileSynch(@RequestBody @Valid AccountMobileSynchRequest accountMobileSynchRequest) {
        AccountMobileSynchResponse response = new AccountMobileSynchResponse();
        boolean updateFlag = mobileSynchronizeService.updateAccountMobileSynch(accountMobileSynchRequest);
        response.setUpdateFlag(updateFlag);
        return response;
    }

    @RequestMapping("/updateMobileSynch")
    public AccountMobileSynchResponse accountMobileAynch(@RequestBody  AccountMobileSynchRequest accountMobileSynchRequest) {
        AccountMobileSynchResponse response = new AccountMobileSynchResponse();
        boolean updateFlag = mobileSynchronizeService.updateMobileSynch(accountMobileSynchRequest);
        response.setUpdateFlag(updateFlag);
        return response;
    }

}
