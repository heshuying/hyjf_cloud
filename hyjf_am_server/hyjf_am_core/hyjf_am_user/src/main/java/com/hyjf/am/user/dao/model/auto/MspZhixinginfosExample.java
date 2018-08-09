package com.hyjf.am.user.dao.model.auto;

import java.util.ArrayList;
import java.util.List;

public class MspZhixinginfosExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public MspZhixinginfosExample() {
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

        public Criteria andZguxubgIdIsNull() {
            addCriterion("zguxubg_id is null");
            return (Criteria) this;
        }

        public Criteria andZguxubgIdIsNotNull() {
            addCriterion("zguxubg_id is not null");
            return (Criteria) this;
        }

        public Criteria andZguxubgIdEqualTo(String value) {
            addCriterion("zguxubg_id =", value, "zguxubgId");
            return (Criteria) this;
        }

        public Criteria andZguxubgIdNotEqualTo(String value) {
            addCriterion("zguxubg_id <>", value, "zguxubgId");
            return (Criteria) this;
        }

        public Criteria andZguxubgIdGreaterThan(String value) {
            addCriterion("zguxubg_id >", value, "zguxubgId");
            return (Criteria) this;
        }

        public Criteria andZguxubgIdGreaterThanOrEqualTo(String value) {
            addCriterion("zguxubg_id >=", value, "zguxubgId");
            return (Criteria) this;
        }

        public Criteria andZguxubgIdLessThan(String value) {
            addCriterion("zguxubg_id <", value, "zguxubgId");
            return (Criteria) this;
        }

        public Criteria andZguxubgIdLessThanOrEqualTo(String value) {
            addCriterion("zguxubg_id <=", value, "zguxubgId");
            return (Criteria) this;
        }

        public Criteria andZguxubgIdLike(String value) {
            addCriterion("zguxubg_id like", value, "zguxubgId");
            return (Criteria) this;
        }

        public Criteria andZguxubgIdNotLike(String value) {
            addCriterion("zguxubg_id not like", value, "zguxubgId");
            return (Criteria) this;
        }

        public Criteria andZguxubgIdIn(List<String> values) {
            addCriterion("zguxubg_id in", values, "zguxubgId");
            return (Criteria) this;
        }

        public Criteria andZguxubgIdNotIn(List<String> values) {
            addCriterion("zguxubg_id not in", values, "zguxubgId");
            return (Criteria) this;
        }

        public Criteria andZguxubgIdBetween(String value1, String value2) {
            addCriterion("zguxubg_id between", value1, value2, "zguxubgId");
            return (Criteria) this;
        }

