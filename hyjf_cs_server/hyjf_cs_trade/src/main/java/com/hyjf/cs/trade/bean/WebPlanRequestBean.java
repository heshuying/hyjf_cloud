package com.hyjf.cs.trade.bean;

import com.hyjf.am.vo.BasePage;

public class WebPlanRequestBean extends BasePage {

    private String planNid;

    // 查询优惠券用
    private String money;

    public String getPlanNid() {
        return planNid;
    }

    public void setPlanNid(String planNid) {
        this.planNid = planNid;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
