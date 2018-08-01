package com.hyjf.cs.user.controller.api.bindcard;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.util.GetCilentIP;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.user.bean.BaseResultBean;
import com.hyjf.cs.user.bean.BindCardPageBean;
import com.hyjf.cs.user.bean.BindCardPageRequestBean;
import com.hyjf.cs.user.bean.BindCardPageResultBean;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.bindcard.BindCardService;
import com.hyjf.cs.user.util.ErrorCodeConstant;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.soa.apiweb.CommonSoaUtils;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Api端绑卡
 * @author hesy
 * @version AppBindCardController, v0.1 2018/7/19 9:34
 */
@Api(tags = "api端-绑卡")
@RestController
@RequestMapping("/hyjf-api/server/user/bindcardpage")
public class ApiBindCardController extends BaseUserController {
    @Autowired
    BindCardService bindCardService;
    @Autowired
    SystemConfig systemConfig;

    /**
     * 用户页面绑卡
     * @return
     */
    @PostMapping("/bind")
    public ModelAndView userBindCardPlus(@RequestBody BindCardPageRequestBean bankCardRequestBean, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView("/bank/user/trusteePay/error");
        logger.info("请求页面绑卡接口参数" + JSONObject.toJSONString(bankCardRequestBean, true) + "]");

        //验证请求参数
        if (Validator.isNull(bankCardRequestBean.getAccountId())||
                Validator.isNull(bankCardRequestBean.getInstCode())||
                Validator.isNull(bankCardRequestBean.getRetUrl())||
                Validator.isNull(bankCardRequestBean.getPlatform())||
                Validator.isNull(bankCardRequestBean.getForgotPwdUrl())||
                Validator.isNull(bankCardRequestBean.getChannel())||
                Validator.isNull(bankCardRequestBean.getNotifyUrl())) {
            logger.info("-------------------绑卡请求参数非法--------------------");
            getErrorMV(bankCardRequestBean, modelAndView,ErrorCodeConstant.STATUS_CE000001);
            bankCardRequestBean.doNotify(bankCardRequestBean.getErrorMap(ErrorCodeConstant.STATUS_CE000001, "请求参数非法"));
            return modelAndView;
        }

        //验签
        if(!bindCardService.verifyRequestSign(bankCardRequestBean, "/server/user/bindcardpage/bind")){
            logger.info("-------------------验签失败！--------------------");

            getErrorMV(bankCardRequestBean, modelAndView,ErrorCodeConstant.STATUS_CE000002);
            bankCardRequestBean.doNotify(bankCardRequestBean.getErrorMap(ErrorCodeConstant.STATUS_CE000002, "验签失败"));
            return modelAndView;
        }

        Map<String,String> checkMap = bindCardService.checkParamBindCardPageApi(bankCardRequestBean);
        if(!checkMap.isEmpty()){
            getErrorMV(bankCardRequestBean, modelAndView,checkMap.get("key"));
            bankCardRequestBean.doNotify(bankCardRequestBean.getErrorMap(ErrorCodeConstant.STATUS_CE000004, checkMap.get("msg")));
            return modelAndView;
        }

        //根据账号找出用户ID
        BankOpenAccountVO bankOpenAccount = bindCardService.getBankOpenAccountByAccount(bankCardRequestBean.getAccountId());
        UserVO userVO = bindCardService.getUsersById(bankOpenAccount.getUserId());
        UserInfoVO userInfo = bindCardService.getUserInfo(bankOpenAccount.getUserId());


        // 跳转到江西银行天下画面
        try {
            // 同步调用路径
            String retUrl = systemConfig.apiWebUrl + request.getContextPath()
                    + "/server/user/bindcardpage/bindCardReturn.do?acqRes="
                    + bankCardRequestBean.getAcqRes()
                    + "&callback=" + bankCardRequestBean.getRetUrl().replace("#", "*-*-*");
            // 异步调用路
            String bgRetUrl = systemConfig.apiWebUrl + request.getContextPath()
                    + "/server/user/bindcardpage/bindCardReturn.do?phone="+userVO.getMobile()+"&acqRes="
                    + bankCardRequestBean.getAcqRes() + "&callback=" + bankCardRequestBean.getNotifyUrl().replace("#", "*-*-*");

            // 拼装参数 调用江西银行
            BindCardPageBean bean = new BindCardPageBean();
            bean.setTxCode(BankCallConstant.TXCODE_BIND_CARD_PAGE);
            bean.setChannel(bankCardRequestBean.getChannel());
            bean.setIdType(BankCallConstant.ID_TYPE_IDCARD);
            bean.setIdNo(userInfo.getIdcard());
            bean.setName(userInfo.getTruename());
            bean.setAccountId(bankOpenAccount.getAccount());
            bean.setUserIP(GetCilentIP.getIpAddr(request));
            bean.setUserId(userVO.getUserId());
            bean.setRetUrl(retUrl);
            bean.setSuccessfulUrl(retUrl+"&isSuccess=1");
            bean.setNotifyUrl(bgRetUrl);
            bean.setForgetPassworedUrl(bankCardRequestBean.getForgotPwdUrl());
            bean.setPlatform(bankCardRequestBean.getPlatform());
            modelAndView = bindCardService.getCallbankMV(bean);

            logger.info("绑卡调用页面end");
            return modelAndView;
        } catch (Exception e) {
            logger.info("---调用银行接口失败~!---");
            e.printStackTrace();

            getErrorMV(bankCardRequestBean, modelAndView,ErrorCodeConstant.STATUS_CE999999);
            bankCardRequestBean.doNotify(bankCardRequestBean.getErrorMap(ErrorCodeConstant.STATUS_CE999999, "调用银行接口失败"));
            return modelAndView;
        }
    }

