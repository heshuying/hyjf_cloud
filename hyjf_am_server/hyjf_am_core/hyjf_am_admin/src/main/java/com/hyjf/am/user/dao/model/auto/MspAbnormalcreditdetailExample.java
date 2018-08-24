package com.hyjf.am.user.dao.model.auto;

import java.util.ArrayList;
import java.util.List;

public class MspAbnormalcreditdetailExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public MspAbnormalcreditdetailExample() {
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

        public Criteria andAbcdIdIsNull() {
            addCriterion("abcd_id is null");
            return (Criteria) this;
        }

        public Criteria andAbcdIdIsNotNull() {
            addCriterion("abcd_id is not null");
            return (Criteria) this;
        }

        public Criteria andAbcdIdEqualTo(String value) {
            addCriterion("abcd_id =", value, "abcdId");
            return (Criteria) this;
        }

        public Criteria andAbcdIdNotEqualTo(String value) {
            addCriterion("abcd_id <>", value, "abcdId");
            return (Criteria) this;
        }

        public Criteria andAbcdIdGreaterThan(String value) {
            addCriterion("abcd_id >", value, "abcdId");
            return (Criteria) this;
        }

        public Criteria andAbcdIdGreaterThanOrEqualTo(String value) {
            addCriterion("abcd_id >=", value, "abcdId");
            return (Criteria) this;
        }

        public Criteria andAbcdIdLessThan(String value) {
            addCriterion("abcd_id <", value, "abcdId");
            return (Criteria) this;
        }

        public Criteria andAbcdIdLessThanOrEqualTo(String value) {
            addCriterion("abcd_id <=", value, "abcdId");
            return (Criteria) this;
        }

        public Criteria andAbcdIdLike(String value) {
            addCriterion("abcd_id like", value, "abcdId");
            return (Criteria) this;
        }

        public Criteria andAbcdIdNotLike(String value) {
            addCriterion("abcd_id not like", value, "abcdId");
            return (Criteria) this;
        }

        public Criteria andAbcdIdIn(List<String> values) {
            addCriterion("abcd_id in", values, "abcdId");
            return (Criteria) this;
        }

        public Criteria andAbcdIdNotIn(List<String> values) {
            addCriterion("abcd_id not in", values, "abcdId");
            return (Criteria) this;
        }

        public Criteria andAbcdIdBetween(String value1, String value2) {
            addCriterion("abcd_id between", value1, value2, "abcdId");
            return (Criteria) this;
        }

        public Criteria andAbcdIdNotBetween(String value1, String value2) {
            addCriterion("abcd_id not between", value1, value2, "abcdId");
            return (Criteria) this;
        }

        public Criteria andCheckoverduedateIsNull() {
            addCriterion("checkOverdueDate is null");
            return (Criteria) this;
        }

        public Criteria andCheckoverduedateIsNotNull() {
            addCriterion("checkOverdueDate is not null");
            return (Criteria) this;
        }

        public Criteria andCheckoverduedateEqualTo(String value) {
            addCriterion("checkOverdueDate =", value, "checkoverduedate");
            return (Criteria) this;
        }

        public Criteria andCheckoverduedateNotEqualTo(String value) {
            addCriterion("checkOverdueDate <>", value, "checkoverduedate");
            return (Criteria) this;
        }

        public Criteria andCheckoverduedateGreaterThan(String value) {
            addCriterion("checkOverdueDate >", value, "checkoverduedate");
            return (Criteria) this;
        }

        public Criteria andCheckoverduedateGreaterThanOrEqualTo(String value) {
            addCriterion("checkOverdueDate >=", value, "checkoverduedate");
            return (Criteria) this;
        }

        public Criteria andCheckoverduedateLessThan(String value) {
            addCriterion("checkOverdueDate <", value, "checkoverduedate");
            return (Criteria) this;
        }

        public Criteria andCheckoverduedateLessThanOrEqualTo(String value) {
            addCriterion("checkOverdueDate <=", value, "checkoverduedate");
            return (Criteria) this;
        }

        public Criteria andCheckoverduedateLike(String value) {
            addCriterion("checkOverdueDate like", value, "checkoverduedate");
            return (Criteria) this;
        }

        public Criteria andCheckoverduedateNotLike(String value) {
            addCriterion("checkOverdueDate not like", value, "checkoverduedate");
            return (Criteria) this;
        }

        public Criteria andCheckoverduedateIn(List<String> values) {
            addCriterion("checkOverdueDate in", values, "checkoverduedate");
            return (Criteria) this;
        }

