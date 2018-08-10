package com.hyjf.am.user.dao.model.auto;

import java.util.ArrayList;
import java.util.List;

public class MspAbnormalcreditExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public MspAbnormalcreditExample() {
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

        public Criteria andIdEqualTo(String value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("id like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("id not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andApplyIdIsNull() {
            addCriterion("apply_id is null");
            return (Criteria) this;
        }

        public Criteria andApplyIdIsNotNull() {
            addCriterion("apply_id is not null");
            return (Criteria) this;
        }

        public Criteria andApplyIdEqualTo(String value) {
            addCriterion("apply_id =", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdNotEqualTo(String value) {
            addCriterion("apply_id <>", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdGreaterThan(String value) {
            addCriterion("apply_id >", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdGreaterThanOrEqualTo(String value) {
            addCriterion("apply_id >=", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdLessThan(String value) {
            addCriterion("apply_id <", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdLessThanOrEqualTo(String value) {
            addCriterion("apply_id <=", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdLike(String value) {
            addCriterion("apply_id like", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdNotLike(String value) {
            addCriterion("apply_id not like", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdIn(List<String> values) {
            addCriterion("apply_id in", values, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdNotIn(List<String> values) {
            addCriterion("apply_id not in", values, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdBetween(String value1, String value2) {
            addCriterion("apply_id between", value1, value2, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdNotBetween(String value1, String value2) {
            addCriterion("apply_id not between", value1, value2, "applyId");
            return (Criteria) this;
        }

        public Criteria andCreditstartdateIsNull() {
            addCriterion("creditStartDate is null");
            return (Criteria) this;
        }

        public Criteria andCreditstartdateIsNotNull() {
            addCriterion("creditStartDate is not null");
            return (Criteria) this;
        }

        public Criteria andCreditstartdateEqualTo(String value) {
            addCriterion("creditStartDate =", value, "creditstartdate");
            return (Criteria) this;
        }

        public Criteria andCreditstartdateNotEqualTo(String value) {
            addCriterion("creditStartDate <>", value, "creditstartdate");
            return (Criteria) this;
        }

        public Criteria andCreditstartdateGreaterThan(String value) {
            addCriterion("creditStartDate >", value, "creditstartdate");
            return (Criteria) this;
        }

        public Criteria andCreditstartdateGreaterThanOrEqualTo(String value) {
            addCriterion("creditStartDate >=", value, "creditstartdate");
            return (Criteria) this;
        }

        public Criteria andCreditstartdateLessThan(String value) {
            addCriterion("creditStartDate <", value, "creditstartdate");
            return (Criteria) this;
        }

        public Criteria andCreditstartdateLessThanOrEqualTo(String value) {
            addCriterion("creditStartDate <=", value, "creditstartdate");
            return (Criteria) this;
        }

        public Criteria andCreditstartdateLike(String value) {
            addCriterion("creditStartDate like", value, "creditstartdate");
            return (Criteria) this;
        }

        public Criteria andCreditstartdateNotLike(String value) {
            addCriterion("creditStartDate not like", value, "creditstartdate");
            return (Criteria) this;
        }

        public Criteria andCreditstartdateIn(List<String> values) {
            addCriterion("creditStartDate in", values, "creditstartdate");
            return (Criteria) this;
        }

        public Criteria andCreditstartdateNotIn(List<String> values) {
            addCriterion("creditStartDate not in", values, "creditstartdate");
            return (Criteria) this;
        }

        public Criteria andCreditstartdateBetween(String value1, String value2) {
            addCriterion("creditStartDate between", value1, value2, "creditstartdate");
            return (Criteria) this;
        }

        public Criteria andCreditstartdateNotBetween(String value1, String value2) {
            addCriterion("creditStartDate not between", value1, value2, "creditstartdate");
            return (Criteria) this;
        }

        public Criteria andCreditenddateIsNull() {
            addCriterion("creditEndDate is null");
            return (Criteria) this;
        }

        public Criteria andCreditenddateIsNotNull() {
            addCriterion("creditEndDate is not null");
            return (Criteria) this;
        }

        public Criteria andCreditenddateEqualTo(String value) {
            addCriterion("creditEndDate =", value, "creditenddate");
            return (Criteria) this;
        }

        public Criteria andCreditenddateNotEqualTo(String value) {
            addCriterion("creditEndDate <>", value, "creditenddate");
            return (Criteria) this;
        }

        public Criteria andCreditenddateGreaterThan(String value) {
            addCriterion("creditEndDate >", value, "creditenddate");
            return (Criteria) this;
        }

        public Criteria andCreditenddateGreaterThanOrEqualTo(String value) {
            addCriterion("creditEndDate >=", value, "creditenddate");
            return (Criteria) this;
        }

        public Criteria andCreditenddateLessThan(String value) {
            addCriterion("creditEndDate <", value, "creditenddate");
            return (Criteria) this;
        }

        public Criteria andCreditenddateLessThanOrEqualTo(String value) {
            addCriterion("creditEndDate <=", value, "creditenddate");
            return (Criteria) this;
        }

        public Criteria andCreditenddateLike(String value) {
            addCriterion("creditEndDate like", value, "creditenddate");
            return (Criteria) this;
        }

        public Criteria andCreditenddateNotLike(String value) {
            addCriterion("creditEndDate not like", value, "creditenddate");
            return (Criteria) this;
        }

        public Criteria andCreditenddateIn(List<String> values) {
            addCriterion("creditEndDate in", values, "creditenddate");
            return (Criteria) this;
        }

        public Criteria andCreditenddateNotIn(List<String> values) {
            addCriterion("creditEndDate not in", values, "creditenddate");
            return (Criteria) this;
        }

        public Criteria andCreditenddateBetween(String value1, String value2) {
            addCriterion("creditEndDate between", value1, value2, "creditenddate");
            return (Criteria) this;
        }

        public Criteria andCreditenddateNotBetween(String value1, String value2) {
            addCriterion("creditEndDate not between", value1, value2, "creditenddate");
            return (Criteria) this;
        }

        public Criteria andLoantypeIsNull() {
            addCriterion("loanType is null");
            return (Criteria) this;
        }

        public Criteria andLoantypeIsNotNull() {
            addCriterion("loanType is not null");
            return (Criteria) this;
        }

        public Criteria andLoantypeEqualTo(String value) {
            addCriterion("loanType =", value, "loantype");
            return (Criteria) this;
        }

        public Criteria andLoantypeNotEqualTo(String value) {
            addCriterion("loanType <>", value, "loantype");
            return (Criteria) this;
        }

        public Criteria andLoantypeGreaterThan(String value) {
            addCriterion("loanType >", value, "loantype");
            return (Criteria) this;
        }

        public Criteria andLoantypeGreaterThanOrEqualTo(String value) {
            addCriterion("loanType >=", value, "loantype");
            return (Criteria) this;
        }

        public Criteria andLoantypeLessThan(String value) {
            addCriterion("loanType <", value, "loantype");
            return (Criteria) this;
        }

        public Criteria andLoantypeLessThanOrEqualTo(String value) {
            addCriterion("loanType <=", value, "loantype");
            return (Criteria) this;
        }

        public Criteria andLoantypeLike(String value) {
            addCriterion("loanType like", value, "loantype");
            return (Criteria) this;
        }

        public Criteria andLoantypeNotLike(String value) {
            addCriterion("loanType not like", value, "loantype");
            return (Criteria) this;
        }

        public Criteria andLoantypeIn(List<String> values) {
            addCriterion("loanType in", values, "loantype");
            return (Criteria) this;
        }

        public Criteria andLoantypeNotIn(List<String> values) {
            addCriterion("loanType not in", values, "loantype");
            return (Criteria) this;
        }

        public Criteria andLoantypeBetween(String value1, String value2) {
            addCriterion("loanType between", value1, value2, "loantype");
            return (Criteria) this;
        }

        public Criteria andLoantypeNotBetween(String value1, String value2) {
            addCriterion("loanType not between", value1, value2, "loantype");
            return (Criteria) this;
        }

        public Criteria andLoanmoneyIsNull() {
            addCriterion("loanMoney is null");
            return (Criteria) this;
        }

        public Criteria andLoanmoneyIsNotNull() {
            addCriterion("loanMoney is not null");
            return (Criteria) this;
        }

        public Criteria andLoanmoneyEqualTo(String value) {
            addCriterion("loanMoney =", value, "loanmoney");
            return (Criteria) this;
        }

        public Criteria andLoanmoneyNotEqualTo(String value) {
            addCriterion("loanMoney <>", value, "loanmoney");
            return (Criteria) this;
        }

        public Criteria andLoanmoneyGreaterThan(String value) {
            addCriterion("loanMoney >", value, "loanmoney");
            return (Criteria) this;
        }

        public Criteria andLoanmoneyGreaterThanOrEqualTo(String value) {
            addCriterion("loanMoney >=", value, "loanmoney");
            return (Criteria) this;
        }

        public Criteria andLoanmoneyLessThan(String value) {
            addCriterion("loanMoney <", value, "loanmoney");
            return (Criteria) this;
        }

        public Criteria andLoanmoneyLessThanOrEqualTo(String value) {
            addCriterion("loanMoney <=", value, "loanmoney");
            return (Criteria) this;
        }

        public Criteria andLoanmoneyLike(String value) {
            addCriterion("loanMoney like", value, "loanmoney");
            return (Criteria) this;
        }

        public Criteria andLoanmoneyNotLike(String value) {
            addCriterion("loanMoney not like", value, "loanmoney");
            return (Criteria) this;
        }

        public Criteria andLoanmoneyIn(List<String> values) {
            addCriterion("loanMoney in", values, "loanmoney");
            return (Criteria) this;
        }

        public Criteria andLoanmoneyNotIn(List<String> values) {
            addCriterion("loanMoney not in", values, "loanmoney");
            return (Criteria) this;
        }

        public Criteria andLoanmoneyBetween(String value1, String value2) {
            addCriterion("loanMoney between", value1, value2, "loanmoney");
            return (Criteria) this;
        }

        public Criteria andLoanmoneyNotBetween(String value1, String value2) {
            addCriterion("loanMoney not between", value1, value2, "loanmoney");
            return (Criteria) this;
        }

        public Criteria andAssuretypeIsNull() {
            addCriterion("assureType is null");
            return (Criteria) this;
        }

        public Criteria andAssuretypeIsNotNull() {
            addCriterion("assureType is not null");
            return (Criteria) this;
        }

        public Criteria andAssuretypeEqualTo(String value) {
            addCriterion("assureType =", value, "assuretype");
            return (Criteria) this;
        }

        public Criteria andAssuretypeNotEqualTo(String value) {
            addCriterion("assureType <>", value, "assuretype");
            return (Criteria) this;
        }

        public Criteria andAssuretypeGreaterThan(String value) {
            addCriterion("assureType >", value, "assuretype");
            return (Criteria) this;
        }

        public Criteria andAssuretypeGreaterThanOrEqualTo(String value) {
            addCriterion("assureType >=", value, "assuretype");
            return (Criteria) this;
        }

        public Criteria andAssuretypeLessThan(String value) {
            addCriterion("assureType <", value, "assuretype");
            return (Criteria) this;
        }

        public Criteria andAssuretypeLessThanOrEqualTo(String value) {
            addCriterion("assureType <=", value, "assuretype");
            return (Criteria) this;
        }

        public Criteria andAssuretypeLike(String value) {
            addCriterion("assureType like", value, "assuretype");
            return (Criteria) this;
        }

        public Criteria andAssuretypeNotLike(String value) {
            addCriterion("assureType not like", value, "assuretype");
            return (Criteria) this;
        }

        public Criteria andAssuretypeIn(List<String> values) {
            addCriterion("assureType in", values, "assuretype");
            return (Criteria) this;
        }

        public Criteria andAssuretypeNotIn(List<String> values) {
            addCriterion("assureType not in", values, "assuretype");
            return (Criteria) this;
        }

        public Criteria andAssuretypeBetween(String value1, String value2) {
            addCriterion("assureType between", value1, value2, "assuretype");
            return (Criteria) this;
        }

        public Criteria andAssuretypeNotBetween(String value1, String value2) {
            addCriterion("assureType not between", value1, value2, "assuretype");
            return (Criteria) this;
        }

        public Criteria andLoanperiodsIsNull() {
            addCriterion("loanPeriods is null");
            return (Criteria) this;
        }

        public Criteria andLoanperiodsIsNotNull() {
            addCriterion("loanPeriods is not null");
            return (Criteria) this;
        }

        public Criteria andLoanperiodsEqualTo(String value) {
            addCriterion("loanPeriods =", value, "loanperiods");
            return (Criteria) this;
        }

        public Criteria andLoanperiodsNotEqualTo(String value) {
            addCriterion("loanPeriods <>", value, "loanperiods");
            return (Criteria) this;
        }

        public Criteria andLoanperiodsGreaterThan(String value) {
            addCriterion("loanPeriods >", value, "loanperiods");
            return (Criteria) this;
        }

        public Criteria andLoanperiodsGreaterThanOrEqualTo(String value) {
            addCriterion("loanPeriods >=", value, "loanperiods");
            return (Criteria) this;
        }

        public Criteria andLoanperiodsLessThan(String value) {
            addCriterion("loanPeriods <", value, "loanperiods");
            return (Criteria) this;
        }

        public Criteria andLoanperiodsLessThanOrEqualTo(String value) {
            addCriterion("loanPeriods <=", value, "loanperiods");
            return (Criteria) this;
        }

        public Criteria andLoanperiodsLike(String value) {
            addCriterion("loanPeriods like", value, "loanperiods");
            return (Criteria) this;
        }

        public Criteria andLoanperiodsNotLike(String value) {
            addCriterion("loanPeriods not like", value, "loanperiods");
            return (Criteria) this;
        }

        public Criteria andLoanperiodsIn(List<String> values) {
            addCriterion("loanPeriods in", values, "loanperiods");
            return (Criteria) this;
        }

        public Criteria andLoanperiodsNotIn(List<String> values) {
            addCriterion("loanPeriods not in", values, "loanperiods");
            return (Criteria) this;
        }

        public Criteria andLoanperiodsBetween(String value1, String value2) {
            addCriterion("loanPeriods between", value1, value2, "loanperiods");
            return (Criteria) this;
        }

        public Criteria andLoanperiodsNotBetween(String value1, String value2) {
            addCriterion("loanPeriods not between", value1, value2, "loanperiods");
            return (Criteria) this;
        }

        public Criteria andNumIsNull() {
            addCriterion("num is null");
            return (Criteria) this;
        }

        public Criteria andNumIsNotNull() {
            addCriterion("num is not null");
            return (Criteria) this;
        }

        public Criteria andNumEqualTo(String value) {
            addCriterion("num =", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumNotEqualTo(String value) {
            addCriterion("num <>", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumGreaterThan(String value) {
            addCriterion("num >", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumGreaterThanOrEqualTo(String value) {
            addCriterion("num >=", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumLessThan(String value) {
            addCriterion("num <", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumLessThanOrEqualTo(String value) {
            addCriterion("num <=", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumLike(String value) {
            addCriterion("num like", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumNotLike(String value) {
            addCriterion("num not like", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumIn(List<String> values) {
            addCriterion("num in", values, "num");
            return (Criteria) this;
        }

        public Criteria andNumNotIn(List<String> values) {
            addCriterion("num not in", values, "num");
            return (Criteria) this;
        }

        public Criteria andNumBetween(String value1, String value2) {
            addCriterion("num between", value1, value2, "num");
            return (Criteria) this;
        }

        public Criteria andNumNotBetween(String value1, String value2) {
            addCriterion("num not between", value1, value2, "num");
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