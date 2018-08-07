package com.hyjf.am.trade.dao.model.auto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DebtPlanAccedeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public DebtPlanAccedeExample() {
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

        public Criteria andPlanNidIsNull() {
            addCriterion("plan_nid is null");
            return (Criteria) this;
        }

        public Criteria andPlanNidIsNotNull() {
            addCriterion("plan_nid is not null");
            return (Criteria) this;
        }

        public Criteria andPlanNidEqualTo(String value) {
            addCriterion("plan_nid =", value, "planNid");
            return (Criteria) this;
        }

        public Criteria andPlanNidNotEqualTo(String value) {
            addCriterion("plan_nid <>", value, "planNid");
            return (Criteria) this;
        }

        public Criteria andPlanNidGreaterThan(String value) {
            addCriterion("plan_nid >", value, "planNid");
            return (Criteria) this;
        }

        public Criteria andPlanNidGreaterThanOrEqualTo(String value) {
            addCriterion("plan_nid >=", value, "planNid");
            return (Criteria) this;
        }

        public Criteria andPlanNidLessThan(String value) {
            addCriterion("plan_nid <", value, "planNid");
            return (Criteria) this;
        }

        public Criteria andPlanNidLessThanOrEqualTo(String value) {
            addCriterion("plan_nid <=", value, "planNid");
            return (Criteria) this;
        }

        public Criteria andPlanNidLike(String value) {
            addCriterion("plan_nid like", value, "planNid");
            return (Criteria) this;
        }

        public Criteria andPlanNidNotLike(String value) {
            addCriterion("plan_nid not like", value, "planNid");
            return (Criteria) this;
        }

        public Criteria andPlanNidIn(List<String> values) {
            addCriterion("plan_nid in", values, "planNid");
            return (Criteria) this;
        }

        public Criteria andPlanNidNotIn(List<String> values) {
            addCriterion("plan_nid not in", values, "planNid");
            return (Criteria) this;
        }

        public Criteria andPlanNidBetween(String value1, String value2) {
            addCriterion("plan_nid between", value1, value2, "planNid");
            return (Criteria) this;
        }

        public Criteria andPlanNidNotBetween(String value1, String value2) {
            addCriterion("plan_nid not between", value1, value2, "planNid");
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

        public Criteria andUserAttributeIsNull() {
            addCriterion("user_attribute is null");
            return (Criteria) this;
        }

        public Criteria andUserAttributeIsNotNull() {
            addCriterion("user_attribute is not null");
            return (Criteria) this;
        }

        public Criteria andUserAttributeEqualTo(Integer value) {
            addCriterion("user_attribute =", value, "userAttribute");
            return (Criteria) this;
        }

        public Criteria andUserAttributeNotEqualTo(Integer value) {
            addCriterion("user_attribute <>", value, "userAttribute");
            return (Criteria) this;
        }

        public Criteria andUserAttributeGreaterThan(Integer value) {
            addCriterion("user_attribute >", value, "userAttribute");
            return (Criteria) this;
        }

        public Criteria andUserAttributeGreaterThanOrEqualTo(Integer value) {
            addCriterion("user_attribute >=", value, "userAttribute");
            return (Criteria) this;
        }

        public Criteria andUserAttributeLessThan(Integer value) {
            addCriterion("user_attribute <", value, "userAttribute");
            return (Criteria) this;
        }

        public Criteria andUserAttributeLessThanOrEqualTo(Integer value) {
            addCriterion("user_attribute <=", value, "userAttribute");
            return (Criteria) this;
        }

        public Criteria andUserAttributeIn(List<Integer> values) {
            addCriterion("user_attribute in", values, "userAttribute");
            return (Criteria) this;
        }

        public Criteria andUserAttributeNotIn(List<Integer> values) {
            addCriterion("user_attribute not in", values, "userAttribute");
            return (Criteria) this;
        }

        public Criteria andUserAttributeBetween(Integer value1, Integer value2) {
            addCriterion("user_attribute between", value1, value2, "userAttribute");
            return (Criteria) this;
        }

        public Criteria andUserAttributeNotBetween(Integer value1, Integer value2) {
            addCriterion("user_attribute not between", value1, value2, "userAttribute");
            return (Criteria) this;
        }

        public Criteria andAccedeOrderIdIsNull() {
            addCriterion("accede_order_id is null");
            return (Criteria) this;
        }

        public Criteria andAccedeOrderIdIsNotNull() {
            addCriterion("accede_order_id is not null");
            return (Criteria) this;
        }

        public Criteria andAccedeOrderIdEqualTo(String value) {
            addCriterion("accede_order_id =", value, "accedeOrderId");
            return (Criteria) this;
        }

        public Criteria andAccedeOrderIdNotEqualTo(String value) {
            addCriterion("accede_order_id <>", value, "accedeOrderId");
            return (Criteria) this;
        }

        public Criteria andAccedeOrderIdGreaterThan(String value) {
            addCriterion("accede_order_id >", value, "accedeOrderId");
            return (Criteria) this;
        }

        public Criteria andAccedeOrderIdGreaterThanOrEqualTo(String value) {
            addCriterion("accede_order_id >=", value, "accedeOrderId");
            return (Criteria) this;
        }

        public Criteria andAccedeOrderIdLessThan(String value) {
            addCriterion("accede_order_id <", value, "accedeOrderId");
            return (Criteria) this;
        }

        public Criteria andAccedeOrderIdLessThanOrEqualTo(String value) {
            addCriterion("accede_order_id <=", value, "accedeOrderId");
            return (Criteria) this;
        }

        public Criteria andAccedeOrderIdLike(String value) {
            addCriterion("accede_order_id like", value, "accedeOrderId");
            return (Criteria) this;
        }

        public Criteria andAccedeOrderIdNotLike(String value) {
            addCriterion("accede_order_id not like", value, "accedeOrderId");
            return (Criteria) this;
        }

        public Criteria andAccedeOrderIdIn(List<String> values) {
            addCriterion("accede_order_id in", values, "accedeOrderId");
            return (Criteria) this;
        }

        public Criteria andAccedeOrderIdNotIn(List<String> values) {
            addCriterion("accede_order_id not in", values, "accedeOrderId");
            return (Criteria) this;
        }

        public Criteria andAccedeOrderIdBetween(String value1, String value2) {
            addCriterion("accede_order_id between", value1, value2, "accedeOrderId");
            return (Criteria) this;
        }

        public Criteria andAccedeOrderIdNotBetween(String value1, String value2) {
            addCriterion("accede_order_id not between", value1, value2, "accedeOrderId");
            return (Criteria) this;
        }

        public Criteria andFreezeOrderIdIsNull() {
            addCriterion("freeze_order_id is null");
            return (Criteria) this;
        }

        public Criteria andFreezeOrderIdIsNotNull() {
            addCriterion("freeze_order_id is not null");
            return (Criteria) this;
        }

        public Criteria andFreezeOrderIdEqualTo(String value) {
            addCriterion("freeze_order_id =", value, "freezeOrderId");
            return (Criteria) this;
        }

        public Criteria andFreezeOrderIdNotEqualTo(String value) {
            addCriterion("freeze_order_id <>", value, "freezeOrderId");
            return (Criteria) this;
        }

        public Criteria andFreezeOrderIdGreaterThan(String value) {
            addCriterion("freeze_order_id >", value, "freezeOrderId");
            return (Criteria) this;
        }

        public Criteria andFreezeOrderIdGreaterThanOrEqualTo(String value) {
            addCriterion("freeze_order_id >=", value, "freezeOrderId");
            return (Criteria) this;
        }

        public Criteria andFreezeOrderIdLessThan(String value) {
            addCriterion("freeze_order_id <", value, "freezeOrderId");
            return (Criteria) this;
        }

        public Criteria andFreezeOrderIdLessThanOrEqualTo(String value) {
            addCriterion("freeze_order_id <=", value, "freezeOrderId");
            return (Criteria) this;
        }

        public Criteria andFreezeOrderIdLike(String value) {
            addCriterion("freeze_order_id like", value, "freezeOrderId");
            return (Criteria) this;
        }

        public Criteria andFreezeOrderIdNotLike(String value) {
            addCriterion("freeze_order_id not like", value, "freezeOrderId");
            return (Criteria) this;
        }

        public Criteria andFreezeOrderIdIn(List<String> values) {
            addCriterion("freeze_order_id in", values, "freezeOrderId");
            return (Criteria) this;
        }

        public Criteria andFreezeOrderIdNotIn(List<String> values) {
            addCriterion("freeze_order_id not in", values, "freezeOrderId");
            return (Criteria) this;
        }

        public Criteria andFreezeOrderIdBetween(String value1, String value2) {
            addCriterion("freeze_order_id between", value1, value2, "freezeOrderId");
            return (Criteria) this;
        }

        public Criteria andFreezeOrderIdNotBetween(String value1, String value2) {
            addCriterion("freeze_order_id not between", value1, value2, "freezeOrderId");
            return (Criteria) this;
        }

        public Criteria andAccedeAccountIsNull() {
            addCriterion("accede_account is null");
            return (Criteria) this;
        }

        public Criteria andAccedeAccountIsNotNull() {
            addCriterion("accede_account is not null");
            return (Criteria) this;
        }

        public Criteria andAccedeAccountEqualTo(BigDecimal value) {
            addCriterion("accede_account =", value, "accedeAccount");
            return (Criteria) this;
        }

        public Criteria andAccedeAccountNotEqualTo(BigDecimal value) {
            addCriterion("accede_account <>", value, "accedeAccount");
            return (Criteria) this;
        }

        public Criteria andAccedeAccountGreaterThan(BigDecimal value) {
            addCriterion("accede_account >", value, "accedeAccount");
            return (Criteria) this;
        }

        public Criteria andAccedeAccountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("accede_account >=", value, "accedeAccount");
            return (Criteria) this;
        }

        public Criteria andAccedeAccountLessThan(BigDecimal value) {
            addCriterion("accede_account <", value, "accedeAccount");
            return (Criteria) this;
        }

        public Criteria andAccedeAccountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("accede_account <=", value, "accedeAccount");
            return (Criteria) this;
        }

        public Criteria andAccedeAccountIn(List<BigDecimal> values) {
            addCriterion("accede_account in", values, "accedeAccount");
            return (Criteria) this;
        }

        public Criteria andAccedeAccountNotIn(List<BigDecimal> values) {
            addCriterion("accede_account not in", values, "accedeAccount");
            return (Criteria) this;
        }

        public Criteria andAccedeAccountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("accede_account between", value1, value2, "accedeAccount");
            return (Criteria) this;
        }

        public Criteria andAccedeAccountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("accede_account not between", value1, value2, "accedeAccount");
            return (Criteria) this;
        }

        public Criteria andAccedeBalanceIsNull() {
            addCriterion("accede_balance is null");
            return (Criteria) this;
        }

        public Criteria andAccedeBalanceIsNotNull() {
            addCriterion("accede_balance is not null");
            return (Criteria) this;
        }

        public Criteria andAccedeBalanceEqualTo(BigDecimal value) {
            addCriterion("accede_balance =", value, "accedeBalance");
            return (Criteria) this;
        }

        public Criteria andAccedeBalanceNotEqualTo(BigDecimal value) {
            addCriterion("accede_balance <>", value, "accedeBalance");
            return (Criteria) this;
        }

        public Criteria andAccedeBalanceGreaterThan(BigDecimal value) {
            addCriterion("accede_balance >", value, "accedeBalance");
            return (Criteria) this;
        }

        public Criteria andAccedeBalanceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("accede_balance >=", value, "accedeBalance");
            return (Criteria) this;
        }

        public Criteria andAccedeBalanceLessThan(BigDecimal value) {
            addCriterion("accede_balance <", value, "accedeBalance");
            return (Criteria) this;
        }

        public Criteria andAccedeBalanceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("accede_balance <=", value, "accedeBalance");
            return (Criteria) this;
        }

        public Criteria andAccedeBalanceIn(List<BigDecimal> values) {
            addCriterion("accede_balance in", values, "accedeBalance");
            return (Criteria) this;
        }

        public Criteria andAccedeBalanceNotIn(List<BigDecimal> values) {
            addCriterion("accede_balance not in", values, "accedeBalance");
            return (Criteria) this;
        }

        public Criteria andAccedeBalanceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("accede_balance between", value1, value2, "accedeBalance");
            return (Criteria) this;
        }

        public Criteria andAccedeBalanceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("accede_balance not between", value1, value2, "accedeBalance");
            return (Criteria) this;
        }

        public Criteria andAccedeFrostIsNull() {
            addCriterion("accede_frost is null");
            return (Criteria) this;
        }

        public Criteria andAccedeFrostIsNotNull() {
            addCriterion("accede_frost is not null");
            return (Criteria) this;
        }

        public Criteria andAccedeFrostEqualTo(BigDecimal value) {
            addCriterion("accede_frost =", value, "accedeFrost");
            return (Criteria) this;
        }

        public Criteria andAccedeFrostNotEqualTo(BigDecimal value) {
            addCriterion("accede_frost <>", value, "accedeFrost");
            return (Criteria) this;
        }

        public Criteria andAccedeFrostGreaterThan(BigDecimal value) {
            addCriterion("accede_frost >", value, "accedeFrost");
            return (Criteria) this;
        }

        public Criteria andAccedeFrostGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("accede_frost >=", value, "accedeFrost");
            return (Criteria) this;
        }

        public Criteria andAccedeFrostLessThan(BigDecimal value) {
            addCriterion("accede_frost <", value, "accedeFrost");
            return (Criteria) this;
        }

        public Criteria andAccedeFrostLessThanOrEqualTo(BigDecimal value) {
            addCriterion("accede_frost <=", value, "accedeFrost");
            return (Criteria) this;
        }

        public Criteria andAccedeFrostIn(List<BigDecimal> values) {
            addCriterion("accede_frost in", values, "accedeFrost");
            return (Criteria) this;
        }

        public Criteria andAccedeFrostNotIn(List<BigDecimal> values) {
            addCriterion("accede_frost not in", values, "accedeFrost");
            return (Criteria) this;
        }

        public Criteria andAccedeFrostBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("accede_frost between", value1, value2, "accedeFrost");
            return (Criteria) this;
        }

        public Criteria andAccedeFrostNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("accede_frost not between", value1, value2, "accedeFrost");
            return (Criteria) this;
        }

        public Criteria andLiquidatesCreditFrostIsNull() {
            addCriterion("liquidates_credit_frost is null");
            return (Criteria) this;
        }

        public Criteria andLiquidatesCreditFrostIsNotNull() {
            addCriterion("liquidates_credit_frost is not null");
            return (Criteria) this;
        }

        public Criteria andLiquidatesCreditFrostEqualTo(BigDecimal value) {
            addCriterion("liquidates_credit_frost =", value, "liquidatesCreditFrost");
            return (Criteria) this;
        }

        public Criteria andLiquidatesCreditFrostNotEqualTo(BigDecimal value) {
            addCriterion("liquidates_credit_frost <>", value, "liquidatesCreditFrost");
            return (Criteria) this;
        }

        public Criteria andLiquidatesCreditFrostGreaterThan(BigDecimal value) {
            addCriterion("liquidates_credit_frost >", value, "liquidatesCreditFrost");
            return (Criteria) this;
        }

        public Criteria andLiquidatesCreditFrostGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("liquidates_credit_frost >=", value, "liquidatesCreditFrost");
            return (Criteria) this;
        }

        public Criteria andLiquidatesCreditFrostLessThan(BigDecimal value) {
            addCriterion("liquidates_credit_frost <", value, "liquidatesCreditFrost");
            return (Criteria) this;
        }

        public Criteria andLiquidatesCreditFrostLessThanOrEqualTo(BigDecimal value) {
            addCriterion("liquidates_credit_frost <=", value, "liquidatesCreditFrost");
            return (Criteria) this;
        }

        public Criteria andLiquidatesCreditFrostIn(List<BigDecimal> values) {
            addCriterion("liquidates_credit_frost in", values, "liquidatesCreditFrost");
            return (Criteria) this;
        }

        public Criteria andLiquidatesCreditFrostNotIn(List<BigDecimal> values) {
            addCriterion("liquidates_credit_frost not in", values, "liquidatesCreditFrost");
            return (Criteria) this;
        }

        public Criteria andLiquidatesCreditFrostBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("liquidates_credit_frost between", value1, value2, "liquidatesCreditFrost");
            return (Criteria) this;
        }

        public Criteria andLiquidatesCreditFrostNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("liquidates_credit_frost not between", value1, value2, "liquidatesCreditFrost");
            return (Criteria) this;
        }

        public Criteria andLiquidatesAprIsNull() {
            addCriterion("liquidates_apr is null");
            return (Criteria) this;
        }

        public Criteria andLiquidatesAprIsNotNull() {
            addCriterion("liquidates_apr is not null");
            return (Criteria) this;
        }

        public Criteria andLiquidatesAprEqualTo(BigDecimal value) {
            addCriterion("liquidates_apr =", value, "liquidatesApr");
            return (Criteria) this;
        }

        public Criteria andLiquidatesAprNotEqualTo(BigDecimal value) {
            addCriterion("liquidates_apr <>", value, "liquidatesApr");
            return (Criteria) this;
        }

        public Criteria andLiquidatesAprGreaterThan(BigDecimal value) {
            addCriterion("liquidates_apr >", value, "liquidatesApr");
            return (Criteria) this;
        }

        public Criteria andLiquidatesAprGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("liquidates_apr >=", value, "liquidatesApr");
            return (Criteria) this;
        }

        public Criteria andLiquidatesAprLessThan(BigDecimal value) {
            addCriterion("liquidates_apr <", value, "liquidatesApr");
            return (Criteria) this;
        }

        public Criteria andLiquidatesAprLessThanOrEqualTo(BigDecimal value) {
            addCriterion("liquidates_apr <=", value, "liquidatesApr");
            return (Criteria) this;
        }

        public Criteria andLiquidatesAprIn(List<BigDecimal> values) {
            addCriterion("liquidates_apr in", values, "liquidatesApr");
            return (Criteria) this;
        }

        public Criteria andLiquidatesAprNotIn(List<BigDecimal> values) {
            addCriterion("liquidates_apr not in", values, "liquidatesApr");
            return (Criteria) this;
        }

        public Criteria andLiquidatesAprBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("liquidates_apr between", value1, value2, "liquidatesApr");
            return (Criteria) this;
        }

        public Criteria andLiquidatesAprNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("liquidates_apr not between", value1, value2, "liquidatesApr");
            return (Criteria) this;
        }

        public Criteria andLiquidatesRepayFrostIsNull() {
            addCriterion("liquidates_repay_frost is null");
            return (Criteria) this;
        }

        public Criteria andLiquidatesRepayFrostIsNotNull() {
            addCriterion("liquidates_repay_frost is not null");
            return (Criteria) this;
        }

        public Criteria andLiquidatesRepayFrostEqualTo(BigDecimal value) {
            addCriterion("liquidates_repay_frost =", value, "liquidatesRepayFrost");
            return (Criteria) this;
        }

        public Criteria andLiquidatesRepayFrostNotEqualTo(BigDecimal value) {
            addCriterion("liquidates_repay_frost <>", value, "liquidatesRepayFrost");
            return (Criteria) this;
        }

        public Criteria andLiquidatesRepayFrostGreaterThan(BigDecimal value) {
            addCriterion("liquidates_repay_frost >", value, "liquidatesRepayFrost");
            return (Criteria) this;
        }

        public Criteria andLiquidatesRepayFrostGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("liquidates_repay_frost >=", value, "liquidatesRepayFrost");
            return (Criteria) this;
        }

        public Criteria andLiquidatesRepayFrostLessThan(BigDecimal value) {
            addCriterion("liquidates_repay_frost <", value, "liquidatesRepayFrost");
            return (Criteria) this;
        }

        public Criteria andLiquidatesRepayFrostLessThanOrEqualTo(BigDecimal value) {
            addCriterion("liquidates_repay_frost <=", value, "liquidatesRepayFrost");
            return (Criteria) this;
        }

        public Criteria andLiquidatesRepayFrostIn(List<BigDecimal> values) {
            addCriterion("liquidates_repay_frost in", values, "liquidatesRepayFrost");
            return (Criteria) this;
        }

        public Criteria andLiquidatesRepayFrostNotIn(List<BigDecimal> values) {
            addCriterion("liquidates_repay_frost not in", values, "liquidatesRepayFrost");
            return (Criteria) this;
        }

        public Criteria andLiquidatesRepayFrostBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("liquidates_repay_frost between", value1, value2, "liquidatesRepayFrost");
            return (Criteria) this;
        }

        public Criteria andLiquidatesRepayFrostNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("liquidates_repay_frost not between", value1, value2, "liquidatesRepayFrost");
            return (Criteria) this;
        }

        public Criteria andServiceFeeRateIsNull() {
            addCriterion("service_fee_rate is null");
            return (Criteria) this;
        }

        public Criteria andServiceFeeRateIsNotNull() {
            addCriterion("service_fee_rate is not null");
            return (Criteria) this;
        }

        public Criteria andServiceFeeRateEqualTo(BigDecimal value) {
            addCriterion("service_fee_rate =", value, "serviceFeeRate");
            return (Criteria) this;
        }

        public Criteria andServiceFeeRateNotEqualTo(BigDecimal value) {
            addCriterion("service_fee_rate <>", value, "serviceFeeRate");
            return (Criteria) this;
        }

        public Criteria andServiceFeeRateGreaterThan(BigDecimal value) {
            addCriterion("service_fee_rate >", value, "serviceFeeRate");
            return (Criteria) this;
        }

        public Criteria andServiceFeeRateGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("service_fee_rate >=", value, "serviceFeeRate");
            return (Criteria) this;
        }

        public Criteria andServiceFeeRateLessThan(BigDecimal value) {
            addCriterion("service_fee_rate <", value, "serviceFeeRate");
            return (Criteria) this;
        }

        public Criteria andServiceFeeRateLessThanOrEqualTo(BigDecimal value) {
            addCriterion("service_fee_rate <=", value, "serviceFeeRate");
            return (Criteria) this;
        }

        public Criteria andServiceFeeRateIn(List<BigDecimal> values) {
            addCriterion("service_fee_rate in", values, "serviceFeeRate");
            return (Criteria) this;
        }

        public Criteria andServiceFeeRateNotIn(List<BigDecimal> values) {
            addCriterion("service_fee_rate not in", values, "serviceFeeRate");
            return (Criteria) this;
        }

        public Criteria andServiceFeeRateBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("service_fee_rate between", value1, value2, "serviceFeeRate");
            return (Criteria) this;
        }

        public Criteria andServiceFeeRateNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("service_fee_rate not between", value1, value2, "serviceFeeRate");
            return (Criteria) this;
        }

        public Criteria andServiceFeeIsNull() {
            addCriterion("service_fee is null");
            return (Criteria) this;
        }

        public Criteria andServiceFeeIsNotNull() {
            addCriterion("service_fee is not null");
            return (Criteria) this;
        }

        public Criteria andServiceFeeEqualTo(BigDecimal value) {
            addCriterion("service_fee =", value, "serviceFee");
            return (Criteria) this;
        }

        public Criteria andServiceFeeNotEqualTo(BigDecimal value) {
            addCriterion("service_fee <>", value, "serviceFee");
            return (Criteria) this;
        }

        public Criteria andServiceFeeGreaterThan(BigDecimal value) {
            addCriterion("service_fee >", value, "serviceFee");
            return (Criteria) this;
        }

        public Criteria andServiceFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("service_fee >=", value, "serviceFee");
            return (Criteria) this;
        }

        public Criteria andServiceFeeLessThan(BigDecimal value) {
            addCriterion("service_fee <", value, "serviceFee");
            return (Criteria) this;
        }

        public Criteria andServiceFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("service_fee <=", value, "serviceFee");
            return (Criteria) this;
        }

        public Criteria andServiceFeeIn(List<BigDecimal> values) {
            addCriterion("service_fee in", values, "serviceFee");
            return (Criteria) this;
        }

        public Criteria andServiceFeeNotIn(List<BigDecimal> values) {
            addCriterion("service_fee not in", values, "serviceFee");
            return (Criteria) this;
        }

        public Criteria andServiceFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("service_fee between", value1, value2, "serviceFee");
            return (Criteria) this;
        }

        public Criteria andServiceFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("service_fee not between", value1, value2, "serviceFee");
            return (Criteria) this;
        }

        public Criteria andExpireFairValueIsNull() {
            addCriterion("expire_fair_value is null");
            return (Criteria) this;
        }

        public Criteria andExpireFairValueIsNotNull() {
            addCriterion("expire_fair_value is not null");
            return (Criteria) this;
        }

        public Criteria andExpireFairValueEqualTo(BigDecimal value) {
            addCriterion("expire_fair_value =", value, "expireFairValue");
            return (Criteria) this;
        }

        public Criteria andExpireFairValueNotEqualTo(BigDecimal value) {
            addCriterion("expire_fair_value <>", value, "expireFairValue");
            return (Criteria) this;
        }

        public Criteria andExpireFairValueGreaterThan(BigDecimal value) {
            addCriterion("expire_fair_value >", value, "expireFairValue");
            return (Criteria) this;
        }

        public Criteria andExpireFairValueGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("expire_fair_value >=", value, "expireFairValue");
            return (Criteria) this;
        }

        public Criteria andExpireFairValueLessThan(BigDecimal value) {
            addCriterion("expire_fair_value <", value, "expireFairValue");
            return (Criteria) this;
        }

        public Criteria andExpireFairValueLessThanOrEqualTo(BigDecimal value) {
            addCriterion("expire_fair_value <=", value, "expireFairValue");
            return (Criteria) this;
        }

        public Criteria andExpireFairValueIn(List<BigDecimal> values) {
            addCriterion("expire_fair_value in", values, "expireFairValue");
            return (Criteria) this;
        }

        public Criteria andExpireFairValueNotIn(List<BigDecimal> values) {
            addCriterion("expire_fair_value not in", values, "expireFairValue");
            return (Criteria) this;
        }

        public Criteria andExpireFairValueBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("expire_fair_value between", value1, value2, "expireFairValue");
            return (Criteria) this;
        }

        public Criteria andExpireFairValueNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("expire_fair_value not between", value1, value2, "expireFairValue");
            return (Criteria) this;
        }

        public Criteria andUnderTakeTimesIsNull() {
            addCriterion("under_take_times is null");
            return (Criteria) this;
        }

        public Criteria andUnderTakeTimesIsNotNull() {
            addCriterion("under_take_times is not null");
            return (Criteria) this;
        }

        public Criteria andUnderTakeTimesEqualTo(Integer value) {
            addCriterion("under_take_times =", value, "underTakeTimes");
            return (Criteria) this;
        }

        public Criteria andUnderTakeTimesNotEqualTo(Integer value) {
            addCriterion("under_take_times <>", value, "underTakeTimes");
            return (Criteria) this;
        }

        public Criteria andUnderTakeTimesGreaterThan(Integer value) {
            addCriterion("under_take_times >", value, "underTakeTimes");
            return (Criteria) this;
        }

        public Criteria andUnderTakeTimesGreaterThanOrEqualTo(Integer value) {
            addCriterion("under_take_times >=", value, "underTakeTimes");
            return (Criteria) this;
        }

        public Criteria andUnderTakeTimesLessThan(Integer value) {
            addCriterion("under_take_times <", value, "underTakeTimes");
            return (Criteria) this;
        }

        public Criteria andUnderTakeTimesLessThanOrEqualTo(Integer value) {
            addCriterion("under_take_times <=", value, "underTakeTimes");
            return (Criteria) this;
        }

        public Criteria andUnderTakeTimesIn(List<Integer> values) {
            addCriterion("under_take_times in", values, "underTakeTimes");
            return (Criteria) this;
        }

        public Criteria andUnderTakeTimesNotIn(List<Integer> values) {
            addCriterion("under_take_times not in", values, "underTakeTimes");
            return (Criteria) this;
        }

        public Criteria andUnderTakeTimesBetween(Integer value1, Integer value2) {
            addCriterion("under_take_times between", value1, value2, "underTakeTimes");
            return (Criteria) this;
        }

        public Criteria andUnderTakeTimesNotBetween(Integer value1, Integer value2) {
            addCriterion("under_take_times not between", value1, value2, "underTakeTimes");
            return (Criteria) this;
        }

        public Criteria andInvestMaxIsNull() {
            addCriterion("invest_max is null");
            return (Criteria) this;
        }

        public Criteria andInvestMaxIsNotNull() {
            addCriterion("invest_max is not null");
            return (Criteria) this;
        }

        public Criteria andInvestMaxEqualTo(BigDecimal value) {
            addCriterion("invest_max =", value, "investMax");
            return (Criteria) this;
        }

        public Criteria andInvestMaxNotEqualTo(BigDecimal value) {
            addCriterion("invest_max <>", value, "investMax");
            return (Criteria) this;
        }

        public Criteria andInvestMaxGreaterThan(BigDecimal value) {
            addCriterion("invest_max >", value, "investMax");
            return (Criteria) this;
        }

        public Criteria andInvestMaxGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("invest_max >=", value, "investMax");
            return (Criteria) this;
        }

        public Criteria andInvestMaxLessThan(BigDecimal value) {
            addCriterion("invest_max <", value, "investMax");
            return (Criteria) this;
        }

        public Criteria andInvestMaxLessThanOrEqualTo(BigDecimal value) {
            addCriterion("invest_max <=", value, "investMax");
            return (Criteria) this;
        }

        public Criteria andInvestMaxIn(List<BigDecimal> values) {
            addCriterion("invest_max in", values, "investMax");
            return (Criteria) this;
        }

        public Criteria andInvestMaxNotIn(List<BigDecimal> values) {
            addCriterion("invest_max not in", values, "investMax");
            return (Criteria) this;
        }

        public Criteria andInvestMaxBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("invest_max between", value1, value2, "investMax");
            return (Criteria) this;
        }

        public Criteria andInvestMaxNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("invest_max not between", value1, value2, "investMax");
            return (Criteria) this;
        }

        public Criteria andInvestMinIsNull() {
            addCriterion("invest_min is null");
            return (Criteria) this;
        }

        public Criteria andInvestMinIsNotNull() {
            addCriterion("invest_min is not null");
            return (Criteria) this;
        }

        public Criteria andInvestMinEqualTo(BigDecimal value) {
            addCriterion("invest_min =", value, "investMin");
            return (Criteria) this;
        }

        public Criteria andInvestMinNotEqualTo(BigDecimal value) {
            addCriterion("invest_min <>", value, "investMin");
            return (Criteria) this;
        }

        public Criteria andInvestMinGreaterThan(BigDecimal value) {
            addCriterion("invest_min >", value, "investMin");
            return (Criteria) this;
        }

        public Criteria andInvestMinGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("invest_min >=", value, "investMin");
            return (Criteria) this;
        }

        public Criteria andInvestMinLessThan(BigDecimal value) {
            addCriterion("invest_min <", value, "investMin");
            return (Criteria) this;
        }

        public Criteria andInvestMinLessThanOrEqualTo(BigDecimal value) {
            addCriterion("invest_min <=", value, "investMin");
            return (Criteria) this;
        }

        public Criteria andInvestMinIn(List<BigDecimal> values) {
            addCriterion("invest_min in", values, "investMin");
            return (Criteria) this;
        }

        public Criteria andInvestMinNotIn(List<BigDecimal> values) {
            addCriterion("invest_min not in", values, "investMin");
            return (Criteria) this;
        }

        public Criteria andInvestMinBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("invest_min between", value1, value2, "investMin");
            return (Criteria) this;
        }

        public Criteria andInvestMinNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("invest_min not between", value1, value2, "investMin");
            return (Criteria) this;
        }

        public Criteria andCycleTimesIsNull() {
            addCriterion("cycle_times is null");
            return (Criteria) this;
        }

        public Criteria andCycleTimesIsNotNull() {
            addCriterion("cycle_times is not null");
            return (Criteria) this;
        }

        public Criteria andCycleTimesEqualTo(Integer value) {
            addCriterion("cycle_times =", value, "cycleTimes");
            return (Criteria) this;
        }

        public Criteria andCycleTimesNotEqualTo(Integer value) {
            addCriterion("cycle_times <>", value, "cycleTimes");
            return (Criteria) this;
        }

        public Criteria andCycleTimesGreaterThan(Integer value) {
            addCriterion("cycle_times >", value, "cycleTimes");
            return (Criteria) this;
        }

        public Criteria andCycleTimesGreaterThanOrEqualTo(Integer value) {
            addCriterion("cycle_times >=", value, "cycleTimes");
            return (Criteria) this;
        }

        public Criteria andCycleTimesLessThan(Integer value) {
            addCriterion("cycle_times <", value, "cycleTimes");
            return (Criteria) this;
        }

        public Criteria andCycleTimesLessThanOrEqualTo(Integer value) {
            addCriterion("cycle_times <=", value, "cycleTimes");
            return (Criteria) this;
        }

        public Criteria andCycleTimesIn(List<Integer> values) {
            addCriterion("cycle_times in", values, "cycleTimes");
            return (Criteria) this;
        }

        public Criteria andCycleTimesNotIn(List<Integer> values) {
            addCriterion("cycle_times not in", values, "cycleTimes");
            return (Criteria) this;
        }

        public Criteria andCycleTimesBetween(Integer value1, Integer value2) {
            addCriterion("cycle_times between", value1, value2, "cycleTimes");
            return (Criteria) this;
        }

        public Criteria andCycleTimesNotBetween(Integer value1, Integer value2) {
            addCriterion("cycle_times not between", value1, value2, "cycleTimes");
            return (Criteria) this;
        }

        public Criteria andRepayAccountIsNull() {
            addCriterion("repay_account is null");
            return (Criteria) this;
        }

        public Criteria andRepayAccountIsNotNull() {
            addCriterion("repay_account is not null");
            return (Criteria) this;
        }

        public Criteria andRepayAccountEqualTo(BigDecimal value) {
            addCriterion("repay_account =", value, "repayAccount");
            return (Criteria) this;
        }

        public Criteria andRepayAccountNotEqualTo(BigDecimal value) {
            addCriterion("repay_account <>", value, "repayAccount");
            return (Criteria) this;
        }

        public Criteria andRepayAccountGreaterThan(BigDecimal value) {
            addCriterion("repay_account >", value, "repayAccount");
            return (Criteria) this;
        }

        public Criteria andRepayAccountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_account >=", value, "repayAccount");
            return (Criteria) this;
        }

        public Criteria andRepayAccountLessThan(BigDecimal value) {
            addCriterion("repay_account <", value, "repayAccount");
            return (Criteria) this;
        }

        public Criteria andRepayAccountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_account <=", value, "repayAccount");
            return (Criteria) this;
        }

        public Criteria andRepayAccountIn(List<BigDecimal> values) {
            addCriterion("repay_account in", values, "repayAccount");
            return (Criteria) this;
        }

        public Criteria andRepayAccountNotIn(List<BigDecimal> values) {
            addCriterion("repay_account not in", values, "repayAccount");
            return (Criteria) this;
        }

        public Criteria andRepayAccountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_account between", value1, value2, "repayAccount");
            return (Criteria) this;
        }

        public Criteria andRepayAccountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_account not between", value1, value2, "repayAccount");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalIsNull() {
            addCriterion("repay_capital is null");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalIsNotNull() {
            addCriterion("repay_capital is not null");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalEqualTo(BigDecimal value) {
            addCriterion("repay_capital =", value, "repayCapital");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalNotEqualTo(BigDecimal value) {
            addCriterion("repay_capital <>", value, "repayCapital");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalGreaterThan(BigDecimal value) {
            addCriterion("repay_capital >", value, "repayCapital");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_capital >=", value, "repayCapital");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalLessThan(BigDecimal value) {
            addCriterion("repay_capital <", value, "repayCapital");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalLessThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_capital <=", value, "repayCapital");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalIn(List<BigDecimal> values) {
            addCriterion("repay_capital in", values, "repayCapital");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalNotIn(List<BigDecimal> values) {
            addCriterion("repay_capital not in", values, "repayCapital");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_capital between", value1, value2, "repayCapital");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_capital not between", value1, value2, "repayCapital");
            return (Criteria) this;
        }

        public Criteria andRepayInterestIsNull() {
            addCriterion("repay_interest is null");
            return (Criteria) this;
        }

        public Criteria andRepayInterestIsNotNull() {
            addCriterion("repay_interest is not null");
            return (Criteria) this;
        }

        public Criteria andRepayInterestEqualTo(BigDecimal value) {
            addCriterion("repay_interest =", value, "repayInterest");
            return (Criteria) this;
        }

        public Criteria andRepayInterestNotEqualTo(BigDecimal value) {
            addCriterion("repay_interest <>", value, "repayInterest");
            return (Criteria) this;
        }

        public Criteria andRepayInterestGreaterThan(BigDecimal value) {
            addCriterion("repay_interest >", value, "repayInterest");
            return (Criteria) this;
        }

        public Criteria andRepayInterestGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_interest >=", value, "repayInterest");
            return (Criteria) this;
        }

        public Criteria andRepayInterestLessThan(BigDecimal value) {
            addCriterion("repay_interest <", value, "repayInterest");
            return (Criteria) this;
        }

        public Criteria andRepayInterestLessThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_interest <=", value, "repayInterest");
            return (Criteria) this;
        }

        public Criteria andRepayInterestIn(List<BigDecimal> values) {
            addCriterion("repay_interest in", values, "repayInterest");
            return (Criteria) this;
        }

        public Criteria andRepayInterestNotIn(List<BigDecimal> values) {
            addCriterion("repay_interest not in", values, "repayInterest");
            return (Criteria) this;
        }

        public Criteria andRepayInterestBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_interest between", value1, value2, "repayInterest");
            return (Criteria) this;
        }

        public Criteria andRepayInterestNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_interest not between", value1, value2, "repayInterest");
            return (Criteria) this;
        }

        public Criteria andRepayAccountWaitIsNull() {
            addCriterion("repay_account_wait is null");
            return (Criteria) this;
        }

        public Criteria andRepayAccountWaitIsNotNull() {
            addCriterion("repay_account_wait is not null");
            return (Criteria) this;
        }

        public Criteria andRepayAccountWaitEqualTo(BigDecimal value) {
            addCriterion("repay_account_wait =", value, "repayAccountWait");
            return (Criteria) this;
        }

        public Criteria andRepayAccountWaitNotEqualTo(BigDecimal value) {
            addCriterion("repay_account_wait <>", value, "repayAccountWait");
            return (Criteria) this;
        }

        public Criteria andRepayAccountWaitGreaterThan(BigDecimal value) {
            addCriterion("repay_account_wait >", value, "repayAccountWait");
            return (Criteria) this;
        }

        public Criteria andRepayAccountWaitGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_account_wait >=", value, "repayAccountWait");
            return (Criteria) this;
        }

        public Criteria andRepayAccountWaitLessThan(BigDecimal value) {
            addCriterion("repay_account_wait <", value, "repayAccountWait");
            return (Criteria) this;
        }

        public Criteria andRepayAccountWaitLessThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_account_wait <=", value, "repayAccountWait");
            return (Criteria) this;
        }

        public Criteria andRepayAccountWaitIn(List<BigDecimal> values) {
            addCriterion("repay_account_wait in", values, "repayAccountWait");
            return (Criteria) this;
        }

        public Criteria andRepayAccountWaitNotIn(List<BigDecimal> values) {
            addCriterion("repay_account_wait not in", values, "repayAccountWait");
            return (Criteria) this;
        }

        public Criteria andRepayAccountWaitBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_account_wait between", value1, value2, "repayAccountWait");
            return (Criteria) this;
        }

        public Criteria andRepayAccountWaitNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_account_wait not between", value1, value2, "repayAccountWait");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalWaitIsNull() {
            addCriterion("repay_capital_wait is null");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalWaitIsNotNull() {
            addCriterion("repay_capital_wait is not null");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalWaitEqualTo(BigDecimal value) {
            addCriterion("repay_capital_wait =", value, "repayCapitalWait");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalWaitNotEqualTo(BigDecimal value) {
            addCriterion("repay_capital_wait <>", value, "repayCapitalWait");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalWaitGreaterThan(BigDecimal value) {
            addCriterion("repay_capital_wait >", value, "repayCapitalWait");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalWaitGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_capital_wait >=", value, "repayCapitalWait");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalWaitLessThan(BigDecimal value) {
            addCriterion("repay_capital_wait <", value, "repayCapitalWait");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalWaitLessThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_capital_wait <=", value, "repayCapitalWait");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalWaitIn(List<BigDecimal> values) {
            addCriterion("repay_capital_wait in", values, "repayCapitalWait");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalWaitNotIn(List<BigDecimal> values) {
            addCriterion("repay_capital_wait not in", values, "repayCapitalWait");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalWaitBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_capital_wait between", value1, value2, "repayCapitalWait");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalWaitNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_capital_wait not between", value1, value2, "repayCapitalWait");
            return (Criteria) this;
        }

        public Criteria andRepayInterestWaitIsNull() {
            addCriterion("repay_interest_wait is null");
            return (Criteria) this;
        }

        public Criteria andRepayInterestWaitIsNotNull() {
            addCriterion("repay_interest_wait is not null");
            return (Criteria) this;
        }

        public Criteria andRepayInterestWaitEqualTo(BigDecimal value) {
            addCriterion("repay_interest_wait =", value, "repayInterestWait");
            return (Criteria) this;
        }

        public Criteria andRepayInterestWaitNotEqualTo(BigDecimal value) {
            addCriterion("repay_interest_wait <>", value, "repayInterestWait");
            return (Criteria) this;
        }

        public Criteria andRepayInterestWaitGreaterThan(BigDecimal value) {
            addCriterion("repay_interest_wait >", value, "repayInterestWait");
            return (Criteria) this;
        }

        public Criteria andRepayInterestWaitGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_interest_wait >=", value, "repayInterestWait");
            return (Criteria) this;
        }

        public Criteria andRepayInterestWaitLessThan(BigDecimal value) {
            addCriterion("repay_interest_wait <", value, "repayInterestWait");
            return (Criteria) this;
        }

        public Criteria andRepayInterestWaitLessThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_interest_wait <=", value, "repayInterestWait");
            return (Criteria) this;
        }

        public Criteria andRepayInterestWaitIn(List<BigDecimal> values) {
            addCriterion("repay_interest_wait in", values, "repayInterestWait");
            return (Criteria) this;
        }

        public Criteria andRepayInterestWaitNotIn(List<BigDecimal> values) {
            addCriterion("repay_interest_wait not in", values, "repayInterestWait");
            return (Criteria) this;
        }

        public Criteria andRepayInterestWaitBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_interest_wait between", value1, value2, "repayInterestWait");
            return (Criteria) this;
        }

        public Criteria andRepayInterestWaitNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_interest_wait not between", value1, value2, "repayInterestWait");
            return (Criteria) this;
        }

        public Criteria andRepayAccountYesIsNull() {
            addCriterion("repay_account_yes is null");
            return (Criteria) this;
        }

        public Criteria andRepayAccountYesIsNotNull() {
            addCriterion("repay_account_yes is not null");
            return (Criteria) this;
        }

        public Criteria andRepayAccountYesEqualTo(BigDecimal value) {
            addCriterion("repay_account_yes =", value, "repayAccountYes");
            return (Criteria) this;
        }

        public Criteria andRepayAccountYesNotEqualTo(BigDecimal value) {
            addCriterion("repay_account_yes <>", value, "repayAccountYes");
            return (Criteria) this;
        }

        public Criteria andRepayAccountYesGreaterThan(BigDecimal value) {
            addCriterion("repay_account_yes >", value, "repayAccountYes");
            return (Criteria) this;
        }

        public Criteria andRepayAccountYesGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_account_yes >=", value, "repayAccountYes");
            return (Criteria) this;
        }

        public Criteria andRepayAccountYesLessThan(BigDecimal value) {
            addCriterion("repay_account_yes <", value, "repayAccountYes");
            return (Criteria) this;
        }

        public Criteria andRepayAccountYesLessThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_account_yes <=", value, "repayAccountYes");
            return (Criteria) this;
        }

        public Criteria andRepayAccountYesIn(List<BigDecimal> values) {
            addCriterion("repay_account_yes in", values, "repayAccountYes");
            return (Criteria) this;
        }

        public Criteria andRepayAccountYesNotIn(List<BigDecimal> values) {
            addCriterion("repay_account_yes not in", values, "repayAccountYes");
            return (Criteria) this;
        }

        public Criteria andRepayAccountYesBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_account_yes between", value1, value2, "repayAccountYes");
            return (Criteria) this;
        }

        public Criteria andRepayAccountYesNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_account_yes not between", value1, value2, "repayAccountYes");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalYesIsNull() {
            addCriterion("repay_capital_yes is null");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalYesIsNotNull() {
            addCriterion("repay_capital_yes is not null");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalYesEqualTo(BigDecimal value) {
            addCriterion("repay_capital_yes =", value, "repayCapitalYes");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalYesNotEqualTo(BigDecimal value) {
            addCriterion("repay_capital_yes <>", value, "repayCapitalYes");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalYesGreaterThan(BigDecimal value) {
            addCriterion("repay_capital_yes >", value, "repayCapitalYes");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalYesGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_capital_yes >=", value, "repayCapitalYes");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalYesLessThan(BigDecimal value) {
            addCriterion("repay_capital_yes <", value, "repayCapitalYes");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalYesLessThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_capital_yes <=", value, "repayCapitalYes");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalYesIn(List<BigDecimal> values) {
            addCriterion("repay_capital_yes in", values, "repayCapitalYes");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalYesNotIn(List<BigDecimal> values) {
            addCriterion("repay_capital_yes not in", values, "repayCapitalYes");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalYesBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_capital_yes between", value1, value2, "repayCapitalYes");
            return (Criteria) this;
        }

        public Criteria andRepayCapitalYesNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_capital_yes not between", value1, value2, "repayCapitalYes");
            return (Criteria) this;
        }

        public Criteria andRepayInterestYesIsNull() {
            addCriterion("repay_interest_yes is null");
            return (Criteria) this;
        }

        public Criteria andRepayInterestYesIsNotNull() {
            addCriterion("repay_interest_yes is not null");
            return (Criteria) this;
        }

        public Criteria andRepayInterestYesEqualTo(BigDecimal value) {
            addCriterion("repay_interest_yes =", value, "repayInterestYes");
            return (Criteria) this;
        }

        public Criteria andRepayInterestYesNotEqualTo(BigDecimal value) {
            addCriterion("repay_interest_yes <>", value, "repayInterestYes");
            return (Criteria) this;
        }

        public Criteria andRepayInterestYesGreaterThan(BigDecimal value) {
            addCriterion("repay_interest_yes >", value, "repayInterestYes");
            return (Criteria) this;
        }

        public Criteria andRepayInterestYesGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_interest_yes >=", value, "repayInterestYes");
            return (Criteria) this;
        }

        public Criteria andRepayInterestYesLessThan(BigDecimal value) {
            addCriterion("repay_interest_yes <", value, "repayInterestYes");
            return (Criteria) this;
        }

        public Criteria andRepayInterestYesLessThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_interest_yes <=", value, "repayInterestYes");
            return (Criteria) this;
        }

        public Criteria andRepayInterestYesIn(List<BigDecimal> values) {
            addCriterion("repay_interest_yes in", values, "repayInterestYes");
            return (Criteria) this;
        }

        public Criteria andRepayInterestYesNotIn(List<BigDecimal> values) {
            addCriterion("repay_interest_yes not in", values, "repayInterestYes");
            return (Criteria) this;
        }

        public Criteria andRepayInterestYesBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_interest_yes between", value1, value2, "repayInterestYes");
            return (Criteria) this;
        }

        public Criteria andRepayInterestYesNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_interest_yes not between", value1, value2, "repayInterestYes");
            return (Criteria) this;
        }

        public Criteria andInviteUserIdIsNull() {
            addCriterion("invite_user_id is null");
            return (Criteria) this;
        }

        public Criteria andInviteUserIdIsNotNull() {
            addCriterion("invite_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andInviteUserIdEqualTo(Integer value) {
            addCriterion("invite_user_id =", value, "inviteUserId");
            return (Criteria) this;
        }

        public Criteria andInviteUserIdNotEqualTo(Integer value) {
            addCriterion("invite_user_id <>", value, "inviteUserId");
            return (Criteria) this;
        }

        public Criteria andInviteUserIdGreaterThan(Integer value) {
            addCriterion("invite_user_id >", value, "inviteUserId");
            return (Criteria) this;
        }

        public Criteria andInviteUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("invite_user_id >=", value, "inviteUserId");
            return (Criteria) this;
        }

        public Criteria andInviteUserIdLessThan(Integer value) {
            addCriterion("invite_user_id <", value, "inviteUserId");
            return (Criteria) this;
        }

        public Criteria andInviteUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("invite_user_id <=", value, "inviteUserId");
            return (Criteria) this;
        }

        public Criteria andInviteUserIdIn(List<Integer> values) {
            addCriterion("invite_user_id in", values, "inviteUserId");
            return (Criteria) this;
        }

        public Criteria andInviteUserIdNotIn(List<Integer> values) {
            addCriterion("invite_user_id not in", values, "inviteUserId");
            return (Criteria) this;
        }

        public Criteria andInviteUserIdBetween(Integer value1, Integer value2) {
            addCriterion("invite_user_id between", value1, value2, "inviteUserId");
            return (Criteria) this;
        }

        public Criteria andInviteUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("invite_user_id not between", value1, value2, "inviteUserId");
            return (Criteria) this;
        }

        public Criteria andInviteUserNameIsNull() {
            addCriterion("invite_user_name is null");
            return (Criteria) this;
        }

        public Criteria andInviteUserNameIsNotNull() {
            addCriterion("invite_user_name is not null");
            return (Criteria) this;
        }

        public Criteria andInviteUserNameEqualTo(String value) {
            addCriterion("invite_user_name =", value, "inviteUserName");
            return (Criteria) this;
        }

        public Criteria andInviteUserNameNotEqualTo(String value) {
            addCriterion("invite_user_name <>", value, "inviteUserName");
            return (Criteria) this;
        }

        public Criteria andInviteUserNameGreaterThan(String value) {
            addCriterion("invite_user_name >", value, "inviteUserName");
            return (Criteria) this;
        }

        public Criteria andInviteUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("invite_user_name >=", value, "inviteUserName");
            return (Criteria) this;
        }

        public Criteria andInviteUserNameLessThan(String value) {
            addCriterion("invite_user_name <", value, "inviteUserName");
            return (Criteria) this;
        }

        public Criteria andInviteUserNameLessThanOrEqualTo(String value) {
            addCriterion("invite_user_name <=", value, "inviteUserName");
            return (Criteria) this;
        }

        public Criteria andInviteUserNameLike(String value) {
            addCriterion("invite_user_name like", value, "inviteUserName");
            return (Criteria) this;
        }

        public Criteria andInviteUserNameNotLike(String value) {
            addCriterion("invite_user_name not like", value, "inviteUserName");
            return (Criteria) this;
        }

        public Criteria andInviteUserNameIn(List<String> values) {
            addCriterion("invite_user_name in", values, "inviteUserName");
            return (Criteria) this;
        }

        public Criteria andInviteUserNameNotIn(List<String> values) {
            addCriterion("invite_user_name not in", values, "inviteUserName");
            return (Criteria) this;
        }

        public Criteria andInviteUserNameBetween(String value1, String value2) {
            addCriterion("invite_user_name between", value1, value2, "inviteUserName");
            return (Criteria) this;
        }

        public Criteria andInviteUserNameNotBetween(String value1, String value2) {
            addCriterion("invite_user_name not between", value1, value2, "inviteUserName");
            return (Criteria) this;
        }

        public Criteria andInviteUserAttributeIsNull() {
            addCriterion("invite_user_attribute is null");
            return (Criteria) this;
        }

        public Criteria andInviteUserAttributeIsNotNull() {
            addCriterion("invite_user_attribute is not null");
            return (Criteria) this;
        }

        public Criteria andInviteUserAttributeEqualTo(Integer value) {
            addCriterion("invite_user_attribute =", value, "inviteUserAttribute");
            return (Criteria) this;
        }

        public Criteria andInviteUserAttributeNotEqualTo(Integer value) {
            addCriterion("invite_user_attribute <>", value, "inviteUserAttribute");
            return (Criteria) this;
        }

        public Criteria andInviteUserAttributeGreaterThan(Integer value) {
            addCriterion("invite_user_attribute >", value, "inviteUserAttribute");
            return (Criteria) this;
        }

        public Criteria andInviteUserAttributeGreaterThanOrEqualTo(Integer value) {
            addCriterion("invite_user_attribute >=", value, "inviteUserAttribute");
            return (Criteria) this;
        }

        public Criteria andInviteUserAttributeLessThan(Integer value) {
            addCriterion("invite_user_attribute <", value, "inviteUserAttribute");
            return (Criteria) this;
        }

        public Criteria andInviteUserAttributeLessThanOrEqualTo(Integer value) {
            addCriterion("invite_user_attribute <=", value, "inviteUserAttribute");
            return (Criteria) this;
        }

        public Criteria andInviteUserAttributeIn(List<Integer> values) {
            addCriterion("invite_user_attribute in", values, "inviteUserAttribute");
            return (Criteria) this;
        }

        public Criteria andInviteUserAttributeNotIn(List<Integer> values) {
            addCriterion("invite_user_attribute not in", values, "inviteUserAttribute");
            return (Criteria) this;
        }

        public Criteria andInviteUserAttributeBetween(Integer value1, Integer value2) {
            addCriterion("invite_user_attribute between", value1, value2, "inviteUserAttribute");
            return (Criteria) this;
        }

        public Criteria andInviteUserAttributeNotBetween(Integer value1, Integer value2) {
            addCriterion("invite_user_attribute not between", value1, value2, "inviteUserAttribute");
            return (Criteria) this;
        }

        public Criteria andInviteRegionIdIsNull() {
            addCriterion("invite_region_id is null");
            return (Criteria) this;
        }

        public Criteria andInviteRegionIdIsNotNull() {
            addCriterion("invite_region_id is not null");
            return (Criteria) this;
        }

        public Criteria andInviteRegionIdEqualTo(Integer value) {
            addCriterion("invite_region_id =", value, "inviteRegionId");
            return (Criteria) this;
        }

        public Criteria andInviteRegionIdNotEqualTo(Integer value) {
            addCriterion("invite_region_id <>", value, "inviteRegionId");
            return (Criteria) this;
        }

        public Criteria andInviteRegionIdGreaterThan(Integer value) {
            addCriterion("invite_region_id >", value, "inviteRegionId");
            return (Criteria) this;
        }

        public Criteria andInviteRegionIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("invite_region_id >=", value, "inviteRegionId");
            return (Criteria) this;
        }

        public Criteria andInviteRegionIdLessThan(Integer value) {
            addCriterion("invite_region_id <", value, "inviteRegionId");
            return (Criteria) this;
        }

        public Criteria andInviteRegionIdLessThanOrEqualTo(Integer value) {
            addCriterion("invite_region_id <=", value, "inviteRegionId");
            return (Criteria) this;
        }

        public Criteria andInviteRegionIdIn(List<Integer> values) {
            addCriterion("invite_region_id in", values, "inviteRegionId");
            return (Criteria) this;
        }

        public Criteria andInviteRegionIdNotIn(List<Integer> values) {
            addCriterion("invite_region_id not in", values, "inviteRegionId");
            return (Criteria) this;
        }

        public Criteria andInviteRegionIdBetween(Integer value1, Integer value2) {
            addCriterion("invite_region_id between", value1, value2, "inviteRegionId");
            return (Criteria) this;
        }

        public Criteria andInviteRegionIdNotBetween(Integer value1, Integer value2) {
            addCriterion("invite_region_id not between", value1, value2, "inviteRegionId");
            return (Criteria) this;
        }

        public Criteria andInviteRegionNameIsNull() {
            addCriterion("invite_region_name is null");
            return (Criteria) this;
        }

        public Criteria andInviteRegionNameIsNotNull() {
            addCriterion("invite_region_name is not null");
            return (Criteria) this;
        }

        public Criteria andInviteRegionNameEqualTo(String value) {
            addCriterion("invite_region_name =", value, "inviteRegionName");
            return (Criteria) this;
        }

        public Criteria andInviteRegionNameNotEqualTo(String value) {
            addCriterion("invite_region_name <>", value, "inviteRegionName");
            return (Criteria) this;
        }

        public Criteria andInviteRegionNameGreaterThan(String value) {
            addCriterion("invite_region_name >", value, "inviteRegionName");
            return (Criteria) this;
        }

        public Criteria andInviteRegionNameGreaterThanOrEqualTo(String value) {
            addCriterion("invite_region_name >=", value, "inviteRegionName");
            return (Criteria) this;
        }

        public Criteria andInviteRegionNameLessThan(String value) {
            addCriterion("invite_region_name <", value, "inviteRegionName");
            return (Criteria) this;
        }

        public Criteria andInviteRegionNameLessThanOrEqualTo(String value) {
            addCriterion("invite_region_name <=", value, "inviteRegionName");
            return (Criteria) this;
        }

        public Criteria andInviteRegionNameLike(String value) {
            addCriterion("invite_region_name like", value, "inviteRegionName");
            return (Criteria) this;
        }

        public Criteria andInviteRegionNameNotLike(String value) {
            addCriterion("invite_region_name not like", value, "inviteRegionName");
            return (Criteria) this;
        }

        public Criteria andInviteRegionNameIn(List<String> values) {
            addCriterion("invite_region_name in", values, "inviteRegionName");
            return (Criteria) this;
        }

        public Criteria andInviteRegionNameNotIn(List<String> values) {
            addCriterion("invite_region_name not in", values, "inviteRegionName");
            return (Criteria) this;
        }

        public Criteria andInviteRegionNameBetween(String value1, String value2) {
            addCriterion("invite_region_name between", value1, value2, "inviteRegionName");
            return (Criteria) this;
        }

        public Criteria andInviteRegionNameNotBetween(String value1, String value2) {
            addCriterion("invite_region_name not between", value1, value2, "inviteRegionName");
            return (Criteria) this;
        }

        public Criteria andInviteBranchIdIsNull() {
            addCriterion("invite_branch_id is null");
            return (Criteria) this;
        }

        public Criteria andInviteBranchIdIsNotNull() {
            addCriterion("invite_branch_id is not null");
            return (Criteria) this;
        }

        public Criteria andInviteBranchIdEqualTo(Integer value) {
            addCriterion("invite_branch_id =", value, "inviteBranchId");
            return (Criteria) this;
        }

        public Criteria andInviteBranchIdNotEqualTo(Integer value) {
            addCriterion("invite_branch_id <>", value, "inviteBranchId");
            return (Criteria) this;
        }

        public Criteria andInviteBranchIdGreaterThan(Integer value) {
            addCriterion("invite_branch_id >", value, "inviteBranchId");
            return (Criteria) this;
        }

        public Criteria andInviteBranchIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("invite_branch_id >=", value, "inviteBranchId");
            return (Criteria) this;
        }

        public Criteria andInviteBranchIdLessThan(Integer value) {
            addCriterion("invite_branch_id <", value, "inviteBranchId");
            return (Criteria) this;
        }

        public Criteria andInviteBranchIdLessThanOrEqualTo(Integer value) {
            addCriterion("invite_branch_id <=", value, "inviteBranchId");
            return (Criteria) this;
        }

        public Criteria andInviteBranchIdIn(List<Integer> values) {
            addCriterion("invite_branch_id in", values, "inviteBranchId");
            return (Criteria) this;
        }

        public Criteria andInviteBranchIdNotIn(List<Integer> values) {
            addCriterion("invite_branch_id not in", values, "inviteBranchId");
            return (Criteria) this;
        }

        public Criteria andInviteBranchIdBetween(Integer value1, Integer value2) {
            addCriterion("invite_branch_id between", value1, value2, "inviteBranchId");
            return (Criteria) this;
        }

        public Criteria andInviteBranchIdNotBetween(Integer value1, Integer value2) {
            addCriterion("invite_branch_id not between", value1, value2, "inviteBranchId");
            return (Criteria) this;
        }

        public Criteria andInviteBranchNameIsNull() {
            addCriterion("invite_branch_name is null");
            return (Criteria) this;
        }

        public Criteria andInviteBranchNameIsNotNull() {
            addCriterion("invite_branch_name is not null");
            return (Criteria) this;
        }

        public Criteria andInviteBranchNameEqualTo(String value) {
            addCriterion("invite_branch_name =", value, "inviteBranchName");
            return (Criteria) this;
        }

        public Criteria andInviteBranchNameNotEqualTo(String value) {
            addCriterion("invite_branch_name <>", value, "inviteBranchName");
            return (Criteria) this;
        }

        public Criteria andInviteBranchNameGreaterThan(String value) {
            addCriterion("invite_branch_name >", value, "inviteBranchName");
            return (Criteria) this;
        }

        public Criteria andInviteBranchNameGreaterThanOrEqualTo(String value) {
            addCriterion("invite_branch_name >=", value, "inviteBranchName");
            return (Criteria) this;
        }

        public Criteria andInviteBranchNameLessThan(String value) {
            addCriterion("invite_branch_name <", value, "inviteBranchName");
            return (Criteria) this;
        }

        public Criteria andInviteBranchNameLessThanOrEqualTo(String value) {
            addCriterion("invite_branch_name <=", value, "inviteBranchName");
            return (Criteria) this;
        }

        public Criteria andInviteBranchNameLike(String value) {
            addCriterion("invite_branch_name like", value, "inviteBranchName");
            return (Criteria) this;
        }

        public Criteria andInviteBranchNameNotLike(String value) {
            addCriterion("invite_branch_name not like", value, "inviteBranchName");
            return (Criteria) this;
        }

        public Criteria andInviteBranchNameIn(List<String> values) {
            addCriterion("invite_branch_name in", values, "inviteBranchName");
            return (Criteria) this;
        }

        public Criteria andInviteBranchNameNotIn(List<String> values) {
            addCriterion("invite_branch_name not in", values, "inviteBranchName");
            return (Criteria) this;
        }

        public Criteria andInviteBranchNameBetween(String value1, String value2) {
            addCriterion("invite_branch_name between", value1, value2, "inviteBranchName");
            return (Criteria) this;
        }

        public Criteria andInviteBranchNameNotBetween(String value1, String value2) {
            addCriterion("invite_branch_name not between", value1, value2, "inviteBranchName");
            return (Criteria) this;
        }

        public Criteria andInviteDepartmentIdIsNull() {
            addCriterion("invite_department_id is null");
            return (Criteria) this;
        }

        public Criteria andInviteDepartmentIdIsNotNull() {
            addCriterion("invite_department_id is not null");
            return (Criteria) this;
        }

        public Criteria andInviteDepartmentIdEqualTo(Integer value) {
            addCriterion("invite_department_id =", value, "inviteDepartmentId");
            return (Criteria) this;
        }

        public Criteria andInviteDepartmentIdNotEqualTo(Integer value) {
            addCriterion("invite_department_id <>", value, "inviteDepartmentId");
            return (Criteria) this;
        }

        public Criteria andInviteDepartmentIdGreaterThan(Integer value) {
            addCriterion("invite_department_id >", value, "inviteDepartmentId");
            return (Criteria) this;
        }

        public Criteria andInviteDepartmentIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("invite_department_id >=", value, "inviteDepartmentId");
            return (Criteria) this;
        }

        public Criteria andInviteDepartmentIdLessThan(Integer value) {
            addCriterion("invite_department_id <", value, "inviteDepartmentId");
            return (Criteria) this;
        }

        public Criteria andInviteDepartmentIdLessThanOrEqualTo(Integer value) {
            addCriterion("invite_department_id <=", value, "inviteDepartmentId");
            return (Criteria) this;
        }

        public Criteria andInviteDepartmentIdIn(List<Integer> values) {
            addCriterion("invite_department_id in", values, "inviteDepartmentId");
            return (Criteria) this;
        }

        public Criteria andInviteDepartmentIdNotIn(List<Integer> values) {
            addCriterion("invite_department_id not in", values, "inviteDepartmentId");
            return (Criteria) this;
        }

        public Criteria andInviteDepartmentIdBetween(Integer value1, Integer value2) {
            addCriterion("invite_department_id between", value1, value2, "inviteDepartmentId");
            return (Criteria) this;
        }

        public Criteria andInviteDepartmentIdNotBetween(Integer value1, Integer value2) {
            addCriterion("invite_department_id not between", value1, value2, "inviteDepartmentId");
            return (Criteria) this;
        }

        public Criteria andInviteDepartmentNameIsNull() {
            addCriterion("invite_department_name is null");
            return (Criteria) this;
        }

        public Criteria andInviteDepartmentNameIsNotNull() {
            addCriterion("invite_department_name is not null");
            return (Criteria) this;
        }

        public Criteria andInviteDepartmentNameEqualTo(String value) {
            addCriterion("invite_department_name =", value, "inviteDepartmentName");
            return (Criteria) this;
        }

        public Criteria andInviteDepartmentNameNotEqualTo(String value) {
            addCriterion("invite_department_name <>", value, "inviteDepartmentName");
            return (Criteria) this;
        }

        public Criteria andInviteDepartmentNameGreaterThan(String value) {
            addCriterion("invite_department_name >", value, "inviteDepartmentName");
            return (Criteria) this;
        }

        public Criteria andInviteDepartmentNameGreaterThanOrEqualTo(String value) {
            addCriterion("invite_department_name >=", value, "inviteDepartmentName");
            return (Criteria) this;
        }

        public Criteria andInviteDepartmentNameLessThan(String value) {
            addCriterion("invite_department_name <", value, "inviteDepartmentName");
            return (Criteria) this;
        }

        public Criteria andInviteDepartmentNameLessThanOrEqualTo(String value) {
            addCriterion("invite_department_name <=", value, "inviteDepartmentName");
            return (Criteria) this;
        }

        public Criteria andInviteDepartmentNameLike(String value) {
            addCriterion("invite_department_name like", value, "inviteDepartmentName");
            return (Criteria) this;
        }

        public Criteria andInviteDepartmentNameNotLike(String value) {
            addCriterion("invite_department_name not like", value, "inviteDepartmentName");
            return (Criteria) this;
        }

        public Criteria andInviteDepartmentNameIn(List<String> values) {
            addCriterion("invite_department_name in", values, "inviteDepartmentName");
            return (Criteria) this;
        }

        public Criteria andInviteDepartmentNameNotIn(List<String> values) {
            addCriterion("invite_department_name not in", values, "inviteDepartmentName");
            return (Criteria) this;
        }

        public Criteria andInviteDepartmentNameBetween(String value1, String value2) {
            addCriterion("invite_department_name between", value1, value2, "inviteDepartmentName");
            return (Criteria) this;
        }

        public Criteria andInviteDepartmentNameNotBetween(String value1, String value2) {
            addCriterion("invite_department_name not between", value1, value2, "inviteDepartmentName");
            return (Criteria) this;
        }

        public Criteria andSendStatusIsNull() {
            addCriterion("send_status is null");
            return (Criteria) this;
        }

        public Criteria andSendStatusIsNotNull() {
            addCriterion("send_status is not null");
            return (Criteria) this;
        }

        public Criteria andSendStatusEqualTo(Integer value) {
            addCriterion("send_status =", value, "sendStatus");
            return (Criteria) this;
        }

        public Criteria andSendStatusNotEqualTo(Integer value) {
            addCriterion("send_status <>", value, "sendStatus");
            return (Criteria) this;
        }

        public Criteria andSendStatusGreaterThan(Integer value) {
            addCriterion("send_status >", value, "sendStatus");
            return (Criteria) this;
        }

        public Criteria andSendStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("send_status >=", value, "sendStatus");
            return (Criteria) this;
        }

        public Criteria andSendStatusLessThan(Integer value) {
            addCriterion("send_status <", value, "sendStatus");
            return (Criteria) this;
        }

        public Criteria andSendStatusLessThanOrEqualTo(Integer value) {
            addCriterion("send_status <=", value, "sendStatus");
            return (Criteria) this;
        }

        public Criteria andSendStatusIn(List<Integer> values) {
            addCriterion("send_status in", values, "sendStatus");
            return (Criteria) this;
        }

        public Criteria andSendStatusNotIn(List<Integer> values) {
            addCriterion("send_status not in", values, "sendStatus");
            return (Criteria) this;
        }

        public Criteria andSendStatusBetween(Integer value1, Integer value2) {
            addCriterion("send_status between", value1, value2, "sendStatus");
            return (Criteria) this;
        }

        public Criteria andSendStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("send_status not between", value1, value2, "sendStatus");
            return (Criteria) this;
        }

        public Criteria andCalculationStatusIsNull() {
            addCriterion("calculation_status is null");
            return (Criteria) this;
        }

        public Criteria andCalculationStatusIsNotNull() {
            addCriterion("calculation_status is not null");
            return (Criteria) this;
        }

        public Criteria andCalculationStatusEqualTo(Integer value) {
            addCriterion("calculation_status =", value, "calculationStatus");
            return (Criteria) this;
        }

        public Criteria andCalculationStatusNotEqualTo(Integer value) {
            addCriterion("calculation_status <>", value, "calculationStatus");
            return (Criteria) this;
        }

        public Criteria andCalculationStatusGreaterThan(Integer value) {
            addCriterion("calculation_status >", value, "calculationStatus");
            return (Criteria) this;
        }

        public Criteria andCalculationStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("calculation_status >=", value, "calculationStatus");
            return (Criteria) this;
        }

        public Criteria andCalculationStatusLessThan(Integer value) {
            addCriterion("calculation_status <", value, "calculationStatus");
            return (Criteria) this;
        }

        public Criteria andCalculationStatusLessThanOrEqualTo(Integer value) {
            addCriterion("calculation_status <=", value, "calculationStatus");
            return (Criteria) this;
        }

        public Criteria andCalculationStatusIn(List<Integer> values) {
            addCriterion("calculation_status in", values, "calculationStatus");
            return (Criteria) this;
        }

        public Criteria andCalculationStatusNotIn(List<Integer> values) {
            addCriterion("calculation_status not in", values, "calculationStatus");
            return (Criteria) this;
        }

        public Criteria andCalculationStatusBetween(Integer value1, Integer value2) {
            addCriterion("calculation_status between", value1, value2, "calculationStatus");
            return (Criteria) this;
        }

        public Criteria andCalculationStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("calculation_status not between", value1, value2, "calculationStatus");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("`status` is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("`status` is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("`status` =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("`status` <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("`status` >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("`status` >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("`status` <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("`status` <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("`status` in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("`status` not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("`status` between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("`status` not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andReinvestStatusIsNull() {
            addCriterion("reinvest_status is null");
            return (Criteria) this;
        }

        public Criteria andReinvestStatusIsNotNull() {
            addCriterion("reinvest_status is not null");
            return (Criteria) this;
        }

        public Criteria andReinvestStatusEqualTo(Integer value) {
            addCriterion("reinvest_status =", value, "reinvestStatus");
            return (Criteria) this;
        }

        public Criteria andReinvestStatusNotEqualTo(Integer value) {
            addCriterion("reinvest_status <>", value, "reinvestStatus");
            return (Criteria) this;
        }

        public Criteria andReinvestStatusGreaterThan(Integer value) {
            addCriterion("reinvest_status >", value, "reinvestStatus");
            return (Criteria) this;
        }

        public Criteria andReinvestStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("reinvest_status >=", value, "reinvestStatus");
            return (Criteria) this;
        }

        public Criteria andReinvestStatusLessThan(Integer value) {
            addCriterion("reinvest_status <", value, "reinvestStatus");
            return (Criteria) this;
        }

        public Criteria andReinvestStatusLessThanOrEqualTo(Integer value) {
            addCriterion("reinvest_status <=", value, "reinvestStatus");
            return (Criteria) this;
        }

        public Criteria andReinvestStatusIn(List<Integer> values) {
            addCriterion("reinvest_status in", values, "reinvestStatus");
            return (Criteria) this;
        }

        public Criteria andReinvestStatusNotIn(List<Integer> values) {
            addCriterion("reinvest_status not in", values, "reinvestStatus");
            return (Criteria) this;
        }

        public Criteria andReinvestStatusBetween(Integer value1, Integer value2) {
            addCriterion("reinvest_status between", value1, value2, "reinvestStatus");
            return (Criteria) this;
        }

        public Criteria andReinvestStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("reinvest_status not between", value1, value2, "reinvestStatus");
            return (Criteria) this;
        }

        public Criteria andRepayRunningStatusIsNull() {
            addCriterion("repay_running_status is null");
            return (Criteria) this;
        }

        public Criteria andRepayRunningStatusIsNotNull() {
            addCriterion("repay_running_status is not null");
            return (Criteria) this;
        }

        public Criteria andRepayRunningStatusEqualTo(Integer value) {
            addCriterion("repay_running_status =", value, "repayRunningStatus");
            return (Criteria) this;
        }

        public Criteria andRepayRunningStatusNotEqualTo(Integer value) {
            addCriterion("repay_running_status <>", value, "repayRunningStatus");
            return (Criteria) this;
        }

        public Criteria andRepayRunningStatusGreaterThan(Integer value) {
            addCriterion("repay_running_status >", value, "repayRunningStatus");
            return (Criteria) this;
        }

        public Criteria andRepayRunningStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("repay_running_status >=", value, "repayRunningStatus");
            return (Criteria) this;
        }

        public Criteria andRepayRunningStatusLessThan(Integer value) {
            addCriterion("repay_running_status <", value, "repayRunningStatus");
            return (Criteria) this;
        }

        public Criteria andRepayRunningStatusLessThanOrEqualTo(Integer value) {
            addCriterion("repay_running_status <=", value, "repayRunningStatus");
            return (Criteria) this;
        }

        public Criteria andRepayRunningStatusIn(List<Integer> values) {
            addCriterion("repay_running_status in", values, "repayRunningStatus");
            return (Criteria) this;
        }

        public Criteria andRepayRunningStatusNotIn(List<Integer> values) {
            addCriterion("repay_running_status not in", values, "repayRunningStatus");
            return (Criteria) this;
        }

        public Criteria andRepayRunningStatusBetween(Integer value1, Integer value2) {
            addCriterion("repay_running_status between", value1, value2, "repayRunningStatus");
            return (Criteria) this;
        }

        public Criteria andRepayRunningStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("repay_running_status not between", value1, value2, "repayRunningStatus");
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

        public Criteria andClientIsNull() {
            addCriterion("client is null");
            return (Criteria) this;
        }

        public Criteria andClientIsNotNull() {
            addCriterion("client is not null");
            return (Criteria) this;
        }

        public Criteria andClientEqualTo(Integer value) {
            addCriterion("client =", value, "client");
            return (Criteria) this;
        }

        public Criteria andClientNotEqualTo(Integer value) {
            addCriterion("client <>", value, "client");
            return (Criteria) this;
        }

        public Criteria andClientGreaterThan(Integer value) {
            addCriterion("client >", value, "client");
            return (Criteria) this;
        }

        public Criteria andClientGreaterThanOrEqualTo(Integer value) {
            addCriterion("client >=", value, "client");
            return (Criteria) this;
        }

        public Criteria andClientLessThan(Integer value) {
            addCriterion("client <", value, "client");
            return (Criteria) this;
        }

        public Criteria andClientLessThanOrEqualTo(Integer value) {
            addCriterion("client <=", value, "client");
            return (Criteria) this;
        }

        public Criteria andClientIn(List<Integer> values) {
            addCriterion("client in", values, "client");
            return (Criteria) this;
        }

        public Criteria andClientNotIn(List<Integer> values) {
            addCriterion("client not in", values, "client");
            return (Criteria) this;
        }

        public Criteria andClientBetween(Integer value1, Integer value2) {
            addCriterion("client between", value1, value2, "client");
            return (Criteria) this;
        }

        public Criteria andClientNotBetween(Integer value1, Integer value2) {
            addCriterion("client not between", value1, value2, "client");
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

        public Criteria andCreateUserNameIsNull() {
            addCriterion("create_user_name is null");
            return (Criteria) this;
        }

        public Criteria andCreateUserNameIsNotNull() {
            addCriterion("create_user_name is not null");
            return (Criteria) this;
        }

        public Criteria andCreateUserNameEqualTo(String value) {
            addCriterion("create_user_name =", value, "createUserName");
            return (Criteria) this;
        }

        public Criteria andCreateUserNameNotEqualTo(String value) {
            addCriterion("create_user_name <>", value, "createUserName");
            return (Criteria) this;
        }

        public Criteria andCreateUserNameGreaterThan(String value) {
            addCriterion("create_user_name >", value, "createUserName");
            return (Criteria) this;
        }

        public Criteria andCreateUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("create_user_name >=", value, "createUserName");
            return (Criteria) this;
        }

        public Criteria andCreateUserNameLessThan(String value) {
            addCriterion("create_user_name <", value, "createUserName");
            return (Criteria) this;
        }

        public Criteria andCreateUserNameLessThanOrEqualTo(String value) {
            addCriterion("create_user_name <=", value, "createUserName");
            return (Criteria) this;
        }

        public Criteria andCreateUserNameLike(String value) {
            addCriterion("create_user_name like", value, "createUserName");
            return (Criteria) this;
        }

        public Criteria andCreateUserNameNotLike(String value) {
            addCriterion("create_user_name not like", value, "createUserName");
            return (Criteria) this;
        }

        public Criteria andCreateUserNameIn(List<String> values) {
            addCriterion("create_user_name in", values, "createUserName");
            return (Criteria) this;
        }

        public Criteria andCreateUserNameNotIn(List<String> values) {
            addCriterion("create_user_name not in", values, "createUserName");
            return (Criteria) this;
        }

        public Criteria andCreateUserNameBetween(String value1, String value2) {
            addCriterion("create_user_name between", value1, value2, "createUserName");
            return (Criteria) this;
        }

        public Criteria andCreateUserNameNotBetween(String value1, String value2) {
            addCriterion("create_user_name not between", value1, value2, "createUserName");
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

        public Criteria andUpdateUserNameIsNull() {
            addCriterion("update_user_name is null");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNameIsNotNull() {
            addCriterion("update_user_name is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNameEqualTo(String value) {
            addCriterion("update_user_name =", value, "updateUserName");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNameNotEqualTo(String value) {
            addCriterion("update_user_name <>", value, "updateUserName");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNameGreaterThan(String value) {
            addCriterion("update_user_name >", value, "updateUserName");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("update_user_name >=", value, "updateUserName");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNameLessThan(String value) {
            addCriterion("update_user_name <", value, "updateUserName");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNameLessThanOrEqualTo(String value) {
            addCriterion("update_user_name <=", value, "updateUserName");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNameLike(String value) {
            addCriterion("update_user_name like", value, "updateUserName");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNameNotLike(String value) {
            addCriterion("update_user_name not like", value, "updateUserName");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNameIn(List<String> values) {
            addCriterion("update_user_name in", values, "updateUserName");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNameNotIn(List<String> values) {
            addCriterion("update_user_name not in", values, "updateUserName");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNameBetween(String value1, String value2) {
            addCriterion("update_user_name between", value1, value2, "updateUserName");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNameNotBetween(String value1, String value2) {
            addCriterion("update_user_name not between", value1, value2, "updateUserName");
            return (Criteria) this;
        }

        public Criteria andInviteRepayUserIdIsNull() {
            addCriterion("invite_repay_user_id is null");
            return (Criteria) this;
        }

        public Criteria andInviteRepayUserIdIsNotNull() {
            addCriterion("invite_repay_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andInviteRepayUserIdEqualTo(Integer value) {
            addCriterion("invite_repay_user_id =", value, "inviteRepayUserId");
            return (Criteria) this;
        }

        public Criteria andInviteRepayUserIdNotEqualTo(Integer value) {
            addCriterion("invite_repay_user_id <>", value, "inviteRepayUserId");
            return (Criteria) this;
        }

        public Criteria andInviteRepayUserIdGreaterThan(Integer value) {
            addCriterion("invite_repay_user_id >", value, "inviteRepayUserId");
            return (Criteria) this;
        }

        public Criteria andInviteRepayUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("invite_repay_user_id >=", value, "inviteRepayUserId");
            return (Criteria) this;
        }

        public Criteria andInviteRepayUserIdLessThan(Integer value) {
            addCriterion("invite_repay_user_id <", value, "inviteRepayUserId");
            return (Criteria) this;
        }

        public Criteria andInviteRepayUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("invite_repay_user_id <=", value, "inviteRepayUserId");
            return (Criteria) this;
        }

        public Criteria andInviteRepayUserIdIn(List<Integer> values) {
            addCriterion("invite_repay_user_id in", values, "inviteRepayUserId");
            return (Criteria) this;
        }

        public Criteria andInviteRepayUserIdNotIn(List<Integer> values) {
            addCriterion("invite_repay_user_id not in", values, "inviteRepayUserId");
            return (Criteria) this;
        }

        public Criteria andInviteRepayUserIdBetween(Integer value1, Integer value2) {
            addCriterion("invite_repay_user_id between", value1, value2, "inviteRepayUserId");
            return (Criteria) this;
        }

        public Criteria andInviteRepayUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("invite_repay_user_id not between", value1, value2, "inviteRepayUserId");
            return (Criteria) this;
        }

        public Criteria andInviteRepayUserAttributeIsNull() {
            addCriterion("invite_repay_user_attribute is null");
            return (Criteria) this;
        }

        public Criteria andInviteRepayUserAttributeIsNotNull() {
            addCriterion("invite_repay_user_attribute is not null");
            return (Criteria) this;
        }

        public Criteria andInviteRepayUserAttributeEqualTo(Integer value) {
            addCriterion("invite_repay_user_attribute =", value, "inviteRepayUserAttribute");
            return (Criteria) this;
        }

        public Criteria andInviteRepayUserAttributeNotEqualTo(Integer value) {
            addCriterion("invite_repay_user_attribute <>", value, "inviteRepayUserAttribute");
            return (Criteria) this;
        }

        public Criteria andInviteRepayUserAttributeGreaterThan(Integer value) {
            addCriterion("invite_repay_user_attribute >", value, "inviteRepayUserAttribute");
            return (Criteria) this;
        }

        public Criteria andInviteRepayUserAttributeGreaterThanOrEqualTo(Integer value) {
            addCriterion("invite_repay_user_attribute >=", value, "inviteRepayUserAttribute");
            return (Criteria) this;
        }

        public Criteria andInviteRepayUserAttributeLessThan(Integer value) {
            addCriterion("invite_repay_user_attribute <", value, "inviteRepayUserAttribute");
            return (Criteria) this;
        }

        public Criteria andInviteRepayUserAttributeLessThanOrEqualTo(Integer value) {
            addCriterion("invite_repay_user_attribute <=", value, "inviteRepayUserAttribute");
            return (Criteria) this;
        }

        public Criteria andInviteRepayUserAttributeIn(List<Integer> values) {
            addCriterion("invite_repay_user_attribute in", values, "inviteRepayUserAttribute");
            return (Criteria) this;
        }

        public Criteria andInviteRepayUserAttributeNotIn(List<Integer> values) {
            addCriterion("invite_repay_user_attribute not in", values, "inviteRepayUserAttribute");
            return (Criteria) this;
        }

        public Criteria andInviteRepayUserAttributeBetween(Integer value1, Integer value2) {
            addCriterion("invite_repay_user_attribute between", value1, value2, "inviteRepayUserAttribute");
            return (Criteria) this;
        }

        public Criteria andInviteRepayUserAttributeNotBetween(Integer value1, Integer value2) {
            addCriterion("invite_repay_user_attribute not between", value1, value2, "inviteRepayUserAttribute");
            return (Criteria) this;
        }

        public Criteria andInviteRepayUserNameIsNull() {
            addCriterion("invite_repay_user_name is null");
            return (Criteria) this;
        }

        public Criteria andInviteRepayUserNameIsNotNull() {
            addCriterion("invite_repay_user_name is not null");
            return (Criteria) this;
        }

        public Criteria andInviteRepayUserNameEqualTo(String value) {
            addCriterion("invite_repay_user_name =", value, "inviteRepayUserName");
            return (Criteria) this;
        }

        public Criteria andInviteRepayUserNameNotEqualTo(String value) {
            addCriterion("invite_repay_user_name <>", value, "inviteRepayUserName");
            return (Criteria) this;
        }

        public Criteria andInviteRepayUserNameGreaterThan(String value) {
            addCriterion("invite_repay_user_name >", value, "inviteRepayUserName");
            return (Criteria) this;
        }

        public Criteria andInviteRepayUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("invite_repay_user_name >=", value, "inviteRepayUserName");
            return (Criteria) this;
        }

        public Criteria andInviteRepayUserNameLessThan(String value) {
            addCriterion("invite_repay_user_name <", value, "inviteRepayUserName");
            return (Criteria) this;
        }

        public Criteria andInviteRepayUserNameLessThanOrEqualTo(String value) {
            addCriterion("invite_repay_user_name <=", value, "inviteRepayUserName");
            return (Criteria) this;
        }

        public Criteria andInviteRepayUserNameLike(String value) {
            addCriterion("invite_repay_user_name like", value, "inviteRepayUserName");
            return (Criteria) this;
        }

        public Criteria andInviteRepayUserNameNotLike(String value) {
            addCriterion("invite_repay_user_name not like", value, "inviteRepayUserName");
            return (Criteria) this;
        }

        public Criteria andInviteRepayUserNameIn(List<String> values) {
            addCriterion("invite_repay_user_name in", values, "inviteRepayUserName");
            return (Criteria) this;
        }

        public Criteria andInviteRepayUserNameNotIn(List<String> values) {
            addCriterion("invite_repay_user_name not in", values, "inviteRepayUserName");
            return (Criteria) this;
        }

        public Criteria andInviteRepayUserNameBetween(String value1, String value2) {
            addCriterion("invite_repay_user_name between", value1, value2, "inviteRepayUserName");
            return (Criteria) this;
        }

        public Criteria andInviteRepayUserNameNotBetween(String value1, String value2) {
            addCriterion("invite_repay_user_name not between", value1, value2, "inviteRepayUserName");
            return (Criteria) this;
        }

        public Criteria andInviteRepayRegionIdIsNull() {
            addCriterion("invite_repay_region_id is null");
            return (Criteria) this;
        }

        public Criteria andInviteRepayRegionIdIsNotNull() {
            addCriterion("invite_repay_region_id is not null");
            return (Criteria) this;
        }

        public Criteria andInviteRepayRegionIdEqualTo(Integer value) {
            addCriterion("invite_repay_region_id =", value, "inviteRepayRegionId");
            return (Criteria) this;
        }

        public Criteria andInviteRepayRegionIdNotEqualTo(Integer value) {
            addCriterion("invite_repay_region_id <>", value, "inviteRepayRegionId");
            return (Criteria) this;
        }

        public Criteria andInviteRepayRegionIdGreaterThan(Integer value) {
            addCriterion("invite_repay_region_id >", value, "inviteRepayRegionId");
            return (Criteria) this;
        }

        public Criteria andInviteRepayRegionIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("invite_repay_region_id >=", value, "inviteRepayRegionId");
            return (Criteria) this;
        }

        public Criteria andInviteRepayRegionIdLessThan(Integer value) {
            addCriterion("invite_repay_region_id <", value, "inviteRepayRegionId");
            return (Criteria) this;
        }

        public Criteria andInviteRepayRegionIdLessThanOrEqualTo(Integer value) {
            addCriterion("invite_repay_region_id <=", value, "inviteRepayRegionId");
            return (Criteria) this;
        }

        public Criteria andInviteRepayRegionIdIn(List<Integer> values) {
            addCriterion("invite_repay_region_id in", values, "inviteRepayRegionId");
            return (Criteria) this;
        }

        public Criteria andInviteRepayRegionIdNotIn(List<Integer> values) {
            addCriterion("invite_repay_region_id not in", values, "inviteRepayRegionId");
            return (Criteria) this;
        }

        public Criteria andInviteRepayRegionIdBetween(Integer value1, Integer value2) {
            addCriterion("invite_repay_region_id between", value1, value2, "inviteRepayRegionId");
            return (Criteria) this;
        }

        public Criteria andInviteRepayRegionIdNotBetween(Integer value1, Integer value2) {
            addCriterion("invite_repay_region_id not between", value1, value2, "inviteRepayRegionId");
            return (Criteria) this;
        }

        public Criteria andInviteRepayRegionNameIsNull() {
            addCriterion("invite_repay_region_name is null");
            return (Criteria) this;
        }

        public Criteria andInviteRepayRegionNameIsNotNull() {
            addCriterion("invite_repay_region_name is not null");
            return (Criteria) this;
        }

        public Criteria andInviteRepayRegionNameEqualTo(String value) {
            addCriterion("invite_repay_region_name =", value, "inviteRepayRegionName");
            return (Criteria) this;
        }

        public Criteria andInviteRepayRegionNameNotEqualTo(String value) {
            addCriterion("invite_repay_region_name <>", value, "inviteRepayRegionName");
            return (Criteria) this;
        }

        public Criteria andInviteRepayRegionNameGreaterThan(String value) {
            addCriterion("invite_repay_region_name >", value, "inviteRepayRegionName");
            return (Criteria) this;
        }

        public Criteria andInviteRepayRegionNameGreaterThanOrEqualTo(String value) {
            addCriterion("invite_repay_region_name >=", value, "inviteRepayRegionName");
            return (Criteria) this;
        }

        public Criteria andInviteRepayRegionNameLessThan(String value) {
            addCriterion("invite_repay_region_name <", value, "inviteRepayRegionName");
            return (Criteria) this;
        }

        public Criteria andInviteRepayRegionNameLessThanOrEqualTo(String value) {
            addCriterion("invite_repay_region_name <=", value, "inviteRepayRegionName");
            return (Criteria) this;
        }

        public Criteria andInviteRepayRegionNameLike(String value) {
            addCriterion("invite_repay_region_name like", value, "inviteRepayRegionName");
            return (Criteria) this;
        }

        public Criteria andInviteRepayRegionNameNotLike(String value) {
            addCriterion("invite_repay_region_name not like", value, "inviteRepayRegionName");
            return (Criteria) this;
        }

        public Criteria andInviteRepayRegionNameIn(List<String> values) {
            addCriterion("invite_repay_region_name in", values, "inviteRepayRegionName");
            return (Criteria) this;
        }

        public Criteria andInviteRepayRegionNameNotIn(List<String> values) {
            addCriterion("invite_repay_region_name not in", values, "inviteRepayRegionName");
            return (Criteria) this;
        }

        public Criteria andInviteRepayRegionNameBetween(String value1, String value2) {
            addCriterion("invite_repay_region_name between", value1, value2, "inviteRepayRegionName");
            return (Criteria) this;
        }

        public Criteria andInviteRepayRegionNameNotBetween(String value1, String value2) {
            addCriterion("invite_repay_region_name not between", value1, value2, "inviteRepayRegionName");
            return (Criteria) this;
        }

        public Criteria andInviteRepayBranchIdIsNull() {
            addCriterion("invite_repay_branch_id is null");
            return (Criteria) this;
        }

        public Criteria andInviteRepayBranchIdIsNotNull() {
            addCriterion("invite_repay_branch_id is not null");
            return (Criteria) this;
        }

        public Criteria andInviteRepayBranchIdEqualTo(Integer value) {
            addCriterion("invite_repay_branch_id =", value, "inviteRepayBranchId");
            return (Criteria) this;
        }

        public Criteria andInviteRepayBranchIdNotEqualTo(Integer value) {
            addCriterion("invite_repay_branch_id <>", value, "inviteRepayBranchId");
            return (Criteria) this;
        }

        public Criteria andInviteRepayBranchIdGreaterThan(Integer value) {
            addCriterion("invite_repay_branch_id >", value, "inviteRepayBranchId");
            return (Criteria) this;
        }

        public Criteria andInviteRepayBranchIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("invite_repay_branch_id >=", value, "inviteRepayBranchId");
            return (Criteria) this;
        }

        public Criteria andInviteRepayBranchIdLessThan(Integer value) {
            addCriterion("invite_repay_branch_id <", value, "inviteRepayBranchId");
            return (Criteria) this;
        }

        public Criteria andInviteRepayBranchIdLessThanOrEqualTo(Integer value) {
            addCriterion("invite_repay_branch_id <=", value, "inviteRepayBranchId");
            return (Criteria) this;
        }

        public Criteria andInviteRepayBranchIdIn(List<Integer> values) {
            addCriterion("invite_repay_branch_id in", values, "inviteRepayBranchId");
            return (Criteria) this;
        }

        public Criteria andInviteRepayBranchIdNotIn(List<Integer> values) {
            addCriterion("invite_repay_branch_id not in", values, "inviteRepayBranchId");
            return (Criteria) this;
        }

        public Criteria andInviteRepayBranchIdBetween(Integer value1, Integer value2) {
            addCriterion("invite_repay_branch_id between", value1, value2, "inviteRepayBranchId");
            return (Criteria) this;
        }

        public Criteria andInviteRepayBranchIdNotBetween(Integer value1, Integer value2) {
            addCriterion("invite_repay_branch_id not between", value1, value2, "inviteRepayBranchId");
            return (Criteria) this;
        }

        public Criteria andInviteRepayBranchNameIsNull() {
            addCriterion("invite_repay_branch_name is null");
            return (Criteria) this;
        }

        public Criteria andInviteRepayBranchNameIsNotNull() {
            addCriterion("invite_repay_branch_name is not null");
            return (Criteria) this;
        }

        public Criteria andInviteRepayBranchNameEqualTo(String value) {
            addCriterion("invite_repay_branch_name =", value, "inviteRepayBranchName");
            return (Criteria) this;
        }

        public Criteria andInviteRepayBranchNameNotEqualTo(String value) {
            addCriterion("invite_repay_branch_name <>", value, "inviteRepayBranchName");
            return (Criteria) this;
        }

        public Criteria andInviteRepayBranchNameGreaterThan(String value) {
            addCriterion("invite_repay_branch_name >", value, "inviteRepayBranchName");
            return (Criteria) this;
        }

        public Criteria andInviteRepayBranchNameGreaterThanOrEqualTo(String value) {
            addCriterion("invite_repay_branch_name >=", value, "inviteRepayBranchName");
            return (Criteria) this;
        }

        public Criteria andInviteRepayBranchNameLessThan(String value) {
            addCriterion("invite_repay_branch_name <", value, "inviteRepayBranchName");
            return (Criteria) this;
        }

        public Criteria andInviteRepayBranchNameLessThanOrEqualTo(String value) {
            addCriterion("invite_repay_branch_name <=", value, "inviteRepayBranchName");
            return (Criteria) this;
        }

        public Criteria andInviteRepayBranchNameLike(String value) {
            addCriterion("invite_repay_branch_name like", value, "inviteRepayBranchName");
            return (Criteria) this;
        }

        public Criteria andInviteRepayBranchNameNotLike(String value) {
            addCriterion("invite_repay_branch_name not like", value, "inviteRepayBranchName");
            return (Criteria) this;
        }

        public Criteria andInviteRepayBranchNameIn(List<String> values) {
            addCriterion("invite_repay_branch_name in", values, "inviteRepayBranchName");
            return (Criteria) this;
        }

        public Criteria andInviteRepayBranchNameNotIn(List<String> values) {
            addCriterion("invite_repay_branch_name not in", values, "inviteRepayBranchName");
            return (Criteria) this;
        }

        public Criteria andInviteRepayBranchNameBetween(String value1, String value2) {
            addCriterion("invite_repay_branch_name between", value1, value2, "inviteRepayBranchName");
            return (Criteria) this;
        }

        public Criteria andInviteRepayBranchNameNotBetween(String value1, String value2) {
            addCriterion("invite_repay_branch_name not between", value1, value2, "inviteRepayBranchName");
            return (Criteria) this;
        }

        public Criteria andInviteRepayDepartmentIdIsNull() {
            addCriterion("invite_repay_department_id is null");
            return (Criteria) this;
        }

        public Criteria andInviteRepayDepartmentIdIsNotNull() {
            addCriterion("invite_repay_department_id is not null");
            return (Criteria) this;
        }

        public Criteria andInviteRepayDepartmentIdEqualTo(Integer value) {
            addCriterion("invite_repay_department_id =", value, "inviteRepayDepartmentId");
            return (Criteria) this;
        }

        public Criteria andInviteRepayDepartmentIdNotEqualTo(Integer value) {
            addCriterion("invite_repay_department_id <>", value, "inviteRepayDepartmentId");
            return (Criteria) this;
        }

        public Criteria andInviteRepayDepartmentIdGreaterThan(Integer value) {
            addCriterion("invite_repay_department_id >", value, "inviteRepayDepartmentId");
            return (Criteria) this;
        }

        public Criteria andInviteRepayDepartmentIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("invite_repay_department_id >=", value, "inviteRepayDepartmentId");
            return (Criteria) this;
        }

        public Criteria andInviteRepayDepartmentIdLessThan(Integer value) {
            addCriterion("invite_repay_department_id <", value, "inviteRepayDepartmentId");
            return (Criteria) this;
        }

        public Criteria andInviteRepayDepartmentIdLessThanOrEqualTo(Integer value) {
            addCriterion("invite_repay_department_id <=", value, "inviteRepayDepartmentId");
            return (Criteria) this;
        }

        public Criteria andInviteRepayDepartmentIdIn(List<Integer> values) {
            addCriterion("invite_repay_department_id in", values, "inviteRepayDepartmentId");
            return (Criteria) this;
        }

        public Criteria andInviteRepayDepartmentIdNotIn(List<Integer> values) {
            addCriterion("invite_repay_department_id not in", values, "inviteRepayDepartmentId");
            return (Criteria) this;
        }

        public Criteria andInviteRepayDepartmentIdBetween(Integer value1, Integer value2) {
            addCriterion("invite_repay_department_id between", value1, value2, "inviteRepayDepartmentId");
            return (Criteria) this;
        }

        public Criteria andInviteRepayDepartmentIdNotBetween(Integer value1, Integer value2) {
            addCriterion("invite_repay_department_id not between", value1, value2, "inviteRepayDepartmentId");
            return (Criteria) this;
        }

        public Criteria andInviteRepayDepartmentNameIsNull() {
            addCriterion("invite_repay_department_name is null");
            return (Criteria) this;
        }

        public Criteria andInviteRepayDepartmentNameIsNotNull() {
            addCriterion("invite_repay_department_name is not null");
            return (Criteria) this;
        }

        public Criteria andInviteRepayDepartmentNameEqualTo(String value) {
            addCriterion("invite_repay_department_name =", value, "inviteRepayDepartmentName");
            return (Criteria) this;
        }

        public Criteria andInviteRepayDepartmentNameNotEqualTo(String value) {
            addCriterion("invite_repay_department_name <>", value, "inviteRepayDepartmentName");
            return (Criteria) this;
        }

        public Criteria andInviteRepayDepartmentNameGreaterThan(String value) {
            addCriterion("invite_repay_department_name >", value, "inviteRepayDepartmentName");
            return (Criteria) this;
        }

        public Criteria andInviteRepayDepartmentNameGreaterThanOrEqualTo(String value) {
            addCriterion("invite_repay_department_name >=", value, "inviteRepayDepartmentName");
            return (Criteria) this;
        }

        public Criteria andInviteRepayDepartmentNameLessThan(String value) {
            addCriterion("invite_repay_department_name <", value, "inviteRepayDepartmentName");
            return (Criteria) this;
        }

        public Criteria andInviteRepayDepartmentNameLessThanOrEqualTo(String value) {
            addCriterion("invite_repay_department_name <=", value, "inviteRepayDepartmentName");
            return (Criteria) this;
        }

        public Criteria andInviteRepayDepartmentNameLike(String value) {
            addCriterion("invite_repay_department_name like", value, "inviteRepayDepartmentName");
            return (Criteria) this;
        }

        public Criteria andInviteRepayDepartmentNameNotLike(String value) {
            addCriterion("invite_repay_department_name not like", value, "inviteRepayDepartmentName");
            return (Criteria) this;
        }

        public Criteria andInviteRepayDepartmentNameIn(List<String> values) {
            addCriterion("invite_repay_department_name in", values, "inviteRepayDepartmentName");
            return (Criteria) this;
        }

        public Criteria andInviteRepayDepartmentNameNotIn(List<String> values) {
            addCriterion("invite_repay_department_name not in", values, "inviteRepayDepartmentName");
            return (Criteria) this;
        }

        public Criteria andInviteRepayDepartmentNameBetween(String value1, String value2) {
            addCriterion("invite_repay_department_name between", value1, value2, "inviteRepayDepartmentName");
            return (Criteria) this;
        }

        public Criteria andInviteRepayDepartmentNameNotBetween(String value1, String value2) {
            addCriterion("invite_repay_department_name not between", value1, value2, "inviteRepayDepartmentName");
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