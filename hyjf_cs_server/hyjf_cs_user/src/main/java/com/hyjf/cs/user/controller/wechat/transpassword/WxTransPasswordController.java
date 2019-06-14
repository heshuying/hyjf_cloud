/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.wechat.transpassword;

import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.util.ClientConstants;
import com.hyjf.cs.common.bean.result.WeChatResult;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.constants.ResultEnum;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.password.PassWordService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author zhangqingqing
 * @version WxTransPasswordController, v0.1 2018/8/16 10:06
 */
@Api(value = "weChat端-密码相关服务",tags = "weChat端-密码相关服务")
@RestController
@RequestMapping("/hyjf-wechat/wx/transpassword")
public class WxTransPasswordController extends BaseUserController {

    @Autowired
    PassWordService passWordService;

    @Autowired
    SystemConfig systemConfig;

    /**
     * 设置交易密码
     *
     * @param request
     * @param
     * @return
     */
    @ApiOperation(value = "设置交易密码",notes = "设置交易密码")
    @GetMapping(value ="/setPassword.page")
    public WeChatResult setPassword(@RequestHeader(value = "userId") Integer userId,
                                    @RequestHeader(value = "wjtClient",required = false) String wjtClient,
                                    HttpServletRequest request) {
        WeChatResult<Object> result = new WeChatResult<>();
        String sign = request.getParameter("sign");
        UserVO user = passWordService.weChatCheck(userId);
        //判断用户是否设置过交易密码
        Integer passwordFlag = user.getIsSetPassword();
        if (passwordFlag == 1) {
            //已设置交易密码
            throw new CheckException(ResultEnum.USER_ERROR_206.getStatus(),ResultEnum.USER_ERROR_206.getStatusDesc());
        }
        UserInfoVO usersInfo= passWordService.getUserInfo(userId);
        BankOpenAccountVO bankOpenAccount = passWordService.getBankOpenAccount(userId);
        // 调用设置密码接口
        String txcode = "";
        BankCallBean bean = new BankCallBean(user.getUserId(),txcode, ClientConstants.WECHAT_CLIENT);
        // 异步调用路
        String bgRetUrl =  "http://CS-USER/hyjf-wechat/wx/transpassword/passwordBgreturn?sign=" + sign;
        // 页面异步返回URL(必须)
        bean.setNotifyUrl(bgRetUrl);
        //拼装参数请求银行
        Map<String,Object> data = passWordService.setWeChatPassword(bean,user,usersInfo,bankOpenAccount,wjtClient,sign);
        result.setData(data);
        return result;
    }

    /**
     * 设置交易密码异步回调
     *
     * @param
     * @param
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "设置交易密码异步回调")
    @PostMapping(value = "/passwordBgreturn")
    public BankCallResult passwordBgreturn(@RequestBody BankCallBean bean) {
        BankCallResult result = new BankCallResult();
        bean.convert();
        Integer userId = Integer.parseInt(bean.getLogUserId());
        // 查询用户开户状态
        UserVO user = passWordService.getUsersById(userId);
        // 成功或审核中
        if (user != null && BankCallConstant.RESPCODE_SUCCESS.equals(bean.get(BankCallConstant.PARAM_RETCODE))) {
            try {
                // 开户后保存相应的数据以及日志
                passWordService.updateUserIsSetPassword(userId);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        result.setMessage("交易密码设置成功");
        result.setStatus(true);
        return result;
    }


    /**
     * 重置交易密码
     *
     * @param request
     * @param
     * @return
     */
    @ApiOperation(value = "重置交易密码")
    @GetMapping(value = "/resetPassword.page")
    public WeChatResult<Object> resetPassword(@RequestHeader(value = "userId") Integer userId,
                                              @RequestHeader(value = "wjtClient",required = false) String wjtClient,
                                              HttpServletRequest request) {
        WeChatResult<Object> result = new WeChatResult<>();
        String sign = request.getParameter("sign");
        UserVO user = passWordService.weChatCheck(userId);
        //判断用户是否设置过交易密码
        Integer passwordFlag = user.getIsSetPassword();
        if (passwordFlag == 0) {
            //已设置交易密码
            throw new CheckException(ResultEnum.USER_ERROR_201.getStatus(),ResultEnum.USER_ERROR_201.getStatusDesc());
        }
        BankOpenAccountVO bankOpenAccount = passWordService.getBankOpenAccount(userId);
        UserInfoVO usersInfo= passWordService.getUserInfo(userId);
        // 调用设置密码接口
        String txcode = "";
        BankCallBean bean = new BankCallBean(user.getUserId(),txcode, ClientConstants.WECHAT_CLIENT);
        // 异步调用路
        String bgRetUrl = "http://CS-USER/hyjf-wechat/wx/transpassword/resetPasswordBgreturn?sign=" + sign;
        // 页面异步返回URL(必须)
        bean.setNotifyUrl(bgRetUrl);
        //拼装参数请求银行
        Map<String,Object> data = passWordService.resetWeChatPassword(bean,user,usersInfo,bankOpenAccount,wjtClient,sign);
        result.setData(data);
        return result;
    }

    /**
     * 重置交易密码异步回调
     * @param bean
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "重置交易密码异步回调")
    @PostMapping(value = "/resetPasswordBgreturn")
    public BankCallResult resetPasswordBgreturn(@RequestBody BankCallBean bean) {
        BankCallResult result = new BankCallResult();
        result.setMessage("交易密码修改成功");
        result.setStatus(true);
        return result;
    }
}
