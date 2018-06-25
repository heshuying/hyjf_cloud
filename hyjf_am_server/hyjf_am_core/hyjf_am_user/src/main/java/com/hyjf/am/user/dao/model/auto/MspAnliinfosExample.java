package com.hyjf.am.user.dao.model.auto;

import java.util.ArrayList;
import java.util.List;

public class MspAnliinfosExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public MspAnliinfosExample() {
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

        public Criteria andAnliIdIsNull() {
            addCriterion("anli_id is null");
            return (Criteria) this;
        }

        public Criteria andAnliIdIsNotNull() {
            addCriterion("anli_id is not null");
            return (Criteria) this;
        }

        public Criteria andAnliIdEqualTo(String value) {
            addCriterion("anli_id =", value, "anliId");
            return (Criteria) this;
        }

        public Criteria andAnliIdNotEqualTo(String value) {
            addCriterion("anli_id <>", value, "anliId");
            return (Criteria) this;
        }

        public Criteria andAnliIdGreaterThan(String value) {
            addCriterion("anli_id >", value, "anliId");
            return (Criteria) this;
        }

        public Criteria andAnliIdGreaterThanOrEqualTo(String value) {
            addCriterion("anli_id >=", value, "anliId");
            return (Criteria) this;
        }

        public Criteria andAnliIdLessThan(String value) {
            addCriterion("anli_id <", value, "anliId");
            return (Criteria) this;
        }

        public Criteria andAnliIdLessThanOrEqualTo(String value) {
            addCriterion("anli_id <=", value, "anliId");
            return (Criteria) this;
        }

        public Criteria andAnliIdLike(String value) {
            addCriterion("anli_id like", value, "anliId");
            return (Criteria) this;
        }

        public Criteria andAnliIdNotLike(String value) {
            addCriterion("anli_id not like", value, "anliId");
            return (Criteria) this;
        }

        public Criteria andAnliIdIn(List<String> values) {
            addCriterion("anli_id in", values, "anliId");
            return (Criteria) this;
        }

        public Criteria andAnliIdNotIn(List<String> values) {
            addCriterion("anli_id not in", values, "anliId");
            return (Criteria) this;
        }

        public Criteria andAnliIdBetween(String value1, String value2) {
            addCriterion("anli_id between", value1, value2, "anliId");
            return (Criteria) this;
        }

