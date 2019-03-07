/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.api.surong.user.password;

import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.bank.LogAcqResBean;
import com.hyjf.common.security.util.MD5;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.cs.user.bean.RetranspasswordResultBean;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.password.PassWordService;
import com.hyjf.cs.user.service.password.TransPasswordService;
import com.hyjf.cs.user.service.register.RegisterService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author libin
 * @version TransPasswordController.java, v0.1 2018年7月19日 上午9:17:39
 */
@Api(value = "api端-融东风密码设置接口",tags = "api端-融东风密码设置接口")
@Controller
@RequestMapping("hyjf-api/surong/user/transpassword")
public class TransPasswordController extends BaseUserController{
	
	public static final String REQUEST_MAPPING = "hyjf-api/surong/user/transpassword";
	
	/** 设置交易密码同步回调 */
    public static final String RETURL_SYN_PASSWORD_ACTION = "/passwordReturn";
    
    /** 设置交易密码异步回调 */
    public static final String RETURN_ASY_PASSWORD_ACTION = "/passwordBgreturn";
    
    /** 修改交易密码同步回调 */
    public static final String RETURL_SYN_RESETPASSWORD_ACTION = "/resetPasswordReturn";
    
    /** 修改交易密码异步回调 */
    public static final String RETURN_ASY_RESETPASSWORD_ACTION = "/resetPasswordBgreturn";

    @Autowired
    private TransPasswordService transPasswordService;
    
	@Autowired
    RegisterService registService;
	
    @Autowired
    PassWordService passWordService;
    
    @Autowired
    SystemConfig systemConfig;
    
