package com.hyjf.am.resquest.app;

import com.hyjf.am.vo.BasePage;

/**
 * @author pangchengchao
 * @version AppTradeDetailBeanRequest, v0.1 2018/8/6 14:21
 */
public class  AppTradeDetailBeanRequest extends BasePage {
    /**
     *
     */
    private static final long serialVersionUID = 3458197418404401541L;
    /** 用户id */
    private Integer userId;
    /** 交易类型 */
    private String tradeType;
    /** 交易年 */
    private String year;
    /** 交易月 */
    private String month;
    private Integer limitStart;

    private Integer limitEnd;

    public AppTradeDetailBeanRequest() {
        super();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Integer getLimitStart() {
        return limitStart;
    }

    public void setLimitStart(Integer limitStart) {
        this.limitStart = limitStart;
    }

    public Integer getLimitEnd() {
        return limitEnd;
    }

    public void setLimitEnd(Integer limitEnd) {
        this.limitEnd = limitEnd;
    }
}
