package com.hyjf.admin.beans;

import com.hyjf.am.vo.BaseVO;
import com.hyjf.am.vo.admin.AdminRepayDelayCustomizeVO;

/**
 * @author pangchengchao
 * @version DelayRepayInfoBean, v0.1 2018/7/6 15:45
 */
public class DelayRepayInfoBean {

    private AdminRepayDelayCustomizeVO borrowRepayInfo;
    private BaseVO repayInfo;
    private  String repayTime;
    private Integer delayDays;
    private  String success;
    private  String repayTimeForMsg;


    public AdminRepayDelayCustomizeVO getBorrowRepayInfo() {
        return borrowRepayInfo;
    }

    public void setBorrowRepayInfo(AdminRepayDelayCustomizeVO borrowRepayInfo) {
        this.borrowRepayInfo = borrowRepayInfo;
    }

    public BaseVO getRepayInfo() {
        return repayInfo;
    }

    public void setRepayInfo(BaseVO repayInfo) {
        this.repayInfo = repayInfo;
    }

    public String getRepayTime() {
        return repayTime;
    }

    public void setRepayTime(String repayTime) {
        this.repayTime = repayTime;
    }

    public Integer getDelayDays() {
        return delayDays;
    }

    public void setDelayDays(Integer delayDays) {
        this.delayDays = delayDays;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getRepayTimeForMsg() {
        return repayTimeForMsg;
    }

    public void setRepayTimeForMsg(String repayTimeForMsg) {
        this.repayTimeForMsg = repayTimeForMsg;
    }
}
