/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.am.trade.service.task.BorrowLoanRepayToMQService;

/**
 * 
 * 批处理原子层入口
 * @author dxj
 *
 */
@RestController
@RequestMapping("/batch")
public class BatchController {

    @Autowired
    private BorrowLoanRepayToMQService borrowLoanRepayToMQService;

    @GetMapping("/taskAssign")
    public String taskAssign() {

    	
       borrowLoanRepayToMQService.taskAssign();
        
       return "ok";
    }
    
    
}
