package com.hyjf.cs.user.controller.api.surong.user.deletecard;

import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.user.BankCardVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.bank.LogAcqResBean;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.util.MD5;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.user.bean.RedeletecardResultBean;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.bindcard.BindCardService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallMethodConstant;
import com.hyjf.pay.lib.bank.util.BankCallStatusConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

/**
 * @author pangchengchao
 * @version DeleteCardController, v0.1 2018/7/26 16:56
 */
@Api(value = "api端-融东风解绑银行卡接口",tags = "api端-融东风解绑银行卡接口")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/surong/deletecard")
public class DeleteCardController extends BaseUserController {
    @Autowired
    BindCardService bindCardService;
    @Autowired
    SystemConfig systemConfig;
    /**
     * 用户删除银行卡
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "用户删除银行卡")
    @PostMapping(value = "/deletecard")
    public ModelAndView deleteCard(HttpServletRequest request, HttpServletResponse response) {
        logger.info("解绑卡接口start");
        //---传入参数---
        String sign = request.getParameter("sign");
        String mobile = request.getParameter("mobile"); // 用户ID
        String cardNo = request.getParameter("cardNo");
        ModelAndView modelAndView = new ModelAndView();
        // 唯一标识
        if(StringUtils.isEmpty(sign)){
            modelAndView = new ModelAndView("/deletecard/deletecard_fail");
            modelAndView.addObject("message", "融东风绑卡sign值为空！");
            return modelAndView;
        }
        String accessKey = systemConfig.getAopAccesskey();

        String miwen =  MD5.toMD5Code(accessKey + mobile + accessKey);

        if(!miwen.equals(sign)){
            modelAndView = new ModelAndView("/deletecard/deletecard_fail");
            modelAndView.addObject("message", "融东风绑卡sign值非法！");
            return modelAndView;
        }

        UserVO user = bindCardService.getUsersByMobile(mobile);
        if(user == null){
            modelAndView = new ModelAndView("/deletecard/deletecard_fail");
            modelAndView.addObject("message", "用户不存在汇盈金服账户！");
            return modelAndView;
        }
        if (Validator.isNull(cardNo)) {
            modelAndView = new ModelAndView("/deletecard/deletecard_fail");
            modelAndView.addObject("message", "获取银行卡号为空");
            return modelAndView;
        }
        Integer userId = user.getUserId();
        if (userId == null || userId == 0) {
            modelAndView = new ModelAndView("/deletecard/deletecard_fail");
            modelAndView.addObject("message", "用户未登录");
            return modelAndView;
        }
        BankOpenAccountVO accountChinapnrTender = bindCardService.getBankOpenAccount(userId);
        if (accountChinapnrTender == null || StringUtils.isEmpty(accountChinapnrTender.getAccount())) {
            modelAndView = new ModelAndView("/deletecard/deletecard_fail");
            modelAndView.addObject("message", "用户未开户");
            return modelAndView;
        }
        // 用户余额大于零不让解绑
        AccountVO account = bindCardService.getAccountByUserId(userId);
        // 用户在银行的账户余额
        BigDecimal bankBalance = bindCardService.getBankBalance(userId, accountChinapnrTender.getAccount());
        if ((Validator.isNotNull(account.getBankBalance()) && account.getBankBalance().compareTo(BigDecimal.ZERO) > 0)
                || ((Validator.isNotNull(bankBalance) && bankBalance.compareTo(BigDecimal.ZERO) > 0))) {
            modelAndView = new ModelAndView("/deletecard/deletecard_fail");
            modelAndView.addObject("message", "抱歉，您还有部分余额，请清空后再删除银行卡！");
            return modelAndView;
        }
        //Integer cardId = Integer.parseInt(this.userDeleteCardService.getBankIdByCardNo(cardNo));
        // 根据银行卡Id获取用户的银行卡信息
        BankCardVO bankCard = this.bindCardService.queryUserCardValid(userId+"",cardNo);
        if (bankCard == null || StringUtils.isEmpty(bankCard.getCardNo())) {
            modelAndView = new ModelAndView("/deletecard/deletecard_fail");
            modelAndView.addObject("message", "获取用户银行卡信息失败");
            return modelAndView;
        }
        // 银行卡Id
        Integer cardId = bankCard.getId();
        UserInfoVO usersInfo = bindCardService.getUserInfo(userId);
        // 调用汇付接口(4.2.6 删除银行卡接口)
        BankCallBean retBean = null;
        BankCallBean bean = new BankCallBean();
        bean.setLogOrderId(GetOrderIdUtils.getOrderId2(userId));
        bean.setLogOrderDate(GetOrderIdUtils.getOrderDate());// 订单时间(必须)格式为yyyyMMdd，例如：20130307
        bean.setLogRemark("解绑银行卡");
        bean.setLogUserId(String.valueOf(userId));
        bean.setVersion(BankCallConstant.VERSION_10);// 接口版本号
        bean.setTxCode(BankCallMethodConstant.TXCODE_CARD_UNBIND);
        bean.setTxDate(GetOrderIdUtils.getTxDate());// 交易日期
        bean.setTxTime(GetOrderIdUtils.getTxTime());// 交易时间
        bean.setSeqNo(GetOrderIdUtils.getSeqNo(6));// 交易流水号
        bean.setChannel(BankCallConstant.CHANNEL_APP);// 交易渠道
        bean.setAccountId(accountChinapnrTender.getAccount());// 存管平台分配的账号
        bean.setIdType(BankCallConstant.ID_TYPE_IDCARD);// 证件类型01身份证
        bean.setIdNo(usersInfo.getIdcard());// 证件号
        bean.setName(usersInfo.getTruename());// 姓名
        bean.setMobile(user.getMobile());// 手机号
        bean.setCardNo(cardNo);// 银行卡号
        LogAcqResBean logAcqResBean = new LogAcqResBean();
        logAcqResBean.setCardNo(cardNo);
        logAcqResBean.setCardId(cardId);
        bean.setLogAcqResBean(logAcqResBean);
        // 调用汇付接口
        try {
            retBean = BankCallUtils.callApiBg(bean);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        // 回调数据处理
        if (retBean == null || !(BankCallStatusConstant.RESPCODE_SUCCESS.equals(retBean.getRetCode()))) {
            logger.info("RetCode:" + (retBean == null ? "" : retBean.getRetCode()) + "&&&&&&&&&&& RetMsg:" + (retBean == null ? "" : retBean.getRetMsg()));
            modelAndView = new ModelAndView("/deletecard/deletecard_fail");
            modelAndView.addObject("message", "抱歉，银行卡删除错误，请联系客服！");
            return modelAndView;
        }
        // 执行删除卡后处理,判断银行卡状态，删除平台本地银行卡信息
        try {
            boolean isdelFlag = this.bindCardService.updateAfterUnBindCard(bean);
            //            // 删除失败
            if (!isdelFlag) {
                modelAndView = new ModelAndView("/deletecard/deletecard_fail");
                modelAndView.addObject("message", "抱歉，银行卡删除错误，请联系客服！");
            } else {
                modelAndView = new ModelAndView("/surong/callback_post");
                RedeletecardResultBean repwdResult = new RedeletecardResultBean();
                repwdResult.setCallBackAction(systemConfig.getSurongDeletecard());
                repwdResult.set("mobile", mobile);
                repwdResult.set("sign",sign);
                repwdResult.set("status", "0");
                repwdResult.set("cardNo",cardNo);
                modelAndView.addObject("callBackForm", repwdResult);
            }
        } catch (Exception e) {
            modelAndView = new ModelAndView("/deletecard/deletecard_fail");
            modelAndView.addObject("message", "抱歉，银行卡删除错误，请联系客服！");
        }
        logger.info("解绑卡接口end");
        return modelAndView;
    }

}
