package com.hyjf.am.trade.dao.model.auto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DebtPlanConfigExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public DebtPlanConfigExample() {
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

        public Criteria andDebtPlanTypeIsNull() {
            addCriterion("debt_plan_type is null");
            return (Criteria) this;
        }

        public Criteria andDebtPlanTypeIsNotNull() {
            addCriterion("debt_plan_type is not null");
            return (Criteria) this;
        }

        public Criteria andDebtPlanTypeEqualTo(Integer value) {
            addCriterion("debt_plan_type =", value, "debtPlanType");
            return (Criteria) this;
        }

        public Criteria andDebtPlanTypeNotEqualTo(Integer value) {
            addCriterion("debt_plan_type <>", value, "debtPlanType");
            return (Criteria) this;
        }

        public Criteria andDebtPlanTypeGreaterThan(Integer value) {
            addCriterion("debt_plan_type >", value, "debtPlanType");
            return (Criteria) this;
        }

        public Criteria andDebtPlanTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("debt_plan_type >=", value, "debtPlanType");
            return (Criteria) this;
        }

        public Criteria andDebtPlanTypeLessThan(Integer value) {
            addCriterion("debt_plan_type <", value, "debtPlanType");
            return (Criteria) this;
        }

        public Criteria andDebtPlanTypeLessThanOrEqualTo(Integer value) {
            addCriterion("debt_plan_type <=", value, "debtPlanType");
            return (Criteria) this;
        }

        public Criteria andDebtPlanTypeIn(List<Integer> values) {
            addCriterion("debt_plan_type in", values, "debtPlanType");
            return (Criteria) this;
        }

        public Criteria andDebtPlanTypeNotIn(List<Integer> values) {
            addCriterion("debt_plan_type not in", values, "debtPlanType");
            return (Criteria) this;
        }

        public Criteria andDebtPlanTypeBetween(Integer value1, Integer value2) {
            addCriterion("debt_plan_type between", value1, value2, "debtPlanType");
            return (Criteria) this;
        }

        public Criteria andDebtPlanTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("debt_plan_type not between", value1, value2, "debtPlanType");
            return (Criteria) this;
        }

        public Criteria andDebtPlanTypeNameIsNull() {
            addCriterion("debt_plan_type_name is null");
            return (Criteria) this;
        }

        public Criteria andDebtPlanTypeNameIsNotNull() {
            addCriterion("debt_plan_type_name is not null");
            return (Criteria) this;
        }

        public Criteria andDebtPlanTypeNameEqualTo(String value) {
            addCriterion("debt_plan_type_name =", value, "debtPlanTypeName");
            return (Criteria) this;
        }

        public Criteria andDebtPlanTypeNameNotEqualTo(String value) {
            addCriterion("debt_plan_type_name <>", value, "debtPlanTypeName");
            return (Criteria) this;
        }

        public Criteria andDebtPlanTypeNameGreaterThan(String value) {
            addCriterion("debt_plan_type_name >", value, "debtPlanTypeName");
            return (Criteria) this;
        }

        public Criteria andDebtPlanTypeNameGreaterThanOrEqualTo(String value) {
            addCriterion("debt_plan_type_name >=", value, "debtPlanTypeName");
            return (Criteria) this;
        }

        public Criteria andDebtPlanTypeNameLessThan(String value) {
            addCriterion("debt_plan_type_name <", value, "debtPlanTypeName");
            return (Criteria) this;
        }

        public Criteria andDebtPlanTypeNameLessThanOrEqualTo(String value) {
            addCriterion("debt_plan_type_name <=", value, "debtPlanTypeName");
            return (Criteria) this;
        }

        public Criteria andDebtPlanTypeNameLike(String value) {
            addCriterion("debt_plan_type_name like", value, "debtPlanTypeName");
            return (Criteria) this;
        }

        public Criteria andDebtPlanTypeNameNotLike(String value) {
            addCriterion("debt_plan_type_name not like", value, "debtPlanTypeName");
            return (Criteria) this;
        }

        public Criteria andDebtPlanTypeNameIn(List<String> values) {
            addCriterion("debt_plan_type_name in", values, "debtPlanTypeName");
            return (Criteria) this;
        }

        public Criteria andDebtPlanTypeNameNotIn(List<String> values) {
            addCriterion("debt_plan_type_name not in", values, "debtPlanTypeName");
            return (Criteria) this;
        }

        public Criteria andDebtPlanTypeNameBetween(String value1, String value2) {
            addCriterion("debt_plan_type_name between", value1, value2, "debtPlanTypeName");
            return (Criteria) this;
        }

        public Criteria andDebtPlanTypeNameNotBetween(String value1, String value2) {
            addCriterion("debt_plan_type_name not between", value1, value2, "debtPlanTypeName");
            return (Criteria) this;
        }

        public Criteria andDebtPlanPrefixIsNull() {
            addCriterion("debt_plan_prefix is null");
            return (Criteria) this;
        }

        public Criteria andDebtPlanPrefixIsNotNull() {
            addCriterion("debt_plan_prefix is not null");
            return (Criteria) this;
        }

        public Criteria andDebtPlanPrefixEqualTo(String value) {
            addCriterion("debt_plan_prefix =", value, "debtPlanPrefix");
            return (Criteria) this;
        }

        public Criteria andDebtPlanPrefixNotEqualTo(String value) {
            addCriterion("debt_plan_prefix <>", value, "debtPlanPrefix");
            return (Criteria) this;
        }

        public Criteria andDebtPlanPrefixGreaterThan(String value) {
            addCriterion("debt_plan_prefix >", value, "debtPlanPrefix");
            return (Criteria) this;
        }

        public Criteria andDebtPlanPrefixGreaterThanOrEqualTo(String value) {
            addCriterion("debt_plan_prefix >=", value, "debtPlanPrefix");
            return (Criteria) this;
        }

        public Criteria andDebtPlanPrefixLessThan(String value) {
            addCriterion("debt_plan_prefix <", value, "debtPlanPrefix");
            return (Criteria) this;
        }

        public Criteria andDebtPlanPrefixLessThanOrEqualTo(String value) {
            addCriterion("debt_plan_prefix <=", value, "debtPlanPrefix");
            return (Criteria) this;
        }

        public Criteria andDebtPlanPrefixLike(String value) {
            addCriterion("debt_plan_prefix like", value, "debtPlanPrefix");
            return (Criteria) this;
        }

        public Criteria andDebtPlanPrefixNotLike(String value) {
            addCriterion("debt_plan_prefix not like", value, "debtPlanPrefix");
            return (Criteria) this;
        }

        public Criteria andDebtPlanPrefixIn(List<String> values) {
            addCriterion("debt_plan_prefix in", values, "debtPlanPrefix");
            return (Criteria) this;
        }

        public Criteria andDebtPlanPrefixNotIn(List<String> values) {
            addCriterion("debt_plan_prefix not in", values, "debtPlanPrefix");
            return (Criteria) this;
        }

        public Criteria andDebtPlanPrefixBetween(String value1, String value2) {
            addCriterion("debt_plan_prefix between", value1, value2, "debtPlanPrefix");
            return (Criteria) this;
        }

        public Criteria andDebtPlanPrefixNotBetween(String value1, String value2) {
            addCriterion("debt_plan_prefix not between", value1, value2, "debtPlanPrefix");
            return (Criteria) this;
        }

        public Criteria andDebtLockPeriodIsNull() {
            addCriterion("debt_lock_period is null");
            return (Criteria) this;
        }

        public Criteria andDebtLockPeriodIsNotNull() {
            addCriterion("debt_lock_period is not null");
            return (Criteria) this;
        }

        public Criteria andDebtLockPeriodEqualTo(Integer value) {
            addCriterion("debt_lock_period =", value, "debtLockPeriod");
            return (Criteria) this;
        }

        public Criteria andDebtLockPeriodNotEqualTo(Integer value) {
            addCriterion("debt_lock_period <>", value, "debtLockPeriod");
            return (Criteria) this;
        }

        public Criteria andDebtLockPeriodGreaterThan(Integer value) {
            addCriterion("debt_lock_period >", value, "debtLockPeriod");
            return (Criteria) this;
        }

        public Criteria andDebtLockPeriodGreaterThanOrEqualTo(Integer value) {
            addCriterion("debt_lock_period >=", value, "debtLockPeriod");
            return (Criteria) this;
        }

        public Criteria andDebtLockPeriodLessThan(Integer value) {
            addCriterion("debt_lock_period <", value, "debtLockPeriod");
            return (Criteria) this;
        }

        public Criteria andDebtLockPeriodLessThanOrEqualTo(Integer value) {
            addCriterion("debt_lock_period <=", value, "debtLockPeriod");
            return (Criteria) this;
        }

        public Criteria andDebtLockPeriodIn(List<Integer> values) {
            addCriterion("debt_lock_period in", values, "debtLockPeriod");
            return (Criteria) this;
        }

        public Criteria andDebtLockPeriodNotIn(List<Integer> values) {
            addCriterion("debt_lock_period not in", values, "debtLockPeriod");
            return (Criteria) this;
        }

        public Criteria andDebtLockPeriodBetween(Integer value1, Integer value2) {
            addCriterion("debt_lock_period between", value1, value2, "debtLockPeriod");
            return (Criteria) this;
        }

        public Criteria andDebtLockPeriodNotBetween(Integer value1, Integer value2) {
            addCriterion("debt_lock_period not between", value1, value2, "debtLockPeriod");
            return (Criteria) this;
        }

        public Criteria andDebtMinInvestmentIsNull() {
            addCriterion("debt_min_investment is null");
            return (Criteria) this;
        }

        public Criteria andDebtMinInvestmentIsNotNull() {
            addCriterion("debt_min_investment is not null");
            return (Criteria) this;
        }

        public Criteria andDebtMinInvestmentEqualTo(BigDecimal value) {
            addCriterion("debt_min_investment =", value, "debtMinInvestment");
            return (Criteria) this;
        }

        public Criteria andDebtMinInvestmentNotEqualTo(BigDecimal value) {
            addCriterion("debt_min_investment <>", value, "debtMinInvestment");
            return (Criteria) this;
        }

        public Criteria andDebtMinInvestmentGreaterThan(BigDecimal value) {
            addCriterion("debt_min_investment >", value, "debtMinInvestment");
            return (Criteria) this;
        }

        public Criteria andDebtMinInvestmentGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("debt_min_investment >=", value, "debtMinInvestment");
            return (Criteria) this;
        }

        public Criteria andDebtMinInvestmentLessThan(BigDecimal value) {
            addCriterion("debt_min_investment <", value, "debtMinInvestment");
            return (Criteria) this;
        }

        public Criteria andDebtMinInvestmentLessThanOrEqualTo(BigDecimal value) {
            addCriterion("debt_min_investment <=", value, "debtMinInvestment");
            return (Criteria) this;
        }

        public Criteria andDebtMinInvestmentIn(List<BigDecimal> values) {
            addCriterion("debt_min_investment in", values, "debtMinInvestment");
            return (Criteria) this;
        }

        public Criteria andDebtMinInvestmentNotIn(List<BigDecimal> values) {
            addCriterion("debt_min_investment not in", values, "debtMinInvestment");
            return (Criteria) this;
        }

        public Criteria andDebtMinInvestmentBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("debt_min_investment between", value1, value2, "debtMinInvestment");
            return (Criteria) this;
        }

        public Criteria andDebtMinInvestmentNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("debt_min_investment not between", value1, value2, "debtMinInvestment");
            return (Criteria) this;
        }

        public Criteria andDebtInvestmentIncrementIsNull() {
            addCriterion("debt_investment_increment is null");
            return (Criteria) this;
        }

        public Criteria andDebtInvestmentIncrementIsNotNull() {
            addCriterion("debt_investment_increment is not null");
            return (Criteria) this;
        }

        public Criteria andDebtInvestmentIncrementEqualTo(BigDecimal value) {
            addCriterion("debt_investment_increment =", value, "debtInvestmentIncrement");
            return (Criteria) this;
        }

        public Criteria andDebtInvestmentIncrementNotEqualTo(BigDecimal value) {
            addCriterion("debt_investment_increment <>", value, "debtInvestmentIncrement");
            return (Criteria) this;
        }

        public Criteria andDebtInvestmentIncrementGreaterThan(BigDecimal value) {
            addCriterion("debt_investment_increment >", value, "debtInvestmentIncrement");
            return (Criteria) this;
        }

        public Criteria andDebtInvestmentIncrementGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("debt_investment_increment >=", value, "debtInvestmentIncrement");
            return (Criteria) this;
        }

        public Criteria andDebtInvestmentIncrementLessThan(BigDecimal value) {
            addCriterion("debt_investment_increment <", value, "debtInvestmentIncrement");
            return (Criteria) this;
        }

        public Criteria andDebtInvestmentIncrementLessThanOrEqualTo(BigDecimal value) {
            addCriterion("debt_investment_increment <=", value, "debtInvestmentIncrement");
            return (Criteria) this;
        }

        public Criteria andDebtInvestmentIncrementIn(List<BigDecimal> values) {
            addCriterion("debt_investment_increment in", values, "debtInvestmentIncrement");
            return (Criteria) this;
        }

        public Criteria andDebtInvestmentIncrementNotIn(List<BigDecimal> values) {
            addCriterion("debt_investment_increment not in", values, "debtInvestmentIncrement");
            return (Criteria) this;
        }

        public Criteria andDebtInvestmentIncrementBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("debt_investment_increment between", value1, value2, "debtInvestmentIncrement");
            return (Criteria) this;
        }

        public Criteria andDebtInvestmentIncrementNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("debt_investment_increment not between", value1, value2, "debtInvestmentIncrement");
            return (Criteria) this;
        }

        public Criteria andDebtMaxInvestmentIsNull() {
            addCriterion("debt_max_investment is null");
            return (Criteria) this;
        }

        public Criteria andDebtMaxInvestmentIsNotNull() {
            addCriterion("debt_max_investment is not null");
            return (Criteria) this;
        }

        public Criteria andDebtMaxInvestmentEqualTo(BigDecimal value) {
            addCriterion("debt_max_investment =", value, "debtMaxInvestment");
            return (Criteria) this;
        }

        public Criteria andDebtMaxInvestmentNotEqualTo(BigDecimal value) {
            addCriterion("debt_max_investment <>", value, "debtMaxInvestment");
            return (Criteria) this;
        }

        public Criteria andDebtMaxInvestmentGreaterThan(BigDecimal value) {
            addCriterion("debt_max_investment >", value, "debtMaxInvestment");
            return (Criteria) this;
        }

        public Criteria andDebtMaxInvestmentGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("debt_max_investment >=", value, "debtMaxInvestment");
            return (Criteria) this;
        }

        public Criteria andDebtMaxInvestmentLessThan(BigDecimal value) {
            addCriterion("debt_max_investment <", value, "debtMaxInvestment");
            return (Criteria) this;
        }

        public Criteria andDebtMaxInvestmentLessThanOrEqualTo(BigDecimal value) {
            addCriterion("debt_max_investment <=", value, "debtMaxInvestment");
            return (Criteria) this;
        }

        public Criteria andDebtMaxInvestmentIn(List<BigDecimal> values) {
            addCriterion("debt_max_investment in", values, "debtMaxInvestment");
            return (Criteria) this;
        }

        public Criteria andDebtMaxInvestmentNotIn(List<BigDecimal> values) {
            addCriterion("debt_max_investment not in", values, "debtMaxInvestment");
            return (Criteria) this;
        }

        public Criteria andDebtMaxInvestmentBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("debt_max_investment between", value1, value2, "debtMaxInvestment");
            return (Criteria) this;
        }

        public Criteria andDebtMaxInvestmentNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("debt_max_investment not between", value1, value2, "debtMaxInvestment");
            return (Criteria) this;
        }

        public Criteria andDebtQuitStyleIsNull() {
            addCriterion("debt_quit_style is null");
            return (Criteria) this;
        }

        public Criteria andDebtQuitStyleIsNotNull() {
            addCriterion("debt_quit_style is not null");
            return (Criteria) this;
        }

        public Criteria andDebtQuitStyleEqualTo(Integer value) {
            addCriterion("debt_quit_style =", value, "debtQuitStyle");
            return (Criteria) this;
        }

        public Criteria andDebtQuitStyleNotEqualTo(Integer value) {
            addCriterion("debt_quit_style <>", value, "debtQuitStyle");
            return (Criteria) this;
        }

        public Criteria andDebtQuitStyleGreaterThan(Integer value) {
            addCriterion("debt_quit_style >", value, "debtQuitStyle");
            return (Criteria) this;
        }

        public Criteria andDebtQuitStyleGreaterThanOrEqualTo(Integer value) {
            addCriterion("debt_quit_style >=", value, "debtQuitStyle");
            return (Criteria) this;
        }

        public Criteria andDebtQuitStyleLessThan(Integer value) {
            addCriterion("debt_quit_style <", value, "debtQuitStyle");
            return (Criteria) this;
        }

        public Criteria andDebtQuitStyleLessThanOrEqualTo(Integer value) {
            addCriterion("debt_quit_style <=", value, "debtQuitStyle");
            return (Criteria) this;
        }

        public Criteria andDebtQuitStyleIn(List<Integer> values) {
            addCriterion("debt_quit_style in", values, "debtQuitStyle");
            return (Criteria) this;
        }

        public Criteria andDebtQuitStyleNotIn(List<Integer> values) {
            addCriterion("debt_quit_style not in", values, "debtQuitStyle");
            return (Criteria) this;
        }

        public Criteria andDebtQuitStyleBetween(Integer value1, Integer value2) {
            addCriterion("debt_quit_style between", value1, value2, "debtQuitStyle");
            return (Criteria) this;
        }

        public Criteria andDebtQuitStyleNotBetween(Integer value1, Integer value2) {
            addCriterion("debt_quit_style not between", value1, value2, "debtQuitStyle");
            return (Criteria) this;
        }

        public Criteria andDebtQuitPeriodIsNull() {
            addCriterion("debt_quit_period is null");
            return (Criteria) this;
        }

        public Criteria andDebtQuitPeriodIsNotNull() {
            addCriterion("debt_quit_period is not null");
            return (Criteria) this;
        }

        public Criteria andDebtQuitPeriodEqualTo(Integer value) {
            addCriterion("debt_quit_period =", value, "debtQuitPeriod");
            return (Criteria) this;
        }

        public Criteria andDebtQuitPeriodNotEqualTo(Integer value) {
            addCriterion("debt_quit_period <>", value, "debtQuitPeriod");
            return (Criteria) this;
        }

        public Criteria andDebtQuitPeriodGreaterThan(Integer value) {
            addCriterion("debt_quit_period >", value, "debtQuitPeriod");
            return (Criteria) this;
        }

        public Criteria andDebtQuitPeriodGreaterThanOrEqualTo(Integer value) {
            addCriterion("debt_quit_period >=", value, "debtQuitPeriod");
            return (Criteria) this;
        }

        public Criteria andDebtQuitPeriodLessThan(Integer value) {
            addCriterion("debt_quit_period <", value, "debtQuitPeriod");
            return (Criteria) this;
        }

        public Criteria andDebtQuitPeriodLessThanOrEqualTo(Integer value) {
            addCriterion("debt_quit_period <=", value, "debtQuitPeriod");
            return (Criteria) this;
        }

        public Criteria andDebtQuitPeriodIn(List<Integer> values) {
            addCriterion("debt_quit_period in", values, "debtQuitPeriod");
            return (Criteria) this;
        }

        public Criteria andDebtQuitPeriodNotIn(List<Integer> values) {
            addCriterion("debt_quit_period not in", values, "debtQuitPeriod");
            return (Criteria) this;
        }

        public Criteria andDebtQuitPeriodBetween(Integer value1, Integer value2) {
            addCriterion("debt_quit_period between", value1, value2, "debtQuitPeriod");
            return (Criteria) this;
        }

        public Criteria andDebtQuitPeriodNotBetween(Integer value1, Integer value2) {
            addCriterion("debt_quit_period not between", value1, value2, "debtQuitPeriod");
            return (Criteria) this;
        }

        public Criteria andMinInvestNumberIsNull() {
            addCriterion("min_invest_number is null");
            return (Criteria) this;
        }

        public Criteria andMinInvestNumberIsNotNull() {
            addCriterion("min_invest_number is not null");
            return (Criteria) this;
        }

        public Criteria andMinInvestNumberEqualTo(Integer value) {
            addCriterion("min_invest_number =", value, "minInvestNumber");
            return (Criteria) this;
        }

        public Criteria andMinInvestNumberNotEqualTo(Integer value) {
            addCriterion("min_invest_number <>", value, "minInvestNumber");
            return (Criteria) this;
        }

        public Criteria andMinInvestNumberGreaterThan(Integer value) {
            addCriterion("min_invest_number >", value, "minInvestNumber");
            return (Criteria) this;
        }

        public Criteria andMinInvestNumberGreaterThanOrEqualTo(Integer value) {
            addCriterion("min_invest_number >=", value, "minInvestNumber");
            return (Criteria) this;
        }

        public Criteria andMinInvestNumberLessThan(Integer value) {
            addCriterion("min_invest_number <", value, "minInvestNumber");
            return (Criteria) this;
        }

        public Criteria andMinInvestNumberLessThanOrEqualTo(Integer value) {
            addCriterion("min_invest_number <=", value, "minInvestNumber");
            return (Criteria) this;
        }

        public Criteria andMinInvestNumberIn(List<Integer> values) {
            addCriterion("min_invest_number in", values, "minInvestNumber");
            return (Criteria) this;
        }

        public Criteria andMinInvestNumberNotIn(List<Integer> values) {
            addCriterion("min_invest_number not in", values, "minInvestNumber");
            return (Criteria) this;
        }

        public Criteria andMinInvestNumberBetween(Integer value1, Integer value2) {
            addCriterion("min_invest_number between", value1, value2, "minInvestNumber");
            return (Criteria) this;
        }

        public Criteria andMinInvestNumberNotBetween(Integer value1, Integer value2) {
            addCriterion("min_invest_number not between", value1, value2, "minInvestNumber");
            return (Criteria) this;
        }

        public Criteria andMaxInvestNumberIsNull() {
            addCriterion("max_invest_number is null");
            return (Criteria) this;
        }

        public Criteria andMaxInvestNumberIsNotNull() {
            addCriterion("max_invest_number is not null");
            return (Criteria) this;
        }

        public Criteria andMaxInvestNumberEqualTo(Integer value) {
            addCriterion("max_invest_number =", value, "maxInvestNumber");
            return (Criteria) this;
        }

        public Criteria andMaxInvestNumberNotEqualTo(Integer value) {
            addCriterion("max_invest_number <>", value, "maxInvestNumber");
            return (Criteria) this;
        }

        public Criteria andMaxInvestNumberGreaterThan(Integer value) {
            addCriterion("max_invest_number >", value, "maxInvestNumber");
            return (Criteria) this;
        }

        public Criteria andMaxInvestNumberGreaterThanOrEqualTo(Integer value) {
            addCriterion("max_invest_number >=", value, "maxInvestNumber");
            return (Criteria) this;
        }

        public Criteria andMaxInvestNumberLessThan(Integer value) {
            addCriterion("max_invest_number <", value, "maxInvestNumber");
            return (Criteria) this;
        }

        public Criteria andMaxInvestNumberLessThanOrEqualTo(Integer value) {
            addCriterion("max_invest_number <=", value, "maxInvestNumber");
            return (Criteria) this;
        }

        public Criteria andMaxInvestNumberIn(List<Integer> values) {
            addCriterion("max_invest_number in", values, "maxInvestNumber");
            return (Criteria) this;
        }

        public Criteria andMaxInvestNumberNotIn(List<Integer> values) {
            addCriterion("max_invest_number not in", values, "maxInvestNumber");
            return (Criteria) this;
        }

        public Criteria andMaxInvestNumberBetween(Integer value1, Integer value2) {
            addCriterion("max_invest_number between", value1, value2, "maxInvestNumber");
            return (Criteria) this;
        }

        public Criteria andMaxInvestNumberNotBetween(Integer value1, Integer value2) {
            addCriterion("max_invest_number not between", value1, value2, "maxInvestNumber");
            return (Criteria) this;
        }

        public Criteria andCycleTimesIsNull() {
            addCriterion("cycle_times is null");
            return (Criteria) this;
        }

        public Criteria andCycleTimesIsNotNull() {
            addCriterion("cycle_times is not null");
            return (Criteria) this;
        }

        public Criteria andCycleTimesEqualTo(Integer value) {
            addCriterion("cycle_times =", value, "cycleTimes");
            return (Criteria) this;
        }

        public Criteria andCycleTimesNotEqualTo(Integer value) {
            addCriterion("cycle_times <>", value, "cycleTimes");
            return (Criteria) this;
        }

        public Criteria andCycleTimesGreaterThan(Integer value) {
            addCriterion("cycle_times >", value, "cycleTimes");
            return (Criteria) this;
        }

        public Criteria andCycleTimesGreaterThanOrEqualTo(Integer value) {
            addCriterion("cycle_times >=", value, "cycleTimes");
            return (Criteria) this;
        }

        public Criteria andCycleTimesLessThan(Integer value) {
            addCriterion("cycle_times <", value, "cycleTimes");
            return (Criteria) this;
        }

        public Criteria andCycleTimesLessThanOrEqualTo(Integer value) {
            addCriterion("cycle_times <=", value, "cycleTimes");
            return (Criteria) this;
        }

        public Criteria andCycleTimesIn(List<Integer> values) {
            addCriterion("cycle_times in", values, "cycleTimes");
            return (Criteria) this;
        }

        public Criteria andCycleTimesNotIn(List<Integer> values) {
            addCriterion("cycle_times not in", values, "cycleTimes");
            return (Criteria) this;
        }

        public Criteria andCycleTimesBetween(Integer value1, Integer value2) {
            addCriterion("cycle_times between", value1, value2, "cycleTimes");
            return (Criteria) this;
        }

        public Criteria andCycleTimesNotBetween(Integer value1, Integer value2) {
            addCriterion("cycle_times not between", value1, value2, "cycleTimes");
            return (Criteria) this;
        }

        public Criteria andUnableCycleTimesIsNull() {
            addCriterion("unable_cycle_times is null");
            return (Criteria) this;
        }

        public Criteria andUnableCycleTimesIsNotNull() {
            addCriterion("unable_cycle_times is not null");
            return (Criteria) this;
        }

        public Criteria andUnableCycleTimesEqualTo(Integer value) {
            addCriterion("unable_cycle_times =", value, "unableCycleTimes");
            return (Criteria) this;
        }

        public Criteria andUnableCycleTimesNotEqualTo(Integer value) {
            addCriterion("unable_cycle_times <>", value, "unableCycleTimes");
            return (Criteria) this;
        }

        public Criteria andUnableCycleTimesGreaterThan(Integer value) {
            addCriterion("unable_cycle_times >", value, "unableCycleTimes");
            return (Criteria) this;
        }

        public Criteria andUnableCycleTimesGreaterThanOrEqualTo(Integer value) {
            addCriterion("unable_cycle_times >=", value, "unableCycleTimes");
            return (Criteria) this;
        }

        public Criteria andUnableCycleTimesLessThan(Integer value) {
            addCriterion("unable_cycle_times <", value, "unableCycleTimes");
            return (Criteria) this;
        }

        public Criteria andUnableCycleTimesLessThanOrEqualTo(Integer value) {
            addCriterion("unable_cycle_times <=", value, "unableCycleTimes");
            return (Criteria) this;
        }

        public Criteria andUnableCycleTimesIn(List<Integer> values) {
            addCriterion("unable_cycle_times in", values, "unableCycleTimes");
            return (Criteria) this;
        }

        public Criteria andUnableCycleTimesNotIn(List<Integer> values) {
            addCriterion("unable_cycle_times not in", values, "unableCycleTimes");
            return (Criteria) this;
        }

        public Criteria andUnableCycleTimesBetween(Integer value1, Integer value2) {
            addCriterion("unable_cycle_times between", value1, value2, "unableCycleTimes");
            return (Criteria) this;
        }

        public Criteria andUnableCycleTimesNotBetween(Integer value1, Integer value2) {
            addCriterion("unable_cycle_times not between", value1, value2, "unableCycleTimes");
            return (Criteria) this;
        }

        public Criteria andInvestAccountLimitIsNull() {
            addCriterion("invest_account_limit is null");
            return (Criteria) this;
        }

        public Criteria andInvestAccountLimitIsNotNull() {
            addCriterion("invest_account_limit is not null");
            return (Criteria) this;
        }

        public Criteria andInvestAccountLimitEqualTo(BigDecimal value) {
            addCriterion("invest_account_limit =", value, "investAccountLimit");
            return (Criteria) this;
        }

        public Criteria andInvestAccountLimitNotEqualTo(BigDecimal value) {
            addCriterion("invest_account_limit <>", value, "investAccountLimit");
            return (Criteria) this;
        }

        public Criteria andInvestAccountLimitGreaterThan(BigDecimal value) {
            addCriterion("invest_account_limit >", value, "investAccountLimit");
            return (Criteria) this;
        }

        public Criteria andInvestAccountLimitGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("invest_account_limit >=", value, "investAccountLimit");
            return (Criteria) this;
        }

        public Criteria andInvestAccountLimitLessThan(BigDecimal value) {
            addCriterion("invest_account_limit <", value, "investAccountLimit");
            return (Criteria) this;
        }

        public Criteria andInvestAccountLimitLessThanOrEqualTo(BigDecimal value) {
            addCriterion("invest_account_limit <=", value, "investAccountLimit");
            return (Criteria) this;
        }

        public Criteria andInvestAccountLimitIn(List<BigDecimal> values) {
            addCriterion("invest_account_limit in", values, "investAccountLimit");
            return (Criteria) this;
        }

        public Criteria andInvestAccountLimitNotIn(List<BigDecimal> values) {
            addCriterion("invest_account_limit not in", values, "investAccountLimit");
            return (Criteria) this;
        }

        public Criteria andInvestAccountLimitBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("invest_account_limit between", value1, value2, "investAccountLimit");
            return (Criteria) this;
        }

        public Criteria andInvestAccountLimitNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("invest_account_limit not between", value1, value2, "investAccountLimit");
            return (Criteria) this;
        }

        public Criteria andMinSurplusInvestAccountIsNull() {
            addCriterion("min_surplus_invest_account is null");
            return (Criteria) this;
        }

        public Criteria andMinSurplusInvestAccountIsNotNull() {
            addCriterion("min_surplus_invest_account is not null");
            return (Criteria) this;
        }

        public Criteria andMinSurplusInvestAccountEqualTo(BigDecimal value) {
            addCriterion("min_surplus_invest_account =", value, "minSurplusInvestAccount");
            return (Criteria) this;
        }

        public Criteria andMinSurplusInvestAccountNotEqualTo(BigDecimal value) {
            addCriterion("min_surplus_invest_account <>", value, "minSurplusInvestAccount");
            return (Criteria) this;
        }

        public Criteria andMinSurplusInvestAccountGreaterThan(BigDecimal value) {
            addCriterion("min_surplus_invest_account >", value, "minSurplusInvestAccount");
            return (Criteria) this;
        }

        public Criteria andMinSurplusInvestAccountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("min_surplus_invest_account >=", value, "minSurplusInvestAccount");
            return (Criteria) this;
        }

        public Criteria andMinSurplusInvestAccountLessThan(BigDecimal value) {
            addCriterion("min_surplus_invest_account <", value, "minSurplusInvestAccount");
            return (Criteria) this;
        }

        public Criteria andMinSurplusInvestAccountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("min_surplus_invest_account <=", value, "minSurplusInvestAccount");
            return (Criteria) this;
        }

        public Criteria andMinSurplusInvestAccountIn(List<BigDecimal> values) {
            addCriterion("min_surplus_invest_account in", values, "minSurplusInvestAccount");
            return (Criteria) this;
        }

        public Criteria andMinSurplusInvestAccountNotIn(List<BigDecimal> values) {
            addCriterion("min_surplus_invest_account not in", values, "minSurplusInvestAccount");
            return (Criteria) this;
        }

        public Criteria andMinSurplusInvestAccountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("min_surplus_invest_account between", value1, value2, "minSurplusInvestAccount");
            return (Criteria) this;
        }

        public Criteria andMinSurplusInvestAccountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("min_surplus_invest_account not between", value1, value2, "minSurplusInvestAccount");
            return (Criteria) this;
        }

        public Criteria andRoundAmountIsNull() {
            addCriterion("round_amount is null");
            return (Criteria) this;
        }

        public Criteria andRoundAmountIsNotNull() {
            addCriterion("round_amount is not null");
            return (Criteria) this;
        }

        public Criteria andRoundAmountEqualTo(BigDecimal value) {
            addCriterion("round_amount =", value, "roundAmount");
            return (Criteria) this;
        }

        public Criteria andRoundAmountNotEqualTo(BigDecimal value) {
            addCriterion("round_amount <>", value, "roundAmount");
            return (Criteria) this;
        }

        public Criteria andRoundAmountGreaterThan(BigDecimal value) {
            addCriterion("round_amount >", value, "roundAmount");
            return (Criteria) this;
        }

        public Criteria andRoundAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("round_amount >=", value, "roundAmount");
            return (Criteria) this;
        }

        public Criteria andRoundAmountLessThan(BigDecimal value) {
            addCriterion("round_amount <", value, "roundAmount");
            return (Criteria) this;
        }

        public Criteria andRoundAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("round_amount <=", value, "roundAmount");
            return (Criteria) this;
        }

        public Criteria andRoundAmountIn(List<BigDecimal> values) {
            addCriterion("round_amount in", values, "roundAmount");
            return (Criteria) this;
        }

        public Criteria andRoundAmountNotIn(List<BigDecimal> values) {
            addCriterion("round_amount not in", values, "roundAmount");
            return (Criteria) this;
        }

        public Criteria andRoundAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("round_amount between", value1, value2, "roundAmount");
            return (Criteria) this;
        }

        public Criteria andRoundAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("round_amount not between", value1, value2, "roundAmount");
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