package com.hyjf.am.trade.dao.model.auto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BorrowProjectTypeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public BorrowProjectTypeExample() {
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

        public Criteria andBorrowProjectTypeIsNull() {
            addCriterion("borrow_project_type is null");
            return (Criteria) this;
        }

        public Criteria andBorrowProjectTypeIsNotNull() {
            addCriterion("borrow_project_type is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowProjectTypeEqualTo(String value) {
            addCriterion("borrow_project_type =", value, "borrowProjectType");
            return (Criteria) this;
        }

        public Criteria andBorrowProjectTypeNotEqualTo(String value) {
            addCriterion("borrow_project_type <>", value, "borrowProjectType");
            return (Criteria) this;
        }

        public Criteria andBorrowProjectTypeGreaterThan(String value) {
            addCriterion("borrow_project_type >", value, "borrowProjectType");
            return (Criteria) this;
        }

        public Criteria andBorrowProjectTypeGreaterThanOrEqualTo(String value) {
            addCriterion("borrow_project_type >=", value, "borrowProjectType");
            return (Criteria) this;
        }

        public Criteria andBorrowProjectTypeLessThan(String value) {
            addCriterion("borrow_project_type <", value, "borrowProjectType");
            return (Criteria) this;
        }

        public Criteria andBorrowProjectTypeLessThanOrEqualTo(String value) {
            addCriterion("borrow_project_type <=", value, "borrowProjectType");
            return (Criteria) this;
        }

        public Criteria andBorrowProjectTypeLike(String value) {
            addCriterion("borrow_project_type like", value, "borrowProjectType");
            return (Criteria) this;
        }

        public Criteria andBorrowProjectTypeNotLike(String value) {
            addCriterion("borrow_project_type not like", value, "borrowProjectType");
            return (Criteria) this;
        }

        public Criteria andBorrowProjectTypeIn(List<String> values) {
            addCriterion("borrow_project_type in", values, "borrowProjectType");
            return (Criteria) this;
        }

        public Criteria andBorrowProjectTypeNotIn(List<String> values) {
            addCriterion("borrow_project_type not in", values, "borrowProjectType");
            return (Criteria) this;
        }

        public Criteria andBorrowProjectTypeBetween(String value1, String value2) {
            addCriterion("borrow_project_type between", value1, value2, "borrowProjectType");
            return (Criteria) this;
        }

        public Criteria andBorrowProjectTypeNotBetween(String value1, String value2) {
            addCriterion("borrow_project_type not between", value1, value2, "borrowProjectType");
            return (Criteria) this;
        }

        public Criteria andBorrowCdIsNull() {
            addCriterion("borrow_cd is null");
            return (Criteria) this;
        }

        public Criteria andBorrowCdIsNotNull() {
            addCriterion("borrow_cd is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowCdEqualTo(String value) {
            addCriterion("borrow_cd =", value, "borrowCd");
            return (Criteria) this;
        }

        public Criteria andBorrowCdNotEqualTo(String value) {
            addCriterion("borrow_cd <>", value, "borrowCd");
            return (Criteria) this;
        }

        public Criteria andBorrowCdGreaterThan(String value) {
            addCriterion("borrow_cd >", value, "borrowCd");
            return (Criteria) this;
        }

        public Criteria andBorrowCdGreaterThanOrEqualTo(String value) {
            addCriterion("borrow_cd >=", value, "borrowCd");
            return (Criteria) this;
        }

        public Criteria andBorrowCdLessThan(String value) {
            addCriterion("borrow_cd <", value, "borrowCd");
            return (Criteria) this;
        }

        public Criteria andBorrowCdLessThanOrEqualTo(String value) {
            addCriterion("borrow_cd <=", value, "borrowCd");
            return (Criteria) this;
        }

        public Criteria andBorrowCdLike(String value) {
            addCriterion("borrow_cd like", value, "borrowCd");
            return (Criteria) this;
        }

        public Criteria andBorrowCdNotLike(String value) {
            addCriterion("borrow_cd not like", value, "borrowCd");
            return (Criteria) this;
        }

        public Criteria andBorrowCdIn(List<String> values) {
            addCriterion("borrow_cd in", values, "borrowCd");
            return (Criteria) this;
        }

        public Criteria andBorrowCdNotIn(List<String> values) {
            addCriterion("borrow_cd not in", values, "borrowCd");
            return (Criteria) this;
        }

        public Criteria andBorrowCdBetween(String value1, String value2) {
            addCriterion("borrow_cd between", value1, value2, "borrowCd");
            return (Criteria) this;
        }

        public Criteria andBorrowCdNotBetween(String value1, String value2) {
            addCriterion("borrow_cd not between", value1, value2, "borrowCd");
            return (Criteria) this;
        }

        public Criteria andBorrowNameIsNull() {
            addCriterion("borrow_name is null");
            return (Criteria) this;
        }

        public Criteria andBorrowNameIsNotNull() {
            addCriterion("borrow_name is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowNameEqualTo(String value) {
            addCriterion("borrow_name =", value, "borrowName");
            return (Criteria) this;
        }

        public Criteria andBorrowNameNotEqualTo(String value) {
            addCriterion("borrow_name <>", value, "borrowName");
            return (Criteria) this;
        }

        public Criteria andBorrowNameGreaterThan(String value) {
            addCriterion("borrow_name >", value, "borrowName");
            return (Criteria) this;
        }

        public Criteria andBorrowNameGreaterThanOrEqualTo(String value) {
            addCriterion("borrow_name >=", value, "borrowName");
            return (Criteria) this;
        }

        public Criteria andBorrowNameLessThan(String value) {
            addCriterion("borrow_name <", value, "borrowName");
            return (Criteria) this;
        }

        public Criteria andBorrowNameLessThanOrEqualTo(String value) {
            addCriterion("borrow_name <=", value, "borrowName");
            return (Criteria) this;
        }

        public Criteria andBorrowNameLike(String value) {
            addCriterion("borrow_name like", value, "borrowName");
            return (Criteria) this;
        }

        public Criteria andBorrowNameNotLike(String value) {
            addCriterion("borrow_name not like", value, "borrowName");
            return (Criteria) this;
        }

        public Criteria andBorrowNameIn(List<String> values) {
            addCriterion("borrow_name in", values, "borrowName");
            return (Criteria) this;
        }

        public Criteria andBorrowNameNotIn(List<String> values) {
            addCriterion("borrow_name not in", values, "borrowName");
            return (Criteria) this;
        }

        public Criteria andBorrowNameBetween(String value1, String value2) {
            addCriterion("borrow_name between", value1, value2, "borrowName");
            return (Criteria) this;
        }

        public Criteria andBorrowNameNotBetween(String value1, String value2) {
            addCriterion("borrow_name not between", value1, value2, "borrowName");
            return (Criteria) this;
        }

        public Criteria andBorrowClassIsNull() {
            addCriterion("borrow_class is null");
            return (Criteria) this;
        }

        public Criteria andBorrowClassIsNotNull() {
            addCriterion("borrow_class is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowClassEqualTo(String value) {
            addCriterion("borrow_class =", value, "borrowClass");
            return (Criteria) this;
        }

        public Criteria andBorrowClassNotEqualTo(String value) {
            addCriterion("borrow_class <>", value, "borrowClass");
            return (Criteria) this;
        }

        public Criteria andBorrowClassGreaterThan(String value) {
            addCriterion("borrow_class >", value, "borrowClass");
            return (Criteria) this;
        }

        public Criteria andBorrowClassGreaterThanOrEqualTo(String value) {
            addCriterion("borrow_class >=", value, "borrowClass");
            return (Criteria) this;
        }

        public Criteria andBorrowClassLessThan(String value) {
            addCriterion("borrow_class <", value, "borrowClass");
            return (Criteria) this;
        }

        public Criteria andBorrowClassLessThanOrEqualTo(String value) {
            addCriterion("borrow_class <=", value, "borrowClass");
            return (Criteria) this;
        }

        public Criteria andBorrowClassLike(String value) {
            addCriterion("borrow_class like", value, "borrowClass");
            return (Criteria) this;
        }

        public Criteria andBorrowClassNotLike(String value) {
            addCriterion("borrow_class not like", value, "borrowClass");
            return (Criteria) this;
        }

        public Criteria andBorrowClassIn(List<String> values) {
            addCriterion("borrow_class in", values, "borrowClass");
            return (Criteria) this;
        }

        public Criteria andBorrowClassNotIn(List<String> values) {
            addCriterion("borrow_class not in", values, "borrowClass");
            return (Criteria) this;
        }

        public Criteria andBorrowClassBetween(String value1, String value2) {
            addCriterion("borrow_class between", value1, value2, "borrowClass");
            return (Criteria) this;
        }

        public Criteria andBorrowClassNotBetween(String value1, String value2) {
            addCriterion("borrow_class not between", value1, value2, "borrowClass");
            return (Criteria) this;
        }

        public Criteria andInvestUserTypeIsNull() {
            addCriterion("invest_user_type is null");
            return (Criteria) this;
        }

        public Criteria andInvestUserTypeIsNotNull() {
            addCriterion("invest_user_type is not null");
            return (Criteria) this;
        }

        public Criteria andInvestUserTypeEqualTo(String value) {
            addCriterion("invest_user_type =", value, "investUserType");
            return (Criteria) this;
        }

        public Criteria andInvestUserTypeNotEqualTo(String value) {
            addCriterion("invest_user_type <>", value, "investUserType");
            return (Criteria) this;
        }

        public Criteria andInvestUserTypeGreaterThan(String value) {
            addCriterion("invest_user_type >", value, "investUserType");
            return (Criteria) this;
        }

        public Criteria andInvestUserTypeGreaterThanOrEqualTo(String value) {
            addCriterion("invest_user_type >=", value, "investUserType");
            return (Criteria) this;
        }

        public Criteria andInvestUserTypeLessThan(String value) {
            addCriterion("invest_user_type <", value, "investUserType");
            return (Criteria) this;
        }

        public Criteria andInvestUserTypeLessThanOrEqualTo(String value) {
            addCriterion("invest_user_type <=", value, "investUserType");
            return (Criteria) this;
        }

        public Criteria andInvestUserTypeLike(String value) {
            addCriterion("invest_user_type like", value, "investUserType");
            return (Criteria) this;
        }

        public Criteria andInvestUserTypeNotLike(String value) {
            addCriterion("invest_user_type not like", value, "investUserType");
            return (Criteria) this;
        }

        public Criteria andInvestUserTypeIn(List<String> values) {
            addCriterion("invest_user_type in", values, "investUserType");
            return (Criteria) this;
        }

        public Criteria andInvestUserTypeNotIn(List<String> values) {
            addCriterion("invest_user_type not in", values, "investUserType");
            return (Criteria) this;
        }

        public Criteria andInvestUserTypeBetween(String value1, String value2) {
            addCriterion("invest_user_type between", value1, value2, "investUserType");
            return (Criteria) this;
        }

        public Criteria andInvestUserTypeNotBetween(String value1, String value2) {
            addCriterion("invest_user_type not between", value1, value2, "investUserType");
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

        public Criteria andStatusEqualTo(String value) {
            addCriterion("`status` =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(String value) {
            addCriterion("`status` <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(String value) {
            addCriterion("`status` >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(String value) {
            addCriterion("`status` >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(String value) {
            addCriterion("`status` <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(String value) {
            addCriterion("`status` <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLike(String value) {
            addCriterion("`status` like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotLike(String value) {
            addCriterion("`status` not like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<String> values) {
            addCriterion("`status` in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<String> values) {
            addCriterion("`status` not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(String value1, String value2) {
            addCriterion("`status` between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(String value1, String value2) {
            addCriterion("`status` not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andInvestStartIsNull() {
            addCriterion("invest_start is null");
            return (Criteria) this;
        }

        public Criteria andInvestStartIsNotNull() {
            addCriterion("invest_start is not null");
            return (Criteria) this;
        }

        public Criteria andInvestStartEqualTo(String value) {
            addCriterion("invest_start =", value, "investStart");
            return (Criteria) this;
        }

        public Criteria andInvestStartNotEqualTo(String value) {
            addCriterion("invest_start <>", value, "investStart");
            return (Criteria) this;
        }

        public Criteria andInvestStartGreaterThan(String value) {
            addCriterion("invest_start >", value, "investStart");
            return (Criteria) this;
        }

        public Criteria andInvestStartGreaterThanOrEqualTo(String value) {
            addCriterion("invest_start >=", value, "investStart");
            return (Criteria) this;
        }

        public Criteria andInvestStartLessThan(String value) {
            addCriterion("invest_start <", value, "investStart");
            return (Criteria) this;
        }

        public Criteria andInvestStartLessThanOrEqualTo(String value) {
            addCriterion("invest_start <=", value, "investStart");
            return (Criteria) this;
        }

        public Criteria andInvestStartLike(String value) {
            addCriterion("invest_start like", value, "investStart");
            return (Criteria) this;
        }

        public Criteria andInvestStartNotLike(String value) {
            addCriterion("invest_start not like", value, "investStart");
            return (Criteria) this;
        }

        public Criteria andInvestStartIn(List<String> values) {
            addCriterion("invest_start in", values, "investStart");
            return (Criteria) this;
        }

        public Criteria andInvestStartNotIn(List<String> values) {
            addCriterion("invest_start not in", values, "investStart");
            return (Criteria) this;
        }

        public Criteria andInvestStartBetween(String value1, String value2) {
            addCriterion("invest_start between", value1, value2, "investStart");
            return (Criteria) this;
        }

        public Criteria andInvestStartNotBetween(String value1, String value2) {
            addCriterion("invest_start not between", value1, value2, "investStart");
            return (Criteria) this;
        }

        public Criteria andInvestEndIsNull() {
            addCriterion("invest_end is null");
            return (Criteria) this;
        }

        public Criteria andInvestEndIsNotNull() {
            addCriterion("invest_end is not null");
            return (Criteria) this;
        }

        public Criteria andInvestEndEqualTo(String value) {
            addCriterion("invest_end =", value, "investEnd");
            return (Criteria) this;
        }

        public Criteria andInvestEndNotEqualTo(String value) {
            addCriterion("invest_end <>", value, "investEnd");
            return (Criteria) this;
        }

        public Criteria andInvestEndGreaterThan(String value) {
            addCriterion("invest_end >", value, "investEnd");
            return (Criteria) this;
        }

        public Criteria andInvestEndGreaterThanOrEqualTo(String value) {
            addCriterion("invest_end >=", value, "investEnd");
            return (Criteria) this;
        }

        public Criteria andInvestEndLessThan(String value) {
            addCriterion("invest_end <", value, "investEnd");
            return (Criteria) this;
        }

        public Criteria andInvestEndLessThanOrEqualTo(String value) {
            addCriterion("invest_end <=", value, "investEnd");
            return (Criteria) this;
        }

        public Criteria andInvestEndLike(String value) {
            addCriterion("invest_end like", value, "investEnd");
            return (Criteria) this;
        }

        public Criteria andInvestEndNotLike(String value) {
            addCriterion("invest_end not like", value, "investEnd");
            return (Criteria) this;
        }

        public Criteria andInvestEndIn(List<String> values) {
            addCriterion("invest_end in", values, "investEnd");
            return (Criteria) this;
        }

        public Criteria andInvestEndNotIn(List<String> values) {
            addCriterion("invest_end not in", values, "investEnd");
            return (Criteria) this;
        }

        public Criteria andInvestEndBetween(String value1, String value2) {
            addCriterion("invest_end between", value1, value2, "investEnd");
            return (Criteria) this;
        }

        public Criteria andInvestEndNotBetween(String value1, String value2) {
            addCriterion("invest_end not between", value1, value2, "investEnd");
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

        public Criteria andCreateGroupIdIsNull() {
            addCriterion("create_group_id is null");
            return (Criteria) this;
        }

        public Criteria andCreateGroupIdIsNotNull() {
            addCriterion("create_group_id is not null");
            return (Criteria) this;
        }

        public Criteria andCreateGroupIdEqualTo(String value) {
            addCriterion("create_group_id =", value, "createGroupId");
            return (Criteria) this;
        }

        public Criteria andCreateGroupIdNotEqualTo(String value) {
            addCriterion("create_group_id <>", value, "createGroupId");
            return (Criteria) this;
        }

        public Criteria andCreateGroupIdGreaterThan(String value) {
            addCriterion("create_group_id >", value, "createGroupId");
            return (Criteria) this;
        }

        public Criteria andCreateGroupIdGreaterThanOrEqualTo(String value) {
            addCriterion("create_group_id >=", value, "createGroupId");
            return (Criteria) this;
        }

        public Criteria andCreateGroupIdLessThan(String value) {
            addCriterion("create_group_id <", value, "createGroupId");
            return (Criteria) this;
        }

        public Criteria andCreateGroupIdLessThanOrEqualTo(String value) {
            addCriterion("create_group_id <=", value, "createGroupId");
            return (Criteria) this;
        }

        public Criteria andCreateGroupIdLike(String value) {
            addCriterion("create_group_id like", value, "createGroupId");
            return (Criteria) this;
        }

        public Criteria andCreateGroupIdNotLike(String value) {
            addCriterion("create_group_id not like", value, "createGroupId");
            return (Criteria) this;
        }

        public Criteria andCreateGroupIdIn(List<String> values) {
            addCriterion("create_group_id in", values, "createGroupId");
            return (Criteria) this;
        }

        public Criteria andCreateGroupIdNotIn(List<String> values) {
            addCriterion("create_group_id not in", values, "createGroupId");
            return (Criteria) this;
        }

        public Criteria andCreateGroupIdBetween(String value1, String value2) {
            addCriterion("create_group_id between", value1, value2, "createGroupId");
            return (Criteria) this;
        }

        public Criteria andCreateGroupIdNotBetween(String value1, String value2) {
            addCriterion("create_group_id not between", value1, value2, "createGroupId");
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

        public Criteria andCreateUserIdEqualTo(String value) {
            addCriterion("create_user_id =", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdNotEqualTo(String value) {
            addCriterion("create_user_id <>", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdGreaterThan(String value) {
            addCriterion("create_user_id >", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("create_user_id >=", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdLessThan(String value) {
            addCriterion("create_user_id <", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdLessThanOrEqualTo(String value) {
            addCriterion("create_user_id <=", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdLike(String value) {
            addCriterion("create_user_id like", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdNotLike(String value) {
            addCriterion("create_user_id not like", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdIn(List<String> values) {
            addCriterion("create_user_id in", values, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdNotIn(List<String> values) {
            addCriterion("create_user_id not in", values, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdBetween(String value1, String value2) {
            addCriterion("create_user_id between", value1, value2, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdNotBetween(String value1, String value2) {
            addCriterion("create_user_id not between", value1, value2, "createUserId");
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

        public Criteria andUpdateGroupIdIsNull() {
            addCriterion("update_group_id is null");
            return (Criteria) this;
        }

        public Criteria andUpdateGroupIdIsNotNull() {
            addCriterion("update_group_id is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateGroupIdEqualTo(String value) {
            addCriterion("update_group_id =", value, "updateGroupId");
            return (Criteria) this;
        }

        public Criteria andUpdateGroupIdNotEqualTo(String value) {
            addCriterion("update_group_id <>", value, "updateGroupId");
            return (Criteria) this;
        }

        public Criteria andUpdateGroupIdGreaterThan(String value) {
            addCriterion("update_group_id >", value, "updateGroupId");
            return (Criteria) this;
        }

        public Criteria andUpdateGroupIdGreaterThanOrEqualTo(String value) {
            addCriterion("update_group_id >=", value, "updateGroupId");
            return (Criteria) this;
        }

        public Criteria andUpdateGroupIdLessThan(String value) {
            addCriterion("update_group_id <", value, "updateGroupId");
            return (Criteria) this;
        }

        public Criteria andUpdateGroupIdLessThanOrEqualTo(String value) {
            addCriterion("update_group_id <=", value, "updateGroupId");
            return (Criteria) this;
        }

        public Criteria andUpdateGroupIdLike(String value) {
            addCriterion("update_group_id like", value, "updateGroupId");
            return (Criteria) this;
        }

        public Criteria andUpdateGroupIdNotLike(String value) {
            addCriterion("update_group_id not like", value, "updateGroupId");
            return (Criteria) this;
        }

        public Criteria andUpdateGroupIdIn(List<String> values) {
            addCriterion("update_group_id in", values, "updateGroupId");
            return (Criteria) this;
        }

        public Criteria andUpdateGroupIdNotIn(List<String> values) {
            addCriterion("update_group_id not in", values, "updateGroupId");
            return (Criteria) this;
        }

        public Criteria andUpdateGroupIdBetween(String value1, String value2) {
            addCriterion("update_group_id between", value1, value2, "updateGroupId");
            return (Criteria) this;
        }

        public Criteria andUpdateGroupIdNotBetween(String value1, String value2) {
            addCriterion("update_group_id not between", value1, value2, "updateGroupId");
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

        public Criteria andUpdateUserIdEqualTo(String value) {
            addCriterion("update_user_id =", value, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdNotEqualTo(String value) {
            addCriterion("update_user_id <>", value, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdGreaterThan(String value) {
            addCriterion("update_user_id >", value, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("update_user_id >=", value, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdLessThan(String value) {
            addCriterion("update_user_id <", value, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdLessThanOrEqualTo(String value) {
            addCriterion("update_user_id <=", value, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdLike(String value) {
            addCriterion("update_user_id like", value, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdNotLike(String value) {
            addCriterion("update_user_id not like", value, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdIn(List<String> values) {
            addCriterion("update_user_id in", values, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdNotIn(List<String> values) {
            addCriterion("update_user_id not in", values, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdBetween(String value1, String value2) {
            addCriterion("update_user_id between", value1, value2, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdNotBetween(String value1, String value2) {
            addCriterion("update_user_id not between", value1, value2, "updateUserId");
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

        public Criteria andIncreaseMoneyIsNull() {
            addCriterion("increase_money is null");
            return (Criteria) this;
        }

        public Criteria andIncreaseMoneyIsNotNull() {
            addCriterion("increase_money is not null");
            return (Criteria) this;
        }

        public Criteria andIncreaseMoneyEqualTo(Long value) {
            addCriterion("increase_money =", value, "increaseMoney");
            return (Criteria) this;
        }

        public Criteria andIncreaseMoneyNotEqualTo(Long value) {
            addCriterion("increase_money <>", value, "increaseMoney");
            return (Criteria) this;
        }

        public Criteria andIncreaseMoneyGreaterThan(Long value) {
            addCriterion("increase_money >", value, "increaseMoney");
            return (Criteria) this;
        }

        public Criteria andIncreaseMoneyGreaterThanOrEqualTo(Long value) {
            addCriterion("increase_money >=", value, "increaseMoney");
            return (Criteria) this;
        }

        public Criteria andIncreaseMoneyLessThan(Long value) {
            addCriterion("increase_money <", value, "increaseMoney");
            return (Criteria) this;
        }

        public Criteria andIncreaseMoneyLessThanOrEqualTo(Long value) {
            addCriterion("increase_money <=", value, "increaseMoney");
            return (Criteria) this;
        }

        public Criteria andIncreaseMoneyIn(List<Long> values) {
            addCriterion("increase_money in", values, "increaseMoney");
            return (Criteria) this;
        }

        public Criteria andIncreaseMoneyNotIn(List<Long> values) {
            addCriterion("increase_money not in", values, "increaseMoney");
            return (Criteria) this;
        }

        public Criteria andIncreaseMoneyBetween(Long value1, Long value2) {
            addCriterion("increase_money between", value1, value2, "increaseMoney");
            return (Criteria) this;
        }

        public Criteria andIncreaseMoneyNotBetween(Long value1, Long value2) {
            addCriterion("increase_money not between", value1, value2, "increaseMoney");
            return (Criteria) this;
        }

        public Criteria andInterestCouponIsNull() {
            addCriterion("interest_coupon is null");
            return (Criteria) this;
        }

        public Criteria andInterestCouponIsNotNull() {
            addCriterion("interest_coupon is not null");
            return (Criteria) this;
        }

        public Criteria andInterestCouponEqualTo(Integer value) {
            addCriterion("interest_coupon =", value, "interestCoupon");
            return (Criteria) this;
        }

        public Criteria andInterestCouponNotEqualTo(Integer value) {
            addCriterion("interest_coupon <>", value, "interestCoupon");
            return (Criteria) this;
        }

        public Criteria andInterestCouponGreaterThan(Integer value) {
            addCriterion("interest_coupon >", value, "interestCoupon");
            return (Criteria) this;
        }

        public Criteria andInterestCouponGreaterThanOrEqualTo(Integer value) {
            addCriterion("interest_coupon >=", value, "interestCoupon");
            return (Criteria) this;
        }

        public Criteria andInterestCouponLessThan(Integer value) {
            addCriterion("interest_coupon <", value, "interestCoupon");
            return (Criteria) this;
        }

        public Criteria andInterestCouponLessThanOrEqualTo(Integer value) {
            addCriterion("interest_coupon <=", value, "interestCoupon");
            return (Criteria) this;
        }

        public Criteria andInterestCouponIn(List<Integer> values) {
            addCriterion("interest_coupon in", values, "interestCoupon");
            return (Criteria) this;
        }

        public Criteria andInterestCouponNotIn(List<Integer> values) {
            addCriterion("interest_coupon not in", values, "interestCoupon");
            return (Criteria) this;
        }

        public Criteria andInterestCouponBetween(Integer value1, Integer value2) {
            addCriterion("interest_coupon between", value1, value2, "interestCoupon");
            return (Criteria) this;
        }

        public Criteria andInterestCouponNotBetween(Integer value1, Integer value2) {
            addCriterion("interest_coupon not between", value1, value2, "interestCoupon");
            return (Criteria) this;
        }

        public Criteria andTasteMoneyIsNull() {
            addCriterion("taste_money is null");
            return (Criteria) this;
        }

        public Criteria andTasteMoneyIsNotNull() {
            addCriterion("taste_money is not null");
            return (Criteria) this;
        }

        public Criteria andTasteMoneyEqualTo(Integer value) {
            addCriterion("taste_money =", value, "tasteMoney");
            return (Criteria) this;
        }

        public Criteria andTasteMoneyNotEqualTo(Integer value) {
            addCriterion("taste_money <>", value, "tasteMoney");
            return (Criteria) this;
        }

        public Criteria andTasteMoneyGreaterThan(Integer value) {
            addCriterion("taste_money >", value, "tasteMoney");
            return (Criteria) this;
        }

        public Criteria andTasteMoneyGreaterThanOrEqualTo(Integer value) {
            addCriterion("taste_money >=", value, "tasteMoney");
            return (Criteria) this;
        }

        public Criteria andTasteMoneyLessThan(Integer value) {
            addCriterion("taste_money <", value, "tasteMoney");
            return (Criteria) this;
        }

        public Criteria andTasteMoneyLessThanOrEqualTo(Integer value) {
            addCriterion("taste_money <=", value, "tasteMoney");
            return (Criteria) this;
        }

        public Criteria andTasteMoneyIn(List<Integer> values) {
            addCriterion("taste_money in", values, "tasteMoney");
            return (Criteria) this;
        }

        public Criteria andTasteMoneyNotIn(List<Integer> values) {
            addCriterion("taste_money not in", values, "tasteMoney");
            return (Criteria) this;
        }

        public Criteria andTasteMoneyBetween(Integer value1, Integer value2) {
            addCriterion("taste_money between", value1, value2, "tasteMoney");
            return (Criteria) this;
        }

        public Criteria andTasteMoneyNotBetween(Integer value1, Integer value2) {
            addCriterion("taste_money not between", value1, value2, "tasteMoney");
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