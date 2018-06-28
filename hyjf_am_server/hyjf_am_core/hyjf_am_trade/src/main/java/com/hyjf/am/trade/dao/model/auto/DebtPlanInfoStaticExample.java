package com.hyjf.am.trade.dao.model.auto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DebtPlanInfoStaticExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public DebtPlanInfoStaticExample() {
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

        public Criteria andWaitInvestIsNull() {
            addCriterion("wait_invest is null");
            return (Criteria) this;
        }

        public Criteria andWaitInvestIsNotNull() {
            addCriterion("wait_invest is not null");
            return (Criteria) this;
        }

        public Criteria andWaitInvestEqualTo(BigDecimal value) {
            addCriterion("wait_invest =", value, "waitInvest");
            return (Criteria) this;
        }

        public Criteria andWaitInvestNotEqualTo(BigDecimal value) {
            addCriterion("wait_invest <>", value, "waitInvest");
            return (Criteria) this;
        }

        public Criteria andWaitInvestGreaterThan(BigDecimal value) {
            addCriterion("wait_invest >", value, "waitInvest");
            return (Criteria) this;
        }

        public Criteria andWaitInvestGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("wait_invest >=", value, "waitInvest");
            return (Criteria) this;
        }

        public Criteria andWaitInvestLessThan(BigDecimal value) {
            addCriterion("wait_invest <", value, "waitInvest");
            return (Criteria) this;
        }

        public Criteria andWaitInvestLessThanOrEqualTo(BigDecimal value) {
            addCriterion("wait_invest <=", value, "waitInvest");
            return (Criteria) this;
        }

        public Criteria andWaitInvestIn(List<BigDecimal> values) {
            addCriterion("wait_invest in", values, "waitInvest");
            return (Criteria) this;
        }

        public Criteria andWaitInvestNotIn(List<BigDecimal> values) {
            addCriterion("wait_invest not in", values, "waitInvest");
            return (Criteria) this;
        }

        public Criteria andWaitInvestBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("wait_invest between", value1, value2, "waitInvest");
            return (Criteria) this;
        }

        public Criteria andWaitInvestNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("wait_invest not between", value1, value2, "waitInvest");
            return (Criteria) this;
        }

        public Criteria andWaitCreditIsNull() {
            addCriterion("wait_credit is null");
            return (Criteria) this;
        }

        public Criteria andWaitCreditIsNotNull() {
            addCriterion("wait_credit is not null");
            return (Criteria) this;
        }

        public Criteria andWaitCreditEqualTo(BigDecimal value) {
            addCriterion("wait_credit =", value, "waitCredit");
            return (Criteria) this;
        }

        public Criteria andWaitCreditNotEqualTo(BigDecimal value) {
            addCriterion("wait_credit <>", value, "waitCredit");
            return (Criteria) this;
        }

        public Criteria andWaitCreditGreaterThan(BigDecimal value) {
            addCriterion("wait_credit >", value, "waitCredit");
            return (Criteria) this;
        }

        public Criteria andWaitCreditGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("wait_credit >=", value, "waitCredit");
            return (Criteria) this;
        }

        public Criteria andWaitCreditLessThan(BigDecimal value) {
            addCriterion("wait_credit <", value, "waitCredit");
            return (Criteria) this;
        }

        public Criteria andWaitCreditLessThanOrEqualTo(BigDecimal value) {
            addCriterion("wait_credit <=", value, "waitCredit");
            return (Criteria) this;
        }

        public Criteria andWaitCreditIn(List<BigDecimal> values) {
            addCriterion("wait_credit in", values, "waitCredit");
            return (Criteria) this;
        }

        public Criteria andWaitCreditNotIn(List<BigDecimal> values) {
            addCriterion("wait_credit not in", values, "waitCredit");
            return (Criteria) this;
        }

        public Criteria andWaitCreditBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("wait_credit between", value1, value2, "waitCredit");
            return (Criteria) this;
        }

        public Criteria andWaitCreditNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("wait_credit not between", value1, value2, "waitCredit");
            return (Criteria) this;
        }

        public Criteria andYesInvestIsNull() {
            addCriterion("yes_invest is null");
            return (Criteria) this;
        }

        public Criteria andYesInvestIsNotNull() {
            addCriterion("yes_invest is not null");
            return (Criteria) this;
        }

        public Criteria andYesInvestEqualTo(Integer value) {
            addCriterion("yes_invest =", value, "yesInvest");
            return (Criteria) this;
        }

        public Criteria andYesInvestNotEqualTo(Integer value) {
            addCriterion("yes_invest <>", value, "yesInvest");
            return (Criteria) this;
        }

        public Criteria andYesInvestGreaterThan(Integer value) {
            addCriterion("yes_invest >", value, "yesInvest");
            return (Criteria) this;
        }

        public Criteria andYesInvestGreaterThanOrEqualTo(Integer value) {
            addCriterion("yes_invest >=", value, "yesInvest");
            return (Criteria) this;
        }

        public Criteria andYesInvestLessThan(Integer value) {
            addCriterion("yes_invest <", value, "yesInvest");
            return (Criteria) this;
        }

        public Criteria andYesInvestLessThanOrEqualTo(Integer value) {
            addCriterion("yes_invest <=", value, "yesInvest");
            return (Criteria) this;
        }

        public Criteria andYesInvestIn(List<Integer> values) {
            addCriterion("yes_invest in", values, "yesInvest");
            return (Criteria) this;
        }

        public Criteria andYesInvestNotIn(List<Integer> values) {
            addCriterion("yes_invest not in", values, "yesInvest");
            return (Criteria) this;
        }

        public Criteria andYesInvestBetween(Integer value1, Integer value2) {
            addCriterion("yes_invest between", value1, value2, "yesInvest");
            return (Criteria) this;
        }

        public Criteria andYesInvestNotBetween(Integer value1, Integer value2) {
            addCriterion("yes_invest not between", value1, value2, "yesInvest");
            return (Criteria) this;
        }

        public Criteria andYesCreditIsNull() {
            addCriterion("yes_credit is null");
            return (Criteria) this;
        }

        public Criteria andYesCreditIsNotNull() {
            addCriterion("yes_credit is not null");
            return (Criteria) this;
        }

        public Criteria andYesCreditEqualTo(Integer value) {
            addCriterion("yes_credit =", value, "yesCredit");
            return (Criteria) this;
        }

        public Criteria andYesCreditNotEqualTo(Integer value) {
            addCriterion("yes_credit <>", value, "yesCredit");
            return (Criteria) this;
        }

        public Criteria andYesCreditGreaterThan(Integer value) {
            addCriterion("yes_credit >", value, "yesCredit");
            return (Criteria) this;
        }

        public Criteria andYesCreditGreaterThanOrEqualTo(Integer value) {
            addCriterion("yes_credit >=", value, "yesCredit");
            return (Criteria) this;
        }

        public Criteria andYesCreditLessThan(Integer value) {
            addCriterion("yes_credit <", value, "yesCredit");
            return (Criteria) this;
        }

        public Criteria andYesCreditLessThanOrEqualTo(Integer value) {
            addCriterion("yes_credit <=", value, "yesCredit");
            return (Criteria) this;
        }

        public Criteria andYesCreditIn(List<Integer> values) {
            addCriterion("yes_credit in", values, "yesCredit");
            return (Criteria) this;
        }

        public Criteria andYesCreditNotIn(List<Integer> values) {
            addCriterion("yes_credit not in", values, "yesCredit");
            return (Criteria) this;
        }

        public Criteria andYesCreditBetween(Integer value1, Integer value2) {
            addCriterion("yes_credit between", value1, value2, "yesCredit");
            return (Criteria) this;
        }

        public Criteria andYesCreditNotBetween(Integer value1, Integer value2) {
            addCriterion("yes_credit not between", value1, value2, "yesCredit");
            return (Criteria) this;
        }

        public Criteria andWaitRepayIsNull() {
            addCriterion("wait_repay is null");
            return (Criteria) this;
        }

        public Criteria andWaitRepayIsNotNull() {
            addCriterion("wait_repay is not null");
            return (Criteria) this;
        }

        public Criteria andWaitRepayEqualTo(BigDecimal value) {
            addCriterion("wait_repay =", value, "waitRepay");
            return (Criteria) this;
        }

        public Criteria andWaitRepayNotEqualTo(BigDecimal value) {
            addCriterion("wait_repay <>", value, "waitRepay");
            return (Criteria) this;
        }

        public Criteria andWaitRepayGreaterThan(BigDecimal value) {
            addCriterion("wait_repay >", value, "waitRepay");
            return (Criteria) this;
        }

        public Criteria andWaitRepayGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("wait_repay >=", value, "waitRepay");
            return (Criteria) this;
        }

        public Criteria andWaitRepayLessThan(BigDecimal value) {
            addCriterion("wait_repay <", value, "waitRepay");
            return (Criteria) this;
        }

        public Criteria andWaitRepayLessThanOrEqualTo(BigDecimal value) {
            addCriterion("wait_repay <=", value, "waitRepay");
            return (Criteria) this;
        }

        public Criteria andWaitRepayIn(List<BigDecimal> values) {
            addCriterion("wait_repay in", values, "waitRepay");
            return (Criteria) this;
        }

        public Criteria andWaitRepayNotIn(List<BigDecimal> values) {
            addCriterion("wait_repay not in", values, "waitRepay");
            return (Criteria) this;
        }

        public Criteria andWaitRepayBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("wait_repay between", value1, value2, "waitRepay");
            return (Criteria) this;
        }

        public Criteria andWaitRepayNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("wait_repay not between", value1, value2, "waitRepay");
            return (Criteria) this;
        }

        public Criteria andYesRepayIsNull() {
            addCriterion("yes_repay is null");
            return (Criteria) this;
        }

        public Criteria andYesRepayIsNotNull() {
            addCriterion("yes_repay is not null");
            return (Criteria) this;
        }

        public Criteria andYesRepayEqualTo(BigDecimal value) {
            addCriterion("yes_repay =", value, "yesRepay");
            return (Criteria) this;
        }

        public Criteria andYesRepayNotEqualTo(BigDecimal value) {
            addCriterion("yes_repay <>", value, "yesRepay");
            return (Criteria) this;
        }

        public Criteria andYesRepayGreaterThan(BigDecimal value) {
            addCriterion("yes_repay >", value, "yesRepay");
            return (Criteria) this;
        }

        public Criteria andYesRepayGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("yes_repay >=", value, "yesRepay");
            return (Criteria) this;
        }

        public Criteria andYesRepayLessThan(BigDecimal value) {
            addCriterion("yes_repay <", value, "yesRepay");
            return (Criteria) this;
        }

        public Criteria andYesRepayLessThanOrEqualTo(BigDecimal value) {
            addCriterion("yes_repay <=", value, "yesRepay");
            return (Criteria) this;
        }

        public Criteria andYesRepayIn(List<BigDecimal> values) {
            addCriterion("yes_repay in", values, "yesRepay");
            return (Criteria) this;
        }

        public Criteria andYesRepayNotIn(List<BigDecimal> values) {
            addCriterion("yes_repay not in", values, "yesRepay");
            return (Criteria) this;
        }

        public Criteria andYesRepayBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("yes_repay between", value1, value2, "yesRepay");
            return (Criteria) this;
        }

        public Criteria andYesRepayNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("yes_repay not between", value1, value2, "yesRepay");
            return (Criteria) this;
        }

        public Criteria andPlanRepayWaitIsNull() {
            addCriterion("plan_repay_wait is null");
            return (Criteria) this;
        }

        public Criteria andPlanRepayWaitIsNotNull() {
            addCriterion("plan_repay_wait is not null");
            return (Criteria) this;
        }

        public Criteria andPlanRepayWaitEqualTo(BigDecimal value) {
            addCriterion("plan_repay_wait =", value, "planRepayWait");
            return (Criteria) this;
        }

        public Criteria andPlanRepayWaitNotEqualTo(BigDecimal value) {
            addCriterion("plan_repay_wait <>", value, "planRepayWait");
            return (Criteria) this;
        }

        public Criteria andPlanRepayWaitGreaterThan(BigDecimal value) {
            addCriterion("plan_repay_wait >", value, "planRepayWait");
            return (Criteria) this;
        }

        public Criteria andPlanRepayWaitGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("plan_repay_wait >=", value, "planRepayWait");
            return (Criteria) this;
        }

        public Criteria andPlanRepayWaitLessThan(BigDecimal value) {
            addCriterion("plan_repay_wait <", value, "planRepayWait");
            return (Criteria) this;
        }

        public Criteria andPlanRepayWaitLessThanOrEqualTo(BigDecimal value) {
            addCriterion("plan_repay_wait <=", value, "planRepayWait");
            return (Criteria) this;
        }

        public Criteria andPlanRepayWaitIn(List<BigDecimal> values) {
            addCriterion("plan_repay_wait in", values, "planRepayWait");
            return (Criteria) this;
        }

        public Criteria andPlanRepayWaitNotIn(List<BigDecimal> values) {
            addCriterion("plan_repay_wait not in", values, "planRepayWait");
            return (Criteria) this;
        }

        public Criteria andPlanRepayWaitBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("plan_repay_wait between", value1, value2, "planRepayWait");
            return (Criteria) this;
        }

        public Criteria andPlanRepayWaitNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("plan_repay_wait not between", value1, value2, "planRepayWait");
            return (Criteria) this;
        }

        public Criteria andPlanRepayYesIsNull() {
            addCriterion("plan_repay_yes is null");
            return (Criteria) this;
        }

        public Criteria andPlanRepayYesIsNotNull() {
            addCriterion("plan_repay_yes is not null");
            return (Criteria) this;
        }

        public Criteria andPlanRepayYesEqualTo(BigDecimal value) {
            addCriterion("plan_repay_yes =", value, "planRepayYes");
            return (Criteria) this;
        }

        public Criteria andPlanRepayYesNotEqualTo(BigDecimal value) {
            addCriterion("plan_repay_yes <>", value, "planRepayYes");
            return (Criteria) this;
        }

        public Criteria andPlanRepayYesGreaterThan(BigDecimal value) {
            addCriterion("plan_repay_yes >", value, "planRepayYes");
            return (Criteria) this;
        }

        public Criteria andPlanRepayYesGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("plan_repay_yes >=", value, "planRepayYes");
            return (Criteria) this;
        }

        public Criteria andPlanRepayYesLessThan(BigDecimal value) {
            addCriterion("plan_repay_yes <", value, "planRepayYes");
            return (Criteria) this;
        }

        public Criteria andPlanRepayYesLessThanOrEqualTo(BigDecimal value) {
            addCriterion("plan_repay_yes <=", value, "planRepayYes");
            return (Criteria) this;
        }

        public Criteria andPlanRepayYesIn(List<BigDecimal> values) {
            addCriterion("plan_repay_yes in", values, "planRepayYes");
            return (Criteria) this;
        }

        public Criteria andPlanRepayYesNotIn(List<BigDecimal> values) {
            addCriterion("plan_repay_yes not in", values, "planRepayYes");
            return (Criteria) this;
        }

        public Criteria andPlanRepayYesBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("plan_repay_yes between", value1, value2, "planRepayYes");
            return (Criteria) this;
        }

        public Criteria andPlanRepayYesNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("plan_repay_yes not between", value1, value2, "planRepayYes");
            return (Criteria) this;
        }

        public Criteria andPlanAccedeAllIsNull() {
            addCriterion("plan_accede_all is null");
            return (Criteria) this;
        }

        public Criteria andPlanAccedeAllIsNotNull() {
            addCriterion("plan_accede_all is not null");
            return (Criteria) this;
        }

        public Criteria andPlanAccedeAllEqualTo(BigDecimal value) {
            addCriterion("plan_accede_all =", value, "planAccedeAll");
            return (Criteria) this;
        }

        public Criteria andPlanAccedeAllNotEqualTo(BigDecimal value) {
            addCriterion("plan_accede_all <>", value, "planAccedeAll");
            return (Criteria) this;
        }

        public Criteria andPlanAccedeAllGreaterThan(BigDecimal value) {
            addCriterion("plan_accede_all >", value, "planAccedeAll");
            return (Criteria) this;
        }

        public Criteria andPlanAccedeAllGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("plan_accede_all >=", value, "planAccedeAll");
            return (Criteria) this;
        }

        public Criteria andPlanAccedeAllLessThan(BigDecimal value) {
            addCriterion("plan_accede_all <", value, "planAccedeAll");
            return (Criteria) this;
        }

        public Criteria andPlanAccedeAllLessThanOrEqualTo(BigDecimal value) {
            addCriterion("plan_accede_all <=", value, "planAccedeAll");
            return (Criteria) this;
        }

        public Criteria andPlanAccedeAllIn(List<BigDecimal> values) {
            addCriterion("plan_accede_all in", values, "planAccedeAll");
            return (Criteria) this;
        }

        public Criteria andPlanAccedeAllNotIn(List<BigDecimal> values) {
            addCriterion("plan_accede_all not in", values, "planAccedeAll");
            return (Criteria) this;
        }

        public Criteria andPlanAccedeAllBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("plan_accede_all between", value1, value2, "planAccedeAll");
            return (Criteria) this;
        }

        public Criteria andPlanAccedeAllNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("plan_accede_all not between", value1, value2, "planAccedeAll");
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

        public Criteria andInvestPeriodOneIsNull() {
            addCriterion("invest_period_one is null");
            return (Criteria) this;
        }

        public Criteria andInvestPeriodOneIsNotNull() {
            addCriterion("invest_period_one is not null");
            return (Criteria) this;
        }

        public Criteria andInvestPeriodOneEqualTo(Integer value) {
            addCriterion("invest_period_one =", value, "investPeriodOne");
            return (Criteria) this;
        }

        public Criteria andInvestPeriodOneNotEqualTo(Integer value) {
            addCriterion("invest_period_one <>", value, "investPeriodOne");
            return (Criteria) this;
        }

        public Criteria andInvestPeriodOneGreaterThan(Integer value) {
            addCriterion("invest_period_one >", value, "investPeriodOne");
            return (Criteria) this;
        }

        public Criteria andInvestPeriodOneGreaterThanOrEqualTo(Integer value) {
            addCriterion("invest_period_one >=", value, "investPeriodOne");
            return (Criteria) this;
        }

        public Criteria andInvestPeriodOneLessThan(Integer value) {
            addCriterion("invest_period_one <", value, "investPeriodOne");
            return (Criteria) this;
        }

        public Criteria andInvestPeriodOneLessThanOrEqualTo(Integer value) {
            addCriterion("invest_period_one <=", value, "investPeriodOne");
            return (Criteria) this;
        }

        public Criteria andInvestPeriodOneIn(List<Integer> values) {
            addCriterion("invest_period_one in", values, "investPeriodOne");
            return (Criteria) this;
        }

        public Criteria andInvestPeriodOneNotIn(List<Integer> values) {
            addCriterion("invest_period_one not in", values, "investPeriodOne");
            return (Criteria) this;
        }

        public Criteria andInvestPeriodOneBetween(Integer value1, Integer value2) {
            addCriterion("invest_period_one between", value1, value2, "investPeriodOne");
            return (Criteria) this;
        }

        public Criteria andInvestPeriodOneNotBetween(Integer value1, Integer value2) {
            addCriterion("invest_period_one not between", value1, value2, "investPeriodOne");
            return (Criteria) this;
        }

        public Criteria andInvestPeriodTwoFourIsNull() {
            addCriterion("invest_period_two_four is null");
            return (Criteria) this;
        }

        public Criteria andInvestPeriodTwoFourIsNotNull() {
            addCriterion("invest_period_two_four is not null");
            return (Criteria) this;
        }

        public Criteria andInvestPeriodTwoFourEqualTo(Integer value) {
            addCriterion("invest_period_two_four =", value, "investPeriodTwoFour");
            return (Criteria) this;
        }

        public Criteria andInvestPeriodTwoFourNotEqualTo(Integer value) {
            addCriterion("invest_period_two_four <>", value, "investPeriodTwoFour");
            return (Criteria) this;
        }

        public Criteria andInvestPeriodTwoFourGreaterThan(Integer value) {
            addCriterion("invest_period_two_four >", value, "investPeriodTwoFour");
            return (Criteria) this;
        }

        public Criteria andInvestPeriodTwoFourGreaterThanOrEqualTo(Integer value) {
            addCriterion("invest_period_two_four >=", value, "investPeriodTwoFour");
            return (Criteria) this;
        }

        public Criteria andInvestPeriodTwoFourLessThan(Integer value) {
            addCriterion("invest_period_two_four <", value, "investPeriodTwoFour");
            return (Criteria) this;
        }

        public Criteria andInvestPeriodTwoFourLessThanOrEqualTo(Integer value) {
            addCriterion("invest_period_two_four <=", value, "investPeriodTwoFour");
            return (Criteria) this;
        }

        public Criteria andInvestPeriodTwoFourIn(List<Integer> values) {
            addCriterion("invest_period_two_four in", values, "investPeriodTwoFour");
            return (Criteria) this;
        }

        public Criteria andInvestPeriodTwoFourNotIn(List<Integer> values) {
            addCriterion("invest_period_two_four not in", values, "investPeriodTwoFour");
            return (Criteria) this;
        }

        public Criteria andInvestPeriodTwoFourBetween(Integer value1, Integer value2) {
            addCriterion("invest_period_two_four between", value1, value2, "investPeriodTwoFour");
            return (Criteria) this;
        }

        public Criteria andInvestPeriodTwoFourNotBetween(Integer value1, Integer value2) {
            addCriterion("invest_period_two_four not between", value1, value2, "investPeriodTwoFour");
            return (Criteria) this;
        }

        public Criteria andInvestPeriodFiveEightIsNull() {
            addCriterion("invest_period_five_eight is null");
            return (Criteria) this;
        }

        public Criteria andInvestPeriodFiveEightIsNotNull() {
            addCriterion("invest_period_five_eight is not null");
            return (Criteria) this;
        }

        public Criteria andInvestPeriodFiveEightEqualTo(Integer value) {
            addCriterion("invest_period_five_eight =", value, "investPeriodFiveEight");
            return (Criteria) this;
        }

        public Criteria andInvestPeriodFiveEightNotEqualTo(Integer value) {
            addCriterion("invest_period_five_eight <>", value, "investPeriodFiveEight");
            return (Criteria) this;
        }

        public Criteria andInvestPeriodFiveEightGreaterThan(Integer value) {
            addCriterion("invest_period_five_eight >", value, "investPeriodFiveEight");
            return (Criteria) this;
        }

        public Criteria andInvestPeriodFiveEightGreaterThanOrEqualTo(Integer value) {
            addCriterion("invest_period_five_eight >=", value, "investPeriodFiveEight");
            return (Criteria) this;
        }

        public Criteria andInvestPeriodFiveEightLessThan(Integer value) {
            addCriterion("invest_period_five_eight <", value, "investPeriodFiveEight");
            return (Criteria) this;
        }

        public Criteria andInvestPeriodFiveEightLessThanOrEqualTo(Integer value) {
            addCriterion("invest_period_five_eight <=", value, "investPeriodFiveEight");
            return (Criteria) this;
        }

        public Criteria andInvestPeriodFiveEightIn(List<Integer> values) {
            addCriterion("invest_period_five_eight in", values, "investPeriodFiveEight");
            return (Criteria) this;
        }

        public Criteria andInvestPeriodFiveEightNotIn(List<Integer> values) {
            addCriterion("invest_period_five_eight not in", values, "investPeriodFiveEight");
            return (Criteria) this;
        }

        public Criteria andInvestPeriodFiveEightBetween(Integer value1, Integer value2) {
            addCriterion("invest_period_five_eight between", value1, value2, "investPeriodFiveEight");
            return (Criteria) this;
        }

        public Criteria andInvestPeriodFiveEightNotBetween(Integer value1, Integer value2) {
            addCriterion("invest_period_five_eight not between", value1, value2, "investPeriodFiveEight");
            return (Criteria) this;
        }

        public Criteria andInvestPeriodNineTwelIsNull() {
            addCriterion("invest_period_nine_twel is null");
            return (Criteria) this;
        }

        public Criteria andInvestPeriodNineTwelIsNotNull() {
            addCriterion("invest_period_nine_twel is not null");
            return (Criteria) this;
        }

        public Criteria andInvestPeriodNineTwelEqualTo(Integer value) {
            addCriterion("invest_period_nine_twel =", value, "investPeriodNineTwel");
            return (Criteria) this;
        }

        public Criteria andInvestPeriodNineTwelNotEqualTo(Integer value) {
            addCriterion("invest_period_nine_twel <>", value, "investPeriodNineTwel");
            return (Criteria) this;
        }

        public Criteria andInvestPeriodNineTwelGreaterThan(Integer value) {
            addCriterion("invest_period_nine_twel >", value, "investPeriodNineTwel");
            return (Criteria) this;
        }

        public Criteria andInvestPeriodNineTwelGreaterThanOrEqualTo(Integer value) {
            addCriterion("invest_period_nine_twel >=", value, "investPeriodNineTwel");
            return (Criteria) this;
        }

        public Criteria andInvestPeriodNineTwelLessThan(Integer value) {
            addCriterion("invest_period_nine_twel <", value, "investPeriodNineTwel");
            return (Criteria) this;
        }

        public Criteria andInvestPeriodNineTwelLessThanOrEqualTo(Integer value) {
            addCriterion("invest_period_nine_twel <=", value, "investPeriodNineTwel");
            return (Criteria) this;
        }

        public Criteria andInvestPeriodNineTwelIn(List<Integer> values) {
            addCriterion("invest_period_nine_twel in", values, "investPeriodNineTwel");
            return (Criteria) this;
        }

        public Criteria andInvestPeriodNineTwelNotIn(List<Integer> values) {
            addCriterion("invest_period_nine_twel not in", values, "investPeriodNineTwel");
            return (Criteria) this;
        }

        public Criteria andInvestPeriodNineTwelBetween(Integer value1, Integer value2) {
            addCriterion("invest_period_nine_twel between", value1, value2, "investPeriodNineTwel");
            return (Criteria) this;
        }

        public Criteria andInvestPeriodNineTwelNotBetween(Integer value1, Integer value2) {
            addCriterion("invest_period_nine_twel not between", value1, value2, "investPeriodNineTwel");
            return (Criteria) this;
        }

        public Criteria andInvestPeriodTwelTfIsNull() {
            addCriterion("invest_period_twel_tf is null");
            return (Criteria) this;
        }

        public Criteria andInvestPeriodTwelTfIsNotNull() {
            addCriterion("invest_period_twel_tf is not null");
            return (Criteria) this;
        }

        public Criteria andInvestPeriodTwelTfEqualTo(Integer value) {
            addCriterion("invest_period_twel_tf =", value, "investPeriodTwelTf");
            return (Criteria) this;
        }

        public Criteria andInvestPeriodTwelTfNotEqualTo(Integer value) {
            addCriterion("invest_period_twel_tf <>", value, "investPeriodTwelTf");
            return (Criteria) this;
        }

        public Criteria andInvestPeriodTwelTfGreaterThan(Integer value) {
            addCriterion("invest_period_twel_tf >", value, "investPeriodTwelTf");
            return (Criteria) this;
        }

        public Criteria andInvestPeriodTwelTfGreaterThanOrEqualTo(Integer value) {
            addCriterion("invest_period_twel_tf >=", value, "investPeriodTwelTf");
            return (Criteria) this;
        }

        public Criteria andInvestPeriodTwelTfLessThan(Integer value) {
            addCriterion("invest_period_twel_tf <", value, "investPeriodTwelTf");
            return (Criteria) this;
        }

        public Criteria andInvestPeriodTwelTfLessThanOrEqualTo(Integer value) {
            addCriterion("invest_period_twel_tf <=", value, "investPeriodTwelTf");
            return (Criteria) this;
        }

        public Criteria andInvestPeriodTwelTfIn(List<Integer> values) {
            addCriterion("invest_period_twel_tf in", values, "investPeriodTwelTf");
            return (Criteria) this;
        }

        public Criteria andInvestPeriodTwelTfNotIn(List<Integer> values) {
            addCriterion("invest_period_twel_tf not in", values, "investPeriodTwelTf");
            return (Criteria) this;
        }

        public Criteria andInvestPeriodTwelTfBetween(Integer value1, Integer value2) {
            addCriterion("invest_period_twel_tf between", value1, value2, "investPeriodTwelTf");
            return (Criteria) this;
        }

        public Criteria andInvestPeriodTwelTfNotBetween(Integer value1, Integer value2) {
            addCriterion("invest_period_twel_tf not between", value1, value2, "investPeriodTwelTf");
            return (Criteria) this;
        }

        public Criteria andInvestPeriodTfIsNull() {
            addCriterion("invest_period_tf is null");
            return (Criteria) this;
        }

        public Criteria andInvestPeriodTfIsNotNull() {
            addCriterion("invest_period_tf is not null");
            return (Criteria) this;
        }

        public Criteria andInvestPeriodTfEqualTo(Integer value) {
            addCriterion("invest_period_tf =", value, "investPeriodTf");
            return (Criteria) this;
        }

        public Criteria andInvestPeriodTfNotEqualTo(Integer value) {
            addCriterion("invest_period_tf <>", value, "investPeriodTf");
            return (Criteria) this;
        }

        public Criteria andInvestPeriodTfGreaterThan(Integer value) {
            addCriterion("invest_period_tf >", value, "investPeriodTf");
            return (Criteria) this;
        }

        public Criteria andInvestPeriodTfGreaterThanOrEqualTo(Integer value) {
            addCriterion("invest_period_tf >=", value, "investPeriodTf");
            return (Criteria) this;
        }

        public Criteria andInvestPeriodTfLessThan(Integer value) {
            addCriterion("invest_period_tf <", value, "investPeriodTf");
            return (Criteria) this;
        }

        public Criteria andInvestPeriodTfLessThanOrEqualTo(Integer value) {
            addCriterion("invest_period_tf <=", value, "investPeriodTf");
            return (Criteria) this;
        }

        public Criteria andInvestPeriodTfIn(List<Integer> values) {
            addCriterion("invest_period_tf in", values, "investPeriodTf");
            return (Criteria) this;
        }

        public Criteria andInvestPeriodTfNotIn(List<Integer> values) {
            addCriterion("invest_period_tf not in", values, "investPeriodTf");
            return (Criteria) this;
        }

        public Criteria andInvestPeriodTfBetween(Integer value1, Integer value2) {
            addCriterion("invest_period_tf between", value1, value2, "investPeriodTf");
            return (Criteria) this;
        }

        public Criteria andInvestPeriodTfNotBetween(Integer value1, Integer value2) {
            addCriterion("invest_period_tf not between", value1, value2, "investPeriodTf");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodOneIsNull() {
            addCriterion("credit_period_one is null");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodOneIsNotNull() {
            addCriterion("credit_period_one is not null");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodOneEqualTo(Integer value) {
            addCriterion("credit_period_one =", value, "creditPeriodOne");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodOneNotEqualTo(Integer value) {
            addCriterion("credit_period_one <>", value, "creditPeriodOne");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodOneGreaterThan(Integer value) {
            addCriterion("credit_period_one >", value, "creditPeriodOne");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodOneGreaterThanOrEqualTo(Integer value) {
            addCriterion("credit_period_one >=", value, "creditPeriodOne");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodOneLessThan(Integer value) {
            addCriterion("credit_period_one <", value, "creditPeriodOne");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodOneLessThanOrEqualTo(Integer value) {
            addCriterion("credit_period_one <=", value, "creditPeriodOne");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodOneIn(List<Integer> values) {
            addCriterion("credit_period_one in", values, "creditPeriodOne");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodOneNotIn(List<Integer> values) {
            addCriterion("credit_period_one not in", values, "creditPeriodOne");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodOneBetween(Integer value1, Integer value2) {
            addCriterion("credit_period_one between", value1, value2, "creditPeriodOne");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodOneNotBetween(Integer value1, Integer value2) {
            addCriterion("credit_period_one not between", value1, value2, "creditPeriodOne");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodTwoFourIsNull() {
            addCriterion("credit_period_two_four is null");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodTwoFourIsNotNull() {
            addCriterion("credit_period_two_four is not null");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodTwoFourEqualTo(Integer value) {
            addCriterion("credit_period_two_four =", value, "creditPeriodTwoFour");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodTwoFourNotEqualTo(Integer value) {
            addCriterion("credit_period_two_four <>", value, "creditPeriodTwoFour");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodTwoFourGreaterThan(Integer value) {
            addCriterion("credit_period_two_four >", value, "creditPeriodTwoFour");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodTwoFourGreaterThanOrEqualTo(Integer value) {
            addCriterion("credit_period_two_four >=", value, "creditPeriodTwoFour");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodTwoFourLessThan(Integer value) {
            addCriterion("credit_period_two_four <", value, "creditPeriodTwoFour");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodTwoFourLessThanOrEqualTo(Integer value) {
            addCriterion("credit_period_two_four <=", value, "creditPeriodTwoFour");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodTwoFourIn(List<Integer> values) {
            addCriterion("credit_period_two_four in", values, "creditPeriodTwoFour");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodTwoFourNotIn(List<Integer> values) {
            addCriterion("credit_period_two_four not in", values, "creditPeriodTwoFour");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodTwoFourBetween(Integer value1, Integer value2) {
            addCriterion("credit_period_two_four between", value1, value2, "creditPeriodTwoFour");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodTwoFourNotBetween(Integer value1, Integer value2) {
            addCriterion("credit_period_two_four not between", value1, value2, "creditPeriodTwoFour");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodFiveEightIsNull() {
            addCriterion("credit_period_five_eight is null");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodFiveEightIsNotNull() {
            addCriterion("credit_period_five_eight is not null");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodFiveEightEqualTo(Integer value) {
            addCriterion("credit_period_five_eight =", value, "creditPeriodFiveEight");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodFiveEightNotEqualTo(Integer value) {
            addCriterion("credit_period_five_eight <>", value, "creditPeriodFiveEight");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodFiveEightGreaterThan(Integer value) {
            addCriterion("credit_period_five_eight >", value, "creditPeriodFiveEight");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodFiveEightGreaterThanOrEqualTo(Integer value) {
            addCriterion("credit_period_five_eight >=", value, "creditPeriodFiveEight");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodFiveEightLessThan(Integer value) {
            addCriterion("credit_period_five_eight <", value, "creditPeriodFiveEight");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodFiveEightLessThanOrEqualTo(Integer value) {
            addCriterion("credit_period_five_eight <=", value, "creditPeriodFiveEight");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodFiveEightIn(List<Integer> values) {
            addCriterion("credit_period_five_eight in", values, "creditPeriodFiveEight");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodFiveEightNotIn(List<Integer> values) {
            addCriterion("credit_period_five_eight not in", values, "creditPeriodFiveEight");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodFiveEightBetween(Integer value1, Integer value2) {
            addCriterion("credit_period_five_eight between", value1, value2, "creditPeriodFiveEight");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodFiveEightNotBetween(Integer value1, Integer value2) {
            addCriterion("credit_period_five_eight not between", value1, value2, "creditPeriodFiveEight");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodNineTwelIsNull() {
            addCriterion("credit_period_nine_twel is null");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodNineTwelIsNotNull() {
            addCriterion("credit_period_nine_twel is not null");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodNineTwelEqualTo(Integer value) {
            addCriterion("credit_period_nine_twel =", value, "creditPeriodNineTwel");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodNineTwelNotEqualTo(Integer value) {
            addCriterion("credit_period_nine_twel <>", value, "creditPeriodNineTwel");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodNineTwelGreaterThan(Integer value) {
            addCriterion("credit_period_nine_twel >", value, "creditPeriodNineTwel");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodNineTwelGreaterThanOrEqualTo(Integer value) {
            addCriterion("credit_period_nine_twel >=", value, "creditPeriodNineTwel");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodNineTwelLessThan(Integer value) {
            addCriterion("credit_period_nine_twel <", value, "creditPeriodNineTwel");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodNineTwelLessThanOrEqualTo(Integer value) {
            addCriterion("credit_period_nine_twel <=", value, "creditPeriodNineTwel");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodNineTwelIn(List<Integer> values) {
            addCriterion("credit_period_nine_twel in", values, "creditPeriodNineTwel");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodNineTwelNotIn(List<Integer> values) {
            addCriterion("credit_period_nine_twel not in", values, "creditPeriodNineTwel");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodNineTwelBetween(Integer value1, Integer value2) {
            addCriterion("credit_period_nine_twel between", value1, value2, "creditPeriodNineTwel");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodNineTwelNotBetween(Integer value1, Integer value2) {
            addCriterion("credit_period_nine_twel not between", value1, value2, "creditPeriodNineTwel");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodTwelTfIsNull() {
            addCriterion("credit_period_twel_tf is null");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodTwelTfIsNotNull() {
            addCriterion("credit_period_twel_tf is not null");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodTwelTfEqualTo(Integer value) {
            addCriterion("credit_period_twel_tf =", value, "creditPeriodTwelTf");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodTwelTfNotEqualTo(Integer value) {
            addCriterion("credit_period_twel_tf <>", value, "creditPeriodTwelTf");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodTwelTfGreaterThan(Integer value) {
            addCriterion("credit_period_twel_tf >", value, "creditPeriodTwelTf");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodTwelTfGreaterThanOrEqualTo(Integer value) {
            addCriterion("credit_period_twel_tf >=", value, "creditPeriodTwelTf");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodTwelTfLessThan(Integer value) {
            addCriterion("credit_period_twel_tf <", value, "creditPeriodTwelTf");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodTwelTfLessThanOrEqualTo(Integer value) {
            addCriterion("credit_period_twel_tf <=", value, "creditPeriodTwelTf");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodTwelTfIn(List<Integer> values) {
            addCriterion("credit_period_twel_tf in", values, "creditPeriodTwelTf");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodTwelTfNotIn(List<Integer> values) {
            addCriterion("credit_period_twel_tf not in", values, "creditPeriodTwelTf");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodTwelTfBetween(Integer value1, Integer value2) {
            addCriterion("credit_period_twel_tf between", value1, value2, "creditPeriodTwelTf");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodTwelTfNotBetween(Integer value1, Integer value2) {
            addCriterion("credit_period_twel_tf not between", value1, value2, "creditPeriodTwelTf");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodTfIsNull() {
            addCriterion("credit_period_tf is null");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodTfIsNotNull() {
            addCriterion("credit_period_tf is not null");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodTfEqualTo(Integer value) {
            addCriterion("credit_period_tf =", value, "creditPeriodTf");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodTfNotEqualTo(Integer value) {
            addCriterion("credit_period_tf <>", value, "creditPeriodTf");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodTfGreaterThan(Integer value) {
            addCriterion("credit_period_tf >", value, "creditPeriodTf");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodTfGreaterThanOrEqualTo(Integer value) {
            addCriterion("credit_period_tf >=", value, "creditPeriodTf");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodTfLessThan(Integer value) {
            addCriterion("credit_period_tf <", value, "creditPeriodTf");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodTfLessThanOrEqualTo(Integer value) {
            addCriterion("credit_period_tf <=", value, "creditPeriodTf");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodTfIn(List<Integer> values) {
            addCriterion("credit_period_tf in", values, "creditPeriodTf");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodTfNotIn(List<Integer> values) {
            addCriterion("credit_period_tf not in", values, "creditPeriodTf");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodTfBetween(Integer value1, Integer value2) {
            addCriterion("credit_period_tf between", value1, value2, "creditPeriodTf");
            return (Criteria) this;
        }

        public Criteria andCreditPeriodTfNotBetween(Integer value1, Integer value2) {
            addCriterion("credit_period_tf not between", value1, value2, "creditPeriodTf");
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

        public Criteria andDataDateIsNull() {
            addCriterion("data_date is null");
            return (Criteria) this;
        }

        public Criteria andDataDateIsNotNull() {
            addCriterion("data_date is not null");
            return (Criteria) this;
        }

        public Criteria andDataDateEqualTo(String value) {
            addCriterion("data_date =", value, "dataDate");
            return (Criteria) this;
        }

        public Criteria andDataDateNotEqualTo(String value) {
            addCriterion("data_date <>", value, "dataDate");
            return (Criteria) this;
        }

        public Criteria andDataDateGreaterThan(String value) {
            addCriterion("data_date >", value, "dataDate");
            return (Criteria) this;
        }

        public Criteria andDataDateGreaterThanOrEqualTo(String value) {
            addCriterion("data_date >=", value, "dataDate");
            return (Criteria) this;
        }

        public Criteria andDataDateLessThan(String value) {
            addCriterion("data_date <", value, "dataDate");
            return (Criteria) this;
        }

        public Criteria andDataDateLessThanOrEqualTo(String value) {
            addCriterion("data_date <=", value, "dataDate");
            return (Criteria) this;
        }

        public Criteria andDataDateLike(String value) {
            addCriterion("data_date like", value, "dataDate");
            return (Criteria) this;
        }

        public Criteria andDataDateNotLike(String value) {
            addCriterion("data_date not like", value, "dataDate");
            return (Criteria) this;
        }

        public Criteria andDataDateIn(List<String> values) {
            addCriterion("data_date in", values, "dataDate");
            return (Criteria) this;
        }

        public Criteria andDataDateNotIn(List<String> values) {
            addCriterion("data_date not in", values, "dataDate");
            return (Criteria) this;
        }

        public Criteria andDataDateBetween(String value1, String value2) {
            addCriterion("data_date between", value1, value2, "dataDate");
            return (Criteria) this;
        }

        public Criteria andDataDateNotBetween(String value1, String value2) {
            addCriterion("data_date not between", value1, value2, "dataDate");
            return (Criteria) this;
        }

        public Criteria andDataMonthIsNull() {
            addCriterion("data_month is null");
            return (Criteria) this;
        }

        public Criteria andDataMonthIsNotNull() {
            addCriterion("data_month is not null");
            return (Criteria) this;
        }

        public Criteria andDataMonthEqualTo(String value) {
            addCriterion("data_month =", value, "dataMonth");
            return (Criteria) this;
        }

        public Criteria andDataMonthNotEqualTo(String value) {
            addCriterion("data_month <>", value, "dataMonth");
            return (Criteria) this;
        }

        public Criteria andDataMonthGreaterThan(String value) {
            addCriterion("data_month >", value, "dataMonth");
            return (Criteria) this;
        }

        public Criteria andDataMonthGreaterThanOrEqualTo(String value) {
            addCriterion("data_month >=", value, "dataMonth");
            return (Criteria) this;
        }

        public Criteria andDataMonthLessThan(String value) {
            addCriterion("data_month <", value, "dataMonth");
            return (Criteria) this;
        }

        public Criteria andDataMonthLessThanOrEqualTo(String value) {
            addCriterion("data_month <=", value, "dataMonth");
            return (Criteria) this;
        }

        public Criteria andDataMonthLike(String value) {
            addCriterion("data_month like", value, "dataMonth");
            return (Criteria) this;
        }

        public Criteria andDataMonthNotLike(String value) {
            addCriterion("data_month not like", value, "dataMonth");
            return (Criteria) this;
        }

        public Criteria andDataMonthIn(List<String> values) {
            addCriterion("data_month in", values, "dataMonth");
            return (Criteria) this;
        }

        public Criteria andDataMonthNotIn(List<String> values) {
            addCriterion("data_month not in", values, "dataMonth");
            return (Criteria) this;
        }

        public Criteria andDataMonthBetween(String value1, String value2) {
            addCriterion("data_month between", value1, value2, "dataMonth");
            return (Criteria) this;
        }

        public Criteria andDataMonthNotBetween(String value1, String value2) {
            addCriterion("data_month not between", value1, value2, "dataMonth");
            return (Criteria) this;
        }

        public Criteria andDataHourIsNull() {
            addCriterion("data_hour is null");
            return (Criteria) this;
        }

        public Criteria andDataHourIsNotNull() {
            addCriterion("data_hour is not null");
            return (Criteria) this;
        }

        public Criteria andDataHourEqualTo(String value) {
            addCriterion("data_hour =", value, "dataHour");
            return (Criteria) this;
        }

        public Criteria andDataHourNotEqualTo(String value) {
            addCriterion("data_hour <>", value, "dataHour");
            return (Criteria) this;
        }

        public Criteria andDataHourGreaterThan(String value) {
            addCriterion("data_hour >", value, "dataHour");
            return (Criteria) this;
        }

        public Criteria andDataHourGreaterThanOrEqualTo(String value) {
            addCriterion("data_hour >=", value, "dataHour");
            return (Criteria) this;
        }

        public Criteria andDataHourLessThan(String value) {
            addCriterion("data_hour <", value, "dataHour");
            return (Criteria) this;
        }

        public Criteria andDataHourLessThanOrEqualTo(String value) {
            addCriterion("data_hour <=", value, "dataHour");
            return (Criteria) this;
        }

        public Criteria andDataHourLike(String value) {
            addCriterion("data_hour like", value, "dataHour");
            return (Criteria) this;
        }

        public Criteria andDataHourNotLike(String value) {
            addCriterion("data_hour not like", value, "dataHour");
            return (Criteria) this;
        }

        public Criteria andDataHourIn(List<String> values) {
            addCriterion("data_hour in", values, "dataHour");
            return (Criteria) this;
        }

        public Criteria andDataHourNotIn(List<String> values) {
            addCriterion("data_hour not in", values, "dataHour");
            return (Criteria) this;
        }

        public Criteria andDataHourBetween(String value1, String value2) {
            addCriterion("data_hour between", value1, value2, "dataHour");
            return (Criteria) this;
        }

        public Criteria andDataHourNotBetween(String value1, String value2) {
            addCriterion("data_hour not between", value1, value2, "dataHour");
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