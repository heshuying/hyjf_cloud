/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.withdraw.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.trade.BankConfigVO;
import com.hyjf.am.vo.trade.JxBankConfigVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.user.AccountBankVO;
import com.hyjf.am.vo.user.AccountChinapnrVO;
import com.hyjf.am.vo.user.BankCardVO;
import com.hyjf.common.util.ClientConstants;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.cs.common.service.BaseServiceImpl;
import com.hyjf.cs.user.bean.BankCardBean;
import com.hyjf.cs.user.client.AmConfigClient;
import com.hyjf.cs.user.client.AmTradeClient;
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.service.withdraw.UserWithdrawService;
import com.hyjf.pay.lib.chinapnr.ChinapnrBean;
import com.hyjf.pay.lib.chinapnr.util.ChinaPnrConstant;
import com.hyjf.pay.lib.chinapnr.util.ChinapnrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: sunpeikai
 * @version: UserWithdrawServiceImpl, v0.1 2018/7/23 15:18
 */
@Service
public class UserWithdrawServiceImpl extends BaseServiceImpl implements UserWithdrawService {
    @Autowired
    private AmConfigClient amConfigClient;

    @Autowired
    private AmUserClient amUserClient;

    @Autowired
    private AmTradeClient amTradeClient;

    @Autowired
    SystemConfig systemConfig;

    /**
     * 根据userId获取accountBank
     * @auth sunpeikai
     * @param userId 用户id
     * @return
     */
    @Override
    public List<AccountBankVO> getBankHfCardByUserId(Integer userId) {
        return amUserClient.getAccountBankByUserId(userId);
    }
    /**
     * 根据银行名查询银行配置
     * @auth sunpeikai
     * @param bank 银行code，例如：招商银行,CMB
     * @return
     */
    @Override
    public BankConfigVO getBankInfo(String bank) {
        return amConfigClient.getBankConfigByCode(bank);
    }

    /**
     * 根据id查询银行配置
     * @auth sunpeikai
     * @param bankId 主键id
     * @return
     */
    @Override
    public JxBankConfigVO getJxBankConfigByBankId(Integer bankId) {
        return amConfigClient.getJxBankConfigById(bankId);
    }

    @Override
    public List<BankCardVO> getBankCardByUserId(Integer userId) {
        return amUserClient.getBankOpenAccountById(userId);
    }

    /**
     * 根据userId获取BankCard
     * @auth sunpeikai
     * @param userId 用户id
     * @return
     */


