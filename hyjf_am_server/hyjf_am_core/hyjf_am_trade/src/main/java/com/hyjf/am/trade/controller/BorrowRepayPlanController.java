/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller;

import com.hyjf.am.response.trade.BorrowRepayPlanResponse;
import com.hyjf.am.trade.dao.model.auto.BorrowRepayPlan;
import com.hyjf.am.trade.service.BorrowRepayPlanService;
import com.hyjf.am.vo.trade.borrow.BorrowRepayPlanVO;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version BorrowRepayPlanController, v0.1 2018/6/23 11:42
 */
@Api(value = "还款记录分期")
@RestController
@RequestMapping("/am-trade/borrowRepayPlan")
public class BorrowRepayPlanController {

    @Autowired
    BorrowRepayPlanService borrowRepayPlanService;

    /**
     * 检索正在还款中的标的
     *
     * @Author liushouyi
     * @return
     */
    @RequestMapping("/selectBorrowPlanRepayList")
    public BorrowRepayPlanResponse selectBorrowPlanRepayList(@PathVariable String borrowNid, @PathVariable Integer repaySmsReminder) {
        BorrowRepayPlanResponse response = new BorrowRepayPlanResponse();
        List<BorrowRepayPlan> borrowRepayPlans = borrowRepayPlanService.selectBorrowRepayPlanList(borrowNid ,repaySmsReminder);
        if(borrowRepayPlans != null){
            List<BorrowRepayPlanVO> borrowRepayPlanVO = CommonUtils.convertBeanList(borrowRepayPlans,BorrowRepayPlanVO.class);
            response.setResultList(borrowRepayPlanVO);
        }
        return response;
    }

    @RequestMapping("/updateBorrowRepay")
    public Integer updateBorrowPlanRepay(@RequestBody @Valid BorrowRepayPlanVO borrowRepayPlanVO) {
        return this.borrowRepayPlanService.updateBorrowRepayPlan(borrowRepayPlanVO);
    }
}
