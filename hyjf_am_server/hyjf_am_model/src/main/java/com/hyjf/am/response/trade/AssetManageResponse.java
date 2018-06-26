package com.hyjf.am.response.trade;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.BaseVO;

import com.hyjf.am.vo.trade.TenderCreditDetailCustomizeVO;
import com.hyjf.am.vo.trade.assetmanage.CurrentHoldObligatoryRightListCustomizeVO;
import com.hyjf.am.vo.trade.assetmanage.CurrentHoldPlanListCustomizeVO;
import com.hyjf.am.vo.trade.assetmanage.RepayMentListCustomizeVO;
import com.hyjf.am.vo.trade.assetmanage.RepayMentPlanListCustomizeVO;

import java.util.List;

/**
 * @Description
 * @Author pangchengchao
 * @Version v0.1
 * @Date
 */
public class AssetManageResponse extends Response<BaseVO> {
    //当前持有债权列表
    private List<CurrentHoldObligatoryRightListCustomizeVO> currentHoldObligatoryRightList;
    //已回款债权列表
    private List<RepayMentListCustomizeVO> repayMentList;
    //转让记录列表
    private List<TenderCreditDetailCustomizeVO> creditRecordList;

    //当前持有债权数量
    private Integer currentHoldObligatoryRightCount=0;
    //已回款债权数量
    private Integer repayMentCount=0;
    //转让债权数量
    private Integer tenderCreditDetailCount=0;


    //当前持有计划列表
    private List<CurrentHoldPlanListCustomizeVO> currentHoldPlanList;
    //已回款计划列表
    private List<RepayMentPlanListCustomizeVO> repayMentPlanList;
    //当前持有计划数量
    private Integer currentHoldPlanCount=0;
    //已回款计划数量
    private Integer repayMentPlanCount=0;

    public List<CurrentHoldObligatoryRightListCustomizeVO> getCurrentHoldObligatoryRightList() {
        return currentHoldObligatoryRightList;
    }

    public void setCurrentHoldObligatoryRightList(List<CurrentHoldObligatoryRightListCustomizeVO> currentHoldObligatoryRightList) {
        this.currentHoldObligatoryRightList = currentHoldObligatoryRightList;
    }

    public List<RepayMentListCustomizeVO> getRepayMentList() {
        return repayMentList;
    }

    public void setRepayMentList(List<RepayMentListCustomizeVO> repayMentList) {
        this.repayMentList = repayMentList;
    }

    public List<TenderCreditDetailCustomizeVO> getCreditRecordList() {
        return creditRecordList;
    }

    public void setCreditRecordList(List<TenderCreditDetailCustomizeVO> creditRecordList) {
        this.creditRecordList = creditRecordList;
    }

    public Integer getCurrentHoldObligatoryRightCount() {
        return currentHoldObligatoryRightCount;
    }

    public void setCurrentHoldObligatoryRightCount(Integer currentHoldObligatoryRightCount) {
        this.currentHoldObligatoryRightCount = currentHoldObligatoryRightCount;
    }

    public Integer getRepayMentCount() {
        return repayMentCount;
    }

    public void setRepayMentCount(Integer repayMentCount) {
        this.repayMentCount = repayMentCount;
    }

    public Integer getTenderCreditDetailCount() {
        return tenderCreditDetailCount;
    }

    public void setTenderCreditDetailCount(Integer tenderCreditDetailCount) {
        this.tenderCreditDetailCount = tenderCreditDetailCount;
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