        public Criteria andCheckoverduedateNotIn(List<String> values) {
            addCriterion("checkOverdueDate not in", values, "checkoverduedate");
            return (Criteria) this;
        }

        public Criteria andCheckoverduedateBetween(String value1, String value2) {
            addCriterion("checkOverdueDate between", value1, value2, "checkoverduedate");
            return (Criteria) this;
        }

        public Criteria andCheckoverduedateNotBetween(String value1, String value2) {
            addCriterion("checkOverdueDate not between", value1, value2, "checkoverduedate");
            return (Criteria) this;
        }

        public Criteria andOverduedaysIsNull() {
            addCriterion("overdueDays is null");
            return (Criteria) this;
        }

        public Criteria andOverduedaysIsNotNull() {
            addCriterion("overdueDays is not null");
            return (Criteria) this;
        }

        public Criteria andOverduedaysEqualTo(String value) {
            addCriterion("overdueDays =", value, "overduedays");
            return (Criteria) this;
        }

        public Criteria andOverduedaysNotEqualTo(String value) {
            addCriterion("overdueDays <>", value, "overduedays");
            return (Criteria) this;
        }

        public Criteria andOverduedaysGreaterThan(String value) {
            addCriterion("overdueDays >", value, "overduedays");
            return (Criteria) this;
        }

        public Criteria andOverduedaysGreaterThanOrEqualTo(String value) {
            addCriterion("overdueDays >=", value, "overduedays");
            return (Criteria) this;
        }

        public Criteria andOverduedaysLessThan(String value) {
            addCriterion("overdueDays <", value, "overduedays");
            return (Criteria) this;
        }

        public Criteria andOverduedaysLessThanOrEqualTo(String value) {
            addCriterion("overdueDays <=", value, "overduedays");
            return (Criteria) this;
        }

        public Criteria andOverduedaysLike(String value) {
            addCriterion("overdueDays like", value, "overduedays");
            return (Criteria) this;
        }

        public Criteria andOverduedaysNotLike(String value) {
            addCriterion("overdueDays not like", value, "overduedays");
            return (Criteria) this;
        }

        public Criteria andOverduedaysIn(List<String> values) {
            addCriterion("overdueDays in", values, "overduedays");
            return (Criteria) this;
        }

        public Criteria andOverduedaysNotIn(List<String> values) {
            addCriterion("overdueDays not in", values, "overduedays");
            return (Criteria) this;
        }

        public Criteria andOverduedaysBetween(String value1, String value2) {
            addCriterion("overdueDays between", value1, value2, "overduedays");
            return (Criteria) this;
        }

        public Criteria andOverduedaysNotBetween(String value1, String value2) {
            addCriterion("overdueDays not between", value1, value2, "overduedays");
            return (Criteria) this;
        }

        public Criteria andOverduereasonIsNull() {
            addCriterion("overdueReason is null");
            return (Criteria) this;
        }

        public Criteria andOverduereasonIsNotNull() {
            addCriterion("overdueReason is not null");
            return (Criteria) this;
        }

        public Criteria andOverduereasonEqualTo(String value) {
            addCriterion("overdueReason =", value, "overduereason");
            return (Criteria) this;
        }

        public Criteria andOverduereasonNotEqualTo(String value) {
            addCriterion("overdueReason <>", value, "overduereason");
            return (Criteria) this;
        }

        public Criteria andOverduereasonGreaterThan(String value) {
            addCriterion("overdueReason >", value, "overduereason");
            return (Criteria) this;
        }

        public Criteria andOverduereasonGreaterThanOrEqualTo(String value) {
            addCriterion("overdueReason >=", value, "overduereason");
            return (Criteria) this;
        }

        public Criteria andOverduereasonLessThan(String value) {
            addCriterion("overdueReason <", value, "overduereason");
            return (Criteria) this;
        }

        public Criteria andOverduereasonLessThanOrEqualTo(String value) {
            addCriterion("overdueReason <=", value, "overduereason");
            return (Criteria) this;
        }

        public Criteria andOverduereasonLike(String value) {
            addCriterion("overdueReason like", value, "overduereason");
            return (Criteria) this;
        }

        public Criteria andOverduereasonNotLike(String value) {
            addCriterion("overdueReason not like", value, "overduereason");
            return (Criteria) this;
        }

        public Criteria andOverduereasonIn(List<String> values) {
            addCriterion("overdueReason in", values, "overduereason");
            return (Criteria) this;
        }

        public Criteria andOverduereasonNotIn(List<String> values) {
            addCriterion("overdueReason not in", values, "overduereason");
            return (Criteria) this;
        }

