package com.hyjf.am.trade.dao.model.auto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class HjhAccedeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public HjhAccedeExample() {
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

        public Criteria andInviteUserRegionnameIsNull() {
            addCriterion("invite_user_regionname is null");
            return (Criteria) this;
        }

        public Criteria andInviteUserRegionnameIsNotNull() {
            addCriterion("invite_user_regionname is not null");
            return (Criteria) this;
        }

        public Criteria andInviteUserRegionnameEqualTo(String value) {
            addCriterion("invite_user_regionname =", value, "inviteUserRegionname");
            return (Criteria) this;
        }

        public Criteria andInviteUserRegionnameNotEqualTo(String value) {
            addCriterion("invite_user_regionname <>", value, "inviteUserRegionname");
            return (Criteria) this;
        }

        public Criteria andInviteUserRegionnameGreaterThan(String value) {
            addCriterion("invite_user_regionname >", value, "inviteUserRegionname");
            return (Criteria) this;
        }

        public Criteria andInviteUserRegionnameGreaterThanOrEqualTo(String value) {
            addCriterion("invite_user_regionname >=", value, "inviteUserRegionname");
            return (Criteria) this;
        }

        public Criteria andInviteUserRegionnameLessThan(String value) {
            addCriterion("invite_user_regionname <", value, "inviteUserRegionname");
            return (Criteria) this;
        }

        public Criteria andInviteUserRegionnameLessThanOrEqualTo(String value) {
            addCriterion("invite_user_regionname <=", value, "inviteUserRegionname");
            return (Criteria) this;
        }

        public Criteria andInviteUserRegionnameLike(String value) {
            addCriterion("invite_user_regionname like", value, "inviteUserRegionname");
            return (Criteria) this;
        }

        public Criteria andInviteUserRegionnameNotLike(String value) {
            addCriterion("invite_user_regionname not like", value, "inviteUserRegionname");
            return (Criteria) this;
        }

        public Criteria andInviteUserRegionnameIn(List<String> values) {
            addCriterion("invite_user_regionname in", values, "inviteUserRegionname");
            return (Criteria) this;
        }

        public Criteria andInviteUserRegionnameNotIn(List<String> values) {
            addCriterion("invite_user_regionname not in", values, "inviteUserRegionname");
            return (Criteria) this;
        }

        public Criteria andInviteUserRegionnameBetween(String value1, String value2) {
            addCriterion("invite_user_regionname between", value1, value2, "inviteUserRegionname");
            return (Criteria) this;
        }

        public Criteria andInviteUserRegionnameNotBetween(String value1, String value2) {
            addCriterion("invite_user_regionname not between", value1, value2, "inviteUserRegionname");
            return (Criteria) this;
        }

        public Criteria andInviteUserBranchnameIsNull() {
            addCriterion("invite_user_branchname is null");
            return (Criteria) this;
        }

        public Criteria andInviteUserBranchnameIsNotNull() {
            addCriterion("invite_user_branchname is not null");
            return (Criteria) this;
        }

        public Criteria andInviteUserBranchnameEqualTo(String value) {
            addCriterion("invite_user_branchname =", value, "inviteUserBranchname");
            return (Criteria) this;
        }

        public Criteria andInviteUserBranchnameNotEqualTo(String value) {
            addCriterion("invite_user_branchname <>", value, "inviteUserBranchname");
            return (Criteria) this;
        }

        public Criteria andInviteUserBranchnameGreaterThan(String value) {
            addCriterion("invite_user_branchname >", value, "inviteUserBranchname");
            return (Criteria) this;
        }

        public Criteria andInviteUserBranchnameGreaterThanOrEqualTo(String value) {
            addCriterion("invite_user_branchname >=", value, "inviteUserBranchname");
            return (Criteria) this;
        }

        public Criteria andInviteUserBranchnameLessThan(String value) {
            addCriterion("invite_user_branchname <", value, "inviteUserBranchname");
            return (Criteria) this;
        }

        public Criteria andInviteUserBranchnameLessThanOrEqualTo(String value) {
            addCriterion("invite_user_branchname <=", value, "inviteUserBranchname");
            return (Criteria) this;
        }

        public Criteria andInviteUserBranchnameLike(String value) {
            addCriterion("invite_user_branchname like", value, "inviteUserBranchname");
            return (Criteria) this;
        }

        public Criteria andInviteUserBranchnameNotLike(String value) {
            addCriterion("invite_user_branchname not like", value, "inviteUserBranchname");
            return (Criteria) this;
        }

        public Criteria andInviteUserBranchnameIn(List<String> values) {
            addCriterion("invite_user_branchname in", values, "inviteUserBranchname");
            return (Criteria) this;
        }

        public Criteria andInviteUserBranchnameNotIn(List<String> values) {
            addCriterion("invite_user_branchname not in", values, "inviteUserBranchname");
            return (Criteria) this;
        }

        public Criteria andInviteUserBranchnameBetween(String value1, String value2) {
            addCriterion("invite_user_branchname between", value1, value2, "inviteUserBranchname");
            return (Criteria) this;
        }

        public Criteria andInviteUserBranchnameNotBetween(String value1, String value2) {
            addCriterion("invite_user_branchname not between", value1, value2, "inviteUserBranchname");
            return (Criteria) this;
        }

        public Criteria andInviteUserDepartmentnameIsNull() {
            addCriterion("invite_user_departmentname is null");
            return (Criteria) this;
        }

        public Criteria andInviteUserDepartmentnameIsNotNull() {
            addCriterion("invite_user_departmentname is not null");
            return (Criteria) this;
        }

        public Criteria andInviteUserDepartmentnameEqualTo(String value) {
            addCriterion("invite_user_departmentname =", value, "inviteUserDepartmentname");
            return (Criteria) this;
        }

        public Criteria andInviteUserDepartmentnameNotEqualTo(String value) {
            addCriterion("invite_user_departmentname <>", value, "inviteUserDepartmentname");
            return (Criteria) this;
        }

        public Criteria andInviteUserDepartmentnameGreaterThan(String value) {
            addCriterion("invite_user_departmentname >", value, "inviteUserDepartmentname");
            return (Criteria) this;
        }

        public Criteria andInviteUserDepartmentnameGreaterThanOrEqualTo(String value) {
            addCriterion("invite_user_departmentname >=", value, "inviteUserDepartmentname");
            return (Criteria) this;
        }

        public Criteria andInviteUserDepartmentnameLessThan(String value) {
            addCriterion("invite_user_departmentname <", value, "inviteUserDepartmentname");
            return (Criteria) this;
        }

        public Criteria andInviteUserDepartmentnameLessThanOrEqualTo(String value) {
            addCriterion("invite_user_departmentname <=", value, "inviteUserDepartmentname");
            return (Criteria) this;
        }

        public Criteria andInviteUserDepartmentnameLike(String value) {
            addCriterion("invite_user_departmentname like", value, "inviteUserDepartmentname");
            return (Criteria) this;
        }

        public Criteria andInviteUserDepartmentnameNotLike(String value) {
            addCriterion("invite_user_departmentname not like", value, "inviteUserDepartmentname");
            return (Criteria) this;
        }

        public Criteria andInviteUserDepartmentnameIn(List<String> values) {
            addCriterion("invite_user_departmentname in", values, "inviteUserDepartmentname");
            return (Criteria) this;
        }

        public Criteria andInviteUserDepartmentnameNotIn(List<String> values) {
            addCriterion("invite_user_departmentname not in", values, "inviteUserDepartmentname");
            return (Criteria) this;
        }

        public Criteria andInviteUserDepartmentnameBetween(String value1, String value2) {
            addCriterion("invite_user_departmentname between", value1, value2, "inviteUserDepartmentname");
            return (Criteria) this;
        }

        public Criteria andInviteUserDepartmentnameNotBetween(String value1, String value2) {
            addCriterion("invite_user_departmentname not between", value1, value2, "inviteUserDepartmentname");
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

        public Criteria andAlreadyInvestIsNull() {
            addCriterion("already_invest is null");
            return (Criteria) this;
        }

        public Criteria andAlreadyInvestIsNotNull() {
            addCriterion("already_invest is not null");
            return (Criteria) this;
        }

        public Criteria andAlreadyInvestEqualTo(BigDecimal value) {
            addCriterion("already_invest =", value, "alreadyInvest");
            return (Criteria) this;
        }

        public Criteria andAlreadyInvestNotEqualTo(BigDecimal value) {
            addCriterion("already_invest <>", value, "alreadyInvest");
            return (Criteria) this;
        }

        public Criteria andAlreadyInvestGreaterThan(BigDecimal value) {
            addCriterion("already_invest >", value, "alreadyInvest");
            return (Criteria) this;
        }

        public Criteria andAlreadyInvestGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("already_invest >=", value, "alreadyInvest");
            return (Criteria) this;
        }

        public Criteria andAlreadyInvestLessThan(BigDecimal value) {
            addCriterion("already_invest <", value, "alreadyInvest");
            return (Criteria) this;
        }

        public Criteria andAlreadyInvestLessThanOrEqualTo(BigDecimal value) {
            addCriterion("already_invest <=", value, "alreadyInvest");
            return (Criteria) this;
        }

        public Criteria andAlreadyInvestIn(List<BigDecimal> values) {
            addCriterion("already_invest in", values, "alreadyInvest");
            return (Criteria) this;
        }

        public Criteria andAlreadyInvestNotIn(List<BigDecimal> values) {
            addCriterion("already_invest not in", values, "alreadyInvest");
            return (Criteria) this;
        }

        public Criteria andAlreadyInvestBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("already_invest between", value1, value2, "alreadyInvest");
            return (Criteria) this;
        }

        public Criteria andAlreadyInvestNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("already_invest not between", value1, value2, "alreadyInvest");
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

        public Criteria andOrderStatusIsNull() {
            addCriterion("order_status is null");
            return (Criteria) this;
        }

        public Criteria andOrderStatusIsNotNull() {
            addCriterion("order_status is not null");
            return (Criteria) this;
        }

        public Criteria andOrderStatusEqualTo(Integer value) {
            addCriterion("order_status =", value, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusNotEqualTo(Integer value) {
            addCriterion("order_status <>", value, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusGreaterThan(Integer value) {
            addCriterion("order_status >", value, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("order_status >=", value, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusLessThan(Integer value) {
            addCriterion("order_status <", value, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusLessThanOrEqualTo(Integer value) {
            addCriterion("order_status <=", value, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusIn(List<Integer> values) {
            addCriterion("order_status in", values, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusNotIn(List<Integer> values) {
            addCriterion("order_status not in", values, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusBetween(Integer value1, Integer value2) {
            addCriterion("order_status between", value1, value2, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("order_status not between", value1, value2, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andCountInterestTimeIsNull() {
            addCriterion("count_interest_time is null");
            return (Criteria) this;
        }

        public Criteria andCountInterestTimeIsNotNull() {
            addCriterion("count_interest_time is not null");
            return (Criteria) this;
        }

        public Criteria andCountInterestTimeEqualTo(Integer value) {
            addCriterion("count_interest_time =", value, "countInterestTime");
            return (Criteria) this;
        }

        public Criteria andCountInterestTimeNotEqualTo(Integer value) {
            addCriterion("count_interest_time <>", value, "countInterestTime");
            return (Criteria) this;
        }

        public Criteria andCountInterestTimeGreaterThan(Integer value) {
            addCriterion("count_interest_time >", value, "countInterestTime");
            return (Criteria) this;
        }

        public Criteria andCountInterestTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("count_interest_time >=", value, "countInterestTime");
            return (Criteria) this;
        }

        public Criteria andCountInterestTimeLessThan(Integer value) {
            addCriterion("count_interest_time <", value, "countInterestTime");
            return (Criteria) this;
        }

        public Criteria andCountInterestTimeLessThanOrEqualTo(Integer value) {
            addCriterion("count_interest_time <=", value, "countInterestTime");
            return (Criteria) this;
        }

        public Criteria andCountInterestTimeIn(List<Integer> values) {
            addCriterion("count_interest_time in", values, "countInterestTime");
            return (Criteria) this;
        }

        public Criteria andCountInterestTimeNotIn(List<Integer> values) {
            addCriterion("count_interest_time not in", values, "countInterestTime");
            return (Criteria) this;
        }

        public Criteria andCountInterestTimeBetween(Integer value1, Integer value2) {
            addCriterion("count_interest_time between", value1, value2, "countInterestTime");
            return (Criteria) this;
        }

        public Criteria andCountInterestTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("count_interest_time not between", value1, value2, "countInterestTime");
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

        public Criteria andLockPeriodIsNull() {
            addCriterion("lock_period is null");
            return (Criteria) this;
        }

        public Criteria andLockPeriodIsNotNull() {
            addCriterion("lock_period is not null");
            return (Criteria) this;
        }

        public Criteria andLockPeriodEqualTo(Integer value) {
            addCriterion("lock_period =", value, "lockPeriod");
            return (Criteria) this;
        }

        public Criteria andLockPeriodNotEqualTo(Integer value) {
            addCriterion("lock_period <>", value, "lockPeriod");
            return (Criteria) this;
        }

        public Criteria andLockPeriodGreaterThan(Integer value) {
            addCriterion("lock_period >", value, "lockPeriod");
            return (Criteria) this;
        }

        public Criteria andLockPeriodGreaterThanOrEqualTo(Integer value) {
            addCriterion("lock_period >=", value, "lockPeriod");
            return (Criteria) this;
        }

        public Criteria andLockPeriodLessThan(Integer value) {
            addCriterion("lock_period <", value, "lockPeriod");
            return (Criteria) this;
        }

        public Criteria andLockPeriodLessThanOrEqualTo(Integer value) {
            addCriterion("lock_period <=", value, "lockPeriod");
            return (Criteria) this;
        }

        public Criteria andLockPeriodIn(List<Integer> values) {
            addCriterion("lock_period in", values, "lockPeriod");
            return (Criteria) this;
        }

        public Criteria andLockPeriodNotIn(List<Integer> values) {
            addCriterion("lock_period not in", values, "lockPeriod");
            return (Criteria) this;
        }

        public Criteria andLockPeriodBetween(Integer value1, Integer value2) {
            addCriterion("lock_period between", value1, value2, "lockPeriod");
            return (Criteria) this;
        }

        public Criteria andLockPeriodNotBetween(Integer value1, Integer value2) {
            addCriterion("lock_period not between", value1, value2, "lockPeriod");
            return (Criteria) this;
        }

        public Criteria andCommissionStatusIsNull() {
            addCriterion("commission_status is null");
            return (Criteria) this;
        }

        public Criteria andCommissionStatusIsNotNull() {
            addCriterion("commission_status is not null");
            return (Criteria) this;
        }

        public Criteria andCommissionStatusEqualTo(Integer value) {
            addCriterion("commission_status =", value, "commissionStatus");
            return (Criteria) this;
        }

        public Criteria andCommissionStatusNotEqualTo(Integer value) {
            addCriterion("commission_status <>", value, "commissionStatus");
            return (Criteria) this;
        }

        public Criteria andCommissionStatusGreaterThan(Integer value) {
            addCriterion("commission_status >", value, "commissionStatus");
            return (Criteria) this;
        }

        public Criteria andCommissionStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("commission_status >=", value, "commissionStatus");
            return (Criteria) this;
        }

        public Criteria andCommissionStatusLessThan(Integer value) {
            addCriterion("commission_status <", value, "commissionStatus");
            return (Criteria) this;
        }

        public Criteria andCommissionStatusLessThanOrEqualTo(Integer value) {
            addCriterion("commission_status <=", value, "commissionStatus");
            return (Criteria) this;
        }

        public Criteria andCommissionStatusIn(List<Integer> values) {
            addCriterion("commission_status in", values, "commissionStatus");
            return (Criteria) this;
        }

        public Criteria andCommissionStatusNotIn(List<Integer> values) {
            addCriterion("commission_status not in", values, "commissionStatus");
            return (Criteria) this;
        }

        public Criteria andCommissionStatusBetween(Integer value1, Integer value2) {
            addCriterion("commission_status between", value1, value2, "commissionStatus");
            return (Criteria) this;
        }

        public Criteria andCommissionStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("commission_status not between", value1, value2, "commissionStatus");
            return (Criteria) this;
        }

        public Criteria andCommissionCountTimeIsNull() {
            addCriterion("commission_count_time is null");
            return (Criteria) this;
        }

        public Criteria andCommissionCountTimeIsNotNull() {
            addCriterion("commission_count_time is not null");
            return (Criteria) this;
        }

        public Criteria andCommissionCountTimeEqualTo(Integer value) {
            addCriterion("commission_count_time =", value, "commissionCountTime");
            return (Criteria) this;
        }

        public Criteria andCommissionCountTimeNotEqualTo(Integer value) {
            addCriterion("commission_count_time <>", value, "commissionCountTime");
            return (Criteria) this;
        }

        public Criteria andCommissionCountTimeGreaterThan(Integer value) {
            addCriterion("commission_count_time >", value, "commissionCountTime");
            return (Criteria) this;
        }

        public Criteria andCommissionCountTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("commission_count_time >=", value, "commissionCountTime");
            return (Criteria) this;
        }

        public Criteria andCommissionCountTimeLessThan(Integer value) {
            addCriterion("commission_count_time <", value, "commissionCountTime");
            return (Criteria) this;
        }

        public Criteria andCommissionCountTimeLessThanOrEqualTo(Integer value) {
            addCriterion("commission_count_time <=", value, "commissionCountTime");
            return (Criteria) this;
        }

        public Criteria andCommissionCountTimeIn(List<Integer> values) {
            addCriterion("commission_count_time in", values, "commissionCountTime");
            return (Criteria) this;
        }

        public Criteria andCommissionCountTimeNotIn(List<Integer> values) {
            addCriterion("commission_count_time not in", values, "commissionCountTime");
            return (Criteria) this;
        }

        public Criteria andCommissionCountTimeBetween(Integer value1, Integer value2) {
            addCriterion("commission_count_time between", value1, value2, "commissionCountTime");
            return (Criteria) this;
        }

        public Criteria andCommissionCountTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("commission_count_time not between", value1, value2, "commissionCountTime");
            return (Criteria) this;
        }

        public Criteria andAvailableInvestAccountIsNull() {
            addCriterion("available_invest_account is null");
            return (Criteria) this;
        }

        public Criteria andAvailableInvestAccountIsNotNull() {
            addCriterion("available_invest_account is not null");
            return (Criteria) this;
        }

        public Criteria andAvailableInvestAccountEqualTo(BigDecimal value) {
            addCriterion("available_invest_account =", value, "availableInvestAccount");
            return (Criteria) this;
        }

        public Criteria andAvailableInvestAccountNotEqualTo(BigDecimal value) {
            addCriterion("available_invest_account <>", value, "availableInvestAccount");
            return (Criteria) this;
        }

        public Criteria andAvailableInvestAccountGreaterThan(BigDecimal value) {
            addCriterion("available_invest_account >", value, "availableInvestAccount");
            return (Criteria) this;
        }

        public Criteria andAvailableInvestAccountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("available_invest_account >=", value, "availableInvestAccount");
            return (Criteria) this;
        }

        public Criteria andAvailableInvestAccountLessThan(BigDecimal value) {
            addCriterion("available_invest_account <", value, "availableInvestAccount");
            return (Criteria) this;
        }

        public Criteria andAvailableInvestAccountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("available_invest_account <=", value, "availableInvestAccount");
            return (Criteria) this;
        }

        public Criteria andAvailableInvestAccountIn(List<BigDecimal> values) {
            addCriterion("available_invest_account in", values, "availableInvestAccount");
            return (Criteria) this;
        }

        public Criteria andAvailableInvestAccountNotIn(List<BigDecimal> values) {
            addCriterion("available_invest_account not in", values, "availableInvestAccount");
            return (Criteria) this;
        }

        public Criteria andAvailableInvestAccountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("available_invest_account between", value1, value2, "availableInvestAccount");
            return (Criteria) this;
        }

        public Criteria andAvailableInvestAccountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("available_invest_account not between", value1, value2, "availableInvestAccount");
            return (Criteria) this;
        }

        public Criteria andEndDateIsNull() {
            addCriterion("end_date is null");
            return (Criteria) this;
        }

        public Criteria andEndDateIsNotNull() {
            addCriterion("end_date is not null");
            return (Criteria) this;
        }

        public Criteria andEndDateEqualTo(Date value) {
            addCriterionForJDBCDate("end_date =", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("end_date <>", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateGreaterThan(Date value) {
            addCriterionForJDBCDate("end_date >", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("end_date >=", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateLessThan(Date value) {
            addCriterionForJDBCDate("end_date <", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("end_date <=", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateIn(List<Date> values) {
            addCriterionForJDBCDate("end_date in", values, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("end_date not in", values, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("end_date between", value1, value2, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("end_date not between", value1, value2, "endDate");
            return (Criteria) this;
        }

        public Criteria andCreditCompleteFlagIsNull() {
            addCriterion("credit_complete_flag is null");
            return (Criteria) this;
        }

        public Criteria andCreditCompleteFlagIsNotNull() {
            addCriterion("credit_complete_flag is not null");
            return (Criteria) this;
        }

        public Criteria andCreditCompleteFlagEqualTo(Integer value) {
            addCriterion("credit_complete_flag =", value, "creditCompleteFlag");
            return (Criteria) this;
        }

        public Criteria andCreditCompleteFlagNotEqualTo(Integer value) {
            addCriterion("credit_complete_flag <>", value, "creditCompleteFlag");
            return (Criteria) this;
        }

        public Criteria andCreditCompleteFlagGreaterThan(Integer value) {
            addCriterion("credit_complete_flag >", value, "creditCompleteFlag");
            return (Criteria) this;
        }

        public Criteria andCreditCompleteFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("credit_complete_flag >=", value, "creditCompleteFlag");
            return (Criteria) this;
        }

        public Criteria andCreditCompleteFlagLessThan(Integer value) {
            addCriterion("credit_complete_flag <", value, "creditCompleteFlag");
            return (Criteria) this;
        }

        public Criteria andCreditCompleteFlagLessThanOrEqualTo(Integer value) {
            addCriterion("credit_complete_flag <=", value, "creditCompleteFlag");
            return (Criteria) this;
        }

        public Criteria andCreditCompleteFlagIn(List<Integer> values) {
            addCriterion("credit_complete_flag in", values, "creditCompleteFlag");
            return (Criteria) this;
        }

        public Criteria andCreditCompleteFlagNotIn(List<Integer> values) {
            addCriterion("credit_complete_flag not in", values, "creditCompleteFlag");
            return (Criteria) this;
        }

        public Criteria andCreditCompleteFlagBetween(Integer value1, Integer value2) {
            addCriterion("credit_complete_flag between", value1, value2, "creditCompleteFlag");
            return (Criteria) this;
        }

        public Criteria andCreditCompleteFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("credit_complete_flag not between", value1, value2, "creditCompleteFlag");
            return (Criteria) this;
        }

        public Criteria andFrostAccountIsNull() {
            addCriterion("frost_account is null");
            return (Criteria) this;
        }

        public Criteria andFrostAccountIsNotNull() {
            addCriterion("frost_account is not null");
            return (Criteria) this;
        }

        public Criteria andFrostAccountEqualTo(BigDecimal value) {
            addCriterion("frost_account =", value, "frostAccount");
            return (Criteria) this;
        }

        public Criteria andFrostAccountNotEqualTo(BigDecimal value) {
            addCriterion("frost_account <>", value, "frostAccount");
            return (Criteria) this;
        }

        public Criteria andFrostAccountGreaterThan(BigDecimal value) {
            addCriterion("frost_account >", value, "frostAccount");
            return (Criteria) this;
        }

        public Criteria andFrostAccountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("frost_account >=", value, "frostAccount");
            return (Criteria) this;
        }

        public Criteria andFrostAccountLessThan(BigDecimal value) {
            addCriterion("frost_account <", value, "frostAccount");
            return (Criteria) this;
        }

        public Criteria andFrostAccountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("frost_account <=", value, "frostAccount");
            return (Criteria) this;
        }

        public Criteria andFrostAccountIn(List<BigDecimal> values) {
            addCriterion("frost_account in", values, "frostAccount");
            return (Criteria) this;
        }

        public Criteria andFrostAccountNotIn(List<BigDecimal> values) {
            addCriterion("frost_account not in", values, "frostAccount");
            return (Criteria) this;
        }

        public Criteria andFrostAccountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("frost_account between", value1, value2, "frostAccount");
            return (Criteria) this;
        }

        public Criteria andFrostAccountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("frost_account not between", value1, value2, "frostAccount");
            return (Criteria) this;
        }

        public Criteria andWaitTotalIsNull() {
            addCriterion("wait_total is null");
            return (Criteria) this;
        }

        public Criteria andWaitTotalIsNotNull() {
            addCriterion("wait_total is not null");
            return (Criteria) this;
        }

        public Criteria andWaitTotalEqualTo(BigDecimal value) {
            addCriterion("wait_total =", value, "waitTotal");
            return (Criteria) this;
        }

        public Criteria andWaitTotalNotEqualTo(BigDecimal value) {
            addCriterion("wait_total <>", value, "waitTotal");
            return (Criteria) this;
        }

        public Criteria andWaitTotalGreaterThan(BigDecimal value) {
            addCriterion("wait_total >", value, "waitTotal");
            return (Criteria) this;
        }

        public Criteria andWaitTotalGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("wait_total >=", value, "waitTotal");
            return (Criteria) this;
        }

        public Criteria andWaitTotalLessThan(BigDecimal value) {
            addCriterion("wait_total <", value, "waitTotal");
            return (Criteria) this;
        }

        public Criteria andWaitTotalLessThanOrEqualTo(BigDecimal value) {
            addCriterion("wait_total <=", value, "waitTotal");
            return (Criteria) this;
        }

        public Criteria andWaitTotalIn(List<BigDecimal> values) {
            addCriterion("wait_total in", values, "waitTotal");
            return (Criteria) this;
        }

        public Criteria andWaitTotalNotIn(List<BigDecimal> values) {
            addCriterion("wait_total not in", values, "waitTotal");
            return (Criteria) this;
        }

        public Criteria andWaitTotalBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("wait_total between", value1, value2, "waitTotal");
            return (Criteria) this;
        }

        public Criteria andWaitTotalNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("wait_total not between", value1, value2, "waitTotal");
            return (Criteria) this;
        }

        public Criteria andWaitCapticalIsNull() {
            addCriterion("wait_captical is null");
            return (Criteria) this;
        }

        public Criteria andWaitCapticalIsNotNull() {
            addCriterion("wait_captical is not null");
            return (Criteria) this;
        }

        public Criteria andWaitCapticalEqualTo(BigDecimal value) {
            addCriterion("wait_captical =", value, "waitCaptical");
            return (Criteria) this;
        }

        public Criteria andWaitCapticalNotEqualTo(BigDecimal value) {
            addCriterion("wait_captical <>", value, "waitCaptical");
            return (Criteria) this;
        }

        public Criteria andWaitCapticalGreaterThan(BigDecimal value) {
            addCriterion("wait_captical >", value, "waitCaptical");
            return (Criteria) this;
        }

        public Criteria andWaitCapticalGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("wait_captical >=", value, "waitCaptical");
            return (Criteria) this;
        }

        public Criteria andWaitCapticalLessThan(BigDecimal value) {
            addCriterion("wait_captical <", value, "waitCaptical");
            return (Criteria) this;
        }

        public Criteria andWaitCapticalLessThanOrEqualTo(BigDecimal value) {
            addCriterion("wait_captical <=", value, "waitCaptical");
            return (Criteria) this;
        }

        public Criteria andWaitCapticalIn(List<BigDecimal> values) {
            addCriterion("wait_captical in", values, "waitCaptical");
            return (Criteria) this;
        }

        public Criteria andWaitCapticalNotIn(List<BigDecimal> values) {
            addCriterion("wait_captical not in", values, "waitCaptical");
            return (Criteria) this;
        }

        public Criteria andWaitCapticalBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("wait_captical between", value1, value2, "waitCaptical");
            return (Criteria) this;
        }

        public Criteria andWaitCapticalNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("wait_captical not between", value1, value2, "waitCaptical");
            return (Criteria) this;
        }

        public Criteria andWaitInterestIsNull() {
            addCriterion("wait_interest is null");
            return (Criteria) this;
        }

        public Criteria andWaitInterestIsNotNull() {
            addCriterion("wait_interest is not null");
            return (Criteria) this;
        }

        public Criteria andWaitInterestEqualTo(BigDecimal value) {
            addCriterion("wait_interest =", value, "waitInterest");
            return (Criteria) this;
        }

        public Criteria andWaitInterestNotEqualTo(BigDecimal value) {
            addCriterion("wait_interest <>", value, "waitInterest");
            return (Criteria) this;
        }

        public Criteria andWaitInterestGreaterThan(BigDecimal value) {
            addCriterion("wait_interest >", value, "waitInterest");
            return (Criteria) this;
        }

        public Criteria andWaitInterestGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("wait_interest >=", value, "waitInterest");
            return (Criteria) this;
        }

        public Criteria andWaitInterestLessThan(BigDecimal value) {
            addCriterion("wait_interest <", value, "waitInterest");
            return (Criteria) this;
        }

        public Criteria andWaitInterestLessThanOrEqualTo(BigDecimal value) {
            addCriterion("wait_interest <=", value, "waitInterest");
            return (Criteria) this;
        }

        public Criteria andWaitInterestIn(List<BigDecimal> values) {
            addCriterion("wait_interest in", values, "waitInterest");
            return (Criteria) this;
        }

        public Criteria andWaitInterestNotIn(List<BigDecimal> values) {
            addCriterion("wait_interest not in", values, "waitInterest");
            return (Criteria) this;
        }

        public Criteria andWaitInterestBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("wait_interest between", value1, value2, "waitInterest");
            return (Criteria) this;
        }

        public Criteria andWaitInterestNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("wait_interest not between", value1, value2, "waitInterest");
            return (Criteria) this;
        }

        public Criteria andReceivedTotalIsNull() {
            addCriterion("received_total is null");
            return (Criteria) this;
        }

        public Criteria andReceivedTotalIsNotNull() {
            addCriterion("received_total is not null");
            return (Criteria) this;
        }

        public Criteria andReceivedTotalEqualTo(BigDecimal value) {
            addCriterion("received_total =", value, "receivedTotal");
            return (Criteria) this;
        }

        public Criteria andReceivedTotalNotEqualTo(BigDecimal value) {
            addCriterion("received_total <>", value, "receivedTotal");
            return (Criteria) this;
        }

        public Criteria andReceivedTotalGreaterThan(BigDecimal value) {
            addCriterion("received_total >", value, "receivedTotal");
            return (Criteria) this;
        }

        public Criteria andReceivedTotalGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("received_total >=", value, "receivedTotal");
            return (Criteria) this;
        }

        public Criteria andReceivedTotalLessThan(BigDecimal value) {
            addCriterion("received_total <", value, "receivedTotal");
            return (Criteria) this;
        }

        public Criteria andReceivedTotalLessThanOrEqualTo(BigDecimal value) {
            addCriterion("received_total <=", value, "receivedTotal");
            return (Criteria) this;
        }

        public Criteria andReceivedTotalIn(List<BigDecimal> values) {
            addCriterion("received_total in", values, "receivedTotal");
            return (Criteria) this;
        }

        public Criteria andReceivedTotalNotIn(List<BigDecimal> values) {
            addCriterion("received_total not in", values, "receivedTotal");
            return (Criteria) this;
        }

        public Criteria andReceivedTotalBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("received_total between", value1, value2, "receivedTotal");
            return (Criteria) this;
        }

        public Criteria andReceivedTotalNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("received_total not between", value1, value2, "receivedTotal");
            return (Criteria) this;
        }

        public Criteria andReceivedInterestIsNull() {
            addCriterion("received_interest is null");
            return (Criteria) this;
        }

        public Criteria andReceivedInterestIsNotNull() {
            addCriterion("received_interest is not null");
            return (Criteria) this;
        }

        public Criteria andReceivedInterestEqualTo(BigDecimal value) {
            addCriterion("received_interest =", value, "receivedInterest");
            return (Criteria) this;
        }

        public Criteria andReceivedInterestNotEqualTo(BigDecimal value) {
            addCriterion("received_interest <>", value, "receivedInterest");
            return (Criteria) this;
        }

        public Criteria andReceivedInterestGreaterThan(BigDecimal value) {
            addCriterion("received_interest >", value, "receivedInterest");
            return (Criteria) this;
        }

        public Criteria andReceivedInterestGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("received_interest >=", value, "receivedInterest");
            return (Criteria) this;
        }

        public Criteria andReceivedInterestLessThan(BigDecimal value) {
            addCriterion("received_interest <", value, "receivedInterest");
            return (Criteria) this;
        }

        public Criteria andReceivedInterestLessThanOrEqualTo(BigDecimal value) {
            addCriterion("received_interest <=", value, "receivedInterest");
            return (Criteria) this;
        }

        public Criteria andReceivedInterestIn(List<BigDecimal> values) {
            addCriterion("received_interest in", values, "receivedInterest");
            return (Criteria) this;
        }

        public Criteria andReceivedInterestNotIn(List<BigDecimal> values) {
            addCriterion("received_interest not in", values, "receivedInterest");
            return (Criteria) this;
        }

        public Criteria andReceivedInterestBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("received_interest between", value1, value2, "receivedInterest");
            return (Criteria) this;
        }

        public Criteria andReceivedInterestNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("received_interest not between", value1, value2, "receivedInterest");
            return (Criteria) this;
        }

        public Criteria andReceivedCapitalIsNull() {
            addCriterion("received_capital is null");
            return (Criteria) this;
        }

        public Criteria andReceivedCapitalIsNotNull() {
            addCriterion("received_capital is not null");
            return (Criteria) this;
        }

        public Criteria andReceivedCapitalEqualTo(BigDecimal value) {
            addCriterion("received_capital =", value, "receivedCapital");
            return (Criteria) this;
        }

        public Criteria andReceivedCapitalNotEqualTo(BigDecimal value) {
            addCriterion("received_capital <>", value, "receivedCapital");
            return (Criteria) this;
        }

        public Criteria andReceivedCapitalGreaterThan(BigDecimal value) {
            addCriterion("received_capital >", value, "receivedCapital");
            return (Criteria) this;
        }

        public Criteria andReceivedCapitalGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("received_capital >=", value, "receivedCapital");
            return (Criteria) this;
        }

        public Criteria andReceivedCapitalLessThan(BigDecimal value) {
            addCriterion("received_capital <", value, "receivedCapital");
            return (Criteria) this;
        }

        public Criteria andReceivedCapitalLessThanOrEqualTo(BigDecimal value) {
            addCriterion("received_capital <=", value, "receivedCapital");
            return (Criteria) this;
        }

        public Criteria andReceivedCapitalIn(List<BigDecimal> values) {
            addCriterion("received_capital in", values, "receivedCapital");
            return (Criteria) this;
        }

        public Criteria andReceivedCapitalNotIn(List<BigDecimal> values) {
            addCriterion("received_capital not in", values, "receivedCapital");
            return (Criteria) this;
        }

        public Criteria andReceivedCapitalBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("received_capital between", value1, value2, "receivedCapital");
            return (Criteria) this;
        }

        public Criteria andReceivedCapitalNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("received_capital not between", value1, value2, "receivedCapital");
            return (Criteria) this;
        }

        public Criteria andQuitTimeIsNull() {
            addCriterion("quit_time is null");
            return (Criteria) this;
        }

        public Criteria andQuitTimeIsNotNull() {
            addCriterion("quit_time is not null");
            return (Criteria) this;
        }

        public Criteria andQuitTimeEqualTo(Integer value) {
            addCriterion("quit_time =", value, "quitTime");
            return (Criteria) this;
        }

        public Criteria andQuitTimeNotEqualTo(Integer value) {
            addCriterion("quit_time <>", value, "quitTime");
            return (Criteria) this;
        }

        public Criteria andQuitTimeGreaterThan(Integer value) {
            addCriterion("quit_time >", value, "quitTime");
            return (Criteria) this;
        }

        public Criteria andQuitTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("quit_time >=", value, "quitTime");
            return (Criteria) this;
        }

        public Criteria andQuitTimeLessThan(Integer value) {
            addCriterion("quit_time <", value, "quitTime");
            return (Criteria) this;
        }

        public Criteria andQuitTimeLessThanOrEqualTo(Integer value) {
            addCriterion("quit_time <=", value, "quitTime");
            return (Criteria) this;
        }

        public Criteria andQuitTimeIn(List<Integer> values) {
            addCriterion("quit_time in", values, "quitTime");
            return (Criteria) this;
        }

        public Criteria andQuitTimeNotIn(List<Integer> values) {
            addCriterion("quit_time not in", values, "quitTime");
            return (Criteria) this;
        }

        public Criteria andQuitTimeBetween(Integer value1, Integer value2) {
            addCriterion("quit_time between", value1, value2, "quitTime");
            return (Criteria) this;
        }

        public Criteria andQuitTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("quit_time not between", value1, value2, "quitTime");
            return (Criteria) this;
        }

        public Criteria andLastPaymentTimeIsNull() {
            addCriterion("last_payment_time is null");
            return (Criteria) this;
        }

        public Criteria andLastPaymentTimeIsNotNull() {
            addCriterion("last_payment_time is not null");
            return (Criteria) this;
        }

        public Criteria andLastPaymentTimeEqualTo(Integer value) {
            addCriterion("last_payment_time =", value, "lastPaymentTime");
            return (Criteria) this;
        }

        public Criteria andLastPaymentTimeNotEqualTo(Integer value) {
            addCriterion("last_payment_time <>", value, "lastPaymentTime");
            return (Criteria) this;
        }

        public Criteria andLastPaymentTimeGreaterThan(Integer value) {
            addCriterion("last_payment_time >", value, "lastPaymentTime");
            return (Criteria) this;
        }

        public Criteria andLastPaymentTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("last_payment_time >=", value, "lastPaymentTime");
            return (Criteria) this;
        }

        public Criteria andLastPaymentTimeLessThan(Integer value) {
            addCriterion("last_payment_time <", value, "lastPaymentTime");
            return (Criteria) this;
        }

        public Criteria andLastPaymentTimeLessThanOrEqualTo(Integer value) {
            addCriterion("last_payment_time <=", value, "lastPaymentTime");
            return (Criteria) this;
        }

        public Criteria andLastPaymentTimeIn(List<Integer> values) {
            addCriterion("last_payment_time in", values, "lastPaymentTime");
            return (Criteria) this;
        }

        public Criteria andLastPaymentTimeNotIn(List<Integer> values) {
            addCriterion("last_payment_time not in", values, "lastPaymentTime");
            return (Criteria) this;
        }

        public Criteria andLastPaymentTimeBetween(Integer value1, Integer value2) {
            addCriterion("last_payment_time between", value1, value2, "lastPaymentTime");
            return (Criteria) this;
        }

        public Criteria andLastPaymentTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("last_payment_time not between", value1, value2, "lastPaymentTime");
            return (Criteria) this;
        }

        public Criteria andAcctualPaymentTimeIsNull() {
            addCriterion("acctual_payment_time is null");
            return (Criteria) this;
        }

        public Criteria andAcctualPaymentTimeIsNotNull() {
            addCriterion("acctual_payment_time is not null");
            return (Criteria) this;
        }

        public Criteria andAcctualPaymentTimeEqualTo(Integer value) {
            addCriterion("acctual_payment_time =", value, "acctualPaymentTime");
            return (Criteria) this;
        }

        public Criteria andAcctualPaymentTimeNotEqualTo(Integer value) {
            addCriterion("acctual_payment_time <>", value, "acctualPaymentTime");
            return (Criteria) this;
        }

        public Criteria andAcctualPaymentTimeGreaterThan(Integer value) {
            addCriterion("acctual_payment_time >", value, "acctualPaymentTime");
            return (Criteria) this;
        }

        public Criteria andAcctualPaymentTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("acctual_payment_time >=", value, "acctualPaymentTime");
            return (Criteria) this;
        }

        public Criteria andAcctualPaymentTimeLessThan(Integer value) {
            addCriterion("acctual_payment_time <", value, "acctualPaymentTime");
            return (Criteria) this;
        }

        public Criteria andAcctualPaymentTimeLessThanOrEqualTo(Integer value) {
            addCriterion("acctual_payment_time <=", value, "acctualPaymentTime");
            return (Criteria) this;
        }

        public Criteria andAcctualPaymentTimeIn(List<Integer> values) {
            addCriterion("acctual_payment_time in", values, "acctualPaymentTime");
            return (Criteria) this;
        }

        public Criteria andAcctualPaymentTimeNotIn(List<Integer> values) {
            addCriterion("acctual_payment_time not in", values, "acctualPaymentTime");
            return (Criteria) this;
        }

        public Criteria andAcctualPaymentTimeBetween(Integer value1, Integer value2) {
            addCriterion("acctual_payment_time between", value1, value2, "acctualPaymentTime");
            return (Criteria) this;
        }

        public Criteria andAcctualPaymentTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("acctual_payment_time not between", value1, value2, "acctualPaymentTime");
            return (Criteria) this;
        }

        public Criteria andShouldPayTotalIsNull() {
            addCriterion("should_pay_total is null");
            return (Criteria) this;
        }

        public Criteria andShouldPayTotalIsNotNull() {
            addCriterion("should_pay_total is not null");
            return (Criteria) this;
        }

        public Criteria andShouldPayTotalEqualTo(BigDecimal value) {
            addCriterion("should_pay_total =", value, "shouldPayTotal");
            return (Criteria) this;
        }

        public Criteria andShouldPayTotalNotEqualTo(BigDecimal value) {
            addCriterion("should_pay_total <>", value, "shouldPayTotal");
            return (Criteria) this;
        }

        public Criteria andShouldPayTotalGreaterThan(BigDecimal value) {
            addCriterion("should_pay_total >", value, "shouldPayTotal");
            return (Criteria) this;
        }

        public Criteria andShouldPayTotalGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("should_pay_total >=", value, "shouldPayTotal");
            return (Criteria) this;
        }

        public Criteria andShouldPayTotalLessThan(BigDecimal value) {
            addCriterion("should_pay_total <", value, "shouldPayTotal");
            return (Criteria) this;
        }

        public Criteria andShouldPayTotalLessThanOrEqualTo(BigDecimal value) {
            addCriterion("should_pay_total <=", value, "shouldPayTotal");
            return (Criteria) this;
        }

        public Criteria andShouldPayTotalIn(List<BigDecimal> values) {
            addCriterion("should_pay_total in", values, "shouldPayTotal");
            return (Criteria) this;
        }

        public Criteria andShouldPayTotalNotIn(List<BigDecimal> values) {
            addCriterion("should_pay_total not in", values, "shouldPayTotal");
            return (Criteria) this;
        }

        public Criteria andShouldPayTotalBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("should_pay_total between", value1, value2, "shouldPayTotal");
            return (Criteria) this;
        }

        public Criteria andShouldPayTotalNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("should_pay_total not between", value1, value2, "shouldPayTotal");
            return (Criteria) this;
        }

        public Criteria andShouldPayCapitalIsNull() {
            addCriterion("should_pay_capital is null");
            return (Criteria) this;
        }

        public Criteria andShouldPayCapitalIsNotNull() {
            addCriterion("should_pay_capital is not null");
            return (Criteria) this;
        }

        public Criteria andShouldPayCapitalEqualTo(BigDecimal value) {
            addCriterion("should_pay_capital =", value, "shouldPayCapital");
            return (Criteria) this;
        }

        public Criteria andShouldPayCapitalNotEqualTo(BigDecimal value) {
            addCriterion("should_pay_capital <>", value, "shouldPayCapital");
            return (Criteria) this;
        }

        public Criteria andShouldPayCapitalGreaterThan(BigDecimal value) {
            addCriterion("should_pay_capital >", value, "shouldPayCapital");
            return (Criteria) this;
        }

        public Criteria andShouldPayCapitalGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("should_pay_capital >=", value, "shouldPayCapital");
            return (Criteria) this;
        }

        public Criteria andShouldPayCapitalLessThan(BigDecimal value) {
            addCriterion("should_pay_capital <", value, "shouldPayCapital");
            return (Criteria) this;
        }

        public Criteria andShouldPayCapitalLessThanOrEqualTo(BigDecimal value) {
            addCriterion("should_pay_capital <=", value, "shouldPayCapital");
            return (Criteria) this;
        }

        public Criteria andShouldPayCapitalIn(List<BigDecimal> values) {
            addCriterion("should_pay_capital in", values, "shouldPayCapital");
            return (Criteria) this;
        }

        public Criteria andShouldPayCapitalNotIn(List<BigDecimal> values) {
            addCriterion("should_pay_capital not in", values, "shouldPayCapital");
            return (Criteria) this;
        }

        public Criteria andShouldPayCapitalBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("should_pay_capital between", value1, value2, "shouldPayCapital");
            return (Criteria) this;
        }

        public Criteria andShouldPayCapitalNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("should_pay_capital not between", value1, value2, "shouldPayCapital");
            return (Criteria) this;
        }

        public Criteria andShouldPayInterestIsNull() {
            addCriterion("should_pay_interest is null");
            return (Criteria) this;
        }

        public Criteria andShouldPayInterestIsNotNull() {
            addCriterion("should_pay_interest is not null");
            return (Criteria) this;
        }

        public Criteria andShouldPayInterestEqualTo(BigDecimal value) {
            addCriterion("should_pay_interest =", value, "shouldPayInterest");
            return (Criteria) this;
        }

        public Criteria andShouldPayInterestNotEqualTo(BigDecimal value) {
            addCriterion("should_pay_interest <>", value, "shouldPayInterest");
            return (Criteria) this;
        }

        public Criteria andShouldPayInterestGreaterThan(BigDecimal value) {
            addCriterion("should_pay_interest >", value, "shouldPayInterest");
            return (Criteria) this;
        }

        public Criteria andShouldPayInterestGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("should_pay_interest >=", value, "shouldPayInterest");
            return (Criteria) this;
        }

        public Criteria andShouldPayInterestLessThan(BigDecimal value) {
            addCriterion("should_pay_interest <", value, "shouldPayInterest");
            return (Criteria) this;
        }

        public Criteria andShouldPayInterestLessThanOrEqualTo(BigDecimal value) {
            addCriterion("should_pay_interest <=", value, "shouldPayInterest");
            return (Criteria) this;
        }

        public Criteria andShouldPayInterestIn(List<BigDecimal> values) {
            addCriterion("should_pay_interest in", values, "shouldPayInterest");
            return (Criteria) this;
        }

        public Criteria andShouldPayInterestNotIn(List<BigDecimal> values) {
            addCriterion("should_pay_interest not in", values, "shouldPayInterest");
            return (Criteria) this;
        }

        public Criteria andShouldPayInterestBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("should_pay_interest between", value1, value2, "shouldPayInterest");
            return (Criteria) this;
        }

        public Criteria andShouldPayInterestNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("should_pay_interest not between", value1, value2, "shouldPayInterest");
            return (Criteria) this;
        }

        public Criteria andExpectAprIsNull() {
            addCriterion("expect_apr is null");
            return (Criteria) this;
        }

        public Criteria andExpectAprIsNotNull() {
            addCriterion("expect_apr is not null");
            return (Criteria) this;
        }

        public Criteria andExpectAprEqualTo(BigDecimal value) {
            addCriterion("expect_apr =", value, "expectApr");
            return (Criteria) this;
        }

        public Criteria andExpectAprNotEqualTo(BigDecimal value) {
            addCriterion("expect_apr <>", value, "expectApr");
            return (Criteria) this;
        }

        public Criteria andExpectAprGreaterThan(BigDecimal value) {
            addCriterion("expect_apr >", value, "expectApr");
            return (Criteria) this;
        }

        public Criteria andExpectAprGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("expect_apr >=", value, "expectApr");
            return (Criteria) this;
        }

        public Criteria andExpectAprLessThan(BigDecimal value) {
            addCriterion("expect_apr <", value, "expectApr");
            return (Criteria) this;
        }

        public Criteria andExpectAprLessThanOrEqualTo(BigDecimal value) {
            addCriterion("expect_apr <=", value, "expectApr");
            return (Criteria) this;
        }

        public Criteria andExpectAprIn(List<BigDecimal> values) {
            addCriterion("expect_apr in", values, "expectApr");
            return (Criteria) this;
        }

        public Criteria andExpectAprNotIn(List<BigDecimal> values) {
            addCriterion("expect_apr not in", values, "expectApr");
            return (Criteria) this;
        }

        public Criteria andExpectAprBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("expect_apr between", value1, value2, "expectApr");
            return (Criteria) this;
        }

        public Criteria andExpectAprNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("expect_apr not between", value1, value2, "expectApr");
            return (Criteria) this;
        }

        public Criteria andFairValueIsNull() {
            addCriterion("fair_value is null");
            return (Criteria) this;
        }

        public Criteria andFairValueIsNotNull() {
            addCriterion("fair_value is not null");
            return (Criteria) this;
        }

        public Criteria andFairValueEqualTo(BigDecimal value) {
            addCriterion("fair_value =", value, "fairValue");
            return (Criteria) this;
        }

        public Criteria andFairValueNotEqualTo(BigDecimal value) {
            addCriterion("fair_value <>", value, "fairValue");
            return (Criteria) this;
        }

        public Criteria andFairValueGreaterThan(BigDecimal value) {
            addCriterion("fair_value >", value, "fairValue");
            return (Criteria) this;
        }

        public Criteria andFairValueGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("fair_value >=", value, "fairValue");
            return (Criteria) this;
        }

        public Criteria andFairValueLessThan(BigDecimal value) {
            addCriterion("fair_value <", value, "fairValue");
            return (Criteria) this;
        }

        public Criteria andFairValueLessThanOrEqualTo(BigDecimal value) {
            addCriterion("fair_value <=", value, "fairValue");
            return (Criteria) this;
        }

        public Criteria andFairValueIn(List<BigDecimal> values) {
            addCriterion("fair_value in", values, "fairValue");
            return (Criteria) this;
        }

        public Criteria andFairValueNotIn(List<BigDecimal> values) {
            addCriterion("fair_value not in", values, "fairValue");
            return (Criteria) this;
        }

        public Criteria andFairValueBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("fair_value between", value1, value2, "fairValue");
            return (Criteria) this;
        }

        public Criteria andFairValueNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("fair_value not between", value1, value2, "fairValue");
            return (Criteria) this;
        }

        public Criteria andLiquidationFairValueIsNull() {
            addCriterion("liquidation_fair_value is null");
            return (Criteria) this;
        }

        public Criteria andLiquidationFairValueIsNotNull() {
            addCriterion("liquidation_fair_value is not null");
            return (Criteria) this;
        }

        public Criteria andLiquidationFairValueEqualTo(BigDecimal value) {
            addCriterion("liquidation_fair_value =", value, "liquidationFairValue");
            return (Criteria) this;
        }

        public Criteria andLiquidationFairValueNotEqualTo(BigDecimal value) {
            addCriterion("liquidation_fair_value <>", value, "liquidationFairValue");
            return (Criteria) this;
        }

        public Criteria andLiquidationFairValueGreaterThan(BigDecimal value) {
            addCriterion("liquidation_fair_value >", value, "liquidationFairValue");
            return (Criteria) this;
        }

        public Criteria andLiquidationFairValueGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("liquidation_fair_value >=", value, "liquidationFairValue");
            return (Criteria) this;
        }

        public Criteria andLiquidationFairValueLessThan(BigDecimal value) {
            addCriterion("liquidation_fair_value <", value, "liquidationFairValue");
            return (Criteria) this;
        }

        public Criteria andLiquidationFairValueLessThanOrEqualTo(BigDecimal value) {
            addCriterion("liquidation_fair_value <=", value, "liquidationFairValue");
            return (Criteria) this;
        }

        public Criteria andLiquidationFairValueIn(List<BigDecimal> values) {
            addCriterion("liquidation_fair_value in", values, "liquidationFairValue");
            return (Criteria) this;
        }

        public Criteria andLiquidationFairValueNotIn(List<BigDecimal> values) {
            addCriterion("liquidation_fair_value not in", values, "liquidationFairValue");
            return (Criteria) this;
        }

        public Criteria andLiquidationFairValueBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("liquidation_fair_value between", value1, value2, "liquidationFairValue");
            return (Criteria) this;
        }

        public Criteria andLiquidationFairValueNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("liquidation_fair_value not between", value1, value2, "liquidationFairValue");
            return (Criteria) this;
        }

        public Criteria andActualAprIsNull() {
            addCriterion("actual_apr is null");
            return (Criteria) this;
        }

        public Criteria andActualAprIsNotNull() {
            addCriterion("actual_apr is not null");
            return (Criteria) this;
        }

        public Criteria andActualAprEqualTo(BigDecimal value) {
            addCriterion("actual_apr =", value, "actualApr");
            return (Criteria) this;
        }

        public Criteria andActualAprNotEqualTo(BigDecimal value) {
            addCriterion("actual_apr <>", value, "actualApr");
            return (Criteria) this;
        }

        public Criteria andActualAprGreaterThan(BigDecimal value) {
            addCriterion("actual_apr >", value, "actualApr");
            return (Criteria) this;
        }

        public Criteria andActualAprGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("actual_apr >=", value, "actualApr");
            return (Criteria) this;
        }

        public Criteria andActualAprLessThan(BigDecimal value) {
            addCriterion("actual_apr <", value, "actualApr");
            return (Criteria) this;
        }

        public Criteria andActualAprLessThanOrEqualTo(BigDecimal value) {
            addCriterion("actual_apr <=", value, "actualApr");
            return (Criteria) this;
        }

        public Criteria andActualAprIn(List<BigDecimal> values) {
            addCriterion("actual_apr in", values, "actualApr");
            return (Criteria) this;
        }

        public Criteria andActualAprNotIn(List<BigDecimal> values) {
            addCriterion("actual_apr not in", values, "actualApr");
            return (Criteria) this;
        }

        public Criteria andActualAprBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("actual_apr between", value1, value2, "actualApr");
            return (Criteria) this;
        }

        public Criteria andActualAprNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("actual_apr not between", value1, value2, "actualApr");
            return (Criteria) this;
        }

        public Criteria andInvestCountsIsNull() {
            addCriterion("invest_counts is null");
            return (Criteria) this;
        }

        public Criteria andInvestCountsIsNotNull() {
            addCriterion("invest_counts is not null");
            return (Criteria) this;
        }

        public Criteria andInvestCountsEqualTo(Integer value) {
            addCriterion("invest_counts =", value, "investCounts");
            return (Criteria) this;
        }

        public Criteria andInvestCountsNotEqualTo(Integer value) {
            addCriterion("invest_counts <>", value, "investCounts");
            return (Criteria) this;
        }

        public Criteria andInvestCountsGreaterThan(Integer value) {
            addCriterion("invest_counts >", value, "investCounts");
            return (Criteria) this;
        }

        public Criteria andInvestCountsGreaterThanOrEqualTo(Integer value) {
            addCriterion("invest_counts >=", value, "investCounts");
            return (Criteria) this;
        }

        public Criteria andInvestCountsLessThan(Integer value) {
            addCriterion("invest_counts <", value, "investCounts");
            return (Criteria) this;
        }

        public Criteria andInvestCountsLessThanOrEqualTo(Integer value) {
            addCriterion("invest_counts <=", value, "investCounts");
            return (Criteria) this;
        }

        public Criteria andInvestCountsIn(List<Integer> values) {
            addCriterion("invest_counts in", values, "investCounts");
            return (Criteria) this;
        }

        public Criteria andInvestCountsNotIn(List<Integer> values) {
            addCriterion("invest_counts not in", values, "investCounts");
            return (Criteria) this;
        }

        public Criteria andInvestCountsBetween(Integer value1, Integer value2) {
            addCriterion("invest_counts between", value1, value2, "investCounts");
            return (Criteria) this;
        }

        public Criteria andInvestCountsNotBetween(Integer value1, Integer value2) {
            addCriterion("invest_counts not between", value1, value2, "investCounts");
            return (Criteria) this;
        }

        public Criteria andMatchDatesIsNull() {
            addCriterion("match_dates is null");
            return (Criteria) this;
        }

        public Criteria andMatchDatesIsNotNull() {
            addCriterion("match_dates is not null");
            return (Criteria) this;
        }

        public Criteria andMatchDatesEqualTo(Integer value) {
            addCriterion("match_dates =", value, "matchDates");
            return (Criteria) this;
        }

        public Criteria andMatchDatesNotEqualTo(Integer value) {
            addCriterion("match_dates <>", value, "matchDates");
            return (Criteria) this;
        }

        public Criteria andMatchDatesGreaterThan(Integer value) {
            addCriterion("match_dates >", value, "matchDates");
            return (Criteria) this;
        }

        public Criteria andMatchDatesGreaterThanOrEqualTo(Integer value) {
            addCriterion("match_dates >=", value, "matchDates");
            return (Criteria) this;
        }

        public Criteria andMatchDatesLessThan(Integer value) {
            addCriterion("match_dates <", value, "matchDates");
            return (Criteria) this;
        }

        public Criteria andMatchDatesLessThanOrEqualTo(Integer value) {
            addCriterion("match_dates <=", value, "matchDates");
            return (Criteria) this;
        }

        public Criteria andMatchDatesIn(List<Integer> values) {
            addCriterion("match_dates in", values, "matchDates");
            return (Criteria) this;
        }

        public Criteria andMatchDatesNotIn(List<Integer> values) {
            addCriterion("match_dates not in", values, "matchDates");
            return (Criteria) this;
        }

        public Criteria andMatchDatesBetween(Integer value1, Integer value2) {
            addCriterion("match_dates between", value1, value2, "matchDates");
            return (Criteria) this;
        }

        public Criteria andMatchDatesNotBetween(Integer value1, Integer value2) {
            addCriterion("match_dates not between", value1, value2, "matchDates");
            return (Criteria) this;
        }

        public Criteria andLqdServiceFeeIsNull() {
            addCriterion("lqd_service_fee is null");
            return (Criteria) this;
        }

        public Criteria andLqdServiceFeeIsNotNull() {
            addCriterion("lqd_service_fee is not null");
            return (Criteria) this;
        }

        public Criteria andLqdServiceFeeEqualTo(BigDecimal value) {
            addCriterion("lqd_service_fee =", value, "lqdServiceFee");
            return (Criteria) this;
        }

        public Criteria andLqdServiceFeeNotEqualTo(BigDecimal value) {
            addCriterion("lqd_service_fee <>", value, "lqdServiceFee");
            return (Criteria) this;
        }

        public Criteria andLqdServiceFeeGreaterThan(BigDecimal value) {
            addCriterion("lqd_service_fee >", value, "lqdServiceFee");
            return (Criteria) this;
        }

        public Criteria andLqdServiceFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("lqd_service_fee >=", value, "lqdServiceFee");
            return (Criteria) this;
        }

        public Criteria andLqdServiceFeeLessThan(BigDecimal value) {
            addCriterion("lqd_service_fee <", value, "lqdServiceFee");
            return (Criteria) this;
        }

        public Criteria andLqdServiceFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("lqd_service_fee <=", value, "lqdServiceFee");
            return (Criteria) this;
        }

        public Criteria andLqdServiceFeeIn(List<BigDecimal> values) {
            addCriterion("lqd_service_fee in", values, "lqdServiceFee");
            return (Criteria) this;
        }

        public Criteria andLqdServiceFeeNotIn(List<BigDecimal> values) {
            addCriterion("lqd_service_fee not in", values, "lqdServiceFee");
            return (Criteria) this;
        }

        public Criteria andLqdServiceFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("lqd_service_fee between", value1, value2, "lqdServiceFee");
            return (Criteria) this;
        }

        public Criteria andLqdServiceFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("lqd_service_fee not between", value1, value2, "lqdServiceFee");
            return (Criteria) this;
        }

        public Criteria andLqdServiceAprIsNull() {
            addCriterion("lqd_service_apr is null");
            return (Criteria) this;
        }

        public Criteria andLqdServiceAprIsNotNull() {
            addCriterion("lqd_service_apr is not null");
            return (Criteria) this;
        }

        public Criteria andLqdServiceAprEqualTo(BigDecimal value) {
            addCriterion("lqd_service_apr =", value, "lqdServiceApr");
            return (Criteria) this;
        }

        public Criteria andLqdServiceAprNotEqualTo(BigDecimal value) {
            addCriterion("lqd_service_apr <>", value, "lqdServiceApr");
            return (Criteria) this;
        }

        public Criteria andLqdServiceAprGreaterThan(BigDecimal value) {
            addCriterion("lqd_service_apr >", value, "lqdServiceApr");
            return (Criteria) this;
        }

        public Criteria andLqdServiceAprGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("lqd_service_apr >=", value, "lqdServiceApr");
            return (Criteria) this;
        }

        public Criteria andLqdServiceAprLessThan(BigDecimal value) {
            addCriterion("lqd_service_apr <", value, "lqdServiceApr");
            return (Criteria) this;
        }

        public Criteria andLqdServiceAprLessThanOrEqualTo(BigDecimal value) {
            addCriterion("lqd_service_apr <=", value, "lqdServiceApr");
            return (Criteria) this;
        }

        public Criteria andLqdServiceAprIn(List<BigDecimal> values) {
            addCriterion("lqd_service_apr in", values, "lqdServiceApr");
            return (Criteria) this;
        }

        public Criteria andLqdServiceAprNotIn(List<BigDecimal> values) {
            addCriterion("lqd_service_apr not in", values, "lqdServiceApr");
            return (Criteria) this;
        }

        public Criteria andLqdServiceAprBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("lqd_service_apr between", value1, value2, "lqdServiceApr");
            return (Criteria) this;
        }

        public Criteria andLqdServiceAprNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("lqd_service_apr not between", value1, value2, "lqdServiceApr");
            return (Criteria) this;
        }

        public Criteria andInvestServiceAprIsNull() {
            addCriterion("invest_service_apr is null");
            return (Criteria) this;
        }

        public Criteria andInvestServiceAprIsNotNull() {
            addCriterion("invest_service_apr is not null");
            return (Criteria) this;
        }

        public Criteria andInvestServiceAprEqualTo(BigDecimal value) {
            addCriterion("invest_service_apr =", value, "investServiceApr");
            return (Criteria) this;
        }

        public Criteria andInvestServiceAprNotEqualTo(BigDecimal value) {
            addCriterion("invest_service_apr <>", value, "investServiceApr");
            return (Criteria) this;
        }

        public Criteria andInvestServiceAprGreaterThan(BigDecimal value) {
            addCriterion("invest_service_apr >", value, "investServiceApr");
            return (Criteria) this;
        }

        public Criteria andInvestServiceAprGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("invest_service_apr >=", value, "investServiceApr");
            return (Criteria) this;
        }

        public Criteria andInvestServiceAprLessThan(BigDecimal value) {
            addCriterion("invest_service_apr <", value, "investServiceApr");
            return (Criteria) this;
        }

        public Criteria andInvestServiceAprLessThanOrEqualTo(BigDecimal value) {
            addCriterion("invest_service_apr <=", value, "investServiceApr");
            return (Criteria) this;
        }

        public Criteria andInvestServiceAprIn(List<BigDecimal> values) {
            addCriterion("invest_service_apr in", values, "investServiceApr");
            return (Criteria) this;
        }

        public Criteria andInvestServiceAprNotIn(List<BigDecimal> values) {
            addCriterion("invest_service_apr not in", values, "investServiceApr");
            return (Criteria) this;
        }

        public Criteria andInvestServiceAprBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("invest_service_apr between", value1, value2, "investServiceApr");
            return (Criteria) this;
        }

        public Criteria andInvestServiceAprNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("invest_service_apr not between", value1, value2, "investServiceApr");
            return (Criteria) this;
        }

        public Criteria andLqdProgressIsNull() {
            addCriterion("lqd_progress is null");
            return (Criteria) this;
        }

        public Criteria andLqdProgressIsNotNull() {
            addCriterion("lqd_progress is not null");
            return (Criteria) this;
        }

        public Criteria andLqdProgressEqualTo(BigDecimal value) {
            addCriterion("lqd_progress =", value, "lqdProgress");
            return (Criteria) this;
        }

        public Criteria andLqdProgressNotEqualTo(BigDecimal value) {
            addCriterion("lqd_progress <>", value, "lqdProgress");
            return (Criteria) this;
        }

        public Criteria andLqdProgressGreaterThan(BigDecimal value) {
            addCriterion("lqd_progress >", value, "lqdProgress");
            return (Criteria) this;
        }

        public Criteria andLqdProgressGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("lqd_progress >=", value, "lqdProgress");
            return (Criteria) this;
        }

        public Criteria andLqdProgressLessThan(BigDecimal value) {
            addCriterion("lqd_progress <", value, "lqdProgress");
            return (Criteria) this;
        }

        public Criteria andLqdProgressLessThanOrEqualTo(BigDecimal value) {
            addCriterion("lqd_progress <=", value, "lqdProgress");
            return (Criteria) this;
        }

        public Criteria andLqdProgressIn(List<BigDecimal> values) {
            addCriterion("lqd_progress in", values, "lqdProgress");
            return (Criteria) this;
        }

        public Criteria andLqdProgressNotIn(List<BigDecimal> values) {
            addCriterion("lqd_progress not in", values, "lqdProgress");
            return (Criteria) this;
        }

        public Criteria andLqdProgressBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("lqd_progress between", value1, value2, "lqdProgress");
            return (Criteria) this;
        }

        public Criteria andLqdProgressNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("lqd_progress not between", value1, value2, "lqdProgress");
            return (Criteria) this;
        }

        public Criteria andCreateUserIsNull() {
            addCriterion("create_user is null");
            return (Criteria) this;
        }

        public Criteria andCreateUserIsNotNull() {
            addCriterion("create_user is not null");
            return (Criteria) this;
        }

        public Criteria andCreateUserEqualTo(Integer value) {
            addCriterion("create_user =", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotEqualTo(Integer value) {
            addCriterion("create_user <>", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserGreaterThan(Integer value) {
            addCriterion("create_user >", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserGreaterThanOrEqualTo(Integer value) {
            addCriterion("create_user >=", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLessThan(Integer value) {
            addCriterion("create_user <", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLessThanOrEqualTo(Integer value) {
            addCriterion("create_user <=", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserIn(List<Integer> values) {
            addCriterion("create_user in", values, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotIn(List<Integer> values) {
            addCriterion("create_user not in", values, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserBetween(Integer value1, Integer value2) {
            addCriterion("create_user between", value1, value2, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotBetween(Integer value1, Integer value2) {
            addCriterion("create_user not between", value1, value2, "createUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIsNull() {
            addCriterion("update_user is null");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIsNotNull() {
            addCriterion("update_user is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateUserEqualTo(Integer value) {
            addCriterion("update_user =", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotEqualTo(Integer value) {
            addCriterion("update_user <>", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserGreaterThan(Integer value) {
            addCriterion("update_user >", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserGreaterThanOrEqualTo(Integer value) {
            addCriterion("update_user >=", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLessThan(Integer value) {
            addCriterion("update_user <", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLessThanOrEqualTo(Integer value) {
            addCriterion("update_user <=", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIn(List<Integer> values) {
            addCriterion("update_user in", values, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotIn(List<Integer> values) {
            addCriterion("update_user not in", values, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserBetween(Integer value1, Integer value2) {
            addCriterion("update_user between", value1, value2, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotBetween(Integer value1, Integer value2) {
            addCriterion("update_user not between", value1, value2, "updateUser");
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

        public Criteria andTenderUserUtmIdIsNull() {
            addCriterion("tender_user_utm_id is null");
            return (Criteria) this;
        }

        public Criteria andTenderUserUtmIdIsNotNull() {
            addCriterion("tender_user_utm_id is not null");
            return (Criteria) this;
        }

        public Criteria andTenderUserUtmIdEqualTo(Integer value) {
            addCriterion("tender_user_utm_id =", value, "tenderUserUtmId");
            return (Criteria) this;
        }

        public Criteria andTenderUserUtmIdNotEqualTo(Integer value) {
            addCriterion("tender_user_utm_id <>", value, "tenderUserUtmId");
            return (Criteria) this;
        }

        public Criteria andTenderUserUtmIdGreaterThan(Integer value) {
            addCriterion("tender_user_utm_id >", value, "tenderUserUtmId");
            return (Criteria) this;
        }

        public Criteria andTenderUserUtmIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("tender_user_utm_id >=", value, "tenderUserUtmId");
            return (Criteria) this;
        }

        public Criteria andTenderUserUtmIdLessThan(Integer value) {
            addCriterion("tender_user_utm_id <", value, "tenderUserUtmId");
            return (Criteria) this;
        }

        public Criteria andTenderUserUtmIdLessThanOrEqualTo(Integer value) {
            addCriterion("tender_user_utm_id <=", value, "tenderUserUtmId");
            return (Criteria) this;
        }

        public Criteria andTenderUserUtmIdIn(List<Integer> values) {
            addCriterion("tender_user_utm_id in", values, "tenderUserUtmId");
            return (Criteria) this;
        }

        public Criteria andTenderUserUtmIdNotIn(List<Integer> values) {
            addCriterion("tender_user_utm_id not in", values, "tenderUserUtmId");
            return (Criteria) this;
        }

        public Criteria andTenderUserUtmIdBetween(Integer value1, Integer value2) {
            addCriterion("tender_user_utm_id between", value1, value2, "tenderUserUtmId");
            return (Criteria) this;
        }

        public Criteria andTenderUserUtmIdNotBetween(Integer value1, Integer value2) {
            addCriterion("tender_user_utm_id not between", value1, value2, "tenderUserUtmId");
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