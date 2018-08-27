package com.hyjf.am.trade.dao.model.auto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AccountDirectionalTransferExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public AccountDirectionalTransferExample() {
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

        public Criteria andTurnOutUsernameIsNull() {
            addCriterion("turn_out_username is null");
            return (Criteria) this;
        }

        public Criteria andTurnOutUsernameIsNotNull() {
            addCriterion("turn_out_username is not null");
            return (Criteria) this;
        }

        public Criteria andTurnOutUsernameEqualTo(String value) {
            addCriterion("turn_out_username =", value, "turnOutUsername");
            return (Criteria) this;
        }

        public Criteria andTurnOutUsernameNotEqualTo(String value) {
            addCriterion("turn_out_username <>", value, "turnOutUsername");
            return (Criteria) this;
        }

        public Criteria andTurnOutUsernameGreaterThan(String value) {
            addCriterion("turn_out_username >", value, "turnOutUsername");
            return (Criteria) this;
        }

        public Criteria andTurnOutUsernameGreaterThanOrEqualTo(String value) {
            addCriterion("turn_out_username >=", value, "turnOutUsername");
            return (Criteria) this;
        }

        public Criteria andTurnOutUsernameLessThan(String value) {
            addCriterion("turn_out_username <", value, "turnOutUsername");
            return (Criteria) this;
        }

        public Criteria andTurnOutUsernameLessThanOrEqualTo(String value) {
            addCriterion("turn_out_username <=", value, "turnOutUsername");
            return (Criteria) this;
        }

        public Criteria andTurnOutUsernameLike(String value) {
            addCriterion("turn_out_username like", value, "turnOutUsername");
            return (Criteria) this;
        }

        public Criteria andTurnOutUsernameNotLike(String value) {
            addCriterion("turn_out_username not like", value, "turnOutUsername");
            return (Criteria) this;
        }

        public Criteria andTurnOutUsernameIn(List<String> values) {
            addCriterion("turn_out_username in", values, "turnOutUsername");
            return (Criteria) this;
        }

        public Criteria andTurnOutUsernameNotIn(List<String> values) {
            addCriterion("turn_out_username not in", values, "turnOutUsername");
            return (Criteria) this;
        }

        public Criteria andTurnOutUsernameBetween(String value1, String value2) {
            addCriterion("turn_out_username between", value1, value2, "turnOutUsername");
            return (Criteria) this;
        }

        public Criteria andTurnOutUsernameNotBetween(String value1, String value2) {
            addCriterion("turn_out_username not between", value1, value2, "turnOutUsername");
            return (Criteria) this;
        }

        public Criteria andTurnOutUserIdIsNull() {
            addCriterion("turn_out_user_id is null");
            return (Criteria) this;
        }

        public Criteria andTurnOutUserIdIsNotNull() {
            addCriterion("turn_out_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andTurnOutUserIdEqualTo(Integer value) {
            addCriterion("turn_out_user_id =", value, "turnOutUserId");
            return (Criteria) this;
        }

        public Criteria andTurnOutUserIdNotEqualTo(Integer value) {
            addCriterion("turn_out_user_id <>", value, "turnOutUserId");
            return (Criteria) this;
        }

        public Criteria andTurnOutUserIdGreaterThan(Integer value) {
            addCriterion("turn_out_user_id >", value, "turnOutUserId");
            return (Criteria) this;
        }

        public Criteria andTurnOutUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("turn_out_user_id >=", value, "turnOutUserId");
            return (Criteria) this;
        }

        public Criteria andTurnOutUserIdLessThan(Integer value) {
            addCriterion("turn_out_user_id <", value, "turnOutUserId");
            return (Criteria) this;
        }

        public Criteria andTurnOutUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("turn_out_user_id <=", value, "turnOutUserId");
            return (Criteria) this;
        }

        public Criteria andTurnOutUserIdIn(List<Integer> values) {
            addCriterion("turn_out_user_id in", values, "turnOutUserId");
            return (Criteria) this;
        }

        public Criteria andTurnOutUserIdNotIn(List<Integer> values) {
            addCriterion("turn_out_user_id not in", values, "turnOutUserId");
            return (Criteria) this;
        }

        public Criteria andTurnOutUserIdBetween(Integer value1, Integer value2) {
            addCriterion("turn_out_user_id between", value1, value2, "turnOutUserId");
            return (Criteria) this;
        }

        public Criteria andTurnOutUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("turn_out_user_id not between", value1, value2, "turnOutUserId");
            return (Criteria) this;
        }

        public Criteria andShiftToUsernameIsNull() {
            addCriterion("shift_to_username is null");
            return (Criteria) this;
        }

        public Criteria andShiftToUsernameIsNotNull() {
            addCriterion("shift_to_username is not null");
            return (Criteria) this;
        }

        public Criteria andShiftToUsernameEqualTo(String value) {
            addCriterion("shift_to_username =", value, "shiftToUsername");
            return (Criteria) this;
        }

        public Criteria andShiftToUsernameNotEqualTo(String value) {
            addCriterion("shift_to_username <>", value, "shiftToUsername");
            return (Criteria) this;
        }

        public Criteria andShiftToUsernameGreaterThan(String value) {
            addCriterion("shift_to_username >", value, "shiftToUsername");
            return (Criteria) this;
        }

        public Criteria andShiftToUsernameGreaterThanOrEqualTo(String value) {
            addCriterion("shift_to_username >=", value, "shiftToUsername");
            return (Criteria) this;
        }

        public Criteria andShiftToUsernameLessThan(String value) {
            addCriterion("shift_to_username <", value, "shiftToUsername");
            return (Criteria) this;
        }

        public Criteria andShiftToUsernameLessThanOrEqualTo(String value) {
            addCriterion("shift_to_username <=", value, "shiftToUsername");
            return (Criteria) this;
        }

        public Criteria andShiftToUsernameLike(String value) {
            addCriterion("shift_to_username like", value, "shiftToUsername");
            return (Criteria) this;
        }

        public Criteria andShiftToUsernameNotLike(String value) {
            addCriterion("shift_to_username not like", value, "shiftToUsername");
            return (Criteria) this;
        }

        public Criteria andShiftToUsernameIn(List<String> values) {
            addCriterion("shift_to_username in", values, "shiftToUsername");
            return (Criteria) this;
        }

        public Criteria andShiftToUsernameNotIn(List<String> values) {
            addCriterion("shift_to_username not in", values, "shiftToUsername");
            return (Criteria) this;
        }

        public Criteria andShiftToUsernameBetween(String value1, String value2) {
            addCriterion("shift_to_username between", value1, value2, "shiftToUsername");
            return (Criteria) this;
        }

        public Criteria andShiftToUsernameNotBetween(String value1, String value2) {
            addCriterion("shift_to_username not between", value1, value2, "shiftToUsername");
            return (Criteria) this;
        }

        public Criteria andShiftToUserIdIsNull() {
            addCriterion("shift_to_user_id is null");
            return (Criteria) this;
        }

        public Criteria andShiftToUserIdIsNotNull() {
            addCriterion("shift_to_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andShiftToUserIdEqualTo(Integer value) {
            addCriterion("shift_to_user_id =", value, "shiftToUserId");
            return (Criteria) this;
        }

        public Criteria andShiftToUserIdNotEqualTo(Integer value) {
            addCriterion("shift_to_user_id <>", value, "shiftToUserId");
            return (Criteria) this;
        }

        public Criteria andShiftToUserIdGreaterThan(Integer value) {
            addCriterion("shift_to_user_id >", value, "shiftToUserId");
            return (Criteria) this;
        }

        public Criteria andShiftToUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("shift_to_user_id >=", value, "shiftToUserId");
            return (Criteria) this;
        }

        public Criteria andShiftToUserIdLessThan(Integer value) {
            addCriterion("shift_to_user_id <", value, "shiftToUserId");
            return (Criteria) this;
        }

        public Criteria andShiftToUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("shift_to_user_id <=", value, "shiftToUserId");
            return (Criteria) this;
        }

        public Criteria andShiftToUserIdIn(List<Integer> values) {
            addCriterion("shift_to_user_id in", values, "shiftToUserId");
            return (Criteria) this;
        }

        public Criteria andShiftToUserIdNotIn(List<Integer> values) {
            addCriterion("shift_to_user_id not in", values, "shiftToUserId");
            return (Criteria) this;
        }

        public Criteria andShiftToUserIdBetween(Integer value1, Integer value2) {
            addCriterion("shift_to_user_id between", value1, value2, "shiftToUserId");
            return (Criteria) this;
        }

        public Criteria andShiftToUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("shift_to_user_id not between", value1, value2, "shiftToUserId");
            return (Criteria) this;
        }

        public Criteria andTransferAccountsMoneyIsNull() {
            addCriterion("transfer_accounts_money is null");
            return (Criteria) this;
        }

        public Criteria andTransferAccountsMoneyIsNotNull() {
            addCriterion("transfer_accounts_money is not null");
            return (Criteria) this;
        }

        public Criteria andTransferAccountsMoneyEqualTo(BigDecimal value) {
            addCriterion("transfer_accounts_money =", value, "transferAccountsMoney");
            return (Criteria) this;
        }

        public Criteria andTransferAccountsMoneyNotEqualTo(BigDecimal value) {
            addCriterion("transfer_accounts_money <>", value, "transferAccountsMoney");
            return (Criteria) this;
        }

        public Criteria andTransferAccountsMoneyGreaterThan(BigDecimal value) {
            addCriterion("transfer_accounts_money >", value, "transferAccountsMoney");
            return (Criteria) this;
        }

        public Criteria andTransferAccountsMoneyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("transfer_accounts_money >=", value, "transferAccountsMoney");
            return (Criteria) this;
        }

        public Criteria andTransferAccountsMoneyLessThan(BigDecimal value) {
            addCriterion("transfer_accounts_money <", value, "transferAccountsMoney");
            return (Criteria) this;
        }

        public Criteria andTransferAccountsMoneyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("transfer_accounts_money <=", value, "transferAccountsMoney");
            return (Criteria) this;
        }

        public Criteria andTransferAccountsMoneyIn(List<BigDecimal> values) {
            addCriterion("transfer_accounts_money in", values, "transferAccountsMoney");
            return (Criteria) this;
        }

        public Criteria andTransferAccountsMoneyNotIn(List<BigDecimal> values) {
            addCriterion("transfer_accounts_money not in", values, "transferAccountsMoney");
            return (Criteria) this;
        }

        public Criteria andTransferAccountsMoneyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("transfer_accounts_money between", value1, value2, "transferAccountsMoney");
            return (Criteria) this;
        }

        public Criteria andTransferAccountsMoneyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("transfer_accounts_money not between", value1, value2, "transferAccountsMoney");
            return (Criteria) this;
        }

        public Criteria andTransferAccountsStateIsNull() {
            addCriterion("transfer_accounts_state is null");
            return (Criteria) this;
        }

        public Criteria andTransferAccountsStateIsNotNull() {
            addCriterion("transfer_accounts_state is not null");
            return (Criteria) this;
        }

        public Criteria andTransferAccountsStateEqualTo(Integer value) {
            addCriterion("transfer_accounts_state =", value, "transferAccountsState");
            return (Criteria) this;
        }

        public Criteria andTransferAccountsStateNotEqualTo(Integer value) {
            addCriterion("transfer_accounts_state <>", value, "transferAccountsState");
            return (Criteria) this;
        }

        public Criteria andTransferAccountsStateGreaterThan(Integer value) {
            addCriterion("transfer_accounts_state >", value, "transferAccountsState");
            return (Criteria) this;
        }

        public Criteria andTransferAccountsStateGreaterThanOrEqualTo(Integer value) {
            addCriterion("transfer_accounts_state >=", value, "transferAccountsState");
            return (Criteria) this;
        }

        public Criteria andTransferAccountsStateLessThan(Integer value) {
            addCriterion("transfer_accounts_state <", value, "transferAccountsState");
            return (Criteria) this;
        }

        public Criteria andTransferAccountsStateLessThanOrEqualTo(Integer value) {
            addCriterion("transfer_accounts_state <=", value, "transferAccountsState");
            return (Criteria) this;
        }

        public Criteria andTransferAccountsStateIn(List<Integer> values) {
            addCriterion("transfer_accounts_state in", values, "transferAccountsState");
            return (Criteria) this;
        }

        public Criteria andTransferAccountsStateNotIn(List<Integer> values) {
            addCriterion("transfer_accounts_state not in", values, "transferAccountsState");
            return (Criteria) this;
        }

        public Criteria andTransferAccountsStateBetween(Integer value1, Integer value2) {
            addCriterion("transfer_accounts_state between", value1, value2, "transferAccountsState");
            return (Criteria) this;
        }

        public Criteria andTransferAccountsStateNotBetween(Integer value1, Integer value2) {
            addCriterion("transfer_accounts_state not between", value1, value2, "transferAccountsState");
            return (Criteria) this;
        }

        public Criteria andTransferAccountsTimeIsNull() {
            addCriterion("transfer_accounts_time is null");
            return (Criteria) this;
        }

        public Criteria andTransferAccountsTimeIsNotNull() {
            addCriterion("transfer_accounts_time is not null");
            return (Criteria) this;
        }

        public Criteria andTransferAccountsTimeEqualTo(Date value) {
            addCriterion("transfer_accounts_time =", value, "transferAccountsTime");
            return (Criteria) this;
        }

        public Criteria andTransferAccountsTimeNotEqualTo(Date value) {
            addCriterion("transfer_accounts_time <>", value, "transferAccountsTime");
            return (Criteria) this;
        }

        public Criteria andTransferAccountsTimeGreaterThan(Date value) {
            addCriterion("transfer_accounts_time >", value, "transferAccountsTime");
            return (Criteria) this;
        }

        public Criteria andTransferAccountsTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("transfer_accounts_time >=", value, "transferAccountsTime");
            return (Criteria) this;
        }

        public Criteria andTransferAccountsTimeLessThan(Date value) {
            addCriterion("transfer_accounts_time <", value, "transferAccountsTime");
            return (Criteria) this;
        }

        public Criteria andTransferAccountsTimeLessThanOrEqualTo(Date value) {
            addCriterion("transfer_accounts_time <=", value, "transferAccountsTime");
            return (Criteria) this;
        }

        public Criteria andTransferAccountsTimeIn(List<Date> values) {
            addCriterion("transfer_accounts_time in", values, "transferAccountsTime");
            return (Criteria) this;
        }

        public Criteria andTransferAccountsTimeNotIn(List<Date> values) {
            addCriterion("transfer_accounts_time not in", values, "transferAccountsTime");
            return (Criteria) this;
        }

        public Criteria andTransferAccountsTimeBetween(Date value1, Date value2) {
            addCriterion("transfer_accounts_time between", value1, value2, "transferAccountsTime");
            return (Criteria) this;
        }

        public Criteria andTransferAccountsTimeNotBetween(Date value1, Date value2) {
            addCriterion("transfer_accounts_time not between", value1, value2, "transferAccountsTime");
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