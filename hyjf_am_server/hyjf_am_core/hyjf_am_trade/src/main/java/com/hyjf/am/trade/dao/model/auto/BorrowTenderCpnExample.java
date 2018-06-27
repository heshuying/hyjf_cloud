package com.hyjf.am.trade.dao.model.auto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BorrowTenderCpnExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public BorrowTenderCpnExample() {
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

        public Criteria andAccountTenderIsNull() {
            addCriterion("account_tender is null");
            return (Criteria) this;
        }

        public Criteria andAccountTenderIsNotNull() {
            addCriterion("account_tender is not null");
            return (Criteria) this;
        }

        public Criteria andAccountTenderEqualTo(BigDecimal value) {
            addCriterion("account_tender =", value, "accountTender");
            return (Criteria) this;
        }

        public Criteria andAccountTenderNotEqualTo(BigDecimal value) {
            addCriterion("account_tender <>", value, "accountTender");
            return (Criteria) this;
        }

        public Criteria andAccountTenderGreaterThan(BigDecimal value) {
            addCriterion("account_tender >", value, "accountTender");
            return (Criteria) this;
        }

        public Criteria andAccountTenderGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("account_tender >=", value, "accountTender");
            return (Criteria) this;
        }

        public Criteria andAccountTenderLessThan(BigDecimal value) {
            addCriterion("account_tender <", value, "accountTender");
            return (Criteria) this;
        }

        public Criteria andAccountTenderLessThanOrEqualTo(BigDecimal value) {
            addCriterion("account_tender <=", value, "accountTender");
            return (Criteria) this;
        }

        public Criteria andAccountTenderIn(List<BigDecimal> values) {
            addCriterion("account_tender in", values, "accountTender");
            return (Criteria) this;
        }

        public Criteria andAccountTenderNotIn(List<BigDecimal> values) {
            addCriterion("account_tender not in", values, "accountTender");
            return (Criteria) this;
        }

        public Criteria andAccountTenderBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("account_tender between", value1, value2, "accountTender");
            return (Criteria) this;
        }

        public Criteria andAccountTenderNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("account_tender not between", value1, value2, "accountTender");
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

        public Criteria andTenderStatusIsNull() {
            addCriterion("tender_status is null");
            return (Criteria) this;
        }

        public Criteria andTenderStatusIsNotNull() {
            addCriterion("tender_status is not null");
            return (Criteria) this;
        }

        public Criteria andTenderStatusEqualTo(Integer value) {
            addCriterion("tender_status =", value, "tenderStatus");
            return (Criteria) this;
        }

        public Criteria andTenderStatusNotEqualTo(Integer value) {
            addCriterion("tender_status <>", value, "tenderStatus");
            return (Criteria) this;
        }

        public Criteria andTenderStatusGreaterThan(Integer value) {
            addCriterion("tender_status >", value, "tenderStatus");
            return (Criteria) this;
        }

        public Criteria andTenderStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("tender_status >=", value, "tenderStatus");
            return (Criteria) this;
        }

        public Criteria andTenderStatusLessThan(Integer value) {
            addCriterion("tender_status <", value, "tenderStatus");
            return (Criteria) this;
        }

        public Criteria andTenderStatusLessThanOrEqualTo(Integer value) {
            addCriterion("tender_status <=", value, "tenderStatus");
            return (Criteria) this;
        }

        public Criteria andTenderStatusIn(List<Integer> values) {
            addCriterion("tender_status in", values, "tenderStatus");
            return (Criteria) this;
        }

        public Criteria andTenderStatusNotIn(List<Integer> values) {
            addCriterion("tender_status not in", values, "tenderStatus");
            return (Criteria) this;
        }

        public Criteria andTenderStatusBetween(Integer value1, Integer value2) {
            addCriterion("tender_status between", value1, value2, "tenderStatus");
            return (Criteria) this;
        }

        public Criteria andTenderStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("tender_status not between", value1, value2, "tenderStatus");
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

        public Criteria andRecoverAccountAllIsNull() {
            addCriterion("recover_account_all is null");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountAllIsNotNull() {
            addCriterion("recover_account_all is not null");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountAllEqualTo(BigDecimal value) {
            addCriterion("recover_account_all =", value, "recoverAccountAll");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountAllNotEqualTo(BigDecimal value) {
            addCriterion("recover_account_all <>", value, "recoverAccountAll");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountAllGreaterThan(BigDecimal value) {
            addCriterion("recover_account_all >", value, "recoverAccountAll");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountAllGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("recover_account_all >=", value, "recoverAccountAll");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountAllLessThan(BigDecimal value) {
            addCriterion("recover_account_all <", value, "recoverAccountAll");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountAllLessThanOrEqualTo(BigDecimal value) {
            addCriterion("recover_account_all <=", value, "recoverAccountAll");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountAllIn(List<BigDecimal> values) {
            addCriterion("recover_account_all in", values, "recoverAccountAll");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountAllNotIn(List<BigDecimal> values) {
            addCriterion("recover_account_all not in", values, "recoverAccountAll");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountAllBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("recover_account_all between", value1, value2, "recoverAccountAll");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountAllNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("recover_account_all not between", value1, value2, "recoverAccountAll");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountInterestIsNull() {
            addCriterion("recover_account_interest is null");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountInterestIsNotNull() {
            addCriterion("recover_account_interest is not null");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountInterestEqualTo(BigDecimal value) {
            addCriterion("recover_account_interest =", value, "recoverAccountInterest");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountInterestNotEqualTo(BigDecimal value) {
            addCriterion("recover_account_interest <>", value, "recoverAccountInterest");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountInterestGreaterThan(BigDecimal value) {
            addCriterion("recover_account_interest >", value, "recoverAccountInterest");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountInterestGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("recover_account_interest >=", value, "recoverAccountInterest");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountInterestLessThan(BigDecimal value) {
            addCriterion("recover_account_interest <", value, "recoverAccountInterest");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountInterestLessThanOrEqualTo(BigDecimal value) {
            addCriterion("recover_account_interest <=", value, "recoverAccountInterest");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountInterestIn(List<BigDecimal> values) {
            addCriterion("recover_account_interest in", values, "recoverAccountInterest");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountInterestNotIn(List<BigDecimal> values) {
            addCriterion("recover_account_interest not in", values, "recoverAccountInterest");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountInterestBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("recover_account_interest between", value1, value2, "recoverAccountInterest");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountInterestNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("recover_account_interest not between", value1, value2, "recoverAccountInterest");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountYesIsNull() {
            addCriterion("recover_account_yes is null");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountYesIsNotNull() {
            addCriterion("recover_account_yes is not null");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountYesEqualTo(BigDecimal value) {
            addCriterion("recover_account_yes =", value, "recoverAccountYes");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountYesNotEqualTo(BigDecimal value) {
            addCriterion("recover_account_yes <>", value, "recoverAccountYes");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountYesGreaterThan(BigDecimal value) {
            addCriterion("recover_account_yes >", value, "recoverAccountYes");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountYesGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("recover_account_yes >=", value, "recoverAccountYes");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountYesLessThan(BigDecimal value) {
            addCriterion("recover_account_yes <", value, "recoverAccountYes");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountYesLessThanOrEqualTo(BigDecimal value) {
            addCriterion("recover_account_yes <=", value, "recoverAccountYes");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountYesIn(List<BigDecimal> values) {
            addCriterion("recover_account_yes in", values, "recoverAccountYes");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountYesNotIn(List<BigDecimal> values) {
            addCriterion("recover_account_yes not in", values, "recoverAccountYes");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountYesBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("recover_account_yes between", value1, value2, "recoverAccountYes");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountYesNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("recover_account_yes not between", value1, value2, "recoverAccountYes");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountInterestYesIsNull() {
            addCriterion("recover_account_interest_yes is null");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountInterestYesIsNotNull() {
            addCriterion("recover_account_interest_yes is not null");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountInterestYesEqualTo(BigDecimal value) {
            addCriterion("recover_account_interest_yes =", value, "recoverAccountInterestYes");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountInterestYesNotEqualTo(BigDecimal value) {
            addCriterion("recover_account_interest_yes <>", value, "recoverAccountInterestYes");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountInterestYesGreaterThan(BigDecimal value) {
            addCriterion("recover_account_interest_yes >", value, "recoverAccountInterestYes");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountInterestYesGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("recover_account_interest_yes >=", value, "recoverAccountInterestYes");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountInterestYesLessThan(BigDecimal value) {
            addCriterion("recover_account_interest_yes <", value, "recoverAccountInterestYes");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountInterestYesLessThanOrEqualTo(BigDecimal value) {
            addCriterion("recover_account_interest_yes <=", value, "recoverAccountInterestYes");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountInterestYesIn(List<BigDecimal> values) {
            addCriterion("recover_account_interest_yes in", values, "recoverAccountInterestYes");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountInterestYesNotIn(List<BigDecimal> values) {
            addCriterion("recover_account_interest_yes not in", values, "recoverAccountInterestYes");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountInterestYesBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("recover_account_interest_yes between", value1, value2, "recoverAccountInterestYes");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountInterestYesNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("recover_account_interest_yes not between", value1, value2, "recoverAccountInterestYes");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountCapitalYesIsNull() {
            addCriterion("recover_account_capital_yes is null");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountCapitalYesIsNotNull() {
            addCriterion("recover_account_capital_yes is not null");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountCapitalYesEqualTo(BigDecimal value) {
            addCriterion("recover_account_capital_yes =", value, "recoverAccountCapitalYes");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountCapitalYesNotEqualTo(BigDecimal value) {
            addCriterion("recover_account_capital_yes <>", value, "recoverAccountCapitalYes");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountCapitalYesGreaterThan(BigDecimal value) {
            addCriterion("recover_account_capital_yes >", value, "recoverAccountCapitalYes");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountCapitalYesGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("recover_account_capital_yes >=", value, "recoverAccountCapitalYes");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountCapitalYesLessThan(BigDecimal value) {
            addCriterion("recover_account_capital_yes <", value, "recoverAccountCapitalYes");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountCapitalYesLessThanOrEqualTo(BigDecimal value) {
            addCriterion("recover_account_capital_yes <=", value, "recoverAccountCapitalYes");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountCapitalYesIn(List<BigDecimal> values) {
            addCriterion("recover_account_capital_yes in", values, "recoverAccountCapitalYes");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountCapitalYesNotIn(List<BigDecimal> values) {
            addCriterion("recover_account_capital_yes not in", values, "recoverAccountCapitalYes");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountCapitalYesBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("recover_account_capital_yes between", value1, value2, "recoverAccountCapitalYes");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountCapitalYesNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("recover_account_capital_yes not between", value1, value2, "recoverAccountCapitalYes");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountWaitIsNull() {
            addCriterion("recover_account_wait is null");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountWaitIsNotNull() {
            addCriterion("recover_account_wait is not null");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountWaitEqualTo(BigDecimal value) {
            addCriterion("recover_account_wait =", value, "recoverAccountWait");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountWaitNotEqualTo(BigDecimal value) {
            addCriterion("recover_account_wait <>", value, "recoverAccountWait");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountWaitGreaterThan(BigDecimal value) {
            addCriterion("recover_account_wait >", value, "recoverAccountWait");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountWaitGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("recover_account_wait >=", value, "recoverAccountWait");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountWaitLessThan(BigDecimal value) {
            addCriterion("recover_account_wait <", value, "recoverAccountWait");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountWaitLessThanOrEqualTo(BigDecimal value) {
            addCriterion("recover_account_wait <=", value, "recoverAccountWait");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountWaitIn(List<BigDecimal> values) {
            addCriterion("recover_account_wait in", values, "recoverAccountWait");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountWaitNotIn(List<BigDecimal> values) {
            addCriterion("recover_account_wait not in", values, "recoverAccountWait");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountWaitBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("recover_account_wait between", value1, value2, "recoverAccountWait");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountWaitNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("recover_account_wait not between", value1, value2, "recoverAccountWait");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountInterestWaitIsNull() {
            addCriterion("recover_account_interest_wait is null");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountInterestWaitIsNotNull() {
            addCriterion("recover_account_interest_wait is not null");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountInterestWaitEqualTo(BigDecimal value) {
            addCriterion("recover_account_interest_wait =", value, "recoverAccountInterestWait");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountInterestWaitNotEqualTo(BigDecimal value) {
            addCriterion("recover_account_interest_wait <>", value, "recoverAccountInterestWait");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountInterestWaitGreaterThan(BigDecimal value) {
            addCriterion("recover_account_interest_wait >", value, "recoverAccountInterestWait");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountInterestWaitGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("recover_account_interest_wait >=", value, "recoverAccountInterestWait");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountInterestWaitLessThan(BigDecimal value) {
            addCriterion("recover_account_interest_wait <", value, "recoverAccountInterestWait");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountInterestWaitLessThanOrEqualTo(BigDecimal value) {
            addCriterion("recover_account_interest_wait <=", value, "recoverAccountInterestWait");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountInterestWaitIn(List<BigDecimal> values) {
            addCriterion("recover_account_interest_wait in", values, "recoverAccountInterestWait");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountInterestWaitNotIn(List<BigDecimal> values) {
            addCriterion("recover_account_interest_wait not in", values, "recoverAccountInterestWait");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountInterestWaitBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("recover_account_interest_wait between", value1, value2, "recoverAccountInterestWait");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountInterestWaitNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("recover_account_interest_wait not between", value1, value2, "recoverAccountInterestWait");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountCapitalWaitIsNull() {
            addCriterion("recover_account_capital_wait is null");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountCapitalWaitIsNotNull() {
            addCriterion("recover_account_capital_wait is not null");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountCapitalWaitEqualTo(BigDecimal value) {
            addCriterion("recover_account_capital_wait =", value, "recoverAccountCapitalWait");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountCapitalWaitNotEqualTo(BigDecimal value) {
            addCriterion("recover_account_capital_wait <>", value, "recoverAccountCapitalWait");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountCapitalWaitGreaterThan(BigDecimal value) {
            addCriterion("recover_account_capital_wait >", value, "recoverAccountCapitalWait");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountCapitalWaitGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("recover_account_capital_wait >=", value, "recoverAccountCapitalWait");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountCapitalWaitLessThan(BigDecimal value) {
            addCriterion("recover_account_capital_wait <", value, "recoverAccountCapitalWait");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountCapitalWaitLessThanOrEqualTo(BigDecimal value) {
            addCriterion("recover_account_capital_wait <=", value, "recoverAccountCapitalWait");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountCapitalWaitIn(List<BigDecimal> values) {
            addCriterion("recover_account_capital_wait in", values, "recoverAccountCapitalWait");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountCapitalWaitNotIn(List<BigDecimal> values) {
            addCriterion("recover_account_capital_wait not in", values, "recoverAccountCapitalWait");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountCapitalWaitBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("recover_account_capital_wait between", value1, value2, "recoverAccountCapitalWait");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountCapitalWaitNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("recover_account_capital_wait not between", value1, value2, "recoverAccountCapitalWait");
            return (Criteria) this;
        }

        public Criteria andRecoverTimesIsNull() {
            addCriterion("recover_times is null");
            return (Criteria) this;
        }

        public Criteria andRecoverTimesIsNotNull() {
            addCriterion("recover_times is not null");
            return (Criteria) this;
        }

        public Criteria andRecoverTimesEqualTo(Integer value) {
            addCriterion("recover_times =", value, "recoverTimes");
            return (Criteria) this;
        }

        public Criteria andRecoverTimesNotEqualTo(Integer value) {
            addCriterion("recover_times <>", value, "recoverTimes");
            return (Criteria) this;
        }

        public Criteria andRecoverTimesGreaterThan(Integer value) {
            addCriterion("recover_times >", value, "recoverTimes");
            return (Criteria) this;
        }

        public Criteria andRecoverTimesGreaterThanOrEqualTo(Integer value) {
            addCriterion("recover_times >=", value, "recoverTimes");
            return (Criteria) this;
        }

        public Criteria andRecoverTimesLessThan(Integer value) {
            addCriterion("recover_times <", value, "recoverTimes");
            return (Criteria) this;
        }

        public Criteria andRecoverTimesLessThanOrEqualTo(Integer value) {
            addCriterion("recover_times <=", value, "recoverTimes");
            return (Criteria) this;
        }

        public Criteria andRecoverTimesIn(List<Integer> values) {
            addCriterion("recover_times in", values, "recoverTimes");
            return (Criteria) this;
        }

        public Criteria andRecoverTimesNotIn(List<Integer> values) {
            addCriterion("recover_times not in", values, "recoverTimes");
            return (Criteria) this;
        }

        public Criteria andRecoverTimesBetween(Integer value1, Integer value2) {
            addCriterion("recover_times between", value1, value2, "recoverTimes");
            return (Criteria) this;
        }

        public Criteria andRecoverTimesNotBetween(Integer value1, Integer value2) {
            addCriterion("recover_times not between", value1, value2, "recoverTimes");
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

        public Criteria andAutoStatusIsNull() {
            addCriterion("auto_status is null");
            return (Criteria) this;
        }

        public Criteria andAutoStatusIsNotNull() {
            addCriterion("auto_status is not null");
            return (Criteria) this;
        }

        public Criteria andAutoStatusEqualTo(Integer value) {
            addCriterion("auto_status =", value, "autoStatus");
            return (Criteria) this;
        }

        public Criteria andAutoStatusNotEqualTo(Integer value) {
            addCriterion("auto_status <>", value, "autoStatus");
            return (Criteria) this;
        }

        public Criteria andAutoStatusGreaterThan(Integer value) {
            addCriterion("auto_status >", value, "autoStatus");
            return (Criteria) this;
        }

        public Criteria andAutoStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("auto_status >=", value, "autoStatus");
            return (Criteria) this;
        }

        public Criteria andAutoStatusLessThan(Integer value) {
            addCriterion("auto_status <", value, "autoStatus");
            return (Criteria) this;
        }

        public Criteria andAutoStatusLessThanOrEqualTo(Integer value) {
            addCriterion("auto_status <=", value, "autoStatus");
            return (Criteria) this;
        }

        public Criteria andAutoStatusIn(List<Integer> values) {
            addCriterion("auto_status in", values, "autoStatus");
            return (Criteria) this;
        }

        public Criteria andAutoStatusNotIn(List<Integer> values) {
            addCriterion("auto_status not in", values, "autoStatus");
            return (Criteria) this;
        }

        public Criteria andAutoStatusBetween(Integer value1, Integer value2) {
            addCriterion("auto_status between", value1, value2, "autoStatus");
            return (Criteria) this;
        }

        public Criteria andAutoStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("auto_status not between", value1, value2, "autoStatus");
            return (Criteria) this;
        }

        public Criteria andWebStatusIsNull() {
            addCriterion("web_status is null");
            return (Criteria) this;
        }

        public Criteria andWebStatusIsNotNull() {
            addCriterion("web_status is not null");
            return (Criteria) this;
        }

        public Criteria andWebStatusEqualTo(Integer value) {
            addCriterion("web_status =", value, "webStatus");
            return (Criteria) this;
        }

        public Criteria andWebStatusNotEqualTo(Integer value) {
            addCriterion("web_status <>", value, "webStatus");
            return (Criteria) this;
        }

        public Criteria andWebStatusGreaterThan(Integer value) {
            addCriterion("web_status >", value, "webStatus");
            return (Criteria) this;
        }

        public Criteria andWebStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("web_status >=", value, "webStatus");
            return (Criteria) this;
        }

        public Criteria andWebStatusLessThan(Integer value) {
            addCriterion("web_status <", value, "webStatus");
            return (Criteria) this;
        }

        public Criteria andWebStatusLessThanOrEqualTo(Integer value) {
            addCriterion("web_status <=", value, "webStatus");
            return (Criteria) this;
        }

        public Criteria andWebStatusIn(List<Integer> values) {
            addCriterion("web_status in", values, "webStatus");
            return (Criteria) this;
        }

        public Criteria andWebStatusNotIn(List<Integer> values) {
            addCriterion("web_status not in", values, "webStatus");
            return (Criteria) this;
        }

        public Criteria andWebStatusBetween(Integer value1, Integer value2) {
            addCriterion("web_status between", value1, value2, "webStatus");
            return (Criteria) this;
        }

        public Criteria andWebStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("web_status not between", value1, value2, "webStatus");
            return (Criteria) this;
        }

        public Criteria andApiStatusIsNull() {
            addCriterion("api_status is null");
            return (Criteria) this;
        }

        public Criteria andApiStatusIsNotNull() {
            addCriterion("api_status is not null");
            return (Criteria) this;
        }

        public Criteria andApiStatusEqualTo(Integer value) {
            addCriterion("api_status =", value, "apiStatus");
            return (Criteria) this;
        }

        public Criteria andApiStatusNotEqualTo(Integer value) {
            addCriterion("api_status <>", value, "apiStatus");
            return (Criteria) this;
        }

        public Criteria andApiStatusGreaterThan(Integer value) {
            addCriterion("api_status >", value, "apiStatus");
            return (Criteria) this;
        }

        public Criteria andApiStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("api_status >=", value, "apiStatus");
            return (Criteria) this;
        }

        public Criteria andApiStatusLessThan(Integer value) {
            addCriterion("api_status <", value, "apiStatus");
            return (Criteria) this;
        }

        public Criteria andApiStatusLessThanOrEqualTo(Integer value) {
            addCriterion("api_status <=", value, "apiStatus");
            return (Criteria) this;
        }

        public Criteria andApiStatusIn(List<Integer> values) {
            addCriterion("api_status in", values, "apiStatus");
            return (Criteria) this;
        }

        public Criteria andApiStatusNotIn(List<Integer> values) {
            addCriterion("api_status not in", values, "apiStatus");
            return (Criteria) this;
        }

        public Criteria andApiStatusBetween(Integer value1, Integer value2) {
            addCriterion("api_status between", value1, value2, "apiStatus");
            return (Criteria) this;
        }

        public Criteria andApiStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("api_status not between", value1, value2, "apiStatus");
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

        public Criteria andPeriodStatusIsNull() {
            addCriterion("period_status is null");
            return (Criteria) this;
        }

        public Criteria andPeriodStatusIsNotNull() {
            addCriterion("period_status is not null");
            return (Criteria) this;
        }

        public Criteria andPeriodStatusEqualTo(Integer value) {
            addCriterion("period_status =", value, "periodStatus");
            return (Criteria) this;
        }

        public Criteria andPeriodStatusNotEqualTo(Integer value) {
            addCriterion("period_status <>", value, "periodStatus");
            return (Criteria) this;
        }

        public Criteria andPeriodStatusGreaterThan(Integer value) {
            addCriterion("period_status >", value, "periodStatus");
            return (Criteria) this;
        }

        public Criteria andPeriodStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("period_status >=", value, "periodStatus");
            return (Criteria) this;
        }

        public Criteria andPeriodStatusLessThan(Integer value) {
            addCriterion("period_status <", value, "periodStatus");
            return (Criteria) this;
        }

        public Criteria andPeriodStatusLessThanOrEqualTo(Integer value) {
            addCriterion("period_status <=", value, "periodStatus");
            return (Criteria) this;
        }

        public Criteria andPeriodStatusIn(List<Integer> values) {
            addCriterion("period_status in", values, "periodStatus");
            return (Criteria) this;
        }

        public Criteria andPeriodStatusNotIn(List<Integer> values) {
            addCriterion("period_status not in", values, "periodStatus");
            return (Criteria) this;
        }

        public Criteria andPeriodStatusBetween(Integer value1, Integer value2) {
            addCriterion("period_status between", value1, value2, "periodStatus");
            return (Criteria) this;
        }

        public Criteria andPeriodStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("period_status not between", value1, value2, "periodStatus");
            return (Criteria) this;
        }

        public Criteria andIsokIsNull() {
            addCriterion("isok is null");
            return (Criteria) this;
        }

        public Criteria andIsokIsNotNull() {
            addCriterion("isok is not null");
            return (Criteria) this;
        }

        public Criteria andIsokEqualTo(Integer value) {
            addCriterion("isok =", value, "isok");
            return (Criteria) this;
        }

        public Criteria andIsokNotEqualTo(Integer value) {
            addCriterion("isok <>", value, "isok");
            return (Criteria) this;
        }

        public Criteria andIsokGreaterThan(Integer value) {
            addCriterion("isok >", value, "isok");
            return (Criteria) this;
        }

        public Criteria andIsokGreaterThanOrEqualTo(Integer value) {
            addCriterion("isok >=", value, "isok");
            return (Criteria) this;
        }

        public Criteria andIsokLessThan(Integer value) {
            addCriterion("isok <", value, "isok");
            return (Criteria) this;
        }

        public Criteria andIsokLessThanOrEqualTo(Integer value) {
            addCriterion("isok <=", value, "isok");
            return (Criteria) this;
        }

        public Criteria andIsokIn(List<Integer> values) {
            addCriterion("isok in", values, "isok");
            return (Criteria) this;
        }

        public Criteria andIsokNotIn(List<Integer> values) {
            addCriterion("isok not in", values, "isok");
            return (Criteria) this;
        }

        public Criteria andIsokBetween(Integer value1, Integer value2) {
            addCriterion("isok between", value1, value2, "isok");
            return (Criteria) this;
        }

        public Criteria andIsokNotBetween(Integer value1, Integer value2) {
            addCriterion("isok not between", value1, value2, "isok");
            return (Criteria) this;
        }

        public Criteria andIsReportIsNull() {
            addCriterion("is_report is null");
            return (Criteria) this;
        }

        public Criteria andIsReportIsNotNull() {
            addCriterion("is_report is not null");
            return (Criteria) this;
        }

        public Criteria andIsReportEqualTo(Integer value) {
            addCriterion("is_report =", value, "isReport");
            return (Criteria) this;
        }

        public Criteria andIsReportNotEqualTo(Integer value) {
            addCriterion("is_report <>", value, "isReport");
            return (Criteria) this;
        }

        public Criteria andIsReportGreaterThan(Integer value) {
            addCriterion("is_report >", value, "isReport");
            return (Criteria) this;
        }

        public Criteria andIsReportGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_report >=", value, "isReport");
            return (Criteria) this;
        }

        public Criteria andIsReportLessThan(Integer value) {
            addCriterion("is_report <", value, "isReport");
            return (Criteria) this;
        }

        public Criteria andIsReportLessThanOrEqualTo(Integer value) {
            addCriterion("is_report <=", value, "isReport");
            return (Criteria) this;
        }

        public Criteria andIsReportIn(List<Integer> values) {
            addCriterion("is_report in", values, "isReport");
            return (Criteria) this;
        }

        public Criteria andIsReportNotIn(List<Integer> values) {
            addCriterion("is_report not in", values, "isReport");
            return (Criteria) this;
        }

        public Criteria andIsReportBetween(Integer value1, Integer value2) {
            addCriterion("is_report between", value1, value2, "isReport");
            return (Criteria) this;
        }

        public Criteria andIsReportNotBetween(Integer value1, Integer value2) {
            addCriterion("is_report not between", value1, value2, "isReport");
            return (Criteria) this;
        }

        public Criteria andFlagIsNull() {
            addCriterion("flag is null");
            return (Criteria) this;
        }

        public Criteria andFlagIsNotNull() {
            addCriterion("flag is not null");
            return (Criteria) this;
        }

        public Criteria andFlagEqualTo(Integer value) {
            addCriterion("flag =", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagNotEqualTo(Integer value) {
            addCriterion("flag <>", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagGreaterThan(Integer value) {
            addCriterion("flag >", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("flag >=", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagLessThan(Integer value) {
            addCriterion("flag <", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagLessThanOrEqualTo(Integer value) {
            addCriterion("flag <=", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagIn(List<Integer> values) {
            addCriterion("flag in", values, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagNotIn(List<Integer> values) {
            addCriterion("flag not in", values, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagBetween(Integer value1, Integer value2) {
            addCriterion("flag between", value1, value2, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("flag not between", value1, value2, "flag");
            return (Criteria) this;
        }

        public Criteria andActivityFlagIsNull() {
            addCriterion("activity_flag is null");
            return (Criteria) this;
        }

        public Criteria andActivityFlagIsNotNull() {
            addCriterion("activity_flag is not null");
            return (Criteria) this;
        }

        public Criteria andActivityFlagEqualTo(Integer value) {
            addCriterion("activity_flag =", value, "activityFlag");
            return (Criteria) this;
        }

        public Criteria andActivityFlagNotEqualTo(Integer value) {
            addCriterion("activity_flag <>", value, "activityFlag");
            return (Criteria) this;
        }

        public Criteria andActivityFlagGreaterThan(Integer value) {
            addCriterion("activity_flag >", value, "activityFlag");
            return (Criteria) this;
        }

        public Criteria andActivityFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("activity_flag >=", value, "activityFlag");
            return (Criteria) this;
        }

        public Criteria andActivityFlagLessThan(Integer value) {
            addCriterion("activity_flag <", value, "activityFlag");
            return (Criteria) this;
        }

        public Criteria andActivityFlagLessThanOrEqualTo(Integer value) {
            addCriterion("activity_flag <=", value, "activityFlag");
            return (Criteria) this;
        }

        public Criteria andActivityFlagIn(List<Integer> values) {
            addCriterion("activity_flag in", values, "activityFlag");
            return (Criteria) this;
        }

        public Criteria andActivityFlagNotIn(List<Integer> values) {
            addCriterion("activity_flag not in", values, "activityFlag");
            return (Criteria) this;
        }

        public Criteria andActivityFlagBetween(Integer value1, Integer value2) {
            addCriterion("activity_flag between", value1, value2, "activityFlag");
            return (Criteria) this;
        }

        public Criteria andActivityFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("activity_flag not between", value1, value2, "activityFlag");
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

        public Criteria andTenderUserAttributeIsNull() {
            addCriterion("tender_user_attribute is null");
            return (Criteria) this;
        }

        public Criteria andTenderUserAttributeIsNotNull() {
            addCriterion("tender_user_attribute is not null");
            return (Criteria) this;
        }

        public Criteria andTenderUserAttributeEqualTo(Integer value) {
            addCriterion("tender_user_attribute =", value, "tenderUserAttribute");
            return (Criteria) this;
        }

        public Criteria andTenderUserAttributeNotEqualTo(Integer value) {
            addCriterion("tender_user_attribute <>", value, "tenderUserAttribute");
            return (Criteria) this;
        }

        public Criteria andTenderUserAttributeGreaterThan(Integer value) {
            addCriterion("tender_user_attribute >", value, "tenderUserAttribute");
            return (Criteria) this;
        }

        public Criteria andTenderUserAttributeGreaterThanOrEqualTo(Integer value) {
            addCriterion("tender_user_attribute >=", value, "tenderUserAttribute");
            return (Criteria) this;
        }

        public Criteria andTenderUserAttributeLessThan(Integer value) {
            addCriterion("tender_user_attribute <", value, "tenderUserAttribute");
            return (Criteria) this;
        }

        public Criteria andTenderUserAttributeLessThanOrEqualTo(Integer value) {
            addCriterion("tender_user_attribute <=", value, "tenderUserAttribute");
            return (Criteria) this;
        }

        public Criteria andTenderUserAttributeIn(List<Integer> values) {
            addCriterion("tender_user_attribute in", values, "tenderUserAttribute");
            return (Criteria) this;
        }

        public Criteria andTenderUserAttributeNotIn(List<Integer> values) {
            addCriterion("tender_user_attribute not in", values, "tenderUserAttribute");
            return (Criteria) this;
        }

        public Criteria andTenderUserAttributeBetween(Integer value1, Integer value2) {
            addCriterion("tender_user_attribute between", value1, value2, "tenderUserAttribute");
            return (Criteria) this;
        }

        public Criteria andTenderUserAttributeNotBetween(Integer value1, Integer value2) {
            addCriterion("tender_user_attribute not between", value1, value2, "tenderUserAttribute");
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

        public Criteria andLoanOrdidIsNull() {
            addCriterion("loan_ordid is null");
            return (Criteria) this;
        }

        public Criteria andLoanOrdidIsNotNull() {
            addCriterion("loan_ordid is not null");
            return (Criteria) this;
        }

        public Criteria andLoanOrdidEqualTo(String value) {
            addCriterion("loan_ordid =", value, "loanOrdid");
            return (Criteria) this;
        }

        public Criteria andLoanOrdidNotEqualTo(String value) {
            addCriterion("loan_ordid <>", value, "loanOrdid");
            return (Criteria) this;
        }

        public Criteria andLoanOrdidGreaterThan(String value) {
            addCriterion("loan_ordid >", value, "loanOrdid");
            return (Criteria) this;
        }

        public Criteria andLoanOrdidGreaterThanOrEqualTo(String value) {
            addCriterion("loan_ordid >=", value, "loanOrdid");
            return (Criteria) this;
        }

        public Criteria andLoanOrdidLessThan(String value) {
            addCriterion("loan_ordid <", value, "loanOrdid");
            return (Criteria) this;
        }

        public Criteria andLoanOrdidLessThanOrEqualTo(String value) {
            addCriterion("loan_ordid <=", value, "loanOrdid");
            return (Criteria) this;
        }

        public Criteria andLoanOrdidLike(String value) {
            addCriterion("loan_ordid like", value, "loanOrdid");
            return (Criteria) this;
        }

        public Criteria andLoanOrdidNotLike(String value) {
            addCriterion("loan_ordid not like", value, "loanOrdid");
            return (Criteria) this;
        }

        public Criteria andLoanOrdidIn(List<String> values) {
            addCriterion("loan_ordid in", values, "loanOrdid");
            return (Criteria) this;
        }

        public Criteria andLoanOrdidNotIn(List<String> values) {
            addCriterion("loan_ordid not in", values, "loanOrdid");
            return (Criteria) this;
        }

        public Criteria andLoanOrdidBetween(String value1, String value2) {
            addCriterion("loan_ordid between", value1, value2, "loanOrdid");
            return (Criteria) this;
        }

        public Criteria andLoanOrdidNotBetween(String value1, String value2) {
            addCriterion("loan_ordid not between", value1, value2, "loanOrdid");
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

        public Criteria andTenderUserNameIsNull() {
            addCriterion("tender_user_name is null");
            return (Criteria) this;
        }

        public Criteria andTenderUserNameIsNotNull() {
            addCriterion("tender_user_name is not null");
            return (Criteria) this;
        }

        public Criteria andTenderUserNameEqualTo(String value) {
            addCriterion("tender_user_name =", value, "tenderUserName");
            return (Criteria) this;
        }

        public Criteria andTenderUserNameNotEqualTo(String value) {
            addCriterion("tender_user_name <>", value, "tenderUserName");
            return (Criteria) this;
        }

        public Criteria andTenderUserNameGreaterThan(String value) {
            addCriterion("tender_user_name >", value, "tenderUserName");
            return (Criteria) this;
        }

        public Criteria andTenderUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("tender_user_name >=", value, "tenderUserName");
            return (Criteria) this;
        }

        public Criteria andTenderUserNameLessThan(String value) {
            addCriterion("tender_user_name <", value, "tenderUserName");
            return (Criteria) this;
        }

        public Criteria andTenderUserNameLessThanOrEqualTo(String value) {
            addCriterion("tender_user_name <=", value, "tenderUserName");
            return (Criteria) this;
        }

        public Criteria andTenderUserNameLike(String value) {
            addCriterion("tender_user_name like", value, "tenderUserName");
            return (Criteria) this;
        }

        public Criteria andTenderUserNameNotLike(String value) {
            addCriterion("tender_user_name not like", value, "tenderUserName");
            return (Criteria) this;
        }

        public Criteria andTenderUserNameIn(List<String> values) {
            addCriterion("tender_user_name in", values, "tenderUserName");
            return (Criteria) this;
        }

        public Criteria andTenderUserNameNotIn(List<String> values) {
            addCriterion("tender_user_name not in", values, "tenderUserName");
            return (Criteria) this;
        }

        public Criteria andTenderUserNameBetween(String value1, String value2) {
            addCriterion("tender_user_name between", value1, value2, "tenderUserName");
            return (Criteria) this;
        }

        public Criteria andTenderUserNameNotBetween(String value1, String value2) {
            addCriterion("tender_user_name not between", value1, value2, "tenderUserName");
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