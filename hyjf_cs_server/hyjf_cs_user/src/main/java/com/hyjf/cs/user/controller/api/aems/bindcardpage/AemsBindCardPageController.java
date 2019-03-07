package com.hyjf.cs.user.controller.api.aems.bindcardpage;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.util.GetCilentIP;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.common.controller.BaseController;
import com.hyjf.cs.user.bean.AemsBindCardPageRequestBean;
import com.hyjf.cs.user.bean.BaseResultBean;
import com.hyjf.cs.user.bean.BindCardPageBean;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.constants.ErrorCodeConstant;
import com.hyjf.cs.user.service.aems.bindcardpage.AemsBindCardPageService;
import com.hyjf.cs.user.util.SignUtil;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.soa.apiweb.CommonSoaUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @version AemsBindCardPageController, v0.1 2018/12/6 14:15
 * @Author: Zha Daojian
 */

@Api(value = "api端-AEMS用户页绑卡页面调用",tags = "api端-AEMS户页绑卡页面调用")
@RestController
@RequestMapping("/hyjf-api/aems/bindcardpage")
public class AemsBindCardPageController extends BaseController {

    @Autowired
    private AemsBindCardPageService bindCardService;

    @Autowired
    SystemConfig systemConfig;

    /**
     * 用户页面绑卡
     *
     * @param bankCardRequestBean
     * @param request
     * @param response
     * @return
     */
    @PostMapping("/bind")
    @ApiParam(required = true, name = "findDetailById", value = "AEMS用户页面绑卡")
    @ApiOperation(value = "AEMS用户页面绑卡", httpMethod = "POST", notes = "AEMS用户页面绑卡")
    public ModelAndView userBindCardPlus(@RequestBody AemsBindCardPageRequestBean bankCardRequestBean, HttpServletRequest request, HttpServletResponse response) {
        logger.info("请求页面绑卡接口参数" + JSONObject.toJSONString(bankCardRequestBean, true) + "]");
        ModelAndView modelAndView = new ModelAndView();
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("callBackAction",bankCardRequestBean.getRetUrl());
        //验证请求参数
        if (Validator.isNull(bankCardRequestBean.getAccountId())||
                Validator.isNull(bankCardRequestBean.getInstCode())||
                Validator.isNull(bankCardRequestBean.getRetUrl())||
                Validator.isNull(bankCardRequestBean.getPlatform())||
                Validator.isNull(bankCardRequestBean.getChannel())||
                Validator.isNull(bankCardRequestBean.getNotifyUrl())) {
            logger.info("-------------------绑卡请求参数非法--------------------");
            paramMap.put("status", ErrorCodeConstant.STATUS_CE000001);
            paramMap.put("statusDesc","请求参数非法");
            return callbackErrorView(paramMap);
        }

        //验签
        if(!SignUtil.AEMSVerifyRequestSign(bankCardRequestBean, "/aems/bindcardpage/bind")){
            logger.info("-------------------验签失败！--------------------");

            paramMap.put("status", ErrorCodeConstant.STATUS_CE000002);
            paramMap.put("statusDesc","验签失败");
            return callbackErrorView(paramMap);
        }

        Map<String,String> checkMap = bindCardService.checkParamBindCardPageApi(bankCardRequestBean);
        if(!checkMap.isEmpty()){
            paramMap.put("status", checkMap.get("key"));
            paramMap.put("statusDesc", checkMap.get("msg"));
            return callbackErrorView(paramMap);
        }

        //根据账号找出用户ID
        BankOpenAccountVO bankOpenAccount = bindCardService.getBankOpenAccountByAccount(bankCardRequestBean.getAccountId());
        UserVO userVO = bindCardService.getUsersById(bankOpenAccount.getUserId());
        UserInfoVO userInfo = bindCardService.getUserInfo(bankOpenAccount.getUserId());


        // 跳转到江西银行天下画面
        try {
            // 同步调用路径
            String retUrl = systemConfig.getServerHost()
                    + "/hyjf-api/aems/bindcardpage/bindCardReturn?acqRes="
                    + bankCardRequestBean.getAcqRes()
                    + "&callback=" + bankCardRequestBean.getRetUrl().replace("#", "*-*-*");
            // 异步调用路
            String bgRetUrl = systemConfig.getServerHost()
                    + "/hyjf-api/aems/bindcardpage/bindCardReturn?phone="+userVO.getMobile()+"&acqRes="
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
            logger.error(e.getMessage());

            paramMap.put("status", ErrorCodeConstant.STATUS_CE999999);
            paramMap.put("statusDesc","调用银行接口失败");
            return callbackErrorView(paramMap);
        }
    }


    /**
     * 页面绑卡同步回调
     *
     * @param request
     * @return
     */
    @RequestMapping("/bindCardReturn")
    @ApiOperation(value = "绑卡同步回调", notes = "绑卡同步回调")
    public ModelAndView pageReturn(HttpServletRequest request) {
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("status", "success");
        resultMap.put("callBackAction", request.getParameter("callback").replace("*-*-*","#"));
        String isSuccess = request.getParameter("isSuccess");
        // 银行返回响应代码

        if ("1".equals(isSuccess)) {
            // 成功
            resultMap.put("status", ErrorCodeConstant.SUCCESS);
            resultMap.put("statusDesc", "绑卡成功！");
        } else {
            resultMap.put("status", ErrorCodeConstant.STATUS_CE999999);
            resultMap.put("statusDesc", "绑卡失败！");
        }
        logger.info("绑卡同步回调end");
        return callbackErrorView(resultMap);
    }

    /**
     * 页面绑卡异步回调
     *
     * @param request
     * @param response
     * @return
     */
    @PostMapping("/bindCardBgreturn")
    @ApiOperation(value = "绑卡异步回调", notes = "绑卡异步回调")
    public BankCallResult bgreturn(HttpServletRequest request, HttpServletResponse response,
                                   @RequestBody BankCallBean bean) {
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
            logger.error(e.getMessage());
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
