/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.admin;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;
import java.util.Date;

/**
 * @author nxl
 * @version NifaFieldDefinitionVO, v0.1 2018/8/15 10:13
 * 互金字段定义表VO
 */
public class NifaFieldDefinitionVO extends BaseVO implements Serializable {
    private Integer id;

    private String borrowingRestrictions;

    private String judgmentsBased;

    private String repayDateRule;

    private String overdueDefinition;

    private String overdueResponsibility;

    private String overdueProcess;

    private Integer createUserId;

    private Integer updateUserId;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBorrowingRestrictions() {
        return borrowingRestrictions;
    }

    public void setBorrowingRestrictions(String borrowingRestrictions) {
        this.borrowingRestrictions = borrowingRestrictions == null ? null : borrowingRestrictions.trim();
    }

    public String getJudgmentsBased() {
        return judgmentsBased;
    }

    public void setJudgmentsBased(String judgmentsBased) {
        this.judgmentsBased = judgmentsBased == null ? null : judgmentsBased.trim();
    }

    public String getRepayDateRule() {
        return repayDateRule;
    }

    public void setRepayDateRule(String repayDateRule) {
        this.repayDateRule = repayDateRule == null ? null : repayDateRule.trim();
    }

    public String getOverdueDefinition() {
        return overdueDefinition;
    }

    public void setOverdueDefinition(String overdueDefinition) {
        this.overdueDefinition = overdueDefinition == null ? null : overdueDefinition.trim();
    }

    public String getOverdueResponsibility() {
        return overdueResponsibility;
    }

    public void setOverdueResponsibility(String overdueResponsibility) {
        this.overdueResponsibility = overdueResponsibility == null ? null : overdueResponsibility.trim();
    }

    public String getOverdueProcess() {
        return overdueProcess;
    }

    public void setOverdueProcess(String overdueProcess) {
        this.overdueProcess = overdueProcess == null ? null : overdueProcess.trim();
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
