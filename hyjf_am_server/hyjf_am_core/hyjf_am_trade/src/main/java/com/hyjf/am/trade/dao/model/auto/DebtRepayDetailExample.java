package com.hyjf.am.trade.dao.model.auto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DebtRepayDetailExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public DebtRepayDetailExample() {
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

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(Integer value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(Integer value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(Integer value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(Integer value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<Integer> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<Integer> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(Integer value1, Integer value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserNameIsNull() {
            addCriterion("user_name is null");
            return (Criteria) this;
        }

        public Criteria andUserNameIsNotNull() {
            addCriterion("user_name is not null");
            return (Criteria) this;
        }

        public Criteria andUserNameEqualTo(String value) {
            addCriterion("user_name =", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotEqualTo(String value) {
            addCriterion("user_name <>", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameGreaterThan(String value) {
            addCriterion("user_name >", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("user_name >=", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLessThan(String value) {
            addCriterion("user_name <", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLessThanOrEqualTo(String value) {
            addCriterion("user_name <=", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLike(String value) {
            addCriterion("user_name like", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotLike(String value) {
            addCriterion("user_name not like", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameIn(List<String> values) {
            addCriterion("user_name in", values, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotIn(List<String> values) {
            addCriterion("user_name not in", values, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameBetween(String value1, String value2) {
            addCriterion("user_name between", value1, value2, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotBetween(String value1, String value2) {
            addCriterion("user_name not between", value1, value2, "userName");
            return (Criteria) this;
        }

        public Criteria andBorrowNidIsNull() {
            addCriterion("borrow_nid is null");
            return (Criteria) this;
        }

        public Criteria andBorrowNidIsNotNull() {
            addCriterion("borrow_nid is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowNidEqualTo(String value) {
            addCriterion("borrow_nid =", value, "borrowNid");
            return (Criteria) this;
        }

        public Criteria andBorrowNidNotEqualTo(String value) {
            addCriterion("borrow_nid <>", value, "borrowNid");
            return (Criteria) this;
        }

        public Criteria andBorrowNidGreaterThan(String value) {
            addCriterion("borrow_nid >", value, "borrowNid");
            return (Criteria) this;
        }

        public Criteria andBorrowNidGreaterThanOrEqualTo(String value) {
            addCriterion("borrow_nid >=", value, "borrowNid");
            return (Criteria) this;
        }

        public Criteria andBorrowNidLessThan(String value) {
            addCriterion("borrow_nid <", value, "borrowNid");
            return (Criteria) this;
        }

        public Criteria andBorrowNidLessThanOrEqualTo(String value) {
            addCriterion("borrow_nid <=", value, "borrowNid");
            return (Criteria) this;
        }

        public Criteria andBorrowNidLike(String value) {
            addCriterion("borrow_nid like", value, "borrowNid");
            return (Criteria) this;
        }

        public Criteria andBorrowNidNotLike(String value) {
            addCriterion("borrow_nid not like", value, "borrowNid");
            return (Criteria) this;
        }

        public Criteria andBorrowNidIn(List<String> values) {
            addCriterion("borrow_nid in", values, "borrowNid");
            return (Criteria) this;
        }

        public Criteria andBorrowNidNotIn(List<String> values) {
            addCriterion("borrow_nid not in", values, "borrowNid");
            return (Criteria) this;
        }

        public Criteria andBorrowNidBetween(String value1, String value2) {
            addCriterion("borrow_nid between", value1, value2, "borrowNid");
            return (Criteria) this;
        }

        public Criteria andBorrowNidNotBetween(String value1, String value2) {
            addCriterion("borrow_nid not between", value1, value2, "borrowNid");
            return (Criteria) this;
        }

        public Criteria andPlanNidIsNull() {
            addCriterion("plan_nid is null");
            return (Criteria) this;
        }

        public Criteria andPlanNidIsNotNull() {
            addCriterion("plan_nid is not null");
            return (Criteria) this;
        }

        public Criteria andPlanNidEqualTo(String value) {
            addCriterion("plan_nid =", value, "planNid");
            return (Criteria) this;
        }

        public Criteria andPlanNidNotEqualTo(String value) {
            addCriterion("plan_nid <>", value, "planNid");
            return (Criteria) this;
        }

        public Criteria andPlanNidGreaterThan(String value) {
            addCriterion("plan_nid >", value, "planNid");
            return (Criteria) this;
        }

        public Criteria andPlanNidGreaterThanOrEqualTo(String value) {
            addCriterion("plan_nid >=", value, "planNid");
            return (Criteria) this;
        }

        public Criteria andPlanNidLessThan(String value) {
            addCriterion("plan_nid <", value, "planNid");
            return (Criteria) this;
        }

        public Criteria andPlanNidLessThanOrEqualTo(String value) {
            addCriterion("plan_nid <=", value, "planNid");
            return (Criteria) this;
        }

        public Criteria andPlanNidLike(String value) {
            addCriterion("plan_nid like", value, "planNid");
            return (Criteria) this;
        }

        public Criteria andPlanNidNotLike(String value) {
            addCriterion("plan_nid not like", value, "planNid");
            return (Criteria) this;
        }

        public Criteria andPlanNidIn(List<String> values) {
            addCriterion("plan_nid in", values, "planNid");
            return (Criteria) this;
        }

        public Criteria andPlanNidNotIn(List<String> values) {
            addCriterion("plan_nid not in", values, "planNid");
            return (Criteria) this;
        }

        public Criteria andPlanNidBetween(String value1, String value2) {
            addCriterion("plan_nid between", value1, value2, "planNid");
            return (Criteria) this;
        }

        public Criteria andPlanNidNotBetween(String value1, String value2) {
            addCriterion("plan_nid not between", value1, value2, "planNid");
            return (Criteria) this;
        }

        public Criteria andRepayNidIsNull() {
            addCriterion("repay_nid is null");
            return (Criteria) this;
        }

        public Criteria andRepayNidIsNotNull() {
            addCriterion("repay_nid is not null");
            return (Criteria) this;
        }

        public Criteria andRepayNidEqualTo(String value) {
            addCriterion("repay_nid =", value, "repayNid");
            return (Criteria) this;
        }

        public Criteria andRepayNidNotEqualTo(String value) {
            addCriterion("repay_nid <>", value, "repayNid");
            return (Criteria) this;
        }

        public Criteria andRepayNidGreaterThan(String value) {
            addCriterion("repay_nid >", value, "repayNid");
            return (Criteria) this;
        }

        public Criteria andRepayNidGreaterThanOrEqualTo(String value) {
            addCriterion("repay_nid >=", value, "repayNid");
            return (Criteria) this;
        }

        public Criteria andRepayNidLessThan(String value) {
            addCriterion("repay_nid <", value, "repayNid");
            return (Criteria) this;
        }

        public Criteria andRepayNidLessThanOrEqualTo(String value) {
            addCriterion("repay_nid <=", value, "repayNid");
            return (Criteria) this;
        }

        public Criteria andRepayNidLike(String value) {
            addCriterion("repay_nid like", value, "repayNid");
            return (Criteria) this;
        }

        public Criteria andRepayNidNotLike(String value) {
            addCriterion("repay_nid not like", value, "repayNid");
            return (Criteria) this;
        }

        public Criteria andRepayNidIn(List<String> values) {
            addCriterion("repay_nid in", values, "repayNid");
            return (Criteria) this;
        }

        public Criteria andRepayNidNotIn(List<String> values) {
            addCriterion("repay_nid not in", values, "repayNid");
            return (Criteria) this;
        }

        public Criteria andRepayNidBetween(String value1, String value2) {
            addCriterion("repay_nid between", value1, value2, "repayNid");
            return (Criteria) this;
        }

        public Criteria andRepayNidNotBetween(String value1, String value2) {
            addCriterion("repay_nid not between", value1, value2, "repayNid");
            return (Criteria) this;
        }

        public Criteria andManageFeeIsNull() {
            addCriterion("manage_fee is null");
            return (Criteria) this;
        }

        public Criteria andManageFeeIsNotNull() {
            addCriterion("manage_fee is not null");
            return (Criteria) this;
        }

        public Criteria andManageFeeEqualTo(BigDecimal value) {
            addCriterion("manage_fee =", value, "manageFee");
            return (Criteria) this;
        }

        public Criteria andManageFeeNotEqualTo(BigDecimal value) {
            addCriterion("manage_fee <>", value, "manageFee");
            return (Criteria) this;
        }

        public Criteria andManageFeeGreaterThan(BigDecimal value) {
            addCriterion("manage_fee >", value, "manageFee");
            return (Criteria) this;
        }

        public Criteria andManageFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("manage_fee >=", value, "manageFee");
            return (Criteria) this;
        }

        public Criteria andManageFeeLessThan(BigDecimal value) {
            addCriterion("manage_fee <", value, "manageFee");
            return (Criteria) this;
        }

        public Criteria andManageFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("manage_fee <=", value, "manageFee");
            return (Criteria) this;
        }

        public Criteria andManageFeeIn(List<BigDecimal> values) {
            addCriterion("manage_fee in", values, "manageFee");
            return (Criteria) this;
        }

        public Criteria andManageFeeNotIn(List<BigDecimal> values) {
            addCriterion("manage_fee not in", values, "manageFee");
            return (Criteria) this;
        }

        public Criteria andManageFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("manage_fee between", value1, value2, "manageFee");
            return (Criteria) this;
        }

        public Criteria andManageFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("manage_fee not between", value1, value2, "manageFee");
            return (Criteria) this;
        }

        public Criteria andLiquidatesServiceFeeIsNull() {
            addCriterion("liquidates_service_fee is null");
            return (Criteria) this;
        }

        public Criteria andLiquidatesServiceFeeIsNotNull() {
            addCriterion("liquidates_service_fee is not null");
            return (Criteria) this;
        }

        public Criteria andLiquidatesServiceFeeEqualTo(BigDecimal value) {
            addCriterion("liquidates_service_fee =", value, "liquidatesServiceFee");
            return (Criteria) this;
        }

        public Criteria andLiquidatesServiceFeeNotEqualTo(BigDecimal value) {
            addCriterion("liquidates_service_fee <>", value, "liquidatesServiceFee");
            return (Criteria) this;
        }

        public Criteria andLiquidatesServiceFeeGreaterThan(BigDecimal value) {
            addCriterion("liquidates_service_fee >", value, "liquidatesServiceFee");
            return (Criteria) this;
        }

        public Criteria andLiquidatesServiceFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("liquidates_service_fee >=", value, "liquidatesServiceFee");
            return (Criteria) this;
        }

        public Criteria andLiquidatesServiceFeeLessThan(BigDecimal value) {
            addCriterion("liquidates_service_fee <", value, "liquidatesServiceFee");
            return (Criteria) this;
        }

        public Criteria andLiquidatesServiceFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("liquidates_service_fee <=", value, "liquidatesServiceFee");
            return (Criteria) this;
        }

        public Criteria andLiquidatesServiceFeeIn(List<BigDecimal> values) {
            addCriterion("liquidates_service_fee in", values, "liquidatesServiceFee");
            return (Criteria) this;
        }

        public Criteria andLiquidatesServiceFeeNotIn(List<BigDecimal> values) {
            addCriterion("liquidates_service_fee not in", values, "liquidatesServiceFee");
            return (Criteria) this;
        }

        public Criteria andLiquidatesServiceFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("liquidates_service_fee between", value1, value2, "liquidatesServiceFee");
            return (Criteria) this;
        }

        public Criteria andLiquidatesServiceFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("liquidates_service_fee not between", value1, value2, "liquidatesServiceFee");
            return (Criteria) this;
        }

        public Criteria andLoanStatusIsNull() {
            addCriterion("loan_status is null");
            return (Criteria) this;
        }

        public Criteria andLoanStatusIsNotNull() {
            addCriterion("loan_status is not null");
            return (Criteria) this;
        }

        public Criteria andLoanStatusEqualTo(Integer value) {
            addCriterion("loan_status =", value, "loanStatus");
            return (Criteria) this;
        }

        public Criteria andLoanStatusNotEqualTo(Integer value) {
            addCriterion("loan_status <>", value, "loanStatus");
            return (Criteria) this;
        }

        public Criteria andLoanStatusGreaterThan(Integer value) {
            addCriterion("loan_status >", value, "loanStatus");
            return (Criteria) this;
        }

        public Criteria andLoanStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("loan_status >=", value, "loanStatus");
            return (Criteria) this;
        }

        public Criteria andLoanStatusLessThan(Integer value) {
            addCriterion("loan_status <", value, "loanStatus");
            return (Criteria) this;
        }

        public Criteria andLoanStatusLessThanOrEqualTo(Integer value) {
            addCriterion("loan_status <=", value, "loanStatus");
            return (Criteria) this;
        }

        public Criteria andLoanStatusIn(List<Integer> values) {
            addCriterion("loan_status in", values, "loanStatus");
            return (Criteria) this;
        }

        public Criteria andLoanStatusNotIn(List<Integer> values) {
            addCriterion("loan_status not in", values, "loanStatus");
            return (Criteria) this;
        }

        public Criteria andLoanStatusBetween(Integer value1, Integer value2) {
            addCriterion("loan_status between", value1, value2, "loanStatus");
            return (Criteria) this;
        }

        public Criteria andLoanStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("loan_status not between", value1, value2, "loanStatus");
            return (Criteria) this;
        }

        public Criteria andRepayStatusIsNull() {
            addCriterion("repay_status is null");
            return (Criteria) this;
        }

        public Criteria andRepayStatusIsNotNull() {
            addCriterion("repay_status is not null");
            return (Criteria) this;
        }

        public Criteria andRepayStatusEqualTo(Integer value) {
            addCriterion("repay_status =", value, "repayStatus");
            return (Criteria) this;
        }

        public Criteria andRepayStatusNotEqualTo(Integer value) {
            addCriterion("repay_status <>", value, "repayStatus");
            return (Criteria) this;
        }

        public Criteria andRepayStatusGreaterThan(Integer value) {
            addCriterion("repay_status >", value, "repayStatus");
            return (Criteria) this;
        }

        public Criteria andRepayStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("repay_status >=", value, "repayStatus");
            return (Criteria) this;
        }

        public Criteria andRepayStatusLessThan(Integer value) {
            addCriterion("repay_status <", value, "repayStatus");
            return (Criteria) this;
        }

        public Criteria andRepayStatusLessThanOrEqualTo(Integer value) {
            addCriterion("repay_status <=", value, "repayStatus");
            return (Criteria) this;
        }

        public Criteria andRepayStatusIn(List<Integer> values) {
            addCriterion("repay_status in", values, "repayStatus");
            return (Criteria) this;
        }

        public Criteria andRepayStatusNotIn(List<Integer> values) {
            addCriterion("repay_status not in", values, "repayStatus");
            return (Criteria) this;
        }

        public Criteria andRepayStatusBetween(Integer value1, Integer value2) {
            addCriterion("repay_status between", value1, value2, "repayStatus");
            return (Criteria) this;
        }

        public Criteria andRepayStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("repay_status not between", value1, value2, "repayStatus");
            return (Criteria) this;
        }

        public Criteria andRepayPeriodIsNull() {
            addCriterion("repay_period is null");
            return (Criteria) this;
        }

        public Criteria andRepayPeriodIsNotNull() {
            addCriterion("repay_period is not null");
            return (Criteria) this;
        }

        public Criteria andRepayPeriodEqualTo(Integer value) {
            addCriterion("repay_period =", value, "repayPeriod");
            return (Criteria) this;
        }

        public Criteria andRepayPeriodNotEqualTo(Integer value) {
            addCriterion("repay_period <>", value, "repayPeriod");
            return (Criteria) this;
        }

        public Criteria andRepayPeriodGreaterThan(Integer value) {
            addCriterion("repay_period >", value, "repayPeriod");
            return (Criteria) this;
        }

        public Criteria andRepayPeriodGreaterThanOrEqualTo(Integer value) {
            addCriterion("repay_period >=", value, "repayPeriod");
            return (Criteria) this;
        }

        public Criteria andRepayPeriodLessThan(Integer value) {
            addCriterion("repay_period <", value, "repayPeriod");
            return (Criteria) this;
        }

        public Criteria andRepayPeriodLessThanOrEqualTo(Integer value) {
            addCriterion("repay_period <=", value, "repayPeriod");
            return (Criteria) this;
        }

        public Criteria andRepayPeriodIn(List<Integer> values) {
            addCriterion("repay_period in", values, "repayPeriod");
            return (Criteria) this;
        }

        public Criteria andRepayPeriodNotIn(List<Integer> values) {
            addCriterion("repay_period not in", values, "repayPeriod");
            return (Criteria) this;
        }

        public Criteria andRepayPeriodBetween(Integer value1, Integer value2) {
            addCriterion("repay_period between", value1, value2, "repayPeriod");
            return (Criteria) this;
        }

        public Criteria andRepayPeriodNotBetween(Integer value1, Integer value2) {
            addCriterion("repay_period not between", value1, value2, "repayPeriod");
            return (Criteria) this;
        }

        public Criteria andRepayTimeIsNull() {
            addCriterion("repay_time is null");
            return (Criteria) this;
        }

        public Criteria andRepayTimeIsNotNull() {
            addCriterion("repay_time is not null");
            return (Criteria) this;
        }

        public Criteria andRepayTimeEqualTo(String value) {
            addCriterion("repay_time =", value, "repayTime");
            return (Criteria) this;
        }

        public Criteria andRepayTimeNotEqualTo(String value) {
            addCriterion("repay_time <>", value, "repayTime");
            return (Criteria) this;
        }

        public Criteria andRepayTimeGreaterThan(String value) {
            addCriterion("repay_time >", value, "repayTime");
            return (Criteria) this;
        }

        public Criteria andRepayTimeGreaterThanOrEqualTo(String value) {
            addCriterion("repay_time >=", value, "repayTime");
            return (Criteria) this;
        }

        public Criteria andRepayTimeLessThan(String value) {
            addCriterion("repay_time <", value, "repayTime");
            return (Criteria) this;
        }

        public Criteria andRepayTimeLessThanOrEqualTo(String value) {
            addCriterion("repay_time <=", value, "repayTime");
            return (Criteria) this;
        }

        public Criteria andRepayTimeLike(String value) {
            addCriterion("repay_time like", value, "repayTime");
            return (Criteria) this;
        }

        public Criteria andRepayTimeNotLike(String value) {
            addCriterion("repay_time not like", value, "repayTime");
            return (Criteria) this;
        }

        public Criteria andRepayTimeIn(List<String> values) {
            addCriterion("repay_time in", values, "repayTime");
            return (Criteria) this;
        }

        public Criteria andRepayTimeNotIn(List<String> values) {
            addCriterion("repay_time not in", values, "repayTime");
            return (Criteria) this;
        }

        public Criteria andRepayTimeBetween(String value1, String value2) {
            addCriterion("repay_time between", value1, value2, "repayTime");
            return (Criteria) this;
        }

        public Criteria andRepayTimeNotBetween(String value1, String value2) {
            addCriterion("repay_time not between", value1, value2, "repayTime");
            return (Criteria) this;
        }

        public Criteria andRepayActionTimeIsNull() {
            addCriterion("repay_action_time is null");
            return (Criteria) this;
        }

        public Criteria andRepayActionTimeIsNotNull() {
            addCriterion("repay_action_time is not null");
            return (Criteria) this;
        }

        public Criteria andRepayActionTimeEqualTo(String value) {
            addCriterion("repay_action_time =", value, "repayActionTime");
            return (Criteria) this;
        }

        public Criteria andRepayActionTimeNotEqualTo(String value) {
            addCriterion("repay_action_time <>", value, "repayActionTime");
            return (Criteria) this;
        }

        public Criteria andRepayActionTimeGreaterThan(String value) {
            addCriterion("repay_action_time >", value, "repayActionTime");
            return (Criteria) this;
        }

        public Criteria andRepayActionTimeGreaterThanOrEqualTo(String value) {
            addCriterion("repay_action_time >=", value, "repayActionTime");
            return (Criteria) this;
        }

        public Criteria andRepayActionTimeLessThan(String value) {
            addCriterion("repay_action_time <", value, "repayActionTime");
            return (Criteria) this;
        }

        public Criteria andRepayActionTimeLessThanOrEqualTo(String value) {
            addCriterion("repay_action_time <=", value, "repayActionTime");
            return (Criteria) this;
        }

        public Criteria andRepayActionTimeLike(String value) {
            addCriterion("repay_action_time like", value, "repayActionTime");
            return (Criteria) this;
        }

        public Criteria andRepayActionTimeNotLike(String value) {
            addCriterion("repay_action_time not like", value, "repayActionTime");
            return (Criteria) this;
        }

        public Criteria andRepayActionTimeIn(List<String> values) {
            addCriterion("repay_action_time in", values, "repayActionTime");
            return (Criteria) this;
        }

        public Criteria andRepayActionTimeNotIn(List<String> values) {
            addCriterion("repay_action_time not in", values, "repayActionTime");
            return (Criteria) this;
        }

        public Criteria andRepayActionTimeBetween(String value1, String value2) {
            addCriterion("repay_action_time between", value1, value2, "repayActionTime");
            return (Criteria) this;
        }

        public Criteria andRepayActionTimeNotBetween(String value1, String value2) {
            addCriterion("repay_action_time not between", value1, value2, "repayActionTime");
            return (Criteria) this;
        }

        public Criteria andRepayAccountAllIsNull() {
            addCriterion("repay_account_all is null");
            return (Criteria) this;
        }

        public Criteria andRepayAccountAllIsNotNull() {
            addCriterion("repay_account_all is not null");
            return (Criteria) this;
        }

        public Criteria andRepayAccountAllEqualTo(BigDecimal value) {
            addCriterion("repay_account_all =", value, "repayAccountAll");
            return (Criteria) this;
        }

        public Criteria andRepayAccountAllNotEqualTo(BigDecimal value) {
            addCriterion("repay_account_all <>", value, "repayAccountAll");
            return (Criteria) this;
        }

        public Criteria andRepayAccountAllGreaterThan(BigDecimal value) {
            addCriterion("repay_account_all >", value, "repayAccountAll");
            return (Criteria) this;
        }

        public Criteria andRepayAccountAllGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_account_all >=", value, "repayAccountAll");
            return (Criteria) this;
        }

        public Criteria andRepayAccountAllLessThan(BigDecimal value) {
            addCriterion("repay_account_all <", value, "repayAccountAll");
            return (Criteria) this;
        }

        public Criteria andRepayAccountAllLessThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_account_all <=", value, "repayAccountAll");
            return (Criteria) this;
        }

        public Criteria andRepayAccountAllIn(List<BigDecimal> values) {
            addCriterion("repay_account_all in", values, "repayAccountAll");
            return (Criteria) this;
        }

        public Criteria andRepayAccountAllNotIn(List<BigDecimal> values) {
            addCriterion("repay_account_all not in", values, "repayAccountAll");
            return (Criteria) this;
        }

        public Criteria andRepayAccountAllBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_account_all between", value1, value2, "repayAccountAll");
            return (Criteria) this;
        }

        public Criteria andRepayAccountAllNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_account_all not between", value1, value2, "repayAccountAll");
            return (Criteria) this;
        }

        public Criteria andRepayAccountIsNull() {
            addCriterion("repay_account is null");
            return (Criteria) this;
        }

        public Criteria andRepayAccountIsNotNull() {
            addCriterion("repay_account is not null");
            return (Criteria) this;
        }

        public Criteria andRepayAccountEqualTo(BigDecimal value) {
            addCriterion("repay_account =", value, "repayAccount");
            return (Criteria) this;
        }

        public Criteria andRepayAccountNotEqualTo(BigDecimal value) {
            addCriterion("repay_account <>", value, "repayAccount");
            return (Criteria) this;
        }

        public Criteria andRepayAccountGreaterThan(BigDecimal value) {
            addCriterion("repay_account >", value, "repayAccount");
            return (Criteria) this;
        }

        public Criteria andRepayAccountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_account >=", value, "repayAccount");
            return (Criteria) this;
        }

        public Criteria andRepayAccountLessThan(BigDecimal value) {
            addCriterion("repay_account <", value, "repayAccount");
            return (Criteria) this;
        }

        public Criteria andRepayAccountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_account <=", value, "repayAccount");
            return (Criteria) this;
        }

        public Criteria andRepayAccountIn(List<BigDecimal> values) {
            addCriterion("repay_account in", values, "repayAccount");
            return (Criteria) this;
        }

        public Criteria andRepayAccountNotIn(List<BigDecimal> values) {
            addCriterion("repay_account not in", values, "repayAccount");
            return (Criteria) this;
        }

        public Criteria andRepayAccountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_account between", value1, value2, "repayAccount");
            return (Criteria) this;
        }

        public Criteria andRepayAccountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_account not between", value1, value2, "repayAccount");
            return (Criteria) this;
        }

        public Criteria andRepayInterestIsNull() {
            addCriterion("repay_interest is null");
            return (Criteria) this;
        }

        public Criteria andRepayInterestIsNotNull() {
            addCriterion("repay_interest is not null");
            return (Criteria) this;
        }

        public Criteria andRepayInterestEqualTo(BigDecimal value) {
            addCriterion("repay_interest =", value, "repayInterest");
            return (Criteria) this;
        }

        public Criteria andRepayInterestNotEqualTo(BigDecimal value) {
            addCriterion("repay_interest <>", value, "repayInterest");
            return (Criteria) this;
        }

        public Criteria andRepayInterestGreaterThan(BigDecimal value) {
            addCriterion("repay_interest >", value, "repayInterest");
            return (Criteria) this;
        }

        public Criteria andRepayInterestGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_interest >=", value, "repayInterest");
            return (Criteria) this;
        }

        public Criteria andRepayInterestLessThan(BigDecimal value) {
            addCriterion("repay_interest <", value, "repayInterest");
            return (Criteria) this;
        }

        public Criteria andRepayInterestLessThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_interest <=", value, "repayInterest");
            return (Criteria) this;
        }

        public Criteria andRepayInterestIn(List<BigDecimal> values) {
            addCriterion("repay_interest in", values, "repayInterest");
            return (Criteria) this;
        }

        public Criteria andRepayInterestNotIn(List<BigDecimal> values) {
            addCriterion("repay_interest not in", values, "repayInterest");
            return (Criteria) this;
        }

        public Criteria andRepayInterestBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_interest between", value1, value2, "repayInterest");
            return (Criteria) this;
        }

        public Criteria andRepayInterestNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_interest not between", value1, value2, "repayInterest");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalIsNull() {
            addCriterion("repay_capital is null");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalIsNotNull() {
            addCriterion("repay_capital is not null");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalEqualTo(BigDecimal value) {
            addCriterion("repay_capital =", value, "repayCapital");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalNotEqualTo(BigDecimal value) {
            addCriterion("repay_capital <>", value, "repayCapital");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalGreaterThan(BigDecimal value) {
            addCriterion("repay_capital >", value, "repayCapital");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_capital >=", value, "repayCapital");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalLessThan(BigDecimal value) {
            addCriterion("repay_capital <", value, "repayCapital");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalLessThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_capital <=", value, "repayCapital");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalIn(List<BigDecimal> values) {
            addCriterion("repay_capital in", values, "repayCapital");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalNotIn(List<BigDecimal> values) {
            addCriterion("repay_capital not in", values, "repayCapital");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_capital between", value1, value2, "repayCapital");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_capital not between", value1, value2, "repayCapital");
            return (Criteria) this;
        }

        public Criteria andRepayAccountYesIsNull() {
            addCriterion("repay_account_yes is null");
            return (Criteria) this;
        }

        public Criteria andRepayAccountYesIsNotNull() {
            addCriterion("repay_account_yes is not null");
            return (Criteria) this;
        }

        public Criteria andRepayAccountYesEqualTo(BigDecimal value) {
            addCriterion("repay_account_yes =", value, "repayAccountYes");
            return (Criteria) this;
        }

        public Criteria andRepayAccountYesNotEqualTo(BigDecimal value) {
            addCriterion("repay_account_yes <>", value, "repayAccountYes");
            return (Criteria) this;
        }

        public Criteria andRepayAccountYesGreaterThan(BigDecimal value) {
            addCriterion("repay_account_yes >", value, "repayAccountYes");
            return (Criteria) this;
        }

        public Criteria andRepayAccountYesGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_account_yes >=", value, "repayAccountYes");
            return (Criteria) this;
        }

        public Criteria andRepayAccountYesLessThan(BigDecimal value) {
            addCriterion("repay_account_yes <", value, "repayAccountYes");
            return (Criteria) this;
        }

        public Criteria andRepayAccountYesLessThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_account_yes <=", value, "repayAccountYes");
            return (Criteria) this;
        }

        public Criteria andRepayAccountYesIn(List<BigDecimal> values) {
            addCriterion("repay_account_yes in", values, "repayAccountYes");
            return (Criteria) this;
        }

        public Criteria andRepayAccountYesNotIn(List<BigDecimal> values) {
            addCriterion("repay_account_yes not in", values, "repayAccountYes");
            return (Criteria) this;
        }

        public Criteria andRepayAccountYesBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_account_yes between", value1, value2, "repayAccountYes");
            return (Criteria) this;
        }

        public Criteria andRepayAccountYesNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_account_yes not between", value1, value2, "repayAccountYes");
            return (Criteria) this;
        }

        public Criteria andRepayInterestYesIsNull() {
            addCriterion("repay_interest_yes is null");
            return (Criteria) this;
        }

        public Criteria andRepayInterestYesIsNotNull() {
            addCriterion("repay_interest_yes is not null");
            return (Criteria) this;
        }

        public Criteria andRepayInterestYesEqualTo(BigDecimal value) {
            addCriterion("repay_interest_yes =", value, "repayInterestYes");
            return (Criteria) this;
        }

        public Criteria andRepayInterestYesNotEqualTo(BigDecimal value) {
            addCriterion("repay_interest_yes <>", value, "repayInterestYes");
            return (Criteria) this;
        }

        public Criteria andRepayInterestYesGreaterThan(BigDecimal value) {
            addCriterion("repay_interest_yes >", value, "repayInterestYes");
            return (Criteria) this;
        }

        public Criteria andRepayInterestYesGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_interest_yes >=", value, "repayInterestYes");
            return (Criteria) this;
        }

        public Criteria andRepayInterestYesLessThan(BigDecimal value) {
            addCriterion("repay_interest_yes <", value, "repayInterestYes");
            return (Criteria) this;
        }

        public Criteria andRepayInterestYesLessThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_interest_yes <=", value, "repayInterestYes");
            return (Criteria) this;
        }

        public Criteria andRepayInterestYesIn(List<BigDecimal> values) {
            addCriterion("repay_interest_yes in", values, "repayInterestYes");
            return (Criteria) this;
        }

        public Criteria andRepayInterestYesNotIn(List<BigDecimal> values) {
            addCriterion("repay_interest_yes not in", values, "repayInterestYes");
            return (Criteria) this;
        }

        public Criteria andRepayInterestYesBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_interest_yes between", value1, value2, "repayInterestYes");
            return (Criteria) this;
        }

        public Criteria andRepayInterestYesNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_interest_yes not between", value1, value2, "repayInterestYes");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalYesIsNull() {
            addCriterion("repay_capital_yes is null");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalYesIsNotNull() {
            addCriterion("repay_capital_yes is not null");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalYesEqualTo(BigDecimal value) {
            addCriterion("repay_capital_yes =", value, "repayCapitalYes");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalYesNotEqualTo(BigDecimal value) {
            addCriterion("repay_capital_yes <>", value, "repayCapitalYes");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalYesGreaterThan(BigDecimal value) {
            addCriterion("repay_capital_yes >", value, "repayCapitalYes");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalYesGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_capital_yes >=", value, "repayCapitalYes");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalYesLessThan(BigDecimal value) {
            addCriterion("repay_capital_yes <", value, "repayCapitalYes");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalYesLessThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_capital_yes <=", value, "repayCapitalYes");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalYesIn(List<BigDecimal> values) {
            addCriterion("repay_capital_yes in", values, "repayCapitalYes");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalYesNotIn(List<BigDecimal> values) {
            addCriterion("repay_capital_yes not in", values, "repayCapitalYes");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalYesBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_capital_yes between", value1, value2, "repayCapitalYes");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalYesNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_capital_yes not between", value1, value2, "repayCapitalYes");
            return (Criteria) this;
        }

        public Criteria andRepayAccountWaitIsNull() {
            addCriterion("repay_account_wait is null");
            return (Criteria) this;
        }

        public Criteria andRepayAccountWaitIsNotNull() {
            addCriterion("repay_account_wait is not null");
            return (Criteria) this;
        }

        public Criteria andRepayAccountWaitEqualTo(BigDecimal value) {
            addCriterion("repay_account_wait =", value, "repayAccountWait");
            return (Criteria) this;
        }

        public Criteria andRepayAccountWaitNotEqualTo(BigDecimal value) {
            addCriterion("repay_account_wait <>", value, "repayAccountWait");
            return (Criteria) this;
        }

        public Criteria andRepayAccountWaitGreaterThan(BigDecimal value) {
            addCriterion("repay_account_wait >", value, "repayAccountWait");
            return (Criteria) this;
        }

        public Criteria andRepayAccountWaitGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_account_wait >=", value, "repayAccountWait");
            return (Criteria) this;
        }

        public Criteria andRepayAccountWaitLessThan(BigDecimal value) {
            addCriterion("repay_account_wait <", value, "repayAccountWait");
            return (Criteria) this;
        }

        public Criteria andRepayAccountWaitLessThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_account_wait <=", value, "repayAccountWait");
            return (Criteria) this;
        }

        public Criteria andRepayAccountWaitIn(List<BigDecimal> values) {
            addCriterion("repay_account_wait in", values, "repayAccountWait");
            return (Criteria) this;
        }

        public Criteria andRepayAccountWaitNotIn(List<BigDecimal> values) {
            addCriterion("repay_account_wait not in", values, "repayAccountWait");
            return (Criteria) this;
        }

        public Criteria andRepayAccountWaitBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_account_wait between", value1, value2, "repayAccountWait");
            return (Criteria) this;
        }

        public Criteria andRepayAccountWaitNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_account_wait not between", value1, value2, "repayAccountWait");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalWaitIsNull() {
            addCriterion("repay_capital_wait is null");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalWaitIsNotNull() {
            addCriterion("repay_capital_wait is not null");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalWaitEqualTo(BigDecimal value) {
            addCriterion("repay_capital_wait =", value, "repayCapitalWait");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalWaitNotEqualTo(BigDecimal value) {
            addCriterion("repay_capital_wait <>", value, "repayCapitalWait");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalWaitGreaterThan(BigDecimal value) {
            addCriterion("repay_capital_wait >", value, "repayCapitalWait");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalWaitGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_capital_wait >=", value, "repayCapitalWait");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalWaitLessThan(BigDecimal value) {
            addCriterion("repay_capital_wait <", value, "repayCapitalWait");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalWaitLessThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_capital_wait <=", value, "repayCapitalWait");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalWaitIn(List<BigDecimal> values) {
            addCriterion("repay_capital_wait in", values, "repayCapitalWait");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalWaitNotIn(List<BigDecimal> values) {
            addCriterion("repay_capital_wait not in", values, "repayCapitalWait");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalWaitBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_capital_wait between", value1, value2, "repayCapitalWait");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalWaitNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_capital_wait not between", value1, value2, "repayCapitalWait");
            return (Criteria) this;
        }

        public Criteria andRepayInterestWaitIsNull() {
            addCriterion("repay_interest_wait is null");
            return (Criteria) this;
        }

        public Criteria andRepayInterestWaitIsNotNull() {
            addCriterion("repay_interest_wait is not null");
            return (Criteria) this;
        }

        public Criteria andRepayInterestWaitEqualTo(BigDecimal value) {
            addCriterion("repay_interest_wait =", value, "repayInterestWait");
            return (Criteria) this;
        }

        public Criteria andRepayInterestWaitNotEqualTo(BigDecimal value) {
            addCriterion("repay_interest_wait <>", value, "repayInterestWait");
            return (Criteria) this;
        }

        public Criteria andRepayInterestWaitGreaterThan(BigDecimal value) {
            addCriterion("repay_interest_wait >", value, "repayInterestWait");
            return (Criteria) this;
        }

        public Criteria andRepayInterestWaitGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_interest_wait >=", value, "repayInterestWait");
            return (Criteria) this;
        }

        public Criteria andRepayInterestWaitLessThan(BigDecimal value) {
            addCriterion("repay_interest_wait <", value, "repayInterestWait");
            return (Criteria) this;
        }

        public Criteria andRepayInterestWaitLessThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_interest_wait <=", value, "repayInterestWait");
            return (Criteria) this;
        }

        public Criteria andRepayInterestWaitIn(List<BigDecimal> values) {
            addCriterion("repay_interest_wait in", values, "repayInterestWait");
            return (Criteria) this;
        }

        public Criteria andRepayInterestWaitNotIn(List<BigDecimal> values) {
            addCriterion("repay_interest_wait not in", values, "repayInterestWait");
            return (Criteria) this;
        }

        public Criteria andRepayInterestWaitBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_interest_wait between", value1, value2, "repayInterestWait");
            return (Criteria) this;
        }

        public Criteria andRepayInterestWaitNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_interest_wait not between", value1, value2, "repayInterestWait");
            return (Criteria) this;
        }

        public Criteria andAdvanceStatusIsNull() {
            addCriterion("advance_status is null");
            return (Criteria) this;
        }

        public Criteria andAdvanceStatusIsNotNull() {
            addCriterion("advance_status is not null");
            return (Criteria) this;
        }

        public Criteria andAdvanceStatusEqualTo(Integer value) {
            addCriterion("advance_status =", value, "advanceStatus");
            return (Criteria) this;
        }

        public Criteria andAdvanceStatusNotEqualTo(Integer value) {
            addCriterion("advance_status <>", value, "advanceStatus");
            return (Criteria) this;
        }

        public Criteria andAdvanceStatusGreaterThan(Integer value) {
            addCriterion("advance_status >", value, "advanceStatus");
            return (Criteria) this;
        }

        public Criteria andAdvanceStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("advance_status >=", value, "advanceStatus");
            return (Criteria) this;
        }

        public Criteria andAdvanceStatusLessThan(Integer value) {
            addCriterion("advance_status <", value, "advanceStatus");
            return (Criteria) this;
        }

        public Criteria andAdvanceStatusLessThanOrEqualTo(Integer value) {
            addCriterion("advance_status <=", value, "advanceStatus");
            return (Criteria) this;
        }

        public Criteria andAdvanceStatusIn(List<Integer> values) {
            addCriterion("advance_status in", values, "advanceStatus");
            return (Criteria) this;
        }

        public Criteria andAdvanceStatusNotIn(List<Integer> values) {
            addCriterion("advance_status not in", values, "advanceStatus");
            return (Criteria) this;
        }

        public Criteria andAdvanceStatusBetween(Integer value1, Integer value2) {
            addCriterion("advance_status between", value1, value2, "advanceStatus");
            return (Criteria) this;
        }

        public Criteria andAdvanceStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("advance_status not between", value1, value2, "advanceStatus");
            return (Criteria) this;
        }

        public Criteria andAdvanceDaysIsNull() {
            addCriterion("advance_days is null");
            return (Criteria) this;
        }

        public Criteria andAdvanceDaysIsNotNull() {
            addCriterion("advance_days is not null");
            return (Criteria) this;
        }

        public Criteria andAdvanceDaysEqualTo(Integer value) {
            addCriterion("advance_days =", value, "advanceDays");
            return (Criteria) this;
        }

        public Criteria andAdvanceDaysNotEqualTo(Integer value) {
            addCriterion("advance_days <>", value, "advanceDays");
            return (Criteria) this;
        }

        public Criteria andAdvanceDaysGreaterThan(Integer value) {
            addCriterion("advance_days >", value, "advanceDays");
            return (Criteria) this;
        }

        public Criteria andAdvanceDaysGreaterThanOrEqualTo(Integer value) {
            addCriterion("advance_days >=", value, "advanceDays");
            return (Criteria) this;
        }

        public Criteria andAdvanceDaysLessThan(Integer value) {
            addCriterion("advance_days <", value, "advanceDays");
            return (Criteria) this;
        }

        public Criteria andAdvanceDaysLessThanOrEqualTo(Integer value) {
            addCriterion("advance_days <=", value, "advanceDays");
            return (Criteria) this;
        }

        public Criteria andAdvanceDaysIn(List<Integer> values) {
            addCriterion("advance_days in", values, "advanceDays");
            return (Criteria) this;
        }

        public Criteria andAdvanceDaysNotIn(List<Integer> values) {
            addCriterion("advance_days not in", values, "advanceDays");
            return (Criteria) this;
        }

        public Criteria andAdvanceDaysBetween(Integer value1, Integer value2) {
            addCriterion("advance_days between", value1, value2, "advanceDays");
            return (Criteria) this;
        }

        public Criteria andAdvanceDaysNotBetween(Integer value1, Integer value2) {
            addCriterion("advance_days not between", value1, value2, "advanceDays");
            return (Criteria) this;
        }

        public Criteria andAdvanceInterestIsNull() {
            addCriterion("advance_interest is null");
            return (Criteria) this;
        }

        public Criteria andAdvanceInterestIsNotNull() {
            addCriterion("advance_interest is not null");
            return (Criteria) this;
        }

        public Criteria andAdvanceInterestEqualTo(BigDecimal value) {
            addCriterion("advance_interest =", value, "advanceInterest");
            return (Criteria) this;
        }

        public Criteria andAdvanceInterestNotEqualTo(BigDecimal value) {
            addCriterion("advance_interest <>", value, "advanceInterest");
            return (Criteria) this;
        }

        public Criteria andAdvanceInterestGreaterThan(BigDecimal value) {
            addCriterion("advance_interest >", value, "advanceInterest");
            return (Criteria) this;
        }

        public Criteria andAdvanceInterestGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("advance_interest >=", value, "advanceInterest");
            return (Criteria) this;
        }

        public Criteria andAdvanceInterestLessThan(BigDecimal value) {
            addCriterion("advance_interest <", value, "advanceInterest");
            return (Criteria) this;
        }

        public Criteria andAdvanceInterestLessThanOrEqualTo(BigDecimal value) {
            addCriterion("advance_interest <=", value, "advanceInterest");
            return (Criteria) this;
        }

        public Criteria andAdvanceInterestIn(List<BigDecimal> values) {
            addCriterion("advance_interest in", values, "advanceInterest");
            return (Criteria) this;
        }

        public Criteria andAdvanceInterestNotIn(List<BigDecimal> values) {
            addCriterion("advance_interest not in", values, "advanceInterest");
            return (Criteria) this;
        }

        public Criteria andAdvanceInterestBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("advance_interest between", value1, value2, "advanceInterest");
            return (Criteria) this;
        }

        public Criteria andAdvanceInterestNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("advance_interest not between", value1, value2, "advanceInterest");
            return (Criteria) this;
        }

        public Criteria andDelayDaysIsNull() {
            addCriterion("delay_days is null");
            return (Criteria) this;
        }

        public Criteria andDelayDaysIsNotNull() {
            addCriterion("delay_days is not null");
            return (Criteria) this;
        }

        public Criteria andDelayDaysEqualTo(Integer value) {
            addCriterion("delay_days =", value, "delayDays");
            return (Criteria) this;
        }

        public Criteria andDelayDaysNotEqualTo(Integer value) {
            addCriterion("delay_days <>", value, "delayDays");
            return (Criteria) this;
        }

        public Criteria andDelayDaysGreaterThan(Integer value) {
            addCriterion("delay_days >", value, "delayDays");
            return (Criteria) this;
        }

        public Criteria andDelayDaysGreaterThanOrEqualTo(Integer value) {
            addCriterion("delay_days >=", value, "delayDays");
            return (Criteria) this;
        }

        public Criteria andDelayDaysLessThan(Integer value) {
            addCriterion("delay_days <", value, "delayDays");
            return (Criteria) this;
        }

        public Criteria andDelayDaysLessThanOrEqualTo(Integer value) {
            addCriterion("delay_days <=", value, "delayDays");
            return (Criteria) this;
        }

        public Criteria andDelayDaysIn(List<Integer> values) {
            addCriterion("delay_days in", values, "delayDays");
            return (Criteria) this;
        }

        public Criteria andDelayDaysNotIn(List<Integer> values) {
            addCriterion("delay_days not in", values, "delayDays");
            return (Criteria) this;
        }

        public Criteria andDelayDaysBetween(Integer value1, Integer value2) {
            addCriterion("delay_days between", value1, value2, "delayDays");
            return (Criteria) this;
        }

        public Criteria andDelayDaysNotBetween(Integer value1, Integer value2) {
            addCriterion("delay_days not between", value1, value2, "delayDays");
            return (Criteria) this;
        }

        public Criteria andDelayInterestIsNull() {
            addCriterion("delay_interest is null");
            return (Criteria) this;
        }

        public Criteria andDelayInterestIsNotNull() {
            addCriterion("delay_interest is not null");
            return (Criteria) this;
        }

        public Criteria andDelayInterestEqualTo(BigDecimal value) {
            addCriterion("delay_interest =", value, "delayInterest");
            return (Criteria) this;
        }

        public Criteria andDelayInterestNotEqualTo(BigDecimal value) {
            addCriterion("delay_interest <>", value, "delayInterest");
            return (Criteria) this;
        }

        public Criteria andDelayInterestGreaterThan(BigDecimal value) {
            addCriterion("delay_interest >", value, "delayInterest");
            return (Criteria) this;
        }

        public Criteria andDelayInterestGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("delay_interest >=", value, "delayInterest");
            return (Criteria) this;
        }

        public Criteria andDelayInterestLessThan(BigDecimal value) {
            addCriterion("delay_interest <", value, "delayInterest");
            return (Criteria) this;
        }

        public Criteria andDelayInterestLessThanOrEqualTo(BigDecimal value) {
            addCriterion("delay_interest <=", value, "delayInterest");
            return (Criteria) this;
        }

        public Criteria andDelayInterestIn(List<BigDecimal> values) {
            addCriterion("delay_interest in", values, "delayInterest");
            return (Criteria) this;
        }

        public Criteria andDelayInterestNotIn(List<BigDecimal> values) {
            addCriterion("delay_interest not in", values, "delayInterest");
            return (Criteria) this;
        }

        public Criteria andDelayInterestBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("delay_interest between", value1, value2, "delayInterest");
            return (Criteria) this;
        }

        public Criteria andDelayInterestNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("delay_interest not between", value1, value2, "delayInterest");
            return (Criteria) this;
        }

        public Criteria andLateDaysIsNull() {
            addCriterion("late_days is null");
            return (Criteria) this;
        }

        public Criteria andLateDaysIsNotNull() {
            addCriterion("late_days is not null");
            return (Criteria) this;
        }

        public Criteria andLateDaysEqualTo(Integer value) {
            addCriterion("late_days =", value, "lateDays");
            return (Criteria) this;
        }

        public Criteria andLateDaysNotEqualTo(Integer value) {
            addCriterion("late_days <>", value, "lateDays");
            return (Criteria) this;
        }

        public Criteria andLateDaysGreaterThan(Integer value) {
            addCriterion("late_days >", value, "lateDays");
            return (Criteria) this;
        }

        public Criteria andLateDaysGreaterThanOrEqualTo(Integer value) {
            addCriterion("late_days >=", value, "lateDays");
            return (Criteria) this;
        }

        public Criteria andLateDaysLessThan(Integer value) {
            addCriterion("late_days <", value, "lateDays");
            return (Criteria) this;
        }

        public Criteria andLateDaysLessThanOrEqualTo(Integer value) {
            addCriterion("late_days <=", value, "lateDays");
            return (Criteria) this;
        }

        public Criteria andLateDaysIn(List<Integer> values) {
            addCriterion("late_days in", values, "lateDays");
            return (Criteria) this;
        }

        public Criteria andLateDaysNotIn(List<Integer> values) {
            addCriterion("late_days not in", values, "lateDays");
            return (Criteria) this;
        }

        public Criteria andLateDaysBetween(Integer value1, Integer value2) {
            addCriterion("late_days between", value1, value2, "lateDays");
            return (Criteria) this;
        }

        public Criteria andLateDaysNotBetween(Integer value1, Integer value2) {
            addCriterion("late_days not between", value1, value2, "lateDays");
            return (Criteria) this;
        }

        public Criteria andLateInterestIsNull() {
            addCriterion("late_interest is null");
            return (Criteria) this;
        }

        public Criteria andLateInterestIsNotNull() {
            addCriterion("late_interest is not null");
            return (Criteria) this;
        }

        public Criteria andLateInterestEqualTo(BigDecimal value) {
            addCriterion("late_interest =", value, "lateInterest");
            return (Criteria) this;
        }

        public Criteria andLateInterestNotEqualTo(BigDecimal value) {
            addCriterion("late_interest <>", value, "lateInterest");
            return (Criteria) this;
        }

        public Criteria andLateInterestGreaterThan(BigDecimal value) {
            addCriterion("late_interest >", value, "lateInterest");
            return (Criteria) this;
        }

        public Criteria andLateInterestGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("late_interest >=", value, "lateInterest");
            return (Criteria) this;
        }

        public Criteria andLateInterestLessThan(BigDecimal value) {
            addCriterion("late_interest <", value, "lateInterest");
            return (Criteria) this;
        }

        public Criteria andLateInterestLessThanOrEqualTo(BigDecimal value) {
            addCriterion("late_interest <=", value, "lateInterest");
            return (Criteria) this;
        }

        public Criteria andLateInterestIn(List<BigDecimal> values) {
            addCriterion("late_interest in", values, "lateInterest");
            return (Criteria) this;
        }

        public Criteria andLateInterestNotIn(List<BigDecimal> values) {
            addCriterion("late_interest not in", values, "lateInterest");
            return (Criteria) this;
        }

        public Criteria andLateInterestBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("late_interest between", value1, value2, "lateInterest");
            return (Criteria) this;
        }

        public Criteria andLateInterestNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("late_interest not between", value1, value2, "lateInterest");
            return (Criteria) this;
        }

        public Criteria andDelayRemarkIsNull() {
            addCriterion("delay_remark is null");
            return (Criteria) this;
        }

        public Criteria andDelayRemarkIsNotNull() {
            addCriterion("delay_remark is not null");
            return (Criteria) this;
        }

        public Criteria andDelayRemarkEqualTo(String value) {
            addCriterion("delay_remark =", value, "delayRemark");
            return (Criteria) this;
        }

        public Criteria andDelayRemarkNotEqualTo(String value) {
            addCriterion("delay_remark <>", value, "delayRemark");
            return (Criteria) this;
        }

        public Criteria andDelayRemarkGreaterThan(String value) {
            addCriterion("delay_remark >", value, "delayRemark");
            return (Criteria) this;
        }

        public Criteria andDelayRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("delay_remark >=", value, "delayRemark");
            return (Criteria) this;
        }

        public Criteria andDelayRemarkLessThan(String value) {
            addCriterion("delay_remark <", value, "delayRemark");
            return (Criteria) this;
        }

        public Criteria andDelayRemarkLessThanOrEqualTo(String value) {
            addCriterion("delay_remark <=", value, "delayRemark");
            return (Criteria) this;
        }

        public Criteria andDelayRemarkLike(String value) {
            addCriterion("delay_remark like", value, "delayRemark");
            return (Criteria) this;
        }

        public Criteria andDelayRemarkNotLike(String value) {
            addCriterion("delay_remark not like", value, "delayRemark");
            return (Criteria) this;
        }

        public Criteria andDelayRemarkIn(List<String> values) {
            addCriterion("delay_remark in", values, "delayRemark");
            return (Criteria) this;
        }

        public Criteria andDelayRemarkNotIn(List<String> values) {
            addCriterion("delay_remark not in", values, "delayRemark");
            return (Criteria) this;
        }

        public Criteria andDelayRemarkBetween(String value1, String value2) {
            addCriterion("delay_remark between", value1, value2, "delayRemark");
            return (Criteria) this;
        }

        public Criteria andDelayRemarkNotBetween(String value1, String value2) {
            addCriterion("delay_remark not between", value1, value2, "delayRemark");
            return (Criteria) this;
        }

        public Criteria andAddipIsNull() {
            addCriterion("addip is null");
            return (Criteria) this;
        }

        public Criteria andAddipIsNotNull() {
            addCriterion("addip is not null");
            return (Criteria) this;
        }

        public Criteria andAddipEqualTo(String value) {
            addCriterion("addip =", value, "addip");
            return (Criteria) this;
        }

        public Criteria andAddipNotEqualTo(String value) {
            addCriterion("addip <>", value, "addip");
            return (Criteria) this;
        }

        public Criteria andAddipGreaterThan(String value) {
            addCriterion("addip >", value, "addip");
            return (Criteria) this;
        }

        public Criteria andAddipGreaterThanOrEqualTo(String value) {
            addCriterion("addip >=", value, "addip");
            return (Criteria) this;
        }

        public Criteria andAddipLessThan(String value) {
            addCriterion("addip <", value, "addip");
            return (Criteria) this;
        }

        public Criteria andAddipLessThanOrEqualTo(String value) {
            addCriterion("addip <=", value, "addip");
            return (Criteria) this;
        }

        public Criteria andAddipLike(String value) {
            addCriterion("addip like", value, "addip");
            return (Criteria) this;
        }

        public Criteria andAddipNotLike(String value) {
            addCriterion("addip not like", value, "addip");
            return (Criteria) this;
        }

        public Criteria andAddipIn(List<String> values) {
            addCriterion("addip in", values, "addip");
            return (Criteria) this;
        }

        public Criteria andAddipNotIn(List<String> values) {
            addCriterion("addip not in", values, "addip");
            return (Criteria) this;
        }

        public Criteria andAddipBetween(String value1, String value2) {
            addCriterion("addip between", value1, value2, "addip");
            return (Criteria) this;
        }

        public Criteria andAddipNotBetween(String value1, String value2) {
            addCriterion("addip not between", value1, value2, "addip");
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

        public Criteria andCreateTimeEqualTo(Integer value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Integer value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Integer value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Integer value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Integer value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Integer> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Integer> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Integer value1, Integer value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
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

        public Criteria andCreateUserNameIsNull() {
            addCriterion("create_user_name is null");
            return (Criteria) this;
        }

        public Criteria andCreateUserNameIsNotNull() {
            addCriterion("create_user_name is not null");
            return (Criteria) this;
        }

        public Criteria andCreateUserNameEqualTo(String value) {
            addCriterion("create_user_name =", value, "createUserName");
            return (Criteria) this;
        }

        public Criteria andCreateUserNameNotEqualTo(String value) {
            addCriterion("create_user_name <>", value, "createUserName");
            return (Criteria) this;
        }

        public Criteria andCreateUserNameGreaterThan(String value) {
            addCriterion("create_user_name >", value, "createUserName");
            return (Criteria) this;
        }

        public Criteria andCreateUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("create_user_name >=", value, "createUserName");
            return (Criteria) this;
        }

        public Criteria andCreateUserNameLessThan(String value) {
            addCriterion("create_user_name <", value, "createUserName");
            return (Criteria) this;
        }

        public Criteria andCreateUserNameLessThanOrEqualTo(String value) {
            addCriterion("create_user_name <=", value, "createUserName");
            return (Criteria) this;
        }

        public Criteria andCreateUserNameLike(String value) {
            addCriterion("create_user_name like", value, "createUserName");
            return (Criteria) this;
        }

        public Criteria andCreateUserNameNotLike(String value) {
            addCriterion("create_user_name not like", value, "createUserName");
            return (Criteria) this;
        }

        public Criteria andCreateUserNameIn(List<String> values) {
            addCriterion("create_user_name in", values, "createUserName");
            return (Criteria) this;
        }

        public Criteria andCreateUserNameNotIn(List<String> values) {
            addCriterion("create_user_name not in", values, "createUserName");
            return (Criteria) this;
        }

        public Criteria andCreateUserNameBetween(String value1, String value2) {
            addCriterion("create_user_name between", value1, value2, "createUserName");
            return (Criteria) this;
        }

        public Criteria andCreateUserNameNotBetween(String value1, String value2) {
            addCriterion("create_user_name not between", value1, value2, "createUserName");
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

        public Criteria andUpdateTimeEqualTo(Integer value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Integer value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Integer value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Integer value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Integer value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Integer> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Integer> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Integer value1, Integer value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
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

        public Criteria andUpdateUserNameIsNull() {
            addCriterion("update_user_name is null");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNameIsNotNull() {
            addCriterion("update_user_name is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNameEqualTo(String value) {
            addCriterion("update_user_name =", value, "updateUserName");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNameNotEqualTo(String value) {
            addCriterion("update_user_name <>", value, "updateUserName");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNameGreaterThan(String value) {
            addCriterion("update_user_name >", value, "updateUserName");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("update_user_name >=", value, "updateUserName");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNameLessThan(String value) {
            addCriterion("update_user_name <", value, "updateUserName");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNameLessThanOrEqualTo(String value) {
            addCriterion("update_user_name <=", value, "updateUserName");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNameLike(String value) {
            addCriterion("update_user_name like", value, "updateUserName");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNameNotLike(String value) {
            addCriterion("update_user_name not like", value, "updateUserName");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNameIn(List<String> values) {
            addCriterion("update_user_name in", values, "updateUserName");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNameNotIn(List<String> values) {
            addCriterion("update_user_name not in", values, "updateUserName");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNameBetween(String value1, String value2) {
            addCriterion("update_user_name between", value1, value2, "updateUserName");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNameNotBetween(String value1, String value2) {
            addCriterion("update_user_name not between", value1, value2, "updateUserName");
            return (Criteria) this;
        }

        public Criteria andRepayMoneySourceIsNull() {
            addCriterion("repay_money_source is null");
            return (Criteria) this;
        }

        public Criteria andRepayMoneySourceIsNotNull() {
            addCriterion("repay_money_source is not null");
            return (Criteria) this;
        }

        public Criteria andRepayMoneySourceEqualTo(Integer value) {
            addCriterion("repay_money_source =", value, "repayMoneySource");
            return (Criteria) this;
        }

        public Criteria andRepayMoneySourceNotEqualTo(Integer value) {
            addCriterion("repay_money_source <>", value, "repayMoneySource");
            return (Criteria) this;
        }

        public Criteria andRepayMoneySourceGreaterThan(Integer value) {
            addCriterion("repay_money_source >", value, "repayMoneySource");
            return (Criteria) this;
        }

        public Criteria andRepayMoneySourceGreaterThanOrEqualTo(Integer value) {
            addCriterion("repay_money_source >=", value, "repayMoneySource");
            return (Criteria) this;
        }

        public Criteria andRepayMoneySourceLessThan(Integer value) {
            addCriterion("repay_money_source <", value, "repayMoneySource");
            return (Criteria) this;
        }

        public Criteria andRepayMoneySourceLessThanOrEqualTo(Integer value) {
            addCriterion("repay_money_source <=", value, "repayMoneySource");
            return (Criteria) this;
        }

        public Criteria andRepayMoneySourceIn(List<Integer> values) {
            addCriterion("repay_money_source in", values, "repayMoneySource");
            return (Criteria) this;
        }

        public Criteria andRepayMoneySourceNotIn(List<Integer> values) {
            addCriterion("repay_money_source not in", values, "repayMoneySource");
            return (Criteria) this;
        }

        public Criteria andRepayMoneySourceBetween(Integer value1, Integer value2) {
            addCriterion("repay_money_source between", value1, value2, "repayMoneySource");
            return (Criteria) this;
        }

        public Criteria andRepayMoneySourceNotBetween(Integer value1, Integer value2) {
            addCriterion("repay_money_source not between", value1, value2, "repayMoneySource");
            return (Criteria) this;
        }

        public Criteria andRepayUserIdIsNull() {
            addCriterion("repay_user_id is null");
            return (Criteria) this;
        }

        public Criteria andRepayUserIdIsNotNull() {
            addCriterion("repay_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andRepayUserIdEqualTo(Integer value) {
            addCriterion("repay_user_id =", value, "repayUserId");
            return (Criteria) this;
        }

        public Criteria andRepayUserIdNotEqualTo(Integer value) {
            addCriterion("repay_user_id <>", value, "repayUserId");
            return (Criteria) this;
        }

        public Criteria andRepayUserIdGreaterThan(Integer value) {
            addCriterion("repay_user_id >", value, "repayUserId");
            return (Criteria) this;
        }

        public Criteria andRepayUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("repay_user_id >=", value, "repayUserId");
            return (Criteria) this;
        }

        public Criteria andRepayUserIdLessThan(Integer value) {
            addCriterion("repay_user_id <", value, "repayUserId");
            return (Criteria) this;
        }

        public Criteria andRepayUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("repay_user_id <=", value, "repayUserId");
            return (Criteria) this;
        }

        public Criteria andRepayUserIdIn(List<Integer> values) {
            addCriterion("repay_user_id in", values, "repayUserId");
            return (Criteria) this;
        }

        public Criteria andRepayUserIdNotIn(List<Integer> values) {
            addCriterion("repay_user_id not in", values, "repayUserId");
            return (Criteria) this;
        }

        public Criteria andRepayUserIdBetween(Integer value1, Integer value2) {
            addCriterion("repay_user_id between", value1, value2, "repayUserId");
            return (Criteria) this;
        }

        public Criteria andRepayUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("repay_user_id not between", value1, value2, "repayUserId");
            return (Criteria) this;
        }

        public Criteria andRepayUsernameIsNull() {
            addCriterion("repay_username is null");
            return (Criteria) this;
        }

        public Criteria andRepayUsernameIsNotNull() {
            addCriterion("repay_username is not null");
            return (Criteria) this;
        }

        public Criteria andRepayUsernameEqualTo(String value) {
            addCriterion("repay_username =", value, "repayUsername");
            return (Criteria) this;
        }

        public Criteria andRepayUsernameNotEqualTo(String value) {
            addCriterion("repay_username <>", value, "repayUsername");
            return (Criteria) this;
        }

        public Criteria andRepayUsernameGreaterThan(String value) {
            addCriterion("repay_username >", value, "repayUsername");
            return (Criteria) this;
        }

        public Criteria andRepayUsernameGreaterThanOrEqualTo(String value) {
            addCriterion("repay_username >=", value, "repayUsername");
            return (Criteria) this;
        }

        public Criteria andRepayUsernameLessThan(String value) {
            addCriterion("repay_username <", value, "repayUsername");
            return (Criteria) this;
        }

        public Criteria andRepayUsernameLessThanOrEqualTo(String value) {
            addCriterion("repay_username <=", value, "repayUsername");
            return (Criteria) this;
        }

        public Criteria andRepayUsernameLike(String value) {
            addCriterion("repay_username like", value, "repayUsername");
            return (Criteria) this;
        }

        public Criteria andRepayUsernameNotLike(String value) {
            addCriterion("repay_username not like", value, "repayUsername");
            return (Criteria) this;
        }

        public Criteria andRepayUsernameIn(List<String> values) {
            addCriterion("repay_username in", values, "repayUsername");
            return (Criteria) this;
        }

        public Criteria andRepayUsernameNotIn(List<String> values) {
            addCriterion("repay_username not in", values, "repayUsername");
            return (Criteria) this;
        }

        public Criteria andRepayUsernameBetween(String value1, String value2) {
            addCriterion("repay_username between", value1, value2, "repayUsername");
            return (Criteria) this;
        }

        public Criteria andRepayUsernameNotBetween(String value1, String value2) {
            addCriterion("repay_username not between", value1, value2, "repayUsername");
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