/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wangjun
 * @version WxRechargeDescResultBean, v0.1 2018/7/26 9:13
 */
public class WxRechargeDescResultBean extends BaseResultBeanFrontEnd implements Serializable {
    // 充值限额说明
    private List<RechargeLimitAmountDesc> list;

    public WxRechargeDescResultBean(){
        list = new ArrayList<>();
    }

    public static class RechargeLimitAmountDesc {
        private String bankName;
        private String once;
        private String day;
        private String month;
        // 银行ICON add by huanghui
        private String bankIcon;

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

        public String getBankIcon() {
            return bankIcon;
        }

        public void setBankIcon(String bankIcon) {
            this.bankIcon = bankIcon;
        }
    }

    public List<RechargeLimitAmountDesc> getList() {
        return list;
    }

    public void setList(List<RechargeLimitAmountDesc> list) {
        this.list = list;
    }
}
