/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.api.user.autologin;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.security.util.RSA_Hjs;
import com.hyjf.common.security.util.SignUtil;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.common.util.ApiSignUtil;
import com.hyjf.cs.user.bean.ApiResultPageBean;
import com.hyjf.cs.user.bean.ApiSkipFormBean;
import com.hyjf.cs.user.bean.ApiUserPostBean;
import com.hyjf.cs.user.bean.NmcfLoginRequestBean;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.login.LoginService;
import com.hyjf.cs.user.util.GetCilentIP;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: sunpeikai
 * @version: ApiUserAutoLoginController, v0.1 2018/8/23 10:37
 */
@Api(value = "api端-第三方用户自动登录",tags = "api端-第三方用户自动登录")
@Controller
@RequestMapping(value = "/hyjf-api/api/user")
public class ApiUserAutoLoginController extends BaseUserController {

    @Autowired
    private LoginService loginService;
    @Autowired
    private SystemConfig systemConfig;


    @RequestMapping(value = "/thirdLoginApi")
    public ModelAndView thirdLoginApi(ApiUserPostBean bean){
        // 设置接口结果页的信息（返回Url）
        this.initCheckUtil(bean);

        // 验证
        checkPostBeanOfWeb(bean);

        ModelAndView modelAndView = new ModelAndView("/bank/bank_send");
        ApiSkipFormBean apiSkipFormBean = new ApiSkipFormBean();
        String returl = systemConfig.webHost + "/hyjf-web/api/user/thirdLogin.do";

        apiSkipFormBean.setAction(returl);
        apiSkipFormBean.set("bindUniqueIdScy", bean.getBindUniqueIdScy());
        apiSkipFormBean.set("pid", bean.getPid()+"");
        apiSkipFormBean.set("retUrl", bean.getRetUrl());
        apiSkipFormBean.set("timestamp", String.valueOf(bean.getTimestamp()));
        apiSkipFormBean.set("chkValue", bean.getChkValue());
        modelAndView.addObject("bankForm", apiSkipFormBean);
        return modelAndView;
    }

    /**
     * 第三方登录
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/thirdLogin")
    public ModelAndView thirdLogin(HttpServletRequest request, HttpServletResponse response, @ModelAttribute ApiUserPostBean bean){
        logger.info("请求进来啦:【{}】", JSON.toJSONString(bean));
        // 验证
        checkPostBeanOfWeb(bean);
        // 验签
        //apiCommonService.checkSign(bean);
        CheckUtil.check(SignUtil.checkSignDefaultKey(bean.getChkValue(), bean.getBindUniqueIdScy(),bean.getPid(),bean.getRetUrl(), bean.getTimestamp()), MsgEnum.ERR_SIGN);
        // 解密
        int bindUniqueId = bindUniqueIdDecrypt(bean);
        // 查询Userid
        Integer userId = loginService.getUserIdByBind(bindUniqueId, bean.getPid());
        //Integer userId = bean.getUserId();
        // 登陆
        UserVO userVO = loginService.getUsersById(userId);
        loginService.loginOperationOnly(userVO,userVO.getUsername(),GetCilentIP.getIpAddr(request), BankCallConstant.CHANNEL_APP);

/*        WebViewUser webUser = loginService.getWebViewUserByUserId(userId);
        WebUtils.sessionLogin(request, response, webUser);*/

