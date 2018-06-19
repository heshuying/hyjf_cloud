package com.hyjf.am.config.dao.model.auto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FeeConfigExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public FeeConfigExample() {
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

        public Criteria andBankCodeIsNull() {
            addCriterion("bank_code is null");
            return (Criteria) this;
        }

        public Criteria andBankCodeIsNotNull() {
            addCriterion("bank_code is not null");
            return (Criteria) this;
        }

        public Criteria andBankCodeEqualTo(String value) {
            addCriterion("bank_code =", value, "bankCode");
            return (Criteria) this;
        }

        public Criteria andBankCodeNotEqualTo(String value) {
            addCriterion("bank_code <>", value, "bankCode");
            return (Criteria) this;
        }

        public Criteria andBankCodeGreaterThan(String value) {
            addCriterion("bank_code >", value, "bankCode");
            return (Criteria) this;
        }

        public Criteria andBankCodeGreaterThanOrEqualTo(String value) {
            addCriterion("bank_code >=", value, "bankCode");
            return (Criteria) this;
        }

        public Criteria andBankCodeLessThan(String value) {
            addCriterion("bank_code <", value, "bankCode");
            return (Criteria) this;
        }

        public Criteria andBankCodeLessThanOrEqualTo(String value) {
            addCriterion("bank_code <=", value, "bankCode");
            return (Criteria) this;
        }

        public Criteria andBankCodeLike(String value) {
            addCriterion("bank_code like", value, "bankCode");
            return (Criteria) this;
        }

        public Criteria andBankCodeNotLike(String value) {
            addCriterion("bank_code not like", value, "bankCode");
            return (Criteria) this;
        }

        public Criteria andBankCodeIn(List<String> values) {
            addCriterion("bank_code in", values, "bankCode");
            return (Criteria) this;
        }

        public Criteria andBankCodeNotIn(List<String> values) {
            addCriterion("bank_code not in", values, "bankCode");
            return (Criteria) this;
        }

        public Criteria andBankCodeBetween(String value1, String value2) {
            addCriterion("bank_code between", value1, value2, "bankCode");
            return (Criteria) this;
        }

        public Criteria andBankCodeNotBetween(String value1, String value2) {
            addCriterion("bank_code not between", value1, value2, "bankCode");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("`name` is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("`name` is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("`name` =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("`name` <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("`name` >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("`name` >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("`name` <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("`name` <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("`name` like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("`name` not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("`name` in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("`name` not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("`name` between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("`name` not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andPersonalCreditIsNull() {
            addCriterion("personal_credit is null");
            return (Criteria) this;
        }

        public Criteria andPersonalCreditIsNotNull() {
            addCriterion("personal_credit is not null");
            return (Criteria) this;
        }

        public Criteria andPersonalCreditEqualTo(String value) {
            addCriterion("personal_credit =", value, "personalCredit");
            return (Criteria) this;
        }

        public Criteria andPersonalCreditNotEqualTo(String value) {
            addCriterion("personal_credit <>", value, "personalCredit");
            return (Criteria) this;
        }

        public Criteria andPersonalCreditGreaterThan(String value) {
            addCriterion("personal_credit >", value, "personalCredit");
            return (Criteria) this;
        }

        public Criteria andPersonalCreditGreaterThanOrEqualTo(String value) {
            addCriterion("personal_credit >=", value, "personalCredit");
            return (Criteria) this;
        }

        public Criteria andPersonalCreditLessThan(String value) {
            addCriterion("personal_credit <", value, "personalCredit");
            return (Criteria) this;
        }

        public Criteria andPersonalCreditLessThanOrEqualTo(String value) {
            addCriterion("personal_credit <=", value, "personalCredit");
            return (Criteria) this;
        }

        public Criteria andPersonalCreditLike(String value) {
            addCriterion("personal_credit like", value, "personalCredit");
            return (Criteria) this;
        }

        public Criteria andPersonalCreditNotLike(String value) {
            addCriterion("personal_credit not like", value, "personalCredit");
            return (Criteria) this;
        }

        public Criteria andPersonalCreditIn(List<String> values) {
            addCriterion("personal_credit in", values, "personalCredit");
            return (Criteria) this;
        }

        public Criteria andPersonalCreditNotIn(List<String> values) {
            addCriterion("personal_credit not in", values, "personalCredit");
            return (Criteria) this;
        }

        public Criteria andPersonalCreditBetween(String value1, String value2) {
            addCriterion("personal_credit between", value1, value2, "personalCredit");
            return (Criteria) this;
        }

        public Criteria andPersonalCreditNotBetween(String value1, String value2) {
            addCriterion("personal_credit not between", value1, value2, "personalCredit");
            return (Criteria) this;
        }

        public Criteria andEnterpriseCreditIsNull() {
            addCriterion("enterprise_credit is null");
            return (Criteria) this;
        }

        public Criteria andEnterpriseCreditIsNotNull() {
            addCriterion("enterprise_credit is not null");
            return (Criteria) this;
        }

        public Criteria andEnterpriseCreditEqualTo(String value) {
            addCriterion("enterprise_credit =", value, "enterpriseCredit");
            return (Criteria) this;
        }

        public Criteria andEnterpriseCreditNotEqualTo(String value) {
            addCriterion("enterprise_credit <>", value, "enterpriseCredit");
            return (Criteria) this;
        }

        public Criteria andEnterpriseCreditGreaterThan(String value) {
            addCriterion("enterprise_credit >", value, "enterpriseCredit");
            return (Criteria) this;
        }

        public Criteria andEnterpriseCreditGreaterThanOrEqualTo(String value) {
            addCriterion("enterprise_credit >=", value, "enterpriseCredit");
            return (Criteria) this;
        }

        public Criteria andEnterpriseCreditLessThan(String value) {
            addCriterion("enterprise_credit <", value, "enterpriseCredit");
            return (Criteria) this;
        }

        public Criteria andEnterpriseCreditLessThanOrEqualTo(String value) {
            addCriterion("enterprise_credit <=", value, "enterpriseCredit");
            return (Criteria) this;
        }

        public Criteria andEnterpriseCreditLike(String value) {
            addCriterion("enterprise_credit like", value, "enterpriseCredit");
            return (Criteria) this;
        }

        public Criteria andEnterpriseCreditNotLike(String value) {
            addCriterion("enterprise_credit not like", value, "enterpriseCredit");
            return (Criteria) this;
        }

        public Criteria andEnterpriseCreditIn(List<String> values) {
            addCriterion("enterprise_credit in", values, "enterpriseCredit");
            return (Criteria) this;
        }

        public Criteria andEnterpriseCreditNotIn(List<String> values) {
            addCriterion("enterprise_credit not in", values, "enterpriseCredit");
            return (Criteria) this;
        }

        public Criteria andEnterpriseCreditBetween(String value1, String value2) {
            addCriterion("enterprise_credit between", value1, value2, "enterpriseCredit");
            return (Criteria) this;
        }

        public Criteria andEnterpriseCreditNotBetween(String value1, String value2) {
            addCriterion("enterprise_credit not between", value1, value2, "enterpriseCredit");
            return (Criteria) this;
        }

        public Criteria andQuickPaymentIsNull() {
            addCriterion("quick_payment is null");
            return (Criteria) this;
        }

        public Criteria andQuickPaymentIsNotNull() {
            addCriterion("quick_payment is not null");
            return (Criteria) this;
        }

        public Criteria andQuickPaymentEqualTo(String value) {
            addCriterion("quick_payment =", value, "quickPayment");
            return (Criteria) this;
        }

        public Criteria andQuickPaymentNotEqualTo(String value) {
            addCriterion("quick_payment <>", value, "quickPayment");
            return (Criteria) this;
        }

        public Criteria andQuickPaymentGreaterThan(String value) {
            addCriterion("quick_payment >", value, "quickPayment");
            return (Criteria) this;
        }

        public Criteria andQuickPaymentGreaterThanOrEqualTo(String value) {
            addCriterion("quick_payment >=", value, "quickPayment");
            return (Criteria) this;
        }

        public Criteria andQuickPaymentLessThan(String value) {
            addCriterion("quick_payment <", value, "quickPayment");
            return (Criteria) this;
        }

        public Criteria andQuickPaymentLessThanOrEqualTo(String value) {
            addCriterion("quick_payment <=", value, "quickPayment");
            return (Criteria) this;
        }

        public Criteria andQuickPaymentLike(String value) {
            addCriterion("quick_payment like", value, "quickPayment");
            return (Criteria) this;
        }

        public Criteria andQuickPaymentNotLike(String value) {
            addCriterion("quick_payment not like", value, "quickPayment");
            return (Criteria) this;
        }

        public Criteria andQuickPaymentIn(List<String> values) {
            addCriterion("quick_payment in", values, "quickPayment");
            return (Criteria) this;
        }

        public Criteria andQuickPaymentNotIn(List<String> values) {
            addCriterion("quick_payment not in", values, "quickPayment");
            return (Criteria) this;
        }

        public Criteria andQuickPaymentBetween(String value1, String value2) {
            addCriterion("quick_payment between", value1, value2, "quickPayment");
            return (Criteria) this;
        }

        public Criteria andQuickPaymentNotBetween(String value1, String value2) {
            addCriterion("quick_payment not between", value1, value2, "quickPayment");
            return (Criteria) this;
        }

        public Criteria andDirectTakeoutIsNull() {
            addCriterion("direct_takeout is null");
            return (Criteria) this;
        }

        public Criteria andDirectTakeoutIsNotNull() {
            addCriterion("direct_takeout is not null");
            return (Criteria) this;
        }

        public Criteria andDirectTakeoutEqualTo(String value) {
            addCriterion("direct_takeout =", value, "directTakeout");
            return (Criteria) this;
        }

        public Criteria andDirectTakeoutNotEqualTo(String value) {
            addCriterion("direct_takeout <>", value, "directTakeout");
            return (Criteria) this;
        }

        public Criteria andDirectTakeoutGreaterThan(String value) {
            addCriterion("direct_takeout >", value, "directTakeout");
            return (Criteria) this;
        }

        public Criteria andDirectTakeoutGreaterThanOrEqualTo(String value) {
            addCriterion("direct_takeout >=", value, "directTakeout");
            return (Criteria) this;
        }

        public Criteria andDirectTakeoutLessThan(String value) {
            addCriterion("direct_takeout <", value, "directTakeout");
            return (Criteria) this;
        }

        public Criteria andDirectTakeoutLessThanOrEqualTo(String value) {
            addCriterion("direct_takeout <=", value, "directTakeout");
            return (Criteria) this;
        }

        public Criteria andDirectTakeoutLike(String value) {
            addCriterion("direct_takeout like", value, "directTakeout");
            return (Criteria) this;
        }

        public Criteria andDirectTakeoutNotLike(String value) {
            addCriterion("direct_takeout not like", value, "directTakeout");
            return (Criteria) this;
        }

        public Criteria andDirectTakeoutIn(List<String> values) {
            addCriterion("direct_takeout in", values, "directTakeout");
            return (Criteria) this;
        }

        public Criteria andDirectTakeoutNotIn(List<String> values) {
            addCriterion("direct_takeout not in", values, "directTakeout");
            return (Criteria) this;
        }

        public Criteria andDirectTakeoutBetween(String value1, String value2) {
            addCriterion("direct_takeout between", value1, value2, "directTakeout");
            return (Criteria) this;
        }

        public Criteria andDirectTakeoutNotBetween(String value1, String value2) {
            addCriterion("direct_takeout not between", value1, value2, "directTakeout");
            return (Criteria) this;
        }

        public Criteria andDirectTakeoutPercentIsNull() {
            addCriterion("direct_takeout_percent is null");
            return (Criteria) this;
        }

        public Criteria andDirectTakeoutPercentIsNotNull() {
            addCriterion("direct_takeout_percent is not null");
            return (Criteria) this;
        }

        public Criteria andDirectTakeoutPercentEqualTo(String value) {
            addCriterion("direct_takeout_percent =", value, "directTakeoutPercent");
            return (Criteria) this;
        }

        public Criteria andDirectTakeoutPercentNotEqualTo(String value) {
            addCriterion("direct_takeout_percent <>", value, "directTakeoutPercent");
            return (Criteria) this;
        }

        public Criteria andDirectTakeoutPercentGreaterThan(String value) {
            addCriterion("direct_takeout_percent >", value, "directTakeoutPercent");
            return (Criteria) this;
        }

        public Criteria andDirectTakeoutPercentGreaterThanOrEqualTo(String value) {
            addCriterion("direct_takeout_percent >=", value, "directTakeoutPercent");
            return (Criteria) this;
        }

        public Criteria andDirectTakeoutPercentLessThan(String value) {
            addCriterion("direct_takeout_percent <", value, "directTakeoutPercent");
            return (Criteria) this;
        }

        public Criteria andDirectTakeoutPercentLessThanOrEqualTo(String value) {
            addCriterion("direct_takeout_percent <=", value, "directTakeoutPercent");
            return (Criteria) this;
        }

        public Criteria andDirectTakeoutPercentLike(String value) {
            addCriterion("direct_takeout_percent like", value, "directTakeoutPercent");
            return (Criteria) this;
        }

        public Criteria andDirectTakeoutPercentNotLike(String value) {
            addCriterion("direct_takeout_percent not like", value, "directTakeoutPercent");
            return (Criteria) this;
        }

        public Criteria andDirectTakeoutPercentIn(List<String> values) {
            addCriterion("direct_takeout_percent in", values, "directTakeoutPercent");
            return (Criteria) this;
        }

        public Criteria andDirectTakeoutPercentNotIn(List<String> values) {
            addCriterion("direct_takeout_percent not in", values, "directTakeoutPercent");
            return (Criteria) this;
        }

        public Criteria andDirectTakeoutPercentBetween(String value1, String value2) {
            addCriterion("direct_takeout_percent between", value1, value2, "directTakeoutPercent");
            return (Criteria) this;
        }

        public Criteria andDirectTakeoutPercentNotBetween(String value1, String value2) {
            addCriterion("direct_takeout_percent not between", value1, value2, "directTakeoutPercent");
            return (Criteria) this;
        }

        public Criteria andQuickTakeoutIsNull() {
            addCriterion("quick_takeout is null");
            return (Criteria) this;
        }

        public Criteria andQuickTakeoutIsNotNull() {
            addCriterion("quick_takeout is not null");
            return (Criteria) this;
        }

        public Criteria andQuickTakeoutEqualTo(String value) {
            addCriterion("quick_takeout =", value, "quickTakeout");
            return (Criteria) this;
        }

        public Criteria andQuickTakeoutNotEqualTo(String value) {
            addCriterion("quick_takeout <>", value, "quickTakeout");
            return (Criteria) this;
        }

        public Criteria andQuickTakeoutGreaterThan(String value) {
            addCriterion("quick_takeout >", value, "quickTakeout");
            return (Criteria) this;
        }

        public Criteria andQuickTakeoutGreaterThanOrEqualTo(String value) {
            addCriterion("quick_takeout >=", value, "quickTakeout");
            return (Criteria) this;
        }

        public Criteria andQuickTakeoutLessThan(String value) {
            addCriterion("quick_takeout <", value, "quickTakeout");
            return (Criteria) this;
        }

        public Criteria andQuickTakeoutLessThanOrEqualTo(String value) {
            addCriterion("quick_takeout <=", value, "quickTakeout");
            return (Criteria) this;
        }

        public Criteria andQuickTakeoutLike(String value) {
            addCriterion("quick_takeout like", value, "quickTakeout");
            return (Criteria) this;
        }

        public Criteria andQuickTakeoutNotLike(String value) {
            addCriterion("quick_takeout not like", value, "quickTakeout");
            return (Criteria) this;
        }

        public Criteria andQuickTakeoutIn(List<String> values) {
            addCriterion("quick_takeout in", values, "quickTakeout");
            return (Criteria) this;
        }

        public Criteria andQuickTakeoutNotIn(List<String> values) {
            addCriterion("quick_takeout not in", values, "quickTakeout");
            return (Criteria) this;
        }

        public Criteria andQuickTakeoutBetween(String value1, String value2) {
            addCriterion("quick_takeout between", value1, value2, "quickTakeout");
            return (Criteria) this;
        }

        public Criteria andQuickTakeoutNotBetween(String value1, String value2) {
            addCriterion("quick_takeout not between", value1, value2, "quickTakeout");
            return (Criteria) this;
        }

        public Criteria andQuickTakeoutPercentIsNull() {
            addCriterion("quick_takeout_percent is null");
            return (Criteria) this;
        }

        public Criteria andQuickTakeoutPercentIsNotNull() {
            addCriterion("quick_takeout_percent is not null");
            return (Criteria) this;
        }

        public Criteria andQuickTakeoutPercentEqualTo(String value) {
            addCriterion("quick_takeout_percent =", value, "quickTakeoutPercent");
            return (Criteria) this;
        }

        public Criteria andQuickTakeoutPercentNotEqualTo(String value) {
            addCriterion("quick_takeout_percent <>", value, "quickTakeoutPercent");
            return (Criteria) this;
        }

        public Criteria andQuickTakeoutPercentGreaterThan(String value) {
            addCriterion("quick_takeout_percent >", value, "quickTakeoutPercent");
            return (Criteria) this;
        }

        public Criteria andQuickTakeoutPercentGreaterThanOrEqualTo(String value) {
            addCriterion("quick_takeout_percent >=", value, "quickTakeoutPercent");
            return (Criteria) this;
        }

        public Criteria andQuickTakeoutPercentLessThan(String value) {
            addCriterion("quick_takeout_percent <", value, "quickTakeoutPercent");
            return (Criteria) this;
        }

        public Criteria andQuickTakeoutPercentLessThanOrEqualTo(String value) {
            addCriterion("quick_takeout_percent <=", value, "quickTakeoutPercent");
            return (Criteria) this;
        }

        public Criteria andQuickTakeoutPercentLike(String value) {
            addCriterion("quick_takeout_percent like", value, "quickTakeoutPercent");
            return (Criteria) this;
        }

        public Criteria andQuickTakeoutPercentNotLike(String value) {
            addCriterion("quick_takeout_percent not like", value, "quickTakeoutPercent");
            return (Criteria) this;
        }

        public Criteria andQuickTakeoutPercentIn(List<String> values) {
            addCriterion("quick_takeout_percent in", values, "quickTakeoutPercent");
            return (Criteria) this;
        }

        public Criteria andQuickTakeoutPercentNotIn(List<String> values) {
            addCriterion("quick_takeout_percent not in", values, "quickTakeoutPercent");
            return (Criteria) this;
        }

        public Criteria andQuickTakeoutPercentBetween(String value1, String value2) {
            addCriterion("quick_takeout_percent between", value1, value2, "quickTakeoutPercent");
            return (Criteria) this;
        }

        public Criteria andQuickTakeoutPercentNotBetween(String value1, String value2) {
            addCriterion("quick_takeout_percent not between", value1, value2, "quickTakeoutPercent");
            return (Criteria) this;
        }

        public Criteria andNormalTakeoutIsNull() {
            addCriterion("normal_takeout is null");
            return (Criteria) this;
        }

        public Criteria andNormalTakeoutIsNotNull() {
            addCriterion("normal_takeout is not null");
            return (Criteria) this;
        }

        public Criteria andNormalTakeoutEqualTo(String value) {
            addCriterion("normal_takeout =", value, "normalTakeout");
            return (Criteria) this;
        }

        public Criteria andNormalTakeoutNotEqualTo(String value) {
            addCriterion("normal_takeout <>", value, "normalTakeout");
            return (Criteria) this;
        }

        public Criteria andNormalTakeoutGreaterThan(String value) {
            addCriterion("normal_takeout >", value, "normalTakeout");
            return (Criteria) this;
        }

        public Criteria andNormalTakeoutGreaterThanOrEqualTo(String value) {
            addCriterion("normal_takeout >=", value, "normalTakeout");
            return (Criteria) this;
        }

        public Criteria andNormalTakeoutLessThan(String value) {
            addCriterion("normal_takeout <", value, "normalTakeout");
            return (Criteria) this;
        }

        public Criteria andNormalTakeoutLessThanOrEqualTo(String value) {
            addCriterion("normal_takeout <=", value, "normalTakeout");
            return (Criteria) this;
        }

        public Criteria andNormalTakeoutLike(String value) {
            addCriterion("normal_takeout like", value, "normalTakeout");
            return (Criteria) this;
        }

        public Criteria andNormalTakeoutNotLike(String value) {
            addCriterion("normal_takeout not like", value, "normalTakeout");
            return (Criteria) this;
        }

        public Criteria andNormalTakeoutIn(List<String> values) {
            addCriterion("normal_takeout in", values, "normalTakeout");
            return (Criteria) this;
        }

        public Criteria andNormalTakeoutNotIn(List<String> values) {
            addCriterion("normal_takeout not in", values, "normalTakeout");
            return (Criteria) this;
        }

        public Criteria andNormalTakeoutBetween(String value1, String value2) {
            addCriterion("normal_takeout between", value1, value2, "normalTakeout");
            return (Criteria) this;
        }

        public Criteria andNormalTakeoutNotBetween(String value1, String value2) {
            addCriterion("normal_takeout not between", value1, value2, "normalTakeout");
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