    /**
     * 设置交易密码(参照原代码继续返回 ModelAndView 而不是微服务的 jason )
     * @param request
     * @param
     * @return
     */
    @ApiOperation(value = "融东风用户设置交易密码", notes = "融东风用户设置交易密码")
    @PostMapping("/setPassword")
    public ModelAndView setPassword(HttpServletRequest request, HttpServletResponse response) {
    	logger.info("设置交易密码 start");
    	ModelAndView modelAndView = new ModelAndView();
    	// 从request获取 sign验证值
    	String sign = request.getParameter("sign");
        if (StringUtils.isEmpty(sign)) {
            modelAndView = new ModelAndView("/bank/user/personalsetting/error");//TransPasswordDefine.PASSWORD_ERROR_PATH
            modelAndView.addObject("message", "sign验证值为空！");
            return modelAndView;
        }
        String mobile = request.getParameter("mobile");
        //public static final String AOP_INTERFACE_ACCESSKEY = "aop.interface.accesskey";
        // 原 从配置文件中获取 服务接口秘钥 String accessKey = PropUtils.getSystem(ApplyDefine.AOP_INTERFACE_ACCESSKEY);
        // 从配置文件中获取 服务接口秘钥
        String accessKey = systemConfig.getAopAccesskey();
        String miwen = MD5.toMD5Code(accessKey + mobile + accessKey);
        if (!miwen.equals(sign)) {
        	modelAndView = new ModelAndView("/bank/user/personalsetting/error");//TransPasswordDefine.PASSWORD_ERROR_PATH
            modelAndView.addObject("message", "sign值验证失败！");
            return modelAndView;
        }
        UserVO user = transPasswordService.findUserByMobile(mobile);
        if (user == null) {
        	modelAndView = new ModelAndView("/bank/user/personalsetting/error");//TransPasswordDefine.PASSWORD_ERROR_PATH
            modelAndView.addObject("message", mobile + "用户不存在汇盈金服账户！");
            return modelAndView;
        }
        Integer userId = user.getUserId();
        // 未开户
        if (user.getBankOpenAccount().intValue() != 1) {
        	modelAndView = new ModelAndView("/bank/user/personalsetting/error");//TransPasswordDefine.PASSWORD_ERROR_PATH
            modelAndView.addObject("message", "用户未开户！");
            return modelAndView;
        }
        // 判断用户是否设置过交易密码
        Integer passwordFlag = user.getIsSetPassword();
        // 已设置交易密码
        if (passwordFlag == 1) {
        	modelAndView = new ModelAndView("/bank/user/personalsetting/error");//TransPasswordDefine.PASSWORD_ERROR_PATH
            modelAndView.addObject("message", "已设置交易密码");
            return modelAndView;
        }
        // 查询客户信息
        UserInfoVO usersInfo = registService.getUserInfo(userId);
        // 查询用户银行信息
        BankOpenAccountVO bankOpenAccount = registService.getBankOpenAccount(userId);
        // 原 同步调用路径
        // String retUrl = PropUtils.getSystem(CustomConstants.HYJF_WEB_URL) + request.getContextPath()
        // + TransPasswordDefine.REQUEST_MAPPING + TransPasswordDefine.RETURL_SYN_PASSWORD_ACTION + ".do";
        
        // 现 同步调用路径
        String retUrl = systemConfig.getFrontHost() + request.getContextPath() + REQUEST_MAPPING + RETURL_SYN_PASSWORD_ACTION;
        
        // 原 异步调用路
        // String bgRetUrl = PropUtils.getSystem(CustomConstants.HYJF_WEB_URL) + request.getContextPath()
        // + TransPasswordDefine.REQUEST_MAPPING + TransPasswordDefine.RETURN_ASY_PASSWORD_ACTION + ".do";
        
        // 现 同步调用路径
        String bgRetUrl = systemConfig.getServerHost() + request.getContextPath() + REQUEST_MAPPING + RETURN_ASY_PASSWORD_ACTION;
        
        // 调用设置密码接口
        BankCallBean bean = new BankCallBean();
        // 接口版本号
        bean.setVersion(BankCallConstant.VERSION_10);
        // 消息类型(用户开户)
        bean.setTxCode(BankCallConstant.TXCODE_PASSWORD_RESET_PAGE);
        // 机构代码
        bean.setInstCode(systemConfig.getBankInstcode());
        bean.setBankCode(systemConfig.getBankCode());
        bean.setTxDate(GetOrderIdUtils.getTxDate());
        bean.setTxTime(GetOrderIdUtils.getTxTime());
        bean.setSeqNo(GetOrderIdUtils.getSeqNo(6));
        bean.setChannel(BankCallConstant.CHANNEL_APP);
        bean.setIdType(BankCallConstant.ID_TYPE_IDCARD);
        // 电子账号
        bean.setAccountId(String.valueOf(bankOpenAccount.getAccount()));
        bean.setIdNo(usersInfo.getIdcard());
        bean.setName(usersInfo.getTruename());
        bean.setMobile(user.getMobile());
        // 页面同步返回 URL
        bean.setRetUrl(retUrl);
        // 页面异步返回URL(必须)
        bean.setNotifyUrl(bgRetUrl);
        // 商户私有域，存放开户平台,用户userId
        LogAcqResBean acqRes = new LogAcqResBean();
        acqRes.setUserId(userId);
        bean.setLogAcqResBean(acqRes);
        // 操作者ID
        bean.setLogUserId(String.valueOf(userId));
        bean.setLogBankDetailUrl(BankCallConstant.BANK_URL_PASSWORDRESETPAGE);
        bean.setLogOrderId(GetOrderIdUtils.getUsrId(userId));
        // 跳转到汇付天下画面
        try {
            modelAndView = BankCallUtils.callApi(bean);
        } catch (Exception e) {
            logger.error(e.getMessage());
            modelAndView = new ModelAndView("/bank/user/personalsetting/error");//TransPasswordDefine.PASSWORD_ERROR_PATH
            modelAndView.addObject("message", "调用银行接口失败！");
        }
        logger.info("设置交易密码end");
        return modelAndView;
    }

