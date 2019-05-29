/*
 * @Copyright: 2005-2018 so_what. All rights reserved.
 */
package com.hyjf.cs.trade.controller.web.tender.invest;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.trade.TenderRequest;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.util.ClientConstants;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.CustomUtil;
import com.hyjf.cs.common.annotation.RequestLimit;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.trade.bean.TenderInfoResult;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.service.hjh.HjhTenderService;
import com.hyjf.cs.trade.service.invest.BorrowTenderService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

/**
 * @Description Web端散标出借
 * @Author sss
 * @Version v0.1
 * @Date 2018/6/19 9:32
 */
@Api(tags = "Web端-散标出借")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/hyjf-web/tender/borrow")
public class BorrowTenderController extends BaseTradeController {
    private static final Logger logger = LoggerFactory.getLogger(BorrowTenderController.class);

    @Autowired
    private BorrowTenderService borrowTenderService;
    @Autowired
    private HjhTenderService hjhTenderService;

    @ApiOperation(value = "散标出借", notes = "web端散标出借")
    @PostMapping(value = "/tender", produces = "application/json; charset=utf-8")
    @RequestLimit(seconds=3)
    @ApiImplicitParam(name = "tender",value = "{mobile:String}",dataType = "Map")
    public WebResult<Map<String,Object>> borrowTender(@RequestHeader(value = "userId", required = false) Integer userId,
                                                      @RequestBody @Valid TenderRequest tender, HttpServletRequest request) {
        logger.info("web端请求出借接口");
        String ip = CustomUtil.getIpAddr(request);
        tender.setIp(ip);
        tender.setUserId(userId);
        tender.setPlatform(String.valueOf(ClientConstants.WEB_CLIENT));
        WebResult<Map<String,Object>> result = null;
        try{
            result =  borrowTenderService.borrowTender(tender);
        }catch (CheckException e){
            throw e;
        }finally {
            RedisUtils.del(RedisConstants.BORROW_TENDER_REPEAT + userId);
        }
        return result;
    }

    @ApiOperation(value = "散标出借校验", notes = "web端散标出借校验")
    @PostMapping(value = "/investCheck", produces = "application/json; charset=utf-8")
    public WebResult<Map<String,Object>> investCheck(@RequestHeader(value = "userId", required = false) Integer userId,
                                                      @RequestBody @Valid TenderRequest tender, HttpServletRequest request) {
        WebResult<Map<String,Object>> result = new WebResult<Map<String,Object>>();
        logger.info("web端请求出借校验接口");
        String ip = CustomUtil.getIpAddr(request);
        tender.setIp(ip);
        tender.setUserId(userId);
        tender.setPlatform(String.valueOf(ClientConstants.WEB_CLIENT));
        Map<String,Object>  resultMap =  borrowTenderService.borrowTenderCheck(tender,null,null,null,null);
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
        return result;
    }

    /**
     * web端散标异步处理
     * @param bean
     * @param couponGrantId
     * @return
     */
    @ApiIgnore
    @PostMapping("/bgReturn")
    @ResponseBody
    public BankCallResult borrowTenderBgReturn(@RequestBody BankCallBean bean ,Integer platform, @RequestParam("couponGrantId") String couponGrantId) {
        logger.info("{}端散标出借异步处理start,userId:{},优惠券:{}",BankCallUtils.getClientName(platform+""), bean.getLogUserId(),couponGrantId);
        BankCallResult result = new BankCallResult();
        String isRealTender = RedisUtils.get(RedisConstants.BORROW_TENDER_ORDER_CHECK+bean.getLogOrderId());
        if(isRealTender!=null && isRealTender.length()>3){
            logger.info("已经处理过了，不需要重新处理，订单号:{}  标的编号:{}  userId:{}",bean.getLogOrderId(),bean.getProductId(),bean.getLogUserId());
            result.setStatus(true);
            return result;
        }
        try{
            if (platform != null && platform.intValue() >= 0) {
                bean.setLogClient(platform);
            }
            result = borrowTenderService.borrowTenderBgReturn(bean,couponGrantId);
        }catch (CheckException e){
            logger.info(e.getMessage());
            BankCallResult resultError = new BankCallResult();
            resultError.setStatus(true);
            return resultError;
        }finally {
            RedisUtils.del(RedisConstants.BORROW_TENDER_REPEAT + bean.getLogUserId());
        }
        return result;
    }

    @ApiOperation(value = "web端散标出借获取出借结果", notes = "web端散标出借获取出借结果")
    @PostMapping(value = "/getBorrowTenderResult", produces = "application/json; charset=utf-8")
    public WebResult<Map<String,Object>> getBorrowTenderResult(@RequestHeader(value = "userId",required = false) Integer userId,
                                                               @RequestParam String logOrdId,
                                                               @RequestParam String borrowNid,
                                                               HttpServletRequest request) {
        logger.info("web端请求获取出借结果接口，logOrdId:{}  borrowNid:{} isPrincipal:{}",logOrdId , borrowNid);
        return  borrowTenderService.getBorrowTenderResult(userId,logOrdId,borrowNid);
    }

    @ApiOperation(value = "web端散标出借获取投标成功结果", notes = "web端散标出借获取投标成功结果")
    @PostMapping(value = "/getBorrowTenderResultSuccess", produces = "application/json; charset=utf-8")
    public WebResult<Map<String, Object>> getBorrowTenderResultSuccess(@RequestHeader(value = "userId") Integer userId,
                                                                       @RequestParam String logOrdId,
                                                                       @RequestParam Integer couponGrantId,
                                                                       @RequestParam String borrowNid,
                                                                       @RequestParam String isPrincipal,
                                                                       @RequestParam String account) {
        logger.info("web端散标出借获取投标成功结果，logOrdId{}  couponGrantId {}  borrowNid {}   isPrincipal {}   account", logOrdId,couponGrantId,borrowNid,isPrincipal,account);
        return borrowTenderService.getBorrowTenderResultSuccess(userId, logOrdId, borrowNid, couponGrantId,isPrincipal,account);
    }

    @ApiOperation(value = "web端获取出借信息", notes = "web端获取出借信息")
    @PostMapping(value = "/investInfo", produces = "application/json; charset=utf-8")
    public WebResult<TenderInfoResult> getInvestInfo(@RequestHeader(value = "userId", required = false) Integer userId,
                                                     @RequestBody @Valid TenderRequest tender, HttpServletRequest request) {
        logger.info("web端获取出借信息 userId:{}  tender:{} ",userId,JSONObject.toJSONString(tender));
        tender.setUserId(userId);
        tender.setPlatform(String.valueOf(ClientConstants.WEB_CLIENT));
        return borrowTenderService.getInvestInfo(tender);
    }

}
