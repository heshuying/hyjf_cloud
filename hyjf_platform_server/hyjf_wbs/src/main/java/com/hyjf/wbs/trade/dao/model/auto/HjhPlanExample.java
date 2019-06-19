package com.hyjf.wbs.trade.dao.model.auto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HjhPlanExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public HjhPlanExample() {
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

        public Criteria andPlanNameIsNull() {
            addCriterion("plan_name is null");
            return (Criteria) this;
        }

        public Criteria andPlanNameIsNotNull() {
            addCriterion("plan_name is not null");
            return (Criteria) this;
        }

        public Criteria andPlanNameEqualTo(String value) {
            addCriterion("plan_name =", value, "planName");
            return (Criteria) this;
        }

        public Criteria andPlanNameNotEqualTo(String value) {
            addCriterion("plan_name <>", value, "planName");
            return (Criteria) this;
        }

        public Criteria andPlanNameGreaterThan(String value) {
            addCriterion("plan_name >", value, "planName");
            return (Criteria) this;
        }

        public Criteria andPlanNameGreaterThanOrEqualTo(String value) {
            addCriterion("plan_name >=", value, "planName");
            return (Criteria) this;
        }

        public Criteria andPlanNameLessThan(String value) {
            addCriterion("plan_name <", value, "planName");
            return (Criteria) this;
        }

        public Criteria andPlanNameLessThanOrEqualTo(String value) {
            addCriterion("plan_name <=", value, "planName");
            return (Criteria) this;
        }

        public Criteria andPlanNameLike(String value) {
            addCriterion("plan_name like", value, "planName");
            return (Criteria) this;
        }

        public Criteria andPlanNameNotLike(String value) {
            addCriterion("plan_name not like", value, "planName");
            return (Criteria) this;
        }

        public Criteria andPlanNameIn(List<String> values) {
            addCriterion("plan_name in", values, "planName");
            return (Criteria) this;
        }

        public Criteria andPlanNameNotIn(List<String> values) {
            addCriterion("plan_name not in", values, "planName");
            return (Criteria) this;
        }

        public Criteria andPlanNameBetween(String value1, String value2) {
            addCriterion("plan_name between", value1, value2, "planName");
            return (Criteria) this;
        }

        public Criteria andPlanNameNotBetween(String value1, String value2) {
            addCriterion("plan_name not between", value1, value2, "planName");
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

        public Criteria andExpectAprIsNull() {
            addCriterion("expect_apr is null");
            return (Criteria) this;
        }

        public Criteria andExpectAprIsNotNull() {
            addCriterion("expect_apr is not null");
            return (Criteria) this;
        }

        public Criteria andExpectAprEqualTo(BigDecimal value) {
            addCriterion("expect_apr =", value, "expectApr");
            return (Criteria) this;
        }

        public Criteria andExpectAprNotEqualTo(BigDecimal value) {
            addCriterion("expect_apr <>", value, "expectApr");
            return (Criteria) this;
        }

        public Criteria andExpectAprGreaterThan(BigDecimal value) {
            addCriterion("expect_apr >", value, "expectApr");
            return (Criteria) this;
        }

        public Criteria andExpectAprGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("expect_apr >=", value, "expectApr");
            return (Criteria) this;
        }

        public Criteria andExpectAprLessThan(BigDecimal value) {
            addCriterion("expect_apr <", value, "expectApr");
            return (Criteria) this;
        }

        public Criteria andExpectAprLessThanOrEqualTo(BigDecimal value) {
            addCriterion("expect_apr <=", value, "expectApr");
            return (Criteria) this;
        }

        public Criteria andExpectAprIn(List<BigDecimal> values) {
            addCriterion("expect_apr in", values, "expectApr");
            return (Criteria) this;
        }

        public Criteria andExpectAprNotIn(List<BigDecimal> values) {
            addCriterion("expect_apr not in", values, "expectApr");
            return (Criteria) this;
        }

        public Criteria andExpectAprBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("expect_apr between", value1, value2, "expectApr");
            return (Criteria) this;
        }

        public Criteria andExpectAprNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("expect_apr not between", value1, value2, "expectApr");
            return (Criteria) this;
        }

        public Criteria andMinInvestmentIsNull() {
            addCriterion("min_investment is null");
            return (Criteria) this;
        }

        public Criteria andMinInvestmentIsNotNull() {
            addCriterion("min_investment is not null");
            return (Criteria) this;
        }

        public Criteria andMinInvestmentEqualTo(BigDecimal value) {
            addCriterion("min_investment =", value, "minInvestment");
            return (Criteria) this;
        }

        public Criteria andMinInvestmentNotEqualTo(BigDecimal value) {
            addCriterion("min_investment <>", value, "minInvestment");
            return (Criteria) this;
        }

        public Criteria andMinInvestmentGreaterThan(BigDecimal value) {
            addCriterion("min_investment >", value, "minInvestment");
            return (Criteria) this;
        }

        public Criteria andMinInvestmentGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("min_investment >=", value, "minInvestment");
            return (Criteria) this;
        }

        public Criteria andMinInvestmentLessThan(BigDecimal value) {
            addCriterion("min_investment <", value, "minInvestment");
            return (Criteria) this;
        }

        public Criteria andMinInvestmentLessThanOrEqualTo(BigDecimal value) {
            addCriterion("min_investment <=", value, "minInvestment");
            return (Criteria) this;
        }

        public Criteria andMinInvestmentIn(List<BigDecimal> values) {
            addCriterion("min_investment in", values, "minInvestment");
            return (Criteria) this;
        }

        public Criteria andMinInvestmentNotIn(List<BigDecimal> values) {
            addCriterion("min_investment not in", values, "minInvestment");
            return (Criteria) this;
        }

        public Criteria andMinInvestmentBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("min_investment between", value1, value2, "minInvestment");
            return (Criteria) this;
        }

        public Criteria andMinInvestmentNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("min_investment not between", value1, value2, "minInvestment");
            return (Criteria) this;
        }

        public Criteria andMaxInvestmentIsNull() {
            addCriterion("max_investment is null");
            return (Criteria) this;
        }

        public Criteria andMaxInvestmentIsNotNull() {
            addCriterion("max_investment is not null");
            return (Criteria) this;
        }

        public Criteria andMaxInvestmentEqualTo(BigDecimal value) {
            addCriterion("max_investment =", value, "maxInvestment");
            return (Criteria) this;
        }

        public Criteria andMaxInvestmentNotEqualTo(BigDecimal value) {
            addCriterion("max_investment <>", value, "maxInvestment");
            return (Criteria) this;
        }

        public Criteria andMaxInvestmentGreaterThan(BigDecimal value) {
            addCriterion("max_investment >", value, "maxInvestment");
            return (Criteria) this;
        }

        public Criteria andMaxInvestmentGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("max_investment >=", value, "maxInvestment");
            return (Criteria) this;
        }

        public Criteria andMaxInvestmentLessThan(BigDecimal value) {
            addCriterion("max_investment <", value, "maxInvestment");
            return (Criteria) this;
        }

        public Criteria andMaxInvestmentLessThanOrEqualTo(BigDecimal value) {
            addCriterion("max_investment <=", value, "maxInvestment");
            return (Criteria) this;
        }

        public Criteria andMaxInvestmentIn(List<BigDecimal> values) {
            addCriterion("max_investment in", values, "maxInvestment");
            return (Criteria) this;
        }

        public Criteria andMaxInvestmentNotIn(List<BigDecimal> values) {
            addCriterion("max_investment not in", values, "maxInvestment");
            return (Criteria) this;
        }

        public Criteria andMaxInvestmentBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("max_investment between", value1, value2, "maxInvestment");
            return (Criteria) this;
        }

        public Criteria andMaxInvestmentNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("max_investment not between", value1, value2, "maxInvestment");
            return (Criteria) this;
        }

        public Criteria andInvestmentIncrementIsNull() {
            addCriterion("investment_increment is null");
            return (Criteria) this;
        }

        public Criteria andInvestmentIncrementIsNotNull() {
            addCriterion("investment_increment is not null");
            return (Criteria) this;
        }

        public Criteria andInvestmentIncrementEqualTo(BigDecimal value) {
            addCriterion("investment_increment =", value, "investmentIncrement");
            return (Criteria) this;
        }

        public Criteria andInvestmentIncrementNotEqualTo(BigDecimal value) {
            addCriterion("investment_increment <>", value, "investmentIncrement");
            return (Criteria) this;
        }

        public Criteria andInvestmentIncrementGreaterThan(BigDecimal value) {
            addCriterion("investment_increment >", value, "investmentIncrement");
            return (Criteria) this;
        }

        public Criteria andInvestmentIncrementGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("investment_increment >=", value, "investmentIncrement");
            return (Criteria) this;
        }

        public Criteria andInvestmentIncrementLessThan(BigDecimal value) {
            addCriterion("investment_increment <", value, "investmentIncrement");
            return (Criteria) this;
        }

        public Criteria andInvestmentIncrementLessThanOrEqualTo(BigDecimal value) {
            addCriterion("investment_increment <=", value, "investmentIncrement");
            return (Criteria) this;
        }

        public Criteria andInvestmentIncrementIn(List<BigDecimal> values) {
            addCriterion("investment_increment in", values, "investmentIncrement");
            return (Criteria) this;
        }

        public Criteria andInvestmentIncrementNotIn(List<BigDecimal> values) {
            addCriterion("investment_increment not in", values, "investmentIncrement");
            return (Criteria) this;
        }

        public Criteria andInvestmentIncrementBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("investment_increment between", value1, value2, "investmentIncrement");
            return (Criteria) this;
        }

        public Criteria andInvestmentIncrementNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("investment_increment not between", value1, value2, "investmentIncrement");
            return (Criteria) this;
        }

        public Criteria andAvailableInvestAccountIsNull() {
            addCriterion("available_invest_account is null");
            return (Criteria) this;
        }

        public Criteria andAvailableInvestAccountIsNotNull() {
            addCriterion("available_invest_account is not null");
            return (Criteria) this;
        }

        public Criteria andAvailableInvestAccountEqualTo(BigDecimal value) {
            addCriterion("available_invest_account =", value, "availableInvestAccount");
            return (Criteria) this;
        }

        public Criteria andAvailableInvestAccountNotEqualTo(BigDecimal value) {
            addCriterion("available_invest_account <>", value, "availableInvestAccount");
            return (Criteria) this;
        }

        public Criteria andAvailableInvestAccountGreaterThan(BigDecimal value) {
            addCriterion("available_invest_account >", value, "availableInvestAccount");
            return (Criteria) this;
        }

        public Criteria andAvailableInvestAccountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("available_invest_account >=", value, "availableInvestAccount");
            return (Criteria) this;
        }

        public Criteria andAvailableInvestAccountLessThan(BigDecimal value) {
            addCriterion("available_invest_account <", value, "availableInvestAccount");
            return (Criteria) this;
        }

        public Criteria andAvailableInvestAccountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("available_invest_account <=", value, "availableInvestAccount");
            return (Criteria) this;
        }

        public Criteria andAvailableInvestAccountIn(List<BigDecimal> values) {
            addCriterion("available_invest_account in", values, "availableInvestAccount");
            return (Criteria) this;
        }

        public Criteria andAvailableInvestAccountNotIn(List<BigDecimal> values) {
            addCriterion("available_invest_account not in", values, "availableInvestAccount");
            return (Criteria) this;
        }

        public Criteria andAvailableInvestAccountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("available_invest_account between", value1, value2, "availableInvestAccount");
            return (Criteria) this;
        }

        public Criteria andAvailableInvestAccountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("available_invest_account not between", value1, value2, "availableInvestAccount");
            return (Criteria) this;
        }

        public Criteria andRepayWaitAllIsNull() {
            addCriterion("repay_wait_all is null");
            return (Criteria) this;
        }

        public Criteria andRepayWaitAllIsNotNull() {
            addCriterion("repay_wait_all is not null");
            return (Criteria) this;
        }

        public Criteria andRepayWaitAllEqualTo(BigDecimal value) {
            addCriterion("repay_wait_all =", value, "repayWaitAll");
            return (Criteria) this;
        }

        public Criteria andRepayWaitAllNotEqualTo(BigDecimal value) {
            addCriterion("repay_wait_all <>", value, "repayWaitAll");
            return (Criteria) this;
        }

        public Criteria andRepayWaitAllGreaterThan(BigDecimal value) {
            addCriterion("repay_wait_all >", value, "repayWaitAll");
            return (Criteria) this;
        }

        public Criteria andRepayWaitAllGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_wait_all >=", value, "repayWaitAll");
            return (Criteria) this;
        }

        public Criteria andRepayWaitAllLessThan(BigDecimal value) {
            addCriterion("repay_wait_all <", value, "repayWaitAll");
            return (Criteria) this;
        }

        public Criteria andRepayWaitAllLessThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_wait_all <=", value, "repayWaitAll");
            return (Criteria) this;
        }

        public Criteria andRepayWaitAllIn(List<BigDecimal> values) {
            addCriterion("repay_wait_all in", values, "repayWaitAll");
            return (Criteria) this;
        }

        public Criteria andRepayWaitAllNotIn(List<BigDecimal> values) {
            addCriterion("repay_wait_all not in", values, "repayWaitAll");
            return (Criteria) this;
        }

        public Criteria andRepayWaitAllBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_wait_all between", value1, value2, "repayWaitAll");
            return (Criteria) this;
        }

        public Criteria andRepayWaitAllNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_wait_all not between", value1, value2, "repayWaitAll");
            return (Criteria) this;
        }

        public Criteria andPlanInvestStatusIsNull() {
            addCriterion("plan_invest_status is null");
            return (Criteria) this;
        }

        public Criteria andPlanInvestStatusIsNotNull() {
            addCriterion("plan_invest_status is not null");
            return (Criteria) this;
        }

        public Criteria andPlanInvestStatusEqualTo(Integer value) {
            addCriterion("plan_invest_status =", value, "planInvestStatus");
            return (Criteria) this;
        }

        public Criteria andPlanInvestStatusNotEqualTo(Integer value) {
            addCriterion("plan_invest_status <>", value, "planInvestStatus");
            return (Criteria) this;
        }

        public Criteria andPlanInvestStatusGreaterThan(Integer value) {
            addCriterion("plan_invest_status >", value, "planInvestStatus");
            return (Criteria) this;
        }

        public Criteria andPlanInvestStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("plan_invest_status >=", value, "planInvestStatus");
            return (Criteria) this;
        }

        public Criteria andPlanInvestStatusLessThan(Integer value) {
            addCriterion("plan_invest_status <", value, "planInvestStatus");
            return (Criteria) this;
        }

        public Criteria andPlanInvestStatusLessThanOrEqualTo(Integer value) {
            addCriterion("plan_invest_status <=", value, "planInvestStatus");
            return (Criteria) this;
        }

        public Criteria andPlanInvestStatusIn(List<Integer> values) {
            addCriterion("plan_invest_status in", values, "planInvestStatus");
            return (Criteria) this;
        }

        public Criteria andPlanInvestStatusNotIn(List<Integer> values) {
            addCriterion("plan_invest_status not in", values, "planInvestStatus");
            return (Criteria) this;
        }

        public Criteria andPlanInvestStatusBetween(Integer value1, Integer value2) {
            addCriterion("plan_invest_status between", value1, value2, "planInvestStatus");
            return (Criteria) this;
        }

        public Criteria andPlanInvestStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("plan_invest_status not between", value1, value2, "planInvestStatus");
            return (Criteria) this;
        }

        public Criteria andPlanDisplayStatusIsNull() {
            addCriterion("plan_display_status is null");
            return (Criteria) this;
        }

        public Criteria andPlanDisplayStatusIsNotNull() {
            addCriterion("plan_display_status is not null");
            return (Criteria) this;
        }

        public Criteria andPlanDisplayStatusEqualTo(Integer value) {
            addCriterion("plan_display_status =", value, "planDisplayStatus");
            return (Criteria) this;
        }

        public Criteria andPlanDisplayStatusNotEqualTo(Integer value) {
            addCriterion("plan_display_status <>", value, "planDisplayStatus");
            return (Criteria) this;
        }

        public Criteria andPlanDisplayStatusGreaterThan(Integer value) {
            addCriterion("plan_display_status >", value, "planDisplayStatus");
            return (Criteria) this;
        }

        public Criteria andPlanDisplayStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("plan_display_status >=", value, "planDisplayStatus");
            return (Criteria) this;
        }

        public Criteria andPlanDisplayStatusLessThan(Integer value) {
            addCriterion("plan_display_status <", value, "planDisplayStatus");
            return (Criteria) this;
        }

        public Criteria andPlanDisplayStatusLessThanOrEqualTo(Integer value) {
            addCriterion("plan_display_status <=", value, "planDisplayStatus");
            return (Criteria) this;
        }

        public Criteria andPlanDisplayStatusIn(List<Integer> values) {
            addCriterion("plan_display_status in", values, "planDisplayStatus");
            return (Criteria) this;
        }

        public Criteria andPlanDisplayStatusNotIn(List<Integer> values) {
            addCriterion("plan_display_status not in", values, "planDisplayStatus");
            return (Criteria) this;
        }

        public Criteria andPlanDisplayStatusBetween(Integer value1, Integer value2) {
            addCriterion("plan_display_status between", value1, value2, "planDisplayStatus");
            return (Criteria) this;
        }

        public Criteria andPlanDisplayStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("plan_display_status not between", value1, value2, "planDisplayStatus");
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

        public Criteria andCouponConfigIsNull() {
            addCriterion("coupon_config is null");
            return (Criteria) this;
        }

        public Criteria andCouponConfigIsNotNull() {
            addCriterion("coupon_config is not null");
            return (Criteria) this;
        }

        public Criteria andCouponConfigEqualTo(String value) {
            addCriterion("coupon_config =", value, "couponConfig");
            return (Criteria) this;
        }

        public Criteria andCouponConfigNotEqualTo(String value) {
            addCriterion("coupon_config <>", value, "couponConfig");
            return (Criteria) this;
        }

        public Criteria andCouponConfigGreaterThan(String value) {
            addCriterion("coupon_config >", value, "couponConfig");
            return (Criteria) this;
        }

        public Criteria andCouponConfigGreaterThanOrEqualTo(String value) {
            addCriterion("coupon_config >=", value, "couponConfig");
            return (Criteria) this;
        }

        public Criteria andCouponConfigLessThan(String value) {
            addCriterion("coupon_config <", value, "couponConfig");
            return (Criteria) this;
        }

        public Criteria andCouponConfigLessThanOrEqualTo(String value) {
            addCriterion("coupon_config <=", value, "couponConfig");
            return (Criteria) this;
        }

        public Criteria andCouponConfigLike(String value) {
            addCriterion("coupon_config like", value, "couponConfig");
            return (Criteria) this;
        }

        public Criteria andCouponConfigNotLike(String value) {
            addCriterion("coupon_config not like", value, "couponConfig");
            return (Criteria) this;
        }

        public Criteria andCouponConfigIn(List<String> values) {
            addCriterion("coupon_config in", values, "couponConfig");
            return (Criteria) this;
        }

        public Criteria andCouponConfigNotIn(List<String> values) {
            addCriterion("coupon_config not in", values, "couponConfig");
            return (Criteria) this;
        }

        public Criteria andCouponConfigBetween(String value1, String value2) {
            addCriterion("coupon_config between", value1, value2, "couponConfig");
            return (Criteria) this;
        }

        public Criteria andCouponConfigNotBetween(String value1, String value2) {
            addCriterion("coupon_config not between", value1, value2, "couponConfig");
            return (Criteria) this;
        }

        public Criteria andJoinTotalIsNull() {
            addCriterion("join_total is null");
            return (Criteria) this;
        }

        public Criteria andJoinTotalIsNotNull() {
            addCriterion("join_total is not null");
            return (Criteria) this;
        }

        public Criteria andJoinTotalEqualTo(BigDecimal value) {
            addCriterion("join_total =", value, "joinTotal");
            return (Criteria) this;
        }

        public Criteria andJoinTotalNotEqualTo(BigDecimal value) {
            addCriterion("join_total <>", value, "joinTotal");
            return (Criteria) this;
        }

        public Criteria andJoinTotalGreaterThan(BigDecimal value) {
            addCriterion("join_total >", value, "joinTotal");
            return (Criteria) this;
        }

        public Criteria andJoinTotalGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("join_total >=", value, "joinTotal");
            return (Criteria) this;
        }

        public Criteria andJoinTotalLessThan(BigDecimal value) {
            addCriterion("join_total <", value, "joinTotal");
            return (Criteria) this;
        }

        public Criteria andJoinTotalLessThanOrEqualTo(BigDecimal value) {
            addCriterion("join_total <=", value, "joinTotal");
            return (Criteria) this;
        }

        public Criteria andJoinTotalIn(List<BigDecimal> values) {
            addCriterion("join_total in", values, "joinTotal");
            return (Criteria) this;
        }

        public Criteria andJoinTotalNotIn(List<BigDecimal> values) {
            addCriterion("join_total not in", values, "joinTotal");
            return (Criteria) this;
        }

        public Criteria andJoinTotalBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("join_total between", value1, value2, "joinTotal");
            return (Criteria) this;
        }

        public Criteria andJoinTotalNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("join_total not between", value1, value2, "joinTotal");
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

        public Criteria andMinInvestCountsIsNull() {
            addCriterion("min_invest_counts is null");
            return (Criteria) this;
        }

        public Criteria andMinInvestCountsIsNotNull() {
            addCriterion("min_invest_counts is not null");
            return (Criteria) this;
        }

        public Criteria andMinInvestCountsEqualTo(Integer value) {
            addCriterion("min_invest_counts =", value, "minInvestCounts");
            return (Criteria) this;
        }

        public Criteria andMinInvestCountsNotEqualTo(Integer value) {
            addCriterion("min_invest_counts <>", value, "minInvestCounts");
            return (Criteria) this;
        }

        public Criteria andMinInvestCountsGreaterThan(Integer value) {
            addCriterion("min_invest_counts >", value, "minInvestCounts");
            return (Criteria) this;
        }

        public Criteria andMinInvestCountsGreaterThanOrEqualTo(Integer value) {
            addCriterion("min_invest_counts >=", value, "minInvestCounts");
            return (Criteria) this;
        }

        public Criteria andMinInvestCountsLessThan(Integer value) {
            addCriterion("min_invest_counts <", value, "minInvestCounts");
            return (Criteria) this;
        }

        public Criteria andMinInvestCountsLessThanOrEqualTo(Integer value) {
            addCriterion("min_invest_counts <=", value, "minInvestCounts");
            return (Criteria) this;
        }

        public Criteria andMinInvestCountsIn(List<Integer> values) {
            addCriterion("min_invest_counts in", values, "minInvestCounts");
            return (Criteria) this;
        }

        public Criteria andMinInvestCountsNotIn(List<Integer> values) {
            addCriterion("min_invest_counts not in", values, "minInvestCounts");
            return (Criteria) this;
        }

        public Criteria andMinInvestCountsBetween(Integer value1, Integer value2) {
            addCriterion("min_invest_counts between", value1, value2, "minInvestCounts");
            return (Criteria) this;
        }

        public Criteria andMinInvestCountsNotBetween(Integer value1, Integer value2) {
            addCriterion("min_invest_counts not between", value1, value2, "minInvestCounts");
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