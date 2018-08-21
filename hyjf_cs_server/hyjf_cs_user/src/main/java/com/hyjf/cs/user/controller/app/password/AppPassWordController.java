/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.app.password;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.bean.app.BaseResultBeanFrontEnd;
import com.hyjf.am.vo.trade.CorpOpenAccountRecordVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.bank.LogAcqResBean;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.CommonConstant;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.DES;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.user.bean.BaseMapBean;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.bankopen.BankOpenService;
import com.hyjf.cs.user.service.password.PassWordService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wangc
 */
@Api(value = "app端密码相关服务",tags = "app端-密码相关服务")
@Controller
@RequestMapping("/")
public class AppPassWordController extends BaseUserController {

    @Autowired
    BankOpenService bankOpenService;

    @Autowired
    PassWordService passWordService;

    @Autowired
    SystemConfig systemConfig;

    private static final Logger logger = LoggerFactory.getLogger(AppPassWordController.class);

    /**
     * 修改密码
     * @param key
     * @param userId
     * @param request
     * @return
     */
    @ApiOperation(value = "修改登陆密码", notes = "修改登陆密码")
    @ResponseBody
    @PostMapping(value = "/hyjf-app/appUser/updatePasswordAction")
    public JSONObject updatePasswordAction(@RequestHeader(value = "key") String key, @RequestHeader(value = "userId") Integer userId, HttpServletRequest request) {

        JSONObject ret = new JSONObject();
        ret.put("request", "/hyjf-app/appUser/updatePasswordAction");
        // 版本号
        String version = request.getParameter("version");
        // 网络状态
        String netStatus = request.getParameter("netStatus");
        // 平台
        String platform = request.getParameter("platform");
        // 唯一标识
        String sign = request.getParameter("sign");
        // token
        String token = request.getParameter("token");
        // 随机字符串
        String randomString = request.getParameter("randomString");
        // Order
        String order = request.getParameter("order");
        // 新密码
        String newPassword = request.getParameter("newPassword");
        // 旧密码
        String oldPassword = request.getParameter("oldPassword");
        UserVO userVO = passWordService.getUsersById(userId);
        CheckUtil.check(null != userVO && null != userVO.getUserId(), MsgEnum.STATUS_CE000006);
        passWordService.appCheckParam(key, userVO, version, netStatus, platform, sign, token, randomString, order, newPassword, oldPassword);
        // 获取登录信息
        newPassword = DES.decodeValue(key, newPassword);
        try {
            boolean success = passWordService.updatePassWd(userVO, newPassword);
            if (success) {
                //如果修改密码成功或者重置密码就将登陆密码错误次数的key删除
                RedisUtils.del(RedisConstants.PASSWORD_ERR_COUNT_APP + userVO.getUsername());
                ret.put("status", "0");
                ret.put("statusDesc", "修改密码成功");
            } else {
                ret.put("status", "1");
                ret.put("statusDesc", "修改密码失败");
            }
        } catch (Exception e) {
            ret.put("status", "1");
            ret.put("statusDesc", "修改密码发生错误");
        }
        return ret;
    }

