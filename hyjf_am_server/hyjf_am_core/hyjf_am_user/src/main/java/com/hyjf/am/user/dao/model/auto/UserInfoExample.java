package com.hyjf.am.user.dao.model.auto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public UserInfoExample() {
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

        public Criteria andRoleIdIsNull() {
            addCriterion("role_id is null");
            return (Criteria) this;
        }

        public Criteria andRoleIdIsNotNull() {
            addCriterion("role_id is not null");
            return (Criteria) this;
        }

        public Criteria andRoleIdEqualTo(Integer value) {
            addCriterion("role_id =", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdNotEqualTo(Integer value) {
            addCriterion("role_id <>", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdGreaterThan(Integer value) {
            addCriterion("role_id >", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("role_id >=", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdLessThan(Integer value) {
            addCriterion("role_id <", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdLessThanOrEqualTo(Integer value) {
            addCriterion("role_id <=", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdIn(List<Integer> values) {
            addCriterion("role_id in", values, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdNotIn(List<Integer> values) {
            addCriterion("role_id not in", values, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdBetween(Integer value1, Integer value2) {
            addCriterion("role_id between", value1, value2, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdNotBetween(Integer value1, Integer value2) {
            addCriterion("role_id not between", value1, value2, "roleId");
            return (Criteria) this;
        }

        public Criteria andIsStaffIsNull() {
            addCriterion("is_staff is null");
            return (Criteria) this;
        }

        public Criteria andIsStaffIsNotNull() {
            addCriterion("is_staff is not null");
            return (Criteria) this;
        }

        public Criteria andIsStaffEqualTo(Integer value) {
            addCriterion("is_staff =", value, "isStaff");
            return (Criteria) this;
        }

        public Criteria andIsStaffNotEqualTo(Integer value) {
            addCriterion("is_staff <>", value, "isStaff");
            return (Criteria) this;
        }

        public Criteria andIsStaffGreaterThan(Integer value) {
            addCriterion("is_staff >", value, "isStaff");
            return (Criteria) this;
        }

        public Criteria andIsStaffGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_staff >=", value, "isStaff");
            return (Criteria) this;
        }

        public Criteria andIsStaffLessThan(Integer value) {
            addCriterion("is_staff <", value, "isStaff");
            return (Criteria) this;
        }

        public Criteria andIsStaffLessThanOrEqualTo(Integer value) {
            addCriterion("is_staff <=", value, "isStaff");
            return (Criteria) this;
        }

        public Criteria andIsStaffIn(List<Integer> values) {
            addCriterion("is_staff in", values, "isStaff");
            return (Criteria) this;
        }

        public Criteria andIsStaffNotIn(List<Integer> values) {
            addCriterion("is_staff not in", values, "isStaff");
            return (Criteria) this;
        }

        public Criteria andIsStaffBetween(Integer value1, Integer value2) {
            addCriterion("is_staff between", value1, value2, "isStaff");
            return (Criteria) this;
        }

        public Criteria andIsStaffNotBetween(Integer value1, Integer value2) {
            addCriterion("is_staff not between", value1, value2, "isStaff");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdIsNull() {
            addCriterion("department_id is null");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdIsNotNull() {
            addCriterion("department_id is not null");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdEqualTo(Integer value) {
            addCriterion("department_id =", value, "departmentId");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdNotEqualTo(Integer value) {
            addCriterion("department_id <>", value, "departmentId");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdGreaterThan(Integer value) {
            addCriterion("department_id >", value, "departmentId");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("department_id >=", value, "departmentId");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdLessThan(Integer value) {
            addCriterion("department_id <", value, "departmentId");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdLessThanOrEqualTo(Integer value) {
            addCriterion("department_id <=", value, "departmentId");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdIn(List<Integer> values) {
            addCriterion("department_id in", values, "departmentId");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdNotIn(List<Integer> values) {
            addCriterion("department_id not in", values, "departmentId");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdBetween(Integer value1, Integer value2) {
            addCriterion("department_id between", value1, value2, "departmentId");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdNotBetween(Integer value1, Integer value2) {
            addCriterion("department_id not between", value1, value2, "departmentId");
            return (Criteria) this;
        }

        public Criteria andParentIdIsNull() {
            addCriterion("parent_id is null");
            return (Criteria) this;
        }

        public Criteria andParentIdIsNotNull() {
            addCriterion("parent_id is not null");
            return (Criteria) this;
        }

        public Criteria andParentIdEqualTo(Integer value) {
            addCriterion("parent_id =", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdNotEqualTo(Integer value) {
            addCriterion("parent_id <>", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdGreaterThan(Integer value) {
            addCriterion("parent_id >", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("parent_id >=", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdLessThan(Integer value) {
            addCriterion("parent_id <", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdLessThanOrEqualTo(Integer value) {
            addCriterion("parent_id <=", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdIn(List<Integer> values) {
            addCriterion("parent_id in", values, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdNotIn(List<Integer> values) {
            addCriterion("parent_id not in", values, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdBetween(Integer value1, Integer value2) {
            addCriterion("parent_id between", value1, value2, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdNotBetween(Integer value1, Integer value2) {
            addCriterion("parent_id not between", value1, value2, "parentId");
            return (Criteria) this;
        }

        public Criteria andIs51IsNull() {
            addCriterion("is51 is null");
            return (Criteria) this;
        }

        public Criteria andIs51IsNotNull() {
            addCriterion("is51 is not null");
            return (Criteria) this;
        }

        public Criteria andIs51EqualTo(Integer value) {
            addCriterion("is51 =", value, "is51");
            return (Criteria) this;
        }

        public Criteria andIs51NotEqualTo(Integer value) {
            addCriterion("is51 <>", value, "is51");
            return (Criteria) this;
        }

        public Criteria andIs51GreaterThan(Integer value) {
            addCriterion("is51 >", value, "is51");
            return (Criteria) this;
        }

        public Criteria andIs51GreaterThanOrEqualTo(Integer value) {
            addCriterion("is51 >=", value, "is51");
            return (Criteria) this;
        }

        public Criteria andIs51LessThan(Integer value) {
            addCriterion("is51 <", value, "is51");
            return (Criteria) this;
        }

        public Criteria andIs51LessThanOrEqualTo(Integer value) {
            addCriterion("is51 <=", value, "is51");
            return (Criteria) this;
        }

        public Criteria andIs51In(List<Integer> values) {
            addCriterion("is51 in", values, "is51");
            return (Criteria) this;
        }

        public Criteria andIs51NotIn(List<Integer> values) {
            addCriterion("is51 not in", values, "is51");
            return (Criteria) this;
        }

        public Criteria andIs51Between(Integer value1, Integer value2) {
            addCriterion("is51 between", value1, value2, "is51");
            return (Criteria) this;
        }

        public Criteria andIs51NotBetween(Integer value1, Integer value2) {
            addCriterion("is51 not between", value1, value2, "is51");
            return (Criteria) this;
        }

        public Criteria andNicknameIsNull() {
            addCriterion("nickname is null");
            return (Criteria) this;
        }

        public Criteria andNicknameIsNotNull() {
            addCriterion("nickname is not null");
            return (Criteria) this;
        }

        public Criteria andNicknameEqualTo(String value) {
            addCriterion("nickname =", value, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameNotEqualTo(String value) {
            addCriterion("nickname <>", value, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameGreaterThan(String value) {
            addCriterion("nickname >", value, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameGreaterThanOrEqualTo(String value) {
            addCriterion("nickname >=", value, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameLessThan(String value) {
            addCriterion("nickname <", value, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameLessThanOrEqualTo(String value) {
            addCriterion("nickname <=", value, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameLike(String value) {
            addCriterion("nickname like", value, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameNotLike(String value) {
            addCriterion("nickname not like", value, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameIn(List<String> values) {
            addCriterion("nickname in", values, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameNotIn(List<String> values) {
            addCriterion("nickname not in", values, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameBetween(String value1, String value2) {
            addCriterion("nickname between", value1, value2, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameNotBetween(String value1, String value2) {
            addCriterion("nickname not between", value1, value2, "nickname");
            return (Criteria) this;
        }

        public Criteria andBirthdayIsNull() {
            addCriterion("birthday is null");
            return (Criteria) this;
        }

        public Criteria andBirthdayIsNotNull() {
            addCriterion("birthday is not null");
            return (Criteria) this;
        }

        public Criteria andBirthdayEqualTo(String value) {
            addCriterion("birthday =", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayNotEqualTo(String value) {
            addCriterion("birthday <>", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayGreaterThan(String value) {
            addCriterion("birthday >", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayGreaterThanOrEqualTo(String value) {
            addCriterion("birthday >=", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayLessThan(String value) {
            addCriterion("birthday <", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayLessThanOrEqualTo(String value) {
            addCriterion("birthday <=", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayLike(String value) {
            addCriterion("birthday like", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayNotLike(String value) {
            addCriterion("birthday not like", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayIn(List<String> values) {
            addCriterion("birthday in", values, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayNotIn(List<String> values) {
            addCriterion("birthday not in", values, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayBetween(String value1, String value2) {
            addCriterion("birthday between", value1, value2, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayNotBetween(String value1, String value2) {
            addCriterion("birthday not between", value1, value2, "birthday");
            return (Criteria) this;
        }

        public Criteria andSexIsNull() {
            addCriterion("sex is null");
            return (Criteria) this;
        }

        public Criteria andSexIsNotNull() {
            addCriterion("sex is not null");
            return (Criteria) this;
        }

        public Criteria andSexEqualTo(Integer value) {
            addCriterion("sex =", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotEqualTo(Integer value) {
            addCriterion("sex <>", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexGreaterThan(Integer value) {
            addCriterion("sex >", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexGreaterThanOrEqualTo(Integer value) {
            addCriterion("sex >=", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexLessThan(Integer value) {
            addCriterion("sex <", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexLessThanOrEqualTo(Integer value) {
            addCriterion("sex <=", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexIn(List<Integer> values) {
            addCriterion("sex in", values, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotIn(List<Integer> values) {
            addCriterion("sex not in", values, "sex");
            return (Criteria) this;
        }

        public Criteria andSexBetween(Integer value1, Integer value2) {
            addCriterion("sex between", value1, value2, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotBetween(Integer value1, Integer value2) {
            addCriterion("sex not between", value1, value2, "sex");
            return (Criteria) this;
        }

        public Criteria andTruenameIsNull() {
            addCriterion("truename is null");
            return (Criteria) this;
        }

        public Criteria andTruenameIsNotNull() {
            addCriterion("truename is not null");
            return (Criteria) this;
        }

        public Criteria andTruenameEqualTo(String value) {
            addCriterion("truename =", value, "truename");
            return (Criteria) this;
        }

        public Criteria andTruenameNotEqualTo(String value) {
            addCriterion("truename <>", value, "truename");
            return (Criteria) this;
        }

        public Criteria andTruenameGreaterThan(String value) {
            addCriterion("truename >", value, "truename");
            return (Criteria) this;
        }

        public Criteria andTruenameGreaterThanOrEqualTo(String value) {
            addCriterion("truename >=", value, "truename");
            return (Criteria) this;
        }

        public Criteria andTruenameLessThan(String value) {
            addCriterion("truename <", value, "truename");
            return (Criteria) this;
        }

        public Criteria andTruenameLessThanOrEqualTo(String value) {
            addCriterion("truename <=", value, "truename");
            return (Criteria) this;
        }

        public Criteria andTruenameLike(String value) {
            addCriterion("truename like", value, "truename");
            return (Criteria) this;
        }

        public Criteria andTruenameNotLike(String value) {
            addCriterion("truename not like", value, "truename");
            return (Criteria) this;
        }

        public Criteria andTruenameIn(List<String> values) {
            addCriterion("truename in", values, "truename");
            return (Criteria) this;
        }

        public Criteria andTruenameNotIn(List<String> values) {
            addCriterion("truename not in", values, "truename");
            return (Criteria) this;
        }

        public Criteria andTruenameBetween(String value1, String value2) {
            addCriterion("truename between", value1, value2, "truename");
            return (Criteria) this;
        }

        public Criteria andTruenameNotBetween(String value1, String value2) {
            addCriterion("truename not between", value1, value2, "truename");
            return (Criteria) this;
        }

        public Criteria andIdcardIsNull() {
            addCriterion("idcard is null");
            return (Criteria) this;
        }

        public Criteria andIdcardIsNotNull() {
            addCriterion("idcard is not null");
            return (Criteria) this;
        }

        public Criteria andIdcardEqualTo(String value) {
            addCriterion("idcard =", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardNotEqualTo(String value) {
            addCriterion("idcard <>", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardGreaterThan(String value) {
            addCriterion("idcard >", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardGreaterThanOrEqualTo(String value) {
            addCriterion("idcard >=", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardLessThan(String value) {
            addCriterion("idcard <", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardLessThanOrEqualTo(String value) {
            addCriterion("idcard <=", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardLike(String value) {
            addCriterion("idcard like", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardNotLike(String value) {
            addCriterion("idcard not like", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardIn(List<String> values) {
            addCriterion("idcard in", values, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardNotIn(List<String> values) {
            addCriterion("idcard not in", values, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardBetween(String value1, String value2) {
            addCriterion("idcard between", value1, value2, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardNotBetween(String value1, String value2) {
            addCriterion("idcard not between", value1, value2, "idcard");
            return (Criteria) this;
        }

        public Criteria andTruenameIsapproveIsNull() {
            addCriterion("truename_isApprove is null");
            return (Criteria) this;
        }

        public Criteria andTruenameIsapproveIsNotNull() {
            addCriterion("truename_isApprove is not null");
            return (Criteria) this;
        }

        public Criteria andTruenameIsapproveEqualTo(Integer value) {
            addCriterion("truename_isApprove =", value, "truenameIsapprove");
            return (Criteria) this;
        }

        public Criteria andTruenameIsapproveNotEqualTo(Integer value) {
            addCriterion("truename_isApprove <>", value, "truenameIsapprove");
            return (Criteria) this;
        }

        public Criteria andTruenameIsapproveGreaterThan(Integer value) {
            addCriterion("truename_isApprove >", value, "truenameIsapprove");
            return (Criteria) this;
        }

        public Criteria andTruenameIsapproveGreaterThanOrEqualTo(Integer value) {
            addCriterion("truename_isApprove >=", value, "truenameIsapprove");
            return (Criteria) this;
        }

        public Criteria andTruenameIsapproveLessThan(Integer value) {
            addCriterion("truename_isApprove <", value, "truenameIsapprove");
            return (Criteria) this;
        }

        public Criteria andTruenameIsapproveLessThanOrEqualTo(Integer value) {
            addCriterion("truename_isApprove <=", value, "truenameIsapprove");
            return (Criteria) this;
        }

        public Criteria andTruenameIsapproveIn(List<Integer> values) {
            addCriterion("truename_isApprove in", values, "truenameIsapprove");
            return (Criteria) this;
        }

        public Criteria andTruenameIsapproveNotIn(List<Integer> values) {
            addCriterion("truename_isApprove not in", values, "truenameIsapprove");
            return (Criteria) this;
        }

        public Criteria andTruenameIsapproveBetween(Integer value1, Integer value2) {
            addCriterion("truename_isApprove between", value1, value2, "truenameIsapprove");
            return (Criteria) this;
        }

        public Criteria andTruenameIsapproveNotBetween(Integer value1, Integer value2) {
            addCriterion("truename_isApprove not between", value1, value2, "truenameIsapprove");
            return (Criteria) this;
        }

        public Criteria andMobileIsapproveIsNull() {
            addCriterion("mobile_isApprove is null");
            return (Criteria) this;
        }

        public Criteria andMobileIsapproveIsNotNull() {
            addCriterion("mobile_isApprove is not null");
            return (Criteria) this;
        }

        public Criteria andMobileIsapproveEqualTo(Integer value) {
            addCriterion("mobile_isApprove =", value, "mobileIsapprove");
            return (Criteria) this;
        }

        public Criteria andMobileIsapproveNotEqualTo(Integer value) {
            addCriterion("mobile_isApprove <>", value, "mobileIsapprove");
            return (Criteria) this;
        }

        public Criteria andMobileIsapproveGreaterThan(Integer value) {
            addCriterion("mobile_isApprove >", value, "mobileIsapprove");
            return (Criteria) this;
        }

        public Criteria andMobileIsapproveGreaterThanOrEqualTo(Integer value) {
            addCriterion("mobile_isApprove >=", value, "mobileIsapprove");
            return (Criteria) this;
        }

        public Criteria andMobileIsapproveLessThan(Integer value) {
            addCriterion("mobile_isApprove <", value, "mobileIsapprove");
            return (Criteria) this;
        }

        public Criteria andMobileIsapproveLessThanOrEqualTo(Integer value) {
            addCriterion("mobile_isApprove <=", value, "mobileIsapprove");
            return (Criteria) this;
        }

        public Criteria andMobileIsapproveIn(List<Integer> values) {
            addCriterion("mobile_isApprove in", values, "mobileIsapprove");
            return (Criteria) this;
        }

        public Criteria andMobileIsapproveNotIn(List<Integer> values) {
            addCriterion("mobile_isApprove not in", values, "mobileIsapprove");
            return (Criteria) this;
        }

        public Criteria andMobileIsapproveBetween(Integer value1, Integer value2) {
            addCriterion("mobile_isApprove between", value1, value2, "mobileIsapprove");
            return (Criteria) this;
        }

        public Criteria andMobileIsapproveNotBetween(Integer value1, Integer value2) {
            addCriterion("mobile_isApprove not between", value1, value2, "mobileIsapprove");
            return (Criteria) this;
        }

        public Criteria andEmailIsapproveIsNull() {
            addCriterion("email_isApprove is null");
            return (Criteria) this;
        }

        public Criteria andEmailIsapproveIsNotNull() {
            addCriterion("email_isApprove is not null");
            return (Criteria) this;
        }

        public Criteria andEmailIsapproveEqualTo(Integer value) {
            addCriterion("email_isApprove =", value, "emailIsapprove");
            return (Criteria) this;
        }

        public Criteria andEmailIsapproveNotEqualTo(Integer value) {
            addCriterion("email_isApprove <>", value, "emailIsapprove");
            return (Criteria) this;
        }

        public Criteria andEmailIsapproveGreaterThan(Integer value) {
            addCriterion("email_isApprove >", value, "emailIsapprove");
            return (Criteria) this;
        }

        public Criteria andEmailIsapproveGreaterThanOrEqualTo(Integer value) {
            addCriterion("email_isApprove >=", value, "emailIsapprove");
            return (Criteria) this;
        }

        public Criteria andEmailIsapproveLessThan(Integer value) {
            addCriterion("email_isApprove <", value, "emailIsapprove");
            return (Criteria) this;
        }

        public Criteria andEmailIsapproveLessThanOrEqualTo(Integer value) {
            addCriterion("email_isApprove <=", value, "emailIsapprove");
            return (Criteria) this;
        }

        public Criteria andEmailIsapproveIn(List<Integer> values) {
            addCriterion("email_isApprove in", values, "emailIsapprove");
            return (Criteria) this;
        }

        public Criteria andEmailIsapproveNotIn(List<Integer> values) {
            addCriterion("email_isApprove not in", values, "emailIsapprove");
            return (Criteria) this;
        }

        public Criteria andEmailIsapproveBetween(Integer value1, Integer value2) {
            addCriterion("email_isApprove between", value1, value2, "emailIsapprove");
            return (Criteria) this;
        }

        public Criteria andEmailIsapproveNotBetween(Integer value1, Integer value2) {
            addCriterion("email_isApprove not between", value1, value2, "emailIsapprove");
            return (Criteria) this;
        }

        public Criteria andEducationIsNull() {
            addCriterion("education is null");
            return (Criteria) this;
        }

        public Criteria andEducationIsNotNull() {
            addCriterion("education is not null");
            return (Criteria) this;
        }

        public Criteria andEducationEqualTo(String value) {
            addCriterion("education =", value, "education");
            return (Criteria) this;
        }

        public Criteria andEducationNotEqualTo(String value) {
            addCriterion("education <>", value, "education");
            return (Criteria) this;
        }

        public Criteria andEducationGreaterThan(String value) {
            addCriterion("education >", value, "education");
            return (Criteria) this;
        }

        public Criteria andEducationGreaterThanOrEqualTo(String value) {
            addCriterion("education >=", value, "education");
            return (Criteria) this;
        }

        public Criteria andEducationLessThan(String value) {
            addCriterion("education <", value, "education");
            return (Criteria) this;
        }

        public Criteria andEducationLessThanOrEqualTo(String value) {
            addCriterion("education <=", value, "education");
            return (Criteria) this;
        }

        public Criteria andEducationLike(String value) {
            addCriterion("education like", value, "education");
            return (Criteria) this;
        }

        public Criteria andEducationNotLike(String value) {
            addCriterion("education not like", value, "education");
            return (Criteria) this;
        }

        public Criteria andEducationIn(List<String> values) {
            addCriterion("education in", values, "education");
            return (Criteria) this;
        }

        public Criteria andEducationNotIn(List<String> values) {
            addCriterion("education not in", values, "education");
            return (Criteria) this;
        }

        public Criteria andEducationBetween(String value1, String value2) {
            addCriterion("education between", value1, value2, "education");
            return (Criteria) this;
        }

        public Criteria andEducationNotBetween(String value1, String value2) {
            addCriterion("education not between", value1, value2, "education");
            return (Criteria) this;
        }

        public Criteria andProvinceIsNull() {
            addCriterion("province is null");
            return (Criteria) this;
        }

        public Criteria andProvinceIsNotNull() {
            addCriterion("province is not null");
            return (Criteria) this;
        }

        public Criteria andProvinceEqualTo(String value) {
            addCriterion("province =", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceNotEqualTo(String value) {
            addCriterion("province <>", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceGreaterThan(String value) {
            addCriterion("province >", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceGreaterThanOrEqualTo(String value) {
            addCriterion("province >=", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceLessThan(String value) {
            addCriterion("province <", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceLessThanOrEqualTo(String value) {
            addCriterion("province <=", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceLike(String value) {
            addCriterion("province like", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceNotLike(String value) {
            addCriterion("province not like", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceIn(List<String> values) {
            addCriterion("province in", values, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceNotIn(List<String> values) {
            addCriterion("province not in", values, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceBetween(String value1, String value2) {
            addCriterion("province between", value1, value2, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceNotBetween(String value1, String value2) {
            addCriterion("province not between", value1, value2, "province");
            return (Criteria) this;
        }

        public Criteria andCityIsNull() {
            addCriterion("city is null");
            return (Criteria) this;
        }

        public Criteria andCityIsNotNull() {
            addCriterion("city is not null");
            return (Criteria) this;
        }

        public Criteria andCityEqualTo(String value) {
            addCriterion("city =", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotEqualTo(String value) {
            addCriterion("city <>", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityGreaterThan(String value) {
            addCriterion("city >", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityGreaterThanOrEqualTo(String value) {
            addCriterion("city >=", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityLessThan(String value) {
            addCriterion("city <", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityLessThanOrEqualTo(String value) {
            addCriterion("city <=", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityLike(String value) {
            addCriterion("city like", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotLike(String value) {
            addCriterion("city not like", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityIn(List<String> values) {
            addCriterion("city in", values, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotIn(List<String> values) {
            addCriterion("city not in", values, "city");
            return (Criteria) this;
        }

        public Criteria andCityBetween(String value1, String value2) {
            addCriterion("city between", value1, value2, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotBetween(String value1, String value2) {
            addCriterion("city not between", value1, value2, "city");
            return (Criteria) this;
        }

        public Criteria andAreaIsNull() {
            addCriterion("area is null");
            return (Criteria) this;
        }

        public Criteria andAreaIsNotNull() {
            addCriterion("area is not null");
            return (Criteria) this;
        }

        public Criteria andAreaEqualTo(String value) {
            addCriterion("area =", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotEqualTo(String value) {
            addCriterion("area <>", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaGreaterThan(String value) {
            addCriterion("area >", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaGreaterThanOrEqualTo(String value) {
            addCriterion("area >=", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaLessThan(String value) {
            addCriterion("area <", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaLessThanOrEqualTo(String value) {
            addCriterion("area <=", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaLike(String value) {
            addCriterion("area like", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotLike(String value) {
            addCriterion("area not like", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaIn(List<String> values) {
            addCriterion("area in", values, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotIn(List<String> values) {
            addCriterion("area not in", values, "area");
            return (Criteria) this;
        }

        public Criteria andAreaBetween(String value1, String value2) {
            addCriterion("area between", value1, value2, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotBetween(String value1, String value2) {
            addCriterion("area not between", value1, value2, "area");
            return (Criteria) this;
        }

        public Criteria andAddressIsNull() {
            addCriterion("address is null");
            return (Criteria) this;
        }

        public Criteria andAddressIsNotNull() {
            addCriterion("address is not null");
            return (Criteria) this;
        }

        public Criteria andAddressEqualTo(String value) {
            addCriterion("address =", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotEqualTo(String value) {
            addCriterion("address <>", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThan(String value) {
            addCriterion("address >", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThanOrEqualTo(String value) {
            addCriterion("address >=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThan(String value) {
            addCriterion("address <", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThanOrEqualTo(String value) {
            addCriterion("address <=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLike(String value) {
            addCriterion("address like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotLike(String value) {
            addCriterion("address not like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressIn(List<String> values) {
            addCriterion("address in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotIn(List<String> values) {
            addCriterion("address not in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressBetween(String value1, String value2) {
            addCriterion("address between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotBetween(String value1, String value2) {
            addCriterion("address not between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andIntroIsNull() {
            addCriterion("intro is null");
            return (Criteria) this;
        }

        public Criteria andIntroIsNotNull() {
            addCriterion("intro is not null");
            return (Criteria) this;
        }

        public Criteria andIntroEqualTo(String value) {
            addCriterion("intro =", value, "intro");
            return (Criteria) this;
        }

        public Criteria andIntroNotEqualTo(String value) {
            addCriterion("intro <>", value, "intro");
            return (Criteria) this;
        }

        public Criteria andIntroGreaterThan(String value) {
            addCriterion("intro >", value, "intro");
            return (Criteria) this;
        }

        public Criteria andIntroGreaterThanOrEqualTo(String value) {
            addCriterion("intro >=", value, "intro");
            return (Criteria) this;
        }

        public Criteria andIntroLessThan(String value) {
            addCriterion("intro <", value, "intro");
            return (Criteria) this;
        }

        public Criteria andIntroLessThanOrEqualTo(String value) {
            addCriterion("intro <=", value, "intro");
            return (Criteria) this;
        }

        public Criteria andIntroLike(String value) {
            addCriterion("intro like", value, "intro");
            return (Criteria) this;
        }

        public Criteria andIntroNotLike(String value) {
            addCriterion("intro not like", value, "intro");
            return (Criteria) this;
        }

        public Criteria andIntroIn(List<String> values) {
            addCriterion("intro in", values, "intro");
            return (Criteria) this;
        }

        public Criteria andIntroNotIn(List<String> values) {
            addCriterion("intro not in", values, "intro");
            return (Criteria) this;
        }

        public Criteria andIntroBetween(String value1, String value2) {
            addCriterion("intro between", value1, value2, "intro");
            return (Criteria) this;
        }

        public Criteria andIntroNotBetween(String value1, String value2) {
            addCriterion("intro not between", value1, value2, "intro");
            return (Criteria) this;
        }

        public Criteria andInterestIsNull() {
            addCriterion("interest is null");
            return (Criteria) this;
        }

        public Criteria andInterestIsNotNull() {
            addCriterion("interest is not null");
            return (Criteria) this;
        }

        public Criteria andInterestEqualTo(String value) {
            addCriterion("interest =", value, "interest");
            return (Criteria) this;
        }

        public Criteria andInterestNotEqualTo(String value) {
            addCriterion("interest <>", value, "interest");
            return (Criteria) this;
        }

        public Criteria andInterestGreaterThan(String value) {
            addCriterion("interest >", value, "interest");
            return (Criteria) this;
        }

        public Criteria andInterestGreaterThanOrEqualTo(String value) {
            addCriterion("interest >=", value, "interest");
            return (Criteria) this;
        }

        public Criteria andInterestLessThan(String value) {
            addCriterion("interest <", value, "interest");
            return (Criteria) this;
        }

        public Criteria andInterestLessThanOrEqualTo(String value) {
            addCriterion("interest <=", value, "interest");
            return (Criteria) this;
        }

        public Criteria andInterestLike(String value) {
            addCriterion("interest like", value, "interest");
            return (Criteria) this;
        }

        public Criteria andInterestNotLike(String value) {
            addCriterion("interest not like", value, "interest");
            return (Criteria) this;
        }

        public Criteria andInterestIn(List<String> values) {
            addCriterion("interest in", values, "interest");
            return (Criteria) this;
        }

        public Criteria andInterestNotIn(List<String> values) {
            addCriterion("interest not in", values, "interest");
            return (Criteria) this;
        }

        public Criteria andInterestBetween(String value1, String value2) {
            addCriterion("interest between", value1, value2, "interest");
            return (Criteria) this;
        }

        public Criteria andInterestNotBetween(String value1, String value2) {
            addCriterion("interest not between", value1, value2, "interest");
            return (Criteria) this;
        }

        public Criteria andAttributeIsNull() {
            addCriterion("`attribute` is null");
            return (Criteria) this;
        }

        public Criteria andAttributeIsNotNull() {
            addCriterion("`attribute` is not null");
            return (Criteria) this;
        }

        public Criteria andAttributeEqualTo(Integer value) {
            addCriterion("`attribute` =", value, "attribute");
            return (Criteria) this;
        }

        public Criteria andAttributeNotEqualTo(Integer value) {
            addCriterion("`attribute` <>", value, "attribute");
            return (Criteria) this;
        }

        public Criteria andAttributeGreaterThan(Integer value) {
            addCriterion("`attribute` >", value, "attribute");
            return (Criteria) this;
        }

        public Criteria andAttributeGreaterThanOrEqualTo(Integer value) {
            addCriterion("`attribute` >=", value, "attribute");
            return (Criteria) this;
        }

        public Criteria andAttributeLessThan(Integer value) {
            addCriterion("`attribute` <", value, "attribute");
            return (Criteria) this;
        }

        public Criteria andAttributeLessThanOrEqualTo(Integer value) {
            addCriterion("`attribute` <=", value, "attribute");
            return (Criteria) this;
        }

        public Criteria andAttributeIn(List<Integer> values) {
            addCriterion("`attribute` in", values, "attribute");
            return (Criteria) this;
        }

        public Criteria andAttributeNotIn(List<Integer> values) {
            addCriterion("`attribute` not in", values, "attribute");
            return (Criteria) this;
        }

        public Criteria andAttributeBetween(Integer value1, Integer value2) {
            addCriterion("`attribute` between", value1, value2, "attribute");
            return (Criteria) this;
        }

        public Criteria andAttributeNotBetween(Integer value1, Integer value2) {
            addCriterion("`attribute` not between", value1, value2, "attribute");
            return (Criteria) this;
        }

        public Criteria andPromotewayIsNull() {
            addCriterion("promoteway is null");
            return (Criteria) this;
        }

        public Criteria andPromotewayIsNotNull() {
            addCriterion("promoteway is not null");
            return (Criteria) this;
        }

        public Criteria andPromotewayEqualTo(Integer value) {
            addCriterion("promoteway =", value, "promoteway");
            return (Criteria) this;
        }

        public Criteria andPromotewayNotEqualTo(Integer value) {
            addCriterion("promoteway <>", value, "promoteway");
            return (Criteria) this;
        }

        public Criteria andPromotewayGreaterThan(Integer value) {
            addCriterion("promoteway >", value, "promoteway");
            return (Criteria) this;
        }

        public Criteria andPromotewayGreaterThanOrEqualTo(Integer value) {
            addCriterion("promoteway >=", value, "promoteway");
            return (Criteria) this;
        }

        public Criteria andPromotewayLessThan(Integer value) {
            addCriterion("promoteway <", value, "promoteway");
            return (Criteria) this;
        }

        public Criteria andPromotewayLessThanOrEqualTo(Integer value) {
            addCriterion("promoteway <=", value, "promoteway");
            return (Criteria) this;
        }

        public Criteria andPromotewayIn(List<Integer> values) {
            addCriterion("promoteway in", values, "promoteway");
            return (Criteria) this;
        }

        public Criteria andPromotewayNotIn(List<Integer> values) {
            addCriterion("promoteway not in", values, "promoteway");
            return (Criteria) this;
        }

        public Criteria andPromotewayBetween(Integer value1, Integer value2) {
            addCriterion("promoteway between", value1, value2, "promoteway");
            return (Criteria) this;
        }

        public Criteria andPromotewayNotBetween(Integer value1, Integer value2) {
            addCriterion("promoteway not between", value1, value2, "promoteway");
            return (Criteria) this;
        }

        public Criteria andIsContactIsNull() {
            addCriterion("is_contact is null");
            return (Criteria) this;
        }

        public Criteria andIsContactIsNotNull() {
            addCriterion("is_contact is not null");
            return (Criteria) this;
        }

        public Criteria andIsContactEqualTo(Boolean value) {
            addCriterion("is_contact =", value, "isContact");
            return (Criteria) this;
        }

        public Criteria andIsContactNotEqualTo(Boolean value) {
            addCriterion("is_contact <>", value, "isContact");
            return (Criteria) this;
        }

        public Criteria andIsContactGreaterThan(Boolean value) {
            addCriterion("is_contact >", value, "isContact");
            return (Criteria) this;
        }

        public Criteria andIsContactGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_contact >=", value, "isContact");
            return (Criteria) this;
        }

        public Criteria andIsContactLessThan(Boolean value) {
            addCriterion("is_contact <", value, "isContact");
            return (Criteria) this;
        }

        public Criteria andIsContactLessThanOrEqualTo(Boolean value) {
            addCriterion("is_contact <=", value, "isContact");
            return (Criteria) this;
        }

        public Criteria andIsContactIn(List<Boolean> values) {
            addCriterion("is_contact in", values, "isContact");
            return (Criteria) this;
        }

        public Criteria andIsContactNotIn(List<Boolean> values) {
            addCriterion("is_contact not in", values, "isContact");
            return (Criteria) this;
        }

        public Criteria andIsContactBetween(Boolean value1, Boolean value2) {
            addCriterion("is_contact between", value1, value2, "isContact");
            return (Criteria) this;
        }

        public Criteria andIsContactNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_contact not between", value1, value2, "isContact");
            return (Criteria) this;
        }

        public Criteria andVipIdIsNull() {
            addCriterion("vip_id is null");
            return (Criteria) this;
        }

        public Criteria andVipIdIsNotNull() {
            addCriterion("vip_id is not null");
            return (Criteria) this;
        }

        public Criteria andVipIdEqualTo(Integer value) {
            addCriterion("vip_id =", value, "vipId");
            return (Criteria) this;
        }

        public Criteria andVipIdNotEqualTo(Integer value) {
            addCriterion("vip_id <>", value, "vipId");
            return (Criteria) this;
        }

        public Criteria andVipIdGreaterThan(Integer value) {
            addCriterion("vip_id >", value, "vipId");
            return (Criteria) this;
        }

        public Criteria andVipIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("vip_id >=", value, "vipId");
            return (Criteria) this;
        }

        public Criteria andVipIdLessThan(Integer value) {
            addCriterion("vip_id <", value, "vipId");
            return (Criteria) this;
        }

        public Criteria andVipIdLessThanOrEqualTo(Integer value) {
            addCriterion("vip_id <=", value, "vipId");
            return (Criteria) this;
        }

        public Criteria andVipIdIn(List<Integer> values) {
            addCriterion("vip_id in", values, "vipId");
            return (Criteria) this;
        }

        public Criteria andVipIdNotIn(List<Integer> values) {
            addCriterion("vip_id not in", values, "vipId");
            return (Criteria) this;
        }

        public Criteria andVipIdBetween(Integer value1, Integer value2) {
            addCriterion("vip_id between", value1, value2, "vipId");
            return (Criteria) this;
        }

        public Criteria andVipIdNotBetween(Integer value1, Integer value2) {
            addCriterion("vip_id not between", value1, value2, "vipId");
            return (Criteria) this;
        }

        public Criteria andVipValueIsNull() {
            addCriterion("vip_value is null");
            return (Criteria) this;
        }

        public Criteria andVipValueIsNotNull() {
            addCriterion("vip_value is not null");
            return (Criteria) this;
        }

        public Criteria andVipValueEqualTo(Integer value) {
            addCriterion("vip_value =", value, "vipValue");
            return (Criteria) this;
        }

        public Criteria andVipValueNotEqualTo(Integer value) {
            addCriterion("vip_value <>", value, "vipValue");
            return (Criteria) this;
        }

        public Criteria andVipValueGreaterThan(Integer value) {
            addCriterion("vip_value >", value, "vipValue");
            return (Criteria) this;
        }

        public Criteria andVipValueGreaterThanOrEqualTo(Integer value) {
            addCriterion("vip_value >=", value, "vipValue");
            return (Criteria) this;
        }

        public Criteria andVipValueLessThan(Integer value) {
            addCriterion("vip_value <", value, "vipValue");
            return (Criteria) this;
        }

        public Criteria andVipValueLessThanOrEqualTo(Integer value) {
            addCriterion("vip_value <=", value, "vipValue");
            return (Criteria) this;
        }

        public Criteria andVipValueIn(List<Integer> values) {
            addCriterion("vip_value in", values, "vipValue");
            return (Criteria) this;
        }

        public Criteria andVipValueNotIn(List<Integer> values) {
            addCriterion("vip_value not in", values, "vipValue");
            return (Criteria) this;
        }

        public Criteria andVipValueBetween(Integer value1, Integer value2) {
            addCriterion("vip_value between", value1, value2, "vipValue");
            return (Criteria) this;
        }

        public Criteria andVipValueNotBetween(Integer value1, Integer value2) {
            addCriterion("vip_value not between", value1, value2, "vipValue");
            return (Criteria) this;
        }

        public Criteria andVipExpDateIsNull() {
            addCriterion("vip_exp_date is null");
            return (Criteria) this;
        }

        public Criteria andVipExpDateIsNotNull() {
            addCriterion("vip_exp_date is not null");
            return (Criteria) this;
        }

        public Criteria andVipExpDateEqualTo(Integer value) {
            addCriterion("vip_exp_date =", value, "vipExpDate");
            return (Criteria) this;
        }

        public Criteria andVipExpDateNotEqualTo(Integer value) {
            addCriterion("vip_exp_date <>", value, "vipExpDate");
            return (Criteria) this;
        }

        public Criteria andVipExpDateGreaterThan(Integer value) {
            addCriterion("vip_exp_date >", value, "vipExpDate");
            return (Criteria) this;
        }

        public Criteria andVipExpDateGreaterThanOrEqualTo(Integer value) {
            addCriterion("vip_exp_date >=", value, "vipExpDate");
            return (Criteria) this;
        }

        public Criteria andVipExpDateLessThan(Integer value) {
            addCriterion("vip_exp_date <", value, "vipExpDate");
            return (Criteria) this;
        }

        public Criteria andVipExpDateLessThanOrEqualTo(Integer value) {
            addCriterion("vip_exp_date <=", value, "vipExpDate");
            return (Criteria) this;
        }

        public Criteria andVipExpDateIn(List<Integer> values) {
            addCriterion("vip_exp_date in", values, "vipExpDate");
            return (Criteria) this;
        }

        public Criteria andVipExpDateNotIn(List<Integer> values) {
            addCriterion("vip_exp_date not in", values, "vipExpDate");
            return (Criteria) this;
        }

        public Criteria andVipExpDateBetween(Integer value1, Integer value2) {
            addCriterion("vip_exp_date between", value1, value2, "vipExpDate");
            return (Criteria) this;
        }

        public Criteria andVipExpDateNotBetween(Integer value1, Integer value2) {
            addCriterion("vip_exp_date not between", value1, value2, "vipExpDate");
            return (Criteria) this;
        }

        public Criteria andVipPlatformIsNull() {
            addCriterion("vip_platform is null");
            return (Criteria) this;
        }

        public Criteria andVipPlatformIsNotNull() {
            addCriterion("vip_platform is not null");
            return (Criteria) this;
        }

        public Criteria andVipPlatformEqualTo(Byte value) {
            addCriterion("vip_platform =", value, "vipPlatform");
            return (Criteria) this;
        }

        public Criteria andVipPlatformNotEqualTo(Byte value) {
            addCriterion("vip_platform <>", value, "vipPlatform");
            return (Criteria) this;
        }

        public Criteria andVipPlatformGreaterThan(Byte value) {
            addCriterion("vip_platform >", value, "vipPlatform");
            return (Criteria) this;
        }

        public Criteria andVipPlatformGreaterThanOrEqualTo(Byte value) {
            addCriterion("vip_platform >=", value, "vipPlatform");
            return (Criteria) this;
        }

        public Criteria andVipPlatformLessThan(Byte value) {
            addCriterion("vip_platform <", value, "vipPlatform");
            return (Criteria) this;
        }

        public Criteria andVipPlatformLessThanOrEqualTo(Byte value) {
            addCriterion("vip_platform <=", value, "vipPlatform");
            return (Criteria) this;
        }

        public Criteria andVipPlatformIn(List<Byte> values) {
            addCriterion("vip_platform in", values, "vipPlatform");
            return (Criteria) this;
        }

        public Criteria andVipPlatformNotIn(List<Byte> values) {
            addCriterion("vip_platform not in", values, "vipPlatform");
            return (Criteria) this;
        }

        public Criteria andVipPlatformBetween(Byte value1, Byte value2) {
            addCriterion("vip_platform between", value1, value2, "vipPlatform");
            return (Criteria) this;
        }

        public Criteria andVipPlatformNotBetween(Byte value1, Byte value2) {
            addCriterion("vip_platform not between", value1, value2, "vipPlatform");
            return (Criteria) this;
        }

        public Criteria andBorrowerTypeIsNull() {
            addCriterion("borrower_type is null");
            return (Criteria) this;
        }

        public Criteria andBorrowerTypeIsNotNull() {
            addCriterion("borrower_type is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowerTypeEqualTo(Integer value) {
            addCriterion("borrower_type =", value, "borrowerType");
            return (Criteria) this;
        }

        public Criteria andBorrowerTypeNotEqualTo(Integer value) {
            addCriterion("borrower_type <>", value, "borrowerType");
            return (Criteria) this;
        }

        public Criteria andBorrowerTypeGreaterThan(Integer value) {
            addCriterion("borrower_type >", value, "borrowerType");
            return (Criteria) this;
        }

        public Criteria andBorrowerTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("borrower_type >=", value, "borrowerType");
            return (Criteria) this;
        }

        public Criteria andBorrowerTypeLessThan(Integer value) {
            addCriterion("borrower_type <", value, "borrowerType");
            return (Criteria) this;
        }

        public Criteria andBorrowerTypeLessThanOrEqualTo(Integer value) {
            addCriterion("borrower_type <=", value, "borrowerType");
            return (Criteria) this;
        }

        public Criteria andBorrowerTypeIn(List<Integer> values) {
            addCriterion("borrower_type in", values, "borrowerType");
            return (Criteria) this;
        }

        public Criteria andBorrowerTypeNotIn(List<Integer> values) {
            addCriterion("borrower_type not in", values, "borrowerType");
            return (Criteria) this;
        }

        public Criteria andBorrowerTypeBetween(Integer value1, Integer value2) {
            addCriterion("borrower_type between", value1, value2, "borrowerType");
            return (Criteria) this;
        }

        public Criteria andBorrowerTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("borrower_type not between", value1, value2, "borrowerType");
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