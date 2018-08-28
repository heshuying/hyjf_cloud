package com.hyjf.cs.trade.vo;

/**
 * @author pangchengchao
 * @version AppRechargeVO, v0.1 2018/8/7 14:33
 */
public class AppRechargeVO extends AppBaseBean{
    private String money;

    private String cardNo;

    private String code;

    private String mobile;
    private String isMencry;

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIsMencry() {
        return isMencry;
    }

    public void setIsMencry(String isMencry) {
        this.isMencry = isMencry;
    }
}
