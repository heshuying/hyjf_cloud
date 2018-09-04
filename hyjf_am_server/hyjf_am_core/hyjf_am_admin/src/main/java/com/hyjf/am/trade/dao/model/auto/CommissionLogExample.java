package com.hyjf.am.trade.dao.model.auto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommissionLogExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public CommissionLogExample() {
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

        public Criteria andSpreadsUserIdIsNull() {
            addCriterion("spreads_user_id is null");
            return (Criteria) this;
        }

        public Criteria andSpreadsUserIdIsNotNull() {
            addCriterion("spreads_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andSpreadsUserIdEqualTo(Integer value) {
            addCriterion("spreads_user_id =", value, "spreadsUserId");
            return (Criteria) this;
        }

        public Criteria andSpreadsUserIdNotEqualTo(Integer value) {
            addCriterion("spreads_user_id <>", value, "spreadsUserId");
            return (Criteria) this;
        }

        public Criteria andSpreadsUserIdGreaterThan(Integer value) {
            addCriterion("spreads_user_id >", value, "spreadsUserId");
            return (Criteria) this;
        }

        public Criteria andSpreadsUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("spreads_user_id >=", value, "spreadsUserId");
            return (Criteria) this;
        }

        public Criteria andSpreadsUserIdLessThan(Integer value) {
            addCriterion("spreads_user_id <", value, "spreadsUserId");
            return (Criteria) this;
        }

        public Criteria andSpreadsUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("spreads_user_id <=", value, "spreadsUserId");
            return (Criteria) this;
        }

        public Criteria andSpreadsUserIdIn(List<Integer> values) {
            addCriterion("spreads_user_id in", values, "spreadsUserId");
            return (Criteria) this;
        }

        public Criteria andSpreadsUserIdNotIn(List<Integer> values) {
            addCriterion("spreads_user_id not in", values, "spreadsUserId");
            return (Criteria) this;
        }

        public Criteria andSpreadsUserIdBetween(Integer value1, Integer value2) {
            addCriterion("spreads_user_id between", value1, value2, "spreadsUserId");
            return (Criteria) this;
        }

        public Criteria andSpreadsUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("spreads_user_id not between", value1, value2, "spreadsUserId");
            return (Criteria) this;
        }

        public Criteria andNidIsNull() {
            addCriterion("nid is null");
            return (Criteria) this;
        }

        public Criteria andNidIsNotNull() {
            addCriterion("nid is not null");
            return (Criteria) this;
        }

        public Criteria andNidEqualTo(String value) {
            addCriterion("nid =", value, "nid");
            return (Criteria) this;
        }

        public Criteria andNidNotEqualTo(String value) {
            addCriterion("nid <>", value, "nid");
            return (Criteria) this;
        }

        public Criteria andNidGreaterThan(String value) {
            addCriterion("nid >", value, "nid");
            return (Criteria) this;
        }

        public Criteria andNidGreaterThanOrEqualTo(String value) {
            addCriterion("nid >=", value, "nid");
            return (Criteria) this;
        }

        public Criteria andNidLessThan(String value) {
            addCriterion("nid <", value, "nid");
            return (Criteria) this;
        }

        public Criteria andNidLessThanOrEqualTo(String value) {
            addCriterion("nid <=", value, "nid");
            return (Criteria) this;
        }

        public Criteria andNidLike(String value) {
            addCriterion("nid like", value, "nid");
            return (Criteria) this;
        }

        public Criteria andNidNotLike(String value) {
            addCriterion("nid not like", value, "nid");
            return (Criteria) this;
        }

        public Criteria andNidIn(List<String> values) {
            addCriterion("nid in", values, "nid");
            return (Criteria) this;
        }

        public Criteria andNidNotIn(List<String> values) {
            addCriterion("nid not in", values, "nid");
            return (Criteria) this;
        }

        public Criteria andNidBetween(String value1, String value2) {
            addCriterion("nid between", value1, value2, "nid");
            return (Criteria) this;
        }

        public Criteria andNidNotBetween(String value1, String value2) {
            addCriterion("nid not between", value1, value2, "nid");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("`type` is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("`type` is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(String value) {
            addCriterion("`type` =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(String value) {
            addCriterion("`type` <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(String value) {
            addCriterion("`type` >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(String value) {
            addCriterion("`type` >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(String value) {
            addCriterion("`type` <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(String value) {
            addCriterion("`type` <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLike(String value) {
            addCriterion("`type` like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotLike(String value) {
            addCriterion("`type` not like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<String> values) {
            addCriterion("`type` in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<String> values) {
            addCriterion("`type` not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(String value1, String value2) {
            addCriterion("`type` between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(String value1, String value2) {
            addCriterion("`type` not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andSpreadsTypeIsNull() {
            addCriterion("spreads_type is null");
            return (Criteria) this;
        }

        public Criteria andSpreadsTypeIsNotNull() {
            addCriterion("spreads_type is not null");
            return (Criteria) this;
        }

        public Criteria andSpreadsTypeEqualTo(String value) {
            addCriterion("spreads_type =", value, "spreadsType");
            return (Criteria) this;
        }

        public Criteria andSpreadsTypeNotEqualTo(String value) {
            addCriterion("spreads_type <>", value, "spreadsType");
            return (Criteria) this;
        }

        public Criteria andSpreadsTypeGreaterThan(String value) {
            addCriterion("spreads_type >", value, "spreadsType");
            return (Criteria) this;
        }

        public Criteria andSpreadsTypeGreaterThanOrEqualTo(String value) {
            addCriterion("spreads_type >=", value, "spreadsType");
            return (Criteria) this;
        }

        public Criteria andSpreadsTypeLessThan(String value) {
            addCriterion("spreads_type <", value, "spreadsType");
            return (Criteria) this;
        }

        public Criteria andSpreadsTypeLessThanOrEqualTo(String value) {
            addCriterion("spreads_type <=", value, "spreadsType");
            return (Criteria) this;
        }

        public Criteria andSpreadsTypeLike(String value) {
            addCriterion("spreads_type like", value, "spreadsType");
            return (Criteria) this;
        }

        public Criteria andSpreadsTypeNotLike(String value) {
            addCriterion("spreads_type not like", value, "spreadsType");
            return (Criteria) this;
        }

        public Criteria andSpreadsTypeIn(List<String> values) {
            addCriterion("spreads_type in", values, "spreadsType");
            return (Criteria) this;
        }

        public Criteria andSpreadsTypeNotIn(List<String> values) {
            addCriterion("spreads_type not in", values, "spreadsType");
            return (Criteria) this;
        }

        public Criteria andSpreadsTypeBetween(String value1, String value2) {
            addCriterion("spreads_type between", value1, value2, "spreadsType");
            return (Criteria) this;
        }

        public Criteria andSpreadsTypeNotBetween(String value1, String value2) {
            addCriterion("spreads_type not between", value1, value2, "spreadsType");
            return (Criteria) this;
        }

        public Criteria andAccountTypeIsNull() {
            addCriterion("account_type is null");
            return (Criteria) this;
        }

        public Criteria andAccountTypeIsNotNull() {
            addCriterion("account_type is not null");
            return (Criteria) this;
        }

        public Criteria andAccountTypeEqualTo(String value) {
            addCriterion("account_type =", value, "accountType");
            return (Criteria) this;
        }

        public Criteria andAccountTypeNotEqualTo(String value) {
            addCriterion("account_type <>", value, "accountType");
            return (Criteria) this;
        }

        public Criteria andAccountTypeGreaterThan(String value) {
            addCriterion("account_type >", value, "accountType");
            return (Criteria) this;
        }

        public Criteria andAccountTypeGreaterThanOrEqualTo(String value) {
            addCriterion("account_type >=", value, "accountType");
            return (Criteria) this;
        }

        public Criteria andAccountTypeLessThan(String value) {
            addCriterion("account_type <", value, "accountType");
            return (Criteria) this;
        }

        public Criteria andAccountTypeLessThanOrEqualTo(String value) {
            addCriterion("account_type <=", value, "accountType");
            return (Criteria) this;
        }

        public Criteria andAccountTypeLike(String value) {
            addCriterion("account_type like", value, "accountType");
            return (Criteria) this;
        }

        public Criteria andAccountTypeNotLike(String value) {
            addCriterion("account_type not like", value, "accountType");
            return (Criteria) this;
        }

        public Criteria andAccountTypeIn(List<String> values) {
            addCriterion("account_type in", values, "accountType");
            return (Criteria) this;
        }

        public Criteria andAccountTypeNotIn(List<String> values) {
            addCriterion("account_type not in", values, "accountType");
            return (Criteria) this;
        }

        public Criteria andAccountTypeBetween(String value1, String value2) {
            addCriterion("account_type between", value1, value2, "accountType");
            return (Criteria) this;
        }

        public Criteria andAccountTypeNotBetween(String value1, String value2) {
            addCriterion("account_type not between", value1, value2, "accountType");
            return (Criteria) this;
        }

        public Criteria andScalesIsNull() {
            addCriterion("scales is null");
            return (Criteria) this;
        }

        public Criteria andScalesIsNotNull() {
            addCriterion("scales is not null");
            return (Criteria) this;
        }

        public Criteria andScalesEqualTo(String value) {
            addCriterion("scales =", value, "scales");
            return (Criteria) this;
        }

        public Criteria andScalesNotEqualTo(String value) {
            addCriterion("scales <>", value, "scales");
            return (Criteria) this;
        }

        public Criteria andScalesGreaterThan(String value) {
            addCriterion("scales >", value, "scales");
            return (Criteria) this;
        }

        public Criteria andScalesGreaterThanOrEqualTo(String value) {
            addCriterion("scales >=", value, "scales");
            return (Criteria) this;
        }

        public Criteria andScalesLessThan(String value) {
            addCriterion("scales <", value, "scales");
            return (Criteria) this;
        }

        public Criteria andScalesLessThanOrEqualTo(String value) {
            addCriterion("scales <=", value, "scales");
            return (Criteria) this;
        }

        public Criteria andScalesLike(String value) {
            addCriterion("scales like", value, "scales");
            return (Criteria) this;
        }

        public Criteria andScalesNotLike(String value) {
            addCriterion("scales not like", value, "scales");
            return (Criteria) this;
        }

        public Criteria andScalesIn(List<String> values) {
            addCriterion("scales in", values, "scales");
            return (Criteria) this;
        }

        public Criteria andScalesNotIn(List<String> values) {
            addCriterion("scales not in", values, "scales");
            return (Criteria) this;
        }

        public Criteria andScalesBetween(String value1, String value2) {
            addCriterion("scales between", value1, value2, "scales");
            return (Criteria) this;
        }

        public Criteria andScalesNotBetween(String value1, String value2) {
            addCriterion("scales not between", value1, value2, "scales");
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

        public Criteria andTenderIdIsNull() {
            addCriterion("tender_id is null");
            return (Criteria) this;
        }

        public Criteria andTenderIdIsNotNull() {
            addCriterion("tender_id is not null");
            return (Criteria) this;
        }

        public Criteria andTenderIdEqualTo(Integer value) {
            addCriterion("tender_id =", value, "tenderId");
            return (Criteria) this;
        }

        public Criteria andTenderIdNotEqualTo(Integer value) {
            addCriterion("tender_id <>", value, "tenderId");
            return (Criteria) this;
        }

        public Criteria andTenderIdGreaterThan(Integer value) {
            addCriterion("tender_id >", value, "tenderId");
            return (Criteria) this;
        }

        public Criteria andTenderIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("tender_id >=", value, "tenderId");
            return (Criteria) this;
        }

        public Criteria andTenderIdLessThan(Integer value) {
            addCriterion("tender_id <", value, "tenderId");
            return (Criteria) this;
        }

        public Criteria andTenderIdLessThanOrEqualTo(Integer value) {
            addCriterion("tender_id <=", value, "tenderId");
            return (Criteria) this;
        }

        public Criteria andTenderIdIn(List<Integer> values) {
            addCriterion("tender_id in", values, "tenderId");
            return (Criteria) this;
        }

        public Criteria andTenderIdNotIn(List<Integer> values) {
            addCriterion("tender_id not in", values, "tenderId");
            return (Criteria) this;
        }

        public Criteria andTenderIdBetween(Integer value1, Integer value2) {
            addCriterion("tender_id between", value1, value2, "tenderId");
            return (Criteria) this;
        }

        public Criteria andTenderIdNotBetween(Integer value1, Integer value2) {
            addCriterion("tender_id not between", value1, value2, "tenderId");
            return (Criteria) this;
        }

        public Criteria andRepayIdIsNull() {
            addCriterion("repay_id is null");
            return (Criteria) this;
        }

        public Criteria andRepayIdIsNotNull() {
            addCriterion("repay_id is not null");
            return (Criteria) this;
        }

        public Criteria andRepayIdEqualTo(Integer value) {
            addCriterion("repay_id =", value, "repayId");
            return (Criteria) this;
        }

        public Criteria andRepayIdNotEqualTo(Integer value) {
            addCriterion("repay_id <>", value, "repayId");
            return (Criteria) this;
        }

        public Criteria andRepayIdGreaterThan(Integer value) {
            addCriterion("repay_id >", value, "repayId");
            return (Criteria) this;
        }

        public Criteria andRepayIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("repay_id >=", value, "repayId");
            return (Criteria) this;
        }

        public Criteria andRepayIdLessThan(Integer value) {
            addCriterion("repay_id <", value, "repayId");
            return (Criteria) this;
        }

        public Criteria andRepayIdLessThanOrEqualTo(Integer value) {
            addCriterion("repay_id <=", value, "repayId");
            return (Criteria) this;
        }

        public Criteria andRepayIdIn(List<Integer> values) {
            addCriterion("repay_id in", values, "repayId");
            return (Criteria) this;
        }

        public Criteria andRepayIdNotIn(List<Integer> values) {
            addCriterion("repay_id not in", values, "repayId");
            return (Criteria) this;
        }

        public Criteria andRepayIdBetween(Integer value1, Integer value2) {
            addCriterion("repay_id between", value1, value2, "repayId");
            return (Criteria) this;
        }

        public Criteria andRepayIdNotBetween(Integer value1, Integer value2) {
            addCriterion("repay_id not between", value1, value2, "repayId");
            return (Criteria) this;
        }

        public Criteria andAccountAllIsNull() {
            addCriterion("account_all is null");
            return (Criteria) this;
        }

        public Criteria andAccountAllIsNotNull() {
            addCriterion("account_all is not null");
            return (Criteria) this;
        }

        public Criteria andAccountAllEqualTo(BigDecimal value) {
            addCriterion("account_all =", value, "accountAll");
            return (Criteria) this;
        }

        public Criteria andAccountAllNotEqualTo(BigDecimal value) {
            addCriterion("account_all <>", value, "accountAll");
            return (Criteria) this;
        }

        public Criteria andAccountAllGreaterThan(BigDecimal value) {
            addCriterion("account_all >", value, "accountAll");
            return (Criteria) this;
        }

        public Criteria andAccountAllGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("account_all >=", value, "accountAll");
            return (Criteria) this;
        }

        public Criteria andAccountAllLessThan(BigDecimal value) {
            addCriterion("account_all <", value, "accountAll");
            return (Criteria) this;
        }

        public Criteria andAccountAllLessThanOrEqualTo(BigDecimal value) {
            addCriterion("account_all <=", value, "accountAll");
            return (Criteria) this;
        }

        public Criteria andAccountAllIn(List<BigDecimal> values) {
            addCriterion("account_all in", values, "accountAll");
            return (Criteria) this;
        }

        public Criteria andAccountAllNotIn(List<BigDecimal> values) {
            addCriterion("account_all not in", values, "accountAll");
            return (Criteria) this;
        }

        public Criteria andAccountAllBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("account_all between", value1, value2, "accountAll");
            return (Criteria) this;
        }

        public Criteria andAccountAllNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("account_all not between", value1, value2, "accountAll");
            return (Criteria) this;
        }

        public Criteria andAccountCapitalIsNull() {
            addCriterion("account_capital is null");
            return (Criteria) this;
        }

        public Criteria andAccountCapitalIsNotNull() {
            addCriterion("account_capital is not null");
            return (Criteria) this;
        }

        public Criteria andAccountCapitalEqualTo(BigDecimal value) {
            addCriterion("account_capital =", value, "accountCapital");
            return (Criteria) this;
        }

        public Criteria andAccountCapitalNotEqualTo(BigDecimal value) {
            addCriterion("account_capital <>", value, "accountCapital");
            return (Criteria) this;
        }

        public Criteria andAccountCapitalGreaterThan(BigDecimal value) {
            addCriterion("account_capital >", value, "accountCapital");
            return (Criteria) this;
        }

        public Criteria andAccountCapitalGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("account_capital >=", value, "accountCapital");
            return (Criteria) this;
        }

        public Criteria andAccountCapitalLessThan(BigDecimal value) {
            addCriterion("account_capital <", value, "accountCapital");
            return (Criteria) this;
        }

        public Criteria andAccountCapitalLessThanOrEqualTo(BigDecimal value) {
            addCriterion("account_capital <=", value, "accountCapital");
            return (Criteria) this;
        }

        public Criteria andAccountCapitalIn(List<BigDecimal> values) {
            addCriterion("account_capital in", values, "accountCapital");
            return (Criteria) this;
        }

        public Criteria andAccountCapitalNotIn(List<BigDecimal> values) {
            addCriterion("account_capital not in", values, "accountCapital");
            return (Criteria) this;
        }

        public Criteria andAccountCapitalBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("account_capital between", value1, value2, "accountCapital");
            return (Criteria) this;
        }

        public Criteria andAccountCapitalNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("account_capital not between", value1, value2, "accountCapital");
            return (Criteria) this;
        }

        public Criteria andAccountInterestIsNull() {
            addCriterion("account_interest is null");
            return (Criteria) this;
        }

        public Criteria andAccountInterestIsNotNull() {
            addCriterion("account_interest is not null");
            return (Criteria) this;
        }

        public Criteria andAccountInterestEqualTo(BigDecimal value) {
            addCriterion("account_interest =", value, "accountInterest");
            return (Criteria) this;
        }

        public Criteria andAccountInterestNotEqualTo(BigDecimal value) {
            addCriterion("account_interest <>", value, "accountInterest");
            return (Criteria) this;
        }

        public Criteria andAccountInterestGreaterThan(BigDecimal value) {
            addCriterion("account_interest >", value, "accountInterest");
            return (Criteria) this;
        }

        public Criteria andAccountInterestGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("account_interest >=", value, "accountInterest");
            return (Criteria) this;
        }

        public Criteria andAccountInterestLessThan(BigDecimal value) {
            addCriterion("account_interest <", value, "accountInterest");
            return (Criteria) this;
        }

        public Criteria andAccountInterestLessThanOrEqualTo(BigDecimal value) {
            addCriterion("account_interest <=", value, "accountInterest");
            return (Criteria) this;
        }

        public Criteria andAccountInterestIn(List<BigDecimal> values) {
            addCriterion("account_interest in", values, "accountInterest");
            return (Criteria) this;
        }

        public Criteria andAccountInterestNotIn(List<BigDecimal> values) {
            addCriterion("account_interest not in", values, "accountInterest");
            return (Criteria) this;
        }

        public Criteria andAccountInterestBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("account_interest between", value1, value2, "accountInterest");
            return (Criteria) this;
        }

        public Criteria andAccountInterestNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("account_interest not between", value1, value2, "accountInterest");
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

        public Criteria andCreateIpIsNull() {
            addCriterion("create_ip is null");
            return (Criteria) this;
        }

        public Criteria andCreateIpIsNotNull() {
            addCriterion("create_ip is not null");
            return (Criteria) this;
        }

        public Criteria andCreateIpEqualTo(String value) {
            addCriterion("create_ip =", value, "createIp");
            return (Criteria) this;
        }

        public Criteria andCreateIpNotEqualTo(String value) {
            addCriterion("create_ip <>", value, "createIp");
            return (Criteria) this;
        }

        public Criteria andCreateIpGreaterThan(String value) {
            addCriterion("create_ip >", value, "createIp");
            return (Criteria) this;
        }

        public Criteria andCreateIpGreaterThanOrEqualTo(String value) {
            addCriterion("create_ip >=", value, "createIp");
            return (Criteria) this;
        }

        public Criteria andCreateIpLessThan(String value) {
            addCriterion("create_ip <", value, "createIp");
            return (Criteria) this;
        }

        public Criteria andCreateIpLessThanOrEqualTo(String value) {
            addCriterion("create_ip <=", value, "createIp");
            return (Criteria) this;
        }

        public Criteria andCreateIpLike(String value) {
            addCriterion("create_ip like", value, "createIp");
            return (Criteria) this;
        }

        public Criteria andCreateIpNotLike(String value) {
            addCriterion("create_ip not like", value, "createIp");
            return (Criteria) this;
        }

        public Criteria andCreateIpIn(List<String> values) {
            addCriterion("create_ip in", values, "createIp");
            return (Criteria) this;
        }

        public Criteria andCreateIpNotIn(List<String> values) {
            addCriterion("create_ip not in", values, "createIp");
            return (Criteria) this;
        }

        public Criteria andCreateIpBetween(String value1, String value2) {
            addCriterion("create_ip between", value1, value2, "createIp");
            return (Criteria) this;
        }

        public Criteria andCreateIpNotBetween(String value1, String value2) {
            addCriterion("create_ip not between", value1, value2, "createIp");
            return (Criteria) this;
        }

        public Criteria andPayStatusIsNull() {
            addCriterion("pay_status is null");
            return (Criteria) this;
        }

        public Criteria andPayStatusIsNotNull() {
            addCriterion("pay_status is not null");
            return (Criteria) this;
        }

        public Criteria andPayStatusEqualTo(Integer value) {
            addCriterion("pay_status =", value, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusNotEqualTo(Integer value) {
            addCriterion("pay_status <>", value, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusGreaterThan(Integer value) {
            addCriterion("pay_status >", value, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("pay_status >=", value, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusLessThan(Integer value) {
            addCriterion("pay_status <", value, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusLessThanOrEqualTo(Integer value) {
            addCriterion("pay_status <=", value, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusIn(List<Integer> values) {
            addCriterion("pay_status in", values, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusNotIn(List<Integer> values) {
            addCriterion("pay_status not in", values, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusBetween(Integer value1, Integer value2) {
            addCriterion("pay_status between", value1, value2, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("pay_status not between", value1, value2, "payStatus");
            return (Criteria) this;
        }

        public Criteria andIsValidIsNull() {
            addCriterion("is_valid is null");
            return (Criteria) this;
        }

        public Criteria andIsValidIsNotNull() {
            addCriterion("is_valid is not null");
            return (Criteria) this;
        }

        public Criteria andIsValidEqualTo(Integer value) {
            addCriterion("is_valid =", value, "isValid");
            return (Criteria) this;
        }

        public Criteria andIsValidNotEqualTo(Integer value) {
            addCriterion("is_valid <>", value, "isValid");
            return (Criteria) this;
        }

        public Criteria andIsValidGreaterThan(Integer value) {
            addCriterion("is_valid >", value, "isValid");
            return (Criteria) this;
        }

        public Criteria andIsValidGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_valid >=", value, "isValid");
            return (Criteria) this;
        }

        public Criteria andIsValidLessThan(Integer value) {
            addCriterion("is_valid <", value, "isValid");
            return (Criteria) this;
        }

        public Criteria andIsValidLessThanOrEqualTo(Integer value) {
            addCriterion("is_valid <=", value, "isValid");
            return (Criteria) this;
        }

        public Criteria andIsValidIn(List<Integer> values) {
            addCriterion("is_valid in", values, "isValid");
            return (Criteria) this;
        }

        public Criteria andIsValidNotIn(List<Integer> values) {
            addCriterion("is_valid not in", values, "isValid");
            return (Criteria) this;
        }

        public Criteria andIsValidBetween(Integer value1, Integer value2) {
            addCriterion("is_valid between", value1, value2, "isValid");
            return (Criteria) this;
        }

        public Criteria andIsValidNotBetween(Integer value1, Integer value2) {
            addCriterion("is_valid not between", value1, value2, "isValid");
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