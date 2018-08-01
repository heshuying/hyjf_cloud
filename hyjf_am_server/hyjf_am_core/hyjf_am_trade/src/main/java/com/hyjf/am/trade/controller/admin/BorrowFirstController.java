/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.admin;

import com.hyjf.am.response.admin.BorrowFirstCustomizeResponse;
import com.hyjf.am.resquest.admin.BorrowFireRequest;
import com.hyjf.am.resquest.admin.BorrowFirstRequest;
import com.hyjf.am.trade.dao.model.customize.admin.BorrowFirstCustomize;
import com.hyjf.am.trade.service.admin.BorrowFirstService;
import com.hyjf.am.vo.admin.BorrowFirstCustomizeVO;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @author wangjun
 * @version BorrowFirstController, v0.1 2018/7/3 15:56
 */
@RestController
@RequestMapping("/am-trade/borrow_first")
public class BorrowFirstController {
    @Autowired
    BorrowFirstService borrowFirstService;

    /**
     * 获取借款初审列表count
     *
     * @return
     */
    @RequestMapping("/count_borrow_first")
    public BorrowFirstCustomizeResponse countBorrowFirst(@RequestBody @Valid BorrowFirstRequest borrowFirstRequest) {
        BorrowFirstCustomizeResponse response = new BorrowFirstCustomizeResponse();
        int count = borrowFirstService.countBorrowFirst(borrowFirstRequest);
        response.setTotal(count);
        return response;
    }

    /**
     * 获取借款初审列表
     *
     * @return
     */
    @RequestMapping("/select_borrow_first_list")
    public BorrowFirstCustomizeResponse selectBorrowFirstList(@RequestBody @Valid BorrowFirstRequest borrowFirstRequest) {
        BorrowFirstCustomizeResponse response = new BorrowFirstCustomizeResponse();
        List<BorrowFirstCustomize> borrowRegistCustomizeList = borrowFirstService.selectBorrowFirstList(borrowFirstRequest);
        if (!CollectionUtils.isEmpty(borrowRegistCustomizeList)) {
            List<BorrowFirstCustomizeVO> voList = CommonUtils.convertBeanList(borrowRegistCustomizeList, BorrowFirstCustomizeVO.class);
            response.setResultList(voList);
        }
        return response;
    }

    /**
     * 获取借款初审列表合计
     *
     * @return
     */
    @RequestMapping("/sum_borrow_first_account")
    public BorrowFirstCustomizeResponse sumBorrowFirstAccount(@RequestBody @Valid BorrowFirstRequest borrowFirstRequest) {
        BorrowFirstCustomizeResponse response = new BorrowFirstCustomizeResponse();
        String sumAccount = borrowFirstService.getSumBorrowFirstAccount(borrowFirstRequest);
        response.setSumAccount(sumAccount);
        return response;
    }

    /**
     * 交保证金
     *
     * @return
     */
    @RequestMapping("/insert_borrow_bail/{borrowNid}/{currUserId}")
    public BorrowFirstCustomizeResponse insertBorrowBail(@PathVariable String borrowNid, @PathVariable String currUserId) {
        BorrowFirstCustomizeResponse response = new BorrowFirstCustomizeResponse();
        boolean flag = borrowFirstService.insertBorrowBail(borrowNid, currUserId);
        response.setFlag(flag);
        return response;
    }

    /**
     * 更新-发标
     *
     * @param borrowFireRequest
     */
    @RequestMapping("/update_ontime_record")
    public BorrowFirstCustomizeResponse updateOntimeRecord(@RequestBody @Valid BorrowFireRequest borrowFireRequest) {
        BorrowFirstCustomizeResponse response = new BorrowFirstCustomizeResponse();
        boolean flag = borrowFirstService.updateOntimeRecord(borrowFireRequest);
        response.setFlag(flag);
        return response;
    }
}
