package com.hyjf.am.vo.wdzj;

/**
 * @author hesy
 * @version TenderListCustomizeVO, v0.1 2018/7/16 12:11
 */
public class TenderListCustomizeVO {
    private String subscribeUserName;
    private String amount;
    private String validAmount;
    private String addDate;
    private String status;
    private String type;

    public String getSubscribeUserName() {
        return subscribeUserName;
    }

    public void setSubscribeUserName(String subscribeUserName) {
        this.subscribeUserName = subscribeUserName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getValidAmount() {
        return validAmount;
    }

    public void setValidAmount(String validAmount) {
        this.validAmount = validAmount;
    }

    public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
