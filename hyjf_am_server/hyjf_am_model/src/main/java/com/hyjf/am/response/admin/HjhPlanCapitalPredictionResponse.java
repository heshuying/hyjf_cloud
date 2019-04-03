package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.trade.HjhPlanCapitalPredictionVO;

import java.math.BigDecimal;

/**
 * 汇计划-计划资金
 * @Author : huanghui
 */
public class HjhPlanCapitalPredictionResponse extends Response<HjhPlanCapitalPredictionVO> {

    private int count;

    /**
     * 预计当日新增债转额
     */
    private BigDecimal creditAccount;

    /**
     * 预计当日新增复投额
     */
    private BigDecimal reinvestAccount;

    /**
     * 预计当日所需资金量:预计当日新增债转额（元）- 预计当日新增债转额（元）（若为负数显示为0）
     */
    private BigDecimal capitalAccount;

    /**
     * 预计当日所需资产量:预计当日新增债转额（元）-预计当日新增债转额（元）（若为负数显示为0）
     */
    private BigDecimal assetAccount;

    private HjhPlanCapitalPredictionVO sumHjhPlanCapitalPredictionVO;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public BigDecimal getCreditAccount() {
        return creditAccount;
    }

    public void setCreditAccount(BigDecimal creditAccount) {
        this.creditAccount = creditAccount;
    }

    public BigDecimal getReinvestAccount() {
        return reinvestAccount;
    }

    public void setReinvestAccount(BigDecimal reinvestAccount) {
        this.reinvestAccount = reinvestAccount;
    }

    public BigDecimal getCapitalAccount() {
        return capitalAccount;
    }

    public void setCapitalAccount(BigDecimal capitalAccount) {
        this.capitalAccount = capitalAccount;
    }

    public BigDecimal getAssetAccount() {
        return assetAccount;
    }

    public void setAssetAccount(BigDecimal assetAccount) {
        this.assetAccount = assetAccount;
    }

    public HjhPlanCapitalPredictionVO getSumHjhPlanCapitalPredictionVO() {
        return sumHjhPlanCapitalPredictionVO;
    }

    public void setSumHjhPlanCapitalPredictionVO(HjhPlanCapitalPredictionVO sumHjhPlanCapitalPredictionVO) {
        this.sumHjhPlanCapitalPredictionVO = sumHjhPlanCapitalPredictionVO;
    }
}
