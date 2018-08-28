/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.wechat.transpassword;

import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.exception.CheckException;
import com.hyjf.cs.common.bean.result.WeChatResult;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.password.PassWordService;
import com.hyjf.cs.user.constants.ResultEnum;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
    public WeChatResult setPassword(@RequestHeader(value = "userId") Integer userId, HttpServletRequest request) {
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
        BankCallBean bean = new BankCallBean();
        // 异步调用路
        String bgRetUrl = systemConfig.getWeChatHost()  + request.getContextPath() +  "/wx/transpassword/passwordBgreturn?sign=" + sign;
        // 同步调用路径
        String retUrl = systemConfig.getWeiFrontHost() +"/user/setting/bankPassword/result/failed?logOrdId="+bean.getLogOrderId()+"&sign=" + sign ;
        String success = systemConfig.getWeiFrontHost() +"/user/setting/bankPassword/result/success?sign=" + sign ;
        // 页面同步返回 URL
        bean.setRetUrl(retUrl);
        bean.setSuccessfulUrl(success);
        // 页面异步返回URL(必须)
        bean.setNotifyUrl(bgRetUrl);
        //拼装参数请求银行
        Map<String,Object> data = passWordService.setWeChatPassword(bean,user,usersInfo,bankOpenAccount);
        result.setData(data);
        return result;
    }

    /**
     * 设置交易密码同步回调
     *
     * @param request
     * @param
     * @return
     */