        // 返回到hyjf的系统
        return new ModelAndView("redirect:" + systemConfig.webHost + "/hyjf-web/user/pandect");
    }

    @ApiOperation(value = "纳觅财富自动登录",notes = "纳觅财富自动登录")
    @PostMapping(value = "/nmcfThirdLogin.do")
    public ModelAndView nmcfThirdLogin(HttpServletResponse response,HttpServletRequest httpServletRequest,@ModelAttribute NmcfLoginRequestBean request){

        // 验证
        //this.checkNmcfPostBean(request);
        // 验签
        //String sign = request.getInstCode() + request.getUserId() + (request.getBorrowNid()==null?"":request.getBorrowNid()) + request.getTimestamp() + request.getInstCode();
        //CheckUtil.check(ApiSignUtil.verifyByRSA(request.getInstCode(), request.getChkValue(), sign), MsgEnum.ERR_SIGN);
        // userId 解密
        //int nmUserId = this.userIdDecrypt(request);
        // 查询userid
        //Integer userId = loginService.getUserIdByBind(nmUserId, Integer.valueOf(request.getInstCode()));
        Integer userId = Integer.valueOf(request.getUserId());
        // userid不存在,跳转登录页面
        if(userId == null) {
            return new ModelAndView("redirect:" + systemConfig.webHost + "/hyjf-web/user/login/init.do");
        }
        //TODO:登录以后，前端页面还是未登录状态
        // 登陆
        UserVO userVO = loginService.getUsersById(userId);
        logger.info("userId:[{}],userName:[{}],password:[{}]",userId,userVO.getUsername(),userVO.getPassword());
        WebViewUserVO webViewUserVO = loginService.loginOperationOnly(userVO,userVO.getUsername(),GetCilentIP.getIpAddr(httpServletRequest), BankCallConstant.CHANNEL_PC);
        //WebViewUser webUser = loginService.getWebViewUserByUserId(userId);
        //WebUtils.sessionLogin(request, response, webUser);
        String token = webViewUserVO.getToken();
        logger.info("用户登录生成的token::[{}]",token);
        response.setHeader("token",token);
        // 先跳转纳觅传过来的url
        if (request.getRetUrl() != null) {
            logger.info("retUrl");
            return new ModelAndView("redirect:" + request.getRetUrl());
        } else {
            // 如果纳觅没传url,有borrowNid,跳标的详情,无borowNid,跳个人中心
            if (request.getBorrowNid() == null || "".equals(request.getBorrowNid())) {
                logger.info("pandect");
                return new ModelAndView("redirect:https://frontweb1.hyjf.com/user/pandect?token=" + token);
            } else {
                // 跳转到前端的标的详情
                logger.info("borrowDetail");
                //return new ModelAndView("redirect:" + systemConfig.webHost + "/hyjf-web/projectlist/getBorrowDetail?borrowNid=" + request.getBorrowNid());
                return new ModelAndView("redirect:https://frontweb1.hyjf.com/borrow/borrowDetail?borrowNid=" + request.getBorrowNid() + "&token=" + token);

            }
        }

    }

    /**
     * 错误页跳转用，初期化结果页数据（错误信息除外）
     * @return
     * @author liubin
     */
    protected void initCheckUtil(ApiUserPostBean apiUserPostBean) {
        // 结果页FormBean赋值
        ApiResultPageBean apiResultPageBean = new ApiResultPageBean();
        apiResultPageBean.setRetUrl(apiUserPostBean.getRetUrl());
        apiResultPageBean.setButMes("返回");
        // 结果页FormBean传入CheckUtil
        CheckUtil.setData(apiResultPageBean);
    }

    /**
     * 传入信息验证,汇晶社自动登录、绑定
     * @param bean
     */
    public void checkPostBeanOfWeb(ApiUserPostBean bean) {
        //传入信息验证
        CheckUtil.check(Validator.isNotNull(bean.getBindUniqueIdScy()), MsgEnum.ERR_OBJECT_REQUIRED, "bindUniqueIdScy");
        CheckUtil.check(Validator.isNotNull(bean.getChkValue()), MsgEnum.ERR_OBJECT_REQUIRED, "chkValue");
        CheckUtil.check(Validator.isNotNull(bean.getPid()), MsgEnum.ERR_OBJECT_REQUIRED, "pid");
        CheckUtil.check(Validator.isNotNull(bean.getRetUrl()), MsgEnum.ERR_OBJECT_REQUIRED, "retUrl");
        CheckUtil.check(Validator.isNotNull(bean.getTimestamp()), MsgEnum.ERR_OBJECT_REQUIRED, "timestamp");
    }
    /**
     * 请求参数校验
     * @auth sunpeikai
     * @param
     * @return
     */
    private void checkNmcfPostBean(NmcfLoginRequestBean bean) {
        //传入信息验证
        CheckUtil.check(Validator.isNotNull(bean.getTimestamp()), MsgEnum.ERR_OBJECT_REQUIRED, "时间戳");
        CheckUtil.check(Validator.isNotNull(bean.getInstCode()),  MsgEnum.ERR_OBJECT_REQUIRED, "机构编号");
        CheckUtil.check(Validator.isNotNull(bean.getUserId()),  MsgEnum.ERR_OBJECT_REQUIRED, "用户ID");
        CheckUtil.check(Validator.isNotNull(bean.getChkValue()),  MsgEnum.ERR_OBJECT_REQUIRED, "签名");
    }

    /**
     * 用户id解密
     * @auth sunpeikai
     * @param
     * @return
     */
    private int bindUniqueIdDecrypt(ApiUserPostBean bean){
        // RAC解密
        String str = decrypt(bean.getBindUniqueIdScy());
        // 解密结果数字验证
        CheckUtil.check(Validator.isDigit(str), MsgEnum.ERR_OBJECT_DIGIT, "bindUniqueIdScy");
        // 返回
        return Integer.parseInt(str);
    }

    /**
     * 用户id解密
     * @auth sunpeikai
     * @param
     * @return
     */
    private int userIdDecrypt(NmcfLoginRequestBean bean){
        // RAC解密
        String str = decrypt(bean.getUserId());
        // 解密结果数字验证
        CheckUtil.check(Validator.isDigit(str), MsgEnum.ERR_OBJECT_DIGIT, "userId");
        // 返回
        return Integer.parseInt(str);
    }

    private String decrypt(String str) {
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        try {
            return RSA_Hjs.decrypt(str,"utf-8",systemConfig.getPublickeyhjs());
        } catch (Exception e) {
            CheckUtil.check(false, MsgEnum.ERR_OBJECT_DECRYPT,"用户ID");
        }
        return null;

    }
}
