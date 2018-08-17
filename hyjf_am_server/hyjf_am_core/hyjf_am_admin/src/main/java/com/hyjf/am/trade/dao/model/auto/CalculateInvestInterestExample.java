package com.hyjf.am.trade.dao.model.auto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CalculateInvestInterestExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public CalculateInvestInterestExample() {
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

        public Criteria andTenderSumIsNull() {
            addCriterion("tender_sum is null");
            return (Criteria) this;
        }

        public Criteria andTenderSumIsNotNull() {
            addCriterion("tender_sum is not null");
            return (Criteria) this;
        }

        public Criteria andTenderSumEqualTo(BigDecimal value) {
            addCriterion("tender_sum =", value, "tenderSum");
            return (Criteria) this;
        }

        public Criteria andTenderSumNotEqualTo(BigDecimal value) {
            addCriterion("tender_sum <>", value, "tenderSum");
            return (Criteria) this;
        }

        public Criteria andTenderSumGreaterThan(BigDecimal value) {
            addCriterion("tender_sum >", value, "tenderSum");
            return (Criteria) this;
        }

        public Criteria andTenderSumGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("tender_sum >=", value, "tenderSum");
            return (Criteria) this;
        }

        public Criteria andTenderSumLessThan(BigDecimal value) {
            addCriterion("tender_sum <", value, "tenderSum");
            return (Criteria) this;
        }

        public Criteria andTenderSumLessThanOrEqualTo(BigDecimal value) {
            addCriterion("tender_sum <=", value, "tenderSum");
            return (Criteria) this;
        }

        public Criteria andTenderSumIn(List<BigDecimal> values) {
            addCriterion("tender_sum in", values, "tenderSum");
            return (Criteria) this;
        }

        public Criteria andTenderSumNotIn(List<BigDecimal> values) {
            addCriterion("tender_sum not in", values, "tenderSum");
            return (Criteria) this;
        }

        public Criteria andTenderSumBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("tender_sum between", value1, value2, "tenderSum");
            return (Criteria) this;
        }

        public Criteria andTenderSumNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("tender_sum not between", value1, value2, "tenderSum");
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

        public Criteria andSevenDayTenderSumIsNull() {
            addCriterion("seven_day_tender_sum is null");
            return (Criteria) this;
        }

        public Criteria andSevenDayTenderSumIsNotNull() {
            addCriterion("seven_day_tender_sum is not null");
            return (Criteria) this;
        }

        public Criteria andSevenDayTenderSumEqualTo(BigDecimal value) {
            addCriterion("seven_day_tender_sum =", value, "sevenDayTenderSum");
            return (Criteria) this;
        }

        public Criteria andSevenDayTenderSumNotEqualTo(BigDecimal value) {
            addCriterion("seven_day_tender_sum <>", value, "sevenDayTenderSum");
            return (Criteria) this;
        }

        public Criteria andSevenDayTenderSumGreaterThan(BigDecimal value) {
            addCriterion("seven_day_tender_sum >", value, "sevenDayTenderSum");
            return (Criteria) this;
        }

        public Criteria andSevenDayTenderSumGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("seven_day_tender_sum >=", value, "sevenDayTenderSum");
            return (Criteria) this;
        }

        public Criteria andSevenDayTenderSumLessThan(BigDecimal value) {
            addCriterion("seven_day_tender_sum <", value, "sevenDayTenderSum");
            return (Criteria) this;
        }

        public Criteria andSevenDayTenderSumLessThanOrEqualTo(BigDecimal value) {
            addCriterion("seven_day_tender_sum <=", value, "sevenDayTenderSum");
            return (Criteria) this;
        }

        public Criteria andSevenDayTenderSumIn(List<BigDecimal> values) {
            addCriterion("seven_day_tender_sum in", values, "sevenDayTenderSum");
            return (Criteria) this;
        }

        public Criteria andSevenDayTenderSumNotIn(List<BigDecimal> values) {
            addCriterion("seven_day_tender_sum not in", values, "sevenDayTenderSum");
            return (Criteria) this;
        }

        public Criteria andSevenDayTenderSumBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("seven_day_tender_sum between", value1, value2, "sevenDayTenderSum");
            return (Criteria) this;
        }

        public Criteria andSevenDayTenderSumNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("seven_day_tender_sum not between", value1, value2, "sevenDayTenderSum");
            return (Criteria) this;
        }

        public Criteria andSevenDayInterestSumIsNull() {
            addCriterion("seven_day_interest_sum is null");
            return (Criteria) this;
        }

        public Criteria andSevenDayInterestSumIsNotNull() {
            addCriterion("seven_day_interest_sum is not null");
            return (Criteria) this;
        }

        public Criteria andSevenDayInterestSumEqualTo(BigDecimal value) {
            addCriterion("seven_day_interest_sum =", value, "sevenDayInterestSum");
            return (Criteria) this;
        }

        public Criteria andSevenDayInterestSumNotEqualTo(BigDecimal value) {
            addCriterion("seven_day_interest_sum <>", value, "sevenDayInterestSum");
            return (Criteria) this;
        }

        public Criteria andSevenDayInterestSumGreaterThan(BigDecimal value) {
            addCriterion("seven_day_interest_sum >", value, "sevenDayInterestSum");
            return (Criteria) this;
        }

        public Criteria andSevenDayInterestSumGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("seven_day_interest_sum >=", value, "sevenDayInterestSum");
            return (Criteria) this;
        }

        public Criteria andSevenDayInterestSumLessThan(BigDecimal value) {
            addCriterion("seven_day_interest_sum <", value, "sevenDayInterestSum");
            return (Criteria) this;
        }

        public Criteria andSevenDayInterestSumLessThanOrEqualTo(BigDecimal value) {
            addCriterion("seven_day_interest_sum <=", value, "sevenDayInterestSum");
            return (Criteria) this;
        }

        public Criteria andSevenDayInterestSumIn(List<BigDecimal> values) {
            addCriterion("seven_day_interest_sum in", values, "sevenDayInterestSum");
            return (Criteria) this;
        }

        public Criteria andSevenDayInterestSumNotIn(List<BigDecimal> values) {
            addCriterion("seven_day_interest_sum not in", values, "sevenDayInterestSum");
            return (Criteria) this;
        }

        public Criteria andSevenDayInterestSumBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("seven_day_interest_sum between", value1, value2, "sevenDayInterestSum");
            return (Criteria) this;
        }

        public Criteria andSevenDayInterestSumNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("seven_day_interest_sum not between", value1, value2, "sevenDayInterestSum");
            return (Criteria) this;
        }

        public Criteria andBorrowZeroOneIsNull() {
            addCriterion("borrow_zero_one is null");
            return (Criteria) this;
        }

        public Criteria andBorrowZeroOneIsNotNull() {
            addCriterion("borrow_zero_one is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowZeroOneEqualTo(Integer value) {
            addCriterion("borrow_zero_one =", value, "borrowZeroOne");
            return (Criteria) this;
        }

        public Criteria andBorrowZeroOneNotEqualTo(Integer value) {
            addCriterion("borrow_zero_one <>", value, "borrowZeroOne");
            return (Criteria) this;
        }

        public Criteria andBorrowZeroOneGreaterThan(Integer value) {
            addCriterion("borrow_zero_one >", value, "borrowZeroOne");
            return (Criteria) this;
        }

        public Criteria andBorrowZeroOneGreaterThanOrEqualTo(Integer value) {
            addCriterion("borrow_zero_one >=", value, "borrowZeroOne");
            return (Criteria) this;
        }

        public Criteria andBorrowZeroOneLessThan(Integer value) {
            addCriterion("borrow_zero_one <", value, "borrowZeroOne");
            return (Criteria) this;
        }

        public Criteria andBorrowZeroOneLessThanOrEqualTo(Integer value) {
            addCriterion("borrow_zero_one <=", value, "borrowZeroOne");
            return (Criteria) this;
        }

        public Criteria andBorrowZeroOneIn(List<Integer> values) {
            addCriterion("borrow_zero_one in", values, "borrowZeroOne");
            return (Criteria) this;
        }

        public Criteria andBorrowZeroOneNotIn(List<Integer> values) {
            addCriterion("borrow_zero_one not in", values, "borrowZeroOne");
            return (Criteria) this;
        }

        public Criteria andBorrowZeroOneBetween(Integer value1, Integer value2) {
            addCriterion("borrow_zero_one between", value1, value2, "borrowZeroOne");
            return (Criteria) this;
        }

        public Criteria andBorrowZeroOneNotBetween(Integer value1, Integer value2) {
            addCriterion("borrow_zero_one not between", value1, value2, "borrowZeroOne");
            return (Criteria) this;
        }

        public Criteria andBorrowOneThreeIsNull() {
            addCriterion("borrow_one_three is null");
            return (Criteria) this;
        }

        public Criteria andBorrowOneThreeIsNotNull() {
            addCriterion("borrow_one_three is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowOneThreeEqualTo(Integer value) {
            addCriterion("borrow_one_three =", value, "borrowOneThree");
            return (Criteria) this;
        }

        public Criteria andBorrowOneThreeNotEqualTo(Integer value) {
            addCriterion("borrow_one_three <>", value, "borrowOneThree");
            return (Criteria) this;
        }

        public Criteria andBorrowOneThreeGreaterThan(Integer value) {
            addCriterion("borrow_one_three >", value, "borrowOneThree");
            return (Criteria) this;
        }

        public Criteria andBorrowOneThreeGreaterThanOrEqualTo(Integer value) {
            addCriterion("borrow_one_three >=", value, "borrowOneThree");
            return (Criteria) this;
        }

        public Criteria andBorrowOneThreeLessThan(Integer value) {
            addCriterion("borrow_one_three <", value, "borrowOneThree");
            return (Criteria) this;
        }

        public Criteria andBorrowOneThreeLessThanOrEqualTo(Integer value) {
            addCriterion("borrow_one_three <=", value, "borrowOneThree");
            return (Criteria) this;
        }

        public Criteria andBorrowOneThreeIn(List<Integer> values) {
            addCriterion("borrow_one_three in", values, "borrowOneThree");
            return (Criteria) this;
        }

        public Criteria andBorrowOneThreeNotIn(List<Integer> values) {
            addCriterion("borrow_one_three not in", values, "borrowOneThree");
            return (Criteria) this;
        }

        public Criteria andBorrowOneThreeBetween(Integer value1, Integer value2) {
            addCriterion("borrow_one_three between", value1, value2, "borrowOneThree");
            return (Criteria) this;
        }

        public Criteria andBorrowOneThreeNotBetween(Integer value1, Integer value2) {
            addCriterion("borrow_one_three not between", value1, value2, "borrowOneThree");
            return (Criteria) this;
        }

        public Criteria andBorrowThreeSixIsNull() {
            addCriterion("borrow_three_six is null");
            return (Criteria) this;
        }

        public Criteria andBorrowThreeSixIsNotNull() {
            addCriterion("borrow_three_six is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowThreeSixEqualTo(Integer value) {
            addCriterion("borrow_three_six =", value, "borrowThreeSix");
            return (Criteria) this;
        }

        public Criteria andBorrowThreeSixNotEqualTo(Integer value) {
            addCriterion("borrow_three_six <>", value, "borrowThreeSix");
            return (Criteria) this;
        }

        public Criteria andBorrowThreeSixGreaterThan(Integer value) {
            addCriterion("borrow_three_six >", value, "borrowThreeSix");
            return (Criteria) this;
        }

        public Criteria andBorrowThreeSixGreaterThanOrEqualTo(Integer value) {
            addCriterion("borrow_three_six >=", value, "borrowThreeSix");
            return (Criteria) this;
        }

        public Criteria andBorrowThreeSixLessThan(Integer value) {
            addCriterion("borrow_three_six <", value, "borrowThreeSix");
            return (Criteria) this;
        }

        public Criteria andBorrowThreeSixLessThanOrEqualTo(Integer value) {
            addCriterion("borrow_three_six <=", value, "borrowThreeSix");
            return (Criteria) this;
        }

        public Criteria andBorrowThreeSixIn(List<Integer> values) {
            addCriterion("borrow_three_six in", values, "borrowThreeSix");
            return (Criteria) this;
        }

        public Criteria andBorrowThreeSixNotIn(List<Integer> values) {
            addCriterion("borrow_three_six not in", values, "borrowThreeSix");
            return (Criteria) this;
        }

        public Criteria andBorrowThreeSixBetween(Integer value1, Integer value2) {
            addCriterion("borrow_three_six between", value1, value2, "borrowThreeSix");
            return (Criteria) this;
        }

        public Criteria andBorrowThreeSixNotBetween(Integer value1, Integer value2) {
            addCriterion("borrow_three_six not between", value1, value2, "borrowThreeSix");
            return (Criteria) this;
        }

        public Criteria andBorrowSixTwelveIsNull() {
            addCriterion("borrow_six_twelve is null");
            return (Criteria) this;
        }

        public Criteria andBorrowSixTwelveIsNotNull() {
            addCriterion("borrow_six_twelve is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowSixTwelveEqualTo(Integer value) {
            addCriterion("borrow_six_twelve =", value, "borrowSixTwelve");
            return (Criteria) this;
        }

        public Criteria andBorrowSixTwelveNotEqualTo(Integer value) {
            addCriterion("borrow_six_twelve <>", value, "borrowSixTwelve");
            return (Criteria) this;
        }

        public Criteria andBorrowSixTwelveGreaterThan(Integer value) {
            addCriterion("borrow_six_twelve >", value, "borrowSixTwelve");
            return (Criteria) this;
        }

        public Criteria andBorrowSixTwelveGreaterThanOrEqualTo(Integer value) {
            addCriterion("borrow_six_twelve >=", value, "borrowSixTwelve");
            return (Criteria) this;
        }

        public Criteria andBorrowSixTwelveLessThan(Integer value) {
            addCriterion("borrow_six_twelve <", value, "borrowSixTwelve");
            return (Criteria) this;
        }

        public Criteria andBorrowSixTwelveLessThanOrEqualTo(Integer value) {
            addCriterion("borrow_six_twelve <=", value, "borrowSixTwelve");
            return (Criteria) this;
        }

        public Criteria andBorrowSixTwelveIn(List<Integer> values) {
            addCriterion("borrow_six_twelve in", values, "borrowSixTwelve");
            return (Criteria) this;
        }

        public Criteria andBorrowSixTwelveNotIn(List<Integer> values) {
            addCriterion("borrow_six_twelve not in", values, "borrowSixTwelve");
            return (Criteria) this;
        }

        public Criteria andBorrowSixTwelveBetween(Integer value1, Integer value2) {
            addCriterion("borrow_six_twelve between", value1, value2, "borrowSixTwelve");
            return (Criteria) this;
        }

        public Criteria andBorrowSixTwelveNotBetween(Integer value1, Integer value2) {
            addCriterion("borrow_six_twelve not between", value1, value2, "borrowSixTwelve");
            return (Criteria) this;
        }

        public Criteria andBorrowTwelveUpIsNull() {
            addCriterion("borrow_twelve_up is null");
            return (Criteria) this;
        }

        public Criteria andBorrowTwelveUpIsNotNull() {
            addCriterion("borrow_twelve_up is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowTwelveUpEqualTo(Integer value) {
            addCriterion("borrow_twelve_up =", value, "borrowTwelveUp");
            return (Criteria) this;
        }

        public Criteria andBorrowTwelveUpNotEqualTo(Integer value) {
            addCriterion("borrow_twelve_up <>", value, "borrowTwelveUp");
            return (Criteria) this;
        }

        public Criteria andBorrowTwelveUpGreaterThan(Integer value) {
            addCriterion("borrow_twelve_up >", value, "borrowTwelveUp");
            return (Criteria) this;
        }

        public Criteria andBorrowTwelveUpGreaterThanOrEqualTo(Integer value) {
            addCriterion("borrow_twelve_up >=", value, "borrowTwelveUp");
            return (Criteria) this;
        }

        public Criteria andBorrowTwelveUpLessThan(Integer value) {
            addCriterion("borrow_twelve_up <", value, "borrowTwelveUp");
            return (Criteria) this;
        }

        public Criteria andBorrowTwelveUpLessThanOrEqualTo(Integer value) {
            addCriterion("borrow_twelve_up <=", value, "borrowTwelveUp");
            return (Criteria) this;
        }

        public Criteria andBorrowTwelveUpIn(List<Integer> values) {
            addCriterion("borrow_twelve_up in", values, "borrowTwelveUp");
            return (Criteria) this;
        }

        public Criteria andBorrowTwelveUpNotIn(List<Integer> values) {
            addCriterion("borrow_twelve_up not in", values, "borrowTwelveUp");
            return (Criteria) this;
        }

        public Criteria andBorrowTwelveUpBetween(Integer value1, Integer value2) {
            addCriterion("borrow_twelve_up between", value1, value2, "borrowTwelveUp");
            return (Criteria) this;
        }

        public Criteria andBorrowTwelveUpNotBetween(Integer value1, Integer value2) {
            addCriterion("borrow_twelve_up not between", value1, value2, "borrowTwelveUp");
            return (Criteria) this;
        }

        public Criteria andInvestOneDownIsNull() {
            addCriterion("invest_one_down is null");
            return (Criteria) this;
        }

        public Criteria andInvestOneDownIsNotNull() {
            addCriterion("invest_one_down is not null");
            return (Criteria) this;
        }

        public Criteria andInvestOneDownEqualTo(Integer value) {
            addCriterion("invest_one_down =", value, "investOneDown");
            return (Criteria) this;
        }

        public Criteria andInvestOneDownNotEqualTo(Integer value) {
            addCriterion("invest_one_down <>", value, "investOneDown");
            return (Criteria) this;
        }

        public Criteria andInvestOneDownGreaterThan(Integer value) {
            addCriterion("invest_one_down >", value, "investOneDown");
            return (Criteria) this;
        }

        public Criteria andInvestOneDownGreaterThanOrEqualTo(Integer value) {
            addCriterion("invest_one_down >=", value, "investOneDown");
            return (Criteria) this;
        }

        public Criteria andInvestOneDownLessThan(Integer value) {
            addCriterion("invest_one_down <", value, "investOneDown");
            return (Criteria) this;
        }

        public Criteria andInvestOneDownLessThanOrEqualTo(Integer value) {
            addCriterion("invest_one_down <=", value, "investOneDown");
            return (Criteria) this;
        }

        public Criteria andInvestOneDownIn(List<Integer> values) {
            addCriterion("invest_one_down in", values, "investOneDown");
            return (Criteria) this;
        }

        public Criteria andInvestOneDownNotIn(List<Integer> values) {
            addCriterion("invest_one_down not in", values, "investOneDown");
            return (Criteria) this;
        }

        public Criteria andInvestOneDownBetween(Integer value1, Integer value2) {
            addCriterion("invest_one_down between", value1, value2, "investOneDown");
            return (Criteria) this;
        }

        public Criteria andInvestOneDownNotBetween(Integer value1, Integer value2) {
            addCriterion("invest_one_down not between", value1, value2, "investOneDown");
            return (Criteria) this;
        }

        public Criteria andInvestOneFiveIsNull() {
            addCriterion("invest_one_five is null");
            return (Criteria) this;
        }

        public Criteria andInvestOneFiveIsNotNull() {
            addCriterion("invest_one_five is not null");
            return (Criteria) this;
        }

        public Criteria andInvestOneFiveEqualTo(Integer value) {
            addCriterion("invest_one_five =", value, "investOneFive");
            return (Criteria) this;
        }

        public Criteria andInvestOneFiveNotEqualTo(Integer value) {
            addCriterion("invest_one_five <>", value, "investOneFive");
            return (Criteria) this;
        }

        public Criteria andInvestOneFiveGreaterThan(Integer value) {
            addCriterion("invest_one_five >", value, "investOneFive");
            return (Criteria) this;
        }

        public Criteria andInvestOneFiveGreaterThanOrEqualTo(Integer value) {
            addCriterion("invest_one_five >=", value, "investOneFive");
            return (Criteria) this;
        }

        public Criteria andInvestOneFiveLessThan(Integer value) {
            addCriterion("invest_one_five <", value, "investOneFive");
            return (Criteria) this;
        }

        public Criteria andInvestOneFiveLessThanOrEqualTo(Integer value) {
            addCriterion("invest_one_five <=", value, "investOneFive");
            return (Criteria) this;
        }

        public Criteria andInvestOneFiveIn(List<Integer> values) {
            addCriterion("invest_one_five in", values, "investOneFive");
            return (Criteria) this;
        }

        public Criteria andInvestOneFiveNotIn(List<Integer> values) {
            addCriterion("invest_one_five not in", values, "investOneFive");
            return (Criteria) this;
        }

        public Criteria andInvestOneFiveBetween(Integer value1, Integer value2) {
            addCriterion("invest_one_five between", value1, value2, "investOneFive");
            return (Criteria) this;
        }

        public Criteria andInvestOneFiveNotBetween(Integer value1, Integer value2) {
            addCriterion("invest_one_five not between", value1, value2, "investOneFive");
            return (Criteria) this;
        }

        public Criteria andInvestFiveTenIsNull() {
            addCriterion("invest_five_ten is null");
            return (Criteria) this;
        }

        public Criteria andInvestFiveTenIsNotNull() {
            addCriterion("invest_five_ten is not null");
            return (Criteria) this;
        }

        public Criteria andInvestFiveTenEqualTo(Integer value) {
            addCriterion("invest_five_ten =", value, "investFiveTen");
            return (Criteria) this;
        }

        public Criteria andInvestFiveTenNotEqualTo(Integer value) {
            addCriterion("invest_five_ten <>", value, "investFiveTen");
            return (Criteria) this;
        }

        public Criteria andInvestFiveTenGreaterThan(Integer value) {
            addCriterion("invest_five_ten >", value, "investFiveTen");
            return (Criteria) this;
        }

        public Criteria andInvestFiveTenGreaterThanOrEqualTo(Integer value) {
            addCriterion("invest_five_ten >=", value, "investFiveTen");
            return (Criteria) this;
        }

        public Criteria andInvestFiveTenLessThan(Integer value) {
            addCriterion("invest_five_ten <", value, "investFiveTen");
            return (Criteria) this;
        }

        public Criteria andInvestFiveTenLessThanOrEqualTo(Integer value) {
            addCriterion("invest_five_ten <=", value, "investFiveTen");
            return (Criteria) this;
        }

        public Criteria andInvestFiveTenIn(List<Integer> values) {
            addCriterion("invest_five_ten in", values, "investFiveTen");
            return (Criteria) this;
        }

        public Criteria andInvestFiveTenNotIn(List<Integer> values) {
            addCriterion("invest_five_ten not in", values, "investFiveTen");
            return (Criteria) this;
        }

        public Criteria andInvestFiveTenBetween(Integer value1, Integer value2) {
            addCriterion("invest_five_ten between", value1, value2, "investFiveTen");
            return (Criteria) this;
        }

        public Criteria andInvestFiveTenNotBetween(Integer value1, Integer value2) {
            addCriterion("invest_five_ten not between", value1, value2, "investFiveTen");
            return (Criteria) this;
        }

        public Criteria andInvestTenFifthIsNull() {
            addCriterion("invest_ten_fifth is null");
            return (Criteria) this;
        }

        public Criteria andInvestTenFifthIsNotNull() {
            addCriterion("invest_ten_fifth is not null");
            return (Criteria) this;
        }

        public Criteria andInvestTenFifthEqualTo(Integer value) {
            addCriterion("invest_ten_fifth =", value, "investTenFifth");
            return (Criteria) this;
        }

        public Criteria andInvestTenFifthNotEqualTo(Integer value) {
            addCriterion("invest_ten_fifth <>", value, "investTenFifth");
            return (Criteria) this;
        }

        public Criteria andInvestTenFifthGreaterThan(Integer value) {
            addCriterion("invest_ten_fifth >", value, "investTenFifth");
            return (Criteria) this;
        }

        public Criteria andInvestTenFifthGreaterThanOrEqualTo(Integer value) {
            addCriterion("invest_ten_fifth >=", value, "investTenFifth");
            return (Criteria) this;
        }

        public Criteria andInvestTenFifthLessThan(Integer value) {
            addCriterion("invest_ten_fifth <", value, "investTenFifth");
            return (Criteria) this;
        }

        public Criteria andInvestTenFifthLessThanOrEqualTo(Integer value) {
            addCriterion("invest_ten_fifth <=", value, "investTenFifth");
            return (Criteria) this;
        }

        public Criteria andInvestTenFifthIn(List<Integer> values) {
            addCriterion("invest_ten_fifth in", values, "investTenFifth");
            return (Criteria) this;
        }

        public Criteria andInvestTenFifthNotIn(List<Integer> values) {
            addCriterion("invest_ten_fifth not in", values, "investTenFifth");
            return (Criteria) this;
        }

        public Criteria andInvestTenFifthBetween(Integer value1, Integer value2) {
            addCriterion("invest_ten_fifth between", value1, value2, "investTenFifth");
            return (Criteria) this;
        }

        public Criteria andInvestTenFifthNotBetween(Integer value1, Integer value2) {
            addCriterion("invest_ten_fifth not between", value1, value2, "investTenFifth");
            return (Criteria) this;
        }

        public Criteria andInvestFifthUpIsNull() {
            addCriterion("invest_fifth_up is null");
            return (Criteria) this;
        }

        public Criteria andInvestFifthUpIsNotNull() {
            addCriterion("invest_fifth_up is not null");
            return (Criteria) this;
        }

        public Criteria andInvestFifthUpEqualTo(Integer value) {
            addCriterion("invest_fifth_up =", value, "investFifthUp");
            return (Criteria) this;
        }

        public Criteria andInvestFifthUpNotEqualTo(Integer value) {
            addCriterion("invest_fifth_up <>", value, "investFifthUp");
            return (Criteria) this;
        }

        public Criteria andInvestFifthUpGreaterThan(Integer value) {
            addCriterion("invest_fifth_up >", value, "investFifthUp");
            return (Criteria) this;
        }

        public Criteria andInvestFifthUpGreaterThanOrEqualTo(Integer value) {
            addCriterion("invest_fifth_up >=", value, "investFifthUp");
            return (Criteria) this;
        }

        public Criteria andInvestFifthUpLessThan(Integer value) {
            addCriterion("invest_fifth_up <", value, "investFifthUp");
            return (Criteria) this;
        }

        public Criteria andInvestFifthUpLessThanOrEqualTo(Integer value) {
            addCriterion("invest_fifth_up <=", value, "investFifthUp");
            return (Criteria) this;
        }

        public Criteria andInvestFifthUpIn(List<Integer> values) {
            addCriterion("invest_fifth_up in", values, "investFifthUp");
            return (Criteria) this;
        }

        public Criteria andInvestFifthUpNotIn(List<Integer> values) {
            addCriterion("invest_fifth_up not in", values, "investFifthUp");
            return (Criteria) this;
        }

        public Criteria andInvestFifthUpBetween(Integer value1, Integer value2) {
            addCriterion("invest_fifth_up between", value1, value2, "investFifthUp");
            return (Criteria) this;
        }

        public Criteria andInvestFifthUpNotBetween(Integer value1, Integer value2) {
            addCriterion("invest_fifth_up not between", value1, value2, "investFifthUp");
            return (Criteria) this;
        }

        public Criteria andBailMoneyIsNull() {
            addCriterion("bail_money is null");
            return (Criteria) this;
        }

        public Criteria andBailMoneyIsNotNull() {
            addCriterion("bail_money is not null");
            return (Criteria) this;
        }

        public Criteria andBailMoneyEqualTo(BigDecimal value) {
            addCriterion("bail_money =", value, "bailMoney");
            return (Criteria) this;
        }

        public Criteria andBailMoneyNotEqualTo(BigDecimal value) {
            addCriterion("bail_money <>", value, "bailMoney");
            return (Criteria) this;
        }

        public Criteria andBailMoneyGreaterThan(BigDecimal value) {
            addCriterion("bail_money >", value, "bailMoney");
            return (Criteria) this;
        }

        public Criteria andBailMoneyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("bail_money >=", value, "bailMoney");
            return (Criteria) this;
        }

        public Criteria andBailMoneyLessThan(BigDecimal value) {
            addCriterion("bail_money <", value, "bailMoney");
            return (Criteria) this;
        }

        public Criteria andBailMoneyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("bail_money <=", value, "bailMoney");
            return (Criteria) this;
        }

        public Criteria andBailMoneyIn(List<BigDecimal> values) {
            addCriterion("bail_money in", values, "bailMoney");
            return (Criteria) this;
        }

        public Criteria andBailMoneyNotIn(List<BigDecimal> values) {
            addCriterion("bail_money not in", values, "bailMoney");
            return (Criteria) this;
        }

        public Criteria andBailMoneyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("bail_money between", value1, value2, "bailMoney");
            return (Criteria) this;
        }

        public Criteria andBailMoneyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("bail_money not between", value1, value2, "bailMoney");
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