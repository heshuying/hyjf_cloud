/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.controller.wechat.recharge;

import com.hyjf.am.vo.trade.BanksConfigVO;
import com.hyjf.am.vo.trade.CorpOpenAccountRecordVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.user.BankCardVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.util.BankCardUtil;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.cs.common.bean.result.WeChatResult;
import com.hyjf.cs.trade.bean.WxRechargeDescResultBean;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.service.recharge.WechatRechargeRuleService;
import com.hyjf.cs.trade.vo.WxRechargeInfoResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.MessageFormat;

/**
 * @author wangjun
 * @version WechatRechargeRuleController, v0.1 2018/7/26 9:23
 */
@Api(tags = "weChat端-获取充值规则")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/hyjf-wechat/wx/recharge")
public class WechatRechargeRuleController {
    @Autowired
    WechatRechargeRuleService wechatRechargeRuleService;

    @Autowired
    SystemConfig systemConfig;

    /** 金额单位 万元 */
    private final Integer AMOUNT_UNIT = 10000;

    /** 充值描述 */
    private final String CARD_DESC = "限额:单笔{0}，单日{1}，单月{2}";
    private final String RECHARGE_KINDLY_REMINDER = "友情提示:您可以通过支付宝转账、银行柜台转账、网银转账、手机银行转账的方式，将资金充值到您的江西银行存管账户，实现账户充值，须填写的信息如上。";
    private final String RCV_ACCOUNT_NAME = "惠众商务顾问（北京）有限公司";
    private final String RCV_ACCOUNT = "791913149300306";
    private final String RCV_OPEN_BANK_NAME = "江西银行南昌铁路支行";
    private final String KINDLY_REMINDER = "注：①用户必须使用在平台唯一绑定银行卡进行充值；<br/>②银行转账时，请选择（城市商业银行）江西银行或者南昌银行；<br/>③线下充值的到账时间一般为1-3天（具体到账时间以银行的实际到账时间为准）；";


    @ApiOperation(value = "获取充值规则", notes = "获取充值规则")
    @GetMapping(value = "/rechargeRule")
    public WeChatResult rechargeRule() {
        WeChatResult result = new WeChatResult();
        WxRechargeDescResultBean bean = wechatRechargeRuleService.getRechargeRule();
        result.setData(bean);
        return result;
    }

