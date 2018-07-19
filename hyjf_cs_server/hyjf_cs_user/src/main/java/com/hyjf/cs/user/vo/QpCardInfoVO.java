package com.hyjf.cs.user.vo;

import java.io.Serializable;

public class QpCardInfoVO implements Serializable {

    /**
     * 此处为属性说明
     */
    private static final long serialVersionUID = 2119937708894571646L;

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    private String bank;

    private String logo;

    private String code;

    private String cardNo;

}
