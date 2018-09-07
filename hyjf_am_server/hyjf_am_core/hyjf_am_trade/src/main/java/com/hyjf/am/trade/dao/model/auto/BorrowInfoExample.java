package com.hyjf.am.trade.dao.model.auto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BorrowInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public BorrowInfoExample() {
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

        public Criteria andBorrowPreNidIsNull() {
            addCriterion("borrow_pre_nid is null");
            return (Criteria) this;
        }

        public Criteria andBorrowPreNidIsNotNull() {
            addCriterion("borrow_pre_nid is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowPreNidEqualTo(String value) {
            addCriterion("borrow_pre_nid =", value, "borrowPreNid");
            return (Criteria) this;
        }

        public Criteria andBorrowPreNidNotEqualTo(String value) {
            addCriterion("borrow_pre_nid <>", value, "borrowPreNid");
            return (Criteria) this;
        }

        public Criteria andBorrowPreNidGreaterThan(String value) {
            addCriterion("borrow_pre_nid >", value, "borrowPreNid");
            return (Criteria) this;
        }

        public Criteria andBorrowPreNidGreaterThanOrEqualTo(String value) {
            addCriterion("borrow_pre_nid >=", value, "borrowPreNid");
            return (Criteria) this;
        }

        public Criteria andBorrowPreNidLessThan(String value) {
            addCriterion("borrow_pre_nid <", value, "borrowPreNid");
            return (Criteria) this;
        }

        public Criteria andBorrowPreNidLessThanOrEqualTo(String value) {
            addCriterion("borrow_pre_nid <=", value, "borrowPreNid");
            return (Criteria) this;
        }

        public Criteria andBorrowPreNidLike(String value) {
            addCriterion("borrow_pre_nid like", value, "borrowPreNid");
            return (Criteria) this;
        }

        public Criteria andBorrowPreNidNotLike(String value) {
            addCriterion("borrow_pre_nid not like", value, "borrowPreNid");
            return (Criteria) this;
        }

        public Criteria andBorrowPreNidIn(List<String> values) {
            addCriterion("borrow_pre_nid in", values, "borrowPreNid");
            return (Criteria) this;
        }

        public Criteria andBorrowPreNidNotIn(List<String> values) {
            addCriterion("borrow_pre_nid not in", values, "borrowPreNid");
            return (Criteria) this;
        }

        public Criteria andBorrowPreNidBetween(String value1, String value2) {
            addCriterion("borrow_pre_nid between", value1, value2, "borrowPreNid");
            return (Criteria) this;
        }

        public Criteria andBorrowPreNidNotBetween(String value1, String value2) {
            addCriterion("borrow_pre_nid not between", value1, value2, "borrowPreNid");
            return (Criteria) this;
        }

        public Criteria andBorrowPreNidNewIsNull() {
            addCriterion("borrow_pre_nid_new is null");
            return (Criteria) this;
        }

        public Criteria andBorrowPreNidNewIsNotNull() {
            addCriterion("borrow_pre_nid_new is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowPreNidNewEqualTo(String value) {
            addCriterion("borrow_pre_nid_new =", value, "borrowPreNidNew");
            return (Criteria) this;
        }

        public Criteria andBorrowPreNidNewNotEqualTo(String value) {
            addCriterion("borrow_pre_nid_new <>", value, "borrowPreNidNew");
            return (Criteria) this;
        }

        public Criteria andBorrowPreNidNewGreaterThan(String value) {
            addCriterion("borrow_pre_nid_new >", value, "borrowPreNidNew");
            return (Criteria) this;
        }

        public Criteria andBorrowPreNidNewGreaterThanOrEqualTo(String value) {
            addCriterion("borrow_pre_nid_new >=", value, "borrowPreNidNew");
            return (Criteria) this;
        }

        public Criteria andBorrowPreNidNewLessThan(String value) {
            addCriterion("borrow_pre_nid_new <", value, "borrowPreNidNew");
            return (Criteria) this;
        }

        public Criteria andBorrowPreNidNewLessThanOrEqualTo(String value) {
            addCriterion("borrow_pre_nid_new <=", value, "borrowPreNidNew");
            return (Criteria) this;
        }

        public Criteria andBorrowPreNidNewLike(String value) {
            addCriterion("borrow_pre_nid_new like", value, "borrowPreNidNew");
            return (Criteria) this;
        }

        public Criteria andBorrowPreNidNewNotLike(String value) {
            addCriterion("borrow_pre_nid_new not like", value, "borrowPreNidNew");
            return (Criteria) this;
        }

        public Criteria andBorrowPreNidNewIn(List<String> values) {
            addCriterion("borrow_pre_nid_new in", values, "borrowPreNidNew");
            return (Criteria) this;
        }

        public Criteria andBorrowPreNidNewNotIn(List<String> values) {
            addCriterion("borrow_pre_nid_new not in", values, "borrowPreNidNew");
            return (Criteria) this;
        }

        public Criteria andBorrowPreNidNewBetween(String value1, String value2) {
            addCriterion("borrow_pre_nid_new between", value1, value2, "borrowPreNidNew");
            return (Criteria) this;
        }

        public Criteria andBorrowPreNidNewNotBetween(String value1, String value2) {
            addCriterion("borrow_pre_nid_new not between", value1, value2, "borrowPreNidNew");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("`name` is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("`name` is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("`name` =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("`name` <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("`name` >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("`name` >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("`name` <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("`name` <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("`name` like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("`name` not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("`name` in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("`name` not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("`name` between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("`name` not between", value1, value2, "name");
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

        public Criteria andApplicantIsNull() {
            addCriterion("applicant is null");
            return (Criteria) this;
        }

        public Criteria andApplicantIsNotNull() {
            addCriterion("applicant is not null");
            return (Criteria) this;
        }

        public Criteria andApplicantEqualTo(String value) {
            addCriterion("applicant =", value, "applicant");
            return (Criteria) this;
        }

        public Criteria andApplicantNotEqualTo(String value) {
            addCriterion("applicant <>", value, "applicant");
            return (Criteria) this;
        }

        public Criteria andApplicantGreaterThan(String value) {
            addCriterion("applicant >", value, "applicant");
            return (Criteria) this;
        }

        public Criteria andApplicantGreaterThanOrEqualTo(String value) {
            addCriterion("applicant >=", value, "applicant");
            return (Criteria) this;
        }

        public Criteria andApplicantLessThan(String value) {
            addCriterion("applicant <", value, "applicant");
            return (Criteria) this;
        }

        public Criteria andApplicantLessThanOrEqualTo(String value) {
            addCriterion("applicant <=", value, "applicant");
            return (Criteria) this;
        }

        public Criteria andApplicantLike(String value) {
            addCriterion("applicant like", value, "applicant");
            return (Criteria) this;
        }

        public Criteria andApplicantNotLike(String value) {
            addCriterion("applicant not like", value, "applicant");
            return (Criteria) this;
        }

        public Criteria andApplicantIn(List<String> values) {
            addCriterion("applicant in", values, "applicant");
            return (Criteria) this;
        }

        public Criteria andApplicantNotIn(List<String> values) {
            addCriterion("applicant not in", values, "applicant");
            return (Criteria) this;
        }

        public Criteria andApplicantBetween(String value1, String value2) {
            addCriterion("applicant between", value1, value2, "applicant");
            return (Criteria) this;
        }

        public Criteria andApplicantNotBetween(String value1, String value2) {
            addCriterion("applicant not between", value1, value2, "applicant");
            return (Criteria) this;
        }

        public Criteria andRepayOrgNameIsNull() {
            addCriterion("repay_org_name is null");
            return (Criteria) this;
        }

        public Criteria andRepayOrgNameIsNotNull() {
            addCriterion("repay_org_name is not null");
            return (Criteria) this;
        }

        public Criteria andRepayOrgNameEqualTo(String value) {
            addCriterion("repay_org_name =", value, "repayOrgName");
            return (Criteria) this;
        }

        public Criteria andRepayOrgNameNotEqualTo(String value) {
            addCriterion("repay_org_name <>", value, "repayOrgName");
            return (Criteria) this;
        }

        public Criteria andRepayOrgNameGreaterThan(String value) {
            addCriterion("repay_org_name >", value, "repayOrgName");
            return (Criteria) this;
        }

        public Criteria andRepayOrgNameGreaterThanOrEqualTo(String value) {
            addCriterion("repay_org_name >=", value, "repayOrgName");
            return (Criteria) this;
        }

        public Criteria andRepayOrgNameLessThan(String value) {
            addCriterion("repay_org_name <", value, "repayOrgName");
            return (Criteria) this;
        }

        public Criteria andRepayOrgNameLessThanOrEqualTo(String value) {
            addCriterion("repay_org_name <=", value, "repayOrgName");
            return (Criteria) this;
        }

        public Criteria andRepayOrgNameLike(String value) {
            addCriterion("repay_org_name like", value, "repayOrgName");
            return (Criteria) this;
        }

        public Criteria andRepayOrgNameNotLike(String value) {
            addCriterion("repay_org_name not like", value, "repayOrgName");
            return (Criteria) this;
        }

        public Criteria andRepayOrgNameIn(List<String> values) {
            addCriterion("repay_org_name in", values, "repayOrgName");
            return (Criteria) this;
        }

        public Criteria andRepayOrgNameNotIn(List<String> values) {
            addCriterion("repay_org_name not in", values, "repayOrgName");
            return (Criteria) this;
        }

        public Criteria andRepayOrgNameBetween(String value1, String value2) {
            addCriterion("repay_org_name between", value1, value2, "repayOrgName");
            return (Criteria) this;
        }

        public Criteria andRepayOrgNameNotBetween(String value1, String value2) {
            addCriterion("repay_org_name not between", value1, value2, "repayOrgName");
            return (Criteria) this;
        }

        public Criteria andIsRepayOrgFlagIsNull() {
            addCriterion("is_repay_org_flag is null");
            return (Criteria) this;
        }

        public Criteria andIsRepayOrgFlagIsNotNull() {
            addCriterion("is_repay_org_flag is not null");
            return (Criteria) this;
        }

        public Criteria andIsRepayOrgFlagEqualTo(Integer value) {
            addCriterion("is_repay_org_flag =", value, "isRepayOrgFlag");
            return (Criteria) this;
        }

        public Criteria andIsRepayOrgFlagNotEqualTo(Integer value) {
            addCriterion("is_repay_org_flag <>", value, "isRepayOrgFlag");
            return (Criteria) this;
        }

        public Criteria andIsRepayOrgFlagGreaterThan(Integer value) {
            addCriterion("is_repay_org_flag >", value, "isRepayOrgFlag");
            return (Criteria) this;
        }

        public Criteria andIsRepayOrgFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_repay_org_flag >=", value, "isRepayOrgFlag");
            return (Criteria) this;
        }

        public Criteria andIsRepayOrgFlagLessThan(Integer value) {
            addCriterion("is_repay_org_flag <", value, "isRepayOrgFlag");
            return (Criteria) this;
        }

        public Criteria andIsRepayOrgFlagLessThanOrEqualTo(Integer value) {
            addCriterion("is_repay_org_flag <=", value, "isRepayOrgFlag");
            return (Criteria) this;
        }

        public Criteria andIsRepayOrgFlagIn(List<Integer> values) {
            addCriterion("is_repay_org_flag in", values, "isRepayOrgFlag");
            return (Criteria) this;
        }

        public Criteria andIsRepayOrgFlagNotIn(List<Integer> values) {
            addCriterion("is_repay_org_flag not in", values, "isRepayOrgFlag");
            return (Criteria) this;
        }

        public Criteria andIsRepayOrgFlagBetween(Integer value1, Integer value2) {
            addCriterion("is_repay_org_flag between", value1, value2, "isRepayOrgFlag");
            return (Criteria) this;
        }

        public Criteria andIsRepayOrgFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("is_repay_org_flag not between", value1, value2, "isRepayOrgFlag");
            return (Criteria) this;
        }

        public Criteria andRepayOrgUserIdIsNull() {
            addCriterion("repay_org_user_id is null");
            return (Criteria) this;
        }

        public Criteria andRepayOrgUserIdIsNotNull() {
            addCriterion("repay_org_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andRepayOrgUserIdEqualTo(Integer value) {
            addCriterion("repay_org_user_id =", value, "repayOrgUserId");
            return (Criteria) this;
        }

        public Criteria andRepayOrgUserIdNotEqualTo(Integer value) {
            addCriterion("repay_org_user_id <>", value, "repayOrgUserId");
            return (Criteria) this;
        }

        public Criteria andRepayOrgUserIdGreaterThan(Integer value) {
            addCriterion("repay_org_user_id >", value, "repayOrgUserId");
            return (Criteria) this;
        }

        public Criteria andRepayOrgUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("repay_org_user_id >=", value, "repayOrgUserId");
            return (Criteria) this;
        }

        public Criteria andRepayOrgUserIdLessThan(Integer value) {
            addCriterion("repay_org_user_id <", value, "repayOrgUserId");
            return (Criteria) this;
        }

        public Criteria andRepayOrgUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("repay_org_user_id <=", value, "repayOrgUserId");
            return (Criteria) this;
        }

        public Criteria andRepayOrgUserIdIn(List<Integer> values) {
            addCriterion("repay_org_user_id in", values, "repayOrgUserId");
            return (Criteria) this;
        }

        public Criteria andRepayOrgUserIdNotIn(List<Integer> values) {
            addCriterion("repay_org_user_id not in", values, "repayOrgUserId");
            return (Criteria) this;
        }

        public Criteria andRepayOrgUserIdBetween(Integer value1, Integer value2) {
            addCriterion("repay_org_user_id between", value1, value2, "repayOrgUserId");
            return (Criteria) this;
        }

        public Criteria andRepayOrgUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("repay_org_user_id not between", value1, value2, "repayOrgUserId");
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

        public Criteria andTypeEqualTo(String value) {
            addCriterion("`type` =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(String value) {
            addCriterion("`type` <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(String value) {
            addCriterion("`type` >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(String value) {
            addCriterion("`type` >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(String value) {
            addCriterion("`type` <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(String value) {
            addCriterion("`type` <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLike(String value) {
            addCriterion("`type` like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotLike(String value) {
            addCriterion("`type` not like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<String> values) {
            addCriterion("`type` in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<String> values) {
            addCriterion("`type` not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(String value1, String value2) {
            addCriterion("`type` between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(String value1, String value2) {
            addCriterion("`type` not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andBorrowUseIsNull() {
            addCriterion("borrow_use is null");
            return (Criteria) this;
        }

        public Criteria andBorrowUseIsNotNull() {
            addCriterion("borrow_use is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowUseEqualTo(String value) {
            addCriterion("borrow_use =", value, "borrowUse");
            return (Criteria) this;
        }

        public Criteria andBorrowUseNotEqualTo(String value) {
            addCriterion("borrow_use <>", value, "borrowUse");
            return (Criteria) this;
        }

        public Criteria andBorrowUseGreaterThan(String value) {
            addCriterion("borrow_use >", value, "borrowUse");
            return (Criteria) this;
        }

        public Criteria andBorrowUseGreaterThanOrEqualTo(String value) {
            addCriterion("borrow_use >=", value, "borrowUse");
            return (Criteria) this;
        }

        public Criteria andBorrowUseLessThan(String value) {
            addCriterion("borrow_use <", value, "borrowUse");
            return (Criteria) this;
        }

        public Criteria andBorrowUseLessThanOrEqualTo(String value) {
            addCriterion("borrow_use <=", value, "borrowUse");
            return (Criteria) this;
        }

        public Criteria andBorrowUseLike(String value) {
            addCriterion("borrow_use like", value, "borrowUse");
            return (Criteria) this;
        }

        public Criteria andBorrowUseNotLike(String value) {
            addCriterion("borrow_use not like", value, "borrowUse");
            return (Criteria) this;
        }

        public Criteria andBorrowUseIn(List<String> values) {
            addCriterion("borrow_use in", values, "borrowUse");
            return (Criteria) this;
        }

        public Criteria andBorrowUseNotIn(List<String> values) {
            addCriterion("borrow_use not in", values, "borrowUse");
            return (Criteria) this;
        }

        public Criteria andBorrowUseBetween(String value1, String value2) {
            addCriterion("borrow_use between", value1, value2, "borrowUse");
            return (Criteria) this;
        }

        public Criteria andBorrowUseNotBetween(String value1, String value2) {
            addCriterion("borrow_use not between", value1, value2, "borrowUse");
            return (Criteria) this;
        }

        public Criteria andBorrowValidTimeIsNull() {
            addCriterion("borrow_valid_time is null");
            return (Criteria) this;
        }

        public Criteria andBorrowValidTimeIsNotNull() {
            addCriterion("borrow_valid_time is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowValidTimeEqualTo(Integer value) {
            addCriterion("borrow_valid_time =", value, "borrowValidTime");
            return (Criteria) this;
        }

        public Criteria andBorrowValidTimeNotEqualTo(Integer value) {
            addCriterion("borrow_valid_time <>", value, "borrowValidTime");
            return (Criteria) this;
        }

        public Criteria andBorrowValidTimeGreaterThan(Integer value) {
            addCriterion("borrow_valid_time >", value, "borrowValidTime");
            return (Criteria) this;
        }

        public Criteria andBorrowValidTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("borrow_valid_time >=", value, "borrowValidTime");
            return (Criteria) this;
        }

        public Criteria andBorrowValidTimeLessThan(Integer value) {
            addCriterion("borrow_valid_time <", value, "borrowValidTime");
            return (Criteria) this;
        }

        public Criteria andBorrowValidTimeLessThanOrEqualTo(Integer value) {
            addCriterion("borrow_valid_time <=", value, "borrowValidTime");
            return (Criteria) this;
        }

        public Criteria andBorrowValidTimeIn(List<Integer> values) {
            addCriterion("borrow_valid_time in", values, "borrowValidTime");
            return (Criteria) this;
        }

        public Criteria andBorrowValidTimeNotIn(List<Integer> values) {
            addCriterion("borrow_valid_time not in", values, "borrowValidTime");
            return (Criteria) this;
        }

        public Criteria andBorrowValidTimeBetween(Integer value1, Integer value2) {
            addCriterion("borrow_valid_time between", value1, value2, "borrowValidTime");
            return (Criteria) this;
        }

        public Criteria andBorrowValidTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("borrow_valid_time not between", value1, value2, "borrowValidTime");
            return (Criteria) this;
        }

        public Criteria andInstCodeIsNull() {
            addCriterion("inst_code is null");
            return (Criteria) this;
        }

        public Criteria andInstCodeIsNotNull() {
            addCriterion("inst_code is not null");
            return (Criteria) this;
        }

        public Criteria andInstCodeEqualTo(String value) {
            addCriterion("inst_code =", value, "instCode");
            return (Criteria) this;
        }

        public Criteria andInstCodeNotEqualTo(String value) {
            addCriterion("inst_code <>", value, "instCode");
            return (Criteria) this;
        }

        public Criteria andInstCodeGreaterThan(String value) {
            addCriterion("inst_code >", value, "instCode");
            return (Criteria) this;
        }

        public Criteria andInstCodeGreaterThanOrEqualTo(String value) {
            addCriterion("inst_code >=", value, "instCode");
            return (Criteria) this;
        }

        public Criteria andInstCodeLessThan(String value) {
            addCriterion("inst_code <", value, "instCode");
            return (Criteria) this;
        }

        public Criteria andInstCodeLessThanOrEqualTo(String value) {
            addCriterion("inst_code <=", value, "instCode");
            return (Criteria) this;
        }

        public Criteria andInstCodeLike(String value) {
            addCriterion("inst_code like", value, "instCode");
            return (Criteria) this;
        }

        public Criteria andInstCodeNotLike(String value) {
            addCriterion("inst_code not like", value, "instCode");
            return (Criteria) this;
        }

        public Criteria andInstCodeIn(List<String> values) {
            addCriterion("inst_code in", values, "instCode");
            return (Criteria) this;
        }

        public Criteria andInstCodeNotIn(List<String> values) {
            addCriterion("inst_code not in", values, "instCode");
            return (Criteria) this;
        }

        public Criteria andInstCodeBetween(String value1, String value2) {
            addCriterion("inst_code between", value1, value2, "instCode");
            return (Criteria) this;
        }

        public Criteria andInstCodeNotBetween(String value1, String value2) {
            addCriterion("inst_code not between", value1, value2, "instCode");
            return (Criteria) this;
        }

        public Criteria andAssetTypeIsNull() {
            addCriterion("asset_type is null");
            return (Criteria) this;
        }

        public Criteria andAssetTypeIsNotNull() {
            addCriterion("asset_type is not null");
            return (Criteria) this;
        }

        public Criteria andAssetTypeEqualTo(Integer value) {
            addCriterion("asset_type =", value, "assetType");
            return (Criteria) this;
        }

        public Criteria andAssetTypeNotEqualTo(Integer value) {
            addCriterion("asset_type <>", value, "assetType");
            return (Criteria) this;
        }

        public Criteria andAssetTypeGreaterThan(Integer value) {
            addCriterion("asset_type >", value, "assetType");
            return (Criteria) this;
        }

        public Criteria andAssetTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("asset_type >=", value, "assetType");
            return (Criteria) this;
        }

        public Criteria andAssetTypeLessThan(Integer value) {
            addCriterion("asset_type <", value, "assetType");
            return (Criteria) this;
        }

        public Criteria andAssetTypeLessThanOrEqualTo(Integer value) {
            addCriterion("asset_type <=", value, "assetType");
            return (Criteria) this;
        }

        public Criteria andAssetTypeIn(List<Integer> values) {
            addCriterion("asset_type in", values, "assetType");
            return (Criteria) this;
        }

        public Criteria andAssetTypeNotIn(List<Integer> values) {
            addCriterion("asset_type not in", values, "assetType");
            return (Criteria) this;
        }

        public Criteria andAssetTypeBetween(Integer value1, Integer value2) {
            addCriterion("asset_type between", value1, value2, "assetType");
            return (Criteria) this;
        }

        public Criteria andAssetTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("asset_type not between", value1, value2, "assetType");
            return (Criteria) this;
        }

        public Criteria andAssetTypeNameIsNull() {
            addCriterion("asset_type_name is null");
            return (Criteria) this;
        }

        public Criteria andAssetTypeNameIsNotNull() {
            addCriterion("asset_type_name is not null");
            return (Criteria) this;
        }

        public Criteria andAssetTypeNameEqualTo(String value) {
            addCriterion("asset_type_name =", value, "assetTypeName");
            return (Criteria) this;
        }

        public Criteria andAssetTypeNameNotEqualTo(String value) {
            addCriterion("asset_type_name <>", value, "assetTypeName");
            return (Criteria) this;
        }

        public Criteria andAssetTypeNameGreaterThan(String value) {
            addCriterion("asset_type_name >", value, "assetTypeName");
            return (Criteria) this;
        }

        public Criteria andAssetTypeNameGreaterThanOrEqualTo(String value) {
            addCriterion("asset_type_name >=", value, "assetTypeName");
            return (Criteria) this;
        }

        public Criteria andAssetTypeNameLessThan(String value) {
            addCriterion("asset_type_name <", value, "assetTypeName");
            return (Criteria) this;
        }

        public Criteria andAssetTypeNameLessThanOrEqualTo(String value) {
            addCriterion("asset_type_name <=", value, "assetTypeName");
            return (Criteria) this;
        }

        public Criteria andAssetTypeNameLike(String value) {
            addCriterion("asset_type_name like", value, "assetTypeName");
            return (Criteria) this;
        }

        public Criteria andAssetTypeNameNotLike(String value) {
            addCriterion("asset_type_name not like", value, "assetTypeName");
            return (Criteria) this;
        }

        public Criteria andAssetTypeNameIn(List<String> values) {
            addCriterion("asset_type_name in", values, "assetTypeName");
            return (Criteria) this;
        }

        public Criteria andAssetTypeNameNotIn(List<String> values) {
            addCriterion("asset_type_name not in", values, "assetTypeName");
            return (Criteria) this;
        }

        public Criteria andAssetTypeNameBetween(String value1, String value2) {
            addCriterion("asset_type_name between", value1, value2, "assetTypeName");
            return (Criteria) this;
        }

        public Criteria andAssetTypeNameNotBetween(String value1, String value2) {
            addCriterion("asset_type_name not between", value1, value2, "assetTypeName");
            return (Criteria) this;
        }

        public Criteria andEntrustedFlgIsNull() {
            addCriterion("entrusted_flg is null");
            return (Criteria) this;
        }

        public Criteria andEntrustedFlgIsNotNull() {
            addCriterion("entrusted_flg is not null");
            return (Criteria) this;
        }

        public Criteria andEntrustedFlgEqualTo(Integer value) {
            addCriterion("entrusted_flg =", value, "entrustedFlg");
            return (Criteria) this;
        }

        public Criteria andEntrustedFlgNotEqualTo(Integer value) {
            addCriterion("entrusted_flg <>", value, "entrustedFlg");
            return (Criteria) this;
        }

        public Criteria andEntrustedFlgGreaterThan(Integer value) {
            addCriterion("entrusted_flg >", value, "entrustedFlg");
            return (Criteria) this;
        }

        public Criteria andEntrustedFlgGreaterThanOrEqualTo(Integer value) {
            addCriterion("entrusted_flg >=", value, "entrustedFlg");
            return (Criteria) this;
        }

        public Criteria andEntrustedFlgLessThan(Integer value) {
            addCriterion("entrusted_flg <", value, "entrustedFlg");
            return (Criteria) this;
        }

        public Criteria andEntrustedFlgLessThanOrEqualTo(Integer value) {
            addCriterion("entrusted_flg <=", value, "entrustedFlg");
            return (Criteria) this;
        }

        public Criteria andEntrustedFlgIn(List<Integer> values) {
            addCriterion("entrusted_flg in", values, "entrustedFlg");
            return (Criteria) this;
        }

        public Criteria andEntrustedFlgNotIn(List<Integer> values) {
            addCriterion("entrusted_flg not in", values, "entrustedFlg");
            return (Criteria) this;
        }

        public Criteria andEntrustedFlgBetween(Integer value1, Integer value2) {
            addCriterion("entrusted_flg between", value1, value2, "entrustedFlg");
            return (Criteria) this;
        }

        public Criteria andEntrustedFlgNotBetween(Integer value1, Integer value2) {
            addCriterion("entrusted_flg not between", value1, value2, "entrustedFlg");
            return (Criteria) this;
        }

        public Criteria andEntrustedUserNameIsNull() {
            addCriterion("entrusted_user_name is null");
            return (Criteria) this;
        }

        public Criteria andEntrustedUserNameIsNotNull() {
            addCriterion("entrusted_user_name is not null");
            return (Criteria) this;
        }

        public Criteria andEntrustedUserNameEqualTo(String value) {
            addCriterion("entrusted_user_name =", value, "entrustedUserName");
            return (Criteria) this;
        }

        public Criteria andEntrustedUserNameNotEqualTo(String value) {
            addCriterion("entrusted_user_name <>", value, "entrustedUserName");
            return (Criteria) this;
        }

        public Criteria andEntrustedUserNameGreaterThan(String value) {
            addCriterion("entrusted_user_name >", value, "entrustedUserName");
            return (Criteria) this;
        }

        public Criteria andEntrustedUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("entrusted_user_name >=", value, "entrustedUserName");
            return (Criteria) this;
        }

        public Criteria andEntrustedUserNameLessThan(String value) {
            addCriterion("entrusted_user_name <", value, "entrustedUserName");
            return (Criteria) this;
        }

        public Criteria andEntrustedUserNameLessThanOrEqualTo(String value) {
            addCriterion("entrusted_user_name <=", value, "entrustedUserName");
            return (Criteria) this;
        }

        public Criteria andEntrustedUserNameLike(String value) {
            addCriterion("entrusted_user_name like", value, "entrustedUserName");
            return (Criteria) this;
        }

        public Criteria andEntrustedUserNameNotLike(String value) {
            addCriterion("entrusted_user_name not like", value, "entrustedUserName");
            return (Criteria) this;
        }

        public Criteria andEntrustedUserNameIn(List<String> values) {
            addCriterion("entrusted_user_name in", values, "entrustedUserName");
            return (Criteria) this;
        }

        public Criteria andEntrustedUserNameNotIn(List<String> values) {
            addCriterion("entrusted_user_name not in", values, "entrustedUserName");
            return (Criteria) this;
        }

        public Criteria andEntrustedUserNameBetween(String value1, String value2) {
            addCriterion("entrusted_user_name between", value1, value2, "entrustedUserName");
            return (Criteria) this;
        }

        public Criteria andEntrustedUserNameNotBetween(String value1, String value2) {
            addCriterion("entrusted_user_name not between", value1, value2, "entrustedUserName");
            return (Criteria) this;
        }

        public Criteria andEntrustedUserIdIsNull() {
            addCriterion("entrusted_user_id is null");
            return (Criteria) this;
        }

        public Criteria andEntrustedUserIdIsNotNull() {
            addCriterion("entrusted_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andEntrustedUserIdEqualTo(Integer value) {
            addCriterion("entrusted_user_id =", value, "entrustedUserId");
            return (Criteria) this;
        }

        public Criteria andEntrustedUserIdNotEqualTo(Integer value) {
            addCriterion("entrusted_user_id <>", value, "entrustedUserId");
            return (Criteria) this;
        }

        public Criteria andEntrustedUserIdGreaterThan(Integer value) {
            addCriterion("entrusted_user_id >", value, "entrustedUserId");
            return (Criteria) this;
        }

        public Criteria andEntrustedUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("entrusted_user_id >=", value, "entrustedUserId");
            return (Criteria) this;
        }

        public Criteria andEntrustedUserIdLessThan(Integer value) {
            addCriterion("entrusted_user_id <", value, "entrustedUserId");
            return (Criteria) this;
        }

        public Criteria andEntrustedUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("entrusted_user_id <=", value, "entrustedUserId");
            return (Criteria) this;
        }

        public Criteria andEntrustedUserIdIn(List<Integer> values) {
            addCriterion("entrusted_user_id in", values, "entrustedUserId");
            return (Criteria) this;
        }

        public Criteria andEntrustedUserIdNotIn(List<Integer> values) {
            addCriterion("entrusted_user_id not in", values, "entrustedUserId");
            return (Criteria) this;
        }

        public Criteria andEntrustedUserIdBetween(Integer value1, Integer value2) {
            addCriterion("entrusted_user_id between", value1, value2, "entrustedUserId");
            return (Criteria) this;
        }

        public Criteria andEntrustedUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("entrusted_user_id not between", value1, value2, "entrustedUserId");
            return (Criteria) this;
        }

        public Criteria andTrusteePayTimeIsNull() {
            addCriterion("trustee_pay_time is null");
            return (Criteria) this;
        }

        public Criteria andTrusteePayTimeIsNotNull() {
            addCriterion("trustee_pay_time is not null");
            return (Criteria) this;
        }

        public Criteria andTrusteePayTimeEqualTo(Integer value) {
            addCriterion("trustee_pay_time =", value, "trusteePayTime");
            return (Criteria) this;
        }

        public Criteria andTrusteePayTimeNotEqualTo(Integer value) {
            addCriterion("trustee_pay_time <>", value, "trusteePayTime");
            return (Criteria) this;
        }

        public Criteria andTrusteePayTimeGreaterThan(Integer value) {
            addCriterion("trustee_pay_time >", value, "trusteePayTime");
            return (Criteria) this;
        }

        public Criteria andTrusteePayTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("trustee_pay_time >=", value, "trusteePayTime");
            return (Criteria) this;
        }

        public Criteria andTrusteePayTimeLessThan(Integer value) {
            addCriterion("trustee_pay_time <", value, "trusteePayTime");
            return (Criteria) this;
        }

        public Criteria andTrusteePayTimeLessThanOrEqualTo(Integer value) {
            addCriterion("trustee_pay_time <=", value, "trusteePayTime");
            return (Criteria) this;
        }

        public Criteria andTrusteePayTimeIn(List<Integer> values) {
            addCriterion("trustee_pay_time in", values, "trusteePayTime");
            return (Criteria) this;
        }

        public Criteria andTrusteePayTimeNotIn(List<Integer> values) {
            addCriterion("trustee_pay_time not in", values, "trusteePayTime");
            return (Criteria) this;
        }

        public Criteria andTrusteePayTimeBetween(Integer value1, Integer value2) {
            addCriterion("trustee_pay_time between", value1, value2, "trusteePayTime");
            return (Criteria) this;
        }

        public Criteria andTrusteePayTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("trustee_pay_time not between", value1, value2, "trusteePayTime");
            return (Criteria) this;
        }

        public Criteria andTenderAccountMinIsNull() {
            addCriterion("tender_account_min is null");
            return (Criteria) this;
        }

        public Criteria andTenderAccountMinIsNotNull() {
            addCriterion("tender_account_min is not null");
            return (Criteria) this;
        }

        public Criteria andTenderAccountMinEqualTo(Integer value) {
            addCriterion("tender_account_min =", value, "tenderAccountMin");
            return (Criteria) this;
        }

        public Criteria andTenderAccountMinNotEqualTo(Integer value) {
            addCriterion("tender_account_min <>", value, "tenderAccountMin");
            return (Criteria) this;
        }

        public Criteria andTenderAccountMinGreaterThan(Integer value) {
            addCriterion("tender_account_min >", value, "tenderAccountMin");
            return (Criteria) this;
        }

        public Criteria andTenderAccountMinGreaterThanOrEqualTo(Integer value) {
            addCriterion("tender_account_min >=", value, "tenderAccountMin");
            return (Criteria) this;
        }

        public Criteria andTenderAccountMinLessThan(Integer value) {
            addCriterion("tender_account_min <", value, "tenderAccountMin");
            return (Criteria) this;
        }

        public Criteria andTenderAccountMinLessThanOrEqualTo(Integer value) {
            addCriterion("tender_account_min <=", value, "tenderAccountMin");
            return (Criteria) this;
        }

        public Criteria andTenderAccountMinIn(List<Integer> values) {
            addCriterion("tender_account_min in", values, "tenderAccountMin");
            return (Criteria) this;
        }

        public Criteria andTenderAccountMinNotIn(List<Integer> values) {
            addCriterion("tender_account_min not in", values, "tenderAccountMin");
            return (Criteria) this;
        }

        public Criteria andTenderAccountMinBetween(Integer value1, Integer value2) {
            addCriterion("tender_account_min between", value1, value2, "tenderAccountMin");
            return (Criteria) this;
        }

        public Criteria andTenderAccountMinNotBetween(Integer value1, Integer value2) {
            addCriterion("tender_account_min not between", value1, value2, "tenderAccountMin");
            return (Criteria) this;
        }

        public Criteria andTenderAccountMaxIsNull() {
            addCriterion("tender_account_max is null");
            return (Criteria) this;
        }

        public Criteria andTenderAccountMaxIsNotNull() {
            addCriterion("tender_account_max is not null");
            return (Criteria) this;
        }

        public Criteria andTenderAccountMaxEqualTo(Integer value) {
            addCriterion("tender_account_max =", value, "tenderAccountMax");
            return (Criteria) this;
        }

        public Criteria andTenderAccountMaxNotEqualTo(Integer value) {
            addCriterion("tender_account_max <>", value, "tenderAccountMax");
            return (Criteria) this;
        }

        public Criteria andTenderAccountMaxGreaterThan(Integer value) {
            addCriterion("tender_account_max >", value, "tenderAccountMax");
            return (Criteria) this;
        }

        public Criteria andTenderAccountMaxGreaterThanOrEqualTo(Integer value) {
            addCriterion("tender_account_max >=", value, "tenderAccountMax");
            return (Criteria) this;
        }

        public Criteria andTenderAccountMaxLessThan(Integer value) {
            addCriterion("tender_account_max <", value, "tenderAccountMax");
            return (Criteria) this;
        }

        public Criteria andTenderAccountMaxLessThanOrEqualTo(Integer value) {
            addCriterion("tender_account_max <=", value, "tenderAccountMax");
            return (Criteria) this;
        }

        public Criteria andTenderAccountMaxIn(List<Integer> values) {
            addCriterion("tender_account_max in", values, "tenderAccountMax");
            return (Criteria) this;
        }

        public Criteria andTenderAccountMaxNotIn(List<Integer> values) {
            addCriterion("tender_account_max not in", values, "tenderAccountMax");
            return (Criteria) this;
        }

        public Criteria andTenderAccountMaxBetween(Integer value1, Integer value2) {
            addCriterion("tender_account_max between", value1, value2, "tenderAccountMax");
            return (Criteria) this;
        }

        public Criteria andTenderAccountMaxNotBetween(Integer value1, Integer value2) {
            addCriterion("tender_account_max not between", value1, value2, "tenderAccountMax");
            return (Criteria) this;
        }

        public Criteria andUpfilesIdIsNull() {
            addCriterion("upfiles_id is null");
            return (Criteria) this;
        }

        public Criteria andUpfilesIdIsNotNull() {
            addCriterion("upfiles_id is not null");
            return (Criteria) this;
        }

        public Criteria andUpfilesIdEqualTo(String value) {
            addCriterion("upfiles_id =", value, "upfilesId");
            return (Criteria) this;
        }

        public Criteria andUpfilesIdNotEqualTo(String value) {
            addCriterion("upfiles_id <>", value, "upfilesId");
            return (Criteria) this;
        }

        public Criteria andUpfilesIdGreaterThan(String value) {
            addCriterion("upfiles_id >", value, "upfilesId");
            return (Criteria) this;
        }

        public Criteria andUpfilesIdGreaterThanOrEqualTo(String value) {
            addCriterion("upfiles_id >=", value, "upfilesId");
            return (Criteria) this;
        }

        public Criteria andUpfilesIdLessThan(String value) {
            addCriterion("upfiles_id <", value, "upfilesId");
            return (Criteria) this;
        }

        public Criteria andUpfilesIdLessThanOrEqualTo(String value) {
            addCriterion("upfiles_id <=", value, "upfilesId");
            return (Criteria) this;
        }

        public Criteria andUpfilesIdLike(String value) {
            addCriterion("upfiles_id like", value, "upfilesId");
            return (Criteria) this;
        }

        public Criteria andUpfilesIdNotLike(String value) {
            addCriterion("upfiles_id not like", value, "upfilesId");
            return (Criteria) this;
        }

        public Criteria andUpfilesIdIn(List<String> values) {
            addCriterion("upfiles_id in", values, "upfilesId");
            return (Criteria) this;
        }

        public Criteria andUpfilesIdNotIn(List<String> values) {
            addCriterion("upfiles_id not in", values, "upfilesId");
            return (Criteria) this;
        }

        public Criteria andUpfilesIdBetween(String value1, String value2) {
            addCriterion("upfiles_id between", value1, value2, "upfilesId");
            return (Criteria) this;
        }

        public Criteria andUpfilesIdNotBetween(String value1, String value2) {
            addCriterion("upfiles_id not between", value1, value2, "upfilesId");
            return (Criteria) this;
        }

        public Criteria andProjectTypeIsNull() {
            addCriterion("project_type is null");
            return (Criteria) this;
        }

        public Criteria andProjectTypeIsNotNull() {
            addCriterion("project_type is not null");
            return (Criteria) this;
        }

        public Criteria andProjectTypeEqualTo(Integer value) {
            addCriterion("project_type =", value, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeNotEqualTo(Integer value) {
            addCriterion("project_type <>", value, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeGreaterThan(Integer value) {
            addCriterion("project_type >", value, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("project_type >=", value, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeLessThan(Integer value) {
            addCriterion("project_type <", value, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeLessThanOrEqualTo(Integer value) {
            addCriterion("project_type <=", value, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeIn(List<Integer> values) {
            addCriterion("project_type in", values, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeNotIn(List<Integer> values) {
            addCriterion("project_type not in", values, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeBetween(Integer value1, Integer value2) {
            addCriterion("project_type between", value1, value2, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("project_type not between", value1, value2, "projectType");
            return (Criteria) this;
        }

        public Criteria andCanTransactionPcIsNull() {
            addCriterion("can_transaction_pc is null");
            return (Criteria) this;
        }

        public Criteria andCanTransactionPcIsNotNull() {
            addCriterion("can_transaction_pc is not null");
            return (Criteria) this;
        }

        public Criteria andCanTransactionPcEqualTo(String value) {
            addCriterion("can_transaction_pc =", value, "canTransactionPc");
            return (Criteria) this;
        }

        public Criteria andCanTransactionPcNotEqualTo(String value) {
            addCriterion("can_transaction_pc <>", value, "canTransactionPc");
            return (Criteria) this;
        }

        public Criteria andCanTransactionPcGreaterThan(String value) {
            addCriterion("can_transaction_pc >", value, "canTransactionPc");
            return (Criteria) this;
        }

        public Criteria andCanTransactionPcGreaterThanOrEqualTo(String value) {
            addCriterion("can_transaction_pc >=", value, "canTransactionPc");
            return (Criteria) this;
        }

        public Criteria andCanTransactionPcLessThan(String value) {
            addCriterion("can_transaction_pc <", value, "canTransactionPc");
            return (Criteria) this;
        }

        public Criteria andCanTransactionPcLessThanOrEqualTo(String value) {
            addCriterion("can_transaction_pc <=", value, "canTransactionPc");
            return (Criteria) this;
        }

        public Criteria andCanTransactionPcLike(String value) {
            addCriterion("can_transaction_pc like", value, "canTransactionPc");
            return (Criteria) this;
        }

        public Criteria andCanTransactionPcNotLike(String value) {
            addCriterion("can_transaction_pc not like", value, "canTransactionPc");
            return (Criteria) this;
        }

        public Criteria andCanTransactionPcIn(List<String> values) {
            addCriterion("can_transaction_pc in", values, "canTransactionPc");
            return (Criteria) this;
        }

        public Criteria andCanTransactionPcNotIn(List<String> values) {
            addCriterion("can_transaction_pc not in", values, "canTransactionPc");
            return (Criteria) this;
        }

        public Criteria andCanTransactionPcBetween(String value1, String value2) {
            addCriterion("can_transaction_pc between", value1, value2, "canTransactionPc");
            return (Criteria) this;
        }

        public Criteria andCanTransactionPcNotBetween(String value1, String value2) {
            addCriterion("can_transaction_pc not between", value1, value2, "canTransactionPc");
            return (Criteria) this;
        }

        public Criteria andCanTransactionWeiIsNull() {
            addCriterion("can_transaction_wei is null");
            return (Criteria) this;
        }

        public Criteria andCanTransactionWeiIsNotNull() {
            addCriterion("can_transaction_wei is not null");
            return (Criteria) this;
        }

        public Criteria andCanTransactionWeiEqualTo(String value) {
            addCriterion("can_transaction_wei =", value, "canTransactionWei");
            return (Criteria) this;
        }

        public Criteria andCanTransactionWeiNotEqualTo(String value) {
            addCriterion("can_transaction_wei <>", value, "canTransactionWei");
            return (Criteria) this;
        }

        public Criteria andCanTransactionWeiGreaterThan(String value) {
            addCriterion("can_transaction_wei >", value, "canTransactionWei");
            return (Criteria) this;
        }

        public Criteria andCanTransactionWeiGreaterThanOrEqualTo(String value) {
            addCriterion("can_transaction_wei >=", value, "canTransactionWei");
            return (Criteria) this;
        }

        public Criteria andCanTransactionWeiLessThan(String value) {
            addCriterion("can_transaction_wei <", value, "canTransactionWei");
            return (Criteria) this;
        }

        public Criteria andCanTransactionWeiLessThanOrEqualTo(String value) {
            addCriterion("can_transaction_wei <=", value, "canTransactionWei");
            return (Criteria) this;
        }

        public Criteria andCanTransactionWeiLike(String value) {
            addCriterion("can_transaction_wei like", value, "canTransactionWei");
            return (Criteria) this;
        }

        public Criteria andCanTransactionWeiNotLike(String value) {
            addCriterion("can_transaction_wei not like", value, "canTransactionWei");
            return (Criteria) this;
        }

        public Criteria andCanTransactionWeiIn(List<String> values) {
            addCriterion("can_transaction_wei in", values, "canTransactionWei");
            return (Criteria) this;
        }

        public Criteria andCanTransactionWeiNotIn(List<String> values) {
            addCriterion("can_transaction_wei not in", values, "canTransactionWei");
            return (Criteria) this;
        }

        public Criteria andCanTransactionWeiBetween(String value1, String value2) {
            addCriterion("can_transaction_wei between", value1, value2, "canTransactionWei");
            return (Criteria) this;
        }

        public Criteria andCanTransactionWeiNotBetween(String value1, String value2) {
            addCriterion("can_transaction_wei not between", value1, value2, "canTransactionWei");
            return (Criteria) this;
        }

        public Criteria andCanTransactionIosIsNull() {
            addCriterion("can_transaction_ios is null");
            return (Criteria) this;
        }

        public Criteria andCanTransactionIosIsNotNull() {
            addCriterion("can_transaction_ios is not null");
            return (Criteria) this;
        }

        public Criteria andCanTransactionIosEqualTo(String value) {
            addCriterion("can_transaction_ios =", value, "canTransactionIos");
            return (Criteria) this;
        }

        public Criteria andCanTransactionIosNotEqualTo(String value) {
            addCriterion("can_transaction_ios <>", value, "canTransactionIos");
            return (Criteria) this;
        }

        public Criteria andCanTransactionIosGreaterThan(String value) {
            addCriterion("can_transaction_ios >", value, "canTransactionIos");
            return (Criteria) this;
        }

        public Criteria andCanTransactionIosGreaterThanOrEqualTo(String value) {
            addCriterion("can_transaction_ios >=", value, "canTransactionIos");
            return (Criteria) this;
        }

        public Criteria andCanTransactionIosLessThan(String value) {
            addCriterion("can_transaction_ios <", value, "canTransactionIos");
            return (Criteria) this;
        }

        public Criteria andCanTransactionIosLessThanOrEqualTo(String value) {
            addCriterion("can_transaction_ios <=", value, "canTransactionIos");
            return (Criteria) this;
        }

        public Criteria andCanTransactionIosLike(String value) {
            addCriterion("can_transaction_ios like", value, "canTransactionIos");
            return (Criteria) this;
        }

        public Criteria andCanTransactionIosNotLike(String value) {
            addCriterion("can_transaction_ios not like", value, "canTransactionIos");
            return (Criteria) this;
        }

        public Criteria andCanTransactionIosIn(List<String> values) {
            addCriterion("can_transaction_ios in", values, "canTransactionIos");
            return (Criteria) this;
        }

        public Criteria andCanTransactionIosNotIn(List<String> values) {
            addCriterion("can_transaction_ios not in", values, "canTransactionIos");
            return (Criteria) this;
        }

        public Criteria andCanTransactionIosBetween(String value1, String value2) {
            addCriterion("can_transaction_ios between", value1, value2, "canTransactionIos");
            return (Criteria) this;
        }

        public Criteria andCanTransactionIosNotBetween(String value1, String value2) {
            addCriterion("can_transaction_ios not between", value1, value2, "canTransactionIos");
            return (Criteria) this;
        }

        public Criteria andCanTransactionAndroidIsNull() {
            addCriterion("can_transaction_android is null");
            return (Criteria) this;
        }

        public Criteria andCanTransactionAndroidIsNotNull() {
            addCriterion("can_transaction_android is not null");
            return (Criteria) this;
        }

        public Criteria andCanTransactionAndroidEqualTo(String value) {
            addCriterion("can_transaction_android =", value, "canTransactionAndroid");
            return (Criteria) this;
        }

        public Criteria andCanTransactionAndroidNotEqualTo(String value) {
            addCriterion("can_transaction_android <>", value, "canTransactionAndroid");
            return (Criteria) this;
        }

        public Criteria andCanTransactionAndroidGreaterThan(String value) {
            addCriterion("can_transaction_android >", value, "canTransactionAndroid");
            return (Criteria) this;
        }

        public Criteria andCanTransactionAndroidGreaterThanOrEqualTo(String value) {
            addCriterion("can_transaction_android >=", value, "canTransactionAndroid");
            return (Criteria) this;
        }

        public Criteria andCanTransactionAndroidLessThan(String value) {
            addCriterion("can_transaction_android <", value, "canTransactionAndroid");
            return (Criteria) this;
        }

        public Criteria andCanTransactionAndroidLessThanOrEqualTo(String value) {
            addCriterion("can_transaction_android <=", value, "canTransactionAndroid");
            return (Criteria) this;
        }

        public Criteria andCanTransactionAndroidLike(String value) {
            addCriterion("can_transaction_android like", value, "canTransactionAndroid");
            return (Criteria) this;
        }

        public Criteria andCanTransactionAndroidNotLike(String value) {
            addCriterion("can_transaction_android not like", value, "canTransactionAndroid");
            return (Criteria) this;
        }

        public Criteria andCanTransactionAndroidIn(List<String> values) {
            addCriterion("can_transaction_android in", values, "canTransactionAndroid");
            return (Criteria) this;
        }

        public Criteria andCanTransactionAndroidNotIn(List<String> values) {
            addCriterion("can_transaction_android not in", values, "canTransactionAndroid");
            return (Criteria) this;
        }

        public Criteria andCanTransactionAndroidBetween(String value1, String value2) {
            addCriterion("can_transaction_android between", value1, value2, "canTransactionAndroid");
            return (Criteria) this;
        }

        public Criteria andCanTransactionAndroidNotBetween(String value1, String value2) {
            addCriterion("can_transaction_android not between", value1, value2, "canTransactionAndroid");
            return (Criteria) this;
        }

        public Criteria andOperationLabelIsNull() {
            addCriterion("operation_label is null");
            return (Criteria) this;
        }

        public Criteria andOperationLabelIsNotNull() {
            addCriterion("operation_label is not null");
            return (Criteria) this;
        }

        public Criteria andOperationLabelEqualTo(String value) {
            addCriterion("operation_label =", value, "operationLabel");
            return (Criteria) this;
        }

        public Criteria andOperationLabelNotEqualTo(String value) {
            addCriterion("operation_label <>", value, "operationLabel");
            return (Criteria) this;
        }

        public Criteria andOperationLabelGreaterThan(String value) {
            addCriterion("operation_label >", value, "operationLabel");
            return (Criteria) this;
        }

        public Criteria andOperationLabelGreaterThanOrEqualTo(String value) {
            addCriterion("operation_label >=", value, "operationLabel");
            return (Criteria) this;
        }

        public Criteria andOperationLabelLessThan(String value) {
            addCriterion("operation_label <", value, "operationLabel");
            return (Criteria) this;
        }

        public Criteria andOperationLabelLessThanOrEqualTo(String value) {
            addCriterion("operation_label <=", value, "operationLabel");
            return (Criteria) this;
        }

        public Criteria andOperationLabelLike(String value) {
            addCriterion("operation_label like", value, "operationLabel");
            return (Criteria) this;
        }

        public Criteria andOperationLabelNotLike(String value) {
            addCriterion("operation_label not like", value, "operationLabel");
            return (Criteria) this;
        }

        public Criteria andOperationLabelIn(List<String> values) {
            addCriterion("operation_label in", values, "operationLabel");
            return (Criteria) this;
        }

        public Criteria andOperationLabelNotIn(List<String> values) {
            addCriterion("operation_label not in", values, "operationLabel");
            return (Criteria) this;
        }

        public Criteria andOperationLabelBetween(String value1, String value2) {
            addCriterion("operation_label between", value1, value2, "operationLabel");
            return (Criteria) this;
        }

        public Criteria andOperationLabelNotBetween(String value1, String value2) {
            addCriterion("operation_label not between", value1, value2, "operationLabel");
            return (Criteria) this;
        }

        public Criteria andCompanyOrPersonalIsNull() {
            addCriterion("company_or_personal is null");
            return (Criteria) this;
        }

        public Criteria andCompanyOrPersonalIsNotNull() {
            addCriterion("company_or_personal is not null");
            return (Criteria) this;
        }

        public Criteria andCompanyOrPersonalEqualTo(Integer value) {
            addCriterion("company_or_personal =", value, "companyOrPersonal");
            return (Criteria) this;
        }

        public Criteria andCompanyOrPersonalNotEqualTo(Integer value) {
            addCriterion("company_or_personal <>", value, "companyOrPersonal");
            return (Criteria) this;
        }

        public Criteria andCompanyOrPersonalGreaterThan(Integer value) {
            addCriterion("company_or_personal >", value, "companyOrPersonal");
            return (Criteria) this;
        }

        public Criteria andCompanyOrPersonalGreaterThanOrEqualTo(Integer value) {
            addCriterion("company_or_personal >=", value, "companyOrPersonal");
            return (Criteria) this;
        }

        public Criteria andCompanyOrPersonalLessThan(Integer value) {
            addCriterion("company_or_personal <", value, "companyOrPersonal");
            return (Criteria) this;
        }

        public Criteria andCompanyOrPersonalLessThanOrEqualTo(Integer value) {
            addCriterion("company_or_personal <=", value, "companyOrPersonal");
            return (Criteria) this;
        }

        public Criteria andCompanyOrPersonalIn(List<Integer> values) {
            addCriterion("company_or_personal in", values, "companyOrPersonal");
            return (Criteria) this;
        }

        public Criteria andCompanyOrPersonalNotIn(List<Integer> values) {
            addCriterion("company_or_personal not in", values, "companyOrPersonal");
            return (Criteria) this;
        }

        public Criteria andCompanyOrPersonalBetween(Integer value1, Integer value2) {
            addCriterion("company_or_personal between", value1, value2, "companyOrPersonal");
            return (Criteria) this;
        }

        public Criteria andCompanyOrPersonalNotBetween(Integer value1, Integer value2) {
            addCriterion("company_or_personal not between", value1, value2, "companyOrPersonal");
            return (Criteria) this;
        }

        public Criteria andBorrowManagerScaleEndIsNull() {
            addCriterion("borrow_manager_scale_end is null");
            return (Criteria) this;
        }

        public Criteria andBorrowManagerScaleEndIsNotNull() {
            addCriterion("borrow_manager_scale_end is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowManagerScaleEndEqualTo(String value) {
            addCriterion("borrow_manager_scale_end =", value, "borrowManagerScaleEnd");
            return (Criteria) this;
        }

        public Criteria andBorrowManagerScaleEndNotEqualTo(String value) {
            addCriterion("borrow_manager_scale_end <>", value, "borrowManagerScaleEnd");
            return (Criteria) this;
        }

        public Criteria andBorrowManagerScaleEndGreaterThan(String value) {
            addCriterion("borrow_manager_scale_end >", value, "borrowManagerScaleEnd");
            return (Criteria) this;
        }

        public Criteria andBorrowManagerScaleEndGreaterThanOrEqualTo(String value) {
            addCriterion("borrow_manager_scale_end >=", value, "borrowManagerScaleEnd");
            return (Criteria) this;
        }

        public Criteria andBorrowManagerScaleEndLessThan(String value) {
            addCriterion("borrow_manager_scale_end <", value, "borrowManagerScaleEnd");
            return (Criteria) this;
        }

        public Criteria andBorrowManagerScaleEndLessThanOrEqualTo(String value) {
            addCriterion("borrow_manager_scale_end <=", value, "borrowManagerScaleEnd");
            return (Criteria) this;
        }

        public Criteria andBorrowManagerScaleEndLike(String value) {
            addCriterion("borrow_manager_scale_end like", value, "borrowManagerScaleEnd");
            return (Criteria) this;
        }

        public Criteria andBorrowManagerScaleEndNotLike(String value) {
            addCriterion("borrow_manager_scale_end not like", value, "borrowManagerScaleEnd");
            return (Criteria) this;
        }

        public Criteria andBorrowManagerScaleEndIn(List<String> values) {
            addCriterion("borrow_manager_scale_end in", values, "borrowManagerScaleEnd");
            return (Criteria) this;
        }

        public Criteria andBorrowManagerScaleEndNotIn(List<String> values) {
            addCriterion("borrow_manager_scale_end not in", values, "borrowManagerScaleEnd");
            return (Criteria) this;
        }

        public Criteria andBorrowManagerScaleEndBetween(String value1, String value2) {
            addCriterion("borrow_manager_scale_end between", value1, value2, "borrowManagerScaleEnd");
            return (Criteria) this;
        }

        public Criteria andBorrowManagerScaleEndNotBetween(String value1, String value2) {
            addCriterion("borrow_manager_scale_end not between", value1, value2, "borrowManagerScaleEnd");
            return (Criteria) this;
        }

        public Criteria andConsumeIdIsNull() {
            addCriterion("consume_id is null");
            return (Criteria) this;
        }

        public Criteria andConsumeIdIsNotNull() {
            addCriterion("consume_id is not null");
            return (Criteria) this;
        }

        public Criteria andConsumeIdEqualTo(String value) {
            addCriterion("consume_id =", value, "consumeId");
            return (Criteria) this;
        }

        public Criteria andConsumeIdNotEqualTo(String value) {
            addCriterion("consume_id <>", value, "consumeId");
            return (Criteria) this;
        }

        public Criteria andConsumeIdGreaterThan(String value) {
            addCriterion("consume_id >", value, "consumeId");
            return (Criteria) this;
        }

        public Criteria andConsumeIdGreaterThanOrEqualTo(String value) {
            addCriterion("consume_id >=", value, "consumeId");
            return (Criteria) this;
        }

        public Criteria andConsumeIdLessThan(String value) {
            addCriterion("consume_id <", value, "consumeId");
            return (Criteria) this;
        }

        public Criteria andConsumeIdLessThanOrEqualTo(String value) {
            addCriterion("consume_id <=", value, "consumeId");
            return (Criteria) this;
        }

        public Criteria andConsumeIdLike(String value) {
            addCriterion("consume_id like", value, "consumeId");
            return (Criteria) this;
        }

        public Criteria andConsumeIdNotLike(String value) {
            addCriterion("consume_id not like", value, "consumeId");
            return (Criteria) this;
        }

        public Criteria andConsumeIdIn(List<String> values) {
            addCriterion("consume_id in", values, "consumeId");
            return (Criteria) this;
        }

        public Criteria andConsumeIdNotIn(List<String> values) {
            addCriterion("consume_id not in", values, "consumeId");
            return (Criteria) this;
        }

        public Criteria andConsumeIdBetween(String value1, String value2) {
            addCriterion("consume_id between", value1, value2, "consumeId");
            return (Criteria) this;
        }

        public Criteria andConsumeIdNotBetween(String value1, String value2) {
            addCriterion("consume_id not between", value1, value2, "consumeId");
            return (Criteria) this;
        }

        public Criteria andDisposalPriceEstimateIsNull() {
            addCriterion("disposal_price_estimate is null");
            return (Criteria) this;
        }

        public Criteria andDisposalPriceEstimateIsNotNull() {
            addCriterion("disposal_price_estimate is not null");
            return (Criteria) this;
        }

        public Criteria andDisposalPriceEstimateEqualTo(String value) {
            addCriterion("disposal_price_estimate =", value, "disposalPriceEstimate");
            return (Criteria) this;
        }

        public Criteria andDisposalPriceEstimateNotEqualTo(String value) {
            addCriterion("disposal_price_estimate <>", value, "disposalPriceEstimate");
            return (Criteria) this;
        }

        public Criteria andDisposalPriceEstimateGreaterThan(String value) {
            addCriterion("disposal_price_estimate >", value, "disposalPriceEstimate");
            return (Criteria) this;
        }

        public Criteria andDisposalPriceEstimateGreaterThanOrEqualTo(String value) {
            addCriterion("disposal_price_estimate >=", value, "disposalPriceEstimate");
            return (Criteria) this;
        }

        public Criteria andDisposalPriceEstimateLessThan(String value) {
            addCriterion("disposal_price_estimate <", value, "disposalPriceEstimate");
            return (Criteria) this;
        }

        public Criteria andDisposalPriceEstimateLessThanOrEqualTo(String value) {
            addCriterion("disposal_price_estimate <=", value, "disposalPriceEstimate");
            return (Criteria) this;
        }

        public Criteria andDisposalPriceEstimateLike(String value) {
            addCriterion("disposal_price_estimate like", value, "disposalPriceEstimate");
            return (Criteria) this;
        }

        public Criteria andDisposalPriceEstimateNotLike(String value) {
            addCriterion("disposal_price_estimate not like", value, "disposalPriceEstimate");
            return (Criteria) this;
        }

        public Criteria andDisposalPriceEstimateIn(List<String> values) {
            addCriterion("disposal_price_estimate in", values, "disposalPriceEstimate");
            return (Criteria) this;
        }

        public Criteria andDisposalPriceEstimateNotIn(List<String> values) {
            addCriterion("disposal_price_estimate not in", values, "disposalPriceEstimate");
            return (Criteria) this;
        }

        public Criteria andDisposalPriceEstimateBetween(String value1, String value2) {
            addCriterion("disposal_price_estimate between", value1, value2, "disposalPriceEstimate");
            return (Criteria) this;
        }

        public Criteria andDisposalPriceEstimateNotBetween(String value1, String value2) {
            addCriterion("disposal_price_estimate not between", value1, value2, "disposalPriceEstimate");
            return (Criteria) this;
        }

        public Criteria andDisposalPeriodIsNull() {
            addCriterion("disposal_period is null");
            return (Criteria) this;
        }

        public Criteria andDisposalPeriodIsNotNull() {
            addCriterion("disposal_period is not null");
            return (Criteria) this;
        }

        public Criteria andDisposalPeriodEqualTo(String value) {
            addCriterion("disposal_period =", value, "disposalPeriod");
            return (Criteria) this;
        }

        public Criteria andDisposalPeriodNotEqualTo(String value) {
            addCriterion("disposal_period <>", value, "disposalPeriod");
            return (Criteria) this;
        }

        public Criteria andDisposalPeriodGreaterThan(String value) {
            addCriterion("disposal_period >", value, "disposalPeriod");
            return (Criteria) this;
        }

        public Criteria andDisposalPeriodGreaterThanOrEqualTo(String value) {
            addCriterion("disposal_period >=", value, "disposalPeriod");
            return (Criteria) this;
        }

        public Criteria andDisposalPeriodLessThan(String value) {
            addCriterion("disposal_period <", value, "disposalPeriod");
            return (Criteria) this;
        }

        public Criteria andDisposalPeriodLessThanOrEqualTo(String value) {
            addCriterion("disposal_period <=", value, "disposalPeriod");
            return (Criteria) this;
        }

        public Criteria andDisposalPeriodLike(String value) {
            addCriterion("disposal_period like", value, "disposalPeriod");
            return (Criteria) this;
        }

        public Criteria andDisposalPeriodNotLike(String value) {
            addCriterion("disposal_period not like", value, "disposalPeriod");
            return (Criteria) this;
        }

        public Criteria andDisposalPeriodIn(List<String> values) {
            addCriterion("disposal_period in", values, "disposalPeriod");
            return (Criteria) this;
        }

        public Criteria andDisposalPeriodNotIn(List<String> values) {
            addCriterion("disposal_period not in", values, "disposalPeriod");
            return (Criteria) this;
        }

        public Criteria andDisposalPeriodBetween(String value1, String value2) {
            addCriterion("disposal_period between", value1, value2, "disposalPeriod");
            return (Criteria) this;
        }

        public Criteria andDisposalPeriodNotBetween(String value1, String value2) {
            addCriterion("disposal_period not between", value1, value2, "disposalPeriod");
            return (Criteria) this;
        }

        public Criteria andDisposalChannelIsNull() {
            addCriterion("disposal_channel is null");
            return (Criteria) this;
        }

        public Criteria andDisposalChannelIsNotNull() {
            addCriterion("disposal_channel is not null");
            return (Criteria) this;
        }

        public Criteria andDisposalChannelEqualTo(String value) {
            addCriterion("disposal_channel =", value, "disposalChannel");
            return (Criteria) this;
        }

        public Criteria andDisposalChannelNotEqualTo(String value) {
            addCriterion("disposal_channel <>", value, "disposalChannel");
            return (Criteria) this;
        }

        public Criteria andDisposalChannelGreaterThan(String value) {
            addCriterion("disposal_channel >", value, "disposalChannel");
            return (Criteria) this;
        }

        public Criteria andDisposalChannelGreaterThanOrEqualTo(String value) {
            addCriterion("disposal_channel >=", value, "disposalChannel");
            return (Criteria) this;
        }

        public Criteria andDisposalChannelLessThan(String value) {
            addCriterion("disposal_channel <", value, "disposalChannel");
            return (Criteria) this;
        }

        public Criteria andDisposalChannelLessThanOrEqualTo(String value) {
            addCriterion("disposal_channel <=", value, "disposalChannel");
            return (Criteria) this;
        }

        public Criteria andDisposalChannelLike(String value) {
            addCriterion("disposal_channel like", value, "disposalChannel");
            return (Criteria) this;
        }

        public Criteria andDisposalChannelNotLike(String value) {
            addCriterion("disposal_channel not like", value, "disposalChannel");
            return (Criteria) this;
        }

        public Criteria andDisposalChannelIn(List<String> values) {
            addCriterion("disposal_channel in", values, "disposalChannel");
            return (Criteria) this;
        }

        public Criteria andDisposalChannelNotIn(List<String> values) {
            addCriterion("disposal_channel not in", values, "disposalChannel");
            return (Criteria) this;
        }

        public Criteria andDisposalChannelBetween(String value1, String value2) {
            addCriterion("disposal_channel between", value1, value2, "disposalChannel");
            return (Criteria) this;
        }

        public Criteria andDisposalChannelNotBetween(String value1, String value2) {
            addCriterion("disposal_channel not between", value1, value2, "disposalChannel");
            return (Criteria) this;
        }

        public Criteria andDisposalResultIsNull() {
            addCriterion("disposal_result is null");
            return (Criteria) this;
        }

        public Criteria andDisposalResultIsNotNull() {
            addCriterion("disposal_result is not null");
            return (Criteria) this;
        }

        public Criteria andDisposalResultEqualTo(String value) {
            addCriterion("disposal_result =", value, "disposalResult");
            return (Criteria) this;
        }

        public Criteria andDisposalResultNotEqualTo(String value) {
            addCriterion("disposal_result <>", value, "disposalResult");
            return (Criteria) this;
        }

        public Criteria andDisposalResultGreaterThan(String value) {
            addCriterion("disposal_result >", value, "disposalResult");
            return (Criteria) this;
        }

        public Criteria andDisposalResultGreaterThanOrEqualTo(String value) {
            addCriterion("disposal_result >=", value, "disposalResult");
            return (Criteria) this;
        }

        public Criteria andDisposalResultLessThan(String value) {
            addCriterion("disposal_result <", value, "disposalResult");
            return (Criteria) this;
        }

        public Criteria andDisposalResultLessThanOrEqualTo(String value) {
            addCriterion("disposal_result <=", value, "disposalResult");
            return (Criteria) this;
        }

        public Criteria andDisposalResultLike(String value) {
            addCriterion("disposal_result like", value, "disposalResult");
            return (Criteria) this;
        }

        public Criteria andDisposalResultNotLike(String value) {
            addCriterion("disposal_result not like", value, "disposalResult");
            return (Criteria) this;
        }

        public Criteria andDisposalResultIn(List<String> values) {
            addCriterion("disposal_result in", values, "disposalResult");
            return (Criteria) this;
        }

        public Criteria andDisposalResultNotIn(List<String> values) {
            addCriterion("disposal_result not in", values, "disposalResult");
            return (Criteria) this;
        }

        public Criteria andDisposalResultBetween(String value1, String value2) {
            addCriterion("disposal_result between", value1, value2, "disposalResult");
            return (Criteria) this;
        }

        public Criteria andDisposalResultNotBetween(String value1, String value2) {
            addCriterion("disposal_result not between", value1, value2, "disposalResult");
            return (Criteria) this;
        }

        public Criteria andDisposalNoteIsNull() {
            addCriterion("disposal_note is null");
            return (Criteria) this;
        }

        public Criteria andDisposalNoteIsNotNull() {
            addCriterion("disposal_note is not null");
            return (Criteria) this;
        }

        public Criteria andDisposalNoteEqualTo(String value) {
            addCriterion("disposal_note =", value, "disposalNote");
            return (Criteria) this;
        }

        public Criteria andDisposalNoteNotEqualTo(String value) {
            addCriterion("disposal_note <>", value, "disposalNote");
            return (Criteria) this;
        }

        public Criteria andDisposalNoteGreaterThan(String value) {
            addCriterion("disposal_note >", value, "disposalNote");
            return (Criteria) this;
        }

        public Criteria andDisposalNoteGreaterThanOrEqualTo(String value) {
            addCriterion("disposal_note >=", value, "disposalNote");
            return (Criteria) this;
        }

        public Criteria andDisposalNoteLessThan(String value) {
            addCriterion("disposal_note <", value, "disposalNote");
            return (Criteria) this;
        }

        public Criteria andDisposalNoteLessThanOrEqualTo(String value) {
            addCriterion("disposal_note <=", value, "disposalNote");
            return (Criteria) this;
        }

        public Criteria andDisposalNoteLike(String value) {
            addCriterion("disposal_note like", value, "disposalNote");
            return (Criteria) this;
        }

        public Criteria andDisposalNoteNotLike(String value) {
            addCriterion("disposal_note not like", value, "disposalNote");
            return (Criteria) this;
        }

        public Criteria andDisposalNoteIn(List<String> values) {
            addCriterion("disposal_note in", values, "disposalNote");
            return (Criteria) this;
        }

        public Criteria andDisposalNoteNotIn(List<String> values) {
            addCriterion("disposal_note not in", values, "disposalNote");
            return (Criteria) this;
        }

        public Criteria andDisposalNoteBetween(String value1, String value2) {
            addCriterion("disposal_note between", value1, value2, "disposalNote");
            return (Criteria) this;
        }

        public Criteria andDisposalNoteNotBetween(String value1, String value2) {
            addCriterion("disposal_note not between", value1, value2, "disposalNote");
            return (Criteria) this;
        }

        public Criteria andDisposalProjectNameIsNull() {
            addCriterion("disposal_project_name is null");
            return (Criteria) this;
        }

        public Criteria andDisposalProjectNameIsNotNull() {
            addCriterion("disposal_project_name is not null");
            return (Criteria) this;
        }

        public Criteria andDisposalProjectNameEqualTo(String value) {
            addCriterion("disposal_project_name =", value, "disposalProjectName");
            return (Criteria) this;
        }

        public Criteria andDisposalProjectNameNotEqualTo(String value) {
            addCriterion("disposal_project_name <>", value, "disposalProjectName");
            return (Criteria) this;
        }

        public Criteria andDisposalProjectNameGreaterThan(String value) {
            addCriterion("disposal_project_name >", value, "disposalProjectName");
            return (Criteria) this;
        }

        public Criteria andDisposalProjectNameGreaterThanOrEqualTo(String value) {
            addCriterion("disposal_project_name >=", value, "disposalProjectName");
            return (Criteria) this;
        }

        public Criteria andDisposalProjectNameLessThan(String value) {
            addCriterion("disposal_project_name <", value, "disposalProjectName");
            return (Criteria) this;
        }

        public Criteria andDisposalProjectNameLessThanOrEqualTo(String value) {
            addCriterion("disposal_project_name <=", value, "disposalProjectName");
            return (Criteria) this;
        }

        public Criteria andDisposalProjectNameLike(String value) {
            addCriterion("disposal_project_name like", value, "disposalProjectName");
            return (Criteria) this;
        }

        public Criteria andDisposalProjectNameNotLike(String value) {
            addCriterion("disposal_project_name not like", value, "disposalProjectName");
            return (Criteria) this;
        }

        public Criteria andDisposalProjectNameIn(List<String> values) {
            addCriterion("disposal_project_name in", values, "disposalProjectName");
            return (Criteria) this;
        }

        public Criteria andDisposalProjectNameNotIn(List<String> values) {
            addCriterion("disposal_project_name not in", values, "disposalProjectName");
            return (Criteria) this;
        }

        public Criteria andDisposalProjectNameBetween(String value1, String value2) {
            addCriterion("disposal_project_name between", value1, value2, "disposalProjectName");
            return (Criteria) this;
        }

        public Criteria andDisposalProjectNameNotBetween(String value1, String value2) {
            addCriterion("disposal_project_name not between", value1, value2, "disposalProjectName");
            return (Criteria) this;
        }

        public Criteria andDisposalProjectTypeIsNull() {
            addCriterion("disposal_project_type is null");
            return (Criteria) this;
        }

        public Criteria andDisposalProjectTypeIsNotNull() {
            addCriterion("disposal_project_type is not null");
            return (Criteria) this;
        }

        public Criteria andDisposalProjectTypeEqualTo(String value) {
            addCriterion("disposal_project_type =", value, "disposalProjectType");
            return (Criteria) this;
        }

        public Criteria andDisposalProjectTypeNotEqualTo(String value) {
            addCriterion("disposal_project_type <>", value, "disposalProjectType");
            return (Criteria) this;
        }

        public Criteria andDisposalProjectTypeGreaterThan(String value) {
            addCriterion("disposal_project_type >", value, "disposalProjectType");
            return (Criteria) this;
        }

        public Criteria andDisposalProjectTypeGreaterThanOrEqualTo(String value) {
            addCriterion("disposal_project_type >=", value, "disposalProjectType");
            return (Criteria) this;
        }

        public Criteria andDisposalProjectTypeLessThan(String value) {
            addCriterion("disposal_project_type <", value, "disposalProjectType");
            return (Criteria) this;
        }

        public Criteria andDisposalProjectTypeLessThanOrEqualTo(String value) {
            addCriterion("disposal_project_type <=", value, "disposalProjectType");
            return (Criteria) this;
        }

        public Criteria andDisposalProjectTypeLike(String value) {
            addCriterion("disposal_project_type like", value, "disposalProjectType");
            return (Criteria) this;
        }

        public Criteria andDisposalProjectTypeNotLike(String value) {
            addCriterion("disposal_project_type not like", value, "disposalProjectType");
            return (Criteria) this;
        }

        public Criteria andDisposalProjectTypeIn(List<String> values) {
            addCriterion("disposal_project_type in", values, "disposalProjectType");
            return (Criteria) this;
        }

        public Criteria andDisposalProjectTypeNotIn(List<String> values) {
            addCriterion("disposal_project_type not in", values, "disposalProjectType");
            return (Criteria) this;
        }

        public Criteria andDisposalProjectTypeBetween(String value1, String value2) {
            addCriterion("disposal_project_type between", value1, value2, "disposalProjectType");
            return (Criteria) this;
        }

        public Criteria andDisposalProjectTypeNotBetween(String value1, String value2) {
            addCriterion("disposal_project_type not between", value1, value2, "disposalProjectType");
            return (Criteria) this;
        }

        public Criteria andDisposalAreaIsNull() {
            addCriterion("disposal_area is null");
            return (Criteria) this;
        }

        public Criteria andDisposalAreaIsNotNull() {
            addCriterion("disposal_area is not null");
            return (Criteria) this;
        }

        public Criteria andDisposalAreaEqualTo(String value) {
            addCriterion("disposal_area =", value, "disposalArea");
            return (Criteria) this;
        }

        public Criteria andDisposalAreaNotEqualTo(String value) {
            addCriterion("disposal_area <>", value, "disposalArea");
            return (Criteria) this;
        }

        public Criteria andDisposalAreaGreaterThan(String value) {
            addCriterion("disposal_area >", value, "disposalArea");
            return (Criteria) this;
        }

        public Criteria andDisposalAreaGreaterThanOrEqualTo(String value) {
            addCriterion("disposal_area >=", value, "disposalArea");
            return (Criteria) this;
        }

        public Criteria andDisposalAreaLessThan(String value) {
            addCriterion("disposal_area <", value, "disposalArea");
            return (Criteria) this;
        }

        public Criteria andDisposalAreaLessThanOrEqualTo(String value) {
            addCriterion("disposal_area <=", value, "disposalArea");
            return (Criteria) this;
        }

        public Criteria andDisposalAreaLike(String value) {
            addCriterion("disposal_area like", value, "disposalArea");
            return (Criteria) this;
        }

        public Criteria andDisposalAreaNotLike(String value) {
            addCriterion("disposal_area not like", value, "disposalArea");
            return (Criteria) this;
        }

        public Criteria andDisposalAreaIn(List<String> values) {
            addCriterion("disposal_area in", values, "disposalArea");
            return (Criteria) this;
        }

        public Criteria andDisposalAreaNotIn(List<String> values) {
            addCriterion("disposal_area not in", values, "disposalArea");
            return (Criteria) this;
        }

        public Criteria andDisposalAreaBetween(String value1, String value2) {
            addCriterion("disposal_area between", value1, value2, "disposalArea");
            return (Criteria) this;
        }

        public Criteria andDisposalAreaNotBetween(String value1, String value2) {
            addCriterion("disposal_area not between", value1, value2, "disposalArea");
            return (Criteria) this;
        }

        public Criteria andDisposalPredictiveValueIsNull() {
            addCriterion("disposal_predictive_value is null");
            return (Criteria) this;
        }

        public Criteria andDisposalPredictiveValueIsNotNull() {
            addCriterion("disposal_predictive_value is not null");
            return (Criteria) this;
        }

        public Criteria andDisposalPredictiveValueEqualTo(String value) {
            addCriterion("disposal_predictive_value =", value, "disposalPredictiveValue");
            return (Criteria) this;
        }

        public Criteria andDisposalPredictiveValueNotEqualTo(String value) {
            addCriterion("disposal_predictive_value <>", value, "disposalPredictiveValue");
            return (Criteria) this;
        }

        public Criteria andDisposalPredictiveValueGreaterThan(String value) {
            addCriterion("disposal_predictive_value >", value, "disposalPredictiveValue");
            return (Criteria) this;
        }

        public Criteria andDisposalPredictiveValueGreaterThanOrEqualTo(String value) {
            addCriterion("disposal_predictive_value >=", value, "disposalPredictiveValue");
            return (Criteria) this;
        }

        public Criteria andDisposalPredictiveValueLessThan(String value) {
            addCriterion("disposal_predictive_value <", value, "disposalPredictiveValue");
            return (Criteria) this;
        }

        public Criteria andDisposalPredictiveValueLessThanOrEqualTo(String value) {
            addCriterion("disposal_predictive_value <=", value, "disposalPredictiveValue");
            return (Criteria) this;
        }

        public Criteria andDisposalPredictiveValueLike(String value) {
            addCriterion("disposal_predictive_value like", value, "disposalPredictiveValue");
            return (Criteria) this;
        }

        public Criteria andDisposalPredictiveValueNotLike(String value) {
            addCriterion("disposal_predictive_value not like", value, "disposalPredictiveValue");
            return (Criteria) this;
        }

        public Criteria andDisposalPredictiveValueIn(List<String> values) {
            addCriterion("disposal_predictive_value in", values, "disposalPredictiveValue");
            return (Criteria) this;
        }

        public Criteria andDisposalPredictiveValueNotIn(List<String> values) {
            addCriterion("disposal_predictive_value not in", values, "disposalPredictiveValue");
            return (Criteria) this;
        }

        public Criteria andDisposalPredictiveValueBetween(String value1, String value2) {
            addCriterion("disposal_predictive_value between", value1, value2, "disposalPredictiveValue");
            return (Criteria) this;
        }

        public Criteria andDisposalPredictiveValueNotBetween(String value1, String value2) {
            addCriterion("disposal_predictive_value not between", value1, value2, "disposalPredictiveValue");
            return (Criteria) this;
        }

        public Criteria andDisposalOwnershipCategoryIsNull() {
            addCriterion("disposal_ownership_category is null");
            return (Criteria) this;
        }

        public Criteria andDisposalOwnershipCategoryIsNotNull() {
            addCriterion("disposal_ownership_category is not null");
            return (Criteria) this;
        }

        public Criteria andDisposalOwnershipCategoryEqualTo(String value) {
            addCriterion("disposal_ownership_category =", value, "disposalOwnershipCategory");
            return (Criteria) this;
        }

        public Criteria andDisposalOwnershipCategoryNotEqualTo(String value) {
            addCriterion("disposal_ownership_category <>", value, "disposalOwnershipCategory");
            return (Criteria) this;
        }

        public Criteria andDisposalOwnershipCategoryGreaterThan(String value) {
            addCriterion("disposal_ownership_category >", value, "disposalOwnershipCategory");
            return (Criteria) this;
        }

        public Criteria andDisposalOwnershipCategoryGreaterThanOrEqualTo(String value) {
            addCriterion("disposal_ownership_category >=", value, "disposalOwnershipCategory");
            return (Criteria) this;
        }

        public Criteria andDisposalOwnershipCategoryLessThan(String value) {
            addCriterion("disposal_ownership_category <", value, "disposalOwnershipCategory");
            return (Criteria) this;
        }

        public Criteria andDisposalOwnershipCategoryLessThanOrEqualTo(String value) {
            addCriterion("disposal_ownership_category <=", value, "disposalOwnershipCategory");
            return (Criteria) this;
        }

        public Criteria andDisposalOwnershipCategoryLike(String value) {
            addCriterion("disposal_ownership_category like", value, "disposalOwnershipCategory");
            return (Criteria) this;
        }

        public Criteria andDisposalOwnershipCategoryNotLike(String value) {
            addCriterion("disposal_ownership_category not like", value, "disposalOwnershipCategory");
            return (Criteria) this;
        }

        public Criteria andDisposalOwnershipCategoryIn(List<String> values) {
            addCriterion("disposal_ownership_category in", values, "disposalOwnershipCategory");
            return (Criteria) this;
        }

        public Criteria andDisposalOwnershipCategoryNotIn(List<String> values) {
            addCriterion("disposal_ownership_category not in", values, "disposalOwnershipCategory");
            return (Criteria) this;
        }

        public Criteria andDisposalOwnershipCategoryBetween(String value1, String value2) {
            addCriterion("disposal_ownership_category between", value1, value2, "disposalOwnershipCategory");
            return (Criteria) this;
        }

        public Criteria andDisposalOwnershipCategoryNotBetween(String value1, String value2) {
            addCriterion("disposal_ownership_category not between", value1, value2, "disposalOwnershipCategory");
            return (Criteria) this;
        }

        public Criteria andDisposalAssetOriginIsNull() {
            addCriterion("disposal_asset_origin is null");
            return (Criteria) this;
        }

        public Criteria andDisposalAssetOriginIsNotNull() {
            addCriterion("disposal_asset_origin is not null");
            return (Criteria) this;
        }

        public Criteria andDisposalAssetOriginEqualTo(String value) {
            addCriterion("disposal_asset_origin =", value, "disposalAssetOrigin");
            return (Criteria) this;
        }

        public Criteria andDisposalAssetOriginNotEqualTo(String value) {
            addCriterion("disposal_asset_origin <>", value, "disposalAssetOrigin");
            return (Criteria) this;
        }

        public Criteria andDisposalAssetOriginGreaterThan(String value) {
            addCriterion("disposal_asset_origin >", value, "disposalAssetOrigin");
            return (Criteria) this;
        }

        public Criteria andDisposalAssetOriginGreaterThanOrEqualTo(String value) {
            addCriterion("disposal_asset_origin >=", value, "disposalAssetOrigin");
            return (Criteria) this;
        }

        public Criteria andDisposalAssetOriginLessThan(String value) {
            addCriterion("disposal_asset_origin <", value, "disposalAssetOrigin");
            return (Criteria) this;
        }

        public Criteria andDisposalAssetOriginLessThanOrEqualTo(String value) {
            addCriterion("disposal_asset_origin <=", value, "disposalAssetOrigin");
            return (Criteria) this;
        }

        public Criteria andDisposalAssetOriginLike(String value) {
            addCriterion("disposal_asset_origin like", value, "disposalAssetOrigin");
            return (Criteria) this;
        }

        public Criteria andDisposalAssetOriginNotLike(String value) {
            addCriterion("disposal_asset_origin not like", value, "disposalAssetOrigin");
            return (Criteria) this;
        }

        public Criteria andDisposalAssetOriginIn(List<String> values) {
            addCriterion("disposal_asset_origin in", values, "disposalAssetOrigin");
            return (Criteria) this;
        }

        public Criteria andDisposalAssetOriginNotIn(List<String> values) {
            addCriterion("disposal_asset_origin not in", values, "disposalAssetOrigin");
            return (Criteria) this;
        }

        public Criteria andDisposalAssetOriginBetween(String value1, String value2) {
            addCriterion("disposal_asset_origin between", value1, value2, "disposalAssetOrigin");
            return (Criteria) this;
        }

        public Criteria andDisposalAssetOriginNotBetween(String value1, String value2) {
            addCriterion("disposal_asset_origin not between", value1, value2, "disposalAssetOrigin");
            return (Criteria) this;
        }

        public Criteria andDisposalAttachmentInfoIsNull() {
            addCriterion("disposal_attachment_info is null");
            return (Criteria) this;
        }

        public Criteria andDisposalAttachmentInfoIsNotNull() {
            addCriterion("disposal_attachment_info is not null");
            return (Criteria) this;
        }

        public Criteria andDisposalAttachmentInfoEqualTo(String value) {
            addCriterion("disposal_attachment_info =", value, "disposalAttachmentInfo");
            return (Criteria) this;
        }

        public Criteria andDisposalAttachmentInfoNotEqualTo(String value) {
            addCriterion("disposal_attachment_info <>", value, "disposalAttachmentInfo");
            return (Criteria) this;
        }

        public Criteria andDisposalAttachmentInfoGreaterThan(String value) {
            addCriterion("disposal_attachment_info >", value, "disposalAttachmentInfo");
            return (Criteria) this;
        }

        public Criteria andDisposalAttachmentInfoGreaterThanOrEqualTo(String value) {
            addCriterion("disposal_attachment_info >=", value, "disposalAttachmentInfo");
            return (Criteria) this;
        }

        public Criteria andDisposalAttachmentInfoLessThan(String value) {
            addCriterion("disposal_attachment_info <", value, "disposalAttachmentInfo");
            return (Criteria) this;
        }

        public Criteria andDisposalAttachmentInfoLessThanOrEqualTo(String value) {
            addCriterion("disposal_attachment_info <=", value, "disposalAttachmentInfo");
            return (Criteria) this;
        }

        public Criteria andDisposalAttachmentInfoLike(String value) {
            addCriterion("disposal_attachment_info like", value, "disposalAttachmentInfo");
            return (Criteria) this;
        }

        public Criteria andDisposalAttachmentInfoNotLike(String value) {
            addCriterion("disposal_attachment_info not like", value, "disposalAttachmentInfo");
            return (Criteria) this;
        }

        public Criteria andDisposalAttachmentInfoIn(List<String> values) {
            addCriterion("disposal_attachment_info in", values, "disposalAttachmentInfo");
            return (Criteria) this;
        }

        public Criteria andDisposalAttachmentInfoNotIn(List<String> values) {
            addCriterion("disposal_attachment_info not in", values, "disposalAttachmentInfo");
            return (Criteria) this;
        }

        public Criteria andDisposalAttachmentInfoBetween(String value1, String value2) {
            addCriterion("disposal_attachment_info between", value1, value2, "disposalAttachmentInfo");
            return (Criteria) this;
        }

        public Criteria andDisposalAttachmentInfoNotBetween(String value1, String value2) {
            addCriterion("disposal_attachment_info not between", value1, value2, "disposalAttachmentInfo");
            return (Criteria) this;
        }

        public Criteria andBorrowIncreaseMoneyIsNull() {
            addCriterion("borrow_increase_money is null");
            return (Criteria) this;
        }

        public Criteria andBorrowIncreaseMoneyIsNotNull() {
            addCriterion("borrow_increase_money is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowIncreaseMoneyEqualTo(Long value) {
            addCriterion("borrow_increase_money =", value, "borrowIncreaseMoney");
            return (Criteria) this;
        }

        public Criteria andBorrowIncreaseMoneyNotEqualTo(Long value) {
            addCriterion("borrow_increase_money <>", value, "borrowIncreaseMoney");
            return (Criteria) this;
        }

        public Criteria andBorrowIncreaseMoneyGreaterThan(Long value) {
            addCriterion("borrow_increase_money >", value, "borrowIncreaseMoney");
            return (Criteria) this;
        }

        public Criteria andBorrowIncreaseMoneyGreaterThanOrEqualTo(Long value) {
            addCriterion("borrow_increase_money >=", value, "borrowIncreaseMoney");
            return (Criteria) this;
        }

        public Criteria andBorrowIncreaseMoneyLessThan(Long value) {
            addCriterion("borrow_increase_money <", value, "borrowIncreaseMoney");
            return (Criteria) this;
        }

        public Criteria andBorrowIncreaseMoneyLessThanOrEqualTo(Long value) {
            addCriterion("borrow_increase_money <=", value, "borrowIncreaseMoney");
            return (Criteria) this;
        }

        public Criteria andBorrowIncreaseMoneyIn(List<Long> values) {
            addCriterion("borrow_increase_money in", values, "borrowIncreaseMoney");
            return (Criteria) this;
        }

        public Criteria andBorrowIncreaseMoneyNotIn(List<Long> values) {
            addCriterion("borrow_increase_money not in", values, "borrowIncreaseMoney");
            return (Criteria) this;
        }

        public Criteria andBorrowIncreaseMoneyBetween(Long value1, Long value2) {
            addCriterion("borrow_increase_money between", value1, value2, "borrowIncreaseMoney");
            return (Criteria) this;
        }

        public Criteria andBorrowIncreaseMoneyNotBetween(Long value1, Long value2) {
            addCriterion("borrow_increase_money not between", value1, value2, "borrowIncreaseMoney");
            return (Criteria) this;
        }

        public Criteria andBorrowInterestCouponIsNull() {
            addCriterion("borrow_interest_coupon is null");
            return (Criteria) this;
        }

        public Criteria andBorrowInterestCouponIsNotNull() {
            addCriterion("borrow_interest_coupon is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowInterestCouponEqualTo(Integer value) {
            addCriterion("borrow_interest_coupon =", value, "borrowInterestCoupon");
            return (Criteria) this;
        }

        public Criteria andBorrowInterestCouponNotEqualTo(Integer value) {
            addCriterion("borrow_interest_coupon <>", value, "borrowInterestCoupon");
            return (Criteria) this;
        }

        public Criteria andBorrowInterestCouponGreaterThan(Integer value) {
            addCriterion("borrow_interest_coupon >", value, "borrowInterestCoupon");
            return (Criteria) this;
        }

        public Criteria andBorrowInterestCouponGreaterThanOrEqualTo(Integer value) {
            addCriterion("borrow_interest_coupon >=", value, "borrowInterestCoupon");
            return (Criteria) this;
        }

        public Criteria andBorrowInterestCouponLessThan(Integer value) {
            addCriterion("borrow_interest_coupon <", value, "borrowInterestCoupon");
            return (Criteria) this;
        }

        public Criteria andBorrowInterestCouponLessThanOrEqualTo(Integer value) {
            addCriterion("borrow_interest_coupon <=", value, "borrowInterestCoupon");
            return (Criteria) this;
        }

        public Criteria andBorrowInterestCouponIn(List<Integer> values) {
            addCriterion("borrow_interest_coupon in", values, "borrowInterestCoupon");
            return (Criteria) this;
        }

        public Criteria andBorrowInterestCouponNotIn(List<Integer> values) {
            addCriterion("borrow_interest_coupon not in", values, "borrowInterestCoupon");
            return (Criteria) this;
        }

        public Criteria andBorrowInterestCouponBetween(Integer value1, Integer value2) {
            addCriterion("borrow_interest_coupon between", value1, value2, "borrowInterestCoupon");
            return (Criteria) this;
        }

        public Criteria andBorrowInterestCouponNotBetween(Integer value1, Integer value2) {
            addCriterion("borrow_interest_coupon not between", value1, value2, "borrowInterestCoupon");
            return (Criteria) this;
        }

        public Criteria andBorrowTasteMoneyIsNull() {
            addCriterion("borrow_taste_money is null");
            return (Criteria) this;
        }

        public Criteria andBorrowTasteMoneyIsNotNull() {
            addCriterion("borrow_taste_money is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowTasteMoneyEqualTo(Integer value) {
            addCriterion("borrow_taste_money =", value, "borrowTasteMoney");
            return (Criteria) this;
        }

        public Criteria andBorrowTasteMoneyNotEqualTo(Integer value) {
            addCriterion("borrow_taste_money <>", value, "borrowTasteMoney");
            return (Criteria) this;
        }

        public Criteria andBorrowTasteMoneyGreaterThan(Integer value) {
            addCriterion("borrow_taste_money >", value, "borrowTasteMoney");
            return (Criteria) this;
        }

        public Criteria andBorrowTasteMoneyGreaterThanOrEqualTo(Integer value) {
            addCriterion("borrow_taste_money >=", value, "borrowTasteMoney");
            return (Criteria) this;
        }

        public Criteria andBorrowTasteMoneyLessThan(Integer value) {
            addCriterion("borrow_taste_money <", value, "borrowTasteMoney");
            return (Criteria) this;
        }

        public Criteria andBorrowTasteMoneyLessThanOrEqualTo(Integer value) {
            addCriterion("borrow_taste_money <=", value, "borrowTasteMoney");
            return (Criteria) this;
        }

        public Criteria andBorrowTasteMoneyIn(List<Integer> values) {
            addCriterion("borrow_taste_money in", values, "borrowTasteMoney");
            return (Criteria) this;
        }

        public Criteria andBorrowTasteMoneyNotIn(List<Integer> values) {
            addCriterion("borrow_taste_money not in", values, "borrowTasteMoney");
            return (Criteria) this;
        }

        public Criteria andBorrowTasteMoneyBetween(Integer value1, Integer value2) {
            addCriterion("borrow_taste_money between", value1, value2, "borrowTasteMoney");
            return (Criteria) this;
        }

        public Criteria andBorrowTasteMoneyNotBetween(Integer value1, Integer value2) {
            addCriterion("borrow_taste_money not between", value1, value2, "borrowTasteMoney");
            return (Criteria) this;
        }

        public Criteria andBorrowAssetNumberIsNull() {
            addCriterion("borrow_asset_number is null");
            return (Criteria) this;
        }

        public Criteria andBorrowAssetNumberIsNotNull() {
            addCriterion("borrow_asset_number is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowAssetNumberEqualTo(String value) {
            addCriterion("borrow_asset_number =", value, "borrowAssetNumber");
            return (Criteria) this;
        }

        public Criteria andBorrowAssetNumberNotEqualTo(String value) {
            addCriterion("borrow_asset_number <>", value, "borrowAssetNumber");
            return (Criteria) this;
        }

        public Criteria andBorrowAssetNumberGreaterThan(String value) {
            addCriterion("borrow_asset_number >", value, "borrowAssetNumber");
            return (Criteria) this;
        }

        public Criteria andBorrowAssetNumberGreaterThanOrEqualTo(String value) {
            addCriterion("borrow_asset_number >=", value, "borrowAssetNumber");
            return (Criteria) this;
        }

        public Criteria andBorrowAssetNumberLessThan(String value) {
            addCriterion("borrow_asset_number <", value, "borrowAssetNumber");
            return (Criteria) this;
        }

        public Criteria andBorrowAssetNumberLessThanOrEqualTo(String value) {
            addCriterion("borrow_asset_number <=", value, "borrowAssetNumber");
            return (Criteria) this;
        }

        public Criteria andBorrowAssetNumberLike(String value) {
            addCriterion("borrow_asset_number like", value, "borrowAssetNumber");
            return (Criteria) this;
        }

        public Criteria andBorrowAssetNumberNotLike(String value) {
            addCriterion("borrow_asset_number not like", value, "borrowAssetNumber");
            return (Criteria) this;
        }

        public Criteria andBorrowAssetNumberIn(List<String> values) {
            addCriterion("borrow_asset_number in", values, "borrowAssetNumber");
            return (Criteria) this;
        }

        public Criteria andBorrowAssetNumberNotIn(List<String> values) {
            addCriterion("borrow_asset_number not in", values, "borrowAssetNumber");
            return (Criteria) this;
        }

        public Criteria andBorrowAssetNumberBetween(String value1, String value2) {
            addCriterion("borrow_asset_number between", value1, value2, "borrowAssetNumber");
            return (Criteria) this;
        }

        public Criteria andBorrowAssetNumberNotBetween(String value1, String value2) {
            addCriterion("borrow_asset_number not between", value1, value2, "borrowAssetNumber");
            return (Criteria) this;
        }

        public Criteria andBorrowProjectSourceIsNull() {
            addCriterion("borrow_project_source is null");
            return (Criteria) this;
        }

        public Criteria andBorrowProjectSourceIsNotNull() {
            addCriterion("borrow_project_source is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowProjectSourceEqualTo(String value) {
            addCriterion("borrow_project_source =", value, "borrowProjectSource");
            return (Criteria) this;
        }

        public Criteria andBorrowProjectSourceNotEqualTo(String value) {
            addCriterion("borrow_project_source <>", value, "borrowProjectSource");
            return (Criteria) this;
        }

        public Criteria andBorrowProjectSourceGreaterThan(String value) {
            addCriterion("borrow_project_source >", value, "borrowProjectSource");
            return (Criteria) this;
        }

        public Criteria andBorrowProjectSourceGreaterThanOrEqualTo(String value) {
            addCriterion("borrow_project_source >=", value, "borrowProjectSource");
            return (Criteria) this;
        }

        public Criteria andBorrowProjectSourceLessThan(String value) {
            addCriterion("borrow_project_source <", value, "borrowProjectSource");
            return (Criteria) this;
        }

        public Criteria andBorrowProjectSourceLessThanOrEqualTo(String value) {
            addCriterion("borrow_project_source <=", value, "borrowProjectSource");
            return (Criteria) this;
        }

        public Criteria andBorrowProjectSourceLike(String value) {
            addCriterion("borrow_project_source like", value, "borrowProjectSource");
            return (Criteria) this;
        }

        public Criteria andBorrowProjectSourceNotLike(String value) {
            addCriterion("borrow_project_source not like", value, "borrowProjectSource");
            return (Criteria) this;
        }

        public Criteria andBorrowProjectSourceIn(List<String> values) {
            addCriterion("borrow_project_source in", values, "borrowProjectSource");
            return (Criteria) this;
        }

        public Criteria andBorrowProjectSourceNotIn(List<String> values) {
            addCriterion("borrow_project_source not in", values, "borrowProjectSource");
            return (Criteria) this;
        }

        public Criteria andBorrowProjectSourceBetween(String value1, String value2) {
            addCriterion("borrow_project_source between", value1, value2, "borrowProjectSource");
            return (Criteria) this;
        }

        public Criteria andBorrowProjectSourceNotBetween(String value1, String value2) {
            addCriterion("borrow_project_source not between", value1, value2, "borrowProjectSource");
            return (Criteria) this;
        }

        public Criteria andBorrowInterestTimeIsNull() {
            addCriterion("borrow_interest_time is null");
            return (Criteria) this;
        }

        public Criteria andBorrowInterestTimeIsNotNull() {
            addCriterion("borrow_interest_time is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowInterestTimeEqualTo(String value) {
            addCriterion("borrow_interest_time =", value, "borrowInterestTime");
            return (Criteria) this;
        }

        public Criteria andBorrowInterestTimeNotEqualTo(String value) {
            addCriterion("borrow_interest_time <>", value, "borrowInterestTime");
            return (Criteria) this;
        }

        public Criteria andBorrowInterestTimeGreaterThan(String value) {
            addCriterion("borrow_interest_time >", value, "borrowInterestTime");
            return (Criteria) this;
        }

        public Criteria andBorrowInterestTimeGreaterThanOrEqualTo(String value) {
            addCriterion("borrow_interest_time >=", value, "borrowInterestTime");
            return (Criteria) this;
        }

        public Criteria andBorrowInterestTimeLessThan(String value) {
            addCriterion("borrow_interest_time <", value, "borrowInterestTime");
            return (Criteria) this;
        }

        public Criteria andBorrowInterestTimeLessThanOrEqualTo(String value) {
            addCriterion("borrow_interest_time <=", value, "borrowInterestTime");
            return (Criteria) this;
        }

        public Criteria andBorrowInterestTimeLike(String value) {
            addCriterion("borrow_interest_time like", value, "borrowInterestTime");
            return (Criteria) this;
        }

        public Criteria andBorrowInterestTimeNotLike(String value) {
            addCriterion("borrow_interest_time not like", value, "borrowInterestTime");
            return (Criteria) this;
        }

        public Criteria andBorrowInterestTimeIn(List<String> values) {
            addCriterion("borrow_interest_time in", values, "borrowInterestTime");
            return (Criteria) this;
        }

        public Criteria andBorrowInterestTimeNotIn(List<String> values) {
            addCriterion("borrow_interest_time not in", values, "borrowInterestTime");
            return (Criteria) this;
        }

        public Criteria andBorrowInterestTimeBetween(String value1, String value2) {
            addCriterion("borrow_interest_time between", value1, value2, "borrowInterestTime");
            return (Criteria) this;
        }

        public Criteria andBorrowInterestTimeNotBetween(String value1, String value2) {
            addCriterion("borrow_interest_time not between", value1, value2, "borrowInterestTime");
            return (Criteria) this;
        }

        public Criteria andBorrowDueTimeIsNull() {
            addCriterion("borrow_due_time is null");
            return (Criteria) this;
        }

        public Criteria andBorrowDueTimeIsNotNull() {
            addCriterion("borrow_due_time is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowDueTimeEqualTo(String value) {
            addCriterion("borrow_due_time =", value, "borrowDueTime");
            return (Criteria) this;
        }

        public Criteria andBorrowDueTimeNotEqualTo(String value) {
            addCriterion("borrow_due_time <>", value, "borrowDueTime");
            return (Criteria) this;
        }

        public Criteria andBorrowDueTimeGreaterThan(String value) {
            addCriterion("borrow_due_time >", value, "borrowDueTime");
            return (Criteria) this;
        }

        public Criteria andBorrowDueTimeGreaterThanOrEqualTo(String value) {
            addCriterion("borrow_due_time >=", value, "borrowDueTime");
            return (Criteria) this;
        }

        public Criteria andBorrowDueTimeLessThan(String value) {
            addCriterion("borrow_due_time <", value, "borrowDueTime");
            return (Criteria) this;
        }

        public Criteria andBorrowDueTimeLessThanOrEqualTo(String value) {
            addCriterion("borrow_due_time <=", value, "borrowDueTime");
            return (Criteria) this;
        }

        public Criteria andBorrowDueTimeLike(String value) {
            addCriterion("borrow_due_time like", value, "borrowDueTime");
            return (Criteria) this;
        }

        public Criteria andBorrowDueTimeNotLike(String value) {
            addCriterion("borrow_due_time not like", value, "borrowDueTime");
            return (Criteria) this;
        }

        public Criteria andBorrowDueTimeIn(List<String> values) {
            addCriterion("borrow_due_time in", values, "borrowDueTime");
            return (Criteria) this;
        }

        public Criteria andBorrowDueTimeNotIn(List<String> values) {
            addCriterion("borrow_due_time not in", values, "borrowDueTime");
            return (Criteria) this;
        }

        public Criteria andBorrowDueTimeBetween(String value1, String value2) {
            addCriterion("borrow_due_time between", value1, value2, "borrowDueTime");
            return (Criteria) this;
        }

        public Criteria andBorrowDueTimeNotBetween(String value1, String value2) {
            addCriterion("borrow_due_time not between", value1, value2, "borrowDueTime");
            return (Criteria) this;
        }

        public Criteria andBorrowSafeguardWayIsNull() {
            addCriterion("borrow_safeguard_way is null");
            return (Criteria) this;
        }

        public Criteria andBorrowSafeguardWayIsNotNull() {
            addCriterion("borrow_safeguard_way is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowSafeguardWayEqualTo(String value) {
            addCriterion("borrow_safeguard_way =", value, "borrowSafeguardWay");
            return (Criteria) this;
        }

        public Criteria andBorrowSafeguardWayNotEqualTo(String value) {
            addCriterion("borrow_safeguard_way <>", value, "borrowSafeguardWay");
            return (Criteria) this;
        }

        public Criteria andBorrowSafeguardWayGreaterThan(String value) {
            addCriterion("borrow_safeguard_way >", value, "borrowSafeguardWay");
            return (Criteria) this;
        }

        public Criteria andBorrowSafeguardWayGreaterThanOrEqualTo(String value) {
            addCriterion("borrow_safeguard_way >=", value, "borrowSafeguardWay");
            return (Criteria) this;
        }

        public Criteria andBorrowSafeguardWayLessThan(String value) {
            addCriterion("borrow_safeguard_way <", value, "borrowSafeguardWay");
            return (Criteria) this;
        }

        public Criteria andBorrowSafeguardWayLessThanOrEqualTo(String value) {
            addCriterion("borrow_safeguard_way <=", value, "borrowSafeguardWay");
            return (Criteria) this;
        }

        public Criteria andBorrowSafeguardWayLike(String value) {
            addCriterion("borrow_safeguard_way like", value, "borrowSafeguardWay");
            return (Criteria) this;
        }

        public Criteria andBorrowSafeguardWayNotLike(String value) {
            addCriterion("borrow_safeguard_way not like", value, "borrowSafeguardWay");
            return (Criteria) this;
        }

        public Criteria andBorrowSafeguardWayIn(List<String> values) {
            addCriterion("borrow_safeguard_way in", values, "borrowSafeguardWay");
            return (Criteria) this;
        }

        public Criteria andBorrowSafeguardWayNotIn(List<String> values) {
            addCriterion("borrow_safeguard_way not in", values, "borrowSafeguardWay");
            return (Criteria) this;
        }

        public Criteria andBorrowSafeguardWayBetween(String value1, String value2) {
            addCriterion("borrow_safeguard_way between", value1, value2, "borrowSafeguardWay");
            return (Criteria) this;
        }

        public Criteria andBorrowSafeguardWayNotBetween(String value1, String value2) {
            addCriterion("borrow_safeguard_way not between", value1, value2, "borrowSafeguardWay");
            return (Criteria) this;
        }

        public Criteria andBorrowIncomeDescriptionIsNull() {
            addCriterion("borrow_income_description is null");
            return (Criteria) this;
        }

        public Criteria andBorrowIncomeDescriptionIsNotNull() {
            addCriterion("borrow_income_description is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowIncomeDescriptionEqualTo(String value) {
            addCriterion("borrow_income_description =", value, "borrowIncomeDescription");
            return (Criteria) this;
        }

        public Criteria andBorrowIncomeDescriptionNotEqualTo(String value) {
            addCriterion("borrow_income_description <>", value, "borrowIncomeDescription");
            return (Criteria) this;
        }

        public Criteria andBorrowIncomeDescriptionGreaterThan(String value) {
            addCriterion("borrow_income_description >", value, "borrowIncomeDescription");
            return (Criteria) this;
        }

        public Criteria andBorrowIncomeDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("borrow_income_description >=", value, "borrowIncomeDescription");
            return (Criteria) this;
        }

        public Criteria andBorrowIncomeDescriptionLessThan(String value) {
            addCriterion("borrow_income_description <", value, "borrowIncomeDescription");
            return (Criteria) this;
        }

        public Criteria andBorrowIncomeDescriptionLessThanOrEqualTo(String value) {
            addCriterion("borrow_income_description <=", value, "borrowIncomeDescription");
            return (Criteria) this;
        }

        public Criteria andBorrowIncomeDescriptionLike(String value) {
            addCriterion("borrow_income_description like", value, "borrowIncomeDescription");
            return (Criteria) this;
        }

        public Criteria andBorrowIncomeDescriptionNotLike(String value) {
            addCriterion("borrow_income_description not like", value, "borrowIncomeDescription");
            return (Criteria) this;
        }

        public Criteria andBorrowIncomeDescriptionIn(List<String> values) {
            addCriterion("borrow_income_description in", values, "borrowIncomeDescription");
            return (Criteria) this;
        }

        public Criteria andBorrowIncomeDescriptionNotIn(List<String> values) {
            addCriterion("borrow_income_description not in", values, "borrowIncomeDescription");
            return (Criteria) this;
        }

        public Criteria andBorrowIncomeDescriptionBetween(String value1, String value2) {
            addCriterion("borrow_income_description between", value1, value2, "borrowIncomeDescription");
            return (Criteria) this;
        }

        public Criteria andBorrowIncomeDescriptionNotBetween(String value1, String value2) {
            addCriterion("borrow_income_description not between", value1, value2, "borrowIncomeDescription");
            return (Criteria) this;
        }

        public Criteria andBorrowPublisherIsNull() {
            addCriterion("borrow_publisher is null");
            return (Criteria) this;
        }

        public Criteria andBorrowPublisherIsNotNull() {
            addCriterion("borrow_publisher is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowPublisherEqualTo(String value) {
            addCriterion("borrow_publisher =", value, "borrowPublisher");
            return (Criteria) this;
        }

        public Criteria andBorrowPublisherNotEqualTo(String value) {
            addCriterion("borrow_publisher <>", value, "borrowPublisher");
            return (Criteria) this;
        }

        public Criteria andBorrowPublisherGreaterThan(String value) {
            addCriterion("borrow_publisher >", value, "borrowPublisher");
            return (Criteria) this;
        }

        public Criteria andBorrowPublisherGreaterThanOrEqualTo(String value) {
            addCriterion("borrow_publisher >=", value, "borrowPublisher");
            return (Criteria) this;
        }

        public Criteria andBorrowPublisherLessThan(String value) {
            addCriterion("borrow_publisher <", value, "borrowPublisher");
            return (Criteria) this;
        }

        public Criteria andBorrowPublisherLessThanOrEqualTo(String value) {
            addCriterion("borrow_publisher <=", value, "borrowPublisher");
            return (Criteria) this;
        }

        public Criteria andBorrowPublisherLike(String value) {
            addCriterion("borrow_publisher like", value, "borrowPublisher");
            return (Criteria) this;
        }

        public Criteria andBorrowPublisherNotLike(String value) {
            addCriterion("borrow_publisher not like", value, "borrowPublisher");
            return (Criteria) this;
        }

        public Criteria andBorrowPublisherIn(List<String> values) {
            addCriterion("borrow_publisher in", values, "borrowPublisher");
            return (Criteria) this;
        }

        public Criteria andBorrowPublisherNotIn(List<String> values) {
            addCriterion("borrow_publisher not in", values, "borrowPublisher");
            return (Criteria) this;
        }

        public Criteria andBorrowPublisherBetween(String value1, String value2) {
            addCriterion("borrow_publisher between", value1, value2, "borrowPublisher");
            return (Criteria) this;
        }

        public Criteria andBorrowPublisherNotBetween(String value1, String value2) {
            addCriterion("borrow_publisher not between", value1, value2, "borrowPublisher");
            return (Criteria) this;
        }

        public Criteria andBorrowExtraYieldIsNull() {
            addCriterion("borrow_extra_yield is null");
            return (Criteria) this;
        }

        public Criteria andBorrowExtraYieldIsNotNull() {
            addCriterion("borrow_extra_yield is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowExtraYieldEqualTo(BigDecimal value) {
            addCriterion("borrow_extra_yield =", value, "borrowExtraYield");
            return (Criteria) this;
        }

        public Criteria andBorrowExtraYieldNotEqualTo(BigDecimal value) {
            addCriterion("borrow_extra_yield <>", value, "borrowExtraYield");
            return (Criteria) this;
        }

        public Criteria andBorrowExtraYieldGreaterThan(BigDecimal value) {
            addCriterion("borrow_extra_yield >", value, "borrowExtraYield");
            return (Criteria) this;
        }

        public Criteria andBorrowExtraYieldGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("borrow_extra_yield >=", value, "borrowExtraYield");
            return (Criteria) this;
        }

        public Criteria andBorrowExtraYieldLessThan(BigDecimal value) {
            addCriterion("borrow_extra_yield <", value, "borrowExtraYield");
            return (Criteria) this;
        }

        public Criteria andBorrowExtraYieldLessThanOrEqualTo(BigDecimal value) {
            addCriterion("borrow_extra_yield <=", value, "borrowExtraYield");
            return (Criteria) this;
        }

        public Criteria andBorrowExtraYieldIn(List<BigDecimal> values) {
            addCriterion("borrow_extra_yield in", values, "borrowExtraYield");
            return (Criteria) this;
        }

        public Criteria andBorrowExtraYieldNotIn(List<BigDecimal> values) {
            addCriterion("borrow_extra_yield not in", values, "borrowExtraYield");
            return (Criteria) this;
        }

        public Criteria andBorrowExtraYieldBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("borrow_extra_yield between", value1, value2, "borrowExtraYield");
            return (Criteria) this;
        }

        public Criteria andBorrowExtraYieldNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("borrow_extra_yield not between", value1, value2, "borrowExtraYield");
            return (Criteria) this;
        }

        public Criteria andIncreaseInterestFlagIsNull() {
            addCriterion("increase_interest_flag is null");
            return (Criteria) this;
        }

        public Criteria andIncreaseInterestFlagIsNotNull() {
            addCriterion("increase_interest_flag is not null");
            return (Criteria) this;
        }

        public Criteria andIncreaseInterestFlagEqualTo(Integer value) {
            addCriterion("increase_interest_flag =", value, "increaseInterestFlag");
            return (Criteria) this;
        }

        public Criteria andIncreaseInterestFlagNotEqualTo(Integer value) {
            addCriterion("increase_interest_flag <>", value, "increaseInterestFlag");
            return (Criteria) this;
        }

        public Criteria andIncreaseInterestFlagGreaterThan(Integer value) {
            addCriterion("increase_interest_flag >", value, "increaseInterestFlag");
            return (Criteria) this;
        }

        public Criteria andIncreaseInterestFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("increase_interest_flag >=", value, "increaseInterestFlag");
            return (Criteria) this;
        }

        public Criteria andIncreaseInterestFlagLessThan(Integer value) {
            addCriterion("increase_interest_flag <", value, "increaseInterestFlag");
            return (Criteria) this;
        }

        public Criteria andIncreaseInterestFlagLessThanOrEqualTo(Integer value) {
            addCriterion("increase_interest_flag <=", value, "increaseInterestFlag");
            return (Criteria) this;
        }

        public Criteria andIncreaseInterestFlagIn(List<Integer> values) {
            addCriterion("increase_interest_flag in", values, "increaseInterestFlag");
            return (Criteria) this;
        }

        public Criteria andIncreaseInterestFlagNotIn(List<Integer> values) {
            addCriterion("increase_interest_flag not in", values, "increaseInterestFlag");
            return (Criteria) this;
        }

        public Criteria andIncreaseInterestFlagBetween(Integer value1, Integer value2) {
            addCriterion("increase_interest_flag between", value1, value2, "increaseInterestFlag");
            return (Criteria) this;
        }

        public Criteria andIncreaseInterestFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("increase_interest_flag not between", value1, value2, "increaseInterestFlag");
            return (Criteria) this;
        }

        public Criteria andContractPeriodIsNull() {
            addCriterion("contract_period is null");
            return (Criteria) this;
        }

        public Criteria andContractPeriodIsNotNull() {
            addCriterion("contract_period is not null");
            return (Criteria) this;
        }

        public Criteria andContractPeriodEqualTo(Integer value) {
            addCriterion("contract_period =", value, "contractPeriod");
            return (Criteria) this;
        }

        public Criteria andContractPeriodNotEqualTo(Integer value) {
            addCriterion("contract_period <>", value, "contractPeriod");
            return (Criteria) this;
        }

        public Criteria andContractPeriodGreaterThan(Integer value) {
            addCriterion("contract_period >", value, "contractPeriod");
            return (Criteria) this;
        }

        public Criteria andContractPeriodGreaterThanOrEqualTo(Integer value) {
            addCriterion("contract_period >=", value, "contractPeriod");
            return (Criteria) this;
        }

        public Criteria andContractPeriodLessThan(Integer value) {
            addCriterion("contract_period <", value, "contractPeriod");
            return (Criteria) this;
        }

        public Criteria andContractPeriodLessThanOrEqualTo(Integer value) {
            addCriterion("contract_period <=", value, "contractPeriod");
            return (Criteria) this;
        }

        public Criteria andContractPeriodIn(List<Integer> values) {
            addCriterion("contract_period in", values, "contractPeriod");
            return (Criteria) this;
        }

        public Criteria andContractPeriodNotIn(List<Integer> values) {
            addCriterion("contract_period not in", values, "contractPeriod");
            return (Criteria) this;
        }

        public Criteria andContractPeriodBetween(Integer value1, Integer value2) {
            addCriterion("contract_period between", value1, value2, "contractPeriod");
            return (Criteria) this;
        }

        public Criteria andContractPeriodNotBetween(Integer value1, Integer value2) {
            addCriterion("contract_period not between", value1, value2, "contractPeriod");
            return (Criteria) this;
        }

        public Criteria andBorrowLevelIsNull() {
            addCriterion("borrow_level is null");
            return (Criteria) this;
        }

        public Criteria andBorrowLevelIsNotNull() {
            addCriterion("borrow_level is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowLevelEqualTo(String value) {
            addCriterion("borrow_level =", value, "borrowLevel");
            return (Criteria) this;
        }

        public Criteria andBorrowLevelNotEqualTo(String value) {
            addCriterion("borrow_level <>", value, "borrowLevel");
            return (Criteria) this;
        }

        public Criteria andBorrowLevelGreaterThan(String value) {
            addCriterion("borrow_level >", value, "borrowLevel");
            return (Criteria) this;
        }

        public Criteria andBorrowLevelGreaterThanOrEqualTo(String value) {
            addCriterion("borrow_level >=", value, "borrowLevel");
            return (Criteria) this;
        }

        public Criteria andBorrowLevelLessThan(String value) {
            addCriterion("borrow_level <", value, "borrowLevel");
            return (Criteria) this;
        }

        public Criteria andBorrowLevelLessThanOrEqualTo(String value) {
            addCriterion("borrow_level <=", value, "borrowLevel");
            return (Criteria) this;
        }

        public Criteria andBorrowLevelLike(String value) {
            addCriterion("borrow_level like", value, "borrowLevel");
            return (Criteria) this;
        }

        public Criteria andBorrowLevelNotLike(String value) {
            addCriterion("borrow_level not like", value, "borrowLevel");
            return (Criteria) this;
        }

        public Criteria andBorrowLevelIn(List<String> values) {
            addCriterion("borrow_level in", values, "borrowLevel");
            return (Criteria) this;
        }

        public Criteria andBorrowLevelNotIn(List<String> values) {
            addCriterion("borrow_level not in", values, "borrowLevel");
            return (Criteria) this;
        }

        public Criteria andBorrowLevelBetween(String value1, String value2) {
            addCriterion("borrow_level between", value1, value2, "borrowLevel");
            return (Criteria) this;
        }

        public Criteria andBorrowLevelNotBetween(String value1, String value2) {
            addCriterion("borrow_level not between", value1, value2, "borrowLevel");
            return (Criteria) this;
        }

        public Criteria andAssetAttributesIsNull() {
            addCriterion("asset_attributes is null");
            return (Criteria) this;
        }

        public Criteria andAssetAttributesIsNotNull() {
            addCriterion("asset_attributes is not null");
            return (Criteria) this;
        }

        public Criteria andAssetAttributesEqualTo(Integer value) {
            addCriterion("asset_attributes =", value, "assetAttributes");
            return (Criteria) this;
        }

        public Criteria andAssetAttributesNotEqualTo(Integer value) {
            addCriterion("asset_attributes <>", value, "assetAttributes");
            return (Criteria) this;
        }

        public Criteria andAssetAttributesGreaterThan(Integer value) {
            addCriterion("asset_attributes >", value, "assetAttributes");
            return (Criteria) this;
        }

        public Criteria andAssetAttributesGreaterThanOrEqualTo(Integer value) {
            addCriterion("asset_attributes >=", value, "assetAttributes");
            return (Criteria) this;
        }

        public Criteria andAssetAttributesLessThan(Integer value) {
            addCriterion("asset_attributes <", value, "assetAttributes");
            return (Criteria) this;
        }

        public Criteria andAssetAttributesLessThanOrEqualTo(Integer value) {
            addCriterion("asset_attributes <=", value, "assetAttributes");
            return (Criteria) this;
        }

        public Criteria andAssetAttributesIn(List<Integer> values) {
            addCriterion("asset_attributes in", values, "assetAttributes");
            return (Criteria) this;
        }

        public Criteria andAssetAttributesNotIn(List<Integer> values) {
            addCriterion("asset_attributes not in", values, "assetAttributes");
            return (Criteria) this;
        }

        public Criteria andAssetAttributesBetween(Integer value1, Integer value2) {
            addCriterion("asset_attributes between", value1, value2, "assetAttributes");
            return (Criteria) this;
        }

        public Criteria andAssetAttributesNotBetween(Integer value1, Integer value2) {
            addCriterion("asset_attributes not between", value1, value2, "assetAttributes");
            return (Criteria) this;
        }

        public Criteria andBankRaiseStartDateIsNull() {
            addCriterion("bank_raise_start_date is null");
            return (Criteria) this;
        }

        public Criteria andBankRaiseStartDateIsNotNull() {
            addCriterion("bank_raise_start_date is not null");
            return (Criteria) this;
        }

        public Criteria andBankRaiseStartDateEqualTo(String value) {
            addCriterion("bank_raise_start_date =", value, "bankRaiseStartDate");
            return (Criteria) this;
        }

        public Criteria andBankRaiseStartDateNotEqualTo(String value) {
            addCriterion("bank_raise_start_date <>", value, "bankRaiseStartDate");
            return (Criteria) this;
        }

        public Criteria andBankRaiseStartDateGreaterThan(String value) {
            addCriterion("bank_raise_start_date >", value, "bankRaiseStartDate");
            return (Criteria) this;
        }

        public Criteria andBankRaiseStartDateGreaterThanOrEqualTo(String value) {
            addCriterion("bank_raise_start_date >=", value, "bankRaiseStartDate");
            return (Criteria) this;
        }

        public Criteria andBankRaiseStartDateLessThan(String value) {
            addCriterion("bank_raise_start_date <", value, "bankRaiseStartDate");
            return (Criteria) this;
        }

        public Criteria andBankRaiseStartDateLessThanOrEqualTo(String value) {
            addCriterion("bank_raise_start_date <=", value, "bankRaiseStartDate");
            return (Criteria) this;
        }

        public Criteria andBankRaiseStartDateLike(String value) {
            addCriterion("bank_raise_start_date like", value, "bankRaiseStartDate");
            return (Criteria) this;
        }

        public Criteria andBankRaiseStartDateNotLike(String value) {
            addCriterion("bank_raise_start_date not like", value, "bankRaiseStartDate");
            return (Criteria) this;
        }

        public Criteria andBankRaiseStartDateIn(List<String> values) {
            addCriterion("bank_raise_start_date in", values, "bankRaiseStartDate");
            return (Criteria) this;
        }

        public Criteria andBankRaiseStartDateNotIn(List<String> values) {
            addCriterion("bank_raise_start_date not in", values, "bankRaiseStartDate");
            return (Criteria) this;
        }

        public Criteria andBankRaiseStartDateBetween(String value1, String value2) {
            addCriterion("bank_raise_start_date between", value1, value2, "bankRaiseStartDate");
            return (Criteria) this;
        }

        public Criteria andBankRaiseStartDateNotBetween(String value1, String value2) {
            addCriterion("bank_raise_start_date not between", value1, value2, "bankRaiseStartDate");
            return (Criteria) this;
        }

        public Criteria andBankRaiseEndDateIsNull() {
            addCriterion("bank_raise_end_date is null");
            return (Criteria) this;
        }

        public Criteria andBankRaiseEndDateIsNotNull() {
            addCriterion("bank_raise_end_date is not null");
            return (Criteria) this;
        }

        public Criteria andBankRaiseEndDateEqualTo(String value) {
            addCriterion("bank_raise_end_date =", value, "bankRaiseEndDate");
            return (Criteria) this;
        }

        public Criteria andBankRaiseEndDateNotEqualTo(String value) {
            addCriterion("bank_raise_end_date <>", value, "bankRaiseEndDate");
            return (Criteria) this;
        }

        public Criteria andBankRaiseEndDateGreaterThan(String value) {
            addCriterion("bank_raise_end_date >", value, "bankRaiseEndDate");
            return (Criteria) this;
        }

        public Criteria andBankRaiseEndDateGreaterThanOrEqualTo(String value) {
            addCriterion("bank_raise_end_date >=", value, "bankRaiseEndDate");
            return (Criteria) this;
        }

        public Criteria andBankRaiseEndDateLessThan(String value) {
            addCriterion("bank_raise_end_date <", value, "bankRaiseEndDate");
            return (Criteria) this;
        }

        public Criteria andBankRaiseEndDateLessThanOrEqualTo(String value) {
            addCriterion("bank_raise_end_date <=", value, "bankRaiseEndDate");
            return (Criteria) this;
        }

        public Criteria andBankRaiseEndDateLike(String value) {
            addCriterion("bank_raise_end_date like", value, "bankRaiseEndDate");
            return (Criteria) this;
        }

        public Criteria andBankRaiseEndDateNotLike(String value) {
            addCriterion("bank_raise_end_date not like", value, "bankRaiseEndDate");
            return (Criteria) this;
        }

        public Criteria andBankRaiseEndDateIn(List<String> values) {
            addCriterion("bank_raise_end_date in", values, "bankRaiseEndDate");
            return (Criteria) this;
        }

        public Criteria andBankRaiseEndDateNotIn(List<String> values) {
            addCriterion("bank_raise_end_date not in", values, "bankRaiseEndDate");
            return (Criteria) this;
        }

        public Criteria andBankRaiseEndDateBetween(String value1, String value2) {
            addCriterion("bank_raise_end_date between", value1, value2, "bankRaiseEndDate");
            return (Criteria) this;
        }

        public Criteria andBankRaiseEndDateNotBetween(String value1, String value2) {
            addCriterion("bank_raise_end_date not between", value1, value2, "bankRaiseEndDate");
            return (Criteria) this;
        }

        public Criteria andBankRegistDaysIsNull() {
            addCriterion("bank_regist_days is null");
            return (Criteria) this;
        }

        public Criteria andBankRegistDaysIsNotNull() {
            addCriterion("bank_regist_days is not null");
            return (Criteria) this;
        }

        public Criteria andBankRegistDaysEqualTo(Integer value) {
            addCriterion("bank_regist_days =", value, "bankRegistDays");
            return (Criteria) this;
        }

        public Criteria andBankRegistDaysNotEqualTo(Integer value) {
            addCriterion("bank_regist_days <>", value, "bankRegistDays");
            return (Criteria) this;
        }

        public Criteria andBankRegistDaysGreaterThan(Integer value) {
            addCriterion("bank_regist_days >", value, "bankRegistDays");
            return (Criteria) this;
        }

        public Criteria andBankRegistDaysGreaterThanOrEqualTo(Integer value) {
            addCriterion("bank_regist_days >=", value, "bankRegistDays");
            return (Criteria) this;
        }

        public Criteria andBankRegistDaysLessThan(Integer value) {
            addCriterion("bank_regist_days <", value, "bankRegistDays");
            return (Criteria) this;
        }

        public Criteria andBankRegistDaysLessThanOrEqualTo(Integer value) {
            addCriterion("bank_regist_days <=", value, "bankRegistDays");
            return (Criteria) this;
        }

        public Criteria andBankRegistDaysIn(List<Integer> values) {
            addCriterion("bank_regist_days in", values, "bankRegistDays");
            return (Criteria) this;
        }

        public Criteria andBankRegistDaysNotIn(List<Integer> values) {
            addCriterion("bank_regist_days not in", values, "bankRegistDays");
            return (Criteria) this;
        }

        public Criteria andBankRegistDaysBetween(Integer value1, Integer value2) {
            addCriterion("bank_regist_days between", value1, value2, "bankRegistDays");
            return (Criteria) this;
        }

        public Criteria andBankRegistDaysNotBetween(Integer value1, Integer value2) {
            addCriterion("bank_regist_days not between", value1, value2, "bankRegistDays");
            return (Criteria) this;
        }

        public Criteria andBankBorrowDaysIsNull() {
            addCriterion("bank_borrow_days is null");
            return (Criteria) this;
        }

        public Criteria andBankBorrowDaysIsNotNull() {
            addCriterion("bank_borrow_days is not null");
            return (Criteria) this;
        }

        public Criteria andBankBorrowDaysEqualTo(Integer value) {
            addCriterion("bank_borrow_days =", value, "bankBorrowDays");
            return (Criteria) this;
        }

        public Criteria andBankBorrowDaysNotEqualTo(Integer value) {
            addCriterion("bank_borrow_days <>", value, "bankBorrowDays");
            return (Criteria) this;
        }

        public Criteria andBankBorrowDaysGreaterThan(Integer value) {
            addCriterion("bank_borrow_days >", value, "bankBorrowDays");
            return (Criteria) this;
        }

        public Criteria andBankBorrowDaysGreaterThanOrEqualTo(Integer value) {
            addCriterion("bank_borrow_days >=", value, "bankBorrowDays");
            return (Criteria) this;
        }

        public Criteria andBankBorrowDaysLessThan(Integer value) {
            addCriterion("bank_borrow_days <", value, "bankBorrowDays");
            return (Criteria) this;
        }

        public Criteria andBankBorrowDaysLessThanOrEqualTo(Integer value) {
            addCriterion("bank_borrow_days <=", value, "bankBorrowDays");
            return (Criteria) this;
        }

        public Criteria andBankBorrowDaysIn(List<Integer> values) {
            addCriterion("bank_borrow_days in", values, "bankBorrowDays");
            return (Criteria) this;
        }

        public Criteria andBankBorrowDaysNotIn(List<Integer> values) {
            addCriterion("bank_borrow_days not in", values, "bankBorrowDays");
            return (Criteria) this;
        }

        public Criteria andBankBorrowDaysBetween(Integer value1, Integer value2) {
            addCriterion("bank_borrow_days between", value1, value2, "bankBorrowDays");
            return (Criteria) this;
        }

        public Criteria andBankBorrowDaysNotBetween(Integer value1, Integer value2) {
            addCriterion("bank_borrow_days not between", value1, value2, "bankBorrowDays");
            return (Criteria) this;
        }

        public Criteria andProjectNameIsNull() {
            addCriterion("project_name is null");
            return (Criteria) this;
        }

        public Criteria andProjectNameIsNotNull() {
            addCriterion("project_name is not null");
            return (Criteria) this;
        }

        public Criteria andProjectNameEqualTo(String value) {
            addCriterion("project_name =", value, "projectName");
            return (Criteria) this;
        }

        public Criteria andProjectNameNotEqualTo(String value) {
            addCriterion("project_name <>", value, "projectName");
            return (Criteria) this;
        }

        public Criteria andProjectNameGreaterThan(String value) {
            addCriterion("project_name >", value, "projectName");
            return (Criteria) this;
        }

        public Criteria andProjectNameGreaterThanOrEqualTo(String value) {
            addCriterion("project_name >=", value, "projectName");
            return (Criteria) this;
        }

        public Criteria andProjectNameLessThan(String value) {
            addCriterion("project_name <", value, "projectName");
            return (Criteria) this;
        }

        public Criteria andProjectNameLessThanOrEqualTo(String value) {
            addCriterion("project_name <=", value, "projectName");
            return (Criteria) this;
        }

        public Criteria andProjectNameLike(String value) {
            addCriterion("project_name like", value, "projectName");
            return (Criteria) this;
        }

        public Criteria andProjectNameNotLike(String value) {
            addCriterion("project_name not like", value, "projectName");
            return (Criteria) this;
        }

        public Criteria andProjectNameIn(List<String> values) {
            addCriterion("project_name in", values, "projectName");
            return (Criteria) this;
        }

        public Criteria andProjectNameNotIn(List<String> values) {
            addCriterion("project_name not in", values, "projectName");
            return (Criteria) this;
        }

        public Criteria andProjectNameBetween(String value1, String value2) {
            addCriterion("project_name between", value1, value2, "projectName");
            return (Criteria) this;
        }

        public Criteria andProjectNameNotBetween(String value1, String value2) {
            addCriterion("project_name not between", value1, value2, "projectName");
            return (Criteria) this;
        }

        public Criteria andFinancePurposeIsNull() {
            addCriterion("finance_purpose is null");
            return (Criteria) this;
        }

        public Criteria andFinancePurposeIsNotNull() {
            addCriterion("finance_purpose is not null");
            return (Criteria) this;
        }

        public Criteria andFinancePurposeEqualTo(String value) {
            addCriterion("finance_purpose =", value, "financePurpose");
            return (Criteria) this;
        }

        public Criteria andFinancePurposeNotEqualTo(String value) {
            addCriterion("finance_purpose <>", value, "financePurpose");
            return (Criteria) this;
        }

        public Criteria andFinancePurposeGreaterThan(String value) {
            addCriterion("finance_purpose >", value, "financePurpose");
            return (Criteria) this;
        }

        public Criteria andFinancePurposeGreaterThanOrEqualTo(String value) {
            addCriterion("finance_purpose >=", value, "financePurpose");
            return (Criteria) this;
        }

        public Criteria andFinancePurposeLessThan(String value) {
            addCriterion("finance_purpose <", value, "financePurpose");
            return (Criteria) this;
        }

        public Criteria andFinancePurposeLessThanOrEqualTo(String value) {
            addCriterion("finance_purpose <=", value, "financePurpose");
            return (Criteria) this;
        }

        public Criteria andFinancePurposeLike(String value) {
            addCriterion("finance_purpose like", value, "financePurpose");
            return (Criteria) this;
        }

        public Criteria andFinancePurposeNotLike(String value) {
            addCriterion("finance_purpose not like", value, "financePurpose");
            return (Criteria) this;
        }

        public Criteria andFinancePurposeIn(List<String> values) {
            addCriterion("finance_purpose in", values, "financePurpose");
            return (Criteria) this;
        }

        public Criteria andFinancePurposeNotIn(List<String> values) {
            addCriterion("finance_purpose not in", values, "financePurpose");
            return (Criteria) this;
        }

        public Criteria andFinancePurposeBetween(String value1, String value2) {
            addCriterion("finance_purpose between", value1, value2, "financePurpose");
            return (Criteria) this;
        }

        public Criteria andFinancePurposeNotBetween(String value1, String value2) {
            addCriterion("finance_purpose not between", value1, value2, "financePurpose");
            return (Criteria) this;
        }

        public Criteria andMonthlyIncomeIsNull() {
            addCriterion("monthly_income is null");
            return (Criteria) this;
        }

        public Criteria andMonthlyIncomeIsNotNull() {
            addCriterion("monthly_income is not null");
            return (Criteria) this;
        }

        public Criteria andMonthlyIncomeEqualTo(String value) {
            addCriterion("monthly_income =", value, "monthlyIncome");
            return (Criteria) this;
        }

        public Criteria andMonthlyIncomeNotEqualTo(String value) {
            addCriterion("monthly_income <>", value, "monthlyIncome");
            return (Criteria) this;
        }

        public Criteria andMonthlyIncomeGreaterThan(String value) {
            addCriterion("monthly_income >", value, "monthlyIncome");
            return (Criteria) this;
        }

        public Criteria andMonthlyIncomeGreaterThanOrEqualTo(String value) {
            addCriterion("monthly_income >=", value, "monthlyIncome");
            return (Criteria) this;
        }

        public Criteria andMonthlyIncomeLessThan(String value) {
            addCriterion("monthly_income <", value, "monthlyIncome");
            return (Criteria) this;
        }

        public Criteria andMonthlyIncomeLessThanOrEqualTo(String value) {
            addCriterion("monthly_income <=", value, "monthlyIncome");
            return (Criteria) this;
        }

        public Criteria andMonthlyIncomeLike(String value) {
            addCriterion("monthly_income like", value, "monthlyIncome");
            return (Criteria) this;
        }

        public Criteria andMonthlyIncomeNotLike(String value) {
            addCriterion("monthly_income not like", value, "monthlyIncome");
            return (Criteria) this;
        }

        public Criteria andMonthlyIncomeIn(List<String> values) {
            addCriterion("monthly_income in", values, "monthlyIncome");
            return (Criteria) this;
        }

        public Criteria andMonthlyIncomeNotIn(List<String> values) {
            addCriterion("monthly_income not in", values, "monthlyIncome");
            return (Criteria) this;
        }

        public Criteria andMonthlyIncomeBetween(String value1, String value2) {
            addCriterion("monthly_income between", value1, value2, "monthlyIncome");
            return (Criteria) this;
        }

        public Criteria andMonthlyIncomeNotBetween(String value1, String value2) {
            addCriterion("monthly_income not between", value1, value2, "monthlyIncome");
            return (Criteria) this;
        }

        public Criteria andPaymentIsNull() {
            addCriterion("payment is null");
            return (Criteria) this;
        }

        public Criteria andPaymentIsNotNull() {
            addCriterion("payment is not null");
            return (Criteria) this;
        }

        public Criteria andPaymentEqualTo(String value) {
            addCriterion("payment =", value, "payment");
            return (Criteria) this;
        }

        public Criteria andPaymentNotEqualTo(String value) {
            addCriterion("payment <>", value, "payment");
            return (Criteria) this;
        }

        public Criteria andPaymentGreaterThan(String value) {
            addCriterion("payment >", value, "payment");
            return (Criteria) this;
        }

        public Criteria andPaymentGreaterThanOrEqualTo(String value) {
            addCriterion("payment >=", value, "payment");
            return (Criteria) this;
        }

        public Criteria andPaymentLessThan(String value) {
            addCriterion("payment <", value, "payment");
            return (Criteria) this;
        }

        public Criteria andPaymentLessThanOrEqualTo(String value) {
            addCriterion("payment <=", value, "payment");
            return (Criteria) this;
        }

        public Criteria andPaymentLike(String value) {
            addCriterion("payment like", value, "payment");
            return (Criteria) this;
        }

        public Criteria andPaymentNotLike(String value) {
            addCriterion("payment not like", value, "payment");
            return (Criteria) this;
        }

        public Criteria andPaymentIn(List<String> values) {
            addCriterion("payment in", values, "payment");
            return (Criteria) this;
        }

        public Criteria andPaymentNotIn(List<String> values) {
            addCriterion("payment not in", values, "payment");
            return (Criteria) this;
        }

        public Criteria andPaymentBetween(String value1, String value2) {
            addCriterion("payment between", value1, value2, "payment");
            return (Criteria) this;
        }

        public Criteria andPaymentNotBetween(String value1, String value2) {
            addCriterion("payment not between", value1, value2, "payment");
            return (Criteria) this;
        }

        public Criteria andFirstPaymentIsNull() {
            addCriterion("first_payment is null");
            return (Criteria) this;
        }

        public Criteria andFirstPaymentIsNotNull() {
            addCriterion("first_payment is not null");
            return (Criteria) this;
        }

        public Criteria andFirstPaymentEqualTo(String value) {
            addCriterion("first_payment =", value, "firstPayment");
            return (Criteria) this;
        }

        public Criteria andFirstPaymentNotEqualTo(String value) {
            addCriterion("first_payment <>", value, "firstPayment");
            return (Criteria) this;
        }

        public Criteria andFirstPaymentGreaterThan(String value) {
            addCriterion("first_payment >", value, "firstPayment");
            return (Criteria) this;
        }

        public Criteria andFirstPaymentGreaterThanOrEqualTo(String value) {
            addCriterion("first_payment >=", value, "firstPayment");
            return (Criteria) this;
        }

        public Criteria andFirstPaymentLessThan(String value) {
            addCriterion("first_payment <", value, "firstPayment");
            return (Criteria) this;
        }

        public Criteria andFirstPaymentLessThanOrEqualTo(String value) {
            addCriterion("first_payment <=", value, "firstPayment");
            return (Criteria) this;
        }

        public Criteria andFirstPaymentLike(String value) {
            addCriterion("first_payment like", value, "firstPayment");
            return (Criteria) this;
        }

        public Criteria andFirstPaymentNotLike(String value) {
            addCriterion("first_payment not like", value, "firstPayment");
            return (Criteria) this;
        }

        public Criteria andFirstPaymentIn(List<String> values) {
            addCriterion("first_payment in", values, "firstPayment");
            return (Criteria) this;
        }

        public Criteria andFirstPaymentNotIn(List<String> values) {
            addCriterion("first_payment not in", values, "firstPayment");
            return (Criteria) this;
        }

        public Criteria andFirstPaymentBetween(String value1, String value2) {
            addCriterion("first_payment between", value1, value2, "firstPayment");
            return (Criteria) this;
        }

        public Criteria andFirstPaymentNotBetween(String value1, String value2) {
            addCriterion("first_payment not between", value1, value2, "firstPayment");
            return (Criteria) this;
        }

        public Criteria andSecondPaymentIsNull() {
            addCriterion("second_payment is null");
            return (Criteria) this;
        }

        public Criteria andSecondPaymentIsNotNull() {
            addCriterion("second_payment is not null");
            return (Criteria) this;
        }

        public Criteria andSecondPaymentEqualTo(String value) {
            addCriterion("second_payment =", value, "secondPayment");
            return (Criteria) this;
        }

        public Criteria andSecondPaymentNotEqualTo(String value) {
            addCriterion("second_payment <>", value, "secondPayment");
            return (Criteria) this;
        }

        public Criteria andSecondPaymentGreaterThan(String value) {
            addCriterion("second_payment >", value, "secondPayment");
            return (Criteria) this;
        }

        public Criteria andSecondPaymentGreaterThanOrEqualTo(String value) {
            addCriterion("second_payment >=", value, "secondPayment");
            return (Criteria) this;
        }

        public Criteria andSecondPaymentLessThan(String value) {
            addCriterion("second_payment <", value, "secondPayment");
            return (Criteria) this;
        }

        public Criteria andSecondPaymentLessThanOrEqualTo(String value) {
            addCriterion("second_payment <=", value, "secondPayment");
            return (Criteria) this;
        }

        public Criteria andSecondPaymentLike(String value) {
            addCriterion("second_payment like", value, "secondPayment");
            return (Criteria) this;
        }

        public Criteria andSecondPaymentNotLike(String value) {
            addCriterion("second_payment not like", value, "secondPayment");
            return (Criteria) this;
        }

        public Criteria andSecondPaymentIn(List<String> values) {
            addCriterion("second_payment in", values, "secondPayment");
            return (Criteria) this;
        }

        public Criteria andSecondPaymentNotIn(List<String> values) {
            addCriterion("second_payment not in", values, "secondPayment");
            return (Criteria) this;
        }

        public Criteria andSecondPaymentBetween(String value1, String value2) {
            addCriterion("second_payment between", value1, value2, "secondPayment");
            return (Criteria) this;
        }

        public Criteria andSecondPaymentNotBetween(String value1, String value2) {
            addCriterion("second_payment not between", value1, value2, "secondPayment");
            return (Criteria) this;
        }

        public Criteria andCostIntrodutionIsNull() {
            addCriterion("cost_introdution is null");
            return (Criteria) this;
        }

        public Criteria andCostIntrodutionIsNotNull() {
            addCriterion("cost_introdution is not null");
            return (Criteria) this;
        }

        public Criteria andCostIntrodutionEqualTo(String value) {
            addCriterion("cost_introdution =", value, "costIntrodution");
            return (Criteria) this;
        }

        public Criteria andCostIntrodutionNotEqualTo(String value) {
            addCriterion("cost_introdution <>", value, "costIntrodution");
            return (Criteria) this;
        }

        public Criteria andCostIntrodutionGreaterThan(String value) {
            addCriterion("cost_introdution >", value, "costIntrodution");
            return (Criteria) this;
        }

        public Criteria andCostIntrodutionGreaterThanOrEqualTo(String value) {
            addCriterion("cost_introdution >=", value, "costIntrodution");
            return (Criteria) this;
        }

        public Criteria andCostIntrodutionLessThan(String value) {
            addCriterion("cost_introdution <", value, "costIntrodution");
            return (Criteria) this;
        }

        public Criteria andCostIntrodutionLessThanOrEqualTo(String value) {
            addCriterion("cost_introdution <=", value, "costIntrodution");
            return (Criteria) this;
        }

        public Criteria andCostIntrodutionLike(String value) {
            addCriterion("cost_introdution like", value, "costIntrodution");
            return (Criteria) this;
        }

        public Criteria andCostIntrodutionNotLike(String value) {
            addCriterion("cost_introdution not like", value, "costIntrodution");
            return (Criteria) this;
        }

        public Criteria andCostIntrodutionIn(List<String> values) {
            addCriterion("cost_introdution in", values, "costIntrodution");
            return (Criteria) this;
        }

        public Criteria andCostIntrodutionNotIn(List<String> values) {
            addCriterion("cost_introdution not in", values, "costIntrodution");
            return (Criteria) this;
        }

        public Criteria andCostIntrodutionBetween(String value1, String value2) {
            addCriterion("cost_introdution between", value1, value2, "costIntrodution");
            return (Criteria) this;
        }

        public Criteria andCostIntrodutionNotBetween(String value1, String value2) {
            addCriterion("cost_introdution not between", value1, value2, "costIntrodution");
            return (Criteria) this;
        }

        public Criteria andFianceConditionIsNull() {
            addCriterion("fiance_condition is null");
            return (Criteria) this;
        }

        public Criteria andFianceConditionIsNotNull() {
            addCriterion("fiance_condition is not null");
            return (Criteria) this;
        }

        public Criteria andFianceConditionEqualTo(String value) {
            addCriterion("fiance_condition =", value, "fianceCondition");
            return (Criteria) this;
        }

        public Criteria andFianceConditionNotEqualTo(String value) {
            addCriterion("fiance_condition <>", value, "fianceCondition");
            return (Criteria) this;
        }

        public Criteria andFianceConditionGreaterThan(String value) {
            addCriterion("fiance_condition >", value, "fianceCondition");
            return (Criteria) this;
        }

        public Criteria andFianceConditionGreaterThanOrEqualTo(String value) {
            addCriterion("fiance_condition >=", value, "fianceCondition");
            return (Criteria) this;
        }

        public Criteria andFianceConditionLessThan(String value) {
            addCriterion("fiance_condition <", value, "fianceCondition");
            return (Criteria) this;
        }

        public Criteria andFianceConditionLessThanOrEqualTo(String value) {
            addCriterion("fiance_condition <=", value, "fianceCondition");
            return (Criteria) this;
        }

        public Criteria andFianceConditionLike(String value) {
            addCriterion("fiance_condition like", value, "fianceCondition");
            return (Criteria) this;
        }

        public Criteria andFianceConditionNotLike(String value) {
            addCriterion("fiance_condition not like", value, "fianceCondition");
            return (Criteria) this;
        }

        public Criteria andFianceConditionIn(List<String> values) {
            addCriterion("fiance_condition in", values, "fianceCondition");
            return (Criteria) this;
        }

        public Criteria andFianceConditionNotIn(List<String> values) {
            addCriterion("fiance_condition not in", values, "fianceCondition");
            return (Criteria) this;
        }

        public Criteria andFianceConditionBetween(String value1, String value2) {
            addCriterion("fiance_condition between", value1, value2, "fianceCondition");
            return (Criteria) this;
        }

        public Criteria andFianceConditionNotBetween(String value1, String value2) {
            addCriterion("fiance_condition not between", value1, value2, "fianceCondition");
            return (Criteria) this;
        }

        public Criteria andIsNewIsNull() {
            addCriterion("is_new is null");
            return (Criteria) this;
        }

        public Criteria andIsNewIsNotNull() {
            addCriterion("is_new is not null");
            return (Criteria) this;
        }

        public Criteria andIsNewEqualTo(Integer value) {
            addCriterion("is_new =", value, "isNew");
            return (Criteria) this;
        }

        public Criteria andIsNewNotEqualTo(Integer value) {
            addCriterion("is_new <>", value, "isNew");
            return (Criteria) this;
        }

        public Criteria andIsNewGreaterThan(Integer value) {
            addCriterion("is_new >", value, "isNew");
            return (Criteria) this;
        }

        public Criteria andIsNewGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_new >=", value, "isNew");
            return (Criteria) this;
        }

        public Criteria andIsNewLessThan(Integer value) {
            addCriterion("is_new <", value, "isNew");
            return (Criteria) this;
        }

        public Criteria andIsNewLessThanOrEqualTo(Integer value) {
            addCriterion("is_new <=", value, "isNew");
            return (Criteria) this;
        }

        public Criteria andIsNewIn(List<Integer> values) {
            addCriterion("is_new in", values, "isNew");
            return (Criteria) this;
        }

        public Criteria andIsNewNotIn(List<Integer> values) {
            addCriterion("is_new not in", values, "isNew");
            return (Criteria) this;
        }

        public Criteria andIsNewBetween(Integer value1, Integer value2) {
            addCriterion("is_new between", value1, value2, "isNew");
            return (Criteria) this;
        }

        public Criteria andIsNewNotBetween(Integer value1, Integer value2) {
            addCriterion("is_new not between", value1, value2, "isNew");
            return (Criteria) this;
        }

        public Criteria andPublishInstCodeIsNull() {
            addCriterion("publish_inst_code is null");
            return (Criteria) this;
        }

        public Criteria andPublishInstCodeIsNotNull() {
            addCriterion("publish_inst_code is not null");
            return (Criteria) this;
        }

        public Criteria andPublishInstCodeEqualTo(String value) {
            addCriterion("publish_inst_code =", value, "publishInstCode");
            return (Criteria) this;
        }

        public Criteria andPublishInstCodeNotEqualTo(String value) {
            addCriterion("publish_inst_code <>", value, "publishInstCode");
            return (Criteria) this;
        }

        public Criteria andPublishInstCodeGreaterThan(String value) {
            addCriterion("publish_inst_code >", value, "publishInstCode");
            return (Criteria) this;
        }

        public Criteria andPublishInstCodeGreaterThanOrEqualTo(String value) {
            addCriterion("publish_inst_code >=", value, "publishInstCode");
            return (Criteria) this;
        }

        public Criteria andPublishInstCodeLessThan(String value) {
            addCriterion("publish_inst_code <", value, "publishInstCode");
            return (Criteria) this;
        }

        public Criteria andPublishInstCodeLessThanOrEqualTo(String value) {
            addCriterion("publish_inst_code <=", value, "publishInstCode");
            return (Criteria) this;
        }

        public Criteria andPublishInstCodeLike(String value) {
            addCriterion("publish_inst_code like", value, "publishInstCode");
            return (Criteria) this;
        }

        public Criteria andPublishInstCodeNotLike(String value) {
            addCriterion("publish_inst_code not like", value, "publishInstCode");
            return (Criteria) this;
        }

        public Criteria andPublishInstCodeIn(List<String> values) {
            addCriterion("publish_inst_code in", values, "publishInstCode");
            return (Criteria) this;
        }

        public Criteria andPublishInstCodeNotIn(List<String> values) {
            addCriterion("publish_inst_code not in", values, "publishInstCode");
            return (Criteria) this;
        }

        public Criteria andPublishInstCodeBetween(String value1, String value2) {
            addCriterion("publish_inst_code between", value1, value2, "publishInstCode");
            return (Criteria) this;
        }

        public Criteria andPublishInstCodeNotBetween(String value1, String value2) {
            addCriterion("publish_inst_code not between", value1, value2, "publishInstCode");
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