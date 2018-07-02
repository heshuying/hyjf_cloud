package com.hyjf.am.trade.dao.model.auto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HjhDebtCreditTenderExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public HjhDebtCreditTenderExample() {
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

        public Criteria andLiquidatesPlanNidIsNull() {
            addCriterion("liquidates_plan_nid is null");
            return (Criteria) this;
        }

        public Criteria andLiquidatesPlanNidIsNotNull() {
            addCriterion("liquidates_plan_nid is not null");
            return (Criteria) this;
        }

        public Criteria andLiquidatesPlanNidEqualTo(String value) {
            addCriterion("liquidates_plan_nid =", value, "liquidatesPlanNid");
            return (Criteria) this;
        }

        public Criteria andLiquidatesPlanNidNotEqualTo(String value) {
            addCriterion("liquidates_plan_nid <>", value, "liquidatesPlanNid");
            return (Criteria) this;
        }

        public Criteria andLiquidatesPlanNidGreaterThan(String value) {
            addCriterion("liquidates_plan_nid >", value, "liquidatesPlanNid");
            return (Criteria) this;
        }

        public Criteria andLiquidatesPlanNidGreaterThanOrEqualTo(String value) {
            addCriterion("liquidates_plan_nid >=", value, "liquidatesPlanNid");
            return (Criteria) this;
        }

        public Criteria andLiquidatesPlanNidLessThan(String value) {
            addCriterion("liquidates_plan_nid <", value, "liquidatesPlanNid");
            return (Criteria) this;
        }

        public Criteria andLiquidatesPlanNidLessThanOrEqualTo(String value) {
            addCriterion("liquidates_plan_nid <=", value, "liquidatesPlanNid");
            return (Criteria) this;
        }

        public Criteria andLiquidatesPlanNidLike(String value) {
            addCriterion("liquidates_plan_nid like", value, "liquidatesPlanNid");
            return (Criteria) this;
        }

        public Criteria andLiquidatesPlanNidNotLike(String value) {
            addCriterion("liquidates_plan_nid not like", value, "liquidatesPlanNid");
            return (Criteria) this;
        }

        public Criteria andLiquidatesPlanNidIn(List<String> values) {
            addCriterion("liquidates_plan_nid in", values, "liquidatesPlanNid");
            return (Criteria) this;
        }

        public Criteria andLiquidatesPlanNidNotIn(List<String> values) {
            addCriterion("liquidates_plan_nid not in", values, "liquidatesPlanNid");
            return (Criteria) this;
        }

        public Criteria andLiquidatesPlanNidBetween(String value1, String value2) {
            addCriterion("liquidates_plan_nid between", value1, value2, "liquidatesPlanNid");
            return (Criteria) this;
        }

        public Criteria andLiquidatesPlanNidNotBetween(String value1, String value2) {
            addCriterion("liquidates_plan_nid not between", value1, value2, "liquidatesPlanNid");
            return (Criteria) this;
        }

        public Criteria andLiquidatesPlanOrderIdIsNull() {
            addCriterion("liquidates_plan_order_id is null");
            return (Criteria) this;
        }

        public Criteria andLiquidatesPlanOrderIdIsNotNull() {
            addCriterion("liquidates_plan_order_id is not null");
            return (Criteria) this;
        }

        public Criteria andLiquidatesPlanOrderIdEqualTo(String value) {
            addCriterion("liquidates_plan_order_id =", value, "liquidatesPlanOrderId");
            return (Criteria) this;
        }

        public Criteria andLiquidatesPlanOrderIdNotEqualTo(String value) {
            addCriterion("liquidates_plan_order_id <>", value, "liquidatesPlanOrderId");
            return (Criteria) this;
        }

        public Criteria andLiquidatesPlanOrderIdGreaterThan(String value) {
            addCriterion("liquidates_plan_order_id >", value, "liquidatesPlanOrderId");
            return (Criteria) this;
        }

        public Criteria andLiquidatesPlanOrderIdGreaterThanOrEqualTo(String value) {
            addCriterion("liquidates_plan_order_id >=", value, "liquidatesPlanOrderId");
            return (Criteria) this;
        }

        public Criteria andLiquidatesPlanOrderIdLessThan(String value) {
            addCriterion("liquidates_plan_order_id <", value, "liquidatesPlanOrderId");
            return (Criteria) this;
        }

        public Criteria andLiquidatesPlanOrderIdLessThanOrEqualTo(String value) {
            addCriterion("liquidates_plan_order_id <=", value, "liquidatesPlanOrderId");
            return (Criteria) this;
        }

        public Criteria andLiquidatesPlanOrderIdLike(String value) {
            addCriterion("liquidates_plan_order_id like", value, "liquidatesPlanOrderId");
            return (Criteria) this;
        }

        public Criteria andLiquidatesPlanOrderIdNotLike(String value) {
            addCriterion("liquidates_plan_order_id not like", value, "liquidatesPlanOrderId");
            return (Criteria) this;
        }

        public Criteria andLiquidatesPlanOrderIdIn(List<String> values) {
            addCriterion("liquidates_plan_order_id in", values, "liquidatesPlanOrderId");
            return (Criteria) this;
        }

        public Criteria andLiquidatesPlanOrderIdNotIn(List<String> values) {
            addCriterion("liquidates_plan_order_id not in", values, "liquidatesPlanOrderId");
            return (Criteria) this;
        }

        public Criteria andLiquidatesPlanOrderIdBetween(String value1, String value2) {
            addCriterion("liquidates_plan_order_id between", value1, value2, "liquidatesPlanOrderId");
            return (Criteria) this;
        }

        public Criteria andLiquidatesPlanOrderIdNotBetween(String value1, String value2) {
            addCriterion("liquidates_plan_order_id not between", value1, value2, "liquidatesPlanOrderId");
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

        public Criteria andAssignOrderDateIsNull() {
            addCriterion("assign_order_date is null");
            return (Criteria) this;
        }

        public Criteria andAssignOrderDateIsNotNull() {
            addCriterion("assign_order_date is not null");
            return (Criteria) this;
        }

        public Criteria andAssignOrderDateEqualTo(String value) {
            addCriterion("assign_order_date =", value, "assignOrderDate");
            return (Criteria) this;
        }

        public Criteria andAssignOrderDateNotEqualTo(String value) {
            addCriterion("assign_order_date <>", value, "assignOrderDate");
            return (Criteria) this;
        }

        public Criteria andAssignOrderDateGreaterThan(String value) {
            addCriterion("assign_order_date >", value, "assignOrderDate");
            return (Criteria) this;
        }

        public Criteria andAssignOrderDateGreaterThanOrEqualTo(String value) {
            addCriterion("assign_order_date >=", value, "assignOrderDate");
            return (Criteria) this;
        }

        public Criteria andAssignOrderDateLessThan(String value) {
            addCriterion("assign_order_date <", value, "assignOrderDate");
            return (Criteria) this;
        }

        public Criteria andAssignOrderDateLessThanOrEqualTo(String value) {
            addCriterion("assign_order_date <=", value, "assignOrderDate");
            return (Criteria) this;
        }

        public Criteria andAssignOrderDateLike(String value) {
            addCriterion("assign_order_date like", value, "assignOrderDate");
            return (Criteria) this;
        }

        public Criteria andAssignOrderDateNotLike(String value) {
            addCriterion("assign_order_date not like", value, "assignOrderDate");
            return (Criteria) this;
        }

        public Criteria andAssignOrderDateIn(List<String> values) {
            addCriterion("assign_order_date in", values, "assignOrderDate");
            return (Criteria) this;
        }

        public Criteria andAssignOrderDateNotIn(List<String> values) {
            addCriterion("assign_order_date not in", values, "assignOrderDate");
            return (Criteria) this;
        }

        public Criteria andAssignOrderDateBetween(String value1, String value2) {
            addCriterion("assign_order_date between", value1, value2, "assignOrderDate");
            return (Criteria) this;
        }

        public Criteria andAssignOrderDateNotBetween(String value1, String value2) {
            addCriterion("assign_order_date not between", value1, value2, "assignOrderDate");
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

        public Criteria andAssignCapitalIsNull() {
            addCriterion("assign_capital is null");
            return (Criteria) this;
        }

        public Criteria andAssignCapitalIsNotNull() {
            addCriterion("assign_capital is not null");
            return (Criteria) this;
        }

        public Criteria andAssignCapitalEqualTo(BigDecimal value) {
            addCriterion("assign_capital =", value, "assignCapital");
            return (Criteria) this;
        }

        public Criteria andAssignCapitalNotEqualTo(BigDecimal value) {
            addCriterion("assign_capital <>", value, "assignCapital");
            return (Criteria) this;
        }

        public Criteria andAssignCapitalGreaterThan(BigDecimal value) {
            addCriterion("assign_capital >", value, "assignCapital");
            return (Criteria) this;
        }

        public Criteria andAssignCapitalGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("assign_capital >=", value, "assignCapital");
            return (Criteria) this;
        }

        public Criteria andAssignCapitalLessThan(BigDecimal value) {
            addCriterion("assign_capital <", value, "assignCapital");
            return (Criteria) this;
        }

        public Criteria andAssignCapitalLessThanOrEqualTo(BigDecimal value) {
            addCriterion("assign_capital <=", value, "assignCapital");
            return (Criteria) this;
        }

        public Criteria andAssignCapitalIn(List<BigDecimal> values) {
            addCriterion("assign_capital in", values, "assignCapital");
            return (Criteria) this;
        }

        public Criteria andAssignCapitalNotIn(List<BigDecimal> values) {
            addCriterion("assign_capital not in", values, "assignCapital");
            return (Criteria) this;
        }

        public Criteria andAssignCapitalBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("assign_capital between", value1, value2, "assignCapital");
            return (Criteria) this;
        }

        public Criteria andAssignCapitalNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("assign_capital not between", value1, value2, "assignCapital");
            return (Criteria) this;
        }

        public Criteria andAssignAccountIsNull() {
            addCriterion("assign_account is null");
            return (Criteria) this;
        }

        public Criteria andAssignAccountIsNotNull() {
            addCriterion("assign_account is not null");
            return (Criteria) this;
        }

        public Criteria andAssignAccountEqualTo(BigDecimal value) {
            addCriterion("assign_account =", value, "assignAccount");
            return (Criteria) this;
        }

        public Criteria andAssignAccountNotEqualTo(BigDecimal value) {
            addCriterion("assign_account <>", value, "assignAccount");
            return (Criteria) this;
        }

        public Criteria andAssignAccountGreaterThan(BigDecimal value) {
            addCriterion("assign_account >", value, "assignAccount");
            return (Criteria) this;
        }

        public Criteria andAssignAccountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("assign_account >=", value, "assignAccount");
            return (Criteria) this;
        }

        public Criteria andAssignAccountLessThan(BigDecimal value) {
            addCriterion("assign_account <", value, "assignAccount");
            return (Criteria) this;
        }

        public Criteria andAssignAccountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("assign_account <=", value, "assignAccount");
            return (Criteria) this;
        }

        public Criteria andAssignAccountIn(List<BigDecimal> values) {
            addCriterion("assign_account in", values, "assignAccount");
            return (Criteria) this;
        }

        public Criteria andAssignAccountNotIn(List<BigDecimal> values) {
            addCriterion("assign_account not in", values, "assignAccount");
            return (Criteria) this;
        }

        public Criteria andAssignAccountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("assign_account between", value1, value2, "assignAccount");
            return (Criteria) this;
        }

        public Criteria andAssignAccountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("assign_account not between", value1, value2, "assignAccount");
            return (Criteria) this;
        }

        public Criteria andAssignInterestIsNull() {
            addCriterion("assign_interest is null");
            return (Criteria) this;
        }

        public Criteria andAssignInterestIsNotNull() {
            addCriterion("assign_interest is not null");
            return (Criteria) this;
        }

        public Criteria andAssignInterestEqualTo(BigDecimal value) {
            addCriterion("assign_interest =", value, "assignInterest");
            return (Criteria) this;
        }

        public Criteria andAssignInterestNotEqualTo(BigDecimal value) {
            addCriterion("assign_interest <>", value, "assignInterest");
            return (Criteria) this;
        }

        public Criteria andAssignInterestGreaterThan(BigDecimal value) {
            addCriterion("assign_interest >", value, "assignInterest");
            return (Criteria) this;
        }

        public Criteria andAssignInterestGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("assign_interest >=", value, "assignInterest");
            return (Criteria) this;
        }

        public Criteria andAssignInterestLessThan(BigDecimal value) {
            addCriterion("assign_interest <", value, "assignInterest");
            return (Criteria) this;
        }

        public Criteria andAssignInterestLessThanOrEqualTo(BigDecimal value) {
            addCriterion("assign_interest <=", value, "assignInterest");
            return (Criteria) this;
        }

        public Criteria andAssignInterestIn(List<BigDecimal> values) {
            addCriterion("assign_interest in", values, "assignInterest");
            return (Criteria) this;
        }

        public Criteria andAssignInterestNotIn(List<BigDecimal> values) {
            addCriterion("assign_interest not in", values, "assignInterest");
            return (Criteria) this;
        }

        public Criteria andAssignInterestBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("assign_interest between", value1, value2, "assignInterest");
            return (Criteria) this;
        }

        public Criteria andAssignInterestNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("assign_interest not between", value1, value2, "assignInterest");
            return (Criteria) this;
        }

        public Criteria andAssignRepayDelayInterestIsNull() {
            addCriterion("assign_repay_delay_interest is null");
            return (Criteria) this;
        }

        public Criteria andAssignRepayDelayInterestIsNotNull() {
            addCriterion("assign_repay_delay_interest is not null");
            return (Criteria) this;
        }

        public Criteria andAssignRepayDelayInterestEqualTo(BigDecimal value) {
            addCriterion("assign_repay_delay_interest =", value, "assignRepayDelayInterest");
            return (Criteria) this;
        }

        public Criteria andAssignRepayDelayInterestNotEqualTo(BigDecimal value) {
            addCriterion("assign_repay_delay_interest <>", value, "assignRepayDelayInterest");
            return (Criteria) this;
        }

        public Criteria andAssignRepayDelayInterestGreaterThan(BigDecimal value) {
            addCriterion("assign_repay_delay_interest >", value, "assignRepayDelayInterest");
            return (Criteria) this;
        }

        public Criteria andAssignRepayDelayInterestGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("assign_repay_delay_interest >=", value, "assignRepayDelayInterest");
            return (Criteria) this;
        }

        public Criteria andAssignRepayDelayInterestLessThan(BigDecimal value) {
            addCriterion("assign_repay_delay_interest <", value, "assignRepayDelayInterest");
            return (Criteria) this;
        }

        public Criteria andAssignRepayDelayInterestLessThanOrEqualTo(BigDecimal value) {
            addCriterion("assign_repay_delay_interest <=", value, "assignRepayDelayInterest");
            return (Criteria) this;
        }

        public Criteria andAssignRepayDelayInterestIn(List<BigDecimal> values) {
            addCriterion("assign_repay_delay_interest in", values, "assignRepayDelayInterest");
            return (Criteria) this;
        }

        public Criteria andAssignRepayDelayInterestNotIn(List<BigDecimal> values) {
            addCriterion("assign_repay_delay_interest not in", values, "assignRepayDelayInterest");
            return (Criteria) this;
        }

        public Criteria andAssignRepayDelayInterestBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("assign_repay_delay_interest between", value1, value2, "assignRepayDelayInterest");
            return (Criteria) this;
        }

        public Criteria andAssignRepayDelayInterestNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("assign_repay_delay_interest not between", value1, value2, "assignRepayDelayInterest");
            return (Criteria) this;
        }

        public Criteria andAssignRepayLateInterestIsNull() {
            addCriterion("assign_repay_late_interest is null");
            return (Criteria) this;
        }

        public Criteria andAssignRepayLateInterestIsNotNull() {
            addCriterion("assign_repay_late_interest is not null");
            return (Criteria) this;
        }

        public Criteria andAssignRepayLateInterestEqualTo(BigDecimal value) {
            addCriterion("assign_repay_late_interest =", value, "assignRepayLateInterest");
            return (Criteria) this;
        }

        public Criteria andAssignRepayLateInterestNotEqualTo(BigDecimal value) {
            addCriterion("assign_repay_late_interest <>", value, "assignRepayLateInterest");
            return (Criteria) this;
        }

        public Criteria andAssignRepayLateInterestGreaterThan(BigDecimal value) {
            addCriterion("assign_repay_late_interest >", value, "assignRepayLateInterest");
            return (Criteria) this;
        }

        public Criteria andAssignRepayLateInterestGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("assign_repay_late_interest >=", value, "assignRepayLateInterest");
            return (Criteria) this;
        }

        public Criteria andAssignRepayLateInterestLessThan(BigDecimal value) {
            addCriterion("assign_repay_late_interest <", value, "assignRepayLateInterest");
            return (Criteria) this;
        }

        public Criteria andAssignRepayLateInterestLessThanOrEqualTo(BigDecimal value) {
            addCriterion("assign_repay_late_interest <=", value, "assignRepayLateInterest");
            return (Criteria) this;
        }

        public Criteria andAssignRepayLateInterestIn(List<BigDecimal> values) {
            addCriterion("assign_repay_late_interest in", values, "assignRepayLateInterest");
            return (Criteria) this;
        }

        public Criteria andAssignRepayLateInterestNotIn(List<BigDecimal> values) {
            addCriterion("assign_repay_late_interest not in", values, "assignRepayLateInterest");
            return (Criteria) this;
        }

        public Criteria andAssignRepayLateInterestBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("assign_repay_late_interest between", value1, value2, "assignRepayLateInterest");
            return (Criteria) this;
        }

        public Criteria andAssignRepayLateInterestNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("assign_repay_late_interest not between", value1, value2, "assignRepayLateInterest");
            return (Criteria) this;
        }

        public Criteria andAssignInterestAdvanceIsNull() {
            addCriterion("assign_interest_advance is null");
            return (Criteria) this;
        }

        public Criteria andAssignInterestAdvanceIsNotNull() {
            addCriterion("assign_interest_advance is not null");
            return (Criteria) this;
        }

        public Criteria andAssignInterestAdvanceEqualTo(BigDecimal value) {
            addCriterion("assign_interest_advance =", value, "assignInterestAdvance");
            return (Criteria) this;
        }

        public Criteria andAssignInterestAdvanceNotEqualTo(BigDecimal value) {
            addCriterion("assign_interest_advance <>", value, "assignInterestAdvance");
            return (Criteria) this;
        }

        public Criteria andAssignInterestAdvanceGreaterThan(BigDecimal value) {
            addCriterion("assign_interest_advance >", value, "assignInterestAdvance");
            return (Criteria) this;
        }

        public Criteria andAssignInterestAdvanceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("assign_interest_advance >=", value, "assignInterestAdvance");
            return (Criteria) this;
        }

        public Criteria andAssignInterestAdvanceLessThan(BigDecimal value) {
            addCriterion("assign_interest_advance <", value, "assignInterestAdvance");
            return (Criteria) this;
        }

        public Criteria andAssignInterestAdvanceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("assign_interest_advance <=", value, "assignInterestAdvance");
            return (Criteria) this;
        }

        public Criteria andAssignInterestAdvanceIn(List<BigDecimal> values) {
            addCriterion("assign_interest_advance in", values, "assignInterestAdvance");
            return (Criteria) this;
        }

        public Criteria andAssignInterestAdvanceNotIn(List<BigDecimal> values) {
            addCriterion("assign_interest_advance not in", values, "assignInterestAdvance");
            return (Criteria) this;
        }

        public Criteria andAssignInterestAdvanceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("assign_interest_advance between", value1, value2, "assignInterestAdvance");
            return (Criteria) this;
        }

        public Criteria andAssignInterestAdvanceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("assign_interest_advance not between", value1, value2, "assignInterestAdvance");
            return (Criteria) this;
        }

        public Criteria andAssignPriceIsNull() {
            addCriterion("assign_price is null");
            return (Criteria) this;
        }

        public Criteria andAssignPriceIsNotNull() {
            addCriterion("assign_price is not null");
            return (Criteria) this;
        }

        public Criteria andAssignPriceEqualTo(BigDecimal value) {
            addCriterion("assign_price =", value, "assignPrice");
            return (Criteria) this;
        }

        public Criteria andAssignPriceNotEqualTo(BigDecimal value) {
            addCriterion("assign_price <>", value, "assignPrice");
            return (Criteria) this;
        }

        public Criteria andAssignPriceGreaterThan(BigDecimal value) {
            addCriterion("assign_price >", value, "assignPrice");
            return (Criteria) this;
        }

        public Criteria andAssignPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("assign_price >=", value, "assignPrice");
            return (Criteria) this;
        }

        public Criteria andAssignPriceLessThan(BigDecimal value) {
            addCriterion("assign_price <", value, "assignPrice");
            return (Criteria) this;
        }

        public Criteria andAssignPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("assign_price <=", value, "assignPrice");
            return (Criteria) this;
        }

        public Criteria andAssignPriceIn(List<BigDecimal> values) {
            addCriterion("assign_price in", values, "assignPrice");
            return (Criteria) this;
        }

        public Criteria andAssignPriceNotIn(List<BigDecimal> values) {
            addCriterion("assign_price not in", values, "assignPrice");
            return (Criteria) this;
        }

        public Criteria andAssignPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("assign_price between", value1, value2, "assignPrice");
            return (Criteria) this;
        }

        public Criteria andAssignPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("assign_price not between", value1, value2, "assignPrice");
            return (Criteria) this;
        }

        public Criteria andAssignPayIsNull() {
            addCriterion("assign_pay is null");
            return (Criteria) this;
        }

        public Criteria andAssignPayIsNotNull() {
            addCriterion("assign_pay is not null");
            return (Criteria) this;
        }

        public Criteria andAssignPayEqualTo(BigDecimal value) {
            addCriterion("assign_pay =", value, "assignPay");
            return (Criteria) this;
        }

        public Criteria andAssignPayNotEqualTo(BigDecimal value) {
            addCriterion("assign_pay <>", value, "assignPay");
            return (Criteria) this;
        }

        public Criteria andAssignPayGreaterThan(BigDecimal value) {
            addCriterion("assign_pay >", value, "assignPay");
            return (Criteria) this;
        }

        public Criteria andAssignPayGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("assign_pay >=", value, "assignPay");
            return (Criteria) this;
        }

        public Criteria andAssignPayLessThan(BigDecimal value) {
            addCriterion("assign_pay <", value, "assignPay");
            return (Criteria) this;
        }

        public Criteria andAssignPayLessThanOrEqualTo(BigDecimal value) {
            addCriterion("assign_pay <=", value, "assignPay");
            return (Criteria) this;
        }

        public Criteria andAssignPayIn(List<BigDecimal> values) {
            addCriterion("assign_pay in", values, "assignPay");
            return (Criteria) this;
        }

        public Criteria andAssignPayNotIn(List<BigDecimal> values) {
            addCriterion("assign_pay not in", values, "assignPay");
            return (Criteria) this;
        }

        public Criteria andAssignPayBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("assign_pay between", value1, value2, "assignPay");
            return (Criteria) this;
        }

        public Criteria andAssignPayNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("assign_pay not between", value1, value2, "assignPay");
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

        public Criteria andAssignTypeIsNull() {
            addCriterion("assign_type is null");
            return (Criteria) this;
        }

        public Criteria andAssignTypeIsNotNull() {
            addCriterion("assign_type is not null");
            return (Criteria) this;
        }

        public Criteria andAssignTypeEqualTo(Integer value) {
            addCriterion("assign_type =", value, "assignType");
            return (Criteria) this;
        }

        public Criteria andAssignTypeNotEqualTo(Integer value) {
            addCriterion("assign_type <>", value, "assignType");
            return (Criteria) this;
        }

        public Criteria andAssignTypeGreaterThan(Integer value) {
            addCriterion("assign_type >", value, "assignType");
            return (Criteria) this;
        }

        public Criteria andAssignTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("assign_type >=", value, "assignType");
            return (Criteria) this;
        }

        public Criteria andAssignTypeLessThan(Integer value) {
            addCriterion("assign_type <", value, "assignType");
            return (Criteria) this;
        }

        public Criteria andAssignTypeLessThanOrEqualTo(Integer value) {
            addCriterion("assign_type <=", value, "assignType");
            return (Criteria) this;
        }

        public Criteria andAssignTypeIn(List<Integer> values) {
            addCriterion("assign_type in", values, "assignType");
            return (Criteria) this;
        }

        public Criteria andAssignTypeNotIn(List<Integer> values) {
            addCriterion("assign_type not in", values, "assignType");
            return (Criteria) this;
        }

        public Criteria andAssignTypeBetween(Integer value1, Integer value2) {
            addCriterion("assign_type between", value1, value2, "assignType");
            return (Criteria) this;
        }

        public Criteria andAssignTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("assign_type not between", value1, value2, "assignType");
            return (Criteria) this;
        }

        public Criteria andTenderTypeIsNull() {
            addCriterion("tender_type is null");
            return (Criteria) this;
        }

        public Criteria andTenderTypeIsNotNull() {
            addCriterion("tender_type is not null");
            return (Criteria) this;
        }

        public Criteria andTenderTypeEqualTo(Integer value) {
            addCriterion("tender_type =", value, "tenderType");
            return (Criteria) this;
        }

        public Criteria andTenderTypeNotEqualTo(Integer value) {
            addCriterion("tender_type <>", value, "tenderType");
            return (Criteria) this;
        }

        public Criteria andTenderTypeGreaterThan(Integer value) {
            addCriterion("tender_type >", value, "tenderType");
            return (Criteria) this;
        }

        public Criteria andTenderTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("tender_type >=", value, "tenderType");
            return (Criteria) this;
        }

        public Criteria andTenderTypeLessThan(Integer value) {
            addCriterion("tender_type <", value, "tenderType");
            return (Criteria) this;
        }

        public Criteria andTenderTypeLessThanOrEqualTo(Integer value) {
            addCriterion("tender_type <=", value, "tenderType");
            return (Criteria) this;
        }

        public Criteria andTenderTypeIn(List<Integer> values) {
            addCriterion("tender_type in", values, "tenderType");
            return (Criteria) this;
        }

        public Criteria andTenderTypeNotIn(List<Integer> values) {
            addCriterion("tender_type not in", values, "tenderType");
            return (Criteria) this;
        }

        public Criteria andTenderTypeBetween(Integer value1, Integer value2) {
            addCriterion("tender_type between", value1, value2, "tenderType");
            return (Criteria) this;
        }

        public Criteria andTenderTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("tender_type not between", value1, value2, "tenderType");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("`status` is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("`status` is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("`status` =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("`status` <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("`status` >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("`status` >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("`status` <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("`status` <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("`status` in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("`status` not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("`status` between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("`status` not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andAssignServiceAprIsNull() {
            addCriterion("assign_service_apr is null");
            return (Criteria) this;
        }

        public Criteria andAssignServiceAprIsNotNull() {
            addCriterion("assign_service_apr is not null");
            return (Criteria) this;
        }

        public Criteria andAssignServiceAprEqualTo(BigDecimal value) {
            addCriterion("assign_service_apr =", value, "assignServiceApr");
            return (Criteria) this;
        }

        public Criteria andAssignServiceAprNotEqualTo(BigDecimal value) {
            addCriterion("assign_service_apr <>", value, "assignServiceApr");
            return (Criteria) this;
        }

        public Criteria andAssignServiceAprGreaterThan(BigDecimal value) {
            addCriterion("assign_service_apr >", value, "assignServiceApr");
            return (Criteria) this;
        }

        public Criteria andAssignServiceAprGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("assign_service_apr >=", value, "assignServiceApr");
            return (Criteria) this;
        }

        public Criteria andAssignServiceAprLessThan(BigDecimal value) {
            addCriterion("assign_service_apr <", value, "assignServiceApr");
            return (Criteria) this;
        }

        public Criteria andAssignServiceAprLessThanOrEqualTo(BigDecimal value) {
            addCriterion("assign_service_apr <=", value, "assignServiceApr");
            return (Criteria) this;
        }

        public Criteria andAssignServiceAprIn(List<BigDecimal> values) {
            addCriterion("assign_service_apr in", values, "assignServiceApr");
            return (Criteria) this;
        }

        public Criteria andAssignServiceAprNotIn(List<BigDecimal> values) {
            addCriterion("assign_service_apr not in", values, "assignServiceApr");
            return (Criteria) this;
        }

        public Criteria andAssignServiceAprBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("assign_service_apr between", value1, value2, "assignServiceApr");
            return (Criteria) this;
        }

        public Criteria andAssignServiceAprNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("assign_service_apr not between", value1, value2, "assignServiceApr");
            return (Criteria) this;
        }

        public Criteria andAssignServiceFeeIsNull() {
            addCriterion("assign_service_fee is null");
            return (Criteria) this;
        }

        public Criteria andAssignServiceFeeIsNotNull() {
            addCriterion("assign_service_fee is not null");
            return (Criteria) this;
        }

        public Criteria andAssignServiceFeeEqualTo(BigDecimal value) {
            addCriterion("assign_service_fee =", value, "assignServiceFee");
            return (Criteria) this;
        }

        public Criteria andAssignServiceFeeNotEqualTo(BigDecimal value) {
            addCriterion("assign_service_fee <>", value, "assignServiceFee");
            return (Criteria) this;
        }

        public Criteria andAssignServiceFeeGreaterThan(BigDecimal value) {
            addCriterion("assign_service_fee >", value, "assignServiceFee");
            return (Criteria) this;
        }

        public Criteria andAssignServiceFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("assign_service_fee >=", value, "assignServiceFee");
            return (Criteria) this;
        }

        public Criteria andAssignServiceFeeLessThan(BigDecimal value) {
            addCriterion("assign_service_fee <", value, "assignServiceFee");
            return (Criteria) this;
        }

        public Criteria andAssignServiceFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("assign_service_fee <=", value, "assignServiceFee");
            return (Criteria) this;
        }

        public Criteria andAssignServiceFeeIn(List<BigDecimal> values) {
            addCriterion("assign_service_fee in", values, "assignServiceFee");
            return (Criteria) this;
        }

        public Criteria andAssignServiceFeeNotIn(List<BigDecimal> values) {
            addCriterion("assign_service_fee not in", values, "assignServiceFee");
            return (Criteria) this;
        }

        public Criteria andAssignServiceFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("assign_service_fee between", value1, value2, "assignServiceFee");
            return (Criteria) this;
        }

        public Criteria andAssignServiceFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("assign_service_fee not between", value1, value2, "assignServiceFee");
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