package com.hyjf.am.trade.dao.model.auto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DebtLoanDetailExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public DebtLoanDetailExample() {
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

        public Criteria andInvestIdIsNull() {
            addCriterion("invest_id is null");
            return (Criteria) this;
        }

        public Criteria andInvestIdIsNotNull() {
            addCriterion("invest_id is not null");
            return (Criteria) this;
        }

        public Criteria andInvestIdEqualTo(Integer value) {
            addCriterion("invest_id =", value, "investId");
            return (Criteria) this;
        }

        public Criteria andInvestIdNotEqualTo(Integer value) {
            addCriterion("invest_id <>", value, "investId");
            return (Criteria) this;
        }

        public Criteria andInvestIdGreaterThan(Integer value) {
            addCriterion("invest_id >", value, "investId");
            return (Criteria) this;
        }

        public Criteria andInvestIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("invest_id >=", value, "investId");
            return (Criteria) this;
        }

        public Criteria andInvestIdLessThan(Integer value) {
            addCriterion("invest_id <", value, "investId");
            return (Criteria) this;
        }

        public Criteria andInvestIdLessThanOrEqualTo(Integer value) {
            addCriterion("invest_id <=", value, "investId");
            return (Criteria) this;
        }

        public Criteria andInvestIdIn(List<Integer> values) {
            addCriterion("invest_id in", values, "investId");
            return (Criteria) this;
        }

        public Criteria andInvestIdNotIn(List<Integer> values) {
            addCriterion("invest_id not in", values, "investId");
            return (Criteria) this;
        }

        public Criteria andInvestIdBetween(Integer value1, Integer value2) {
            addCriterion("invest_id between", value1, value2, "investId");
            return (Criteria) this;
        }

        public Criteria andInvestIdNotBetween(Integer value1, Integer value2) {
            addCriterion("invest_id not between", value1, value2, "investId");
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

        public Criteria andLoanAccountIsNull() {
            addCriterion("loan_account is null");
            return (Criteria) this;
        }

        public Criteria andLoanAccountIsNotNull() {
            addCriterion("loan_account is not null");
            return (Criteria) this;
        }

        public Criteria andLoanAccountEqualTo(BigDecimal value) {
            addCriterion("loan_account =", value, "loanAccount");
            return (Criteria) this;
        }

        public Criteria andLoanAccountNotEqualTo(BigDecimal value) {
            addCriterion("loan_account <>", value, "loanAccount");
            return (Criteria) this;
        }

        public Criteria andLoanAccountGreaterThan(BigDecimal value) {
            addCriterion("loan_account >", value, "loanAccount");
            return (Criteria) this;
        }

        public Criteria andLoanAccountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("loan_account >=", value, "loanAccount");
            return (Criteria) this;
        }

        public Criteria andLoanAccountLessThan(BigDecimal value) {
            addCriterion("loan_account <", value, "loanAccount");
            return (Criteria) this;
        }

        public Criteria andLoanAccountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("loan_account <=", value, "loanAccount");
            return (Criteria) this;
        }

        public Criteria andLoanAccountIn(List<BigDecimal> values) {
            addCriterion("loan_account in", values, "loanAccount");
            return (Criteria) this;
        }

        public Criteria andLoanAccountNotIn(List<BigDecimal> values) {
            addCriterion("loan_account not in", values, "loanAccount");
            return (Criteria) this;
        }

        public Criteria andLoanAccountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("loan_account between", value1, value2, "loanAccount");
            return (Criteria) this;
        }

        public Criteria andLoanAccountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("loan_account not between", value1, value2, "loanAccount");
            return (Criteria) this;
        }

        public Criteria andLoanInterestIsNull() {
            addCriterion("loan_interest is null");
            return (Criteria) this;
        }

        public Criteria andLoanInterestIsNotNull() {
            addCriterion("loan_interest is not null");
            return (Criteria) this;
        }

        public Criteria andLoanInterestEqualTo(BigDecimal value) {
            addCriterion("loan_interest =", value, "loanInterest");
            return (Criteria) this;
        }

        public Criteria andLoanInterestNotEqualTo(BigDecimal value) {
            addCriterion("loan_interest <>", value, "loanInterest");
            return (Criteria) this;
        }

        public Criteria andLoanInterestGreaterThan(BigDecimal value) {
            addCriterion("loan_interest >", value, "loanInterest");
            return (Criteria) this;
        }

        public Criteria andLoanInterestGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("loan_interest >=", value, "loanInterest");
            return (Criteria) this;
        }

        public Criteria andLoanInterestLessThan(BigDecimal value) {
            addCriterion("loan_interest <", value, "loanInterest");
            return (Criteria) this;
        }

        public Criteria andLoanInterestLessThanOrEqualTo(BigDecimal value) {
            addCriterion("loan_interest <=", value, "loanInterest");
            return (Criteria) this;
        }

        public Criteria andLoanInterestIn(List<BigDecimal> values) {
            addCriterion("loan_interest in", values, "loanInterest");
            return (Criteria) this;
        }

        public Criteria andLoanInterestNotIn(List<BigDecimal> values) {
            addCriterion("loan_interest not in", values, "loanInterest");
            return (Criteria) this;
        }

        public Criteria andLoanInterestBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("loan_interest between", value1, value2, "loanInterest");
            return (Criteria) this;
        }

        public Criteria andLoanInterestNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("loan_interest not between", value1, value2, "loanInterest");
            return (Criteria) this;
        }

        public Criteria andLoanCapitalIsNull() {
            addCriterion("loan_capital is null");
            return (Criteria) this;
        }

        public Criteria andLoanCapitalIsNotNull() {
            addCriterion("loan_capital is not null");
            return (Criteria) this;
        }

        public Criteria andLoanCapitalEqualTo(BigDecimal value) {
            addCriterion("loan_capital =", value, "loanCapital");
            return (Criteria) this;
        }

        public Criteria andLoanCapitalNotEqualTo(BigDecimal value) {
            addCriterion("loan_capital <>", value, "loanCapital");
            return (Criteria) this;
        }

        public Criteria andLoanCapitalGreaterThan(BigDecimal value) {
            addCriterion("loan_capital >", value, "loanCapital");
            return (Criteria) this;
        }

        public Criteria andLoanCapitalGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("loan_capital >=", value, "loanCapital");
            return (Criteria) this;
        }

        public Criteria andLoanCapitalLessThan(BigDecimal value) {
            addCriterion("loan_capital <", value, "loanCapital");
            return (Criteria) this;
        }

        public Criteria andLoanCapitalLessThanOrEqualTo(BigDecimal value) {
            addCriterion("loan_capital <=", value, "loanCapital");
            return (Criteria) this;
        }

        public Criteria andLoanCapitalIn(List<BigDecimal> values) {
            addCriterion("loan_capital in", values, "loanCapital");
            return (Criteria) this;
        }

        public Criteria andLoanCapitalNotIn(List<BigDecimal> values) {
            addCriterion("loan_capital not in", values, "loanCapital");
            return (Criteria) this;
        }

        public Criteria andLoanCapitalBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("loan_capital between", value1, value2, "loanCapital");
            return (Criteria) this;
        }

        public Criteria andLoanCapitalNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("loan_capital not between", value1, value2, "loanCapital");
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

        public Criteria andLoanTypeIsNull() {
            addCriterion("loan_type is null");
            return (Criteria) this;
        }

        public Criteria andLoanTypeIsNotNull() {
            addCriterion("loan_type is not null");
            return (Criteria) this;
        }

        public Criteria andLoanTypeEqualTo(String value) {
            addCriterion("loan_type =", value, "loanType");
            return (Criteria) this;
        }

        public Criteria andLoanTypeNotEqualTo(String value) {
            addCriterion("loan_type <>", value, "loanType");
            return (Criteria) this;
        }

        public Criteria andLoanTypeGreaterThan(String value) {
            addCriterion("loan_type >", value, "loanType");
            return (Criteria) this;
        }

        public Criteria andLoanTypeGreaterThanOrEqualTo(String value) {
            addCriterion("loan_type >=", value, "loanType");
            return (Criteria) this;
        }

        public Criteria andLoanTypeLessThan(String value) {
            addCriterion("loan_type <", value, "loanType");
            return (Criteria) this;
        }

        public Criteria andLoanTypeLessThanOrEqualTo(String value) {
            addCriterion("loan_type <=", value, "loanType");
            return (Criteria) this;
        }

        public Criteria andLoanTypeLike(String value) {
            addCriterion("loan_type like", value, "loanType");
            return (Criteria) this;
        }

        public Criteria andLoanTypeNotLike(String value) {
            addCriterion("loan_type not like", value, "loanType");
            return (Criteria) this;
        }

        public Criteria andLoanTypeIn(List<String> values) {
            addCriterion("loan_type in", values, "loanType");
            return (Criteria) this;
        }

        public Criteria andLoanTypeNotIn(List<String> values) {
            addCriterion("loan_type not in", values, "loanType");
            return (Criteria) this;
        }

        public Criteria andLoanTypeBetween(String value1, String value2) {
            addCriterion("loan_type between", value1, value2, "loanType");
            return (Criteria) this;
        }

        public Criteria andLoanTypeNotBetween(String value1, String value2) {
            addCriterion("loan_type not between", value1, value2, "loanType");
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

        public Criteria andRepayAdvanceInterestYesIsNull() {
            addCriterion("repay_advance_interest_yes is null");
            return (Criteria) this;
        }

        public Criteria andRepayAdvanceInterestYesIsNotNull() {
            addCriterion("repay_advance_interest_yes is not null");
            return (Criteria) this;
        }

        public Criteria andRepayAdvanceInterestYesEqualTo(BigDecimal value) {
            addCriterion("repay_advance_interest_yes =", value, "repayAdvanceInterestYes");
            return (Criteria) this;
        }

        public Criteria andRepayAdvanceInterestYesNotEqualTo(BigDecimal value) {
            addCriterion("repay_advance_interest_yes <>", value, "repayAdvanceInterestYes");
            return (Criteria) this;
        }

        public Criteria andRepayAdvanceInterestYesGreaterThan(BigDecimal value) {
            addCriterion("repay_advance_interest_yes >", value, "repayAdvanceInterestYes");
            return (Criteria) this;
        }

        public Criteria andRepayAdvanceInterestYesGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_advance_interest_yes >=", value, "repayAdvanceInterestYes");
            return (Criteria) this;
        }

        public Criteria andRepayAdvanceInterestYesLessThan(BigDecimal value) {
            addCriterion("repay_advance_interest_yes <", value, "repayAdvanceInterestYes");
            return (Criteria) this;
        }

        public Criteria andRepayAdvanceInterestYesLessThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_advance_interest_yes <=", value, "repayAdvanceInterestYes");
            return (Criteria) this;
        }

        public Criteria andRepayAdvanceInterestYesIn(List<BigDecimal> values) {
            addCriterion("repay_advance_interest_yes in", values, "repayAdvanceInterestYes");
            return (Criteria) this;
        }

        public Criteria andRepayAdvanceInterestYesNotIn(List<BigDecimal> values) {
            addCriterion("repay_advance_interest_yes not in", values, "repayAdvanceInterestYes");
            return (Criteria) this;
        }

        public Criteria andRepayAdvanceInterestYesBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_advance_interest_yes between", value1, value2, "repayAdvanceInterestYes");
            return (Criteria) this;
        }

        public Criteria andRepayAdvanceInterestYesNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_advance_interest_yes not between", value1, value2, "repayAdvanceInterestYes");
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

        public Criteria andRepayLateInterestYesIsNull() {
            addCriterion("repay_late_interest_yes is null");
            return (Criteria) this;
        }

        public Criteria andRepayLateInterestYesIsNotNull() {
            addCriterion("repay_late_interest_yes is not null");
            return (Criteria) this;
        }

        public Criteria andRepayLateInterestYesEqualTo(BigDecimal value) {
            addCriterion("repay_late_interest_yes =", value, "repayLateInterestYes");
            return (Criteria) this;
        }

        public Criteria andRepayLateInterestYesNotEqualTo(BigDecimal value) {
            addCriterion("repay_late_interest_yes <>", value, "repayLateInterestYes");
            return (Criteria) this;
        }

        public Criteria andRepayLateInterestYesGreaterThan(BigDecimal value) {
            addCriterion("repay_late_interest_yes >", value, "repayLateInterestYes");
            return (Criteria) this;
        }

        public Criteria andRepayLateInterestYesGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_late_interest_yes >=", value, "repayLateInterestYes");
            return (Criteria) this;
        }

        public Criteria andRepayLateInterestYesLessThan(BigDecimal value) {
            addCriterion("repay_late_interest_yes <", value, "repayLateInterestYes");
            return (Criteria) this;
        }

        public Criteria andRepayLateInterestYesLessThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_late_interest_yes <=", value, "repayLateInterestYes");
            return (Criteria) this;
        }

        public Criteria andRepayLateInterestYesIn(List<BigDecimal> values) {
            addCriterion("repay_late_interest_yes in", values, "repayLateInterestYes");
            return (Criteria) this;
        }

        public Criteria andRepayLateInterestYesNotIn(List<BigDecimal> values) {
            addCriterion("repay_late_interest_yes not in", values, "repayLateInterestYes");
            return (Criteria) this;
        }

        public Criteria andRepayLateInterestYesBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_late_interest_yes between", value1, value2, "repayLateInterestYes");
            return (Criteria) this;
        }

        public Criteria andRepayLateInterestYesNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_late_interest_yes not between", value1, value2, "repayLateInterestYes");
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

        public Criteria andRepayDelayInterestYesIsNull() {
            addCriterion("repay_delay_interest_yes is null");
            return (Criteria) this;
        }

        public Criteria andRepayDelayInterestYesIsNotNull() {
            addCriterion("repay_delay_interest_yes is not null");
            return (Criteria) this;
        }

        public Criteria andRepayDelayInterestYesEqualTo(BigDecimal value) {
            addCriterion("repay_delay_interest_yes =", value, "repayDelayInterestYes");
            return (Criteria) this;
        }

        public Criteria andRepayDelayInterestYesNotEqualTo(BigDecimal value) {
            addCriterion("repay_delay_interest_yes <>", value, "repayDelayInterestYes");
            return (Criteria) this;
        }

        public Criteria andRepayDelayInterestYesGreaterThan(BigDecimal value) {
            addCriterion("repay_delay_interest_yes >", value, "repayDelayInterestYes");
            return (Criteria) this;
        }

        public Criteria andRepayDelayInterestYesGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_delay_interest_yes >=", value, "repayDelayInterestYes");
            return (Criteria) this;
        }

        public Criteria andRepayDelayInterestYesLessThan(BigDecimal value) {
            addCriterion("repay_delay_interest_yes <", value, "repayDelayInterestYes");
            return (Criteria) this;
        }

        public Criteria andRepayDelayInterestYesLessThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_delay_interest_yes <=", value, "repayDelayInterestYes");
            return (Criteria) this;
        }

        public Criteria andRepayDelayInterestYesIn(List<BigDecimal> values) {
            addCriterion("repay_delay_interest_yes in", values, "repayDelayInterestYes");
            return (Criteria) this;
        }

        public Criteria andRepayDelayInterestYesNotIn(List<BigDecimal> values) {
            addCriterion("repay_delay_interest_yes not in", values, "repayDelayInterestYes");
            return (Criteria) this;
        }

        public Criteria andRepayDelayInterestYesBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_delay_interest_yes between", value1, value2, "repayDelayInterestYes");
            return (Criteria) this;
        }

        public Criteria andRepayDelayInterestYesNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_delay_interest_yes not between", value1, value2, "repayDelayInterestYes");
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

        public Criteria andSendmailIsNull() {
            addCriterion("sendmail is null");
            return (Criteria) this;
        }

        public Criteria andSendmailIsNotNull() {
            addCriterion("sendmail is not null");
            return (Criteria) this;
        }

        public Criteria andSendmailEqualTo(Integer value) {
            addCriterion("sendmail =", value, "sendmail");
            return (Criteria) this;
        }

        public Criteria andSendmailNotEqualTo(Integer value) {
            addCriterion("sendmail <>", value, "sendmail");
            return (Criteria) this;
        }

        public Criteria andSendmailGreaterThan(Integer value) {
            addCriterion("sendmail >", value, "sendmail");
            return (Criteria) this;
        }

        public Criteria andSendmailGreaterThanOrEqualTo(Integer value) {
            addCriterion("sendmail >=", value, "sendmail");
            return (Criteria) this;
        }

        public Criteria andSendmailLessThan(Integer value) {
            addCriterion("sendmail <", value, "sendmail");
            return (Criteria) this;
        }

        public Criteria andSendmailLessThanOrEqualTo(Integer value) {
            addCriterion("sendmail <=", value, "sendmail");
            return (Criteria) this;
        }

        public Criteria andSendmailIn(List<Integer> values) {
            addCriterion("sendmail in", values, "sendmail");
            return (Criteria) this;
        }

        public Criteria andSendmailNotIn(List<Integer> values) {
            addCriterion("sendmail not in", values, "sendmail");
            return (Criteria) this;
        }

        public Criteria andSendmailBetween(Integer value1, Integer value2) {
            addCriterion("sendmail between", value1, value2, "sendmail");
            return (Criteria) this;
        }

        public Criteria andSendmailNotBetween(Integer value1, Integer value2) {
            addCriterion("sendmail not between", value1, value2, "sendmail");
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

        public Criteria andCreditAmountIsNull() {
            addCriterion("credit_amount is null");
            return (Criteria) this;
        }

        public Criteria andCreditAmountIsNotNull() {
            addCriterion("credit_amount is not null");
            return (Criteria) this;
        }

        public Criteria andCreditAmountEqualTo(BigDecimal value) {
            addCriterion("credit_amount =", value, "creditAmount");
            return (Criteria) this;
        }

        public Criteria andCreditAmountNotEqualTo(BigDecimal value) {
            addCriterion("credit_amount <>", value, "creditAmount");
            return (Criteria) this;
        }

        public Criteria andCreditAmountGreaterThan(BigDecimal value) {
            addCriterion("credit_amount >", value, "creditAmount");
            return (Criteria) this;
        }

        public Criteria andCreditAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("credit_amount >=", value, "creditAmount");
            return (Criteria) this;
        }

        public Criteria andCreditAmountLessThan(BigDecimal value) {
            addCriterion("credit_amount <", value, "creditAmount");
            return (Criteria) this;
        }

        public Criteria andCreditAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("credit_amount <=", value, "creditAmount");
            return (Criteria) this;
        }

        public Criteria andCreditAmountIn(List<BigDecimal> values) {
            addCriterion("credit_amount in", values, "creditAmount");
            return (Criteria) this;
        }

        public Criteria andCreditAmountNotIn(List<BigDecimal> values) {
            addCriterion("credit_amount not in", values, "creditAmount");
            return (Criteria) this;
        }

        public Criteria andCreditAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("credit_amount between", value1, value2, "creditAmount");
            return (Criteria) this;
        }

        public Criteria andCreditAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("credit_amount not between", value1, value2, "creditAmount");
            return (Criteria) this;
        }

        public Criteria andCreditInterestAmountIsNull() {
            addCriterion("credit_interest_amount is null");
            return (Criteria) this;
        }

        public Criteria andCreditInterestAmountIsNotNull() {
            addCriterion("credit_interest_amount is not null");
            return (Criteria) this;
        }

        public Criteria andCreditInterestAmountEqualTo(BigDecimal value) {
            addCriterion("credit_interest_amount =", value, "creditInterestAmount");
            return (Criteria) this;
        }

        public Criteria andCreditInterestAmountNotEqualTo(BigDecimal value) {
            addCriterion("credit_interest_amount <>", value, "creditInterestAmount");
            return (Criteria) this;
        }

        public Criteria andCreditInterestAmountGreaterThan(BigDecimal value) {
            addCriterion("credit_interest_amount >", value, "creditInterestAmount");
            return (Criteria) this;
        }

        public Criteria andCreditInterestAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("credit_interest_amount >=", value, "creditInterestAmount");
            return (Criteria) this;
        }

        public Criteria andCreditInterestAmountLessThan(BigDecimal value) {
            addCriterion("credit_interest_amount <", value, "creditInterestAmount");
            return (Criteria) this;
        }

        public Criteria andCreditInterestAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("credit_interest_amount <=", value, "creditInterestAmount");
            return (Criteria) this;
        }

        public Criteria andCreditInterestAmountIn(List<BigDecimal> values) {
            addCriterion("credit_interest_amount in", values, "creditInterestAmount");
            return (Criteria) this;
        }

        public Criteria andCreditInterestAmountNotIn(List<BigDecimal> values) {
            addCriterion("credit_interest_amount not in", values, "creditInterestAmount");
            return (Criteria) this;
        }

        public Criteria andCreditInterestAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("credit_interest_amount between", value1, value2, "creditInterestAmount");
            return (Criteria) this;
        }

        public Criteria andCreditInterestAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("credit_interest_amount not between", value1, value2, "creditInterestAmount");
            return (Criteria) this;
        }

        public Criteria andCreditTimeIsNull() {
            addCriterion("credit_time is null");
            return (Criteria) this;
        }

        public Criteria andCreditTimeIsNotNull() {
            addCriterion("credit_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreditTimeEqualTo(Integer value) {
            addCriterion("credit_time =", value, "creditTime");
            return (Criteria) this;
        }

        public Criteria andCreditTimeNotEqualTo(Integer value) {
            addCriterion("credit_time <>", value, "creditTime");
            return (Criteria) this;
        }

        public Criteria andCreditTimeGreaterThan(Integer value) {
            addCriterion("credit_time >", value, "creditTime");
            return (Criteria) this;
        }

        public Criteria andCreditTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("credit_time >=", value, "creditTime");
            return (Criteria) this;
        }

        public Criteria andCreditTimeLessThan(Integer value) {
            addCriterion("credit_time <", value, "creditTime");
            return (Criteria) this;
        }

        public Criteria andCreditTimeLessThanOrEqualTo(Integer value) {
            addCriterion("credit_time <=", value, "creditTime");
            return (Criteria) this;
        }

        public Criteria andCreditTimeIn(List<Integer> values) {
            addCriterion("credit_time in", values, "creditTime");
            return (Criteria) this;
        }

        public Criteria andCreditTimeNotIn(List<Integer> values) {
            addCriterion("credit_time not in", values, "creditTime");
            return (Criteria) this;
        }

        public Criteria andCreditTimeBetween(Integer value1, Integer value2) {
            addCriterion("credit_time between", value1, value2, "creditTime");
            return (Criteria) this;
        }

        public Criteria andCreditTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("credit_time not between", value1, value2, "creditTime");
            return (Criteria) this;
        }

        public Criteria andRepayOrderIdIsNull() {
            addCriterion("repay_order_id is null");
            return (Criteria) this;
        }

        public Criteria andRepayOrderIdIsNotNull() {
            addCriterion("repay_order_id is not null");
            return (Criteria) this;
        }

        public Criteria andRepayOrderIdEqualTo(String value) {
            addCriterion("repay_order_id =", value, "repayOrderId");
            return (Criteria) this;
        }

        public Criteria andRepayOrderIdNotEqualTo(String value) {
            addCriterion("repay_order_id <>", value, "repayOrderId");
            return (Criteria) this;
        }

        public Criteria andRepayOrderIdGreaterThan(String value) {
            addCriterion("repay_order_id >", value, "repayOrderId");
            return (Criteria) this;
        }

        public Criteria andRepayOrderIdGreaterThanOrEqualTo(String value) {
            addCriterion("repay_order_id >=", value, "repayOrderId");
            return (Criteria) this;
        }

        public Criteria andRepayOrderIdLessThan(String value) {
            addCriterion("repay_order_id <", value, "repayOrderId");
            return (Criteria) this;
        }

        public Criteria andRepayOrderIdLessThanOrEqualTo(String value) {
            addCriterion("repay_order_id <=", value, "repayOrderId");
            return (Criteria) this;
        }

        public Criteria andRepayOrderIdLike(String value) {
            addCriterion("repay_order_id like", value, "repayOrderId");
            return (Criteria) this;
        }

        public Criteria andRepayOrderIdNotLike(String value) {
            addCriterion("repay_order_id not like", value, "repayOrderId");
            return (Criteria) this;
        }

        public Criteria andRepayOrderIdIn(List<String> values) {
            addCriterion("repay_order_id in", values, "repayOrderId");
            return (Criteria) this;
        }

        public Criteria andRepayOrderIdNotIn(List<String> values) {
            addCriterion("repay_order_id not in", values, "repayOrderId");
            return (Criteria) this;
        }

        public Criteria andRepayOrderIdBetween(String value1, String value2) {
            addCriterion("repay_order_id between", value1, value2, "repayOrderId");
            return (Criteria) this;
        }

        public Criteria andRepayOrderIdNotBetween(String value1, String value2) {
            addCriterion("repay_order_id not between", value1, value2, "repayOrderId");
            return (Criteria) this;
        }

        public Criteria andRepayOrderDateIsNull() {
            addCriterion("repay_order_date is null");
            return (Criteria) this;
        }

        public Criteria andRepayOrderDateIsNotNull() {
            addCriterion("repay_order_date is not null");
            return (Criteria) this;
        }

        public Criteria andRepayOrderDateEqualTo(String value) {
            addCriterion("repay_order_date =", value, "repayOrderDate");
            return (Criteria) this;
        }

        public Criteria andRepayOrderDateNotEqualTo(String value) {
            addCriterion("repay_order_date <>", value, "repayOrderDate");
            return (Criteria) this;
        }

        public Criteria andRepayOrderDateGreaterThan(String value) {
            addCriterion("repay_order_date >", value, "repayOrderDate");
            return (Criteria) this;
        }

        public Criteria andRepayOrderDateGreaterThanOrEqualTo(String value) {
            addCriterion("repay_order_date >=", value, "repayOrderDate");
            return (Criteria) this;
        }

        public Criteria andRepayOrderDateLessThan(String value) {
            addCriterion("repay_order_date <", value, "repayOrderDate");
            return (Criteria) this;
        }

        public Criteria andRepayOrderDateLessThanOrEqualTo(String value) {
            addCriterion("repay_order_date <=", value, "repayOrderDate");
            return (Criteria) this;
        }

        public Criteria andRepayOrderDateLike(String value) {
            addCriterion("repay_order_date like", value, "repayOrderDate");
            return (Criteria) this;
        }

        public Criteria andRepayOrderDateNotLike(String value) {
            addCriterion("repay_order_date not like", value, "repayOrderDate");
            return (Criteria) this;
        }

        public Criteria andRepayOrderDateIn(List<String> values) {
            addCriterion("repay_order_date in", values, "repayOrderDate");
            return (Criteria) this;
        }

        public Criteria andRepayOrderDateNotIn(List<String> values) {
            addCriterion("repay_order_date not in", values, "repayOrderDate");
            return (Criteria) this;
        }

        public Criteria andRepayOrderDateBetween(String value1, String value2) {
            addCriterion("repay_order_date between", value1, value2, "repayOrderDate");
            return (Criteria) this;
        }

        public Criteria andRepayOrderDateNotBetween(String value1, String value2) {
            addCriterion("repay_order_date not between", value1, value2, "repayOrderDate");
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