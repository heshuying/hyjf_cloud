/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.wechat.autoplus;

import com.hyjf.am.vo.user.AuthorizedVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.common.util.ClientConstants;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.user.constants.OpenAccountError;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.autoplus.AutoPlusService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * @author zhangqingqing
 * @version AutoPlusController, v0.1 2018/6/11 14:37
 */

@Api(value = "weChat端用户授权自动投资债转接口")
@RestController
@RequestMapping("/wechat/user")
public class WeChatAutoPlusController extends BaseUserController {

    private static final Logger logger = LoggerFactory.getLogger(WeChatAutoPlusController.class);

    @Autowired
    AutoPlusService autoPlusService;

    /**
     * @Author: zhangqingqing
     * @Desc :用户自动债转授权
     * @Param: * @param token
     * @param
     * @Date: 16:36 2018/5/30
     * @Return: ModelAndView
     */
    @ApiOperation(value = "用户自动债转授权", notes = "用户自动债转授权")
    @PostMapping(value ="/userAuthCredit", produces = "application/json; charset=utf-8")
    public WebResult<Object> userAuthCredit(@RequestHeader(value = "token", required = true) String token,  @RequestBody AuthorizedVO authorizedVO){
        WebResult<Object> result = new WebResult<Object>();
        String lastSrvAuthCode = authorizedVO.getLastSrvAuthCode();
        String smsCode = authorizedVO.getSmsCode();
        // 验证请求参数
        if (token == null) {
            throw new ReturnMessageException(OpenAccountError.USER_NOT_LOGIN_ERROR);
        }
        UserVO user = this.autoPlusService.getUsers(token);
        //检查用户信息
        autoPlusService.checkUserMessage(user,lastSrvAuthCode,smsCode);
        Map<String,Object> map = autoPlusService.userCreditAuthInves(user, ClientConstants.WECHAT_CLIENT, BankCallConstant.QUERY_TYPE_2,ClientConstants.CHANNEL_WEI,lastSrvAuthCode,smsCode);
        result.setData(map);
        return result;
    }

    /**
     * @Author: zhangqingqing
     * @Desc :自动投资授权接口
     * @Param: * @param token
     * @param
     * @Date: 16:36 2018/5/30
     * @Return: ModelAndView
     */
    @ApiOperation(value = "自动投资授权接口", notes = "自动投资授权接口")
    @PostMapping("/userAuthInves")
    public WebResult<Object> userAuthInves(@RequestHeader(value = "token", required = true) String token, @RequestBody AuthorizedVO authorizedVO){
        WebResult<Object> result = new WebResult<Object>();
        String lastSrvAuthCode = authorizedVO.getLastSrvAuthCode();
        String smsCode = authorizedVO.getSmsCode();
        // 验证请求参数
        if (token == null) {
            throw new ReturnMessageException(OpenAccountError.USER_NOT_LOGIN_ERROR);
        }
        UserVO user = this.autoPlusService.getUsers(token);
        //检查用户信息
        autoPlusService.checkUserMessage(user,lastSrvAuthCode,smsCode);
        Map<String,Object> map  = autoPlusService.userCreditAuthInves(user, ClientConstants.WECHAT_CLIENT, BankCallConstant.QUERY_TYPE_1,ClientConstants.CHANNEL_WEI,lastSrvAuthCode,smsCode);
        result.setData(map);
        return result;
    }

    /**
     * @Author: zhangqingqing
     * @Desc :用户授权自动投资异步回调
     * @Param: * @param bean
     * @Date: 16:37 2018/5/30
     * @Return: String
     */
    @ApiOperation(value = "用户授权自动投资异步回调", notes = "用户授权自动投资异步回调")
    @ResponseBody
    @PostMapping(value = "/userAuthInvesBgreturn", produces = "application/json; charset=utf-8")
    public String userAuthInvesBgreturn(@RequestBody @Valid BankCallBean bean) {
        String result = autoPlusService.userBgreturn(bean);
        return result;
    }

    /**
     * @Author: zhangqingqing
     * @Desc :用户授权自动债转异步回调
     * @Param: * @param bean
     * @Date: 16:37 2018/5/30
     * @Return: String
     */
    @ApiOperation(value = "用户授权自动债转异步回调", notes = "用户授权自动债转异步回调")
    @PostMapping(value = "/userAuthCreditBgreturn", produces = "application/json; charset=utf-8")
    public String userCreditAuthInvesBgreturn(@RequestBody @Valid BankCallBean bean) {

        String result = autoPlusService.userBgreturn(bean);
        return result;
    }
}
