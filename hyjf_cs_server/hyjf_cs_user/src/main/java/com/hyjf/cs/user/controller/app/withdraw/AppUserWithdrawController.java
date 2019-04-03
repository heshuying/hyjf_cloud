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
public class AppUserWithdrawController extends BaseUserController {

    @Autowired
    private UserWithdrawService userWithdrawService;
    /**
     * 卡片描述
     */
    private final String CARD_DESC = "充值限额:{0}{1}{2}";
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
                        bankCardBean.setLogo(systemConfig.getAppFrontHost() + bankConfig.getAppLogo());
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

                        //通用赋值
                        bankCardBean.setBankCode(bank.getBank());
                        bankCardBean.setCardNo(bank.getCardNo());
                        bankCardBean.setCardNo_info(BankCardUtil.getCardNo(bank.getCardNo()));
                        bankCardBean.setNotice(WARM_AND_SWEET_REMINDERS_URL);
                        bankCardBean.setBank(bank.getBank());
                        // 每次限额 单位：万元
                        BigDecimal timesLimitAmount = BigDecimal.ZERO;
                        // 每日限额 单位：万元
                        BigDecimal dayLimitAmount = BigDecimal.ZERO;
                        // 每月限额 单位: 万元
                        BigDecimal monthLimitAmount = BigDecimal.ZERO;

                        if(jxBankConfigVO != null){
                            if (StringUtils.isNotEmpty(jxBankConfigVO.getBankIcon())) {
                                bankCardBean.setLogo(systemConfig.getAppFrontHost() + jxBankConfigVO.getBankIcon());
                            } else {
                                bankCardBean.setLogo(systemConfig.getAppFrontHost() + "/data/upfiles/filetemp/image/bank_log.png");// 应前台要求，logo路径给绝对路径
                            }

                            // 是否快捷卡
                            if (jxBankConfigVO.getQuickPayment() == 1) {
                                bankCardBean.setIsDefault("2");
                            } else {
                                bankCardBean.setIsDefault("0");
                            }

                            if (StringUtils.isNotEmpty(jxBankConfigVO.getBankName())) {
                                bankCardBean.setBank(jxBankConfigVO.getBankName());
                            }

                            // 每次限额 单位：万元
                            timesLimitAmount = jxBankConfigVO.getSingleQuota().divide(new BigDecimal(AMOUNT_UNIT));
                            // 每日限额 单位：万元
                            dayLimitAmount = jxBankConfigVO.getSingleCardQuota().divide(new BigDecimal(AMOUNT_UNIT));
                            // 每月限额 单位: 万元
                            monthLimitAmount = jxBankConfigVO.getMonthCardQuota().divide(new BigDecimal(AMOUNT_UNIT));
                        }else{
                            bankCardBean.setLogo(systemConfig.getAppFrontHost() + "/data/upfiles/filetemp/image/bank_log.png");// 应前台要求，logo路径给绝对路径
                            bankCardBean.setIsDefault("0");

                        }

                        timesLimitAmount = (timesLimitAmount == null)?BigDecimal.ZERO:timesLimitAmount;
                        dayLimitAmount = (dayLimitAmount == null)?BigDecimal.ZERO:dayLimitAmount;
                        monthLimitAmount = (monthLimitAmount == null)?BigDecimal.ZERO:monthLimitAmount;

                        // 是否支持快捷支付1:支持 2:不支持
                        //Integer quickPayment = jxBankConfigVO.getQuickPayment();
                        // modify by yangchangwei app3.1.1 迁移 2018-10-15
                        String symbol = ",";
                        String symBol2 = ",";
                        if(BigDecimal.ZERO.compareTo(dayLimitAmount)==0 && BigDecimal.ZERO.compareTo(monthLimitAmount)==0){
                            symbol = "";
                            symBol2 = "";
                        }
                        if(BigDecimal.ZERO.compareTo(monthLimitAmount)==0){
                            symBol2 = "";
                        }
                        bankCardBean.setDesc(MessageFormat.format(CARD_DESC, (BigDecimal.ZERO.compareTo(timesLimitAmount) == 0) ? "" : "单笔" + timesLimitAmount.toString() + "万元" + symbol,
                                (BigDecimal.ZERO.compareTo(dayLimitAmount) == 0) ? "" : "单日" + dayLimitAmount.toString() + "万元" + symBol2, (BigDecimal.ZERO.compareTo(monthLimitAmount) == 0) ? "" : "单月" + monthLimitAmount.toString() + "万元"));
                        bankCardBean.setNotice(WARM_AND_SWEET_REMINDERS_URL);
                        // add by xiashuqing 20171205 app2.1改版新增 end
                        if(BigDecimal.ZERO.compareTo(monthLimitAmount)==0){
                            symBol2 = "";
                        }

                        bankCardBean.setDesc(MessageFormat.format(CARD_DESC, (BigDecimal.ZERO.compareTo(timesLimitAmount) == 0) ? "" : "单笔" + timesLimitAmount.toString() + "万元" + symbol,
                                (BigDecimal.ZERO.compareTo(dayLimitAmount) == 0) ? "" : "单日" + dayLimitAmount.toString() + "万元" + symBol2, (BigDecimal.ZERO.compareTo(monthLimitAmount) == 0) ? "" : "单月" + monthLimitAmount.toString() + "万元"));

                        bankcards.add(bankCardBean);
                    }
                } else {
                    ret.put("cnt", "0");
                }
            }
            ret.put("banks", bankcards);

        } catch (Exception e) {
            logger.error(e.getMessage());
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
        ret.put("url", systemConfig.getAppServerHost() + request.getContextPath() + "/user/withdraw/withdrawRule" );
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
