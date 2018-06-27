package com.hyjf.am.trade.controller;

import com.hyjf.am.resquest.trade.BorrowTenderRequest;
import com.hyjf.am.trade.dao.model.auto.BorrowTender;
import com.hyjf.am.vo.trade.borrow.BorrowTenderVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hyjf.am.response.trade.BorrowTenderResponse;
import com.hyjf.am.trade.service.BorrowTenderService;

@RestController
@RequestMapping("/am-trade/borrowTender")
public class BorrowTenderController {

    @Autowired
    private BorrowTenderService borrowTenderService;


    /**
     * 获取投资笔数
     * @author zhangyk
     * @date 2018/6/26 9:31
     */
    @GetMapping("/countUserInvest/{borrowNid}/{userId}")
    public BorrowTenderResponse countUserInvest(String borrowNid, Integer userId) {
        BorrowTenderResponse response = new BorrowTenderResponse();
        Integer count  = borrowTenderService.getCountBorrowTenderService(userId, borrowNid);
        response.setTenderCount(count);
        return response;
    }


    @PostMapping("/selectBorrowTender")
    public BorrowTenderResponse selectBorrowTender(@RequestBody BorrowTenderRequest request){
        BorrowTenderResponse response = new BorrowTenderResponse();
        BorrowTender borrowTender =borrowTenderService.selectBorrowTender(request);
        if (Validator.isNotNull(borrowTender)){
            response.setResult(CommonUtils.convertBean(borrowTender,BorrowTenderVO.class));
        }
        return response;
    }

}
