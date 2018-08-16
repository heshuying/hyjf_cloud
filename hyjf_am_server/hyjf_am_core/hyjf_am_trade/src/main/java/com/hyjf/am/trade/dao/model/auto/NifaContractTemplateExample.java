package com.hyjf.am.trade.dao.model.auto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NifaContractTemplateExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public NifaContractTemplateExample() {
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

        public Criteria andTempletNidIsNull() {
            addCriterion("templet_nid is null");
            return (Criteria) this;
        }

        public Criteria andTempletNidIsNotNull() {
            addCriterion("templet_nid is not null");
            return (Criteria) this;
        }

        public Criteria andTempletNidEqualTo(String value) {
            addCriterion("templet_nid =", value, "templetNid");
            return (Criteria) this;
        }

        public Criteria andTempletNidNotEqualTo(String value) {
            addCriterion("templet_nid <>", value, "templetNid");
            return (Criteria) this;
        }

        public Criteria andTempletNidGreaterThan(String value) {
            addCriterion("templet_nid >", value, "templetNid");
            return (Criteria) this;
        }

        public Criteria andTempletNidGreaterThanOrEqualTo(String value) {
            addCriterion("templet_nid >=", value, "templetNid");
            return (Criteria) this;
        }

        public Criteria andTempletNidLessThan(String value) {
            addCriterion("templet_nid <", value, "templetNid");
            return (Criteria) this;
        }

        public Criteria andTempletNidLessThanOrEqualTo(String value) {
            addCriterion("templet_nid <=", value, "templetNid");
            return (Criteria) this;
        }

        public Criteria andTempletNidLike(String value) {
            addCriterion("templet_nid like", value, "templetNid");
            return (Criteria) this;
        }

        public Criteria andTempletNidNotLike(String value) {
            addCriterion("templet_nid not like", value, "templetNid");
            return (Criteria) this;
        }

        public Criteria andTempletNidIn(List<String> values) {
            addCriterion("templet_nid in", values, "templetNid");
            return (Criteria) this;
        }

        public Criteria andTempletNidNotIn(List<String> values) {
            addCriterion("templet_nid not in", values, "templetNid");
            return (Criteria) this;
        }

        public Criteria andTempletNidBetween(String value1, String value2) {
            addCriterion("templet_nid between", value1, value2, "templetNid");
            return (Criteria) this;
        }

        public Criteria andTempletNidNotBetween(String value1, String value2) {
            addCriterion("templet_nid not between", value1, value2, "templetNid");
            return (Criteria) this;
        }

        public Criteria andNormalDefinitionIsNull() {
            addCriterion("normal_definition is null");
            return (Criteria) this;
        }

        public Criteria andNormalDefinitionIsNotNull() {
            addCriterion("normal_definition is not null");
            return (Criteria) this;
        }

        public Criteria andNormalDefinitionEqualTo(String value) {
            addCriterion("normal_definition =", value, "normalDefinition");
            return (Criteria) this;
        }

        public Criteria andNormalDefinitionNotEqualTo(String value) {
            addCriterion("normal_definition <>", value, "normalDefinition");
            return (Criteria) this;
        }

        public Criteria andNormalDefinitionGreaterThan(String value) {
            addCriterion("normal_definition >", value, "normalDefinition");
            return (Criteria) this;
        }

        public Criteria andNormalDefinitionGreaterThanOrEqualTo(String value) {
            addCriterion("normal_definition >=", value, "normalDefinition");
            return (Criteria) this;
        }

        public Criteria andNormalDefinitionLessThan(String value) {
            addCriterion("normal_definition <", value, "normalDefinition");
            return (Criteria) this;
        }

        public Criteria andNormalDefinitionLessThanOrEqualTo(String value) {
            addCriterion("normal_definition <=", value, "normalDefinition");
            return (Criteria) this;
        }

        public Criteria andNormalDefinitionLike(String value) {
            addCriterion("normal_definition like", value, "normalDefinition");
            return (Criteria) this;
        }

        public Criteria andNormalDefinitionNotLike(String value) {
            addCriterion("normal_definition not like", value, "normalDefinition");
            return (Criteria) this;
        }

        public Criteria andNormalDefinitionIn(List<String> values) {
            addCriterion("normal_definition in", values, "normalDefinition");
            return (Criteria) this;
        }

        public Criteria andNormalDefinitionNotIn(List<String> values) {
            addCriterion("normal_definition not in", values, "normalDefinition");
            return (Criteria) this;
        }

        public Criteria andNormalDefinitionBetween(String value1, String value2) {
            addCriterion("normal_definition between", value1, value2, "normalDefinition");
            return (Criteria) this;
        }

        public Criteria andNormalDefinitionNotBetween(String value1, String value2) {
            addCriterion("normal_definition not between", value1, value2, "normalDefinition");
            return (Criteria) this;
        }

        public Criteria andPrepaymentDefinitionIsNull() {
            addCriterion("prepayment_definition is null");
            return (Criteria) this;
        }

        public Criteria andPrepaymentDefinitionIsNotNull() {
            addCriterion("prepayment_definition is not null");
            return (Criteria) this;
        }

        public Criteria andPrepaymentDefinitionEqualTo(String value) {
            addCriterion("prepayment_definition =", value, "prepaymentDefinition");
            return (Criteria) this;
        }

        public Criteria andPrepaymentDefinitionNotEqualTo(String value) {
            addCriterion("prepayment_definition <>", value, "prepaymentDefinition");
            return (Criteria) this;
        }

        public Criteria andPrepaymentDefinitionGreaterThan(String value) {
            addCriterion("prepayment_definition >", value, "prepaymentDefinition");
            return (Criteria) this;
        }

        public Criteria andPrepaymentDefinitionGreaterThanOrEqualTo(String value) {
            addCriterion("prepayment_definition >=", value, "prepaymentDefinition");
            return (Criteria) this;
        }

        public Criteria andPrepaymentDefinitionLessThan(String value) {
            addCriterion("prepayment_definition <", value, "prepaymentDefinition");
            return (Criteria) this;
        }

        public Criteria andPrepaymentDefinitionLessThanOrEqualTo(String value) {
            addCriterion("prepayment_definition <=", value, "prepaymentDefinition");
            return (Criteria) this;
        }

        public Criteria andPrepaymentDefinitionLike(String value) {
            addCriterion("prepayment_definition like", value, "prepaymentDefinition");
            return (Criteria) this;
        }

        public Criteria andPrepaymentDefinitionNotLike(String value) {
            addCriterion("prepayment_definition not like", value, "prepaymentDefinition");
            return (Criteria) this;
        }

        public Criteria andPrepaymentDefinitionIn(List<String> values) {
            addCriterion("prepayment_definition in", values, "prepaymentDefinition");
            return (Criteria) this;
        }

        public Criteria andPrepaymentDefinitionNotIn(List<String> values) {
            addCriterion("prepayment_definition not in", values, "prepaymentDefinition");
            return (Criteria) this;
        }

        public Criteria andPrepaymentDefinitionBetween(String value1, String value2) {
            addCriterion("prepayment_definition between", value1, value2, "prepaymentDefinition");
            return (Criteria) this;
        }

        public Criteria andPrepaymentDefinitionNotBetween(String value1, String value2) {
            addCriterion("prepayment_definition not between", value1, value2, "prepaymentDefinition");
            return (Criteria) this;
        }

        public Criteria andBorrowerPromisesIsNull() {
            addCriterion("borrower_promises is null");
            return (Criteria) this;
        }

        public Criteria andBorrowerPromisesIsNotNull() {
            addCriterion("borrower_promises is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowerPromisesEqualTo(String value) {
            addCriterion("borrower_promises =", value, "borrowerPromises");
            return (Criteria) this;
        }

        public Criteria andBorrowerPromisesNotEqualTo(String value) {
            addCriterion("borrower_promises <>", value, "borrowerPromises");
            return (Criteria) this;
        }

        public Criteria andBorrowerPromisesGreaterThan(String value) {
            addCriterion("borrower_promises >", value, "borrowerPromises");
            return (Criteria) this;
        }

        public Criteria andBorrowerPromisesGreaterThanOrEqualTo(String value) {
            addCriterion("borrower_promises >=", value, "borrowerPromises");
            return (Criteria) this;
        }

        public Criteria andBorrowerPromisesLessThan(String value) {
            addCriterion("borrower_promises <", value, "borrowerPromises");
            return (Criteria) this;
        }

        public Criteria andBorrowerPromisesLessThanOrEqualTo(String value) {
            addCriterion("borrower_promises <=", value, "borrowerPromises");
            return (Criteria) this;
        }

        public Criteria andBorrowerPromisesLike(String value) {
            addCriterion("borrower_promises like", value, "borrowerPromises");
            return (Criteria) this;
        }

        public Criteria andBorrowerPromisesNotLike(String value) {
            addCriterion("borrower_promises not like", value, "borrowerPromises");
            return (Criteria) this;
        }

        public Criteria andBorrowerPromisesIn(List<String> values) {
            addCriterion("borrower_promises in", values, "borrowerPromises");
            return (Criteria) this;
        }

        public Criteria andBorrowerPromisesNotIn(List<String> values) {
            addCriterion("borrower_promises not in", values, "borrowerPromises");
            return (Criteria) this;
        }

        public Criteria andBorrowerPromisesBetween(String value1, String value2) {
            addCriterion("borrower_promises between", value1, value2, "borrowerPromises");
            return (Criteria) this;
        }

        public Criteria andBorrowerPromisesNotBetween(String value1, String value2) {
            addCriterion("borrower_promises not between", value1, value2, "borrowerPromises");
            return (Criteria) this;
        }

        public Criteria andLenderPromisesIsNull() {
            addCriterion("lender_promises is null");
            return (Criteria) this;
        }

        public Criteria andLenderPromisesIsNotNull() {
            addCriterion("lender_promises is not null");
            return (Criteria) this;
        }

        public Criteria andLenderPromisesEqualTo(String value) {
            addCriterion("lender_promises =", value, "lenderPromises");
            return (Criteria) this;
        }

        public Criteria andLenderPromisesNotEqualTo(String value) {
            addCriterion("lender_promises <>", value, "lenderPromises");
            return (Criteria) this;
        }

        public Criteria andLenderPromisesGreaterThan(String value) {
            addCriterion("lender_promises >", value, "lenderPromises");
            return (Criteria) this;
        }

        public Criteria andLenderPromisesGreaterThanOrEqualTo(String value) {
            addCriterion("lender_promises >=", value, "lenderPromises");
            return (Criteria) this;
        }

        public Criteria andLenderPromisesLessThan(String value) {
            addCriterion("lender_promises <", value, "lenderPromises");
            return (Criteria) this;
        }

        public Criteria andLenderPromisesLessThanOrEqualTo(String value) {
            addCriterion("lender_promises <=", value, "lenderPromises");
            return (Criteria) this;
        }

        public Criteria andLenderPromisesLike(String value) {
            addCriterion("lender_promises like", value, "lenderPromises");
            return (Criteria) this;
        }

        public Criteria andLenderPromisesNotLike(String value) {
            addCriterion("lender_promises not like", value, "lenderPromises");
            return (Criteria) this;
        }

        public Criteria andLenderPromisesIn(List<String> values) {
            addCriterion("lender_promises in", values, "lenderPromises");
            return (Criteria) this;
        }

        public Criteria andLenderPromisesNotIn(List<String> values) {
            addCriterion("lender_promises not in", values, "lenderPromises");
            return (Criteria) this;
        }

        public Criteria andLenderPromisesBetween(String value1, String value2) {
            addCriterion("lender_promises between", value1, value2, "lenderPromises");
            return (Criteria) this;
        }

        public Criteria andLenderPromisesNotBetween(String value1, String value2) {
            addCriterion("lender_promises not between", value1, value2, "lenderPromises");
            return (Criteria) this;
        }

        public Criteria andBorrowerObligationIsNull() {
            addCriterion("borrower_obligation is null");
            return (Criteria) this;
        }

        public Criteria andBorrowerObligationIsNotNull() {
            addCriterion("borrower_obligation is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowerObligationEqualTo(String value) {
            addCriterion("borrower_obligation =", value, "borrowerObligation");
            return (Criteria) this;
        }

        public Criteria andBorrowerObligationNotEqualTo(String value) {
            addCriterion("borrower_obligation <>", value, "borrowerObligation");
            return (Criteria) this;
        }

        public Criteria andBorrowerObligationGreaterThan(String value) {
            addCriterion("borrower_obligation >", value, "borrowerObligation");
            return (Criteria) this;
        }

        public Criteria andBorrowerObligationGreaterThanOrEqualTo(String value) {
            addCriterion("borrower_obligation >=", value, "borrowerObligation");
            return (Criteria) this;
        }

        public Criteria andBorrowerObligationLessThan(String value) {
            addCriterion("borrower_obligation <", value, "borrowerObligation");
            return (Criteria) this;
        }

        public Criteria andBorrowerObligationLessThanOrEqualTo(String value) {
            addCriterion("borrower_obligation <=", value, "borrowerObligation");
            return (Criteria) this;
        }

        public Criteria andBorrowerObligationLike(String value) {
            addCriterion("borrower_obligation like", value, "borrowerObligation");
            return (Criteria) this;
        }

        public Criteria andBorrowerObligationNotLike(String value) {
            addCriterion("borrower_obligation not like", value, "borrowerObligation");
            return (Criteria) this;
        }

        public Criteria andBorrowerObligationIn(List<String> values) {
            addCriterion("borrower_obligation in", values, "borrowerObligation");
            return (Criteria) this;
        }

        public Criteria andBorrowerObligationNotIn(List<String> values) {
            addCriterion("borrower_obligation not in", values, "borrowerObligation");
            return (Criteria) this;
        }

        public Criteria andBorrowerObligationBetween(String value1, String value2) {
            addCriterion("borrower_obligation between", value1, value2, "borrowerObligation");
            return (Criteria) this;
        }

        public Criteria andBorrowerObligationNotBetween(String value1, String value2) {
            addCriterion("borrower_obligation not between", value1, value2, "borrowerObligation");
            return (Criteria) this;
        }

        public Criteria andConfidentialityIsNull() {
            addCriterion("confidentiality is null");
            return (Criteria) this;
        }

        public Criteria andConfidentialityIsNotNull() {
            addCriterion("confidentiality is not null");
            return (Criteria) this;
        }

        public Criteria andConfidentialityEqualTo(String value) {
            addCriterion("confidentiality =", value, "confidentiality");
            return (Criteria) this;
        }

        public Criteria andConfidentialityNotEqualTo(String value) {
            addCriterion("confidentiality <>", value, "confidentiality");
            return (Criteria) this;
        }

        public Criteria andConfidentialityGreaterThan(String value) {
            addCriterion("confidentiality >", value, "confidentiality");
            return (Criteria) this;
        }

        public Criteria andConfidentialityGreaterThanOrEqualTo(String value) {
            addCriterion("confidentiality >=", value, "confidentiality");
            return (Criteria) this;
        }

        public Criteria andConfidentialityLessThan(String value) {
            addCriterion("confidentiality <", value, "confidentiality");
            return (Criteria) this;
        }

        public Criteria andConfidentialityLessThanOrEqualTo(String value) {
            addCriterion("confidentiality <=", value, "confidentiality");
            return (Criteria) this;
        }

        public Criteria andConfidentialityLike(String value) {
            addCriterion("confidentiality like", value, "confidentiality");
            return (Criteria) this;
        }

        public Criteria andConfidentialityNotLike(String value) {
            addCriterion("confidentiality not like", value, "confidentiality");
            return (Criteria) this;
        }

        public Criteria andConfidentialityIn(List<String> values) {
            addCriterion("confidentiality in", values, "confidentiality");
            return (Criteria) this;
        }

        public Criteria andConfidentialityNotIn(List<String> values) {
            addCriterion("confidentiality not in", values, "confidentiality");
            return (Criteria) this;
        }

        public Criteria andConfidentialityBetween(String value1, String value2) {
            addCriterion("confidentiality between", value1, value2, "confidentiality");
            return (Criteria) this;
        }

        public Criteria andConfidentialityNotBetween(String value1, String value2) {
            addCriterion("confidentiality not between", value1, value2, "confidentiality");
            return (Criteria) this;
        }

        public Criteria andBreachContractIsNull() {
            addCriterion("breach_contract is null");
            return (Criteria) this;
        }

        public Criteria andBreachContractIsNotNull() {
            addCriterion("breach_contract is not null");
            return (Criteria) this;
        }

        public Criteria andBreachContractEqualTo(String value) {
            addCriterion("breach_contract =", value, "breachContract");
            return (Criteria) this;
        }

        public Criteria andBreachContractNotEqualTo(String value) {
            addCriterion("breach_contract <>", value, "breachContract");
            return (Criteria) this;
        }

        public Criteria andBreachContractGreaterThan(String value) {
            addCriterion("breach_contract >", value, "breachContract");
            return (Criteria) this;
        }

        public Criteria andBreachContractGreaterThanOrEqualTo(String value) {
            addCriterion("breach_contract >=", value, "breachContract");
            return (Criteria) this;
        }

        public Criteria andBreachContractLessThan(String value) {
            addCriterion("breach_contract <", value, "breachContract");
            return (Criteria) this;
        }

        public Criteria andBreachContractLessThanOrEqualTo(String value) {
            addCriterion("breach_contract <=", value, "breachContract");
            return (Criteria) this;
        }

        public Criteria andBreachContractLike(String value) {
            addCriterion("breach_contract like", value, "breachContract");
            return (Criteria) this;
        }

        public Criteria andBreachContractNotLike(String value) {
            addCriterion("breach_contract not like", value, "breachContract");
            return (Criteria) this;
        }

        public Criteria andBreachContractIn(List<String> values) {
            addCriterion("breach_contract in", values, "breachContract");
            return (Criteria) this;
        }

        public Criteria andBreachContractNotIn(List<String> values) {
            addCriterion("breach_contract not in", values, "breachContract");
            return (Criteria) this;
        }

        public Criteria andBreachContractBetween(String value1, String value2) {
            addCriterion("breach_contract between", value1, value2, "breachContract");
            return (Criteria) this;
        }

        public Criteria andBreachContractNotBetween(String value1, String value2) {
            addCriterion("breach_contract not between", value1, value2, "breachContract");
            return (Criteria) this;
        }

        public Criteria andApplicableLawIsNull() {
            addCriterion("applicable_law is null");
            return (Criteria) this;
        }

        public Criteria andApplicableLawIsNotNull() {
            addCriterion("applicable_law is not null");
            return (Criteria) this;
        }

        public Criteria andApplicableLawEqualTo(String value) {
            addCriterion("applicable_law =", value, "applicableLaw");
            return (Criteria) this;
        }

        public Criteria andApplicableLawNotEqualTo(String value) {
            addCriterion("applicable_law <>", value, "applicableLaw");
            return (Criteria) this;
        }

        public Criteria andApplicableLawGreaterThan(String value) {
            addCriterion("applicable_law >", value, "applicableLaw");
            return (Criteria) this;
        }

        public Criteria andApplicableLawGreaterThanOrEqualTo(String value) {
            addCriterion("applicable_law >=", value, "applicableLaw");
            return (Criteria) this;
        }

        public Criteria andApplicableLawLessThan(String value) {
            addCriterion("applicable_law <", value, "applicableLaw");
            return (Criteria) this;
        }

        public Criteria andApplicableLawLessThanOrEqualTo(String value) {
            addCriterion("applicable_law <=", value, "applicableLaw");
            return (Criteria) this;
        }

        public Criteria andApplicableLawLike(String value) {
            addCriterion("applicable_law like", value, "applicableLaw");
            return (Criteria) this;
        }

        public Criteria andApplicableLawNotLike(String value) {
            addCriterion("applicable_law not like", value, "applicableLaw");
            return (Criteria) this;
        }

        public Criteria andApplicableLawIn(List<String> values) {
            addCriterion("applicable_law in", values, "applicableLaw");
            return (Criteria) this;
        }

        public Criteria andApplicableLawNotIn(List<String> values) {
            addCriterion("applicable_law not in", values, "applicableLaw");
            return (Criteria) this;
        }

        public Criteria andApplicableLawBetween(String value1, String value2) {
            addCriterion("applicable_law between", value1, value2, "applicableLaw");
            return (Criteria) this;
        }

        public Criteria andApplicableLawNotBetween(String value1, String value2) {
            addCriterion("applicable_law not between", value1, value2, "applicableLaw");
            return (Criteria) this;
        }

        public Criteria andDisputeResolutionIsNull() {
            addCriterion("dispute_resolution is null");
            return (Criteria) this;
        }

        public Criteria andDisputeResolutionIsNotNull() {
            addCriterion("dispute_resolution is not null");
            return (Criteria) this;
        }

        public Criteria andDisputeResolutionEqualTo(String value) {
            addCriterion("dispute_resolution =", value, "disputeResolution");
            return (Criteria) this;
        }

        public Criteria andDisputeResolutionNotEqualTo(String value) {
            addCriterion("dispute_resolution <>", value, "disputeResolution");
            return (Criteria) this;
        }

        public Criteria andDisputeResolutionGreaterThan(String value) {
            addCriterion("dispute_resolution >", value, "disputeResolution");
            return (Criteria) this;
        }

        public Criteria andDisputeResolutionGreaterThanOrEqualTo(String value) {
            addCriterion("dispute_resolution >=", value, "disputeResolution");
            return (Criteria) this;
        }

        public Criteria andDisputeResolutionLessThan(String value) {
            addCriterion("dispute_resolution <", value, "disputeResolution");
            return (Criteria) this;
        }

        public Criteria andDisputeResolutionLessThanOrEqualTo(String value) {
            addCriterion("dispute_resolution <=", value, "disputeResolution");
            return (Criteria) this;
        }

        public Criteria andDisputeResolutionLike(String value) {
            addCriterion("dispute_resolution like", value, "disputeResolution");
            return (Criteria) this;
        }

        public Criteria andDisputeResolutionNotLike(String value) {
            addCriterion("dispute_resolution not like", value, "disputeResolution");
            return (Criteria) this;
        }

        public Criteria andDisputeResolutionIn(List<String> values) {
            addCriterion("dispute_resolution in", values, "disputeResolution");
            return (Criteria) this;
        }

        public Criteria andDisputeResolutionNotIn(List<String> values) {
            addCriterion("dispute_resolution not in", values, "disputeResolution");
            return (Criteria) this;
        }

        public Criteria andDisputeResolutionBetween(String value1, String value2) {
            addCriterion("dispute_resolution between", value1, value2, "disputeResolution");
            return (Criteria) this;
        }

        public Criteria andDisputeResolutionNotBetween(String value1, String value2) {
            addCriterion("dispute_resolution not between", value1, value2, "disputeResolution");
            return (Criteria) this;
        }

        public Criteria andOtherConditionsIsNull() {
            addCriterion("other_conditions is null");
            return (Criteria) this;
        }

        public Criteria andOtherConditionsIsNotNull() {
            addCriterion("other_conditions is not null");
            return (Criteria) this;
        }

        public Criteria andOtherConditionsEqualTo(String value) {
            addCriterion("other_conditions =", value, "otherConditions");
            return (Criteria) this;
        }

        public Criteria andOtherConditionsNotEqualTo(String value) {
            addCriterion("other_conditions <>", value, "otherConditions");
            return (Criteria) this;
        }

        public Criteria andOtherConditionsGreaterThan(String value) {
            addCriterion("other_conditions >", value, "otherConditions");
            return (Criteria) this;
        }

        public Criteria andOtherConditionsGreaterThanOrEqualTo(String value) {
            addCriterion("other_conditions >=", value, "otherConditions");
            return (Criteria) this;
        }

        public Criteria andOtherConditionsLessThan(String value) {
            addCriterion("other_conditions <", value, "otherConditions");
            return (Criteria) this;
        }

        public Criteria andOtherConditionsLessThanOrEqualTo(String value) {
            addCriterion("other_conditions <=", value, "otherConditions");
            return (Criteria) this;
        }

        public Criteria andOtherConditionsLike(String value) {
            addCriterion("other_conditions like", value, "otherConditions");
            return (Criteria) this;
        }

        public Criteria andOtherConditionsNotLike(String value) {
            addCriterion("other_conditions not like", value, "otherConditions");
            return (Criteria) this;
        }

        public Criteria andOtherConditionsIn(List<String> values) {
            addCriterion("other_conditions in", values, "otherConditions");
            return (Criteria) this;
        }

        public Criteria andOtherConditionsNotIn(List<String> values) {
            addCriterion("other_conditions not in", values, "otherConditions");
            return (Criteria) this;
        }

        public Criteria andOtherConditionsBetween(String value1, String value2) {
            addCriterion("other_conditions between", value1, value2, "otherConditions");
            return (Criteria) this;
        }

        public Criteria andOtherConditionsNotBetween(String value1, String value2) {
            addCriterion("other_conditions not between", value1, value2, "otherConditions");
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