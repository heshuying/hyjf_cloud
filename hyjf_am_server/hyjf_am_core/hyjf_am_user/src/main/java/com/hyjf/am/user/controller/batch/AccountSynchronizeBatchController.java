/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.controller.batch;

import com.hyjf.am.response.user.AccountMobileSynchResponse;
import com.hyjf.am.resquest.user.AccountMobileSynchRequest;
import com.hyjf.am.user.dao.model.auto.AccountMobileSynch;
import com.hyjf.am.user.service.batch.MobileSynchronizeService;
import com.hyjf.am.vo.user.AccountMobileSynchVO;
import com.hyjf.common.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class AccountSynchronizeBatchController {
    private static final Logger logger = LoggerFactory.getLogger(AccountSynchronizeBatchController.class);

    @Autowired
    MobileSynchronizeService mobileSynchronizeService;

    @RequestMapping("/searchAccountMobileSynch/{flag}")
    public AccountMobileSynchResponse updateActivityEndStatus(@PathVariable String flag){
        AccountMobileSynchResponse response = new AccountMobileSynchResponse();
        List<AccountMobileSynch> accountMobileSynches = mobileSynchronizeService.searchAccountMobileSynch(flag);
        if(!CollectionUtils.isEmpty(accountMobileSynches)){
            List<AccountMobileSynchVO> list = CommonUtils.convertBeanList(accountMobileSynches,AccountMobileSynchVO.class);
            response.setResultList(list);
        }
        return response;
    }

    @RequestMapping("/updateAccountMobileSynch")
    public boolean updateAccountMobileSynch(@RequestBody @Valid AccountMobileSynchRequest accountMobileSynchRequest){
        return mobileSynchronizeService.updateAccountMobileSynch(accountMobileSynchRequest);
    }
}
