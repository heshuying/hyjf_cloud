package com.hyjf.am.trade.dao.model.auto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BankRepayOrgFreezeLogExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public BankRepayOrgFreezeLogExample() {
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

        public Criteria andRepayUserNameIsNull() {
            addCriterion("repay_user_name is null");
            return (Criteria) this;
        }

        public Criteria andRepayUserNameIsNotNull() {
            addCriterion("repay_user_name is not null");
            return (Criteria) this;
        }

        public Criteria andRepayUserNameEqualTo(String value) {
            addCriterion("repay_user_name =", value, "repayUserName");
            return (Criteria) this;
        }

        public Criteria andRepayUserNameNotEqualTo(String value) {
            addCriterion("repay_user_name <>", value, "repayUserName");
            return (Criteria) this;
        }

        public Criteria andRepayUserNameGreaterThan(String value) {
            addCriterion("repay_user_name >", value, "repayUserName");
            return (Criteria) this;
        }

        public Criteria andRepayUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("repay_user_name >=", value, "repayUserName");
            return (Criteria) this;
        }

        public Criteria andRepayUserNameLessThan(String value) {
            addCriterion("repay_user_name <", value, "repayUserName");
            return (Criteria) this;
        }

        public Criteria andRepayUserNameLessThanOrEqualTo(String value) {
            addCriterion("repay_user_name <=", value, "repayUserName");
            return (Criteria) this;
        }

        public Criteria andRepayUserNameLike(String value) {
            addCriterion("repay_user_name like", value, "repayUserName");
            return (Criteria) this;
        }

        public Criteria andRepayUserNameNotLike(String value) {
            addCriterion("repay_user_name not like", value, "repayUserName");
            return (Criteria) this;
        }

        public Criteria andRepayUserNameIn(List<String> values) {
            addCriterion("repay_user_name in", values, "repayUserName");
            return (Criteria) this;
        }

        public Criteria andRepayUserNameNotIn(List<String> values) {
            addCriterion("repay_user_name not in", values, "repayUserName");
            return (Criteria) this;
        }

        public Criteria andRepayUserNameBetween(String value1, String value2) {
            addCriterion("repay_user_name between", value1, value2, "repayUserName");
            return (Criteria) this;
        }

        public Criteria andRepayUserNameNotBetween(String value1, String value2) {
            addCriterion("repay_user_name not between", value1, value2, "repayUserName");
            return (Criteria) this;
        }

        public Criteria andBorrowUserIdIsNull() {
            addCriterion("borrow_user_id is null");
            return (Criteria) this;
        }

        public Criteria andBorrowUserIdIsNotNull() {
            addCriterion("borrow_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowUserIdEqualTo(Integer value) {
            addCriterion("borrow_user_id =", value, "borrowUserId");
            return (Criteria) this;
        }

        public Criteria andBorrowUserIdNotEqualTo(Integer value) {
            addCriterion("borrow_user_id <>", value, "borrowUserId");
            return (Criteria) this;
        }

        public Criteria andBorrowUserIdGreaterThan(Integer value) {
            addCriterion("borrow_user_id >", value, "borrowUserId");
            return (Criteria) this;
        }

        public Criteria andBorrowUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("borrow_user_id >=", value, "borrowUserId");
            return (Criteria) this;
        }

        public Criteria andBorrowUserIdLessThan(Integer value) {
            addCriterion("borrow_user_id <", value, "borrowUserId");
            return (Criteria) this;
        }

        public Criteria andBorrowUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("borrow_user_id <=", value, "borrowUserId");
            return (Criteria) this;
        }

        public Criteria andBorrowUserIdIn(List<Integer> values) {
            addCriterion("borrow_user_id in", values, "borrowUserId");
            return (Criteria) this;
        }

        public Criteria andBorrowUserIdNotIn(List<Integer> values) {
            addCriterion("borrow_user_id not in", values, "borrowUserId");
            return (Criteria) this;
        }

        public Criteria andBorrowUserIdBetween(Integer value1, Integer value2) {
            addCriterion("borrow_user_id between", value1, value2, "borrowUserId");
            return (Criteria) this;
        }

        public Criteria andBorrowUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("borrow_user_id not between", value1, value2, "borrowUserId");
            return (Criteria) this;
        }

        public Criteria andBorrowUserNameIsNull() {
            addCriterion("borrow_user_name is null");
            return (Criteria) this;
        }

        public Criteria andBorrowUserNameIsNotNull() {
            addCriterion("borrow_user_name is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowUserNameEqualTo(String value) {
            addCriterion("borrow_user_name =", value, "borrowUserName");
            return (Criteria) this;
        }

        public Criteria andBorrowUserNameNotEqualTo(String value) {
            addCriterion("borrow_user_name <>", value, "borrowUserName");
            return (Criteria) this;
        }

        public Criteria andBorrowUserNameGreaterThan(String value) {
            addCriterion("borrow_user_name >", value, "borrowUserName");
            return (Criteria) this;
        }

        public Criteria andBorrowUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("borrow_user_name >=", value, "borrowUserName");
            return (Criteria) this;
        }

        public Criteria andBorrowUserNameLessThan(String value) {
            addCriterion("borrow_user_name <", value, "borrowUserName");
            return (Criteria) this;
        }

        public Criteria andBorrowUserNameLessThanOrEqualTo(String value) {
            addCriterion("borrow_user_name <=", value, "borrowUserName");
            return (Criteria) this;
        }

        public Criteria andBorrowUserNameLike(String value) {
            addCriterion("borrow_user_name like", value, "borrowUserName");
            return (Criteria) this;
        }

        public Criteria andBorrowUserNameNotLike(String value) {
            addCriterion("borrow_user_name not like", value, "borrowUserName");
            return (Criteria) this;
        }

        public Criteria andBorrowUserNameIn(List<String> values) {
            addCriterion("borrow_user_name in", values, "borrowUserName");
            return (Criteria) this;
        }

        public Criteria andBorrowUserNameNotIn(List<String> values) {
            addCriterion("borrow_user_name not in", values, "borrowUserName");
            return (Criteria) this;
        }

        public Criteria andBorrowUserNameBetween(String value1, String value2) {
            addCriterion("borrow_user_name between", value1, value2, "borrowUserName");
            return (Criteria) this;
        }

        public Criteria andBorrowUserNameNotBetween(String value1, String value2) {
            addCriterion("borrow_user_name not between", value1, value2, "borrowUserName");
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

        public Criteria andAccountEqualTo(String value) {
            addCriterion("account =", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountNotEqualTo(String value) {
            addCriterion("account <>", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountGreaterThan(String value) {
            addCriterion("account >", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountGreaterThanOrEqualTo(String value) {
            addCriterion("account >=", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountLessThan(String value) {
            addCriterion("account <", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountLessThanOrEqualTo(String value) {
            addCriterion("account <=", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountLike(String value) {
            addCriterion("account like", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountNotLike(String value) {
            addCriterion("account not like", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountIn(List<String> values) {
            addCriterion("account in", values, "account");
            return (Criteria) this;
        }

        public Criteria andAccountNotIn(List<String> values) {
            addCriterion("account not in", values, "account");
            return (Criteria) this;
        }

        public Criteria andAccountBetween(String value1, String value2) {
            addCriterion("account between", value1, value2, "account");
            return (Criteria) this;
        }

        public Criteria andAccountNotBetween(String value1, String value2) {
            addCriterion("account not between", value1, value2, "account");
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

        public Criteria andAmountIsNull() {
            addCriterion("amount is null");
            return (Criteria) this;
        }

        public Criteria andAmountIsNotNull() {
            addCriterion("amount is not null");
            return (Criteria) this;
        }

        public Criteria andAmountEqualTo(BigDecimal value) {
            addCriterion("amount =", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotEqualTo(BigDecimal value) {
            addCriterion("amount <>", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThan(BigDecimal value) {
            addCriterion("amount >", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("amount >=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThan(BigDecimal value) {
            addCriterion("amount <", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("amount <=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountIn(List<BigDecimal> values) {
            addCriterion("amount in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotIn(List<BigDecimal> values) {
            addCriterion("amount not in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("amount between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("amount not between", value1, value2, "amount");
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

        public Criteria andRepayFeeIsNull() {
            addCriterion("repay_fee is null");
            return (Criteria) this;
        }

        public Criteria andRepayFeeIsNotNull() {
            addCriterion("repay_fee is not null");
            return (Criteria) this;
        }

        public Criteria andRepayFeeEqualTo(BigDecimal value) {
            addCriterion("repay_fee =", value, "repayFee");
            return (Criteria) this;
        }

        public Criteria andRepayFeeNotEqualTo(BigDecimal value) {
            addCriterion("repay_fee <>", value, "repayFee");
            return (Criteria) this;
        }

        public Criteria andRepayFeeGreaterThan(BigDecimal value) {
            addCriterion("repay_fee >", value, "repayFee");
            return (Criteria) this;
        }

        public Criteria andRepayFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_fee >=", value, "repayFee");
            return (Criteria) this;
        }

        public Criteria andRepayFeeLessThan(BigDecimal value) {
            addCriterion("repay_fee <", value, "repayFee");
            return (Criteria) this;
        }

        public Criteria andRepayFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_fee <=", value, "repayFee");
            return (Criteria) this;
        }

        public Criteria andRepayFeeIn(List<BigDecimal> values) {
            addCriterion("repay_fee in", values, "repayFee");
            return (Criteria) this;
        }

        public Criteria andRepayFeeNotIn(List<BigDecimal> values) {
            addCriterion("repay_fee not in", values, "repayFee");
            return (Criteria) this;
        }

        public Criteria andRepayFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_fee between", value1, value2, "repayFee");
            return (Criteria) this;
        }

        public Criteria andRepayFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_fee not between", value1, value2, "repayFee");
            return (Criteria) this;
        }

        public Criteria andAmountFreezeIsNull() {
            addCriterion("amount_freeze is null");
            return (Criteria) this;
        }

        public Criteria andAmountFreezeIsNotNull() {
            addCriterion("amount_freeze is not null");
            return (Criteria) this;
        }

        public Criteria andAmountFreezeEqualTo(BigDecimal value) {
            addCriterion("amount_freeze =", value, "amountFreeze");
            return (Criteria) this;
        }

        public Criteria andAmountFreezeNotEqualTo(BigDecimal value) {
            addCriterion("amount_freeze <>", value, "amountFreeze");
            return (Criteria) this;
        }

        public Criteria andAmountFreezeGreaterThan(BigDecimal value) {
            addCriterion("amount_freeze >", value, "amountFreeze");
            return (Criteria) this;
        }

        public Criteria andAmountFreezeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("amount_freeze >=", value, "amountFreeze");
            return (Criteria) this;
        }

        public Criteria andAmountFreezeLessThan(BigDecimal value) {
            addCriterion("amount_freeze <", value, "amountFreeze");
            return (Criteria) this;
        }

        public Criteria andAmountFreezeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("amount_freeze <=", value, "amountFreeze");
            return (Criteria) this;
        }

        public Criteria andAmountFreezeIn(List<BigDecimal> values) {
            addCriterion("amount_freeze in", values, "amountFreeze");
            return (Criteria) this;
        }

        public Criteria andAmountFreezeNotIn(List<BigDecimal> values) {
            addCriterion("amount_freeze not in", values, "amountFreeze");
            return (Criteria) this;
        }

        public Criteria andAmountFreezeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("amount_freeze between", value1, value2, "amountFreeze");
            return (Criteria) this;
        }

        public Criteria andAmountFreezeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("amount_freeze not between", value1, value2, "amountFreeze");
            return (Criteria) this;
        }

        public Criteria andLowerInterestIsNull() {
            addCriterion("lower_interest is null");
            return (Criteria) this;
        }

        public Criteria andLowerInterestIsNotNull() {
            addCriterion("lower_interest is not null");
            return (Criteria) this;
        }

        public Criteria andLowerInterestEqualTo(BigDecimal value) {
            addCriterion("lower_interest =", value, "lowerInterest");
            return (Criteria) this;
        }

        public Criteria andLowerInterestNotEqualTo(BigDecimal value) {
            addCriterion("lower_interest <>", value, "lowerInterest");
            return (Criteria) this;
        }

        public Criteria andLowerInterestGreaterThan(BigDecimal value) {
            addCriterion("lower_interest >", value, "lowerInterest");
            return (Criteria) this;
        }

        public Criteria andLowerInterestGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("lower_interest >=", value, "lowerInterest");
            return (Criteria) this;
        }

        public Criteria andLowerInterestLessThan(BigDecimal value) {
            addCriterion("lower_interest <", value, "lowerInterest");
            return (Criteria) this;
        }

        public Criteria andLowerInterestLessThanOrEqualTo(BigDecimal value) {
            addCriterion("lower_interest <=", value, "lowerInterest");
            return (Criteria) this;
        }

        public Criteria andLowerInterestIn(List<BigDecimal> values) {
            addCriterion("lower_interest in", values, "lowerInterest");
            return (Criteria) this;
        }

        public Criteria andLowerInterestNotIn(List<BigDecimal> values) {
            addCriterion("lower_interest not in", values, "lowerInterest");
            return (Criteria) this;
        }

        public Criteria andLowerInterestBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("lower_interest between", value1, value2, "lowerInterest");
            return (Criteria) this;
        }

        public Criteria andLowerInterestNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("lower_interest not between", value1, value2, "lowerInterest");
            return (Criteria) this;
        }

        public Criteria andPenaltyAmountIsNull() {
            addCriterion("penalty_amount is null");
            return (Criteria) this;
        }

        public Criteria andPenaltyAmountIsNotNull() {
            addCriterion("penalty_amount is not null");
            return (Criteria) this;
        }

        public Criteria andPenaltyAmountEqualTo(BigDecimal value) {
            addCriterion("penalty_amount =", value, "penaltyAmount");
            return (Criteria) this;
        }

        public Criteria andPenaltyAmountNotEqualTo(BigDecimal value) {
            addCriterion("penalty_amount <>", value, "penaltyAmount");
            return (Criteria) this;
        }

        public Criteria andPenaltyAmountGreaterThan(BigDecimal value) {
            addCriterion("penalty_amount >", value, "penaltyAmount");
            return (Criteria) this;
        }

        public Criteria andPenaltyAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("penalty_amount >=", value, "penaltyAmount");
            return (Criteria) this;
        }

        public Criteria andPenaltyAmountLessThan(BigDecimal value) {
            addCriterion("penalty_amount <", value, "penaltyAmount");
            return (Criteria) this;
        }

        public Criteria andPenaltyAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("penalty_amount <=", value, "penaltyAmount");
            return (Criteria) this;
        }

        public Criteria andPenaltyAmountIn(List<BigDecimal> values) {
            addCriterion("penalty_amount in", values, "penaltyAmount");
            return (Criteria) this;
        }

        public Criteria andPenaltyAmountNotIn(List<BigDecimal> values) {
            addCriterion("penalty_amount not in", values, "penaltyAmount");
            return (Criteria) this;
        }

        public Criteria andPenaltyAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("penalty_amount between", value1, value2, "penaltyAmount");
            return (Criteria) this;
        }

        public Criteria andPenaltyAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("penalty_amount not between", value1, value2, "penaltyAmount");
            return (Criteria) this;
        }

        public Criteria andDefaultInterestIsNull() {
            addCriterion("default_interest is null");
            return (Criteria) this;
        }

        public Criteria andDefaultInterestIsNotNull() {
            addCriterion("default_interest is not null");
            return (Criteria) this;
        }

        public Criteria andDefaultInterestEqualTo(BigDecimal value) {
            addCriterion("default_interest =", value, "defaultInterest");
            return (Criteria) this;
        }

        public Criteria andDefaultInterestNotEqualTo(BigDecimal value) {
            addCriterion("default_interest <>", value, "defaultInterest");
            return (Criteria) this;
        }

        public Criteria andDefaultInterestGreaterThan(BigDecimal value) {
            addCriterion("default_interest >", value, "defaultInterest");
            return (Criteria) this;
        }

        public Criteria andDefaultInterestGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("default_interest >=", value, "defaultInterest");
            return (Criteria) this;
        }

        public Criteria andDefaultInterestLessThan(BigDecimal value) {
            addCriterion("default_interest <", value, "defaultInterest");
            return (Criteria) this;
        }

        public Criteria andDefaultInterestLessThanOrEqualTo(BigDecimal value) {
            addCriterion("default_interest <=", value, "defaultInterest");
            return (Criteria) this;
        }

        public Criteria andDefaultInterestIn(List<BigDecimal> values) {
            addCriterion("default_interest in", values, "defaultInterest");
            return (Criteria) this;
        }

        public Criteria andDefaultInterestNotIn(List<BigDecimal> values) {
            addCriterion("default_interest not in", values, "defaultInterest");
            return (Criteria) this;
        }

        public Criteria andDefaultInterestBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("default_interest between", value1, value2, "defaultInterest");
            return (Criteria) this;
        }

        public Criteria andDefaultInterestNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("default_interest not between", value1, value2, "defaultInterest");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodIsNull() {
            addCriterion("borrow_period is null");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodIsNotNull() {
            addCriterion("borrow_period is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodEqualTo(String value) {
            addCriterion("borrow_period =", value, "borrowPeriod");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodNotEqualTo(String value) {
            addCriterion("borrow_period <>", value, "borrowPeriod");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodGreaterThan(String value) {
            addCriterion("borrow_period >", value, "borrowPeriod");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodGreaterThanOrEqualTo(String value) {
            addCriterion("borrow_period >=", value, "borrowPeriod");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodLessThan(String value) {
            addCriterion("borrow_period <", value, "borrowPeriod");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodLessThanOrEqualTo(String value) {
            addCriterion("borrow_period <=", value, "borrowPeriod");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodLike(String value) {
            addCriterion("borrow_period like", value, "borrowPeriod");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodNotLike(String value) {
            addCriterion("borrow_period not like", value, "borrowPeriod");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodIn(List<String> values) {
            addCriterion("borrow_period in", values, "borrowPeriod");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodNotIn(List<String> values) {
            addCriterion("borrow_period not in", values, "borrowPeriod");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodBetween(String value1, String value2) {
            addCriterion("borrow_period between", value1, value2, "borrowPeriod");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodNotBetween(String value1, String value2) {
            addCriterion("borrow_period not between", value1, value2, "borrowPeriod");
            return (Criteria) this;
        }

        public Criteria andTotalPeriodIsNull() {
            addCriterion("total_period is null");
            return (Criteria) this;
        }

        public Criteria andTotalPeriodIsNotNull() {
            addCriterion("total_period is not null");
            return (Criteria) this;
        }

        public Criteria andTotalPeriodEqualTo(Integer value) {
            addCriterion("total_period =", value, "totalPeriod");
            return (Criteria) this;
        }

        public Criteria andTotalPeriodNotEqualTo(Integer value) {
            addCriterion("total_period <>", value, "totalPeriod");
            return (Criteria) this;
        }

        public Criteria andTotalPeriodGreaterThan(Integer value) {
            addCriterion("total_period >", value, "totalPeriod");
            return (Criteria) this;
        }

        public Criteria andTotalPeriodGreaterThanOrEqualTo(Integer value) {
            addCriterion("total_period >=", value, "totalPeriod");
            return (Criteria) this;
        }

        public Criteria andTotalPeriodLessThan(Integer value) {
            addCriterion("total_period <", value, "totalPeriod");
            return (Criteria) this;
        }

        public Criteria andTotalPeriodLessThanOrEqualTo(Integer value) {
            addCriterion("total_period <=", value, "totalPeriod");
            return (Criteria) this;
        }

        public Criteria andTotalPeriodIn(List<Integer> values) {
            addCriterion("total_period in", values, "totalPeriod");
            return (Criteria) this;
        }

        public Criteria andTotalPeriodNotIn(List<Integer> values) {
            addCriterion("total_period not in", values, "totalPeriod");
            return (Criteria) this;
        }

        public Criteria andTotalPeriodBetween(Integer value1, Integer value2) {
            addCriterion("total_period between", value1, value2, "totalPeriod");
            return (Criteria) this;
        }

        public Criteria andTotalPeriodNotBetween(Integer value1, Integer value2) {
            addCriterion("total_period not between", value1, value2, "totalPeriod");
            return (Criteria) this;
        }

        public Criteria andCurrentPeriodIsNull() {
            addCriterion("current_period is null");
            return (Criteria) this;
        }

        public Criteria andCurrentPeriodIsNotNull() {
            addCriterion("current_period is not null");
            return (Criteria) this;
        }

        public Criteria andCurrentPeriodEqualTo(Integer value) {
            addCriterion("current_period =", value, "currentPeriod");
            return (Criteria) this;
        }

        public Criteria andCurrentPeriodNotEqualTo(Integer value) {
            addCriterion("current_period <>", value, "currentPeriod");
            return (Criteria) this;
        }

        public Criteria andCurrentPeriodGreaterThan(Integer value) {
            addCriterion("current_period >", value, "currentPeriod");
            return (Criteria) this;
        }

        public Criteria andCurrentPeriodGreaterThanOrEqualTo(Integer value) {
            addCriterion("current_period >=", value, "currentPeriod");
            return (Criteria) this;
        }

        public Criteria andCurrentPeriodLessThan(Integer value) {
            addCriterion("current_period <", value, "currentPeriod");
            return (Criteria) this;
        }

        public Criteria andCurrentPeriodLessThanOrEqualTo(Integer value) {
            addCriterion("current_period <=", value, "currentPeriod");
            return (Criteria) this;
        }

        public Criteria andCurrentPeriodIn(List<Integer> values) {
            addCriterion("current_period in", values, "currentPeriod");
            return (Criteria) this;
        }

        public Criteria andCurrentPeriodNotIn(List<Integer> values) {
            addCriterion("current_period not in", values, "currentPeriod");
            return (Criteria) this;
        }

        public Criteria andCurrentPeriodBetween(Integer value1, Integer value2) {
            addCriterion("current_period between", value1, value2, "currentPeriod");
            return (Criteria) this;
        }

        public Criteria andCurrentPeriodNotBetween(Integer value1, Integer value2) {
            addCriterion("current_period not between", value1, value2, "currentPeriod");
            return (Criteria) this;
        }

        public Criteria andAllRepayFlagIsNull() {
            addCriterion("all_repay_flag is null");
            return (Criteria) this;
        }

        public Criteria andAllRepayFlagIsNotNull() {
            addCriterion("all_repay_flag is not null");
            return (Criteria) this;
        }

        public Criteria andAllRepayFlagEqualTo(Integer value) {
            addCriterion("all_repay_flag =", value, "allRepayFlag");
            return (Criteria) this;
        }

        public Criteria andAllRepayFlagNotEqualTo(Integer value) {
            addCriterion("all_repay_flag <>", value, "allRepayFlag");
            return (Criteria) this;
        }

        public Criteria andAllRepayFlagGreaterThan(Integer value) {
            addCriterion("all_repay_flag >", value, "allRepayFlag");
            return (Criteria) this;
        }

        public Criteria andAllRepayFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("all_repay_flag >=", value, "allRepayFlag");
            return (Criteria) this;
        }

        public Criteria andAllRepayFlagLessThan(Integer value) {
            addCriterion("all_repay_flag <", value, "allRepayFlag");
            return (Criteria) this;
        }

        public Criteria andAllRepayFlagLessThanOrEqualTo(Integer value) {
            addCriterion("all_repay_flag <=", value, "allRepayFlag");
            return (Criteria) this;
        }

        public Criteria andAllRepayFlagIn(List<Integer> values) {
            addCriterion("all_repay_flag in", values, "allRepayFlag");
            return (Criteria) this;
        }

        public Criteria andAllRepayFlagNotIn(List<Integer> values) {
            addCriterion("all_repay_flag not in", values, "allRepayFlag");
            return (Criteria) this;
        }

        public Criteria andAllRepayFlagBetween(Integer value1, Integer value2) {
            addCriterion("all_repay_flag between", value1, value2, "allRepayFlag");
            return (Criteria) this;
        }

        public Criteria andAllRepayFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("all_repay_flag not between", value1, value2, "allRepayFlag");
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

        public Criteria andLatePeriodIsNull() {
            addCriterion("late_period is null");
            return (Criteria) this;
        }

        public Criteria andLatePeriodIsNotNull() {
            addCriterion("late_period is not null");
            return (Criteria) this;
        }

        public Criteria andLatePeriodEqualTo(Integer value) {
            addCriterion("late_period =", value, "latePeriod");
            return (Criteria) this;
        }

        public Criteria andLatePeriodNotEqualTo(Integer value) {
            addCriterion("late_period <>", value, "latePeriod");
            return (Criteria) this;
        }

        public Criteria andLatePeriodGreaterThan(Integer value) {
            addCriterion("late_period >", value, "latePeriod");
            return (Criteria) this;
        }

        public Criteria andLatePeriodGreaterThanOrEqualTo(Integer value) {
            addCriterion("late_period >=", value, "latePeriod");
            return (Criteria) this;
        }

        public Criteria andLatePeriodLessThan(Integer value) {
            addCriterion("late_period <", value, "latePeriod");
            return (Criteria) this;
        }

        public Criteria andLatePeriodLessThanOrEqualTo(Integer value) {
            addCriterion("late_period <=", value, "latePeriod");
            return (Criteria) this;
        }

        public Criteria andLatePeriodIn(List<Integer> values) {
            addCriterion("late_period in", values, "latePeriod");
            return (Criteria) this;
        }

        public Criteria andLatePeriodNotIn(List<Integer> values) {
            addCriterion("late_period not in", values, "latePeriod");
            return (Criteria) this;
        }

        public Criteria andLatePeriodBetween(Integer value1, Integer value2) {
            addCriterion("late_period between", value1, value2, "latePeriod");
            return (Criteria) this;
        }

        public Criteria andLatePeriodNotBetween(Integer value1, Integer value2) {
            addCriterion("late_period not between", value1, value2, "latePeriod");
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