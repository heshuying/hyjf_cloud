package com.hyjf.am.user.dao.model.auto;

import java.util.ArrayList;
import java.util.List;

public class MspApplydetailsExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public MspApplydetailsExample() {
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

        public Criteria andApplydateIsNull() {
            addCriterion("applyDate is null");
            return (Criteria) this;
        }

        public Criteria andApplydateIsNotNull() {
            addCriterion("applyDate is not null");
            return (Criteria) this;
        }

        public Criteria andApplydateEqualTo(String value) {
            addCriterion("applyDate =", value, "applydate");
            return (Criteria) this;
        }

        public Criteria andApplydateNotEqualTo(String value) {
            addCriterion("applyDate <>", value, "applydate");
            return (Criteria) this;
        }

        public Criteria andApplydateGreaterThan(String value) {
            addCriterion("applyDate >", value, "applydate");
            return (Criteria) this;
        }

        public Criteria andApplydateGreaterThanOrEqualTo(String value) {
            addCriterion("applyDate >=", value, "applydate");
            return (Criteria) this;
        }

        public Criteria andApplydateLessThan(String value) {
            addCriterion("applyDate <", value, "applydate");
            return (Criteria) this;
        }

        public Criteria andApplydateLessThanOrEqualTo(String value) {
            addCriterion("applyDate <=", value, "applydate");
            return (Criteria) this;
        }

        public Criteria andApplydateLike(String value) {
            addCriterion("applyDate like", value, "applydate");
            return (Criteria) this;
        }

        public Criteria andApplydateNotLike(String value) {
            addCriterion("applyDate not like", value, "applydate");
            return (Criteria) this;
        }

        public Criteria andApplydateIn(List<String> values) {
            addCriterion("applyDate in", values, "applydate");
            return (Criteria) this;
        }

        public Criteria andApplydateNotIn(List<String> values) {
            addCriterion("applyDate not in", values, "applydate");
            return (Criteria) this;
        }

        public Criteria andApplydateBetween(String value1, String value2) {
            addCriterion("applyDate between", value1, value2, "applydate");
            return (Criteria) this;
        }

        public Criteria andApplydateNotBetween(String value1, String value2) {
            addCriterion("applyDate not between", value1, value2, "applydate");
            return (Criteria) this;
        }

        public Criteria andMembertypeIsNull() {
            addCriterion("memberType is null");
            return (Criteria) this;
        }

        public Criteria andMembertypeIsNotNull() {
            addCriterion("memberType is not null");
            return (Criteria) this;
        }

        public Criteria andMembertypeEqualTo(String value) {
            addCriterion("memberType =", value, "membertype");
            return (Criteria) this;
        }

        public Criteria andMembertypeNotEqualTo(String value) {
            addCriterion("memberType <>", value, "membertype");
            return (Criteria) this;
        }

        public Criteria andMembertypeGreaterThan(String value) {
            addCriterion("memberType >", value, "membertype");
            return (Criteria) this;
        }

        public Criteria andMembertypeGreaterThanOrEqualTo(String value) {
            addCriterion("memberType >=", value, "membertype");
            return (Criteria) this;
        }

        public Criteria andMembertypeLessThan(String value) {
            addCriterion("memberType <", value, "membertype");
            return (Criteria) this;
        }

        public Criteria andMembertypeLessThanOrEqualTo(String value) {
            addCriterion("memberType <=", value, "membertype");
            return (Criteria) this;
        }

        public Criteria andMembertypeLike(String value) {
            addCriterion("memberType like", value, "membertype");
            return (Criteria) this;
        }

        public Criteria andMembertypeNotLike(String value) {
            addCriterion("memberType not like", value, "membertype");
            return (Criteria) this;
        }

        public Criteria andMembertypeIn(List<String> values) {
            addCriterion("memberType in", values, "membertype");
            return (Criteria) this;
        }

        public Criteria andMembertypeNotIn(List<String> values) {
            addCriterion("memberType not in", values, "membertype");
            return (Criteria) this;
        }

        public Criteria andMembertypeBetween(String value1, String value2) {
            addCriterion("memberType between", value1, value2, "membertype");
            return (Criteria) this;
        }

        public Criteria andMembertypeNotBetween(String value1, String value2) {
            addCriterion("memberType not between", value1, value2, "membertype");
            return (Criteria) this;
        }

