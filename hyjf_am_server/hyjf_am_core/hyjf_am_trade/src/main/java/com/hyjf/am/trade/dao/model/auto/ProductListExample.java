package com.hyjf.am.trade.dao.model.auto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductListExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public ProductListExample() {
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

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(Integer value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(Integer value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(Integer value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(Integer value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<Integer> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<Integer> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(Integer value1, Integer value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andProductIdIsNull() {
            addCriterion("product_id is null");
            return (Criteria) this;
        }

        public Criteria andProductIdIsNotNull() {
            addCriterion("product_id is not null");
            return (Criteria) this;
        }

        public Criteria andProductIdEqualTo(Integer value) {
            addCriterion("product_id =", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdNotEqualTo(Integer value) {
            addCriterion("product_id <>", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdGreaterThan(Integer value) {
            addCriterion("product_id >", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("product_id >=", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdLessThan(Integer value) {
            addCriterion("product_id <", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdLessThanOrEqualTo(Integer value) {
            addCriterion("product_id <=", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdIn(List<Integer> values) {
            addCriterion("product_id in", values, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdNotIn(List<Integer> values) {
            addCriterion("product_id not in", values, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdBetween(Integer value1, Integer value2) {
            addCriterion("product_id between", value1, value2, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdNotBetween(Integer value1, Integer value2) {
            addCriterion("product_id not between", value1, value2, "productId");
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

        public Criteria andInvestTimeIsNull() {
            addCriterion("invest_time is null");
            return (Criteria) this;
        }

        public Criteria andInvestTimeIsNotNull() {
            addCriterion("invest_time is not null");
            return (Criteria) this;
        }

        public Criteria andInvestTimeEqualTo(Integer value) {
            addCriterion("invest_time =", value, "investTime");
            return (Criteria) this;
        }

        public Criteria andInvestTimeNotEqualTo(Integer value) {
            addCriterion("invest_time <>", value, "investTime");
            return (Criteria) this;
        }

        public Criteria andInvestTimeGreaterThan(Integer value) {
            addCriterion("invest_time >", value, "investTime");
            return (Criteria) this;
        }

        public Criteria andInvestTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("invest_time >=", value, "investTime");
            return (Criteria) this;
        }

        public Criteria andInvestTimeLessThan(Integer value) {
            addCriterion("invest_time <", value, "investTime");
            return (Criteria) this;
        }

        public Criteria andInvestTimeLessThanOrEqualTo(Integer value) {
            addCriterion("invest_time <=", value, "investTime");
            return (Criteria) this;
        }

        public Criteria andInvestTimeIn(List<Integer> values) {
            addCriterion("invest_time in", values, "investTime");
            return (Criteria) this;
        }

        public Criteria andInvestTimeNotIn(List<Integer> values) {
            addCriterion("invest_time not in", values, "investTime");
            return (Criteria) this;
        }

        public Criteria andInvestTimeBetween(Integer value1, Integer value2) {
            addCriterion("invest_time between", value1, value2, "investTime");
            return (Criteria) this;
        }

        public Criteria andInvestTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("invest_time not between", value1, value2, "investTime");
            return (Criteria) this;
        }

        public Criteria andInterestTimeIsNull() {
            addCriterion("interest_time is null");
            return (Criteria) this;
        }

        public Criteria andInterestTimeIsNotNull() {
            addCriterion("interest_time is not null");
            return (Criteria) this;
        }

        public Criteria andInterestTimeEqualTo(Integer value) {
            addCriterion("interest_time =", value, "interestTime");
            return (Criteria) this;
        }

        public Criteria andInterestTimeNotEqualTo(Integer value) {
            addCriterion("interest_time <>", value, "interestTime");
            return (Criteria) this;
        }

        public Criteria andInterestTimeGreaterThan(Integer value) {
            addCriterion("interest_time >", value, "interestTime");
            return (Criteria) this;
        }

        public Criteria andInterestTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("interest_time >=", value, "interestTime");
            return (Criteria) this;
        }

        public Criteria andInterestTimeLessThan(Integer value) {
            addCriterion("interest_time <", value, "interestTime");
            return (Criteria) this;
        }

        public Criteria andInterestTimeLessThanOrEqualTo(Integer value) {
            addCriterion("interest_time <=", value, "interestTime");
            return (Criteria) this;
        }

        public Criteria andInterestTimeIn(List<Integer> values) {
            addCriterion("interest_time in", values, "interestTime");
            return (Criteria) this;
        }

        public Criteria andInterestTimeNotIn(List<Integer> values) {
            addCriterion("interest_time not in", values, "interestTime");
            return (Criteria) this;
        }

        public Criteria andInterestTimeBetween(Integer value1, Integer value2) {
            addCriterion("interest_time between", value1, value2, "interestTime");
            return (Criteria) this;
        }

        public Criteria andInterestTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("interest_time not between", value1, value2, "interestTime");
            return (Criteria) this;
        }

        public Criteria andValidDaysIsNull() {
            addCriterion("valid_days is null");
            return (Criteria) this;
        }

        public Criteria andValidDaysIsNotNull() {
            addCriterion("valid_days is not null");
            return (Criteria) this;
        }

        public Criteria andValidDaysEqualTo(Integer value) {
            addCriterion("valid_days =", value, "validDays");
            return (Criteria) this;
        }

        public Criteria andValidDaysNotEqualTo(Integer value) {
            addCriterion("valid_days <>", value, "validDays");
            return (Criteria) this;
        }

        public Criteria andValidDaysGreaterThan(Integer value) {
            addCriterion("valid_days >", value, "validDays");
            return (Criteria) this;
        }

        public Criteria andValidDaysGreaterThanOrEqualTo(Integer value) {
            addCriterion("valid_days >=", value, "validDays");
            return (Criteria) this;
        }

        public Criteria andValidDaysLessThan(Integer value) {
            addCriterion("valid_days <", value, "validDays");
            return (Criteria) this;
        }

        public Criteria andValidDaysLessThanOrEqualTo(Integer value) {
            addCriterion("valid_days <=", value, "validDays");
            return (Criteria) this;
        }

        public Criteria andValidDaysIn(List<Integer> values) {
            addCriterion("valid_days in", values, "validDays");
            return (Criteria) this;
        }

        public Criteria andValidDaysNotIn(List<Integer> values) {
            addCriterion("valid_days not in", values, "validDays");
            return (Criteria) this;
        }

        public Criteria andValidDaysBetween(Integer value1, Integer value2) {
            addCriterion("valid_days between", value1, value2, "validDays");
            return (Criteria) this;
        }

        public Criteria andValidDaysNotBetween(Integer value1, Integer value2) {
            addCriterion("valid_days not between", value1, value2, "validDays");
            return (Criteria) this;
        }

        public Criteria andRedeemedIsNull() {
            addCriterion("redeemed is null");
            return (Criteria) this;
        }

        public Criteria andRedeemedIsNotNull() {
            addCriterion("redeemed is not null");
            return (Criteria) this;
        }

        public Criteria andRedeemedEqualTo(BigDecimal value) {
            addCriterion("redeemed =", value, "redeemed");
            return (Criteria) this;
        }

        public Criteria andRedeemedNotEqualTo(BigDecimal value) {
            addCriterion("redeemed <>", value, "redeemed");
            return (Criteria) this;
        }

        public Criteria andRedeemedGreaterThan(BigDecimal value) {
            addCriterion("redeemed >", value, "redeemed");
            return (Criteria) this;
        }

        public Criteria andRedeemedGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("redeemed >=", value, "redeemed");
            return (Criteria) this;
        }

        public Criteria andRedeemedLessThan(BigDecimal value) {
            addCriterion("redeemed <", value, "redeemed");
            return (Criteria) this;
        }

        public Criteria andRedeemedLessThanOrEqualTo(BigDecimal value) {
            addCriterion("redeemed <=", value, "redeemed");
            return (Criteria) this;
        }

        public Criteria andRedeemedIn(List<BigDecimal> values) {
            addCriterion("redeemed in", values, "redeemed");
            return (Criteria) this;
        }

        public Criteria andRedeemedNotIn(List<BigDecimal> values) {
            addCriterion("redeemed not in", values, "redeemed");
            return (Criteria) this;
        }

        public Criteria andRedeemedBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("redeemed between", value1, value2, "redeemed");
            return (Criteria) this;
        }

        public Criteria andRedeemedNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("redeemed not between", value1, value2, "redeemed");
            return (Criteria) this;
        }

        public Criteria andRestAmountIsNull() {
            addCriterion("rest_amount is null");
            return (Criteria) this;
        }

        public Criteria andRestAmountIsNotNull() {
            addCriterion("rest_amount is not null");
            return (Criteria) this;
        }

        public Criteria andRestAmountEqualTo(BigDecimal value) {
            addCriterion("rest_amount =", value, "restAmount");
            return (Criteria) this;
        }

        public Criteria andRestAmountNotEqualTo(BigDecimal value) {
            addCriterion("rest_amount <>", value, "restAmount");
            return (Criteria) this;
        }

        public Criteria andRestAmountGreaterThan(BigDecimal value) {
            addCriterion("rest_amount >", value, "restAmount");
            return (Criteria) this;
        }

        public Criteria andRestAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("rest_amount >=", value, "restAmount");
            return (Criteria) this;
        }

        public Criteria andRestAmountLessThan(BigDecimal value) {
            addCriterion("rest_amount <", value, "restAmount");
            return (Criteria) this;
        }

        public Criteria andRestAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("rest_amount <=", value, "restAmount");
            return (Criteria) this;
        }

        public Criteria andRestAmountIn(List<BigDecimal> values) {
            addCriterion("rest_amount in", values, "restAmount");
            return (Criteria) this;
        }

        public Criteria andRestAmountNotIn(List<BigDecimal> values) {
            addCriterion("rest_amount not in", values, "restAmount");
            return (Criteria) this;
        }

        public Criteria andRestAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("rest_amount between", value1, value2, "restAmount");
            return (Criteria) this;
        }

        public Criteria andRestAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("rest_amount not between", value1, value2, "restAmount");
            return (Criteria) this;
        }

        public Criteria andRefereeIsNull() {
            addCriterion("referee is null");
            return (Criteria) this;
        }

        public Criteria andRefereeIsNotNull() {
            addCriterion("referee is not null");
            return (Criteria) this;
        }

        public Criteria andRefereeEqualTo(Integer value) {
            addCriterion("referee =", value, "referee");
            return (Criteria) this;
        }

        public Criteria andRefereeNotEqualTo(Integer value) {
            addCriterion("referee <>", value, "referee");
            return (Criteria) this;
        }

        public Criteria andRefereeGreaterThan(Integer value) {
            addCriterion("referee >", value, "referee");
            return (Criteria) this;
        }

        public Criteria andRefereeGreaterThanOrEqualTo(Integer value) {
            addCriterion("referee >=", value, "referee");
            return (Criteria) this;
        }

        public Criteria andRefereeLessThan(Integer value) {
            addCriterion("referee <", value, "referee");
            return (Criteria) this;
        }

        public Criteria andRefereeLessThanOrEqualTo(Integer value) {
            addCriterion("referee <=", value, "referee");
            return (Criteria) this;
        }

        public Criteria andRefereeIn(List<Integer> values) {
            addCriterion("referee in", values, "referee");
            return (Criteria) this;
        }

        public Criteria andRefereeNotIn(List<Integer> values) {
            addCriterion("referee not in", values, "referee");
            return (Criteria) this;
        }

        public Criteria andRefereeBetween(Integer value1, Integer value2) {
            addCriterion("referee between", value1, value2, "referee");
            return (Criteria) this;
        }

        public Criteria andRefereeNotBetween(Integer value1, Integer value2) {
            addCriterion("referee not between", value1, value2, "referee");
            return (Criteria) this;
        }

        public Criteria andAreaIsNull() {
            addCriterion("area is null");
            return (Criteria) this;
        }

        public Criteria andAreaIsNotNull() {
            addCriterion("area is not null");
            return (Criteria) this;
        }

        public Criteria andAreaEqualTo(Integer value) {
            addCriterion("area =", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotEqualTo(Integer value) {
            addCriterion("area <>", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaGreaterThan(Integer value) {
            addCriterion("area >", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaGreaterThanOrEqualTo(Integer value) {
            addCriterion("area >=", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaLessThan(Integer value) {
            addCriterion("area <", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaLessThanOrEqualTo(Integer value) {
            addCriterion("area <=", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaIn(List<Integer> values) {
            addCriterion("area in", values, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotIn(List<Integer> values) {
            addCriterion("area not in", values, "area");
            return (Criteria) this;
        }

        public Criteria andAreaBetween(Integer value1, Integer value2) {
            addCriterion("area between", value1, value2, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotBetween(Integer value1, Integer value2) {
            addCriterion("area not between", value1, value2, "area");
            return (Criteria) this;
        }

        public Criteria andCompanyIsNull() {
            addCriterion("company is null");
            return (Criteria) this;
        }

        public Criteria andCompanyIsNotNull() {
            addCriterion("company is not null");
            return (Criteria) this;
        }

        public Criteria andCompanyEqualTo(Integer value) {
            addCriterion("company =", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyNotEqualTo(Integer value) {
            addCriterion("company <>", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyGreaterThan(Integer value) {
            addCriterion("company >", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyGreaterThanOrEqualTo(Integer value) {
            addCriterion("company >=", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyLessThan(Integer value) {
            addCriterion("company <", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyLessThanOrEqualTo(Integer value) {
            addCriterion("company <=", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyIn(List<Integer> values) {
            addCriterion("company in", values, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyNotIn(List<Integer> values) {
            addCriterion("company not in", values, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyBetween(Integer value1, Integer value2) {
            addCriterion("company between", value1, value2, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyNotBetween(Integer value1, Integer value2) {
            addCriterion("company not between", value1, value2, "company");
            return (Criteria) this;
        }

        public Criteria andDepartmentIsNull() {
            addCriterion("department is null");
            return (Criteria) this;
        }

        public Criteria andDepartmentIsNotNull() {
            addCriterion("department is not null");
            return (Criteria) this;
        }

        public Criteria andDepartmentEqualTo(Integer value) {
            addCriterion("department =", value, "department");
            return (Criteria) this;
        }

        public Criteria andDepartmentNotEqualTo(Integer value) {
            addCriterion("department <>", value, "department");
            return (Criteria) this;
        }

        public Criteria andDepartmentGreaterThan(Integer value) {
            addCriterion("department >", value, "department");
            return (Criteria) this;
        }

        public Criteria andDepartmentGreaterThanOrEqualTo(Integer value) {
            addCriterion("department >=", value, "department");
            return (Criteria) this;
        }

        public Criteria andDepartmentLessThan(Integer value) {
            addCriterion("department <", value, "department");
            return (Criteria) this;
        }

        public Criteria andDepartmentLessThanOrEqualTo(Integer value) {
            addCriterion("department <=", value, "department");
            return (Criteria) this;
        }

        public Criteria andDepartmentIn(List<Integer> values) {
            addCriterion("department in", values, "department");
            return (Criteria) this;
        }

        public Criteria andDepartmentNotIn(List<Integer> values) {
            addCriterion("department not in", values, "department");
            return (Criteria) this;
        }

        public Criteria andDepartmentBetween(Integer value1, Integer value2) {
            addCriterion("department between", value1, value2, "department");
            return (Criteria) this;
        }

        public Criteria andDepartmentNotBetween(Integer value1, Integer value2) {
            addCriterion("department not between", value1, value2, "department");
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

        public Criteria andOrderIdIsNull() {
            addCriterion("order_id is null");
            return (Criteria) this;
        }

        public Criteria andOrderIdIsNotNull() {
            addCriterion("order_id is not null");
            return (Criteria) this;
        }

        public Criteria andOrderIdEqualTo(String value) {
            addCriterion("order_id =", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotEqualTo(String value) {
            addCriterion("order_id <>", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThan(String value) {
            addCriterion("order_id >", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThanOrEqualTo(String value) {
            addCriterion("order_id >=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThan(String value) {
            addCriterion("order_id <", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThanOrEqualTo(String value) {
            addCriterion("order_id <=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLike(String value) {
            addCriterion("order_id like", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotLike(String value) {
            addCriterion("order_id not like", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdIn(List<String> values) {
            addCriterion("order_id in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotIn(List<String> values) {
            addCriterion("order_id not in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdBetween(String value1, String value2) {
            addCriterion("order_id between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotBetween(String value1, String value2) {
            addCriterion("order_id not between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderDateIsNull() {
            addCriterion("order_date is null");
            return (Criteria) this;
        }

        public Criteria andOrderDateIsNotNull() {
            addCriterion("order_date is not null");
            return (Criteria) this;
        }

        public Criteria andOrderDateEqualTo(String value) {
            addCriterion("order_date =", value, "orderDate");
            return (Criteria) this;
        }

        public Criteria andOrderDateNotEqualTo(String value) {
            addCriterion("order_date <>", value, "orderDate");
            return (Criteria) this;
        }

        public Criteria andOrderDateGreaterThan(String value) {
            addCriterion("order_date >", value, "orderDate");
            return (Criteria) this;
        }

        public Criteria andOrderDateGreaterThanOrEqualTo(String value) {
            addCriterion("order_date >=", value, "orderDate");
            return (Criteria) this;
        }

        public Criteria andOrderDateLessThan(String value) {
            addCriterion("order_date <", value, "orderDate");
            return (Criteria) this;
        }

        public Criteria andOrderDateLessThanOrEqualTo(String value) {
            addCriterion("order_date <=", value, "orderDate");
            return (Criteria) this;
        }

        public Criteria andOrderDateLike(String value) {
            addCriterion("order_date like", value, "orderDate");
            return (Criteria) this;
        }

        public Criteria andOrderDateNotLike(String value) {
            addCriterion("order_date not like", value, "orderDate");
            return (Criteria) this;
        }

        public Criteria andOrderDateIn(List<String> values) {
            addCriterion("order_date in", values, "orderDate");
            return (Criteria) this;
        }

        public Criteria andOrderDateNotIn(List<String> values) {
            addCriterion("order_date not in", values, "orderDate");
            return (Criteria) this;
        }

        public Criteria andOrderDateBetween(String value1, String value2) {
            addCriterion("order_date between", value1, value2, "orderDate");
            return (Criteria) this;
        }

        public Criteria andOrderDateNotBetween(String value1, String value2) {
            addCriterion("order_date not between", value1, value2, "orderDate");
            return (Criteria) this;
        }

        public Criteria andInterestIsNull() {
            addCriterion("interest is null");
            return (Criteria) this;
        }

        public Criteria andInterestIsNotNull() {
            addCriterion("interest is not null");
            return (Criteria) this;
        }

        public Criteria andInterestEqualTo(BigDecimal value) {
            addCriterion("interest =", value, "interest");
            return (Criteria) this;
        }

        public Criteria andInterestNotEqualTo(BigDecimal value) {
            addCriterion("interest <>", value, "interest");
            return (Criteria) this;
        }

        public Criteria andInterestGreaterThan(BigDecimal value) {
            addCriterion("interest >", value, "interest");
            return (Criteria) this;
        }

        public Criteria andInterestGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("interest >=", value, "interest");
            return (Criteria) this;
        }

        public Criteria andInterestLessThan(BigDecimal value) {
            addCriterion("interest <", value, "interest");
            return (Criteria) this;
        }

        public Criteria andInterestLessThanOrEqualTo(BigDecimal value) {
            addCriterion("interest <=", value, "interest");
            return (Criteria) this;
        }

        public Criteria andInterestIn(List<BigDecimal> values) {
            addCriterion("interest in", values, "interest");
            return (Criteria) this;
        }

        public Criteria andInterestNotIn(List<BigDecimal> values) {
            addCriterion("interest not in", values, "interest");
            return (Criteria) this;
        }

        public Criteria andInterestBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("interest between", value1, value2, "interest");
            return (Criteria) this;
        }

        public Criteria andInterestNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("interest not between", value1, value2, "interest");
            return (Criteria) this;
        }

        public Criteria andClientIsNull() {
            addCriterion("client is null");
            return (Criteria) this;
        }

        public Criteria andClientIsNotNull() {
            addCriterion("client is not null");
            return (Criteria) this;
        }

        public Criteria andClientEqualTo(Integer value) {
            addCriterion("client =", value, "client");
            return (Criteria) this;
        }

        public Criteria andClientNotEqualTo(Integer value) {
            addCriterion("client <>", value, "client");
            return (Criteria) this;
        }

        public Criteria andClientGreaterThan(Integer value) {
            addCriterion("client >", value, "client");
            return (Criteria) this;
        }

        public Criteria andClientGreaterThanOrEqualTo(Integer value) {
            addCriterion("client >=", value, "client");
            return (Criteria) this;
        }

        public Criteria andClientLessThan(Integer value) {
            addCriterion("client <", value, "client");
            return (Criteria) this;
        }

        public Criteria andClientLessThanOrEqualTo(Integer value) {
            addCriterion("client <=", value, "client");
            return (Criteria) this;
        }

        public Criteria andClientIn(List<Integer> values) {
            addCriterion("client in", values, "client");
            return (Criteria) this;
        }

        public Criteria andClientNotIn(List<Integer> values) {
            addCriterion("client not in", values, "client");
            return (Criteria) this;
        }

        public Criteria andClientBetween(Integer value1, Integer value2) {
            addCriterion("client between", value1, value2, "client");
            return (Criteria) this;
        }

        public Criteria andClientNotBetween(Integer value1, Integer value2) {
            addCriterion("client not between", value1, value2, "client");
            return (Criteria) this;
        }

        public Criteria andBalanceIsNull() {
            addCriterion("balance is null");
            return (Criteria) this;
        }

        public Criteria andBalanceIsNotNull() {
            addCriterion("balance is not null");
            return (Criteria) this;
        }

        public Criteria andBalanceEqualTo(BigDecimal value) {
            addCriterion("balance =", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceNotEqualTo(BigDecimal value) {
            addCriterion("balance <>", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceGreaterThan(BigDecimal value) {
            addCriterion("balance >", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("balance >=", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceLessThan(BigDecimal value) {
            addCriterion("balance <", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("balance <=", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceIn(List<BigDecimal> values) {
            addCriterion("balance in", values, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceNotIn(List<BigDecimal> values) {
            addCriterion("balance not in", values, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("balance between", value1, value2, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("balance not between", value1, value2, "balance");
            return (Criteria) this;
        }

        public Criteria andLoansIdIsNull() {
            addCriterion("loans_id is null");
            return (Criteria) this;
        }

        public Criteria andLoansIdIsNotNull() {
            addCriterion("loans_id is not null");
            return (Criteria) this;
        }

        public Criteria andLoansIdEqualTo(String value) {
            addCriterion("loans_id =", value, "loansId");
            return (Criteria) this;
        }

        public Criteria andLoansIdNotEqualTo(String value) {
            addCriterion("loans_id <>", value, "loansId");
            return (Criteria) this;
        }

        public Criteria andLoansIdGreaterThan(String value) {
            addCriterion("loans_id >", value, "loansId");
            return (Criteria) this;
        }

        public Criteria andLoansIdGreaterThanOrEqualTo(String value) {
            addCriterion("loans_id >=", value, "loansId");
            return (Criteria) this;
        }

        public Criteria andLoansIdLessThan(String value) {
            addCriterion("loans_id <", value, "loansId");
            return (Criteria) this;
        }

        public Criteria andLoansIdLessThanOrEqualTo(String value) {
            addCriterion("loans_id <=", value, "loansId");
            return (Criteria) this;
        }

        public Criteria andLoansIdLike(String value) {
            addCriterion("loans_id like", value, "loansId");
            return (Criteria) this;
        }

        public Criteria andLoansIdNotLike(String value) {
            addCriterion("loans_id not like", value, "loansId");
            return (Criteria) this;
        }

        public Criteria andLoansIdIn(List<String> values) {
            addCriterion("loans_id in", values, "loansId");
            return (Criteria) this;
        }

        public Criteria andLoansIdNotIn(List<String> values) {
            addCriterion("loans_id not in", values, "loansId");
            return (Criteria) this;
        }

        public Criteria andLoansIdBetween(String value1, String value2) {
            addCriterion("loans_id between", value1, value2, "loansId");
            return (Criteria) this;
        }

        public Criteria andLoansIdNotBetween(String value1, String value2) {
            addCriterion("loans_id not between", value1, value2, "loansId");
            return (Criteria) this;
        }

        public Criteria andLoansDateIsNull() {
            addCriterion("loans_date is null");
            return (Criteria) this;
        }

        public Criteria andLoansDateIsNotNull() {
            addCriterion("loans_date is not null");
            return (Criteria) this;
        }

        public Criteria andLoansDateEqualTo(String value) {
            addCriterion("loans_date =", value, "loansDate");
            return (Criteria) this;
        }

        public Criteria andLoansDateNotEqualTo(String value) {
            addCriterion("loans_date <>", value, "loansDate");
            return (Criteria) this;
        }

        public Criteria andLoansDateGreaterThan(String value) {
            addCriterion("loans_date >", value, "loansDate");
            return (Criteria) this;
        }

        public Criteria andLoansDateGreaterThanOrEqualTo(String value) {
            addCriterion("loans_date >=", value, "loansDate");
            return (Criteria) this;
        }

        public Criteria andLoansDateLessThan(String value) {
            addCriterion("loans_date <", value, "loansDate");
            return (Criteria) this;
        }

        public Criteria andLoansDateLessThanOrEqualTo(String value) {
            addCriterion("loans_date <=", value, "loansDate");
            return (Criteria) this;
        }

        public Criteria andLoansDateLike(String value) {
            addCriterion("loans_date like", value, "loansDate");
            return (Criteria) this;
        }

        public Criteria andLoansDateNotLike(String value) {
            addCriterion("loans_date not like", value, "loansDate");
            return (Criteria) this;
        }

        public Criteria andLoansDateIn(List<String> values) {
            addCriterion("loans_date in", values, "loansDate");
            return (Criteria) this;
        }

        public Criteria andLoansDateNotIn(List<String> values) {
            addCriterion("loans_date not in", values, "loansDate");
            return (Criteria) this;
        }

        public Criteria andLoansDateBetween(String value1, String value2) {
            addCriterion("loans_date between", value1, value2, "loansDate");
            return (Criteria) this;
        }

        public Criteria andLoansDateNotBetween(String value1, String value2) {
            addCriterion("loans_date not between", value1, value2, "loansDate");
            return (Criteria) this;
        }

        public Criteria andIsNewIsNull() {
            addCriterion("is_new is null");
            return (Criteria) this;
        }

        public Criteria andIsNewIsNotNull() {
            addCriterion("is_new is not null");
            return (Criteria) this;
        }

        public Criteria andIsNewEqualTo(Integer value) {
            addCriterion("is_new =", value, "isNew");
            return (Criteria) this;
        }

        public Criteria andIsNewNotEqualTo(Integer value) {
            addCriterion("is_new <>", value, "isNew");
            return (Criteria) this;
        }

        public Criteria andIsNewGreaterThan(Integer value) {
            addCriterion("is_new >", value, "isNew");
            return (Criteria) this;
        }

        public Criteria andIsNewGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_new >=", value, "isNew");
            return (Criteria) this;
        }

        public Criteria andIsNewLessThan(Integer value) {
            addCriterion("is_new <", value, "isNew");
            return (Criteria) this;
        }

        public Criteria andIsNewLessThanOrEqualTo(Integer value) {
            addCriterion("is_new <=", value, "isNew");
            return (Criteria) this;
        }

        public Criteria andIsNewIn(List<Integer> values) {
            addCriterion("is_new in", values, "isNew");
            return (Criteria) this;
        }

        public Criteria andIsNewNotIn(List<Integer> values) {
            addCriterion("is_new not in", values, "isNew");
            return (Criteria) this;
        }

        public Criteria andIsNewBetween(Integer value1, Integer value2) {
            addCriterion("is_new between", value1, value2, "isNew");
            return (Criteria) this;
        }

        public Criteria andIsNewNotBetween(Integer value1, Integer value2) {
            addCriterion("is_new not between", value1, value2, "isNew");
            return (Criteria) this;
        }

        public Criteria andInvestStatusIsNull() {
            addCriterion("invest_status is null");
            return (Criteria) this;
        }

        public Criteria andInvestStatusIsNotNull() {
            addCriterion("invest_status is not null");
            return (Criteria) this;
        }

        public Criteria andInvestStatusEqualTo(Integer value) {
            addCriterion("invest_status =", value, "investStatus");
            return (Criteria) this;
        }

        public Criteria andInvestStatusNotEqualTo(Integer value) {
            addCriterion("invest_status <>", value, "investStatus");
            return (Criteria) this;
        }

        public Criteria andInvestStatusGreaterThan(Integer value) {
            addCriterion("invest_status >", value, "investStatus");
            return (Criteria) this;
        }

        public Criteria andInvestStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("invest_status >=", value, "investStatus");
            return (Criteria) this;
        }

        public Criteria andInvestStatusLessThan(Integer value) {
            addCriterion("invest_status <", value, "investStatus");
            return (Criteria) this;
        }

        public Criteria andInvestStatusLessThanOrEqualTo(Integer value) {
            addCriterion("invest_status <=", value, "investStatus");
            return (Criteria) this;
        }

        public Criteria andInvestStatusIn(List<Integer> values) {
            addCriterion("invest_status in", values, "investStatus");
            return (Criteria) this;
        }

        public Criteria andInvestStatusNotIn(List<Integer> values) {
            addCriterion("invest_status not in", values, "investStatus");
            return (Criteria) this;
        }

        public Criteria andInvestStatusBetween(Integer value1, Integer value2) {
            addCriterion("invest_status between", value1, value2, "investStatus");
            return (Criteria) this;
        }

        public Criteria andInvestStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("invest_status not between", value1, value2, "investStatus");
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