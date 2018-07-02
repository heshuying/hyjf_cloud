/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller;

import com.hyjf.am.response.trade.*;
import com.hyjf.am.response.user.HjhPlanResponse;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.service.BatchHjhBorrowRepayService;
import com.hyjf.am.vo.trade.CalculateInvestInterestVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.borrow.BorrowRecoverVO;
import com.hyjf.am.vo.trade.borrow.BorrowTenderVO;
import com.hyjf.am.vo.trade.hjh.HjhAccedeVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanVO;
import com.hyjf.am.vo.trade.hjh.HjhRepayVO;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version BatchHjhBorrowRepayController, v0.1 2018/6/27 9:20
 */

@RestController
@RequestMapping("/am-trade/batchHjhBorrowRepay")
public class BatchHjhBorrowRepayController extends BaseController {

    @Autowired
    BatchHjhBorrowRepayService batchHjhBorrowRepayService;

    @GetMapping("/selectBorrowTenderListByAccedeOrderId/{accedeOrderId}")
    public BorrowTenderResponse selectBorrowTenderListByAccedeOrderId(@PathVariable String accedeOrderId) {
        BorrowTenderResponse response = new BorrowTenderResponse();
        List<BorrowTender> borrowTender = batchHjhBorrowRepayService.selectBorrowTenderListByAccedeOrderId(accedeOrderId);
        if (borrowTender != null) {
            List<BorrowTenderVO> borrowTenderVOS = CommonUtils.convertBeanList(borrowTender,BorrowTenderVO.class);
            response.setResultList(borrowTenderVOS);
        }
        return response;
    }

    @GetMapping("/selectHjhAccedeListByOrderId/{accedeOrderId}")
    public HjhAccedeResponse selectHjhAccedeListByOrderId(@PathVariable String accedeOrderId) {
        HjhAccedeResponse response = new HjhAccedeResponse();
        List<HjhAccede> hjhAccedes = batchHjhBorrowRepayService.selectHjhAccedeListByOrderId(accedeOrderId);
        if (hjhAccedes != null) {
            List<HjhAccedeVO> hjhAccedeVOS = CommonUtils.convertBeanList(hjhAccedes,HjhAccedeVO.class);
            response.setResultList(hjhAccedeVOS);
        }
        return response;
    }

    @GetMapping("/selectHjhPlanListByPlanNid/{planNid}")
    public HjhPlanResponse selectHjhPlanListByPlanNid(@PathVariable String planNid) {
        HjhPlanResponse response = new HjhPlanResponse();
        List<HjhPlan> hjhPlans = batchHjhBorrowRepayService.selectHjhPlanListByPlanNid(planNid);
        if (hjhPlans != null) {
            List<HjhPlanVO> hjhPlanVOS = CommonUtils.convertBeanList(hjhPlans,HjhPlanVO.class);
            response.setResultList(hjhPlanVOS);
        }
        return response;
    }

    @GetMapping("/selectHjhAccedeListById/{id}")
    public HjhAccedeResponse selectHjhAccedeListById(@PathVariable Integer id) {
        HjhAccedeResponse response = new HjhAccedeResponse();
        List<HjhAccede> hjhAccedes = batchHjhBorrowRepayService.selectHjhAccedeListById(id);
        if (hjhAccedes != null) {
            List<HjhAccedeVO> hjhAccedeVOS = CommonUtils.convertBeanList(hjhAccedes,HjhAccedeVO.class);
            response.setResultList(hjhAccedeVOS);
        }
        return response;
    }

    @GetMapping("/selectBorrowRecoverListByAccedeOrderId/{accedeOrderId}")
    public BorrowRecoverResponse selectBorrowRecoverListByAccedeOrderId(@PathVariable String accedeOrderId) {
        BorrowRecoverResponse response = new BorrowRecoverResponse();
        List<BorrowRecover> borrowRecovers = batchHjhBorrowRepayService.selectBorrowRecoverListByAccedeOrderId(accedeOrderId);
        if (borrowRecovers != null) {
            List<BorrowRecoverVO> borrowRecoverVOS = CommonUtils.convertBeanList(borrowRecovers,BorrowRecoverVO.class);
            response.setResultList(borrowRecoverVOS);
        }
        return response;
    }

