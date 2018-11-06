package com.hyjf.cs.user.controller.api.unbindcardpage;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.user.BankCardVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetCilentIP;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.util.StringPool;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.user.bean.*;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.constants.ErrorCodeConstant;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.bindcard.BindCardService;
import com.hyjf.cs.user.service.unbindcard.UnBindCardService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import com.hyjf.soa.apiweb.CommonSoaUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Api端解绑银行卡
 * @author nxl
 * @version AppBindCardController, v0.1 2018/10/16 9:34
 */
@Api(value = "api端-解绑银行卡(页面调用)", tags = "api端-解绑银行卡(页面调用)")
@RestController
@RequestMapping("/hyjf-api/server/user/unbindCardPage")
public class ApiUnBindCardPageController extends BaseUserController {
    @Autowired
    UnBindCardService unBindCardService;
    @Autowired
    SystemConfig systemConfig;

    /**
     * 用户页面绑卡
     * @return
     */
    @PostMapping("/deleteCardPage.do")
    @ApiOperation(value = "解绑银行卡", notes = "解绑银行卡")
    public ModelAndView userBindCardPlus(@RequestBody UnbindCardPageRequestBean unbindCardPageRequestBean, HttpServletRequest request, HttpServletResponse response) {
        logger.info("-----------------------------"+unbindCardPageRequestBean.getAccountId() + "用户解除绑定银行卡开始-----------------------------");
        logger.info("第三方请求参数：" + JSONObject.toJSONString(unbindCardPageRequestBean));
        ModelAndView modelAndView = new ModelAndView("/bank/user/trusteePay/error");
        try {
            // 验证请求参数
            if (Validator.isNull(unbindCardPageRequestBean.getAccountId())||
                    Validator.isNull(unbindCardPageRequestBean.getMobile())||
                    Validator.isNull(unbindCardPageRequestBean.getCardNo())||
                    Validator.isNull(unbindCardPageRequestBean.getInstCode())||
                    Validator.isNull(unbindCardPageRequestBean.getRetUrl())||
                    Validator.isNull(unbindCardPageRequestBean.getNotifyUrl())||
                    Validator.isNull(unbindCardPageRequestBean.getForgotPwdUrl())){
                getErrorMV(unbindCardPageRequestBean, modelAndView, ErrorCodeConstant.STATUS_CE000001, "请求参数非法");
                logger.info("-------------------请求参数非法--------------------");
                return modelAndView;
            }
            //验签
            if(!unBindCardService.verifyRequestSign(unbindCardPageRequestBean, "/server/user/unbindCardPage/deleteCardPage")){
                getErrorMV(unbindCardPageRequestBean, modelAndView, ErrorCodeConstant.STATUS_CE000002, "验签失败");
                logger.info("-------------------验签失败！--------------------");
                return modelAndView;
            }
            //根据账号找出用户ID
            BankOpenAccountVO bankOpenAccount =unBindCardService.getBankOpenAccountByAccount(unbindCardPageRequestBean.getAccountId());
            if(bankOpenAccount == null){
                getErrorMV(unbindCardPageRequestBean, modelAndView, ErrorCodeConstant.STATUS_CE000004, "没有根据电子银行卡找到用户");
                logger.info("没有根据电子银行卡找到用户 "+unbindCardPageRequestBean.getAccountId());
                return modelAndView;
            }

            UserVO user =unBindCardService.getUsersById(unbindCardPageRequestBean.getUserId());//用户ID
            if(user.getUserType()==1){
                //企业用户提示联系客服
                getErrorMV(unbindCardPageRequestBean, modelAndView, ErrorCodeConstant.STATUS_CE000002, "企业用户解绑请联系客服");
                logger.info("-------------------企业用户解绑请联系客服！--------------------");
                return modelAndView;
            }
            Integer userId=user.getUserId();
            // 用户余额大于零不让解绑
            AccountVO account =unBindCardService.getAccountByUserId(userId);
            // 用户在银行的账户余额
            BigDecimal bankBalance =unBindCardService.getBankBalance(userId,bankOpenAccount.getAccount());
            if ((Validator.isNotNull(account.getBankBalance()) && account.getBankBalance().compareTo(BigDecimal.ZERO) > 0)
                    || ((Validator.isNotNull(bankBalance) && bankBalance.compareTo(BigDecimal.ZERO) > 0))) {
                getErrorMV(unbindCardPageRequestBean, modelAndView, ErrorCodeConstant.STATUS_BC000004, "抱歉，请先清空当前余额和待收后，再申请解绑。");
                logger.info("解绑失败，余额大于0元是不能解绑银行卡"+unbindCardPageRequestBean.getAccountId());
                return modelAndView;
            }
            // 根据银行卡Id获取用户的银行卡信息
            BankCardVO bankCard =unBindCardService.queryUserCardValid(userId.toString(),unbindCardPageRequestBean.getCardNo());
            if (bankCard == null || StringUtils.isEmpty(bankCard.getCardNo())) {
                getErrorMV(unbindCardPageRequestBean, modelAndView, ErrorCodeConstant.STATUS_BC000002, "获取用户银行卡信息失败 ");
                logger.info("获取用户银行卡信息失败"+unbindCardPageRequestBean.getCardNo());
                return modelAndView;
            }
            UserInfoVO usersInfo =unBindCardService.getUserInfo(userId);
            // 拼装参数 调用江西银行
            // 同步调用路径
            String retUrl = systemConfig.getServerHost() + request.getContextPath()
                    + "/server/user/unbindCardPage/return"+".do?acqRes="
                    + unbindCardPageRequestBean.getAcqRes() + StringPool.AMPERSAND + "callback="
                    + unbindCardPageRequestBean.getRetUrl().replace("#", "*-*-*");

            // 异步调用路
            String bgRetUrl =systemConfig.getServerHost() + request.getContextPath()
                    + "/server/user/unbindCardPage/notifyReturn"+ ".do?acqRes="
                    + unbindCardPageRequestBean.getAcqRes() + "&callback=" + unbindCardPageRequestBean.getNotifyUrl().replace("#", "*-*-*");

            // 拼装参数 调用江西银行
            DeleteCardPageBean bean = new DeleteCardPageBean();
            bean.setUserId(user.getUserId());
            bean.setTxCode(BankCallConstant.TXCODE_BIND_CARD_PAGE);
            bean.setAccountId(bankOpenAccount.getAccount());
            bean.setName(usersInfo.getTruename());
            bean.setIdType(BankCallConstant.ID_TYPE_IDCARD);
            bean.setIdNo(usersInfo.getIdcard());
            bean.setCardNo(bankCard.getCardNo());// 银行卡号
            bean.setMobile(user.getMobile());
            bean.setRetUrl(retUrl);
            bean.setForgotPwdUrl(unbindCardPageRequestBean.getForgotPwdUrl());
            bean.setSuccessfulUrl(retUrl+"&isSuccess=1");
            bean.setNotifyUrl(bgRetUrl);
            bean.setChannel(unbindCardPageRequestBean.getChannel());// 交易渠道
            bean.setPlatform(unbindCardPageRequestBean.getPlatform());
            modelAndView =getCallbankMV(bean);
            logger.info("解卡调用页面end");
            logger.info("-----------------------------"+unbindCardPageRequestBean.getAccountId() + "用户解除绑定银行卡结束-----------------------------");
            return modelAndView;
        } catch(Exception e){
            e.printStackTrace();
            logger.info("解卡异常,异常信息:[" + e.toString() + "]");
            return null;
        }
    }
    private ModelAndView getErrorMV(UnbindCardPageRequestBean unBindCardPageRequestBean, ModelAndView modelAndView,
                                    String status, String des) {
        UnbindCardPageResultBean unbindCardPageResultBean = new UnbindCardPageResultBean();
        BaseResultBean resultBean = new BaseResultBean();
        resultBean.setStatusForResponse(status);
        unbindCardPageResultBean.setCallBackAction(unBindCardPageRequestBean.getRetUrl());
        unbindCardPageResultBean.set("chkValue", resultBean.getChkValue());
        unbindCardPageResultBean.set("status", resultBean.getStatus());
        unbindCardPageResultBean.set("phone", unBindCardPageRequestBean.getMobile());
        unbindCardPageResultBean.setDesc(des);
        unbindCardPageResultBean.setAcqRes(unBindCardPageRequestBean.getAcqRes());
        Map<String, String> params = new HashMap<String, String>();
        params.put("accountId", unBindCardPageRequestBean.getAccountId());
        params.put("status", status);
        params.put("statusDesc",des);
        params.put("chkValue", resultBean.getChkValue());
        CommonSoaUtils.noRetPostThree(unBindCardPageRequestBean.getNotifyUrl(), params);
        modelAndView.addObject("callBackForm", unbindCardPageResultBean);
        return modelAndView;
    }
    /**
     * 解卡页面调用(合规四期需求)
     * @param bean
     * @return
     */
    public ModelAndView getCallbankMV(DeleteCardPageBean bean) {
        ModelAndView mv = new ModelAndView();
        // 获取共同参数
        String bankCode =systemConfig.getBankCode();;
        String bankInstCode =systemConfig.getBankInstcode();
        String orderDate = GetOrderIdUtils.getOrderDate();
        String txDate = GetOrderIdUtils.getTxDate();
        String txTime = GetOrderIdUtils.getTxTime();
        String seqNo = GetOrderIdUtils.getSeqNo(6);
        // 调用开户接口
        BankCallBean bindCardBean = new BankCallBean();
        //通用必填参数
        bindCardBean.setVersion(BankCallConstant.VERSION_10);// 接口版本号
        bindCardBean.setInstCode(bankInstCode);// 机构代码
        bindCardBean.setBankCode(bankCode);
        bindCardBean.setTxDate(txDate);
        bindCardBean.setTxTime(txTime);
        bindCardBean.setSeqNo(seqNo);
        bindCardBean.setChannel(bean.getChannel());
        //解卡参数
        bindCardBean.setTxCode(BankCallConstant.TXCODE_ACCOUNT_UNBINDCARD_PAGE);
        bindCardBean.setAccountId(bean.getAccountId());
        bindCardBean.setName(bean.getName());
        bindCardBean.setIdType(BankCallConstant.ID_TYPE_IDCARD);
        bindCardBean.setIdNo(bean.getIdNo());
        bindCardBean.setCardNo(bean.getCardNo());
        bindCardBean.setMobile(bean.getMobile());
        bindCardBean.setRetUrl(bean.getRetUrl());
        bindCardBean.setSuccessfulUrl(bean.getSuccessfulUrl());
        bindCardBean.setForgotPwdUrl(bean.getForgotPwdUrl());
        bindCardBean.setNotifyUrl(bean.getNotifyUrl());
        // 页面调用必须传的
        String orderId = GetOrderIdUtils.getOrderId2(bean.getUserId());
        bindCardBean.setLogBankDetailUrl(BankCallConstant.BANK_URL_UNBIND_CARD_PAGE);
        bindCardBean.setLogOrderId(orderId);
        bindCardBean.setLogOrderDate(orderDate);
        bindCardBean.setLogUserId(String.valueOf(bean.getUserId()));
        bindCardBean.setLogRemark("外部服务接口:解卡页面");
        bindCardBean.setLogIp(bean.getIp());
        bindCardBean.setLogClient(Integer.parseInt(bean.getPlatform()));
        try {
            mv = BankCallUtils.callApi(bindCardBean);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mv;
    }

    /**
     * 页面解卡同步回调
     * @param request
     * @param response
     * @return
     */
    @PostMapping("/return")
    @ApiOperation(value = "页面解卡同步回调", notes = "页面解卡同步回调")
    public ModelAndView unbindCardReturn(HttpServletRequest request, HttpServletResponse response,@ModelAttribute BankCallBean bean) {
        logger.info("==========解卡同步回调开始==============");
        ModelAndView modelAndView = new ModelAndView("/callback/callback_trusteepay");
        UnbindCardPageResultBean unbindCardPageResultBean = new UnbindCardPageResultBean();
        unbindCardPageResultBean.setCallBackAction(request.getParameter("callback").replace("*-*-*","#"));
        String isSuccess = request.getParameter("isSuccess");
        bean.convert();
        // 银行返回响应代码
        String retCode = StringUtils.isNotBlank(bean.getRetCode()) ? bean.getRetCode() : "";
        logger.info("解卡同步返回值,用户ID:[" + bean.getLogUserId() + "],retCode:[" + retCode + "]");
        // 解卡后处理
        try {
            BaseResultBean resultBean = new BaseResultBean();
            if (BankCallConstant.RESPCODE_SUCCESS.equals(retCode)||"1".equals(isSuccess)) {
                // 成功
                modelAndView.addObject("statusDesc", "解绑银行卡成功！");
                resultBean.setStatusForResponse(ErrorCodeConstant.SUCCESS);
                logger.info("================交易完成后,回调结束================");
            } else {
                //解卡失败
                modelAndView.addObject("statusDesc", "银行解卡失败");
                resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE999999);
                logger.info("================交易完成后,回调结束(银行解卡失败)================");
            }
            unbindCardPageResultBean.set("chkValue", resultBean.getChkValue());
            unbindCardPageResultBean.set("status", resultBean.getStatus());
            unbindCardPageResultBean.set("acqRes",request.getParameter("acqRes"));
            modelAndView.addObject("callBackForm", unbindCardPageResultBean);
            return modelAndView;
        } catch (Exception e) {
            BaseResultBean resultBean = new BaseResultBean();
            resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE999999);
            logger.info("操作异常");
            resultBean.setStatusDesc("系统异常,请稍后再试!");
            e.printStackTrace();
        }
        return modelAndView;
    }

    /**
     * 解卡异步回调方法
     * @param request
     * @param response
     * @param bean
     * @return
     */
    @PostMapping("/notifyReturn")
    @ApiOperation(value = "解卡异步回调方法", notes = "解卡异步回调方法")
    public BankCallResult bgreturn(HttpServletRequest request, HttpServletResponse response, @ModelAttribute BankCallBean bean) {
        BankCallResult result = new BankCallResult();
        BaseResultBean resultBean = new BaseResultBean();
        Map<String, String> params = new HashMap<String, String>();
        logger.info("页面解卡异步回调start");
        bean.convert();
        String message = "";
        String status = "";
        int userId = Integer.parseInt(bean.getLogUserId());
        // 绑卡后处理
        try {
            // 删除银行卡信息
            if(BankCallConstant.RESPCODE_SUCCESS.equals(bean.getRetCode())){
                // 删除银行卡信息
                unBindCardService.updateAfterUnBindCard(bean,userId);
                message = "解绑成功";
                status = ErrorCodeConstant.SUCCESS;
            }else {
                message = "解绑失败，,失败原因：" + unBindCardService.getBankRetMsg(bean.getRetCode());
                status = ErrorCodeConstant.STATUS_CE999999;
            }
        } catch (Exception e) {
            message = "解绑失败，,失败原因：" + unBindCardService.getBankRetMsg(bean.getRetCode());
            status = ErrorCodeConstant.STATUS_CE999999;
            e.printStackTrace();
        }
        params.put("accountId", bean.getAccountId());
        params.put("status", status);
        params.put("statusDesc",message);
        resultBean.setStatusForResponse(status);
        params.put("chkValue", resultBean.getChkValue());
        params.put("acqRes",request.getParameter("acqRes"));
        CommonSoaUtils.noRetPostThree(request.getParameter("callback").replace("*-*-*","#"), params);
        logger.info("页面解卡成功,用户ID:[" + userId + ",用户电子账户号:[" + bean.getAccountId() + "]");
        result.setStatus(true);
        return result;
    }
}
