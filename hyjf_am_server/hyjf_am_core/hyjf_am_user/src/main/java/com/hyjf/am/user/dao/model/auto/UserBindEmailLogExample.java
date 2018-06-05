package com.hyjf.am.user.dao.model.auto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserBindEmailLogExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public UserBindEmailLogExample() {
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

        public Criteria andUserEmailIsNull() {
            addCriterion("user_Email is null");
            return (Criteria) this;
        }

        public Criteria andUserEmailIsNotNull() {
            addCriterion("user_Email is not null");
            return (Criteria) this;
        }

        public Criteria andUserEmailEqualTo(String value) {
            addCriterion("user_Email =", value, "userEmail");
            return (Criteria) this;
        }

        public Criteria andUserEmailNotEqualTo(String value) {
            addCriterion("user_Email <>", value, "userEmail");
            return (Criteria) this;
        }

        public Criteria andUserEmailGreaterThan(String value) {
            addCriterion("user_Email >", value, "userEmail");
            return (Criteria) this;
        }

        public Criteria andUserEmailGreaterThanOrEqualTo(String value) {
            addCriterion("user_Email >=", value, "userEmail");
            return (Criteria) this;
        }

        public Criteria andUserEmailLessThan(String value) {
            addCriterion("user_Email <", value, "userEmail");
            return (Criteria) this;
        }

        public Criteria andUserEmailLessThanOrEqualTo(String value) {
            addCriterion("user_Email <=", value, "userEmail");
            return (Criteria) this;
        }

        public Criteria andUserEmailLike(String value) {
            addCriterion("user_Email like", value, "userEmail");
            return (Criteria) this;
        }

        public Criteria andUserEmailNotLike(String value) {
            addCriterion("user_Email not like", value, "userEmail");
            return (Criteria) this;
        }

        public Criteria andUserEmailIn(List<String> values) {
            addCriterion("user_Email in", values, "userEmail");
            return (Criteria) this;
        }

        public Criteria andUserEmailNotIn(List<String> values) {
            addCriterion("user_Email not in", values, "userEmail");
            return (Criteria) this;
        }

        public Criteria andUserEmailBetween(String value1, String value2) {
            addCriterion("user_Email between", value1, value2, "userEmail");
            return (Criteria) this;
        }

        public Criteria andUserEmailNotBetween(String value1, String value2) {
            addCriterion("user_Email not between", value1, value2, "userEmail");
            return (Criteria) this;
        }

        public Criteria andUserEmailStatusIsNull() {
            addCriterion("user_Email_Status is null");
            return (Criteria) this;
        }

        public Criteria andUserEmailStatusIsNotNull() {
            addCriterion("user_Email_Status is not null");
            return (Criteria) this;
        }

        public Criteria andUserEmailStatusEqualTo(Integer value) {
            addCriterion("user_Email_Status =", value, "userEmailStatus");
            return (Criteria) this;
        }

        public Criteria andUserEmailStatusNotEqualTo(Integer value) {
            addCriterion("user_Email_Status <>", value, "userEmailStatus");
            return (Criteria) this;
        }

        public Criteria andUserEmailStatusGreaterThan(Integer value) {
            addCriterion("user_Email_Status >", value, "userEmailStatus");
            return (Criteria) this;
        }

        public Criteria andUserEmailStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("user_Email_Status >=", value, "userEmailStatus");
            return (Criteria) this;
        }

        public Criteria andUserEmailStatusLessThan(Integer value) {
            addCriterion("user_Email_Status <", value, "userEmailStatus");
            return (Criteria) this;
        }

        public Criteria andUserEmailStatusLessThanOrEqualTo(Integer value) {
            addCriterion("user_Email_Status <=", value, "userEmailStatus");
            return (Criteria) this;
        }

        public Criteria andUserEmailStatusIn(List<Integer> values) {
            addCriterion("user_Email_Status in", values, "userEmailStatus");
            return (Criteria) this;
        }

        public Criteria andUserEmailStatusNotIn(List<Integer> values) {
            addCriterion("user_Email_Status not in", values, "userEmailStatus");
            return (Criteria) this;
        }

        public Criteria andUserEmailStatusBetween(Integer value1, Integer value2) {
            addCriterion("user_Email_Status between", value1, value2, "userEmailStatus");
            return (Criteria) this;
        }

        public Criteria andUserEmailStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("user_Email_Status not between", value1, value2, "userEmailStatus");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIsNull() {
            addCriterion("createTime is null");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIsNotNull() {
            addCriterion("createTime is not null");
            return (Criteria) this;
        }

        public Criteria andCreatetimeEqualTo(Date value) {
            addCriterion("createTime =", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotEqualTo(Date value) {
            addCriterion("createTime <>", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeGreaterThan(Date value) {
            addCriterion("createTime >", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeGreaterThanOrEqualTo(Date value) {
            addCriterion("createTime >=", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeLessThan(Date value) {
            addCriterion("createTime <", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeLessThanOrEqualTo(Date value) {
            addCriterion("createTime <=", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIn(List<Date> values) {
            addCriterion("createTime in", values, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotIn(List<Date> values) {
            addCriterion("createTime not in", values, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeBetween(Date value1, Date value2) {
            addCriterion("createTime between", value1, value2, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotBetween(Date value1, Date value2) {
            addCriterion("createTime not between", value1, value2, "createtime");
            return (Criteria) this;
        }

        public Criteria andEmailActiveCodeIsNull() {
            addCriterion("email_Active_Code is null");
            return (Criteria) this;
        }

        public Criteria andEmailActiveCodeIsNotNull() {
            addCriterion("email_Active_Code is not null");
            return (Criteria) this;
        }

        public Criteria andEmailActiveCodeEqualTo(String value) {
            addCriterion("email_Active_Code =", value, "emailActiveCode");
            return (Criteria) this;
        }

        public Criteria andEmailActiveCodeNotEqualTo(String value) {
            addCriterion("email_Active_Code <>", value, "emailActiveCode");
            return (Criteria) this;
        }

        public Criteria andEmailActiveCodeGreaterThan(String value) {
            addCriterion("email_Active_Code >", value, "emailActiveCode");
            return (Criteria) this;
        }

        public Criteria andEmailActiveCodeGreaterThanOrEqualTo(String value) {
            addCriterion("email_Active_Code >=", value, "emailActiveCode");
            return (Criteria) this;
        }

        public Criteria andEmailActiveCodeLessThan(String value) {
            addCriterion("email_Active_Code <", value, "emailActiveCode");
            return (Criteria) this;
        }

        public Criteria andEmailActiveCodeLessThanOrEqualTo(String value) {
            addCriterion("email_Active_Code <=", value, "emailActiveCode");
            return (Criteria) this;
        }

        public Criteria andEmailActiveCodeLike(String value) {
            addCriterion("email_Active_Code like", value, "emailActiveCode");
            return (Criteria) this;
        }

        public Criteria andEmailActiveCodeNotLike(String value) {
            addCriterion("email_Active_Code not like", value, "emailActiveCode");
            return (Criteria) this;
        }

        public Criteria andEmailActiveCodeIn(List<String> values) {
            addCriterion("email_Active_Code in", values, "emailActiveCode");
            return (Criteria) this;
        }

        public Criteria andEmailActiveCodeNotIn(List<String> values) {
            addCriterion("email_Active_Code not in", values, "emailActiveCode");
            return (Criteria) this;
        }

        public Criteria andEmailActiveCodeBetween(String value1, String value2) {
            addCriterion("email_Active_Code between", value1, value2, "emailActiveCode");
            return (Criteria) this;
        }

        public Criteria andEmailActiveCodeNotBetween(String value1, String value2) {
            addCriterion("email_Active_Code not between", value1, value2, "emailActiveCode");
            return (Criteria) this;
        }

        public Criteria andEmailActiveUrlDeadtimeIsNull() {
            addCriterion("email_Active_Url_DeadTime is null");
            return (Criteria) this;
        }

        public Criteria andEmailActiveUrlDeadtimeIsNotNull() {
            addCriterion("email_Active_Url_DeadTime is not null");
            return (Criteria) this;
        }

        public Criteria andEmailActiveUrlDeadtimeEqualTo(Date value) {
            addCriterion("email_Active_Url_DeadTime =", value, "emailActiveUrlDeadtime");
            return (Criteria) this;
        }

        public Criteria andEmailActiveUrlDeadtimeNotEqualTo(Date value) {
            addCriterion("email_Active_Url_DeadTime <>", value, "emailActiveUrlDeadtime");
            return (Criteria) this;
        }

        public Criteria andEmailActiveUrlDeadtimeGreaterThan(Date value) {
            addCriterion("email_Active_Url_DeadTime >", value, "emailActiveUrlDeadtime");
            return (Criteria) this;
        }

        public Criteria andEmailActiveUrlDeadtimeGreaterThanOrEqualTo(Date value) {
            addCriterion("email_Active_Url_DeadTime >=", value, "emailActiveUrlDeadtime");
            return (Criteria) this;
        }

        public Criteria andEmailActiveUrlDeadtimeLessThan(Date value) {
            addCriterion("email_Active_Url_DeadTime <", value, "emailActiveUrlDeadtime");
            return (Criteria) this;
        }

        public Criteria andEmailActiveUrlDeadtimeLessThanOrEqualTo(Date value) {
            addCriterion("email_Active_Url_DeadTime <=", value, "emailActiveUrlDeadtime");
            return (Criteria) this;
        }

        public Criteria andEmailActiveUrlDeadtimeIn(List<Date> values) {
            addCriterion("email_Active_Url_DeadTime in", values, "emailActiveUrlDeadtime");
            return (Criteria) this;
        }

        public Criteria andEmailActiveUrlDeadtimeNotIn(List<Date> values) {
            addCriterion("email_Active_Url_DeadTime not in", values, "emailActiveUrlDeadtime");
            return (Criteria) this;
        }

        public Criteria andEmailActiveUrlDeadtimeBetween(Date value1, Date value2) {
            addCriterion("email_Active_Url_DeadTime between", value1, value2, "emailActiveUrlDeadtime");
            return (Criteria) this;
        }

        public Criteria andEmailActiveUrlDeadtimeNotBetween(Date value1, Date value2) {
            addCriterion("email_Active_Url_DeadTime not between", value1, value2, "emailActiveUrlDeadtime");
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