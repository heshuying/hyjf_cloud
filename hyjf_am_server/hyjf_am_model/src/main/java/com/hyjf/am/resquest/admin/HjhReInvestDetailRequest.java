package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;

import java.io.Serializable;

/**
 * 产品中心 --> 汇计划 --> 资金计划 -> 复投详情
 * @Author : huanghui
 */
public class HjhReInvestDetailRequest extends BasePage implements Serializable {

    private String planNidSrch;

    public String getPlanNidSrch() {
        return planNidSrch;
    }

    public void setPlanNidSrch(String planNidSrch) {
        this.planNidSrch = planNidSrch;
    }
}
