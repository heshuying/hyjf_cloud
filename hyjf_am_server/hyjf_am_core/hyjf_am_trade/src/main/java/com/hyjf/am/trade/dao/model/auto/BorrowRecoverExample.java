package com.hyjf.am.trade.dao.model.auto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BorrowRecoverExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public BorrowRecoverExample() {
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

        public Criteria andBorrowNidIsNull() {
            addCriterion("borrow_nid is null");
            return (Criteria) this;
        }

        public Criteria andBorrowNidIsNotNull() {
            addCriterion("borrow_nid is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowNidEqualTo(String value) {
            addCriterion("borrow_nid =", value, "borrowNid");
            return (Criteria) this;
        }

        public Criteria andBorrowNidNotEqualTo(String value) {
            addCriterion("borrow_nid <>", value, "borrowNid");
            return (Criteria) this;
        }

        public Criteria andBorrowNidGreaterThan(String value) {
            addCriterion("borrow_nid >", value, "borrowNid");
            return (Criteria) this;
        }

        public Criteria andBorrowNidGreaterThanOrEqualTo(String value) {
            addCriterion("borrow_nid >=", value, "borrowNid");
            return (Criteria) this;
        }

        public Criteria andBorrowNidLessThan(String value) {
            addCriterion("borrow_nid <", value, "borrowNid");
            return (Criteria) this;
        }

        public Criteria andBorrowNidLessThanOrEqualTo(String value) {
            addCriterion("borrow_nid <=", value, "borrowNid");
            return (Criteria) this;
        }

        public Criteria andBorrowNidLike(String value) {
            addCriterion("borrow_nid like", value, "borrowNid");
            return (Criteria) this;
        }

        public Criteria andBorrowNidNotLike(String value) {
            addCriterion("borrow_nid not like", value, "borrowNid");
            return (Criteria) this;
        }

        public Criteria andBorrowNidIn(List<String> values) {
            addCriterion("borrow_nid in", values, "borrowNid");
            return (Criteria) this;
        }

        public Criteria andBorrowNidNotIn(List<String> values) {
            addCriterion("borrow_nid not in", values, "borrowNid");
            return (Criteria) this;
        }

        public Criteria andBorrowNidBetween(String value1, String value2) {
            addCriterion("borrow_nid between", value1, value2, "borrowNid");
            return (Criteria) this;
        }

        public Criteria andBorrowNidNotBetween(String value1, String value2) {
            addCriterion("borrow_nid not between", value1, value2, "borrowNid");
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

        public Criteria andAuthCodeIsNull() {
            addCriterion("auth_code is null");
            return (Criteria) this;
        }

        public Criteria andAuthCodeIsNotNull() {
            addCriterion("auth_code is not null");
            return (Criteria) this;
        }

        public Criteria andAuthCodeEqualTo(String value) {
            addCriterion("auth_code =", value, "authCode");
            return (Criteria) this;
        }

        public Criteria andAuthCodeNotEqualTo(String value) {
            addCriterion("auth_code <>", value, "authCode");
            return (Criteria) this;
        }

        public Criteria andAuthCodeGreaterThan(String value) {
            addCriterion("auth_code >", value, "authCode");
            return (Criteria) this;
        }

        public Criteria andAuthCodeGreaterThanOrEqualTo(String value) {
            addCriterion("auth_code >=", value, "authCode");
            return (Criteria) this;
        }

        public Criteria andAuthCodeLessThan(String value) {
            addCriterion("auth_code <", value, "authCode");
            return (Criteria) this;
        }

        public Criteria andAuthCodeLessThanOrEqualTo(String value) {
            addCriterion("auth_code <=", value, "authCode");
            return (Criteria) this;
        }

        public Criteria andAuthCodeLike(String value) {
            addCriterion("auth_code like", value, "authCode");
            return (Criteria) this;
        }

        public Criteria andAuthCodeNotLike(String value) {
            addCriterion("auth_code not like", value, "authCode");
            return (Criteria) this;
        }

        public Criteria andAuthCodeIn(List<String> values) {
            addCriterion("auth_code in", values, "authCode");
            return (Criteria) this;
        }

        public Criteria andAuthCodeNotIn(List<String> values) {
            addCriterion("auth_code not in", values, "authCode");
            return (Criteria) this;
        }

        public Criteria andAuthCodeBetween(String value1, String value2) {
            addCriterion("auth_code between", value1, value2, "authCode");
            return (Criteria) this;
        }

        public Criteria andAuthCodeNotBetween(String value1, String value2) {
            addCriterion("auth_code not between", value1, value2, "authCode");
            return (Criteria) this;
        }

        public Criteria andBorrowUseridIsNull() {
            addCriterion("borrow_userid is null");
            return (Criteria) this;
        }

        public Criteria andBorrowUseridIsNotNull() {
            addCriterion("borrow_userid is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowUseridEqualTo(Integer value) {
            addCriterion("borrow_userid =", value, "borrowUserid");
            return (Criteria) this;
        }

        public Criteria andBorrowUseridNotEqualTo(Integer value) {
            addCriterion("borrow_userid <>", value, "borrowUserid");
            return (Criteria) this;
        }

        public Criteria andBorrowUseridGreaterThan(Integer value) {
            addCriterion("borrow_userid >", value, "borrowUserid");
            return (Criteria) this;
        }

        public Criteria andBorrowUseridGreaterThanOrEqualTo(Integer value) {
            addCriterion("borrow_userid >=", value, "borrowUserid");
            return (Criteria) this;
        }

        public Criteria andBorrowUseridLessThan(Integer value) {
            addCriterion("borrow_userid <", value, "borrowUserid");
            return (Criteria) this;
        }

        public Criteria andBorrowUseridLessThanOrEqualTo(Integer value) {
            addCriterion("borrow_userid <=", value, "borrowUserid");
            return (Criteria) this;
        }

        public Criteria andBorrowUseridIn(List<Integer> values) {
            addCriterion("borrow_userid in", values, "borrowUserid");
            return (Criteria) this;
        }

        public Criteria andBorrowUseridNotIn(List<Integer> values) {
            addCriterion("borrow_userid not in", values, "borrowUserid");
            return (Criteria) this;
        }

        public Criteria andBorrowUseridBetween(Integer value1, Integer value2) {
            addCriterion("borrow_userid between", value1, value2, "borrowUserid");
            return (Criteria) this;
        }

        public Criteria andBorrowUseridNotBetween(Integer value1, Integer value2) {
            addCriterion("borrow_userid not between", value1, value2, "borrowUserid");
            return (Criteria) this;
        }

        public Criteria andBorrowUserNameIsNull() {
            addCriterion("borrow_user_name is null");
            return (Criteria) this;
        }

        public Criteria andBorrowUserNameIsNotNull() {
            addCriterion("borrow_user_name is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowUserNameEqualTo(String value) {
            addCriterion("borrow_user_name =", value, "borrowUserName");
            return (Criteria) this;
        }

        public Criteria andBorrowUserNameNotEqualTo(String value) {
            addCriterion("borrow_user_name <>", value, "borrowUserName");
            return (Criteria) this;
        }

        public Criteria andBorrowUserNameGreaterThan(String value) {
            addCriterion("borrow_user_name >", value, "borrowUserName");
            return (Criteria) this;
        }

        public Criteria andBorrowUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("borrow_user_name >=", value, "borrowUserName");
            return (Criteria) this;
        }

        public Criteria andBorrowUserNameLessThan(String value) {
            addCriterion("borrow_user_name <", value, "borrowUserName");
            return (Criteria) this;
        }

        public Criteria andBorrowUserNameLessThanOrEqualTo(String value) {
            addCriterion("borrow_user_name <=", value, "borrowUserName");
            return (Criteria) this;
        }

        public Criteria andBorrowUserNameLike(String value) {
            addCriterion("borrow_user_name like", value, "borrowUserName");
            return (Criteria) this;
        }

        public Criteria andBorrowUserNameNotLike(String value) {
            addCriterion("borrow_user_name not like", value, "borrowUserName");
            return (Criteria) this;
        }

        public Criteria andBorrowUserNameIn(List<String> values) {
            addCriterion("borrow_user_name in", values, "borrowUserName");
            return (Criteria) this;
        }

        public Criteria andBorrowUserNameNotIn(List<String> values) {
            addCriterion("borrow_user_name not in", values, "borrowUserName");
            return (Criteria) this;
        }

        public Criteria andBorrowUserNameBetween(String value1, String value2) {
            addCriterion("borrow_user_name between", value1, value2, "borrowUserName");
            return (Criteria) this;
        }

        public Criteria andBorrowUserNameNotBetween(String value1, String value2) {
            addCriterion("borrow_user_name not between", value1, value2, "borrowUserName");
            return (Criteria) this;
        }

        public Criteria andTenderIdIsNull() {
            addCriterion("tender_id is null");
            return (Criteria) this;
        }

        public Criteria andTenderIdIsNotNull() {
            addCriterion("tender_id is not null");
            return (Criteria) this;
        }

        public Criteria andTenderIdEqualTo(Integer value) {
            addCriterion("tender_id =", value, "tenderId");
            return (Criteria) this;
        }

        public Criteria andTenderIdNotEqualTo(Integer value) {
            addCriterion("tender_id <>", value, "tenderId");
            return (Criteria) this;
        }

        public Criteria andTenderIdGreaterThan(Integer value) {
            addCriterion("tender_id >", value, "tenderId");
            return (Criteria) this;
        }

        public Criteria andTenderIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("tender_id >=", value, "tenderId");
            return (Criteria) this;
        }

        public Criteria andTenderIdLessThan(Integer value) {
            addCriterion("tender_id <", value, "tenderId");
            return (Criteria) this;
        }

        public Criteria andTenderIdLessThanOrEqualTo(Integer value) {
            addCriterion("tender_id <=", value, "tenderId");
            return (Criteria) this;
        }

        public Criteria andTenderIdIn(List<Integer> values) {
            addCriterion("tender_id in", values, "tenderId");
            return (Criteria) this;
        }

        public Criteria andTenderIdNotIn(List<Integer> values) {
            addCriterion("tender_id not in", values, "tenderId");
            return (Criteria) this;
        }

        public Criteria andTenderIdBetween(Integer value1, Integer value2) {
            addCriterion("tender_id between", value1, value2, "tenderId");
            return (Criteria) this;
        }

        public Criteria andTenderIdNotBetween(Integer value1, Integer value2) {
            addCriterion("tender_id not between", value1, value2, "tenderId");
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

        public Criteria andRecoverStatusIsNull() {
            addCriterion("recover_status is null");
            return (Criteria) this;
        }

        public Criteria andRecoverStatusIsNotNull() {
            addCriterion("recover_status is not null");
            return (Criteria) this;
        }

        public Criteria andRecoverStatusEqualTo(Integer value) {
            addCriterion("recover_status =", value, "recoverStatus");
            return (Criteria) this;
        }

        public Criteria andRecoverStatusNotEqualTo(Integer value) {
            addCriterion("recover_status <>", value, "recoverStatus");
            return (Criteria) this;
        }

        public Criteria andRecoverStatusGreaterThan(Integer value) {
            addCriterion("recover_status >", value, "recoverStatus");
            return (Criteria) this;
        }

        public Criteria andRecoverStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("recover_status >=", value, "recoverStatus");
            return (Criteria) this;
        }

        public Criteria andRecoverStatusLessThan(Integer value) {
            addCriterion("recover_status <", value, "recoverStatus");
            return (Criteria) this;
        }

        public Criteria andRecoverStatusLessThanOrEqualTo(Integer value) {
            addCriterion("recover_status <=", value, "recoverStatus");
            return (Criteria) this;
        }

        public Criteria andRecoverStatusIn(List<Integer> values) {
            addCriterion("recover_status in", values, "recoverStatus");
            return (Criteria) this;
        }

        public Criteria andRecoverStatusNotIn(List<Integer> values) {
            addCriterion("recover_status not in", values, "recoverStatus");
            return (Criteria) this;
        }

        public Criteria andRecoverStatusBetween(Integer value1, Integer value2) {
            addCriterion("recover_status between", value1, value2, "recoverStatus");
            return (Criteria) this;
        }

        public Criteria andRecoverStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("recover_status not between", value1, value2, "recoverStatus");
            return (Criteria) this;
        }

        public Criteria andRecoverPeriodIsNull() {
            addCriterion("recover_period is null");
            return (Criteria) this;
        }

        public Criteria andRecoverPeriodIsNotNull() {
            addCriterion("recover_period is not null");
            return (Criteria) this;
        }

        public Criteria andRecoverPeriodEqualTo(Integer value) {
            addCriterion("recover_period =", value, "recoverPeriod");
            return (Criteria) this;
        }

        public Criteria andRecoverPeriodNotEqualTo(Integer value) {
            addCriterion("recover_period <>", value, "recoverPeriod");
            return (Criteria) this;
        }

        public Criteria andRecoverPeriodGreaterThan(Integer value) {
            addCriterion("recover_period >", value, "recoverPeriod");
            return (Criteria) this;
        }

        public Criteria andRecoverPeriodGreaterThanOrEqualTo(Integer value) {
            addCriterion("recover_period >=", value, "recoverPeriod");
            return (Criteria) this;
        }

        public Criteria andRecoverPeriodLessThan(Integer value) {
            addCriterion("recover_period <", value, "recoverPeriod");
            return (Criteria) this;
        }

        public Criteria andRecoverPeriodLessThanOrEqualTo(Integer value) {
            addCriterion("recover_period <=", value, "recoverPeriod");
            return (Criteria) this;
        }

        public Criteria andRecoverPeriodIn(List<Integer> values) {
            addCriterion("recover_period in", values, "recoverPeriod");
            return (Criteria) this;
        }

        public Criteria andRecoverPeriodNotIn(List<Integer> values) {
            addCriterion("recover_period not in", values, "recoverPeriod");
            return (Criteria) this;
        }

        public Criteria andRecoverPeriodBetween(Integer value1, Integer value2) {
            addCriterion("recover_period between", value1, value2, "recoverPeriod");
            return (Criteria) this;
        }

        public Criteria andRecoverPeriodNotBetween(Integer value1, Integer value2) {
            addCriterion("recover_period not between", value1, value2, "recoverPeriod");
            return (Criteria) this;
        }

        public Criteria andRecoverTimeIsNull() {
            addCriterion("recover_time is null");
            return (Criteria) this;
        }

        public Criteria andRecoverTimeIsNotNull() {
            addCriterion("recover_time is not null");
            return (Criteria) this;
        }

        public Criteria andRecoverTimeEqualTo(Integer value) {
            addCriterion("recover_time =", value, "recoverTime");
            return (Criteria) this;
        }

        public Criteria andRecoverTimeNotEqualTo(Integer value) {
            addCriterion("recover_time <>", value, "recoverTime");
            return (Criteria) this;
        }

        public Criteria andRecoverTimeGreaterThan(Integer value) {
            addCriterion("recover_time >", value, "recoverTime");
            return (Criteria) this;
        }

        public Criteria andRecoverTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("recover_time >=", value, "recoverTime");
            return (Criteria) this;
        }

        public Criteria andRecoverTimeLessThan(Integer value) {
            addCriterion("recover_time <", value, "recoverTime");
            return (Criteria) this;
        }

        public Criteria andRecoverTimeLessThanOrEqualTo(Integer value) {
            addCriterion("recover_time <=", value, "recoverTime");
            return (Criteria) this;
        }

        public Criteria andRecoverTimeIn(List<Integer> values) {
            addCriterion("recover_time in", values, "recoverTime");
            return (Criteria) this;
        }

        public Criteria andRecoverTimeNotIn(List<Integer> values) {
            addCriterion("recover_time not in", values, "recoverTime");
            return (Criteria) this;
        }

        public Criteria andRecoverTimeBetween(Integer value1, Integer value2) {
            addCriterion("recover_time between", value1, value2, "recoverTime");
            return (Criteria) this;
        }

        public Criteria andRecoverTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("recover_time not between", value1, value2, "recoverTime");
            return (Criteria) this;
        }

        public Criteria andRecoverYestimeIsNull() {
            addCriterion("recover_yestime is null");
            return (Criteria) this;
        }

        public Criteria andRecoverYestimeIsNotNull() {
            addCriterion("recover_yestime is not null");
            return (Criteria) this;
        }

        public Criteria andRecoverYestimeEqualTo(Integer value) {
            addCriterion("recover_yestime =", value, "recoverYestime");
            return (Criteria) this;
        }

        public Criteria andRecoverYestimeNotEqualTo(Integer value) {
            addCriterion("recover_yestime <>", value, "recoverYestime");
            return (Criteria) this;
        }

        public Criteria andRecoverYestimeGreaterThan(Integer value) {
            addCriterion("recover_yestime >", value, "recoverYestime");
            return (Criteria) this;
        }

        public Criteria andRecoverYestimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("recover_yestime >=", value, "recoverYestime");
            return (Criteria) this;
        }

        public Criteria andRecoverYestimeLessThan(Integer value) {
            addCriterion("recover_yestime <", value, "recoverYestime");
            return (Criteria) this;
        }

        public Criteria andRecoverYestimeLessThanOrEqualTo(Integer value) {
            addCriterion("recover_yestime <=", value, "recoverYestime");
            return (Criteria) this;
        }

        public Criteria andRecoverYestimeIn(List<Integer> values) {
            addCriterion("recover_yestime in", values, "recoverYestime");
            return (Criteria) this;
        }

        public Criteria andRecoverYestimeNotIn(List<Integer> values) {
            addCriterion("recover_yestime not in", values, "recoverYestime");
            return (Criteria) this;
        }

        public Criteria andRecoverYestimeBetween(Integer value1, Integer value2) {
            addCriterion("recover_yestime between", value1, value2, "recoverYestime");
            return (Criteria) this;
        }

        public Criteria andRecoverYestimeNotBetween(Integer value1, Integer value2) {
            addCriterion("recover_yestime not between", value1, value2, "recoverYestime");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountIsNull() {
            addCriterion("recover_account is null");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountIsNotNull() {
            addCriterion("recover_account is not null");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountEqualTo(BigDecimal value) {
            addCriterion("recover_account =", value, "recoverAccount");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountNotEqualTo(BigDecimal value) {
            addCriterion("recover_account <>", value, "recoverAccount");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountGreaterThan(BigDecimal value) {
            addCriterion("recover_account >", value, "recoverAccount");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("recover_account >=", value, "recoverAccount");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountLessThan(BigDecimal value) {
            addCriterion("recover_account <", value, "recoverAccount");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("recover_account <=", value, "recoverAccount");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountIn(List<BigDecimal> values) {
            addCriterion("recover_account in", values, "recoverAccount");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountNotIn(List<BigDecimal> values) {
            addCriterion("recover_account not in", values, "recoverAccount");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("recover_account between", value1, value2, "recoverAccount");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("recover_account not between", value1, value2, "recoverAccount");
            return (Criteria) this;
        }

        public Criteria andRecoverInterestIsNull() {
            addCriterion("recover_interest is null");
            return (Criteria) this;
        }

        public Criteria andRecoverInterestIsNotNull() {
            addCriterion("recover_interest is not null");
            return (Criteria) this;
        }

        public Criteria andRecoverInterestEqualTo(BigDecimal value) {
            addCriterion("recover_interest =", value, "recoverInterest");
            return (Criteria) this;
        }

        public Criteria andRecoverInterestNotEqualTo(BigDecimal value) {
            addCriterion("recover_interest <>", value, "recoverInterest");
            return (Criteria) this;
        }

        public Criteria andRecoverInterestGreaterThan(BigDecimal value) {
            addCriterion("recover_interest >", value, "recoverInterest");
            return (Criteria) this;
        }

        public Criteria andRecoverInterestGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("recover_interest >=", value, "recoverInterest");
            return (Criteria) this;
        }

        public Criteria andRecoverInterestLessThan(BigDecimal value) {
            addCriterion("recover_interest <", value, "recoverInterest");
            return (Criteria) this;
        }

        public Criteria andRecoverInterestLessThanOrEqualTo(BigDecimal value) {
            addCriterion("recover_interest <=", value, "recoverInterest");
            return (Criteria) this;
        }

        public Criteria andRecoverInterestIn(List<BigDecimal> values) {
            addCriterion("recover_interest in", values, "recoverInterest");
            return (Criteria) this;
        }

        public Criteria andRecoverInterestNotIn(List<BigDecimal> values) {
            addCriterion("recover_interest not in", values, "recoverInterest");
            return (Criteria) this;
        }

        public Criteria andRecoverInterestBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("recover_interest between", value1, value2, "recoverInterest");
            return (Criteria) this;
        }

        public Criteria andRecoverInterestNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("recover_interest not between", value1, value2, "recoverInterest");
            return (Criteria) this;
        }

        public Criteria andRecoverCapitalIsNull() {
            addCriterion("recover_capital is null");
            return (Criteria) this;
        }

        public Criteria andRecoverCapitalIsNotNull() {
            addCriterion("recover_capital is not null");
            return (Criteria) this;
        }

        public Criteria andRecoverCapitalEqualTo(BigDecimal value) {
            addCriterion("recover_capital =", value, "recoverCapital");
            return (Criteria) this;
        }

        public Criteria andRecoverCapitalNotEqualTo(BigDecimal value) {
            addCriterion("recover_capital <>", value, "recoverCapital");
            return (Criteria) this;
        }

        public Criteria andRecoverCapitalGreaterThan(BigDecimal value) {
            addCriterion("recover_capital >", value, "recoverCapital");
            return (Criteria) this;
        }

        public Criteria andRecoverCapitalGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("recover_capital >=", value, "recoverCapital");
            return (Criteria) this;
        }

        public Criteria andRecoverCapitalLessThan(BigDecimal value) {
            addCriterion("recover_capital <", value, "recoverCapital");
            return (Criteria) this;
        }

        public Criteria andRecoverCapitalLessThanOrEqualTo(BigDecimal value) {
            addCriterion("recover_capital <=", value, "recoverCapital");
            return (Criteria) this;
        }

        public Criteria andRecoverCapitalIn(List<BigDecimal> values) {
            addCriterion("recover_capital in", values, "recoverCapital");
            return (Criteria) this;
        }

        public Criteria andRecoverCapitalNotIn(List<BigDecimal> values) {
            addCriterion("recover_capital not in", values, "recoverCapital");
            return (Criteria) this;
        }

        public Criteria andRecoverCapitalBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("recover_capital between", value1, value2, "recoverCapital");
            return (Criteria) this;
        }

        public Criteria andRecoverCapitalNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("recover_capital not between", value1, value2, "recoverCapital");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountYesIsNull() {
            addCriterion("recover_account_yes is null");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountYesIsNotNull() {
            addCriterion("recover_account_yes is not null");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountYesEqualTo(BigDecimal value) {
            addCriterion("recover_account_yes =", value, "recoverAccountYes");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountYesNotEqualTo(BigDecimal value) {
            addCriterion("recover_account_yes <>", value, "recoverAccountYes");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountYesGreaterThan(BigDecimal value) {
            addCriterion("recover_account_yes >", value, "recoverAccountYes");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountYesGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("recover_account_yes >=", value, "recoverAccountYes");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountYesLessThan(BigDecimal value) {
            addCriterion("recover_account_yes <", value, "recoverAccountYes");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountYesLessThanOrEqualTo(BigDecimal value) {
            addCriterion("recover_account_yes <=", value, "recoverAccountYes");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountYesIn(List<BigDecimal> values) {
            addCriterion("recover_account_yes in", values, "recoverAccountYes");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountYesNotIn(List<BigDecimal> values) {
            addCriterion("recover_account_yes not in", values, "recoverAccountYes");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountYesBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("recover_account_yes between", value1, value2, "recoverAccountYes");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountYesNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("recover_account_yes not between", value1, value2, "recoverAccountYes");
            return (Criteria) this;
        }

        public Criteria andRecoverInterestYesIsNull() {
            addCriterion("recover_interest_yes is null");
            return (Criteria) this;
        }

        public Criteria andRecoverInterestYesIsNotNull() {
            addCriterion("recover_interest_yes is not null");
            return (Criteria) this;
        }

        public Criteria andRecoverInterestYesEqualTo(BigDecimal value) {
            addCriterion("recover_interest_yes =", value, "recoverInterestYes");
            return (Criteria) this;
        }

        public Criteria andRecoverInterestYesNotEqualTo(BigDecimal value) {
            addCriterion("recover_interest_yes <>", value, "recoverInterestYes");
            return (Criteria) this;
        }

        public Criteria andRecoverInterestYesGreaterThan(BigDecimal value) {
            addCriterion("recover_interest_yes >", value, "recoverInterestYes");
            return (Criteria) this;
        }

        public Criteria andRecoverInterestYesGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("recover_interest_yes >=", value, "recoverInterestYes");
            return (Criteria) this;
        }

        public Criteria andRecoverInterestYesLessThan(BigDecimal value) {
            addCriterion("recover_interest_yes <", value, "recoverInterestYes");
            return (Criteria) this;
        }

        public Criteria andRecoverInterestYesLessThanOrEqualTo(BigDecimal value) {
            addCriterion("recover_interest_yes <=", value, "recoverInterestYes");
            return (Criteria) this;
        }

        public Criteria andRecoverInterestYesIn(List<BigDecimal> values) {
            addCriterion("recover_interest_yes in", values, "recoverInterestYes");
            return (Criteria) this;
        }

        public Criteria andRecoverInterestYesNotIn(List<BigDecimal> values) {
            addCriterion("recover_interest_yes not in", values, "recoverInterestYes");
            return (Criteria) this;
        }

        public Criteria andRecoverInterestYesBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("recover_interest_yes between", value1, value2, "recoverInterestYes");
            return (Criteria) this;
        }

        public Criteria andRecoverInterestYesNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("recover_interest_yes not between", value1, value2, "recoverInterestYes");
            return (Criteria) this;
        }

        public Criteria andRecoverCapitalYesIsNull() {
            addCriterion("recover_capital_yes is null");
            return (Criteria) this;
        }

        public Criteria andRecoverCapitalYesIsNotNull() {
            addCriterion("recover_capital_yes is not null");
            return (Criteria) this;
        }

        public Criteria andRecoverCapitalYesEqualTo(BigDecimal value) {
            addCriterion("recover_capital_yes =", value, "recoverCapitalYes");
            return (Criteria) this;
        }

        public Criteria andRecoverCapitalYesNotEqualTo(BigDecimal value) {
            addCriterion("recover_capital_yes <>", value, "recoverCapitalYes");
            return (Criteria) this;
        }

        public Criteria andRecoverCapitalYesGreaterThan(BigDecimal value) {
            addCriterion("recover_capital_yes >", value, "recoverCapitalYes");
            return (Criteria) this;
        }

        public Criteria andRecoverCapitalYesGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("recover_capital_yes >=", value, "recoverCapitalYes");
            return (Criteria) this;
        }

        public Criteria andRecoverCapitalYesLessThan(BigDecimal value) {
            addCriterion("recover_capital_yes <", value, "recoverCapitalYes");
            return (Criteria) this;
        }

        public Criteria andRecoverCapitalYesLessThanOrEqualTo(BigDecimal value) {
            addCriterion("recover_capital_yes <=", value, "recoverCapitalYes");
            return (Criteria) this;
        }

        public Criteria andRecoverCapitalYesIn(List<BigDecimal> values) {
            addCriterion("recover_capital_yes in", values, "recoverCapitalYes");
            return (Criteria) this;
        }

        public Criteria andRecoverCapitalYesNotIn(List<BigDecimal> values) {
            addCriterion("recover_capital_yes not in", values, "recoverCapitalYes");
            return (Criteria) this;
        }

        public Criteria andRecoverCapitalYesBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("recover_capital_yes between", value1, value2, "recoverCapitalYes");
            return (Criteria) this;
        }

        public Criteria andRecoverCapitalYesNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("recover_capital_yes not between", value1, value2, "recoverCapitalYes");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountWaitIsNull() {
            addCriterion("recover_account_wait is null");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountWaitIsNotNull() {
            addCriterion("recover_account_wait is not null");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountWaitEqualTo(BigDecimal value) {
            addCriterion("recover_account_wait =", value, "recoverAccountWait");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountWaitNotEqualTo(BigDecimal value) {
            addCriterion("recover_account_wait <>", value, "recoverAccountWait");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountWaitGreaterThan(BigDecimal value) {
            addCriterion("recover_account_wait >", value, "recoverAccountWait");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountWaitGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("recover_account_wait >=", value, "recoverAccountWait");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountWaitLessThan(BigDecimal value) {
            addCriterion("recover_account_wait <", value, "recoverAccountWait");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountWaitLessThanOrEqualTo(BigDecimal value) {
            addCriterion("recover_account_wait <=", value, "recoverAccountWait");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountWaitIn(List<BigDecimal> values) {
            addCriterion("recover_account_wait in", values, "recoverAccountWait");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountWaitNotIn(List<BigDecimal> values) {
            addCriterion("recover_account_wait not in", values, "recoverAccountWait");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountWaitBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("recover_account_wait between", value1, value2, "recoverAccountWait");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountWaitNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("recover_account_wait not between", value1, value2, "recoverAccountWait");
            return (Criteria) this;
        }

        public Criteria andRecoverCapitalWaitIsNull() {
            addCriterion("recover_capital_wait is null");
            return (Criteria) this;
        }

        public Criteria andRecoverCapitalWaitIsNotNull() {
            addCriterion("recover_capital_wait is not null");
            return (Criteria) this;
        }

        public Criteria andRecoverCapitalWaitEqualTo(BigDecimal value) {
            addCriterion("recover_capital_wait =", value, "recoverCapitalWait");
            return (Criteria) this;
        }

        public Criteria andRecoverCapitalWaitNotEqualTo(BigDecimal value) {
            addCriterion("recover_capital_wait <>", value, "recoverCapitalWait");
            return (Criteria) this;
        }

        public Criteria andRecoverCapitalWaitGreaterThan(BigDecimal value) {
            addCriterion("recover_capital_wait >", value, "recoverCapitalWait");
            return (Criteria) this;
        }

        public Criteria andRecoverCapitalWaitGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("recover_capital_wait >=", value, "recoverCapitalWait");
            return (Criteria) this;
        }

        public Criteria andRecoverCapitalWaitLessThan(BigDecimal value) {
            addCriterion("recover_capital_wait <", value, "recoverCapitalWait");
            return (Criteria) this;
        }

        public Criteria andRecoverCapitalWaitLessThanOrEqualTo(BigDecimal value) {
            addCriterion("recover_capital_wait <=", value, "recoverCapitalWait");
            return (Criteria) this;
        }

        public Criteria andRecoverCapitalWaitIn(List<BigDecimal> values) {
            addCriterion("recover_capital_wait in", values, "recoverCapitalWait");
            return (Criteria) this;
        }

        public Criteria andRecoverCapitalWaitNotIn(List<BigDecimal> values) {
            addCriterion("recover_capital_wait not in", values, "recoverCapitalWait");
            return (Criteria) this;
        }

        public Criteria andRecoverCapitalWaitBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("recover_capital_wait between", value1, value2, "recoverCapitalWait");
            return (Criteria) this;
        }

        public Criteria andRecoverCapitalWaitNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("recover_capital_wait not between", value1, value2, "recoverCapitalWait");
            return (Criteria) this;
        }

        public Criteria andRecoverInterestWaitIsNull() {
            addCriterion("recover_interest_wait is null");
            return (Criteria) this;
        }

        public Criteria andRecoverInterestWaitIsNotNull() {
            addCriterion("recover_interest_wait is not null");
            return (Criteria) this;
        }

        public Criteria andRecoverInterestWaitEqualTo(BigDecimal value) {
            addCriterion("recover_interest_wait =", value, "recoverInterestWait");
            return (Criteria) this;
        }

        public Criteria andRecoverInterestWaitNotEqualTo(BigDecimal value) {
            addCriterion("recover_interest_wait <>", value, "recoverInterestWait");
            return (Criteria) this;
        }

        public Criteria andRecoverInterestWaitGreaterThan(BigDecimal value) {
            addCriterion("recover_interest_wait >", value, "recoverInterestWait");
            return (Criteria) this;
        }

        public Criteria andRecoverInterestWaitGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("recover_interest_wait >=", value, "recoverInterestWait");
            return (Criteria) this;
        }

        public Criteria andRecoverInterestWaitLessThan(BigDecimal value) {
            addCriterion("recover_interest_wait <", value, "recoverInterestWait");
            return (Criteria) this;
        }

        public Criteria andRecoverInterestWaitLessThanOrEqualTo(BigDecimal value) {
            addCriterion("recover_interest_wait <=", value, "recoverInterestWait");
            return (Criteria) this;
        }

        public Criteria andRecoverInterestWaitIn(List<BigDecimal> values) {
            addCriterion("recover_interest_wait in", values, "recoverInterestWait");
            return (Criteria) this;
        }

        public Criteria andRecoverInterestWaitNotIn(List<BigDecimal> values) {
            addCriterion("recover_interest_wait not in", values, "recoverInterestWait");
            return (Criteria) this;
        }

        public Criteria andRecoverInterestWaitBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("recover_interest_wait between", value1, value2, "recoverInterestWait");
            return (Criteria) this;
        }

        public Criteria andRecoverInterestWaitNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("recover_interest_wait not between", value1, value2, "recoverInterestWait");
            return (Criteria) this;
        }

        public Criteria andRecoverTypeIsNull() {
            addCriterion("recover_type is null");
            return (Criteria) this;
        }

        public Criteria andRecoverTypeIsNotNull() {
            addCriterion("recover_type is not null");
            return (Criteria) this;
        }

        public Criteria andRecoverTypeEqualTo(String value) {
            addCriterion("recover_type =", value, "recoverType");
            return (Criteria) this;
        }

        public Criteria andRecoverTypeNotEqualTo(String value) {
            addCriterion("recover_type <>", value, "recoverType");
            return (Criteria) this;
        }

        public Criteria andRecoverTypeGreaterThan(String value) {
            addCriterion("recover_type >", value, "recoverType");
            return (Criteria) this;
        }

        public Criteria andRecoverTypeGreaterThanOrEqualTo(String value) {
            addCriterion("recover_type >=", value, "recoverType");
            return (Criteria) this;
        }

        public Criteria andRecoverTypeLessThan(String value) {
            addCriterion("recover_type <", value, "recoverType");
            return (Criteria) this;
        }

        public Criteria andRecoverTypeLessThanOrEqualTo(String value) {
            addCriterion("recover_type <=", value, "recoverType");
            return (Criteria) this;
        }

        public Criteria andRecoverTypeLike(String value) {
            addCriterion("recover_type like", value, "recoverType");
            return (Criteria) this;
        }

        public Criteria andRecoverTypeNotLike(String value) {
            addCriterion("recover_type not like", value, "recoverType");
            return (Criteria) this;
        }

        public Criteria andRecoverTypeIn(List<String> values) {
            addCriterion("recover_type in", values, "recoverType");
            return (Criteria) this;
        }

        public Criteria andRecoverTypeNotIn(List<String> values) {
            addCriterion("recover_type not in", values, "recoverType");
            return (Criteria) this;
        }

        public Criteria andRecoverTypeBetween(String value1, String value2) {
            addCriterion("recover_type between", value1, value2, "recoverType");
            return (Criteria) this;
        }

        public Criteria andRecoverTypeNotBetween(String value1, String value2) {
            addCriterion("recover_type not between", value1, value2, "recoverType");
            return (Criteria) this;
        }

        public Criteria andRecoverServiceFeeIsNull() {
            addCriterion("recover_service_fee is null");
            return (Criteria) this;
        }

        public Criteria andRecoverServiceFeeIsNotNull() {
            addCriterion("recover_service_fee is not null");
            return (Criteria) this;
        }

        public Criteria andRecoverServiceFeeEqualTo(BigDecimal value) {
            addCriterion("recover_service_fee =", value, "recoverServiceFee");
            return (Criteria) this;
        }

        public Criteria andRecoverServiceFeeNotEqualTo(BigDecimal value) {
            addCriterion("recover_service_fee <>", value, "recoverServiceFee");
            return (Criteria) this;
        }

        public Criteria andRecoverServiceFeeGreaterThan(BigDecimal value) {
            addCriterion("recover_service_fee >", value, "recoverServiceFee");
            return (Criteria) this;
        }

        public Criteria andRecoverServiceFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("recover_service_fee >=", value, "recoverServiceFee");
            return (Criteria) this;
        }

        public Criteria andRecoverServiceFeeLessThan(BigDecimal value) {
            addCriterion("recover_service_fee <", value, "recoverServiceFee");
            return (Criteria) this;
        }

        public Criteria andRecoverServiceFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("recover_service_fee <=", value, "recoverServiceFee");
            return (Criteria) this;
        }

        public Criteria andRecoverServiceFeeIn(List<BigDecimal> values) {
            addCriterion("recover_service_fee in", values, "recoverServiceFee");
            return (Criteria) this;
        }

        public Criteria andRecoverServiceFeeNotIn(List<BigDecimal> values) {
            addCriterion("recover_service_fee not in", values, "recoverServiceFee");
            return (Criteria) this;
        }

        public Criteria andRecoverServiceFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("recover_service_fee between", value1, value2, "recoverServiceFee");
            return (Criteria) this;
        }

        public Criteria andRecoverServiceFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("recover_service_fee not between", value1, value2, "recoverServiceFee");
            return (Criteria) this;
        }

        public Criteria andRecoverFeeYesIsNull() {
            addCriterion("recover_fee_yes is null");
            return (Criteria) this;
        }

        public Criteria andRecoverFeeYesIsNotNull() {
            addCriterion("recover_fee_yes is not null");
            return (Criteria) this;
        }

        public Criteria andRecoverFeeYesEqualTo(BigDecimal value) {
            addCriterion("recover_fee_yes =", value, "recoverFeeYes");
            return (Criteria) this;
        }

        public Criteria andRecoverFeeYesNotEqualTo(BigDecimal value) {
            addCriterion("recover_fee_yes <>", value, "recoverFeeYes");
            return (Criteria) this;
        }

        public Criteria andRecoverFeeYesGreaterThan(BigDecimal value) {
            addCriterion("recover_fee_yes >", value, "recoverFeeYes");
            return (Criteria) this;
        }

        public Criteria andRecoverFeeYesGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("recover_fee_yes >=", value, "recoverFeeYes");
            return (Criteria) this;
        }

        public Criteria andRecoverFeeYesLessThan(BigDecimal value) {
            addCriterion("recover_fee_yes <", value, "recoverFeeYes");
            return (Criteria) this;
        }

        public Criteria andRecoverFeeYesLessThanOrEqualTo(BigDecimal value) {
            addCriterion("recover_fee_yes <=", value, "recoverFeeYes");
            return (Criteria) this;
        }

        public Criteria andRecoverFeeYesIn(List<BigDecimal> values) {
            addCriterion("recover_fee_yes in", values, "recoverFeeYes");
            return (Criteria) this;
        }

        public Criteria andRecoverFeeYesNotIn(List<BigDecimal> values) {
            addCriterion("recover_fee_yes not in", values, "recoverFeeYes");
            return (Criteria) this;
        }

        public Criteria andRecoverFeeYesBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("recover_fee_yes between", value1, value2, "recoverFeeYes");
            return (Criteria) this;
        }

        public Criteria andRecoverFeeYesNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("recover_fee_yes not between", value1, value2, "recoverFeeYes");
            return (Criteria) this;
        }

        public Criteria andRepayChargeInterestIsNull() {
            addCriterion("repay_charge_interest is null");
            return (Criteria) this;
        }

        public Criteria andRepayChargeInterestIsNotNull() {
            addCriterion("repay_charge_interest is not null");
            return (Criteria) this;
        }

        public Criteria andRepayChargeInterestEqualTo(BigDecimal value) {
            addCriterion("repay_charge_interest =", value, "repayChargeInterest");
            return (Criteria) this;
        }

        public Criteria andRepayChargeInterestNotEqualTo(BigDecimal value) {
            addCriterion("repay_charge_interest <>", value, "repayChargeInterest");
            return (Criteria) this;
        }

        public Criteria andRepayChargeInterestGreaterThan(BigDecimal value) {
            addCriterion("repay_charge_interest >", value, "repayChargeInterest");
            return (Criteria) this;
        }

        public Criteria andRepayChargeInterestGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_charge_interest >=", value, "repayChargeInterest");
            return (Criteria) this;
        }

        public Criteria andRepayChargeInterestLessThan(BigDecimal value) {
            addCriterion("repay_charge_interest <", value, "repayChargeInterest");
            return (Criteria) this;
        }

        public Criteria andRepayChargeInterestLessThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_charge_interest <=", value, "repayChargeInterest");
            return (Criteria) this;
        }

        public Criteria andRepayChargeInterestIn(List<BigDecimal> values) {
            addCriterion("repay_charge_interest in", values, "repayChargeInterest");
            return (Criteria) this;
        }

        public Criteria andRepayChargeInterestNotIn(List<BigDecimal> values) {
            addCriterion("repay_charge_interest not in", values, "repayChargeInterest");
            return (Criteria) this;
        }

        public Criteria andRepayChargeInterestBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_charge_interest between", value1, value2, "repayChargeInterest");
            return (Criteria) this;
        }

        public Criteria andRepayChargeInterestNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_charge_interest not between", value1, value2, "repayChargeInterest");
            return (Criteria) this;
        }

        public Criteria andRepayDelayInterestIsNull() {
            addCriterion("repay_delay_interest is null");
            return (Criteria) this;
        }

        public Criteria andRepayDelayInterestIsNotNull() {
            addCriterion("repay_delay_interest is not null");
            return (Criteria) this;
        }

        public Criteria andRepayDelayInterestEqualTo(BigDecimal value) {
            addCriterion("repay_delay_interest =", value, "repayDelayInterest");
            return (Criteria) this;
        }

        public Criteria andRepayDelayInterestNotEqualTo(BigDecimal value) {
            addCriterion("repay_delay_interest <>", value, "repayDelayInterest");
            return (Criteria) this;
        }

        public Criteria andRepayDelayInterestGreaterThan(BigDecimal value) {
            addCriterion("repay_delay_interest >", value, "repayDelayInterest");
            return (Criteria) this;
        }

        public Criteria andRepayDelayInterestGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_delay_interest >=", value, "repayDelayInterest");
            return (Criteria) this;
        }

        public Criteria andRepayDelayInterestLessThan(BigDecimal value) {
            addCriterion("repay_delay_interest <", value, "repayDelayInterest");
            return (Criteria) this;
        }

        public Criteria andRepayDelayInterestLessThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_delay_interest <=", value, "repayDelayInterest");
            return (Criteria) this;
        }

        public Criteria andRepayDelayInterestIn(List<BigDecimal> values) {
            addCriterion("repay_delay_interest in", values, "repayDelayInterest");
            return (Criteria) this;
        }

        public Criteria andRepayDelayInterestNotIn(List<BigDecimal> values) {
            addCriterion("repay_delay_interest not in", values, "repayDelayInterest");
            return (Criteria) this;
        }

        public Criteria andRepayDelayInterestBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_delay_interest between", value1, value2, "repayDelayInterest");
            return (Criteria) this;
        }

        public Criteria andRepayDelayInterestNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_delay_interest not between", value1, value2, "repayDelayInterest");
            return (Criteria) this;
        }

        public Criteria andCreditManageFeeIsNull() {
            addCriterion("credit_manage_fee is null");
            return (Criteria) this;
        }

        public Criteria andCreditManageFeeIsNotNull() {
            addCriterion("credit_manage_fee is not null");
            return (Criteria) this;
        }

        public Criteria andCreditManageFeeEqualTo(BigDecimal value) {
            addCriterion("credit_manage_fee =", value, "creditManageFee");
            return (Criteria) this;
        }

        public Criteria andCreditManageFeeNotEqualTo(BigDecimal value) {
            addCriterion("credit_manage_fee <>", value, "creditManageFee");
            return (Criteria) this;
        }

        public Criteria andCreditManageFeeGreaterThan(BigDecimal value) {
            addCriterion("credit_manage_fee >", value, "creditManageFee");
            return (Criteria) this;
        }

        public Criteria andCreditManageFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("credit_manage_fee >=", value, "creditManageFee");
            return (Criteria) this;
        }

        public Criteria andCreditManageFeeLessThan(BigDecimal value) {
            addCriterion("credit_manage_fee <", value, "creditManageFee");
            return (Criteria) this;
        }

        public Criteria andCreditManageFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("credit_manage_fee <=", value, "creditManageFee");
            return (Criteria) this;
        }

        public Criteria andCreditManageFeeIn(List<BigDecimal> values) {
            addCriterion("credit_manage_fee in", values, "creditManageFee");
            return (Criteria) this;
        }

        public Criteria andCreditManageFeeNotIn(List<BigDecimal> values) {
            addCriterion("credit_manage_fee not in", values, "creditManageFee");
            return (Criteria) this;
        }

        public Criteria andCreditManageFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("credit_manage_fee between", value1, value2, "creditManageFee");
            return (Criteria) this;
        }

        public Criteria andCreditManageFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("credit_manage_fee not between", value1, value2, "creditManageFee");
            return (Criteria) this;
        }

        public Criteria andRepayLateInterestIsNull() {
            addCriterion("repay_late_interest is null");
            return (Criteria) this;
        }

        public Criteria andRepayLateInterestIsNotNull() {
            addCriterion("repay_late_interest is not null");
            return (Criteria) this;
        }

        public Criteria andRepayLateInterestEqualTo(BigDecimal value) {
            addCriterion("repay_late_interest =", value, "repayLateInterest");
            return (Criteria) this;
        }

        public Criteria andRepayLateInterestNotEqualTo(BigDecimal value) {
            addCriterion("repay_late_interest <>", value, "repayLateInterest");
            return (Criteria) this;
        }

        public Criteria andRepayLateInterestGreaterThan(BigDecimal value) {
            addCriterion("repay_late_interest >", value, "repayLateInterest");
            return (Criteria) this;
        }

        public Criteria andRepayLateInterestGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_late_interest >=", value, "repayLateInterest");
            return (Criteria) this;
        }

        public Criteria andRepayLateInterestLessThan(BigDecimal value) {
            addCriterion("repay_late_interest <", value, "repayLateInterest");
            return (Criteria) this;
        }

        public Criteria andRepayLateInterestLessThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_late_interest <=", value, "repayLateInterest");
            return (Criteria) this;
        }

        public Criteria andRepayLateInterestIn(List<BigDecimal> values) {
            addCriterion("repay_late_interest in", values, "repayLateInterest");
            return (Criteria) this;
        }

        public Criteria andRepayLateInterestNotIn(List<BigDecimal> values) {
            addCriterion("repay_late_interest not in", values, "repayLateInterest");
            return (Criteria) this;
        }

        public Criteria andRepayLateInterestBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_late_interest between", value1, value2, "repayLateInterest");
            return (Criteria) this;
        }

        public Criteria andRepayLateInterestNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_late_interest not between", value1, value2, "repayLateInterest");
            return (Criteria) this;
        }

        public Criteria andDebtStatusIsNull() {
            addCriterion("debt_status is null");
            return (Criteria) this;
        }

        public Criteria andDebtStatusIsNotNull() {
            addCriterion("debt_status is not null");
            return (Criteria) this;
        }

        public Criteria andDebtStatusEqualTo(Integer value) {
            addCriterion("debt_status =", value, "debtStatus");
            return (Criteria) this;
        }

        public Criteria andDebtStatusNotEqualTo(Integer value) {
            addCriterion("debt_status <>", value, "debtStatus");
            return (Criteria) this;
        }

        public Criteria andDebtStatusGreaterThan(Integer value) {
            addCriterion("debt_status >", value, "debtStatus");
            return (Criteria) this;
        }

        public Criteria andDebtStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("debt_status >=", value, "debtStatus");
            return (Criteria) this;
        }

        public Criteria andDebtStatusLessThan(Integer value) {
            addCriterion("debt_status <", value, "debtStatus");
            return (Criteria) this;
        }

        public Criteria andDebtStatusLessThanOrEqualTo(Integer value) {
            addCriterion("debt_status <=", value, "debtStatus");
            return (Criteria) this;
        }

        public Criteria andDebtStatusIn(List<Integer> values) {
            addCriterion("debt_status in", values, "debtStatus");
            return (Criteria) this;
        }

        public Criteria andDebtStatusNotIn(List<Integer> values) {
            addCriterion("debt_status not in", values, "debtStatus");
            return (Criteria) this;
        }

        public Criteria andDebtStatusBetween(Integer value1, Integer value2) {
            addCriterion("debt_status between", value1, value2, "debtStatus");
            return (Criteria) this;
        }

        public Criteria andDebtStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("debt_status not between", value1, value2, "debtStatus");
            return (Criteria) this;
        }

        public Criteria andCreditStatusIsNull() {
            addCriterion("credit_status is null");
            return (Criteria) this;
        }

        public Criteria andCreditStatusIsNotNull() {
            addCriterion("credit_status is not null");
            return (Criteria) this;
        }

        public Criteria andCreditStatusEqualTo(Integer value) {
            addCriterion("credit_status =", value, "creditStatus");
            return (Criteria) this;
        }

        public Criteria andCreditStatusNotEqualTo(Integer value) {
            addCriterion("credit_status <>", value, "creditStatus");
            return (Criteria) this;
        }

        public Criteria andCreditStatusGreaterThan(Integer value) {
            addCriterion("credit_status >", value, "creditStatus");
            return (Criteria) this;
        }

        public Criteria andCreditStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("credit_status >=", value, "creditStatus");
            return (Criteria) this;
        }

        public Criteria andCreditStatusLessThan(Integer value) {
            addCriterion("credit_status <", value, "creditStatus");
            return (Criteria) this;
        }

        public Criteria andCreditStatusLessThanOrEqualTo(Integer value) {
            addCriterion("credit_status <=", value, "creditStatus");
            return (Criteria) this;
        }

        public Criteria andCreditStatusIn(List<Integer> values) {
            addCriterion("credit_status in", values, "creditStatus");
            return (Criteria) this;
        }

        public Criteria andCreditStatusNotIn(List<Integer> values) {
            addCriterion("credit_status not in", values, "creditStatus");
            return (Criteria) this;
        }

        public Criteria andCreditStatusBetween(Integer value1, Integer value2) {
            addCriterion("credit_status between", value1, value2, "creditStatus");
            return (Criteria) this;
        }

        public Criteria andCreditStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("credit_status not between", value1, value2, "creditStatus");
            return (Criteria) this;
        }

        public Criteria andRecoverFeeIsNull() {
            addCriterion("recover_fee is null");
            return (Criteria) this;
        }

        public Criteria andRecoverFeeIsNotNull() {
            addCriterion("recover_fee is not null");
            return (Criteria) this;
        }

        public Criteria andRecoverFeeEqualTo(BigDecimal value) {
            addCriterion("recover_fee =", value, "recoverFee");
            return (Criteria) this;
        }

        public Criteria andRecoverFeeNotEqualTo(BigDecimal value) {
            addCriterion("recover_fee <>", value, "recoverFee");
            return (Criteria) this;
        }

        public Criteria andRecoverFeeGreaterThan(BigDecimal value) {
            addCriterion("recover_fee >", value, "recoverFee");
            return (Criteria) this;
        }

        public Criteria andRecoverFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("recover_fee >=", value, "recoverFee");
            return (Criteria) this;
        }

        public Criteria andRecoverFeeLessThan(BigDecimal value) {
            addCriterion("recover_fee <", value, "recoverFee");
            return (Criteria) this;
        }

        public Criteria andRecoverFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("recover_fee <=", value, "recoverFee");
            return (Criteria) this;
        }

        public Criteria andRecoverFeeIn(List<BigDecimal> values) {
            addCriterion("recover_fee in", values, "recoverFee");
            return (Criteria) this;
        }

        public Criteria andRecoverFeeNotIn(List<BigDecimal> values) {
            addCriterion("recover_fee not in", values, "recoverFee");
            return (Criteria) this;
        }

        public Criteria andRecoverFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("recover_fee between", value1, value2, "recoverFee");
            return (Criteria) this;
        }

        public Criteria andRecoverFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("recover_fee not between", value1, value2, "recoverFee");
            return (Criteria) this;
        }

        public Criteria andRecoverLateFeeIsNull() {
            addCriterion("recover_late_fee is null");
            return (Criteria) this;
        }

        public Criteria andRecoverLateFeeIsNotNull() {
            addCriterion("recover_late_fee is not null");
            return (Criteria) this;
        }

        public Criteria andRecoverLateFeeEqualTo(BigDecimal value) {
            addCriterion("recover_late_fee =", value, "recoverLateFee");
            return (Criteria) this;
        }

        public Criteria andRecoverLateFeeNotEqualTo(BigDecimal value) {
            addCriterion("recover_late_fee <>", value, "recoverLateFee");
            return (Criteria) this;
        }

        public Criteria andRecoverLateFeeGreaterThan(BigDecimal value) {
            addCriterion("recover_late_fee >", value, "recoverLateFee");
            return (Criteria) this;
        }

        public Criteria andRecoverLateFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("recover_late_fee >=", value, "recoverLateFee");
            return (Criteria) this;
        }

        public Criteria andRecoverLateFeeLessThan(BigDecimal value) {
            addCriterion("recover_late_fee <", value, "recoverLateFee");
            return (Criteria) this;
        }

        public Criteria andRecoverLateFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("recover_late_fee <=", value, "recoverLateFee");
            return (Criteria) this;
        }

        public Criteria andRecoverLateFeeIn(List<BigDecimal> values) {
            addCriterion("recover_late_fee in", values, "recoverLateFee");
            return (Criteria) this;
        }

        public Criteria andRecoverLateFeeNotIn(List<BigDecimal> values) {
            addCriterion("recover_late_fee not in", values, "recoverLateFee");
            return (Criteria) this;
        }

        public Criteria andRecoverLateFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("recover_late_fee between", value1, value2, "recoverLateFee");
            return (Criteria) this;
        }

        public Criteria andRecoverLateFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("recover_late_fee not between", value1, value2, "recoverLateFee");
            return (Criteria) this;
        }

        public Criteria andAdvanceStatusIsNull() {
            addCriterion("advance_status is null");
            return (Criteria) this;
        }

        public Criteria andAdvanceStatusIsNotNull() {
            addCriterion("advance_status is not null");
            return (Criteria) this;
        }

        public Criteria andAdvanceStatusEqualTo(Integer value) {
            addCriterion("advance_status =", value, "advanceStatus");
            return (Criteria) this;
        }

        public Criteria andAdvanceStatusNotEqualTo(Integer value) {
            addCriterion("advance_status <>", value, "advanceStatus");
            return (Criteria) this;
        }

        public Criteria andAdvanceStatusGreaterThan(Integer value) {
            addCriterion("advance_status >", value, "advanceStatus");
            return (Criteria) this;
        }

        public Criteria andAdvanceStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("advance_status >=", value, "advanceStatus");
            return (Criteria) this;
        }

        public Criteria andAdvanceStatusLessThan(Integer value) {
            addCriterion("advance_status <", value, "advanceStatus");
            return (Criteria) this;
        }

        public Criteria andAdvanceStatusLessThanOrEqualTo(Integer value) {
            addCriterion("advance_status <=", value, "advanceStatus");
            return (Criteria) this;
        }

        public Criteria andAdvanceStatusIn(List<Integer> values) {
            addCriterion("advance_status in", values, "advanceStatus");
            return (Criteria) this;
        }

        public Criteria andAdvanceStatusNotIn(List<Integer> values) {
            addCriterion("advance_status not in", values, "advanceStatus");
            return (Criteria) this;
        }

        public Criteria andAdvanceStatusBetween(Integer value1, Integer value2) {
            addCriterion("advance_status between", value1, value2, "advanceStatus");
            return (Criteria) this;
        }

        public Criteria andAdvanceStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("advance_status not between", value1, value2, "advanceStatus");
            return (Criteria) this;
        }

        public Criteria andChargeDaysIsNull() {
            addCriterion("charge_days is null");
            return (Criteria) this;
        }

        public Criteria andChargeDaysIsNotNull() {
            addCriterion("charge_days is not null");
            return (Criteria) this;
        }

        public Criteria andChargeDaysEqualTo(Integer value) {
            addCriterion("charge_days =", value, "chargeDays");
            return (Criteria) this;
        }

        public Criteria andChargeDaysNotEqualTo(Integer value) {
            addCriterion("charge_days <>", value, "chargeDays");
            return (Criteria) this;
        }

        public Criteria andChargeDaysGreaterThan(Integer value) {
            addCriterion("charge_days >", value, "chargeDays");
            return (Criteria) this;
        }

        public Criteria andChargeDaysGreaterThanOrEqualTo(Integer value) {
            addCriterion("charge_days >=", value, "chargeDays");
            return (Criteria) this;
        }

        public Criteria andChargeDaysLessThan(Integer value) {
            addCriterion("charge_days <", value, "chargeDays");
            return (Criteria) this;
        }

        public Criteria andChargeDaysLessThanOrEqualTo(Integer value) {
            addCriterion("charge_days <=", value, "chargeDays");
            return (Criteria) this;
        }

        public Criteria andChargeDaysIn(List<Integer> values) {
            addCriterion("charge_days in", values, "chargeDays");
            return (Criteria) this;
        }

        public Criteria andChargeDaysNotIn(List<Integer> values) {
            addCriterion("charge_days not in", values, "chargeDays");
            return (Criteria) this;
        }

        public Criteria andChargeDaysBetween(Integer value1, Integer value2) {
            addCriterion("charge_days between", value1, value2, "chargeDays");
            return (Criteria) this;
        }

        public Criteria andChargeDaysNotBetween(Integer value1, Integer value2) {
            addCriterion("charge_days not between", value1, value2, "chargeDays");
            return (Criteria) this;
        }

        public Criteria andChargeInterestIsNull() {
            addCriterion("charge_interest is null");
            return (Criteria) this;
        }

        public Criteria andChargeInterestIsNotNull() {
            addCriterion("charge_interest is not null");
            return (Criteria) this;
        }

        public Criteria andChargeInterestEqualTo(BigDecimal value) {
            addCriterion("charge_interest =", value, "chargeInterest");
            return (Criteria) this;
        }

        public Criteria andChargeInterestNotEqualTo(BigDecimal value) {
            addCriterion("charge_interest <>", value, "chargeInterest");
            return (Criteria) this;
        }

        public Criteria andChargeInterestGreaterThan(BigDecimal value) {
            addCriterion("charge_interest >", value, "chargeInterest");
            return (Criteria) this;
        }

        public Criteria andChargeInterestGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("charge_interest >=", value, "chargeInterest");
            return (Criteria) this;
        }

        public Criteria andChargeInterestLessThan(BigDecimal value) {
            addCriterion("charge_interest <", value, "chargeInterest");
            return (Criteria) this;
        }

        public Criteria andChargeInterestLessThanOrEqualTo(BigDecimal value) {
            addCriterion("charge_interest <=", value, "chargeInterest");
            return (Criteria) this;
        }

        public Criteria andChargeInterestIn(List<BigDecimal> values) {
            addCriterion("charge_interest in", values, "chargeInterest");
            return (Criteria) this;
        }

        public Criteria andChargeInterestNotIn(List<BigDecimal> values) {
            addCriterion("charge_interest not in", values, "chargeInterest");
            return (Criteria) this;
        }

        public Criteria andChargeInterestBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("charge_interest between", value1, value2, "chargeInterest");
            return (Criteria) this;
        }

        public Criteria andChargeInterestNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("charge_interest not between", value1, value2, "chargeInterest");
            return (Criteria) this;
        }

        public Criteria andLateDaysIsNull() {
            addCriterion("late_days is null");
            return (Criteria) this;
        }

        public Criteria andLateDaysIsNotNull() {
            addCriterion("late_days is not null");
            return (Criteria) this;
        }

        public Criteria andLateDaysEqualTo(Integer value) {
            addCriterion("late_days =", value, "lateDays");
            return (Criteria) this;
        }

        public Criteria andLateDaysNotEqualTo(Integer value) {
            addCriterion("late_days <>", value, "lateDays");
            return (Criteria) this;
        }

        public Criteria andLateDaysGreaterThan(Integer value) {
            addCriterion("late_days >", value, "lateDays");
            return (Criteria) this;
        }

        public Criteria andLateDaysGreaterThanOrEqualTo(Integer value) {
            addCriterion("late_days >=", value, "lateDays");
            return (Criteria) this;
        }

        public Criteria andLateDaysLessThan(Integer value) {
            addCriterion("late_days <", value, "lateDays");
            return (Criteria) this;
        }

        public Criteria andLateDaysLessThanOrEqualTo(Integer value) {
            addCriterion("late_days <=", value, "lateDays");
            return (Criteria) this;
        }

        public Criteria andLateDaysIn(List<Integer> values) {
            addCriterion("late_days in", values, "lateDays");
            return (Criteria) this;
        }

        public Criteria andLateDaysNotIn(List<Integer> values) {
            addCriterion("late_days not in", values, "lateDays");
            return (Criteria) this;
        }

        public Criteria andLateDaysBetween(Integer value1, Integer value2) {
            addCriterion("late_days between", value1, value2, "lateDays");
            return (Criteria) this;
        }

        public Criteria andLateDaysNotBetween(Integer value1, Integer value2) {
            addCriterion("late_days not between", value1, value2, "lateDays");
            return (Criteria) this;
        }

        public Criteria andLateInterestIsNull() {
            addCriterion("late_interest is null");
            return (Criteria) this;
        }

        public Criteria andLateInterestIsNotNull() {
            addCriterion("late_interest is not null");
            return (Criteria) this;
        }

        public Criteria andLateInterestEqualTo(BigDecimal value) {
            addCriterion("late_interest =", value, "lateInterest");
            return (Criteria) this;
        }

        public Criteria andLateInterestNotEqualTo(BigDecimal value) {
            addCriterion("late_interest <>", value, "lateInterest");
            return (Criteria) this;
        }

        public Criteria andLateInterestGreaterThan(BigDecimal value) {
            addCriterion("late_interest >", value, "lateInterest");
            return (Criteria) this;
        }

        public Criteria andLateInterestGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("late_interest >=", value, "lateInterest");
            return (Criteria) this;
        }

        public Criteria andLateInterestLessThan(BigDecimal value) {
            addCriterion("late_interest <", value, "lateInterest");
            return (Criteria) this;
        }

        public Criteria andLateInterestLessThanOrEqualTo(BigDecimal value) {
            addCriterion("late_interest <=", value, "lateInterest");
            return (Criteria) this;
        }

        public Criteria andLateInterestIn(List<BigDecimal> values) {
            addCriterion("late_interest in", values, "lateInterest");
            return (Criteria) this;
        }

        public Criteria andLateInterestNotIn(List<BigDecimal> values) {
            addCriterion("late_interest not in", values, "lateInterest");
            return (Criteria) this;
        }

        public Criteria andLateInterestBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("late_interest between", value1, value2, "lateInterest");
            return (Criteria) this;
        }

        public Criteria andLateInterestNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("late_interest not between", value1, value2, "lateInterest");
            return (Criteria) this;
        }

        public Criteria andDelayDaysIsNull() {
            addCriterion("delay_days is null");
            return (Criteria) this;
        }

        public Criteria andDelayDaysIsNotNull() {
            addCriterion("delay_days is not null");
            return (Criteria) this;
        }

        public Criteria andDelayDaysEqualTo(Integer value) {
            addCriterion("delay_days =", value, "delayDays");
            return (Criteria) this;
        }

        public Criteria andDelayDaysNotEqualTo(Integer value) {
            addCriterion("delay_days <>", value, "delayDays");
            return (Criteria) this;
        }

        public Criteria andDelayDaysGreaterThan(Integer value) {
            addCriterion("delay_days >", value, "delayDays");
            return (Criteria) this;
        }

        public Criteria andDelayDaysGreaterThanOrEqualTo(Integer value) {
            addCriterion("delay_days >=", value, "delayDays");
            return (Criteria) this;
        }

        public Criteria andDelayDaysLessThan(Integer value) {
            addCriterion("delay_days <", value, "delayDays");
            return (Criteria) this;
        }

        public Criteria andDelayDaysLessThanOrEqualTo(Integer value) {
            addCriterion("delay_days <=", value, "delayDays");
            return (Criteria) this;
        }

        public Criteria andDelayDaysIn(List<Integer> values) {
            addCriterion("delay_days in", values, "delayDays");
            return (Criteria) this;
        }

        public Criteria andDelayDaysNotIn(List<Integer> values) {
            addCriterion("delay_days not in", values, "delayDays");
            return (Criteria) this;
        }

        public Criteria andDelayDaysBetween(Integer value1, Integer value2) {
            addCriterion("delay_days between", value1, value2, "delayDays");
            return (Criteria) this;
        }

        public Criteria andDelayDaysNotBetween(Integer value1, Integer value2) {
            addCriterion("delay_days not between", value1, value2, "delayDays");
            return (Criteria) this;
        }

        public Criteria andDelayInterestIsNull() {
            addCriterion("delay_interest is null");
            return (Criteria) this;
        }

        public Criteria andDelayInterestIsNotNull() {
            addCriterion("delay_interest is not null");
            return (Criteria) this;
        }

        public Criteria andDelayInterestEqualTo(BigDecimal value) {
            addCriterion("delay_interest =", value, "delayInterest");
            return (Criteria) this;
        }

        public Criteria andDelayInterestNotEqualTo(BigDecimal value) {
            addCriterion("delay_interest <>", value, "delayInterest");
            return (Criteria) this;
        }

        public Criteria andDelayInterestGreaterThan(BigDecimal value) {
            addCriterion("delay_interest >", value, "delayInterest");
            return (Criteria) this;
        }

        public Criteria andDelayInterestGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("delay_interest >=", value, "delayInterest");
            return (Criteria) this;
        }

        public Criteria andDelayInterestLessThan(BigDecimal value) {
            addCriterion("delay_interest <", value, "delayInterest");
            return (Criteria) this;
        }

        public Criteria andDelayInterestLessThanOrEqualTo(BigDecimal value) {
            addCriterion("delay_interest <=", value, "delayInterest");
            return (Criteria) this;
        }

        public Criteria andDelayInterestIn(List<BigDecimal> values) {
            addCriterion("delay_interest in", values, "delayInterest");
            return (Criteria) this;
        }

        public Criteria andDelayInterestNotIn(List<BigDecimal> values) {
            addCriterion("delay_interest not in", values, "delayInterest");
            return (Criteria) this;
        }

        public Criteria andDelayInterestBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("delay_interest between", value1, value2, "delayInterest");
            return (Criteria) this;
        }

        public Criteria andDelayInterestNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("delay_interest not between", value1, value2, "delayInterest");
            return (Criteria) this;
        }

        public Criteria andDelayRateIsNull() {
            addCriterion("delay_rate is null");
            return (Criteria) this;
        }

        public Criteria andDelayRateIsNotNull() {
            addCriterion("delay_rate is not null");
            return (Criteria) this;
        }

        public Criteria andDelayRateEqualTo(BigDecimal value) {
            addCriterion("delay_rate =", value, "delayRate");
            return (Criteria) this;
        }

        public Criteria andDelayRateNotEqualTo(BigDecimal value) {
            addCriterion("delay_rate <>", value, "delayRate");
            return (Criteria) this;
        }

        public Criteria andDelayRateGreaterThan(BigDecimal value) {
            addCriterion("delay_rate >", value, "delayRate");
            return (Criteria) this;
        }

        public Criteria andDelayRateGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("delay_rate >=", value, "delayRate");
            return (Criteria) this;
        }

        public Criteria andDelayRateLessThan(BigDecimal value) {
            addCriterion("delay_rate <", value, "delayRate");
            return (Criteria) this;
        }

        public Criteria andDelayRateLessThanOrEqualTo(BigDecimal value) {
            addCriterion("delay_rate <=", value, "delayRate");
            return (Criteria) this;
        }

        public Criteria andDelayRateIn(List<BigDecimal> values) {
            addCriterion("delay_rate in", values, "delayRate");
            return (Criteria) this;
        }

        public Criteria andDelayRateNotIn(List<BigDecimal> values) {
            addCriterion("delay_rate not in", values, "delayRate");
            return (Criteria) this;
        }

        public Criteria andDelayRateBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("delay_rate between", value1, value2, "delayRate");
            return (Criteria) this;
        }

        public Criteria andDelayRateNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("delay_rate not between", value1, value2, "delayRate");
            return (Criteria) this;
        }

        public Criteria andAddIpIsNull() {
            addCriterion("add_ip is null");
            return (Criteria) this;
        }

        public Criteria andAddIpIsNotNull() {
            addCriterion("add_ip is not null");
            return (Criteria) this;
        }

        public Criteria andAddIpEqualTo(String value) {
            addCriterion("add_ip =", value, "addIp");
            return (Criteria) this;
        }

        public Criteria andAddIpNotEqualTo(String value) {
            addCriterion("add_ip <>", value, "addIp");
            return (Criteria) this;
        }

        public Criteria andAddIpGreaterThan(String value) {
            addCriterion("add_ip >", value, "addIp");
            return (Criteria) this;
        }

        public Criteria andAddIpGreaterThanOrEqualTo(String value) {
            addCriterion("add_ip >=", value, "addIp");
            return (Criteria) this;
        }

        public Criteria andAddIpLessThan(String value) {
            addCriterion("add_ip <", value, "addIp");
            return (Criteria) this;
        }

        public Criteria andAddIpLessThanOrEqualTo(String value) {
            addCriterion("add_ip <=", value, "addIp");
            return (Criteria) this;
        }

        public Criteria andAddIpLike(String value) {
            addCriterion("add_ip like", value, "addIp");
            return (Criteria) this;
        }

        public Criteria andAddIpNotLike(String value) {
            addCriterion("add_ip not like", value, "addIp");
            return (Criteria) this;
        }

        public Criteria andAddIpIn(List<String> values) {
            addCriterion("add_ip in", values, "addIp");
            return (Criteria) this;
        }

        public Criteria andAddIpNotIn(List<String> values) {
            addCriterion("add_ip not in", values, "addIp");
            return (Criteria) this;
        }

        public Criteria andAddIpBetween(String value1, String value2) {
            addCriterion("add_ip between", value1, value2, "addIp");
            return (Criteria) this;
        }

        public Criteria andAddIpNotBetween(String value1, String value2) {
            addCriterion("add_ip not between", value1, value2, "addIp");
            return (Criteria) this;
        }

        public Criteria andSendmailIsNull() {
            addCriterion("sendmail is null");
            return (Criteria) this;
        }

        public Criteria andSendmailIsNotNull() {
            addCriterion("sendmail is not null");
            return (Criteria) this;
        }

        public Criteria andSendmailEqualTo(Integer value) {
            addCriterion("sendmail =", value, "sendmail");
            return (Criteria) this;
        }

        public Criteria andSendmailNotEqualTo(Integer value) {
            addCriterion("sendmail <>", value, "sendmail");
            return (Criteria) this;
        }

        public Criteria andSendmailGreaterThan(Integer value) {
            addCriterion("sendmail >", value, "sendmail");
            return (Criteria) this;
        }

        public Criteria andSendmailGreaterThanOrEqualTo(Integer value) {
            addCriterion("sendmail >=", value, "sendmail");
            return (Criteria) this;
        }

        public Criteria andSendmailLessThan(Integer value) {
            addCriterion("sendmail <", value, "sendmail");
            return (Criteria) this;
        }

        public Criteria andSendmailLessThanOrEqualTo(Integer value) {
            addCriterion("sendmail <=", value, "sendmail");
            return (Criteria) this;
        }

        public Criteria andSendmailIn(List<Integer> values) {
            addCriterion("sendmail in", values, "sendmail");
            return (Criteria) this;
        }

        public Criteria andSendmailNotIn(List<Integer> values) {
            addCriterion("sendmail not in", values, "sendmail");
            return (Criteria) this;
        }

        public Criteria andSendmailBetween(Integer value1, Integer value2) {
            addCriterion("sendmail between", value1, value2, "sendmail");
            return (Criteria) this;
        }

        public Criteria andSendmailNotBetween(Integer value1, Integer value2) {
            addCriterion("sendmail not between", value1, value2, "sendmail");
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

        public Criteria andCreditAmountIsNull() {
            addCriterion("credit_amount is null");
            return (Criteria) this;
        }

        public Criteria andCreditAmountIsNotNull() {
            addCriterion("credit_amount is not null");
            return (Criteria) this;
        }

        public Criteria andCreditAmountEqualTo(BigDecimal value) {
            addCriterion("credit_amount =", value, "creditAmount");
            return (Criteria) this;
        }

        public Criteria andCreditAmountNotEqualTo(BigDecimal value) {
            addCriterion("credit_amount <>", value, "creditAmount");
            return (Criteria) this;
        }

        public Criteria andCreditAmountGreaterThan(BigDecimal value) {
            addCriterion("credit_amount >", value, "creditAmount");
            return (Criteria) this;
        }

        public Criteria andCreditAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("credit_amount >=", value, "creditAmount");
            return (Criteria) this;
        }

        public Criteria andCreditAmountLessThan(BigDecimal value) {
            addCriterion("credit_amount <", value, "creditAmount");
            return (Criteria) this;
        }

        public Criteria andCreditAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("credit_amount <=", value, "creditAmount");
            return (Criteria) this;
        }

        public Criteria andCreditAmountIn(List<BigDecimal> values) {
            addCriterion("credit_amount in", values, "creditAmount");
            return (Criteria) this;
        }

        public Criteria andCreditAmountNotIn(List<BigDecimal> values) {
            addCriterion("credit_amount not in", values, "creditAmount");
            return (Criteria) this;
        }

        public Criteria andCreditAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("credit_amount between", value1, value2, "creditAmount");
            return (Criteria) this;
        }

        public Criteria andCreditAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("credit_amount not between", value1, value2, "creditAmount");
            return (Criteria) this;
        }

        public Criteria andCreditInterestAmountIsNull() {
            addCriterion("credit_interest_amount is null");
            return (Criteria) this;
        }

        public Criteria andCreditInterestAmountIsNotNull() {
            addCriterion("credit_interest_amount is not null");
            return (Criteria) this;
        }

        public Criteria andCreditInterestAmountEqualTo(BigDecimal value) {
            addCriterion("credit_interest_amount =", value, "creditInterestAmount");
            return (Criteria) this;
        }

        public Criteria andCreditInterestAmountNotEqualTo(BigDecimal value) {
            addCriterion("credit_interest_amount <>", value, "creditInterestAmount");
            return (Criteria) this;
        }

        public Criteria andCreditInterestAmountGreaterThan(BigDecimal value) {
            addCriterion("credit_interest_amount >", value, "creditInterestAmount");
            return (Criteria) this;
        }

        public Criteria andCreditInterestAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("credit_interest_amount >=", value, "creditInterestAmount");
            return (Criteria) this;
        }

        public Criteria andCreditInterestAmountLessThan(BigDecimal value) {
            addCriterion("credit_interest_amount <", value, "creditInterestAmount");
            return (Criteria) this;
        }

        public Criteria andCreditInterestAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("credit_interest_amount <=", value, "creditInterestAmount");
            return (Criteria) this;
        }

        public Criteria andCreditInterestAmountIn(List<BigDecimal> values) {
            addCriterion("credit_interest_amount in", values, "creditInterestAmount");
            return (Criteria) this;
        }

        public Criteria andCreditInterestAmountNotIn(List<BigDecimal> values) {
            addCriterion("credit_interest_amount not in", values, "creditInterestAmount");
            return (Criteria) this;
        }

        public Criteria andCreditInterestAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("credit_interest_amount between", value1, value2, "creditInterestAmount");
            return (Criteria) this;
        }

        public Criteria andCreditInterestAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("credit_interest_amount not between", value1, value2, "creditInterestAmount");
            return (Criteria) this;
        }

        public Criteria andCreditTimeIsNull() {
            addCriterion("credit_time is null");
            return (Criteria) this;
        }

        public Criteria andCreditTimeIsNotNull() {
            addCriterion("credit_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreditTimeEqualTo(Integer value) {
            addCriterion("credit_time =", value, "creditTime");
            return (Criteria) this;
        }

        public Criteria andCreditTimeNotEqualTo(Integer value) {
            addCriterion("credit_time <>", value, "creditTime");
            return (Criteria) this;
        }

        public Criteria andCreditTimeGreaterThan(Integer value) {
            addCriterion("credit_time >", value, "creditTime");
            return (Criteria) this;
        }

        public Criteria andCreditTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("credit_time >=", value, "creditTime");
            return (Criteria) this;
        }

        public Criteria andCreditTimeLessThan(Integer value) {
            addCriterion("credit_time <", value, "creditTime");
            return (Criteria) this;
        }

        public Criteria andCreditTimeLessThanOrEqualTo(Integer value) {
            addCriterion("credit_time <=", value, "creditTime");
            return (Criteria) this;
        }

        public Criteria andCreditTimeIn(List<Integer> values) {
            addCriterion("credit_time in", values, "creditTime");
            return (Criteria) this;
        }

        public Criteria andCreditTimeNotIn(List<Integer> values) {
            addCriterion("credit_time not in", values, "creditTime");
            return (Criteria) this;
        }

        public Criteria andCreditTimeBetween(Integer value1, Integer value2) {
            addCriterion("credit_time between", value1, value2, "creditTime");
            return (Criteria) this;
        }

        public Criteria andCreditTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("credit_time not between", value1, value2, "creditTime");
            return (Criteria) this;
        }

        public Criteria andRepayOrdidIsNull() {
            addCriterion("repay_ordid is null");
            return (Criteria) this;
        }

        public Criteria andRepayOrdidIsNotNull() {
            addCriterion("repay_ordid is not null");
            return (Criteria) this;
        }

        public Criteria andRepayOrdidEqualTo(String value) {
            addCriterion("repay_ordid =", value, "repayOrdid");
            return (Criteria) this;
        }

        public Criteria andRepayOrdidNotEqualTo(String value) {
            addCriterion("repay_ordid <>", value, "repayOrdid");
            return (Criteria) this;
        }

        public Criteria andRepayOrdidGreaterThan(String value) {
            addCriterion("repay_ordid >", value, "repayOrdid");
            return (Criteria) this;
        }

        public Criteria andRepayOrdidGreaterThanOrEqualTo(String value) {
            addCriterion("repay_ordid >=", value, "repayOrdid");
            return (Criteria) this;
        }

        public Criteria andRepayOrdidLessThan(String value) {
            addCriterion("repay_ordid <", value, "repayOrdid");
            return (Criteria) this;
        }

        public Criteria andRepayOrdidLessThanOrEqualTo(String value) {
            addCriterion("repay_ordid <=", value, "repayOrdid");
            return (Criteria) this;
        }

        public Criteria andRepayOrdidLike(String value) {
            addCriterion("repay_ordid like", value, "repayOrdid");
            return (Criteria) this;
        }

        public Criteria andRepayOrdidNotLike(String value) {
            addCriterion("repay_ordid not like", value, "repayOrdid");
            return (Criteria) this;
        }

        public Criteria andRepayOrdidIn(List<String> values) {
            addCriterion("repay_ordid in", values, "repayOrdid");
            return (Criteria) this;
        }

        public Criteria andRepayOrdidNotIn(List<String> values) {
            addCriterion("repay_ordid not in", values, "repayOrdid");
            return (Criteria) this;
        }

        public Criteria andRepayOrdidBetween(String value1, String value2) {
            addCriterion("repay_ordid between", value1, value2, "repayOrdid");
            return (Criteria) this;
        }

        public Criteria andRepayOrdidNotBetween(String value1, String value2) {
            addCriterion("repay_ordid not between", value1, value2, "repayOrdid");
            return (Criteria) this;
        }

        public Criteria andRepayOrddateIsNull() {
            addCriterion("repay_orddate is null");
            return (Criteria) this;
        }

        public Criteria andRepayOrddateIsNotNull() {
            addCriterion("repay_orddate is not null");
            return (Criteria) this;
        }

        public Criteria andRepayOrddateEqualTo(String value) {
            addCriterion("repay_orddate =", value, "repayOrddate");
            return (Criteria) this;
        }

        public Criteria andRepayOrddateNotEqualTo(String value) {
            addCriterion("repay_orddate <>", value, "repayOrddate");
            return (Criteria) this;
        }

        public Criteria andRepayOrddateGreaterThan(String value) {
            addCriterion("repay_orddate >", value, "repayOrddate");
            return (Criteria) this;
        }

        public Criteria andRepayOrddateGreaterThanOrEqualTo(String value) {
            addCriterion("repay_orddate >=", value, "repayOrddate");
            return (Criteria) this;
        }

        public Criteria andRepayOrddateLessThan(String value) {
            addCriterion("repay_orddate <", value, "repayOrddate");
            return (Criteria) this;
        }

        public Criteria andRepayOrddateLessThanOrEqualTo(String value) {
            addCriterion("repay_orddate <=", value, "repayOrddate");
            return (Criteria) this;
        }

        public Criteria andRepayOrddateLike(String value) {
            addCriterion("repay_orddate like", value, "repayOrddate");
            return (Criteria) this;
        }

        public Criteria andRepayOrddateNotLike(String value) {
            addCriterion("repay_orddate not like", value, "repayOrddate");
            return (Criteria) this;
        }

        public Criteria andRepayOrddateIn(List<String> values) {
            addCriterion("repay_orddate in", values, "repayOrddate");
            return (Criteria) this;
        }

        public Criteria andRepayOrddateNotIn(List<String> values) {
            addCriterion("repay_orddate not in", values, "repayOrddate");
            return (Criteria) this;
        }

        public Criteria andRepayOrddateBetween(String value1, String value2) {
            addCriterion("repay_orddate between", value1, value2, "repayOrddate");
            return (Criteria) this;
        }

        public Criteria andRepayOrddateNotBetween(String value1, String value2) {
            addCriterion("repay_orddate not between", value1, value2, "repayOrddate");
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

        public Criteria andTenderUserAttributeIsNull() {
            addCriterion("tender_user_attribute is null");
            return (Criteria) this;
        }

        public Criteria andTenderUserAttributeIsNotNull() {
            addCriterion("tender_user_attribute is not null");
            return (Criteria) this;
        }

        public Criteria andTenderUserAttributeEqualTo(Integer value) {
            addCriterion("tender_user_attribute =", value, "tenderUserAttribute");
            return (Criteria) this;
        }

        public Criteria andTenderUserAttributeNotEqualTo(Integer value) {
            addCriterion("tender_user_attribute <>", value, "tenderUserAttribute");
            return (Criteria) this;
        }

        public Criteria andTenderUserAttributeGreaterThan(Integer value) {
            addCriterion("tender_user_attribute >", value, "tenderUserAttribute");
            return (Criteria) this;
        }

        public Criteria andTenderUserAttributeGreaterThanOrEqualTo(Integer value) {
            addCriterion("tender_user_attribute >=", value, "tenderUserAttribute");
            return (Criteria) this;
        }

        public Criteria andTenderUserAttributeLessThan(Integer value) {
            addCriterion("tender_user_attribute <", value, "tenderUserAttribute");
            return (Criteria) this;
        }

        public Criteria andTenderUserAttributeLessThanOrEqualTo(Integer value) {
            addCriterion("tender_user_attribute <=", value, "tenderUserAttribute");
            return (Criteria) this;
        }

        public Criteria andTenderUserAttributeIn(List<Integer> values) {
            addCriterion("tender_user_attribute in", values, "tenderUserAttribute");
            return (Criteria) this;
        }

        public Criteria andTenderUserAttributeNotIn(List<Integer> values) {
            addCriterion("tender_user_attribute not in", values, "tenderUserAttribute");
            return (Criteria) this;
        }

        public Criteria andTenderUserAttributeBetween(Integer value1, Integer value2) {
            addCriterion("tender_user_attribute between", value1, value2, "tenderUserAttribute");
            return (Criteria) this;
        }

        public Criteria andTenderUserAttributeNotBetween(Integer value1, Integer value2) {
            addCriterion("tender_user_attribute not between", value1, value2, "tenderUserAttribute");
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

        public Criteria andLoanBatchNoIsNull() {
            addCriterion("loan_batch_no is null");
            return (Criteria) this;
        }

        public Criteria andLoanBatchNoIsNotNull() {
            addCriterion("loan_batch_no is not null");
            return (Criteria) this;
        }

        public Criteria andLoanBatchNoEqualTo(String value) {
            addCriterion("loan_batch_no =", value, "loanBatchNo");
            return (Criteria) this;
        }

        public Criteria andLoanBatchNoNotEqualTo(String value) {
            addCriterion("loan_batch_no <>", value, "loanBatchNo");
            return (Criteria) this;
        }

        public Criteria andLoanBatchNoGreaterThan(String value) {
            addCriterion("loan_batch_no >", value, "loanBatchNo");
            return (Criteria) this;
        }

        public Criteria andLoanBatchNoGreaterThanOrEqualTo(String value) {
            addCriterion("loan_batch_no >=", value, "loanBatchNo");
            return (Criteria) this;
        }

        public Criteria andLoanBatchNoLessThan(String value) {
            addCriterion("loan_batch_no <", value, "loanBatchNo");
            return (Criteria) this;
        }

        public Criteria andLoanBatchNoLessThanOrEqualTo(String value) {
            addCriterion("loan_batch_no <=", value, "loanBatchNo");
            return (Criteria) this;
        }

        public Criteria andLoanBatchNoLike(String value) {
            addCriterion("loan_batch_no like", value, "loanBatchNo");
            return (Criteria) this;
        }

        public Criteria andLoanBatchNoNotLike(String value) {
            addCriterion("loan_batch_no not like", value, "loanBatchNo");
            return (Criteria) this;
        }

        public Criteria andLoanBatchNoIn(List<String> values) {
            addCriterion("loan_batch_no in", values, "loanBatchNo");
            return (Criteria) this;
        }

        public Criteria andLoanBatchNoNotIn(List<String> values) {
            addCriterion("loan_batch_no not in", values, "loanBatchNo");
            return (Criteria) this;
        }

        public Criteria andLoanBatchNoBetween(String value1, String value2) {
            addCriterion("loan_batch_no between", value1, value2, "loanBatchNo");
            return (Criteria) this;
        }

        public Criteria andLoanBatchNoNotBetween(String value1, String value2) {
            addCriterion("loan_batch_no not between", value1, value2, "loanBatchNo");
            return (Criteria) this;
        }

        public Criteria andRepayBatchNoIsNull() {
            addCriterion("repay_batch_no is null");
            return (Criteria) this;
        }

        public Criteria andRepayBatchNoIsNotNull() {
            addCriterion("repay_batch_no is not null");
            return (Criteria) this;
        }

        public Criteria andRepayBatchNoEqualTo(String value) {
            addCriterion("repay_batch_no =", value, "repayBatchNo");
            return (Criteria) this;
        }

        public Criteria andRepayBatchNoNotEqualTo(String value) {
            addCriterion("repay_batch_no <>", value, "repayBatchNo");
            return (Criteria) this;
        }

        public Criteria andRepayBatchNoGreaterThan(String value) {
            addCriterion("repay_batch_no >", value, "repayBatchNo");
            return (Criteria) this;
        }

        public Criteria andRepayBatchNoGreaterThanOrEqualTo(String value) {
            addCriterion("repay_batch_no >=", value, "repayBatchNo");
            return (Criteria) this;
        }

        public Criteria andRepayBatchNoLessThan(String value) {
            addCriterion("repay_batch_no <", value, "repayBatchNo");
            return (Criteria) this;
        }

        public Criteria andRepayBatchNoLessThanOrEqualTo(String value) {
            addCriterion("repay_batch_no <=", value, "repayBatchNo");
            return (Criteria) this;
        }

        public Criteria andRepayBatchNoLike(String value) {
            addCriterion("repay_batch_no like", value, "repayBatchNo");
            return (Criteria) this;
        }

        public Criteria andRepayBatchNoNotLike(String value) {
            addCriterion("repay_batch_no not like", value, "repayBatchNo");
            return (Criteria) this;
        }

        public Criteria andRepayBatchNoIn(List<String> values) {
            addCriterion("repay_batch_no in", values, "repayBatchNo");
            return (Criteria) this;
        }

        public Criteria andRepayBatchNoNotIn(List<String> values) {
            addCriterion("repay_batch_no not in", values, "repayBatchNo");
            return (Criteria) this;
        }

        public Criteria andRepayBatchNoBetween(String value1, String value2) {
            addCriterion("repay_batch_no between", value1, value2, "repayBatchNo");
            return (Criteria) this;
        }

        public Criteria andRepayBatchNoNotBetween(String value1, String value2) {
            addCriterion("repay_batch_no not between", value1, value2, "repayBatchNo");
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

        public Criteria andChargePenaltyInterestIsNull() {
            addCriterion("charge_penalty_interest is null");
            return (Criteria) this;
        }

        public Criteria andChargePenaltyInterestIsNotNull() {
            addCriterion("charge_penalty_interest is not null");
            return (Criteria) this;
        }

        public Criteria andChargePenaltyInterestEqualTo(BigDecimal value) {
            addCriterion("charge_penalty_interest =", value, "chargePenaltyInterest");
            return (Criteria) this;
        }

        public Criteria andChargePenaltyInterestNotEqualTo(BigDecimal value) {
            addCriterion("charge_penalty_interest <>", value, "chargePenaltyInterest");
            return (Criteria) this;
        }

        public Criteria andChargePenaltyInterestGreaterThan(BigDecimal value) {
            addCriterion("charge_penalty_interest >", value, "chargePenaltyInterest");
            return (Criteria) this;
        }

        public Criteria andChargePenaltyInterestGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("charge_penalty_interest >=", value, "chargePenaltyInterest");
            return (Criteria) this;
        }

        public Criteria andChargePenaltyInterestLessThan(BigDecimal value) {
            addCriterion("charge_penalty_interest <", value, "chargePenaltyInterest");
            return (Criteria) this;
        }

        public Criteria andChargePenaltyInterestLessThanOrEqualTo(BigDecimal value) {
            addCriterion("charge_penalty_interest <=", value, "chargePenaltyInterest");
            return (Criteria) this;
        }

        public Criteria andChargePenaltyInterestIn(List<BigDecimal> values) {
            addCriterion("charge_penalty_interest in", values, "chargePenaltyInterest");
            return (Criteria) this;
        }

        public Criteria andChargePenaltyInterestNotIn(List<BigDecimal> values) {
            addCriterion("charge_penalty_interest not in", values, "chargePenaltyInterest");
            return (Criteria) this;
        }

        public Criteria andChargePenaltyInterestBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("charge_penalty_interest between", value1, value2, "chargePenaltyInterest");
            return (Criteria) this;
        }

        public Criteria andChargePenaltyInterestNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("charge_penalty_interest not between", value1, value2, "chargePenaltyInterest");
            return (Criteria) this;
        }

        public Criteria andRepayChargePenaltyInterestIsNull() {
            addCriterion("repay_charge_penalty_interest is null");
            return (Criteria) this;
        }

        public Criteria andRepayChargePenaltyInterestIsNotNull() {
            addCriterion("repay_charge_penalty_interest is not null");
            return (Criteria) this;
        }

        public Criteria andRepayChargePenaltyInterestEqualTo(BigDecimal value) {
            addCriterion("repay_charge_penalty_interest =", value, "repayChargePenaltyInterest");
            return (Criteria) this;
        }

        public Criteria andRepayChargePenaltyInterestNotEqualTo(BigDecimal value) {
            addCriterion("repay_charge_penalty_interest <>", value, "repayChargePenaltyInterest");
            return (Criteria) this;
        }

        public Criteria andRepayChargePenaltyInterestGreaterThan(BigDecimal value) {
            addCriterion("repay_charge_penalty_interest >", value, "repayChargePenaltyInterest");
            return (Criteria) this;
        }

        public Criteria andRepayChargePenaltyInterestGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_charge_penalty_interest >=", value, "repayChargePenaltyInterest");
            return (Criteria) this;
        }

        public Criteria andRepayChargePenaltyInterestLessThan(BigDecimal value) {
            addCriterion("repay_charge_penalty_interest <", value, "repayChargePenaltyInterest");
            return (Criteria) this;
        }

        public Criteria andRepayChargePenaltyInterestLessThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_charge_penalty_interest <=", value, "repayChargePenaltyInterest");
            return (Criteria) this;
        }

        public Criteria andRepayChargePenaltyInterestIn(List<BigDecimal> values) {
            addCriterion("repay_charge_penalty_interest in", values, "repayChargePenaltyInterest");
            return (Criteria) this;
        }

        public Criteria andRepayChargePenaltyInterestNotIn(List<BigDecimal> values) {
            addCriterion("repay_charge_penalty_interest not in", values, "repayChargePenaltyInterest");
            return (Criteria) this;
        }

        public Criteria andRepayChargePenaltyInterestBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_charge_penalty_interest between", value1, value2, "repayChargePenaltyInterest");
            return (Criteria) this;
        }

        public Criteria andRepayChargePenaltyInterestNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_charge_penalty_interest not between", value1, value2, "repayChargePenaltyInterest");
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