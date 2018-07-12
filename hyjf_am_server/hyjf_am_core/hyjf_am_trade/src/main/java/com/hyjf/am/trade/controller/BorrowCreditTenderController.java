/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller;

import com.hyjf.am.response.admin.AdminBorrowCreditInfoResponse;
import com.hyjf.am.response.admin.AdminBorrowCreditResponse;
import com.hyjf.am.response.trade.BorrowCreditDetailResponse;
import com.hyjf.am.response.trade.BorrowCreditResponse;
import com.hyjf.am.response.trade.BorrowCreditTenderResponse;
import com.hyjf.am.resquest.admin.BorrowCreditAmRequest;
import com.hyjf.am.resquest.admin.BorrowCreditRepayAmRequest;
import com.hyjf.am.resquest.trade.BorrowCreditRequest;
import com.hyjf.am.trade.dao.model.auto.BorrowCredit;
import com.hyjf.am.trade.dao.model.customize.admin.AdminBorrowCreditCustomize;
import com.hyjf.am.trade.dao.model.customize.admin.AdminBorrowCreditTenderCustomize;
import com.hyjf.am.trade.service.BorrowCreditService;
import com.hyjf.am.trade.service.BorrowCreditTenderService;
import com.hyjf.am.vo.admin.BorrowCreditInfoSumVO;
import com.hyjf.am.vo.admin.BorrowCreditInfoVO;
import com.hyjf.am.vo.admin.BorrowCreditSumVO;
import com.hyjf.am.vo.admin.BorrowCreditTenderSumVO;
import com.hyjf.am.vo.trade.BorrowCreditVO;
import com.hyjf.am.vo.trade.borrow.BorrowCreditDetailVO;
import com.hyjf.am.vo.trade.borrow.BorrowCreditRepayVO;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.Api;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 *
 * @author zhangyk
 * @date 2018/7/11 15:28
 */
@RestController
@RequestMapping("/am-trade/creditTender")
public class BorrowCreditTenderController extends BaseController{

    @Autowired
    private BorrowCreditTenderService borrowCreditTenderService;

    @PostMapping("/getList")
    public BorrowCreditTenderResponse getBorrowCreditTenderList(@RequestBody BorrowCreditRepayAmRequest request){
        BorrowCreditTenderResponse response = new BorrowCreditTenderResponse();
        List<AdminBorrowCreditTenderCustomize> list = borrowCreditTenderService.searchBorrowCreditTenderList(request);
        if (CollectionUtils.isNotEmpty(list)){
            response.setResultList(CommonUtils.convertBeanList(list,BorrowCreditRepayVO.class));
        }
        return response;
    }


    @PostMapping("/getCount")
    public BorrowCreditTenderResponse countBorrowCreditTender(@RequestBody BorrowCreditRepayAmRequest request){
        BorrowCreditTenderResponse response = new BorrowCreditTenderResponse();
        Integer count = borrowCreditTenderService.countBorrowCreditTender(request);
        response.setCount(count);
        return response;
    }

    @PostMapping("/getSum")
    public BorrowCreditTenderResponse sumBorrowCreditTender(@RequestBody BorrowCreditRepayAmRequest request){
        BorrowCreditTenderResponse response = new BorrowCreditTenderResponse();
        BorrowCreditTenderSumVO sumVO = borrowCreditTenderService.sumBorrowCreditTender(request);
        response.setSumData(sumVO);
        return response;
    }









}
