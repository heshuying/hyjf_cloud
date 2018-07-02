package com.hyjf.am.trade.dao.model.auto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public ProductInfoExample() {
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

        public Criteria andInCountIsNull() {
            addCriterion("in_count is null");
            return (Criteria) this;
        }

        public Criteria andInCountIsNotNull() {
            addCriterion("in_count is not null");
            return (Criteria) this;
        }

        public Criteria andInCountEqualTo(Integer value) {
            addCriterion("in_count =", value, "inCount");
            return (Criteria) this;
        }

        public Criteria andInCountNotEqualTo(Integer value) {
            addCriterion("in_count <>", value, "inCount");
            return (Criteria) this;
        }

        public Criteria andInCountGreaterThan(Integer value) {
            addCriterion("in_count >", value, "inCount");
            return (Criteria) this;
        }

        public Criteria andInCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("in_count >=", value, "inCount");
            return (Criteria) this;
        }

        public Criteria andInCountLessThan(Integer value) {
            addCriterion("in_count <", value, "inCount");
            return (Criteria) this;
        }

        public Criteria andInCountLessThanOrEqualTo(Integer value) {
            addCriterion("in_count <=", value, "inCount");
            return (Criteria) this;
        }

        public Criteria andInCountIn(List<Integer> values) {
            addCriterion("in_count in", values, "inCount");
            return (Criteria) this;
        }

        public Criteria andInCountNotIn(List<Integer> values) {
            addCriterion("in_count not in", values, "inCount");
            return (Criteria) this;
        }

        public Criteria andInCountBetween(Integer value1, Integer value2) {
            addCriterion("in_count between", value1, value2, "inCount");
            return (Criteria) this;
        }

        public Criteria andInCountNotBetween(Integer value1, Integer value2) {
            addCriterion("in_count not between", value1, value2, "inCount");
            return (Criteria) this;
        }

        public Criteria andOutCountIsNull() {
            addCriterion("out_count is null");
            return (Criteria) this;
        }

        public Criteria andOutCountIsNotNull() {
            addCriterion("out_count is not null");
            return (Criteria) this;
        }

        public Criteria andOutCountEqualTo(Integer value) {
            addCriterion("out_count =", value, "outCount");
            return (Criteria) this;
        }

        public Criteria andOutCountNotEqualTo(Integer value) {
            addCriterion("out_count <>", value, "outCount");
            return (Criteria) this;
        }

        public Criteria andOutCountGreaterThan(Integer value) {
            addCriterion("out_count >", value, "outCount");
            return (Criteria) this;
        }

        public Criteria andOutCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("out_count >=", value, "outCount");
            return (Criteria) this;
        }

        public Criteria andOutCountLessThan(Integer value) {
            addCriterion("out_count <", value, "outCount");
            return (Criteria) this;
        }

        public Criteria andOutCountLessThanOrEqualTo(Integer value) {
            addCriterion("out_count <=", value, "outCount");
            return (Criteria) this;
        }

        public Criteria andOutCountIn(List<Integer> values) {
            addCriterion("out_count in", values, "outCount");
            return (Criteria) this;
        }

        public Criteria andOutCountNotIn(List<Integer> values) {
            addCriterion("out_count not in", values, "outCount");
            return (Criteria) this;
        }

        public Criteria andOutCountBetween(Integer value1, Integer value2) {
            addCriterion("out_count between", value1, value2, "outCount");
            return (Criteria) this;
        }

        public Criteria andOutCountNotBetween(Integer value1, Integer value2) {
            addCriterion("out_count not between", value1, value2, "outCount");
            return (Criteria) this;
        }

        public Criteria andInAmountIsNull() {
            addCriterion("in_amount is null");
            return (Criteria) this;
        }

        public Criteria andInAmountIsNotNull() {
            addCriterion("in_amount is not null");
            return (Criteria) this;
        }

        public Criteria andInAmountEqualTo(BigDecimal value) {
            addCriterion("in_amount =", value, "inAmount");
            return (Criteria) this;
        }

        public Criteria andInAmountNotEqualTo(BigDecimal value) {
            addCriterion("in_amount <>", value, "inAmount");
            return (Criteria) this;
        }

        public Criteria andInAmountGreaterThan(BigDecimal value) {
            addCriterion("in_amount >", value, "inAmount");
            return (Criteria) this;
        }

        public Criteria andInAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("in_amount >=", value, "inAmount");
            return (Criteria) this;
        }

        public Criteria andInAmountLessThan(BigDecimal value) {
            addCriterion("in_amount <", value, "inAmount");
            return (Criteria) this;
        }

        public Criteria andInAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("in_amount <=", value, "inAmount");
            return (Criteria) this;
        }

        public Criteria andInAmountIn(List<BigDecimal> values) {
            addCriterion("in_amount in", values, "inAmount");
            return (Criteria) this;
        }

        public Criteria andInAmountNotIn(List<BigDecimal> values) {
            addCriterion("in_amount not in", values, "inAmount");
            return (Criteria) this;
        }

        public Criteria andInAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("in_amount between", value1, value2, "inAmount");
            return (Criteria) this;
        }

        public Criteria andInAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("in_amount not between", value1, value2, "inAmount");
            return (Criteria) this;
        }

        public Criteria andOutAmountIsNull() {
            addCriterion("out_amount is null");
            return (Criteria) this;
        }

        public Criteria andOutAmountIsNotNull() {
            addCriterion("out_amount is not null");
            return (Criteria) this;
        }

        public Criteria andOutAmountEqualTo(BigDecimal value) {
            addCriterion("out_amount =", value, "outAmount");
            return (Criteria) this;
        }

        public Criteria andOutAmountNotEqualTo(BigDecimal value) {
            addCriterion("out_amount <>", value, "outAmount");
            return (Criteria) this;
        }

        public Criteria andOutAmountGreaterThan(BigDecimal value) {
            addCriterion("out_amount >", value, "outAmount");
            return (Criteria) this;
        }

        public Criteria andOutAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("out_amount >=", value, "outAmount");
            return (Criteria) this;
        }

        public Criteria andOutAmountLessThan(BigDecimal value) {
            addCriterion("out_amount <", value, "outAmount");
            return (Criteria) this;
        }

        public Criteria andOutAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("out_amount <=", value, "outAmount");
            return (Criteria) this;
        }

        public Criteria andOutAmountIn(List<BigDecimal> values) {
            addCriterion("out_amount in", values, "outAmount");
            return (Criteria) this;
        }

        public Criteria andOutAmountNotIn(List<BigDecimal> values) {
            addCriterion("out_amount not in", values, "outAmount");
            return (Criteria) this;
        }

        public Criteria andOutAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("out_amount between", value1, value2, "outAmount");
            return (Criteria) this;
        }

        public Criteria andOutAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("out_amount not between", value1, value2, "outAmount");
            return (Criteria) this;
        }

        public Criteria andOutInterestIsNull() {
            addCriterion("out_interest is null");
            return (Criteria) this;
        }

        public Criteria andOutInterestIsNotNull() {
            addCriterion("out_interest is not null");
            return (Criteria) this;
        }

        public Criteria andOutInterestEqualTo(BigDecimal value) {
            addCriterion("out_interest =", value, "outInterest");
            return (Criteria) this;
        }

        public Criteria andOutInterestNotEqualTo(BigDecimal value) {
            addCriterion("out_interest <>", value, "outInterest");
            return (Criteria) this;
        }

        public Criteria andOutInterestGreaterThan(BigDecimal value) {
            addCriterion("out_interest >", value, "outInterest");
            return (Criteria) this;
        }

        public Criteria andOutInterestGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("out_interest >=", value, "outInterest");
            return (Criteria) this;
        }

        public Criteria andOutInterestLessThan(BigDecimal value) {
            addCriterion("out_interest <", value, "outInterest");
            return (Criteria) this;
        }

        public Criteria andOutInterestLessThanOrEqualTo(BigDecimal value) {
            addCriterion("out_interest <=", value, "outInterest");
            return (Criteria) this;
        }

        public Criteria andOutInterestIn(List<BigDecimal> values) {
            addCriterion("out_interest in", values, "outInterest");
            return (Criteria) this;
        }

        public Criteria andOutInterestNotIn(List<BigDecimal> values) {
            addCriterion("out_interest not in", values, "outInterest");
            return (Criteria) this;
        }

        public Criteria andOutInterestBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("out_interest between", value1, value2, "outInterest");
            return (Criteria) this;
        }

        public Criteria andOutInterestNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("out_interest not between", value1, value2, "outInterest");
            return (Criteria) this;
        }

        public Criteria andLoanBalanceIsNull() {
            addCriterion("loan_balance is null");
            return (Criteria) this;
        }

        public Criteria andLoanBalanceIsNotNull() {
            addCriterion("loan_balance is not null");
            return (Criteria) this;
        }

        public Criteria andLoanBalanceEqualTo(BigDecimal value) {
            addCriterion("loan_balance =", value, "loanBalance");
            return (Criteria) this;
        }

        public Criteria andLoanBalanceNotEqualTo(BigDecimal value) {
            addCriterion("loan_balance <>", value, "loanBalance");
            return (Criteria) this;
        }

        public Criteria andLoanBalanceGreaterThan(BigDecimal value) {
            addCriterion("loan_balance >", value, "loanBalance");
            return (Criteria) this;
        }

        public Criteria andLoanBalanceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("loan_balance >=", value, "loanBalance");
            return (Criteria) this;
        }

        public Criteria andLoanBalanceLessThan(BigDecimal value) {
            addCriterion("loan_balance <", value, "loanBalance");
            return (Criteria) this;
        }

        public Criteria andLoanBalanceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("loan_balance <=", value, "loanBalance");
            return (Criteria) this;
        }

        public Criteria andLoanBalanceIn(List<BigDecimal> values) {
            addCriterion("loan_balance in", values, "loanBalance");
            return (Criteria) this;
        }

        public Criteria andLoanBalanceNotIn(List<BigDecimal> values) {
            addCriterion("loan_balance not in", values, "loanBalance");
            return (Criteria) this;
        }

        public Criteria andLoanBalanceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("loan_balance between", value1, value2, "loanBalance");
            return (Criteria) this;
        }

        public Criteria andLoanBalanceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("loan_balance not between", value1, value2, "loanBalance");
            return (Criteria) this;
        }

        public Criteria andInvestAmountIsNull() {
            addCriterion("invest_amount is null");
            return (Criteria) this;
        }

        public Criteria andInvestAmountIsNotNull() {
            addCriterion("invest_amount is not null");
            return (Criteria) this;
        }

        public Criteria andInvestAmountEqualTo(BigDecimal value) {
            addCriterion("invest_amount =", value, "investAmount");
            return (Criteria) this;
        }

        public Criteria andInvestAmountNotEqualTo(BigDecimal value) {
            addCriterion("invest_amount <>", value, "investAmount");
            return (Criteria) this;
        }

        public Criteria andInvestAmountGreaterThan(BigDecimal value) {
            addCriterion("invest_amount >", value, "investAmount");
            return (Criteria) this;
        }

        public Criteria andInvestAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("invest_amount >=", value, "investAmount");
            return (Criteria) this;
        }

        public Criteria andInvestAmountLessThan(BigDecimal value) {
            addCriterion("invest_amount <", value, "investAmount");
            return (Criteria) this;
        }

        public Criteria andInvestAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("invest_amount <=", value, "investAmount");
            return (Criteria) this;
        }

        public Criteria andInvestAmountIn(List<BigDecimal> values) {
            addCriterion("invest_amount in", values, "investAmount");
            return (Criteria) this;
        }

        public Criteria andInvestAmountNotIn(List<BigDecimal> values) {
            addCriterion("invest_amount not in", values, "investAmount");
            return (Criteria) this;
        }

        public Criteria andInvestAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("invest_amount between", value1, value2, "investAmount");
            return (Criteria) this;
        }

        public Criteria andInvestAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("invest_amount not between", value1, value2, "investAmount");
            return (Criteria) this;
        }

        public Criteria andDataDateIsNull() {
            addCriterion("data_date is null");
            return (Criteria) this;
        }

        public Criteria andDataDateIsNotNull() {
            addCriterion("data_date is not null");
            return (Criteria) this;
        }

        public Criteria andDataDateEqualTo(String value) {
            addCriterion("data_date =", value, "dataDate");
            return (Criteria) this;
        }

        public Criteria andDataDateNotEqualTo(String value) {
            addCriterion("data_date <>", value, "dataDate");
            return (Criteria) this;
        }

        public Criteria andDataDateGreaterThan(String value) {
            addCriterion("data_date >", value, "dataDate");
            return (Criteria) this;
        }

        public Criteria andDataDateGreaterThanOrEqualTo(String value) {
            addCriterion("data_date >=", value, "dataDate");
            return (Criteria) this;
        }

        public Criteria andDataDateLessThan(String value) {
            addCriterion("data_date <", value, "dataDate");
            return (Criteria) this;
        }

        public Criteria andDataDateLessThanOrEqualTo(String value) {
            addCriterion("data_date <=", value, "dataDate");
            return (Criteria) this;
        }

        public Criteria andDataDateLike(String value) {
            addCriterion("data_date like", value, "dataDate");
            return (Criteria) this;
        }

        public Criteria andDataDateNotLike(String value) {
            addCriterion("data_date not like", value, "dataDate");
            return (Criteria) this;
        }

        public Criteria andDataDateIn(List<String> values) {
            addCriterion("data_date in", values, "dataDate");
            return (Criteria) this;
        }

        public Criteria andDataDateNotIn(List<String> values) {
            addCriterion("data_date not in", values, "dataDate");
            return (Criteria) this;
        }

        public Criteria andDataDateBetween(String value1, String value2) {
            addCriterion("data_date between", value1, value2, "dataDate");
            return (Criteria) this;
        }

        public Criteria andDataDateNotBetween(String value1, String value2) {
            addCriterion("data_date not between", value1, value2, "dataDate");
            return (Criteria) this;
        }

        public Criteria andDataMonthIsNull() {
            addCriterion("data_month is null");
            return (Criteria) this;
        }

        public Criteria andDataMonthIsNotNull() {
            addCriterion("data_month is not null");
            return (Criteria) this;
        }

        public Criteria andDataMonthEqualTo(String value) {
            addCriterion("data_month =", value, "dataMonth");
            return (Criteria) this;
        }

        public Criteria andDataMonthNotEqualTo(String value) {
            addCriterion("data_month <>", value, "dataMonth");
            return (Criteria) this;
        }

        public Criteria andDataMonthGreaterThan(String value) {
            addCriterion("data_month >", value, "dataMonth");
            return (Criteria) this;
        }

        public Criteria andDataMonthGreaterThanOrEqualTo(String value) {
            addCriterion("data_month >=", value, "dataMonth");
            return (Criteria) this;
        }

        public Criteria andDataMonthLessThan(String value) {
            addCriterion("data_month <", value, "dataMonth");
            return (Criteria) this;
        }

        public Criteria andDataMonthLessThanOrEqualTo(String value) {
            addCriterion("data_month <=", value, "dataMonth");
            return (Criteria) this;
        }

        public Criteria andDataMonthLike(String value) {
            addCriterion("data_month like", value, "dataMonth");
            return (Criteria) this;
        }

        public Criteria andDataMonthNotLike(String value) {
            addCriterion("data_month not like", value, "dataMonth");
            return (Criteria) this;
        }

        public Criteria andDataMonthIn(List<String> values) {
            addCriterion("data_month in", values, "dataMonth");
            return (Criteria) this;
        }

        public Criteria andDataMonthNotIn(List<String> values) {
            addCriterion("data_month not in", values, "dataMonth");
            return (Criteria) this;
        }

        public Criteria andDataMonthBetween(String value1, String value2) {
            addCriterion("data_month between", value1, value2, "dataMonth");
            return (Criteria) this;
        }

        public Criteria andDataMonthNotBetween(String value1, String value2) {
            addCriterion("data_month not between", value1, value2, "dataMonth");
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