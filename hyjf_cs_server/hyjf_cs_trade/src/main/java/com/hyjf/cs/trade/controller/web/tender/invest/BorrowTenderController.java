/*
 * @Copyright: 2005-2018 so_what. All rights reserved.
 */
package com.hyjf.cs.trade.controller.web.tender.invest;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.trade.TenderRequest;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.util.ClientConstants;
import com.hyjf.common.util.CustomUtil;
import com.hyjf.cs.common.annotation.RequestLimit;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.trade.bean.TenderInfoResult;
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
import javax.validation.Valid;
import java.util.Map;

/**
 * @Description Web端散标投资
 * @Author sss
 * @Version v0.1
 * @Date 2018/6/19 9:32
 */
@Api(tags = "Web端-散标投资")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/hyjf-web/tender/borrow")
public class BorrowTenderController extends BaseTradeController {
    private static final Logger logger = LoggerFactory.getLogger(BorrowTenderController.class);

    @Autowired
    private BorrowTenderService borrowTenderService;

    @ApiOperation(value = "散标投资", notes = "web端散标投资")
    @PostMapping(value = "/tender", produces = "application/json; charset=utf-8")
    @RequestLimit(seconds=3)
    public WebResult<Map<String,Object>> borrowTender(@RequestHeader(value = "userId", required = false) Integer userId,
                                                      @RequestBody @Valid TenderRequest tender, HttpServletRequest request) {
        logger.info("web端请求投资接口");
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
            RedisUtils.del(RedisConstants.BORROW_TENDER_REPEAT + tender.getUser().getUserId());
        }
        return result;
    }

    @ApiOperation(value = "散标投资校验", notes = "web端散标投资校验")
    @PostMapping(value = "/investCheck", produces = "application/json; charset=utf-8")
    public WebResult<Map<String,Object>> investCheck(@RequestHeader(value = "userId", required = false) Integer userId,
                                                      @RequestBody @Valid TenderRequest tender, HttpServletRequest request) {
        logger.info("web端请求投资校验接口");
        String ip = CustomUtil.getIpAddr(request);
        tender.setIp(ip);
        tender.setUserId(userId);
        tender.setPlatform(String.valueOf(ClientConstants.WEB_CLIENT));
        WebResult<Map<String,Object>>  result =  borrowTenderService.borrowTenderCheck(tender,null,null,null,null);
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
        logger.info("web端散标投资异步处理start,userId:{}", bean.getLogUserId());
        BankCallResult result ;
        try{
            if (platform != null && platform.intValue() >= 0) {
                bean.setLogClient(platform);
            }
            result = borrowTenderService.borrowTenderBgReturn(bean,couponGrantId);
        }catch (CheckException e){
            throw e;
        }finally {
            RedisUtils.del(RedisConstants.BORROW_TENDER_REPEAT + bean.getLogUserId());
        }
        return result;
    }

    @ApiOperation(value = "web端散标投资获取投资结果", notes = "web端散标投资获取投资结果")
    @PostMapping(value = "/getBorrowTenderResult", produces = "application/json; charset=utf-8")
    public WebResult<Map<String,Object>> getBorrowTenderResult(@RequestHeader(value = "userId",required = false) Integer userId,
                                                               @RequestParam String logOrdId,
                                                               @RequestParam String borrowNid,
                                                               HttpServletRequest request) {
        logger.info("web端请求获取投资结果接口，logOrdId:{}  borrowNid:{} ",logOrdId , borrowNid);
        return  borrowTenderService.getBorrowTenderResult(userId,logOrdId,borrowNid);
    }

    @ApiOperation(value = "web端散标投资获取投资成功结果", notes = "web端散标投资获取投资成功结果")
    @PostMapping(value = "/getBorrowTenderResultSuccess", produces = "application/json; charset=utf-8")
    public WebResult<Map<String, Object>> getBorrowTenderResultSuccess(@RequestHeader(value = "userId") Integer userId,
                                                                       @RequestParam String logOrdId,
                                                                       @RequestParam Integer couponGrantId,
                                                                       @RequestParam String borrowNid) {
        logger.info("web端散标投资获取投资成功结果，logOrdId{}  couponGrantId {}  borrowNid {}   borrowNid2 {}", logOrdId,couponGrantId,borrowNid,borrowNid);
        return borrowTenderService.getBorrowTenderResultSuccess(userId, logOrdId, borrowNid, couponGrantId);
    }

    @ApiOperation(value = "web端获取投资信息", notes = "web端获取投资信息")
    @PostMapping(value = "/investInfo", produces = "application/json; charset=utf-8")
    public WebResult<TenderInfoResult> getInvestInfo(@RequestHeader(value = "userId", required = false) Integer userId,
                                                     @RequestBody @Valid TenderRequest tender, HttpServletRequest request) {
        logger.info("web端获取投资信息 userId:{}  tender:{} ",userId,JSONObject.toJSONString(tender));
        tender.setUserId(userId);
        tender.setPlatform(String.valueOf(ClientConstants.WEB_CLIENT));
        return borrowTenderService.getInvestInfo(tender);
    }

}
