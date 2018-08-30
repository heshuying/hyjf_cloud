/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.front.trade;

import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.trade.dao.model.auto.PushMoney;
import com.hyjf.am.trade.service.CommisionComputeService;
import com.hyjf.am.trade.service.front.borrow.PlanLockQuitService;
import com.hyjf.am.vo.trade.HjhLockVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author wangjun
 * @version PlanLockQuitController, v0.1 2018/7/17 17:58
 */
@RestController
@RequestMapping("/am-trade/planLockQuit")
public class PlanLockQuitController {
    @Autowired
    PlanLockQuitService planLockQuitService;

    @Autowired
    CommisionComputeService commisionComputeService;

    /**
     * 计划锁定更新操作
     * @param hjhLockVo
     */
    @PostMapping("/updateLockRepayInfo")
    public void updateLockRepayInfo(@RequestBody HjhLockVo hjhLockVo){
        planLockQuitService.updateLockRepayInfo(hjhLockVo);
    }

    /**
     * 获取配置信息
     * @param map
     */
    @PostMapping("/getCommisionConfig")
    public IntegerResponse getCommisionConfig(@RequestBody Map map){
        Integer projectType  = (Integer) map.get("projectType");
        String userType  = (String) map.get("userType");
        PushMoney commisionConfig = commisionComputeService.getCommisionConfig(projectType, userType);
        Integer rewardSend = commisionConfig.getRewardSend();
        return new IntegerResponse(rewardSend);
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
