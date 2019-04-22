package com.hyjf.am.config.dao.model.auto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WithdrawRuleConfigExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public WithdrawRuleConfigExample() {
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

        public Criteria andCustomerTypeIsNull() {
            addCriterion("customer_type is null");
            return (Criteria) this;
        }

        public Criteria andCustomerTypeIsNotNull() {
            addCriterion("customer_type is not null");
            return (Criteria) this;
        }

        public Criteria andCustomerTypeEqualTo(Integer value) {
            addCriterion("customer_type =", value, "customerType");
            return (Criteria) this;
        }

        public Criteria andCustomerTypeNotEqualTo(Integer value) {
            addCriterion("customer_type <>", value, "customerType");
            return (Criteria) this;
        }

        public Criteria andCustomerTypeGreaterThan(Integer value) {
            addCriterion("customer_type >", value, "customerType");
            return (Criteria) this;
        }

        public Criteria andCustomerTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("customer_type >=", value, "customerType");
            return (Criteria) this;
        }

        public Criteria andCustomerTypeLessThan(Integer value) {
            addCriterion("customer_type <", value, "customerType");
            return (Criteria) this;
        }

        public Criteria andCustomerTypeLessThanOrEqualTo(Integer value) {
            addCriterion("customer_type <=", value, "customerType");
            return (Criteria) this;
        }

        public Criteria andCustomerTypeIn(List<Integer> values) {
            addCriterion("customer_type in", values, "customerType");
            return (Criteria) this;
        }

        public Criteria andCustomerTypeNotIn(List<Integer> values) {
            addCriterion("customer_type not in", values, "customerType");
            return (Criteria) this;
        }

        public Criteria andCustomerTypeBetween(Integer value1, Integer value2) {
            addCriterion("customer_type between", value1, value2, "customerType");
            return (Criteria) this;
        }

        public Criteria andCustomerTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("customer_type not between", value1, value2, "customerType");
            return (Criteria) this;
        }

        public Criteria andMinMoneyIsNull() {
            addCriterion("min_money is null");
            return (Criteria) this;
        }

        public Criteria andMinMoneyIsNotNull() {
            addCriterion("min_money is not null");
            return (Criteria) this;
        }

        public Criteria andMinMoneyEqualTo(BigDecimal value) {
            addCriterion("min_money =", value, "minMoney");
            return (Criteria) this;
        }

        public Criteria andMinMoneyNotEqualTo(BigDecimal value) {
            addCriterion("min_money <>", value, "minMoney");
            return (Criteria) this;
        }

        public Criteria andMinMoneyGreaterThan(BigDecimal value) {
            addCriterion("min_money >", value, "minMoney");
            return (Criteria) this;
        }

        public Criteria andMinMoneyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("min_money >=", value, "minMoney");
            return (Criteria) this;
        }

        public Criteria andMinMoneyLessThan(BigDecimal value) {
            addCriterion("min_money <", value, "minMoney");
            return (Criteria) this;
        }

        public Criteria andMinMoneyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("min_money <=", value, "minMoney");
            return (Criteria) this;
        }

        public Criteria andMinMoneyIn(List<BigDecimal> values) {
            addCriterion("min_money in", values, "minMoney");
            return (Criteria) this;
        }

        public Criteria andMinMoneyNotIn(List<BigDecimal> values) {
            addCriterion("min_money not in", values, "minMoney");
            return (Criteria) this;
        }

        public Criteria andMinMoneyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("min_money between", value1, value2, "minMoney");
            return (Criteria) this;
        }

        public Criteria andMinMoneyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("min_money not between", value1, value2, "minMoney");
            return (Criteria) this;
        }

        public Criteria andMaxMoneyIsNull() {
            addCriterion("max_money is null");
            return (Criteria) this;
        }

        public Criteria andMaxMoneyIsNotNull() {
            addCriterion("max_money is not null");
            return (Criteria) this;
        }

        public Criteria andMaxMoneyEqualTo(BigDecimal value) {
            addCriterion("max_money =", value, "maxMoney");
            return (Criteria) this;
        }

        public Criteria andMaxMoneyNotEqualTo(BigDecimal value) {
            addCriterion("max_money <>", value, "maxMoney");
            return (Criteria) this;
        }

        public Criteria andMaxMoneyGreaterThan(BigDecimal value) {
            addCriterion("max_money >", value, "maxMoney");
            return (Criteria) this;
        }

        public Criteria andMaxMoneyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("max_money >=", value, "maxMoney");
            return (Criteria) this;
        }

        public Criteria andMaxMoneyLessThan(BigDecimal value) {
            addCriterion("max_money <", value, "maxMoney");
            return (Criteria) this;
        }

        public Criteria andMaxMoneyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("max_money <=", value, "maxMoney");
            return (Criteria) this;
        }

        public Criteria andMaxMoneyIn(List<BigDecimal> values) {
            addCriterion("max_money in", values, "maxMoney");
            return (Criteria) this;
        }

        public Criteria andMaxMoneyNotIn(List<BigDecimal> values) {
            addCriterion("max_money not in", values, "maxMoney");
            return (Criteria) this;
        }

        public Criteria andMaxMoneyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("max_money between", value1, value2, "maxMoney");
            return (Criteria) this;
        }

        public Criteria andMaxMoneyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("max_money not between", value1, value2, "maxMoney");
            return (Criteria) this;
        }

        public Criteria andStartTimeIsNull() {
            addCriterion("start_time is null");
            return (Criteria) this;
        }

        public Criteria andStartTimeIsNotNull() {
            addCriterion("start_time is not null");
            return (Criteria) this;
        }

        public Criteria andStartTimeEqualTo(String value) {
            addCriterion("start_time =", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotEqualTo(String value) {
            addCriterion("start_time <>", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThan(String value) {
            addCriterion("start_time >", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThanOrEqualTo(String value) {
            addCriterion("start_time >=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThan(String value) {
            addCriterion("start_time <", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThanOrEqualTo(String value) {
            addCriterion("start_time <=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLike(String value) {
            addCriterion("start_time like", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotLike(String value) {
            addCriterion("start_time not like", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeIn(List<String> values) {
            addCriterion("start_time in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotIn(List<String> values) {
            addCriterion("start_time not in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeBetween(String value1, String value2) {
            addCriterion("start_time between", value1, value2, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotBetween(String value1, String value2) {
            addCriterion("start_time not between", value1, value2, "startTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNull() {
            addCriterion("end_time is null");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNotNull() {
            addCriterion("end_time is not null");
            return (Criteria) this;
        }

        public Criteria andEndTimeEqualTo(String value) {
            addCriterion("end_time =", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotEqualTo(String value) {
            addCriterion("end_time <>", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThan(String value) {
            addCriterion("end_time >", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThanOrEqualTo(String value) {
            addCriterion("end_time >=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThan(String value) {
            addCriterion("end_time <", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThanOrEqualTo(String value) {
            addCriterion("end_time <=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLike(String value) {
            addCriterion("end_time like", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotLike(String value) {
            addCriterion("end_time not like", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIn(List<String> values) {
            addCriterion("end_time in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotIn(List<String> values) {
            addCriterion("end_time not in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeBetween(String value1, String value2) {
            addCriterion("end_time between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotBetween(String value1, String value2) {
            addCriterion("end_time not between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andCouldWithdrawIsNull() {
            addCriterion("could_withdraw is null");
            return (Criteria) this;
        }

        public Criteria andCouldWithdrawIsNotNull() {
            addCriterion("could_withdraw is not null");
            return (Criteria) this;
        }

        public Criteria andCouldWithdrawEqualTo(Integer value) {
            addCriterion("could_withdraw =", value, "couldWithdraw");
            return (Criteria) this;
        }

        public Criteria andCouldWithdrawNotEqualTo(Integer value) {
            addCriterion("could_withdraw <>", value, "couldWithdraw");
            return (Criteria) this;
        }

        public Criteria andCouldWithdrawGreaterThan(Integer value) {
            addCriterion("could_withdraw >", value, "couldWithdraw");
            return (Criteria) this;
        }

        public Criteria andCouldWithdrawGreaterThanOrEqualTo(Integer value) {
            addCriterion("could_withdraw >=", value, "couldWithdraw");
            return (Criteria) this;
        }

        public Criteria andCouldWithdrawLessThan(Integer value) {
            addCriterion("could_withdraw <", value, "couldWithdraw");
            return (Criteria) this;
        }

        public Criteria andCouldWithdrawLessThanOrEqualTo(Integer value) {
            addCriterion("could_withdraw <=", value, "couldWithdraw");
            return (Criteria) this;
        }

        public Criteria andCouldWithdrawIn(List<Integer> values) {
            addCriterion("could_withdraw in", values, "couldWithdraw");
            return (Criteria) this;
        }

        public Criteria andCouldWithdrawNotIn(List<Integer> values) {
            addCriterion("could_withdraw not in", values, "couldWithdraw");
            return (Criteria) this;
        }

        public Criteria andCouldWithdrawBetween(Integer value1, Integer value2) {
            addCriterion("could_withdraw between", value1, value2, "couldWithdraw");
            return (Criteria) this;
        }

        public Criteria andCouldWithdrawNotBetween(Integer value1, Integer value2) {
            addCriterion("could_withdraw not between", value1, value2, "couldWithdraw");
            return (Criteria) this;
        }

        public Criteria andRouteCodeIsNull() {
            addCriterion("route_code is null");
            return (Criteria) this;
        }

        public Criteria andRouteCodeIsNotNull() {
            addCriterion("route_code is not null");
            return (Criteria) this;
        }

        public Criteria andRouteCodeEqualTo(String value) {
            addCriterion("route_code =", value, "routeCode");
            return (Criteria) this;
        }

        public Criteria andRouteCodeNotEqualTo(String value) {
            addCriterion("route_code <>", value, "routeCode");
            return (Criteria) this;
        }

        public Criteria andRouteCodeGreaterThan(String value) {
            addCriterion("route_code >", value, "routeCode");
            return (Criteria) this;
        }

        public Criteria andRouteCodeGreaterThanOrEqualTo(String value) {
            addCriterion("route_code >=", value, "routeCode");
            return (Criteria) this;
        }

        public Criteria andRouteCodeLessThan(String value) {
            addCriterion("route_code <", value, "routeCode");
            return (Criteria) this;
        }

        public Criteria andRouteCodeLessThanOrEqualTo(String value) {
            addCriterion("route_code <=", value, "routeCode");
            return (Criteria) this;
        }

        public Criteria andRouteCodeLike(String value) {
            addCriterion("route_code like", value, "routeCode");
            return (Criteria) this;
        }

        public Criteria andRouteCodeNotLike(String value) {
            addCriterion("route_code not like", value, "routeCode");
            return (Criteria) this;
        }

        public Criteria andRouteCodeIn(List<String> values) {
            addCriterion("route_code in", values, "routeCode");
            return (Criteria) this;
        }

        public Criteria andRouteCodeNotIn(List<String> values) {
            addCriterion("route_code not in", values, "routeCode");
            return (Criteria) this;
        }

        public Criteria andRouteCodeBetween(String value1, String value2) {
            addCriterion("route_code between", value1, value2, "routeCode");
            return (Criteria) this;
        }

        public Criteria andRouteCodeNotBetween(String value1, String value2) {
            addCriterion("route_code not between", value1, value2, "routeCode");
            return (Criteria) this;
        }

        public Criteria andPayAllianceCodeIsNull() {
            addCriterion("pay_alliance_code is null");
            return (Criteria) this;
        }

        public Criteria andPayAllianceCodeIsNotNull() {
            addCriterion("pay_alliance_code is not null");
            return (Criteria) this;
        }

        public Criteria andPayAllianceCodeEqualTo(Integer value) {
            addCriterion("pay_alliance_code =", value, "payAllianceCode");
            return (Criteria) this;
        }

        public Criteria andPayAllianceCodeNotEqualTo(Integer value) {
            addCriterion("pay_alliance_code <>", value, "payAllianceCode");
            return (Criteria) this;
        }

        public Criteria andPayAllianceCodeGreaterThan(Integer value) {
            addCriterion("pay_alliance_code >", value, "payAllianceCode");
            return (Criteria) this;
        }

        public Criteria andPayAllianceCodeGreaterThanOrEqualTo(Integer value) {
            addCriterion("pay_alliance_code >=", value, "payAllianceCode");
            return (Criteria) this;
        }

        public Criteria andPayAllianceCodeLessThan(Integer value) {
            addCriterion("pay_alliance_code <", value, "payAllianceCode");
            return (Criteria) this;
        }

        public Criteria andPayAllianceCodeLessThanOrEqualTo(Integer value) {
            addCriterion("pay_alliance_code <=", value, "payAllianceCode");
            return (Criteria) this;
        }

        public Criteria andPayAllianceCodeIn(List<Integer> values) {
            addCriterion("pay_alliance_code in", values, "payAllianceCode");
            return (Criteria) this;
        }

        public Criteria andPayAllianceCodeNotIn(List<Integer> values) {
            addCriterion("pay_alliance_code not in", values, "payAllianceCode");
            return (Criteria) this;
        }

        public Criteria andPayAllianceCodeBetween(Integer value1, Integer value2) {
            addCriterion("pay_alliance_code between", value1, value2, "payAllianceCode");
            return (Criteria) this;
        }

        public Criteria andPayAllianceCodeNotBetween(Integer value1, Integer value2) {
            addCriterion("pay_alliance_code not between", value1, value2, "payAllianceCode");
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

        public Criteria andCreateByIsNull() {
            addCriterion("create_by is null");
            return (Criteria) this;
        }

        public Criteria andCreateByIsNotNull() {
            addCriterion("create_by is not null");
            return (Criteria) this;
        }

        public Criteria andCreateByEqualTo(String value) {
            addCriterion("create_by =", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotEqualTo(String value) {
            addCriterion("create_by <>", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByGreaterThan(String value) {
            addCriterion("create_by >", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByGreaterThanOrEqualTo(String value) {
            addCriterion("create_by >=", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByLessThan(String value) {
            addCriterion("create_by <", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByLessThanOrEqualTo(String value) {
            addCriterion("create_by <=", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByLike(String value) {
            addCriterion("create_by like", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotLike(String value) {
            addCriterion("create_by not like", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByIn(List<String> values) {
            addCriterion("create_by in", values, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotIn(List<String> values) {
            addCriterion("create_by not in", values, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByBetween(String value1, String value2) {
            addCriterion("create_by between", value1, value2, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotBetween(String value1, String value2) {
            addCriterion("create_by not between", value1, value2, "createBy");
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

        public Criteria andUpdateByIsNull() {
            addCriterion("update_by is null");
            return (Criteria) this;
        }

        public Criteria andUpdateByIsNotNull() {
            addCriterion("update_by is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateByEqualTo(String value) {
            addCriterion("update_by =", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByNotEqualTo(String value) {
            addCriterion("update_by <>", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByGreaterThan(String value) {
            addCriterion("update_by >", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByGreaterThanOrEqualTo(String value) {
            addCriterion("update_by >=", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByLessThan(String value) {
            addCriterion("update_by <", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByLessThanOrEqualTo(String value) {
            addCriterion("update_by <=", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByLike(String value) {
            addCriterion("update_by like", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByNotLike(String value) {
            addCriterion("update_by not like", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByIn(List<String> values) {
            addCriterion("update_by in", values, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByNotIn(List<String> values) {
            addCriterion("update_by not in", values, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByBetween(String value1, String value2) {
            addCriterion("update_by between", value1, value2, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByNotBetween(String value1, String value2) {
            addCriterion("update_by not between", value1, value2, "updateBy");
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

        public Criteria andIsHolidayIsNull() {
            addCriterion("is_holiday is null");
            return (Criteria) this;
        }

        public Criteria andIsHolidayIsNotNull() {
            addCriterion("is_holiday is not null");
            return (Criteria) this;
        }

        public Criteria andIsHolidayEqualTo(Integer value) {
            addCriterion("is_holiday =", value, "isHoliday");
            return (Criteria) this;
        }

        public Criteria andIsHolidayNotEqualTo(Integer value) {
            addCriterion("is_holiday <>", value, "isHoliday");
            return (Criteria) this;
        }

        public Criteria andIsHolidayGreaterThan(Integer value) {
            addCriterion("is_holiday >", value, "isHoliday");
            return (Criteria) this;
        }

        public Criteria andIsHolidayGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_holiday >=", value, "isHoliday");
            return (Criteria) this;
        }

        public Criteria andIsHolidayLessThan(Integer value) {
            addCriterion("is_holiday <", value, "isHoliday");
            return (Criteria) this;
        }

        public Criteria andIsHolidayLessThanOrEqualTo(Integer value) {
            addCriterion("is_holiday <=", value, "isHoliday");
            return (Criteria) this;
        }

        public Criteria andIsHolidayIn(List<Integer> values) {
            addCriterion("is_holiday in", values, "isHoliday");
            return (Criteria) this;
        }

        public Criteria andIsHolidayNotIn(List<Integer> values) {
            addCriterion("is_holiday not in", values, "isHoliday");
            return (Criteria) this;
        }

        public Criteria andIsHolidayBetween(Integer value1, Integer value2) {
            addCriterion("is_holiday between", value1, value2, "isHoliday");
            return (Criteria) this;
        }

        public Criteria andIsHolidayNotBetween(Integer value1, Integer value2) {
            addCriterion("is_holiday not between", value1, value2, "isHoliday");
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