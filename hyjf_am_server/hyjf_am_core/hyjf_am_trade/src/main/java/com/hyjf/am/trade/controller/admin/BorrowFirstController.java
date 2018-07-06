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
    public Integer countBorrowFirst(@RequestBody @Valid BorrowFirstRequest borrowFirstRequest) {
        return borrowFirstService.countBorrowFirst(borrowFirstRequest);
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
     * 获取借款初审列表count
     *
     * @return
     */
    @RequestMapping("/sum_borrow_first_account")
    public String sumBorrowFirstAccount(@RequestBody @Valid BorrowFirstRequest borrowFirstRequest) {
        return borrowFirstService.getSumBorrowFirstAccount(borrowFirstRequest);
    }

    /**
     * 交保证金
     *
     * @return
     */
    @RequestMapping("/insert_borrow_bail/{borrowNid}/{currUserId}")
    public boolean insertBorrowBail(@PathVariable String borrowNid, @PathVariable String currUserId) {
        return borrowFirstService.insertBorrowBail(borrowNid, currUserId);
    }

    /**
     * 更新-发标
     *
     * @param borrowFireRequest
     */
    @RequestMapping("/update_ontime_record")
    public void updateOntimeRecord(@RequestBody @Valid BorrowFireRequest borrowFireRequest) {
        borrowFirstService.updateOntimeRecord(borrowFireRequest);
    }

    /**
     * 加入计划
     *
     * @param borrowFireRequest
     */
    @RequestMapping("/send_to_mq")
    public void sendToMQ(@RequestBody @Valid BorrowFireRequest borrowFireRequest) {
        borrowFirstService.sendToMQ(borrowFireRequest);
    }
}
