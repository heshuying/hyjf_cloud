/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.bean;

/**
 * @author fq
 * @version WrbInvestRequest, v0.1 2018/9/26 9:40
 */
public class WrbInvestRequest {
    /** 投资日期 */
    private String invest_date;

    /** 条数 */
    private Integer limit;

    /** 页数 */
    private Integer page;

    public String getInvest_date() {
        return invest_date;
    }

    public void setInvest_date(String invest_date) {
        this.invest_date = invest_date;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }
}
