package com.hyjf.am.trade.dao.model.auto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DebtBorrowExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public DebtBorrowExample() {
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

        public Criteria andOrderIsNull() {
            addCriterion("`order` is null");
            return (Criteria) this;
        }

        public Criteria andOrderIsNotNull() {
            addCriterion("`order` is not null");
            return (Criteria) this;
        }

        public Criteria andOrderEqualTo(Integer value) {
            addCriterion("`order` =", value, "order");
            return (Criteria) this;
        }

        public Criteria andOrderNotEqualTo(Integer value) {
            addCriterion("`order` <>", value, "order");
            return (Criteria) this;
        }

        public Criteria andOrderGreaterThan(Integer value) {
            addCriterion("`order` >", value, "order");
            return (Criteria) this;
        }

        public Criteria andOrderGreaterThanOrEqualTo(Integer value) {
            addCriterion("`order` >=", value, "order");
            return (Criteria) this;
        }

        public Criteria andOrderLessThan(Integer value) {
            addCriterion("`order` <", value, "order");
            return (Criteria) this;
        }

        public Criteria andOrderLessThanOrEqualTo(Integer value) {
            addCriterion("`order` <=", value, "order");
            return (Criteria) this;
        }

        public Criteria andOrderIn(List<Integer> values) {
            addCriterion("`order` in", values, "order");
            return (Criteria) this;
        }

        public Criteria andOrderNotIn(List<Integer> values) {
            addCriterion("`order` not in", values, "order");
            return (Criteria) this;
        }

        public Criteria andOrderBetween(Integer value1, Integer value2) {
            addCriterion("`order` between", value1, value2, "order");
            return (Criteria) this;
        }

        public Criteria andOrderNotBetween(Integer value1, Integer value2) {
            addCriterion("`order` not between", value1, value2, "order");
            return (Criteria) this;
        }

        public Criteria andBorrowPicIsNull() {
            addCriterion("borrow_pic is null");
            return (Criteria) this;
        }

        public Criteria andBorrowPicIsNotNull() {
            addCriterion("borrow_pic is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowPicEqualTo(String value) {
            addCriterion("borrow_pic =", value, "borrowPic");
            return (Criteria) this;
        }

        public Criteria andBorrowPicNotEqualTo(String value) {
            addCriterion("borrow_pic <>", value, "borrowPic");
            return (Criteria) this;
        }

        public Criteria andBorrowPicGreaterThan(String value) {
            addCriterion("borrow_pic >", value, "borrowPic");
            return (Criteria) this;
        }

        public Criteria andBorrowPicGreaterThanOrEqualTo(String value) {
            addCriterion("borrow_pic >=", value, "borrowPic");
            return (Criteria) this;
        }

        public Criteria andBorrowPicLessThan(String value) {
            addCriterion("borrow_pic <", value, "borrowPic");
            return (Criteria) this;
        }

        public Criteria andBorrowPicLessThanOrEqualTo(String value) {
            addCriterion("borrow_pic <=", value, "borrowPic");
            return (Criteria) this;
        }

        public Criteria andBorrowPicLike(String value) {
            addCriterion("borrow_pic like", value, "borrowPic");
            return (Criteria) this;
        }

        public Criteria andBorrowPicNotLike(String value) {
            addCriterion("borrow_pic not like", value, "borrowPic");
            return (Criteria) this;
        }

        public Criteria andBorrowPicIn(List<String> values) {
            addCriterion("borrow_pic in", values, "borrowPic");
            return (Criteria) this;
        }

        public Criteria andBorrowPicNotIn(List<String> values) {
            addCriterion("borrow_pic not in", values, "borrowPic");
            return (Criteria) this;
        }

        public Criteria andBorrowPicBetween(String value1, String value2) {
            addCriterion("borrow_pic between", value1, value2, "borrowPic");
            return (Criteria) this;
        }

        public Criteria andBorrowPicNotBetween(String value1, String value2) {
            addCriterion("borrow_pic not between", value1, value2, "borrowPic");
            return (Criteria) this;
        }

        public Criteria andHitsIsNull() {
            addCriterion("hits is null");
            return (Criteria) this;
        }

        public Criteria andHitsIsNotNull() {
            addCriterion("hits is not null");
            return (Criteria) this;
        }

        public Criteria andHitsEqualTo(Integer value) {
            addCriterion("hits =", value, "hits");
            return (Criteria) this;
        }

        public Criteria andHitsNotEqualTo(Integer value) {
            addCriterion("hits <>", value, "hits");
            return (Criteria) this;
        }

        public Criteria andHitsGreaterThan(Integer value) {
            addCriterion("hits >", value, "hits");
            return (Criteria) this;
        }

        public Criteria andHitsGreaterThanOrEqualTo(Integer value) {
            addCriterion("hits >=", value, "hits");
            return (Criteria) this;
        }

        public Criteria andHitsLessThan(Integer value) {
            addCriterion("hits <", value, "hits");
            return (Criteria) this;
        }

        public Criteria andHitsLessThanOrEqualTo(Integer value) {
            addCriterion("hits <=", value, "hits");
            return (Criteria) this;
        }

        public Criteria andHitsIn(List<Integer> values) {
            addCriterion("hits in", values, "hits");
            return (Criteria) this;
        }

        public Criteria andHitsNotIn(List<Integer> values) {
            addCriterion("hits not in", values, "hits");
            return (Criteria) this;
        }

        public Criteria andHitsBetween(Integer value1, Integer value2) {
            addCriterion("hits between", value1, value2, "hits");
            return (Criteria) this;
        }

        public Criteria andHitsNotBetween(Integer value1, Integer value2) {
            addCriterion("hits not between", value1, value2, "hits");
            return (Criteria) this;
        }

        public Criteria andCommentCountIsNull() {
            addCriterion("comment_count is null");
            return (Criteria) this;
        }

        public Criteria andCommentCountIsNotNull() {
            addCriterion("comment_count is not null");
            return (Criteria) this;
        }

        public Criteria andCommentCountEqualTo(Integer value) {
            addCriterion("comment_count =", value, "commentCount");
            return (Criteria) this;
        }

        public Criteria andCommentCountNotEqualTo(Integer value) {
            addCriterion("comment_count <>", value, "commentCount");
            return (Criteria) this;
        }

        public Criteria andCommentCountGreaterThan(Integer value) {
            addCriterion("comment_count >", value, "commentCount");
            return (Criteria) this;
        }

        public Criteria andCommentCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("comment_count >=", value, "commentCount");
            return (Criteria) this;
        }

        public Criteria andCommentCountLessThan(Integer value) {
            addCriterion("comment_count <", value, "commentCount");
            return (Criteria) this;
        }

        public Criteria andCommentCountLessThanOrEqualTo(Integer value) {
            addCriterion("comment_count <=", value, "commentCount");
            return (Criteria) this;
        }

        public Criteria andCommentCountIn(List<Integer> values) {
            addCriterion("comment_count in", values, "commentCount");
            return (Criteria) this;
        }

        public Criteria andCommentCountNotIn(List<Integer> values) {
            addCriterion("comment_count not in", values, "commentCount");
            return (Criteria) this;
        }

        public Criteria andCommentCountBetween(Integer value1, Integer value2) {
            addCriterion("comment_count between", value1, value2, "commentCount");
            return (Criteria) this;
        }

        public Criteria andCommentCountNotBetween(Integer value1, Integer value2) {
            addCriterion("comment_count not between", value1, value2, "commentCount");
            return (Criteria) this;
        }

        public Criteria andLitpicIsNull() {
            addCriterion("litpic is null");
            return (Criteria) this;
        }

        public Criteria andLitpicIsNotNull() {
            addCriterion("litpic is not null");
            return (Criteria) this;
        }

        public Criteria andLitpicEqualTo(String value) {
            addCriterion("litpic =", value, "litpic");
            return (Criteria) this;
        }

        public Criteria andLitpicNotEqualTo(String value) {
            addCriterion("litpic <>", value, "litpic");
            return (Criteria) this;
        }

        public Criteria andLitpicGreaterThan(String value) {
            addCriterion("litpic >", value, "litpic");
            return (Criteria) this;
        }

        public Criteria andLitpicGreaterThanOrEqualTo(String value) {
            addCriterion("litpic >=", value, "litpic");
            return (Criteria) this;
        }

        public Criteria andLitpicLessThan(String value) {
            addCriterion("litpic <", value, "litpic");
            return (Criteria) this;
        }

        public Criteria andLitpicLessThanOrEqualTo(String value) {
            addCriterion("litpic <=", value, "litpic");
            return (Criteria) this;
        }

        public Criteria andLitpicLike(String value) {
            addCriterion("litpic like", value, "litpic");
            return (Criteria) this;
        }

        public Criteria andLitpicNotLike(String value) {
            addCriterion("litpic not like", value, "litpic");
            return (Criteria) this;
        }

        public Criteria andLitpicIn(List<String> values) {
            addCriterion("litpic in", values, "litpic");
            return (Criteria) this;
        }

        public Criteria andLitpicNotIn(List<String> values) {
            addCriterion("litpic not in", values, "litpic");
            return (Criteria) this;
        }

        public Criteria andLitpicBetween(String value1, String value2) {
            addCriterion("litpic between", value1, value2, "litpic");
            return (Criteria) this;
        }

        public Criteria andLitpicNotBetween(String value1, String value2) {
            addCriterion("litpic not between", value1, value2, "litpic");
            return (Criteria) this;
        }

        public Criteria andFlagIsNull() {
            addCriterion("flag is null");
            return (Criteria) this;
        }

        public Criteria andFlagIsNotNull() {
            addCriterion("flag is not null");
            return (Criteria) this;
        }

        public Criteria andFlagEqualTo(String value) {
            addCriterion("flag =", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagNotEqualTo(String value) {
            addCriterion("flag <>", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagGreaterThan(String value) {
            addCriterion("flag >", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagGreaterThanOrEqualTo(String value) {
            addCriterion("flag >=", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagLessThan(String value) {
            addCriterion("flag <", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagLessThanOrEqualTo(String value) {
            addCriterion("flag <=", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagLike(String value) {
            addCriterion("flag like", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagNotLike(String value) {
            addCriterion("flag not like", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagIn(List<String> values) {
            addCriterion("flag in", values, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagNotIn(List<String> values) {
            addCriterion("flag not in", values, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagBetween(String value1, String value2) {
            addCriterion("flag between", value1, value2, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagNotBetween(String value1, String value2) {
            addCriterion("flag not between", value1, value2, "flag");
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

        public Criteria andViewTypeIsNull() {
            addCriterion("view_type is null");
            return (Criteria) this;
        }

        public Criteria andViewTypeIsNotNull() {
            addCriterion("view_type is not null");
            return (Criteria) this;
        }

        public Criteria andViewTypeEqualTo(String value) {
            addCriterion("view_type =", value, "viewType");
            return (Criteria) this;
        }

        public Criteria andViewTypeNotEqualTo(String value) {
            addCriterion("view_type <>", value, "viewType");
            return (Criteria) this;
        }

        public Criteria andViewTypeGreaterThan(String value) {
            addCriterion("view_type >", value, "viewType");
            return (Criteria) this;
        }

        public Criteria andViewTypeGreaterThanOrEqualTo(String value) {
            addCriterion("view_type >=", value, "viewType");
            return (Criteria) this;
        }

        public Criteria andViewTypeLessThan(String value) {
            addCriterion("view_type <", value, "viewType");
            return (Criteria) this;
        }

        public Criteria andViewTypeLessThanOrEqualTo(String value) {
            addCriterion("view_type <=", value, "viewType");
            return (Criteria) this;
        }

        public Criteria andViewTypeLike(String value) {
            addCriterion("view_type like", value, "viewType");
            return (Criteria) this;
        }

        public Criteria andViewTypeNotLike(String value) {
            addCriterion("view_type not like", value, "viewType");
            return (Criteria) this;
        }

        public Criteria andViewTypeIn(List<String> values) {
            addCriterion("view_type in", values, "viewType");
            return (Criteria) this;
        }

        public Criteria andViewTypeNotIn(List<String> values) {
            addCriterion("view_type not in", values, "viewType");
            return (Criteria) this;
        }

        public Criteria andViewTypeBetween(String value1, String value2) {
            addCriterion("view_type between", value1, value2, "viewType");
            return (Criteria) this;
        }

        public Criteria andViewTypeNotBetween(String value1, String value2) {
            addCriterion("view_type not between", value1, value2, "viewType");
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

        public Criteria andAmountAccountIsNull() {
            addCriterion("amount_account is null");
            return (Criteria) this;
        }

        public Criteria andAmountAccountIsNotNull() {
            addCriterion("amount_account is not null");
            return (Criteria) this;
        }

        public Criteria andAmountAccountEqualTo(BigDecimal value) {
            addCriterion("amount_account =", value, "amountAccount");
            return (Criteria) this;
        }

        public Criteria andAmountAccountNotEqualTo(BigDecimal value) {
            addCriterion("amount_account <>", value, "amountAccount");
            return (Criteria) this;
        }

        public Criteria andAmountAccountGreaterThan(BigDecimal value) {
            addCriterion("amount_account >", value, "amountAccount");
            return (Criteria) this;
        }

        public Criteria andAmountAccountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("amount_account >=", value, "amountAccount");
            return (Criteria) this;
        }

        public Criteria andAmountAccountLessThan(BigDecimal value) {
            addCriterion("amount_account <", value, "amountAccount");
            return (Criteria) this;
        }

        public Criteria andAmountAccountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("amount_account <=", value, "amountAccount");
            return (Criteria) this;
        }

        public Criteria andAmountAccountIn(List<BigDecimal> values) {
            addCriterion("amount_account in", values, "amountAccount");
            return (Criteria) this;
        }

        public Criteria andAmountAccountNotIn(List<BigDecimal> values) {
            addCriterion("amount_account not in", values, "amountAccount");
            return (Criteria) this;
        }

        public Criteria andAmountAccountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("amount_account between", value1, value2, "amountAccount");
            return (Criteria) this;
        }

        public Criteria andAmountAccountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("amount_account not between", value1, value2, "amountAccount");
            return (Criteria) this;
        }

        public Criteria andAmountTypeIsNull() {
            addCriterion("amount_type is null");
            return (Criteria) this;
        }

        public Criteria andAmountTypeIsNotNull() {
            addCriterion("amount_type is not null");
            return (Criteria) this;
        }

        public Criteria andAmountTypeEqualTo(String value) {
            addCriterion("amount_type =", value, "amountType");
            return (Criteria) this;
        }

        public Criteria andAmountTypeNotEqualTo(String value) {
            addCriterion("amount_type <>", value, "amountType");
            return (Criteria) this;
        }

        public Criteria andAmountTypeGreaterThan(String value) {
            addCriterion("amount_type >", value, "amountType");
            return (Criteria) this;
        }

        public Criteria andAmountTypeGreaterThanOrEqualTo(String value) {
            addCriterion("amount_type >=", value, "amountType");
            return (Criteria) this;
        }

        public Criteria andAmountTypeLessThan(String value) {
            addCriterion("amount_type <", value, "amountType");
            return (Criteria) this;
        }

        public Criteria andAmountTypeLessThanOrEqualTo(String value) {
            addCriterion("amount_type <=", value, "amountType");
            return (Criteria) this;
        }

        public Criteria andAmountTypeLike(String value) {
            addCriterion("amount_type like", value, "amountType");
            return (Criteria) this;
        }

        public Criteria andAmountTypeNotLike(String value) {
            addCriterion("amount_type not like", value, "amountType");
            return (Criteria) this;
        }

        public Criteria andAmountTypeIn(List<String> values) {
            addCriterion("amount_type in", values, "amountType");
            return (Criteria) this;
        }

        public Criteria andAmountTypeNotIn(List<String> values) {
            addCriterion("amount_type not in", values, "amountType");
            return (Criteria) this;
        }

        public Criteria andAmountTypeBetween(String value1, String value2) {
            addCriterion("amount_type between", value1, value2, "amountType");
            return (Criteria) this;
        }

        public Criteria andAmountTypeNotBetween(String value1, String value2) {
            addCriterion("amount_type not between", value1, value2, "amountType");
            return (Criteria) this;
        }

        public Criteria andCashStatusIsNull() {
            addCriterion("cash_status is null");
            return (Criteria) this;
        }

        public Criteria andCashStatusIsNotNull() {
            addCriterion("cash_status is not null");
            return (Criteria) this;
        }

        public Criteria andCashStatusEqualTo(Integer value) {
            addCriterion("cash_status =", value, "cashStatus");
            return (Criteria) this;
        }

        public Criteria andCashStatusNotEqualTo(Integer value) {
            addCriterion("cash_status <>", value, "cashStatus");
            return (Criteria) this;
        }

        public Criteria andCashStatusGreaterThan(Integer value) {
            addCriterion("cash_status >", value, "cashStatus");
            return (Criteria) this;
        }

        public Criteria andCashStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("cash_status >=", value, "cashStatus");
            return (Criteria) this;
        }

        public Criteria andCashStatusLessThan(Integer value) {
            addCriterion("cash_status <", value, "cashStatus");
            return (Criteria) this;
        }

        public Criteria andCashStatusLessThanOrEqualTo(Integer value) {
            addCriterion("cash_status <=", value, "cashStatus");
            return (Criteria) this;
        }

        public Criteria andCashStatusIn(List<Integer> values) {
            addCriterion("cash_status in", values, "cashStatus");
            return (Criteria) this;
        }

        public Criteria andCashStatusNotIn(List<Integer> values) {
            addCriterion("cash_status not in", values, "cashStatus");
            return (Criteria) this;
        }

        public Criteria andCashStatusBetween(Integer value1, Integer value2) {
            addCriterion("cash_status between", value1, value2, "cashStatus");
            return (Criteria) this;
        }

        public Criteria andCashStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("cash_status not between", value1, value2, "cashStatus");
            return (Criteria) this;
        }

        public Criteria andAccountIsNull() {
            addCriterion("account is null");
            return (Criteria) this;
        }

        public Criteria andAccountIsNotNull() {
            addCriterion("account is not null");
            return (Criteria) this;
        }

        public Criteria andAccountEqualTo(BigDecimal value) {
            addCriterion("account =", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountNotEqualTo(BigDecimal value) {
            addCriterion("account <>", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountGreaterThan(BigDecimal value) {
            addCriterion("account >", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("account >=", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountLessThan(BigDecimal value) {
            addCriterion("account <", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("account <=", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountIn(List<BigDecimal> values) {
            addCriterion("account in", values, "account");
            return (Criteria) this;
        }

        public Criteria andAccountNotIn(List<BigDecimal> values) {
            addCriterion("account not in", values, "account");
            return (Criteria) this;
        }

        public Criteria andAccountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("account between", value1, value2, "account");
            return (Criteria) this;
        }

        public Criteria andAccountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("account not between", value1, value2, "account");
            return (Criteria) this;
        }

        public Criteria andOtherWebStatusIsNull() {
            addCriterion("other_web_status is null");
            return (Criteria) this;
        }

        public Criteria andOtherWebStatusIsNotNull() {
            addCriterion("other_web_status is not null");
            return (Criteria) this;
        }

        public Criteria andOtherWebStatusEqualTo(Integer value) {
            addCriterion("other_web_status =", value, "otherWebStatus");
            return (Criteria) this;
        }

        public Criteria andOtherWebStatusNotEqualTo(Integer value) {
            addCriterion("other_web_status <>", value, "otherWebStatus");
            return (Criteria) this;
        }

        public Criteria andOtherWebStatusGreaterThan(Integer value) {
            addCriterion("other_web_status >", value, "otherWebStatus");
            return (Criteria) this;
        }

        public Criteria andOtherWebStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("other_web_status >=", value, "otherWebStatus");
            return (Criteria) this;
        }

        public Criteria andOtherWebStatusLessThan(Integer value) {
            addCriterion("other_web_status <", value, "otherWebStatus");
            return (Criteria) this;
        }

        public Criteria andOtherWebStatusLessThanOrEqualTo(Integer value) {
            addCriterion("other_web_status <=", value, "otherWebStatus");
            return (Criteria) this;
        }

        public Criteria andOtherWebStatusIn(List<Integer> values) {
            addCriterion("other_web_status in", values, "otherWebStatus");
            return (Criteria) this;
        }

        public Criteria andOtherWebStatusNotIn(List<Integer> values) {
            addCriterion("other_web_status not in", values, "otherWebStatus");
            return (Criteria) this;
        }

        public Criteria andOtherWebStatusBetween(Integer value1, Integer value2) {
            addCriterion("other_web_status between", value1, value2, "otherWebStatus");
            return (Criteria) this;
        }

        public Criteria andOtherWebStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("other_web_status not between", value1, value2, "otherWebStatus");
            return (Criteria) this;
        }

        public Criteria andBorrowTypeIsNull() {
            addCriterion("borrow_type is null");
            return (Criteria) this;
        }

        public Criteria andBorrowTypeIsNotNull() {
            addCriterion("borrow_type is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowTypeEqualTo(String value) {
            addCriterion("borrow_type =", value, "borrowType");
            return (Criteria) this;
        }

        public Criteria andBorrowTypeNotEqualTo(String value) {
            addCriterion("borrow_type <>", value, "borrowType");
            return (Criteria) this;
        }

        public Criteria andBorrowTypeGreaterThan(String value) {
            addCriterion("borrow_type >", value, "borrowType");
            return (Criteria) this;
        }

        public Criteria andBorrowTypeGreaterThanOrEqualTo(String value) {
            addCriterion("borrow_type >=", value, "borrowType");
            return (Criteria) this;
        }

        public Criteria andBorrowTypeLessThan(String value) {
            addCriterion("borrow_type <", value, "borrowType");
            return (Criteria) this;
        }

        public Criteria andBorrowTypeLessThanOrEqualTo(String value) {
            addCriterion("borrow_type <=", value, "borrowType");
            return (Criteria) this;
        }

        public Criteria andBorrowTypeLike(String value) {
            addCriterion("borrow_type like", value, "borrowType");
            return (Criteria) this;
        }

        public Criteria andBorrowTypeNotLike(String value) {
            addCriterion("borrow_type not like", value, "borrowType");
            return (Criteria) this;
        }

        public Criteria andBorrowTypeIn(List<String> values) {
            addCriterion("borrow_type in", values, "borrowType");
            return (Criteria) this;
        }

        public Criteria andBorrowTypeNotIn(List<String> values) {
            addCriterion("borrow_type not in", values, "borrowType");
            return (Criteria) this;
        }

        public Criteria andBorrowTypeBetween(String value1, String value2) {
            addCriterion("borrow_type between", value1, value2, "borrowType");
            return (Criteria) this;
        }

        public Criteria andBorrowTypeNotBetween(String value1, String value2) {
            addCriterion("borrow_type not between", value1, value2, "borrowType");
            return (Criteria) this;
        }

        public Criteria andBorrowPasswordIsNull() {
            addCriterion("borrow_password is null");
            return (Criteria) this;
        }

        public Criteria andBorrowPasswordIsNotNull() {
            addCriterion("borrow_password is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowPasswordEqualTo(String value) {
            addCriterion("borrow_password =", value, "borrowPassword");
            return (Criteria) this;
        }

        public Criteria andBorrowPasswordNotEqualTo(String value) {
            addCriterion("borrow_password <>", value, "borrowPassword");
            return (Criteria) this;
        }

        public Criteria andBorrowPasswordGreaterThan(String value) {
            addCriterion("borrow_password >", value, "borrowPassword");
            return (Criteria) this;
        }

        public Criteria andBorrowPasswordGreaterThanOrEqualTo(String value) {
            addCriterion("borrow_password >=", value, "borrowPassword");
            return (Criteria) this;
        }

        public Criteria andBorrowPasswordLessThan(String value) {
            addCriterion("borrow_password <", value, "borrowPassword");
            return (Criteria) this;
        }

        public Criteria andBorrowPasswordLessThanOrEqualTo(String value) {
            addCriterion("borrow_password <=", value, "borrowPassword");
            return (Criteria) this;
        }

        public Criteria andBorrowPasswordLike(String value) {
            addCriterion("borrow_password like", value, "borrowPassword");
            return (Criteria) this;
        }

        public Criteria andBorrowPasswordNotLike(String value) {
            addCriterion("borrow_password not like", value, "borrowPassword");
            return (Criteria) this;
        }

        public Criteria andBorrowPasswordIn(List<String> values) {
            addCriterion("borrow_password in", values, "borrowPassword");
            return (Criteria) this;
        }

        public Criteria andBorrowPasswordNotIn(List<String> values) {
            addCriterion("borrow_password not in", values, "borrowPassword");
            return (Criteria) this;
        }

        public Criteria andBorrowPasswordBetween(String value1, String value2) {
            addCriterion("borrow_password between", value1, value2, "borrowPassword");
            return (Criteria) this;
        }

        public Criteria andBorrowPasswordNotBetween(String value1, String value2) {
            addCriterion("borrow_password not between", value1, value2, "borrowPassword");
            return (Criteria) this;
        }

        public Criteria andBorrowFlagIsNull() {
            addCriterion("borrow_flag is null");
            return (Criteria) this;
        }

        public Criteria andBorrowFlagIsNotNull() {
            addCriterion("borrow_flag is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowFlagEqualTo(String value) {
            addCriterion("borrow_flag =", value, "borrowFlag");
            return (Criteria) this;
        }

        public Criteria andBorrowFlagNotEqualTo(String value) {
            addCriterion("borrow_flag <>", value, "borrowFlag");
            return (Criteria) this;
        }

        public Criteria andBorrowFlagGreaterThan(String value) {
            addCriterion("borrow_flag >", value, "borrowFlag");
            return (Criteria) this;
        }

        public Criteria andBorrowFlagGreaterThanOrEqualTo(String value) {
            addCriterion("borrow_flag >=", value, "borrowFlag");
            return (Criteria) this;
        }

        public Criteria andBorrowFlagLessThan(String value) {
            addCriterion("borrow_flag <", value, "borrowFlag");
            return (Criteria) this;
        }

        public Criteria andBorrowFlagLessThanOrEqualTo(String value) {
            addCriterion("borrow_flag <=", value, "borrowFlag");
            return (Criteria) this;
        }

        public Criteria andBorrowFlagLike(String value) {
            addCriterion("borrow_flag like", value, "borrowFlag");
            return (Criteria) this;
        }

        public Criteria andBorrowFlagNotLike(String value) {
            addCriterion("borrow_flag not like", value, "borrowFlag");
            return (Criteria) this;
        }

        public Criteria andBorrowFlagIn(List<String> values) {
            addCriterion("borrow_flag in", values, "borrowFlag");
            return (Criteria) this;
        }

        public Criteria andBorrowFlagNotIn(List<String> values) {
            addCriterion("borrow_flag not in", values, "borrowFlag");
            return (Criteria) this;
        }

        public Criteria andBorrowFlagBetween(String value1, String value2) {
            addCriterion("borrow_flag between", value1, value2, "borrowFlag");
            return (Criteria) this;
        }

        public Criteria andBorrowFlagNotBetween(String value1, String value2) {
            addCriterion("borrow_flag not between", value1, value2, "borrowFlag");
            return (Criteria) this;
        }

        public Criteria andBorrowStatusIsNull() {
            addCriterion("borrow_status is null");
            return (Criteria) this;
        }

        public Criteria andBorrowStatusIsNotNull() {
            addCriterion("borrow_status is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowStatusEqualTo(Integer value) {
            addCriterion("borrow_status =", value, "borrowStatus");
            return (Criteria) this;
        }

        public Criteria andBorrowStatusNotEqualTo(Integer value) {
            addCriterion("borrow_status <>", value, "borrowStatus");
            return (Criteria) this;
        }

        public Criteria andBorrowStatusGreaterThan(Integer value) {
            addCriterion("borrow_status >", value, "borrowStatus");
            return (Criteria) this;
        }

        public Criteria andBorrowStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("borrow_status >=", value, "borrowStatus");
            return (Criteria) this;
        }

        public Criteria andBorrowStatusLessThan(Integer value) {
            addCriterion("borrow_status <", value, "borrowStatus");
            return (Criteria) this;
        }

        public Criteria andBorrowStatusLessThanOrEqualTo(Integer value) {
            addCriterion("borrow_status <=", value, "borrowStatus");
            return (Criteria) this;
        }

        public Criteria andBorrowStatusIn(List<Integer> values) {
            addCriterion("borrow_status in", values, "borrowStatus");
            return (Criteria) this;
        }

        public Criteria andBorrowStatusNotIn(List<Integer> values) {
            addCriterion("borrow_status not in", values, "borrowStatus");
            return (Criteria) this;
        }

        public Criteria andBorrowStatusBetween(Integer value1, Integer value2) {
            addCriterion("borrow_status between", value1, value2, "borrowStatus");
            return (Criteria) this;
        }

        public Criteria andBorrowStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("borrow_status not between", value1, value2, "borrowStatus");
            return (Criteria) this;
        }

        public Criteria andBorrowFullStatusIsNull() {
            addCriterion("borrow_full_status is null");
            return (Criteria) this;
        }

        public Criteria andBorrowFullStatusIsNotNull() {
            addCriterion("borrow_full_status is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowFullStatusEqualTo(Integer value) {
            addCriterion("borrow_full_status =", value, "borrowFullStatus");
            return (Criteria) this;
        }

        public Criteria andBorrowFullStatusNotEqualTo(Integer value) {
            addCriterion("borrow_full_status <>", value, "borrowFullStatus");
            return (Criteria) this;
        }

        public Criteria andBorrowFullStatusGreaterThan(Integer value) {
            addCriterion("borrow_full_status >", value, "borrowFullStatus");
            return (Criteria) this;
        }

        public Criteria andBorrowFullStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("borrow_full_status >=", value, "borrowFullStatus");
            return (Criteria) this;
        }

        public Criteria andBorrowFullStatusLessThan(Integer value) {
            addCriterion("borrow_full_status <", value, "borrowFullStatus");
            return (Criteria) this;
        }

        public Criteria andBorrowFullStatusLessThanOrEqualTo(Integer value) {
            addCriterion("borrow_full_status <=", value, "borrowFullStatus");
            return (Criteria) this;
        }

        public Criteria andBorrowFullStatusIn(List<Integer> values) {
            addCriterion("borrow_full_status in", values, "borrowFullStatus");
            return (Criteria) this;
        }

        public Criteria andBorrowFullStatusNotIn(List<Integer> values) {
            addCriterion("borrow_full_status not in", values, "borrowFullStatus");
            return (Criteria) this;
        }

        public Criteria andBorrowFullStatusBetween(Integer value1, Integer value2) {
            addCriterion("borrow_full_status between", value1, value2, "borrowFullStatus");
            return (Criteria) this;
        }

        public Criteria andBorrowFullStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("borrow_full_status not between", value1, value2, "borrowFullStatus");
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

        public Criteria andBorrowPreNidEqualTo(Integer value) {
            addCriterion("borrow_pre_nid =", value, "borrowPreNid");
            return (Criteria) this;
        }

        public Criteria andBorrowPreNidNotEqualTo(Integer value) {
            addCriterion("borrow_pre_nid <>", value, "borrowPreNid");
            return (Criteria) this;
        }

        public Criteria andBorrowPreNidGreaterThan(Integer value) {
            addCriterion("borrow_pre_nid >", value, "borrowPreNid");
            return (Criteria) this;
        }

        public Criteria andBorrowPreNidGreaterThanOrEqualTo(Integer value) {
            addCriterion("borrow_pre_nid >=", value, "borrowPreNid");
            return (Criteria) this;
        }

        public Criteria andBorrowPreNidLessThan(Integer value) {
            addCriterion("borrow_pre_nid <", value, "borrowPreNid");
            return (Criteria) this;
        }

        public Criteria andBorrowPreNidLessThanOrEqualTo(Integer value) {
            addCriterion("borrow_pre_nid <=", value, "borrowPreNid");
            return (Criteria) this;
        }

        public Criteria andBorrowPreNidIn(List<Integer> values) {
            addCriterion("borrow_pre_nid in", values, "borrowPreNid");
            return (Criteria) this;
        }

        public Criteria andBorrowPreNidNotIn(List<Integer> values) {
            addCriterion("borrow_pre_nid not in", values, "borrowPreNid");
            return (Criteria) this;
        }

        public Criteria andBorrowPreNidBetween(Integer value1, Integer value2) {
            addCriterion("borrow_pre_nid between", value1, value2, "borrowPreNid");
            return (Criteria) this;
        }

        public Criteria andBorrowPreNidNotBetween(Integer value1, Integer value2) {
            addCriterion("borrow_pre_nid not between", value1, value2, "borrowPreNid");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountYesIsNull() {
            addCriterion("borrow_account_yes is null");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountYesIsNotNull() {
            addCriterion("borrow_account_yes is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountYesEqualTo(BigDecimal value) {
            addCriterion("borrow_account_yes =", value, "borrowAccountYes");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountYesNotEqualTo(BigDecimal value) {
            addCriterion("borrow_account_yes <>", value, "borrowAccountYes");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountYesGreaterThan(BigDecimal value) {
            addCriterion("borrow_account_yes >", value, "borrowAccountYes");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountYesGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("borrow_account_yes >=", value, "borrowAccountYes");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountYesLessThan(BigDecimal value) {
            addCriterion("borrow_account_yes <", value, "borrowAccountYes");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountYesLessThanOrEqualTo(BigDecimal value) {
            addCriterion("borrow_account_yes <=", value, "borrowAccountYes");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountYesIn(List<BigDecimal> values) {
            addCriterion("borrow_account_yes in", values, "borrowAccountYes");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountYesNotIn(List<BigDecimal> values) {
            addCriterion("borrow_account_yes not in", values, "borrowAccountYes");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountYesBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("borrow_account_yes between", value1, value2, "borrowAccountYes");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountYesNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("borrow_account_yes not between", value1, value2, "borrowAccountYes");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountWaitIsNull() {
            addCriterion("borrow_account_wait is null");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountWaitIsNotNull() {
            addCriterion("borrow_account_wait is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountWaitEqualTo(BigDecimal value) {
            addCriterion("borrow_account_wait =", value, "borrowAccountWait");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountWaitNotEqualTo(BigDecimal value) {
            addCriterion("borrow_account_wait <>", value, "borrowAccountWait");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountWaitGreaterThan(BigDecimal value) {
            addCriterion("borrow_account_wait >", value, "borrowAccountWait");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountWaitGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("borrow_account_wait >=", value, "borrowAccountWait");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountWaitLessThan(BigDecimal value) {
            addCriterion("borrow_account_wait <", value, "borrowAccountWait");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountWaitLessThanOrEqualTo(BigDecimal value) {
            addCriterion("borrow_account_wait <=", value, "borrowAccountWait");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountWaitIn(List<BigDecimal> values) {
            addCriterion("borrow_account_wait in", values, "borrowAccountWait");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountWaitNotIn(List<BigDecimal> values) {
            addCriterion("borrow_account_wait not in", values, "borrowAccountWait");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountWaitBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("borrow_account_wait between", value1, value2, "borrowAccountWait");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountWaitNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("borrow_account_wait not between", value1, value2, "borrowAccountWait");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountScaleIsNull() {
            addCriterion("borrow_account_scale is null");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountScaleIsNotNull() {
            addCriterion("borrow_account_scale is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountScaleEqualTo(BigDecimal value) {
            addCriterion("borrow_account_scale =", value, "borrowAccountScale");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountScaleNotEqualTo(BigDecimal value) {
            addCriterion("borrow_account_scale <>", value, "borrowAccountScale");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountScaleGreaterThan(BigDecimal value) {
            addCriterion("borrow_account_scale >", value, "borrowAccountScale");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountScaleGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("borrow_account_scale >=", value, "borrowAccountScale");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountScaleLessThan(BigDecimal value) {
            addCriterion("borrow_account_scale <", value, "borrowAccountScale");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountScaleLessThanOrEqualTo(BigDecimal value) {
            addCriterion("borrow_account_scale <=", value, "borrowAccountScale");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountScaleIn(List<BigDecimal> values) {
            addCriterion("borrow_account_scale in", values, "borrowAccountScale");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountScaleNotIn(List<BigDecimal> values) {
            addCriterion("borrow_account_scale not in", values, "borrowAccountScale");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountScaleBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("borrow_account_scale between", value1, value2, "borrowAccountScale");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountScaleNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("borrow_account_scale not between", value1, value2, "borrowAccountScale");
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

        public Criteria andBorrowStyleIsNull() {
            addCriterion("borrow_style is null");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleIsNotNull() {
            addCriterion("borrow_style is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleEqualTo(String value) {
            addCriterion("borrow_style =", value, "borrowStyle");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleNotEqualTo(String value) {
            addCriterion("borrow_style <>", value, "borrowStyle");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleGreaterThan(String value) {
            addCriterion("borrow_style >", value, "borrowStyle");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleGreaterThanOrEqualTo(String value) {
            addCriterion("borrow_style >=", value, "borrowStyle");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleLessThan(String value) {
            addCriterion("borrow_style <", value, "borrowStyle");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleLessThanOrEqualTo(String value) {
            addCriterion("borrow_style <=", value, "borrowStyle");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleLike(String value) {
            addCriterion("borrow_style like", value, "borrowStyle");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleNotLike(String value) {
            addCriterion("borrow_style not like", value, "borrowStyle");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleIn(List<String> values) {
            addCriterion("borrow_style in", values, "borrowStyle");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleNotIn(List<String> values) {
            addCriterion("borrow_style not in", values, "borrowStyle");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleBetween(String value1, String value2) {
            addCriterion("borrow_style between", value1, value2, "borrowStyle");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleNotBetween(String value1, String value2) {
            addCriterion("borrow_style not between", value1, value2, "borrowStyle");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodIsNull() {
            addCriterion("borrow_period is null");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodIsNotNull() {
            addCriterion("borrow_period is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodEqualTo(Integer value) {
            addCriterion("borrow_period =", value, "borrowPeriod");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodNotEqualTo(Integer value) {
            addCriterion("borrow_period <>", value, "borrowPeriod");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodGreaterThan(Integer value) {
            addCriterion("borrow_period >", value, "borrowPeriod");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodGreaterThanOrEqualTo(Integer value) {
            addCriterion("borrow_period >=", value, "borrowPeriod");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodLessThan(Integer value) {
            addCriterion("borrow_period <", value, "borrowPeriod");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodLessThanOrEqualTo(Integer value) {
            addCriterion("borrow_period <=", value, "borrowPeriod");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodIn(List<Integer> values) {
            addCriterion("borrow_period in", values, "borrowPeriod");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodNotIn(List<Integer> values) {
            addCriterion("borrow_period not in", values, "borrowPeriod");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodBetween(Integer value1, Integer value2) {
            addCriterion("borrow_period between", value1, value2, "borrowPeriod");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodNotBetween(Integer value1, Integer value2) {
            addCriterion("borrow_period not between", value1, value2, "borrowPeriod");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodRoamIsNull() {
            addCriterion("borrow_period_roam is null");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodRoamIsNotNull() {
            addCriterion("borrow_period_roam is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodRoamEqualTo(Integer value) {
            addCriterion("borrow_period_roam =", value, "borrowPeriodRoam");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodRoamNotEqualTo(Integer value) {
            addCriterion("borrow_period_roam <>", value, "borrowPeriodRoam");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodRoamGreaterThan(Integer value) {
            addCriterion("borrow_period_roam >", value, "borrowPeriodRoam");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodRoamGreaterThanOrEqualTo(Integer value) {
            addCriterion("borrow_period_roam >=", value, "borrowPeriodRoam");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodRoamLessThan(Integer value) {
            addCriterion("borrow_period_roam <", value, "borrowPeriodRoam");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodRoamLessThanOrEqualTo(Integer value) {
            addCriterion("borrow_period_roam <=", value, "borrowPeriodRoam");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodRoamIn(List<Integer> values) {
            addCriterion("borrow_period_roam in", values, "borrowPeriodRoam");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodRoamNotIn(List<Integer> values) {
            addCriterion("borrow_period_roam not in", values, "borrowPeriodRoam");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodRoamBetween(Integer value1, Integer value2) {
            addCriterion("borrow_period_roam between", value1, value2, "borrowPeriodRoam");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodRoamNotBetween(Integer value1, Integer value2) {
            addCriterion("borrow_period_roam not between", value1, value2, "borrowPeriodRoam");
            return (Criteria) this;
        }

        public Criteria andBorrowDayIsNull() {
            addCriterion("borrow_day is null");
            return (Criteria) this;
        }

        public Criteria andBorrowDayIsNotNull() {
            addCriterion("borrow_day is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowDayEqualTo(Integer value) {
            addCriterion("borrow_day =", value, "borrowDay");
            return (Criteria) this;
        }

        public Criteria andBorrowDayNotEqualTo(Integer value) {
            addCriterion("borrow_day <>", value, "borrowDay");
            return (Criteria) this;
        }

        public Criteria andBorrowDayGreaterThan(Integer value) {
            addCriterion("borrow_day >", value, "borrowDay");
            return (Criteria) this;
        }

        public Criteria andBorrowDayGreaterThanOrEqualTo(Integer value) {
            addCriterion("borrow_day >=", value, "borrowDay");
            return (Criteria) this;
        }

        public Criteria andBorrowDayLessThan(Integer value) {
            addCriterion("borrow_day <", value, "borrowDay");
            return (Criteria) this;
        }

        public Criteria andBorrowDayLessThanOrEqualTo(Integer value) {
            addCriterion("borrow_day <=", value, "borrowDay");
            return (Criteria) this;
        }

        public Criteria andBorrowDayIn(List<Integer> values) {
            addCriterion("borrow_day in", values, "borrowDay");
            return (Criteria) this;
        }

        public Criteria andBorrowDayNotIn(List<Integer> values) {
            addCriterion("borrow_day not in", values, "borrowDay");
            return (Criteria) this;
        }

        public Criteria andBorrowDayBetween(Integer value1, Integer value2) {
            addCriterion("borrow_day between", value1, value2, "borrowDay");
            return (Criteria) this;
        }

        public Criteria andBorrowDayNotBetween(Integer value1, Integer value2) {
            addCriterion("borrow_day not between", value1, value2, "borrowDay");
            return (Criteria) this;
        }

        public Criteria andBorrowAprIsNull() {
            addCriterion("borrow_apr is null");
            return (Criteria) this;
        }

        public Criteria andBorrowAprIsNotNull() {
            addCriterion("borrow_apr is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowAprEqualTo(BigDecimal value) {
            addCriterion("borrow_apr =", value, "borrowApr");
            return (Criteria) this;
        }

        public Criteria andBorrowAprNotEqualTo(BigDecimal value) {
            addCriterion("borrow_apr <>", value, "borrowApr");
            return (Criteria) this;
        }

        public Criteria andBorrowAprGreaterThan(BigDecimal value) {
            addCriterion("borrow_apr >", value, "borrowApr");
            return (Criteria) this;
        }

        public Criteria andBorrowAprGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("borrow_apr >=", value, "borrowApr");
            return (Criteria) this;
        }

        public Criteria andBorrowAprLessThan(BigDecimal value) {
            addCriterion("borrow_apr <", value, "borrowApr");
            return (Criteria) this;
        }

        public Criteria andBorrowAprLessThanOrEqualTo(BigDecimal value) {
            addCriterion("borrow_apr <=", value, "borrowApr");
            return (Criteria) this;
        }

        public Criteria andBorrowAprIn(List<BigDecimal> values) {
            addCriterion("borrow_apr in", values, "borrowApr");
            return (Criteria) this;
        }

        public Criteria andBorrowAprNotIn(List<BigDecimal> values) {
            addCriterion("borrow_apr not in", values, "borrowApr");
            return (Criteria) this;
        }

        public Criteria andBorrowAprBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("borrow_apr between", value1, value2, "borrowApr");
            return (Criteria) this;
        }

        public Criteria andBorrowAprNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("borrow_apr not between", value1, value2, "borrowApr");
            return (Criteria) this;
        }

        public Criteria andBorrowFrostAccountIsNull() {
            addCriterion("borrow_frost_account is null");
            return (Criteria) this;
        }

        public Criteria andBorrowFrostAccountIsNotNull() {
            addCriterion("borrow_frost_account is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowFrostAccountEqualTo(BigDecimal value) {
            addCriterion("borrow_frost_account =", value, "borrowFrostAccount");
            return (Criteria) this;
        }

        public Criteria andBorrowFrostAccountNotEqualTo(BigDecimal value) {
            addCriterion("borrow_frost_account <>", value, "borrowFrostAccount");
            return (Criteria) this;
        }

        public Criteria andBorrowFrostAccountGreaterThan(BigDecimal value) {
            addCriterion("borrow_frost_account >", value, "borrowFrostAccount");
            return (Criteria) this;
        }

        public Criteria andBorrowFrostAccountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("borrow_frost_account >=", value, "borrowFrostAccount");
            return (Criteria) this;
        }

        public Criteria andBorrowFrostAccountLessThan(BigDecimal value) {
            addCriterion("borrow_frost_account <", value, "borrowFrostAccount");
            return (Criteria) this;
        }

        public Criteria andBorrowFrostAccountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("borrow_frost_account <=", value, "borrowFrostAccount");
            return (Criteria) this;
        }

        public Criteria andBorrowFrostAccountIn(List<BigDecimal> values) {
            addCriterion("borrow_frost_account in", values, "borrowFrostAccount");
            return (Criteria) this;
        }

        public Criteria andBorrowFrostAccountNotIn(List<BigDecimal> values) {
            addCriterion("borrow_frost_account not in", values, "borrowFrostAccount");
            return (Criteria) this;
        }

        public Criteria andBorrowFrostAccountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("borrow_frost_account between", value1, value2, "borrowFrostAccount");
            return (Criteria) this;
        }

        public Criteria andBorrowFrostAccountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("borrow_frost_account not between", value1, value2, "borrowFrostAccount");
            return (Criteria) this;
        }

        public Criteria andBorrowFrostScaleIsNull() {
            addCriterion("borrow_frost_scale is null");
            return (Criteria) this;
        }

        public Criteria andBorrowFrostScaleIsNotNull() {
            addCriterion("borrow_frost_scale is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowFrostScaleEqualTo(String value) {
            addCriterion("borrow_frost_scale =", value, "borrowFrostScale");
            return (Criteria) this;
        }

        public Criteria andBorrowFrostScaleNotEqualTo(String value) {
            addCriterion("borrow_frost_scale <>", value, "borrowFrostScale");
            return (Criteria) this;
        }

        public Criteria andBorrowFrostScaleGreaterThan(String value) {
            addCriterion("borrow_frost_scale >", value, "borrowFrostScale");
            return (Criteria) this;
        }

        public Criteria andBorrowFrostScaleGreaterThanOrEqualTo(String value) {
            addCriterion("borrow_frost_scale >=", value, "borrowFrostScale");
            return (Criteria) this;
        }

        public Criteria andBorrowFrostScaleLessThan(String value) {
            addCriterion("borrow_frost_scale <", value, "borrowFrostScale");
            return (Criteria) this;
        }

        public Criteria andBorrowFrostScaleLessThanOrEqualTo(String value) {
            addCriterion("borrow_frost_scale <=", value, "borrowFrostScale");
            return (Criteria) this;
        }

        public Criteria andBorrowFrostScaleLike(String value) {
            addCriterion("borrow_frost_scale like", value, "borrowFrostScale");
            return (Criteria) this;
        }

        public Criteria andBorrowFrostScaleNotLike(String value) {
            addCriterion("borrow_frost_scale not like", value, "borrowFrostScale");
            return (Criteria) this;
        }

        public Criteria andBorrowFrostScaleIn(List<String> values) {
            addCriterion("borrow_frost_scale in", values, "borrowFrostScale");
            return (Criteria) this;
        }

        public Criteria andBorrowFrostScaleNotIn(List<String> values) {
            addCriterion("borrow_frost_scale not in", values, "borrowFrostScale");
            return (Criteria) this;
        }

        public Criteria andBorrowFrostScaleBetween(String value1, String value2) {
            addCriterion("borrow_frost_scale between", value1, value2, "borrowFrostScale");
            return (Criteria) this;
        }

        public Criteria andBorrowFrostScaleNotBetween(String value1, String value2) {
            addCriterion("borrow_frost_scale not between", value1, value2, "borrowFrostScale");
            return (Criteria) this;
        }

        public Criteria andBorrowFrostSecondIsNull() {
            addCriterion("borrow_frost_second is null");
            return (Criteria) this;
        }

        public Criteria andBorrowFrostSecondIsNotNull() {
            addCriterion("borrow_frost_second is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowFrostSecondEqualTo(BigDecimal value) {
            addCriterion("borrow_frost_second =", value, "borrowFrostSecond");
            return (Criteria) this;
        }

        public Criteria andBorrowFrostSecondNotEqualTo(BigDecimal value) {
            addCriterion("borrow_frost_second <>", value, "borrowFrostSecond");
            return (Criteria) this;
        }

        public Criteria andBorrowFrostSecondGreaterThan(BigDecimal value) {
            addCriterion("borrow_frost_second >", value, "borrowFrostSecond");
            return (Criteria) this;
        }

        public Criteria andBorrowFrostSecondGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("borrow_frost_second >=", value, "borrowFrostSecond");
            return (Criteria) this;
        }

        public Criteria andBorrowFrostSecondLessThan(BigDecimal value) {
            addCriterion("borrow_frost_second <", value, "borrowFrostSecond");
            return (Criteria) this;
        }

        public Criteria andBorrowFrostSecondLessThanOrEqualTo(BigDecimal value) {
            addCriterion("borrow_frost_second <=", value, "borrowFrostSecond");
            return (Criteria) this;
        }

        public Criteria andBorrowFrostSecondIn(List<BigDecimal> values) {
            addCriterion("borrow_frost_second in", values, "borrowFrostSecond");
            return (Criteria) this;
        }

        public Criteria andBorrowFrostSecondNotIn(List<BigDecimal> values) {
            addCriterion("borrow_frost_second not in", values, "borrowFrostSecond");
            return (Criteria) this;
        }

        public Criteria andBorrowFrostSecondBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("borrow_frost_second between", value1, value2, "borrowFrostSecond");
            return (Criteria) this;
        }

        public Criteria andBorrowFrostSecondNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("borrow_frost_second not between", value1, value2, "borrowFrostSecond");
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

        public Criteria andBorrowSuccessTimeIsNull() {
            addCriterion("borrow_success_time is null");
            return (Criteria) this;
        }

        public Criteria andBorrowSuccessTimeIsNotNull() {
            addCriterion("borrow_success_time is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowSuccessTimeEqualTo(Integer value) {
            addCriterion("borrow_success_time =", value, "borrowSuccessTime");
            return (Criteria) this;
        }

        public Criteria andBorrowSuccessTimeNotEqualTo(Integer value) {
            addCriterion("borrow_success_time <>", value, "borrowSuccessTime");
            return (Criteria) this;
        }

        public Criteria andBorrowSuccessTimeGreaterThan(Integer value) {
            addCriterion("borrow_success_time >", value, "borrowSuccessTime");
            return (Criteria) this;
        }

        public Criteria andBorrowSuccessTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("borrow_success_time >=", value, "borrowSuccessTime");
            return (Criteria) this;
        }

        public Criteria andBorrowSuccessTimeLessThan(Integer value) {
            addCriterion("borrow_success_time <", value, "borrowSuccessTime");
            return (Criteria) this;
        }

        public Criteria andBorrowSuccessTimeLessThanOrEqualTo(Integer value) {
            addCriterion("borrow_success_time <=", value, "borrowSuccessTime");
            return (Criteria) this;
        }

        public Criteria andBorrowSuccessTimeIn(List<Integer> values) {
            addCriterion("borrow_success_time in", values, "borrowSuccessTime");
            return (Criteria) this;
        }

        public Criteria andBorrowSuccessTimeNotIn(List<Integer> values) {
            addCriterion("borrow_success_time not in", values, "borrowSuccessTime");
            return (Criteria) this;
        }

        public Criteria andBorrowSuccessTimeBetween(Integer value1, Integer value2) {
            addCriterion("borrow_success_time between", value1, value2, "borrowSuccessTime");
            return (Criteria) this;
        }

        public Criteria andBorrowSuccessTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("borrow_success_time not between", value1, value2, "borrowSuccessTime");
            return (Criteria) this;
        }

        public Criteria andBorrowEndTimeIsNull() {
            addCriterion("borrow_end_time is null");
            return (Criteria) this;
        }

        public Criteria andBorrowEndTimeIsNotNull() {
            addCriterion("borrow_end_time is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowEndTimeEqualTo(String value) {
            addCriterion("borrow_end_time =", value, "borrowEndTime");
            return (Criteria) this;
        }

        public Criteria andBorrowEndTimeNotEqualTo(String value) {
            addCriterion("borrow_end_time <>", value, "borrowEndTime");
            return (Criteria) this;
        }

        public Criteria andBorrowEndTimeGreaterThan(String value) {
            addCriterion("borrow_end_time >", value, "borrowEndTime");
            return (Criteria) this;
        }

        public Criteria andBorrowEndTimeGreaterThanOrEqualTo(String value) {
            addCriterion("borrow_end_time >=", value, "borrowEndTime");
            return (Criteria) this;
        }

        public Criteria andBorrowEndTimeLessThan(String value) {
            addCriterion("borrow_end_time <", value, "borrowEndTime");
            return (Criteria) this;
        }

        public Criteria andBorrowEndTimeLessThanOrEqualTo(String value) {
            addCriterion("borrow_end_time <=", value, "borrowEndTime");
            return (Criteria) this;
        }

        public Criteria andBorrowEndTimeLike(String value) {
            addCriterion("borrow_end_time like", value, "borrowEndTime");
            return (Criteria) this;
        }

        public Criteria andBorrowEndTimeNotLike(String value) {
            addCriterion("borrow_end_time not like", value, "borrowEndTime");
            return (Criteria) this;
        }

        public Criteria andBorrowEndTimeIn(List<String> values) {
            addCriterion("borrow_end_time in", values, "borrowEndTime");
            return (Criteria) this;
        }

        public Criteria andBorrowEndTimeNotIn(List<String> values) {
            addCriterion("borrow_end_time not in", values, "borrowEndTime");
            return (Criteria) this;
        }

        public Criteria andBorrowEndTimeBetween(String value1, String value2) {
            addCriterion("borrow_end_time between", value1, value2, "borrowEndTime");
            return (Criteria) this;
        }

        public Criteria andBorrowEndTimeNotBetween(String value1, String value2) {
            addCriterion("borrow_end_time not between", value1, value2, "borrowEndTime");
            return (Criteria) this;
        }

        public Criteria andBorrowPartStatusIsNull() {
            addCriterion("borrow_part_status is null");
            return (Criteria) this;
        }

        public Criteria andBorrowPartStatusIsNotNull() {
            addCriterion("borrow_part_status is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowPartStatusEqualTo(Integer value) {
            addCriterion("borrow_part_status =", value, "borrowPartStatus");
            return (Criteria) this;
        }

        public Criteria andBorrowPartStatusNotEqualTo(Integer value) {
            addCriterion("borrow_part_status <>", value, "borrowPartStatus");
            return (Criteria) this;
        }

        public Criteria andBorrowPartStatusGreaterThan(Integer value) {
            addCriterion("borrow_part_status >", value, "borrowPartStatus");
            return (Criteria) this;
        }

        public Criteria andBorrowPartStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("borrow_part_status >=", value, "borrowPartStatus");
            return (Criteria) this;
        }

        public Criteria andBorrowPartStatusLessThan(Integer value) {
            addCriterion("borrow_part_status <", value, "borrowPartStatus");
            return (Criteria) this;
        }

        public Criteria andBorrowPartStatusLessThanOrEqualTo(Integer value) {
            addCriterion("borrow_part_status <=", value, "borrowPartStatus");
            return (Criteria) this;
        }

        public Criteria andBorrowPartStatusIn(List<Integer> values) {
            addCriterion("borrow_part_status in", values, "borrowPartStatus");
            return (Criteria) this;
        }

        public Criteria andBorrowPartStatusNotIn(List<Integer> values) {
            addCriterion("borrow_part_status not in", values, "borrowPartStatus");
            return (Criteria) this;
        }

        public Criteria andBorrowPartStatusBetween(Integer value1, Integer value2) {
            addCriterion("borrow_part_status between", value1, value2, "borrowPartStatus");
            return (Criteria) this;
        }

        public Criteria andBorrowPartStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("borrow_part_status not between", value1, value2, "borrowPartStatus");
            return (Criteria) this;
        }

        public Criteria andCancelUseridIsNull() {
            addCriterion("cancel_userid is null");
            return (Criteria) this;
        }

        public Criteria andCancelUseridIsNotNull() {
            addCriterion("cancel_userid is not null");
            return (Criteria) this;
        }

        public Criteria andCancelUseridEqualTo(Integer value) {
            addCriterion("cancel_userid =", value, "cancelUserid");
            return (Criteria) this;
        }

        public Criteria andCancelUseridNotEqualTo(Integer value) {
            addCriterion("cancel_userid <>", value, "cancelUserid");
            return (Criteria) this;
        }

        public Criteria andCancelUseridGreaterThan(Integer value) {
            addCriterion("cancel_userid >", value, "cancelUserid");
            return (Criteria) this;
        }

        public Criteria andCancelUseridGreaterThanOrEqualTo(Integer value) {
            addCriterion("cancel_userid >=", value, "cancelUserid");
            return (Criteria) this;
        }

        public Criteria andCancelUseridLessThan(Integer value) {
            addCriterion("cancel_userid <", value, "cancelUserid");
            return (Criteria) this;
        }

        public Criteria andCancelUseridLessThanOrEqualTo(Integer value) {
            addCriterion("cancel_userid <=", value, "cancelUserid");
            return (Criteria) this;
        }

        public Criteria andCancelUseridIn(List<Integer> values) {
            addCriterion("cancel_userid in", values, "cancelUserid");
            return (Criteria) this;
        }

        public Criteria andCancelUseridNotIn(List<Integer> values) {
            addCriterion("cancel_userid not in", values, "cancelUserid");
            return (Criteria) this;
        }

        public Criteria andCancelUseridBetween(Integer value1, Integer value2) {
            addCriterion("cancel_userid between", value1, value2, "cancelUserid");
            return (Criteria) this;
        }

        public Criteria andCancelUseridNotBetween(Integer value1, Integer value2) {
            addCriterion("cancel_userid not between", value1, value2, "cancelUserid");
            return (Criteria) this;
        }

        public Criteria andCancelStatusIsNull() {
            addCriterion("cancel_status is null");
            return (Criteria) this;
        }

        public Criteria andCancelStatusIsNotNull() {
            addCriterion("cancel_status is not null");
            return (Criteria) this;
        }

        public Criteria andCancelStatusEqualTo(Integer value) {
            addCriterion("cancel_status =", value, "cancelStatus");
            return (Criteria) this;
        }

        public Criteria andCancelStatusNotEqualTo(Integer value) {
            addCriterion("cancel_status <>", value, "cancelStatus");
            return (Criteria) this;
        }

        public Criteria andCancelStatusGreaterThan(Integer value) {
            addCriterion("cancel_status >", value, "cancelStatus");
            return (Criteria) this;
        }

        public Criteria andCancelStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("cancel_status >=", value, "cancelStatus");
            return (Criteria) this;
        }

        public Criteria andCancelStatusLessThan(Integer value) {
            addCriterion("cancel_status <", value, "cancelStatus");
            return (Criteria) this;
        }

        public Criteria andCancelStatusLessThanOrEqualTo(Integer value) {
            addCriterion("cancel_status <=", value, "cancelStatus");
            return (Criteria) this;
        }

        public Criteria andCancelStatusIn(List<Integer> values) {
            addCriterion("cancel_status in", values, "cancelStatus");
            return (Criteria) this;
        }

        public Criteria andCancelStatusNotIn(List<Integer> values) {
            addCriterion("cancel_status not in", values, "cancelStatus");
            return (Criteria) this;
        }

        public Criteria andCancelStatusBetween(Integer value1, Integer value2) {
            addCriterion("cancel_status between", value1, value2, "cancelStatus");
            return (Criteria) this;
        }

        public Criteria andCancelStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("cancel_status not between", value1, value2, "cancelStatus");
            return (Criteria) this;
        }

        public Criteria andCancelTimeIsNull() {
            addCriterion("cancel_time is null");
            return (Criteria) this;
        }

        public Criteria andCancelTimeIsNotNull() {
            addCriterion("cancel_time is not null");
            return (Criteria) this;
        }

        public Criteria andCancelTimeEqualTo(String value) {
            addCriterion("cancel_time =", value, "cancelTime");
            return (Criteria) this;
        }

        public Criteria andCancelTimeNotEqualTo(String value) {
            addCriterion("cancel_time <>", value, "cancelTime");
            return (Criteria) this;
        }

        public Criteria andCancelTimeGreaterThan(String value) {
            addCriterion("cancel_time >", value, "cancelTime");
            return (Criteria) this;
        }

        public Criteria andCancelTimeGreaterThanOrEqualTo(String value) {
            addCriterion("cancel_time >=", value, "cancelTime");
            return (Criteria) this;
        }

        public Criteria andCancelTimeLessThan(String value) {
            addCriterion("cancel_time <", value, "cancelTime");
            return (Criteria) this;
        }

        public Criteria andCancelTimeLessThanOrEqualTo(String value) {
            addCriterion("cancel_time <=", value, "cancelTime");
            return (Criteria) this;
        }

        public Criteria andCancelTimeLike(String value) {
            addCriterion("cancel_time like", value, "cancelTime");
            return (Criteria) this;
        }

        public Criteria andCancelTimeNotLike(String value) {
            addCriterion("cancel_time not like", value, "cancelTime");
            return (Criteria) this;
        }

        public Criteria andCancelTimeIn(List<String> values) {
            addCriterion("cancel_time in", values, "cancelTime");
            return (Criteria) this;
        }

        public Criteria andCancelTimeNotIn(List<String> values) {
            addCriterion("cancel_time not in", values, "cancelTime");
            return (Criteria) this;
        }

        public Criteria andCancelTimeBetween(String value1, String value2) {
            addCriterion("cancel_time between", value1, value2, "cancelTime");
            return (Criteria) this;
        }

        public Criteria andCancelTimeNotBetween(String value1, String value2) {
            addCriterion("cancel_time not between", value1, value2, "cancelTime");
            return (Criteria) this;
        }

        public Criteria andCancelRemarkIsNull() {
            addCriterion("cancel_remark is null");
            return (Criteria) this;
        }

        public Criteria andCancelRemarkIsNotNull() {
            addCriterion("cancel_remark is not null");
            return (Criteria) this;
        }

        public Criteria andCancelRemarkEqualTo(String value) {
            addCriterion("cancel_remark =", value, "cancelRemark");
            return (Criteria) this;
        }

        public Criteria andCancelRemarkNotEqualTo(String value) {
            addCriterion("cancel_remark <>", value, "cancelRemark");
            return (Criteria) this;
        }

        public Criteria andCancelRemarkGreaterThan(String value) {
            addCriterion("cancel_remark >", value, "cancelRemark");
            return (Criteria) this;
        }

        public Criteria andCancelRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("cancel_remark >=", value, "cancelRemark");
            return (Criteria) this;
        }

        public Criteria andCancelRemarkLessThan(String value) {
            addCriterion("cancel_remark <", value, "cancelRemark");
            return (Criteria) this;
        }

        public Criteria andCancelRemarkLessThanOrEqualTo(String value) {
            addCriterion("cancel_remark <=", value, "cancelRemark");
            return (Criteria) this;
        }

        public Criteria andCancelRemarkLike(String value) {
            addCriterion("cancel_remark like", value, "cancelRemark");
            return (Criteria) this;
        }

        public Criteria andCancelRemarkNotLike(String value) {
            addCriterion("cancel_remark not like", value, "cancelRemark");
            return (Criteria) this;
        }

        public Criteria andCancelRemarkIn(List<String> values) {
            addCriterion("cancel_remark in", values, "cancelRemark");
            return (Criteria) this;
        }

        public Criteria andCancelRemarkNotIn(List<String> values) {
            addCriterion("cancel_remark not in", values, "cancelRemark");
            return (Criteria) this;
        }

        public Criteria andCancelRemarkBetween(String value1, String value2) {
            addCriterion("cancel_remark between", value1, value2, "cancelRemark");
            return (Criteria) this;
        }

        public Criteria andCancelRemarkNotBetween(String value1, String value2) {
            addCriterion("cancel_remark not between", value1, value2, "cancelRemark");
            return (Criteria) this;
        }

        public Criteria andCancelContentsIsNull() {
            addCriterion("cancel_contents is null");
            return (Criteria) this;
        }

        public Criteria andCancelContentsIsNotNull() {
            addCriterion("cancel_contents is not null");
            return (Criteria) this;
        }

        public Criteria andCancelContentsEqualTo(String value) {
            addCriterion("cancel_contents =", value, "cancelContents");
            return (Criteria) this;
        }

        public Criteria andCancelContentsNotEqualTo(String value) {
            addCriterion("cancel_contents <>", value, "cancelContents");
            return (Criteria) this;
        }

        public Criteria andCancelContentsGreaterThan(String value) {
            addCriterion("cancel_contents >", value, "cancelContents");
            return (Criteria) this;
        }

        public Criteria andCancelContentsGreaterThanOrEqualTo(String value) {
            addCriterion("cancel_contents >=", value, "cancelContents");
            return (Criteria) this;
        }

        public Criteria andCancelContentsLessThan(String value) {
            addCriterion("cancel_contents <", value, "cancelContents");
            return (Criteria) this;
        }

        public Criteria andCancelContentsLessThanOrEqualTo(String value) {
            addCriterion("cancel_contents <=", value, "cancelContents");
            return (Criteria) this;
        }

        public Criteria andCancelContentsLike(String value) {
            addCriterion("cancel_contents like", value, "cancelContents");
            return (Criteria) this;
        }

        public Criteria andCancelContentsNotLike(String value) {
            addCriterion("cancel_contents not like", value, "cancelContents");
            return (Criteria) this;
        }

        public Criteria andCancelContentsIn(List<String> values) {
            addCriterion("cancel_contents in", values, "cancelContents");
            return (Criteria) this;
        }

        public Criteria andCancelContentsNotIn(List<String> values) {
            addCriterion("cancel_contents not in", values, "cancelContents");
            return (Criteria) this;
        }

        public Criteria andCancelContentsBetween(String value1, String value2) {
            addCriterion("cancel_contents between", value1, value2, "cancelContents");
            return (Criteria) this;
        }

        public Criteria andCancelContentsNotBetween(String value1, String value2) {
            addCriterion("cancel_contents not between", value1, value2, "cancelContents");
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

        public Criteria andTenderTimesIsNull() {
            addCriterion("tender_times is null");
            return (Criteria) this;
        }

        public Criteria andTenderTimesIsNotNull() {
            addCriterion("tender_times is not null");
            return (Criteria) this;
        }

        public Criteria andTenderTimesEqualTo(Integer value) {
            addCriterion("tender_times =", value, "tenderTimes");
            return (Criteria) this;
        }

        public Criteria andTenderTimesNotEqualTo(Integer value) {
            addCriterion("tender_times <>", value, "tenderTimes");
            return (Criteria) this;
        }

        public Criteria andTenderTimesGreaterThan(Integer value) {
            addCriterion("tender_times >", value, "tenderTimes");
            return (Criteria) this;
        }

        public Criteria andTenderTimesGreaterThanOrEqualTo(Integer value) {
            addCriterion("tender_times >=", value, "tenderTimes");
            return (Criteria) this;
        }

        public Criteria andTenderTimesLessThan(Integer value) {
            addCriterion("tender_times <", value, "tenderTimes");
            return (Criteria) this;
        }

        public Criteria andTenderTimesLessThanOrEqualTo(Integer value) {
            addCriterion("tender_times <=", value, "tenderTimes");
            return (Criteria) this;
        }

        public Criteria andTenderTimesIn(List<Integer> values) {
            addCriterion("tender_times in", values, "tenderTimes");
            return (Criteria) this;
        }

        public Criteria andTenderTimesNotIn(List<Integer> values) {
            addCriterion("tender_times not in", values, "tenderTimes");
            return (Criteria) this;
        }

        public Criteria andTenderTimesBetween(Integer value1, Integer value2) {
            addCriterion("tender_times between", value1, value2, "tenderTimes");
            return (Criteria) this;
        }

        public Criteria andTenderTimesNotBetween(Integer value1, Integer value2) {
            addCriterion("tender_times not between", value1, value2, "tenderTimes");
            return (Criteria) this;
        }

        public Criteria andTenderLastTimeIsNull() {
            addCriterion("tender_last_time is null");
            return (Criteria) this;
        }

        public Criteria andTenderLastTimeIsNotNull() {
            addCriterion("tender_last_time is not null");
            return (Criteria) this;
        }

        public Criteria andTenderLastTimeEqualTo(String value) {
            addCriterion("tender_last_time =", value, "tenderLastTime");
            return (Criteria) this;
        }

        public Criteria andTenderLastTimeNotEqualTo(String value) {
            addCriterion("tender_last_time <>", value, "tenderLastTime");
            return (Criteria) this;
        }

        public Criteria andTenderLastTimeGreaterThan(String value) {
            addCriterion("tender_last_time >", value, "tenderLastTime");
            return (Criteria) this;
        }

        public Criteria andTenderLastTimeGreaterThanOrEqualTo(String value) {
            addCriterion("tender_last_time >=", value, "tenderLastTime");
            return (Criteria) this;
        }

        public Criteria andTenderLastTimeLessThan(String value) {
            addCriterion("tender_last_time <", value, "tenderLastTime");
            return (Criteria) this;
        }

        public Criteria andTenderLastTimeLessThanOrEqualTo(String value) {
            addCriterion("tender_last_time <=", value, "tenderLastTime");
            return (Criteria) this;
        }

        public Criteria andTenderLastTimeLike(String value) {
            addCriterion("tender_last_time like", value, "tenderLastTime");
            return (Criteria) this;
        }

        public Criteria andTenderLastTimeNotLike(String value) {
            addCriterion("tender_last_time not like", value, "tenderLastTime");
            return (Criteria) this;
        }

        public Criteria andTenderLastTimeIn(List<String> values) {
            addCriterion("tender_last_time in", values, "tenderLastTime");
            return (Criteria) this;
        }

        public Criteria andTenderLastTimeNotIn(List<String> values) {
            addCriterion("tender_last_time not in", values, "tenderLastTime");
            return (Criteria) this;
        }

        public Criteria andTenderLastTimeBetween(String value1, String value2) {
            addCriterion("tender_last_time between", value1, value2, "tenderLastTime");
            return (Criteria) this;
        }

        public Criteria andTenderLastTimeNotBetween(String value1, String value2) {
            addCriterion("tender_last_time not between", value1, value2, "tenderLastTime");
            return (Criteria) this;
        }

        public Criteria andRepayAdvanceStatusIsNull() {
            addCriterion("repay_advance_status is null");
            return (Criteria) this;
        }

        public Criteria andRepayAdvanceStatusIsNotNull() {
            addCriterion("repay_advance_status is not null");
            return (Criteria) this;
        }

        public Criteria andRepayAdvanceStatusEqualTo(Integer value) {
            addCriterion("repay_advance_status =", value, "repayAdvanceStatus");
            return (Criteria) this;
        }

        public Criteria andRepayAdvanceStatusNotEqualTo(Integer value) {
            addCriterion("repay_advance_status <>", value, "repayAdvanceStatus");
            return (Criteria) this;
        }

        public Criteria andRepayAdvanceStatusGreaterThan(Integer value) {
            addCriterion("repay_advance_status >", value, "repayAdvanceStatus");
            return (Criteria) this;
        }

        public Criteria andRepayAdvanceStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("repay_advance_status >=", value, "repayAdvanceStatus");
            return (Criteria) this;
        }

        public Criteria andRepayAdvanceStatusLessThan(Integer value) {
            addCriterion("repay_advance_status <", value, "repayAdvanceStatus");
            return (Criteria) this;
        }

        public Criteria andRepayAdvanceStatusLessThanOrEqualTo(Integer value) {
            addCriterion("repay_advance_status <=", value, "repayAdvanceStatus");
            return (Criteria) this;
        }

        public Criteria andRepayAdvanceStatusIn(List<Integer> values) {
            addCriterion("repay_advance_status in", values, "repayAdvanceStatus");
            return (Criteria) this;
        }

        public Criteria andRepayAdvanceStatusNotIn(List<Integer> values) {
            addCriterion("repay_advance_status not in", values, "repayAdvanceStatus");
            return (Criteria) this;
        }

        public Criteria andRepayAdvanceStatusBetween(Integer value1, Integer value2) {
            addCriterion("repay_advance_status between", value1, value2, "repayAdvanceStatus");
            return (Criteria) this;
        }

        public Criteria andRepayAdvanceStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("repay_advance_status not between", value1, value2, "repayAdvanceStatus");
            return (Criteria) this;
        }

        public Criteria andRepayAdvanceTimeIsNull() {
            addCriterion("repay_advance_time is null");
            return (Criteria) this;
        }

        public Criteria andRepayAdvanceTimeIsNotNull() {
            addCriterion("repay_advance_time is not null");
            return (Criteria) this;
        }

        public Criteria andRepayAdvanceTimeEqualTo(String value) {
            addCriterion("repay_advance_time =", value, "repayAdvanceTime");
            return (Criteria) this;
        }

        public Criteria andRepayAdvanceTimeNotEqualTo(String value) {
            addCriterion("repay_advance_time <>", value, "repayAdvanceTime");
            return (Criteria) this;
        }

        public Criteria andRepayAdvanceTimeGreaterThan(String value) {
            addCriterion("repay_advance_time >", value, "repayAdvanceTime");
            return (Criteria) this;
        }

        public Criteria andRepayAdvanceTimeGreaterThanOrEqualTo(String value) {
            addCriterion("repay_advance_time >=", value, "repayAdvanceTime");
            return (Criteria) this;
        }

        public Criteria andRepayAdvanceTimeLessThan(String value) {
            addCriterion("repay_advance_time <", value, "repayAdvanceTime");
            return (Criteria) this;
        }

        public Criteria andRepayAdvanceTimeLessThanOrEqualTo(String value) {
            addCriterion("repay_advance_time <=", value, "repayAdvanceTime");
            return (Criteria) this;
        }

        public Criteria andRepayAdvanceTimeLike(String value) {
            addCriterion("repay_advance_time like", value, "repayAdvanceTime");
            return (Criteria) this;
        }

        public Criteria andRepayAdvanceTimeNotLike(String value) {
            addCriterion("repay_advance_time not like", value, "repayAdvanceTime");
            return (Criteria) this;
        }

        public Criteria andRepayAdvanceTimeIn(List<String> values) {
            addCriterion("repay_advance_time in", values, "repayAdvanceTime");
            return (Criteria) this;
        }

        public Criteria andRepayAdvanceTimeNotIn(List<String> values) {
            addCriterion("repay_advance_time not in", values, "repayAdvanceTime");
            return (Criteria) this;
        }

        public Criteria andRepayAdvanceTimeBetween(String value1, String value2) {
            addCriterion("repay_advance_time between", value1, value2, "repayAdvanceTime");
            return (Criteria) this;
        }

        public Criteria andRepayAdvanceTimeNotBetween(String value1, String value2) {
            addCriterion("repay_advance_time not between", value1, value2, "repayAdvanceTime");
            return (Criteria) this;
        }

        public Criteria andRepayAdvanceStepIsNull() {
            addCriterion("repay_advance_step is null");
            return (Criteria) this;
        }

        public Criteria andRepayAdvanceStepIsNotNull() {
            addCriterion("repay_advance_step is not null");
            return (Criteria) this;
        }

        public Criteria andRepayAdvanceStepEqualTo(Integer value) {
            addCriterion("repay_advance_step =", value, "repayAdvanceStep");
            return (Criteria) this;
        }

        public Criteria andRepayAdvanceStepNotEqualTo(Integer value) {
            addCriterion("repay_advance_step <>", value, "repayAdvanceStep");
            return (Criteria) this;
        }

        public Criteria andRepayAdvanceStepGreaterThan(Integer value) {
            addCriterion("repay_advance_step >", value, "repayAdvanceStep");
            return (Criteria) this;
        }

        public Criteria andRepayAdvanceStepGreaterThanOrEqualTo(Integer value) {
            addCriterion("repay_advance_step >=", value, "repayAdvanceStep");
            return (Criteria) this;
        }

        public Criteria andRepayAdvanceStepLessThan(Integer value) {
            addCriterion("repay_advance_step <", value, "repayAdvanceStep");
            return (Criteria) this;
        }

        public Criteria andRepayAdvanceStepLessThanOrEqualTo(Integer value) {
            addCriterion("repay_advance_step <=", value, "repayAdvanceStep");
            return (Criteria) this;
        }

        public Criteria andRepayAdvanceStepIn(List<Integer> values) {
            addCriterion("repay_advance_step in", values, "repayAdvanceStep");
            return (Criteria) this;
        }

        public Criteria andRepayAdvanceStepNotIn(List<Integer> values) {
            addCriterion("repay_advance_step not in", values, "repayAdvanceStep");
            return (Criteria) this;
        }

        public Criteria andRepayAdvanceStepBetween(Integer value1, Integer value2) {
            addCriterion("repay_advance_step between", value1, value2, "repayAdvanceStep");
            return (Criteria) this;
        }

        public Criteria andRepayAdvanceStepNotBetween(Integer value1, Integer value2) {
            addCriterion("repay_advance_step not between", value1, value2, "repayAdvanceStep");
            return (Criteria) this;
        }

        public Criteria andRepayAccountAllIsNull() {
            addCriterion("repay_account_all is null");
            return (Criteria) this;
        }

        public Criteria andRepayAccountAllIsNotNull() {
            addCriterion("repay_account_all is not null");
            return (Criteria) this;
        }

        public Criteria andRepayAccountAllEqualTo(BigDecimal value) {
            addCriterion("repay_account_all =", value, "repayAccountAll");
            return (Criteria) this;
        }

        public Criteria andRepayAccountAllNotEqualTo(BigDecimal value) {
            addCriterion("repay_account_all <>", value, "repayAccountAll");
            return (Criteria) this;
        }

        public Criteria andRepayAccountAllGreaterThan(BigDecimal value) {
            addCriterion("repay_account_all >", value, "repayAccountAll");
            return (Criteria) this;
        }

        public Criteria andRepayAccountAllGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_account_all >=", value, "repayAccountAll");
            return (Criteria) this;
        }

        public Criteria andRepayAccountAllLessThan(BigDecimal value) {
            addCriterion("repay_account_all <", value, "repayAccountAll");
            return (Criteria) this;
        }

        public Criteria andRepayAccountAllLessThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_account_all <=", value, "repayAccountAll");
            return (Criteria) this;
        }

        public Criteria andRepayAccountAllIn(List<BigDecimal> values) {
            addCriterion("repay_account_all in", values, "repayAccountAll");
            return (Criteria) this;
        }

        public Criteria andRepayAccountAllNotIn(List<BigDecimal> values) {
            addCriterion("repay_account_all not in", values, "repayAccountAll");
            return (Criteria) this;
        }

        public Criteria andRepayAccountAllBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_account_all between", value1, value2, "repayAccountAll");
            return (Criteria) this;
        }

        public Criteria andRepayAccountAllNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_account_all not between", value1, value2, "repayAccountAll");
            return (Criteria) this;
        }

        public Criteria andRepayAccountInterestIsNull() {
            addCriterion("repay_account_interest is null");
            return (Criteria) this;
        }

        public Criteria andRepayAccountInterestIsNotNull() {
            addCriterion("repay_account_interest is not null");
            return (Criteria) this;
        }

        public Criteria andRepayAccountInterestEqualTo(BigDecimal value) {
            addCriterion("repay_account_interest =", value, "repayAccountInterest");
            return (Criteria) this;
        }

        public Criteria andRepayAccountInterestNotEqualTo(BigDecimal value) {
            addCriterion("repay_account_interest <>", value, "repayAccountInterest");
            return (Criteria) this;
        }

        public Criteria andRepayAccountInterestGreaterThan(BigDecimal value) {
            addCriterion("repay_account_interest >", value, "repayAccountInterest");
            return (Criteria) this;
        }

        public Criteria andRepayAccountInterestGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_account_interest >=", value, "repayAccountInterest");
            return (Criteria) this;
        }

        public Criteria andRepayAccountInterestLessThan(BigDecimal value) {
            addCriterion("repay_account_interest <", value, "repayAccountInterest");
            return (Criteria) this;
        }

        public Criteria andRepayAccountInterestLessThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_account_interest <=", value, "repayAccountInterest");
            return (Criteria) this;
        }

        public Criteria andRepayAccountInterestIn(List<BigDecimal> values) {
            addCriterion("repay_account_interest in", values, "repayAccountInterest");
            return (Criteria) this;
        }

        public Criteria andRepayAccountInterestNotIn(List<BigDecimal> values) {
            addCriterion("repay_account_interest not in", values, "repayAccountInterest");
            return (Criteria) this;
        }

        public Criteria andRepayAccountInterestBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_account_interest between", value1, value2, "repayAccountInterest");
            return (Criteria) this;
        }

        public Criteria andRepayAccountInterestNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_account_interest not between", value1, value2, "repayAccountInterest");
            return (Criteria) this;
        }

        public Criteria andRepayAccountCapitalIsNull() {
            addCriterion("repay_account_capital is null");
            return (Criteria) this;
        }

        public Criteria andRepayAccountCapitalIsNotNull() {
            addCriterion("repay_account_capital is not null");
            return (Criteria) this;
        }

        public Criteria andRepayAccountCapitalEqualTo(BigDecimal value) {
            addCriterion("repay_account_capital =", value, "repayAccountCapital");
            return (Criteria) this;
        }

        public Criteria andRepayAccountCapitalNotEqualTo(BigDecimal value) {
            addCriterion("repay_account_capital <>", value, "repayAccountCapital");
            return (Criteria) this;
        }

        public Criteria andRepayAccountCapitalGreaterThan(BigDecimal value) {
            addCriterion("repay_account_capital >", value, "repayAccountCapital");
            return (Criteria) this;
        }

        public Criteria andRepayAccountCapitalGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_account_capital >=", value, "repayAccountCapital");
            return (Criteria) this;
        }

        public Criteria andRepayAccountCapitalLessThan(BigDecimal value) {
            addCriterion("repay_account_capital <", value, "repayAccountCapital");
            return (Criteria) this;
        }

        public Criteria andRepayAccountCapitalLessThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_account_capital <=", value, "repayAccountCapital");
            return (Criteria) this;
        }

        public Criteria andRepayAccountCapitalIn(List<BigDecimal> values) {
            addCriterion("repay_account_capital in", values, "repayAccountCapital");
            return (Criteria) this;
        }

        public Criteria andRepayAccountCapitalNotIn(List<BigDecimal> values) {
            addCriterion("repay_account_capital not in", values, "repayAccountCapital");
            return (Criteria) this;
        }

        public Criteria andRepayAccountCapitalBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_account_capital between", value1, value2, "repayAccountCapital");
            return (Criteria) this;
        }

        public Criteria andRepayAccountCapitalNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_account_capital not between", value1, value2, "repayAccountCapital");
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

        public Criteria andRepayAccountInterestYesIsNull() {
            addCriterion("repay_account_interest_yes is null");
            return (Criteria) this;
        }

        public Criteria andRepayAccountInterestYesIsNotNull() {
            addCriterion("repay_account_interest_yes is not null");
            return (Criteria) this;
        }

        public Criteria andRepayAccountInterestYesEqualTo(BigDecimal value) {
            addCriterion("repay_account_interest_yes =", value, "repayAccountInterestYes");
            return (Criteria) this;
        }

        public Criteria andRepayAccountInterestYesNotEqualTo(BigDecimal value) {
            addCriterion("repay_account_interest_yes <>", value, "repayAccountInterestYes");
            return (Criteria) this;
        }

        public Criteria andRepayAccountInterestYesGreaterThan(BigDecimal value) {
            addCriterion("repay_account_interest_yes >", value, "repayAccountInterestYes");
            return (Criteria) this;
        }

        public Criteria andRepayAccountInterestYesGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_account_interest_yes >=", value, "repayAccountInterestYes");
            return (Criteria) this;
        }

        public Criteria andRepayAccountInterestYesLessThan(BigDecimal value) {
            addCriterion("repay_account_interest_yes <", value, "repayAccountInterestYes");
            return (Criteria) this;
        }

        public Criteria andRepayAccountInterestYesLessThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_account_interest_yes <=", value, "repayAccountInterestYes");
            return (Criteria) this;
        }

        public Criteria andRepayAccountInterestYesIn(List<BigDecimal> values) {
            addCriterion("repay_account_interest_yes in", values, "repayAccountInterestYes");
            return (Criteria) this;
        }

        public Criteria andRepayAccountInterestYesNotIn(List<BigDecimal> values) {
            addCriterion("repay_account_interest_yes not in", values, "repayAccountInterestYes");
            return (Criteria) this;
        }

        public Criteria andRepayAccountInterestYesBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_account_interest_yes between", value1, value2, "repayAccountInterestYes");
            return (Criteria) this;
        }

        public Criteria andRepayAccountInterestYesNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_account_interest_yes not between", value1, value2, "repayAccountInterestYes");
            return (Criteria) this;
        }

        public Criteria andRepayAccountCapitalYesIsNull() {
            addCriterion("repay_account_capital_yes is null");
            return (Criteria) this;
        }

        public Criteria andRepayAccountCapitalYesIsNotNull() {
            addCriterion("repay_account_capital_yes is not null");
            return (Criteria) this;
        }

        public Criteria andRepayAccountCapitalYesEqualTo(BigDecimal value) {
            addCriterion("repay_account_capital_yes =", value, "repayAccountCapitalYes");
            return (Criteria) this;
        }

        public Criteria andRepayAccountCapitalYesNotEqualTo(BigDecimal value) {
            addCriterion("repay_account_capital_yes <>", value, "repayAccountCapitalYes");
            return (Criteria) this;
        }

        public Criteria andRepayAccountCapitalYesGreaterThan(BigDecimal value) {
            addCriterion("repay_account_capital_yes >", value, "repayAccountCapitalYes");
            return (Criteria) this;
        }

        public Criteria andRepayAccountCapitalYesGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_account_capital_yes >=", value, "repayAccountCapitalYes");
            return (Criteria) this;
        }

        public Criteria andRepayAccountCapitalYesLessThan(BigDecimal value) {
            addCriterion("repay_account_capital_yes <", value, "repayAccountCapitalYes");
            return (Criteria) this;
        }

        public Criteria andRepayAccountCapitalYesLessThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_account_capital_yes <=", value, "repayAccountCapitalYes");
            return (Criteria) this;
        }

        public Criteria andRepayAccountCapitalYesIn(List<BigDecimal> values) {
            addCriterion("repay_account_capital_yes in", values, "repayAccountCapitalYes");
            return (Criteria) this;
        }

        public Criteria andRepayAccountCapitalYesNotIn(List<BigDecimal> values) {
            addCriterion("repay_account_capital_yes not in", values, "repayAccountCapitalYes");
            return (Criteria) this;
        }

        public Criteria andRepayAccountCapitalYesBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_account_capital_yes between", value1, value2, "repayAccountCapitalYes");
            return (Criteria) this;
        }

        public Criteria andRepayAccountCapitalYesNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_account_capital_yes not between", value1, value2, "repayAccountCapitalYes");
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

        public Criteria andRepayAccountInterestWaitIsNull() {
            addCriterion("repay_account_interest_wait is null");
            return (Criteria) this;
        }

        public Criteria andRepayAccountInterestWaitIsNotNull() {
            addCriterion("repay_account_interest_wait is not null");
            return (Criteria) this;
        }

        public Criteria andRepayAccountInterestWaitEqualTo(BigDecimal value) {
            addCriterion("repay_account_interest_wait =", value, "repayAccountInterestWait");
            return (Criteria) this;
        }

        public Criteria andRepayAccountInterestWaitNotEqualTo(BigDecimal value) {
            addCriterion("repay_account_interest_wait <>", value, "repayAccountInterestWait");
            return (Criteria) this;
        }

        public Criteria andRepayAccountInterestWaitGreaterThan(BigDecimal value) {
            addCriterion("repay_account_interest_wait >", value, "repayAccountInterestWait");
            return (Criteria) this;
        }

        public Criteria andRepayAccountInterestWaitGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_account_interest_wait >=", value, "repayAccountInterestWait");
            return (Criteria) this;
        }

        public Criteria andRepayAccountInterestWaitLessThan(BigDecimal value) {
            addCriterion("repay_account_interest_wait <", value, "repayAccountInterestWait");
            return (Criteria) this;
        }

        public Criteria andRepayAccountInterestWaitLessThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_account_interest_wait <=", value, "repayAccountInterestWait");
            return (Criteria) this;
        }

        public Criteria andRepayAccountInterestWaitIn(List<BigDecimal> values) {
            addCriterion("repay_account_interest_wait in", values, "repayAccountInterestWait");
            return (Criteria) this;
        }

        public Criteria andRepayAccountInterestWaitNotIn(List<BigDecimal> values) {
            addCriterion("repay_account_interest_wait not in", values, "repayAccountInterestWait");
            return (Criteria) this;
        }

        public Criteria andRepayAccountInterestWaitBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_account_interest_wait between", value1, value2, "repayAccountInterestWait");
            return (Criteria) this;
        }

        public Criteria andRepayAccountInterestWaitNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_account_interest_wait not between", value1, value2, "repayAccountInterestWait");
            return (Criteria) this;
        }

        public Criteria andRepayAccountCapitalWaitIsNull() {
            addCriterion("repay_account_capital_wait is null");
            return (Criteria) this;
        }

        public Criteria andRepayAccountCapitalWaitIsNotNull() {
            addCriterion("repay_account_capital_wait is not null");
            return (Criteria) this;
        }

        public Criteria andRepayAccountCapitalWaitEqualTo(BigDecimal value) {
            addCriterion("repay_account_capital_wait =", value, "repayAccountCapitalWait");
            return (Criteria) this;
        }

        public Criteria andRepayAccountCapitalWaitNotEqualTo(BigDecimal value) {
            addCriterion("repay_account_capital_wait <>", value, "repayAccountCapitalWait");
            return (Criteria) this;
        }

        public Criteria andRepayAccountCapitalWaitGreaterThan(BigDecimal value) {
            addCriterion("repay_account_capital_wait >", value, "repayAccountCapitalWait");
            return (Criteria) this;
        }

        public Criteria andRepayAccountCapitalWaitGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_account_capital_wait >=", value, "repayAccountCapitalWait");
            return (Criteria) this;
        }

        public Criteria andRepayAccountCapitalWaitLessThan(BigDecimal value) {
            addCriterion("repay_account_capital_wait <", value, "repayAccountCapitalWait");
            return (Criteria) this;
        }

        public Criteria andRepayAccountCapitalWaitLessThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_account_capital_wait <=", value, "repayAccountCapitalWait");
            return (Criteria) this;
        }

        public Criteria andRepayAccountCapitalWaitIn(List<BigDecimal> values) {
            addCriterion("repay_account_capital_wait in", values, "repayAccountCapitalWait");
            return (Criteria) this;
        }

        public Criteria andRepayAccountCapitalWaitNotIn(List<BigDecimal> values) {
            addCriterion("repay_account_capital_wait not in", values, "repayAccountCapitalWait");
            return (Criteria) this;
        }

        public Criteria andRepayAccountCapitalWaitBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_account_capital_wait between", value1, value2, "repayAccountCapitalWait");
            return (Criteria) this;
        }

        public Criteria andRepayAccountCapitalWaitNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_account_capital_wait not between", value1, value2, "repayAccountCapitalWait");
            return (Criteria) this;
        }

        public Criteria andRepayAccountTimesIsNull() {
            addCriterion("repay_account_times is null");
            return (Criteria) this;
        }

        public Criteria andRepayAccountTimesIsNotNull() {
            addCriterion("repay_account_times is not null");
            return (Criteria) this;
        }

        public Criteria andRepayAccountTimesEqualTo(Integer value) {
            addCriterion("repay_account_times =", value, "repayAccountTimes");
            return (Criteria) this;
        }

        public Criteria andRepayAccountTimesNotEqualTo(Integer value) {
            addCriterion("repay_account_times <>", value, "repayAccountTimes");
            return (Criteria) this;
        }

        public Criteria andRepayAccountTimesGreaterThan(Integer value) {
            addCriterion("repay_account_times >", value, "repayAccountTimes");
            return (Criteria) this;
        }

        public Criteria andRepayAccountTimesGreaterThanOrEqualTo(Integer value) {
            addCriterion("repay_account_times >=", value, "repayAccountTimes");
            return (Criteria) this;
        }

        public Criteria andRepayAccountTimesLessThan(Integer value) {
            addCriterion("repay_account_times <", value, "repayAccountTimes");
            return (Criteria) this;
        }

        public Criteria andRepayAccountTimesLessThanOrEqualTo(Integer value) {
            addCriterion("repay_account_times <=", value, "repayAccountTimes");
            return (Criteria) this;
        }

        public Criteria andRepayAccountTimesIn(List<Integer> values) {
            addCriterion("repay_account_times in", values, "repayAccountTimes");
            return (Criteria) this;
        }

        public Criteria andRepayAccountTimesNotIn(List<Integer> values) {
            addCriterion("repay_account_times not in", values, "repayAccountTimes");
            return (Criteria) this;
        }

        public Criteria andRepayAccountTimesBetween(Integer value1, Integer value2) {
            addCriterion("repay_account_times between", value1, value2, "repayAccountTimes");
            return (Criteria) this;
        }

        public Criteria andRepayAccountTimesNotBetween(Integer value1, Integer value2) {
            addCriterion("repay_account_times not between", value1, value2, "repayAccountTimes");
            return (Criteria) this;
        }

        public Criteria andRepayMonthAccountIsNull() {
            addCriterion("repay_month_account is null");
            return (Criteria) this;
        }

        public Criteria andRepayMonthAccountIsNotNull() {
            addCriterion("repay_month_account is not null");
            return (Criteria) this;
        }

        public Criteria andRepayMonthAccountEqualTo(Integer value) {
            addCriterion("repay_month_account =", value, "repayMonthAccount");
            return (Criteria) this;
        }

        public Criteria andRepayMonthAccountNotEqualTo(Integer value) {
            addCriterion("repay_month_account <>", value, "repayMonthAccount");
            return (Criteria) this;
        }

        public Criteria andRepayMonthAccountGreaterThan(Integer value) {
            addCriterion("repay_month_account >", value, "repayMonthAccount");
            return (Criteria) this;
        }

        public Criteria andRepayMonthAccountGreaterThanOrEqualTo(Integer value) {
            addCriterion("repay_month_account >=", value, "repayMonthAccount");
            return (Criteria) this;
        }

        public Criteria andRepayMonthAccountLessThan(Integer value) {
            addCriterion("repay_month_account <", value, "repayMonthAccount");
            return (Criteria) this;
        }

        public Criteria andRepayMonthAccountLessThanOrEqualTo(Integer value) {
            addCriterion("repay_month_account <=", value, "repayMonthAccount");
            return (Criteria) this;
        }

        public Criteria andRepayMonthAccountIn(List<Integer> values) {
            addCriterion("repay_month_account in", values, "repayMonthAccount");
            return (Criteria) this;
        }

        public Criteria andRepayMonthAccountNotIn(List<Integer> values) {
            addCriterion("repay_month_account not in", values, "repayMonthAccount");
            return (Criteria) this;
        }

        public Criteria andRepayMonthAccountBetween(Integer value1, Integer value2) {
            addCriterion("repay_month_account between", value1, value2, "repayMonthAccount");
            return (Criteria) this;
        }

        public Criteria andRepayMonthAccountNotBetween(Integer value1, Integer value2) {
            addCriterion("repay_month_account not between", value1, value2, "repayMonthAccount");
            return (Criteria) this;
        }

        public Criteria andRepayLastTimeIsNull() {
            addCriterion("repay_last_time is null");
            return (Criteria) this;
        }

        public Criteria andRepayLastTimeIsNotNull() {
            addCriterion("repay_last_time is not null");
            return (Criteria) this;
        }

        public Criteria andRepayLastTimeEqualTo(String value) {
            addCriterion("repay_last_time =", value, "repayLastTime");
            return (Criteria) this;
        }

        public Criteria andRepayLastTimeNotEqualTo(String value) {
            addCriterion("repay_last_time <>", value, "repayLastTime");
            return (Criteria) this;
        }

        public Criteria andRepayLastTimeGreaterThan(String value) {
            addCriterion("repay_last_time >", value, "repayLastTime");
            return (Criteria) this;
        }

        public Criteria andRepayLastTimeGreaterThanOrEqualTo(String value) {
            addCriterion("repay_last_time >=", value, "repayLastTime");
            return (Criteria) this;
        }

        public Criteria andRepayLastTimeLessThan(String value) {
            addCriterion("repay_last_time <", value, "repayLastTime");
            return (Criteria) this;
        }

        public Criteria andRepayLastTimeLessThanOrEqualTo(String value) {
            addCriterion("repay_last_time <=", value, "repayLastTime");
            return (Criteria) this;
        }

        public Criteria andRepayLastTimeLike(String value) {
            addCriterion("repay_last_time like", value, "repayLastTime");
            return (Criteria) this;
        }

        public Criteria andRepayLastTimeNotLike(String value) {
            addCriterion("repay_last_time not like", value, "repayLastTime");
            return (Criteria) this;
        }

        public Criteria andRepayLastTimeIn(List<String> values) {
            addCriterion("repay_last_time in", values, "repayLastTime");
            return (Criteria) this;
        }

        public Criteria andRepayLastTimeNotIn(List<String> values) {
            addCriterion("repay_last_time not in", values, "repayLastTime");
            return (Criteria) this;
        }

        public Criteria andRepayLastTimeBetween(String value1, String value2) {
            addCriterion("repay_last_time between", value1, value2, "repayLastTime");
            return (Criteria) this;
        }

        public Criteria andRepayLastTimeNotBetween(String value1, String value2) {
            addCriterion("repay_last_time not between", value1, value2, "repayLastTime");
            return (Criteria) this;
        }

        public Criteria andRepayEachTimeIsNull() {
            addCriterion("repay_each_time is null");
            return (Criteria) this;
        }

        public Criteria andRepayEachTimeIsNotNull() {
            addCriterion("repay_each_time is not null");
            return (Criteria) this;
        }

        public Criteria andRepayEachTimeEqualTo(String value) {
            addCriterion("repay_each_time =", value, "repayEachTime");
            return (Criteria) this;
        }

        public Criteria andRepayEachTimeNotEqualTo(String value) {
            addCriterion("repay_each_time <>", value, "repayEachTime");
            return (Criteria) this;
        }

        public Criteria andRepayEachTimeGreaterThan(String value) {
            addCriterion("repay_each_time >", value, "repayEachTime");
            return (Criteria) this;
        }

        public Criteria andRepayEachTimeGreaterThanOrEqualTo(String value) {
            addCriterion("repay_each_time >=", value, "repayEachTime");
            return (Criteria) this;
        }

        public Criteria andRepayEachTimeLessThan(String value) {
            addCriterion("repay_each_time <", value, "repayEachTime");
            return (Criteria) this;
        }

        public Criteria andRepayEachTimeLessThanOrEqualTo(String value) {
            addCriterion("repay_each_time <=", value, "repayEachTime");
            return (Criteria) this;
        }

        public Criteria andRepayEachTimeLike(String value) {
            addCriterion("repay_each_time like", value, "repayEachTime");
            return (Criteria) this;
        }

        public Criteria andRepayEachTimeNotLike(String value) {
            addCriterion("repay_each_time not like", value, "repayEachTime");
            return (Criteria) this;
        }

        public Criteria andRepayEachTimeIn(List<String> values) {
            addCriterion("repay_each_time in", values, "repayEachTime");
            return (Criteria) this;
        }

        public Criteria andRepayEachTimeNotIn(List<String> values) {
            addCriterion("repay_each_time not in", values, "repayEachTime");
            return (Criteria) this;
        }

        public Criteria andRepayEachTimeBetween(String value1, String value2) {
            addCriterion("repay_each_time between", value1, value2, "repayEachTime");
            return (Criteria) this;
        }

        public Criteria andRepayEachTimeNotBetween(String value1, String value2) {
            addCriterion("repay_each_time not between", value1, value2, "repayEachTime");
            return (Criteria) this;
        }

        public Criteria andRepayNextTimeIsNull() {
            addCriterion("repay_next_time is null");
            return (Criteria) this;
        }

        public Criteria andRepayNextTimeIsNotNull() {
            addCriterion("repay_next_time is not null");
            return (Criteria) this;
        }

        public Criteria andRepayNextTimeEqualTo(Integer value) {
            addCriterion("repay_next_time =", value, "repayNextTime");
            return (Criteria) this;
        }

        public Criteria andRepayNextTimeNotEqualTo(Integer value) {
            addCriterion("repay_next_time <>", value, "repayNextTime");
            return (Criteria) this;
        }

        public Criteria andRepayNextTimeGreaterThan(Integer value) {
            addCriterion("repay_next_time >", value, "repayNextTime");
            return (Criteria) this;
        }

        public Criteria andRepayNextTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("repay_next_time >=", value, "repayNextTime");
            return (Criteria) this;
        }

        public Criteria andRepayNextTimeLessThan(Integer value) {
            addCriterion("repay_next_time <", value, "repayNextTime");
            return (Criteria) this;
        }

        public Criteria andRepayNextTimeLessThanOrEqualTo(Integer value) {
            addCriterion("repay_next_time <=", value, "repayNextTime");
            return (Criteria) this;
        }

        public Criteria andRepayNextTimeIn(List<Integer> values) {
            addCriterion("repay_next_time in", values, "repayNextTime");
            return (Criteria) this;
        }

        public Criteria andRepayNextTimeNotIn(List<Integer> values) {
            addCriterion("repay_next_time not in", values, "repayNextTime");
            return (Criteria) this;
        }

        public Criteria andRepayNextTimeBetween(Integer value1, Integer value2) {
            addCriterion("repay_next_time between", value1, value2, "repayNextTime");
            return (Criteria) this;
        }

        public Criteria andRepayNextTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("repay_next_time not between", value1, value2, "repayNextTime");
            return (Criteria) this;
        }

        public Criteria andRepayNextAccountIsNull() {
            addCriterion("repay_next_account is null");
            return (Criteria) this;
        }

        public Criteria andRepayNextAccountIsNotNull() {
            addCriterion("repay_next_account is not null");
            return (Criteria) this;
        }

        public Criteria andRepayNextAccountEqualTo(BigDecimal value) {
            addCriterion("repay_next_account =", value, "repayNextAccount");
            return (Criteria) this;
        }

        public Criteria andRepayNextAccountNotEqualTo(BigDecimal value) {
            addCriterion("repay_next_account <>", value, "repayNextAccount");
            return (Criteria) this;
        }

        public Criteria andRepayNextAccountGreaterThan(BigDecimal value) {
            addCriterion("repay_next_account >", value, "repayNextAccount");
            return (Criteria) this;
        }

        public Criteria andRepayNextAccountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_next_account >=", value, "repayNextAccount");
            return (Criteria) this;
        }

        public Criteria andRepayNextAccountLessThan(BigDecimal value) {
            addCriterion("repay_next_account <", value, "repayNextAccount");
            return (Criteria) this;
        }

        public Criteria andRepayNextAccountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_next_account <=", value, "repayNextAccount");
            return (Criteria) this;
        }

        public Criteria andRepayNextAccountIn(List<BigDecimal> values) {
            addCriterion("repay_next_account in", values, "repayNextAccount");
            return (Criteria) this;
        }

        public Criteria andRepayNextAccountNotIn(List<BigDecimal> values) {
            addCriterion("repay_next_account not in", values, "repayNextAccount");
            return (Criteria) this;
        }

        public Criteria andRepayNextAccountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_next_account between", value1, value2, "repayNextAccount");
            return (Criteria) this;
        }

        public Criteria andRepayNextAccountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_next_account not between", value1, value2, "repayNextAccount");
            return (Criteria) this;
        }

        public Criteria andRepayTimesIsNull() {
            addCriterion("repay_times is null");
            return (Criteria) this;
        }

        public Criteria andRepayTimesIsNotNull() {
            addCriterion("repay_times is not null");
            return (Criteria) this;
        }

        public Criteria andRepayTimesEqualTo(Integer value) {
            addCriterion("repay_times =", value, "repayTimes");
            return (Criteria) this;
        }

        public Criteria andRepayTimesNotEqualTo(Integer value) {
            addCriterion("repay_times <>", value, "repayTimes");
            return (Criteria) this;
        }

        public Criteria andRepayTimesGreaterThan(Integer value) {
            addCriterion("repay_times >", value, "repayTimes");
            return (Criteria) this;
        }

        public Criteria andRepayTimesGreaterThanOrEqualTo(Integer value) {
            addCriterion("repay_times >=", value, "repayTimes");
            return (Criteria) this;
        }

        public Criteria andRepayTimesLessThan(Integer value) {
            addCriterion("repay_times <", value, "repayTimes");
            return (Criteria) this;
        }

        public Criteria andRepayTimesLessThanOrEqualTo(Integer value) {
            addCriterion("repay_times <=", value, "repayTimes");
            return (Criteria) this;
        }

        public Criteria andRepayTimesIn(List<Integer> values) {
            addCriterion("repay_times in", values, "repayTimes");
            return (Criteria) this;
        }

        public Criteria andRepayTimesNotIn(List<Integer> values) {
            addCriterion("repay_times not in", values, "repayTimes");
            return (Criteria) this;
        }

        public Criteria andRepayTimesBetween(Integer value1, Integer value2) {
            addCriterion("repay_times between", value1, value2, "repayTimes");
            return (Criteria) this;
        }

        public Criteria andRepayTimesNotBetween(Integer value1, Integer value2) {
            addCriterion("repay_times not between", value1, value2, "repayTimes");
            return (Criteria) this;
        }

        public Criteria andRepayFullStatusIsNull() {
            addCriterion("repay_full_status is null");
            return (Criteria) this;
        }

        public Criteria andRepayFullStatusIsNotNull() {
            addCriterion("repay_full_status is not null");
            return (Criteria) this;
        }

        public Criteria andRepayFullStatusEqualTo(Integer value) {
            addCriterion("repay_full_status =", value, "repayFullStatus");
            return (Criteria) this;
        }

        public Criteria andRepayFullStatusNotEqualTo(Integer value) {
            addCriterion("repay_full_status <>", value, "repayFullStatus");
            return (Criteria) this;
        }

        public Criteria andRepayFullStatusGreaterThan(Integer value) {
            addCriterion("repay_full_status >", value, "repayFullStatus");
            return (Criteria) this;
        }

        public Criteria andRepayFullStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("repay_full_status >=", value, "repayFullStatus");
            return (Criteria) this;
        }

        public Criteria andRepayFullStatusLessThan(Integer value) {
            addCriterion("repay_full_status <", value, "repayFullStatus");
            return (Criteria) this;
        }

        public Criteria andRepayFullStatusLessThanOrEqualTo(Integer value) {
            addCriterion("repay_full_status <=", value, "repayFullStatus");
            return (Criteria) this;
        }

        public Criteria andRepayFullStatusIn(List<Integer> values) {
            addCriterion("repay_full_status in", values, "repayFullStatus");
            return (Criteria) this;
        }

        public Criteria andRepayFullStatusNotIn(List<Integer> values) {
            addCriterion("repay_full_status not in", values, "repayFullStatus");
            return (Criteria) this;
        }

        public Criteria andRepayFullStatusBetween(Integer value1, Integer value2) {
            addCriterion("repay_full_status between", value1, value2, "repayFullStatus");
            return (Criteria) this;
        }

        public Criteria andRepayFullStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("repay_full_status not between", value1, value2, "repayFullStatus");
            return (Criteria) this;
        }

        public Criteria andRepayFeeNormalIsNull() {
            addCriterion("repay_fee_normal is null");
            return (Criteria) this;
        }

        public Criteria andRepayFeeNormalIsNotNull() {
            addCriterion("repay_fee_normal is not null");
            return (Criteria) this;
        }

        public Criteria andRepayFeeNormalEqualTo(BigDecimal value) {
            addCriterion("repay_fee_normal =", value, "repayFeeNormal");
            return (Criteria) this;
        }

        public Criteria andRepayFeeNormalNotEqualTo(BigDecimal value) {
            addCriterion("repay_fee_normal <>", value, "repayFeeNormal");
            return (Criteria) this;
        }

        public Criteria andRepayFeeNormalGreaterThan(BigDecimal value) {
            addCriterion("repay_fee_normal >", value, "repayFeeNormal");
            return (Criteria) this;
        }

        public Criteria andRepayFeeNormalGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_fee_normal >=", value, "repayFeeNormal");
            return (Criteria) this;
        }

        public Criteria andRepayFeeNormalLessThan(BigDecimal value) {
            addCriterion("repay_fee_normal <", value, "repayFeeNormal");
            return (Criteria) this;
        }

        public Criteria andRepayFeeNormalLessThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_fee_normal <=", value, "repayFeeNormal");
            return (Criteria) this;
        }

        public Criteria andRepayFeeNormalIn(List<BigDecimal> values) {
            addCriterion("repay_fee_normal in", values, "repayFeeNormal");
            return (Criteria) this;
        }

        public Criteria andRepayFeeNormalNotIn(List<BigDecimal> values) {
            addCriterion("repay_fee_normal not in", values, "repayFeeNormal");
            return (Criteria) this;
        }

        public Criteria andRepayFeeNormalBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_fee_normal between", value1, value2, "repayFeeNormal");
            return (Criteria) this;
        }

        public Criteria andRepayFeeNormalNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_fee_normal not between", value1, value2, "repayFeeNormal");
            return (Criteria) this;
        }

        public Criteria andRepayFeeAdvanceIsNull() {
            addCriterion("repay_fee_advance is null");
            return (Criteria) this;
        }

        public Criteria andRepayFeeAdvanceIsNotNull() {
            addCriterion("repay_fee_advance is not null");
            return (Criteria) this;
        }

        public Criteria andRepayFeeAdvanceEqualTo(BigDecimal value) {
            addCriterion("repay_fee_advance =", value, "repayFeeAdvance");
            return (Criteria) this;
        }

        public Criteria andRepayFeeAdvanceNotEqualTo(BigDecimal value) {
            addCriterion("repay_fee_advance <>", value, "repayFeeAdvance");
            return (Criteria) this;
        }

        public Criteria andRepayFeeAdvanceGreaterThan(BigDecimal value) {
            addCriterion("repay_fee_advance >", value, "repayFeeAdvance");
            return (Criteria) this;
        }

        public Criteria andRepayFeeAdvanceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_fee_advance >=", value, "repayFeeAdvance");
            return (Criteria) this;
        }

        public Criteria andRepayFeeAdvanceLessThan(BigDecimal value) {
            addCriterion("repay_fee_advance <", value, "repayFeeAdvance");
            return (Criteria) this;
        }

        public Criteria andRepayFeeAdvanceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_fee_advance <=", value, "repayFeeAdvance");
            return (Criteria) this;
        }

        public Criteria andRepayFeeAdvanceIn(List<BigDecimal> values) {
            addCriterion("repay_fee_advance in", values, "repayFeeAdvance");
            return (Criteria) this;
        }

        public Criteria andRepayFeeAdvanceNotIn(List<BigDecimal> values) {
            addCriterion("repay_fee_advance not in", values, "repayFeeAdvance");
            return (Criteria) this;
        }

        public Criteria andRepayFeeAdvanceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_fee_advance between", value1, value2, "repayFeeAdvance");
            return (Criteria) this;
        }

        public Criteria andRepayFeeAdvanceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_fee_advance not between", value1, value2, "repayFeeAdvance");
            return (Criteria) this;
        }

        public Criteria andRepayFeeLateIsNull() {
            addCriterion("repay_fee_late is null");
            return (Criteria) this;
        }

        public Criteria andRepayFeeLateIsNotNull() {
            addCriterion("repay_fee_late is not null");
            return (Criteria) this;
        }

        public Criteria andRepayFeeLateEqualTo(BigDecimal value) {
            addCriterion("repay_fee_late =", value, "repayFeeLate");
            return (Criteria) this;
        }

        public Criteria andRepayFeeLateNotEqualTo(BigDecimal value) {
            addCriterion("repay_fee_late <>", value, "repayFeeLate");
            return (Criteria) this;
        }

        public Criteria andRepayFeeLateGreaterThan(BigDecimal value) {
            addCriterion("repay_fee_late >", value, "repayFeeLate");
            return (Criteria) this;
        }

        public Criteria andRepayFeeLateGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_fee_late >=", value, "repayFeeLate");
            return (Criteria) this;
        }

        public Criteria andRepayFeeLateLessThan(BigDecimal value) {
            addCriterion("repay_fee_late <", value, "repayFeeLate");
            return (Criteria) this;
        }

        public Criteria andRepayFeeLateLessThanOrEqualTo(BigDecimal value) {
            addCriterion("repay_fee_late <=", value, "repayFeeLate");
            return (Criteria) this;
        }

        public Criteria andRepayFeeLateIn(List<BigDecimal> values) {
            addCriterion("repay_fee_late in", values, "repayFeeLate");
            return (Criteria) this;
        }

        public Criteria andRepayFeeLateNotIn(List<BigDecimal> values) {
            addCriterion("repay_fee_late not in", values, "repayFeeLate");
            return (Criteria) this;
        }

        public Criteria andRepayFeeLateBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_fee_late between", value1, value2, "repayFeeLate");
            return (Criteria) this;
        }

        public Criteria andRepayFeeLateNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay_fee_late not between", value1, value2, "repayFeeLate");
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

        public Criteria andLateForfeitIsNull() {
            addCriterion("late_forfeit is null");
            return (Criteria) this;
        }

        public Criteria andLateForfeitIsNotNull() {
            addCriterion("late_forfeit is not null");
            return (Criteria) this;
        }

        public Criteria andLateForfeitEqualTo(BigDecimal value) {
            addCriterion("late_forfeit =", value, "lateForfeit");
            return (Criteria) this;
        }

        public Criteria andLateForfeitNotEqualTo(BigDecimal value) {
            addCriterion("late_forfeit <>", value, "lateForfeit");
            return (Criteria) this;
        }

        public Criteria andLateForfeitGreaterThan(BigDecimal value) {
            addCriterion("late_forfeit >", value, "lateForfeit");
            return (Criteria) this;
        }

        public Criteria andLateForfeitGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("late_forfeit >=", value, "lateForfeit");
            return (Criteria) this;
        }

        public Criteria andLateForfeitLessThan(BigDecimal value) {
            addCriterion("late_forfeit <", value, "lateForfeit");
            return (Criteria) this;
        }

        public Criteria andLateForfeitLessThanOrEqualTo(BigDecimal value) {
            addCriterion("late_forfeit <=", value, "lateForfeit");
            return (Criteria) this;
        }

        public Criteria andLateForfeitIn(List<BigDecimal> values) {
            addCriterion("late_forfeit in", values, "lateForfeit");
            return (Criteria) this;
        }

        public Criteria andLateForfeitNotIn(List<BigDecimal> values) {
            addCriterion("late_forfeit not in", values, "lateForfeit");
            return (Criteria) this;
        }

        public Criteria andLateForfeitBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("late_forfeit between", value1, value2, "lateForfeit");
            return (Criteria) this;
        }

        public Criteria andLateForfeitNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("late_forfeit not between", value1, value2, "lateForfeit");
            return (Criteria) this;
        }

        public Criteria andVouchStatusIsNull() {
            addCriterion("vouch_status is null");
            return (Criteria) this;
        }

        public Criteria andVouchStatusIsNotNull() {
            addCriterion("vouch_status is not null");
            return (Criteria) this;
        }

        public Criteria andVouchStatusEqualTo(Integer value) {
            addCriterion("vouch_status =", value, "vouchStatus");
            return (Criteria) this;
        }

        public Criteria andVouchStatusNotEqualTo(Integer value) {
            addCriterion("vouch_status <>", value, "vouchStatus");
            return (Criteria) this;
        }

        public Criteria andVouchStatusGreaterThan(Integer value) {
            addCriterion("vouch_status >", value, "vouchStatus");
            return (Criteria) this;
        }

        public Criteria andVouchStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("vouch_status >=", value, "vouchStatus");
            return (Criteria) this;
        }

        public Criteria andVouchStatusLessThan(Integer value) {
            addCriterion("vouch_status <", value, "vouchStatus");
            return (Criteria) this;
        }

        public Criteria andVouchStatusLessThanOrEqualTo(Integer value) {
            addCriterion("vouch_status <=", value, "vouchStatus");
            return (Criteria) this;
        }

        public Criteria andVouchStatusIn(List<Integer> values) {
            addCriterion("vouch_status in", values, "vouchStatus");
            return (Criteria) this;
        }

        public Criteria andVouchStatusNotIn(List<Integer> values) {
            addCriterion("vouch_status not in", values, "vouchStatus");
            return (Criteria) this;
        }

        public Criteria andVouchStatusBetween(Integer value1, Integer value2) {
            addCriterion("vouch_status between", value1, value2, "vouchStatus");
            return (Criteria) this;
        }

        public Criteria andVouchStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("vouch_status not between", value1, value2, "vouchStatus");
            return (Criteria) this;
        }

        public Criteria andVouchAdvanceStatusIsNull() {
            addCriterion("vouch_advance_status is null");
            return (Criteria) this;
        }

        public Criteria andVouchAdvanceStatusIsNotNull() {
            addCriterion("vouch_advance_status is not null");
            return (Criteria) this;
        }

        public Criteria andVouchAdvanceStatusEqualTo(Integer value) {
            addCriterion("vouch_advance_status =", value, "vouchAdvanceStatus");
            return (Criteria) this;
        }

        public Criteria andVouchAdvanceStatusNotEqualTo(Integer value) {
            addCriterion("vouch_advance_status <>", value, "vouchAdvanceStatus");
            return (Criteria) this;
        }

        public Criteria andVouchAdvanceStatusGreaterThan(Integer value) {
            addCriterion("vouch_advance_status >", value, "vouchAdvanceStatus");
            return (Criteria) this;
        }

        public Criteria andVouchAdvanceStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("vouch_advance_status >=", value, "vouchAdvanceStatus");
            return (Criteria) this;
        }

        public Criteria andVouchAdvanceStatusLessThan(Integer value) {
            addCriterion("vouch_advance_status <", value, "vouchAdvanceStatus");
            return (Criteria) this;
        }

        public Criteria andVouchAdvanceStatusLessThanOrEqualTo(Integer value) {
            addCriterion("vouch_advance_status <=", value, "vouchAdvanceStatus");
            return (Criteria) this;
        }

        public Criteria andVouchAdvanceStatusIn(List<Integer> values) {
            addCriterion("vouch_advance_status in", values, "vouchAdvanceStatus");
            return (Criteria) this;
        }

        public Criteria andVouchAdvanceStatusNotIn(List<Integer> values) {
            addCriterion("vouch_advance_status not in", values, "vouchAdvanceStatus");
            return (Criteria) this;
        }

        public Criteria andVouchAdvanceStatusBetween(Integer value1, Integer value2) {
            addCriterion("vouch_advance_status between", value1, value2, "vouchAdvanceStatus");
            return (Criteria) this;
        }

        public Criteria andVouchAdvanceStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("vouch_advance_status not between", value1, value2, "vouchAdvanceStatus");
            return (Criteria) this;
        }

        public Criteria andVouchUserStatusIsNull() {
            addCriterion("vouch_user_status is null");
            return (Criteria) this;
        }

        public Criteria andVouchUserStatusIsNotNull() {
            addCriterion("vouch_user_status is not null");
            return (Criteria) this;
        }

        public Criteria andVouchUserStatusEqualTo(Integer value) {
            addCriterion("vouch_user_status =", value, "vouchUserStatus");
            return (Criteria) this;
        }

        public Criteria andVouchUserStatusNotEqualTo(Integer value) {
            addCriterion("vouch_user_status <>", value, "vouchUserStatus");
            return (Criteria) this;
        }

        public Criteria andVouchUserStatusGreaterThan(Integer value) {
            addCriterion("vouch_user_status >", value, "vouchUserStatus");
            return (Criteria) this;
        }

        public Criteria andVouchUserStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("vouch_user_status >=", value, "vouchUserStatus");
            return (Criteria) this;
        }

        public Criteria andVouchUserStatusLessThan(Integer value) {
            addCriterion("vouch_user_status <", value, "vouchUserStatus");
            return (Criteria) this;
        }

        public Criteria andVouchUserStatusLessThanOrEqualTo(Integer value) {
            addCriterion("vouch_user_status <=", value, "vouchUserStatus");
            return (Criteria) this;
        }

        public Criteria andVouchUserStatusIn(List<Integer> values) {
            addCriterion("vouch_user_status in", values, "vouchUserStatus");
            return (Criteria) this;
        }

        public Criteria andVouchUserStatusNotIn(List<Integer> values) {
            addCriterion("vouch_user_status not in", values, "vouchUserStatus");
            return (Criteria) this;
        }

        public Criteria andVouchUserStatusBetween(Integer value1, Integer value2) {
            addCriterion("vouch_user_status between", value1, value2, "vouchUserStatus");
            return (Criteria) this;
        }

        public Criteria andVouchUserStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("vouch_user_status not between", value1, value2, "vouchUserStatus");
            return (Criteria) this;
        }

        public Criteria andVouchUsersIsNull() {
            addCriterion("vouch_users is null");
            return (Criteria) this;
        }

        public Criteria andVouchUsersIsNotNull() {
            addCriterion("vouch_users is not null");
            return (Criteria) this;
        }

        public Criteria andVouchUsersEqualTo(String value) {
            addCriterion("vouch_users =", value, "vouchUsers");
            return (Criteria) this;
        }

        public Criteria andVouchUsersNotEqualTo(String value) {
            addCriterion("vouch_users <>", value, "vouchUsers");
            return (Criteria) this;
        }

        public Criteria andVouchUsersGreaterThan(String value) {
            addCriterion("vouch_users >", value, "vouchUsers");
            return (Criteria) this;
        }

        public Criteria andVouchUsersGreaterThanOrEqualTo(String value) {
            addCriterion("vouch_users >=", value, "vouchUsers");
            return (Criteria) this;
        }

        public Criteria andVouchUsersLessThan(String value) {
            addCriterion("vouch_users <", value, "vouchUsers");
            return (Criteria) this;
        }

        public Criteria andVouchUsersLessThanOrEqualTo(String value) {
            addCriterion("vouch_users <=", value, "vouchUsers");
            return (Criteria) this;
        }

        public Criteria andVouchUsersLike(String value) {
            addCriterion("vouch_users like", value, "vouchUsers");
            return (Criteria) this;
        }

        public Criteria andVouchUsersNotLike(String value) {
            addCriterion("vouch_users not like", value, "vouchUsers");
            return (Criteria) this;
        }

        public Criteria andVouchUsersIn(List<String> values) {
            addCriterion("vouch_users in", values, "vouchUsers");
            return (Criteria) this;
        }

        public Criteria andVouchUsersNotIn(List<String> values) {
            addCriterion("vouch_users not in", values, "vouchUsers");
            return (Criteria) this;
        }

        public Criteria andVouchUsersBetween(String value1, String value2) {
            addCriterion("vouch_users between", value1, value2, "vouchUsers");
            return (Criteria) this;
        }

        public Criteria andVouchUsersNotBetween(String value1, String value2) {
            addCriterion("vouch_users not between", value1, value2, "vouchUsers");
            return (Criteria) this;
        }

        public Criteria andVouchAccountIsNull() {
            addCriterion("vouch_account is null");
            return (Criteria) this;
        }

        public Criteria andVouchAccountIsNotNull() {
            addCriterion("vouch_account is not null");
            return (Criteria) this;
        }

        public Criteria andVouchAccountEqualTo(BigDecimal value) {
            addCriterion("vouch_account =", value, "vouchAccount");
            return (Criteria) this;
        }

        public Criteria andVouchAccountNotEqualTo(BigDecimal value) {
            addCriterion("vouch_account <>", value, "vouchAccount");
            return (Criteria) this;
        }

        public Criteria andVouchAccountGreaterThan(BigDecimal value) {
            addCriterion("vouch_account >", value, "vouchAccount");
            return (Criteria) this;
        }

        public Criteria andVouchAccountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("vouch_account >=", value, "vouchAccount");
            return (Criteria) this;
        }

        public Criteria andVouchAccountLessThan(BigDecimal value) {
            addCriterion("vouch_account <", value, "vouchAccount");
            return (Criteria) this;
        }

        public Criteria andVouchAccountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("vouch_account <=", value, "vouchAccount");
            return (Criteria) this;
        }

        public Criteria andVouchAccountIn(List<BigDecimal> values) {
            addCriterion("vouch_account in", values, "vouchAccount");
            return (Criteria) this;
        }

        public Criteria andVouchAccountNotIn(List<BigDecimal> values) {
            addCriterion("vouch_account not in", values, "vouchAccount");
            return (Criteria) this;
        }

        public Criteria andVouchAccountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("vouch_account between", value1, value2, "vouchAccount");
            return (Criteria) this;
        }

        public Criteria andVouchAccountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("vouch_account not between", value1, value2, "vouchAccount");
            return (Criteria) this;
        }

        public Criteria andVouchAccountYesIsNull() {
            addCriterion("vouch_account_yes is null");
            return (Criteria) this;
        }

        public Criteria andVouchAccountYesIsNotNull() {
            addCriterion("vouch_account_yes is not null");
            return (Criteria) this;
        }

        public Criteria andVouchAccountYesEqualTo(BigDecimal value) {
            addCriterion("vouch_account_yes =", value, "vouchAccountYes");
            return (Criteria) this;
        }

        public Criteria andVouchAccountYesNotEqualTo(BigDecimal value) {
            addCriterion("vouch_account_yes <>", value, "vouchAccountYes");
            return (Criteria) this;
        }

        public Criteria andVouchAccountYesGreaterThan(BigDecimal value) {
            addCriterion("vouch_account_yes >", value, "vouchAccountYes");
            return (Criteria) this;
        }

        public Criteria andVouchAccountYesGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("vouch_account_yes >=", value, "vouchAccountYes");
            return (Criteria) this;
        }

        public Criteria andVouchAccountYesLessThan(BigDecimal value) {
            addCriterion("vouch_account_yes <", value, "vouchAccountYes");
            return (Criteria) this;
        }

        public Criteria andVouchAccountYesLessThanOrEqualTo(BigDecimal value) {
            addCriterion("vouch_account_yes <=", value, "vouchAccountYes");
            return (Criteria) this;
        }

        public Criteria andVouchAccountYesIn(List<BigDecimal> values) {
            addCriterion("vouch_account_yes in", values, "vouchAccountYes");
            return (Criteria) this;
        }

        public Criteria andVouchAccountYesNotIn(List<BigDecimal> values) {
            addCriterion("vouch_account_yes not in", values, "vouchAccountYes");
            return (Criteria) this;
        }

        public Criteria andVouchAccountYesBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("vouch_account_yes between", value1, value2, "vouchAccountYes");
            return (Criteria) this;
        }

        public Criteria andVouchAccountYesNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("vouch_account_yes not between", value1, value2, "vouchAccountYes");
            return (Criteria) this;
        }

        public Criteria andVouchAccountWaitIsNull() {
            addCriterion("vouch_account_wait is null");
            return (Criteria) this;
        }

        public Criteria andVouchAccountWaitIsNotNull() {
            addCriterion("vouch_account_wait is not null");
            return (Criteria) this;
        }

        public Criteria andVouchAccountWaitEqualTo(BigDecimal value) {
            addCriterion("vouch_account_wait =", value, "vouchAccountWait");
            return (Criteria) this;
        }

        public Criteria andVouchAccountWaitNotEqualTo(BigDecimal value) {
            addCriterion("vouch_account_wait <>", value, "vouchAccountWait");
            return (Criteria) this;
        }

        public Criteria andVouchAccountWaitGreaterThan(BigDecimal value) {
            addCriterion("vouch_account_wait >", value, "vouchAccountWait");
            return (Criteria) this;
        }

        public Criteria andVouchAccountWaitGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("vouch_account_wait >=", value, "vouchAccountWait");
            return (Criteria) this;
        }

        public Criteria andVouchAccountWaitLessThan(BigDecimal value) {
            addCriterion("vouch_account_wait <", value, "vouchAccountWait");
            return (Criteria) this;
        }

        public Criteria andVouchAccountWaitLessThanOrEqualTo(BigDecimal value) {
            addCriterion("vouch_account_wait <=", value, "vouchAccountWait");
            return (Criteria) this;
        }

        public Criteria andVouchAccountWaitIn(List<BigDecimal> values) {
            addCriterion("vouch_account_wait in", values, "vouchAccountWait");
            return (Criteria) this;
        }

        public Criteria andVouchAccountWaitNotIn(List<BigDecimal> values) {
            addCriterion("vouch_account_wait not in", values, "vouchAccountWait");
            return (Criteria) this;
        }

        public Criteria andVouchAccountWaitBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("vouch_account_wait between", value1, value2, "vouchAccountWait");
            return (Criteria) this;
        }

        public Criteria andVouchAccountWaitNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("vouch_account_wait not between", value1, value2, "vouchAccountWait");
            return (Criteria) this;
        }

        public Criteria andVouchAccountScaleIsNull() {
            addCriterion("vouch_account_scale is null");
            return (Criteria) this;
        }

        public Criteria andVouchAccountScaleIsNotNull() {
            addCriterion("vouch_account_scale is not null");
            return (Criteria) this;
        }

        public Criteria andVouchAccountScaleEqualTo(Long value) {
            addCriterion("vouch_account_scale =", value, "vouchAccountScale");
            return (Criteria) this;
        }

        public Criteria andVouchAccountScaleNotEqualTo(Long value) {
            addCriterion("vouch_account_scale <>", value, "vouchAccountScale");
            return (Criteria) this;
        }

        public Criteria andVouchAccountScaleGreaterThan(Long value) {
            addCriterion("vouch_account_scale >", value, "vouchAccountScale");
            return (Criteria) this;
        }

        public Criteria andVouchAccountScaleGreaterThanOrEqualTo(Long value) {
            addCriterion("vouch_account_scale >=", value, "vouchAccountScale");
            return (Criteria) this;
        }

        public Criteria andVouchAccountScaleLessThan(Long value) {
            addCriterion("vouch_account_scale <", value, "vouchAccountScale");
            return (Criteria) this;
        }

        public Criteria andVouchAccountScaleLessThanOrEqualTo(Long value) {
            addCriterion("vouch_account_scale <=", value, "vouchAccountScale");
            return (Criteria) this;
        }

        public Criteria andVouchAccountScaleIn(List<Long> values) {
            addCriterion("vouch_account_scale in", values, "vouchAccountScale");
            return (Criteria) this;
        }

        public Criteria andVouchAccountScaleNotIn(List<Long> values) {
            addCriterion("vouch_account_scale not in", values, "vouchAccountScale");
            return (Criteria) this;
        }

        public Criteria andVouchAccountScaleBetween(Long value1, Long value2) {
            addCriterion("vouch_account_scale between", value1, value2, "vouchAccountScale");
            return (Criteria) this;
        }

        public Criteria andVouchAccountScaleNotBetween(Long value1, Long value2) {
            addCriterion("vouch_account_scale not between", value1, value2, "vouchAccountScale");
            return (Criteria) this;
        }

        public Criteria andVouchTimesIsNull() {
            addCriterion("vouch_times is null");
            return (Criteria) this;
        }

        public Criteria andVouchTimesIsNotNull() {
            addCriterion("vouch_times is not null");
            return (Criteria) this;
        }

        public Criteria andVouchTimesEqualTo(Integer value) {
            addCriterion("vouch_times =", value, "vouchTimes");
            return (Criteria) this;
        }

        public Criteria andVouchTimesNotEqualTo(Integer value) {
            addCriterion("vouch_times <>", value, "vouchTimes");
            return (Criteria) this;
        }

        public Criteria andVouchTimesGreaterThan(Integer value) {
            addCriterion("vouch_times >", value, "vouchTimes");
            return (Criteria) this;
        }

        public Criteria andVouchTimesGreaterThanOrEqualTo(Integer value) {
            addCriterion("vouch_times >=", value, "vouchTimes");
            return (Criteria) this;
        }

        public Criteria andVouchTimesLessThan(Integer value) {
            addCriterion("vouch_times <", value, "vouchTimes");
            return (Criteria) this;
        }

        public Criteria andVouchTimesLessThanOrEqualTo(Integer value) {
            addCriterion("vouch_times <=", value, "vouchTimes");
            return (Criteria) this;
        }

        public Criteria andVouchTimesIn(List<Integer> values) {
            addCriterion("vouch_times in", values, "vouchTimes");
            return (Criteria) this;
        }

        public Criteria andVouchTimesNotIn(List<Integer> values) {
            addCriterion("vouch_times not in", values, "vouchTimes");
            return (Criteria) this;
        }

        public Criteria andVouchTimesBetween(Integer value1, Integer value2) {
            addCriterion("vouch_times between", value1, value2, "vouchTimes");
            return (Criteria) this;
        }

        public Criteria andVouchTimesNotBetween(Integer value1, Integer value2) {
            addCriterion("vouch_times not between", value1, value2, "vouchTimes");
            return (Criteria) this;
        }

        public Criteria andVouchAwardStatusIsNull() {
            addCriterion("vouch_award_status is null");
            return (Criteria) this;
        }

        public Criteria andVouchAwardStatusIsNotNull() {
            addCriterion("vouch_award_status is not null");
            return (Criteria) this;
        }

        public Criteria andVouchAwardStatusEqualTo(Integer value) {
            addCriterion("vouch_award_status =", value, "vouchAwardStatus");
            return (Criteria) this;
        }

        public Criteria andVouchAwardStatusNotEqualTo(Integer value) {
            addCriterion("vouch_award_status <>", value, "vouchAwardStatus");
            return (Criteria) this;
        }

        public Criteria andVouchAwardStatusGreaterThan(Integer value) {
            addCriterion("vouch_award_status >", value, "vouchAwardStatus");
            return (Criteria) this;
        }

        public Criteria andVouchAwardStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("vouch_award_status >=", value, "vouchAwardStatus");
            return (Criteria) this;
        }

        public Criteria andVouchAwardStatusLessThan(Integer value) {
            addCriterion("vouch_award_status <", value, "vouchAwardStatus");
            return (Criteria) this;
        }

        public Criteria andVouchAwardStatusLessThanOrEqualTo(Integer value) {
            addCriterion("vouch_award_status <=", value, "vouchAwardStatus");
            return (Criteria) this;
        }

        public Criteria andVouchAwardStatusIn(List<Integer> values) {
            addCriterion("vouch_award_status in", values, "vouchAwardStatus");
            return (Criteria) this;
        }

        public Criteria andVouchAwardStatusNotIn(List<Integer> values) {
            addCriterion("vouch_award_status not in", values, "vouchAwardStatus");
            return (Criteria) this;
        }

        public Criteria andVouchAwardStatusBetween(Integer value1, Integer value2) {
            addCriterion("vouch_award_status between", value1, value2, "vouchAwardStatus");
            return (Criteria) this;
        }

        public Criteria andVouchAwardStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("vouch_award_status not between", value1, value2, "vouchAwardStatus");
            return (Criteria) this;
        }

        public Criteria andVouchAwardScaleIsNull() {
            addCriterion("vouch_award_scale is null");
            return (Criteria) this;
        }

        public Criteria andVouchAwardScaleIsNotNull() {
            addCriterion("vouch_award_scale is not null");
            return (Criteria) this;
        }

        public Criteria andVouchAwardScaleEqualTo(BigDecimal value) {
            addCriterion("vouch_award_scale =", value, "vouchAwardScale");
            return (Criteria) this;
        }

        public Criteria andVouchAwardScaleNotEqualTo(BigDecimal value) {
            addCriterion("vouch_award_scale <>", value, "vouchAwardScale");
            return (Criteria) this;
        }

        public Criteria andVouchAwardScaleGreaterThan(BigDecimal value) {
            addCriterion("vouch_award_scale >", value, "vouchAwardScale");
            return (Criteria) this;
        }

        public Criteria andVouchAwardScaleGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("vouch_award_scale >=", value, "vouchAwardScale");
            return (Criteria) this;
        }

        public Criteria andVouchAwardScaleLessThan(BigDecimal value) {
            addCriterion("vouch_award_scale <", value, "vouchAwardScale");
            return (Criteria) this;
        }

        public Criteria andVouchAwardScaleLessThanOrEqualTo(BigDecimal value) {
            addCriterion("vouch_award_scale <=", value, "vouchAwardScale");
            return (Criteria) this;
        }

        public Criteria andVouchAwardScaleIn(List<BigDecimal> values) {
            addCriterion("vouch_award_scale in", values, "vouchAwardScale");
            return (Criteria) this;
        }

        public Criteria andVouchAwardScaleNotIn(List<BigDecimal> values) {
            addCriterion("vouch_award_scale not in", values, "vouchAwardScale");
            return (Criteria) this;
        }

        public Criteria andVouchAwardScaleBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("vouch_award_scale between", value1, value2, "vouchAwardScale");
            return (Criteria) this;
        }

        public Criteria andVouchAwardScaleNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("vouch_award_scale not between", value1, value2, "vouchAwardScale");
            return (Criteria) this;
        }

        public Criteria andVouchAwardAccountIsNull() {
            addCriterion("vouch_award_account is null");
            return (Criteria) this;
        }

        public Criteria andVouchAwardAccountIsNotNull() {
            addCriterion("vouch_award_account is not null");
            return (Criteria) this;
        }

        public Criteria andVouchAwardAccountEqualTo(BigDecimal value) {
            addCriterion("vouch_award_account =", value, "vouchAwardAccount");
            return (Criteria) this;
        }

        public Criteria andVouchAwardAccountNotEqualTo(BigDecimal value) {
            addCriterion("vouch_award_account <>", value, "vouchAwardAccount");
            return (Criteria) this;
        }

        public Criteria andVouchAwardAccountGreaterThan(BigDecimal value) {
            addCriterion("vouch_award_account >", value, "vouchAwardAccount");
            return (Criteria) this;
        }

        public Criteria andVouchAwardAccountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("vouch_award_account >=", value, "vouchAwardAccount");
            return (Criteria) this;
        }

        public Criteria andVouchAwardAccountLessThan(BigDecimal value) {
            addCriterion("vouch_award_account <", value, "vouchAwardAccount");
            return (Criteria) this;
        }

        public Criteria andVouchAwardAccountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("vouch_award_account <=", value, "vouchAwardAccount");
            return (Criteria) this;
        }

        public Criteria andVouchAwardAccountIn(List<BigDecimal> values) {
            addCriterion("vouch_award_account in", values, "vouchAwardAccount");
            return (Criteria) this;
        }

        public Criteria andVouchAwardAccountNotIn(List<BigDecimal> values) {
            addCriterion("vouch_award_account not in", values, "vouchAwardAccount");
            return (Criteria) this;
        }

        public Criteria andVouchAwardAccountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("vouch_award_account between", value1, value2, "vouchAwardAccount");
            return (Criteria) this;
        }

        public Criteria andVouchAwardAccountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("vouch_award_account not between", value1, value2, "vouchAwardAccount");
            return (Criteria) this;
        }

        public Criteria andVoucherNameIsNull() {
            addCriterion("voucher_name is null");
            return (Criteria) this;
        }

        public Criteria andVoucherNameIsNotNull() {
            addCriterion("voucher_name is not null");
            return (Criteria) this;
        }

        public Criteria andVoucherNameEqualTo(String value) {
            addCriterion("voucher_name =", value, "voucherName");
            return (Criteria) this;
        }

        public Criteria andVoucherNameNotEqualTo(String value) {
            addCriterion("voucher_name <>", value, "voucherName");
            return (Criteria) this;
        }

        public Criteria andVoucherNameGreaterThan(String value) {
            addCriterion("voucher_name >", value, "voucherName");
            return (Criteria) this;
        }

        public Criteria andVoucherNameGreaterThanOrEqualTo(String value) {
            addCriterion("voucher_name >=", value, "voucherName");
            return (Criteria) this;
        }

        public Criteria andVoucherNameLessThan(String value) {
            addCriterion("voucher_name <", value, "voucherName");
            return (Criteria) this;
        }

        public Criteria andVoucherNameLessThanOrEqualTo(String value) {
            addCriterion("voucher_name <=", value, "voucherName");
            return (Criteria) this;
        }

        public Criteria andVoucherNameLike(String value) {
            addCriterion("voucher_name like", value, "voucherName");
            return (Criteria) this;
        }

        public Criteria andVoucherNameNotLike(String value) {
            addCriterion("voucher_name not like", value, "voucherName");
            return (Criteria) this;
        }

        public Criteria andVoucherNameIn(List<String> values) {
            addCriterion("voucher_name in", values, "voucherName");
            return (Criteria) this;
        }

        public Criteria andVoucherNameNotIn(List<String> values) {
            addCriterion("voucher_name not in", values, "voucherName");
            return (Criteria) this;
        }

        public Criteria andVoucherNameBetween(String value1, String value2) {
            addCriterion("voucher_name between", value1, value2, "voucherName");
            return (Criteria) this;
        }

        public Criteria andVoucherNameNotBetween(String value1, String value2) {
            addCriterion("voucher_name not between", value1, value2, "voucherName");
            return (Criteria) this;
        }

        public Criteria andVoucherLianxiIsNull() {
            addCriterion("voucher_lianxi is null");
            return (Criteria) this;
        }

        public Criteria andVoucherLianxiIsNotNull() {
            addCriterion("voucher_lianxi is not null");
            return (Criteria) this;
        }

        public Criteria andVoucherLianxiEqualTo(String value) {
            addCriterion("voucher_lianxi =", value, "voucherLianxi");
            return (Criteria) this;
        }

        public Criteria andVoucherLianxiNotEqualTo(String value) {
            addCriterion("voucher_lianxi <>", value, "voucherLianxi");
            return (Criteria) this;
        }

        public Criteria andVoucherLianxiGreaterThan(String value) {
            addCriterion("voucher_lianxi >", value, "voucherLianxi");
            return (Criteria) this;
        }

        public Criteria andVoucherLianxiGreaterThanOrEqualTo(String value) {
            addCriterion("voucher_lianxi >=", value, "voucherLianxi");
            return (Criteria) this;
        }

        public Criteria andVoucherLianxiLessThan(String value) {
            addCriterion("voucher_lianxi <", value, "voucherLianxi");
            return (Criteria) this;
        }

        public Criteria andVoucherLianxiLessThanOrEqualTo(String value) {
            addCriterion("voucher_lianxi <=", value, "voucherLianxi");
            return (Criteria) this;
        }

        public Criteria andVoucherLianxiLike(String value) {
            addCriterion("voucher_lianxi like", value, "voucherLianxi");
            return (Criteria) this;
        }

        public Criteria andVoucherLianxiNotLike(String value) {
            addCriterion("voucher_lianxi not like", value, "voucherLianxi");
            return (Criteria) this;
        }

        public Criteria andVoucherLianxiIn(List<String> values) {
            addCriterion("voucher_lianxi in", values, "voucherLianxi");
            return (Criteria) this;
        }

        public Criteria andVoucherLianxiNotIn(List<String> values) {
            addCriterion("voucher_lianxi not in", values, "voucherLianxi");
            return (Criteria) this;
        }

        public Criteria andVoucherLianxiBetween(String value1, String value2) {
            addCriterion("voucher_lianxi between", value1, value2, "voucherLianxi");
            return (Criteria) this;
        }

        public Criteria andVoucherLianxiNotBetween(String value1, String value2) {
            addCriterion("voucher_lianxi not between", value1, value2, "voucherLianxi");
            return (Criteria) this;
        }

        public Criteria andVoucherAttIsNull() {
            addCriterion("voucher_att is null");
            return (Criteria) this;
        }

        public Criteria andVoucherAttIsNotNull() {
            addCriterion("voucher_att is not null");
            return (Criteria) this;
        }

        public Criteria andVoucherAttEqualTo(String value) {
            addCriterion("voucher_att =", value, "voucherAtt");
            return (Criteria) this;
        }

        public Criteria andVoucherAttNotEqualTo(String value) {
            addCriterion("voucher_att <>", value, "voucherAtt");
            return (Criteria) this;
        }

        public Criteria andVoucherAttGreaterThan(String value) {
            addCriterion("voucher_att >", value, "voucherAtt");
            return (Criteria) this;
        }

        public Criteria andVoucherAttGreaterThanOrEqualTo(String value) {
            addCriterion("voucher_att >=", value, "voucherAtt");
            return (Criteria) this;
        }

        public Criteria andVoucherAttLessThan(String value) {
            addCriterion("voucher_att <", value, "voucherAtt");
            return (Criteria) this;
        }

        public Criteria andVoucherAttLessThanOrEqualTo(String value) {
            addCriterion("voucher_att <=", value, "voucherAtt");
            return (Criteria) this;
        }

        public Criteria andVoucherAttLike(String value) {
            addCriterion("voucher_att like", value, "voucherAtt");
            return (Criteria) this;
        }

        public Criteria andVoucherAttNotLike(String value) {
            addCriterion("voucher_att not like", value, "voucherAtt");
            return (Criteria) this;
        }

        public Criteria andVoucherAttIn(List<String> values) {
            addCriterion("voucher_att in", values, "voucherAtt");
            return (Criteria) this;
        }

        public Criteria andVoucherAttNotIn(List<String> values) {
            addCriterion("voucher_att not in", values, "voucherAtt");
            return (Criteria) this;
        }

        public Criteria andVoucherAttBetween(String value1, String value2) {
            addCriterion("voucher_att between", value1, value2, "voucherAtt");
            return (Criteria) this;
        }

        public Criteria andVoucherAttNotBetween(String value1, String value2) {
            addCriterion("voucher_att not between", value1, value2, "voucherAtt");
            return (Criteria) this;
        }

        public Criteria andVouchjgNameIsNull() {
            addCriterion("vouchjg_name is null");
            return (Criteria) this;
        }

        public Criteria andVouchjgNameIsNotNull() {
            addCriterion("vouchjg_name is not null");
            return (Criteria) this;
        }

        public Criteria andVouchjgNameEqualTo(String value) {
            addCriterion("vouchjg_name =", value, "vouchjgName");
            return (Criteria) this;
        }

        public Criteria andVouchjgNameNotEqualTo(String value) {
            addCriterion("vouchjg_name <>", value, "vouchjgName");
            return (Criteria) this;
        }

        public Criteria andVouchjgNameGreaterThan(String value) {
            addCriterion("vouchjg_name >", value, "vouchjgName");
            return (Criteria) this;
        }

        public Criteria andVouchjgNameGreaterThanOrEqualTo(String value) {
            addCriterion("vouchjg_name >=", value, "vouchjgName");
            return (Criteria) this;
        }

        public Criteria andVouchjgNameLessThan(String value) {
            addCriterion("vouchjg_name <", value, "vouchjgName");
            return (Criteria) this;
        }

        public Criteria andVouchjgNameLessThanOrEqualTo(String value) {
            addCriterion("vouchjg_name <=", value, "vouchjgName");
            return (Criteria) this;
        }

        public Criteria andVouchjgNameLike(String value) {
            addCriterion("vouchjg_name like", value, "vouchjgName");
            return (Criteria) this;
        }

        public Criteria andVouchjgNameNotLike(String value) {
            addCriterion("vouchjg_name not like", value, "vouchjgName");
            return (Criteria) this;
        }

        public Criteria andVouchjgNameIn(List<String> values) {
            addCriterion("vouchjg_name in", values, "vouchjgName");
            return (Criteria) this;
        }

        public Criteria andVouchjgNameNotIn(List<String> values) {
            addCriterion("vouchjg_name not in", values, "vouchjgName");
            return (Criteria) this;
        }

        public Criteria andVouchjgNameBetween(String value1, String value2) {
            addCriterion("vouchjg_name between", value1, value2, "vouchjgName");
            return (Criteria) this;
        }

        public Criteria andVouchjgNameNotBetween(String value1, String value2) {
            addCriterion("vouchjg_name not between", value1, value2, "vouchjgName");
            return (Criteria) this;
        }

        public Criteria andVouchjgLianxiIsNull() {
            addCriterion("vouchjg_lianxi is null");
            return (Criteria) this;
        }

        public Criteria andVouchjgLianxiIsNotNull() {
            addCriterion("vouchjg_lianxi is not null");
            return (Criteria) this;
        }

        public Criteria andVouchjgLianxiEqualTo(String value) {
            addCriterion("vouchjg_lianxi =", value, "vouchjgLianxi");
            return (Criteria) this;
        }

        public Criteria andVouchjgLianxiNotEqualTo(String value) {
            addCriterion("vouchjg_lianxi <>", value, "vouchjgLianxi");
            return (Criteria) this;
        }

        public Criteria andVouchjgLianxiGreaterThan(String value) {
            addCriterion("vouchjg_lianxi >", value, "vouchjgLianxi");
            return (Criteria) this;
        }

        public Criteria andVouchjgLianxiGreaterThanOrEqualTo(String value) {
            addCriterion("vouchjg_lianxi >=", value, "vouchjgLianxi");
            return (Criteria) this;
        }

        public Criteria andVouchjgLianxiLessThan(String value) {
            addCriterion("vouchjg_lianxi <", value, "vouchjgLianxi");
            return (Criteria) this;
        }

        public Criteria andVouchjgLianxiLessThanOrEqualTo(String value) {
            addCriterion("vouchjg_lianxi <=", value, "vouchjgLianxi");
            return (Criteria) this;
        }

        public Criteria andVouchjgLianxiLike(String value) {
            addCriterion("vouchjg_lianxi like", value, "vouchjgLianxi");
            return (Criteria) this;
        }

        public Criteria andVouchjgLianxiNotLike(String value) {
            addCriterion("vouchjg_lianxi not like", value, "vouchjgLianxi");
            return (Criteria) this;
        }

        public Criteria andVouchjgLianxiIn(List<String> values) {
            addCriterion("vouchjg_lianxi in", values, "vouchjgLianxi");
            return (Criteria) this;
        }

        public Criteria andVouchjgLianxiNotIn(List<String> values) {
            addCriterion("vouchjg_lianxi not in", values, "vouchjgLianxi");
            return (Criteria) this;
        }

        public Criteria andVouchjgLianxiBetween(String value1, String value2) {
            addCriterion("vouchjg_lianxi between", value1, value2, "vouchjgLianxi");
            return (Criteria) this;
        }

        public Criteria andVouchjgLianxiNotBetween(String value1, String value2) {
            addCriterion("vouchjg_lianxi not between", value1, value2, "vouchjgLianxi");
            return (Criteria) this;
        }

        public Criteria andVouchjgJsIsNull() {
            addCriterion("vouchjg_js is null");
            return (Criteria) this;
        }

        public Criteria andVouchjgJsIsNotNull() {
            addCriterion("vouchjg_js is not null");
            return (Criteria) this;
        }

        public Criteria andVouchjgJsEqualTo(String value) {
            addCriterion("vouchjg_js =", value, "vouchjgJs");
            return (Criteria) this;
        }

        public Criteria andVouchjgJsNotEqualTo(String value) {
            addCriterion("vouchjg_js <>", value, "vouchjgJs");
            return (Criteria) this;
        }

        public Criteria andVouchjgJsGreaterThan(String value) {
            addCriterion("vouchjg_js >", value, "vouchjgJs");
            return (Criteria) this;
        }

        public Criteria andVouchjgJsGreaterThanOrEqualTo(String value) {
            addCriterion("vouchjg_js >=", value, "vouchjgJs");
            return (Criteria) this;
        }

        public Criteria andVouchjgJsLessThan(String value) {
            addCriterion("vouchjg_js <", value, "vouchjgJs");
            return (Criteria) this;
        }

        public Criteria andVouchjgJsLessThanOrEqualTo(String value) {
            addCriterion("vouchjg_js <=", value, "vouchjgJs");
            return (Criteria) this;
        }

        public Criteria andVouchjgJsLike(String value) {
            addCriterion("vouchjg_js like", value, "vouchjgJs");
            return (Criteria) this;
        }

        public Criteria andVouchjgJsNotLike(String value) {
            addCriterion("vouchjg_js not like", value, "vouchjgJs");
            return (Criteria) this;
        }

        public Criteria andVouchjgJsIn(List<String> values) {
            addCriterion("vouchjg_js in", values, "vouchjgJs");
            return (Criteria) this;
        }

        public Criteria andVouchjgJsNotIn(List<String> values) {
            addCriterion("vouchjg_js not in", values, "vouchjgJs");
            return (Criteria) this;
        }

        public Criteria andVouchjgJsBetween(String value1, String value2) {
            addCriterion("vouchjg_js between", value1, value2, "vouchjgJs");
            return (Criteria) this;
        }

        public Criteria andVouchjgJsNotBetween(String value1, String value2) {
            addCriterion("vouchjg_js not between", value1, value2, "vouchjgJs");
            return (Criteria) this;
        }

        public Criteria andVouchjgXyIsNull() {
            addCriterion("vouchjg_xy is null");
            return (Criteria) this;
        }

        public Criteria andVouchjgXyIsNotNull() {
            addCriterion("vouchjg_xy is not null");
            return (Criteria) this;
        }

        public Criteria andVouchjgXyEqualTo(String value) {
            addCriterion("vouchjg_xy =", value, "vouchjgXy");
            return (Criteria) this;
        }

        public Criteria andVouchjgXyNotEqualTo(String value) {
            addCriterion("vouchjg_xy <>", value, "vouchjgXy");
            return (Criteria) this;
        }

        public Criteria andVouchjgXyGreaterThan(String value) {
            addCriterion("vouchjg_xy >", value, "vouchjgXy");
            return (Criteria) this;
        }

        public Criteria andVouchjgXyGreaterThanOrEqualTo(String value) {
            addCriterion("vouchjg_xy >=", value, "vouchjgXy");
            return (Criteria) this;
        }

        public Criteria andVouchjgXyLessThan(String value) {
            addCriterion("vouchjg_xy <", value, "vouchjgXy");
            return (Criteria) this;
        }

        public Criteria andVouchjgXyLessThanOrEqualTo(String value) {
            addCriterion("vouchjg_xy <=", value, "vouchjgXy");
            return (Criteria) this;
        }

        public Criteria andVouchjgXyLike(String value) {
            addCriterion("vouchjg_xy like", value, "vouchjgXy");
            return (Criteria) this;
        }

        public Criteria andVouchjgXyNotLike(String value) {
            addCriterion("vouchjg_xy not like", value, "vouchjgXy");
            return (Criteria) this;
        }

        public Criteria andVouchjgXyIn(List<String> values) {
            addCriterion("vouchjg_xy in", values, "vouchjgXy");
            return (Criteria) this;
        }

        public Criteria andVouchjgXyNotIn(List<String> values) {
            addCriterion("vouchjg_xy not in", values, "vouchjgXy");
            return (Criteria) this;
        }

        public Criteria andVouchjgXyBetween(String value1, String value2) {
            addCriterion("vouchjg_xy between", value1, value2, "vouchjgXy");
            return (Criteria) this;
        }

        public Criteria andVouchjgXyNotBetween(String value1, String value2) {
            addCriterion("vouchjg_xy not between", value1, value2, "vouchjgXy");
            return (Criteria) this;
        }

        public Criteria andFastStatusIsNull() {
            addCriterion("fast_status is null");
            return (Criteria) this;
        }

        public Criteria andFastStatusIsNotNull() {
            addCriterion("fast_status is not null");
            return (Criteria) this;
        }

        public Criteria andFastStatusEqualTo(Integer value) {
            addCriterion("fast_status =", value, "fastStatus");
            return (Criteria) this;
        }

        public Criteria andFastStatusNotEqualTo(Integer value) {
            addCriterion("fast_status <>", value, "fastStatus");
            return (Criteria) this;
        }

        public Criteria andFastStatusGreaterThan(Integer value) {
            addCriterion("fast_status >", value, "fastStatus");
            return (Criteria) this;
        }

        public Criteria andFastStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("fast_status >=", value, "fastStatus");
            return (Criteria) this;
        }

        public Criteria andFastStatusLessThan(Integer value) {
            addCriterion("fast_status <", value, "fastStatus");
            return (Criteria) this;
        }

        public Criteria andFastStatusLessThanOrEqualTo(Integer value) {
            addCriterion("fast_status <=", value, "fastStatus");
            return (Criteria) this;
        }

        public Criteria andFastStatusIn(List<Integer> values) {
            addCriterion("fast_status in", values, "fastStatus");
            return (Criteria) this;
        }

        public Criteria andFastStatusNotIn(List<Integer> values) {
            addCriterion("fast_status not in", values, "fastStatus");
            return (Criteria) this;
        }

        public Criteria andFastStatusBetween(Integer value1, Integer value2) {
            addCriterion("fast_status between", value1, value2, "fastStatus");
            return (Criteria) this;
        }

        public Criteria andFastStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("fast_status not between", value1, value2, "fastStatus");
            return (Criteria) this;
        }

        public Criteria andVouchstatusIsNull() {
            addCriterion("vouchstatus is null");
            return (Criteria) this;
        }

        public Criteria andVouchstatusIsNotNull() {
            addCriterion("vouchstatus is not null");
            return (Criteria) this;
        }

        public Criteria andVouchstatusEqualTo(Integer value) {
            addCriterion("vouchstatus =", value, "vouchstatus");
            return (Criteria) this;
        }

        public Criteria andVouchstatusNotEqualTo(Integer value) {
            addCriterion("vouchstatus <>", value, "vouchstatus");
            return (Criteria) this;
        }

        public Criteria andVouchstatusGreaterThan(Integer value) {
            addCriterion("vouchstatus >", value, "vouchstatus");
            return (Criteria) this;
        }

        public Criteria andVouchstatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("vouchstatus >=", value, "vouchstatus");
            return (Criteria) this;
        }

        public Criteria andVouchstatusLessThan(Integer value) {
            addCriterion("vouchstatus <", value, "vouchstatus");
            return (Criteria) this;
        }

        public Criteria andVouchstatusLessThanOrEqualTo(Integer value) {
            addCriterion("vouchstatus <=", value, "vouchstatus");
            return (Criteria) this;
        }

        public Criteria andVouchstatusIn(List<Integer> values) {
            addCriterion("vouchstatus in", values, "vouchstatus");
            return (Criteria) this;
        }

        public Criteria andVouchstatusNotIn(List<Integer> values) {
            addCriterion("vouchstatus not in", values, "vouchstatus");
            return (Criteria) this;
        }

        public Criteria andVouchstatusBetween(Integer value1, Integer value2) {
            addCriterion("vouchstatus between", value1, value2, "vouchstatus");
            return (Criteria) this;
        }

        public Criteria andVouchstatusNotBetween(Integer value1, Integer value2) {
            addCriterion("vouchstatus not between", value1, value2, "vouchstatus");
            return (Criteria) this;
        }

        public Criteria andGroupStatusIsNull() {
            addCriterion("group_status is null");
            return (Criteria) this;
        }

        public Criteria andGroupStatusIsNotNull() {
            addCriterion("group_status is not null");
            return (Criteria) this;
        }

        public Criteria andGroupStatusEqualTo(Integer value) {
            addCriterion("group_status =", value, "groupStatus");
            return (Criteria) this;
        }

        public Criteria andGroupStatusNotEqualTo(Integer value) {
            addCriterion("group_status <>", value, "groupStatus");
            return (Criteria) this;
        }

        public Criteria andGroupStatusGreaterThan(Integer value) {
            addCriterion("group_status >", value, "groupStatus");
            return (Criteria) this;
        }

        public Criteria andGroupStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("group_status >=", value, "groupStatus");
            return (Criteria) this;
        }

        public Criteria andGroupStatusLessThan(Integer value) {
            addCriterion("group_status <", value, "groupStatus");
            return (Criteria) this;
        }

        public Criteria andGroupStatusLessThanOrEqualTo(Integer value) {
            addCriterion("group_status <=", value, "groupStatus");
            return (Criteria) this;
        }

        public Criteria andGroupStatusIn(List<Integer> values) {
            addCriterion("group_status in", values, "groupStatus");
            return (Criteria) this;
        }

        public Criteria andGroupStatusNotIn(List<Integer> values) {
            addCriterion("group_status not in", values, "groupStatus");
            return (Criteria) this;
        }

        public Criteria andGroupStatusBetween(Integer value1, Integer value2) {
            addCriterion("group_status between", value1, value2, "groupStatus");
            return (Criteria) this;
        }

        public Criteria andGroupStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("group_status not between", value1, value2, "groupStatus");
            return (Criteria) this;
        }

        public Criteria andGroupIdIsNull() {
            addCriterion("group_id is null");
            return (Criteria) this;
        }

        public Criteria andGroupIdIsNotNull() {
            addCriterion("group_id is not null");
            return (Criteria) this;
        }

        public Criteria andGroupIdEqualTo(Integer value) {
            addCriterion("group_id =", value, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdNotEqualTo(Integer value) {
            addCriterion("group_id <>", value, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdGreaterThan(Integer value) {
            addCriterion("group_id >", value, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("group_id >=", value, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdLessThan(Integer value) {
            addCriterion("group_id <", value, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdLessThanOrEqualTo(Integer value) {
            addCriterion("group_id <=", value, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdIn(List<Integer> values) {
            addCriterion("group_id in", values, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdNotIn(List<Integer> values) {
            addCriterion("group_id not in", values, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdBetween(Integer value1, Integer value2) {
            addCriterion("group_id between", value1, value2, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdNotBetween(Integer value1, Integer value2) {
            addCriterion("group_id not between", value1, value2, "groupId");
            return (Criteria) this;
        }

        public Criteria andAwardStatusIsNull() {
            addCriterion("award_status is null");
            return (Criteria) this;
        }

        public Criteria andAwardStatusIsNotNull() {
            addCriterion("award_status is not null");
            return (Criteria) this;
        }

        public Criteria andAwardStatusEqualTo(Integer value) {
            addCriterion("award_status =", value, "awardStatus");
            return (Criteria) this;
        }

        public Criteria andAwardStatusNotEqualTo(Integer value) {
            addCriterion("award_status <>", value, "awardStatus");
            return (Criteria) this;
        }

        public Criteria andAwardStatusGreaterThan(Integer value) {
            addCriterion("award_status >", value, "awardStatus");
            return (Criteria) this;
        }

        public Criteria andAwardStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("award_status >=", value, "awardStatus");
            return (Criteria) this;
        }

        public Criteria andAwardStatusLessThan(Integer value) {
            addCriterion("award_status <", value, "awardStatus");
            return (Criteria) this;
        }

        public Criteria andAwardStatusLessThanOrEqualTo(Integer value) {
            addCriterion("award_status <=", value, "awardStatus");
            return (Criteria) this;
        }

        public Criteria andAwardStatusIn(List<Integer> values) {
            addCriterion("award_status in", values, "awardStatus");
            return (Criteria) this;
        }

        public Criteria andAwardStatusNotIn(List<Integer> values) {
            addCriterion("award_status not in", values, "awardStatus");
            return (Criteria) this;
        }

        public Criteria andAwardStatusBetween(Integer value1, Integer value2) {
            addCriterion("award_status between", value1, value2, "awardStatus");
            return (Criteria) this;
        }

        public Criteria andAwardStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("award_status not between", value1, value2, "awardStatus");
            return (Criteria) this;
        }

        public Criteria andAwardFalseIsNull() {
            addCriterion("award_false is null");
            return (Criteria) this;
        }

        public Criteria andAwardFalseIsNotNull() {
            addCriterion("award_false is not null");
            return (Criteria) this;
        }

        public Criteria andAwardFalseEqualTo(Integer value) {
            addCriterion("award_false =", value, "awardFalse");
            return (Criteria) this;
        }

        public Criteria andAwardFalseNotEqualTo(Integer value) {
            addCriterion("award_false <>", value, "awardFalse");
            return (Criteria) this;
        }

        public Criteria andAwardFalseGreaterThan(Integer value) {
            addCriterion("award_false >", value, "awardFalse");
            return (Criteria) this;
        }

        public Criteria andAwardFalseGreaterThanOrEqualTo(Integer value) {
            addCriterion("award_false >=", value, "awardFalse");
            return (Criteria) this;
        }

        public Criteria andAwardFalseLessThan(Integer value) {
            addCriterion("award_false <", value, "awardFalse");
            return (Criteria) this;
        }

        public Criteria andAwardFalseLessThanOrEqualTo(Integer value) {
            addCriterion("award_false <=", value, "awardFalse");
            return (Criteria) this;
        }

        public Criteria andAwardFalseIn(List<Integer> values) {
            addCriterion("award_false in", values, "awardFalse");
            return (Criteria) this;
        }

        public Criteria andAwardFalseNotIn(List<Integer> values) {
            addCriterion("award_false not in", values, "awardFalse");
            return (Criteria) this;
        }

        public Criteria andAwardFalseBetween(Integer value1, Integer value2) {
            addCriterion("award_false between", value1, value2, "awardFalse");
            return (Criteria) this;
        }

        public Criteria andAwardFalseNotBetween(Integer value1, Integer value2) {
            addCriterion("award_false not between", value1, value2, "awardFalse");
            return (Criteria) this;
        }

        public Criteria andAwardAccountIsNull() {
            addCriterion("award_account is null");
            return (Criteria) this;
        }

        public Criteria andAwardAccountIsNotNull() {
            addCriterion("award_account is not null");
            return (Criteria) this;
        }

        public Criteria andAwardAccountEqualTo(BigDecimal value) {
            addCriterion("award_account =", value, "awardAccount");
            return (Criteria) this;
        }

        public Criteria andAwardAccountNotEqualTo(BigDecimal value) {
            addCriterion("award_account <>", value, "awardAccount");
            return (Criteria) this;
        }

        public Criteria andAwardAccountGreaterThan(BigDecimal value) {
            addCriterion("award_account >", value, "awardAccount");
            return (Criteria) this;
        }

        public Criteria andAwardAccountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("award_account >=", value, "awardAccount");
            return (Criteria) this;
        }

        public Criteria andAwardAccountLessThan(BigDecimal value) {
            addCriterion("award_account <", value, "awardAccount");
            return (Criteria) this;
        }

        public Criteria andAwardAccountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("award_account <=", value, "awardAccount");
            return (Criteria) this;
        }

        public Criteria andAwardAccountIn(List<BigDecimal> values) {
            addCriterion("award_account in", values, "awardAccount");
            return (Criteria) this;
        }

        public Criteria andAwardAccountNotIn(List<BigDecimal> values) {
            addCriterion("award_account not in", values, "awardAccount");
            return (Criteria) this;
        }

        public Criteria andAwardAccountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("award_account between", value1, value2, "awardAccount");
            return (Criteria) this;
        }

        public Criteria andAwardAccountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("award_account not between", value1, value2, "awardAccount");
            return (Criteria) this;
        }

        public Criteria andAwardScaleIsNull() {
            addCriterion("award_scale is null");
            return (Criteria) this;
        }

        public Criteria andAwardScaleIsNotNull() {
            addCriterion("award_scale is not null");
            return (Criteria) this;
        }

        public Criteria andAwardScaleEqualTo(BigDecimal value) {
            addCriterion("award_scale =", value, "awardScale");
            return (Criteria) this;
        }

        public Criteria andAwardScaleNotEqualTo(BigDecimal value) {
            addCriterion("award_scale <>", value, "awardScale");
            return (Criteria) this;
        }

        public Criteria andAwardScaleGreaterThan(BigDecimal value) {
            addCriterion("award_scale >", value, "awardScale");
            return (Criteria) this;
        }

        public Criteria andAwardScaleGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("award_scale >=", value, "awardScale");
            return (Criteria) this;
        }

        public Criteria andAwardScaleLessThan(BigDecimal value) {
            addCriterion("award_scale <", value, "awardScale");
            return (Criteria) this;
        }

        public Criteria andAwardScaleLessThanOrEqualTo(BigDecimal value) {
            addCriterion("award_scale <=", value, "awardScale");
            return (Criteria) this;
        }

        public Criteria andAwardScaleIn(List<BigDecimal> values) {
            addCriterion("award_scale in", values, "awardScale");
            return (Criteria) this;
        }

        public Criteria andAwardScaleNotIn(List<BigDecimal> values) {
            addCriterion("award_scale not in", values, "awardScale");
            return (Criteria) this;
        }

        public Criteria andAwardScaleBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("award_scale between", value1, value2, "awardScale");
            return (Criteria) this;
        }

        public Criteria andAwardScaleNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("award_scale not between", value1, value2, "awardScale");
            return (Criteria) this;
        }

        public Criteria andAwardAccountAllIsNull() {
            addCriterion("award_account_all is null");
            return (Criteria) this;
        }

        public Criteria andAwardAccountAllIsNotNull() {
            addCriterion("award_account_all is not null");
            return (Criteria) this;
        }

        public Criteria andAwardAccountAllEqualTo(BigDecimal value) {
            addCriterion("award_account_all =", value, "awardAccountAll");
            return (Criteria) this;
        }

        public Criteria andAwardAccountAllNotEqualTo(BigDecimal value) {
            addCriterion("award_account_all <>", value, "awardAccountAll");
            return (Criteria) this;
        }

        public Criteria andAwardAccountAllGreaterThan(BigDecimal value) {
            addCriterion("award_account_all >", value, "awardAccountAll");
            return (Criteria) this;
        }

        public Criteria andAwardAccountAllGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("award_account_all >=", value, "awardAccountAll");
            return (Criteria) this;
        }

        public Criteria andAwardAccountAllLessThan(BigDecimal value) {
            addCriterion("award_account_all <", value, "awardAccountAll");
            return (Criteria) this;
        }

        public Criteria andAwardAccountAllLessThanOrEqualTo(BigDecimal value) {
            addCriterion("award_account_all <=", value, "awardAccountAll");
            return (Criteria) this;
        }

        public Criteria andAwardAccountAllIn(List<BigDecimal> values) {
            addCriterion("award_account_all in", values, "awardAccountAll");
            return (Criteria) this;
        }

        public Criteria andAwardAccountAllNotIn(List<BigDecimal> values) {
            addCriterion("award_account_all not in", values, "awardAccountAll");
            return (Criteria) this;
        }

        public Criteria andAwardAccountAllBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("award_account_all between", value1, value2, "awardAccountAll");
            return (Criteria) this;
        }

        public Criteria andAwardAccountAllNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("award_account_all not between", value1, value2, "awardAccountAll");
            return (Criteria) this;
        }

        public Criteria andOpenAccountIsNull() {
            addCriterion("open_account is null");
            return (Criteria) this;
        }

        public Criteria andOpenAccountIsNotNull() {
            addCriterion("open_account is not null");
            return (Criteria) this;
        }

        public Criteria andOpenAccountEqualTo(Integer value) {
            addCriterion("open_account =", value, "openAccount");
            return (Criteria) this;
        }

        public Criteria andOpenAccountNotEqualTo(Integer value) {
            addCriterion("open_account <>", value, "openAccount");
            return (Criteria) this;
        }

        public Criteria andOpenAccountGreaterThan(Integer value) {
            addCriterion("open_account >", value, "openAccount");
            return (Criteria) this;
        }

        public Criteria andOpenAccountGreaterThanOrEqualTo(Integer value) {
            addCriterion("open_account >=", value, "openAccount");
            return (Criteria) this;
        }

        public Criteria andOpenAccountLessThan(Integer value) {
            addCriterion("open_account <", value, "openAccount");
            return (Criteria) this;
        }

        public Criteria andOpenAccountLessThanOrEqualTo(Integer value) {
            addCriterion("open_account <=", value, "openAccount");
            return (Criteria) this;
        }

        public Criteria andOpenAccountIn(List<Integer> values) {
            addCriterion("open_account in", values, "openAccount");
            return (Criteria) this;
        }

        public Criteria andOpenAccountNotIn(List<Integer> values) {
            addCriterion("open_account not in", values, "openAccount");
            return (Criteria) this;
        }

        public Criteria andOpenAccountBetween(Integer value1, Integer value2) {
            addCriterion("open_account between", value1, value2, "openAccount");
            return (Criteria) this;
        }

        public Criteria andOpenAccountNotBetween(Integer value1, Integer value2) {
            addCriterion("open_account not between", value1, value2, "openAccount");
            return (Criteria) this;
        }

        public Criteria andOpenBorrowIsNull() {
            addCriterion("open_borrow is null");
            return (Criteria) this;
        }

        public Criteria andOpenBorrowIsNotNull() {
            addCriterion("open_borrow is not null");
            return (Criteria) this;
        }

        public Criteria andOpenBorrowEqualTo(Integer value) {
            addCriterion("open_borrow =", value, "openBorrow");
            return (Criteria) this;
        }

        public Criteria andOpenBorrowNotEqualTo(Integer value) {
            addCriterion("open_borrow <>", value, "openBorrow");
            return (Criteria) this;
        }

        public Criteria andOpenBorrowGreaterThan(Integer value) {
            addCriterion("open_borrow >", value, "openBorrow");
            return (Criteria) this;
        }

        public Criteria andOpenBorrowGreaterThanOrEqualTo(Integer value) {
            addCriterion("open_borrow >=", value, "openBorrow");
            return (Criteria) this;
        }

        public Criteria andOpenBorrowLessThan(Integer value) {
            addCriterion("open_borrow <", value, "openBorrow");
            return (Criteria) this;
        }

        public Criteria andOpenBorrowLessThanOrEqualTo(Integer value) {
            addCriterion("open_borrow <=", value, "openBorrow");
            return (Criteria) this;
        }

        public Criteria andOpenBorrowIn(List<Integer> values) {
            addCriterion("open_borrow in", values, "openBorrow");
            return (Criteria) this;
        }

        public Criteria andOpenBorrowNotIn(List<Integer> values) {
            addCriterion("open_borrow not in", values, "openBorrow");
            return (Criteria) this;
        }

        public Criteria andOpenBorrowBetween(Integer value1, Integer value2) {
            addCriterion("open_borrow between", value1, value2, "openBorrow");
            return (Criteria) this;
        }

        public Criteria andOpenBorrowNotBetween(Integer value1, Integer value2) {
            addCriterion("open_borrow not between", value1, value2, "openBorrow");
            return (Criteria) this;
        }

        public Criteria andOpenTenderIsNull() {
            addCriterion("open_tender is null");
            return (Criteria) this;
        }

        public Criteria andOpenTenderIsNotNull() {
            addCriterion("open_tender is not null");
            return (Criteria) this;
        }

        public Criteria andOpenTenderEqualTo(Integer value) {
            addCriterion("open_tender =", value, "openTender");
            return (Criteria) this;
        }

        public Criteria andOpenTenderNotEqualTo(Integer value) {
            addCriterion("open_tender <>", value, "openTender");
            return (Criteria) this;
        }

        public Criteria andOpenTenderGreaterThan(Integer value) {
            addCriterion("open_tender >", value, "openTender");
            return (Criteria) this;
        }

        public Criteria andOpenTenderGreaterThanOrEqualTo(Integer value) {
            addCriterion("open_tender >=", value, "openTender");
            return (Criteria) this;
        }

        public Criteria andOpenTenderLessThan(Integer value) {
            addCriterion("open_tender <", value, "openTender");
            return (Criteria) this;
        }

        public Criteria andOpenTenderLessThanOrEqualTo(Integer value) {
            addCriterion("open_tender <=", value, "openTender");
            return (Criteria) this;
        }

        public Criteria andOpenTenderIn(List<Integer> values) {
            addCriterion("open_tender in", values, "openTender");
            return (Criteria) this;
        }

        public Criteria andOpenTenderNotIn(List<Integer> values) {
            addCriterion("open_tender not in", values, "openTender");
            return (Criteria) this;
        }

        public Criteria andOpenTenderBetween(Integer value1, Integer value2) {
            addCriterion("open_tender between", value1, value2, "openTender");
            return (Criteria) this;
        }

        public Criteria andOpenTenderNotBetween(Integer value1, Integer value2) {
            addCriterion("open_tender not between", value1, value2, "openTender");
            return (Criteria) this;
        }

        public Criteria andOpenCreditIsNull() {
            addCriterion("open_credit is null");
            return (Criteria) this;
        }

        public Criteria andOpenCreditIsNotNull() {
            addCriterion("open_credit is not null");
            return (Criteria) this;
        }

        public Criteria andOpenCreditEqualTo(Integer value) {
            addCriterion("open_credit =", value, "openCredit");
            return (Criteria) this;
        }

        public Criteria andOpenCreditNotEqualTo(Integer value) {
            addCriterion("open_credit <>", value, "openCredit");
            return (Criteria) this;
        }

        public Criteria andOpenCreditGreaterThan(Integer value) {
            addCriterion("open_credit >", value, "openCredit");
            return (Criteria) this;
        }

        public Criteria andOpenCreditGreaterThanOrEqualTo(Integer value) {
            addCriterion("open_credit >=", value, "openCredit");
            return (Criteria) this;
        }

        public Criteria andOpenCreditLessThan(Integer value) {
            addCriterion("open_credit <", value, "openCredit");
            return (Criteria) this;
        }

        public Criteria andOpenCreditLessThanOrEqualTo(Integer value) {
            addCriterion("open_credit <=", value, "openCredit");
            return (Criteria) this;
        }

        public Criteria andOpenCreditIn(List<Integer> values) {
            addCriterion("open_credit in", values, "openCredit");
            return (Criteria) this;
        }

        public Criteria andOpenCreditNotIn(List<Integer> values) {
            addCriterion("open_credit not in", values, "openCredit");
            return (Criteria) this;
        }

        public Criteria andOpenCreditBetween(Integer value1, Integer value2) {
            addCriterion("open_credit between", value1, value2, "openCredit");
            return (Criteria) this;
        }

        public Criteria andOpenCreditNotBetween(Integer value1, Integer value2) {
            addCriterion("open_credit not between", value1, value2, "openCredit");
            return (Criteria) this;
        }

        public Criteria andCommentStausIsNull() {
            addCriterion("comment_staus is null");
            return (Criteria) this;
        }

        public Criteria andCommentStausIsNotNull() {
            addCriterion("comment_staus is not null");
            return (Criteria) this;
        }

        public Criteria andCommentStausEqualTo(Integer value) {
            addCriterion("comment_staus =", value, "commentStaus");
            return (Criteria) this;
        }

        public Criteria andCommentStausNotEqualTo(Integer value) {
            addCriterion("comment_staus <>", value, "commentStaus");
            return (Criteria) this;
        }

        public Criteria andCommentStausGreaterThan(Integer value) {
            addCriterion("comment_staus >", value, "commentStaus");
            return (Criteria) this;
        }

        public Criteria andCommentStausGreaterThanOrEqualTo(Integer value) {
            addCriterion("comment_staus >=", value, "commentStaus");
            return (Criteria) this;
        }

        public Criteria andCommentStausLessThan(Integer value) {
            addCriterion("comment_staus <", value, "commentStaus");
            return (Criteria) this;
        }

        public Criteria andCommentStausLessThanOrEqualTo(Integer value) {
            addCriterion("comment_staus <=", value, "commentStaus");
            return (Criteria) this;
        }

        public Criteria andCommentStausIn(List<Integer> values) {
            addCriterion("comment_staus in", values, "commentStaus");
            return (Criteria) this;
        }

        public Criteria andCommentStausNotIn(List<Integer> values) {
            addCriterion("comment_staus not in", values, "commentStaus");
            return (Criteria) this;
        }

        public Criteria andCommentStausBetween(Integer value1, Integer value2) {
            addCriterion("comment_staus between", value1, value2, "commentStaus");
            return (Criteria) this;
        }

        public Criteria andCommentStausNotBetween(Integer value1, Integer value2) {
            addCriterion("comment_staus not between", value1, value2, "commentStaus");
            return (Criteria) this;
        }

        public Criteria andCommentTimesIsNull() {
            addCriterion("comment_times is null");
            return (Criteria) this;
        }

        public Criteria andCommentTimesIsNotNull() {
            addCriterion("comment_times is not null");
            return (Criteria) this;
        }

        public Criteria andCommentTimesEqualTo(Integer value) {
            addCriterion("comment_times =", value, "commentTimes");
            return (Criteria) this;
        }

        public Criteria andCommentTimesNotEqualTo(Integer value) {
            addCriterion("comment_times <>", value, "commentTimes");
            return (Criteria) this;
        }

        public Criteria andCommentTimesGreaterThan(Integer value) {
            addCriterion("comment_times >", value, "commentTimes");
            return (Criteria) this;
        }

        public Criteria andCommentTimesGreaterThanOrEqualTo(Integer value) {
            addCriterion("comment_times >=", value, "commentTimes");
            return (Criteria) this;
        }

        public Criteria andCommentTimesLessThan(Integer value) {
            addCriterion("comment_times <", value, "commentTimes");
            return (Criteria) this;
        }

        public Criteria andCommentTimesLessThanOrEqualTo(Integer value) {
            addCriterion("comment_times <=", value, "commentTimes");
            return (Criteria) this;
        }

        public Criteria andCommentTimesIn(List<Integer> values) {
            addCriterion("comment_times in", values, "commentTimes");
            return (Criteria) this;
        }

        public Criteria andCommentTimesNotIn(List<Integer> values) {
            addCriterion("comment_times not in", values, "commentTimes");
            return (Criteria) this;
        }

        public Criteria andCommentTimesBetween(Integer value1, Integer value2) {
            addCriterion("comment_times between", value1, value2, "commentTimes");
            return (Criteria) this;
        }

        public Criteria andCommentTimesNotBetween(Integer value1, Integer value2) {
            addCriterion("comment_times not between", value1, value2, "commentTimes");
            return (Criteria) this;
        }

        public Criteria andCommentUsertypeIsNull() {
            addCriterion("comment_usertype is null");
            return (Criteria) this;
        }

        public Criteria andCommentUsertypeIsNotNull() {
            addCriterion("comment_usertype is not null");
            return (Criteria) this;
        }

        public Criteria andCommentUsertypeEqualTo(String value) {
            addCriterion("comment_usertype =", value, "commentUsertype");
            return (Criteria) this;
        }

        public Criteria andCommentUsertypeNotEqualTo(String value) {
            addCriterion("comment_usertype <>", value, "commentUsertype");
            return (Criteria) this;
        }

        public Criteria andCommentUsertypeGreaterThan(String value) {
            addCriterion("comment_usertype >", value, "commentUsertype");
            return (Criteria) this;
        }

        public Criteria andCommentUsertypeGreaterThanOrEqualTo(String value) {
            addCriterion("comment_usertype >=", value, "commentUsertype");
            return (Criteria) this;
        }

        public Criteria andCommentUsertypeLessThan(String value) {
            addCriterion("comment_usertype <", value, "commentUsertype");
            return (Criteria) this;
        }

        public Criteria andCommentUsertypeLessThanOrEqualTo(String value) {
            addCriterion("comment_usertype <=", value, "commentUsertype");
            return (Criteria) this;
        }

        public Criteria andCommentUsertypeLike(String value) {
            addCriterion("comment_usertype like", value, "commentUsertype");
            return (Criteria) this;
        }

        public Criteria andCommentUsertypeNotLike(String value) {
            addCriterion("comment_usertype not like", value, "commentUsertype");
            return (Criteria) this;
        }

        public Criteria andCommentUsertypeIn(List<String> values) {
            addCriterion("comment_usertype in", values, "commentUsertype");
            return (Criteria) this;
        }

        public Criteria andCommentUsertypeNotIn(List<String> values) {
            addCriterion("comment_usertype not in", values, "commentUsertype");
            return (Criteria) this;
        }

        public Criteria andCommentUsertypeBetween(String value1, String value2) {
            addCriterion("comment_usertype between", value1, value2, "commentUsertype");
            return (Criteria) this;
        }

        public Criteria andCommentUsertypeNotBetween(String value1, String value2) {
            addCriterion("comment_usertype not between", value1, value2, "commentUsertype");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnAppIsNull() {
            addCriterion("borrow_pawn_app is null");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnAppIsNotNull() {
            addCriterion("borrow_pawn_app is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnAppEqualTo(String value) {
            addCriterion("borrow_pawn_app =", value, "borrowPawnApp");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnAppNotEqualTo(String value) {
            addCriterion("borrow_pawn_app <>", value, "borrowPawnApp");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnAppGreaterThan(String value) {
            addCriterion("borrow_pawn_app >", value, "borrowPawnApp");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnAppGreaterThanOrEqualTo(String value) {
            addCriterion("borrow_pawn_app >=", value, "borrowPawnApp");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnAppLessThan(String value) {
            addCriterion("borrow_pawn_app <", value, "borrowPawnApp");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnAppLessThanOrEqualTo(String value) {
            addCriterion("borrow_pawn_app <=", value, "borrowPawnApp");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnAppLike(String value) {
            addCriterion("borrow_pawn_app like", value, "borrowPawnApp");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnAppNotLike(String value) {
            addCriterion("borrow_pawn_app not like", value, "borrowPawnApp");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnAppIn(List<String> values) {
            addCriterion("borrow_pawn_app in", values, "borrowPawnApp");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnAppNotIn(List<String> values) {
            addCriterion("borrow_pawn_app not in", values, "borrowPawnApp");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnAppBetween(String value1, String value2) {
            addCriterion("borrow_pawn_app between", value1, value2, "borrowPawnApp");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnAppNotBetween(String value1, String value2) {
            addCriterion("borrow_pawn_app not between", value1, value2, "borrowPawnApp");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnAppUrlIsNull() {
            addCriterion("borrow_pawn_app_url is null");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnAppUrlIsNotNull() {
            addCriterion("borrow_pawn_app_url is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnAppUrlEqualTo(String value) {
            addCriterion("borrow_pawn_app_url =", value, "borrowPawnAppUrl");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnAppUrlNotEqualTo(String value) {
            addCriterion("borrow_pawn_app_url <>", value, "borrowPawnAppUrl");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnAppUrlGreaterThan(String value) {
            addCriterion("borrow_pawn_app_url >", value, "borrowPawnAppUrl");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnAppUrlGreaterThanOrEqualTo(String value) {
            addCriterion("borrow_pawn_app_url >=", value, "borrowPawnAppUrl");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnAppUrlLessThan(String value) {
            addCriterion("borrow_pawn_app_url <", value, "borrowPawnAppUrl");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnAppUrlLessThanOrEqualTo(String value) {
            addCriterion("borrow_pawn_app_url <=", value, "borrowPawnAppUrl");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnAppUrlLike(String value) {
            addCriterion("borrow_pawn_app_url like", value, "borrowPawnAppUrl");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnAppUrlNotLike(String value) {
            addCriterion("borrow_pawn_app_url not like", value, "borrowPawnAppUrl");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnAppUrlIn(List<String> values) {
            addCriterion("borrow_pawn_app_url in", values, "borrowPawnAppUrl");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnAppUrlNotIn(List<String> values) {
            addCriterion("borrow_pawn_app_url not in", values, "borrowPawnAppUrl");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnAppUrlBetween(String value1, String value2) {
            addCriterion("borrow_pawn_app_url between", value1, value2, "borrowPawnAppUrl");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnAppUrlNotBetween(String value1, String value2) {
            addCriterion("borrow_pawn_app_url not between", value1, value2, "borrowPawnAppUrl");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnAuthIsNull() {
            addCriterion("borrow_pawn_auth is null");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnAuthIsNotNull() {
            addCriterion("borrow_pawn_auth is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnAuthEqualTo(String value) {
            addCriterion("borrow_pawn_auth =", value, "borrowPawnAuth");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnAuthNotEqualTo(String value) {
            addCriterion("borrow_pawn_auth <>", value, "borrowPawnAuth");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnAuthGreaterThan(String value) {
            addCriterion("borrow_pawn_auth >", value, "borrowPawnAuth");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnAuthGreaterThanOrEqualTo(String value) {
            addCriterion("borrow_pawn_auth >=", value, "borrowPawnAuth");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnAuthLessThan(String value) {
            addCriterion("borrow_pawn_auth <", value, "borrowPawnAuth");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnAuthLessThanOrEqualTo(String value) {
            addCriterion("borrow_pawn_auth <=", value, "borrowPawnAuth");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnAuthLike(String value) {
            addCriterion("borrow_pawn_auth like", value, "borrowPawnAuth");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnAuthNotLike(String value) {
            addCriterion("borrow_pawn_auth not like", value, "borrowPawnAuth");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnAuthIn(List<String> values) {
            addCriterion("borrow_pawn_auth in", values, "borrowPawnAuth");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnAuthNotIn(List<String> values) {
            addCriterion("borrow_pawn_auth not in", values, "borrowPawnAuth");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnAuthBetween(String value1, String value2) {
            addCriterion("borrow_pawn_auth between", value1, value2, "borrowPawnAuth");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnAuthNotBetween(String value1, String value2) {
            addCriterion("borrow_pawn_auth not between", value1, value2, "borrowPawnAuth");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnAuthUrlIsNull() {
            addCriterion("borrow_pawn_auth_url is null");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnAuthUrlIsNotNull() {
            addCriterion("borrow_pawn_auth_url is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnAuthUrlEqualTo(String value) {
            addCriterion("borrow_pawn_auth_url =", value, "borrowPawnAuthUrl");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnAuthUrlNotEqualTo(String value) {
            addCriterion("borrow_pawn_auth_url <>", value, "borrowPawnAuthUrl");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnAuthUrlGreaterThan(String value) {
            addCriterion("borrow_pawn_auth_url >", value, "borrowPawnAuthUrl");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnAuthUrlGreaterThanOrEqualTo(String value) {
            addCriterion("borrow_pawn_auth_url >=", value, "borrowPawnAuthUrl");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnAuthUrlLessThan(String value) {
            addCriterion("borrow_pawn_auth_url <", value, "borrowPawnAuthUrl");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnAuthUrlLessThanOrEqualTo(String value) {
            addCriterion("borrow_pawn_auth_url <=", value, "borrowPawnAuthUrl");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnAuthUrlLike(String value) {
            addCriterion("borrow_pawn_auth_url like", value, "borrowPawnAuthUrl");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnAuthUrlNotLike(String value) {
            addCriterion("borrow_pawn_auth_url not like", value, "borrowPawnAuthUrl");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnAuthUrlIn(List<String> values) {
            addCriterion("borrow_pawn_auth_url in", values, "borrowPawnAuthUrl");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnAuthUrlNotIn(List<String> values) {
            addCriterion("borrow_pawn_auth_url not in", values, "borrowPawnAuthUrl");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnAuthUrlBetween(String value1, String value2) {
            addCriterion("borrow_pawn_auth_url between", value1, value2, "borrowPawnAuthUrl");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnAuthUrlNotBetween(String value1, String value2) {
            addCriterion("borrow_pawn_auth_url not between", value1, value2, "borrowPawnAuthUrl");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnFormalitiesIsNull() {
            addCriterion("borrow_pawn_formalities is null");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnFormalitiesIsNotNull() {
            addCriterion("borrow_pawn_formalities is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnFormalitiesEqualTo(String value) {
            addCriterion("borrow_pawn_formalities =", value, "borrowPawnFormalities");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnFormalitiesNotEqualTo(String value) {
            addCriterion("borrow_pawn_formalities <>", value, "borrowPawnFormalities");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnFormalitiesGreaterThan(String value) {
            addCriterion("borrow_pawn_formalities >", value, "borrowPawnFormalities");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnFormalitiesGreaterThanOrEqualTo(String value) {
            addCriterion("borrow_pawn_formalities >=", value, "borrowPawnFormalities");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnFormalitiesLessThan(String value) {
            addCriterion("borrow_pawn_formalities <", value, "borrowPawnFormalities");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnFormalitiesLessThanOrEqualTo(String value) {
            addCriterion("borrow_pawn_formalities <=", value, "borrowPawnFormalities");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnFormalitiesLike(String value) {
            addCriterion("borrow_pawn_formalities like", value, "borrowPawnFormalities");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnFormalitiesNotLike(String value) {
            addCriterion("borrow_pawn_formalities not like", value, "borrowPawnFormalities");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnFormalitiesIn(List<String> values) {
            addCriterion("borrow_pawn_formalities in", values, "borrowPawnFormalities");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnFormalitiesNotIn(List<String> values) {
            addCriterion("borrow_pawn_formalities not in", values, "borrowPawnFormalities");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnFormalitiesBetween(String value1, String value2) {
            addCriterion("borrow_pawn_formalities between", value1, value2, "borrowPawnFormalities");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnFormalitiesNotBetween(String value1, String value2) {
            addCriterion("borrow_pawn_formalities not between", value1, value2, "borrowPawnFormalities");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnFormalitiesUrlIsNull() {
            addCriterion("borrow_pawn_formalities_url is null");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnFormalitiesUrlIsNotNull() {
            addCriterion("borrow_pawn_formalities_url is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnFormalitiesUrlEqualTo(String value) {
            addCriterion("borrow_pawn_formalities_url =", value, "borrowPawnFormalitiesUrl");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnFormalitiesUrlNotEqualTo(String value) {
            addCriterion("borrow_pawn_formalities_url <>", value, "borrowPawnFormalitiesUrl");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnFormalitiesUrlGreaterThan(String value) {
            addCriterion("borrow_pawn_formalities_url >", value, "borrowPawnFormalitiesUrl");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnFormalitiesUrlGreaterThanOrEqualTo(String value) {
            addCriterion("borrow_pawn_formalities_url >=", value, "borrowPawnFormalitiesUrl");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnFormalitiesUrlLessThan(String value) {
            addCriterion("borrow_pawn_formalities_url <", value, "borrowPawnFormalitiesUrl");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnFormalitiesUrlLessThanOrEqualTo(String value) {
            addCriterion("borrow_pawn_formalities_url <=", value, "borrowPawnFormalitiesUrl");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnFormalitiesUrlLike(String value) {
            addCriterion("borrow_pawn_formalities_url like", value, "borrowPawnFormalitiesUrl");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnFormalitiesUrlNotLike(String value) {
            addCriterion("borrow_pawn_formalities_url not like", value, "borrowPawnFormalitiesUrl");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnFormalitiesUrlIn(List<String> values) {
            addCriterion("borrow_pawn_formalities_url in", values, "borrowPawnFormalitiesUrl");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnFormalitiesUrlNotIn(List<String> values) {
            addCriterion("borrow_pawn_formalities_url not in", values, "borrowPawnFormalitiesUrl");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnFormalitiesUrlBetween(String value1, String value2) {
            addCriterion("borrow_pawn_formalities_url between", value1, value2, "borrowPawnFormalitiesUrl");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnFormalitiesUrlNotBetween(String value1, String value2) {
            addCriterion("borrow_pawn_formalities_url not between", value1, value2, "borrowPawnFormalitiesUrl");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnTypeIsNull() {
            addCriterion("borrow_pawn_type is null");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnTypeIsNotNull() {
            addCriterion("borrow_pawn_type is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnTypeEqualTo(String value) {
            addCriterion("borrow_pawn_type =", value, "borrowPawnType");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnTypeNotEqualTo(String value) {
            addCriterion("borrow_pawn_type <>", value, "borrowPawnType");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnTypeGreaterThan(String value) {
            addCriterion("borrow_pawn_type >", value, "borrowPawnType");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnTypeGreaterThanOrEqualTo(String value) {
            addCriterion("borrow_pawn_type >=", value, "borrowPawnType");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnTypeLessThan(String value) {
            addCriterion("borrow_pawn_type <", value, "borrowPawnType");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnTypeLessThanOrEqualTo(String value) {
            addCriterion("borrow_pawn_type <=", value, "borrowPawnType");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnTypeLike(String value) {
            addCriterion("borrow_pawn_type like", value, "borrowPawnType");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnTypeNotLike(String value) {
            addCriterion("borrow_pawn_type not like", value, "borrowPawnType");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnTypeIn(List<String> values) {
            addCriterion("borrow_pawn_type in", values, "borrowPawnType");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnTypeNotIn(List<String> values) {
            addCriterion("borrow_pawn_type not in", values, "borrowPawnType");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnTypeBetween(String value1, String value2) {
            addCriterion("borrow_pawn_type between", value1, value2, "borrowPawnType");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnTypeNotBetween(String value1, String value2) {
            addCriterion("borrow_pawn_type not between", value1, value2, "borrowPawnType");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnTimeIsNull() {
            addCriterion("borrow_pawn_time is null");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnTimeIsNotNull() {
            addCriterion("borrow_pawn_time is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnTimeEqualTo(String value) {
            addCriterion("borrow_pawn_time =", value, "borrowPawnTime");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnTimeNotEqualTo(String value) {
            addCriterion("borrow_pawn_time <>", value, "borrowPawnTime");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnTimeGreaterThan(String value) {
            addCriterion("borrow_pawn_time >", value, "borrowPawnTime");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnTimeGreaterThanOrEqualTo(String value) {
            addCriterion("borrow_pawn_time >=", value, "borrowPawnTime");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnTimeLessThan(String value) {
            addCriterion("borrow_pawn_time <", value, "borrowPawnTime");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnTimeLessThanOrEqualTo(String value) {
            addCriterion("borrow_pawn_time <=", value, "borrowPawnTime");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnTimeLike(String value) {
            addCriterion("borrow_pawn_time like", value, "borrowPawnTime");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnTimeNotLike(String value) {
            addCriterion("borrow_pawn_time not like", value, "borrowPawnTime");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnTimeIn(List<String> values) {
            addCriterion("borrow_pawn_time in", values, "borrowPawnTime");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnTimeNotIn(List<String> values) {
            addCriterion("borrow_pawn_time not in", values, "borrowPawnTime");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnTimeBetween(String value1, String value2) {
            addCriterion("borrow_pawn_time between", value1, value2, "borrowPawnTime");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnTimeNotBetween(String value1, String value2) {
            addCriterion("borrow_pawn_time not between", value1, value2, "borrowPawnTime");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnValueIsNull() {
            addCriterion("borrow_pawn_value is null");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnValueIsNotNull() {
            addCriterion("borrow_pawn_value is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnValueEqualTo(String value) {
            addCriterion("borrow_pawn_value =", value, "borrowPawnValue");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnValueNotEqualTo(String value) {
            addCriterion("borrow_pawn_value <>", value, "borrowPawnValue");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnValueGreaterThan(String value) {
            addCriterion("borrow_pawn_value >", value, "borrowPawnValue");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnValueGreaterThanOrEqualTo(String value) {
            addCriterion("borrow_pawn_value >=", value, "borrowPawnValue");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnValueLessThan(String value) {
            addCriterion("borrow_pawn_value <", value, "borrowPawnValue");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnValueLessThanOrEqualTo(String value) {
            addCriterion("borrow_pawn_value <=", value, "borrowPawnValue");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnValueLike(String value) {
            addCriterion("borrow_pawn_value like", value, "borrowPawnValue");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnValueNotLike(String value) {
            addCriterion("borrow_pawn_value not like", value, "borrowPawnValue");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnValueIn(List<String> values) {
            addCriterion("borrow_pawn_value in", values, "borrowPawnValue");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnValueNotIn(List<String> values) {
            addCriterion("borrow_pawn_value not in", values, "borrowPawnValue");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnValueBetween(String value1, String value2) {
            addCriterion("borrow_pawn_value between", value1, value2, "borrowPawnValue");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnValueNotBetween(String value1, String value2) {
            addCriterion("borrow_pawn_value not between", value1, value2, "borrowPawnValue");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnXinIsNull() {
            addCriterion("borrow_pawn_xin is null");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnXinIsNotNull() {
            addCriterion("borrow_pawn_xin is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnXinEqualTo(String value) {
            addCriterion("borrow_pawn_xin =", value, "borrowPawnXin");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnXinNotEqualTo(String value) {
            addCriterion("borrow_pawn_xin <>", value, "borrowPawnXin");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnXinGreaterThan(String value) {
            addCriterion("borrow_pawn_xin >", value, "borrowPawnXin");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnXinGreaterThanOrEqualTo(String value) {
            addCriterion("borrow_pawn_xin >=", value, "borrowPawnXin");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnXinLessThan(String value) {
            addCriterion("borrow_pawn_xin <", value, "borrowPawnXin");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnXinLessThanOrEqualTo(String value) {
            addCriterion("borrow_pawn_xin <=", value, "borrowPawnXin");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnXinLike(String value) {
            addCriterion("borrow_pawn_xin like", value, "borrowPawnXin");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnXinNotLike(String value) {
            addCriterion("borrow_pawn_xin not like", value, "borrowPawnXin");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnXinIn(List<String> values) {
            addCriterion("borrow_pawn_xin in", values, "borrowPawnXin");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnXinNotIn(List<String> values) {
            addCriterion("borrow_pawn_xin not in", values, "borrowPawnXin");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnXinBetween(String value1, String value2) {
            addCriterion("borrow_pawn_xin between", value1, value2, "borrowPawnXin");
            return (Criteria) this;
        }

        public Criteria andBorrowPawnXinNotBetween(String value1, String value2) {
            addCriterion("borrow_pawn_xin not between", value1, value2, "borrowPawnXin");
            return (Criteria) this;
        }

        public Criteria andOrderTopIsNull() {
            addCriterion("order_top is null");
            return (Criteria) this;
        }

        public Criteria andOrderTopIsNotNull() {
            addCriterion("order_top is not null");
            return (Criteria) this;
        }

        public Criteria andOrderTopEqualTo(String value) {
            addCriterion("order_top =", value, "orderTop");
            return (Criteria) this;
        }

        public Criteria andOrderTopNotEqualTo(String value) {
            addCriterion("order_top <>", value, "orderTop");
            return (Criteria) this;
        }

        public Criteria andOrderTopGreaterThan(String value) {
            addCriterion("order_top >", value, "orderTop");
            return (Criteria) this;
        }

        public Criteria andOrderTopGreaterThanOrEqualTo(String value) {
            addCriterion("order_top >=", value, "orderTop");
            return (Criteria) this;
        }

        public Criteria andOrderTopLessThan(String value) {
            addCriterion("order_top <", value, "orderTop");
            return (Criteria) this;
        }

        public Criteria andOrderTopLessThanOrEqualTo(String value) {
            addCriterion("order_top <=", value, "orderTop");
            return (Criteria) this;
        }

        public Criteria andOrderTopLike(String value) {
            addCriterion("order_top like", value, "orderTop");
            return (Criteria) this;
        }

        public Criteria andOrderTopNotLike(String value) {
            addCriterion("order_top not like", value, "orderTop");
            return (Criteria) this;
        }

        public Criteria andOrderTopIn(List<String> values) {
            addCriterion("order_top in", values, "orderTop");
            return (Criteria) this;
        }

        public Criteria andOrderTopNotIn(List<String> values) {
            addCriterion("order_top not in", values, "orderTop");
            return (Criteria) this;
        }

        public Criteria andOrderTopBetween(String value1, String value2) {
            addCriterion("order_top between", value1, value2, "orderTop");
            return (Criteria) this;
        }

        public Criteria andOrderTopNotBetween(String value1, String value2) {
            addCriterion("order_top not between", value1, value2, "orderTop");
            return (Criteria) this;
        }

        public Criteria andVerifyUseridIsNull() {
            addCriterion("verify_userid is null");
            return (Criteria) this;
        }

        public Criteria andVerifyUseridIsNotNull() {
            addCriterion("verify_userid is not null");
            return (Criteria) this;
        }

        public Criteria andVerifyUseridEqualTo(String value) {
            addCriterion("verify_userid =", value, "verifyUserid");
            return (Criteria) this;
        }

        public Criteria andVerifyUseridNotEqualTo(String value) {
            addCriterion("verify_userid <>", value, "verifyUserid");
            return (Criteria) this;
        }

        public Criteria andVerifyUseridGreaterThan(String value) {
            addCriterion("verify_userid >", value, "verifyUserid");
            return (Criteria) this;
        }

        public Criteria andVerifyUseridGreaterThanOrEqualTo(String value) {
            addCriterion("verify_userid >=", value, "verifyUserid");
            return (Criteria) this;
        }

        public Criteria andVerifyUseridLessThan(String value) {
            addCriterion("verify_userid <", value, "verifyUserid");
            return (Criteria) this;
        }

        public Criteria andVerifyUseridLessThanOrEqualTo(String value) {
            addCriterion("verify_userid <=", value, "verifyUserid");
            return (Criteria) this;
        }

        public Criteria andVerifyUseridLike(String value) {
            addCriterion("verify_userid like", value, "verifyUserid");
            return (Criteria) this;
        }

        public Criteria andVerifyUseridNotLike(String value) {
            addCriterion("verify_userid not like", value, "verifyUserid");
            return (Criteria) this;
        }

        public Criteria andVerifyUseridIn(List<String> values) {
            addCriterion("verify_userid in", values, "verifyUserid");
            return (Criteria) this;
        }

        public Criteria andVerifyUseridNotIn(List<String> values) {
            addCriterion("verify_userid not in", values, "verifyUserid");
            return (Criteria) this;
        }

        public Criteria andVerifyUseridBetween(String value1, String value2) {
            addCriterion("verify_userid between", value1, value2, "verifyUserid");
            return (Criteria) this;
        }

        public Criteria andVerifyUseridNotBetween(String value1, String value2) {
            addCriterion("verify_userid not between", value1, value2, "verifyUserid");
            return (Criteria) this;
        }

        public Criteria andVerifyTimeIsNull() {
            addCriterion("verify_time is null");
            return (Criteria) this;
        }

        public Criteria andVerifyTimeIsNotNull() {
            addCriterion("verify_time is not null");
            return (Criteria) this;
        }

        public Criteria andVerifyTimeEqualTo(String value) {
            addCriterion("verify_time =", value, "verifyTime");
            return (Criteria) this;
        }

        public Criteria andVerifyTimeNotEqualTo(String value) {
            addCriterion("verify_time <>", value, "verifyTime");
            return (Criteria) this;
        }

        public Criteria andVerifyTimeGreaterThan(String value) {
            addCriterion("verify_time >", value, "verifyTime");
            return (Criteria) this;
        }

        public Criteria andVerifyTimeGreaterThanOrEqualTo(String value) {
            addCriterion("verify_time >=", value, "verifyTime");
            return (Criteria) this;
        }

        public Criteria andVerifyTimeLessThan(String value) {
            addCriterion("verify_time <", value, "verifyTime");
            return (Criteria) this;
        }

        public Criteria andVerifyTimeLessThanOrEqualTo(String value) {
            addCriterion("verify_time <=", value, "verifyTime");
            return (Criteria) this;
        }

        public Criteria andVerifyTimeLike(String value) {
            addCriterion("verify_time like", value, "verifyTime");
            return (Criteria) this;
        }

        public Criteria andVerifyTimeNotLike(String value) {
            addCriterion("verify_time not like", value, "verifyTime");
            return (Criteria) this;
        }

        public Criteria andVerifyTimeIn(List<String> values) {
            addCriterion("verify_time in", values, "verifyTime");
            return (Criteria) this;
        }

        public Criteria andVerifyTimeNotIn(List<String> values) {
            addCriterion("verify_time not in", values, "verifyTime");
            return (Criteria) this;
        }

        public Criteria andVerifyTimeBetween(String value1, String value2) {
            addCriterion("verify_time between", value1, value2, "verifyTime");
            return (Criteria) this;
        }

        public Criteria andVerifyTimeNotBetween(String value1, String value2) {
            addCriterion("verify_time not between", value1, value2, "verifyTime");
            return (Criteria) this;
        }

        public Criteria andVerifyRemarkIsNull() {
            addCriterion("verify_remark is null");
            return (Criteria) this;
        }

        public Criteria andVerifyRemarkIsNotNull() {
            addCriterion("verify_remark is not null");
            return (Criteria) this;
        }

        public Criteria andVerifyRemarkEqualTo(String value) {
            addCriterion("verify_remark =", value, "verifyRemark");
            return (Criteria) this;
        }

        public Criteria andVerifyRemarkNotEqualTo(String value) {
            addCriterion("verify_remark <>", value, "verifyRemark");
            return (Criteria) this;
        }

        public Criteria andVerifyRemarkGreaterThan(String value) {
            addCriterion("verify_remark >", value, "verifyRemark");
            return (Criteria) this;
        }

        public Criteria andVerifyRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("verify_remark >=", value, "verifyRemark");
            return (Criteria) this;
        }

        public Criteria andVerifyRemarkLessThan(String value) {
            addCriterion("verify_remark <", value, "verifyRemark");
            return (Criteria) this;
        }

        public Criteria andVerifyRemarkLessThanOrEqualTo(String value) {
            addCriterion("verify_remark <=", value, "verifyRemark");
            return (Criteria) this;
        }

        public Criteria andVerifyRemarkLike(String value) {
            addCriterion("verify_remark like", value, "verifyRemark");
            return (Criteria) this;
        }

        public Criteria andVerifyRemarkNotLike(String value) {
            addCriterion("verify_remark not like", value, "verifyRemark");
            return (Criteria) this;
        }

        public Criteria andVerifyRemarkIn(List<String> values) {
            addCriterion("verify_remark in", values, "verifyRemark");
            return (Criteria) this;
        }

        public Criteria andVerifyRemarkNotIn(List<String> values) {
            addCriterion("verify_remark not in", values, "verifyRemark");
            return (Criteria) this;
        }

        public Criteria andVerifyRemarkBetween(String value1, String value2) {
            addCriterion("verify_remark between", value1, value2, "verifyRemark");
            return (Criteria) this;
        }

        public Criteria andVerifyRemarkNotBetween(String value1, String value2) {
            addCriterion("verify_remark not between", value1, value2, "verifyRemark");
            return (Criteria) this;
        }

        public Criteria andVerifyContentsIsNull() {
            addCriterion("verify_contents is null");
            return (Criteria) this;
        }

        public Criteria andVerifyContentsIsNotNull() {
            addCriterion("verify_contents is not null");
            return (Criteria) this;
        }

        public Criteria andVerifyContentsEqualTo(String value) {
            addCriterion("verify_contents =", value, "verifyContents");
            return (Criteria) this;
        }

        public Criteria andVerifyContentsNotEqualTo(String value) {
            addCriterion("verify_contents <>", value, "verifyContents");
            return (Criteria) this;
        }

        public Criteria andVerifyContentsGreaterThan(String value) {
            addCriterion("verify_contents >", value, "verifyContents");
            return (Criteria) this;
        }

        public Criteria andVerifyContentsGreaterThanOrEqualTo(String value) {
            addCriterion("verify_contents >=", value, "verifyContents");
            return (Criteria) this;
        }

        public Criteria andVerifyContentsLessThan(String value) {
            addCriterion("verify_contents <", value, "verifyContents");
            return (Criteria) this;
        }

        public Criteria andVerifyContentsLessThanOrEqualTo(String value) {
            addCriterion("verify_contents <=", value, "verifyContents");
            return (Criteria) this;
        }

        public Criteria andVerifyContentsLike(String value) {
            addCriterion("verify_contents like", value, "verifyContents");
            return (Criteria) this;
        }

        public Criteria andVerifyContentsNotLike(String value) {
            addCriterion("verify_contents not like", value, "verifyContents");
            return (Criteria) this;
        }

        public Criteria andVerifyContentsIn(List<String> values) {
            addCriterion("verify_contents in", values, "verifyContents");
            return (Criteria) this;
        }

        public Criteria andVerifyContentsNotIn(List<String> values) {
            addCriterion("verify_contents not in", values, "verifyContents");
            return (Criteria) this;
        }

        public Criteria andVerifyContentsBetween(String value1, String value2) {
            addCriterion("verify_contents between", value1, value2, "verifyContents");
            return (Criteria) this;
        }

        public Criteria andVerifyContentsNotBetween(String value1, String value2) {
            addCriterion("verify_contents not between", value1, value2, "verifyContents");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusIsNull() {
            addCriterion("verify_status is null");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusIsNotNull() {
            addCriterion("verify_status is not null");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusEqualTo(Integer value) {
            addCriterion("verify_status =", value, "verifyStatus");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusNotEqualTo(Integer value) {
            addCriterion("verify_status <>", value, "verifyStatus");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusGreaterThan(Integer value) {
            addCriterion("verify_status >", value, "verifyStatus");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("verify_status >=", value, "verifyStatus");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusLessThan(Integer value) {
            addCriterion("verify_status <", value, "verifyStatus");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusLessThanOrEqualTo(Integer value) {
            addCriterion("verify_status <=", value, "verifyStatus");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusIn(List<Integer> values) {
            addCriterion("verify_status in", values, "verifyStatus");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusNotIn(List<Integer> values) {
            addCriterion("verify_status not in", values, "verifyStatus");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusBetween(Integer value1, Integer value2) {
            addCriterion("verify_status between", value1, value2, "verifyStatus");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("verify_status not between", value1, value2, "verifyStatus");
            return (Criteria) this;
        }

        public Criteria andReverifyUseridIsNull() {
            addCriterion("reverify_userid is null");
            return (Criteria) this;
        }

        public Criteria andReverifyUseridIsNotNull() {
            addCriterion("reverify_userid is not null");
            return (Criteria) this;
        }

        public Criteria andReverifyUseridEqualTo(String value) {
            addCriterion("reverify_userid =", value, "reverifyUserid");
            return (Criteria) this;
        }

        public Criteria andReverifyUseridNotEqualTo(String value) {
            addCriterion("reverify_userid <>", value, "reverifyUserid");
            return (Criteria) this;
        }

        public Criteria andReverifyUseridGreaterThan(String value) {
            addCriterion("reverify_userid >", value, "reverifyUserid");
            return (Criteria) this;
        }

        public Criteria andReverifyUseridGreaterThanOrEqualTo(String value) {
            addCriterion("reverify_userid >=", value, "reverifyUserid");
            return (Criteria) this;
        }

        public Criteria andReverifyUseridLessThan(String value) {
            addCriterion("reverify_userid <", value, "reverifyUserid");
            return (Criteria) this;
        }

        public Criteria andReverifyUseridLessThanOrEqualTo(String value) {
            addCriterion("reverify_userid <=", value, "reverifyUserid");
            return (Criteria) this;
        }

        public Criteria andReverifyUseridLike(String value) {
            addCriterion("reverify_userid like", value, "reverifyUserid");
            return (Criteria) this;
        }

        public Criteria andReverifyUseridNotLike(String value) {
            addCriterion("reverify_userid not like", value, "reverifyUserid");
            return (Criteria) this;
        }

        public Criteria andReverifyUseridIn(List<String> values) {
            addCriterion("reverify_userid in", values, "reverifyUserid");
            return (Criteria) this;
        }

        public Criteria andReverifyUseridNotIn(List<String> values) {
            addCriterion("reverify_userid not in", values, "reverifyUserid");
            return (Criteria) this;
        }

        public Criteria andReverifyUseridBetween(String value1, String value2) {
            addCriterion("reverify_userid between", value1, value2, "reverifyUserid");
            return (Criteria) this;
        }

        public Criteria andReverifyUseridNotBetween(String value1, String value2) {
            addCriterion("reverify_userid not between", value1, value2, "reverifyUserid");
            return (Criteria) this;
        }

        public Criteria andReverifyTimeIsNull() {
            addCriterion("reverify_time is null");
            return (Criteria) this;
        }

        public Criteria andReverifyTimeIsNotNull() {
            addCriterion("reverify_time is not null");
            return (Criteria) this;
        }

        public Criteria andReverifyTimeEqualTo(String value) {
            addCriterion("reverify_time =", value, "reverifyTime");
            return (Criteria) this;
        }

        public Criteria andReverifyTimeNotEqualTo(String value) {
            addCriterion("reverify_time <>", value, "reverifyTime");
            return (Criteria) this;
        }

        public Criteria andReverifyTimeGreaterThan(String value) {
            addCriterion("reverify_time >", value, "reverifyTime");
            return (Criteria) this;
        }

        public Criteria andReverifyTimeGreaterThanOrEqualTo(String value) {
            addCriterion("reverify_time >=", value, "reverifyTime");
            return (Criteria) this;
        }

        public Criteria andReverifyTimeLessThan(String value) {
            addCriterion("reverify_time <", value, "reverifyTime");
            return (Criteria) this;
        }

        public Criteria andReverifyTimeLessThanOrEqualTo(String value) {
            addCriterion("reverify_time <=", value, "reverifyTime");
            return (Criteria) this;
        }

        public Criteria andReverifyTimeLike(String value) {
            addCriterion("reverify_time like", value, "reverifyTime");
            return (Criteria) this;
        }

        public Criteria andReverifyTimeNotLike(String value) {
            addCriterion("reverify_time not like", value, "reverifyTime");
            return (Criteria) this;
        }

        public Criteria andReverifyTimeIn(List<String> values) {
            addCriterion("reverify_time in", values, "reverifyTime");
            return (Criteria) this;
        }

        public Criteria andReverifyTimeNotIn(List<String> values) {
            addCriterion("reverify_time not in", values, "reverifyTime");
            return (Criteria) this;
        }

        public Criteria andReverifyTimeBetween(String value1, String value2) {
            addCriterion("reverify_time between", value1, value2, "reverifyTime");
            return (Criteria) this;
        }

        public Criteria andReverifyTimeNotBetween(String value1, String value2) {
            addCriterion("reverify_time not between", value1, value2, "reverifyTime");
            return (Criteria) this;
        }

        public Criteria andReverifyRemarkIsNull() {
            addCriterion("reverify_remark is null");
            return (Criteria) this;
        }

        public Criteria andReverifyRemarkIsNotNull() {
            addCriterion("reverify_remark is not null");
            return (Criteria) this;
        }

        public Criteria andReverifyRemarkEqualTo(String value) {
            addCriterion("reverify_remark =", value, "reverifyRemark");
            return (Criteria) this;
        }

        public Criteria andReverifyRemarkNotEqualTo(String value) {
            addCriterion("reverify_remark <>", value, "reverifyRemark");
            return (Criteria) this;
        }

        public Criteria andReverifyRemarkGreaterThan(String value) {
            addCriterion("reverify_remark >", value, "reverifyRemark");
            return (Criteria) this;
        }

        public Criteria andReverifyRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("reverify_remark >=", value, "reverifyRemark");
            return (Criteria) this;
        }

        public Criteria andReverifyRemarkLessThan(String value) {
            addCriterion("reverify_remark <", value, "reverifyRemark");
            return (Criteria) this;
        }

        public Criteria andReverifyRemarkLessThanOrEqualTo(String value) {
            addCriterion("reverify_remark <=", value, "reverifyRemark");
            return (Criteria) this;
        }

        public Criteria andReverifyRemarkLike(String value) {
            addCriterion("reverify_remark like", value, "reverifyRemark");
            return (Criteria) this;
        }

        public Criteria andReverifyRemarkNotLike(String value) {
            addCriterion("reverify_remark not like", value, "reverifyRemark");
            return (Criteria) this;
        }

        public Criteria andReverifyRemarkIn(List<String> values) {
            addCriterion("reverify_remark in", values, "reverifyRemark");
            return (Criteria) this;
        }

        public Criteria andReverifyRemarkNotIn(List<String> values) {
            addCriterion("reverify_remark not in", values, "reverifyRemark");
            return (Criteria) this;
        }

        public Criteria andReverifyRemarkBetween(String value1, String value2) {
            addCriterion("reverify_remark between", value1, value2, "reverifyRemark");
            return (Criteria) this;
        }

        public Criteria andReverifyRemarkNotBetween(String value1, String value2) {
            addCriterion("reverify_remark not between", value1, value2, "reverifyRemark");
            return (Criteria) this;
        }

        public Criteria andReverifyStatusIsNull() {
            addCriterion("reverify_status is null");
            return (Criteria) this;
        }

        public Criteria andReverifyStatusIsNotNull() {
            addCriterion("reverify_status is not null");
            return (Criteria) this;
        }

        public Criteria andReverifyStatusEqualTo(Integer value) {
            addCriterion("reverify_status =", value, "reverifyStatus");
            return (Criteria) this;
        }

        public Criteria andReverifyStatusNotEqualTo(Integer value) {
            addCriterion("reverify_status <>", value, "reverifyStatus");
            return (Criteria) this;
        }

        public Criteria andReverifyStatusGreaterThan(Integer value) {
            addCriterion("reverify_status >", value, "reverifyStatus");
            return (Criteria) this;
        }

        public Criteria andReverifyStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("reverify_status >=", value, "reverifyStatus");
            return (Criteria) this;
        }

        public Criteria andReverifyStatusLessThan(Integer value) {
            addCriterion("reverify_status <", value, "reverifyStatus");
            return (Criteria) this;
        }

        public Criteria andReverifyStatusLessThanOrEqualTo(Integer value) {
            addCriterion("reverify_status <=", value, "reverifyStatus");
            return (Criteria) this;
        }

        public Criteria andReverifyStatusIn(List<Integer> values) {
            addCriterion("reverify_status in", values, "reverifyStatus");
            return (Criteria) this;
        }

        public Criteria andReverifyStatusNotIn(List<Integer> values) {
            addCriterion("reverify_status not in", values, "reverifyStatus");
            return (Criteria) this;
        }

        public Criteria andReverifyStatusBetween(Integer value1, Integer value2) {
            addCriterion("reverify_status between", value1, value2, "reverifyStatus");
            return (Criteria) this;
        }

        public Criteria andReverifyStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("reverify_status not between", value1, value2, "reverifyStatus");
            return (Criteria) this;
        }

        public Criteria andReverifyContentsIsNull() {
            addCriterion("reverify_contents is null");
            return (Criteria) this;
        }

        public Criteria andReverifyContentsIsNotNull() {
            addCriterion("reverify_contents is not null");
            return (Criteria) this;
        }

        public Criteria andReverifyContentsEqualTo(String value) {
            addCriterion("reverify_contents =", value, "reverifyContents");
            return (Criteria) this;
        }

        public Criteria andReverifyContentsNotEqualTo(String value) {
            addCriterion("reverify_contents <>", value, "reverifyContents");
            return (Criteria) this;
        }

        public Criteria andReverifyContentsGreaterThan(String value) {
            addCriterion("reverify_contents >", value, "reverifyContents");
            return (Criteria) this;
        }

        public Criteria andReverifyContentsGreaterThanOrEqualTo(String value) {
            addCriterion("reverify_contents >=", value, "reverifyContents");
            return (Criteria) this;
        }

        public Criteria andReverifyContentsLessThan(String value) {
            addCriterion("reverify_contents <", value, "reverifyContents");
            return (Criteria) this;
        }

        public Criteria andReverifyContentsLessThanOrEqualTo(String value) {
            addCriterion("reverify_contents <=", value, "reverifyContents");
            return (Criteria) this;
        }

        public Criteria andReverifyContentsLike(String value) {
            addCriterion("reverify_contents like", value, "reverifyContents");
            return (Criteria) this;
        }

        public Criteria andReverifyContentsNotLike(String value) {
            addCriterion("reverify_contents not like", value, "reverifyContents");
            return (Criteria) this;
        }

        public Criteria andReverifyContentsIn(List<String> values) {
            addCriterion("reverify_contents in", values, "reverifyContents");
            return (Criteria) this;
        }

        public Criteria andReverifyContentsNotIn(List<String> values) {
            addCriterion("reverify_contents not in", values, "reverifyContents");
            return (Criteria) this;
        }

        public Criteria andReverifyContentsBetween(String value1, String value2) {
            addCriterion("reverify_contents between", value1, value2, "reverifyContents");
            return (Criteria) this;
        }

        public Criteria andReverifyContentsNotBetween(String value1, String value2) {
            addCriterion("reverify_contents not between", value1, value2, "reverifyContents");
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

        public Criteria andXmupfilesIdIsNull() {
            addCriterion("xmupfiles_id is null");
            return (Criteria) this;
        }

        public Criteria andXmupfilesIdIsNotNull() {
            addCriterion("xmupfiles_id is not null");
            return (Criteria) this;
        }

        public Criteria andXmupfilesIdEqualTo(String value) {
            addCriterion("xmupfiles_id =", value, "xmupfilesId");
            return (Criteria) this;
        }

        public Criteria andXmupfilesIdNotEqualTo(String value) {
            addCriterion("xmupfiles_id <>", value, "xmupfilesId");
            return (Criteria) this;
        }

        public Criteria andXmupfilesIdGreaterThan(String value) {
            addCriterion("xmupfiles_id >", value, "xmupfilesId");
            return (Criteria) this;
        }

        public Criteria andXmupfilesIdGreaterThanOrEqualTo(String value) {
            addCriterion("xmupfiles_id >=", value, "xmupfilesId");
            return (Criteria) this;
        }

        public Criteria andXmupfilesIdLessThan(String value) {
            addCriterion("xmupfiles_id <", value, "xmupfilesId");
            return (Criteria) this;
        }

        public Criteria andXmupfilesIdLessThanOrEqualTo(String value) {
            addCriterion("xmupfiles_id <=", value, "xmupfilesId");
            return (Criteria) this;
        }

        public Criteria andXmupfilesIdLike(String value) {
            addCriterion("xmupfiles_id like", value, "xmupfilesId");
            return (Criteria) this;
        }

        public Criteria andXmupfilesIdNotLike(String value) {
            addCriterion("xmupfiles_id not like", value, "xmupfilesId");
            return (Criteria) this;
        }

        public Criteria andXmupfilesIdIn(List<String> values) {
            addCriterion("xmupfiles_id in", values, "xmupfilesId");
            return (Criteria) this;
        }

        public Criteria andXmupfilesIdNotIn(List<String> values) {
            addCriterion("xmupfiles_id not in", values, "xmupfilesId");
            return (Criteria) this;
        }

        public Criteria andXmupfilesIdBetween(String value1, String value2) {
            addCriterion("xmupfiles_id between", value1, value2, "xmupfilesId");
            return (Criteria) this;
        }

        public Criteria andXmupfilesIdNotBetween(String value1, String value2) {
            addCriterion("xmupfiles_id not between", value1, value2, "xmupfilesId");
            return (Criteria) this;
        }

        public Criteria andDyupfilesIdIsNull() {
            addCriterion("dyupfiles_id is null");
            return (Criteria) this;
        }

        public Criteria andDyupfilesIdIsNotNull() {
            addCriterion("dyupfiles_id is not null");
            return (Criteria) this;
        }

        public Criteria andDyupfilesIdEqualTo(String value) {
            addCriterion("dyupfiles_id =", value, "dyupfilesId");
            return (Criteria) this;
        }

        public Criteria andDyupfilesIdNotEqualTo(String value) {
            addCriterion("dyupfiles_id <>", value, "dyupfilesId");
            return (Criteria) this;
        }

        public Criteria andDyupfilesIdGreaterThan(String value) {
            addCriterion("dyupfiles_id >", value, "dyupfilesId");
            return (Criteria) this;
        }

        public Criteria andDyupfilesIdGreaterThanOrEqualTo(String value) {
            addCriterion("dyupfiles_id >=", value, "dyupfilesId");
            return (Criteria) this;
        }

        public Criteria andDyupfilesIdLessThan(String value) {
            addCriterion("dyupfiles_id <", value, "dyupfilesId");
            return (Criteria) this;
        }

        public Criteria andDyupfilesIdLessThanOrEqualTo(String value) {
            addCriterion("dyupfiles_id <=", value, "dyupfilesId");
            return (Criteria) this;
        }

        public Criteria andDyupfilesIdLike(String value) {
            addCriterion("dyupfiles_id like", value, "dyupfilesId");
            return (Criteria) this;
        }

        public Criteria andDyupfilesIdNotLike(String value) {
            addCriterion("dyupfiles_id not like", value, "dyupfilesId");
            return (Criteria) this;
        }

        public Criteria andDyupfilesIdIn(List<String> values) {
            addCriterion("dyupfiles_id in", values, "dyupfilesId");
            return (Criteria) this;
        }

        public Criteria andDyupfilesIdNotIn(List<String> values) {
            addCriterion("dyupfiles_id not in", values, "dyupfilesId");
            return (Criteria) this;
        }

        public Criteria andDyupfilesIdBetween(String value1, String value2) {
            addCriterion("dyupfiles_id between", value1, value2, "dyupfilesId");
            return (Criteria) this;
        }

        public Criteria andDyupfilesIdNotBetween(String value1, String value2) {
            addCriterion("dyupfiles_id not between", value1, value2, "dyupfilesId");
            return (Criteria) this;
        }

        public Criteria andGuaranteeTypeIsNull() {
            addCriterion("guarantee_type is null");
            return (Criteria) this;
        }

        public Criteria andGuaranteeTypeIsNotNull() {
            addCriterion("guarantee_type is not null");
            return (Criteria) this;
        }

        public Criteria andGuaranteeTypeEqualTo(Integer value) {
            addCriterion("guarantee_type =", value, "guaranteeType");
            return (Criteria) this;
        }

        public Criteria andGuaranteeTypeNotEqualTo(Integer value) {
            addCriterion("guarantee_type <>", value, "guaranteeType");
            return (Criteria) this;
        }

        public Criteria andGuaranteeTypeGreaterThan(Integer value) {
            addCriterion("guarantee_type >", value, "guaranteeType");
            return (Criteria) this;
        }

        public Criteria andGuaranteeTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("guarantee_type >=", value, "guaranteeType");
            return (Criteria) this;
        }

        public Criteria andGuaranteeTypeLessThan(Integer value) {
            addCriterion("guarantee_type <", value, "guaranteeType");
            return (Criteria) this;
        }

        public Criteria andGuaranteeTypeLessThanOrEqualTo(Integer value) {
            addCriterion("guarantee_type <=", value, "guaranteeType");
            return (Criteria) this;
        }

        public Criteria andGuaranteeTypeIn(List<Integer> values) {
            addCriterion("guarantee_type in", values, "guaranteeType");
            return (Criteria) this;
        }

        public Criteria andGuaranteeTypeNotIn(List<Integer> values) {
            addCriterion("guarantee_type not in", values, "guaranteeType");
            return (Criteria) this;
        }

        public Criteria andGuaranteeTypeBetween(Integer value1, Integer value2) {
            addCriterion("guarantee_type between", value1, value2, "guaranteeType");
            return (Criteria) this;
        }

        public Criteria andGuaranteeTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("guarantee_type not between", value1, value2, "guaranteeType");
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

        public Criteria andOntimeIsNull() {
            addCriterion("ontime is null");
            return (Criteria) this;
        }

        public Criteria andOntimeIsNotNull() {
            addCriterion("ontime is not null");
            return (Criteria) this;
        }

        public Criteria andOntimeEqualTo(Integer value) {
            addCriterion("ontime =", value, "ontime");
            return (Criteria) this;
        }

        public Criteria andOntimeNotEqualTo(Integer value) {
            addCriterion("ontime <>", value, "ontime");
            return (Criteria) this;
        }

        public Criteria andOntimeGreaterThan(Integer value) {
            addCriterion("ontime >", value, "ontime");
            return (Criteria) this;
        }

        public Criteria andOntimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("ontime >=", value, "ontime");
            return (Criteria) this;
        }

        public Criteria andOntimeLessThan(Integer value) {
            addCriterion("ontime <", value, "ontime");
            return (Criteria) this;
        }

        public Criteria andOntimeLessThanOrEqualTo(Integer value) {
            addCriterion("ontime <=", value, "ontime");
            return (Criteria) this;
        }

        public Criteria andOntimeIn(List<Integer> values) {
            addCriterion("ontime in", values, "ontime");
            return (Criteria) this;
        }

        public Criteria andOntimeNotIn(List<Integer> values) {
            addCriterion("ontime not in", values, "ontime");
            return (Criteria) this;
        }

        public Criteria andOntimeBetween(Integer value1, Integer value2) {
            addCriterion("ontime between", value1, value2, "ontime");
            return (Criteria) this;
        }

        public Criteria andOntimeNotBetween(Integer value1, Integer value2) {
            addCriterion("ontime not between", value1, value2, "ontime");
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

        public Criteria andServiceFeeRateEqualTo(String value) {
            addCriterion("service_fee_rate =", value, "serviceFeeRate");
            return (Criteria) this;
        }

        public Criteria andServiceFeeRateNotEqualTo(String value) {
            addCriterion("service_fee_rate <>", value, "serviceFeeRate");
            return (Criteria) this;
        }

        public Criteria andServiceFeeRateGreaterThan(String value) {
            addCriterion("service_fee_rate >", value, "serviceFeeRate");
            return (Criteria) this;
        }

        public Criteria andServiceFeeRateGreaterThanOrEqualTo(String value) {
            addCriterion("service_fee_rate >=", value, "serviceFeeRate");
            return (Criteria) this;
        }

        public Criteria andServiceFeeRateLessThan(String value) {
            addCriterion("service_fee_rate <", value, "serviceFeeRate");
            return (Criteria) this;
        }

        public Criteria andServiceFeeRateLessThanOrEqualTo(String value) {
            addCriterion("service_fee_rate <=", value, "serviceFeeRate");
            return (Criteria) this;
        }

        public Criteria andServiceFeeRateLike(String value) {
            addCriterion("service_fee_rate like", value, "serviceFeeRate");
            return (Criteria) this;
        }

        public Criteria andServiceFeeRateNotLike(String value) {
            addCriterion("service_fee_rate not like", value, "serviceFeeRate");
            return (Criteria) this;
        }

        public Criteria andServiceFeeRateIn(List<String> values) {
            addCriterion("service_fee_rate in", values, "serviceFeeRate");
            return (Criteria) this;
        }

        public Criteria andServiceFeeRateNotIn(List<String> values) {
            addCriterion("service_fee_rate not in", values, "serviceFeeRate");
            return (Criteria) this;
        }

        public Criteria andServiceFeeRateBetween(String value1, String value2) {
            addCriterion("service_fee_rate between", value1, value2, "serviceFeeRate");
            return (Criteria) this;
        }

        public Criteria andServiceFeeRateNotBetween(String value1, String value2) {
            addCriterion("service_fee_rate not between", value1, value2, "serviceFeeRate");
            return (Criteria) this;
        }

        public Criteria andManageFeeRateIsNull() {
            addCriterion("manage_fee_rate is null");
            return (Criteria) this;
        }

        public Criteria andManageFeeRateIsNotNull() {
            addCriterion("manage_fee_rate is not null");
            return (Criteria) this;
        }

        public Criteria andManageFeeRateEqualTo(String value) {
            addCriterion("manage_fee_rate =", value, "manageFeeRate");
            return (Criteria) this;
        }

        public Criteria andManageFeeRateNotEqualTo(String value) {
            addCriterion("manage_fee_rate <>", value, "manageFeeRate");
            return (Criteria) this;
        }

        public Criteria andManageFeeRateGreaterThan(String value) {
            addCriterion("manage_fee_rate >", value, "manageFeeRate");
            return (Criteria) this;
        }

        public Criteria andManageFeeRateGreaterThanOrEqualTo(String value) {
            addCriterion("manage_fee_rate >=", value, "manageFeeRate");
            return (Criteria) this;
        }

        public Criteria andManageFeeRateLessThan(String value) {
            addCriterion("manage_fee_rate <", value, "manageFeeRate");
            return (Criteria) this;
        }

        public Criteria andManageFeeRateLessThanOrEqualTo(String value) {
            addCriterion("manage_fee_rate <=", value, "manageFeeRate");
            return (Criteria) this;
        }

        public Criteria andManageFeeRateLike(String value) {
            addCriterion("manage_fee_rate like", value, "manageFeeRate");
            return (Criteria) this;
        }

        public Criteria andManageFeeRateNotLike(String value) {
            addCriterion("manage_fee_rate not like", value, "manageFeeRate");
            return (Criteria) this;
        }

        public Criteria andManageFeeRateIn(List<String> values) {
            addCriterion("manage_fee_rate in", values, "manageFeeRate");
            return (Criteria) this;
        }

        public Criteria andManageFeeRateNotIn(List<String> values) {
            addCriterion("manage_fee_rate not in", values, "manageFeeRate");
            return (Criteria) this;
        }

        public Criteria andManageFeeRateBetween(String value1, String value2) {
            addCriterion("manage_fee_rate between", value1, value2, "manageFeeRate");
            return (Criteria) this;
        }

        public Criteria andManageFeeRateNotBetween(String value1, String value2) {
            addCriterion("manage_fee_rate not between", value1, value2, "manageFeeRate");
            return (Criteria) this;
        }

        public Criteria andDifferentialRateIsNull() {
            addCriterion("differential_rate is null");
            return (Criteria) this;
        }

        public Criteria andDifferentialRateIsNotNull() {
            addCriterion("differential_rate is not null");
            return (Criteria) this;
        }

        public Criteria andDifferentialRateEqualTo(String value) {
            addCriterion("differential_rate =", value, "differentialRate");
            return (Criteria) this;
        }

        public Criteria andDifferentialRateNotEqualTo(String value) {
            addCriterion("differential_rate <>", value, "differentialRate");
            return (Criteria) this;
        }

        public Criteria andDifferentialRateGreaterThan(String value) {
            addCriterion("differential_rate >", value, "differentialRate");
            return (Criteria) this;
        }

        public Criteria andDifferentialRateGreaterThanOrEqualTo(String value) {
            addCriterion("differential_rate >=", value, "differentialRate");
            return (Criteria) this;
        }

        public Criteria andDifferentialRateLessThan(String value) {
            addCriterion("differential_rate <", value, "differentialRate");
            return (Criteria) this;
        }

        public Criteria andDifferentialRateLessThanOrEqualTo(String value) {
            addCriterion("differential_rate <=", value, "differentialRate");
            return (Criteria) this;
        }

        public Criteria andDifferentialRateLike(String value) {
            addCriterion("differential_rate like", value, "differentialRate");
            return (Criteria) this;
        }

        public Criteria andDifferentialRateNotLike(String value) {
            addCriterion("differential_rate not like", value, "differentialRate");
            return (Criteria) this;
        }

        public Criteria andDifferentialRateIn(List<String> values) {
            addCriterion("differential_rate in", values, "differentialRate");
            return (Criteria) this;
        }

        public Criteria andDifferentialRateNotIn(List<String> values) {
            addCriterion("differential_rate not in", values, "differentialRate");
            return (Criteria) this;
        }

        public Criteria andDifferentialRateBetween(String value1, String value2) {
            addCriterion("differential_rate between", value1, value2, "differentialRate");
            return (Criteria) this;
        }

        public Criteria andDifferentialRateNotBetween(String value1, String value2) {
            addCriterion("differential_rate not between", value1, value2, "differentialRate");
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

        public Criteria andBorrowManagerIsNull() {
            addCriterion("borrow_manager is null");
            return (Criteria) this;
        }

        public Criteria andBorrowManagerIsNotNull() {
            addCriterion("borrow_manager is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowManagerEqualTo(String value) {
            addCriterion("borrow_manager =", value, "borrowManager");
            return (Criteria) this;
        }

        public Criteria andBorrowManagerNotEqualTo(String value) {
            addCriterion("borrow_manager <>", value, "borrowManager");
            return (Criteria) this;
        }

        public Criteria andBorrowManagerGreaterThan(String value) {
            addCriterion("borrow_manager >", value, "borrowManager");
            return (Criteria) this;
        }

        public Criteria andBorrowManagerGreaterThanOrEqualTo(String value) {
            addCriterion("borrow_manager >=", value, "borrowManager");
            return (Criteria) this;
        }

        public Criteria andBorrowManagerLessThan(String value) {
            addCriterion("borrow_manager <", value, "borrowManager");
            return (Criteria) this;
        }

        public Criteria andBorrowManagerLessThanOrEqualTo(String value) {
            addCriterion("borrow_manager <=", value, "borrowManager");
            return (Criteria) this;
        }

        public Criteria andBorrowManagerLike(String value) {
            addCriterion("borrow_manager like", value, "borrowManager");
            return (Criteria) this;
        }

        public Criteria andBorrowManagerNotLike(String value) {
            addCriterion("borrow_manager not like", value, "borrowManager");
            return (Criteria) this;
        }

        public Criteria andBorrowManagerIn(List<String> values) {
            addCriterion("borrow_manager in", values, "borrowManager");
            return (Criteria) this;
        }

        public Criteria andBorrowManagerNotIn(List<String> values) {
            addCriterion("borrow_manager not in", values, "borrowManager");
            return (Criteria) this;
        }

        public Criteria andBorrowManagerBetween(String value1, String value2) {
            addCriterion("borrow_manager between", value1, value2, "borrowManager");
            return (Criteria) this;
        }

        public Criteria andBorrowManagerNotBetween(String value1, String value2) {
            addCriterion("borrow_manager not between", value1, value2, "borrowManager");
            return (Criteria) this;
        }

        public Criteria andBorrowServiceIsNull() {
            addCriterion("borrow_service is null");
            return (Criteria) this;
        }

        public Criteria andBorrowServiceIsNotNull() {
            addCriterion("borrow_service is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowServiceEqualTo(String value) {
            addCriterion("borrow_service =", value, "borrowService");
            return (Criteria) this;
        }

        public Criteria andBorrowServiceNotEqualTo(String value) {
            addCriterion("borrow_service <>", value, "borrowService");
            return (Criteria) this;
        }

        public Criteria andBorrowServiceGreaterThan(String value) {
            addCriterion("borrow_service >", value, "borrowService");
            return (Criteria) this;
        }

        public Criteria andBorrowServiceGreaterThanOrEqualTo(String value) {
            addCriterion("borrow_service >=", value, "borrowService");
            return (Criteria) this;
        }

        public Criteria andBorrowServiceLessThan(String value) {
            addCriterion("borrow_service <", value, "borrowService");
            return (Criteria) this;
        }

        public Criteria andBorrowServiceLessThanOrEqualTo(String value) {
            addCriterion("borrow_service <=", value, "borrowService");
            return (Criteria) this;
        }

        public Criteria andBorrowServiceLike(String value) {
            addCriterion("borrow_service like", value, "borrowService");
            return (Criteria) this;
        }

        public Criteria andBorrowServiceNotLike(String value) {
            addCriterion("borrow_service not like", value, "borrowService");
            return (Criteria) this;
        }

        public Criteria andBorrowServiceIn(List<String> values) {
            addCriterion("borrow_service in", values, "borrowService");
            return (Criteria) this;
        }

        public Criteria andBorrowServiceNotIn(List<String> values) {
            addCriterion("borrow_service not in", values, "borrowService");
            return (Criteria) this;
        }

        public Criteria andBorrowServiceBetween(String value1, String value2) {
            addCriterion("borrow_service between", value1, value2, "borrowService");
            return (Criteria) this;
        }

        public Criteria andBorrowServiceNotBetween(String value1, String value2) {
            addCriterion("borrow_service not between", value1, value2, "borrowService");
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

        public Criteria andBorrowFullTimeIsNull() {
            addCriterion("borrow_full_time is null");
            return (Criteria) this;
        }

        public Criteria andBorrowFullTimeIsNotNull() {
            addCriterion("borrow_full_time is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowFullTimeEqualTo(Integer value) {
            addCriterion("borrow_full_time =", value, "borrowFullTime");
            return (Criteria) this;
        }

        public Criteria andBorrowFullTimeNotEqualTo(Integer value) {
            addCriterion("borrow_full_time <>", value, "borrowFullTime");
            return (Criteria) this;
        }

        public Criteria andBorrowFullTimeGreaterThan(Integer value) {
            addCriterion("borrow_full_time >", value, "borrowFullTime");
            return (Criteria) this;
        }

        public Criteria andBorrowFullTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("borrow_full_time >=", value, "borrowFullTime");
            return (Criteria) this;
        }

        public Criteria andBorrowFullTimeLessThan(Integer value) {
            addCriterion("borrow_full_time <", value, "borrowFullTime");
            return (Criteria) this;
        }

        public Criteria andBorrowFullTimeLessThanOrEqualTo(Integer value) {
            addCriterion("borrow_full_time <=", value, "borrowFullTime");
            return (Criteria) this;
        }

        public Criteria andBorrowFullTimeIn(List<Integer> values) {
            addCriterion("borrow_full_time in", values, "borrowFullTime");
            return (Criteria) this;
        }

        public Criteria andBorrowFullTimeNotIn(List<Integer> values) {
            addCriterion("borrow_full_time not in", values, "borrowFullTime");
            return (Criteria) this;
        }

        public Criteria andBorrowFullTimeBetween(Integer value1, Integer value2) {
            addCriterion("borrow_full_time between", value1, value2, "borrowFullTime");
            return (Criteria) this;
        }

        public Criteria andBorrowFullTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("borrow_full_time not between", value1, value2, "borrowFullTime");
            return (Criteria) this;
        }

        public Criteria andRecoverLastTimeIsNull() {
            addCriterion("recover_last_time is null");
            return (Criteria) this;
        }

        public Criteria andRecoverLastTimeIsNotNull() {
            addCriterion("recover_last_time is not null");
            return (Criteria) this;
        }

        public Criteria andRecoverLastTimeEqualTo(Integer value) {
            addCriterion("recover_last_time =", value, "recoverLastTime");
            return (Criteria) this;
        }

        public Criteria andRecoverLastTimeNotEqualTo(Integer value) {
            addCriterion("recover_last_time <>", value, "recoverLastTime");
            return (Criteria) this;
        }

        public Criteria andRecoverLastTimeGreaterThan(Integer value) {
            addCriterion("recover_last_time >", value, "recoverLastTime");
            return (Criteria) this;
        }

        public Criteria andRecoverLastTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("recover_last_time >=", value, "recoverLastTime");
            return (Criteria) this;
        }

        public Criteria andRecoverLastTimeLessThan(Integer value) {
            addCriterion("recover_last_time <", value, "recoverLastTime");
            return (Criteria) this;
        }

        public Criteria andRecoverLastTimeLessThanOrEqualTo(Integer value) {
            addCriterion("recover_last_time <=", value, "recoverLastTime");
            return (Criteria) this;
        }

        public Criteria andRecoverLastTimeIn(List<Integer> values) {
            addCriterion("recover_last_time in", values, "recoverLastTime");
            return (Criteria) this;
        }

        public Criteria andRecoverLastTimeNotIn(List<Integer> values) {
            addCriterion("recover_last_time not in", values, "recoverLastTime");
            return (Criteria) this;
        }

        public Criteria andRecoverLastTimeBetween(Integer value1, Integer value2) {
            addCriterion("recover_last_time between", value1, value2, "recoverLastTime");
            return (Criteria) this;
        }

        public Criteria andRecoverLastTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("recover_last_time not between", value1, value2, "recoverLastTime");
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

        public Criteria andVerifyOverTimeIsNull() {
            addCriterion("verify_over_time is null");
            return (Criteria) this;
        }

        public Criteria andVerifyOverTimeIsNotNull() {
            addCriterion("verify_over_time is not null");
            return (Criteria) this;
        }

        public Criteria andVerifyOverTimeEqualTo(Integer value) {
            addCriterion("verify_over_time =", value, "verifyOverTime");
            return (Criteria) this;
        }

        public Criteria andVerifyOverTimeNotEqualTo(Integer value) {
            addCriterion("verify_over_time <>", value, "verifyOverTime");
            return (Criteria) this;
        }

        public Criteria andVerifyOverTimeGreaterThan(Integer value) {
            addCriterion("verify_over_time >", value, "verifyOverTime");
            return (Criteria) this;
        }

        public Criteria andVerifyOverTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("verify_over_time >=", value, "verifyOverTime");
            return (Criteria) this;
        }

        public Criteria andVerifyOverTimeLessThan(Integer value) {
            addCriterion("verify_over_time <", value, "verifyOverTime");
            return (Criteria) this;
        }

        public Criteria andVerifyOverTimeLessThanOrEqualTo(Integer value) {
            addCriterion("verify_over_time <=", value, "verifyOverTime");
            return (Criteria) this;
        }

        public Criteria andVerifyOverTimeIn(List<Integer> values) {
            addCriterion("verify_over_time in", values, "verifyOverTime");
            return (Criteria) this;
        }

        public Criteria andVerifyOverTimeNotIn(List<Integer> values) {
            addCriterion("verify_over_time not in", values, "verifyOverTime");
            return (Criteria) this;
        }

        public Criteria andVerifyOverTimeBetween(Integer value1, Integer value2) {
            addCriterion("verify_over_time between", value1, value2, "verifyOverTime");
            return (Criteria) this;
        }

        public Criteria andVerifyOverTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("verify_over_time not between", value1, value2, "verifyOverTime");
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

        public Criteria andBankInputFlagIsNull() {
            addCriterion("bank_input_flag is null");
            return (Criteria) this;
        }

        public Criteria andBankInputFlagIsNotNull() {
            addCriterion("bank_input_flag is not null");
            return (Criteria) this;
        }

        public Criteria andBankInputFlagEqualTo(Integer value) {
            addCriterion("bank_input_flag =", value, "bankInputFlag");
            return (Criteria) this;
        }

        public Criteria andBankInputFlagNotEqualTo(Integer value) {
            addCriterion("bank_input_flag <>", value, "bankInputFlag");
            return (Criteria) this;
        }

        public Criteria andBankInputFlagGreaterThan(Integer value) {
            addCriterion("bank_input_flag >", value, "bankInputFlag");
            return (Criteria) this;
        }

        public Criteria andBankInputFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("bank_input_flag >=", value, "bankInputFlag");
            return (Criteria) this;
        }

        public Criteria andBankInputFlagLessThan(Integer value) {
            addCriterion("bank_input_flag <", value, "bankInputFlag");
            return (Criteria) this;
        }

        public Criteria andBankInputFlagLessThanOrEqualTo(Integer value) {
            addCriterion("bank_input_flag <=", value, "bankInputFlag");
            return (Criteria) this;
        }

        public Criteria andBankInputFlagIn(List<Integer> values) {
            addCriterion("bank_input_flag in", values, "bankInputFlag");
            return (Criteria) this;
        }

        public Criteria andBankInputFlagNotIn(List<Integer> values) {
            addCriterion("bank_input_flag not in", values, "bankInputFlag");
            return (Criteria) this;
        }

        public Criteria andBankInputFlagBetween(Integer value1, Integer value2) {
            addCriterion("bank_input_flag between", value1, value2, "bankInputFlag");
            return (Criteria) this;
        }

        public Criteria andBankInputFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("bank_input_flag not between", value1, value2, "bankInputFlag");
            return (Criteria) this;
        }

        public Criteria andBookingBeginTimeIsNull() {
            addCriterion("booking_begin_time is null");
            return (Criteria) this;
        }

        public Criteria andBookingBeginTimeIsNotNull() {
            addCriterion("booking_begin_time is not null");
            return (Criteria) this;
        }

        public Criteria andBookingBeginTimeEqualTo(Integer value) {
            addCriterion("booking_begin_time =", value, "bookingBeginTime");
            return (Criteria) this;
        }

        public Criteria andBookingBeginTimeNotEqualTo(Integer value) {
            addCriterion("booking_begin_time <>", value, "bookingBeginTime");
            return (Criteria) this;
        }

        public Criteria andBookingBeginTimeGreaterThan(Integer value) {
            addCriterion("booking_begin_time >", value, "bookingBeginTime");
            return (Criteria) this;
        }

        public Criteria andBookingBeginTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("booking_begin_time >=", value, "bookingBeginTime");
            return (Criteria) this;
        }

        public Criteria andBookingBeginTimeLessThan(Integer value) {
            addCriterion("booking_begin_time <", value, "bookingBeginTime");
            return (Criteria) this;
        }

        public Criteria andBookingBeginTimeLessThanOrEqualTo(Integer value) {
            addCriterion("booking_begin_time <=", value, "bookingBeginTime");
            return (Criteria) this;
        }

        public Criteria andBookingBeginTimeIn(List<Integer> values) {
            addCriterion("booking_begin_time in", values, "bookingBeginTime");
            return (Criteria) this;
        }

        public Criteria andBookingBeginTimeNotIn(List<Integer> values) {
            addCriterion("booking_begin_time not in", values, "bookingBeginTime");
            return (Criteria) this;
        }

        public Criteria andBookingBeginTimeBetween(Integer value1, Integer value2) {
            addCriterion("booking_begin_time between", value1, value2, "bookingBeginTime");
            return (Criteria) this;
        }

        public Criteria andBookingBeginTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("booking_begin_time not between", value1, value2, "bookingBeginTime");
            return (Criteria) this;
        }

        public Criteria andBookingEndTimeIsNull() {
            addCriterion("booking_end_time is null");
            return (Criteria) this;
        }

        public Criteria andBookingEndTimeIsNotNull() {
            addCriterion("booking_end_time is not null");
            return (Criteria) this;
        }

        public Criteria andBookingEndTimeEqualTo(Integer value) {
            addCriterion("booking_end_time =", value, "bookingEndTime");
            return (Criteria) this;
        }

        public Criteria andBookingEndTimeNotEqualTo(Integer value) {
            addCriterion("booking_end_time <>", value, "bookingEndTime");
            return (Criteria) this;
        }

        public Criteria andBookingEndTimeGreaterThan(Integer value) {
            addCriterion("booking_end_time >", value, "bookingEndTime");
            return (Criteria) this;
        }

        public Criteria andBookingEndTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("booking_end_time >=", value, "bookingEndTime");
            return (Criteria) this;
        }

        public Criteria andBookingEndTimeLessThan(Integer value) {
            addCriterion("booking_end_time <", value, "bookingEndTime");
            return (Criteria) this;
        }

        public Criteria andBookingEndTimeLessThanOrEqualTo(Integer value) {
            addCriterion("booking_end_time <=", value, "bookingEndTime");
            return (Criteria) this;
        }

        public Criteria andBookingEndTimeIn(List<Integer> values) {
            addCriterion("booking_end_time in", values, "bookingEndTime");
            return (Criteria) this;
        }

        public Criteria andBookingEndTimeNotIn(List<Integer> values) {
            addCriterion("booking_end_time not in", values, "bookingEndTime");
            return (Criteria) this;
        }

        public Criteria andBookingEndTimeBetween(Integer value1, Integer value2) {
            addCriterion("booking_end_time between", value1, value2, "bookingEndTime");
            return (Criteria) this;
        }

        public Criteria andBookingEndTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("booking_end_time not between", value1, value2, "bookingEndTime");
            return (Criteria) this;
        }

        public Criteria andBookingStatusIsNull() {
            addCriterion("booking_status is null");
            return (Criteria) this;
        }

        public Criteria andBookingStatusIsNotNull() {
            addCriterion("booking_status is not null");
            return (Criteria) this;
        }

        public Criteria andBookingStatusEqualTo(Integer value) {
            addCriterion("booking_status =", value, "bookingStatus");
            return (Criteria) this;
        }

        public Criteria andBookingStatusNotEqualTo(Integer value) {
            addCriterion("booking_status <>", value, "bookingStatus");
            return (Criteria) this;
        }

        public Criteria andBookingStatusGreaterThan(Integer value) {
            addCriterion("booking_status >", value, "bookingStatus");
            return (Criteria) this;
        }

        public Criteria andBookingStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("booking_status >=", value, "bookingStatus");
            return (Criteria) this;
        }

        public Criteria andBookingStatusLessThan(Integer value) {
            addCriterion("booking_status <", value, "bookingStatus");
            return (Criteria) this;
        }

        public Criteria andBookingStatusLessThanOrEqualTo(Integer value) {
            addCriterion("booking_status <=", value, "bookingStatus");
            return (Criteria) this;
        }

        public Criteria andBookingStatusIn(List<Integer> values) {
            addCriterion("booking_status in", values, "bookingStatus");
            return (Criteria) this;
        }

        public Criteria andBookingStatusNotIn(List<Integer> values) {
            addCriterion("booking_status not in", values, "bookingStatus");
            return (Criteria) this;
        }

        public Criteria andBookingStatusBetween(Integer value1, Integer value2) {
            addCriterion("booking_status between", value1, value2, "bookingStatus");
            return (Criteria) this;
        }

        public Criteria andBookingStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("booking_status not between", value1, value2, "bookingStatus");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountWaitAppointIsNull() {
            addCriterion("borrow_account_wait_appoint is null");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountWaitAppointIsNotNull() {
            addCriterion("borrow_account_wait_appoint is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountWaitAppointEqualTo(BigDecimal value) {
            addCriterion("borrow_account_wait_appoint =", value, "borrowAccountWaitAppoint");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountWaitAppointNotEqualTo(BigDecimal value) {
            addCriterion("borrow_account_wait_appoint <>", value, "borrowAccountWaitAppoint");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountWaitAppointGreaterThan(BigDecimal value) {
            addCriterion("borrow_account_wait_appoint >", value, "borrowAccountWaitAppoint");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountWaitAppointGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("borrow_account_wait_appoint >=", value, "borrowAccountWaitAppoint");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountWaitAppointLessThan(BigDecimal value) {
            addCriterion("borrow_account_wait_appoint <", value, "borrowAccountWaitAppoint");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountWaitAppointLessThanOrEqualTo(BigDecimal value) {
            addCriterion("borrow_account_wait_appoint <=", value, "borrowAccountWaitAppoint");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountWaitAppointIn(List<BigDecimal> values) {
            addCriterion("borrow_account_wait_appoint in", values, "borrowAccountWaitAppoint");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountWaitAppointNotIn(List<BigDecimal> values) {
            addCriterion("borrow_account_wait_appoint not in", values, "borrowAccountWaitAppoint");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountWaitAppointBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("borrow_account_wait_appoint between", value1, value2, "borrowAccountWaitAppoint");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountWaitAppointNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("borrow_account_wait_appoint not between", value1, value2, "borrowAccountWaitAppoint");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountScaleAppointIsNull() {
            addCriterion("borrow_account_scale_appoint is null");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountScaleAppointIsNotNull() {
            addCriterion("borrow_account_scale_appoint is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountScaleAppointEqualTo(BigDecimal value) {
            addCriterion("borrow_account_scale_appoint =", value, "borrowAccountScaleAppoint");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountScaleAppointNotEqualTo(BigDecimal value) {
            addCriterion("borrow_account_scale_appoint <>", value, "borrowAccountScaleAppoint");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountScaleAppointGreaterThan(BigDecimal value) {
            addCriterion("borrow_account_scale_appoint >", value, "borrowAccountScaleAppoint");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountScaleAppointGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("borrow_account_scale_appoint >=", value, "borrowAccountScaleAppoint");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountScaleAppointLessThan(BigDecimal value) {
            addCriterion("borrow_account_scale_appoint <", value, "borrowAccountScaleAppoint");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountScaleAppointLessThanOrEqualTo(BigDecimal value) {
            addCriterion("borrow_account_scale_appoint <=", value, "borrowAccountScaleAppoint");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountScaleAppointIn(List<BigDecimal> values) {
            addCriterion("borrow_account_scale_appoint in", values, "borrowAccountScaleAppoint");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountScaleAppointNotIn(List<BigDecimal> values) {
            addCriterion("borrow_account_scale_appoint not in", values, "borrowAccountScaleAppoint");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountScaleAppointBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("borrow_account_scale_appoint between", value1, value2, "borrowAccountScaleAppoint");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountScaleAppointNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("borrow_account_scale_appoint not between", value1, value2, "borrowAccountScaleAppoint");
            return (Criteria) this;
        }

        public Criteria andBorrowPlanSelectedIsNull() {
            addCriterion("borrow_plan_selected is null");
            return (Criteria) this;
        }

        public Criteria andBorrowPlanSelectedIsNotNull() {
            addCriterion("borrow_plan_selected is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowPlanSelectedEqualTo(Integer value) {
            addCriterion("borrow_plan_selected =", value, "borrowPlanSelected");
            return (Criteria) this;
        }

        public Criteria andBorrowPlanSelectedNotEqualTo(Integer value) {
            addCriterion("borrow_plan_selected <>", value, "borrowPlanSelected");
            return (Criteria) this;
        }

        public Criteria andBorrowPlanSelectedGreaterThan(Integer value) {
            addCriterion("borrow_plan_selected >", value, "borrowPlanSelected");
            return (Criteria) this;
        }

        public Criteria andBorrowPlanSelectedGreaterThanOrEqualTo(Integer value) {
            addCriterion("borrow_plan_selected >=", value, "borrowPlanSelected");
            return (Criteria) this;
        }

        public Criteria andBorrowPlanSelectedLessThan(Integer value) {
            addCriterion("borrow_plan_selected <", value, "borrowPlanSelected");
            return (Criteria) this;
        }

        public Criteria andBorrowPlanSelectedLessThanOrEqualTo(Integer value) {
            addCriterion("borrow_plan_selected <=", value, "borrowPlanSelected");
            return (Criteria) this;
        }

        public Criteria andBorrowPlanSelectedIn(List<Integer> values) {
            addCriterion("borrow_plan_selected in", values, "borrowPlanSelected");
            return (Criteria) this;
        }

        public Criteria andBorrowPlanSelectedNotIn(List<Integer> values) {
            addCriterion("borrow_plan_selected not in", values, "borrowPlanSelected");
            return (Criteria) this;
        }

        public Criteria andBorrowPlanSelectedBetween(Integer value1, Integer value2) {
            addCriterion("borrow_plan_selected between", value1, value2, "borrowPlanSelected");
            return (Criteria) this;
        }

        public Criteria andBorrowPlanSelectedNotBetween(Integer value1, Integer value2) {
            addCriterion("borrow_plan_selected not between", value1, value2, "borrowPlanSelected");
            return (Criteria) this;
        }

        public Criteria andBorrowRepayWebAdvanceIsNull() {
            addCriterion("borrow_repay_web_advance is null");
            return (Criteria) this;
        }

        public Criteria andBorrowRepayWebAdvanceIsNotNull() {
            addCriterion("borrow_repay_web_advance is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowRepayWebAdvanceEqualTo(BigDecimal value) {
            addCriterion("borrow_repay_web_advance =", value, "borrowRepayWebAdvance");
            return (Criteria) this;
        }

        public Criteria andBorrowRepayWebAdvanceNotEqualTo(BigDecimal value) {
            addCriterion("borrow_repay_web_advance <>", value, "borrowRepayWebAdvance");
            return (Criteria) this;
        }

        public Criteria andBorrowRepayWebAdvanceGreaterThan(BigDecimal value) {
            addCriterion("borrow_repay_web_advance >", value, "borrowRepayWebAdvance");
            return (Criteria) this;
        }

        public Criteria andBorrowRepayWebAdvanceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("borrow_repay_web_advance >=", value, "borrowRepayWebAdvance");
            return (Criteria) this;
        }

        public Criteria andBorrowRepayWebAdvanceLessThan(BigDecimal value) {
            addCriterion("borrow_repay_web_advance <", value, "borrowRepayWebAdvance");
            return (Criteria) this;
        }

        public Criteria andBorrowRepayWebAdvanceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("borrow_repay_web_advance <=", value, "borrowRepayWebAdvance");
            return (Criteria) this;
        }

        public Criteria andBorrowRepayWebAdvanceIn(List<BigDecimal> values) {
            addCriterion("borrow_repay_web_advance in", values, "borrowRepayWebAdvance");
            return (Criteria) this;
        }

        public Criteria andBorrowRepayWebAdvanceNotIn(List<BigDecimal> values) {
            addCriterion("borrow_repay_web_advance not in", values, "borrowRepayWebAdvance");
            return (Criteria) this;
        }

        public Criteria andBorrowRepayWebAdvanceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("borrow_repay_web_advance between", value1, value2, "borrowRepayWebAdvance");
            return (Criteria) this;
        }

        public Criteria andBorrowRepayWebAdvanceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("borrow_repay_web_advance not between", value1, value2, "borrowRepayWebAdvance");
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