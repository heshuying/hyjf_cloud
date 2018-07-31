package com.hyjf.am.resquest.trade;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/27 10:05
 * @Description: AppCouponRequest
 */
public class AppCouponRequest {
    String borrowNid;
    String sign;
    String platform;
    String money;
    // 项目类型  HJH传HJH
    String borrowType;

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getBorrowType() {
        return borrowType;
    }

    public void setBorrowType(String borrowType) {
        this.borrowType = borrowType;
    }
}