    /**
     * 设置交易密码
     * @param token
     * @param sign
     * @param request
     * @return
     */
    @ApiOperation(value = "设置交易密码", notes = "设置交易密码")
    @GetMapping(value = "/hyjf-app/bank/user/transpassword/setPassword")
    public ModelAndView setPassword(@RequestHeader(value = "token") String token,@RequestHeader(value = "sign") String sign,HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView = new ModelAndView("/jumpHTML");
        BaseMapBean baseMapBean=new BaseMapBean();
        Map<String,Object> map = passWordService.checkStatus(token,sign);
        if (null!=map&&map.get("baseMapBean")!=null){
            modelAndView.addObject("callBackForm", map.get("baseMapBean"));
            return modelAndView;
        }
        UserVO user = (UserVO) map.get("user");
        int userId = user.getUserId();
        UserInfoVO usersInfo= passWordService.getUserInfo(userId);
        BankOpenAccountVO bankOpenAccount = passWordService.getBankOpenAccount(userId);
        // 同步调用路径
        String retUrl = systemConfig.getAppHost() + request.getContextPath() +  CommonConstant.REQUEST_MAPPING
                + CommonConstant.RETURL_SYN_PASSWORD_ACTION ;
        // 异步调用路
        String bgRetUrl = systemConfig.getAppHost() + request.getContextPath() +  CommonConstant.REQUEST_MAPPING
                + CommonConstant.RETURN_ASY_PASSWORD_ACTION;
        // 调用设置密码接口
        BankCallBean bean = new BankCallBean();
        bean.setTxCode(BankCallConstant.TXCODE_PASSWORD_SET);
        bean.setChannel(BankCallConstant.CHANNEL_APP);
        if(user.getUserType() == 1){
            //企业用户 传组织机构代码
            CorpOpenAccountRecordVO record= passWordService.getCorpOpenAccountRecord(userId);
            // 证件类型 20：其他证件（组织机构代码）25：社会信用号
            bean.setIdType(record.getCardType() != null ? String.valueOf(record.getCardType()) : BankCallConstant.ID_TYPE_COMCODE);
            bean.setIdNo(record.getBusiCode());
            bean.setName(record.getBusiName());
        }else{
            bean.setIdType(BankCallConstant.ID_TYPE_IDCARD);
            bean.setIdNo(usersInfo.getIdcard());
            bean.setName(usersInfo.getTruename());
        }
        bean.setAccountId(String.valueOf(bankOpenAccount.getAccount()));
        bean.setMobile(user.getMobile());
        bean.setRetUrl(retUrl);
        bean.setNotifyUrl(bgRetUrl);
        // 商户私有域，存放开户平台,用户userId
        LogAcqResBean acqRes = new LogAcqResBean();
        acqRes.setUserId(userId);
        bean.setLogAcqResBean(acqRes);
        // 操作者ID
        bean.setLogUserId(String.valueOf(userId));
        bean.setLogBankDetailUrl(BankCallConstant.BANK_URL_PASSWORDSET);
        bean.setLogOrderId(GetOrderIdUtils.getOrderId2(userId));
        try {
            modelAndView = BankCallUtils.callApi(bean);
        } catch (Exception e) {
            logger.error("调用银行接口失败",e);
            baseMapBean.set(CustomConstants.APP_STATUS, BaseResultBeanFrontEnd.SUCCESS);
            baseMapBean.set(CustomConstants.APP_STATUS_DESC, "调用银行接口失败！");
            baseMapBean.setCallBackAction(CustomConstants.HOST+CommonConstant.JUMP_HTML_FAILED_PATH);
            modelAndView.addObject("callBackForm", baseMapBean);
        }
        return modelAndView;
    }

    /**
     * 设置交易密码同步回调
     *
     *
     * @return
     */
    @ApiOperation(value = "设置交易密码同步回调", notes = "设置交易密码同步回调")
    @GetMapping("/bank/user/transpassword/passwordReturn")
    public ModelAndView passwordReturn(@ModelAttribute BankCallBean bean) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView = new ModelAndView("/jumpHTML");
        BaseMapBean baseMapBean=new BaseMapBean();
        bean.convert();
        LogAcqResBean acqes = bean.getLogAcqResBean();
        int userId = acqes.getUserId();
        UserVO user = passWordService.getUsersById(userId);
        // 返回失败
        if (bean.getRetCode()!=null&&!BankCallConstant.RESPCODE_SUCCESS.equals(bean.getRetCode())) {
            baseMapBean.set(CustomConstants.APP_STATUS, BaseResultBeanFrontEnd.SUCCESS);
            baseMapBean.set(CustomConstants.APP_STATUS_DESC, "交易密码设置失败,失败原因：" + passWordService.getBankRetMsg(bean.getRetCode()));
            baseMapBean.setCallBackAction(CustomConstants.HOST+CommonConstant.JUMP_HTML_FAILED_PATH);
            modelAndView.addObject("callBackForm", baseMapBean);
            return modelAndView;
        }
        //判断用户是否设置了交易密码
        boolean flag = user.getIsSetPassword() == 1 ? true : false ;
        if(flag){
            baseMapBean.set(CustomConstants.APP_STATUS, BaseResultBeanFrontEnd.SUCCESS);
            baseMapBean.set(CustomConstants.APP_STATUS_DESC, "交易密码设置成功");
            baseMapBean.setCallBackAction(CustomConstants.HOST+CommonConstant.JUMP_HTML_SUCCESS_PATH);
            baseMapBean.setJumpFlag(BaseMapBean.JUMP_FLAG_NO);
            modelAndView.addObject("callBackForm", baseMapBean);
            return modelAndView;
        }
        BankOpenAccountVO bankOpenAccount = passWordService.getBankOpenAccount(userId);
        // 调用查询电子账户密码是否设置
        BankCallBean selectbean = new BankCallBean();
        selectbean.setTxCode(BankCallConstant.TXCODE_PASSWORD_SET_QUERY);
        selectbean.setChannel(BankCallConstant.CHANNEL_APP);
        selectbean.setAccountId(String.valueOf(bankOpenAccount.getAccount()));
        // 操作者ID
        selectbean.setLogUserId(String.valueOf(userId));
        selectbean.setLogOrderId(GetOrderIdUtils.getOrderId2(userId));
        selectbean.setLogClient(0);
        // 返回参数
        BankCallBean retBean = null;
        // 调用接口
        retBean = BankCallUtils.callApiBg(selectbean);

