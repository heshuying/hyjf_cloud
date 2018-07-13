/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.admin;

import com.hyjf.am.response.AdminResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.BorrowFullCustomizeResponse;
import com.hyjf.am.resquest.admin.BorrowFullRequest;
import com.hyjf.am.trade.dao.model.customize.admin.BorrowFullCustomize;
import com.hyjf.am.trade.service.admin.BorrowFullService;
import com.hyjf.am.vo.admin.BorrowFullCustomizeVO;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.BeanUtils;
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
 * @version BorrowFullController, v0.1 2018/7/6 10:42
 */
@RestController
@RequestMapping("/am-trade/borrow_full")
public class BorrowFullController {
    @Autowired
    BorrowFullService borrowFullService;

    /**
     * 获取借款复审列表count
     *
     * @param borrowFullRequest
     * @return
     */
    @RequestMapping("/count_borrow_full")
    public BorrowFullCustomizeResponse countBorrowFull(@RequestBody @Valid BorrowFullRequest borrowFullRequest) {
        BorrowFullCustomizeResponse response = new BorrowFullCustomizeResponse();
        int count = borrowFullService.countBorrowFull(borrowFullRequest);
        response.setTotal(count);
        return response;
    }

    /**
     * 查询借款复审列表
     *
     * @param borrowFullRequest
     * @return
     */
    @RequestMapping("/select_borrow_full_list")
    public BorrowFullCustomizeResponse selectBorrowFullList(@RequestBody @Valid BorrowFullRequest borrowFullRequest) {
        BorrowFullCustomizeResponse response = new BorrowFullCustomizeResponse();
        List<BorrowFullCustomize> borrowRegistCustomizeList = borrowFullService.selectBorrowFullList(borrowFullRequest);
        if (!CollectionUtils.isEmpty(borrowRegistCustomizeList)) {
            List<BorrowFullCustomizeVO> voList = CommonUtils.convertBeanList(borrowRegistCustomizeList, BorrowFullCustomizeVO.class);
            response.setResultList(voList);
        }
        return response;
    }

    /**
     * 复审列表合计
     *
     * @param borrowFullRequest
     * @return
     */
    @RequestMapping("/sum_account")
    public BorrowFullCustomizeResponse sumAccount(@RequestBody @Valid BorrowFullRequest borrowFullRequest) {
        BorrowFullCustomizeResponse response = new BorrowFullCustomizeResponse();
        BorrowFullCustomize borrowFullCustomize = borrowFullService.sumAccount(borrowFullRequest);
        if (borrowFullCustomize != null) {
            BorrowFullCustomizeVO vo = new BorrowFullCustomizeVO();
            BeanUtils.copyProperties(borrowFullCustomize, vo);
            response.setResult(vo);
        }
        return response;
    }

    /**
     * 复审详细信息
     *
     * @param borrowNid
     * @return
     */
    @RequestMapping("/get_full_info/{borrowNid}")
    public BorrowFullCustomizeResponse getFullInfo(@PathVariable String borrowNid) {
        BorrowFullCustomizeResponse response = new BorrowFullCustomizeResponse();
        BorrowFullCustomize borrowFullCustomize = borrowFullService.getFullInfo(borrowNid);
        if (borrowFullCustomize != null) {
            BorrowFullCustomizeVO vo = new BorrowFullCustomizeVO();
            BeanUtils.copyProperties(borrowFullCustomize, vo);
            response.setResult(vo);
        }
        return response;
    }

    /**
     * 借款复审详细列表count
     *
     * @param borrowNid
     * @return
     */
    @RequestMapping("/count_full_list/{borrowNid}")
    public BorrowFullCustomizeResponse countFullList(@PathVariable String borrowNid) {
        BorrowFullCustomizeResponse response = new BorrowFullCustomizeResponse();
        int count = borrowFullService.countFullList(borrowNid);
        response.setTotal(count);
        return response;
    }

    /**
     * 复审详细信息列表
     *
     * @param borrowFullRequest
     * @return
     */
    @RequestMapping("/get_full_list")
    public BorrowFullCustomizeResponse getFullList(@RequestBody @Valid BorrowFullRequest borrowFullRequest) {
        BorrowFullCustomizeResponse response = new BorrowFullCustomizeResponse();
        List<BorrowFullCustomize> list = borrowFullService.getFullList(borrowFullRequest);
        if (!CollectionUtils.isEmpty(list)) {
            List<BorrowFullCustomizeVO> voList = CommonUtils.convertBeanList(list, BorrowFullCustomizeVO.class);
            response.setResultList(voList);
        }
        return response;
    }

    /**
     * 复审详细列表合计
     *
     * @param borrowNid
     * @return
     */
    @RequestMapping("/sum_amount/{borrowNid}")
    public BorrowFullCustomizeResponse sumAmount(@PathVariable String borrowNid) {
        BorrowFullCustomizeResponse response = new BorrowFullCustomizeResponse();
        BorrowFullCustomize borrowFullCustomize = borrowFullService.sumAmount(borrowNid);
        if (borrowFullCustomize != null) {
            BorrowFullCustomizeVO vo = new BorrowFullCustomizeVO();
            BeanUtils.copyProperties(borrowFullCustomize, vo);
            response.setResult(vo);
        }
        return response;
    }

    /**
     * 复审提交
     *
     * @param borrowFullRequest
     * @return
     */
    @RequestMapping("/update_borrow_full")
    public Response updateBorrowFull(@RequestBody @Valid BorrowFullRequest borrowFullRequest) {
        return borrowFullService.updateBorrowFull(borrowFullRequest);
    }

    /**
     * 流标
     *
     * @param borrowFullRequest
     * @return
     */
    @RequestMapping("/update_borrow_over")
    public Response updateBorrowOver(@RequestBody @Valid BorrowFullRequest borrowFullRequest) {
        return borrowFullService.updateBorrowOver(borrowFullRequest);
    }
}
