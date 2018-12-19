package com.hyjf.am.resquest.api;

/**
 * @author lisheng
 * @version WrbInvestRecordRequest, v0.1 2018/11/7 10:09
 */

public class WrbInvestRecordRequest {
    /** 平台用户id */
    private String pf_user_id;

    /** 开始时间 */
    private String start_time;

    /** 结束时间 */
    private String end_time;

    /** 出借记录类型 */
    private Integer invest_status;

    /** 偏移 0,10,20递增 */
    private Integer offset;

    /** 条数 10即每页10条 */
    private Integer limit;

    /** 出借记录id  如果传了id，只查询该用户这个id的记录*/
    private String invest_record_id;

    public String getPf_user_id() {
        return pf_user_id;
    }

    public void setPf_user_id(String pf_user_id) {
        this.pf_user_id = pf_user_id;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public Integer getInvest_status() {
        return invest_status;
    }

    public void setInvest_status(Integer invest_status) {
        this.invest_status = invest_status;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public String getInvest_record_id() {
        return invest_record_id;
    }

    public void setInvest_record_id(String invest_record_id) {
        this.invest_record_id = invest_record_id;
    }
}
