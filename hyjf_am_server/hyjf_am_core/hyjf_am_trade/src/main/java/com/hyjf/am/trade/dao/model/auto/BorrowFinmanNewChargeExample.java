package com.hyjf.am.trade.dao.model.auto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BorrowFinmanNewChargeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public BorrowFinmanNewChargeExample() {
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

        public Criteria andManChargeTimeIsNull() {
            addCriterion("man_charge_time is null");
            return (Criteria) this;
        }

        public Criteria andManChargeTimeIsNotNull() {
            addCriterion("man_charge_time is not null");
            return (Criteria) this;
        }

        public Criteria andManChargeTimeEqualTo(Integer value) {
            addCriterion("man_charge_time =", value, "manChargeTime");
            return (Criteria) this;
        }

        public Criteria andManChargeTimeNotEqualTo(Integer value) {
            addCriterion("man_charge_time <>", value, "manChargeTime");
            return (Criteria) this;
        }

        public Criteria andManChargeTimeGreaterThan(Integer value) {
            addCriterion("man_charge_time >", value, "manChargeTime");
            return (Criteria) this;
        }

        public Criteria andManChargeTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("man_charge_time >=", value, "manChargeTime");
            return (Criteria) this;
        }

        public Criteria andManChargeTimeLessThan(Integer value) {
            addCriterion("man_charge_time <", value, "manChargeTime");
            return (Criteria) this;
        }

        public Criteria andManChargeTimeLessThanOrEqualTo(Integer value) {
            addCriterion("man_charge_time <=", value, "manChargeTime");
            return (Criteria) this;
        }

        public Criteria andManChargeTimeIn(List<Integer> values) {
            addCriterion("man_charge_time in", values, "manChargeTime");
            return (Criteria) this;
        }

        public Criteria andManChargeTimeNotIn(List<Integer> values) {
            addCriterion("man_charge_time not in", values, "manChargeTime");
            return (Criteria) this;
        }

        public Criteria andManChargeTimeBetween(Integer value1, Integer value2) {
            addCriterion("man_charge_time between", value1, value2, "manChargeTime");
            return (Criteria) this;
        }

        public Criteria andManChargeTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("man_charge_time not between", value1, value2, "manChargeTime");
            return (Criteria) this;
        }

        public Criteria andManChargeTimeTypeIsNull() {
            addCriterion("man_charge_time_type is null");
            return (Criteria) this;
        }

        public Criteria andManChargeTimeTypeIsNotNull() {
            addCriterion("man_charge_time_type is not null");
            return (Criteria) this;
        }

        public Criteria andManChargeTimeTypeEqualTo(String value) {
            addCriterion("man_charge_time_type =", value, "manChargeTimeType");
            return (Criteria) this;
        }

        public Criteria andManChargeTimeTypeNotEqualTo(String value) {
            addCriterion("man_charge_time_type <>", value, "manChargeTimeType");
            return (Criteria) this;
        }

        public Criteria andManChargeTimeTypeGreaterThan(String value) {
            addCriterion("man_charge_time_type >", value, "manChargeTimeType");
            return (Criteria) this;
        }

        public Criteria andManChargeTimeTypeGreaterThanOrEqualTo(String value) {
            addCriterion("man_charge_time_type >=", value, "manChargeTimeType");
            return (Criteria) this;
        }

        public Criteria andManChargeTimeTypeLessThan(String value) {
            addCriterion("man_charge_time_type <", value, "manChargeTimeType");
            return (Criteria) this;
        }

        public Criteria andManChargeTimeTypeLessThanOrEqualTo(String value) {
            addCriterion("man_charge_time_type <=", value, "manChargeTimeType");
            return (Criteria) this;
        }

        public Criteria andManChargeTimeTypeLike(String value) {
            addCriterion("man_charge_time_type like", value, "manChargeTimeType");
            return (Criteria) this;
        }

        public Criteria andManChargeTimeTypeNotLike(String value) {
            addCriterion("man_charge_time_type not like", value, "manChargeTimeType");
            return (Criteria) this;
        }

        public Criteria andManChargeTimeTypeIn(List<String> values) {
            addCriterion("man_charge_time_type in", values, "manChargeTimeType");
            return (Criteria) this;
        }

        public Criteria andManChargeTimeTypeNotIn(List<String> values) {
            addCriterion("man_charge_time_type not in", values, "manChargeTimeType");
            return (Criteria) this;
        }

        public Criteria andManChargeTimeTypeBetween(String value1, String value2) {
            addCriterion("man_charge_time_type between", value1, value2, "manChargeTimeType");
            return (Criteria) this;
        }

        public Criteria andManChargeTimeTypeNotBetween(String value1, String value2) {
            addCriterion("man_charge_time_type not between", value1, value2, "manChargeTimeType");
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

        public Criteria andInstCodeIsNull() {
            addCriterion("inst_code is null");
            return (Criteria) this;
        }

        public Criteria andInstCodeIsNotNull() {
            addCriterion("inst_code is not null");
            return (Criteria) this;
        }

        public Criteria andInstCodeEqualTo(String value) {
            addCriterion("inst_code =", value, "instCode");
            return (Criteria) this;
        }

        public Criteria andInstCodeNotEqualTo(String value) {
            addCriterion("inst_code <>", value, "instCode");
            return (Criteria) this;
        }

        public Criteria andInstCodeGreaterThan(String value) {
            addCriterion("inst_code >", value, "instCode");
            return (Criteria) this;
        }

        public Criteria andInstCodeGreaterThanOrEqualTo(String value) {
            addCriterion("inst_code >=", value, "instCode");
            return (Criteria) this;
        }

        public Criteria andInstCodeLessThan(String value) {
            addCriterion("inst_code <", value, "instCode");
            return (Criteria) this;
        }

        public Criteria andInstCodeLessThanOrEqualTo(String value) {
            addCriterion("inst_code <=", value, "instCode");
            return (Criteria) this;
        }

        public Criteria andInstCodeLike(String value) {
            addCriterion("inst_code like", value, "instCode");
            return (Criteria) this;
        }

        public Criteria andInstCodeNotLike(String value) {
            addCriterion("inst_code not like", value, "instCode");
            return (Criteria) this;
        }

        public Criteria andInstCodeIn(List<String> values) {
            addCriterion("inst_code in", values, "instCode");
            return (Criteria) this;
        }

        public Criteria andInstCodeNotIn(List<String> values) {
            addCriterion("inst_code not in", values, "instCode");
            return (Criteria) this;
        }

        public Criteria andInstCodeBetween(String value1, String value2) {
            addCriterion("inst_code between", value1, value2, "instCode");
            return (Criteria) this;
        }

        public Criteria andInstCodeNotBetween(String value1, String value2) {
            addCriterion("inst_code not between", value1, value2, "instCode");
            return (Criteria) this;
        }

        public Criteria andAssetTypeIsNull() {
            addCriterion("asset_type is null");
            return (Criteria) this;
        }

        public Criteria andAssetTypeIsNotNull() {
            addCriterion("asset_type is not null");
            return (Criteria) this;
        }

        public Criteria andAssetTypeEqualTo(Integer value) {
            addCriterion("asset_type =", value, "assetType");
            return (Criteria) this;
        }

        public Criteria andAssetTypeNotEqualTo(Integer value) {
            addCriterion("asset_type <>", value, "assetType");
            return (Criteria) this;
        }

        public Criteria andAssetTypeGreaterThan(Integer value) {
            addCriterion("asset_type >", value, "assetType");
            return (Criteria) this;
        }

        public Criteria andAssetTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("asset_type >=", value, "assetType");
            return (Criteria) this;
        }

        public Criteria andAssetTypeLessThan(Integer value) {
            addCriterion("asset_type <", value, "assetType");
            return (Criteria) this;
        }

        public Criteria andAssetTypeLessThanOrEqualTo(Integer value) {
            addCriterion("asset_type <=", value, "assetType");
            return (Criteria) this;
        }

        public Criteria andAssetTypeIn(List<Integer> values) {
            addCriterion("asset_type in", values, "assetType");
            return (Criteria) this;
        }

        public Criteria andAssetTypeNotIn(List<Integer> values) {
            addCriterion("asset_type not in", values, "assetType");
            return (Criteria) this;
        }

        public Criteria andAssetTypeBetween(Integer value1, Integer value2) {
            addCriterion("asset_type between", value1, value2, "assetType");
            return (Criteria) this;
        }

        public Criteria andAssetTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("asset_type not between", value1, value2, "assetType");
            return (Criteria) this;
        }

        public Criteria andAutoBorrowAprIsNull() {
            addCriterion("auto_borrow_apr is null");
            return (Criteria) this;
        }

        public Criteria andAutoBorrowAprIsNotNull() {
            addCriterion("auto_borrow_apr is not null");
            return (Criteria) this;
        }

        public Criteria andAutoBorrowAprEqualTo(String value) {
            addCriterion("auto_borrow_apr =", value, "autoBorrowApr");
            return (Criteria) this;
        }

        public Criteria andAutoBorrowAprNotEqualTo(String value) {
            addCriterion("auto_borrow_apr <>", value, "autoBorrowApr");
            return (Criteria) this;
        }

        public Criteria andAutoBorrowAprGreaterThan(String value) {
            addCriterion("auto_borrow_apr >", value, "autoBorrowApr");
            return (Criteria) this;
        }

        public Criteria andAutoBorrowAprGreaterThanOrEqualTo(String value) {
            addCriterion("auto_borrow_apr >=", value, "autoBorrowApr");
            return (Criteria) this;
        }

        public Criteria andAutoBorrowAprLessThan(String value) {
            addCriterion("auto_borrow_apr <", value, "autoBorrowApr");
            return (Criteria) this;
        }

        public Criteria andAutoBorrowAprLessThanOrEqualTo(String value) {
            addCriterion("auto_borrow_apr <=", value, "autoBorrowApr");
            return (Criteria) this;
        }

        public Criteria andAutoBorrowAprLike(String value) {
            addCriterion("auto_borrow_apr like", value, "autoBorrowApr");
            return (Criteria) this;
        }

        public Criteria andAutoBorrowAprNotLike(String value) {
            addCriterion("auto_borrow_apr not like", value, "autoBorrowApr");
            return (Criteria) this;
        }

        public Criteria andAutoBorrowAprIn(List<String> values) {
            addCriterion("auto_borrow_apr in", values, "autoBorrowApr");
            return (Criteria) this;
        }

        public Criteria andAutoBorrowAprNotIn(List<String> values) {
            addCriterion("auto_borrow_apr not in", values, "autoBorrowApr");
            return (Criteria) this;
        }

        public Criteria andAutoBorrowAprBetween(String value1, String value2) {
            addCriterion("auto_borrow_apr between", value1, value2, "autoBorrowApr");
            return (Criteria) this;
        }

        public Criteria andAutoBorrowAprNotBetween(String value1, String value2) {
            addCriterion("auto_borrow_apr not between", value1, value2, "autoBorrowApr");
            return (Criteria) this;
        }

        public Criteria andChargeModeIsNull() {
            addCriterion("charge_mode is null");
            return (Criteria) this;
        }

        public Criteria andChargeModeIsNotNull() {
            addCriterion("charge_mode is not null");
            return (Criteria) this;
        }

        public Criteria andChargeModeEqualTo(Integer value) {
            addCriterion("charge_mode =", value, "chargeMode");
            return (Criteria) this;
        }

        public Criteria andChargeModeNotEqualTo(Integer value) {
            addCriterion("charge_mode <>", value, "chargeMode");
            return (Criteria) this;
        }

        public Criteria andChargeModeGreaterThan(Integer value) {
            addCriterion("charge_mode >", value, "chargeMode");
            return (Criteria) this;
        }

        public Criteria andChargeModeGreaterThanOrEqualTo(Integer value) {
            addCriterion("charge_mode >=", value, "chargeMode");
            return (Criteria) this;
        }

        public Criteria andChargeModeLessThan(Integer value) {
            addCriterion("charge_mode <", value, "chargeMode");
            return (Criteria) this;
        }

        public Criteria andChargeModeLessThanOrEqualTo(Integer value) {
            addCriterion("charge_mode <=", value, "chargeMode");
            return (Criteria) this;
        }

        public Criteria andChargeModeIn(List<Integer> values) {
            addCriterion("charge_mode in", values, "chargeMode");
            return (Criteria) this;
        }

        public Criteria andChargeModeNotIn(List<Integer> values) {
            addCriterion("charge_mode not in", values, "chargeMode");
            return (Criteria) this;
        }

        public Criteria andChargeModeBetween(Integer value1, Integer value2) {
            addCriterion("charge_mode between", value1, value2, "chargeMode");
            return (Criteria) this;
        }

        public Criteria andChargeModeNotBetween(Integer value1, Integer value2) {
            addCriterion("charge_mode not between", value1, value2, "chargeMode");
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

        public Criteria andManChargeRateIsNull() {
            addCriterion("man_charge_rate is null");
            return (Criteria) this;
        }

        public Criteria andManChargeRateIsNotNull() {
            addCriterion("man_charge_rate is not null");
            return (Criteria) this;
        }

        public Criteria andManChargeRateEqualTo(String value) {
            addCriterion("man_charge_rate =", value, "manChargeRate");
            return (Criteria) this;
        }

        public Criteria andManChargeRateNotEqualTo(String value) {
            addCriterion("man_charge_rate <>", value, "manChargeRate");
            return (Criteria) this;
        }

        public Criteria andManChargeRateGreaterThan(String value) {
            addCriterion("man_charge_rate >", value, "manChargeRate");
            return (Criteria) this;
        }

        public Criteria andManChargeRateGreaterThanOrEqualTo(String value) {
            addCriterion("man_charge_rate >=", value, "manChargeRate");
            return (Criteria) this;
        }

        public Criteria andManChargeRateLessThan(String value) {
            addCriterion("man_charge_rate <", value, "manChargeRate");
            return (Criteria) this;
        }

        public Criteria andManChargeRateLessThanOrEqualTo(String value) {
            addCriterion("man_charge_rate <=", value, "manChargeRate");
            return (Criteria) this;
        }

        public Criteria andManChargeRateLike(String value) {
            addCriterion("man_charge_rate like", value, "manChargeRate");
            return (Criteria) this;
        }

        public Criteria andManChargeRateNotLike(String value) {
            addCriterion("man_charge_rate not like", value, "manChargeRate");
            return (Criteria) this;
        }

        public Criteria andManChargeRateIn(List<String> values) {
            addCriterion("man_charge_rate in", values, "manChargeRate");
            return (Criteria) this;
        }

        public Criteria andManChargeRateNotIn(List<String> values) {
            addCriterion("man_charge_rate not in", values, "manChargeRate");
            return (Criteria) this;
        }

        public Criteria andManChargeRateBetween(String value1, String value2) {
            addCriterion("man_charge_rate between", value1, value2, "manChargeRate");
            return (Criteria) this;
        }

        public Criteria andManChargeRateNotBetween(String value1, String value2) {
            addCriterion("man_charge_rate not between", value1, value2, "manChargeRate");
            return (Criteria) this;
        }

        public Criteria andReturnRateIsNull() {
            addCriterion("return_rate is null");
            return (Criteria) this;
        }

        public Criteria andReturnRateIsNotNull() {
            addCriterion("return_rate is not null");
            return (Criteria) this;
        }

        public Criteria andReturnRateEqualTo(String value) {
            addCriterion("return_rate =", value, "returnRate");
            return (Criteria) this;
        }

        public Criteria andReturnRateNotEqualTo(String value) {
            addCriterion("return_rate <>", value, "returnRate");
            return (Criteria) this;
        }

        public Criteria andReturnRateGreaterThan(String value) {
            addCriterion("return_rate >", value, "returnRate");
            return (Criteria) this;
        }

        public Criteria andReturnRateGreaterThanOrEqualTo(String value) {
            addCriterion("return_rate >=", value, "returnRate");
            return (Criteria) this;
        }

        public Criteria andReturnRateLessThan(String value) {
            addCriterion("return_rate <", value, "returnRate");
            return (Criteria) this;
        }

        public Criteria andReturnRateLessThanOrEqualTo(String value) {
            addCriterion("return_rate <=", value, "returnRate");
            return (Criteria) this;
        }

        public Criteria andReturnRateLike(String value) {
            addCriterion("return_rate like", value, "returnRate");
            return (Criteria) this;
        }

        public Criteria andReturnRateNotLike(String value) {
            addCriterion("return_rate not like", value, "returnRate");
            return (Criteria) this;
        }

        public Criteria andReturnRateIn(List<String> values) {
            addCriterion("return_rate in", values, "returnRate");
            return (Criteria) this;
        }

        public Criteria andReturnRateNotIn(List<String> values) {
            addCriterion("return_rate not in", values, "returnRate");
            return (Criteria) this;
        }

        public Criteria andReturnRateBetween(String value1, String value2) {
            addCriterion("return_rate between", value1, value2, "returnRate");
            return (Criteria) this;
        }

        public Criteria andReturnRateNotBetween(String value1, String value2) {
            addCriterion("return_rate not between", value1, value2, "returnRate");
            return (Criteria) this;
        }

        public Criteria andLateInterestIsNull() {
            addCriterion("late_interest is null");
            return (Criteria) this;
        }

        public Criteria andLateInterestIsNotNull() {
            addCriterion("late_interest is not null");
            return (Criteria) this;
        }

        public Criteria andLateInterestEqualTo(String value) {
            addCriterion("late_interest =", value, "lateInterest");
            return (Criteria) this;
        }

        public Criteria andLateInterestNotEqualTo(String value) {
            addCriterion("late_interest <>", value, "lateInterest");
            return (Criteria) this;
        }

        public Criteria andLateInterestGreaterThan(String value) {
            addCriterion("late_interest >", value, "lateInterest");
            return (Criteria) this;
        }

        public Criteria andLateInterestGreaterThanOrEqualTo(String value) {
            addCriterion("late_interest >=", value, "lateInterest");
            return (Criteria) this;
        }

        public Criteria andLateInterestLessThan(String value) {
            addCriterion("late_interest <", value, "lateInterest");
            return (Criteria) this;
        }

        public Criteria andLateInterestLessThanOrEqualTo(String value) {
            addCriterion("late_interest <=", value, "lateInterest");
            return (Criteria) this;
        }

        public Criteria andLateInterestLike(String value) {
            addCriterion("late_interest like", value, "lateInterest");
            return (Criteria) this;
        }

        public Criteria andLateInterestNotLike(String value) {
            addCriterion("late_interest not like", value, "lateInterest");
            return (Criteria) this;
        }

        public Criteria andLateInterestIn(List<String> values) {
            addCriterion("late_interest in", values, "lateInterest");
            return (Criteria) this;
        }

        public Criteria andLateInterestNotIn(List<String> values) {
            addCriterion("late_interest not in", values, "lateInterest");
            return (Criteria) this;
        }

        public Criteria andLateInterestBetween(String value1, String value2) {
            addCriterion("late_interest between", value1, value2, "lateInterest");
            return (Criteria) this;
        }

        public Criteria andLateInterestNotBetween(String value1, String value2) {
            addCriterion("late_interest not between", value1, value2, "lateInterest");
            return (Criteria) this;
        }

        public Criteria andServiceFeeTotalIsNull() {
            addCriterion("service_fee_total is null");
            return (Criteria) this;
        }

        public Criteria andServiceFeeTotalIsNotNull() {
            addCriterion("service_fee_total is not null");
            return (Criteria) this;
        }

        public Criteria andServiceFeeTotalEqualTo(BigDecimal value) {
            addCriterion("service_fee_total =", value, "serviceFeeTotal");
            return (Criteria) this;
        }

        public Criteria andServiceFeeTotalNotEqualTo(BigDecimal value) {
            addCriterion("service_fee_total <>", value, "serviceFeeTotal");
            return (Criteria) this;
        }

        public Criteria andServiceFeeTotalGreaterThan(BigDecimal value) {
            addCriterion("service_fee_total >", value, "serviceFeeTotal");
            return (Criteria) this;
        }

        public Criteria andServiceFeeTotalGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("service_fee_total >=", value, "serviceFeeTotal");
            return (Criteria) this;
        }

        public Criteria andServiceFeeTotalLessThan(BigDecimal value) {
            addCriterion("service_fee_total <", value, "serviceFeeTotal");
            return (Criteria) this;
        }

        public Criteria andServiceFeeTotalLessThanOrEqualTo(BigDecimal value) {
            addCriterion("service_fee_total <=", value, "serviceFeeTotal");
            return (Criteria) this;
        }

        public Criteria andServiceFeeTotalIn(List<BigDecimal> values) {
            addCriterion("service_fee_total in", values, "serviceFeeTotal");
            return (Criteria) this;
        }

        public Criteria andServiceFeeTotalNotIn(List<BigDecimal> values) {
            addCriterion("service_fee_total not in", values, "serviceFeeTotal");
            return (Criteria) this;
        }

        public Criteria andServiceFeeTotalBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("service_fee_total between", value1, value2, "serviceFeeTotal");
            return (Criteria) this;
        }

        public Criteria andServiceFeeTotalNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("service_fee_total not between", value1, value2, "serviceFeeTotal");
            return (Criteria) this;
        }

        public Criteria andLateFreeDaysIsNull() {
            addCriterion("late_free_days is null");
            return (Criteria) this;
        }

        public Criteria andLateFreeDaysIsNotNull() {
            addCriterion("late_free_days is not null");
            return (Criteria) this;
        }

        public Criteria andLateFreeDaysEqualTo(Byte value) {
            addCriterion("late_free_days =", value, "lateFreeDays");
            return (Criteria) this;
        }

        public Criteria andLateFreeDaysNotEqualTo(Byte value) {
            addCriterion("late_free_days <>", value, "lateFreeDays");
            return (Criteria) this;
        }

        public Criteria andLateFreeDaysGreaterThan(Byte value) {
            addCriterion("late_free_days >", value, "lateFreeDays");
            return (Criteria) this;
        }

        public Criteria andLateFreeDaysGreaterThanOrEqualTo(Byte value) {
            addCriterion("late_free_days >=", value, "lateFreeDays");
            return (Criteria) this;
        }

        public Criteria andLateFreeDaysLessThan(Byte value) {
            addCriterion("late_free_days <", value, "lateFreeDays");
            return (Criteria) this;
        }

        public Criteria andLateFreeDaysLessThanOrEqualTo(Byte value) {
            addCriterion("late_free_days <=", value, "lateFreeDays");
            return (Criteria) this;
        }

        public Criteria andLateFreeDaysIn(List<Byte> values) {
            addCriterion("late_free_days in", values, "lateFreeDays");
            return (Criteria) this;
        }

        public Criteria andLateFreeDaysNotIn(List<Byte> values) {
            addCriterion("late_free_days not in", values, "lateFreeDays");
            return (Criteria) this;
        }

        public Criteria andLateFreeDaysBetween(Byte value1, Byte value2) {
            addCriterion("late_free_days between", value1, value2, "lateFreeDays");
            return (Criteria) this;
        }

        public Criteria andLateFreeDaysNotBetween(Byte value1, Byte value2) {
            addCriterion("late_free_days not between", value1, value2, "lateFreeDays");
            return (Criteria) this;
        }

        public Criteria andAutoRepayIsNull() {
            addCriterion("auto_repay is null");
            return (Criteria) this;
        }

        public Criteria andAutoRepayIsNotNull() {
            addCriterion("auto_repay is not null");
            return (Criteria) this;
        }

        public Criteria andAutoRepayEqualTo(Integer value) {
            addCriterion("auto_repay =", value, "autoRepay");
            return (Criteria) this;
        }

        public Criteria andAutoRepayNotEqualTo(Integer value) {
            addCriterion("auto_repay <>", value, "autoRepay");
            return (Criteria) this;
        }

        public Criteria andAutoRepayGreaterThan(Integer value) {
            addCriterion("auto_repay >", value, "autoRepay");
            return (Criteria) this;
        }

        public Criteria andAutoRepayGreaterThanOrEqualTo(Integer value) {
            addCriterion("auto_repay >=", value, "autoRepay");
            return (Criteria) this;
        }

        public Criteria andAutoRepayLessThan(Integer value) {
            addCriterion("auto_repay <", value, "autoRepay");
            return (Criteria) this;
        }

        public Criteria andAutoRepayLessThanOrEqualTo(Integer value) {
            addCriterion("auto_repay <=", value, "autoRepay");
            return (Criteria) this;
        }

        public Criteria andAutoRepayIn(List<Integer> values) {
            addCriterion("auto_repay in", values, "autoRepay");
            return (Criteria) this;
        }

        public Criteria andAutoRepayNotIn(List<Integer> values) {
            addCriterion("auto_repay not in", values, "autoRepay");
            return (Criteria) this;
        }

        public Criteria andAutoRepayBetween(Integer value1, Integer value2) {
            addCriterion("auto_repay between", value1, value2, "autoRepay");
            return (Criteria) this;
        }

        public Criteria andAutoRepayNotBetween(Integer value1, Integer value2) {
            addCriterion("auto_repay not between", value1, value2, "autoRepay");
            return (Criteria) this;
        }

        public Criteria andRepayerTypeIsNull() {
            addCriterion("repayer_type is null");
            return (Criteria) this;
        }

        public Criteria andRepayerTypeIsNotNull() {
            addCriterion("repayer_type is not null");
            return (Criteria) this;
        }

        public Criteria andRepayerTypeEqualTo(Integer value) {
            addCriterion("repayer_type =", value, "repayerType");
            return (Criteria) this;
        }

        public Criteria andRepayerTypeNotEqualTo(Integer value) {
            addCriterion("repayer_type <>", value, "repayerType");
            return (Criteria) this;
        }

        public Criteria andRepayerTypeGreaterThan(Integer value) {
            addCriterion("repayer_type >", value, "repayerType");
            return (Criteria) this;
        }

        public Criteria andRepayerTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("repayer_type >=", value, "repayerType");
            return (Criteria) this;
        }

        public Criteria andRepayerTypeLessThan(Integer value) {
            addCriterion("repayer_type <", value, "repayerType");
            return (Criteria) this;
        }

        public Criteria andRepayerTypeLessThanOrEqualTo(Integer value) {
            addCriterion("repayer_type <=", value, "repayerType");
            return (Criteria) this;
        }

        public Criteria andRepayerTypeIn(List<Integer> values) {
            addCriterion("repayer_type in", values, "repayerType");
            return (Criteria) this;
        }

        public Criteria andRepayerTypeNotIn(List<Integer> values) {
            addCriterion("repayer_type not in", values, "repayerType");
            return (Criteria) this;
        }

        public Criteria andRepayerTypeBetween(Integer value1, Integer value2) {
            addCriterion("repayer_type between", value1, value2, "repayerType");
            return (Criteria) this;
        }

        public Criteria andRepayerTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("repayer_type not between", value1, value2, "repayerType");
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