package com.hyjf.am.trade.dao.model.auto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BorrowExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public BorrowExample() {
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

        public Criteria andProjectTypeIsNull() {
            addCriterion("project_type is null");
            return (Criteria) this;
        }

        public Criteria andProjectTypeIsNotNull() {
            addCriterion("project_type is not null");
            return (Criteria) this;
        }

        public Criteria andProjectTypeEqualTo(Integer value) {
            addCriterion("project_type =", value, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeNotEqualTo(Integer value) {
            addCriterion("project_type <>", value, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeGreaterThan(Integer value) {
            addCriterion("project_type >", value, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("project_type >=", value, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeLessThan(Integer value) {
            addCriterion("project_type <", value, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeLessThanOrEqualTo(Integer value) {
            addCriterion("project_type <=", value, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeIn(List<Integer> values) {
            addCriterion("project_type in", values, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeNotIn(List<Integer> values) {
            addCriterion("project_type not in", values, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeBetween(Integer value1, Integer value2) {
            addCriterion("project_type between", value1, value2, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("project_type not between", value1, value2, "projectType");
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

        public Criteria andBorrowValidTimeIsNull() {
            addCriterion("borrow_valid_time is null");
            return (Criteria) this;
        }

        public Criteria andBorrowValidTimeIsNotNull() {
            addCriterion("borrow_valid_time is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowValidTimeEqualTo(Integer value) {
            addCriterion("borrow_valid_time =", value, "borrowValidTime");
            return (Criteria) this;
        }

        public Criteria andBorrowValidTimeNotEqualTo(Integer value) {
            addCriterion("borrow_valid_time <>", value, "borrowValidTime");
            return (Criteria) this;
        }

        public Criteria andBorrowValidTimeGreaterThan(Integer value) {
            addCriterion("borrow_valid_time >", value, "borrowValidTime");
            return (Criteria) this;
        }

        public Criteria andBorrowValidTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("borrow_valid_time >=", value, "borrowValidTime");
            return (Criteria) this;
        }

        public Criteria andBorrowValidTimeLessThan(Integer value) {
            addCriterion("borrow_valid_time <", value, "borrowValidTime");
            return (Criteria) this;
        }

        public Criteria andBorrowValidTimeLessThanOrEqualTo(Integer value) {
            addCriterion("borrow_valid_time <=", value, "borrowValidTime");
            return (Criteria) this;
        }

        public Criteria andBorrowValidTimeIn(List<Integer> values) {
            addCriterion("borrow_valid_time in", values, "borrowValidTime");
            return (Criteria) this;
        }

        public Criteria andBorrowValidTimeNotIn(List<Integer> values) {
            addCriterion("borrow_valid_time not in", values, "borrowValidTime");
            return (Criteria) this;
        }

        public Criteria andBorrowValidTimeBetween(Integer value1, Integer value2) {
            addCriterion("borrow_valid_time between", value1, value2, "borrowValidTime");
            return (Criteria) this;
        }

        public Criteria andBorrowValidTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("borrow_valid_time not between", value1, value2, "borrowValidTime");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleIsNull() {
            addCriterion("borrow_style is null");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleIsNotNull() {
            addCriterion("borrow_style is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleEqualTo(String value) {
            addCriterion("borrow_style =", value, "borrowStyle");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleNotEqualTo(String value) {
            addCriterion("borrow_style <>", value, "borrowStyle");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleGreaterThan(String value) {
            addCriterion("borrow_style >", value, "borrowStyle");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleGreaterThanOrEqualTo(String value) {
            addCriterion("borrow_style >=", value, "borrowStyle");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleLessThan(String value) {
            addCriterion("borrow_style <", value, "borrowStyle");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleLessThanOrEqualTo(String value) {
            addCriterion("borrow_style <=", value, "borrowStyle");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleLike(String value) {
            addCriterion("borrow_style like", value, "borrowStyle");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleNotLike(String value) {
            addCriterion("borrow_style not like", value, "borrowStyle");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleIn(List<String> values) {
            addCriterion("borrow_style in", values, "borrowStyle");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleNotIn(List<String> values) {
            addCriterion("borrow_style not in", values, "borrowStyle");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleBetween(String value1, String value2) {
            addCriterion("borrow_style between", value1, value2, "borrowStyle");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleNotBetween(String value1, String value2) {
            addCriterion("borrow_style not between", value1, value2, "borrowStyle");
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

        public Criteria andBorrowAprIsNull() {
            addCriterion("borrow_apr is null");
            return (Criteria) this;
        }

        public Criteria andBorrowAprIsNotNull() {
            addCriterion("borrow_apr is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowAprEqualTo(BigDecimal value) {
            addCriterion("borrow_apr =", value, "borrowApr");
            return (Criteria) this;
        }

        public Criteria andBorrowAprNotEqualTo(BigDecimal value) {
            addCriterion("borrow_apr <>", value, "borrowApr");
            return (Criteria) this;
        }

        public Criteria andBorrowAprGreaterThan(BigDecimal value) {
            addCriterion("borrow_apr >", value, "borrowApr");
            return (Criteria) this;
        }

        public Criteria andBorrowAprGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("borrow_apr >=", value, "borrowApr");
            return (Criteria) this;
        }

        public Criteria andBorrowAprLessThan(BigDecimal value) {
            addCriterion("borrow_apr <", value, "borrowApr");
            return (Criteria) this;
        }

        public Criteria andBorrowAprLessThanOrEqualTo(BigDecimal value) {
            addCriterion("borrow_apr <=", value, "borrowApr");
            return (Criteria) this;
        }

        public Criteria andBorrowAprIn(List<BigDecimal> values) {
            addCriterion("borrow_apr in", values, "borrowApr");
            return (Criteria) this;
        }

        public Criteria andBorrowAprNotIn(List<BigDecimal> values) {
            addCriterion("borrow_apr not in", values, "borrowApr");
            return (Criteria) this;
        }

        public Criteria andBorrowAprBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("borrow_apr between", value1, value2, "borrowApr");
            return (Criteria) this;
        }

        public Criteria andBorrowAprNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("borrow_apr not between", value1, value2, "borrowApr");
            return (Criteria) this;
        }

        public Criteria andLabelIdIsNull() {
            addCriterion("label_id is null");
            return (Criteria) this;
        }

        public Criteria andLabelIdIsNotNull() {
            addCriterion("label_id is not null");
            return (Criteria) this;
        }

        public Criteria andLabelIdEqualTo(Integer value) {
            addCriterion("label_id =", value, "labelId");
            return (Criteria) this;
        }

        public Criteria andLabelIdNotEqualTo(Integer value) {
            addCriterion("label_id <>", value, "labelId");
            return (Criteria) this;
        }

        public Criteria andLabelIdGreaterThan(Integer value) {
            addCriterion("label_id >", value, "labelId");
            return (Criteria) this;
        }

        public Criteria andLabelIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("label_id >=", value, "labelId");
            return (Criteria) this;
        }

        public Criteria andLabelIdLessThan(Integer value) {
            addCriterion("label_id <", value, "labelId");
            return (Criteria) this;
        }

        public Criteria andLabelIdLessThanOrEqualTo(Integer value) {
            addCriterion("label_id <=", value, "labelId");
            return (Criteria) this;
        }

        public Criteria andLabelIdIn(List<Integer> values) {
            addCriterion("label_id in", values, "labelId");
            return (Criteria) this;
        }

        public Criteria andLabelIdNotIn(List<Integer> values) {
            addCriterion("label_id not in", values, "labelId");
            return (Criteria) this;
        }

        public Criteria andLabelIdBetween(Integer value1, Integer value2) {
            addCriterion("label_id between", value1, value2, "labelId");
            return (Criteria) this;
        }

        public Criteria andLabelIdNotBetween(Integer value1, Integer value2) {
            addCriterion("label_id not between", value1, value2, "labelId");
            return (Criteria) this;
        }

        public Criteria andIsShowIsNull() {
            addCriterion("is_show is null");
            return (Criteria) this;
        }

        public Criteria andIsShowIsNotNull() {
            addCriterion("is_show is not null");
            return (Criteria) this;
        }

        public Criteria andIsShowEqualTo(Integer value) {
            addCriterion("is_show =", value, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowNotEqualTo(Integer value) {
            addCriterion("is_show <>", value, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowGreaterThan(Integer value) {
            addCriterion("is_show >", value, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_show >=", value, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowLessThan(Integer value) {
            addCriterion("is_show <", value, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowLessThanOrEqualTo(Integer value) {
            addCriterion("is_show <=", value, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowIn(List<Integer> values) {
            addCriterion("is_show in", values, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowNotIn(List<Integer> values) {
            addCriterion("is_show not in", values, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowBetween(Integer value1, Integer value2) {
            addCriterion("is_show between", value1, value2, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowNotBetween(Integer value1, Integer value2) {
            addCriterion("is_show not between", value1, value2, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsEngineUsedIsNull() {
            addCriterion("is_engine_used is null");
            return (Criteria) this;
        }

        public Criteria andIsEngineUsedIsNotNull() {
            addCriterion("is_engine_used is not null");
            return (Criteria) this;
        }

        public Criteria andIsEngineUsedEqualTo(Integer value) {
            addCriterion("is_engine_used =", value, "isEngineUsed");
            return (Criteria) this;
        }

        public Criteria andIsEngineUsedNotEqualTo(Integer value) {
            addCriterion("is_engine_used <>", value, "isEngineUsed");
            return (Criteria) this;
        }

        public Criteria andIsEngineUsedGreaterThan(Integer value) {
            addCriterion("is_engine_used >", value, "isEngineUsed");
            return (Criteria) this;
        }

        public Criteria andIsEngineUsedGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_engine_used >=", value, "isEngineUsed");
            return (Criteria) this;
        }

        public Criteria andIsEngineUsedLessThan(Integer value) {
            addCriterion("is_engine_used <", value, "isEngineUsed");
            return (Criteria) this;
        }

        public Criteria andIsEngineUsedLessThanOrEqualTo(Integer value) {
            addCriterion("is_engine_used <=", value, "isEngineUsed");
            return (Criteria) this;
        }

        public Criteria andIsEngineUsedIn(List<Integer> values) {
            addCriterion("is_engine_used in", values, "isEngineUsed");
            return (Criteria) this;
        }

        public Criteria andIsEngineUsedNotIn(List<Integer> values) {
            addCriterion("is_engine_used not in", values, "isEngineUsed");
            return (Criteria) this;
        }

        public Criteria andIsEngineUsedBetween(Integer value1, Integer value2) {
            addCriterion("is_engine_used between", value1, value2, "isEngineUsed");
            return (Criteria) this;
        }

        public Criteria andIsEngineUsedNotBetween(Integer value1, Integer value2) {
            addCriterion("is_engine_used not between", value1, value2, "isEngineUsed");
            return (Criteria) this;
        }

        public Criteria andIsInstallmentIsNull() {
            addCriterion("is_installment is null");
            return (Criteria) this;
        }

        public Criteria andIsInstallmentIsNotNull() {
            addCriterion("is_installment is not null");
            return (Criteria) this;
        }

        public Criteria andIsInstallmentEqualTo(Integer value) {
            addCriterion("is_installment =", value, "isInstallment");
            return (Criteria) this;
        }

        public Criteria andIsInstallmentNotEqualTo(Integer value) {
            addCriterion("is_installment <>", value, "isInstallment");
            return (Criteria) this;
        }

        public Criteria andIsInstallmentGreaterThan(Integer value) {
            addCriterion("is_installment >", value, "isInstallment");
            return (Criteria) this;
        }

        public Criteria andIsInstallmentGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_installment >=", value, "isInstallment");
            return (Criteria) this;
        }

        public Criteria andIsInstallmentLessThan(Integer value) {
            addCriterion("is_installment <", value, "isInstallment");
            return (Criteria) this;
        }

        public Criteria andIsInstallmentLessThanOrEqualTo(Integer value) {
            addCriterion("is_installment <=", value, "isInstallment");
            return (Criteria) this;
        }

        public Criteria andIsInstallmentIn(List<Integer> values) {
            addCriterion("is_installment in", values, "isInstallment");
            return (Criteria) this;
        }

        public Criteria andIsInstallmentNotIn(List<Integer> values) {
            addCriterion("is_installment not in", values, "isInstallment");
            return (Criteria) this;
        }

        public Criteria andIsInstallmentBetween(Integer value1, Integer value2) {
            addCriterion("is_installment between", value1, value2, "isInstallment");
            return (Criteria) this;
        }

        public Criteria andIsInstallmentNotBetween(Integer value1, Integer value2) {
            addCriterion("is_installment not between", value1, value2, "isInstallment");
            return (Criteria) this;
        }

        public Criteria andIsMonthIsNull() {
            addCriterion("is_month is null");
            return (Criteria) this;
        }

        public Criteria andIsMonthIsNotNull() {
            addCriterion("is_month is not null");
            return (Criteria) this;
        }

        public Criteria andIsMonthEqualTo(Integer value) {
            addCriterion("is_month =", value, "isMonth");
            return (Criteria) this;
        }

        public Criteria andIsMonthNotEqualTo(Integer value) {
            addCriterion("is_month <>", value, "isMonth");
            return (Criteria) this;
        }

        public Criteria andIsMonthGreaterThan(Integer value) {
            addCriterion("is_month >", value, "isMonth");
            return (Criteria) this;
        }

        public Criteria andIsMonthGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_month >=", value, "isMonth");
            return (Criteria) this;
        }

        public Criteria andIsMonthLessThan(Integer value) {
            addCriterion("is_month <", value, "isMonth");
            return (Criteria) this;
        }

        public Criteria andIsMonthLessThanOrEqualTo(Integer value) {
            addCriterion("is_month <=", value, "isMonth");
            return (Criteria) this;
        }

        public Criteria andIsMonthIn(List<Integer> values) {
            addCriterion("is_month in", values, "isMonth");
            return (Criteria) this;
        }

        public Criteria andIsMonthNotIn(List<Integer> values) {
            addCriterion("is_month not in", values, "isMonth");
            return (Criteria) this;
        }

        public Criteria andIsMonthBetween(Integer value1, Integer value2) {
            addCriterion("is_month between", value1, value2, "isMonth");
            return (Criteria) this;
        }

        public Criteria andIsMonthNotBetween(Integer value1, Integer value2) {
            addCriterion("is_month not between", value1, value2, "isMonth");
            return (Criteria) this;
        }

        public Criteria andRegistStatusIsNull() {
            addCriterion("regist_status is null");
            return (Criteria) this;
        }

        public Criteria andRegistStatusIsNotNull() {
            addCriterion("regist_status is not null");
            return (Criteria) this;
        }

        public Criteria andRegistStatusEqualTo(Integer value) {
            addCriterion("regist_status =", value, "registStatus");
            return (Criteria) this;
        }

        public Criteria andRegistStatusNotEqualTo(Integer value) {
            addCriterion("regist_status <>", value, "registStatus");
            return (Criteria) this;
        }

        public Criteria andRegistStatusGreaterThan(Integer value) {
            addCriterion("regist_status >", value, "registStatus");
            return (Criteria) this;
        }

        public Criteria andRegistStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("regist_status >=", value, "registStatus");
            return (Criteria) this;
        }

        public Criteria andRegistStatusLessThan(Integer value) {
            addCriterion("regist_status <", value, "registStatus");
            return (Criteria) this;
        }

        public Criteria andRegistStatusLessThanOrEqualTo(Integer value) {
            addCriterion("regist_status <=", value, "registStatus");
            return (Criteria) this;
        }

        public Criteria andRegistStatusIn(List<Integer> values) {
            addCriterion("regist_status in", values, "registStatus");
            return (Criteria) this;
        }

        public Criteria andRegistStatusNotIn(List<Integer> values) {
            addCriterion("regist_status not in", values, "registStatus");
            return (Criteria) this;
        }

        public Criteria andRegistStatusBetween(Integer value1, Integer value2) {
            addCriterion("regist_status between", value1, value2, "registStatus");
            return (Criteria) this;
        }

        public Criteria andRegistStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("regist_status not between", value1, value2, "registStatus");
            return (Criteria) this;
        }

        public Criteria andRegistTimeIsNull() {
            addCriterion("regist_time is null");
            return (Criteria) this;
        }

        public Criteria andRegistTimeIsNotNull() {
            addCriterion("regist_time is not null");
            return (Criteria) this;
        }

        public Criteria andRegistTimeEqualTo(Date value) {
            addCriterion("regist_time =", value, "registTime");
            return (Criteria) this;
        }

        public Criteria andRegistTimeNotEqualTo(Date value) {
            addCriterion("regist_time <>", value, "registTime");
            return (Criteria) this;
        }

        public Criteria andRegistTimeGreaterThan(Date value) {
            addCriterion("regist_time >", value, "registTime");
            return (Criteria) this;
        }

        public Criteria andRegistTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("regist_time >=", value, "registTime");
            return (Criteria) this;
        }

        public Criteria andRegistTimeLessThan(Date value) {
            addCriterion("regist_time <", value, "registTime");
            return (Criteria) this;
        }

        public Criteria andRegistTimeLessThanOrEqualTo(Date value) {
            addCriterion("regist_time <=", value, "registTime");
            return (Criteria) this;
        }

        public Criteria andRegistTimeIn(List<Date> values) {
            addCriterion("regist_time in", values, "registTime");
            return (Criteria) this;
        }

        public Criteria andRegistTimeNotIn(List<Date> values) {
            addCriterion("regist_time not in", values, "registTime");
            return (Criteria) this;
        }

        public Criteria andRegistTimeBetween(Date value1, Date value2) {
            addCriterion("regist_time between", value1, value2, "registTime");
            return (Criteria) this;
        }

        public Criteria andRegistTimeNotBetween(Date value1, Date value2) {
            addCriterion("regist_time not between", value1, value2, "registTime");
            return (Criteria) this;
        }

        public Criteria andRegistUserIdIsNull() {
            addCriterion("regist_user_id is null");
            return (Criteria) this;
        }

        public Criteria andRegistUserIdIsNotNull() {
            addCriterion("regist_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andRegistUserIdEqualTo(Integer value) {
            addCriterion("regist_user_id =", value, "registUserId");
            return (Criteria) this;
        }

        public Criteria andRegistUserIdNotEqualTo(Integer value) {
            addCriterion("regist_user_id <>", value, "registUserId");
            return (Criteria) this;
        }

        public Criteria andRegistUserIdGreaterThan(Integer value) {
            addCriterion("regist_user_id >", value, "registUserId");
            return (Criteria) this;
        }

        public Criteria andRegistUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("regist_user_id >=", value, "registUserId");
            return (Criteria) this;
        }

        public Criteria andRegistUserIdLessThan(Integer value) {
            addCriterion("regist_user_id <", value, "registUserId");
            return (Criteria) this;
        }

        public Criteria andRegistUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("regist_user_id <=", value, "registUserId");
            return (Criteria) this;
        }

        public Criteria andRegistUserIdIn(List<Integer> values) {
            addCriterion("regist_user_id in", values, "registUserId");
            return (Criteria) this;
        }

        public Criteria andRegistUserIdNotIn(List<Integer> values) {
            addCriterion("regist_user_id not in", values, "registUserId");
            return (Criteria) this;
        }

        public Criteria andRegistUserIdBetween(Integer value1, Integer value2) {
            addCriterion("regist_user_id between", value1, value2, "registUserId");
            return (Criteria) this;
        }

        public Criteria andRegistUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("regist_user_id not between", value1, value2, "registUserId");
            return (Criteria) this;
        }

        public Criteria andRegistUserNameIsNull() {
            addCriterion("regist_user_name is null");
            return (Criteria) this;
        }

        public Criteria andRegistUserNameIsNotNull() {
            addCriterion("regist_user_name is not null");
            return (Criteria) this;
        }

        public Criteria andRegistUserNameEqualTo(String value) {
            addCriterion("regist_user_name =", value, "registUserName");
            return (Criteria) this;
        }

        public Criteria andRegistUserNameNotEqualTo(String value) {
            addCriterion("regist_user_name <>", value, "registUserName");
            return (Criteria) this;
        }

        public Criteria andRegistUserNameGreaterThan(String value) {
            addCriterion("regist_user_name >", value, "registUserName");
            return (Criteria) this;
        }

        public Criteria andRegistUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("regist_user_name >=", value, "registUserName");
            return (Criteria) this;
        }

        public Criteria andRegistUserNameLessThan(String value) {
            addCriterion("regist_user_name <", value, "registUserName");
            return (Criteria) this;
        }

        public Criteria andRegistUserNameLessThanOrEqualTo(String value) {
            addCriterion("regist_user_name <=", value, "registUserName");
            return (Criteria) this;
        }

        public Criteria andRegistUserNameLike(String value) {
            addCriterion("regist_user_name like", value, "registUserName");
            return (Criteria) this;
        }

        public Criteria andRegistUserNameNotLike(String value) {
            addCriterion("regist_user_name not like", value, "registUserName");
            return (Criteria) this;
        }

        public Criteria andRegistUserNameIn(List<String> values) {
            addCriterion("regist_user_name in", values, "registUserName");
            return (Criteria) this;
        }

        public Criteria andRegistUserNameNotIn(List<String> values) {
            addCriterion("regist_user_name not in", values, "registUserName");
            return (Criteria) this;
        }

        public Criteria andRegistUserNameBetween(String value1, String value2) {
            addCriterion("regist_user_name between", value1, value2, "registUserName");
            return (Criteria) this;
        }

        public Criteria andRegistUserNameNotBetween(String value1, String value2) {
            addCriterion("regist_user_name not between", value1, value2, "registUserName");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusIsNull() {
            addCriterion("verify_status is null");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusIsNotNull() {
            addCriterion("verify_status is not null");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusEqualTo(Integer value) {
            addCriterion("verify_status =", value, "verifyStatus");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusNotEqualTo(Integer value) {
            addCriterion("verify_status <>", value, "verifyStatus");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusGreaterThan(Integer value) {
            addCriterion("verify_status >", value, "verifyStatus");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("verify_status >=", value, "verifyStatus");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusLessThan(Integer value) {
            addCriterion("verify_status <", value, "verifyStatus");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusLessThanOrEqualTo(Integer value) {
            addCriterion("verify_status <=", value, "verifyStatus");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusIn(List<Integer> values) {
            addCriterion("verify_status in", values, "verifyStatus");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusNotIn(List<Integer> values) {
            addCriterion("verify_status not in", values, "verifyStatus");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusBetween(Integer value1, Integer value2) {
            addCriterion("verify_status between", value1, value2, "verifyStatus");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("verify_status not between", value1, value2, "verifyStatus");
            return (Criteria) this;
        }

        public Criteria andVerifyOverTimeIsNull() {
            addCriterion("verify_over_time is null");
            return (Criteria) this;
        }

        public Criteria andVerifyOverTimeIsNotNull() {
            addCriterion("verify_over_time is not null");
            return (Criteria) this;
        }

        public Criteria andVerifyOverTimeEqualTo(Integer value) {
            addCriterion("verify_over_time =", value, "verifyOverTime");
            return (Criteria) this;
        }

        public Criteria andVerifyOverTimeNotEqualTo(Integer value) {
            addCriterion("verify_over_time <>", value, "verifyOverTime");
            return (Criteria) this;
        }

        public Criteria andVerifyOverTimeGreaterThan(Integer value) {
            addCriterion("verify_over_time >", value, "verifyOverTime");
            return (Criteria) this;
        }

        public Criteria andVerifyOverTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("verify_over_time >=", value, "verifyOverTime");
            return (Criteria) this;
        }

        public Criteria andVerifyOverTimeLessThan(Integer value) {
            addCriterion("verify_over_time <", value, "verifyOverTime");
            return (Criteria) this;
        }

        public Criteria andVerifyOverTimeLessThanOrEqualTo(Integer value) {
            addCriterion("verify_over_time <=", value, "verifyOverTime");
            return (Criteria) this;
        }

        public Criteria andVerifyOverTimeIn(List<Integer> values) {
            addCriterion("verify_over_time in", values, "verifyOverTime");
            return (Criteria) this;
        }

        public Criteria andVerifyOverTimeNotIn(List<Integer> values) {
            addCriterion("verify_over_time not in", values, "verifyOverTime");
            return (Criteria) this;
        }

        public Criteria andVerifyOverTimeBetween(Integer value1, Integer value2) {
            addCriterion("verify_over_time between", value1, value2, "verifyOverTime");
            return (Criteria) this;
        }

        public Criteria andVerifyOverTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("verify_over_time not between", value1, value2, "verifyOverTime");
            return (Criteria) this;
        }

        public Criteria andVerifyTimeIsNull() {
            addCriterion("verify_time is null");
            return (Criteria) this;
        }

        public Criteria andVerifyTimeIsNotNull() {
            addCriterion("verify_time is not null");
            return (Criteria) this;
        }

        public Criteria andVerifyTimeEqualTo(Integer value) {
            addCriterion("verify_time =", value, "verifyTime");
            return (Criteria) this;
        }

        public Criteria andVerifyTimeNotEqualTo(Integer value) {
            addCriterion("verify_time <>", value, "verifyTime");
            return (Criteria) this;
        }

        public Criteria andVerifyTimeGreaterThan(Integer value) {
            addCriterion("verify_time >", value, "verifyTime");
            return (Criteria) this;
        }

        public Criteria andVerifyTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("verify_time >=", value, "verifyTime");
            return (Criteria) this;
        }

        public Criteria andVerifyTimeLessThan(Integer value) {
            addCriterion("verify_time <", value, "verifyTime");
            return (Criteria) this;
        }

        public Criteria andVerifyTimeLessThanOrEqualTo(Integer value) {
            addCriterion("verify_time <=", value, "verifyTime");
            return (Criteria) this;
        }

        public Criteria andVerifyTimeIn(List<Integer> values) {
            addCriterion("verify_time in", values, "verifyTime");
            return (Criteria) this;
        }

        public Criteria andVerifyTimeNotIn(List<Integer> values) {
            addCriterion("verify_time not in", values, "verifyTime");
            return (Criteria) this;
        }

        public Criteria andVerifyTimeBetween(Integer value1, Integer value2) {
            addCriterion("verify_time between", value1, value2, "verifyTime");
            return (Criteria) this;
        }

        public Criteria andVerifyTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("verify_time not between", value1, value2, "verifyTime");
            return (Criteria) this;
        }

        public Criteria andVerifyUseridIsNull() {
            addCriterion("verify_userid is null");
            return (Criteria) this;
        }

        public Criteria andVerifyUseridIsNotNull() {
            addCriterion("verify_userid is not null");
            return (Criteria) this;
        }

        public Criteria andVerifyUseridEqualTo(String value) {
            addCriterion("verify_userid =", value, "verifyUserid");
            return (Criteria) this;
        }

        public Criteria andVerifyUseridNotEqualTo(String value) {
            addCriterion("verify_userid <>", value, "verifyUserid");
            return (Criteria) this;
        }

        public Criteria andVerifyUseridGreaterThan(String value) {
            addCriterion("verify_userid >", value, "verifyUserid");
            return (Criteria) this;
        }

        public Criteria andVerifyUseridGreaterThanOrEqualTo(String value) {
            addCriterion("verify_userid >=", value, "verifyUserid");
            return (Criteria) this;
        }

        public Criteria andVerifyUseridLessThan(String value) {
            addCriterion("verify_userid <", value, "verifyUserid");
            return (Criteria) this;
        }

        public Criteria andVerifyUseridLessThanOrEqualTo(String value) {
            addCriterion("verify_userid <=", value, "verifyUserid");
            return (Criteria) this;
        }

        public Criteria andVerifyUseridLike(String value) {
            addCriterion("verify_userid like", value, "verifyUserid");
            return (Criteria) this;
        }

        public Criteria andVerifyUseridNotLike(String value) {
            addCriterion("verify_userid not like", value, "verifyUserid");
            return (Criteria) this;
        }

        public Criteria andVerifyUseridIn(List<String> values) {
            addCriterion("verify_userid in", values, "verifyUserid");
            return (Criteria) this;
        }

        public Criteria andVerifyUseridNotIn(List<String> values) {
            addCriterion("verify_userid not in", values, "verifyUserid");
            return (Criteria) this;
        }

        public Criteria andVerifyUseridBetween(String value1, String value2) {
            addCriterion("verify_userid between", value1, value2, "verifyUserid");
            return (Criteria) this;
        }

        public Criteria andVerifyUseridNotBetween(String value1, String value2) {
            addCriterion("verify_userid not between", value1, value2, "verifyUserid");
            return (Criteria) this;
        }

        public Criteria andVerifyUserNameIsNull() {
            addCriterion("verify_user_name is null");
            return (Criteria) this;
        }

        public Criteria andVerifyUserNameIsNotNull() {
            addCriterion("verify_user_name is not null");
            return (Criteria) this;
        }

        public Criteria andVerifyUserNameEqualTo(String value) {
            addCriterion("verify_user_name =", value, "verifyUserName");
            return (Criteria) this;
        }

        public Criteria andVerifyUserNameNotEqualTo(String value) {
            addCriterion("verify_user_name <>", value, "verifyUserName");
            return (Criteria) this;
        }

        public Criteria andVerifyUserNameGreaterThan(String value) {
            addCriterion("verify_user_name >", value, "verifyUserName");
            return (Criteria) this;
        }

        public Criteria andVerifyUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("verify_user_name >=", value, "verifyUserName");
            return (Criteria) this;
        }

        public Criteria andVerifyUserNameLessThan(String value) {
            addCriterion("verify_user_name <", value, "verifyUserName");
            return (Criteria) this;
        }

        public Criteria andVerifyUserNameLessThanOrEqualTo(String value) {
            addCriterion("verify_user_name <=", value, "verifyUserName");
            return (Criteria) this;
        }

        public Criteria andVerifyUserNameLike(String value) {
            addCriterion("verify_user_name like", value, "verifyUserName");
            return (Criteria) this;
        }

        public Criteria andVerifyUserNameNotLike(String value) {
            addCriterion("verify_user_name not like", value, "verifyUserName");
            return (Criteria) this;
        }

        public Criteria andVerifyUserNameIn(List<String> values) {
            addCriterion("verify_user_name in", values, "verifyUserName");
            return (Criteria) this;
        }

        public Criteria andVerifyUserNameNotIn(List<String> values) {
            addCriterion("verify_user_name not in", values, "verifyUserName");
            return (Criteria) this;
        }

        public Criteria andVerifyUserNameBetween(String value1, String value2) {
            addCriterion("verify_user_name between", value1, value2, "verifyUserName");
            return (Criteria) this;
        }

        public Criteria andVerifyUserNameNotBetween(String value1, String value2) {
            addCriterion("verify_user_name not between", value1, value2, "verifyUserName");
            return (Criteria) this;
        }

        public Criteria andVerifyRemarkIsNull() {
            addCriterion("verify_remark is null");
            return (Criteria) this;
        }

        public Criteria andVerifyRemarkIsNotNull() {
            addCriterion("verify_remark is not null");
            return (Criteria) this;
        }

        public Criteria andVerifyRemarkEqualTo(String value) {
            addCriterion("verify_remark =", value, "verifyRemark");
            return (Criteria) this;
        }

        public Criteria andVerifyRemarkNotEqualTo(String value) {
            addCriterion("verify_remark <>", value, "verifyRemark");
            return (Criteria) this;
        }

        public Criteria andVerifyRemarkGreaterThan(String value) {
            addCriterion("verify_remark >", value, "verifyRemark");
            return (Criteria) this;
        }

        public Criteria andVerifyRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("verify_remark >=", value, "verifyRemark");
            return (Criteria) this;
        }

        public Criteria andVerifyRemarkLessThan(String value) {
            addCriterion("verify_remark <", value, "verifyRemark");
            return (Criteria) this;
        }

        public Criteria andVerifyRemarkLessThanOrEqualTo(String value) {
            addCriterion("verify_remark <=", value, "verifyRemark");
            return (Criteria) this;
        }

        public Criteria andVerifyRemarkLike(String value) {
            addCriterion("verify_remark like", value, "verifyRemark");
            return (Criteria) this;
        }

        public Criteria andVerifyRemarkNotLike(String value) {
            addCriterion("verify_remark not like", value, "verifyRemark");
            return (Criteria) this;
        }

        public Criteria andVerifyRemarkIn(List<String> values) {
            addCriterion("verify_remark in", values, "verifyRemark");
            return (Criteria) this;
        }

        public Criteria andVerifyRemarkNotIn(List<String> values) {
            addCriterion("verify_remark not in", values, "verifyRemark");
            return (Criteria) this;
        }

        public Criteria andVerifyRemarkBetween(String value1, String value2) {
            addCriterion("verify_remark between", value1, value2, "verifyRemark");
            return (Criteria) this;
        }

        public Criteria andVerifyRemarkNotBetween(String value1, String value2) {
            addCriterion("verify_remark not between", value1, value2, "verifyRemark");
            return (Criteria) this;
        }

        public Criteria andVerifyContentsIsNull() {
            addCriterion("verify_contents is null");
            return (Criteria) this;
        }

        public Criteria andVerifyContentsIsNotNull() {
            addCriterion("verify_contents is not null");
            return (Criteria) this;
        }

        public Criteria andVerifyContentsEqualTo(String value) {
            addCriterion("verify_contents =", value, "verifyContents");
            return (Criteria) this;
        }

        public Criteria andVerifyContentsNotEqualTo(String value) {
            addCriterion("verify_contents <>", value, "verifyContents");
            return (Criteria) this;
        }

        public Criteria andVerifyContentsGreaterThan(String value) {
            addCriterion("verify_contents >", value, "verifyContents");
            return (Criteria) this;
        }

        public Criteria andVerifyContentsGreaterThanOrEqualTo(String value) {
            addCriterion("verify_contents >=", value, "verifyContents");
            return (Criteria) this;
        }

        public Criteria andVerifyContentsLessThan(String value) {
            addCriterion("verify_contents <", value, "verifyContents");
            return (Criteria) this;
        }

        public Criteria andVerifyContentsLessThanOrEqualTo(String value) {
            addCriterion("verify_contents <=", value, "verifyContents");
            return (Criteria) this;
        }

        public Criteria andVerifyContentsLike(String value) {
            addCriterion("verify_contents like", value, "verifyContents");
            return (Criteria) this;
        }

        public Criteria andVerifyContentsNotLike(String value) {
            addCriterion("verify_contents not like", value, "verifyContents");
            return (Criteria) this;
        }

        public Criteria andVerifyContentsIn(List<String> values) {
            addCriterion("verify_contents in", values, "verifyContents");
            return (Criteria) this;
        }

        public Criteria andVerifyContentsNotIn(List<String> values) {
            addCriterion("verify_contents not in", values, "verifyContents");
            return (Criteria) this;
        }

        public Criteria andVerifyContentsBetween(String value1, String value2) {
            addCriterion("verify_contents between", value1, value2, "verifyContents");
            return (Criteria) this;
        }

        public Criteria andVerifyContentsNotBetween(String value1, String value2) {
            addCriterion("verify_contents not between", value1, value2, "verifyContents");
            return (Criteria) this;
        }

        public Criteria andBorrowStatusIsNull() {
            addCriterion("borrow_status is null");
            return (Criteria) this;
        }

        public Criteria andBorrowStatusIsNotNull() {
            addCriterion("borrow_status is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowStatusEqualTo(Integer value) {
            addCriterion("borrow_status =", value, "borrowStatus");
            return (Criteria) this;
        }

        public Criteria andBorrowStatusNotEqualTo(Integer value) {
            addCriterion("borrow_status <>", value, "borrowStatus");
            return (Criteria) this;
        }

        public Criteria andBorrowStatusGreaterThan(Integer value) {
            addCriterion("borrow_status >", value, "borrowStatus");
            return (Criteria) this;
        }

        public Criteria andBorrowStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("borrow_status >=", value, "borrowStatus");
            return (Criteria) this;
        }

        public Criteria andBorrowStatusLessThan(Integer value) {
            addCriterion("borrow_status <", value, "borrowStatus");
            return (Criteria) this;
        }

        public Criteria andBorrowStatusLessThanOrEqualTo(Integer value) {
            addCriterion("borrow_status <=", value, "borrowStatus");
            return (Criteria) this;
        }

        public Criteria andBorrowStatusIn(List<Integer> values) {
            addCriterion("borrow_status in", values, "borrowStatus");
            return (Criteria) this;
        }

        public Criteria andBorrowStatusNotIn(List<Integer> values) {
            addCriterion("borrow_status not in", values, "borrowStatus");
            return (Criteria) this;
        }

        public Criteria andBorrowStatusBetween(Integer value1, Integer value2) {
            addCriterion("borrow_status between", value1, value2, "borrowStatus");
            return (Criteria) this;
        }

        public Criteria andBorrowStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("borrow_status not between", value1, value2, "borrowStatus");
            return (Criteria) this;
        }

        public Criteria andOntimeIsNull() {
            addCriterion("ontime is null");
            return (Criteria) this;
        }

        public Criteria andOntimeIsNotNull() {
            addCriterion("ontime is not null");
            return (Criteria) this;
        }

        public Criteria andOntimeEqualTo(Integer value) {
            addCriterion("ontime =", value, "ontime");
            return (Criteria) this;
        }

        public Criteria andOntimeNotEqualTo(Integer value) {
            addCriterion("ontime <>", value, "ontime");
            return (Criteria) this;
        }

        public Criteria andOntimeGreaterThan(Integer value) {
            addCriterion("ontime >", value, "ontime");
            return (Criteria) this;
        }

        public Criteria andOntimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("ontime >=", value, "ontime");
            return (Criteria) this;
        }

        public Criteria andOntimeLessThan(Integer value) {
            addCriterion("ontime <", value, "ontime");
            return (Criteria) this;
        }

        public Criteria andOntimeLessThanOrEqualTo(Integer value) {
            addCriterion("ontime <=", value, "ontime");
            return (Criteria) this;
        }

        public Criteria andOntimeIn(List<Integer> values) {
            addCriterion("ontime in", values, "ontime");
            return (Criteria) this;
        }

        public Criteria andOntimeNotIn(List<Integer> values) {
            addCriterion("ontime not in", values, "ontime");
            return (Criteria) this;
        }

        public Criteria andOntimeBetween(Integer value1, Integer value2) {
            addCriterion("ontime between", value1, value2, "ontime");
            return (Criteria) this;
        }

        public Criteria andOntimeNotBetween(Integer value1, Integer value2) {
            addCriterion("ontime not between", value1, value2, "ontime");
            return (Criteria) this;
        }

        public Criteria andBorrowEndTimeIsNull() {
            addCriterion("borrow_end_time is null");
            return (Criteria) this;
        }

        public Criteria andBorrowEndTimeIsNotNull() {
            addCriterion("borrow_end_time is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowEndTimeEqualTo(String value) {
            addCriterion("borrow_end_time =", value, "borrowEndTime");
            return (Criteria) this;
        }

        public Criteria andBorrowEndTimeNotEqualTo(String value) {
            addCriterion("borrow_end_time <>", value, "borrowEndTime");
            return (Criteria) this;
        }

        public Criteria andBorrowEndTimeGreaterThan(String value) {
            addCriterion("borrow_end_time >", value, "borrowEndTime");
            return (Criteria) this;
        }

        public Criteria andBorrowEndTimeGreaterThanOrEqualTo(String value) {
            addCriterion("borrow_end_time >=", value, "borrowEndTime");
            return (Criteria) this;
        }

        public Criteria andBorrowEndTimeLessThan(String value) {
            addCriterion("borrow_end_time <", value, "borrowEndTime");
            return (Criteria) this;
        }

        public Criteria andBorrowEndTimeLessThanOrEqualTo(String value) {
            addCriterion("borrow_end_time <=", value, "borrowEndTime");
            return (Criteria) this;
        }

        public Criteria andBorrowEndTimeLike(String value) {
            addCriterion("borrow_end_time like", value, "borrowEndTime");
            return (Criteria) this;
        }

        public Criteria andBorrowEndTimeNotLike(String value) {
            addCriterion("borrow_end_time not like", value, "borrowEndTime");
            return (Criteria) this;
        }

        public Criteria andBorrowEndTimeIn(List<String> values) {
            addCriterion("borrow_end_time in", values, "borrowEndTime");
            return (Criteria) this;
        }

        public Criteria andBorrowEndTimeNotIn(List<String> values) {
            addCriterion("borrow_end_time not in", values, "borrowEndTime");
            return (Criteria) this;
        }

        public Criteria andBorrowEndTimeBetween(String value1, String value2) {
            addCriterion("borrow_end_time between", value1, value2, "borrowEndTime");
            return (Criteria) this;
        }

        public Criteria andBorrowEndTimeNotBetween(String value1, String value2) {
            addCriterion("borrow_end_time not between", value1, value2, "borrowEndTime");
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

        public Criteria andBorrowFullStatusIsNull() {
            addCriterion("borrow_full_status is null");
            return (Criteria) this;
        }

        public Criteria andBorrowFullStatusIsNotNull() {
            addCriterion("borrow_full_status is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowFullStatusEqualTo(Integer value) {
            addCriterion("borrow_full_status =", value, "borrowFullStatus");
            return (Criteria) this;
        }

        public Criteria andBorrowFullStatusNotEqualTo(Integer value) {
            addCriterion("borrow_full_status <>", value, "borrowFullStatus");
            return (Criteria) this;
        }

        public Criteria andBorrowFullStatusGreaterThan(Integer value) {
            addCriterion("borrow_full_status >", value, "borrowFullStatus");
            return (Criteria) this;
        }

        public Criteria andBorrowFullStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("borrow_full_status >=", value, "borrowFullStatus");
            return (Criteria) this;
        }

        public Criteria andBorrowFullStatusLessThan(Integer value) {
            addCriterion("borrow_full_status <", value, "borrowFullStatus");
            return (Criteria) this;
        }

        public Criteria andBorrowFullStatusLessThanOrEqualTo(Integer value) {
            addCriterion("borrow_full_status <=", value, "borrowFullStatus");
            return (Criteria) this;
        }

        public Criteria andBorrowFullStatusIn(List<Integer> values) {
            addCriterion("borrow_full_status in", values, "borrowFullStatus");
            return (Criteria) this;
        }

        public Criteria andBorrowFullStatusNotIn(List<Integer> values) {
            addCriterion("borrow_full_status not in", values, "borrowFullStatus");
            return (Criteria) this;
        }

        public Criteria andBorrowFullStatusBetween(Integer value1, Integer value2) {
            addCriterion("borrow_full_status between", value1, value2, "borrowFullStatus");
            return (Criteria) this;
        }

        public Criteria andBorrowFullStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("borrow_full_status not between", value1, value2, "borrowFullStatus");
            return (Criteria) this;
        }

        public Criteria andBorrowFullTimeIsNull() {
            addCriterion("borrow_full_time is null");
            return (Criteria) this;
        }

        public Criteria andBorrowFullTimeIsNotNull() {
            addCriterion("borrow_full_time is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowFullTimeEqualTo(Integer value) {
            addCriterion("borrow_full_time =", value, "borrowFullTime");
            return (Criteria) this;
        }

        public Criteria andBorrowFullTimeNotEqualTo(Integer value) {
            addCriterion("borrow_full_time <>", value, "borrowFullTime");
            return (Criteria) this;
        }

        public Criteria andBorrowFullTimeGreaterThan(Integer value) {
            addCriterion("borrow_full_time >", value, "borrowFullTime");
            return (Criteria) this;
        }

        public Criteria andBorrowFullTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("borrow_full_time >=", value, "borrowFullTime");
            return (Criteria) this;
        }

        public Criteria andBorrowFullTimeLessThan(Integer value) {
            addCriterion("borrow_full_time <", value, "borrowFullTime");
            return (Criteria) this;
        }

        public Criteria andBorrowFullTimeLessThanOrEqualTo(Integer value) {
            addCriterion("borrow_full_time <=", value, "borrowFullTime");
            return (Criteria) this;
        }

        public Criteria andBorrowFullTimeIn(List<Integer> values) {
            addCriterion("borrow_full_time in", values, "borrowFullTime");
            return (Criteria) this;
        }

        public Criteria andBorrowFullTimeNotIn(List<Integer> values) {
            addCriterion("borrow_full_time not in", values, "borrowFullTime");
            return (Criteria) this;
        }

        public Criteria andBorrowFullTimeBetween(Integer value1, Integer value2) {
            addCriterion("borrow_full_time between", value1, value2, "borrowFullTime");
            return (Criteria) this;
        }

        public Criteria andBorrowFullTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("borrow_full_time not between", value1, value2, "borrowFullTime");
            return (Criteria) this;
        }

        public Criteria andTenderTimesIsNull() {
            addCriterion("tender_times is null");
            return (Criteria) this;
        }

        public Criteria andTenderTimesIsNotNull() {
            addCriterion("tender_times is not null");
            return (Criteria) this;
        }

        public Criteria andTenderTimesEqualTo(Integer value) {
            addCriterion("tender_times =", value, "tenderTimes");
            return (Criteria) this;
        }

        public Criteria andTenderTimesNotEqualTo(Integer value) {
            addCriterion("tender_times <>", value, "tenderTimes");
            return (Criteria) this;
        }

        public Criteria andTenderTimesGreaterThan(Integer value) {
            addCriterion("tender_times >", value, "tenderTimes");
            return (Criteria) this;
        }

        public Criteria andTenderTimesGreaterThanOrEqualTo(Integer value) {
            addCriterion("tender_times >=", value, "tenderTimes");
            return (Criteria) this;
        }

        public Criteria andTenderTimesLessThan(Integer value) {
            addCriterion("tender_times <", value, "tenderTimes");
            return (Criteria) this;
        }

        public Criteria andTenderTimesLessThanOrEqualTo(Integer value) {
            addCriterion("tender_times <=", value, "tenderTimes");
            return (Criteria) this;
        }

        public Criteria andTenderTimesIn(List<Integer> values) {
            addCriterion("tender_times in", values, "tenderTimes");
            return (Criteria) this;
        }

        public Criteria andTenderTimesNotIn(List<Integer> values) {
            addCriterion("tender_times not in", values, "tenderTimes");
            return (Criteria) this;
        }

        public Criteria andTenderTimesBetween(Integer value1, Integer value2) {
            addCriterion("tender_times between", value1, value2, "tenderTimes");
            return (Criteria) this;
        }

        public Criteria andTenderTimesNotBetween(Integer value1, Integer value2) {
            addCriterion("tender_times not between", value1, value2, "tenderTimes");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountYesIsNull() {
            addCriterion("borrow_account_yes is null");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountYesIsNotNull() {
            addCriterion("borrow_account_yes is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountYesEqualTo(BigDecimal value) {
            addCriterion("borrow_account_yes =", value, "borrowAccountYes");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountYesNotEqualTo(BigDecimal value) {
            addCriterion("borrow_account_yes <>", value, "borrowAccountYes");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountYesGreaterThan(BigDecimal value) {
            addCriterion("borrow_account_yes >", value, "borrowAccountYes");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountYesGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("borrow_account_yes >=", value, "borrowAccountYes");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountYesLessThan(BigDecimal value) {
            addCriterion("borrow_account_yes <", value, "borrowAccountYes");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountYesLessThanOrEqualTo(BigDecimal value) {
            addCriterion("borrow_account_yes <=", value, "borrowAccountYes");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountYesIn(List<BigDecimal> values) {
            addCriterion("borrow_account_yes in", values, "borrowAccountYes");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountYesNotIn(List<BigDecimal> values) {
            addCriterion("borrow_account_yes not in", values, "borrowAccountYes");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountYesBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("borrow_account_yes between", value1, value2, "borrowAccountYes");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountYesNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("borrow_account_yes not between", value1, value2, "borrowAccountYes");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountWaitIsNull() {
            addCriterion("borrow_account_wait is null");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountWaitIsNotNull() {
            addCriterion("borrow_account_wait is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountWaitEqualTo(BigDecimal value) {
            addCriterion("borrow_account_wait =", value, "borrowAccountWait");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountWaitNotEqualTo(BigDecimal value) {
            addCriterion("borrow_account_wait <>", value, "borrowAccountWait");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountWaitGreaterThan(BigDecimal value) {
            addCriterion("borrow_account_wait >", value, "borrowAccountWait");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountWaitGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("borrow_account_wait >=", value, "borrowAccountWait");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountWaitLessThan(BigDecimal value) {
            addCriterion("borrow_account_wait <", value, "borrowAccountWait");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountWaitLessThanOrEqualTo(BigDecimal value) {
            addCriterion("borrow_account_wait <=", value, "borrowAccountWait");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountWaitIn(List<BigDecimal> values) {
            addCriterion("borrow_account_wait in", values, "borrowAccountWait");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountWaitNotIn(List<BigDecimal> values) {
            addCriterion("borrow_account_wait not in", values, "borrowAccountWait");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountWaitBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("borrow_account_wait between", value1, value2, "borrowAccountWait");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountWaitNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("borrow_account_wait not between", value1, value2, "borrowAccountWait");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountScaleIsNull() {
            addCriterion("borrow_account_scale is null");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountScaleIsNotNull() {
            addCriterion("borrow_account_scale is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountScaleEqualTo(BigDecimal value) {
            addCriterion("borrow_account_scale =", value, "borrowAccountScale");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountScaleNotEqualTo(BigDecimal value) {
            addCriterion("borrow_account_scale <>", value, "borrowAccountScale");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountScaleGreaterThan(BigDecimal value) {
            addCriterion("borrow_account_scale >", value, "borrowAccountScale");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountScaleGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("borrow_account_scale >=", value, "borrowAccountScale");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountScaleLessThan(BigDecimal value) {
            addCriterion("borrow_account_scale <", value, "borrowAccountScale");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountScaleLessThanOrEqualTo(BigDecimal value) {
            addCriterion("borrow_account_scale <=", value, "borrowAccountScale");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountScaleIn(List<BigDecimal> values) {
            addCriterion("borrow_account_scale in", values, "borrowAccountScale");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountScaleNotIn(List<BigDecimal> values) {
            addCriterion("borrow_account_scale not in", values, "borrowAccountScale");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountScaleBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("borrow_account_scale between", value1, value2, "borrowAccountScale");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountScaleNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("borrow_account_scale not between", value1, value2, "borrowAccountScale");
            return (Criteria) this;
        }

        public Criteria andBorrowServiceIsNull() {
            addCriterion("borrow_service is null");
            return (Criteria) this;
        }

        public Criteria andBorrowServiceIsNotNull() {
            addCriterion("borrow_service is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowServiceEqualTo(String value) {
            addCriterion("borrow_service =", value, "borrowService");
            return (Criteria) this;
        }

        public Criteria andBorrowServiceNotEqualTo(String value) {
            addCriterion("borrow_service <>", value, "borrowService");
            return (Criteria) this;
        }

        public Criteria andBorrowServiceGreaterThan(String value) {
            addCriterion("borrow_service >", value, "borrowService");
            return (Criteria) this;
        }

        public Criteria andBorrowServiceGreaterThanOrEqualTo(String value) {
            addCriterion("borrow_service >=", value, "borrowService");
            return (Criteria) this;
        }

        public Criteria andBorrowServiceLessThan(String value) {
            addCriterion("borrow_service <", value, "borrowService");
            return (Criteria) this;
        }

        public Criteria andBorrowServiceLessThanOrEqualTo(String value) {
            addCriterion("borrow_service <=", value, "borrowService");
            return (Criteria) this;
        }

        public Criteria andBorrowServiceLike(String value) {
            addCriterion("borrow_service like", value, "borrowService");
            return (Criteria) this;
        }

        public Criteria andBorrowServiceNotLike(String value) {
            addCriterion("borrow_service not like", value, "borrowService");
            return (Criteria) this;
        }

        public Criteria andBorrowServiceIn(List<String> values) {
            addCriterion("borrow_service in", values, "borrowService");
            return (Criteria) this;
        }

        public Criteria andBorrowServiceNotIn(List<String> values) {
            addCriterion("borrow_service not in", values, "borrowService");
            return (Criteria) this;
        }

        public Criteria andBorrowServiceBetween(String value1, String value2) {
            addCriterion("borrow_service between", value1, value2, "borrowService");
            return (Criteria) this;
        }

        public Criteria andBorrowServiceNotBetween(String value1, String value2) {
            addCriterion("borrow_service not between", value1, value2, "borrowService");
            return (Criteria) this;
        }

        public Criteria andReverifyStatusIsNull() {
            addCriterion("reverify_status is null");
            return (Criteria) this;
        }

        public Criteria andReverifyStatusIsNotNull() {
            addCriterion("reverify_status is not null");
            return (Criteria) this;
        }

        public Criteria andReverifyStatusEqualTo(Integer value) {
            addCriterion("reverify_status =", value, "reverifyStatus");
            return (Criteria) this;
        }

        public Criteria andReverifyStatusNotEqualTo(Integer value) {
            addCriterion("reverify_status <>", value, "reverifyStatus");
            return (Criteria) this;
        }

        public Criteria andReverifyStatusGreaterThan(Integer value) {
            addCriterion("reverify_status >", value, "reverifyStatus");
            return (Criteria) this;
        }

        public Criteria andReverifyStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("reverify_status >=", value, "reverifyStatus");
            return (Criteria) this;
        }

        public Criteria andReverifyStatusLessThan(Integer value) {
            addCriterion("reverify_status <", value, "reverifyStatus");
            return (Criteria) this;
        }

        public Criteria andReverifyStatusLessThanOrEqualTo(Integer value) {
            addCriterion("reverify_status <=", value, "reverifyStatus");
            return (Criteria) this;
        }

        public Criteria andReverifyStatusIn(List<Integer> values) {
            addCriterion("reverify_status in", values, "reverifyStatus");
            return (Criteria) this;
        }

        public Criteria andReverifyStatusNotIn(List<Integer> values) {
            addCriterion("reverify_status not in", values, "reverifyStatus");
            return (Criteria) this;
        }

        public Criteria andReverifyStatusBetween(Integer value1, Integer value2) {
            addCriterion("reverify_status between", value1, value2, "reverifyStatus");
            return (Criteria) this;
        }

        public Criteria andReverifyStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("reverify_status not between", value1, value2, "reverifyStatus");
            return (Criteria) this;
        }

        public Criteria andReverifyTimeIsNull() {
            addCriterion("reverify_time is null");
            return (Criteria) this;
        }

        public Criteria andReverifyTimeIsNotNull() {
            addCriterion("reverify_time is not null");
            return (Criteria) this;
        }

        public Criteria andReverifyTimeEqualTo(Integer value) {
            addCriterion("reverify_time =", value, "reverifyTime");
            return (Criteria) this;
        }

        public Criteria andReverifyTimeNotEqualTo(Integer value) {
            addCriterion("reverify_time <>", value, "reverifyTime");
            return (Criteria) this;
        }

        public Criteria andReverifyTimeGreaterThan(Integer value) {
            addCriterion("reverify_time >", value, "reverifyTime");
            return (Criteria) this;
        }

        public Criteria andReverifyTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("reverify_time >=", value, "reverifyTime");
            return (Criteria) this;
        }

        public Criteria andReverifyTimeLessThan(Integer value) {
            addCriterion("reverify_time <", value, "reverifyTime");
            return (Criteria) this;
        }

        public Criteria andReverifyTimeLessThanOrEqualTo(Integer value) {
            addCriterion("reverify_time <=", value, "reverifyTime");
            return (Criteria) this;
        }

        public Criteria andReverifyTimeIn(List<Integer> values) {
            addCriterion("reverify_time in", values, "reverifyTime");
            return (Criteria) this;
        }

        public Criteria andReverifyTimeNotIn(List<Integer> values) {
            addCriterion("reverify_time not in", values, "reverifyTime");
            return (Criteria) this;
        }

        public Criteria andReverifyTimeBetween(Integer value1, Integer value2) {
            addCriterion("reverify_time between", value1, value2, "reverifyTime");
            return (Criteria) this;
        }

        public Criteria andReverifyTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("reverify_time not between", value1, value2, "reverifyTime");
            return (Criteria) this;
        }

        public Criteria andReverifyUseridIsNull() {
            addCriterion("reverify_userid is null");
            return (Criteria) this;
        }

        public Criteria andReverifyUseridIsNotNull() {
            addCriterion("reverify_userid is not null");
            return (Criteria) this;
        }

        public Criteria andReverifyUseridEqualTo(String value) {
            addCriterion("reverify_userid =", value, "reverifyUserid");
            return (Criteria) this;
        }

        public Criteria andReverifyUseridNotEqualTo(String value) {
            addCriterion("reverify_userid <>", value, "reverifyUserid");
            return (Criteria) this;
        }

        public Criteria andReverifyUseridGreaterThan(String value) {
            addCriterion("reverify_userid >", value, "reverifyUserid");
            return (Criteria) this;
        }

        public Criteria andReverifyUseridGreaterThanOrEqualTo(String value) {
            addCriterion("reverify_userid >=", value, "reverifyUserid");
            return (Criteria) this;
        }

        public Criteria andReverifyUseridLessThan(String value) {
            addCriterion("reverify_userid <", value, "reverifyUserid");
            return (Criteria) this;
        }

        public Criteria andReverifyUseridLessThanOrEqualTo(String value) {
            addCriterion("reverify_userid <=", value, "reverifyUserid");
            return (Criteria) this;
        }

        public Criteria andReverifyUseridLike(String value) {
            addCriterion("reverify_userid like", value, "reverifyUserid");
            return (Criteria) this;
        }

        public Criteria andReverifyUseridNotLike(String value) {
            addCriterion("reverify_userid not like", value, "reverifyUserid");
            return (Criteria) this;
        }

        public Criteria andReverifyUseridIn(List<String> values) {
            addCriterion("reverify_userid in", values, "reverifyUserid");
            return (Criteria) this;
        }

        public Criteria andReverifyUseridNotIn(List<String> values) {
            addCriterion("reverify_userid not in", values, "reverifyUserid");
            return (Criteria) this;
        }

        public Criteria andReverifyUseridBetween(String value1, String value2) {
            addCriterion("reverify_userid between", value1, value2, "reverifyUserid");
            return (Criteria) this;
        }

        public Criteria andReverifyUseridNotBetween(String value1, String value2) {
            addCriterion("reverify_userid not between", value1, value2, "reverifyUserid");
            return (Criteria) this;
        }

        public Criteria andReverifyUserNameIsNull() {
            addCriterion("reverify_user_name is null");
            return (Criteria) this;
        }

        public Criteria andReverifyUserNameIsNotNull() {
            addCriterion("reverify_user_name is not null");
            return (Criteria) this;
        }

        public Criteria andReverifyUserNameEqualTo(String value) {
            addCriterion("reverify_user_name =", value, "reverifyUserName");
            return (Criteria) this;
        }

        public Criteria andReverifyUserNameNotEqualTo(String value) {
            addCriterion("reverify_user_name <>", value, "reverifyUserName");
            return (Criteria) this;
        }

        public Criteria andReverifyUserNameGreaterThan(String value) {
            addCriterion("reverify_user_name >", value, "reverifyUserName");
            return (Criteria) this;
        }

        public Criteria andReverifyUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("reverify_user_name >=", value, "reverifyUserName");
            return (Criteria) this;
        }

        public Criteria andReverifyUserNameLessThan(String value) {
            addCriterion("reverify_user_name <", value, "reverifyUserName");
            return (Criteria) this;
        }

        public Criteria andReverifyUserNameLessThanOrEqualTo(String value) {
            addCriterion("reverify_user_name <=", value, "reverifyUserName");
            return (Criteria) this;
        }

        public Criteria andReverifyUserNameLike(String value) {
            addCriterion("reverify_user_name like", value, "reverifyUserName");
            return (Criteria) this;
        }

        public Criteria andReverifyUserNameNotLike(String value) {
            addCriterion("reverify_user_name not like", value, "reverifyUserName");
            return (Criteria) this;
        }

        public Criteria andReverifyUserNameIn(List<String> values) {
            addCriterion("reverify_user_name in", values, "reverifyUserName");
            return (Criteria) this;
        }

        public Criteria andReverifyUserNameNotIn(List<String> values) {
            addCriterion("reverify_user_name not in", values, "reverifyUserName");
            return (Criteria) this;
        }

        public Criteria andReverifyUserNameBetween(String value1, String value2) {
            addCriterion("reverify_user_name between", value1, value2, "reverifyUserName");
            return (Criteria) this;
        }

        public Criteria andReverifyUserNameNotBetween(String value1, String value2) {
            addCriterion("reverify_user_name not between", value1, value2, "reverifyUserName");
            return (Criteria) this;
        }

        public Criteria andReverifyRemarkIsNull() {
            addCriterion("reverify_remark is null");
            return (Criteria) this;
        }

        public Criteria andReverifyRemarkIsNotNull() {
            addCriterion("reverify_remark is not null");
            return (Criteria) this;
        }

        public Criteria andReverifyRemarkEqualTo(String value) {
            addCriterion("reverify_remark =", value, "reverifyRemark");
            return (Criteria) this;
        }

        public Criteria andReverifyRemarkNotEqualTo(String value) {
            addCriterion("reverify_remark <>", value, "reverifyRemark");
            return (Criteria) this;
        }

        public Criteria andReverifyRemarkGreaterThan(String value) {
            addCriterion("reverify_remark >", value, "reverifyRemark");
            return (Criteria) this;
        }

        public Criteria andReverifyRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("reverify_remark >=", value, "reverifyRemark");
            return (Criteria) this;
        }

        public Criteria andReverifyRemarkLessThan(String value) {
            addCriterion("reverify_remark <", value, "reverifyRemark");
            return (Criteria) this;
        }

        public Criteria andReverifyRemarkLessThanOrEqualTo(String value) {
            addCriterion("reverify_remark <=", value, "reverifyRemark");
            return (Criteria) this;
        }

        public Criteria andReverifyRemarkLike(String value) {
            addCriterion("reverify_remark like", value, "reverifyRemark");
            return (Criteria) this;
        }

        public Criteria andReverifyRemarkNotLike(String value) {
            addCriterion("reverify_remark not like", value, "reverifyRemark");
            return (Criteria) this;
        }

        public Criteria andReverifyRemarkIn(List<String> values) {
            addCriterion("reverify_remark in", values, "reverifyRemark");
            return (Criteria) this;
        }

        public Criteria andReverifyRemarkNotIn(List<String> values) {
            addCriterion("reverify_remark not in", values, "reverifyRemark");
            return (Criteria) this;
        }

        public Criteria andReverifyRemarkBetween(String value1, String value2) {
            addCriterion("reverify_remark between", value1, value2, "reverifyRemark");
            return (Criteria) this;
        }

        public Criteria andReverifyRemarkNotBetween(String value1, String value2) {
            addCriterion("reverify_remark not between", value1, value2, "reverifyRemark");
            return (Criteria) this;
        }

        public Criteria andReverifyContentsIsNull() {
            addCriterion("reverify_contents is null");
            return (Criteria) this;
        }

        public Criteria andReverifyContentsIsNotNull() {
            addCriterion("reverify_contents is not null");
            return (Criteria) this;
        }

        public Criteria andReverifyContentsEqualTo(String value) {
            addCriterion("reverify_contents =", value, "reverifyContents");
            return (Criteria) this;
        }

        public Criteria andReverifyContentsNotEqualTo(String value) {
            addCriterion("reverify_contents <>", value, "reverifyContents");
            return (Criteria) this;
        }

        public Criteria andReverifyContentsGreaterThan(String value) {
            addCriterion("reverify_contents >", value, "reverifyContents");
            return (Criteria) this;
        }

        public Criteria andReverifyContentsGreaterThanOrEqualTo(String value) {
            addCriterion("reverify_contents >=", value, "reverifyContents");
            return (Criteria) this;
        }

        public Criteria andReverifyContentsLessThan(String value) {
            addCriterion("reverify_contents <", value, "reverifyContents");
            return (Criteria) this;
        }

        public Criteria andReverifyContentsLessThanOrEqualTo(String value) {
            addCriterion("reverify_contents <=", value, "reverifyContents");
            return (Criteria) this;
        }

        public Criteria andReverifyContentsLike(String value) {
            addCriterion("reverify_contents like", value, "reverifyContents");
            return (Criteria) this;
        }

        public Criteria andReverifyContentsNotLike(String value) {
            addCriterion("reverify_contents not like", value, "reverifyContents");
            return (Criteria) this;
        }

        public Criteria andReverifyContentsIn(List<String> values) {
            addCriterion("reverify_contents in", values, "reverifyContents");
            return (Criteria) this;
        }

        public Criteria andReverifyContentsNotIn(List<String> values) {
            addCriterion("reverify_contents not in", values, "reverifyContents");
            return (Criteria) this;
        }

        public Criteria andReverifyContentsBetween(String value1, String value2) {
            addCriterion("reverify_contents between", value1, value2, "reverifyContents");
            return (Criteria) this;
        }

        public Criteria andReverifyContentsNotBetween(String value1, String value2) {
            addCriterion("reverify_contents not between", value1, value2, "reverifyContents");
            return (Criteria) this;
        }

        public Criteria andRecoverLastTimeIsNull() {
            addCriterion("recover_last_time is null");
            return (Criteria) this;
        }

        public Criteria andRecoverLastTimeIsNotNull() {
            addCriterion("recover_last_time is not null");
            return (Criteria) this;
        }

        public Criteria andRecoverLastTimeEqualTo(Integer value) {
            addCriterion("recover_last_time =", value, "recoverLastTime");
            return (Criteria) this;
        }

        public Criteria andRecoverLastTimeNotEqualTo(Integer value) {
            addCriterion("recover_last_time <>", value, "recoverLastTime");
            return (Criteria) this;
        }

        public Criteria andRecoverLastTimeGreaterThan(Integer value) {
            addCriterion("recover_last_time >", value, "recoverLastTime");
            return (Criteria) this;
        }

        public Criteria andRecoverLastTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("recover_last_time >=", value, "recoverLastTime");
            return (Criteria) this;
        }

        public Criteria andRecoverLastTimeLessThan(Integer value) {
            addCriterion("recover_last_time <", value, "recoverLastTime");
            return (Criteria) this;
        }

        public Criteria andRecoverLastTimeLessThanOrEqualTo(Integer value) {
            addCriterion("recover_last_time <=", value, "recoverLastTime");
            return (Criteria) this;
        }

        public Criteria andRecoverLastTimeIn(List<Integer> values) {
            addCriterion("recover_last_time in", values, "recoverLastTime");
            return (Criteria) this;
        }

        public Criteria andRecoverLastTimeNotIn(List<Integer> values) {
            addCriterion("recover_last_time not in", values, "recoverLastTime");
            return (Criteria) this;
        }

        public Criteria andRecoverLastTimeBetween(Integer value1, Integer value2) {
            addCriterion("recover_last_time between", value1, value2, "recoverLastTime");
            return (Criteria) this;
        }

        public Criteria andRecoverLastTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("recover_last_time not between", value1, value2, "recoverLastTime");
            return (Criteria) this;
        }

        public Criteria andRepayLastTimeIsNull() {
            addCriterion("repay_last_time is null");
            return (Criteria) this;
        }

        public Criteria andRepayLastTimeIsNotNull() {
            addCriterion("repay_last_time is not null");
            return (Criteria) this;
        }

        public Criteria andRepayLastTimeEqualTo(Integer value) {
            addCriterion("repay_last_time =", value, "repayLastTime");
            return (Criteria) this;
        }

        public Criteria andRepayLastTimeNotEqualTo(Integer value) {
            addCriterion("repay_last_time <>", value, "repayLastTime");
            return (Criteria) this;
        }

        public Criteria andRepayLastTimeGreaterThan(Integer value) {
            addCriterion("repay_last_time >", value, "repayLastTime");
            return (Criteria) this;
        }

        public Criteria andRepayLastTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("repay_last_time >=", value, "repayLastTime");
            return (Criteria) this;
        }

        public Criteria andRepayLastTimeLessThan(Integer value) {
            addCriterion("repay_last_time <", value, "repayLastTime");
            return (Criteria) this;
        }

        public Criteria andRepayLastTimeLessThanOrEqualTo(Integer value) {
            addCriterion("repay_last_time <=", value, "repayLastTime");
            return (Criteria) this;
        }

        public Criteria andRepayLastTimeIn(List<Integer> values) {
            addCriterion("repay_last_time in", values, "repayLastTime");
            return (Criteria) this;
        }

        public Criteria andRepayLastTimeNotIn(List<Integer> values) {
            addCriterion("repay_last_time not in", values, "repayLastTime");
            return (Criteria) this;
        }

        public Criteria andRepayLastTimeBetween(Integer value1, Integer value2) {
            addCriterion("repay_last_time between", value1, value2, "repayLastTime");
            return (Criteria) this;
        }

        public Criteria andRepayLastTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("repay_last_time not between", value1, value2, "repayLastTime");
            return (Criteria) this;
        }

        public Criteria andRepayNextTimeIsNull() {
            addCriterion("repay_next_time is null");
            return (Criteria) this;
        }

        public Criteria andRepayNextTimeIsNotNull() {
            addCriterion("repay_next_time is not null");
            return (Criteria) this;
        }

        public Criteria andRepayNextTimeEqualTo(Integer value) {
            addCriterion("repay_next_time =", value, "repayNextTime");
            return (Criteria) this;
        }

        public Criteria andRepayNextTimeNotEqualTo(Integer value) {
            addCriterion("repay_next_time <>", value, "repayNextTime");
            return (Criteria) this;
        }

        public Criteria andRepayNextTimeGreaterThan(Integer value) {
            addCriterion("repay_next_time >", value, "repayNextTime");
            return (Criteria) this;
        }

        public Criteria andRepayNextTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("repay_next_time >=", value, "repayNextTime");
            return (Criteria) this;
        }

        public Criteria andRepayNextTimeLessThan(Integer value) {
            addCriterion("repay_next_time <", value, "repayNextTime");
            return (Criteria) this;
        }

        public Criteria andRepayNextTimeLessThanOrEqualTo(Integer value) {
            addCriterion("repay_next_time <=", value, "repayNextTime");
            return (Criteria) this;
        }

        public Criteria andRepayNextTimeIn(List<Integer> values) {
            addCriterion("repay_next_time in", values, "repayNextTime");
            return (Criteria) this;
        }

        public Criteria andRepayNextTimeNotIn(List<Integer> values) {
            addCriterion("repay_next_time not in", values, "repayNextTime");
            return (Criteria) this;
        }

        public Criteria andRepayNextTimeBetween(Integer value1, Integer value2) {
            addCriterion("repay_next_time between", value1, value2, "repayNextTime");
            return (Criteria) this;
        }

        public Criteria andRepayNextTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("repay_next_time not between", value1, value2, "repayNextTime");
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

        public Criteria andRepayFullStatusIsNull() {
            addCriterion("repay_full_status is null");
            return (Criteria) this;
        }

        public Criteria andRepayFullStatusIsNotNull() {
            addCriterion("repay_full_status is not null");
            return (Criteria) this;
        }

        public Criteria andRepayFullStatusEqualTo(Integer value) {
            addCriterion("repay_full_status =", value, "repayFullStatus");
            return (Criteria) this;
        }

        public Criteria andRepayFullStatusNotEqualTo(Integer value) {
            addCriterion("repay_full_status <>", value, "repayFullStatus");
            return (Criteria) this;
        }

        public Criteria andRepayFullStatusGreaterThan(Integer value) {
            addCriterion("repay_full_status >", value, "repayFullStatus");
            return (Criteria) this;
        }

        public Criteria andRepayFullStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("repay_full_status >=", value, "repayFullStatus");
            return (Criteria) this;
        }

        public Criteria andRepayFullStatusLessThan(Integer value) {
            addCriterion("repay_full_status <", value, "repayFullStatus");
            return (Criteria) this;
        }

        public Criteria andRepayFullStatusLessThanOrEqualTo(Integer value) {
            addCriterion("repay_full_status <=", value, "repayFullStatus");
            return (Criteria) this;
        }

        public Criteria andRepayFullStatusIn(List<Integer> values) {
            addCriterion("repay_full_status in", values, "repayFullStatus");
            return (Criteria) this;
        }

        public Criteria andRepayFullStatusNotIn(List<Integer> values) {
            addCriterion("repay_full_status not in", values, "repayFullStatus");
            return (Criteria) this;
        }

        public Criteria andRepayFullStatusBetween(Integer value1, Integer value2) {
            addCriterion("repay_full_status between", value1, value2, "repayFullStatus");
            return (Criteria) this;
        }

        public Criteria andRepayFullStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("repay_full_status not between", value1, value2, "repayFullStatus");
            return (Criteria) this;
        }

        public Criteria andRepayFeeNormalIsNull() {
            addCriterion("repay_fee_normal is null");
            return (Criteria) this;
        }

        public Criteria andRepayFeeNormalIsNotNull() {
            addCriterion("repay_fee_normal is not null");
            return (Criteria) this;
        }

        public Criteria andRepayFeeNormalEqualTo(BigDecimal value) {
            addCriterion("repay_fee_normal =", value, "repayFeeNormal");
            return (Criteria) this;
        }

        public Criteria andRepayFeeNormalNotEqualTo(BigDecimal value) {
            addCriterion("repay_fee_normal <>", value, "repayFeeNormal");
            return (Criteria) this;
        }

        public Criteria andRepayFeeNormalGreaterThan(BigDecimal value) {
            addCriterion("repay_fee_normal >", value, "repayFeeNormal");
            return (Criteria) this;
        }

        public Criteria andRepayFeeNormalGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_fee_normal >=", value, "repayFeeNormal");
            return (Criteria) this;
        }

        public Criteria andRepayFeeNormalLessThan(BigDecimal value) {
            addCriterion("repay_fee_normal <", value, "repayFeeNormal");
            return (Criteria) this;
        }

        public Criteria andRepayFeeNormalLessThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_fee_normal <=", value, "repayFeeNormal");
            return (Criteria) this;
        }

        public Criteria andRepayFeeNormalIn(List<BigDecimal> values) {
            addCriterion("repay_fee_normal in", values, "repayFeeNormal");
            return (Criteria) this;
        }

        public Criteria andRepayFeeNormalNotIn(List<BigDecimal> values) {
            addCriterion("repay_fee_normal not in", values, "repayFeeNormal");
            return (Criteria) this;
        }

        public Criteria andRepayFeeNormalBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_fee_normal between", value1, value2, "repayFeeNormal");
            return (Criteria) this;
        }

        public Criteria andRepayFeeNormalNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_fee_normal not between", value1, value2, "repayFeeNormal");
            return (Criteria) this;
        }

        public Criteria andRepayAccountAllIsNull() {
            addCriterion("repay_account_all is null");
            return (Criteria) this;
        }

        public Criteria andRepayAccountAllIsNotNull() {
            addCriterion("repay_account_all is not null");
            return (Criteria) this;
        }

        public Criteria andRepayAccountAllEqualTo(BigDecimal value) {
            addCriterion("repay_account_all =", value, "repayAccountAll");
            return (Criteria) this;
        }

        public Criteria andRepayAccountAllNotEqualTo(BigDecimal value) {
            addCriterion("repay_account_all <>", value, "repayAccountAll");
            return (Criteria) this;
        }

        public Criteria andRepayAccountAllGreaterThan(BigDecimal value) {
            addCriterion("repay_account_all >", value, "repayAccountAll");
            return (Criteria) this;
        }

        public Criteria andRepayAccountAllGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_account_all >=", value, "repayAccountAll");
            return (Criteria) this;
        }

        public Criteria andRepayAccountAllLessThan(BigDecimal value) {
            addCriterion("repay_account_all <", value, "repayAccountAll");
            return (Criteria) this;
        }

        public Criteria andRepayAccountAllLessThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_account_all <=", value, "repayAccountAll");
            return (Criteria) this;
        }

        public Criteria andRepayAccountAllIn(List<BigDecimal> values) {
            addCriterion("repay_account_all in", values, "repayAccountAll");
            return (Criteria) this;
        }

        public Criteria andRepayAccountAllNotIn(List<BigDecimal> values) {
            addCriterion("repay_account_all not in", values, "repayAccountAll");
            return (Criteria) this;
        }

        public Criteria andRepayAccountAllBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_account_all between", value1, value2, "repayAccountAll");
            return (Criteria) this;
        }

        public Criteria andRepayAccountAllNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_account_all not between", value1, value2, "repayAccountAll");
            return (Criteria) this;
        }

        public Criteria andRepayAccountInterestIsNull() {
            addCriterion("repay_account_interest is null");
            return (Criteria) this;
        }

        public Criteria andRepayAccountInterestIsNotNull() {
            addCriterion("repay_account_interest is not null");
            return (Criteria) this;
        }

        public Criteria andRepayAccountInterestEqualTo(BigDecimal value) {
            addCriterion("repay_account_interest =", value, "repayAccountInterest");
            return (Criteria) this;
        }

        public Criteria andRepayAccountInterestNotEqualTo(BigDecimal value) {
            addCriterion("repay_account_interest <>", value, "repayAccountInterest");
            return (Criteria) this;
        }

        public Criteria andRepayAccountInterestGreaterThan(BigDecimal value) {
            addCriterion("repay_account_interest >", value, "repayAccountInterest");
            return (Criteria) this;
        }

        public Criteria andRepayAccountInterestGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_account_interest >=", value, "repayAccountInterest");
            return (Criteria) this;
        }

        public Criteria andRepayAccountInterestLessThan(BigDecimal value) {
            addCriterion("repay_account_interest <", value, "repayAccountInterest");
            return (Criteria) this;
        }

        public Criteria andRepayAccountInterestLessThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_account_interest <=", value, "repayAccountInterest");
            return (Criteria) this;
        }

        public Criteria andRepayAccountInterestIn(List<BigDecimal> values) {
            addCriterion("repay_account_interest in", values, "repayAccountInterest");
            return (Criteria) this;
        }

        public Criteria andRepayAccountInterestNotIn(List<BigDecimal> values) {
            addCriterion("repay_account_interest not in", values, "repayAccountInterest");
            return (Criteria) this;
        }

        public Criteria andRepayAccountInterestBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_account_interest between", value1, value2, "repayAccountInterest");
            return (Criteria) this;
        }

        public Criteria andRepayAccountInterestNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_account_interest not between", value1, value2, "repayAccountInterest");
            return (Criteria) this;
        }

        public Criteria andRepayAccountCapitalIsNull() {
            addCriterion("repay_account_capital is null");
            return (Criteria) this;
        }

        public Criteria andRepayAccountCapitalIsNotNull() {
            addCriterion("repay_account_capital is not null");
            return (Criteria) this;
        }

        public Criteria andRepayAccountCapitalEqualTo(BigDecimal value) {
            addCriterion("repay_account_capital =", value, "repayAccountCapital");
            return (Criteria) this;
        }

        public Criteria andRepayAccountCapitalNotEqualTo(BigDecimal value) {
            addCriterion("repay_account_capital <>", value, "repayAccountCapital");
            return (Criteria) this;
        }

        public Criteria andRepayAccountCapitalGreaterThan(BigDecimal value) {
            addCriterion("repay_account_capital >", value, "repayAccountCapital");
            return (Criteria) this;
        }

        public Criteria andRepayAccountCapitalGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_account_capital >=", value, "repayAccountCapital");
            return (Criteria) this;
        }

        public Criteria andRepayAccountCapitalLessThan(BigDecimal value) {
            addCriterion("repay_account_capital <", value, "repayAccountCapital");
            return (Criteria) this;
        }

        public Criteria andRepayAccountCapitalLessThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_account_capital <=", value, "repayAccountCapital");
            return (Criteria) this;
        }

        public Criteria andRepayAccountCapitalIn(List<BigDecimal> values) {
            addCriterion("repay_account_capital in", values, "repayAccountCapital");
            return (Criteria) this;
        }

        public Criteria andRepayAccountCapitalNotIn(List<BigDecimal> values) {
            addCriterion("repay_account_capital not in", values, "repayAccountCapital");
            return (Criteria) this;
        }

        public Criteria andRepayAccountCapitalBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_account_capital between", value1, value2, "repayAccountCapital");
            return (Criteria) this;
        }

        public Criteria andRepayAccountCapitalNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_account_capital not between", value1, value2, "repayAccountCapital");
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

        public Criteria andRepayAccountInterestYesIsNull() {
            addCriterion("repay_account_interest_yes is null");
            return (Criteria) this;
        }

        public Criteria andRepayAccountInterestYesIsNotNull() {
            addCriterion("repay_account_interest_yes is not null");
            return (Criteria) this;
        }

        public Criteria andRepayAccountInterestYesEqualTo(BigDecimal value) {
            addCriterion("repay_account_interest_yes =", value, "repayAccountInterestYes");
            return (Criteria) this;
        }

        public Criteria andRepayAccountInterestYesNotEqualTo(BigDecimal value) {
            addCriterion("repay_account_interest_yes <>", value, "repayAccountInterestYes");
            return (Criteria) this;
        }

        public Criteria andRepayAccountInterestYesGreaterThan(BigDecimal value) {
            addCriterion("repay_account_interest_yes >", value, "repayAccountInterestYes");
            return (Criteria) this;
        }

        public Criteria andRepayAccountInterestYesGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_account_interest_yes >=", value, "repayAccountInterestYes");
            return (Criteria) this;
        }

        public Criteria andRepayAccountInterestYesLessThan(BigDecimal value) {
            addCriterion("repay_account_interest_yes <", value, "repayAccountInterestYes");
            return (Criteria) this;
        }

        public Criteria andRepayAccountInterestYesLessThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_account_interest_yes <=", value, "repayAccountInterestYes");
            return (Criteria) this;
        }

        public Criteria andRepayAccountInterestYesIn(List<BigDecimal> values) {
            addCriterion("repay_account_interest_yes in", values, "repayAccountInterestYes");
            return (Criteria) this;
        }

        public Criteria andRepayAccountInterestYesNotIn(List<BigDecimal> values) {
            addCriterion("repay_account_interest_yes not in", values, "repayAccountInterestYes");
            return (Criteria) this;
        }

        public Criteria andRepayAccountInterestYesBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_account_interest_yes between", value1, value2, "repayAccountInterestYes");
            return (Criteria) this;
        }

        public Criteria andRepayAccountInterestYesNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_account_interest_yes not between", value1, value2, "repayAccountInterestYes");
            return (Criteria) this;
        }

        public Criteria andRepayAccountCapitalYesIsNull() {
            addCriterion("repay_account_capital_yes is null");
            return (Criteria) this;
        }

        public Criteria andRepayAccountCapitalYesIsNotNull() {
            addCriterion("repay_account_capital_yes is not null");
            return (Criteria) this;
        }

        public Criteria andRepayAccountCapitalYesEqualTo(BigDecimal value) {
            addCriterion("repay_account_capital_yes =", value, "repayAccountCapitalYes");
            return (Criteria) this;
        }

        public Criteria andRepayAccountCapitalYesNotEqualTo(BigDecimal value) {
            addCriterion("repay_account_capital_yes <>", value, "repayAccountCapitalYes");
            return (Criteria) this;
        }

        public Criteria andRepayAccountCapitalYesGreaterThan(BigDecimal value) {
            addCriterion("repay_account_capital_yes >", value, "repayAccountCapitalYes");
            return (Criteria) this;
        }

        public Criteria andRepayAccountCapitalYesGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_account_capital_yes >=", value, "repayAccountCapitalYes");
            return (Criteria) this;
        }

        public Criteria andRepayAccountCapitalYesLessThan(BigDecimal value) {
            addCriterion("repay_account_capital_yes <", value, "repayAccountCapitalYes");
            return (Criteria) this;
        }

        public Criteria andRepayAccountCapitalYesLessThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_account_capital_yes <=", value, "repayAccountCapitalYes");
            return (Criteria) this;
        }

        public Criteria andRepayAccountCapitalYesIn(List<BigDecimal> values) {
            addCriterion("repay_account_capital_yes in", values, "repayAccountCapitalYes");
            return (Criteria) this;
        }

        public Criteria andRepayAccountCapitalYesNotIn(List<BigDecimal> values) {
            addCriterion("repay_account_capital_yes not in", values, "repayAccountCapitalYes");
            return (Criteria) this;
        }

        public Criteria andRepayAccountCapitalYesBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_account_capital_yes between", value1, value2, "repayAccountCapitalYes");
            return (Criteria) this;
        }

        public Criteria andRepayAccountCapitalYesNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_account_capital_yes not between", value1, value2, "repayAccountCapitalYes");
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

        public Criteria andRepayAccountInterestWaitIsNull() {
            addCriterion("repay_account_interest_wait is null");
            return (Criteria) this;
        }

        public Criteria andRepayAccountInterestWaitIsNotNull() {
            addCriterion("repay_account_interest_wait is not null");
            return (Criteria) this;
        }

        public Criteria andRepayAccountInterestWaitEqualTo(BigDecimal value) {
            addCriterion("repay_account_interest_wait =", value, "repayAccountInterestWait");
            return (Criteria) this;
        }

        public Criteria andRepayAccountInterestWaitNotEqualTo(BigDecimal value) {
            addCriterion("repay_account_interest_wait <>", value, "repayAccountInterestWait");
            return (Criteria) this;
        }

        public Criteria andRepayAccountInterestWaitGreaterThan(BigDecimal value) {
            addCriterion("repay_account_interest_wait >", value, "repayAccountInterestWait");
            return (Criteria) this;
        }

        public Criteria andRepayAccountInterestWaitGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_account_interest_wait >=", value, "repayAccountInterestWait");
            return (Criteria) this;
        }

        public Criteria andRepayAccountInterestWaitLessThan(BigDecimal value) {
            addCriterion("repay_account_interest_wait <", value, "repayAccountInterestWait");
            return (Criteria) this;
        }

        public Criteria andRepayAccountInterestWaitLessThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_account_interest_wait <=", value, "repayAccountInterestWait");
            return (Criteria) this;
        }

        public Criteria andRepayAccountInterestWaitIn(List<BigDecimal> values) {
            addCriterion("repay_account_interest_wait in", values, "repayAccountInterestWait");
            return (Criteria) this;
        }

        public Criteria andRepayAccountInterestWaitNotIn(List<BigDecimal> values) {
            addCriterion("repay_account_interest_wait not in", values, "repayAccountInterestWait");
            return (Criteria) this;
        }

        public Criteria andRepayAccountInterestWaitBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_account_interest_wait between", value1, value2, "repayAccountInterestWait");
            return (Criteria) this;
        }

        public Criteria andRepayAccountInterestWaitNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_account_interest_wait not between", value1, value2, "repayAccountInterestWait");
            return (Criteria) this;
        }

        public Criteria andRepayAccountCapitalWaitIsNull() {
            addCriterion("repay_account_capital_wait is null");
            return (Criteria) this;
        }

        public Criteria andRepayAccountCapitalWaitIsNotNull() {
            addCriterion("repay_account_capital_wait is not null");
            return (Criteria) this;
        }

        public Criteria andRepayAccountCapitalWaitEqualTo(BigDecimal value) {
            addCriterion("repay_account_capital_wait =", value, "repayAccountCapitalWait");
            return (Criteria) this;
        }

        public Criteria andRepayAccountCapitalWaitNotEqualTo(BigDecimal value) {
            addCriterion("repay_account_capital_wait <>", value, "repayAccountCapitalWait");
            return (Criteria) this;
        }

        public Criteria andRepayAccountCapitalWaitGreaterThan(BigDecimal value) {
            addCriterion("repay_account_capital_wait >", value, "repayAccountCapitalWait");
            return (Criteria) this;
        }

        public Criteria andRepayAccountCapitalWaitGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_account_capital_wait >=", value, "repayAccountCapitalWait");
            return (Criteria) this;
        }

        public Criteria andRepayAccountCapitalWaitLessThan(BigDecimal value) {
            addCriterion("repay_account_capital_wait <", value, "repayAccountCapitalWait");
            return (Criteria) this;
        }

        public Criteria andRepayAccountCapitalWaitLessThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_account_capital_wait <=", value, "repayAccountCapitalWait");
            return (Criteria) this;
        }

        public Criteria andRepayAccountCapitalWaitIn(List<BigDecimal> values) {
            addCriterion("repay_account_capital_wait in", values, "repayAccountCapitalWait");
            return (Criteria) this;
        }

        public Criteria andRepayAccountCapitalWaitNotIn(List<BigDecimal> values) {
            addCriterion("repay_account_capital_wait not in", values, "repayAccountCapitalWait");
            return (Criteria) this;
        }

        public Criteria andRepayAccountCapitalWaitBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_account_capital_wait between", value1, value2, "repayAccountCapitalWait");
            return (Criteria) this;
        }

        public Criteria andRepayAccountCapitalWaitNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_account_capital_wait not between", value1, value2, "repayAccountCapitalWait");
            return (Criteria) this;
        }

        public Criteria andBorrowManagerIsNull() {
            addCriterion("borrow_manager is null");
            return (Criteria) this;
        }

        public Criteria andBorrowManagerIsNotNull() {
            addCriterion("borrow_manager is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowManagerEqualTo(String value) {
            addCriterion("borrow_manager =", value, "borrowManager");
            return (Criteria) this;
        }

        public Criteria andBorrowManagerNotEqualTo(String value) {
            addCriterion("borrow_manager <>", value, "borrowManager");
            return (Criteria) this;
        }

        public Criteria andBorrowManagerGreaterThan(String value) {
            addCriterion("borrow_manager >", value, "borrowManager");
            return (Criteria) this;
        }

        public Criteria andBorrowManagerGreaterThanOrEqualTo(String value) {
            addCriterion("borrow_manager >=", value, "borrowManager");
            return (Criteria) this;
        }

        public Criteria andBorrowManagerLessThan(String value) {
            addCriterion("borrow_manager <", value, "borrowManager");
            return (Criteria) this;
        }

        public Criteria andBorrowManagerLessThanOrEqualTo(String value) {
            addCriterion("borrow_manager <=", value, "borrowManager");
            return (Criteria) this;
        }

        public Criteria andBorrowManagerLike(String value) {
            addCriterion("borrow_manager like", value, "borrowManager");
            return (Criteria) this;
        }

        public Criteria andBorrowManagerNotLike(String value) {
            addCriterion("borrow_manager not like", value, "borrowManager");
            return (Criteria) this;
        }

        public Criteria andBorrowManagerIn(List<String> values) {
            addCriterion("borrow_manager in", values, "borrowManager");
            return (Criteria) this;
        }

        public Criteria andBorrowManagerNotIn(List<String> values) {
            addCriterion("borrow_manager not in", values, "borrowManager");
            return (Criteria) this;
        }

        public Criteria andBorrowManagerBetween(String value1, String value2) {
            addCriterion("borrow_manager between", value1, value2, "borrowManager");
            return (Criteria) this;
        }

        public Criteria andBorrowManagerNotBetween(String value1, String value2) {
            addCriterion("borrow_manager not between", value1, value2, "borrowManager");
            return (Criteria) this;
        }

        public Criteria andServiceFeeRateIsNull() {
            addCriterion("service_fee_rate is null");
            return (Criteria) this;
        }

        public Criteria andServiceFeeRateIsNotNull() {
            addCriterion("service_fee_rate is not null");
            return (Criteria) this;
        }

        public Criteria andServiceFeeRateEqualTo(String value) {
            addCriterion("service_fee_rate =", value, "serviceFeeRate");
            return (Criteria) this;
        }

        public Criteria andServiceFeeRateNotEqualTo(String value) {
            addCriterion("service_fee_rate <>", value, "serviceFeeRate");
            return (Criteria) this;
        }

        public Criteria andServiceFeeRateGreaterThan(String value) {
            addCriterion("service_fee_rate >", value, "serviceFeeRate");
            return (Criteria) this;
        }

        public Criteria andServiceFeeRateGreaterThanOrEqualTo(String value) {
            addCriterion("service_fee_rate >=", value, "serviceFeeRate");
            return (Criteria) this;
        }

        public Criteria andServiceFeeRateLessThan(String value) {
            addCriterion("service_fee_rate <", value, "serviceFeeRate");
            return (Criteria) this;
        }

        public Criteria andServiceFeeRateLessThanOrEqualTo(String value) {
            addCriterion("service_fee_rate <=", value, "serviceFeeRate");
            return (Criteria) this;
        }

        public Criteria andServiceFeeRateLike(String value) {
            addCriterion("service_fee_rate like", value, "serviceFeeRate");
            return (Criteria) this;
        }

        public Criteria andServiceFeeRateNotLike(String value) {
            addCriterion("service_fee_rate not like", value, "serviceFeeRate");
            return (Criteria) this;
        }

        public Criteria andServiceFeeRateIn(List<String> values) {
            addCriterion("service_fee_rate in", values, "serviceFeeRate");
            return (Criteria) this;
        }

        public Criteria andServiceFeeRateNotIn(List<String> values) {
            addCriterion("service_fee_rate not in", values, "serviceFeeRate");
            return (Criteria) this;
        }

        public Criteria andServiceFeeRateBetween(String value1, String value2) {
            addCriterion("service_fee_rate between", value1, value2, "serviceFeeRate");
            return (Criteria) this;
        }

        public Criteria andServiceFeeRateNotBetween(String value1, String value2) {
            addCriterion("service_fee_rate not between", value1, value2, "serviceFeeRate");
            return (Criteria) this;
        }

        public Criteria andManageFeeRateIsNull() {
            addCriterion("manage_fee_rate is null");
            return (Criteria) this;
        }

        public Criteria andManageFeeRateIsNotNull() {
            addCriterion("manage_fee_rate is not null");
            return (Criteria) this;
        }

        public Criteria andManageFeeRateEqualTo(String value) {
            addCriterion("manage_fee_rate =", value, "manageFeeRate");
            return (Criteria) this;
        }

        public Criteria andManageFeeRateNotEqualTo(String value) {
            addCriterion("manage_fee_rate <>", value, "manageFeeRate");
            return (Criteria) this;
        }

        public Criteria andManageFeeRateGreaterThan(String value) {
            addCriterion("manage_fee_rate >", value, "manageFeeRate");
            return (Criteria) this;
        }

        public Criteria andManageFeeRateGreaterThanOrEqualTo(String value) {
            addCriterion("manage_fee_rate >=", value, "manageFeeRate");
            return (Criteria) this;
        }

        public Criteria andManageFeeRateLessThan(String value) {
            addCriterion("manage_fee_rate <", value, "manageFeeRate");
            return (Criteria) this;
        }

        public Criteria andManageFeeRateLessThanOrEqualTo(String value) {
            addCriterion("manage_fee_rate <=", value, "manageFeeRate");
            return (Criteria) this;
        }

        public Criteria andManageFeeRateLike(String value) {
            addCriterion("manage_fee_rate like", value, "manageFeeRate");
            return (Criteria) this;
        }

        public Criteria andManageFeeRateNotLike(String value) {
            addCriterion("manage_fee_rate not like", value, "manageFeeRate");
            return (Criteria) this;
        }

        public Criteria andManageFeeRateIn(List<String> values) {
            addCriterion("manage_fee_rate in", values, "manageFeeRate");
            return (Criteria) this;
        }

        public Criteria andManageFeeRateNotIn(List<String> values) {
            addCriterion("manage_fee_rate not in", values, "manageFeeRate");
            return (Criteria) this;
        }

        public Criteria andManageFeeRateBetween(String value1, String value2) {
            addCriterion("manage_fee_rate between", value1, value2, "manageFeeRate");
            return (Criteria) this;
        }

        public Criteria andManageFeeRateNotBetween(String value1, String value2) {
            addCriterion("manage_fee_rate not between", value1, value2, "manageFeeRate");
            return (Criteria) this;
        }

        public Criteria andDifferentialRateIsNull() {
            addCriterion("differential_rate is null");
            return (Criteria) this;
        }

        public Criteria andDifferentialRateIsNotNull() {
            addCriterion("differential_rate is not null");
            return (Criteria) this;
        }

        public Criteria andDifferentialRateEqualTo(String value) {
            addCriterion("differential_rate =", value, "differentialRate");
            return (Criteria) this;
        }

        public Criteria andDifferentialRateNotEqualTo(String value) {
            addCriterion("differential_rate <>", value, "differentialRate");
            return (Criteria) this;
        }

        public Criteria andDifferentialRateGreaterThan(String value) {
            addCriterion("differential_rate >", value, "differentialRate");
            return (Criteria) this;
        }

        public Criteria andDifferentialRateGreaterThanOrEqualTo(String value) {
            addCriterion("differential_rate >=", value, "differentialRate");
            return (Criteria) this;
        }

        public Criteria andDifferentialRateLessThan(String value) {
            addCriterion("differential_rate <", value, "differentialRate");
            return (Criteria) this;
        }

        public Criteria andDifferentialRateLessThanOrEqualTo(String value) {
            addCriterion("differential_rate <=", value, "differentialRate");
            return (Criteria) this;
        }

        public Criteria andDifferentialRateLike(String value) {
            addCriterion("differential_rate like", value, "differentialRate");
            return (Criteria) this;
        }

        public Criteria andDifferentialRateNotLike(String value) {
            addCriterion("differential_rate not like", value, "differentialRate");
            return (Criteria) this;
        }

        public Criteria andDifferentialRateIn(List<String> values) {
            addCriterion("differential_rate in", values, "differentialRate");
            return (Criteria) this;
        }

        public Criteria andDifferentialRateNotIn(List<String> values) {
            addCriterion("differential_rate not in", values, "differentialRate");
            return (Criteria) this;
        }

        public Criteria andDifferentialRateBetween(String value1, String value2) {
            addCriterion("differential_rate between", value1, value2, "differentialRate");
            return (Criteria) this;
        }

        public Criteria andDifferentialRateNotBetween(String value1, String value2) {
            addCriterion("differential_rate not between", value1, value2, "differentialRate");
            return (Criteria) this;
        }

        public Criteria andLateInterestRateIsNull() {
            addCriterion("late_interest_rate is null");
            return (Criteria) this;
        }

        public Criteria andLateInterestRateIsNotNull() {
            addCriterion("late_interest_rate is not null");
            return (Criteria) this;
        }

        public Criteria andLateInterestRateEqualTo(String value) {
            addCriterion("late_interest_rate =", value, "lateInterestRate");
            return (Criteria) this;
        }

        public Criteria andLateInterestRateNotEqualTo(String value) {
            addCriterion("late_interest_rate <>", value, "lateInterestRate");
            return (Criteria) this;
        }

        public Criteria andLateInterestRateGreaterThan(String value) {
            addCriterion("late_interest_rate >", value, "lateInterestRate");
            return (Criteria) this;
        }

        public Criteria andLateInterestRateGreaterThanOrEqualTo(String value) {
            addCriterion("late_interest_rate >=", value, "lateInterestRate");
            return (Criteria) this;
        }

        public Criteria andLateInterestRateLessThan(String value) {
            addCriterion("late_interest_rate <", value, "lateInterestRate");
            return (Criteria) this;
        }

        public Criteria andLateInterestRateLessThanOrEqualTo(String value) {
            addCriterion("late_interest_rate <=", value, "lateInterestRate");
            return (Criteria) this;
        }

        public Criteria andLateInterestRateLike(String value) {
            addCriterion("late_interest_rate like", value, "lateInterestRate");
            return (Criteria) this;
        }

        public Criteria andLateInterestRateNotLike(String value) {
            addCriterion("late_interest_rate not like", value, "lateInterestRate");
            return (Criteria) this;
        }

        public Criteria andLateInterestRateIn(List<String> values) {
            addCriterion("late_interest_rate in", values, "lateInterestRate");
            return (Criteria) this;
        }

        public Criteria andLateInterestRateNotIn(List<String> values) {
            addCriterion("late_interest_rate not in", values, "lateInterestRate");
            return (Criteria) this;
        }

        public Criteria andLateInterestRateBetween(String value1, String value2) {
            addCriterion("late_interest_rate between", value1, value2, "lateInterestRate");
            return (Criteria) this;
        }

        public Criteria andLateInterestRateNotBetween(String value1, String value2) {
            addCriterion("late_interest_rate not between", value1, value2, "lateInterestRate");
            return (Criteria) this;
        }

        public Criteria andLateFreeDaysIsNull() {
            addCriterion("late_free_days is null");
            return (Criteria) this;
        }

        public Criteria andLateFreeDaysIsNotNull() {
            addCriterion("late_free_days is not null");
            return (Criteria) this;
        }

        public Criteria andLateFreeDaysEqualTo(Integer value) {
            addCriterion("late_free_days =", value, "lateFreeDays");
            return (Criteria) this;
        }

        public Criteria andLateFreeDaysNotEqualTo(Integer value) {
            addCriterion("late_free_days <>", value, "lateFreeDays");
            return (Criteria) this;
        }

        public Criteria andLateFreeDaysGreaterThan(Integer value) {
            addCriterion("late_free_days >", value, "lateFreeDays");
            return (Criteria) this;
        }

        public Criteria andLateFreeDaysGreaterThanOrEqualTo(Integer value) {
            addCriterion("late_free_days >=", value, "lateFreeDays");
            return (Criteria) this;
        }

        public Criteria andLateFreeDaysLessThan(Integer value) {
            addCriterion("late_free_days <", value, "lateFreeDays");
            return (Criteria) this;
        }

        public Criteria andLateFreeDaysLessThanOrEqualTo(Integer value) {
            addCriterion("late_free_days <=", value, "lateFreeDays");
            return (Criteria) this;
        }

        public Criteria andLateFreeDaysIn(List<Integer> values) {
            addCriterion("late_free_days in", values, "lateFreeDays");
            return (Criteria) this;
        }

        public Criteria andLateFreeDaysNotIn(List<Integer> values) {
            addCriterion("late_free_days not in", values, "lateFreeDays");
            return (Criteria) this;
        }

        public Criteria andLateFreeDaysBetween(Integer value1, Integer value2) {
            addCriterion("late_free_days between", value1, value2, "lateFreeDays");
            return (Criteria) this;
        }

        public Criteria andLateFreeDaysNotBetween(Integer value1, Integer value2) {
            addCriterion("late_free_days not between", value1, value2, "lateFreeDays");
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

        public Criteria andUpdatetimeIsNull() {
            addCriterion("updatetime is null");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeIsNotNull() {
            addCriterion("updatetime is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeEqualTo(Date value) {
            addCriterion("updatetime =", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeNotEqualTo(Date value) {
            addCriterion("updatetime <>", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeGreaterThan(Date value) {
            addCriterion("updatetime >", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeGreaterThanOrEqualTo(Date value) {
            addCriterion("updatetime >=", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeLessThan(Date value) {
            addCriterion("updatetime <", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeLessThanOrEqualTo(Date value) {
            addCriterion("updatetime <=", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeIn(List<Date> values) {
            addCriterion("updatetime in", values, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeNotIn(List<Date> values) {
            addCriterion("updatetime not in", values, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeBetween(Date value1, Date value2) {
            addCriterion("updatetime between", value1, value2, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeNotBetween(Date value1, Date value2) {
            addCriterion("updatetime not between", value1, value2, "updatetime");
            return (Criteria) this;
        }

        public Criteria andIncreaseInterestFlagIsNull() {
            addCriterion("increase_interest_flag is null");
            return (Criteria) this;
        }

        public Criteria andIncreaseInterestFlagIsNotNull() {
            addCriterion("increase_interest_flag is not null");
            return (Criteria) this;
        }

        public Criteria andIncreaseInterestFlagEqualTo(Integer value) {
            addCriterion("increase_interest_flag =", value, "increaseInterestFlag");
            return (Criteria) this;
        }

        public Criteria andIncreaseInterestFlagNotEqualTo(Integer value) {
            addCriterion("increase_interest_flag <>", value, "increaseInterestFlag");
            return (Criteria) this;
        }

        public Criteria andIncreaseInterestFlagGreaterThan(Integer value) {
            addCriterion("increase_interest_flag >", value, "increaseInterestFlag");
            return (Criteria) this;
        }

        public Criteria andIncreaseInterestFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("increase_interest_flag >=", value, "increaseInterestFlag");
            return (Criteria) this;
        }

        public Criteria andIncreaseInterestFlagLessThan(Integer value) {
            addCriterion("increase_interest_flag <", value, "increaseInterestFlag");
            return (Criteria) this;
        }

        public Criteria andIncreaseInterestFlagLessThanOrEqualTo(Integer value) {
            addCriterion("increase_interest_flag <=", value, "increaseInterestFlag");
            return (Criteria) this;
        }

        public Criteria andIncreaseInterestFlagIn(List<Integer> values) {
            addCriterion("increase_interest_flag in", values, "increaseInterestFlag");
            return (Criteria) this;
        }

        public Criteria andIncreaseInterestFlagNotIn(List<Integer> values) {
            addCriterion("increase_interest_flag not in", values, "increaseInterestFlag");
            return (Criteria) this;
        }

        public Criteria andIncreaseInterestFlagBetween(Integer value1, Integer value2) {
            addCriterion("increase_interest_flag between", value1, value2, "increaseInterestFlag");
            return (Criteria) this;
        }

        public Criteria andIncreaseInterestFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("increase_interest_flag not between", value1, value2, "increaseInterestFlag");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalTypeIsNull() {
            addCriterion("repay_capital_type is null");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalTypeIsNotNull() {
            addCriterion("repay_capital_type is not null");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalTypeEqualTo(Integer value) {
            addCriterion("repay_capital_type =", value, "repayCapitalType");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalTypeNotEqualTo(Integer value) {
            addCriterion("repay_capital_type <>", value, "repayCapitalType");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalTypeGreaterThan(Integer value) {
            addCriterion("repay_capital_type >", value, "repayCapitalType");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("repay_capital_type >=", value, "repayCapitalType");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalTypeLessThan(Integer value) {
            addCriterion("repay_capital_type <", value, "repayCapitalType");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalTypeLessThanOrEqualTo(Integer value) {
            addCriterion("repay_capital_type <=", value, "repayCapitalType");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalTypeIn(List<Integer> values) {
            addCriterion("repay_capital_type in", values, "repayCapitalType");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalTypeNotIn(List<Integer> values) {
            addCriterion("repay_capital_type not in", values, "repayCapitalType");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalTypeBetween(Integer value1, Integer value2) {
            addCriterion("repay_capital_type between", value1, value2, "repayCapitalType");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("repay_capital_type not between", value1, value2, "repayCapitalType");
            return (Criteria) this;
        }

        public Criteria andInvestLevelIsNull() {
            addCriterion("invest_level is null");
            return (Criteria) this;
        }

        public Criteria andInvestLevelIsNotNull() {
            addCriterion("invest_level is not null");
            return (Criteria) this;
        }

        public Criteria andInvestLevelEqualTo(String value) {
            addCriterion("invest_level =", value, "investLevel");
            return (Criteria) this;
        }

        public Criteria andInvestLevelNotEqualTo(String value) {
            addCriterion("invest_level <>", value, "investLevel");
            return (Criteria) this;
        }

        public Criteria andInvestLevelGreaterThan(String value) {
            addCriterion("invest_level >", value, "investLevel");
            return (Criteria) this;
        }

        public Criteria andInvestLevelGreaterThanOrEqualTo(String value) {
            addCriterion("invest_level >=", value, "investLevel");
            return (Criteria) this;
        }

        public Criteria andInvestLevelLessThan(String value) {
            addCriterion("invest_level <", value, "investLevel");
            return (Criteria) this;
        }

        public Criteria andInvestLevelLessThanOrEqualTo(String value) {
            addCriterion("invest_level <=", value, "investLevel");
            return (Criteria) this;
        }

        public Criteria andInvestLevelLike(String value) {
            addCriterion("invest_level like", value, "investLevel");
            return (Criteria) this;
        }

        public Criteria andInvestLevelNotLike(String value) {
            addCriterion("invest_level not like", value, "investLevel");
            return (Criteria) this;
        }

        public Criteria andInvestLevelIn(List<String> values) {
            addCriterion("invest_level in", values, "investLevel");
            return (Criteria) this;
        }

        public Criteria andInvestLevelNotIn(List<String> values) {
            addCriterion("invest_level not in", values, "investLevel");
            return (Criteria) this;
        }

        public Criteria andInvestLevelBetween(String value1, String value2) {
            addCriterion("invest_level between", value1, value2, "investLevel");
            return (Criteria) this;
        }

        public Criteria andInvestLevelNotBetween(String value1, String value2) {
            addCriterion("invest_level not between", value1, value2, "investLevel");
            return (Criteria) this;
        }

        public Criteria andRegistBankmsgIsNull() {
            addCriterion("regist_bankmsg is null");
            return (Criteria) this;
        }

        public Criteria andRegistBankmsgIsNotNull() {
            addCriterion("regist_bankmsg is not null");
            return (Criteria) this;
        }

        public Criteria andRegistBankmsgEqualTo(String value) {
            addCriterion("regist_bankmsg =", value, "registBankmsg");
            return (Criteria) this;
        }

        public Criteria andRegistBankmsgNotEqualTo(String value) {
            addCriterion("regist_bankmsg <>", value, "registBankmsg");
            return (Criteria) this;
        }

        public Criteria andRegistBankmsgGreaterThan(String value) {
            addCriterion("regist_bankmsg >", value, "registBankmsg");
            return (Criteria) this;
        }

        public Criteria andRegistBankmsgGreaterThanOrEqualTo(String value) {
            addCriterion("regist_bankmsg >=", value, "registBankmsg");
            return (Criteria) this;
        }

        public Criteria andRegistBankmsgLessThan(String value) {
            addCriterion("regist_bankmsg <", value, "registBankmsg");
            return (Criteria) this;
        }

        public Criteria andRegistBankmsgLessThanOrEqualTo(String value) {
            addCriterion("regist_bankmsg <=", value, "registBankmsg");
            return (Criteria) this;
        }

        public Criteria andRegistBankmsgLike(String value) {
            addCriterion("regist_bankmsg like", value, "registBankmsg");
            return (Criteria) this;
        }

        public Criteria andRegistBankmsgNotLike(String value) {
            addCriterion("regist_bankmsg not like", value, "registBankmsg");
            return (Criteria) this;
        }

        public Criteria andRegistBankmsgIn(List<String> values) {
            addCriterion("regist_bankmsg in", values, "registBankmsg");
            return (Criteria) this;
        }

        public Criteria andRegistBankmsgNotIn(List<String> values) {
            addCriterion("regist_bankmsg not in", values, "registBankmsg");
            return (Criteria) this;
        }

        public Criteria andRegistBankmsgBetween(String value1, String value2) {
            addCriterion("regist_bankmsg between", value1, value2, "registBankmsg");
            return (Criteria) this;
        }

        public Criteria andRegistBankmsgNotBetween(String value1, String value2) {
            addCriterion("regist_bankmsg not between", value1, value2, "registBankmsg");
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