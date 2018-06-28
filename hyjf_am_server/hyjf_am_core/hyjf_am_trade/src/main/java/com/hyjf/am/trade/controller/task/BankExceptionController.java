package com.hyjf.am.trade.controller.task;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.trade.*;
import com.hyjf.am.resquest.trade.BatchBorrowTenderCustomizeRequest;
import com.hyjf.am.resquest.trade.BorrowCreditRequest;
import com.hyjf.am.resquest.trade.BorrowTenderTmpRequest;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.dao.model.customize.BatchBorrowTenderCustomize;
import com.hyjf.am.trade.service.*;
import com.hyjf.am.vo.trade.BorrowCreditVO;
import com.hyjf.am.vo.trade.CreditTenderLogVO;
import com.hyjf.am.vo.trade.CreditTenderVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.account.AccountWithdrawVO;
import com.hyjf.am.vo.trade.borrow.BatchBorrowTenderCustomizeVO;
import com.hyjf.am.vo.trade.borrow.BorrowTenderTmpVO;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.Api;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "江西银行充值掉单异常处理定时任务")
@RestController
@RequestMapping("/am-trade/bankException")
public class BankExceptionController {

    private static final Logger logger = LoggerFactory.getLogger(BankExceptionController.class);

    @Autowired
    private BankRechargeService bankRechargeService;
    @Autowired
    private BankWithdrawService bankWithdrawService;
    @Autowired
    private BankCreditTenderService bankCreditTenderService;
    @Autowired
    private BankInvestService bankInvestExceptionService;
	@Autowired
	private BankInvestAllService bankInvestAllExceptionService;
    @Autowired
	private BankTenderCancelService bankTenderCancelService;

    @RequestMapping("/recharge")
    public void recharge(){
        logger.info("recharge...");
        bankRechargeService.recharge();
    }


    @GetMapping("/selectCreditTender/{assignNid}")
    public CreditTenderResponse selectCreditTender(@PathVariable String assignNid){
        CreditTenderResponse response = new CreditTenderResponse();
        List<CreditTender> creditTenderList=bankCreditTenderService.selectCreditTender(assignNid);
        if (CollectionUtils.isNotEmpty(creditTenderList)){
            List<CreditTenderVO> voList = CommonUtils.convertBeanList(creditTenderList, CreditTenderVO.class);
            response.setResultList(voList);
        }

        return response;
    }


    /**
     * 查询债转承接掉单的数据
     */
    @GetMapping("/selectCreditTenderLogs")
    public CreditTenderLogResponse selectCreditTenderLogs(){
        logger.info("selectCreditTenderLogs...");
        CreditTenderLogResponse response = new CreditTenderLogResponse();
        List<CreditTenderLog> creditTenderLogList=bankCreditTenderService.selectCreditTenderLogs();
        if (CollectionUtils.isNotEmpty(creditTenderLogList)){
            List<CreditTenderLogVO> voList = CommonUtils.convertBeanList(creditTenderLogList, CreditTenderLogVO.class);
            response.setResultList(voList);
        }
        return response;
    }


    /**
     * 获取银行充值记录
     * @return
     */
    @RequestMapping(value = "/selectBankWithdrawList")
    public AccountWithdrawResponse selectBankWithdrawList(){
        logger.info("selectBankWithdrawList...");
        AccountWithdrawResponse response = new AccountWithdrawResponse();
        List<AccountWithdraw> withdrawList=bankWithdrawService.selectBankWithdrawList();
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
    	logger.info("updateBankWithdraw...");
    	int count = bankWithdrawService.updateBankWithdraw(accountVO);
    	if(count>0) {
    		return true;
    	}else {
    		return false;
    	}
    }


    @PostMapping(value = "/updateCreditTenderLog")
    public Boolean updateCreditTenderLog(@RequestBody CreditTenderLogVO creditTenderLog){
        logger.info("updateCreditTenderLog...");
        int count = bankCreditTenderService.updateCreditTenderLog(creditTenderLog);
        if(count>0) {
            return true;
        }else {
            return false;
        }
    }

    /**
     * 同步回调收到后,根据logOrderId检索投资记录表
     * @param logOrderId
     * @return
     */
    @GetMapping(value = "/selectCreditTenderLogByOrderId")
    public CreditTenderLogResponse selectCreditTenderLogByOrderId(@PathVariable String logOrderId){
        logger.info("selectCreditTenderLogByOrderId...");
        CreditTenderLogResponse response = new CreditTenderLogResponse();
        CreditTenderLog creditTenderLog =bankCreditTenderService.selectCreditTenderLogByOrderId(logOrderId);
        if(null != creditTenderLog){
            response.setResult(CommonUtils.convertBean(creditTenderLog,CreditTenderLogVO.class));
        }
        return response;
    }


    @GetMapping("/selectByOrderIdAndUserId/{assignOrderId}/{userId}")
    public CreditTenderLogResponse selectByOrderIdAndUserId(@PathVariable String assignOrderId,@PathVariable Integer userId){
        CreditTenderLogResponse response = new CreditTenderLogResponse();
        List<CreditTenderLog> creditTenderLogs=bankCreditTenderService.selectByOrderIdAndUserId(assignOrderId,userId);
        if(CollectionUtils.isNotEmpty(creditTenderLogs)){
            List<CreditTenderLogVO> voList = CommonUtils.convertBeanList(creditTenderLogs, CreditTenderLogVO.class);
            response.setResultList(voList);
        }
        return response;
    }