        if("1".equals(retBean.getPinFlag())){
            // 是否设置密码中间状态
            passWordService.updateUserIsSetPassword(user.getUserId());
            baseMapBean.set(CustomConstants.APP_STATUS, BaseResultBeanFrontEnd.SUCCESS);
            baseMapBean.set(CustomConstants.APP_STATUS_DESC, "交易密码设置成功");
            baseMapBean.setCallBackAction(CustomConstants.HOST+CommonConstant.JUMP_HTML_SUCCESS_PATH);
            baseMapBean.setJumpFlag(BaseMapBean.JUMP_FLAG_NO);
            modelAndView.addObject("callBackForm", baseMapBean);
            return modelAndView;
        }
        baseMapBean.set(CustomConstants.APP_STATUS, BaseResultBeanFrontEnd.SUCCESS);
        baseMapBean.set(CustomConstants.APP_STATUS_DESC, "交易密码设置失败");
        baseMapBean.setCallBackAction(CustomConstants.HOST+CommonConstant.JUMP_HTML_FAILED_PATH);
        modelAndView.addObject("callBackForm", baseMapBean);
        return modelAndView;
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
    @PostMapping(value = "/bank/user/transpassword/passwordBgreturn")
    public String passwordBgreturn(@ModelAttribute BankCallBean bean) {
        BankCallResult result = new BankCallResult();
        bean.convert();
        LogAcqResBean acqes = bean.getLogAcqResBean();
        int userId = acqes.getUserId();
        // 查询用户开户状态
        UserVO user = passWordService.getUsersById(userId);
        // 成功或审核中
        if (user != null && BankCallConstant.RESPCODE_SUCCESS.equals(bean.get(BankCallConstant.PARAM_RETCODE))) {
            try {
                // 开户后保存相应的数据以及日志
                passWordService.updateUserIsSetPassword(user.getUserId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        result.setMessage("交易密码设置成功");
        result.setStatus(true);
        return JSONObject.toJSONString(result, true);
    }

    /**
     * 重置交易密码
     * @param token
     * @param sign
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(value = "重置交易密码")
    @GetMapping(value = "/hyjf-app/bank/user/transpassword/resetPassword")
    public ModelAndView resetPassword(@RequestHeader(value = "token") String token,@RequestHeader(value = "sign") String sign,HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView = new ModelAndView("/jumpHTML");
        BaseMapBean baseMapBean=new BaseMapBean();
        Map<String,Object> map = passWordService.checkStatus(token,sign);
        if (null!=map){
            modelAndView.addObject("callBackForm", map.get("baseMapBean"));
            return modelAndView;
        }
        UserVO user = (UserVO) map.get("user");
        int userId = user.getUserId();
        BankOpenAccountVO bankOpenAccount = passWordService.getBankOpenAccount(userId);
        UserInfoVO usersInfo= passWordService.getUserInfo(userId);
        // 同步调用路径
        String retUrl = systemConfig.getAppHost() + request.getContextPath() +  CommonConstant.REQUEST_MAPPING
                + CommonConstant.RETURL_SYN_RESETPASSWORD_ACTION;
        // 异步调用路
        String bgRetUrl = systemConfig.getAppHost() + request.getContextPath() +  CommonConstant.REQUEST_MAPPING
                + CommonConstant.RETURN_ASY_RESETPASSWORD_ACTION;
        // 调用设置密码接口
        BankCallBean bean = new BankCallBean();
        bean.setTxCode(BankCallConstant.TXCODE_PASSWORD_RESET);
        bean.setChannel(BankCallConstant.CHANNEL_APP);
        if(user.getUserType() == 1){
            CorpOpenAccountRecordVO record= passWordService.getCorpOpenAccountRecord(userId);
            // 证件类型 20：其他证件（组织机构代码）25：社会信用号
            bean.setIdType(record.getCardType() != null ? String.valueOf(record.getCardType()) : BankCallConstant.ID_TYPE_COMCODE);
            bean.setIdNo(record.getBusiCode());
            bean.setName(record.getBusiName());
        }else{
            bean.setIdType(BankCallConstant.ID_TYPE_IDCARD);
            bean.setIdNo(usersInfo.getIdcard());
            bean.setName(usersInfo.getTruename());
        }
        bean.setAccountId(String.valueOf(bankOpenAccount.getAccount()));
        bean.setMobile(user.getMobile());
        bean.setRetUrl(retUrl);
        bean.setNotifyUrl(bgRetUrl);
        // 商户私有域，存放开户平台,用户userId
        LogAcqResBean acqRes = new LogAcqResBean();
        acqRes.setUserId(userId);
        bean.setLogAcqResBean(acqRes);
        // 操作者ID
        bean.setLogUserId(String.valueOf(userId));
        bean.setLogBankDetailUrl(BankCallConstant.BANK_URL_MOBILE);
        bean.setLogOrderId(GetOrderIdUtils.getOrderId2(userId));
        bean.setLogOrderDate(GetOrderIdUtils.getOrderDate());
        try {
            modelAndView = BankCallUtils.callApi(bean);
        } catch (Exception e) {
            logger.error("调用银行接口失败!",e);
            baseMapBean.set(CustomConstants.APP_STATUS, BaseResultBeanFrontEnd.SUCCESS);
            baseMapBean.set(CustomConstants.APP_STATUS_DESC, "调用银行接口失败！");
            baseMapBean.setCallBackAction(CustomConstants.HOST+CommonConstant.JUMP_HTML_FAILED_PATH);
            modelAndView.addObject("callBackForm", baseMapBean);
        }
        return modelAndView;
    }


    /**
     * 重置交易密码同步回调
     *
     * @param
     * @param
     * @return
     */
    @ApiOperation(value = "重置交易密码同步回调")
    @GetMapping(value = "/bank/user/transpassword/resetPasswordReturn")
    public ModelAndView resetPasswordReturn(@ModelAttribute BankCallBean bean) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView = new ModelAndView("/jumpHTML");
        BaseMapBean baseMapBean=new BaseMapBean();
        // 返回失败
        if (bean.getRetCode()!=null&&!BankCallConstant.RESPCODE_SUCCESS.equals(bean.getRetCode())) {

            baseMapBean.set(CustomConstants.APP_STATUS, BaseResultBeanFrontEnd.SUCCESS);
            baseMapBean.set(CustomConstants.APP_STATUS_DESC, "交易密码修改失败,失败原因：" + passWordService.getBankRetMsg(bean.getRetCode()));
            baseMapBean.setCallBackAction(CustomConstants.HOST+CommonConstant.JUMP_HTML_FAILED_PATH);
            modelAndView.addObject("callBackForm", baseMapBean);
            return modelAndView;
        }
        baseMapBean.set(CustomConstants.APP_STATUS, BaseResultBeanFrontEnd.SUCCESS);
        baseMapBean.set(CustomConstants.APP_STATUS_DESC, "交易密码修改成功");
        baseMapBean.setCallBackAction(CustomConstants.HOST+CommonConstant.JUMP_HTML_SUCCESS_PATH);
        baseMapBean.setJumpFlag(BaseMapBean.JUMP_FLAG_NO);
        modelAndView.addObject("callBackForm", baseMapBean);
        return modelAndView;
    }

    /**
     * 重置交易密码异步回调
     *
     * @param
     * @param
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "重置交易密码异步回调")
    @PostMapping(value = "/bank/user/transpassword/resetPasswordBgreturn")
    public String resetPasswordBgreturn(@ModelAttribute BankCallBean bean) {
        BankCallResult result = new BankCallResult();
        result.setMessage("交易密码修改成功");
        result.setStatus(true);
        return JSONObject.toJSONString(result, true);
    }


    /**
     * 找回密码(重置密码)
     *
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "找回密码",notes = "找回密码")
    @PostMapping(value = "/hyjf-app/appUser/getBackPasswordAction")
    public JSONObject getBackPasswordAction(@RequestHeader(value = "key") String key,HttpServletRequest request, HttpServletResponse response) {
        JSONObject ret = new JSONObject();
        ret.put("request", "/hyjf-app/appUser/getBackPasswordAction");

        // 版本号
        String version = request.getParameter("version");
        // 网络状态
        String netStatus = request.getParameter("netStatus");
        // 平台
        String platform = request.getParameter("platform");
        // 唯一标识
        String sign = request.getParameter("sign");
        // 随机字符串
        String randomString = request.getParameter("randomString");
        // Order
        String order = request.getParameter("order");

        // 新密码
        String newPassword = request.getParameter("newPassword");
        // 验证方式
        String verificationCode = request.getParameter("verificationCode");
        // 手机号
        String mobile = request.getParameter("mobile");

        // 检查参数正确性
        if (Validator.isNull(version) || Validator.isNull(netStatus) || Validator.isNull(platform) || Validator.isNull(sign) || Validator.isNull(randomString) || Validator.isNull(order) || Validator.isNull(mobile) || Validator.isNull(verificationCode) || Validator.isNull(newPassword)) {
            ret.put("status", "1");
            ret.put("statusDesc", "请求参数非法");
            return ret;
        }
        // 取得加密用的Key
        if (Validator.isNull(key)) {
            ret.put("status", "1");
            ret.put("statusDesc", "请求参数非法");
            return ret;
        }
        // 业务逻辑
        try {
            // 根据手机号取得用户ID
            UserVO user = passWordService.getUsersByMobile(mobile);
            if (user != null) {
                if (Validator.isNull(mobile)) {
                    ret.put("status", "1");
                    ret.put("statusDesc", "手机号不能为空");
                    return ret;
                }
                if (!Validator.isMobile(mobile)) {
                    ret.put("status", "1");
                    ret.put("statusDesc", "请输入您的真实手机号码");
                    return ret;
                }
                if (Validator.isNull(verificationCode)) {
                    ret.put("status", "1");
                    ret.put("statusDesc", "验证码不能为空");
                    return ret;
                }
                if (Validator.isNull(newPassword)) {
                    ret.put("status", "1");
                    ret.put("statusDesc", "密码不能为空");
                    return ret;
                }
                if (newPassword.length() < 6 || newPassword.length() > 16) {
                    ret.put("status", "1");
                    ret.put("statusDesc", "密码长度6-16位");
                    return ret;
                }
                boolean hasNumber = false;

                for (int i = 0; i < newPassword.length(); i++) {
                    if (Validator.isNumber(newPassword.substring(i, i + 1))) {
                        hasNumber = true;
                        break;
                    }
                }
                if (!hasNumber) {
                    ret.put("status", "1");
                    ret.put("statusDesc", "密码必须包含数字");
                    return ret;
                }

                String regEx = "^[a-zA-Z0-9]+$";
                Pattern p = Pattern.compile(regEx);
                Matcher m = p.matcher(newPassword);
                if (!m.matches()) {
                    ret.put("status", "1");
                    ret.put("statusDesc", "密码必须由数字和字母组成，如abc123");
                    return ret;
                }
                String verificationType = CommonConstant.PARAM_TPL_ZHAOHUIMIMA;
                int cnt = passWordService.updateCheckMobileCode(mobile, verificationCode, verificationType, platform, CommonConstant.CKCODE_YIYAN, CommonConstant.CKCODE_USED,true);
                if (cnt == 0) {
                    ret.put("status", "1");
                    ret.put("statusDesc", "验证码无效");
                    return ret;
                }

                boolean success = passWordService.updatePassWd(user, newPassword);

                if (success) {
                    //如果修改密码成功或者重置密码就将登陆密码错误次数的key删除
                    RedisUtils.del(RedisConstants.PASSWORD_ERR_COUNT_APP+mobile);
                    ret.put("status", "0");
                    ret.put("statusDesc", "找回密码成功");
                } else {
                    ret.put("status", "1");
                    ret.put("statusDesc", "找回密码失败");
                }
            } else {
                ret.put("status", "1");
                ret.put("statusDesc", "用户信息不存在");
            }
        } catch (Exception e) {
            ret.put("status", "1");
            ret.put("statusDesc", "找回密码发生错误");
        }
        return ret;
    }

}