package com.hyjf.am.config.dao.model.auto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FeerateModifyLogExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public FeerateModifyLogExample() {
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

        public Criteria andBorrowPeriodIsNull() {
            addCriterion("borrow_period is null");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodIsNotNull() {
            addCriterion("borrow_period is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodEqualTo(Integer value) {
            addCriterion("borrow_period =", value, "borrowPeriod");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodNotEqualTo(Integer value) {
            addCriterion("borrow_period <>", value, "borrowPeriod");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodGreaterThan(Integer value) {
            addCriterion("borrow_period >", value, "borrowPeriod");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodGreaterThanOrEqualTo(Integer value) {
            addCriterion("borrow_period >=", value, "borrowPeriod");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodLessThan(Integer value) {
            addCriterion("borrow_period <", value, "borrowPeriod");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodLessThanOrEqualTo(Integer value) {
            addCriterion("borrow_period <=", value, "borrowPeriod");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodIn(List<Integer> values) {
            addCriterion("borrow_period in", values, "borrowPeriod");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodNotIn(List<Integer> values) {
            addCriterion("borrow_period not in", values, "borrowPeriod");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodBetween(Integer value1, Integer value2) {
            addCriterion("borrow_period between", value1, value2, "borrowPeriod");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodNotBetween(Integer value1, Integer value2) {
            addCriterion("borrow_period not between", value1, value2, "borrowPeriod");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleIsNull() {
            addCriterion("borrow_style is null");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleIsNotNull() {
            addCriterion("borrow_style is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleEqualTo(String value) {
            addCriterion("borrow_style =", value, "borrowStyle");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleNotEqualTo(String value) {
            addCriterion("borrow_style <>", value, "borrowStyle");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleGreaterThan(String value) {
            addCriterion("borrow_style >", value, "borrowStyle");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleGreaterThanOrEqualTo(String value) {
            addCriterion("borrow_style >=", value, "borrowStyle");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleLessThan(String value) {
            addCriterion("borrow_style <", value, "borrowStyle");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleLessThanOrEqualTo(String value) {
            addCriterion("borrow_style <=", value, "borrowStyle");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleLike(String value) {
            addCriterion("borrow_style like", value, "borrowStyle");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleNotLike(String value) {
            addCriterion("borrow_style not like", value, "borrowStyle");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleIn(List<String> values) {
            addCriterion("borrow_style in", values, "borrowStyle");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleNotIn(List<String> values) {
            addCriterion("borrow_style not in", values, "borrowStyle");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleBetween(String value1, String value2) {
            addCriterion("borrow_style between", value1, value2, "borrowStyle");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleNotBetween(String value1, String value2) {
            addCriterion("borrow_style not between", value1, value2, "borrowStyle");
            return (Criteria) this;
        }

        public Criteria andBorrowAprIsNull() {
            addCriterion("borrow_apr is null");
            return (Criteria) this;
        }

        public Criteria andBorrowAprIsNotNull() {
            addCriterion("borrow_apr is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowAprEqualTo(BigDecimal value) {
            addCriterion("borrow_apr =", value, "borrowApr");
            return (Criteria) this;
        }

        public Criteria andBorrowAprNotEqualTo(BigDecimal value) {
            addCriterion("borrow_apr <>", value, "borrowApr");
            return (Criteria) this;
        }

        public Criteria andBorrowAprGreaterThan(BigDecimal value) {
            addCriterion("borrow_apr >", value, "borrowApr");
            return (Criteria) this;
        }

        public Criteria andBorrowAprGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("borrow_apr >=", value, "borrowApr");
            return (Criteria) this;
        }

        public Criteria andBorrowAprLessThan(BigDecimal value) {
            addCriterion("borrow_apr <", value, "borrowApr");
            return (Criteria) this;
        }

        public Criteria andBorrowAprLessThanOrEqualTo(BigDecimal value) {
            addCriterion("borrow_apr <=", value, "borrowApr");
            return (Criteria) this;
        }

        public Criteria andBorrowAprIn(List<BigDecimal> values) {
            addCriterion("borrow_apr in", values, "borrowApr");
            return (Criteria) this;
        }

        public Criteria andBorrowAprNotIn(List<BigDecimal> values) {
            addCriterion("borrow_apr not in", values, "borrowApr");
            return (Criteria) this;
        }

        public Criteria andBorrowAprBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("borrow_apr between", value1, value2, "borrowApr");
            return (Criteria) this;
        }

        public Criteria andBorrowAprNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("borrow_apr not between", value1, value2, "borrowApr");
            return (Criteria) this;
        }

        public Criteria andServiceFeeIsNull() {
            addCriterion("service_fee is null");
            return (Criteria) this;
        }

        public Criteria andServiceFeeIsNotNull() {
            addCriterion("service_fee is not null");
            return (Criteria) this;
        }

        public Criteria andServiceFeeEqualTo(String value) {
            addCriterion("service_fee =", value, "serviceFee");
            return (Criteria) this;
        }

        public Criteria andServiceFeeNotEqualTo(String value) {
            addCriterion("service_fee <>", value, "serviceFee");
            return (Criteria) this;
        }

        public Criteria andServiceFeeGreaterThan(String value) {
            addCriterion("service_fee >", value, "serviceFee");
            return (Criteria) this;
        }

        public Criteria andServiceFeeGreaterThanOrEqualTo(String value) {
            addCriterion("service_fee >=", value, "serviceFee");
            return (Criteria) this;
        }

        public Criteria andServiceFeeLessThan(String value) {
            addCriterion("service_fee <", value, "serviceFee");
            return (Criteria) this;
        }

        public Criteria andServiceFeeLessThanOrEqualTo(String value) {
            addCriterion("service_fee <=", value, "serviceFee");
            return (Criteria) this;
        }

        public Criteria andServiceFeeLike(String value) {
            addCriterion("service_fee like", value, "serviceFee");
            return (Criteria) this;
        }

        public Criteria andServiceFeeNotLike(String value) {
            addCriterion("service_fee not like", value, "serviceFee");
            return (Criteria) this;
        }

        public Criteria andServiceFeeIn(List<String> values) {
            addCriterion("service_fee in", values, "serviceFee");
            return (Criteria) this;
        }

        public Criteria andServiceFeeNotIn(List<String> values) {
            addCriterion("service_fee not in", values, "serviceFee");
            return (Criteria) this;
        }

        public Criteria andServiceFeeBetween(String value1, String value2) {
            addCriterion("service_fee between", value1, value2, "serviceFee");
            return (Criteria) this;
        }

        public Criteria andServiceFeeNotBetween(String value1, String value2) {
            addCriterion("service_fee not between", value1, value2, "serviceFee");
            return (Criteria) this;
        }

        public Criteria andManageFeeIsNull() {
            addCriterion("manage_fee is null");
            return (Criteria) this;
        }

        public Criteria andManageFeeIsNotNull() {
            addCriterion("manage_fee is not null");
            return (Criteria) this;
        }

        public Criteria andManageFeeEqualTo(String value) {
            addCriterion("manage_fee =", value, "manageFee");
            return (Criteria) this;
        }

        public Criteria andManageFeeNotEqualTo(String value) {
            addCriterion("manage_fee <>", value, "manageFee");
            return (Criteria) this;
        }

        public Criteria andManageFeeGreaterThan(String value) {
            addCriterion("manage_fee >", value, "manageFee");
            return (Criteria) this;
        }

        public Criteria andManageFeeGreaterThanOrEqualTo(String value) {
            addCriterion("manage_fee >=", value, "manageFee");
            return (Criteria) this;
        }

        public Criteria andManageFeeLessThan(String value) {
            addCriterion("manage_fee <", value, "manageFee");
            return (Criteria) this;
        }

        public Criteria andManageFeeLessThanOrEqualTo(String value) {
            addCriterion("manage_fee <=", value, "manageFee");
            return (Criteria) this;
        }

        public Criteria andManageFeeLike(String value) {
            addCriterion("manage_fee like", value, "manageFee");
            return (Criteria) this;
        }

        public Criteria andManageFeeNotLike(String value) {
            addCriterion("manage_fee not like", value, "manageFee");
            return (Criteria) this;
        }

        public Criteria andManageFeeIn(List<String> values) {
            addCriterion("manage_fee in", values, "manageFee");
            return (Criteria) this;
        }

        public Criteria andManageFeeNotIn(List<String> values) {
            addCriterion("manage_fee not in", values, "manageFee");
            return (Criteria) this;
        }

        public Criteria andManageFeeBetween(String value1, String value2) {
            addCriterion("manage_fee between", value1, value2, "manageFee");
            return (Criteria) this;
        }

        public Criteria andManageFeeNotBetween(String value1, String value2) {
            addCriterion("manage_fee not between", value1, value2, "manageFee");
            return (Criteria) this;
        }

        public Criteria andRevenueDiffRateIsNull() {
            addCriterion("revenue_diff_rate is null");
            return (Criteria) this;
        }

        public Criteria andRevenueDiffRateIsNotNull() {
            addCriterion("revenue_diff_rate is not null");
            return (Criteria) this;
        }

        public Criteria andRevenueDiffRateEqualTo(String value) {
            addCriterion("revenue_diff_rate =", value, "revenueDiffRate");
            return (Criteria) this;
        }

        public Criteria andRevenueDiffRateNotEqualTo(String value) {
            addCriterion("revenue_diff_rate <>", value, "revenueDiffRate");
            return (Criteria) this;
        }

        public Criteria andRevenueDiffRateGreaterThan(String value) {
            addCriterion("revenue_diff_rate >", value, "revenueDiffRate");
            return (Criteria) this;
        }

        public Criteria andRevenueDiffRateGreaterThanOrEqualTo(String value) {
            addCriterion("revenue_diff_rate >=", value, "revenueDiffRate");
            return (Criteria) this;
        }

        public Criteria andRevenueDiffRateLessThan(String value) {
            addCriterion("revenue_diff_rate <", value, "revenueDiffRate");
            return (Criteria) this;
        }

        public Criteria andRevenueDiffRateLessThanOrEqualTo(String value) {
            addCriterion("revenue_diff_rate <=", value, "revenueDiffRate");
            return (Criteria) this;
        }

        public Criteria andRevenueDiffRateLike(String value) {
            addCriterion("revenue_diff_rate like", value, "revenueDiffRate");
            return (Criteria) this;
        }

        public Criteria andRevenueDiffRateNotLike(String value) {
            addCriterion("revenue_diff_rate not like", value, "revenueDiffRate");
            return (Criteria) this;
        }

        public Criteria andRevenueDiffRateIn(List<String> values) {
            addCriterion("revenue_diff_rate in", values, "revenueDiffRate");
            return (Criteria) this;
        }

        public Criteria andRevenueDiffRateNotIn(List<String> values) {
            addCriterion("revenue_diff_rate not in", values, "revenueDiffRate");
            return (Criteria) this;
        }

        public Criteria andRevenueDiffRateBetween(String value1, String value2) {
            addCriterion("revenue_diff_rate between", value1, value2, "revenueDiffRate");
            return (Criteria) this;
        }

        public Criteria andRevenueDiffRateNotBetween(String value1, String value2) {
            addCriterion("revenue_diff_rate not between", value1, value2, "revenueDiffRate");
            return (Criteria) this;
        }

        public Criteria andLateInterestRateIsNull() {
            addCriterion("late_interest_rate is null");
            return (Criteria) this;
        }

        public Criteria andLateInterestRateIsNotNull() {
            addCriterion("late_interest_rate is not null");
            return (Criteria) this;
        }

        public Criteria andLateInterestRateEqualTo(String value) {
            addCriterion("late_interest_rate =", value, "lateInterestRate");
            return (Criteria) this;
        }

        public Criteria andLateInterestRateNotEqualTo(String value) {
            addCriterion("late_interest_rate <>", value, "lateInterestRate");
            return (Criteria) this;
        }

        public Criteria andLateInterestRateGreaterThan(String value) {
            addCriterion("late_interest_rate >", value, "lateInterestRate");
            return (Criteria) this;
        }

        public Criteria andLateInterestRateGreaterThanOrEqualTo(String value) {
            addCriterion("late_interest_rate >=", value, "lateInterestRate");
            return (Criteria) this;
        }

        public Criteria andLateInterestRateLessThan(String value) {
            addCriterion("late_interest_rate <", value, "lateInterestRate");
            return (Criteria) this;
        }

        public Criteria andLateInterestRateLessThanOrEqualTo(String value) {
            addCriterion("late_interest_rate <=", value, "lateInterestRate");
            return (Criteria) this;
        }

        public Criteria andLateInterestRateLike(String value) {
            addCriterion("late_interest_rate like", value, "lateInterestRate");
            return (Criteria) this;
        }

        public Criteria andLateInterestRateNotLike(String value) {
            addCriterion("late_interest_rate not like", value, "lateInterestRate");
            return (Criteria) this;
        }

        public Criteria andLateInterestRateIn(List<String> values) {
            addCriterion("late_interest_rate in", values, "lateInterestRate");
            return (Criteria) this;
        }

        public Criteria andLateInterestRateNotIn(List<String> values) {
            addCriterion("late_interest_rate not in", values, "lateInterestRate");
            return (Criteria) this;
        }

        public Criteria andLateInterestRateBetween(String value1, String value2) {
            addCriterion("late_interest_rate between", value1, value2, "lateInterestRate");
            return (Criteria) this;
        }

        public Criteria andLateInterestRateNotBetween(String value1, String value2) {
            addCriterion("late_interest_rate not between", value1, value2, "lateInterestRate");
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

        public Criteria andLateFreeDaysEqualTo(Integer value) {
            addCriterion("late_free_days =", value, "lateFreeDays");
            return (Criteria) this;
        }

        public Criteria andLateFreeDaysNotEqualTo(Integer value) {
            addCriterion("late_free_days <>", value, "lateFreeDays");
            return (Criteria) this;
        }

        public Criteria andLateFreeDaysGreaterThan(Integer value) {
            addCriterion("late_free_days >", value, "lateFreeDays");
            return (Criteria) this;
        }

        public Criteria andLateFreeDaysGreaterThanOrEqualTo(Integer value) {
            addCriterion("late_free_days >=", value, "lateFreeDays");
            return (Criteria) this;
        }

        public Criteria andLateFreeDaysLessThan(Integer value) {
            addCriterion("late_free_days <", value, "lateFreeDays");
            return (Criteria) this;
        }

        public Criteria andLateFreeDaysLessThanOrEqualTo(Integer value) {
            addCriterion("late_free_days <=", value, "lateFreeDays");
            return (Criteria) this;
        }

        public Criteria andLateFreeDaysIn(List<Integer> values) {
            addCriterion("late_free_days in", values, "lateFreeDays");
            return (Criteria) this;
        }

        public Criteria andLateFreeDaysNotIn(List<Integer> values) {
            addCriterion("late_free_days not in", values, "lateFreeDays");
            return (Criteria) this;
        }

        public Criteria andLateFreeDaysBetween(Integer value1, Integer value2) {
            addCriterion("late_free_days between", value1, value2, "lateFreeDays");
            return (Criteria) this;
        }

        public Criteria andLateFreeDaysNotBetween(Integer value1, Integer value2) {
            addCriterion("late_free_days not between", value1, value2, "lateFreeDays");
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

        public Criteria andModifyTypeIsNull() {
            addCriterion("modify_type is null");
            return (Criteria) this;
        }

        public Criteria andModifyTypeIsNotNull() {
            addCriterion("modify_type is not null");
            return (Criteria) this;
        }

        public Criteria andModifyTypeEqualTo(Integer value) {
            addCriterion("modify_type =", value, "modifyType");
            return (Criteria) this;
        }

        public Criteria andModifyTypeNotEqualTo(Integer value) {
            addCriterion("modify_type <>", value, "modifyType");
            return (Criteria) this;
        }

        public Criteria andModifyTypeGreaterThan(Integer value) {
            addCriterion("modify_type >", value, "modifyType");
            return (Criteria) this;
        }

        public Criteria andModifyTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("modify_type >=", value, "modifyType");
            return (Criteria) this;
        }

        public Criteria andModifyTypeLessThan(Integer value) {
            addCriterion("modify_type <", value, "modifyType");
            return (Criteria) this;
        }

        public Criteria andModifyTypeLessThanOrEqualTo(Integer value) {
            addCriterion("modify_type <=", value, "modifyType");
            return (Criteria) this;
        }

        public Criteria andModifyTypeIn(List<Integer> values) {
            addCriterion("modify_type in", values, "modifyType");
            return (Criteria) this;
        }

        public Criteria andModifyTypeNotIn(List<Integer> values) {
            addCriterion("modify_type not in", values, "modifyType");
            return (Criteria) this;
        }

        public Criteria andModifyTypeBetween(Integer value1, Integer value2) {
            addCriterion("modify_type between", value1, value2, "modifyType");
            return (Criteria) this;
        }

        public Criteria andModifyTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("modify_type not between", value1, value2, "modifyType");
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