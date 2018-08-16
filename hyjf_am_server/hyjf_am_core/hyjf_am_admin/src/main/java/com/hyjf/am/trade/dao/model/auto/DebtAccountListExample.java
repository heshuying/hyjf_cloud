package com.hyjf.am.trade.dao.model.auto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DebtAccountListExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public DebtAccountListExample() {
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

        public Criteria andNidIsNull() {
            addCriterion("nid is null");
            return (Criteria) this;
        }

        public Criteria andNidIsNotNull() {
            addCriterion("nid is not null");
            return (Criteria) this;
        }

        public Criteria andNidEqualTo(String value) {
            addCriterion("nid =", value, "nid");
            return (Criteria) this;
        }

        public Criteria andNidNotEqualTo(String value) {
            addCriterion("nid <>", value, "nid");
            return (Criteria) this;
        }

        public Criteria andNidGreaterThan(String value) {
            addCriterion("nid >", value, "nid");
            return (Criteria) this;
        }

        public Criteria andNidGreaterThanOrEqualTo(String value) {
            addCriterion("nid >=", value, "nid");
            return (Criteria) this;
        }

        public Criteria andNidLessThan(String value) {
            addCriterion("nid <", value, "nid");
            return (Criteria) this;
        }

        public Criteria andNidLessThanOrEqualTo(String value) {
            addCriterion("nid <=", value, "nid");
            return (Criteria) this;
        }

        public Criteria andNidLike(String value) {
            addCriterion("nid like", value, "nid");
            return (Criteria) this;
        }

        public Criteria andNidNotLike(String value) {
            addCriterion("nid not like", value, "nid");
            return (Criteria) this;
        }

        public Criteria andNidIn(List<String> values) {
            addCriterion("nid in", values, "nid");
            return (Criteria) this;
        }

        public Criteria andNidNotIn(List<String> values) {
            addCriterion("nid not in", values, "nid");
            return (Criteria) this;
        }

        public Criteria andNidBetween(String value1, String value2) {
            addCriterion("nid between", value1, value2, "nid");
            return (Criteria) this;
        }

        public Criteria andNidNotBetween(String value1, String value2) {
            addCriterion("nid not between", value1, value2, "nid");
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

        public Criteria andRefererUserIdIsNull() {
            addCriterion("referer_user_id is null");
            return (Criteria) this;
        }

        public Criteria andRefererUserIdIsNotNull() {
            addCriterion("referer_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andRefererUserIdEqualTo(Integer value) {
            addCriterion("referer_user_id =", value, "refererUserId");
            return (Criteria) this;
        }

        public Criteria andRefererUserIdNotEqualTo(Integer value) {
            addCriterion("referer_user_id <>", value, "refererUserId");
            return (Criteria) this;
        }

        public Criteria andRefererUserIdGreaterThan(Integer value) {
            addCriterion("referer_user_id >", value, "refererUserId");
            return (Criteria) this;
        }

        public Criteria andRefererUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("referer_user_id >=", value, "refererUserId");
            return (Criteria) this;
        }

        public Criteria andRefererUserIdLessThan(Integer value) {
            addCriterion("referer_user_id <", value, "refererUserId");
            return (Criteria) this;
        }

        public Criteria andRefererUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("referer_user_id <=", value, "refererUserId");
            return (Criteria) this;
        }

        public Criteria andRefererUserIdIn(List<Integer> values) {
            addCriterion("referer_user_id in", values, "refererUserId");
            return (Criteria) this;
        }

        public Criteria andRefererUserIdNotIn(List<Integer> values) {
            addCriterion("referer_user_id not in", values, "refererUserId");
            return (Criteria) this;
        }

        public Criteria andRefererUserIdBetween(Integer value1, Integer value2) {
            addCriterion("referer_user_id between", value1, value2, "refererUserId");
            return (Criteria) this;
        }

        public Criteria andRefererUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("referer_user_id not between", value1, value2, "refererUserId");
            return (Criteria) this;
        }

        public Criteria andRefererUserNameIsNull() {
            addCriterion("referer_user_name is null");
            return (Criteria) this;
        }

        public Criteria andRefererUserNameIsNotNull() {
            addCriterion("referer_user_name is not null");
            return (Criteria) this;
        }

        public Criteria andRefererUserNameEqualTo(String value) {
            addCriterion("referer_user_name =", value, "refererUserName");
            return (Criteria) this;
        }

        public Criteria andRefererUserNameNotEqualTo(String value) {
            addCriterion("referer_user_name <>", value, "refererUserName");
            return (Criteria) this;
        }

        public Criteria andRefererUserNameGreaterThan(String value) {
            addCriterion("referer_user_name >", value, "refererUserName");
            return (Criteria) this;
        }

        public Criteria andRefererUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("referer_user_name >=", value, "refererUserName");
            return (Criteria) this;
        }

        public Criteria andRefererUserNameLessThan(String value) {
            addCriterion("referer_user_name <", value, "refererUserName");
            return (Criteria) this;
        }

        public Criteria andRefererUserNameLessThanOrEqualTo(String value) {
            addCriterion("referer_user_name <=", value, "refererUserName");
            return (Criteria) this;
        }

        public Criteria andRefererUserNameLike(String value) {
            addCriterion("referer_user_name like", value, "refererUserName");
            return (Criteria) this;
        }

        public Criteria andRefererUserNameNotLike(String value) {
            addCriterion("referer_user_name not like", value, "refererUserName");
            return (Criteria) this;
        }

        public Criteria andRefererUserNameIn(List<String> values) {
            addCriterion("referer_user_name in", values, "refererUserName");
            return (Criteria) this;
        }

        public Criteria andRefererUserNameNotIn(List<String> values) {
            addCriterion("referer_user_name not in", values, "refererUserName");
            return (Criteria) this;
        }

        public Criteria andRefererUserNameBetween(String value1, String value2) {
            addCriterion("referer_user_name between", value1, value2, "refererUserName");
            return (Criteria) this;
        }

        public Criteria andRefererUserNameNotBetween(String value1, String value2) {
            addCriterion("referer_user_name not between", value1, value2, "refererUserName");
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

        public Criteria andPlanOrderIdIsNull() {
            addCriterion("plan_order_id is null");
            return (Criteria) this;
        }

        public Criteria andPlanOrderIdIsNotNull() {
            addCriterion("plan_order_id is not null");
            return (Criteria) this;
        }

        public Criteria andPlanOrderIdEqualTo(String value) {
            addCriterion("plan_order_id =", value, "planOrderId");
            return (Criteria) this;
        }

        public Criteria andPlanOrderIdNotEqualTo(String value) {
            addCriterion("plan_order_id <>", value, "planOrderId");
            return (Criteria) this;
        }

        public Criteria andPlanOrderIdGreaterThan(String value) {
            addCriterion("plan_order_id >", value, "planOrderId");
            return (Criteria) this;
        }

        public Criteria andPlanOrderIdGreaterThanOrEqualTo(String value) {
            addCriterion("plan_order_id >=", value, "planOrderId");
            return (Criteria) this;
        }

        public Criteria andPlanOrderIdLessThan(String value) {
            addCriterion("plan_order_id <", value, "planOrderId");
            return (Criteria) this;
        }

        public Criteria andPlanOrderIdLessThanOrEqualTo(String value) {
            addCriterion("plan_order_id <=", value, "planOrderId");
            return (Criteria) this;
        }

        public Criteria andPlanOrderIdLike(String value) {
            addCriterion("plan_order_id like", value, "planOrderId");
            return (Criteria) this;
        }

        public Criteria andPlanOrderIdNotLike(String value) {
            addCriterion("plan_order_id not like", value, "planOrderId");
            return (Criteria) this;
        }

        public Criteria andPlanOrderIdIn(List<String> values) {
            addCriterion("plan_order_id in", values, "planOrderId");
            return (Criteria) this;
        }

        public Criteria andPlanOrderIdNotIn(List<String> values) {
            addCriterion("plan_order_id not in", values, "planOrderId");
            return (Criteria) this;
        }

        public Criteria andPlanOrderIdBetween(String value1, String value2) {
            addCriterion("plan_order_id between", value1, value2, "planOrderId");
            return (Criteria) this;
        }

        public Criteria andPlanOrderIdNotBetween(String value1, String value2) {
            addCriterion("plan_order_id not between", value1, value2, "planOrderId");
            return (Criteria) this;
        }

        public Criteria andPlanOrderBalanceIsNull() {
            addCriterion("plan_order_balance is null");
            return (Criteria) this;
        }

        public Criteria andPlanOrderBalanceIsNotNull() {
            addCriterion("plan_order_balance is not null");
            return (Criteria) this;
        }

        public Criteria andPlanOrderBalanceEqualTo(BigDecimal value) {
            addCriterion("plan_order_balance =", value, "planOrderBalance");
            return (Criteria) this;
        }

        public Criteria andPlanOrderBalanceNotEqualTo(BigDecimal value) {
            addCriterion("plan_order_balance <>", value, "planOrderBalance");
            return (Criteria) this;
        }

        public Criteria andPlanOrderBalanceGreaterThan(BigDecimal value) {
            addCriterion("plan_order_balance >", value, "planOrderBalance");
            return (Criteria) this;
        }

        public Criteria andPlanOrderBalanceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("plan_order_balance >=", value, "planOrderBalance");
            return (Criteria) this;
        }

        public Criteria andPlanOrderBalanceLessThan(BigDecimal value) {
            addCriterion("plan_order_balance <", value, "planOrderBalance");
            return (Criteria) this;
        }

        public Criteria andPlanOrderBalanceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("plan_order_balance <=", value, "planOrderBalance");
            return (Criteria) this;
        }

        public Criteria andPlanOrderBalanceIn(List<BigDecimal> values) {
            addCriterion("plan_order_balance in", values, "planOrderBalance");
            return (Criteria) this;
        }

        public Criteria andPlanOrderBalanceNotIn(List<BigDecimal> values) {
            addCriterion("plan_order_balance not in", values, "planOrderBalance");
            return (Criteria) this;
        }

        public Criteria andPlanOrderBalanceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("plan_order_balance between", value1, value2, "planOrderBalance");
            return (Criteria) this;
        }

        public Criteria andPlanOrderBalanceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("plan_order_balance not between", value1, value2, "planOrderBalance");
            return (Criteria) this;
        }

        public Criteria andPlanOrderFrostIsNull() {
            addCriterion("plan_order_frost is null");
            return (Criteria) this;
        }

        public Criteria andPlanOrderFrostIsNotNull() {
            addCriterion("plan_order_frost is not null");
            return (Criteria) this;
        }

        public Criteria andPlanOrderFrostEqualTo(BigDecimal value) {
            addCriterion("plan_order_frost =", value, "planOrderFrost");
            return (Criteria) this;
        }

        public Criteria andPlanOrderFrostNotEqualTo(BigDecimal value) {
            addCriterion("plan_order_frost <>", value, "planOrderFrost");
            return (Criteria) this;
        }

        public Criteria andPlanOrderFrostGreaterThan(BigDecimal value) {
            addCriterion("plan_order_frost >", value, "planOrderFrost");
            return (Criteria) this;
        }

        public Criteria andPlanOrderFrostGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("plan_order_frost >=", value, "planOrderFrost");
            return (Criteria) this;
        }

        public Criteria andPlanOrderFrostLessThan(BigDecimal value) {
            addCriterion("plan_order_frost <", value, "planOrderFrost");
            return (Criteria) this;
        }

        public Criteria andPlanOrderFrostLessThanOrEqualTo(BigDecimal value) {
            addCriterion("plan_order_frost <=", value, "planOrderFrost");
            return (Criteria) this;
        }

        public Criteria andPlanOrderFrostIn(List<BigDecimal> values) {
            addCriterion("plan_order_frost in", values, "planOrderFrost");
            return (Criteria) this;
        }

        public Criteria andPlanOrderFrostNotIn(List<BigDecimal> values) {
            addCriterion("plan_order_frost not in", values, "planOrderFrost");
            return (Criteria) this;
        }

        public Criteria andPlanOrderFrostBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("plan_order_frost between", value1, value2, "planOrderFrost");
            return (Criteria) this;
        }

        public Criteria andPlanOrderFrostNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("plan_order_frost not between", value1, value2, "planOrderFrost");
            return (Criteria) this;
        }

        public Criteria andPlanBalanceIsNull() {
            addCriterion("plan_balance is null");
            return (Criteria) this;
        }

        public Criteria andPlanBalanceIsNotNull() {
            addCriterion("plan_balance is not null");
            return (Criteria) this;
        }

        public Criteria andPlanBalanceEqualTo(BigDecimal value) {
            addCriterion("plan_balance =", value, "planBalance");
            return (Criteria) this;
        }

        public Criteria andPlanBalanceNotEqualTo(BigDecimal value) {
            addCriterion("plan_balance <>", value, "planBalance");
            return (Criteria) this;
        }

        public Criteria andPlanBalanceGreaterThan(BigDecimal value) {
            addCriterion("plan_balance >", value, "planBalance");
            return (Criteria) this;
        }

        public Criteria andPlanBalanceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("plan_balance >=", value, "planBalance");
            return (Criteria) this;
        }

        public Criteria andPlanBalanceLessThan(BigDecimal value) {
            addCriterion("plan_balance <", value, "planBalance");
            return (Criteria) this;
        }

        public Criteria andPlanBalanceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("plan_balance <=", value, "planBalance");
            return (Criteria) this;
        }

        public Criteria andPlanBalanceIn(List<BigDecimal> values) {
            addCriterion("plan_balance in", values, "planBalance");
            return (Criteria) this;
        }

        public Criteria andPlanBalanceNotIn(List<BigDecimal> values) {
            addCriterion("plan_balance not in", values, "planBalance");
            return (Criteria) this;
        }

        public Criteria andPlanBalanceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("plan_balance between", value1, value2, "planBalance");
            return (Criteria) this;
        }

        public Criteria andPlanBalanceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("plan_balance not between", value1, value2, "planBalance");
            return (Criteria) this;
        }

        public Criteria andPlanFrostIsNull() {
            addCriterion("plan_frost is null");
            return (Criteria) this;
        }

        public Criteria andPlanFrostIsNotNull() {
            addCriterion("plan_frost is not null");
            return (Criteria) this;
        }

        public Criteria andPlanFrostEqualTo(BigDecimal value) {
            addCriterion("plan_frost =", value, "planFrost");
            return (Criteria) this;
        }

        public Criteria andPlanFrostNotEqualTo(BigDecimal value) {
            addCriterion("plan_frost <>", value, "planFrost");
            return (Criteria) this;
        }

        public Criteria andPlanFrostGreaterThan(BigDecimal value) {
            addCriterion("plan_frost >", value, "planFrost");
            return (Criteria) this;
        }

        public Criteria andPlanFrostGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("plan_frost >=", value, "planFrost");
            return (Criteria) this;
        }

        public Criteria andPlanFrostLessThan(BigDecimal value) {
            addCriterion("plan_frost <", value, "planFrost");
            return (Criteria) this;
        }

        public Criteria andPlanFrostLessThanOrEqualTo(BigDecimal value) {
            addCriterion("plan_frost <=", value, "planFrost");
            return (Criteria) this;
        }

        public Criteria andPlanFrostIn(List<BigDecimal> values) {
            addCriterion("plan_frost in", values, "planFrost");
            return (Criteria) this;
        }

        public Criteria andPlanFrostNotIn(List<BigDecimal> values) {
            addCriterion("plan_frost not in", values, "planFrost");
            return (Criteria) this;
        }

        public Criteria andPlanFrostBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("plan_frost between", value1, value2, "planFrost");
            return (Criteria) this;
        }

        public Criteria andPlanFrostNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("plan_frost not between", value1, value2, "planFrost");
            return (Criteria) this;
        }

        public Criteria andAmountIsNull() {
            addCriterion("amount is null");
            return (Criteria) this;
        }

        public Criteria andAmountIsNotNull() {
            addCriterion("amount is not null");
            return (Criteria) this;
        }

        public Criteria andAmountEqualTo(BigDecimal value) {
            addCriterion("amount =", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotEqualTo(BigDecimal value) {
            addCriterion("amount <>", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThan(BigDecimal value) {
            addCriterion("amount >", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("amount >=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThan(BigDecimal value) {
            addCriterion("amount <", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("amount <=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountIn(List<BigDecimal> values) {
            addCriterion("amount in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotIn(List<BigDecimal> values) {
            addCriterion("amount not in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("amount between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("amount not between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("`type` is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("`type` is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(Integer value) {
            addCriterion("`type` =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Integer value) {
            addCriterion("`type` <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Integer value) {
            addCriterion("`type` >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("`type` >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Integer value) {
            addCriterion("`type` <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Integer value) {
            addCriterion("`type` <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Integer> values) {
            addCriterion("`type` in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Integer> values) {
            addCriterion("`type` not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Integer value1, Integer value2) {
            addCriterion("`type` between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("`type` not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTradeIsNull() {
            addCriterion("trade is null");
            return (Criteria) this;
        }

        public Criteria andTradeIsNotNull() {
            addCriterion("trade is not null");
            return (Criteria) this;
        }

        public Criteria andTradeEqualTo(String value) {
            addCriterion("trade =", value, "trade");
            return (Criteria) this;
        }

        public Criteria andTradeNotEqualTo(String value) {
            addCriterion("trade <>", value, "trade");
            return (Criteria) this;
        }

        public Criteria andTradeGreaterThan(String value) {
            addCriterion("trade >", value, "trade");
            return (Criteria) this;
        }

        public Criteria andTradeGreaterThanOrEqualTo(String value) {
            addCriterion("trade >=", value, "trade");
            return (Criteria) this;
        }

        public Criteria andTradeLessThan(String value) {
            addCriterion("trade <", value, "trade");
            return (Criteria) this;
        }

        public Criteria andTradeLessThanOrEqualTo(String value) {
            addCriterion("trade <=", value, "trade");
            return (Criteria) this;
        }

        public Criteria andTradeLike(String value) {
            addCriterion("trade like", value, "trade");
            return (Criteria) this;
        }

        public Criteria andTradeNotLike(String value) {
            addCriterion("trade not like", value, "trade");
            return (Criteria) this;
        }

        public Criteria andTradeIn(List<String> values) {
            addCriterion("trade in", values, "trade");
            return (Criteria) this;
        }

        public Criteria andTradeNotIn(List<String> values) {
            addCriterion("trade not in", values, "trade");
            return (Criteria) this;
        }

        public Criteria andTradeBetween(String value1, String value2) {
            addCriterion("trade between", value1, value2, "trade");
            return (Criteria) this;
        }

        public Criteria andTradeNotBetween(String value1, String value2) {
            addCriterion("trade not between", value1, value2, "trade");
            return (Criteria) this;
        }

        public Criteria andTradeCodeIsNull() {
            addCriterion("trade_code is null");
            return (Criteria) this;
        }

        public Criteria andTradeCodeIsNotNull() {
            addCriterion("trade_code is not null");
            return (Criteria) this;
        }

        public Criteria andTradeCodeEqualTo(String value) {
            addCriterion("trade_code =", value, "tradeCode");
            return (Criteria) this;
        }

        public Criteria andTradeCodeNotEqualTo(String value) {
            addCriterion("trade_code <>", value, "tradeCode");
            return (Criteria) this;
        }

        public Criteria andTradeCodeGreaterThan(String value) {
            addCriterion("trade_code >", value, "tradeCode");
            return (Criteria) this;
        }

        public Criteria andTradeCodeGreaterThanOrEqualTo(String value) {
            addCriterion("trade_code >=", value, "tradeCode");
            return (Criteria) this;
        }

        public Criteria andTradeCodeLessThan(String value) {
            addCriterion("trade_code <", value, "tradeCode");
            return (Criteria) this;
        }

        public Criteria andTradeCodeLessThanOrEqualTo(String value) {
            addCriterion("trade_code <=", value, "tradeCode");
            return (Criteria) this;
        }

        public Criteria andTradeCodeLike(String value) {
            addCriterion("trade_code like", value, "tradeCode");
            return (Criteria) this;
        }

        public Criteria andTradeCodeNotLike(String value) {
            addCriterion("trade_code not like", value, "tradeCode");
            return (Criteria) this;
        }

        public Criteria andTradeCodeIn(List<String> values) {
            addCriterion("trade_code in", values, "tradeCode");
            return (Criteria) this;
        }

        public Criteria andTradeCodeNotIn(List<String> values) {
            addCriterion("trade_code not in", values, "tradeCode");
            return (Criteria) this;
        }

        public Criteria andTradeCodeBetween(String value1, String value2) {
            addCriterion("trade_code between", value1, value2, "tradeCode");
            return (Criteria) this;
        }

        public Criteria andTradeCodeNotBetween(String value1, String value2) {
            addCriterion("trade_code not between", value1, value2, "tradeCode");
            return (Criteria) this;
        }

        public Criteria andTotalIsNull() {
            addCriterion("total is null");
            return (Criteria) this;
        }

        public Criteria andTotalIsNotNull() {
            addCriterion("total is not null");
            return (Criteria) this;
        }

        public Criteria andTotalEqualTo(BigDecimal value) {
            addCriterion("total =", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalNotEqualTo(BigDecimal value) {
            addCriterion("total <>", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalGreaterThan(BigDecimal value) {
            addCriterion("total >", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("total >=", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalLessThan(BigDecimal value) {
            addCriterion("total <", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalLessThanOrEqualTo(BigDecimal value) {
            addCriterion("total <=", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalIn(List<BigDecimal> values) {
            addCriterion("total in", values, "total");
            return (Criteria) this;
        }

        public Criteria andTotalNotIn(List<BigDecimal> values) {
            addCriterion("total not in", values, "total");
            return (Criteria) this;
        }

        public Criteria andTotalBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("total between", value1, value2, "total");
            return (Criteria) this;
        }

        public Criteria andTotalNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("total not between", value1, value2, "total");
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

        public Criteria andFrostIsNull() {
            addCriterion("frost is null");
            return (Criteria) this;
        }

        public Criteria andFrostIsNotNull() {
            addCriterion("frost is not null");
            return (Criteria) this;
        }

        public Criteria andFrostEqualTo(BigDecimal value) {
            addCriterion("frost =", value, "frost");
            return (Criteria) this;
        }

        public Criteria andFrostNotEqualTo(BigDecimal value) {
            addCriterion("frost <>", value, "frost");
            return (Criteria) this;
        }

        public Criteria andFrostGreaterThan(BigDecimal value) {
            addCriterion("frost >", value, "frost");
            return (Criteria) this;
        }

        public Criteria andFrostGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("frost >=", value, "frost");
            return (Criteria) this;
        }

        public Criteria andFrostLessThan(BigDecimal value) {
            addCriterion("frost <", value, "frost");
            return (Criteria) this;
        }

        public Criteria andFrostLessThanOrEqualTo(BigDecimal value) {
            addCriterion("frost <=", value, "frost");
            return (Criteria) this;
        }

        public Criteria andFrostIn(List<BigDecimal> values) {
            addCriterion("frost in", values, "frost");
            return (Criteria) this;
        }

        public Criteria andFrostNotIn(List<BigDecimal> values) {
            addCriterion("frost not in", values, "frost");
            return (Criteria) this;
        }

        public Criteria andFrostBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("frost between", value1, value2, "frost");
            return (Criteria) this;
        }

        public Criteria andFrostNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("frost not between", value1, value2, "frost");
            return (Criteria) this;
        }

        public Criteria andAccountWaitIsNull() {
            addCriterion("account_wait is null");
            return (Criteria) this;
        }

        public Criteria andAccountWaitIsNotNull() {
            addCriterion("account_wait is not null");
            return (Criteria) this;
        }

        public Criteria andAccountWaitEqualTo(BigDecimal value) {
            addCriterion("account_wait =", value, "accountWait");
            return (Criteria) this;
        }

        public Criteria andAccountWaitNotEqualTo(BigDecimal value) {
            addCriterion("account_wait <>", value, "accountWait");
            return (Criteria) this;
        }

        public Criteria andAccountWaitGreaterThan(BigDecimal value) {
            addCriterion("account_wait >", value, "accountWait");
            return (Criteria) this;
        }

        public Criteria andAccountWaitGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("account_wait >=", value, "accountWait");
            return (Criteria) this;
        }

        public Criteria andAccountWaitLessThan(BigDecimal value) {
            addCriterion("account_wait <", value, "accountWait");
            return (Criteria) this;
        }

        public Criteria andAccountWaitLessThanOrEqualTo(BigDecimal value) {
            addCriterion("account_wait <=", value, "accountWait");
            return (Criteria) this;
        }

        public Criteria andAccountWaitIn(List<BigDecimal> values) {
            addCriterion("account_wait in", values, "accountWait");
            return (Criteria) this;
        }

        public Criteria andAccountWaitNotIn(List<BigDecimal> values) {
            addCriterion("account_wait not in", values, "accountWait");
            return (Criteria) this;
        }

        public Criteria andAccountWaitBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("account_wait between", value1, value2, "accountWait");
            return (Criteria) this;
        }

        public Criteria andAccountWaitNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("account_wait not between", value1, value2, "accountWait");
            return (Criteria) this;
        }

        public Criteria andCapitalWaitIsNull() {
            addCriterion("capital_wait is null");
            return (Criteria) this;
        }

        public Criteria andCapitalWaitIsNotNull() {
            addCriterion("capital_wait is not null");
            return (Criteria) this;
        }

        public Criteria andCapitalWaitEqualTo(BigDecimal value) {
            addCriterion("capital_wait =", value, "capitalWait");
            return (Criteria) this;
        }

        public Criteria andCapitalWaitNotEqualTo(BigDecimal value) {
            addCriterion("capital_wait <>", value, "capitalWait");
            return (Criteria) this;
        }

        public Criteria andCapitalWaitGreaterThan(BigDecimal value) {
            addCriterion("capital_wait >", value, "capitalWait");
            return (Criteria) this;
        }

        public Criteria andCapitalWaitGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("capital_wait >=", value, "capitalWait");
            return (Criteria) this;
        }

        public Criteria andCapitalWaitLessThan(BigDecimal value) {
            addCriterion("capital_wait <", value, "capitalWait");
            return (Criteria) this;
        }

        public Criteria andCapitalWaitLessThanOrEqualTo(BigDecimal value) {
            addCriterion("capital_wait <=", value, "capitalWait");
            return (Criteria) this;
        }

        public Criteria andCapitalWaitIn(List<BigDecimal> values) {
            addCriterion("capital_wait in", values, "capitalWait");
            return (Criteria) this;
        }

        public Criteria andCapitalWaitNotIn(List<BigDecimal> values) {
            addCriterion("capital_wait not in", values, "capitalWait");
            return (Criteria) this;
        }

        public Criteria andCapitalWaitBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("capital_wait between", value1, value2, "capitalWait");
            return (Criteria) this;
        }

        public Criteria andCapitalWaitNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("capital_wait not between", value1, value2, "capitalWait");
            return (Criteria) this;
        }

        public Criteria andInterestWaitIsNull() {
            addCriterion("interest_wait is null");
            return (Criteria) this;
        }

        public Criteria andInterestWaitIsNotNull() {
            addCriterion("interest_wait is not null");
            return (Criteria) this;
        }

        public Criteria andInterestWaitEqualTo(BigDecimal value) {
            addCriterion("interest_wait =", value, "interestWait");
            return (Criteria) this;
        }

        public Criteria andInterestWaitNotEqualTo(BigDecimal value) {
            addCriterion("interest_wait <>", value, "interestWait");
            return (Criteria) this;
        }

        public Criteria andInterestWaitGreaterThan(BigDecimal value) {
            addCriterion("interest_wait >", value, "interestWait");
            return (Criteria) this;
        }

        public Criteria andInterestWaitGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("interest_wait >=", value, "interestWait");
            return (Criteria) this;
        }

        public Criteria andInterestWaitLessThan(BigDecimal value) {
            addCriterion("interest_wait <", value, "interestWait");
            return (Criteria) this;
        }

        public Criteria andInterestWaitLessThanOrEqualTo(BigDecimal value) {
            addCriterion("interest_wait <=", value, "interestWait");
            return (Criteria) this;
        }

        public Criteria andInterestWaitIn(List<BigDecimal> values) {
            addCriterion("interest_wait in", values, "interestWait");
            return (Criteria) this;
        }

        public Criteria andInterestWaitNotIn(List<BigDecimal> values) {
            addCriterion("interest_wait not in", values, "interestWait");
            return (Criteria) this;
        }

        public Criteria andInterestWaitBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("interest_wait between", value1, value2, "interestWait");
            return (Criteria) this;
        }

        public Criteria andInterestWaitNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("interest_wait not between", value1, value2, "interestWait");
            return (Criteria) this;
        }

        public Criteria andRepayWaitIsNull() {
            addCriterion("repay_wait is null");
            return (Criteria) this;
        }

        public Criteria andRepayWaitIsNotNull() {
            addCriterion("repay_wait is not null");
            return (Criteria) this;
        }

        public Criteria andRepayWaitEqualTo(BigDecimal value) {
            addCriterion("repay_wait =", value, "repayWait");
            return (Criteria) this;
        }

        public Criteria andRepayWaitNotEqualTo(BigDecimal value) {
            addCriterion("repay_wait <>", value, "repayWait");
            return (Criteria) this;
        }

        public Criteria andRepayWaitGreaterThan(BigDecimal value) {
            addCriterion("repay_wait >", value, "repayWait");
            return (Criteria) this;
        }

        public Criteria andRepayWaitGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_wait >=", value, "repayWait");
            return (Criteria) this;
        }

        public Criteria andRepayWaitLessThan(BigDecimal value) {
            addCriterion("repay_wait <", value, "repayWait");
            return (Criteria) this;
        }

        public Criteria andRepayWaitLessThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_wait <=", value, "repayWait");
            return (Criteria) this;
        }

        public Criteria andRepayWaitIn(List<BigDecimal> values) {
            addCriterion("repay_wait in", values, "repayWait");
            return (Criteria) this;
        }

        public Criteria andRepayWaitNotIn(List<BigDecimal> values) {
            addCriterion("repay_wait not in", values, "repayWait");
            return (Criteria) this;
        }

        public Criteria andRepayWaitBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_wait between", value1, value2, "repayWait");
            return (Criteria) this;
        }

        public Criteria andRepayWaitNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_wait not between", value1, value2, "repayWait");
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

        public Criteria andIpIsNull() {
            addCriterion("ip is null");
            return (Criteria) this;
        }

        public Criteria andIpIsNotNull() {
            addCriterion("ip is not null");
            return (Criteria) this;
        }

        public Criteria andIpEqualTo(String value) {
            addCriterion("ip =", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotEqualTo(String value) {
            addCriterion("ip <>", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpGreaterThan(String value) {
            addCriterion("ip >", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpGreaterThanOrEqualTo(String value) {
            addCriterion("ip >=", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLessThan(String value) {
            addCriterion("ip <", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLessThanOrEqualTo(String value) {
            addCriterion("ip <=", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLike(String value) {
            addCriterion("ip like", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotLike(String value) {
            addCriterion("ip not like", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpIn(List<String> values) {
            addCriterion("ip in", values, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotIn(List<String> values) {
            addCriterion("ip not in", values, "ip");
            return (Criteria) this;
        }

        public Criteria andIpBetween(String value1, String value2) {
            addCriterion("ip between", value1, value2, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotBetween(String value1, String value2) {
            addCriterion("ip not between", value1, value2, "ip");
            return (Criteria) this;
        }

        public Criteria andWebIsNull() {
            addCriterion("web is null");
            return (Criteria) this;
        }

        public Criteria andWebIsNotNull() {
            addCriterion("web is not null");
            return (Criteria) this;
        }

        public Criteria andWebEqualTo(Integer value) {
            addCriterion("web =", value, "web");
            return (Criteria) this;
        }

        public Criteria andWebNotEqualTo(Integer value) {
            addCriterion("web <>", value, "web");
            return (Criteria) this;
        }

        public Criteria andWebGreaterThan(Integer value) {
            addCriterion("web >", value, "web");
            return (Criteria) this;
        }

        public Criteria andWebGreaterThanOrEqualTo(Integer value) {
            addCriterion("web >=", value, "web");
            return (Criteria) this;
        }

        public Criteria andWebLessThan(Integer value) {
            addCriterion("web <", value, "web");
            return (Criteria) this;
        }

        public Criteria andWebLessThanOrEqualTo(Integer value) {
            addCriterion("web <=", value, "web");
            return (Criteria) this;
        }

        public Criteria andWebIn(List<Integer> values) {
            addCriterion("web in", values, "web");
            return (Criteria) this;
        }

        public Criteria andWebNotIn(List<Integer> values) {
            addCriterion("web not in", values, "web");
            return (Criteria) this;
        }

        public Criteria andWebBetween(Integer value1, Integer value2) {
            addCriterion("web between", value1, value2, "web");
            return (Criteria) this;
        }

        public Criteria andWebNotBetween(Integer value1, Integer value2) {
            addCriterion("web not between", value1, value2, "web");
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