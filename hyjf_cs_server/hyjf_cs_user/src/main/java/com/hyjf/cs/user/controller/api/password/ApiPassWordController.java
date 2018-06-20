/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.api.password;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.common.controller.BaseController;
import com.hyjf.cs.user.bean.*;
import com.hyjf.cs.user.service.bankopen.BankOpenService;
import com.hyjf.cs.user.util.ErrorCodeConstant;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import com.hyjf.soa.apiweb.CommonSoaUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.Api;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wangc
 */
@Api(value = "密码相关服务")
@Controller
@RequestMapping("/api/user/password")
public class ApiPassWordController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(ApiPassWordController.class);


    @Autowired
    BankOpenService bankOpenService;

 /*   *//**
     * 设置交易密码
     *
     * @param request
     * @return
     *//*
    @PostMapping(value = "/setTeaderPassword", produces = "application/json; charset=utf-8")
    public ModelAndView setPassword(@RequestBody ThirdPartyTransPasswordRequestBean transPasswordRequestBean,HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView();
        logger.info("第三方请求参数："+JSONObject.toJSONString(transPasswordRequestBean));
        //验证请求参数
        if (Validator.isNull(transPasswordRequestBean.getAccountId())||
                Validator.isNull(transPasswordRequestBean.getRetUrl())||
                Validator.isNull(transPasswordRequestBean.getBgRetUrl())||
                Validator.isNull(transPasswordRequestBean.getChannel())||
                Validator.isNull(transPasswordRequestBean.getInstCode())) {

            if(Validator.isNotNull(transPasswordRequestBean.getBgRetUrl())){
                Map<String, String> params = new HashMap<String, String>();
                params.put("accountId", transPasswordRequestBean.getAccountId());
                params.put("statusDesc","请求参数非法");
                params.put("acqRes",transPasswordRequestBean.getAcqRes());
                BaseResultBean resultBean = new BaseResultBean();
                resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE000001);
                params.put("status", resultBean.getStatus());
                params.put("chkValue", resultBean.getChkValue());

                CommonSoaUtils.noRetPostThree(transPasswordRequestBean.getBgRetUrl(), params);
            }
            if(Validator.isNotNull(transPasswordRequestBean.getRetUrl())){
                ThirdPartyTransPasswordResultBean repwdResult = new ThirdPartyTransPasswordResultBean();
                repwdResult.setCallBackAction(transPasswordRequestBean.getRetUrl());
                //
                modelAndView = new ModelAndView("");
                // 设置交易密码
                repwdResult.set("statusDesc", "请求参数非法");

                BaseResultBean resultBean = new BaseResultBean();
                resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE000001);
                repwdResult.set("chkValue", resultBean.getChkValue());
                repwdResult.set("status", resultBean.getStatus());
                repwdResult.set("acqRes",transPasswordRequestBean.getAcqRes());
                modelAndView.addObject("callBackForm", repwdResult);
                return modelAndView;
            }


            modelAndView = new ModelAndView("");
            logger.info("-------------------请求参数非法--------------------");
            modelAndView.addObject("message", "请求参数非法");
            return modelAndView;
        }

        //验签  暂时去掉验签
*//*        if(!this.verifyRequestSign(transPasswordRequestBean, BaseDefine.METHOD_SERVER_SET_PASSWORD)){
            logger.info("-------------------验签失败！--------------------");
            ThirdPartyTransPasswordResultBean repwdResult = new ThirdPartyTransPasswordResultBean();
            repwdResult.setCallBackAction(transPasswordRequestBean.getRetUrl());
            repwdResult.set("accountId", transPasswordRequestBean.getAccountId());
            repwdResult.set("acqRes",transPasswordRequestBean.getAcqRes());
            modelAndView = new ModelAndView(ApplyDefine.CALL_BACK_TRANSPASSWORD_VIEW);
            // 设置交易密码
            repwdResult.set("statusDesc", "验签失败！");

            BaseResultBean resultBean = new BaseResultBean();
            resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE000002);
            repwdResult.set("status", resultBean.getStatus());
            repwdResult.set("chkValue", resultBean.getChkValue());
            modelAndView.addObject("callBackForm", repwdResult);
            if(Validator.isNotNull(transPasswordRequestBean.getBgRetUrl())){
                Map<String, String> params = new HashMap<String, String>();
                params.put("accountId", transPasswordRequestBean.getAccountId());
                params.put("status", resultBean.getStatus());
                params.put("statusDesc","验签失败！");
                params.put("acqRes",transPasswordRequestBean.getAcqRes());
                params.put("chkValue", resultBean.getChkValue());
                CommonSoaUtils.noRetPostThree(transPasswordRequestBean.getBgRetUrl(), params);
            }
            return modelAndView;
        }*//*


        //根据账号找出用户ID
        BankOpenAccountVO bankOpenAccount = bankOpenService.getBankOpenAccount(Integer.valueOf(transPasswordRequestBean.getAccountId()));
        if(bankOpenAccount == null){
            modelAndView = new ModelAndView(ThirdPartyTransPasswordDefine.PASSWORD_ERROR_PATH);
            logger.info("-------------------没有根据电子银行卡找到用户"+transPasswordRequestBean.getAccountId()+"！--------------------");

            ThirdPartyTransPasswordResultBean repwdResult = new ThirdPartyTransPasswordResultBean();
            repwdResult.setCallBackAction(transPasswordRequestBean.getRetUrl());
            repwdResult.set("accountId", transPasswordRequestBean.getAccountId());
            modelAndView = new ModelAndView(ApplyDefine.CALL_BACK_TRANSPASSWORD_VIEW);
            // 设置交易密码
            repwdResult.set("statusDesc", "没有根据电子银行卡找到用户 ");

            BaseResultBean resultBean = new BaseResultBean();
            resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE000004);
            repwdResult.set("status", resultBean.getStatus());
            repwdResult.set("chkValue", resultBean.getChkValue());
            repwdResult.set("acqRes",transPasswordRequestBean.getAcqRes());
            modelAndView.addObject("callBackForm", repwdResult);
            if(Validator.isNotNull(transPasswordRequestBean.getBgRetUrl())){
                Map<String, String> params = new HashMap<String, String>();
                params.put("accountId", transPasswordRequestBean.getAccountId());
                params.put("status", resultBean.getStatus());
                params.put("statusDesc","没有根据电子银行卡找到用户 ");
                params.put("chkValue", resultBean.getChkValue());
                params.put("acqRes",transPasswordRequestBean.getAcqRes());
                CommonSoaUtils.noRetPostThree(transPasswordRequestBean.getBgRetUrl(), params);
            }
            return modelAndView;

        }
        UserVO user = bankOpenService.getUsersById(bankOpenAccount.getUserId());//用户ID
        if (user == null) {
            logger.info("-------------------"+transPasswordRequestBean.getAccountId() + "用户不存在汇盈金服账户！--------------------");
            ThirdPartyTransPasswordResultBean repwdResult = new ThirdPartyTransPasswordResultBean();
            repwdResult.setCallBackAction(transPasswordRequestBean.getRetUrl());
            repwdResult.set("accountId", transPasswordRequestBean.getAccountId());
            modelAndView = new ModelAndView(ApplyDefine.CALL_BACK_TRANSPASSWORD_VIEW);
            // 设置交易密码
            repwdResult.set("statusDesc","用户不存在汇盈金服账户！");

            BaseResultBean resultBean = new BaseResultBean();
            resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE000006);
            repwdResult.set("chkValue", resultBean.getChkValue());
            repwdResult.set("status", resultBean.getStatus());
            repwdResult.set("acqRes",transPasswordRequestBean.getAcqRes());
            modelAndView.addObject("callBackForm", repwdResult);
            if(Validator.isNotNull(transPasswordRequestBean.getBgRetUrl())){
                Map<String, String> params = new HashMap<String, String>();
                params.put("accountId", transPasswordRequestBean.getAccountId());
                params.put("status", repwdResult.getStatus());
                params.put("statusDesc","用户不存在汇盈金服账户！");
                params.put("chkValue", resultBean.getChkValue());
                params.put("acqRes",transPasswordRequestBean.getAcqRes());
                CommonSoaUtils.noRetPostThree(transPasswordRequestBean.getBgRetUrl(), params);
            }
            return modelAndView;

        }
        Integer userId = user.getUserId();
        if (user.getBankOpenAccount().intValue() != 1) {// 未开户
            logger.info("-------------------用户未开户！--------------------");
            ThirdPartyTransPasswordResultBean repwdResult = new ThirdPartyTransPasswordResultBean();
            repwdResult.setCallBackAction(transPasswordRequestBean.getRetUrl());
            repwdResult.set("accountId", transPasswordRequestBean.getAccountId());
            modelAndView = new ModelAndView(ApplyDefine.CALL_BACK_TRANSPASSWORD_VIEW);
            // 设置交易密码
            repwdResult.set("statusDesc", "用户未开户！");

            BaseResultBean resultBean = new BaseResultBean();
            resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE000007);
            repwdResult.set("chkValue", resultBean.getChkValue());
            repwdResult.set("status", resultBean.getStatus());
            repwdResult.set("acqRes",transPasswordRequestBean.getAcqRes());
            modelAndView.addObject("callBackForm", repwdResult);
            if(Validator.isNotNull(transPasswordRequestBean.getBgRetUrl())){
                Map<String, String> params = new HashMap<String, String>();
                params.put("accountId", transPasswordRequestBean.getAccountId());
                params.put("status", resultBean.getStatus());
                params.put("statusDesc","用户未开户！");
                params.put("chkValue", resultBean.getChkValue());
                params.put("acqRes",transPasswordRequestBean.getAcqRes());
                CommonSoaUtils.noRetPostThree(transPasswordRequestBean.getBgRetUrl(), params);
            }
            return modelAndView;
        }

        // 判断用户是否设置过交易密码

        Integer passwordFlag = user.getIsSetPassword();
        if (passwordFlag == 1) {// 已设置交易密码

            logger.info("-------------------已设置交易密码--------------------");
            ThirdPartyTransPasswordResultBean repwdResult = new ThirdPartyTransPasswordResultBean();
            repwdResult.setCallBackAction(transPasswordRequestBean.getRetUrl());
            repwdResult.set("accountId", transPasswordRequestBean.getAccountId());
            modelAndView = new ModelAndView(ApplyDefine.CALL_BACK_TRANSPASSWORD_VIEW);
            // 设置交易密码
            repwdResult.set("statusDesc", "已设置交易密码");

            BaseResultBean resultBean = new BaseResultBean();
            resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_TP000001);
            repwdResult.set("chkValue", resultBean.getChkValue());
            repwdResult.set("status", resultBean.getStatus());
            repwdResult.set("acqRes",transPasswordRequestBean.getAcqRes());
            modelAndView.addObject("callBackForm", repwdResult);
            if(Validator.isNotNull(transPasswordRequestBean.getBgRetUrl())){
                Map<String, String> params = new HashMap<String, String>();
                params.put("accountId", transPasswordRequestBean.getAccountId());
                params.put("status", resultBean.getStatus());
                params.put("statusDesc","已设置交易密码");
                params.put("chkValue", resultBean.getChkValue());
                params.put("acqRes",transPasswordRequestBean.getAcqRes());
                CommonSoaUtils.noRetPostThree(transPasswordRequestBean.getBgRetUrl(), params);
            }
            return modelAndView;
        }

        UsersInfo usersInfo = bankOpenService.getUsersInfoByUserId(userId);
        // 同步调用路径
        String retUrl = PropUtils.getSystem(CustomConstants.HTTP_HYJF_WEB_URL)
                + ThirdPartyTransPasswordDefine.REQUEST_MAPPING + ThirdPartyTransPasswordDefine.RETURL_SYN_PASSWORD_ACTION + ".do?acqRes="+
                transPasswordRequestBean.getAcqRes()+"&callback="+transPasswordRequestBean.getRetUrl().replace("#", "*-*-*");
        // 异步调用路
        String bgRetUrl = PropUtils.getSystem(CustomConstants.HTTP_HYJF_WEB_URL)
                + ThirdPartyTransPasswordDefine.REQUEST_MAPPING + ThirdPartyTransPasswordDefine.RETURN_ASY_PASSWORD_ACTION + ".do?acqRes="+
                transPasswordRequestBean.getAcqRes()+"&callback="+transPasswordRequestBean.getBgRetUrl().replace("#", "*-*-*");
        // 调用设置密码接口
        System.out.println(retUrl+"..."+bgRetUrl);
        BankCallBean bean = new BankCallBean();
        bean.setVersion(BankCallConstant.VERSION_10);// 接口版本号
        bean.setTxCode(BankCallConstant.TXCODE_PASSWORD_SET);// 消息类型(用户开户)
        bean.setInstCode(PropUtils.getSystem(BankCallConstant.BANK_INSTCODE));// 机构代码
        bean.setBankCode(PropUtils.getSystem(BankCallConstant.BANK_BANKCODE));
        bean.setTxDate(GetOrderIdUtils.getTxDate());
        bean.setTxTime(GetOrderIdUtils.getTxTime());
        bean.setSeqNo(GetOrderIdUtils.getSeqNo(6));
        bean.setChannel(transPasswordRequestBean.getChannel());
        bean.setIdType(BankCallConstant.ID_TYPE_IDCARD);
        bean.setAccountId(String.valueOf(bankOpenAccount.getAccount()));// 电子账号
        bean.setIdNo(usersInfo.getIdcard());
        bean.setName(usersInfo.getTruename());
        bean.setMobile(user.getMobile());

        bean.setRetUrl(retUrl);// 页面同步返回 URL
        bean.setNotifyUrl(bgRetUrl);// 页面异步返回URL(必须)
        // 操作者ID
        bean.setLogUserId(String.valueOf(userId));
        bean.setLogBankDetailUrl(BankCallConstant.BANK_URL_PASSWORDSET);
        bean.setLogOrderId(GetOrderIdUtils.getOrderId2(userId));
        // 跳转到汇付天下画面
        try {
            modelAndView = BankCallUtils.callApi(bean);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("设置交易密码end");
        return modelAndView;
    }

    *//**
     * 设置交易密码同步回调
     *
     * @param request
     * @param response
     * @return
     *//*
    @RequestMapping(ThirdPartyTransPasswordDefine.RETURL_SYN_PASSWORD_ACTION)
    public ModelAndView passwordReturn(HttpServletRequest request, HttpServletResponse response,
                                       @ModelAttribute BankCallBean bean) {
        logger.info("设置交易密码同步回调start");
        ModelAndView modelAndView = new ModelAndView(ApplyDefine.CALL_BACK_TRANSPASSWORD_VIEW);
        ThirdPartyTransPasswordResultBean repwdResult = new ThirdPartyTransPasswordResultBean();
        repwdResult.setCallBackAction(request.getParameter("callback").replace("*-*-*","#"));
        bean.convert();
        int userId = Integer.parseInt(bean.getLogUserId());
        Users user = this.transPasswordService.getUsers(userId);


        BankOpenAccount bankOpenAccount = transPasswordService.getBankOpenAccount(userId);

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
        selectbean.setLogClient(0);
        // 返回参数
        BankCallBean retBean = null;
        // 调用接口
        retBean = BankCallUtils.callApiBg(selectbean);
        repwdResult.set("accountId", bankOpenAccount.getAccount());

        if ("1".equals(retBean.getPinFlag())) {
            // 是否设置密码中间状态
            this.transPasswordService.updateUserIsSetPassword(user, 1);

            modelAndView.addObject("statusDesc", "交易密码设置成功！");
            BaseResultBean resultBean = new BaseResultBean();
            resultBean.setStatusForResponse(ErrorCodeConstant.SUCCESS);
            repwdResult.set("chkValue", resultBean.getChkValue());
            repwdResult.set("status", resultBean.getStatus());
        } else {
            // 设置交易密码
            modelAndView.addObject("statusDesc", "交易密码设置失败,失败原因：" + transPasswordService.getBankRetMsg(bean.getRetCode()));

            BaseResultBean resultBean = new BaseResultBean();
            resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE999999);
            repwdResult.set("chkValue", resultBean.getChkValue());
            repwdResult.set("status", resultBean.getStatus());
        }
        repwdResult.set("acqRes",request.getParameter("acqRes"));
        modelAndView.addObject("callBackForm", repwdResult);
        logger.info("设置交易密码同步回调end");
        return modelAndView;
    }

    *//**
     * 设置交易密码异步回调
     *
     * @param request
     * @param response
     * @return
     *//*
    @ResponseBody
    @RequestMapping(ThirdPartyTransPasswordDefine.RETURN_ASY_PASSWORD_ACTION)
    public BankCallResult passwordBgreturn(HttpServletRequest request, HttpServletResponse response,
                                           @ModelAttribute BankCallBean bean) {
        logger.info("设置交易密码异步回调start");
        // 返回值  9-22修改
        BankCallResult result = new BankCallResult();
        String message = "";
        String status = "";
        Map<String, String> params = new HashMap<String, String>();
        // 返回值修改 end
        bean.convert();
        int userId = Integer.parseInt(bean.getLogUserId());
        Users user = this.transPasswordService.getUsers(userId);


        BankOpenAccount bankOpenAccount = transPasswordService.getBankOpenAccount(userId);

        // 成功或审核中
        if (user != null && BankCallConstant.RESPCODE_SUCCESS.equals(bean.get(BankCallConstant.PARAM_RETCODE))) {
            try {
                // 开户后保存相应的数据以及日志
                this.transPasswordService.updateUserIsSetPassword(user, 1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            message = "交易密码设置成功";
            status = ErrorCodeConstant.SUCCESS;
        }else{
            // 设置交易密码
            message = "交易密码设置失败,失败原因：" + transPasswordService.getBankRetMsg(bean.getRetCode());
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

        logger.info("设置交易密码异步回调end");
        result.setMessage("设置交易密码成功");
        result.setStatus(true);
        // 返回值  9-22修改 end

        return result;
    }
    *//**
     * 修改交易密码
     *
     * @param request
     * @param form
     * @return
     *//*
    @RequestMapping(ThirdPartyTransPasswordDefine.RESETPASSWORD_ACTION)
    public ModelAndView resetPassword(@RequestBody ThirdPartyTransPasswordRequestBean transPasswordRequestBean,HttpServletRequest request, HttpServletResponse response) {
        logger.info("修改交易密码 start");
        ModelAndView modelAndView = new ModelAndView();
        logger.info(transPasswordRequestBean.getAccountId()+"修改交易密码开始-----------------------------");
        logger.info("第三方请求参数："+JSONObject.toJSONString(transPasswordRequestBean));
        //验证请求参数
        if (Validator.isNull(transPasswordRequestBean.getAccountId())||
                Validator.isNull(transPasswordRequestBean.getRetUrl())||
                Validator.isNull(transPasswordRequestBean.getBgRetUrl())||
                Validator.isNull(transPasswordRequestBean.getChannel())||
                Validator.isNull(transPasswordRequestBean.getInstCode())) {
            modelAndView = new ModelAndView(ThirdPartyTransPasswordDefine.PASSWORD_ERROR_PATH);
            logger.info("-------------------请求参数非法--------------------");
            if(Validator.isNotNull(transPasswordRequestBean.getBgRetUrl())){
                Map<String, String> params = new HashMap<String, String>();
                params.put("accountId", transPasswordRequestBean.getAccountId());
                params.put("statusDesc","请求参数非法");
                BaseResultBean resultBean = new BaseResultBean();
                resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE000001);
                params.put("status", resultBean.getStatus());
                params.put("chkValue", resultBean.getChkValue());
                params.put("acqRes",transPasswordRequestBean.getAcqRes());
                CommonSoaUtils.noRetPostThree(transPasswordRequestBean.getBgRetUrl(), params);
            }

            if(Validator.isNotNull(transPasswordRequestBean.getRetUrl())){
                ThirdPartyTransPasswordResultBean repwdResult = new ThirdPartyTransPasswordResultBean();
                repwdResult.setCallBackAction(transPasswordRequestBean.getRetUrl());
                modelAndView = new ModelAndView(ApplyDefine.CALL_BACK_TRANSPASSWORD_VIEW);
                // 设置交易密码
                repwdResult.set("statusDesc", "请求参数非法");

                BaseResultBean resultBean = new BaseResultBean();
                resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE000001);
                repwdResult.set("chkValue", resultBean.getChkValue());
                repwdResult.set("status", resultBean.getStatus());
                repwdResult.set("acqRes",transPasswordRequestBean.getAcqRes());
                modelAndView.addObject("callBackForm", repwdResult);
                return modelAndView;
            }


            modelAndView.addObject("message", "请求参数非法");
            return modelAndView;
        }

        //验签
        if(!this.verifyRequestSign(transPasswordRequestBean, BaseDefine.METHOD_SERVER_RESET_PASSWORD)){
            logger.info("-------------------验签失败！--------------------");
            ThirdPartyTransPasswordResultBean repwdResult = new ThirdPartyTransPasswordResultBean();
            repwdResult.setCallBackAction(transPasswordRequestBean.getRetUrl());
            repwdResult.set("accountId", transPasswordRequestBean.getAccountId());
            modelAndView = new ModelAndView(ApplyDefine.CALL_BACK_TRANSPASSWORD_VIEW);
            // 设置交易密码
            repwdResult.set("statusDesc", "验签失败！");

            BaseResultBean resultBean = new BaseResultBean();
            resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE000002);
            repwdResult.set("chkValue", resultBean.getChkValue());
            repwdResult.set("status", resultBean.getStatus());
            repwdResult.set("acqRes",transPasswordRequestBean.getAcqRes());
            modelAndView.addObject("callBackForm", repwdResult);
            if(Validator.isNotNull(transPasswordRequestBean.getBgRetUrl())){
                Map<String, String> params = new HashMap<String, String>();
                params.put("accountId", transPasswordRequestBean.getAccountId());
                params.put("status", resultBean.getStatus());
                params.put("statusDesc","验签失败！");
                params.put("chkValue", resultBean.getChkValue());
                params.put("acqRes",transPasswordRequestBean.getAcqRes());
                CommonSoaUtils.noRetPostThree(transPasswordRequestBean.getBgRetUrl(), params);
            }
            return modelAndView;
        }

        //根据账号找出用户ID
        BankOpenAccount bankOpenAccount = transPasswordService.getBankOpenAccount(transPasswordRequestBean.getAccountId());
        if(bankOpenAccount == null){
            modelAndView = new ModelAndView(ThirdPartyTransPasswordDefine.PASSWORD_ERROR_PATH);
            logger.info("-------------------没有根据电子银行卡找到用户"+transPasswordRequestBean.getAccountId()+"！--------------------");

            ThirdPartyTransPasswordResultBean repwdResult = new ThirdPartyTransPasswordResultBean();
            repwdResult.setCallBackAction(transPasswordRequestBean.getRetUrl());
            repwdResult.set("accountId", transPasswordRequestBean.getAccountId());
            modelAndView = new ModelAndView(ApplyDefine.CALL_BACK_TRANSPASSWORD_VIEW);
            // 设置交易密码
            repwdResult.set("statusDesc", "没有根据电子银行卡找到用户");

            BaseResultBean resultBean = new BaseResultBean();
            resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE000004);
            repwdResult.set("chkValue", resultBean.getChkValue());
            modelAndView.addObject("callBackForm", repwdResult);
            repwdResult.set("status", resultBean.getStatus());
            repwdResult.set("acqRes",transPasswordRequestBean.getAcqRes());
            if(Validator.isNotNull(transPasswordRequestBean.getBgRetUrl())){
                Map<String, String> params = new HashMap<String, String>();
                params.put("accountId", transPasswordRequestBean.getAccountId());
                params.put("status", resultBean.getStatus());
                params.put("statusDesc","没有根据电子银行卡找到用户");
                params.put("chkValue", resultBean.getChkValue());
                params.put("acqRes",transPasswordRequestBean.getAcqRes());
                CommonSoaUtils.noRetPostThree(transPasswordRequestBean.getBgRetUrl(), params);
            }
            return modelAndView;

        }
        Users user = transPasswordService.getUsers(bankOpenAccount.getUserId());//用户ID

        if (user.getBankOpenAccount().intValue() != 1) {//未开户
            ThirdPartyTransPasswordResultBean repwdResult = new ThirdPartyTransPasswordResultBean();
            repwdResult.setCallBackAction(transPasswordRequestBean.getRetUrl());
            repwdResult.set("accountId", transPasswordRequestBean.getAccountId());
            modelAndView = new ModelAndView(ApplyDefine.CALL_BACK_TRANSPASSWORD_VIEW);
            // 设置交易密码
            repwdResult.set("statusDesc", "用户未开户！");

            BaseResultBean resultBean = new BaseResultBean();
            resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE000006);
            repwdResult.set("chkValue", resultBean.getChkValue());
            repwdResult.set("status", resultBean.getStatus());
            repwdResult.set("acqRes",transPasswordRequestBean.getAcqRes());
            modelAndView.addObject("callBackForm", repwdResult);
            if(Validator.isNotNull(transPasswordRequestBean.getBgRetUrl())){
                Map<String, String> params = new HashMap<String, String>();
                params.put("accountId", transPasswordRequestBean.getAccountId());
                params.put("status", resultBean.getStatus());
                params.put("statusDesc","用户未开户！");
                params.put("chkValue", resultBean.getChkValue());
                params.put("acqRes",transPasswordRequestBean.getAcqRes());
                CommonSoaUtils.noRetPostThree(transPasswordRequestBean.getBgRetUrl(), params);
            }
            return modelAndView;
        }
        //判断用户是否设置过交易密码

        Integer passwordFlag = user.getIsSetPassword();
        if (passwordFlag == 0) {//未设置交易密码
            ThirdPartyTransPasswordResultBean repwdResult = new ThirdPartyTransPasswordResultBean();
            repwdResult.setCallBackAction(transPasswordRequestBean.getRetUrl());
            repwdResult.set("accountId", transPasswordRequestBean.getAccountId());
            modelAndView = new ModelAndView(ApplyDefine.CALL_BACK_TRANSPASSWORD_VIEW);
            // 设置交易密码
            repwdResult.set("statusDesc", "未设置过交易密码，请先设置交易密码");

            BaseResultBean resultBean = new BaseResultBean();
            resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_TP000002);
            repwdResult.set("chkValue", resultBean.getChkValue());
            repwdResult.set("status", resultBean.getStatus());
            repwdResult.set("acqRes",transPasswordRequestBean.getAcqRes());
            modelAndView.addObject("callBackForm", repwdResult);
            if(Validator.isNotNull(transPasswordRequestBean.getBgRetUrl())){
                Map<String, String> params = new HashMap<String, String>();
                params.put("accountId", transPasswordRequestBean.getAccountId());
                params.put("status", resultBean.getStatus());
                params.put("statusDesc","未设置过交易密码，请先设置交易密码");
                params.put("chkValue", resultBean.getChkValue());
                params.put("acqRes",transPasswordRequestBean.getAcqRes());
                CommonSoaUtils.noRetPostThree(transPasswordRequestBean.getBgRetUrl(), params);
            }
            return modelAndView;
        }
        Integer userId = user.getUserId();
        UsersInfo usersInfo=transPasswordService.getUsersInfoByUserId(userId);
        // 同步调用路径
        String retUrl = PropUtils.getSystem(CustomConstants.HTTP_HYJF_WEB_URL)
                + ThirdPartyTransPasswordDefine.REQUEST_MAPPING + ThirdPartyTransPasswordDefine.RETURL_SYN_RESETPASSWORD_ACTION + ".do?acqRes="+
                transPasswordRequestBean.getAcqRes()+"&callback="+transPasswordRequestBean.getRetUrl().replace("#", "*-*-*");
        // 异步调用路
        String bgRetUrl = PropUtils.getSystem(CustomConstants.HTTP_HYJF_WEB_URL)
                + ThirdPartyTransPasswordDefine.REQUEST_MAPPING + ThirdPartyTransPasswordDefine.RETURN_ASY_RESETPASSWORD_ACTION + ".do?acqRes="+
                transPasswordRequestBean.getAcqRes()+"&callback="+transPasswordRequestBean.getBgRetUrl().replace("#", "*-*-*");

        System.out.println("retUrl:"+retUrl);
        System.out.println("bgRetUrl:"+bgRetUrl);
        // 调用设置密码接口
        BankCallBean bean = new BankCallBean();
        bean.setVersion(BankCallConstant.VERSION_10);// 接口版本号
        bean.setTxCode(BankCallConstant.TXCODE_PASSWORD_RESET);// 消息类型(用户开户)
        bean.setInstCode(PropUtils.getSystem(BankCallConstant.BANK_INSTCODE));// 机构代码
        bean.setBankCode(PropUtils.getSystem(BankCallConstant.BANK_BANKCODE));
        bean.setTxDate(GetOrderIdUtils.getTxDate());
        bean.setTxTime(GetOrderIdUtils.getTxTime());
        bean.setSeqNo(GetOrderIdUtils.getSeqNo(6));
        bean.setChannel(transPasswordRequestBean.getChannel());
        bean.setIdType(BankCallConstant.ID_TYPE_IDCARD);
        bean.setAccountId(String.valueOf(bankOpenAccount.getAccount()));//电子账号
        bean.setIdNo(usersInfo.getIdcard());
        bean.setName(usersInfo.getTruename());
        bean.setMobile(user.getMobile());

        bean.setRetUrl(retUrl);// 页面同步返回 URL
        bean.setNotifyUrl(bgRetUrl);// 页面异步返回URL(必须)

        // 操作者ID
        bean.setLogUserId(String.valueOf(userId));
        bean.setLogBankDetailUrl(BankCallConstant.BANK_URL_MOBILE);
        bean.setLogOrderId(GetOrderIdUtils.getOrderId2(userId));
        // 跳转到汇付天下画面
        try {
            modelAndView = BankCallUtils.callApi(bean);
        } catch (Exception e) {
            e.printStackTrace();
            modelAndView = new ModelAndView(ThirdPartyTransPasswordDefine.PASSWORD_ERROR_PATH);
            modelAndView.addObject("message", "调用银行接口失败！");
        }
        logger.info("修改交易密码 end");
        return modelAndView;
    }
    *//**
     * 修改交易密码同步回调
     *
     * @param request
     * @param response
     * @return
     *//*

    @RequestMapping(ThirdPartyTransPasswordDefine.RETURL_SYN_RESETPASSWORD_ACTION)
    public ModelAndView resetPasswordReturn(HttpServletRequest request, HttpServletResponse response,
                                            @ModelAttribute BankCallBean bean) {
        logger.info("修改交易密码同步回调start");
        ModelAndView modelAndView = new ModelAndView(ApplyDefine.CALL_BACK_TRANSPASSWORD_VIEW);
        ThirdPartyTransPasswordResultBean repwdResult = new ThirdPartyTransPasswordResultBean();
        bean.convert();
        repwdResult.setCallBackAction(request.getParameter("callback").replace("*-*-*","#"));
        int userId = Integer.parseInt(bean.getLogUserId());
        BankOpenAccount bankOpenAccount = transPasswordService.getBankOpenAccount(userId);


        repwdResult.set("accountId", bankOpenAccount.getAccount());
        // 返回失败
        if (bean.getRetCode()!=null&&!BankCallConstant.RESPCODE_SUCCESS.equals(bean.getRetCode())) {
            modelAndView = new ModelAndView(ThirdPartyTransPasswordDefine.PASSWORD_ERROR_PATH);

            BaseResultBean resultBean = new BaseResultBean();
            resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE999999);
            repwdResult.set("chkValue", resultBean.getChkValue());
            repwdResult.set("status", resultBean.getStatus());
            repwdResult.set("acqRes",request.getParameter("acqRes"));
            modelAndView.addObject("statusDesc", "交易密码修改失败,失败原因：" + transPasswordService.getBankRetMsg(bean.getRetCode()));
            return modelAndView;
        }

        modelAndView.addObject("statusDesc", "修改交易密码成功");
        BaseResultBean resultBean = new BaseResultBean();
        resultBean.setStatusForResponse(ErrorCodeConstant.SUCCESS);
        repwdResult.set("chkValue", resultBean.getChkValue());
        repwdResult.set("status", resultBean.getStatus());
        repwdResult.set("acqRes",request.getParameter("acqRes"));
        modelAndView.addObject("callBackForm", repwdResult);
        logger.info("修改交易密码同步回调end");
        return modelAndView;
    }
    *//**
     * 修改交易密码异步回调
     *
     * @param request
     *
     * @param response
     * @return
     *//*
    @ResponseBody
    @RequestMapping(ThirdPartyTransPasswordDefine.RETURN_ASY_RESETPASSWORD_ACTION)
    public BankCallResult resetPasswordBgreturn(HttpServletRequest request, HttpServletResponse response,
                                                @ModelAttribute BankCallBean bean) {
        logger.info("修改交易密码异步回调start");
        // 返回值  9-22修改
        BankCallResult result = new BankCallResult();
        String message = "";
        String status = "";
        Map<String, String> params = new HashMap<String, String>();
        bean.convert();
        int userId = Integer.parseInt(bean.getLogUserId());
        Users user = this.transPasswordService.getUsers(userId);

        BankOpenAccount bankOpenAccount = transPasswordService.getBankOpenAccount(userId);

        // 成功或审核中
        if (user != null && BankCallConstant.RESPCODE_SUCCESS.equals(bean.get(BankCallConstant.PARAM_RETCODE))) {
            message = "交易密码修改成功";
            status = ErrorCodeConstant.SUCCESS;
        }else{
            // 设置交易密码
            message = "交易密码修改失败,失败原因：" + transPasswordService.getBankRetMsg(bean.getRetCode());
            status = ErrorCodeConstant.SUCCESS;
        }


        logger.info("修改交易密码同步回调end");

        params.put("accountId", bankOpenAccount.getAccount());
        params.put("status", status);
        params.put("statusDesc",message);
        params.put("acqRes",request.getParameter("acqRes"));
        BaseResultBean resultBean = new BaseResultBean();
        resultBean.setStatusForResponse(status);
        params.put("chkValue", resultBean.getChkValue());


        CommonSoaUtils.noRetPostThree(request.getParameter("callback").replace("*-*-*","#"), params);

        logger.info("修改交易密码同步回调end");
        result.setMessage("设置交易密码成功");
        result.setStatus(true);

        return result;

    }*/
}