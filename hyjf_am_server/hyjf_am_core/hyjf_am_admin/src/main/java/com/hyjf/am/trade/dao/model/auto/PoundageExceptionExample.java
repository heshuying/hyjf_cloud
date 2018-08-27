package com.hyjf.am.trade.dao.model.auto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PoundageExceptionExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public PoundageExceptionExample() {
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

        public Criteria andLedgerAmountIsNull() {
            addCriterion("ledger_amount is null");
            return (Criteria) this;
        }

        public Criteria andLedgerAmountIsNotNull() {
            addCriterion("ledger_amount is not null");
            return (Criteria) this;
        }

        public Criteria andLedgerAmountEqualTo(BigDecimal value) {
            addCriterion("ledger_amount =", value, "ledgerAmount");
            return (Criteria) this;
        }

        public Criteria andLedgerAmountNotEqualTo(BigDecimal value) {
            addCriterion("ledger_amount <>", value, "ledgerAmount");
            return (Criteria) this;
        }

        public Criteria andLedgerAmountGreaterThan(BigDecimal value) {
            addCriterion("ledger_amount >", value, "ledgerAmount");
            return (Criteria) this;
        }

        public Criteria andLedgerAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ledger_amount >=", value, "ledgerAmount");
            return (Criteria) this;
        }

        public Criteria andLedgerAmountLessThan(BigDecimal value) {
            addCriterion("ledger_amount <", value, "ledgerAmount");
            return (Criteria) this;
        }

        public Criteria andLedgerAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ledger_amount <=", value, "ledgerAmount");
            return (Criteria) this;
        }

        public Criteria andLedgerAmountIn(List<BigDecimal> values) {
            addCriterion("ledger_amount in", values, "ledgerAmount");
            return (Criteria) this;
        }

        public Criteria andLedgerAmountNotIn(List<BigDecimal> values) {
            addCriterion("ledger_amount not in", values, "ledgerAmount");
            return (Criteria) this;
        }

        public Criteria andLedgerAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ledger_amount between", value1, value2, "ledgerAmount");
            return (Criteria) this;
        }

        public Criteria andLedgerAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ledger_amount not between", value1, value2, "ledgerAmount");
            return (Criteria) this;
        }

        public Criteria andPoundageIdIsNull() {
            addCriterion("poundage_id is null");
            return (Criteria) this;
        }

        public Criteria andPoundageIdIsNotNull() {
            addCriterion("poundage_id is not null");
            return (Criteria) this;
        }

        public Criteria andPoundageIdEqualTo(Integer value) {
            addCriterion("poundage_id =", value, "poundageId");
            return (Criteria) this;
        }

        public Criteria andPoundageIdNotEqualTo(Integer value) {
            addCriterion("poundage_id <>", value, "poundageId");
            return (Criteria) this;
        }

        public Criteria andPoundageIdGreaterThan(Integer value) {
            addCriterion("poundage_id >", value, "poundageId");
            return (Criteria) this;
        }

        public Criteria andPoundageIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("poundage_id >=", value, "poundageId");
            return (Criteria) this;
        }

        public Criteria andPoundageIdLessThan(Integer value) {
            addCriterion("poundage_id <", value, "poundageId");
            return (Criteria) this;
        }

        public Criteria andPoundageIdLessThanOrEqualTo(Integer value) {
            addCriterion("poundage_id <=", value, "poundageId");
            return (Criteria) this;
        }

        public Criteria andPoundageIdIn(List<Integer> values) {
            addCriterion("poundage_id in", values, "poundageId");
            return (Criteria) this;
        }

        public Criteria andPoundageIdNotIn(List<Integer> values) {
            addCriterion("poundage_id not in", values, "poundageId");
            return (Criteria) this;
        }

        public Criteria andPoundageIdBetween(Integer value1, Integer value2) {
            addCriterion("poundage_id between", value1, value2, "poundageId");
            return (Criteria) this;
        }

        public Criteria andPoundageIdNotBetween(Integer value1, Integer value2) {
            addCriterion("poundage_id not between", value1, value2, "poundageId");
            return (Criteria) this;
        }

        public Criteria andLedgerIdIsNull() {
            addCriterion("ledger_id is null");
            return (Criteria) this;
        }

        public Criteria andLedgerIdIsNotNull() {
            addCriterion("ledger_id is not null");
            return (Criteria) this;
        }

        public Criteria andLedgerIdEqualTo(Integer value) {
            addCriterion("ledger_id =", value, "ledgerId");
            return (Criteria) this;
        }

        public Criteria andLedgerIdNotEqualTo(Integer value) {
            addCriterion("ledger_id <>", value, "ledgerId");
            return (Criteria) this;
        }

        public Criteria andLedgerIdGreaterThan(Integer value) {
            addCriterion("ledger_id >", value, "ledgerId");
            return (Criteria) this;
        }

        public Criteria andLedgerIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("ledger_id >=", value, "ledgerId");
            return (Criteria) this;
        }

        public Criteria andLedgerIdLessThan(Integer value) {
            addCriterion("ledger_id <", value, "ledgerId");
            return (Criteria) this;
        }

        public Criteria andLedgerIdLessThanOrEqualTo(Integer value) {
            addCriterion("ledger_id <=", value, "ledgerId");
            return (Criteria) this;
        }

        public Criteria andLedgerIdIn(List<Integer> values) {
            addCriterion("ledger_id in", values, "ledgerId");
            return (Criteria) this;
        }

        public Criteria andLedgerIdNotIn(List<Integer> values) {
            addCriterion("ledger_id not in", values, "ledgerId");
            return (Criteria) this;
        }

        public Criteria andLedgerIdBetween(Integer value1, Integer value2) {
            addCriterion("ledger_id between", value1, value2, "ledgerId");
            return (Criteria) this;
        }

        public Criteria andLedgerIdNotBetween(Integer value1, Integer value2) {
            addCriterion("ledger_id not between", value1, value2, "ledgerId");
            return (Criteria) this;
        }

        public Criteria andPayeeNameIsNull() {
            addCriterion("payee_name is null");
            return (Criteria) this;
        }

        public Criteria andPayeeNameIsNotNull() {
            addCriterion("payee_name is not null");
            return (Criteria) this;
        }

        public Criteria andPayeeNameEqualTo(String value) {
            addCriterion("payee_name =", value, "payeeName");
            return (Criteria) this;
        }

        public Criteria andPayeeNameNotEqualTo(String value) {
            addCriterion("payee_name <>", value, "payeeName");
            return (Criteria) this;
        }

        public Criteria andPayeeNameGreaterThan(String value) {
            addCriterion("payee_name >", value, "payeeName");
            return (Criteria) this;
        }

        public Criteria andPayeeNameGreaterThanOrEqualTo(String value) {
            addCriterion("payee_name >=", value, "payeeName");
            return (Criteria) this;
        }

        public Criteria andPayeeNameLessThan(String value) {
            addCriterion("payee_name <", value, "payeeName");
            return (Criteria) this;
        }

        public Criteria andPayeeNameLessThanOrEqualTo(String value) {
            addCriterion("payee_name <=", value, "payeeName");
            return (Criteria) this;
        }

        public Criteria andPayeeNameLike(String value) {
            addCriterion("payee_name like", value, "payeeName");
            return (Criteria) this;
        }

        public Criteria andPayeeNameNotLike(String value) {
            addCriterion("payee_name not like", value, "payeeName");
            return (Criteria) this;
        }

        public Criteria andPayeeNameIn(List<String> values) {
            addCriterion("payee_name in", values, "payeeName");
            return (Criteria) this;
        }

        public Criteria andPayeeNameNotIn(List<String> values) {
            addCriterion("payee_name not in", values, "payeeName");
            return (Criteria) this;
        }

        public Criteria andPayeeNameBetween(String value1, String value2) {
            addCriterion("payee_name between", value1, value2, "payeeName");
            return (Criteria) this;
        }

        public Criteria andPayeeNameNotBetween(String value1, String value2) {
            addCriterion("payee_name not between", value1, value2, "payeeName");
            return (Criteria) this;
        }

        public Criteria andLedgerStatusIsNull() {
            addCriterion("ledger_status is null");
            return (Criteria) this;
        }

        public Criteria andLedgerStatusIsNotNull() {
            addCriterion("ledger_status is not null");
            return (Criteria) this;
        }

        public Criteria andLedgerStatusEqualTo(Integer value) {
            addCriterion("ledger_status =", value, "ledgerStatus");
            return (Criteria) this;
        }

        public Criteria andLedgerStatusNotEqualTo(Integer value) {
            addCriterion("ledger_status <>", value, "ledgerStatus");
            return (Criteria) this;
        }

        public Criteria andLedgerStatusGreaterThan(Integer value) {
            addCriterion("ledger_status >", value, "ledgerStatus");
            return (Criteria) this;
        }

        public Criteria andLedgerStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("ledger_status >=", value, "ledgerStatus");
            return (Criteria) this;
        }

        public Criteria andLedgerStatusLessThan(Integer value) {
            addCriterion("ledger_status <", value, "ledgerStatus");
            return (Criteria) this;
        }

        public Criteria andLedgerStatusLessThanOrEqualTo(Integer value) {
            addCriterion("ledger_status <=", value, "ledgerStatus");
            return (Criteria) this;
        }

        public Criteria andLedgerStatusIn(List<Integer> values) {
            addCriterion("ledger_status in", values, "ledgerStatus");
            return (Criteria) this;
        }

        public Criteria andLedgerStatusNotIn(List<Integer> values) {
            addCriterion("ledger_status not in", values, "ledgerStatus");
            return (Criteria) this;
        }

        public Criteria andLedgerStatusBetween(Integer value1, Integer value2) {
            addCriterion("ledger_status between", value1, value2, "ledgerStatus");
            return (Criteria) this;
        }

        public Criteria andLedgerStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("ledger_status not between", value1, value2, "ledgerStatus");
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