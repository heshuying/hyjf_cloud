package com.hyjf.cs.user.controller.web.bindcard;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.RedisKey;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.util.GetCilentIP;
import com.hyjf.cs.common.bean.result.ApiResult;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.user.service.bindcard.BindCardService;
import com.hyjf.cs.user.vo.BindCardVO;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import com.hyjf.pay.lib.bank.util.BankCallStatusConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
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
@Api(value = "web端用户解绑卡接口",description = "web端用户解绑卡接口")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/hyjf-web/card")
public class WebBindCardPageController {
    private static final Logger logger = LoggerFactory.getLogger(WebBindCardPageController.class);

    @Autowired
    BindCardService bindCardService;

    /**
     * 绑卡接口
     * @param token
     * @param request
     * @return
     */
    @ApiOperation(value = "绑卡接口页面", notes = "绑卡接口页面")
    @ApiImplicitParam(name = "paraMap",value = "{urlstatus:string}", dataType = "Map")
    @PostMapping(value = "/bindCardPage", produces = "application/json; charset=utf-8")
    public WebResult<Object> bindCardPage(@RequestHeader(value = "token", required = true) String token, @RequestBody Map<String,String> param, HttpServletRequest request) {
        WebResult<Object> result = new WebResult<Object>();

        WebViewUserVO user = RedisUtils.getObj(RedisKey.USER_TOKEN_REDIS+token, WebViewUserVO.class);
        String userIp = GetCilentIP.getIpAddr(request);
        String urlstatus = param.get("urlstatus"); // 请求来源
        // 条件校验
        bindCardService.checkParamBindCardPage(user);

        // 请求银行接口
        try {
            Map<String,Object> data = bindCardService.callBankBindCardPage(user, userIp, urlstatus);
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
    @ApiOperation(value = "绑卡接口回调", notes = "绑卡接口回调")
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

    /**
     * 解绑卡接口
     * @param token
     * @param bindCardVO
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(value = "用户解绑卡", notes = "用户解绑卡")
    @PostMapping(value = "/unBindCard", produces = "application/json; charset=utf-8")
    public WebResult<Object> unBindCard(@RequestHeader(value = "token", required = true) String token, @RequestBody @Valid BindCardVO bindCardVO, HttpServletRequest request,
                                        HttpServletResponse response) {
        logger.info("解绑卡开始, bindCardVO :{}", JSONObject.toJSONString(bindCardVO));
        WebResult<Object> result = new WebResult<Object>();

        WebViewUserVO user = RedisUtils.getObj(RedisKey.USER_TOKEN_REDIS+token, WebViewUserVO.class);

        bindCardService.checkParamUnBindCard(bindCardVO, user.getUserId());

        // 请求银行绑卡接口
        BankCallBean bankBean = null;
        try {
            bankBean = bindCardService.callBankUnBindCard(bindCardVO, user.getUserId());
        } catch (Exception e) {
            result.setStatus(ApiResult.FAIL);
            result.setStatusDesc(MsgEnum.ERR_BANK_CALL.getMsg());
            logger.error("请求解绑卡接口发生异常", e);
        }

        if(bankBean == null || !(BankCallStatusConstant.RESPCODE_SUCCESS.equals(bankBean.getRetCode()))) {
            result.setStatus(ApiResult.FAIL);
            result.setStatusDesc(MsgEnum.ERR_BANK_CALL.getMsg());
            logger.error("请求解绑卡接口失败");
        }

        // 绑卡请求后业务处理
        try {
            bindCardService.updateAfterUnBindCard(bankBean);
        } catch (Exception e) {
            result.setStatus(ApiResult.FAIL);
            result.setStatusDesc(MsgEnum.ERR_CARD_SAVE.getMsg());
            logger.error("解绑卡后处理异常", e);
        }

        return result;
    }


}