        public Criteria andZguxubgIdNotBetween(String value1, String value2) {
            addCriterion("zguxubg_id not between", value1, value2, "zguxubgId");
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

        public Criteria andZhixingcourtIsNull() {
            addCriterion("zhixingCourt is null");
            return (Criteria) this;
        }

        public Criteria andZhixingcourtIsNotNull() {
            addCriterion("zhixingCourt is not null");
            return (Criteria) this;
        }

        public Criteria andZhixingcourtEqualTo(String value) {
            addCriterion("zhixingCourt =", value, "zhixingcourt");
            return (Criteria) this;
        }

        public Criteria andZhixingcourtNotEqualTo(String value) {
            addCriterion("zhixingCourt <>", value, "zhixingcourt");
            return (Criteria) this;
        }

        public Criteria andZhixingcourtGreaterThan(String value) {
            addCriterion("zhixingCourt >", value, "zhixingcourt");
            return (Criteria) this;
        }

        public Criteria andZhixingcourtGreaterThanOrEqualTo(String value) {
            addCriterion("zhixingCourt >=", value, "zhixingcourt");
            return (Criteria) this;
        }

        public Criteria andZhixingcourtLessThan(String value) {
            addCriterion("zhixingCourt <", value, "zhixingcourt");
            return (Criteria) this;
        }

        public Criteria andZhixingcourtLessThanOrEqualTo(String value) {
            addCriterion("zhixingCourt <=", value, "zhixingcourt");
            return (Criteria) this;
        }

        public Criteria andZhixingcourtLike(String value) {
            addCriterion("zhixingCourt like", value, "zhixingcourt");
            return (Criteria) this;
        }

        public Criteria andZhixingcourtNotLike(String value) {
            addCriterion("zhixingCourt not like", value, "zhixingcourt");
            return (Criteria) this;
        }

        public Criteria andZhixingcourtIn(List<String> values) {
            addCriterion("zhixingCourt in", values, "zhixingcourt");
            return (Criteria) this;
        }

        public Criteria andZhixingcourtNotIn(List<String> values) {
            addCriterion("zhixingCourt not in", values, "zhixingcourt");
            return (Criteria) this;
        }

        public Criteria andZhixingcourtBetween(String value1, String value2) {
            addCriterion("zhixingCourt between", value1, value2, "zhixingcourt");
            return (Criteria) this;
        }

        public Criteria andZhixingcourtNotBetween(String value1, String value2) {
            addCriterion("zhixingCourt not between", value1, value2, "zhixingcourt");
            return (Criteria) this;
        }

        public Criteria andAnlinumIsNull() {
            addCriterion("anliNum is null");
            return (Criteria) this;
        }

        public Criteria andAnlinumIsNotNull() {
            addCriterion("anliNum is not null");
            return (Criteria) this;
        }

        public Criteria andAnlinumEqualTo(String value) {
            addCriterion("anliNum =", value, "anlinum");
            return (Criteria) this;
        }

        public Criteria andAnlinumNotEqualTo(String value) {
            addCriterion("anliNum <>", value, "anlinum");
            return (Criteria) this;
        }

        public Criteria andAnlinumGreaterThan(String value) {
            addCriterion("anliNum >", value, "anlinum");
            return (Criteria) this;
        }

        public Criteria andAnlinumGreaterThanOrEqualTo(String value) {
            addCriterion("anliNum >=", value, "anlinum");
            return (Criteria) this;
        }

        public Criteria andAnlinumLessThan(String value) {
            addCriterion("anliNum <", value, "anlinum");
            return (Criteria) this;
        }

        public Criteria andAnlinumLessThanOrEqualTo(String value) {
            addCriterion("anliNum <=", value, "anlinum");
            return (Criteria) this;
        }

        public Criteria andAnlinumLike(String value) {
            addCriterion("anliNum like", value, "anlinum");
            return (Criteria) this;
        }

        public Criteria andAnlinumNotLike(String value) {
            addCriterion("anliNum not like", value, "anlinum");
            return (Criteria) this;
        }

        public Criteria andAnlinumIn(List<String> values) {
            addCriterion("anliNum in", values, "anlinum");
            return (Criteria) this;
        }

        public Criteria andAnlinumNotIn(List<String> values) {
            addCriterion("anliNum not in", values, "anlinum");
            return (Criteria) this;
        }

        public Criteria andAnlinumBetween(String value1, String value2) {
            addCriterion("anliNum between", value1, value2, "anlinum");
            return (Criteria) this;
        }

        public Criteria andAnlinumNotBetween(String value1, String value2) {
            addCriterion("anliNum not between", value1, value2, "anlinum");
            return (Criteria) this;
        }

        public Criteria andAnjianstateIsNull() {
            addCriterion("anjianState is null");
            return (Criteria) this;
        }

        public Criteria andAnjianstateIsNotNull() {
            addCriterion("anjianState is not null");
            return (Criteria) this;
        }

        public Criteria andAnjianstateEqualTo(String value) {
            addCriterion("anjianState =", value, "anjianstate");
            return (Criteria) this;
        }

        public Criteria andAnjianstateNotEqualTo(String value) {
            addCriterion("anjianState <>", value, "anjianstate");
            return (Criteria) this;
        }

        public Criteria andAnjianstateGreaterThan(String value) {
            addCriterion("anjianState >", value, "anjianstate");
            return (Criteria) this;
        }

        public Criteria andAnjianstateGreaterThanOrEqualTo(String value) {
            addCriterion("anjianState >=", value, "anjianstate");
            return (Criteria) this;
        }

        public Criteria andAnjianstateLessThan(String value) {
            addCriterion("anjianState <", value, "anjianstate");
            return (Criteria) this;
        }

        public Criteria andAnjianstateLessThanOrEqualTo(String value) {
            addCriterion("anjianState <=", value, "anjianstate");
            return (Criteria) this;
        }

        public Criteria andAnjianstateLike(String value) {
            addCriterion("anjianState like", value, "anjianstate");
            return (Criteria) this;
        }

        public Criteria andAnjianstateNotLike(String value) {
            addCriterion("anjianState not like", value, "anjianstate");
            return (Criteria) this;
        }

        public Criteria andAnjianstateIn(List<String> values) {
            addCriterion("anjianState in", values, "anjianstate");
            return (Criteria) this;
        }

        public Criteria andAnjianstateNotIn(List<String> values) {
            addCriterion("anjianState not in", values, "anjianstate");
            return (Criteria) this;
        }

        public Criteria andAnjianstateBetween(String value1, String value2) {
            addCriterion("anjianState between", value1, value2, "anjianstate");
            return (Criteria) this;
        }

        public Criteria andAnjianstateNotBetween(String value1, String value2) {
            addCriterion("anjianState not between", value1, value2, "anjianstate");
            return (Criteria) this;
        }

        public Criteria andZhixingtagetIsNull() {
            addCriterion("zhixingTaget is null");
            return (Criteria) this;
        }

        public Criteria andZhixingtagetIsNotNull() {
            addCriterion("zhixingTaget is not null");
            return (Criteria) this;
        }

        public Criteria andZhixingtagetEqualTo(String value) {
            addCriterion("zhixingTaget =", value, "zhixingtaget");
            return (Criteria) this;
        }

        public Criteria andZhixingtagetNotEqualTo(String value) {
            addCriterion("zhixingTaget <>", value, "zhixingtaget");
            return (Criteria) this;
        }

        public Criteria andZhixingtagetGreaterThan(String value) {
            addCriterion("zhixingTaget >", value, "zhixingtaget");
            return (Criteria) this;
        }

        public Criteria andZhixingtagetGreaterThanOrEqualTo(String value) {
            addCriterion("zhixingTaget >=", value, "zhixingtaget");
            return (Criteria) this;
        }

        public Criteria andZhixingtagetLessThan(String value) {
            addCriterion("zhixingTaget <", value, "zhixingtaget");
            return (Criteria) this;
        }

        public Criteria andZhixingtagetLessThanOrEqualTo(String value) {
            addCriterion("zhixingTaget <=", value, "zhixingtaget");
            return (Criteria) this;
        }

        public Criteria andZhixingtagetLike(String value) {
            addCriterion("zhixingTaget like", value, "zhixingtaget");
            return (Criteria) this;
        }

        public Criteria andZhixingtagetNotLike(String value) {
            addCriterion("zhixingTaget not like", value, "zhixingtaget");
            return (Criteria) this;
        }

        public Criteria andZhixingtagetIn(List<String> values) {
            addCriterion("zhixingTaget in", values, "zhixingtaget");
            return (Criteria) this;
        }

        public Criteria andZhixingtagetNotIn(List<String> values) {
            addCriterion("zhixingTaget not in", values, "zhixingtaget");
            return (Criteria) this;
        }

        public Criteria andZhixingtagetBetween(String value1, String value2) {
            addCriterion("zhixingTaget between", value1, value2, "zhixingtaget");
            return (Criteria) this;
        }

        public Criteria andZhixingtagetNotBetween(String value1, String value2) {
            addCriterion("zhixingTaget not between", value1, value2, "zhixingtaget");
            return (Criteria) this;
        }

        public Criteria andLiantimeIsNull() {
            addCriterion("lianTime is null");
            return (Criteria) this;
        }

        public Criteria andLiantimeIsNotNull() {
            addCriterion("lianTime is not null");
            return (Criteria) this;
        }

        public Criteria andLiantimeEqualTo(String value) {
            addCriterion("lianTime =", value, "liantime");
            return (Criteria) this;
        }

        public Criteria andLiantimeNotEqualTo(String value) {
            addCriterion("lianTime <>", value, "liantime");
            return (Criteria) this;
        }

        public Criteria andLiantimeGreaterThan(String value) {
            addCriterion("lianTime >", value, "liantime");
            return (Criteria) this;
        }

        public Criteria andLiantimeGreaterThanOrEqualTo(String value) {
            addCriterion("lianTime >=", value, "liantime");
            return (Criteria) this;
        }

        public Criteria andLiantimeLessThan(String value) {
            addCriterion("lianTime <", value, "liantime");
            return (Criteria) this;
        }

        public Criteria andLiantimeLessThanOrEqualTo(String value) {
            addCriterion("lianTime <=", value, "liantime");
            return (Criteria) this;
        }

        public Criteria andLiantimeLike(String value) {
            addCriterion("lianTime like", value, "liantime");
            return (Criteria) this;
        }

        public Criteria andLiantimeNotLike(String value) {
            addCriterion("lianTime not like", value, "liantime");
            return (Criteria) this;
        }

        public Criteria andLiantimeIn(List<String> values) {
            addCriterion("lianTime in", values, "liantime");
            return (Criteria) this;
        }

        public Criteria andLiantimeNotIn(List<String> values) {
            addCriterion("lianTime not in", values, "liantime");
            return (Criteria) this;
        }

        public Criteria andLiantimeBetween(String value1, String value2) {
            addCriterion("lianTime between", value1, value2, "liantime");
            return (Criteria) this;
        }

        public Criteria andLiantimeNotBetween(String value1, String value2) {
            addCriterion("lianTime not between", value1, value2, "liantime");
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