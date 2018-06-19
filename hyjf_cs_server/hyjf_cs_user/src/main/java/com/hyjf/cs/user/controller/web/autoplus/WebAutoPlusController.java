/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.web.autoplus;

import com.hyjf.am.vo.user.AuthorizedVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.enums.utils.MsgEnum;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.common.util.ClientConstants;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.cs.common.bean.result.ApiResult;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.constants.BindCardError;
import com.hyjf.cs.user.constants.OpenAccountError;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.autoplus.AutoPlusService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallStatusConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

/**
 * @author zhangqingqing
 * @version AutoPlusController, v0.1 2018/6/11 14:09
 */
@Api(value = "web端用户自动投标自动债转授权")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/web/user")
public class WebAutoPlusController extends BaseUserController {

    private static final Logger logger = LoggerFactory.getLogger(WebAutoPlusController.class);

    @Autowired
    private AutoPlusService autoPlusService;

    @Autowired
    SystemConfig systemConfig;


    @RequestMapping(value = "/init")
    public String init(Model model) {

        return "init";
    }

    @ApiOperation(value = "授权发送短信验证码", notes = "授权发送短信验证码")
    @ApiImplicitParam(name = "param",value = "{type: string} type=1授权自动投标；type=2授权自动债转", dataType = "Map")
    @PostMapping(value = "/autoPlusSendCode", produces = "application/json; charset=utf-8")
    public WebResult<Object> autoPlusSendCode(@RequestHeader(value = "token", required = true) String token, @RequestBody Map<String,String> param) {
        logger.info("Web端授权发送短信验证码, param :{}", param);
        WebResult<Object> result = new WebResult<Object>();
        UserVO user = autoPlusService.getUsers(token);
        CheckUtil.check(user!=null,MsgEnum.ERR_USER_NOT_LOGIN);
        CheckUtil.check(user.getMobile()!=null,MsgEnum.ERR_MOBILE);
        CheckUtil.check(null!=param && StringUtils.isNotBlank(param.get("type")), MsgEnum.ERR_PARAM_TYPE);
        String srvTxCode = "1".equals(param.get("type"))? BankCallConstant.TXCODE_AUTO_BID_AUTH_PLUS:BankCallConstant.TXCODE_AUTO_CREDIT_INVEST_AUTH_PLUSS;
                // 请求银行绑卡接口
        BankCallBean bankBean = null;
        try {
            bankBean = autoPlusService.callSendCode(user.getUserId(),user.getMobile(),srvTxCode, ClientConstants.CHANNEL_PC,null);
        } catch (Exception e) {
            result.setStatus(ApiResult.FAIL);
            result.setStatusDesc(BindCardError.BANK_CALL_ERROR.getMsg());
            logger.error("请求验证码接口发生异常", e);
        }
        if(bankBean == null || !(BankCallStatusConstant.RESPCODE_SUCCESS.equals(bankBean.getRetCode()))) {
            result.setStatus(ApiResult.FAIL);
            result.setStatusDesc(BindCardError.BANK_CALL_ERROR.getMsg());
            logger.error("请求验证码接口失败");
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
        BankCallBean bean = autoPlusService.userCreditAuthInves(user, ClientConstants.WEB_CLIENT, ClientConstants.QUERY_TYPE_1, ClientConstants.CHANNEL_PC, lastSrvAuthCode, smsCode);
        ModelAndView modelAndView = new ModelAndView();
        try {
            modelAndView = BankCallUtils.callApi(bean);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ReturnMessageException(MsgEnum.ERR_CALL_BANK);
        }
        return modelAndView;
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
    @PostMapping("/creditUserAuthInves")
    public ModelAndView creditUserAuthInves(@RequestHeader(value = "token", required = true) String token,@RequestBody AuthorizedVO authorizedVO) {
        String lastSrvAuthCode = authorizedVO.getLastSrvAuthCode();
        String smsCode = authorizedVO.getSmsCode();
        // 验证请求参数
        if (token == null) {
            throw new ReturnMessageException(OpenAccountError.USER_NOT_LOGIN_ERROR);
        }
        UserVO user = this.autoPlusService.getUsers(token);
        //检查用户信息
        autoPlusService.checkUserMessage(user,lastSrvAuthCode,smsCode);
        BankCallBean bean = autoPlusService.userCreditAuthInves(user, ClientConstants.WEB_CLIENT, ClientConstants.QUERY_TYPE_2, ClientConstants.CHANNEL_PC, lastSrvAuthCode, smsCode);
        ModelAndView modelAndView = new ModelAndView();
        try {
            modelAndView = com.hyjf.pay.lib.bank.util.BankCallUtils.callApi(bean);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ReturnMessageException(MsgEnum.ERR_CALL_BANK);
        }
        return modelAndView;
    }

    /**
     * @Author: zhangqingqing
     * @Desc :用户授权自动投资同步回调
     * @Param: * @param token
     * @param request
     * @param bean
     * @Date: 16:42 2018/5/30
     * @Return: Map
     */
    @ApiOperation(value = "用户授权自动投资同步回调", notes = "用户授权自动投资同步回调")
    @PostMapping(value = "/userAuthInvesReturn", produces = "application/json; charset=utf-8")
    public Map<String, String> userAuthInvesReturn(@RequestHeader(value = "token", required = true) String token, HttpServletRequest request,@RequestBody @Valid BankCallBean bean) {
        logger.info("userAuthInvesReturn:" + "[投资人自动投标签约增强同步回调开始]");
        String isSuccess = request.getParameter("isSuccess");
        Map<String, String> result = autoPlusService.userAuthReturn(token, bean, ClientConstants.INVES_URL_TYPE, isSuccess);
        return result;
    }

    /**
     * @Author: zhangqingqing
     * @Desc :用户授权自动债转同步回调
     * @Param: * @param token
     * @param request
     * @param bean
     * @Date: 16:42 2018/5/30
     * @Return: Map
     */
    @ApiOperation(value = "用户授权自动债转同步回调", notes = "用户授权自动债转同步回调")
    @PostMapping(value = "/credituserAuthInvesReturn", produces = "application/json; charset=utf-8")
    public Map<String, String> userCreditAuthInvesReturn(@RequestHeader(value = "token", required = true) String token, HttpServletRequest request,
                                                         @RequestBody @Valid  BankCallBean bean) {
        logger.info("[投资人自动债转签约增强同步回调开始]");
        String isSuccess = request.getParameter("isSuccess");
        Map<String, String> result = autoPlusService.userAuthReturn(token, bean, ClientConstants.CREDIT_URL_TYPE, isSuccess);
        return result;
    }

    /**
     * @Author: zhangqingqing
     * @Desc :用户授权自动债转异步回调
     * @Param: * @param bean
     * @Date: 16:43 2018/5/30
     * @Return: String
     */
    @ApiOperation(value = "用户授权异步回调", notes = "用户授权自动债转异步回调")
    @PostMapping(value = "/bgreturn", produces = "application/json; charset=utf-8")
    public String userCreditAuthInvesBgreturn(@RequestBody @Valid BankCallBean bean) {
        String result = autoPlusService.userBgreturn(bean);
        return result;

    }
}
