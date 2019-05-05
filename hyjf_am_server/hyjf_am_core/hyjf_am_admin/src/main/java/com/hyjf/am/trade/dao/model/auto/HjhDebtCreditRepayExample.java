package com.hyjf.am.trade.dao.model.auto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HjhDebtCreditRepayExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public HjhDebtCreditRepayExample() {
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

        public Criteria andCreditUserIdIsNull() {
            addCriterion("credit_user_id is null");
            return (Criteria) this;
        }

        public Criteria andCreditUserIdIsNotNull() {
            addCriterion("credit_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andCreditUserIdEqualTo(Integer value) {
            addCriterion("credit_user_id =", value, "creditUserId");
            return (Criteria) this;
        }

        public Criteria andCreditUserIdNotEqualTo(Integer value) {
            addCriterion("credit_user_id <>", value, "creditUserId");
            return (Criteria) this;
        }

        public Criteria andCreditUserIdGreaterThan(Integer value) {
            addCriterion("credit_user_id >", value, "creditUserId");
            return (Criteria) this;
        }

        public Criteria andCreditUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("credit_user_id >=", value, "creditUserId");
            return (Criteria) this;
        }

        public Criteria andCreditUserIdLessThan(Integer value) {
            addCriterion("credit_user_id <", value, "creditUserId");
            return (Criteria) this;
        }

        public Criteria andCreditUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("credit_user_id <=", value, "creditUserId");
            return (Criteria) this;
        }

        public Criteria andCreditUserIdIn(List<Integer> values) {
            addCriterion("credit_user_id in", values, "creditUserId");
            return (Criteria) this;
        }

        public Criteria andCreditUserIdNotIn(List<Integer> values) {
            addCriterion("credit_user_id not in", values, "creditUserId");
            return (Criteria) this;
        }

        public Criteria andCreditUserIdBetween(Integer value1, Integer value2) {
            addCriterion("credit_user_id between", value1, value2, "creditUserId");
            return (Criteria) this;
        }

        public Criteria andCreditUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("credit_user_id not between", value1, value2, "creditUserId");
            return (Criteria) this;
        }

        public Criteria andCreditUserNameIsNull() {
            addCriterion("credit_user_name is null");
            return (Criteria) this;
        }

        public Criteria andCreditUserNameIsNotNull() {
            addCriterion("credit_user_name is not null");
            return (Criteria) this;
        }

        public Criteria andCreditUserNameEqualTo(String value) {
            addCriterion("credit_user_name =", value, "creditUserName");
            return (Criteria) this;
        }

        public Criteria andCreditUserNameNotEqualTo(String value) {
            addCriterion("credit_user_name <>", value, "creditUserName");
            return (Criteria) this;
        }

        public Criteria andCreditUserNameGreaterThan(String value) {
            addCriterion("credit_user_name >", value, "creditUserName");
            return (Criteria) this;
        }

        public Criteria andCreditUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("credit_user_name >=", value, "creditUserName");
            return (Criteria) this;
        }

        public Criteria andCreditUserNameLessThan(String value) {
            addCriterion("credit_user_name <", value, "creditUserName");
            return (Criteria) this;
        }

        public Criteria andCreditUserNameLessThanOrEqualTo(String value) {
            addCriterion("credit_user_name <=", value, "creditUserName");
            return (Criteria) this;
        }

        public Criteria andCreditUserNameLike(String value) {
            addCriterion("credit_user_name like", value, "creditUserName");
            return (Criteria) this;
        }

        public Criteria andCreditUserNameNotLike(String value) {
            addCriterion("credit_user_name not like", value, "creditUserName");
            return (Criteria) this;
        }

        public Criteria andCreditUserNameIn(List<String> values) {
            addCriterion("credit_user_name in", values, "creditUserName");
            return (Criteria) this;
        }

        public Criteria andCreditUserNameNotIn(List<String> values) {
            addCriterion("credit_user_name not in", values, "creditUserName");
            return (Criteria) this;
        }

        public Criteria andCreditUserNameBetween(String value1, String value2) {
            addCriterion("credit_user_name between", value1, value2, "creditUserName");
            return (Criteria) this;
        }

        public Criteria andCreditUserNameNotBetween(String value1, String value2) {
            addCriterion("credit_user_name not between", value1, value2, "creditUserName");
            return (Criteria) this;
        }

        public Criteria andInvestOrderIdIsNull() {
            addCriterion("invest_order_id is null");
            return (Criteria) this;
        }

        public Criteria andInvestOrderIdIsNotNull() {
            addCriterion("invest_order_id is not null");
            return (Criteria) this;
        }

        public Criteria andInvestOrderIdEqualTo(String value) {
            addCriterion("invest_order_id =", value, "investOrderId");
            return (Criteria) this;
        }

        public Criteria andInvestOrderIdNotEqualTo(String value) {
            addCriterion("invest_order_id <>", value, "investOrderId");
            return (Criteria) this;
        }

        public Criteria andInvestOrderIdGreaterThan(String value) {
            addCriterion("invest_order_id >", value, "investOrderId");
            return (Criteria) this;
        }

        public Criteria andInvestOrderIdGreaterThanOrEqualTo(String value) {
            addCriterion("invest_order_id >=", value, "investOrderId");
            return (Criteria) this;
        }

        public Criteria andInvestOrderIdLessThan(String value) {
            addCriterion("invest_order_id <", value, "investOrderId");
            return (Criteria) this;
        }

        public Criteria andInvestOrderIdLessThanOrEqualTo(String value) {
            addCriterion("invest_order_id <=", value, "investOrderId");
            return (Criteria) this;
        }

        public Criteria andInvestOrderIdLike(String value) {
            addCriterion("invest_order_id like", value, "investOrderId");
            return (Criteria) this;
        }

        public Criteria andInvestOrderIdNotLike(String value) {
            addCriterion("invest_order_id not like", value, "investOrderId");
            return (Criteria) this;
        }

        public Criteria andInvestOrderIdIn(List<String> values) {
            addCriterion("invest_order_id in", values, "investOrderId");
            return (Criteria) this;
        }

        public Criteria andInvestOrderIdNotIn(List<String> values) {
            addCriterion("invest_order_id not in", values, "investOrderId");
            return (Criteria) this;
        }

        public Criteria andInvestOrderIdBetween(String value1, String value2) {
            addCriterion("invest_order_id between", value1, value2, "investOrderId");
            return (Criteria) this;
        }

        public Criteria andInvestOrderIdNotBetween(String value1, String value2) {
            addCriterion("invest_order_id not between", value1, value2, "investOrderId");
            return (Criteria) this;
        }

        public Criteria andSellOrderIdIsNull() {
            addCriterion("sell_order_id is null");
            return (Criteria) this;
        }

        public Criteria andSellOrderIdIsNotNull() {
            addCriterion("sell_order_id is not null");
            return (Criteria) this;
        }

        public Criteria andSellOrderIdEqualTo(String value) {
            addCriterion("sell_order_id =", value, "sellOrderId");
            return (Criteria) this;
        }

        public Criteria andSellOrderIdNotEqualTo(String value) {
            addCriterion("sell_order_id <>", value, "sellOrderId");
            return (Criteria) this;
        }

        public Criteria andSellOrderIdGreaterThan(String value) {
            addCriterion("sell_order_id >", value, "sellOrderId");
            return (Criteria) this;
        }

        public Criteria andSellOrderIdGreaterThanOrEqualTo(String value) {
            addCriterion("sell_order_id >=", value, "sellOrderId");
            return (Criteria) this;
        }

        public Criteria andSellOrderIdLessThan(String value) {
            addCriterion("sell_order_id <", value, "sellOrderId");
            return (Criteria) this;
        }

        public Criteria andSellOrderIdLessThanOrEqualTo(String value) {
            addCriterion("sell_order_id <=", value, "sellOrderId");
            return (Criteria) this;
        }

        public Criteria andSellOrderIdLike(String value) {
            addCriterion("sell_order_id like", value, "sellOrderId");
            return (Criteria) this;
        }

        public Criteria andSellOrderIdNotLike(String value) {
            addCriterion("sell_order_id not like", value, "sellOrderId");
            return (Criteria) this;
        }

        public Criteria andSellOrderIdIn(List<String> values) {
            addCriterion("sell_order_id in", values, "sellOrderId");
            return (Criteria) this;
        }

        public Criteria andSellOrderIdNotIn(List<String> values) {
            addCriterion("sell_order_id not in", values, "sellOrderId");
            return (Criteria) this;
        }

        public Criteria andSellOrderIdBetween(String value1, String value2) {
            addCriterion("sell_order_id between", value1, value2, "sellOrderId");
            return (Criteria) this;
        }

        public Criteria andSellOrderIdNotBetween(String value1, String value2) {
            addCriterion("sell_order_id not between", value1, value2, "sellOrderId");
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

        public Criteria andCreditNidIsNull() {
            addCriterion("credit_nid is null");
            return (Criteria) this;
        }

        public Criteria andCreditNidIsNotNull() {
            addCriterion("credit_nid is not null");
            return (Criteria) this;
        }

        public Criteria andCreditNidEqualTo(String value) {
            addCriterion("credit_nid =", value, "creditNid");
            return (Criteria) this;
        }

        public Criteria andCreditNidNotEqualTo(String value) {
            addCriterion("credit_nid <>", value, "creditNid");
            return (Criteria) this;
        }

        public Criteria andCreditNidGreaterThan(String value) {
            addCriterion("credit_nid >", value, "creditNid");
            return (Criteria) this;
        }

        public Criteria andCreditNidGreaterThanOrEqualTo(String value) {
            addCriterion("credit_nid >=", value, "creditNid");
            return (Criteria) this;
        }

        public Criteria andCreditNidLessThan(String value) {
            addCriterion("credit_nid <", value, "creditNid");
            return (Criteria) this;
        }

        public Criteria andCreditNidLessThanOrEqualTo(String value) {
            addCriterion("credit_nid <=", value, "creditNid");
            return (Criteria) this;
        }

        public Criteria andCreditNidLike(String value) {
            addCriterion("credit_nid like", value, "creditNid");
            return (Criteria) this;
        }

        public Criteria andCreditNidNotLike(String value) {
            addCriterion("credit_nid not like", value, "creditNid");
            return (Criteria) this;
        }

        public Criteria andCreditNidIn(List<String> values) {
            addCriterion("credit_nid in", values, "creditNid");
            return (Criteria) this;
        }

        public Criteria andCreditNidNotIn(List<String> values) {
            addCriterion("credit_nid not in", values, "creditNid");
            return (Criteria) this;
        }

        public Criteria andCreditNidBetween(String value1, String value2) {
            addCriterion("credit_nid between", value1, value2, "creditNid");
            return (Criteria) this;
        }

        public Criteria andCreditNidNotBetween(String value1, String value2) {
            addCriterion("credit_nid not between", value1, value2, "creditNid");
            return (Criteria) this;
        }

        public Criteria andAssignPlanNidIsNull() {
            addCriterion("assign_plan_nid is null");
            return (Criteria) this;
        }

        public Criteria andAssignPlanNidIsNotNull() {
            addCriterion("assign_plan_nid is not null");
            return (Criteria) this;
        }

        public Criteria andAssignPlanNidEqualTo(String value) {
            addCriterion("assign_plan_nid =", value, "assignPlanNid");
            return (Criteria) this;
        }

        public Criteria andAssignPlanNidNotEqualTo(String value) {
            addCriterion("assign_plan_nid <>", value, "assignPlanNid");
            return (Criteria) this;
        }

        public Criteria andAssignPlanNidGreaterThan(String value) {
            addCriterion("assign_plan_nid >", value, "assignPlanNid");
            return (Criteria) this;
        }

        public Criteria andAssignPlanNidGreaterThanOrEqualTo(String value) {
            addCriterion("assign_plan_nid >=", value, "assignPlanNid");
            return (Criteria) this;
        }

        public Criteria andAssignPlanNidLessThan(String value) {
            addCriterion("assign_plan_nid <", value, "assignPlanNid");
            return (Criteria) this;
        }

        public Criteria andAssignPlanNidLessThanOrEqualTo(String value) {
            addCriterion("assign_plan_nid <=", value, "assignPlanNid");
            return (Criteria) this;
        }

        public Criteria andAssignPlanNidLike(String value) {
            addCriterion("assign_plan_nid like", value, "assignPlanNid");
            return (Criteria) this;
        }

        public Criteria andAssignPlanNidNotLike(String value) {
            addCriterion("assign_plan_nid not like", value, "assignPlanNid");
            return (Criteria) this;
        }

        public Criteria andAssignPlanNidIn(List<String> values) {
            addCriterion("assign_plan_nid in", values, "assignPlanNid");
            return (Criteria) this;
        }

        public Criteria andAssignPlanNidNotIn(List<String> values) {
            addCriterion("assign_plan_nid not in", values, "assignPlanNid");
            return (Criteria) this;
        }

        public Criteria andAssignPlanNidBetween(String value1, String value2) {
            addCriterion("assign_plan_nid between", value1, value2, "assignPlanNid");
            return (Criteria) this;
        }

        public Criteria andAssignPlanNidNotBetween(String value1, String value2) {
            addCriterion("assign_plan_nid not between", value1, value2, "assignPlanNid");
            return (Criteria) this;
        }

        public Criteria andAssignPlanOrderIdIsNull() {
            addCriterion("assign_plan_order_id is null");
            return (Criteria) this;
        }

        public Criteria andAssignPlanOrderIdIsNotNull() {
            addCriterion("assign_plan_order_id is not null");
            return (Criteria) this;
        }

        public Criteria andAssignPlanOrderIdEqualTo(String value) {
            addCriterion("assign_plan_order_id =", value, "assignPlanOrderId");
            return (Criteria) this;
        }

        public Criteria andAssignPlanOrderIdNotEqualTo(String value) {
            addCriterion("assign_plan_order_id <>", value, "assignPlanOrderId");
            return (Criteria) this;
        }

        public Criteria andAssignPlanOrderIdGreaterThan(String value) {
            addCriterion("assign_plan_order_id >", value, "assignPlanOrderId");
            return (Criteria) this;
        }

        public Criteria andAssignPlanOrderIdGreaterThanOrEqualTo(String value) {
            addCriterion("assign_plan_order_id >=", value, "assignPlanOrderId");
            return (Criteria) this;
        }

        public Criteria andAssignPlanOrderIdLessThan(String value) {
            addCriterion("assign_plan_order_id <", value, "assignPlanOrderId");
            return (Criteria) this;
        }

        public Criteria andAssignPlanOrderIdLessThanOrEqualTo(String value) {
            addCriterion("assign_plan_order_id <=", value, "assignPlanOrderId");
            return (Criteria) this;
        }

        public Criteria andAssignPlanOrderIdLike(String value) {
            addCriterion("assign_plan_order_id like", value, "assignPlanOrderId");
            return (Criteria) this;
        }

        public Criteria andAssignPlanOrderIdNotLike(String value) {
            addCriterion("assign_plan_order_id not like", value, "assignPlanOrderId");
            return (Criteria) this;
        }

        public Criteria andAssignPlanOrderIdIn(List<String> values) {
            addCriterion("assign_plan_order_id in", values, "assignPlanOrderId");
            return (Criteria) this;
        }

        public Criteria andAssignPlanOrderIdNotIn(List<String> values) {
            addCriterion("assign_plan_order_id not in", values, "assignPlanOrderId");
            return (Criteria) this;
        }

        public Criteria andAssignPlanOrderIdBetween(String value1, String value2) {
            addCriterion("assign_plan_order_id between", value1, value2, "assignPlanOrderId");
            return (Criteria) this;
        }

        public Criteria andAssignPlanOrderIdNotBetween(String value1, String value2) {
            addCriterion("assign_plan_order_id not between", value1, value2, "assignPlanOrderId");
            return (Criteria) this;
        }

        public Criteria andAssignOrderIdIsNull() {
            addCriterion("assign_order_id is null");
            return (Criteria) this;
        }

        public Criteria andAssignOrderIdIsNotNull() {
            addCriterion("assign_order_id is not null");
            return (Criteria) this;
        }

        public Criteria andAssignOrderIdEqualTo(String value) {
            addCriterion("assign_order_id =", value, "assignOrderId");
            return (Criteria) this;
        }

        public Criteria andAssignOrderIdNotEqualTo(String value) {
            addCriterion("assign_order_id <>", value, "assignOrderId");
            return (Criteria) this;
        }

        public Criteria andAssignOrderIdGreaterThan(String value) {
            addCriterion("assign_order_id >", value, "assignOrderId");
            return (Criteria) this;
        }

        public Criteria andAssignOrderIdGreaterThanOrEqualTo(String value) {
            addCriterion("assign_order_id >=", value, "assignOrderId");
            return (Criteria) this;
        }

        public Criteria andAssignOrderIdLessThan(String value) {
            addCriterion("assign_order_id <", value, "assignOrderId");
            return (Criteria) this;
        }

        public Criteria andAssignOrderIdLessThanOrEqualTo(String value) {
            addCriterion("assign_order_id <=", value, "assignOrderId");
            return (Criteria) this;
        }

        public Criteria andAssignOrderIdLike(String value) {
            addCriterion("assign_order_id like", value, "assignOrderId");
            return (Criteria) this;
        }

        public Criteria andAssignOrderIdNotLike(String value) {
            addCriterion("assign_order_id not like", value, "assignOrderId");
            return (Criteria) this;
        }

        public Criteria andAssignOrderIdIn(List<String> values) {
            addCriterion("assign_order_id in", values, "assignOrderId");
            return (Criteria) this;
        }

        public Criteria andAssignOrderIdNotIn(List<String> values) {
            addCriterion("assign_order_id not in", values, "assignOrderId");
            return (Criteria) this;
        }

        public Criteria andAssignOrderIdBetween(String value1, String value2) {
            addCriterion("assign_order_id between", value1, value2, "assignOrderId");
            return (Criteria) this;
        }

        public Criteria andAssignOrderIdNotBetween(String value1, String value2) {
            addCriterion("assign_order_id not between", value1, value2, "assignOrderId");
            return (Criteria) this;
        }

        public Criteria andAuthCodeIsNull() {
            addCriterion("auth_code is null");
            return (Criteria) this;
        }

        public Criteria andAuthCodeIsNotNull() {
            addCriterion("auth_code is not null");
            return (Criteria) this;
        }

        public Criteria andAuthCodeEqualTo(String value) {
            addCriterion("auth_code =", value, "authCode");
            return (Criteria) this;
        }

        public Criteria andAuthCodeNotEqualTo(String value) {
            addCriterion("auth_code <>", value, "authCode");
            return (Criteria) this;
        }

        public Criteria andAuthCodeGreaterThan(String value) {
            addCriterion("auth_code >", value, "authCode");
            return (Criteria) this;
        }

        public Criteria andAuthCodeGreaterThanOrEqualTo(String value) {
            addCriterion("auth_code >=", value, "authCode");
            return (Criteria) this;
        }

        public Criteria andAuthCodeLessThan(String value) {
            addCriterion("auth_code <", value, "authCode");
            return (Criteria) this;
        }

        public Criteria andAuthCodeLessThanOrEqualTo(String value) {
            addCriterion("auth_code <=", value, "authCode");
            return (Criteria) this;
        }

        public Criteria andAuthCodeLike(String value) {
            addCriterion("auth_code like", value, "authCode");
            return (Criteria) this;
        }

        public Criteria andAuthCodeNotLike(String value) {
            addCriterion("auth_code not like", value, "authCode");
            return (Criteria) this;
        }

        public Criteria andAuthCodeIn(List<String> values) {
            addCriterion("auth_code in", values, "authCode");
            return (Criteria) this;
        }

        public Criteria andAuthCodeNotIn(List<String> values) {
            addCriterion("auth_code not in", values, "authCode");
            return (Criteria) this;
        }

        public Criteria andAuthCodeBetween(String value1, String value2) {
            addCriterion("auth_code between", value1, value2, "authCode");
            return (Criteria) this;
        }

        public Criteria andAuthCodeNotBetween(String value1, String value2) {
            addCriterion("auth_code not between", value1, value2, "authCode");
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

        public Criteria andAssignRepayEndTimeIsNull() {
            addCriterion("assign_repay_end_time is null");
            return (Criteria) this;
        }

        public Criteria andAssignRepayEndTimeIsNotNull() {
            addCriterion("assign_repay_end_time is not null");
            return (Criteria) this;
        }

        public Criteria andAssignRepayEndTimeEqualTo(Integer value) {
            addCriterion("assign_repay_end_time =", value, "assignRepayEndTime");
            return (Criteria) this;
        }

        public Criteria andAssignRepayEndTimeNotEqualTo(Integer value) {
            addCriterion("assign_repay_end_time <>", value, "assignRepayEndTime");
            return (Criteria) this;
        }

        public Criteria andAssignRepayEndTimeGreaterThan(Integer value) {
            addCriterion("assign_repay_end_time >", value, "assignRepayEndTime");
            return (Criteria) this;
        }

        public Criteria andAssignRepayEndTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("assign_repay_end_time >=", value, "assignRepayEndTime");
            return (Criteria) this;
        }

        public Criteria andAssignRepayEndTimeLessThan(Integer value) {
            addCriterion("assign_repay_end_time <", value, "assignRepayEndTime");
            return (Criteria) this;
        }

        public Criteria andAssignRepayEndTimeLessThanOrEqualTo(Integer value) {
            addCriterion("assign_repay_end_time <=", value, "assignRepayEndTime");
            return (Criteria) this;
        }

        public Criteria andAssignRepayEndTimeIn(List<Integer> values) {
            addCriterion("assign_repay_end_time in", values, "assignRepayEndTime");
            return (Criteria) this;
        }

        public Criteria andAssignRepayEndTimeNotIn(List<Integer> values) {
            addCriterion("assign_repay_end_time not in", values, "assignRepayEndTime");
            return (Criteria) this;
        }

        public Criteria andAssignRepayEndTimeBetween(Integer value1, Integer value2) {
            addCriterion("assign_repay_end_time between", value1, value2, "assignRepayEndTime");
            return (Criteria) this;
        }

        public Criteria andAssignRepayEndTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("assign_repay_end_time not between", value1, value2, "assignRepayEndTime");
            return (Criteria) this;
        }

        public Criteria andAssignRepayLastTimeIsNull() {
            addCriterion("assign_repay_last_time is null");
            return (Criteria) this;
        }

        public Criteria andAssignRepayLastTimeIsNotNull() {
            addCriterion("assign_repay_last_time is not null");
            return (Criteria) this;
        }

        public Criteria andAssignRepayLastTimeEqualTo(Integer value) {
            addCriterion("assign_repay_last_time =", value, "assignRepayLastTime");
            return (Criteria) this;
        }

        public Criteria andAssignRepayLastTimeNotEqualTo(Integer value) {
            addCriterion("assign_repay_last_time <>", value, "assignRepayLastTime");
            return (Criteria) this;
        }

        public Criteria andAssignRepayLastTimeGreaterThan(Integer value) {
            addCriterion("assign_repay_last_time >", value, "assignRepayLastTime");
            return (Criteria) this;
        }

        public Criteria andAssignRepayLastTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("assign_repay_last_time >=", value, "assignRepayLastTime");
            return (Criteria) this;
        }

        public Criteria andAssignRepayLastTimeLessThan(Integer value) {
            addCriterion("assign_repay_last_time <", value, "assignRepayLastTime");
            return (Criteria) this;
        }

        public Criteria andAssignRepayLastTimeLessThanOrEqualTo(Integer value) {
            addCriterion("assign_repay_last_time <=", value, "assignRepayLastTime");
            return (Criteria) this;
        }

        public Criteria andAssignRepayLastTimeIn(List<Integer> values) {
            addCriterion("assign_repay_last_time in", values, "assignRepayLastTime");
            return (Criteria) this;
        }

        public Criteria andAssignRepayLastTimeNotIn(List<Integer> values) {
            addCriterion("assign_repay_last_time not in", values, "assignRepayLastTime");
            return (Criteria) this;
        }

        public Criteria andAssignRepayLastTimeBetween(Integer value1, Integer value2) {
            addCriterion("assign_repay_last_time between", value1, value2, "assignRepayLastTime");
            return (Criteria) this;
        }

        public Criteria andAssignRepayLastTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("assign_repay_last_time not between", value1, value2, "assignRepayLastTime");
            return (Criteria) this;
        }

        public Criteria andAssignRepayNextTimeIsNull() {
            addCriterion("assign_repay_next_time is null");
            return (Criteria) this;
        }

        public Criteria andAssignRepayNextTimeIsNotNull() {
            addCriterion("assign_repay_next_time is not null");
            return (Criteria) this;
        }

        public Criteria andAssignRepayNextTimeEqualTo(Integer value) {
            addCriterion("assign_repay_next_time =", value, "assignRepayNextTime");
            return (Criteria) this;
        }

        public Criteria andAssignRepayNextTimeNotEqualTo(Integer value) {
            addCriterion("assign_repay_next_time <>", value, "assignRepayNextTime");
            return (Criteria) this;
        }

        public Criteria andAssignRepayNextTimeGreaterThan(Integer value) {
            addCriterion("assign_repay_next_time >", value, "assignRepayNextTime");
            return (Criteria) this;
        }

        public Criteria andAssignRepayNextTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("assign_repay_next_time >=", value, "assignRepayNextTime");
            return (Criteria) this;
        }

        public Criteria andAssignRepayNextTimeLessThan(Integer value) {
            addCriterion("assign_repay_next_time <", value, "assignRepayNextTime");
            return (Criteria) this;
        }

        public Criteria andAssignRepayNextTimeLessThanOrEqualTo(Integer value) {
            addCriterion("assign_repay_next_time <=", value, "assignRepayNextTime");
            return (Criteria) this;
        }

        public Criteria andAssignRepayNextTimeIn(List<Integer> values) {
            addCriterion("assign_repay_next_time in", values, "assignRepayNextTime");
            return (Criteria) this;
        }

        public Criteria andAssignRepayNextTimeNotIn(List<Integer> values) {
            addCriterion("assign_repay_next_time not in", values, "assignRepayNextTime");
            return (Criteria) this;
        }

        public Criteria andAssignRepayNextTimeBetween(Integer value1, Integer value2) {
            addCriterion("assign_repay_next_time between", value1, value2, "assignRepayNextTime");
            return (Criteria) this;
        }

        public Criteria andAssignRepayNextTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("assign_repay_next_time not between", value1, value2, "assignRepayNextTime");
            return (Criteria) this;
        }

        public Criteria andAssignRepayYesTimeIsNull() {
            addCriterion("assign_repay_yes_time is null");
            return (Criteria) this;
        }

        public Criteria andAssignRepayYesTimeIsNotNull() {
            addCriterion("assign_repay_yes_time is not null");
            return (Criteria) this;
        }

        public Criteria andAssignRepayYesTimeEqualTo(Integer value) {
            addCriterion("assign_repay_yes_time =", value, "assignRepayYesTime");
            return (Criteria) this;
        }

        public Criteria andAssignRepayYesTimeNotEqualTo(Integer value) {
            addCriterion("assign_repay_yes_time <>", value, "assignRepayYesTime");
            return (Criteria) this;
        }

        public Criteria andAssignRepayYesTimeGreaterThan(Integer value) {
            addCriterion("assign_repay_yes_time >", value, "assignRepayYesTime");
            return (Criteria) this;
        }

        public Criteria andAssignRepayYesTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("assign_repay_yes_time >=", value, "assignRepayYesTime");
            return (Criteria) this;
        }

        public Criteria andAssignRepayYesTimeLessThan(Integer value) {
            addCriterion("assign_repay_yes_time <", value, "assignRepayYesTime");
            return (Criteria) this;
        }

        public Criteria andAssignRepayYesTimeLessThanOrEqualTo(Integer value) {
            addCriterion("assign_repay_yes_time <=", value, "assignRepayYesTime");
            return (Criteria) this;
        }

        public Criteria andAssignRepayYesTimeIn(List<Integer> values) {
            addCriterion("assign_repay_yes_time in", values, "assignRepayYesTime");
            return (Criteria) this;
        }

        public Criteria andAssignRepayYesTimeNotIn(List<Integer> values) {
            addCriterion("assign_repay_yes_time not in", values, "assignRepayYesTime");
            return (Criteria) this;
        }

        public Criteria andAssignRepayYesTimeBetween(Integer value1, Integer value2) {
            addCriterion("assign_repay_yes_time between", value1, value2, "assignRepayYesTime");
            return (Criteria) this;
        }

        public Criteria andAssignRepayYesTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("assign_repay_yes_time not between", value1, value2, "assignRepayYesTime");
            return (Criteria) this;
        }

        public Criteria andAssignRepayTimeIsNull() {
            addCriterion("assign_repay_time is null");
            return (Criteria) this;
        }

        public Criteria andAssignRepayTimeIsNotNull() {
            addCriterion("assign_repay_time is not null");
            return (Criteria) this;
        }

        public Criteria andAssignRepayTimeEqualTo(Integer value) {
            addCriterion("assign_repay_time =", value, "assignRepayTime");
            return (Criteria) this;
        }

        public Criteria andAssignRepayTimeNotEqualTo(Integer value) {
            addCriterion("assign_repay_time <>", value, "assignRepayTime");
            return (Criteria) this;
        }

        public Criteria andAssignRepayTimeGreaterThan(Integer value) {
            addCriterion("assign_repay_time >", value, "assignRepayTime");
            return (Criteria) this;
        }

        public Criteria andAssignRepayTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("assign_repay_time >=", value, "assignRepayTime");
            return (Criteria) this;
        }

        public Criteria andAssignRepayTimeLessThan(Integer value) {
            addCriterion("assign_repay_time <", value, "assignRepayTime");
            return (Criteria) this;
        }

        public Criteria andAssignRepayTimeLessThanOrEqualTo(Integer value) {
            addCriterion("assign_repay_time <=", value, "assignRepayTime");
            return (Criteria) this;
        }

        public Criteria andAssignRepayTimeIn(List<Integer> values) {
            addCriterion("assign_repay_time in", values, "assignRepayTime");
            return (Criteria) this;
        }

        public Criteria andAssignRepayTimeNotIn(List<Integer> values) {
            addCriterion("assign_repay_time not in", values, "assignRepayTime");
            return (Criteria) this;
        }

        public Criteria andAssignRepayTimeBetween(Integer value1, Integer value2) {
            addCriterion("assign_repay_time between", value1, value2, "assignRepayTime");
            return (Criteria) this;
        }

        public Criteria andAssignRepayTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("assign_repay_time not between", value1, value2, "assignRepayTime");
            return (Criteria) this;
        }

        public Criteria andDelFlagIsNull() {
            addCriterion("del_flag is null");
            return (Criteria) this;
        }

        public Criteria andDelFlagIsNotNull() {
            addCriterion("del_flag is not null");
            return (Criteria) this;
        }

        public Criteria andDelFlagEqualTo(Integer value) {
            addCriterion("del_flag =", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagNotEqualTo(Integer value) {
            addCriterion("del_flag <>", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagGreaterThan(Integer value) {
            addCriterion("del_flag >", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("del_flag >=", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagLessThan(Integer value) {
            addCriterion("del_flag <", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagLessThanOrEqualTo(Integer value) {
            addCriterion("del_flag <=", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagIn(List<Integer> values) {
            addCriterion("del_flag in", values, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagNotIn(List<Integer> values) {
            addCriterion("del_flag not in", values, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagBetween(Integer value1, Integer value2) {
            addCriterion("del_flag between", value1, value2, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("del_flag not between", value1, value2, "delFlag");
            return (Criteria) this;
        }

        public Criteria andAssignRepayPeriodIsNull() {
            addCriterion("assign_repay_period is null");
            return (Criteria) this;
        }

        public Criteria andAssignRepayPeriodIsNotNull() {
            addCriterion("assign_repay_period is not null");
            return (Criteria) this;
        }

        public Criteria andAssignRepayPeriodEqualTo(Integer value) {
            addCriterion("assign_repay_period =", value, "assignRepayPeriod");
            return (Criteria) this;
        }

        public Criteria andAssignRepayPeriodNotEqualTo(Integer value) {
            addCriterion("assign_repay_period <>", value, "assignRepayPeriod");
            return (Criteria) this;
        }

        public Criteria andAssignRepayPeriodGreaterThan(Integer value) {
            addCriterion("assign_repay_period >", value, "assignRepayPeriod");
            return (Criteria) this;
        }

        public Criteria andAssignRepayPeriodGreaterThanOrEqualTo(Integer value) {
            addCriterion("assign_repay_period >=", value, "assignRepayPeriod");
            return (Criteria) this;
        }

        public Criteria andAssignRepayPeriodLessThan(Integer value) {
            addCriterion("assign_repay_period <", value, "assignRepayPeriod");
            return (Criteria) this;
        }

        public Criteria andAssignRepayPeriodLessThanOrEqualTo(Integer value) {
            addCriterion("assign_repay_period <=", value, "assignRepayPeriod");
            return (Criteria) this;
        }

        public Criteria andAssignRepayPeriodIn(List<Integer> values) {
            addCriterion("assign_repay_period in", values, "assignRepayPeriod");
            return (Criteria) this;
        }

        public Criteria andAssignRepayPeriodNotIn(List<Integer> values) {
            addCriterion("assign_repay_period not in", values, "assignRepayPeriod");
            return (Criteria) this;
        }

        public Criteria andAssignRepayPeriodBetween(Integer value1, Integer value2) {
            addCriterion("assign_repay_period between", value1, value2, "assignRepayPeriod");
            return (Criteria) this;
        }

        public Criteria andAssignRepayPeriodNotBetween(Integer value1, Integer value2) {
            addCriterion("assign_repay_period not between", value1, value2, "assignRepayPeriod");
            return (Criteria) this;
        }

        public Criteria andAssignCreateDateIsNull() {
            addCriterion("assign_create_date is null");
            return (Criteria) this;
        }

        public Criteria andAssignCreateDateIsNotNull() {
            addCriterion("assign_create_date is not null");
            return (Criteria) this;
        }

        public Criteria andAssignCreateDateEqualTo(Integer value) {
            addCriterion("assign_create_date =", value, "assignCreateDate");
            return (Criteria) this;
        }

        public Criteria andAssignCreateDateNotEqualTo(Integer value) {
            addCriterion("assign_create_date <>", value, "assignCreateDate");
            return (Criteria) this;
        }

        public Criteria andAssignCreateDateGreaterThan(Integer value) {
            addCriterion("assign_create_date >", value, "assignCreateDate");
            return (Criteria) this;
        }

        public Criteria andAssignCreateDateGreaterThanOrEqualTo(Integer value) {
            addCriterion("assign_create_date >=", value, "assignCreateDate");
            return (Criteria) this;
        }

        public Criteria andAssignCreateDateLessThan(Integer value) {
            addCriterion("assign_create_date <", value, "assignCreateDate");
            return (Criteria) this;
        }

        public Criteria andAssignCreateDateLessThanOrEqualTo(Integer value) {
            addCriterion("assign_create_date <=", value, "assignCreateDate");
            return (Criteria) this;
        }

        public Criteria andAssignCreateDateIn(List<Integer> values) {
            addCriterion("assign_create_date in", values, "assignCreateDate");
            return (Criteria) this;
        }

        public Criteria andAssignCreateDateNotIn(List<Integer> values) {
            addCriterion("assign_create_date not in", values, "assignCreateDate");
            return (Criteria) this;
        }

        public Criteria andAssignCreateDateBetween(Integer value1, Integer value2) {
            addCriterion("assign_create_date between", value1, value2, "assignCreateDate");
            return (Criteria) this;
        }

        public Criteria andAssignCreateDateNotBetween(Integer value1, Integer value2) {
            addCriterion("assign_create_date not between", value1, value2, "assignCreateDate");
            return (Criteria) this;
        }

        public Criteria andClientIsNull() {
            addCriterion("client is null");
            return (Criteria) this;
        }

        public Criteria andClientIsNotNull() {
            addCriterion("client is not null");
            return (Criteria) this;
        }

        public Criteria andClientEqualTo(Integer value) {
            addCriterion("client =", value, "client");
            return (Criteria) this;
        }

        public Criteria andClientNotEqualTo(Integer value) {
            addCriterion("client <>", value, "client");
            return (Criteria) this;
        }

        public Criteria andClientGreaterThan(Integer value) {
            addCriterion("client >", value, "client");
            return (Criteria) this;
        }

        public Criteria andClientGreaterThanOrEqualTo(Integer value) {
            addCriterion("client >=", value, "client");
            return (Criteria) this;
        }

        public Criteria andClientLessThan(Integer value) {
            addCriterion("client <", value, "client");
            return (Criteria) this;
        }

        public Criteria andClientLessThanOrEqualTo(Integer value) {
            addCriterion("client <=", value, "client");
            return (Criteria) this;
        }

        public Criteria andClientIn(List<Integer> values) {
            addCriterion("client in", values, "client");
            return (Criteria) this;
        }

        public Criteria andClientNotIn(List<Integer> values) {
            addCriterion("client not in", values, "client");
            return (Criteria) this;
        }

        public Criteria andClientBetween(Integer value1, Integer value2) {
            addCriterion("client between", value1, value2, "client");
            return (Criteria) this;
        }

        public Criteria andClientNotBetween(Integer value1, Integer value2) {
            addCriterion("client not between", value1, value2, "client");
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

        public Criteria andUniqueNidIsNull() {
            addCriterion("unique_nid is null");
            return (Criteria) this;
        }

        public Criteria andUniqueNidIsNotNull() {
            addCriterion("unique_nid is not null");
            return (Criteria) this;
        }

        public Criteria andUniqueNidEqualTo(String value) {
            addCriterion("unique_nid =", value, "uniqueNid");
            return (Criteria) this;
        }

        public Criteria andUniqueNidNotEqualTo(String value) {
            addCriterion("unique_nid <>", value, "uniqueNid");
            return (Criteria) this;
        }

        public Criteria andUniqueNidGreaterThan(String value) {
            addCriterion("unique_nid >", value, "uniqueNid");
            return (Criteria) this;
        }

        public Criteria andUniqueNidGreaterThanOrEqualTo(String value) {
            addCriterion("unique_nid >=", value, "uniqueNid");
            return (Criteria) this;
        }

        public Criteria andUniqueNidLessThan(String value) {
            addCriterion("unique_nid <", value, "uniqueNid");
            return (Criteria) this;
        }

        public Criteria andUniqueNidLessThanOrEqualTo(String value) {
            addCriterion("unique_nid <=", value, "uniqueNid");
            return (Criteria) this;
        }

        public Criteria andUniqueNidLike(String value) {
            addCriterion("unique_nid like", value, "uniqueNid");
            return (Criteria) this;
        }

        public Criteria andUniqueNidNotLike(String value) {
            addCriterion("unique_nid not like", value, "uniqueNid");
            return (Criteria) this;
        }

        public Criteria andUniqueNidIn(List<String> values) {
            addCriterion("unique_nid in", values, "uniqueNid");
            return (Criteria) this;
        }

        public Criteria andUniqueNidNotIn(List<String> values) {
            addCriterion("unique_nid not in", values, "uniqueNid");
            return (Criteria) this;
        }

        public Criteria andUniqueNidBetween(String value1, String value2) {
            addCriterion("unique_nid between", value1, value2, "uniqueNid");
            return (Criteria) this;
        }

        public Criteria andUniqueNidNotBetween(String value1, String value2) {
            addCriterion("unique_nid not between", value1, value2, "uniqueNid");
            return (Criteria) this;
        }

        public Criteria andCreditRepayOrderIdIsNull() {
            addCriterion("credit_repay_order_id is null");
            return (Criteria) this;
        }

        public Criteria andCreditRepayOrderIdIsNotNull() {
            addCriterion("credit_repay_order_id is not null");
            return (Criteria) this;
        }

        public Criteria andCreditRepayOrderIdEqualTo(String value) {
            addCriterion("credit_repay_order_id =", value, "creditRepayOrderId");
            return (Criteria) this;
        }

        public Criteria andCreditRepayOrderIdNotEqualTo(String value) {
            addCriterion("credit_repay_order_id <>", value, "creditRepayOrderId");
            return (Criteria) this;
        }

        public Criteria andCreditRepayOrderIdGreaterThan(String value) {
            addCriterion("credit_repay_order_id >", value, "creditRepayOrderId");
            return (Criteria) this;
        }

        public Criteria andCreditRepayOrderIdGreaterThanOrEqualTo(String value) {
            addCriterion("credit_repay_order_id >=", value, "creditRepayOrderId");
            return (Criteria) this;
        }

        public Criteria andCreditRepayOrderIdLessThan(String value) {
            addCriterion("credit_repay_order_id <", value, "creditRepayOrderId");
            return (Criteria) this;
        }

        public Criteria andCreditRepayOrderIdLessThanOrEqualTo(String value) {
            addCriterion("credit_repay_order_id <=", value, "creditRepayOrderId");
            return (Criteria) this;
        }

        public Criteria andCreditRepayOrderIdLike(String value) {
            addCriterion("credit_repay_order_id like", value, "creditRepayOrderId");
            return (Criteria) this;
        }

        public Criteria andCreditRepayOrderIdNotLike(String value) {
            addCriterion("credit_repay_order_id not like", value, "creditRepayOrderId");
            return (Criteria) this;
        }

        public Criteria andCreditRepayOrderIdIn(List<String> values) {
            addCriterion("credit_repay_order_id in", values, "creditRepayOrderId");
            return (Criteria) this;
        }

        public Criteria andCreditRepayOrderIdNotIn(List<String> values) {
            addCriterion("credit_repay_order_id not in", values, "creditRepayOrderId");
            return (Criteria) this;
        }

        public Criteria andCreditRepayOrderIdBetween(String value1, String value2) {
            addCriterion("credit_repay_order_id between", value1, value2, "creditRepayOrderId");
            return (Criteria) this;
        }

        public Criteria andCreditRepayOrderIdNotBetween(String value1, String value2) {
            addCriterion("credit_repay_order_id not between", value1, value2, "creditRepayOrderId");
            return (Criteria) this;
        }

        public Criteria andCreditRepayOrderDateIsNull() {
            addCriterion("credit_repay_order_date is null");
            return (Criteria) this;
        }

        public Criteria andCreditRepayOrderDateIsNotNull() {
            addCriterion("credit_repay_order_date is not null");
            return (Criteria) this;
        }

        public Criteria andCreditRepayOrderDateEqualTo(String value) {
            addCriterion("credit_repay_order_date =", value, "creditRepayOrderDate");
            return (Criteria) this;
        }

        public Criteria andCreditRepayOrderDateNotEqualTo(String value) {
            addCriterion("credit_repay_order_date <>", value, "creditRepayOrderDate");
            return (Criteria) this;
        }

        public Criteria andCreditRepayOrderDateGreaterThan(String value) {
            addCriterion("credit_repay_order_date >", value, "creditRepayOrderDate");
            return (Criteria) this;
        }

        public Criteria andCreditRepayOrderDateGreaterThanOrEqualTo(String value) {
            addCriterion("credit_repay_order_date >=", value, "creditRepayOrderDate");
            return (Criteria) this;
        }

        public Criteria andCreditRepayOrderDateLessThan(String value) {
            addCriterion("credit_repay_order_date <", value, "creditRepayOrderDate");
            return (Criteria) this;
        }

        public Criteria andCreditRepayOrderDateLessThanOrEqualTo(String value) {
            addCriterion("credit_repay_order_date <=", value, "creditRepayOrderDate");
            return (Criteria) this;
        }

        public Criteria andCreditRepayOrderDateLike(String value) {
            addCriterion("credit_repay_order_date like", value, "creditRepayOrderDate");
            return (Criteria) this;
        }

        public Criteria andCreditRepayOrderDateNotLike(String value) {
            addCriterion("credit_repay_order_date not like", value, "creditRepayOrderDate");
            return (Criteria) this;
        }

        public Criteria andCreditRepayOrderDateIn(List<String> values) {
            addCriterion("credit_repay_order_date in", values, "creditRepayOrderDate");
            return (Criteria) this;
        }

        public Criteria andCreditRepayOrderDateNotIn(List<String> values) {
            addCriterion("credit_repay_order_date not in", values, "creditRepayOrderDate");
            return (Criteria) this;
        }

        public Criteria andCreditRepayOrderDateBetween(String value1, String value2) {
            addCriterion("credit_repay_order_date between", value1, value2, "creditRepayOrderDate");
            return (Criteria) this;
        }

        public Criteria andCreditRepayOrderDateNotBetween(String value1, String value2) {
            addCriterion("credit_repay_order_date not between", value1, value2, "creditRepayOrderDate");
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

        public Criteria andRepayAdvanceInterestIsNull() {
            addCriterion("repay_advance_interest is null");
            return (Criteria) this;
        }

        public Criteria andRepayAdvanceInterestIsNotNull() {
            addCriterion("repay_advance_interest is not null");
            return (Criteria) this;
        }

        public Criteria andRepayAdvanceInterestEqualTo(BigDecimal value) {
            addCriterion("repay_advance_interest =", value, "repayAdvanceInterest");
            return (Criteria) this;
        }

        public Criteria andRepayAdvanceInterestNotEqualTo(BigDecimal value) {
            addCriterion("repay_advance_interest <>", value, "repayAdvanceInterest");
            return (Criteria) this;
        }

        public Criteria andRepayAdvanceInterestGreaterThan(BigDecimal value) {
            addCriterion("repay_advance_interest >", value, "repayAdvanceInterest");
            return (Criteria) this;
        }

        public Criteria andRepayAdvanceInterestGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_advance_interest >=", value, "repayAdvanceInterest");
            return (Criteria) this;
        }

        public Criteria andRepayAdvanceInterestLessThan(BigDecimal value) {
            addCriterion("repay_advance_interest <", value, "repayAdvanceInterest");
            return (Criteria) this;
        }

        public Criteria andRepayAdvanceInterestLessThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_advance_interest <=", value, "repayAdvanceInterest");
            return (Criteria) this;
        }

        public Criteria andRepayAdvanceInterestIn(List<BigDecimal> values) {
            addCriterion("repay_advance_interest in", values, "repayAdvanceInterest");
            return (Criteria) this;
        }

        public Criteria andRepayAdvanceInterestNotIn(List<BigDecimal> values) {
            addCriterion("repay_advance_interest not in", values, "repayAdvanceInterest");
            return (Criteria) this;
        }

        public Criteria andRepayAdvanceInterestBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_advance_interest between", value1, value2, "repayAdvanceInterest");
            return (Criteria) this;
        }

        public Criteria andRepayAdvanceInterestNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_advance_interest not between", value1, value2, "repayAdvanceInterest");
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

        public Criteria andRepayLateInterestIsNull() {
            addCriterion("repay_late_interest is null");
            return (Criteria) this;
        }

        public Criteria andRepayLateInterestIsNotNull() {
            addCriterion("repay_late_interest is not null");
            return (Criteria) this;
        }

        public Criteria andRepayLateInterestEqualTo(BigDecimal value) {
            addCriterion("repay_late_interest =", value, "repayLateInterest");
            return (Criteria) this;
        }

        public Criteria andRepayLateInterestNotEqualTo(BigDecimal value) {
            addCriterion("repay_late_interest <>", value, "repayLateInterest");
            return (Criteria) this;
        }

        public Criteria andRepayLateInterestGreaterThan(BigDecimal value) {
            addCriterion("repay_late_interest >", value, "repayLateInterest");
            return (Criteria) this;
        }

        public Criteria andRepayLateInterestGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_late_interest >=", value, "repayLateInterest");
            return (Criteria) this;
        }

        public Criteria andRepayLateInterestLessThan(BigDecimal value) {
            addCriterion("repay_late_interest <", value, "repayLateInterest");
            return (Criteria) this;
        }

        public Criteria andRepayLateInterestLessThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_late_interest <=", value, "repayLateInterest");
            return (Criteria) this;
        }

        public Criteria andRepayLateInterestIn(List<BigDecimal> values) {
            addCriterion("repay_late_interest in", values, "repayLateInterest");
            return (Criteria) this;
        }

        public Criteria andRepayLateInterestNotIn(List<BigDecimal> values) {
            addCriterion("repay_late_interest not in", values, "repayLateInterest");
            return (Criteria) this;
        }

        public Criteria andRepayLateInterestBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_late_interest between", value1, value2, "repayLateInterest");
            return (Criteria) this;
        }

        public Criteria andRepayLateInterestNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_late_interest not between", value1, value2, "repayLateInterest");
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

        public Criteria andRepayDelayInterestIsNull() {
            addCriterion("repay_delay_interest is null");
            return (Criteria) this;
        }

        public Criteria andRepayDelayInterestIsNotNull() {
            addCriterion("repay_delay_interest is not null");
            return (Criteria) this;
        }

        public Criteria andRepayDelayInterestEqualTo(BigDecimal value) {
            addCriterion("repay_delay_interest =", value, "repayDelayInterest");
            return (Criteria) this;
        }

        public Criteria andRepayDelayInterestNotEqualTo(BigDecimal value) {
            addCriterion("repay_delay_interest <>", value, "repayDelayInterest");
            return (Criteria) this;
        }

        public Criteria andRepayDelayInterestGreaterThan(BigDecimal value) {
            addCriterion("repay_delay_interest >", value, "repayDelayInterest");
            return (Criteria) this;
        }

        public Criteria andRepayDelayInterestGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_delay_interest >=", value, "repayDelayInterest");
            return (Criteria) this;
        }

        public Criteria andRepayDelayInterestLessThan(BigDecimal value) {
            addCriterion("repay_delay_interest <", value, "repayDelayInterest");
            return (Criteria) this;
        }

        public Criteria andRepayDelayInterestLessThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_delay_interest <=", value, "repayDelayInterest");
            return (Criteria) this;
        }

        public Criteria andRepayDelayInterestIn(List<BigDecimal> values) {
            addCriterion("repay_delay_interest in", values, "repayDelayInterest");
            return (Criteria) this;
        }

        public Criteria andRepayDelayInterestNotIn(List<BigDecimal> values) {
            addCriterion("repay_delay_interest not in", values, "repayDelayInterest");
            return (Criteria) this;
        }

        public Criteria andRepayDelayInterestBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_delay_interest between", value1, value2, "repayDelayInterest");
            return (Criteria) this;
        }

        public Criteria andRepayDelayInterestNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_delay_interest not between", value1, value2, "repayDelayInterest");
            return (Criteria) this;
        }

        public Criteria andReceiveAccountYesIsNull() {
            addCriterion("receive_account_yes is null");
            return (Criteria) this;
        }

        public Criteria andReceiveAccountYesIsNotNull() {
            addCriterion("receive_account_yes is not null");
            return (Criteria) this;
        }

        public Criteria andReceiveAccountYesEqualTo(BigDecimal value) {
            addCriterion("receive_account_yes =", value, "receiveAccountYes");
            return (Criteria) this;
        }

        public Criteria andReceiveAccountYesNotEqualTo(BigDecimal value) {
            addCriterion("receive_account_yes <>", value, "receiveAccountYes");
            return (Criteria) this;
        }

        public Criteria andReceiveAccountYesGreaterThan(BigDecimal value) {
            addCriterion("receive_account_yes >", value, "receiveAccountYes");
            return (Criteria) this;
        }

        public Criteria andReceiveAccountYesGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("receive_account_yes >=", value, "receiveAccountYes");
            return (Criteria) this;
        }

        public Criteria andReceiveAccountYesLessThan(BigDecimal value) {
            addCriterion("receive_account_yes <", value, "receiveAccountYes");
            return (Criteria) this;
        }

        public Criteria andReceiveAccountYesLessThanOrEqualTo(BigDecimal value) {
            addCriterion("receive_account_yes <=", value, "receiveAccountYes");
            return (Criteria) this;
        }

        public Criteria andReceiveAccountYesIn(List<BigDecimal> values) {
            addCriterion("receive_account_yes in", values, "receiveAccountYes");
            return (Criteria) this;
        }

        public Criteria andReceiveAccountYesNotIn(List<BigDecimal> values) {
            addCriterion("receive_account_yes not in", values, "receiveAccountYes");
            return (Criteria) this;
        }

        public Criteria andReceiveAccountYesBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("receive_account_yes between", value1, value2, "receiveAccountYes");
            return (Criteria) this;
        }

        public Criteria andReceiveAccountYesNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("receive_account_yes not between", value1, value2, "receiveAccountYes");
            return (Criteria) this;
        }

        public Criteria andReceiveCapitalYesIsNull() {
            addCriterion("receive_capital_yes is null");
            return (Criteria) this;
        }

        public Criteria andReceiveCapitalYesIsNotNull() {
            addCriterion("receive_capital_yes is not null");
            return (Criteria) this;
        }

        public Criteria andReceiveCapitalYesEqualTo(BigDecimal value) {
            addCriterion("receive_capital_yes =", value, "receiveCapitalYes");
            return (Criteria) this;
        }

        public Criteria andReceiveCapitalYesNotEqualTo(BigDecimal value) {
            addCriterion("receive_capital_yes <>", value, "receiveCapitalYes");
            return (Criteria) this;
        }

        public Criteria andReceiveCapitalYesGreaterThan(BigDecimal value) {
            addCriterion("receive_capital_yes >", value, "receiveCapitalYes");
            return (Criteria) this;
        }

        public Criteria andReceiveCapitalYesGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("receive_capital_yes >=", value, "receiveCapitalYes");
            return (Criteria) this;
        }

        public Criteria andReceiveCapitalYesLessThan(BigDecimal value) {
            addCriterion("receive_capital_yes <", value, "receiveCapitalYes");
            return (Criteria) this;
        }

        public Criteria andReceiveCapitalYesLessThanOrEqualTo(BigDecimal value) {
            addCriterion("receive_capital_yes <=", value, "receiveCapitalYes");
            return (Criteria) this;
        }

        public Criteria andReceiveCapitalYesIn(List<BigDecimal> values) {
            addCriterion("receive_capital_yes in", values, "receiveCapitalYes");
            return (Criteria) this;
        }

        public Criteria andReceiveCapitalYesNotIn(List<BigDecimal> values) {
            addCriterion("receive_capital_yes not in", values, "receiveCapitalYes");
            return (Criteria) this;
        }

        public Criteria andReceiveCapitalYesBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("receive_capital_yes between", value1, value2, "receiveCapitalYes");
            return (Criteria) this;
        }

        public Criteria andReceiveCapitalYesNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("receive_capital_yes not between", value1, value2, "receiveCapitalYes");
            return (Criteria) this;
        }

        public Criteria andReceiveInterestYesIsNull() {
            addCriterion("receive_interest_yes is null");
            return (Criteria) this;
        }

        public Criteria andReceiveInterestYesIsNotNull() {
            addCriterion("receive_interest_yes is not null");
            return (Criteria) this;
        }

        public Criteria andReceiveInterestYesEqualTo(BigDecimal value) {
            addCriterion("receive_interest_yes =", value, "receiveInterestYes");
            return (Criteria) this;
        }

        public Criteria andReceiveInterestYesNotEqualTo(BigDecimal value) {
            addCriterion("receive_interest_yes <>", value, "receiveInterestYes");
            return (Criteria) this;
        }

        public Criteria andReceiveInterestYesGreaterThan(BigDecimal value) {
            addCriterion("receive_interest_yes >", value, "receiveInterestYes");
            return (Criteria) this;
        }

        public Criteria andReceiveInterestYesGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("receive_interest_yes >=", value, "receiveInterestYes");
            return (Criteria) this;
        }

        public Criteria andReceiveInterestYesLessThan(BigDecimal value) {
            addCriterion("receive_interest_yes <", value, "receiveInterestYes");
            return (Criteria) this;
        }

        public Criteria andReceiveInterestYesLessThanOrEqualTo(BigDecimal value) {
            addCriterion("receive_interest_yes <=", value, "receiveInterestYes");
            return (Criteria) this;
        }

        public Criteria andReceiveInterestYesIn(List<BigDecimal> values) {
            addCriterion("receive_interest_yes in", values, "receiveInterestYes");
            return (Criteria) this;
        }

        public Criteria andReceiveInterestYesNotIn(List<BigDecimal> values) {
            addCriterion("receive_interest_yes not in", values, "receiveInterestYes");
            return (Criteria) this;
        }

        public Criteria andReceiveInterestYesBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("receive_interest_yes between", value1, value2, "receiveInterestYes");
            return (Criteria) this;
        }

        public Criteria andReceiveInterestYesNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("receive_interest_yes not between", value1, value2, "receiveInterestYes");
            return (Criteria) this;
        }

        public Criteria andReceiveAdvanceInterestIsNull() {
            addCriterion("receive_advance_interest is null");
            return (Criteria) this;
        }

        public Criteria andReceiveAdvanceInterestIsNotNull() {
            addCriterion("receive_advance_interest is not null");
            return (Criteria) this;
        }

        public Criteria andReceiveAdvanceInterestEqualTo(BigDecimal value) {
            addCriterion("receive_advance_interest =", value, "receiveAdvanceInterest");
            return (Criteria) this;
        }

        public Criteria andReceiveAdvanceInterestNotEqualTo(BigDecimal value) {
            addCriterion("receive_advance_interest <>", value, "receiveAdvanceInterest");
            return (Criteria) this;
        }

        public Criteria andReceiveAdvanceInterestGreaterThan(BigDecimal value) {
            addCriterion("receive_advance_interest >", value, "receiveAdvanceInterest");
            return (Criteria) this;
        }

        public Criteria andReceiveAdvanceInterestGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("receive_advance_interest >=", value, "receiveAdvanceInterest");
            return (Criteria) this;
        }

        public Criteria andReceiveAdvanceInterestLessThan(BigDecimal value) {
            addCriterion("receive_advance_interest <", value, "receiveAdvanceInterest");
            return (Criteria) this;
        }

        public Criteria andReceiveAdvanceInterestLessThanOrEqualTo(BigDecimal value) {
            addCriterion("receive_advance_interest <=", value, "receiveAdvanceInterest");
            return (Criteria) this;
        }

        public Criteria andReceiveAdvanceInterestIn(List<BigDecimal> values) {
            addCriterion("receive_advance_interest in", values, "receiveAdvanceInterest");
            return (Criteria) this;
        }

        public Criteria andReceiveAdvanceInterestNotIn(List<BigDecimal> values) {
            addCriterion("receive_advance_interest not in", values, "receiveAdvanceInterest");
            return (Criteria) this;
        }

        public Criteria andReceiveAdvanceInterestBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("receive_advance_interest between", value1, value2, "receiveAdvanceInterest");
            return (Criteria) this;
        }

        public Criteria andReceiveAdvanceInterestNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("receive_advance_interest not between", value1, value2, "receiveAdvanceInterest");
            return (Criteria) this;
        }

        public Criteria andReceiveLateInterestIsNull() {
            addCriterion("receive_late_interest is null");
            return (Criteria) this;
        }

        public Criteria andReceiveLateInterestIsNotNull() {
            addCriterion("receive_late_interest is not null");
            return (Criteria) this;
        }

        public Criteria andReceiveLateInterestEqualTo(BigDecimal value) {
            addCriterion("receive_late_interest =", value, "receiveLateInterest");
            return (Criteria) this;
        }

        public Criteria andReceiveLateInterestNotEqualTo(BigDecimal value) {
            addCriterion("receive_late_interest <>", value, "receiveLateInterest");
            return (Criteria) this;
        }

        public Criteria andReceiveLateInterestGreaterThan(BigDecimal value) {
            addCriterion("receive_late_interest >", value, "receiveLateInterest");
            return (Criteria) this;
        }

        public Criteria andReceiveLateInterestGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("receive_late_interest >=", value, "receiveLateInterest");
            return (Criteria) this;
        }

        public Criteria andReceiveLateInterestLessThan(BigDecimal value) {
            addCriterion("receive_late_interest <", value, "receiveLateInterest");
            return (Criteria) this;
        }

        public Criteria andReceiveLateInterestLessThanOrEqualTo(BigDecimal value) {
            addCriterion("receive_late_interest <=", value, "receiveLateInterest");
            return (Criteria) this;
        }

        public Criteria andReceiveLateInterestIn(List<BigDecimal> values) {
            addCriterion("receive_late_interest in", values, "receiveLateInterest");
            return (Criteria) this;
        }

        public Criteria andReceiveLateInterestNotIn(List<BigDecimal> values) {
            addCriterion("receive_late_interest not in", values, "receiveLateInterest");
            return (Criteria) this;
        }

        public Criteria andReceiveLateInterestBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("receive_late_interest between", value1, value2, "receiveLateInterest");
            return (Criteria) this;
        }

        public Criteria andReceiveLateInterestNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("receive_late_interest not between", value1, value2, "receiveLateInterest");
            return (Criteria) this;
        }

        public Criteria andReceiveDelayInterestIsNull() {
            addCriterion("receive_delay_interest is null");
            return (Criteria) this;
        }

        public Criteria andReceiveDelayInterestIsNotNull() {
            addCriterion("receive_delay_interest is not null");
            return (Criteria) this;
        }

        public Criteria andReceiveDelayInterestEqualTo(BigDecimal value) {
            addCriterion("receive_delay_interest =", value, "receiveDelayInterest");
            return (Criteria) this;
        }

        public Criteria andReceiveDelayInterestNotEqualTo(BigDecimal value) {
            addCriterion("receive_delay_interest <>", value, "receiveDelayInterest");
            return (Criteria) this;
        }

        public Criteria andReceiveDelayInterestGreaterThan(BigDecimal value) {
            addCriterion("receive_delay_interest >", value, "receiveDelayInterest");
            return (Criteria) this;
        }

        public Criteria andReceiveDelayInterestGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("receive_delay_interest >=", value, "receiveDelayInterest");
            return (Criteria) this;
        }

        public Criteria andReceiveDelayInterestLessThan(BigDecimal value) {
            addCriterion("receive_delay_interest <", value, "receiveDelayInterest");
            return (Criteria) this;
        }

        public Criteria andReceiveDelayInterestLessThanOrEqualTo(BigDecimal value) {
            addCriterion("receive_delay_interest <=", value, "receiveDelayInterest");
            return (Criteria) this;
        }

        public Criteria andReceiveDelayInterestIn(List<BigDecimal> values) {
            addCriterion("receive_delay_interest in", values, "receiveDelayInterest");
            return (Criteria) this;
        }

        public Criteria andReceiveDelayInterestNotIn(List<BigDecimal> values) {
            addCriterion("receive_delay_interest not in", values, "receiveDelayInterest");
            return (Criteria) this;
        }

        public Criteria andReceiveDelayInterestBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("receive_delay_interest between", value1, value2, "receiveDelayInterest");
            return (Criteria) this;
        }

        public Criteria andReceiveDelayInterestNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("receive_delay_interest not between", value1, value2, "receiveDelayInterest");
            return (Criteria) this;
        }

        public Criteria andReceiveAdvanceInterestYesIsNull() {
            addCriterion("receive_advance_interest_yes is null");
            return (Criteria) this;
        }

        public Criteria andReceiveAdvanceInterestYesIsNotNull() {
            addCriterion("receive_advance_interest_yes is not null");
            return (Criteria) this;
        }

        public Criteria andReceiveAdvanceInterestYesEqualTo(BigDecimal value) {
            addCriterion("receive_advance_interest_yes =", value, "receiveAdvanceInterestYes");
            return (Criteria) this;
        }

        public Criteria andReceiveAdvanceInterestYesNotEqualTo(BigDecimal value) {
            addCriterion("receive_advance_interest_yes <>", value, "receiveAdvanceInterestYes");
            return (Criteria) this;
        }

        public Criteria andReceiveAdvanceInterestYesGreaterThan(BigDecimal value) {
            addCriterion("receive_advance_interest_yes >", value, "receiveAdvanceInterestYes");
            return (Criteria) this;
        }

        public Criteria andReceiveAdvanceInterestYesGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("receive_advance_interest_yes >=", value, "receiveAdvanceInterestYes");
            return (Criteria) this;
        }

        public Criteria andReceiveAdvanceInterestYesLessThan(BigDecimal value) {
            addCriterion("receive_advance_interest_yes <", value, "receiveAdvanceInterestYes");
            return (Criteria) this;
        }

        public Criteria andReceiveAdvanceInterestYesLessThanOrEqualTo(BigDecimal value) {
            addCriterion("receive_advance_interest_yes <=", value, "receiveAdvanceInterestYes");
            return (Criteria) this;
        }

        public Criteria andReceiveAdvanceInterestYesIn(List<BigDecimal> values) {
            addCriterion("receive_advance_interest_yes in", values, "receiveAdvanceInterestYes");
            return (Criteria) this;
        }

        public Criteria andReceiveAdvanceInterestYesNotIn(List<BigDecimal> values) {
            addCriterion("receive_advance_interest_yes not in", values, "receiveAdvanceInterestYes");
            return (Criteria) this;
        }

        public Criteria andReceiveAdvanceInterestYesBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("receive_advance_interest_yes between", value1, value2, "receiveAdvanceInterestYes");
            return (Criteria) this;
        }

        public Criteria andReceiveAdvanceInterestYesNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("receive_advance_interest_yes not between", value1, value2, "receiveAdvanceInterestYes");
            return (Criteria) this;
        }

        public Criteria andReceiveLateInterestYesIsNull() {
            addCriterion("receive_late_interest_yes is null");
            return (Criteria) this;
        }

        public Criteria andReceiveLateInterestYesIsNotNull() {
            addCriterion("receive_late_interest_yes is not null");
            return (Criteria) this;
        }

        public Criteria andReceiveLateInterestYesEqualTo(BigDecimal value) {
            addCriterion("receive_late_interest_yes =", value, "receiveLateInterestYes");
            return (Criteria) this;
        }

        public Criteria andReceiveLateInterestYesNotEqualTo(BigDecimal value) {
            addCriterion("receive_late_interest_yes <>", value, "receiveLateInterestYes");
            return (Criteria) this;
        }

        public Criteria andReceiveLateInterestYesGreaterThan(BigDecimal value) {
            addCriterion("receive_late_interest_yes >", value, "receiveLateInterestYes");
            return (Criteria) this;
        }

        public Criteria andReceiveLateInterestYesGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("receive_late_interest_yes >=", value, "receiveLateInterestYes");
            return (Criteria) this;
        }

        public Criteria andReceiveLateInterestYesLessThan(BigDecimal value) {
            addCriterion("receive_late_interest_yes <", value, "receiveLateInterestYes");
            return (Criteria) this;
        }

        public Criteria andReceiveLateInterestYesLessThanOrEqualTo(BigDecimal value) {
            addCriterion("receive_late_interest_yes <=", value, "receiveLateInterestYes");
            return (Criteria) this;
        }

        public Criteria andReceiveLateInterestYesIn(List<BigDecimal> values) {
            addCriterion("receive_late_interest_yes in", values, "receiveLateInterestYes");
            return (Criteria) this;
        }

        public Criteria andReceiveLateInterestYesNotIn(List<BigDecimal> values) {
            addCriterion("receive_late_interest_yes not in", values, "receiveLateInterestYes");
            return (Criteria) this;
        }

        public Criteria andReceiveLateInterestYesBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("receive_late_interest_yes between", value1, value2, "receiveLateInterestYes");
            return (Criteria) this;
        }

        public Criteria andReceiveLateInterestYesNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("receive_late_interest_yes not between", value1, value2, "receiveLateInterestYes");
            return (Criteria) this;
        }

        public Criteria andReceiveDelayInterestYesIsNull() {
            addCriterion("receive_delay_interest_yes is null");
            return (Criteria) this;
        }

        public Criteria andReceiveDelayInterestYesIsNotNull() {
            addCriterion("receive_delay_interest_yes is not null");
            return (Criteria) this;
        }

        public Criteria andReceiveDelayInterestYesEqualTo(BigDecimal value) {
            addCriterion("receive_delay_interest_yes =", value, "receiveDelayInterestYes");
            return (Criteria) this;
        }

        public Criteria andReceiveDelayInterestYesNotEqualTo(BigDecimal value) {
            addCriterion("receive_delay_interest_yes <>", value, "receiveDelayInterestYes");
            return (Criteria) this;
        }

        public Criteria andReceiveDelayInterestYesGreaterThan(BigDecimal value) {
            addCriterion("receive_delay_interest_yes >", value, "receiveDelayInterestYes");
            return (Criteria) this;
        }

        public Criteria andReceiveDelayInterestYesGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("receive_delay_interest_yes >=", value, "receiveDelayInterestYes");
            return (Criteria) this;
        }

        public Criteria andReceiveDelayInterestYesLessThan(BigDecimal value) {
            addCriterion("receive_delay_interest_yes <", value, "receiveDelayInterestYes");
            return (Criteria) this;
        }

        public Criteria andReceiveDelayInterestYesLessThanOrEqualTo(BigDecimal value) {
            addCriterion("receive_delay_interest_yes <=", value, "receiveDelayInterestYes");
            return (Criteria) this;
        }

        public Criteria andReceiveDelayInterestYesIn(List<BigDecimal> values) {
            addCriterion("receive_delay_interest_yes in", values, "receiveDelayInterestYes");
            return (Criteria) this;
        }

        public Criteria andReceiveDelayInterestYesNotIn(List<BigDecimal> values) {
            addCriterion("receive_delay_interest_yes not in", values, "receiveDelayInterestYes");
            return (Criteria) this;
        }

        public Criteria andReceiveDelayInterestYesBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("receive_delay_interest_yes between", value1, value2, "receiveDelayInterestYes");
            return (Criteria) this;
        }

        public Criteria andReceiveDelayInterestYesNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("receive_delay_interest_yes not between", value1, value2, "receiveDelayInterestYes");
            return (Criteria) this;
        }

        public Criteria andCreditTimesIsNull() {
            addCriterion("credit_times is null");
            return (Criteria) this;
        }

        public Criteria andCreditTimesIsNotNull() {
            addCriterion("credit_times is not null");
            return (Criteria) this;
        }

        public Criteria andCreditTimesEqualTo(Integer value) {
            addCriterion("credit_times =", value, "creditTimes");
            return (Criteria) this;
        }

        public Criteria andCreditTimesNotEqualTo(Integer value) {
            addCriterion("credit_times <>", value, "creditTimes");
            return (Criteria) this;
        }

        public Criteria andCreditTimesGreaterThan(Integer value) {
            addCriterion("credit_times >", value, "creditTimes");
            return (Criteria) this;
        }

        public Criteria andCreditTimesGreaterThanOrEqualTo(Integer value) {
            addCriterion("credit_times >=", value, "creditTimes");
            return (Criteria) this;
        }

        public Criteria andCreditTimesLessThan(Integer value) {
            addCriterion("credit_times <", value, "creditTimes");
            return (Criteria) this;
        }

        public Criteria andCreditTimesLessThanOrEqualTo(Integer value) {
            addCriterion("credit_times <=", value, "creditTimes");
            return (Criteria) this;
        }

        public Criteria andCreditTimesIn(List<Integer> values) {
            addCriterion("credit_times in", values, "creditTimes");
            return (Criteria) this;
        }

        public Criteria andCreditTimesNotIn(List<Integer> values) {
            addCriterion("credit_times not in", values, "creditTimes");
            return (Criteria) this;
        }

        public Criteria andCreditTimesBetween(Integer value1, Integer value2) {
            addCriterion("credit_times between", value1, value2, "creditTimes");
            return (Criteria) this;
        }

        public Criteria andCreditTimesNotBetween(Integer value1, Integer value2) {
            addCriterion("credit_times not between", value1, value2, "creditTimes");
            return (Criteria) this;
        }

        public Criteria andDebtStatusIsNull() {
            addCriterion("debt_status is null");
            return (Criteria) this;
        }

        public Criteria andDebtStatusIsNotNull() {
            addCriterion("debt_status is not null");
            return (Criteria) this;
        }

        public Criteria andDebtStatusEqualTo(Integer value) {
            addCriterion("debt_status =", value, "debtStatus");
            return (Criteria) this;
        }

        public Criteria andDebtStatusNotEqualTo(Integer value) {
            addCriterion("debt_status <>", value, "debtStatus");
            return (Criteria) this;
        }

        public Criteria andDebtStatusGreaterThan(Integer value) {
            addCriterion("debt_status >", value, "debtStatus");
            return (Criteria) this;
        }

        public Criteria andDebtStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("debt_status >=", value, "debtStatus");
            return (Criteria) this;
        }

        public Criteria andDebtStatusLessThan(Integer value) {
            addCriterion("debt_status <", value, "debtStatus");
            return (Criteria) this;
        }

        public Criteria andDebtStatusLessThanOrEqualTo(Integer value) {
            addCriterion("debt_status <=", value, "debtStatus");
            return (Criteria) this;
        }

        public Criteria andDebtStatusIn(List<Integer> values) {
            addCriterion("debt_status in", values, "debtStatus");
            return (Criteria) this;
        }

        public Criteria andDebtStatusNotIn(List<Integer> values) {
            addCriterion("debt_status not in", values, "debtStatus");
            return (Criteria) this;
        }

        public Criteria andDebtStatusBetween(Integer value1, Integer value2) {
            addCriterion("debt_status between", value1, value2, "debtStatus");
            return (Criteria) this;
        }

        public Criteria andDebtStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("debt_status not between", value1, value2, "debtStatus");
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

        public Criteria andRepayAdvancePenaltyInterestIsNull() {
            addCriterion("repay_advance_penalty_interest is null");
            return (Criteria) this;
        }

        public Criteria andRepayAdvancePenaltyInterestIsNotNull() {
            addCriterion("repay_advance_penalty_interest is not null");
            return (Criteria) this;
        }

        public Criteria andRepayAdvancePenaltyInterestEqualTo(BigDecimal value) {
            addCriterion("repay_advance_penalty_interest =", value, "repayAdvancePenaltyInterest");
            return (Criteria) this;
        }

        public Criteria andRepayAdvancePenaltyInterestNotEqualTo(BigDecimal value) {
            addCriterion("repay_advance_penalty_interest <>", value, "repayAdvancePenaltyInterest");
            return (Criteria) this;
        }

        public Criteria andRepayAdvancePenaltyInterestGreaterThan(BigDecimal value) {
            addCriterion("repay_advance_penalty_interest >", value, "repayAdvancePenaltyInterest");
            return (Criteria) this;
        }

        public Criteria andRepayAdvancePenaltyInterestGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_advance_penalty_interest >=", value, "repayAdvancePenaltyInterest");
            return (Criteria) this;
        }

        public Criteria andRepayAdvancePenaltyInterestLessThan(BigDecimal value) {
            addCriterion("repay_advance_penalty_interest <", value, "repayAdvancePenaltyInterest");
            return (Criteria) this;
        }

        public Criteria andRepayAdvancePenaltyInterestLessThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_advance_penalty_interest <=", value, "repayAdvancePenaltyInterest");
            return (Criteria) this;
        }

        public Criteria andRepayAdvancePenaltyInterestIn(List<BigDecimal> values) {
            addCriterion("repay_advance_penalty_interest in", values, "repayAdvancePenaltyInterest");
            return (Criteria) this;
        }

        public Criteria andRepayAdvancePenaltyInterestNotIn(List<BigDecimal> values) {
            addCriterion("repay_advance_penalty_interest not in", values, "repayAdvancePenaltyInterest");
            return (Criteria) this;
        }

        public Criteria andRepayAdvancePenaltyInterestBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_advance_penalty_interest between", value1, value2, "repayAdvancePenaltyInterest");
            return (Criteria) this;
        }

        public Criteria andRepayAdvancePenaltyInterestNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_advance_penalty_interest not between", value1, value2, "repayAdvancePenaltyInterest");
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