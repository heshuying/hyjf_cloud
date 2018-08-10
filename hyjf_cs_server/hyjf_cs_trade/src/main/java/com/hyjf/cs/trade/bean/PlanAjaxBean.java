package com.hyjf.cs.trade.bean;

import java.io.Serializable;
import java.util.List;

import com.hyjf.am.vo.trade.assetmanage.CurrentHoldPlanListCustomizeVO;
import com.hyjf.am.vo.trade.assetmanage.RepayMentPlanListCustomizeVO;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.common.util.Page;

public class PlanAjaxBean {

	private static final long serialVersionUID = 3278149257478770256L;
	
	//当前持有计划列表
	private List<CurrentHoldPlanListCustomizeVO> currentHoldPlanList;
	//已回款计划列表
	private List<RepayMentPlanListCustomizeVO> repayMentPlanList;
	//当前持有计划数量
	private Integer currentHoldPlanCount=0;
	//已回款计划数量
	private Integer repayMentPlanCount=0;
    // 分页对象
    private Page page;

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public List<CurrentHoldPlanListCustomizeVO> getCurrentHoldPlanList() {
        return currentHoldPlanList;
    }

    public void setCurrentHoldPlanList(List<CurrentHoldPlanListCustomizeVO> currentHoldPlanList) {
        this.currentHoldPlanList = currentHoldPlanList;
    }

    public List<RepayMentPlanListCustomizeVO> getRepayMentPlanList() {
        return repayMentPlanList;
    }

    public void setRepayMentPlanList(List<RepayMentPlanListCustomizeVO> repayMentPlanList) {
        this.repayMentPlanList = repayMentPlanList;
    }

    public Integer getCurrentHoldPlanCount() {
        return currentHoldPlanCount;
    }

    public void setCurrentHoldPlanCount(Integer currentHoldPlanCount) {
        this.currentHoldPlanCount = currentHoldPlanCount;
    }

    public Integer getRepayMentPlanCount() {
        return repayMentPlanCount;
    }

    public void setRepayMentPlanCount(Integer repayMentPlanCount) {
        this.repayMentPlanCount = repayMentPlanCount;
    }
}
