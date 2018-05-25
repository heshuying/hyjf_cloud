package com.hyjf.am.user.dao.model.auto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HjhUserAuthExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public HjhUserAuthExample() {
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

        public Criteria andAutoInvesStatusIsNull() {
            addCriterion("auto_inves_status is null");
            return (Criteria) this;
        }

        public Criteria andAutoInvesStatusIsNotNull() {
            addCriterion("auto_inves_status is not null");
            return (Criteria) this;
        }

        public Criteria andAutoInvesStatusEqualTo(Integer value) {
            addCriterion("auto_inves_status =", value, "autoInvesStatus");
            return (Criteria) this;
        }

        public Criteria andAutoInvesStatusNotEqualTo(Integer value) {
            addCriterion("auto_inves_status <>", value, "autoInvesStatus");
            return (Criteria) this;
        }

        public Criteria andAutoInvesStatusGreaterThan(Integer value) {
            addCriterion("auto_inves_status >", value, "autoInvesStatus");
            return (Criteria) this;
        }

        public Criteria andAutoInvesStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("auto_inves_status >=", value, "autoInvesStatus");
            return (Criteria) this;
        }

        public Criteria andAutoInvesStatusLessThan(Integer value) {
            addCriterion("auto_inves_status <", value, "autoInvesStatus");
            return (Criteria) this;
        }

        public Criteria andAutoInvesStatusLessThanOrEqualTo(Integer value) {
            addCriterion("auto_inves_status <=", value, "autoInvesStatus");
            return (Criteria) this;
        }

        public Criteria andAutoInvesStatusIn(List<Integer> values) {
            addCriterion("auto_inves_status in", values, "autoInvesStatus");
            return (Criteria) this;
        }

        public Criteria andAutoInvesStatusNotIn(List<Integer> values) {
            addCriterion("auto_inves_status not in", values, "autoInvesStatus");
            return (Criteria) this;
        }

        public Criteria andAutoInvesStatusBetween(Integer value1, Integer value2) {
            addCriterion("auto_inves_status between", value1, value2, "autoInvesStatus");
            return (Criteria) this;
        }

        public Criteria andAutoInvesStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("auto_inves_status not between", value1, value2, "autoInvesStatus");
            return (Criteria) this;
        }

        public Criteria andAutoCreditStatusIsNull() {
            addCriterion("auto_credit_status is null");
            return (Criteria) this;
        }

        public Criteria andAutoCreditStatusIsNotNull() {
            addCriterion("auto_credit_status is not null");
            return (Criteria) this;
        }

        public Criteria andAutoCreditStatusEqualTo(Integer value) {
            addCriterion("auto_credit_status =", value, "autoCreditStatus");
            return (Criteria) this;
        }

        public Criteria andAutoCreditStatusNotEqualTo(Integer value) {
            addCriterion("auto_credit_status <>", value, "autoCreditStatus");
            return (Criteria) this;
        }

        public Criteria andAutoCreditStatusGreaterThan(Integer value) {
            addCriterion("auto_credit_status >", value, "autoCreditStatus");
            return (Criteria) this;
        }

        public Criteria andAutoCreditStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("auto_credit_status >=", value, "autoCreditStatus");
            return (Criteria) this;
        }

        public Criteria andAutoCreditStatusLessThan(Integer value) {
            addCriterion("auto_credit_status <", value, "autoCreditStatus");
            return (Criteria) this;
        }

        public Criteria andAutoCreditStatusLessThanOrEqualTo(Integer value) {
            addCriterion("auto_credit_status <=", value, "autoCreditStatus");
            return (Criteria) this;
        }

        public Criteria andAutoCreditStatusIn(List<Integer> values) {
            addCriterion("auto_credit_status in", values, "autoCreditStatus");
            return (Criteria) this;
        }

        public Criteria andAutoCreditStatusNotIn(List<Integer> values) {
            addCriterion("auto_credit_status not in", values, "autoCreditStatus");
            return (Criteria) this;
        }

        public Criteria andAutoCreditStatusBetween(Integer value1, Integer value2) {
            addCriterion("auto_credit_status between", value1, value2, "autoCreditStatus");
            return (Criteria) this;
        }

        public Criteria andAutoCreditStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("auto_credit_status not between", value1, value2, "autoCreditStatus");
            return (Criteria) this;
        }

        public Criteria andAutoWithdrawStatusIsNull() {
            addCriterion("auto_withdraw_status is null");
            return (Criteria) this;
        }

        public Criteria andAutoWithdrawStatusIsNotNull() {
            addCriterion("auto_withdraw_status is not null");
            return (Criteria) this;
        }

        public Criteria andAutoWithdrawStatusEqualTo(Integer value) {
            addCriterion("auto_withdraw_status =", value, "autoWithdrawStatus");
            return (Criteria) this;
        }

        public Criteria andAutoWithdrawStatusNotEqualTo(Integer value) {
            addCriterion("auto_withdraw_status <>", value, "autoWithdrawStatus");
            return (Criteria) this;
        }

        public Criteria andAutoWithdrawStatusGreaterThan(Integer value) {
            addCriterion("auto_withdraw_status >", value, "autoWithdrawStatus");
            return (Criteria) this;
        }

        public Criteria andAutoWithdrawStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("auto_withdraw_status >=", value, "autoWithdrawStatus");
            return (Criteria) this;
        }

        public Criteria andAutoWithdrawStatusLessThan(Integer value) {
            addCriterion("auto_withdraw_status <", value, "autoWithdrawStatus");
            return (Criteria) this;
        }

        public Criteria andAutoWithdrawStatusLessThanOrEqualTo(Integer value) {
            addCriterion("auto_withdraw_status <=", value, "autoWithdrawStatus");
            return (Criteria) this;
        }

        public Criteria andAutoWithdrawStatusIn(List<Integer> values) {
            addCriterion("auto_withdraw_status in", values, "autoWithdrawStatus");
            return (Criteria) this;
        }

        public Criteria andAutoWithdrawStatusNotIn(List<Integer> values) {
            addCriterion("auto_withdraw_status not in", values, "autoWithdrawStatus");
            return (Criteria) this;
        }

        public Criteria andAutoWithdrawStatusBetween(Integer value1, Integer value2) {
            addCriterion("auto_withdraw_status between", value1, value2, "autoWithdrawStatus");
            return (Criteria) this;
        }

        public Criteria andAutoWithdrawStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("auto_withdraw_status not between", value1, value2, "autoWithdrawStatus");
            return (Criteria) this;
        }

        public Criteria andAutoConsumeStatusIsNull() {
            addCriterion("auto_consume_status is null");
            return (Criteria) this;
        }

        public Criteria andAutoConsumeStatusIsNotNull() {
            addCriterion("auto_consume_status is not null");
            return (Criteria) this;
        }

        public Criteria andAutoConsumeStatusEqualTo(Integer value) {
            addCriterion("auto_consume_status =", value, "autoConsumeStatus");
            return (Criteria) this;
        }

        public Criteria andAutoConsumeStatusNotEqualTo(Integer value) {
            addCriterion("auto_consume_status <>", value, "autoConsumeStatus");
            return (Criteria) this;
        }

        public Criteria andAutoConsumeStatusGreaterThan(Integer value) {
            addCriterion("auto_consume_status >", value, "autoConsumeStatus");
            return (Criteria) this;
        }

        public Criteria andAutoConsumeStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("auto_consume_status >=", value, "autoConsumeStatus");
            return (Criteria) this;
        }

        public Criteria andAutoConsumeStatusLessThan(Integer value) {
            addCriterion("auto_consume_status <", value, "autoConsumeStatus");
            return (Criteria) this;
        }

        public Criteria andAutoConsumeStatusLessThanOrEqualTo(Integer value) {
            addCriterion("auto_consume_status <=", value, "autoConsumeStatus");
            return (Criteria) this;
        }

        public Criteria andAutoConsumeStatusIn(List<Integer> values) {
            addCriterion("auto_consume_status in", values, "autoConsumeStatus");
            return (Criteria) this;
        }

        public Criteria andAutoConsumeStatusNotIn(List<Integer> values) {
            addCriterion("auto_consume_status not in", values, "autoConsumeStatus");
            return (Criteria) this;
        }

        public Criteria andAutoConsumeStatusBetween(Integer value1, Integer value2) {
            addCriterion("auto_consume_status between", value1, value2, "autoConsumeStatus");
            return (Criteria) this;
        }

        public Criteria andAutoConsumeStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("auto_consume_status not between", value1, value2, "autoConsumeStatus");
            return (Criteria) this;
        }

        public Criteria andAutoCreateTimeIsNull() {
            addCriterion("auto_create_time is null");
            return (Criteria) this;
        }

        public Criteria andAutoCreateTimeIsNotNull() {
            addCriterion("auto_create_time is not null");
            return (Criteria) this;
        }

        public Criteria andAutoCreateTimeEqualTo(Integer value) {
            addCriterion("auto_create_time =", value, "autoCreateTime");
            return (Criteria) this;
        }

        public Criteria andAutoCreateTimeNotEqualTo(Integer value) {
            addCriterion("auto_create_time <>", value, "autoCreateTime");
            return (Criteria) this;
        }

        public Criteria andAutoCreateTimeGreaterThan(Integer value) {
            addCriterion("auto_create_time >", value, "autoCreateTime");
            return (Criteria) this;
        }

        public Criteria andAutoCreateTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("auto_create_time >=", value, "autoCreateTime");
            return (Criteria) this;
        }

        public Criteria andAutoCreateTimeLessThan(Integer value) {
            addCriterion("auto_create_time <", value, "autoCreateTime");
            return (Criteria) this;
        }

        public Criteria andAutoCreateTimeLessThanOrEqualTo(Integer value) {
            addCriterion("auto_create_time <=", value, "autoCreateTime");
            return (Criteria) this;
        }

        public Criteria andAutoCreateTimeIn(List<Integer> values) {
            addCriterion("auto_create_time in", values, "autoCreateTime");
            return (Criteria) this;
        }

        public Criteria andAutoCreateTimeNotIn(List<Integer> values) {
            addCriterion("auto_create_time not in", values, "autoCreateTime");
            return (Criteria) this;
        }

        public Criteria andAutoCreateTimeBetween(Integer value1, Integer value2) {
            addCriterion("auto_create_time between", value1, value2, "autoCreateTime");
            return (Criteria) this;
        }

        public Criteria andAutoCreateTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("auto_create_time not between", value1, value2, "autoCreateTime");
            return (Criteria) this;
        }

        public Criteria andAutoOrderIdIsNull() {
            addCriterion("auto_order_id is null");
            return (Criteria) this;
        }

        public Criteria andAutoOrderIdIsNotNull() {
            addCriterion("auto_order_id is not null");
            return (Criteria) this;
        }

        public Criteria andAutoOrderIdEqualTo(String value) {
            addCriterion("auto_order_id =", value, "autoOrderId");
            return (Criteria) this;
        }

        public Criteria andAutoOrderIdNotEqualTo(String value) {
            addCriterion("auto_order_id <>", value, "autoOrderId");
            return (Criteria) this;
        }

        public Criteria andAutoOrderIdGreaterThan(String value) {
            addCriterion("auto_order_id >", value, "autoOrderId");
            return (Criteria) this;
        }

        public Criteria andAutoOrderIdGreaterThanOrEqualTo(String value) {
            addCriterion("auto_order_id >=", value, "autoOrderId");
            return (Criteria) this;
        }

        public Criteria andAutoOrderIdLessThan(String value) {
            addCriterion("auto_order_id <", value, "autoOrderId");
            return (Criteria) this;
        }

        public Criteria andAutoOrderIdLessThanOrEqualTo(String value) {
            addCriterion("auto_order_id <=", value, "autoOrderId");
            return (Criteria) this;
        }

        public Criteria andAutoOrderIdLike(String value) {
            addCriterion("auto_order_id like", value, "autoOrderId");
            return (Criteria) this;
        }

        public Criteria andAutoOrderIdNotLike(String value) {
            addCriterion("auto_order_id not like", value, "autoOrderId");
            return (Criteria) this;
        }

        public Criteria andAutoOrderIdIn(List<String> values) {
            addCriterion("auto_order_id in", values, "autoOrderId");
            return (Criteria) this;
        }

        public Criteria andAutoOrderIdNotIn(List<String> values) {
            addCriterion("auto_order_id not in", values, "autoOrderId");
            return (Criteria) this;
        }

        public Criteria andAutoOrderIdBetween(String value1, String value2) {
            addCriterion("auto_order_id between", value1, value2, "autoOrderId");
            return (Criteria) this;
        }

        public Criteria andAutoOrderIdNotBetween(String value1, String value2) {
            addCriterion("auto_order_id not between", value1, value2, "autoOrderId");
            return (Criteria) this;
        }

        public Criteria andAutoCreditOrderIdIsNull() {
            addCriterion("auto_credit_order_id is null");
            return (Criteria) this;
        }

        public Criteria andAutoCreditOrderIdIsNotNull() {
            addCriterion("auto_credit_order_id is not null");
            return (Criteria) this;
        }

        public Criteria andAutoCreditOrderIdEqualTo(String value) {
            addCriterion("auto_credit_order_id =", value, "autoCreditOrderId");
            return (Criteria) this;
        }

        public Criteria andAutoCreditOrderIdNotEqualTo(String value) {
            addCriterion("auto_credit_order_id <>", value, "autoCreditOrderId");
            return (Criteria) this;
        }

        public Criteria andAutoCreditOrderIdGreaterThan(String value) {
            addCriterion("auto_credit_order_id >", value, "autoCreditOrderId");
            return (Criteria) this;
        }

        public Criteria andAutoCreditOrderIdGreaterThanOrEqualTo(String value) {
            addCriterion("auto_credit_order_id >=", value, "autoCreditOrderId");
            return (Criteria) this;
        }

        public Criteria andAutoCreditOrderIdLessThan(String value) {
            addCriterion("auto_credit_order_id <", value, "autoCreditOrderId");
            return (Criteria) this;
        }

        public Criteria andAutoCreditOrderIdLessThanOrEqualTo(String value) {
            addCriterion("auto_credit_order_id <=", value, "autoCreditOrderId");
            return (Criteria) this;
        }

        public Criteria andAutoCreditOrderIdLike(String value) {
            addCriterion("auto_credit_order_id like", value, "autoCreditOrderId");
            return (Criteria) this;
        }

        public Criteria andAutoCreditOrderIdNotLike(String value) {
            addCriterion("auto_credit_order_id not like", value, "autoCreditOrderId");
            return (Criteria) this;
        }

        public Criteria andAutoCreditOrderIdIn(List<String> values) {
            addCriterion("auto_credit_order_id in", values, "autoCreditOrderId");
            return (Criteria) this;
        }

        public Criteria andAutoCreditOrderIdNotIn(List<String> values) {
            addCriterion("auto_credit_order_id not in", values, "autoCreditOrderId");
            return (Criteria) this;
        }

        public Criteria andAutoCreditOrderIdBetween(String value1, String value2) {
            addCriterion("auto_credit_order_id between", value1, value2, "autoCreditOrderId");
            return (Criteria) this;
        }

        public Criteria andAutoCreditOrderIdNotBetween(String value1, String value2) {
            addCriterion("auto_credit_order_id not between", value1, value2, "autoCreditOrderId");
            return (Criteria) this;
        }

        public Criteria andAutoCreditTimeIsNull() {
            addCriterion("auto_credit_time is null");
            return (Criteria) this;
        }

        public Criteria andAutoCreditTimeIsNotNull() {
            addCriterion("auto_credit_time is not null");
            return (Criteria) this;
        }

        public Criteria andAutoCreditTimeEqualTo(Integer value) {
            addCriterion("auto_credit_time =", value, "autoCreditTime");
            return (Criteria) this;
        }

        public Criteria andAutoCreditTimeNotEqualTo(Integer value) {
            addCriterion("auto_credit_time <>", value, "autoCreditTime");
            return (Criteria) this;
        }

        public Criteria andAutoCreditTimeGreaterThan(Integer value) {
            addCriterion("auto_credit_time >", value, "autoCreditTime");
            return (Criteria) this;
        }

        public Criteria andAutoCreditTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("auto_credit_time >=", value, "autoCreditTime");
            return (Criteria) this;
        }

        public Criteria andAutoCreditTimeLessThan(Integer value) {
            addCriterion("auto_credit_time <", value, "autoCreditTime");
            return (Criteria) this;
        }

        public Criteria andAutoCreditTimeLessThanOrEqualTo(Integer value) {
            addCriterion("auto_credit_time <=", value, "autoCreditTime");
            return (Criteria) this;
        }

        public Criteria andAutoCreditTimeIn(List<Integer> values) {
            addCriterion("auto_credit_time in", values, "autoCreditTime");
            return (Criteria) this;
        }

        public Criteria andAutoCreditTimeNotIn(List<Integer> values) {
            addCriterion("auto_credit_time not in", values, "autoCreditTime");
            return (Criteria) this;
        }

        public Criteria andAutoCreditTimeBetween(Integer value1, Integer value2) {
            addCriterion("auto_credit_time between", value1, value2, "autoCreditTime");
            return (Criteria) this;
        }

        public Criteria andAutoCreditTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("auto_credit_time not between", value1, value2, "autoCreditTime");
            return (Criteria) this;
        }

        public Criteria andAutoBidTimeIsNull() {
            addCriterion("auto_bid_time is null");
            return (Criteria) this;
        }

        public Criteria andAutoBidTimeIsNotNull() {
            addCriterion("auto_bid_time is not null");
            return (Criteria) this;
        }

        public Criteria andAutoBidTimeEqualTo(Integer value) {
            addCriterion("auto_bid_time =", value, "autoBidTime");
            return (Criteria) this;
        }

        public Criteria andAutoBidTimeNotEqualTo(Integer value) {
            addCriterion("auto_bid_time <>", value, "autoBidTime");
            return (Criteria) this;
        }

        public Criteria andAutoBidTimeGreaterThan(Integer value) {
            addCriterion("auto_bid_time >", value, "autoBidTime");
            return (Criteria) this;
        }

        public Criteria andAutoBidTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("auto_bid_time >=", value, "autoBidTime");
            return (Criteria) this;
        }

        public Criteria andAutoBidTimeLessThan(Integer value) {
            addCriterion("auto_bid_time <", value, "autoBidTime");
            return (Criteria) this;
        }

        public Criteria andAutoBidTimeLessThanOrEqualTo(Integer value) {
            addCriterion("auto_bid_time <=", value, "autoBidTime");
            return (Criteria) this;
        }

        public Criteria andAutoBidTimeIn(List<Integer> values) {
            addCriterion("auto_bid_time in", values, "autoBidTime");
            return (Criteria) this;
        }

        public Criteria andAutoBidTimeNotIn(List<Integer> values) {
            addCriterion("auto_bid_time not in", values, "autoBidTime");
            return (Criteria) this;
        }

        public Criteria andAutoBidTimeBetween(Integer value1, Integer value2) {
            addCriterion("auto_bid_time between", value1, value2, "autoBidTime");
            return (Criteria) this;
        }

        public Criteria andAutoBidTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("auto_bid_time not between", value1, value2, "autoBidTime");
            return (Criteria) this;
        }

        public Criteria andAutoBidEndTimeIsNull() {
            addCriterion("auto_bid_end_time is null");
            return (Criteria) this;
        }

        public Criteria andAutoBidEndTimeIsNotNull() {
            addCriterion("auto_bid_end_time is not null");
            return (Criteria) this;
        }

        public Criteria andAutoBidEndTimeEqualTo(String value) {
            addCriterion("auto_bid_end_time =", value, "autoBidEndTime");
            return (Criteria) this;
        }

        public Criteria andAutoBidEndTimeNotEqualTo(String value) {
            addCriterion("auto_bid_end_time <>", value, "autoBidEndTime");
            return (Criteria) this;
        }

        public Criteria andAutoBidEndTimeGreaterThan(String value) {
            addCriterion("auto_bid_end_time >", value, "autoBidEndTime");
            return (Criteria) this;
        }

        public Criteria andAutoBidEndTimeGreaterThanOrEqualTo(String value) {
            addCriterion("auto_bid_end_time >=", value, "autoBidEndTime");
            return (Criteria) this;
        }

        public Criteria andAutoBidEndTimeLessThan(String value) {
            addCriterion("auto_bid_end_time <", value, "autoBidEndTime");
            return (Criteria) this;
        }

        public Criteria andAutoBidEndTimeLessThanOrEqualTo(String value) {
            addCriterion("auto_bid_end_time <=", value, "autoBidEndTime");
            return (Criteria) this;
        }

        public Criteria andAutoBidEndTimeLike(String value) {
            addCriterion("auto_bid_end_time like", value, "autoBidEndTime");
            return (Criteria) this;
        }

        public Criteria andAutoBidEndTimeNotLike(String value) {
            addCriterion("auto_bid_end_time not like", value, "autoBidEndTime");
            return (Criteria) this;
        }

        public Criteria andAutoBidEndTimeIn(List<String> values) {
            addCriterion("auto_bid_end_time in", values, "autoBidEndTime");
            return (Criteria) this;
        }

        public Criteria andAutoBidEndTimeNotIn(List<String> values) {
            addCriterion("auto_bid_end_time not in", values, "autoBidEndTime");
            return (Criteria) this;
        }

        public Criteria andAutoBidEndTimeBetween(String value1, String value2) {
            addCriterion("auto_bid_end_time between", value1, value2, "autoBidEndTime");
            return (Criteria) this;
        }

        public Criteria andAutoBidEndTimeNotBetween(String value1, String value2) {
            addCriterion("auto_bid_end_time not between", value1, value2, "autoBidEndTime");
            return (Criteria) this;
        }

        public Criteria andAutoPaymentStatusIsNull() {
            addCriterion("auto_payment_status is null");
            return (Criteria) this;
        }

        public Criteria andAutoPaymentStatusIsNotNull() {
            addCriterion("auto_payment_status is not null");
            return (Criteria) this;
        }

        public Criteria andAutoPaymentStatusEqualTo(Integer value) {
            addCriterion("auto_payment_status =", value, "autoPaymentStatus");
            return (Criteria) this;
        }

        public Criteria andAutoPaymentStatusNotEqualTo(Integer value) {
            addCriterion("auto_payment_status <>", value, "autoPaymentStatus");
            return (Criteria) this;
        }

        public Criteria andAutoPaymentStatusGreaterThan(Integer value) {
            addCriterion("auto_payment_status >", value, "autoPaymentStatus");
            return (Criteria) this;
        }

        public Criteria andAutoPaymentStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("auto_payment_status >=", value, "autoPaymentStatus");
            return (Criteria) this;
        }

        public Criteria andAutoPaymentStatusLessThan(Integer value) {
            addCriterion("auto_payment_status <", value, "autoPaymentStatus");
            return (Criteria) this;
        }

        public Criteria andAutoPaymentStatusLessThanOrEqualTo(Integer value) {
            addCriterion("auto_payment_status <=", value, "autoPaymentStatus");
            return (Criteria) this;
        }

        public Criteria andAutoPaymentStatusIn(List<Integer> values) {
            addCriterion("auto_payment_status in", values, "autoPaymentStatus");
            return (Criteria) this;
        }

        public Criteria andAutoPaymentStatusNotIn(List<Integer> values) {
            addCriterion("auto_payment_status not in", values, "autoPaymentStatus");
            return (Criteria) this;
        }

        public Criteria andAutoPaymentStatusBetween(Integer value1, Integer value2) {
            addCriterion("auto_payment_status between", value1, value2, "autoPaymentStatus");
            return (Criteria) this;
        }

        public Criteria andAutoPaymentStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("auto_payment_status not between", value1, value2, "autoPaymentStatus");
            return (Criteria) this;
        }

        public Criteria andAutoPaymentTimeIsNull() {
            addCriterion("auto_payment_time is null");
            return (Criteria) this;
        }

        public Criteria andAutoPaymentTimeIsNotNull() {
            addCriterion("auto_payment_time is not null");
            return (Criteria) this;
        }

        public Criteria andAutoPaymentTimeEqualTo(Integer value) {
            addCriterion("auto_payment_time =", value, "autoPaymentTime");
            return (Criteria) this;
        }

        public Criteria andAutoPaymentTimeNotEqualTo(Integer value) {
            addCriterion("auto_payment_time <>", value, "autoPaymentTime");
            return (Criteria) this;
        }

        public Criteria andAutoPaymentTimeGreaterThan(Integer value) {
            addCriterion("auto_payment_time >", value, "autoPaymentTime");
            return (Criteria) this;
        }

        public Criteria andAutoPaymentTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("auto_payment_time >=", value, "autoPaymentTime");
            return (Criteria) this;
        }

        public Criteria andAutoPaymentTimeLessThan(Integer value) {
            addCriterion("auto_payment_time <", value, "autoPaymentTime");
            return (Criteria) this;
        }

        public Criteria andAutoPaymentTimeLessThanOrEqualTo(Integer value) {
            addCriterion("auto_payment_time <=", value, "autoPaymentTime");
            return (Criteria) this;
        }

        public Criteria andAutoPaymentTimeIn(List<Integer> values) {
            addCriterion("auto_payment_time in", values, "autoPaymentTime");
            return (Criteria) this;
        }

        public Criteria andAutoPaymentTimeNotIn(List<Integer> values) {
            addCriterion("auto_payment_time not in", values, "autoPaymentTime");
            return (Criteria) this;
        }

        public Criteria andAutoPaymentTimeBetween(Integer value1, Integer value2) {
            addCriterion("auto_payment_time between", value1, value2, "autoPaymentTime");
            return (Criteria) this;
        }

        public Criteria andAutoPaymentTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("auto_payment_time not between", value1, value2, "autoPaymentTime");
            return (Criteria) this;
        }

        public Criteria andAutoPaymentEndTimeIsNull() {
            addCriterion("auto_payment_end_time is null");
            return (Criteria) this;
        }

        public Criteria andAutoPaymentEndTimeIsNotNull() {
            addCriterion("auto_payment_end_time is not null");
            return (Criteria) this;
        }

        public Criteria andAutoPaymentEndTimeEqualTo(String value) {
            addCriterion("auto_payment_end_time =", value, "autoPaymentEndTime");
            return (Criteria) this;
        }

        public Criteria andAutoPaymentEndTimeNotEqualTo(String value) {
            addCriterion("auto_payment_end_time <>", value, "autoPaymentEndTime");
            return (Criteria) this;
        }

        public Criteria andAutoPaymentEndTimeGreaterThan(String value) {
            addCriterion("auto_payment_end_time >", value, "autoPaymentEndTime");
            return (Criteria) this;
        }

        public Criteria andAutoPaymentEndTimeGreaterThanOrEqualTo(String value) {
            addCriterion("auto_payment_end_time >=", value, "autoPaymentEndTime");
            return (Criteria) this;
        }

        public Criteria andAutoPaymentEndTimeLessThan(String value) {
            addCriterion("auto_payment_end_time <", value, "autoPaymentEndTime");
            return (Criteria) this;
        }

        public Criteria andAutoPaymentEndTimeLessThanOrEqualTo(String value) {
            addCriterion("auto_payment_end_time <=", value, "autoPaymentEndTime");
            return (Criteria) this;
        }

        public Criteria andAutoPaymentEndTimeLike(String value) {
            addCriterion("auto_payment_end_time like", value, "autoPaymentEndTime");
            return (Criteria) this;
        }

        public Criteria andAutoPaymentEndTimeNotLike(String value) {
            addCriterion("auto_payment_end_time not like", value, "autoPaymentEndTime");
            return (Criteria) this;
        }

        public Criteria andAutoPaymentEndTimeIn(List<String> values) {
            addCriterion("auto_payment_end_time in", values, "autoPaymentEndTime");
            return (Criteria) this;
        }

        public Criteria andAutoPaymentEndTimeNotIn(List<String> values) {
            addCriterion("auto_payment_end_time not in", values, "autoPaymentEndTime");
            return (Criteria) this;
        }

        public Criteria andAutoPaymentEndTimeBetween(String value1, String value2) {
            addCriterion("auto_payment_end_time between", value1, value2, "autoPaymentEndTime");
            return (Criteria) this;
        }

        public Criteria andAutoPaymentEndTimeNotBetween(String value1, String value2) {
            addCriterion("auto_payment_end_time not between", value1, value2, "autoPaymentEndTime");
            return (Criteria) this;
        }

        public Criteria andAutoRepayStatusIsNull() {
            addCriterion("auto_repay_status is null");
            return (Criteria) this;
        }

        public Criteria andAutoRepayStatusIsNotNull() {
            addCriterion("auto_repay_status is not null");
            return (Criteria) this;
        }

        public Criteria andAutoRepayStatusEqualTo(Integer value) {
            addCriterion("auto_repay_status =", value, "autoRepayStatus");
            return (Criteria) this;
        }

        public Criteria andAutoRepayStatusNotEqualTo(Integer value) {
            addCriterion("auto_repay_status <>", value, "autoRepayStatus");
            return (Criteria) this;
        }

        public Criteria andAutoRepayStatusGreaterThan(Integer value) {
            addCriterion("auto_repay_status >", value, "autoRepayStatus");
            return (Criteria) this;
        }

        public Criteria andAutoRepayStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("auto_repay_status >=", value, "autoRepayStatus");
            return (Criteria) this;
        }

        public Criteria andAutoRepayStatusLessThan(Integer value) {
            addCriterion("auto_repay_status <", value, "autoRepayStatus");
            return (Criteria) this;
        }

        public Criteria andAutoRepayStatusLessThanOrEqualTo(Integer value) {
            addCriterion("auto_repay_status <=", value, "autoRepayStatus");
            return (Criteria) this;
        }

        public Criteria andAutoRepayStatusIn(List<Integer> values) {
            addCriterion("auto_repay_status in", values, "autoRepayStatus");
            return (Criteria) this;
        }

        public Criteria andAutoRepayStatusNotIn(List<Integer> values) {
            addCriterion("auto_repay_status not in", values, "autoRepayStatus");
            return (Criteria) this;
        }

        public Criteria andAutoRepayStatusBetween(Integer value1, Integer value2) {
            addCriterion("auto_repay_status between", value1, value2, "autoRepayStatus");
            return (Criteria) this;
        }

        public Criteria andAutoRepayStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("auto_repay_status not between", value1, value2, "autoRepayStatus");
            return (Criteria) this;
        }

        public Criteria andAutoRepayTimeIsNull() {
            addCriterion("auto_repay_time is null");
            return (Criteria) this;
        }

        public Criteria andAutoRepayTimeIsNotNull() {
            addCriterion("auto_repay_time is not null");
            return (Criteria) this;
        }

        public Criteria andAutoRepayTimeEqualTo(Integer value) {
            addCriterion("auto_repay_time =", value, "autoRepayTime");
            return (Criteria) this;
        }

        public Criteria andAutoRepayTimeNotEqualTo(Integer value) {
            addCriterion("auto_repay_time <>", value, "autoRepayTime");
            return (Criteria) this;
        }

        public Criteria andAutoRepayTimeGreaterThan(Integer value) {
            addCriterion("auto_repay_time >", value, "autoRepayTime");
            return (Criteria) this;
        }

        public Criteria andAutoRepayTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("auto_repay_time >=", value, "autoRepayTime");
            return (Criteria) this;
        }

        public Criteria andAutoRepayTimeLessThan(Integer value) {
            addCriterion("auto_repay_time <", value, "autoRepayTime");
            return (Criteria) this;
        }

        public Criteria andAutoRepayTimeLessThanOrEqualTo(Integer value) {
            addCriterion("auto_repay_time <=", value, "autoRepayTime");
            return (Criteria) this;
        }

        public Criteria andAutoRepayTimeIn(List<Integer> values) {
            addCriterion("auto_repay_time in", values, "autoRepayTime");
            return (Criteria) this;
        }

        public Criteria andAutoRepayTimeNotIn(List<Integer> values) {
            addCriterion("auto_repay_time not in", values, "autoRepayTime");
            return (Criteria) this;
        }

        public Criteria andAutoRepayTimeBetween(Integer value1, Integer value2) {
            addCriterion("auto_repay_time between", value1, value2, "autoRepayTime");
            return (Criteria) this;
        }

        public Criteria andAutoRepayTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("auto_repay_time not between", value1, value2, "autoRepayTime");
            return (Criteria) this;
        }

        public Criteria andAutoRepayEndTimeIsNull() {
            addCriterion("auto_repay_end_time is null");
            return (Criteria) this;
        }

        public Criteria andAutoRepayEndTimeIsNotNull() {
            addCriterion("auto_repay_end_time is not null");
            return (Criteria) this;
        }

        public Criteria andAutoRepayEndTimeEqualTo(String value) {
            addCriterion("auto_repay_end_time =", value, "autoRepayEndTime");
            return (Criteria) this;
        }

        public Criteria andAutoRepayEndTimeNotEqualTo(String value) {
            addCriterion("auto_repay_end_time <>", value, "autoRepayEndTime");
            return (Criteria) this;
        }

        public Criteria andAutoRepayEndTimeGreaterThan(String value) {
            addCriterion("auto_repay_end_time >", value, "autoRepayEndTime");
            return (Criteria) this;
        }

        public Criteria andAutoRepayEndTimeGreaterThanOrEqualTo(String value) {
            addCriterion("auto_repay_end_time >=", value, "autoRepayEndTime");
            return (Criteria) this;
        }

        public Criteria andAutoRepayEndTimeLessThan(String value) {
            addCriterion("auto_repay_end_time <", value, "autoRepayEndTime");
            return (Criteria) this;
        }

        public Criteria andAutoRepayEndTimeLessThanOrEqualTo(String value) {
            addCriterion("auto_repay_end_time <=", value, "autoRepayEndTime");
            return (Criteria) this;
        }

        public Criteria andAutoRepayEndTimeLike(String value) {
            addCriterion("auto_repay_end_time like", value, "autoRepayEndTime");
            return (Criteria) this;
        }

        public Criteria andAutoRepayEndTimeNotLike(String value) {
            addCriterion("auto_repay_end_time not like", value, "autoRepayEndTime");
            return (Criteria) this;
        }

        public Criteria andAutoRepayEndTimeIn(List<String> values) {
            addCriterion("auto_repay_end_time in", values, "autoRepayEndTime");
            return (Criteria) this;
        }

        public Criteria andAutoRepayEndTimeNotIn(List<String> values) {
            addCriterion("auto_repay_end_time not in", values, "autoRepayEndTime");
            return (Criteria) this;
        }

        public Criteria andAutoRepayEndTimeBetween(String value1, String value2) {
            addCriterion("auto_repay_end_time between", value1, value2, "autoRepayEndTime");
            return (Criteria) this;
        }

        public Criteria andAutoRepayEndTimeNotBetween(String value1, String value2) {
            addCriterion("auto_repay_end_time not between", value1, value2, "autoRepayEndTime");
            return (Criteria) this;
        }

        public Criteria andInvesCancelTimeIsNull() {
            addCriterion("inves_cancel_time is null");
            return (Criteria) this;
        }

        public Criteria andInvesCancelTimeIsNotNull() {
            addCriterion("inves_cancel_time is not null");
            return (Criteria) this;
        }

        public Criteria andInvesCancelTimeEqualTo(String value) {
            addCriterion("inves_cancel_time =", value, "invesCancelTime");
            return (Criteria) this;
        }

        public Criteria andInvesCancelTimeNotEqualTo(String value) {
            addCriterion("inves_cancel_time <>", value, "invesCancelTime");
            return (Criteria) this;
        }

        public Criteria andInvesCancelTimeGreaterThan(String value) {
            addCriterion("inves_cancel_time >", value, "invesCancelTime");
            return (Criteria) this;
        }

        public Criteria andInvesCancelTimeGreaterThanOrEqualTo(String value) {
            addCriterion("inves_cancel_time >=", value, "invesCancelTime");
            return (Criteria) this;
        }

        public Criteria andInvesCancelTimeLessThan(String value) {
            addCriterion("inves_cancel_time <", value, "invesCancelTime");
            return (Criteria) this;
        }

        public Criteria andInvesCancelTimeLessThanOrEqualTo(String value) {
            addCriterion("inves_cancel_time <=", value, "invesCancelTime");
            return (Criteria) this;
        }

        public Criteria andInvesCancelTimeLike(String value) {
            addCriterion("inves_cancel_time like", value, "invesCancelTime");
            return (Criteria) this;
        }

        public Criteria andInvesCancelTimeNotLike(String value) {
            addCriterion("inves_cancel_time not like", value, "invesCancelTime");
            return (Criteria) this;
        }

        public Criteria andInvesCancelTimeIn(List<String> values) {
            addCriterion("inves_cancel_time in", values, "invesCancelTime");
            return (Criteria) this;
        }

        public Criteria andInvesCancelTimeNotIn(List<String> values) {
            addCriterion("inves_cancel_time not in", values, "invesCancelTime");
            return (Criteria) this;
        }

        public Criteria andInvesCancelTimeBetween(String value1, String value2) {
            addCriterion("inves_cancel_time between", value1, value2, "invesCancelTime");
            return (Criteria) this;
        }

        public Criteria andInvesCancelTimeNotBetween(String value1, String value2) {
            addCriterion("inves_cancel_time not between", value1, value2, "invesCancelTime");
            return (Criteria) this;
        }

        public Criteria andCreditCancelTimeIsNull() {
            addCriterion("credit_cancel_time is null");
            return (Criteria) this;
        }

        public Criteria andCreditCancelTimeIsNotNull() {
            addCriterion("credit_cancel_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreditCancelTimeEqualTo(String value) {
            addCriterion("credit_cancel_time =", value, "creditCancelTime");
            return (Criteria) this;
        }

        public Criteria andCreditCancelTimeNotEqualTo(String value) {
            addCriterion("credit_cancel_time <>", value, "creditCancelTime");
            return (Criteria) this;
        }

        public Criteria andCreditCancelTimeGreaterThan(String value) {
            addCriterion("credit_cancel_time >", value, "creditCancelTime");
            return (Criteria) this;
        }

        public Criteria andCreditCancelTimeGreaterThanOrEqualTo(String value) {
            addCriterion("credit_cancel_time >=", value, "creditCancelTime");
            return (Criteria) this;
        }

        public Criteria andCreditCancelTimeLessThan(String value) {
            addCriterion("credit_cancel_time <", value, "creditCancelTime");
            return (Criteria) this;
        }

        public Criteria andCreditCancelTimeLessThanOrEqualTo(String value) {
            addCriterion("credit_cancel_time <=", value, "creditCancelTime");
            return (Criteria) this;
        }

        public Criteria andCreditCancelTimeLike(String value) {
            addCriterion("credit_cancel_time like", value, "creditCancelTime");
            return (Criteria) this;
        }

        public Criteria andCreditCancelTimeNotLike(String value) {
            addCriterion("credit_cancel_time not like", value, "creditCancelTime");
            return (Criteria) this;
        }

        public Criteria andCreditCancelTimeIn(List<String> values) {
            addCriterion("credit_cancel_time in", values, "creditCancelTime");
            return (Criteria) this;
        }

        public Criteria andCreditCancelTimeNotIn(List<String> values) {
            addCriterion("credit_cancel_time not in", values, "creditCancelTime");
            return (Criteria) this;
        }

        public Criteria andCreditCancelTimeBetween(String value1, String value2) {
            addCriterion("credit_cancel_time between", value1, value2, "creditCancelTime");
            return (Criteria) this;
        }

        public Criteria andCreditCancelTimeNotBetween(String value1, String value2) {
            addCriterion("credit_cancel_time not between", value1, value2, "creditCancelTime");
            return (Criteria) this;
        }

        public Criteria andPaymentCancelTimeIsNull() {
            addCriterion("payment_cancel_time is null");
            return (Criteria) this;
        }

        public Criteria andPaymentCancelTimeIsNotNull() {
            addCriterion("payment_cancel_time is not null");
            return (Criteria) this;
        }

        public Criteria andPaymentCancelTimeEqualTo(String value) {
            addCriterion("payment_cancel_time =", value, "paymentCancelTime");
            return (Criteria) this;
        }

        public Criteria andPaymentCancelTimeNotEqualTo(String value) {
            addCriterion("payment_cancel_time <>", value, "paymentCancelTime");
            return (Criteria) this;
        }

        public Criteria andPaymentCancelTimeGreaterThan(String value) {
            addCriterion("payment_cancel_time >", value, "paymentCancelTime");
            return (Criteria) this;
        }

        public Criteria andPaymentCancelTimeGreaterThanOrEqualTo(String value) {
            addCriterion("payment_cancel_time >=", value, "paymentCancelTime");
            return (Criteria) this;
        }

        public Criteria andPaymentCancelTimeLessThan(String value) {
            addCriterion("payment_cancel_time <", value, "paymentCancelTime");
            return (Criteria) this;
        }

        public Criteria andPaymentCancelTimeLessThanOrEqualTo(String value) {
            addCriterion("payment_cancel_time <=", value, "paymentCancelTime");
            return (Criteria) this;
        }

        public Criteria andPaymentCancelTimeLike(String value) {
            addCriterion("payment_cancel_time like", value, "paymentCancelTime");
            return (Criteria) this;
        }

        public Criteria andPaymentCancelTimeNotLike(String value) {
            addCriterion("payment_cancel_time not like", value, "paymentCancelTime");
            return (Criteria) this;
        }

        public Criteria andPaymentCancelTimeIn(List<String> values) {
            addCriterion("payment_cancel_time in", values, "paymentCancelTime");
            return (Criteria) this;
        }

        public Criteria andPaymentCancelTimeNotIn(List<String> values) {
            addCriterion("payment_cancel_time not in", values, "paymentCancelTime");
            return (Criteria) this;
        }

        public Criteria andPaymentCancelTimeBetween(String value1, String value2) {
            addCriterion("payment_cancel_time between", value1, value2, "paymentCancelTime");
            return (Criteria) this;
        }

        public Criteria andPaymentCancelTimeNotBetween(String value1, String value2) {
            addCriterion("payment_cancel_time not between", value1, value2, "paymentCancelTime");
            return (Criteria) this;
        }

        public Criteria andRepayCancelTimeIsNull() {
            addCriterion("repay_cancel_time is null");
            return (Criteria) this;
        }

        public Criteria andRepayCancelTimeIsNotNull() {
            addCriterion("repay_cancel_time is not null");
            return (Criteria) this;
        }

        public Criteria andRepayCancelTimeEqualTo(String value) {
            addCriterion("repay_cancel_time =", value, "repayCancelTime");
            return (Criteria) this;
        }

        public Criteria andRepayCancelTimeNotEqualTo(String value) {
            addCriterion("repay_cancel_time <>", value, "repayCancelTime");
            return (Criteria) this;
        }

        public Criteria andRepayCancelTimeGreaterThan(String value) {
            addCriterion("repay_cancel_time >", value, "repayCancelTime");
            return (Criteria) this;
        }

        public Criteria andRepayCancelTimeGreaterThanOrEqualTo(String value) {
            addCriterion("repay_cancel_time >=", value, "repayCancelTime");
            return (Criteria) this;
        }

        public Criteria andRepayCancelTimeLessThan(String value) {
            addCriterion("repay_cancel_time <", value, "repayCancelTime");
            return (Criteria) this;
        }

        public Criteria andRepayCancelTimeLessThanOrEqualTo(String value) {
            addCriterion("repay_cancel_time <=", value, "repayCancelTime");
            return (Criteria) this;
        }

        public Criteria andRepayCancelTimeLike(String value) {
            addCriterion("repay_cancel_time like", value, "repayCancelTime");
            return (Criteria) this;
        }

        public Criteria andRepayCancelTimeNotLike(String value) {
            addCriterion("repay_cancel_time not like", value, "repayCancelTime");
            return (Criteria) this;
        }

        public Criteria andRepayCancelTimeIn(List<String> values) {
            addCriterion("repay_cancel_time in", values, "repayCancelTime");
            return (Criteria) this;
        }

        public Criteria andRepayCancelTimeNotIn(List<String> values) {
            addCriterion("repay_cancel_time not in", values, "repayCancelTime");
            return (Criteria) this;
        }

        public Criteria andRepayCancelTimeBetween(String value1, String value2) {
            addCriterion("repay_cancel_time between", value1, value2, "repayCancelTime");
            return (Criteria) this;
        }

        public Criteria andRepayCancelTimeNotBetween(String value1, String value2) {
            addCriterion("repay_cancel_time not between", value1, value2, "repayCancelTime");
            return (Criteria) this;
        }

        public Criteria andDelFlgIsNull() {
            addCriterion("del_flg is null");
            return (Criteria) this;
        }

        public Criteria andDelFlgIsNotNull() {
            addCriterion("del_flg is not null");
            return (Criteria) this;
        }

        public Criteria andDelFlgEqualTo(Integer value) {
            addCriterion("del_flg =", value, "delFlg");
            return (Criteria) this;
        }

        public Criteria andDelFlgNotEqualTo(Integer value) {
            addCriterion("del_flg <>", value, "delFlg");
            return (Criteria) this;
        }

        public Criteria andDelFlgGreaterThan(Integer value) {
            addCriterion("del_flg >", value, "delFlg");
            return (Criteria) this;
        }

        public Criteria andDelFlgGreaterThanOrEqualTo(Integer value) {
            addCriterion("del_flg >=", value, "delFlg");
            return (Criteria) this;
        }

        public Criteria andDelFlgLessThan(Integer value) {
            addCriterion("del_flg <", value, "delFlg");
            return (Criteria) this;
        }

        public Criteria andDelFlgLessThanOrEqualTo(Integer value) {
            addCriterion("del_flg <=", value, "delFlg");
            return (Criteria) this;
        }

        public Criteria andDelFlgIn(List<Integer> values) {
            addCriterion("del_flg in", values, "delFlg");
            return (Criteria) this;
        }

        public Criteria andDelFlgNotIn(List<Integer> values) {
            addCriterion("del_flg not in", values, "delFlg");
            return (Criteria) this;
        }

        public Criteria andDelFlgBetween(Integer value1, Integer value2) {
            addCriterion("del_flg between", value1, value2, "delFlg");
            return (Criteria) this;
        }

        public Criteria andDelFlgNotBetween(Integer value1, Integer value2) {
            addCriterion("del_flg not between", value1, value2, "delFlg");
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