        public Criteria andCreditaddressIsNull() {
            addCriterion("creditAddress is null");
            return (Criteria) this;
        }

        public Criteria andCreditaddressIsNotNull() {
            addCriterion("creditAddress is not null");
            return (Criteria) this;
        }

        public Criteria andCreditaddressEqualTo(String value) {
            addCriterion("creditAddress =", value, "creditaddress");
            return (Criteria) this;
        }

        public Criteria andCreditaddressNotEqualTo(String value) {
            addCriterion("creditAddress <>", value, "creditaddress");
            return (Criteria) this;
        }

        public Criteria andCreditaddressGreaterThan(String value) {
            addCriterion("creditAddress >", value, "creditaddress");
            return (Criteria) this;
        }

        public Criteria andCreditaddressGreaterThanOrEqualTo(String value) {
            addCriterion("creditAddress >=", value, "creditaddress");
            return (Criteria) this;
        }

        public Criteria andCreditaddressLessThan(String value) {
            addCriterion("creditAddress <", value, "creditaddress");
            return (Criteria) this;
        }

        public Criteria andCreditaddressLessThanOrEqualTo(String value) {
            addCriterion("creditAddress <=", value, "creditaddress");
            return (Criteria) this;
        }

        public Criteria andCreditaddressLike(String value) {
            addCriterion("creditAddress like", value, "creditaddress");
            return (Criteria) this;
        }

        public Criteria andCreditaddressNotLike(String value) {
            addCriterion("creditAddress not like", value, "creditaddress");
            return (Criteria) this;
        }

        public Criteria andCreditaddressIn(List<String> values) {
            addCriterion("creditAddress in", values, "creditaddress");
            return (Criteria) this;
        }

        public Criteria andCreditaddressNotIn(List<String> values) {
            addCriterion("creditAddress not in", values, "creditaddress");
            return (Criteria) this;
        }

        public Criteria andCreditaddressBetween(String value1, String value2) {
            addCriterion("creditAddress between", value1, value2, "creditaddress");
            return (Criteria) this;
        }

        public Criteria andCreditaddressNotBetween(String value1, String value2) {
            addCriterion("creditAddress not between", value1, value2, "creditaddress");
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

        public Criteria andApplyresultIsNull() {
            addCriterion("applyResult is null");
            return (Criteria) this;
        }

        public Criteria andApplyresultIsNotNull() {
            addCriterion("applyResult is not null");
            return (Criteria) this;
        }

        public Criteria andApplyresultEqualTo(String value) {
            addCriterion("applyResult =", value, "applyresult");
            return (Criteria) this;
        }

        public Criteria andApplyresultNotEqualTo(String value) {
            addCriterion("applyResult <>", value, "applyresult");
            return (Criteria) this;
        }

        public Criteria andApplyresultGreaterThan(String value) {
            addCriterion("applyResult >", value, "applyresult");
            return (Criteria) this;
        }

        public Criteria andApplyresultGreaterThanOrEqualTo(String value) {
            addCriterion("applyResult >=", value, "applyresult");
            return (Criteria) this;
        }

        public Criteria andApplyresultLessThan(String value) {
            addCriterion("applyResult <", value, "applyresult");
            return (Criteria) this;
        }

        public Criteria andApplyresultLessThanOrEqualTo(String value) {
            addCriterion("applyResult <=", value, "applyresult");
            return (Criteria) this;
        }

        public Criteria andApplyresultLike(String value) {
            addCriterion("applyResult like", value, "applyresult");
            return (Criteria) this;
        }

        public Criteria andApplyresultNotLike(String value) {
            addCriterion("applyResult not like", value, "applyresult");
            return (Criteria) this;
        }

        public Criteria andApplyresultIn(List<String> values) {
            addCriterion("applyResult in", values, "applyresult");
            return (Criteria) this;
        }

        public Criteria andApplyresultNotIn(List<String> values) {
            addCriterion("applyResult not in", values, "applyresult");
            return (Criteria) this;
        }

        public Criteria andApplyresultBetween(String value1, String value2) {
            addCriterion("applyResult between", value1, value2, "applyresult");
            return (Criteria) this;
        }

        public Criteria andApplyresultNotBetween(String value1, String value2) {
            addCriterion("applyResult not between", value1, value2, "applyresult");
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