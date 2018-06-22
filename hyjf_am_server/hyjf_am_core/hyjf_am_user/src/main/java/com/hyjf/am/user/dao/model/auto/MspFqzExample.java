package com.hyjf.am.user.dao.model.auto;

import java.util.ArrayList;
import java.util.List;

public class MspFqzExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public MspFqzExample() {
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

        public Criteria andApplyIdIsNull() {
            addCriterion("apply_id is null");
            return (Criteria) this;
        }

        public Criteria andApplyIdIsNotNull() {
            addCriterion("apply_id is not null");
            return (Criteria) this;
        }

        public Criteria andApplyIdEqualTo(String value) {
            addCriterion("apply_id =", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdNotEqualTo(String value) {
            addCriterion("apply_id <>", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdGreaterThan(String value) {
            addCriterion("apply_id >", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdGreaterThanOrEqualTo(String value) {
            addCriterion("apply_id >=", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdLessThan(String value) {
            addCriterion("apply_id <", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdLessThanOrEqualTo(String value) {
            addCriterion("apply_id <=", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdLike(String value) {
            addCriterion("apply_id like", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdNotLike(String value) {
            addCriterion("apply_id not like", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdIn(List<String> values) {
            addCriterion("apply_id in", values, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdNotIn(List<String> values) {
            addCriterion("apply_id not in", values, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdBetween(String value1, String value2) {
            addCriterion("apply_id between", value1, value2, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdNotBetween(String value1, String value2) {
            addCriterion("apply_id not between", value1, value2, "applyId");
            return (Criteria) this;
        }

        public Criteria andIdentityauthIsNull() {
            addCriterion("identityAuth is null");
            return (Criteria) this;
        }

        public Criteria andIdentityauthIsNotNull() {
            addCriterion("identityAuth is not null");
            return (Criteria) this;
        }

        public Criteria andIdentityauthEqualTo(String value) {
            addCriterion("identityAuth =", value, "identityauth");
            return (Criteria) this;
        }

        public Criteria andIdentityauthNotEqualTo(String value) {
            addCriterion("identityAuth <>", value, "identityauth");
            return (Criteria) this;
        }

        public Criteria andIdentityauthGreaterThan(String value) {
            addCriterion("identityAuth >", value, "identityauth");
            return (Criteria) this;
        }

        public Criteria andIdentityauthGreaterThanOrEqualTo(String value) {
            addCriterion("identityAuth >=", value, "identityauth");
            return (Criteria) this;
        }

        public Criteria andIdentityauthLessThan(String value) {
            addCriterion("identityAuth <", value, "identityauth");
            return (Criteria) this;
        }

        public Criteria andIdentityauthLessThanOrEqualTo(String value) {
            addCriterion("identityAuth <=", value, "identityauth");
            return (Criteria) this;
        }

        public Criteria andIdentityauthLike(String value) {
            addCriterion("identityAuth like", value, "identityauth");
            return (Criteria) this;
        }

        public Criteria andIdentityauthNotLike(String value) {
            addCriterion("identityAuth not like", value, "identityauth");
            return (Criteria) this;
        }

        public Criteria andIdentityauthIn(List<String> values) {
            addCriterion("identityAuth in", values, "identityauth");
            return (Criteria) this;
        }

        public Criteria andIdentityauthNotIn(List<String> values) {
            addCriterion("identityAuth not in", values, "identityauth");
            return (Criteria) this;
        }

        public Criteria andIdentityauthBetween(String value1, String value2) {
            addCriterion("identityAuth between", value1, value2, "identityauth");
            return (Criteria) this;
        }

        public Criteria andIdentityauthNotBetween(String value1, String value2) {
            addCriterion("identityAuth not between", value1, value2, "identityauth");
            return (Criteria) this;
        }

        public Criteria andIdentityandphotoauthIsNull() {
            addCriterion("identityAndPhotoAuth is null");
            return (Criteria) this;
        }

        public Criteria andIdentityandphotoauthIsNotNull() {
            addCriterion("identityAndPhotoAuth is not null");
            return (Criteria) this;
        }

        public Criteria andIdentityandphotoauthEqualTo(String value) {
            addCriterion("identityAndPhotoAuth =", value, "identityandphotoauth");
            return (Criteria) this;
        }

        public Criteria andIdentityandphotoauthNotEqualTo(String value) {
            addCriterion("identityAndPhotoAuth <>", value, "identityandphotoauth");
            return (Criteria) this;
        }

        public Criteria andIdentityandphotoauthGreaterThan(String value) {
            addCriterion("identityAndPhotoAuth >", value, "identityandphotoauth");
            return (Criteria) this;
        }

        public Criteria andIdentityandphotoauthGreaterThanOrEqualTo(String value) {
            addCriterion("identityAndPhotoAuth >=", value, "identityandphotoauth");
            return (Criteria) this;
        }

        public Criteria andIdentityandphotoauthLessThan(String value) {
            addCriterion("identityAndPhotoAuth <", value, "identityandphotoauth");
            return (Criteria) this;
        }

        public Criteria andIdentityandphotoauthLessThanOrEqualTo(String value) {
            addCriterion("identityAndPhotoAuth <=", value, "identityandphotoauth");
            return (Criteria) this;
        }

        public Criteria andIdentityandphotoauthLike(String value) {
            addCriterion("identityAndPhotoAuth like", value, "identityandphotoauth");
            return (Criteria) this;
        }

        public Criteria andIdentityandphotoauthNotLike(String value) {
            addCriterion("identityAndPhotoAuth not like", value, "identityandphotoauth");
            return (Criteria) this;
        }

        public Criteria andIdentityandphotoauthIn(List<String> values) {
            addCriterion("identityAndPhotoAuth in", values, "identityandphotoauth");
            return (Criteria) this;
        }

        public Criteria andIdentityandphotoauthNotIn(List<String> values) {
            addCriterion("identityAndPhotoAuth not in", values, "identityandphotoauth");
            return (Criteria) this;
        }

        public Criteria andIdentityandphotoauthBetween(String value1, String value2) {
            addCriterion("identityAndPhotoAuth between", value1, value2, "identityandphotoauth");
            return (Criteria) this;
        }

        public Criteria andIdentityandphotoauthNotBetween(String value1, String value2) {
            addCriterion("identityAndPhotoAuth not between", value1, value2, "identityandphotoauth");
            return (Criteria) this;
        }

        public Criteria andNamewithcardauthIsNull() {
            addCriterion("nameWithCardAuth is null");
            return (Criteria) this;
        }

        public Criteria andNamewithcardauthIsNotNull() {
            addCriterion("nameWithCardAuth is not null");
            return (Criteria) this;
        }

        public Criteria andNamewithcardauthEqualTo(String value) {
            addCriterion("nameWithCardAuth =", value, "namewithcardauth");
            return (Criteria) this;
        }

        public Criteria andNamewithcardauthNotEqualTo(String value) {
            addCriterion("nameWithCardAuth <>", value, "namewithcardauth");
            return (Criteria) this;
        }

        public Criteria andNamewithcardauthGreaterThan(String value) {
            addCriterion("nameWithCardAuth >", value, "namewithcardauth");
            return (Criteria) this;
        }

        public Criteria andNamewithcardauthGreaterThanOrEqualTo(String value) {
            addCriterion("nameWithCardAuth >=", value, "namewithcardauth");
            return (Criteria) this;
        }

        public Criteria andNamewithcardauthLessThan(String value) {
            addCriterion("nameWithCardAuth <", value, "namewithcardauth");
            return (Criteria) this;
        }

        public Criteria andNamewithcardauthLessThanOrEqualTo(String value) {
            addCriterion("nameWithCardAuth <=", value, "namewithcardauth");
            return (Criteria) this;
        }

        public Criteria andNamewithcardauthLike(String value) {
            addCriterion("nameWithCardAuth like", value, "namewithcardauth");
            return (Criteria) this;
        }

        public Criteria andNamewithcardauthNotLike(String value) {
            addCriterion("nameWithCardAuth not like", value, "namewithcardauth");
            return (Criteria) this;
        }

        public Criteria andNamewithcardauthIn(List<String> values) {
            addCriterion("nameWithCardAuth in", values, "namewithcardauth");
            return (Criteria) this;
        }

        public Criteria andNamewithcardauthNotIn(List<String> values) {
            addCriterion("nameWithCardAuth not in", values, "namewithcardauth");
            return (Criteria) this;
        }

        public Criteria andNamewithcardauthBetween(String value1, String value2) {
            addCriterion("nameWithCardAuth between", value1, value2, "namewithcardauth");
            return (Criteria) this;
        }

        public Criteria andNamewithcardauthNotBetween(String value1, String value2) {
            addCriterion("nameWithCardAuth not between", value1, value2, "namewithcardauth");
            return (Criteria) this;
        }

        public Criteria andNameidentitycardauthIsNull() {
            addCriterion("nameIdentityCardAuth is null");
            return (Criteria) this;
        }

        public Criteria andNameidentitycardauthIsNotNull() {
            addCriterion("nameIdentityCardAuth is not null");
            return (Criteria) this;
        }

        public Criteria andNameidentitycardauthEqualTo(String value) {
            addCriterion("nameIdentityCardAuth =", value, "nameidentitycardauth");
            return (Criteria) this;
        }

        public Criteria andNameidentitycardauthNotEqualTo(String value) {
            addCriterion("nameIdentityCardAuth <>", value, "nameidentitycardauth");
            return (Criteria) this;
        }

        public Criteria andNameidentitycardauthGreaterThan(String value) {
            addCriterion("nameIdentityCardAuth >", value, "nameidentitycardauth");
            return (Criteria) this;
        }

        public Criteria andNameidentitycardauthGreaterThanOrEqualTo(String value) {
            addCriterion("nameIdentityCardAuth >=", value, "nameidentitycardauth");
            return (Criteria) this;
        }

        public Criteria andNameidentitycardauthLessThan(String value) {
            addCriterion("nameIdentityCardAuth <", value, "nameidentitycardauth");
            return (Criteria) this;
        }

        public Criteria andNameidentitycardauthLessThanOrEqualTo(String value) {
            addCriterion("nameIdentityCardAuth <=", value, "nameidentitycardauth");
            return (Criteria) this;
        }

        public Criteria andNameidentitycardauthLike(String value) {
            addCriterion("nameIdentityCardAuth like", value, "nameidentitycardauth");
            return (Criteria) this;
        }

        public Criteria andNameidentitycardauthNotLike(String value) {
            addCriterion("nameIdentityCardAuth not like", value, "nameidentitycardauth");
            return (Criteria) this;
        }

        public Criteria andNameidentitycardauthIn(List<String> values) {
            addCriterion("nameIdentityCardAuth in", values, "nameidentitycardauth");
            return (Criteria) this;
        }

        public Criteria andNameidentitycardauthNotIn(List<String> values) {
            addCriterion("nameIdentityCardAuth not in", values, "nameidentitycardauth");
            return (Criteria) this;
        }

        public Criteria andNameidentitycardauthBetween(String value1, String value2) {
            addCriterion("nameIdentityCardAuth between", value1, value2, "nameidentitycardauth");
            return (Criteria) this;
        }

        public Criteria andNameidentitycardauthNotBetween(String value1, String value2) {
            addCriterion("nameIdentityCardAuth not between", value1, value2, "nameidentitycardauth");
            return (Criteria) this;
        }

        public Criteria andDegreecodeIsNull() {
            addCriterion("degreeCode is null");
            return (Criteria) this;
        }

        public Criteria andDegreecodeIsNotNull() {
            addCriterion("degreeCode is not null");
            return (Criteria) this;
        }

        public Criteria andDegreecodeEqualTo(String value) {
            addCriterion("degreeCode =", value, "degreecode");
            return (Criteria) this;
        }

        public Criteria andDegreecodeNotEqualTo(String value) {
            addCriterion("degreeCode <>", value, "degreecode");
            return (Criteria) this;
        }

        public Criteria andDegreecodeGreaterThan(String value) {
            addCriterion("degreeCode >", value, "degreecode");
            return (Criteria) this;
        }

        public Criteria andDegreecodeGreaterThanOrEqualTo(String value) {
            addCriterion("degreeCode >=", value, "degreecode");
            return (Criteria) this;
        }

        public Criteria andDegreecodeLessThan(String value) {
            addCriterion("degreeCode <", value, "degreecode");
            return (Criteria) this;
        }

        public Criteria andDegreecodeLessThanOrEqualTo(String value) {
            addCriterion("degreeCode <=", value, "degreecode");
            return (Criteria) this;
        }

        public Criteria andDegreecodeLike(String value) {
            addCriterion("degreeCode like", value, "degreecode");
            return (Criteria) this;
        }

        public Criteria andDegreecodeNotLike(String value) {
            addCriterion("degreeCode not like", value, "degreecode");
            return (Criteria) this;
        }

        public Criteria andDegreecodeIn(List<String> values) {
            addCriterion("degreeCode in", values, "degreecode");
            return (Criteria) this;
        }

        public Criteria andDegreecodeNotIn(List<String> values) {
            addCriterion("degreeCode not in", values, "degreecode");
            return (Criteria) this;
        }

        public Criteria andDegreecodeBetween(String value1, String value2) {
            addCriterion("degreeCode between", value1, value2, "degreecode");
            return (Criteria) this;
        }

        public Criteria andDegreecodeNotBetween(String value1, String value2) {
            addCriterion("degreeCode not between", value1, value2, "degreecode");
            return (Criteria) this;
        }

        public Criteria andMspapplyIsNull() {
            addCriterion("mspApply is null");
            return (Criteria) this;
        }

        public Criteria andMspapplyIsNotNull() {
            addCriterion("mspApply is not null");
            return (Criteria) this;
        }

        public Criteria andMspapplyEqualTo(String value) {
            addCriterion("mspApply =", value, "mspapply");
            return (Criteria) this;
        }

        public Criteria andMspapplyNotEqualTo(String value) {
            addCriterion("mspApply <>", value, "mspapply");
            return (Criteria) this;
        }

        public Criteria andMspapplyGreaterThan(String value) {
            addCriterion("mspApply >", value, "mspapply");
            return (Criteria) this;
        }

        public Criteria andMspapplyGreaterThanOrEqualTo(String value) {
            addCriterion("mspApply >=", value, "mspapply");
            return (Criteria) this;
        }

        public Criteria andMspapplyLessThan(String value) {
            addCriterion("mspApply <", value, "mspapply");
            return (Criteria) this;
        }

        public Criteria andMspapplyLessThanOrEqualTo(String value) {
            addCriterion("mspApply <=", value, "mspapply");
            return (Criteria) this;
        }

        public Criteria andMspapplyLike(String value) {
            addCriterion("mspApply like", value, "mspapply");
            return (Criteria) this;
        }

        public Criteria andMspapplyNotLike(String value) {
            addCriterion("mspApply not like", value, "mspapply");
            return (Criteria) this;
        }

        public Criteria andMspapplyIn(List<String> values) {
            addCriterion("mspApply in", values, "mspapply");
            return (Criteria) this;
        }

        public Criteria andMspapplyNotIn(List<String> values) {
            addCriterion("mspApply not in", values, "mspapply");
            return (Criteria) this;
        }

        public Criteria andMspapplyBetween(String value1, String value2) {
            addCriterion("mspApply between", value1, value2, "mspapply");
            return (Criteria) this;
        }

        public Criteria andMspapplyNotBetween(String value1, String value2) {
            addCriterion("mspApply not between", value1, value2, "mspapply");
            return (Criteria) this;
        }

        public Criteria andMspcontractIsNull() {
            addCriterion("mspContract is null");
            return (Criteria) this;
        }

        public Criteria andMspcontractIsNotNull() {
            addCriterion("mspContract is not null");
            return (Criteria) this;
        }

        public Criteria andMspcontractEqualTo(String value) {
            addCriterion("mspContract =", value, "mspcontract");
            return (Criteria) this;
        }

        public Criteria andMspcontractNotEqualTo(String value) {
            addCriterion("mspContract <>", value, "mspcontract");
            return (Criteria) this;
        }

        public Criteria andMspcontractGreaterThan(String value) {
            addCriterion("mspContract >", value, "mspcontract");
            return (Criteria) this;
        }

        public Criteria andMspcontractGreaterThanOrEqualTo(String value) {
            addCriterion("mspContract >=", value, "mspcontract");
            return (Criteria) this;
        }

        public Criteria andMspcontractLessThan(String value) {
            addCriterion("mspContract <", value, "mspcontract");
            return (Criteria) this;
        }

        public Criteria andMspcontractLessThanOrEqualTo(String value) {
            addCriterion("mspContract <=", value, "mspcontract");
            return (Criteria) this;
        }

        public Criteria andMspcontractLike(String value) {
            addCriterion("mspContract like", value, "mspcontract");
            return (Criteria) this;
        }

        public Criteria andMspcontractNotLike(String value) {
            addCriterion("mspContract not like", value, "mspcontract");
            return (Criteria) this;
        }

        public Criteria andMspcontractIn(List<String> values) {
            addCriterion("mspContract in", values, "mspcontract");
            return (Criteria) this;
        }

        public Criteria andMspcontractNotIn(List<String> values) {
            addCriterion("mspContract not in", values, "mspcontract");
            return (Criteria) this;
        }

        public Criteria andMspcontractBetween(String value1, String value2) {
            addCriterion("mspContract between", value1, value2, "mspcontract");
            return (Criteria) this;
        }

        public Criteria andMspcontractNotBetween(String value1, String value2) {
            addCriterion("mspContract not between", value1, value2, "mspcontract");
            return (Criteria) this;
        }

        public Criteria andMspendcontractIsNull() {
            addCriterion("mspEndContract is null");
            return (Criteria) this;
        }

        public Criteria andMspendcontractIsNotNull() {
            addCriterion("mspEndContract is not null");
            return (Criteria) this;
        }

        public Criteria andMspendcontractEqualTo(String value) {
            addCriterion("mspEndContract =", value, "mspendcontract");
            return (Criteria) this;
        }

        public Criteria andMspendcontractNotEqualTo(String value) {
            addCriterion("mspEndContract <>", value, "mspendcontract");
            return (Criteria) this;
        }

        public Criteria andMspendcontractGreaterThan(String value) {
            addCriterion("mspEndContract >", value, "mspendcontract");
            return (Criteria) this;
        }

        public Criteria andMspendcontractGreaterThanOrEqualTo(String value) {
            addCriterion("mspEndContract >=", value, "mspendcontract");
            return (Criteria) this;
        }

        public Criteria andMspendcontractLessThan(String value) {
            addCriterion("mspEndContract <", value, "mspendcontract");
            return (Criteria) this;
        }

        public Criteria andMspendcontractLessThanOrEqualTo(String value) {
            addCriterion("mspEndContract <=", value, "mspendcontract");
            return (Criteria) this;
        }

        public Criteria andMspendcontractLike(String value) {
            addCriterion("mspEndContract like", value, "mspendcontract");
            return (Criteria) this;
        }

        public Criteria andMspendcontractNotLike(String value) {
            addCriterion("mspEndContract not like", value, "mspendcontract");
            return (Criteria) this;
        }

        public Criteria andMspendcontractIn(List<String> values) {
            addCriterion("mspEndContract in", values, "mspendcontract");
            return (Criteria) this;
        }

        public Criteria andMspendcontractNotIn(List<String> values) {
            addCriterion("mspEndContract not in", values, "mspendcontract");
            return (Criteria) this;
        }

        public Criteria andMspendcontractBetween(String value1, String value2) {
            addCriterion("mspEndContract between", value1, value2, "mspendcontract");
            return (Criteria) this;
        }

        public Criteria andMspendcontractNotBetween(String value1, String value2) {
            addCriterion("mspEndContract not between", value1, value2, "mspendcontract");
            return (Criteria) this;
        }

        public Criteria andMspblacklistIsNull() {
            addCriterion("mspBlacklist is null");
            return (Criteria) this;
        }

        public Criteria andMspblacklistIsNotNull() {
            addCriterion("mspBlacklist is not null");
            return (Criteria) this;
        }

        public Criteria andMspblacklistEqualTo(String value) {
            addCriterion("mspBlacklist =", value, "mspblacklist");
            return (Criteria) this;
        }

        public Criteria andMspblacklistNotEqualTo(String value) {
            addCriterion("mspBlacklist <>", value, "mspblacklist");
            return (Criteria) this;
        }

        public Criteria andMspblacklistGreaterThan(String value) {
            addCriterion("mspBlacklist >", value, "mspblacklist");
            return (Criteria) this;
        }

        public Criteria andMspblacklistGreaterThanOrEqualTo(String value) {
            addCriterion("mspBlacklist >=", value, "mspblacklist");
            return (Criteria) this;
        }

        public Criteria andMspblacklistLessThan(String value) {
            addCriterion("mspBlacklist <", value, "mspblacklist");
            return (Criteria) this;
        }

        public Criteria andMspblacklistLessThanOrEqualTo(String value) {
            addCriterion("mspBlacklist <=", value, "mspblacklist");
            return (Criteria) this;
        }

        public Criteria andMspblacklistLike(String value) {
            addCriterion("mspBlacklist like", value, "mspblacklist");
            return (Criteria) this;
        }

        public Criteria andMspblacklistNotLike(String value) {
            addCriterion("mspBlacklist not like", value, "mspblacklist");
            return (Criteria) this;
        }

        public Criteria andMspblacklistIn(List<String> values) {
            addCriterion("mspBlacklist in", values, "mspblacklist");
            return (Criteria) this;
        }

        public Criteria andMspblacklistNotIn(List<String> values) {
            addCriterion("mspBlacklist not in", values, "mspblacklist");
            return (Criteria) this;
        }

        public Criteria andMspblacklistBetween(String value1, String value2) {
            addCriterion("mspBlacklist between", value1, value2, "mspblacklist");
            return (Criteria) this;
        }

        public Criteria andMspblacklistNotBetween(String value1, String value2) {
            addCriterion("mspBlacklist not between", value1, value2, "mspblacklist");
            return (Criteria) this;
        }

        public Criteria andMspscoreIsNull() {
            addCriterion("mspScore is null");
            return (Criteria) this;
        }

        public Criteria andMspscoreIsNotNull() {
            addCriterion("mspScore is not null");
            return (Criteria) this;
        }

        public Criteria andMspscoreEqualTo(String value) {
            addCriterion("mspScore =", value, "mspscore");
            return (Criteria) this;
        }

        public Criteria andMspscoreNotEqualTo(String value) {
            addCriterion("mspScore <>", value, "mspscore");
            return (Criteria) this;
        }

        public Criteria andMspscoreGreaterThan(String value) {
            addCriterion("mspScore >", value, "mspscore");
            return (Criteria) this;
        }

        public Criteria andMspscoreGreaterThanOrEqualTo(String value) {
            addCriterion("mspScore >=", value, "mspscore");
            return (Criteria) this;
        }

        public Criteria andMspscoreLessThan(String value) {
            addCriterion("mspScore <", value, "mspscore");
            return (Criteria) this;
        }

        public Criteria andMspscoreLessThanOrEqualTo(String value) {
            addCriterion("mspScore <=", value, "mspscore");
            return (Criteria) this;
        }

        public Criteria andMspscoreLike(String value) {
            addCriterion("mspScore like", value, "mspscore");
            return (Criteria) this;
        }

        public Criteria andMspscoreNotLike(String value) {
            addCriterion("mspScore not like", value, "mspscore");
            return (Criteria) this;
        }

        public Criteria andMspscoreIn(List<String> values) {
            addCriterion("mspScore in", values, "mspscore");
            return (Criteria) this;
        }

        public Criteria andMspscoreNotIn(List<String> values) {
            addCriterion("mspScore not in", values, "mspscore");
            return (Criteria) this;
        }

        public Criteria andMspscoreBetween(String value1, String value2) {
            addCriterion("mspScore between", value1, value2, "mspscore");
            return (Criteria) this;
        }

        public Criteria andMspscoreNotBetween(String value1, String value2) {
            addCriterion("mspScore not between", value1, value2, "mspscore");
            return (Criteria) this;
        }

        public Criteria andCountriskblackIsNull() {
            addCriterion("countRiskBlack is null");
            return (Criteria) this;
        }

        public Criteria andCountriskblackIsNotNull() {
            addCriterion("countRiskBlack is not null");
            return (Criteria) this;
        }

        public Criteria andCountriskblackEqualTo(String value) {
            addCriterion("countRiskBlack =", value, "countriskblack");
            return (Criteria) this;
        }

        public Criteria andCountriskblackNotEqualTo(String value) {
            addCriterion("countRiskBlack <>", value, "countriskblack");
            return (Criteria) this;
        }

        public Criteria andCountriskblackGreaterThan(String value) {
            addCriterion("countRiskBlack >", value, "countriskblack");
            return (Criteria) this;
        }

        public Criteria andCountriskblackGreaterThanOrEqualTo(String value) {
            addCriterion("countRiskBlack >=", value, "countriskblack");
            return (Criteria) this;
        }

        public Criteria andCountriskblackLessThan(String value) {
            addCriterion("countRiskBlack <", value, "countriskblack");
            return (Criteria) this;
        }

        public Criteria andCountriskblackLessThanOrEqualTo(String value) {
            addCriterion("countRiskBlack <=", value, "countriskblack");
            return (Criteria) this;
        }

        public Criteria andCountriskblackLike(String value) {
            addCriterion("countRiskBlack like", value, "countriskblack");
            return (Criteria) this;
        }

        public Criteria andCountriskblackNotLike(String value) {
            addCriterion("countRiskBlack not like", value, "countriskblack");
            return (Criteria) this;
        }

        public Criteria andCountriskblackIn(List<String> values) {
            addCriterion("countRiskBlack in", values, "countriskblack");
            return (Criteria) this;
        }

        public Criteria andCountriskblackNotIn(List<String> values) {
            addCriterion("countRiskBlack not in", values, "countriskblack");
            return (Criteria) this;
        }

        public Criteria andCountriskblackBetween(String value1, String value2) {
            addCriterion("countRiskBlack between", value1, value2, "countriskblack");
            return (Criteria) this;
        }

        public Criteria andCountriskblackNotBetween(String value1, String value2) {
            addCriterion("countRiskBlack not between", value1, value2, "countriskblack");
            return (Criteria) this;
        }

        public Criteria andMspblackIsNull() {
            addCriterion("mspBlack is null");
            return (Criteria) this;
        }

        public Criteria andMspblackIsNotNull() {
            addCriterion("mspBlack is not null");
            return (Criteria) this;
        }

        public Criteria andMspblackEqualTo(String value) {
            addCriterion("mspBlack =", value, "mspblack");
            return (Criteria) this;
        }

        public Criteria andMspblackNotEqualTo(String value) {
            addCriterion("mspBlack <>", value, "mspblack");
            return (Criteria) this;
        }

        public Criteria andMspblackGreaterThan(String value) {
            addCriterion("mspBlack >", value, "mspblack");
            return (Criteria) this;
        }

        public Criteria andMspblackGreaterThanOrEqualTo(String value) {
            addCriterion("mspBlack >=", value, "mspblack");
            return (Criteria) this;
        }

        public Criteria andMspblackLessThan(String value) {
            addCriterion("mspBlack <", value, "mspblack");
            return (Criteria) this;
        }

        public Criteria andMspblackLessThanOrEqualTo(String value) {
            addCriterion("mspBlack <=", value, "mspblack");
            return (Criteria) this;
        }

        public Criteria andMspblackLike(String value) {
            addCriterion("mspBlack like", value, "mspblack");
            return (Criteria) this;
        }

        public Criteria andMspblackNotLike(String value) {
            addCriterion("mspBlack not like", value, "mspblack");
            return (Criteria) this;
        }

        public Criteria andMspblackIn(List<String> values) {
            addCriterion("mspBlack in", values, "mspblack");
            return (Criteria) this;
        }

        public Criteria andMspblackNotIn(List<String> values) {
            addCriterion("mspBlack not in", values, "mspblack");
            return (Criteria) this;
        }

        public Criteria andMspblackBetween(String value1, String value2) {
            addCriterion("mspBlack between", value1, value2, "mspblack");
            return (Criteria) this;
        }

        public Criteria andMspblackNotBetween(String value1, String value2) {
            addCriterion("mspBlack not between", value1, value2, "mspblack");
            return (Criteria) this;
        }

        public Criteria andInternetblackIsNull() {
            addCriterion("internetBlack is null");
            return (Criteria) this;
        }

        public Criteria andInternetblackIsNotNull() {
            addCriterion("internetBlack is not null");
            return (Criteria) this;
        }

        public Criteria andInternetblackEqualTo(String value) {
            addCriterion("internetBlack =", value, "internetblack");
            return (Criteria) this;
        }

        public Criteria andInternetblackNotEqualTo(String value) {
            addCriterion("internetBlack <>", value, "internetblack");
            return (Criteria) this;
        }

        public Criteria andInternetblackGreaterThan(String value) {
            addCriterion("internetBlack >", value, "internetblack");
            return (Criteria) this;
        }

        public Criteria andInternetblackGreaterThanOrEqualTo(String value) {
            addCriterion("internetBlack >=", value, "internetblack");
            return (Criteria) this;
        }

        public Criteria andInternetblackLessThan(String value) {
            addCriterion("internetBlack <", value, "internetblack");
            return (Criteria) this;
        }

        public Criteria andInternetblackLessThanOrEqualTo(String value) {
            addCriterion("internetBlack <=", value, "internetblack");
            return (Criteria) this;
        }

        public Criteria andInternetblackLike(String value) {
            addCriterion("internetBlack like", value, "internetblack");
            return (Criteria) this;
        }

        public Criteria andInternetblackNotLike(String value) {
            addCriterion("internetBlack not like", value, "internetblack");
            return (Criteria) this;
        }

        public Criteria andInternetblackIn(List<String> values) {
            addCriterion("internetBlack in", values, "internetblack");
            return (Criteria) this;
        }

        public Criteria andInternetblackNotIn(List<String> values) {
            addCriterion("internetBlack not in", values, "internetblack");
            return (Criteria) this;
        }

        public Criteria andInternetblackBetween(String value1, String value2) {
            addCriterion("internetBlack between", value1, value2, "internetblack");
            return (Criteria) this;
        }

        public Criteria andInternetblackNotBetween(String value1, String value2) {
            addCriterion("internetBlack not between", value1, value2, "internetblack");
            return (Criteria) this;
        }

        public Criteria andAllwinblackIsNull() {
            addCriterion("allwinBlack is null");
            return (Criteria) this;
        }

        public Criteria andAllwinblackIsNotNull() {
            addCriterion("allwinBlack is not null");
            return (Criteria) this;
        }

        public Criteria andAllwinblackEqualTo(String value) {
            addCriterion("allwinBlack =", value, "allwinblack");
            return (Criteria) this;
        }

        public Criteria andAllwinblackNotEqualTo(String value) {
            addCriterion("allwinBlack <>", value, "allwinblack");
            return (Criteria) this;
        }

        public Criteria andAllwinblackGreaterThan(String value) {
            addCriterion("allwinBlack >", value, "allwinblack");
            return (Criteria) this;
        }

        public Criteria andAllwinblackGreaterThanOrEqualTo(String value) {
            addCriterion("allwinBlack >=", value, "allwinblack");
            return (Criteria) this;
        }

        public Criteria andAllwinblackLessThan(String value) {
            addCriterion("allwinBlack <", value, "allwinblack");
            return (Criteria) this;
        }

        public Criteria andAllwinblackLessThanOrEqualTo(String value) {
            addCriterion("allwinBlack <=", value, "allwinblack");
            return (Criteria) this;
        }

        public Criteria andAllwinblackLike(String value) {
            addCriterion("allwinBlack like", value, "allwinblack");
            return (Criteria) this;
        }

        public Criteria andAllwinblackNotLike(String value) {
            addCriterion("allwinBlack not like", value, "allwinblack");
            return (Criteria) this;
        }

        public Criteria andAllwinblackIn(List<String> values) {
            addCriterion("allwinBlack in", values, "allwinblack");
            return (Criteria) this;
        }

        public Criteria andAllwinblackNotIn(List<String> values) {
            addCriterion("allwinBlack not in", values, "allwinblack");
            return (Criteria) this;
        }

        public Criteria andAllwinblackBetween(String value1, String value2) {
            addCriterion("allwinBlack between", value1, value2, "allwinblack");
            return (Criteria) this;
        }

        public Criteria andAllwinblackNotBetween(String value1, String value2) {
            addCriterion("allwinBlack not between", value1, value2, "allwinblack");
            return (Criteria) this;
        }

        public Criteria andIdentityrisk1IsNull() {
            addCriterion("identityRisk1 is null");
            return (Criteria) this;
        }

        public Criteria andIdentityrisk1IsNotNull() {
            addCriterion("identityRisk1 is not null");
            return (Criteria) this;
        }

        public Criteria andIdentityrisk1EqualTo(String value) {
            addCriterion("identityRisk1 =", value, "identityrisk1");
            return (Criteria) this;
        }

        public Criteria andIdentityrisk1NotEqualTo(String value) {
            addCriterion("identityRisk1 <>", value, "identityrisk1");
            return (Criteria) this;
        }

        public Criteria andIdentityrisk1GreaterThan(String value) {
            addCriterion("identityRisk1 >", value, "identityrisk1");
            return (Criteria) this;
        }

        public Criteria andIdentityrisk1GreaterThanOrEqualTo(String value) {
            addCriterion("identityRisk1 >=", value, "identityrisk1");
            return (Criteria) this;
        }

        public Criteria andIdentityrisk1LessThan(String value) {
            addCriterion("identityRisk1 <", value, "identityrisk1");
            return (Criteria) this;
        }

        public Criteria andIdentityrisk1LessThanOrEqualTo(String value) {
            addCriterion("identityRisk1 <=", value, "identityrisk1");
            return (Criteria) this;
        }

        public Criteria andIdentityrisk1Like(String value) {
            addCriterion("identityRisk1 like", value, "identityrisk1");
            return (Criteria) this;
        }

        public Criteria andIdentityrisk1NotLike(String value) {
            addCriterion("identityRisk1 not like", value, "identityrisk1");
            return (Criteria) this;
        }

        public Criteria andIdentityrisk1In(List<String> values) {
            addCriterion("identityRisk1 in", values, "identityrisk1");
            return (Criteria) this;
        }

        public Criteria andIdentityrisk1NotIn(List<String> values) {
            addCriterion("identityRisk1 not in", values, "identityrisk1");
            return (Criteria) this;
        }

        public Criteria andIdentityrisk1Between(String value1, String value2) {
            addCriterion("identityRisk1 between", value1, value2, "identityrisk1");
            return (Criteria) this;
        }

        public Criteria andIdentityrisk1NotBetween(String value1, String value2) {
            addCriterion("identityRisk1 not between", value1, value2, "identityrisk1");
            return (Criteria) this;
        }

        public Criteria andIdentityrisk2IsNull() {
            addCriterion("identityRisk2 is null");
            return (Criteria) this;
        }

        public Criteria andIdentityrisk2IsNotNull() {
            addCriterion("identityRisk2 is not null");
            return (Criteria) this;
        }

        public Criteria andIdentityrisk2EqualTo(String value) {
            addCriterion("identityRisk2 =", value, "identityrisk2");
            return (Criteria) this;
        }

        public Criteria andIdentityrisk2NotEqualTo(String value) {
            addCriterion("identityRisk2 <>", value, "identityrisk2");
            return (Criteria) this;
        }

        public Criteria andIdentityrisk2GreaterThan(String value) {
            addCriterion("identityRisk2 >", value, "identityrisk2");
            return (Criteria) this;
        }

        public Criteria andIdentityrisk2GreaterThanOrEqualTo(String value) {
            addCriterion("identityRisk2 >=", value, "identityrisk2");
            return (Criteria) this;
        }

        public Criteria andIdentityrisk2LessThan(String value) {
            addCriterion("identityRisk2 <", value, "identityrisk2");
            return (Criteria) this;
        }

        public Criteria andIdentityrisk2LessThanOrEqualTo(String value) {
            addCriterion("identityRisk2 <=", value, "identityrisk2");
            return (Criteria) this;
        }

        public Criteria andIdentityrisk2Like(String value) {
            addCriterion("identityRisk2 like", value, "identityrisk2");
            return (Criteria) this;
        }

        public Criteria andIdentityrisk2NotLike(String value) {
            addCriterion("identityRisk2 not like", value, "identityrisk2");
            return (Criteria) this;
        }

        public Criteria andIdentityrisk2In(List<String> values) {
            addCriterion("identityRisk2 in", values, "identityrisk2");
            return (Criteria) this;
        }

        public Criteria andIdentityrisk2NotIn(List<String> values) {
            addCriterion("identityRisk2 not in", values, "identityrisk2");
            return (Criteria) this;
        }

        public Criteria andIdentityrisk2Between(String value1, String value2) {
            addCriterion("identityRisk2 between", value1, value2, "identityrisk2");
            return (Criteria) this;
        }

        public Criteria andIdentityrisk2NotBetween(String value1, String value2) {
            addCriterion("identityRisk2 not between", value1, value2, "identityrisk2");
            return (Criteria) this;
        }

        public Criteria andIdentityrisk3IsNull() {
            addCriterion("identityRisk3 is null");
            return (Criteria) this;
        }

        public Criteria andIdentityrisk3IsNotNull() {
            addCriterion("identityRisk3 is not null");
            return (Criteria) this;
        }

        public Criteria andIdentityrisk3EqualTo(String value) {
            addCriterion("identityRisk3 =", value, "identityrisk3");
            return (Criteria) this;
        }

        public Criteria andIdentityrisk3NotEqualTo(String value) {
            addCriterion("identityRisk3 <>", value, "identityrisk3");
            return (Criteria) this;
        }

        public Criteria andIdentityrisk3GreaterThan(String value) {
            addCriterion("identityRisk3 >", value, "identityrisk3");
            return (Criteria) this;
        }

        public Criteria andIdentityrisk3GreaterThanOrEqualTo(String value) {
            addCriterion("identityRisk3 >=", value, "identityrisk3");
            return (Criteria) this;
        }

        public Criteria andIdentityrisk3LessThan(String value) {
            addCriterion("identityRisk3 <", value, "identityrisk3");
            return (Criteria) this;
        }

        public Criteria andIdentityrisk3LessThanOrEqualTo(String value) {
            addCriterion("identityRisk3 <=", value, "identityrisk3");
            return (Criteria) this;
        }

        public Criteria andIdentityrisk3Like(String value) {
            addCriterion("identityRisk3 like", value, "identityrisk3");
            return (Criteria) this;
        }

        public Criteria andIdentityrisk3NotLike(String value) {
            addCriterion("identityRisk3 not like", value, "identityrisk3");
            return (Criteria) this;
        }

        public Criteria andIdentityrisk3In(List<String> values) {
            addCriterion("identityRisk3 in", values, "identityrisk3");
            return (Criteria) this;
        }

        public Criteria andIdentityrisk3NotIn(List<String> values) {
            addCriterion("identityRisk3 not in", values, "identityrisk3");
            return (Criteria) this;
        }

        public Criteria andIdentityrisk3Between(String value1, String value2) {
            addCriterion("identityRisk3 between", value1, value2, "identityrisk3");
            return (Criteria) this;
        }

        public Criteria andIdentityrisk3NotBetween(String value1, String value2) {
            addCriterion("identityRisk3 not between", value1, value2, "identityrisk3");
            return (Criteria) this;
        }

        public Criteria andCountidentityriskIsNull() {
            addCriterion("countIdentityRisk is null");
            return (Criteria) this;
        }

        public Criteria andCountidentityriskIsNotNull() {
            addCriterion("countIdentityRisk is not null");
            return (Criteria) this;
        }

        public Criteria andCountidentityriskEqualTo(String value) {
            addCriterion("countIdentityRisk =", value, "countidentityrisk");
            return (Criteria) this;
        }

        public Criteria andCountidentityriskNotEqualTo(String value) {
            addCriterion("countIdentityRisk <>", value, "countidentityrisk");
            return (Criteria) this;
        }

        public Criteria andCountidentityriskGreaterThan(String value) {
            addCriterion("countIdentityRisk >", value, "countidentityrisk");
            return (Criteria) this;
        }

        public Criteria andCountidentityriskGreaterThanOrEqualTo(String value) {
            addCriterion("countIdentityRisk >=", value, "countidentityrisk");
            return (Criteria) this;
        }

        public Criteria andCountidentityriskLessThan(String value) {
            addCriterion("countIdentityRisk <", value, "countidentityrisk");
            return (Criteria) this;
        }

        public Criteria andCountidentityriskLessThanOrEqualTo(String value) {
            addCriterion("countIdentityRisk <=", value, "countidentityrisk");
            return (Criteria) this;
        }

        public Criteria andCountidentityriskLike(String value) {
            addCriterion("countIdentityRisk like", value, "countidentityrisk");
            return (Criteria) this;
        }

        public Criteria andCountidentityriskNotLike(String value) {
            addCriterion("countIdentityRisk not like", value, "countidentityrisk");
            return (Criteria) this;
        }

        public Criteria andCountidentityriskIn(List<String> values) {
            addCriterion("countIdentityRisk in", values, "countidentityrisk");
            return (Criteria) this;
        }

        public Criteria andCountidentityriskNotIn(List<String> values) {
            addCriterion("countIdentityRisk not in", values, "countidentityrisk");
            return (Criteria) this;
        }

        public Criteria andCountidentityriskBetween(String value1, String value2) {
            addCriterion("countIdentityRisk between", value1, value2, "countidentityrisk");
            return (Criteria) this;
        }

        public Criteria andCountidentityriskNotBetween(String value1, String value2) {
            addCriterion("countIdentityRisk not between", value1, value2, "countidentityrisk");
            return (Criteria) this;
        }

        public Criteria andPhonerisk1IsNull() {
            addCriterion("phoneRisk1 is null");
            return (Criteria) this;
        }

        public Criteria andPhonerisk1IsNotNull() {
            addCriterion("phoneRisk1 is not null");
            return (Criteria) this;
        }

        public Criteria andPhonerisk1EqualTo(String value) {
            addCriterion("phoneRisk1 =", value, "phonerisk1");
            return (Criteria) this;
        }

        public Criteria andPhonerisk1NotEqualTo(String value) {
            addCriterion("phoneRisk1 <>", value, "phonerisk1");
            return (Criteria) this;
        }

        public Criteria andPhonerisk1GreaterThan(String value) {
            addCriterion("phoneRisk1 >", value, "phonerisk1");
            return (Criteria) this;
        }

        public Criteria andPhonerisk1GreaterThanOrEqualTo(String value) {
            addCriterion("phoneRisk1 >=", value, "phonerisk1");
            return (Criteria) this;
        }

        public Criteria andPhonerisk1LessThan(String value) {
            addCriterion("phoneRisk1 <", value, "phonerisk1");
            return (Criteria) this;
        }

        public Criteria andPhonerisk1LessThanOrEqualTo(String value) {
            addCriterion("phoneRisk1 <=", value, "phonerisk1");
            return (Criteria) this;
        }

        public Criteria andPhonerisk1Like(String value) {
            addCriterion("phoneRisk1 like", value, "phonerisk1");
            return (Criteria) this;
        }

        public Criteria andPhonerisk1NotLike(String value) {
            addCriterion("phoneRisk1 not like", value, "phonerisk1");
            return (Criteria) this;
        }

        public Criteria andPhonerisk1In(List<String> values) {
            addCriterion("phoneRisk1 in", values, "phonerisk1");
            return (Criteria) this;
        }

        public Criteria andPhonerisk1NotIn(List<String> values) {
            addCriterion("phoneRisk1 not in", values, "phonerisk1");
            return (Criteria) this;
        }

        public Criteria andPhonerisk1Between(String value1, String value2) {
            addCriterion("phoneRisk1 between", value1, value2, "phonerisk1");
            return (Criteria) this;
        }

        public Criteria andPhonerisk1NotBetween(String value1, String value2) {
            addCriterion("phoneRisk1 not between", value1, value2, "phonerisk1");
            return (Criteria) this;
        }

        public Criteria andPhonerisk2IsNull() {
            addCriterion("phoneRisk2 is null");
            return (Criteria) this;
        }

        public Criteria andPhonerisk2IsNotNull() {
            addCriterion("phoneRisk2 is not null");
            return (Criteria) this;
        }

        public Criteria andPhonerisk2EqualTo(String value) {
            addCriterion("phoneRisk2 =", value, "phonerisk2");
            return (Criteria) this;
        }

        public Criteria andPhonerisk2NotEqualTo(String value) {
            addCriterion("phoneRisk2 <>", value, "phonerisk2");
            return (Criteria) this;
        }

        public Criteria andPhonerisk2GreaterThan(String value) {
            addCriterion("phoneRisk2 >", value, "phonerisk2");
            return (Criteria) this;
        }

        public Criteria andPhonerisk2GreaterThanOrEqualTo(String value) {
            addCriterion("phoneRisk2 >=", value, "phonerisk2");
            return (Criteria) this;
        }

        public Criteria andPhonerisk2LessThan(String value) {
            addCriterion("phoneRisk2 <", value, "phonerisk2");
            return (Criteria) this;
        }

        public Criteria andPhonerisk2LessThanOrEqualTo(String value) {
            addCriterion("phoneRisk2 <=", value, "phonerisk2");
            return (Criteria) this;
        }

        public Criteria andPhonerisk2Like(String value) {
            addCriterion("phoneRisk2 like", value, "phonerisk2");
            return (Criteria) this;
        }

        public Criteria andPhonerisk2NotLike(String value) {
            addCriterion("phoneRisk2 not like", value, "phonerisk2");
            return (Criteria) this;
        }

        public Criteria andPhonerisk2In(List<String> values) {
            addCriterion("phoneRisk2 in", values, "phonerisk2");
            return (Criteria) this;
        }

        public Criteria andPhonerisk2NotIn(List<String> values) {
            addCriterion("phoneRisk2 not in", values, "phonerisk2");
            return (Criteria) this;
        }

        public Criteria andPhonerisk2Between(String value1, String value2) {
            addCriterion("phoneRisk2 between", value1, value2, "phonerisk2");
            return (Criteria) this;
        }

        public Criteria andPhonerisk2NotBetween(String value1, String value2) {
            addCriterion("phoneRisk2 not between", value1, value2, "phonerisk2");
            return (Criteria) this;
        }

        public Criteria andPhonerisk3IsNull() {
            addCriterion("phoneRisk3 is null");
            return (Criteria) this;
        }

        public Criteria andPhonerisk3IsNotNull() {
            addCriterion("phoneRisk3 is not null");
            return (Criteria) this;
        }

        public Criteria andPhonerisk3EqualTo(String value) {
            addCriterion("phoneRisk3 =", value, "phonerisk3");
            return (Criteria) this;
        }

        public Criteria andPhonerisk3NotEqualTo(String value) {
            addCriterion("phoneRisk3 <>", value, "phonerisk3");
            return (Criteria) this;
        }

        public Criteria andPhonerisk3GreaterThan(String value) {
            addCriterion("phoneRisk3 >", value, "phonerisk3");
            return (Criteria) this;
        }

        public Criteria andPhonerisk3GreaterThanOrEqualTo(String value) {
            addCriterion("phoneRisk3 >=", value, "phonerisk3");
            return (Criteria) this;
        }

        public Criteria andPhonerisk3LessThan(String value) {
            addCriterion("phoneRisk3 <", value, "phonerisk3");
            return (Criteria) this;
        }

        public Criteria andPhonerisk3LessThanOrEqualTo(String value) {
            addCriterion("phoneRisk3 <=", value, "phonerisk3");
            return (Criteria) this;
        }

        public Criteria andPhonerisk3Like(String value) {
            addCriterion("phoneRisk3 like", value, "phonerisk3");
            return (Criteria) this;
        }

        public Criteria andPhonerisk3NotLike(String value) {
            addCriterion("phoneRisk3 not like", value, "phonerisk3");
            return (Criteria) this;
        }

        public Criteria andPhonerisk3In(List<String> values) {
            addCriterion("phoneRisk3 in", values, "phonerisk3");
            return (Criteria) this;
        }

        public Criteria andPhonerisk3NotIn(List<String> values) {
            addCriterion("phoneRisk3 not in", values, "phonerisk3");
            return (Criteria) this;
        }

        public Criteria andPhonerisk3Between(String value1, String value2) {
            addCriterion("phoneRisk3 between", value1, value2, "phonerisk3");
            return (Criteria) this;
        }

        public Criteria andPhonerisk3NotBetween(String value1, String value2) {
            addCriterion("phoneRisk3 not between", value1, value2, "phonerisk3");
            return (Criteria) this;
        }

        public Criteria andPhonerisk4IsNull() {
            addCriterion("phoneRisk4 is null");
            return (Criteria) this;
        }

        public Criteria andPhonerisk4IsNotNull() {
            addCriterion("phoneRisk4 is not null");
            return (Criteria) this;
        }

        public Criteria andPhonerisk4EqualTo(String value) {
            addCriterion("phoneRisk4 =", value, "phonerisk4");
            return (Criteria) this;
        }

        public Criteria andPhonerisk4NotEqualTo(String value) {
            addCriterion("phoneRisk4 <>", value, "phonerisk4");
            return (Criteria) this;
        }

        public Criteria andPhonerisk4GreaterThan(String value) {
            addCriterion("phoneRisk4 >", value, "phonerisk4");
            return (Criteria) this;
        }

        public Criteria andPhonerisk4GreaterThanOrEqualTo(String value) {
            addCriterion("phoneRisk4 >=", value, "phonerisk4");
            return (Criteria) this;
        }

        public Criteria andPhonerisk4LessThan(String value) {
            addCriterion("phoneRisk4 <", value, "phonerisk4");
            return (Criteria) this;
        }

        public Criteria andPhonerisk4LessThanOrEqualTo(String value) {
            addCriterion("phoneRisk4 <=", value, "phonerisk4");
            return (Criteria) this;
        }

        public Criteria andPhonerisk4Like(String value) {
            addCriterion("phoneRisk4 like", value, "phonerisk4");
            return (Criteria) this;
        }

        public Criteria andPhonerisk4NotLike(String value) {
            addCriterion("phoneRisk4 not like", value, "phonerisk4");
            return (Criteria) this;
        }

        public Criteria andPhonerisk4In(List<String> values) {
            addCriterion("phoneRisk4 in", values, "phonerisk4");
            return (Criteria) this;
        }

        public Criteria andPhonerisk4NotIn(List<String> values) {
            addCriterion("phoneRisk4 not in", values, "phonerisk4");
            return (Criteria) this;
        }

        public Criteria andPhonerisk4Between(String value1, String value2) {
            addCriterion("phoneRisk4 between", value1, value2, "phonerisk4");
            return (Criteria) this;
        }

        public Criteria andPhonerisk4NotBetween(String value1, String value2) {
            addCriterion("phoneRisk4 not between", value1, value2, "phonerisk4");
            return (Criteria) this;
        }

        public Criteria andPhonerisk5IsNull() {
            addCriterion("phoneRisk5 is null");
            return (Criteria) this;
        }

        public Criteria andPhonerisk5IsNotNull() {
            addCriterion("phoneRisk5 is not null");
            return (Criteria) this;
        }

        public Criteria andPhonerisk5EqualTo(String value) {
            addCriterion("phoneRisk5 =", value, "phonerisk5");
            return (Criteria) this;
        }

        public Criteria andPhonerisk5NotEqualTo(String value) {
            addCriterion("phoneRisk5 <>", value, "phonerisk5");
            return (Criteria) this;
        }

        public Criteria andPhonerisk5GreaterThan(String value) {
            addCriterion("phoneRisk5 >", value, "phonerisk5");
            return (Criteria) this;
        }

        public Criteria andPhonerisk5GreaterThanOrEqualTo(String value) {
            addCriterion("phoneRisk5 >=", value, "phonerisk5");
            return (Criteria) this;
        }

        public Criteria andPhonerisk5LessThan(String value) {
            addCriterion("phoneRisk5 <", value, "phonerisk5");
            return (Criteria) this;
        }

        public Criteria andPhonerisk5LessThanOrEqualTo(String value) {
            addCriterion("phoneRisk5 <=", value, "phonerisk5");
            return (Criteria) this;
        }

        public Criteria andPhonerisk5Like(String value) {
            addCriterion("phoneRisk5 like", value, "phonerisk5");
            return (Criteria) this;
        }

        public Criteria andPhonerisk5NotLike(String value) {
            addCriterion("phoneRisk5 not like", value, "phonerisk5");
            return (Criteria) this;
        }

        public Criteria andPhonerisk5In(List<String> values) {
            addCriterion("phoneRisk5 in", values, "phonerisk5");
            return (Criteria) this;
        }

        public Criteria andPhonerisk5NotIn(List<String> values) {
            addCriterion("phoneRisk5 not in", values, "phonerisk5");
            return (Criteria) this;
        }

        public Criteria andPhonerisk5Between(String value1, String value2) {
            addCriterion("phoneRisk5 between", value1, value2, "phonerisk5");
            return (Criteria) this;
        }

        public Criteria andPhonerisk5NotBetween(String value1, String value2) {
            addCriterion("phoneRisk5 not between", value1, value2, "phonerisk5");
            return (Criteria) this;
        }

        public Criteria andPhonerisk6IsNull() {
            addCriterion("phoneRisk6 is null");
            return (Criteria) this;
        }

        public Criteria andPhonerisk6IsNotNull() {
            addCriterion("phoneRisk6 is not null");
            return (Criteria) this;
        }

        public Criteria andPhonerisk6EqualTo(String value) {
            addCriterion("phoneRisk6 =", value, "phonerisk6");
            return (Criteria) this;
        }

        public Criteria andPhonerisk6NotEqualTo(String value) {
            addCriterion("phoneRisk6 <>", value, "phonerisk6");
            return (Criteria) this;
        }

        public Criteria andPhonerisk6GreaterThan(String value) {
            addCriterion("phoneRisk6 >", value, "phonerisk6");
            return (Criteria) this;
        }

        public Criteria andPhonerisk6GreaterThanOrEqualTo(String value) {
            addCriterion("phoneRisk6 >=", value, "phonerisk6");
            return (Criteria) this;
        }

        public Criteria andPhonerisk6LessThan(String value) {
            addCriterion("phoneRisk6 <", value, "phonerisk6");
            return (Criteria) this;
        }

        public Criteria andPhonerisk6LessThanOrEqualTo(String value) {
            addCriterion("phoneRisk6 <=", value, "phonerisk6");
            return (Criteria) this;
        }

        public Criteria andPhonerisk6Like(String value) {
            addCriterion("phoneRisk6 like", value, "phonerisk6");
            return (Criteria) this;
        }

        public Criteria andPhonerisk6NotLike(String value) {
            addCriterion("phoneRisk6 not like", value, "phonerisk6");
            return (Criteria) this;
        }

        public Criteria andPhonerisk6In(List<String> values) {
            addCriterion("phoneRisk6 in", values, "phonerisk6");
            return (Criteria) this;
        }

        public Criteria andPhonerisk6NotIn(List<String> values) {
            addCriterion("phoneRisk6 not in", values, "phonerisk6");
            return (Criteria) this;
        }

        public Criteria andPhonerisk6Between(String value1, String value2) {
            addCriterion("phoneRisk6 between", value1, value2, "phonerisk6");
            return (Criteria) this;
        }

        public Criteria andPhonerisk6NotBetween(String value1, String value2) {
            addCriterion("phoneRisk6 not between", value1, value2, "phonerisk6");
            return (Criteria) this;
        }

        public Criteria andPhonerisk7IsNull() {
            addCriterion("phoneRisk7 is null");
            return (Criteria) this;
        }

        public Criteria andPhonerisk7IsNotNull() {
            addCriterion("phoneRisk7 is not null");
            return (Criteria) this;
        }

        public Criteria andPhonerisk7EqualTo(String value) {
            addCriterion("phoneRisk7 =", value, "phonerisk7");
            return (Criteria) this;
        }

        public Criteria andPhonerisk7NotEqualTo(String value) {
            addCriterion("phoneRisk7 <>", value, "phonerisk7");
            return (Criteria) this;
        }

        public Criteria andPhonerisk7GreaterThan(String value) {
            addCriterion("phoneRisk7 >", value, "phonerisk7");
            return (Criteria) this;
        }

        public Criteria andPhonerisk7GreaterThanOrEqualTo(String value) {
            addCriterion("phoneRisk7 >=", value, "phonerisk7");
            return (Criteria) this;
        }

        public Criteria andPhonerisk7LessThan(String value) {
            addCriterion("phoneRisk7 <", value, "phonerisk7");
            return (Criteria) this;
        }

        public Criteria andPhonerisk7LessThanOrEqualTo(String value) {
            addCriterion("phoneRisk7 <=", value, "phonerisk7");
            return (Criteria) this;
        }

        public Criteria andPhonerisk7Like(String value) {
            addCriterion("phoneRisk7 like", value, "phonerisk7");
            return (Criteria) this;
        }

        public Criteria andPhonerisk7NotLike(String value) {
            addCriterion("phoneRisk7 not like", value, "phonerisk7");
            return (Criteria) this;
        }

        public Criteria andPhonerisk7In(List<String> values) {
            addCriterion("phoneRisk7 in", values, "phonerisk7");
            return (Criteria) this;
        }

        public Criteria andPhonerisk7NotIn(List<String> values) {
            addCriterion("phoneRisk7 not in", values, "phonerisk7");
            return (Criteria) this;
        }

        public Criteria andPhonerisk7Between(String value1, String value2) {
            addCriterion("phoneRisk7 between", value1, value2, "phonerisk7");
            return (Criteria) this;
        }

        public Criteria andPhonerisk7NotBetween(String value1, String value2) {
            addCriterion("phoneRisk7 not between", value1, value2, "phonerisk7");
            return (Criteria) this;
        }

        public Criteria andCountphoneriskIsNull() {
            addCriterion("countPhoneRisk is null");
            return (Criteria) this;
        }

        public Criteria andCountphoneriskIsNotNull() {
            addCriterion("countPhoneRisk is not null");
            return (Criteria) this;
        }

        public Criteria andCountphoneriskEqualTo(String value) {
            addCriterion("countPhoneRisk =", value, "countphonerisk");
            return (Criteria) this;
        }

        public Criteria andCountphoneriskNotEqualTo(String value) {
            addCriterion("countPhoneRisk <>", value, "countphonerisk");
            return (Criteria) this;
        }

        public Criteria andCountphoneriskGreaterThan(String value) {
            addCriterion("countPhoneRisk >", value, "countphonerisk");
            return (Criteria) this;
        }

        public Criteria andCountphoneriskGreaterThanOrEqualTo(String value) {
            addCriterion("countPhoneRisk >=", value, "countphonerisk");
            return (Criteria) this;
        }

        public Criteria andCountphoneriskLessThan(String value) {
            addCriterion("countPhoneRisk <", value, "countphonerisk");
            return (Criteria) this;
        }

        public Criteria andCountphoneriskLessThanOrEqualTo(String value) {
            addCriterion("countPhoneRisk <=", value, "countphonerisk");
            return (Criteria) this;
        }

        public Criteria andCountphoneriskLike(String value) {
            addCriterion("countPhoneRisk like", value, "countphonerisk");
            return (Criteria) this;
        }

        public Criteria andCountphoneriskNotLike(String value) {
            addCriterion("countPhoneRisk not like", value, "countphonerisk");
            return (Criteria) this;
        }

        public Criteria andCountphoneriskIn(List<String> values) {
            addCriterion("countPhoneRisk in", values, "countphonerisk");
            return (Criteria) this;
        }

        public Criteria andCountphoneriskNotIn(List<String> values) {
            addCriterion("countPhoneRisk not in", values, "countphonerisk");
            return (Criteria) this;
        }

        public Criteria andCountphoneriskBetween(String value1, String value2) {
            addCriterion("countPhoneRisk between", value1, value2, "countphonerisk");
            return (Criteria) this;
        }

        public Criteria andCountphoneriskNotBetween(String value1, String value2) {
            addCriterion("countPhoneRisk not between", value1, value2, "countphonerisk");
            return (Criteria) this;
        }

        public Criteria andEmailrisk1IsNull() {
            addCriterion("emailRisk1 is null");
            return (Criteria) this;
        }

        public Criteria andEmailrisk1IsNotNull() {
            addCriterion("emailRisk1 is not null");
            return (Criteria) this;
        }

        public Criteria andEmailrisk1EqualTo(String value) {
            addCriterion("emailRisk1 =", value, "emailrisk1");
            return (Criteria) this;
        }

        public Criteria andEmailrisk1NotEqualTo(String value) {
            addCriterion("emailRisk1 <>", value, "emailrisk1");
            return (Criteria) this;
        }

        public Criteria andEmailrisk1GreaterThan(String value) {
            addCriterion("emailRisk1 >", value, "emailrisk1");
            return (Criteria) this;
        }

        public Criteria andEmailrisk1GreaterThanOrEqualTo(String value) {
            addCriterion("emailRisk1 >=", value, "emailrisk1");
            return (Criteria) this;
        }

        public Criteria andEmailrisk1LessThan(String value) {
            addCriterion("emailRisk1 <", value, "emailrisk1");
            return (Criteria) this;
        }

        public Criteria andEmailrisk1LessThanOrEqualTo(String value) {
            addCriterion("emailRisk1 <=", value, "emailrisk1");
            return (Criteria) this;
        }

        public Criteria andEmailrisk1Like(String value) {
            addCriterion("emailRisk1 like", value, "emailrisk1");
            return (Criteria) this;
        }

        public Criteria andEmailrisk1NotLike(String value) {
            addCriterion("emailRisk1 not like", value, "emailrisk1");
            return (Criteria) this;
        }

        public Criteria andEmailrisk1In(List<String> values) {
            addCriterion("emailRisk1 in", values, "emailrisk1");
            return (Criteria) this;
        }

        public Criteria andEmailrisk1NotIn(List<String> values) {
            addCriterion("emailRisk1 not in", values, "emailrisk1");
            return (Criteria) this;
        }

        public Criteria andEmailrisk1Between(String value1, String value2) {
            addCriterion("emailRisk1 between", value1, value2, "emailrisk1");
            return (Criteria) this;
        }

        public Criteria andEmailrisk1NotBetween(String value1, String value2) {
            addCriterion("emailRisk1 not between", value1, value2, "emailrisk1");
            return (Criteria) this;
        }

        public Criteria andEmailrisk2IsNull() {
            addCriterion("emailRisk2 is null");
            return (Criteria) this;
        }

        public Criteria andEmailrisk2IsNotNull() {
            addCriterion("emailRisk2 is not null");
            return (Criteria) this;
        }

        public Criteria andEmailrisk2EqualTo(String value) {
            addCriterion("emailRisk2 =", value, "emailrisk2");
            return (Criteria) this;
        }

        public Criteria andEmailrisk2NotEqualTo(String value) {
            addCriterion("emailRisk2 <>", value, "emailrisk2");
            return (Criteria) this;
        }

        public Criteria andEmailrisk2GreaterThan(String value) {
            addCriterion("emailRisk2 >", value, "emailrisk2");
            return (Criteria) this;
        }

        public Criteria andEmailrisk2GreaterThanOrEqualTo(String value) {
            addCriterion("emailRisk2 >=", value, "emailrisk2");
            return (Criteria) this;
        }

        public Criteria andEmailrisk2LessThan(String value) {
            addCriterion("emailRisk2 <", value, "emailrisk2");
            return (Criteria) this;
        }

        public Criteria andEmailrisk2LessThanOrEqualTo(String value) {
            addCriterion("emailRisk2 <=", value, "emailrisk2");
            return (Criteria) this;
        }

        public Criteria andEmailrisk2Like(String value) {
            addCriterion("emailRisk2 like", value, "emailrisk2");
            return (Criteria) this;
        }

        public Criteria andEmailrisk2NotLike(String value) {
            addCriterion("emailRisk2 not like", value, "emailrisk2");
            return (Criteria) this;
        }

        public Criteria andEmailrisk2In(List<String> values) {
            addCriterion("emailRisk2 in", values, "emailrisk2");
            return (Criteria) this;
        }

        public Criteria andEmailrisk2NotIn(List<String> values) {
            addCriterion("emailRisk2 not in", values, "emailrisk2");
            return (Criteria) this;
        }

        public Criteria andEmailrisk2Between(String value1, String value2) {
            addCriterion("emailRisk2 between", value1, value2, "emailrisk2");
            return (Criteria) this;
        }

        public Criteria andEmailrisk2NotBetween(String value1, String value2) {
            addCriterion("emailRisk2 not between", value1, value2, "emailrisk2");
            return (Criteria) this;
        }

        public Criteria andEmailrisk3IsNull() {
            addCriterion("emailRisk3 is null");
            return (Criteria) this;
        }

        public Criteria andEmailrisk3IsNotNull() {
            addCriterion("emailRisk3 is not null");
            return (Criteria) this;
        }

        public Criteria andEmailrisk3EqualTo(String value) {
            addCriterion("emailRisk3 =", value, "emailrisk3");
            return (Criteria) this;
        }

        public Criteria andEmailrisk3NotEqualTo(String value) {
            addCriterion("emailRisk3 <>", value, "emailrisk3");
            return (Criteria) this;
        }

        public Criteria andEmailrisk3GreaterThan(String value) {
            addCriterion("emailRisk3 >", value, "emailrisk3");
            return (Criteria) this;
        }

        public Criteria andEmailrisk3GreaterThanOrEqualTo(String value) {
            addCriterion("emailRisk3 >=", value, "emailrisk3");
            return (Criteria) this;
        }

        public Criteria andEmailrisk3LessThan(String value) {
            addCriterion("emailRisk3 <", value, "emailrisk3");
            return (Criteria) this;
        }

        public Criteria andEmailrisk3LessThanOrEqualTo(String value) {
            addCriterion("emailRisk3 <=", value, "emailrisk3");
            return (Criteria) this;
        }

        public Criteria andEmailrisk3Like(String value) {
            addCriterion("emailRisk3 like", value, "emailrisk3");
            return (Criteria) this;
        }

        public Criteria andEmailrisk3NotLike(String value) {
            addCriterion("emailRisk3 not like", value, "emailrisk3");
            return (Criteria) this;
        }

        public Criteria andEmailrisk3In(List<String> values) {
            addCriterion("emailRisk3 in", values, "emailrisk3");
            return (Criteria) this;
        }

        public Criteria andEmailrisk3NotIn(List<String> values) {
            addCriterion("emailRisk3 not in", values, "emailrisk3");
            return (Criteria) this;
        }

        public Criteria andEmailrisk3Between(String value1, String value2) {
            addCriterion("emailRisk3 between", value1, value2, "emailrisk3");
            return (Criteria) this;
        }

        public Criteria andEmailrisk3NotBetween(String value1, String value2) {
            addCriterion("emailRisk3 not between", value1, value2, "emailrisk3");
            return (Criteria) this;
        }

        public Criteria andEmailrisk4IsNull() {
            addCriterion("emailRisk4 is null");
            return (Criteria) this;
        }

        public Criteria andEmailrisk4IsNotNull() {
            addCriterion("emailRisk4 is not null");
            return (Criteria) this;
        }

        public Criteria andEmailrisk4EqualTo(String value) {
            addCriterion("emailRisk4 =", value, "emailrisk4");
            return (Criteria) this;
        }

        public Criteria andEmailrisk4NotEqualTo(String value) {
            addCriterion("emailRisk4 <>", value, "emailrisk4");
            return (Criteria) this;
        }

        public Criteria andEmailrisk4GreaterThan(String value) {
            addCriterion("emailRisk4 >", value, "emailrisk4");
            return (Criteria) this;
        }

        public Criteria andEmailrisk4GreaterThanOrEqualTo(String value) {
            addCriterion("emailRisk4 >=", value, "emailrisk4");
            return (Criteria) this;
        }

        public Criteria andEmailrisk4LessThan(String value) {
            addCriterion("emailRisk4 <", value, "emailrisk4");
            return (Criteria) this;
        }

        public Criteria andEmailrisk4LessThanOrEqualTo(String value) {
            addCriterion("emailRisk4 <=", value, "emailrisk4");
            return (Criteria) this;
        }

        public Criteria andEmailrisk4Like(String value) {
            addCriterion("emailRisk4 like", value, "emailrisk4");
            return (Criteria) this;
        }

        public Criteria andEmailrisk4NotLike(String value) {
            addCriterion("emailRisk4 not like", value, "emailrisk4");
            return (Criteria) this;
        }

        public Criteria andEmailrisk4In(List<String> values) {
            addCriterion("emailRisk4 in", values, "emailrisk4");
            return (Criteria) this;
        }

        public Criteria andEmailrisk4NotIn(List<String> values) {
            addCriterion("emailRisk4 not in", values, "emailrisk4");
            return (Criteria) this;
        }

        public Criteria andEmailrisk4Between(String value1, String value2) {
            addCriterion("emailRisk4 between", value1, value2, "emailrisk4");
            return (Criteria) this;
        }

        public Criteria andEmailrisk4NotBetween(String value1, String value2) {
            addCriterion("emailRisk4 not between", value1, value2, "emailrisk4");
            return (Criteria) this;
        }

        public Criteria andEmailrisk5IsNull() {
            addCriterion("emailRisk5 is null");
            return (Criteria) this;
        }

        public Criteria andEmailrisk5IsNotNull() {
            addCriterion("emailRisk5 is not null");
            return (Criteria) this;
        }

        public Criteria andEmailrisk5EqualTo(String value) {
            addCriterion("emailRisk5 =", value, "emailrisk5");
            return (Criteria) this;
        }

        public Criteria andEmailrisk5NotEqualTo(String value) {
            addCriterion("emailRisk5 <>", value, "emailrisk5");
            return (Criteria) this;
        }

        public Criteria andEmailrisk5GreaterThan(String value) {
            addCriterion("emailRisk5 >", value, "emailrisk5");
            return (Criteria) this;
        }

        public Criteria andEmailrisk5GreaterThanOrEqualTo(String value) {
            addCriterion("emailRisk5 >=", value, "emailrisk5");
            return (Criteria) this;
        }

        public Criteria andEmailrisk5LessThan(String value) {
            addCriterion("emailRisk5 <", value, "emailrisk5");
            return (Criteria) this;
        }

        public Criteria andEmailrisk5LessThanOrEqualTo(String value) {
            addCriterion("emailRisk5 <=", value, "emailrisk5");
            return (Criteria) this;
        }

        public Criteria andEmailrisk5Like(String value) {
            addCriterion("emailRisk5 like", value, "emailrisk5");
            return (Criteria) this;
        }

        public Criteria andEmailrisk5NotLike(String value) {
            addCriterion("emailRisk5 not like", value, "emailrisk5");
            return (Criteria) this;
        }

        public Criteria andEmailrisk5In(List<String> values) {
            addCriterion("emailRisk5 in", values, "emailrisk5");
            return (Criteria) this;
        }

        public Criteria andEmailrisk5NotIn(List<String> values) {
            addCriterion("emailRisk5 not in", values, "emailrisk5");
            return (Criteria) this;
        }

        public Criteria andEmailrisk5Between(String value1, String value2) {
            addCriterion("emailRisk5 between", value1, value2, "emailrisk5");
            return (Criteria) this;
        }

        public Criteria andEmailrisk5NotBetween(String value1, String value2) {
            addCriterion("emailRisk5 not between", value1, value2, "emailrisk5");
            return (Criteria) this;
        }

        public Criteria andEmailrisk6IsNull() {
            addCriterion("emailRisk6 is null");
            return (Criteria) this;
        }

        public Criteria andEmailrisk6IsNotNull() {
            addCriterion("emailRisk6 is not null");
            return (Criteria) this;
        }

        public Criteria andEmailrisk6EqualTo(String value) {
            addCriterion("emailRisk6 =", value, "emailrisk6");
            return (Criteria) this;
        }

        public Criteria andEmailrisk6NotEqualTo(String value) {
            addCriterion("emailRisk6 <>", value, "emailrisk6");
            return (Criteria) this;
        }

        public Criteria andEmailrisk6GreaterThan(String value) {
            addCriterion("emailRisk6 >", value, "emailrisk6");
            return (Criteria) this;
        }

        public Criteria andEmailrisk6GreaterThanOrEqualTo(String value) {
            addCriterion("emailRisk6 >=", value, "emailrisk6");
            return (Criteria) this;
        }

        public Criteria andEmailrisk6LessThan(String value) {
            addCriterion("emailRisk6 <", value, "emailrisk6");
            return (Criteria) this;
        }

        public Criteria andEmailrisk6LessThanOrEqualTo(String value) {
            addCriterion("emailRisk6 <=", value, "emailrisk6");
            return (Criteria) this;
        }

        public Criteria andEmailrisk6Like(String value) {
            addCriterion("emailRisk6 like", value, "emailrisk6");
            return (Criteria) this;
        }

        public Criteria andEmailrisk6NotLike(String value) {
            addCriterion("emailRisk6 not like", value, "emailrisk6");
            return (Criteria) this;
        }

        public Criteria andEmailrisk6In(List<String> values) {
            addCriterion("emailRisk6 in", values, "emailrisk6");
            return (Criteria) this;
        }

        public Criteria andEmailrisk6NotIn(List<String> values) {
            addCriterion("emailRisk6 not in", values, "emailrisk6");
            return (Criteria) this;
        }

        public Criteria andEmailrisk6Between(String value1, String value2) {
            addCriterion("emailRisk6 between", value1, value2, "emailrisk6");
            return (Criteria) this;
        }

        public Criteria andEmailrisk6NotBetween(String value1, String value2) {
            addCriterion("emailRisk6 not between", value1, value2, "emailrisk6");
            return (Criteria) this;
        }

        public Criteria andCountemailriskIsNull() {
            addCriterion("countEmailRisk is null");
            return (Criteria) this;
        }

        public Criteria andCountemailriskIsNotNull() {
            addCriterion("countEmailRisk is not null");
            return (Criteria) this;
        }

        public Criteria andCountemailriskEqualTo(String value) {
            addCriterion("countEmailRisk =", value, "countemailrisk");
            return (Criteria) this;
        }

        public Criteria andCountemailriskNotEqualTo(String value) {
            addCriterion("countEmailRisk <>", value, "countemailrisk");
            return (Criteria) this;
        }

        public Criteria andCountemailriskGreaterThan(String value) {
            addCriterion("countEmailRisk >", value, "countemailrisk");
            return (Criteria) this;
        }

        public Criteria andCountemailriskGreaterThanOrEqualTo(String value) {
            addCriterion("countEmailRisk >=", value, "countemailrisk");
            return (Criteria) this;
        }

        public Criteria andCountemailriskLessThan(String value) {
            addCriterion("countEmailRisk <", value, "countemailrisk");
            return (Criteria) this;
        }

        public Criteria andCountemailriskLessThanOrEqualTo(String value) {
            addCriterion("countEmailRisk <=", value, "countemailrisk");
            return (Criteria) this;
        }

        public Criteria andCountemailriskLike(String value) {
            addCriterion("countEmailRisk like", value, "countemailrisk");
            return (Criteria) this;
        }

        public Criteria andCountemailriskNotLike(String value) {
            addCriterion("countEmailRisk not like", value, "countemailrisk");
            return (Criteria) this;
        }

        public Criteria andCountemailriskIn(List<String> values) {
            addCriterion("countEmailRisk in", values, "countemailrisk");
            return (Criteria) this;
        }

        public Criteria andCountemailriskNotIn(List<String> values) {
            addCriterion("countEmailRisk not in", values, "countemailrisk");
            return (Criteria) this;
        }

        public Criteria andCountemailriskBetween(String value1, String value2) {
            addCriterion("countEmailRisk between", value1, value2, "countemailrisk");
            return (Criteria) this;
        }

        public Criteria andCountemailriskNotBetween(String value1, String value2) {
            addCriterion("countEmailRisk not between", value1, value2, "countemailrisk");
            return (Criteria) this;
        }

        public Criteria andQqrisk1IsNull() {
            addCriterion("qqRisk1 is null");
            return (Criteria) this;
        }

        public Criteria andQqrisk1IsNotNull() {
            addCriterion("qqRisk1 is not null");
            return (Criteria) this;
        }

        public Criteria andQqrisk1EqualTo(String value) {
            addCriterion("qqRisk1 =", value, "qqrisk1");
            return (Criteria) this;
        }

        public Criteria andQqrisk1NotEqualTo(String value) {
            addCriterion("qqRisk1 <>", value, "qqrisk1");
            return (Criteria) this;
        }

        public Criteria andQqrisk1GreaterThan(String value) {
            addCriterion("qqRisk1 >", value, "qqrisk1");
            return (Criteria) this;
        }

        public Criteria andQqrisk1GreaterThanOrEqualTo(String value) {
            addCriterion("qqRisk1 >=", value, "qqrisk1");
            return (Criteria) this;
        }

        public Criteria andQqrisk1LessThan(String value) {
            addCriterion("qqRisk1 <", value, "qqrisk1");
            return (Criteria) this;
        }

        public Criteria andQqrisk1LessThanOrEqualTo(String value) {
            addCriterion("qqRisk1 <=", value, "qqrisk1");
            return (Criteria) this;
        }

        public Criteria andQqrisk1Like(String value) {
            addCriterion("qqRisk1 like", value, "qqrisk1");
            return (Criteria) this;
        }

        public Criteria andQqrisk1NotLike(String value) {
            addCriterion("qqRisk1 not like", value, "qqrisk1");
            return (Criteria) this;
        }

        public Criteria andQqrisk1In(List<String> values) {
            addCriterion("qqRisk1 in", values, "qqrisk1");
            return (Criteria) this;
        }

        public Criteria andQqrisk1NotIn(List<String> values) {
            addCriterion("qqRisk1 not in", values, "qqrisk1");
            return (Criteria) this;
        }

        public Criteria andQqrisk1Between(String value1, String value2) {
            addCriterion("qqRisk1 between", value1, value2, "qqrisk1");
            return (Criteria) this;
        }

        public Criteria andQqrisk1NotBetween(String value1, String value2) {
            addCriterion("qqRisk1 not between", value1, value2, "qqrisk1");
            return (Criteria) this;
        }

        public Criteria andQqrisk2IsNull() {
            addCriterion("qqRisk2 is null");
            return (Criteria) this;
        }

        public Criteria andQqrisk2IsNotNull() {
            addCriterion("qqRisk2 is not null");
            return (Criteria) this;
        }

        public Criteria andQqrisk2EqualTo(String value) {
            addCriterion("qqRisk2 =", value, "qqrisk2");
            return (Criteria) this;
        }

        public Criteria andQqrisk2NotEqualTo(String value) {
            addCriterion("qqRisk2 <>", value, "qqrisk2");
            return (Criteria) this;
        }

        public Criteria andQqrisk2GreaterThan(String value) {
            addCriterion("qqRisk2 >", value, "qqrisk2");
            return (Criteria) this;
        }

        public Criteria andQqrisk2GreaterThanOrEqualTo(String value) {
            addCriterion("qqRisk2 >=", value, "qqrisk2");
            return (Criteria) this;
        }

        public Criteria andQqrisk2LessThan(String value) {
            addCriterion("qqRisk2 <", value, "qqrisk2");
            return (Criteria) this;
        }

        public Criteria andQqrisk2LessThanOrEqualTo(String value) {
            addCriterion("qqRisk2 <=", value, "qqrisk2");
            return (Criteria) this;
        }

        public Criteria andQqrisk2Like(String value) {
            addCriterion("qqRisk2 like", value, "qqrisk2");
            return (Criteria) this;
        }

        public Criteria andQqrisk2NotLike(String value) {
            addCriterion("qqRisk2 not like", value, "qqrisk2");
            return (Criteria) this;
        }

        public Criteria andQqrisk2In(List<String> values) {
            addCriterion("qqRisk2 in", values, "qqrisk2");
            return (Criteria) this;
        }

        public Criteria andQqrisk2NotIn(List<String> values) {
            addCriterion("qqRisk2 not in", values, "qqrisk2");
            return (Criteria) this;
        }

        public Criteria andQqrisk2Between(String value1, String value2) {
            addCriterion("qqRisk2 between", value1, value2, "qqrisk2");
            return (Criteria) this;
        }

        public Criteria andQqrisk2NotBetween(String value1, String value2) {
            addCriterion("qqRisk2 not between", value1, value2, "qqrisk2");
            return (Criteria) this;
        }

        public Criteria andQqrisk3IsNull() {
            addCriterion("qqRisk3 is null");
            return (Criteria) this;
        }

        public Criteria andQqrisk3IsNotNull() {
            addCriterion("qqRisk3 is not null");
            return (Criteria) this;
        }

        public Criteria andQqrisk3EqualTo(String value) {
            addCriterion("qqRisk3 =", value, "qqrisk3");
            return (Criteria) this;
        }

        public Criteria andQqrisk3NotEqualTo(String value) {
            addCriterion("qqRisk3 <>", value, "qqrisk3");
            return (Criteria) this;
        }

        public Criteria andQqrisk3GreaterThan(String value) {
            addCriterion("qqRisk3 >", value, "qqrisk3");
            return (Criteria) this;
        }

        public Criteria andQqrisk3GreaterThanOrEqualTo(String value) {
            addCriterion("qqRisk3 >=", value, "qqrisk3");
            return (Criteria) this;
        }

        public Criteria andQqrisk3LessThan(String value) {
            addCriterion("qqRisk3 <", value, "qqrisk3");
            return (Criteria) this;
        }

        public Criteria andQqrisk3LessThanOrEqualTo(String value) {
            addCriterion("qqRisk3 <=", value, "qqrisk3");
            return (Criteria) this;
        }

        public Criteria andQqrisk3Like(String value) {
            addCriterion("qqRisk3 like", value, "qqrisk3");
            return (Criteria) this;
        }

        public Criteria andQqrisk3NotLike(String value) {
            addCriterion("qqRisk3 not like", value, "qqrisk3");
            return (Criteria) this;
        }

        public Criteria andQqrisk3In(List<String> values) {
            addCriterion("qqRisk3 in", values, "qqrisk3");
            return (Criteria) this;
        }

        public Criteria andQqrisk3NotIn(List<String> values) {
            addCriterion("qqRisk3 not in", values, "qqrisk3");
            return (Criteria) this;
        }

        public Criteria andQqrisk3Between(String value1, String value2) {
            addCriterion("qqRisk3 between", value1, value2, "qqrisk3");
            return (Criteria) this;
        }

        public Criteria andQqrisk3NotBetween(String value1, String value2) {
            addCriterion("qqRisk3 not between", value1, value2, "qqrisk3");
            return (Criteria) this;
        }

        public Criteria andQqrisk4IsNull() {
            addCriterion("qqRisk4 is null");
            return (Criteria) this;
        }

        public Criteria andQqrisk4IsNotNull() {
            addCriterion("qqRisk4 is not null");
            return (Criteria) this;
        }

        public Criteria andQqrisk4EqualTo(String value) {
            addCriterion("qqRisk4 =", value, "qqrisk4");
            return (Criteria) this;
        }

        public Criteria andQqrisk4NotEqualTo(String value) {
            addCriterion("qqRisk4 <>", value, "qqrisk4");
            return (Criteria) this;
        }

        public Criteria andQqrisk4GreaterThan(String value) {
            addCriterion("qqRisk4 >", value, "qqrisk4");
            return (Criteria) this;
        }

        public Criteria andQqrisk4GreaterThanOrEqualTo(String value) {
            addCriterion("qqRisk4 >=", value, "qqrisk4");
            return (Criteria) this;
        }

        public Criteria andQqrisk4LessThan(String value) {
            addCriterion("qqRisk4 <", value, "qqrisk4");
            return (Criteria) this;
        }

        public Criteria andQqrisk4LessThanOrEqualTo(String value) {
            addCriterion("qqRisk4 <=", value, "qqrisk4");
            return (Criteria) this;
        }

        public Criteria andQqrisk4Like(String value) {
            addCriterion("qqRisk4 like", value, "qqrisk4");
            return (Criteria) this;
        }

        public Criteria andQqrisk4NotLike(String value) {
            addCriterion("qqRisk4 not like", value, "qqrisk4");
            return (Criteria) this;
        }

        public Criteria andQqrisk4In(List<String> values) {
            addCriterion("qqRisk4 in", values, "qqrisk4");
            return (Criteria) this;
        }

        public Criteria andQqrisk4NotIn(List<String> values) {
            addCriterion("qqRisk4 not in", values, "qqrisk4");
            return (Criteria) this;
        }

        public Criteria andQqrisk4Between(String value1, String value2) {
            addCriterion("qqRisk4 between", value1, value2, "qqrisk4");
            return (Criteria) this;
        }

        public Criteria andQqrisk4NotBetween(String value1, String value2) {
            addCriterion("qqRisk4 not between", value1, value2, "qqrisk4");
            return (Criteria) this;
        }

        public Criteria andQqrisk5IsNull() {
            addCriterion("qqRisk5 is null");
            return (Criteria) this;
        }

        public Criteria andQqrisk5IsNotNull() {
            addCriterion("qqRisk5 is not null");
            return (Criteria) this;
        }

        public Criteria andQqrisk5EqualTo(String value) {
            addCriterion("qqRisk5 =", value, "qqrisk5");
            return (Criteria) this;
        }

        public Criteria andQqrisk5NotEqualTo(String value) {
            addCriterion("qqRisk5 <>", value, "qqrisk5");
            return (Criteria) this;
        }

        public Criteria andQqrisk5GreaterThan(String value) {
            addCriterion("qqRisk5 >", value, "qqrisk5");
            return (Criteria) this;
        }

        public Criteria andQqrisk5GreaterThanOrEqualTo(String value) {
            addCriterion("qqRisk5 >=", value, "qqrisk5");
            return (Criteria) this;
        }

        public Criteria andQqrisk5LessThan(String value) {
            addCriterion("qqRisk5 <", value, "qqrisk5");
            return (Criteria) this;
        }

        public Criteria andQqrisk5LessThanOrEqualTo(String value) {
            addCriterion("qqRisk5 <=", value, "qqrisk5");
            return (Criteria) this;
        }

        public Criteria andQqrisk5Like(String value) {
            addCriterion("qqRisk5 like", value, "qqrisk5");
            return (Criteria) this;
        }

        public Criteria andQqrisk5NotLike(String value) {
            addCriterion("qqRisk5 not like", value, "qqrisk5");
            return (Criteria) this;
        }

        public Criteria andQqrisk5In(List<String> values) {
            addCriterion("qqRisk5 in", values, "qqrisk5");
            return (Criteria) this;
        }

        public Criteria andQqrisk5NotIn(List<String> values) {
            addCriterion("qqRisk5 not in", values, "qqrisk5");
            return (Criteria) this;
        }

        public Criteria andQqrisk5Between(String value1, String value2) {
            addCriterion("qqRisk5 between", value1, value2, "qqrisk5");
            return (Criteria) this;
        }

        public Criteria andQqrisk5NotBetween(String value1, String value2) {
            addCriterion("qqRisk5 not between", value1, value2, "qqrisk5");
            return (Criteria) this;
        }

        public Criteria andCountqqriskIsNull() {
            addCriterion("countQqRisk is null");
            return (Criteria) this;
        }

        public Criteria andCountqqriskIsNotNull() {
            addCriterion("countQqRisk is not null");
            return (Criteria) this;
        }

        public Criteria andCountqqriskEqualTo(String value) {
            addCriterion("countQqRisk =", value, "countqqrisk");
            return (Criteria) this;
        }

        public Criteria andCountqqriskNotEqualTo(String value) {
            addCriterion("countQqRisk <>", value, "countqqrisk");
            return (Criteria) this;
        }

        public Criteria andCountqqriskGreaterThan(String value) {
            addCriterion("countQqRisk >", value, "countqqrisk");
            return (Criteria) this;
        }

        public Criteria andCountqqriskGreaterThanOrEqualTo(String value) {
            addCriterion("countQqRisk >=", value, "countqqrisk");
            return (Criteria) this;
        }

        public Criteria andCountqqriskLessThan(String value) {
            addCriterion("countQqRisk <", value, "countqqrisk");
            return (Criteria) this;
        }

        public Criteria andCountqqriskLessThanOrEqualTo(String value) {
            addCriterion("countQqRisk <=", value, "countqqrisk");
            return (Criteria) this;
        }

        public Criteria andCountqqriskLike(String value) {
            addCriterion("countQqRisk like", value, "countqqrisk");
            return (Criteria) this;
        }

        public Criteria andCountqqriskNotLike(String value) {
            addCriterion("countQqRisk not like", value, "countqqrisk");
            return (Criteria) this;
        }

        public Criteria andCountqqriskIn(List<String> values) {
            addCriterion("countQqRisk in", values, "countqqrisk");
            return (Criteria) this;
        }

        public Criteria andCountqqriskNotIn(List<String> values) {
            addCriterion("countQqRisk not in", values, "countqqrisk");
            return (Criteria) this;
        }

        public Criteria andCountqqriskBetween(String value1, String value2) {
            addCriterion("countQqRisk between", value1, value2, "countqqrisk");
            return (Criteria) this;
        }

        public Criteria andCountqqriskNotBetween(String value1, String value2) {
            addCriterion("countQqRisk not between", value1, value2, "countqqrisk");
            return (Criteria) this;
        }

        public Criteria andCompanyrisk1IsNull() {
            addCriterion("companyRisk1 is null");
            return (Criteria) this;
        }

        public Criteria andCompanyrisk1IsNotNull() {
            addCriterion("companyRisk1 is not null");
            return (Criteria) this;
        }

        public Criteria andCompanyrisk1EqualTo(String value) {
            addCriterion("companyRisk1 =", value, "companyrisk1");
            return (Criteria) this;
        }

        public Criteria andCompanyrisk1NotEqualTo(String value) {
            addCriterion("companyRisk1 <>", value, "companyrisk1");
            return (Criteria) this;
        }

        public Criteria andCompanyrisk1GreaterThan(String value) {
            addCriterion("companyRisk1 >", value, "companyrisk1");
            return (Criteria) this;
        }

        public Criteria andCompanyrisk1GreaterThanOrEqualTo(String value) {
            addCriterion("companyRisk1 >=", value, "companyrisk1");
            return (Criteria) this;
        }

        public Criteria andCompanyrisk1LessThan(String value) {
            addCriterion("companyRisk1 <", value, "companyrisk1");
            return (Criteria) this;
        }

        public Criteria andCompanyrisk1LessThanOrEqualTo(String value) {
            addCriterion("companyRisk1 <=", value, "companyrisk1");
            return (Criteria) this;
        }

        public Criteria andCompanyrisk1Like(String value) {
            addCriterion("companyRisk1 like", value, "companyrisk1");
            return (Criteria) this;
        }

        public Criteria andCompanyrisk1NotLike(String value) {
            addCriterion("companyRisk1 not like", value, "companyrisk1");
            return (Criteria) this;
        }

        public Criteria andCompanyrisk1In(List<String> values) {
            addCriterion("companyRisk1 in", values, "companyrisk1");
            return (Criteria) this;
        }

        public Criteria andCompanyrisk1NotIn(List<String> values) {
            addCriterion("companyRisk1 not in", values, "companyrisk1");
            return (Criteria) this;
        }

        public Criteria andCompanyrisk1Between(String value1, String value2) {
            addCriterion("companyRisk1 between", value1, value2, "companyrisk1");
            return (Criteria) this;
        }

        public Criteria andCompanyrisk1NotBetween(String value1, String value2) {
            addCriterion("companyRisk1 not between", value1, value2, "companyrisk1");
            return (Criteria) this;
        }

        public Criteria andCompanyrisk2IsNull() {
            addCriterion("companyRisk2 is null");
            return (Criteria) this;
        }

        public Criteria andCompanyrisk2IsNotNull() {
            addCriterion("companyRisk2 is not null");
            return (Criteria) this;
        }

        public Criteria andCompanyrisk2EqualTo(String value) {
            addCriterion("companyRisk2 =", value, "companyrisk2");
            return (Criteria) this;
        }

        public Criteria andCompanyrisk2NotEqualTo(String value) {
            addCriterion("companyRisk2 <>", value, "companyrisk2");
            return (Criteria) this;
        }

        public Criteria andCompanyrisk2GreaterThan(String value) {
            addCriterion("companyRisk2 >", value, "companyrisk2");
            return (Criteria) this;
        }

        public Criteria andCompanyrisk2GreaterThanOrEqualTo(String value) {
            addCriterion("companyRisk2 >=", value, "companyrisk2");
            return (Criteria) this;
        }

        public Criteria andCompanyrisk2LessThan(String value) {
            addCriterion("companyRisk2 <", value, "companyrisk2");
            return (Criteria) this;
        }

        public Criteria andCompanyrisk2LessThanOrEqualTo(String value) {
            addCriterion("companyRisk2 <=", value, "companyrisk2");
            return (Criteria) this;
        }

        public Criteria andCompanyrisk2Like(String value) {
            addCriterion("companyRisk2 like", value, "companyrisk2");
            return (Criteria) this;
        }

        public Criteria andCompanyrisk2NotLike(String value) {
            addCriterion("companyRisk2 not like", value, "companyrisk2");
            return (Criteria) this;
        }

        public Criteria andCompanyrisk2In(List<String> values) {
            addCriterion("companyRisk2 in", values, "companyrisk2");
            return (Criteria) this;
        }

        public Criteria andCompanyrisk2NotIn(List<String> values) {
            addCriterion("companyRisk2 not in", values, "companyrisk2");
            return (Criteria) this;
        }

        public Criteria andCompanyrisk2Between(String value1, String value2) {
            addCriterion("companyRisk2 between", value1, value2, "companyrisk2");
            return (Criteria) this;
        }

        public Criteria andCompanyrisk2NotBetween(String value1, String value2) {
            addCriterion("companyRisk2 not between", value1, value2, "companyrisk2");
            return (Criteria) this;
        }

        public Criteria andCountcompanyriskIsNull() {
            addCriterion("countCompanyRisk is null");
            return (Criteria) this;
        }

        public Criteria andCountcompanyriskIsNotNull() {
            addCriterion("countCompanyRisk is not null");
            return (Criteria) this;
        }

        public Criteria andCountcompanyriskEqualTo(String value) {
            addCriterion("countCompanyRisk =", value, "countcompanyrisk");
            return (Criteria) this;
        }

        public Criteria andCountcompanyriskNotEqualTo(String value) {
            addCriterion("countCompanyRisk <>", value, "countcompanyrisk");
            return (Criteria) this;
        }

        public Criteria andCountcompanyriskGreaterThan(String value) {
            addCriterion("countCompanyRisk >", value, "countcompanyrisk");
            return (Criteria) this;
        }

        public Criteria andCountcompanyriskGreaterThanOrEqualTo(String value) {
            addCriterion("countCompanyRisk >=", value, "countcompanyrisk");
            return (Criteria) this;
        }

        public Criteria andCountcompanyriskLessThan(String value) {
            addCriterion("countCompanyRisk <", value, "countcompanyrisk");
            return (Criteria) this;
        }

        public Criteria andCountcompanyriskLessThanOrEqualTo(String value) {
            addCriterion("countCompanyRisk <=", value, "countcompanyrisk");
            return (Criteria) this;
        }

        public Criteria andCountcompanyriskLike(String value) {
            addCriterion("countCompanyRisk like", value, "countcompanyrisk");
            return (Criteria) this;
        }

        public Criteria andCountcompanyriskNotLike(String value) {
            addCriterion("countCompanyRisk not like", value, "countcompanyrisk");
            return (Criteria) this;
        }

        public Criteria andCountcompanyriskIn(List<String> values) {
            addCriterion("countCompanyRisk in", values, "countcompanyrisk");
            return (Criteria) this;
        }

        public Criteria andCountcompanyriskNotIn(List<String> values) {
            addCriterion("countCompanyRisk not in", values, "countcompanyrisk");
            return (Criteria) this;
        }

        public Criteria andCountcompanyriskBetween(String value1, String value2) {
            addCriterion("countCompanyRisk between", value1, value2, "countcompanyrisk");
            return (Criteria) this;
        }

        public Criteria andCountcompanyriskNotBetween(String value1, String value2) {
            addCriterion("countCompanyRisk not between", value1, value2, "countcompanyrisk");
            return (Criteria) this;
        }

        public Criteria andApplyaddressrisk1IsNull() {
            addCriterion("applyAddressRisk1 is null");
            return (Criteria) this;
        }

        public Criteria andApplyaddressrisk1IsNotNull() {
            addCriterion("applyAddressRisk1 is not null");
            return (Criteria) this;
        }

        public Criteria andApplyaddressrisk1EqualTo(String value) {
            addCriterion("applyAddressRisk1 =", value, "applyaddressrisk1");
            return (Criteria) this;
        }

        public Criteria andApplyaddressrisk1NotEqualTo(String value) {
            addCriterion("applyAddressRisk1 <>", value, "applyaddressrisk1");
            return (Criteria) this;
        }

        public Criteria andApplyaddressrisk1GreaterThan(String value) {
            addCriterion("applyAddressRisk1 >", value, "applyaddressrisk1");
            return (Criteria) this;
        }

        public Criteria andApplyaddressrisk1GreaterThanOrEqualTo(String value) {
            addCriterion("applyAddressRisk1 >=", value, "applyaddressrisk1");
            return (Criteria) this;
        }

        public Criteria andApplyaddressrisk1LessThan(String value) {
            addCriterion("applyAddressRisk1 <", value, "applyaddressrisk1");
            return (Criteria) this;
        }

        public Criteria andApplyaddressrisk1LessThanOrEqualTo(String value) {
            addCriterion("applyAddressRisk1 <=", value, "applyaddressrisk1");
            return (Criteria) this;
        }

        public Criteria andApplyaddressrisk1Like(String value) {
            addCriterion("applyAddressRisk1 like", value, "applyaddressrisk1");
            return (Criteria) this;
        }

        public Criteria andApplyaddressrisk1NotLike(String value) {
            addCriterion("applyAddressRisk1 not like", value, "applyaddressrisk1");
            return (Criteria) this;
        }

        public Criteria andApplyaddressrisk1In(List<String> values) {
            addCriterion("applyAddressRisk1 in", values, "applyaddressrisk1");
            return (Criteria) this;
        }

        public Criteria andApplyaddressrisk1NotIn(List<String> values) {
            addCriterion("applyAddressRisk1 not in", values, "applyaddressrisk1");
            return (Criteria) this;
        }

        public Criteria andApplyaddressrisk1Between(String value1, String value2) {
            addCriterion("applyAddressRisk1 between", value1, value2, "applyaddressrisk1");
            return (Criteria) this;
        }

        public Criteria andApplyaddressrisk1NotBetween(String value1, String value2) {
            addCriterion("applyAddressRisk1 not between", value1, value2, "applyaddressrisk1");
            return (Criteria) this;
        }

        public Criteria andCountaddressriskIsNull() {
            addCriterion("countAddressRisk is null");
            return (Criteria) this;
        }

        public Criteria andCountaddressriskIsNotNull() {
            addCriterion("countAddressRisk is not null");
            return (Criteria) this;
        }

        public Criteria andCountaddressriskEqualTo(String value) {
            addCriterion("countAddressRisk =", value, "countaddressrisk");
            return (Criteria) this;
        }

        public Criteria andCountaddressriskNotEqualTo(String value) {
            addCriterion("countAddressRisk <>", value, "countaddressrisk");
            return (Criteria) this;
        }

        public Criteria andCountaddressriskGreaterThan(String value) {
            addCriterion("countAddressRisk >", value, "countaddressrisk");
            return (Criteria) this;
        }

        public Criteria andCountaddressriskGreaterThanOrEqualTo(String value) {
            addCriterion("countAddressRisk >=", value, "countaddressrisk");
            return (Criteria) this;
        }

        public Criteria andCountaddressriskLessThan(String value) {
            addCriterion("countAddressRisk <", value, "countaddressrisk");
            return (Criteria) this;
        }

        public Criteria andCountaddressriskLessThanOrEqualTo(String value) {
            addCriterion("countAddressRisk <=", value, "countaddressrisk");
            return (Criteria) this;
        }

        public Criteria andCountaddressriskLike(String value) {
            addCriterion("countAddressRisk like", value, "countaddressrisk");
            return (Criteria) this;
        }

        public Criteria andCountaddressriskNotLike(String value) {
            addCriterion("countAddressRisk not like", value, "countaddressrisk");
            return (Criteria) this;
        }

        public Criteria andCountaddressriskIn(List<String> values) {
            addCriterion("countAddressRisk in", values, "countaddressrisk");
            return (Criteria) this;
        }

        public Criteria andCountaddressriskNotIn(List<String> values) {
            addCriterion("countAddressRisk not in", values, "countaddressrisk");
            return (Criteria) this;
        }

        public Criteria andCountaddressriskBetween(String value1, String value2) {
            addCriterion("countAddressRisk between", value1, value2, "countaddressrisk");
            return (Criteria) this;
        }

        public Criteria andCountaddressriskNotBetween(String value1, String value2) {
            addCriterion("countAddressRisk not between", value1, value2, "countaddressrisk");
            return (Criteria) this;
        }

        public Criteria andQuerytimescodeIsNull() {
            addCriterion("queryTimesCode is null");
            return (Criteria) this;
        }

        public Criteria andQuerytimescodeIsNotNull() {
            addCriterion("queryTimesCode is not null");
            return (Criteria) this;
        }

        public Criteria andQuerytimescodeEqualTo(String value) {
            addCriterion("queryTimesCode =", value, "querytimescode");
            return (Criteria) this;
        }

        public Criteria andQuerytimescodeNotEqualTo(String value) {
            addCriterion("queryTimesCode <>", value, "querytimescode");
            return (Criteria) this;
        }

        public Criteria andQuerytimescodeGreaterThan(String value) {
            addCriterion("queryTimesCode >", value, "querytimescode");
            return (Criteria) this;
        }

        public Criteria andQuerytimescodeGreaterThanOrEqualTo(String value) {
            addCriterion("queryTimesCode >=", value, "querytimescode");
            return (Criteria) this;
        }

        public Criteria andQuerytimescodeLessThan(String value) {
            addCriterion("queryTimesCode <", value, "querytimescode");
            return (Criteria) this;
        }

        public Criteria andQuerytimescodeLessThanOrEqualTo(String value) {
            addCriterion("queryTimesCode <=", value, "querytimescode");
            return (Criteria) this;
        }

        public Criteria andQuerytimescodeLike(String value) {
            addCriterion("queryTimesCode like", value, "querytimescode");
            return (Criteria) this;
        }

        public Criteria andQuerytimescodeNotLike(String value) {
            addCriterion("queryTimesCode not like", value, "querytimescode");
            return (Criteria) this;
        }

        public Criteria andQuerytimescodeIn(List<String> values) {
            addCriterion("queryTimesCode in", values, "querytimescode");
            return (Criteria) this;
        }

        public Criteria andQuerytimescodeNotIn(List<String> values) {
            addCriterion("queryTimesCode not in", values, "querytimescode");
            return (Criteria) this;
        }

        public Criteria andQuerytimescodeBetween(String value1, String value2) {
            addCriterion("queryTimesCode between", value1, value2, "querytimescode");
            return (Criteria) this;
        }

        public Criteria andQuerytimescodeNotBetween(String value1, String value2) {
            addCriterion("queryTimesCode not between", value1, value2, "querytimescode");
            return (Criteria) this;
        }

        public Criteria andQuerytimesinoneyeaIsNull() {
            addCriterion("queryTimesInOneYea is null");
            return (Criteria) this;
        }

        public Criteria andQuerytimesinoneyeaIsNotNull() {
            addCriterion("queryTimesInOneYea is not null");
            return (Criteria) this;
        }

        public Criteria andQuerytimesinoneyeaEqualTo(String value) {
            addCriterion("queryTimesInOneYea =", value, "querytimesinoneyea");
            return (Criteria) this;
        }

        public Criteria andQuerytimesinoneyeaNotEqualTo(String value) {
            addCriterion("queryTimesInOneYea <>", value, "querytimesinoneyea");
            return (Criteria) this;
        }

        public Criteria andQuerytimesinoneyeaGreaterThan(String value) {
            addCriterion("queryTimesInOneYea >", value, "querytimesinoneyea");
            return (Criteria) this;
        }

        public Criteria andQuerytimesinoneyeaGreaterThanOrEqualTo(String value) {
            addCriterion("queryTimesInOneYea >=", value, "querytimesinoneyea");
            return (Criteria) this;
        }

        public Criteria andQuerytimesinoneyeaLessThan(String value) {
            addCriterion("queryTimesInOneYea <", value, "querytimesinoneyea");
            return (Criteria) this;
        }

        public Criteria andQuerytimesinoneyeaLessThanOrEqualTo(String value) {
            addCriterion("queryTimesInOneYea <=", value, "querytimesinoneyea");
            return (Criteria) this;
        }

        public Criteria andQuerytimesinoneyeaLike(String value) {
            addCriterion("queryTimesInOneYea like", value, "querytimesinoneyea");
            return (Criteria) this;
        }

        public Criteria andQuerytimesinoneyeaNotLike(String value) {
            addCriterion("queryTimesInOneYea not like", value, "querytimesinoneyea");
            return (Criteria) this;
        }

        public Criteria andQuerytimesinoneyeaIn(List<String> values) {
            addCriterion("queryTimesInOneYea in", values, "querytimesinoneyea");
            return (Criteria) this;
        }

        public Criteria andQuerytimesinoneyeaNotIn(List<String> values) {
            addCriterion("queryTimesInOneYea not in", values, "querytimesinoneyea");
            return (Criteria) this;
        }

        public Criteria andQuerytimesinoneyeaBetween(String value1, String value2) {
            addCriterion("queryTimesInOneYea between", value1, value2, "querytimesinoneyea");
            return (Criteria) this;
        }

        public Criteria andQuerytimesinoneyeaNotBetween(String value1, String value2) {
            addCriterion("queryTimesInOneYea not between", value1, value2, "querytimesinoneyea");
            return (Criteria) this;
        }

        public Criteria andQuerytimesinsixIsNull() {
            addCriterion("queryTimesInSix is null");
            return (Criteria) this;
        }

        public Criteria andQuerytimesinsixIsNotNull() {
            addCriterion("queryTimesInSix is not null");
            return (Criteria) this;
        }

        public Criteria andQuerytimesinsixEqualTo(String value) {
            addCriterion("queryTimesInSix =", value, "querytimesinsix");
            return (Criteria) this;
        }

        public Criteria andQuerytimesinsixNotEqualTo(String value) {
            addCriterion("queryTimesInSix <>", value, "querytimesinsix");
            return (Criteria) this;
        }

        public Criteria andQuerytimesinsixGreaterThan(String value) {
            addCriterion("queryTimesInSix >", value, "querytimesinsix");
            return (Criteria) this;
        }

        public Criteria andQuerytimesinsixGreaterThanOrEqualTo(String value) {
            addCriterion("queryTimesInSix >=", value, "querytimesinsix");
            return (Criteria) this;
        }

        public Criteria andQuerytimesinsixLessThan(String value) {
            addCriterion("queryTimesInSix <", value, "querytimesinsix");
            return (Criteria) this;
        }

        public Criteria andQuerytimesinsixLessThanOrEqualTo(String value) {
            addCriterion("queryTimesInSix <=", value, "querytimesinsix");
            return (Criteria) this;
        }

        public Criteria andQuerytimesinsixLike(String value) {
            addCriterion("queryTimesInSix like", value, "querytimesinsix");
            return (Criteria) this;
        }

        public Criteria andQuerytimesinsixNotLike(String value) {
            addCriterion("queryTimesInSix not like", value, "querytimesinsix");
            return (Criteria) this;
        }

        public Criteria andQuerytimesinsixIn(List<String> values) {
            addCriterion("queryTimesInSix in", values, "querytimesinsix");
            return (Criteria) this;
        }

        public Criteria andQuerytimesinsixNotIn(List<String> values) {
            addCriterion("queryTimesInSix not in", values, "querytimesinsix");
            return (Criteria) this;
        }

        public Criteria andQuerytimesinsixBetween(String value1, String value2) {
            addCriterion("queryTimesInSix between", value1, value2, "querytimesinsix");
            return (Criteria) this;
        }

        public Criteria andQuerytimesinsixNotBetween(String value1, String value2) {
            addCriterion("queryTimesInSix not between", value1, value2, "querytimesinsix");
            return (Criteria) this;
        }

        public Criteria andQuerytimesinthreeIsNull() {
            addCriterion("queryTimesInThree is null");
            return (Criteria) this;
        }

        public Criteria andQuerytimesinthreeIsNotNull() {
            addCriterion("queryTimesInThree is not null");
            return (Criteria) this;
        }

        public Criteria andQuerytimesinthreeEqualTo(String value) {
            addCriterion("queryTimesInThree =", value, "querytimesinthree");
            return (Criteria) this;
        }

        public Criteria andQuerytimesinthreeNotEqualTo(String value) {
            addCriterion("queryTimesInThree <>", value, "querytimesinthree");
            return (Criteria) this;
        }

        public Criteria andQuerytimesinthreeGreaterThan(String value) {
            addCriterion("queryTimesInThree >", value, "querytimesinthree");
            return (Criteria) this;
        }

        public Criteria andQuerytimesinthreeGreaterThanOrEqualTo(String value) {
            addCriterion("queryTimesInThree >=", value, "querytimesinthree");
            return (Criteria) this;
        }

        public Criteria andQuerytimesinthreeLessThan(String value) {
            addCriterion("queryTimesInThree <", value, "querytimesinthree");
            return (Criteria) this;
        }

        public Criteria andQuerytimesinthreeLessThanOrEqualTo(String value) {
            addCriterion("queryTimesInThree <=", value, "querytimesinthree");
            return (Criteria) this;
        }

        public Criteria andQuerytimesinthreeLike(String value) {
            addCriterion("queryTimesInThree like", value, "querytimesinthree");
            return (Criteria) this;
        }

        public Criteria andQuerytimesinthreeNotLike(String value) {
            addCriterion("queryTimesInThree not like", value, "querytimesinthree");
            return (Criteria) this;
        }

        public Criteria andQuerytimesinthreeIn(List<String> values) {
            addCriterion("queryTimesInThree in", values, "querytimesinthree");
            return (Criteria) this;
        }

        public Criteria andQuerytimesinthreeNotIn(List<String> values) {
            addCriterion("queryTimesInThree not in", values, "querytimesinthree");
            return (Criteria) this;
        }

        public Criteria andQuerytimesinthreeBetween(String value1, String value2) {
            addCriterion("queryTimesInThree between", value1, value2, "querytimesinthree");
            return (Criteria) this;
        }

        public Criteria andQuerytimesinthreeNotBetween(String value1, String value2) {
            addCriterion("queryTimesInThree not between", value1, value2, "querytimesinthree");
            return (Criteria) this;
        }

        public Criteria andQuerytimesintwoyearIsNull() {
            addCriterion("queryTimesInTwoYear is null");
            return (Criteria) this;
        }

        public Criteria andQuerytimesintwoyearIsNotNull() {
            addCriterion("queryTimesInTwoYear is not null");
            return (Criteria) this;
        }

        public Criteria andQuerytimesintwoyearEqualTo(String value) {
            addCriterion("queryTimesInTwoYear =", value, "querytimesintwoyear");
            return (Criteria) this;
        }

        public Criteria andQuerytimesintwoyearNotEqualTo(String value) {
            addCriterion("queryTimesInTwoYear <>", value, "querytimesintwoyear");
            return (Criteria) this;
        }

        public Criteria andQuerytimesintwoyearGreaterThan(String value) {
            addCriterion("queryTimesInTwoYear >", value, "querytimesintwoyear");
            return (Criteria) this;
        }

        public Criteria andQuerytimesintwoyearGreaterThanOrEqualTo(String value) {
            addCriterion("queryTimesInTwoYear >=", value, "querytimesintwoyear");
            return (Criteria) this;
        }

        public Criteria andQuerytimesintwoyearLessThan(String value) {
            addCriterion("queryTimesInTwoYear <", value, "querytimesintwoyear");
            return (Criteria) this;
        }

        public Criteria andQuerytimesintwoyearLessThanOrEqualTo(String value) {
            addCriterion("queryTimesInTwoYear <=", value, "querytimesintwoyear");
            return (Criteria) this;
        }

        public Criteria andQuerytimesintwoyearLike(String value) {
            addCriterion("queryTimesInTwoYear like", value, "querytimesintwoyear");
            return (Criteria) this;
        }

        public Criteria andQuerytimesintwoyearNotLike(String value) {
            addCriterion("queryTimesInTwoYear not like", value, "querytimesintwoyear");
            return (Criteria) this;
        }

        public Criteria andQuerytimesintwoyearIn(List<String> values) {
            addCriterion("queryTimesInTwoYear in", values, "querytimesintwoyear");
            return (Criteria) this;
        }

        public Criteria andQuerytimesintwoyearNotIn(List<String> values) {
            addCriterion("queryTimesInTwoYear not in", values, "querytimesintwoyear");
            return (Criteria) this;
        }

        public Criteria andQuerytimesintwoyearBetween(String value1, String value2) {
            addCriterion("queryTimesInTwoYear between", value1, value2, "querytimesintwoyear");
            return (Criteria) this;
        }

        public Criteria andQuerytimesintwoyearNotBetween(String value1, String value2) {
            addCriterion("queryTimesInTwoYear not between", value1, value2, "querytimesintwoyear");
            return (Criteria) this;
        }

        public Criteria andHavesifaIsNull() {
            addCriterion("haveSifa is null");
            return (Criteria) this;
        }

        public Criteria andHavesifaIsNotNull() {
            addCriterion("haveSifa is not null");
            return (Criteria) this;
        }

        public Criteria andHavesifaEqualTo(String value) {
            addCriterion("haveSifa =", value, "havesifa");
            return (Criteria) this;
        }

        public Criteria andHavesifaNotEqualTo(String value) {
            addCriterion("haveSifa <>", value, "havesifa");
            return (Criteria) this;
        }

        public Criteria andHavesifaGreaterThan(String value) {
            addCriterion("haveSifa >", value, "havesifa");
            return (Criteria) this;
        }

        public Criteria andHavesifaGreaterThanOrEqualTo(String value) {
            addCriterion("haveSifa >=", value, "havesifa");
            return (Criteria) this;
        }

        public Criteria andHavesifaLessThan(String value) {
            addCriterion("haveSifa <", value, "havesifa");
            return (Criteria) this;
        }

        public Criteria andHavesifaLessThanOrEqualTo(String value) {
            addCriterion("haveSifa <=", value, "havesifa");
            return (Criteria) this;
        }

        public Criteria andHavesifaLike(String value) {
            addCriterion("haveSifa like", value, "havesifa");
            return (Criteria) this;
        }

        public Criteria andHavesifaNotLike(String value) {
            addCriterion("haveSifa not like", value, "havesifa");
            return (Criteria) this;
        }

        public Criteria andHavesifaIn(List<String> values) {
            addCriterion("haveSifa in", values, "havesifa");
            return (Criteria) this;
        }

        public Criteria andHavesifaNotIn(List<String> values) {
            addCriterion("haveSifa not in", values, "havesifa");
            return (Criteria) this;
        }

        public Criteria andHavesifaBetween(String value1, String value2) {
            addCriterion("haveSifa between", value1, value2, "havesifa");
            return (Criteria) this;
        }

        public Criteria andHavesifaNotBetween(String value1, String value2) {
            addCriterion("haveSifa not between", value1, value2, "havesifa");
            return (Criteria) this;
        }

        public Criteria andSifacodeIsNull() {
            addCriterion("sifaCode is null");
            return (Criteria) this;
        }

        public Criteria andSifacodeIsNotNull() {
            addCriterion("sifaCode is not null");
            return (Criteria) this;
        }

        public Criteria andSifacodeEqualTo(String value) {
            addCriterion("sifaCode =", value, "sifacode");
            return (Criteria) this;
        }

        public Criteria andSifacodeNotEqualTo(String value) {
            addCriterion("sifaCode <>", value, "sifacode");
            return (Criteria) this;
        }

        public Criteria andSifacodeGreaterThan(String value) {
            addCriterion("sifaCode >", value, "sifacode");
            return (Criteria) this;
        }

        public Criteria andSifacodeGreaterThanOrEqualTo(String value) {
            addCriterion("sifaCode >=", value, "sifacode");
            return (Criteria) this;
        }

        public Criteria andSifacodeLessThan(String value) {
            addCriterion("sifaCode <", value, "sifacode");
            return (Criteria) this;
        }

        public Criteria andSifacodeLessThanOrEqualTo(String value) {
            addCriterion("sifaCode <=", value, "sifacode");
            return (Criteria) this;
        }

        public Criteria andSifacodeLike(String value) {
            addCriterion("sifaCode like", value, "sifacode");
            return (Criteria) this;
        }

        public Criteria andSifacodeNotLike(String value) {
            addCriterion("sifaCode not like", value, "sifacode");
            return (Criteria) this;
        }

        public Criteria andSifacodeIn(List<String> values) {
            addCriterion("sifaCode in", values, "sifacode");
            return (Criteria) this;
        }

        public Criteria andSifacodeNotIn(List<String> values) {
            addCriterion("sifaCode not in", values, "sifacode");
            return (Criteria) this;
        }

        public Criteria andSifacodeBetween(String value1, String value2) {
            addCriterion("sifaCode between", value1, value2, "sifacode");
            return (Criteria) this;
        }

        public Criteria andSifacodeNotBetween(String value1, String value2) {
            addCriterion("sifaCode not between", value1, value2, "sifacode");
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