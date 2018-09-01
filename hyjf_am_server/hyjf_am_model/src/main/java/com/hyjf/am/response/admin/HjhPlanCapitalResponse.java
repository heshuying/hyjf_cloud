package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.trade.HjhPlanCapitalVO;

import java.math.BigDecimal;

/**
 * 汇计划-计划资金
 * @Author : huanghui
 */
public class HjhPlanCapitalResponse extends Response<HjhPlanCapitalVO> {

    private int count;

    private BigDecimal sumAccedeAccount;

    private BigDecimal sumRepayInterest;

    private HjhPlanCapitalVO sumHjhPlanCapitalVO;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public BigDecimal getSumAccedeAccount() {
        return sumAccedeAccount;
    }

    public void setSumAccedeAccount(BigDecimal sumAccedeAccount) {
        this.sumAccedeAccount = sumAccedeAccount;
    }

    public BigDecimal getSumRepayInterest() {
        return sumRepayInterest;
    }

    public void setSumRepayInterest(BigDecimal sumRepayInterest) {
        this.sumRepayInterest = sumRepayInterest;
    }

    public HjhPlanCapitalVO getSumHjhPlanCapitalVO() {
        return sumHjhPlanCapitalVO;
    }

    public void setSumHjhPlanCapitalVO(HjhPlanCapitalVO sumHjhPlanCapitalVO) {
        this.sumHjhPlanCapitalVO = sumHjhPlanCapitalVO;
    }
}
