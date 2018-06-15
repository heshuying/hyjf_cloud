/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller;

import com.hyjf.am.response.trade.BorrowWithBLOBSResponse;
import com.hyjf.am.response.trade.IncreaseInterestInvestResponse;
import com.hyjf.am.response.trade.IncreaseInterestRepayDetailResponse;
import com.hyjf.am.response.trade.IncreaseInterestRepayResponse;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.service.RtbLoansService;
import com.hyjf.am.vo.rtbbatch.*;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ${yaoy}
 * @version RtbLoansBatchController, v0.1 2018/6/14 10:30
 */
@RestController
@RequestMapping("/am-trade/rtbLoanBatch")
public class RtbLoansBatchController {

    @Autowired
    private RtbLoansService rtbLoansService;

    @GetMapping("/getBorrowTenderList/{borrowNid}")
    public IncreaseInterestInvestResponse getBorrowTenderList(String borrowNid) {
        IncreaseInterestInvestResponse response = new IncreaseInterestInvestResponse();
        List<IncreaseInterestInvest> interestInvestList = rtbLoansService.getBorrowTenderList(borrowNid);
        if (!CollectionUtils.isEmpty(interestInvestList)) {
            List<IncreaseInterestInvestVo> interestInvestVoList = CommonUtils.convertBeanList(interestInvestList,IncreaseInterestInvestVo.class);
            response.setResultList(interestInvestVoList);
        }
        return response;
     }

    @PostMapping("/updateBorrowTender")
    public int updateBorrowTender(@RequestBody IncreaseInterestInvestVo increaseInterestInvestVo) {
        int result = 0;
        if (increaseInterestInvestVo != null) {
            IncreaseInterestInvest increaseInterestInvest = new IncreaseInterestInvest();
            BeanUtils.copyProperties(increaseInterestInvestVo,increaseInterestInvest);
            result = rtbLoansService.updateBorrowTender(increaseInterestInvest);
        }
        return result;
    }

    @PostMapping("/insertBorrowRecover")
    public int insertBorrowRecover(@RequestBody IncreaseInterestLoanVo increaseInterestLoanVo) {
        int result = 0;
        if (increaseInterestLoanVo != null) {
            IncreaseInterestLoan increaseInterestLoan = new IncreaseInterestLoan();
            BeanUtils.copyProperties(increaseInterestLoanVo,increaseInterestLoan);
            result = rtbLoansService.insertBorrowRecover(increaseInterestLoan);
        }
        return result;
    }

    @PostMapping("/insertIncreaseInterestRepay")
    public int insertIncreaseInterestRepay(@RequestBody IncreaseInterestRepayVo increaseInterestRepayVo) {
        int result = 0;
        if (increaseInterestRepayVo != null) {
            IncreaseInterestRepay increaseInterestRepay = new IncreaseInterestRepay();
            BeanUtils.copyProperties(increaseInterestRepayVo,increaseInterestRepay);
            result = rtbLoansService.insertIncreaseInterestRepay(increaseInterestRepay);
        }
        return result;
    }

    @PostMapping("/updateIncreaseInterestRepay")
    public int updateIncreaseInterestRepay(@RequestBody IncreaseInterestRepayVo increaseInterestRepayVo) {
        int result = 0;
        if (increaseInterestRepayVo != null) {
            IncreaseInterestRepay increaseInterestRepay = new IncreaseInterestRepay();
            BeanUtils.copyProperties(increaseInterestRepayVo,increaseInterestRepay);
            result = rtbLoansService.updateIncreaseInterestRepay(increaseInterestRepay);
        }
        return result;
    }

    @GetMapping("/getBorrowRepay/borrowNid")
    public IncreaseInterestRepayResponse getBorrowRepay(String borrowNid) {
        IncreaseInterestRepayResponse response = new IncreaseInterestRepayResponse();
        IncreaseInterestRepay increaseInterestRepay = rtbLoansService.getBorrowRepay(borrowNid);
        if (increaseInterestRepay != null) {
            IncreaseInterestRepayVo increaseInterestRepayVo = new IncreaseInterestRepayVo();
            BeanUtils.copyProperties(increaseInterestRepay,increaseInterestRepayVo);
            response.setResult(increaseInterestRepayVo);
        }
        return response;
    }

    @PostMapping("/insertIncreaseInterestLoanDetail")
    public int increaseInterestLoanDetail(@RequestBody IncreaseInterestLoanDetailVo increaseInterestLoanDetailVo) {
        int result = 0;
        if (increaseInterestLoanDetailVo != null) {
            IncreaseInterestLoanDetail increaseInterestLoanDetail = new IncreaseInterestLoanDetail();
            BeanUtils.copyProperties(increaseInterestLoanDetailVo,increaseInterestLoanDetail);
            result = rtbLoansService.IncreaseInterestLoanDetail(increaseInterestLoanDetail);
        }
        return result;
    }

    @GetMapping("/getBorrowRepayPlan/{borrowNid}/{period}")
    public IncreaseInterestRepayDetailResponse getBorrowRepayPlan(@RequestBody String borrowNid, Integer period) {
        IncreaseInterestRepayDetailResponse response = new IncreaseInterestRepayDetailResponse();
        IncreaseInterestRepayDetail increaseInterestRepayDetail = rtbLoansService.getBorrowRepayPlan(borrowNid,period);
        if (increaseInterestRepayDetail != null) {
            IncreaseInterestRepayDetailVo increaseInterestRepayDetailVo = new IncreaseInterestRepayDetailVo();
            BeanUtils.copyProperties(increaseInterestRepayDetail,increaseInterestRepayDetailVo);
            response.setResult(increaseInterestRepayDetailVo);
        }
        return response;
    }

    @PostMapping("/insertIncreaseInterestRepayDetail")
    public int insertIncreaseInterestRepayDetail(@RequestBody IncreaseInterestRepayDetailVo increaseInterestRepayDetailVo) {
        int result = 0;
        if (increaseInterestRepayDetailVo != null) {
            IncreaseInterestRepayDetail increaseInterestRepayDetail = new IncreaseInterestRepayDetail();
            BeanUtils.copyProperties(increaseInterestRepayDetailVo,increaseInterestRepayDetail);
            result = rtbLoansService.insertIncreaseInterestRepayDetail(increaseInterestRepayDetail);
        }
        return result;
    }

    @PostMapping("/updateIncreaseInterestRepayDetail")
    public int updateIncreaseInterestRepayDetail(@RequestBody IncreaseInterestRepayDetailVo increaseInterestRepayDetailVo) {
        int result = 0;
        if (increaseInterestRepayDetailVo != null) {
            IncreaseInterestRepayDetail increaseInterestRepayDetail = new IncreaseInterestRepayDetail();
            BeanUtils.copyProperties(increaseInterestRepayDetailVo,increaseInterestRepayDetail);
            result = rtbLoansService.updateIncreaseInterestRepayDetail(increaseInterestRepayDetail);
        }
        return result;
    }
}
