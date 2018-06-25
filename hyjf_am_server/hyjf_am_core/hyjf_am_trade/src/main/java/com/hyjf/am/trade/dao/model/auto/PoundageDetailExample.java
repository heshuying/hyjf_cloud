package com.hyjf.am.trade.dao.model.auto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PoundageDetailExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public PoundageDetailExample() {
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

        public Criteria andAddTimeIsNull() {
            addCriterion("add_time is null");
            return (Criteria) this;
        }

        public Criteria andAddTimeIsNotNull() {
            addCriterion("add_time is not null");
            return (Criteria) this;
        }

        public Criteria andAddTimeEqualTo(Date value) {
            addCriterion("add_time =", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeNotEqualTo(Date value) {
            addCriterion("add_time <>", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeGreaterThan(Date value) {
            addCriterion("add_time >", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("add_time >=", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeLessThan(Date value) {
            addCriterion("add_time <", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeLessThanOrEqualTo(Date value) {
            addCriterion("add_time <=", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeIn(List<Date> values) {
            addCriterion("add_time in", values, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeNotIn(List<Date> values) {
            addCriterion("add_time not in", values, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeBetween(Date value1, Date value2) {
            addCriterion("add_time between", value1, value2, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeNotBetween(Date value1, Date value2) {
            addCriterion("add_time not between", value1, value2, "addTime");
            return (Criteria) this;
        }

        public Criteria andUsernnameIsNull() {
            addCriterion("usernname is null");
            return (Criteria) this;
        }

        public Criteria andUsernnameIsNotNull() {
            addCriterion("usernname is not null");
            return (Criteria) this;
        }

        public Criteria andUsernnameEqualTo(String value) {
            addCriterion("usernname =", value, "usernname");
            return (Criteria) this;
        }

        public Criteria andUsernnameNotEqualTo(String value) {
            addCriterion("usernname <>", value, "usernname");
            return (Criteria) this;
        }

        public Criteria andUsernnameGreaterThan(String value) {
            addCriterion("usernname >", value, "usernname");
            return (Criteria) this;
        }

        public Criteria andUsernnameGreaterThanOrEqualTo(String value) {
            addCriterion("usernname >=", value, "usernname");
            return (Criteria) this;
        }

        public Criteria andUsernnameLessThan(String value) {
            addCriterion("usernname <", value, "usernname");
            return (Criteria) this;
        }

        public Criteria andUsernnameLessThanOrEqualTo(String value) {
            addCriterion("usernname <=", value, "usernname");
            return (Criteria) this;
        }

        public Criteria andUsernnameLike(String value) {
            addCriterion("usernname like", value, "usernname");
            return (Criteria) this;
        }

        public Criteria andUsernnameNotLike(String value) {
            addCriterion("usernname not like", value, "usernname");
            return (Criteria) this;
        }

        public Criteria andUsernnameIn(List<String> values) {
            addCriterion("usernname in", values, "usernname");
            return (Criteria) this;
        }

        public Criteria andUsernnameNotIn(List<String> values) {
            addCriterion("usernname not in", values, "usernname");
            return (Criteria) this;
        }

        public Criteria andUsernnameBetween(String value1, String value2) {
            addCriterion("usernname between", value1, value2, "usernname");
            return (Criteria) this;
        }

        public Criteria andUsernnameNotBetween(String value1, String value2) {
            addCriterion("usernname not between", value1, value2, "usernname");
            return (Criteria) this;
        }

        public Criteria andPoundageTypeIsNull() {
            addCriterion("poundage_type is null");
            return (Criteria) this;
        }

        public Criteria andPoundageTypeIsNotNull() {
            addCriterion("poundage_type is not null");
            return (Criteria) this;
        }

        public Criteria andPoundageTypeEqualTo(String value) {
            addCriterion("poundage_type =", value, "poundageType");
            return (Criteria) this;
        }

        public Criteria andPoundageTypeNotEqualTo(String value) {
            addCriterion("poundage_type <>", value, "poundageType");
            return (Criteria) this;
        }

        public Criteria andPoundageTypeGreaterThan(String value) {
            addCriterion("poundage_type >", value, "poundageType");
            return (Criteria) this;
        }

        public Criteria andPoundageTypeGreaterThanOrEqualTo(String value) {
            addCriterion("poundage_type >=", value, "poundageType");
            return (Criteria) this;
        }

        public Criteria andPoundageTypeLessThan(String value) {
            addCriterion("poundage_type <", value, "poundageType");
            return (Criteria) this;
        }

        public Criteria andPoundageTypeLessThanOrEqualTo(String value) {
            addCriterion("poundage_type <=", value, "poundageType");
            return (Criteria) this;
        }

        public Criteria andPoundageTypeLike(String value) {
            addCriterion("poundage_type like", value, "poundageType");
            return (Criteria) this;
        }

        public Criteria andPoundageTypeNotLike(String value) {
            addCriterion("poundage_type not like", value, "poundageType");
            return (Criteria) this;
        }

        public Criteria andPoundageTypeIn(List<String> values) {
            addCriterion("poundage_type in", values, "poundageType");
            return (Criteria) this;
        }

        public Criteria andPoundageTypeNotIn(List<String> values) {
            addCriterion("poundage_type not in", values, "poundageType");
            return (Criteria) this;
        }

        public Criteria andPoundageTypeBetween(String value1, String value2) {
            addCriterion("poundage_type between", value1, value2, "poundageType");
            return (Criteria) this;
        }

        public Criteria andPoundageTypeNotBetween(String value1, String value2) {
            addCriterion("poundage_type not between", value1, value2, "poundageType");
            return (Criteria) this;
        }

        public Criteria andSourceIsNull() {
            addCriterion("`source` is null");
            return (Criteria) this;
        }

        public Criteria andSourceIsNotNull() {
            addCriterion("`source` is not null");
            return (Criteria) this;
        }

        public Criteria andSourceEqualTo(String value) {
            addCriterion("`source` =", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceNotEqualTo(String value) {
            addCriterion("`source` <>", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceGreaterThan(String value) {
            addCriterion("`source` >", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceGreaterThanOrEqualTo(String value) {
            addCriterion("`source` >=", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceLessThan(String value) {
            addCriterion("`source` <", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceLessThanOrEqualTo(String value) {
            addCriterion("`source` <=", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceLike(String value) {
            addCriterion("`source` like", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceNotLike(String value) {
            addCriterion("`source` not like", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceIn(List<String> values) {
            addCriterion("`source` in", values, "source");
            return (Criteria) this;
        }

        public Criteria andSourceNotIn(List<String> values) {
            addCriterion("`source` not in", values, "source");
            return (Criteria) this;
        }

        public Criteria andSourceBetween(String value1, String value2) {
            addCriterion("`source` between", value1, value2, "source");
            return (Criteria) this;
        }

        public Criteria andSourceNotBetween(String value1, String value2) {
            addCriterion("`source` not between", value1, value2, "source");
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

        public Criteria andLedgerTimeIsNull() {
            addCriterion("ledger_time is null");
            return (Criteria) this;
        }

        public Criteria andLedgerTimeIsNotNull() {
            addCriterion("ledger_time is not null");
            return (Criteria) this;
        }

        public Criteria andLedgerTimeEqualTo(Integer value) {
            addCriterion("ledger_time =", value, "ledgerTime");
            return (Criteria) this;
        }

        public Criteria andLedgerTimeNotEqualTo(Integer value) {
            addCriterion("ledger_time <>", value, "ledgerTime");
            return (Criteria) this;
        }

        public Criteria andLedgerTimeGreaterThan(Integer value) {
            addCriterion("ledger_time >", value, "ledgerTime");
            return (Criteria) this;
        }

        public Criteria andLedgerTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("ledger_time >=", value, "ledgerTime");
            return (Criteria) this;
        }

        public Criteria andLedgerTimeLessThan(Integer value) {
            addCriterion("ledger_time <", value, "ledgerTime");
            return (Criteria) this;
        }

        public Criteria andLedgerTimeLessThanOrEqualTo(Integer value) {
            addCriterion("ledger_time <=", value, "ledgerTime");
            return (Criteria) this;
        }

        public Criteria andLedgerTimeIn(List<Integer> values) {
            addCriterion("ledger_time in", values, "ledgerTime");
            return (Criteria) this;
        }

        public Criteria andLedgerTimeNotIn(List<Integer> values) {
            addCriterion("ledger_time not in", values, "ledgerTime");
            return (Criteria) this;
        }

        public Criteria andLedgerTimeBetween(Integer value1, Integer value2) {
            addCriterion("ledger_time between", value1, value2, "ledgerTime");
            return (Criteria) this;
        }

        public Criteria andLedgerTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("ledger_time not between", value1, value2, "ledgerTime");
            return (Criteria) this;
        }

        public Criteria andLedgerIdIsNull() {
            addCriterion("ledger_id is null");
            return (Criteria) this;
        }

        public Criteria andLedgerIdIsNotNull() {
            addCriterion("ledger_id is not null");
            return (Criteria) this;
        }

        public Criteria andLedgerIdEqualTo(Integer value) {
            addCriterion("ledger_id =", value, "ledgerId");
            return (Criteria) this;
        }

        public Criteria andLedgerIdNotEqualTo(Integer value) {
            addCriterion("ledger_id <>", value, "ledgerId");
            return (Criteria) this;
        }

        public Criteria andLedgerIdGreaterThan(Integer value) {
            addCriterion("ledger_id >", value, "ledgerId");
            return (Criteria) this;
        }

        public Criteria andLedgerIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("ledger_id >=", value, "ledgerId");
            return (Criteria) this;
        }

        public Criteria andLedgerIdLessThan(Integer value) {
            addCriterion("ledger_id <", value, "ledgerId");
            return (Criteria) this;
        }

        public Criteria andLedgerIdLessThanOrEqualTo(Integer value) {
            addCriterion("ledger_id <=", value, "ledgerId");
            return (Criteria) this;
        }

        public Criteria andLedgerIdIn(List<Integer> values) {
            addCriterion("ledger_id in", values, "ledgerId");
            return (Criteria) this;
        }

        public Criteria andLedgerIdNotIn(List<Integer> values) {
            addCriterion("ledger_id not in", values, "ledgerId");
            return (Criteria) this;
        }

        public Criteria andLedgerIdBetween(Integer value1, Integer value2) {
            addCriterion("ledger_id between", value1, value2, "ledgerId");
            return (Criteria) this;
        }

        public Criteria andLedgerIdNotBetween(Integer value1, Integer value2) {
            addCriterion("ledger_id not between", value1, value2, "ledgerId");
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