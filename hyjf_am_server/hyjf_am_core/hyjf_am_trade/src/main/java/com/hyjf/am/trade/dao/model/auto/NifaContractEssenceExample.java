package com.hyjf.am.trade.dao.model.auto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NifaContractEssenceExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public NifaContractEssenceExample() {
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

        public Criteria andPlatformNameIsNull() {
            addCriterion("platform_name is null");
            return (Criteria) this;
        }

        public Criteria andPlatformNameIsNotNull() {
            addCriterion("platform_name is not null");
            return (Criteria) this;
        }

        public Criteria andPlatformNameEqualTo(String value) {
            addCriterion("platform_name =", value, "platformName");
            return (Criteria) this;
        }

        public Criteria andPlatformNameNotEqualTo(String value) {
            addCriterion("platform_name <>", value, "platformName");
            return (Criteria) this;
        }

        public Criteria andPlatformNameGreaterThan(String value) {
            addCriterion("platform_name >", value, "platformName");
            return (Criteria) this;
        }

        public Criteria andPlatformNameGreaterThanOrEqualTo(String value) {
            addCriterion("platform_name >=", value, "platformName");
            return (Criteria) this;
        }

        public Criteria andPlatformNameLessThan(String value) {
            addCriterion("platform_name <", value, "platformName");
            return (Criteria) this;
        }

        public Criteria andPlatformNameLessThanOrEqualTo(String value) {
            addCriterion("platform_name <=", value, "platformName");
            return (Criteria) this;
        }

        public Criteria andPlatformNameLike(String value) {
            addCriterion("platform_name like", value, "platformName");
            return (Criteria) this;
        }

        public Criteria andPlatformNameNotLike(String value) {
            addCriterion("platform_name not like", value, "platformName");
            return (Criteria) this;
        }

        public Criteria andPlatformNameIn(List<String> values) {
            addCriterion("platform_name in", values, "platformName");
            return (Criteria) this;
        }

        public Criteria andPlatformNameNotIn(List<String> values) {
            addCriterion("platform_name not in", values, "platformName");
            return (Criteria) this;
        }

        public Criteria andPlatformNameBetween(String value1, String value2) {
            addCriterion("platform_name between", value1, value2, "platformName");
            return (Criteria) this;
        }

        public Criteria andPlatformNameNotBetween(String value1, String value2) {
            addCriterion("platform_name not between", value1, value2, "platformName");
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

        public Criteria andContractNameIsNull() {
            addCriterion("contract_name is null");
            return (Criteria) this;
        }

        public Criteria andContractNameIsNotNull() {
            addCriterion("contract_name is not null");
            return (Criteria) this;
        }

        public Criteria andContractNameEqualTo(String value) {
            addCriterion("contract_name =", value, "contractName");
            return (Criteria) this;
        }

        public Criteria andContractNameNotEqualTo(String value) {
            addCriterion("contract_name <>", value, "contractName");
            return (Criteria) this;
        }

        public Criteria andContractNameGreaterThan(String value) {
            addCriterion("contract_name >", value, "contractName");
            return (Criteria) this;
        }

        public Criteria andContractNameGreaterThanOrEqualTo(String value) {
            addCriterion("contract_name >=", value, "contractName");
            return (Criteria) this;
        }

        public Criteria andContractNameLessThan(String value) {
            addCriterion("contract_name <", value, "contractName");
            return (Criteria) this;
        }

        public Criteria andContractNameLessThanOrEqualTo(String value) {
            addCriterion("contract_name <=", value, "contractName");
            return (Criteria) this;
        }

        public Criteria andContractNameLike(String value) {
            addCriterion("contract_name like", value, "contractName");
            return (Criteria) this;
        }

        public Criteria andContractNameNotLike(String value) {
            addCriterion("contract_name not like", value, "contractName");
            return (Criteria) this;
        }

        public Criteria andContractNameIn(List<String> values) {
            addCriterion("contract_name in", values, "contractName");
            return (Criteria) this;
        }

        public Criteria andContractNameNotIn(List<String> values) {
            addCriterion("contract_name not in", values, "contractName");
            return (Criteria) this;
        }

        public Criteria andContractNameBetween(String value1, String value2) {
            addCriterion("contract_name between", value1, value2, "contractName");
            return (Criteria) this;
        }

        public Criteria andContractNameNotBetween(String value1, String value2) {
            addCriterion("contract_name not between", value1, value2, "contractName");
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

        public Criteria andContractSignerIsNull() {
            addCriterion("contract_signer is null");
            return (Criteria) this;
        }

        public Criteria andContractSignerIsNotNull() {
            addCriterion("contract_signer is not null");
            return (Criteria) this;
        }

        public Criteria andContractSignerEqualTo(String value) {
            addCriterion("contract_signer =", value, "contractSigner");
            return (Criteria) this;
        }

        public Criteria andContractSignerNotEqualTo(String value) {
            addCriterion("contract_signer <>", value, "contractSigner");
            return (Criteria) this;
        }

        public Criteria andContractSignerGreaterThan(String value) {
            addCriterion("contract_signer >", value, "contractSigner");
            return (Criteria) this;
        }

        public Criteria andContractSignerGreaterThanOrEqualTo(String value) {
            addCriterion("contract_signer >=", value, "contractSigner");
            return (Criteria) this;
        }

        public Criteria andContractSignerLessThan(String value) {
            addCriterion("contract_signer <", value, "contractSigner");
            return (Criteria) this;
        }

        public Criteria andContractSignerLessThanOrEqualTo(String value) {
            addCriterion("contract_signer <=", value, "contractSigner");
            return (Criteria) this;
        }

        public Criteria andContractSignerLike(String value) {
            addCriterion("contract_signer like", value, "contractSigner");
            return (Criteria) this;
        }

        public Criteria andContractSignerNotLike(String value) {
            addCriterion("contract_signer not like", value, "contractSigner");
            return (Criteria) this;
        }

        public Criteria andContractSignerIn(List<String> values) {
            addCriterion("contract_signer in", values, "contractSigner");
            return (Criteria) this;
        }

        public Criteria andContractSignerNotIn(List<String> values) {
            addCriterion("contract_signer not in", values, "contractSigner");
            return (Criteria) this;
        }

        public Criteria andContractSignerBetween(String value1, String value2) {
            addCriterion("contract_signer between", value1, value2, "contractSigner");
            return (Criteria) this;
        }

        public Criteria andContractSignerNotBetween(String value1, String value2) {
            addCriterion("contract_signer not between", value1, value2, "contractSigner");
            return (Criteria) this;
        }

        public Criteria andContractTimeIsNull() {
            addCriterion("contract_time is null");
            return (Criteria) this;
        }

        public Criteria andContractTimeIsNotNull() {
            addCriterion("contract_time is not null");
            return (Criteria) this;
        }

        public Criteria andContractTimeEqualTo(String value) {
            addCriterion("contract_time =", value, "contractTime");
            return (Criteria) this;
        }

        public Criteria andContractTimeNotEqualTo(String value) {
            addCriterion("contract_time <>", value, "contractTime");
            return (Criteria) this;
        }

        public Criteria andContractTimeGreaterThan(String value) {
            addCriterion("contract_time >", value, "contractTime");
            return (Criteria) this;
        }

        public Criteria andContractTimeGreaterThanOrEqualTo(String value) {
            addCriterion("contract_time >=", value, "contractTime");
            return (Criteria) this;
        }

        public Criteria andContractTimeLessThan(String value) {
            addCriterion("contract_time <", value, "contractTime");
            return (Criteria) this;
        }

        public Criteria andContractTimeLessThanOrEqualTo(String value) {
            addCriterion("contract_time <=", value, "contractTime");
            return (Criteria) this;
        }

        public Criteria andContractTimeLike(String value) {
            addCriterion("contract_time like", value, "contractTime");
            return (Criteria) this;
        }

        public Criteria andContractTimeNotLike(String value) {
            addCriterion("contract_time not like", value, "contractTime");
            return (Criteria) this;
        }

        public Criteria andContractTimeIn(List<String> values) {
            addCriterion("contract_time in", values, "contractTime");
            return (Criteria) this;
        }

        public Criteria andContractTimeNotIn(List<String> values) {
            addCriterion("contract_time not in", values, "contractTime");
            return (Criteria) this;
        }

        public Criteria andContractTimeBetween(String value1, String value2) {
            addCriterion("contract_time between", value1, value2, "contractTime");
            return (Criteria) this;
        }

        public Criteria andContractTimeNotBetween(String value1, String value2) {
            addCriterion("contract_time not between", value1, value2, "contractTime");
            return (Criteria) this;
        }

        public Criteria andBorrowerTypeIsNull() {
            addCriterion("borrower_type is null");
            return (Criteria) this;
        }

        public Criteria andBorrowerTypeIsNotNull() {
            addCriterion("borrower_type is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowerTypeEqualTo(Integer value) {
            addCriterion("borrower_type =", value, "borrowerType");
            return (Criteria) this;
        }

        public Criteria andBorrowerTypeNotEqualTo(Integer value) {
            addCriterion("borrower_type <>", value, "borrowerType");
            return (Criteria) this;
        }

        public Criteria andBorrowerTypeGreaterThan(Integer value) {
            addCriterion("borrower_type >", value, "borrowerType");
            return (Criteria) this;
        }

        public Criteria andBorrowerTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("borrower_type >=", value, "borrowerType");
            return (Criteria) this;
        }

        public Criteria andBorrowerTypeLessThan(Integer value) {
            addCriterion("borrower_type <", value, "borrowerType");
            return (Criteria) this;
        }

        public Criteria andBorrowerTypeLessThanOrEqualTo(Integer value) {
            addCriterion("borrower_type <=", value, "borrowerType");
            return (Criteria) this;
        }

        public Criteria andBorrowerTypeIn(List<Integer> values) {
            addCriterion("borrower_type in", values, "borrowerType");
            return (Criteria) this;
        }

        public Criteria andBorrowerTypeNotIn(List<Integer> values) {
            addCriterion("borrower_type not in", values, "borrowerType");
            return (Criteria) this;
        }

        public Criteria andBorrowerTypeBetween(Integer value1, Integer value2) {
            addCriterion("borrower_type between", value1, value2, "borrowerType");
            return (Criteria) this;
        }

        public Criteria andBorrowerTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("borrower_type not between", value1, value2, "borrowerType");
            return (Criteria) this;
        }

        public Criteria andBorrowerCertTypeIsNull() {
            addCriterion("borrower_cert_type is null");
            return (Criteria) this;
        }

        public Criteria andBorrowerCertTypeIsNotNull() {
            addCriterion("borrower_cert_type is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowerCertTypeEqualTo(String value) {
            addCriterion("borrower_cert_type =", value, "borrowerCertType");
            return (Criteria) this;
        }

        public Criteria andBorrowerCertTypeNotEqualTo(String value) {
            addCriterion("borrower_cert_type <>", value, "borrowerCertType");
            return (Criteria) this;
        }

        public Criteria andBorrowerCertTypeGreaterThan(String value) {
            addCriterion("borrower_cert_type >", value, "borrowerCertType");
            return (Criteria) this;
        }

        public Criteria andBorrowerCertTypeGreaterThanOrEqualTo(String value) {
            addCriterion("borrower_cert_type >=", value, "borrowerCertType");
            return (Criteria) this;
        }

        public Criteria andBorrowerCertTypeLessThan(String value) {
            addCriterion("borrower_cert_type <", value, "borrowerCertType");
            return (Criteria) this;
        }

        public Criteria andBorrowerCertTypeLessThanOrEqualTo(String value) {
            addCriterion("borrower_cert_type <=", value, "borrowerCertType");
            return (Criteria) this;
        }

        public Criteria andBorrowerCertTypeLike(String value) {
            addCriterion("borrower_cert_type like", value, "borrowerCertType");
            return (Criteria) this;
        }

        public Criteria andBorrowerCertTypeNotLike(String value) {
            addCriterion("borrower_cert_type not like", value, "borrowerCertType");
            return (Criteria) this;
        }

        public Criteria andBorrowerCertTypeIn(List<String> values) {
            addCriterion("borrower_cert_type in", values, "borrowerCertType");
            return (Criteria) this;
        }

        public Criteria andBorrowerCertTypeNotIn(List<String> values) {
            addCriterion("borrower_cert_type not in", values, "borrowerCertType");
            return (Criteria) this;
        }

        public Criteria andBorrowerCertTypeBetween(String value1, String value2) {
            addCriterion("borrower_cert_type between", value1, value2, "borrowerCertType");
            return (Criteria) this;
        }

        public Criteria andBorrowerCertTypeNotBetween(String value1, String value2) {
            addCriterion("borrower_cert_type not between", value1, value2, "borrowerCertType");
            return (Criteria) this;
        }

        public Criteria andBorrowerCertNoIsNull() {
            addCriterion("borrower_cert_no is null");
            return (Criteria) this;
        }

        public Criteria andBorrowerCertNoIsNotNull() {
            addCriterion("borrower_cert_no is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowerCertNoEqualTo(String value) {
            addCriterion("borrower_cert_no =", value, "borrowerCertNo");
            return (Criteria) this;
        }

        public Criteria andBorrowerCertNoNotEqualTo(String value) {
            addCriterion("borrower_cert_no <>", value, "borrowerCertNo");
            return (Criteria) this;
        }

        public Criteria andBorrowerCertNoGreaterThan(String value) {
            addCriterion("borrower_cert_no >", value, "borrowerCertNo");
            return (Criteria) this;
        }

        public Criteria andBorrowerCertNoGreaterThanOrEqualTo(String value) {
            addCriterion("borrower_cert_no >=", value, "borrowerCertNo");
            return (Criteria) this;
        }

        public Criteria andBorrowerCertNoLessThan(String value) {
            addCriterion("borrower_cert_no <", value, "borrowerCertNo");
            return (Criteria) this;
        }

        public Criteria andBorrowerCertNoLessThanOrEqualTo(String value) {
            addCriterion("borrower_cert_no <=", value, "borrowerCertNo");
            return (Criteria) this;
        }

        public Criteria andBorrowerCertNoLike(String value) {
            addCriterion("borrower_cert_no like", value, "borrowerCertNo");
            return (Criteria) this;
        }

        public Criteria andBorrowerCertNoNotLike(String value) {
            addCriterion("borrower_cert_no not like", value, "borrowerCertNo");
            return (Criteria) this;
        }

        public Criteria andBorrowerCertNoIn(List<String> values) {
            addCriterion("borrower_cert_no in", values, "borrowerCertNo");
            return (Criteria) this;
        }

        public Criteria andBorrowerCertNoNotIn(List<String> values) {
            addCriterion("borrower_cert_no not in", values, "borrowerCertNo");
            return (Criteria) this;
        }

        public Criteria andBorrowerCertNoBetween(String value1, String value2) {
            addCriterion("borrower_cert_no between", value1, value2, "borrowerCertNo");
            return (Criteria) this;
        }

        public Criteria andBorrowerCertNoNotBetween(String value1, String value2) {
            addCriterion("borrower_cert_no not between", value1, value2, "borrowerCertNo");
            return (Criteria) this;
        }

        public Criteria andBorrowerNameIsNull() {
            addCriterion("borrower_name is null");
            return (Criteria) this;
        }

        public Criteria andBorrowerNameIsNotNull() {
            addCriterion("borrower_name is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowerNameEqualTo(String value) {
            addCriterion("borrower_name =", value, "borrowerName");
            return (Criteria) this;
        }

        public Criteria andBorrowerNameNotEqualTo(String value) {
            addCriterion("borrower_name <>", value, "borrowerName");
            return (Criteria) this;
        }

        public Criteria andBorrowerNameGreaterThan(String value) {
            addCriterion("borrower_name >", value, "borrowerName");
            return (Criteria) this;
        }

        public Criteria andBorrowerNameGreaterThanOrEqualTo(String value) {
            addCriterion("borrower_name >=", value, "borrowerName");
            return (Criteria) this;
        }

        public Criteria andBorrowerNameLessThan(String value) {
            addCriterion("borrower_name <", value, "borrowerName");
            return (Criteria) this;
        }

        public Criteria andBorrowerNameLessThanOrEqualTo(String value) {
            addCriterion("borrower_name <=", value, "borrowerName");
            return (Criteria) this;
        }

        public Criteria andBorrowerNameLike(String value) {
            addCriterion("borrower_name like", value, "borrowerName");
            return (Criteria) this;
        }

        public Criteria andBorrowerNameNotLike(String value) {
            addCriterion("borrower_name not like", value, "borrowerName");
            return (Criteria) this;
        }

        public Criteria andBorrowerNameIn(List<String> values) {
            addCriterion("borrower_name in", values, "borrowerName");
            return (Criteria) this;
        }

        public Criteria andBorrowerNameNotIn(List<String> values) {
            addCriterion("borrower_name not in", values, "borrowerName");
            return (Criteria) this;
        }

        public Criteria andBorrowerNameBetween(String value1, String value2) {
            addCriterion("borrower_name between", value1, value2, "borrowerName");
            return (Criteria) this;
        }

        public Criteria andBorrowerNameNotBetween(String value1, String value2) {
            addCriterion("borrower_name not between", value1, value2, "borrowerName");
            return (Criteria) this;
        }

        public Criteria andBorrowerAddressIsNull() {
            addCriterion("borrower_address is null");
            return (Criteria) this;
        }

        public Criteria andBorrowerAddressIsNotNull() {
            addCriterion("borrower_address is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowerAddressEqualTo(String value) {
            addCriterion("borrower_address =", value, "borrowerAddress");
            return (Criteria) this;
        }

        public Criteria andBorrowerAddressNotEqualTo(String value) {
            addCriterion("borrower_address <>", value, "borrowerAddress");
            return (Criteria) this;
        }

        public Criteria andBorrowerAddressGreaterThan(String value) {
            addCriterion("borrower_address >", value, "borrowerAddress");
            return (Criteria) this;
        }

        public Criteria andBorrowerAddressGreaterThanOrEqualTo(String value) {
            addCriterion("borrower_address >=", value, "borrowerAddress");
            return (Criteria) this;
        }

        public Criteria andBorrowerAddressLessThan(String value) {
            addCriterion("borrower_address <", value, "borrowerAddress");
            return (Criteria) this;
        }

        public Criteria andBorrowerAddressLessThanOrEqualTo(String value) {
            addCriterion("borrower_address <=", value, "borrowerAddress");
            return (Criteria) this;
        }

        public Criteria andBorrowerAddressLike(String value) {
            addCriterion("borrower_address like", value, "borrowerAddress");
            return (Criteria) this;
        }

        public Criteria andBorrowerAddressNotLike(String value) {
            addCriterion("borrower_address not like", value, "borrowerAddress");
            return (Criteria) this;
        }

        public Criteria andBorrowerAddressIn(List<String> values) {
            addCriterion("borrower_address in", values, "borrowerAddress");
            return (Criteria) this;
        }

        public Criteria andBorrowerAddressNotIn(List<String> values) {
            addCriterion("borrower_address not in", values, "borrowerAddress");
            return (Criteria) this;
        }

        public Criteria andBorrowerAddressBetween(String value1, String value2) {
            addCriterion("borrower_address between", value1, value2, "borrowerAddress");
            return (Criteria) this;
        }

        public Criteria andBorrowerAddressNotBetween(String value1, String value2) {
            addCriterion("borrower_address not between", value1, value2, "borrowerAddress");
            return (Criteria) this;
        }

        public Criteria andBorrowerNacaoNoIsNull() {
            addCriterion("borrower_nacao_no is null");
            return (Criteria) this;
        }

        public Criteria andBorrowerNacaoNoIsNotNull() {
            addCriterion("borrower_nacao_no is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowerNacaoNoEqualTo(String value) {
            addCriterion("borrower_nacao_no =", value, "borrowerNacaoNo");
            return (Criteria) this;
        }

        public Criteria andBorrowerNacaoNoNotEqualTo(String value) {
            addCriterion("borrower_nacao_no <>", value, "borrowerNacaoNo");
            return (Criteria) this;
        }

        public Criteria andBorrowerNacaoNoGreaterThan(String value) {
            addCriterion("borrower_nacao_no >", value, "borrowerNacaoNo");
            return (Criteria) this;
        }

        public Criteria andBorrowerNacaoNoGreaterThanOrEqualTo(String value) {
            addCriterion("borrower_nacao_no >=", value, "borrowerNacaoNo");
            return (Criteria) this;
        }

        public Criteria andBorrowerNacaoNoLessThan(String value) {
            addCriterion("borrower_nacao_no <", value, "borrowerNacaoNo");
            return (Criteria) this;
        }

        public Criteria andBorrowerNacaoNoLessThanOrEqualTo(String value) {
            addCriterion("borrower_nacao_no <=", value, "borrowerNacaoNo");
            return (Criteria) this;
        }

        public Criteria andBorrowerNacaoNoLike(String value) {
            addCriterion("borrower_nacao_no like", value, "borrowerNacaoNo");
            return (Criteria) this;
        }

        public Criteria andBorrowerNacaoNoNotLike(String value) {
            addCriterion("borrower_nacao_no not like", value, "borrowerNacaoNo");
            return (Criteria) this;
        }

        public Criteria andBorrowerNacaoNoIn(List<String> values) {
            addCriterion("borrower_nacao_no in", values, "borrowerNacaoNo");
            return (Criteria) this;
        }

        public Criteria andBorrowerNacaoNoNotIn(List<String> values) {
            addCriterion("borrower_nacao_no not in", values, "borrowerNacaoNo");
            return (Criteria) this;
        }

        public Criteria andBorrowerNacaoNoBetween(String value1, String value2) {
            addCriterion("borrower_nacao_no between", value1, value2, "borrowerNacaoNo");
            return (Criteria) this;
        }

        public Criteria andBorrowerNacaoNoNotBetween(String value1, String value2) {
            addCriterion("borrower_nacao_no not between", value1, value2, "borrowerNacaoNo");
            return (Criteria) this;
        }

        public Criteria andBorrowerOrgcodeNoIsNull() {
            addCriterion("borrower_orgcode_no is null");
            return (Criteria) this;
        }

        public Criteria andBorrowerOrgcodeNoIsNotNull() {
            addCriterion("borrower_orgcode_no is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowerOrgcodeNoEqualTo(String value) {
            addCriterion("borrower_orgcode_no =", value, "borrowerOrgcodeNo");
            return (Criteria) this;
        }

        public Criteria andBorrowerOrgcodeNoNotEqualTo(String value) {
            addCriterion("borrower_orgcode_no <>", value, "borrowerOrgcodeNo");
            return (Criteria) this;
        }

        public Criteria andBorrowerOrgcodeNoGreaterThan(String value) {
            addCriterion("borrower_orgcode_no >", value, "borrowerOrgcodeNo");
            return (Criteria) this;
        }

        public Criteria andBorrowerOrgcodeNoGreaterThanOrEqualTo(String value) {
            addCriterion("borrower_orgcode_no >=", value, "borrowerOrgcodeNo");
            return (Criteria) this;
        }

        public Criteria andBorrowerOrgcodeNoLessThan(String value) {
            addCriterion("borrower_orgcode_no <", value, "borrowerOrgcodeNo");
            return (Criteria) this;
        }

        public Criteria andBorrowerOrgcodeNoLessThanOrEqualTo(String value) {
            addCriterion("borrower_orgcode_no <=", value, "borrowerOrgcodeNo");
            return (Criteria) this;
        }

        public Criteria andBorrowerOrgcodeNoLike(String value) {
            addCriterion("borrower_orgcode_no like", value, "borrowerOrgcodeNo");
            return (Criteria) this;
        }

        public Criteria andBorrowerOrgcodeNoNotLike(String value) {
            addCriterion("borrower_orgcode_no not like", value, "borrowerOrgcodeNo");
            return (Criteria) this;
        }

        public Criteria andBorrowerOrgcodeNoIn(List<String> values) {
            addCriterion("borrower_orgcode_no in", values, "borrowerOrgcodeNo");
            return (Criteria) this;
        }

        public Criteria andBorrowerOrgcodeNoNotIn(List<String> values) {
            addCriterion("borrower_orgcode_no not in", values, "borrowerOrgcodeNo");
            return (Criteria) this;
        }

        public Criteria andBorrowerOrgcodeNoBetween(String value1, String value2) {
            addCriterion("borrower_orgcode_no between", value1, value2, "borrowerOrgcodeNo");
            return (Criteria) this;
        }

        public Criteria andBorrowerOrgcodeNoNotBetween(String value1, String value2) {
            addCriterion("borrower_orgcode_no not between", value1, value2, "borrowerOrgcodeNo");
            return (Criteria) this;
        }

        public Criteria andBorrowerCompanyIsNull() {
            addCriterion("borrower_company is null");
            return (Criteria) this;
        }

        public Criteria andBorrowerCompanyIsNotNull() {
            addCriterion("borrower_company is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowerCompanyEqualTo(String value) {
            addCriterion("borrower_company =", value, "borrowerCompany");
            return (Criteria) this;
        }

        public Criteria andBorrowerCompanyNotEqualTo(String value) {
            addCriterion("borrower_company <>", value, "borrowerCompany");
            return (Criteria) this;
        }

        public Criteria andBorrowerCompanyGreaterThan(String value) {
            addCriterion("borrower_company >", value, "borrowerCompany");
            return (Criteria) this;
        }

        public Criteria andBorrowerCompanyGreaterThanOrEqualTo(String value) {
            addCriterion("borrower_company >=", value, "borrowerCompany");
            return (Criteria) this;
        }

        public Criteria andBorrowerCompanyLessThan(String value) {
            addCriterion("borrower_company <", value, "borrowerCompany");
            return (Criteria) this;
        }

        public Criteria andBorrowerCompanyLessThanOrEqualTo(String value) {
            addCriterion("borrower_company <=", value, "borrowerCompany");
            return (Criteria) this;
        }

        public Criteria andBorrowerCompanyLike(String value) {
            addCriterion("borrower_company like", value, "borrowerCompany");
            return (Criteria) this;
        }

        public Criteria andBorrowerCompanyNotLike(String value) {
            addCriterion("borrower_company not like", value, "borrowerCompany");
            return (Criteria) this;
        }

        public Criteria andBorrowerCompanyIn(List<String> values) {
            addCriterion("borrower_company in", values, "borrowerCompany");
            return (Criteria) this;
        }

        public Criteria andBorrowerCompanyNotIn(List<String> values) {
            addCriterion("borrower_company not in", values, "borrowerCompany");
            return (Criteria) this;
        }

        public Criteria andBorrowerCompanyBetween(String value1, String value2) {
            addCriterion("borrower_company between", value1, value2, "borrowerCompany");
            return (Criteria) this;
        }

        public Criteria andBorrowerCompanyNotBetween(String value1, String value2) {
            addCriterion("borrower_company not between", value1, value2, "borrowerCompany");
            return (Criteria) this;
        }

        public Criteria andInvestorTypeIsNull() {
            addCriterion("investor_type is null");
            return (Criteria) this;
        }

        public Criteria andInvestorTypeIsNotNull() {
            addCriterion("investor_type is not null");
            return (Criteria) this;
        }

        public Criteria andInvestorTypeEqualTo(Integer value) {
            addCriterion("investor_type =", value, "investorType");
            return (Criteria) this;
        }

        public Criteria andInvestorTypeNotEqualTo(Integer value) {
            addCriterion("investor_type <>", value, "investorType");
            return (Criteria) this;
        }

        public Criteria andInvestorTypeGreaterThan(Integer value) {
            addCriterion("investor_type >", value, "investorType");
            return (Criteria) this;
        }

        public Criteria andInvestorTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("investor_type >=", value, "investorType");
            return (Criteria) this;
        }

        public Criteria andInvestorTypeLessThan(Integer value) {
            addCriterion("investor_type <", value, "investorType");
            return (Criteria) this;
        }

        public Criteria andInvestorTypeLessThanOrEqualTo(Integer value) {
            addCriterion("investor_type <=", value, "investorType");
            return (Criteria) this;
        }

        public Criteria andInvestorTypeIn(List<Integer> values) {
            addCriterion("investor_type in", values, "investorType");
            return (Criteria) this;
        }

        public Criteria andInvestorTypeNotIn(List<Integer> values) {
            addCriterion("investor_type not in", values, "investorType");
            return (Criteria) this;
        }

        public Criteria andInvestorTypeBetween(Integer value1, Integer value2) {
            addCriterion("investor_type between", value1, value2, "investorType");
            return (Criteria) this;
        }

        public Criteria andInvestorTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("investor_type not between", value1, value2, "investorType");
            return (Criteria) this;
        }

        public Criteria andInvestorCertTypeIsNull() {
            addCriterion("investor_cert_type is null");
            return (Criteria) this;
        }

        public Criteria andInvestorCertTypeIsNotNull() {
            addCriterion("investor_cert_type is not null");
            return (Criteria) this;
        }

        public Criteria andInvestorCertTypeEqualTo(String value) {
            addCriterion("investor_cert_type =", value, "investorCertType");
            return (Criteria) this;
        }

        public Criteria andInvestorCertTypeNotEqualTo(String value) {
            addCriterion("investor_cert_type <>", value, "investorCertType");
            return (Criteria) this;
        }

        public Criteria andInvestorCertTypeGreaterThan(String value) {
            addCriterion("investor_cert_type >", value, "investorCertType");
            return (Criteria) this;
        }

        public Criteria andInvestorCertTypeGreaterThanOrEqualTo(String value) {
            addCriterion("investor_cert_type >=", value, "investorCertType");
            return (Criteria) this;
        }

        public Criteria andInvestorCertTypeLessThan(String value) {
            addCriterion("investor_cert_type <", value, "investorCertType");
            return (Criteria) this;
        }

        public Criteria andInvestorCertTypeLessThanOrEqualTo(String value) {
            addCriterion("investor_cert_type <=", value, "investorCertType");
            return (Criteria) this;
        }

        public Criteria andInvestorCertTypeLike(String value) {
            addCriterion("investor_cert_type like", value, "investorCertType");
            return (Criteria) this;
        }

        public Criteria andInvestorCertTypeNotLike(String value) {
            addCriterion("investor_cert_type not like", value, "investorCertType");
            return (Criteria) this;
        }

        public Criteria andInvestorCertTypeIn(List<String> values) {
            addCriterion("investor_cert_type in", values, "investorCertType");
            return (Criteria) this;
        }

        public Criteria andInvestorCertTypeNotIn(List<String> values) {
            addCriterion("investor_cert_type not in", values, "investorCertType");
            return (Criteria) this;
        }

        public Criteria andInvestorCertTypeBetween(String value1, String value2) {
            addCriterion("investor_cert_type between", value1, value2, "investorCertType");
            return (Criteria) this;
        }

        public Criteria andInvestorCertTypeNotBetween(String value1, String value2) {
            addCriterion("investor_cert_type not between", value1, value2, "investorCertType");
            return (Criteria) this;
        }

        public Criteria andInvestorCertNoIsNull() {
            addCriterion("investor_cert_no is null");
            return (Criteria) this;
        }

        public Criteria andInvestorCertNoIsNotNull() {
            addCriterion("investor_cert_no is not null");
            return (Criteria) this;
        }

        public Criteria andInvestorCertNoEqualTo(String value) {
            addCriterion("investor_cert_no =", value, "investorCertNo");
            return (Criteria) this;
        }

        public Criteria andInvestorCertNoNotEqualTo(String value) {
            addCriterion("investor_cert_no <>", value, "investorCertNo");
            return (Criteria) this;
        }

        public Criteria andInvestorCertNoGreaterThan(String value) {
            addCriterion("investor_cert_no >", value, "investorCertNo");
            return (Criteria) this;
        }

        public Criteria andInvestorCertNoGreaterThanOrEqualTo(String value) {
            addCriterion("investor_cert_no >=", value, "investorCertNo");
            return (Criteria) this;
        }

        public Criteria andInvestorCertNoLessThan(String value) {
            addCriterion("investor_cert_no <", value, "investorCertNo");
            return (Criteria) this;
        }

        public Criteria andInvestorCertNoLessThanOrEqualTo(String value) {
            addCriterion("investor_cert_no <=", value, "investorCertNo");
            return (Criteria) this;
        }

        public Criteria andInvestorCertNoLike(String value) {
            addCriterion("investor_cert_no like", value, "investorCertNo");
            return (Criteria) this;
        }

        public Criteria andInvestorCertNoNotLike(String value) {
            addCriterion("investor_cert_no not like", value, "investorCertNo");
            return (Criteria) this;
        }

        public Criteria andInvestorCertNoIn(List<String> values) {
            addCriterion("investor_cert_no in", values, "investorCertNo");
            return (Criteria) this;
        }

        public Criteria andInvestorCertNoNotIn(List<String> values) {
            addCriterion("investor_cert_no not in", values, "investorCertNo");
            return (Criteria) this;
        }

        public Criteria andInvestorCertNoBetween(String value1, String value2) {
            addCriterion("investor_cert_no between", value1, value2, "investorCertNo");
            return (Criteria) this;
        }

        public Criteria andInvestorCertNoNotBetween(String value1, String value2) {
            addCriterion("investor_cert_no not between", value1, value2, "investorCertNo");
            return (Criteria) this;
        }

        public Criteria andInvestorNameIsNull() {
            addCriterion("investor_name is null");
            return (Criteria) this;
        }

        public Criteria andInvestorNameIsNotNull() {
            addCriterion("investor_name is not null");
            return (Criteria) this;
        }

        public Criteria andInvestorNameEqualTo(String value) {
            addCriterion("investor_name =", value, "investorName");
            return (Criteria) this;
        }

        public Criteria andInvestorNameNotEqualTo(String value) {
            addCriterion("investor_name <>", value, "investorName");
            return (Criteria) this;
        }

        public Criteria andInvestorNameGreaterThan(String value) {
            addCriterion("investor_name >", value, "investorName");
            return (Criteria) this;
        }

        public Criteria andInvestorNameGreaterThanOrEqualTo(String value) {
            addCriterion("investor_name >=", value, "investorName");
            return (Criteria) this;
        }

        public Criteria andInvestorNameLessThan(String value) {
            addCriterion("investor_name <", value, "investorName");
            return (Criteria) this;
        }

        public Criteria andInvestorNameLessThanOrEqualTo(String value) {
            addCriterion("investor_name <=", value, "investorName");
            return (Criteria) this;
        }

        public Criteria andInvestorNameLike(String value) {
            addCriterion("investor_name like", value, "investorName");
            return (Criteria) this;
        }

        public Criteria andInvestorNameNotLike(String value) {
            addCriterion("investor_name not like", value, "investorName");
            return (Criteria) this;
        }

        public Criteria andInvestorNameIn(List<String> values) {
            addCriterion("investor_name in", values, "investorName");
            return (Criteria) this;
        }

        public Criteria andInvestorNameNotIn(List<String> values) {
            addCriterion("investor_name not in", values, "investorName");
            return (Criteria) this;
        }

        public Criteria andInvestorNameBetween(String value1, String value2) {
            addCriterion("investor_name between", value1, value2, "investorName");
            return (Criteria) this;
        }

        public Criteria andInvestorNameNotBetween(String value1, String value2) {
            addCriterion("investor_name not between", value1, value2, "investorName");
            return (Criteria) this;
        }

        public Criteria andInvestorNacaoNoIsNull() {
            addCriterion("investor_nacao_no is null");
            return (Criteria) this;
        }

        public Criteria andInvestorNacaoNoIsNotNull() {
            addCriterion("investor_nacao_no is not null");
            return (Criteria) this;
        }

        public Criteria andInvestorNacaoNoEqualTo(String value) {
            addCriterion("investor_nacao_no =", value, "investorNacaoNo");
            return (Criteria) this;
        }

        public Criteria andInvestorNacaoNoNotEqualTo(String value) {
            addCriterion("investor_nacao_no <>", value, "investorNacaoNo");
            return (Criteria) this;
        }

        public Criteria andInvestorNacaoNoGreaterThan(String value) {
            addCriterion("investor_nacao_no >", value, "investorNacaoNo");
            return (Criteria) this;
        }

        public Criteria andInvestorNacaoNoGreaterThanOrEqualTo(String value) {
            addCriterion("investor_nacao_no >=", value, "investorNacaoNo");
            return (Criteria) this;
        }

        public Criteria andInvestorNacaoNoLessThan(String value) {
            addCriterion("investor_nacao_no <", value, "investorNacaoNo");
            return (Criteria) this;
        }

        public Criteria andInvestorNacaoNoLessThanOrEqualTo(String value) {
            addCriterion("investor_nacao_no <=", value, "investorNacaoNo");
            return (Criteria) this;
        }

        public Criteria andInvestorNacaoNoLike(String value) {
            addCriterion("investor_nacao_no like", value, "investorNacaoNo");
            return (Criteria) this;
        }

        public Criteria andInvestorNacaoNoNotLike(String value) {
            addCriterion("investor_nacao_no not like", value, "investorNacaoNo");
            return (Criteria) this;
        }

        public Criteria andInvestorNacaoNoIn(List<String> values) {
            addCriterion("investor_nacao_no in", values, "investorNacaoNo");
            return (Criteria) this;
        }

        public Criteria andInvestorNacaoNoNotIn(List<String> values) {
            addCriterion("investor_nacao_no not in", values, "investorNacaoNo");
            return (Criteria) this;
        }

        public Criteria andInvestorNacaoNoBetween(String value1, String value2) {
            addCriterion("investor_nacao_no between", value1, value2, "investorNacaoNo");
            return (Criteria) this;
        }

        public Criteria andInvestorNacaoNoNotBetween(String value1, String value2) {
            addCriterion("investor_nacao_no not between", value1, value2, "investorNacaoNo");
            return (Criteria) this;
        }

        public Criteria andInvestorOrgcodeNoIsNull() {
            addCriterion("investor_orgcode_no is null");
            return (Criteria) this;
        }

        public Criteria andInvestorOrgcodeNoIsNotNull() {
            addCriterion("investor_orgcode_no is not null");
            return (Criteria) this;
        }

        public Criteria andInvestorOrgcodeNoEqualTo(String value) {
            addCriterion("investor_orgcode_no =", value, "investorOrgcodeNo");
            return (Criteria) this;
        }

        public Criteria andInvestorOrgcodeNoNotEqualTo(String value) {
            addCriterion("investor_orgcode_no <>", value, "investorOrgcodeNo");
            return (Criteria) this;
        }

        public Criteria andInvestorOrgcodeNoGreaterThan(String value) {
            addCriterion("investor_orgcode_no >", value, "investorOrgcodeNo");
            return (Criteria) this;
        }

        public Criteria andInvestorOrgcodeNoGreaterThanOrEqualTo(String value) {
            addCriterion("investor_orgcode_no >=", value, "investorOrgcodeNo");
            return (Criteria) this;
        }

        public Criteria andInvestorOrgcodeNoLessThan(String value) {
            addCriterion("investor_orgcode_no <", value, "investorOrgcodeNo");
            return (Criteria) this;
        }

        public Criteria andInvestorOrgcodeNoLessThanOrEqualTo(String value) {
            addCriterion("investor_orgcode_no <=", value, "investorOrgcodeNo");
            return (Criteria) this;
        }

        public Criteria andInvestorOrgcodeNoLike(String value) {
            addCriterion("investor_orgcode_no like", value, "investorOrgcodeNo");
            return (Criteria) this;
        }

        public Criteria andInvestorOrgcodeNoNotLike(String value) {
            addCriterion("investor_orgcode_no not like", value, "investorOrgcodeNo");
            return (Criteria) this;
        }

        public Criteria andInvestorOrgcodeNoIn(List<String> values) {
            addCriterion("investor_orgcode_no in", values, "investorOrgcodeNo");
            return (Criteria) this;
        }

        public Criteria andInvestorOrgcodeNoNotIn(List<String> values) {
            addCriterion("investor_orgcode_no not in", values, "investorOrgcodeNo");
            return (Criteria) this;
        }

        public Criteria andInvestorOrgcodeNoBetween(String value1, String value2) {
            addCriterion("investor_orgcode_no between", value1, value2, "investorOrgcodeNo");
            return (Criteria) this;
        }

        public Criteria andInvestorOrgcodeNoNotBetween(String value1, String value2) {
            addCriterion("investor_orgcode_no not between", value1, value2, "investorOrgcodeNo");
            return (Criteria) this;
        }

        public Criteria andInvestorCompanyIsNull() {
            addCriterion("investor_company is null");
            return (Criteria) this;
        }

        public Criteria andInvestorCompanyIsNotNull() {
            addCriterion("investor_company is not null");
            return (Criteria) this;
        }

        public Criteria andInvestorCompanyEqualTo(String value) {
            addCriterion("investor_company =", value, "investorCompany");
            return (Criteria) this;
        }

        public Criteria andInvestorCompanyNotEqualTo(String value) {
            addCriterion("investor_company <>", value, "investorCompany");
            return (Criteria) this;
        }

        public Criteria andInvestorCompanyGreaterThan(String value) {
            addCriterion("investor_company >", value, "investorCompany");
            return (Criteria) this;
        }

        public Criteria andInvestorCompanyGreaterThanOrEqualTo(String value) {
            addCriterion("investor_company >=", value, "investorCompany");
            return (Criteria) this;
        }

        public Criteria andInvestorCompanyLessThan(String value) {
            addCriterion("investor_company <", value, "investorCompany");
            return (Criteria) this;
        }

        public Criteria andInvestorCompanyLessThanOrEqualTo(String value) {
            addCriterion("investor_company <=", value, "investorCompany");
            return (Criteria) this;
        }

        public Criteria andInvestorCompanyLike(String value) {
            addCriterion("investor_company like", value, "investorCompany");
            return (Criteria) this;
        }

        public Criteria andInvestorCompanyNotLike(String value) {
            addCriterion("investor_company not like", value, "investorCompany");
            return (Criteria) this;
        }

        public Criteria andInvestorCompanyIn(List<String> values) {
            addCriterion("investor_company in", values, "investorCompany");
            return (Criteria) this;
        }

        public Criteria andInvestorCompanyNotIn(List<String> values) {
            addCriterion("investor_company not in", values, "investorCompany");
            return (Criteria) this;
        }

        public Criteria andInvestorCompanyBetween(String value1, String value2) {
            addCriterion("investor_company between", value1, value2, "investorCompany");
            return (Criteria) this;
        }

        public Criteria andInvestorCompanyNotBetween(String value1, String value2) {
            addCriterion("investor_company not between", value1, value2, "investorCompany");
            return (Criteria) this;
        }

        public Criteria andInvestAmountIsNull() {
            addCriterion("invest_amount is null");
            return (Criteria) this;
        }

        public Criteria andInvestAmountIsNotNull() {
            addCriterion("invest_amount is not null");
            return (Criteria) this;
        }

        public Criteria andInvestAmountEqualTo(String value) {
            addCriterion("invest_amount =", value, "investAmount");
            return (Criteria) this;
        }

        public Criteria andInvestAmountNotEqualTo(String value) {
            addCriterion("invest_amount <>", value, "investAmount");
            return (Criteria) this;
        }

        public Criteria andInvestAmountGreaterThan(String value) {
            addCriterion("invest_amount >", value, "investAmount");
            return (Criteria) this;
        }

        public Criteria andInvestAmountGreaterThanOrEqualTo(String value) {
            addCriterion("invest_amount >=", value, "investAmount");
            return (Criteria) this;
        }

        public Criteria andInvestAmountLessThan(String value) {
            addCriterion("invest_amount <", value, "investAmount");
            return (Criteria) this;
        }

        public Criteria andInvestAmountLessThanOrEqualTo(String value) {
            addCriterion("invest_amount <=", value, "investAmount");
            return (Criteria) this;
        }

        public Criteria andInvestAmountLike(String value) {
            addCriterion("invest_amount like", value, "investAmount");
            return (Criteria) this;
        }

        public Criteria andInvestAmountNotLike(String value) {
            addCriterion("invest_amount not like", value, "investAmount");
            return (Criteria) this;
        }

        public Criteria andInvestAmountIn(List<String> values) {
            addCriterion("invest_amount in", values, "investAmount");
            return (Criteria) this;
        }

        public Criteria andInvestAmountNotIn(List<String> values) {
            addCriterion("invest_amount not in", values, "investAmount");
            return (Criteria) this;
        }

        public Criteria andInvestAmountBetween(String value1, String value2) {
            addCriterion("invest_amount between", value1, value2, "investAmount");
            return (Criteria) this;
        }

        public Criteria andInvestAmountNotBetween(String value1, String value2) {
            addCriterion("invest_amount not between", value1, value2, "investAmount");
            return (Criteria) this;
        }

        public Criteria andBorrowRateIsNull() {
            addCriterion("borrow_rate is null");
            return (Criteria) this;
        }

        public Criteria andBorrowRateIsNotNull() {
            addCriterion("borrow_rate is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowRateEqualTo(String value) {
            addCriterion("borrow_rate =", value, "borrowRate");
            return (Criteria) this;
        }

        public Criteria andBorrowRateNotEqualTo(String value) {
            addCriterion("borrow_rate <>", value, "borrowRate");
            return (Criteria) this;
        }

        public Criteria andBorrowRateGreaterThan(String value) {
            addCriterion("borrow_rate >", value, "borrowRate");
            return (Criteria) this;
        }

        public Criteria andBorrowRateGreaterThanOrEqualTo(String value) {
            addCriterion("borrow_rate >=", value, "borrowRate");
            return (Criteria) this;
        }

        public Criteria andBorrowRateLessThan(String value) {
            addCriterion("borrow_rate <", value, "borrowRate");
            return (Criteria) this;
        }

        public Criteria andBorrowRateLessThanOrEqualTo(String value) {
            addCriterion("borrow_rate <=", value, "borrowRate");
            return (Criteria) this;
        }

        public Criteria andBorrowRateLike(String value) {
            addCriterion("borrow_rate like", value, "borrowRate");
            return (Criteria) this;
        }

        public Criteria andBorrowRateNotLike(String value) {
            addCriterion("borrow_rate not like", value, "borrowRate");
            return (Criteria) this;
        }

        public Criteria andBorrowRateIn(List<String> values) {
            addCriterion("borrow_rate in", values, "borrowRate");
            return (Criteria) this;
        }

        public Criteria andBorrowRateNotIn(List<String> values) {
            addCriterion("borrow_rate not in", values, "borrowRate");
            return (Criteria) this;
        }

        public Criteria andBorrowRateBetween(String value1, String value2) {
            addCriterion("borrow_rate between", value1, value2, "borrowRate");
            return (Criteria) this;
        }

        public Criteria andBorrowRateNotBetween(String value1, String value2) {
            addCriterion("borrow_rate not between", value1, value2, "borrowRate");
            return (Criteria) this;
        }

        public Criteria andBorrowUseIsNull() {
            addCriterion("borrow_use is null");
            return (Criteria) this;
        }

        public Criteria andBorrowUseIsNotNull() {
            addCriterion("borrow_use is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowUseEqualTo(String value) {
            addCriterion("borrow_use =", value, "borrowUse");
            return (Criteria) this;
        }

        public Criteria andBorrowUseNotEqualTo(String value) {
            addCriterion("borrow_use <>", value, "borrowUse");
            return (Criteria) this;
        }

        public Criteria andBorrowUseGreaterThan(String value) {
            addCriterion("borrow_use >", value, "borrowUse");
            return (Criteria) this;
        }

        public Criteria andBorrowUseGreaterThanOrEqualTo(String value) {
            addCriterion("borrow_use >=", value, "borrowUse");
            return (Criteria) this;
        }

        public Criteria andBorrowUseLessThan(String value) {
            addCriterion("borrow_use <", value, "borrowUse");
            return (Criteria) this;
        }

        public Criteria andBorrowUseLessThanOrEqualTo(String value) {
            addCriterion("borrow_use <=", value, "borrowUse");
            return (Criteria) this;
        }

        public Criteria andBorrowUseLike(String value) {
            addCriterion("borrow_use like", value, "borrowUse");
            return (Criteria) this;
        }

        public Criteria andBorrowUseNotLike(String value) {
            addCriterion("borrow_use not like", value, "borrowUse");
            return (Criteria) this;
        }

        public Criteria andBorrowUseIn(List<String> values) {
            addCriterion("borrow_use in", values, "borrowUse");
            return (Criteria) this;
        }

        public Criteria andBorrowUseNotIn(List<String> values) {
            addCriterion("borrow_use not in", values, "borrowUse");
            return (Criteria) this;
        }

        public Criteria andBorrowUseBetween(String value1, String value2) {
            addCriterion("borrow_use between", value1, value2, "borrowUse");
            return (Criteria) this;
        }

        public Criteria andBorrowUseNotBetween(String value1, String value2) {
            addCriterion("borrow_use not between", value1, value2, "borrowUse");
            return (Criteria) this;
        }

        public Criteria andBorrowUseLimitIsNull() {
            addCriterion("borrow_use_limit is null");
            return (Criteria) this;
        }

        public Criteria andBorrowUseLimitIsNotNull() {
            addCriterion("borrow_use_limit is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowUseLimitEqualTo(String value) {
            addCriterion("borrow_use_limit =", value, "borrowUseLimit");
            return (Criteria) this;
        }

        public Criteria andBorrowUseLimitNotEqualTo(String value) {
            addCriterion("borrow_use_limit <>", value, "borrowUseLimit");
            return (Criteria) this;
        }

        public Criteria andBorrowUseLimitGreaterThan(String value) {
            addCriterion("borrow_use_limit >", value, "borrowUseLimit");
            return (Criteria) this;
        }

        public Criteria andBorrowUseLimitGreaterThanOrEqualTo(String value) {
            addCriterion("borrow_use_limit >=", value, "borrowUseLimit");
            return (Criteria) this;
        }

        public Criteria andBorrowUseLimitLessThan(String value) {
            addCriterion("borrow_use_limit <", value, "borrowUseLimit");
            return (Criteria) this;
        }

        public Criteria andBorrowUseLimitLessThanOrEqualTo(String value) {
            addCriterion("borrow_use_limit <=", value, "borrowUseLimit");
            return (Criteria) this;
        }

        public Criteria andBorrowUseLimitLike(String value) {
            addCriterion("borrow_use_limit like", value, "borrowUseLimit");
            return (Criteria) this;
        }

        public Criteria andBorrowUseLimitNotLike(String value) {
            addCriterion("borrow_use_limit not like", value, "borrowUseLimit");
            return (Criteria) this;
        }

        public Criteria andBorrowUseLimitIn(List<String> values) {
            addCriterion("borrow_use_limit in", values, "borrowUseLimit");
            return (Criteria) this;
        }

        public Criteria andBorrowUseLimitNotIn(List<String> values) {
            addCriterion("borrow_use_limit not in", values, "borrowUseLimit");
            return (Criteria) this;
        }

        public Criteria andBorrowUseLimitBetween(String value1, String value2) {
            addCriterion("borrow_use_limit between", value1, value2, "borrowUseLimit");
            return (Criteria) this;
        }

        public Criteria andBorrowUseLimitNotBetween(String value1, String value2) {
            addCriterion("borrow_use_limit not between", value1, value2, "borrowUseLimit");
            return (Criteria) this;
        }

        public Criteria andLoanDateIsNull() {
            addCriterion("loan_date is null");
            return (Criteria) this;
        }

        public Criteria andLoanDateIsNotNull() {
            addCriterion("loan_date is not null");
            return (Criteria) this;
        }

        public Criteria andLoanDateEqualTo(String value) {
            addCriterion("loan_date =", value, "loanDate");
            return (Criteria) this;
        }

        public Criteria andLoanDateNotEqualTo(String value) {
            addCriterion("loan_date <>", value, "loanDate");
            return (Criteria) this;
        }

        public Criteria andLoanDateGreaterThan(String value) {
            addCriterion("loan_date >", value, "loanDate");
            return (Criteria) this;
        }

        public Criteria andLoanDateGreaterThanOrEqualTo(String value) {
            addCriterion("loan_date >=", value, "loanDate");
            return (Criteria) this;
        }

        public Criteria andLoanDateLessThan(String value) {
            addCriterion("loan_date <", value, "loanDate");
            return (Criteria) this;
        }

        public Criteria andLoanDateLessThanOrEqualTo(String value) {
            addCriterion("loan_date <=", value, "loanDate");
            return (Criteria) this;
        }

        public Criteria andLoanDateLike(String value) {
            addCriterion("loan_date like", value, "loanDate");
            return (Criteria) this;
        }

        public Criteria andLoanDateNotLike(String value) {
            addCriterion("loan_date not like", value, "loanDate");
            return (Criteria) this;
        }

        public Criteria andLoanDateIn(List<String> values) {
            addCriterion("loan_date in", values, "loanDate");
            return (Criteria) this;
        }

        public Criteria andLoanDateNotIn(List<String> values) {
            addCriterion("loan_date not in", values, "loanDate");
            return (Criteria) this;
        }

        public Criteria andLoanDateBetween(String value1, String value2) {
            addCriterion("loan_date between", value1, value2, "loanDate");
            return (Criteria) this;
        }

        public Criteria andLoanDateNotBetween(String value1, String value2) {
            addCriterion("loan_date not between", value1, value2, "loanDate");
            return (Criteria) this;
        }

        public Criteria andLoanDateBasisIsNull() {
            addCriterion("loan_date_basis is null");
            return (Criteria) this;
        }

        public Criteria andLoanDateBasisIsNotNull() {
            addCriterion("loan_date_basis is not null");
            return (Criteria) this;
        }

        public Criteria andLoanDateBasisEqualTo(String value) {
            addCriterion("loan_date_basis =", value, "loanDateBasis");
            return (Criteria) this;
        }

        public Criteria andLoanDateBasisNotEqualTo(String value) {
            addCriterion("loan_date_basis <>", value, "loanDateBasis");
            return (Criteria) this;
        }

        public Criteria andLoanDateBasisGreaterThan(String value) {
            addCriterion("loan_date_basis >", value, "loanDateBasis");
            return (Criteria) this;
        }

        public Criteria andLoanDateBasisGreaterThanOrEqualTo(String value) {
            addCriterion("loan_date_basis >=", value, "loanDateBasis");
            return (Criteria) this;
        }

        public Criteria andLoanDateBasisLessThan(String value) {
            addCriterion("loan_date_basis <", value, "loanDateBasis");
            return (Criteria) this;
        }

        public Criteria andLoanDateBasisLessThanOrEqualTo(String value) {
            addCriterion("loan_date_basis <=", value, "loanDateBasis");
            return (Criteria) this;
        }

        public Criteria andLoanDateBasisLike(String value) {
            addCriterion("loan_date_basis like", value, "loanDateBasis");
            return (Criteria) this;
        }

        public Criteria andLoanDateBasisNotLike(String value) {
            addCriterion("loan_date_basis not like", value, "loanDateBasis");
            return (Criteria) this;
        }

        public Criteria andLoanDateBasisIn(List<String> values) {
            addCriterion("loan_date_basis in", values, "loanDateBasis");
            return (Criteria) this;
        }

        public Criteria andLoanDateBasisNotIn(List<String> values) {
            addCriterion("loan_date_basis not in", values, "loanDateBasis");
            return (Criteria) this;
        }

        public Criteria andLoanDateBasisBetween(String value1, String value2) {
            addCriterion("loan_date_basis between", value1, value2, "loanDateBasis");
            return (Criteria) this;
        }

        public Criteria andLoanDateBasisNotBetween(String value1, String value2) {
            addCriterion("loan_date_basis not between", value1, value2, "loanDateBasis");
            return (Criteria) this;
        }

        public Criteria andStartDateIsNull() {
            addCriterion("start_date is null");
            return (Criteria) this;
        }

        public Criteria andStartDateIsNotNull() {
            addCriterion("start_date is not null");
            return (Criteria) this;
        }

        public Criteria andStartDateEqualTo(String value) {
            addCriterion("start_date =", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateNotEqualTo(String value) {
            addCriterion("start_date <>", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateGreaterThan(String value) {
            addCriterion("start_date >", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateGreaterThanOrEqualTo(String value) {
            addCriterion("start_date >=", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateLessThan(String value) {
            addCriterion("start_date <", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateLessThanOrEqualTo(String value) {
            addCriterion("start_date <=", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateLike(String value) {
            addCriterion("start_date like", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateNotLike(String value) {
            addCriterion("start_date not like", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateIn(List<String> values) {
            addCriterion("start_date in", values, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateNotIn(List<String> values) {
            addCriterion("start_date not in", values, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateBetween(String value1, String value2) {
            addCriterion("start_date between", value1, value2, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateNotBetween(String value1, String value2) {
            addCriterion("start_date not between", value1, value2, "startDate");
            return (Criteria) this;
        }

        public Criteria andExpiryDateIsNull() {
            addCriterion("expiry_date is null");
            return (Criteria) this;
        }

        public Criteria andExpiryDateIsNotNull() {
            addCriterion("expiry_date is not null");
            return (Criteria) this;
        }

        public Criteria andExpiryDateEqualTo(String value) {
            addCriterion("expiry_date =", value, "expiryDate");
            return (Criteria) this;
        }

        public Criteria andExpiryDateNotEqualTo(String value) {
            addCriterion("expiry_date <>", value, "expiryDate");
            return (Criteria) this;
        }

        public Criteria andExpiryDateGreaterThan(String value) {
            addCriterion("expiry_date >", value, "expiryDate");
            return (Criteria) this;
        }

        public Criteria andExpiryDateGreaterThanOrEqualTo(String value) {
            addCriterion("expiry_date >=", value, "expiryDate");
            return (Criteria) this;
        }

        public Criteria andExpiryDateLessThan(String value) {
            addCriterion("expiry_date <", value, "expiryDate");
            return (Criteria) this;
        }

        public Criteria andExpiryDateLessThanOrEqualTo(String value) {
            addCriterion("expiry_date <=", value, "expiryDate");
            return (Criteria) this;
        }

        public Criteria andExpiryDateLike(String value) {
            addCriterion("expiry_date like", value, "expiryDate");
            return (Criteria) this;
        }

        public Criteria andExpiryDateNotLike(String value) {
            addCriterion("expiry_date not like", value, "expiryDate");
            return (Criteria) this;
        }

        public Criteria andExpiryDateIn(List<String> values) {
            addCriterion("expiry_date in", values, "expiryDate");
            return (Criteria) this;
        }

        public Criteria andExpiryDateNotIn(List<String> values) {
            addCriterion("expiry_date not in", values, "expiryDate");
            return (Criteria) this;
        }

        public Criteria andExpiryDateBetween(String value1, String value2) {
            addCriterion("expiry_date between", value1, value2, "expiryDate");
            return (Criteria) this;
        }

        public Criteria andExpiryDateNotBetween(String value1, String value2) {
            addCriterion("expiry_date not between", value1, value2, "expiryDate");
            return (Criteria) this;
        }

        public Criteria andRepayTypeIsNull() {
            addCriterion("repay_type is null");
            return (Criteria) this;
        }

        public Criteria andRepayTypeIsNotNull() {
            addCriterion("repay_type is not null");
            return (Criteria) this;
        }

        public Criteria andRepayTypeEqualTo(Integer value) {
            addCriterion("repay_type =", value, "repayType");
            return (Criteria) this;
        }

        public Criteria andRepayTypeNotEqualTo(Integer value) {
            addCriterion("repay_type <>", value, "repayType");
            return (Criteria) this;
        }

        public Criteria andRepayTypeGreaterThan(Integer value) {
            addCriterion("repay_type >", value, "repayType");
            return (Criteria) this;
        }

        public Criteria andRepayTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("repay_type >=", value, "repayType");
            return (Criteria) this;
        }

        public Criteria andRepayTypeLessThan(Integer value) {
            addCriterion("repay_type <", value, "repayType");
            return (Criteria) this;
        }

        public Criteria andRepayTypeLessThanOrEqualTo(Integer value) {
            addCriterion("repay_type <=", value, "repayType");
            return (Criteria) this;
        }

        public Criteria andRepayTypeIn(List<Integer> values) {
            addCriterion("repay_type in", values, "repayType");
            return (Criteria) this;
        }

        public Criteria andRepayTypeNotIn(List<Integer> values) {
            addCriterion("repay_type not in", values, "repayType");
            return (Criteria) this;
        }

        public Criteria andRepayTypeBetween(Integer value1, Integer value2) {
            addCriterion("repay_type between", value1, value2, "repayType");
            return (Criteria) this;
        }

        public Criteria andRepayTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("repay_type not between", value1, value2, "repayType");
            return (Criteria) this;
        }

        public Criteria andRepayFormulaIsNull() {
            addCriterion("repay_formula is null");
            return (Criteria) this;
        }

        public Criteria andRepayFormulaIsNotNull() {
            addCriterion("repay_formula is not null");
            return (Criteria) this;
        }

        public Criteria andRepayFormulaEqualTo(String value) {
            addCriterion("repay_formula =", value, "repayFormula");
            return (Criteria) this;
        }

        public Criteria andRepayFormulaNotEqualTo(String value) {
            addCriterion("repay_formula <>", value, "repayFormula");
            return (Criteria) this;
        }

        public Criteria andRepayFormulaGreaterThan(String value) {
            addCriterion("repay_formula >", value, "repayFormula");
            return (Criteria) this;
        }

        public Criteria andRepayFormulaGreaterThanOrEqualTo(String value) {
            addCriterion("repay_formula >=", value, "repayFormula");
            return (Criteria) this;
        }

        public Criteria andRepayFormulaLessThan(String value) {
            addCriterion("repay_formula <", value, "repayFormula");
            return (Criteria) this;
        }

        public Criteria andRepayFormulaLessThanOrEqualTo(String value) {
            addCriterion("repay_formula <=", value, "repayFormula");
            return (Criteria) this;
        }

        public Criteria andRepayFormulaLike(String value) {
            addCriterion("repay_formula like", value, "repayFormula");
            return (Criteria) this;
        }

        public Criteria andRepayFormulaNotLike(String value) {
            addCriterion("repay_formula not like", value, "repayFormula");
            return (Criteria) this;
        }

        public Criteria andRepayFormulaIn(List<String> values) {
            addCriterion("repay_formula in", values, "repayFormula");
            return (Criteria) this;
        }

        public Criteria andRepayFormulaNotIn(List<String> values) {
            addCriterion("repay_formula not in", values, "repayFormula");
            return (Criteria) this;
        }

        public Criteria andRepayFormulaBetween(String value1, String value2) {
            addCriterion("repay_formula between", value1, value2, "repayFormula");
            return (Criteria) this;
        }

        public Criteria andRepayFormulaNotBetween(String value1, String value2) {
            addCriterion("repay_formula not between", value1, value2, "repayFormula");
            return (Criteria) this;
        }

        public Criteria andRepayDateRuleIsNull() {
            addCriterion("repay_date_rule is null");
            return (Criteria) this;
        }

        public Criteria andRepayDateRuleIsNotNull() {
            addCriterion("repay_date_rule is not null");
            return (Criteria) this;
        }

        public Criteria andRepayDateRuleEqualTo(String value) {
            addCriterion("repay_date_rule =", value, "repayDateRule");
            return (Criteria) this;
        }

        public Criteria andRepayDateRuleNotEqualTo(String value) {
            addCriterion("repay_date_rule <>", value, "repayDateRule");
            return (Criteria) this;
        }

        public Criteria andRepayDateRuleGreaterThan(String value) {
            addCriterion("repay_date_rule >", value, "repayDateRule");
            return (Criteria) this;
        }

        public Criteria andRepayDateRuleGreaterThanOrEqualTo(String value) {
            addCriterion("repay_date_rule >=", value, "repayDateRule");
            return (Criteria) this;
        }

        public Criteria andRepayDateRuleLessThan(String value) {
            addCriterion("repay_date_rule <", value, "repayDateRule");
            return (Criteria) this;
        }

        public Criteria andRepayDateRuleLessThanOrEqualTo(String value) {
            addCriterion("repay_date_rule <=", value, "repayDateRule");
            return (Criteria) this;
        }

        public Criteria andRepayDateRuleLike(String value) {
            addCriterion("repay_date_rule like", value, "repayDateRule");
            return (Criteria) this;
        }

        public Criteria andRepayDateRuleNotLike(String value) {
            addCriterion("repay_date_rule not like", value, "repayDateRule");
            return (Criteria) this;
        }

        public Criteria andRepayDateRuleIn(List<String> values) {
            addCriterion("repay_date_rule in", values, "repayDateRule");
            return (Criteria) this;
        }

        public Criteria andRepayDateRuleNotIn(List<String> values) {
            addCriterion("repay_date_rule not in", values, "repayDateRule");
            return (Criteria) this;
        }

        public Criteria andRepayDateRuleBetween(String value1, String value2) {
            addCriterion("repay_date_rule between", value1, value2, "repayDateRule");
            return (Criteria) this;
        }

        public Criteria andRepayDateRuleNotBetween(String value1, String value2) {
            addCriterion("repay_date_rule not between", value1, value2, "repayDateRule");
            return (Criteria) this;
        }

        public Criteria andRepayNumIsNull() {
            addCriterion("repay_num is null");
            return (Criteria) this;
        }

        public Criteria andRepayNumIsNotNull() {
            addCriterion("repay_num is not null");
            return (Criteria) this;
        }

        public Criteria andRepayNumEqualTo(Integer value) {
            addCriterion("repay_num =", value, "repayNum");
            return (Criteria) this;
        }

        public Criteria andRepayNumNotEqualTo(Integer value) {
            addCriterion("repay_num <>", value, "repayNum");
            return (Criteria) this;
        }

        public Criteria andRepayNumGreaterThan(Integer value) {
            addCriterion("repay_num >", value, "repayNum");
            return (Criteria) this;
        }

        public Criteria andRepayNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("repay_num >=", value, "repayNum");
            return (Criteria) this;
        }

        public Criteria andRepayNumLessThan(Integer value) {
            addCriterion("repay_num <", value, "repayNum");
            return (Criteria) this;
        }

        public Criteria andRepayNumLessThanOrEqualTo(Integer value) {
            addCriterion("repay_num <=", value, "repayNum");
            return (Criteria) this;
        }

        public Criteria andRepayNumIn(List<Integer> values) {
            addCriterion("repay_num in", values, "repayNum");
            return (Criteria) this;
        }

        public Criteria andRepayNumNotIn(List<Integer> values) {
            addCriterion("repay_num not in", values, "repayNum");
            return (Criteria) this;
        }

        public Criteria andRepayNumBetween(Integer value1, Integer value2) {
            addCriterion("repay_num between", value1, value2, "repayNum");
            return (Criteria) this;
        }

        public Criteria andRepayNumNotBetween(Integer value1, Integer value2) {
            addCriterion("repay_num not between", value1, value2, "repayNum");
            return (Criteria) this;
        }

        public Criteria andRepayPlanIsNull() {
            addCriterion("repay_plan is null");
            return (Criteria) this;
        }

        public Criteria andRepayPlanIsNotNull() {
            addCriterion("repay_plan is not null");
            return (Criteria) this;
        }

        public Criteria andRepayPlanEqualTo(String value) {
            addCriterion("repay_plan =", value, "repayPlan");
            return (Criteria) this;
        }

        public Criteria andRepayPlanNotEqualTo(String value) {
            addCriterion("repay_plan <>", value, "repayPlan");
            return (Criteria) this;
        }

        public Criteria andRepayPlanGreaterThan(String value) {
            addCriterion("repay_plan >", value, "repayPlan");
            return (Criteria) this;
        }

        public Criteria andRepayPlanGreaterThanOrEqualTo(String value) {
            addCriterion("repay_plan >=", value, "repayPlan");
            return (Criteria) this;
        }

        public Criteria andRepayPlanLessThan(String value) {
            addCriterion("repay_plan <", value, "repayPlan");
            return (Criteria) this;
        }

        public Criteria andRepayPlanLessThanOrEqualTo(String value) {
            addCriterion("repay_plan <=", value, "repayPlan");
            return (Criteria) this;
        }

        public Criteria andRepayPlanLike(String value) {
            addCriterion("repay_plan like", value, "repayPlan");
            return (Criteria) this;
        }

        public Criteria andRepayPlanNotLike(String value) {
            addCriterion("repay_plan not like", value, "repayPlan");
            return (Criteria) this;
        }

        public Criteria andRepayPlanIn(List<String> values) {
            addCriterion("repay_plan in", values, "repayPlan");
            return (Criteria) this;
        }

        public Criteria andRepayPlanNotIn(List<String> values) {
            addCriterion("repay_plan not in", values, "repayPlan");
            return (Criteria) this;
        }

        public Criteria andRepayPlanBetween(String value1, String value2) {
            addCriterion("repay_plan between", value1, value2, "repayPlan");
            return (Criteria) this;
        }

        public Criteria andRepayPlanNotBetween(String value1, String value2) {
            addCriterion("repay_plan not between", value1, value2, "repayPlan");
            return (Criteria) this;
        }

        public Criteria andOverdueRepayDefIsNull() {
            addCriterion("overdue_repay_def is null");
            return (Criteria) this;
        }

        public Criteria andOverdueRepayDefIsNotNull() {
            addCriterion("overdue_repay_def is not null");
            return (Criteria) this;
        }

        public Criteria andOverdueRepayDefEqualTo(String value) {
            addCriterion("overdue_repay_def =", value, "overdueRepayDef");
            return (Criteria) this;
        }

        public Criteria andOverdueRepayDefNotEqualTo(String value) {
            addCriterion("overdue_repay_def <>", value, "overdueRepayDef");
            return (Criteria) this;
        }

        public Criteria andOverdueRepayDefGreaterThan(String value) {
            addCriterion("overdue_repay_def >", value, "overdueRepayDef");
            return (Criteria) this;
        }

        public Criteria andOverdueRepayDefGreaterThanOrEqualTo(String value) {
            addCriterion("overdue_repay_def >=", value, "overdueRepayDef");
            return (Criteria) this;
        }

        public Criteria andOverdueRepayDefLessThan(String value) {
            addCriterion("overdue_repay_def <", value, "overdueRepayDef");
            return (Criteria) this;
        }

        public Criteria andOverdueRepayDefLessThanOrEqualTo(String value) {
            addCriterion("overdue_repay_def <=", value, "overdueRepayDef");
            return (Criteria) this;
        }

        public Criteria andOverdueRepayDefLike(String value) {
            addCriterion("overdue_repay_def like", value, "overdueRepayDef");
            return (Criteria) this;
        }

        public Criteria andOverdueRepayDefNotLike(String value) {
            addCriterion("overdue_repay_def not like", value, "overdueRepayDef");
            return (Criteria) this;
        }

        public Criteria andOverdueRepayDefIn(List<String> values) {
            addCriterion("overdue_repay_def in", values, "overdueRepayDef");
            return (Criteria) this;
        }

        public Criteria andOverdueRepayDefNotIn(List<String> values) {
            addCriterion("overdue_repay_def not in", values, "overdueRepayDef");
            return (Criteria) this;
        }

        public Criteria andOverdueRepayDefBetween(String value1, String value2) {
            addCriterion("overdue_repay_def between", value1, value2, "overdueRepayDef");
            return (Criteria) this;
        }

        public Criteria andOverdueRepayDefNotBetween(String value1, String value2) {
            addCriterion("overdue_repay_def not between", value1, value2, "overdueRepayDef");
            return (Criteria) this;
        }

        public Criteria andOverdueRepayRespIsNull() {
            addCriterion("overdue_repay_resp is null");
            return (Criteria) this;
        }

        public Criteria andOverdueRepayRespIsNotNull() {
            addCriterion("overdue_repay_resp is not null");
            return (Criteria) this;
        }

        public Criteria andOverdueRepayRespEqualTo(String value) {
            addCriterion("overdue_repay_resp =", value, "overdueRepayResp");
            return (Criteria) this;
        }

        public Criteria andOverdueRepayRespNotEqualTo(String value) {
            addCriterion("overdue_repay_resp <>", value, "overdueRepayResp");
            return (Criteria) this;
        }

        public Criteria andOverdueRepayRespGreaterThan(String value) {
            addCriterion("overdue_repay_resp >", value, "overdueRepayResp");
            return (Criteria) this;
        }

        public Criteria andOverdueRepayRespGreaterThanOrEqualTo(String value) {
            addCriterion("overdue_repay_resp >=", value, "overdueRepayResp");
            return (Criteria) this;
        }

        public Criteria andOverdueRepayRespLessThan(String value) {
            addCriterion("overdue_repay_resp <", value, "overdueRepayResp");
            return (Criteria) this;
        }

        public Criteria andOverdueRepayRespLessThanOrEqualTo(String value) {
            addCriterion("overdue_repay_resp <=", value, "overdueRepayResp");
            return (Criteria) this;
        }

        public Criteria andOverdueRepayRespLike(String value) {
            addCriterion("overdue_repay_resp like", value, "overdueRepayResp");
            return (Criteria) this;
        }

        public Criteria andOverdueRepayRespNotLike(String value) {
            addCriterion("overdue_repay_resp not like", value, "overdueRepayResp");
            return (Criteria) this;
        }

        public Criteria andOverdueRepayRespIn(List<String> values) {
            addCriterion("overdue_repay_resp in", values, "overdueRepayResp");
            return (Criteria) this;
        }

        public Criteria andOverdueRepayRespNotIn(List<String> values) {
            addCriterion("overdue_repay_resp not in", values, "overdueRepayResp");
            return (Criteria) this;
        }

        public Criteria andOverdueRepayRespBetween(String value1, String value2) {
            addCriterion("overdue_repay_resp between", value1, value2, "overdueRepayResp");
            return (Criteria) this;
        }

        public Criteria andOverdueRepayRespNotBetween(String value1, String value2) {
            addCriterion("overdue_repay_resp not between", value1, value2, "overdueRepayResp");
            return (Criteria) this;
        }

        public Criteria andOverdueRepayProcIsNull() {
            addCriterion("overdue_repay_proc is null");
            return (Criteria) this;
        }

        public Criteria andOverdueRepayProcIsNotNull() {
            addCriterion("overdue_repay_proc is not null");
            return (Criteria) this;
        }

        public Criteria andOverdueRepayProcEqualTo(String value) {
            addCriterion("overdue_repay_proc =", value, "overdueRepayProc");
            return (Criteria) this;
        }

        public Criteria andOverdueRepayProcNotEqualTo(String value) {
            addCriterion("overdue_repay_proc <>", value, "overdueRepayProc");
            return (Criteria) this;
        }

        public Criteria andOverdueRepayProcGreaterThan(String value) {
            addCriterion("overdue_repay_proc >", value, "overdueRepayProc");
            return (Criteria) this;
        }

        public Criteria andOverdueRepayProcGreaterThanOrEqualTo(String value) {
            addCriterion("overdue_repay_proc >=", value, "overdueRepayProc");
            return (Criteria) this;
        }

        public Criteria andOverdueRepayProcLessThan(String value) {
            addCriterion("overdue_repay_proc <", value, "overdueRepayProc");
            return (Criteria) this;
        }

        public Criteria andOverdueRepayProcLessThanOrEqualTo(String value) {
            addCriterion("overdue_repay_proc <=", value, "overdueRepayProc");
            return (Criteria) this;
        }

        public Criteria andOverdueRepayProcLike(String value) {
            addCriterion("overdue_repay_proc like", value, "overdueRepayProc");
            return (Criteria) this;
        }

        public Criteria andOverdueRepayProcNotLike(String value) {
            addCriterion("overdue_repay_proc not like", value, "overdueRepayProc");
            return (Criteria) this;
        }

        public Criteria andOverdueRepayProcIn(List<String> values) {
            addCriterion("overdue_repay_proc in", values, "overdueRepayProc");
            return (Criteria) this;
        }

        public Criteria andOverdueRepayProcNotIn(List<String> values) {
            addCriterion("overdue_repay_proc not in", values, "overdueRepayProc");
            return (Criteria) this;
        }

        public Criteria andOverdueRepayProcBetween(String value1, String value2) {
            addCriterion("overdue_repay_proc between", value1, value2, "overdueRepayProc");
            return (Criteria) this;
        }

        public Criteria andOverdueRepayProcNotBetween(String value1, String value2) {
            addCriterion("overdue_repay_proc not between", value1, value2, "overdueRepayProc");
            return (Criteria) this;
        }

        public Criteria andNoticeAddressIsNull() {
            addCriterion("notice_address is null");
            return (Criteria) this;
        }

        public Criteria andNoticeAddressIsNotNull() {
            addCriterion("notice_address is not null");
            return (Criteria) this;
        }

        public Criteria andNoticeAddressEqualTo(String value) {
            addCriterion("notice_address =", value, "noticeAddress");
            return (Criteria) this;
        }

        public Criteria andNoticeAddressNotEqualTo(String value) {
            addCriterion("notice_address <>", value, "noticeAddress");
            return (Criteria) this;
        }

        public Criteria andNoticeAddressGreaterThan(String value) {
            addCriterion("notice_address >", value, "noticeAddress");
            return (Criteria) this;
        }

        public Criteria andNoticeAddressGreaterThanOrEqualTo(String value) {
            addCriterion("notice_address >=", value, "noticeAddress");
            return (Criteria) this;
        }

        public Criteria andNoticeAddressLessThan(String value) {
            addCriterion("notice_address <", value, "noticeAddress");
            return (Criteria) this;
        }

        public Criteria andNoticeAddressLessThanOrEqualTo(String value) {
            addCriterion("notice_address <=", value, "noticeAddress");
            return (Criteria) this;
        }

        public Criteria andNoticeAddressLike(String value) {
            addCriterion("notice_address like", value, "noticeAddress");
            return (Criteria) this;
        }

        public Criteria andNoticeAddressNotLike(String value) {
            addCriterion("notice_address not like", value, "noticeAddress");
            return (Criteria) this;
        }

        public Criteria andNoticeAddressIn(List<String> values) {
            addCriterion("notice_address in", values, "noticeAddress");
            return (Criteria) this;
        }

        public Criteria andNoticeAddressNotIn(List<String> values) {
            addCriterion("notice_address not in", values, "noticeAddress");
            return (Criteria) this;
        }

        public Criteria andNoticeAddressBetween(String value1, String value2) {
            addCriterion("notice_address between", value1, value2, "noticeAddress");
            return (Criteria) this;
        }

        public Criteria andNoticeAddressNotBetween(String value1, String value2) {
            addCriterion("notice_address not between", value1, value2, "noticeAddress");
            return (Criteria) this;
        }

        public Criteria andContractEffectiveDateIsNull() {
            addCriterion("contract_effective_date is null");
            return (Criteria) this;
        }

        public Criteria andContractEffectiveDateIsNotNull() {
            addCriterion("contract_effective_date is not null");
            return (Criteria) this;
        }

        public Criteria andContractEffectiveDateEqualTo(String value) {
            addCriterion("contract_effective_date =", value, "contractEffectiveDate");
            return (Criteria) this;
        }

        public Criteria andContractEffectiveDateNotEqualTo(String value) {
            addCriterion("contract_effective_date <>", value, "contractEffectiveDate");
            return (Criteria) this;
        }

        public Criteria andContractEffectiveDateGreaterThan(String value) {
            addCriterion("contract_effective_date >", value, "contractEffectiveDate");
            return (Criteria) this;
        }

        public Criteria andContractEffectiveDateGreaterThanOrEqualTo(String value) {
            addCriterion("contract_effective_date >=", value, "contractEffectiveDate");
            return (Criteria) this;
        }

        public Criteria andContractEffectiveDateLessThan(String value) {
            addCriterion("contract_effective_date <", value, "contractEffectiveDate");
            return (Criteria) this;
        }

        public Criteria andContractEffectiveDateLessThanOrEqualTo(String value) {
            addCriterion("contract_effective_date <=", value, "contractEffectiveDate");
            return (Criteria) this;
        }

        public Criteria andContractEffectiveDateLike(String value) {
            addCriterion("contract_effective_date like", value, "contractEffectiveDate");
            return (Criteria) this;
        }

        public Criteria andContractEffectiveDateNotLike(String value) {
            addCriterion("contract_effective_date not like", value, "contractEffectiveDate");
            return (Criteria) this;
        }

        public Criteria andContractEffectiveDateIn(List<String> values) {
            addCriterion("contract_effective_date in", values, "contractEffectiveDate");
            return (Criteria) this;
        }

        public Criteria andContractEffectiveDateNotIn(List<String> values) {
            addCriterion("contract_effective_date not in", values, "contractEffectiveDate");
            return (Criteria) this;
        }

        public Criteria andContractEffectiveDateBetween(String value1, String value2) {
            addCriterion("contract_effective_date between", value1, value2, "contractEffectiveDate");
            return (Criteria) this;
        }

        public Criteria andContractEffectiveDateNotBetween(String value1, String value2) {
            addCriterion("contract_effective_date not between", value1, value2, "contractEffectiveDate");
            return (Criteria) this;
        }

        public Criteria andContractTemplateNoIsNull() {
            addCriterion("contract_template_no is null");
            return (Criteria) this;
        }

        public Criteria andContractTemplateNoIsNotNull() {
            addCriterion("contract_template_no is not null");
            return (Criteria) this;
        }

        public Criteria andContractTemplateNoEqualTo(String value) {
            addCriterion("contract_template_no =", value, "contractTemplateNo");
            return (Criteria) this;
        }

        public Criteria andContractTemplateNoNotEqualTo(String value) {
            addCriterion("contract_template_no <>", value, "contractTemplateNo");
            return (Criteria) this;
        }

        public Criteria andContractTemplateNoGreaterThan(String value) {
            addCriterion("contract_template_no >", value, "contractTemplateNo");
            return (Criteria) this;
        }

        public Criteria andContractTemplateNoGreaterThanOrEqualTo(String value) {
            addCriterion("contract_template_no >=", value, "contractTemplateNo");
            return (Criteria) this;
        }

        public Criteria andContractTemplateNoLessThan(String value) {
            addCriterion("contract_template_no <", value, "contractTemplateNo");
            return (Criteria) this;
        }

        public Criteria andContractTemplateNoLessThanOrEqualTo(String value) {
            addCriterion("contract_template_no <=", value, "contractTemplateNo");
            return (Criteria) this;
        }

        public Criteria andContractTemplateNoLike(String value) {
            addCriterion("contract_template_no like", value, "contractTemplateNo");
            return (Criteria) this;
        }

        public Criteria andContractTemplateNoNotLike(String value) {
            addCriterion("contract_template_no not like", value, "contractTemplateNo");
            return (Criteria) this;
        }

        public Criteria andContractTemplateNoIn(List<String> values) {
            addCriterion("contract_template_no in", values, "contractTemplateNo");
            return (Criteria) this;
        }

        public Criteria andContractTemplateNoNotIn(List<String> values) {
            addCriterion("contract_template_no not in", values, "contractTemplateNo");
            return (Criteria) this;
        }

        public Criteria andContractTemplateNoBetween(String value1, String value2) {
            addCriterion("contract_template_no between", value1, value2, "contractTemplateNo");
            return (Criteria) this;
        }

        public Criteria andContractTemplateNoNotBetween(String value1, String value2) {
            addCriterion("contract_template_no not between", value1, value2, "contractTemplateNo");
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