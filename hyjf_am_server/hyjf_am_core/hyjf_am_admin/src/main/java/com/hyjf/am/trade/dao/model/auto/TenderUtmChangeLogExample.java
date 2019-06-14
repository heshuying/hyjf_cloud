package com.hyjf.am.trade.dao.model.auto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TenderUtmChangeLogExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public TenderUtmChangeLogExample() {
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

        public Criteria andNidIsNull() {
            addCriterion("nid is null");
            return (Criteria) this;
        }

        public Criteria andNidIsNotNull() {
            addCriterion("nid is not null");
            return (Criteria) this;
        }

        public Criteria andNidEqualTo(String value) {
            addCriterion("nid =", value, "nid");
            return (Criteria) this;
        }

        public Criteria andNidNotEqualTo(String value) {
            addCriterion("nid <>", value, "nid");
            return (Criteria) this;
        }

        public Criteria andNidGreaterThan(String value) {
            addCriterion("nid >", value, "nid");
            return (Criteria) this;
        }

        public Criteria andNidGreaterThanOrEqualTo(String value) {
            addCriterion("nid >=", value, "nid");
            return (Criteria) this;
        }

        public Criteria andNidLessThan(String value) {
            addCriterion("nid <", value, "nid");
            return (Criteria) this;
        }

        public Criteria andNidLessThanOrEqualTo(String value) {
            addCriterion("nid <=", value, "nid");
            return (Criteria) this;
        }

        public Criteria andNidLike(String value) {
            addCriterion("nid like", value, "nid");
            return (Criteria) this;
        }

        public Criteria andNidNotLike(String value) {
            addCriterion("nid not like", value, "nid");
            return (Criteria) this;
        }

        public Criteria andNidIn(List<String> values) {
            addCriterion("nid in", values, "nid");
            return (Criteria) this;
        }

        public Criteria andNidNotIn(List<String> values) {
            addCriterion("nid not in", values, "nid");
            return (Criteria) this;
        }

        public Criteria andNidBetween(String value1, String value2) {
            addCriterion("nid between", value1, value2, "nid");
            return (Criteria) this;
        }

        public Criteria andNidNotBetween(String value1, String value2) {
            addCriterion("nid not between", value1, value2, "nid");
            return (Criteria) this;
        }

        public Criteria andTenderUtmIdIsNull() {
            addCriterion("tender_utm_id is null");
            return (Criteria) this;
        }

        public Criteria andTenderUtmIdIsNotNull() {
            addCriterion("tender_utm_id is not null");
            return (Criteria) this;
        }

        public Criteria andTenderUtmIdEqualTo(Integer value) {
            addCriterion("tender_utm_id =", value, "tenderUtmId");
            return (Criteria) this;
        }

        public Criteria andTenderUtmIdNotEqualTo(Integer value) {
            addCriterion("tender_utm_id <>", value, "tenderUtmId");
            return (Criteria) this;
        }

        public Criteria andTenderUtmIdGreaterThan(Integer value) {
            addCriterion("tender_utm_id >", value, "tenderUtmId");
            return (Criteria) this;
        }

        public Criteria andTenderUtmIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("tender_utm_id >=", value, "tenderUtmId");
            return (Criteria) this;
        }

        public Criteria andTenderUtmIdLessThan(Integer value) {
            addCriterion("tender_utm_id <", value, "tenderUtmId");
            return (Criteria) this;
        }

        public Criteria andTenderUtmIdLessThanOrEqualTo(Integer value) {
            addCriterion("tender_utm_id <=", value, "tenderUtmId");
            return (Criteria) this;
        }

        public Criteria andTenderUtmIdIn(List<Integer> values) {
            addCriterion("tender_utm_id in", values, "tenderUtmId");
            return (Criteria) this;
        }

        public Criteria andTenderUtmIdNotIn(List<Integer> values) {
            addCriterion("tender_utm_id not in", values, "tenderUtmId");
            return (Criteria) this;
        }

        public Criteria andTenderUtmIdBetween(Integer value1, Integer value2) {
            addCriterion("tender_utm_id between", value1, value2, "tenderUtmId");
            return (Criteria) this;
        }

        public Criteria andTenderUtmIdNotBetween(Integer value1, Integer value2) {
            addCriterion("tender_utm_id not between", value1, value2, "tenderUtmId");
            return (Criteria) this;
        }

        public Criteria andTopDeptIdIsNull() {
            addCriterion("top_dept_id is null");
            return (Criteria) this;
        }

        public Criteria andTopDeptIdIsNotNull() {
            addCriterion("top_dept_id is not null");
            return (Criteria) this;
        }

        public Criteria andTopDeptIdEqualTo(Integer value) {
            addCriterion("top_dept_id =", value, "topDeptId");
            return (Criteria) this;
        }

        public Criteria andTopDeptIdNotEqualTo(Integer value) {
            addCriterion("top_dept_id <>", value, "topDeptId");
            return (Criteria) this;
        }

        public Criteria andTopDeptIdGreaterThan(Integer value) {
            addCriterion("top_dept_id >", value, "topDeptId");
            return (Criteria) this;
        }

        public Criteria andTopDeptIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("top_dept_id >=", value, "topDeptId");
            return (Criteria) this;
        }

        public Criteria andTopDeptIdLessThan(Integer value) {
            addCriterion("top_dept_id <", value, "topDeptId");
            return (Criteria) this;
        }

        public Criteria andTopDeptIdLessThanOrEqualTo(Integer value) {
            addCriterion("top_dept_id <=", value, "topDeptId");
            return (Criteria) this;
        }

        public Criteria andTopDeptIdIn(List<Integer> values) {
            addCriterion("top_dept_id in", values, "topDeptId");
            return (Criteria) this;
        }

        public Criteria andTopDeptIdNotIn(List<Integer> values) {
            addCriterion("top_dept_id not in", values, "topDeptId");
            return (Criteria) this;
        }

        public Criteria andTopDeptIdBetween(Integer value1, Integer value2) {
            addCriterion("top_dept_id between", value1, value2, "topDeptId");
            return (Criteria) this;
        }

        public Criteria andTopDeptIdNotBetween(Integer value1, Integer value2) {
            addCriterion("top_dept_id not between", value1, value2, "topDeptId");
            return (Criteria) this;
        }

        public Criteria andSecondDeptIdIsNull() {
            addCriterion("second_dept_id is null");
            return (Criteria) this;
        }

        public Criteria andSecondDeptIdIsNotNull() {
            addCriterion("second_dept_id is not null");
            return (Criteria) this;
        }

        public Criteria andSecondDeptIdEqualTo(Integer value) {
            addCriterion("second_dept_id =", value, "secondDeptId");
            return (Criteria) this;
        }

        public Criteria andSecondDeptIdNotEqualTo(Integer value) {
            addCriterion("second_dept_id <>", value, "secondDeptId");
            return (Criteria) this;
        }

        public Criteria andSecondDeptIdGreaterThan(Integer value) {
            addCriterion("second_dept_id >", value, "secondDeptId");
            return (Criteria) this;
        }

        public Criteria andSecondDeptIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("second_dept_id >=", value, "secondDeptId");
            return (Criteria) this;
        }

        public Criteria andSecondDeptIdLessThan(Integer value) {
            addCriterion("second_dept_id <", value, "secondDeptId");
            return (Criteria) this;
        }

        public Criteria andSecondDeptIdLessThanOrEqualTo(Integer value) {
            addCriterion("second_dept_id <=", value, "secondDeptId");
            return (Criteria) this;
        }

        public Criteria andSecondDeptIdIn(List<Integer> values) {
            addCriterion("second_dept_id in", values, "secondDeptId");
            return (Criteria) this;
        }

        public Criteria andSecondDeptIdNotIn(List<Integer> values) {
            addCriterion("second_dept_id not in", values, "secondDeptId");
            return (Criteria) this;
        }

        public Criteria andSecondDeptIdBetween(Integer value1, Integer value2) {
            addCriterion("second_dept_id between", value1, value2, "secondDeptId");
            return (Criteria) this;
        }

        public Criteria andSecondDeptIdNotBetween(Integer value1, Integer value2) {
            addCriterion("second_dept_id not between", value1, value2, "secondDeptId");
            return (Criteria) this;
        }

        public Criteria andThirdDeptIdIsNull() {
            addCriterion("third_dept_id is null");
            return (Criteria) this;
        }

        public Criteria andThirdDeptIdIsNotNull() {
            addCriterion("third_dept_id is not null");
            return (Criteria) this;
        }

        public Criteria andThirdDeptIdEqualTo(Integer value) {
            addCriterion("third_dept_id =", value, "thirdDeptId");
            return (Criteria) this;
        }

        public Criteria andThirdDeptIdNotEqualTo(Integer value) {
            addCriterion("third_dept_id <>", value, "thirdDeptId");
            return (Criteria) this;
        }

        public Criteria andThirdDeptIdGreaterThan(Integer value) {
            addCriterion("third_dept_id >", value, "thirdDeptId");
            return (Criteria) this;
        }

        public Criteria andThirdDeptIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("third_dept_id >=", value, "thirdDeptId");
            return (Criteria) this;
        }

        public Criteria andThirdDeptIdLessThan(Integer value) {
            addCriterion("third_dept_id <", value, "thirdDeptId");
            return (Criteria) this;
        }

        public Criteria andThirdDeptIdLessThanOrEqualTo(Integer value) {
            addCriterion("third_dept_id <=", value, "thirdDeptId");
            return (Criteria) this;
        }

        public Criteria andThirdDeptIdIn(List<Integer> values) {
            addCriterion("third_dept_id in", values, "thirdDeptId");
            return (Criteria) this;
        }

        public Criteria andThirdDeptIdNotIn(List<Integer> values) {
            addCriterion("third_dept_id not in", values, "thirdDeptId");
            return (Criteria) this;
        }

        public Criteria andThirdDeptIdBetween(Integer value1, Integer value2) {
            addCriterion("third_dept_id between", value1, value2, "thirdDeptId");
            return (Criteria) this;
        }

        public Criteria andThirdDeptIdNotBetween(Integer value1, Integer value2) {
            addCriterion("third_dept_id not between", value1, value2, "thirdDeptId");
            return (Criteria) this;
        }

        public Criteria andOperatorIsNull() {
            addCriterion("`operator` is null");
            return (Criteria) this;
        }

        public Criteria andOperatorIsNotNull() {
            addCriterion("`operator` is not null");
            return (Criteria) this;
        }

        public Criteria andOperatorEqualTo(Integer value) {
            addCriterion("`operator` =", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorNotEqualTo(Integer value) {
            addCriterion("`operator` <>", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorGreaterThan(Integer value) {
            addCriterion("`operator` >", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorGreaterThanOrEqualTo(Integer value) {
            addCriterion("`operator` >=", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorLessThan(Integer value) {
            addCriterion("`operator` <", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorLessThanOrEqualTo(Integer value) {
            addCriterion("`operator` <=", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorIn(List<Integer> values) {
            addCriterion("`operator` in", values, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorNotIn(List<Integer> values) {
            addCriterion("`operator` not in", values, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorBetween(Integer value1, Integer value2) {
            addCriterion("`operator` between", value1, value2, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorNotBetween(Integer value1, Integer value2) {
            addCriterion("`operator` not between", value1, value2, "operator");
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