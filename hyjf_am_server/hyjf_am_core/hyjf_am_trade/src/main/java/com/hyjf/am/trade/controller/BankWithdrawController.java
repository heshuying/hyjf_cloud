package com.hyjf.am.trade.controller;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.am.response.trade.AccountwithdrawResponse;
import com.hyjf.am.trade.dao.model.auto.Accountwithdraw;
import com.hyjf.am.trade.service.BankWithdrawService;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.account.AccountWithdrawVO;
import com.hyjf.common.util.CommonUtils;

import io.swagger.annotations.Api;

@Api(value = "江西银行提现掉单异常处理定时任务")
@RestController
@RequestMapping("/am-trade/bankWithdraw")
public class BankWithdrawController {

    private static final Logger logger = LoggerFactory.getLogger(BankWithdrawController.class);

    @Autowired
    private BankWithdrawService bankWithdrawService;

    /**
     * 检索处理中的充值订单
     * @return
     */
    @RequestMapping(value = "/selectBankWithdrawList")
    public AccountwithdrawResponse selectBankWithdrawList(){
        logger.info("selectBankWithdrawList...");
        AccountwithdrawResponse response = new AccountwithdrawResponse();
        List<Accountwithdraw> withdrawList=bankWithdrawService.selectBankWithdrawList();
        if (CollectionUtils.isNotEmpty(withdrawList)){
            List<AccountWithdrawVO> voList = CommonUtils.convertBeanList(withdrawList, AccountWithdrawVO.class);
            response.setResultList(voList);
        }
        return response;
    }

    /**
     * 
     * @return
     */
    @RequestMapping(value = "/updateBankWithdraw")
    public boolean updateBankWithdraw(@RequestBody AccountVO accountVO){
    	int count = bankWithdrawService.updateBankWithdraw(accountVO);
    	if(count>0) {
    		return true;
    	}else {
    		return false;
    	}
    }
    

}