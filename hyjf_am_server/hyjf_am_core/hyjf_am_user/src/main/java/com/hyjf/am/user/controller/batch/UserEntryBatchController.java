package com.hyjf.am.user.controller.batch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.am.user.controller.BaseController;
import com.hyjf.am.user.service.batch.UserEntryBatchService;

/**
 * @author wangjun
 * @version UserEntryBatchController, v0.1 2018/6/12 14:58
 */

@RestController
@RequestMapping("/am-user/batch")
public class UserEntryBatchController extends BaseController {

    @Autowired
    private UserEntryBatchService userEntryBatchService;

    @RequestMapping("/entryupdate")
    public void entryUpdate(){
        logger.info("员工入职修改客户属性...");
        userEntryBatchService.updateUserEntryInfo();
    }
}
