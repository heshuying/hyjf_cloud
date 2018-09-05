package com.hyjf.am.trade.dao.model.auto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HjhDebtCreditExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public HjhDebtCreditExample() {
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

        public Criteria andPlanNidNewIsNull() {
            addCriterion("plan_nid_new is null");
            return (Criteria) this;
        }

        public Criteria andPlanNidNewIsNotNull() {
            addCriterion("plan_nid_new is not null");
            return (Criteria) this;
        }

        public Criteria andPlanNidNewEqualTo(String value) {
            addCriterion("plan_nid_new =", value, "planNidNew");
            return (Criteria) this;
        }

        public Criteria andPlanNidNewNotEqualTo(String value) {
            addCriterion("plan_nid_new <>", value, "planNidNew");
            return (Criteria) this;
        }

        public Criteria andPlanNidNewGreaterThan(String value) {
            addCriterion("plan_nid_new >", value, "planNidNew");
            return (Criteria) this;
        }

        public Criteria andPlanNidNewGreaterThanOrEqualTo(String value) {
            addCriterion("plan_nid_new >=", value, "planNidNew");
            return (Criteria) this;
        }

        public Criteria andPlanNidNewLessThan(String value) {
            addCriterion("plan_nid_new <", value, "planNidNew");
            return (Criteria) this;
        }

        public Criteria andPlanNidNewLessThanOrEqualTo(String value) {
            addCriterion("plan_nid_new <=", value, "planNidNew");
            return (Criteria) this;
        }

        public Criteria andPlanNidNewLike(String value) {
            addCriterion("plan_nid_new like", value, "planNidNew");
            return (Criteria) this;
        }

        public Criteria andPlanNidNewNotLike(String value) {
            addCriterion("plan_nid_new not like", value, "planNidNew");
            return (Criteria) this;
        }

        public Criteria andPlanNidNewIn(List<String> values) {
            addCriterion("plan_nid_new in", values, "planNidNew");
            return (Criteria) this;
        }

        public Criteria andPlanNidNewNotIn(List<String> values) {
            addCriterion("plan_nid_new not in", values, "planNidNew");
            return (Criteria) this;
        }

        public Criteria andPlanNidNewBetween(String value1, String value2) {
            addCriterion("plan_nid_new between", value1, value2, "planNidNew");
            return (Criteria) this;
        }

        public Criteria andPlanNidNewNotBetween(String value1, String value2) {
            addCriterion("plan_nid_new not between", value1, value2, "planNidNew");
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

        public Criteria andAssetTypeIsNull() {
            addCriterion("asset_type is null");
            return (Criteria) this;
        }

        public Criteria andAssetTypeIsNotNull() {
            addCriterion("asset_type is not null");
            return (Criteria) this;
        }

        public Criteria andAssetTypeEqualTo(Integer value) {
            addCriterion("asset_type =", value, "assetType");
            return (Criteria) this;
        }

        public Criteria andAssetTypeNotEqualTo(Integer value) {
            addCriterion("asset_type <>", value, "assetType");
            return (Criteria) this;
        }

        public Criteria andAssetTypeGreaterThan(Integer value) {
            addCriterion("asset_type >", value, "assetType");
            return (Criteria) this;
        }

        public Criteria andAssetTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("asset_type >=", value, "assetType");
            return (Criteria) this;
        }

        public Criteria andAssetTypeLessThan(Integer value) {
            addCriterion("asset_type <", value, "assetType");
            return (Criteria) this;
        }

        public Criteria andAssetTypeLessThanOrEqualTo(Integer value) {
            addCriterion("asset_type <=", value, "assetType");
            return (Criteria) this;
        }

        public Criteria andAssetTypeIn(List<Integer> values) {
            addCriterion("asset_type in", values, "assetType");
            return (Criteria) this;
        }

        public Criteria andAssetTypeNotIn(List<Integer> values) {
            addCriterion("asset_type not in", values, "assetType");
            return (Criteria) this;
        }

        public Criteria andAssetTypeBetween(Integer value1, Integer value2) {
            addCriterion("asset_type between", value1, value2, "assetType");
            return (Criteria) this;
        }

        public Criteria andAssetTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("asset_type not between", value1, value2, "assetType");
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

        public Criteria andActualAprIsNull() {
            addCriterion("actual_apr is null");
            return (Criteria) this;
        }

        public Criteria andActualAprIsNotNull() {
            addCriterion("actual_apr is not null");
            return (Criteria) this;
        }

        public Criteria andActualAprEqualTo(BigDecimal value) {
            addCriterion("actual_apr =", value, "actualApr");
            return (Criteria) this;
        }

        public Criteria andActualAprNotEqualTo(BigDecimal value) {
            addCriterion("actual_apr <>", value, "actualApr");
            return (Criteria) this;
        }

        public Criteria andActualAprGreaterThan(BigDecimal value) {
            addCriterion("actual_apr >", value, "actualApr");
            return (Criteria) this;
        }

        public Criteria andActualAprGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("actual_apr >=", value, "actualApr");
            return (Criteria) this;
        }

        public Criteria andActualAprLessThan(BigDecimal value) {
            addCriterion("actual_apr <", value, "actualApr");
            return (Criteria) this;
        }

        public Criteria andActualAprLessThanOrEqualTo(BigDecimal value) {
            addCriterion("actual_apr <=", value, "actualApr");
            return (Criteria) this;
        }

        public Criteria andActualAprIn(List<BigDecimal> values) {
            addCriterion("actual_apr in", values, "actualApr");
            return (Criteria) this;
        }

        public Criteria andActualAprNotIn(List<BigDecimal> values) {
            addCriterion("actual_apr not in", values, "actualApr");
            return (Criteria) this;
        }

        public Criteria andActualAprBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("actual_apr between", value1, value2, "actualApr");
            return (Criteria) this;
        }

        public Criteria andActualAprNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("actual_apr not between", value1, value2, "actualApr");
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

        public Criteria andSellOrderIdIsNull() {
            addCriterion("sell_order_id is null");
            return (Criteria) this;
        }

        public Criteria andSellOrderIdIsNotNull() {
            addCriterion("sell_order_id is not null");
            return (Criteria) this;
        }

        public Criteria andSellOrderIdEqualTo(String value) {
            addCriterion("sell_order_id =", value, "sellOrderId");
            return (Criteria) this;
        }

        public Criteria andSellOrderIdNotEqualTo(String value) {
            addCriterion("sell_order_id <>", value, "sellOrderId");
            return (Criteria) this;
        }

        public Criteria andSellOrderIdGreaterThan(String value) {
            addCriterion("sell_order_id >", value, "sellOrderId");
            return (Criteria) this;
        }

        public Criteria andSellOrderIdGreaterThanOrEqualTo(String value) {
            addCriterion("sell_order_id >=", value, "sellOrderId");
            return (Criteria) this;
        }

        public Criteria andSellOrderIdLessThan(String value) {
            addCriterion("sell_order_id <", value, "sellOrderId");
            return (Criteria) this;
        }

        public Criteria andSellOrderIdLessThanOrEqualTo(String value) {
            addCriterion("sell_order_id <=", value, "sellOrderId");
            return (Criteria) this;
        }

        public Criteria andSellOrderIdLike(String value) {
            addCriterion("sell_order_id like", value, "sellOrderId");
            return (Criteria) this;
        }

        public Criteria andSellOrderIdNotLike(String value) {
            addCriterion("sell_order_id not like", value, "sellOrderId");
            return (Criteria) this;
        }

        public Criteria andSellOrderIdIn(List<String> values) {
            addCriterion("sell_order_id in", values, "sellOrderId");
            return (Criteria) this;
        }

        public Criteria andSellOrderIdNotIn(List<String> values) {
            addCriterion("sell_order_id not in", values, "sellOrderId");
            return (Criteria) this;
        }

        public Criteria andSellOrderIdBetween(String value1, String value2) {
            addCriterion("sell_order_id between", value1, value2, "sellOrderId");
            return (Criteria) this;
        }

        public Criteria andSellOrderIdNotBetween(String value1, String value2) {
            addCriterion("sell_order_id not between", value1, value2, "sellOrderId");
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

        public Criteria andIsLiquidatesIsNull() {
            addCriterion("is_liquidates is null");
            return (Criteria) this;
        }

        public Criteria andIsLiquidatesIsNotNull() {
            addCriterion("is_liquidates is not null");
            return (Criteria) this;
        }

        public Criteria andIsLiquidatesEqualTo(Integer value) {
            addCriterion("is_liquidates =", value, "isLiquidates");
            return (Criteria) this;
        }

        public Criteria andIsLiquidatesNotEqualTo(Integer value) {
            addCriterion("is_liquidates <>", value, "isLiquidates");
            return (Criteria) this;
        }

        public Criteria andIsLiquidatesGreaterThan(Integer value) {
            addCriterion("is_liquidates >", value, "isLiquidates");
            return (Criteria) this;
        }

        public Criteria andIsLiquidatesGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_liquidates >=", value, "isLiquidates");
            return (Criteria) this;
        }

        public Criteria andIsLiquidatesLessThan(Integer value) {
            addCriterion("is_liquidates <", value, "isLiquidates");
            return (Criteria) this;
        }

        public Criteria andIsLiquidatesLessThanOrEqualTo(Integer value) {
            addCriterion("is_liquidates <=", value, "isLiquidates");
            return (Criteria) this;
        }

        public Criteria andIsLiquidatesIn(List<Integer> values) {
            addCriterion("is_liquidates in", values, "isLiquidates");
            return (Criteria) this;
        }

        public Criteria andIsLiquidatesNotIn(List<Integer> values) {
            addCriterion("is_liquidates not in", values, "isLiquidates");
            return (Criteria) this;
        }

        public Criteria andIsLiquidatesBetween(Integer value1, Integer value2) {
            addCriterion("is_liquidates between", value1, value2, "isLiquidates");
            return (Criteria) this;
        }

        public Criteria andIsLiquidatesNotBetween(Integer value1, Integer value2) {
            addCriterion("is_liquidates not between", value1, value2, "isLiquidates");
            return (Criteria) this;
        }

        public Criteria andHoldDaysIsNull() {
            addCriterion("hold_days is null");
            return (Criteria) this;
        }

        public Criteria andHoldDaysIsNotNull() {
            addCriterion("hold_days is not null");
            return (Criteria) this;
        }

        public Criteria andHoldDaysEqualTo(Integer value) {
            addCriterion("hold_days =", value, "holdDays");
            return (Criteria) this;
        }

        public Criteria andHoldDaysNotEqualTo(Integer value) {
            addCriterion("hold_days <>", value, "holdDays");
            return (Criteria) this;
        }

        public Criteria andHoldDaysGreaterThan(Integer value) {
            addCriterion("hold_days >", value, "holdDays");
            return (Criteria) this;
        }

        public Criteria andHoldDaysGreaterThanOrEqualTo(Integer value) {
            addCriterion("hold_days >=", value, "holdDays");
            return (Criteria) this;
        }

        public Criteria andHoldDaysLessThan(Integer value) {
            addCriterion("hold_days <", value, "holdDays");
            return (Criteria) this;
        }

        public Criteria andHoldDaysLessThanOrEqualTo(Integer value) {
            addCriterion("hold_days <=", value, "holdDays");
            return (Criteria) this;
        }

        public Criteria andHoldDaysIn(List<Integer> values) {
            addCriterion("hold_days in", values, "holdDays");
            return (Criteria) this;
        }

        public Criteria andHoldDaysNotIn(List<Integer> values) {
            addCriterion("hold_days not in", values, "holdDays");
            return (Criteria) this;
        }

        public Criteria andHoldDaysBetween(Integer value1, Integer value2) {
            addCriterion("hold_days between", value1, value2, "holdDays");
            return (Criteria) this;
        }

        public Criteria andHoldDaysNotBetween(Integer value1, Integer value2) {
            addCriterion("hold_days not between", value1, value2, "holdDays");
            return (Criteria) this;
        }

        public Criteria andRemainDaysIsNull() {
            addCriterion("remain_days is null");
            return (Criteria) this;
        }

        public Criteria andRemainDaysIsNotNull() {
            addCriterion("remain_days is not null");
            return (Criteria) this;
        }

        public Criteria andRemainDaysEqualTo(Integer value) {
            addCriterion("remain_days =", value, "remainDays");
            return (Criteria) this;
        }

        public Criteria andRemainDaysNotEqualTo(Integer value) {
            addCriterion("remain_days <>", value, "remainDays");
            return (Criteria) this;
        }

        public Criteria andRemainDaysGreaterThan(Integer value) {
            addCriterion("remain_days >", value, "remainDays");
            return (Criteria) this;
        }

        public Criteria andRemainDaysGreaterThanOrEqualTo(Integer value) {
            addCriterion("remain_days >=", value, "remainDays");
            return (Criteria) this;
        }

        public Criteria andRemainDaysLessThan(Integer value) {
            addCriterion("remain_days <", value, "remainDays");
            return (Criteria) this;
        }

        public Criteria andRemainDaysLessThanOrEqualTo(Integer value) {
            addCriterion("remain_days <=", value, "remainDays");
            return (Criteria) this;
        }

        public Criteria andRemainDaysIn(List<Integer> values) {
            addCriterion("remain_days in", values, "remainDays");
            return (Criteria) this;
        }

        public Criteria andRemainDaysNotIn(List<Integer> values) {
            addCriterion("remain_days not in", values, "remainDays");
            return (Criteria) this;
        }

        public Criteria andRemainDaysBetween(Integer value1, Integer value2) {
            addCriterion("remain_days between", value1, value2, "remainDays");
            return (Criteria) this;
        }

        public Criteria andRemainDaysNotBetween(Integer value1, Integer value2) {
            addCriterion("remain_days not between", value1, value2, "remainDays");
            return (Criteria) this;
        }

        public Criteria andDuringDaysIsNull() {
            addCriterion("during_days is null");
            return (Criteria) this;
        }

        public Criteria andDuringDaysIsNotNull() {
            addCriterion("during_days is not null");
            return (Criteria) this;
        }

        public Criteria andDuringDaysEqualTo(Integer value) {
            addCriterion("during_days =", value, "duringDays");
            return (Criteria) this;
        }

        public Criteria andDuringDaysNotEqualTo(Integer value) {
            addCriterion("during_days <>", value, "duringDays");
            return (Criteria) this;
        }

        public Criteria andDuringDaysGreaterThan(Integer value) {
            addCriterion("during_days >", value, "duringDays");
            return (Criteria) this;
        }

        public Criteria andDuringDaysGreaterThanOrEqualTo(Integer value) {
            addCriterion("during_days >=", value, "duringDays");
            return (Criteria) this;
        }

        public Criteria andDuringDaysLessThan(Integer value) {
            addCriterion("during_days <", value, "duringDays");
            return (Criteria) this;
        }

        public Criteria andDuringDaysLessThanOrEqualTo(Integer value) {
            addCriterion("during_days <=", value, "duringDays");
            return (Criteria) this;
        }

        public Criteria andDuringDaysIn(List<Integer> values) {
            addCriterion("during_days in", values, "duringDays");
            return (Criteria) this;
        }

        public Criteria andDuringDaysNotIn(List<Integer> values) {
            addCriterion("during_days not in", values, "duringDays");
            return (Criteria) this;
        }

        public Criteria andDuringDaysBetween(Integer value1, Integer value2) {
            addCriterion("during_days between", value1, value2, "duringDays");
            return (Criteria) this;
        }

        public Criteria andDuringDaysNotBetween(Integer value1, Integer value2) {
            addCriterion("during_days not between", value1, value2, "duringDays");
            return (Criteria) this;
        }

        public Criteria andAssignPeriodIsNull() {
            addCriterion("assign_period is null");
            return (Criteria) this;
        }

        public Criteria andAssignPeriodIsNotNull() {
            addCriterion("assign_period is not null");
            return (Criteria) this;
        }

        public Criteria andAssignPeriodEqualTo(Integer value) {
            addCriterion("assign_period =", value, "assignPeriod");
            return (Criteria) this;
        }

        public Criteria andAssignPeriodNotEqualTo(Integer value) {
            addCriterion("assign_period <>", value, "assignPeriod");
            return (Criteria) this;
        }

        public Criteria andAssignPeriodGreaterThan(Integer value) {
            addCriterion("assign_period >", value, "assignPeriod");
            return (Criteria) this;
        }

        public Criteria andAssignPeriodGreaterThanOrEqualTo(Integer value) {
            addCriterion("assign_period >=", value, "assignPeriod");
            return (Criteria) this;
        }

        public Criteria andAssignPeriodLessThan(Integer value) {
            addCriterion("assign_period <", value, "assignPeriod");
            return (Criteria) this;
        }

        public Criteria andAssignPeriodLessThanOrEqualTo(Integer value) {
            addCriterion("assign_period <=", value, "assignPeriod");
            return (Criteria) this;
        }

        public Criteria andAssignPeriodIn(List<Integer> values) {
            addCriterion("assign_period in", values, "assignPeriod");
            return (Criteria) this;
        }

        public Criteria andAssignPeriodNotIn(List<Integer> values) {
            addCriterion("assign_period not in", values, "assignPeriod");
            return (Criteria) this;
        }

        public Criteria andAssignPeriodBetween(Integer value1, Integer value2) {
            addCriterion("assign_period between", value1, value2, "assignPeriod");
            return (Criteria) this;
        }

        public Criteria andAssignPeriodNotBetween(Integer value1, Integer value2) {
            addCriterion("assign_period not between", value1, value2, "assignPeriod");
            return (Criteria) this;
        }

        public Criteria andLiquidatesPeriodIsNull() {
            addCriterion("liquidates_period is null");
            return (Criteria) this;
        }

        public Criteria andLiquidatesPeriodIsNotNull() {
            addCriterion("liquidates_period is not null");
            return (Criteria) this;
        }

        public Criteria andLiquidatesPeriodEqualTo(Integer value) {
            addCriterion("liquidates_period =", value, "liquidatesPeriod");
            return (Criteria) this;
        }

        public Criteria andLiquidatesPeriodNotEqualTo(Integer value) {
            addCriterion("liquidates_period <>", value, "liquidatesPeriod");
            return (Criteria) this;
        }

        public Criteria andLiquidatesPeriodGreaterThan(Integer value) {
            addCriterion("liquidates_period >", value, "liquidatesPeriod");
            return (Criteria) this;
        }

        public Criteria andLiquidatesPeriodGreaterThanOrEqualTo(Integer value) {
            addCriterion("liquidates_period >=", value, "liquidatesPeriod");
            return (Criteria) this;
        }

        public Criteria andLiquidatesPeriodLessThan(Integer value) {
            addCriterion("liquidates_period <", value, "liquidatesPeriod");
            return (Criteria) this;
        }

        public Criteria andLiquidatesPeriodLessThanOrEqualTo(Integer value) {
            addCriterion("liquidates_period <=", value, "liquidatesPeriod");
            return (Criteria) this;
        }

        public Criteria andLiquidatesPeriodIn(List<Integer> values) {
            addCriterion("liquidates_period in", values, "liquidatesPeriod");
            return (Criteria) this;
        }

        public Criteria andLiquidatesPeriodNotIn(List<Integer> values) {
            addCriterion("liquidates_period not in", values, "liquidatesPeriod");
            return (Criteria) this;
        }

        public Criteria andLiquidatesPeriodBetween(Integer value1, Integer value2) {
            addCriterion("liquidates_period between", value1, value2, "liquidatesPeriod");
            return (Criteria) this;
        }

        public Criteria andLiquidatesPeriodNotBetween(Integer value1, Integer value2) {
            addCriterion("liquidates_period not between", value1, value2, "liquidatesPeriod");
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

        public Criteria andLiquidationFairValueIsNull() {
            addCriterion("liquidation_fair_value is null");
            return (Criteria) this;
        }

        public Criteria andLiquidationFairValueIsNotNull() {
            addCriterion("liquidation_fair_value is not null");
            return (Criteria) this;
        }

        public Criteria andLiquidationFairValueEqualTo(BigDecimal value) {
            addCriterion("liquidation_fair_value =", value, "liquidationFairValue");
            return (Criteria) this;
        }

        public Criteria andLiquidationFairValueNotEqualTo(BigDecimal value) {
            addCriterion("liquidation_fair_value <>", value, "liquidationFairValue");
            return (Criteria) this;
        }

        public Criteria andLiquidationFairValueGreaterThan(BigDecimal value) {
            addCriterion("liquidation_fair_value >", value, "liquidationFairValue");
            return (Criteria) this;
        }

        public Criteria andLiquidationFairValueGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("liquidation_fair_value >=", value, "liquidationFairValue");
            return (Criteria) this;
        }

        public Criteria andLiquidationFairValueLessThan(BigDecimal value) {
            addCriterion("liquidation_fair_value <", value, "liquidationFairValue");
            return (Criteria) this;
        }

        public Criteria andLiquidationFairValueLessThanOrEqualTo(BigDecimal value) {
            addCriterion("liquidation_fair_value <=", value, "liquidationFairValue");
            return (Criteria) this;
        }

        public Criteria andLiquidationFairValueIn(List<BigDecimal> values) {
            addCriterion("liquidation_fair_value in", values, "liquidationFairValue");
            return (Criteria) this;
        }

        public Criteria andLiquidationFairValueNotIn(List<BigDecimal> values) {
            addCriterion("liquidation_fair_value not in", values, "liquidationFairValue");
            return (Criteria) this;
        }

        public Criteria andLiquidationFairValueBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("liquidation_fair_value between", value1, value2, "liquidationFairValue");
            return (Criteria) this;
        }

        public Criteria andLiquidationFairValueNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("liquidation_fair_value not between", value1, value2, "liquidationFairValue");
            return (Criteria) this;
        }

        public Criteria andLiquidatesCapitalIsNull() {
            addCriterion("liquidates_capital is null");
            return (Criteria) this;
        }

        public Criteria andLiquidatesCapitalIsNotNull() {
            addCriterion("liquidates_capital is not null");
            return (Criteria) this;
        }

        public Criteria andLiquidatesCapitalEqualTo(BigDecimal value) {
            addCriterion("liquidates_capital =", value, "liquidatesCapital");
            return (Criteria) this;
        }

        public Criteria andLiquidatesCapitalNotEqualTo(BigDecimal value) {
            addCriterion("liquidates_capital <>", value, "liquidatesCapital");
            return (Criteria) this;
        }

        public Criteria andLiquidatesCapitalGreaterThan(BigDecimal value) {
            addCriterion("liquidates_capital >", value, "liquidatesCapital");
            return (Criteria) this;
        }

        public Criteria andLiquidatesCapitalGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("liquidates_capital >=", value, "liquidatesCapital");
            return (Criteria) this;
        }

        public Criteria andLiquidatesCapitalLessThan(BigDecimal value) {
            addCriterion("liquidates_capital <", value, "liquidatesCapital");
            return (Criteria) this;
        }

        public Criteria andLiquidatesCapitalLessThanOrEqualTo(BigDecimal value) {
            addCriterion("liquidates_capital <=", value, "liquidatesCapital");
            return (Criteria) this;
        }

        public Criteria andLiquidatesCapitalIn(List<BigDecimal> values) {
            addCriterion("liquidates_capital in", values, "liquidatesCapital");
            return (Criteria) this;
        }

        public Criteria andLiquidatesCapitalNotIn(List<BigDecimal> values) {
            addCriterion("liquidates_capital not in", values, "liquidatesCapital");
            return (Criteria) this;
        }

        public Criteria andLiquidatesCapitalBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("liquidates_capital between", value1, value2, "liquidatesCapital");
            return (Criteria) this;
        }

        public Criteria andLiquidatesCapitalNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("liquidates_capital not between", value1, value2, "liquidatesCapital");
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

        public Criteria andCreditDelayInterestIsNull() {
            addCriterion("credit_delay_interest is null");
            return (Criteria) this;
        }

        public Criteria andCreditDelayInterestIsNotNull() {
            addCriterion("credit_delay_interest is not null");
            return (Criteria) this;
        }

        public Criteria andCreditDelayInterestEqualTo(BigDecimal value) {
            addCriterion("credit_delay_interest =", value, "creditDelayInterest");
            return (Criteria) this;
        }

        public Criteria andCreditDelayInterestNotEqualTo(BigDecimal value) {
            addCriterion("credit_delay_interest <>", value, "creditDelayInterest");
            return (Criteria) this;
        }

        public Criteria andCreditDelayInterestGreaterThan(BigDecimal value) {
            addCriterion("credit_delay_interest >", value, "creditDelayInterest");
            return (Criteria) this;
        }

        public Criteria andCreditDelayInterestGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("credit_delay_interest >=", value, "creditDelayInterest");
            return (Criteria) this;
        }

        public Criteria andCreditDelayInterestLessThan(BigDecimal value) {
            addCriterion("credit_delay_interest <", value, "creditDelayInterest");
            return (Criteria) this;
        }

        public Criteria andCreditDelayInterestLessThanOrEqualTo(BigDecimal value) {
            addCriterion("credit_delay_interest <=", value, "creditDelayInterest");
            return (Criteria) this;
        }

        public Criteria andCreditDelayInterestIn(List<BigDecimal> values) {
            addCriterion("credit_delay_interest in", values, "creditDelayInterest");
            return (Criteria) this;
        }

        public Criteria andCreditDelayInterestNotIn(List<BigDecimal> values) {
            addCriterion("credit_delay_interest not in", values, "creditDelayInterest");
            return (Criteria) this;
        }

        public Criteria andCreditDelayInterestBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("credit_delay_interest between", value1, value2, "creditDelayInterest");
            return (Criteria) this;
        }

        public Criteria andCreditDelayInterestNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("credit_delay_interest not between", value1, value2, "creditDelayInterest");
            return (Criteria) this;
        }

        public Criteria andCreditLateInterestIsNull() {
            addCriterion("credit_late_interest is null");
            return (Criteria) this;
        }

        public Criteria andCreditLateInterestIsNotNull() {
            addCriterion("credit_late_interest is not null");
            return (Criteria) this;
        }

        public Criteria andCreditLateInterestEqualTo(BigDecimal value) {
            addCriterion("credit_late_interest =", value, "creditLateInterest");
            return (Criteria) this;
        }

        public Criteria andCreditLateInterestNotEqualTo(BigDecimal value) {
            addCriterion("credit_late_interest <>", value, "creditLateInterest");
            return (Criteria) this;
        }

        public Criteria andCreditLateInterestGreaterThan(BigDecimal value) {
            addCriterion("credit_late_interest >", value, "creditLateInterest");
            return (Criteria) this;
        }

        public Criteria andCreditLateInterestGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("credit_late_interest >=", value, "creditLateInterest");
            return (Criteria) this;
        }

        public Criteria andCreditLateInterestLessThan(BigDecimal value) {
            addCriterion("credit_late_interest <", value, "creditLateInterest");
            return (Criteria) this;
        }

        public Criteria andCreditLateInterestLessThanOrEqualTo(BigDecimal value) {
            addCriterion("credit_late_interest <=", value, "creditLateInterest");
            return (Criteria) this;
        }

        public Criteria andCreditLateInterestIn(List<BigDecimal> values) {
            addCriterion("credit_late_interest in", values, "creditLateInterest");
            return (Criteria) this;
        }

        public Criteria andCreditLateInterestNotIn(List<BigDecimal> values) {
            addCriterion("credit_late_interest not in", values, "creditLateInterest");
            return (Criteria) this;
        }

        public Criteria andCreditLateInterestBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("credit_late_interest between", value1, value2, "creditLateInterest");
            return (Criteria) this;
        }

        public Criteria andCreditLateInterestNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("credit_late_interest not between", value1, value2, "creditLateInterest");
            return (Criteria) this;
        }

        public Criteria andCreditAccountAssignedIsNull() {
            addCriterion("credit_account_assigned is null");
            return (Criteria) this;
        }

        public Criteria andCreditAccountAssignedIsNotNull() {
            addCriterion("credit_account_assigned is not null");
            return (Criteria) this;
        }

        public Criteria andCreditAccountAssignedEqualTo(BigDecimal value) {
            addCriterion("credit_account_assigned =", value, "creditAccountAssigned");
            return (Criteria) this;
        }

        public Criteria andCreditAccountAssignedNotEqualTo(BigDecimal value) {
            addCriterion("credit_account_assigned <>", value, "creditAccountAssigned");
            return (Criteria) this;
        }

        public Criteria andCreditAccountAssignedGreaterThan(BigDecimal value) {
            addCriterion("credit_account_assigned >", value, "creditAccountAssigned");
            return (Criteria) this;
        }

        public Criteria andCreditAccountAssignedGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("credit_account_assigned >=", value, "creditAccountAssigned");
            return (Criteria) this;
        }

        public Criteria andCreditAccountAssignedLessThan(BigDecimal value) {
            addCriterion("credit_account_assigned <", value, "creditAccountAssigned");
            return (Criteria) this;
        }

        public Criteria andCreditAccountAssignedLessThanOrEqualTo(BigDecimal value) {
            addCriterion("credit_account_assigned <=", value, "creditAccountAssigned");
            return (Criteria) this;
        }

        public Criteria andCreditAccountAssignedIn(List<BigDecimal> values) {
            addCriterion("credit_account_assigned in", values, "creditAccountAssigned");
            return (Criteria) this;
        }

        public Criteria andCreditAccountAssignedNotIn(List<BigDecimal> values) {
            addCriterion("credit_account_assigned not in", values, "creditAccountAssigned");
            return (Criteria) this;
        }

        public Criteria andCreditAccountAssignedBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("credit_account_assigned between", value1, value2, "creditAccountAssigned");
            return (Criteria) this;
        }

        public Criteria andCreditAccountAssignedNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("credit_account_assigned not between", value1, value2, "creditAccountAssigned");
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

        public Criteria andCreditDelayInterestAssignedIsNull() {
            addCriterion("credit_delay_interest_assigned is null");
            return (Criteria) this;
        }

        public Criteria andCreditDelayInterestAssignedIsNotNull() {
            addCriterion("credit_delay_interest_assigned is not null");
            return (Criteria) this;
        }

        public Criteria andCreditDelayInterestAssignedEqualTo(BigDecimal value) {
            addCriterion("credit_delay_interest_assigned =", value, "creditDelayInterestAssigned");
            return (Criteria) this;
        }

        public Criteria andCreditDelayInterestAssignedNotEqualTo(BigDecimal value) {
            addCriterion("credit_delay_interest_assigned <>", value, "creditDelayInterestAssigned");
            return (Criteria) this;
        }

        public Criteria andCreditDelayInterestAssignedGreaterThan(BigDecimal value) {
            addCriterion("credit_delay_interest_assigned >", value, "creditDelayInterestAssigned");
            return (Criteria) this;
        }

        public Criteria andCreditDelayInterestAssignedGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("credit_delay_interest_assigned >=", value, "creditDelayInterestAssigned");
            return (Criteria) this;
        }

        public Criteria andCreditDelayInterestAssignedLessThan(BigDecimal value) {
            addCriterion("credit_delay_interest_assigned <", value, "creditDelayInterestAssigned");
            return (Criteria) this;
        }

        public Criteria andCreditDelayInterestAssignedLessThanOrEqualTo(BigDecimal value) {
            addCriterion("credit_delay_interest_assigned <=", value, "creditDelayInterestAssigned");
            return (Criteria) this;
        }

        public Criteria andCreditDelayInterestAssignedIn(List<BigDecimal> values) {
            addCriterion("credit_delay_interest_assigned in", values, "creditDelayInterestAssigned");
            return (Criteria) this;
        }

        public Criteria andCreditDelayInterestAssignedNotIn(List<BigDecimal> values) {
            addCriterion("credit_delay_interest_assigned not in", values, "creditDelayInterestAssigned");
            return (Criteria) this;
        }

        public Criteria andCreditDelayInterestAssignedBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("credit_delay_interest_assigned between", value1, value2, "creditDelayInterestAssigned");
            return (Criteria) this;
        }

        public Criteria andCreditDelayInterestAssignedNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("credit_delay_interest_assigned not between", value1, value2, "creditDelayInterestAssigned");
            return (Criteria) this;
        }

        public Criteria andCreditLateInterestAssignedIsNull() {
            addCriterion("credit_late_interest_assigned is null");
            return (Criteria) this;
        }

        public Criteria andCreditLateInterestAssignedIsNotNull() {
            addCriterion("credit_late_interest_assigned is not null");
            return (Criteria) this;
        }

        public Criteria andCreditLateInterestAssignedEqualTo(BigDecimal value) {
            addCriterion("credit_late_interest_assigned =", value, "creditLateInterestAssigned");
            return (Criteria) this;
        }

        public Criteria andCreditLateInterestAssignedNotEqualTo(BigDecimal value) {
            addCriterion("credit_late_interest_assigned <>", value, "creditLateInterestAssigned");
            return (Criteria) this;
        }

        public Criteria andCreditLateInterestAssignedGreaterThan(BigDecimal value) {
            addCriterion("credit_late_interest_assigned >", value, "creditLateInterestAssigned");
            return (Criteria) this;
        }

        public Criteria andCreditLateInterestAssignedGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("credit_late_interest_assigned >=", value, "creditLateInterestAssigned");
            return (Criteria) this;
        }

        public Criteria andCreditLateInterestAssignedLessThan(BigDecimal value) {
            addCriterion("credit_late_interest_assigned <", value, "creditLateInterestAssigned");
            return (Criteria) this;
        }

        public Criteria andCreditLateInterestAssignedLessThanOrEqualTo(BigDecimal value) {
            addCriterion("credit_late_interest_assigned <=", value, "creditLateInterestAssigned");
            return (Criteria) this;
        }

        public Criteria andCreditLateInterestAssignedIn(List<BigDecimal> values) {
            addCriterion("credit_late_interest_assigned in", values, "creditLateInterestAssigned");
            return (Criteria) this;
        }

        public Criteria andCreditLateInterestAssignedNotIn(List<BigDecimal> values) {
            addCriterion("credit_late_interest_assigned not in", values, "creditLateInterestAssigned");
            return (Criteria) this;
        }

        public Criteria andCreditLateInterestAssignedBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("credit_late_interest_assigned between", value1, value2, "creditLateInterestAssigned");
            return (Criteria) this;
        }

        public Criteria andCreditLateInterestAssignedNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("credit_late_interest_assigned not between", value1, value2, "creditLateInterestAssigned");
            return (Criteria) this;
        }

        public Criteria andCreditAccountWaitIsNull() {
            addCriterion("credit_account_wait is null");
            return (Criteria) this;
        }

        public Criteria andCreditAccountWaitIsNotNull() {
            addCriterion("credit_account_wait is not null");
            return (Criteria) this;
        }

        public Criteria andCreditAccountWaitEqualTo(BigDecimal value) {
            addCriterion("credit_account_wait =", value, "creditAccountWait");
            return (Criteria) this;
        }

        public Criteria andCreditAccountWaitNotEqualTo(BigDecimal value) {
            addCriterion("credit_account_wait <>", value, "creditAccountWait");
            return (Criteria) this;
        }

        public Criteria andCreditAccountWaitGreaterThan(BigDecimal value) {
            addCriterion("credit_account_wait >", value, "creditAccountWait");
            return (Criteria) this;
        }

        public Criteria andCreditAccountWaitGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("credit_account_wait >=", value, "creditAccountWait");
            return (Criteria) this;
        }

        public Criteria andCreditAccountWaitLessThan(BigDecimal value) {
            addCriterion("credit_account_wait <", value, "creditAccountWait");
            return (Criteria) this;
        }

        public Criteria andCreditAccountWaitLessThanOrEqualTo(BigDecimal value) {
            addCriterion("credit_account_wait <=", value, "creditAccountWait");
            return (Criteria) this;
        }

        public Criteria andCreditAccountWaitIn(List<BigDecimal> values) {
            addCriterion("credit_account_wait in", values, "creditAccountWait");
            return (Criteria) this;
        }

        public Criteria andCreditAccountWaitNotIn(List<BigDecimal> values) {
            addCriterion("credit_account_wait not in", values, "creditAccountWait");
            return (Criteria) this;
        }

        public Criteria andCreditAccountWaitBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("credit_account_wait between", value1, value2, "creditAccountWait");
            return (Criteria) this;
        }

        public Criteria andCreditAccountWaitNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("credit_account_wait not between", value1, value2, "creditAccountWait");
            return (Criteria) this;
        }

        public Criteria andCreditCapitalWaitIsNull() {
            addCriterion("credit_capital_wait is null");
            return (Criteria) this;
        }

        public Criteria andCreditCapitalWaitIsNotNull() {
            addCriterion("credit_capital_wait is not null");
            return (Criteria) this;
        }

        public Criteria andCreditCapitalWaitEqualTo(BigDecimal value) {
            addCriterion("credit_capital_wait =", value, "creditCapitalWait");
            return (Criteria) this;
        }

        public Criteria andCreditCapitalWaitNotEqualTo(BigDecimal value) {
            addCriterion("credit_capital_wait <>", value, "creditCapitalWait");
            return (Criteria) this;
        }

        public Criteria andCreditCapitalWaitGreaterThan(BigDecimal value) {
            addCriterion("credit_capital_wait >", value, "creditCapitalWait");
            return (Criteria) this;
        }

        public Criteria andCreditCapitalWaitGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("credit_capital_wait >=", value, "creditCapitalWait");
            return (Criteria) this;
        }

        public Criteria andCreditCapitalWaitLessThan(BigDecimal value) {
            addCriterion("credit_capital_wait <", value, "creditCapitalWait");
            return (Criteria) this;
        }

        public Criteria andCreditCapitalWaitLessThanOrEqualTo(BigDecimal value) {
            addCriterion("credit_capital_wait <=", value, "creditCapitalWait");
            return (Criteria) this;
        }

        public Criteria andCreditCapitalWaitIn(List<BigDecimal> values) {
            addCriterion("credit_capital_wait in", values, "creditCapitalWait");
            return (Criteria) this;
        }

        public Criteria andCreditCapitalWaitNotIn(List<BigDecimal> values) {
            addCriterion("credit_capital_wait not in", values, "creditCapitalWait");
            return (Criteria) this;
        }

        public Criteria andCreditCapitalWaitBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("credit_capital_wait between", value1, value2, "creditCapitalWait");
            return (Criteria) this;
        }

        public Criteria andCreditCapitalWaitNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("credit_capital_wait not between", value1, value2, "creditCapitalWait");
            return (Criteria) this;
        }

        public Criteria andCreditInterestWaitIsNull() {
            addCriterion("credit_interest_wait is null");
            return (Criteria) this;
        }

        public Criteria andCreditInterestWaitIsNotNull() {
            addCriterion("credit_interest_wait is not null");
            return (Criteria) this;
        }

        public Criteria andCreditInterestWaitEqualTo(BigDecimal value) {
            addCriterion("credit_interest_wait =", value, "creditInterestWait");
            return (Criteria) this;
        }

        public Criteria andCreditInterestWaitNotEqualTo(BigDecimal value) {
            addCriterion("credit_interest_wait <>", value, "creditInterestWait");
            return (Criteria) this;
        }

        public Criteria andCreditInterestWaitGreaterThan(BigDecimal value) {
            addCriterion("credit_interest_wait >", value, "creditInterestWait");
            return (Criteria) this;
        }

        public Criteria andCreditInterestWaitGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("credit_interest_wait >=", value, "creditInterestWait");
            return (Criteria) this;
        }

        public Criteria andCreditInterestWaitLessThan(BigDecimal value) {
            addCriterion("credit_interest_wait <", value, "creditInterestWait");
            return (Criteria) this;
        }

        public Criteria andCreditInterestWaitLessThanOrEqualTo(BigDecimal value) {
            addCriterion("credit_interest_wait <=", value, "creditInterestWait");
            return (Criteria) this;
        }

        public Criteria andCreditInterestWaitIn(List<BigDecimal> values) {
            addCriterion("credit_interest_wait in", values, "creditInterestWait");
            return (Criteria) this;
        }

        public Criteria andCreditInterestWaitNotIn(List<BigDecimal> values) {
            addCriterion("credit_interest_wait not in", values, "creditInterestWait");
            return (Criteria) this;
        }

        public Criteria andCreditInterestWaitBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("credit_interest_wait between", value1, value2, "creditInterestWait");
            return (Criteria) this;
        }

        public Criteria andCreditInterestWaitNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("credit_interest_wait not between", value1, value2, "creditInterestWait");
            return (Criteria) this;
        }

        public Criteria andCreditInterestAdvanceWaitIsNull() {
            addCriterion("credit_interest_advance_wait is null");
            return (Criteria) this;
        }

        public Criteria andCreditInterestAdvanceWaitIsNotNull() {
            addCriterion("credit_interest_advance_wait is not null");
            return (Criteria) this;
        }

        public Criteria andCreditInterestAdvanceWaitEqualTo(BigDecimal value) {
            addCriterion("credit_interest_advance_wait =", value, "creditInterestAdvanceWait");
            return (Criteria) this;
        }

        public Criteria andCreditInterestAdvanceWaitNotEqualTo(BigDecimal value) {
            addCriterion("credit_interest_advance_wait <>", value, "creditInterestAdvanceWait");
            return (Criteria) this;
        }

        public Criteria andCreditInterestAdvanceWaitGreaterThan(BigDecimal value) {
            addCriterion("credit_interest_advance_wait >", value, "creditInterestAdvanceWait");
            return (Criteria) this;
        }

        public Criteria andCreditInterestAdvanceWaitGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("credit_interest_advance_wait >=", value, "creditInterestAdvanceWait");
            return (Criteria) this;
        }

        public Criteria andCreditInterestAdvanceWaitLessThan(BigDecimal value) {
            addCriterion("credit_interest_advance_wait <", value, "creditInterestAdvanceWait");
            return (Criteria) this;
        }

        public Criteria andCreditInterestAdvanceWaitLessThanOrEqualTo(BigDecimal value) {
            addCriterion("credit_interest_advance_wait <=", value, "creditInterestAdvanceWait");
            return (Criteria) this;
        }

        public Criteria andCreditInterestAdvanceWaitIn(List<BigDecimal> values) {
            addCriterion("credit_interest_advance_wait in", values, "creditInterestAdvanceWait");
            return (Criteria) this;
        }

        public Criteria andCreditInterestAdvanceWaitNotIn(List<BigDecimal> values) {
            addCriterion("credit_interest_advance_wait not in", values, "creditInterestAdvanceWait");
            return (Criteria) this;
        }

        public Criteria andCreditInterestAdvanceWaitBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("credit_interest_advance_wait between", value1, value2, "creditInterestAdvanceWait");
            return (Criteria) this;
        }

        public Criteria andCreditInterestAdvanceWaitNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("credit_interest_advance_wait not between", value1, value2, "creditInterestAdvanceWait");
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

        public Criteria andCreditServiceFeeIsNull() {
            addCriterion("credit_service_fee is null");
            return (Criteria) this;
        }

        public Criteria andCreditServiceFeeIsNotNull() {
            addCriterion("credit_service_fee is not null");
            return (Criteria) this;
        }

        public Criteria andCreditServiceFeeEqualTo(BigDecimal value) {
            addCriterion("credit_service_fee =", value, "creditServiceFee");
            return (Criteria) this;
        }

        public Criteria andCreditServiceFeeNotEqualTo(BigDecimal value) {
            addCriterion("credit_service_fee <>", value, "creditServiceFee");
            return (Criteria) this;
        }

        public Criteria andCreditServiceFeeGreaterThan(BigDecimal value) {
            addCriterion("credit_service_fee >", value, "creditServiceFee");
            return (Criteria) this;
        }

        public Criteria andCreditServiceFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("credit_service_fee >=", value, "creditServiceFee");
            return (Criteria) this;
        }

        public Criteria andCreditServiceFeeLessThan(BigDecimal value) {
            addCriterion("credit_service_fee <", value, "creditServiceFee");
            return (Criteria) this;
        }

        public Criteria andCreditServiceFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("credit_service_fee <=", value, "creditServiceFee");
            return (Criteria) this;
        }

        public Criteria andCreditServiceFeeIn(List<BigDecimal> values) {
            addCriterion("credit_service_fee in", values, "creditServiceFee");
            return (Criteria) this;
        }

        public Criteria andCreditServiceFeeNotIn(List<BigDecimal> values) {
            addCriterion("credit_service_fee not in", values, "creditServiceFee");
            return (Criteria) this;
        }

        public Criteria andCreditServiceFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("credit_service_fee between", value1, value2, "creditServiceFee");
            return (Criteria) this;
        }

        public Criteria andCreditServiceFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("credit_service_fee not between", value1, value2, "creditServiceFee");
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

        public Criteria andRepayCapitalIsNull() {
            addCriterion("repay_capital is null");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalIsNotNull() {
            addCriterion("repay_capital is not null");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalEqualTo(BigDecimal value) {
            addCriterion("repay_capital =", value, "repayCapital");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalNotEqualTo(BigDecimal value) {
            addCriterion("repay_capital <>", value, "repayCapital");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalGreaterThan(BigDecimal value) {
            addCriterion("repay_capital >", value, "repayCapital");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_capital >=", value, "repayCapital");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalLessThan(BigDecimal value) {
            addCriterion("repay_capital <", value, "repayCapital");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalLessThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_capital <=", value, "repayCapital");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalIn(List<BigDecimal> values) {
            addCriterion("repay_capital in", values, "repayCapital");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalNotIn(List<BigDecimal> values) {
            addCriterion("repay_capital not in", values, "repayCapital");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_capital between", value1, value2, "repayCapital");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_capital not between", value1, value2, "repayCapital");
            return (Criteria) this;
        }

        public Criteria andRepayInterestIsNull() {
            addCriterion("repay_interest is null");
            return (Criteria) this;
        }

        public Criteria andRepayInterestIsNotNull() {
            addCriterion("repay_interest is not null");
            return (Criteria) this;
        }

        public Criteria andRepayInterestEqualTo(BigDecimal value) {
            addCriterion("repay_interest =", value, "repayInterest");
            return (Criteria) this;
        }

        public Criteria andRepayInterestNotEqualTo(BigDecimal value) {
            addCriterion("repay_interest <>", value, "repayInterest");
            return (Criteria) this;
        }

        public Criteria andRepayInterestGreaterThan(BigDecimal value) {
            addCriterion("repay_interest >", value, "repayInterest");
            return (Criteria) this;
        }

        public Criteria andRepayInterestGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_interest >=", value, "repayInterest");
            return (Criteria) this;
        }

        public Criteria andRepayInterestLessThan(BigDecimal value) {
            addCriterion("repay_interest <", value, "repayInterest");
            return (Criteria) this;
        }

        public Criteria andRepayInterestLessThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_interest <=", value, "repayInterest");
            return (Criteria) this;
        }

        public Criteria andRepayInterestIn(List<BigDecimal> values) {
            addCriterion("repay_interest in", values, "repayInterest");
            return (Criteria) this;
        }

        public Criteria andRepayInterestNotIn(List<BigDecimal> values) {
            addCriterion("repay_interest not in", values, "repayInterest");
            return (Criteria) this;
        }

        public Criteria andRepayInterestBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_interest between", value1, value2, "repayInterest");
            return (Criteria) this;
        }

        public Criteria andRepayInterestNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_interest not between", value1, value2, "repayInterest");
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

        public Criteria andIsLateCreditIsNull() {
            addCriterion("is_late_credit is null");
            return (Criteria) this;
        }

        public Criteria andIsLateCreditIsNotNull() {
            addCriterion("is_late_credit is not null");
            return (Criteria) this;
        }

        public Criteria andIsLateCreditEqualTo(Integer value) {
            addCriterion("is_late_credit =", value, "isLateCredit");
            return (Criteria) this;
        }

        public Criteria andIsLateCreditNotEqualTo(Integer value) {
            addCriterion("is_late_credit <>", value, "isLateCredit");
            return (Criteria) this;
        }

        public Criteria andIsLateCreditGreaterThan(Integer value) {
            addCriterion("is_late_credit >", value, "isLateCredit");
            return (Criteria) this;
        }

        public Criteria andIsLateCreditGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_late_credit >=", value, "isLateCredit");
            return (Criteria) this;
        }

        public Criteria andIsLateCreditLessThan(Integer value) {
            addCriterion("is_late_credit <", value, "isLateCredit");
            return (Criteria) this;
        }

        public Criteria andIsLateCreditLessThanOrEqualTo(Integer value) {
            addCriterion("is_late_credit <=", value, "isLateCredit");
            return (Criteria) this;
        }

        public Criteria andIsLateCreditIn(List<Integer> values) {
            addCriterion("is_late_credit in", values, "isLateCredit");
            return (Criteria) this;
        }

        public Criteria andIsLateCreditNotIn(List<Integer> values) {
            addCriterion("is_late_credit not in", values, "isLateCredit");
            return (Criteria) this;
        }

        public Criteria andIsLateCreditBetween(Integer value1, Integer value2) {
            addCriterion("is_late_credit between", value1, value2, "isLateCredit");
            return (Criteria) this;
        }

        public Criteria andIsLateCreditNotBetween(Integer value1, Integer value2) {
            addCriterion("is_late_credit not between", value1, value2, "isLateCredit");
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

        public Criteria andLabelNameIsNull() {
            addCriterion("label_name is null");
            return (Criteria) this;
        }

        public Criteria andLabelNameIsNotNull() {
            addCriterion("label_name is not null");
            return (Criteria) this;
        }

        public Criteria andLabelNameEqualTo(String value) {
            addCriterion("label_name =", value, "labelName");
            return (Criteria) this;
        }

        public Criteria andLabelNameNotEqualTo(String value) {
            addCriterion("label_name <>", value, "labelName");
            return (Criteria) this;
        }

        public Criteria andLabelNameGreaterThan(String value) {
            addCriterion("label_name >", value, "labelName");
            return (Criteria) this;
        }

        public Criteria andLabelNameGreaterThanOrEqualTo(String value) {
            addCriterion("label_name >=", value, "labelName");
            return (Criteria) this;
        }

        public Criteria andLabelNameLessThan(String value) {
            addCriterion("label_name <", value, "labelName");
            return (Criteria) this;
        }

        public Criteria andLabelNameLessThanOrEqualTo(String value) {
            addCriterion("label_name <=", value, "labelName");
            return (Criteria) this;
        }

        public Criteria andLabelNameLike(String value) {
            addCriterion("label_name like", value, "labelName");
            return (Criteria) this;
        }

        public Criteria andLabelNameNotLike(String value) {
            addCriterion("label_name not like", value, "labelName");
            return (Criteria) this;
        }

        public Criteria andLabelNameIn(List<String> values) {
            addCriterion("label_name in", values, "labelName");
            return (Criteria) this;
        }

        public Criteria andLabelNameNotIn(List<String> values) {
            addCriterion("label_name not in", values, "labelName");
            return (Criteria) this;
        }

        public Criteria andLabelNameBetween(String value1, String value2) {
            addCriterion("label_name between", value1, value2, "labelName");
            return (Criteria) this;
        }

        public Criteria andLabelNameNotBetween(String value1, String value2) {
            addCriterion("label_name not between", value1, value2, "labelName");
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