    /**
     * 设置交易密码同步回调(参照原代码继续返回 ModelAndView 而不是微服务的 jason )
     * @param request
     * @param form
     * @return
     */
    @ApiOperation(value = "设置交易密码同步回调", notes = "设置交易密码同步回调")
    @PostMapping("/passwordReturn")
    public ModelAndView passwordReturn(HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute BankCallBean bean) {
    	logger.info("设置交易密码同步回调start");
    	ModelAndView modelAndView = new ModelAndView("/callback/callback_transpassword");//ApplyDefine.CALL_BACK_TRANSPASSWORD_VIEW
    	bean.convert();
    	RetranspasswordResultBean repwdResult = new RetranspasswordResultBean();
        repwdResult.setCallBackAction(systemConfig.getRetransPassword());
        //String accessKey = PropUtils.getSystem(ApplyDefine.AOP_INTERFACE_ACCESSKEY);
        String accessKey = systemConfig.getAopAccesskey();
        LogAcqResBean acqes = bean.getLogAcqResBean();
        int userId = acqes.getUserId();
        UserVO user = this.passWordService.getUsersById(userId);
        String miwen = MD5.toMD5Code(accessKey + user.getMobile() + accessKey);
        repwdResult.set("mobile", user.getMobile());
        // 查询开户状态
        BankOpenAccountVO bankOpenAccount = registService.getBankOpenAccount(userId);
        // 调用查询电子账户密码是否设置
        BankCallBean selectbean = new BankCallBean();
        // 接口版本号
        selectbean.setVersion(BankCallConstant.VERSION_10);
        selectbean.setTxCode(BankCallConstant.TXCODE_PASSWORD_SET_QUERY);
        // 机构代码
        selectbean.setInstCode(systemConfig.getBankInstcode());
        selectbean.setBankCode(systemConfig.getBankCode());
        selectbean.setTxDate(GetOrderIdUtils.getTxDate());
        selectbean.setTxTime(GetOrderIdUtils.getTxTime());
        selectbean.setSeqNo(GetOrderIdUtils.getSeqNo(6));
        selectbean.setChannel(BankCallConstant.CHANNEL_PC);
        // 电子账号
        selectbean.setAccountId(String.valueOf(bankOpenAccount.getAccount()));
        // 操作者ID
        selectbean.setLogUserId(String.valueOf(userId));
        selectbean.setLogOrderId(GetOrderIdUtils.getUsrId(userId));
        selectbean.setLogClient(0);
        // 返回参数
        BankCallBean retBean = null;
        // 调用接口
        retBean = BankCallUtils.callApiBg(selectbean);
        if ("1".equals(retBean.getPinFlag())) {
            // 是否设置密码中间状态--将 IsSetPassword 字段设置为1 0：未设置 1：已设置
            this.passWordService.updateUserIsSetPassword(user.getUserId());
            repwdResult.set("status", "1");
            repwdResult.set("sign", miwen);
        } else {
            // 充值失败
            repwdResult.set("sign", miwen);
            modelAndView.addObject("message", "交易密码设置失败,失败原因：" + passWordService.getBankRetMsg(bean.getRetCode()));
            repwdResult.set("status", "0");
        }
        repwdResult.set("way", "同步");
        modelAndView.addObject("callBackForm", repwdResult);
        logger.info("设置交易密码同步回调end");
        return modelAndView;
    }
    
