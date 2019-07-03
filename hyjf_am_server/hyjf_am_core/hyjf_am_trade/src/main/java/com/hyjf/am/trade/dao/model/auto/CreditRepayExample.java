package com.hyjf.am.trade.dao.model.auto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CreditRepayExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public CreditRepayExample() {
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

        public Criteria andChargeDaysIsNull() {
            addCriterion("charge_days is null");
            return (Criteria) this;
        }

        public Criteria andChargeDaysIsNotNull() {
            addCriterion("charge_days is not null");
            return (Criteria) this;
        }

        public Criteria andChargeDaysEqualTo(Integer value) {
            addCriterion("charge_days =", value, "chargeDays");
            return (Criteria) this;
        }

        public Criteria andChargeDaysNotEqualTo(Integer value) {
            addCriterion("charge_days <>", value, "chargeDays");
            return (Criteria) this;
        }

        public Criteria andChargeDaysGreaterThan(Integer value) {
            addCriterion("charge_days >", value, "chargeDays");
            return (Criteria) this;
        }

        public Criteria andChargeDaysGreaterThanOrEqualTo(Integer value) {
            addCriterion("charge_days >=", value, "chargeDays");
            return (Criteria) this;
        }

        public Criteria andChargeDaysLessThan(Integer value) {
            addCriterion("charge_days <", value, "chargeDays");
            return (Criteria) this;
        }

        public Criteria andChargeDaysLessThanOrEqualTo(Integer value) {
            addCriterion("charge_days <=", value, "chargeDays");
            return (Criteria) this;
        }

        public Criteria andChargeDaysIn(List<Integer> values) {
            addCriterion("charge_days in", values, "chargeDays");
            return (Criteria) this;
        }

        public Criteria andChargeDaysNotIn(List<Integer> values) {
            addCriterion("charge_days not in", values, "chargeDays");
            return (Criteria) this;
        }

        public Criteria andChargeDaysBetween(Integer value1, Integer value2) {
            addCriterion("charge_days between", value1, value2, "chargeDays");
            return (Criteria) this;
        }

        public Criteria andChargeDaysNotBetween(Integer value1, Integer value2) {
            addCriterion("charge_days not between", value1, value2, "chargeDays");
            return (Criteria) this;
        }

        public Criteria andChargeInterestIsNull() {
            addCriterion("charge_interest is null");
            return (Criteria) this;
        }

        public Criteria andChargeInterestIsNotNull() {
            addCriterion("charge_interest is not null");
            return (Criteria) this;
        }

        public Criteria andChargeInterestEqualTo(BigDecimal value) {
            addCriterion("charge_interest =", value, "chargeInterest");
            return (Criteria) this;
        }

        public Criteria andChargeInterestNotEqualTo(BigDecimal value) {
            addCriterion("charge_interest <>", value, "chargeInterest");
            return (Criteria) this;
        }

        public Criteria andChargeInterestGreaterThan(BigDecimal value) {
            addCriterion("charge_interest >", value, "chargeInterest");
            return (Criteria) this;
        }

        public Criteria andChargeInterestGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("charge_interest >=", value, "chargeInterest");
            return (Criteria) this;
        }

        public Criteria andChargeInterestLessThan(BigDecimal value) {
            addCriterion("charge_interest <", value, "chargeInterest");
            return (Criteria) this;
        }

        public Criteria andChargeInterestLessThanOrEqualTo(BigDecimal value) {
            addCriterion("charge_interest <=", value, "chargeInterest");
            return (Criteria) this;
        }

        public Criteria andChargeInterestIn(List<BigDecimal> values) {
            addCriterion("charge_interest in", values, "chargeInterest");
            return (Criteria) this;
        }

        public Criteria andChargeInterestNotIn(List<BigDecimal> values) {
            addCriterion("charge_interest not in", values, "chargeInterest");
            return (Criteria) this;
        }

        public Criteria andChargeInterestBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("charge_interest between", value1, value2, "chargeInterest");
            return (Criteria) this;
        }

        public Criteria andChargeInterestNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("charge_interest not between", value1, value2, "chargeInterest");
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

        public Criteria andChargePenaltyInterestIsNull() {
            addCriterion("charge_penalty_interest is null");
            return (Criteria) this;
        }

        public Criteria andChargePenaltyInterestIsNotNull() {
            addCriterion("charge_penalty_interest is not null");
            return (Criteria) this;
        }

        public Criteria andChargePenaltyInterestEqualTo(BigDecimal value) {
            addCriterion("charge_penalty_interest =", value, "chargePenaltyInterest");
            return (Criteria) this;
        }

        public Criteria andChargePenaltyInterestNotEqualTo(BigDecimal value) {
            addCriterion("charge_penalty_interest <>", value, "chargePenaltyInterest");
            return (Criteria) this;
        }

        public Criteria andChargePenaltyInterestGreaterThan(BigDecimal value) {
            addCriterion("charge_penalty_interest >", value, "chargePenaltyInterest");
            return (Criteria) this;
        }

        public Criteria andChargePenaltyInterestGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("charge_penalty_interest >=", value, "chargePenaltyInterest");
            return (Criteria) this;
        }

        public Criteria andChargePenaltyInterestLessThan(BigDecimal value) {
            addCriterion("charge_penalty_interest <", value, "chargePenaltyInterest");
            return (Criteria) this;
        }

        public Criteria andChargePenaltyInterestLessThanOrEqualTo(BigDecimal value) {
            addCriterion("charge_penalty_interest <=", value, "chargePenaltyInterest");
            return (Criteria) this;
        }

        public Criteria andChargePenaltyInterestIn(List<BigDecimal> values) {
            addCriterion("charge_penalty_interest in", values, "chargePenaltyInterest");
            return (Criteria) this;
        }

        public Criteria andChargePenaltyInterestNotIn(List<BigDecimal> values) {
            addCriterion("charge_penalty_interest not in", values, "chargePenaltyInterest");
            return (Criteria) this;
        }

        public Criteria andChargePenaltyInterestBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("charge_penalty_interest between", value1, value2, "chargePenaltyInterest");
            return (Criteria) this;
        }

        public Criteria andChargePenaltyInterestNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("charge_penalty_interest not between", value1, value2, "chargePenaltyInterest");
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