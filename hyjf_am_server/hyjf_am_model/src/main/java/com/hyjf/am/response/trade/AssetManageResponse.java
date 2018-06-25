package com.hyjf.am.response.trade;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.BaseVO;

import com.hyjf.am.vo.trade.TenderCreditDetailCustomizeVO;
import com.hyjf.am.vo.trade.assetmanage.CurrentHoldObligatoryRightListCustomizeVO;
import com.hyjf.am.vo.trade.assetmanage.RepayMentListCustomizeVO;

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
}
