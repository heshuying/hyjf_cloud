package com.hyjf.am.trade.controller;

import java.util.ArrayList;
import java.util.List;

import com.hyjf.am.response.admin.WithdrawCustomizeResponse;
import com.hyjf.am.resquest.admin.WithdrawBeanRequest;
import com.hyjf.am.trade.dao.model.customize.admin.WithdrawCustomize;
import com.hyjf.am.vo.admin.finance.withdraw.WithdrawCustomizeVO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.trade.AccountRechargeResponse;
import com.hyjf.am.response.trade.AccountWithdrawResponse;
import com.hyjf.am.resquest.trade.BankWithdrawBeanRequest;
import com.hyjf.am.trade.dao.model.auto.AccountRecharge;
import com.hyjf.am.trade.dao.model.auto.AccountWithdraw;
import com.hyjf.am.trade.service.AccountWithdrawService;
import com.hyjf.am.vo.trade.account.AccountRechargeVO;
import com.hyjf.am.vo.trade.account.AccountWithdrawVO;
import com.hyjf.common.util.CommonUtils;

/**
 * @author pangchengchao
 * @version AccountWithdrawController, v0.1 2018/6/11 11:56
 */
@RestController
@RequestMapping("/am-trade/accountWithdraw")
public class AccountWithdrawController extends BaseController {

    @Autowired
    private AccountWithdrawService accountWithdrawService;
    /**
     * @Description 插入用户提现记录
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @RequestMapping("/insertAccountWithdrawLog")
    public void insertAccountWithdrawLog(@RequestBody AccountWithdraw accountWithdraw){
        logger.info("insertAccountWithdrawLog:" + JSONObject.toJSONString(accountWithdraw));
        accountWithdrawService.insertAccountWithdrawLog(accountWithdraw);
    }
    /**
     * @Description 根据订单号查询用户提现记录
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @RequestMapping("/findByOrdId/{ordId}")
    public AccountWithdrawResponse findByOrdId(@PathVariable String ordId){
        logger.info("findByOrdId:" + ordId);
        AccountWithdrawResponse response = new AccountWithdrawResponse();
        List<AccountWithdraw> accountWithdrawList = accountWithdrawService.findByOrdId(ordId);
        List<AccountWithdrawVO> accountWithdrawVoList=null;
        if(!CollectionUtils.isEmpty(accountWithdrawList)){
            accountWithdrawVoList=new ArrayList<>(accountWithdrawList.size());
            for (AccountWithdraw accountWithdraw:accountWithdrawList) {
                AccountWithdrawVO vo=new AccountWithdrawVO();
                BeanUtils.copyProperties(accountWithdraw,vo);;
                accountWithdrawVoList.add(vo);
            }
        }
        response.setResultList(accountWithdrawVoList);
        return response;
    }
    /**
     * @Description
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @RequestMapping("/getAccountWithdrawByOrdId/{logOrderId}")
    public AccountWithdrawResponse getAccountWithdrawByOrdId(@PathVariable String logOrderId){
        logger.info("getAccountWithdrawByOrdId:" + logOrderId);
        AccountWithdrawResponse response = new AccountWithdrawResponse();
        AccountWithdraw accountWithdraw = accountWithdrawService.getAccountWithdrawByOrdId(logOrderId);
        if (accountWithdraw != null) {
            AccountWithdrawVO accountWithdrawVO = new AccountWithdrawVO();
            BeanUtils.copyProperties(accountWithdraw, accountWithdrawVO);
            response.setResult(accountWithdrawVO);
        }
        return response;

    }


    /**
     * @Description 插入用户提现记录
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @RequestMapping("/updatUserBankWithdrawHandler")
    public int updatUserBankWithdrawHandler(@RequestBody BankWithdrawBeanRequest bankWithdrawBeanRequest){
        logger.info("updatUserBankWithdrawHandler:" + JSONObject.toJSONString(bankWithdrawBeanRequest));
        try {
            return accountWithdrawService.updatUserBankWithdrawHandler(bankWithdrawBeanRequest);
        } catch (Exception e){
            return 0;
        }

    }
    /**
     * @Description 修改用户提现记录
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @RequestMapping("/updateAccountWithdrawLog")
    public void updateAccountWithdrawLog(@RequestBody AccountWithdraw accountwithdraw){
        logger.info("updateAccountWithdrawLog:" + JSONObject.toJSONString(accountwithdraw));
        accountWithdrawService.updateAccountWithdrawLog(accountwithdraw);
    }
    
    /**
     * 提现掉单更新订单信息
     * add by jijun 20180616
     */
    @PostMapping("/updateAccountWithdraw")
    public boolean updateAccountWithdraw(@RequestBody AccountWithdrawVO accountWithdraw){
        int count=accountWithdrawService.updateAccountWithdraw(CommonUtils.convertBean(accountWithdraw, AccountWithdraw.class));
        if (count>0) {
            return true;
        }else{
            return false;
        }
    }
    
	/**
	 * 选择并更新账户提现信息
	 * @param pamaMap
	 */
    @GetMapping("/selectAndUpdateAccountWithdraw")
    public void selectAndUpdateAccountWithdraw(@RequestBody JSONObject pamaMap){
        try {
			accountWithdrawService.selectAndUpdateAccountWithdraw(pamaMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    @GetMapping("/getBorrowTender/{userId}")
    public AccountWithdrawResponse getBorrowTender(@PathVariable Integer userId) {
        AccountWithdrawResponse response=new AccountWithdrawResponse();
        int userBorrowTenderCounte = accountWithdrawService.getBorrowTender(userId);
        response.setUserBorrowTenderCounte(userBorrowTenderCounte);
        return response;
    }

    @GetMapping("/getTodayRecharge/{userId}")
    public AccountRechargeResponse getTodayRecharge(@PathVariable Integer userId) {
        AccountRechargeResponse response = new AccountRechargeResponse();
        List<AccountRecharge> accountRechargeList = accountWithdrawService.getTodayRecharge(userId);
        List<AccountRechargeVO> accountRechargeVOS=null;
        if (accountRechargeList != null) {
            accountRechargeVOS=new ArrayList<>(accountRechargeList.size());
            for (AccountRecharge accountRecharge:accountRechargeList) {
                AccountRechargeVO vo=new AccountRechargeVO();
                BeanUtils.copyProperties(accountRecharge,vo);;
                accountRechargeVOS.add(vo);
            }
        }
        response.setResultList(accountRechargeVOS);
        return response;
    }

    /**
     * 获取提现列表数量
     * @param request
     * @return
     */
    @PostMapping("/getWithdrawRecordCount")
    public int getWithdrawRecordCount(@RequestBody WithdrawBeanRequest request){
        return accountWithdrawService.getWithdrawRecordCount(request);
    }

    /**
     * 获取提现列表
     * @param request
     * @return
     */
    @PostMapping("/getWithdrawRecordList")
    public WithdrawCustomizeResponse getWithdrawRecordList(@RequestBody WithdrawBeanRequest request){
        WithdrawCustomizeResponse response = new WithdrawCustomizeResponse();
        List<WithdrawCustomize> withdrawCustomizes =accountWithdrawService.getWithdrawRecordList(request);
        if (CollectionUtils.isNotEmpty(withdrawCustomizes)){
            response.setResultList(CommonUtils.convertBeanList(withdrawCustomizes,WithdrawCustomizeVO.class));
        }
        return response;
    }

}
