/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.web.autoplus;

import com.hyjf.am.vo.user.AuthorizedVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.util.ClientConstants;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.user.config.SystemConfig;
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
 * @version AutoPlusController, v0.1 2018/6/11 14:09
 */
@Api(tags = {"web端-用户自动投标自动债转授权"})
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/hyjf-web/user")
public class WebAutoPlusController extends BaseUserController {

    private static final Logger logger = LoggerFactory.getLogger(WebAutoPlusController.class);

    @Autowired
    private AutoPlusService autoPlusService;

    @Autowired
    SystemConfig systemConfig;

    @ApiOperation(value = "授权发送短信验证码", notes = "授权发送短信验证码")
    @ApiImplicitParam(name = "param",value = "{userAutoType: string} type=0授权自动投标；type=1授权自动债转", dataType = "Map")
    @PostMapping(value = "/autoPlusSendCode", produces = "application/json; charset=utf-8")
    public WebResult<Object> autoPlusSendCode(@RequestHeader(value = "userId") Integer userId, @RequestBody Map<String,String> param) {
        logger.info("Web端授权发送短信验证码, param :{}", param);
        WebResult<Object> result = new WebResult<Object>();
        CheckUtil.check(userId!=null,MsgEnum.ERR_USER_NOT_LOGIN);
        UserVO user = autoPlusService.getUsersById(userId);
        String userAutoType = param.get("userAutoType");
        String srvTxCode = autoPlusService.checkSmsParam(user,userAutoType);
        // 请求银行接口
        BankCallBean bankBean = null;
        try {
            bankBean = autoPlusService.callSendCode(userId,user.getMobile(),srvTxCode, ClientConstants.CHANNEL_PC,null);
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
     * @Author: zhangqingqing
     * @Desc :用户授权自动投资
     * @Param: * @param token
     * @param authorizedVO
     * @Date: 16:43 2018/5/30
     * @Return: ModelAndView
     */
    @ApiOperation(value = "用户授权自动投资", notes = "用户授权自动投资")
    @PostMapping(value = "/userAuthInves" , produces = "application/json; charset=utf-8")
    public  WebResult<Object> userAuthInves(@RequestHeader(value = "userId") Integer userId, @RequestBody AuthorizedVO authorizedVO) {
        WebResult<Object> result = new WebResult<Object>();
        String lastSrvAuthCode = authorizedVO.getLastSrvAuthCode();
        String smsCode = authorizedVO.getSmsCode();
        // 验证请求参数
        CheckUtil.check(userId!=null,MsgEnum.ERR_USER_NOT_LOGIN);
        UserVO user = this.autoPlusService.getUsersById(userId);
        //检查用户信息
       autoPlusService.checkUserMessage(user,lastSrvAuthCode,smsCode);
        Map<String,Object> map = autoPlusService.userCreditAuthInves(user, ClientConstants.WEB_CLIENT, ClientConstants.QUERY_TYPE_1, ClientConstants.CHANNEL_PC, lastSrvAuthCode, smsCode);
        result.setData(map);
        return result;
    }

    /**
     * @Author: zhangqingqing
     * @Desc :用户授权自动债转
     * @Param: * @param token
     * @param authorizedVO
     * @Date: 16:42 2018/5/30
     * @Return: ModelAndView
     */
    @ApiOperation(value = "用户授权自动债转", notes = "用户授权自动债转")
    @PostMapping(value = "/creditUserAuthInves", produces = "application/json; charset=utf-8")
    public  WebResult<Object> creditUserAuthInves(@RequestHeader(value = "userId") Integer userId,@RequestBody AuthorizedVO authorizedVO) {
        WebResult<Object> result = new WebResult<Object>();
        String lastSrvAuthCode = authorizedVO.getLastSrvAuthCode();
        String smsCode = authorizedVO.getSmsCode();
        // 验证请求参数
        CheckUtil.check(userId != null,MsgEnum.ERR_USER_NOT_LOGIN);
        UserVO user = this.autoPlusService.getUsersById(userId);
        //检查用户信息
        autoPlusService.checkUserMessage(user,lastSrvAuthCode,smsCode);
        Map<String,Object> map = autoPlusService.userCreditAuthInves(user, ClientConstants.WEB_CLIENT, ClientConstants.QUERY_TYPE_2, ClientConstants.CHANNEL_PC, lastSrvAuthCode, smsCode);
        result.setData(map);
        return result;
    }

    /**
     * 用户授权自动债转异步回调
     * @param bean
     * @return
     */
    @ApiOperation(value = "用户授权自动债转异步回调", notes = "用户授权自动债转异步回调")
    @PostMapping(value = "/creditbgreturn", produces = "application/json; charset=utf-8")
    public String userCreditBgreturn(@RequestBody @Valid BankCallBean bean) {
        String result = autoPlusService.userBgreturn(bean, BankCallConstant.QUERY_TYPE_2);
        return result;
    }

    /**
     * 用户授权自动投资异步回调
     * @param bean
     * @return
     */
    @ApiOperation(value = "用户授权自动投资异步回调", notes = "用户授权自动投资异步回调")
    @PostMapping(value = "/invesbgreturn", produces = "application/json; charset=utf-8")
    public String userInvesAuthBgreturn(@RequestBody @Valid BankCallBean bean) {
        String result = autoPlusService.userBgreturn(bean, BankCallConstant.QUERY_TYPE_1);
        return result;
    }

    @ApiOperation(value = "授权状态接口", notes = "授权状态接口")
    @PostMapping(value = "/userAutoStatus", produces = "application/json; charset=utf-8")
    public WebResult<Object> userAutoStatus(@RequestHeader(value = "token") String token){
        WebResult<Object> result = new WebResult<Object>();
        UserVO user = autoPlusService.getUsers(token);
        Map<String, Integer> map = autoPlusService.userAutoStatus(user.getUserId());
        result.setData(map);
        return result;
    }

    public WebResult getStatus(@RequestHeader(value = "userId") Integer userId){
        WebResult<Object> result = new WebResult<Object>();
        Map<String,String> map = autoPlusService.getStatus(userId);
        result.setData(map);
        return result;
    }

}
