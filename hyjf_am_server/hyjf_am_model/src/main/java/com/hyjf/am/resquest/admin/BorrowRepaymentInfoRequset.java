package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author pangchengchao
 * @version BorrowRepaymentInfoRequset, v0.1 2018/7/7 15:00
 */
public class BorrowRepaymentInfoRequset extends BasePage {


    private String nid;
    @ApiModelProperty(value = "借款编号（导出用）")
    private String borrowNid;
    /**
     * 借款人 检索条件
     */
    @ApiModelProperty(value = "借款人")
    private String borrowUserName;
    @ApiModelProperty(value = "资产来源")
    private String instCodeSrch;
    /**
     * 借款编号 检索条件
     */
    @ApiModelProperty(value = "借款编号")
    private String borrowNidSrch;
    /**
     * 借款编号 检索条件
     */
    @ApiModelProperty(value = "计划编号")
    private String planNidSrch;

    /**
     * 用户名 检索条件
     */
    @ApiModelProperty(value = "用户名")
    private String recoverUserName;
    /**
     * 还款批次号 检索条件
     */
    @ApiModelProperty(value = "还款批次号")
    private String repayBatchNo;
    /**
     * 还款状态 检索条件
     */
    @ApiModelProperty(value = "还款状态")
    private String status;

    /**
     * 应还日期 检索条件
     */
    @ApiModelProperty(value = "应还日期开始")
    private String timeStartSrch;
    /**
     * 出借时间 检索条件
     */
    @ApiModelProperty(value = "应还日期结束")
    private String timeEndSrch;

    /**
     * 实际还款时间 检索条件
     */
    @ApiModelProperty(value = "实际还款时间开始")
    private String yesTimeStartSrch;
    /**
     * 实际还款时间 检索条件
     */
    @ApiModelProperty(value = "实际还款时间结束")
    private String yesTimeEndSrch;


    /**
     * 列表来源标识 0：还款明细 1：批次还款-查看按钮
     */
    @ApiModelProperty(value = "列表来源标识 0：还款明细 1：批次还款-查看按钮")
    private  int serchFlag = 0;
    private String accedeOrderIdSrch;
    private String recoverPeriod;
    
    
    public String getRecoverPeriod() {
		return recoverPeriod;
	}

	public void setRecoverPeriod(String recoverPeriod) {
		this.recoverPeriod = recoverPeriod;
	}
    /**
     * 检索条件 limitStart
     */
    private int limitStart = -1;
    /**
     * 检索条件 limitEnd
     */
    private int limitEnd = -1;

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getBorrowUserName() {
        return borrowUserName;
    }

    public void setBorrowUserName(String borrowUserName) {
        this.borrowUserName = borrowUserName;
    }

    public String getInstCodeSrch() {
        return instCodeSrch;
    }

    public void setInstCodeSrch(String instCodeSrch) {
        this.instCodeSrch = instCodeSrch;
    }

    public String getBorrowNidSrch() {
        return borrowNidSrch;
    }

    public void setBorrowNidSrch(String borrowNidSrch) {
        this.borrowNidSrch = borrowNidSrch;
    }

    public String getPlanNidSrch() {
        return planNidSrch;
    }

    public void setPlanNidSrch(String planNidSrch) {
        this.planNidSrch = planNidSrch;
    }

    public String getRecoverUserName() {
        return recoverUserName;
    }

    public void setRecoverUserName(String recoverUserName) {
        this.recoverUserName = recoverUserName;
    }

    public String getRepayBatchNo() {
        return repayBatchNo;
    }

    public void setRepayBatchNo(String repayBatchNo) {
        this.repayBatchNo = repayBatchNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTimeStartSrch() {
        return timeStartSrch;
    }

    public void setTimeStartSrch(String timeStartSrch) {
        this.timeStartSrch = timeStartSrch;
    }

    public String getTimeEndSrch() {
        return timeEndSrch;
    }

    public void setTimeEndSrch(String timeEndSrch) {
        this.timeEndSrch = timeEndSrch;
    }

    public String getYesTimeStartSrch() {
        return yesTimeStartSrch;
    }

    public void setYesTimeStartSrch(String yesTimeStartSrch) {
        this.yesTimeStartSrch = yesTimeStartSrch;
    }

    public String getYesTimeEndSrch() {
        return yesTimeEndSrch;
    }

    public void setYesTimeEndSrch(String yesTimeEndSrch) {
        this.yesTimeEndSrch = yesTimeEndSrch;
    }

    public int getSerchFlag() {
        return serchFlag;
    }

    public void setSerchFlag(int serchFlag) {
        this.serchFlag = serchFlag;
    }

    public int getLimitStart() {
        return limitStart;
    }

    public void setLimitStart(int limitStart) {
        this.limitStart = limitStart;
    }

    public int getLimitEnd() {
        return limitEnd;
    }

    public void setLimitEnd(int limitEnd) {
        this.limitEnd = limitEnd;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getAccedeOrderIdSrch() {
        return accedeOrderIdSrch;
    }

    public void setAccedeOrderIdSrch(String accedeOrderIdSrch) {
        this.accedeOrderIdSrch = accedeOrderIdSrch;
    }
}
