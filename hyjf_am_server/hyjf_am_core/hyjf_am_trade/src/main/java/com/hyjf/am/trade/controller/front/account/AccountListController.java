/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.front.account;

import com.hyjf.am.response.trade.ApiTransactionDetailsCustomizeResponse;
import com.hyjf.am.response.trade.account.AccountListResponse;
import com.hyjf.am.resquest.ApiTransactionDetailsRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.AccountList;
import com.hyjf.am.trade.dao.model.customize.ApiTransactionDetailsCustomize;
import com.hyjf.am.trade.service.front.account.AccountListService;
import com.hyjf.am.vo.trade.ApiTransactionDetailsCustomizeVO;
import com.hyjf.am.vo.trade.account.AccountListVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author $yaoy
 * @version AccountController, v0.1 2018/6/14 16:59
 */
@RestController
@RequestMapping("/am-trade/accountList")
public class AccountListController extends BaseController {
    @Autowired
    private AccountListService accountListService;

   
    /**
     * 保存accountList
     * @param accountListVO
     * @return
     */
    @PostMapping("/addAccountList")
    public boolean addAccountList(@RequestBody AccountListVO accountListVO){
    	int count=accountListService.addAccountList(CommonUtils.convertBean(accountListVO, AccountList.class));
    	if(count>0) {
    		return true;
    	}else {
    		return false;
    	}
    }


    /**
     * @Description 根据订单号查询用户提现记录
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @RequestMapping("/selectAccountListByOrdId/{ordId}/{type}")
    public AccountListResponse selectAccountListByOrdId(@PathVariable String ordId, @PathVariable String type){
        logger.info("selectAccountListByOrdId:" + ordId+",type"+type);
        AccountListResponse response = new AccountListResponse();
        AccountList accountList = accountListService.countAccountListByOrdId(ordId,type);
        if (accountList != null) {
            AccountListVO accountListVO = new AccountListVO();
            BeanUtils.copyProperties(accountList, accountListVO);
            response.setResult(accountListVO);
        }
        return response;
    }

    @RequestMapping("/insertAccountListSelective")
    public Integer insertAccountListSelective(@RequestBody @Valid AccountListVO accountListVO) {
        return this.accountListService.insertAccountListSelective(accountListVO);
    }

    @RequestMapping("/updateOfPlanRepayAccount")
    public Integer updateOfPlanRepayAccount(@RequestBody @Valid AccountVO accountVO) {
        return this.accountListService.updateOfPlanRepayAccount(accountVO);
    }

    /**
     * 根据订单id查询交易列表
     * @param orderId
     * @return
     */
    @GetMapping("/selectAccountListByNidCoupon/{orderId}")
    public AccountListResponse selectAccountListByNidCoupon(@PathVariable String orderId) {
        AccountListResponse response = new AccountListResponse();
        AccountList accountList = accountListService.countAccountListByOrderId(orderId);
        if (accountList != null) {
            AccountListVO accountListVO = new AccountListVO();
            BeanUtils.copyProperties(accountList,accountListVO);
            response.setResult(accountListVO);
        }
        return response;
    }

    /**
     * @Author walter.limeng
     * @Description  根据nid和trade查询收支明细
     * @Date 10:11 2018/7/18
     * @Param nid
     * @Param trade
     * @return
     */
    @RequestMapping("/countbynidandtrade/{nid}/{trade}")
    public AccountListResponse countByNidAndTrade(@PathVariable String nid, @PathVariable String trade){
        logger.info("countbynidandtrade:" + nid+",type"+trade);
        AccountListResponse response = new AccountListResponse();
        Integer total = accountListService.countByNidAndTrade(nid,trade);
        response.setTotalRecord(total);
        return response;
    }

    /**
     * 第三方交易明细查询
     * @param detailsRequest
     * @return
     * @Author : huanghui
     */
    @RequestMapping(value = "/selectTransactionDetails", method = RequestMethod.POST)
    public ApiTransactionDetailsCustomizeResponse selectTransactionDetails(@RequestBody ApiTransactionDetailsRequest detailsRequest){
        ApiTransactionDetailsCustomizeResponse response = new ApiTransactionDetailsCustomizeResponse();
        List<ApiTransactionDetailsCustomize> transactionDetailsCustomizeList = new ArrayList<>();

        transactionDetailsCustomizeList = this.accountListService.selectTransactionDetails(detailsRequest);

        if (transactionDetailsCustomizeList != null && transactionDetailsCustomizeList.size() > 0) {
            List<ApiTransactionDetailsCustomizeVO> transactionDetailsCustomizeVO = CommonUtils.convertBeanList(transactionDetailsCustomizeList, ApiTransactionDetailsCustomizeVO.class);
            response.setResultList(transactionDetailsCustomizeVO);
        }
        return response;
    }
}
