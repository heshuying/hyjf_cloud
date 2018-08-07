package com.hyjf.am.trade.dao.model.auto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CouponConfigExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public CouponConfigExample() {
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

        public Criteria andCouponCodeIsNull() {
            addCriterion("coupon_code is null");
            return (Criteria) this;
        }

        public Criteria andCouponCodeIsNotNull() {
            addCriterion("coupon_code is not null");
            return (Criteria) this;
        }

        public Criteria andCouponCodeEqualTo(String value) {
            addCriterion("coupon_code =", value, "couponCode");
            return (Criteria) this;
        }

        public Criteria andCouponCodeNotEqualTo(String value) {
            addCriterion("coupon_code <>", value, "couponCode");
            return (Criteria) this;
        }

        public Criteria andCouponCodeGreaterThan(String value) {
            addCriterion("coupon_code >", value, "couponCode");
            return (Criteria) this;
        }

        public Criteria andCouponCodeGreaterThanOrEqualTo(String value) {
            addCriterion("coupon_code >=", value, "couponCode");
            return (Criteria) this;
        }

        public Criteria andCouponCodeLessThan(String value) {
            addCriterion("coupon_code <", value, "couponCode");
            return (Criteria) this;
        }

        public Criteria andCouponCodeLessThanOrEqualTo(String value) {
            addCriterion("coupon_code <=", value, "couponCode");
            return (Criteria) this;
        }

        public Criteria andCouponCodeLike(String value) {
            addCriterion("coupon_code like", value, "couponCode");
            return (Criteria) this;
        }

        public Criteria andCouponCodeNotLike(String value) {
            addCriterion("coupon_code not like", value, "couponCode");
            return (Criteria) this;
        }

        public Criteria andCouponCodeIn(List<String> values) {
            addCriterion("coupon_code in", values, "couponCode");
            return (Criteria) this;
        }

        public Criteria andCouponCodeNotIn(List<String> values) {
            addCriterion("coupon_code not in", values, "couponCode");
            return (Criteria) this;
        }

        public Criteria andCouponCodeBetween(String value1, String value2) {
            addCriterion("coupon_code between", value1, value2, "couponCode");
            return (Criteria) this;
        }

        public Criteria andCouponCodeNotBetween(String value1, String value2) {
            addCriterion("coupon_code not between", value1, value2, "couponCode");
            return (Criteria) this;
        }

        public Criteria andCouponNameIsNull() {
            addCriterion("coupon_name is null");
            return (Criteria) this;
        }

        public Criteria andCouponNameIsNotNull() {
            addCriterion("coupon_name is not null");
            return (Criteria) this;
        }

        public Criteria andCouponNameEqualTo(String value) {
            addCriterion("coupon_name =", value, "couponName");
            return (Criteria) this;
        }

        public Criteria andCouponNameNotEqualTo(String value) {
            addCriterion("coupon_name <>", value, "couponName");
            return (Criteria) this;
        }

        public Criteria andCouponNameGreaterThan(String value) {
            addCriterion("coupon_name >", value, "couponName");
            return (Criteria) this;
        }

        public Criteria andCouponNameGreaterThanOrEqualTo(String value) {
            addCriterion("coupon_name >=", value, "couponName");
            return (Criteria) this;
        }

        public Criteria andCouponNameLessThan(String value) {
            addCriterion("coupon_name <", value, "couponName");
            return (Criteria) this;
        }

        public Criteria andCouponNameLessThanOrEqualTo(String value) {
            addCriterion("coupon_name <=", value, "couponName");
            return (Criteria) this;
        }

        public Criteria andCouponNameLike(String value) {
            addCriterion("coupon_name like", value, "couponName");
            return (Criteria) this;
        }

        public Criteria andCouponNameNotLike(String value) {
            addCriterion("coupon_name not like", value, "couponName");
            return (Criteria) this;
        }

        public Criteria andCouponNameIn(List<String> values) {
            addCriterion("coupon_name in", values, "couponName");
            return (Criteria) this;
        }

        public Criteria andCouponNameNotIn(List<String> values) {
            addCriterion("coupon_name not in", values, "couponName");
            return (Criteria) this;
        }

        public Criteria andCouponNameBetween(String value1, String value2) {
            addCriterion("coupon_name between", value1, value2, "couponName");
            return (Criteria) this;
        }

        public Criteria andCouponNameNotBetween(String value1, String value2) {
            addCriterion("coupon_name not between", value1, value2, "couponName");
            return (Criteria) this;
        }

        public Criteria andCouponQuotaIsNull() {
            addCriterion("coupon_quota is null");
            return (Criteria) this;
        }

        public Criteria andCouponQuotaIsNotNull() {
            addCriterion("coupon_quota is not null");
            return (Criteria) this;
        }

        public Criteria andCouponQuotaEqualTo(BigDecimal value) {
            addCriterion("coupon_quota =", value, "couponQuota");
            return (Criteria) this;
        }

        public Criteria andCouponQuotaNotEqualTo(BigDecimal value) {
            addCriterion("coupon_quota <>", value, "couponQuota");
            return (Criteria) this;
        }

        public Criteria andCouponQuotaGreaterThan(BigDecimal value) {
            addCriterion("coupon_quota >", value, "couponQuota");
            return (Criteria) this;
        }

        public Criteria andCouponQuotaGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("coupon_quota >=", value, "couponQuota");
            return (Criteria) this;
        }

        public Criteria andCouponQuotaLessThan(BigDecimal value) {
            addCriterion("coupon_quota <", value, "couponQuota");
            return (Criteria) this;
        }

        public Criteria andCouponQuotaLessThanOrEqualTo(BigDecimal value) {
            addCriterion("coupon_quota <=", value, "couponQuota");
            return (Criteria) this;
        }

        public Criteria andCouponQuotaIn(List<BigDecimal> values) {
            addCriterion("coupon_quota in", values, "couponQuota");
            return (Criteria) this;
        }

        public Criteria andCouponQuotaNotIn(List<BigDecimal> values) {
            addCriterion("coupon_quota not in", values, "couponQuota");
            return (Criteria) this;
        }

        public Criteria andCouponQuotaBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("coupon_quota between", value1, value2, "couponQuota");
            return (Criteria) this;
        }

        public Criteria andCouponQuotaNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("coupon_quota not between", value1, value2, "couponQuota");
            return (Criteria) this;
        }

        public Criteria andCouponQuantityIsNull() {
            addCriterion("coupon_quantity is null");
            return (Criteria) this;
        }

        public Criteria andCouponQuantityIsNotNull() {
            addCriterion("coupon_quantity is not null");
            return (Criteria) this;
        }

        public Criteria andCouponQuantityEqualTo(Integer value) {
            addCriterion("coupon_quantity =", value, "couponQuantity");
            return (Criteria) this;
        }

        public Criteria andCouponQuantityNotEqualTo(Integer value) {
            addCriterion("coupon_quantity <>", value, "couponQuantity");
            return (Criteria) this;
        }

        public Criteria andCouponQuantityGreaterThan(Integer value) {
            addCriterion("coupon_quantity >", value, "couponQuantity");
            return (Criteria) this;
        }

        public Criteria andCouponQuantityGreaterThanOrEqualTo(Integer value) {
            addCriterion("coupon_quantity >=", value, "couponQuantity");
            return (Criteria) this;
        }

        public Criteria andCouponQuantityLessThan(Integer value) {
            addCriterion("coupon_quantity <", value, "couponQuantity");
            return (Criteria) this;
        }

        public Criteria andCouponQuantityLessThanOrEqualTo(Integer value) {
            addCriterion("coupon_quantity <=", value, "couponQuantity");
            return (Criteria) this;
        }

        public Criteria andCouponQuantityIn(List<Integer> values) {
            addCriterion("coupon_quantity in", values, "couponQuantity");
            return (Criteria) this;
        }

        public Criteria andCouponQuantityNotIn(List<Integer> values) {
            addCriterion("coupon_quantity not in", values, "couponQuantity");
            return (Criteria) this;
        }

        public Criteria andCouponQuantityBetween(Integer value1, Integer value2) {
            addCriterion("coupon_quantity between", value1, value2, "couponQuantity");
            return (Criteria) this;
        }

        public Criteria andCouponQuantityNotBetween(Integer value1, Integer value2) {
            addCriterion("coupon_quantity not between", value1, value2, "couponQuantity");
            return (Criteria) this;
        }

        public Criteria andCouponProfitTimeIsNull() {
            addCriterion("coupon_profit_time is null");
            return (Criteria) this;
        }

        public Criteria andCouponProfitTimeIsNotNull() {
            addCriterion("coupon_profit_time is not null");
            return (Criteria) this;
        }

        public Criteria andCouponProfitTimeEqualTo(Short value) {
            addCriterion("coupon_profit_time =", value, "couponProfitTime");
            return (Criteria) this;
        }

        public Criteria andCouponProfitTimeNotEqualTo(Short value) {
            addCriterion("coupon_profit_time <>", value, "couponProfitTime");
            return (Criteria) this;
        }

        public Criteria andCouponProfitTimeGreaterThan(Short value) {
            addCriterion("coupon_profit_time >", value, "couponProfitTime");
            return (Criteria) this;
        }

        public Criteria andCouponProfitTimeGreaterThanOrEqualTo(Short value) {
            addCriterion("coupon_profit_time >=", value, "couponProfitTime");
            return (Criteria) this;
        }

        public Criteria andCouponProfitTimeLessThan(Short value) {
            addCriterion("coupon_profit_time <", value, "couponProfitTime");
            return (Criteria) this;
        }

        public Criteria andCouponProfitTimeLessThanOrEqualTo(Short value) {
            addCriterion("coupon_profit_time <=", value, "couponProfitTime");
            return (Criteria) this;
        }

        public Criteria andCouponProfitTimeIn(List<Short> values) {
            addCriterion("coupon_profit_time in", values, "couponProfitTime");
            return (Criteria) this;
        }

        public Criteria andCouponProfitTimeNotIn(List<Short> values) {
            addCriterion("coupon_profit_time not in", values, "couponProfitTime");
            return (Criteria) this;
        }

        public Criteria andCouponProfitTimeBetween(Short value1, Short value2) {
            addCriterion("coupon_profit_time between", value1, value2, "couponProfitTime");
            return (Criteria) this;
        }

        public Criteria andCouponProfitTimeNotBetween(Short value1, Short value2) {
            addCriterion("coupon_profit_time not between", value1, value2, "couponProfitTime");
            return (Criteria) this;
        }

        public Criteria andExpirationTypeIsNull() {
            addCriterion("expiration_type is null");
            return (Criteria) this;
        }

        public Criteria andExpirationTypeIsNotNull() {
            addCriterion("expiration_type is not null");
            return (Criteria) this;
        }

        public Criteria andExpirationTypeEqualTo(Integer value) {
            addCriterion("expiration_type =", value, "expirationType");
            return (Criteria) this;
        }

        public Criteria andExpirationTypeNotEqualTo(Integer value) {
            addCriterion("expiration_type <>", value, "expirationType");
            return (Criteria) this;
        }

        public Criteria andExpirationTypeGreaterThan(Integer value) {
            addCriterion("expiration_type >", value, "expirationType");
            return (Criteria) this;
        }

        public Criteria andExpirationTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("expiration_type >=", value, "expirationType");
            return (Criteria) this;
        }

        public Criteria andExpirationTypeLessThan(Integer value) {
            addCriterion("expiration_type <", value, "expirationType");
            return (Criteria) this;
        }

        public Criteria andExpirationTypeLessThanOrEqualTo(Integer value) {
            addCriterion("expiration_type <=", value, "expirationType");
            return (Criteria) this;
        }

        public Criteria andExpirationTypeIn(List<Integer> values) {
            addCriterion("expiration_type in", values, "expirationType");
            return (Criteria) this;
        }

        public Criteria andExpirationTypeNotIn(List<Integer> values) {
            addCriterion("expiration_type not in", values, "expirationType");
            return (Criteria) this;
        }

        public Criteria andExpirationTypeBetween(Integer value1, Integer value2) {
            addCriterion("expiration_type between", value1, value2, "expirationType");
            return (Criteria) this;
        }

        public Criteria andExpirationTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("expiration_type not between", value1, value2, "expirationType");
            return (Criteria) this;
        }

        public Criteria andExpirationDateIsNull() {
            addCriterion("expiration_date is null");
            return (Criteria) this;
        }

        public Criteria andExpirationDateIsNotNull() {
            addCriterion("expiration_date is not null");
            return (Criteria) this;
        }

        public Criteria andExpirationDateEqualTo(Integer value) {
            addCriterion("expiration_date =", value, "expirationDate");
            return (Criteria) this;
        }

        public Criteria andExpirationDateNotEqualTo(Integer value) {
            addCriterion("expiration_date <>", value, "expirationDate");
            return (Criteria) this;
        }

        public Criteria andExpirationDateGreaterThan(Integer value) {
            addCriterion("expiration_date >", value, "expirationDate");
            return (Criteria) this;
        }

        public Criteria andExpirationDateGreaterThanOrEqualTo(Integer value) {
            addCriterion("expiration_date >=", value, "expirationDate");
            return (Criteria) this;
        }

        public Criteria andExpirationDateLessThan(Integer value) {
            addCriterion("expiration_date <", value, "expirationDate");
            return (Criteria) this;
        }

        public Criteria andExpirationDateLessThanOrEqualTo(Integer value) {
            addCriterion("expiration_date <=", value, "expirationDate");
            return (Criteria) this;
        }

        public Criteria andExpirationDateIn(List<Integer> values) {
            addCriterion("expiration_date in", values, "expirationDate");
            return (Criteria) this;
        }

        public Criteria andExpirationDateNotIn(List<Integer> values) {
            addCriterion("expiration_date not in", values, "expirationDate");
            return (Criteria) this;
        }

        public Criteria andExpirationDateBetween(Integer value1, Integer value2) {
            addCriterion("expiration_date between", value1, value2, "expirationDate");
            return (Criteria) this;
        }

        public Criteria andExpirationDateNotBetween(Integer value1, Integer value2) {
            addCriterion("expiration_date not between", value1, value2, "expirationDate");
            return (Criteria) this;
        }

        public Criteria andExpirationLengthIsNull() {
            addCriterion("expiration_length is null");
            return (Criteria) this;
        }

        public Criteria andExpirationLengthIsNotNull() {
            addCriterion("expiration_length is not null");
            return (Criteria) this;
        }

        public Criteria andExpirationLengthEqualTo(Integer value) {
            addCriterion("expiration_length =", value, "expirationLength");
            return (Criteria) this;
        }

        public Criteria andExpirationLengthNotEqualTo(Integer value) {
            addCriterion("expiration_length <>", value, "expirationLength");
            return (Criteria) this;
        }

        public Criteria andExpirationLengthGreaterThan(Integer value) {
            addCriterion("expiration_length >", value, "expirationLength");
            return (Criteria) this;
        }

        public Criteria andExpirationLengthGreaterThanOrEqualTo(Integer value) {
            addCriterion("expiration_length >=", value, "expirationLength");
            return (Criteria) this;
        }

        public Criteria andExpirationLengthLessThan(Integer value) {
            addCriterion("expiration_length <", value, "expirationLength");
            return (Criteria) this;
        }

        public Criteria andExpirationLengthLessThanOrEqualTo(Integer value) {
            addCriterion("expiration_length <=", value, "expirationLength");
            return (Criteria) this;
        }

        public Criteria andExpirationLengthIn(List<Integer> values) {
            addCriterion("expiration_length in", values, "expirationLength");
            return (Criteria) this;
        }

        public Criteria andExpirationLengthNotIn(List<Integer> values) {
            addCriterion("expiration_length not in", values, "expirationLength");
            return (Criteria) this;
        }

        public Criteria andExpirationLengthBetween(Integer value1, Integer value2) {
            addCriterion("expiration_length between", value1, value2, "expirationLength");
            return (Criteria) this;
        }

        public Criteria andExpirationLengthNotBetween(Integer value1, Integer value2) {
            addCriterion("expiration_length not between", value1, value2, "expirationLength");
            return (Criteria) this;
        }

        public Criteria andExpirationLengthDayIsNull() {
            addCriterion("expiration_length_day is null");
            return (Criteria) this;
        }

        public Criteria andExpirationLengthDayIsNotNull() {
            addCriterion("expiration_length_day is not null");
            return (Criteria) this;
        }

        public Criteria andExpirationLengthDayEqualTo(Short value) {
            addCriterion("expiration_length_day =", value, "expirationLengthDay");
            return (Criteria) this;
        }

        public Criteria andExpirationLengthDayNotEqualTo(Short value) {
            addCriterion("expiration_length_day <>", value, "expirationLengthDay");
            return (Criteria) this;
        }

        public Criteria andExpirationLengthDayGreaterThan(Short value) {
            addCriterion("expiration_length_day >", value, "expirationLengthDay");
            return (Criteria) this;
        }

        public Criteria andExpirationLengthDayGreaterThanOrEqualTo(Short value) {
            addCriterion("expiration_length_day >=", value, "expirationLengthDay");
            return (Criteria) this;
        }

        public Criteria andExpirationLengthDayLessThan(Short value) {
            addCriterion("expiration_length_day <", value, "expirationLengthDay");
            return (Criteria) this;
        }

        public Criteria andExpirationLengthDayLessThanOrEqualTo(Short value) {
            addCriterion("expiration_length_day <=", value, "expirationLengthDay");
            return (Criteria) this;
        }

        public Criteria andExpirationLengthDayIn(List<Short> values) {
            addCriterion("expiration_length_day in", values, "expirationLengthDay");
            return (Criteria) this;
        }

        public Criteria andExpirationLengthDayNotIn(List<Short> values) {
            addCriterion("expiration_length_day not in", values, "expirationLengthDay");
            return (Criteria) this;
        }

        public Criteria andExpirationLengthDayBetween(Short value1, Short value2) {
            addCriterion("expiration_length_day between", value1, value2, "expirationLengthDay");
            return (Criteria) this;
        }

        public Criteria andExpirationLengthDayNotBetween(Short value1, Short value2) {
            addCriterion("expiration_length_day not between", value1, value2, "expirationLengthDay");
            return (Criteria) this;
        }

        public Criteria andAddFlagIsNull() {
            addCriterion("add_flag is null");
            return (Criteria) this;
        }

        public Criteria andAddFlagIsNotNull() {
            addCriterion("add_flag is not null");
            return (Criteria) this;
        }

        public Criteria andAddFlagEqualTo(Integer value) {
            addCriterion("add_flag =", value, "addFlag");
            return (Criteria) this;
        }

        public Criteria andAddFlagNotEqualTo(Integer value) {
            addCriterion("add_flag <>", value, "addFlag");
            return (Criteria) this;
        }

        public Criteria andAddFlagGreaterThan(Integer value) {
            addCriterion("add_flag >", value, "addFlag");
            return (Criteria) this;
        }

        public Criteria andAddFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("add_flag >=", value, "addFlag");
            return (Criteria) this;
        }

        public Criteria andAddFlagLessThan(Integer value) {
            addCriterion("add_flag <", value, "addFlag");
            return (Criteria) this;
        }

        public Criteria andAddFlagLessThanOrEqualTo(Integer value) {
            addCriterion("add_flag <=", value, "addFlag");
            return (Criteria) this;
        }

        public Criteria andAddFlagIn(List<Integer> values) {
            addCriterion("add_flag in", values, "addFlag");
            return (Criteria) this;
        }

        public Criteria andAddFlagNotIn(List<Integer> values) {
            addCriterion("add_flag not in", values, "addFlag");
            return (Criteria) this;
        }

        public Criteria andAddFlagBetween(Integer value1, Integer value2) {
            addCriterion("add_flag between", value1, value2, "addFlag");
            return (Criteria) this;
        }

        public Criteria andAddFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("add_flag not between", value1, value2, "addFlag");
            return (Criteria) this;
        }

        public Criteria andCouponSystemIsNull() {
            addCriterion("coupon_system is null");
            return (Criteria) this;
        }

        public Criteria andCouponSystemIsNotNull() {
            addCriterion("coupon_system is not null");
            return (Criteria) this;
        }

        public Criteria andCouponSystemEqualTo(String value) {
            addCriterion("coupon_system =", value, "couponSystem");
            return (Criteria) this;
        }

        public Criteria andCouponSystemNotEqualTo(String value) {
            addCriterion("coupon_system <>", value, "couponSystem");
            return (Criteria) this;
        }

        public Criteria andCouponSystemGreaterThan(String value) {
            addCriterion("coupon_system >", value, "couponSystem");
            return (Criteria) this;
        }

        public Criteria andCouponSystemGreaterThanOrEqualTo(String value) {
            addCriterion("coupon_system >=", value, "couponSystem");
            return (Criteria) this;
        }

        public Criteria andCouponSystemLessThan(String value) {
            addCriterion("coupon_system <", value, "couponSystem");
            return (Criteria) this;
        }

        public Criteria andCouponSystemLessThanOrEqualTo(String value) {
            addCriterion("coupon_system <=", value, "couponSystem");
            return (Criteria) this;
        }

        public Criteria andCouponSystemLike(String value) {
            addCriterion("coupon_system like", value, "couponSystem");
            return (Criteria) this;
        }

        public Criteria andCouponSystemNotLike(String value) {
            addCriterion("coupon_system not like", value, "couponSystem");
            return (Criteria) this;
        }

        public Criteria andCouponSystemIn(List<String> values) {
            addCriterion("coupon_system in", values, "couponSystem");
            return (Criteria) this;
        }

        public Criteria andCouponSystemNotIn(List<String> values) {
            addCriterion("coupon_system not in", values, "couponSystem");
            return (Criteria) this;
        }

        public Criteria andCouponSystemBetween(String value1, String value2) {
            addCriterion("coupon_system between", value1, value2, "couponSystem");
            return (Criteria) this;
        }

        public Criteria andCouponSystemNotBetween(String value1, String value2) {
            addCriterion("coupon_system not between", value1, value2, "couponSystem");
            return (Criteria) this;
        }

        public Criteria andCouponTypeIsNull() {
            addCriterion("coupon_type is null");
            return (Criteria) this;
        }

        public Criteria andCouponTypeIsNotNull() {
            addCriterion("coupon_type is not null");
            return (Criteria) this;
        }

        public Criteria andCouponTypeEqualTo(Integer value) {
            addCriterion("coupon_type =", value, "couponType");
            return (Criteria) this;
        }

        public Criteria andCouponTypeNotEqualTo(Integer value) {
            addCriterion("coupon_type <>", value, "couponType");
            return (Criteria) this;
        }

        public Criteria andCouponTypeGreaterThan(Integer value) {
            addCriterion("coupon_type >", value, "couponType");
            return (Criteria) this;
        }

        public Criteria andCouponTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("coupon_type >=", value, "couponType");
            return (Criteria) this;
        }

        public Criteria andCouponTypeLessThan(Integer value) {
            addCriterion("coupon_type <", value, "couponType");
            return (Criteria) this;
        }

        public Criteria andCouponTypeLessThanOrEqualTo(Integer value) {
            addCriterion("coupon_type <=", value, "couponType");
            return (Criteria) this;
        }

        public Criteria andCouponTypeIn(List<Integer> values) {
            addCriterion("coupon_type in", values, "couponType");
            return (Criteria) this;
        }

        public Criteria andCouponTypeNotIn(List<Integer> values) {
            addCriterion("coupon_type not in", values, "couponType");
            return (Criteria) this;
        }

        public Criteria andCouponTypeBetween(Integer value1, Integer value2) {
            addCriterion("coupon_type between", value1, value2, "couponType");
            return (Criteria) this;
        }

        public Criteria andCouponTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("coupon_type not between", value1, value2, "couponType");
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

        public Criteria andProjectTypeEqualTo(String value) {
            addCriterion("project_type =", value, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeNotEqualTo(String value) {
            addCriterion("project_type <>", value, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeGreaterThan(String value) {
            addCriterion("project_type >", value, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeGreaterThanOrEqualTo(String value) {
            addCriterion("project_type >=", value, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeLessThan(String value) {
            addCriterion("project_type <", value, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeLessThanOrEqualTo(String value) {
            addCriterion("project_type <=", value, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeLike(String value) {
            addCriterion("project_type like", value, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeNotLike(String value) {
            addCriterion("project_type not like", value, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeIn(List<String> values) {
            addCriterion("project_type in", values, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeNotIn(List<String> values) {
            addCriterion("project_type not in", values, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeBetween(String value1, String value2) {
            addCriterion("project_type between", value1, value2, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeNotBetween(String value1, String value2) {
            addCriterion("project_type not between", value1, value2, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectExpirationTypeIsNull() {
            addCriterion("project_expiration_type is null");
            return (Criteria) this;
        }

        public Criteria andProjectExpirationTypeIsNotNull() {
            addCriterion("project_expiration_type is not null");
            return (Criteria) this;
        }

        public Criteria andProjectExpirationTypeEqualTo(Integer value) {
            addCriterion("project_expiration_type =", value, "projectExpirationType");
            return (Criteria) this;
        }

        public Criteria andProjectExpirationTypeNotEqualTo(Integer value) {
            addCriterion("project_expiration_type <>", value, "projectExpirationType");
            return (Criteria) this;
        }

        public Criteria andProjectExpirationTypeGreaterThan(Integer value) {
            addCriterion("project_expiration_type >", value, "projectExpirationType");
            return (Criteria) this;
        }

        public Criteria andProjectExpirationTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("project_expiration_type >=", value, "projectExpirationType");
            return (Criteria) this;
        }

        public Criteria andProjectExpirationTypeLessThan(Integer value) {
            addCriterion("project_expiration_type <", value, "projectExpirationType");
            return (Criteria) this;
        }

        public Criteria andProjectExpirationTypeLessThanOrEqualTo(Integer value) {
            addCriterion("project_expiration_type <=", value, "projectExpirationType");
            return (Criteria) this;
        }

        public Criteria andProjectExpirationTypeIn(List<Integer> values) {
            addCriterion("project_expiration_type in", values, "projectExpirationType");
            return (Criteria) this;
        }

        public Criteria andProjectExpirationTypeNotIn(List<Integer> values) {
            addCriterion("project_expiration_type not in", values, "projectExpirationType");
            return (Criteria) this;
        }

        public Criteria andProjectExpirationTypeBetween(Integer value1, Integer value2) {
            addCriterion("project_expiration_type between", value1, value2, "projectExpirationType");
            return (Criteria) this;
        }

        public Criteria andProjectExpirationTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("project_expiration_type not between", value1, value2, "projectExpirationType");
            return (Criteria) this;
        }

        public Criteria andProjectExpirationLengthIsNull() {
            addCriterion("project_expiration_length is null");
            return (Criteria) this;
        }

        public Criteria andProjectExpirationLengthIsNotNull() {
            addCriterion("project_expiration_length is not null");
            return (Criteria) this;
        }

        public Criteria andProjectExpirationLengthEqualTo(Integer value) {
            addCriterion("project_expiration_length =", value, "projectExpirationLength");
            return (Criteria) this;
        }

        public Criteria andProjectExpirationLengthNotEqualTo(Integer value) {
            addCriterion("project_expiration_length <>", value, "projectExpirationLength");
            return (Criteria) this;
        }

        public Criteria andProjectExpirationLengthGreaterThan(Integer value) {
            addCriterion("project_expiration_length >", value, "projectExpirationLength");
            return (Criteria) this;
        }

        public Criteria andProjectExpirationLengthGreaterThanOrEqualTo(Integer value) {
            addCriterion("project_expiration_length >=", value, "projectExpirationLength");
            return (Criteria) this;
        }

        public Criteria andProjectExpirationLengthLessThan(Integer value) {
            addCriterion("project_expiration_length <", value, "projectExpirationLength");
            return (Criteria) this;
        }

        public Criteria andProjectExpirationLengthLessThanOrEqualTo(Integer value) {
            addCriterion("project_expiration_length <=", value, "projectExpirationLength");
            return (Criteria) this;
        }

        public Criteria andProjectExpirationLengthIn(List<Integer> values) {
            addCriterion("project_expiration_length in", values, "projectExpirationLength");
            return (Criteria) this;
        }

        public Criteria andProjectExpirationLengthNotIn(List<Integer> values) {
            addCriterion("project_expiration_length not in", values, "projectExpirationLength");
            return (Criteria) this;
        }

        public Criteria andProjectExpirationLengthBetween(Integer value1, Integer value2) {
            addCriterion("project_expiration_length between", value1, value2, "projectExpirationLength");
            return (Criteria) this;
        }

        public Criteria andProjectExpirationLengthNotBetween(Integer value1, Integer value2) {
            addCriterion("project_expiration_length not between", value1, value2, "projectExpirationLength");
            return (Criteria) this;
        }

        public Criteria andProjectExpirationLengthMinIsNull() {
            addCriterion("project_expiration_length_min is null");
            return (Criteria) this;
        }

        public Criteria andProjectExpirationLengthMinIsNotNull() {
            addCriterion("project_expiration_length_min is not null");
            return (Criteria) this;
        }

        public Criteria andProjectExpirationLengthMinEqualTo(Integer value) {
            addCriterion("project_expiration_length_min =", value, "projectExpirationLengthMin");
            return (Criteria) this;
        }

        public Criteria andProjectExpirationLengthMinNotEqualTo(Integer value) {
            addCriterion("project_expiration_length_min <>", value, "projectExpirationLengthMin");
            return (Criteria) this;
        }

        public Criteria andProjectExpirationLengthMinGreaterThan(Integer value) {
            addCriterion("project_expiration_length_min >", value, "projectExpirationLengthMin");
            return (Criteria) this;
        }

        public Criteria andProjectExpirationLengthMinGreaterThanOrEqualTo(Integer value) {
            addCriterion("project_expiration_length_min >=", value, "projectExpirationLengthMin");
            return (Criteria) this;
        }

        public Criteria andProjectExpirationLengthMinLessThan(Integer value) {
            addCriterion("project_expiration_length_min <", value, "projectExpirationLengthMin");
            return (Criteria) this;
        }

        public Criteria andProjectExpirationLengthMinLessThanOrEqualTo(Integer value) {
            addCriterion("project_expiration_length_min <=", value, "projectExpirationLengthMin");
            return (Criteria) this;
        }

        public Criteria andProjectExpirationLengthMinIn(List<Integer> values) {
            addCriterion("project_expiration_length_min in", values, "projectExpirationLengthMin");
            return (Criteria) this;
        }

        public Criteria andProjectExpirationLengthMinNotIn(List<Integer> values) {
            addCriterion("project_expiration_length_min not in", values, "projectExpirationLengthMin");
            return (Criteria) this;
        }

        public Criteria andProjectExpirationLengthMinBetween(Integer value1, Integer value2) {
            addCriterion("project_expiration_length_min between", value1, value2, "projectExpirationLengthMin");
            return (Criteria) this;
        }

        public Criteria andProjectExpirationLengthMinNotBetween(Integer value1, Integer value2) {
            addCriterion("project_expiration_length_min not between", value1, value2, "projectExpirationLengthMin");
            return (Criteria) this;
        }

        public Criteria andProjectExpirationLengthMaxIsNull() {
            addCriterion("project_expiration_length_max is null");
            return (Criteria) this;
        }

        public Criteria andProjectExpirationLengthMaxIsNotNull() {
            addCriterion("project_expiration_length_max is not null");
            return (Criteria) this;
        }

        public Criteria andProjectExpirationLengthMaxEqualTo(Integer value) {
            addCriterion("project_expiration_length_max =", value, "projectExpirationLengthMax");
            return (Criteria) this;
        }

        public Criteria andProjectExpirationLengthMaxNotEqualTo(Integer value) {
            addCriterion("project_expiration_length_max <>", value, "projectExpirationLengthMax");
            return (Criteria) this;
        }

        public Criteria andProjectExpirationLengthMaxGreaterThan(Integer value) {
            addCriterion("project_expiration_length_max >", value, "projectExpirationLengthMax");
            return (Criteria) this;
        }

        public Criteria andProjectExpirationLengthMaxGreaterThanOrEqualTo(Integer value) {
            addCriterion("project_expiration_length_max >=", value, "projectExpirationLengthMax");
            return (Criteria) this;
        }

        public Criteria andProjectExpirationLengthMaxLessThan(Integer value) {
            addCriterion("project_expiration_length_max <", value, "projectExpirationLengthMax");
            return (Criteria) this;
        }

        public Criteria andProjectExpirationLengthMaxLessThanOrEqualTo(Integer value) {
            addCriterion("project_expiration_length_max <=", value, "projectExpirationLengthMax");
            return (Criteria) this;
        }

        public Criteria andProjectExpirationLengthMaxIn(List<Integer> values) {
            addCriterion("project_expiration_length_max in", values, "projectExpirationLengthMax");
            return (Criteria) this;
        }

        public Criteria andProjectExpirationLengthMaxNotIn(List<Integer> values) {
            addCriterion("project_expiration_length_max not in", values, "projectExpirationLengthMax");
            return (Criteria) this;
        }

        public Criteria andProjectExpirationLengthMaxBetween(Integer value1, Integer value2) {
            addCriterion("project_expiration_length_max between", value1, value2, "projectExpirationLengthMax");
            return (Criteria) this;
        }

        public Criteria andProjectExpirationLengthMaxNotBetween(Integer value1, Integer value2) {
            addCriterion("project_expiration_length_max not between", value1, value2, "projectExpirationLengthMax");
            return (Criteria) this;
        }

        public Criteria andTenderQuotaTypeIsNull() {
            addCriterion("tender_quota_type is null");
            return (Criteria) this;
        }

        public Criteria andTenderQuotaTypeIsNotNull() {
            addCriterion("tender_quota_type is not null");
            return (Criteria) this;
        }

        public Criteria andTenderQuotaTypeEqualTo(Integer value) {
            addCriterion("tender_quota_type =", value, "tenderQuotaType");
            return (Criteria) this;
        }

        public Criteria andTenderQuotaTypeNotEqualTo(Integer value) {
            addCriterion("tender_quota_type <>", value, "tenderQuotaType");
            return (Criteria) this;
        }

        public Criteria andTenderQuotaTypeGreaterThan(Integer value) {
            addCriterion("tender_quota_type >", value, "tenderQuotaType");
            return (Criteria) this;
        }

        public Criteria andTenderQuotaTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("tender_quota_type >=", value, "tenderQuotaType");
            return (Criteria) this;
        }

        public Criteria andTenderQuotaTypeLessThan(Integer value) {
            addCriterion("tender_quota_type <", value, "tenderQuotaType");
            return (Criteria) this;
        }

        public Criteria andTenderQuotaTypeLessThanOrEqualTo(Integer value) {
            addCriterion("tender_quota_type <=", value, "tenderQuotaType");
            return (Criteria) this;
        }

        public Criteria andTenderQuotaTypeIn(List<Integer> values) {
            addCriterion("tender_quota_type in", values, "tenderQuotaType");
            return (Criteria) this;
        }

        public Criteria andTenderQuotaTypeNotIn(List<Integer> values) {
            addCriterion("tender_quota_type not in", values, "tenderQuotaType");
            return (Criteria) this;
        }

        public Criteria andTenderQuotaTypeBetween(Integer value1, Integer value2) {
            addCriterion("tender_quota_type between", value1, value2, "tenderQuotaType");
            return (Criteria) this;
        }

        public Criteria andTenderQuotaTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("tender_quota_type not between", value1, value2, "tenderQuotaType");
            return (Criteria) this;
        }

        public Criteria andTenderQuotaIsNull() {
            addCriterion("tender_quota is null");
            return (Criteria) this;
        }

        public Criteria andTenderQuotaIsNotNull() {
            addCriterion("tender_quota is not null");
            return (Criteria) this;
        }

        public Criteria andTenderQuotaEqualTo(Integer value) {
            addCriterion("tender_quota =", value, "tenderQuota");
            return (Criteria) this;
        }

        public Criteria andTenderQuotaNotEqualTo(Integer value) {
            addCriterion("tender_quota <>", value, "tenderQuota");
            return (Criteria) this;
        }

        public Criteria andTenderQuotaGreaterThan(Integer value) {
            addCriterion("tender_quota >", value, "tenderQuota");
            return (Criteria) this;
        }

        public Criteria andTenderQuotaGreaterThanOrEqualTo(Integer value) {
            addCriterion("tender_quota >=", value, "tenderQuota");
            return (Criteria) this;
        }

        public Criteria andTenderQuotaLessThan(Integer value) {
            addCriterion("tender_quota <", value, "tenderQuota");
            return (Criteria) this;
        }

        public Criteria andTenderQuotaLessThanOrEqualTo(Integer value) {
            addCriterion("tender_quota <=", value, "tenderQuota");
            return (Criteria) this;
        }

        public Criteria andTenderQuotaIn(List<Integer> values) {
            addCriterion("tender_quota in", values, "tenderQuota");
            return (Criteria) this;
        }

        public Criteria andTenderQuotaNotIn(List<Integer> values) {
            addCriterion("tender_quota not in", values, "tenderQuota");
            return (Criteria) this;
        }

        public Criteria andTenderQuotaBetween(Integer value1, Integer value2) {
            addCriterion("tender_quota between", value1, value2, "tenderQuota");
            return (Criteria) this;
        }

        public Criteria andTenderQuotaNotBetween(Integer value1, Integer value2) {
            addCriterion("tender_quota not between", value1, value2, "tenderQuota");
            return (Criteria) this;
        }

        public Criteria andTenderQuotaMinIsNull() {
            addCriterion("tender_quota_min is null");
            return (Criteria) this;
        }

        public Criteria andTenderQuotaMinIsNotNull() {
            addCriterion("tender_quota_min is not null");
            return (Criteria) this;
        }

        public Criteria andTenderQuotaMinEqualTo(Integer value) {
            addCriterion("tender_quota_min =", value, "tenderQuotaMin");
            return (Criteria) this;
        }

        public Criteria andTenderQuotaMinNotEqualTo(Integer value) {
            addCriterion("tender_quota_min <>", value, "tenderQuotaMin");
            return (Criteria) this;
        }

        public Criteria andTenderQuotaMinGreaterThan(Integer value) {
            addCriterion("tender_quota_min >", value, "tenderQuotaMin");
            return (Criteria) this;
        }

        public Criteria andTenderQuotaMinGreaterThanOrEqualTo(Integer value) {
            addCriterion("tender_quota_min >=", value, "tenderQuotaMin");
            return (Criteria) this;
        }

        public Criteria andTenderQuotaMinLessThan(Integer value) {
            addCriterion("tender_quota_min <", value, "tenderQuotaMin");
            return (Criteria) this;
        }

        public Criteria andTenderQuotaMinLessThanOrEqualTo(Integer value) {
            addCriterion("tender_quota_min <=", value, "tenderQuotaMin");
            return (Criteria) this;
        }

        public Criteria andTenderQuotaMinIn(List<Integer> values) {
            addCriterion("tender_quota_min in", values, "tenderQuotaMin");
            return (Criteria) this;
        }

        public Criteria andTenderQuotaMinNotIn(List<Integer> values) {
            addCriterion("tender_quota_min not in", values, "tenderQuotaMin");
            return (Criteria) this;
        }

        public Criteria andTenderQuotaMinBetween(Integer value1, Integer value2) {
            addCriterion("tender_quota_min between", value1, value2, "tenderQuotaMin");
            return (Criteria) this;
        }

        public Criteria andTenderQuotaMinNotBetween(Integer value1, Integer value2) {
            addCriterion("tender_quota_min not between", value1, value2, "tenderQuotaMin");
            return (Criteria) this;
        }

        public Criteria andTenderQuotaMaxIsNull() {
            addCriterion("tender_quota_max is null");
            return (Criteria) this;
        }

        public Criteria andTenderQuotaMaxIsNotNull() {
            addCriterion("tender_quota_max is not null");
            return (Criteria) this;
        }

        public Criteria andTenderQuotaMaxEqualTo(Integer value) {
            addCriterion("tender_quota_max =", value, "tenderQuotaMax");
            return (Criteria) this;
        }

        public Criteria andTenderQuotaMaxNotEqualTo(Integer value) {
            addCriterion("tender_quota_max <>", value, "tenderQuotaMax");
            return (Criteria) this;
        }

        public Criteria andTenderQuotaMaxGreaterThan(Integer value) {
            addCriterion("tender_quota_max >", value, "tenderQuotaMax");
            return (Criteria) this;
        }

        public Criteria andTenderQuotaMaxGreaterThanOrEqualTo(Integer value) {
            addCriterion("tender_quota_max >=", value, "tenderQuotaMax");
            return (Criteria) this;
        }

        public Criteria andTenderQuotaMaxLessThan(Integer value) {
            addCriterion("tender_quota_max <", value, "tenderQuotaMax");
            return (Criteria) this;
        }

        public Criteria andTenderQuotaMaxLessThanOrEqualTo(Integer value) {
            addCriterion("tender_quota_max <=", value, "tenderQuotaMax");
            return (Criteria) this;
        }

        public Criteria andTenderQuotaMaxIn(List<Integer> values) {
            addCriterion("tender_quota_max in", values, "tenderQuotaMax");
            return (Criteria) this;
        }

        public Criteria andTenderQuotaMaxNotIn(List<Integer> values) {
            addCriterion("tender_quota_max not in", values, "tenderQuotaMax");
            return (Criteria) this;
        }

        public Criteria andTenderQuotaMaxBetween(Integer value1, Integer value2) {
            addCriterion("tender_quota_max between", value1, value2, "tenderQuotaMax");
            return (Criteria) this;
        }

        public Criteria andTenderQuotaMaxNotBetween(Integer value1, Integer value2) {
            addCriterion("tender_quota_max not between", value1, value2, "tenderQuotaMax");
            return (Criteria) this;
        }

        public Criteria andContentIsNull() {
            addCriterion("content is null");
            return (Criteria) this;
        }

        public Criteria andContentIsNotNull() {
            addCriterion("content is not null");
            return (Criteria) this;
        }

        public Criteria andContentEqualTo(String value) {
            addCriterion("content =", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotEqualTo(String value) {
            addCriterion("content <>", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentGreaterThan(String value) {
            addCriterion("content >", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentGreaterThanOrEqualTo(String value) {
            addCriterion("content >=", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLessThan(String value) {
            addCriterion("content <", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLessThanOrEqualTo(String value) {
            addCriterion("content <=", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLike(String value) {
            addCriterion("content like", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotLike(String value) {
            addCriterion("content not like", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentIn(List<String> values) {
            addCriterion("content in", values, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotIn(List<String> values) {
            addCriterion("content not in", values, "content");
            return (Criteria) this;
        }

        public Criteria andContentBetween(String value1, String value2) {
            addCriterion("content between", value1, value2, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotBetween(String value1, String value2) {
            addCriterion("content not between", value1, value2, "content");
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

        public Criteria andAuditContentIsNull() {
            addCriterion("audit_content is null");
            return (Criteria) this;
        }

        public Criteria andAuditContentIsNotNull() {
            addCriterion("audit_content is not null");
            return (Criteria) this;
        }

        public Criteria andAuditContentEqualTo(String value) {
            addCriterion("audit_content =", value, "auditContent");
            return (Criteria) this;
        }

        public Criteria andAuditContentNotEqualTo(String value) {
            addCriterion("audit_content <>", value, "auditContent");
            return (Criteria) this;
        }

        public Criteria andAuditContentGreaterThan(String value) {
            addCriterion("audit_content >", value, "auditContent");
            return (Criteria) this;
        }

        public Criteria andAuditContentGreaterThanOrEqualTo(String value) {
            addCriterion("audit_content >=", value, "auditContent");
            return (Criteria) this;
        }

        public Criteria andAuditContentLessThan(String value) {
            addCriterion("audit_content <", value, "auditContent");
            return (Criteria) this;
        }

        public Criteria andAuditContentLessThanOrEqualTo(String value) {
            addCriterion("audit_content <=", value, "auditContent");
            return (Criteria) this;
        }

        public Criteria andAuditContentLike(String value) {
            addCriterion("audit_content like", value, "auditContent");
            return (Criteria) this;
        }

        public Criteria andAuditContentNotLike(String value) {
            addCriterion("audit_content not like", value, "auditContent");
            return (Criteria) this;
        }

        public Criteria andAuditContentIn(List<String> values) {
            addCriterion("audit_content in", values, "auditContent");
            return (Criteria) this;
        }

        public Criteria andAuditContentNotIn(List<String> values) {
            addCriterion("audit_content not in", values, "auditContent");
            return (Criteria) this;
        }

        public Criteria andAuditContentBetween(String value1, String value2) {
            addCriterion("audit_content between", value1, value2, "auditContent");
            return (Criteria) this;
        }

        public Criteria andAuditContentNotBetween(String value1, String value2) {
            addCriterion("audit_content not between", value1, value2, "auditContent");
            return (Criteria) this;
        }

        public Criteria andAuditUserIsNull() {
            addCriterion("audit_user is null");
            return (Criteria) this;
        }

        public Criteria andAuditUserIsNotNull() {
            addCriterion("audit_user is not null");
            return (Criteria) this;
        }

        public Criteria andAuditUserEqualTo(String value) {
            addCriterion("audit_user =", value, "auditUser");
            return (Criteria) this;
        }

        public Criteria andAuditUserNotEqualTo(String value) {
            addCriterion("audit_user <>", value, "auditUser");
            return (Criteria) this;
        }

        public Criteria andAuditUserGreaterThan(String value) {
            addCriterion("audit_user >", value, "auditUser");
            return (Criteria) this;
        }

        public Criteria andAuditUserGreaterThanOrEqualTo(String value) {
            addCriterion("audit_user >=", value, "auditUser");
            return (Criteria) this;
        }

        public Criteria andAuditUserLessThan(String value) {
            addCriterion("audit_user <", value, "auditUser");
            return (Criteria) this;
        }

        public Criteria andAuditUserLessThanOrEqualTo(String value) {
            addCriterion("audit_user <=", value, "auditUser");
            return (Criteria) this;
        }

        public Criteria andAuditUserLike(String value) {
            addCriterion("audit_user like", value, "auditUser");
            return (Criteria) this;
        }

        public Criteria andAuditUserNotLike(String value) {
            addCriterion("audit_user not like", value, "auditUser");
            return (Criteria) this;
        }

        public Criteria andAuditUserIn(List<String> values) {
            addCriterion("audit_user in", values, "auditUser");
            return (Criteria) this;
        }

        public Criteria andAuditUserNotIn(List<String> values) {
            addCriterion("audit_user not in", values, "auditUser");
            return (Criteria) this;
        }

        public Criteria andAuditUserBetween(String value1, String value2) {
            addCriterion("audit_user between", value1, value2, "auditUser");
            return (Criteria) this;
        }

        public Criteria andAuditUserNotBetween(String value1, String value2) {
            addCriterion("audit_user not between", value1, value2, "auditUser");
            return (Criteria) this;
        }

        public Criteria andAuditTimeIsNull() {
            addCriterion("audit_time is null");
            return (Criteria) this;
        }

        public Criteria andAuditTimeIsNotNull() {
            addCriterion("audit_time is not null");
            return (Criteria) this;
        }

        public Criteria andAuditTimeEqualTo(Integer value) {
            addCriterion("audit_time =", value, "auditTime");
            return (Criteria) this;
        }

        public Criteria andAuditTimeNotEqualTo(Integer value) {
            addCriterion("audit_time <>", value, "auditTime");
            return (Criteria) this;
        }

        public Criteria andAuditTimeGreaterThan(Integer value) {
            addCriterion("audit_time >", value, "auditTime");
            return (Criteria) this;
        }

        public Criteria andAuditTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("audit_time >=", value, "auditTime");
            return (Criteria) this;
        }

        public Criteria andAuditTimeLessThan(Integer value) {
            addCriterion("audit_time <", value, "auditTime");
            return (Criteria) this;
        }

        public Criteria andAuditTimeLessThanOrEqualTo(Integer value) {
            addCriterion("audit_time <=", value, "auditTime");
            return (Criteria) this;
        }

        public Criteria andAuditTimeIn(List<Integer> values) {
            addCriterion("audit_time in", values, "auditTime");
            return (Criteria) this;
        }

        public Criteria andAuditTimeNotIn(List<Integer> values) {
            addCriterion("audit_time not in", values, "auditTime");
            return (Criteria) this;
        }

        public Criteria andAuditTimeBetween(Integer value1, Integer value2) {
            addCriterion("audit_time between", value1, value2, "auditTime");
            return (Criteria) this;
        }

        public Criteria andAuditTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("audit_time not between", value1, value2, "auditTime");
            return (Criteria) this;
        }

        public Criteria andRepayTimeConfigIsNull() {
            addCriterion("repay_time_config is null");
            return (Criteria) this;
        }

        public Criteria andRepayTimeConfigIsNotNull() {
            addCriterion("repay_time_config is not null");
            return (Criteria) this;
        }

        public Criteria andRepayTimeConfigEqualTo(Integer value) {
            addCriterion("repay_time_config =", value, "repayTimeConfig");
            return (Criteria) this;
        }

        public Criteria andRepayTimeConfigNotEqualTo(Integer value) {
            addCriterion("repay_time_config <>", value, "repayTimeConfig");
            return (Criteria) this;
        }

        public Criteria andRepayTimeConfigGreaterThan(Integer value) {
            addCriterion("repay_time_config >", value, "repayTimeConfig");
            return (Criteria) this;
        }

        public Criteria andRepayTimeConfigGreaterThanOrEqualTo(Integer value) {
            addCriterion("repay_time_config >=", value, "repayTimeConfig");
            return (Criteria) this;
        }

        public Criteria andRepayTimeConfigLessThan(Integer value) {
            addCriterion("repay_time_config <", value, "repayTimeConfig");
            return (Criteria) this;
        }

        public Criteria andRepayTimeConfigLessThanOrEqualTo(Integer value) {
            addCriterion("repay_time_config <=", value, "repayTimeConfig");
            return (Criteria) this;
        }

        public Criteria andRepayTimeConfigIn(List<Integer> values) {
            addCriterion("repay_time_config in", values, "repayTimeConfig");
            return (Criteria) this;
        }

        public Criteria andRepayTimeConfigNotIn(List<Integer> values) {
            addCriterion("repay_time_config not in", values, "repayTimeConfig");
            return (Criteria) this;
        }

        public Criteria andRepayTimeConfigBetween(Integer value1, Integer value2) {
            addCriterion("repay_time_config between", value1, value2, "repayTimeConfig");
            return (Criteria) this;
        }

        public Criteria andRepayTimeConfigNotBetween(Integer value1, Integer value2) {
            addCriterion("repay_time_config not between", value1, value2, "repayTimeConfig");
            return (Criteria) this;
        }

        public Criteria andDelFlagIsNull() {
            addCriterion("del_flag is null");
            return (Criteria) this;
        }

        public Criteria andDelFlagIsNotNull() {
            addCriterion("del_flag is not null");
            return (Criteria) this;
        }

        public Criteria andDelFlagEqualTo(Integer value) {
            addCriterion("del_flag =", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagNotEqualTo(Integer value) {
            addCriterion("del_flag <>", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagGreaterThan(Integer value) {
            addCriterion("del_flag >", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("del_flag >=", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagLessThan(Integer value) {
            addCriterion("del_flag <", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagLessThanOrEqualTo(Integer value) {
            addCriterion("del_flag <=", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagIn(List<Integer> values) {
            addCriterion("del_flag in", values, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagNotIn(List<Integer> values) {
            addCriterion("del_flag not in", values, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagBetween(Integer value1, Integer value2) {
            addCriterion("del_flag between", value1, value2, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("del_flag not between", value1, value2, "delFlag");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdIsNull() {
            addCriterion("create_user_id is null");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdIsNotNull() {
            addCriterion("create_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdEqualTo(Integer value) {
            addCriterion("create_user_id =", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdNotEqualTo(Integer value) {
            addCriterion("create_user_id <>", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdGreaterThan(Integer value) {
            addCriterion("create_user_id >", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("create_user_id >=", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdLessThan(Integer value) {
            addCriterion("create_user_id <", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("create_user_id <=", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdIn(List<Integer> values) {
            addCriterion("create_user_id in", values, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdNotIn(List<Integer> values) {
            addCriterion("create_user_id not in", values, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdBetween(Integer value1, Integer value2) {
            addCriterion("create_user_id between", value1, value2, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("create_user_id not between", value1, value2, "createUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdIsNull() {
            addCriterion("update_user_id is null");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdIsNotNull() {
            addCriterion("update_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdEqualTo(Integer value) {
            addCriterion("update_user_id =", value, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdNotEqualTo(Integer value) {
            addCriterion("update_user_id <>", value, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdGreaterThan(Integer value) {
            addCriterion("update_user_id >", value, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("update_user_id >=", value, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdLessThan(Integer value) {
            addCriterion("update_user_id <", value, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("update_user_id <=", value, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdIn(List<Integer> values) {
            addCriterion("update_user_id in", values, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdNotIn(List<Integer> values) {
            addCriterion("update_user_id not in", values, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdBetween(Integer value1, Integer value2) {
            addCriterion("update_user_id between", value1, value2, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("update_user_id not between", value1, value2, "updateUserId");
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