package com.hyjf.cs.user.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.CustomUtil;
import com.hyjf.cs.user.beans.OpenAccountPageBean;
import com.hyjf.cs.user.constants.OpenAccountError;
import com.hyjf.cs.user.constants.RegisterError;
import com.hyjf.cs.user.result.ApiResult;
import com.hyjf.cs.user.result.AppResult;
import com.hyjf.cs.user.service.BankOpenService;
import com.hyjf.cs.user.service.UserService;
import com.hyjf.cs.user.vo.BankOpenVO;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

/**
 * @author sunss
 */
@Api(value = "微信端用户开户")
@Controller
@RequestMapping("/wechat/user/open")
public class WeChatBankOpenController {
    private static final Logger logger = LoggerFactory.getLogger(WeChatBankOpenController.class);

    @Autowired
    private BankOpenService bankOpenService;

    @Autowired
    private UserService userService;

    /**
     * 获取开户信息
     *
     * @return
     * @Author: sunss
     */
    @ApiOperation(value = "微信端获取开户信息", notes = "微信端获取开户信息")
    @PostMapping(value = "/userInfo", produces = "application/json; charset=utf-8")
    public AppResult<String> userInfo(@RequestHeader(value = "token", required = true) String token, HttpServletRequest request) {
        logger.info("openAccount userInfo start, token is :{}", token);
        AppResult<String> result = new AppResult<String>();
        UserVO userVO = userService.getUsersByToken(token);
        if (userVO != null) {
            logger.info("openAccount userInfo, success, userId is :{}", userVO.getUserId());
            String mobile = userVO.getMobile();
            if (StringUtils.isEmpty(mobile)) {
                mobile = "";
            }
            result.setResult(mobile);
        } else {
            logger.error("openAccount userInfo failed...");
            result.setStatus(ApiResult.STATUS_FAIL);
            result.setStatusDesc(RegisterError.REGISTER_ERROR.getMessage());
        }
        return result;
    }

    @ApiOperation(value = "微信端用户开户", notes = "微信端用户开户")
    @RequestMapping(value = "/openBankAccount")
    public ModelAndView openBankAccount(@RequestHeader(value = "token", required = true) String token, @RequestBody @Valid BankOpenVO bankOpenVO, HttpServletRequest request) {
        logger.info("wechat openBankAccount start, bankOpenVO is :{}", JSONObject.toJSONString(bankOpenVO));
        // 返回给前端的值
        AppResult<String> appResult = new AppResult<String>();
        ModelAndView reuslt = new ModelAndView("bankopen/error");
        // 获取登录信息
        UserVO user = userService.getUsersByToken(token);
        // 检查参数
        appResult = bankOpenService.checkRequestParam(user, bankOpenVO);
        if (!OpenAccountError.SUCCESS.getErrCode().equals(appResult.getStatus())) {
            // 返回失败
            reuslt.addObject("callback", appResult);
            return reuslt;
        }
        // 拼装参数 调用江西银行
        // 同步调用路径
        OpenAccountPageBean openBean = new OpenAccountPageBean();

        try {
            PropertyUtils.copyProperties(openBean, bankOpenVO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        openBean.setChannel(BankCallConstant.CHANNEL_WEI);
        openBean.setUserId(user.getUserId());
        openBean.setIp(CustomUtil.getIpAddr(request));
        openBean.setCoinstName("汇盈金服");
        /**1：出借角色2：借款角色3：代偿角色*/
        openBean.setIdentity("1");
        // 组装调用江西银行的MV
        reuslt = bankOpenService.getOpenAccountMV(openBean);
        //保存开户日志  银行卡号不必传了
        int uflag = this.bankOpenService.updateUserAccountLog(user.getUserId(), user.getUsername(), openBean.getMobile(), openBean.getOrderId(), CustomConstants.CLIENT_WECHAT, openBean.getTrueName(), openBean.getIdNo(), "");
        if (uflag == 0) {
            logger.info("保存开户日志失败,手机号:[" + openBean.getMobile() + "],用户ID:[" + user.getUserId() + "]");
            appResult.setStatus(OpenAccountError.SYSTEM_ERROR.getErrCode());
            appResult.setStatusDesc(OpenAccountError.SYSTEM_ERROR.getMessage());
            reuslt.addObject("callback", appResult);
            return reuslt;
        }
        logger.info("开户end");
        return reuslt;
    }

    /**
     * 微信开户同步跳转地址
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "微信端用户同步回调", notes = "微信端用户开户")
    @RequestMapping(value = "/return")
    public Map<String, String> returnPage(HttpServletRequest request, @RequestHeader(value = "token", required = true) String token) {
        String isSuccess = request.getParameter("isSuccess");
        if (StringUtils.isEmpty(token)) {
            token = request.getParameter("token");
        }
        logger.info("微信端开户同步请求,token:{},isSuccess:{}", token, isSuccess);
        Map<String, String> result = bankOpenService.openAccountReturn(token, isSuccess);
        logger.info("微信端开户同步请求返回值：", JSONObject.toJSONString(result));
        return result;
    }

    /**
     * 页面开户异步处理
     *
     * @param bean
     * @return
     */
    @ApiOperation(value = "页面开户异步处理", notes = "页面开户异步处理")
    @RequestMapping("/bgReturn")
    public BankCallResult openAccountBgReturn(BankCallBean bean, @RequestParam("phone") String mobile) {
        logger.info("开户异步处理start,userId:{}", bean.getLogUserId());
        bean.setMobile(mobile);
        BankCallResult result = bankOpenService.openAccountBgReturn(bean);
        return result;
    }

}