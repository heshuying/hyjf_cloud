package com.hyjf.am.trade.controller.api.aems.userrepay;

import com.hyjf.am.response.trade.BorrowInfoResponse;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.service.api.aems.repay.AemsUserRepayService;
import com.hyjf.am.vo.trade.borrow.BorrowInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * aems用户还款controller
 */
@RestController
@RequestMapping("/am-trade/aems/repay")
public class AemsUserRepayController extends BaseController {

    @Autowired
    AemsUserRepayService aemsUserRepayService;

    @GetMapping("/get_borrow/{userId}/{roleId}/{borrowNid}")
    public BorrowInfoResponse getBorrow(@PathVariable int userId,
                                        @PathVariable String roleId,
                                        @PathVariable String borrowNid) {
        BorrowInfoResponse response = new BorrowInfoResponse();
        BorrowInfoVO borrowInfoVO = aemsUserRepayService.getBorrow(userId, roleId, borrowNid);
        if (borrowInfoVO != null) {
            response.setResult(borrowInfoVO);
        }
        return response;
    }
}
