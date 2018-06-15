package com.hyjf.am.trade.dao.model.auto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HjhRepayExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public HjhRepayExample() {
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

        public Criteria andAccedeOrderIdIsNull() {
            addCriterion("accede_order_id is null");
            return (Criteria) this;
        }

        public Criteria andAccedeOrderIdIsNotNull() {
            addCriterion("accede_order_id is not null");
            return (Criteria) this;
        }

        public Criteria andAccedeOrderIdEqualTo(String value) {
            addCriterion("accede_order_id =", value, "accedeOrderId");
            return (Criteria) this;
        }

        public Criteria andAccedeOrderIdNotEqualTo(String value) {
            addCriterion("accede_order_id <>", value, "accedeOrderId");
            return (Criteria) this;
        }

        public Criteria andAccedeOrderIdGreaterThan(String value) {
            addCriterion("accede_order_id >", value, "accedeOrderId");
            return (Criteria) this;
        }

        public Criteria andAccedeOrderIdGreaterThanOrEqualTo(String value) {
            addCriterion("accede_order_id >=", value, "accedeOrderId");
            return (Criteria) this;
        }

        public Criteria andAccedeOrderIdLessThan(String value) {
            addCriterion("accede_order_id <", value, "accedeOrderId");
            return (Criteria) this;
        }

        public Criteria andAccedeOrderIdLessThanOrEqualTo(String value) {
            addCriterion("accede_order_id <=", value, "accedeOrderId");
            return (Criteria) this;
        }

        public Criteria andAccedeOrderIdLike(String value) {
            addCriterion("accede_order_id like", value, "accedeOrderId");
            return (Criteria) this;
        }

        public Criteria andAccedeOrderIdNotLike(String value) {
            addCriterion("accede_order_id not like", value, "accedeOrderId");
            return (Criteria) this;
        }

        public Criteria andAccedeOrderIdIn(List<String> values) {
            addCriterion("accede_order_id in", values, "accedeOrderId");
            return (Criteria) this;
        }

        public Criteria andAccedeOrderIdNotIn(List<String> values) {
            addCriterion("accede_order_id not in", values, "accedeOrderId");
            return (Criteria) this;
        }

        public Criteria andAccedeOrderIdBetween(String value1, String value2) {
            addCriterion("accede_order_id between", value1, value2, "accedeOrderId");
            return (Criteria) this;
        }

        public Criteria andAccedeOrderIdNotBetween(String value1, String value2) {
            addCriterion("accede_order_id not between", value1, value2, "accedeOrderId");
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

        public Criteria andLockPeriodIsNull() {
            addCriterion("lock_period is null");
            return (Criteria) this;
        }

        public Criteria andLockPeriodIsNotNull() {
            addCriterion("lock_period is not null");
            return (Criteria) this;
        }

        public Criteria andLockPeriodEqualTo(Integer value) {
            addCriterion("lock_period =", value, "lockPeriod");
            return (Criteria) this;
        }

        public Criteria andLockPeriodNotEqualTo(Integer value) {
            addCriterion("lock_period <>", value, "lockPeriod");
            return (Criteria) this;
        }

        public Criteria andLockPeriodGreaterThan(Integer value) {
            addCriterion("lock_period >", value, "lockPeriod");
            return (Criteria) this;
        }

        public Criteria andLockPeriodGreaterThanOrEqualTo(Integer value) {
            addCriterion("lock_period >=", value, "lockPeriod");
            return (Criteria) this;
        }

        public Criteria andLockPeriodLessThan(Integer value) {
            addCriterion("lock_period <", value, "lockPeriod");
            return (Criteria) this;
        }

        public Criteria andLockPeriodLessThanOrEqualTo(Integer value) {
            addCriterion("lock_period <=", value, "lockPeriod");
            return (Criteria) this;
        }

        public Criteria andLockPeriodIn(List<Integer> values) {
            addCriterion("lock_period in", values, "lockPeriod");
            return (Criteria) this;
        }

        public Criteria andLockPeriodNotIn(List<Integer> values) {
            addCriterion("lock_period not in", values, "lockPeriod");
            return (Criteria) this;
        }

        public Criteria andLockPeriodBetween(Integer value1, Integer value2) {
            addCriterion("lock_period between", value1, value2, "lockPeriod");
            return (Criteria) this;
        }

        public Criteria andLockPeriodNotBetween(Integer value1, Integer value2) {
            addCriterion("lock_period not between", value1, value2, "lockPeriod");
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

        public Criteria andUserAttributeIsNull() {
            addCriterion("user_attribute is null");
            return (Criteria) this;
        }

        public Criteria andUserAttributeIsNotNull() {
            addCriterion("user_attribute is not null");
            return (Criteria) this;
        }

        public Criteria andUserAttributeEqualTo(Integer value) {
            addCriterion("user_attribute =", value, "userAttribute");
            return (Criteria) this;
        }

        public Criteria andUserAttributeNotEqualTo(Integer value) {
            addCriterion("user_attribute <>", value, "userAttribute");
            return (Criteria) this;
        }

        public Criteria andUserAttributeGreaterThan(Integer value) {
            addCriterion("user_attribute >", value, "userAttribute");
            return (Criteria) this;
        }

        public Criteria andUserAttributeGreaterThanOrEqualTo(Integer value) {
            addCriterion("user_attribute >=", value, "userAttribute");
            return (Criteria) this;
        }

        public Criteria andUserAttributeLessThan(Integer value) {
            addCriterion("user_attribute <", value, "userAttribute");
            return (Criteria) this;
        }

        public Criteria andUserAttributeLessThanOrEqualTo(Integer value) {
            addCriterion("user_attribute <=", value, "userAttribute");
            return (Criteria) this;
        }

        public Criteria andUserAttributeIn(List<Integer> values) {
            addCriterion("user_attribute in", values, "userAttribute");
            return (Criteria) this;
        }

        public Criteria andUserAttributeNotIn(List<Integer> values) {
            addCriterion("user_attribute not in", values, "userAttribute");
            return (Criteria) this;
        }

        public Criteria andUserAttributeBetween(Integer value1, Integer value2) {
            addCriterion("user_attribute between", value1, value2, "userAttribute");
            return (Criteria) this;
        }

        public Criteria andUserAttributeNotBetween(Integer value1, Integer value2) {
            addCriterion("user_attribute not between", value1, value2, "userAttribute");
            return (Criteria) this;
        }

        public Criteria andAccedeAccountIsNull() {
            addCriterion("accede_account is null");
            return (Criteria) this;
        }

        public Criteria andAccedeAccountIsNotNull() {
            addCriterion("accede_account is not null");
            return (Criteria) this;
        }

        public Criteria andAccedeAccountEqualTo(BigDecimal value) {
            addCriterion("accede_account =", value, "accedeAccount");
            return (Criteria) this;
        }

        public Criteria andAccedeAccountNotEqualTo(BigDecimal value) {
            addCriterion("accede_account <>", value, "accedeAccount");
            return (Criteria) this;
        }

        public Criteria andAccedeAccountGreaterThan(BigDecimal value) {
            addCriterion("accede_account >", value, "accedeAccount");
            return (Criteria) this;
        }

        public Criteria andAccedeAccountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("accede_account >=", value, "accedeAccount");
            return (Criteria) this;
        }

        public Criteria andAccedeAccountLessThan(BigDecimal value) {
            addCriterion("accede_account <", value, "accedeAccount");
            return (Criteria) this;
        }

        public Criteria andAccedeAccountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("accede_account <=", value, "accedeAccount");
            return (Criteria) this;
        }

        public Criteria andAccedeAccountIn(List<BigDecimal> values) {
            addCriterion("accede_account in", values, "accedeAccount");
            return (Criteria) this;
        }

        public Criteria andAccedeAccountNotIn(List<BigDecimal> values) {
            addCriterion("accede_account not in", values, "accedeAccount");
            return (Criteria) this;
        }

        public Criteria andAccedeAccountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("accede_account between", value1, value2, "accedeAccount");
            return (Criteria) this;
        }

        public Criteria andAccedeAccountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("accede_account not between", value1, value2, "accedeAccount");
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

        public Criteria andRepayAlreadyIsNull() {
            addCriterion("repay_already is null");
            return (Criteria) this;
        }

        public Criteria andRepayAlreadyIsNotNull() {
            addCriterion("repay_already is not null");
            return (Criteria) this;
        }

        public Criteria andRepayAlreadyEqualTo(BigDecimal value) {
            addCriterion("repay_already =", value, "repayAlready");
            return (Criteria) this;
        }

        public Criteria andRepayAlreadyNotEqualTo(BigDecimal value) {
            addCriterion("repay_already <>", value, "repayAlready");
            return (Criteria) this;
        }

        public Criteria andRepayAlreadyGreaterThan(BigDecimal value) {
            addCriterion("repay_already >", value, "repayAlready");
            return (Criteria) this;
        }

        public Criteria andRepayAlreadyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_already >=", value, "repayAlready");
            return (Criteria) this;
        }

        public Criteria andRepayAlreadyLessThan(BigDecimal value) {
            addCriterion("repay_already <", value, "repayAlready");
            return (Criteria) this;
        }

        public Criteria andRepayAlreadyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_already <=", value, "repayAlready");
            return (Criteria) this;
        }

        public Criteria andRepayAlreadyIn(List<BigDecimal> values) {
            addCriterion("repay_already in", values, "repayAlready");
            return (Criteria) this;
        }

        public Criteria andRepayAlreadyNotIn(List<BigDecimal> values) {
            addCriterion("repay_already not in", values, "repayAlready");
            return (Criteria) this;
        }

        public Criteria andRepayAlreadyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_already between", value1, value2, "repayAlready");
            return (Criteria) this;
        }

        public Criteria andRepayAlreadyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_already not between", value1, value2, "repayAlready");
            return (Criteria) this;
        }

        public Criteria andRepayWaitIsNull() {
            addCriterion("repay_wait is null");
            return (Criteria) this;
        }

        public Criteria andRepayWaitIsNotNull() {
            addCriterion("repay_wait is not null");
            return (Criteria) this;
        }

        public Criteria andRepayWaitEqualTo(BigDecimal value) {
            addCriterion("repay_wait =", value, "repayWait");
            return (Criteria) this;
        }

        public Criteria andRepayWaitNotEqualTo(BigDecimal value) {
            addCriterion("repay_wait <>", value, "repayWait");
            return (Criteria) this;
        }

        public Criteria andRepayWaitGreaterThan(BigDecimal value) {
            addCriterion("repay_wait >", value, "repayWait");
            return (Criteria) this;
        }

        public Criteria andRepayWaitGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_wait >=", value, "repayWait");
            return (Criteria) this;
        }

        public Criteria andRepayWaitLessThan(BigDecimal value) {
            addCriterion("repay_wait <", value, "repayWait");
            return (Criteria) this;
        }

        public Criteria andRepayWaitLessThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_wait <=", value, "repayWait");
            return (Criteria) this;
        }

        public Criteria andRepayWaitIn(List<BigDecimal> values) {
            addCriterion("repay_wait in", values, "repayWait");
            return (Criteria) this;
        }

        public Criteria andRepayWaitNotIn(List<BigDecimal> values) {
            addCriterion("repay_wait not in", values, "repayWait");
            return (Criteria) this;
        }

        public Criteria andRepayWaitBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_wait between", value1, value2, "repayWait");
            return (Criteria) this;
        }

        public Criteria andRepayWaitNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_wait not between", value1, value2, "repayWait");
            return (Criteria) this;
        }

        public Criteria andRepayShouldIsNull() {
            addCriterion("repay_should is null");
            return (Criteria) this;
        }

        public Criteria andRepayShouldIsNotNull() {
            addCriterion("repay_should is not null");
            return (Criteria) this;
        }

        public Criteria andRepayShouldEqualTo(BigDecimal value) {
            addCriterion("repay_should =", value, "repayShould");
            return (Criteria) this;
        }

        public Criteria andRepayShouldNotEqualTo(BigDecimal value) {
            addCriterion("repay_should <>", value, "repayShould");
            return (Criteria) this;
        }

        public Criteria andRepayShouldGreaterThan(BigDecimal value) {
            addCriterion("repay_should >", value, "repayShould");
            return (Criteria) this;
        }

        public Criteria andRepayShouldGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_should >=", value, "repayShould");
            return (Criteria) this;
        }

        public Criteria andRepayShouldLessThan(BigDecimal value) {
            addCriterion("repay_should <", value, "repayShould");
            return (Criteria) this;
        }

        public Criteria andRepayShouldLessThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_should <=", value, "repayShould");
            return (Criteria) this;
        }

        public Criteria andRepayShouldIn(List<BigDecimal> values) {
            addCriterion("repay_should in", values, "repayShould");
            return (Criteria) this;
        }

        public Criteria andRepayShouldNotIn(List<BigDecimal> values) {
            addCriterion("repay_should not in", values, "repayShould");
            return (Criteria) this;
        }

        public Criteria andRepayShouldBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_should between", value1, value2, "repayShould");
            return (Criteria) this;
        }

        public Criteria andRepayShouldNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_should not between", value1, value2, "repayShould");
            return (Criteria) this;
        }

        public Criteria andRepayActualIsNull() {
            addCriterion("repay_actual is null");
            return (Criteria) this;
        }

        public Criteria andRepayActualIsNotNull() {
            addCriterion("repay_actual is not null");
            return (Criteria) this;
        }

        public Criteria andRepayActualEqualTo(BigDecimal value) {
            addCriterion("repay_actual =", value, "repayActual");
            return (Criteria) this;
        }

        public Criteria andRepayActualNotEqualTo(BigDecimal value) {
            addCriterion("repay_actual <>", value, "repayActual");
            return (Criteria) this;
        }

        public Criteria andRepayActualGreaterThan(BigDecimal value) {
            addCriterion("repay_actual >", value, "repayActual");
            return (Criteria) this;
        }

        public Criteria andRepayActualGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_actual >=", value, "repayActual");
            return (Criteria) this;
        }

        public Criteria andRepayActualLessThan(BigDecimal value) {
            addCriterion("repay_actual <", value, "repayActual");
            return (Criteria) this;
        }

        public Criteria andRepayActualLessThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_actual <=", value, "repayActual");
            return (Criteria) this;
        }

        public Criteria andRepayActualIn(List<BigDecimal> values) {
            addCriterion("repay_actual in", values, "repayActual");
            return (Criteria) this;
        }

        public Criteria andRepayActualNotIn(List<BigDecimal> values) {
            addCriterion("repay_actual not in", values, "repayActual");
            return (Criteria) this;
        }

        public Criteria andRepayActualBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_actual between", value1, value2, "repayActual");
            return (Criteria) this;
        }

        public Criteria andRepayActualNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_actual not between", value1, value2, "repayActual");
            return (Criteria) this;
        }

        public Criteria andOrderStatusIsNull() {
            addCriterion("order_status is null");
            return (Criteria) this;
        }

        public Criteria andOrderStatusIsNotNull() {
            addCriterion("order_status is not null");
            return (Criteria) this;
        }

        public Criteria andOrderStatusEqualTo(Integer value) {
            addCriterion("order_status =", value, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusNotEqualTo(Integer value) {
            addCriterion("order_status <>", value, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusGreaterThan(Integer value) {
            addCriterion("order_status >", value, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("order_status >=", value, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusLessThan(Integer value) {
            addCriterion("order_status <", value, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusLessThanOrEqualTo(Integer value) {
            addCriterion("order_status <=", value, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusIn(List<Integer> values) {
            addCriterion("order_status in", values, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusNotIn(List<Integer> values) {
            addCriterion("order_status not in", values, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusBetween(Integer value1, Integer value2) {
            addCriterion("order_status between", value1, value2, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("order_status not between", value1, value2, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andRepayActualTimeIsNull() {
            addCriterion("repay_actual_time is null");
            return (Criteria) this;
        }

        public Criteria andRepayActualTimeIsNotNull() {
            addCriterion("repay_actual_time is not null");
            return (Criteria) this;
        }

        public Criteria andRepayActualTimeEqualTo(Integer value) {
            addCriterion("repay_actual_time =", value, "repayActualTime");
            return (Criteria) this;
        }

        public Criteria andRepayActualTimeNotEqualTo(Integer value) {
            addCriterion("repay_actual_time <>", value, "repayActualTime");
            return (Criteria) this;
        }

        public Criteria andRepayActualTimeGreaterThan(Integer value) {
            addCriterion("repay_actual_time >", value, "repayActualTime");
            return (Criteria) this;
        }

        public Criteria andRepayActualTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("repay_actual_time >=", value, "repayActualTime");
            return (Criteria) this;
        }

        public Criteria andRepayActualTimeLessThan(Integer value) {
            addCriterion("repay_actual_time <", value, "repayActualTime");
            return (Criteria) this;
        }

        public Criteria andRepayActualTimeLessThanOrEqualTo(Integer value) {
            addCriterion("repay_actual_time <=", value, "repayActualTime");
            return (Criteria) this;
        }

        public Criteria andRepayActualTimeIn(List<Integer> values) {
            addCriterion("repay_actual_time in", values, "repayActualTime");
            return (Criteria) this;
        }

        public Criteria andRepayActualTimeNotIn(List<Integer> values) {
            addCriterion("repay_actual_time not in", values, "repayActualTime");
            return (Criteria) this;
        }

        public Criteria andRepayActualTimeBetween(Integer value1, Integer value2) {
            addCriterion("repay_actual_time between", value1, value2, "repayActualTime");
            return (Criteria) this;
        }

        public Criteria andRepayActualTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("repay_actual_time not between", value1, value2, "repayActualTime");
            return (Criteria) this;
        }

        public Criteria andRepayShouldTimeIsNull() {
            addCriterion("repay_should_time is null");
            return (Criteria) this;
        }

        public Criteria andRepayShouldTimeIsNotNull() {
            addCriterion("repay_should_time is not null");
            return (Criteria) this;
        }

        public Criteria andRepayShouldTimeEqualTo(Integer value) {
            addCriterion("repay_should_time =", value, "repayShouldTime");
            return (Criteria) this;
        }

        public Criteria andRepayShouldTimeNotEqualTo(Integer value) {
            addCriterion("repay_should_time <>", value, "repayShouldTime");
            return (Criteria) this;
        }

        public Criteria andRepayShouldTimeGreaterThan(Integer value) {
            addCriterion("repay_should_time >", value, "repayShouldTime");
            return (Criteria) this;
        }

        public Criteria andRepayShouldTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("repay_should_time >=", value, "repayShouldTime");
            return (Criteria) this;
        }

        public Criteria andRepayShouldTimeLessThan(Integer value) {
            addCriterion("repay_should_time <", value, "repayShouldTime");
            return (Criteria) this;
        }

        public Criteria andRepayShouldTimeLessThanOrEqualTo(Integer value) {
            addCriterion("repay_should_time <=", value, "repayShouldTime");
            return (Criteria) this;
        }

        public Criteria andRepayShouldTimeIn(List<Integer> values) {
            addCriterion("repay_should_time in", values, "repayShouldTime");
            return (Criteria) this;
        }

        public Criteria andRepayShouldTimeNotIn(List<Integer> values) {
            addCriterion("repay_should_time not in", values, "repayShouldTime");
            return (Criteria) this;
        }

        public Criteria andRepayShouldTimeBetween(Integer value1, Integer value2) {
            addCriterion("repay_should_time between", value1, value2, "repayShouldTime");
            return (Criteria) this;
        }

        public Criteria andRepayShouldTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("repay_should_time not between", value1, value2, "repayShouldTime");
            return (Criteria) this;
        }

        public Criteria andPlanRepayCapitalIsNull() {
            addCriterion("plan_repay_capital is null");
            return (Criteria) this;
        }

        public Criteria andPlanRepayCapitalIsNotNull() {
            addCriterion("plan_repay_capital is not null");
            return (Criteria) this;
        }

        public Criteria andPlanRepayCapitalEqualTo(BigDecimal value) {
            addCriterion("plan_repay_capital =", value, "planRepayCapital");
            return (Criteria) this;
        }

        public Criteria andPlanRepayCapitalNotEqualTo(BigDecimal value) {
            addCriterion("plan_repay_capital <>", value, "planRepayCapital");
            return (Criteria) this;
        }

        public Criteria andPlanRepayCapitalGreaterThan(BigDecimal value) {
            addCriterion("plan_repay_capital >", value, "planRepayCapital");
            return (Criteria) this;
        }

        public Criteria andPlanRepayCapitalGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("plan_repay_capital >=", value, "planRepayCapital");
            return (Criteria) this;
        }

        public Criteria andPlanRepayCapitalLessThan(BigDecimal value) {
            addCriterion("plan_repay_capital <", value, "planRepayCapital");
            return (Criteria) this;
        }

        public Criteria andPlanRepayCapitalLessThanOrEqualTo(BigDecimal value) {
            addCriterion("plan_repay_capital <=", value, "planRepayCapital");
            return (Criteria) this;
        }

        public Criteria andPlanRepayCapitalIn(List<BigDecimal> values) {
            addCriterion("plan_repay_capital in", values, "planRepayCapital");
            return (Criteria) this;
        }

        public Criteria andPlanRepayCapitalNotIn(List<BigDecimal> values) {
            addCriterion("plan_repay_capital not in", values, "planRepayCapital");
            return (Criteria) this;
        }

        public Criteria andPlanRepayCapitalBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("plan_repay_capital between", value1, value2, "planRepayCapital");
            return (Criteria) this;
        }

        public Criteria andPlanRepayCapitalNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("plan_repay_capital not between", value1, value2, "planRepayCapital");
            return (Criteria) this;
        }

        public Criteria andPlanRepayInterestIsNull() {
            addCriterion("plan_repay_interest is null");
            return (Criteria) this;
        }

        public Criteria andPlanRepayInterestIsNotNull() {
            addCriterion("plan_repay_interest is not null");
            return (Criteria) this;
        }

        public Criteria andPlanRepayInterestEqualTo(BigDecimal value) {
            addCriterion("plan_repay_interest =", value, "planRepayInterest");
            return (Criteria) this;
        }

        public Criteria andPlanRepayInterestNotEqualTo(BigDecimal value) {
            addCriterion("plan_repay_interest <>", value, "planRepayInterest");
            return (Criteria) this;
        }

        public Criteria andPlanRepayInterestGreaterThan(BigDecimal value) {
            addCriterion("plan_repay_interest >", value, "planRepayInterest");
            return (Criteria) this;
        }

        public Criteria andPlanRepayInterestGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("plan_repay_interest >=", value, "planRepayInterest");
            return (Criteria) this;
        }

        public Criteria andPlanRepayInterestLessThan(BigDecimal value) {
            addCriterion("plan_repay_interest <", value, "planRepayInterest");
            return (Criteria) this;
        }

        public Criteria andPlanRepayInterestLessThanOrEqualTo(BigDecimal value) {
            addCriterion("plan_repay_interest <=", value, "planRepayInterest");
            return (Criteria) this;
        }

        public Criteria andPlanRepayInterestIn(List<BigDecimal> values) {
            addCriterion("plan_repay_interest in", values, "planRepayInterest");
            return (Criteria) this;
        }

        public Criteria andPlanRepayInterestNotIn(List<BigDecimal> values) {
            addCriterion("plan_repay_interest not in", values, "planRepayInterest");
            return (Criteria) this;
        }

        public Criteria andPlanRepayInterestBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("plan_repay_interest between", value1, value2, "planRepayInterest");
            return (Criteria) this;
        }

        public Criteria andPlanRepayInterestNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("plan_repay_interest not between", value1, value2, "planRepayInterest");
            return (Criteria) this;
        }

        public Criteria andRepayTotalIsNull() {
            addCriterion("repay_total is null");
            return (Criteria) this;
        }

        public Criteria andRepayTotalIsNotNull() {
            addCriterion("repay_total is not null");
            return (Criteria) this;
        }

        public Criteria andRepayTotalEqualTo(BigDecimal value) {
            addCriterion("repay_total =", value, "repayTotal");
            return (Criteria) this;
        }

        public Criteria andRepayTotalNotEqualTo(BigDecimal value) {
            addCriterion("repay_total <>", value, "repayTotal");
            return (Criteria) this;
        }

        public Criteria andRepayTotalGreaterThan(BigDecimal value) {
            addCriterion("repay_total >", value, "repayTotal");
            return (Criteria) this;
        }

        public Criteria andRepayTotalGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_total >=", value, "repayTotal");
            return (Criteria) this;
        }

        public Criteria andRepayTotalLessThan(BigDecimal value) {
            addCriterion("repay_total <", value, "repayTotal");
            return (Criteria) this;
        }

        public Criteria andRepayTotalLessThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_total <=", value, "repayTotal");
            return (Criteria) this;
        }

        public Criteria andRepayTotalIn(List<BigDecimal> values) {
            addCriterion("repay_total in", values, "repayTotal");
            return (Criteria) this;
        }

        public Criteria andRepayTotalNotIn(List<BigDecimal> values) {
            addCriterion("repay_total not in", values, "repayTotal");
            return (Criteria) this;
        }

        public Criteria andRepayTotalBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_total between", value1, value2, "repayTotal");
            return (Criteria) this;
        }

        public Criteria andRepayTotalNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_total not between", value1, value2, "repayTotal");
            return (Criteria) this;
        }

        public Criteria andPlanWaitCapticalIsNull() {
            addCriterion("plan_wait_captical is null");
            return (Criteria) this;
        }

        public Criteria andPlanWaitCapticalIsNotNull() {
            addCriterion("plan_wait_captical is not null");
            return (Criteria) this;
        }

        public Criteria andPlanWaitCapticalEqualTo(BigDecimal value) {
            addCriterion("plan_wait_captical =", value, "planWaitCaptical");
            return (Criteria) this;
        }

        public Criteria andPlanWaitCapticalNotEqualTo(BigDecimal value) {
            addCriterion("plan_wait_captical <>", value, "planWaitCaptical");
            return (Criteria) this;
        }

        public Criteria andPlanWaitCapticalGreaterThan(BigDecimal value) {
            addCriterion("plan_wait_captical >", value, "planWaitCaptical");
            return (Criteria) this;
        }

        public Criteria andPlanWaitCapticalGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("plan_wait_captical >=", value, "planWaitCaptical");
            return (Criteria) this;
        }

        public Criteria andPlanWaitCapticalLessThan(BigDecimal value) {
            addCriterion("plan_wait_captical <", value, "planWaitCaptical");
            return (Criteria) this;
        }

        public Criteria andPlanWaitCapticalLessThanOrEqualTo(BigDecimal value) {
            addCriterion("plan_wait_captical <=", value, "planWaitCaptical");
            return (Criteria) this;
        }

        public Criteria andPlanWaitCapticalIn(List<BigDecimal> values) {
            addCriterion("plan_wait_captical in", values, "planWaitCaptical");
            return (Criteria) this;
        }

        public Criteria andPlanWaitCapticalNotIn(List<BigDecimal> values) {
            addCriterion("plan_wait_captical not in", values, "planWaitCaptical");
            return (Criteria) this;
        }

        public Criteria andPlanWaitCapticalBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("plan_wait_captical between", value1, value2, "planWaitCaptical");
            return (Criteria) this;
        }

        public Criteria andPlanWaitCapticalNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("plan_wait_captical not between", value1, value2, "planWaitCaptical");
            return (Criteria) this;
        }

        public Criteria andPlanWaitInterestIsNull() {
            addCriterion("plan_wait_interest is null");
            return (Criteria) this;
        }

        public Criteria andPlanWaitInterestIsNotNull() {
            addCriterion("plan_wait_interest is not null");
            return (Criteria) this;
        }

        public Criteria andPlanWaitInterestEqualTo(BigDecimal value) {
            addCriterion("plan_wait_interest =", value, "planWaitInterest");
            return (Criteria) this;
        }

        public Criteria andPlanWaitInterestNotEqualTo(BigDecimal value) {
            addCriterion("plan_wait_interest <>", value, "planWaitInterest");
            return (Criteria) this;
        }

        public Criteria andPlanWaitInterestGreaterThan(BigDecimal value) {
            addCriterion("plan_wait_interest >", value, "planWaitInterest");
            return (Criteria) this;
        }

        public Criteria andPlanWaitInterestGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("plan_wait_interest >=", value, "planWaitInterest");
            return (Criteria) this;
        }

        public Criteria andPlanWaitInterestLessThan(BigDecimal value) {
            addCriterion("plan_wait_interest <", value, "planWaitInterest");
            return (Criteria) this;
        }

        public Criteria andPlanWaitInterestLessThanOrEqualTo(BigDecimal value) {
            addCriterion("plan_wait_interest <=", value, "planWaitInterest");
            return (Criteria) this;
        }

        public Criteria andPlanWaitInterestIn(List<BigDecimal> values) {
            addCriterion("plan_wait_interest in", values, "planWaitInterest");
            return (Criteria) this;
        }

        public Criteria andPlanWaitInterestNotIn(List<BigDecimal> values) {
            addCriterion("plan_wait_interest not in", values, "planWaitInterest");
            return (Criteria) this;
        }

        public Criteria andPlanWaitInterestBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("plan_wait_interest between", value1, value2, "planWaitInterest");
            return (Criteria) this;
        }

        public Criteria andPlanWaitInterestNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("plan_wait_interest not between", value1, value2, "planWaitInterest");
            return (Criteria) this;
        }

        public Criteria andWaitTotalIsNull() {
            addCriterion("wait_total is null");
            return (Criteria) this;
        }

        public Criteria andWaitTotalIsNotNull() {
            addCriterion("wait_total is not null");
            return (Criteria) this;
        }

        public Criteria andWaitTotalEqualTo(BigDecimal value) {
            addCriterion("wait_total =", value, "waitTotal");
            return (Criteria) this;
        }

        public Criteria andWaitTotalNotEqualTo(BigDecimal value) {
            addCriterion("wait_total <>", value, "waitTotal");
            return (Criteria) this;
        }

        public Criteria andWaitTotalGreaterThan(BigDecimal value) {
            addCriterion("wait_total >", value, "waitTotal");
            return (Criteria) this;
        }

        public Criteria andWaitTotalGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("wait_total >=", value, "waitTotal");
            return (Criteria) this;
        }

        public Criteria andWaitTotalLessThan(BigDecimal value) {
            addCriterion("wait_total <", value, "waitTotal");
            return (Criteria) this;
        }

        public Criteria andWaitTotalLessThanOrEqualTo(BigDecimal value) {
            addCriterion("wait_total <=", value, "waitTotal");
            return (Criteria) this;
        }

        public Criteria andWaitTotalIn(List<BigDecimal> values) {
            addCriterion("wait_total in", values, "waitTotal");
            return (Criteria) this;
        }

        public Criteria andWaitTotalNotIn(List<BigDecimal> values) {
            addCriterion("wait_total not in", values, "waitTotal");
            return (Criteria) this;
        }

        public Criteria andWaitTotalBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("wait_total between", value1, value2, "waitTotal");
            return (Criteria) this;
        }

        public Criteria andWaitTotalNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("wait_total not between", value1, value2, "waitTotal");
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

        public Criteria andCreateUserIsNull() {
            addCriterion("create_user is null");
            return (Criteria) this;
        }

        public Criteria andCreateUserIsNotNull() {
            addCriterion("create_user is not null");
            return (Criteria) this;
        }

        public Criteria andCreateUserEqualTo(Integer value) {
            addCriterion("create_user =", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotEqualTo(Integer value) {
            addCriterion("create_user <>", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserGreaterThan(Integer value) {
            addCriterion("create_user >", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserGreaterThanOrEqualTo(Integer value) {
            addCriterion("create_user >=", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLessThan(Integer value) {
            addCriterion("create_user <", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLessThanOrEqualTo(Integer value) {
            addCriterion("create_user <=", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserIn(List<Integer> values) {
            addCriterion("create_user in", values, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotIn(List<Integer> values) {
            addCriterion("create_user not in", values, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserBetween(Integer value1, Integer value2) {
            addCriterion("create_user between", value1, value2, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotBetween(Integer value1, Integer value2) {
            addCriterion("create_user not between", value1, value2, "createUser");
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

        public Criteria andUpdateUserIsNull() {
            addCriterion("update_user is null");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIsNotNull() {
            addCriterion("update_user is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateUserEqualTo(Integer value) {
            addCriterion("update_user =", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotEqualTo(Integer value) {
            addCriterion("update_user <>", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserGreaterThan(Integer value) {
            addCriterion("update_user >", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserGreaterThanOrEqualTo(Integer value) {
            addCriterion("update_user >=", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLessThan(Integer value) {
            addCriterion("update_user <", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLessThanOrEqualTo(Integer value) {
            addCriterion("update_user <=", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIn(List<Integer> values) {
            addCriterion("update_user in", values, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotIn(List<Integer> values) {
            addCriterion("update_user not in", values, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserBetween(Integer value1, Integer value2) {
            addCriterion("update_user between", value1, value2, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotBetween(Integer value1, Integer value2) {
            addCriterion("update_user not between", value1, value2, "updateUser");
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