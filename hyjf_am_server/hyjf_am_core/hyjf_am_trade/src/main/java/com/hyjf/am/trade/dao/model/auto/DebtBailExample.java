package com.hyjf.am.trade.dao.model.auto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DebtBailExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public DebtBailExample() {
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

        public Criteria andBorrowNidIsNull() {
            addCriterion("borrow_nid is null");
            return (Criteria) this;
        }

        public Criteria andBorrowNidIsNotNull() {
            addCriterion("borrow_nid is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowNidEqualTo(String value) {
            addCriterion("borrow_nid =", value, "borrowNid");
            return (Criteria) this;
        }

        public Criteria andBorrowNidNotEqualTo(String value) {
            addCriterion("borrow_nid <>", value, "borrowNid");
            return (Criteria) this;
        }

        public Criteria andBorrowNidGreaterThan(String value) {
            addCriterion("borrow_nid >", value, "borrowNid");
            return (Criteria) this;
        }

        public Criteria andBorrowNidGreaterThanOrEqualTo(String value) {
            addCriterion("borrow_nid >=", value, "borrowNid");
            return (Criteria) this;
        }

        public Criteria andBorrowNidLessThan(String value) {
            addCriterion("borrow_nid <", value, "borrowNid");
            return (Criteria) this;
        }

        public Criteria andBorrowNidLessThanOrEqualTo(String value) {
            addCriterion("borrow_nid <=", value, "borrowNid");
            return (Criteria) this;
        }

        public Criteria andBorrowNidLike(String value) {
            addCriterion("borrow_nid like", value, "borrowNid");
            return (Criteria) this;
        }

        public Criteria andBorrowNidNotLike(String value) {
            addCriterion("borrow_nid not like", value, "borrowNid");
            return (Criteria) this;
        }

        public Criteria andBorrowNidIn(List<String> values) {
            addCriterion("borrow_nid in", values, "borrowNid");
            return (Criteria) this;
        }

        public Criteria andBorrowNidNotIn(List<String> values) {
            addCriterion("borrow_nid not in", values, "borrowNid");
            return (Criteria) this;
        }

        public Criteria andBorrowNidBetween(String value1, String value2) {
            addCriterion("borrow_nid between", value1, value2, "borrowNid");
            return (Criteria) this;
        }

        public Criteria andBorrowNidNotBetween(String value1, String value2) {
            addCriterion("borrow_nid not between", value1, value2, "borrowNid");
            return (Criteria) this;
        }

        public Criteria andBorrowUidIsNull() {
            addCriterion("borrow_uid is null");
            return (Criteria) this;
        }

        public Criteria andBorrowUidIsNotNull() {
            addCriterion("borrow_uid is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowUidEqualTo(Integer value) {
            addCriterion("borrow_uid =", value, "borrowUid");
            return (Criteria) this;
        }

        public Criteria andBorrowUidNotEqualTo(Integer value) {
            addCriterion("borrow_uid <>", value, "borrowUid");
            return (Criteria) this;
        }

        public Criteria andBorrowUidGreaterThan(Integer value) {
            addCriterion("borrow_uid >", value, "borrowUid");
            return (Criteria) this;
        }

        public Criteria andBorrowUidGreaterThanOrEqualTo(Integer value) {
            addCriterion("borrow_uid >=", value, "borrowUid");
            return (Criteria) this;
        }

        public Criteria andBorrowUidLessThan(Integer value) {
            addCriterion("borrow_uid <", value, "borrowUid");
            return (Criteria) this;
        }

        public Criteria andBorrowUidLessThanOrEqualTo(Integer value) {
            addCriterion("borrow_uid <=", value, "borrowUid");
            return (Criteria) this;
        }

        public Criteria andBorrowUidIn(List<Integer> values) {
            addCriterion("borrow_uid in", values, "borrowUid");
            return (Criteria) this;
        }

        public Criteria andBorrowUidNotIn(List<Integer> values) {
            addCriterion("borrow_uid not in", values, "borrowUid");
            return (Criteria) this;
        }

        public Criteria andBorrowUidBetween(Integer value1, Integer value2) {
            addCriterion("borrow_uid between", value1, value2, "borrowUid");
            return (Criteria) this;
        }

        public Criteria andBorrowUidNotBetween(Integer value1, Integer value2) {
            addCriterion("borrow_uid not between", value1, value2, "borrowUid");
            return (Criteria) this;
        }

        public Criteria andOperaterUidIsNull() {
            addCriterion("operater_uid is null");
            return (Criteria) this;
        }

        public Criteria andOperaterUidIsNotNull() {
            addCriterion("operater_uid is not null");
            return (Criteria) this;
        }

        public Criteria andOperaterUidEqualTo(Integer value) {
            addCriterion("operater_uid =", value, "operaterUid");
            return (Criteria) this;
        }

        public Criteria andOperaterUidNotEqualTo(Integer value) {
            addCriterion("operater_uid <>", value, "operaterUid");
            return (Criteria) this;
        }

        public Criteria andOperaterUidGreaterThan(Integer value) {
            addCriterion("operater_uid >", value, "operaterUid");
            return (Criteria) this;
        }

        public Criteria andOperaterUidGreaterThanOrEqualTo(Integer value) {
            addCriterion("operater_uid >=", value, "operaterUid");
            return (Criteria) this;
        }

        public Criteria andOperaterUidLessThan(Integer value) {
            addCriterion("operater_uid <", value, "operaterUid");
            return (Criteria) this;
        }

        public Criteria andOperaterUidLessThanOrEqualTo(Integer value) {
            addCriterion("operater_uid <=", value, "operaterUid");
            return (Criteria) this;
        }

        public Criteria andOperaterUidIn(List<Integer> values) {
            addCriterion("operater_uid in", values, "operaterUid");
            return (Criteria) this;
        }

        public Criteria andOperaterUidNotIn(List<Integer> values) {
            addCriterion("operater_uid not in", values, "operaterUid");
            return (Criteria) this;
        }

        public Criteria andOperaterUidBetween(Integer value1, Integer value2) {
            addCriterion("operater_uid between", value1, value2, "operaterUid");
            return (Criteria) this;
        }

        public Criteria andOperaterUidNotBetween(Integer value1, Integer value2) {
            addCriterion("operater_uid not between", value1, value2, "operaterUid");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeIsNull() {
            addCriterion("updatetime is null");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeIsNotNull() {
            addCriterion("updatetime is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeEqualTo(Integer value) {
            addCriterion("updatetime =", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeNotEqualTo(Integer value) {
            addCriterion("updatetime <>", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeGreaterThan(Integer value) {
            addCriterion("updatetime >", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("updatetime >=", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeLessThan(Integer value) {
            addCriterion("updatetime <", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeLessThanOrEqualTo(Integer value) {
            addCriterion("updatetime <=", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeIn(List<Integer> values) {
            addCriterion("updatetime in", values, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeNotIn(List<Integer> values) {
            addCriterion("updatetime not in", values, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeBetween(Integer value1, Integer value2) {
            addCriterion("updatetime between", value1, value2, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeNotBetween(Integer value1, Integer value2) {
            addCriterion("updatetime not between", value1, value2, "updatetime");
            return (Criteria) this;
        }

        public Criteria andBailNumIsNull() {
            addCriterion("bail_num is null");
            return (Criteria) this;
        }

        public Criteria andBailNumIsNotNull() {
            addCriterion("bail_num is not null");
            return (Criteria) this;
        }

        public Criteria andBailNumEqualTo(BigDecimal value) {
            addCriterion("bail_num =", value, "bailNum");
            return (Criteria) this;
        }

        public Criteria andBailNumNotEqualTo(BigDecimal value) {
            addCriterion("bail_num <>", value, "bailNum");
            return (Criteria) this;
        }

        public Criteria andBailNumGreaterThan(BigDecimal value) {
            addCriterion("bail_num >", value, "bailNum");
            return (Criteria) this;
        }

        public Criteria andBailNumGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("bail_num >=", value, "bailNum");
            return (Criteria) this;
        }

        public Criteria andBailNumLessThan(BigDecimal value) {
            addCriterion("bail_num <", value, "bailNum");
            return (Criteria) this;
        }

        public Criteria andBailNumLessThanOrEqualTo(BigDecimal value) {
            addCriterion("bail_num <=", value, "bailNum");
            return (Criteria) this;
        }

        public Criteria andBailNumIn(List<BigDecimal> values) {
            addCriterion("bail_num in", values, "bailNum");
            return (Criteria) this;
        }

        public Criteria andBailNumNotIn(List<BigDecimal> values) {
            addCriterion("bail_num not in", values, "bailNum");
            return (Criteria) this;
        }

        public Criteria andBailNumBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("bail_num between", value1, value2, "bailNum");
            return (Criteria) this;
        }

        public Criteria andBailNumNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("bail_num not between", value1, value2, "bailNum");
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