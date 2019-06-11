/*
 * @Copyright: 2005-2018 so_what. All rights reserved.
 */
package com.hyjf.cs.trade.controller.app.tender.invest;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.trade.TenderRequest;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.CustomUtil;
import com.hyjf.cs.common.annotation.RequestLimit;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.trade.bean.app.AppInvestInfoResultVO;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.service.invest.BorrowTenderService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * app端散标出借
 */
@Api(value = "app端-散标出借",tags = "app端-散标出借")
@RestController
@RequestMapping("/hyjf-app/user/invest")
public class AppBorrowTenderController extends BaseTradeController {
    private static final Logger logger = LoggerFactory.getLogger(AppBorrowTenderController.class);

    @Autowired
    private BorrowTenderService borrowTenderService;

    @ApiOperation(value = "APP端散标出借", notes = "APP端散标出借")
    @PostMapping(value = "/tender", produces = "application/json; charset=utf-8")
    @RequestLimit(seconds=3)
    public WebResult<Map<String,Object>> borrowTender(@RequestHeader(value = "userId") Integer userId,TenderRequest tender, HttpServletRequest request) {
        logger.info("APP端请求出借接口--------------标的编号:{}，请求端：{}",tender.getBorrowNid(), tender.getPlatform());
        String ip = CustomUtil.getIpAddr(request);
        tender.setIp(ip);
        tender.setUserId(userId);
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

    /**
     * APP端散标异步处理
     * @param bean
     * @param couponGrantId
     * @return
     */
    @ApiIgnore
    @PostMapping("/bgReturn")
    @ResponseBody
    public BankCallResult borrowTenderBgReturn(BankCallBean bean , @RequestParam("couponGrantId") String couponGrantId) {
        logger.info("APP端散标出借异步处理start,userId:{}", bean.getLogUserId());
        BankCallResult result ;
        try{
            result = borrowTenderService.borrowTenderBgReturn(bean,couponGrantId);
        }catch (CheckException e){
            throw e;
        }finally {
            RedisUtils.del(RedisConstants.BORROW_TENDER_REPEAT + bean.getLogUserId());
        }
        return result;
    }

    @ApiOperation(value = "APP端散标出借获取出借结果", notes = "APP端散标出借获取出借结果")
    @PostMapping(value = "/getBorrowTenderResult", produces = "application/json; charset=utf-8")
    public WebResult<Map<String,Object>> getBorrowTenderResult(@RequestHeader(value = "userId") Integer userId,
                                                               @RequestParam String logOrdId,
                                                               @RequestParam String borrowNid,
                                                               HttpServletRequest request) {
        logger.info("APP端请求获取出借结果接口，logOrdId{}",logOrdId);
        return  borrowTenderService.getBorrowTenderResult(userId,logOrdId,borrowNid);
    }

    @ApiOperation(value = "APP端散标出借获取投标成功结果", notes = "APP端散标出借获取投标成功结果")
    @PostMapping(value = "/getBorrowTenderResultSuccess", produces = "application/json; charset=utf-8")
    public WebResult<Map<String, Object>> getBorrowTenderResultSuccess(@RequestHeader(value = "userId") Integer userId,
                                                                       @RequestParam String logOrdId,
                                                                       @RequestParam Integer couponGrantId,
                                                                       @RequestParam String borrowNid,
                                                                       @RequestParam String isPrincipal,
                                                                       @RequestParam String account) {
        logger.info("APP端散标出借获取投标成功结果，logOrdId{}", logOrdId);
        WebResult<Map<String, Object>> result = borrowTenderService.getBorrowTenderResultSuccess(userId, logOrdId, borrowNid, couponGrantId,isPrincipal,account);
        Map<String, Object> resultMap = result.getData();
        if(resultMap!=null&&resultMap.containsKey("appIncome")){
            // 如果是代金券 并且是app
            resultMap.remove("income");
            resultMap.put("income",resultMap.get("appIncome"));
            result.setData(resultMap);
        }
        return result;
    }

    @ApiOperation(value = "APP端获取出借信息", notes = "APP端获取出借信息")
    @PostMapping(value = "/getInvestInfo", produces = "application/json; charset=utf-8")
    public AppInvestInfoResultVO getInvestInfo(@RequestHeader(value = "userId") Integer userId, TenderRequest tender, HttpServletRequest request) {
        logger.info("APP端获取出借信息,请求参数：",JSONObject.toJSONString(tender));
        tender.setUserId(userId);
        if(tender.getCouponId()!=null&&!"".equals(tender.getCouponId())){
            tender.setCouponGrantId(Integer.parseInt(tender.getCouponId()));
        }
        // 前端要求改成bean，不要封装
        AppInvestInfoResultVO result = borrowTenderService.getInvestInfoApp(tender);
        // 校验风险测评
        Map<String,Object>  resultEval = borrowTenderService.checkEvalApp(tender);
        result.setRevalJudge((boolean) resultEval.get("revalJudge"));
        result.setProjectRevalJudge((boolean) resultEval.get("projectRevalJudge"));
        result.setRevalPrincipalJudge((boolean) resultEval.get("revalPrincipalJudge"));
        // 根据前端王琪要求当revalPrincipalJudge为true时，设置revalJudge为true
        if((boolean) resultEval.get("revalPrincipalJudge")){
            result.setRevalJudge(true);
        }
        // 以下参数为测评返回变量信息
        result.setEvalType((String) resultEval.get("evalType"));
        result.setRevaluationMoney((String) resultEval.get("revaluationMoney"));
        result.setRiskLevelDesc((String) resultEval.get("riskLevelDesc"));
        result.setProjectRiskLevelDesc((String) resultEval.get("projectRiskLevelDesc"));
        return result;
    }

    @ApiOperation(value = "APP端获取出借URL", notes = "APP端获取出借URL")
    @PostMapping(value = "/getTenderUrl", produces = "application/json; charset=utf-8")
    public JSONObject getTenderUrl(@RequestHeader(value = "userId") Integer userId, TenderRequest tender, HttpServletRequest request) {
        logger.info("APP端获取出借URL,请求参数：",JSONObject.toJSONString(tender));
        tender.setUserId(userId);
        JSONObject result = new JSONObject();
        try{
            //getTenderUrl 用于区分是否在 getAppTenderUrl 方法中判断用户测评
            String url = borrowTenderService.getAppTenderUrl(tender,"getTenderUrl");
            result.put("tenderUrl", url);
            result.put(CustomConstants.APP_STATUS, CustomConstants.APP_STATUS_SUCCESS);
            result.put(CustomConstants.APP_STATUS_DESC, CustomConstants.APP_STATUS_DESC_SUCCESS);
            result.put(CustomConstants.APP_REQUEST,"/hyjf-app/user/invest/getTenderUrl");
        }catch (CheckException e){
            logger.error(e.getMessage());
            result.put(CustomConstants.APP_STATUS, CustomConstants.APP_STATUS_FAIL);
            result.put(CustomConstants.APP_STATUS_DESC, e.getMessage());
            result.put(CustomConstants.APP_REQUEST,"/hyjf-app/user/invest/getTenderUrl");
        }
        return result;
    }
}
