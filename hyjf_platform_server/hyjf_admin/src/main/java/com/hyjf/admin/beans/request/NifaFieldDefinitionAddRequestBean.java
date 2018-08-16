/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.request;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author nxl
 * @version NifaFieldDefinitionAddRequestBean, v0.1 2018/6/22 11:36
 */
public class NifaFieldDefinitionAddRequestBean {
    @ApiModelProperty(value = "id(主键)")
    private Integer id;
    /** 用户id */
    @ApiModelProperty(value = "借款用途限制")
    private String borrowingRestrictions;
    @ApiModelProperty(value = "借款放款日判断依据")
    private String judgmentsBased;
    @ApiModelProperty(value = "还款日规则说明")
    private String repayDateRule;
    @ApiModelProperty(value = "逾期定义")
    private String overdueDefinition;
    @ApiModelProperty(value = "逾期还款责任")
    private String overdueResponsibility;
    @ApiModelProperty(value = "逾期还款流程")
    private String overdueProcess;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
