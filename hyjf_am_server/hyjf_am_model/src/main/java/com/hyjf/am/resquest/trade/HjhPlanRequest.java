package com.hyjf.am.resquest.trade;

public class HjhPlanRequest {

    private Integer limitStart;

    private Integer limitEnd;
    /**
     * app端计划列表查询使用
     */
    private String isHome;

    // 没有可以投资的汇计划，按照锁定时间倒叙排列
    private String lockFlag;


    public Integer getLimitStart() {
        return limitStart;
    }

    public void setLimitStart(Integer limitStart) {
        this.limitStart = limitStart;
    }

    public Integer getLimitEnd() {
        return limitEnd;
    }

    public void setLimitEnd(Integer limitEnd) {
        this.limitEnd = limitEnd;
    }

    public String getIsHome() {
        return isHome;
    }

    public void setIsHome(String isHome) {
        this.isHome = isHome;
    }

    public String getLockFlag() {
        return lockFlag;
    }

    public void setLockFlag(String lockFlag) {
        this.lockFlag = lockFlag;
    }
}
