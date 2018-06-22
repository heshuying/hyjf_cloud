package com.hyjf.cs.user.controller.web.bindcard;

import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.RedisKey;
import com.hyjf.common.util.GetCilentIP;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.user.service.bindcard.BindCardService;
import com.hyjf.cs.user.vo.BindCardVO;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;

/**
 * 绑卡/解绑卡
 * @author hesy
 * @version WebBindCardPageController, v0.1 2018/6/21 14:26
 */
@Api(value = "web端用户解绑卡接口")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/web/card")
public class WebBindCardPageController {
    private static final Logger logger = LoggerFactory.getLogger(WebBindCardPageController.class);

    @Autowired
    BindCardService bindCardService;

    /**
     * 绑卡
     * @param token
     * @param request
     * @return
     */
    @PostMapping(value = "/bindCardPage", produces = "application/json; charset=utf-8")
    public WebResult<Object> bindCardPage(@RequestHeader(value = "token", required = true) String token, HttpServletRequest request) {
        WebResult<Object> result = new WebResult<Object>();

        WebViewUserVO user = RedisUtils.getObj(RedisKey.USER_TOKEN_REDIS+token, WebViewUserVO.class);
        String userIp = GetCilentIP.getIpAddr(request);
        // 条件校验
        bindCardService.checkParamBindCardPage(user);

        // 请求银行接口
        try {
            Map<String,Object> data = bindCardService.callBankBindCardPage(user, userIp);
            result.setData(data);
        } catch (Exception e) {
            result.setStatus(WebResult.ERROR);
            result.setStatusDesc(WebResult.ERROR_DESC);
            logger.error("请求银行接口异常", e);
        }

        return result;
    }

    /**
     * 绑卡异步回调
     * @param token
     * @param bean
     * @param request
     * @return
     */
    @RequestMapping(value = "/bgReturn", produces = "application/json; charset=utf-8")
    public BankCallResult bindCardBgReturn(@RequestHeader(value = "token", required = true) String token, @RequestBody  BankCallBean bean, HttpServletRequest request) {
        WebViewUserVO user = RedisUtils.getObj(RedisKey.USER_TOKEN_REDIS+token, WebViewUserVO.class);

        BankCallResult result = new BankCallResult();
        String phone = request.getParameter("phone");
        logger.info("页面绑卡异步回调start");
        bean.setMobile(phone);
        bean.convert();
        int userId = Integer.parseInt(bean.getLogUserId());

        // 绑卡后处理
        try {
            boolean checkTender = RedisUtils.tranactionSet("bindCard" + bean.getLogOrderId(), 600);
            if(checkTender){
                // 保存银行卡信息
                bindCardService.updateAfterBindCard(bean);
            }
        } catch (Exception e) {
            result.setStatus(false);
            logger.error("绑卡异步回调处理异常", e);
        }
        logger.info("页面绑卡成功,用户ID:[" + userId + ",用户电子账户号:[" + bean.getAccountId() + "]");
        result.setStatus(true);
        return result;
    }


}
