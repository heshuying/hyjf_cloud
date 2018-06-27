package com.hyjf.am.trade.controller;

import com.hyjf.am.response.trade.BorrowTenderResponse;
import com.hyjf.am.response.trade.BorrowUserResponse;
import com.hyjf.am.trade.dao.model.auto.BorrowUsers;
import com.hyjf.am.trade.service.BorrowTenderService;
import com.hyjf.am.vo.trade.borrow.BorrowUserVO;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
