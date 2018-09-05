/*
 * @Copyright: 2005-2018 so_what. All rights reserved.
 */
package com.hyjf.cs.trade.controller.wechat.tender.invest;

import com.hyjf.am.resquest.trade.TenderRequest;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.util.ClientConstants;
import com.hyjf.common.util.CustomUtil;
import com.hyjf.cs.common.annotation.RequestLimit;
import com.hyjf.cs.common.bean.result.WeChatResult;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.trade.bean.TenderInfoResult;
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
import javax.validation.Valid;
import java.util.Map;

/**
 * wechat端-散标投资
 */
@Api(tags = "weChat端-散标投资")
@RestController
@RequestMapping("/hyjf-wechat/wx/bank/wechat/borrow")
public class WechatBorrowTenderController extends BaseTradeController {

    @Autowired
    private BorrowTenderService borrowTenderService;

    @ApiOperation(value = "散标投资", notes = "散标投资")
    @PostMapping(value = "/tender", produces = "application/json; charset=utf-8")
    @RequestLimit(seconds=3)
    public WeChatResult<Map<String,Object>> borrowTender(@RequestHeader(value = "userId") Integer userId, @RequestBody @Valid TenderRequest tender, HttpServletRequest request) {
        logger.info("wechat端-请求投资接口");
        String ip = CustomUtil.getIpAddr(request);
        tender.setIp(ip);
        tender.setPlatform(String.valueOf(ClientConstants.WECHAT_CLIENT));
        tender.setUserId(userId);
        WebResult<Map<String,Object>> result = null;
        WeChatResult weChatResult = new WeChatResult();
        try{
            result =  borrowTenderService.borrowTender(tender);
            weChatResult.setData(result.getData());
        }catch (CheckException e){
            throw e;
        }finally {
            RedisUtils.del(RedisConstants.BORROW_TENDER_REPEAT + tender.getUser().getUserId());
        }
        return weChatResult;
    }

    /**
     * wechat端-散标异步处理
     * @param bean
     * @param couponGrantId
     * @return
     */
    @ApiIgnore
    @PostMapping("/bgReturn")
    @ResponseBody
    public BankCallResult borrowTenderBgReturn(BankCallBean bean , @RequestParam("couponGrantId") String couponGrantId) {
        logger.info("wechat端-散标投资异步处理start,userId:{}", bean.getLogUserId());
        BankCallResult result ;
        try{
            bean.setLogClient(ClientConstants.WECHAT_CLIENT);
            result = borrowTenderService.borrowTenderBgReturn(bean,couponGrantId);
        }catch (CheckException e){
            throw e;
        }finally {
            RedisUtils.del(RedisConstants.BORROW_TENDER_REPEAT + bean.getLogUserId());
        }
        return result;
    }

    @ApiOperation(value = "散标投资获取投资结果", notes = "散标投资获取投资结果")
    @PostMapping(value = "/getBorrowTenderResult", produces = "application/json; charset=utf-8")
    public WeChatResult<Map<String,Object>> getBorrowTenderResult(@RequestHeader(value = "userId") Integer userId,
                                                               @RequestParam String logOrdId,
                                                               @RequestParam String borrowNid) {
        logger.info("wechat端-请求获取投资结果接口，logOrdId{}",logOrdId);
        WebResult<Map<String,Object>> result = borrowTenderService.getBorrowTenderResult(userId,logOrdId,borrowNid);
        WeChatResult weChatResult = new WeChatResult();
        weChatResult.setObject(result.getData());
        return  weChatResult;
    }

    @ApiOperation(value = "散标投资获取投资成功结果", notes = "散标投资获取投资成功结果")
    @PostMapping(value = "/getBorrowTenderResultSuccess", produces = "application/json; charset=utf-8")
    public WeChatResult<Map<String, Object>> getBorrowTenderResultSuccess(@RequestHeader(value = "userId") Integer userId,
                                                                       @RequestParam String logOrdId,
                                                                       @RequestParam Integer couponGrantId,
                                                                       @RequestParam String borrowNid) {
        logger.info("wechat端-散标投资获取投资成功结果，logOrdId{}", logOrdId);
        WebResult<Map<String,Object>> result = borrowTenderService.getBorrowTenderResultSuccess(userId, logOrdId, borrowNid, couponGrantId);
        WeChatResult weChatResult = new WeChatResult();
        weChatResult.setObject(result.getData());
        return  weChatResult;
    }

    @ApiOperation(value = "获取投资信息", notes = "获取投资信息")
    @GetMapping(value = "/getInvestInfo", produces = "application/json; charset=utf-8")
    public WeChatResult getInvestInfo(@RequestHeader(value = "userId") Integer userId, TenderRequest tender, HttpServletRequest request) {
        logger.info("wechat端-获取投资信息 userid:{}" , userId);
        tender.setUserId(userId);
        tender.setPlatform(String.valueOf(ClientConstants.WECHAT_CLIENT));
        WeChatResult result = new WeChatResult();
        result.setData(borrowTenderService.getInvestInfoWeChat(tender));
        return result;
    }

}
