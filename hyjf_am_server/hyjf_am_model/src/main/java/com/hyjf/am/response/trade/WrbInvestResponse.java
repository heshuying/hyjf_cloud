/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.trade;

import com.hyjf.am.response.WrbResponse;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author fq
 * @version WrbInvestResponse, v0.1 2018/9/26 9:41
 */
public class WrbInvestResponse extends WrbResponse {

    private List<InvestDetail> invest_list;

    public WrbInvestResponse() {
        super();
        this.invest_list = new ArrayList();
    }

    public List<InvestDetail> getInvest_list() {
        return invest_list;
    }

    public void setInvest_list(List<InvestDetail> invest_list) {
        this.invest_list = invest_list;
    }

    public static final class InvestDetail {

        /** 记录流水号 */
        private String index;

        /** 投资用户 */
        private String invest_user = "";

        /** 投资金额 */
        private BigDecimal invest_money = BigDecimal.ZERO;

        /** 投资时间 */
        private String invest_time = null;

        /** 借款人ID */
        private String borrow_id = "";

        /** 项目ID */
        private String bid_id = "";

        public String getIndex() {
            return index;
        }

        public void setIndex(String index) {
            this.index = index;
        }

        public String getInvest_user() {
            return invest_user;
        }

        public void setInvest_user(String invest_user) {
            this.invest_user = invest_user;
        }

        public BigDecimal getInvest_money() {
            return invest_money;
        }

        public String getInvest_time() {
            return invest_time;
        }

        public void setInvest_time(String invest_time) {
            this.invest_time = invest_time;
        }

        public void setInvest_money(BigDecimal invest_money) {
            this.invest_money = invest_money;
        }

        public String getBorrow_id() {
            return borrow_id;
        }

        public void setBorrow_id(String borrow_id) {
            this.borrow_id = borrow_id;
        }

        public String getBid_id() {
            return bid_id;
        }

        public void setBid_id(String bid_id) {
            this.bid_id = bid_id;
        }
    }
}
