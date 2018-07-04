/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.app.autoplus;

import com.hyjf.am.vo.user.AuthorizedVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.common.util.ClientConstants;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.autoplus.AutoPlusService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallStatusConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation(value = "授权发送短信验证码", notes = "授权发送短信验证码")
    @ApiImplicitParam(name = "param",value = "{userAutoType: string} type=0授权自动投标；type=1授权自动债转", dataType = "Map")
    @PostMapping(value = "/autoPlusSendCode", produces = "application/json; charset=utf-8")
    public WebResult<Object> autoPlusSendCode(@RequestHeader(value = "userId") Integer userId, @RequestBody Map<String,String> param) {
        logger.info("weChat端授权发送短信验证码, param :{}", param);
        WebResult<Object> result = new WebResult<Object>();
        CheckUtil.check(userId!=null,MsgEnum.ERR_USER_NOT_LOGIN);
        UserVO user = autoPlusService.getUsersById(userId);
        String srvTxCode = autoPlusService.checkSmsParam(user,param);
        // 请求银行接口
        BankCallBean bankBean = null;
        try {
            bankBean = autoPlusService.callSendCode(userId,user.getMobile(),srvTxCode, ClientConstants.CHANNEL_APP,null);
        } catch (Exception e) {
            logger.error("请求验证码接口发生异常", e);
            throw new CheckException(MsgEnum.ERR_BANK_CALL);
        }
        if(bankBean == null || !(BankCallStatusConstant.RESPCODE_SUCCESS.equals(bankBean.getRetCode()))) {
            logger.error("请求验证码接口发生异常");
            throw new CheckException(MsgEnum.ERR_BANK_CALL);
        }else {
            result.setData(bankBean.getSrvAuthCode());
        }
        return result;
    }

    /**
     * 用户授权自动债转
     * @param token
     * @param
     * @param
     * @return
     */
    @ApiOperation(value = "用户授权自动债转", notes = "用户授权自动债转")
    @PostMapping("/userAuthCredit")
    public WebResult<Object> userAuthCredit(@RequestHeader(value = "token") String token, @RequestBody AuthorizedVO authorizedVO) {
        WebResult<Object> result = new WebResult<Object>();
        String lastSrvAuthCode = authorizedVO.getLastSrvAuthCode();
        String smsCode = authorizedVO.getSmsCode();
        // 验证请求参数
        if (token == null) {
            throw new ReturnMessageException(MsgEnum.ERR_USER_NOT_LOGIN);
        }
        UserVO user = this.autoPlusService.getUsers(token);
        //检查用户信息
        autoPlusService.checkUserMessage(user,lastSrvAuthCode,smsCode);
        Map<String,Object> map =  autoPlusService.userCreditAuthInves(user, ClientConstants.APP_CLIENT, BankCallConstant.QUERY_TYPE_2,ClientConstants.CHANNEL_APP,lastSrvAuthCode,smsCode);
        result.setData(map);
        return result;
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
    @PostMapping(value = "/userAuthInves", produces = "application/json; charset=utf-8")
    public  WebResult<Object> userAuthInves(@RequestHeader(value = "token") String token, @RequestBody AuthorizedVO authorizedVO) {
        WebResult<Object> result = new WebResult<Object>();
        String lastSrvAuthCode = authorizedVO.getLastSrvAuthCode();
        String smsCode = authorizedVO.getSmsCode();
        // 验证请求参数
        if (token == null) {
            throw new ReturnMessageException(MsgEnum.ERR_USER_NOT_LOGIN);
        }
        UserVO user = this.autoPlusService.getUsers(token);
        //检查用户信息
        autoPlusService.checkUserMessage(user,lastSrvAuthCode,smsCode);
        Map<String,Object> map = autoPlusService.userCreditAuthInves(user, ClientConstants.APP_CLIENT, BankCallConstant.QUERY_TYPE_1,ClientConstants.CHANNEL_APP,lastSrvAuthCode,smsCode);
        result.setData(map);
        return result;
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
