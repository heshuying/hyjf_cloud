/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.controller.admin;

import com.hyjf.am.response.AdminResponse;
import com.hyjf.cs.trade.service.BorrowRegistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangjun
 * @version BorrowRegistController, v0.1 2018/6/29 16:41
 */
@RestController
@RequestMapping("/cs-trade/borrow_regist")
public class BorrowRegistController {
    @Autowired
    BorrowRegistService borrowRegistService;

    /**
     * 标的备案
     * @return
     */
    @RequestMapping("/update_borrow_regist/{borrowNid}")
    public AdminResponse updateBorrowRegist(@PathVariable String borrowNid) {
        return borrowRegistService.updateBorrowRegist(borrowNid);
    }

}