        public Criteria andOverduereasonBetween(String value1, String value2) {
            addCriterion("overdueReason between", value1, value2, "overduereason");
            return (Criteria) this;
        }

        public Criteria andOverduereasonNotBetween(String value1, String value2) {
            addCriterion("overdueReason not between", value1, value2, "overduereason");
            return (Criteria) this;
        }

        public Criteria andOverduestateIsNull() {
            addCriterion("overdueState is null");
            return (Criteria) this;
        }

        public Criteria andOverduestateIsNotNull() {
            addCriterion("overdueState is not null");
            return (Criteria) this;
        }

        public Criteria andOverduestateEqualTo(String value) {
            addCriterion("overdueState =", value, "overduestate");
            return (Criteria) this;
        }

        public Criteria andOverduestateNotEqualTo(String value) {
            addCriterion("overdueState <>", value, "overduestate");
            return (Criteria) this;
        }

        public Criteria andOverduestateGreaterThan(String value) {
            addCriterion("overdueState >", value, "overduestate");
            return (Criteria) this;
        }

        public Criteria andOverduestateGreaterThanOrEqualTo(String value) {
            addCriterion("overdueState >=", value, "overduestate");
            return (Criteria) this;
        }

        public Criteria andOverduestateLessThan(String value) {
            addCriterion("overdueState <", value, "overduestate");
            return (Criteria) this;
        }

        public Criteria andOverduestateLessThanOrEqualTo(String value) {
            addCriterion("overdueState <=", value, "overduestate");
            return (Criteria) this;
        }

        public Criteria andOverduestateLike(String value) {
            addCriterion("overdueState like", value, "overduestate");
            return (Criteria) this;
        }

        public Criteria andOverduestateNotLike(String value) {
            addCriterion("overdueState not like", value, "overduestate");
            return (Criteria) this;
        }

        public Criteria andOverduestateIn(List<String> values) {
            addCriterion("overdueState in", values, "overduestate");
            return (Criteria) this;
        }

        public Criteria andOverduestateNotIn(List<String> values) {
            addCriterion("overdueState not in", values, "overduestate");
            return (Criteria) this;
        }

        public Criteria andOverduestateBetween(String value1, String value2) {
            addCriterion("overdueState between", value1, value2, "overduestate");
            return (Criteria) this;
        }

        public Criteria andOverduestateNotBetween(String value1, String value2) {
            addCriterion("overdueState not between", value1, value2, "overduestate");
            return (Criteria) this;
        }

        public Criteria andOpertimeIsNull() {
            addCriterion("operTime is null");
            return (Criteria) this;
        }

        public Criteria andOpertimeIsNotNull() {
            addCriterion("operTime is not null");
            return (Criteria) this;
        }

        public Criteria andOpertimeEqualTo(String value) {
            addCriterion("operTime =", value, "opertime");
            return (Criteria) this;
        }

        public Criteria andOpertimeNotEqualTo(String value) {
            addCriterion("operTime <>", value, "opertime");
            return (Criteria) this;
        }

        public Criteria andOpertimeGreaterThan(String value) {
            addCriterion("operTime >", value, "opertime");
            return (Criteria) this;
        }

        public Criteria andOpertimeGreaterThanOrEqualTo(String value) {
            addCriterion("operTime >=", value, "opertime");
            return (Criteria) this;
        }

        public Criteria andOpertimeLessThan(String value) {
            addCriterion("operTime <", value, "opertime");
            return (Criteria) this;
        }

        public Criteria andOpertimeLessThanOrEqualTo(String value) {
            addCriterion("operTime <=", value, "opertime");
            return (Criteria) this;
        }

        public Criteria andOpertimeLike(String value) {
            addCriterion("operTime like", value, "opertime");
            return (Criteria) this;
        }

        public Criteria andOpertimeNotLike(String value) {
            addCriterion("operTime not like", value, "opertime");
            return (Criteria) this;
        }

        public Criteria andOpertimeIn(List<String> values) {
            addCriterion("operTime in", values, "opertime");
            return (Criteria) this;
        }

        public Criteria andOpertimeNotIn(List<String> values) {
            addCriterion("operTime not in", values, "opertime");
            return (Criteria) this;
        }

        public Criteria andOpertimeBetween(String value1, String value2) {
            addCriterion("operTime between", value1, value2, "opertime");
            return (Criteria) this;
        }

        public Criteria andOpertimeNotBetween(String value1, String value2) {
            addCriterion("operTime not between", value1, value2, "opertime");
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