package com.hyjf.am.trade.dao.model.auto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AleveErrorLogExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public AleveErrorLogExample() {
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

        public Criteria andFilelineIsNull() {
            addCriterion("fileline is null");
            return (Criteria) this;
        }

        public Criteria andFilelineIsNotNull() {
            addCriterion("fileline is not null");
            return (Criteria) this;
        }

        public Criteria andFilelineEqualTo(Integer value) {
            addCriterion("fileline =", value, "fileline");
            return (Criteria) this;
        }

        public Criteria andFilelineNotEqualTo(Integer value) {
            addCriterion("fileline <>", value, "fileline");
            return (Criteria) this;
        }

        public Criteria andFilelineGreaterThan(Integer value) {
            addCriterion("fileline >", value, "fileline");
            return (Criteria) this;
        }

        public Criteria andFilelineGreaterThanOrEqualTo(Integer value) {
            addCriterion("fileline >=", value, "fileline");
            return (Criteria) this;
        }

        public Criteria andFilelineLessThan(Integer value) {
            addCriterion("fileline <", value, "fileline");
            return (Criteria) this;
        }

        public Criteria andFilelineLessThanOrEqualTo(Integer value) {
            addCriterion("fileline <=", value, "fileline");
            return (Criteria) this;
        }

        public Criteria andFilelineIn(List<Integer> values) {
            addCriterion("fileline in", values, "fileline");
            return (Criteria) this;
        }

        public Criteria andFilelineNotIn(List<Integer> values) {
            addCriterion("fileline not in", values, "fileline");
            return (Criteria) this;
        }

        public Criteria andFilelineBetween(Integer value1, Integer value2) {
            addCriterion("fileline between", value1, value2, "fileline");
            return (Criteria) this;
        }

        public Criteria andFilelineNotBetween(Integer value1, Integer value2) {
            addCriterion("fileline not between", value1, value2, "fileline");
            return (Criteria) this;
        }

        public Criteria andSavelineIsNull() {
            addCriterion("saveline is null");
            return (Criteria) this;
        }

        public Criteria andSavelineIsNotNull() {
            addCriterion("saveline is not null");
            return (Criteria) this;
        }

        public Criteria andSavelineEqualTo(Integer value) {
            addCriterion("saveline =", value, "saveline");
            return (Criteria) this;
        }

        public Criteria andSavelineNotEqualTo(Integer value) {
            addCriterion("saveline <>", value, "saveline");
            return (Criteria) this;
        }

        public Criteria andSavelineGreaterThan(Integer value) {
            addCriterion("saveline >", value, "saveline");
            return (Criteria) this;
        }

        public Criteria andSavelineGreaterThanOrEqualTo(Integer value) {
            addCriterion("saveline >=", value, "saveline");
            return (Criteria) this;
        }

        public Criteria andSavelineLessThan(Integer value) {
            addCriterion("saveline <", value, "saveline");
            return (Criteria) this;
        }

        public Criteria andSavelineLessThanOrEqualTo(Integer value) {
            addCriterion("saveline <=", value, "saveline");
            return (Criteria) this;
        }

        public Criteria andSavelineIn(List<Integer> values) {
            addCriterion("saveline in", values, "saveline");
            return (Criteria) this;
        }

        public Criteria andSavelineNotIn(List<Integer> values) {
            addCriterion("saveline not in", values, "saveline");
            return (Criteria) this;
        }

        public Criteria andSavelineBetween(Integer value1, Integer value2) {
            addCriterion("saveline between", value1, value2, "saveline");
            return (Criteria) this;
        }

        public Criteria andSavelineNotBetween(Integer value1, Integer value2) {
            addCriterion("saveline not between", value1, value2, "saveline");
            return (Criteria) this;
        }

        public Criteria andFilestringIsNull() {
            addCriterion("filestring is null");
            return (Criteria) this;
        }

        public Criteria andFilestringIsNotNull() {
            addCriterion("filestring is not null");
            return (Criteria) this;
        }

        public Criteria andFilestringEqualTo(String value) {
            addCriterion("filestring =", value, "filestring");
            return (Criteria) this;
        }

        public Criteria andFilestringNotEqualTo(String value) {
            addCriterion("filestring <>", value, "filestring");
            return (Criteria) this;
        }

        public Criteria andFilestringGreaterThan(String value) {
            addCriterion("filestring >", value, "filestring");
            return (Criteria) this;
        }

        public Criteria andFilestringGreaterThanOrEqualTo(String value) {
            addCriterion("filestring >=", value, "filestring");
            return (Criteria) this;
        }

        public Criteria andFilestringLessThan(String value) {
            addCriterion("filestring <", value, "filestring");
            return (Criteria) this;
        }

        public Criteria andFilestringLessThanOrEqualTo(String value) {
            addCriterion("filestring <=", value, "filestring");
            return (Criteria) this;
        }

        public Criteria andFilestringLike(String value) {
            addCriterion("filestring like", value, "filestring");
            return (Criteria) this;
        }

        public Criteria andFilestringNotLike(String value) {
            addCriterion("filestring not like", value, "filestring");
            return (Criteria) this;
        }

        public Criteria andFilestringIn(List<String> values) {
            addCriterion("filestring in", values, "filestring");
            return (Criteria) this;
        }

        public Criteria andFilestringNotIn(List<String> values) {
            addCriterion("filestring not in", values, "filestring");
            return (Criteria) this;
        }

        public Criteria andFilestringBetween(String value1, String value2) {
            addCriterion("filestring between", value1, value2, "filestring");
            return (Criteria) this;
        }

        public Criteria andFilestringNotBetween(String value1, String value2) {
            addCriterion("filestring not between", value1, value2, "filestring");
            return (Criteria) this;
        }

        public Criteria andFilestatsIsNull() {
            addCriterion("filestats is null");
            return (Criteria) this;
        }

        public Criteria andFilestatsIsNotNull() {
            addCriterion("filestats is not null");
            return (Criteria) this;
        }

        public Criteria andFilestatsEqualTo(String value) {
            addCriterion("filestats =", value, "filestats");
            return (Criteria) this;
        }

        public Criteria andFilestatsNotEqualTo(String value) {
            addCriterion("filestats <>", value, "filestats");
            return (Criteria) this;
        }

        public Criteria andFilestatsGreaterThan(String value) {
            addCriterion("filestats >", value, "filestats");
            return (Criteria) this;
        }

        public Criteria andFilestatsGreaterThanOrEqualTo(String value) {
            addCriterion("filestats >=", value, "filestats");
            return (Criteria) this;
        }

        public Criteria andFilestatsLessThan(String value) {
            addCriterion("filestats <", value, "filestats");
            return (Criteria) this;
        }

        public Criteria andFilestatsLessThanOrEqualTo(String value) {
            addCriterion("filestats <=", value, "filestats");
            return (Criteria) this;
        }

        public Criteria andFilestatsLike(String value) {
            addCriterion("filestats like", value, "filestats");
            return (Criteria) this;
        }

        public Criteria andFilestatsNotLike(String value) {
            addCriterion("filestats not like", value, "filestats");
            return (Criteria) this;
        }

        public Criteria andFilestatsIn(List<String> values) {
            addCriterion("filestats in", values, "filestats");
            return (Criteria) this;
        }

        public Criteria andFilestatsNotIn(List<String> values) {
            addCriterion("filestats not in", values, "filestats");
            return (Criteria) this;
        }

        public Criteria andFilestatsBetween(String value1, String value2) {
            addCriterion("filestats between", value1, value2, "filestats");
            return (Criteria) this;
        }

        public Criteria andFilestatsNotBetween(String value1, String value2) {
            addCriterion("filestats not between", value1, value2, "filestats");
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