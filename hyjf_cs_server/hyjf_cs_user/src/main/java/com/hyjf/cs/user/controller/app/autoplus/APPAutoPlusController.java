/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.app.autoplus;

import com.hyjf.am.vo.user.AuthorizedVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.common.util.ClientConstants;
import com.hyjf.cs.common.bean.result.ApiResult;
import com.hyjf.cs.common.bean.result.AppResult;
import com.hyjf.cs.user.bean.BaseMapBean;
import com.hyjf.cs.user.constants.AuthorizedError;
import com.hyjf.cs.user.constants.OpenAccountError;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.autoplus.AutoPlusService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
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
import javax.validation.Valid;
import java.util.Map;

/**
 * @author zhangqingqing
 * @version AutoPlusController, v0.1 2018/6/11 14:20
 */
@Api(value = "app端用户授权自动投资自动债转接口")
@RestController
@RequestMapping("/app/bank/user/autoplus")
public class APPAutoPlusController extends BaseUserController {

    private static final Logger logger = LoggerFactory.getLogger(APPAutoPlusController.class);
    @Autowired
    AutoPlusService autoPlusService;

    /**
     * 用户授权自动债转
     * @param token
     * @param
     * @param
     * @return
     */
    @ApiOperation(value = "用户授权自动债转", notes = "用户授权自动债转")
    @PostMapping("/userAuthCredit")
    public ModelAndView userAuthCredit(@RequestHeader(value = "token", required = true) String token, @RequestBody AuthorizedVO authorizedVO) {
        String lastSrvAuthCode = authorizedVO.getLastSrvAuthCode();
        String smsCode = authorizedVO.getSmsCode();
        // 验证请求参数
        if (token == null) {
            throw new ReturnMessageException(OpenAccountError.USER_NOT_LOGIN_ERROR);
        }
        UserVO user = this.autoPlusService.getUsers(token);
        //检查用户信息
        autoPlusService.checkUserMessage(user,lastSrvAuthCode,smsCode);
        BankCallBean bean =  autoPlusService.userCreditAuthInves(user, ClientConstants.APP_CLIENT, BankCallConstant.QUERY_TYPE_2,ClientConstants.CHANNEL_APP,lastSrvAuthCode,smsCode);
        ModelAndView modelAndView = new ModelAndView();
        try {
            modelAndView = BankCallUtils.callApi(bean);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ReturnMessageException(AuthorizedError.CALL_BANK_ERROR);
        }
        return modelAndView;
    }

    /**
     * 用户授权自动债转同步回调
     * @param token
     * @param request
     * @param bean
     * @return
     */
    @ApiOperation(value = "用户授权自动债转同步回调", notes = "用户授权自动债转同步回调")
    @PostMapping(value = "/userAuthCreditReturn", produces = "application/json; charset=utf-8")
    public AppResult<Object> userAuthCreditReturn(@RequestHeader(value = "token", required = true) String token, HttpServletRequest request, @RequestBody @Valid BankCallBean bean) {
        AppResult<Object> apiResult = new AppResult<>();
        String sign = request.getHeader("sign");
        String isSuccess = request.getParameter("isSuccess");
        Map<String,BaseMapBean> result = autoPlusService.userAuthCreditReturn(token,bean,ClientConstants.CREDIT_AUTO_TYPE,sign,isSuccess);
        apiResult.setData(result);
        return apiResult;
    }

    /**
     * 用户授权自动债转异步回调
     * @param bean
     * @return
     */
    @ApiOperation(value = "用户授权自动债转异步回调", notes = "用户授权自动债转异步回调")
    @PostMapping("/userAuthCreditBgreturn")
    public String userCreditAuthInvesBgreturn(BankCallBean bean) {
        String result = autoPlusService.userBgreturn(bean);
        return result;
    }

    /**
     * 用户授权自动投资
     * @param token
     * @param
     * @param
     * @return
     */
    @ApiOperation(value = "用户授权自动投资", notes = "用户授权自动投资")
    @PostMapping("/userAuthInves")
    public ModelAndView userAuthInves(@RequestHeader(value = "token", required = true) String token, @RequestBody AuthorizedVO authorizedVO) {
        String lastSrvAuthCode = authorizedVO.getLastSrvAuthCode();
        String smsCode = authorizedVO.getSmsCode();
        // 验证请求参数
        if (token == null) {
            throw new ReturnMessageException(OpenAccountError.USER_NOT_LOGIN_ERROR);
        }
        UserVO user = this.autoPlusService.getUsers(token);
        //检查用户信息
        autoPlusService.checkUserMessage(user,lastSrvAuthCode,smsCode);
        BankCallBean bean = autoPlusService.userCreditAuthInves(user, ClientConstants.APP_CLIENT, BankCallConstant.QUERY_TYPE_1,ClientConstants.CHANNEL_APP,lastSrvAuthCode,smsCode);
        ModelAndView modelAndView = new ModelAndView();
        try {
            modelAndView = BankCallUtils.callApi(bean);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ReturnMessageException(AuthorizedError.CALL_BANK_ERROR);
        }
        return modelAndView;
    }

    /**
     * @Author: zhangqingqing
     * @Desc :用户授权自动投资同步回调
     * @Param: * @param token
     * @param request
     * @param bean
     * @Date: 16:45 2018/5/30
     * @Return: ApiResult
     */
    @ApiOperation(value = "用户授权自动投资同步回调", notes = "用户授权自动投资同步回调")
    @PostMapping(value = "/userAuthInvesReturn", produces = "application/json; charset=utf-8")
    public AppResult<Object> userAuthInvesReturn(@RequestHeader(value = "token", required = true) String token, HttpServletRequest request, @RequestBody @Valid BankCallBean bean) {
        AppResult<Object> apiResult = new AppResult<>();
        String sign = request.getHeader("sign");
        String isSuccess = request.getParameter("isSuccess");
        Map<String, BaseMapBean> result = autoPlusService.userAuthCreditReturn(token, bean, ClientConstants.INVES_AUTO_TYPE, sign, isSuccess);
        apiResult.setData(result);
        return apiResult;
    }

    /**
     * @Author: zhangqingqing
     * @Desc :用户授权自动投资异步回调
     * @Param: * @param bean
     * @Date: 16:46 2018/5/30
     * @Return: String
     */
    @ApiOperation(value = "用户授权自动投资异步回调", notes = "用户授权自动投资异步回调")
    @ResponseBody
    @PostMapping(value = "/userAuthInvesBgreturn", produces = "application/json; charset=utf-8")
    public String userAuthInvesBgreturn(@RequestBody @Valid BankCallBean bean) {

        String result = autoPlusService.userBgreturn(bean);
        return result;
    }

}
