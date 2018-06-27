package com.hyjf.am.trade.dao.model.auto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CreditTenderExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public CreditTenderExample() {
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

        public Criteria andAssignIdIsNull() {
            addCriterion("assign_id is null");
            return (Criteria) this;
        }

        public Criteria andAssignIdIsNotNull() {
            addCriterion("assign_id is not null");
            return (Criteria) this;
        }

        public Criteria andAssignIdEqualTo(Integer value) {
            addCriterion("assign_id =", value, "assignId");
            return (Criteria) this;
        }

        public Criteria andAssignIdNotEqualTo(Integer value) {
            addCriterion("assign_id <>", value, "assignId");
            return (Criteria) this;
        }

        public Criteria andAssignIdGreaterThan(Integer value) {
            addCriterion("assign_id >", value, "assignId");
            return (Criteria) this;
        }

        public Criteria andAssignIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("assign_id >=", value, "assignId");
            return (Criteria) this;
        }

        public Criteria andAssignIdLessThan(Integer value) {
            addCriterion("assign_id <", value, "assignId");
            return (Criteria) this;
        }

        public Criteria andAssignIdLessThanOrEqualTo(Integer value) {
            addCriterion("assign_id <=", value, "assignId");
            return (Criteria) this;
        }

        public Criteria andAssignIdIn(List<Integer> values) {
            addCriterion("assign_id in", values, "assignId");
            return (Criteria) this;
        }

        public Criteria andAssignIdNotIn(List<Integer> values) {
            addCriterion("assign_id not in", values, "assignId");
            return (Criteria) this;
        }

        public Criteria andAssignIdBetween(Integer value1, Integer value2) {
            addCriterion("assign_id between", value1, value2, "assignId");
            return (Criteria) this;
        }

        public Criteria andAssignIdNotBetween(Integer value1, Integer value2) {
            addCriterion("assign_id not between", value1, value2, "assignId");
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

        public Criteria andBidNidIsNull() {
            addCriterion("bid_nid is null");
            return (Criteria) this;
        }

        public Criteria andBidNidIsNotNull() {
            addCriterion("bid_nid is not null");
            return (Criteria) this;
        }

        public Criteria andBidNidEqualTo(String value) {
            addCriterion("bid_nid =", value, "bidNid");
            return (Criteria) this;
        }

        public Criteria andBidNidNotEqualTo(String value) {
            addCriterion("bid_nid <>", value, "bidNid");
            return (Criteria) this;
        }

        public Criteria andBidNidGreaterThan(String value) {
            addCriterion("bid_nid >", value, "bidNid");
            return (Criteria) this;
        }

        public Criteria andBidNidGreaterThanOrEqualTo(String value) {
            addCriterion("bid_nid >=", value, "bidNid");
            return (Criteria) this;
        }

        public Criteria andBidNidLessThan(String value) {
            addCriterion("bid_nid <", value, "bidNid");
            return (Criteria) this;
        }

        public Criteria andBidNidLessThanOrEqualTo(String value) {
            addCriterion("bid_nid <=", value, "bidNid");
            return (Criteria) this;
        }

        public Criteria andBidNidLike(String value) {
            addCriterion("bid_nid like", value, "bidNid");
            return (Criteria) this;
        }

        public Criteria andBidNidNotLike(String value) {
            addCriterion("bid_nid not like", value, "bidNid");
            return (Criteria) this;
        }

        public Criteria andBidNidIn(List<String> values) {
            addCriterion("bid_nid in", values, "bidNid");
            return (Criteria) this;
        }

        public Criteria andBidNidNotIn(List<String> values) {
            addCriterion("bid_nid not in", values, "bidNid");
            return (Criteria) this;
        }

        public Criteria andBidNidBetween(String value1, String value2) {
            addCriterion("bid_nid between", value1, value2, "bidNid");
            return (Criteria) this;
        }

        public Criteria andBidNidNotBetween(String value1, String value2) {
            addCriterion("bid_nid not between", value1, value2, "bidNid");
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

        public Criteria andCreditTenderNidIsNull() {
            addCriterion("credit_tender_nid is null");
            return (Criteria) this;
        }

        public Criteria andCreditTenderNidIsNotNull() {
            addCriterion("credit_tender_nid is not null");
            return (Criteria) this;
        }

        public Criteria andCreditTenderNidEqualTo(String value) {
            addCriterion("credit_tender_nid =", value, "creditTenderNid");
            return (Criteria) this;
        }

        public Criteria andCreditTenderNidNotEqualTo(String value) {
            addCriterion("credit_tender_nid <>", value, "creditTenderNid");
            return (Criteria) this;
        }

        public Criteria andCreditTenderNidGreaterThan(String value) {
            addCriterion("credit_tender_nid >", value, "creditTenderNid");
            return (Criteria) this;
        }

        public Criteria andCreditTenderNidGreaterThanOrEqualTo(String value) {
            addCriterion("credit_tender_nid >=", value, "creditTenderNid");
            return (Criteria) this;
        }

        public Criteria andCreditTenderNidLessThan(String value) {
            addCriterion("credit_tender_nid <", value, "creditTenderNid");
            return (Criteria) this;
        }

        public Criteria andCreditTenderNidLessThanOrEqualTo(String value) {
            addCriterion("credit_tender_nid <=", value, "creditTenderNid");
            return (Criteria) this;
        }

        public Criteria andCreditTenderNidLike(String value) {
            addCriterion("credit_tender_nid like", value, "creditTenderNid");
            return (Criteria) this;
        }

        public Criteria andCreditTenderNidNotLike(String value) {
            addCriterion("credit_tender_nid not like", value, "creditTenderNid");
            return (Criteria) this;
        }

        public Criteria andCreditTenderNidIn(List<String> values) {
            addCriterion("credit_tender_nid in", values, "creditTenderNid");
            return (Criteria) this;
        }

        public Criteria andCreditTenderNidNotIn(List<String> values) {
            addCriterion("credit_tender_nid not in", values, "creditTenderNid");
            return (Criteria) this;
        }

        public Criteria andCreditTenderNidBetween(String value1, String value2) {
            addCriterion("credit_tender_nid between", value1, value2, "creditTenderNid");
            return (Criteria) this;
        }

        public Criteria andCreditTenderNidNotBetween(String value1, String value2) {
            addCriterion("credit_tender_nid not between", value1, value2, "creditTenderNid");
            return (Criteria) this;
        }

        public Criteria andAssignNidIsNull() {
            addCriterion("assign_nid is null");
            return (Criteria) this;
        }

        public Criteria andAssignNidIsNotNull() {
            addCriterion("assign_nid is not null");
            return (Criteria) this;
        }

        public Criteria andAssignNidEqualTo(String value) {
            addCriterion("assign_nid =", value, "assignNid");
            return (Criteria) this;
        }

        public Criteria andAssignNidNotEqualTo(String value) {
            addCriterion("assign_nid <>", value, "assignNid");
            return (Criteria) this;
        }

        public Criteria andAssignNidGreaterThan(String value) {
            addCriterion("assign_nid >", value, "assignNid");
            return (Criteria) this;
        }

        public Criteria andAssignNidGreaterThanOrEqualTo(String value) {
            addCriterion("assign_nid >=", value, "assignNid");
            return (Criteria) this;
        }

        public Criteria andAssignNidLessThan(String value) {
            addCriterion("assign_nid <", value, "assignNid");
            return (Criteria) this;
        }

        public Criteria andAssignNidLessThanOrEqualTo(String value) {
            addCriterion("assign_nid <=", value, "assignNid");
            return (Criteria) this;
        }

        public Criteria andAssignNidLike(String value) {
            addCriterion("assign_nid like", value, "assignNid");
            return (Criteria) this;
        }

        public Criteria andAssignNidNotLike(String value) {
            addCriterion("assign_nid not like", value, "assignNid");
            return (Criteria) this;
        }

        public Criteria andAssignNidIn(List<String> values) {
            addCriterion("assign_nid in", values, "assignNid");
            return (Criteria) this;
        }

        public Criteria andAssignNidNotIn(List<String> values) {
            addCriterion("assign_nid not in", values, "assignNid");
            return (Criteria) this;
        }

        public Criteria andAssignNidBetween(String value1, String value2) {
            addCriterion("assign_nid between", value1, value2, "assignNid");
            return (Criteria) this;
        }

        public Criteria andAssignNidNotBetween(String value1, String value2) {
            addCriterion("assign_nid not between", value1, value2, "assignNid");
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

        public Criteria andAssignRepayAccountIsNull() {
            addCriterion("assign_repay_account is null");
            return (Criteria) this;
        }

        public Criteria andAssignRepayAccountIsNotNull() {
            addCriterion("assign_repay_account is not null");
            return (Criteria) this;
        }

        public Criteria andAssignRepayAccountEqualTo(BigDecimal value) {
            addCriterion("assign_repay_account =", value, "assignRepayAccount");
            return (Criteria) this;
        }

        public Criteria andAssignRepayAccountNotEqualTo(BigDecimal value) {
            addCriterion("assign_repay_account <>", value, "assignRepayAccount");
            return (Criteria) this;
        }

        public Criteria andAssignRepayAccountGreaterThan(BigDecimal value) {
            addCriterion("assign_repay_account >", value, "assignRepayAccount");
            return (Criteria) this;
        }

        public Criteria andAssignRepayAccountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("assign_repay_account >=", value, "assignRepayAccount");
            return (Criteria) this;
        }

        public Criteria andAssignRepayAccountLessThan(BigDecimal value) {
            addCriterion("assign_repay_account <", value, "assignRepayAccount");
            return (Criteria) this;
        }

        public Criteria andAssignRepayAccountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("assign_repay_account <=", value, "assignRepayAccount");
            return (Criteria) this;
        }

        public Criteria andAssignRepayAccountIn(List<BigDecimal> values) {
            addCriterion("assign_repay_account in", values, "assignRepayAccount");
            return (Criteria) this;
        }

        public Criteria andAssignRepayAccountNotIn(List<BigDecimal> values) {
            addCriterion("assign_repay_account not in", values, "assignRepayAccount");
            return (Criteria) this;
        }

        public Criteria andAssignRepayAccountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("assign_repay_account between", value1, value2, "assignRepayAccount");
            return (Criteria) this;
        }

        public Criteria andAssignRepayAccountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("assign_repay_account not between", value1, value2, "assignRepayAccount");
            return (Criteria) this;
        }

        public Criteria andAssignRepayCapitalIsNull() {
            addCriterion("assign_repay_capital is null");
            return (Criteria) this;
        }

        public Criteria andAssignRepayCapitalIsNotNull() {
            addCriterion("assign_repay_capital is not null");
            return (Criteria) this;
        }

        public Criteria andAssignRepayCapitalEqualTo(BigDecimal value) {
            addCriterion("assign_repay_capital =", value, "assignRepayCapital");
            return (Criteria) this;
        }

        public Criteria andAssignRepayCapitalNotEqualTo(BigDecimal value) {
            addCriterion("assign_repay_capital <>", value, "assignRepayCapital");
            return (Criteria) this;
        }

        public Criteria andAssignRepayCapitalGreaterThan(BigDecimal value) {
            addCriterion("assign_repay_capital >", value, "assignRepayCapital");
            return (Criteria) this;
        }

        public Criteria andAssignRepayCapitalGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("assign_repay_capital >=", value, "assignRepayCapital");
            return (Criteria) this;
        }

        public Criteria andAssignRepayCapitalLessThan(BigDecimal value) {
            addCriterion("assign_repay_capital <", value, "assignRepayCapital");
            return (Criteria) this;
        }

        public Criteria andAssignRepayCapitalLessThanOrEqualTo(BigDecimal value) {
            addCriterion("assign_repay_capital <=", value, "assignRepayCapital");
            return (Criteria) this;
        }

        public Criteria andAssignRepayCapitalIn(List<BigDecimal> values) {
            addCriterion("assign_repay_capital in", values, "assignRepayCapital");
            return (Criteria) this;
        }

        public Criteria andAssignRepayCapitalNotIn(List<BigDecimal> values) {
            addCriterion("assign_repay_capital not in", values, "assignRepayCapital");
            return (Criteria) this;
        }

        public Criteria andAssignRepayCapitalBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("assign_repay_capital between", value1, value2, "assignRepayCapital");
            return (Criteria) this;
        }

        public Criteria andAssignRepayCapitalNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("assign_repay_capital not between", value1, value2, "assignRepayCapital");
            return (Criteria) this;
        }

        public Criteria andAssignRepayInterestIsNull() {
            addCriterion("assign_repay_interest is null");
            return (Criteria) this;
        }

        public Criteria andAssignRepayInterestIsNotNull() {
            addCriterion("assign_repay_interest is not null");
            return (Criteria) this;
        }

        public Criteria andAssignRepayInterestEqualTo(BigDecimal value) {
            addCriterion("assign_repay_interest =", value, "assignRepayInterest");
            return (Criteria) this;
        }

        public Criteria andAssignRepayInterestNotEqualTo(BigDecimal value) {
            addCriterion("assign_repay_interest <>", value, "assignRepayInterest");
            return (Criteria) this;
        }

        public Criteria andAssignRepayInterestGreaterThan(BigDecimal value) {
            addCriterion("assign_repay_interest >", value, "assignRepayInterest");
            return (Criteria) this;
        }

        public Criteria andAssignRepayInterestGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("assign_repay_interest >=", value, "assignRepayInterest");
            return (Criteria) this;
        }

        public Criteria andAssignRepayInterestLessThan(BigDecimal value) {
            addCriterion("assign_repay_interest <", value, "assignRepayInterest");
            return (Criteria) this;
        }

        public Criteria andAssignRepayInterestLessThanOrEqualTo(BigDecimal value) {
            addCriterion("assign_repay_interest <=", value, "assignRepayInterest");
            return (Criteria) this;
        }

        public Criteria andAssignRepayInterestIn(List<BigDecimal> values) {
            addCriterion("assign_repay_interest in", values, "assignRepayInterest");
            return (Criteria) this;
        }

        public Criteria andAssignRepayInterestNotIn(List<BigDecimal> values) {
            addCriterion("assign_repay_interest not in", values, "assignRepayInterest");
            return (Criteria) this;
        }

        public Criteria andAssignRepayInterestBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("assign_repay_interest between", value1, value2, "assignRepayInterest");
            return (Criteria) this;
        }

        public Criteria andAssignRepayInterestNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("assign_repay_interest not between", value1, value2, "assignRepayInterest");
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

        public Criteria andCreditFeeIsNull() {
            addCriterion("credit_fee is null");
            return (Criteria) this;
        }

        public Criteria andCreditFeeIsNotNull() {
            addCriterion("credit_fee is not null");
            return (Criteria) this;
        }

        public Criteria andCreditFeeEqualTo(BigDecimal value) {
            addCriterion("credit_fee =", value, "creditFee");
            return (Criteria) this;
        }

        public Criteria andCreditFeeNotEqualTo(BigDecimal value) {
            addCriterion("credit_fee <>", value, "creditFee");
            return (Criteria) this;
        }

        public Criteria andCreditFeeGreaterThan(BigDecimal value) {
            addCriterion("credit_fee >", value, "creditFee");
            return (Criteria) this;
        }

        public Criteria andCreditFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("credit_fee >=", value, "creditFee");
            return (Criteria) this;
        }

        public Criteria andCreditFeeLessThan(BigDecimal value) {
            addCriterion("credit_fee <", value, "creditFee");
            return (Criteria) this;
        }

        public Criteria andCreditFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("credit_fee <=", value, "creditFee");
            return (Criteria) this;
        }

        public Criteria andCreditFeeIn(List<BigDecimal> values) {
            addCriterion("credit_fee in", values, "creditFee");
            return (Criteria) this;
        }

        public Criteria andCreditFeeNotIn(List<BigDecimal> values) {
            addCriterion("credit_fee not in", values, "creditFee");
            return (Criteria) this;
        }

        public Criteria andCreditFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("credit_fee between", value1, value2, "creditFee");
            return (Criteria) this;
        }

        public Criteria andCreditFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("credit_fee not between", value1, value2, "creditFee");
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

        public Criteria andInviteUserRegionnameIsNull() {
            addCriterion("invite_user_regionname is null");
            return (Criteria) this;
        }

        public Criteria andInviteUserRegionnameIsNotNull() {
            addCriterion("invite_user_regionname is not null");
            return (Criteria) this;
        }

        public Criteria andInviteUserRegionnameEqualTo(String value) {
            addCriterion("invite_user_regionname =", value, "inviteUserRegionname");
            return (Criteria) this;
        }

        public Criteria andInviteUserRegionnameNotEqualTo(String value) {
            addCriterion("invite_user_regionname <>", value, "inviteUserRegionname");
            return (Criteria) this;
        }

        public Criteria andInviteUserRegionnameGreaterThan(String value) {
            addCriterion("invite_user_regionname >", value, "inviteUserRegionname");
            return (Criteria) this;
        }

        public Criteria andInviteUserRegionnameGreaterThanOrEqualTo(String value) {
            addCriterion("invite_user_regionname >=", value, "inviteUserRegionname");
            return (Criteria) this;
        }

        public Criteria andInviteUserRegionnameLessThan(String value) {
            addCriterion("invite_user_regionname <", value, "inviteUserRegionname");
            return (Criteria) this;
        }

        public Criteria andInviteUserRegionnameLessThanOrEqualTo(String value) {
            addCriterion("invite_user_regionname <=", value, "inviteUserRegionname");
            return (Criteria) this;
        }

        public Criteria andInviteUserRegionnameLike(String value) {
            addCriterion("invite_user_regionname like", value, "inviteUserRegionname");
            return (Criteria) this;
        }

        public Criteria andInviteUserRegionnameNotLike(String value) {
            addCriterion("invite_user_regionname not like", value, "inviteUserRegionname");
            return (Criteria) this;
        }

        public Criteria andInviteUserRegionnameIn(List<String> values) {
            addCriterion("invite_user_regionname in", values, "inviteUserRegionname");
            return (Criteria) this;
        }

        public Criteria andInviteUserRegionnameNotIn(List<String> values) {
            addCriterion("invite_user_regionname not in", values, "inviteUserRegionname");
            return (Criteria) this;
        }

        public Criteria andInviteUserRegionnameBetween(String value1, String value2) {
            addCriterion("invite_user_regionname between", value1, value2, "inviteUserRegionname");
            return (Criteria) this;
        }

        public Criteria andInviteUserRegionnameNotBetween(String value1, String value2) {
            addCriterion("invite_user_regionname not between", value1, value2, "inviteUserRegionname");
            return (Criteria) this;
        }

        public Criteria andInviteUserBranchnameIsNull() {
            addCriterion("invite_user_branchname is null");
            return (Criteria) this;
        }

        public Criteria andInviteUserBranchnameIsNotNull() {
            addCriterion("invite_user_branchname is not null");
            return (Criteria) this;
        }

        public Criteria andInviteUserBranchnameEqualTo(String value) {
            addCriterion("invite_user_branchname =", value, "inviteUserBranchname");
            return (Criteria) this;
        }

        public Criteria andInviteUserBranchnameNotEqualTo(String value) {
            addCriterion("invite_user_branchname <>", value, "inviteUserBranchname");
            return (Criteria) this;
        }

        public Criteria andInviteUserBranchnameGreaterThan(String value) {
            addCriterion("invite_user_branchname >", value, "inviteUserBranchname");
            return (Criteria) this;
        }

        public Criteria andInviteUserBranchnameGreaterThanOrEqualTo(String value) {
            addCriterion("invite_user_branchname >=", value, "inviteUserBranchname");
            return (Criteria) this;
        }

        public Criteria andInviteUserBranchnameLessThan(String value) {
            addCriterion("invite_user_branchname <", value, "inviteUserBranchname");
            return (Criteria) this;
        }

        public Criteria andInviteUserBranchnameLessThanOrEqualTo(String value) {
            addCriterion("invite_user_branchname <=", value, "inviteUserBranchname");
            return (Criteria) this;
        }

        public Criteria andInviteUserBranchnameLike(String value) {
            addCriterion("invite_user_branchname like", value, "inviteUserBranchname");
            return (Criteria) this;
        }

        public Criteria andInviteUserBranchnameNotLike(String value) {
            addCriterion("invite_user_branchname not like", value, "inviteUserBranchname");
            return (Criteria) this;
        }

        public Criteria andInviteUserBranchnameIn(List<String> values) {
            addCriterion("invite_user_branchname in", values, "inviteUserBranchname");
            return (Criteria) this;
        }

        public Criteria andInviteUserBranchnameNotIn(List<String> values) {
            addCriterion("invite_user_branchname not in", values, "inviteUserBranchname");
            return (Criteria) this;
        }

        public Criteria andInviteUserBranchnameBetween(String value1, String value2) {
            addCriterion("invite_user_branchname between", value1, value2, "inviteUserBranchname");
            return (Criteria) this;
        }

        public Criteria andInviteUserBranchnameNotBetween(String value1, String value2) {
            addCriterion("invite_user_branchname not between", value1, value2, "inviteUserBranchname");
            return (Criteria) this;
        }

        public Criteria andInviteUserDepartmentnameIsNull() {
            addCriterion("invite_user_departmentname is null");
            return (Criteria) this;
        }

        public Criteria andInviteUserDepartmentnameIsNotNull() {
            addCriterion("invite_user_departmentname is not null");
            return (Criteria) this;
        }

        public Criteria andInviteUserDepartmentnameEqualTo(String value) {
            addCriterion("invite_user_departmentname =", value, "inviteUserDepartmentname");
            return (Criteria) this;
        }

        public Criteria andInviteUserDepartmentnameNotEqualTo(String value) {
            addCriterion("invite_user_departmentname <>", value, "inviteUserDepartmentname");
            return (Criteria) this;
        }

        public Criteria andInviteUserDepartmentnameGreaterThan(String value) {
            addCriterion("invite_user_departmentname >", value, "inviteUserDepartmentname");
            return (Criteria) this;
        }

        public Criteria andInviteUserDepartmentnameGreaterThanOrEqualTo(String value) {
            addCriterion("invite_user_departmentname >=", value, "inviteUserDepartmentname");
            return (Criteria) this;
        }

        public Criteria andInviteUserDepartmentnameLessThan(String value) {
            addCriterion("invite_user_departmentname <", value, "inviteUserDepartmentname");
            return (Criteria) this;
        }

        public Criteria andInviteUserDepartmentnameLessThanOrEqualTo(String value) {
            addCriterion("invite_user_departmentname <=", value, "inviteUserDepartmentname");
            return (Criteria) this;
        }

        public Criteria andInviteUserDepartmentnameLike(String value) {
            addCriterion("invite_user_departmentname like", value, "inviteUserDepartmentname");
            return (Criteria) this;
        }

        public Criteria andInviteUserDepartmentnameNotLike(String value) {
            addCriterion("invite_user_departmentname not like", value, "inviteUserDepartmentname");
            return (Criteria) this;
        }

        public Criteria andInviteUserDepartmentnameIn(List<String> values) {
            addCriterion("invite_user_departmentname in", values, "inviteUserDepartmentname");
            return (Criteria) this;
        }

        public Criteria andInviteUserDepartmentnameNotIn(List<String> values) {
            addCriterion("invite_user_departmentname not in", values, "inviteUserDepartmentname");
            return (Criteria) this;
        }

        public Criteria andInviteUserDepartmentnameBetween(String value1, String value2) {
            addCriterion("invite_user_departmentname between", value1, value2, "inviteUserDepartmentname");
            return (Criteria) this;
        }

        public Criteria andInviteUserDepartmentnameNotBetween(String value1, String value2) {
            addCriterion("invite_user_departmentname not between", value1, value2, "inviteUserDepartmentname");
            return (Criteria) this;
        }

        public Criteria andInviteUserCreditNameIsNull() {
            addCriterion("invite_user_credit_name is null");
            return (Criteria) this;
        }

        public Criteria andInviteUserCreditNameIsNotNull() {
            addCriterion("invite_user_credit_name is not null");
            return (Criteria) this;
        }

        public Criteria andInviteUserCreditNameEqualTo(String value) {
            addCriterion("invite_user_credit_name =", value, "inviteUserCreditName");
            return (Criteria) this;
        }

        public Criteria andInviteUserCreditNameNotEqualTo(String value) {
            addCriterion("invite_user_credit_name <>", value, "inviteUserCreditName");
            return (Criteria) this;
        }

        public Criteria andInviteUserCreditNameGreaterThan(String value) {
            addCriterion("invite_user_credit_name >", value, "inviteUserCreditName");
            return (Criteria) this;
        }

        public Criteria andInviteUserCreditNameGreaterThanOrEqualTo(String value) {
            addCriterion("invite_user_credit_name >=", value, "inviteUserCreditName");
            return (Criteria) this;
        }

        public Criteria andInviteUserCreditNameLessThan(String value) {
            addCriterion("invite_user_credit_name <", value, "inviteUserCreditName");
            return (Criteria) this;
        }

        public Criteria andInviteUserCreditNameLessThanOrEqualTo(String value) {
            addCriterion("invite_user_credit_name <=", value, "inviteUserCreditName");
            return (Criteria) this;
        }

        public Criteria andInviteUserCreditNameLike(String value) {
            addCriterion("invite_user_credit_name like", value, "inviteUserCreditName");
            return (Criteria) this;
        }

        public Criteria andInviteUserCreditNameNotLike(String value) {
            addCriterion("invite_user_credit_name not like", value, "inviteUserCreditName");
            return (Criteria) this;
        }

        public Criteria andInviteUserCreditNameIn(List<String> values) {
            addCriterion("invite_user_credit_name in", values, "inviteUserCreditName");
            return (Criteria) this;
        }

        public Criteria andInviteUserCreditNameNotIn(List<String> values) {
            addCriterion("invite_user_credit_name not in", values, "inviteUserCreditName");
            return (Criteria) this;
        }

        public Criteria andInviteUserCreditNameBetween(String value1, String value2) {
            addCriterion("invite_user_credit_name between", value1, value2, "inviteUserCreditName");
            return (Criteria) this;
        }

        public Criteria andInviteUserCreditNameNotBetween(String value1, String value2) {
            addCriterion("invite_user_credit_name not between", value1, value2, "inviteUserCreditName");
            return (Criteria) this;
        }

        public Criteria andInviteUserCreditAttributeIsNull() {
            addCriterion("invite_user_credit_attribute is null");
            return (Criteria) this;
        }

        public Criteria andInviteUserCreditAttributeIsNotNull() {
            addCriterion("invite_user_credit_attribute is not null");
            return (Criteria) this;
        }

        public Criteria andInviteUserCreditAttributeEqualTo(Integer value) {
            addCriterion("invite_user_credit_attribute =", value, "inviteUserCreditAttribute");
            return (Criteria) this;
        }

        public Criteria andInviteUserCreditAttributeNotEqualTo(Integer value) {
            addCriterion("invite_user_credit_attribute <>", value, "inviteUserCreditAttribute");
            return (Criteria) this;
        }

        public Criteria andInviteUserCreditAttributeGreaterThan(Integer value) {
            addCriterion("invite_user_credit_attribute >", value, "inviteUserCreditAttribute");
            return (Criteria) this;
        }

        public Criteria andInviteUserCreditAttributeGreaterThanOrEqualTo(Integer value) {
            addCriterion("invite_user_credit_attribute >=", value, "inviteUserCreditAttribute");
            return (Criteria) this;
        }

        public Criteria andInviteUserCreditAttributeLessThan(Integer value) {
            addCriterion("invite_user_credit_attribute <", value, "inviteUserCreditAttribute");
            return (Criteria) this;
        }

        public Criteria andInviteUserCreditAttributeLessThanOrEqualTo(Integer value) {
            addCriterion("invite_user_credit_attribute <=", value, "inviteUserCreditAttribute");
            return (Criteria) this;
        }

        public Criteria andInviteUserCreditAttributeIn(List<Integer> values) {
            addCriterion("invite_user_credit_attribute in", values, "inviteUserCreditAttribute");
            return (Criteria) this;
        }

        public Criteria andInviteUserCreditAttributeNotIn(List<Integer> values) {
            addCriterion("invite_user_credit_attribute not in", values, "inviteUserCreditAttribute");
            return (Criteria) this;
        }

        public Criteria andInviteUserCreditAttributeBetween(Integer value1, Integer value2) {
            addCriterion("invite_user_credit_attribute between", value1, value2, "inviteUserCreditAttribute");
            return (Criteria) this;
        }

        public Criteria andInviteUserCreditAttributeNotBetween(Integer value1, Integer value2) {
            addCriterion("invite_user_credit_attribute not between", value1, value2, "inviteUserCreditAttribute");
            return (Criteria) this;
        }

        public Criteria andInviteUserCreditRegionnameIsNull() {
            addCriterion("invite_user_credit_regionname is null");
            return (Criteria) this;
        }

        public Criteria andInviteUserCreditRegionnameIsNotNull() {
            addCriterion("invite_user_credit_regionname is not null");
            return (Criteria) this;
        }

        public Criteria andInviteUserCreditRegionnameEqualTo(String value) {
            addCriterion("invite_user_credit_regionname =", value, "inviteUserCreditRegionname");
            return (Criteria) this;
        }

        public Criteria andInviteUserCreditRegionnameNotEqualTo(String value) {
            addCriterion("invite_user_credit_regionname <>", value, "inviteUserCreditRegionname");
            return (Criteria) this;
        }

        public Criteria andInviteUserCreditRegionnameGreaterThan(String value) {
            addCriterion("invite_user_credit_regionname >", value, "inviteUserCreditRegionname");
            return (Criteria) this;
        }

        public Criteria andInviteUserCreditRegionnameGreaterThanOrEqualTo(String value) {
            addCriterion("invite_user_credit_regionname >=", value, "inviteUserCreditRegionname");
            return (Criteria) this;
        }

        public Criteria andInviteUserCreditRegionnameLessThan(String value) {
            addCriterion("invite_user_credit_regionname <", value, "inviteUserCreditRegionname");
            return (Criteria) this;
        }

        public Criteria andInviteUserCreditRegionnameLessThanOrEqualTo(String value) {
            addCriterion("invite_user_credit_regionname <=", value, "inviteUserCreditRegionname");
            return (Criteria) this;
        }

        public Criteria andInviteUserCreditRegionnameLike(String value) {
            addCriterion("invite_user_credit_regionname like", value, "inviteUserCreditRegionname");
            return (Criteria) this;
        }

        public Criteria andInviteUserCreditRegionnameNotLike(String value) {
            addCriterion("invite_user_credit_regionname not like", value, "inviteUserCreditRegionname");
            return (Criteria) this;
        }

        public Criteria andInviteUserCreditRegionnameIn(List<String> values) {
            addCriterion("invite_user_credit_regionname in", values, "inviteUserCreditRegionname");
            return (Criteria) this;
        }

        public Criteria andInviteUserCreditRegionnameNotIn(List<String> values) {
            addCriterion("invite_user_credit_regionname not in", values, "inviteUserCreditRegionname");
            return (Criteria) this;
        }

        public Criteria andInviteUserCreditRegionnameBetween(String value1, String value2) {
            addCriterion("invite_user_credit_regionname between", value1, value2, "inviteUserCreditRegionname");
            return (Criteria) this;
        }

        public Criteria andInviteUserCreditRegionnameNotBetween(String value1, String value2) {
            addCriterion("invite_user_credit_regionname not between", value1, value2, "inviteUserCreditRegionname");
            return (Criteria) this;
        }

        public Criteria andInviteUserCreditBranchnameIsNull() {
            addCriterion("invite_user_credit_branchname is null");
            return (Criteria) this;
        }

        public Criteria andInviteUserCreditBranchnameIsNotNull() {
            addCriterion("invite_user_credit_branchname is not null");
            return (Criteria) this;
        }

        public Criteria andInviteUserCreditBranchnameEqualTo(String value) {
            addCriterion("invite_user_credit_branchname =", value, "inviteUserCreditBranchname");
            return (Criteria) this;
        }

        public Criteria andInviteUserCreditBranchnameNotEqualTo(String value) {
            addCriterion("invite_user_credit_branchname <>", value, "inviteUserCreditBranchname");
            return (Criteria) this;
        }

        public Criteria andInviteUserCreditBranchnameGreaterThan(String value) {
            addCriterion("invite_user_credit_branchname >", value, "inviteUserCreditBranchname");
            return (Criteria) this;
        }

        public Criteria andInviteUserCreditBranchnameGreaterThanOrEqualTo(String value) {
            addCriterion("invite_user_credit_branchname >=", value, "inviteUserCreditBranchname");
            return (Criteria) this;
        }

        public Criteria andInviteUserCreditBranchnameLessThan(String value) {
            addCriterion("invite_user_credit_branchname <", value, "inviteUserCreditBranchname");
            return (Criteria) this;
        }

        public Criteria andInviteUserCreditBranchnameLessThanOrEqualTo(String value) {
            addCriterion("invite_user_credit_branchname <=", value, "inviteUserCreditBranchname");
            return (Criteria) this;
        }

        public Criteria andInviteUserCreditBranchnameLike(String value) {
            addCriterion("invite_user_credit_branchname like", value, "inviteUserCreditBranchname");
            return (Criteria) this;
        }

        public Criteria andInviteUserCreditBranchnameNotLike(String value) {
            addCriterion("invite_user_credit_branchname not like", value, "inviteUserCreditBranchname");
            return (Criteria) this;
        }

        public Criteria andInviteUserCreditBranchnameIn(List<String> values) {
            addCriterion("invite_user_credit_branchname in", values, "inviteUserCreditBranchname");
            return (Criteria) this;
        }

        public Criteria andInviteUserCreditBranchnameNotIn(List<String> values) {
            addCriterion("invite_user_credit_branchname not in", values, "inviteUserCreditBranchname");
            return (Criteria) this;
        }

        public Criteria andInviteUserCreditBranchnameBetween(String value1, String value2) {
            addCriterion("invite_user_credit_branchname between", value1, value2, "inviteUserCreditBranchname");
            return (Criteria) this;
        }

        public Criteria andInviteUserCreditBranchnameNotBetween(String value1, String value2) {
            addCriterion("invite_user_credit_branchname not between", value1, value2, "inviteUserCreditBranchname");
            return (Criteria) this;
        }

        public Criteria andInviteUserCreditDepartmentnameIsNull() {
            addCriterion("invite_user_credit_departmentname is null");
            return (Criteria) this;
        }

        public Criteria andInviteUserCreditDepartmentnameIsNotNull() {
            addCriterion("invite_user_credit_departmentname is not null");
            return (Criteria) this;
        }

        public Criteria andInviteUserCreditDepartmentnameEqualTo(String value) {
            addCriterion("invite_user_credit_departmentname =", value, "inviteUserCreditDepartmentname");
            return (Criteria) this;
        }

        public Criteria andInviteUserCreditDepartmentnameNotEqualTo(String value) {
            addCriterion("invite_user_credit_departmentname <>", value, "inviteUserCreditDepartmentname");
            return (Criteria) this;
        }

        public Criteria andInviteUserCreditDepartmentnameGreaterThan(String value) {
            addCriterion("invite_user_credit_departmentname >", value, "inviteUserCreditDepartmentname");
            return (Criteria) this;
        }

        public Criteria andInviteUserCreditDepartmentnameGreaterThanOrEqualTo(String value) {
            addCriterion("invite_user_credit_departmentname >=", value, "inviteUserCreditDepartmentname");
            return (Criteria) this;
        }

        public Criteria andInviteUserCreditDepartmentnameLessThan(String value) {
            addCriterion("invite_user_credit_departmentname <", value, "inviteUserCreditDepartmentname");
            return (Criteria) this;
        }

        public Criteria andInviteUserCreditDepartmentnameLessThanOrEqualTo(String value) {
            addCriterion("invite_user_credit_departmentname <=", value, "inviteUserCreditDepartmentname");
            return (Criteria) this;
        }

        public Criteria andInviteUserCreditDepartmentnameLike(String value) {
            addCriterion("invite_user_credit_departmentname like", value, "inviteUserCreditDepartmentname");
            return (Criteria) this;
        }

        public Criteria andInviteUserCreditDepartmentnameNotLike(String value) {
            addCriterion("invite_user_credit_departmentname not like", value, "inviteUserCreditDepartmentname");
            return (Criteria) this;
        }

        public Criteria andInviteUserCreditDepartmentnameIn(List<String> values) {
            addCriterion("invite_user_credit_departmentname in", values, "inviteUserCreditDepartmentname");
            return (Criteria) this;
        }

        public Criteria andInviteUserCreditDepartmentnameNotIn(List<String> values) {
            addCriterion("invite_user_credit_departmentname not in", values, "inviteUserCreditDepartmentname");
            return (Criteria) this;
        }

        public Criteria andInviteUserCreditDepartmentnameBetween(String value1, String value2) {
            addCriterion("invite_user_credit_departmentname between", value1, value2, "inviteUserCreditDepartmentname");
            return (Criteria) this;
        }

        public Criteria andInviteUserCreditDepartmentnameNotBetween(String value1, String value2) {
            addCriterion("invite_user_credit_departmentname not between", value1, value2, "inviteUserCreditDepartmentname");
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

        public Criteria andRecoverPeriodIsNull() {
            addCriterion("recover_period is null");
            return (Criteria) this;
        }

        public Criteria andRecoverPeriodIsNotNull() {
            addCriterion("recover_period is not null");
            return (Criteria) this;
        }

        public Criteria andRecoverPeriodEqualTo(Integer value) {
            addCriterion("recover_period =", value, "recoverPeriod");
            return (Criteria) this;
        }

        public Criteria andRecoverPeriodNotEqualTo(Integer value) {
            addCriterion("recover_period <>", value, "recoverPeriod");
            return (Criteria) this;
        }

        public Criteria andRecoverPeriodGreaterThan(Integer value) {
            addCriterion("recover_period >", value, "recoverPeriod");
            return (Criteria) this;
        }

        public Criteria andRecoverPeriodGreaterThanOrEqualTo(Integer value) {
            addCriterion("recover_period >=", value, "recoverPeriod");
            return (Criteria) this;
        }

        public Criteria andRecoverPeriodLessThan(Integer value) {
            addCriterion("recover_period <", value, "recoverPeriod");
            return (Criteria) this;
        }

        public Criteria andRecoverPeriodLessThanOrEqualTo(Integer value) {
            addCriterion("recover_period <=", value, "recoverPeriod");
            return (Criteria) this;
        }

        public Criteria andRecoverPeriodIn(List<Integer> values) {
            addCriterion("recover_period in", values, "recoverPeriod");
            return (Criteria) this;
        }

        public Criteria andRecoverPeriodNotIn(List<Integer> values) {
            addCriterion("recover_period not in", values, "recoverPeriod");
            return (Criteria) this;
        }

        public Criteria andRecoverPeriodBetween(Integer value1, Integer value2) {
            addCriterion("recover_period between", value1, value2, "recoverPeriod");
            return (Criteria) this;
        }

        public Criteria andRecoverPeriodNotBetween(Integer value1, Integer value2) {
            addCriterion("recover_period not between", value1, value2, "recoverPeriod");
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