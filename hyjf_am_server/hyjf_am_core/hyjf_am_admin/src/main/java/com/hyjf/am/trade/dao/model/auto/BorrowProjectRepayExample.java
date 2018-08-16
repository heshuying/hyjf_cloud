package com.hyjf.am.trade.dao.model.auto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BorrowProjectRepayExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public BorrowProjectRepayExample() {
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

        public Criteria andRepayMethodIsNull() {
            addCriterion("repay_method is null");
            return (Criteria) this;
        }

        public Criteria andRepayMethodIsNotNull() {
            addCriterion("repay_method is not null");
            return (Criteria) this;
        }

        public Criteria andRepayMethodEqualTo(String value) {
            addCriterion("repay_method =", value, "repayMethod");
            return (Criteria) this;
        }

        public Criteria andRepayMethodNotEqualTo(String value) {
            addCriterion("repay_method <>", value, "repayMethod");
            return (Criteria) this;
        }

        public Criteria andRepayMethodGreaterThan(String value) {
            addCriterion("repay_method >", value, "repayMethod");
            return (Criteria) this;
        }

        public Criteria andRepayMethodGreaterThanOrEqualTo(String value) {
            addCriterion("repay_method >=", value, "repayMethod");
            return (Criteria) this;
        }

        public Criteria andRepayMethodLessThan(String value) {
            addCriterion("repay_method <", value, "repayMethod");
            return (Criteria) this;
        }

        public Criteria andRepayMethodLessThanOrEqualTo(String value) {
            addCriterion("repay_method <=", value, "repayMethod");
            return (Criteria) this;
        }

        public Criteria andRepayMethodLike(String value) {
            addCriterion("repay_method like", value, "repayMethod");
            return (Criteria) this;
        }

        public Criteria andRepayMethodNotLike(String value) {
            addCriterion("repay_method not like", value, "repayMethod");
            return (Criteria) this;
        }

        public Criteria andRepayMethodIn(List<String> values) {
            addCriterion("repay_method in", values, "repayMethod");
            return (Criteria) this;
        }

        public Criteria andRepayMethodNotIn(List<String> values) {
            addCriterion("repay_method not in", values, "repayMethod");
            return (Criteria) this;
        }

        public Criteria andRepayMethodBetween(String value1, String value2) {
            addCriterion("repay_method between", value1, value2, "repayMethod");
            return (Criteria) this;
        }

        public Criteria andRepayMethodNotBetween(String value1, String value2) {
            addCriterion("repay_method not between", value1, value2, "repayMethod");
            return (Criteria) this;
        }

        public Criteria andMethodNameIsNull() {
            addCriterion("method_name is null");
            return (Criteria) this;
        }

        public Criteria andMethodNameIsNotNull() {
            addCriterion("method_name is not null");
            return (Criteria) this;
        }

        public Criteria andMethodNameEqualTo(String value) {
            addCriterion("method_name =", value, "methodName");
            return (Criteria) this;
        }

        public Criteria andMethodNameNotEqualTo(String value) {
            addCriterion("method_name <>", value, "methodName");
            return (Criteria) this;
        }

        public Criteria andMethodNameGreaterThan(String value) {
            addCriterion("method_name >", value, "methodName");
            return (Criteria) this;
        }

        public Criteria andMethodNameGreaterThanOrEqualTo(String value) {
            addCriterion("method_name >=", value, "methodName");
            return (Criteria) this;
        }

        public Criteria andMethodNameLessThan(String value) {
            addCriterion("method_name <", value, "methodName");
            return (Criteria) this;
        }

        public Criteria andMethodNameLessThanOrEqualTo(String value) {
            addCriterion("method_name <=", value, "methodName");
            return (Criteria) this;
        }

        public Criteria andMethodNameLike(String value) {
            addCriterion("method_name like", value, "methodName");
            return (Criteria) this;
        }

        public Criteria andMethodNameNotLike(String value) {
            addCriterion("method_name not like", value, "methodName");
            return (Criteria) this;
        }

        public Criteria andMethodNameIn(List<String> values) {
            addCriterion("method_name in", values, "methodName");
            return (Criteria) this;
        }

        public Criteria andMethodNameNotIn(List<String> values) {
            addCriterion("method_name not in", values, "methodName");
            return (Criteria) this;
        }

        public Criteria andMethodNameBetween(String value1, String value2) {
            addCriterion("method_name between", value1, value2, "methodName");
            return (Criteria) this;
        }

        public Criteria andMethodNameNotBetween(String value1, String value2) {
            addCriterion("method_name not between", value1, value2, "methodName");
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