        public Criteria andAnliIdNotBetween(String value1, String value2) {
            addCriterion("anli_id not between", value1, value2, "anliId");
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

        public Criteria andPapernumIsNull() {
            addCriterion("paperNum is null");
            return (Criteria) this;
        }

        public Criteria andPapernumIsNotNull() {
            addCriterion("paperNum is not null");
            return (Criteria) this;
        }

        public Criteria andPapernumEqualTo(String value) {
            addCriterion("paperNum =", value, "papernum");
            return (Criteria) this;
        }

        public Criteria andPapernumNotEqualTo(String value) {
            addCriterion("paperNum <>", value, "papernum");
            return (Criteria) this;
        }

        public Criteria andPapernumGreaterThan(String value) {
            addCriterion("paperNum >", value, "papernum");
            return (Criteria) this;
        }

        public Criteria andPapernumGreaterThanOrEqualTo(String value) {
            addCriterion("paperNum >=", value, "papernum");
            return (Criteria) this;
        }

        public Criteria andPapernumLessThan(String value) {
            addCriterion("paperNum <", value, "papernum");
            return (Criteria) this;
        }

        public Criteria andPapernumLessThanOrEqualTo(String value) {
            addCriterion("paperNum <=", value, "papernum");
            return (Criteria) this;
        }

        public Criteria andPapernumLike(String value) {
            addCriterion("paperNum like", value, "papernum");
            return (Criteria) this;
        }

        public Criteria andPapernumNotLike(String value) {
            addCriterion("paperNum not like", value, "papernum");
            return (Criteria) this;
        }

        public Criteria andPapernumIn(List<String> values) {
            addCriterion("paperNum in", values, "papernum");
            return (Criteria) this;
        }

        public Criteria andPapernumNotIn(List<String> values) {
            addCriterion("paperNum not in", values, "papernum");
            return (Criteria) this;
        }

        public Criteria andPapernumBetween(String value1, String value2) {
            addCriterion("paperNum between", value1, value2, "papernum");
            return (Criteria) this;
        }

        public Criteria andPapernumNotBetween(String value1, String value2) {
            addCriterion("paperNum not between", value1, value2, "papernum");
            return (Criteria) this;
        }

        public Criteria andDangshirentypeIsNull() {
            addCriterion("dangshirenType is null");
            return (Criteria) this;
        }

        public Criteria andDangshirentypeIsNotNull() {
            addCriterion("dangshirenType is not null");
            return (Criteria) this;
        }

        public Criteria andDangshirentypeEqualTo(String value) {
            addCriterion("dangshirenType =", value, "dangshirentype");
            return (Criteria) this;
        }

        public Criteria andDangshirentypeNotEqualTo(String value) {
            addCriterion("dangshirenType <>", value, "dangshirentype");
            return (Criteria) this;
        }

        public Criteria andDangshirentypeGreaterThan(String value) {
            addCriterion("dangshirenType >", value, "dangshirentype");
            return (Criteria) this;
        }

        public Criteria andDangshirentypeGreaterThanOrEqualTo(String value) {
            addCriterion("dangshirenType >=", value, "dangshirentype");
            return (Criteria) this;
        }

        public Criteria andDangshirentypeLessThan(String value) {
            addCriterion("dangshirenType <", value, "dangshirentype");
            return (Criteria) this;
        }

        public Criteria andDangshirentypeLessThanOrEqualTo(String value) {
            addCriterion("dangshirenType <=", value, "dangshirentype");
            return (Criteria) this;
        }

        public Criteria andDangshirentypeLike(String value) {
            addCriterion("dangshirenType like", value, "dangshirentype");
            return (Criteria) this;
        }

        public Criteria andDangshirentypeNotLike(String value) {
            addCriterion("dangshirenType not like", value, "dangshirentype");
            return (Criteria) this;
        }

        public Criteria andDangshirentypeIn(List<String> values) {
            addCriterion("dangshirenType in", values, "dangshirentype");
            return (Criteria) this;
        }

        public Criteria andDangshirentypeNotIn(List<String> values) {
            addCriterion("dangshirenType not in", values, "dangshirentype");
            return (Criteria) this;
        }

        public Criteria andDangshirentypeBetween(String value1, String value2) {
            addCriterion("dangshirenType between", value1, value2, "dangshirentype");
            return (Criteria) this;
        }

        public Criteria andDangshirentypeNotBetween(String value1, String value2) {
            addCriterion("dangshirenType not between", value1, value2, "dangshirentype");
            return (Criteria) this;
        }

        public Criteria andSexIsNull() {
            addCriterion("sex is null");
            return (Criteria) this;
        }

        public Criteria andSexIsNotNull() {
            addCriterion("sex is not null");
            return (Criteria) this;
        }

        public Criteria andSexEqualTo(String value) {
            addCriterion("sex =", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotEqualTo(String value) {
            addCriterion("sex <>", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexGreaterThan(String value) {
            addCriterion("sex >", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexGreaterThanOrEqualTo(String value) {
            addCriterion("sex >=", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexLessThan(String value) {
            addCriterion("sex <", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexLessThanOrEqualTo(String value) {
            addCriterion("sex <=", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexLike(String value) {
            addCriterion("sex like", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotLike(String value) {
            addCriterion("sex not like", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexIn(List<String> values) {
            addCriterion("sex in", values, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotIn(List<String> values) {
            addCriterion("sex not in", values, "sex");
            return (Criteria) this;
        }

        public Criteria andSexBetween(String value1, String value2) {
            addCriterion("sex between", value1, value2, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotBetween(String value1, String value2) {
            addCriterion("sex not between", value1, value2, "sex");
            return (Criteria) this;
        }

        public Criteria andBirthdayIsNull() {
            addCriterion("birthday is null");
            return (Criteria) this;
        }

        public Criteria andBirthdayIsNotNull() {
            addCriterion("birthday is not null");
            return (Criteria) this;
        }

        public Criteria andBirthdayEqualTo(String value) {
            addCriterion("birthday =", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayNotEqualTo(String value) {
            addCriterion("birthday <>", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayGreaterThan(String value) {
            addCriterion("birthday >", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayGreaterThanOrEqualTo(String value) {
            addCriterion("birthday >=", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayLessThan(String value) {
            addCriterion("birthday <", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayLessThanOrEqualTo(String value) {
            addCriterion("birthday <=", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayLike(String value) {
            addCriterion("birthday like", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayNotLike(String value) {
            addCriterion("birthday not like", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayIn(List<String> values) {
            addCriterion("birthday in", values, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayNotIn(List<String> values) {
            addCriterion("birthday not in", values, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayBetween(String value1, String value2) {
            addCriterion("birthday between", value1, value2, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayNotBetween(String value1, String value2) {
            addCriterion("birthday not between", value1, value2, "birthday");
            return (Criteria) this;
        }

        public Criteria andAnjiantitleIsNull() {
            addCriterion("anjianTitle is null");
            return (Criteria) this;
        }

        public Criteria andAnjiantitleIsNotNull() {
            addCriterion("anjianTitle is not null");
            return (Criteria) this;
        }

        public Criteria andAnjiantitleEqualTo(String value) {
            addCriterion("anjianTitle =", value, "anjiantitle");
            return (Criteria) this;
        }

        public Criteria andAnjiantitleNotEqualTo(String value) {
            addCriterion("anjianTitle <>", value, "anjiantitle");
            return (Criteria) this;
        }

        public Criteria andAnjiantitleGreaterThan(String value) {
            addCriterion("anjianTitle >", value, "anjiantitle");
            return (Criteria) this;
        }

        public Criteria andAnjiantitleGreaterThanOrEqualTo(String value) {
            addCriterion("anjianTitle >=", value, "anjiantitle");
            return (Criteria) this;
        }

        public Criteria andAnjiantitleLessThan(String value) {
            addCriterion("anjianTitle <", value, "anjiantitle");
            return (Criteria) this;
        }

        public Criteria andAnjiantitleLessThanOrEqualTo(String value) {
            addCriterion("anjianTitle <=", value, "anjiantitle");
            return (Criteria) this;
        }

        public Criteria andAnjiantitleLike(String value) {
            addCriterion("anjianTitle like", value, "anjiantitle");
            return (Criteria) this;
        }

        public Criteria andAnjiantitleNotLike(String value) {
            addCriterion("anjianTitle not like", value, "anjiantitle");
            return (Criteria) this;
        }

        public Criteria andAnjiantitleIn(List<String> values) {
            addCriterion("anjianTitle in", values, "anjiantitle");
            return (Criteria) this;
        }

        public Criteria andAnjiantitleNotIn(List<String> values) {
            addCriterion("anjianTitle not in", values, "anjiantitle");
            return (Criteria) this;
        }

        public Criteria andAnjiantitleBetween(String value1, String value2) {
            addCriterion("anjianTitle between", value1, value2, "anjiantitle");
            return (Criteria) this;
        }

        public Criteria andAnjiantitleNotBetween(String value1, String value2) {
            addCriterion("anjianTitle not between", value1, value2, "anjiantitle");
            return (Criteria) this;
        }

        public Criteria andEnddateIsNull() {
            addCriterion("endDate is null");
            return (Criteria) this;
        }

        public Criteria andEnddateIsNotNull() {
            addCriterion("endDate is not null");
            return (Criteria) this;
        }

        public Criteria andEnddateEqualTo(String value) {
            addCriterion("endDate =", value, "enddate");
            return (Criteria) this;
        }

        public Criteria andEnddateNotEqualTo(String value) {
            addCriterion("endDate <>", value, "enddate");
            return (Criteria) this;
        }

        public Criteria andEnddateGreaterThan(String value) {
            addCriterion("endDate >", value, "enddate");
            return (Criteria) this;
        }

        public Criteria andEnddateGreaterThanOrEqualTo(String value) {
            addCriterion("endDate >=", value, "enddate");
            return (Criteria) this;
        }

        public Criteria andEnddateLessThan(String value) {
            addCriterion("endDate <", value, "enddate");
            return (Criteria) this;
        }

        public Criteria andEnddateLessThanOrEqualTo(String value) {
            addCriterion("endDate <=", value, "enddate");
            return (Criteria) this;
        }

        public Criteria andEnddateLike(String value) {
            addCriterion("endDate like", value, "enddate");
            return (Criteria) this;
        }

        public Criteria andEnddateNotLike(String value) {
            addCriterion("endDate not like", value, "enddate");
            return (Criteria) this;
        }

        public Criteria andEnddateIn(List<String> values) {
            addCriterion("endDate in", values, "enddate");
            return (Criteria) this;
        }

        public Criteria andEnddateNotIn(List<String> values) {
            addCriterion("endDate not in", values, "enddate");
            return (Criteria) this;
        }

        public Criteria andEnddateBetween(String value1, String value2) {
            addCriterion("endDate between", value1, value2, "enddate");
            return (Criteria) this;
        }

        public Criteria andEnddateNotBetween(String value1, String value2) {
            addCriterion("endDate not between", value1, value2, "enddate");
            return (Criteria) this;
        }

        public Criteria andAnjiantypeIsNull() {
            addCriterion("anjianType is null");
            return (Criteria) this;
        }

        public Criteria andAnjiantypeIsNotNull() {
            addCriterion("anjianType is not null");
            return (Criteria) this;
        }

        public Criteria andAnjiantypeEqualTo(String value) {
            addCriterion("anjianType =", value, "anjiantype");
            return (Criteria) this;
        }

        public Criteria andAnjiantypeNotEqualTo(String value) {
            addCriterion("anjianType <>", value, "anjiantype");
            return (Criteria) this;
        }

        public Criteria andAnjiantypeGreaterThan(String value) {
            addCriterion("anjianType >", value, "anjiantype");
            return (Criteria) this;
        }

        public Criteria andAnjiantypeGreaterThanOrEqualTo(String value) {
            addCriterion("anjianType >=", value, "anjiantype");
            return (Criteria) this;
        }

        public Criteria andAnjiantypeLessThan(String value) {
            addCriterion("anjianType <", value, "anjiantype");
            return (Criteria) this;
        }

        public Criteria andAnjiantypeLessThanOrEqualTo(String value) {
            addCriterion("anjianType <=", value, "anjiantype");
            return (Criteria) this;
        }

        public Criteria andAnjiantypeLike(String value) {
            addCriterion("anjianType like", value, "anjiantype");
            return (Criteria) this;
        }

        public Criteria andAnjiantypeNotLike(String value) {
            addCriterion("anjianType not like", value, "anjiantype");
            return (Criteria) this;
        }

        public Criteria andAnjiantypeIn(List<String> values) {
            addCriterion("anjianType in", values, "anjiantype");
            return (Criteria) this;
        }

        public Criteria andAnjiantypeNotIn(List<String> values) {
            addCriterion("anjianType not in", values, "anjiantype");
            return (Criteria) this;
        }

        public Criteria andAnjiantypeBetween(String value1, String value2) {
            addCriterion("anjianType between", value1, value2, "anjiantype");
            return (Criteria) this;
        }

        public Criteria andAnjiantypeNotBetween(String value1, String value2) {
            addCriterion("anjianType not between", value1, value2, "anjiantype");
            return (Criteria) this;
        }

        public Criteria andAnjiannumIsNull() {
            addCriterion("anjianNum is null");
            return (Criteria) this;
        }

        public Criteria andAnjiannumIsNotNull() {
            addCriterion("anjianNum is not null");
            return (Criteria) this;
        }

        public Criteria andAnjiannumEqualTo(String value) {
            addCriterion("anjianNum =", value, "anjiannum");
            return (Criteria) this;
        }

        public Criteria andAnjiannumNotEqualTo(String value) {
            addCriterion("anjianNum <>", value, "anjiannum");
            return (Criteria) this;
        }

        public Criteria andAnjiannumGreaterThan(String value) {
            addCriterion("anjianNum >", value, "anjiannum");
            return (Criteria) this;
        }

        public Criteria andAnjiannumGreaterThanOrEqualTo(String value) {
            addCriterion("anjianNum >=", value, "anjiannum");
            return (Criteria) this;
        }

        public Criteria andAnjiannumLessThan(String value) {
            addCriterion("anjianNum <", value, "anjiannum");
            return (Criteria) this;
        }

        public Criteria andAnjiannumLessThanOrEqualTo(String value) {
            addCriterion("anjianNum <=", value, "anjiannum");
            return (Criteria) this;
        }

        public Criteria andAnjiannumLike(String value) {
            addCriterion("anjianNum like", value, "anjiannum");
            return (Criteria) this;
        }

        public Criteria andAnjiannumNotLike(String value) {
            addCriterion("anjianNum not like", value, "anjiannum");
            return (Criteria) this;
        }

        public Criteria andAnjiannumIn(List<String> values) {
            addCriterion("anjianNum in", values, "anjiannum");
            return (Criteria) this;
        }

        public Criteria andAnjiannumNotIn(List<String> values) {
            addCriterion("anjianNum not in", values, "anjiannum");
            return (Criteria) this;
        }

        public Criteria andAnjiannumBetween(String value1, String value2) {
            addCriterion("anjianNum between", value1, value2, "anjiannum");
            return (Criteria) this;
        }

        public Criteria andAnjiannumNotBetween(String value1, String value2) {
            addCriterion("anjianNum not between", value1, value2, "anjiannum");
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