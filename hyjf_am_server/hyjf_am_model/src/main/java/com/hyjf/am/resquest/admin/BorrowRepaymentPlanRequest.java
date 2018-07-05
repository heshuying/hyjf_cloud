package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;

/**
 * @author pangchengchao
 * @version BorrowRepaymentPlanRequest, v0.1 2018/7/5 11:27
 */
public class BorrowRepaymentPlanRequest extends BasePage {

    /**
     * 应还日期 检索条件
     */
    private String repayLastTimeStartSrch;
    /**
     * 应还日期 检索条件
     */
    private String repayLastTimeEndSrch;

    /**
     * 发布日期 检索条件
     */
    private String verifyTimeStartSrch;
    /**
     * 发布日期 检索条件
     */
    private String verifyTimeEndSrch;

    /**
     * 机构名称代号 检索条件
     */
    private String instCodeSrch;

    public String getRepayLastTimeStartSrch() {
        return repayLastTimeStartSrch;
    }

    public void setRepayLastTimeStartSrch(String repayLastTimeStartSrch) {
        this.repayLastTimeStartSrch = repayLastTimeStartSrch;
    }

    public String getRepayLastTimeEndSrch() {
        return repayLastTimeEndSrch;
    }

    public void setRepayLastTimeEndSrch(String repayLastTimeEndSrch) {
        this.repayLastTimeEndSrch = repayLastTimeEndSrch;
    }

    public String getVerifyTimeStartSrch() {
        return verifyTimeStartSrch;
    }

    public void setVerifyTimeStartSrch(String verifyTimeStartSrch) {
        this.verifyTimeStartSrch = verifyTimeStartSrch;
    }

    public String getVerifyTimeEndSrch() {
        return verifyTimeEndSrch;
    }

    public void setVerifyTimeEndSrch(String verifyTimeEndSrch) {
        this.verifyTimeEndSrch = verifyTimeEndSrch;
    }

    public String getInstCodeSrch() {
        return instCodeSrch;
    }

    public void setInstCodeSrch(String instCodeSrch) {
        this.instCodeSrch = instCodeSrch;
    }
}
