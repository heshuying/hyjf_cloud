package com.hyjf.am.config.dao.model.auto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdminUtmReadPermissionsExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public AdminUtmReadPermissionsExample() {
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

        public Criteria andAdminUserIdIsNull() {
            addCriterion("admin_user_id is null");
            return (Criteria) this;
        }

        public Criteria andAdminUserIdIsNotNull() {
            addCriterion("admin_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andAdminUserIdEqualTo(Integer value) {
            addCriterion("admin_user_id =", value, "adminUserId");
            return (Criteria) this;
        }

        public Criteria andAdminUserIdNotEqualTo(Integer value) {
            addCriterion("admin_user_id <>", value, "adminUserId");
            return (Criteria) this;
        }

        public Criteria andAdminUserIdGreaterThan(Integer value) {
            addCriterion("admin_user_id >", value, "adminUserId");
            return (Criteria) this;
        }

        public Criteria andAdminUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("admin_user_id >=", value, "adminUserId");
            return (Criteria) this;
        }

        public Criteria andAdminUserIdLessThan(Integer value) {
            addCriterion("admin_user_id <", value, "adminUserId");
            return (Criteria) this;
        }

        public Criteria andAdminUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("admin_user_id <=", value, "adminUserId");
            return (Criteria) this;
        }

        public Criteria andAdminUserIdIn(List<Integer> values) {
            addCriterion("admin_user_id in", values, "adminUserId");
            return (Criteria) this;
        }

        public Criteria andAdminUserIdNotIn(List<Integer> values) {
            addCriterion("admin_user_id not in", values, "adminUserId");
            return (Criteria) this;
        }

        public Criteria andAdminUserIdBetween(Integer value1, Integer value2) {
            addCriterion("admin_user_id between", value1, value2, "adminUserId");
            return (Criteria) this;
        }

        public Criteria andAdminUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("admin_user_id not between", value1, value2, "adminUserId");
            return (Criteria) this;
        }

        public Criteria andAdminUserNameIsNull() {
            addCriterion("admin_user_name is null");
            return (Criteria) this;
        }

        public Criteria andAdminUserNameIsNotNull() {
            addCriterion("admin_user_name is not null");
            return (Criteria) this;
        }

        public Criteria andAdminUserNameEqualTo(String value) {
            addCriterion("admin_user_name =", value, "adminUserName");
            return (Criteria) this;
        }

        public Criteria andAdminUserNameNotEqualTo(String value) {
            addCriterion("admin_user_name <>", value, "adminUserName");
            return (Criteria) this;
        }

        public Criteria andAdminUserNameGreaterThan(String value) {
            addCriterion("admin_user_name >", value, "adminUserName");
            return (Criteria) this;
        }

        public Criteria andAdminUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("admin_user_name >=", value, "adminUserName");
            return (Criteria) this;
        }

        public Criteria andAdminUserNameLessThan(String value) {
            addCriterion("admin_user_name <", value, "adminUserName");
            return (Criteria) this;
        }

        public Criteria andAdminUserNameLessThanOrEqualTo(String value) {
            addCriterion("admin_user_name <=", value, "adminUserName");
            return (Criteria) this;
        }

        public Criteria andAdminUserNameLike(String value) {
            addCriterion("admin_user_name like", value, "adminUserName");
            return (Criteria) this;
        }

        public Criteria andAdminUserNameNotLike(String value) {
            addCriterion("admin_user_name not like", value, "adminUserName");
            return (Criteria) this;
        }

        public Criteria andAdminUserNameIn(List<String> values) {
            addCriterion("admin_user_name in", values, "adminUserName");
            return (Criteria) this;
        }

        public Criteria andAdminUserNameNotIn(List<String> values) {
            addCriterion("admin_user_name not in", values, "adminUserName");
            return (Criteria) this;
        }

        public Criteria andAdminUserNameBetween(String value1, String value2) {
            addCriterion("admin_user_name between", value1, value2, "adminUserName");
            return (Criteria) this;
        }

        public Criteria andAdminUserNameNotBetween(String value1, String value2) {
            addCriterion("admin_user_name not between", value1, value2, "adminUserName");
            return (Criteria) this;
        }

        public Criteria andUtmIdsIsNull() {
            addCriterion("utm_ids is null");
            return (Criteria) this;
        }

        public Criteria andUtmIdsIsNotNull() {
            addCriterion("utm_ids is not null");
            return (Criteria) this;
        }

        public Criteria andUtmIdsEqualTo(String value) {
            addCriterion("utm_ids =", value, "utmIds");
            return (Criteria) this;
        }

        public Criteria andUtmIdsNotEqualTo(String value) {
            addCriterion("utm_ids <>", value, "utmIds");
            return (Criteria) this;
        }

        public Criteria andUtmIdsGreaterThan(String value) {
            addCriterion("utm_ids >", value, "utmIds");
            return (Criteria) this;
        }

        public Criteria andUtmIdsGreaterThanOrEqualTo(String value) {
            addCriterion("utm_ids >=", value, "utmIds");
            return (Criteria) this;
        }

        public Criteria andUtmIdsLessThan(String value) {
            addCriterion("utm_ids <", value, "utmIds");
            return (Criteria) this;
        }

        public Criteria andUtmIdsLessThanOrEqualTo(String value) {
            addCriterion("utm_ids <=", value, "utmIds");
            return (Criteria) this;
        }

        public Criteria andUtmIdsLike(String value) {
            addCriterion("utm_ids like", value, "utmIds");
            return (Criteria) this;
        }

        public Criteria andUtmIdsNotLike(String value) {
            addCriterion("utm_ids not like", value, "utmIds");
            return (Criteria) this;
        }

        public Criteria andUtmIdsIn(List<String> values) {
            addCriterion("utm_ids in", values, "utmIds");
            return (Criteria) this;
        }

        public Criteria andUtmIdsNotIn(List<String> values) {
            addCriterion("utm_ids not in", values, "utmIds");
            return (Criteria) this;
        }

        public Criteria andUtmIdsBetween(String value1, String value2) {
            addCriterion("utm_ids between", value1, value2, "utmIds");
            return (Criteria) this;
        }

        public Criteria andUtmIdsNotBetween(String value1, String value2) {
            addCriterion("utm_ids not between", value1, value2, "utmIds");
            return (Criteria) this;
        }

        public Criteria andKeyCodeIsNull() {
            addCriterion("key_code is null");
            return (Criteria) this;
        }

        public Criteria andKeyCodeIsNotNull() {
            addCriterion("key_code is not null");
            return (Criteria) this;
        }

        public Criteria andKeyCodeEqualTo(String value) {
            addCriterion("key_code =", value, "keyCode");
            return (Criteria) this;
        }

        public Criteria andKeyCodeNotEqualTo(String value) {
            addCriterion("key_code <>", value, "keyCode");
            return (Criteria) this;
        }

        public Criteria andKeyCodeGreaterThan(String value) {
            addCriterion("key_code >", value, "keyCode");
            return (Criteria) this;
        }

        public Criteria andKeyCodeGreaterThanOrEqualTo(String value) {
            addCriterion("key_code >=", value, "keyCode");
            return (Criteria) this;
        }

        public Criteria andKeyCodeLessThan(String value) {
            addCriterion("key_code <", value, "keyCode");
            return (Criteria) this;
        }

        public Criteria andKeyCodeLessThanOrEqualTo(String value) {
            addCriterion("key_code <=", value, "keyCode");
            return (Criteria) this;
        }

        public Criteria andKeyCodeLike(String value) {
            addCriterion("key_code like", value, "keyCode");
            return (Criteria) this;
        }

        public Criteria andKeyCodeNotLike(String value) {
            addCriterion("key_code not like", value, "keyCode");
            return (Criteria) this;
        }

        public Criteria andKeyCodeIn(List<String> values) {
            addCriterion("key_code in", values, "keyCode");
            return (Criteria) this;
        }

        public Criteria andKeyCodeNotIn(List<String> values) {
            addCriterion("key_code not in", values, "keyCode");
            return (Criteria) this;
        }

        public Criteria andKeyCodeBetween(String value1, String value2) {
            addCriterion("key_code between", value1, value2, "keyCode");
            return (Criteria) this;
        }

        public Criteria andKeyCodeNotBetween(String value1, String value2) {
            addCriterion("key_code not between", value1, value2, "keyCode");
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