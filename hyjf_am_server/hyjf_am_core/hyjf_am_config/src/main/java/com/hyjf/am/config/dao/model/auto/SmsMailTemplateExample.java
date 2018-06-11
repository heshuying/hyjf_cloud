package com.hyjf.am.config.dao.model.auto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SmsMailTemplateExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public SmsMailTemplateExample() {
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

        public Criteria andMailNameIsNull() {
            addCriterion("mail_name is null");
            return (Criteria) this;
        }

        public Criteria andMailNameIsNotNull() {
            addCriterion("mail_name is not null");
            return (Criteria) this;
        }

        public Criteria andMailNameEqualTo(String value) {
            addCriterion("mail_name =", value, "mailName");
            return (Criteria) this;
        }

        public Criteria andMailNameNotEqualTo(String value) {
            addCriterion("mail_name <>", value, "mailName");
            return (Criteria) this;
        }

        public Criteria andMailNameGreaterThan(String value) {
            addCriterion("mail_name >", value, "mailName");
            return (Criteria) this;
        }

        public Criteria andMailNameGreaterThanOrEqualTo(String value) {
            addCriterion("mail_name >=", value, "mailName");
            return (Criteria) this;
        }

        public Criteria andMailNameLessThan(String value) {
            addCriterion("mail_name <", value, "mailName");
            return (Criteria) this;
        }

        public Criteria andMailNameLessThanOrEqualTo(String value) {
            addCriterion("mail_name <=", value, "mailName");
            return (Criteria) this;
        }

        public Criteria andMailNameLike(String value) {
            addCriterion("mail_name like", value, "mailName");
            return (Criteria) this;
        }

        public Criteria andMailNameNotLike(String value) {
            addCriterion("mail_name not like", value, "mailName");
            return (Criteria) this;
        }

        public Criteria andMailNameIn(List<String> values) {
            addCriterion("mail_name in", values, "mailName");
            return (Criteria) this;
        }

        public Criteria andMailNameNotIn(List<String> values) {
            addCriterion("mail_name not in", values, "mailName");
            return (Criteria) this;
        }

        public Criteria andMailNameBetween(String value1, String value2) {
            addCriterion("mail_name between", value1, value2, "mailName");
            return (Criteria) this;
        }

        public Criteria andMailNameNotBetween(String value1, String value2) {
            addCriterion("mail_name not between", value1, value2, "mailName");
            return (Criteria) this;
        }

        public Criteria andMailValueIsNull() {
            addCriterion("mail_value is null");
            return (Criteria) this;
        }

        public Criteria andMailValueIsNotNull() {
            addCriterion("mail_value is not null");
            return (Criteria) this;
        }

        public Criteria andMailValueEqualTo(String value) {
            addCriterion("mail_value =", value, "mailValue");
            return (Criteria) this;
        }

        public Criteria andMailValueNotEqualTo(String value) {
            addCriterion("mail_value <>", value, "mailValue");
            return (Criteria) this;
        }

        public Criteria andMailValueGreaterThan(String value) {
            addCriterion("mail_value >", value, "mailValue");
            return (Criteria) this;
        }

        public Criteria andMailValueGreaterThanOrEqualTo(String value) {
            addCriterion("mail_value >=", value, "mailValue");
            return (Criteria) this;
        }

        public Criteria andMailValueLessThan(String value) {
            addCriterion("mail_value <", value, "mailValue");
            return (Criteria) this;
        }

        public Criteria andMailValueLessThanOrEqualTo(String value) {
            addCriterion("mail_value <=", value, "mailValue");
            return (Criteria) this;
        }

        public Criteria andMailValueLike(String value) {
            addCriterion("mail_value like", value, "mailValue");
            return (Criteria) this;
        }

        public Criteria andMailValueNotLike(String value) {
            addCriterion("mail_value not like", value, "mailValue");
            return (Criteria) this;
        }

        public Criteria andMailValueIn(List<String> values) {
            addCriterion("mail_value in", values, "mailValue");
            return (Criteria) this;
        }

        public Criteria andMailValueNotIn(List<String> values) {
            addCriterion("mail_value not in", values, "mailValue");
            return (Criteria) this;
        }

        public Criteria andMailValueBetween(String value1, String value2) {
            addCriterion("mail_value between", value1, value2, "mailValue");
            return (Criteria) this;
        }

        public Criteria andMailValueNotBetween(String value1, String value2) {
            addCriterion("mail_value not between", value1, value2, "mailValue");
            return (Criteria) this;
        }

        public Criteria andMailStatusIsNull() {
            addCriterion("mail_status is null");
            return (Criteria) this;
        }

        public Criteria andMailStatusIsNotNull() {
            addCriterion("mail_status is not null");
            return (Criteria) this;
        }

        public Criteria andMailStatusEqualTo(Integer value) {
            addCriterion("mail_status =", value, "mailStatus");
            return (Criteria) this;
        }

        public Criteria andMailStatusNotEqualTo(Integer value) {
            addCriterion("mail_status <>", value, "mailStatus");
            return (Criteria) this;
        }

        public Criteria andMailStatusGreaterThan(Integer value) {
            addCriterion("mail_status >", value, "mailStatus");
            return (Criteria) this;
        }

        public Criteria andMailStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("mail_status >=", value, "mailStatus");
            return (Criteria) this;
        }

        public Criteria andMailStatusLessThan(Integer value) {
            addCriterion("mail_status <", value, "mailStatus");
            return (Criteria) this;
        }

        public Criteria andMailStatusLessThanOrEqualTo(Integer value) {
            addCriterion("mail_status <=", value, "mailStatus");
            return (Criteria) this;
        }

        public Criteria andMailStatusIn(List<Integer> values) {
            addCriterion("mail_status in", values, "mailStatus");
            return (Criteria) this;
        }

        public Criteria andMailStatusNotIn(List<Integer> values) {
            addCriterion("mail_status not in", values, "mailStatus");
            return (Criteria) this;
        }

        public Criteria andMailStatusBetween(Integer value1, Integer value2) {
            addCriterion("mail_status between", value1, value2, "mailStatus");
            return (Criteria) this;
        }

        public Criteria andMailStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("mail_status not between", value1, value2, "mailStatus");
            return (Criteria) this;
        }

        public Criteria andMailContentIsNull() {
            addCriterion("mail_content is null");
            return (Criteria) this;
        }

        public Criteria andMailContentIsNotNull() {
            addCriterion("mail_content is not null");
            return (Criteria) this;
        }

        public Criteria andMailContentEqualTo(String value) {
            addCriterion("mail_content =", value, "mailContent");
            return (Criteria) this;
        }

        public Criteria andMailContentNotEqualTo(String value) {
            addCriterion("mail_content <>", value, "mailContent");
            return (Criteria) this;
        }

        public Criteria andMailContentGreaterThan(String value) {
            addCriterion("mail_content >", value, "mailContent");
            return (Criteria) this;
        }

        public Criteria andMailContentGreaterThanOrEqualTo(String value) {
            addCriterion("mail_content >=", value, "mailContent");
            return (Criteria) this;
        }

        public Criteria andMailContentLessThan(String value) {
            addCriterion("mail_content <", value, "mailContent");
            return (Criteria) this;
        }

        public Criteria andMailContentLessThanOrEqualTo(String value) {
            addCriterion("mail_content <=", value, "mailContent");
            return (Criteria) this;
        }

        public Criteria andMailContentLike(String value) {
            addCriterion("mail_content like", value, "mailContent");
            return (Criteria) this;
        }

        public Criteria andMailContentNotLike(String value) {
            addCriterion("mail_content not like", value, "mailContent");
            return (Criteria) this;
        }

        public Criteria andMailContentIn(List<String> values) {
            addCriterion("mail_content in", values, "mailContent");
            return (Criteria) this;
        }

        public Criteria andMailContentNotIn(List<String> values) {
            addCriterion("mail_content not in", values, "mailContent");
            return (Criteria) this;
        }

        public Criteria andMailContentBetween(String value1, String value2) {
            addCriterion("mail_content between", value1, value2, "mailContent");
            return (Criteria) this;
        }

        public Criteria andMailContentNotBetween(String value1, String value2) {
            addCriterion("mail_content not between", value1, value2, "mailContent");
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

        public Criteria andCreateUserIdEqualTo(Integer value) {
            addCriterion("create_user_id =", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdNotEqualTo(Integer value) {
            addCriterion("create_user_id <>", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdGreaterThan(Integer value) {
            addCriterion("create_user_id >", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("create_user_id >=", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdLessThan(Integer value) {
            addCriterion("create_user_id <", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("create_user_id <=", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdIn(List<Integer> values) {
            addCriterion("create_user_id in", values, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdNotIn(List<Integer> values) {
            addCriterion("create_user_id not in", values, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdBetween(Integer value1, Integer value2) {
            addCriterion("create_user_id between", value1, value2, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("create_user_id not between", value1, value2, "createUserId");
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

        public Criteria andUpdateUserIdEqualTo(Integer value) {
            addCriterion("update_user_id =", value, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdNotEqualTo(Integer value) {
            addCriterion("update_user_id <>", value, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdGreaterThan(Integer value) {
            addCriterion("update_user_id >", value, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("update_user_id >=", value, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdLessThan(Integer value) {
            addCriterion("update_user_id <", value, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("update_user_id <=", value, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdIn(List<Integer> values) {
            addCriterion("update_user_id in", values, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdNotIn(List<Integer> values) {
            addCriterion("update_user_id not in", values, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdBetween(Integer value1, Integer value2) {
            addCriterion("update_user_id between", value1, value2, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("update_user_id not between", value1, value2, "updateUserId");
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