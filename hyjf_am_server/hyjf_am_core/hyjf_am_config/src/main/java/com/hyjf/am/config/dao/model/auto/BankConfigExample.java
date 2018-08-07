package com.hyjf.am.config.dao.model.auto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BankConfigExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public BankConfigExample() {
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

        public Criteria andCodeIsNull() {
            addCriterion("code is null");
            return (Criteria) this;
        }

        public Criteria andCodeIsNotNull() {
            addCriterion("code is not null");
            return (Criteria) this;
        }

        public Criteria andCodeEqualTo(String value) {
            addCriterion("code =", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotEqualTo(String value) {
            addCriterion("code <>", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeGreaterThan(String value) {
            addCriterion("code >", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeGreaterThanOrEqualTo(String value) {
            addCriterion("code >=", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLessThan(String value) {
            addCriterion("code <", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLessThanOrEqualTo(String value) {
            addCriterion("code <=", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLike(String value) {
            addCriterion("code like", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotLike(String value) {
            addCriterion("code not like", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeIn(List<String> values) {
            addCriterion("code in", values, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotIn(List<String> values) {
            addCriterion("code not in", values, "code");
            return (Criteria) this;
        }

        public Criteria andCodeBetween(String value1, String value2) {
            addCriterion("code between", value1, value2, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotBetween(String value1, String value2) {
            addCriterion("code not between", value1, value2, "code");
            return (Criteria) this;
        }

        public Criteria andAppLogoIsNull() {
            addCriterion("app_logo is null");
            return (Criteria) this;
        }

        public Criteria andAppLogoIsNotNull() {
            addCriterion("app_logo is not null");
            return (Criteria) this;
        }

        public Criteria andAppLogoEqualTo(String value) {
            addCriterion("app_logo =", value, "appLogo");
            return (Criteria) this;
        }

        public Criteria andAppLogoNotEqualTo(String value) {
            addCriterion("app_logo <>", value, "appLogo");
            return (Criteria) this;
        }

        public Criteria andAppLogoGreaterThan(String value) {
            addCriterion("app_logo >", value, "appLogo");
            return (Criteria) this;
        }

        public Criteria andAppLogoGreaterThanOrEqualTo(String value) {
            addCriterion("app_logo >=", value, "appLogo");
            return (Criteria) this;
        }

        public Criteria andAppLogoLessThan(String value) {
            addCriterion("app_logo <", value, "appLogo");
            return (Criteria) this;
        }

        public Criteria andAppLogoLessThanOrEqualTo(String value) {
            addCriterion("app_logo <=", value, "appLogo");
            return (Criteria) this;
        }

        public Criteria andAppLogoLike(String value) {
            addCriterion("app_logo like", value, "appLogo");
            return (Criteria) this;
        }

        public Criteria andAppLogoNotLike(String value) {
            addCriterion("app_logo not like", value, "appLogo");
            return (Criteria) this;
        }

        public Criteria andAppLogoIn(List<String> values) {
            addCriterion("app_logo in", values, "appLogo");
            return (Criteria) this;
        }

        public Criteria andAppLogoNotIn(List<String> values) {
            addCriterion("app_logo not in", values, "appLogo");
            return (Criteria) this;
        }

        public Criteria andAppLogoBetween(String value1, String value2) {
            addCriterion("app_logo between", value1, value2, "appLogo");
            return (Criteria) this;
        }

        public Criteria andAppLogoNotBetween(String value1, String value2) {
            addCriterion("app_logo not between", value1, value2, "appLogo");
            return (Criteria) this;
        }

        public Criteria andLogoIsNull() {
            addCriterion("logo is null");
            return (Criteria) this;
        }

        public Criteria andLogoIsNotNull() {
            addCriterion("logo is not null");
            return (Criteria) this;
        }

        public Criteria andLogoEqualTo(String value) {
            addCriterion("logo =", value, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoNotEqualTo(String value) {
            addCriterion("logo <>", value, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoGreaterThan(String value) {
            addCriterion("logo >", value, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoGreaterThanOrEqualTo(String value) {
            addCriterion("logo >=", value, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoLessThan(String value) {
            addCriterion("logo <", value, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoLessThanOrEqualTo(String value) {
            addCriterion("logo <=", value, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoLike(String value) {
            addCriterion("logo like", value, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoNotLike(String value) {
            addCriterion("logo not like", value, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoIn(List<String> values) {
            addCriterion("logo in", values, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoNotIn(List<String> values) {
            addCriterion("logo not in", values, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoBetween(String value1, String value2) {
            addCriterion("logo between", value1, value2, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoNotBetween(String value1, String value2) {
            addCriterion("logo not between", value1, value2, "logo");
            return (Criteria) this;
        }

        public Criteria andPersonalEbankIsNull() {
            addCriterion("personal_ebank is null");
            return (Criteria) this;
        }

        public Criteria andPersonalEbankIsNotNull() {
            addCriterion("personal_ebank is not null");
            return (Criteria) this;
        }

        public Criteria andPersonalEbankEqualTo(Integer value) {
            addCriterion("personal_ebank =", value, "personalEbank");
            return (Criteria) this;
        }

        public Criteria andPersonalEbankNotEqualTo(Integer value) {
            addCriterion("personal_ebank <>", value, "personalEbank");
            return (Criteria) this;
        }

        public Criteria andPersonalEbankGreaterThan(Integer value) {
            addCriterion("personal_ebank >", value, "personalEbank");
            return (Criteria) this;
        }

        public Criteria andPersonalEbankGreaterThanOrEqualTo(Integer value) {
            addCriterion("personal_ebank >=", value, "personalEbank");
            return (Criteria) this;
        }

        public Criteria andPersonalEbankLessThan(Integer value) {
            addCriterion("personal_ebank <", value, "personalEbank");
            return (Criteria) this;
        }

        public Criteria andPersonalEbankLessThanOrEqualTo(Integer value) {
            addCriterion("personal_ebank <=", value, "personalEbank");
            return (Criteria) this;
        }

        public Criteria andPersonalEbankIn(List<Integer> values) {
            addCriterion("personal_ebank in", values, "personalEbank");
            return (Criteria) this;
        }

        public Criteria andPersonalEbankNotIn(List<Integer> values) {
            addCriterion("personal_ebank not in", values, "personalEbank");
            return (Criteria) this;
        }

        public Criteria andPersonalEbankBetween(Integer value1, Integer value2) {
            addCriterion("personal_ebank between", value1, value2, "personalEbank");
            return (Criteria) this;
        }

        public Criteria andPersonalEbankNotBetween(Integer value1, Integer value2) {
            addCriterion("personal_ebank not between", value1, value2, "personalEbank");
            return (Criteria) this;
        }

        public Criteria andEnterpriseEbankIsNull() {
            addCriterion("enterprise_ebank is null");
            return (Criteria) this;
        }

        public Criteria andEnterpriseEbankIsNotNull() {
            addCriterion("enterprise_ebank is not null");
            return (Criteria) this;
        }

        public Criteria andEnterpriseEbankEqualTo(Integer value) {
            addCriterion("enterprise_ebank =", value, "enterpriseEbank");
            return (Criteria) this;
        }

        public Criteria andEnterpriseEbankNotEqualTo(Integer value) {
            addCriterion("enterprise_ebank <>", value, "enterpriseEbank");
            return (Criteria) this;
        }

        public Criteria andEnterpriseEbankGreaterThan(Integer value) {
            addCriterion("enterprise_ebank >", value, "enterpriseEbank");
            return (Criteria) this;
        }

        public Criteria andEnterpriseEbankGreaterThanOrEqualTo(Integer value) {
            addCriterion("enterprise_ebank >=", value, "enterpriseEbank");
            return (Criteria) this;
        }

        public Criteria andEnterpriseEbankLessThan(Integer value) {
            addCriterion("enterprise_ebank <", value, "enterpriseEbank");
            return (Criteria) this;
        }

        public Criteria andEnterpriseEbankLessThanOrEqualTo(Integer value) {
            addCriterion("enterprise_ebank <=", value, "enterpriseEbank");
            return (Criteria) this;
        }

        public Criteria andEnterpriseEbankIn(List<Integer> values) {
            addCriterion("enterprise_ebank in", values, "enterpriseEbank");
            return (Criteria) this;
        }

        public Criteria andEnterpriseEbankNotIn(List<Integer> values) {
            addCriterion("enterprise_ebank not in", values, "enterpriseEbank");
            return (Criteria) this;
        }

        public Criteria andEnterpriseEbankBetween(Integer value1, Integer value2) {
            addCriterion("enterprise_ebank between", value1, value2, "enterpriseEbank");
            return (Criteria) this;
        }

        public Criteria andEnterpriseEbankNotBetween(Integer value1, Integer value2) {
            addCriterion("enterprise_ebank not between", value1, value2, "enterpriseEbank");
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

        public Criteria andImmediatelyWithdrawIsNull() {
            addCriterion("immediately_withdraw is null");
            return (Criteria) this;
        }

        public Criteria andImmediatelyWithdrawIsNotNull() {
            addCriterion("immediately_withdraw is not null");
            return (Criteria) this;
        }

        public Criteria andImmediatelyWithdrawEqualTo(Integer value) {
            addCriterion("immediately_withdraw =", value, "immediatelyWithdraw");
            return (Criteria) this;
        }

        public Criteria andImmediatelyWithdrawNotEqualTo(Integer value) {
            addCriterion("immediately_withdraw <>", value, "immediatelyWithdraw");
            return (Criteria) this;
        }

        public Criteria andImmediatelyWithdrawGreaterThan(Integer value) {
            addCriterion("immediately_withdraw >", value, "immediatelyWithdraw");
            return (Criteria) this;
        }

        public Criteria andImmediatelyWithdrawGreaterThanOrEqualTo(Integer value) {
            addCriterion("immediately_withdraw >=", value, "immediatelyWithdraw");
            return (Criteria) this;
        }

        public Criteria andImmediatelyWithdrawLessThan(Integer value) {
            addCriterion("immediately_withdraw <", value, "immediatelyWithdraw");
            return (Criteria) this;
        }

        public Criteria andImmediatelyWithdrawLessThanOrEqualTo(Integer value) {
            addCriterion("immediately_withdraw <=", value, "immediatelyWithdraw");
            return (Criteria) this;
        }

        public Criteria andImmediatelyWithdrawIn(List<Integer> values) {
            addCriterion("immediately_withdraw in", values, "immediatelyWithdraw");
            return (Criteria) this;
        }

        public Criteria andImmediatelyWithdrawNotIn(List<Integer> values) {
            addCriterion("immediately_withdraw not in", values, "immediatelyWithdraw");
            return (Criteria) this;
        }

        public Criteria andImmediatelyWithdrawBetween(Integer value1, Integer value2) {
            addCriterion("immediately_withdraw between", value1, value2, "immediatelyWithdraw");
            return (Criteria) this;
        }

        public Criteria andImmediatelyWithdrawNotBetween(Integer value1, Integer value2) {
            addCriterion("immediately_withdraw not between", value1, value2, "immediatelyWithdraw");
            return (Criteria) this;
        }

        public Criteria andQuickWithdrawIsNull() {
            addCriterion("quick_withdraw is null");
            return (Criteria) this;
        }

        public Criteria andQuickWithdrawIsNotNull() {
            addCriterion("quick_withdraw is not null");
            return (Criteria) this;
        }

        public Criteria andQuickWithdrawEqualTo(Integer value) {
            addCriterion("quick_withdraw =", value, "quickWithdraw");
            return (Criteria) this;
        }

        public Criteria andQuickWithdrawNotEqualTo(Integer value) {
            addCriterion("quick_withdraw <>", value, "quickWithdraw");
            return (Criteria) this;
        }

        public Criteria andQuickWithdrawGreaterThan(Integer value) {
            addCriterion("quick_withdraw >", value, "quickWithdraw");
            return (Criteria) this;
        }

        public Criteria andQuickWithdrawGreaterThanOrEqualTo(Integer value) {
            addCriterion("quick_withdraw >=", value, "quickWithdraw");
            return (Criteria) this;
        }

        public Criteria andQuickWithdrawLessThan(Integer value) {
            addCriterion("quick_withdraw <", value, "quickWithdraw");
            return (Criteria) this;
        }

        public Criteria andQuickWithdrawLessThanOrEqualTo(Integer value) {
            addCriterion("quick_withdraw <=", value, "quickWithdraw");
            return (Criteria) this;
        }

        public Criteria andQuickWithdrawIn(List<Integer> values) {
            addCriterion("quick_withdraw in", values, "quickWithdraw");
            return (Criteria) this;
        }

        public Criteria andQuickWithdrawNotIn(List<Integer> values) {
            addCriterion("quick_withdraw not in", values, "quickWithdraw");
            return (Criteria) this;
        }

        public Criteria andQuickWithdrawBetween(Integer value1, Integer value2) {
            addCriterion("quick_withdraw between", value1, value2, "quickWithdraw");
            return (Criteria) this;
        }

        public Criteria andQuickWithdrawNotBetween(Integer value1, Integer value2) {
            addCriterion("quick_withdraw not between", value1, value2, "quickWithdraw");
            return (Criteria) this;
        }

        public Criteria andNormalWithdrawIsNull() {
            addCriterion("normal_withdraw is null");
            return (Criteria) this;
        }

        public Criteria andNormalWithdrawIsNotNull() {
            addCriterion("normal_withdraw is not null");
            return (Criteria) this;
        }

        public Criteria andNormalWithdrawEqualTo(Integer value) {
            addCriterion("normal_withdraw =", value, "normalWithdraw");
            return (Criteria) this;
        }

        public Criteria andNormalWithdrawNotEqualTo(Integer value) {
            addCriterion("normal_withdraw <>", value, "normalWithdraw");
            return (Criteria) this;
        }

        public Criteria andNormalWithdrawGreaterThan(Integer value) {
            addCriterion("normal_withdraw >", value, "normalWithdraw");
            return (Criteria) this;
        }

        public Criteria andNormalWithdrawGreaterThanOrEqualTo(Integer value) {
            addCriterion("normal_withdraw >=", value, "normalWithdraw");
            return (Criteria) this;
        }

        public Criteria andNormalWithdrawLessThan(Integer value) {
            addCriterion("normal_withdraw <", value, "normalWithdraw");
            return (Criteria) this;
        }

        public Criteria andNormalWithdrawLessThanOrEqualTo(Integer value) {
            addCriterion("normal_withdraw <=", value, "normalWithdraw");
            return (Criteria) this;
        }

        public Criteria andNormalWithdrawIn(List<Integer> values) {
            addCriterion("normal_withdraw in", values, "normalWithdraw");
            return (Criteria) this;
        }

        public Criteria andNormalWithdrawNotIn(List<Integer> values) {
            addCriterion("normal_withdraw not in", values, "normalWithdraw");
            return (Criteria) this;
        }

        public Criteria andNormalWithdrawBetween(Integer value1, Integer value2) {
            addCriterion("normal_withdraw between", value1, value2, "normalWithdraw");
            return (Criteria) this;
        }

        public Criteria andNormalWithdrawNotBetween(Integer value1, Integer value2) {
            addCriterion("normal_withdraw not between", value1, value2, "normalWithdraw");
            return (Criteria) this;
        }

        public Criteria andWithdrawDefaulttypeIsNull() {
            addCriterion("withdraw_defaulttype is null");
            return (Criteria) this;
        }

        public Criteria andWithdrawDefaulttypeIsNotNull() {
            addCriterion("withdraw_defaulttype is not null");
            return (Criteria) this;
        }

        public Criteria andWithdrawDefaulttypeEqualTo(Integer value) {
            addCriterion("withdraw_defaulttype =", value, "withdrawDefaulttype");
            return (Criteria) this;
        }

        public Criteria andWithdrawDefaulttypeNotEqualTo(Integer value) {
            addCriterion("withdraw_defaulttype <>", value, "withdrawDefaulttype");
            return (Criteria) this;
        }

        public Criteria andWithdrawDefaulttypeGreaterThan(Integer value) {
            addCriterion("withdraw_defaulttype >", value, "withdrawDefaulttype");
            return (Criteria) this;
        }

        public Criteria andWithdrawDefaulttypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("withdraw_defaulttype >=", value, "withdrawDefaulttype");
            return (Criteria) this;
        }

        public Criteria andWithdrawDefaulttypeLessThan(Integer value) {
            addCriterion("withdraw_defaulttype <", value, "withdrawDefaulttype");
            return (Criteria) this;
        }

        public Criteria andWithdrawDefaulttypeLessThanOrEqualTo(Integer value) {
            addCriterion("withdraw_defaulttype <=", value, "withdrawDefaulttype");
            return (Criteria) this;
        }

        public Criteria andWithdrawDefaulttypeIn(List<Integer> values) {
            addCriterion("withdraw_defaulttype in", values, "withdrawDefaulttype");
            return (Criteria) this;
        }

        public Criteria andWithdrawDefaulttypeNotIn(List<Integer> values) {
            addCriterion("withdraw_defaulttype not in", values, "withdrawDefaulttype");
            return (Criteria) this;
        }

        public Criteria andWithdrawDefaulttypeBetween(Integer value1, Integer value2) {
            addCriterion("withdraw_defaulttype between", value1, value2, "withdrawDefaulttype");
            return (Criteria) this;
        }

        public Criteria andWithdrawDefaulttypeNotBetween(Integer value1, Integer value2) {
            addCriterion("withdraw_defaulttype not between", value1, value2, "withdrawDefaulttype");
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