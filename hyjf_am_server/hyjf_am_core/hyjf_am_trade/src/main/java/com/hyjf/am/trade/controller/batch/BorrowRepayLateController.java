/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.batch;

import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.customize.BorrowRepayLateCustomize;
import com.hyjf.am.trade.service.front.batch.BorrowRepayLateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author wangjun
 * @version BorrowRepayLateController, v0.1 2019/3/20 14:37
 */
@RestController
@RequestMapping("/am-trade/batch")
public class BorrowRepayLateController extends BaseController {
    @Autowired
    BorrowRepayLateService borrowRepayLateService;

    @GetMapping("/repaylate")
    public void borrowRepayLate(){
        // 查询不分期逾期数据
        List<BorrowRepayLateCustomize> list = borrowRepayLateService.selectBorrowRepayLate();
        // 查询分期逾期数据
        List<BorrowRepayLateCustomize> listByStages = borrowRepayLateService.selectBorrowRepayLateByStages();
        // 更新还款逾期信息(不开事务，方法里每一笔为单位开启事务)
        borrowRepayLateService.updBorrowRepayLate(list, listByStages);
    }
}
