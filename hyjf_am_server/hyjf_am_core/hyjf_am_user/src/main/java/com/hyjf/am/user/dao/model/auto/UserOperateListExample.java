package com.hyjf.am.user.dao.model.auto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserOperateListExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public UserOperateListExample() {
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

        public Criteria andCustomerGroupIsNull() {
            addCriterion("customer_group is null");
            return (Criteria) this;
        }

        public Criteria andCustomerGroupIsNotNull() {
            addCriterion("customer_group is not null");
            return (Criteria) this;
        }

        public Criteria andCustomerGroupEqualTo(Integer value) {
            addCriterion("customer_group =", value, "customerGroup");
            return (Criteria) this;
        }

        public Criteria andCustomerGroupNotEqualTo(Integer value) {
            addCriterion("customer_group <>", value, "customerGroup");
            return (Criteria) this;
        }

        public Criteria andCustomerGroupGreaterThan(Integer value) {
            addCriterion("customer_group >", value, "customerGroup");
            return (Criteria) this;
        }

        public Criteria andCustomerGroupGreaterThanOrEqualTo(Integer value) {
            addCriterion("customer_group >=", value, "customerGroup");
            return (Criteria) this;
        }

        public Criteria andCustomerGroupLessThan(Integer value) {
            addCriterion("customer_group <", value, "customerGroup");
            return (Criteria) this;
        }

        public Criteria andCustomerGroupLessThanOrEqualTo(Integer value) {
            addCriterion("customer_group <=", value, "customerGroup");
            return (Criteria) this;
        }

        public Criteria andCustomerGroupIn(List<Integer> values) {
            addCriterion("customer_group in", values, "customerGroup");
            return (Criteria) this;
        }

        public Criteria andCustomerGroupNotIn(List<Integer> values) {
            addCriterion("customer_group not in", values, "customerGroup");
            return (Criteria) this;
        }

        public Criteria andCustomerGroupBetween(Integer value1, Integer value2) {
            addCriterion("customer_group between", value1, value2, "customerGroup");
            return (Criteria) this;
        }

        public Criteria andCustomerGroupNotBetween(Integer value1, Integer value2) {
            addCriterion("customer_group not between", value1, value2, "customerGroup");
            return (Criteria) this;
        }

        public Criteria andMoneyIsNull() {
            addCriterion("money is null");
            return (Criteria) this;
        }

        public Criteria andMoneyIsNotNull() {
            addCriterion("money is not null");
            return (Criteria) this;
        }

        public Criteria andMoneyEqualTo(BigDecimal value) {
            addCriterion("money =", value, "money");
            return (Criteria) this;
        }

        public Criteria andMoneyNotEqualTo(BigDecimal value) {
            addCriterion("money <>", value, "money");
            return (Criteria) this;
        }

        public Criteria andMoneyGreaterThan(BigDecimal value) {
            addCriterion("money >", value, "money");
            return (Criteria) this;
        }

        public Criteria andMoneyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("money >=", value, "money");
            return (Criteria) this;
        }

        public Criteria andMoneyLessThan(BigDecimal value) {
            addCriterion("money <", value, "money");
            return (Criteria) this;
        }

        public Criteria andMoneyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("money <=", value, "money");
            return (Criteria) this;
        }

        public Criteria andMoneyIn(List<BigDecimal> values) {
            addCriterion("money in", values, "money");
            return (Criteria) this;
        }

        public Criteria andMoneyNotIn(List<BigDecimal> values) {
            addCriterion("money not in", values, "money");
            return (Criteria) this;
        }

        public Criteria andMoneyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("money between", value1, value2, "money");
            return (Criteria) this;
        }

        public Criteria andMoneyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("money not between", value1, value2, "money");
            return (Criteria) this;
        }

        public Criteria andYearMoneyIsNull() {
            addCriterion("year_money is null");
            return (Criteria) this;
        }

        public Criteria andYearMoneyIsNotNull() {
            addCriterion("year_money is not null");
            return (Criteria) this;
        }

        public Criteria andYearMoneyEqualTo(BigDecimal value) {
            addCriterion("year_money =", value, "yearMoney");
            return (Criteria) this;
        }

        public Criteria andYearMoneyNotEqualTo(BigDecimal value) {
            addCriterion("year_money <>", value, "yearMoney");
            return (Criteria) this;
        }

        public Criteria andYearMoneyGreaterThan(BigDecimal value) {
            addCriterion("year_money >", value, "yearMoney");
            return (Criteria) this;
        }

        public Criteria andYearMoneyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("year_money >=", value, "yearMoney");
            return (Criteria) this;
        }

        public Criteria andYearMoneyLessThan(BigDecimal value) {
            addCriterion("year_money <", value, "yearMoney");
            return (Criteria) this;
        }

        public Criteria andYearMoneyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("year_money <=", value, "yearMoney");
            return (Criteria) this;
        }

        public Criteria andYearMoneyIn(List<BigDecimal> values) {
            addCriterion("year_money in", values, "yearMoney");
            return (Criteria) this;
        }

        public Criteria andYearMoneyNotIn(List<BigDecimal> values) {
            addCriterion("year_money not in", values, "yearMoney");
            return (Criteria) this;
        }

        public Criteria andYearMoneyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("year_money between", value1, value2, "yearMoney");
            return (Criteria) this;
        }

        public Criteria andYearMoneyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("year_money not between", value1, value2, "yearMoney");
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

        public Criteria andOperatingIsNull() {
            addCriterion("operating is null");
            return (Criteria) this;
        }

        public Criteria andOperatingIsNotNull() {
            addCriterion("operating is not null");
            return (Criteria) this;
        }

        public Criteria andOperatingEqualTo(Integer value) {
            addCriterion("operating =", value, "operating");
            return (Criteria) this;
        }

        public Criteria andOperatingNotEqualTo(Integer value) {
            addCriterion("operating <>", value, "operating");
            return (Criteria) this;
        }

        public Criteria andOperatingGreaterThan(Integer value) {
            addCriterion("operating >", value, "operating");
            return (Criteria) this;
        }

        public Criteria andOperatingGreaterThanOrEqualTo(Integer value) {
            addCriterion("operating >=", value, "operating");
            return (Criteria) this;
        }

        public Criteria andOperatingLessThan(Integer value) {
            addCriterion("operating <", value, "operating");
            return (Criteria) this;
        }

        public Criteria andOperatingLessThanOrEqualTo(Integer value) {
            addCriterion("operating <=", value, "operating");
            return (Criteria) this;
        }

        public Criteria andOperatingIn(List<Integer> values) {
            addCriterion("operating in", values, "operating");
            return (Criteria) this;
        }

        public Criteria andOperatingNotIn(List<Integer> values) {
            addCriterion("operating not in", values, "operating");
            return (Criteria) this;
        }

        public Criteria andOperatingBetween(Integer value1, Integer value2) {
            addCriterion("operating between", value1, value2, "operating");
            return (Criteria) this;
        }

        public Criteria andOperatingNotBetween(Integer value1, Integer value2) {
            addCriterion("operating not between", value1, value2, "operating");
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