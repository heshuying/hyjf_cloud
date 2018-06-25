package com.hyjf.am.trade.dao.model.auto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DebtPlanInfoStaticCountExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public DebtPlanInfoStaticCountExample() {
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

        public Criteria andAccedeMoneyOneIsNull() {
            addCriterion("accede_money_one is null");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyOneIsNotNull() {
            addCriterion("accede_money_one is not null");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyOneEqualTo(BigDecimal value) {
            addCriterion("accede_money_one =", value, "accedeMoneyOne");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyOneNotEqualTo(BigDecimal value) {
            addCriterion("accede_money_one <>", value, "accedeMoneyOne");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyOneGreaterThan(BigDecimal value) {
            addCriterion("accede_money_one >", value, "accedeMoneyOne");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyOneGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("accede_money_one >=", value, "accedeMoneyOne");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyOneLessThan(BigDecimal value) {
            addCriterion("accede_money_one <", value, "accedeMoneyOne");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyOneLessThanOrEqualTo(BigDecimal value) {
            addCriterion("accede_money_one <=", value, "accedeMoneyOne");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyOneIn(List<BigDecimal> values) {
            addCriterion("accede_money_one in", values, "accedeMoneyOne");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyOneNotIn(List<BigDecimal> values) {
            addCriterion("accede_money_one not in", values, "accedeMoneyOne");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyOneBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("accede_money_one between", value1, value2, "accedeMoneyOne");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyOneNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("accede_money_one not between", value1, value2, "accedeMoneyOne");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyTwoIsNull() {
            addCriterion("accede_money_two is null");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyTwoIsNotNull() {
            addCriterion("accede_money_two is not null");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyTwoEqualTo(BigDecimal value) {
            addCriterion("accede_money_two =", value, "accedeMoneyTwo");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyTwoNotEqualTo(BigDecimal value) {
            addCriterion("accede_money_two <>", value, "accedeMoneyTwo");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyTwoGreaterThan(BigDecimal value) {
            addCriterion("accede_money_two >", value, "accedeMoneyTwo");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyTwoGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("accede_money_two >=", value, "accedeMoneyTwo");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyTwoLessThan(BigDecimal value) {
            addCriterion("accede_money_two <", value, "accedeMoneyTwo");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyTwoLessThanOrEqualTo(BigDecimal value) {
            addCriterion("accede_money_two <=", value, "accedeMoneyTwo");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyTwoIn(List<BigDecimal> values) {
            addCriterion("accede_money_two in", values, "accedeMoneyTwo");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyTwoNotIn(List<BigDecimal> values) {
            addCriterion("accede_money_two not in", values, "accedeMoneyTwo");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyTwoBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("accede_money_two between", value1, value2, "accedeMoneyTwo");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyTwoNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("accede_money_two not between", value1, value2, "accedeMoneyTwo");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyThreeIsNull() {
            addCriterion("accede_money_three is null");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyThreeIsNotNull() {
            addCriterion("accede_money_three is not null");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyThreeEqualTo(BigDecimal value) {
            addCriterion("accede_money_three =", value, "accedeMoneyThree");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyThreeNotEqualTo(BigDecimal value) {
            addCriterion("accede_money_three <>", value, "accedeMoneyThree");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyThreeGreaterThan(BigDecimal value) {
            addCriterion("accede_money_three >", value, "accedeMoneyThree");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyThreeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("accede_money_three >=", value, "accedeMoneyThree");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyThreeLessThan(BigDecimal value) {
            addCriterion("accede_money_three <", value, "accedeMoneyThree");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyThreeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("accede_money_three <=", value, "accedeMoneyThree");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyThreeIn(List<BigDecimal> values) {
            addCriterion("accede_money_three in", values, "accedeMoneyThree");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyThreeNotIn(List<BigDecimal> values) {
            addCriterion("accede_money_three not in", values, "accedeMoneyThree");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyThreeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("accede_money_three between", value1, value2, "accedeMoneyThree");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyThreeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("accede_money_three not between", value1, value2, "accedeMoneyThree");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyFourIsNull() {
            addCriterion("accede_money_four is null");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyFourIsNotNull() {
            addCriterion("accede_money_four is not null");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyFourEqualTo(BigDecimal value) {
            addCriterion("accede_money_four =", value, "accedeMoneyFour");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyFourNotEqualTo(BigDecimal value) {
            addCriterion("accede_money_four <>", value, "accedeMoneyFour");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyFourGreaterThan(BigDecimal value) {
            addCriterion("accede_money_four >", value, "accedeMoneyFour");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyFourGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("accede_money_four >=", value, "accedeMoneyFour");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyFourLessThan(BigDecimal value) {
            addCriterion("accede_money_four <", value, "accedeMoneyFour");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyFourLessThanOrEqualTo(BigDecimal value) {
            addCriterion("accede_money_four <=", value, "accedeMoneyFour");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyFourIn(List<BigDecimal> values) {
            addCriterion("accede_money_four in", values, "accedeMoneyFour");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyFourNotIn(List<BigDecimal> values) {
            addCriterion("accede_money_four not in", values, "accedeMoneyFour");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyFourBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("accede_money_four between", value1, value2, "accedeMoneyFour");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyFourNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("accede_money_four not between", value1, value2, "accedeMoneyFour");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyFiveIsNull() {
            addCriterion("accede_money_five is null");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyFiveIsNotNull() {
            addCriterion("accede_money_five is not null");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyFiveEqualTo(BigDecimal value) {
            addCriterion("accede_money_five =", value, "accedeMoneyFive");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyFiveNotEqualTo(BigDecimal value) {
            addCriterion("accede_money_five <>", value, "accedeMoneyFive");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyFiveGreaterThan(BigDecimal value) {
            addCriterion("accede_money_five >", value, "accedeMoneyFive");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyFiveGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("accede_money_five >=", value, "accedeMoneyFive");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyFiveLessThan(BigDecimal value) {
            addCriterion("accede_money_five <", value, "accedeMoneyFive");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyFiveLessThanOrEqualTo(BigDecimal value) {
            addCriterion("accede_money_five <=", value, "accedeMoneyFive");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyFiveIn(List<BigDecimal> values) {
            addCriterion("accede_money_five in", values, "accedeMoneyFive");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyFiveNotIn(List<BigDecimal> values) {
            addCriterion("accede_money_five not in", values, "accedeMoneyFive");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyFiveBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("accede_money_five between", value1, value2, "accedeMoneyFive");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyFiveNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("accede_money_five not between", value1, value2, "accedeMoneyFive");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyFiveUpIsNull() {
            addCriterion("accede_money_five_up is null");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyFiveUpIsNotNull() {
            addCriterion("accede_money_five_up is not null");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyFiveUpEqualTo(BigDecimal value) {
            addCriterion("accede_money_five_up =", value, "accedeMoneyFiveUp");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyFiveUpNotEqualTo(BigDecimal value) {
            addCriterion("accede_money_five_up <>", value, "accedeMoneyFiveUp");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyFiveUpGreaterThan(BigDecimal value) {
            addCriterion("accede_money_five_up >", value, "accedeMoneyFiveUp");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyFiveUpGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("accede_money_five_up >=", value, "accedeMoneyFiveUp");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyFiveUpLessThan(BigDecimal value) {
            addCriterion("accede_money_five_up <", value, "accedeMoneyFiveUp");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyFiveUpLessThanOrEqualTo(BigDecimal value) {
            addCriterion("accede_money_five_up <=", value, "accedeMoneyFiveUp");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyFiveUpIn(List<BigDecimal> values) {
            addCriterion("accede_money_five_up in", values, "accedeMoneyFiveUp");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyFiveUpNotIn(List<BigDecimal> values) {
            addCriterion("accede_money_five_up not in", values, "accedeMoneyFiveUp");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyFiveUpBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("accede_money_five_up between", value1, value2, "accedeMoneyFiveUp");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyFiveUpNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("accede_money_five_up not between", value1, value2, "accedeMoneyFiveUp");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyCountOneIsNull() {
            addCriterion("accede_money_count_one is null");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyCountOneIsNotNull() {
            addCriterion("accede_money_count_one is not null");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyCountOneEqualTo(Integer value) {
            addCriterion("accede_money_count_one =", value, "accedeMoneyCountOne");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyCountOneNotEqualTo(Integer value) {
            addCriterion("accede_money_count_one <>", value, "accedeMoneyCountOne");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyCountOneGreaterThan(Integer value) {
            addCriterion("accede_money_count_one >", value, "accedeMoneyCountOne");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyCountOneGreaterThanOrEqualTo(Integer value) {
            addCriterion("accede_money_count_one >=", value, "accedeMoneyCountOne");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyCountOneLessThan(Integer value) {
            addCriterion("accede_money_count_one <", value, "accedeMoneyCountOne");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyCountOneLessThanOrEqualTo(Integer value) {
            addCriterion("accede_money_count_one <=", value, "accedeMoneyCountOne");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyCountOneIn(List<Integer> values) {
            addCriterion("accede_money_count_one in", values, "accedeMoneyCountOne");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyCountOneNotIn(List<Integer> values) {
            addCriterion("accede_money_count_one not in", values, "accedeMoneyCountOne");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyCountOneBetween(Integer value1, Integer value2) {
            addCriterion("accede_money_count_one between", value1, value2, "accedeMoneyCountOne");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyCountOneNotBetween(Integer value1, Integer value2) {
            addCriterion("accede_money_count_one not between", value1, value2, "accedeMoneyCountOne");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyCountTwoIsNull() {
            addCriterion("accede_money_count_two is null");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyCountTwoIsNotNull() {
            addCriterion("accede_money_count_two is not null");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyCountTwoEqualTo(Integer value) {
            addCriterion("accede_money_count_two =", value, "accedeMoneyCountTwo");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyCountTwoNotEqualTo(Integer value) {
            addCriterion("accede_money_count_two <>", value, "accedeMoneyCountTwo");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyCountTwoGreaterThan(Integer value) {
            addCriterion("accede_money_count_two >", value, "accedeMoneyCountTwo");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyCountTwoGreaterThanOrEqualTo(Integer value) {
            addCriterion("accede_money_count_two >=", value, "accedeMoneyCountTwo");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyCountTwoLessThan(Integer value) {
            addCriterion("accede_money_count_two <", value, "accedeMoneyCountTwo");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyCountTwoLessThanOrEqualTo(Integer value) {
            addCriterion("accede_money_count_two <=", value, "accedeMoneyCountTwo");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyCountTwoIn(List<Integer> values) {
            addCriterion("accede_money_count_two in", values, "accedeMoneyCountTwo");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyCountTwoNotIn(List<Integer> values) {
            addCriterion("accede_money_count_two not in", values, "accedeMoneyCountTwo");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyCountTwoBetween(Integer value1, Integer value2) {
            addCriterion("accede_money_count_two between", value1, value2, "accedeMoneyCountTwo");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyCountTwoNotBetween(Integer value1, Integer value2) {
            addCriterion("accede_money_count_two not between", value1, value2, "accedeMoneyCountTwo");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyCountThreeIsNull() {
            addCriterion("accede_money_count_three is null");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyCountThreeIsNotNull() {
            addCriterion("accede_money_count_three is not null");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyCountThreeEqualTo(Integer value) {
            addCriterion("accede_money_count_three =", value, "accedeMoneyCountThree");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyCountThreeNotEqualTo(Integer value) {
            addCriterion("accede_money_count_three <>", value, "accedeMoneyCountThree");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyCountThreeGreaterThan(Integer value) {
            addCriterion("accede_money_count_three >", value, "accedeMoneyCountThree");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyCountThreeGreaterThanOrEqualTo(Integer value) {
            addCriterion("accede_money_count_three >=", value, "accedeMoneyCountThree");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyCountThreeLessThan(Integer value) {
            addCriterion("accede_money_count_three <", value, "accedeMoneyCountThree");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyCountThreeLessThanOrEqualTo(Integer value) {
            addCriterion("accede_money_count_three <=", value, "accedeMoneyCountThree");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyCountThreeIn(List<Integer> values) {
            addCriterion("accede_money_count_three in", values, "accedeMoneyCountThree");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyCountThreeNotIn(List<Integer> values) {
            addCriterion("accede_money_count_three not in", values, "accedeMoneyCountThree");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyCountThreeBetween(Integer value1, Integer value2) {
            addCriterion("accede_money_count_three between", value1, value2, "accedeMoneyCountThree");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyCountThreeNotBetween(Integer value1, Integer value2) {
            addCriterion("accede_money_count_three not between", value1, value2, "accedeMoneyCountThree");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyCountFourIsNull() {
            addCriterion("accede_money_count_four is null");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyCountFourIsNotNull() {
            addCriterion("accede_money_count_four is not null");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyCountFourEqualTo(Integer value) {
            addCriterion("accede_money_count_four =", value, "accedeMoneyCountFour");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyCountFourNotEqualTo(Integer value) {
            addCriterion("accede_money_count_four <>", value, "accedeMoneyCountFour");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyCountFourGreaterThan(Integer value) {
            addCriterion("accede_money_count_four >", value, "accedeMoneyCountFour");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyCountFourGreaterThanOrEqualTo(Integer value) {
            addCriterion("accede_money_count_four >=", value, "accedeMoneyCountFour");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyCountFourLessThan(Integer value) {
            addCriterion("accede_money_count_four <", value, "accedeMoneyCountFour");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyCountFourLessThanOrEqualTo(Integer value) {
            addCriterion("accede_money_count_four <=", value, "accedeMoneyCountFour");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyCountFourIn(List<Integer> values) {
            addCriterion("accede_money_count_four in", values, "accedeMoneyCountFour");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyCountFourNotIn(List<Integer> values) {
            addCriterion("accede_money_count_four not in", values, "accedeMoneyCountFour");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyCountFourBetween(Integer value1, Integer value2) {
            addCriterion("accede_money_count_four between", value1, value2, "accedeMoneyCountFour");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyCountFourNotBetween(Integer value1, Integer value2) {
            addCriterion("accede_money_count_four not between", value1, value2, "accedeMoneyCountFour");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyCountFiveIsNull() {
            addCriterion("accede_money_count_five is null");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyCountFiveIsNotNull() {
            addCriterion("accede_money_count_five is not null");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyCountFiveEqualTo(Integer value) {
            addCriterion("accede_money_count_five =", value, "accedeMoneyCountFive");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyCountFiveNotEqualTo(Integer value) {
            addCriterion("accede_money_count_five <>", value, "accedeMoneyCountFive");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyCountFiveGreaterThan(Integer value) {
            addCriterion("accede_money_count_five >", value, "accedeMoneyCountFive");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyCountFiveGreaterThanOrEqualTo(Integer value) {
            addCriterion("accede_money_count_five >=", value, "accedeMoneyCountFive");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyCountFiveLessThan(Integer value) {
            addCriterion("accede_money_count_five <", value, "accedeMoneyCountFive");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyCountFiveLessThanOrEqualTo(Integer value) {
            addCriterion("accede_money_count_five <=", value, "accedeMoneyCountFive");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyCountFiveIn(List<Integer> values) {
            addCriterion("accede_money_count_five in", values, "accedeMoneyCountFive");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyCountFiveNotIn(List<Integer> values) {
            addCriterion("accede_money_count_five not in", values, "accedeMoneyCountFive");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyCountFiveBetween(Integer value1, Integer value2) {
            addCriterion("accede_money_count_five between", value1, value2, "accedeMoneyCountFive");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyCountFiveNotBetween(Integer value1, Integer value2) {
            addCriterion("accede_money_count_five not between", value1, value2, "accedeMoneyCountFive");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyCountFiveUpIsNull() {
            addCriterion("accede_money_count_five_up is null");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyCountFiveUpIsNotNull() {
            addCriterion("accede_money_count_five_up is not null");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyCountFiveUpEqualTo(Integer value) {
            addCriterion("accede_money_count_five_up =", value, "accedeMoneyCountFiveUp");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyCountFiveUpNotEqualTo(Integer value) {
            addCriterion("accede_money_count_five_up <>", value, "accedeMoneyCountFiveUp");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyCountFiveUpGreaterThan(Integer value) {
            addCriterion("accede_money_count_five_up >", value, "accedeMoneyCountFiveUp");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyCountFiveUpGreaterThanOrEqualTo(Integer value) {
            addCriterion("accede_money_count_five_up >=", value, "accedeMoneyCountFiveUp");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyCountFiveUpLessThan(Integer value) {
            addCriterion("accede_money_count_five_up <", value, "accedeMoneyCountFiveUp");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyCountFiveUpLessThanOrEqualTo(Integer value) {
            addCriterion("accede_money_count_five_up <=", value, "accedeMoneyCountFiveUp");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyCountFiveUpIn(List<Integer> values) {
            addCriterion("accede_money_count_five_up in", values, "accedeMoneyCountFiveUp");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyCountFiveUpNotIn(List<Integer> values) {
            addCriterion("accede_money_count_five_up not in", values, "accedeMoneyCountFiveUp");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyCountFiveUpBetween(Integer value1, Integer value2) {
            addCriterion("accede_money_count_five_up between", value1, value2, "accedeMoneyCountFiveUp");
            return (Criteria) this;
        }

        public Criteria andAccedeMoneyCountFiveUpNotBetween(Integer value1, Integer value2) {
            addCriterion("accede_money_count_five_up not between", value1, value2, "accedeMoneyCountFiveUp");
            return (Criteria) this;
        }

        public Criteria andAccedeCountOneIsNull() {
            addCriterion("accede_count_one is null");
            return (Criteria) this;
        }

        public Criteria andAccedeCountOneIsNotNull() {
            addCriterion("accede_count_one is not null");
            return (Criteria) this;
        }

        public Criteria andAccedeCountOneEqualTo(Integer value) {
            addCriterion("accede_count_one =", value, "accedeCountOne");
            return (Criteria) this;
        }

        public Criteria andAccedeCountOneNotEqualTo(Integer value) {
            addCriterion("accede_count_one <>", value, "accedeCountOne");
            return (Criteria) this;
        }

        public Criteria andAccedeCountOneGreaterThan(Integer value) {
            addCriterion("accede_count_one >", value, "accedeCountOne");
            return (Criteria) this;
        }

        public Criteria andAccedeCountOneGreaterThanOrEqualTo(Integer value) {
            addCriterion("accede_count_one >=", value, "accedeCountOne");
            return (Criteria) this;
        }

        public Criteria andAccedeCountOneLessThan(Integer value) {
            addCriterion("accede_count_one <", value, "accedeCountOne");
            return (Criteria) this;
        }

        public Criteria andAccedeCountOneLessThanOrEqualTo(Integer value) {
            addCriterion("accede_count_one <=", value, "accedeCountOne");
            return (Criteria) this;
        }

        public Criteria andAccedeCountOneIn(List<Integer> values) {
            addCriterion("accede_count_one in", values, "accedeCountOne");
            return (Criteria) this;
        }

        public Criteria andAccedeCountOneNotIn(List<Integer> values) {
            addCriterion("accede_count_one not in", values, "accedeCountOne");
            return (Criteria) this;
        }

        public Criteria andAccedeCountOneBetween(Integer value1, Integer value2) {
            addCriterion("accede_count_one between", value1, value2, "accedeCountOne");
            return (Criteria) this;
        }

        public Criteria andAccedeCountOneNotBetween(Integer value1, Integer value2) {
            addCriterion("accede_count_one not between", value1, value2, "accedeCountOne");
            return (Criteria) this;
        }

        public Criteria andAccedeCountTwoFourIsNull() {
            addCriterion("accede_count_two_four is null");
            return (Criteria) this;
        }

        public Criteria andAccedeCountTwoFourIsNotNull() {
            addCriterion("accede_count_two_four is not null");
            return (Criteria) this;
        }

        public Criteria andAccedeCountTwoFourEqualTo(Integer value) {
            addCriterion("accede_count_two_four =", value, "accedeCountTwoFour");
            return (Criteria) this;
        }

        public Criteria andAccedeCountTwoFourNotEqualTo(Integer value) {
            addCriterion("accede_count_two_four <>", value, "accedeCountTwoFour");
            return (Criteria) this;
        }

        public Criteria andAccedeCountTwoFourGreaterThan(Integer value) {
            addCriterion("accede_count_two_four >", value, "accedeCountTwoFour");
            return (Criteria) this;
        }

        public Criteria andAccedeCountTwoFourGreaterThanOrEqualTo(Integer value) {
            addCriterion("accede_count_two_four >=", value, "accedeCountTwoFour");
            return (Criteria) this;
        }

        public Criteria andAccedeCountTwoFourLessThan(Integer value) {
            addCriterion("accede_count_two_four <", value, "accedeCountTwoFour");
            return (Criteria) this;
        }

        public Criteria andAccedeCountTwoFourLessThanOrEqualTo(Integer value) {
            addCriterion("accede_count_two_four <=", value, "accedeCountTwoFour");
            return (Criteria) this;
        }

        public Criteria andAccedeCountTwoFourIn(List<Integer> values) {
            addCriterion("accede_count_two_four in", values, "accedeCountTwoFour");
            return (Criteria) this;
        }

        public Criteria andAccedeCountTwoFourNotIn(List<Integer> values) {
            addCriterion("accede_count_two_four not in", values, "accedeCountTwoFour");
            return (Criteria) this;
        }

        public Criteria andAccedeCountTwoFourBetween(Integer value1, Integer value2) {
            addCriterion("accede_count_two_four between", value1, value2, "accedeCountTwoFour");
            return (Criteria) this;
        }

        public Criteria andAccedeCountTwoFourNotBetween(Integer value1, Integer value2) {
            addCriterion("accede_count_two_four not between", value1, value2, "accedeCountTwoFour");
            return (Criteria) this;
        }

        public Criteria andAccedeCountFiveEgihtIsNull() {
            addCriterion("accede_count_five_egiht is null");
            return (Criteria) this;
        }

        public Criteria andAccedeCountFiveEgihtIsNotNull() {
            addCriterion("accede_count_five_egiht is not null");
            return (Criteria) this;
        }

        public Criteria andAccedeCountFiveEgihtEqualTo(Integer value) {
            addCriterion("accede_count_five_egiht =", value, "accedeCountFiveEgiht");
            return (Criteria) this;
        }

        public Criteria andAccedeCountFiveEgihtNotEqualTo(Integer value) {
            addCriterion("accede_count_five_egiht <>", value, "accedeCountFiveEgiht");
            return (Criteria) this;
        }

        public Criteria andAccedeCountFiveEgihtGreaterThan(Integer value) {
            addCriterion("accede_count_five_egiht >", value, "accedeCountFiveEgiht");
            return (Criteria) this;
        }

        public Criteria andAccedeCountFiveEgihtGreaterThanOrEqualTo(Integer value) {
            addCriterion("accede_count_five_egiht >=", value, "accedeCountFiveEgiht");
            return (Criteria) this;
        }

        public Criteria andAccedeCountFiveEgihtLessThan(Integer value) {
            addCriterion("accede_count_five_egiht <", value, "accedeCountFiveEgiht");
            return (Criteria) this;
        }

        public Criteria andAccedeCountFiveEgihtLessThanOrEqualTo(Integer value) {
            addCriterion("accede_count_five_egiht <=", value, "accedeCountFiveEgiht");
            return (Criteria) this;
        }

        public Criteria andAccedeCountFiveEgihtIn(List<Integer> values) {
            addCriterion("accede_count_five_egiht in", values, "accedeCountFiveEgiht");
            return (Criteria) this;
        }

        public Criteria andAccedeCountFiveEgihtNotIn(List<Integer> values) {
            addCriterion("accede_count_five_egiht not in", values, "accedeCountFiveEgiht");
            return (Criteria) this;
        }

        public Criteria andAccedeCountFiveEgihtBetween(Integer value1, Integer value2) {
            addCriterion("accede_count_five_egiht between", value1, value2, "accedeCountFiveEgiht");
            return (Criteria) this;
        }

        public Criteria andAccedeCountFiveEgihtNotBetween(Integer value1, Integer value2) {
            addCriterion("accede_count_five_egiht not between", value1, value2, "accedeCountFiveEgiht");
            return (Criteria) this;
        }

        public Criteria andAccedeCountNineFifteenIsNull() {
            addCriterion("accede_count_nine_fifteen is null");
            return (Criteria) this;
        }

        public Criteria andAccedeCountNineFifteenIsNotNull() {
            addCriterion("accede_count_nine_fifteen is not null");
            return (Criteria) this;
        }

        public Criteria andAccedeCountNineFifteenEqualTo(Integer value) {
            addCriterion("accede_count_nine_fifteen =", value, "accedeCountNineFifteen");
            return (Criteria) this;
        }

        public Criteria andAccedeCountNineFifteenNotEqualTo(Integer value) {
            addCriterion("accede_count_nine_fifteen <>", value, "accedeCountNineFifteen");
            return (Criteria) this;
        }

        public Criteria andAccedeCountNineFifteenGreaterThan(Integer value) {
            addCriterion("accede_count_nine_fifteen >", value, "accedeCountNineFifteen");
            return (Criteria) this;
        }

        public Criteria andAccedeCountNineFifteenGreaterThanOrEqualTo(Integer value) {
            addCriterion("accede_count_nine_fifteen >=", value, "accedeCountNineFifteen");
            return (Criteria) this;
        }

        public Criteria andAccedeCountNineFifteenLessThan(Integer value) {
            addCriterion("accede_count_nine_fifteen <", value, "accedeCountNineFifteen");
            return (Criteria) this;
        }

        public Criteria andAccedeCountNineFifteenLessThanOrEqualTo(Integer value) {
            addCriterion("accede_count_nine_fifteen <=", value, "accedeCountNineFifteen");
            return (Criteria) this;
        }

        public Criteria andAccedeCountNineFifteenIn(List<Integer> values) {
            addCriterion("accede_count_nine_fifteen in", values, "accedeCountNineFifteen");
            return (Criteria) this;
        }

        public Criteria andAccedeCountNineFifteenNotIn(List<Integer> values) {
            addCriterion("accede_count_nine_fifteen not in", values, "accedeCountNineFifteen");
            return (Criteria) this;
        }

        public Criteria andAccedeCountNineFifteenBetween(Integer value1, Integer value2) {
            addCriterion("accede_count_nine_fifteen between", value1, value2, "accedeCountNineFifteen");
            return (Criteria) this;
        }

        public Criteria andAccedeCountNineFifteenNotBetween(Integer value1, Integer value2) {
            addCriterion("accede_count_nine_fifteen not between", value1, value2, "accedeCountNineFifteen");
            return (Criteria) this;
        }

        public Criteria andAccedeCountSixteenThirtyIsNull() {
            addCriterion("accede_count_sixteen_thirty is null");
            return (Criteria) this;
        }

        public Criteria andAccedeCountSixteenThirtyIsNotNull() {
            addCriterion("accede_count_sixteen_thirty is not null");
            return (Criteria) this;
        }

        public Criteria andAccedeCountSixteenThirtyEqualTo(Integer value) {
            addCriterion("accede_count_sixteen_thirty =", value, "accedeCountSixteenThirty");
            return (Criteria) this;
        }

        public Criteria andAccedeCountSixteenThirtyNotEqualTo(Integer value) {
            addCriterion("accede_count_sixteen_thirty <>", value, "accedeCountSixteenThirty");
            return (Criteria) this;
        }

        public Criteria andAccedeCountSixteenThirtyGreaterThan(Integer value) {
            addCriterion("accede_count_sixteen_thirty >", value, "accedeCountSixteenThirty");
            return (Criteria) this;
        }

        public Criteria andAccedeCountSixteenThirtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("accede_count_sixteen_thirty >=", value, "accedeCountSixteenThirty");
            return (Criteria) this;
        }

        public Criteria andAccedeCountSixteenThirtyLessThan(Integer value) {
            addCriterion("accede_count_sixteen_thirty <", value, "accedeCountSixteenThirty");
            return (Criteria) this;
        }

        public Criteria andAccedeCountSixteenThirtyLessThanOrEqualTo(Integer value) {
            addCriterion("accede_count_sixteen_thirty <=", value, "accedeCountSixteenThirty");
            return (Criteria) this;
        }

        public Criteria andAccedeCountSixteenThirtyIn(List<Integer> values) {
            addCriterion("accede_count_sixteen_thirty in", values, "accedeCountSixteenThirty");
            return (Criteria) this;
        }

        public Criteria andAccedeCountSixteenThirtyNotIn(List<Integer> values) {
            addCriterion("accede_count_sixteen_thirty not in", values, "accedeCountSixteenThirty");
            return (Criteria) this;
        }

        public Criteria andAccedeCountSixteenThirtyBetween(Integer value1, Integer value2) {
            addCriterion("accede_count_sixteen_thirty between", value1, value2, "accedeCountSixteenThirty");
            return (Criteria) this;
        }

        public Criteria andAccedeCountSixteenThirtyNotBetween(Integer value1, Integer value2) {
            addCriterion("accede_count_sixteen_thirty not between", value1, value2, "accedeCountSixteenThirty");
            return (Criteria) this;
        }

        public Criteria andAccedeCountThirtyfirstUpIsNull() {
            addCriterion("accede_count_thirtyfirst_up is null");
            return (Criteria) this;
        }

        public Criteria andAccedeCountThirtyfirstUpIsNotNull() {
            addCriterion("accede_count_thirtyfirst_up is not null");
            return (Criteria) this;
        }

        public Criteria andAccedeCountThirtyfirstUpEqualTo(Integer value) {
            addCriterion("accede_count_thirtyfirst_up =", value, "accedeCountThirtyfirstUp");
            return (Criteria) this;
        }

        public Criteria andAccedeCountThirtyfirstUpNotEqualTo(Integer value) {
            addCriterion("accede_count_thirtyfirst_up <>", value, "accedeCountThirtyfirstUp");
            return (Criteria) this;
        }

        public Criteria andAccedeCountThirtyfirstUpGreaterThan(Integer value) {
            addCriterion("accede_count_thirtyfirst_up >", value, "accedeCountThirtyfirstUp");
            return (Criteria) this;
        }

        public Criteria andAccedeCountThirtyfirstUpGreaterThanOrEqualTo(Integer value) {
            addCriterion("accede_count_thirtyfirst_up >=", value, "accedeCountThirtyfirstUp");
            return (Criteria) this;
        }

        public Criteria andAccedeCountThirtyfirstUpLessThan(Integer value) {
            addCriterion("accede_count_thirtyfirst_up <", value, "accedeCountThirtyfirstUp");
            return (Criteria) this;
        }

        public Criteria andAccedeCountThirtyfirstUpLessThanOrEqualTo(Integer value) {
            addCriterion("accede_count_thirtyfirst_up <=", value, "accedeCountThirtyfirstUp");
            return (Criteria) this;
        }

        public Criteria andAccedeCountThirtyfirstUpIn(List<Integer> values) {
            addCriterion("accede_count_thirtyfirst_up in", values, "accedeCountThirtyfirstUp");
            return (Criteria) this;
        }

        public Criteria andAccedeCountThirtyfirstUpNotIn(List<Integer> values) {
            addCriterion("accede_count_thirtyfirst_up not in", values, "accedeCountThirtyfirstUp");
            return (Criteria) this;
        }

        public Criteria andAccedeCountThirtyfirstUpBetween(Integer value1, Integer value2) {
            addCriterion("accede_count_thirtyfirst_up between", value1, value2, "accedeCountThirtyfirstUp");
            return (Criteria) this;
        }

        public Criteria andAccedeCountThirtyfirstUpNotBetween(Integer value1, Integer value2) {
            addCriterion("accede_count_thirtyfirst_up not between", value1, value2, "accedeCountThirtyfirstUp");
            return (Criteria) this;
        }

        public Criteria andAccedeClientMoneyPcIsNull() {
            addCriterion("accede_client_money_pc is null");
            return (Criteria) this;
        }

        public Criteria andAccedeClientMoneyPcIsNotNull() {
            addCriterion("accede_client_money_pc is not null");
            return (Criteria) this;
        }

        public Criteria andAccedeClientMoneyPcEqualTo(BigDecimal value) {
            addCriterion("accede_client_money_pc =", value, "accedeClientMoneyPc");
            return (Criteria) this;
        }

        public Criteria andAccedeClientMoneyPcNotEqualTo(BigDecimal value) {
            addCriterion("accede_client_money_pc <>", value, "accedeClientMoneyPc");
            return (Criteria) this;
        }

        public Criteria andAccedeClientMoneyPcGreaterThan(BigDecimal value) {
            addCriterion("accede_client_money_pc >", value, "accedeClientMoneyPc");
            return (Criteria) this;
        }

        public Criteria andAccedeClientMoneyPcGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("accede_client_money_pc >=", value, "accedeClientMoneyPc");
            return (Criteria) this;
        }

        public Criteria andAccedeClientMoneyPcLessThan(BigDecimal value) {
            addCriterion("accede_client_money_pc <", value, "accedeClientMoneyPc");
            return (Criteria) this;
        }

        public Criteria andAccedeClientMoneyPcLessThanOrEqualTo(BigDecimal value) {
            addCriterion("accede_client_money_pc <=", value, "accedeClientMoneyPc");
            return (Criteria) this;
        }

        public Criteria andAccedeClientMoneyPcIn(List<BigDecimal> values) {
            addCriterion("accede_client_money_pc in", values, "accedeClientMoneyPc");
            return (Criteria) this;
        }

        public Criteria andAccedeClientMoneyPcNotIn(List<BigDecimal> values) {
            addCriterion("accede_client_money_pc not in", values, "accedeClientMoneyPc");
            return (Criteria) this;
        }

        public Criteria andAccedeClientMoneyPcBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("accede_client_money_pc between", value1, value2, "accedeClientMoneyPc");
            return (Criteria) this;
        }

        public Criteria andAccedeClientMoneyPcNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("accede_client_money_pc not between", value1, value2, "accedeClientMoneyPc");
            return (Criteria) this;
        }

        public Criteria andAccedeClientMoneyWeiIsNull() {
            addCriterion("accede_client_money_wei is null");
            return (Criteria) this;
        }

        public Criteria andAccedeClientMoneyWeiIsNotNull() {
            addCriterion("accede_client_money_wei is not null");
            return (Criteria) this;
        }

        public Criteria andAccedeClientMoneyWeiEqualTo(BigDecimal value) {
            addCriterion("accede_client_money_wei =", value, "accedeClientMoneyWei");
            return (Criteria) this;
        }

        public Criteria andAccedeClientMoneyWeiNotEqualTo(BigDecimal value) {
            addCriterion("accede_client_money_wei <>", value, "accedeClientMoneyWei");
            return (Criteria) this;
        }

        public Criteria andAccedeClientMoneyWeiGreaterThan(BigDecimal value) {
            addCriterion("accede_client_money_wei >", value, "accedeClientMoneyWei");
            return (Criteria) this;
        }

        public Criteria andAccedeClientMoneyWeiGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("accede_client_money_wei >=", value, "accedeClientMoneyWei");
            return (Criteria) this;
        }

        public Criteria andAccedeClientMoneyWeiLessThan(BigDecimal value) {
            addCriterion("accede_client_money_wei <", value, "accedeClientMoneyWei");
            return (Criteria) this;
        }

        public Criteria andAccedeClientMoneyWeiLessThanOrEqualTo(BigDecimal value) {
            addCriterion("accede_client_money_wei <=", value, "accedeClientMoneyWei");
            return (Criteria) this;
        }

        public Criteria andAccedeClientMoneyWeiIn(List<BigDecimal> values) {
            addCriterion("accede_client_money_wei in", values, "accedeClientMoneyWei");
            return (Criteria) this;
        }

        public Criteria andAccedeClientMoneyWeiNotIn(List<BigDecimal> values) {
            addCriterion("accede_client_money_wei not in", values, "accedeClientMoneyWei");
            return (Criteria) this;
        }

        public Criteria andAccedeClientMoneyWeiBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("accede_client_money_wei between", value1, value2, "accedeClientMoneyWei");
            return (Criteria) this;
        }

        public Criteria andAccedeClientMoneyWeiNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("accede_client_money_wei not between", value1, value2, "accedeClientMoneyWei");
            return (Criteria) this;
        }

        public Criteria andAccedeClientMoneyIosIsNull() {
            addCriterion("accede_client_money_ios is null");
            return (Criteria) this;
        }

        public Criteria andAccedeClientMoneyIosIsNotNull() {
            addCriterion("accede_client_money_ios is not null");
            return (Criteria) this;
        }

        public Criteria andAccedeClientMoneyIosEqualTo(BigDecimal value) {
            addCriterion("accede_client_money_ios =", value, "accedeClientMoneyIos");
            return (Criteria) this;
        }

        public Criteria andAccedeClientMoneyIosNotEqualTo(BigDecimal value) {
            addCriterion("accede_client_money_ios <>", value, "accedeClientMoneyIos");
            return (Criteria) this;
        }

        public Criteria andAccedeClientMoneyIosGreaterThan(BigDecimal value) {
            addCriterion("accede_client_money_ios >", value, "accedeClientMoneyIos");
            return (Criteria) this;
        }

        public Criteria andAccedeClientMoneyIosGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("accede_client_money_ios >=", value, "accedeClientMoneyIos");
            return (Criteria) this;
        }

        public Criteria andAccedeClientMoneyIosLessThan(BigDecimal value) {
            addCriterion("accede_client_money_ios <", value, "accedeClientMoneyIos");
            return (Criteria) this;
        }

        public Criteria andAccedeClientMoneyIosLessThanOrEqualTo(BigDecimal value) {
            addCriterion("accede_client_money_ios <=", value, "accedeClientMoneyIos");
            return (Criteria) this;
        }

        public Criteria andAccedeClientMoneyIosIn(List<BigDecimal> values) {
            addCriterion("accede_client_money_ios in", values, "accedeClientMoneyIos");
            return (Criteria) this;
        }

        public Criteria andAccedeClientMoneyIosNotIn(List<BigDecimal> values) {
            addCriterion("accede_client_money_ios not in", values, "accedeClientMoneyIos");
            return (Criteria) this;
        }

        public Criteria andAccedeClientMoneyIosBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("accede_client_money_ios between", value1, value2, "accedeClientMoneyIos");
            return (Criteria) this;
        }

        public Criteria andAccedeClientMoneyIosNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("accede_client_money_ios not between", value1, value2, "accedeClientMoneyIos");
            return (Criteria) this;
        }

        public Criteria andAccedeClientMoneyAndroidIsNull() {
            addCriterion("accede_client_money_android is null");
            return (Criteria) this;
        }

        public Criteria andAccedeClientMoneyAndroidIsNotNull() {
            addCriterion("accede_client_money_android is not null");
            return (Criteria) this;
        }

        public Criteria andAccedeClientMoneyAndroidEqualTo(BigDecimal value) {
            addCriterion("accede_client_money_android =", value, "accedeClientMoneyAndroid");
            return (Criteria) this;
        }

        public Criteria andAccedeClientMoneyAndroidNotEqualTo(BigDecimal value) {
            addCriterion("accede_client_money_android <>", value, "accedeClientMoneyAndroid");
            return (Criteria) this;
        }

        public Criteria andAccedeClientMoneyAndroidGreaterThan(BigDecimal value) {
            addCriterion("accede_client_money_android >", value, "accedeClientMoneyAndroid");
            return (Criteria) this;
        }

        public Criteria andAccedeClientMoneyAndroidGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("accede_client_money_android >=", value, "accedeClientMoneyAndroid");
            return (Criteria) this;
        }

        public Criteria andAccedeClientMoneyAndroidLessThan(BigDecimal value) {
            addCriterion("accede_client_money_android <", value, "accedeClientMoneyAndroid");
            return (Criteria) this;
        }

        public Criteria andAccedeClientMoneyAndroidLessThanOrEqualTo(BigDecimal value) {
            addCriterion("accede_client_money_android <=", value, "accedeClientMoneyAndroid");
            return (Criteria) this;
        }

        public Criteria andAccedeClientMoneyAndroidIn(List<BigDecimal> values) {
            addCriterion("accede_client_money_android in", values, "accedeClientMoneyAndroid");
            return (Criteria) this;
        }

        public Criteria andAccedeClientMoneyAndroidNotIn(List<BigDecimal> values) {
            addCriterion("accede_client_money_android not in", values, "accedeClientMoneyAndroid");
            return (Criteria) this;
        }

        public Criteria andAccedeClientMoneyAndroidBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("accede_client_money_android between", value1, value2, "accedeClientMoneyAndroid");
            return (Criteria) this;
        }

        public Criteria andAccedeClientMoneyAndroidNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("accede_client_money_android not between", value1, value2, "accedeClientMoneyAndroid");
            return (Criteria) this;
        }

        public Criteria andAccedeClientCountPcIsNull() {
            addCriterion("accede_client_count_pc is null");
            return (Criteria) this;
        }

        public Criteria andAccedeClientCountPcIsNotNull() {
            addCriterion("accede_client_count_pc is not null");
            return (Criteria) this;
        }

        public Criteria andAccedeClientCountPcEqualTo(Integer value) {
            addCriterion("accede_client_count_pc =", value, "accedeClientCountPc");
            return (Criteria) this;
        }

        public Criteria andAccedeClientCountPcNotEqualTo(Integer value) {
            addCriterion("accede_client_count_pc <>", value, "accedeClientCountPc");
            return (Criteria) this;
        }

        public Criteria andAccedeClientCountPcGreaterThan(Integer value) {
            addCriterion("accede_client_count_pc >", value, "accedeClientCountPc");
            return (Criteria) this;
        }

        public Criteria andAccedeClientCountPcGreaterThanOrEqualTo(Integer value) {
            addCriterion("accede_client_count_pc >=", value, "accedeClientCountPc");
            return (Criteria) this;
        }

        public Criteria andAccedeClientCountPcLessThan(Integer value) {
            addCriterion("accede_client_count_pc <", value, "accedeClientCountPc");
            return (Criteria) this;
        }

        public Criteria andAccedeClientCountPcLessThanOrEqualTo(Integer value) {
            addCriterion("accede_client_count_pc <=", value, "accedeClientCountPc");
            return (Criteria) this;
        }

        public Criteria andAccedeClientCountPcIn(List<Integer> values) {
            addCriterion("accede_client_count_pc in", values, "accedeClientCountPc");
            return (Criteria) this;
        }

        public Criteria andAccedeClientCountPcNotIn(List<Integer> values) {
            addCriterion("accede_client_count_pc not in", values, "accedeClientCountPc");
            return (Criteria) this;
        }

        public Criteria andAccedeClientCountPcBetween(Integer value1, Integer value2) {
            addCriterion("accede_client_count_pc between", value1, value2, "accedeClientCountPc");
            return (Criteria) this;
        }

        public Criteria andAccedeClientCountPcNotBetween(Integer value1, Integer value2) {
            addCriterion("accede_client_count_pc not between", value1, value2, "accedeClientCountPc");
            return (Criteria) this;
        }

        public Criteria andAccedeClientCountWeiIsNull() {
            addCriterion("accede_client_count_wei is null");
            return (Criteria) this;
        }

        public Criteria andAccedeClientCountWeiIsNotNull() {
            addCriterion("accede_client_count_wei is not null");
            return (Criteria) this;
        }

        public Criteria andAccedeClientCountWeiEqualTo(Integer value) {
            addCriterion("accede_client_count_wei =", value, "accedeClientCountWei");
            return (Criteria) this;
        }

        public Criteria andAccedeClientCountWeiNotEqualTo(Integer value) {
            addCriterion("accede_client_count_wei <>", value, "accedeClientCountWei");
            return (Criteria) this;
        }

        public Criteria andAccedeClientCountWeiGreaterThan(Integer value) {
            addCriterion("accede_client_count_wei >", value, "accedeClientCountWei");
            return (Criteria) this;
        }

        public Criteria andAccedeClientCountWeiGreaterThanOrEqualTo(Integer value) {
            addCriterion("accede_client_count_wei >=", value, "accedeClientCountWei");
            return (Criteria) this;
        }

        public Criteria andAccedeClientCountWeiLessThan(Integer value) {
            addCriterion("accede_client_count_wei <", value, "accedeClientCountWei");
            return (Criteria) this;
        }

        public Criteria andAccedeClientCountWeiLessThanOrEqualTo(Integer value) {
            addCriterion("accede_client_count_wei <=", value, "accedeClientCountWei");
            return (Criteria) this;
        }

        public Criteria andAccedeClientCountWeiIn(List<Integer> values) {
            addCriterion("accede_client_count_wei in", values, "accedeClientCountWei");
            return (Criteria) this;
        }

        public Criteria andAccedeClientCountWeiNotIn(List<Integer> values) {
            addCriterion("accede_client_count_wei not in", values, "accedeClientCountWei");
            return (Criteria) this;
        }

        public Criteria andAccedeClientCountWeiBetween(Integer value1, Integer value2) {
            addCriterion("accede_client_count_wei between", value1, value2, "accedeClientCountWei");
            return (Criteria) this;
        }

        public Criteria andAccedeClientCountWeiNotBetween(Integer value1, Integer value2) {
            addCriterion("accede_client_count_wei not between", value1, value2, "accedeClientCountWei");
            return (Criteria) this;
        }

        public Criteria andAccedeClientCountIosIsNull() {
            addCriterion("accede_client_count_ios is null");
            return (Criteria) this;
        }

        public Criteria andAccedeClientCountIosIsNotNull() {
            addCriterion("accede_client_count_ios is not null");
            return (Criteria) this;
        }

        public Criteria andAccedeClientCountIosEqualTo(Integer value) {
            addCriterion("accede_client_count_ios =", value, "accedeClientCountIos");
            return (Criteria) this;
        }

        public Criteria andAccedeClientCountIosNotEqualTo(Integer value) {
            addCriterion("accede_client_count_ios <>", value, "accedeClientCountIos");
            return (Criteria) this;
        }

        public Criteria andAccedeClientCountIosGreaterThan(Integer value) {
            addCriterion("accede_client_count_ios >", value, "accedeClientCountIos");
            return (Criteria) this;
        }

        public Criteria andAccedeClientCountIosGreaterThanOrEqualTo(Integer value) {
            addCriterion("accede_client_count_ios >=", value, "accedeClientCountIos");
            return (Criteria) this;
        }

        public Criteria andAccedeClientCountIosLessThan(Integer value) {
            addCriterion("accede_client_count_ios <", value, "accedeClientCountIos");
            return (Criteria) this;
        }

        public Criteria andAccedeClientCountIosLessThanOrEqualTo(Integer value) {
            addCriterion("accede_client_count_ios <=", value, "accedeClientCountIos");
            return (Criteria) this;
        }

        public Criteria andAccedeClientCountIosIn(List<Integer> values) {
            addCriterion("accede_client_count_ios in", values, "accedeClientCountIos");
            return (Criteria) this;
        }

        public Criteria andAccedeClientCountIosNotIn(List<Integer> values) {
            addCriterion("accede_client_count_ios not in", values, "accedeClientCountIos");
            return (Criteria) this;
        }

        public Criteria andAccedeClientCountIosBetween(Integer value1, Integer value2) {
            addCriterion("accede_client_count_ios between", value1, value2, "accedeClientCountIos");
            return (Criteria) this;
        }

        public Criteria andAccedeClientCountIosNotBetween(Integer value1, Integer value2) {
            addCriterion("accede_client_count_ios not between", value1, value2, "accedeClientCountIos");
            return (Criteria) this;
        }

        public Criteria andAccedeClientCountAndroidIsNull() {
            addCriterion("accede_client_count_android is null");
            return (Criteria) this;
        }

        public Criteria andAccedeClientCountAndroidIsNotNull() {
            addCriterion("accede_client_count_android is not null");
            return (Criteria) this;
        }

        public Criteria andAccedeClientCountAndroidEqualTo(Integer value) {
            addCriterion("accede_client_count_android =", value, "accedeClientCountAndroid");
            return (Criteria) this;
        }

        public Criteria andAccedeClientCountAndroidNotEqualTo(Integer value) {
            addCriterion("accede_client_count_android <>", value, "accedeClientCountAndroid");
            return (Criteria) this;
        }

        public Criteria andAccedeClientCountAndroidGreaterThan(Integer value) {
            addCriterion("accede_client_count_android >", value, "accedeClientCountAndroid");
            return (Criteria) this;
        }

        public Criteria andAccedeClientCountAndroidGreaterThanOrEqualTo(Integer value) {
            addCriterion("accede_client_count_android >=", value, "accedeClientCountAndroid");
            return (Criteria) this;
        }

        public Criteria andAccedeClientCountAndroidLessThan(Integer value) {
            addCriterion("accede_client_count_android <", value, "accedeClientCountAndroid");
            return (Criteria) this;
        }

        public Criteria andAccedeClientCountAndroidLessThanOrEqualTo(Integer value) {
            addCriterion("accede_client_count_android <=", value, "accedeClientCountAndroid");
            return (Criteria) this;
        }

        public Criteria andAccedeClientCountAndroidIn(List<Integer> values) {
            addCriterion("accede_client_count_android in", values, "accedeClientCountAndroid");
            return (Criteria) this;
        }

        public Criteria andAccedeClientCountAndroidNotIn(List<Integer> values) {
            addCriterion("accede_client_count_android not in", values, "accedeClientCountAndroid");
            return (Criteria) this;
        }

        public Criteria andAccedeClientCountAndroidBetween(Integer value1, Integer value2) {
            addCriterion("accede_client_count_android between", value1, value2, "accedeClientCountAndroid");
            return (Criteria) this;
        }

        public Criteria andAccedeClientCountAndroidNotBetween(Integer value1, Integer value2) {
            addCriterion("accede_client_count_android not between", value1, value2, "accedeClientCountAndroid");
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