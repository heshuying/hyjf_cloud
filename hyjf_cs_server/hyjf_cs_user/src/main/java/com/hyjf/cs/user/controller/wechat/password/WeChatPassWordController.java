/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.wechat.password;

import com.hyjf.cs.user.util.RequestUtil;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangc
 */
@Api(value = "密码相关服务")
@RestController
@RequestMapping("/wecat/user/password")
public class WeChatPassWordController {


    @Autowired
    public RequestUtil requestUtil;
    
    private static final Logger logger = LoggerFactory.getLogger(WeChatPassWordController.class);

    /**
     * 设置交易密码
     *
     * @param request
     * @param
     * @return
     */

  /*  @PostMapping(value = "/setTeaderPassword", produces = "application/json; charset=utf-8")
    public ModelAndView setPassword(@RequestHeader(value = "token", required = true) String token, HttpServletRequest request, HttpServletResponse response) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView = new ModelAndView("/jumpHTML");
        String sign = request.getParameter("sign");
        //判断用户是否登录
        Integer userId = requestUtil.getRequestUserId(request);
        if(StringUtil.isBlank(userId.toString())){
            logger.info("用户未登录");
            return getErrorModelAndView(ResultEnum.ERROR_004, sign, null, null);
        }
        //判断用户是否开户
        Users user= wxTransPasswordService.getUsers(userId);
        if (user.getBankOpenAccount().intValue() != 1) {//未开户
            logger.info("用户未开户,userId=="+userId);
            return getErrorModelAndView(ResultEnum.USER_ERROR_200, sign, null, null);
        }

        //判断用户是否设置过交易密码
        Integer passwordFlag = user.getIsSetPassword();
        if (passwordFlag == 1) {
            logger.info("用户未设置交易密码,userId=="+userId);
            //已设置交易密码
            return getErrorModelAndView(ResultEnum.USER_ERROR_206, sign, null, null);
        }

        UsersInfo usersInfo= wxTransPasswordService.getUsersInfoByUserId(userId);
        BankOpenAccount bankOpenAccount = wxTransPasswordService.getBankOpenAccount(userId);
        // 同步调用路径
        String retUrl = PropUtils.getSystem(CustomConstants.HYJF_WEB_URL) + request.getContextPath() +  WxTransPasswordDefine.REQUEST_MAPPING
                + WxTransPasswordDefine.RETURL_SYN_PASSWORD_ACTION + ".page?sign=" + sign;
        // 异步调用路
        String bgRetUrl = PropUtils.getSystem(CustomConstants.HYJF_WEB_URL) + request.getContextPath() +  WxTransPasswordDefine.REQUEST_MAPPING
                + WxTransPasswordDefine.RETURN_ASY_PASSWORD_ACTION + ".do?sign=" + sign;
        // 调用设置密码接口
        BankCallBean bean = new BankCallBean();
        bean.setVersion(BankCallConstant.VERSION_10);// 接口版本号
        bean.setTxCode(BankCallConstant.TXCODE_PASSWORD_SET);// 消息类型(用户开户)
        bean.setInstCode(PropUtils.getSystem(BankCallConstant.BANK_INSTCODE));// 机构代码
        bean.setBankCode(PropUtils.getSystem(BankCallConstant.BANK_BANKCODE));
        bean.setTxDate(GetOrderIdUtils.getTxDate());
        bean.setTxTime(GetOrderIdUtils.getTxTime());
        bean.setSeqNo(GetOrderIdUtils.getSeqNo(6));
        bean.setChannel(BankCallConstant.CHANNEL_APP);
        if(user.getUserType() == 1){ //企业用户 传组织机构代码
            CorpOpenAccountRecord record = wxTransPasswordService.getCorpOpenAccountRecord(Integer.valueOf(userId));
            bean.setIdType(record.getCardType() != null ? String.valueOf(record.getCardType()) : BankCallConstant.ID_TYPE_COMCODE);// 证件类型 20：其他证件（组织机构代码）25：社会信用号
            bean.setIdNo(record.getBusiCode());
            bean.setName(record.getBusiName());
        }else{
            bean.setIdType(BankCallConstant.ID_TYPE_IDCARD);
            bean.setIdNo(usersInfo.getIdcard());
            bean.setName(usersInfo.getTruename());
        }
        bean.setAccountId(String.valueOf(bankOpenAccount.getAccount()));//电子账号
        bean.setMobile(user.getMobile());
        bean.setRetUrl(retUrl);// 页面同步返回 URL
        bean.setNotifyUrl(bgRetUrl);// 页面异步返回URL(必须)
        // 商户私有域，存放开户平台,用户userId
        LogAcqResBean acqRes = new LogAcqResBean();
        acqRes.setUserId(userId);
        bean.setLogAcqResBean(acqRes);
        // 操作者ID
        bean.setLogUserId(String.valueOf(userId));
        bean.setLogBankDetailUrl(BankCallConstant.BANK_URL_PASSWORDSET);
        bean.setLogOrderId(GetOrderIdUtils.getOrderId2(userId));
        bean.setLogOrderDate(GetOrderIdUtils.getOrderDate());
        // 跳转到汇付天下画面
        try {
            modelAndView = BankCallUtils.callApi(bean);
            logger.info(WxTransPasswordController.class.toString(), WxTransPasswordDefine.SETPASSWORD_ACTION);
        } catch (Exception e) {
            logger.error("调用江西银行接口异常...", e);
            return getErrorModelAndView(ResultEnum.USER_ERROR_208, sign, null, null);
        }
        return modelAndView;
    }

    *//**
     * 设置交易密码同步回调
     *
     * @param request
     * @param response
     * @return
     *//*
    @RequestMapping(value = WxTransPasswordDefine.RETURL_SYN_PASSWORD_ACTION)
    public ModelAndView passwordReturn(HttpServletRequest request, HttpServletResponse response,
                                       @ModelAttribute BankCallBean bean) {

        logger.info(WxTransPasswordDefine.THIS_CLASS, WxTransPasswordDefine.RETURL_SYN_PASSWORD_ACTION, "[交易密码同步回调开始]");
        bean.convert();
        String sign = request.getParameter("sign");
        String userIdStr = bean.getLogUserId();
        Integer userId = 0;
        if(Validator.isNotNull(userIdStr)){
            userId = Integer.parseInt(userIdStr);
        }else{
            userId = requestUtil.getRequestUserId(request);
        }

        Users user = this.wxTransPasswordService.getUsers(userId);
        logger.info("设置交易密码同步回调,userId : {}", userId);
        // 返回失败
        *//*if (bean.getRetCode()!=null&&!BankCallConstant.RESPCODE_SUCCESS.equals(bean.getRetCode())) {
            return getErrorModelAndView(null, sign, bean.getRetCode(), null);
        }*//*
        //判断用户是否设置了交易密码
        boolean flag = user.getIsSetPassword() == 1 ? true : false ;
        if(flag){
            logger.info("设置交易密码同步回调返回成功,flag : {}", flag);
            return getSuccessModelAndView(sign);
        }
        BankOpenAccount bankOpenAccount = wxTransPasswordService.getBankOpenAccount(userId);
        // 调用查询电子账户密码是否设置
        BankCallBean selectbean = new BankCallBean();
        selectbean.setVersion(BankCallConstant.VERSION_10);// 接口版本号
        selectbean.setTxCode(BankCallConstant.TXCODE_PASSWORD_SET_QUERY);
        selectbean.setInstCode(PropUtils.getSystem(BankCallConstant.BANK_INSTCODE));// 机构代码
        selectbean.setBankCode(PropUtils.getSystem(BankCallConstant.BANK_BANKCODE));
        selectbean.setTxDate(GetOrderIdUtils.getTxDate());
        selectbean.setTxTime(GetOrderIdUtils.getTxTime());
        selectbean.setSeqNo(GetOrderIdUtils.getSeqNo(6));
        selectbean.setChannel(BankCallConstant.CHANNEL_PC);
        selectbean.setAccountId(String.valueOf(bankOpenAccount.getAccount()));// 电子账号

        // 操作者ID
        selectbean.setLogUserId(String.valueOf(userId));
        selectbean.setLogOrderId(GetOrderIdUtils.getOrderId2(userId));
        selectbean.setLogOrderDate(GetOrderIdUtils.getOrderDate());
        selectbean.setLogClient(0);
        // 返回参数
        BankCallBean retBean = null;
        // 调用接口
        retBean = BankCallUtils.callApiBg(selectbean);
        logger.info(WxTransPasswordController.class.toString(), WxTransPasswordDefine.RETURL_SYN_PASSWORD_ACTION);


        if("1".equals(retBean.getPinFlag())){
            // 是否设置密码中间状态
            this.wxTransPasswordService.updateUserIsSetPassword(user, 1);
            logger.info("设置交易密码同步回调返回成功,pinFlag : {}", retBean.getPinFlag());
            return getSuccessModelAndView(sign);
        }

        logger.info(WxTransPasswordDefine.THIS_CLASS, WxTransPasswordDefine.RETURL_SYN_PASSWORD_ACTION, "[交易完成后,回调结束]");
        logger.info("设置密码同步回调返回失败");
        return getErrorModelAndView(ResultEnum.USER_ERROR_207,sign,null,null);
    }

    *//**
     * 设置交易密码异步回调
     *
     * @param request
     * @param response
     * @return
     *//*
    @ResponseBody
    @RequestMapping(value = WxTransPasswordDefine.RETURN_ASY_PASSWORD_ACTION)
    public BankCallResult passwordBgreturn(HttpServletRequest request, HttpServletResponse response,
                                           @ModelAttribute BankCallBean bean) {
        BankCallResult result = new BankCallResult();
        logger.info(WxTransPasswordDefine.THIS_CLASS, WxTransPasswordDefine.RETURN_ASY_PASSWORD_ACTION, "[开户异步回调开始]");
        bean.convert();
        Integer userId = Integer.parseInt(bean.getLogUserId());
        // 查询用户开户状态
        Users user = this.wxTransPasswordService.getUsers(userId);

        // 成功或审核中
        if (user != null && BankCallConstant.RESPCODE_SUCCESS.equals(bean.get(BankCallConstant.PARAM_RETCODE))) {
            try {
                // 开户后保存相应的数据以及日志
                this.wxTransPasswordService.updateUserIsSetPassword(user, 1);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(WxTransPasswordDefine.THIS_CLASS, WxTransPasswordDefine.RETURN_ASY_PASSWORD_ACTION, e);
            }
        }
        logger.info(WxTransPasswordDefine.THIS_CLASS, WxTransPasswordDefine.RETURN_ASY_PASSWORD_ACTION, "[交易完成后,回调结束]");
        result.setMessage("交易密码设置成功");
        result.setStatus(true);
        return result;
    }



    *//**
     * 重置交易密码
     *
     * @param request
     * @param
     * @return
     *//*
    @SignValidate
    @RequestMapping(value = WxTransPasswordDefine.RESETPASSWORD_ACTION)
    public ModelAndView resetPassword(HttpServletRequest request, HttpServletResponse response) {

        logger.info(WxTransPasswordDefine.THIS_CLASS, WxTransPasswordDefine.RESETPASSWORD_ACTION);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView = new ModelAndView(WxTransPasswordDefine.JUMP_HTML);

        String sign = request.getParameter("sign");
        //判断用户是否登录
        Integer userId = requestUtil.getRequestUserId(request);
        if(StringUtil.isBlank(userId.toString())){
            logger.info(WxTransPasswordDefine.THIS_CLASS, WxTransPasswordDefine.INIT);
            return getErrorModelAndView(ResultEnum.ERROR_004, sign, null, null);
        }
        //判断用户是否开户
        Users user= wxTransPasswordService.getUsers(userId);
        if (user.getBankOpenAccount().intValue() != 1) {//未开户
            return getErrorModelAndView(ResultEnum.USER_ERROR_200, sign, null, null);
        }

        //判断用户是否设置过交易密码
        Integer passwordFlag = user.getIsSetPassword();
        if (passwordFlag == 0) {//已设置交易密码
            return getErrorModelAndView(ResultEnum.USER_ERROR_201, sign, null, null);
        }
        BankOpenAccount bankOpenAccount = wxTransPasswordService.getBankOpenAccount(userId);
        UsersInfo usersInfo= wxTransPasswordService.getUsersInfoByUserId(userId);
        // 同步调用路径
        String retUrl = PropUtils.getSystem(CustomConstants.HYJF_WEB_URL) + request.getContextPath() +  WxTransPasswordDefine.REQUEST_MAPPING
                + WxTransPasswordDefine.RETURL_SYN_RESETPASSWORD_ACTION + ".page?sign=" + sign;
        // 异步调用路
        String bgRetUrl = PropUtils.getSystem(CustomConstants.HYJF_WEB_URL) + request.getContextPath() +  WxTransPasswordDefine.REQUEST_MAPPING
                + WxTransPasswordDefine.RETURN_ASY_RESETPASSWORD_ACTION + ".do?sign=" + sign;

        // 调用设置密码接口
        BankCallBean bean = new BankCallBean();
        bean.setVersion(BankCallConstant.VERSION_10);// 接口版本号
        bean.setTxCode(BankCallConstant.TXCODE_PASSWORD_RESET);// 消息类型(用户开户)
        bean.setInstCode(PropUtils.getSystem(BankCallConstant.BANK_INSTCODE));// 机构代码
        bean.setBankCode(PropUtils.getSystem(BankCallConstant.BANK_BANKCODE));
        bean.setTxDate(GetOrderIdUtils.getTxDate());
        bean.setTxTime(GetOrderIdUtils.getTxTime());
        bean.setSeqNo(GetOrderIdUtils.getSeqNo(6));
        bean.setChannel(BankCallConstant.CHANNEL_APP);
        if(user.getUserType() == 1){ //企业用户 传组织机构代码
            CorpOpenAccountRecord record = wxTransPasswordService.getCorpOpenAccountRecord(Integer.valueOf(userId));
            bean.setIdType(record.getCardType() != null ? String.valueOf(record.getCardType()) : BankCallConstant.ID_TYPE_COMCODE);// 证件类型 20：其他证件（组织机构代码）25：社会信用号
            bean.setIdNo(record.getBusiCode());
            bean.setName(record.getBusiName());
        }else{
            bean.setIdType(BankCallConstant.ID_TYPE_IDCARD);
            bean.setIdNo(usersInfo.getIdcard());
            bean.setName(usersInfo.getTruename());
        }
        bean.setAccountId(String.valueOf(bankOpenAccount.getAccount()));//电子账号
        bean.setMobile(user.getMobile());
        bean.setSuccessfulUrl(retUrl+"&isSuccess=1");
        bean.setRetUrl(retUrl);// 页面同步返回 URL
        bean.setNotifyUrl(bgRetUrl);// 页面异步返回URL(必须)
        // 商户私有域，存放开户平台,用户userId
        LogAcqResBean acqRes = new LogAcqResBean();
        acqRes.setUserId(userId);
        bean.setLogAcqResBean(acqRes);
        // 操作者ID
        bean.setLogUserId(String.valueOf(userId));
        bean.setLogBankDetailUrl(BankCallConstant.BANK_URL_MOBILE);
        bean.setLogOrderId(GetOrderIdUtils.getOrderId2(userId));
        bean.setLogOrderDate(GetOrderIdUtils.getOrderDate());
        // 跳转到汇付天下画面
        try {
            modelAndView = BankCallUtils.callApi(bean);
            logger.info(WxTransPasswordController.class.toString(), WxTransPasswordDefine.RESETPASSWORD_ACTION);
        } catch (Exception e) {
            logger.error("调用银行接口失败!",e);
            return getErrorModelAndView(ResultEnum.USER_ERROR_208, sign, null, null);
        }
        return modelAndView;
    }

    *//**
     * 重置交易密码同步回调
     *
     * @param request
     * @param response
     * @return
     *//*
    @RequestMapping(value = WxTransPasswordDefine.RETURL_SYN_RESETPASSWORD_ACTION)
    public ModelAndView resetPasswordReturn(HttpServletRequest request, HttpServletResponse response,
                                            @ModelAttribute BankCallBean bean) {

        logger.info(WxTransPasswordDefine.THIS_CLASS, WxTransPasswordDefine.RETURL_SYN_RESETPASSWORD_ACTION, "[重置交易密码同步回调开始]");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView = new ModelAndView(WxTransPasswordDefine.JUMP_HTML);
        BaseMapBean baseMapBean=new BaseMapBean();
        String sign = request.getParameter("sign");
        String isSuccess = request.getParameter("isSuccess");
        // 返回失败
        if (bean.getRetCode()!=null&&!BankCallConstant.RESPCODE_SUCCESS.equals(bean.getRetCode())||!"1".equals(isSuccess)) {
            logger.info("重置交易密码回调,返回失败");
            return getErrorModelAndView(ResultEnum.USER_ERROR_215, sign, null, bean.getRetCode());
        }
        baseMapBean.set(CustomConstants.APP_STATUS, ResultEnum.SUCCESS.getStatus());
        baseMapBean.set(CustomConstants.APP_STATUS_DESC, "交易密码修改成功");
        baseMapBean.setCallBackAction(CustomConstants.HOST+WxTransPasswordDefine.JUMP_HTML_SUCCESS_PATH);
        baseMapBean.setJumpFlag(BaseMapBean.JUMP_FLAG_NO);
        modelAndView.addObject("callBackForm", baseMapBean);
        logger.info(WxTransPasswordDefine.THIS_CLASS, WxTransPasswordDefine.RETURL_SYN_RESETPASSWORD_ACTION, "[重置交易密码同步回调结束]");
        return modelAndView;
    }

    *//**
     * 重置交易密码异步回调
     *
     * @param request
     * @param response
     * @return
     *//*
    @ResponseBody
    @RequestMapping(value = WxTransPasswordDefine.RETURN_ASY_RESETPASSWORD_ACTION)
    public BankCallResult resetPasswordBgreturn(HttpServletRequest request, HttpServletResponse response,
                                                @ModelAttribute BankCallBean bean) {
        BankCallResult result = new BankCallResult();
        logger.info(WxTransPasswordDefine.THIS_CLASS, WxTransPasswordDefine.RETURN_ASY_RESETPASSWORD_ACTION, "[重置交易密码回调开始]");

        logger.info(WxTransPasswordDefine.THIS_CLASS, WxTransPasswordDefine.RETURN_ASY_RESETPASSWORD_ACTION, "[重置交易密码回调结束]");
        result.setMessage("交易密码修改成功");
        result.setStatus(true);
        return result;
    }



    private ModelAndView getErrorModelAndView(ResultEnum param, String sign, String retCodeSet,
                                              String retCodeUpd) {
        ModelAndView modelAndView = new ModelAndView(WxTransPasswordDefine.JUMP_HTML);
        BaseMapBean baseMapBean = new BaseMapBean();
        if (StringUtils.isNotBlank(retCodeSet)) {
            baseMapBean.set(CustomConstants.APP_STATUS, ResultEnum.FAIL.getStatus());
            baseMapBean.set(CustomConstants.APP_STATUS_DESC, "交易密码设置失败,失败原因：" + wxTransPasswordService.getBankRetMsg(retCodeSet));
        } else if (StringUtils.isNotBlank(retCodeUpd)) {
            baseMapBean.set(CustomConstants.APP_STATUS, ResultEnum.FAIL.getStatus());
            baseMapBean.set(CustomConstants.APP_STATUS_DESC, "交易密码修改失败,失败原因：" + wxTransPasswordService.getBankRetMsg(retCodeUpd));
        }
        else {
            baseMapBean.set(CustomConstants.APP_STATUS, param.getStatus());
            baseMapBean.set(CustomConstants.APP_STATUS_DESC, param.getStatusDesc());
        }
        baseMapBean.set(CustomConstants.APP_SIGN, sign);
        baseMapBean.setCallBackAction(CustomConstants.HOST + WxTransPasswordDefine.JUMP_HTML_ERROR_PATH);
        modelAndView.addObject("callBackForm", baseMapBean);
        return modelAndView;
    }

    private ModelAndView getSuccessModelAndView(String sign) {
        ModelAndView modelAndView = new ModelAndView(WxTransPasswordDefine.JUMP_HTML);
        BaseMapBean baseMapBean = new BaseMapBean();
        baseMapBean.set(CustomConstants.APP_STATUS, ResultEnum.SUCCESS.getStatus());
        baseMapBean.set(CustomConstants.APP_STATUS_DESC, ResultEnum.SUCCESS.getStatusDesc());
        baseMapBean.set(CustomConstants.APP_SIGN, sign);
        baseMapBean.setCallBackAction(CustomConstants.HOST + WxTransPasswordDefine.PASSWORD_SUCCESS_PATH);
        modelAndView.addObject("callBackForm", baseMapBean);
        return modelAndView;
    }
*/

}