package com.hyjf.am.trade.dao.model.auto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DebtInvestExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public DebtInvestExample() {
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

        public Criteria andUserAttributeIsNull() {
            addCriterion("user_attribute is null");
            return (Criteria) this;
        }

        public Criteria andUserAttributeIsNotNull() {
            addCriterion("user_attribute is not null");
            return (Criteria) this;
        }

        public Criteria andUserAttributeEqualTo(Integer value) {
            addCriterion("user_attribute =", value, "userAttribute");
            return (Criteria) this;
        }

        public Criteria andUserAttributeNotEqualTo(Integer value) {
            addCriterion("user_attribute <>", value, "userAttribute");
            return (Criteria) this;
        }

        public Criteria andUserAttributeGreaterThan(Integer value) {
            addCriterion("user_attribute >", value, "userAttribute");
            return (Criteria) this;
        }

        public Criteria andUserAttributeGreaterThanOrEqualTo(Integer value) {
            addCriterion("user_attribute >=", value, "userAttribute");
            return (Criteria) this;
        }

        public Criteria andUserAttributeLessThan(Integer value) {
            addCriterion("user_attribute <", value, "userAttribute");
            return (Criteria) this;
        }

        public Criteria andUserAttributeLessThanOrEqualTo(Integer value) {
            addCriterion("user_attribute <=", value, "userAttribute");
            return (Criteria) this;
        }

        public Criteria andUserAttributeIn(List<Integer> values) {
            addCriterion("user_attribute in", values, "userAttribute");
            return (Criteria) this;
        }

        public Criteria andUserAttributeNotIn(List<Integer> values) {
            addCriterion("user_attribute not in", values, "userAttribute");
            return (Criteria) this;
        }

        public Criteria andUserAttributeBetween(Integer value1, Integer value2) {
            addCriterion("user_attribute between", value1, value2, "userAttribute");
            return (Criteria) this;
        }

        public Criteria andUserAttributeNotBetween(Integer value1, Integer value2) {
            addCriterion("user_attribute not between", value1, value2, "userAttribute");
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

        public Criteria andPlanOrderIdIsNull() {
            addCriterion("plan_order_id is null");
            return (Criteria) this;
        }

        public Criteria andPlanOrderIdIsNotNull() {
            addCriterion("plan_order_id is not null");
            return (Criteria) this;
        }

        public Criteria andPlanOrderIdEqualTo(String value) {
            addCriterion("plan_order_id =", value, "planOrderId");
            return (Criteria) this;
        }

        public Criteria andPlanOrderIdNotEqualTo(String value) {
            addCriterion("plan_order_id <>", value, "planOrderId");
            return (Criteria) this;
        }

        public Criteria andPlanOrderIdGreaterThan(String value) {
            addCriterion("plan_order_id >", value, "planOrderId");
            return (Criteria) this;
        }

        public Criteria andPlanOrderIdGreaterThanOrEqualTo(String value) {
            addCriterion("plan_order_id >=", value, "planOrderId");
            return (Criteria) this;
        }

        public Criteria andPlanOrderIdLessThan(String value) {
            addCriterion("plan_order_id <", value, "planOrderId");
            return (Criteria) this;
        }

        public Criteria andPlanOrderIdLessThanOrEqualTo(String value) {
            addCriterion("plan_order_id <=", value, "planOrderId");
            return (Criteria) this;
        }

        public Criteria andPlanOrderIdLike(String value) {
            addCriterion("plan_order_id like", value, "planOrderId");
            return (Criteria) this;
        }

        public Criteria andPlanOrderIdNotLike(String value) {
            addCriterion("plan_order_id not like", value, "planOrderId");
            return (Criteria) this;
        }

        public Criteria andPlanOrderIdIn(List<String> values) {
            addCriterion("plan_order_id in", values, "planOrderId");
            return (Criteria) this;
        }

        public Criteria andPlanOrderIdNotIn(List<String> values) {
            addCriterion("plan_order_id not in", values, "planOrderId");
            return (Criteria) this;
        }

        public Criteria andPlanOrderIdBetween(String value1, String value2) {
            addCriterion("plan_order_id between", value1, value2, "planOrderId");
            return (Criteria) this;
        }

        public Criteria andPlanOrderIdNotBetween(String value1, String value2) {
            addCriterion("plan_order_id not between", value1, value2, "planOrderId");
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

        public Criteria andOrderIdIsNull() {
            addCriterion("order_id is null");
            return (Criteria) this;
        }

        public Criteria andOrderIdIsNotNull() {
            addCriterion("order_id is not null");
            return (Criteria) this;
        }

        public Criteria andOrderIdEqualTo(String value) {
            addCriterion("order_id =", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotEqualTo(String value) {
            addCriterion("order_id <>", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThan(String value) {
            addCriterion("order_id >", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThanOrEqualTo(String value) {
            addCriterion("order_id >=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThan(String value) {
            addCriterion("order_id <", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThanOrEqualTo(String value) {
            addCriterion("order_id <=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLike(String value) {
            addCriterion("order_id like", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotLike(String value) {
            addCriterion("order_id not like", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdIn(List<String> values) {
            addCriterion("order_id in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotIn(List<String> values) {
            addCriterion("order_id not in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdBetween(String value1, String value2) {
            addCriterion("order_id between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotBetween(String value1, String value2) {
            addCriterion("order_id not between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderDateIsNull() {
            addCriterion("order_date is null");
            return (Criteria) this;
        }

        public Criteria andOrderDateIsNotNull() {
            addCriterion("order_date is not null");
            return (Criteria) this;
        }

        public Criteria andOrderDateEqualTo(String value) {
            addCriterion("order_date =", value, "orderDate");
            return (Criteria) this;
        }

        public Criteria andOrderDateNotEqualTo(String value) {
            addCriterion("order_date <>", value, "orderDate");
            return (Criteria) this;
        }

        public Criteria andOrderDateGreaterThan(String value) {
            addCriterion("order_date >", value, "orderDate");
            return (Criteria) this;
        }

        public Criteria andOrderDateGreaterThanOrEqualTo(String value) {
            addCriterion("order_date >=", value, "orderDate");
            return (Criteria) this;
        }

        public Criteria andOrderDateLessThan(String value) {
            addCriterion("order_date <", value, "orderDate");
            return (Criteria) this;
        }

        public Criteria andOrderDateLessThanOrEqualTo(String value) {
            addCriterion("order_date <=", value, "orderDate");
            return (Criteria) this;
        }

        public Criteria andOrderDateLike(String value) {
            addCriterion("order_date like", value, "orderDate");
            return (Criteria) this;
        }

        public Criteria andOrderDateNotLike(String value) {
            addCriterion("order_date not like", value, "orderDate");
            return (Criteria) this;
        }

        public Criteria andOrderDateIn(List<String> values) {
            addCriterion("order_date in", values, "orderDate");
            return (Criteria) this;
        }

        public Criteria andOrderDateNotIn(List<String> values) {
            addCriterion("order_date not in", values, "orderDate");
            return (Criteria) this;
        }

        public Criteria andOrderDateBetween(String value1, String value2) {
            addCriterion("order_date between", value1, value2, "orderDate");
            return (Criteria) this;
        }

        public Criteria andOrderDateNotBetween(String value1, String value2) {
            addCriterion("order_date not between", value1, value2, "orderDate");
            return (Criteria) this;
        }

        public Criteria andTrxIdIsNull() {
            addCriterion("trx_id is null");
            return (Criteria) this;
        }

        public Criteria andTrxIdIsNotNull() {
            addCriterion("trx_id is not null");
            return (Criteria) this;
        }

        public Criteria andTrxIdEqualTo(String value) {
            addCriterion("trx_id =", value, "trxId");
            return (Criteria) this;
        }

        public Criteria andTrxIdNotEqualTo(String value) {
            addCriterion("trx_id <>", value, "trxId");
            return (Criteria) this;
        }

        public Criteria andTrxIdGreaterThan(String value) {
            addCriterion("trx_id >", value, "trxId");
            return (Criteria) this;
        }

        public Criteria andTrxIdGreaterThanOrEqualTo(String value) {
            addCriterion("trx_id >=", value, "trxId");
            return (Criteria) this;
        }

        public Criteria andTrxIdLessThan(String value) {
            addCriterion("trx_id <", value, "trxId");
            return (Criteria) this;
        }

        public Criteria andTrxIdLessThanOrEqualTo(String value) {
            addCriterion("trx_id <=", value, "trxId");
            return (Criteria) this;
        }

        public Criteria andTrxIdLike(String value) {
            addCriterion("trx_id like", value, "trxId");
            return (Criteria) this;
        }

        public Criteria andTrxIdNotLike(String value) {
            addCriterion("trx_id not like", value, "trxId");
            return (Criteria) this;
        }

        public Criteria andTrxIdIn(List<String> values) {
            addCriterion("trx_id in", values, "trxId");
            return (Criteria) this;
        }

        public Criteria andTrxIdNotIn(List<String> values) {
            addCriterion("trx_id not in", values, "trxId");
            return (Criteria) this;
        }

        public Criteria andTrxIdBetween(String value1, String value2) {
            addCriterion("trx_id between", value1, value2, "trxId");
            return (Criteria) this;
        }

        public Criteria andTrxIdNotBetween(String value1, String value2) {
            addCriterion("trx_id not between", value1, value2, "trxId");
            return (Criteria) this;
        }

        public Criteria andAccountIsNull() {
            addCriterion("account is null");
            return (Criteria) this;
        }

        public Criteria andAccountIsNotNull() {
            addCriterion("account is not null");
            return (Criteria) this;
        }

        public Criteria andAccountEqualTo(BigDecimal value) {
            addCriterion("account =", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountNotEqualTo(BigDecimal value) {
            addCriterion("account <>", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountGreaterThan(BigDecimal value) {
            addCriterion("account >", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("account >=", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountLessThan(BigDecimal value) {
            addCriterion("account <", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("account <=", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountIn(List<BigDecimal> values) {
            addCriterion("account in", values, "account");
            return (Criteria) this;
        }

        public Criteria andAccountNotIn(List<BigDecimal> values) {
            addCriterion("account not in", values, "account");
            return (Criteria) this;
        }

        public Criteria andAccountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("account between", value1, value2, "account");
            return (Criteria) this;
        }

        public Criteria andAccountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("account not between", value1, value2, "account");
            return (Criteria) this;
        }

        public Criteria andLoanAmountIsNull() {
            addCriterion("loan_amount is null");
            return (Criteria) this;
        }

        public Criteria andLoanAmountIsNotNull() {
            addCriterion("loan_amount is not null");
            return (Criteria) this;
        }

        public Criteria andLoanAmountEqualTo(BigDecimal value) {
            addCriterion("loan_amount =", value, "loanAmount");
            return (Criteria) this;
        }

        public Criteria andLoanAmountNotEqualTo(BigDecimal value) {
            addCriterion("loan_amount <>", value, "loanAmount");
            return (Criteria) this;
        }

        public Criteria andLoanAmountGreaterThan(BigDecimal value) {
            addCriterion("loan_amount >", value, "loanAmount");
            return (Criteria) this;
        }

        public Criteria andLoanAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("loan_amount >=", value, "loanAmount");
            return (Criteria) this;
        }

        public Criteria andLoanAmountLessThan(BigDecimal value) {
            addCriterion("loan_amount <", value, "loanAmount");
            return (Criteria) this;
        }

        public Criteria andLoanAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("loan_amount <=", value, "loanAmount");
            return (Criteria) this;
        }

        public Criteria andLoanAmountIn(List<BigDecimal> values) {
            addCriterion("loan_amount in", values, "loanAmount");
            return (Criteria) this;
        }

        public Criteria andLoanAmountNotIn(List<BigDecimal> values) {
            addCriterion("loan_amount not in", values, "loanAmount");
            return (Criteria) this;
        }

        public Criteria andLoanAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("loan_amount between", value1, value2, "loanAmount");
            return (Criteria) this;
        }

        public Criteria andLoanAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("loan_amount not between", value1, value2, "loanAmount");
            return (Criteria) this;
        }

        public Criteria andLoanFeeIsNull() {
            addCriterion("loan_fee is null");
            return (Criteria) this;
        }

        public Criteria andLoanFeeIsNotNull() {
            addCriterion("loan_fee is not null");
            return (Criteria) this;
        }

        public Criteria andLoanFeeEqualTo(BigDecimal value) {
            addCriterion("loan_fee =", value, "loanFee");
            return (Criteria) this;
        }

        public Criteria andLoanFeeNotEqualTo(BigDecimal value) {
            addCriterion("loan_fee <>", value, "loanFee");
            return (Criteria) this;
        }

        public Criteria andLoanFeeGreaterThan(BigDecimal value) {
            addCriterion("loan_fee >", value, "loanFee");
            return (Criteria) this;
        }

        public Criteria andLoanFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("loan_fee >=", value, "loanFee");
            return (Criteria) this;
        }

        public Criteria andLoanFeeLessThan(BigDecimal value) {
            addCriterion("loan_fee <", value, "loanFee");
            return (Criteria) this;
        }

        public Criteria andLoanFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("loan_fee <=", value, "loanFee");
            return (Criteria) this;
        }

        public Criteria andLoanFeeIn(List<BigDecimal> values) {
            addCriterion("loan_fee in", values, "loanFee");
            return (Criteria) this;
        }

        public Criteria andLoanFeeNotIn(List<BigDecimal> values) {
            addCriterion("loan_fee not in", values, "loanFee");
            return (Criteria) this;
        }

        public Criteria andLoanFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("loan_fee between", value1, value2, "loanFee");
            return (Criteria) this;
        }

        public Criteria andLoanFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("loan_fee not between", value1, value2, "loanFee");
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

        public Criteria andInvestTypeIsNull() {
            addCriterion("invest_type is null");
            return (Criteria) this;
        }

        public Criteria andInvestTypeIsNotNull() {
            addCriterion("invest_type is not null");
            return (Criteria) this;
        }

        public Criteria andInvestTypeEqualTo(Integer value) {
            addCriterion("invest_type =", value, "investType");
            return (Criteria) this;
        }

        public Criteria andInvestTypeNotEqualTo(Integer value) {
            addCriterion("invest_type <>", value, "investType");
            return (Criteria) this;
        }

        public Criteria andInvestTypeGreaterThan(Integer value) {
            addCriterion("invest_type >", value, "investType");
            return (Criteria) this;
        }

        public Criteria andInvestTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("invest_type >=", value, "investType");
            return (Criteria) this;
        }

        public Criteria andInvestTypeLessThan(Integer value) {
            addCriterion("invest_type <", value, "investType");
            return (Criteria) this;
        }

        public Criteria andInvestTypeLessThanOrEqualTo(Integer value) {
            addCriterion("invest_type <=", value, "investType");
            return (Criteria) this;
        }

        public Criteria andInvestTypeIn(List<Integer> values) {
            addCriterion("invest_type in", values, "investType");
            return (Criteria) this;
        }

        public Criteria andInvestTypeNotIn(List<Integer> values) {
            addCriterion("invest_type not in", values, "investType");
            return (Criteria) this;
        }

        public Criteria andInvestTypeBetween(Integer value1, Integer value2) {
            addCriterion("invest_type between", value1, value2, "investType");
            return (Criteria) this;
        }

        public Criteria andInvestTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("invest_type not between", value1, value2, "investType");
            return (Criteria) this;
        }

        public Criteria andRepayTimesIsNull() {
            addCriterion("repay_times is null");
            return (Criteria) this;
        }

        public Criteria andRepayTimesIsNotNull() {
            addCriterion("repay_times is not null");
            return (Criteria) this;
        }

        public Criteria andRepayTimesEqualTo(Integer value) {
            addCriterion("repay_times =", value, "repayTimes");
            return (Criteria) this;
        }

        public Criteria andRepayTimesNotEqualTo(Integer value) {
            addCriterion("repay_times <>", value, "repayTimes");
            return (Criteria) this;
        }

        public Criteria andRepayTimesGreaterThan(Integer value) {
            addCriterion("repay_times >", value, "repayTimes");
            return (Criteria) this;
        }

        public Criteria andRepayTimesGreaterThanOrEqualTo(Integer value) {
            addCriterion("repay_times >=", value, "repayTimes");
            return (Criteria) this;
        }

        public Criteria andRepayTimesLessThan(Integer value) {
            addCriterion("repay_times <", value, "repayTimes");
            return (Criteria) this;
        }

        public Criteria andRepayTimesLessThanOrEqualTo(Integer value) {
            addCriterion("repay_times <=", value, "repayTimes");
            return (Criteria) this;
        }

        public Criteria andRepayTimesIn(List<Integer> values) {
            addCriterion("repay_times in", values, "repayTimes");
            return (Criteria) this;
        }

        public Criteria andRepayTimesNotIn(List<Integer> values) {
            addCriterion("repay_times not in", values, "repayTimes");
            return (Criteria) this;
        }

        public Criteria andRepayTimesBetween(Integer value1, Integer value2) {
            addCriterion("repay_times between", value1, value2, "repayTimes");
            return (Criteria) this;
        }

        public Criteria andRepayTimesNotBetween(Integer value1, Integer value2) {
            addCriterion("repay_times not between", value1, value2, "repayTimes");
            return (Criteria) this;
        }

        public Criteria andInviteUserIdIsNull() {
            addCriterion("invite_user_id is null");
            return (Criteria) this;
        }

        public Criteria andInviteUserIdIsNotNull() {
            addCriterion("invite_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andInviteUserIdEqualTo(Integer value) {
            addCriterion("invite_user_id =", value, "inviteUserId");
            return (Criteria) this;
        }

        public Criteria andInviteUserIdNotEqualTo(Integer value) {
            addCriterion("invite_user_id <>", value, "inviteUserId");
            return (Criteria) this;
        }

        public Criteria andInviteUserIdGreaterThan(Integer value) {
            addCriterion("invite_user_id >", value, "inviteUserId");
            return (Criteria) this;
        }

        public Criteria andInviteUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("invite_user_id >=", value, "inviteUserId");
            return (Criteria) this;
        }

        public Criteria andInviteUserIdLessThan(Integer value) {
            addCriterion("invite_user_id <", value, "inviteUserId");
            return (Criteria) this;
        }

        public Criteria andInviteUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("invite_user_id <=", value, "inviteUserId");
            return (Criteria) this;
        }

        public Criteria andInviteUserIdIn(List<Integer> values) {
            addCriterion("invite_user_id in", values, "inviteUserId");
            return (Criteria) this;
        }

        public Criteria andInviteUserIdNotIn(List<Integer> values) {
            addCriterion("invite_user_id not in", values, "inviteUserId");
            return (Criteria) this;
        }

        public Criteria andInviteUserIdBetween(Integer value1, Integer value2) {
            addCriterion("invite_user_id between", value1, value2, "inviteUserId");
            return (Criteria) this;
        }

        public Criteria andInviteUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("invite_user_id not between", value1, value2, "inviteUserId");
            return (Criteria) this;
        }

        public Criteria andInviteUserNameIsNull() {
            addCriterion("invite_user_name is null");
            return (Criteria) this;
        }

        public Criteria andInviteUserNameIsNotNull() {
            addCriterion("invite_user_name is not null");
            return (Criteria) this;
        }

        public Criteria andInviteUserNameEqualTo(String value) {
            addCriterion("invite_user_name =", value, "inviteUserName");
            return (Criteria) this;
        }

        public Criteria andInviteUserNameNotEqualTo(String value) {
            addCriterion("invite_user_name <>", value, "inviteUserName");
            return (Criteria) this;
        }

        public Criteria andInviteUserNameGreaterThan(String value) {
            addCriterion("invite_user_name >", value, "inviteUserName");
            return (Criteria) this;
        }

        public Criteria andInviteUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("invite_user_name >=", value, "inviteUserName");
            return (Criteria) this;
        }

        public Criteria andInviteUserNameLessThan(String value) {
            addCriterion("invite_user_name <", value, "inviteUserName");
            return (Criteria) this;
        }

        public Criteria andInviteUserNameLessThanOrEqualTo(String value) {
            addCriterion("invite_user_name <=", value, "inviteUserName");
            return (Criteria) this;
        }

        public Criteria andInviteUserNameLike(String value) {
            addCriterion("invite_user_name like", value, "inviteUserName");
            return (Criteria) this;
        }

        public Criteria andInviteUserNameNotLike(String value) {
            addCriterion("invite_user_name not like", value, "inviteUserName");
            return (Criteria) this;
        }

        public Criteria andInviteUserNameIn(List<String> values) {
            addCriterion("invite_user_name in", values, "inviteUserName");
            return (Criteria) this;
        }

        public Criteria andInviteUserNameNotIn(List<String> values) {
            addCriterion("invite_user_name not in", values, "inviteUserName");
            return (Criteria) this;
        }

        public Criteria andInviteUserNameBetween(String value1, String value2) {
            addCriterion("invite_user_name between", value1, value2, "inviteUserName");
            return (Criteria) this;
        }

        public Criteria andInviteUserNameNotBetween(String value1, String value2) {
            addCriterion("invite_user_name not between", value1, value2, "inviteUserName");
            return (Criteria) this;
        }

        public Criteria andInviteUserAttributeIsNull() {
            addCriterion("invite_user_attribute is null");
            return (Criteria) this;
        }

        public Criteria andInviteUserAttributeIsNotNull() {
            addCriterion("invite_user_attribute is not null");
            return (Criteria) this;
        }

        public Criteria andInviteUserAttributeEqualTo(Integer value) {
            addCriterion("invite_user_attribute =", value, "inviteUserAttribute");
            return (Criteria) this;
        }

        public Criteria andInviteUserAttributeNotEqualTo(Integer value) {
            addCriterion("invite_user_attribute <>", value, "inviteUserAttribute");
            return (Criteria) this;
        }

        public Criteria andInviteUserAttributeGreaterThan(Integer value) {
            addCriterion("invite_user_attribute >", value, "inviteUserAttribute");
            return (Criteria) this;
        }

        public Criteria andInviteUserAttributeGreaterThanOrEqualTo(Integer value) {
            addCriterion("invite_user_attribute >=", value, "inviteUserAttribute");
            return (Criteria) this;
        }

        public Criteria andInviteUserAttributeLessThan(Integer value) {
            addCriterion("invite_user_attribute <", value, "inviteUserAttribute");
            return (Criteria) this;
        }

        public Criteria andInviteUserAttributeLessThanOrEqualTo(Integer value) {
            addCriterion("invite_user_attribute <=", value, "inviteUserAttribute");
            return (Criteria) this;
        }

        public Criteria andInviteUserAttributeIn(List<Integer> values) {
            addCriterion("invite_user_attribute in", values, "inviteUserAttribute");
            return (Criteria) this;
        }

        public Criteria andInviteUserAttributeNotIn(List<Integer> values) {
            addCriterion("invite_user_attribute not in", values, "inviteUserAttribute");
            return (Criteria) this;
        }

        public Criteria andInviteUserAttributeBetween(Integer value1, Integer value2) {
            addCriterion("invite_user_attribute between", value1, value2, "inviteUserAttribute");
            return (Criteria) this;
        }

        public Criteria andInviteUserAttributeNotBetween(Integer value1, Integer value2) {
            addCriterion("invite_user_attribute not between", value1, value2, "inviteUserAttribute");
            return (Criteria) this;
        }

        public Criteria andInviteRegionIdIsNull() {
            addCriterion("invite_region_id is null");
            return (Criteria) this;
        }

        public Criteria andInviteRegionIdIsNotNull() {
            addCriterion("invite_region_id is not null");
            return (Criteria) this;
        }

        public Criteria andInviteRegionIdEqualTo(Integer value) {
            addCriterion("invite_region_id =", value, "inviteRegionId");
            return (Criteria) this;
        }

        public Criteria andInviteRegionIdNotEqualTo(Integer value) {
            addCriterion("invite_region_id <>", value, "inviteRegionId");
            return (Criteria) this;
        }

        public Criteria andInviteRegionIdGreaterThan(Integer value) {
            addCriterion("invite_region_id >", value, "inviteRegionId");
            return (Criteria) this;
        }

        public Criteria andInviteRegionIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("invite_region_id >=", value, "inviteRegionId");
            return (Criteria) this;
        }

        public Criteria andInviteRegionIdLessThan(Integer value) {
            addCriterion("invite_region_id <", value, "inviteRegionId");
            return (Criteria) this;
        }

        public Criteria andInviteRegionIdLessThanOrEqualTo(Integer value) {
            addCriterion("invite_region_id <=", value, "inviteRegionId");
            return (Criteria) this;
        }

        public Criteria andInviteRegionIdIn(List<Integer> values) {
            addCriterion("invite_region_id in", values, "inviteRegionId");
            return (Criteria) this;
        }

        public Criteria andInviteRegionIdNotIn(List<Integer> values) {
            addCriterion("invite_region_id not in", values, "inviteRegionId");
            return (Criteria) this;
        }

        public Criteria andInviteRegionIdBetween(Integer value1, Integer value2) {
            addCriterion("invite_region_id between", value1, value2, "inviteRegionId");
            return (Criteria) this;
        }

        public Criteria andInviteRegionIdNotBetween(Integer value1, Integer value2) {
            addCriterion("invite_region_id not between", value1, value2, "inviteRegionId");
            return (Criteria) this;
        }

        public Criteria andInviteRegionNameIsNull() {
            addCriterion("invite_region_name is null");
            return (Criteria) this;
        }

        public Criteria andInviteRegionNameIsNotNull() {
            addCriterion("invite_region_name is not null");
            return (Criteria) this;
        }

        public Criteria andInviteRegionNameEqualTo(String value) {
            addCriterion("invite_region_name =", value, "inviteRegionName");
            return (Criteria) this;
        }

        public Criteria andInviteRegionNameNotEqualTo(String value) {
            addCriterion("invite_region_name <>", value, "inviteRegionName");
            return (Criteria) this;
        }

        public Criteria andInviteRegionNameGreaterThan(String value) {
            addCriterion("invite_region_name >", value, "inviteRegionName");
            return (Criteria) this;
        }

        public Criteria andInviteRegionNameGreaterThanOrEqualTo(String value) {
            addCriterion("invite_region_name >=", value, "inviteRegionName");
            return (Criteria) this;
        }

        public Criteria andInviteRegionNameLessThan(String value) {
            addCriterion("invite_region_name <", value, "inviteRegionName");
            return (Criteria) this;
        }

        public Criteria andInviteRegionNameLessThanOrEqualTo(String value) {
            addCriterion("invite_region_name <=", value, "inviteRegionName");
            return (Criteria) this;
        }

        public Criteria andInviteRegionNameLike(String value) {
            addCriterion("invite_region_name like", value, "inviteRegionName");
            return (Criteria) this;
        }

        public Criteria andInviteRegionNameNotLike(String value) {
            addCriterion("invite_region_name not like", value, "inviteRegionName");
            return (Criteria) this;
        }

        public Criteria andInviteRegionNameIn(List<String> values) {
            addCriterion("invite_region_name in", values, "inviteRegionName");
            return (Criteria) this;
        }

        public Criteria andInviteRegionNameNotIn(List<String> values) {
            addCriterion("invite_region_name not in", values, "inviteRegionName");
            return (Criteria) this;
        }

        public Criteria andInviteRegionNameBetween(String value1, String value2) {
            addCriterion("invite_region_name between", value1, value2, "inviteRegionName");
            return (Criteria) this;
        }

        public Criteria andInviteRegionNameNotBetween(String value1, String value2) {
            addCriterion("invite_region_name not between", value1, value2, "inviteRegionName");
            return (Criteria) this;
        }

        public Criteria andInviteBranchIdIsNull() {
            addCriterion("invite_branch_id is null");
            return (Criteria) this;
        }

        public Criteria andInviteBranchIdIsNotNull() {
            addCriterion("invite_branch_id is not null");
            return (Criteria) this;
        }

        public Criteria andInviteBranchIdEqualTo(Integer value) {
            addCriterion("invite_branch_id =", value, "inviteBranchId");
            return (Criteria) this;
        }

        public Criteria andInviteBranchIdNotEqualTo(Integer value) {
            addCriterion("invite_branch_id <>", value, "inviteBranchId");
            return (Criteria) this;
        }

        public Criteria andInviteBranchIdGreaterThan(Integer value) {
            addCriterion("invite_branch_id >", value, "inviteBranchId");
            return (Criteria) this;
        }

        public Criteria andInviteBranchIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("invite_branch_id >=", value, "inviteBranchId");
            return (Criteria) this;
        }

        public Criteria andInviteBranchIdLessThan(Integer value) {
            addCriterion("invite_branch_id <", value, "inviteBranchId");
            return (Criteria) this;
        }

        public Criteria andInviteBranchIdLessThanOrEqualTo(Integer value) {
            addCriterion("invite_branch_id <=", value, "inviteBranchId");
            return (Criteria) this;
        }

        public Criteria andInviteBranchIdIn(List<Integer> values) {
            addCriterion("invite_branch_id in", values, "inviteBranchId");
            return (Criteria) this;
        }

        public Criteria andInviteBranchIdNotIn(List<Integer> values) {
            addCriterion("invite_branch_id not in", values, "inviteBranchId");
            return (Criteria) this;
        }

        public Criteria andInviteBranchIdBetween(Integer value1, Integer value2) {
            addCriterion("invite_branch_id between", value1, value2, "inviteBranchId");
            return (Criteria) this;
        }

        public Criteria andInviteBranchIdNotBetween(Integer value1, Integer value2) {
            addCriterion("invite_branch_id not between", value1, value2, "inviteBranchId");
            return (Criteria) this;
        }

        public Criteria andInviteBranchNameIsNull() {
            addCriterion("invite_branch_name is null");
            return (Criteria) this;
        }

        public Criteria andInviteBranchNameIsNotNull() {
            addCriterion("invite_branch_name is not null");
            return (Criteria) this;
        }

        public Criteria andInviteBranchNameEqualTo(String value) {
            addCriterion("invite_branch_name =", value, "inviteBranchName");
            return (Criteria) this;
        }

        public Criteria andInviteBranchNameNotEqualTo(String value) {
            addCriterion("invite_branch_name <>", value, "inviteBranchName");
            return (Criteria) this;
        }

        public Criteria andInviteBranchNameGreaterThan(String value) {
            addCriterion("invite_branch_name >", value, "inviteBranchName");
            return (Criteria) this;
        }

        public Criteria andInviteBranchNameGreaterThanOrEqualTo(String value) {
            addCriterion("invite_branch_name >=", value, "inviteBranchName");
            return (Criteria) this;
        }

        public Criteria andInviteBranchNameLessThan(String value) {
            addCriterion("invite_branch_name <", value, "inviteBranchName");
            return (Criteria) this;
        }

        public Criteria andInviteBranchNameLessThanOrEqualTo(String value) {
            addCriterion("invite_branch_name <=", value, "inviteBranchName");
            return (Criteria) this;
        }

        public Criteria andInviteBranchNameLike(String value) {
            addCriterion("invite_branch_name like", value, "inviteBranchName");
            return (Criteria) this;
        }

        public Criteria andInviteBranchNameNotLike(String value) {
            addCriterion("invite_branch_name not like", value, "inviteBranchName");
            return (Criteria) this;
        }

        public Criteria andInviteBranchNameIn(List<String> values) {
            addCriterion("invite_branch_name in", values, "inviteBranchName");
            return (Criteria) this;
        }

        public Criteria andInviteBranchNameNotIn(List<String> values) {
            addCriterion("invite_branch_name not in", values, "inviteBranchName");
            return (Criteria) this;
        }

        public Criteria andInviteBranchNameBetween(String value1, String value2) {
            addCriterion("invite_branch_name between", value1, value2, "inviteBranchName");
            return (Criteria) this;
        }

        public Criteria andInviteBranchNameNotBetween(String value1, String value2) {
            addCriterion("invite_branch_name not between", value1, value2, "inviteBranchName");
            return (Criteria) this;
        }

        public Criteria andInviteDepartmentIdIsNull() {
            addCriterion("invite_department_id is null");
            return (Criteria) this;
        }

        public Criteria andInviteDepartmentIdIsNotNull() {
            addCriterion("invite_department_id is not null");
            return (Criteria) this;
        }

        public Criteria andInviteDepartmentIdEqualTo(Integer value) {
            addCriterion("invite_department_id =", value, "inviteDepartmentId");
            return (Criteria) this;
        }

        public Criteria andInviteDepartmentIdNotEqualTo(Integer value) {
            addCriterion("invite_department_id <>", value, "inviteDepartmentId");
            return (Criteria) this;
        }

        public Criteria andInviteDepartmentIdGreaterThan(Integer value) {
            addCriterion("invite_department_id >", value, "inviteDepartmentId");
            return (Criteria) this;
        }

        public Criteria andInviteDepartmentIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("invite_department_id >=", value, "inviteDepartmentId");
            return (Criteria) this;
        }

        public Criteria andInviteDepartmentIdLessThan(Integer value) {
            addCriterion("invite_department_id <", value, "inviteDepartmentId");
            return (Criteria) this;
        }

        public Criteria andInviteDepartmentIdLessThanOrEqualTo(Integer value) {
            addCriterion("invite_department_id <=", value, "inviteDepartmentId");
            return (Criteria) this;
        }

        public Criteria andInviteDepartmentIdIn(List<Integer> values) {
            addCriterion("invite_department_id in", values, "inviteDepartmentId");
            return (Criteria) this;
        }

        public Criteria andInviteDepartmentIdNotIn(List<Integer> values) {
            addCriterion("invite_department_id not in", values, "inviteDepartmentId");
            return (Criteria) this;
        }

        public Criteria andInviteDepartmentIdBetween(Integer value1, Integer value2) {
            addCriterion("invite_department_id between", value1, value2, "inviteDepartmentId");
            return (Criteria) this;
        }

        public Criteria andInviteDepartmentIdNotBetween(Integer value1, Integer value2) {
            addCriterion("invite_department_id not between", value1, value2, "inviteDepartmentId");
            return (Criteria) this;
        }

        public Criteria andInviteDepartmentNameIsNull() {
            addCriterion("invite_department_name is null");
            return (Criteria) this;
        }

        public Criteria andInviteDepartmentNameIsNotNull() {
            addCriterion("invite_department_name is not null");
            return (Criteria) this;
        }

        public Criteria andInviteDepartmentNameEqualTo(String value) {
            addCriterion("invite_department_name =", value, "inviteDepartmentName");
            return (Criteria) this;
        }

        public Criteria andInviteDepartmentNameNotEqualTo(String value) {
            addCriterion("invite_department_name <>", value, "inviteDepartmentName");
            return (Criteria) this;
        }

        public Criteria andInviteDepartmentNameGreaterThan(String value) {
            addCriterion("invite_department_name >", value, "inviteDepartmentName");
            return (Criteria) this;
        }

        public Criteria andInviteDepartmentNameGreaterThanOrEqualTo(String value) {
            addCriterion("invite_department_name >=", value, "inviteDepartmentName");
            return (Criteria) this;
        }

        public Criteria andInviteDepartmentNameLessThan(String value) {
            addCriterion("invite_department_name <", value, "inviteDepartmentName");
            return (Criteria) this;
        }

        public Criteria andInviteDepartmentNameLessThanOrEqualTo(String value) {
            addCriterion("invite_department_name <=", value, "inviteDepartmentName");
            return (Criteria) this;
        }

        public Criteria andInviteDepartmentNameLike(String value) {
            addCriterion("invite_department_name like", value, "inviteDepartmentName");
            return (Criteria) this;
        }

        public Criteria andInviteDepartmentNameNotLike(String value) {
            addCriterion("invite_department_name not like", value, "inviteDepartmentName");
            return (Criteria) this;
        }

        public Criteria andInviteDepartmentNameIn(List<String> values) {
            addCriterion("invite_department_name in", values, "inviteDepartmentName");
            return (Criteria) this;
        }

        public Criteria andInviteDepartmentNameNotIn(List<String> values) {
            addCriterion("invite_department_name not in", values, "inviteDepartmentName");
            return (Criteria) this;
        }

        public Criteria andInviteDepartmentNameBetween(String value1, String value2) {
            addCriterion("invite_department_name between", value1, value2, "inviteDepartmentName");
            return (Criteria) this;
        }

        public Criteria andInviteDepartmentNameNotBetween(String value1, String value2) {
            addCriterion("invite_department_name not between", value1, value2, "inviteDepartmentName");
            return (Criteria) this;
        }

        public Criteria andLoanOrderIdIsNull() {
            addCriterion("loan_order_id is null");
            return (Criteria) this;
        }

        public Criteria andLoanOrderIdIsNotNull() {
            addCriterion("loan_order_id is not null");
            return (Criteria) this;
        }

        public Criteria andLoanOrderIdEqualTo(String value) {
            addCriterion("loan_order_id =", value, "loanOrderId");
            return (Criteria) this;
        }

        public Criteria andLoanOrderIdNotEqualTo(String value) {
            addCriterion("loan_order_id <>", value, "loanOrderId");
            return (Criteria) this;
        }

        public Criteria andLoanOrderIdGreaterThan(String value) {
            addCriterion("loan_order_id >", value, "loanOrderId");
            return (Criteria) this;
        }

        public Criteria andLoanOrderIdGreaterThanOrEqualTo(String value) {
            addCriterion("loan_order_id >=", value, "loanOrderId");
            return (Criteria) this;
        }

        public Criteria andLoanOrderIdLessThan(String value) {
            addCriterion("loan_order_id <", value, "loanOrderId");
            return (Criteria) this;
        }

        public Criteria andLoanOrderIdLessThanOrEqualTo(String value) {
            addCriterion("loan_order_id <=", value, "loanOrderId");
            return (Criteria) this;
        }

        public Criteria andLoanOrderIdLike(String value) {
            addCriterion("loan_order_id like", value, "loanOrderId");
            return (Criteria) this;
        }

        public Criteria andLoanOrderIdNotLike(String value) {
            addCriterion("loan_order_id not like", value, "loanOrderId");
            return (Criteria) this;
        }

        public Criteria andLoanOrderIdIn(List<String> values) {
            addCriterion("loan_order_id in", values, "loanOrderId");
            return (Criteria) this;
        }

        public Criteria andLoanOrderIdNotIn(List<String> values) {
            addCriterion("loan_order_id not in", values, "loanOrderId");
            return (Criteria) this;
        }

        public Criteria andLoanOrderIdBetween(String value1, String value2) {
            addCriterion("loan_order_id between", value1, value2, "loanOrderId");
            return (Criteria) this;
        }

        public Criteria andLoanOrderIdNotBetween(String value1, String value2) {
            addCriterion("loan_order_id not between", value1, value2, "loanOrderId");
            return (Criteria) this;
        }

        public Criteria andLoanOrderDateIsNull() {
            addCriterion("loan_order_date is null");
            return (Criteria) this;
        }

        public Criteria andLoanOrderDateIsNotNull() {
            addCriterion("loan_order_date is not null");
            return (Criteria) this;
        }

        public Criteria andLoanOrderDateEqualTo(String value) {
            addCriterion("loan_order_date =", value, "loanOrderDate");
            return (Criteria) this;
        }

        public Criteria andLoanOrderDateNotEqualTo(String value) {
            addCriterion("loan_order_date <>", value, "loanOrderDate");
            return (Criteria) this;
        }

        public Criteria andLoanOrderDateGreaterThan(String value) {
            addCriterion("loan_order_date >", value, "loanOrderDate");
            return (Criteria) this;
        }

        public Criteria andLoanOrderDateGreaterThanOrEqualTo(String value) {
            addCriterion("loan_order_date >=", value, "loanOrderDate");
            return (Criteria) this;
        }

        public Criteria andLoanOrderDateLessThan(String value) {
            addCriterion("loan_order_date <", value, "loanOrderDate");
            return (Criteria) this;
        }

        public Criteria andLoanOrderDateLessThanOrEqualTo(String value) {
            addCriterion("loan_order_date <=", value, "loanOrderDate");
            return (Criteria) this;
        }

        public Criteria andLoanOrderDateLike(String value) {
            addCriterion("loan_order_date like", value, "loanOrderDate");
            return (Criteria) this;
        }

        public Criteria andLoanOrderDateNotLike(String value) {
            addCriterion("loan_order_date not like", value, "loanOrderDate");
            return (Criteria) this;
        }

        public Criteria andLoanOrderDateIn(List<String> values) {
            addCriterion("loan_order_date in", values, "loanOrderDate");
            return (Criteria) this;
        }

        public Criteria andLoanOrderDateNotIn(List<String> values) {
            addCriterion("loan_order_date not in", values, "loanOrderDate");
            return (Criteria) this;
        }

        public Criteria andLoanOrderDateBetween(String value1, String value2) {
            addCriterion("loan_order_date between", value1, value2, "loanOrderDate");
            return (Criteria) this;
        }

        public Criteria andLoanOrderDateNotBetween(String value1, String value2) {
            addCriterion("loan_order_date not between", value1, value2, "loanOrderDate");
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

        public Criteria andWebIsNull() {
            addCriterion("web is null");
            return (Criteria) this;
        }

        public Criteria andWebIsNotNull() {
            addCriterion("web is not null");
            return (Criteria) this;
        }

        public Criteria andWebEqualTo(Integer value) {
            addCriterion("web =", value, "web");
            return (Criteria) this;
        }

        public Criteria andWebNotEqualTo(Integer value) {
            addCriterion("web <>", value, "web");
            return (Criteria) this;
        }

        public Criteria andWebGreaterThan(Integer value) {
            addCriterion("web >", value, "web");
            return (Criteria) this;
        }

        public Criteria andWebGreaterThanOrEqualTo(Integer value) {
            addCriterion("web >=", value, "web");
            return (Criteria) this;
        }

        public Criteria andWebLessThan(Integer value) {
            addCriterion("web <", value, "web");
            return (Criteria) this;
        }

        public Criteria andWebLessThanOrEqualTo(Integer value) {
            addCriterion("web <=", value, "web");
            return (Criteria) this;
        }

        public Criteria andWebIn(List<Integer> values) {
            addCriterion("web in", values, "web");
            return (Criteria) this;
        }

        public Criteria andWebNotIn(List<Integer> values) {
            addCriterion("web not in", values, "web");
            return (Criteria) this;
        }

        public Criteria andWebBetween(Integer value1, Integer value2) {
            addCriterion("web between", value1, value2, "web");
            return (Criteria) this;
        }

        public Criteria andWebNotBetween(Integer value1, Integer value2) {
            addCriterion("web not between", value1, value2, "web");
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

        public Criteria andAddIpIsNull() {
            addCriterion("add_ip is null");
            return (Criteria) this;
        }

        public Criteria andAddIpIsNotNull() {
            addCriterion("add_ip is not null");
            return (Criteria) this;
        }

        public Criteria andAddIpEqualTo(String value) {
            addCriterion("add_ip =", value, "addIp");
            return (Criteria) this;
        }

        public Criteria andAddIpNotEqualTo(String value) {
            addCriterion("add_ip <>", value, "addIp");
            return (Criteria) this;
        }

        public Criteria andAddIpGreaterThan(String value) {
            addCriterion("add_ip >", value, "addIp");
            return (Criteria) this;
        }

        public Criteria andAddIpGreaterThanOrEqualTo(String value) {
            addCriterion("add_ip >=", value, "addIp");
            return (Criteria) this;
        }

        public Criteria andAddIpLessThan(String value) {
            addCriterion("add_ip <", value, "addIp");
            return (Criteria) this;
        }

        public Criteria andAddIpLessThanOrEqualTo(String value) {
            addCriterion("add_ip <=", value, "addIp");
            return (Criteria) this;
        }

        public Criteria andAddIpLike(String value) {
            addCriterion("add_ip like", value, "addIp");
            return (Criteria) this;
        }

        public Criteria andAddIpNotLike(String value) {
            addCriterion("add_ip not like", value, "addIp");
            return (Criteria) this;
        }

        public Criteria andAddIpIn(List<String> values) {
            addCriterion("add_ip in", values, "addIp");
            return (Criteria) this;
        }

        public Criteria andAddIpNotIn(List<String> values) {
            addCriterion("add_ip not in", values, "addIp");
            return (Criteria) this;
        }

        public Criteria andAddIpBetween(String value1, String value2) {
            addCriterion("add_ip between", value1, value2, "addIp");
            return (Criteria) this;
        }

        public Criteria andAddIpNotBetween(String value1, String value2) {
            addCriterion("add_ip not between", value1, value2, "addIp");
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

        public Criteria andRecoverAdvanceFeeIsNull() {
            addCriterion("recover_advance_fee is null");
            return (Criteria) this;
        }

        public Criteria andRecoverAdvanceFeeIsNotNull() {
            addCriterion("recover_advance_fee is not null");
            return (Criteria) this;
        }

        public Criteria andRecoverAdvanceFeeEqualTo(BigDecimal value) {
            addCriterion("recover_advance_fee =", value, "recoverAdvanceFee");
            return (Criteria) this;
        }

        public Criteria andRecoverAdvanceFeeNotEqualTo(BigDecimal value) {
            addCriterion("recover_advance_fee <>", value, "recoverAdvanceFee");
            return (Criteria) this;
        }

        public Criteria andRecoverAdvanceFeeGreaterThan(BigDecimal value) {
            addCriterion("recover_advance_fee >", value, "recoverAdvanceFee");
            return (Criteria) this;
        }

        public Criteria andRecoverAdvanceFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("recover_advance_fee >=", value, "recoverAdvanceFee");
            return (Criteria) this;
        }

        public Criteria andRecoverAdvanceFeeLessThan(BigDecimal value) {
            addCriterion("recover_advance_fee <", value, "recoverAdvanceFee");
            return (Criteria) this;
        }

        public Criteria andRecoverAdvanceFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("recover_advance_fee <=", value, "recoverAdvanceFee");
            return (Criteria) this;
        }

        public Criteria andRecoverAdvanceFeeIn(List<BigDecimal> values) {
            addCriterion("recover_advance_fee in", values, "recoverAdvanceFee");
            return (Criteria) this;
        }

        public Criteria andRecoverAdvanceFeeNotIn(List<BigDecimal> values) {
            addCriterion("recover_advance_fee not in", values, "recoverAdvanceFee");
            return (Criteria) this;
        }

        public Criteria andRecoverAdvanceFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("recover_advance_fee between", value1, value2, "recoverAdvanceFee");
            return (Criteria) this;
        }

        public Criteria andRecoverAdvanceFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("recover_advance_fee not between", value1, value2, "recoverAdvanceFee");
            return (Criteria) this;
        }

        public Criteria andRecoverLateFeeIsNull() {
            addCriterion("recover_late_fee is null");
            return (Criteria) this;
        }

        public Criteria andRecoverLateFeeIsNotNull() {
            addCriterion("recover_late_fee is not null");
            return (Criteria) this;
        }

        public Criteria andRecoverLateFeeEqualTo(BigDecimal value) {
            addCriterion("recover_late_fee =", value, "recoverLateFee");
            return (Criteria) this;
        }

        public Criteria andRecoverLateFeeNotEqualTo(BigDecimal value) {
            addCriterion("recover_late_fee <>", value, "recoverLateFee");
            return (Criteria) this;
        }

        public Criteria andRecoverLateFeeGreaterThan(BigDecimal value) {
            addCriterion("recover_late_fee >", value, "recoverLateFee");
            return (Criteria) this;
        }

        public Criteria andRecoverLateFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("recover_late_fee >=", value, "recoverLateFee");
            return (Criteria) this;
        }

        public Criteria andRecoverLateFeeLessThan(BigDecimal value) {
            addCriterion("recover_late_fee <", value, "recoverLateFee");
            return (Criteria) this;
        }

        public Criteria andRecoverLateFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("recover_late_fee <=", value, "recoverLateFee");
            return (Criteria) this;
        }

        public Criteria andRecoverLateFeeIn(List<BigDecimal> values) {
            addCriterion("recover_late_fee in", values, "recoverLateFee");
            return (Criteria) this;
        }

        public Criteria andRecoverLateFeeNotIn(List<BigDecimal> values) {
            addCriterion("recover_late_fee not in", values, "recoverLateFee");
            return (Criteria) this;
        }

        public Criteria andRecoverLateFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("recover_late_fee between", value1, value2, "recoverLateFee");
            return (Criteria) this;
        }

        public Criteria andRecoverLateFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("recover_late_fee not between", value1, value2, "recoverLateFee");
            return (Criteria) this;
        }

        public Criteria andTenderAwardFeeIsNull() {
            addCriterion("tender_award_fee is null");
            return (Criteria) this;
        }

        public Criteria andTenderAwardFeeIsNotNull() {
            addCriterion("tender_award_fee is not null");
            return (Criteria) this;
        }

        public Criteria andTenderAwardFeeEqualTo(BigDecimal value) {
            addCriterion("tender_award_fee =", value, "tenderAwardFee");
            return (Criteria) this;
        }

        public Criteria andTenderAwardFeeNotEqualTo(BigDecimal value) {
            addCriterion("tender_award_fee <>", value, "tenderAwardFee");
            return (Criteria) this;
        }

        public Criteria andTenderAwardFeeGreaterThan(BigDecimal value) {
            addCriterion("tender_award_fee >", value, "tenderAwardFee");
            return (Criteria) this;
        }

        public Criteria andTenderAwardFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("tender_award_fee >=", value, "tenderAwardFee");
            return (Criteria) this;
        }

        public Criteria andTenderAwardFeeLessThan(BigDecimal value) {
            addCriterion("tender_award_fee <", value, "tenderAwardFee");
            return (Criteria) this;
        }

        public Criteria andTenderAwardFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("tender_award_fee <=", value, "tenderAwardFee");
            return (Criteria) this;
        }

        public Criteria andTenderAwardFeeIn(List<BigDecimal> values) {
            addCriterion("tender_award_fee in", values, "tenderAwardFee");
            return (Criteria) this;
        }

        public Criteria andTenderAwardFeeNotIn(List<BigDecimal> values) {
            addCriterion("tender_award_fee not in", values, "tenderAwardFee");
            return (Criteria) this;
        }

        public Criteria andTenderAwardFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("tender_award_fee between", value1, value2, "tenderAwardFee");
            return (Criteria) this;
        }

        public Criteria andTenderAwardFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("tender_award_fee not between", value1, value2, "tenderAwardFee");
            return (Criteria) this;
        }

        public Criteria andContentsIsNull() {
            addCriterion("contents is null");
            return (Criteria) this;
        }

        public Criteria andContentsIsNotNull() {
            addCriterion("contents is not null");
            return (Criteria) this;
        }

        public Criteria andContentsEqualTo(String value) {
            addCriterion("contents =", value, "contents");
            return (Criteria) this;
        }

        public Criteria andContentsNotEqualTo(String value) {
            addCriterion("contents <>", value, "contents");
            return (Criteria) this;
        }

        public Criteria andContentsGreaterThan(String value) {
            addCriterion("contents >", value, "contents");
            return (Criteria) this;
        }

        public Criteria andContentsGreaterThanOrEqualTo(String value) {
            addCriterion("contents >=", value, "contents");
            return (Criteria) this;
        }

        public Criteria andContentsLessThan(String value) {
            addCriterion("contents <", value, "contents");
            return (Criteria) this;
        }

        public Criteria andContentsLessThanOrEqualTo(String value) {
            addCriterion("contents <=", value, "contents");
            return (Criteria) this;
        }

        public Criteria andContentsLike(String value) {
            addCriterion("contents like", value, "contents");
            return (Criteria) this;
        }

        public Criteria andContentsNotLike(String value) {
            addCriterion("contents not like", value, "contents");
            return (Criteria) this;
        }

        public Criteria andContentsIn(List<String> values) {
            addCriterion("contents in", values, "contents");
            return (Criteria) this;
        }

        public Criteria andContentsNotIn(List<String> values) {
            addCriterion("contents not in", values, "contents");
            return (Criteria) this;
        }

        public Criteria andContentsBetween(String value1, String value2) {
            addCriterion("contents between", value1, value2, "contents");
            return (Criteria) this;
        }

        public Criteria andContentsNotBetween(String value1, String value2) {
            addCriterion("contents not between", value1, value2, "contents");
            return (Criteria) this;
        }

        public Criteria andRecoverFullStatusIsNull() {
            addCriterion("recover_full_status is null");
            return (Criteria) this;
        }

        public Criteria andRecoverFullStatusIsNotNull() {
            addCriterion("recover_full_status is not null");
            return (Criteria) this;
        }

        public Criteria andRecoverFullStatusEqualTo(Integer value) {
            addCriterion("recover_full_status =", value, "recoverFullStatus");
            return (Criteria) this;
        }

        public Criteria andRecoverFullStatusNotEqualTo(Integer value) {
            addCriterion("recover_full_status <>", value, "recoverFullStatus");
            return (Criteria) this;
        }

        public Criteria andRecoverFullStatusGreaterThan(Integer value) {
            addCriterion("recover_full_status >", value, "recoverFullStatus");
            return (Criteria) this;
        }

        public Criteria andRecoverFullStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("recover_full_status >=", value, "recoverFullStatus");
            return (Criteria) this;
        }

        public Criteria andRecoverFullStatusLessThan(Integer value) {
            addCriterion("recover_full_status <", value, "recoverFullStatus");
            return (Criteria) this;
        }

        public Criteria andRecoverFullStatusLessThanOrEqualTo(Integer value) {
            addCriterion("recover_full_status <=", value, "recoverFullStatus");
            return (Criteria) this;
        }

        public Criteria andRecoverFullStatusIn(List<Integer> values) {
            addCriterion("recover_full_status in", values, "recoverFullStatus");
            return (Criteria) this;
        }

        public Criteria andRecoverFullStatusNotIn(List<Integer> values) {
            addCriterion("recover_full_status not in", values, "recoverFullStatus");
            return (Criteria) this;
        }

        public Criteria andRecoverFullStatusBetween(Integer value1, Integer value2) {
            addCriterion("recover_full_status between", value1, value2, "recoverFullStatus");
            return (Criteria) this;
        }

        public Criteria andRecoverFullStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("recover_full_status not between", value1, value2, "recoverFullStatus");
            return (Criteria) this;
        }

        public Criteria andRecoverFeeIsNull() {
            addCriterion("recover_fee is null");
            return (Criteria) this;
        }

        public Criteria andRecoverFeeIsNotNull() {
            addCriterion("recover_fee is not null");
            return (Criteria) this;
        }

        public Criteria andRecoverFeeEqualTo(BigDecimal value) {
            addCriterion("recover_fee =", value, "recoverFee");
            return (Criteria) this;
        }

        public Criteria andRecoverFeeNotEqualTo(BigDecimal value) {
            addCriterion("recover_fee <>", value, "recoverFee");
            return (Criteria) this;
        }

        public Criteria andRecoverFeeGreaterThan(BigDecimal value) {
            addCriterion("recover_fee >", value, "recoverFee");
            return (Criteria) this;
        }

        public Criteria andRecoverFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("recover_fee >=", value, "recoverFee");
            return (Criteria) this;
        }

        public Criteria andRecoverFeeLessThan(BigDecimal value) {
            addCriterion("recover_fee <", value, "recoverFee");
            return (Criteria) this;
        }

        public Criteria andRecoverFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("recover_fee <=", value, "recoverFee");
            return (Criteria) this;
        }

        public Criteria andRecoverFeeIn(List<BigDecimal> values) {
            addCriterion("recover_fee in", values, "recoverFee");
            return (Criteria) this;
        }

        public Criteria andRecoverFeeNotIn(List<BigDecimal> values) {
            addCriterion("recover_fee not in", values, "recoverFee");
            return (Criteria) this;
        }

        public Criteria andRecoverFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("recover_fee between", value1, value2, "recoverFee");
            return (Criteria) this;
        }

        public Criteria andRecoverFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("recover_fee not between", value1, value2, "recoverFee");
            return (Criteria) this;
        }

        public Criteria andRecoverTypeIsNull() {
            addCriterion("recover_type is null");
            return (Criteria) this;
        }

        public Criteria andRecoverTypeIsNotNull() {
            addCriterion("recover_type is not null");
            return (Criteria) this;
        }

        public Criteria andRecoverTypeEqualTo(String value) {
            addCriterion("recover_type =", value, "recoverType");
            return (Criteria) this;
        }

        public Criteria andRecoverTypeNotEqualTo(String value) {
            addCriterion("recover_type <>", value, "recoverType");
            return (Criteria) this;
        }

        public Criteria andRecoverTypeGreaterThan(String value) {
            addCriterion("recover_type >", value, "recoverType");
            return (Criteria) this;
        }

        public Criteria andRecoverTypeGreaterThanOrEqualTo(String value) {
            addCriterion("recover_type >=", value, "recoverType");
            return (Criteria) this;
        }

        public Criteria andRecoverTypeLessThan(String value) {
            addCriterion("recover_type <", value, "recoverType");
            return (Criteria) this;
        }

        public Criteria andRecoverTypeLessThanOrEqualTo(String value) {
            addCriterion("recover_type <=", value, "recoverType");
            return (Criteria) this;
        }

        public Criteria andRecoverTypeLike(String value) {
            addCriterion("recover_type like", value, "recoverType");
            return (Criteria) this;
        }

        public Criteria andRecoverTypeNotLike(String value) {
            addCriterion("recover_type not like", value, "recoverType");
            return (Criteria) this;
        }

        public Criteria andRecoverTypeIn(List<String> values) {
            addCriterion("recover_type in", values, "recoverType");
            return (Criteria) this;
        }

        public Criteria andRecoverTypeNotIn(List<String> values) {
            addCriterion("recover_type not in", values, "recoverType");
            return (Criteria) this;
        }

        public Criteria andRecoverTypeBetween(String value1, String value2) {
            addCriterion("recover_type between", value1, value2, "recoverType");
            return (Criteria) this;
        }

        public Criteria andRecoverTypeNotBetween(String value1, String value2) {
            addCriterion("recover_type not between", value1, value2, "recoverType");
            return (Criteria) this;
        }

        public Criteria andChangeStatusIsNull() {
            addCriterion("change_status is null");
            return (Criteria) this;
        }

        public Criteria andChangeStatusIsNotNull() {
            addCriterion("change_status is not null");
            return (Criteria) this;
        }

        public Criteria andChangeStatusEqualTo(Integer value) {
            addCriterion("change_status =", value, "changeStatus");
            return (Criteria) this;
        }

        public Criteria andChangeStatusNotEqualTo(Integer value) {
            addCriterion("change_status <>", value, "changeStatus");
            return (Criteria) this;
        }

        public Criteria andChangeStatusGreaterThan(Integer value) {
            addCriterion("change_status >", value, "changeStatus");
            return (Criteria) this;
        }

        public Criteria andChangeStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("change_status >=", value, "changeStatus");
            return (Criteria) this;
        }

        public Criteria andChangeStatusLessThan(Integer value) {
            addCriterion("change_status <", value, "changeStatus");
            return (Criteria) this;
        }

        public Criteria andChangeStatusLessThanOrEqualTo(Integer value) {
            addCriterion("change_status <=", value, "changeStatus");
            return (Criteria) this;
        }

        public Criteria andChangeStatusIn(List<Integer> values) {
            addCriterion("change_status in", values, "changeStatus");
            return (Criteria) this;
        }

        public Criteria andChangeStatusNotIn(List<Integer> values) {
            addCriterion("change_status not in", values, "changeStatus");
            return (Criteria) this;
        }

        public Criteria andChangeStatusBetween(Integer value1, Integer value2) {
            addCriterion("change_status between", value1, value2, "changeStatus");
            return (Criteria) this;
        }

        public Criteria andChangeStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("change_status not between", value1, value2, "changeStatus");
            return (Criteria) this;
        }

        public Criteria andChangePeriodIsNull() {
            addCriterion("change_period is null");
            return (Criteria) this;
        }

        public Criteria andChangePeriodIsNotNull() {
            addCriterion("change_period is not null");
            return (Criteria) this;
        }

        public Criteria andChangePeriodEqualTo(Integer value) {
            addCriterion("change_period =", value, "changePeriod");
            return (Criteria) this;
        }

        public Criteria andChangePeriodNotEqualTo(Integer value) {
            addCriterion("change_period <>", value, "changePeriod");
            return (Criteria) this;
        }

        public Criteria andChangePeriodGreaterThan(Integer value) {
            addCriterion("change_period >", value, "changePeriod");
            return (Criteria) this;
        }

        public Criteria andChangePeriodGreaterThanOrEqualTo(Integer value) {
            addCriterion("change_period >=", value, "changePeriod");
            return (Criteria) this;
        }

        public Criteria andChangePeriodLessThan(Integer value) {
            addCriterion("change_period <", value, "changePeriod");
            return (Criteria) this;
        }

        public Criteria andChangePeriodLessThanOrEqualTo(Integer value) {
            addCriterion("change_period <=", value, "changePeriod");
            return (Criteria) this;
        }

        public Criteria andChangePeriodIn(List<Integer> values) {
            addCriterion("change_period in", values, "changePeriod");
            return (Criteria) this;
        }

        public Criteria andChangePeriodNotIn(List<Integer> values) {
            addCriterion("change_period not in", values, "changePeriod");
            return (Criteria) this;
        }

        public Criteria andChangePeriodBetween(Integer value1, Integer value2) {
            addCriterion("change_period between", value1, value2, "changePeriod");
            return (Criteria) this;
        }

        public Criteria andChangePeriodNotBetween(Integer value1, Integer value2) {
            addCriterion("change_period not between", value1, value2, "changePeriod");
            return (Criteria) this;
        }

        public Criteria andChangeUseridIsNull() {
            addCriterion("change_userid is null");
            return (Criteria) this;
        }

        public Criteria andChangeUseridIsNotNull() {
            addCriterion("change_userid is not null");
            return (Criteria) this;
        }

        public Criteria andChangeUseridEqualTo(Integer value) {
            addCriterion("change_userid =", value, "changeUserid");
            return (Criteria) this;
        }

        public Criteria andChangeUseridNotEqualTo(Integer value) {
            addCriterion("change_userid <>", value, "changeUserid");
            return (Criteria) this;
        }

        public Criteria andChangeUseridGreaterThan(Integer value) {
            addCriterion("change_userid >", value, "changeUserid");
            return (Criteria) this;
        }

        public Criteria andChangeUseridGreaterThanOrEqualTo(Integer value) {
            addCriterion("change_userid >=", value, "changeUserid");
            return (Criteria) this;
        }

        public Criteria andChangeUseridLessThan(Integer value) {
            addCriterion("change_userid <", value, "changeUserid");
            return (Criteria) this;
        }

        public Criteria andChangeUseridLessThanOrEqualTo(Integer value) {
            addCriterion("change_userid <=", value, "changeUserid");
            return (Criteria) this;
        }

        public Criteria andChangeUseridIn(List<Integer> values) {
            addCriterion("change_userid in", values, "changeUserid");
            return (Criteria) this;
        }

        public Criteria andChangeUseridNotIn(List<Integer> values) {
            addCriterion("change_userid not in", values, "changeUserid");
            return (Criteria) this;
        }

        public Criteria andChangeUseridBetween(Integer value1, Integer value2) {
            addCriterion("change_userid between", value1, value2, "changeUserid");
            return (Criteria) this;
        }

        public Criteria andChangeUseridNotBetween(Integer value1, Integer value2) {
            addCriterion("change_userid not between", value1, value2, "changeUserid");
            return (Criteria) this;
        }

        public Criteria andTenderAwardAccountIsNull() {
            addCriterion("tender_award_account is null");
            return (Criteria) this;
        }

        public Criteria andTenderAwardAccountIsNotNull() {
            addCriterion("tender_award_account is not null");
            return (Criteria) this;
        }

        public Criteria andTenderAwardAccountEqualTo(BigDecimal value) {
            addCriterion("tender_award_account =", value, "tenderAwardAccount");
            return (Criteria) this;
        }

        public Criteria andTenderAwardAccountNotEqualTo(BigDecimal value) {
            addCriterion("tender_award_account <>", value, "tenderAwardAccount");
            return (Criteria) this;
        }

        public Criteria andTenderAwardAccountGreaterThan(BigDecimal value) {
            addCriterion("tender_award_account >", value, "tenderAwardAccount");
            return (Criteria) this;
        }

        public Criteria andTenderAwardAccountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("tender_award_account >=", value, "tenderAwardAccount");
            return (Criteria) this;
        }

        public Criteria andTenderAwardAccountLessThan(BigDecimal value) {
            addCriterion("tender_award_account <", value, "tenderAwardAccount");
            return (Criteria) this;
        }

        public Criteria andTenderAwardAccountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("tender_award_account <=", value, "tenderAwardAccount");
            return (Criteria) this;
        }

        public Criteria andTenderAwardAccountIn(List<BigDecimal> values) {
            addCriterion("tender_award_account in", values, "tenderAwardAccount");
            return (Criteria) this;
        }

        public Criteria andTenderAwardAccountNotIn(List<BigDecimal> values) {
            addCriterion("tender_award_account not in", values, "tenderAwardAccount");
            return (Criteria) this;
        }

        public Criteria andTenderAwardAccountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("tender_award_account between", value1, value2, "tenderAwardAccount");
            return (Criteria) this;
        }

        public Criteria andTenderAwardAccountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("tender_award_account not between", value1, value2, "tenderAwardAccount");
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