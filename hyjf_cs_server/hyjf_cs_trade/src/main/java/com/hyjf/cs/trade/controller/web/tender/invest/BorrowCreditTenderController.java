/*
 * @Copyright: 2005-2018 so_what. All rights reserved.
 */
package com.hyjf.cs.trade.controller.web.tender.invest;

import com.hyjf.am.resquest.trade.TenderRequest;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.util.ClientConstants;
import com.hyjf.common.util.CustomUtil;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.service.BorrowCreditTenderService;
import com.hyjf.cs.trade.service.BorrowTenderService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

/**
 * @Description web端散标债转投资
 * @Author sunss
 * @Date 2018/7/3 14:02
 */
@Api(value = "web端散标债转投资")
@RestController
@RequestMapping("/web/tender/credit")
public class BorrowCreditTenderController extends BaseTradeController {
    private static final Logger logger = LoggerFactory.getLogger(BorrowCreditTenderController.class);

    @Autowired
    private BorrowCreditTenderService borrowTenderService;

    @ApiOperation(value = "web端散标债转投资", notes = "web端散标债转投资")
    @PostMapping(value = "/tender", produces = "application/json; charset=utf-8")
    public WebResult<Map<String,Object>> borrowTender(@RequestHeader(value = "token", required = true) String token, @RequestBody @Valid TenderRequest tender, HttpServletRequest request) {
        logger.info("web端请求债转投资接口");
        String ip = CustomUtil.getIpAddr(request);
        tender.setIp(ip);
        tender.setToken(token);
        tender.setPlatform(String.valueOf(ClientConstants.WEB_CLIENT));

        WebResult<Map<String,Object>> result = null;
        try{
            result =  borrowTenderService.borrowCreditTender(tender);
        }catch (CheckException e){
            throw e;
        }
        return result;
    }

    @ApiOperation(value = "web端债转投资异步处理", notes = "web端债转投资异步处理")
    @RequestMapping("/bgReturn")
    @ResponseBody
    public BankCallResult borrowCreditTenderBgReturn(BankCallBean bean ) {
        logger.info("web端债转投资异步处理start,userId:{}", bean.getLogUserId());
        BankCallResult result = borrowTenderService.borrowCreditTenderBgReturn(bean);
        return result;
    }

    @ApiOperation(value = "web端债转投资获取投资结果  失败", notes = "web端债转投资获取投资结果  失败")
    @PostMapping(value = "/getResult", produces = "application/json; charset=utf-8")
    public WebResult<Map<String,Object>> getBorrowTenderResult(@RequestHeader(value = "token", required = true) String token,
                                                               @RequestParam String logOrdId) {
        logger.info("web端债转投资获取投资结果，logOrdId{}",logOrdId);
        WebViewUserVO userVO = borrowTenderService.getUsersByToken(token);
        return  borrowTenderService.getFaileResult(userVO,logOrdId);
    }

    @ApiOperation(value = "web端债转投资获取投资结果  成功", notes = "web端债转投资获取投资结果  成功")
    @PostMapping(value = "/getSuccessResult", produces = "application/json; charset=utf-8")
    public WebResult<Map<String,Object>> getSuccessResult(@RequestHeader(value = "token", required = true) String token,
                                                               @RequestParam String logOrdId) {
        logger.info("web端债转投资获取投资结果，logOrdId{}",logOrdId);
        WebViewUserVO userVO = borrowTenderService.getUsersByToken(token);
        return  borrowTenderService.getSuccessResult(userVO.getUserId(),logOrdId);
    }

}
