package com.hyjf.am.user.dao.model.auto;

import java.util.ArrayList;
import java.util.List;

public class MspDegreeresultExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public MspDegreeresultExample() {
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

        public Criteria andStatusIsNull() {
            addCriterion("`status` is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("`status` is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(String value) {
            addCriterion("`status` =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(String value) {
            addCriterion("`status` <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(String value) {
            addCriterion("`status` >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(String value) {
            addCriterion("`status` >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(String value) {
            addCriterion("`status` <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(String value) {
            addCriterion("`status` <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLike(String value) {
            addCriterion("`status` like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotLike(String value) {
            addCriterion("`status` not like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<String> values) {
            addCriterion("`status` in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<String> values) {
            addCriterion("`status` not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(String value1, String value2) {
            addCriterion("`status` between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(String value1, String value2) {
            addCriterion("`status` not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andSchoolIsNull() {
            addCriterion("school is null");
            return (Criteria) this;
        }

        public Criteria andSchoolIsNotNull() {
            addCriterion("school is not null");
            return (Criteria) this;
        }

        public Criteria andSchoolEqualTo(String value) {
            addCriterion("school =", value, "school");
            return (Criteria) this;
        }

        public Criteria andSchoolNotEqualTo(String value) {
            addCriterion("school <>", value, "school");
            return (Criteria) this;
        }

        public Criteria andSchoolGreaterThan(String value) {
            addCriterion("school >", value, "school");
            return (Criteria) this;
        }

        public Criteria andSchoolGreaterThanOrEqualTo(String value) {
            addCriterion("school >=", value, "school");
            return (Criteria) this;
        }

        public Criteria andSchoolLessThan(String value) {
            addCriterion("school <", value, "school");
            return (Criteria) this;
        }

        public Criteria andSchoolLessThanOrEqualTo(String value) {
            addCriterion("school <=", value, "school");
            return (Criteria) this;
        }

        public Criteria andSchoolLike(String value) {
            addCriterion("school like", value, "school");
            return (Criteria) this;
        }

        public Criteria andSchoolNotLike(String value) {
            addCriterion("school not like", value, "school");
            return (Criteria) this;
        }

        public Criteria andSchoolIn(List<String> values) {
            addCriterion("school in", values, "school");
            return (Criteria) this;
        }

        public Criteria andSchoolNotIn(List<String> values) {
            addCriterion("school not in", values, "school");
            return (Criteria) this;
        }

        public Criteria andSchoolBetween(String value1, String value2) {
            addCriterion("school between", value1, value2, "school");
            return (Criteria) this;
        }

        public Criteria andSchoolNotBetween(String value1, String value2) {
            addCriterion("school not between", value1, value2, "school");
            return (Criteria) this;
        }

        public Criteria andDegreeIsNull() {
            addCriterion("`degree` is null");
            return (Criteria) this;
        }

        public Criteria andDegreeIsNotNull() {
            addCriterion("`degree` is not null");
            return (Criteria) this;
        }

        public Criteria andDegreeEqualTo(String value) {
            addCriterion("`degree` =", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeNotEqualTo(String value) {
            addCriterion("`degree` <>", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeGreaterThan(String value) {
            addCriterion("`degree` >", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeGreaterThanOrEqualTo(String value) {
            addCriterion("`degree` >=", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeLessThan(String value) {
            addCriterion("`degree` <", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeLessThanOrEqualTo(String value) {
            addCriterion("`degree` <=", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeLike(String value) {
            addCriterion("`degree` like", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeNotLike(String value) {
            addCriterion("`degree` not like", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeIn(List<String> values) {
            addCriterion("`degree` in", values, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeNotIn(List<String> values) {
            addCriterion("`degree` not in", values, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeBetween(String value1, String value2) {
            addCriterion("`degree` between", value1, value2, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeNotBetween(String value1, String value2) {
            addCriterion("`degree` not between", value1, value2, "degree");
            return (Criteria) this;
        }

        public Criteria andAdmissionyearIsNull() {
            addCriterion("admissionYear is null");
            return (Criteria) this;
        }

        public Criteria andAdmissionyearIsNotNull() {
            addCriterion("admissionYear is not null");
            return (Criteria) this;
        }

        public Criteria andAdmissionyearEqualTo(String value) {
            addCriterion("admissionYear =", value, "admissionyear");
            return (Criteria) this;
        }

        public Criteria andAdmissionyearNotEqualTo(String value) {
            addCriterion("admissionYear <>", value, "admissionyear");
            return (Criteria) this;
        }

        public Criteria andAdmissionyearGreaterThan(String value) {
            addCriterion("admissionYear >", value, "admissionyear");
            return (Criteria) this;
        }

        public Criteria andAdmissionyearGreaterThanOrEqualTo(String value) {
            addCriterion("admissionYear >=", value, "admissionyear");
            return (Criteria) this;
        }

        public Criteria andAdmissionyearLessThan(String value) {
            addCriterion("admissionYear <", value, "admissionyear");
            return (Criteria) this;
        }

        public Criteria andAdmissionyearLessThanOrEqualTo(String value) {
            addCriterion("admissionYear <=", value, "admissionyear");
            return (Criteria) this;
        }

        public Criteria andAdmissionyearLike(String value) {
            addCriterion("admissionYear like", value, "admissionyear");
            return (Criteria) this;
        }

        public Criteria andAdmissionyearNotLike(String value) {
            addCriterion("admissionYear not like", value, "admissionyear");
            return (Criteria) this;
        }

        public Criteria andAdmissionyearIn(List<String> values) {
            addCriterion("admissionYear in", values, "admissionyear");
            return (Criteria) this;
        }

        public Criteria andAdmissionyearNotIn(List<String> values) {
            addCriterion("admissionYear not in", values, "admissionyear");
            return (Criteria) this;
        }

        public Criteria andAdmissionyearBetween(String value1, String value2) {
            addCriterion("admissionYear between", value1, value2, "admissionyear");
            return (Criteria) this;
        }

        public Criteria andAdmissionyearNotBetween(String value1, String value2) {
            addCriterion("admissionYear not between", value1, value2, "admissionyear");
            return (Criteria) this;
        }

        public Criteria andMajorIsNull() {
            addCriterion("major is null");
            return (Criteria) this;
        }

        public Criteria andMajorIsNotNull() {
            addCriterion("major is not null");
            return (Criteria) this;
        }

        public Criteria andMajorEqualTo(String value) {
            addCriterion("major =", value, "major");
            return (Criteria) this;
        }

        public Criteria andMajorNotEqualTo(String value) {
            addCriterion("major <>", value, "major");
            return (Criteria) this;
        }

        public Criteria andMajorGreaterThan(String value) {
            addCriterion("major >", value, "major");
            return (Criteria) this;
        }

        public Criteria andMajorGreaterThanOrEqualTo(String value) {
            addCriterion("major >=", value, "major");
            return (Criteria) this;
        }

        public Criteria andMajorLessThan(String value) {
            addCriterion("major <", value, "major");
            return (Criteria) this;
        }

        public Criteria andMajorLessThanOrEqualTo(String value) {
            addCriterion("major <=", value, "major");
            return (Criteria) this;
        }

        public Criteria andMajorLike(String value) {
            addCriterion("major like", value, "major");
            return (Criteria) this;
        }

        public Criteria andMajorNotLike(String value) {
            addCriterion("major not like", value, "major");
            return (Criteria) this;
        }

        public Criteria andMajorIn(List<String> values) {
            addCriterion("major in", values, "major");
            return (Criteria) this;
        }

        public Criteria andMajorNotIn(List<String> values) {
            addCriterion("major not in", values, "major");
            return (Criteria) this;
        }

        public Criteria andMajorBetween(String value1, String value2) {
            addCriterion("major between", value1, value2, "major");
            return (Criteria) this;
        }

        public Criteria andMajorNotBetween(String value1, String value2) {
            addCriterion("major not between", value1, value2, "major");
            return (Criteria) this;
        }

        public Criteria andGraduationtimeIsNull() {
            addCriterion("graduationTime is null");
            return (Criteria) this;
        }

        public Criteria andGraduationtimeIsNotNull() {
            addCriterion("graduationTime is not null");
            return (Criteria) this;
        }

        public Criteria andGraduationtimeEqualTo(String value) {
            addCriterion("graduationTime =", value, "graduationtime");
            return (Criteria) this;
        }

        public Criteria andGraduationtimeNotEqualTo(String value) {
            addCriterion("graduationTime <>", value, "graduationtime");
            return (Criteria) this;
        }

        public Criteria andGraduationtimeGreaterThan(String value) {
            addCriterion("graduationTime >", value, "graduationtime");
            return (Criteria) this;
        }

        public Criteria andGraduationtimeGreaterThanOrEqualTo(String value) {
            addCriterion("graduationTime >=", value, "graduationtime");
            return (Criteria) this;
        }

        public Criteria andGraduationtimeLessThan(String value) {
            addCriterion("graduationTime <", value, "graduationtime");
            return (Criteria) this;
        }

        public Criteria andGraduationtimeLessThanOrEqualTo(String value) {
            addCriterion("graduationTime <=", value, "graduationtime");
            return (Criteria) this;
        }

        public Criteria andGraduationtimeLike(String value) {
            addCriterion("graduationTime like", value, "graduationtime");
            return (Criteria) this;
        }

        public Criteria andGraduationtimeNotLike(String value) {
            addCriterion("graduationTime not like", value, "graduationtime");
            return (Criteria) this;
        }

        public Criteria andGraduationtimeIn(List<String> values) {
            addCriterion("graduationTime in", values, "graduationtime");
            return (Criteria) this;
        }

        public Criteria andGraduationtimeNotIn(List<String> values) {
            addCriterion("graduationTime not in", values, "graduationtime");
            return (Criteria) this;
        }

        public Criteria andGraduationtimeBetween(String value1, String value2) {
            addCriterion("graduationTime between", value1, value2, "graduationtime");
            return (Criteria) this;
        }

        public Criteria andGraduationtimeNotBetween(String value1, String value2) {
            addCriterion("graduationTime not between", value1, value2, "graduationtime");
            return (Criteria) this;
        }

        public Criteria andGraduationconclusionIsNull() {
            addCriterion("graduationConclusion is null");
            return (Criteria) this;
        }

        public Criteria andGraduationconclusionIsNotNull() {
            addCriterion("graduationConclusion is not null");
            return (Criteria) this;
        }

        public Criteria andGraduationconclusionEqualTo(String value) {
            addCriterion("graduationConclusion =", value, "graduationconclusion");
            return (Criteria) this;
        }

        public Criteria andGraduationconclusionNotEqualTo(String value) {
            addCriterion("graduationConclusion <>", value, "graduationconclusion");
            return (Criteria) this;
        }

        public Criteria andGraduationconclusionGreaterThan(String value) {
            addCriterion("graduationConclusion >", value, "graduationconclusion");
            return (Criteria) this;
        }

        public Criteria andGraduationconclusionGreaterThanOrEqualTo(String value) {
            addCriterion("graduationConclusion >=", value, "graduationconclusion");
            return (Criteria) this;
        }

        public Criteria andGraduationconclusionLessThan(String value) {
            addCriterion("graduationConclusion <", value, "graduationconclusion");
            return (Criteria) this;
        }

        public Criteria andGraduationconclusionLessThanOrEqualTo(String value) {
            addCriterion("graduationConclusion <=", value, "graduationconclusion");
            return (Criteria) this;
        }

        public Criteria andGraduationconclusionLike(String value) {
            addCriterion("graduationConclusion like", value, "graduationconclusion");
            return (Criteria) this;
        }

        public Criteria andGraduationconclusionNotLike(String value) {
            addCriterion("graduationConclusion not like", value, "graduationconclusion");
            return (Criteria) this;
        }

        public Criteria andGraduationconclusionIn(List<String> values) {
            addCriterion("graduationConclusion in", values, "graduationconclusion");
            return (Criteria) this;
        }

        public Criteria andGraduationconclusionNotIn(List<String> values) {
            addCriterion("graduationConclusion not in", values, "graduationconclusion");
            return (Criteria) this;
        }

        public Criteria andGraduationconclusionBetween(String value1, String value2) {
            addCriterion("graduationConclusion between", value1, value2, "graduationconclusion");
            return (Criteria) this;
        }

        public Criteria andGraduationconclusionNotBetween(String value1, String value2) {
            addCriterion("graduationConclusion not between", value1, value2, "graduationconclusion");
            return (Criteria) this;
        }

        public Criteria andDegreetypeIsNull() {
            addCriterion("degreeType is null");
            return (Criteria) this;
        }

        public Criteria andDegreetypeIsNotNull() {
            addCriterion("degreeType is not null");
            return (Criteria) this;
        }

        public Criteria andDegreetypeEqualTo(String value) {
            addCriterion("degreeType =", value, "degreetype");
            return (Criteria) this;
        }

        public Criteria andDegreetypeNotEqualTo(String value) {
            addCriterion("degreeType <>", value, "degreetype");
            return (Criteria) this;
        }

        public Criteria andDegreetypeGreaterThan(String value) {
            addCriterion("degreeType >", value, "degreetype");
            return (Criteria) this;
        }

        public Criteria andDegreetypeGreaterThanOrEqualTo(String value) {
            addCriterion("degreeType >=", value, "degreetype");
            return (Criteria) this;
        }

        public Criteria andDegreetypeLessThan(String value) {
            addCriterion("degreeType <", value, "degreetype");
            return (Criteria) this;
        }

        public Criteria andDegreetypeLessThanOrEqualTo(String value) {
            addCriterion("degreeType <=", value, "degreetype");
            return (Criteria) this;
        }

        public Criteria andDegreetypeLike(String value) {
            addCriterion("degreeType like", value, "degreetype");
            return (Criteria) this;
        }

        public Criteria andDegreetypeNotLike(String value) {
            addCriterion("degreeType not like", value, "degreetype");
            return (Criteria) this;
        }

        public Criteria andDegreetypeIn(List<String> values) {
            addCriterion("degreeType in", values, "degreetype");
            return (Criteria) this;
        }

        public Criteria andDegreetypeNotIn(List<String> values) {
            addCriterion("degreeType not in", values, "degreetype");
            return (Criteria) this;
        }

        public Criteria andDegreetypeBetween(String value1, String value2) {
            addCriterion("degreeType between", value1, value2, "degreetype");
            return (Criteria) this;
        }

        public Criteria andDegreetypeNotBetween(String value1, String value2) {
            addCriterion("degreeType not between", value1, value2, "degreetype");
            return (Criteria) this;
        }

        public Criteria andSchoolnatureIsNull() {
            addCriterion("schoolNature is null");
            return (Criteria) this;
        }

        public Criteria andSchoolnatureIsNotNull() {
            addCriterion("schoolNature is not null");
            return (Criteria) this;
        }

        public Criteria andSchoolnatureEqualTo(String value) {
            addCriterion("schoolNature =", value, "schoolnature");
            return (Criteria) this;
        }

        public Criteria andSchoolnatureNotEqualTo(String value) {
            addCriterion("schoolNature <>", value, "schoolnature");
            return (Criteria) this;
        }

        public Criteria andSchoolnatureGreaterThan(String value) {
            addCriterion("schoolNature >", value, "schoolnature");
            return (Criteria) this;
        }

        public Criteria andSchoolnatureGreaterThanOrEqualTo(String value) {
            addCriterion("schoolNature >=", value, "schoolnature");
            return (Criteria) this;
        }

        public Criteria andSchoolnatureLessThan(String value) {
            addCriterion("schoolNature <", value, "schoolnature");
            return (Criteria) this;
        }

        public Criteria andSchoolnatureLessThanOrEqualTo(String value) {
            addCriterion("schoolNature <=", value, "schoolnature");
            return (Criteria) this;
        }

        public Criteria andSchoolnatureLike(String value) {
            addCriterion("schoolNature like", value, "schoolnature");
            return (Criteria) this;
        }

        public Criteria andSchoolnatureNotLike(String value) {
            addCriterion("schoolNature not like", value, "schoolnature");
            return (Criteria) this;
        }

        public Criteria andSchoolnatureIn(List<String> values) {
            addCriterion("schoolNature in", values, "schoolnature");
            return (Criteria) this;
        }

        public Criteria andSchoolnatureNotIn(List<String> values) {
            addCriterion("schoolNature not in", values, "schoolnature");
            return (Criteria) this;
        }

        public Criteria andSchoolnatureBetween(String value1, String value2) {
            addCriterion("schoolNature between", value1, value2, "schoolnature");
            return (Criteria) this;
        }

        public Criteria andSchoolnatureNotBetween(String value1, String value2) {
            addCriterion("schoolNature not between", value1, value2, "schoolnature");
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