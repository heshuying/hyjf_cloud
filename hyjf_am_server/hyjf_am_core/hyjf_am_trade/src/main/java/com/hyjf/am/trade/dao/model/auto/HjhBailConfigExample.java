package com.hyjf.am.trade.dao.model.auto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HjhBailConfigExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public HjhBailConfigExample() {
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

        public Criteria andInstCodeIsNull() {
            addCriterion("inst_code is null");
            return (Criteria) this;
        }

        public Criteria andInstCodeIsNotNull() {
            addCriterion("inst_code is not null");
            return (Criteria) this;
        }

        public Criteria andInstCodeEqualTo(String value) {
            addCriterion("inst_code =", value, "instCode");
            return (Criteria) this;
        }

        public Criteria andInstCodeNotEqualTo(String value) {
            addCriterion("inst_code <>", value, "instCode");
            return (Criteria) this;
        }

        public Criteria andInstCodeGreaterThan(String value) {
            addCriterion("inst_code >", value, "instCode");
            return (Criteria) this;
        }

        public Criteria andInstCodeGreaterThanOrEqualTo(String value) {
            addCriterion("inst_code >=", value, "instCode");
            return (Criteria) this;
        }

        public Criteria andInstCodeLessThan(String value) {
            addCriterion("inst_code <", value, "instCode");
            return (Criteria) this;
        }

        public Criteria andInstCodeLessThanOrEqualTo(String value) {
            addCriterion("inst_code <=", value, "instCode");
            return (Criteria) this;
        }

        public Criteria andInstCodeLike(String value) {
            addCriterion("inst_code like", value, "instCode");
            return (Criteria) this;
        }

        public Criteria andInstCodeNotLike(String value) {
            addCriterion("inst_code not like", value, "instCode");
            return (Criteria) this;
        }

        public Criteria andInstCodeIn(List<String> values) {
            addCriterion("inst_code in", values, "instCode");
            return (Criteria) this;
        }

        public Criteria andInstCodeNotIn(List<String> values) {
            addCriterion("inst_code not in", values, "instCode");
            return (Criteria) this;
        }

        public Criteria andInstCodeBetween(String value1, String value2) {
            addCriterion("inst_code between", value1, value2, "instCode");
            return (Criteria) this;
        }

        public Criteria andInstCodeNotBetween(String value1, String value2) {
            addCriterion("inst_code not between", value1, value2, "instCode");
            return (Criteria) this;
        }

        public Criteria andBailTatolIsNull() {
            addCriterion("bail_tatol is null");
            return (Criteria) this;
        }

        public Criteria andBailTatolIsNotNull() {
            addCriterion("bail_tatol is not null");
            return (Criteria) this;
        }

        public Criteria andBailTatolEqualTo(BigDecimal value) {
            addCriterion("bail_tatol =", value, "bailTatol");
            return (Criteria) this;
        }

        public Criteria andBailTatolNotEqualTo(BigDecimal value) {
            addCriterion("bail_tatol <>", value, "bailTatol");
            return (Criteria) this;
        }

        public Criteria andBailTatolGreaterThan(BigDecimal value) {
            addCriterion("bail_tatol >", value, "bailTatol");
            return (Criteria) this;
        }

        public Criteria andBailTatolGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("bail_tatol >=", value, "bailTatol");
            return (Criteria) this;
        }

        public Criteria andBailTatolLessThan(BigDecimal value) {
            addCriterion("bail_tatol <", value, "bailTatol");
            return (Criteria) this;
        }

        public Criteria andBailTatolLessThanOrEqualTo(BigDecimal value) {
            addCriterion("bail_tatol <=", value, "bailTatol");
            return (Criteria) this;
        }

        public Criteria andBailTatolIn(List<BigDecimal> values) {
            addCriterion("bail_tatol in", values, "bailTatol");
            return (Criteria) this;
        }

        public Criteria andBailTatolNotIn(List<BigDecimal> values) {
            addCriterion("bail_tatol not in", values, "bailTatol");
            return (Criteria) this;
        }

        public Criteria andBailTatolBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("bail_tatol between", value1, value2, "bailTatol");
            return (Criteria) this;
        }

        public Criteria andBailTatolNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("bail_tatol not between", value1, value2, "bailTatol");
            return (Criteria) this;
        }

        public Criteria andBailRateIsNull() {
            addCriterion("bail_rate is null");
            return (Criteria) this;
        }

        public Criteria andBailRateIsNotNull() {
            addCriterion("bail_rate is not null");
            return (Criteria) this;
        }

        public Criteria andBailRateEqualTo(Integer value) {
            addCriterion("bail_rate =", value, "bailRate");
            return (Criteria) this;
        }

        public Criteria andBailRateNotEqualTo(Integer value) {
            addCriterion("bail_rate <>", value, "bailRate");
            return (Criteria) this;
        }

        public Criteria andBailRateGreaterThan(Integer value) {
            addCriterion("bail_rate >", value, "bailRate");
            return (Criteria) this;
        }

        public Criteria andBailRateGreaterThanOrEqualTo(Integer value) {
            addCriterion("bail_rate >=", value, "bailRate");
            return (Criteria) this;
        }

        public Criteria andBailRateLessThan(Integer value) {
            addCriterion("bail_rate <", value, "bailRate");
            return (Criteria) this;
        }

        public Criteria andBailRateLessThanOrEqualTo(Integer value) {
            addCriterion("bail_rate <=", value, "bailRate");
            return (Criteria) this;
        }

        public Criteria andBailRateIn(List<Integer> values) {
            addCriterion("bail_rate in", values, "bailRate");
            return (Criteria) this;
        }

        public Criteria andBailRateNotIn(List<Integer> values) {
            addCriterion("bail_rate not in", values, "bailRate");
            return (Criteria) this;
        }

        public Criteria andBailRateBetween(Integer value1, Integer value2) {
            addCriterion("bail_rate between", value1, value2, "bailRate");
            return (Criteria) this;
        }

        public Criteria andBailRateNotBetween(Integer value1, Integer value2) {
            addCriterion("bail_rate not between", value1, value2, "bailRate");
            return (Criteria) this;
        }

        public Criteria andTimestartIsNull() {
            addCriterion("timeStart is null");
            return (Criteria) this;
        }

        public Criteria andTimestartIsNotNull() {
            addCriterion("timeStart is not null");
            return (Criteria) this;
        }

        public Criteria andTimestartEqualTo(String value) {
            addCriterion("timeStart =", value, "timestart");
            return (Criteria) this;
        }

        public Criteria andTimestartNotEqualTo(String value) {
            addCriterion("timeStart <>", value, "timestart");
            return (Criteria) this;
        }

        public Criteria andTimestartGreaterThan(String value) {
            addCriterion("timeStart >", value, "timestart");
            return (Criteria) this;
        }

        public Criteria andTimestartGreaterThanOrEqualTo(String value) {
            addCriterion("timeStart >=", value, "timestart");
            return (Criteria) this;
        }

        public Criteria andTimestartLessThan(String value) {
            addCriterion("timeStart <", value, "timestart");
            return (Criteria) this;
        }

        public Criteria andTimestartLessThanOrEqualTo(String value) {
            addCriterion("timeStart <=", value, "timestart");
            return (Criteria) this;
        }

        public Criteria andTimestartLike(String value) {
            addCriterion("timeStart like", value, "timestart");
            return (Criteria) this;
        }

        public Criteria andTimestartNotLike(String value) {
            addCriterion("timeStart not like", value, "timestart");
            return (Criteria) this;
        }

        public Criteria andTimestartIn(List<String> values) {
            addCriterion("timeStart in", values, "timestart");
            return (Criteria) this;
        }

        public Criteria andTimestartNotIn(List<String> values) {
            addCriterion("timeStart not in", values, "timestart");
            return (Criteria) this;
        }

        public Criteria andTimestartBetween(String value1, String value2) {
            addCriterion("timeStart between", value1, value2, "timestart");
            return (Criteria) this;
        }

        public Criteria andTimestartNotBetween(String value1, String value2) {
            addCriterion("timeStart not between", value1, value2, "timestart");
            return (Criteria) this;
        }

        public Criteria andTimeendIsNull() {
            addCriterion("timeEnd is null");
            return (Criteria) this;
        }

        public Criteria andTimeendIsNotNull() {
            addCriterion("timeEnd is not null");
            return (Criteria) this;
        }

        public Criteria andTimeendEqualTo(String value) {
            addCriterion("timeEnd =", value, "timeend");
            return (Criteria) this;
        }

        public Criteria andTimeendNotEqualTo(String value) {
            addCriterion("timeEnd <>", value, "timeend");
            return (Criteria) this;
        }

        public Criteria andTimeendGreaterThan(String value) {
            addCriterion("timeEnd >", value, "timeend");
            return (Criteria) this;
        }

        public Criteria andTimeendGreaterThanOrEqualTo(String value) {
            addCriterion("timeEnd >=", value, "timeend");
            return (Criteria) this;
        }

        public Criteria andTimeendLessThan(String value) {
            addCriterion("timeEnd <", value, "timeend");
            return (Criteria) this;
        }

        public Criteria andTimeendLessThanOrEqualTo(String value) {
            addCriterion("timeEnd <=", value, "timeend");
            return (Criteria) this;
        }

        public Criteria andTimeendLike(String value) {
            addCriterion("timeEnd like", value, "timeend");
            return (Criteria) this;
        }

        public Criteria andTimeendNotLike(String value) {
            addCriterion("timeEnd not like", value, "timeend");
            return (Criteria) this;
        }

        public Criteria andTimeendIn(List<String> values) {
            addCriterion("timeEnd in", values, "timeend");
            return (Criteria) this;
        }

        public Criteria andTimeendNotIn(List<String> values) {
            addCriterion("timeEnd not in", values, "timeend");
            return (Criteria) this;
        }

        public Criteria andTimeendBetween(String value1, String value2) {
            addCriterion("timeEnd between", value1, value2, "timeend");
            return (Criteria) this;
        }

        public Criteria andTimeendNotBetween(String value1, String value2) {
            addCriterion("timeEnd not between", value1, value2, "timeend");
            return (Criteria) this;
        }

        public Criteria andNewCreditLineIsNull() {
            addCriterion("new_credit_line is null");
            return (Criteria) this;
        }

        public Criteria andNewCreditLineIsNotNull() {
            addCriterion("new_credit_line is not null");
            return (Criteria) this;
        }

        public Criteria andNewCreditLineEqualTo(BigDecimal value) {
            addCriterion("new_credit_line =", value, "newCreditLine");
            return (Criteria) this;
        }

        public Criteria andNewCreditLineNotEqualTo(BigDecimal value) {
            addCriterion("new_credit_line <>", value, "newCreditLine");
            return (Criteria) this;
        }

        public Criteria andNewCreditLineGreaterThan(BigDecimal value) {
            addCriterion("new_credit_line >", value, "newCreditLine");
            return (Criteria) this;
        }

        public Criteria andNewCreditLineGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("new_credit_line >=", value, "newCreditLine");
            return (Criteria) this;
        }

        public Criteria andNewCreditLineLessThan(BigDecimal value) {
            addCriterion("new_credit_line <", value, "newCreditLine");
            return (Criteria) this;
        }

        public Criteria andNewCreditLineLessThanOrEqualTo(BigDecimal value) {
            addCriterion("new_credit_line <=", value, "newCreditLine");
            return (Criteria) this;
        }

        public Criteria andNewCreditLineIn(List<BigDecimal> values) {
            addCriterion("new_credit_line in", values, "newCreditLine");
            return (Criteria) this;
        }

        public Criteria andNewCreditLineNotIn(List<BigDecimal> values) {
            addCriterion("new_credit_line not in", values, "newCreditLine");
            return (Criteria) this;
        }

        public Criteria andNewCreditLineBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("new_credit_line between", value1, value2, "newCreditLine");
            return (Criteria) this;
        }

        public Criteria andNewCreditLineNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("new_credit_line not between", value1, value2, "newCreditLine");
            return (Criteria) this;
        }

        public Criteria andLoanCreditLineIsNull() {
            addCriterion("loan_credit_line is null");
            return (Criteria) this;
        }

        public Criteria andLoanCreditLineIsNotNull() {
            addCriterion("loan_credit_line is not null");
            return (Criteria) this;
        }

        public Criteria andLoanCreditLineEqualTo(BigDecimal value) {
            addCriterion("loan_credit_line =", value, "loanCreditLine");
            return (Criteria) this;
        }

        public Criteria andLoanCreditLineNotEqualTo(BigDecimal value) {
            addCriterion("loan_credit_line <>", value, "loanCreditLine");
            return (Criteria) this;
        }

        public Criteria andLoanCreditLineGreaterThan(BigDecimal value) {
            addCriterion("loan_credit_line >", value, "loanCreditLine");
            return (Criteria) this;
        }

        public Criteria andLoanCreditLineGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("loan_credit_line >=", value, "loanCreditLine");
            return (Criteria) this;
        }

        public Criteria andLoanCreditLineLessThan(BigDecimal value) {
            addCriterion("loan_credit_line <", value, "loanCreditLine");
            return (Criteria) this;
        }

        public Criteria andLoanCreditLineLessThanOrEqualTo(BigDecimal value) {
            addCriterion("loan_credit_line <=", value, "loanCreditLine");
            return (Criteria) this;
        }

        public Criteria andLoanCreditLineIn(List<BigDecimal> values) {
            addCriterion("loan_credit_line in", values, "loanCreditLine");
            return (Criteria) this;
        }

        public Criteria andLoanCreditLineNotIn(List<BigDecimal> values) {
            addCriterion("loan_credit_line not in", values, "loanCreditLine");
            return (Criteria) this;
        }

        public Criteria andLoanCreditLineBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("loan_credit_line between", value1, value2, "loanCreditLine");
            return (Criteria) this;
        }

        public Criteria andLoanCreditLineNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("loan_credit_line not between", value1, value2, "loanCreditLine");
            return (Criteria) this;
        }

        public Criteria andDayMarkLineIsNull() {
            addCriterion("day_mark_line is null");
            return (Criteria) this;
        }

        public Criteria andDayMarkLineIsNotNull() {
            addCriterion("day_mark_line is not null");
            return (Criteria) this;
        }

        public Criteria andDayMarkLineEqualTo(BigDecimal value) {
            addCriterion("day_mark_line =", value, "dayMarkLine");
            return (Criteria) this;
        }

        public Criteria andDayMarkLineNotEqualTo(BigDecimal value) {
            addCriterion("day_mark_line <>", value, "dayMarkLine");
            return (Criteria) this;
        }

        public Criteria andDayMarkLineGreaterThan(BigDecimal value) {
            addCriterion("day_mark_line >", value, "dayMarkLine");
            return (Criteria) this;
        }

        public Criteria andDayMarkLineGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("day_mark_line >=", value, "dayMarkLine");
            return (Criteria) this;
        }

        public Criteria andDayMarkLineLessThan(BigDecimal value) {
            addCriterion("day_mark_line <", value, "dayMarkLine");
            return (Criteria) this;
        }

        public Criteria andDayMarkLineLessThanOrEqualTo(BigDecimal value) {
            addCriterion("day_mark_line <=", value, "dayMarkLine");
            return (Criteria) this;
        }

        public Criteria andDayMarkLineIn(List<BigDecimal> values) {
            addCriterion("day_mark_line in", values, "dayMarkLine");
            return (Criteria) this;
        }

        public Criteria andDayMarkLineNotIn(List<BigDecimal> values) {
            addCriterion("day_mark_line not in", values, "dayMarkLine");
            return (Criteria) this;
        }

        public Criteria andDayMarkLineBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("day_mark_line between", value1, value2, "dayMarkLine");
            return (Criteria) this;
        }

        public Criteria andDayMarkLineNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("day_mark_line not between", value1, value2, "dayMarkLine");
            return (Criteria) this;
        }

        public Criteria andMonthMarkLineIsNull() {
            addCriterion("month_mark_line is null");
            return (Criteria) this;
        }

        public Criteria andMonthMarkLineIsNotNull() {
            addCriterion("month_mark_line is not null");
            return (Criteria) this;
        }

        public Criteria andMonthMarkLineEqualTo(BigDecimal value) {
            addCriterion("month_mark_line =", value, "monthMarkLine");
            return (Criteria) this;
        }

        public Criteria andMonthMarkLineNotEqualTo(BigDecimal value) {
            addCriterion("month_mark_line <>", value, "monthMarkLine");
            return (Criteria) this;
        }

        public Criteria andMonthMarkLineGreaterThan(BigDecimal value) {
            addCriterion("month_mark_line >", value, "monthMarkLine");
            return (Criteria) this;
        }

        public Criteria andMonthMarkLineGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("month_mark_line >=", value, "monthMarkLine");
            return (Criteria) this;
        }

        public Criteria andMonthMarkLineLessThan(BigDecimal value) {
            addCriterion("month_mark_line <", value, "monthMarkLine");
            return (Criteria) this;
        }

        public Criteria andMonthMarkLineLessThanOrEqualTo(BigDecimal value) {
            addCriterion("month_mark_line <=", value, "monthMarkLine");
            return (Criteria) this;
        }

        public Criteria andMonthMarkLineIn(List<BigDecimal> values) {
            addCriterion("month_mark_line in", values, "monthMarkLine");
            return (Criteria) this;
        }

        public Criteria andMonthMarkLineNotIn(List<BigDecimal> values) {
            addCriterion("month_mark_line not in", values, "monthMarkLine");
            return (Criteria) this;
        }

        public Criteria andMonthMarkLineBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("month_mark_line between", value1, value2, "monthMarkLine");
            return (Criteria) this;
        }

        public Criteria andMonthMarkLineNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("month_mark_line not between", value1, value2, "monthMarkLine");
            return (Criteria) this;
        }

        public Criteria andPushMarkLineIsNull() {
            addCriterion("push_mark_line is null");
            return (Criteria) this;
        }

        public Criteria andPushMarkLineIsNotNull() {
            addCriterion("push_mark_line is not null");
            return (Criteria) this;
        }

        public Criteria andPushMarkLineEqualTo(BigDecimal value) {
            addCriterion("push_mark_line =", value, "pushMarkLine");
            return (Criteria) this;
        }

        public Criteria andPushMarkLineNotEqualTo(BigDecimal value) {
            addCriterion("push_mark_line <>", value, "pushMarkLine");
            return (Criteria) this;
        }

        public Criteria andPushMarkLineGreaterThan(BigDecimal value) {
            addCriterion("push_mark_line >", value, "pushMarkLine");
            return (Criteria) this;
        }

        public Criteria andPushMarkLineGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("push_mark_line >=", value, "pushMarkLine");
            return (Criteria) this;
        }

        public Criteria andPushMarkLineLessThan(BigDecimal value) {
            addCriterion("push_mark_line <", value, "pushMarkLine");
            return (Criteria) this;
        }

        public Criteria andPushMarkLineLessThanOrEqualTo(BigDecimal value) {
            addCriterion("push_mark_line <=", value, "pushMarkLine");
            return (Criteria) this;
        }

        public Criteria andPushMarkLineIn(List<BigDecimal> values) {
            addCriterion("push_mark_line in", values, "pushMarkLine");
            return (Criteria) this;
        }

        public Criteria andPushMarkLineNotIn(List<BigDecimal> values) {
            addCriterion("push_mark_line not in", values, "pushMarkLine");
            return (Criteria) this;
        }

        public Criteria andPushMarkLineBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("push_mark_line between", value1, value2, "pushMarkLine");
            return (Criteria) this;
        }

        public Criteria andPushMarkLineNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("push_mark_line not between", value1, value2, "pushMarkLine");
            return (Criteria) this;
        }

        public Criteria andLoanMarkLineIsNull() {
            addCriterion("loan_mark_line is null");
            return (Criteria) this;
        }

        public Criteria andLoanMarkLineIsNotNull() {
            addCriterion("loan_mark_line is not null");
            return (Criteria) this;
        }

        public Criteria andLoanMarkLineEqualTo(BigDecimal value) {
            addCriterion("loan_mark_line =", value, "loanMarkLine");
            return (Criteria) this;
        }

        public Criteria andLoanMarkLineNotEqualTo(BigDecimal value) {
            addCriterion("loan_mark_line <>", value, "loanMarkLine");
            return (Criteria) this;
        }

        public Criteria andLoanMarkLineGreaterThan(BigDecimal value) {
            addCriterion("loan_mark_line >", value, "loanMarkLine");
            return (Criteria) this;
        }

        public Criteria andLoanMarkLineGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("loan_mark_line >=", value, "loanMarkLine");
            return (Criteria) this;
        }

        public Criteria andLoanMarkLineLessThan(BigDecimal value) {
            addCriterion("loan_mark_line <", value, "loanMarkLine");
            return (Criteria) this;
        }

        public Criteria andLoanMarkLineLessThanOrEqualTo(BigDecimal value) {
            addCriterion("loan_mark_line <=", value, "loanMarkLine");
            return (Criteria) this;
        }

        public Criteria andLoanMarkLineIn(List<BigDecimal> values) {
            addCriterion("loan_mark_line in", values, "loanMarkLine");
            return (Criteria) this;
        }

        public Criteria andLoanMarkLineNotIn(List<BigDecimal> values) {
            addCriterion("loan_mark_line not in", values, "loanMarkLine");
            return (Criteria) this;
        }

        public Criteria andLoanMarkLineBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("loan_mark_line between", value1, value2, "loanMarkLine");
            return (Criteria) this;
        }

        public Criteria andLoanMarkLineNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("loan_mark_line not between", value1, value2, "loanMarkLine");
            return (Criteria) this;
        }

        public Criteria andRemainMarkLineIsNull() {
            addCriterion("remain_mark_line is null");
            return (Criteria) this;
        }

        public Criteria andRemainMarkLineIsNotNull() {
            addCriterion("remain_mark_line is not null");
            return (Criteria) this;
        }

        public Criteria andRemainMarkLineEqualTo(BigDecimal value) {
            addCriterion("remain_mark_line =", value, "remainMarkLine");
            return (Criteria) this;
        }

        public Criteria andRemainMarkLineNotEqualTo(BigDecimal value) {
            addCriterion("remain_mark_line <>", value, "remainMarkLine");
            return (Criteria) this;
        }

        public Criteria andRemainMarkLineGreaterThan(BigDecimal value) {
            addCriterion("remain_mark_line >", value, "remainMarkLine");
            return (Criteria) this;
        }

        public Criteria andRemainMarkLineGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("remain_mark_line >=", value, "remainMarkLine");
            return (Criteria) this;
        }

        public Criteria andRemainMarkLineLessThan(BigDecimal value) {
            addCriterion("remain_mark_line <", value, "remainMarkLine");
            return (Criteria) this;
        }

        public Criteria andRemainMarkLineLessThanOrEqualTo(BigDecimal value) {
            addCriterion("remain_mark_line <=", value, "remainMarkLine");
            return (Criteria) this;
        }

        public Criteria andRemainMarkLineIn(List<BigDecimal> values) {
            addCriterion("remain_mark_line in", values, "remainMarkLine");
            return (Criteria) this;
        }

        public Criteria andRemainMarkLineNotIn(List<BigDecimal> values) {
            addCriterion("remain_mark_line not in", values, "remainMarkLine");
            return (Criteria) this;
        }

        public Criteria andRemainMarkLineBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("remain_mark_line between", value1, value2, "remainMarkLine");
            return (Criteria) this;
        }

        public Criteria andRemainMarkLineNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("remain_mark_line not between", value1, value2, "remainMarkLine");
            return (Criteria) this;
        }

        public Criteria andRepayedCapitalIsNull() {
            addCriterion("repayed_capital is null");
            return (Criteria) this;
        }

        public Criteria andRepayedCapitalIsNotNull() {
            addCriterion("repayed_capital is not null");
            return (Criteria) this;
        }

        public Criteria andRepayedCapitalEqualTo(BigDecimal value) {
            addCriterion("repayed_capital =", value, "repayedCapital");
            return (Criteria) this;
        }

        public Criteria andRepayedCapitalNotEqualTo(BigDecimal value) {
            addCriterion("repayed_capital <>", value, "repayedCapital");
            return (Criteria) this;
        }

        public Criteria andRepayedCapitalGreaterThan(BigDecimal value) {
            addCriterion("repayed_capital >", value, "repayedCapital");
            return (Criteria) this;
        }

        public Criteria andRepayedCapitalGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("repayed_capital >=", value, "repayedCapital");
            return (Criteria) this;
        }

        public Criteria andRepayedCapitalLessThan(BigDecimal value) {
            addCriterion("repayed_capital <", value, "repayedCapital");
            return (Criteria) this;
        }

        public Criteria andRepayedCapitalLessThanOrEqualTo(BigDecimal value) {
            addCriterion("repayed_capital <=", value, "repayedCapital");
            return (Criteria) this;
        }

        public Criteria andRepayedCapitalIn(List<BigDecimal> values) {
            addCriterion("repayed_capital in", values, "repayedCapital");
            return (Criteria) this;
        }

        public Criteria andRepayedCapitalNotIn(List<BigDecimal> values) {
            addCriterion("repayed_capital not in", values, "repayedCapital");
            return (Criteria) this;
        }

        public Criteria andRepayedCapitalBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repayed_capital between", value1, value2, "repayedCapital");
            return (Criteria) this;
        }

        public Criteria andRepayedCapitalNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repayed_capital not between", value1, value2, "repayedCapital");
            return (Criteria) this;
        }

        public Criteria andHisLoanTotalIsNull() {
            addCriterion("his_loan_total is null");
            return (Criteria) this;
        }

        public Criteria andHisLoanTotalIsNotNull() {
            addCriterion("his_loan_total is not null");
            return (Criteria) this;
        }

        public Criteria andHisLoanTotalEqualTo(BigDecimal value) {
            addCriterion("his_loan_total =", value, "hisLoanTotal");
            return (Criteria) this;
        }

        public Criteria andHisLoanTotalNotEqualTo(BigDecimal value) {
            addCriterion("his_loan_total <>", value, "hisLoanTotal");
            return (Criteria) this;
        }

        public Criteria andHisLoanTotalGreaterThan(BigDecimal value) {
            addCriterion("his_loan_total >", value, "hisLoanTotal");
            return (Criteria) this;
        }

        public Criteria andHisLoanTotalGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("his_loan_total >=", value, "hisLoanTotal");
            return (Criteria) this;
        }

        public Criteria andHisLoanTotalLessThan(BigDecimal value) {
            addCriterion("his_loan_total <", value, "hisLoanTotal");
            return (Criteria) this;
        }

        public Criteria andHisLoanTotalLessThanOrEqualTo(BigDecimal value) {
            addCriterion("his_loan_total <=", value, "hisLoanTotal");
            return (Criteria) this;
        }

        public Criteria andHisLoanTotalIn(List<BigDecimal> values) {
            addCriterion("his_loan_total in", values, "hisLoanTotal");
            return (Criteria) this;
        }

        public Criteria andHisLoanTotalNotIn(List<BigDecimal> values) {
            addCriterion("his_loan_total not in", values, "hisLoanTotal");
            return (Criteria) this;
        }

        public Criteria andHisLoanTotalBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("his_loan_total between", value1, value2, "hisLoanTotal");
            return (Criteria) this;
        }

        public Criteria andHisLoanTotalNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("his_loan_total not between", value1, value2, "hisLoanTotal");
            return (Criteria) this;
        }

        public Criteria andCycLoanTotalIsNull() {
            addCriterion("cyc_loan_total is null");
            return (Criteria) this;
        }

        public Criteria andCycLoanTotalIsNotNull() {
            addCriterion("cyc_loan_total is not null");
            return (Criteria) this;
        }

        public Criteria andCycLoanTotalEqualTo(BigDecimal value) {
            addCriterion("cyc_loan_total =", value, "cycLoanTotal");
            return (Criteria) this;
        }

        public Criteria andCycLoanTotalNotEqualTo(BigDecimal value) {
            addCriterion("cyc_loan_total <>", value, "cycLoanTotal");
            return (Criteria) this;
        }

        public Criteria andCycLoanTotalGreaterThan(BigDecimal value) {
            addCriterion("cyc_loan_total >", value, "cycLoanTotal");
            return (Criteria) this;
        }

        public Criteria andCycLoanTotalGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("cyc_loan_total >=", value, "cycLoanTotal");
            return (Criteria) this;
        }

        public Criteria andCycLoanTotalLessThan(BigDecimal value) {
            addCriterion("cyc_loan_total <", value, "cycLoanTotal");
            return (Criteria) this;
        }

        public Criteria andCycLoanTotalLessThanOrEqualTo(BigDecimal value) {
            addCriterion("cyc_loan_total <=", value, "cycLoanTotal");
            return (Criteria) this;
        }

        public Criteria andCycLoanTotalIn(List<BigDecimal> values) {
            addCriterion("cyc_loan_total in", values, "cycLoanTotal");
            return (Criteria) this;
        }

        public Criteria andCycLoanTotalNotIn(List<BigDecimal> values) {
            addCriterion("cyc_loan_total not in", values, "cycLoanTotal");
            return (Criteria) this;
        }

        public Criteria andCycLoanTotalBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("cyc_loan_total between", value1, value2, "cycLoanTotal");
            return (Criteria) this;
        }

        public Criteria andCycLoanTotalNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("cyc_loan_total not between", value1, value2, "cycLoanTotal");
            return (Criteria) this;
        }

        public Criteria andLoanBalanceIsNull() {
            addCriterion("loan_balance is null");
            return (Criteria) this;
        }

        public Criteria andLoanBalanceIsNotNull() {
            addCriterion("loan_balance is not null");
            return (Criteria) this;
        }

        public Criteria andLoanBalanceEqualTo(BigDecimal value) {
            addCriterion("loan_balance =", value, "loanBalance");
            return (Criteria) this;
        }

        public Criteria andLoanBalanceNotEqualTo(BigDecimal value) {
            addCriterion("loan_balance <>", value, "loanBalance");
            return (Criteria) this;
        }

        public Criteria andLoanBalanceGreaterThan(BigDecimal value) {
            addCriterion("loan_balance >", value, "loanBalance");
            return (Criteria) this;
        }

        public Criteria andLoanBalanceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("loan_balance >=", value, "loanBalance");
            return (Criteria) this;
        }

        public Criteria andLoanBalanceLessThan(BigDecimal value) {
            addCriterion("loan_balance <", value, "loanBalance");
            return (Criteria) this;
        }

        public Criteria andLoanBalanceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("loan_balance <=", value, "loanBalance");
            return (Criteria) this;
        }

        public Criteria andLoanBalanceIn(List<BigDecimal> values) {
            addCriterion("loan_balance in", values, "loanBalance");
            return (Criteria) this;
        }

        public Criteria andLoanBalanceNotIn(List<BigDecimal> values) {
            addCriterion("loan_balance not in", values, "loanBalance");
            return (Criteria) this;
        }

        public Criteria andLoanBalanceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("loan_balance between", value1, value2, "loanBalance");
            return (Criteria) this;
        }

        public Criteria andLoanBalanceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("loan_balance not between", value1, value2, "loanBalance");
            return (Criteria) this;
        }

        public Criteria andIsAccumulateIsNull() {
            addCriterion("is_accumulate is null");
            return (Criteria) this;
        }

        public Criteria andIsAccumulateIsNotNull() {
            addCriterion("is_accumulate is not null");
            return (Criteria) this;
        }

        public Criteria andIsAccumulateEqualTo(Integer value) {
            addCriterion("is_accumulate =", value, "isAccumulate");
            return (Criteria) this;
        }

        public Criteria andIsAccumulateNotEqualTo(Integer value) {
            addCriterion("is_accumulate <>", value, "isAccumulate");
            return (Criteria) this;
        }

        public Criteria andIsAccumulateGreaterThan(Integer value) {
            addCriterion("is_accumulate >", value, "isAccumulate");
            return (Criteria) this;
        }

        public Criteria andIsAccumulateGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_accumulate >=", value, "isAccumulate");
            return (Criteria) this;
        }

        public Criteria andIsAccumulateLessThan(Integer value) {
            addCriterion("is_accumulate <", value, "isAccumulate");
            return (Criteria) this;
        }

        public Criteria andIsAccumulateLessThanOrEqualTo(Integer value) {
            addCriterion("is_accumulate <=", value, "isAccumulate");
            return (Criteria) this;
        }

        public Criteria andIsAccumulateIn(List<Integer> values) {
            addCriterion("is_accumulate in", values, "isAccumulate");
            return (Criteria) this;
        }

        public Criteria andIsAccumulateNotIn(List<Integer> values) {
            addCriterion("is_accumulate not in", values, "isAccumulate");
            return (Criteria) this;
        }

        public Criteria andIsAccumulateBetween(Integer value1, Integer value2) {
            addCriterion("is_accumulate between", value1, value2, "isAccumulate");
            return (Criteria) this;
        }

        public Criteria andIsAccumulateNotBetween(Integer value1, Integer value2) {
            addCriterion("is_accumulate not between", value1, value2, "isAccumulate");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("remark not between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdIsNull() {
            addCriterion("create_user_id is null");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdIsNotNull() {
            addCriterion("create_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdEqualTo(Integer value) {
            addCriterion("create_user_id =", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdNotEqualTo(Integer value) {
            addCriterion("create_user_id <>", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdGreaterThan(Integer value) {
            addCriterion("create_user_id >", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("create_user_id >=", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdLessThan(Integer value) {
            addCriterion("create_user_id <", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("create_user_id <=", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdIn(List<Integer> values) {
            addCriterion("create_user_id in", values, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdNotIn(List<Integer> values) {
            addCriterion("create_user_id not in", values, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdBetween(Integer value1, Integer value2) {
            addCriterion("create_user_id between", value1, value2, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("create_user_id not between", value1, value2, "createUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdIsNull() {
            addCriterion("update_user_id is null");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdIsNotNull() {
            addCriterion("update_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdEqualTo(Integer value) {
            addCriterion("update_user_id =", value, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdNotEqualTo(Integer value) {
            addCriterion("update_user_id <>", value, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdGreaterThan(Integer value) {
            addCriterion("update_user_id >", value, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("update_user_id >=", value, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdLessThan(Integer value) {
            addCriterion("update_user_id <", value, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("update_user_id <=", value, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdIn(List<Integer> values) {
            addCriterion("update_user_id in", values, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdNotIn(List<Integer> values) {
            addCriterion("update_user_id not in", values, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdBetween(Integer value1, Integer value2) {
            addCriterion("update_user_id between", value1, value2, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("update_user_id not between", value1, value2, "updateUserId");
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

        public Criteria andDelFlgIsNull() {
            addCriterion("del_flg is null");
            return (Criteria) this;
        }

        public Criteria andDelFlgIsNotNull() {
            addCriterion("del_flg is not null");
            return (Criteria) this;
        }

        public Criteria andDelFlgEqualTo(Integer value) {
            addCriterion("del_flg =", value, "delFlg");
            return (Criteria) this;
        }

        public Criteria andDelFlgNotEqualTo(Integer value) {
            addCriterion("del_flg <>", value, "delFlg");
            return (Criteria) this;
        }

        public Criteria andDelFlgGreaterThan(Integer value) {
            addCriterion("del_flg >", value, "delFlg");
            return (Criteria) this;
        }

        public Criteria andDelFlgGreaterThanOrEqualTo(Integer value) {
            addCriterion("del_flg >=", value, "delFlg");
            return (Criteria) this;
        }

        public Criteria andDelFlgLessThan(Integer value) {
            addCriterion("del_flg <", value, "delFlg");
            return (Criteria) this;
        }

        public Criteria andDelFlgLessThanOrEqualTo(Integer value) {
            addCriterion("del_flg <=", value, "delFlg");
            return (Criteria) this;
        }

        public Criteria andDelFlgIn(List<Integer> values) {
            addCriterion("del_flg in", values, "delFlg");
            return (Criteria) this;
        }

        public Criteria andDelFlgNotIn(List<Integer> values) {
            addCriterion("del_flg not in", values, "delFlg");
            return (Criteria) this;
        }

        public Criteria andDelFlgBetween(Integer value1, Integer value2) {
            addCriterion("del_flg between", value1, value2, "delFlg");
            return (Criteria) this;
        }

        public Criteria andDelFlgNotBetween(Integer value1, Integer value2) {
            addCriterion("del_flg not between", value1, value2, "delFlg");
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