/*    @ApiOperation(value = "设置交易密码同步回调")
    @GetMapping(value = "/passwordReturn")
    public ModelAndView passwordReturn(HttpServletRequest request,@ModelAttribute BankCallBean bean) {

        bean.convert();
        String sign = request.getParameter("sign");
        String userIdStr = bean.getLogUserId();
        Integer userId = 0;
        if(Validator.isNotNull(userIdStr)){
            userId = Integer.parseInt(userIdStr);
        }else{
            userId = (Integer)request.getAttribute("userId");
        }
        UserVO user = passWordService.getUsersById(userId);
        //判断用户是否设置了交易密码
        boolean flag = user.getIsSetPassword() == 1 ? true : false ;
        if(flag){
            return getSuccessModelAndView(sign);
        }
        BankOpenAccountVO bankOpenAccount = passWordService.getBankOpenAccount(userId);
        // 调用查询电子账户密码是否设置
        BankCallBean selectbean = new BankCallBean();
        selectbean.setTxCode(BankCallConstant.TXCODE_PASSWORD_SET_QUERY);
        selectbean.setChannel(BankCallConstant.CHANNEL_PC);
        // 电子账号
        selectbean.setAccountId(String.valueOf(bankOpenAccount.getAccount()));
        // 操作者ID
        selectbean.setLogUserId(String.valueOf(userId));
        selectbean.setLogOrderId(GetOrderIdUtils.getOrderId2(userId));
        selectbean.setLogOrderDate(GetOrderIdUtils.getOrderDate());
        selectbean.setLogClient(0);
        // 返回参数
        BankCallBean retBean = null;
        // 调用接口
        retBean = BankCallUtils.callApiBg(selectbean);
        if("1".equals(retBean.getPinFlag())){
            // 是否设置密码中间状态
            this.passWordService.updateUserIsSetPassword(userId);
            return getSuccessModelAndView(sign);
        }
        return getErrorModelAndView(ResultEnum.USER_ERROR_207,sign,null,null);
    }*/

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
    public BankCallResult passwordBgreturn(BankCallBean bean) {
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
                e.printStackTrace();
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
    public WeChatResult<Object> resetPassword(@RequestHeader(value = "userId") Integer userId,HttpServletRequest request) {
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
        BankCallBean bean = new BankCallBean();
        // 同步调用路径
        String retUrl = systemConfig.getWeiFrontHost() +"/user/setting/bankPassword/result/failed?logOrdId="+bean.getLogOrderId()+"&sign=" + sign;
        String success = systemConfig.getWeiFrontHost() +"/user/setting/bankPassword/result/success?sign=" + sign ;
        // 异步调用路
        String bgRetUrl = systemConfig.getWeChatHost() + request.getContextPath() +  "/wx/transpassword/resetPasswordBgreturn?sign=" + sign;
        bean.setSuccessfulUrl(success);
        // 页面同步返回 URL
        bean.setRetUrl(retUrl);
        // 页面异步返回URL(必须)
        bean.setNotifyUrl(bgRetUrl);
        //拼装参数请求银行
        Map<String,Object> data = passWordService.resetWeChatPassword(bean,user,usersInfo,bankOpenAccount);
        result.setData(data);
        return result;
    }

    /**
     * 重置交易密码同步回调
     *
     * @param request
     * @param
     * @return
     */
  /*  @ApiOperation(value = "重置交易密码同步回调")
    @GetMapping(value = "/resetPasswordReturn")
    public ModelAndView resetPasswordReturn(HttpServletRequest request, BankCallBean bean) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView = new ModelAndView("/jumpHTML");
        BaseMapBean baseMapBean=new BaseMapBean();
        String sign = request.getParameter("sign");
        String isSuccess = request.getParameter("isSuccess");
        // 返回失败
        if (bean.getRetCode()!=null&&!BankCallConstant.RESPCODE_SUCCESS.equals(bean.getRetCode())||!"1".equals(isSuccess)) {
            //重置交易密码回调,返回失败
            return getErrorModelAndView(ResultEnum.USER_ERROR_215, sign, null, bean.getRetCode());
        }
        baseMapBean.set(CustomConstants.APP_STATUS, ResultEnum.SUCCESS.getStatus());
        baseMapBean.set(CustomConstants.APP_STATUS_DESC, "交易密码修改成功");
        baseMapBean.setCallBackAction(systemConfig.getWeChatHost()+ "/user/setting/bankPassword/result/success");
        baseMapBean.setJumpFlag(BaseMapBean.JUMP_FLAG_NO);
        modelAndView.addObject("callBackForm", baseMapBean);
        return modelAndView;
    }*/

    /**
     * 重置交易密码异步回调
     * @param bean
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "重置交易密码异步回调")
    @PostMapping(value = "/resetPasswordBgreturn")
    public BankCallResult resetPasswordBgreturn(BankCallBean bean) {
        BankCallResult result = new BankCallResult();
        result.setMessage("交易密码修改成功");
        result.setStatus(true);
        return result;
    }

 /*   private ModelAndView getSuccessModelAndView(String sign) {
        ModelAndView modelAndView = new ModelAndView("/jumpHTML");
        BaseMapBean baseMapBean = new BaseMapBean();
        baseMapBean.set(CustomConstants.APP_STATUS, ResultEnum.SUCCESS.getStatus());
        baseMapBean.set(CustomConstants.APP_STATUS_DESC, ResultEnum.SUCCESS.getStatusDesc());
        baseMapBean.set(CustomConstants.APP_SIGN, sign);
        baseMapBean.setCallBackAction(systemConfig.getWeChatHost()+ "/user/setting/bankPassword/result/success");
        modelAndView.addObject("callBackForm", baseMapBean);
        return modelAndView;
    }

    private ModelAndView getErrorModelAndView(ResultEnum param, String sign, String retCodeSet,
                                              String retCodeUpd) {
        ModelAndView modelAndView = new ModelAndView("/jumpHTML");
        BaseMapBean baseMapBean = new BaseMapBean();
        if (StringUtils.isNotBlank(retCodeSet)) {
            baseMapBean.set(CustomConstants.APP_STATUS, ResultEnum.FAIL.getStatus());
            baseMapBean.set(CustomConstants.APP_STATUS_DESC, "交易密码设置失败,失败原因：" + passWordService.getBankRetMsg(retCodeSet));
        } else if (StringUtils.isNotBlank(retCodeUpd)) {
            baseMapBean.set(CustomConstants.APP_STATUS, ResultEnum.FAIL.getStatus());
            baseMapBean.set(CustomConstants.APP_STATUS_DESC, "交易密码修改失败,失败原因：" + passWordService.getBankRetMsg(retCodeUpd));
        }
        else {
            baseMapBean.set(CustomConstants.APP_STATUS, param.getStatus());
            baseMapBean.set(CustomConstants.APP_STATUS_DESC, param.getStatusDesc());
        }
        baseMapBean.set(CustomConstants.APP_SIGN, sign);
        baseMapBean.setCallBackAction(systemConfig.getWeChatHost()+ "/user/setting/bankPassword/result/failed");
        modelAndView.addObject("callBackForm", baseMapBean);
        return modelAndView;
    }*/
}
