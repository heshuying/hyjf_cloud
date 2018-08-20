/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.app.withdraw;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.trade.BankConfigVO;
import com.hyjf.am.vo.trade.JxBankConfigVO;
import com.hyjf.am.vo.user.AccountBankVO;
import com.hyjf.am.vo.user.BankCardVO;
import com.hyjf.common.util.BankCardUtil;
import com.hyjf.common.util.ClientConstants;
import com.hyjf.common.util.SecretUtil;
import com.hyjf.cs.user.bean.AppBankCardBean;
import com.hyjf.cs.user.bean.BankCardBean;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.withdraw.UserWithdrawService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: sunpeikai
 * @version: UserWithdrawController, v0.1 2018/7/23 15:10
 */
@Api(value = "app端-获取我的银行卡",tags = "app端-获取我的银行卡")
@RestController
@CrossOrigin("*")
@RequestMapping("/hyjf-app")
public class UserWithdrawController extends BaseUserController {

    @Autowired
    private UserWithdrawService userWithdrawService;
    /**
     * 卡片描述
     */
    private final String CARD_DESC = "限额:单笔{0}，单日{1}，单月{2}";
    /**
     * 温馨提示URL
     */
    private final String WARM_AND_SWEET_REMINDERS_URL = "#";
    /**
     * 金额单位 万元
     */
    private final Integer AMOUNT_UNIT = 10000;

    @Autowired
    private SystemConfig systemConfig;

    @ApiOperation(value = "获取我的银行卡")
    @PostMapping(value = "/bank/user/withdraw/getBankCardAction")
    public JSONObject getBankCardAction(HttpServletRequest request){
        JSONObject ret = checkAppBaseParam(request);
        // 是否是江西银行卡列表
        String isJX = request.getParameter("isJX");

        if (null!= ret&&"1".equals(ret.get("status"))){
            return ret;
        }
        // 唯一标识
        String sign = request.getParameter("sign");
        try {
            ret.put("status", "0");
            ret.put("statusDesc", "成功");
            ret.put("request","/hyjf-app/bank/user/withdraw/getBankCardAction");
            // 取得用户iD
            Integer userId = SecretUtil.getUserId(sign);
            logger.info("获取我的银行卡userId:[{}]",userId);

            List<AppBankCardBean> bankcards = new ArrayList<>();
            if("0".equals(isJX)){
                // 取得银行卡信息
                List<AccountBankVO> banks = userWithdrawService.getBankHfCardByUserId(userId);
                if (banks != null) {
                    ret.put("cnt", banks.size() + "");
                    for (AccountBankVO bank : banks) {
                        AppBankCardBean bankCardBean = new AppBankCardBean();


                        BankConfigVO bankConfig = userWithdrawService.getBankInfo(bank.getBank());
                        // 应前台要求，logo路径给绝对路径
                        bankCardBean.setLogo(systemConfig.webHost + bankConfig.getAppLogo());
                        bankCardBean.setBankCode(bankConfig.getCode());
                        // 银行名称 汉字
                        bankCardBean.setBank(bankConfig.getName());
                        bankCardBean.setBankCode(bankConfig.getCode());
                        bankCardBean.setCardNo(bank.getAccount());
                        bankCardBean.setCardNo_info(BankCardUtil.getCardNo(bank.getAccount()));
                        // 卡类型
                        bankCardBean.setIsDefault(bank.getCardType());
                        // 0普通提现卡1默认卡2快捷支付卡
                        bankcards.add(bankCardBean);
                    }
                } else {
                    ret.put("cnt", "0");
                }
                ret.put("banks", bankcards);
            }else{
                List<BankCardVO> bankCardVOList = userWithdrawService.getBankCardByUserId(userId);
                if (bankCardVOList != null) {
                    ret.put("cnt", bankCardVOList.size() + "");
                    for (BankCardVO bank : bankCardVOList) {
                        AppBankCardBean bankCardBean = new AppBankCardBean();
                        Integer bankId = bank.getBankId();

                        JxBankConfigVO jxBankConfigVO = userWithdrawService.getJxBankConfigByBankId(bankId);

                        if (jxBankConfigVO != null && StringUtils.isNotEmpty(jxBankConfigVO.getBankIcon())) {
                            bankCardBean.setLogo(systemConfig.webHost + jxBankConfigVO.getBankLogo());
                        } else {
                            bankCardBean.setLogo(systemConfig.webHost + "/data/upfiles/filetemp/image/bank_log.png");// 应前台要求，logo路径给绝对路径
                        }

                        // 是否快捷卡
                        if (jxBankConfigVO != null && jxBankConfigVO.getQuickPayment() == 1) {
                            bankCardBean.setIsDefault("2");
                        } else {
                            bankCardBean.setIsDefault("0");
                        }
                        bankCardBean.setBankCode(bank.getBank());

                        if (jxBankConfigVO != null && StringUtils.isNotEmpty(jxBankConfigVO.getBankName())) {
                            bankCardBean.setBank(jxBankConfigVO.getBankName());
                        }

                        bankCardBean.setCardNo(bank.getCardNo());
                        bankCardBean.setCardNo_info(BankCardUtil.getCardNo(bank.getCardNo()));
                        // add by xiashuqing 20171205 app2.1改版新增 begin
                        // 每次限额 单位：万元
                        BigDecimal timesLimitAmount = jxBankConfigVO.getSingleQuota().divide(new BigDecimal(AMOUNT_UNIT));
                        // 每日限额 单位：万元
                        BigDecimal dayLimitAmount = jxBankConfigVO.getSingleCardQuota().divide(new BigDecimal(AMOUNT_UNIT));
                        // 每月限额 单位: 万元
                        BigDecimal monthLimitAmount = jxBankConfigVO.getMonthCardQuota().divide(new BigDecimal(AMOUNT_UNIT));
                        if (monthLimitAmount == null) {
                            monthLimitAmount = BigDecimal.ZERO;
                        }
                        // 是否支持快捷支付1:支持 2:不支持
                        //Integer quickPayment = jxBankConfigVO.getQuickPayment();
                        bankCardBean.setDesc(MessageFormat.format(CARD_DESC, (BigDecimal.ZERO.compareTo(timesLimitAmount) == 0) ? "不限" : timesLimitAmount.toString() + "万元",
                                (BigDecimal.ZERO.compareTo(dayLimitAmount) == 0) ? "不限" : dayLimitAmount.toString() + "万元", (BigDecimal.ZERO.compareTo(monthLimitAmount) == 0) ? "不限" : monthLimitAmount.toString() + "万元"));
                        bankCardBean.setNotice(WARM_AND_SWEET_REMINDERS_URL);
                        // add by xiashuqing 20171205 app2.1改版新增 end

                        bankcards.add(bankCardBean);
                    }
                } else {
                    ret.put("cnt", "0");
                }
            }
            ret.put("banks", bankcards);

        } catch (Exception e) {
            e.printStackTrace();
            ret.put("status", "1");
            ret.put("statusDesc", "获取我的银行卡发生错误");
        }
        return ret;
    }


