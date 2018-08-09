package com.hyjf.am.user.dao.model.auto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class UserPortraitExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public UserPortraitExample() {
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

        public Criteria andUserNameIsNull() {
            addCriterion("user_name is null");
            return (Criteria) this;
        }

        public Criteria andUserNameIsNotNull() {
            addCriterion("user_name is not null");
            return (Criteria) this;
        }

        public Criteria andUserNameEqualTo(String value) {
            addCriterion("user_name =", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotEqualTo(String value) {
            addCriterion("user_name <>", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameGreaterThan(String value) {
            addCriterion("user_name >", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("user_name >=", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLessThan(String value) {
            addCriterion("user_name <", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLessThanOrEqualTo(String value) {
            addCriterion("user_name <=", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLike(String value) {
            addCriterion("user_name like", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotLike(String value) {
            addCriterion("user_name not like", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameIn(List<String> values) {
            addCriterion("user_name in", values, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotIn(List<String> values) {
            addCriterion("user_name not in", values, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameBetween(String value1, String value2) {
            addCriterion("user_name between", value1, value2, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotBetween(String value1, String value2) {
            addCriterion("user_name not between", value1, value2, "userName");
            return (Criteria) this;
        }

        public Criteria andAgeIsNull() {
            addCriterion("age is null");
            return (Criteria) this;
        }

        public Criteria andAgeIsNotNull() {
            addCriterion("age is not null");
            return (Criteria) this;
        }

        public Criteria andAgeEqualTo(Integer value) {
            addCriterion("age =", value, "age");
            return (Criteria) this;
        }

        public Criteria andAgeNotEqualTo(Integer value) {
            addCriterion("age <>", value, "age");
            return (Criteria) this;
        }

        public Criteria andAgeGreaterThan(Integer value) {
            addCriterion("age >", value, "age");
            return (Criteria) this;
        }

        public Criteria andAgeGreaterThanOrEqualTo(Integer value) {
            addCriterion("age >=", value, "age");
            return (Criteria) this;
        }

        public Criteria andAgeLessThan(Integer value) {
            addCriterion("age <", value, "age");
            return (Criteria) this;
        }

        public Criteria andAgeLessThanOrEqualTo(Integer value) {
            addCriterion("age <=", value, "age");
            return (Criteria) this;
        }

        public Criteria andAgeIn(List<Integer> values) {
            addCriterion("age in", values, "age");
            return (Criteria) this;
        }

        public Criteria andAgeNotIn(List<Integer> values) {
            addCriterion("age not in", values, "age");
            return (Criteria) this;
        }

        public Criteria andAgeBetween(Integer value1, Integer value2) {
            addCriterion("age between", value1, value2, "age");
            return (Criteria) this;
        }

        public Criteria andAgeNotBetween(Integer value1, Integer value2) {
            addCriterion("age not between", value1, value2, "age");
            return (Criteria) this;
        }

        public Criteria andSexIsNull() {
            addCriterion("sex is null");
            return (Criteria) this;
        }

        public Criteria andSexIsNotNull() {
            addCriterion("sex is not null");
            return (Criteria) this;
        }

        public Criteria andSexEqualTo(String value) {
            addCriterion("sex =", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotEqualTo(String value) {
            addCriterion("sex <>", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexGreaterThan(String value) {
            addCriterion("sex >", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexGreaterThanOrEqualTo(String value) {
            addCriterion("sex >=", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexLessThan(String value) {
            addCriterion("sex <", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexLessThanOrEqualTo(String value) {
            addCriterion("sex <=", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexLike(String value) {
            addCriterion("sex like", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotLike(String value) {
            addCriterion("sex not like", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexIn(List<String> values) {
            addCriterion("sex in", values, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotIn(List<String> values) {
            addCriterion("sex not in", values, "sex");
            return (Criteria) this;
        }

        public Criteria andSexBetween(String value1, String value2) {
            addCriterion("sex between", value1, value2, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotBetween(String value1, String value2) {
            addCriterion("sex not between", value1, value2, "sex");
            return (Criteria) this;
        }

        public Criteria andEducationIsNull() {
            addCriterion("education is null");
            return (Criteria) this;
        }

        public Criteria andEducationIsNotNull() {
            addCriterion("education is not null");
            return (Criteria) this;
        }

        public Criteria andEducationEqualTo(String value) {
            addCriterion("education =", value, "education");
            return (Criteria) this;
        }

        public Criteria andEducationNotEqualTo(String value) {
            addCriterion("education <>", value, "education");
            return (Criteria) this;
        }

        public Criteria andEducationGreaterThan(String value) {
            addCriterion("education >", value, "education");
            return (Criteria) this;
        }

        public Criteria andEducationGreaterThanOrEqualTo(String value) {
            addCriterion("education >=", value, "education");
            return (Criteria) this;
        }

        public Criteria andEducationLessThan(String value) {
            addCriterion("education <", value, "education");
            return (Criteria) this;
        }

        public Criteria andEducationLessThanOrEqualTo(String value) {
            addCriterion("education <=", value, "education");
            return (Criteria) this;
        }

        public Criteria andEducationLike(String value) {
            addCriterion("education like", value, "education");
            return (Criteria) this;
        }

        public Criteria andEducationNotLike(String value) {
            addCriterion("education not like", value, "education");
            return (Criteria) this;
        }

        public Criteria andEducationIn(List<String> values) {
            addCriterion("education in", values, "education");
            return (Criteria) this;
        }

        public Criteria andEducationNotIn(List<String> values) {
            addCriterion("education not in", values, "education");
            return (Criteria) this;
        }

        public Criteria andEducationBetween(String value1, String value2) {
            addCriterion("education between", value1, value2, "education");
            return (Criteria) this;
        }

        public Criteria andEducationNotBetween(String value1, String value2) {
            addCriterion("education not between", value1, value2, "education");
            return (Criteria) this;
        }

        public Criteria andOccupationIsNull() {
            addCriterion("occupation is null");
            return (Criteria) this;
        }

        public Criteria andOccupationIsNotNull() {
            addCriterion("occupation is not null");
            return (Criteria) this;
        }

        public Criteria andOccupationEqualTo(String value) {
            addCriterion("occupation =", value, "occupation");
            return (Criteria) this;
        }

        public Criteria andOccupationNotEqualTo(String value) {
            addCriterion("occupation <>", value, "occupation");
            return (Criteria) this;
        }

        public Criteria andOccupationGreaterThan(String value) {
            addCriterion("occupation >", value, "occupation");
            return (Criteria) this;
        }

        public Criteria andOccupationGreaterThanOrEqualTo(String value) {
            addCriterion("occupation >=", value, "occupation");
            return (Criteria) this;
        }

        public Criteria andOccupationLessThan(String value) {
            addCriterion("occupation <", value, "occupation");
            return (Criteria) this;
        }

        public Criteria andOccupationLessThanOrEqualTo(String value) {
            addCriterion("occupation <=", value, "occupation");
            return (Criteria) this;
        }

        public Criteria andOccupationLike(String value) {
            addCriterion("occupation like", value, "occupation");
            return (Criteria) this;
        }

        public Criteria andOccupationNotLike(String value) {
            addCriterion("occupation not like", value, "occupation");
            return (Criteria) this;
        }

        public Criteria andOccupationIn(List<String> values) {
            addCriterion("occupation in", values, "occupation");
            return (Criteria) this;
        }

        public Criteria andOccupationNotIn(List<String> values) {
            addCriterion("occupation not in", values, "occupation");
            return (Criteria) this;
        }

        public Criteria andOccupationBetween(String value1, String value2) {
            addCriterion("occupation between", value1, value2, "occupation");
            return (Criteria) this;
        }

        public Criteria andOccupationNotBetween(String value1, String value2) {
            addCriterion("occupation not between", value1, value2, "occupation");
            return (Criteria) this;
        }

        public Criteria andCityIsNull() {
            addCriterion("city is null");
            return (Criteria) this;
        }

        public Criteria andCityIsNotNull() {
            addCriterion("city is not null");
            return (Criteria) this;
        }

        public Criteria andCityEqualTo(String value) {
            addCriterion("city =", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotEqualTo(String value) {
            addCriterion("city <>", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityGreaterThan(String value) {
            addCriterion("city >", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityGreaterThanOrEqualTo(String value) {
            addCriterion("city >=", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityLessThan(String value) {
            addCriterion("city <", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityLessThanOrEqualTo(String value) {
            addCriterion("city <=", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityLike(String value) {
            addCriterion("city like", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotLike(String value) {
            addCriterion("city not like", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityIn(List<String> values) {
            addCriterion("city in", values, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotIn(List<String> values) {
            addCriterion("city not in", values, "city");
            return (Criteria) this;
        }

        public Criteria andCityBetween(String value1, String value2) {
            addCriterion("city between", value1, value2, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotBetween(String value1, String value2) {
            addCriterion("city not between", value1, value2, "city");
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

        public Criteria andInterestEqualTo(String value) {
            addCriterion("interest =", value, "interest");
            return (Criteria) this;
        }

        public Criteria andInterestNotEqualTo(String value) {
            addCriterion("interest <>", value, "interest");
            return (Criteria) this;
        }

        public Criteria andInterestGreaterThan(String value) {
            addCriterion("interest >", value, "interest");
            return (Criteria) this;
        }

        public Criteria andInterestGreaterThanOrEqualTo(String value) {
            addCriterion("interest >=", value, "interest");
            return (Criteria) this;
        }

        public Criteria andInterestLessThan(String value) {
            addCriterion("interest <", value, "interest");
            return (Criteria) this;
        }

        public Criteria andInterestLessThanOrEqualTo(String value) {
            addCriterion("interest <=", value, "interest");
            return (Criteria) this;
        }

        public Criteria andInterestLike(String value) {
            addCriterion("interest like", value, "interest");
            return (Criteria) this;
        }

        public Criteria andInterestNotLike(String value) {
            addCriterion("interest not like", value, "interest");
            return (Criteria) this;
        }

        public Criteria andInterestIn(List<String> values) {
            addCriterion("interest in", values, "interest");
            return (Criteria) this;
        }

        public Criteria andInterestNotIn(List<String> values) {
            addCriterion("interest not in", values, "interest");
            return (Criteria) this;
        }

        public Criteria andInterestBetween(String value1, String value2) {
            addCriterion("interest between", value1, value2, "interest");
            return (Criteria) this;
        }

        public Criteria andInterestNotBetween(String value1, String value2) {
            addCriterion("interest not between", value1, value2, "interest");
            return (Criteria) this;
        }

        public Criteria andInterestSumIsNull() {
            addCriterion("interest_sum is null");
            return (Criteria) this;
        }

        public Criteria andInterestSumIsNotNull() {
            addCriterion("interest_sum is not null");
            return (Criteria) this;
        }

        public Criteria andInterestSumEqualTo(BigDecimal value) {
            addCriterion("interest_sum =", value, "interestSum");
            return (Criteria) this;
        }

        public Criteria andInterestSumNotEqualTo(BigDecimal value) {
            addCriterion("interest_sum <>", value, "interestSum");
            return (Criteria) this;
        }

        public Criteria andInterestSumGreaterThan(BigDecimal value) {
            addCriterion("interest_sum >", value, "interestSum");
            return (Criteria) this;
        }

        public Criteria andInterestSumGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("interest_sum >=", value, "interestSum");
            return (Criteria) this;
        }

        public Criteria andInterestSumLessThan(BigDecimal value) {
            addCriterion("interest_sum <", value, "interestSum");
            return (Criteria) this;
        }

        public Criteria andInterestSumLessThanOrEqualTo(BigDecimal value) {
            addCriterion("interest_sum <=", value, "interestSum");
            return (Criteria) this;
        }

        public Criteria andInterestSumIn(List<BigDecimal> values) {
            addCriterion("interest_sum in", values, "interestSum");
            return (Criteria) this;
        }

        public Criteria andInterestSumNotIn(List<BigDecimal> values) {
            addCriterion("interest_sum not in", values, "interestSum");
            return (Criteria) this;
        }

        public Criteria andInterestSumBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("interest_sum between", value1, value2, "interestSum");
            return (Criteria) this;
        }

        public Criteria andInterestSumNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("interest_sum not between", value1, value2, "interestSum");
            return (Criteria) this;
        }

        public Criteria andInvestSumIsNull() {
            addCriterion("invest_sum is null");
            return (Criteria) this;
        }

        public Criteria andInvestSumIsNotNull() {
            addCriterion("invest_sum is not null");
            return (Criteria) this;
        }

        public Criteria andInvestSumEqualTo(BigDecimal value) {
            addCriterion("invest_sum =", value, "investSum");
            return (Criteria) this;
        }

        public Criteria andInvestSumNotEqualTo(BigDecimal value) {
            addCriterion("invest_sum <>", value, "investSum");
            return (Criteria) this;
        }

        public Criteria andInvestSumGreaterThan(BigDecimal value) {
            addCriterion("invest_sum >", value, "investSum");
            return (Criteria) this;
        }

        public Criteria andInvestSumGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("invest_sum >=", value, "investSum");
            return (Criteria) this;
        }

        public Criteria andInvestSumLessThan(BigDecimal value) {
            addCriterion("invest_sum <", value, "investSum");
            return (Criteria) this;
        }

        public Criteria andInvestSumLessThanOrEqualTo(BigDecimal value) {
            addCriterion("invest_sum <=", value, "investSum");
            return (Criteria) this;
        }

        public Criteria andInvestSumIn(List<BigDecimal> values) {
            addCriterion("invest_sum in", values, "investSum");
            return (Criteria) this;
        }

        public Criteria andInvestSumNotIn(List<BigDecimal> values) {
            addCriterion("invest_sum not in", values, "investSum");
            return (Criteria) this;
        }

        public Criteria andInvestSumBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("invest_sum between", value1, value2, "investSum");
            return (Criteria) this;
        }

        public Criteria andInvestSumNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("invest_sum not between", value1, value2, "investSum");
            return (Criteria) this;
        }

        public Criteria andRechargeSumIsNull() {
            addCriterion("recharge_sum is null");
            return (Criteria) this;
        }

        public Criteria andRechargeSumIsNotNull() {
            addCriterion("recharge_sum is not null");
            return (Criteria) this;
        }

        public Criteria andRechargeSumEqualTo(BigDecimal value) {
            addCriterion("recharge_sum =", value, "rechargeSum");
            return (Criteria) this;
        }

        public Criteria andRechargeSumNotEqualTo(BigDecimal value) {
            addCriterion("recharge_sum <>", value, "rechargeSum");
            return (Criteria) this;
        }

        public Criteria andRechargeSumGreaterThan(BigDecimal value) {
            addCriterion("recharge_sum >", value, "rechargeSum");
            return (Criteria) this;
        }

        public Criteria andRechargeSumGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("recharge_sum >=", value, "rechargeSum");
            return (Criteria) this;
        }

        public Criteria andRechargeSumLessThan(BigDecimal value) {
            addCriterion("recharge_sum <", value, "rechargeSum");
            return (Criteria) this;
        }

        public Criteria andRechargeSumLessThanOrEqualTo(BigDecimal value) {
            addCriterion("recharge_sum <=", value, "rechargeSum");
            return (Criteria) this;
        }

        public Criteria andRechargeSumIn(List<BigDecimal> values) {
            addCriterion("recharge_sum in", values, "rechargeSum");
            return (Criteria) this;
        }

        public Criteria andRechargeSumNotIn(List<BigDecimal> values) {
            addCriterion("recharge_sum not in", values, "rechargeSum");
            return (Criteria) this;
        }

        public Criteria andRechargeSumBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("recharge_sum between", value1, value2, "rechargeSum");
            return (Criteria) this;
        }

        public Criteria andRechargeSumNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("recharge_sum not between", value1, value2, "rechargeSum");
            return (Criteria) this;
        }

        public Criteria andWithdrawSumIsNull() {
            addCriterion("withdraw_sum is null");
            return (Criteria) this;
        }

        public Criteria andWithdrawSumIsNotNull() {
            addCriterion("withdraw_sum is not null");
            return (Criteria) this;
        }

        public Criteria andWithdrawSumEqualTo(BigDecimal value) {
            addCriterion("withdraw_sum =", value, "withdrawSum");
            return (Criteria) this;
        }

        public Criteria andWithdrawSumNotEqualTo(BigDecimal value) {
            addCriterion("withdraw_sum <>", value, "withdrawSum");
            return (Criteria) this;
        }

        public Criteria andWithdrawSumGreaterThan(BigDecimal value) {
            addCriterion("withdraw_sum >", value, "withdrawSum");
            return (Criteria) this;
        }

        public Criteria andWithdrawSumGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("withdraw_sum >=", value, "withdrawSum");
            return (Criteria) this;
        }

        public Criteria andWithdrawSumLessThan(BigDecimal value) {
            addCriterion("withdraw_sum <", value, "withdrawSum");
            return (Criteria) this;
        }

        public Criteria andWithdrawSumLessThanOrEqualTo(BigDecimal value) {
            addCriterion("withdraw_sum <=", value, "withdrawSum");
            return (Criteria) this;
        }

        public Criteria andWithdrawSumIn(List<BigDecimal> values) {
            addCriterion("withdraw_sum in", values, "withdrawSum");
            return (Criteria) this;
        }

        public Criteria andWithdrawSumNotIn(List<BigDecimal> values) {
            addCriterion("withdraw_sum not in", values, "withdrawSum");
            return (Criteria) this;
        }

        public Criteria andWithdrawSumBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("withdraw_sum between", value1, value2, "withdrawSum");
            return (Criteria) this;
        }

        public Criteria andWithdrawSumNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("withdraw_sum not between", value1, value2, "withdrawSum");
            return (Criteria) this;
        }

        public Criteria andLoginActiveIsNull() {
            addCriterion("login_active is null");
            return (Criteria) this;
        }

        public Criteria andLoginActiveIsNotNull() {
            addCriterion("login_active is not null");
            return (Criteria) this;
        }

        public Criteria andLoginActiveEqualTo(String value) {
            addCriterion("login_active =", value, "loginActive");
            return (Criteria) this;
        }

        public Criteria andLoginActiveNotEqualTo(String value) {
            addCriterion("login_active <>", value, "loginActive");
            return (Criteria) this;
        }

        public Criteria andLoginActiveGreaterThan(String value) {
            addCriterion("login_active >", value, "loginActive");
            return (Criteria) this;
        }

        public Criteria andLoginActiveGreaterThanOrEqualTo(String value) {
            addCriterion("login_active >=", value, "loginActive");
            return (Criteria) this;
        }

        public Criteria andLoginActiveLessThan(String value) {
            addCriterion("login_active <", value, "loginActive");
            return (Criteria) this;
        }

        public Criteria andLoginActiveLessThanOrEqualTo(String value) {
            addCriterion("login_active <=", value, "loginActive");
            return (Criteria) this;
        }

        public Criteria andLoginActiveLike(String value) {
            addCriterion("login_active like", value, "loginActive");
            return (Criteria) this;
        }

        public Criteria andLoginActiveNotLike(String value) {
            addCriterion("login_active not like", value, "loginActive");
            return (Criteria) this;
        }

        public Criteria andLoginActiveIn(List<String> values) {
            addCriterion("login_active in", values, "loginActive");
            return (Criteria) this;
        }

        public Criteria andLoginActiveNotIn(List<String> values) {
            addCriterion("login_active not in", values, "loginActive");
            return (Criteria) this;
        }

        public Criteria andLoginActiveBetween(String value1, String value2) {
            addCriterion("login_active between", value1, value2, "loginActive");
            return (Criteria) this;
        }

        public Criteria andLoginActiveNotBetween(String value1, String value2) {
            addCriterion("login_active not between", value1, value2, "loginActive");
            return (Criteria) this;
        }

        public Criteria andCustomerSourceIsNull() {
            addCriterion("customer_source is null");
            return (Criteria) this;
        }

        public Criteria andCustomerSourceIsNotNull() {
            addCriterion("customer_source is not null");
            return (Criteria) this;
        }

        public Criteria andCustomerSourceEqualTo(String value) {
            addCriterion("customer_source =", value, "customerSource");
            return (Criteria) this;
        }

        public Criteria andCustomerSourceNotEqualTo(String value) {
            addCriterion("customer_source <>", value, "customerSource");
            return (Criteria) this;
        }

        public Criteria andCustomerSourceGreaterThan(String value) {
            addCriterion("customer_source >", value, "customerSource");
            return (Criteria) this;
        }

        public Criteria andCustomerSourceGreaterThanOrEqualTo(String value) {
            addCriterion("customer_source >=", value, "customerSource");
            return (Criteria) this;
        }

        public Criteria andCustomerSourceLessThan(String value) {
            addCriterion("customer_source <", value, "customerSource");
            return (Criteria) this;
        }

        public Criteria andCustomerSourceLessThanOrEqualTo(String value) {
            addCriterion("customer_source <=", value, "customerSource");
            return (Criteria) this;
        }

        public Criteria andCustomerSourceLike(String value) {
            addCriterion("customer_source like", value, "customerSource");
            return (Criteria) this;
        }

        public Criteria andCustomerSourceNotLike(String value) {
            addCriterion("customer_source not like", value, "customerSource");
            return (Criteria) this;
        }

        public Criteria andCustomerSourceIn(List<String> values) {
            addCriterion("customer_source in", values, "customerSource");
            return (Criteria) this;
        }

        public Criteria andCustomerSourceNotIn(List<String> values) {
            addCriterion("customer_source not in", values, "customerSource");
            return (Criteria) this;
        }

        public Criteria andCustomerSourceBetween(String value1, String value2) {
            addCriterion("customer_source between", value1, value2, "customerSource");
            return (Criteria) this;
        }

        public Criteria andCustomerSourceNotBetween(String value1, String value2) {
            addCriterion("customer_source not between", value1, value2, "customerSource");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeIsNull() {
            addCriterion("last_login_time is null");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeIsNotNull() {
            addCriterion("last_login_time is not null");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeEqualTo(Integer value) {
            addCriterion("last_login_time =", value, "lastLoginTime");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeNotEqualTo(Integer value) {
            addCriterion("last_login_time <>", value, "lastLoginTime");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeGreaterThan(Integer value) {
            addCriterion("last_login_time >", value, "lastLoginTime");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("last_login_time >=", value, "lastLoginTime");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeLessThan(Integer value) {
            addCriterion("last_login_time <", value, "lastLoginTime");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeLessThanOrEqualTo(Integer value) {
            addCriterion("last_login_time <=", value, "lastLoginTime");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeIn(List<Integer> values) {
            addCriterion("last_login_time in", values, "lastLoginTime");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeNotIn(List<Integer> values) {
            addCriterion("last_login_time not in", values, "lastLoginTime");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeBetween(Integer value1, Integer value2) {
            addCriterion("last_login_time between", value1, value2, "lastLoginTime");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("last_login_time not between", value1, value2, "lastLoginTime");
            return (Criteria) this;
        }

        public Criteria andLastRechargeTimeIsNull() {
            addCriterion("last_recharge_time is null");
            return (Criteria) this;
        }

        public Criteria andLastRechargeTimeIsNotNull() {
            addCriterion("last_recharge_time is not null");
            return (Criteria) this;
        }

        public Criteria andLastRechargeTimeEqualTo(Integer value) {
            addCriterion("last_recharge_time =", value, "lastRechargeTime");
            return (Criteria) this;
        }

        public Criteria andLastRechargeTimeNotEqualTo(Integer value) {
            addCriterion("last_recharge_time <>", value, "lastRechargeTime");
            return (Criteria) this;
        }

        public Criteria andLastRechargeTimeGreaterThan(Integer value) {
            addCriterion("last_recharge_time >", value, "lastRechargeTime");
            return (Criteria) this;
        }

        public Criteria andLastRechargeTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("last_recharge_time >=", value, "lastRechargeTime");
            return (Criteria) this;
        }

        public Criteria andLastRechargeTimeLessThan(Integer value) {
            addCriterion("last_recharge_time <", value, "lastRechargeTime");
            return (Criteria) this;
        }

        public Criteria andLastRechargeTimeLessThanOrEqualTo(Integer value) {
            addCriterion("last_recharge_time <=", value, "lastRechargeTime");
            return (Criteria) this;
        }

        public Criteria andLastRechargeTimeIn(List<Integer> values) {
            addCriterion("last_recharge_time in", values, "lastRechargeTime");
            return (Criteria) this;
        }

        public Criteria andLastRechargeTimeNotIn(List<Integer> values) {
            addCriterion("last_recharge_time not in", values, "lastRechargeTime");
            return (Criteria) this;
        }

        public Criteria andLastRechargeTimeBetween(Integer value1, Integer value2) {
            addCriterion("last_recharge_time between", value1, value2, "lastRechargeTime");
            return (Criteria) this;
        }

        public Criteria andLastRechargeTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("last_recharge_time not between", value1, value2, "lastRechargeTime");
            return (Criteria) this;
        }

        public Criteria andLastWithdrawTimeIsNull() {
            addCriterion("last_withdraw_time is null");
            return (Criteria) this;
        }

        public Criteria andLastWithdrawTimeIsNotNull() {
            addCriterion("last_withdraw_time is not null");
            return (Criteria) this;
        }

        public Criteria andLastWithdrawTimeEqualTo(Integer value) {
            addCriterion("last_withdraw_time =", value, "lastWithdrawTime");
            return (Criteria) this;
        }

        public Criteria andLastWithdrawTimeNotEqualTo(Integer value) {
            addCriterion("last_withdraw_time <>", value, "lastWithdrawTime");
            return (Criteria) this;
        }

        public Criteria andLastWithdrawTimeGreaterThan(Integer value) {
            addCriterion("last_withdraw_time >", value, "lastWithdrawTime");
            return (Criteria) this;
        }

        public Criteria andLastWithdrawTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("last_withdraw_time >=", value, "lastWithdrawTime");
            return (Criteria) this;
        }

        public Criteria andLastWithdrawTimeLessThan(Integer value) {
            addCriterion("last_withdraw_time <", value, "lastWithdrawTime");
            return (Criteria) this;
        }

        public Criteria andLastWithdrawTimeLessThanOrEqualTo(Integer value) {
            addCriterion("last_withdraw_time <=", value, "lastWithdrawTime");
            return (Criteria) this;
        }

        public Criteria andLastWithdrawTimeIn(List<Integer> values) {
            addCriterion("last_withdraw_time in", values, "lastWithdrawTime");
            return (Criteria) this;
        }

        public Criteria andLastWithdrawTimeNotIn(List<Integer> values) {
            addCriterion("last_withdraw_time not in", values, "lastWithdrawTime");
            return (Criteria) this;
        }

        public Criteria andLastWithdrawTimeBetween(Integer value1, Integer value2) {
            addCriterion("last_withdraw_time between", value1, value2, "lastWithdrawTime");
            return (Criteria) this;
        }

        public Criteria andLastWithdrawTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("last_withdraw_time not between", value1, value2, "lastWithdrawTime");
            return (Criteria) this;
        }

        public Criteria andInvestPlatformIsNull() {
            addCriterion("invest_platform is null");
            return (Criteria) this;
        }

        public Criteria andInvestPlatformIsNotNull() {
            addCriterion("invest_platform is not null");
            return (Criteria) this;
        }

        public Criteria andInvestPlatformEqualTo(Integer value) {
            addCriterion("invest_platform =", value, "investPlatform");
            return (Criteria) this;
        }

        public Criteria andInvestPlatformNotEqualTo(Integer value) {
            addCriterion("invest_platform <>", value, "investPlatform");
            return (Criteria) this;
        }

        public Criteria andInvestPlatformGreaterThan(Integer value) {
            addCriterion("invest_platform >", value, "investPlatform");
            return (Criteria) this;
        }

        public Criteria andInvestPlatformGreaterThanOrEqualTo(Integer value) {
            addCriterion("invest_platform >=", value, "investPlatform");
            return (Criteria) this;
        }

        public Criteria andInvestPlatformLessThan(Integer value) {
            addCriterion("invest_platform <", value, "investPlatform");
            return (Criteria) this;
        }

        public Criteria andInvestPlatformLessThanOrEqualTo(Integer value) {
            addCriterion("invest_platform <=", value, "investPlatform");
            return (Criteria) this;
        }

        public Criteria andInvestPlatformIn(List<Integer> values) {
            addCriterion("invest_platform in", values, "investPlatform");
            return (Criteria) this;
        }

        public Criteria andInvestPlatformNotIn(List<Integer> values) {
            addCriterion("invest_platform not in", values, "investPlatform");
            return (Criteria) this;
        }

        public Criteria andInvestPlatformBetween(Integer value1, Integer value2) {
            addCriterion("invest_platform between", value1, value2, "investPlatform");
            return (Criteria) this;
        }

        public Criteria andInvestPlatformNotBetween(Integer value1, Integer value2) {
            addCriterion("invest_platform not between", value1, value2, "investPlatform");
            return (Criteria) this;
        }

        public Criteria andInvestAgeIsNull() {
            addCriterion("invest_age is null");
            return (Criteria) this;
        }

        public Criteria andInvestAgeIsNotNull() {
            addCriterion("invest_age is not null");
            return (Criteria) this;
        }

        public Criteria andInvestAgeEqualTo(Integer value) {
            addCriterion("invest_age =", value, "investAge");
            return (Criteria) this;
        }

        public Criteria andInvestAgeNotEqualTo(Integer value) {
            addCriterion("invest_age <>", value, "investAge");
            return (Criteria) this;
        }

        public Criteria andInvestAgeGreaterThan(Integer value) {
            addCriterion("invest_age >", value, "investAge");
            return (Criteria) this;
        }

        public Criteria andInvestAgeGreaterThanOrEqualTo(Integer value) {
            addCriterion("invest_age >=", value, "investAge");
            return (Criteria) this;
        }

        public Criteria andInvestAgeLessThan(Integer value) {
            addCriterion("invest_age <", value, "investAge");
            return (Criteria) this;
        }

        public Criteria andInvestAgeLessThanOrEqualTo(Integer value) {
            addCriterion("invest_age <=", value, "investAge");
            return (Criteria) this;
        }

        public Criteria andInvestAgeIn(List<Integer> values) {
            addCriterion("invest_age in", values, "investAge");
            return (Criteria) this;
        }

        public Criteria andInvestAgeNotIn(List<Integer> values) {
            addCriterion("invest_age not in", values, "investAge");
            return (Criteria) this;
        }

        public Criteria andInvestAgeBetween(Integer value1, Integer value2) {
            addCriterion("invest_age between", value1, value2, "investAge");
            return (Criteria) this;
        }

        public Criteria andInvestAgeNotBetween(Integer value1, Integer value2) {
            addCriterion("invest_age not between", value1, value2, "investAge");
            return (Criteria) this;
        }

        public Criteria andTradeNumberIsNull() {
            addCriterion("trade_number is null");
            return (Criteria) this;
        }

        public Criteria andTradeNumberIsNotNull() {
            addCriterion("trade_number is not null");
            return (Criteria) this;
        }

        public Criteria andTradeNumberEqualTo(Integer value) {
            addCriterion("trade_number =", value, "tradeNumber");
            return (Criteria) this;
        }

        public Criteria andTradeNumberNotEqualTo(Integer value) {
            addCriterion("trade_number <>", value, "tradeNumber");
            return (Criteria) this;
        }

        public Criteria andTradeNumberGreaterThan(Integer value) {
            addCriterion("trade_number >", value, "tradeNumber");
            return (Criteria) this;
        }

        public Criteria andTradeNumberGreaterThanOrEqualTo(Integer value) {
            addCriterion("trade_number >=", value, "tradeNumber");
            return (Criteria) this;
        }

        public Criteria andTradeNumberLessThan(Integer value) {
            addCriterion("trade_number <", value, "tradeNumber");
            return (Criteria) this;
        }

        public Criteria andTradeNumberLessThanOrEqualTo(Integer value) {
            addCriterion("trade_number <=", value, "tradeNumber");
            return (Criteria) this;
        }

        public Criteria andTradeNumberIn(List<Integer> values) {
            addCriterion("trade_number in", values, "tradeNumber");
            return (Criteria) this;
        }

        public Criteria andTradeNumberNotIn(List<Integer> values) {
            addCriterion("trade_number not in", values, "tradeNumber");
            return (Criteria) this;
        }

        public Criteria andTradeNumberBetween(Integer value1, Integer value2) {
            addCriterion("trade_number between", value1, value2, "tradeNumber");
            return (Criteria) this;
        }

        public Criteria andTradeNumberNotBetween(Integer value1, Integer value2) {
            addCriterion("trade_number not between", value1, value2, "tradeNumber");
            return (Criteria) this;
        }

        public Criteria andCurrentOwnerIsNull() {
            addCriterion("current_owner is null");
            return (Criteria) this;
        }

        public Criteria andCurrentOwnerIsNotNull() {
            addCriterion("current_owner is not null");
            return (Criteria) this;
        }

        public Criteria andCurrentOwnerEqualTo(String value) {
            addCriterion("current_owner =", value, "currentOwner");
            return (Criteria) this;
        }

        public Criteria andCurrentOwnerNotEqualTo(String value) {
            addCriterion("current_owner <>", value, "currentOwner");
            return (Criteria) this;
        }

        public Criteria andCurrentOwnerGreaterThan(String value) {
            addCriterion("current_owner >", value, "currentOwner");
            return (Criteria) this;
        }

        public Criteria andCurrentOwnerGreaterThanOrEqualTo(String value) {
            addCriterion("current_owner >=", value, "currentOwner");
            return (Criteria) this;
        }

        public Criteria andCurrentOwnerLessThan(String value) {
            addCriterion("current_owner <", value, "currentOwner");
            return (Criteria) this;
        }

        public Criteria andCurrentOwnerLessThanOrEqualTo(String value) {
            addCriterion("current_owner <=", value, "currentOwner");
            return (Criteria) this;
        }

        public Criteria andCurrentOwnerLike(String value) {
            addCriterion("current_owner like", value, "currentOwner");
            return (Criteria) this;
        }

        public Criteria andCurrentOwnerNotLike(String value) {
            addCriterion("current_owner not like", value, "currentOwner");
            return (Criteria) this;
        }

        public Criteria andCurrentOwnerIn(List<String> values) {
            addCriterion("current_owner in", values, "currentOwner");
            return (Criteria) this;
        }

        public Criteria andCurrentOwnerNotIn(List<String> values) {
            addCriterion("current_owner not in", values, "currentOwner");
            return (Criteria) this;
        }

        public Criteria andCurrentOwnerBetween(String value1, String value2) {
            addCriterion("current_owner between", value1, value2, "currentOwner");
            return (Criteria) this;
        }

        public Criteria andCurrentOwnerNotBetween(String value1, String value2) {
            addCriterion("current_owner not between", value1, value2, "currentOwner");
            return (Criteria) this;
        }

        public Criteria andAddWechatIsNull() {
            addCriterion("add_wechat is null");
            return (Criteria) this;
        }

        public Criteria andAddWechatIsNotNull() {
            addCriterion("add_wechat is not null");
            return (Criteria) this;
        }

        public Criteria andAddWechatEqualTo(String value) {
            addCriterion("add_wechat =", value, "addWechat");
            return (Criteria) this;
        }

        public Criteria andAddWechatNotEqualTo(String value) {
            addCriterion("add_wechat <>", value, "addWechat");
            return (Criteria) this;
        }

        public Criteria andAddWechatGreaterThan(String value) {
            addCriterion("add_wechat >", value, "addWechat");
            return (Criteria) this;
        }

        public Criteria andAddWechatGreaterThanOrEqualTo(String value) {
            addCriterion("add_wechat >=", value, "addWechat");
            return (Criteria) this;
        }

        public Criteria andAddWechatLessThan(String value) {
            addCriterion("add_wechat <", value, "addWechat");
            return (Criteria) this;
        }

        public Criteria andAddWechatLessThanOrEqualTo(String value) {
            addCriterion("add_wechat <=", value, "addWechat");
            return (Criteria) this;
        }

        public Criteria andAddWechatLike(String value) {
            addCriterion("add_wechat like", value, "addWechat");
            return (Criteria) this;
        }

        public Criteria andAddWechatNotLike(String value) {
            addCriterion("add_wechat not like", value, "addWechat");
            return (Criteria) this;
        }

        public Criteria andAddWechatIn(List<String> values) {
            addCriterion("add_wechat in", values, "addWechat");
            return (Criteria) this;
        }

        public Criteria andAddWechatNotIn(List<String> values) {
            addCriterion("add_wechat not in", values, "addWechat");
            return (Criteria) this;
        }

        public Criteria andAddWechatBetween(String value1, String value2) {
            addCriterion("add_wechat between", value1, value2, "addWechat");
            return (Criteria) this;
        }

        public Criteria andAddWechatNotBetween(String value1, String value2) {
            addCriterion("add_wechat not between", value1, value2, "addWechat");
            return (Criteria) this;
        }

        public Criteria andInvestProcessIsNull() {
            addCriterion("invest_process is null");
            return (Criteria) this;
        }

        public Criteria andInvestProcessIsNotNull() {
            addCriterion("invest_process is not null");
            return (Criteria) this;
        }

        public Criteria andInvestProcessEqualTo(String value) {
            addCriterion("invest_process =", value, "investProcess");
            return (Criteria) this;
        }

        public Criteria andInvestProcessNotEqualTo(String value) {
            addCriterion("invest_process <>", value, "investProcess");
            return (Criteria) this;
        }

        public Criteria andInvestProcessGreaterThan(String value) {
            addCriterion("invest_process >", value, "investProcess");
            return (Criteria) this;
        }

        public Criteria andInvestProcessGreaterThanOrEqualTo(String value) {
            addCriterion("invest_process >=", value, "investProcess");
            return (Criteria) this;
        }

        public Criteria andInvestProcessLessThan(String value) {
            addCriterion("invest_process <", value, "investProcess");
            return (Criteria) this;
        }

        public Criteria andInvestProcessLessThanOrEqualTo(String value) {
            addCriterion("invest_process <=", value, "investProcess");
            return (Criteria) this;
        }

        public Criteria andInvestProcessLike(String value) {
            addCriterion("invest_process like", value, "investProcess");
            return (Criteria) this;
        }

        public Criteria andInvestProcessNotLike(String value) {
            addCriterion("invest_process not like", value, "investProcess");
            return (Criteria) this;
        }

        public Criteria andInvestProcessIn(List<String> values) {
            addCriterion("invest_process in", values, "investProcess");
            return (Criteria) this;
        }

        public Criteria andInvestProcessNotIn(List<String> values) {
            addCriterion("invest_process not in", values, "investProcess");
            return (Criteria) this;
        }

        public Criteria andInvestProcessBetween(String value1, String value2) {
            addCriterion("invest_process between", value1, value2, "investProcess");
            return (Criteria) this;
        }

        public Criteria andInvestProcessNotBetween(String value1, String value2) {
            addCriterion("invest_process not between", value1, value2, "investProcess");
            return (Criteria) this;
        }

        public Criteria andCustomerComplaintIsNull() {
            addCriterion("customer_complaint is null");
            return (Criteria) this;
        }

        public Criteria andCustomerComplaintIsNotNull() {
            addCriterion("customer_complaint is not null");
            return (Criteria) this;
        }

        public Criteria andCustomerComplaintEqualTo(String value) {
            addCriterion("customer_complaint =", value, "customerComplaint");
            return (Criteria) this;
        }

        public Criteria andCustomerComplaintNotEqualTo(String value) {
            addCriterion("customer_complaint <>", value, "customerComplaint");
            return (Criteria) this;
        }

        public Criteria andCustomerComplaintGreaterThan(String value) {
            addCriterion("customer_complaint >", value, "customerComplaint");
            return (Criteria) this;
        }

        public Criteria andCustomerComplaintGreaterThanOrEqualTo(String value) {
            addCriterion("customer_complaint >=", value, "customerComplaint");
            return (Criteria) this;
        }

        public Criteria andCustomerComplaintLessThan(String value) {
            addCriterion("customer_complaint <", value, "customerComplaint");
            return (Criteria) this;
        }

        public Criteria andCustomerComplaintLessThanOrEqualTo(String value) {
            addCriterion("customer_complaint <=", value, "customerComplaint");
            return (Criteria) this;
        }

        public Criteria andCustomerComplaintLike(String value) {
            addCriterion("customer_complaint like", value, "customerComplaint");
            return (Criteria) this;
        }

        public Criteria andCustomerComplaintNotLike(String value) {
            addCriterion("customer_complaint not like", value, "customerComplaint");
            return (Criteria) this;
        }

        public Criteria andCustomerComplaintIn(List<String> values) {
            addCriterion("customer_complaint in", values, "customerComplaint");
            return (Criteria) this;
        }

        public Criteria andCustomerComplaintNotIn(List<String> values) {
            addCriterion("customer_complaint not in", values, "customerComplaint");
            return (Criteria) this;
        }

        public Criteria andCustomerComplaintBetween(String value1, String value2) {
            addCriterion("customer_complaint between", value1, value2, "customerComplaint");
            return (Criteria) this;
        }

        public Criteria andCustomerComplaintNotBetween(String value1, String value2) {
            addCriterion("customer_complaint not between", value1, value2, "customerComplaint");
            return (Criteria) this;
        }

        public Criteria andInviteCustomerIsNull() {
            addCriterion("invite_customer is null");
            return (Criteria) this;
        }

        public Criteria andInviteCustomerIsNotNull() {
            addCriterion("invite_customer is not null");
            return (Criteria) this;
        }

        public Criteria andInviteCustomerEqualTo(Integer value) {
            addCriterion("invite_customer =", value, "inviteCustomer");
            return (Criteria) this;
        }

        public Criteria andInviteCustomerNotEqualTo(Integer value) {
            addCriterion("invite_customer <>", value, "inviteCustomer");
            return (Criteria) this;
        }

        public Criteria andInviteCustomerGreaterThan(Integer value) {
            addCriterion("invite_customer >", value, "inviteCustomer");
            return (Criteria) this;
        }

        public Criteria andInviteCustomerGreaterThanOrEqualTo(Integer value) {
            addCriterion("invite_customer >=", value, "inviteCustomer");
            return (Criteria) this;
        }

        public Criteria andInviteCustomerLessThan(Integer value) {
            addCriterion("invite_customer <", value, "inviteCustomer");
            return (Criteria) this;
        }

        public Criteria andInviteCustomerLessThanOrEqualTo(Integer value) {
            addCriterion("invite_customer <=", value, "inviteCustomer");
            return (Criteria) this;
        }

        public Criteria andInviteCustomerIn(List<Integer> values) {
            addCriterion("invite_customer in", values, "inviteCustomer");
            return (Criteria) this;
        }

        public Criteria andInviteCustomerNotIn(List<Integer> values) {
            addCriterion("invite_customer not in", values, "inviteCustomer");
            return (Criteria) this;
        }

        public Criteria andInviteCustomerBetween(Integer value1, Integer value2) {
            addCriterion("invite_customer between", value1, value2, "inviteCustomer");
            return (Criteria) this;
        }

        public Criteria andInviteCustomerNotBetween(Integer value1, Integer value2) {
            addCriterion("invite_customer not between", value1, value2, "inviteCustomer");
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

        public Criteria andMobileIsNull() {
            addCriterion("mobile is null");
            return (Criteria) this;
        }

        public Criteria andMobileIsNotNull() {
            addCriterion("mobile is not null");
            return (Criteria) this;
        }

        public Criteria andMobileEqualTo(String value) {
            addCriterion("mobile =", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotEqualTo(String value) {
            addCriterion("mobile <>", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileGreaterThan(String value) {
            addCriterion("mobile >", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileGreaterThanOrEqualTo(String value) {
            addCriterion("mobile >=", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLessThan(String value) {
            addCriterion("mobile <", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLessThanOrEqualTo(String value) {
            addCriterion("mobile <=", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLike(String value) {
            addCriterion("mobile like", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotLike(String value) {
            addCriterion("mobile not like", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileIn(List<String> values) {
            addCriterion("mobile in", values, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotIn(List<String> values) {
            addCriterion("mobile not in", values, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileBetween(String value1, String value2) {
            addCriterion("mobile between", value1, value2, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotBetween(String value1, String value2) {
            addCriterion("mobile not between", value1, value2, "mobile");
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