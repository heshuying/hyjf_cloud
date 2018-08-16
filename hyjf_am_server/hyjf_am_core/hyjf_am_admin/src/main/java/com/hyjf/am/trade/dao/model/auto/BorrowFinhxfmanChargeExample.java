package com.hyjf.am.trade.dao.model.auto;

import java.util.ArrayList;
import java.util.List;

public class BorrowFinhxfmanChargeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public BorrowFinhxfmanChargeExample() {
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

        public Criteria andManChargeCdIsNull() {
            addCriterion("man_charge_cd is null");
            return (Criteria) this;
        }

        public Criteria andManChargeCdIsNotNull() {
            addCriterion("man_charge_cd is not null");
            return (Criteria) this;
        }

        public Criteria andManChargeCdEqualTo(String value) {
            addCriterion("man_charge_cd =", value, "manChargeCd");
            return (Criteria) this;
        }

        public Criteria andManChargeCdNotEqualTo(String value) {
            addCriterion("man_charge_cd <>", value, "manChargeCd");
            return (Criteria) this;
        }

        public Criteria andManChargeCdGreaterThan(String value) {
            addCriterion("man_charge_cd >", value, "manChargeCd");
            return (Criteria) this;
        }

        public Criteria andManChargeCdGreaterThanOrEqualTo(String value) {
            addCriterion("man_charge_cd >=", value, "manChargeCd");
            return (Criteria) this;
        }

        public Criteria andManChargeCdLessThan(String value) {
            addCriterion("man_charge_cd <", value, "manChargeCd");
            return (Criteria) this;
        }

        public Criteria andManChargeCdLessThanOrEqualTo(String value) {
            addCriterion("man_charge_cd <=", value, "manChargeCd");
            return (Criteria) this;
        }

        public Criteria andManChargeCdLike(String value) {
            addCriterion("man_charge_cd like", value, "manChargeCd");
            return (Criteria) this;
        }

        public Criteria andManChargeCdNotLike(String value) {
            addCriterion("man_charge_cd not like", value, "manChargeCd");
            return (Criteria) this;
        }

        public Criteria andManChargeCdIn(List<String> values) {
            addCriterion("man_charge_cd in", values, "manChargeCd");
            return (Criteria) this;
        }

        public Criteria andManChargeCdNotIn(List<String> values) {
            addCriterion("man_charge_cd not in", values, "manChargeCd");
            return (Criteria) this;
        }

        public Criteria andManChargeCdBetween(String value1, String value2) {
            addCriterion("man_charge_cd between", value1, value2, "manChargeCd");
            return (Criteria) this;
        }

        public Criteria andManChargeCdNotBetween(String value1, String value2) {
            addCriterion("man_charge_cd not between", value1, value2, "manChargeCd");
            return (Criteria) this;
        }

        public Criteria andManChargeTypeIsNull() {
            addCriterion("man_charge_type is null");
            return (Criteria) this;
        }

        public Criteria andManChargeTypeIsNotNull() {
            addCriterion("man_charge_type is not null");
            return (Criteria) this;
        }

        public Criteria andManChargeTypeEqualTo(String value) {
            addCriterion("man_charge_type =", value, "manChargeType");
            return (Criteria) this;
        }

        public Criteria andManChargeTypeNotEqualTo(String value) {
            addCriterion("man_charge_type <>", value, "manChargeType");
            return (Criteria) this;
        }

        public Criteria andManChargeTypeGreaterThan(String value) {
            addCriterion("man_charge_type >", value, "manChargeType");
            return (Criteria) this;
        }

        public Criteria andManChargeTypeGreaterThanOrEqualTo(String value) {
            addCriterion("man_charge_type >=", value, "manChargeType");
            return (Criteria) this;
        }

        public Criteria andManChargeTypeLessThan(String value) {
            addCriterion("man_charge_type <", value, "manChargeType");
            return (Criteria) this;
        }

        public Criteria andManChargeTypeLessThanOrEqualTo(String value) {
            addCriterion("man_charge_type <=", value, "manChargeType");
            return (Criteria) this;
        }

        public Criteria andManChargeTypeLike(String value) {
            addCriterion("man_charge_type like", value, "manChargeType");
            return (Criteria) this;
        }

        public Criteria andManChargeTypeNotLike(String value) {
            addCriterion("man_charge_type not like", value, "manChargeType");
            return (Criteria) this;
        }

        public Criteria andManChargeTypeIn(List<String> values) {
            addCriterion("man_charge_type in", values, "manChargeType");
            return (Criteria) this;
        }

        public Criteria andManChargeTypeNotIn(List<String> values) {
            addCriterion("man_charge_type not in", values, "manChargeType");
            return (Criteria) this;
        }

        public Criteria andManChargeTypeBetween(String value1, String value2) {
            addCriterion("man_charge_type between", value1, value2, "manChargeType");
            return (Criteria) this;
        }

        public Criteria andManChargeTypeNotBetween(String value1, String value2) {
            addCriterion("man_charge_type not between", value1, value2, "manChargeType");
            return (Criteria) this;
        }

