package com.hyjf.am.trade.dao.model.auto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HjhDebtDetailExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public HjhDebtDetailExample() {
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

        public Criteria andBorrowNameIsNull() {
            addCriterion("borrow_name is null");
            return (Criteria) this;
        }

        public Criteria andBorrowNameIsNotNull() {
            addCriterion("borrow_name is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowNameEqualTo(String value) {
            addCriterion("borrow_name =", value, "borrowName");
            return (Criteria) this;
        }

        public Criteria andBorrowNameNotEqualTo(String value) {
            addCriterion("borrow_name <>", value, "borrowName");
            return (Criteria) this;
        }

        public Criteria andBorrowNameGreaterThan(String value) {
            addCriterion("borrow_name >", value, "borrowName");
            return (Criteria) this;
        }

        public Criteria andBorrowNameGreaterThanOrEqualTo(String value) {
            addCriterion("borrow_name >=", value, "borrowName");
            return (Criteria) this;
        }

        public Criteria andBorrowNameLessThan(String value) {
            addCriterion("borrow_name <", value, "borrowName");
            return (Criteria) this;
        }

        public Criteria andBorrowNameLessThanOrEqualTo(String value) {
            addCriterion("borrow_name <=", value, "borrowName");
            return (Criteria) this;
        }

        public Criteria andBorrowNameLike(String value) {
            addCriterion("borrow_name like", value, "borrowName");
            return (Criteria) this;
        }

        public Criteria andBorrowNameNotLike(String value) {
            addCriterion("borrow_name not like", value, "borrowName");
            return (Criteria) this;
        }

        public Criteria andBorrowNameIn(List<String> values) {
            addCriterion("borrow_name in", values, "borrowName");
            return (Criteria) this;
        }

        public Criteria andBorrowNameNotIn(List<String> values) {
            addCriterion("borrow_name not in", values, "borrowName");
            return (Criteria) this;
        }

        public Criteria andBorrowNameBetween(String value1, String value2) {
            addCriterion("borrow_name between", value1, value2, "borrowName");
            return (Criteria) this;
        }

        public Criteria andBorrowNameNotBetween(String value1, String value2) {
            addCriterion("borrow_name not between", value1, value2, "borrowName");
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

        public Criteria andOrderTypeIsNull() {
            addCriterion("order_type is null");
            return (Criteria) this;
        }

        public Criteria andOrderTypeIsNotNull() {
            addCriterion("order_type is not null");
            return (Criteria) this;
        }

        public Criteria andOrderTypeEqualTo(Integer value) {
            addCriterion("order_type =", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeNotEqualTo(Integer value) {
            addCriterion("order_type <>", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeGreaterThan(Integer value) {
            addCriterion("order_type >", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("order_type >=", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeLessThan(Integer value) {
            addCriterion("order_type <", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeLessThanOrEqualTo(Integer value) {
            addCriterion("order_type <=", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeIn(List<Integer> values) {
            addCriterion("order_type in", values, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeNotIn(List<Integer> values) {
            addCriterion("order_type not in", values, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeBetween(Integer value1, Integer value2) {
            addCriterion("order_type between", value1, value2, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("order_type not between", value1, value2, "orderType");
            return (Criteria) this;
        }

        public Criteria andSourceTypeIsNull() {
            addCriterion("source_type is null");
            return (Criteria) this;
        }

        public Criteria andSourceTypeIsNotNull() {
            addCriterion("source_type is not null");
            return (Criteria) this;
        }

        public Criteria andSourceTypeEqualTo(Integer value) {
            addCriterion("source_type =", value, "sourceType");
            return (Criteria) this;
        }

        public Criteria andSourceTypeNotEqualTo(Integer value) {
            addCriterion("source_type <>", value, "sourceType");
            return (Criteria) this;
        }

        public Criteria andSourceTypeGreaterThan(Integer value) {
            addCriterion("source_type >", value, "sourceType");
            return (Criteria) this;
        }

        public Criteria andSourceTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("source_type >=", value, "sourceType");
            return (Criteria) this;
        }

        public Criteria andSourceTypeLessThan(Integer value) {
            addCriterion("source_type <", value, "sourceType");
            return (Criteria) this;
        }

        public Criteria andSourceTypeLessThanOrEqualTo(Integer value) {
            addCriterion("source_type <=", value, "sourceType");
            return (Criteria) this;
        }

        public Criteria andSourceTypeIn(List<Integer> values) {
            addCriterion("source_type in", values, "sourceType");
            return (Criteria) this;
        }

        public Criteria andSourceTypeNotIn(List<Integer> values) {
            addCriterion("source_type not in", values, "sourceType");
            return (Criteria) this;
        }

        public Criteria andSourceTypeBetween(Integer value1, Integer value2) {
            addCriterion("source_type between", value1, value2, "sourceType");
            return (Criteria) this;
        }

        public Criteria andSourceTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("source_type not between", value1, value2, "sourceType");
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

        public Criteria andLoanTimeIsNull() {
            addCriterion("loan_time is null");
            return (Criteria) this;
        }

        public Criteria andLoanTimeIsNotNull() {
            addCriterion("loan_time is not null");
            return (Criteria) this;
        }

        public Criteria andLoanTimeEqualTo(Integer value) {
            addCriterion("loan_time =", value, "loanTime");
            return (Criteria) this;
        }

        public Criteria andLoanTimeNotEqualTo(Integer value) {
            addCriterion("loan_time <>", value, "loanTime");
            return (Criteria) this;
        }

        public Criteria andLoanTimeGreaterThan(Integer value) {
            addCriterion("loan_time >", value, "loanTime");
            return (Criteria) this;
        }

        public Criteria andLoanTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("loan_time >=", value, "loanTime");
            return (Criteria) this;
        }

        public Criteria andLoanTimeLessThan(Integer value) {
            addCriterion("loan_time <", value, "loanTime");
            return (Criteria) this;
        }

        public Criteria andLoanTimeLessThanOrEqualTo(Integer value) {
            addCriterion("loan_time <=", value, "loanTime");
            return (Criteria) this;
        }

        public Criteria andLoanTimeIn(List<Integer> values) {
            addCriterion("loan_time in", values, "loanTime");
            return (Criteria) this;
        }

        public Criteria andLoanTimeNotIn(List<Integer> values) {
            addCriterion("loan_time not in", values, "loanTime");
            return (Criteria) this;
        }

        public Criteria andLoanTimeBetween(Integer value1, Integer value2) {
            addCriterion("loan_time between", value1, value2, "loanTime");
            return (Criteria) this;
        }

        public Criteria andLoanTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("loan_time not between", value1, value2, "loanTime");
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

        public Criteria andRepayTimeEqualTo(Integer value) {
            addCriterion("repay_time =", value, "repayTime");
            return (Criteria) this;
        }

        public Criteria andRepayTimeNotEqualTo(Integer value) {
            addCriterion("repay_time <>", value, "repayTime");
            return (Criteria) this;
        }

        public Criteria andRepayTimeGreaterThan(Integer value) {
            addCriterion("repay_time >", value, "repayTime");
            return (Criteria) this;
        }

        public Criteria andRepayTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("repay_time >=", value, "repayTime");
            return (Criteria) this;
        }

        public Criteria andRepayTimeLessThan(Integer value) {
            addCriterion("repay_time <", value, "repayTime");
            return (Criteria) this;
        }

        public Criteria andRepayTimeLessThanOrEqualTo(Integer value) {
            addCriterion("repay_time <=", value, "repayTime");
            return (Criteria) this;
        }

        public Criteria andRepayTimeIn(List<Integer> values) {
            addCriterion("repay_time in", values, "repayTime");
            return (Criteria) this;
        }

        public Criteria andRepayTimeNotIn(List<Integer> values) {
            addCriterion("repay_time not in", values, "repayTime");
            return (Criteria) this;
        }

        public Criteria andRepayTimeBetween(Integer value1, Integer value2) {
            addCriterion("repay_time between", value1, value2, "repayTime");
            return (Criteria) this;
        }

        public Criteria andRepayTimeNotBetween(Integer value1, Integer value2) {
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

        public Criteria andRepayActionTimeEqualTo(Integer value) {
            addCriterion("repay_action_time =", value, "repayActionTime");
            return (Criteria) this;
        }

        public Criteria andRepayActionTimeNotEqualTo(Integer value) {
            addCriterion("repay_action_time <>", value, "repayActionTime");
            return (Criteria) this;
        }

        public Criteria andRepayActionTimeGreaterThan(Integer value) {
            addCriterion("repay_action_time >", value, "repayActionTime");
            return (Criteria) this;
        }

        public Criteria andRepayActionTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("repay_action_time >=", value, "repayActionTime");
            return (Criteria) this;
        }

        public Criteria andRepayActionTimeLessThan(Integer value) {
            addCriterion("repay_action_time <", value, "repayActionTime");
            return (Criteria) this;
        }

        public Criteria andRepayActionTimeLessThanOrEqualTo(Integer value) {
            addCriterion("repay_action_time <=", value, "repayActionTime");
            return (Criteria) this;
        }

        public Criteria andRepayActionTimeIn(List<Integer> values) {
            addCriterion("repay_action_time in", values, "repayActionTime");
            return (Criteria) this;
        }

        public Criteria andRepayActionTimeNotIn(List<Integer> values) {
            addCriterion("repay_action_time not in", values, "repayActionTime");
            return (Criteria) this;
        }

        public Criteria andRepayActionTimeBetween(Integer value1, Integer value2) {
            addCriterion("repay_action_time between", value1, value2, "repayActionTime");
            return (Criteria) this;
        }

        public Criteria andRepayActionTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("repay_action_time not between", value1, value2, "repayActionTime");
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

        public Criteria andAdvanceInterestIsNull() {
            addCriterion("advance_interest is null");
            return (Criteria) this;
        }

        public Criteria andAdvanceInterestIsNotNull() {
            addCriterion("advance_interest is not null");
            return (Criteria) this;
        }

        public Criteria andAdvanceInterestEqualTo(BigDecimal value) {
            addCriterion("advance_interest =", value, "advanceInterest");
            return (Criteria) this;
        }

        public Criteria andAdvanceInterestNotEqualTo(BigDecimal value) {
            addCriterion("advance_interest <>", value, "advanceInterest");
            return (Criteria) this;
        }

        public Criteria andAdvanceInterestGreaterThan(BigDecimal value) {
            addCriterion("advance_interest >", value, "advanceInterest");
            return (Criteria) this;
        }

        public Criteria andAdvanceInterestGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("advance_interest >=", value, "advanceInterest");
            return (Criteria) this;
        }

        public Criteria andAdvanceInterestLessThan(BigDecimal value) {
            addCriterion("advance_interest <", value, "advanceInterest");
            return (Criteria) this;
        }

        public Criteria andAdvanceInterestLessThanOrEqualTo(BigDecimal value) {
            addCriterion("advance_interest <=", value, "advanceInterest");
            return (Criteria) this;
        }

        public Criteria andAdvanceInterestIn(List<BigDecimal> values) {
            addCriterion("advance_interest in", values, "advanceInterest");
            return (Criteria) this;
        }

        public Criteria andAdvanceInterestNotIn(List<BigDecimal> values) {
            addCriterion("advance_interest not in", values, "advanceInterest");
            return (Criteria) this;
        }

        public Criteria andAdvanceInterestBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("advance_interest between", value1, value2, "advanceInterest");
            return (Criteria) this;
        }

        public Criteria andAdvanceInterestNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("advance_interest not between", value1, value2, "advanceInterest");
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

        public Criteria andLateInterestAssignedIsNull() {
            addCriterion("late_interest_assigned is null");
            return (Criteria) this;
        }

        public Criteria andLateInterestAssignedIsNotNull() {
            addCriterion("late_interest_assigned is not null");
            return (Criteria) this;
        }

        public Criteria andLateInterestAssignedEqualTo(BigDecimal value) {
            addCriterion("late_interest_assigned =", value, "lateInterestAssigned");
            return (Criteria) this;
        }

        public Criteria andLateInterestAssignedNotEqualTo(BigDecimal value) {
            addCriterion("late_interest_assigned <>", value, "lateInterestAssigned");
            return (Criteria) this;
        }

        public Criteria andLateInterestAssignedGreaterThan(BigDecimal value) {
            addCriterion("late_interest_assigned >", value, "lateInterestAssigned");
            return (Criteria) this;
        }

        public Criteria andLateInterestAssignedGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("late_interest_assigned >=", value, "lateInterestAssigned");
            return (Criteria) this;
        }

        public Criteria andLateInterestAssignedLessThan(BigDecimal value) {
            addCriterion("late_interest_assigned <", value, "lateInterestAssigned");
            return (Criteria) this;
        }

        public Criteria andLateInterestAssignedLessThanOrEqualTo(BigDecimal value) {
            addCriterion("late_interest_assigned <=", value, "lateInterestAssigned");
            return (Criteria) this;
        }

        public Criteria andLateInterestAssignedIn(List<BigDecimal> values) {
            addCriterion("late_interest_assigned in", values, "lateInterestAssigned");
            return (Criteria) this;
        }

        public Criteria andLateInterestAssignedNotIn(List<BigDecimal> values) {
            addCriterion("late_interest_assigned not in", values, "lateInterestAssigned");
            return (Criteria) this;
        }

        public Criteria andLateInterestAssignedBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("late_interest_assigned between", value1, value2, "lateInterestAssigned");
            return (Criteria) this;
        }

        public Criteria andLateInterestAssignedNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("late_interest_assigned not between", value1, value2, "lateInterestAssigned");
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

        public Criteria andDelayInterestAssignedIsNull() {
            addCriterion("delay_interest_assigned is null");
            return (Criteria) this;
        }

        public Criteria andDelayInterestAssignedIsNotNull() {
            addCriterion("delay_interest_assigned is not null");
            return (Criteria) this;
        }

        public Criteria andDelayInterestAssignedEqualTo(BigDecimal value) {
            addCriterion("delay_interest_assigned =", value, "delayInterestAssigned");
            return (Criteria) this;
        }

        public Criteria andDelayInterestAssignedNotEqualTo(BigDecimal value) {
            addCriterion("delay_interest_assigned <>", value, "delayInterestAssigned");
            return (Criteria) this;
        }

        public Criteria andDelayInterestAssignedGreaterThan(BigDecimal value) {
            addCriterion("delay_interest_assigned >", value, "delayInterestAssigned");
            return (Criteria) this;
        }

        public Criteria andDelayInterestAssignedGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("delay_interest_assigned >=", value, "delayInterestAssigned");
            return (Criteria) this;
        }

        public Criteria andDelayInterestAssignedLessThan(BigDecimal value) {
            addCriterion("delay_interest_assigned <", value, "delayInterestAssigned");
            return (Criteria) this;
        }

        public Criteria andDelayInterestAssignedLessThanOrEqualTo(BigDecimal value) {
            addCriterion("delay_interest_assigned <=", value, "delayInterestAssigned");
            return (Criteria) this;
        }

        public Criteria andDelayInterestAssignedIn(List<BigDecimal> values) {
            addCriterion("delay_interest_assigned in", values, "delayInterestAssigned");
            return (Criteria) this;
        }

        public Criteria andDelayInterestAssignedNotIn(List<BigDecimal> values) {
            addCriterion("delay_interest_assigned not in", values, "delayInterestAssigned");
            return (Criteria) this;
        }

        public Criteria andDelayInterestAssignedBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("delay_interest_assigned between", value1, value2, "delayInterestAssigned");
            return (Criteria) this;
        }

        public Criteria andDelayInterestAssignedNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("delay_interest_assigned not between", value1, value2, "delayInterestAssigned");
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

        public Criteria andExpireFairValueIsNull() {
            addCriterion("expire_fair_value is null");
            return (Criteria) this;
        }

        public Criteria andExpireFairValueIsNotNull() {
            addCriterion("expire_fair_value is not null");
            return (Criteria) this;
        }

        public Criteria andExpireFairValueEqualTo(BigDecimal value) {
            addCriterion("expire_fair_value =", value, "expireFairValue");
            return (Criteria) this;
        }

        public Criteria andExpireFairValueNotEqualTo(BigDecimal value) {
            addCriterion("expire_fair_value <>", value, "expireFairValue");
            return (Criteria) this;
        }

        public Criteria andExpireFairValueGreaterThan(BigDecimal value) {
            addCriterion("expire_fair_value >", value, "expireFairValue");
            return (Criteria) this;
        }

        public Criteria andExpireFairValueGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("expire_fair_value >=", value, "expireFairValue");
            return (Criteria) this;
        }

        public Criteria andExpireFairValueLessThan(BigDecimal value) {
            addCriterion("expire_fair_value <", value, "expireFairValue");
            return (Criteria) this;
        }

        public Criteria andExpireFairValueLessThanOrEqualTo(BigDecimal value) {
            addCriterion("expire_fair_value <=", value, "expireFairValue");
            return (Criteria) this;
        }

        public Criteria andExpireFairValueIn(List<BigDecimal> values) {
            addCriterion("expire_fair_value in", values, "expireFairValue");
            return (Criteria) this;
        }

        public Criteria andExpireFairValueNotIn(List<BigDecimal> values) {
            addCriterion("expire_fair_value not in", values, "expireFairValue");
            return (Criteria) this;
        }

        public Criteria andExpireFairValueBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("expire_fair_value between", value1, value2, "expireFairValue");
            return (Criteria) this;
        }

        public Criteria andExpireFairValueNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("expire_fair_value not between", value1, value2, "expireFairValue");
            return (Criteria) this;
        }

        public Criteria andLastLiquidationTimeIsNull() {
            addCriterion("last_liquidation_time is null");
            return (Criteria) this;
        }

        public Criteria andLastLiquidationTimeIsNotNull() {
            addCriterion("last_liquidation_time is not null");
            return (Criteria) this;
        }

        public Criteria andLastLiquidationTimeEqualTo(Integer value) {
            addCriterion("last_liquidation_time =", value, "lastLiquidationTime");
            return (Criteria) this;
        }

        public Criteria andLastLiquidationTimeNotEqualTo(Integer value) {
            addCriterion("last_liquidation_time <>", value, "lastLiquidationTime");
            return (Criteria) this;
        }

        public Criteria andLastLiquidationTimeGreaterThan(Integer value) {
            addCriterion("last_liquidation_time >", value, "lastLiquidationTime");
            return (Criteria) this;
        }

        public Criteria andLastLiquidationTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("last_liquidation_time >=", value, "lastLiquidationTime");
            return (Criteria) this;
        }

        public Criteria andLastLiquidationTimeLessThan(Integer value) {
            addCriterion("last_liquidation_time <", value, "lastLiquidationTime");
            return (Criteria) this;
        }

        public Criteria andLastLiquidationTimeLessThanOrEqualTo(Integer value) {
            addCriterion("last_liquidation_time <=", value, "lastLiquidationTime");
            return (Criteria) this;
        }

        public Criteria andLastLiquidationTimeIn(List<Integer> values) {
            addCriterion("last_liquidation_time in", values, "lastLiquidationTime");
            return (Criteria) this;
        }

        public Criteria andLastLiquidationTimeNotIn(List<Integer> values) {
            addCriterion("last_liquidation_time not in", values, "lastLiquidationTime");
            return (Criteria) this;
        }

        public Criteria andLastLiquidationTimeBetween(Integer value1, Integer value2) {
            addCriterion("last_liquidation_time between", value1, value2, "lastLiquidationTime");
            return (Criteria) this;
        }

        public Criteria andLastLiquidationTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("last_liquidation_time not between", value1, value2, "lastLiquidationTime");
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

        public Criteria andCreditTimesIsNull() {
            addCriterion("credit_times is null");
            return (Criteria) this;
        }

        public Criteria andCreditTimesIsNotNull() {
            addCriterion("credit_times is not null");
            return (Criteria) this;
        }

        public Criteria andCreditTimesEqualTo(Integer value) {
            addCriterion("credit_times =", value, "creditTimes");
            return (Criteria) this;
        }

        public Criteria andCreditTimesNotEqualTo(Integer value) {
            addCriterion("credit_times <>", value, "creditTimes");
            return (Criteria) this;
        }

        public Criteria andCreditTimesGreaterThan(Integer value) {
            addCriterion("credit_times >", value, "creditTimes");
            return (Criteria) this;
        }

        public Criteria andCreditTimesGreaterThanOrEqualTo(Integer value) {
            addCriterion("credit_times >=", value, "creditTimes");
            return (Criteria) this;
        }

        public Criteria andCreditTimesLessThan(Integer value) {
            addCriterion("credit_times <", value, "creditTimes");
            return (Criteria) this;
        }

        public Criteria andCreditTimesLessThanOrEqualTo(Integer value) {
            addCriterion("credit_times <=", value, "creditTimes");
            return (Criteria) this;
        }

        public Criteria andCreditTimesIn(List<Integer> values) {
            addCriterion("credit_times in", values, "creditTimes");
            return (Criteria) this;
        }

        public Criteria andCreditTimesNotIn(List<Integer> values) {
            addCriterion("credit_times not in", values, "creditTimes");
            return (Criteria) this;
        }

        public Criteria andCreditTimesBetween(Integer value1, Integer value2) {
            addCriterion("credit_times between", value1, value2, "creditTimes");
            return (Criteria) this;
        }

        public Criteria andCreditTimesNotBetween(Integer value1, Integer value2) {
            addCriterion("credit_times not between", value1, value2, "creditTimes");
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