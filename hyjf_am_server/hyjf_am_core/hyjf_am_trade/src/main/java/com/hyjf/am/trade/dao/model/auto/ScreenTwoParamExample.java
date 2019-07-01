package com.hyjf.am.trade.dao.model.auto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ScreenTwoParamExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public ScreenTwoParamExample() {
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

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
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

        public Criteria andFlagIsNull() {
            addCriterion("flag is null");
            return (Criteria) this;
        }

        public Criteria andFlagIsNotNull() {
            addCriterion("flag is not null");
            return (Criteria) this;
        }

        public Criteria andFlagEqualTo(Integer value) {
            addCriterion("flag =", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagNotEqualTo(Integer value) {
            addCriterion("flag <>", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagGreaterThan(Integer value) {
            addCriterion("flag >", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("flag >=", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagLessThan(Integer value) {
            addCriterion("flag <", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagLessThanOrEqualTo(Integer value) {
            addCriterion("flag <=", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagIn(List<Integer> values) {
            addCriterion("flag in", values, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagNotIn(List<Integer> values) {
            addCriterion("flag not in", values, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagBetween(Integer value1, Integer value2) {
            addCriterion("flag between", value1, value2, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("flag not between", value1, value2, "flag");
            return (Criteria) this;
        }

        public Criteria andCustomerNameIsNull() {
            addCriterion("customer_name is null");
            return (Criteria) this;
        }

        public Criteria andCustomerNameIsNotNull() {
            addCriterion("customer_name is not null");
            return (Criteria) this;
        }

        public Criteria andCustomerNameEqualTo(String value) {
            addCriterion("customer_name =", value, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameNotEqualTo(String value) {
            addCriterion("customer_name <>", value, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameGreaterThan(String value) {
            addCriterion("customer_name >", value, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameGreaterThanOrEqualTo(String value) {
            addCriterion("customer_name >=", value, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameLessThan(String value) {
            addCriterion("customer_name <", value, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameLessThanOrEqualTo(String value) {
            addCriterion("customer_name <=", value, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameLike(String value) {
            addCriterion("customer_name like", value, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameNotLike(String value) {
            addCriterion("customer_name not like", value, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameIn(List<String> values) {
            addCriterion("customer_name in", values, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameNotIn(List<String> values) {
            addCriterion("customer_name not in", values, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameBetween(String value1, String value2) {
            addCriterion("customer_name between", value1, value2, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameNotBetween(String value1, String value2) {
            addCriterion("customer_name not between", value1, value2, "customerName");
            return (Criteria) this;
        }

        public Criteria andQueryTimeIsNull() {
            addCriterion("query_time is null");
            return (Criteria) this;
        }

        public Criteria andQueryTimeIsNotNull() {
            addCriterion("query_time is not null");
            return (Criteria) this;
        }

        public Criteria andQueryTimeEqualTo(Date value) {
            addCriterionForJDBCDate("query_time =", value, "queryTime");
            return (Criteria) this;
        }

        public Criteria andQueryTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("query_time <>", value, "queryTime");
            return (Criteria) this;
        }

        public Criteria andQueryTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("query_time >", value, "queryTime");
            return (Criteria) this;
        }

        public Criteria andQueryTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("query_time >=", value, "queryTime");
            return (Criteria) this;
        }

        public Criteria andQueryTimeLessThan(Date value) {
            addCriterionForJDBCDate("query_time <", value, "queryTime");
            return (Criteria) this;
        }

        public Criteria andQueryTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("query_time <=", value, "queryTime");
            return (Criteria) this;
        }

        public Criteria andQueryTimeIn(List<Date> values) {
            addCriterionForJDBCDate("query_time in", values, "queryTime");
            return (Criteria) this;
        }

        public Criteria andQueryTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("query_time not in", values, "queryTime");
            return (Criteria) this;
        }

        public Criteria andQueryTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("query_time between", value1, value2, "queryTime");
            return (Criteria) this;
        }

        public Criteria andQueryTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("query_time not between", value1, value2, "queryTime");
            return (Criteria) this;
        }

        public Criteria andCapitalIncreaseIsNull() {
            addCriterion("capital_increase is null");
            return (Criteria) this;
        }

        public Criteria andCapitalIncreaseIsNotNull() {
            addCriterion("capital_increase is not null");
            return (Criteria) this;
        }

        public Criteria andCapitalIncreaseEqualTo(BigDecimal value) {
            addCriterion("capital_increase =", value, "capitalIncrease");
            return (Criteria) this;
        }

        public Criteria andCapitalIncreaseNotEqualTo(BigDecimal value) {
            addCriterion("capital_increase <>", value, "capitalIncrease");
            return (Criteria) this;
        }

        public Criteria andCapitalIncreaseGreaterThan(BigDecimal value) {
            addCriterion("capital_increase >", value, "capitalIncrease");
            return (Criteria) this;
        }

        public Criteria andCapitalIncreaseGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("capital_increase >=", value, "capitalIncrease");
            return (Criteria) this;
        }

        public Criteria andCapitalIncreaseLessThan(BigDecimal value) {
            addCriterion("capital_increase <", value, "capitalIncrease");
            return (Criteria) this;
        }

        public Criteria andCapitalIncreaseLessThanOrEqualTo(BigDecimal value) {
            addCriterion("capital_increase <=", value, "capitalIncrease");
            return (Criteria) this;
        }

        public Criteria andCapitalIncreaseIn(List<BigDecimal> values) {
            addCriterion("capital_increase in", values, "capitalIncrease");
            return (Criteria) this;
        }

        public Criteria andCapitalIncreaseNotIn(List<BigDecimal> values) {
            addCriterion("capital_increase not in", values, "capitalIncrease");
            return (Criteria) this;
        }

        public Criteria andCapitalIncreaseBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("capital_increase between", value1, value2, "capitalIncrease");
            return (Criteria) this;
        }

        public Criteria andCapitalIncreaseNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("capital_increase not between", value1, value2, "capitalIncrease");
            return (Criteria) this;
        }

        public Criteria andCashWithdrawalRateIsNull() {
            addCriterion("cash_withdrawal_rate is null");
            return (Criteria) this;
        }

        public Criteria andCashWithdrawalRateIsNotNull() {
            addCriterion("cash_withdrawal_rate is not null");
            return (Criteria) this;
        }

        public Criteria andCashWithdrawalRateEqualTo(BigDecimal value) {
            addCriterion("cash_withdrawal_rate =", value, "cashWithdrawalRate");
            return (Criteria) this;
        }

        public Criteria andCashWithdrawalRateNotEqualTo(BigDecimal value) {
            addCriterion("cash_withdrawal_rate <>", value, "cashWithdrawalRate");
            return (Criteria) this;
        }

        public Criteria andCashWithdrawalRateGreaterThan(BigDecimal value) {
            addCriterion("cash_withdrawal_rate >", value, "cashWithdrawalRate");
            return (Criteria) this;
        }

        public Criteria andCashWithdrawalRateGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("cash_withdrawal_rate >=", value, "cashWithdrawalRate");
            return (Criteria) this;
        }

        public Criteria andCashWithdrawalRateLessThan(BigDecimal value) {
            addCriterion("cash_withdrawal_rate <", value, "cashWithdrawalRate");
            return (Criteria) this;
        }

        public Criteria andCashWithdrawalRateLessThanOrEqualTo(BigDecimal value) {
            addCriterion("cash_withdrawal_rate <=", value, "cashWithdrawalRate");
            return (Criteria) this;
        }

        public Criteria andCashWithdrawalRateIn(List<BigDecimal> values) {
            addCriterion("cash_withdrawal_rate in", values, "cashWithdrawalRate");
            return (Criteria) this;
        }

        public Criteria andCashWithdrawalRateNotIn(List<BigDecimal> values) {
            addCriterion("cash_withdrawal_rate not in", values, "cashWithdrawalRate");
            return (Criteria) this;
        }

        public Criteria andCashWithdrawalRateBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("cash_withdrawal_rate between", value1, value2, "cashWithdrawalRate");
            return (Criteria) this;
        }

        public Criteria andCashWithdrawalRateNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("cash_withdrawal_rate not between", value1, value2, "cashWithdrawalRate");
            return (Criteria) this;
        }

        public Criteria andNowBalanceIsNull() {
            addCriterion("now_balance is null");
            return (Criteria) this;
        }

        public Criteria andNowBalanceIsNotNull() {
            addCriterion("now_balance is not null");
            return (Criteria) this;
        }

        public Criteria andNowBalanceEqualTo(BigDecimal value) {
            addCriterion("now_balance =", value, "nowBalance");
            return (Criteria) this;
        }

        public Criteria andNowBalanceNotEqualTo(BigDecimal value) {
            addCriterion("now_balance <>", value, "nowBalance");
            return (Criteria) this;
        }

        public Criteria andNowBalanceGreaterThan(BigDecimal value) {
            addCriterion("now_balance >", value, "nowBalance");
            return (Criteria) this;
        }

        public Criteria andNowBalanceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("now_balance >=", value, "nowBalance");
            return (Criteria) this;
        }

        public Criteria andNowBalanceLessThan(BigDecimal value) {
            addCriterion("now_balance <", value, "nowBalance");
            return (Criteria) this;
        }

        public Criteria andNowBalanceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("now_balance <=", value, "nowBalance");
            return (Criteria) this;
        }

        public Criteria andNowBalanceIn(List<BigDecimal> values) {
            addCriterion("now_balance in", values, "nowBalance");
            return (Criteria) this;
        }

        public Criteria andNowBalanceNotIn(List<BigDecimal> values) {
            addCriterion("now_balance not in", values, "nowBalance");
            return (Criteria) this;
        }

        public Criteria andNowBalanceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("now_balance between", value1, value2, "nowBalance");
            return (Criteria) this;
        }

        public Criteria andNowBalanceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("now_balance not between", value1, value2, "nowBalance");
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