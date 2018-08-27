/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.admin;
import java.io.Serializable;
import java.util.Date;


/**
 * @author nxl
 * @version NifaFieldDefinitionAddRequest, v0.1 2018/8/15 10:13
 */
public class NifaFieldDefinitionAddRequest  implements Serializable{
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
        this.borrowingRestrictions = borrowingRestrictions;
    }

    public String getJudgmentsBased() {
        return judgmentsBased;
    }

    public void setJudgmentsBased(String judgmentsBased) {
        this.judgmentsBased = judgmentsBased;
    }

    public String getRepayDateRule() {
        return repayDateRule;
    }

    public void setRepayDateRule(String repayDateRule) {
        this.repayDateRule = repayDateRule;
    }

    public String getOverdueDefinition() {
        return overdueDefinition;
    }

    public void setOverdueDefinition(String overdueDefinition) {
        this.overdueDefinition = overdueDefinition;
    }

    public String getOverdueResponsibility() {
        return overdueResponsibility;
    }

    public void setOverdueResponsibility(String overdueResponsibility) {
        this.overdueResponsibility = overdueResponsibility;
    }

    public String getOverdueProcess() {
        return overdueProcess;
    }

    public void setOverdueProcess(String overdueProcess) {
        this.overdueProcess = overdueProcess;
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