    /**
     * 刪除
     * @param assignOrderId
     * @param userId
     * @return
     */
    @GetMapping("/deleteByOrderIdAndUserId/{assignOrderId}/{userId}")
    public Boolean deleteByOrderIdAndUserId(@PathVariable String assignOrderId,@PathVariable Integer userId){
        int count=bankCreditTenderService.deleteByOrderIdAndUserId(assignOrderId,userId);
        if(count>0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 用户提现回调方法
     * @param para
     * @return
     */
    @PostMapping("/handlerAfterCash")
    public Boolean handlerAfterCash(@RequestBody JSONObject para){
        Boolean ret = true;
        try {
            ret = bankWithdrawService.updateHandlerAfterCash(para);
        }catch (Exception e){
            ret = false;
        }
        return ret;

    }
    
    /**
     * 获取BorrowCredit列表
     * @param request
     * @return
     */
    @PostMapping("/getBorrowCreditList")
    public BorrowCreditResponse getBorrowCreditList(@RequestBody BorrowCreditRequest request) {
    	BorrowCreditResponse response = new BorrowCreditResponse();
         List<BorrowCredit> borrowCredits=bankCreditTenderService.getBorrowCreditList(request);
         if(CollectionUtils.isNotEmpty(borrowCredits)){
             List<BorrowCreditVO> voList = CommonUtils.convertBeanList(borrowCredits, BorrowCreditVO.class);
             response.setResultList(voList);
         }
         return response;
    }

    /**
     * 查询出投资表authcode为空的记录
     * @return
     */
    @GetMapping("/queryAuthCodeBorrowTenderList")
    public BatchBorrowTenderCustomizeResponse queryAuthCodeBorrowTenderList(){
        BatchBorrowTenderCustomizeResponse response = new BatchBorrowTenderCustomizeResponse();
        List<BatchBorrowTenderCustomize> list = bankInvestExceptionService.queryAuthCodeBorrowTenderList();
        if (CollectionUtils.isNotEmpty(list)){
            response.setResultList(CommonUtils.convertBeanList(list,BatchBorrowTenderCustomizeVO.class));
        }
        return response;
    }


    /**
     * 处理投资掉单
     * add by jijun 20180623
     */
    @PostMapping("/insertAuthCode")
    public void insertAuthCode(@RequestBody BatchBorrowTenderCustomizeRequest request){
        List<BatchBorrowTenderCustomizeVO> batchBorrowTenderCustomizeVOList = request.getBatchBorrowTenderCustomizeList();
        List<BatchBorrowTenderCustomize> list = CommonUtils.convertBeanList(batchBorrowTenderCustomizeVOList,BatchBorrowTenderCustomize.class);
        bankInvestExceptionService.insertAuthCode(list);

    }

	/**
     * 投資全部掉單批處理
     */
    @GetMapping("/getBorrowTenderTmpList")
    public BorrowTenderTmpResponse getBorrowTenderTmpList(){
        BorrowTenderTmpResponse response = new BorrowTenderTmpResponse();
        List<BorrowTenderTmp> BorrowTenderTmpList=bankInvestAllExceptionService.getBorrowTenderTmpList();
        if (CollectionUtils.isNotEmpty(BorrowTenderTmpList)){
            response.setResultList(CommonUtils.convertBeanList(BorrowTenderTmpList,BorrowTenderTmpVO.class));
        }
        return response;
    }


    /**
     * 开始进行掉单修复
     * @param request
     * @return
     */
    @PostMapping("/updateTenderStart")
    public boolean updateTenderStart(@RequestBody BorrowTenderTmpRequest request) {
        boolean ret = true;
        try {
            bankInvestAllExceptionService.updateTenderStart(request);
        } catch (Exception e) {
            e.printStackTrace();
            ret = false;
        }

        return ret;
    }


    @GetMapping("/getBorrowTenderTmpsForTenderCancel")
    public BorrowTenderTmpResponse getBorrowTenderTmpsForTenderCancel(){
        BorrowTenderTmpResponse response = new BorrowTenderTmpResponse();
        List<BorrowTenderTmp> tmpList = this.bankTenderCancelService.getBorrowTenderTmpsForTenderCancel();
        if (CollectionUtils.isNotEmpty(tmpList)){
            response.setResultList(CommonUtils.convertBeanList(tmpList,BorrowTenderTmpVO.class));
        }
        return response;
    }


    @PostMapping("/updateBidCancelRecord")
    public boolean updateBidCancelRecord(@RequestBody JSONObject para){
        return this.bankTenderCancelService.updateBidCancelRecord(para);
    }



    @PostMapping("/updateTenderCancelExceptionData")
    public int updateTenderCancelExceptionData(@RequestBody BorrowTenderTmpVO info){
        return this.bankTenderCancelService.updateTenderCancelExceptionData(CommonUtils.convertBean(info,BorrowTenderTmp.class));
    }


}