    /**
     * 获取提现信息
     * @param request
     * @param
     * @return
     */
    @ApiOperation(value = "获取提现信息")
    @PostMapping(value = "/user/withdraw/getInfoAction")
    public JSONObject getCashInfo(@RequestHeader(value = "userId") Integer userId, HttpServletRequest request) {
        JSONObject ret = checkAppBaseParam(request);
        ret.put("request","/hyjf-app/user/withdraw/getInfoAction");
        if (null!= ret&&"1".equals(ret.get("status"))){
            return ret;
        }
        // 版本号
        String version = request.getParameter("version");
        // bankCode 银行编号
        String bankCode = request.getParameter("bankCode");
        // getcash 提现金额
        String getcash = request.getParameter("getcash");
        // 提现规则静态页面的url
        ret.put("url", systemConfig.getAppServerHost() + request.getContextPath() + "/hyjf-app/user/withdraw" + ClientConstants.GET_WITHDRAW_RULE_MAPPING);
        ret =  userWithdrawService.getCashInfo(userId,ret,version,bankCode,getcash);
        return ret;
    }

    /**
     *
     * 获取提现规则H5页面
     *
     * @author renxingchen
     * @return
     */
    @ApiOperation(value = "获取提现规则H5页面",notes = "获取提现规则H5页面")
    @PostMapping(value = "/user/withdraw/getRule")
    public ModelAndView rechargeRule() {
        return new ModelAndView(ClientConstants.WITHDRAW_RULE_PATH);
    }
}
