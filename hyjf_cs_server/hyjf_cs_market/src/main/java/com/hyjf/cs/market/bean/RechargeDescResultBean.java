package com.hyjf.cs.market.bean;

/**
 * @author lisheng
 * @version RechargeDescResultBean, v0.1 2018/8/21 16:02
 */

public class RechargeDescResultBean {
        private static final long serialVersionUID = 1L;
        private String bankName;
        private String once;
        private String day;
        private String month;

        public String getBankName() {
            return bankName;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }

        public String getOnce() {
            return once;
        }

        public void setOnce(String once) {
            this.once = once;
        }

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }


}
