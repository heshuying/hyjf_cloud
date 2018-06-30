/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller;

import com.hyjf.am.response.trade.BorrowCreditDetailResponse;
import com.hyjf.am.response.trade.BorrowCreditResponse;
import com.hyjf.am.resquest.trade.BorrowCreditRequest;
import com.hyjf.am.trade.dao.model.auto.BorrowCredit;
import com.hyjf.am.trade.service.BorrowCreditService;
import com.hyjf.am.vo.trade.BorrowCreditVO;
import com.hyjf.am.vo.trade.borrow.BorrowCreditDetailVO;
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
 * @author PC-LIUSHOUYI
 * @version BorrowCreditController, v0.1 2018/6/24 10:48
 */

@Api(value = "债转信息")
@RestController
@RequestMapping("/am-trade/borrowCredit")
public class BorrowCreditController {

    @Autowired
    BorrowCreditService borrowCreditService;

    /**
     * 查询债转状态为0的数据
     * @return
     */
    @RequestMapping("/selectBorrowCreditList")
    public BorrowCreditResponse selectBorrowCreditList() {
        BorrowCreditResponse response = new BorrowCreditResponse();
        List<BorrowCredit> borrowCredits = borrowCreditService.selectBorrowCreditList();
        if(borrowCredits != null){
            List<BorrowCreditVO> borrowCreditVOS = CommonUtils.convertBeanList(borrowCredits,BorrowCreditVO.class);
            response.setResultList(borrowCreditVOS);
        }
        return response;
    }

    /**
     * 更新债转状态为1
     *
     * @param borrowCreditVO
     * @return
     */
    @RequestMapping("/updateBorrowCredit")
    public Integer updateCreditCredit(@RequestBody @Valid BorrowCreditVO borrowCreditVO) {
        return this.borrowCreditService.updateBorrowCredit(borrowCreditVO);
    }



    @RequestMapping("/borrowCreditDetail/{creditNid}")
    public BorrowCreditDetailResponse updateCreditCredit(@RequestBody @Valid String creditNid) {
        BorrowCreditDetailResponse response = new BorrowCreditDetailResponse();
        BorrowCreditDetailVO detailVO = borrowCreditService.getBorrowCreditDetail(creditNid);
        response.setResult(detailVO);
        return response;
    }


    @PostMapping("/getBorrowCreditList")
    public BorrowCreditResponse getBorrowCreditList(@RequestBody BorrowCreditRequest request1) {
    	BorrowCreditResponse response = new BorrowCreditResponse();
    	List<BorrowCredit> borrowCredits =borrowCreditService.getBorrowCreditList(request1);
    	if(CollectionUtils.isNotEmpty(borrowCredits)) {
    		response.setResultList(CommonUtils.convertBeanList(borrowCredits, BorrowCreditVO.class));
    	}
    	return response;
    }

    
}
