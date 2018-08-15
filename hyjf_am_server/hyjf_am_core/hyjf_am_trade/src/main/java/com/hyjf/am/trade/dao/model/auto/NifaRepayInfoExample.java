package com.hyjf.am.trade.dao.model.auto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NifaRepayInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public NifaRepayInfoExample() {
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

        public Criteria andPlatformNoIsNull() {
            addCriterion("platform_no is null");
            return (Criteria) this;
        }

        public Criteria andPlatformNoIsNotNull() {
            addCriterion("platform_no is not null");
            return (Criteria) this;
        }

        public Criteria andPlatformNoEqualTo(String value) {
            addCriterion("platform_no =", value, "platformNo");
            return (Criteria) this;
        }

        public Criteria andPlatformNoNotEqualTo(String value) {
            addCriterion("platform_no <>", value, "platformNo");
            return (Criteria) this;
        }

        public Criteria andPlatformNoGreaterThan(String value) {
            addCriterion("platform_no >", value, "platformNo");
            return (Criteria) this;
        }

        public Criteria andPlatformNoGreaterThanOrEqualTo(String value) {
            addCriterion("platform_no >=", value, "platformNo");
            return (Criteria) this;
        }

        public Criteria andPlatformNoLessThan(String value) {
            addCriterion("platform_no <", value, "platformNo");
            return (Criteria) this;
        }

        public Criteria andPlatformNoLessThanOrEqualTo(String value) {
            addCriterion("platform_no <=", value, "platformNo");
            return (Criteria) this;
        }

        public Criteria andPlatformNoLike(String value) {
            addCriterion("platform_no like", value, "platformNo");
            return (Criteria) this;
        }

        public Criteria andPlatformNoNotLike(String value) {
            addCriterion("platform_no not like", value, "platformNo");
            return (Criteria) this;
        }

        public Criteria andPlatformNoIn(List<String> values) {
            addCriterion("platform_no in", values, "platformNo");
            return (Criteria) this;
        }

        public Criteria andPlatformNoNotIn(List<String> values) {
            addCriterion("platform_no not in", values, "platformNo");
            return (Criteria) this;
        }

        public Criteria andPlatformNoBetween(String value1, String value2) {
            addCriterion("platform_no between", value1, value2, "platformNo");
            return (Criteria) this;
        }

        public Criteria andPlatformNoNotBetween(String value1, String value2) {
            addCriterion("platform_no not between", value1, value2, "platformNo");
            return (Criteria) this;
        }

        public Criteria andProjectNoIsNull() {
            addCriterion("project_no is null");
            return (Criteria) this;
        }

        public Criteria andProjectNoIsNotNull() {
            addCriterion("project_no is not null");
            return (Criteria) this;
        }

        public Criteria andProjectNoEqualTo(String value) {
            addCriterion("project_no =", value, "projectNo");
            return (Criteria) this;
        }

        public Criteria andProjectNoNotEqualTo(String value) {
            addCriterion("project_no <>", value, "projectNo");
            return (Criteria) this;
        }

        public Criteria andProjectNoGreaterThan(String value) {
            addCriterion("project_no >", value, "projectNo");
            return (Criteria) this;
        }

        public Criteria andProjectNoGreaterThanOrEqualTo(String value) {
            addCriterion("project_no >=", value, "projectNo");
            return (Criteria) this;
        }

        public Criteria andProjectNoLessThan(String value) {
            addCriterion("project_no <", value, "projectNo");
            return (Criteria) this;
        }

        public Criteria andProjectNoLessThanOrEqualTo(String value) {
            addCriterion("project_no <=", value, "projectNo");
            return (Criteria) this;
        }

        public Criteria andProjectNoLike(String value) {
            addCriterion("project_no like", value, "projectNo");
            return (Criteria) this;
        }

        public Criteria andProjectNoNotLike(String value) {
            addCriterion("project_no not like", value, "projectNo");
            return (Criteria) this;
        }

        public Criteria andProjectNoIn(List<String> values) {
            addCriterion("project_no in", values, "projectNo");
            return (Criteria) this;
        }

        public Criteria andProjectNoNotIn(List<String> values) {
            addCriterion("project_no not in", values, "projectNo");
            return (Criteria) this;
        }

        public Criteria andProjectNoBetween(String value1, String value2) {
            addCriterion("project_no between", value1, value2, "projectNo");
            return (Criteria) this;
        }

        public Criteria andProjectNoNotBetween(String value1, String value2) {
            addCriterion("project_no not between", value1, value2, "projectNo");
            return (Criteria) this;
        }

        public Criteria andPaymentNumIsNull() {
            addCriterion("payment_num is null");
            return (Criteria) this;
        }

        public Criteria andPaymentNumIsNotNull() {
            addCriterion("payment_num is not null");
            return (Criteria) this;
        }

        public Criteria andPaymentNumEqualTo(Integer value) {
            addCriterion("payment_num =", value, "paymentNum");
            return (Criteria) this;
        }

        public Criteria andPaymentNumNotEqualTo(Integer value) {
            addCriterion("payment_num <>", value, "paymentNum");
            return (Criteria) this;
        }

        public Criteria andPaymentNumGreaterThan(Integer value) {
            addCriterion("payment_num >", value, "paymentNum");
            return (Criteria) this;
        }

        public Criteria andPaymentNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("payment_num >=", value, "paymentNum");
            return (Criteria) this;
        }

        public Criteria andPaymentNumLessThan(Integer value) {
            addCriterion("payment_num <", value, "paymentNum");
            return (Criteria) this;
        }

        public Criteria andPaymentNumLessThanOrEqualTo(Integer value) {
            addCriterion("payment_num <=", value, "paymentNum");
            return (Criteria) this;
        }

        public Criteria andPaymentNumIn(List<Integer> values) {
            addCriterion("payment_num in", values, "paymentNum");
            return (Criteria) this;
        }

        public Criteria andPaymentNumNotIn(List<Integer> values) {
            addCriterion("payment_num not in", values, "paymentNum");
            return (Criteria) this;
        }

        public Criteria andPaymentNumBetween(Integer value1, Integer value2) {
            addCriterion("payment_num between", value1, value2, "paymentNum");
            return (Criteria) this;
        }

        public Criteria andPaymentNumNotBetween(Integer value1, Integer value2) {
            addCriterion("payment_num not between", value1, value2, "paymentNum");
            return (Criteria) this;
        }

        public Criteria andPaymentDateIsNull() {
            addCriterion("payment_date is null");
            return (Criteria) this;
        }

        public Criteria andPaymentDateIsNotNull() {
            addCriterion("payment_date is not null");
            return (Criteria) this;
        }

        public Criteria andPaymentDateEqualTo(String value) {
            addCriterion("payment_date =", value, "paymentDate");
            return (Criteria) this;
        }

        public Criteria andPaymentDateNotEqualTo(String value) {
            addCriterion("payment_date <>", value, "paymentDate");
            return (Criteria) this;
        }

        public Criteria andPaymentDateGreaterThan(String value) {
            addCriterion("payment_date >", value, "paymentDate");
            return (Criteria) this;
        }

        public Criteria andPaymentDateGreaterThanOrEqualTo(String value) {
            addCriterion("payment_date >=", value, "paymentDate");
            return (Criteria) this;
        }

        public Criteria andPaymentDateLessThan(String value) {
            addCriterion("payment_date <", value, "paymentDate");
            return (Criteria) this;
        }

        public Criteria andPaymentDateLessThanOrEqualTo(String value) {
            addCriterion("payment_date <=", value, "paymentDate");
            return (Criteria) this;
        }

        public Criteria andPaymentDateLike(String value) {
            addCriterion("payment_date like", value, "paymentDate");
            return (Criteria) this;
        }

        public Criteria andPaymentDateNotLike(String value) {
            addCriterion("payment_date not like", value, "paymentDate");
            return (Criteria) this;
        }

        public Criteria andPaymentDateIn(List<String> values) {
            addCriterion("payment_date in", values, "paymentDate");
            return (Criteria) this;
        }

        public Criteria andPaymentDateNotIn(List<String> values) {
            addCriterion("payment_date not in", values, "paymentDate");
            return (Criteria) this;
        }

        public Criteria andPaymentDateBetween(String value1, String value2) {
            addCriterion("payment_date between", value1, value2, "paymentDate");
            return (Criteria) this;
        }

        public Criteria andPaymentDateNotBetween(String value1, String value2) {
            addCriterion("payment_date not between", value1, value2, "paymentDate");
            return (Criteria) this;
        }

        public Criteria andPaymentPrincipalIsNull() {
            addCriterion("payment_principal is null");
            return (Criteria) this;
        }

        public Criteria andPaymentPrincipalIsNotNull() {
            addCriterion("payment_principal is not null");
            return (Criteria) this;
        }

        public Criteria andPaymentPrincipalEqualTo(String value) {
            addCriterion("payment_principal =", value, "paymentPrincipal");
            return (Criteria) this;
        }

        public Criteria andPaymentPrincipalNotEqualTo(String value) {
            addCriterion("payment_principal <>", value, "paymentPrincipal");
            return (Criteria) this;
        }

        public Criteria andPaymentPrincipalGreaterThan(String value) {
            addCriterion("payment_principal >", value, "paymentPrincipal");
            return (Criteria) this;
        }

        public Criteria andPaymentPrincipalGreaterThanOrEqualTo(String value) {
            addCriterion("payment_principal >=", value, "paymentPrincipal");
            return (Criteria) this;
        }

        public Criteria andPaymentPrincipalLessThan(String value) {
            addCriterion("payment_principal <", value, "paymentPrincipal");
            return (Criteria) this;
        }

        public Criteria andPaymentPrincipalLessThanOrEqualTo(String value) {
            addCriterion("payment_principal <=", value, "paymentPrincipal");
            return (Criteria) this;
        }

        public Criteria andPaymentPrincipalLike(String value) {
            addCriterion("payment_principal like", value, "paymentPrincipal");
            return (Criteria) this;
        }

        public Criteria andPaymentPrincipalNotLike(String value) {
            addCriterion("payment_principal not like", value, "paymentPrincipal");
            return (Criteria) this;
        }

        public Criteria andPaymentPrincipalIn(List<String> values) {
            addCriterion("payment_principal in", values, "paymentPrincipal");
            return (Criteria) this;
        }

        public Criteria andPaymentPrincipalNotIn(List<String> values) {
            addCriterion("payment_principal not in", values, "paymentPrincipal");
            return (Criteria) this;
        }

        public Criteria andPaymentPrincipalBetween(String value1, String value2) {
            addCriterion("payment_principal between", value1, value2, "paymentPrincipal");
            return (Criteria) this;
        }

        public Criteria andPaymentPrincipalNotBetween(String value1, String value2) {
            addCriterion("payment_principal not between", value1, value2, "paymentPrincipal");
            return (Criteria) this;
        }

        public Criteria andPaymentInterestIsNull() {
            addCriterion("payment_interest is null");
            return (Criteria) this;
        }

        public Criteria andPaymentInterestIsNotNull() {
            addCriterion("payment_interest is not null");
            return (Criteria) this;
        }

        public Criteria andPaymentInterestEqualTo(String value) {
            addCriterion("payment_interest =", value, "paymentInterest");
            return (Criteria) this;
        }

        public Criteria andPaymentInterestNotEqualTo(String value) {
            addCriterion("payment_interest <>", value, "paymentInterest");
            return (Criteria) this;
        }

        public Criteria andPaymentInterestGreaterThan(String value) {
            addCriterion("payment_interest >", value, "paymentInterest");
            return (Criteria) this;
        }

        public Criteria andPaymentInterestGreaterThanOrEqualTo(String value) {
            addCriterion("payment_interest >=", value, "paymentInterest");
            return (Criteria) this;
        }

        public Criteria andPaymentInterestLessThan(String value) {
            addCriterion("payment_interest <", value, "paymentInterest");
            return (Criteria) this;
        }

        public Criteria andPaymentInterestLessThanOrEqualTo(String value) {
            addCriterion("payment_interest <=", value, "paymentInterest");
            return (Criteria) this;
        }

        public Criteria andPaymentInterestLike(String value) {
            addCriterion("payment_interest like", value, "paymentInterest");
            return (Criteria) this;
        }

        public Criteria andPaymentInterestNotLike(String value) {
            addCriterion("payment_interest not like", value, "paymentInterest");
            return (Criteria) this;
        }

        public Criteria andPaymentInterestIn(List<String> values) {
            addCriterion("payment_interest in", values, "paymentInterest");
            return (Criteria) this;
        }

        public Criteria andPaymentInterestNotIn(List<String> values) {
            addCriterion("payment_interest not in", values, "paymentInterest");
            return (Criteria) this;
        }

        public Criteria andPaymentInterestBetween(String value1, String value2) {
            addCriterion("payment_interest between", value1, value2, "paymentInterest");
            return (Criteria) this;
        }

        public Criteria andPaymentInterestNotBetween(String value1, String value2) {
            addCriterion("payment_interest not between", value1, value2, "paymentInterest");
            return (Criteria) this;
        }

        public Criteria andPaymentSourceIsNull() {
            addCriterion("payment_source is null");
            return (Criteria) this;
        }

        public Criteria andPaymentSourceIsNotNull() {
            addCriterion("payment_source is not null");
            return (Criteria) this;
        }

        public Criteria andPaymentSourceEqualTo(Integer value) {
            addCriterion("payment_source =", value, "paymentSource");
            return (Criteria) this;
        }

        public Criteria andPaymentSourceNotEqualTo(Integer value) {
            addCriterion("payment_source <>", value, "paymentSource");
            return (Criteria) this;
        }

        public Criteria andPaymentSourceGreaterThan(Integer value) {
            addCriterion("payment_source >", value, "paymentSource");
            return (Criteria) this;
        }

        public Criteria andPaymentSourceGreaterThanOrEqualTo(Integer value) {
            addCriterion("payment_source >=", value, "paymentSource");
            return (Criteria) this;
        }

        public Criteria andPaymentSourceLessThan(Integer value) {
            addCriterion("payment_source <", value, "paymentSource");
            return (Criteria) this;
        }

        public Criteria andPaymentSourceLessThanOrEqualTo(Integer value) {
            addCriterion("payment_source <=", value, "paymentSource");
            return (Criteria) this;
        }

        public Criteria andPaymentSourceIn(List<Integer> values) {
            addCriterion("payment_source in", values, "paymentSource");
            return (Criteria) this;
        }

        public Criteria andPaymentSourceNotIn(List<Integer> values) {
            addCriterion("payment_source not in", values, "paymentSource");
            return (Criteria) this;
        }

        public Criteria andPaymentSourceBetween(Integer value1, Integer value2) {
            addCriterion("payment_source between", value1, value2, "paymentSource");
            return (Criteria) this;
        }

        public Criteria andPaymentSourceNotBetween(Integer value1, Integer value2) {
            addCriterion("payment_source not between", value1, value2, "paymentSource");
            return (Criteria) this;
        }

        public Criteria andPaymentSituationIsNull() {
            addCriterion("payment_situation is null");
            return (Criteria) this;
        }

        public Criteria andPaymentSituationIsNotNull() {
            addCriterion("payment_situation is not null");
            return (Criteria) this;
        }

        public Criteria andPaymentSituationEqualTo(Integer value) {
            addCriterion("payment_situation =", value, "paymentSituation");
            return (Criteria) this;
        }

        public Criteria andPaymentSituationNotEqualTo(Integer value) {
            addCriterion("payment_situation <>", value, "paymentSituation");
            return (Criteria) this;
        }

        public Criteria andPaymentSituationGreaterThan(Integer value) {
            addCriterion("payment_situation >", value, "paymentSituation");
            return (Criteria) this;
        }

        public Criteria andPaymentSituationGreaterThanOrEqualTo(Integer value) {
            addCriterion("payment_situation >=", value, "paymentSituation");
            return (Criteria) this;
        }

        public Criteria andPaymentSituationLessThan(Integer value) {
            addCriterion("payment_situation <", value, "paymentSituation");
            return (Criteria) this;
        }

        public Criteria andPaymentSituationLessThanOrEqualTo(Integer value) {
            addCriterion("payment_situation <=", value, "paymentSituation");
            return (Criteria) this;
        }

        public Criteria andPaymentSituationIn(List<Integer> values) {
            addCriterion("payment_situation in", values, "paymentSituation");
            return (Criteria) this;
        }

        public Criteria andPaymentSituationNotIn(List<Integer> values) {
            addCriterion("payment_situation not in", values, "paymentSituation");
            return (Criteria) this;
        }

        public Criteria andPaymentSituationBetween(Integer value1, Integer value2) {
            addCriterion("payment_situation between", value1, value2, "paymentSituation");
            return (Criteria) this;
        }

        public Criteria andPaymentSituationNotBetween(Integer value1, Integer value2) {
            addCriterion("payment_situation not between", value1, value2, "paymentSituation");
            return (Criteria) this;
        }

        public Criteria andPaymentPrincipalRestIsNull() {
            addCriterion("payment_principal_rest is null");
            return (Criteria) this;
        }

        public Criteria andPaymentPrincipalRestIsNotNull() {
            addCriterion("payment_principal_rest is not null");
            return (Criteria) this;
        }

        public Criteria andPaymentPrincipalRestEqualTo(String value) {
            addCriterion("payment_principal_rest =", value, "paymentPrincipalRest");
            return (Criteria) this;
        }

        public Criteria andPaymentPrincipalRestNotEqualTo(String value) {
            addCriterion("payment_principal_rest <>", value, "paymentPrincipalRest");
            return (Criteria) this;
        }

        public Criteria andPaymentPrincipalRestGreaterThan(String value) {
            addCriterion("payment_principal_rest >", value, "paymentPrincipalRest");
            return (Criteria) this;
        }

        public Criteria andPaymentPrincipalRestGreaterThanOrEqualTo(String value) {
            addCriterion("payment_principal_rest >=", value, "paymentPrincipalRest");
            return (Criteria) this;
        }

        public Criteria andPaymentPrincipalRestLessThan(String value) {
            addCriterion("payment_principal_rest <", value, "paymentPrincipalRest");
            return (Criteria) this;
        }

        public Criteria andPaymentPrincipalRestLessThanOrEqualTo(String value) {
            addCriterion("payment_principal_rest <=", value, "paymentPrincipalRest");
            return (Criteria) this;
        }

        public Criteria andPaymentPrincipalRestLike(String value) {
            addCriterion("payment_principal_rest like", value, "paymentPrincipalRest");
            return (Criteria) this;
        }

        public Criteria andPaymentPrincipalRestNotLike(String value) {
            addCriterion("payment_principal_rest not like", value, "paymentPrincipalRest");
            return (Criteria) this;
        }

        public Criteria andPaymentPrincipalRestIn(List<String> values) {
            addCriterion("payment_principal_rest in", values, "paymentPrincipalRest");
            return (Criteria) this;
        }

        public Criteria andPaymentPrincipalRestNotIn(List<String> values) {
            addCriterion("payment_principal_rest not in", values, "paymentPrincipalRest");
            return (Criteria) this;
        }

        public Criteria andPaymentPrincipalRestBetween(String value1, String value2) {
            addCriterion("payment_principal_rest between", value1, value2, "paymentPrincipalRest");
            return (Criteria) this;
        }

        public Criteria andPaymentPrincipalRestNotBetween(String value1, String value2) {
            addCriterion("payment_principal_rest not between", value1, value2, "paymentPrincipalRest");
            return (Criteria) this;
        }

        public Criteria andPaymentInterestRestIsNull() {
            addCriterion("payment_interest_rest is null");
            return (Criteria) this;
        }

        public Criteria andPaymentInterestRestIsNotNull() {
            addCriterion("payment_interest_rest is not null");
            return (Criteria) this;
        }

        public Criteria andPaymentInterestRestEqualTo(String value) {
            addCriterion("payment_interest_rest =", value, "paymentInterestRest");
            return (Criteria) this;
        }

        public Criteria andPaymentInterestRestNotEqualTo(String value) {
            addCriterion("payment_interest_rest <>", value, "paymentInterestRest");
            return (Criteria) this;
        }

        public Criteria andPaymentInterestRestGreaterThan(String value) {
            addCriterion("payment_interest_rest >", value, "paymentInterestRest");
            return (Criteria) this;
        }

        public Criteria andPaymentInterestRestGreaterThanOrEqualTo(String value) {
            addCriterion("payment_interest_rest >=", value, "paymentInterestRest");
            return (Criteria) this;
        }

        public Criteria andPaymentInterestRestLessThan(String value) {
            addCriterion("payment_interest_rest <", value, "paymentInterestRest");
            return (Criteria) this;
        }

        public Criteria andPaymentInterestRestLessThanOrEqualTo(String value) {
            addCriterion("payment_interest_rest <=", value, "paymentInterestRest");
            return (Criteria) this;
        }

        public Criteria andPaymentInterestRestLike(String value) {
            addCriterion("payment_interest_rest like", value, "paymentInterestRest");
            return (Criteria) this;
        }

        public Criteria andPaymentInterestRestNotLike(String value) {
            addCriterion("payment_interest_rest not like", value, "paymentInterestRest");
            return (Criteria) this;
        }

        public Criteria andPaymentInterestRestIn(List<String> values) {
            addCriterion("payment_interest_rest in", values, "paymentInterestRest");
            return (Criteria) this;
        }

        public Criteria andPaymentInterestRestNotIn(List<String> values) {
            addCriterion("payment_interest_rest not in", values, "paymentInterestRest");
            return (Criteria) this;
        }

        public Criteria andPaymentInterestRestBetween(String value1, String value2) {
            addCriterion("payment_interest_rest between", value1, value2, "paymentInterestRest");
            return (Criteria) this;
        }

        public Criteria andPaymentInterestRestNotBetween(String value1, String value2) {
            addCriterion("payment_interest_rest not between", value1, value2, "paymentInterestRest");
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