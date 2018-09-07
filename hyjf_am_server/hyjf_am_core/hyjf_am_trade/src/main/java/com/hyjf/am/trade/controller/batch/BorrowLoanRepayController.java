/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.batch;

import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.service.task.BorrowLoanRepayToMQService;
import com.hyjf.common.cache.CacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author dxj
 * @version BorrowLoadRepayController, v0.1 2018/7/5 10:00
 */
@RestController
@RequestMapping("/am-trade/batch")
public class BorrowLoanRepayController extends BaseController {

    @Autowired
    private BorrowLoanRepayToMQService borrowLoanRepayToMQService;

    @GetMapping("/taskAssign")
    public String taskAssign() {
        
       borrowLoanRepayToMQService.taskAssign();
        
       return "ok";
    }

    @GetMapping("/testRedis")
    public Map testRedis() {
        
    	return CacheUtil.getParamNameMap("ACCOUNT_STATUS");
    }
}
