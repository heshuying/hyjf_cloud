/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller;

import com.hyjf.am.trade.service.PlanLockQuitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangjun
 * @version PlanLockQuitController, v0.1 2018/7/17 17:58
 */
@RestController
@RequestMapping("/am-trade/planLockQuit")
public class PlanLockQuitController {
    @Autowired
    PlanLockQuitService planLockQuitService;
    /**
     * 计划锁定更新操作
     * @param accedeOrderId
     */
    @GetMapping("/updateLockRepayInfo/{accedeOrderId}")
    public void updateLockRepayInfo(@PathVariable String accedeOrderId){
        planLockQuitService.updateLockRepayInfo(accedeOrderId);
    }

    /**
     * 计划退出更新操作
     * @param accedeOrderId
     */
    @GetMapping("/updateQuitRepayInfo/{accedeOrderId}")
    public void updateQuitRepayInfo(@PathVariable String accedeOrderId){
        planLockQuitService.updateQuitRepayInfo(accedeOrderId);
    }
}
