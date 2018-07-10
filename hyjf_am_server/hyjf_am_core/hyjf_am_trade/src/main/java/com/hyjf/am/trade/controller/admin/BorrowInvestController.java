/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.admin;

import com.hyjf.am.response.admin.BorrowInvestCustomizeResponse;
import com.hyjf.am.resquest.admin.BorrowInvestRequest;
import com.hyjf.am.trade.dao.model.customize.admin.BorrowInvestCustomize;
import com.hyjf.am.trade.service.admin.BorrowInvestService;
import com.hyjf.am.vo.admin.BorrowInvestCustomizeVO;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @author wangjun
 * @version BorrowInvestController, v0.1 2018/7/10 9:33
 */
@RestController
@RequestMapping("/am-trade/borrow_invest")
public class BorrowInvestController {
    @Autowired
    BorrowInvestService borrowInvestService;

    /**
     * 投资明细记录 总数COUNT
     *
     * @return
     */
    @RequestMapping("/count_borrow_invest")
    public BorrowInvestCustomizeResponse countBorrowInvest(@RequestBody @Valid BorrowInvestRequest borrowInvestRequest) {
        BorrowInvestCustomizeResponse response = new BorrowInvestCustomizeResponse();
        int count = borrowInvestService.countBorrowFirst(borrowInvestRequest);
        response.setTotal(count);
        return response;
    }

    /**
     * 投资明细列表
     *
     * @return
     */
    @RequestMapping("/select_borrow_invest_list")
    public BorrowInvestCustomizeResponse selectBorrowInvestList(@RequestBody @Valid BorrowInvestRequest borrowInvestRequest) {
        BorrowInvestCustomizeResponse response = new BorrowInvestCustomizeResponse();
        List<BorrowInvestCustomize> list = borrowInvestService.selectBorrowInvestList(borrowInvestRequest);
        if (!CollectionUtils.isEmpty(list)) {
            List<BorrowInvestCustomizeVO> voList = CommonUtils.convertBeanList(list, BorrowInvestCustomizeVO.class);
            response.setResultList(voList);
        }
        return response;
    }

    /**
     * 投资明细列表合计
     *
     * @return
     */
    @RequestMapping("/select_borrow_invest_account")
    public BorrowInvestCustomizeResponse selectBorrowInvestAccount(@RequestBody @Valid BorrowInvestRequest borrowInvestRequest) {
        BorrowInvestCustomizeResponse response = new BorrowInvestCustomizeResponse();
        String sumAccount = borrowInvestService.selectBorrowInvestAccount(borrowInvestRequest);
        response.setSumAccount(sumAccount);
        return response;
    }
}
