package com.hyjf.am.market.dao.model.auto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SellDailyExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public SellDailyExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    public void setLimitStart(int limitStart) {
        this.limitStart=limitStart;
    }

    public int getLimitStart() {
        return limitStart;
    }

    public void setLimitEnd(int limitEnd) {
        this.limitEnd=limitEnd;
    }

    public int getLimitEnd() {
        return limitEnd;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andDateStrIsNull() {
            addCriterion("date_str is null");
            return (Criteria) this;
        }

        public Criteria andDateStrIsNotNull() {
            addCriterion("date_str is not null");
            return (Criteria) this;
        }

        public Criteria andDateStrEqualTo(String value) {
            addCriterion("date_str =", value, "dateStr");
            return (Criteria) this;
        }

        public Criteria andDateStrNotEqualTo(String value) {
            addCriterion("date_str <>", value, "dateStr");
            return (Criteria) this;
        }

        public Criteria andDateStrGreaterThan(String value) {
            addCriterion("date_str >", value, "dateStr");
            return (Criteria) this;
        }

        public Criteria andDateStrGreaterThanOrEqualTo(String value) {
            addCriterion("date_str >=", value, "dateStr");
            return (Criteria) this;
        }

        public Criteria andDateStrLessThan(String value) {
            addCriterion("date_str <", value, "dateStr");
            return (Criteria) this;
        }

        public Criteria andDateStrLessThanOrEqualTo(String value) {
            addCriterion("date_str <=", value, "dateStr");
            return (Criteria) this;
        }

        public Criteria andDateStrLike(String value) {
            addCriterion("date_str like", value, "dateStr");
            return (Criteria) this;
        }

        public Criteria andDateStrNotLike(String value) {
            addCriterion("date_str not like", value, "dateStr");
            return (Criteria) this;
        }

        public Criteria andDateStrIn(List<String> values) {
            addCriterion("date_str in", values, "dateStr");
            return (Criteria) this;
        }

        public Criteria andDateStrNotIn(List<String> values) {
            addCriterion("date_str not in", values, "dateStr");
            return (Criteria) this;
        }

        public Criteria andDateStrBetween(String value1, String value2) {
            addCriterion("date_str between", value1, value2, "dateStr");
            return (Criteria) this;
        }

        public Criteria andDateStrNotBetween(String value1, String value2) {
            addCriterion("date_str not between", value1, value2, "dateStr");
            return (Criteria) this;
        }

        public Criteria andDrawOrderIsNull() {
            addCriterion("draw_order is null");
            return (Criteria) this;
        }

        public Criteria andDrawOrderIsNotNull() {
            addCriterion("draw_order is not null");
            return (Criteria) this;
        }

        public Criteria andDrawOrderEqualTo(Integer value) {
            addCriterion("draw_order =", value, "drawOrder");
            return (Criteria) this;
        }

        public Criteria andDrawOrderNotEqualTo(Integer value) {
            addCriterion("draw_order <>", value, "drawOrder");
            return (Criteria) this;
        }

        public Criteria andDrawOrderGreaterThan(Integer value) {
            addCriterion("draw_order >", value, "drawOrder");
            return (Criteria) this;
        }

        public Criteria andDrawOrderGreaterThanOrEqualTo(Integer value) {
            addCriterion("draw_order >=", value, "drawOrder");
            return (Criteria) this;
        }

        public Criteria andDrawOrderLessThan(Integer value) {
            addCriterion("draw_order <", value, "drawOrder");
            return (Criteria) this;
        }

        public Criteria andDrawOrderLessThanOrEqualTo(Integer value) {
            addCriterion("draw_order <=", value, "drawOrder");
            return (Criteria) this;
        }

        public Criteria andDrawOrderIn(List<Integer> values) {
            addCriterion("draw_order in", values, "drawOrder");
            return (Criteria) this;
        }

        public Criteria andDrawOrderNotIn(List<Integer> values) {
            addCriterion("draw_order not in", values, "drawOrder");
            return (Criteria) this;
        }

        public Criteria andDrawOrderBetween(Integer value1, Integer value2) {
            addCriterion("draw_order between", value1, value2, "drawOrder");
            return (Criteria) this;
        }

        public Criteria andDrawOrderNotBetween(Integer value1, Integer value2) {
            addCriterion("draw_order not between", value1, value2, "drawOrder");
            return (Criteria) this;
        }

        public Criteria andPrimaryDivisionIsNull() {
            addCriterion("primary_division is null");
            return (Criteria) this;
        }

        public Criteria andPrimaryDivisionIsNotNull() {
            addCriterion("primary_division is not null");
            return (Criteria) this;
        }

        public Criteria andPrimaryDivisionEqualTo(String value) {
            addCriterion("primary_division =", value, "primaryDivision");
            return (Criteria) this;
        }

        public Criteria andPrimaryDivisionNotEqualTo(String value) {
            addCriterion("primary_division <>", value, "primaryDivision");
            return (Criteria) this;
        }

        public Criteria andPrimaryDivisionGreaterThan(String value) {
            addCriterion("primary_division >", value, "primaryDivision");
            return (Criteria) this;
        }

        public Criteria andPrimaryDivisionGreaterThanOrEqualTo(String value) {
            addCriterion("primary_division >=", value, "primaryDivision");
            return (Criteria) this;
        }

        public Criteria andPrimaryDivisionLessThan(String value) {
            addCriterion("primary_division <", value, "primaryDivision");
            return (Criteria) this;
        }

        public Criteria andPrimaryDivisionLessThanOrEqualTo(String value) {
            addCriterion("primary_division <=", value, "primaryDivision");
            return (Criteria) this;
        }

        public Criteria andPrimaryDivisionLike(String value) {
            addCriterion("primary_division like", value, "primaryDivision");
            return (Criteria) this;
        }

        public Criteria andPrimaryDivisionNotLike(String value) {
            addCriterion("primary_division not like", value, "primaryDivision");
            return (Criteria) this;
        }

        public Criteria andPrimaryDivisionIn(List<String> values) {
            addCriterion("primary_division in", values, "primaryDivision");
            return (Criteria) this;
        }

        public Criteria andPrimaryDivisionNotIn(List<String> values) {
            addCriterion("primary_division not in", values, "primaryDivision");
            return (Criteria) this;
        }

        public Criteria andPrimaryDivisionBetween(String value1, String value2) {
            addCriterion("primary_division between", value1, value2, "primaryDivision");
            return (Criteria) this;
        }

        public Criteria andPrimaryDivisionNotBetween(String value1, String value2) {
            addCriterion("primary_division not between", value1, value2, "primaryDivision");
            return (Criteria) this;
        }

        public Criteria andTwoDivisionIsNull() {
            addCriterion("two_division is null");
            return (Criteria) this;
        }

        public Criteria andTwoDivisionIsNotNull() {
            addCriterion("two_division is not null");
            return (Criteria) this;
        }

        public Criteria andTwoDivisionEqualTo(String value) {
            addCriterion("two_division =", value, "twoDivision");
            return (Criteria) this;
        }

        public Criteria andTwoDivisionNotEqualTo(String value) {
            addCriterion("two_division <>", value, "twoDivision");
            return (Criteria) this;
        }

        public Criteria andTwoDivisionGreaterThan(String value) {
            addCriterion("two_division >", value, "twoDivision");
            return (Criteria) this;
        }

        public Criteria andTwoDivisionGreaterThanOrEqualTo(String value) {
            addCriterion("two_division >=", value, "twoDivision");
            return (Criteria) this;
        }

        public Criteria andTwoDivisionLessThan(String value) {
            addCriterion("two_division <", value, "twoDivision");
            return (Criteria) this;
        }

        public Criteria andTwoDivisionLessThanOrEqualTo(String value) {
            addCriterion("two_division <=", value, "twoDivision");
            return (Criteria) this;
        }

        public Criteria andTwoDivisionLike(String value) {
            addCriterion("two_division like", value, "twoDivision");
            return (Criteria) this;
        }

        public Criteria andTwoDivisionNotLike(String value) {
            addCriterion("two_division not like", value, "twoDivision");
            return (Criteria) this;
        }

        public Criteria andTwoDivisionIn(List<String> values) {
            addCriterion("two_division in", values, "twoDivision");
            return (Criteria) this;
        }

        public Criteria andTwoDivisionNotIn(List<String> values) {
            addCriterion("two_division not in", values, "twoDivision");
            return (Criteria) this;
        }

        public Criteria andTwoDivisionBetween(String value1, String value2) {
            addCriterion("two_division between", value1, value2, "twoDivision");
            return (Criteria) this;
        }

        public Criteria andTwoDivisionNotBetween(String value1, String value2) {
            addCriterion("two_division not between", value1, value2, "twoDivision");
            return (Criteria) this;
        }

        public Criteria andStoreNumIsNull() {
            addCriterion("store_num is null");
            return (Criteria) this;
        }

        public Criteria andStoreNumIsNotNull() {
            addCriterion("store_num is not null");
            return (Criteria) this;
        }

        public Criteria andStoreNumEqualTo(Integer value) {
            addCriterion("store_num =", value, "storeNum");
            return (Criteria) this;
        }

        public Criteria andStoreNumNotEqualTo(Integer value) {
            addCriterion("store_num <>", value, "storeNum");
            return (Criteria) this;
        }

        public Criteria andStoreNumGreaterThan(Integer value) {
            addCriterion("store_num >", value, "storeNum");
            return (Criteria) this;
        }

        public Criteria andStoreNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("store_num >=", value, "storeNum");
            return (Criteria) this;
        }

        public Criteria andStoreNumLessThan(Integer value) {
            addCriterion("store_num <", value, "storeNum");
            return (Criteria) this;
        }

        public Criteria andStoreNumLessThanOrEqualTo(Integer value) {
            addCriterion("store_num <=", value, "storeNum");
            return (Criteria) this;
        }

        public Criteria andStoreNumIn(List<Integer> values) {
            addCriterion("store_num in", values, "storeNum");
            return (Criteria) this;
        }

        public Criteria andStoreNumNotIn(List<Integer> values) {
            addCriterion("store_num not in", values, "storeNum");
            return (Criteria) this;
        }

        public Criteria andStoreNumBetween(Integer value1, Integer value2) {
            addCriterion("store_num between", value1, value2, "storeNum");
            return (Criteria) this;
        }

        public Criteria andStoreNumNotBetween(Integer value1, Integer value2) {
            addCriterion("store_num not between", value1, value2, "storeNum");
            return (Criteria) this;
        }

        public Criteria andInvestTotalMonthIsNull() {
            addCriterion("invest_total_month is null");
            return (Criteria) this;
        }

        public Criteria andInvestTotalMonthIsNotNull() {
            addCriterion("invest_total_month is not null");
            return (Criteria) this;
        }

        public Criteria andInvestTotalMonthEqualTo(BigDecimal value) {
            addCriterion("invest_total_month =", value, "investTotalMonth");
            return (Criteria) this;
        }

        public Criteria andInvestTotalMonthNotEqualTo(BigDecimal value) {
            addCriterion("invest_total_month <>", value, "investTotalMonth");
            return (Criteria) this;
        }

        public Criteria andInvestTotalMonthGreaterThan(BigDecimal value) {
            addCriterion("invest_total_month >", value, "investTotalMonth");
            return (Criteria) this;
        }

        public Criteria andInvestTotalMonthGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("invest_total_month >=", value, "investTotalMonth");
            return (Criteria) this;
        }

        public Criteria andInvestTotalMonthLessThan(BigDecimal value) {
            addCriterion("invest_total_month <", value, "investTotalMonth");
            return (Criteria) this;
        }

        public Criteria andInvestTotalMonthLessThanOrEqualTo(BigDecimal value) {
            addCriterion("invest_total_month <=", value, "investTotalMonth");
            return (Criteria) this;
        }

        public Criteria andInvestTotalMonthIn(List<BigDecimal> values) {
            addCriterion("invest_total_month in", values, "investTotalMonth");
            return (Criteria) this;
        }

        public Criteria andInvestTotalMonthNotIn(List<BigDecimal> values) {
            addCriterion("invest_total_month not in", values, "investTotalMonth");
            return (Criteria) this;
        }

        public Criteria andInvestTotalMonthBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("invest_total_month between", value1, value2, "investTotalMonth");
            return (Criteria) this;
        }

        public Criteria andInvestTotalMonthNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("invest_total_month not between", value1, value2, "investTotalMonth");
            return (Criteria) this;
        }

        public Criteria andRepaymentTotalMonthIsNull() {
            addCriterion("repayment_total_month is null");
            return (Criteria) this;
        }

        public Criteria andRepaymentTotalMonthIsNotNull() {
            addCriterion("repayment_total_month is not null");
            return (Criteria) this;
        }

        public Criteria andRepaymentTotalMonthEqualTo(BigDecimal value) {
            addCriterion("repayment_total_month =", value, "repaymentTotalMonth");
            return (Criteria) this;
        }

        public Criteria andRepaymentTotalMonthNotEqualTo(BigDecimal value) {
            addCriterion("repayment_total_month <>", value, "repaymentTotalMonth");
            return (Criteria) this;
        }

        public Criteria andRepaymentTotalMonthGreaterThan(BigDecimal value) {
            addCriterion("repayment_total_month >", value, "repaymentTotalMonth");
            return (Criteria) this;
        }

        public Criteria andRepaymentTotalMonthGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("repayment_total_month >=", value, "repaymentTotalMonth");
            return (Criteria) this;
        }

        public Criteria andRepaymentTotalMonthLessThan(BigDecimal value) {
            addCriterion("repayment_total_month <", value, "repaymentTotalMonth");
            return (Criteria) this;
        }

        public Criteria andRepaymentTotalMonthLessThanOrEqualTo(BigDecimal value) {
            addCriterion("repayment_total_month <=", value, "repaymentTotalMonth");
            return (Criteria) this;
        }

        public Criteria andRepaymentTotalMonthIn(List<BigDecimal> values) {
            addCriterion("repayment_total_month in", values, "repaymentTotalMonth");
            return (Criteria) this;
        }

        public Criteria andRepaymentTotalMonthNotIn(List<BigDecimal> values) {
            addCriterion("repayment_total_month not in", values, "repaymentTotalMonth");
            return (Criteria) this;
        }

        public Criteria andRepaymentTotalMonthBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repayment_total_month between", value1, value2, "repaymentTotalMonth");
            return (Criteria) this;
        }

        public Criteria andRepaymentTotalMonthNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repayment_total_month not between", value1, value2, "repaymentTotalMonth");
            return (Criteria) this;
        }

        public Criteria andInvestTotalPreviousMonthIsNull() {
            addCriterion("invest_total_previous_month is null");
            return (Criteria) this;
        }

        public Criteria andInvestTotalPreviousMonthIsNotNull() {
            addCriterion("invest_total_previous_month is not null");
            return (Criteria) this;
        }

        public Criteria andInvestTotalPreviousMonthEqualTo(BigDecimal value) {
            addCriterion("invest_total_previous_month =", value, "investTotalPreviousMonth");
            return (Criteria) this;
        }

        public Criteria andInvestTotalPreviousMonthNotEqualTo(BigDecimal value) {
            addCriterion("invest_total_previous_month <>", value, "investTotalPreviousMonth");
            return (Criteria) this;
        }

        public Criteria andInvestTotalPreviousMonthGreaterThan(BigDecimal value) {
            addCriterion("invest_total_previous_month >", value, "investTotalPreviousMonth");
            return (Criteria) this;
        }

        public Criteria andInvestTotalPreviousMonthGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("invest_total_previous_month >=", value, "investTotalPreviousMonth");
            return (Criteria) this;
        }

        public Criteria andInvestTotalPreviousMonthLessThan(BigDecimal value) {
            addCriterion("invest_total_previous_month <", value, "investTotalPreviousMonth");
            return (Criteria) this;
        }

        public Criteria andInvestTotalPreviousMonthLessThanOrEqualTo(BigDecimal value) {
            addCriterion("invest_total_previous_month <=", value, "investTotalPreviousMonth");
            return (Criteria) this;
        }

        public Criteria andInvestTotalPreviousMonthIn(List<BigDecimal> values) {
            addCriterion("invest_total_previous_month in", values, "investTotalPreviousMonth");
            return (Criteria) this;
        }

        public Criteria andInvestTotalPreviousMonthNotIn(List<BigDecimal> values) {
            addCriterion("invest_total_previous_month not in", values, "investTotalPreviousMonth");
            return (Criteria) this;
        }

        public Criteria andInvestTotalPreviousMonthBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("invest_total_previous_month between", value1, value2, "investTotalPreviousMonth");
            return (Criteria) this;
        }

        public Criteria andInvestTotalPreviousMonthNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("invest_total_previous_month not between", value1, value2, "investTotalPreviousMonth");
            return (Criteria) this;
        }

        public Criteria andInvestRatioGrowthIsNull() {
            addCriterion("invest_ratio_growth is null");
            return (Criteria) this;
        }

        public Criteria andInvestRatioGrowthIsNotNull() {
            addCriterion("invest_ratio_growth is not null");
            return (Criteria) this;
        }

        public Criteria andInvestRatioGrowthEqualTo(String value) {
            addCriterion("invest_ratio_growth =", value, "investRatioGrowth");
            return (Criteria) this;
        }

        public Criteria andInvestRatioGrowthNotEqualTo(String value) {
            addCriterion("invest_ratio_growth <>", value, "investRatioGrowth");
            return (Criteria) this;
        }

        public Criteria andInvestRatioGrowthGreaterThan(String value) {
            addCriterion("invest_ratio_growth >", value, "investRatioGrowth");
            return (Criteria) this;
        }

        public Criteria andInvestRatioGrowthGreaterThanOrEqualTo(String value) {
            addCriterion("invest_ratio_growth >=", value, "investRatioGrowth");
            return (Criteria) this;
        }

        public Criteria andInvestRatioGrowthLessThan(String value) {
            addCriterion("invest_ratio_growth <", value, "investRatioGrowth");
            return (Criteria) this;
        }

        public Criteria andInvestRatioGrowthLessThanOrEqualTo(String value) {
            addCriterion("invest_ratio_growth <=", value, "investRatioGrowth");
            return (Criteria) this;
        }

        public Criteria andInvestRatioGrowthLike(String value) {
            addCriterion("invest_ratio_growth like", value, "investRatioGrowth");
            return (Criteria) this;
        }

        public Criteria andInvestRatioGrowthNotLike(String value) {
            addCriterion("invest_ratio_growth not like", value, "investRatioGrowth");
            return (Criteria) this;
        }

        public Criteria andInvestRatioGrowthIn(List<String> values) {
            addCriterion("invest_ratio_growth in", values, "investRatioGrowth");
            return (Criteria) this;
        }

        public Criteria andInvestRatioGrowthNotIn(List<String> values) {
            addCriterion("invest_ratio_growth not in", values, "investRatioGrowth");
            return (Criteria) this;
        }

        public Criteria andInvestRatioGrowthBetween(String value1, String value2) {
            addCriterion("invest_ratio_growth between", value1, value2, "investRatioGrowth");
            return (Criteria) this;
        }

        public Criteria andInvestRatioGrowthNotBetween(String value1, String value2) {
            addCriterion("invest_ratio_growth not between", value1, value2, "investRatioGrowth");
            return (Criteria) this;
        }

        public Criteria andWithdrawTotalMonthIsNull() {
            addCriterion("withdraw_total_month is null");
            return (Criteria) this;
        }

        public Criteria andWithdrawTotalMonthIsNotNull() {
            addCriterion("withdraw_total_month is not null");
            return (Criteria) this;
        }

        public Criteria andWithdrawTotalMonthEqualTo(BigDecimal value) {
            addCriterion("withdraw_total_month =", value, "withdrawTotalMonth");
            return (Criteria) this;
        }

        public Criteria andWithdrawTotalMonthNotEqualTo(BigDecimal value) {
            addCriterion("withdraw_total_month <>", value, "withdrawTotalMonth");
            return (Criteria) this;
        }

        public Criteria andWithdrawTotalMonthGreaterThan(BigDecimal value) {
            addCriterion("withdraw_total_month >", value, "withdrawTotalMonth");
            return (Criteria) this;
        }

        public Criteria andWithdrawTotalMonthGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("withdraw_total_month >=", value, "withdrawTotalMonth");
            return (Criteria) this;
        }

        public Criteria andWithdrawTotalMonthLessThan(BigDecimal value) {
            addCriterion("withdraw_total_month <", value, "withdrawTotalMonth");
            return (Criteria) this;
        }

        public Criteria andWithdrawTotalMonthLessThanOrEqualTo(BigDecimal value) {
            addCriterion("withdraw_total_month <=", value, "withdrawTotalMonth");
            return (Criteria) this;
        }

        public Criteria andWithdrawTotalMonthIn(List<BigDecimal> values) {
            addCriterion("withdraw_total_month in", values, "withdrawTotalMonth");
            return (Criteria) this;
        }

        public Criteria andWithdrawTotalMonthNotIn(List<BigDecimal> values) {
            addCriterion("withdraw_total_month not in", values, "withdrawTotalMonth");
            return (Criteria) this;
        }

        public Criteria andWithdrawTotalMonthBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("withdraw_total_month between", value1, value2, "withdrawTotalMonth");
            return (Criteria) this;
        }

        public Criteria andWithdrawTotalMonthNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("withdraw_total_month not between", value1, value2, "withdrawTotalMonth");
            return (Criteria) this;
        }

        public Criteria andWithdrawRateIsNull() {
            addCriterion("withdraw_rate is null");
            return (Criteria) this;
        }

        public Criteria andWithdrawRateIsNotNull() {
            addCriterion("withdraw_rate is not null");
            return (Criteria) this;
        }

        public Criteria andWithdrawRateEqualTo(String value) {
            addCriterion("withdraw_rate =", value, "withdrawRate");
            return (Criteria) this;
        }

        public Criteria andWithdrawRateNotEqualTo(String value) {
            addCriterion("withdraw_rate <>", value, "withdrawRate");
            return (Criteria) this;
        }

        public Criteria andWithdrawRateGreaterThan(String value) {
            addCriterion("withdraw_rate >", value, "withdrawRate");
            return (Criteria) this;
        }

        public Criteria andWithdrawRateGreaterThanOrEqualTo(String value) {
            addCriterion("withdraw_rate >=", value, "withdrawRate");
            return (Criteria) this;
        }

        public Criteria andWithdrawRateLessThan(String value) {
            addCriterion("withdraw_rate <", value, "withdrawRate");
            return (Criteria) this;
        }

        public Criteria andWithdrawRateLessThanOrEqualTo(String value) {
            addCriterion("withdraw_rate <=", value, "withdrawRate");
            return (Criteria) this;
        }

        public Criteria andWithdrawRateLike(String value) {
            addCriterion("withdraw_rate like", value, "withdrawRate");
            return (Criteria) this;
        }

        public Criteria andWithdrawRateNotLike(String value) {
            addCriterion("withdraw_rate not like", value, "withdrawRate");
            return (Criteria) this;
        }

        public Criteria andWithdrawRateIn(List<String> values) {
            addCriterion("withdraw_rate in", values, "withdrawRate");
            return (Criteria) this;
        }

        public Criteria andWithdrawRateNotIn(List<String> values) {
            addCriterion("withdraw_rate not in", values, "withdrawRate");
            return (Criteria) this;
        }

        public Criteria andWithdrawRateBetween(String value1, String value2) {
            addCriterion("withdraw_rate between", value1, value2, "withdrawRate");
            return (Criteria) this;
        }

        public Criteria andWithdrawRateNotBetween(String value1, String value2) {
            addCriterion("withdraw_rate not between", value1, value2, "withdrawRate");
            return (Criteria) this;
        }

        public Criteria andRechargeTotalMonthIsNull() {
            addCriterion("recharge_total_month is null");
            return (Criteria) this;
        }

        public Criteria andRechargeTotalMonthIsNotNull() {
            addCriterion("recharge_total_month is not null");
            return (Criteria) this;
        }

        public Criteria andRechargeTotalMonthEqualTo(BigDecimal value) {
            addCriterion("recharge_total_month =", value, "rechargeTotalMonth");
            return (Criteria) this;
        }

        public Criteria andRechargeTotalMonthNotEqualTo(BigDecimal value) {
            addCriterion("recharge_total_month <>", value, "rechargeTotalMonth");
            return (Criteria) this;
        }

        public Criteria andRechargeTotalMonthGreaterThan(BigDecimal value) {
            addCriterion("recharge_total_month >", value, "rechargeTotalMonth");
            return (Criteria) this;
        }

        public Criteria andRechargeTotalMonthGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("recharge_total_month >=", value, "rechargeTotalMonth");
            return (Criteria) this;
        }

        public Criteria andRechargeTotalMonthLessThan(BigDecimal value) {
            addCriterion("recharge_total_month <", value, "rechargeTotalMonth");
            return (Criteria) this;
        }

        public Criteria andRechargeTotalMonthLessThanOrEqualTo(BigDecimal value) {
            addCriterion("recharge_total_month <=", value, "rechargeTotalMonth");
            return (Criteria) this;
        }

        public Criteria andRechargeTotalMonthIn(List<BigDecimal> values) {
            addCriterion("recharge_total_month in", values, "rechargeTotalMonth");
            return (Criteria) this;
        }

        public Criteria andRechargeTotalMonthNotIn(List<BigDecimal> values) {
            addCriterion("recharge_total_month not in", values, "rechargeTotalMonth");
            return (Criteria) this;
        }

        public Criteria andRechargeTotalMonthBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("recharge_total_month between", value1, value2, "rechargeTotalMonth");
            return (Criteria) this;
        }

        public Criteria andRechargeTotalMonthNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("recharge_total_month not between", value1, value2, "rechargeTotalMonth");
            return (Criteria) this;
        }

        public Criteria andInvestAnnualTotalMonthIsNull() {
            addCriterion("invest_annual_total_month is null");
            return (Criteria) this;
        }

        public Criteria andInvestAnnualTotalMonthIsNotNull() {
            addCriterion("invest_annual_total_month is not null");
            return (Criteria) this;
        }

        public Criteria andInvestAnnualTotalMonthEqualTo(BigDecimal value) {
            addCriterion("invest_annual_total_month =", value, "investAnnualTotalMonth");
            return (Criteria) this;
        }

        public Criteria andInvestAnnualTotalMonthNotEqualTo(BigDecimal value) {
            addCriterion("invest_annual_total_month <>", value, "investAnnualTotalMonth");
            return (Criteria) this;
        }

        public Criteria andInvestAnnualTotalMonthGreaterThan(BigDecimal value) {
            addCriterion("invest_annual_total_month >", value, "investAnnualTotalMonth");
            return (Criteria) this;
        }

        public Criteria andInvestAnnualTotalMonthGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("invest_annual_total_month >=", value, "investAnnualTotalMonth");
            return (Criteria) this;
        }

        public Criteria andInvestAnnualTotalMonthLessThan(BigDecimal value) {
            addCriterion("invest_annual_total_month <", value, "investAnnualTotalMonth");
            return (Criteria) this;
        }

        public Criteria andInvestAnnualTotalMonthLessThanOrEqualTo(BigDecimal value) {
            addCriterion("invest_annual_total_month <=", value, "investAnnualTotalMonth");
            return (Criteria) this;
        }

        public Criteria andInvestAnnualTotalMonthIn(List<BigDecimal> values) {
            addCriterion("invest_annual_total_month in", values, "investAnnualTotalMonth");
            return (Criteria) this;
        }

        public Criteria andInvestAnnualTotalMonthNotIn(List<BigDecimal> values) {
            addCriterion("invest_annual_total_month not in", values, "investAnnualTotalMonth");
            return (Criteria) this;
        }

        public Criteria andInvestAnnualTotalMonthBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("invest_annual_total_month between", value1, value2, "investAnnualTotalMonth");
            return (Criteria) this;
        }

        public Criteria andInvestAnnualTotalMonthNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("invest_annual_total_month not between", value1, value2, "investAnnualTotalMonth");
            return (Criteria) this;
        }

        public Criteria andInvestAnnualTotalPreviousMonthIsNull() {
            addCriterion("invest_annual_total_previous_month is null");
            return (Criteria) this;
        }

        public Criteria andInvestAnnualTotalPreviousMonthIsNotNull() {
            addCriterion("invest_annual_total_previous_month is not null");
            return (Criteria) this;
        }

        public Criteria andInvestAnnualTotalPreviousMonthEqualTo(BigDecimal value) {
            addCriterion("invest_annual_total_previous_month =", value, "investAnnualTotalPreviousMonth");
            return (Criteria) this;
        }

        public Criteria andInvestAnnualTotalPreviousMonthNotEqualTo(BigDecimal value) {
            addCriterion("invest_annual_total_previous_month <>", value, "investAnnualTotalPreviousMonth");
            return (Criteria) this;
        }

        public Criteria andInvestAnnualTotalPreviousMonthGreaterThan(BigDecimal value) {
            addCriterion("invest_annual_total_previous_month >", value, "investAnnualTotalPreviousMonth");
            return (Criteria) this;
        }

        public Criteria andInvestAnnualTotalPreviousMonthGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("invest_annual_total_previous_month >=", value, "investAnnualTotalPreviousMonth");
            return (Criteria) this;
        }

        public Criteria andInvestAnnualTotalPreviousMonthLessThan(BigDecimal value) {
            addCriterion("invest_annual_total_previous_month <", value, "investAnnualTotalPreviousMonth");
            return (Criteria) this;
        }

        public Criteria andInvestAnnualTotalPreviousMonthLessThanOrEqualTo(BigDecimal value) {
            addCriterion("invest_annual_total_previous_month <=", value, "investAnnualTotalPreviousMonth");
            return (Criteria) this;
        }

        public Criteria andInvestAnnualTotalPreviousMonthIn(List<BigDecimal> values) {
            addCriterion("invest_annual_total_previous_month in", values, "investAnnualTotalPreviousMonth");
            return (Criteria) this;
        }

        public Criteria andInvestAnnualTotalPreviousMonthNotIn(List<BigDecimal> values) {
            addCriterion("invest_annual_total_previous_month not in", values, "investAnnualTotalPreviousMonth");
            return (Criteria) this;
        }

        public Criteria andInvestAnnualTotalPreviousMonthBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("invest_annual_total_previous_month between", value1, value2, "investAnnualTotalPreviousMonth");
            return (Criteria) this;
        }

        public Criteria andInvestAnnualTotalPreviousMonthNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("invest_annual_total_previous_month not between", value1, value2, "investAnnualTotalPreviousMonth");
            return (Criteria) this;
        }

        public Criteria andInvestAnnularRatioGrowthIsNull() {
            addCriterion("invest_annular_ratio_growth is null");
            return (Criteria) this;
        }

        public Criteria andInvestAnnularRatioGrowthIsNotNull() {
            addCriterion("invest_annular_ratio_growth is not null");
            return (Criteria) this;
        }

        public Criteria andInvestAnnularRatioGrowthEqualTo(String value) {
            addCriterion("invest_annular_ratio_growth =", value, "investAnnularRatioGrowth");
            return (Criteria) this;
        }

        public Criteria andInvestAnnularRatioGrowthNotEqualTo(String value) {
            addCriterion("invest_annular_ratio_growth <>", value, "investAnnularRatioGrowth");
            return (Criteria) this;
        }

        public Criteria andInvestAnnularRatioGrowthGreaterThan(String value) {
            addCriterion("invest_annular_ratio_growth >", value, "investAnnularRatioGrowth");
            return (Criteria) this;
        }

        public Criteria andInvestAnnularRatioGrowthGreaterThanOrEqualTo(String value) {
            addCriterion("invest_annular_ratio_growth >=", value, "investAnnularRatioGrowth");
            return (Criteria) this;
        }

        public Criteria andInvestAnnularRatioGrowthLessThan(String value) {
            addCriterion("invest_annular_ratio_growth <", value, "investAnnularRatioGrowth");
            return (Criteria) this;
        }

        public Criteria andInvestAnnularRatioGrowthLessThanOrEqualTo(String value) {
            addCriterion("invest_annular_ratio_growth <=", value, "investAnnularRatioGrowth");
            return (Criteria) this;
        }

        public Criteria andInvestAnnularRatioGrowthLike(String value) {
            addCriterion("invest_annular_ratio_growth like", value, "investAnnularRatioGrowth");
            return (Criteria) this;
        }

        public Criteria andInvestAnnularRatioGrowthNotLike(String value) {
            addCriterion("invest_annular_ratio_growth not like", value, "investAnnularRatioGrowth");
            return (Criteria) this;
        }

        public Criteria andInvestAnnularRatioGrowthIn(List<String> values) {
            addCriterion("invest_annular_ratio_growth in", values, "investAnnularRatioGrowth");
            return (Criteria) this;
        }

        public Criteria andInvestAnnularRatioGrowthNotIn(List<String> values) {
            addCriterion("invest_annular_ratio_growth not in", values, "investAnnularRatioGrowth");
            return (Criteria) this;
        }

        public Criteria andInvestAnnularRatioGrowthBetween(String value1, String value2) {
            addCriterion("invest_annular_ratio_growth between", value1, value2, "investAnnularRatioGrowth");
            return (Criteria) this;
        }

        public Criteria andInvestAnnularRatioGrowthNotBetween(String value1, String value2) {
            addCriterion("invest_annular_ratio_growth not between", value1, value2, "investAnnularRatioGrowth");
            return (Criteria) this;
        }

        public Criteria andInvestTotalYesterdayIsNull() {
            addCriterion("invest_total_yesterday is null");
            return (Criteria) this;
        }

        public Criteria andInvestTotalYesterdayIsNotNull() {
            addCriterion("invest_total_yesterday is not null");
            return (Criteria) this;
        }

        public Criteria andInvestTotalYesterdayEqualTo(BigDecimal value) {
            addCriterion("invest_total_yesterday =", value, "investTotalYesterday");
            return (Criteria) this;
        }

        public Criteria andInvestTotalYesterdayNotEqualTo(BigDecimal value) {
            addCriterion("invest_total_yesterday <>", value, "investTotalYesterday");
            return (Criteria) this;
        }

        public Criteria andInvestTotalYesterdayGreaterThan(BigDecimal value) {
            addCriterion("invest_total_yesterday >", value, "investTotalYesterday");
            return (Criteria) this;
        }

        public Criteria andInvestTotalYesterdayGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("invest_total_yesterday >=", value, "investTotalYesterday");
            return (Criteria) this;
        }

        public Criteria andInvestTotalYesterdayLessThan(BigDecimal value) {
            addCriterion("invest_total_yesterday <", value, "investTotalYesterday");
            return (Criteria) this;
        }

        public Criteria andInvestTotalYesterdayLessThanOrEqualTo(BigDecimal value) {
            addCriterion("invest_total_yesterday <=", value, "investTotalYesterday");
            return (Criteria) this;
        }

        public Criteria andInvestTotalYesterdayIn(List<BigDecimal> values) {
            addCriterion("invest_total_yesterday in", values, "investTotalYesterday");
            return (Criteria) this;
        }

        public Criteria andInvestTotalYesterdayNotIn(List<BigDecimal> values) {
            addCriterion("invest_total_yesterday not in", values, "investTotalYesterday");
            return (Criteria) this;
        }

        public Criteria andInvestTotalYesterdayBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("invest_total_yesterday between", value1, value2, "investTotalYesterday");
            return (Criteria) this;
        }

        public Criteria andInvestTotalYesterdayNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("invest_total_yesterday not between", value1, value2, "investTotalYesterday");
            return (Criteria) this;
        }

        public Criteria andRepaymentTotalYesterdayIsNull() {
            addCriterion("repayment_total_yesterday is null");
            return (Criteria) this;
        }

        public Criteria andRepaymentTotalYesterdayIsNotNull() {
            addCriterion("repayment_total_yesterday is not null");
            return (Criteria) this;
        }

        public Criteria andRepaymentTotalYesterdayEqualTo(BigDecimal value) {
            addCriterion("repayment_total_yesterday =", value, "repaymentTotalYesterday");
            return (Criteria) this;
        }

        public Criteria andRepaymentTotalYesterdayNotEqualTo(BigDecimal value) {
            addCriterion("repayment_total_yesterday <>", value, "repaymentTotalYesterday");
            return (Criteria) this;
        }

        public Criteria andRepaymentTotalYesterdayGreaterThan(BigDecimal value) {
            addCriterion("repayment_total_yesterday >", value, "repaymentTotalYesterday");
            return (Criteria) this;
        }

        public Criteria andRepaymentTotalYesterdayGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("repayment_total_yesterday >=", value, "repaymentTotalYesterday");
            return (Criteria) this;
        }

        public Criteria andRepaymentTotalYesterdayLessThan(BigDecimal value) {
            addCriterion("repayment_total_yesterday <", value, "repaymentTotalYesterday");
            return (Criteria) this;
        }

        public Criteria andRepaymentTotalYesterdayLessThanOrEqualTo(BigDecimal value) {
            addCriterion("repayment_total_yesterday <=", value, "repaymentTotalYesterday");
            return (Criteria) this;
        }

        public Criteria andRepaymentTotalYesterdayIn(List<BigDecimal> values) {
            addCriterion("repayment_total_yesterday in", values, "repaymentTotalYesterday");
            return (Criteria) this;
        }

        public Criteria andRepaymentTotalYesterdayNotIn(List<BigDecimal> values) {
            addCriterion("repayment_total_yesterday not in", values, "repaymentTotalYesterday");
            return (Criteria) this;
        }

        public Criteria andRepaymentTotalYesterdayBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repayment_total_yesterday between", value1, value2, "repaymentTotalYesterday");
            return (Criteria) this;
        }

        public Criteria andRepaymentTotalYesterdayNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repayment_total_yesterday not between", value1, value2, "repaymentTotalYesterday");
            return (Criteria) this;
        }

        public Criteria andInvestAnnualTotalYesterdayIsNull() {
            addCriterion("invest_annual_total_yesterday is null");
            return (Criteria) this;
        }

        public Criteria andInvestAnnualTotalYesterdayIsNotNull() {
            addCriterion("invest_annual_total_yesterday is not null");
            return (Criteria) this;
        }

        public Criteria andInvestAnnualTotalYesterdayEqualTo(BigDecimal value) {
            addCriterion("invest_annual_total_yesterday =", value, "investAnnualTotalYesterday");
            return (Criteria) this;
        }

        public Criteria andInvestAnnualTotalYesterdayNotEqualTo(BigDecimal value) {
            addCriterion("invest_annual_total_yesterday <>", value, "investAnnualTotalYesterday");
            return (Criteria) this;
        }

        public Criteria andInvestAnnualTotalYesterdayGreaterThan(BigDecimal value) {
            addCriterion("invest_annual_total_yesterday >", value, "investAnnualTotalYesterday");
            return (Criteria) this;
        }

        public Criteria andInvestAnnualTotalYesterdayGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("invest_annual_total_yesterday >=", value, "investAnnualTotalYesterday");
            return (Criteria) this;
        }

        public Criteria andInvestAnnualTotalYesterdayLessThan(BigDecimal value) {
            addCriterion("invest_annual_total_yesterday <", value, "investAnnualTotalYesterday");
            return (Criteria) this;
        }

        public Criteria andInvestAnnualTotalYesterdayLessThanOrEqualTo(BigDecimal value) {
            addCriterion("invest_annual_total_yesterday <=", value, "investAnnualTotalYesterday");
            return (Criteria) this;
        }

        public Criteria andInvestAnnualTotalYesterdayIn(List<BigDecimal> values) {
            addCriterion("invest_annual_total_yesterday in", values, "investAnnualTotalYesterday");
            return (Criteria) this;
        }

        public Criteria andInvestAnnualTotalYesterdayNotIn(List<BigDecimal> values) {
            addCriterion("invest_annual_total_yesterday not in", values, "investAnnualTotalYesterday");
            return (Criteria) this;
        }

        public Criteria andInvestAnnualTotalYesterdayBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("invest_annual_total_yesterday between", value1, value2, "investAnnualTotalYesterday");
            return (Criteria) this;
        }

        public Criteria andInvestAnnualTotalYesterdayNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("invest_annual_total_yesterday not between", value1, value2, "investAnnualTotalYesterday");
            return (Criteria) this;
        }

        public Criteria andWithdrawTotalYesterdayIsNull() {
            addCriterion("withdraw_total_yesterday is null");
            return (Criteria) this;
        }

        public Criteria andWithdrawTotalYesterdayIsNotNull() {
            addCriterion("withdraw_total_yesterday is not null");
            return (Criteria) this;
        }

        public Criteria andWithdrawTotalYesterdayEqualTo(BigDecimal value) {
            addCriterion("withdraw_total_yesterday =", value, "withdrawTotalYesterday");
            return (Criteria) this;
        }

        public Criteria andWithdrawTotalYesterdayNotEqualTo(BigDecimal value) {
            addCriterion("withdraw_total_yesterday <>", value, "withdrawTotalYesterday");
            return (Criteria) this;
        }

        public Criteria andWithdrawTotalYesterdayGreaterThan(BigDecimal value) {
            addCriterion("withdraw_total_yesterday >", value, "withdrawTotalYesterday");
            return (Criteria) this;
        }

        public Criteria andWithdrawTotalYesterdayGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("withdraw_total_yesterday >=", value, "withdrawTotalYesterday");
            return (Criteria) this;
        }

        public Criteria andWithdrawTotalYesterdayLessThan(BigDecimal value) {
            addCriterion("withdraw_total_yesterday <", value, "withdrawTotalYesterday");
            return (Criteria) this;
        }

        public Criteria andWithdrawTotalYesterdayLessThanOrEqualTo(BigDecimal value) {
            addCriterion("withdraw_total_yesterday <=", value, "withdrawTotalYesterday");
            return (Criteria) this;
        }

        public Criteria andWithdrawTotalYesterdayIn(List<BigDecimal> values) {
            addCriterion("withdraw_total_yesterday in", values, "withdrawTotalYesterday");
            return (Criteria) this;
        }

        public Criteria andWithdrawTotalYesterdayNotIn(List<BigDecimal> values) {
            addCriterion("withdraw_total_yesterday not in", values, "withdrawTotalYesterday");
            return (Criteria) this;
        }

        public Criteria andWithdrawTotalYesterdayBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("withdraw_total_yesterday between", value1, value2, "withdrawTotalYesterday");
            return (Criteria) this;
        }

        public Criteria andWithdrawTotalYesterdayNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("withdraw_total_yesterday not between", value1, value2, "withdrawTotalYesterday");
            return (Criteria) this;
        }

        public Criteria andRechargeTotalYesterdayIsNull() {
            addCriterion("recharge_total_yesterday is null");
            return (Criteria) this;
        }

        public Criteria andRechargeTotalYesterdayIsNotNull() {
            addCriterion("recharge_total_yesterday is not null");
            return (Criteria) this;
        }

        public Criteria andRechargeTotalYesterdayEqualTo(BigDecimal value) {
            addCriterion("recharge_total_yesterday =", value, "rechargeTotalYesterday");
            return (Criteria) this;
        }

        public Criteria andRechargeTotalYesterdayNotEqualTo(BigDecimal value) {
            addCriterion("recharge_total_yesterday <>", value, "rechargeTotalYesterday");
            return (Criteria) this;
        }

        public Criteria andRechargeTotalYesterdayGreaterThan(BigDecimal value) {
            addCriterion("recharge_total_yesterday >", value, "rechargeTotalYesterday");
            return (Criteria) this;
        }

        public Criteria andRechargeTotalYesterdayGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("recharge_total_yesterday >=", value, "rechargeTotalYesterday");
            return (Criteria) this;
        }

        public Criteria andRechargeTotalYesterdayLessThan(BigDecimal value) {
            addCriterion("recharge_total_yesterday <", value, "rechargeTotalYesterday");
            return (Criteria) this;
        }

        public Criteria andRechargeTotalYesterdayLessThanOrEqualTo(BigDecimal value) {
            addCriterion("recharge_total_yesterday <=", value, "rechargeTotalYesterday");
            return (Criteria) this;
        }

        public Criteria andRechargeTotalYesterdayIn(List<BigDecimal> values) {
            addCriterion("recharge_total_yesterday in", values, "rechargeTotalYesterday");
            return (Criteria) this;
        }

        public Criteria andRechargeTotalYesterdayNotIn(List<BigDecimal> values) {
            addCriterion("recharge_total_yesterday not in", values, "rechargeTotalYesterday");
            return (Criteria) this;
        }

        public Criteria andRechargeTotalYesterdayBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("recharge_total_yesterday between", value1, value2, "rechargeTotalYesterday");
            return (Criteria) this;
        }

        public Criteria andRechargeTotalYesterdayNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("recharge_total_yesterday not between", value1, value2, "rechargeTotalYesterday");
            return (Criteria) this;
        }

        public Criteria andNetCapitalInflowYesterdayIsNull() {
            addCriterion("net_capital_inflow_yesterday is null");
            return (Criteria) this;
        }

        public Criteria andNetCapitalInflowYesterdayIsNotNull() {
            addCriterion("net_capital_inflow_yesterday is not null");
            return (Criteria) this;
        }

        public Criteria andNetCapitalInflowYesterdayEqualTo(BigDecimal value) {
            addCriterion("net_capital_inflow_yesterday =", value, "netCapitalInflowYesterday");
            return (Criteria) this;
        }

        public Criteria andNetCapitalInflowYesterdayNotEqualTo(BigDecimal value) {
            addCriterion("net_capital_inflow_yesterday <>", value, "netCapitalInflowYesterday");
            return (Criteria) this;
        }

        public Criteria andNetCapitalInflowYesterdayGreaterThan(BigDecimal value) {
            addCriterion("net_capital_inflow_yesterday >", value, "netCapitalInflowYesterday");
            return (Criteria) this;
        }

        public Criteria andNetCapitalInflowYesterdayGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("net_capital_inflow_yesterday >=", value, "netCapitalInflowYesterday");
            return (Criteria) this;
        }

        public Criteria andNetCapitalInflowYesterdayLessThan(BigDecimal value) {
            addCriterion("net_capital_inflow_yesterday <", value, "netCapitalInflowYesterday");
            return (Criteria) this;
        }

        public Criteria andNetCapitalInflowYesterdayLessThanOrEqualTo(BigDecimal value) {
            addCriterion("net_capital_inflow_yesterday <=", value, "netCapitalInflowYesterday");
            return (Criteria) this;
        }

        public Criteria andNetCapitalInflowYesterdayIn(List<BigDecimal> values) {
            addCriterion("net_capital_inflow_yesterday in", values, "netCapitalInflowYesterday");
            return (Criteria) this;
        }

        public Criteria andNetCapitalInflowYesterdayNotIn(List<BigDecimal> values) {
            addCriterion("net_capital_inflow_yesterday not in", values, "netCapitalInflowYesterday");
            return (Criteria) this;
        }

        public Criteria andNetCapitalInflowYesterdayBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("net_capital_inflow_yesterday between", value1, value2, "netCapitalInflowYesterday");
            return (Criteria) this;
        }

        public Criteria andNetCapitalInflowYesterdayNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("net_capital_inflow_yesterday not between", value1, value2, "netCapitalInflowYesterday");
            return (Criteria) this;
        }

        public Criteria andNonRepaymentTodayIsNull() {
            addCriterion("non_repayment_today is null");
            return (Criteria) this;
        }

        public Criteria andNonRepaymentTodayIsNotNull() {
            addCriterion("non_repayment_today is not null");
            return (Criteria) this;
        }

        public Criteria andNonRepaymentTodayEqualTo(BigDecimal value) {
            addCriterion("non_repayment_today =", value, "nonRepaymentToday");
            return (Criteria) this;
        }

        public Criteria andNonRepaymentTodayNotEqualTo(BigDecimal value) {
            addCriterion("non_repayment_today <>", value, "nonRepaymentToday");
            return (Criteria) this;
        }

        public Criteria andNonRepaymentTodayGreaterThan(BigDecimal value) {
            addCriterion("non_repayment_today >", value, "nonRepaymentToday");
            return (Criteria) this;
        }

        public Criteria andNonRepaymentTodayGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("non_repayment_today >=", value, "nonRepaymentToday");
            return (Criteria) this;
        }

        public Criteria andNonRepaymentTodayLessThan(BigDecimal value) {
            addCriterion("non_repayment_today <", value, "nonRepaymentToday");
            return (Criteria) this;
        }

        public Criteria andNonRepaymentTodayLessThanOrEqualTo(BigDecimal value) {
            addCriterion("non_repayment_today <=", value, "nonRepaymentToday");
            return (Criteria) this;
        }

        public Criteria andNonRepaymentTodayIn(List<BigDecimal> values) {
            addCriterion("non_repayment_today in", values, "nonRepaymentToday");
            return (Criteria) this;
        }

        public Criteria andNonRepaymentTodayNotIn(List<BigDecimal> values) {
            addCriterion("non_repayment_today not in", values, "nonRepaymentToday");
            return (Criteria) this;
        }

        public Criteria andNonRepaymentTodayBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("non_repayment_today between", value1, value2, "nonRepaymentToday");
            return (Criteria) this;
        }

        public Criteria andNonRepaymentTodayNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("non_repayment_today not between", value1, value2, "nonRepaymentToday");
            return (Criteria) this;
        }

        public Criteria andRegisterTotalYesterdayIsNull() {
            addCriterion("register_total_yesterday is null");
            return (Criteria) this;
        }

        public Criteria andRegisterTotalYesterdayIsNotNull() {
            addCriterion("register_total_yesterday is not null");
            return (Criteria) this;
        }

        public Criteria andRegisterTotalYesterdayEqualTo(Integer value) {
            addCriterion("register_total_yesterday =", value, "registerTotalYesterday");
            return (Criteria) this;
        }

        public Criteria andRegisterTotalYesterdayNotEqualTo(Integer value) {
            addCriterion("register_total_yesterday <>", value, "registerTotalYesterday");
            return (Criteria) this;
        }

        public Criteria andRegisterTotalYesterdayGreaterThan(Integer value) {
            addCriterion("register_total_yesterday >", value, "registerTotalYesterday");
            return (Criteria) this;
        }

        public Criteria andRegisterTotalYesterdayGreaterThanOrEqualTo(Integer value) {
            addCriterion("register_total_yesterday >=", value, "registerTotalYesterday");
            return (Criteria) this;
        }

        public Criteria andRegisterTotalYesterdayLessThan(Integer value) {
            addCriterion("register_total_yesterday <", value, "registerTotalYesterday");
            return (Criteria) this;
        }

        public Criteria andRegisterTotalYesterdayLessThanOrEqualTo(Integer value) {
            addCriterion("register_total_yesterday <=", value, "registerTotalYesterday");
            return (Criteria) this;
        }

        public Criteria andRegisterTotalYesterdayIn(List<Integer> values) {
            addCriterion("register_total_yesterday in", values, "registerTotalYesterday");
            return (Criteria) this;
        }

        public Criteria andRegisterTotalYesterdayNotIn(List<Integer> values) {
            addCriterion("register_total_yesterday not in", values, "registerTotalYesterday");
            return (Criteria) this;
        }

        public Criteria andRegisterTotalYesterdayBetween(Integer value1, Integer value2) {
            addCriterion("register_total_yesterday between", value1, value2, "registerTotalYesterday");
            return (Criteria) this;
        }

        public Criteria andRegisterTotalYesterdayNotBetween(Integer value1, Integer value2) {
            addCriterion("register_total_yesterday not between", value1, value2, "registerTotalYesterday");
            return (Criteria) this;
        }

        public Criteria andRechargeGt3000UserNumIsNull() {
            addCriterion("recharge_gt3000_user_num is null");
            return (Criteria) this;
        }

        public Criteria andRechargeGt3000UserNumIsNotNull() {
            addCriterion("recharge_gt3000_user_num is not null");
            return (Criteria) this;
        }

        public Criteria andRechargeGt3000UserNumEqualTo(Integer value) {
            addCriterion("recharge_gt3000_user_num =", value, "rechargeGt3000UserNum");
            return (Criteria) this;
        }

        public Criteria andRechargeGt3000UserNumNotEqualTo(Integer value) {
            addCriterion("recharge_gt3000_user_num <>", value, "rechargeGt3000UserNum");
            return (Criteria) this;
        }

        public Criteria andRechargeGt3000UserNumGreaterThan(Integer value) {
            addCriterion("recharge_gt3000_user_num >", value, "rechargeGt3000UserNum");
            return (Criteria) this;
        }

        public Criteria andRechargeGt3000UserNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("recharge_gt3000_user_num >=", value, "rechargeGt3000UserNum");
            return (Criteria) this;
        }

        public Criteria andRechargeGt3000UserNumLessThan(Integer value) {
            addCriterion("recharge_gt3000_user_num <", value, "rechargeGt3000UserNum");
            return (Criteria) this;
        }

        public Criteria andRechargeGt3000UserNumLessThanOrEqualTo(Integer value) {
            addCriterion("recharge_gt3000_user_num <=", value, "rechargeGt3000UserNum");
            return (Criteria) this;
        }

        public Criteria andRechargeGt3000UserNumIn(List<Integer> values) {
            addCriterion("recharge_gt3000_user_num in", values, "rechargeGt3000UserNum");
            return (Criteria) this;
        }

        public Criteria andRechargeGt3000UserNumNotIn(List<Integer> values) {
            addCriterion("recharge_gt3000_user_num not in", values, "rechargeGt3000UserNum");
            return (Criteria) this;
        }

        public Criteria andRechargeGt3000UserNumBetween(Integer value1, Integer value2) {
            addCriterion("recharge_gt3000_user_num between", value1, value2, "rechargeGt3000UserNum");
            return (Criteria) this;
        }

        public Criteria andRechargeGt3000UserNumNotBetween(Integer value1, Integer value2) {
            addCriterion("recharge_gt3000_user_num not between", value1, value2, "rechargeGt3000UserNum");
            return (Criteria) this;
        }

        public Criteria andInvestGt3000UserNumIsNull() {
            addCriterion("invest_gt3000_user_num is null");
            return (Criteria) this;
        }

        public Criteria andInvestGt3000UserNumIsNotNull() {
            addCriterion("invest_gt3000_user_num is not null");
            return (Criteria) this;
        }

        public Criteria andInvestGt3000UserNumEqualTo(Integer value) {
            addCriterion("invest_gt3000_user_num =", value, "investGt3000UserNum");
            return (Criteria) this;
        }

        public Criteria andInvestGt3000UserNumNotEqualTo(Integer value) {
            addCriterion("invest_gt3000_user_num <>", value, "investGt3000UserNum");
            return (Criteria) this;
        }

        public Criteria andInvestGt3000UserNumGreaterThan(Integer value) {
            addCriterion("invest_gt3000_user_num >", value, "investGt3000UserNum");
            return (Criteria) this;
        }

        public Criteria andInvestGt3000UserNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("invest_gt3000_user_num >=", value, "investGt3000UserNum");
            return (Criteria) this;
        }

        public Criteria andInvestGt3000UserNumLessThan(Integer value) {
            addCriterion("invest_gt3000_user_num <", value, "investGt3000UserNum");
            return (Criteria) this;
        }

        public Criteria andInvestGt3000UserNumLessThanOrEqualTo(Integer value) {
            addCriterion("invest_gt3000_user_num <=", value, "investGt3000UserNum");
            return (Criteria) this;
        }

        public Criteria andInvestGt3000UserNumIn(List<Integer> values) {
            addCriterion("invest_gt3000_user_num in", values, "investGt3000UserNum");
            return (Criteria) this;
        }

        public Criteria andInvestGt3000UserNumNotIn(List<Integer> values) {
            addCriterion("invest_gt3000_user_num not in", values, "investGt3000UserNum");
            return (Criteria) this;
        }

        public Criteria andInvestGt3000UserNumBetween(Integer value1, Integer value2) {
            addCriterion("invest_gt3000_user_num between", value1, value2, "investGt3000UserNum");
            return (Criteria) this;
        }

        public Criteria andInvestGt3000UserNumNotBetween(Integer value1, Integer value2) {
            addCriterion("invest_gt3000_user_num not between", value1, value2, "investGt3000UserNum");
            return (Criteria) this;
        }

        public Criteria andInvestGt3000MonthUserNumIsNull() {
            addCriterion("invest_gt3000_month_user_num is null");
            return (Criteria) this;
        }

        public Criteria andInvestGt3000MonthUserNumIsNotNull() {
            addCriterion("invest_gt3000_month_user_num is not null");
            return (Criteria) this;
        }

        public Criteria andInvestGt3000MonthUserNumEqualTo(Integer value) {
            addCriterion("invest_gt3000_month_user_num =", value, "investGt3000MonthUserNum");
            return (Criteria) this;
        }

        public Criteria andInvestGt3000MonthUserNumNotEqualTo(Integer value) {
            addCriterion("invest_gt3000_month_user_num <>", value, "investGt3000MonthUserNum");
            return (Criteria) this;
        }

        public Criteria andInvestGt3000MonthUserNumGreaterThan(Integer value) {
            addCriterion("invest_gt3000_month_user_num >", value, "investGt3000MonthUserNum");
            return (Criteria) this;
        }

        public Criteria andInvestGt3000MonthUserNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("invest_gt3000_month_user_num >=", value, "investGt3000MonthUserNum");
            return (Criteria) this;
        }

        public Criteria andInvestGt3000MonthUserNumLessThan(Integer value) {
            addCriterion("invest_gt3000_month_user_num <", value, "investGt3000MonthUserNum");
            return (Criteria) this;
        }

        public Criteria andInvestGt3000MonthUserNumLessThanOrEqualTo(Integer value) {
            addCriterion("invest_gt3000_month_user_num <=", value, "investGt3000MonthUserNum");
            return (Criteria) this;
        }

        public Criteria andInvestGt3000MonthUserNumIn(List<Integer> values) {
            addCriterion("invest_gt3000_month_user_num in", values, "investGt3000MonthUserNum");
            return (Criteria) this;
        }

        public Criteria andInvestGt3000MonthUserNumNotIn(List<Integer> values) {
            addCriterion("invest_gt3000_month_user_num not in", values, "investGt3000MonthUserNum");
            return (Criteria) this;
        }

        public Criteria andInvestGt3000MonthUserNumBetween(Integer value1, Integer value2) {
            addCriterion("invest_gt3000_month_user_num between", value1, value2, "investGt3000MonthUserNum");
            return (Criteria) this;
        }

        public Criteria andInvestGt3000MonthUserNumNotBetween(Integer value1, Integer value2) {
            addCriterion("invest_gt3000_month_user_num not between", value1, value2, "investGt3000MonthUserNum");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}