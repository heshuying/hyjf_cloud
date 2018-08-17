/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.api.password;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.cs.common.bean.result.ApiResult;
import com.hyjf.cs.common.controller.BaseController;
import com.hyjf.cs.user.bean.BaseResultBean;
import com.hyjf.cs.user.bean.ThirdPartyTransPasswordRequestBean;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.service.password.PassWordService;
import com.hyjf.cs.user.util.ErrorCodeConstant;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.soa.apiweb.CommonSoaUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wangc
 */
@Api(value = "api端-密码相关服务",tags = "api端-密码相关服务")
@Controller
@RestController
@RequestMapping("/hyjf-api/user/password")
public class ApiPassWordController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(ApiPassWordController.class);

    @Autowired
    PassWordService passWordService;

    @Autowired
    SystemConfig systemConfig;

    /**
     * 设置交易密码
     * @param transPasswordRequestBean
     * @return
     */
    @ApiOperation(value = "设置交易密码", notes = "设置交易密码")
    @PostMapping(value = "/setTeaderPassword", produces = "application/json; charset=utf-8")
    public ApiResult<Object> setPassword(@RequestBody ThirdPartyTransPasswordRequestBean transPasswordRequestBean) {
        logger.info("api端设置交易密码 start");
        ApiResult<Object> result = new ApiResult<>();
        logger.info("第三方请求参数："+JSONObject.toJSONString(transPasswordRequestBean));
        Map<String,Object> map = passWordService.apiSetPassword(transPasswordRequestBean,BankCallConstant.BANK_URL_PASSWORDSET,BankCallConstant.TXCODE_PASSWORD_SET);
        result.setData(map);
        logger.info("设置交易密码end");
        return result;
    }

    /**
     * 设置交易密码异步回调
     *
     * @param request
     * @param
     * @return
     */
    @ApiOperation(value = " 设置交易密码异步回调",notes = " 设置交易密码异步回调")
    @PostMapping(value = "/passwordReturn", produces = "application/json; charset=utf-8")
    public BankCallResult passwordBgreturn(HttpServletRequest request, @RequestBody BankCallBean bean) {
        logger.info("api 交易密码异步回调start");
        // 返回值  9-22修改
        BankCallResult result = new BankCallResult();
        String message = "";
        String status = "";
        Map<String, String> params = new HashMap<String, String>();
        // 返回值修改 end
        bean.convert();
        int userId = Integer.parseInt(bean.getLogUserId());
        UserVO user = this.passWordService.getUsersById(userId);
        BankOpenAccountVO bankOpenAccount = passWordService.getBankOpenAccount(userId);
        // 成功或审核中
        if (user != null && BankCallConstant.RESPCODE_SUCCESS.equals(bean.get(BankCallConstant.PARAM_RETCODE))) {
            try {
                // 开户后保存相应的数据以及日志
                passWordService.updateUserIsSetPassword(userId);
            } catch (Exception e) {
                e.printStackTrace();
            }
            message = "交易密码处理成功";
            status = ErrorCodeConstant.SUCCESS;
        }else{
            // 设置交易密码
            message = "交易密码处理失败";
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
    @PostMapping(value = "/resetPassword", produces = "application/json; charset=utf-8")
    @ApiOperation(value = "修改交易密码", notes = "修改交易密码")
    public ApiResult<Object> resetPassword(@RequestBody ThirdPartyTransPasswordRequestBean transPasswordRequestBean) {
        logger.info("api 修改交易密码 start");
        ApiResult<Object> result = new ApiResult<>();
        logger.info("第三方请求参数："+JSONObject.toJSONString(transPasswordRequestBean));
        Map<String,Object> map = passWordService.apiSetPassword(transPasswordRequestBean,BankCallConstant.BANK_URL_MOBILE,BankCallConstant.TXCODE_PASSWORD_RESET);
        result.setData(map);
        return result;
    }
}