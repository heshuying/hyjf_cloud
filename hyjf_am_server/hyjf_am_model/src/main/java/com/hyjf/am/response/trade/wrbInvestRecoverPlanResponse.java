package com.hyjf.am.response.trade;

import com.hyjf.am.response.WrbResponse;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author lisheng
 * @version wrbInvestRecoverPlanResponse, v0.1 2018/11/7 10:09
 */

public class wrbInvestRecoverPlanResponse extends WrbResponse {

    /** 出借记录流水号 */
    private String invest_record_id;

    /** 回款记录 */
    private List<WrbRecoverRecord> back_records;

    public String getInvest_record_id() {
        return invest_record_id;
    }

    public void setInvest_record_id(String invest_record_id) {
        this.invest_record_id = invest_record_id;
    }

    public List<WrbRecoverRecord> getBack_records() {
        return back_records;
    }

    public void setBack_records(List<WrbRecoverRecord> back_records) {
        this.back_records = back_records;
    }

    public static final class WrbRecoverRecord {

        /** 回款日期 */
        private String back_date = "";

        /** 回款本金 */
        private BigDecimal back_principal = BigDecimal.ZERO;

        /** 回款利息 */
        private BigDecimal back_interest = BigDecimal.ZERO;

        /** 回款金额 */
        private BigDecimal back_money = BigDecimal.ZERO;

        /** 回款状态 */
        private Integer back_status;

        /** 服务费 */
        private BigDecimal service_fee = BigDecimal.ZERO;

        public String getBack_date() {
            return back_date;
        }

        public void setBack_date(String back_date) {
            this.back_date = back_date;
        }

        public BigDecimal getBack_principal() {
            return back_principal;
        }

        public void setBack_principal(BigDecimal back_principal) {
            this.back_principal = back_principal;
        }

        public BigDecimal getBack_interest() {
            return back_interest;
        }

        public void setBack_interest(BigDecimal back_interest) {
            this.back_interest = back_interest;
        }

        public BigDecimal getBack_money() {
            return back_money;
        }

        public void setBack_money(BigDecimal back_money) {
            this.back_money = back_money;
        }

        public Integer getBack_status() {
            return back_status;
        }

        public void setBack_status(Integer back_status) {
            this.back_status = back_status;
        }

        public BigDecimal getService_fee() {
            return service_fee;
        }

        public void setService_fee(BigDecimal service_fee) {
            this.service_fee = service_fee;
        }
    }
}