        public Criteria andManChargePerIsNull() {
            addCriterion("man_charge_per is null");
            return (Criteria) this;
        }

        public Criteria andManChargePerIsNotNull() {
            addCriterion("man_charge_per is not null");
            return (Criteria) this;
        }

        public Criteria andManChargePerEqualTo(String value) {
            addCriterion("man_charge_per =", value, "manChargePer");
            return (Criteria) this;
        }

        public Criteria andManChargePerNotEqualTo(String value) {
            addCriterion("man_charge_per <>", value, "manChargePer");
            return (Criteria) this;
        }

        public Criteria andManChargePerGreaterThan(String value) {
            addCriterion("man_charge_per >", value, "manChargePer");
            return (Criteria) this;
        }

        public Criteria andManChargePerGreaterThanOrEqualTo(String value) {
            addCriterion("man_charge_per >=", value, "manChargePer");
            return (Criteria) this;
        }

        public Criteria andManChargePerLessThan(String value) {
            addCriterion("man_charge_per <", value, "manChargePer");
            return (Criteria) this;
        }

        public Criteria andManChargePerLessThanOrEqualTo(String value) {
            addCriterion("man_charge_per <=", value, "manChargePer");
            return (Criteria) this;
        }

        public Criteria andManChargePerLike(String value) {
            addCriterion("man_charge_per like", value, "manChargePer");
            return (Criteria) this;
        }

        public Criteria andManChargePerNotLike(String value) {
            addCriterion("man_charge_per not like", value, "manChargePer");
            return (Criteria) this;
        }

        public Criteria andManChargePerIn(List<String> values) {
            addCriterion("man_charge_per in", values, "manChargePer");
            return (Criteria) this;
        }

        public Criteria andManChargePerNotIn(List<String> values) {
            addCriterion("man_charge_per not in", values, "manChargePer");
            return (Criteria) this;
        }

        public Criteria andManChargePerBetween(String value1, String value2) {
            addCriterion("man_charge_per between", value1, value2, "manChargePer");
            return (Criteria) this;
        }

        public Criteria andManChargePerNotBetween(String value1, String value2) {
            addCriterion("man_charge_per not between", value1, value2, "manChargePer");
            return (Criteria) this;
        }

        public Criteria andManChargePerEndIsNull() {
            addCriterion("man_charge_per_end is null");
            return (Criteria) this;
        }

        public Criteria andManChargePerEndIsNotNull() {
            addCriterion("man_charge_per_end is not null");
            return (Criteria) this;
        }

        public Criteria andManChargePerEndEqualTo(String value) {
            addCriterion("man_charge_per_end =", value, "manChargePerEnd");
            return (Criteria) this;
        }

        public Criteria andManChargePerEndNotEqualTo(String value) {
            addCriterion("man_charge_per_end <>", value, "manChargePerEnd");
            return (Criteria) this;
        }

        public Criteria andManChargePerEndGreaterThan(String value) {
            addCriterion("man_charge_per_end >", value, "manChargePerEnd");
            return (Criteria) this;
        }

        public Criteria andManChargePerEndGreaterThanOrEqualTo(String value) {
            addCriterion("man_charge_per_end >=", value, "manChargePerEnd");
            return (Criteria) this;
        }

        public Criteria andManChargePerEndLessThan(String value) {
            addCriterion("man_charge_per_end <", value, "manChargePerEnd");
            return (Criteria) this;
        }

        public Criteria andManChargePerEndLessThanOrEqualTo(String value) {
            addCriterion("man_charge_per_end <=", value, "manChargePerEnd");
            return (Criteria) this;
        }

        public Criteria andManChargePerEndLike(String value) {
            addCriterion("man_charge_per_end like", value, "manChargePerEnd");
            return (Criteria) this;
        }

        public Criteria andManChargePerEndNotLike(String value) {
            addCriterion("man_charge_per_end not like", value, "manChargePerEnd");
            return (Criteria) this;
        }

        public Criteria andManChargePerEndIn(List<String> values) {
            addCriterion("man_charge_per_end in", values, "manChargePerEnd");
            return (Criteria) this;
        }

        public Criteria andManChargePerEndNotIn(List<String> values) {
            addCriterion("man_charge_per_end not in", values, "manChargePerEnd");
            return (Criteria) this;
        }

        public Criteria andManChargePerEndBetween(String value1, String value2) {
            addCriterion("man_charge_per_end between", value1, value2, "manChargePerEnd");
            return (Criteria) this;
        }

        public Criteria andManChargePerEndNotBetween(String value1, String value2) {
            addCriterion("man_charge_per_end not between", value1, value2, "manChargePerEnd");
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

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Integer value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Integer value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Integer value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Integer value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Integer value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Integer> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Integer> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Integer value1, Integer value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Integer value1, Integer value2) {
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

        public Criteria andUpdateTimeEqualTo(Integer value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Integer value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Integer value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Integer value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Integer value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Integer> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Integer> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Integer value1, Integer value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Integer value1, Integer value2) {
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