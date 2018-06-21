package com.hyjf.am.trade.controller;

import java.util.ArrayList;
import java.util.List;

import com.hyjf.am.response.trade.AccountRechargeResponse;
import com.hyjf.am.resquest.trade.BankWithdrawBeanRequest;
import com.hyjf.am.trade.dao.model.auto.AccountRecharge;
import com.hyjf.am.vo.trade.account.AccountRechargeVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.trade.AccountwithdrawResponse;
import com.hyjf.am.trade.dao.model.auto.Accountwithdraw;
import com.hyjf.am.trade.service.AccountWithdrawService;
import com.hyjf.am.vo.trade.account.AccountWithdrawVO;
import com.hyjf.common.util.CommonUtils;

/**
 * @author pangchengchao
 * @version AccountWithdrawController, v0.1 2018/6/11 11:56
 */
@RestController
@RequestMapping("/am-trade/accountWithdraw")
public class AccountWithdrawController {
    private static Logger logger = LoggerFactory.getLogger(AccountWithdrawController.class);

    @Autowired
    private AccountWithdrawService accountWithdrawService;
    /**
     * @Description 插入用户提现记录
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @RequestMapping("/insertAccountWithdrawLog")
    public void insertAccountWithdrawLog(@RequestBody Accountwithdraw accountWithdraw){
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
    public AccountwithdrawResponse findByOrdId(@PathVariable String ordId){
        logger.info("findByOrdId:" + ordId);
        AccountwithdrawResponse response = new AccountwithdrawResponse();
        List<Accountwithdraw> accountWithdrawList = accountWithdrawService.findByOrdId(ordId);
        List<AccountWithdrawVO> accountWithdrawVoList=null;
        if(!CollectionUtils.isEmpty(accountWithdrawList)){
            accountWithdrawVoList=new ArrayList<>(accountWithdrawList.size());
            for (Accountwithdraw accountWithdraw:accountWithdrawList) {
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
    public AccountwithdrawResponse getAccountWithdrawByOrdId(@PathVariable String logOrderId){
        logger.info("getAccountWithdrawByOrdId:" + logOrderId);
        AccountwithdrawResponse response = new AccountwithdrawResponse();
        Accountwithdraw accountWithdraw = accountWithdrawService.getAccountWithdrawByOrdId(logOrderId);
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
    @RequestMapping("/updateAccountwithdrawLog")
    public void updateAccountwithdrawLog(@RequestBody Accountwithdraw accountwithdraw){
        logger.info("updateAccountwithdrawLog:" + JSONObject.toJSONString(accountwithdraw));
        accountWithdrawService.updateAccountwithdrawLog(accountwithdraw);
    }
    
    /**
     * 提现掉单更新订单信息
     * add by jijun 20180616
     */
    @PostMapping("/updateAccountwithdraw")
    public boolean updateAccountwithdraw(@RequestBody AccountWithdrawVO accountWithdraw){
        int count=accountWithdrawService.updateAccountwithdraw(CommonUtils.convertBean(accountWithdraw, Accountwithdraw.class));
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
    public AccountwithdrawResponse getBorrowTender(@PathVariable Integer userId) {
        AccountwithdrawResponse response=new AccountwithdrawResponse();
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

}