    /**
     * 设置交易密码异步回调
     *
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "设置交易密码异步回调", notes = "设置交易密码异步回调")
    @PostMapping("/passwordBgreturn")
    public Object passwordBgreturn(HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute BankCallBean bean) {
    	logger.info("设置交易密码异步回调start");
        BankCallResult result = new BankCallResult();   
        bean.convert();
        LogAcqResBean acqes = bean.getLogAcqResBean();
        int userId = acqes.getUserId();
        // 查询用户开户状态
        UserVO user = this.passWordService.getUsersById(userId);
        // 成功或审核中
        if (user != null && BankCallConstant.RESPCODE_SUCCESS.equals(bean.get(BankCallConstant.PARAM_RETCODE))) {
            try {
                // 开户后保存相应的数据以及日志
            	this.passWordService.updateUserIsSetPassword(user.getUserId());
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        logger.info("设置交易密码异步回调end");
        result.setMessage("交易密码设置成功");
        result.setStatus(true);
        return result; 
    }
	
    
    /**
     * 修改交易密码(参照原代码继续返回 ModelAndView 而不是微服务的 jason )
     * @param request
     * @param form
     * @return
     */
    @ApiOperation(value = "融东风用户修改交易密码", notes = "融东风用户修改交易密码")
    @PostMapping("/resetPassword")
    public ModelAndView resetPassword(HttpServletRequest request, HttpServletResponse response) {
    	logger.info("修改交易密码 start");
    	ModelAndView modelAndView = new ModelAndView();
        String sign = request.getParameter("sign");
        if (StringUtils.isEmpty(sign)) {
        	modelAndView = new ModelAndView("/bank/user/personalsetting/error");//TransPasswordDefine.PASSWORD_ERROR_PATH
            modelAndView.addObject("message", "sign验证值为空！");
            return modelAndView;
        }
        //获取miwen
        String miwen = request.getParameter("miwen");
        String mobile = request.getParameter("mobile");
        UserVO user = transPasswordService.findUserByMobile(mobile);
        Integer userId = user.getUserId();
        if(userId==null||userId<=0){
        	modelAndView = new ModelAndView("/bank/user/personalsetting/error");//TransPasswordDefine.PASSWORD_ERROR_PATH
            modelAndView.addObject("message", "用户未登录");
            return modelAndView; 
        }
        if (user.getBankOpenAccount().intValue() != 1) {//未开户
        	modelAndView = new ModelAndView("/bank/user/personalsetting/error");//TransPasswordDefine.PASSWORD_ERROR_PATH
            modelAndView.addObject("message", "用户未开户！");
            return modelAndView;
        }
        //验证sign值
        String accessKey = systemConfig.getAopAccesskey();
        String miwenNew = MD5.toMD5Code(accessKey + mobile + accessKey);
        if (!miwen.equals(miwenNew)) {
        	modelAndView = new ModelAndView("/bank/user/personalsetting/error");//TransPasswordDefine.PASSWORD_ERROR_PATH
            modelAndView.addObject("message", "sign值验证失败！");
            return modelAndView;
        }
        //判断用户是否设置过交易密码
        Integer passwordFlag = user.getIsSetPassword();
        if (passwordFlag == 0) {//未设置交易密码
        	modelAndView = new ModelAndView("/bank/user/personalsetting/error");//TransPasswordDefine.PASSWORD_ERROR_PATH
            modelAndView.addObject("message", "未设置过交易密码，请先设置交易密码");
            return modelAndView;
        }
        // 查询客户信息
        UserInfoVO usersInfo = registService.getUserInfo(userId);
        // 查询用户银行信息
        BankOpenAccountVO bankOpenAccount = registService.getBankOpenAccount(userId);
        // 原 同步调用路径
/*        String retUrl = PropUtils.getSystem(CustomConstants.HYJF_WEB_URL) + request.getContextPath() +  TransPasswordDefine.REQUEST_MAPPING
                + TransPasswordDefine.RETURL_SYN_RESETPASSWORD_ACTION + ".do";*/
        // 新 同步调用路径
        String retUrl = systemConfig.getFrontHost() + request.getContextPath() + REQUEST_MAPPING + RETURL_SYN_RESETPASSWORD_ACTION;
        // 原 异步调用路径
/*        String bgRetUrl = PropUtils.getSystem(CustomConstants.HYJF_WEB_URL) + request.getContextPath() +  TransPasswordDefine.REQUEST_MAPPING
                + TransPasswordDefine.RETURN_ASY_RESETPASSWORD_ACTION + ".do";*/
        // 新 异步调用路径
        String bgRetUrl = systemConfig.getServerHost() + request.getContextPath() + REQUEST_MAPPING + RETURN_ASY_RESETPASSWORD_ACTION;
        // 调用设置密码接口
        BankCallBean bean = new BankCallBean();
        // 接口版本号
        bean.setVersion(BankCallConstant.VERSION_10);
        // 消息类型(用户开户)
        bean.setTxCode(BankCallConstant.TXCODE_PASSWORD_RESET_PAGE);
        // 机构代码
        bean.setInstCode(systemConfig.getBankInstcode());
        bean.setBankCode(systemConfig.getBankCode());
        bean.setTxDate(GetOrderIdUtils.getTxDate());
        bean.setTxTime(GetOrderIdUtils.getTxTime());
        bean.setSeqNo(GetOrderIdUtils.getSeqNo(6));
        bean.setChannel(BankCallConstant.CHANNEL_APP);
        bean.setIdType(BankCallConstant.ID_TYPE_IDCARD);
        //电子账号
        bean.setAccountId(String.valueOf(bankOpenAccount.getAccount()));
        bean.setIdNo(usersInfo.getIdcard());
        bean.setName(usersInfo.getTruename());
        bean.setMobile(user.getMobile());
        // 页面同步返回 URL
        bean.setRetUrl(retUrl);
        // 页面异步返回URL(必须)
        bean.setNotifyUrl(bgRetUrl);
        // 商户私有域，存放开户平台,用户userId
        LogAcqResBean acqRes = new LogAcqResBean();
        acqRes.setUserId(userId);
        bean.setLogAcqResBean(acqRes);
        // 操作者ID
        bean.setLogUserId(String.valueOf(userId));
        bean.setLogBankDetailUrl(BankCallConstant.BANK_URL_PASSWORDRESETPAGE);
        bean.setLogOrderId(GetOrderIdUtils.getUsrId(userId));
        // 跳转到汇付天下画面
        try {
            modelAndView = BankCallUtils.callApi(bean);
        } catch (Exception e) {
            logger.error(e.getMessage());
            modelAndView = new ModelAndView("/bank/user/personalsetting/error");//TransPasswordDefine.PASSWORD_ERROR_PATH
            modelAndView.addObject("message", "调用银行接口失败！");
        }
        logger.info("修改交易密码 end");
        return modelAndView;
    }
    