    /**
     *
     * wx获取快捷充值卡及手续费的数据接口
     *
     * @author
     * @param
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "获取快捷充值卡及手续费的数据接口",notes = "获取快捷充值卡及手续费的数据接口")
    @GetMapping(value = "/getQpRechargeInfo")
    public WxRechargeInfoResultVO getQpRechargeInfo(@RequestHeader(value = "userId")Integer userId) {
        WxRechargeInfoResultVO resultVo = new WxRechargeInfoResultVO();
        if (StringUtils.isNotBlank(userId.toString())) {
            AccountVO account = wechatRechargeRuleService.getAccountByUserId(userId);
            if (account != null) {
                resultVo.setAvailableAmount(account.getBankBalance());
            }
            // 获取用户的快捷卡信息
            BankCardVO bankCard = this.wechatRechargeRuleService.selectBankCardByUserId(userId);
            if (null != bankCard) {
                resultVo.setStatus(CustomConstants.APP_STATUS_SUCCESS);
                resultVo.setStatusDesc(CustomConstants.APP_STATUS_DESC_SUCCESS);
                resultVo.setBank(StringUtils.isBlank(bankCard.getBank()) ? StringUtils.EMPTY : bankCard.getBank());
                // 银行卡号
                resultVo.setCardNo(bankCard.getCardNo());
                resultVo.setIsBindCard("1");
                // 脱敏显示的银行卡号
                resultVo.setCardNoInfo(BankCardUtil.getCardNo(bankCard.getCardNo()));
                //成功充值手机号码
                resultVo.setMobile(bankCard.getMobile());
                // 银行代码
                resultVo.setCode("");
                Integer bankId = bankCard.getBankId();
                BanksConfigVO banksConfig = wechatRechargeRuleService.getBanksConfigByBankId(bankId);
                if (banksConfig != null && StringUtils.isNotEmpty(banksConfig.getBankIcon())) {
                    resultVo.setLogo(systemConfig.getWechatHost()+ banksConfig.getBankIcon());
                } else {
                    resultVo.setLogo(systemConfig.getWechatHost() + "/data/upfiles/filetemp/image/bank_log.png");
                }

                if(banksConfig !=null && StringUtils.isNotEmpty(banksConfig.getBankName())){
                    resultVo.setBank(banksConfig.getBankName());
                }
                // 是否快捷卡
                if(banksConfig != null && banksConfig.getQuickPayment() == 1){
                    resultVo.setIsDefault("2");
                }else {
                    resultVo.setIsDefault("0");
                }
                //限额获取
                if (banksConfig != null) {
                    // 每次限额 单位：万元
                    BigDecimal timesLimitAmount = banksConfig.getSingleQuota()
                            .divide(new BigDecimal(AMOUNT_UNIT));
                    // 每日限额 单位：万元
                    BigDecimal dayLimitAmount = banksConfig.getSingleCardQuota()
                            .divide(new BigDecimal(AMOUNT_UNIT));
                    // 每月限额 单位: 万元
                    BigDecimal monthLimitAmount = banksConfig.getMonthCardQuota().divide(new BigDecimal(AMOUNT_UNIT));
                    if (monthLimitAmount == null) {
                        monthLimitAmount = BigDecimal.ZERO;
                    }
                    String moneyInfo = MessageFormat.format(CARD_DESC, (BigDecimal.ZERO.compareTo(timesLimitAmount) == 0)?"不限":timesLimitAmount.toString() + "万元",
                            (BigDecimal.ZERO.compareTo(dayLimitAmount)==0)?"不限":dayLimitAmount.toString() + "万元",
                            (BigDecimal.ZERO.compareTo(monthLimitAmount)==0)?"不限":monthLimitAmount.toString() + "万元");
                    resultVo.setMoneyInfo(moneyInfo);
                }
            } else {
                resultVo.setStatus("1003");
                resultVo.setStatusDesc("用户未绑定银行卡");
                resultVo.setIsBindCard("0");
            }
            //按钮字段显示前台处理
            //设置线下充值信息
            this.setOffLineRechageInfo(resultVo, userId);

        } else {
            resultVo.setStatus(CustomConstants.TOKEN_ERROR);
            resultVo.setStatusDesc("用户认证失败");
        }
        /** 充值描述 */
        resultVo.setRcvAccountName(RCV_ACCOUNT_NAME);
        resultVo.setRcvAccount(RCV_ACCOUNT);
        resultVo.setRcvOpenBankName(RCV_OPEN_BANK_NAME);
        resultVo.setKindlyReminder(KINDLY_REMINDER);
        resultVo.setStatus("000");
        return resultVo;
    }

    private void setOffLineRechageInfo(WxRechargeInfoResultVO resultVo, Integer userId) {

        resultVo.setRcvOpenBankName(RCV_OPEN_BANK_NAME);
        resultVo.setKindlyReminder(RECHARGE_KINDLY_REMINDER);

        BankOpenAccountVO bankOpenAccount = wechatRechargeRuleService.getBankOpenAccount(userId);
        if (bankOpenAccount != null) {
            resultVo.setRcvAccount(bankOpenAccount.getAccount());
        }

        UserInfoVO usersInfo = wechatRechargeRuleService.getUsersInfoByUserId(userId);
        if (usersInfo != null) {
            resultVo.setRcvAccountName(usersInfo.getTruename());
        }
        // 用户信息
        UserVO users = wechatRechargeRuleService.getUsers(userId);
        // 用户类型
        Integer userType = users.getUserType();
        // 如果是企业用户
        if (userType == 1) {
            // 根据用户ID查询企业用户账户信息
            CorpOpenAccountRecordVO record = wechatRechargeRuleService.getCorpOpenAccountRecord(userId);
            resultVo.setRcvAccountName(record.getBusiName());
        }
    }
}
