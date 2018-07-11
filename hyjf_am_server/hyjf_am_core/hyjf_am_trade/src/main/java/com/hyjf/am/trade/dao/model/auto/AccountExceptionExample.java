package com.hyjf.am.trade.dao.model.auto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AccountExceptionExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public AccountExceptionExample() {
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

        public Criteria andUsernameIsNull() {
            addCriterion("username is null");
            return (Criteria) this;
        }

        public Criteria andUsernameIsNotNull() {
            addCriterion("username is not null");
            return (Criteria) this;
        }

        public Criteria andUsernameEqualTo(String value) {
            addCriterion("username =", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotEqualTo(String value) {
            addCriterion("username <>", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameGreaterThan(String value) {
            addCriterion("username >", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameGreaterThanOrEqualTo(String value) {
            addCriterion("username >=", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLessThan(String value) {
            addCriterion("username <", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLessThanOrEqualTo(String value) {
            addCriterion("username <=", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLike(String value) {
            addCriterion("username like", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotLike(String value) {
            addCriterion("username not like", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameIn(List<String> values) {
            addCriterion("username in", values, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotIn(List<String> values) {
            addCriterion("username not in", values, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameBetween(String value1, String value2) {
            addCriterion("username between", value1, value2, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotBetween(String value1, String value2) {
            addCriterion("username not between", value1, value2, "username");
            return (Criteria) this;
        }

        public Criteria andCustomIdIsNull() {
            addCriterion("custom_id is null");
            return (Criteria) this;
        }

        public Criteria andCustomIdIsNotNull() {
            addCriterion("custom_id is not null");
            return (Criteria) this;
        }

        public Criteria andCustomIdEqualTo(Long value) {
            addCriterion("custom_id =", value, "customId");
            return (Criteria) this;
        }

        public Criteria andCustomIdNotEqualTo(Long value) {
            addCriterion("custom_id <>", value, "customId");
            return (Criteria) this;
        }

        public Criteria andCustomIdGreaterThan(Long value) {
            addCriterion("custom_id >", value, "customId");
            return (Criteria) this;
        }

        public Criteria andCustomIdGreaterThanOrEqualTo(Long value) {
            addCriterion("custom_id >=", value, "customId");
            return (Criteria) this;
        }

        public Criteria andCustomIdLessThan(Long value) {
            addCriterion("custom_id <", value, "customId");
            return (Criteria) this;
        }

        public Criteria andCustomIdLessThanOrEqualTo(Long value) {
            addCriterion("custom_id <=", value, "customId");
            return (Criteria) this;
        }

        public Criteria andCustomIdIn(List<Long> values) {
            addCriterion("custom_id in", values, "customId");
            return (Criteria) this;
        }

        public Criteria andCustomIdNotIn(List<Long> values) {
            addCriterion("custom_id not in", values, "customId");
            return (Criteria) this;
        }

        public Criteria andCustomIdBetween(Long value1, Long value2) {
            addCriterion("custom_id between", value1, value2, "customId");
            return (Criteria) this;
        }

        public Criteria andCustomIdNotBetween(Long value1, Long value2) {
            addCriterion("custom_id not between", value1, value2, "customId");
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

        public Criteria andBalancePlatIsNull() {
            addCriterion("balance_plat is null");
            return (Criteria) this;
        }

        public Criteria andBalancePlatIsNotNull() {
            addCriterion("balance_plat is not null");
            return (Criteria) this;
        }

        public Criteria andBalancePlatEqualTo(BigDecimal value) {
            addCriterion("balance_plat =", value, "balancePlat");
            return (Criteria) this;
        }

        public Criteria andBalancePlatNotEqualTo(BigDecimal value) {
            addCriterion("balance_plat <>", value, "balancePlat");
            return (Criteria) this;
        }

        public Criteria andBalancePlatGreaterThan(BigDecimal value) {
            addCriterion("balance_plat >", value, "balancePlat");
            return (Criteria) this;
        }

        public Criteria andBalancePlatGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("balance_plat >=", value, "balancePlat");
            return (Criteria) this;
        }

        public Criteria andBalancePlatLessThan(BigDecimal value) {
            addCriterion("balance_plat <", value, "balancePlat");
            return (Criteria) this;
        }

        public Criteria andBalancePlatLessThanOrEqualTo(BigDecimal value) {
            addCriterion("balance_plat <=", value, "balancePlat");
            return (Criteria) this;
        }

        public Criteria andBalancePlatIn(List<BigDecimal> values) {
            addCriterion("balance_plat in", values, "balancePlat");
            return (Criteria) this;
        }

        public Criteria andBalancePlatNotIn(List<BigDecimal> values) {
            addCriterion("balance_plat not in", values, "balancePlat");
            return (Criteria) this;
        }

        public Criteria andBalancePlatBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("balance_plat between", value1, value2, "balancePlat");
            return (Criteria) this;
        }

        public Criteria andBalancePlatNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("balance_plat not between", value1, value2, "balancePlat");
            return (Criteria) this;
        }

        public Criteria andFrostPlatIsNull() {
            addCriterion("frost_plat is null");
            return (Criteria) this;
        }

        public Criteria andFrostPlatIsNotNull() {
            addCriterion("frost_plat is not null");
            return (Criteria) this;
        }

        public Criteria andFrostPlatEqualTo(BigDecimal value) {
            addCriterion("frost_plat =", value, "frostPlat");
            return (Criteria) this;
        }

        public Criteria andFrostPlatNotEqualTo(BigDecimal value) {
            addCriterion("frost_plat <>", value, "frostPlat");
            return (Criteria) this;
        }

        public Criteria andFrostPlatGreaterThan(BigDecimal value) {
            addCriterion("frost_plat >", value, "frostPlat");
            return (Criteria) this;
        }

        public Criteria andFrostPlatGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("frost_plat >=", value, "frostPlat");
            return (Criteria) this;
        }

        public Criteria andFrostPlatLessThan(BigDecimal value) {
            addCriterion("frost_plat <", value, "frostPlat");
            return (Criteria) this;
        }

        public Criteria andFrostPlatLessThanOrEqualTo(BigDecimal value) {
            addCriterion("frost_plat <=", value, "frostPlat");
            return (Criteria) this;
        }

        public Criteria andFrostPlatIn(List<BigDecimal> values) {
            addCriterion("frost_plat in", values, "frostPlat");
            return (Criteria) this;
        }

        public Criteria andFrostPlatNotIn(List<BigDecimal> values) {
            addCriterion("frost_plat not in", values, "frostPlat");
            return (Criteria) this;
        }

        public Criteria andFrostPlatBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("frost_plat between", value1, value2, "frostPlat");
            return (Criteria) this;
        }

        public Criteria andFrostPlatNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("frost_plat not between", value1, value2, "frostPlat");
            return (Criteria) this;
        }

        public Criteria andBalanceHuifuIsNull() {
            addCriterion("balance_huifu is null");
            return (Criteria) this;
        }

        public Criteria andBalanceHuifuIsNotNull() {
            addCriterion("balance_huifu is not null");
            return (Criteria) this;
        }

        public Criteria andBalanceHuifuEqualTo(BigDecimal value) {
            addCriterion("balance_huifu =", value, "balanceHuifu");
            return (Criteria) this;
        }

        public Criteria andBalanceHuifuNotEqualTo(BigDecimal value) {
            addCriterion("balance_huifu <>", value, "balanceHuifu");
            return (Criteria) this;
        }

        public Criteria andBalanceHuifuGreaterThan(BigDecimal value) {
            addCriterion("balance_huifu >", value, "balanceHuifu");
            return (Criteria) this;
        }

        public Criteria andBalanceHuifuGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("balance_huifu >=", value, "balanceHuifu");
            return (Criteria) this;
        }

        public Criteria andBalanceHuifuLessThan(BigDecimal value) {
            addCriterion("balance_huifu <", value, "balanceHuifu");
            return (Criteria) this;
        }

        public Criteria andBalanceHuifuLessThanOrEqualTo(BigDecimal value) {
            addCriterion("balance_huifu <=", value, "balanceHuifu");
            return (Criteria) this;
        }

        public Criteria andBalanceHuifuIn(List<BigDecimal> values) {
            addCriterion("balance_huifu in", values, "balanceHuifu");
            return (Criteria) this;
        }

        public Criteria andBalanceHuifuNotIn(List<BigDecimal> values) {
            addCriterion("balance_huifu not in", values, "balanceHuifu");
            return (Criteria) this;
        }

        public Criteria andBalanceHuifuBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("balance_huifu between", value1, value2, "balanceHuifu");
            return (Criteria) this;
        }

        public Criteria andBalanceHuifuNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("balance_huifu not between", value1, value2, "balanceHuifu");
            return (Criteria) this;
        }

        public Criteria andFrostHuifuIsNull() {
            addCriterion("frost_huifu is null");
            return (Criteria) this;
        }

        public Criteria andFrostHuifuIsNotNull() {
            addCriterion("frost_huifu is not null");
            return (Criteria) this;
        }

        public Criteria andFrostHuifuEqualTo(BigDecimal value) {
            addCriterion("frost_huifu =", value, "frostHuifu");
            return (Criteria) this;
        }

        public Criteria andFrostHuifuNotEqualTo(BigDecimal value) {
            addCriterion("frost_huifu <>", value, "frostHuifu");
            return (Criteria) this;
        }

        public Criteria andFrostHuifuGreaterThan(BigDecimal value) {
            addCriterion("frost_huifu >", value, "frostHuifu");
            return (Criteria) this;
        }

        public Criteria andFrostHuifuGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("frost_huifu >=", value, "frostHuifu");
            return (Criteria) this;
        }

        public Criteria andFrostHuifuLessThan(BigDecimal value) {
            addCriterion("frost_huifu <", value, "frostHuifu");
            return (Criteria) this;
        }

        public Criteria andFrostHuifuLessThanOrEqualTo(BigDecimal value) {
            addCriterion("frost_huifu <=", value, "frostHuifu");
            return (Criteria) this;
        }

        public Criteria andFrostHuifuIn(List<BigDecimal> values) {
            addCriterion("frost_huifu in", values, "frostHuifu");
            return (Criteria) this;
        }

        public Criteria andFrostHuifuNotIn(List<BigDecimal> values) {
            addCriterion("frost_huifu not in", values, "frostHuifu");
            return (Criteria) this;
        }

        public Criteria andFrostHuifuBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("frost_huifu between", value1, value2, "frostHuifu");
            return (Criteria) this;
        }

        public Criteria andFrostHuifuNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("frost_huifu not between", value1, value2, "frostHuifu");
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

        public Criteria andRoleIsNull() {
            addCriterion("`role` is null");
            return (Criteria) this;
        }

        public Criteria andRoleIsNotNull() {
            addCriterion("`role` is not null");
            return (Criteria) this;
        }

        public Criteria andRoleEqualTo(String value) {
            addCriterion("`role` =", value, "role");
            return (Criteria) this;
        }

        public Criteria andRoleNotEqualTo(String value) {
            addCriterion("`role` <>", value, "role");
            return (Criteria) this;
        }

        public Criteria andRoleGreaterThan(String value) {
            addCriterion("`role` >", value, "role");
            return (Criteria) this;
        }

        public Criteria andRoleGreaterThanOrEqualTo(String value) {
            addCriterion("`role` >=", value, "role");
            return (Criteria) this;
        }

        public Criteria andRoleLessThan(String value) {
            addCriterion("`role` <", value, "role");
            return (Criteria) this;
        }

        public Criteria andRoleLessThanOrEqualTo(String value) {
            addCriterion("`role` <=", value, "role");
            return (Criteria) this;
        }

        public Criteria andRoleLike(String value) {
            addCriterion("`role` like", value, "role");
            return (Criteria) this;
        }

        public Criteria andRoleNotLike(String value) {
            addCriterion("`role` not like", value, "role");
            return (Criteria) this;
        }

        public Criteria andRoleIn(List<String> values) {
            addCriterion("`role` in", values, "role");
            return (Criteria) this;
        }

        public Criteria andRoleNotIn(List<String> values) {
            addCriterion("`role` not in", values, "role");
            return (Criteria) this;
        }

        public Criteria andRoleBetween(String value1, String value2) {
            addCriterion("`role` between", value1, value2, "role");
            return (Criteria) this;
        }

        public Criteria andRoleNotBetween(String value1, String value2) {
            addCriterion("`role` not between", value1, value2, "role");
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