    /**
     * 修改交易密码同步回调
     *
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(value = "修改交易密码同步回调", notes = "修改交易密码同步回调")
    @PostMapping("/resetPasswordReturn")
    public ModelAndView resetPasswordReturn(HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute BankCallBean bean) {
    	logger.info("设置交易密码同步回调start");
    	ModelAndView modelAndView = new ModelAndView("/callback/callback_transpassword");//ApplyDefine.CALL_BACK_TRANSPASSWORD_VIEW
        bean.convert();
        RetranspasswordResultBean repwdResult = new RetranspasswordResultBean();
        repwdResult.setCallBackAction(systemConfig.getResetpassword());
        String accessKey = systemConfig.getAopAccesskey();
        LogAcqResBean acqes = bean.getLogAcqResBean();
        int userId = acqes.getUserId();
        UserVO user = this.passWordService.getUsersById(userId);
        String miwen = MD5.toMD5Code(accessKey + user.getMobile() + accessKey);
        repwdResult.set("mobile", user.getMobile());
        // 返回失败
        if (bean.getRetCode()!=null&&!BankCallConstant.RESPCODE_SUCCESS.equals(bean.getRetCode())) {
        	modelAndView = new ModelAndView("/bank/user/personalsetting/error");//TransPasswordDefine.PASSWORD_ERROR_PATH
            modelAndView.addObject("message", "交易密码修改失败,失败原因：" + passWordService.getBankRetMsg(bean.getRetCode()));
            return modelAndView;
        }
        repwdResult.set("status", "1");
        repwdResult.set("sign", miwen);
        modelAndView.addObject("callBackForm", repwdResult);
        logger.info("设置交易密码同步回调end");
        return modelAndView; 
    }
    
    /**
     * 修改交易密码异步回调
     *
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "修改交易密码异步回调", notes = "修改交易密码异步回调")
    @PostMapping("/resetPasswordBgreturn")
    public Object resetPasswordBgreturn(HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute BankCallBean bean) {
        BankCallResult result = new BankCallResult();
        result.setMessage("交易密码修改成功");
        result.setStatus(true);
        return result;
    }
}
