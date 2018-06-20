package com.hyjf.cs.trade.controller.web;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.CommonConstant;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.common.util.CustomUtil;

import com.hyjf.cs.trade.constants.BankWithdrawError;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.service.BankWithdrawService;
import com.hyjf.cs.trade.service.WebBorrowService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author pangchengchao
 * @version BankWithdrawController, v0.1 2018/6/12 18:32
 *//*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
@Api(value = "web端用户提现接口")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/web/withdraw")
public class BankWithdrawController extends BaseTradeController {

    private static final Logger logger = LoggerFactory.getLogger(BankWithdrawController.class);
    @Autowired
    private BankWithdrawService webBorrowService;


    /**
     * 用户银行提现
     * @Description
     * @Author pangchengchao
     * @Version v0.1
     * @Date  用户提现调用银行页面
     */
    @ApiOperation(value = "用户银行提现", notes = "用户提现")
    @PostMapping("/userBankWithdraw")
    public ModelAndView userBankWithdraw(@RequestHeader(value = "token", required = true) String token, HttpServletRequest request) {
        logger.info("web端提现接口, token is :{}", JSONObject.toJSONString(token));
        String transAmt = request.getParameter("withdrawmoney");// 交易金额
        String cardNo = request.getParameter("widCard");// 提现银行卡号
        String payAllianceCode = request.getParameter("payAllianceCode");// 银联行号

        WebViewUserVO user = RedisUtils.getObj(token, WebViewUserVO.class);
        UserVO userVO=webBorrowService.getUserByUserId(user.getUserId());
        logger.info("user is :{}", JSONObject.toJSONString(user));
        String ip=CustomUtil.getIpAddr(request);
        BankCallBean bean = webBorrowService.getUserBankWithdrawView(userVO,transAmt,cardNo,payAllianceCode,CommonConstant.CLIENT_PC,BankCallConstant.CHANNEL_PC,ip);
        ModelAndView modelAndView = new ModelAndView();
        try {
            modelAndView = BankCallUtils.callApi(bean);
        } catch (Exception e) {
            logger.info("web端提现失败");
            e.printStackTrace();
            throw new ReturnMessageException(BankWithdrawError.CALL_BANK_ERROR);
        }
        return modelAndView;
    }

    /**
     * 用户银行提现同步回调
     * @Description
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @ApiOperation(value = "用户银行提现同步回调", notes = "用户银行提现同步回调")
    @RequestMapping("/userBankWithdrawReturn")
    public Map<String, String> userBankWithdrawReturn(@RequestHeader(value = "token", required = true) String token, HttpServletRequest request,
                                                      @ModelAttribute BankCallBean bean) {
        logger.info("[web用户银行提现同步回调开始]");
        logger.info("web端提现银行返回参数, bean is :{}", JSONObject.toJSONString(bean));
        String isSuccess = request.getParameter("isSuccess");
        String withdrawmoney = request.getParameter("withdrawmoney");
        String wifee = request.getParameter("wifee");
        Map<String, String> result = webBorrowService.userBankWithdrawReturn(bean, isSuccess,wifee,withdrawmoney);
        logger.info("[web用户银行提现同步回调结束]");
        return result;
    }

    /**
     * 用户银行提现异步回调
     * @Description
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @ApiOperation(value = "用户银行提现异步回调", notes = "用户银行提现异步回调")
    @RequestMapping("/userBankWithdrawBgreturn")
    public String userBankWithdrawBgreturn(HttpServletRequest request,BankCallBean bean) {
        logger.info("[web用户银行提现异步回调开始]");
        logger.info("web端提现银行返回参数, bean is :{}", JSONObject.toJSONString(bean));
        BankCallResult result = new BankCallResult();
        bean.convert();
        Integer userId = Integer.parseInt(bean.getLogUserId()); // 用户ID
        // 插值用参数
        Map<String, String> params = new HashMap<String, String>();
        params.put("userId", String.valueOf(userId));
        params.put("ip", CustomUtil.getIpAddr(request));
        // 执行提现后处理
        this.webBorrowService.handlerAfterCash(bean, params);
        logger.info( "成功");
        result.setStatus(true);
        logger.info("[web用户银行提现异步回调结束]");
        return JSONObject.toJSONString(result, true);
    }
}
