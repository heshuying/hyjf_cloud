package com.hyjf.am.config.dao.model.auto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JxBankConfigExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public JxBankConfigExample() {
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

        public Criteria andBankIdIsNull() {
            addCriterion("bank_id is null");
            return (Criteria) this;
        }

        public Criteria andBankIdIsNotNull() {
            addCriterion("bank_id is not null");
            return (Criteria) this;
        }

        public Criteria andBankIdEqualTo(Integer value) {
            addCriterion("bank_id =", value, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdNotEqualTo(Integer value) {
            addCriterion("bank_id <>", value, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdGreaterThan(Integer value) {
            addCriterion("bank_id >", value, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("bank_id >=", value, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdLessThan(Integer value) {
            addCriterion("bank_id <", value, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdLessThanOrEqualTo(Integer value) {
            addCriterion("bank_id <=", value, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdIn(List<Integer> values) {
            addCriterion("bank_id in", values, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdNotIn(List<Integer> values) {
            addCriterion("bank_id not in", values, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdBetween(Integer value1, Integer value2) {
            addCriterion("bank_id between", value1, value2, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdNotBetween(Integer value1, Integer value2) {
            addCriterion("bank_id not between", value1, value2, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankNameIsNull() {
            addCriterion("bank_name is null");
            return (Criteria) this;
        }

        public Criteria andBankNameIsNotNull() {
            addCriterion("bank_name is not null");
            return (Criteria) this;
        }

        public Criteria andBankNameEqualTo(String value) {
            addCriterion("bank_name =", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameNotEqualTo(String value) {
            addCriterion("bank_name <>", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameGreaterThan(String value) {
            addCriterion("bank_name >", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameGreaterThanOrEqualTo(String value) {
            addCriterion("bank_name >=", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameLessThan(String value) {
            addCriterion("bank_name <", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameLessThanOrEqualTo(String value) {
            addCriterion("bank_name <=", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameLike(String value) {
            addCriterion("bank_name like", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameNotLike(String value) {
            addCriterion("bank_name not like", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameIn(List<String> values) {
            addCriterion("bank_name in", values, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameNotIn(List<String> values) {
            addCriterion("bank_name not in", values, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameBetween(String value1, String value2) {
            addCriterion("bank_name between", value1, value2, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameNotBetween(String value1, String value2) {
            addCriterion("bank_name not between", value1, value2, "bankName");
            return (Criteria) this;
        }

        public Criteria andPayAllianceCodeIsNull() {
            addCriterion("pay_alliance_code is null");
            return (Criteria) this;
        }

        public Criteria andPayAllianceCodeIsNotNull() {
            addCriterion("pay_alliance_code is not null");
            return (Criteria) this;
        }

        public Criteria andPayAllianceCodeEqualTo(String value) {
            addCriterion("pay_alliance_code =", value, "payAllianceCode");
            return (Criteria) this;
        }

        public Criteria andPayAllianceCodeNotEqualTo(String value) {
            addCriterion("pay_alliance_code <>", value, "payAllianceCode");
            return (Criteria) this;
        }

        public Criteria andPayAllianceCodeGreaterThan(String value) {
            addCriterion("pay_alliance_code >", value, "payAllianceCode");
            return (Criteria) this;
        }

        public Criteria andPayAllianceCodeGreaterThanOrEqualTo(String value) {
            addCriterion("pay_alliance_code >=", value, "payAllianceCode");
            return (Criteria) this;
        }

        public Criteria andPayAllianceCodeLessThan(String value) {
            addCriterion("pay_alliance_code <", value, "payAllianceCode");
            return (Criteria) this;
        }

        public Criteria andPayAllianceCodeLessThanOrEqualTo(String value) {
            addCriterion("pay_alliance_code <=", value, "payAllianceCode");
            return (Criteria) this;
        }

        public Criteria andPayAllianceCodeLike(String value) {
            addCriterion("pay_alliance_code like", value, "payAllianceCode");
            return (Criteria) this;
        }

        public Criteria andPayAllianceCodeNotLike(String value) {
            addCriterion("pay_alliance_code not like", value, "payAllianceCode");
            return (Criteria) this;
        }

        public Criteria andPayAllianceCodeIn(List<String> values) {
            addCriterion("pay_alliance_code in", values, "payAllianceCode");
            return (Criteria) this;
        }

        public Criteria andPayAllianceCodeNotIn(List<String> values) {
            addCriterion("pay_alliance_code not in", values, "payAllianceCode");
            return (Criteria) this;
        }

        public Criteria andPayAllianceCodeBetween(String value1, String value2) {
            addCriterion("pay_alliance_code between", value1, value2, "payAllianceCode");
            return (Criteria) this;
        }

        public Criteria andPayAllianceCodeNotBetween(String value1, String value2) {
            addCriterion("pay_alliance_code not between", value1, value2, "payAllianceCode");
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

        public Criteria andBankIconIsNull() {
            addCriterion("bank_icon is null");
            return (Criteria) this;
        }

        public Criteria andBankIconIsNotNull() {
            addCriterion("bank_icon is not null");
            return (Criteria) this;
        }

        public Criteria andBankIconEqualTo(String value) {
            addCriterion("bank_icon =", value, "bankIcon");
            return (Criteria) this;
        }

        public Criteria andBankIconNotEqualTo(String value) {
            addCriterion("bank_icon <>", value, "bankIcon");
            return (Criteria) this;
        }

        public Criteria andBankIconGreaterThan(String value) {
            addCriterion("bank_icon >", value, "bankIcon");
            return (Criteria) this;
        }

        public Criteria andBankIconGreaterThanOrEqualTo(String value) {
            addCriterion("bank_icon >=", value, "bankIcon");
            return (Criteria) this;
        }

        public Criteria andBankIconLessThan(String value) {
            addCriterion("bank_icon <", value, "bankIcon");
            return (Criteria) this;
        }

        public Criteria andBankIconLessThanOrEqualTo(String value) {
            addCriterion("bank_icon <=", value, "bankIcon");
            return (Criteria) this;
        }

        public Criteria andBankIconLike(String value) {
            addCriterion("bank_icon like", value, "bankIcon");
            return (Criteria) this;
        }

        public Criteria andBankIconNotLike(String value) {
            addCriterion("bank_icon not like", value, "bankIcon");
            return (Criteria) this;
        }

        public Criteria andBankIconIn(List<String> values) {
            addCriterion("bank_icon in", values, "bankIcon");
            return (Criteria) this;
        }

        public Criteria andBankIconNotIn(List<String> values) {
            addCriterion("bank_icon not in", values, "bankIcon");
            return (Criteria) this;
        }

        public Criteria andBankIconBetween(String value1, String value2) {
            addCriterion("bank_icon between", value1, value2, "bankIcon");
            return (Criteria) this;
        }

        public Criteria andBankIconNotBetween(String value1, String value2) {
            addCriterion("bank_icon not between", value1, value2, "bankIcon");
            return (Criteria) this;
        }

        public Criteria andBankLogoIsNull() {
            addCriterion("bank_logo is null");
            return (Criteria) this;
        }

        public Criteria andBankLogoIsNotNull() {
            addCriterion("bank_logo is not null");
            return (Criteria) this;
        }

        public Criteria andBankLogoEqualTo(String value) {
            addCriterion("bank_logo =", value, "bankLogo");
            return (Criteria) this;
        }

        public Criteria andBankLogoNotEqualTo(String value) {
            addCriterion("bank_logo <>", value, "bankLogo");
            return (Criteria) this;
        }

        public Criteria andBankLogoGreaterThan(String value) {
            addCriterion("bank_logo >", value, "bankLogo");
            return (Criteria) this;
        }

        public Criteria andBankLogoGreaterThanOrEqualTo(String value) {
            addCriterion("bank_logo >=", value, "bankLogo");
            return (Criteria) this;
        }

        public Criteria andBankLogoLessThan(String value) {
            addCriterion("bank_logo <", value, "bankLogo");
            return (Criteria) this;
        }

        public Criteria andBankLogoLessThanOrEqualTo(String value) {
            addCriterion("bank_logo <=", value, "bankLogo");
            return (Criteria) this;
        }

        public Criteria andBankLogoLike(String value) {
            addCriterion("bank_logo like", value, "bankLogo");
            return (Criteria) this;
        }

        public Criteria andBankLogoNotLike(String value) {
            addCriterion("bank_logo not like", value, "bankLogo");
            return (Criteria) this;
        }

        public Criteria andBankLogoIn(List<String> values) {
            addCriterion("bank_logo in", values, "bankLogo");
            return (Criteria) this;
        }

        public Criteria andBankLogoNotIn(List<String> values) {
            addCriterion("bank_logo not in", values, "bankLogo");
            return (Criteria) this;
        }

        public Criteria andBankLogoBetween(String value1, String value2) {
            addCriterion("bank_logo between", value1, value2, "bankLogo");
            return (Criteria) this;
        }

        public Criteria andBankLogoNotBetween(String value1, String value2) {
            addCriterion("bank_logo not between", value1, value2, "bankLogo");
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

        public Criteria andQuickPaymentEqualTo(Integer value) {
            addCriterion("quick_payment =", value, "quickPayment");
            return (Criteria) this;
        }

        public Criteria andQuickPaymentNotEqualTo(Integer value) {
            addCriterion("quick_payment <>", value, "quickPayment");
            return (Criteria) this;
        }

        public Criteria andQuickPaymentGreaterThan(Integer value) {
            addCriterion("quick_payment >", value, "quickPayment");
            return (Criteria) this;
        }

        public Criteria andQuickPaymentGreaterThanOrEqualTo(Integer value) {
            addCriterion("quick_payment >=", value, "quickPayment");
            return (Criteria) this;
        }

        public Criteria andQuickPaymentLessThan(Integer value) {
            addCriterion("quick_payment <", value, "quickPayment");
            return (Criteria) this;
        }

        public Criteria andQuickPaymentLessThanOrEqualTo(Integer value) {
            addCriterion("quick_payment <=", value, "quickPayment");
            return (Criteria) this;
        }

        public Criteria andQuickPaymentIn(List<Integer> values) {
            addCriterion("quick_payment in", values, "quickPayment");
            return (Criteria) this;
        }

        public Criteria andQuickPaymentNotIn(List<Integer> values) {
            addCriterion("quick_payment not in", values, "quickPayment");
            return (Criteria) this;
        }

        public Criteria andQuickPaymentBetween(Integer value1, Integer value2) {
            addCriterion("quick_payment between", value1, value2, "quickPayment");
            return (Criteria) this;
        }

        public Criteria andQuickPaymentNotBetween(Integer value1, Integer value2) {
            addCriterion("quick_payment not between", value1, value2, "quickPayment");
            return (Criteria) this;
        }

        public Criteria andSingleQuotaIsNull() {
            addCriterion("single_quota is null");
            return (Criteria) this;
        }

        public Criteria andSingleQuotaIsNotNull() {
            addCriterion("single_quota is not null");
            return (Criteria) this;
        }

        public Criteria andSingleQuotaEqualTo(BigDecimal value) {
            addCriterion("single_quota =", value, "singleQuota");
            return (Criteria) this;
        }

        public Criteria andSingleQuotaNotEqualTo(BigDecimal value) {
            addCriterion("single_quota <>", value, "singleQuota");
            return (Criteria) this;
        }

        public Criteria andSingleQuotaGreaterThan(BigDecimal value) {
            addCriterion("single_quota >", value, "singleQuota");
            return (Criteria) this;
        }

        public Criteria andSingleQuotaGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("single_quota >=", value, "singleQuota");
            return (Criteria) this;
        }

        public Criteria andSingleQuotaLessThan(BigDecimal value) {
            addCriterion("single_quota <", value, "singleQuota");
            return (Criteria) this;
        }

        public Criteria andSingleQuotaLessThanOrEqualTo(BigDecimal value) {
            addCriterion("single_quota <=", value, "singleQuota");
            return (Criteria) this;
        }

        public Criteria andSingleQuotaIn(List<BigDecimal> values) {
            addCriterion("single_quota in", values, "singleQuota");
            return (Criteria) this;
        }

        public Criteria andSingleQuotaNotIn(List<BigDecimal> values) {
            addCriterion("single_quota not in", values, "singleQuota");
            return (Criteria) this;
        }

        public Criteria andSingleQuotaBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("single_quota between", value1, value2, "singleQuota");
            return (Criteria) this;
        }

        public Criteria andSingleQuotaNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("single_quota not between", value1, value2, "singleQuota");
            return (Criteria) this;
        }

        public Criteria andSingleCardQuotaIsNull() {
            addCriterion("single_card_quota is null");
            return (Criteria) this;
        }

        public Criteria andSingleCardQuotaIsNotNull() {
            addCriterion("single_card_quota is not null");
            return (Criteria) this;
        }

        public Criteria andSingleCardQuotaEqualTo(BigDecimal value) {
            addCriterion("single_card_quota =", value, "singleCardQuota");
            return (Criteria) this;
        }

        public Criteria andSingleCardQuotaNotEqualTo(BigDecimal value) {
            addCriterion("single_card_quota <>", value, "singleCardQuota");
            return (Criteria) this;
        }

        public Criteria andSingleCardQuotaGreaterThan(BigDecimal value) {
            addCriterion("single_card_quota >", value, "singleCardQuota");
            return (Criteria) this;
        }

        public Criteria andSingleCardQuotaGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("single_card_quota >=", value, "singleCardQuota");
            return (Criteria) this;
        }

        public Criteria andSingleCardQuotaLessThan(BigDecimal value) {
            addCriterion("single_card_quota <", value, "singleCardQuota");
            return (Criteria) this;
        }

        public Criteria andSingleCardQuotaLessThanOrEqualTo(BigDecimal value) {
            addCriterion("single_card_quota <=", value, "singleCardQuota");
            return (Criteria) this;
        }

        public Criteria andSingleCardQuotaIn(List<BigDecimal> values) {
            addCriterion("single_card_quota in", values, "singleCardQuota");
            return (Criteria) this;
        }

        public Criteria andSingleCardQuotaNotIn(List<BigDecimal> values) {
            addCriterion("single_card_quota not in", values, "singleCardQuota");
            return (Criteria) this;
        }

        public Criteria andSingleCardQuotaBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("single_card_quota between", value1, value2, "singleCardQuota");
            return (Criteria) this;
        }

        public Criteria andSingleCardQuotaNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("single_card_quota not between", value1, value2, "singleCardQuota");
            return (Criteria) this;
        }

        public Criteria andFeeWithdrawIsNull() {
            addCriterion("fee_withdraw is null");
            return (Criteria) this;
        }

        public Criteria andFeeWithdrawIsNotNull() {
            addCriterion("fee_withdraw is not null");
            return (Criteria) this;
        }

        public Criteria andFeeWithdrawEqualTo(BigDecimal value) {
            addCriterion("fee_withdraw =", value, "feeWithdraw");
            return (Criteria) this;
        }

        public Criteria andFeeWithdrawNotEqualTo(BigDecimal value) {
            addCriterion("fee_withdraw <>", value, "feeWithdraw");
            return (Criteria) this;
        }

        public Criteria andFeeWithdrawGreaterThan(BigDecimal value) {
            addCriterion("fee_withdraw >", value, "feeWithdraw");
            return (Criteria) this;
        }

        public Criteria andFeeWithdrawGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("fee_withdraw >=", value, "feeWithdraw");
            return (Criteria) this;
        }

        public Criteria andFeeWithdrawLessThan(BigDecimal value) {
            addCriterion("fee_withdraw <", value, "feeWithdraw");
            return (Criteria) this;
        }

        public Criteria andFeeWithdrawLessThanOrEqualTo(BigDecimal value) {
            addCriterion("fee_withdraw <=", value, "feeWithdraw");
            return (Criteria) this;
        }

        public Criteria andFeeWithdrawIn(List<BigDecimal> values) {
            addCriterion("fee_withdraw in", values, "feeWithdraw");
            return (Criteria) this;
        }

        public Criteria andFeeWithdrawNotIn(List<BigDecimal> values) {
            addCriterion("fee_withdraw not in", values, "feeWithdraw");
            return (Criteria) this;
        }

        public Criteria andFeeWithdrawBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("fee_withdraw between", value1, value2, "feeWithdraw");
            return (Criteria) this;
        }

        public Criteria andFeeWithdrawNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("fee_withdraw not between", value1, value2, "feeWithdraw");
            return (Criteria) this;
        }

        public Criteria andSortIdIsNull() {
            addCriterion("sort_id is null");
            return (Criteria) this;
        }

        public Criteria andSortIdIsNotNull() {
            addCriterion("sort_id is not null");
            return (Criteria) this;
        }

        public Criteria andSortIdEqualTo(Short value) {
            addCriterion("sort_id =", value, "sortId");
            return (Criteria) this;
        }

        public Criteria andSortIdNotEqualTo(Short value) {
            addCriterion("sort_id <>", value, "sortId");
            return (Criteria) this;
        }

        public Criteria andSortIdGreaterThan(Short value) {
            addCriterion("sort_id >", value, "sortId");
            return (Criteria) this;
        }

        public Criteria andSortIdGreaterThanOrEqualTo(Short value) {
            addCriterion("sort_id >=", value, "sortId");
            return (Criteria) this;
        }

        public Criteria andSortIdLessThan(Short value) {
            addCriterion("sort_id <", value, "sortId");
            return (Criteria) this;
        }

        public Criteria andSortIdLessThanOrEqualTo(Short value) {
            addCriterion("sort_id <=", value, "sortId");
            return (Criteria) this;
        }

        public Criteria andSortIdIn(List<Short> values) {
            addCriterion("sort_id in", values, "sortId");
            return (Criteria) this;
        }

        public Criteria andSortIdNotIn(List<Short> values) {
            addCriterion("sort_id not in", values, "sortId");
            return (Criteria) this;
        }

        public Criteria andSortIdBetween(Short value1, Short value2) {
            addCriterion("sort_id between", value1, value2, "sortId");
            return (Criteria) this;
        }

        public Criteria andSortIdNotBetween(Short value1, Short value2) {
            addCriterion("sort_id not between", value1, value2, "sortId");
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

        public Criteria andMonthCardQuotaIsNull() {
            addCriterion("month_card_quota is null");
            return (Criteria) this;
        }

        public Criteria andMonthCardQuotaIsNotNull() {
            addCriterion("month_card_quota is not null");
            return (Criteria) this;
        }

        public Criteria andMonthCardQuotaEqualTo(BigDecimal value) {
            addCriterion("month_card_quota =", value, "monthCardQuota");
            return (Criteria) this;
        }

        public Criteria andMonthCardQuotaNotEqualTo(BigDecimal value) {
            addCriterion("month_card_quota <>", value, "monthCardQuota");
            return (Criteria) this;
        }

        public Criteria andMonthCardQuotaGreaterThan(BigDecimal value) {
            addCriterion("month_card_quota >", value, "monthCardQuota");
            return (Criteria) this;
        }

        public Criteria andMonthCardQuotaGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("month_card_quota >=", value, "monthCardQuota");
            return (Criteria) this;
        }

        public Criteria andMonthCardQuotaLessThan(BigDecimal value) {
            addCriterion("month_card_quota <", value, "monthCardQuota");
            return (Criteria) this;
        }

        public Criteria andMonthCardQuotaLessThanOrEqualTo(BigDecimal value) {
            addCriterion("month_card_quota <=", value, "monthCardQuota");
            return (Criteria) this;
        }

        public Criteria andMonthCardQuotaIn(List<BigDecimal> values) {
            addCriterion("month_card_quota in", values, "monthCardQuota");
            return (Criteria) this;
        }

        public Criteria andMonthCardQuotaNotIn(List<BigDecimal> values) {
            addCriterion("month_card_quota not in", values, "monthCardQuota");
            return (Criteria) this;
        }

        public Criteria andMonthCardQuotaBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("month_card_quota between", value1, value2, "monthCardQuota");
            return (Criteria) this;
        }

        public Criteria andMonthCardQuotaNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("month_card_quota not between", value1, value2, "monthCardQuota");
            return (Criteria) this;
        }

        public Criteria andAndroidMarketIsNull() {
            addCriterion("android_market is null");
            return (Criteria) this;
        }

        public Criteria andAndroidMarketIsNotNull() {
            addCriterion("android_market is not null");
            return (Criteria) this;
        }

        public Criteria andAndroidMarketEqualTo(String value) {
            addCriterion("android_market =", value, "androidMarket");
            return (Criteria) this;
        }

        public Criteria andAndroidMarketNotEqualTo(String value) {
            addCriterion("android_market <>", value, "androidMarket");
            return (Criteria) this;
        }

        public Criteria andAndroidMarketGreaterThan(String value) {
            addCriterion("android_market >", value, "androidMarket");
            return (Criteria) this;
        }

        public Criteria andAndroidMarketGreaterThanOrEqualTo(String value) {
            addCriterion("android_market >=", value, "androidMarket");
            return (Criteria) this;
        }

        public Criteria andAndroidMarketLessThan(String value) {
            addCriterion("android_market <", value, "androidMarket");
            return (Criteria) this;
        }

        public Criteria andAndroidMarketLessThanOrEqualTo(String value) {
            addCriterion("android_market <=", value, "androidMarket");
            return (Criteria) this;
        }

        public Criteria andAndroidMarketLike(String value) {
            addCriterion("android_market like", value, "androidMarket");
            return (Criteria) this;
        }

        public Criteria andAndroidMarketNotLike(String value) {
            addCriterion("android_market not like", value, "androidMarket");
            return (Criteria) this;
        }

        public Criteria andAndroidMarketIn(List<String> values) {
            addCriterion("android_market in", values, "androidMarket");
            return (Criteria) this;
        }

        public Criteria andAndroidMarketNotIn(List<String> values) {
            addCriterion("android_market not in", values, "androidMarket");
            return (Criteria) this;
        }

        public Criteria andAndroidMarketBetween(String value1, String value2) {
            addCriterion("android_market between", value1, value2, "androidMarket");
            return (Criteria) this;
        }

        public Criteria andAndroidMarketNotBetween(String value1, String value2) {
            addCriterion("android_market not between", value1, value2, "androidMarket");
            return (Criteria) this;
        }

        public Criteria andIosMarketIsNull() {
            addCriterion("ios_market is null");
            return (Criteria) this;
        }

        public Criteria andIosMarketIsNotNull() {
            addCriterion("ios_market is not null");
            return (Criteria) this;
        }

        public Criteria andIosMarketEqualTo(String value) {
            addCriterion("ios_market =", value, "iosMarket");
            return (Criteria) this;
        }

        public Criteria andIosMarketNotEqualTo(String value) {
            addCriterion("ios_market <>", value, "iosMarket");
            return (Criteria) this;
        }

        public Criteria andIosMarketGreaterThan(String value) {
            addCriterion("ios_market >", value, "iosMarket");
            return (Criteria) this;
        }

        public Criteria andIosMarketGreaterThanOrEqualTo(String value) {
            addCriterion("ios_market >=", value, "iosMarket");
            return (Criteria) this;
        }

        public Criteria andIosMarketLessThan(String value) {
            addCriterion("ios_market <", value, "iosMarket");
            return (Criteria) this;
        }

        public Criteria andIosMarketLessThanOrEqualTo(String value) {
            addCriterion("ios_market <=", value, "iosMarket");
            return (Criteria) this;
        }

        public Criteria andIosMarketLike(String value) {
            addCriterion("ios_market like", value, "iosMarket");
            return (Criteria) this;
        }

        public Criteria andIosMarketNotLike(String value) {
            addCriterion("ios_market not like", value, "iosMarket");
            return (Criteria) this;
        }

        public Criteria andIosMarketIn(List<String> values) {
            addCriterion("ios_market in", values, "iosMarket");
            return (Criteria) this;
        }

        public Criteria andIosMarketNotIn(List<String> values) {
            addCriterion("ios_market not in", values, "iosMarket");
            return (Criteria) this;
        }

        public Criteria andIosMarketBetween(String value1, String value2) {
            addCriterion("ios_market between", value1, value2, "iosMarket");
            return (Criteria) this;
        }

        public Criteria andIosMarketNotBetween(String value1, String value2) {
            addCriterion("ios_market not between", value1, value2, "iosMarket");
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