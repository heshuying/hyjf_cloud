package com.hyjf.am.trade.dao.model.auto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DebtAccedeCommissionExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public DebtAccedeCommissionExample() {
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

        public Criteria andPlanLockPeriodIsNull() {
            addCriterion("plan_lock_period is null");
            return (Criteria) this;
        }

        public Criteria andPlanLockPeriodIsNotNull() {
            addCriterion("plan_lock_period is not null");
            return (Criteria) this;
        }

        public Criteria andPlanLockPeriodEqualTo(Integer value) {
            addCriterion("plan_lock_period =", value, "planLockPeriod");
            return (Criteria) this;
        }

        public Criteria andPlanLockPeriodNotEqualTo(Integer value) {
            addCriterion("plan_lock_period <>", value, "planLockPeriod");
            return (Criteria) this;
        }

        public Criteria andPlanLockPeriodGreaterThan(Integer value) {
            addCriterion("plan_lock_period >", value, "planLockPeriod");
            return (Criteria) this;
        }

        public Criteria andPlanLockPeriodGreaterThanOrEqualTo(Integer value) {
            addCriterion("plan_lock_period >=", value, "planLockPeriod");
            return (Criteria) this;
        }

        public Criteria andPlanLockPeriodLessThan(Integer value) {
            addCriterion("plan_lock_period <", value, "planLockPeriod");
            return (Criteria) this;
        }

        public Criteria andPlanLockPeriodLessThanOrEqualTo(Integer value) {
            addCriterion("plan_lock_period <=", value, "planLockPeriod");
            return (Criteria) this;
        }

        public Criteria andPlanLockPeriodIn(List<Integer> values) {
            addCriterion("plan_lock_period in", values, "planLockPeriod");
            return (Criteria) this;
        }

        public Criteria andPlanLockPeriodNotIn(List<Integer> values) {
            addCriterion("plan_lock_period not in", values, "planLockPeriod");
            return (Criteria) this;
        }

        public Criteria andPlanLockPeriodBetween(Integer value1, Integer value2) {
            addCriterion("plan_lock_period between", value1, value2, "planLockPeriod");
            return (Criteria) this;
        }

        public Criteria andPlanLockPeriodNotBetween(Integer value1, Integer value2) {
            addCriterion("plan_lock_period not between", value1, value2, "planLockPeriod");
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

        public Criteria andIs51IsNull() {
            addCriterion("is51 is null");
            return (Criteria) this;
        }

        public Criteria andIs51IsNotNull() {
            addCriterion("is51 is not null");
            return (Criteria) this;
        }

        public Criteria andIs51EqualTo(Integer value) {
            addCriterion("is51 =", value, "is51");
            return (Criteria) this;
        }

        public Criteria andIs51NotEqualTo(Integer value) {
            addCriterion("is51 <>", value, "is51");
            return (Criteria) this;
        }

        public Criteria andIs51GreaterThan(Integer value) {
            addCriterion("is51 >", value, "is51");
            return (Criteria) this;
        }

        public Criteria andIs51GreaterThanOrEqualTo(Integer value) {
            addCriterion("is51 >=", value, "is51");
            return (Criteria) this;
        }

        public Criteria andIs51LessThan(Integer value) {
            addCriterion("is51 <", value, "is51");
            return (Criteria) this;
        }

        public Criteria andIs51LessThanOrEqualTo(Integer value) {
            addCriterion("is51 <=", value, "is51");
            return (Criteria) this;
        }

        public Criteria andIs51In(List<Integer> values) {
            addCriterion("is51 in", values, "is51");
            return (Criteria) this;
        }

        public Criteria andIs51NotIn(List<Integer> values) {
            addCriterion("is51 not in", values, "is51");
            return (Criteria) this;
        }

        public Criteria andIs51Between(Integer value1, Integer value2) {
            addCriterion("is51 between", value1, value2, "is51");
            return (Criteria) this;
        }

        public Criteria andIs51NotBetween(Integer value1, Integer value2) {
            addCriterion("is51 not between", value1, value2, "is51");
            return (Criteria) this;
        }

        public Criteria andRegionIdIsNull() {
            addCriterion("region_id is null");
            return (Criteria) this;
        }

        public Criteria andRegionIdIsNotNull() {
            addCriterion("region_id is not null");
            return (Criteria) this;
        }

        public Criteria andRegionIdEqualTo(Integer value) {
            addCriterion("region_id =", value, "regionId");
            return (Criteria) this;
        }

        public Criteria andRegionIdNotEqualTo(Integer value) {
            addCriterion("region_id <>", value, "regionId");
            return (Criteria) this;
        }

        public Criteria andRegionIdGreaterThan(Integer value) {
            addCriterion("region_id >", value, "regionId");
            return (Criteria) this;
        }

        public Criteria andRegionIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("region_id >=", value, "regionId");
            return (Criteria) this;
        }

        public Criteria andRegionIdLessThan(Integer value) {
            addCriterion("region_id <", value, "regionId");
            return (Criteria) this;
        }

        public Criteria andRegionIdLessThanOrEqualTo(Integer value) {
            addCriterion("region_id <=", value, "regionId");
            return (Criteria) this;
        }

        public Criteria andRegionIdIn(List<Integer> values) {
            addCriterion("region_id in", values, "regionId");
            return (Criteria) this;
        }

        public Criteria andRegionIdNotIn(List<Integer> values) {
            addCriterion("region_id not in", values, "regionId");
            return (Criteria) this;
        }

        public Criteria andRegionIdBetween(Integer value1, Integer value2) {
            addCriterion("region_id between", value1, value2, "regionId");
            return (Criteria) this;
        }

        public Criteria andRegionIdNotBetween(Integer value1, Integer value2) {
            addCriterion("region_id not between", value1, value2, "regionId");
            return (Criteria) this;
        }

        public Criteria andRegionNameIsNull() {
            addCriterion("region_name is null");
            return (Criteria) this;
        }

        public Criteria andRegionNameIsNotNull() {
            addCriterion("region_name is not null");
            return (Criteria) this;
        }

        public Criteria andRegionNameEqualTo(String value) {
            addCriterion("region_name =", value, "regionName");
            return (Criteria) this;
        }

        public Criteria andRegionNameNotEqualTo(String value) {
            addCriterion("region_name <>", value, "regionName");
            return (Criteria) this;
        }

        public Criteria andRegionNameGreaterThan(String value) {
            addCriterion("region_name >", value, "regionName");
            return (Criteria) this;
        }

        public Criteria andRegionNameGreaterThanOrEqualTo(String value) {
            addCriterion("region_name >=", value, "regionName");
            return (Criteria) this;
        }

        public Criteria andRegionNameLessThan(String value) {
            addCriterion("region_name <", value, "regionName");
            return (Criteria) this;
        }

        public Criteria andRegionNameLessThanOrEqualTo(String value) {
            addCriterion("region_name <=", value, "regionName");
            return (Criteria) this;
        }

        public Criteria andRegionNameLike(String value) {
            addCriterion("region_name like", value, "regionName");
            return (Criteria) this;
        }

        public Criteria andRegionNameNotLike(String value) {
            addCriterion("region_name not like", value, "regionName");
            return (Criteria) this;
        }

        public Criteria andRegionNameIn(List<String> values) {
            addCriterion("region_name in", values, "regionName");
            return (Criteria) this;
        }

        public Criteria andRegionNameNotIn(List<String> values) {
            addCriterion("region_name not in", values, "regionName");
            return (Criteria) this;
        }

        public Criteria andRegionNameBetween(String value1, String value2) {
            addCriterion("region_name between", value1, value2, "regionName");
            return (Criteria) this;
        }

        public Criteria andRegionNameNotBetween(String value1, String value2) {
            addCriterion("region_name not between", value1, value2, "regionName");
            return (Criteria) this;
        }

        public Criteria andBranchIdIsNull() {
            addCriterion("branch_id is null");
            return (Criteria) this;
        }

        public Criteria andBranchIdIsNotNull() {
            addCriterion("branch_id is not null");
            return (Criteria) this;
        }

        public Criteria andBranchIdEqualTo(Integer value) {
            addCriterion("branch_id =", value, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdNotEqualTo(Integer value) {
            addCriterion("branch_id <>", value, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdGreaterThan(Integer value) {
            addCriterion("branch_id >", value, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("branch_id >=", value, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdLessThan(Integer value) {
            addCriterion("branch_id <", value, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdLessThanOrEqualTo(Integer value) {
            addCriterion("branch_id <=", value, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdIn(List<Integer> values) {
            addCriterion("branch_id in", values, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdNotIn(List<Integer> values) {
            addCriterion("branch_id not in", values, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdBetween(Integer value1, Integer value2) {
            addCriterion("branch_id between", value1, value2, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdNotBetween(Integer value1, Integer value2) {
            addCriterion("branch_id not between", value1, value2, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchNameIsNull() {
            addCriterion("branch_name is null");
            return (Criteria) this;
        }

        public Criteria andBranchNameIsNotNull() {
            addCriterion("branch_name is not null");
            return (Criteria) this;
        }

        public Criteria andBranchNameEqualTo(String value) {
            addCriterion("branch_name =", value, "branchName");
            return (Criteria) this;
        }

        public Criteria andBranchNameNotEqualTo(String value) {
            addCriterion("branch_name <>", value, "branchName");
            return (Criteria) this;
        }

        public Criteria andBranchNameGreaterThan(String value) {
            addCriterion("branch_name >", value, "branchName");
            return (Criteria) this;
        }

        public Criteria andBranchNameGreaterThanOrEqualTo(String value) {
            addCriterion("branch_name >=", value, "branchName");
            return (Criteria) this;
        }

        public Criteria andBranchNameLessThan(String value) {
            addCriterion("branch_name <", value, "branchName");
            return (Criteria) this;
        }

        public Criteria andBranchNameLessThanOrEqualTo(String value) {
            addCriterion("branch_name <=", value, "branchName");
            return (Criteria) this;
        }

        public Criteria andBranchNameLike(String value) {
            addCriterion("branch_name like", value, "branchName");
            return (Criteria) this;
        }

        public Criteria andBranchNameNotLike(String value) {
            addCriterion("branch_name not like", value, "branchName");
            return (Criteria) this;
        }

        public Criteria andBranchNameIn(List<String> values) {
            addCriterion("branch_name in", values, "branchName");
            return (Criteria) this;
        }

        public Criteria andBranchNameNotIn(List<String> values) {
            addCriterion("branch_name not in", values, "branchName");
            return (Criteria) this;
        }

        public Criteria andBranchNameBetween(String value1, String value2) {
            addCriterion("branch_name between", value1, value2, "branchName");
            return (Criteria) this;
        }

        public Criteria andBranchNameNotBetween(String value1, String value2) {
            addCriterion("branch_name not between", value1, value2, "branchName");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdIsNull() {
            addCriterion("department_id is null");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdIsNotNull() {
            addCriterion("department_id is not null");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdEqualTo(Integer value) {
            addCriterion("department_id =", value, "departmentId");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdNotEqualTo(Integer value) {
            addCriterion("department_id <>", value, "departmentId");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdGreaterThan(Integer value) {
            addCriterion("department_id >", value, "departmentId");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("department_id >=", value, "departmentId");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdLessThan(Integer value) {
            addCriterion("department_id <", value, "departmentId");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdLessThanOrEqualTo(Integer value) {
            addCriterion("department_id <=", value, "departmentId");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdIn(List<Integer> values) {
            addCriterion("department_id in", values, "departmentId");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdNotIn(List<Integer> values) {
            addCriterion("department_id not in", values, "departmentId");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdBetween(Integer value1, Integer value2) {
            addCriterion("department_id between", value1, value2, "departmentId");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdNotBetween(Integer value1, Integer value2) {
            addCriterion("department_id not between", value1, value2, "departmentId");
            return (Criteria) this;
        }

        public Criteria andDepartmentNameIsNull() {
            addCriterion("department_name is null");
            return (Criteria) this;
        }

        public Criteria andDepartmentNameIsNotNull() {
            addCriterion("department_name is not null");
            return (Criteria) this;
        }

        public Criteria andDepartmentNameEqualTo(String value) {
            addCriterion("department_name =", value, "departmentName");
            return (Criteria) this;
        }

        public Criteria andDepartmentNameNotEqualTo(String value) {
            addCriterion("department_name <>", value, "departmentName");
            return (Criteria) this;
        }

        public Criteria andDepartmentNameGreaterThan(String value) {
            addCriterion("department_name >", value, "departmentName");
            return (Criteria) this;
        }

        public Criteria andDepartmentNameGreaterThanOrEqualTo(String value) {
            addCriterion("department_name >=", value, "departmentName");
            return (Criteria) this;
        }

        public Criteria andDepartmentNameLessThan(String value) {
            addCriterion("department_name <", value, "departmentName");
            return (Criteria) this;
        }

        public Criteria andDepartmentNameLessThanOrEqualTo(String value) {
            addCriterion("department_name <=", value, "departmentName");
            return (Criteria) this;
        }

        public Criteria andDepartmentNameLike(String value) {
            addCriterion("department_name like", value, "departmentName");
            return (Criteria) this;
        }

        public Criteria andDepartmentNameNotLike(String value) {
            addCriterion("department_name not like", value, "departmentName");
            return (Criteria) this;
        }

        public Criteria andDepartmentNameIn(List<String> values) {
            addCriterion("department_name in", values, "departmentName");
            return (Criteria) this;
        }

        public Criteria andDepartmentNameNotIn(List<String> values) {
            addCriterion("department_name not in", values, "departmentName");
            return (Criteria) this;
        }

        public Criteria andDepartmentNameBetween(String value1, String value2) {
            addCriterion("department_name between", value1, value2, "departmentName");
            return (Criteria) this;
        }

        public Criteria andDepartmentNameNotBetween(String value1, String value2) {
            addCriterion("department_name not between", value1, value2, "departmentName");
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

        public Criteria andAccedeUserIdIsNull() {
            addCriterion("accede_user_id is null");
            return (Criteria) this;
        }

        public Criteria andAccedeUserIdIsNotNull() {
            addCriterion("accede_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andAccedeUserIdEqualTo(Integer value) {
            addCriterion("accede_user_id =", value, "accedeUserId");
            return (Criteria) this;
        }

        public Criteria andAccedeUserIdNotEqualTo(Integer value) {
            addCriterion("accede_user_id <>", value, "accedeUserId");
            return (Criteria) this;
        }

        public Criteria andAccedeUserIdGreaterThan(Integer value) {
            addCriterion("accede_user_id >", value, "accedeUserId");
            return (Criteria) this;
        }

        public Criteria andAccedeUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("accede_user_id >=", value, "accedeUserId");
            return (Criteria) this;
        }

        public Criteria andAccedeUserIdLessThan(Integer value) {
            addCriterion("accede_user_id <", value, "accedeUserId");
            return (Criteria) this;
        }

        public Criteria andAccedeUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("accede_user_id <=", value, "accedeUserId");
            return (Criteria) this;
        }

        public Criteria andAccedeUserIdIn(List<Integer> values) {
            addCriterion("accede_user_id in", values, "accedeUserId");
            return (Criteria) this;
        }

        public Criteria andAccedeUserIdNotIn(List<Integer> values) {
            addCriterion("accede_user_id not in", values, "accedeUserId");
            return (Criteria) this;
        }

        public Criteria andAccedeUserIdBetween(Integer value1, Integer value2) {
            addCriterion("accede_user_id between", value1, value2, "accedeUserId");
            return (Criteria) this;
        }

        public Criteria andAccedeUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("accede_user_id not between", value1, value2, "accedeUserId");
            return (Criteria) this;
        }

        public Criteria andAccedeUserNameIsNull() {
            addCriterion("accede_user_name is null");
            return (Criteria) this;
        }

        public Criteria andAccedeUserNameIsNotNull() {
            addCriterion("accede_user_name is not null");
            return (Criteria) this;
        }

        public Criteria andAccedeUserNameEqualTo(String value) {
            addCriterion("accede_user_name =", value, "accedeUserName");
            return (Criteria) this;
        }

        public Criteria andAccedeUserNameNotEqualTo(String value) {
            addCriterion("accede_user_name <>", value, "accedeUserName");
            return (Criteria) this;
        }

        public Criteria andAccedeUserNameGreaterThan(String value) {
            addCriterion("accede_user_name >", value, "accedeUserName");
            return (Criteria) this;
        }

        public Criteria andAccedeUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("accede_user_name >=", value, "accedeUserName");
            return (Criteria) this;
        }

        public Criteria andAccedeUserNameLessThan(String value) {
            addCriterion("accede_user_name <", value, "accedeUserName");
            return (Criteria) this;
        }

        public Criteria andAccedeUserNameLessThanOrEqualTo(String value) {
            addCriterion("accede_user_name <=", value, "accedeUserName");
            return (Criteria) this;
        }

        public Criteria andAccedeUserNameLike(String value) {
            addCriterion("accede_user_name like", value, "accedeUserName");
            return (Criteria) this;
        }

        public Criteria andAccedeUserNameNotLike(String value) {
            addCriterion("accede_user_name not like", value, "accedeUserName");
            return (Criteria) this;
        }

        public Criteria andAccedeUserNameIn(List<String> values) {
            addCriterion("accede_user_name in", values, "accedeUserName");
            return (Criteria) this;
        }

        public Criteria andAccedeUserNameNotIn(List<String> values) {
            addCriterion("accede_user_name not in", values, "accedeUserName");
            return (Criteria) this;
        }

        public Criteria andAccedeUserNameBetween(String value1, String value2) {
            addCriterion("accede_user_name between", value1, value2, "accedeUserName");
            return (Criteria) this;
        }

        public Criteria andAccedeUserNameNotBetween(String value1, String value2) {
            addCriterion("accede_user_name not between", value1, value2, "accedeUserName");
            return (Criteria) this;
        }

        public Criteria andAccedeDepartmentIdIsNull() {
            addCriterion("accede_department_id is null");
            return (Criteria) this;
        }

        public Criteria andAccedeDepartmentIdIsNotNull() {
            addCriterion("accede_department_id is not null");
            return (Criteria) this;
        }

        public Criteria andAccedeDepartmentIdEqualTo(Integer value) {
            addCriterion("accede_department_id =", value, "accedeDepartmentId");
            return (Criteria) this;
        }

        public Criteria andAccedeDepartmentIdNotEqualTo(Integer value) {
            addCriterion("accede_department_id <>", value, "accedeDepartmentId");
            return (Criteria) this;
        }

        public Criteria andAccedeDepartmentIdGreaterThan(Integer value) {
            addCriterion("accede_department_id >", value, "accedeDepartmentId");
            return (Criteria) this;
        }

        public Criteria andAccedeDepartmentIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("accede_department_id >=", value, "accedeDepartmentId");
            return (Criteria) this;
        }

        public Criteria andAccedeDepartmentIdLessThan(Integer value) {
            addCriterion("accede_department_id <", value, "accedeDepartmentId");
            return (Criteria) this;
        }

        public Criteria andAccedeDepartmentIdLessThanOrEqualTo(Integer value) {
            addCriterion("accede_department_id <=", value, "accedeDepartmentId");
            return (Criteria) this;
        }

        public Criteria andAccedeDepartmentIdIn(List<Integer> values) {
            addCriterion("accede_department_id in", values, "accedeDepartmentId");
            return (Criteria) this;
        }

        public Criteria andAccedeDepartmentIdNotIn(List<Integer> values) {
            addCriterion("accede_department_id not in", values, "accedeDepartmentId");
            return (Criteria) this;
        }

        public Criteria andAccedeDepartmentIdBetween(Integer value1, Integer value2) {
            addCriterion("accede_department_id between", value1, value2, "accedeDepartmentId");
            return (Criteria) this;
        }

        public Criteria andAccedeDepartmentIdNotBetween(Integer value1, Integer value2) {
            addCriterion("accede_department_id not between", value1, value2, "accedeDepartmentId");
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

        public Criteria andAccedeTimeIsNull() {
            addCriterion("accede_time is null");
            return (Criteria) this;
        }

        public Criteria andAccedeTimeIsNotNull() {
            addCriterion("accede_time is not null");
            return (Criteria) this;
        }

        public Criteria andAccedeTimeEqualTo(Integer value) {
            addCriterion("accede_time =", value, "accedeTime");
            return (Criteria) this;
        }

        public Criteria andAccedeTimeNotEqualTo(Integer value) {
            addCriterion("accede_time <>", value, "accedeTime");
            return (Criteria) this;
        }

        public Criteria andAccedeTimeGreaterThan(Integer value) {
            addCriterion("accede_time >", value, "accedeTime");
            return (Criteria) this;
        }

        public Criteria andAccedeTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("accede_time >=", value, "accedeTime");
            return (Criteria) this;
        }

        public Criteria andAccedeTimeLessThan(Integer value) {
            addCriterion("accede_time <", value, "accedeTime");
            return (Criteria) this;
        }

        public Criteria andAccedeTimeLessThanOrEqualTo(Integer value) {
            addCriterion("accede_time <=", value, "accedeTime");
            return (Criteria) this;
        }

        public Criteria andAccedeTimeIn(List<Integer> values) {
            addCriterion("accede_time in", values, "accedeTime");
            return (Criteria) this;
        }

        public Criteria andAccedeTimeNotIn(List<Integer> values) {
            addCriterion("accede_time not in", values, "accedeTime");
            return (Criteria) this;
        }

        public Criteria andAccedeTimeBetween(Integer value1, Integer value2) {
            addCriterion("accede_time between", value1, value2, "accedeTime");
            return (Criteria) this;
        }

        public Criteria andAccedeTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("accede_time not between", value1, value2, "accedeTime");
            return (Criteria) this;
        }

        public Criteria andCommissionIsNull() {
            addCriterion("commission is null");
            return (Criteria) this;
        }

        public Criteria andCommissionIsNotNull() {
            addCriterion("commission is not null");
            return (Criteria) this;
        }

        public Criteria andCommissionEqualTo(BigDecimal value) {
            addCriterion("commission =", value, "commission");
            return (Criteria) this;
        }

        public Criteria andCommissionNotEqualTo(BigDecimal value) {
            addCriterion("commission <>", value, "commission");
            return (Criteria) this;
        }

        public Criteria andCommissionGreaterThan(BigDecimal value) {
            addCriterion("commission >", value, "commission");
            return (Criteria) this;
        }

        public Criteria andCommissionGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("commission >=", value, "commission");
            return (Criteria) this;
        }

        public Criteria andCommissionLessThan(BigDecimal value) {
            addCriterion("commission <", value, "commission");
            return (Criteria) this;
        }

        public Criteria andCommissionLessThanOrEqualTo(BigDecimal value) {
            addCriterion("commission <=", value, "commission");
            return (Criteria) this;
        }

        public Criteria andCommissionIn(List<BigDecimal> values) {
            addCriterion("commission in", values, "commission");
            return (Criteria) this;
        }

        public Criteria andCommissionNotIn(List<BigDecimal> values) {
            addCriterion("commission not in", values, "commission");
            return (Criteria) this;
        }

        public Criteria andCommissionBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("commission between", value1, value2, "commission");
            return (Criteria) this;
        }

        public Criteria andCommissionNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("commission not between", value1, value2, "commission");
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

        public Criteria andStatusEqualTo(Byte value) {
            addCriterion("`status` =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Byte value) {
            addCriterion("`status` <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Byte value) {
            addCriterion("`status` >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Byte value) {
            addCriterion("`status` >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Byte value) {
            addCriterion("`status` <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Byte value) {
            addCriterion("`status` <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Byte> values) {
            addCriterion("`status` in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Byte> values) {
            addCriterion("`status` not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Byte value1, Byte value2) {
            addCriterion("`status` between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Byte value1, Byte value2) {
            addCriterion("`status` not between", value1, value2, "status");
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

        public Criteria andComputeTimeIsNull() {
            addCriterion("compute_time is null");
            return (Criteria) this;
        }

        public Criteria andComputeTimeIsNotNull() {
            addCriterion("compute_time is not null");
            return (Criteria) this;
        }

        public Criteria andComputeTimeEqualTo(Integer value) {
            addCriterion("compute_time =", value, "computeTime");
            return (Criteria) this;
        }

        public Criteria andComputeTimeNotEqualTo(Integer value) {
            addCriterion("compute_time <>", value, "computeTime");
            return (Criteria) this;
        }

        public Criteria andComputeTimeGreaterThan(Integer value) {
            addCriterion("compute_time >", value, "computeTime");
            return (Criteria) this;
        }

        public Criteria andComputeTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("compute_time >=", value, "computeTime");
            return (Criteria) this;
        }

        public Criteria andComputeTimeLessThan(Integer value) {
            addCriterion("compute_time <", value, "computeTime");
            return (Criteria) this;
        }

        public Criteria andComputeTimeLessThanOrEqualTo(Integer value) {
            addCriterion("compute_time <=", value, "computeTime");
            return (Criteria) this;
        }

        public Criteria andComputeTimeIn(List<Integer> values) {
            addCriterion("compute_time in", values, "computeTime");
            return (Criteria) this;
        }

        public Criteria andComputeTimeNotIn(List<Integer> values) {
            addCriterion("compute_time not in", values, "computeTime");
            return (Criteria) this;
        }

        public Criteria andComputeTimeBetween(Integer value1, Integer value2) {
            addCriterion("compute_time between", value1, value2, "computeTime");
            return (Criteria) this;
        }

        public Criteria andComputeTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("compute_time not between", value1, value2, "computeTime");
            return (Criteria) this;
        }

        public Criteria andReturnTimeIsNull() {
            addCriterion("return_time is null");
            return (Criteria) this;
        }

        public Criteria andReturnTimeIsNotNull() {
            addCriterion("return_time is not null");
            return (Criteria) this;
        }

        public Criteria andReturnTimeEqualTo(Integer value) {
            addCriterion("return_time =", value, "returnTime");
            return (Criteria) this;
        }

        public Criteria andReturnTimeNotEqualTo(Integer value) {
            addCriterion("return_time <>", value, "returnTime");
            return (Criteria) this;
        }

        public Criteria andReturnTimeGreaterThan(Integer value) {
            addCriterion("return_time >", value, "returnTime");
            return (Criteria) this;
        }

        public Criteria andReturnTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("return_time >=", value, "returnTime");
            return (Criteria) this;
        }

        public Criteria andReturnTimeLessThan(Integer value) {
            addCriterion("return_time <", value, "returnTime");
            return (Criteria) this;
        }

        public Criteria andReturnTimeLessThanOrEqualTo(Integer value) {
            addCriterion("return_time <=", value, "returnTime");
            return (Criteria) this;
        }

        public Criteria andReturnTimeIn(List<Integer> values) {
            addCriterion("return_time in", values, "returnTime");
            return (Criteria) this;
        }

        public Criteria andReturnTimeNotIn(List<Integer> values) {
            addCriterion("return_time not in", values, "returnTime");
            return (Criteria) this;
        }

        public Criteria andReturnTimeBetween(Integer value1, Integer value2) {
            addCriterion("return_time between", value1, value2, "returnTime");
            return (Criteria) this;
        }

        public Criteria andReturnTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("return_time not between", value1, value2, "returnTime");
            return (Criteria) this;
        }

        public Criteria andReturnUserIdIsNull() {
            addCriterion("return_user_id is null");
            return (Criteria) this;
        }

        public Criteria andReturnUserIdIsNotNull() {
            addCriterion("return_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andReturnUserIdEqualTo(Integer value) {
            addCriterion("return_user_id =", value, "returnUserId");
            return (Criteria) this;
        }

        public Criteria andReturnUserIdNotEqualTo(Integer value) {
            addCriterion("return_user_id <>", value, "returnUserId");
            return (Criteria) this;
        }

        public Criteria andReturnUserIdGreaterThan(Integer value) {
            addCriterion("return_user_id >", value, "returnUserId");
            return (Criteria) this;
        }

        public Criteria andReturnUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("return_user_id >=", value, "returnUserId");
            return (Criteria) this;
        }

        public Criteria andReturnUserIdLessThan(Integer value) {
            addCriterion("return_user_id <", value, "returnUserId");
            return (Criteria) this;
        }

        public Criteria andReturnUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("return_user_id <=", value, "returnUserId");
            return (Criteria) this;
        }

        public Criteria andReturnUserIdIn(List<Integer> values) {
            addCriterion("return_user_id in", values, "returnUserId");
            return (Criteria) this;
        }

        public Criteria andReturnUserIdNotIn(List<Integer> values) {
            addCriterion("return_user_id not in", values, "returnUserId");
            return (Criteria) this;
        }

        public Criteria andReturnUserIdBetween(Integer value1, Integer value2) {
            addCriterion("return_user_id between", value1, value2, "returnUserId");
            return (Criteria) this;
        }

        public Criteria andReturnUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("return_user_id not between", value1, value2, "returnUserId");
            return (Criteria) this;
        }

        public Criteria andReturnUserNameIsNull() {
            addCriterion("return_user_name is null");
            return (Criteria) this;
        }

        public Criteria andReturnUserNameIsNotNull() {
            addCriterion("return_user_name is not null");
            return (Criteria) this;
        }

        public Criteria andReturnUserNameEqualTo(String value) {
            addCriterion("return_user_name =", value, "returnUserName");
            return (Criteria) this;
        }

        public Criteria andReturnUserNameNotEqualTo(String value) {
            addCriterion("return_user_name <>", value, "returnUserName");
            return (Criteria) this;
        }

        public Criteria andReturnUserNameGreaterThan(String value) {
            addCriterion("return_user_name >", value, "returnUserName");
            return (Criteria) this;
        }

        public Criteria andReturnUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("return_user_name >=", value, "returnUserName");
            return (Criteria) this;
        }

        public Criteria andReturnUserNameLessThan(String value) {
            addCriterion("return_user_name <", value, "returnUserName");
            return (Criteria) this;
        }

        public Criteria andReturnUserNameLessThanOrEqualTo(String value) {
            addCriterion("return_user_name <=", value, "returnUserName");
            return (Criteria) this;
        }

        public Criteria andReturnUserNameLike(String value) {
            addCriterion("return_user_name like", value, "returnUserName");
            return (Criteria) this;
        }

        public Criteria andReturnUserNameNotLike(String value) {
            addCriterion("return_user_name not like", value, "returnUserName");
            return (Criteria) this;
        }

        public Criteria andReturnUserNameIn(List<String> values) {
            addCriterion("return_user_name in", values, "returnUserName");
            return (Criteria) this;
        }

        public Criteria andReturnUserNameNotIn(List<String> values) {
            addCriterion("return_user_name not in", values, "returnUserName");
            return (Criteria) this;
        }

        public Criteria andReturnUserNameBetween(String value1, String value2) {
            addCriterion("return_user_name between", value1, value2, "returnUserName");
            return (Criteria) this;
        }

        public Criteria andReturnUserNameNotBetween(String value1, String value2) {
            addCriterion("return_user_name not between", value1, value2, "returnUserName");
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