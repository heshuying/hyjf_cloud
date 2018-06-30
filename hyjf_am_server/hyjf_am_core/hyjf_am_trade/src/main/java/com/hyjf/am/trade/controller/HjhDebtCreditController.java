/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller;

import com.hyjf.am.response.trade.AccountListResponse;
import com.hyjf.am.response.trade.BorrowRepayResponse;
import com.hyjf.am.response.trade.HjhDebtCreditResponse;
import com.hyjf.am.response.trade.HjhDebtCreditTenderResponse;
import com.hyjf.am.resquest.trade.HjhDebtCreditRequest;
import com.hyjf.am.trade.dao.model.auto.AccountList;
import com.hyjf.am.trade.dao.model.auto.BorrowRepay;
import com.hyjf.am.trade.dao.model.auto.HjhDebtCredit;
import com.hyjf.am.trade.dao.model.auto.HjhDebtCreditTender;
import com.hyjf.am.trade.service.HjhDebtCreditService;
import com.hyjf.am.vo.trade.account.AccountListVO;
import com.hyjf.am.vo.trade.borrow.BorrowRepayVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditTenderVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditVO;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.Api;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version HjhDebtCreditController, v0.1 2018/6/27 14:43
 */

@Api(value = "汇计划加入明细表")
@RestController
@RequestMapping("/am-trade/hjhDebtCredit")
public class HjhDebtCreditController {

    @Autowired
    HjhDebtCreditService hjhDebtCreditService;

    @GetMapping("/selectHjhDebtCreditListByAccedeOrderId/{accedeOrderId}")
    public HjhDebtCreditResponse selectHjhDebtCreditListByAccedeOrderId(@PathVariable String accedeOrderId) {
        HjhDebtCreditResponse response = new HjhDebtCreditResponse();
        List<HjhDebtCredit> hjhDebtCredits = hjhDebtCreditService.selectHjhDebtCreditListByAccedeOrderId(accedeOrderId);
        if(hjhDebtCredits != null){
            List<HjhDebtCreditVO> hjhDebtCreditVOS = CommonUtils.convertBeanList(hjhDebtCredits,HjhDebtCreditVO.class);
            response.setResultList(hjhDebtCreditVOS);
        }
        return response;
    }

    /**
     * @Author liushouyi
     * @Version v0.1
     * @Date
     */
    @GetMapping("/selectHjhDebtCreditListByOrderIdNid/{accedeOrderId}/{borrowNid}")
    public HjhDebtCreditResponse selectHjhDebtCreditListByOrderIdNid(@PathVariable String accedeOrderId, @PathVariable String borrowNid){
        HjhDebtCreditResponse response = new HjhDebtCreditResponse();
        List<HjhDebtCredit> hjhDebtCredits = hjhDebtCreditService.selectHjhDebtCreditListByOrderIdNid(accedeOrderId,borrowNid);
        if(hjhDebtCredits != null){
            List<HjhDebtCreditVO> hjhDebtCreditVOS = CommonUtils.convertBeanList(hjhDebtCredits,HjhDebtCreditVO.class);
            response.setResultList(hjhDebtCreditVOS);
        }
        return response;
    }


    @GetMapping("/selectHjhCreditTenderListByAssignOrderId/{assignOrderId}")
    public HjhDebtCreditTenderResponse selectHjhCreditTenderListByAssignOrderId(@PathVariable String assignOrderId){
        HjhDebtCreditTenderResponse response = new HjhDebtCreditTenderResponse();
        List<HjhDebtCreditTender> hjhDebtCreditTenders =hjhDebtCreditService.selectHjhCreditTenderListByAssignOrderId(assignOrderId);
        if (CollectionUtils.isNotEmpty(hjhDebtCreditTenders)){
            response.setResultList(CommonUtils.convertBeanList(hjhDebtCreditTenders,HjhDebtCreditTenderVO.class));
        }
        return response;
    }


    @PostMapping("/getHjhDebtCreditList")
    public HjhDebtCreditResponse getHjhDebtCreditList(@RequestBody HjhDebtCreditRequest request){
        HjhDebtCreditResponse response=new HjhDebtCreditResponse();
        List<HjhDebtCredit> borrowCredits=hjhDebtCreditService.getHjhDebtCreditList(request);
        if (CollectionUtils.isNotEmpty(borrowCredits)){
            response.setResultList(CommonUtils.convertBeanList(borrowCredits,HjhDebtCreditVO.class));
        }
        return response;
    }

}
