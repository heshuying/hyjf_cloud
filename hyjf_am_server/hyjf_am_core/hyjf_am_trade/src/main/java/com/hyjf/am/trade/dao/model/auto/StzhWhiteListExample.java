package com.hyjf.am.trade.dao.model.auto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StzhWhiteListExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public StzhWhiteListExample() {
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

        public Criteria andAccountidIsNull() {
            addCriterion("accountid is null");
            return (Criteria) this;
        }

        public Criteria andAccountidIsNotNull() {
            addCriterion("accountid is not null");
            return (Criteria) this;
        }

        public Criteria andAccountidEqualTo(String value) {
            addCriterion("accountid =", value, "accountid");
            return (Criteria) this;
        }

        public Criteria andAccountidNotEqualTo(String value) {
            addCriterion("accountid <>", value, "accountid");
            return (Criteria) this;
        }

        public Criteria andAccountidGreaterThan(String value) {
            addCriterion("accountid >", value, "accountid");
            return (Criteria) this;
        }

        public Criteria andAccountidGreaterThanOrEqualTo(String value) {
            addCriterion("accountid >=", value, "accountid");
            return (Criteria) this;
        }

        public Criteria andAccountidLessThan(String value) {
            addCriterion("accountid <", value, "accountid");
            return (Criteria) this;
        }

        public Criteria andAccountidLessThanOrEqualTo(String value) {
            addCriterion("accountid <=", value, "accountid");
            return (Criteria) this;
        }

        public Criteria andAccountidLike(String value) {
            addCriterion("accountid like", value, "accountid");
            return (Criteria) this;
        }

        public Criteria andAccountidNotLike(String value) {
            addCriterion("accountid not like", value, "accountid");
            return (Criteria) this;
        }

        public Criteria andAccountidIn(List<String> values) {
            addCriterion("accountid in", values, "accountid");
            return (Criteria) this;
        }

        public Criteria andAccountidNotIn(List<String> values) {
            addCriterion("accountid not in", values, "accountid");
            return (Criteria) this;
        }

        public Criteria andAccountidBetween(String value1, String value2) {
            addCriterion("accountid between", value1, value2, "accountid");
            return (Criteria) this;
        }

        public Criteria andAccountidNotBetween(String value1, String value2) {
            addCriterion("accountid not between", value1, value2, "accountid");
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

        public Criteria andCustomerNameIsNull() {
            addCriterion("customer_name is null");
            return (Criteria) this;
        }

        public Criteria andCustomerNameIsNotNull() {
            addCriterion("customer_name is not null");
            return (Criteria) this;
        }

        public Criteria andCustomerNameEqualTo(String value) {
            addCriterion("customer_name =", value, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameNotEqualTo(String value) {
            addCriterion("customer_name <>", value, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameGreaterThan(String value) {
            addCriterion("customer_name >", value, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameGreaterThanOrEqualTo(String value) {
            addCriterion("customer_name >=", value, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameLessThan(String value) {
            addCriterion("customer_name <", value, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameLessThanOrEqualTo(String value) {
            addCriterion("customer_name <=", value, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameLike(String value) {
            addCriterion("customer_name like", value, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameNotLike(String value) {
            addCriterion("customer_name not like", value, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameIn(List<String> values) {
            addCriterion("customer_name in", values, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameNotIn(List<String> values) {
            addCriterion("customer_name not in", values, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameBetween(String value1, String value2) {
            addCriterion("customer_name between", value1, value2, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameNotBetween(String value1, String value2) {
            addCriterion("customer_name not between", value1, value2, "customerName");
            return (Criteria) this;
        }

        public Criteria andStUserIdIsNull() {
            addCriterion("st_user_id is null");
            return (Criteria) this;
        }

        public Criteria andStUserIdIsNotNull() {
            addCriterion("st_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andStUserIdEqualTo(Integer value) {
            addCriterion("st_user_id =", value, "stUserId");
            return (Criteria) this;
        }

        public Criteria andStUserIdNotEqualTo(Integer value) {
            addCriterion("st_user_id <>", value, "stUserId");
            return (Criteria) this;
        }

        public Criteria andStUserIdGreaterThan(Integer value) {
            addCriterion("st_user_id >", value, "stUserId");
            return (Criteria) this;
        }

        public Criteria andStUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("st_user_id >=", value, "stUserId");
            return (Criteria) this;
        }

        public Criteria andStUserIdLessThan(Integer value) {
            addCriterion("st_user_id <", value, "stUserId");
            return (Criteria) this;
        }

        public Criteria andStUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("st_user_id <=", value, "stUserId");
            return (Criteria) this;
        }

        public Criteria andStUserIdIn(List<Integer> values) {
            addCriterion("st_user_id in", values, "stUserId");
            return (Criteria) this;
        }

        public Criteria andStUserIdNotIn(List<Integer> values) {
            addCriterion("st_user_id not in", values, "stUserId");
            return (Criteria) this;
        }

        public Criteria andStUserIdBetween(Integer value1, Integer value2) {
            addCriterion("st_user_id between", value1, value2, "stUserId");
            return (Criteria) this;
        }

        public Criteria andStUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("st_user_id not between", value1, value2, "stUserId");
            return (Criteria) this;
        }

        public Criteria andStUserNameIsNull() {
            addCriterion("st_user_name is null");
            return (Criteria) this;
        }

        public Criteria andStUserNameIsNotNull() {
            addCriterion("st_user_name is not null");
            return (Criteria) this;
        }

        public Criteria andStUserNameEqualTo(String value) {
            addCriterion("st_user_name =", value, "stUserName");
            return (Criteria) this;
        }

        public Criteria andStUserNameNotEqualTo(String value) {
            addCriterion("st_user_name <>", value, "stUserName");
            return (Criteria) this;
        }

        public Criteria andStUserNameGreaterThan(String value) {
            addCriterion("st_user_name >", value, "stUserName");
            return (Criteria) this;
        }

        public Criteria andStUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("st_user_name >=", value, "stUserName");
            return (Criteria) this;
        }

        public Criteria andStUserNameLessThan(String value) {
            addCriterion("st_user_name <", value, "stUserName");
            return (Criteria) this;
        }

        public Criteria andStUserNameLessThanOrEqualTo(String value) {
            addCriterion("st_user_name <=", value, "stUserName");
            return (Criteria) this;
        }

        public Criteria andStUserNameLike(String value) {
            addCriterion("st_user_name like", value, "stUserName");
            return (Criteria) this;
        }

        public Criteria andStUserNameNotLike(String value) {
            addCriterion("st_user_name not like", value, "stUserName");
            return (Criteria) this;
        }

        public Criteria andStUserNameIn(List<String> values) {
            addCriterion("st_user_name in", values, "stUserName");
            return (Criteria) this;
        }

        public Criteria andStUserNameNotIn(List<String> values) {
            addCriterion("st_user_name not in", values, "stUserName");
            return (Criteria) this;
        }

        public Criteria andStUserNameBetween(String value1, String value2) {
            addCriterion("st_user_name between", value1, value2, "stUserName");
            return (Criteria) this;
        }

        public Criteria andStUserNameNotBetween(String value1, String value2) {
            addCriterion("st_user_name not between", value1, value2, "stUserName");
            return (Criteria) this;
        }

        public Criteria andStAccountidIsNull() {
            addCriterion("st_accountid is null");
            return (Criteria) this;
        }

        public Criteria andStAccountidIsNotNull() {
            addCriterion("st_accountid is not null");
            return (Criteria) this;
        }

        public Criteria andStAccountidEqualTo(String value) {
            addCriterion("st_accountid =", value, "stAccountid");
            return (Criteria) this;
        }

        public Criteria andStAccountidNotEqualTo(String value) {
            addCriterion("st_accountid <>", value, "stAccountid");
            return (Criteria) this;
        }

        public Criteria andStAccountidGreaterThan(String value) {
            addCriterion("st_accountid >", value, "stAccountid");
            return (Criteria) this;
        }

        public Criteria andStAccountidGreaterThanOrEqualTo(String value) {
            addCriterion("st_accountid >=", value, "stAccountid");
            return (Criteria) this;
        }

        public Criteria andStAccountidLessThan(String value) {
            addCriterion("st_accountid <", value, "stAccountid");
            return (Criteria) this;
        }

        public Criteria andStAccountidLessThanOrEqualTo(String value) {
            addCriterion("st_accountid <=", value, "stAccountid");
            return (Criteria) this;
        }

        public Criteria andStAccountidLike(String value) {
            addCriterion("st_accountid like", value, "stAccountid");
            return (Criteria) this;
        }

        public Criteria andStAccountidNotLike(String value) {
            addCriterion("st_accountid not like", value, "stAccountid");
            return (Criteria) this;
        }

        public Criteria andStAccountidIn(List<String> values) {
            addCriterion("st_accountid in", values, "stAccountid");
            return (Criteria) this;
        }

        public Criteria andStAccountidNotIn(List<String> values) {
            addCriterion("st_accountid not in", values, "stAccountid");
            return (Criteria) this;
        }

        public Criteria andStAccountidBetween(String value1, String value2) {
            addCriterion("st_accountid between", value1, value2, "stAccountid");
            return (Criteria) this;
        }

        public Criteria andStAccountidNotBetween(String value1, String value2) {
            addCriterion("st_accountid not between", value1, value2, "stAccountid");
            return (Criteria) this;
        }

        public Criteria andStMobileIsNull() {
            addCriterion("st_mobile is null");
            return (Criteria) this;
        }

        public Criteria andStMobileIsNotNull() {
            addCriterion("st_mobile is not null");
            return (Criteria) this;
        }

        public Criteria andStMobileEqualTo(String value) {
            addCriterion("st_mobile =", value, "stMobile");
            return (Criteria) this;
        }

        public Criteria andStMobileNotEqualTo(String value) {
            addCriterion("st_mobile <>", value, "stMobile");
            return (Criteria) this;
        }

        public Criteria andStMobileGreaterThan(String value) {
            addCriterion("st_mobile >", value, "stMobile");
            return (Criteria) this;
        }

        public Criteria andStMobileGreaterThanOrEqualTo(String value) {
            addCriterion("st_mobile >=", value, "stMobile");
            return (Criteria) this;
        }

        public Criteria andStMobileLessThan(String value) {
            addCriterion("st_mobile <", value, "stMobile");
            return (Criteria) this;
        }

        public Criteria andStMobileLessThanOrEqualTo(String value) {
            addCriterion("st_mobile <=", value, "stMobile");
            return (Criteria) this;
        }

        public Criteria andStMobileLike(String value) {
            addCriterion("st_mobile like", value, "stMobile");
            return (Criteria) this;
        }

        public Criteria andStMobileNotLike(String value) {
            addCriterion("st_mobile not like", value, "stMobile");
            return (Criteria) this;
        }

        public Criteria andStMobileIn(List<String> values) {
            addCriterion("st_mobile in", values, "stMobile");
            return (Criteria) this;
        }

        public Criteria andStMobileNotIn(List<String> values) {
            addCriterion("st_mobile not in", values, "stMobile");
            return (Criteria) this;
        }

        public Criteria andStMobileBetween(String value1, String value2) {
            addCriterion("st_mobile between", value1, value2, "stMobile");
            return (Criteria) this;
        }

        public Criteria andStMobileNotBetween(String value1, String value2) {
            addCriterion("st_mobile not between", value1, value2, "stMobile");
            return (Criteria) this;
        }

        public Criteria andStCustomerNameIsNull() {
            addCriterion("st_customer_name is null");
            return (Criteria) this;
        }

        public Criteria andStCustomerNameIsNotNull() {
            addCriterion("st_customer_name is not null");
            return (Criteria) this;
        }

        public Criteria andStCustomerNameEqualTo(String value) {
            addCriterion("st_customer_name =", value, "stCustomerName");
            return (Criteria) this;
        }

        public Criteria andStCustomerNameNotEqualTo(String value) {
            addCriterion("st_customer_name <>", value, "stCustomerName");
            return (Criteria) this;
        }

        public Criteria andStCustomerNameGreaterThan(String value) {
            addCriterion("st_customer_name >", value, "stCustomerName");
            return (Criteria) this;
        }

        public Criteria andStCustomerNameGreaterThanOrEqualTo(String value) {
            addCriterion("st_customer_name >=", value, "stCustomerName");
            return (Criteria) this;
        }

        public Criteria andStCustomerNameLessThan(String value) {
            addCriterion("st_customer_name <", value, "stCustomerName");
            return (Criteria) this;
        }

        public Criteria andStCustomerNameLessThanOrEqualTo(String value) {
            addCriterion("st_customer_name <=", value, "stCustomerName");
            return (Criteria) this;
        }

        public Criteria andStCustomerNameLike(String value) {
            addCriterion("st_customer_name like", value, "stCustomerName");
            return (Criteria) this;
        }

        public Criteria andStCustomerNameNotLike(String value) {
            addCriterion("st_customer_name not like", value, "stCustomerName");
            return (Criteria) this;
        }

        public Criteria andStCustomerNameIn(List<String> values) {
            addCriterion("st_customer_name in", values, "stCustomerName");
            return (Criteria) this;
        }

        public Criteria andStCustomerNameNotIn(List<String> values) {
            addCriterion("st_customer_name not in", values, "stCustomerName");
            return (Criteria) this;
        }

        public Criteria andStCustomerNameBetween(String value1, String value2) {
            addCriterion("st_customer_name between", value1, value2, "stCustomerName");
            return (Criteria) this;
        }

        public Criteria andStCustomerNameNotBetween(String value1, String value2) {
            addCriterion("st_customer_name not between", value1, value2, "stCustomerName");
            return (Criteria) this;
        }

        public Criteria andApprovalNameIsNull() {
            addCriterion("approval_name is null");
            return (Criteria) this;
        }

        public Criteria andApprovalNameIsNotNull() {
            addCriterion("approval_name is not null");
            return (Criteria) this;
        }

        public Criteria andApprovalNameEqualTo(String value) {
            addCriterion("approval_name =", value, "approvalName");
            return (Criteria) this;
        }

        public Criteria andApprovalNameNotEqualTo(String value) {
            addCriterion("approval_name <>", value, "approvalName");
            return (Criteria) this;
        }

        public Criteria andApprovalNameGreaterThan(String value) {
            addCriterion("approval_name >", value, "approvalName");
            return (Criteria) this;
        }

        public Criteria andApprovalNameGreaterThanOrEqualTo(String value) {
            addCriterion("approval_name >=", value, "approvalName");
            return (Criteria) this;
        }

        public Criteria andApprovalNameLessThan(String value) {
            addCriterion("approval_name <", value, "approvalName");
            return (Criteria) this;
        }

        public Criteria andApprovalNameLessThanOrEqualTo(String value) {
            addCriterion("approval_name <=", value, "approvalName");
            return (Criteria) this;
        }

        public Criteria andApprovalNameLike(String value) {
            addCriterion("approval_name like", value, "approvalName");
            return (Criteria) this;
        }

        public Criteria andApprovalNameNotLike(String value) {
            addCriterion("approval_name not like", value, "approvalName");
            return (Criteria) this;
        }

        public Criteria andApprovalNameIn(List<String> values) {
            addCriterion("approval_name in", values, "approvalName");
            return (Criteria) this;
        }

        public Criteria andApprovalNameNotIn(List<String> values) {
            addCriterion("approval_name not in", values, "approvalName");
            return (Criteria) this;
        }

        public Criteria andApprovalNameBetween(String value1, String value2) {
            addCriterion("approval_name between", value1, value2, "approvalName");
            return (Criteria) this;
        }

        public Criteria andApprovalNameNotBetween(String value1, String value2) {
            addCriterion("approval_name not between", value1, value2, "approvalName");
            return (Criteria) this;
        }

        public Criteria andApprovalTimeIsNull() {
            addCriterion("approval_time is null");
            return (Criteria) this;
        }

        public Criteria andApprovalTimeIsNotNull() {
            addCriterion("approval_time is not null");
            return (Criteria) this;
        }

        public Criteria andApprovalTimeEqualTo(String value) {
            addCriterion("approval_time =", value, "approvalTime");
            return (Criteria) this;
        }

        public Criteria andApprovalTimeNotEqualTo(String value) {
            addCriterion("approval_time <>", value, "approvalTime");
            return (Criteria) this;
        }

        public Criteria andApprovalTimeGreaterThan(String value) {
            addCriterion("approval_time >", value, "approvalTime");
            return (Criteria) this;
        }

        public Criteria andApprovalTimeGreaterThanOrEqualTo(String value) {
            addCriterion("approval_time >=", value, "approvalTime");
            return (Criteria) this;
        }

        public Criteria andApprovalTimeLessThan(String value) {
            addCriterion("approval_time <", value, "approvalTime");
            return (Criteria) this;
        }

        public Criteria andApprovalTimeLessThanOrEqualTo(String value) {
            addCriterion("approval_time <=", value, "approvalTime");
            return (Criteria) this;
        }

        public Criteria andApprovalTimeLike(String value) {
            addCriterion("approval_time like", value, "approvalTime");
            return (Criteria) this;
        }

        public Criteria andApprovalTimeNotLike(String value) {
            addCriterion("approval_time not like", value, "approvalTime");
            return (Criteria) this;
        }

        public Criteria andApprovalTimeIn(List<String> values) {
            addCriterion("approval_time in", values, "approvalTime");
            return (Criteria) this;
        }

        public Criteria andApprovalTimeNotIn(List<String> values) {
            addCriterion("approval_time not in", values, "approvalTime");
            return (Criteria) this;
        }

        public Criteria andApprovalTimeBetween(String value1, String value2) {
            addCriterion("approval_time between", value1, value2, "approvalTime");
            return (Criteria) this;
        }

        public Criteria andApprovalTimeNotBetween(String value1, String value2) {
            addCriterion("approval_time not between", value1, value2, "approvalTime");
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

        public Criteria andInstcodeIsNull() {
            addCriterion("instCode is null");
            return (Criteria) this;
        }

        public Criteria andInstcodeIsNotNull() {
            addCriterion("instCode is not null");
            return (Criteria) this;
        }

        public Criteria andInstcodeEqualTo(String value) {
            addCriterion("instCode =", value, "instcode");
            return (Criteria) this;
        }

        public Criteria andInstcodeNotEqualTo(String value) {
            addCriterion("instCode <>", value, "instcode");
            return (Criteria) this;
        }

        public Criteria andInstcodeGreaterThan(String value) {
            addCriterion("instCode >", value, "instcode");
            return (Criteria) this;
        }

        public Criteria andInstcodeGreaterThanOrEqualTo(String value) {
            addCriterion("instCode >=", value, "instcode");
            return (Criteria) this;
        }

        public Criteria andInstcodeLessThan(String value) {
            addCriterion("instCode <", value, "instcode");
            return (Criteria) this;
        }

        public Criteria andInstcodeLessThanOrEqualTo(String value) {
            addCriterion("instCode <=", value, "instcode");
            return (Criteria) this;
        }

        public Criteria andInstcodeLike(String value) {
            addCriterion("instCode like", value, "instcode");
            return (Criteria) this;
        }

        public Criteria andInstcodeNotLike(String value) {
            addCriterion("instCode not like", value, "instcode");
            return (Criteria) this;
        }

        public Criteria andInstcodeIn(List<String> values) {
            addCriterion("instCode in", values, "instcode");
            return (Criteria) this;
        }

        public Criteria andInstcodeNotIn(List<String> values) {
            addCriterion("instCode not in", values, "instcode");
            return (Criteria) this;
        }

        public Criteria andInstcodeBetween(String value1, String value2) {
            addCriterion("instCode between", value1, value2, "instcode");
            return (Criteria) this;
        }

        public Criteria andInstcodeNotBetween(String value1, String value2) {
            addCriterion("instCode not between", value1, value2, "instcode");
            return (Criteria) this;
        }

        public Criteria andInstnameIsNull() {
            addCriterion("instName is null");
            return (Criteria) this;
        }

        public Criteria andInstnameIsNotNull() {
            addCriterion("instName is not null");
            return (Criteria) this;
        }

        public Criteria andInstnameEqualTo(String value) {
            addCriterion("instName =", value, "instname");
            return (Criteria) this;
        }

        public Criteria andInstnameNotEqualTo(String value) {
            addCriterion("instName <>", value, "instname");
            return (Criteria) this;
        }

        public Criteria andInstnameGreaterThan(String value) {
            addCriterion("instName >", value, "instname");
            return (Criteria) this;
        }

        public Criteria andInstnameGreaterThanOrEqualTo(String value) {
            addCriterion("instName >=", value, "instname");
            return (Criteria) this;
        }

        public Criteria andInstnameLessThan(String value) {
            addCriterion("instName <", value, "instname");
            return (Criteria) this;
        }

        public Criteria andInstnameLessThanOrEqualTo(String value) {
            addCriterion("instName <=", value, "instname");
            return (Criteria) this;
        }

        public Criteria andInstnameLike(String value) {
            addCriterion("instName like", value, "instname");
            return (Criteria) this;
        }

        public Criteria andInstnameNotLike(String value) {
            addCriterion("instName not like", value, "instname");
            return (Criteria) this;
        }

        public Criteria andInstnameIn(List<String> values) {
            addCriterion("instName in", values, "instname");
            return (Criteria) this;
        }

        public Criteria andInstnameNotIn(List<String> values) {
            addCriterion("instName not in", values, "instname");
            return (Criteria) this;
        }

        public Criteria andInstnameBetween(String value1, String value2) {
            addCriterion("instName between", value1, value2, "instname");
            return (Criteria) this;
        }

        public Criteria andInstnameNotBetween(String value1, String value2) {
            addCriterion("instName not between", value1, value2, "instname");
            return (Criteria) this;
        }

        public Criteria andStateIsNull() {
            addCriterion("`state` is null");
            return (Criteria) this;
        }

        public Criteria andStateIsNotNull() {
            addCriterion("`state` is not null");
            return (Criteria) this;
        }

        public Criteria andStateEqualTo(Integer value) {
            addCriterion("`state` =", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotEqualTo(Integer value) {
            addCriterion("`state` <>", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThan(Integer value) {
            addCriterion("`state` >", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThanOrEqualTo(Integer value) {
            addCriterion("`state` >=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThan(Integer value) {
            addCriterion("`state` <", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThanOrEqualTo(Integer value) {
            addCriterion("`state` <=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateIn(List<Integer> values) {
            addCriterion("`state` in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotIn(List<Integer> values) {
            addCriterion("`state` not in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateBetween(Integer value1, Integer value2) {
            addCriterion("`state` between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotBetween(Integer value1, Integer value2) {
            addCriterion("`state` not between", value1, value2, "state");
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