package com.hyjf.am.trade.controller.front.account;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.BooleanResponse;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.admin.WithdrawCustomizeResponse;
import com.hyjf.am.response.trade.account.AccountRechargeResponse;
import com.hyjf.am.response.trade.account.AccountWithdrawResponse;
import com.hyjf.am.resquest.admin.WithdrawBeanRequest;
import com.hyjf.am.resquest.trade.BankWithdrawBeanRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.AccountRecharge;
import com.hyjf.am.trade.dao.model.auto.AccountWithdraw;
import com.hyjf.am.trade.dao.model.customize.WithdrawCustomize;
import com.hyjf.am.trade.service.front.account.AccountWithdrawService;
import com.hyjf.am.vo.admin.finance.withdraw.WithdrawCustomizeVO;
import com.hyjf.am.vo.trade.account.AccountRechargeVO;
import com.hyjf.am.vo.trade.account.AccountWithdrawVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
    public IntegerResponse insertAccountWithdrawLog(@RequestBody AccountWithdraw accountWithdraw){
        logger.info("insertAccountWithdrawLog:" + JSONObject.toJSONString(accountWithdraw));
        IntegerResponse response=new IntegerResponse();
        try {
            response.setResultInt(accountWithdrawService.insertAccountWithdrawLog(accountWithdraw));
            return  response;
        } catch (Exception e){
            return response;
        }
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
                BeanUtils.copyProperties(accountWithdraw,vo);
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
     * @Description 修改用户提现记录
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @RequestMapping("/updatUserBankWithdrawHandler")
    public IntegerResponse updatUserBankWithdrawHandler(@RequestBody BankWithdrawBeanRequest bankWithdrawBeanRequest){
        logger.info("updatUserBankWithdrawHandler:" + JSONObject.toJSONString(bankWithdrawBeanRequest));
        IntegerResponse response=new IntegerResponse();
        try {
            response.setResultInt(accountWithdrawService.updatUserBankWithdrawHandler(bankWithdrawBeanRequest));
            return response;
        } catch (Exception e){
            return response;
        }

    }
    /**
     * @Description 修改用户提现记录
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @PostMapping("/updateAccountWithdrawLog")
    public IntegerResponse updateAccountWithdrawLog(@RequestBody AccountWithdraw accountwithdraw){
        logger.info("updateAccountWithdrawLog:" + JSONObject.toJSONString(accountwithdraw));
        IntegerResponse response=new IntegerResponse();
        try {
            response.setResultInt(accountWithdrawService.updateAccountWithdrawLog(accountwithdraw));
            return response;
        } catch (Exception e){
            return response;
        }
    }
    
    /**
     * 提现掉单更新订单信息
     * add by jijun 20180616
     */
    @PostMapping("/updateAccountWithdraw")
    public BooleanResponse updateAccountWithdraw(@RequestBody AccountWithdrawVO accountWithdraw){
        int count=accountWithdrawService.updateAccountWithdraw(CommonUtils.convertBean(accountWithdraw, AccountWithdraw.class));
        BooleanResponse response=new BooleanResponse();
        if (count>0) {
            response.setResultBoolean(true);
            return response;
        }else{
            response.setResultBoolean(false);
            return response;
        }
    }
    
	/**
	 * 选择并更新账户提现信息
	 * @param pamaMap
	 */
    @GetMapping("/selectAndUpdateAccountWithdraw")
    public BooleanResponse selectAndUpdateAccountWithdraw(@RequestBody JSONObject pamaMap){
        BooleanResponse response=new BooleanResponse();
        try {
            response.setResultBoolean(accountWithdrawService.selectAndUpdateAccountWithdraw(pamaMap));
			return response;
		} catch (Exception e) {
			logger.error(e.getMessage());
            return response;
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
                BeanUtils.copyProperties(accountRecharge,vo);
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
    public IntegerResponse getWithdrawRecordCount(@RequestBody WithdrawBeanRequest request){
        IntegerResponse response=new IntegerResponse();
        response.setResultInt(accountWithdrawService.getWithdrawRecordCount(request));
        return response;
    }

    /**
     * 获取提现列表
     * @param request
     * @return
     */
    @PostMapping("/getWithdrawRecordList")
    public WithdrawCustomizeResponse getWithdrawRecordList(@RequestBody WithdrawBeanRequest request){
        WithdrawCustomizeResponse response = new WithdrawCustomizeResponse();
        int count=accountWithdrawService.getWithdrawRecordCount(request);
        if (count==0){
            return response;
        }
        if(request.getCurrPage()>0){
            Paginator paginator = new Paginator(request.getCurrPage(),count);
            request.setLimitStart(paginator.getOffset());
            request.setLimitEnd(paginator.getLimit());
        }

        List<WithdrawCustomize> withdrawCustomizes =accountWithdrawService.getWithdrawRecordList(request);
        String returnCode = "0";
        if (CollectionUtils.isNotEmpty(withdrawCustomizes)){
            response.setCount(count);
            response.setResultList(CommonUtils.convertBeanList(withdrawCustomizes,WithdrawCustomizeVO.class));
            response.setRtn(returnCode);
        }
        return response;
    }


    /**
     * 根据用户ID查询用户提现记录
     *
     * @param userId
     * @return
     */
    @RequestMapping("/selectAccountWithdrawByUserId/{userId}")
    public AccountWithdrawResponse selectAccountWithdrawByUserId(@PathVariable Integer userId) {
        AccountWithdrawResponse accountWithdrawResponse = new AccountWithdrawResponse();
        List<AccountWithdraw> accountWithdrawList = accountWithdrawService.selectAccountWithdrawByUserId(userId);
        if (CollectionUtils.isNotEmpty(accountWithdrawList)){
            accountWithdrawResponse.setResultList(CommonUtils.convertBeanList(accountWithdrawList,AccountWithdrawVO.class));
        }
        return accountWithdrawResponse;
    }
}
