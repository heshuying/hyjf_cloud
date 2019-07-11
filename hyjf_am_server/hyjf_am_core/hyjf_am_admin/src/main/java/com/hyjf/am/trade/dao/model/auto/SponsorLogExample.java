package com.hyjf.am.trade.dao.model.auto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SponsorLogExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public SponsorLogExample() {
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

        public Criteria andOldSponsorIdIsNull() {
            addCriterion("old_sponsor_id is null");
            return (Criteria) this;
        }

        public Criteria andOldSponsorIdIsNotNull() {
            addCriterion("old_sponsor_id is not null");
            return (Criteria) this;
        }

        public Criteria andOldSponsorIdEqualTo(Integer value) {
            addCriterion("old_sponsor_id =", value, "oldSponsorId");
            return (Criteria) this;
        }

        public Criteria andOldSponsorIdNotEqualTo(Integer value) {
            addCriterion("old_sponsor_id <>", value, "oldSponsorId");
            return (Criteria) this;
        }

        public Criteria andOldSponsorIdGreaterThan(Integer value) {
            addCriterion("old_sponsor_id >", value, "oldSponsorId");
            return (Criteria) this;
        }

        public Criteria andOldSponsorIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("old_sponsor_id >=", value, "oldSponsorId");
            return (Criteria) this;
        }

        public Criteria andOldSponsorIdLessThan(Integer value) {
            addCriterion("old_sponsor_id <", value, "oldSponsorId");
            return (Criteria) this;
        }

        public Criteria andOldSponsorIdLessThanOrEqualTo(Integer value) {
            addCriterion("old_sponsor_id <=", value, "oldSponsorId");
            return (Criteria) this;
        }

        public Criteria andOldSponsorIdIn(List<Integer> values) {
            addCriterion("old_sponsor_id in", values, "oldSponsorId");
            return (Criteria) this;
        }

        public Criteria andOldSponsorIdNotIn(List<Integer> values) {
            addCriterion("old_sponsor_id not in", values, "oldSponsorId");
            return (Criteria) this;
        }

        public Criteria andOldSponsorIdBetween(Integer value1, Integer value2) {
            addCriterion("old_sponsor_id between", value1, value2, "oldSponsorId");
            return (Criteria) this;
        }

        public Criteria andOldSponsorIdNotBetween(Integer value1, Integer value2) {
            addCriterion("old_sponsor_id not between", value1, value2, "oldSponsorId");
            return (Criteria) this;
        }

        public Criteria andOldSponsorIsNull() {
            addCriterion("old_sponsor is null");
            return (Criteria) this;
        }

        public Criteria andOldSponsorIsNotNull() {
            addCriterion("old_sponsor is not null");
            return (Criteria) this;
        }

        public Criteria andOldSponsorEqualTo(String value) {
            addCriterion("old_sponsor =", value, "oldSponsor");
            return (Criteria) this;
        }

        public Criteria andOldSponsorNotEqualTo(String value) {
            addCriterion("old_sponsor <>", value, "oldSponsor");
            return (Criteria) this;
        }

        public Criteria andOldSponsorGreaterThan(String value) {
            addCriterion("old_sponsor >", value, "oldSponsor");
            return (Criteria) this;
        }

        public Criteria andOldSponsorGreaterThanOrEqualTo(String value) {
            addCriterion("old_sponsor >=", value, "oldSponsor");
            return (Criteria) this;
        }

        public Criteria andOldSponsorLessThan(String value) {
            addCriterion("old_sponsor <", value, "oldSponsor");
            return (Criteria) this;
        }

        public Criteria andOldSponsorLessThanOrEqualTo(String value) {
            addCriterion("old_sponsor <=", value, "oldSponsor");
            return (Criteria) this;
        }

        public Criteria andOldSponsorLike(String value) {
            addCriterion("old_sponsor like", value, "oldSponsor");
            return (Criteria) this;
        }

        public Criteria andOldSponsorNotLike(String value) {
            addCriterion("old_sponsor not like", value, "oldSponsor");
            return (Criteria) this;
        }

        public Criteria andOldSponsorIn(List<String> values) {
            addCriterion("old_sponsor in", values, "oldSponsor");
            return (Criteria) this;
        }

        public Criteria andOldSponsorNotIn(List<String> values) {
            addCriterion("old_sponsor not in", values, "oldSponsor");
            return (Criteria) this;
        }

        public Criteria andOldSponsorBetween(String value1, String value2) {
            addCriterion("old_sponsor between", value1, value2, "oldSponsor");
            return (Criteria) this;
        }

        public Criteria andOldSponsorNotBetween(String value1, String value2) {
            addCriterion("old_sponsor not between", value1, value2, "oldSponsor");
            return (Criteria) this;
        }

        public Criteria andNewSponsorIdIsNull() {
            addCriterion("new_sponsor_id is null");
            return (Criteria) this;
        }

        public Criteria andNewSponsorIdIsNotNull() {
            addCriterion("new_sponsor_id is not null");
            return (Criteria) this;
        }

        public Criteria andNewSponsorIdEqualTo(Integer value) {
            addCriterion("new_sponsor_id =", value, "newSponsorId");
            return (Criteria) this;
        }

        public Criteria andNewSponsorIdNotEqualTo(Integer value) {
            addCriterion("new_sponsor_id <>", value, "newSponsorId");
            return (Criteria) this;
        }

        public Criteria andNewSponsorIdGreaterThan(Integer value) {
            addCriterion("new_sponsor_id >", value, "newSponsorId");
            return (Criteria) this;
        }

        public Criteria andNewSponsorIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("new_sponsor_id >=", value, "newSponsorId");
            return (Criteria) this;
        }

        public Criteria andNewSponsorIdLessThan(Integer value) {
            addCriterion("new_sponsor_id <", value, "newSponsorId");
            return (Criteria) this;
        }

        public Criteria andNewSponsorIdLessThanOrEqualTo(Integer value) {
            addCriterion("new_sponsor_id <=", value, "newSponsorId");
            return (Criteria) this;
        }

        public Criteria andNewSponsorIdIn(List<Integer> values) {
            addCriterion("new_sponsor_id in", values, "newSponsorId");
            return (Criteria) this;
        }

        public Criteria andNewSponsorIdNotIn(List<Integer> values) {
            addCriterion("new_sponsor_id not in", values, "newSponsorId");
            return (Criteria) this;
        }

        public Criteria andNewSponsorIdBetween(Integer value1, Integer value2) {
            addCriterion("new_sponsor_id between", value1, value2, "newSponsorId");
            return (Criteria) this;
        }

        public Criteria andNewSponsorIdNotBetween(Integer value1, Integer value2) {
            addCriterion("new_sponsor_id not between", value1, value2, "newSponsorId");
            return (Criteria) this;
        }

        public Criteria andNewSponsorIsNull() {
            addCriterion("new_sponsor is null");
            return (Criteria) this;
        }

        public Criteria andNewSponsorIsNotNull() {
            addCriterion("new_sponsor is not null");
            return (Criteria) this;
        }

        public Criteria andNewSponsorEqualTo(String value) {
            addCriterion("new_sponsor =", value, "newSponsor");
            return (Criteria) this;
        }

        public Criteria andNewSponsorNotEqualTo(String value) {
            addCriterion("new_sponsor <>", value, "newSponsor");
            return (Criteria) this;
        }

        public Criteria andNewSponsorGreaterThan(String value) {
            addCriterion("new_sponsor >", value, "newSponsor");
            return (Criteria) this;
        }

        public Criteria andNewSponsorGreaterThanOrEqualTo(String value) {
            addCriterion("new_sponsor >=", value, "newSponsor");
            return (Criteria) this;
        }

        public Criteria andNewSponsorLessThan(String value) {
            addCriterion("new_sponsor <", value, "newSponsor");
            return (Criteria) this;
        }

        public Criteria andNewSponsorLessThanOrEqualTo(String value) {
            addCriterion("new_sponsor <=", value, "newSponsor");
            return (Criteria) this;
        }

        public Criteria andNewSponsorLike(String value) {
            addCriterion("new_sponsor like", value, "newSponsor");
            return (Criteria) this;
        }

        public Criteria andNewSponsorNotLike(String value) {
            addCriterion("new_sponsor not like", value, "newSponsor");
            return (Criteria) this;
        }

        public Criteria andNewSponsorIn(List<String> values) {
            addCriterion("new_sponsor in", values, "newSponsor");
            return (Criteria) this;
        }

        public Criteria andNewSponsorNotIn(List<String> values) {
            addCriterion("new_sponsor not in", values, "newSponsor");
            return (Criteria) this;
        }

        public Criteria andNewSponsorBetween(String value1, String value2) {
            addCriterion("new_sponsor between", value1, value2, "newSponsor");
            return (Criteria) this;
        }

        public Criteria andNewSponsorNotBetween(String value1, String value2) {
            addCriterion("new_sponsor not between", value1, value2, "newSponsor");
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