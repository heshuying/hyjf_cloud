package com.hyjf.admin.beans.request;

import com.hyjf.admin.beans.BaseRequest;

import java.io.Serializable;

/**
 * @author pangchengchao
 * @version BorrowRepaymentInfoListRequestBean, v0.1 2018/7/10 9:34
 */
public class BorrowRepaymentInfoListRequestBean extends BaseRequest implements Serializable {

    private String isMonth;//是否分期
    private String nid;// 投资nid,还款订单号
    private String borrowNid;// 借款编号
    private String accedeOrderId;//加入订单号
    private String recoverPeriod;// 还款期次
    private String recoverUserName;// 投资人用户名
    private String status;// 还款状态
    /**
     * 来自哪个Controller,0,1等
     */
    private String actfrom;
    /**
     * 应还日期 检索条件
     */
    private String recoverTimeStartSrch;

    /**
     * 机构名称代号 检索条件
     */
    private String instCodeSrch;
    /**
     * 应还日期 检索条件
     */
    private String recoverTimeEndSrch;

    public String getIsMonth() {
        return isMonth;
    }

    public void setIsMonth(String isMonth) {
        this.isMonth = isMonth;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getAccedeOrderId() {
        return accedeOrderId;
    }

    public void setAccedeOrderId(String accedeOrderId) {
        this.accedeOrderId = accedeOrderId;
    }

    public String getRecoverPeriod() {
        return recoverPeriod;
    }

    public void setRecoverPeriod(String recoverPeriod) {
        this.recoverPeriod = recoverPeriod;
    }

    public String getRecoverUserName() {
        return recoverUserName;
    }

    public void setRecoverUserName(String recoverUserName) {
        this.recoverUserName = recoverUserName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getActfrom() {
        return actfrom;
    }

    public void setActfrom(String actfrom) {
        this.actfrom = actfrom;
    }

    public String getRecoverTimeStartSrch() {
        return recoverTimeStartSrch;
    }

    public void setRecoverTimeStartSrch(String recoverTimeStartSrch) {
        this.recoverTimeStartSrch = recoverTimeStartSrch;
    }

    public String getInstCodeSrch() {
        return instCodeSrch;
    }

    public void setInstCodeSrch(String instCodeSrch) {
        this.instCodeSrch = instCodeSrch;
    }

    public String getRecoverTimeEndSrch() {
        return recoverTimeEndSrch;
    }

    public void setRecoverTimeEndSrch(String recoverTimeEndSrch) {
        this.recoverTimeEndSrch = recoverTimeEndSrch;
    }
}
