/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.app.recharge;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.BankCardVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.DES;
import com.hyjf.common.util.SecretUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.result.AppRechargeInfoResult;
import com.hyjf.cs.user.service.recharge.AppRechargeService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author fq
 * @version AppRechargeController, v0.1 2018/7/30 9:34
 */
@Api(value = "用户充值", description = "用户充值")
@RestController
@RequestMapping("/hyjf-app/user/bank/recharge")
public class AppRechargeController extends BaseUserController {
    @Autowired
    private AppRechargeService appRechargeService;

    @ApiOperation(value = "短信充值发送验证码", notes = "短信充值发送验证码")
    @RequestMapping("/sendCode")
    public AppRechargeInfoResult sendCode(HttpServletRequest request, HttpServletResponse response) {
        logger.info("短信充值发送验证码开始......");
        AppRechargeInfoResult result = new AppRechargeInfoResult("/hyjf-app/user/bank/recharge/sendCode");
        String sign = request.getParameter("sign");
        Integer userId = SecretUtil.getUserId(sign); // 用户ID
        String cardNo = request.getParameter("cardNo");// 开户银行代号
        String mobile = request.getParameter("mobile");// 用户的手机号
        String isMencry = request.getParameter("isMencry");

        String message = "系统参数错误";
        String status = CustomConstants.APP_STATUS_FAIL;
        JSONObject checkResult;

        // 检查参数
        if(userId == null || StringUtils.isBlank(mobile) || StringUtils.isBlank(sign) || StringUtils.isBlank(cardNo)){
            result.setStatusDesc(message);
            result.setStatus(status);
            return result;
        }

        // 取得加密用的Key
        if(!"1".equals(isMencry)){
            String key = SecretUtil.getKey(sign);
            if (Validator.isNull(key)) {
                result.setStatus("1");
                result.setStatusDesc("请求参数非法");
                return result;
            }

            // 解密
            mobile = DES.decodeValue(key, mobile);

        }

        if(!Validator.isMobile(mobile)){
            result.setStatusDesc(message);
            result.setStatus(status);
            return result;
        }

        // 判断用户是否被禁用
        UserVO users = this.appRechargeService.getUsersById(userId);
        if (users == null || users.getStatus() == 1) {
            result.setStatusDesc("对不起,该用户已经被禁用。");
            result.setStatus(status);
            return result;
        }
        if (users.getUserType() == 1) {
            result.setStatusDesc("对不起,企业用户只能通过线下充值。");
            result.setStatus(status);
            return result;
        }
        // 用户银行卡号
        if (StringUtils.isBlank(cardNo)) {
            // 获取绑定的银行卡号
            List<BankCardVO> bankCardVOList = this.appRechargeService.getBankOpenAccountById(users);
			if (!CollectionUtils.isEmpty(bankCardVOList)) {
				BankCardVO bankCardVO = bankCardVOList.get(0);
				cardNo = bankCardVO.getCardNo();
			}
        }

        // 调用短信发送接口
        BankCallBean mobileBean = this.appRechargeService.callSendCode(userId, mobile, "directRechargeOnline", BankCallConstant.CHANNEL_APP, cardNo);

        if (Validator.isNull(mobileBean)) {
            message = "短信验证码发送失败，请稍后再试！";
            result.setStatusDesc(message);
            result.setStatus(status);
            return result;
        }

        // 短信发送返回结果码
        String retCode = mobileBean.getRetCode();
        if (!BankCallConstant.RESPCODE_SUCCESS.equals(retCode)
                && !BankCallConstant.RESPCODE_MOBILE_REPEAT.equals(retCode)) {
            message = "短信验证码发送失败！";
            result.setStatusDesc(message);
            result.setStatus(status);
            return result;
        }
        if (Validator.isNull(mobileBean.getSrvAuthCode()) && !BankCallConstant.RESPCODE_MOBILE_REPEAT.equals(retCode)) {
            message = "短信验证码发送失败！";
            result.setStatusDesc(message);
            result.setStatus(status);
            return result;
        }
        String smsSeq = mobileBean.getSmsSeq();
        message = "短信发送成功！";
        status = CustomConstants.APP_STATUS_SUCCESS;
        result.setStatusDesc(message);
        result.setSmsSeq(smsSeq);
        result.setStatus(status);
        return result;
    }
}