    @Override
    public JSONObject getCashInfo(Integer userId, JSONObject ret,String version, String bankCode, String getcash) {
        // 金额显示格式
        DecimalFormat moneyFormat = null;
        // 判断选择哪种金融样式
        if (version.contains(CustomConstants.APP_VERSION_NUM)) {
            moneyFormat = CustomConstants.DF_FOR_VIEW_V1;
        } else {
            moneyFormat = CustomConstants.DF_FOR_VIEW;
        }
        // 取得用户当前余额
        AccountVO account = amTradeClient.getAccount(userId);
        if (account == null) {
            ret.put("status", "1");
            ret.put("statusDesc", "你的账户信息存在异常，请联系客服人员处理。");
            return ret;
        } else {
            // 可提现金额
            ret.put("total", moneyFormat.format(account.getBalance()));
        }
        // 取得用户在汇付天下的账户信息
        AccountChinapnrVO accountChinapnr = amUserClient.getAccountChinapnr(userId);
        // 用户未开户时,返回错误信息
        if (accountChinapnr == null) {
            ret.put("status", "1");
            ret.put("statusDesc", "用户未开户!");
            return ret;
        }
        // 取得银行卡信息
        // begin 调汇付接口查询银行卡信息 4.4.11 因为绑卡的时候汇付未能传递给我们是否默认卡
        ChinapnrBean bean = new ChinapnrBean();
        bean.setVersion(ChinaPnrConstant.VERSION_10);
        // 消息类型(必须)
        bean.setCmdId(ChinaPnrConstant.CMDID_QUERY_CARD_INFO);
        // 商户客户号
        bean.setMerCustId(systemConfig.getChinapnrMercustid());
        // 用户客户号(必须)
        bean.setUsrCustId(String.valueOf(accountChinapnr.getChinapnrUsrcustid()));
        // 调用汇付接口
        ChinapnrBean chinaPnrBean = ChinapnrUtil.callApiBg(bean);
        // end 调汇付接口查询银行卡信息 4.4.11 因为绑卡的时候汇付未能传递给我们是否默认卡
        if (chinaPnrBean == null) {
            ret.put("status", "1");
            ret.put("statusDesc", "调用汇付接口(查询银行卡信息 4.4.11)发生错误!");
            return ret;
        }
        String UsrCardInfolist = chinaPnrBean.getUsrCardInfolist();
        JSONArray array = JSONObject.parseArray(UsrCardInfolist);
        BankConfigVO bankSetUp = new BankConfigVO();
        if (array.size() > 0) {
            ret.put("bankCnt", array.size() + "");
            List<BankCardBean> bankcards = new ArrayList<BankCardBean>();
            for (int j = 0; j < array.size(); j++) {
                JSONObject obj = array.getJSONObject(j);
                if (!"R".equals(obj.getString("RealFlag"))) {
                    // 只有实名卡才入库
                    continue;
                }
                BankConfigVO bankConfig = amConfigClient.selectBankConfigByCode(obj.getString("BankId"));
                BankCardBean bankCardBean = new BankCardBean();
                // 普通卡
                bankCardBean.setIsDefault("0");
                if ("Y".equals(obj.getString("IsDefault"))) {
                    // 默认卡
                    bankCardBean.setIsDefault("1");
                }
                if ("Y".equals(obj.getString("ExpressFlag"))) {
                    // 快捷卡
                    bankCardBean.setIsDefault("2");
                }
                // 银行代号
                bankCardBean.setBankCode(obj.getString("BankId"));
                // 银行名称
                bankCardBean.setBank(bankConfig.getName());
                // 应前台要求，logo路径给绝对路径
                bankCardBean.setLogo(systemConfig.getAppServerHost() + bankConfig.getAppLogo());
                bankCardBean.setCardNo(obj.getString("CardId"));
                bankcards.add(bankCardBean);
                // 判断是否已经传银行卡code，如果已传则获取该银行的信息
                if (bankCode != null && bankCode.equals(obj.getString("BankId"))) {
                    bankSetUp = bankConfig;
                } else {
                    // 如果没有传银行卡code，则判断是默认银行卡或快捷卡记录银行设置
                    if ("Y".equals(obj.getString("IsDefault")) || "Y".equals(obj.getString("ExpressFlag"))) {
                        bankSetUp = bankConfig;
                    }
                }
            }
            ret.put("banks", bankcards);
            ret.put("logo", bankcards.get(0).getLogo());
            ret.put("cardNo", bankcards.get(0).getCardNo());
            ret.put("bank", bankcards.get(0).getBank());
        } else {
            ret.put("bankCnt", "0");
        }

        // 银行卡支持的提现方式 开始
        int cashchlCnt = 0;
        JSONArray cashchls = new JSONArray();
        // 判断是否有一般提现
        if (1 == bankSetUp.getNormalWithdraw()) {
            JSONObject jo = new JSONObject();
            jo.put("cashchlNm", "GENERAL");
            jo.put("cashchlRemark", "一般提现");
            // 默认提现方式,0一般提现,1快速提现,2即时提现,默认0
            if (bankSetUp.getWithdrawDefaulttype() == 0) {
                // 是否默认提现方式1是，0否
                jo.put("isDefaultCashchl", "1");
            } else {
                // 是否默认提现方式1是，0否
                jo.put("isDefaultCashchl", "0");
            }
            cashchlCnt++;
            cashchls.add(jo);
        }
        // 判断是否有快速提现
        if (1 == bankSetUp.getQuickWithdraw()) {
            JSONObject jo = new JSONObject();
            jo.put("cashchlNm", "FAST");
            jo.put("cashchlRemark", "快速提现");
            // 默认提现方式,0一般提现,1快速提现,2即时提现,默认0
            if (bankSetUp.getWithdrawDefaulttype() == 1) {
                // 是否默认提现方式1是，0否
                jo.put("isDefaultCashchl", "1");
            } else {
                // 是否默认提现方式1是，0否
                jo.put("isDefaultCashchl", "0");
            }
            cashchlCnt++;
            cashchls.add(jo);
        }
        // 判断是否有即时提现
        if (1 == bankSetUp.getImmediatelyWithdraw()) {
            JSONObject jo = new JSONObject();
            jo.put("cashchlNm", "IMMEDIATE");
            jo.put("cashchlRemark", "即时提现");
            // 默认提现方式,0一般提现,1快速提现,2即时提现,默认0
            if (bankSetUp.getWithdrawDefaulttype() == 2) {
                // 是否默认提现方式1是，0否
                jo.put("isDefaultCashchl", "1");
            } else {
                // 是否默认提现方式1是，0否
                jo.put("isDefaultCashchl", "0");
            }
            cashchlCnt++;
            cashchls.add(jo);
        }
        // 提现方式总数
        ret.put("cashchlCnt", cashchlCnt + "");
        ret.put("cashchls", cashchls);
        // 银行卡支持的提现方式 结束
        // 如果提现金额是0
        if ("0".equals(getcash) || "".equals(getcash)) {
            ret.put("accountDesc", "手续费: 0 元；实际到账: 0 元");
            ret.put("fee", "0.00 元");
            ret.put("balance", "0.00 元");
            ret.put("buttonWord", "提现");
        } else {

            String balance = "";
            if ((new BigDecimal(getcash).subtract(BigDecimal.ONE)).compareTo(BigDecimal.ZERO) < 0) {
                balance = "0";
            } else {
                balance = moneyFormat.format(new BigDecimal(getcash).subtract(BigDecimal.ONE));
            }
            ret.put("accountDesc", "手续费: 1 元；实际到账: " + balance + " 元");
            ret.put("fee", "1.00 元");
            ret.put("balance", balance+" 元");
            ret.put("buttonWord", "确认提现"+moneyFormat.format(new BigDecimal("".equals(getcash)?"0":getcash))+"元");
        }

        ret.put("status", "0");
        ret.put("statusDesc", "成功");
        ret.put("request", "/hyjf-app/user/withdraw" + ClientConstants.GET_WITHDRAW_INFO_MAPPING);
        return ret;
    }
}
