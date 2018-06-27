package com.hyjf.am.trade.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.am.response.trade.BorrowTenderResponse;
import com.hyjf.am.trade.service.BorrowTenderService;

@RestController
@RequestMapping("/am-trade/borrow")
public class BorrowTenderController {

    @Autowired
    private BorrowTenderService borrowTenderService;


    /**
     * 获取投资笔数
     * @author zhangyk
     * @date 2018/6/26 9:31
     */
    @GetMapping("/borrowtender/{borrowNid}/{userId}")
    public BorrowTenderResponse countUserInvest(String borrowNid, Integer userId) {
        BorrowTenderResponse response = new BorrowTenderResponse();
        Integer count  = borrowTenderService.getCountBorrowTenderService(userId, borrowNid);
        response.setTenderCount(count);
        return response;
    }
}
