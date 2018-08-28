package com.hyjf.cs.user.controller.api.bindcard;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.user.bean.BaseDefine;
import com.hyjf.cs.user.bean.BaseResultBean;
import com.hyjf.cs.user.bean.ThirdPartyBankCardPlusResultBean;
import com.hyjf.cs.user.bean.ThirdPartyBankCardRequestBean;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.constants.ErrorCodeConstant;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.bindcard.BindCardService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallMethodConstant;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 绑卡相关接口
 * @author hesy
 * @version ApiBindCardController, v0.1 2018/8/27 15:39
 */
@Api(value = "api端-绑卡(接口)", tags = "api端-绑卡（接口）")
@RestController
@RequestMapping("/hyjf-api/server/user/bankcard")
public class ApiBindCardController extends BaseUserController {
    @Autowired
    BindCardService bindCardService;
    @Autowired
    SystemConfig systemConfig;

    /**
     * 用户绑卡增强发送验证码接口
     * @param request
     * @param response
     * @param bankCardRequestBean
     * @return
     *//*
    @PostMapping("/sendPlusCode")
    public BaseResultBean sendPlusCode(HttpServletRequest request, HttpServletResponse response, @RequestBody ThirdPartyBankCardRequestBean bankCardRequestBean) {
        _log.info(bankCardRequestBean.getMobile()+"用户绑卡增强发送验证码接口-----------------------------");
        _log.info("第三方请求参数："+JSONObject.toJSONString(bankCardRequestBean));
        ThirdPartyBankCardPlusResultBean ret = new ThirdPartyBankCardPlusResultBean();

        if (Validator.isNull(bankCardRequestBean.getAccountId())||
                Validator.isNull(bankCardRequestBean.getMobile())||
                Validator.isNull(bankCardRequestBean.getCardNo())||
                Validator.isNull(bankCardRequestBean.getInstCode())) {

            _log.info("-------------------请求参数非法--------------------");
            ret.setStatus(ErrorCodeConstant.STATUS_CE000001);
            ret.setStatusDesc("请求参数非法");
            return ret;
        }

        //验签
        if(!bindCardService.verifyRequestSign(bankCardRequestBean, "/server/user/bankcard/sendPlusCode")){
            _log.info("-------------------验签失败！--------------------");
            ret.setStatus(ErrorCodeConstant.STATUS_CE000002);
            ret.setStatusDesc("验签失败");
            return ret;
        }


        //根据账号找出用户ID
        BankOpenAccount bankOpenAccount = bindCardService.getBankOpenAccount(bankCardRequestBean.getAccountId());
        if(bankOpenAccount == null){
            _log.info("-------------------没有根据电子银行卡找到用户"+bankCardRequestBean.getAccountId()+"！--------------------");
            ret.setStatus(ErrorCodeConstant.STATUS_CE000004);
            ret.setStatusDesc("没有根据电子银行卡找到用户");
            return ret;
        }
        Users user = bindCardService.getUsers(bankOpenAccount.getUserId());//用户ID
        if(user == null){
            _log.info("---用户不存在汇盈金服账户---");
            ret.setStatus(ErrorCodeConstant.STATUS_CE000006);
            ret.setStatusDesc("用户不存在汇盈金服账户");
            return ret;
        }
        if (user.getBankOpenAccount()==0) {
            _log.info("-------------------没有根据电子银行卡找到用户"+bankCardRequestBean.getAccountId()+"！--------------------");
            ret.setStatus(ErrorCodeConstant.STATUS_CE000004);
            ret.setStatusDesc("没有根据电子银行卡找到用户");
            return ret;
        }


        // 请求发送短信验证码
        BankCallBean bean = this.bindCardService.cardBindPlusSendSms(user.getUserId(),PropUtils.getSystem(BankCallConstant.BANK_INSTCODE),
                BankCallMethodConstant.TXCODE_CARD_BIND_PLUS, bankCardRequestBean.getMobile(), BankCallConstant.CHANNEL_PC,bankCardRequestBean.getCardNo());
        if (bean == null) {
            _log.info("-------------------发送短信验证码异常！--------------------");
            ret.setStatus(ErrorCodeConstant.STATUS_CE999999);
            ret.setStatusDesc("发送短信验证码异常");
            return ret;
        }
        // 返回失败
        if (!BankCallConstant.RESPCODE_SUCCESS.equals(bean.getRetCode())) {
            if("JX900651".equals(bean.getRetCode())){
                // 成功返回业务授权码
                ret.setStatus(ErrorCodeConstant.STATUS_CE999999);
                ret.setStatusDesc(BaseResultBean.STATUS_DESC_SUCCESS);
                ret.setSrvAuthCode(bean.getSrvAuthCode());
                return ret;
            }
            ret.setStatus(BaseResultBean.STATUS_FAIL);
            ret.setStatusDesc("发送短信验证码失败，失败原因：" + bindCardService.getBankRetMsg(bean.getRetCode()));
            _log.info("-------------------发送短信验证码失败，失败原因：" + bindCardService.getBankRetMsg(bean.getRetCode())+"--------------------");
            return ret;
        }
        // 成功返回业务授权码
        ret.setStatus(ErrorCodeConstant.SUCCESS);
        ret.setStatusDesc(BaseResultBean.STATUS_DESC_SUCCESS);
        ret.setSrvAuthCode(bean.getSrvAuthCode());
        return ret;
    }*/
}
