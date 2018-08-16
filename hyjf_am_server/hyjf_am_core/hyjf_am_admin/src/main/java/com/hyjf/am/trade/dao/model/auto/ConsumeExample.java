package com.hyjf.am.trade.dao.model.auto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ConsumeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public ConsumeExample() {
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

        public Criteria andAmountIsNull() {
            addCriterion("amount is null");
            return (Criteria) this;
        }

        public Criteria andAmountIsNotNull() {
            addCriterion("amount is not null");
            return (Criteria) this;
        }

        public Criteria andAmountEqualTo(BigDecimal value) {
            addCriterion("amount =", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotEqualTo(BigDecimal value) {
            addCriterion("amount <>", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThan(BigDecimal value) {
            addCriterion("amount >", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("amount >=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThan(BigDecimal value) {
            addCriterion("amount <", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("amount <=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountIn(List<BigDecimal> values) {
            addCriterion("amount in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotIn(List<BigDecimal> values) {
            addCriterion("amount not in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("amount between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("amount not between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountRealIsNull() {
            addCriterion("amount_real is null");
            return (Criteria) this;
        }

        public Criteria andAmountRealIsNotNull() {
            addCriterion("amount_real is not null");
            return (Criteria) this;
        }

        public Criteria andAmountRealEqualTo(BigDecimal value) {
            addCriterion("amount_real =", value, "amountReal");
            return (Criteria) this;
        }

        public Criteria andAmountRealNotEqualTo(BigDecimal value) {
            addCriterion("amount_real <>", value, "amountReal");
            return (Criteria) this;
        }

        public Criteria andAmountRealGreaterThan(BigDecimal value) {
            addCriterion("amount_real >", value, "amountReal");
            return (Criteria) this;
        }

        public Criteria andAmountRealGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("amount_real >=", value, "amountReal");
            return (Criteria) this;
        }

        public Criteria andAmountRealLessThan(BigDecimal value) {
            addCriterion("amount_real <", value, "amountReal");
            return (Criteria) this;
        }

        public Criteria andAmountRealLessThanOrEqualTo(BigDecimal value) {
            addCriterion("amount_real <=", value, "amountReal");
            return (Criteria) this;
        }

        public Criteria andAmountRealIn(List<BigDecimal> values) {
            addCriterion("amount_real in", values, "amountReal");
            return (Criteria) this;
        }

        public Criteria andAmountRealNotIn(List<BigDecimal> values) {
            addCriterion("amount_real not in", values, "amountReal");
            return (Criteria) this;
        }

        public Criteria andAmountRealBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("amount_real between", value1, value2, "amountReal");
            return (Criteria) this;
        }

        public Criteria andAmountRealNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("amount_real not between", value1, value2, "amountReal");
            return (Criteria) this;
        }

        public Criteria andPersonNumIsNull() {
            addCriterion("person_num is null");
            return (Criteria) this;
        }

        public Criteria andPersonNumIsNotNull() {
            addCriterion("person_num is not null");
            return (Criteria) this;
        }

        public Criteria andPersonNumEqualTo(Integer value) {
            addCriterion("person_num =", value, "personNum");
            return (Criteria) this;
        }

        public Criteria andPersonNumNotEqualTo(Integer value) {
            addCriterion("person_num <>", value, "personNum");
            return (Criteria) this;
        }

        public Criteria andPersonNumGreaterThan(Integer value) {
            addCriterion("person_num >", value, "personNum");
            return (Criteria) this;
        }

        public Criteria andPersonNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("person_num >=", value, "personNum");
            return (Criteria) this;
        }

        public Criteria andPersonNumLessThan(Integer value) {
            addCriterion("person_num <", value, "personNum");
            return (Criteria) this;
        }

        public Criteria andPersonNumLessThanOrEqualTo(Integer value) {
            addCriterion("person_num <=", value, "personNum");
            return (Criteria) this;
        }

        public Criteria andPersonNumIn(List<Integer> values) {
            addCriterion("person_num in", values, "personNum");
            return (Criteria) this;
        }

        public Criteria andPersonNumNotIn(List<Integer> values) {
            addCriterion("person_num not in", values, "personNum");
            return (Criteria) this;
        }

        public Criteria andPersonNumBetween(Integer value1, Integer value2) {
            addCriterion("person_num between", value1, value2, "personNum");
            return (Criteria) this;
        }

        public Criteria andPersonNumNotBetween(Integer value1, Integer value2) {
            addCriterion("person_num not between", value1, value2, "personNum");
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

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("`status` =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("`status` <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("`status` >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("`status` >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("`status` <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("`status` <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("`status` in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("`status` not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("`status` between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("`status` not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andTimeIsNull() {
            addCriterion("`time` is null");
            return (Criteria) this;
        }

        public Criteria andTimeIsNotNull() {
            addCriterion("`time` is not null");
            return (Criteria) this;
        }

        public Criteria andTimeEqualTo(String value) {
            addCriterion("`time` =", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeNotEqualTo(String value) {
            addCriterion("`time` <>", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeGreaterThan(String value) {
            addCriterion("`time` >", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeGreaterThanOrEqualTo(String value) {
            addCriterion("`time` >=", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeLessThan(String value) {
            addCriterion("`time` <", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeLessThanOrEqualTo(String value) {
            addCriterion("`time` <=", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeLike(String value) {
            addCriterion("`time` like", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeNotLike(String value) {
            addCriterion("`time` not like", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeIn(List<String> values) {
            addCriterion("`time` in", values, "time");
            return (Criteria) this;
        }

        public Criteria andTimeNotIn(List<String> values) {
            addCriterion("`time` not in", values, "time");
            return (Criteria) this;
        }

        public Criteria andTimeBetween(String value1, String value2) {
            addCriterion("`time` between", value1, value2, "time");
            return (Criteria) this;
        }

        public Criteria andTimeNotBetween(String value1, String value2) {
            addCriterion("`time` not between", value1, value2, "time");
            return (Criteria) this;
        }

        public Criteria andInsertTimeIsNull() {
            addCriterion("insert_time is null");
            return (Criteria) this;
        }

        public Criteria andInsertTimeIsNotNull() {
            addCriterion("insert_time is not null");
            return (Criteria) this;
        }

        public Criteria andInsertTimeEqualTo(String value) {
            addCriterion("insert_time =", value, "insertTime");
            return (Criteria) this;
        }

        public Criteria andInsertTimeNotEqualTo(String value) {
            addCriterion("insert_time <>", value, "insertTime");
            return (Criteria) this;
        }

        public Criteria andInsertTimeGreaterThan(String value) {
            addCriterion("insert_time >", value, "insertTime");
            return (Criteria) this;
        }

        public Criteria andInsertTimeGreaterThanOrEqualTo(String value) {
            addCriterion("insert_time >=", value, "insertTime");
            return (Criteria) this;
        }

        public Criteria andInsertTimeLessThan(String value) {
            addCriterion("insert_time <", value, "insertTime");
            return (Criteria) this;
        }

        public Criteria andInsertTimeLessThanOrEqualTo(String value) {
            addCriterion("insert_time <=", value, "insertTime");
            return (Criteria) this;
        }

        public Criteria andInsertTimeLike(String value) {
            addCriterion("insert_time like", value, "insertTime");
            return (Criteria) this;
        }

        public Criteria andInsertTimeNotLike(String value) {
            addCriterion("insert_time not like", value, "insertTime");
            return (Criteria) this;
        }

        public Criteria andInsertTimeIn(List<String> values) {
            addCriterion("insert_time in", values, "insertTime");
            return (Criteria) this;
        }

        public Criteria andInsertTimeNotIn(List<String> values) {
            addCriterion("insert_time not in", values, "insertTime");
            return (Criteria) this;
        }

        public Criteria andInsertTimeBetween(String value1, String value2) {
            addCriterion("insert_time between", value1, value2, "insertTime");
            return (Criteria) this;
        }

        public Criteria andInsertTimeNotBetween(String value1, String value2) {
            addCriterion("insert_time not between", value1, value2, "insertTime");
            return (Criteria) this;
        }

        public Criteria andPersonRealIsNull() {
            addCriterion("person_real is null");
            return (Criteria) this;
        }

        public Criteria andPersonRealIsNotNull() {
            addCriterion("person_real is not null");
            return (Criteria) this;
        }

        public Criteria andPersonRealEqualTo(Integer value) {
            addCriterion("person_real =", value, "personReal");
            return (Criteria) this;
        }

        public Criteria andPersonRealNotEqualTo(Integer value) {
            addCriterion("person_real <>", value, "personReal");
            return (Criteria) this;
        }

        public Criteria andPersonRealGreaterThan(Integer value) {
            addCriterion("person_real >", value, "personReal");
            return (Criteria) this;
        }

        public Criteria andPersonRealGreaterThanOrEqualTo(Integer value) {
            addCriterion("person_real >=", value, "personReal");
            return (Criteria) this;
        }

        public Criteria andPersonRealLessThan(Integer value) {
            addCriterion("person_real <", value, "personReal");
            return (Criteria) this;
        }

        public Criteria andPersonRealLessThanOrEqualTo(Integer value) {
            addCriterion("person_real <=", value, "personReal");
            return (Criteria) this;
        }

        public Criteria andPersonRealIn(List<Integer> values) {
            addCriterion("person_real in", values, "personReal");
            return (Criteria) this;
        }

        public Criteria andPersonRealNotIn(List<Integer> values) {
            addCriterion("person_real not in", values, "personReal");
            return (Criteria) this;
        }

        public Criteria andPersonRealBetween(Integer value1, Integer value2) {
            addCriterion("person_real between", value1, value2, "personReal");
            return (Criteria) this;
        }

        public Criteria andPersonRealNotBetween(Integer value1, Integer value2) {
            addCriterion("person_real not between", value1, value2, "personReal");
            return (Criteria) this;
        }

        public Criteria andReleaseIsNull() {
            addCriterion("`release` is null");
            return (Criteria) this;
        }

        public Criteria andReleaseIsNotNull() {
            addCriterion("`release` is not null");
            return (Criteria) this;
        }

        public Criteria andReleaseEqualTo(Integer value) {
            addCriterion("`release` =", value, "release");
            return (Criteria) this;
        }

        public Criteria andReleaseNotEqualTo(Integer value) {
            addCriterion("`release` <>", value, "release");
            return (Criteria) this;
        }

        public Criteria andReleaseGreaterThan(Integer value) {
            addCriterion("`release` >", value, "release");
            return (Criteria) this;
        }

        public Criteria andReleaseGreaterThanOrEqualTo(Integer value) {
            addCriterion("`release` >=", value, "release");
            return (Criteria) this;
        }

        public Criteria andReleaseLessThan(Integer value) {
            addCriterion("`release` <", value, "release");
            return (Criteria) this;
        }

        public Criteria andReleaseLessThanOrEqualTo(Integer value) {
            addCriterion("`release` <=", value, "release");
            return (Criteria) this;
        }

        public Criteria andReleaseIn(List<Integer> values) {
            addCriterion("`release` in", values, "release");
            return (Criteria) this;
        }

        public Criteria andReleaseNotIn(List<Integer> values) {
            addCriterion("`release` not in", values, "release");
            return (Criteria) this;
        }

        public Criteria andReleaseBetween(Integer value1, Integer value2) {
            addCriterion("`release` between", value1, value2, "release");
            return (Criteria) this;
        }

        public Criteria andReleaseNotBetween(Integer value1, Integer value2) {
            addCriterion("`release` not between", value1, value2, "release");
            return (Criteria) this;
        }

        public Criteria andInsertDayIsNull() {
            addCriterion("insert_day is null");
            return (Criteria) this;
        }

        public Criteria andInsertDayIsNotNull() {
            addCriterion("insert_day is not null");
            return (Criteria) this;
        }

        public Criteria andInsertDayEqualTo(String value) {
            addCriterion("insert_day =", value, "insertDay");
            return (Criteria) this;
        }

        public Criteria andInsertDayNotEqualTo(String value) {
            addCriterion("insert_day <>", value, "insertDay");
            return (Criteria) this;
        }

        public Criteria andInsertDayGreaterThan(String value) {
            addCriterion("insert_day >", value, "insertDay");
            return (Criteria) this;
        }

        public Criteria andInsertDayGreaterThanOrEqualTo(String value) {
            addCriterion("insert_day >=", value, "insertDay");
            return (Criteria) this;
        }

        public Criteria andInsertDayLessThan(String value) {
            addCriterion("insert_day <", value, "insertDay");
            return (Criteria) this;
        }

        public Criteria andInsertDayLessThanOrEqualTo(String value) {
            addCriterion("insert_day <=", value, "insertDay");
            return (Criteria) this;
        }

        public Criteria andInsertDayLike(String value) {
            addCriterion("insert_day like", value, "insertDay");
            return (Criteria) this;
        }

        public Criteria andInsertDayNotLike(String value) {
            addCriterion("insert_day not like", value, "insertDay");
            return (Criteria) this;
        }

        public Criteria andInsertDayIn(List<String> values) {
            addCriterion("insert_day in", values, "insertDay");
            return (Criteria) this;
        }

        public Criteria andInsertDayNotIn(List<String> values) {
            addCriterion("insert_day not in", values, "insertDay");
            return (Criteria) this;
        }

        public Criteria andInsertDayBetween(String value1, String value2) {
            addCriterion("insert_day between", value1, value2, "insertDay");
            return (Criteria) this;
        }

        public Criteria andInsertDayNotBetween(String value1, String value2) {
            addCriterion("insert_day not between", value1, value2, "insertDay");
            return (Criteria) this;
        }

        public Criteria andConsumeIdIsNull() {
            addCriterion("consume_id is null");
            return (Criteria) this;
        }

        public Criteria andConsumeIdIsNotNull() {
            addCriterion("consume_id is not null");
            return (Criteria) this;
        }

        public Criteria andConsumeIdEqualTo(String value) {
            addCriterion("consume_id =", value, "consumeId");
            return (Criteria) this;
        }

        public Criteria andConsumeIdNotEqualTo(String value) {
            addCriterion("consume_id <>", value, "consumeId");
            return (Criteria) this;
        }

        public Criteria andConsumeIdGreaterThan(String value) {
            addCriterion("consume_id >", value, "consumeId");
            return (Criteria) this;
        }

        public Criteria andConsumeIdGreaterThanOrEqualTo(String value) {
            addCriterion("consume_id >=", value, "consumeId");
            return (Criteria) this;
        }

        public Criteria andConsumeIdLessThan(String value) {
            addCriterion("consume_id <", value, "consumeId");
            return (Criteria) this;
        }

        public Criteria andConsumeIdLessThanOrEqualTo(String value) {
            addCriterion("consume_id <=", value, "consumeId");
            return (Criteria) this;
        }

        public Criteria andConsumeIdLike(String value) {
            addCriterion("consume_id like", value, "consumeId");
            return (Criteria) this;
        }

        public Criteria andConsumeIdNotLike(String value) {
            addCriterion("consume_id not like", value, "consumeId");
            return (Criteria) this;
        }

        public Criteria andConsumeIdIn(List<String> values) {
            addCriterion("consume_id in", values, "consumeId");
            return (Criteria) this;
        }

        public Criteria andConsumeIdNotIn(List<String> values) {
            addCriterion("consume_id not in", values, "consumeId");
            return (Criteria) this;
        }

        public Criteria andConsumeIdBetween(String value1, String value2) {
            addCriterion("consume_id between", value1, value2, "consumeId");
            return (Criteria) this;
        }

        public Criteria andConsumeIdNotBetween(String value1, String value2) {
            addCriterion("consume_id not between", value1, value2, "consumeId");
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