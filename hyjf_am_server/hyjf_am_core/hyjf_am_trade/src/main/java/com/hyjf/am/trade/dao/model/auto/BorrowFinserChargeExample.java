package com.hyjf.am.trade.dao.model.auto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BorrowFinserChargeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public BorrowFinserChargeExample() {
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

        public Criteria andChargeCdIsNull() {
            addCriterion("charge_cd is null");
            return (Criteria) this;
        }

        public Criteria andChargeCdIsNotNull() {
            addCriterion("charge_cd is not null");
            return (Criteria) this;
        }

        public Criteria andChargeCdEqualTo(String value) {
            addCriterion("charge_cd =", value, "chargeCd");
            return (Criteria) this;
        }

        public Criteria andChargeCdNotEqualTo(String value) {
            addCriterion("charge_cd <>", value, "chargeCd");
            return (Criteria) this;
        }

        public Criteria andChargeCdGreaterThan(String value) {
            addCriterion("charge_cd >", value, "chargeCd");
            return (Criteria) this;
        }

        public Criteria andChargeCdGreaterThanOrEqualTo(String value) {
            addCriterion("charge_cd >=", value, "chargeCd");
            return (Criteria) this;
        }

        public Criteria andChargeCdLessThan(String value) {
            addCriterion("charge_cd <", value, "chargeCd");
            return (Criteria) this;
        }

        public Criteria andChargeCdLessThanOrEqualTo(String value) {
            addCriterion("charge_cd <=", value, "chargeCd");
            return (Criteria) this;
        }

        public Criteria andChargeCdLike(String value) {
            addCriterion("charge_cd like", value, "chargeCd");
            return (Criteria) this;
        }

        public Criteria andChargeCdNotLike(String value) {
            addCriterion("charge_cd not like", value, "chargeCd");
            return (Criteria) this;
        }

        public Criteria andChargeCdIn(List<String> values) {
            addCriterion("charge_cd in", values, "chargeCd");
            return (Criteria) this;
        }

        public Criteria andChargeCdNotIn(List<String> values) {
            addCriterion("charge_cd not in", values, "chargeCd");
            return (Criteria) this;
        }

        public Criteria andChargeCdBetween(String value1, String value2) {
            addCriterion("charge_cd between", value1, value2, "chargeCd");
            return (Criteria) this;
        }

        public Criteria andChargeCdNotBetween(String value1, String value2) {
            addCriterion("charge_cd not between", value1, value2, "chargeCd");
            return (Criteria) this;
        }

        public Criteria andChargeTypeIsNull() {
            addCriterion("charge_type is null");
            return (Criteria) this;
        }

        public Criteria andChargeTypeIsNotNull() {
            addCriterion("charge_type is not null");
            return (Criteria) this;
        }

        public Criteria andChargeTypeEqualTo(String value) {
            addCriterion("charge_type =", value, "chargeType");
            return (Criteria) this;
        }

        public Criteria andChargeTypeNotEqualTo(String value) {
            addCriterion("charge_type <>", value, "chargeType");
            return (Criteria) this;
        }

        public Criteria andChargeTypeGreaterThan(String value) {
            addCriterion("charge_type >", value, "chargeType");
            return (Criteria) this;
        }

        public Criteria andChargeTypeGreaterThanOrEqualTo(String value) {
            addCriterion("charge_type >=", value, "chargeType");
            return (Criteria) this;
        }

        public Criteria andChargeTypeLessThan(String value) {
            addCriterion("charge_type <", value, "chargeType");
            return (Criteria) this;
        }

        public Criteria andChargeTypeLessThanOrEqualTo(String value) {
            addCriterion("charge_type <=", value, "chargeType");
            return (Criteria) this;
        }

        public Criteria andChargeTypeLike(String value) {
            addCriterion("charge_type like", value, "chargeType");
            return (Criteria) this;
        }

        public Criteria andChargeTypeNotLike(String value) {
            addCriterion("charge_type not like", value, "chargeType");
            return (Criteria) this;
        }

        public Criteria andChargeTypeIn(List<String> values) {
            addCriterion("charge_type in", values, "chargeType");
            return (Criteria) this;
        }

        public Criteria andChargeTypeNotIn(List<String> values) {
            addCriterion("charge_type not in", values, "chargeType");
            return (Criteria) this;
        }

        public Criteria andChargeTypeBetween(String value1, String value2) {
            addCriterion("charge_type between", value1, value2, "chargeType");
            return (Criteria) this;
        }

        public Criteria andChargeTypeNotBetween(String value1, String value2) {
            addCriterion("charge_type not between", value1, value2, "chargeType");
            return (Criteria) this;
        }

        public Criteria andChargeTimeIsNull() {
            addCriterion("charge_time is null");
            return (Criteria) this;
        }

        public Criteria andChargeTimeIsNotNull() {
            addCriterion("charge_time is not null");
            return (Criteria) this;
        }

        public Criteria andChargeTimeEqualTo(Integer value) {
            addCriterion("charge_time =", value, "chargeTime");
            return (Criteria) this;
        }

        public Criteria andChargeTimeNotEqualTo(Integer value) {
            addCriterion("charge_time <>", value, "chargeTime");
            return (Criteria) this;
        }

        public Criteria andChargeTimeGreaterThan(Integer value) {
            addCriterion("charge_time >", value, "chargeTime");
            return (Criteria) this;
        }

        public Criteria andChargeTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("charge_time >=", value, "chargeTime");
            return (Criteria) this;
        }

        public Criteria andChargeTimeLessThan(Integer value) {
            addCriterion("charge_time <", value, "chargeTime");
            return (Criteria) this;
        }

        public Criteria andChargeTimeLessThanOrEqualTo(Integer value) {
            addCriterion("charge_time <=", value, "chargeTime");
            return (Criteria) this;
        }

        public Criteria andChargeTimeIn(List<Integer> values) {
            addCriterion("charge_time in", values, "chargeTime");
            return (Criteria) this;
        }

        public Criteria andChargeTimeNotIn(List<Integer> values) {
            addCriterion("charge_time not in", values, "chargeTime");
            return (Criteria) this;
        }

        public Criteria andChargeTimeBetween(Integer value1, Integer value2) {
            addCriterion("charge_time between", value1, value2, "chargeTime");
            return (Criteria) this;
        }

        public Criteria andChargeTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("charge_time not between", value1, value2, "chargeTime");
            return (Criteria) this;
        }

        public Criteria andChargeTimeTypeIsNull() {
            addCriterion("charge_time_type is null");
            return (Criteria) this;
        }

        public Criteria andChargeTimeTypeIsNotNull() {
            addCriterion("charge_time_type is not null");
            return (Criteria) this;
        }

        public Criteria andChargeTimeTypeEqualTo(String value) {
            addCriterion("charge_time_type =", value, "chargeTimeType");
            return (Criteria) this;
        }

        public Criteria andChargeTimeTypeNotEqualTo(String value) {
            addCriterion("charge_time_type <>", value, "chargeTimeType");
            return (Criteria) this;
        }

        public Criteria andChargeTimeTypeGreaterThan(String value) {
            addCriterion("charge_time_type >", value, "chargeTimeType");
            return (Criteria) this;
        }

        public Criteria andChargeTimeTypeGreaterThanOrEqualTo(String value) {
            addCriterion("charge_time_type >=", value, "chargeTimeType");
            return (Criteria) this;
        }

        public Criteria andChargeTimeTypeLessThan(String value) {
            addCriterion("charge_time_type <", value, "chargeTimeType");
            return (Criteria) this;
        }

        public Criteria andChargeTimeTypeLessThanOrEqualTo(String value) {
            addCriterion("charge_time_type <=", value, "chargeTimeType");
            return (Criteria) this;
        }

        public Criteria andChargeTimeTypeLike(String value) {
            addCriterion("charge_time_type like", value, "chargeTimeType");
            return (Criteria) this;
        }

        public Criteria andChargeTimeTypeNotLike(String value) {
            addCriterion("charge_time_type not like", value, "chargeTimeType");
            return (Criteria) this;
        }

        public Criteria andChargeTimeTypeIn(List<String> values) {
            addCriterion("charge_time_type in", values, "chargeTimeType");
            return (Criteria) this;
        }

        public Criteria andChargeTimeTypeNotIn(List<String> values) {
            addCriterion("charge_time_type not in", values, "chargeTimeType");
            return (Criteria) this;
        }

        public Criteria andChargeTimeTypeBetween(String value1, String value2) {
            addCriterion("charge_time_type between", value1, value2, "chargeTimeType");
            return (Criteria) this;
        }

        public Criteria andChargeTimeTypeNotBetween(String value1, String value2) {
            addCriterion("charge_time_type not between", value1, value2, "chargeTimeType");
            return (Criteria) this;
        }

        public Criteria andChargeRateIsNull() {
            addCriterion("charge_rate is null");
            return (Criteria) this;
        }

        public Criteria andChargeRateIsNotNull() {
            addCriterion("charge_rate is not null");
            return (Criteria) this;
        }

        public Criteria andChargeRateEqualTo(String value) {
            addCriterion("charge_rate =", value, "chargeRate");
            return (Criteria) this;
        }

        public Criteria andChargeRateNotEqualTo(String value) {
            addCriterion("charge_rate <>", value, "chargeRate");
            return (Criteria) this;
        }

        public Criteria andChargeRateGreaterThan(String value) {
            addCriterion("charge_rate >", value, "chargeRate");
            return (Criteria) this;
        }

        public Criteria andChargeRateGreaterThanOrEqualTo(String value) {
            addCriterion("charge_rate >=", value, "chargeRate");
            return (Criteria) this;
        }

        public Criteria andChargeRateLessThan(String value) {
            addCriterion("charge_rate <", value, "chargeRate");
            return (Criteria) this;
        }

        public Criteria andChargeRateLessThanOrEqualTo(String value) {
            addCriterion("charge_rate <=", value, "chargeRate");
            return (Criteria) this;
        }

        public Criteria andChargeRateLike(String value) {
            addCriterion("charge_rate like", value, "chargeRate");
            return (Criteria) this;
        }

        public Criteria andChargeRateNotLike(String value) {
            addCriterion("charge_rate not like", value, "chargeRate");
            return (Criteria) this;
        }

        public Criteria andChargeRateIn(List<String> values) {
            addCriterion("charge_rate in", values, "chargeRate");
            return (Criteria) this;
        }

        public Criteria andChargeRateNotIn(List<String> values) {
            addCriterion("charge_rate not in", values, "chargeRate");
            return (Criteria) this;
        }

        public Criteria andChargeRateBetween(String value1, String value2) {
            addCriterion("charge_rate between", value1, value2, "chargeRate");
            return (Criteria) this;
        }

        public Criteria andChargeRateNotBetween(String value1, String value2) {
            addCriterion("charge_rate not between", value1, value2, "chargeRate");
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

        public Criteria andProjectTypeIsNull() {
            addCriterion("project_type is null");
            return (Criteria) this;
        }

        public Criteria andProjectTypeIsNotNull() {
            addCriterion("project_type is not null");
            return (Criteria) this;
        }

        public Criteria andProjectTypeEqualTo(Integer value) {
            addCriterion("project_type =", value, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeNotEqualTo(Integer value) {
            addCriterion("project_type <>", value, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeGreaterThan(Integer value) {
            addCriterion("project_type >", value, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("project_type >=", value, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeLessThan(Integer value) {
            addCriterion("project_type <", value, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeLessThanOrEqualTo(Integer value) {
            addCriterion("project_type <=", value, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeIn(List<Integer> values) {
            addCriterion("project_type in", values, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeNotIn(List<Integer> values) {
            addCriterion("project_type not in", values, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeBetween(Integer value1, Integer value2) {
            addCriterion("project_type between", value1, value2, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("project_type not between", value1, value2, "projectType");
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