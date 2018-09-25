/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.trade;

import com.hyjf.am.response.WrbResponse;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 标的投资详情查询相应
 * @author fq
 * @version WrbBorrowInvestResponse, v0.1 2018/9/25 11:31
 */
public class WrbBorrowInvestResponse extends WrbResponse {
    // 借款人id
    private String borrow_id;
    // 首笔投资时间
    private String first_invest_time = "";
    // 末笔投资时间
    private String last_invest_time = "";
    // 标的投资人数
    private String all_investors = "";
    // 标的已投金额
    private BigDecimal invest_all_money = BigDecimal.ZERO;
    // 标的投资情况
    private List<InvestInfo> invest_list = new ArrayList<>();

    public static final class InvestInfo{
        // 记录流水号
        private String index = "";
        // 投资用户
        private String invest_user = "";
        // 投资金额
        private BigDecimal invest_money = BigDecimal.ZERO;
        // 投资时间
        private String invest_time = "";

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

        public void setInvest_money(BigDecimal invest_money) {
            this.invest_money = invest_money;
        }

        public String getInvest_time() {
            return invest_time;
        }

        public void setInvest_time(String invest_time) {
            this.invest_time = invest_time;
        }
    }

    public String getBorrow_id() {
        return borrow_id;
    }

    public void setBorrow_id(String borrow_id) {
        this.borrow_id = borrow_id;
    }

    public String getFirst_invest_time() {
        return first_invest_time;
    }

    public void setFirst_invest_time(String first_invest_time) {
        this.first_invest_time = first_invest_time;
    }

    public String getLast_invest_time() {
        return last_invest_time;
    }

    public void setLast_invest_time(String last_invest_time) {
        this.last_invest_time = last_invest_time;
    }

    public String getAll_investors() {
        return all_investors;
    }

    public void setAll_investors(String all_investors) {
        this.all_investors = all_investors;
    }

    public BigDecimal getInvest_all_money() {
        return invest_all_money;
    }

    public void setInvest_all_money(BigDecimal invest_all_money) {
        this.invest_all_money = invest_all_money;
    }

    public List<InvestInfo> getInvest_list() {
        return invest_list;
    }

    public void setInvest_list(List<InvestInfo> invest_list) {
        this.invest_list = invest_list;
    }
}
