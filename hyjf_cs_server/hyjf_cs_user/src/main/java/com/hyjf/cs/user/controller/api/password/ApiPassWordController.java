/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.api.password;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.cs.common.controller.BaseController;
import com.hyjf.cs.user.bean.BaseDefine;
import com.hyjf.cs.user.bean.BaseResultBean;
import com.hyjf.cs.user.bean.ThirdPartyTransPasswordRequestBean;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.constants.ErrorCodeConstant;
import com.hyjf.cs.user.service.password.PassWordService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import com.hyjf.soa.apiweb.CommonSoaUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wangc
 */
@Api(value = "api端-密码相关服务",tags = "api端-密码相关服务")
@Controller
@RequestMapping("/hyjf-api/server/user/transpassword")
public class ApiPassWordController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(ApiPassWordController.class);

    @Autowired
    PassWordService passWordService;

    @Autowired
    SystemConfig systemConfig;

    public static final String CALL_BACK_TRANSPASSWORD_VIEW = "/callback/callback_transpassword";

    /**
     * 设置交易密码
     * @param transPasswordRequestBean
     * @return
     */
    @ApiOperation(value = "设置交易密码", notes = "设置交易密码")
    @PostMapping(value = "/setPassword.do")
    public ModelAndView setPassword(@RequestBody ThirdPartyTransPasswordRequestBean transPasswordRequestBean) {
        logger.info("api端设置交易密码 start");
        ModelAndView modelAndView = new ModelAndView();
        logger.info("第三方请求参数："+JSONObject.toJSONString(transPasswordRequestBean));
        Map<String,Object> map = passWordService.apiCheack(transPasswordRequestBean,BankCallConstant.TXCODE_PASSWORD_SET_PAGE, BaseDefine.METHOD_SERVER_RESET_PASSWORD);
        map.put("callBackAction",transPasswordRequestBean.getRetUrl());
        if (null==map.get("flag")){
            return callbackErrorViewForMap(map);
        }
        UserVO user = (UserVO) map.get("user");
        BankOpenAccountVO bankOpenAccount = (BankOpenAccountVO) map.get("bankOpenAccount");
        BankCallBean bean = passWordService.apiSetPassword(transPasswordRequestBean,BankCallConstant.BANK_URL_PASSWORDRESETPAGE,BankCallConstant.TXCODE_PASSWORD_SET_PAGE,user,bankOpenAccount);
        try {
            modelAndView = BankCallUtils.callApi(bean);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return modelAndView;
    }

    /**
     * 设置交易密码同步回调
     * @param request
     * @param bean
     * @return
     */
    @ApiOperation(value = "设置交易密码同步回调")
    @GetMapping("/passwordReturn")
    public ModelAndView passwordReturn(HttpServletRequest request, BankCallBean bean) {
        logger.info("设置交易密码同步回调start");
        String url = request.getParameter("callback").replace("*-*-*", "#");
        bean.convert();
        String logUserId = bean.getLogUserId();
        if(null==logUserId){
            logUserId = request.getParameter("logUserId");
        }
        int userId = Integer.parseInt(logUserId);
        BankOpenAccountVO bankOpenAccount = passWordService.getBankOpenAccount(userId);
        // 调用查询电子账户密码是否设置
        BankCallBean selectbean = new BankCallBean();
        selectbean.setTxCode(BankCallConstant.TXCODE_PASSWORD_SET_QUERY);
        selectbean.setTxDate(GetOrderIdUtils.getTxDate());
        selectbean.setTxTime(GetOrderIdUtils.getTxTime());
        selectbean.setSeqNo(GetOrderIdUtils.getSeqNo(6));
        selectbean.setChannel(BankCallConstant.CHANNEL_PC);
        // 电子账号
        selectbean.setAccountId(String.valueOf(bankOpenAccount.getAccount()));
        // 操作者ID
        selectbean.setLogUserId(String.valueOf(userId));
        selectbean.setLogOrderId(GetOrderIdUtils.getOrderId2(userId));
        selectbean.setLogClient(0);
        // 返回参数
        BankCallBean retBean = null;
        // 调用接口
        retBean = BankCallUtils.callApiBg(selectbean);
        Map<String,Object> result =new HashMap<>();
        result.put("accountId", bankOpenAccount.getAccount());
        result.put("status", "success");
        result.put("callBackAction", url);
        if ("1".equals(retBean.getPinFlag())) {
            // 是否设置密码中间状态
            passWordService.updateUserIsSetPassword(userId);
            result.put("status", ErrorCodeConstant.SUCCESS);
            result.put("statusDesc", "交易密码设置成功");
        } else {
            // 设置交易密码
            result.put("statusDesc", "交易密码设置失败！");
            result.put("status",ErrorCodeConstant.STATUS_CE999999);
            result.put("acqRes",request.getParameter("acqRes"));
            return callbackErrorViewForMap(result);
        }
        result.put("acqRes",request.getParameter("acqRes"));
        logger.info("设置交易密码同步回调end");
        return callbackErrorViewForMap(result);
    }

    /**
     * 设置交易密码异步回调
     *
     * @param request
     * @param
     * @return
     */
    @ApiOperation(value = " 设置交易密码异步回调",notes = " 设置交易密码异步回调")
    @ResponseBody
    @PostMapping(value = "/passwordBgreturn")
    public BankCallResult passwordBgreturn(HttpServletRequest request, @RequestBody BankCallBean bean) {
        logger.info("api 交易密码异步回调start");
        // 返回值  9-22修改
        BankCallResult result = new BankCallResult();
        String message = "";
        String status = "";
        Map<String, String> params = new HashMap<String, String>();
        // 返回值修改 end
        bean.convert();
        logger.info("设置交易密码异步回调，bean后:{}", JSONObject.toJSONString(bean, true));
        int userId = Integer.parseInt(bean.getLogUserId());
        logger.info("userId："+userId);
        UserVO user = this.passWordService.getUsersById(userId);
        BankOpenAccountVO bankOpenAccount = passWordService.getBankOpenAccount(userId);
        // 成功或审核中
        if (user != null && BankCallConstant.RESPCODE_SUCCESS.equals(bean.get(BankCallConstant.PARAM_RETCODE))) {
            try {
                // 开户后保存相应的数据以及日志
                passWordService.updateUserIsSetPassword(userId);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
            message = "交易密码处理成功";
            status = ErrorCodeConstant.SUCCESS;
        }else{
            // 设置交易密码
            message = "交易密码处理失败，失败原因："+passWordService.getBankRetMsg(bean.getRetCode());
            status = ErrorCodeConstant.STATUS_CE999999;
        }
        // 返回值  9-22修改
        params.put("accountId", bankOpenAccount.getAccount());
        params.put("status", status);
        params.put("statusDesc",message);
        BaseResultBean resultBean = new BaseResultBean();
        resultBean.setStatusForResponse(status);
        params.put("chkValue", resultBean.getChkValue());
        params.put("acqRes",request.getParameter("acqRes"));
        CommonSoaUtils.noRetPostThree(request.getParameter("callback").replace("*-*-*","#"), params);
        logger.info("处理交易密码异步回调end");
        result.setMessage("处理交易密码成功");
        result.setStatus(true);
        // 返回值  9-22修改 end
        return result;
    }

    /**
     * 修改交易密码
     * @param transPasswordRequestBean
     * @return
     */
    @PostMapping(value = "/resetPassword.do")
    @ApiOperation(value = "修改交易密码", notes = "修改交易密码")
    public ModelAndView resetPassword(@RequestBody ThirdPartyTransPasswordRequestBean transPasswordRequestBean) {
       ModelAndView modelAndView = new ModelAndView();
        logger.info("第三方请求参数："+JSONObject.toJSONString(transPasswordRequestBean));
        Map<String,Object> map = passWordService.apiCheack(transPasswordRequestBean, BankCallConstant.TXCODE_PASSWORD_RESET_PAGE,BaseDefine.METHOD_SERVER_RESET_PASSWORD);
        map.put("callBackAction",transPasswordRequestBean.getRetUrl());
        if (null==map.get("flag")){
            return callbackErrorViewForMap(map);
        }
        UserVO user = (UserVO) map.get("user");
        BankOpenAccountVO bankOpenAccount = (BankOpenAccountVO) map.get("bankOpenAccount");
        BankCallBean bean = passWordService.apiSetPassword(transPasswordRequestBean,BankCallConstant.BANK_URL_PASSWORDRESETPAGE,BankCallConstant.TXCODE_PASSWORD_RESET_PAGE,user,bankOpenAccount);
        try {
            modelAndView = BankCallUtils.callApi(bean);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return modelAndView;
    }

    /**
     * 修改交易密码同步回调
     *
     * @param request
     * @return
     */
    @ApiIgnore
    @RequestMapping("/resetPasswordReturn")
    public ModelAndView resetPasswordReturn(HttpServletRequest request, @ModelAttribute  BankCallBean bean) {
        logger.info("修改交易密码同步回调start");
        bean.convert();
        logger.info("bean后:{}", JSONObject.toJSONString(bean, true));
        String isSuccess = request.getParameter("isSuccess");
        String url = request.getParameter("callback").replace("*-*-*","#");
        String logUserId = bean.getLogUserId();
        if(null==logUserId){
            logUserId = request.getParameter("logUserId");
        }
        int userId = Integer.parseInt(logUserId);
        BankOpenAccountVO bankOpenAccount = passWordService.getBankOpenAccount(userId);
        Map<String,Object> result =new HashMap<>();
        result.put("accountId", bankOpenAccount.getAccount());
        result.put("callBackAction", url);
        result.put("status", ErrorCodeConstant.SUCCESS);
        result.put("acqRes",request.getParameter("acqRes"));
        result.put("statusDesc", "交易密码设置成功");
        // 返回失败
        if (isSuccess == null || !"1".equals(isSuccess)) {
            result.put("statusDesc", "交易密码修改失败！");
            result.put("status",ErrorCodeConstant.STATUS_CE999999);
            return callbackErrorViewForMap(result);
        }
        logger.info("设置交易密码同步回调end");
        return callbackErrorViewForMap(result);
    }

    /**
     * 修改交易密码异步回调
     *
     * @param request
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "修改交易密码异步回调")
    @PostMapping("/resetPasswordBgreturn")
    public BankCallResult resetPasswordBgreturn(HttpServletRequest request,@RequestBody BankCallBean bean) {
        logger.info("修改交易密码异步回调start");
        // 返回值  9-22修改
        BankCallResult result = new BankCallResult();
        String message = "";
        String status = "";
        Map<String, String> params = new HashMap<String, String>();
        bean.convert();
        int userId = Integer.parseInt(bean.getLogUserId());
        UserVO user = passWordService.getUsersById(userId);
        BankOpenAccountVO bankOpenAccount = passWordService.getBankOpenAccount(userId);
        // 成功或审核中
        if (user != null && BankCallConstant.RESPCODE_SUCCESS.equals(bean.get(BankCallConstant.PARAM_RETCODE))) {
            message = "交易密码修改成功";
            status = ErrorCodeConstant.SUCCESS;
        }else{
            // 设置交易密码
            message = "交易密码修改失败,失败原因：" + passWordService.getBankRetMsg(bean.getRetCode());
            // 修改失败时返回的状态码
            status = ErrorCodeConstant.STATUS_CE999999;
        }
        logger.info("修改交易密码异步回调end");
        params.put("accountId", bankOpenAccount.getAccount());
        params.put("status", status);
        params.put("statusDesc",message);
        params.put("acqRes",request.getParameter("acqRes"));
        BaseResultBean resultBean = new BaseResultBean();
        resultBean.setStatusForResponse(status);
        params.put("chkValue", resultBean.getChkValue());
        CommonSoaUtils.noRetPostThree(request.getParameter("callback").replace("*-*-*","#"), params);
        logger.info("修改交易密码异步回调end");
        result.setMessage("设置交易密码成功");
        result.setStatus(true);
        return result;
    }
}