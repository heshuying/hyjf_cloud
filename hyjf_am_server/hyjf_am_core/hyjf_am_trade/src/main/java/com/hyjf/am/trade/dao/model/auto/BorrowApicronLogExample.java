package com.hyjf.am.trade.dao.model.auto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BorrowApicronLogExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public BorrowApicronLogExample() {
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

        public Criteria andPeriodNowIsNull() {
            addCriterion("period_now is null");
            return (Criteria) this;
        }

        public Criteria andPeriodNowIsNotNull() {
            addCriterion("period_now is not null");
            return (Criteria) this;
        }

        public Criteria andPeriodNowEqualTo(Integer value) {
            addCriterion("period_now =", value, "periodNow");
            return (Criteria) this;
        }

        public Criteria andPeriodNowNotEqualTo(Integer value) {
            addCriterion("period_now <>", value, "periodNow");
            return (Criteria) this;
        }

        public Criteria andPeriodNowGreaterThan(Integer value) {
            addCriterion("period_now >", value, "periodNow");
            return (Criteria) this;
        }

        public Criteria andPeriodNowGreaterThanOrEqualTo(Integer value) {
            addCriterion("period_now >=", value, "periodNow");
            return (Criteria) this;
        }

        public Criteria andPeriodNowLessThan(Integer value) {
            addCriterion("period_now <", value, "periodNow");
            return (Criteria) this;
        }

        public Criteria andPeriodNowLessThanOrEqualTo(Integer value) {
            addCriterion("period_now <=", value, "periodNow");
            return (Criteria) this;
        }

        public Criteria andPeriodNowIn(List<Integer> values) {
            addCriterion("period_now in", values, "periodNow");
            return (Criteria) this;
        }

        public Criteria andPeriodNowNotIn(List<Integer> values) {
            addCriterion("period_now not in", values, "periodNow");
            return (Criteria) this;
        }

        public Criteria andPeriodNowBetween(Integer value1, Integer value2) {
            addCriterion("period_now between", value1, value2, "periodNow");
            return (Criteria) this;
        }

        public Criteria andPeriodNowNotBetween(Integer value1, Integer value2) {
            addCriterion("period_now not between", value1, value2, "periodNow");
            return (Criteria) this;
        }

        public Criteria andIsRepayOrgFlagIsNull() {
            addCriterion("is_repay_org_flag is null");
            return (Criteria) this;
        }

        public Criteria andIsRepayOrgFlagIsNotNull() {
            addCriterion("is_repay_org_flag is not null");
            return (Criteria) this;
        }

        public Criteria andIsRepayOrgFlagEqualTo(Integer value) {
            addCriterion("is_repay_org_flag =", value, "isRepayOrgFlag");
            return (Criteria) this;
        }

        public Criteria andIsRepayOrgFlagNotEqualTo(Integer value) {
            addCriterion("is_repay_org_flag <>", value, "isRepayOrgFlag");
            return (Criteria) this;
        }

        public Criteria andIsRepayOrgFlagGreaterThan(Integer value) {
            addCriterion("is_repay_org_flag >", value, "isRepayOrgFlag");
            return (Criteria) this;
        }

        public Criteria andIsRepayOrgFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_repay_org_flag >=", value, "isRepayOrgFlag");
            return (Criteria) this;
        }

        public Criteria andIsRepayOrgFlagLessThan(Integer value) {
            addCriterion("is_repay_org_flag <", value, "isRepayOrgFlag");
            return (Criteria) this;
        }

        public Criteria andIsRepayOrgFlagLessThanOrEqualTo(Integer value) {
            addCriterion("is_repay_org_flag <=", value, "isRepayOrgFlag");
            return (Criteria) this;
        }

        public Criteria andIsRepayOrgFlagIn(List<Integer> values) {
            addCriterion("is_repay_org_flag in", values, "isRepayOrgFlag");
            return (Criteria) this;
        }

        public Criteria andIsRepayOrgFlagNotIn(List<Integer> values) {
            addCriterion("is_repay_org_flag not in", values, "isRepayOrgFlag");
            return (Criteria) this;
        }

        public Criteria andIsRepayOrgFlagBetween(Integer value1, Integer value2) {
            addCriterion("is_repay_org_flag between", value1, value2, "isRepayOrgFlag");
            return (Criteria) this;
        }

        public Criteria andIsRepayOrgFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("is_repay_org_flag not between", value1, value2, "isRepayOrgFlag");
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

        public Criteria andBorrowAccountIsNull() {
            addCriterion("borrow_account is null");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountIsNotNull() {
            addCriterion("borrow_account is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountEqualTo(BigDecimal value) {
            addCriterion("borrow_account =", value, "borrowAccount");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountNotEqualTo(BigDecimal value) {
            addCriterion("borrow_account <>", value, "borrowAccount");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountGreaterThan(BigDecimal value) {
            addCriterion("borrow_account >", value, "borrowAccount");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("borrow_account >=", value, "borrowAccount");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountLessThan(BigDecimal value) {
            addCriterion("borrow_account <", value, "borrowAccount");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("borrow_account <=", value, "borrowAccount");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountIn(List<BigDecimal> values) {
            addCriterion("borrow_account in", values, "borrowAccount");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountNotIn(List<BigDecimal> values) {
            addCriterion("borrow_account not in", values, "borrowAccount");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("borrow_account between", value1, value2, "borrowAccount");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("borrow_account not between", value1, value2, "borrowAccount");
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

        public Criteria andBorrowPeriodEqualTo(Integer value) {
            addCriterion("borrow_period =", value, "borrowPeriod");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodNotEqualTo(Integer value) {
            addCriterion("borrow_period <>", value, "borrowPeriod");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodGreaterThan(Integer value) {
            addCriterion("borrow_period >", value, "borrowPeriod");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodGreaterThanOrEqualTo(Integer value) {
            addCriterion("borrow_period >=", value, "borrowPeriod");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodLessThan(Integer value) {
            addCriterion("borrow_period <", value, "borrowPeriod");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodLessThanOrEqualTo(Integer value) {
            addCriterion("borrow_period <=", value, "borrowPeriod");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodIn(List<Integer> values) {
            addCriterion("borrow_period in", values, "borrowPeriod");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodNotIn(List<Integer> values) {
            addCriterion("borrow_period not in", values, "borrowPeriod");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodBetween(Integer value1, Integer value2) {
            addCriterion("borrow_period between", value1, value2, "borrowPeriod");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodNotBetween(Integer value1, Integer value2) {
            addCriterion("borrow_period not between", value1, value2, "borrowPeriod");
            return (Criteria) this;
        }

        public Criteria andBatchNoIsNull() {
            addCriterion("batch_no is null");
            return (Criteria) this;
        }

        public Criteria andBatchNoIsNotNull() {
            addCriterion("batch_no is not null");
            return (Criteria) this;
        }

        public Criteria andBatchNoEqualTo(String value) {
            addCriterion("batch_no =", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoNotEqualTo(String value) {
            addCriterion("batch_no <>", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoGreaterThan(String value) {
            addCriterion("batch_no >", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoGreaterThanOrEqualTo(String value) {
            addCriterion("batch_no >=", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoLessThan(String value) {
            addCriterion("batch_no <", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoLessThanOrEqualTo(String value) {
            addCriterion("batch_no <=", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoLike(String value) {
            addCriterion("batch_no like", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoNotLike(String value) {
            addCriterion("batch_no not like", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoIn(List<String> values) {
            addCriterion("batch_no in", values, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoNotIn(List<String> values) {
            addCriterion("batch_no not in", values, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoBetween(String value1, String value2) {
            addCriterion("batch_no between", value1, value2, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoNotBetween(String value1, String value2) {
            addCriterion("batch_no not between", value1, value2, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchAmountIsNull() {
            addCriterion("batch_amount is null");
            return (Criteria) this;
        }

        public Criteria andBatchAmountIsNotNull() {
            addCriterion("batch_amount is not null");
            return (Criteria) this;
        }

        public Criteria andBatchAmountEqualTo(BigDecimal value) {
            addCriterion("batch_amount =", value, "batchAmount");
            return (Criteria) this;
        }

        public Criteria andBatchAmountNotEqualTo(BigDecimal value) {
            addCriterion("batch_amount <>", value, "batchAmount");
            return (Criteria) this;
        }

        public Criteria andBatchAmountGreaterThan(BigDecimal value) {
            addCriterion("batch_amount >", value, "batchAmount");
            return (Criteria) this;
        }

        public Criteria andBatchAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("batch_amount >=", value, "batchAmount");
            return (Criteria) this;
        }

        public Criteria andBatchAmountLessThan(BigDecimal value) {
            addCriterion("batch_amount <", value, "batchAmount");
            return (Criteria) this;
        }

        public Criteria andBatchAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("batch_amount <=", value, "batchAmount");
            return (Criteria) this;
        }

        public Criteria andBatchAmountIn(List<BigDecimal> values) {
            addCriterion("batch_amount in", values, "batchAmount");
            return (Criteria) this;
        }

        public Criteria andBatchAmountNotIn(List<BigDecimal> values) {
            addCriterion("batch_amount not in", values, "batchAmount");
            return (Criteria) this;
        }

        public Criteria andBatchAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("batch_amount between", value1, value2, "batchAmount");
            return (Criteria) this;
        }

        public Criteria andBatchAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("batch_amount not between", value1, value2, "batchAmount");
            return (Criteria) this;
        }

        public Criteria andBatchCountsIsNull() {
            addCriterion("batch_counts is null");
            return (Criteria) this;
        }

        public Criteria andBatchCountsIsNotNull() {
            addCriterion("batch_counts is not null");
            return (Criteria) this;
        }

        public Criteria andBatchCountsEqualTo(Integer value) {
            addCriterion("batch_counts =", value, "batchCounts");
            return (Criteria) this;
        }

        public Criteria andBatchCountsNotEqualTo(Integer value) {
            addCriterion("batch_counts <>", value, "batchCounts");
            return (Criteria) this;
        }

        public Criteria andBatchCountsGreaterThan(Integer value) {
            addCriterion("batch_counts >", value, "batchCounts");
            return (Criteria) this;
        }

        public Criteria andBatchCountsGreaterThanOrEqualTo(Integer value) {
            addCriterion("batch_counts >=", value, "batchCounts");
            return (Criteria) this;
        }

        public Criteria andBatchCountsLessThan(Integer value) {
            addCriterion("batch_counts <", value, "batchCounts");
            return (Criteria) this;
        }

        public Criteria andBatchCountsLessThanOrEqualTo(Integer value) {
            addCriterion("batch_counts <=", value, "batchCounts");
            return (Criteria) this;
        }

        public Criteria andBatchCountsIn(List<Integer> values) {
            addCriterion("batch_counts in", values, "batchCounts");
            return (Criteria) this;
        }

        public Criteria andBatchCountsNotIn(List<Integer> values) {
            addCriterion("batch_counts not in", values, "batchCounts");
            return (Criteria) this;
        }

        public Criteria andBatchCountsBetween(Integer value1, Integer value2) {
            addCriterion("batch_counts between", value1, value2, "batchCounts");
            return (Criteria) this;
        }

        public Criteria andBatchCountsNotBetween(Integer value1, Integer value2) {
            addCriterion("batch_counts not between", value1, value2, "batchCounts");
            return (Criteria) this;
        }

        public Criteria andBatchServiceFeeIsNull() {
            addCriterion("batch_service_fee is null");
            return (Criteria) this;
        }

        public Criteria andBatchServiceFeeIsNotNull() {
            addCriterion("batch_service_fee is not null");
            return (Criteria) this;
        }

        public Criteria andBatchServiceFeeEqualTo(BigDecimal value) {
            addCriterion("batch_service_fee =", value, "batchServiceFee");
            return (Criteria) this;
        }

        public Criteria andBatchServiceFeeNotEqualTo(BigDecimal value) {
            addCriterion("batch_service_fee <>", value, "batchServiceFee");
            return (Criteria) this;
        }

        public Criteria andBatchServiceFeeGreaterThan(BigDecimal value) {
            addCriterion("batch_service_fee >", value, "batchServiceFee");
            return (Criteria) this;
        }

        public Criteria andBatchServiceFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("batch_service_fee >=", value, "batchServiceFee");
            return (Criteria) this;
        }

        public Criteria andBatchServiceFeeLessThan(BigDecimal value) {
            addCriterion("batch_service_fee <", value, "batchServiceFee");
            return (Criteria) this;
        }

        public Criteria andBatchServiceFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("batch_service_fee <=", value, "batchServiceFee");
            return (Criteria) this;
        }

        public Criteria andBatchServiceFeeIn(List<BigDecimal> values) {
            addCriterion("batch_service_fee in", values, "batchServiceFee");
            return (Criteria) this;
        }

        public Criteria andBatchServiceFeeNotIn(List<BigDecimal> values) {
            addCriterion("batch_service_fee not in", values, "batchServiceFee");
            return (Criteria) this;
        }

        public Criteria andBatchServiceFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("batch_service_fee between", value1, value2, "batchServiceFee");
            return (Criteria) this;
        }

        public Criteria andBatchServiceFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("batch_service_fee not between", value1, value2, "batchServiceFee");
            return (Criteria) this;
        }

        public Criteria andTxAmountIsNull() {
            addCriterion("tx_amount is null");
            return (Criteria) this;
        }

        public Criteria andTxAmountIsNotNull() {
            addCriterion("tx_amount is not null");
            return (Criteria) this;
        }

        public Criteria andTxAmountEqualTo(BigDecimal value) {
            addCriterion("tx_amount =", value, "txAmount");
            return (Criteria) this;
        }

        public Criteria andTxAmountNotEqualTo(BigDecimal value) {
            addCriterion("tx_amount <>", value, "txAmount");
            return (Criteria) this;
        }

        public Criteria andTxAmountGreaterThan(BigDecimal value) {
            addCriterion("tx_amount >", value, "txAmount");
            return (Criteria) this;
        }

        public Criteria andTxAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("tx_amount >=", value, "txAmount");
            return (Criteria) this;
        }

        public Criteria andTxAmountLessThan(BigDecimal value) {
            addCriterion("tx_amount <", value, "txAmount");
            return (Criteria) this;
        }

        public Criteria andTxAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("tx_amount <=", value, "txAmount");
            return (Criteria) this;
        }

        public Criteria andTxAmountIn(List<BigDecimal> values) {
            addCriterion("tx_amount in", values, "txAmount");
            return (Criteria) this;
        }

        public Criteria andTxAmountNotIn(List<BigDecimal> values) {
            addCriterion("tx_amount not in", values, "txAmount");
            return (Criteria) this;
        }

        public Criteria andTxAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("tx_amount between", value1, value2, "txAmount");
            return (Criteria) this;
        }

        public Criteria andTxAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("tx_amount not between", value1, value2, "txAmount");
            return (Criteria) this;
        }

        public Criteria andTxCountsIsNull() {
            addCriterion("tx_counts is null");
            return (Criteria) this;
        }

        public Criteria andTxCountsIsNotNull() {
            addCriterion("tx_counts is not null");
            return (Criteria) this;
        }

        public Criteria andTxCountsEqualTo(Integer value) {
            addCriterion("tx_counts =", value, "txCounts");
            return (Criteria) this;
        }

        public Criteria andTxCountsNotEqualTo(Integer value) {
            addCriterion("tx_counts <>", value, "txCounts");
            return (Criteria) this;
        }

        public Criteria andTxCountsGreaterThan(Integer value) {
            addCriterion("tx_counts >", value, "txCounts");
            return (Criteria) this;
        }

        public Criteria andTxCountsGreaterThanOrEqualTo(Integer value) {
            addCriterion("tx_counts >=", value, "txCounts");
            return (Criteria) this;
        }

        public Criteria andTxCountsLessThan(Integer value) {
            addCriterion("tx_counts <", value, "txCounts");
            return (Criteria) this;
        }

        public Criteria andTxCountsLessThanOrEqualTo(Integer value) {
            addCriterion("tx_counts <=", value, "txCounts");
            return (Criteria) this;
        }

        public Criteria andTxCountsIn(List<Integer> values) {
            addCriterion("tx_counts in", values, "txCounts");
            return (Criteria) this;
        }

        public Criteria andTxCountsNotIn(List<Integer> values) {
            addCriterion("tx_counts not in", values, "txCounts");
            return (Criteria) this;
        }

        public Criteria andTxCountsBetween(Integer value1, Integer value2) {
            addCriterion("tx_counts between", value1, value2, "txCounts");
            return (Criteria) this;
        }

        public Criteria andTxCountsNotBetween(Integer value1, Integer value2) {
            addCriterion("tx_counts not between", value1, value2, "txCounts");
            return (Criteria) this;
        }

        public Criteria andSucCountsIsNull() {
            addCriterion("suc_counts is null");
            return (Criteria) this;
        }

        public Criteria andSucCountsIsNotNull() {
            addCriterion("suc_counts is not null");
            return (Criteria) this;
        }

        public Criteria andSucCountsEqualTo(Integer value) {
            addCriterion("suc_counts =", value, "sucCounts");
            return (Criteria) this;
        }

        public Criteria andSucCountsNotEqualTo(Integer value) {
            addCriterion("suc_counts <>", value, "sucCounts");
            return (Criteria) this;
        }

        public Criteria andSucCountsGreaterThan(Integer value) {
            addCriterion("suc_counts >", value, "sucCounts");
            return (Criteria) this;
        }

        public Criteria andSucCountsGreaterThanOrEqualTo(Integer value) {
            addCriterion("suc_counts >=", value, "sucCounts");
            return (Criteria) this;
        }

        public Criteria andSucCountsLessThan(Integer value) {
            addCriterion("suc_counts <", value, "sucCounts");
            return (Criteria) this;
        }

        public Criteria andSucCountsLessThanOrEqualTo(Integer value) {
            addCriterion("suc_counts <=", value, "sucCounts");
            return (Criteria) this;
        }

        public Criteria andSucCountsIn(List<Integer> values) {
            addCriterion("suc_counts in", values, "sucCounts");
            return (Criteria) this;
        }

        public Criteria andSucCountsNotIn(List<Integer> values) {
            addCriterion("suc_counts not in", values, "sucCounts");
            return (Criteria) this;
        }

        public Criteria andSucCountsBetween(Integer value1, Integer value2) {
            addCriterion("suc_counts between", value1, value2, "sucCounts");
            return (Criteria) this;
        }

        public Criteria andSucCountsNotBetween(Integer value1, Integer value2) {
            addCriterion("suc_counts not between", value1, value2, "sucCounts");
            return (Criteria) this;
        }

        public Criteria andSucAmountIsNull() {
            addCriterion("suc_amount is null");
            return (Criteria) this;
        }

        public Criteria andSucAmountIsNotNull() {
            addCriterion("suc_amount is not null");
            return (Criteria) this;
        }

        public Criteria andSucAmountEqualTo(BigDecimal value) {
            addCriterion("suc_amount =", value, "sucAmount");
            return (Criteria) this;
        }

        public Criteria andSucAmountNotEqualTo(BigDecimal value) {
            addCriterion("suc_amount <>", value, "sucAmount");
            return (Criteria) this;
        }

        public Criteria andSucAmountGreaterThan(BigDecimal value) {
            addCriterion("suc_amount >", value, "sucAmount");
            return (Criteria) this;
        }

        public Criteria andSucAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("suc_amount >=", value, "sucAmount");
            return (Criteria) this;
        }

        public Criteria andSucAmountLessThan(BigDecimal value) {
            addCriterion("suc_amount <", value, "sucAmount");
            return (Criteria) this;
        }

        public Criteria andSucAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("suc_amount <=", value, "sucAmount");
            return (Criteria) this;
        }

        public Criteria andSucAmountIn(List<BigDecimal> values) {
            addCriterion("suc_amount in", values, "sucAmount");
            return (Criteria) this;
        }

        public Criteria andSucAmountNotIn(List<BigDecimal> values) {
            addCriterion("suc_amount not in", values, "sucAmount");
            return (Criteria) this;
        }

        public Criteria andSucAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("suc_amount between", value1, value2, "sucAmount");
            return (Criteria) this;
        }

        public Criteria andSucAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("suc_amount not between", value1, value2, "sucAmount");
            return (Criteria) this;
        }

        public Criteria andFailCountsIsNull() {
            addCriterion("fail_counts is null");
            return (Criteria) this;
        }

        public Criteria andFailCountsIsNotNull() {
            addCriterion("fail_counts is not null");
            return (Criteria) this;
        }

        public Criteria andFailCountsEqualTo(Integer value) {
            addCriterion("fail_counts =", value, "failCounts");
            return (Criteria) this;
        }

        public Criteria andFailCountsNotEqualTo(Integer value) {
            addCriterion("fail_counts <>", value, "failCounts");
            return (Criteria) this;
        }

        public Criteria andFailCountsGreaterThan(Integer value) {
            addCriterion("fail_counts >", value, "failCounts");
            return (Criteria) this;
        }

        public Criteria andFailCountsGreaterThanOrEqualTo(Integer value) {
            addCriterion("fail_counts >=", value, "failCounts");
            return (Criteria) this;
        }

        public Criteria andFailCountsLessThan(Integer value) {
            addCriterion("fail_counts <", value, "failCounts");
            return (Criteria) this;
        }

        public Criteria andFailCountsLessThanOrEqualTo(Integer value) {
            addCriterion("fail_counts <=", value, "failCounts");
            return (Criteria) this;
        }

        public Criteria andFailCountsIn(List<Integer> values) {
            addCriterion("fail_counts in", values, "failCounts");
            return (Criteria) this;
        }

        public Criteria andFailCountsNotIn(List<Integer> values) {
            addCriterion("fail_counts not in", values, "failCounts");
            return (Criteria) this;
        }

        public Criteria andFailCountsBetween(Integer value1, Integer value2) {
            addCriterion("fail_counts between", value1, value2, "failCounts");
            return (Criteria) this;
        }

        public Criteria andFailCountsNotBetween(Integer value1, Integer value2) {
            addCriterion("fail_counts not between", value1, value2, "failCounts");
            return (Criteria) this;
        }

        public Criteria andFailAmountIsNull() {
            addCriterion("fail_amount is null");
            return (Criteria) this;
        }

        public Criteria andFailAmountIsNotNull() {
            addCriterion("fail_amount is not null");
            return (Criteria) this;
        }

        public Criteria andFailAmountEqualTo(BigDecimal value) {
            addCriterion("fail_amount =", value, "failAmount");
            return (Criteria) this;
        }

        public Criteria andFailAmountNotEqualTo(BigDecimal value) {
            addCriterion("fail_amount <>", value, "failAmount");
            return (Criteria) this;
        }

        public Criteria andFailAmountGreaterThan(BigDecimal value) {
            addCriterion("fail_amount >", value, "failAmount");
            return (Criteria) this;
        }

        public Criteria andFailAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("fail_amount >=", value, "failAmount");
            return (Criteria) this;
        }

        public Criteria andFailAmountLessThan(BigDecimal value) {
            addCriterion("fail_amount <", value, "failAmount");
            return (Criteria) this;
        }

        public Criteria andFailAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("fail_amount <=", value, "failAmount");
            return (Criteria) this;
        }

        public Criteria andFailAmountIn(List<BigDecimal> values) {
            addCriterion("fail_amount in", values, "failAmount");
            return (Criteria) this;
        }

        public Criteria andFailAmountNotIn(List<BigDecimal> values) {
            addCriterion("fail_amount not in", values, "failAmount");
            return (Criteria) this;
        }

        public Criteria andFailAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("fail_amount between", value1, value2, "failAmount");
            return (Criteria) this;
        }

        public Criteria andFailAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("fail_amount not between", value1, value2, "failAmount");
            return (Criteria) this;
        }

        public Criteria andServiceFeeIsNull() {
            addCriterion("service_fee is null");
            return (Criteria) this;
        }

        public Criteria andServiceFeeIsNotNull() {
            addCriterion("service_fee is not null");
            return (Criteria) this;
        }

        public Criteria andServiceFeeEqualTo(BigDecimal value) {
            addCriterion("service_fee =", value, "serviceFee");
            return (Criteria) this;
        }

        public Criteria andServiceFeeNotEqualTo(BigDecimal value) {
            addCriterion("service_fee <>", value, "serviceFee");
            return (Criteria) this;
        }

        public Criteria andServiceFeeGreaterThan(BigDecimal value) {
            addCriterion("service_fee >", value, "serviceFee");
            return (Criteria) this;
        }

        public Criteria andServiceFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("service_fee >=", value, "serviceFee");
            return (Criteria) this;
        }

        public Criteria andServiceFeeLessThan(BigDecimal value) {
            addCriterion("service_fee <", value, "serviceFee");
            return (Criteria) this;
        }

        public Criteria andServiceFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("service_fee <=", value, "serviceFee");
            return (Criteria) this;
        }

        public Criteria andServiceFeeIn(List<BigDecimal> values) {
            addCriterion("service_fee in", values, "serviceFee");
            return (Criteria) this;
        }

        public Criteria andServiceFeeNotIn(List<BigDecimal> values) {
            addCriterion("service_fee not in", values, "serviceFee");
            return (Criteria) this;
        }

        public Criteria andServiceFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("service_fee between", value1, value2, "serviceFee");
            return (Criteria) this;
        }

        public Criteria andServiceFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("service_fee not between", value1, value2, "serviceFee");
            return (Criteria) this;
        }

        public Criteria andTxDateIsNull() {
            addCriterion("tx_date is null");
            return (Criteria) this;
        }

        public Criteria andTxDateIsNotNull() {
            addCriterion("tx_date is not null");
            return (Criteria) this;
        }

        public Criteria andTxDateEqualTo(Integer value) {
            addCriterion("tx_date =", value, "txDate");
            return (Criteria) this;
        }

        public Criteria andTxDateNotEqualTo(Integer value) {
            addCriterion("tx_date <>", value, "txDate");
            return (Criteria) this;
        }

        public Criteria andTxDateGreaterThan(Integer value) {
            addCriterion("tx_date >", value, "txDate");
            return (Criteria) this;
        }

        public Criteria andTxDateGreaterThanOrEqualTo(Integer value) {
            addCriterion("tx_date >=", value, "txDate");
            return (Criteria) this;
        }

        public Criteria andTxDateLessThan(Integer value) {
            addCriterion("tx_date <", value, "txDate");
            return (Criteria) this;
        }

        public Criteria andTxDateLessThanOrEqualTo(Integer value) {
            addCriterion("tx_date <=", value, "txDate");
            return (Criteria) this;
        }

        public Criteria andTxDateIn(List<Integer> values) {
            addCriterion("tx_date in", values, "txDate");
            return (Criteria) this;
        }

        public Criteria andTxDateNotIn(List<Integer> values) {
            addCriterion("tx_date not in", values, "txDate");
            return (Criteria) this;
        }

        public Criteria andTxDateBetween(Integer value1, Integer value2) {
            addCriterion("tx_date between", value1, value2, "txDate");
            return (Criteria) this;
        }

        public Criteria andTxDateNotBetween(Integer value1, Integer value2) {
            addCriterion("tx_date not between", value1, value2, "txDate");
            return (Criteria) this;
        }

        public Criteria andTxTimeIsNull() {
            addCriterion("tx_time is null");
            return (Criteria) this;
        }

        public Criteria andTxTimeIsNotNull() {
            addCriterion("tx_time is not null");
            return (Criteria) this;
        }

        public Criteria andTxTimeEqualTo(Integer value) {
            addCriterion("tx_time =", value, "txTime");
            return (Criteria) this;
        }

        public Criteria andTxTimeNotEqualTo(Integer value) {
            addCriterion("tx_time <>", value, "txTime");
            return (Criteria) this;
        }

        public Criteria andTxTimeGreaterThan(Integer value) {
            addCriterion("tx_time >", value, "txTime");
            return (Criteria) this;
        }

        public Criteria andTxTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("tx_time >=", value, "txTime");
            return (Criteria) this;
        }

        public Criteria andTxTimeLessThan(Integer value) {
            addCriterion("tx_time <", value, "txTime");
            return (Criteria) this;
        }

        public Criteria andTxTimeLessThanOrEqualTo(Integer value) {
            addCriterion("tx_time <=", value, "txTime");
            return (Criteria) this;
        }

        public Criteria andTxTimeIn(List<Integer> values) {
            addCriterion("tx_time in", values, "txTime");
            return (Criteria) this;
        }

        public Criteria andTxTimeNotIn(List<Integer> values) {
            addCriterion("tx_time not in", values, "txTime");
            return (Criteria) this;
        }

        public Criteria andTxTimeBetween(Integer value1, Integer value2) {
            addCriterion("tx_time between", value1, value2, "txTime");
            return (Criteria) this;
        }

        public Criteria andTxTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("tx_time not between", value1, value2, "txTime");
            return (Criteria) this;
        }

        public Criteria andSeqNoIsNull() {
            addCriterion("seq_no is null");
            return (Criteria) this;
        }

        public Criteria andSeqNoIsNotNull() {
            addCriterion("seq_no is not null");
            return (Criteria) this;
        }

        public Criteria andSeqNoEqualTo(Integer value) {
            addCriterion("seq_no =", value, "seqNo");
            return (Criteria) this;
        }

        public Criteria andSeqNoNotEqualTo(Integer value) {
            addCriterion("seq_no <>", value, "seqNo");
            return (Criteria) this;
        }

        public Criteria andSeqNoGreaterThan(Integer value) {
            addCriterion("seq_no >", value, "seqNo");
            return (Criteria) this;
        }

        public Criteria andSeqNoGreaterThanOrEqualTo(Integer value) {
            addCriterion("seq_no >=", value, "seqNo");
            return (Criteria) this;
        }

        public Criteria andSeqNoLessThan(Integer value) {
            addCriterion("seq_no <", value, "seqNo");
            return (Criteria) this;
        }

        public Criteria andSeqNoLessThanOrEqualTo(Integer value) {
            addCriterion("seq_no <=", value, "seqNo");
            return (Criteria) this;
        }

        public Criteria andSeqNoIn(List<Integer> values) {
            addCriterion("seq_no in", values, "seqNo");
            return (Criteria) this;
        }

        public Criteria andSeqNoNotIn(List<Integer> values) {
            addCriterion("seq_no not in", values, "seqNo");
            return (Criteria) this;
        }

        public Criteria andSeqNoBetween(Integer value1, Integer value2) {
            addCriterion("seq_no between", value1, value2, "seqNo");
            return (Criteria) this;
        }

        public Criteria andSeqNoNotBetween(Integer value1, Integer value2) {
            addCriterion("seq_no not between", value1, value2, "seqNo");
            return (Criteria) this;
        }

        public Criteria andBankSeqNoIsNull() {
            addCriterion("bank_seq_no is null");
            return (Criteria) this;
        }

        public Criteria andBankSeqNoIsNotNull() {
            addCriterion("bank_seq_no is not null");
            return (Criteria) this;
        }

        public Criteria andBankSeqNoEqualTo(String value) {
            addCriterion("bank_seq_no =", value, "bankSeqNo");
            return (Criteria) this;
        }

        public Criteria andBankSeqNoNotEqualTo(String value) {
            addCriterion("bank_seq_no <>", value, "bankSeqNo");
            return (Criteria) this;
        }

        public Criteria andBankSeqNoGreaterThan(String value) {
            addCriterion("bank_seq_no >", value, "bankSeqNo");
            return (Criteria) this;
        }

        public Criteria andBankSeqNoGreaterThanOrEqualTo(String value) {
            addCriterion("bank_seq_no >=", value, "bankSeqNo");
            return (Criteria) this;
        }

        public Criteria andBankSeqNoLessThan(String value) {
            addCriterion("bank_seq_no <", value, "bankSeqNo");
            return (Criteria) this;
        }

        public Criteria andBankSeqNoLessThanOrEqualTo(String value) {
            addCriterion("bank_seq_no <=", value, "bankSeqNo");
            return (Criteria) this;
        }

        public Criteria andBankSeqNoLike(String value) {
            addCriterion("bank_seq_no like", value, "bankSeqNo");
            return (Criteria) this;
        }

        public Criteria andBankSeqNoNotLike(String value) {
            addCriterion("bank_seq_no not like", value, "bankSeqNo");
            return (Criteria) this;
        }

        public Criteria andBankSeqNoIn(List<String> values) {
            addCriterion("bank_seq_no in", values, "bankSeqNo");
            return (Criteria) this;
        }

        public Criteria andBankSeqNoNotIn(List<String> values) {
            addCriterion("bank_seq_no not in", values, "bankSeqNo");
            return (Criteria) this;
        }

        public Criteria andBankSeqNoBetween(String value1, String value2) {
            addCriterion("bank_seq_no between", value1, value2, "bankSeqNo");
            return (Criteria) this;
        }

        public Criteria andBankSeqNoNotBetween(String value1, String value2) {
            addCriterion("bank_seq_no not between", value1, value2, "bankSeqNo");
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

        public Criteria andOrdidIsNull() {
            addCriterion("ordid is null");
            return (Criteria) this;
        }

        public Criteria andOrdidIsNotNull() {
            addCriterion("ordid is not null");
            return (Criteria) this;
        }

        public Criteria andOrdidEqualTo(String value) {
            addCriterion("ordid =", value, "ordid");
            return (Criteria) this;
        }

        public Criteria andOrdidNotEqualTo(String value) {
            addCriterion("ordid <>", value, "ordid");
            return (Criteria) this;
        }

        public Criteria andOrdidGreaterThan(String value) {
            addCriterion("ordid >", value, "ordid");
            return (Criteria) this;
        }

        public Criteria andOrdidGreaterThanOrEqualTo(String value) {
            addCriterion("ordid >=", value, "ordid");
            return (Criteria) this;
        }

        public Criteria andOrdidLessThan(String value) {
            addCriterion("ordid <", value, "ordid");
            return (Criteria) this;
        }

        public Criteria andOrdidLessThanOrEqualTo(String value) {
            addCriterion("ordid <=", value, "ordid");
            return (Criteria) this;
        }

        public Criteria andOrdidLike(String value) {
            addCriterion("ordid like", value, "ordid");
            return (Criteria) this;
        }

        public Criteria andOrdidNotLike(String value) {
            addCriterion("ordid not like", value, "ordid");
            return (Criteria) this;
        }

        public Criteria andOrdidIn(List<String> values) {
            addCriterion("ordid in", values, "ordid");
            return (Criteria) this;
        }

        public Criteria andOrdidNotIn(List<String> values) {
            addCriterion("ordid not in", values, "ordid");
            return (Criteria) this;
        }

        public Criteria andOrdidBetween(String value1, String value2) {
            addCriterion("ordid between", value1, value2, "ordid");
            return (Criteria) this;
        }

        public Criteria andOrdidNotBetween(String value1, String value2) {
            addCriterion("ordid not between", value1, value2, "ordid");
            return (Criteria) this;
        }

        public Criteria andIsAllrepayIsNull() {
            addCriterion("is_allrepay is null");
            return (Criteria) this;
        }

        public Criteria andIsAllrepayIsNotNull() {
            addCriterion("is_allrepay is not null");
            return (Criteria) this;
        }

        public Criteria andIsAllrepayEqualTo(Integer value) {
            addCriterion("is_allrepay =", value, "isAllrepay");
            return (Criteria) this;
        }

        public Criteria andIsAllrepayNotEqualTo(Integer value) {
            addCriterion("is_allrepay <>", value, "isAllrepay");
            return (Criteria) this;
        }

        public Criteria andIsAllrepayGreaterThan(Integer value) {
            addCriterion("is_allrepay >", value, "isAllrepay");
            return (Criteria) this;
        }

        public Criteria andIsAllrepayGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_allrepay >=", value, "isAllrepay");
            return (Criteria) this;
        }

        public Criteria andIsAllrepayLessThan(Integer value) {
            addCriterion("is_allrepay <", value, "isAllrepay");
            return (Criteria) this;
        }

        public Criteria andIsAllrepayLessThanOrEqualTo(Integer value) {
            addCriterion("is_allrepay <=", value, "isAllrepay");
            return (Criteria) this;
        }

        public Criteria andIsAllrepayIn(List<Integer> values) {
            addCriterion("is_allrepay in", values, "isAllrepay");
            return (Criteria) this;
        }

        public Criteria andIsAllrepayNotIn(List<Integer> values) {
            addCriterion("is_allrepay not in", values, "isAllrepay");
            return (Criteria) this;
        }

        public Criteria andIsAllrepayBetween(Integer value1, Integer value2) {
            addCriterion("is_allrepay between", value1, value2, "isAllrepay");
            return (Criteria) this;
        }

        public Criteria andIsAllrepayNotBetween(Integer value1, Integer value2) {
            addCriterion("is_allrepay not between", value1, value2, "isAllrepay");
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