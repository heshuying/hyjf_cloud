/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.bean;

/**
 * @author: sunpeikai
 * @version: AppBankCardBean, v0.1 2018/8/17 18:07
 */
public class AppBankCardBean {
    // 发卡行的名称
    private String bank = "";
    // 发卡行的代号
    private String bankCode = "";
    // 发卡行logo的url
    private String logo = "";
    // 银行卡号
    private String cardNo = "";
    // 0普通提现卡1默认提现卡2快捷支付卡
    private String isDefault ="0";

    //add by xiashuqing 20171205 app2.1改版新增 begin
    //卡片描述
    private String desc = "";
    // 温馨提示url
    private String notice = "";
    //add by xiashuqing 20171205 app2.1改版新增 end
    // 合规改造 需要银行卡统一脱敏显示
    private String cardNo_info = "";

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getCardNo_info() {
        return cardNo_info;
    }

    public void setCardNo_info(String cardNo_info) {
        this.cardNo_info = cardNo_info;
    }
}
