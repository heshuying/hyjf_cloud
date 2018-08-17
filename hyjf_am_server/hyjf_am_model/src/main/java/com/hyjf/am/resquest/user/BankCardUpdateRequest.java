package com.hyjf.am.resquest.user;

/**
 * @author hesy
 * @version BankCardUpdateRequest, v0.1 2018/7/19 9:58
 */
public class BankCardUpdateRequest {
    private Integer userId;
    private String userName;
    private String cardNo;
    private Integer cardId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public Integer getCardId() {
        return cardId;
    }

    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }
}
