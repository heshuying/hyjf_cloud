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
import com.hyjf.cs.trade.service.invest.BorrowCreditTenderService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "web端散标债转投资")
@RestController
@RequestMapping("/hyjf-web/tender/credit")
public class BorrowCreditTenderController extends BaseTradeController {

    @Autowired
    private BorrowCreditTenderService borrowTenderService;

    @ApiOperation(value = "web端散标债转投资", notes = "web端散标债转投资")
    @PostMapping(value = "/tender", produces = "application/json; charset=utf-8")
    public WebResult<Map<String,Object>> borrowTender(@RequestHeader(value = "userId") int userId, @RequestBody @Valid TenderRequest tender, HttpServletRequest request) {
        logger.info("web端请求债转投资接口");
        String ip = CustomUtil.getIpAddr(request);
        tender.setIp(ip);
        tender.setUser(this.borrowTenderService.getUserFromCache(userId));
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
    @RequestMapping("/bgReturn")
    @ResponseBody
    public BankCallResult borrowCreditTenderBgReturn(BankCallBean bean ) {
        logger.info("web端债转投资异步处理start,userId:{},返回码:{}", bean.getLogUserId(),bean.getRetCode());
        BankCallResult result = borrowTenderService.borrowCreditTenderBgReturn(bean);
        return result;
    }

    @ApiOperation(value = "web端债转投资获取投资结果  失败", notes = "web端债转投资获取投资结果  失败")
    @PostMapping(value = "/getResult", produces = "application/json; charset=utf-8")
    public WebResult<Map<String,Object>> getBorrowTenderResult(@RequestHeader(value = "userId") int userId,
                                                               @RequestParam String logOrdId) {
        logger.info("web端债转投资获取投资结果，logOrdId{}",logOrdId);
        WebViewUserVO userVO = borrowTenderService.getUserFromCache(userId);
        return  borrowTenderService.getFaileResult(userVO,logOrdId);
    }

    @ApiOperation(value = "web端债转投资获取投资结果  成功", notes = "web端债转投资获取投资结果  成功")
    @PostMapping(value = "/getSuccessResult", produces = "application/json; charset=utf-8")
    public WebResult<Map<String,Object>> getSuccessResult(@RequestHeader(value = "userId") int userId,
                                                               @RequestParam String logOrdId) {
        logger.info("web端债转投资获取投资结果，logOrdId{}",logOrdId);
        WebViewUserVO userVO = borrowTenderService.getUserFromCache(userId);
        return  borrowTenderService.getSuccessResult(userVO.getUserId(),logOrdId);
    }

}