    private ModelAndView getErrorMV(BindCardPageRequestBean payRequestBean, ModelAndView modelAndView,String status) {
        BindCardPageResultBean repwdResult = new BindCardPageResultBean();
        BaseResultBean resultBean = new BaseResultBean();
        resultBean.setStatusForResponse(status);
        repwdResult.setCallBackAction(payRequestBean.getRetUrl());
        repwdResult.set("chkValue", resultBean.getChkValue());
        repwdResult.set("status", resultBean.getStatus());
        repwdResult.set("acqRes",payRequestBean.getAcqRes());
        modelAndView.addObject("callBackForm", repwdResult);
        return modelAndView;
    }

    /**
     * 页面绑卡同步回调
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/bindCardReturn")
    public ModelAndView openAccountReturn(HttpServletRequest request, HttpServletResponse response,
                                          @ModelAttribute BankCallBean bean) {
        BindCardPageResultBean repwdResult = new BindCardPageResultBean();
        repwdResult.setCallBackAction(request.getParameter("callback").replace("*-*-*","#"));
        ModelAndView modelAndView = new ModelAndView("/callback/callback_trusteepay");
        String frontParams = request.getParameter("frontParams");
        String isSuccess = request.getParameter("isSuccess");
        if(StringUtils.isBlank(bean.getRetCode())&&StringUtils.isNotBlank(frontParams)){
            JSONObject jsonParm = JSONObject.parseObject(frontParams);
            if(jsonParm.containsKey("RETCODE")){
                bean.setRetCode(jsonParm.getString("RETCODE"));
            }
        }
        bean.convert();
        // 银行返回响应代码
        String retCode = StringUtils.isNotBlank(bean.getRetCode()) ? bean.getRetCode() : "";
        logger.info("绑卡同步返回值,用户ID:[" + bean.getLogUserId() + "],retCode:[" + retCode + "]");

        if (BankCallConstant.RESPCODE_SUCCESS.equals(retCode)||"1".equals(isSuccess)) {
            // 成功
            modelAndView.addObject("statusDesc", "绑卡成功！");
            BaseResultBean resultBean = new BaseResultBean();
            resultBean.setStatusForResponse(ErrorCodeConstant.SUCCESS);
            repwdResult.set("chkValue", resultBean.getChkValue());
            repwdResult.set("status", resultBean.getStatus());
        } else {
            BaseResultBean resultBean = new BaseResultBean();
            resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE999999);
            repwdResult.set("chkValue", resultBean.getChkValue());
            repwdResult.set("status", resultBean.getStatus());
        }
        repwdResult.set("acqRes",request.getParameter("acqRes"));
        modelAndView.addObject("callBackForm", repwdResult);
        logger.info("绑卡同步回调end");
        return modelAndView;
    }

    /**
     * 页面绑卡异步回调
     *
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("/bindCardBgreturn")
    public BankCallResult bgreturn(HttpServletRequest request, HttpServletResponse response,
                                   @ModelAttribute BankCallBean bean) {
        // 上送的异步地址里面有
        Map<String, String> params = new HashMap<String, String>();
        BankCallResult result = new BankCallResult();
        String phone = request.getParameter("phone");
        logger.info("页面绑卡异步回调start");
        bean.setMobile(phone);
        bean.convert();
        String status = "";
        String statusDesc = "";
        int userId = Integer.parseInt(bean.getLogUserId());

        // 绑卡后处理
        try {
            // 保存银行卡信息
            bindCardService.updateAfterBindCard(bean);
            if (BankCallConstant.RESPCODE_SUCCESS.equals(bean.getRetCode())) {
                params.put("cardNo", bean.getCardNo());
                params.put("payAllianceCode", bean.getPayAllianceCode());
                status = ErrorCodeConstant.SUCCESS;
                statusDesc = "绑卡成功";
            }else{
                status = ErrorCodeConstant.SUCCESS;
                statusDesc = bindCardService.getBankRetMsg(bean.getRetCode());
            }

        } catch (Exception e) {
            e.printStackTrace();
            status = ErrorCodeConstant.STATUS_CE999999;
            statusDesc = "调用银行接口失败";
        }
        params.put("accountId", bean.getAccountId());
        params.put("statusDesc",statusDesc);
        BaseResultBean resultBean = new BaseResultBean();
        resultBean.setStatusForResponse(status);
        params.put("chkValue", resultBean.getChkValue());
        params.put("acqRes",request.getParameter("acqRes"));
        CommonSoaUtils.noRetPostThree(request.getParameter("callback").replace("*-*-*","#"), params);
        logger.info("页面绑卡成功,用户ID:[" + userId + ",用户电子账户号:[" + bean.getAccountId() + "]");
        result.setStatus(true);
        return result;
    }
}
