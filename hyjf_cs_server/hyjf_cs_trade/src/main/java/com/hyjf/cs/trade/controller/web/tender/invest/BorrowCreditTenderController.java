/*
 * @Copyright: 2005-2018 so_what. All rights reserved.
 */
package com.hyjf.cs.trade.controller.web.tender.invest;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.trade.TenderRequest;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.util.ClientConstants;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.CustomUtil;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.service.hjh.HjhTenderService;
import com.hyjf.cs.trade.service.invest.BorrowCreditTenderService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

/**
 * @Description web端散标债转出借
 * @Author sunss
 * @Date 2018/7/3 14:02
 */
@Api(tags = "web端-散标债转出借")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/hyjf-web/tender/credit")
public class BorrowCreditTenderController extends BaseTradeController {

    @Autowired
    private BorrowCreditTenderService borrowTenderService;

    @Autowired
    private HjhTenderService hjhTenderService;

    @ApiOperation(value = "web端-散标债转出借", notes = "web端-散标债转出借")
    @PostMapping(value = "/tender", produces = "application/json; charset=utf-8")
    public WebResult<Map<String,Object>> borrowTender(@RequestHeader(value = "userId",required = false) int userId, @RequestBody @Valid TenderRequest tender, HttpServletRequest request) {
        logger.info("web端请求债转出借接口 编号 {}   金额  {} " ,tender.getCreditNid() ,tender.getAssignCapital() );
        String ip = CustomUtil.getIpAddr(request);
        tender.setIp(ip);
        tender.setUserId(userId);
        tender.setPlatform(String.valueOf(ClientConstants.WEB_CLIENT));

        WebResult<Map<String,Object>> result = null;
        try{
            result =  borrowTenderService.borrowCreditTender(tender);
        }catch (CheckException e){
            throw e;
        }
        return result;
    }

    /**
     * web端债转标异步处理
     * @param bean
     * @return
     */
    @ApiIgnore
    @PostMapping("/bgReturn")
    @ResponseBody
    public BankCallResult borrowCreditTenderBgReturn(@RequestBody BankCallBean bean ,Integer platform) {
        logger.info("{}端债转出借异步处理start,userId:{},返回码:{}  userId:{} ",BankCallUtils.getClientName(platform+""), bean.getLogUserId(),bean.getRetCode(),bean.getLogUserId());
        bean.setLogClient(platform);
        BankCallResult result = borrowTenderService.borrowCreditTenderBgReturn(bean);
        return result;
    }

    @ApiOperation(value = "web端债转出借获取出借结果  失败", notes = "web端债转出借获取出借结果  失败")
    @PostMapping(value = "/getResult", produces = "application/json; charset=utf-8")
    public WebResult<Map<String,Object>> getBorrowTenderResult(@RequestHeader(value = "userId") int userId,
                                                               @RequestParam String logOrdId) {
        logger.info("web端债转出借获取出借失败结果，logOrdId{}",logOrdId);
        return  borrowTenderService.getFaileResult(userId,logOrdId);
    }

    @ApiOperation(value = "web端债转出借获取出借结果  成功", notes = "web端债转出借获取出借结果  成功")
    @PostMapping(value = "/getSuccessResult", produces = "application/json; charset=utf-8")
    public WebResult<Map<String,Object>> getSuccessResult(@RequestHeader(value = "userId") int userId,
                                                               @RequestParam String logOrdId) {
        logger.info("web端债转出借获取投标成功结果，logOrdId{}",logOrdId);
        return  borrowTenderService.getSuccessResult(userId,logOrdId);
    }

    @ApiOperation(value = "前端Web页面出借可债转输入出借金额后获取收益", notes = "前端Web页面出借可债转输入出借金额后获取收益")
    @PostMapping(value = "/webCreditTenderInterest", produces = "application/json; charset=utf-8")
    public WebResult<Map<String,Object>> getInterestInfo(@RequestHeader(value = "userId", required = false) int userId,
                                                         @RequestBody @Valid TenderRequest tender) {
        logger.info("前端Web页面出借可债转输入出借金额后获取收益，creditNid:{},assignCapital:{}",tender.getCreditNid(),tender.getAssignCapital());
        JSONObject json = borrowTenderService.getInterestInfo(userId,tender.getCreditNid(),tender.getAssignCapital());
        WebResult result = new WebResult();
        result.setData(json);
        return result;
    }

    @ApiOperation(value = "web端-散标债转出借校验", notes = "web端-散标债转出借校验")
    @PostMapping(value = "/webcheckcredittenderassign", produces = "application/json; charset=utf-8")
    public WebResult<Map<String,Object>> webCheckCreditTenderAssign(@RequestHeader(value = "userId") int userId, @RequestBody @Valid TenderRequest tender, HttpServletRequest request) {
        logger.info("web端请求债转出借校验接口 编号 {}   金额  {} " ,tender.getCreditNid() ,tender.getAssignCapital() );
        String ip = CustomUtil.getIpAddr(request);
        tender.setIp(ip);
        tender.setUserId(userId);
        tender.setPlatform(String.valueOf(ClientConstants.WEB_CLIENT));
        WebResult<Map<String,Object>> result = new WebResult<Map<String,Object>>();
        try{
            Map<String,Object> resultMap =  borrowTenderService.borrowCreditCheck(tender);
            result.setData(resultMap);
            //用户测评校验状态转换
            if(resultMap!=null){
                if(resultMap.get("riskTested") != null && resultMap.get("riskTested") != ""){
                    String riskTested = (String) resultMap.get("riskTested");
                    if(CustomConstants.BANK_TENDER_RETURN_ANSWER_FAIL.equals(riskTested)){
                        //未测评需要重新评测
                        result.setStatus(MsgEnum.ERR_AMT_TENDER_NEED_RISK_ASSESSMENT.getCode());
                    }else if(CustomConstants.BANK_TENDER_RETURN_ANSWER_EXPIRED.equals(riskTested)){
                        //已过期需要重新评测
                        result.setStatus(MsgEnum.STATUS_EV000004.getCode());
                    }else if(CustomConstants.BANK_TENDER_RETURN_CUSTOMER_STANDARD_FAIL.equals(riskTested)){
                        //计划类判断用户类型为标的设置类型以上才可以投资
                        result.setStatus(MsgEnum.STATUS_EV000007.getCode());
                    }else if(CustomConstants.BANK_TENDER_RETURN_LIMIT_EXCESS.equals(riskTested)){
                        //金额对比判断（校验金额 大于 设置测评金额）
                        result.setStatus(MsgEnum.STATUS_EV000005.getCode());
                    }else if(CustomConstants.BANK_TENDER_RETURN_LIMIT_EXCESS_PRINCIPAL.equals(riskTested)){
                        //金额对比判断（校验金额 大于 设置测评金额）代收本金
                        result.setStatus(MsgEnum.STATUS_EV000008.getCode());
                    }
                }
            }
        }catch (CheckException e){
            throw e;
        }
        return result;
    }

}
