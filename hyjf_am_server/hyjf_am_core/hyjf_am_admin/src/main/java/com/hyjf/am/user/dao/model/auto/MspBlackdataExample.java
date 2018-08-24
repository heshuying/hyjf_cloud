package com.hyjf.am.user.dao.model.auto;

import java.util.ArrayList;
import java.util.List;

public class MspBlackdataExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public MspBlackdataExample() {
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

        public Criteria andApplyIdIsNull() {
            addCriterion("apply_id is null");
            return (Criteria) this;
        }

        public Criteria andApplyIdIsNotNull() {
            addCriterion("apply_id is not null");
            return (Criteria) this;
        }

        public Criteria andApplyIdEqualTo(String value) {
            addCriterion("apply_id =", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdNotEqualTo(String value) {
            addCriterion("apply_id <>", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdGreaterThan(String value) {
            addCriterion("apply_id >", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdGreaterThanOrEqualTo(String value) {
            addCriterion("apply_id >=", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdLessThan(String value) {
            addCriterion("apply_id <", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdLessThanOrEqualTo(String value) {
            addCriterion("apply_id <=", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdLike(String value) {
            addCriterion("apply_id like", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdNotLike(String value) {
            addCriterion("apply_id not like", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdIn(List<String> values) {
            addCriterion("apply_id in", values, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdNotIn(List<String> values) {
            addCriterion("apply_id not in", values, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdBetween(String value1, String value2) {
            addCriterion("apply_id between", value1, value2, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdNotBetween(String value1, String value2) {
            addCriterion("apply_id not between", value1, value2, "applyId");
            return (Criteria) this;
        }

        public Criteria andCreatedateIsNull() {
            addCriterion("createDate is null");
            return (Criteria) this;
        }

        public Criteria andCreatedateIsNotNull() {
            addCriterion("createDate is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedateEqualTo(String value) {
            addCriterion("createDate =", value, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateNotEqualTo(String value) {
            addCriterion("createDate <>", value, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateGreaterThan(String value) {
            addCriterion("createDate >", value, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateGreaterThanOrEqualTo(String value) {
            addCriterion("createDate >=", value, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateLessThan(String value) {
            addCriterion("createDate <", value, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateLessThanOrEqualTo(String value) {
            addCriterion("createDate <=", value, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateLike(String value) {
            addCriterion("createDate like", value, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateNotLike(String value) {
            addCriterion("createDate not like", value, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateIn(List<String> values) {
            addCriterion("createDate in", values, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateNotIn(List<String> values) {
            addCriterion("createDate not in", values, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateBetween(String value1, String value2) {
            addCriterion("createDate between", value1, value2, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateNotBetween(String value1, String value2) {
            addCriterion("createDate not between", value1, value2, "createdate");
            return (Criteria) this;
        }

        public Criteria andLastoverduedateIsNull() {
            addCriterion("lastOverdueDate is null");
            return (Criteria) this;
        }

        public Criteria andLastoverduedateIsNotNull() {
            addCriterion("lastOverdueDate is not null");
            return (Criteria) this;
        }

        public Criteria andLastoverduedateEqualTo(String value) {
            addCriterion("lastOverdueDate =", value, "lastoverduedate");
            return (Criteria) this;
        }

        public Criteria andLastoverduedateNotEqualTo(String value) {
            addCriterion("lastOverdueDate <>", value, "lastoverduedate");
            return (Criteria) this;
        }

        public Criteria andLastoverduedateGreaterThan(String value) {
            addCriterion("lastOverdueDate >", value, "lastoverduedate");
            return (Criteria) this;
        }

        public Criteria andLastoverduedateGreaterThanOrEqualTo(String value) {
            addCriterion("lastOverdueDate >=", value, "lastoverduedate");
            return (Criteria) this;
        }

        public Criteria andLastoverduedateLessThan(String value) {
            addCriterion("lastOverdueDate <", value, "lastoverduedate");
            return (Criteria) this;
        }

        public Criteria andLastoverduedateLessThanOrEqualTo(String value) {
            addCriterion("lastOverdueDate <=", value, "lastoverduedate");
            return (Criteria) this;
        }

        public Criteria andLastoverduedateLike(String value) {
            addCriterion("lastOverdueDate like", value, "lastoverduedate");
            return (Criteria) this;
        }

        public Criteria andLastoverduedateNotLike(String value) {
            addCriterion("lastOverdueDate not like", value, "lastoverduedate");
            return (Criteria) this;
        }

        public Criteria andLastoverduedateIn(List<String> values) {
            addCriterion("lastOverdueDate in", values, "lastoverduedate");
            return (Criteria) this;
        }

        public Criteria andLastoverduedateNotIn(List<String> values) {
            addCriterion("lastOverdueDate not in", values, "lastoverduedate");
            return (Criteria) this;
        }

        public Criteria andLastoverduedateBetween(String value1, String value2) {
            addCriterion("lastOverdueDate between", value1, value2, "lastoverduedate");
            return (Criteria) this;
        }

        public Criteria andLastoverduedateNotBetween(String value1, String value2) {
            addCriterion("lastOverdueDate not between", value1, value2, "lastoverduedate");
            return (Criteria) this;
        }

        public Criteria andCreditaddressIsNull() {
            addCriterion("creditAddress is null");
            return (Criteria) this;
        }

        public Criteria andCreditaddressIsNotNull() {
            addCriterion("creditAddress is not null");
            return (Criteria) this;
        }

        public Criteria andCreditaddressEqualTo(String value) {
            addCriterion("creditAddress =", value, "creditaddress");
            return (Criteria) this;
        }

        public Criteria andCreditaddressNotEqualTo(String value) {
            addCriterion("creditAddress <>", value, "creditaddress");
            return (Criteria) this;
        }

        public Criteria andCreditaddressGreaterThan(String value) {
            addCriterion("creditAddress >", value, "creditaddress");
            return (Criteria) this;
        }

        public Criteria andCreditaddressGreaterThanOrEqualTo(String value) {
            addCriterion("creditAddress >=", value, "creditaddress");
            return (Criteria) this;
        }

        public Criteria andCreditaddressLessThan(String value) {
            addCriterion("creditAddress <", value, "creditaddress");
            return (Criteria) this;
        }

        public Criteria andCreditaddressLessThanOrEqualTo(String value) {
            addCriterion("creditAddress <=", value, "creditaddress");
            return (Criteria) this;
        }

        public Criteria andCreditaddressLike(String value) {
            addCriterion("creditAddress like", value, "creditaddress");
            return (Criteria) this;
        }

        public Criteria andCreditaddressNotLike(String value) {
            addCriterion("creditAddress not like", value, "creditaddress");
            return (Criteria) this;
        }

        public Criteria andCreditaddressIn(List<String> values) {
            addCriterion("creditAddress in", values, "creditaddress");
            return (Criteria) this;
        }

        public Criteria andCreditaddressNotIn(List<String> values) {
            addCriterion("creditAddress not in", values, "creditaddress");
            return (Criteria) this;
        }

        public Criteria andCreditaddressBetween(String value1, String value2) {
            addCriterion("creditAddress between", value1, value2, "creditaddress");
            return (Criteria) this;
        }

        public Criteria andCreditaddressNotBetween(String value1, String value2) {
            addCriterion("creditAddress not between", value1, value2, "creditaddress");
            return (Criteria) this;
        }

        public Criteria andArrearsIsNull() {
            addCriterion("arrears is null");
            return (Criteria) this;
        }

        public Criteria andArrearsIsNotNull() {
            addCriterion("arrears is not null");
            return (Criteria) this;
        }

        public Criteria andArrearsEqualTo(String value) {
            addCriterion("arrears =", value, "arrears");
            return (Criteria) this;
        }

        public Criteria andArrearsNotEqualTo(String value) {
            addCriterion("arrears <>", value, "arrears");
            return (Criteria) this;
        }

        public Criteria andArrearsGreaterThan(String value) {
            addCriterion("arrears >", value, "arrears");
            return (Criteria) this;
        }

        public Criteria andArrearsGreaterThanOrEqualTo(String value) {
            addCriterion("arrears >=", value, "arrears");
            return (Criteria) this;
        }

        public Criteria andArrearsLessThan(String value) {
            addCriterion("arrears <", value, "arrears");
            return (Criteria) this;
        }

        public Criteria andArrearsLessThanOrEqualTo(String value) {
            addCriterion("arrears <=", value, "arrears");
            return (Criteria) this;
        }

        public Criteria andArrearsLike(String value) {
            addCriterion("arrears like", value, "arrears");
            return (Criteria) this;
        }

        public Criteria andArrearsNotLike(String value) {
            addCriterion("arrears not like", value, "arrears");
            return (Criteria) this;
        }

        public Criteria andArrearsIn(List<String> values) {
            addCriterion("arrears in", values, "arrears");
            return (Criteria) this;
        }

        public Criteria andArrearsNotIn(List<String> values) {
            addCriterion("arrears not in", values, "arrears");
            return (Criteria) this;
        }

        public Criteria andArrearsBetween(String value1, String value2) {
            addCriterion("arrears between", value1, value2, "arrears");
            return (Criteria) this;
        }

        public Criteria andArrearsNotBetween(String value1, String value2) {
            addCriterion("arrears not between", value1, value2, "arrears");
            return (Criteria) this;
        }

        public Criteria andOverduedaysIsNull() {
            addCriterion("overdueDays is null");
            return (Criteria) this;
        }

        public Criteria andOverduedaysIsNotNull() {
            addCriterion("overdueDays is not null");
            return (Criteria) this;
        }

        public Criteria andOverduedaysEqualTo(String value) {
            addCriterion("overdueDays =", value, "overduedays");
            return (Criteria) this;
        }

        public Criteria andOverduedaysNotEqualTo(String value) {
            addCriterion("overdueDays <>", value, "overduedays");
            return (Criteria) this;
        }

        public Criteria andOverduedaysGreaterThan(String value) {
            addCriterion("overdueDays >", value, "overduedays");
            return (Criteria) this;
        }

        public Criteria andOverduedaysGreaterThanOrEqualTo(String value) {
            addCriterion("overdueDays >=", value, "overduedays");
            return (Criteria) this;
        }

        public Criteria andOverduedaysLessThan(String value) {
            addCriterion("overdueDays <", value, "overduedays");
            return (Criteria) this;
        }

        public Criteria andOverduedaysLessThanOrEqualTo(String value) {
            addCriterion("overdueDays <=", value, "overduedays");
            return (Criteria) this;
        }

        public Criteria andOverduedaysLike(String value) {
            addCriterion("overdueDays like", value, "overduedays");
            return (Criteria) this;
        }

        public Criteria andOverduedaysNotLike(String value) {
            addCriterion("overdueDays not like", value, "overduedays");
            return (Criteria) this;
        }

        public Criteria andOverduedaysIn(List<String> values) {
            addCriterion("overdueDays in", values, "overduedays");
            return (Criteria) this;
        }

        public Criteria andOverduedaysNotIn(List<String> values) {
            addCriterion("overdueDays not in", values, "overduedays");
            return (Criteria) this;
        }

        public Criteria andOverduedaysBetween(String value1, String value2) {
            addCriterion("overdueDays between", value1, value2, "overduedays");
            return (Criteria) this;
        }

        public Criteria andOverduedaysNotBetween(String value1, String value2) {
            addCriterion("overdueDays not between", value1, value2, "overduedays");
            return (Criteria) this;
        }

        public Criteria andPhoneIsNull() {
            addCriterion("phone is null");
            return (Criteria) this;
        }

        public Criteria andPhoneIsNotNull() {
            addCriterion("phone is not null");
            return (Criteria) this;
        }

        public Criteria andPhoneEqualTo(String value) {
            addCriterion("phone =", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotEqualTo(String value) {
            addCriterion("phone <>", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneGreaterThan(String value) {
            addCriterion("phone >", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("phone >=", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLessThan(String value) {
            addCriterion("phone <", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLessThanOrEqualTo(String value) {
            addCriterion("phone <=", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLike(String value) {
            addCriterion("phone like", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotLike(String value) {
            addCriterion("phone not like", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneIn(List<String> values) {
            addCriterion("phone in", values, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotIn(List<String> values) {
            addCriterion("phone not in", values, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneBetween(String value1, String value2) {
            addCriterion("phone between", value1, value2, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotBetween(String value1, String value2) {
            addCriterion("phone not between", value1, value2, "phone");
            return (Criteria) this;
        }

        public Criteria andEmailIsNull() {
            addCriterion("email is null");
            return (Criteria) this;
        }

        public Criteria andEmailIsNotNull() {
            addCriterion("email is not null");
            return (Criteria) this;
        }

        public Criteria andEmailEqualTo(String value) {
            addCriterion("email =", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotEqualTo(String value) {
            addCriterion("email <>", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThan(String value) {
            addCriterion("email >", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThanOrEqualTo(String value) {
            addCriterion("email >=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThan(String value) {
            addCriterion("email <", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThanOrEqualTo(String value) {
            addCriterion("email <=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLike(String value) {
            addCriterion("email like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotLike(String value) {
            addCriterion("email not like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailIn(List<String> values) {
            addCriterion("email in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotIn(List<String> values) {
            addCriterion("email not in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailBetween(String value1, String value2) {
            addCriterion("email between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotBetween(String value1, String value2) {
            addCriterion("email not between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andResidenceaddressIsNull() {
            addCriterion("residenceAddress is null");
            return (Criteria) this;
        }

        public Criteria andResidenceaddressIsNotNull() {
            addCriterion("residenceAddress is not null");
            return (Criteria) this;
        }

        public Criteria andResidenceaddressEqualTo(String value) {
            addCriterion("residenceAddress =", value, "residenceaddress");
            return (Criteria) this;
        }

        public Criteria andResidenceaddressNotEqualTo(String value) {
            addCriterion("residenceAddress <>", value, "residenceaddress");
            return (Criteria) this;
        }

        public Criteria andResidenceaddressGreaterThan(String value) {
            addCriterion("residenceAddress >", value, "residenceaddress");
            return (Criteria) this;
        }

        public Criteria andResidenceaddressGreaterThanOrEqualTo(String value) {
            addCriterion("residenceAddress >=", value, "residenceaddress");
            return (Criteria) this;
        }

        public Criteria andResidenceaddressLessThan(String value) {
            addCriterion("residenceAddress <", value, "residenceaddress");
            return (Criteria) this;
        }

        public Criteria andResidenceaddressLessThanOrEqualTo(String value) {
            addCriterion("residenceAddress <=", value, "residenceaddress");
            return (Criteria) this;
        }

        public Criteria andResidenceaddressLike(String value) {
            addCriterion("residenceAddress like", value, "residenceaddress");
            return (Criteria) this;
        }

        public Criteria andResidenceaddressNotLike(String value) {
            addCriterion("residenceAddress not like", value, "residenceaddress");
            return (Criteria) this;
        }

        public Criteria andResidenceaddressIn(List<String> values) {
            addCriterion("residenceAddress in", values, "residenceaddress");
            return (Criteria) this;
        }

        public Criteria andResidenceaddressNotIn(List<String> values) {
            addCriterion("residenceAddress not in", values, "residenceaddress");
            return (Criteria) this;
        }

        public Criteria andResidenceaddressBetween(String value1, String value2) {
            addCriterion("residenceAddress between", value1, value2, "residenceaddress");
            return (Criteria) this;
        }

        public Criteria andResidenceaddressNotBetween(String value1, String value2) {
            addCriterion("residenceAddress not between", value1, value2, "residenceaddress");
            return (Criteria) this;
        }

        public Criteria andCurrentaddressIsNull() {
            addCriterion("currentAddress is null");
            return (Criteria) this;
        }

        public Criteria andCurrentaddressIsNotNull() {
            addCriterion("currentAddress is not null");
            return (Criteria) this;
        }

        public Criteria andCurrentaddressEqualTo(String value) {
            addCriterion("currentAddress =", value, "currentaddress");
            return (Criteria) this;
        }

        public Criteria andCurrentaddressNotEqualTo(String value) {
            addCriterion("currentAddress <>", value, "currentaddress");
            return (Criteria) this;
        }

        public Criteria andCurrentaddressGreaterThan(String value) {
            addCriterion("currentAddress >", value, "currentaddress");
            return (Criteria) this;
        }

        public Criteria andCurrentaddressGreaterThanOrEqualTo(String value) {
            addCriterion("currentAddress >=", value, "currentaddress");
            return (Criteria) this;
        }

        public Criteria andCurrentaddressLessThan(String value) {
            addCriterion("currentAddress <", value, "currentaddress");
            return (Criteria) this;
        }

        public Criteria andCurrentaddressLessThanOrEqualTo(String value) {
            addCriterion("currentAddress <=", value, "currentaddress");
            return (Criteria) this;
        }

        public Criteria andCurrentaddressLike(String value) {
            addCriterion("currentAddress like", value, "currentaddress");
            return (Criteria) this;
        }

        public Criteria andCurrentaddressNotLike(String value) {
            addCriterion("currentAddress not like", value, "currentaddress");
            return (Criteria) this;
        }

        public Criteria andCurrentaddressIn(List<String> values) {
            addCriterion("currentAddress in", values, "currentaddress");
            return (Criteria) this;
        }

        public Criteria andCurrentaddressNotIn(List<String> values) {
            addCriterion("currentAddress not in", values, "currentaddress");
            return (Criteria) this;
        }

        public Criteria andCurrentaddressBetween(String value1, String value2) {
            addCriterion("currentAddress between", value1, value2, "currentaddress");
            return (Criteria) this;
        }

        public Criteria andCurrentaddressNotBetween(String value1, String value2) {
            addCriterion("currentAddress not between", value1, value2, "currentaddress");
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