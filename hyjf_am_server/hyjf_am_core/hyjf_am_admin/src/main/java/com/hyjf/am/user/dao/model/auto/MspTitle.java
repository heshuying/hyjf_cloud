package com.hyjf.am.user.dao.model.auto;

import java.io.Serializable;

public class MspTitle implements Serializable {
    private Integer id;

    /**
     * 申请编号
     *
     * @mbggenerated
     */
    private String applyId;

    /**
     * 姓名
     *
     * @mbggenerated
     */
    private String customername;

    /**
     * 身份证号
     *
     * @mbggenerated
     */
    private String papernumber;

    /**
     * 报告生成时间
     *
     * @mbggenerated
     */
    private String reporttime;

    /**
     * 未结清
     *
     * @mbggenerated
     */
    private String wjqcount;

    /**
     * 已结清
     *
     * @mbggenerated
     */
    private String jqcount;

    /**
     * 小计
     *
     * @mbggenerated
     */
    private String totalcount;

    /**
     * 未结清
     *
     * @mbggenerated
     */
    private String ewjqcount;

    /**
     * 已结清
     *
     * @mbggenerated
     */
    private String ejqcount;

    /**
     * 小计
     *
     * @mbggenerated
     */
    private String etotalcount;

    /**
     * 申请中笔数
     *
     * @mbggenerated
     */
    private String applyingcount;

    /**
     * 通过笔数
     *
     * @mbggenerated
     */
    private String applypassedcount;

    /**
     * 拒绝笔数
     *
     * @mbggenerated
     */
    private String applyrejectcount;

    /**
     * 小计
     *
     * @mbggenerated
     */
    private String applytotalcount;

    /**
     * 申请/债权/逾期/补录/行业不良记录
     *
     * @mbggenerated
     */
    private String querycount;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId == null ? null : applyId.trim();
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername == null ? null : customername.trim();
    }

    public String getPapernumber() {
        return papernumber;
    }

    public void setPapernumber(String papernumber) {
        this.papernumber = papernumber == null ? null : papernumber.trim();
    }

    public String getReporttime() {
        return reporttime;
    }

    public void setReporttime(String reporttime) {
        this.reporttime = reporttime == null ? null : reporttime.trim();
    }

    public String getWjqcount() {
        return wjqcount;
    }

    public void setWjqcount(String wjqcount) {
        this.wjqcount = wjqcount == null ? null : wjqcount.trim();
    }

    public String getJqcount() {
        return jqcount;
    }

    public void setJqcount(String jqcount) {
        this.jqcount = jqcount == null ? null : jqcount.trim();
    }

    public String getTotalcount() {
        return totalcount;
    }

    public void setTotalcount(String totalcount) {
        this.totalcount = totalcount == null ? null : totalcount.trim();
    }

    public String getEwjqcount() {
        return ewjqcount;
    }

    public void setEwjqcount(String ewjqcount) {
        this.ewjqcount = ewjqcount == null ? null : ewjqcount.trim();
    }

    public String getEjqcount() {
        return ejqcount;
    }

    public void setEjqcount(String ejqcount) {
        this.ejqcount = ejqcount == null ? null : ejqcount.trim();
    }

    public String getEtotalcount() {
        return etotalcount;
    }

    public void setEtotalcount(String etotalcount) {
        this.etotalcount = etotalcount == null ? null : etotalcount.trim();
    }

    public String getApplyingcount() {
        return applyingcount;
    }

    public void setApplyingcount(String applyingcount) {
        this.applyingcount = applyingcount == null ? null : applyingcount.trim();
    }

    public String getApplypassedcount() {
        return applypassedcount;
    }

    public void setApplypassedcount(String applypassedcount) {
        this.applypassedcount = applypassedcount == null ? null : applypassedcount.trim();
    }

    public String getApplyrejectcount() {
        return applyrejectcount;
    }

    public void setApplyrejectcount(String applyrejectcount) {
        this.applyrejectcount = applyrejectcount == null ? null : applyrejectcount.trim();
    }

    public String getApplytotalcount() {
        return applytotalcount;
    }

    public void setApplytotalcount(String applytotalcount) {
        this.applytotalcount = applytotalcount == null ? null : applytotalcount.trim();
    }

    public String getQuerycount() {
        return querycount;
    }

    public void setQuerycount(String querycount) {
        this.querycount = querycount == null ? null : querycount.trim();
    }
}