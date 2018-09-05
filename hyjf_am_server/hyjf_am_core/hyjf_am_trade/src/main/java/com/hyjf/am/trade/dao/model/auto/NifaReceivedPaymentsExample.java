package com.hyjf.am.trade.dao.model.auto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NifaReceivedPaymentsExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public NifaReceivedPaymentsExample() {
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

        public Criteria andContractNoIsNull() {
            addCriterion("contract_no is null");
            return (Criteria) this;
        }

        public Criteria andContractNoIsNotNull() {
            addCriterion("contract_no is not null");
            return (Criteria) this;
        }

        public Criteria andContractNoEqualTo(String value) {
            addCriterion("contract_no =", value, "contractNo");
            return (Criteria) this;
        }

        public Criteria andContractNoNotEqualTo(String value) {
            addCriterion("contract_no <>", value, "contractNo");
            return (Criteria) this;
        }

        public Criteria andContractNoGreaterThan(String value) {
            addCriterion("contract_no >", value, "contractNo");
            return (Criteria) this;
        }

        public Criteria andContractNoGreaterThanOrEqualTo(String value) {
            addCriterion("contract_no >=", value, "contractNo");
            return (Criteria) this;
        }

        public Criteria andContractNoLessThan(String value) {
            addCriterion("contract_no <", value, "contractNo");
            return (Criteria) this;
        }

        public Criteria andContractNoLessThanOrEqualTo(String value) {
            addCriterion("contract_no <=", value, "contractNo");
            return (Criteria) this;
        }

        public Criteria andContractNoLike(String value) {
            addCriterion("contract_no like", value, "contractNo");
            return (Criteria) this;
        }

        public Criteria andContractNoNotLike(String value) {
            addCriterion("contract_no not like", value, "contractNo");
            return (Criteria) this;
        }

        public Criteria andContractNoIn(List<String> values) {
            addCriterion("contract_no in", values, "contractNo");
            return (Criteria) this;
        }

        public Criteria andContractNoNotIn(List<String> values) {
            addCriterion("contract_no not in", values, "contractNo");
            return (Criteria) this;
        }

        public Criteria andContractNoBetween(String value1, String value2) {
            addCriterion("contract_no between", value1, value2, "contractNo");
            return (Criteria) this;
        }

        public Criteria andContractNoNotBetween(String value1, String value2) {
            addCriterion("contract_no not between", value1, value2, "contractNo");
            return (Criteria) this;
        }

        public Criteria andReturnNumIsNull() {
            addCriterion("return_num is null");
            return (Criteria) this;
        }

        public Criteria andReturnNumIsNotNull() {
            addCriterion("return_num is not null");
            return (Criteria) this;
        }

        public Criteria andReturnNumEqualTo(Integer value) {
            addCriterion("return_num =", value, "returnNum");
            return (Criteria) this;
        }

        public Criteria andReturnNumNotEqualTo(Integer value) {
            addCriterion("return_num <>", value, "returnNum");
            return (Criteria) this;
        }

        public Criteria andReturnNumGreaterThan(Integer value) {
            addCriterion("return_num >", value, "returnNum");
            return (Criteria) this;
        }

        public Criteria andReturnNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("return_num >=", value, "returnNum");
            return (Criteria) this;
        }

        public Criteria andReturnNumLessThan(Integer value) {
            addCriterion("return_num <", value, "returnNum");
            return (Criteria) this;
        }

        public Criteria andReturnNumLessThanOrEqualTo(Integer value) {
            addCriterion("return_num <=", value, "returnNum");
            return (Criteria) this;
        }

        public Criteria andReturnNumIn(List<Integer> values) {
            addCriterion("return_num in", values, "returnNum");
            return (Criteria) this;
        }

        public Criteria andReturnNumNotIn(List<Integer> values) {
            addCriterion("return_num not in", values, "returnNum");
            return (Criteria) this;
        }

        public Criteria andReturnNumBetween(Integer value1, Integer value2) {
            addCriterion("return_num between", value1, value2, "returnNum");
            return (Criteria) this;
        }

        public Criteria andReturnNumNotBetween(Integer value1, Integer value2) {
            addCriterion("return_num not between", value1, value2, "returnNum");
            return (Criteria) this;
        }

        public Criteria andReturnDateIsNull() {
            addCriterion("return_date is null");
            return (Criteria) this;
        }

        public Criteria andReturnDateIsNotNull() {
            addCriterion("return_date is not null");
            return (Criteria) this;
        }

        public Criteria andReturnDateEqualTo(String value) {
            addCriterion("return_date =", value, "returnDate");
            return (Criteria) this;
        }

        public Criteria andReturnDateNotEqualTo(String value) {
            addCriterion("return_date <>", value, "returnDate");
            return (Criteria) this;
        }

        public Criteria andReturnDateGreaterThan(String value) {
            addCriterion("return_date >", value, "returnDate");
            return (Criteria) this;
        }

        public Criteria andReturnDateGreaterThanOrEqualTo(String value) {
            addCriterion("return_date >=", value, "returnDate");
            return (Criteria) this;
        }

        public Criteria andReturnDateLessThan(String value) {
            addCriterion("return_date <", value, "returnDate");
            return (Criteria) this;
        }

        public Criteria andReturnDateLessThanOrEqualTo(String value) {
            addCriterion("return_date <=", value, "returnDate");
            return (Criteria) this;
        }

        public Criteria andReturnDateLike(String value) {
            addCriterion("return_date like", value, "returnDate");
            return (Criteria) this;
        }

        public Criteria andReturnDateNotLike(String value) {
            addCriterion("return_date not like", value, "returnDate");
            return (Criteria) this;
        }

        public Criteria andReturnDateIn(List<String> values) {
            addCriterion("return_date in", values, "returnDate");
            return (Criteria) this;
        }

        public Criteria andReturnDateNotIn(List<String> values) {
            addCriterion("return_date not in", values, "returnDate");
            return (Criteria) this;
        }

        public Criteria andReturnDateBetween(String value1, String value2) {
            addCriterion("return_date between", value1, value2, "returnDate");
            return (Criteria) this;
        }

        public Criteria andReturnDateNotBetween(String value1, String value2) {
            addCriterion("return_date not between", value1, value2, "returnDate");
            return (Criteria) this;
        }

        public Criteria andReturnPrincipalIsNull() {
            addCriterion("return_principal is null");
            return (Criteria) this;
        }

        public Criteria andReturnPrincipalIsNotNull() {
            addCriterion("return_principal is not null");
            return (Criteria) this;
        }

        public Criteria andReturnPrincipalEqualTo(String value) {
            addCriterion("return_principal =", value, "returnPrincipal");
            return (Criteria) this;
        }

        public Criteria andReturnPrincipalNotEqualTo(String value) {
            addCriterion("return_principal <>", value, "returnPrincipal");
            return (Criteria) this;
        }

        public Criteria andReturnPrincipalGreaterThan(String value) {
            addCriterion("return_principal >", value, "returnPrincipal");
            return (Criteria) this;
        }

        public Criteria andReturnPrincipalGreaterThanOrEqualTo(String value) {
            addCriterion("return_principal >=", value, "returnPrincipal");
            return (Criteria) this;
        }

        public Criteria andReturnPrincipalLessThan(String value) {
            addCriterion("return_principal <", value, "returnPrincipal");
            return (Criteria) this;
        }

        public Criteria andReturnPrincipalLessThanOrEqualTo(String value) {
            addCriterion("return_principal <=", value, "returnPrincipal");
            return (Criteria) this;
        }

        public Criteria andReturnPrincipalLike(String value) {
            addCriterion("return_principal like", value, "returnPrincipal");
            return (Criteria) this;
        }

        public Criteria andReturnPrincipalNotLike(String value) {
            addCriterion("return_principal not like", value, "returnPrincipal");
            return (Criteria) this;
        }

        public Criteria andReturnPrincipalIn(List<String> values) {
            addCriterion("return_principal in", values, "returnPrincipal");
            return (Criteria) this;
        }

        public Criteria andReturnPrincipalNotIn(List<String> values) {
            addCriterion("return_principal not in", values, "returnPrincipal");
            return (Criteria) this;
        }

        public Criteria andReturnPrincipalBetween(String value1, String value2) {
            addCriterion("return_principal between", value1, value2, "returnPrincipal");
            return (Criteria) this;
        }

        public Criteria andReturnPrincipalNotBetween(String value1, String value2) {
            addCriterion("return_principal not between", value1, value2, "returnPrincipal");
            return (Criteria) this;
        }

        public Criteria andReturnInterestIsNull() {
            addCriterion("return_interest is null");
            return (Criteria) this;
        }

        public Criteria andReturnInterestIsNotNull() {
            addCriterion("return_interest is not null");
            return (Criteria) this;
        }

        public Criteria andReturnInterestEqualTo(String value) {
            addCriterion("return_interest =", value, "returnInterest");
            return (Criteria) this;
        }

        public Criteria andReturnInterestNotEqualTo(String value) {
            addCriterion("return_interest <>", value, "returnInterest");
            return (Criteria) this;
        }

        public Criteria andReturnInterestGreaterThan(String value) {
            addCriterion("return_interest >", value, "returnInterest");
            return (Criteria) this;
        }

        public Criteria andReturnInterestGreaterThanOrEqualTo(String value) {
            addCriterion("return_interest >=", value, "returnInterest");
            return (Criteria) this;
        }

        public Criteria andReturnInterestLessThan(String value) {
            addCriterion("return_interest <", value, "returnInterest");
            return (Criteria) this;
        }

        public Criteria andReturnInterestLessThanOrEqualTo(String value) {
            addCriterion("return_interest <=", value, "returnInterest");
            return (Criteria) this;
        }

        public Criteria andReturnInterestLike(String value) {
            addCriterion("return_interest like", value, "returnInterest");
            return (Criteria) this;
        }

        public Criteria andReturnInterestNotLike(String value) {
            addCriterion("return_interest not like", value, "returnInterest");
            return (Criteria) this;
        }

        public Criteria andReturnInterestIn(List<String> values) {
            addCriterion("return_interest in", values, "returnInterest");
            return (Criteria) this;
        }

        public Criteria andReturnInterestNotIn(List<String> values) {
            addCriterion("return_interest not in", values, "returnInterest");
            return (Criteria) this;
        }

        public Criteria andReturnInterestBetween(String value1, String value2) {
            addCriterion("return_interest between", value1, value2, "returnInterest");
            return (Criteria) this;
        }

        public Criteria andReturnInterestNotBetween(String value1, String value2) {
            addCriterion("return_interest not between", value1, value2, "returnInterest");
            return (Criteria) this;
        }

        public Criteria andReturnSourceIsNull() {
            addCriterion("return_source is null");
            return (Criteria) this;
        }

        public Criteria andReturnSourceIsNotNull() {
            addCriterion("return_source is not null");
            return (Criteria) this;
        }

        public Criteria andReturnSourceEqualTo(Integer value) {
            addCriterion("return_source =", value, "returnSource");
            return (Criteria) this;
        }

        public Criteria andReturnSourceNotEqualTo(Integer value) {
            addCriterion("return_source <>", value, "returnSource");
            return (Criteria) this;
        }

        public Criteria andReturnSourceGreaterThan(Integer value) {
            addCriterion("return_source >", value, "returnSource");
            return (Criteria) this;
        }

        public Criteria andReturnSourceGreaterThanOrEqualTo(Integer value) {
            addCriterion("return_source >=", value, "returnSource");
            return (Criteria) this;
        }

        public Criteria andReturnSourceLessThan(Integer value) {
            addCriterion("return_source <", value, "returnSource");
            return (Criteria) this;
        }

        public Criteria andReturnSourceLessThanOrEqualTo(Integer value) {
            addCriterion("return_source <=", value, "returnSource");
            return (Criteria) this;
        }

        public Criteria andReturnSourceIn(List<Integer> values) {
            addCriterion("return_source in", values, "returnSource");
            return (Criteria) this;
        }

        public Criteria andReturnSourceNotIn(List<Integer> values) {
            addCriterion("return_source not in", values, "returnSource");
            return (Criteria) this;
        }

        public Criteria andReturnSourceBetween(Integer value1, Integer value2) {
            addCriterion("return_source between", value1, value2, "returnSource");
            return (Criteria) this;
        }

        public Criteria andReturnSourceNotBetween(Integer value1, Integer value2) {
            addCriterion("return_source not between", value1, value2, "returnSource");
            return (Criteria) this;
        }

        public Criteria andReturnSituationIsNull() {
            addCriterion("return_situation is null");
            return (Criteria) this;
        }

        public Criteria andReturnSituationIsNotNull() {
            addCriterion("return_situation is not null");
            return (Criteria) this;
        }

        public Criteria andReturnSituationEqualTo(Integer value) {
            addCriterion("return_situation =", value, "returnSituation");
            return (Criteria) this;
        }

        public Criteria andReturnSituationNotEqualTo(Integer value) {
            addCriterion("return_situation <>", value, "returnSituation");
            return (Criteria) this;
        }

        public Criteria andReturnSituationGreaterThan(Integer value) {
            addCriterion("return_situation >", value, "returnSituation");
            return (Criteria) this;
        }

        public Criteria andReturnSituationGreaterThanOrEqualTo(Integer value) {
            addCriterion("return_situation >=", value, "returnSituation");
            return (Criteria) this;
        }

        public Criteria andReturnSituationLessThan(Integer value) {
            addCriterion("return_situation <", value, "returnSituation");
            return (Criteria) this;
        }

        public Criteria andReturnSituationLessThanOrEqualTo(Integer value) {
            addCriterion("return_situation <=", value, "returnSituation");
            return (Criteria) this;
        }

        public Criteria andReturnSituationIn(List<Integer> values) {
            addCriterion("return_situation in", values, "returnSituation");
            return (Criteria) this;
        }

        public Criteria andReturnSituationNotIn(List<Integer> values) {
            addCriterion("return_situation not in", values, "returnSituation");
            return (Criteria) this;
        }

        public Criteria andReturnSituationBetween(Integer value1, Integer value2) {
            addCriterion("return_situation between", value1, value2, "returnSituation");
            return (Criteria) this;
        }

        public Criteria andReturnSituationNotBetween(Integer value1, Integer value2) {
            addCriterion("return_situation not between", value1, value2, "returnSituation");
            return (Criteria) this;
        }

        public Criteria andReturnPrincipalRestIsNull() {
            addCriterion("return_principal_rest is null");
            return (Criteria) this;
        }

        public Criteria andReturnPrincipalRestIsNotNull() {
            addCriterion("return_principal_rest is not null");
            return (Criteria) this;
        }

        public Criteria andReturnPrincipalRestEqualTo(String value) {
            addCriterion("return_principal_rest =", value, "returnPrincipalRest");
            return (Criteria) this;
        }

        public Criteria andReturnPrincipalRestNotEqualTo(String value) {
            addCriterion("return_principal_rest <>", value, "returnPrincipalRest");
            return (Criteria) this;
        }

        public Criteria andReturnPrincipalRestGreaterThan(String value) {
            addCriterion("return_principal_rest >", value, "returnPrincipalRest");
            return (Criteria) this;
        }

        public Criteria andReturnPrincipalRestGreaterThanOrEqualTo(String value) {
            addCriterion("return_principal_rest >=", value, "returnPrincipalRest");
            return (Criteria) this;
        }

        public Criteria andReturnPrincipalRestLessThan(String value) {
            addCriterion("return_principal_rest <", value, "returnPrincipalRest");
            return (Criteria) this;
        }

        public Criteria andReturnPrincipalRestLessThanOrEqualTo(String value) {
            addCriterion("return_principal_rest <=", value, "returnPrincipalRest");
            return (Criteria) this;
        }

        public Criteria andReturnPrincipalRestLike(String value) {
            addCriterion("return_principal_rest like", value, "returnPrincipalRest");
            return (Criteria) this;
        }

        public Criteria andReturnPrincipalRestNotLike(String value) {
            addCriterion("return_principal_rest not like", value, "returnPrincipalRest");
            return (Criteria) this;
        }

        public Criteria andReturnPrincipalRestIn(List<String> values) {
            addCriterion("return_principal_rest in", values, "returnPrincipalRest");
            return (Criteria) this;
        }

        public Criteria andReturnPrincipalRestNotIn(List<String> values) {
            addCriterion("return_principal_rest not in", values, "returnPrincipalRest");
            return (Criteria) this;
        }

        public Criteria andReturnPrincipalRestBetween(String value1, String value2) {
            addCriterion("return_principal_rest between", value1, value2, "returnPrincipalRest");
            return (Criteria) this;
        }

        public Criteria andReturnPrincipalRestNotBetween(String value1, String value2) {
            addCriterion("return_principal_rest not between", value1, value2, "returnPrincipalRest");
            return (Criteria) this;
        }

        public Criteria andReturnInterestRestIsNull() {
            addCriterion("return_interest_rest is null");
            return (Criteria) this;
        }

        public Criteria andReturnInterestRestIsNotNull() {
            addCriterion("return_interest_rest is not null");
            return (Criteria) this;
        }

        public Criteria andReturnInterestRestEqualTo(String value) {
            addCriterion("return_interest_rest =", value, "returnInterestRest");
            return (Criteria) this;
        }

        public Criteria andReturnInterestRestNotEqualTo(String value) {
            addCriterion("return_interest_rest <>", value, "returnInterestRest");
            return (Criteria) this;
        }

        public Criteria andReturnInterestRestGreaterThan(String value) {
            addCriterion("return_interest_rest >", value, "returnInterestRest");
            return (Criteria) this;
        }

        public Criteria andReturnInterestRestGreaterThanOrEqualTo(String value) {
            addCriterion("return_interest_rest >=", value, "returnInterestRest");
            return (Criteria) this;
        }

        public Criteria andReturnInterestRestLessThan(String value) {
            addCriterion("return_interest_rest <", value, "returnInterestRest");
            return (Criteria) this;
        }

        public Criteria andReturnInterestRestLessThanOrEqualTo(String value) {
            addCriterion("return_interest_rest <=", value, "returnInterestRest");
            return (Criteria) this;
        }

        public Criteria andReturnInterestRestLike(String value) {
            addCriterion("return_interest_rest like", value, "returnInterestRest");
            return (Criteria) this;
        }

        public Criteria andReturnInterestRestNotLike(String value) {
            addCriterion("return_interest_rest not like", value, "returnInterestRest");
            return (Criteria) this;
        }

        public Criteria andReturnInterestRestIn(List<String> values) {
            addCriterion("return_interest_rest in", values, "returnInterestRest");
            return (Criteria) this;
        }

        public Criteria andReturnInterestRestNotIn(List<String> values) {
            addCriterion("return_interest_rest not in", values, "returnInterestRest");
            return (Criteria) this;
        }

        public Criteria andReturnInterestRestBetween(String value1, String value2) {
            addCriterion("return_interest_rest between", value1, value2, "returnInterestRest");
            return (Criteria) this;
        }

        public Criteria andReturnInterestRestNotBetween(String value1, String value2) {
            addCriterion("return_interest_rest not between", value1, value2, "returnInterestRest");
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