    @GetMapping("/selectHjhRepayListByAccedeOrderId/{accedeOrderId}")
    public HjhRepayResponse selectHjhRepayListByAccedeOrderId(@PathVariable String accedeOrderId) {
        HjhRepayResponse response = new HjhRepayResponse();
        List<HjhRepay> hjhRepays = batchHjhBorrowRepayService.selectHjhRepayListByAccedeOrderId(accedeOrderId);
        if (hjhRepays != null) {
            List<HjhRepayVO> hjhRepayVOS = CommonUtils.convertBeanList(hjhRepays,HjhRepayVO.class);
            response.setResultList(hjhRepayVOS);
        }
        return response;
    }

    @GetMapping("/selectHjhRepayListById/{id}")
    public HjhRepayResponse selectHjhRepayListById(@PathVariable Integer id) {
        HjhRepayResponse response = new HjhRepayResponse();
        HjhRepay hjhRepay = batchHjhBorrowRepayService.selectHjhRepayListById(id);
        if (hjhRepay != null) {
            HjhRepayVO hjhRepayVO = new HjhRepayVO();
            BeanUtils.copyProperties(hjhRepay,hjhRepayVO);
            response.setResult(hjhRepayVO);
        }
        return response;
    }

    @GetMapping("/selectCalculateInvestInterest/{accedeOrderId}")
    public CalculateInvestInterestResponse selectCalculateInvestInterest() {
        CalculateInvestInterestResponse response = new CalculateInvestInterestResponse();
        List<CalculateInvestInterest> calculateInvestInterests = batchHjhBorrowRepayService.selectCalculateInvestInterest();
        if (calculateInvestInterests != null) {
            List<CalculateInvestInterestVO> calculateInvestInterestVOS = CommonUtils.convertBeanList(calculateInvestInterests,CalculateInvestInterestVO.class);
            response.setResultList(calculateInvestInterestVOS);
        }
        return response;
    }

    @RequestMapping("/updateHjhBorrowRepayInterest")
    public Integer updateHjhBorrowRepayInterest(@RequestBody @Valid HjhAccedeVO hjhAccedeVO) {
        return this.batchHjhBorrowRepayService.updateHjhBorrowRepayInterest(hjhAccedeVO);
    }

    @RequestMapping("/updateHjhAccedeByPrimaryKey")
    public Integer updateHjhAccedeByPrimaryKey(@RequestBody @Valid HjhAccedeVO hjhAccedeVO) {
        return this.batchHjhBorrowRepayService.updateHjhAccedeByPrimaryKey(hjhAccedeVO);
    }

    @RequestMapping("/insertHjhBorrowRepay")
    public Integer insertHjhBorrowRepay(@RequestBody @Valid HjhRepayVO hjhRepayVO) {
        return this.batchHjhBorrowRepayService.insertHjhBorrowRepay(hjhRepayVO);
    }

    @RequestMapping("/updateBankTotalForLockPlan")
    public Integer updateBankTotalForLockPlan(@RequestBody @Valid AccountVO accountVO) {
        return this.batchHjhBorrowRepayService.updateBankTotalForLockPlan(accountVO);
    }

    @RequestMapping("/updateHjhRepayByPrimaryKey")
    public Integer updateHjhRepayByPrimaryKey(@RequestBody @Valid HjhRepayVO hjhRepayVO) {
        return this.batchHjhBorrowRepayService.updateHjhRepayByPrimaryKey(hjhRepayVO);
    }

    @RequestMapping("/updateCalculateInvestByPrimaryKey")
    public Integer updateCalculateInvestByPrimaryKey(@RequestBody @Valid CalculateInvestInterestVO calculateInvestInterestVO) {
        return this.batchHjhBorrowRepayService.updateCalculateInvestByPrimaryKey(calculateInvestInterestVO);
    }

}
