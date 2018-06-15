package com.hyjf.am.trade.dao.model.auto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BorrowCreditExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public BorrowCreditExample() {
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

        public Criteria andCreditIdIsNull() {
            addCriterion("credit_id is null");
            return (Criteria) this;
        }

        public Criteria andCreditIdIsNotNull() {
            addCriterion("credit_id is not null");
            return (Criteria) this;
        }

        public Criteria andCreditIdEqualTo(Integer value) {
            addCriterion("credit_id =", value, "creditId");
            return (Criteria) this;
        }

        public Criteria andCreditIdNotEqualTo(Integer value) {
            addCriterion("credit_id <>", value, "creditId");
            return (Criteria) this;
        }

        public Criteria andCreditIdGreaterThan(Integer value) {
            addCriterion("credit_id >", value, "creditId");
            return (Criteria) this;
        }

        public Criteria andCreditIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("credit_id >=", value, "creditId");
            return (Criteria) this;
        }

        public Criteria andCreditIdLessThan(Integer value) {
            addCriterion("credit_id <", value, "creditId");
            return (Criteria) this;
        }

        public Criteria andCreditIdLessThanOrEqualTo(Integer value) {
            addCriterion("credit_id <=", value, "creditId");
            return (Criteria) this;
        }

        public Criteria andCreditIdIn(List<Integer> values) {
            addCriterion("credit_id in", values, "creditId");
            return (Criteria) this;
        }

        public Criteria andCreditIdNotIn(List<Integer> values) {
            addCriterion("credit_id not in", values, "creditId");
            return (Criteria) this;
        }

        public Criteria andCreditIdBetween(Integer value1, Integer value2) {
            addCriterion("credit_id between", value1, value2, "creditId");
            return (Criteria) this;
        }

        public Criteria andCreditIdNotBetween(Integer value1, Integer value2) {
            addCriterion("credit_id not between", value1, value2, "creditId");
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

        public Criteria andCreditNidEqualTo(Integer value) {
            addCriterion("credit_nid =", value, "creditNid");
            return (Criteria) this;
        }

        public Criteria andCreditNidNotEqualTo(Integer value) {
            addCriterion("credit_nid <>", value, "creditNid");
            return (Criteria) this;
        }

        public Criteria andCreditNidGreaterThan(Integer value) {
            addCriterion("credit_nid >", value, "creditNid");
            return (Criteria) this;
        }

        public Criteria andCreditNidGreaterThanOrEqualTo(Integer value) {
            addCriterion("credit_nid >=", value, "creditNid");
            return (Criteria) this;
        }

        public Criteria andCreditNidLessThan(Integer value) {
            addCriterion("credit_nid <", value, "creditNid");
            return (Criteria) this;
        }

        public Criteria andCreditNidLessThanOrEqualTo(Integer value) {
            addCriterion("credit_nid <=", value, "creditNid");
            return (Criteria) this;
        }

        public Criteria andCreditNidIn(List<Integer> values) {
            addCriterion("credit_nid in", values, "creditNid");
            return (Criteria) this;
        }

        public Criteria andCreditNidNotIn(List<Integer> values) {
            addCriterion("credit_nid not in", values, "creditNid");
            return (Criteria) this;
        }

        public Criteria andCreditNidBetween(Integer value1, Integer value2) {
            addCriterion("credit_nid between", value1, value2, "creditNid");
            return (Criteria) this;
        }

        public Criteria andCreditNidNotBetween(Integer value1, Integer value2) {
            addCriterion("credit_nid not between", value1, value2, "creditNid");
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

        public Criteria andBidAprIsNull() {
            addCriterion("bid_apr is null");
            return (Criteria) this;
        }

        public Criteria andBidAprIsNotNull() {
            addCriterion("bid_apr is not null");
            return (Criteria) this;
        }

        public Criteria andBidAprEqualTo(BigDecimal value) {
            addCriterion("bid_apr =", value, "bidApr");
            return (Criteria) this;
        }

        public Criteria andBidAprNotEqualTo(BigDecimal value) {
            addCriterion("bid_apr <>", value, "bidApr");
            return (Criteria) this;
        }

        public Criteria andBidAprGreaterThan(BigDecimal value) {
            addCriterion("bid_apr >", value, "bidApr");
            return (Criteria) this;
        }

        public Criteria andBidAprGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("bid_apr >=", value, "bidApr");
            return (Criteria) this;
        }

        public Criteria andBidAprLessThan(BigDecimal value) {
            addCriterion("bid_apr <", value, "bidApr");
            return (Criteria) this;
        }

        public Criteria andBidAprLessThanOrEqualTo(BigDecimal value) {
            addCriterion("bid_apr <=", value, "bidApr");
            return (Criteria) this;
        }

        public Criteria andBidAprIn(List<BigDecimal> values) {
            addCriterion("bid_apr in", values, "bidApr");
            return (Criteria) this;
        }

        public Criteria andBidAprNotIn(List<BigDecimal> values) {
            addCriterion("bid_apr not in", values, "bidApr");
            return (Criteria) this;
        }

        public Criteria andBidAprBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("bid_apr between", value1, value2, "bidApr");
            return (Criteria) this;
        }

        public Criteria andBidAprNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("bid_apr not between", value1, value2, "bidApr");
            return (Criteria) this;
        }

        public Criteria andBidNameIsNull() {
            addCriterion("bid_name is null");
            return (Criteria) this;
        }

        public Criteria andBidNameIsNotNull() {
            addCriterion("bid_name is not null");
            return (Criteria) this;
        }

        public Criteria andBidNameEqualTo(String value) {
            addCriterion("bid_name =", value, "bidName");
            return (Criteria) this;
        }

        public Criteria andBidNameNotEqualTo(String value) {
            addCriterion("bid_name <>", value, "bidName");
            return (Criteria) this;
        }

        public Criteria andBidNameGreaterThan(String value) {
            addCriterion("bid_name >", value, "bidName");
            return (Criteria) this;
        }

        public Criteria andBidNameGreaterThanOrEqualTo(String value) {
            addCriterion("bid_name >=", value, "bidName");
            return (Criteria) this;
        }

        public Criteria andBidNameLessThan(String value) {
            addCriterion("bid_name <", value, "bidName");
            return (Criteria) this;
        }

        public Criteria andBidNameLessThanOrEqualTo(String value) {
            addCriterion("bid_name <=", value, "bidName");
            return (Criteria) this;
        }

        public Criteria andBidNameLike(String value) {
            addCriterion("bid_name like", value, "bidName");
            return (Criteria) this;
        }

        public Criteria andBidNameNotLike(String value) {
            addCriterion("bid_name not like", value, "bidName");
            return (Criteria) this;
        }

        public Criteria andBidNameIn(List<String> values) {
            addCriterion("bid_name in", values, "bidName");
            return (Criteria) this;
        }

        public Criteria andBidNameNotIn(List<String> values) {
            addCriterion("bid_name not in", values, "bidName");
            return (Criteria) this;
        }

        public Criteria andBidNameBetween(String value1, String value2) {
            addCriterion("bid_name between", value1, value2, "bidName");
            return (Criteria) this;
        }

        public Criteria andBidNameNotBetween(String value1, String value2) {
            addCriterion("bid_name not between", value1, value2, "bidName");
            return (Criteria) this;
        }

        public Criteria andTenderNidIsNull() {
            addCriterion("tender_nid is null");
            return (Criteria) this;
        }

        public Criteria andTenderNidIsNotNull() {
            addCriterion("tender_nid is not null");
            return (Criteria) this;
        }

        public Criteria andTenderNidEqualTo(String value) {
            addCriterion("tender_nid =", value, "tenderNid");
            return (Criteria) this;
        }

        public Criteria andTenderNidNotEqualTo(String value) {
            addCriterion("tender_nid <>", value, "tenderNid");
            return (Criteria) this;
        }

        public Criteria andTenderNidGreaterThan(String value) {
            addCriterion("tender_nid >", value, "tenderNid");
            return (Criteria) this;
        }

        public Criteria andTenderNidGreaterThanOrEqualTo(String value) {
            addCriterion("tender_nid >=", value, "tenderNid");
            return (Criteria) this;
        }

        public Criteria andTenderNidLessThan(String value) {
            addCriterion("tender_nid <", value, "tenderNid");
            return (Criteria) this;
        }

        public Criteria andTenderNidLessThanOrEqualTo(String value) {
            addCriterion("tender_nid <=", value, "tenderNid");
            return (Criteria) this;
        }

        public Criteria andTenderNidLike(String value) {
            addCriterion("tender_nid like", value, "tenderNid");
            return (Criteria) this;
        }

        public Criteria andTenderNidNotLike(String value) {
            addCriterion("tender_nid not like", value, "tenderNid");
            return (Criteria) this;
        }

        public Criteria andTenderNidIn(List<String> values) {
            addCriterion("tender_nid in", values, "tenderNid");
            return (Criteria) this;
        }

        public Criteria andTenderNidNotIn(List<String> values) {
            addCriterion("tender_nid not in", values, "tenderNid");
            return (Criteria) this;
        }

        public Criteria andTenderNidBetween(String value1, String value2) {
            addCriterion("tender_nid between", value1, value2, "tenderNid");
            return (Criteria) this;
        }

        public Criteria andTenderNidNotBetween(String value1, String value2) {
            addCriterion("tender_nid not between", value1, value2, "tenderNid");
            return (Criteria) this;
        }

        public Criteria andCreditStatusIsNull() {
            addCriterion("credit_status is null");
            return (Criteria) this;
        }

        public Criteria andCreditStatusIsNotNull() {
            addCriterion("credit_status is not null");
            return (Criteria) this;
        }

        public Criteria andCreditStatusEqualTo(Integer value) {
            addCriterion("credit_status =", value, "creditStatus");
            return (Criteria) this;
        }

        public Criteria andCreditStatusNotEqualTo(Integer value) {
            addCriterion("credit_status <>", value, "creditStatus");
            return (Criteria) this;
        }

        public Criteria andCreditStatusGreaterThan(Integer value) {
            addCriterion("credit_status >", value, "creditStatus");
            return (Criteria) this;
        }

        public Criteria andCreditStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("credit_status >=", value, "creditStatus");
            return (Criteria) this;
        }

        public Criteria andCreditStatusLessThan(Integer value) {
            addCriterion("credit_status <", value, "creditStatus");
            return (Criteria) this;
        }

        public Criteria andCreditStatusLessThanOrEqualTo(Integer value) {
            addCriterion("credit_status <=", value, "creditStatus");
            return (Criteria) this;
        }

        public Criteria andCreditStatusIn(List<Integer> values) {
            addCriterion("credit_status in", values, "creditStatus");
            return (Criteria) this;
        }

        public Criteria andCreditStatusNotIn(List<Integer> values) {
            addCriterion("credit_status not in", values, "creditStatus");
            return (Criteria) this;
        }

        public Criteria andCreditStatusBetween(Integer value1, Integer value2) {
            addCriterion("credit_status between", value1, value2, "creditStatus");
            return (Criteria) this;
        }

        public Criteria andCreditStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("credit_status not between", value1, value2, "creditStatus");
            return (Criteria) this;
        }

        public Criteria andCreditOrderIsNull() {
            addCriterion("credit_order is null");
            return (Criteria) this;
        }

        public Criteria andCreditOrderIsNotNull() {
            addCriterion("credit_order is not null");
            return (Criteria) this;
        }

        public Criteria andCreditOrderEqualTo(Integer value) {
            addCriterion("credit_order =", value, "creditOrder");
            return (Criteria) this;
        }

        public Criteria andCreditOrderNotEqualTo(Integer value) {
            addCriterion("credit_order <>", value, "creditOrder");
            return (Criteria) this;
        }

        public Criteria andCreditOrderGreaterThan(Integer value) {
            addCriterion("credit_order >", value, "creditOrder");
            return (Criteria) this;
        }

        public Criteria andCreditOrderGreaterThanOrEqualTo(Integer value) {
            addCriterion("credit_order >=", value, "creditOrder");
            return (Criteria) this;
        }

        public Criteria andCreditOrderLessThan(Integer value) {
            addCriterion("credit_order <", value, "creditOrder");
            return (Criteria) this;
        }

        public Criteria andCreditOrderLessThanOrEqualTo(Integer value) {
            addCriterion("credit_order <=", value, "creditOrder");
            return (Criteria) this;
        }

        public Criteria andCreditOrderIn(List<Integer> values) {
            addCriterion("credit_order in", values, "creditOrder");
            return (Criteria) this;
        }

        public Criteria andCreditOrderNotIn(List<Integer> values) {
            addCriterion("credit_order not in", values, "creditOrder");
            return (Criteria) this;
        }

        public Criteria andCreditOrderBetween(Integer value1, Integer value2) {
            addCriterion("credit_order between", value1, value2, "creditOrder");
            return (Criteria) this;
        }

        public Criteria andCreditOrderNotBetween(Integer value1, Integer value2) {
            addCriterion("credit_order not between", value1, value2, "creditOrder");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodIsNull() {
            addCriterion("credit_period is null");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodIsNotNull() {
            addCriterion("credit_period is not null");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodEqualTo(Integer value) {
            addCriterion("credit_period =", value, "creditPeriod");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodNotEqualTo(Integer value) {
            addCriterion("credit_period <>", value, "creditPeriod");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodGreaterThan(Integer value) {
            addCriterion("credit_period >", value, "creditPeriod");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodGreaterThanOrEqualTo(Integer value) {
            addCriterion("credit_period >=", value, "creditPeriod");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodLessThan(Integer value) {
            addCriterion("credit_period <", value, "creditPeriod");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodLessThanOrEqualTo(Integer value) {
            addCriterion("credit_period <=", value, "creditPeriod");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodIn(List<Integer> values) {
            addCriterion("credit_period in", values, "creditPeriod");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodNotIn(List<Integer> values) {
            addCriterion("credit_period not in", values, "creditPeriod");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodBetween(Integer value1, Integer value2) {
            addCriterion("credit_period between", value1, value2, "creditPeriod");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodNotBetween(Integer value1, Integer value2) {
            addCriterion("credit_period not between", value1, value2, "creditPeriod");
            return (Criteria) this;
        }

        public Criteria andCreditTermIsNull() {
            addCriterion("credit_term is null");
            return (Criteria) this;
        }

        public Criteria andCreditTermIsNotNull() {
            addCriterion("credit_term is not null");
            return (Criteria) this;
        }

        public Criteria andCreditTermEqualTo(Integer value) {
            addCriterion("credit_term =", value, "creditTerm");
            return (Criteria) this;
        }

        public Criteria andCreditTermNotEqualTo(Integer value) {
            addCriterion("credit_term <>", value, "creditTerm");
            return (Criteria) this;
        }

        public Criteria andCreditTermGreaterThan(Integer value) {
            addCriterion("credit_term >", value, "creditTerm");
            return (Criteria) this;
        }

        public Criteria andCreditTermGreaterThanOrEqualTo(Integer value) {
            addCriterion("credit_term >=", value, "creditTerm");
            return (Criteria) this;
        }

        public Criteria andCreditTermLessThan(Integer value) {
            addCriterion("credit_term <", value, "creditTerm");
            return (Criteria) this;
        }

        public Criteria andCreditTermLessThanOrEqualTo(Integer value) {
            addCriterion("credit_term <=", value, "creditTerm");
            return (Criteria) this;
        }

        public Criteria andCreditTermIn(List<Integer> values) {
            addCriterion("credit_term in", values, "creditTerm");
            return (Criteria) this;
        }

        public Criteria andCreditTermNotIn(List<Integer> values) {
            addCriterion("credit_term not in", values, "creditTerm");
            return (Criteria) this;
        }

        public Criteria andCreditTermBetween(Integer value1, Integer value2) {
            addCriterion("credit_term between", value1, value2, "creditTerm");
            return (Criteria) this;
        }

        public Criteria andCreditTermNotBetween(Integer value1, Integer value2) {
            addCriterion("credit_term not between", value1, value2, "creditTerm");
            return (Criteria) this;
        }

        public Criteria andCreditTermHoldIsNull() {
            addCriterion("credit_term_hold is null");
            return (Criteria) this;
        }

        public Criteria andCreditTermHoldIsNotNull() {
            addCriterion("credit_term_hold is not null");
            return (Criteria) this;
        }

        public Criteria andCreditTermHoldEqualTo(Integer value) {
            addCriterion("credit_term_hold =", value, "creditTermHold");
            return (Criteria) this;
        }

        public Criteria andCreditTermHoldNotEqualTo(Integer value) {
            addCriterion("credit_term_hold <>", value, "creditTermHold");
            return (Criteria) this;
        }

        public Criteria andCreditTermHoldGreaterThan(Integer value) {
            addCriterion("credit_term_hold >", value, "creditTermHold");
            return (Criteria) this;
        }

        public Criteria andCreditTermHoldGreaterThanOrEqualTo(Integer value) {
            addCriterion("credit_term_hold >=", value, "creditTermHold");
            return (Criteria) this;
        }

        public Criteria andCreditTermHoldLessThan(Integer value) {
            addCriterion("credit_term_hold <", value, "creditTermHold");
            return (Criteria) this;
        }

        public Criteria andCreditTermHoldLessThanOrEqualTo(Integer value) {
            addCriterion("credit_term_hold <=", value, "creditTermHold");
            return (Criteria) this;
        }

        public Criteria andCreditTermHoldIn(List<Integer> values) {
            addCriterion("credit_term_hold in", values, "creditTermHold");
            return (Criteria) this;
        }

        public Criteria andCreditTermHoldNotIn(List<Integer> values) {
            addCriterion("credit_term_hold not in", values, "creditTermHold");
            return (Criteria) this;
        }

        public Criteria andCreditTermHoldBetween(Integer value1, Integer value2) {
            addCriterion("credit_term_hold between", value1, value2, "creditTermHold");
            return (Criteria) this;
        }

        public Criteria andCreditTermHoldNotBetween(Integer value1, Integer value2) {
            addCriterion("credit_term_hold not between", value1, value2, "creditTermHold");
            return (Criteria) this;
        }

        public Criteria andCreditCapitalIsNull() {
            addCriterion("credit_capital is null");
            return (Criteria) this;
        }

        public Criteria andCreditCapitalIsNotNull() {
            addCriterion("credit_capital is not null");
            return (Criteria) this;
        }

        public Criteria andCreditCapitalEqualTo(BigDecimal value) {
            addCriterion("credit_capital =", value, "creditCapital");
            return (Criteria) this;
        }

        public Criteria andCreditCapitalNotEqualTo(BigDecimal value) {
            addCriterion("credit_capital <>", value, "creditCapital");
            return (Criteria) this;
        }

        public Criteria andCreditCapitalGreaterThan(BigDecimal value) {
            addCriterion("credit_capital >", value, "creditCapital");
            return (Criteria) this;
        }

        public Criteria andCreditCapitalGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("credit_capital >=", value, "creditCapital");
            return (Criteria) this;
        }

        public Criteria andCreditCapitalLessThan(BigDecimal value) {
            addCriterion("credit_capital <", value, "creditCapital");
            return (Criteria) this;
        }

        public Criteria andCreditCapitalLessThanOrEqualTo(BigDecimal value) {
            addCriterion("credit_capital <=", value, "creditCapital");
            return (Criteria) this;
        }

        public Criteria andCreditCapitalIn(List<BigDecimal> values) {
            addCriterion("credit_capital in", values, "creditCapital");
            return (Criteria) this;
        }

        public Criteria andCreditCapitalNotIn(List<BigDecimal> values) {
            addCriterion("credit_capital not in", values, "creditCapital");
            return (Criteria) this;
        }

        public Criteria andCreditCapitalBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("credit_capital between", value1, value2, "creditCapital");
            return (Criteria) this;
        }

        public Criteria andCreditCapitalNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("credit_capital not between", value1, value2, "creditCapital");
            return (Criteria) this;
        }

        public Criteria andCreditAccountIsNull() {
            addCriterion("credit_account is null");
            return (Criteria) this;
        }

        public Criteria andCreditAccountIsNotNull() {
            addCriterion("credit_account is not null");
            return (Criteria) this;
        }

        public Criteria andCreditAccountEqualTo(BigDecimal value) {
            addCriterion("credit_account =", value, "creditAccount");
            return (Criteria) this;
        }

        public Criteria andCreditAccountNotEqualTo(BigDecimal value) {
            addCriterion("credit_account <>", value, "creditAccount");
            return (Criteria) this;
        }

        public Criteria andCreditAccountGreaterThan(BigDecimal value) {
            addCriterion("credit_account >", value, "creditAccount");
            return (Criteria) this;
        }

        public Criteria andCreditAccountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("credit_account >=", value, "creditAccount");
            return (Criteria) this;
        }

        public Criteria andCreditAccountLessThan(BigDecimal value) {
            addCriterion("credit_account <", value, "creditAccount");
            return (Criteria) this;
        }

        public Criteria andCreditAccountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("credit_account <=", value, "creditAccount");
            return (Criteria) this;
        }

        public Criteria andCreditAccountIn(List<BigDecimal> values) {
            addCriterion("credit_account in", values, "creditAccount");
            return (Criteria) this;
        }

        public Criteria andCreditAccountNotIn(List<BigDecimal> values) {
            addCriterion("credit_account not in", values, "creditAccount");
            return (Criteria) this;
        }

        public Criteria andCreditAccountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("credit_account between", value1, value2, "creditAccount");
            return (Criteria) this;
        }

        public Criteria andCreditAccountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("credit_account not between", value1, value2, "creditAccount");
            return (Criteria) this;
        }

        public Criteria andCreditInterestIsNull() {
            addCriterion("credit_interest is null");
            return (Criteria) this;
        }

        public Criteria andCreditInterestIsNotNull() {
            addCriterion("credit_interest is not null");
            return (Criteria) this;
        }

        public Criteria andCreditInterestEqualTo(BigDecimal value) {
            addCriterion("credit_interest =", value, "creditInterest");
            return (Criteria) this;
        }

        public Criteria andCreditInterestNotEqualTo(BigDecimal value) {
            addCriterion("credit_interest <>", value, "creditInterest");
            return (Criteria) this;
        }

        public Criteria andCreditInterestGreaterThan(BigDecimal value) {
            addCriterion("credit_interest >", value, "creditInterest");
            return (Criteria) this;
        }

        public Criteria andCreditInterestGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("credit_interest >=", value, "creditInterest");
            return (Criteria) this;
        }

        public Criteria andCreditInterestLessThan(BigDecimal value) {
            addCriterion("credit_interest <", value, "creditInterest");
            return (Criteria) this;
        }

        public Criteria andCreditInterestLessThanOrEqualTo(BigDecimal value) {
            addCriterion("credit_interest <=", value, "creditInterest");
            return (Criteria) this;
        }

        public Criteria andCreditInterestIn(List<BigDecimal> values) {
            addCriterion("credit_interest in", values, "creditInterest");
            return (Criteria) this;
        }

        public Criteria andCreditInterestNotIn(List<BigDecimal> values) {
            addCriterion("credit_interest not in", values, "creditInterest");
            return (Criteria) this;
        }

        public Criteria andCreditInterestBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("credit_interest between", value1, value2, "creditInterest");
            return (Criteria) this;
        }

        public Criteria andCreditInterestNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("credit_interest not between", value1, value2, "creditInterest");
            return (Criteria) this;
        }

        public Criteria andCreditInterestAdvanceIsNull() {
            addCriterion("credit_interest_advance is null");
            return (Criteria) this;
        }

        public Criteria andCreditInterestAdvanceIsNotNull() {
            addCriterion("credit_interest_advance is not null");
            return (Criteria) this;
        }

        public Criteria andCreditInterestAdvanceEqualTo(BigDecimal value) {
            addCriterion("credit_interest_advance =", value, "creditInterestAdvance");
            return (Criteria) this;
        }

        public Criteria andCreditInterestAdvanceNotEqualTo(BigDecimal value) {
            addCriterion("credit_interest_advance <>", value, "creditInterestAdvance");
            return (Criteria) this;
        }

        public Criteria andCreditInterestAdvanceGreaterThan(BigDecimal value) {
            addCriterion("credit_interest_advance >", value, "creditInterestAdvance");
            return (Criteria) this;
        }

        public Criteria andCreditInterestAdvanceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("credit_interest_advance >=", value, "creditInterestAdvance");
            return (Criteria) this;
        }

        public Criteria andCreditInterestAdvanceLessThan(BigDecimal value) {
            addCriterion("credit_interest_advance <", value, "creditInterestAdvance");
            return (Criteria) this;
        }

        public Criteria andCreditInterestAdvanceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("credit_interest_advance <=", value, "creditInterestAdvance");
            return (Criteria) this;
        }

        public Criteria andCreditInterestAdvanceIn(List<BigDecimal> values) {
            addCriterion("credit_interest_advance in", values, "creditInterestAdvance");
            return (Criteria) this;
        }

        public Criteria andCreditInterestAdvanceNotIn(List<BigDecimal> values) {
            addCriterion("credit_interest_advance not in", values, "creditInterestAdvance");
            return (Criteria) this;
        }

        public Criteria andCreditInterestAdvanceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("credit_interest_advance between", value1, value2, "creditInterestAdvance");
            return (Criteria) this;
        }

        public Criteria andCreditInterestAdvanceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("credit_interest_advance not between", value1, value2, "creditInterestAdvance");
            return (Criteria) this;
        }

        public Criteria andCreditDiscountIsNull() {
            addCriterion("credit_discount is null");
            return (Criteria) this;
        }

        public Criteria andCreditDiscountIsNotNull() {
            addCriterion("credit_discount is not null");
            return (Criteria) this;
        }

        public Criteria andCreditDiscountEqualTo(BigDecimal value) {
            addCriterion("credit_discount =", value, "creditDiscount");
            return (Criteria) this;
        }

        public Criteria andCreditDiscountNotEqualTo(BigDecimal value) {
            addCriterion("credit_discount <>", value, "creditDiscount");
            return (Criteria) this;
        }

        public Criteria andCreditDiscountGreaterThan(BigDecimal value) {
            addCriterion("credit_discount >", value, "creditDiscount");
            return (Criteria) this;
        }

        public Criteria andCreditDiscountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("credit_discount >=", value, "creditDiscount");
            return (Criteria) this;
        }

        public Criteria andCreditDiscountLessThan(BigDecimal value) {
            addCriterion("credit_discount <", value, "creditDiscount");
            return (Criteria) this;
        }

        public Criteria andCreditDiscountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("credit_discount <=", value, "creditDiscount");
            return (Criteria) this;
        }

        public Criteria andCreditDiscountIn(List<BigDecimal> values) {
            addCriterion("credit_discount in", values, "creditDiscount");
            return (Criteria) this;
        }

        public Criteria andCreditDiscountNotIn(List<BigDecimal> values) {
            addCriterion("credit_discount not in", values, "creditDiscount");
            return (Criteria) this;
        }

        public Criteria andCreditDiscountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("credit_discount between", value1, value2, "creditDiscount");
            return (Criteria) this;
        }

        public Criteria andCreditDiscountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("credit_discount not between", value1, value2, "creditDiscount");
            return (Criteria) this;
        }

        public Criteria andCreditIncomeIsNull() {
            addCriterion("credit_income is null");
            return (Criteria) this;
        }

        public Criteria andCreditIncomeIsNotNull() {
            addCriterion("credit_income is not null");
            return (Criteria) this;
        }

        public Criteria andCreditIncomeEqualTo(BigDecimal value) {
            addCriterion("credit_income =", value, "creditIncome");
            return (Criteria) this;
        }

        public Criteria andCreditIncomeNotEqualTo(BigDecimal value) {
            addCriterion("credit_income <>", value, "creditIncome");
            return (Criteria) this;
        }

        public Criteria andCreditIncomeGreaterThan(BigDecimal value) {
            addCriterion("credit_income >", value, "creditIncome");
            return (Criteria) this;
        }

        public Criteria andCreditIncomeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("credit_income >=", value, "creditIncome");
            return (Criteria) this;
        }

        public Criteria andCreditIncomeLessThan(BigDecimal value) {
            addCriterion("credit_income <", value, "creditIncome");
            return (Criteria) this;
        }

        public Criteria andCreditIncomeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("credit_income <=", value, "creditIncome");
            return (Criteria) this;
        }

        public Criteria andCreditIncomeIn(List<BigDecimal> values) {
            addCriterion("credit_income in", values, "creditIncome");
            return (Criteria) this;
        }

        public Criteria andCreditIncomeNotIn(List<BigDecimal> values) {
            addCriterion("credit_income not in", values, "creditIncome");
            return (Criteria) this;
        }

        public Criteria andCreditIncomeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("credit_income between", value1, value2, "creditIncome");
            return (Criteria) this;
        }

        public Criteria andCreditIncomeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("credit_income not between", value1, value2, "creditIncome");
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

        public Criteria andCreditPriceIsNull() {
            addCriterion("credit_price is null");
            return (Criteria) this;
        }

        public Criteria andCreditPriceIsNotNull() {
            addCriterion("credit_price is not null");
            return (Criteria) this;
        }

        public Criteria andCreditPriceEqualTo(BigDecimal value) {
            addCriterion("credit_price =", value, "creditPrice");
            return (Criteria) this;
        }

        public Criteria andCreditPriceNotEqualTo(BigDecimal value) {
            addCriterion("credit_price <>", value, "creditPrice");
            return (Criteria) this;
        }

        public Criteria andCreditPriceGreaterThan(BigDecimal value) {
            addCriterion("credit_price >", value, "creditPrice");
            return (Criteria) this;
        }

        public Criteria andCreditPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("credit_price >=", value, "creditPrice");
            return (Criteria) this;
        }

        public Criteria andCreditPriceLessThan(BigDecimal value) {
            addCriterion("credit_price <", value, "creditPrice");
            return (Criteria) this;
        }

        public Criteria andCreditPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("credit_price <=", value, "creditPrice");
            return (Criteria) this;
        }

        public Criteria andCreditPriceIn(List<BigDecimal> values) {
            addCriterion("credit_price in", values, "creditPrice");
            return (Criteria) this;
        }

        public Criteria andCreditPriceNotIn(List<BigDecimal> values) {
            addCriterion("credit_price not in", values, "creditPrice");
            return (Criteria) this;
        }

        public Criteria andCreditPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("credit_price between", value1, value2, "creditPrice");
            return (Criteria) this;
        }

        public Criteria andCreditPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("credit_price not between", value1, value2, "creditPrice");
            return (Criteria) this;
        }

        public Criteria andCreditCapitalAssignedIsNull() {
            addCriterion("credit_capital_assigned is null");
            return (Criteria) this;
        }

        public Criteria andCreditCapitalAssignedIsNotNull() {
            addCriterion("credit_capital_assigned is not null");
            return (Criteria) this;
        }

        public Criteria andCreditCapitalAssignedEqualTo(BigDecimal value) {
            addCriterion("credit_capital_assigned =", value, "creditCapitalAssigned");
            return (Criteria) this;
        }

        public Criteria andCreditCapitalAssignedNotEqualTo(BigDecimal value) {
            addCriterion("credit_capital_assigned <>", value, "creditCapitalAssigned");
            return (Criteria) this;
        }

        public Criteria andCreditCapitalAssignedGreaterThan(BigDecimal value) {
            addCriterion("credit_capital_assigned >", value, "creditCapitalAssigned");
            return (Criteria) this;
        }

        public Criteria andCreditCapitalAssignedGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("credit_capital_assigned >=", value, "creditCapitalAssigned");
            return (Criteria) this;
        }

        public Criteria andCreditCapitalAssignedLessThan(BigDecimal value) {
            addCriterion("credit_capital_assigned <", value, "creditCapitalAssigned");
            return (Criteria) this;
        }

        public Criteria andCreditCapitalAssignedLessThanOrEqualTo(BigDecimal value) {
            addCriterion("credit_capital_assigned <=", value, "creditCapitalAssigned");
            return (Criteria) this;
        }

        public Criteria andCreditCapitalAssignedIn(List<BigDecimal> values) {
            addCriterion("credit_capital_assigned in", values, "creditCapitalAssigned");
            return (Criteria) this;
        }

        public Criteria andCreditCapitalAssignedNotIn(List<BigDecimal> values) {
            addCriterion("credit_capital_assigned not in", values, "creditCapitalAssigned");
            return (Criteria) this;
        }

        public Criteria andCreditCapitalAssignedBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("credit_capital_assigned between", value1, value2, "creditCapitalAssigned");
            return (Criteria) this;
        }

        public Criteria andCreditCapitalAssignedNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("credit_capital_assigned not between", value1, value2, "creditCapitalAssigned");
            return (Criteria) this;
        }

        public Criteria andCreditInterestAssignedIsNull() {
            addCriterion("credit_interest_assigned is null");
            return (Criteria) this;
        }

        public Criteria andCreditInterestAssignedIsNotNull() {
            addCriterion("credit_interest_assigned is not null");
            return (Criteria) this;
        }

        public Criteria andCreditInterestAssignedEqualTo(BigDecimal value) {
            addCriterion("credit_interest_assigned =", value, "creditInterestAssigned");
            return (Criteria) this;
        }

        public Criteria andCreditInterestAssignedNotEqualTo(BigDecimal value) {
            addCriterion("credit_interest_assigned <>", value, "creditInterestAssigned");
            return (Criteria) this;
        }

        public Criteria andCreditInterestAssignedGreaterThan(BigDecimal value) {
            addCriterion("credit_interest_assigned >", value, "creditInterestAssigned");
            return (Criteria) this;
        }

        public Criteria andCreditInterestAssignedGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("credit_interest_assigned >=", value, "creditInterestAssigned");
            return (Criteria) this;
        }

        public Criteria andCreditInterestAssignedLessThan(BigDecimal value) {
            addCriterion("credit_interest_assigned <", value, "creditInterestAssigned");
            return (Criteria) this;
        }

        public Criteria andCreditInterestAssignedLessThanOrEqualTo(BigDecimal value) {
            addCriterion("credit_interest_assigned <=", value, "creditInterestAssigned");
            return (Criteria) this;
        }

        public Criteria andCreditInterestAssignedIn(List<BigDecimal> values) {
            addCriterion("credit_interest_assigned in", values, "creditInterestAssigned");
            return (Criteria) this;
        }

        public Criteria andCreditInterestAssignedNotIn(List<BigDecimal> values) {
            addCriterion("credit_interest_assigned not in", values, "creditInterestAssigned");
            return (Criteria) this;
        }

        public Criteria andCreditInterestAssignedBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("credit_interest_assigned between", value1, value2, "creditInterestAssigned");
            return (Criteria) this;
        }

        public Criteria andCreditInterestAssignedNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("credit_interest_assigned not between", value1, value2, "creditInterestAssigned");
            return (Criteria) this;
        }

        public Criteria andCreditInterestAdvanceAssignedIsNull() {
            addCriterion("credit_interest_advance_assigned is null");
            return (Criteria) this;
        }

        public Criteria andCreditInterestAdvanceAssignedIsNotNull() {
            addCriterion("credit_interest_advance_assigned is not null");
            return (Criteria) this;
        }

        public Criteria andCreditInterestAdvanceAssignedEqualTo(BigDecimal value) {
            addCriterion("credit_interest_advance_assigned =", value, "creditInterestAdvanceAssigned");
            return (Criteria) this;
        }

        public Criteria andCreditInterestAdvanceAssignedNotEqualTo(BigDecimal value) {
            addCriterion("credit_interest_advance_assigned <>", value, "creditInterestAdvanceAssigned");
            return (Criteria) this;
        }

        public Criteria andCreditInterestAdvanceAssignedGreaterThan(BigDecimal value) {
            addCriterion("credit_interest_advance_assigned >", value, "creditInterestAdvanceAssigned");
            return (Criteria) this;
        }

        public Criteria andCreditInterestAdvanceAssignedGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("credit_interest_advance_assigned >=", value, "creditInterestAdvanceAssigned");
            return (Criteria) this;
        }

        public Criteria andCreditInterestAdvanceAssignedLessThan(BigDecimal value) {
            addCriterion("credit_interest_advance_assigned <", value, "creditInterestAdvanceAssigned");
            return (Criteria) this;
        }

        public Criteria andCreditInterestAdvanceAssignedLessThanOrEqualTo(BigDecimal value) {
            addCriterion("credit_interest_advance_assigned <=", value, "creditInterestAdvanceAssigned");
            return (Criteria) this;
        }

        public Criteria andCreditInterestAdvanceAssignedIn(List<BigDecimal> values) {
            addCriterion("credit_interest_advance_assigned in", values, "creditInterestAdvanceAssigned");
            return (Criteria) this;
        }

        public Criteria andCreditInterestAdvanceAssignedNotIn(List<BigDecimal> values) {
            addCriterion("credit_interest_advance_assigned not in", values, "creditInterestAdvanceAssigned");
            return (Criteria) this;
        }

        public Criteria andCreditInterestAdvanceAssignedBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("credit_interest_advance_assigned between", value1, value2, "creditInterestAdvanceAssigned");
            return (Criteria) this;
        }

        public Criteria andCreditInterestAdvanceAssignedNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("credit_interest_advance_assigned not between", value1, value2, "creditInterestAdvanceAssigned");
            return (Criteria) this;
        }

        public Criteria andCreditRepayAccountIsNull() {
            addCriterion("credit_repay_account is null");
            return (Criteria) this;
        }

        public Criteria andCreditRepayAccountIsNotNull() {
            addCriterion("credit_repay_account is not null");
            return (Criteria) this;
        }

        public Criteria andCreditRepayAccountEqualTo(BigDecimal value) {
            addCriterion("credit_repay_account =", value, "creditRepayAccount");
            return (Criteria) this;
        }

        public Criteria andCreditRepayAccountNotEqualTo(BigDecimal value) {
            addCriterion("credit_repay_account <>", value, "creditRepayAccount");
            return (Criteria) this;
        }

        public Criteria andCreditRepayAccountGreaterThan(BigDecimal value) {
            addCriterion("credit_repay_account >", value, "creditRepayAccount");
            return (Criteria) this;
        }

        public Criteria andCreditRepayAccountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("credit_repay_account >=", value, "creditRepayAccount");
            return (Criteria) this;
        }

        public Criteria andCreditRepayAccountLessThan(BigDecimal value) {
            addCriterion("credit_repay_account <", value, "creditRepayAccount");
            return (Criteria) this;
        }

        public Criteria andCreditRepayAccountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("credit_repay_account <=", value, "creditRepayAccount");
            return (Criteria) this;
        }

        public Criteria andCreditRepayAccountIn(List<BigDecimal> values) {
            addCriterion("credit_repay_account in", values, "creditRepayAccount");
            return (Criteria) this;
        }

        public Criteria andCreditRepayAccountNotIn(List<BigDecimal> values) {
            addCriterion("credit_repay_account not in", values, "creditRepayAccount");
            return (Criteria) this;
        }

        public Criteria andCreditRepayAccountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("credit_repay_account between", value1, value2, "creditRepayAccount");
            return (Criteria) this;
        }

        public Criteria andCreditRepayAccountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("credit_repay_account not between", value1, value2, "creditRepayAccount");
            return (Criteria) this;
        }

        public Criteria andCreditRepayCapitalIsNull() {
            addCriterion("credit_repay_capital is null");
            return (Criteria) this;
        }

        public Criteria andCreditRepayCapitalIsNotNull() {
            addCriterion("credit_repay_capital is not null");
            return (Criteria) this;
        }

        public Criteria andCreditRepayCapitalEqualTo(BigDecimal value) {
            addCriterion("credit_repay_capital =", value, "creditRepayCapital");
            return (Criteria) this;
        }

        public Criteria andCreditRepayCapitalNotEqualTo(BigDecimal value) {
            addCriterion("credit_repay_capital <>", value, "creditRepayCapital");
            return (Criteria) this;
        }

        public Criteria andCreditRepayCapitalGreaterThan(BigDecimal value) {
            addCriterion("credit_repay_capital >", value, "creditRepayCapital");
            return (Criteria) this;
        }

        public Criteria andCreditRepayCapitalGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("credit_repay_capital >=", value, "creditRepayCapital");
            return (Criteria) this;
        }

        public Criteria andCreditRepayCapitalLessThan(BigDecimal value) {
            addCriterion("credit_repay_capital <", value, "creditRepayCapital");
            return (Criteria) this;
        }

        public Criteria andCreditRepayCapitalLessThanOrEqualTo(BigDecimal value) {
            addCriterion("credit_repay_capital <=", value, "creditRepayCapital");
            return (Criteria) this;
        }

        public Criteria andCreditRepayCapitalIn(List<BigDecimal> values) {
            addCriterion("credit_repay_capital in", values, "creditRepayCapital");
            return (Criteria) this;
        }

        public Criteria andCreditRepayCapitalNotIn(List<BigDecimal> values) {
            addCriterion("credit_repay_capital not in", values, "creditRepayCapital");
            return (Criteria) this;
        }

        public Criteria andCreditRepayCapitalBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("credit_repay_capital between", value1, value2, "creditRepayCapital");
            return (Criteria) this;
        }

        public Criteria andCreditRepayCapitalNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("credit_repay_capital not between", value1, value2, "creditRepayCapital");
            return (Criteria) this;
        }

        public Criteria andCreditRepayInterestIsNull() {
            addCriterion("credit_repay_interest is null");
            return (Criteria) this;
        }

        public Criteria andCreditRepayInterestIsNotNull() {
            addCriterion("credit_repay_interest is not null");
            return (Criteria) this;
        }

        public Criteria andCreditRepayInterestEqualTo(BigDecimal value) {
            addCriterion("credit_repay_interest =", value, "creditRepayInterest");
            return (Criteria) this;
        }

        public Criteria andCreditRepayInterestNotEqualTo(BigDecimal value) {
            addCriterion("credit_repay_interest <>", value, "creditRepayInterest");
            return (Criteria) this;
        }

        public Criteria andCreditRepayInterestGreaterThan(BigDecimal value) {
            addCriterion("credit_repay_interest >", value, "creditRepayInterest");
            return (Criteria) this;
        }

        public Criteria andCreditRepayInterestGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("credit_repay_interest >=", value, "creditRepayInterest");
            return (Criteria) this;
        }

        public Criteria andCreditRepayInterestLessThan(BigDecimal value) {
            addCriterion("credit_repay_interest <", value, "creditRepayInterest");
            return (Criteria) this;
        }

        public Criteria andCreditRepayInterestLessThanOrEqualTo(BigDecimal value) {
            addCriterion("credit_repay_interest <=", value, "creditRepayInterest");
            return (Criteria) this;
        }

        public Criteria andCreditRepayInterestIn(List<BigDecimal> values) {
            addCriterion("credit_repay_interest in", values, "creditRepayInterest");
            return (Criteria) this;
        }

        public Criteria andCreditRepayInterestNotIn(List<BigDecimal> values) {
            addCriterion("credit_repay_interest not in", values, "creditRepayInterest");
            return (Criteria) this;
        }

        public Criteria andCreditRepayInterestBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("credit_repay_interest between", value1, value2, "creditRepayInterest");
            return (Criteria) this;
        }

        public Criteria andCreditRepayInterestNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("credit_repay_interest not between", value1, value2, "creditRepayInterest");
            return (Criteria) this;
        }

        public Criteria andCreditRepayEndTimeIsNull() {
            addCriterion("credit_repay_end_time is null");
            return (Criteria) this;
        }

        public Criteria andCreditRepayEndTimeIsNotNull() {
            addCriterion("credit_repay_end_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreditRepayEndTimeEqualTo(Integer value) {
            addCriterion("credit_repay_end_time =", value, "creditRepayEndTime");
            return (Criteria) this;
        }

        public Criteria andCreditRepayEndTimeNotEqualTo(Integer value) {
            addCriterion("credit_repay_end_time <>", value, "creditRepayEndTime");
            return (Criteria) this;
        }

        public Criteria andCreditRepayEndTimeGreaterThan(Integer value) {
            addCriterion("credit_repay_end_time >", value, "creditRepayEndTime");
            return (Criteria) this;
        }

        public Criteria andCreditRepayEndTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("credit_repay_end_time >=", value, "creditRepayEndTime");
            return (Criteria) this;
        }

        public Criteria andCreditRepayEndTimeLessThan(Integer value) {
            addCriterion("credit_repay_end_time <", value, "creditRepayEndTime");
            return (Criteria) this;
        }

        public Criteria andCreditRepayEndTimeLessThanOrEqualTo(Integer value) {
            addCriterion("credit_repay_end_time <=", value, "creditRepayEndTime");
            return (Criteria) this;
        }

        public Criteria andCreditRepayEndTimeIn(List<Integer> values) {
            addCriterion("credit_repay_end_time in", values, "creditRepayEndTime");
            return (Criteria) this;
        }

        public Criteria andCreditRepayEndTimeNotIn(List<Integer> values) {
            addCriterion("credit_repay_end_time not in", values, "creditRepayEndTime");
            return (Criteria) this;
        }

        public Criteria andCreditRepayEndTimeBetween(Integer value1, Integer value2) {
            addCriterion("credit_repay_end_time between", value1, value2, "creditRepayEndTime");
            return (Criteria) this;
        }

        public Criteria andCreditRepayEndTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("credit_repay_end_time not between", value1, value2, "creditRepayEndTime");
            return (Criteria) this;
        }

        public Criteria andCreditRepayLastTimeIsNull() {
            addCriterion("credit_repay_last_time is null");
            return (Criteria) this;
        }

        public Criteria andCreditRepayLastTimeIsNotNull() {
            addCriterion("credit_repay_last_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreditRepayLastTimeEqualTo(Integer value) {
            addCriterion("credit_repay_last_time =", value, "creditRepayLastTime");
            return (Criteria) this;
        }

        public Criteria andCreditRepayLastTimeNotEqualTo(Integer value) {
            addCriterion("credit_repay_last_time <>", value, "creditRepayLastTime");
            return (Criteria) this;
        }

        public Criteria andCreditRepayLastTimeGreaterThan(Integer value) {
            addCriterion("credit_repay_last_time >", value, "creditRepayLastTime");
            return (Criteria) this;
        }

        public Criteria andCreditRepayLastTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("credit_repay_last_time >=", value, "creditRepayLastTime");
            return (Criteria) this;
        }

        public Criteria andCreditRepayLastTimeLessThan(Integer value) {
            addCriterion("credit_repay_last_time <", value, "creditRepayLastTime");
            return (Criteria) this;
        }

        public Criteria andCreditRepayLastTimeLessThanOrEqualTo(Integer value) {
            addCriterion("credit_repay_last_time <=", value, "creditRepayLastTime");
            return (Criteria) this;
        }

        public Criteria andCreditRepayLastTimeIn(List<Integer> values) {
            addCriterion("credit_repay_last_time in", values, "creditRepayLastTime");
            return (Criteria) this;
        }

        public Criteria andCreditRepayLastTimeNotIn(List<Integer> values) {
            addCriterion("credit_repay_last_time not in", values, "creditRepayLastTime");
            return (Criteria) this;
        }

        public Criteria andCreditRepayLastTimeBetween(Integer value1, Integer value2) {
            addCriterion("credit_repay_last_time between", value1, value2, "creditRepayLastTime");
            return (Criteria) this;
        }

        public Criteria andCreditRepayLastTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("credit_repay_last_time not between", value1, value2, "creditRepayLastTime");
            return (Criteria) this;
        }

        public Criteria andCreditRepayNextTimeIsNull() {
            addCriterion("credit_repay_next_time is null");
            return (Criteria) this;
        }

        public Criteria andCreditRepayNextTimeIsNotNull() {
            addCriterion("credit_repay_next_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreditRepayNextTimeEqualTo(Integer value) {
            addCriterion("credit_repay_next_time =", value, "creditRepayNextTime");
            return (Criteria) this;
        }

        public Criteria andCreditRepayNextTimeNotEqualTo(Integer value) {
            addCriterion("credit_repay_next_time <>", value, "creditRepayNextTime");
            return (Criteria) this;
        }

        public Criteria andCreditRepayNextTimeGreaterThan(Integer value) {
            addCriterion("credit_repay_next_time >", value, "creditRepayNextTime");
            return (Criteria) this;
        }

        public Criteria andCreditRepayNextTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("credit_repay_next_time >=", value, "creditRepayNextTime");
            return (Criteria) this;
        }

        public Criteria andCreditRepayNextTimeLessThan(Integer value) {
            addCriterion("credit_repay_next_time <", value, "creditRepayNextTime");
            return (Criteria) this;
        }

        public Criteria andCreditRepayNextTimeLessThanOrEqualTo(Integer value) {
            addCriterion("credit_repay_next_time <=", value, "creditRepayNextTime");
            return (Criteria) this;
        }

        public Criteria andCreditRepayNextTimeIn(List<Integer> values) {
            addCriterion("credit_repay_next_time in", values, "creditRepayNextTime");
            return (Criteria) this;
        }

        public Criteria andCreditRepayNextTimeNotIn(List<Integer> values) {
            addCriterion("credit_repay_next_time not in", values, "creditRepayNextTime");
            return (Criteria) this;
        }

        public Criteria andCreditRepayNextTimeBetween(Integer value1, Integer value2) {
            addCriterion("credit_repay_next_time between", value1, value2, "creditRepayNextTime");
            return (Criteria) this;
        }

        public Criteria andCreditRepayNextTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("credit_repay_next_time not between", value1, value2, "creditRepayNextTime");
            return (Criteria) this;
        }

        public Criteria andCreditRepayYesTimeIsNull() {
            addCriterion("credit_repay_yes_time is null");
            return (Criteria) this;
        }

        public Criteria andCreditRepayYesTimeIsNotNull() {
            addCriterion("credit_repay_yes_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreditRepayYesTimeEqualTo(Integer value) {
            addCriterion("credit_repay_yes_time =", value, "creditRepayYesTime");
            return (Criteria) this;
        }

        public Criteria andCreditRepayYesTimeNotEqualTo(Integer value) {
            addCriterion("credit_repay_yes_time <>", value, "creditRepayYesTime");
            return (Criteria) this;
        }

        public Criteria andCreditRepayYesTimeGreaterThan(Integer value) {
            addCriterion("credit_repay_yes_time >", value, "creditRepayYesTime");
            return (Criteria) this;
        }

        public Criteria andCreditRepayYesTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("credit_repay_yes_time >=", value, "creditRepayYesTime");
            return (Criteria) this;
        }

        public Criteria andCreditRepayYesTimeLessThan(Integer value) {
            addCriterion("credit_repay_yes_time <", value, "creditRepayYesTime");
            return (Criteria) this;
        }

        public Criteria andCreditRepayYesTimeLessThanOrEqualTo(Integer value) {
            addCriterion("credit_repay_yes_time <=", value, "creditRepayYesTime");
            return (Criteria) this;
        }

        public Criteria andCreditRepayYesTimeIn(List<Integer> values) {
            addCriterion("credit_repay_yes_time in", values, "creditRepayYesTime");
            return (Criteria) this;
        }

        public Criteria andCreditRepayYesTimeNotIn(List<Integer> values) {
            addCriterion("credit_repay_yes_time not in", values, "creditRepayYesTime");
            return (Criteria) this;
        }

        public Criteria andCreditRepayYesTimeBetween(Integer value1, Integer value2) {
            addCriterion("credit_repay_yes_time between", value1, value2, "creditRepayYesTime");
            return (Criteria) this;
        }

        public Criteria andCreditRepayYesTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("credit_repay_yes_time not between", value1, value2, "creditRepayYesTime");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNull() {
            addCriterion("create_date is null");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNotNull() {
            addCriterion("create_date is not null");
            return (Criteria) this;
        }

        public Criteria andCreateDateEqualTo(Integer value) {
            addCriterion("create_date =", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotEqualTo(Integer value) {
            addCriterion("create_date <>", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThan(Integer value) {
            addCriterion("create_date >", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThanOrEqualTo(Integer value) {
            addCriterion("create_date >=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThan(Integer value) {
            addCriterion("create_date <", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThanOrEqualTo(Integer value) {
            addCriterion("create_date <=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateIn(List<Integer> values) {
            addCriterion("create_date in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotIn(List<Integer> values) {
            addCriterion("create_date not in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateBetween(Integer value1, Integer value2) {
            addCriterion("create_date between", value1, value2, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotBetween(Integer value1, Integer value2) {
            addCriterion("create_date not between", value1, value2, "createDate");
            return (Criteria) this;
        }

        public Criteria andAddTimeIsNull() {
            addCriterion("add_time is null");
            return (Criteria) this;
        }

        public Criteria andAddTimeIsNotNull() {
            addCriterion("add_time is not null");
            return (Criteria) this;
        }

        public Criteria andAddTimeEqualTo(Integer value) {
            addCriterion("add_time =", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeNotEqualTo(Integer value) {
            addCriterion("add_time <>", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeGreaterThan(Integer value) {
            addCriterion("add_time >", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("add_time >=", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeLessThan(Integer value) {
            addCriterion("add_time <", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeLessThanOrEqualTo(Integer value) {
            addCriterion("add_time <=", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeIn(List<Integer> values) {
            addCriterion("add_time in", values, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeNotIn(List<Integer> values) {
            addCriterion("add_time not in", values, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeBetween(Integer value1, Integer value2) {
            addCriterion("add_time between", value1, value2, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("add_time not between", value1, value2, "addTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNull() {
            addCriterion("end_time is null");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNotNull() {
            addCriterion("end_time is not null");
            return (Criteria) this;
        }

        public Criteria andEndTimeEqualTo(Integer value) {
            addCriterion("end_time =", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotEqualTo(Integer value) {
            addCriterion("end_time <>", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThan(Integer value) {
            addCriterion("end_time >", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("end_time >=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThan(Integer value) {
            addCriterion("end_time <", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThanOrEqualTo(Integer value) {
            addCriterion("end_time <=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIn(List<Integer> values) {
            addCriterion("end_time in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotIn(List<Integer> values) {
            addCriterion("end_time not in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeBetween(Integer value1, Integer value2) {
            addCriterion("end_time between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("end_time not between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andAssignTimeIsNull() {
            addCriterion("assign_time is null");
            return (Criteria) this;
        }

        public Criteria andAssignTimeIsNotNull() {
            addCriterion("assign_time is not null");
            return (Criteria) this;
        }

        public Criteria andAssignTimeEqualTo(Integer value) {
            addCriterion("assign_time =", value, "assignTime");
            return (Criteria) this;
        }

        public Criteria andAssignTimeNotEqualTo(Integer value) {
            addCriterion("assign_time <>", value, "assignTime");
            return (Criteria) this;
        }

        public Criteria andAssignTimeGreaterThan(Integer value) {
            addCriterion("assign_time >", value, "assignTime");
            return (Criteria) this;
        }

        public Criteria andAssignTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("assign_time >=", value, "assignTime");
            return (Criteria) this;
        }

        public Criteria andAssignTimeLessThan(Integer value) {
            addCriterion("assign_time <", value, "assignTime");
            return (Criteria) this;
        }

        public Criteria andAssignTimeLessThanOrEqualTo(Integer value) {
            addCriterion("assign_time <=", value, "assignTime");
            return (Criteria) this;
        }

        public Criteria andAssignTimeIn(List<Integer> values) {
            addCriterion("assign_time in", values, "assignTime");
            return (Criteria) this;
        }

        public Criteria andAssignTimeNotIn(List<Integer> values) {
            addCriterion("assign_time not in", values, "assignTime");
            return (Criteria) this;
        }

        public Criteria andAssignTimeBetween(Integer value1, Integer value2) {
            addCriterion("assign_time between", value1, value2, "assignTime");
            return (Criteria) this;
        }

        public Criteria andAssignTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("assign_time not between", value1, value2, "assignTime");
            return (Criteria) this;
        }

        public Criteria andAssignNumIsNull() {
            addCriterion("assign_num is null");
            return (Criteria) this;
        }

        public Criteria andAssignNumIsNotNull() {
            addCriterion("assign_num is not null");
            return (Criteria) this;
        }

        public Criteria andAssignNumEqualTo(Integer value) {
            addCriterion("assign_num =", value, "assignNum");
            return (Criteria) this;
        }

        public Criteria andAssignNumNotEqualTo(Integer value) {
            addCriterion("assign_num <>", value, "assignNum");
            return (Criteria) this;
        }

        public Criteria andAssignNumGreaterThan(Integer value) {
            addCriterion("assign_num >", value, "assignNum");
            return (Criteria) this;
        }

        public Criteria andAssignNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("assign_num >=", value, "assignNum");
            return (Criteria) this;
        }

        public Criteria andAssignNumLessThan(Integer value) {
            addCriterion("assign_num <", value, "assignNum");
            return (Criteria) this;
        }

        public Criteria andAssignNumLessThanOrEqualTo(Integer value) {
            addCriterion("assign_num <=", value, "assignNum");
            return (Criteria) this;
        }

        public Criteria andAssignNumIn(List<Integer> values) {
            addCriterion("assign_num in", values, "assignNum");
            return (Criteria) this;
        }

        public Criteria andAssignNumNotIn(List<Integer> values) {
            addCriterion("assign_num not in", values, "assignNum");
            return (Criteria) this;
        }

        public Criteria andAssignNumBetween(Integer value1, Integer value2) {
            addCriterion("assign_num between", value1, value2, "assignNum");
            return (Criteria) this;
        }

        public Criteria andAssignNumNotBetween(Integer value1, Integer value2) {
            addCriterion("assign_num not between", value1, value2, "assignNum");
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