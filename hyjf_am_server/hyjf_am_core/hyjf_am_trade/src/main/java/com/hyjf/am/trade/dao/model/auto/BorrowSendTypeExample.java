package com.hyjf.am.trade.dao.model.auto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BorrowSendTypeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public BorrowSendTypeExample() {
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

        public Criteria andSendCdIsNull() {
            addCriterion("send_cd is null");
            return (Criteria) this;
        }

        public Criteria andSendCdIsNotNull() {
            addCriterion("send_cd is not null");
            return (Criteria) this;
        }

        public Criteria andSendCdEqualTo(String value) {
            addCriterion("send_cd =", value, "sendCd");
            return (Criteria) this;
        }

        public Criteria andSendCdNotEqualTo(String value) {
            addCriterion("send_cd <>", value, "sendCd");
            return (Criteria) this;
        }

        public Criteria andSendCdGreaterThan(String value) {
            addCriterion("send_cd >", value, "sendCd");
            return (Criteria) this;
        }

        public Criteria andSendCdGreaterThanOrEqualTo(String value) {
            addCriterion("send_cd >=", value, "sendCd");
            return (Criteria) this;
        }

        public Criteria andSendCdLessThan(String value) {
            addCriterion("send_cd <", value, "sendCd");
            return (Criteria) this;
        }

        public Criteria andSendCdLessThanOrEqualTo(String value) {
            addCriterion("send_cd <=", value, "sendCd");
            return (Criteria) this;
        }

        public Criteria andSendCdLike(String value) {
            addCriterion("send_cd like", value, "sendCd");
            return (Criteria) this;
        }

        public Criteria andSendCdNotLike(String value) {
            addCriterion("send_cd not like", value, "sendCd");
            return (Criteria) this;
        }

        public Criteria andSendCdIn(List<String> values) {
            addCriterion("send_cd in", values, "sendCd");
            return (Criteria) this;
        }

        public Criteria andSendCdNotIn(List<String> values) {
            addCriterion("send_cd not in", values, "sendCd");
            return (Criteria) this;
        }

        public Criteria andSendCdBetween(String value1, String value2) {
            addCriterion("send_cd between", value1, value2, "sendCd");
            return (Criteria) this;
        }

        public Criteria andSendCdNotBetween(String value1, String value2) {
            addCriterion("send_cd not between", value1, value2, "sendCd");
            return (Criteria) this;
        }

        public Criteria andSendNameIsNull() {
            addCriterion("send_name is null");
            return (Criteria) this;
        }

        public Criteria andSendNameIsNotNull() {
            addCriterion("send_name is not null");
            return (Criteria) this;
        }

        public Criteria andSendNameEqualTo(String value) {
            addCriterion("send_name =", value, "sendName");
            return (Criteria) this;
        }

        public Criteria andSendNameNotEqualTo(String value) {
            addCriterion("send_name <>", value, "sendName");
            return (Criteria) this;
        }

        public Criteria andSendNameGreaterThan(String value) {
            addCriterion("send_name >", value, "sendName");
            return (Criteria) this;
        }

        public Criteria andSendNameGreaterThanOrEqualTo(String value) {
            addCriterion("send_name >=", value, "sendName");
            return (Criteria) this;
        }

        public Criteria andSendNameLessThan(String value) {
            addCriterion("send_name <", value, "sendName");
            return (Criteria) this;
        }

        public Criteria andSendNameLessThanOrEqualTo(String value) {
            addCriterion("send_name <=", value, "sendName");
            return (Criteria) this;
        }

        public Criteria andSendNameLike(String value) {
            addCriterion("send_name like", value, "sendName");
            return (Criteria) this;
        }

        public Criteria andSendNameNotLike(String value) {
            addCriterion("send_name not like", value, "sendName");
            return (Criteria) this;
        }

        public Criteria andSendNameIn(List<String> values) {
            addCriterion("send_name in", values, "sendName");
            return (Criteria) this;
        }

        public Criteria andSendNameNotIn(List<String> values) {
            addCriterion("send_name not in", values, "sendName");
            return (Criteria) this;
        }

        public Criteria andSendNameBetween(String value1, String value2) {
            addCriterion("send_name between", value1, value2, "sendName");
            return (Criteria) this;
        }

        public Criteria andSendNameNotBetween(String value1, String value2) {
            addCriterion("send_name not between", value1, value2, "sendName");
            return (Criteria) this;
        }

        public Criteria andAfterTimeIsNull() {
            addCriterion("after_time is null");
            return (Criteria) this;
        }

        public Criteria andAfterTimeIsNotNull() {
            addCriterion("after_time is not null");
            return (Criteria) this;
        }

        public Criteria andAfterTimeEqualTo(Integer value) {
            addCriterion("after_time =", value, "afterTime");
            return (Criteria) this;
        }

        public Criteria andAfterTimeNotEqualTo(Integer value) {
            addCriterion("after_time <>", value, "afterTime");
            return (Criteria) this;
        }

        public Criteria andAfterTimeGreaterThan(Integer value) {
            addCriterion("after_time >", value, "afterTime");
            return (Criteria) this;
        }

        public Criteria andAfterTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("after_time >=", value, "afterTime");
            return (Criteria) this;
        }

        public Criteria andAfterTimeLessThan(Integer value) {
            addCriterion("after_time <", value, "afterTime");
            return (Criteria) this;
        }

        public Criteria andAfterTimeLessThanOrEqualTo(Integer value) {
            addCriterion("after_time <=", value, "afterTime");
            return (Criteria) this;
        }

        public Criteria andAfterTimeIn(List<Integer> values) {
            addCriterion("after_time in", values, "afterTime");
            return (Criteria) this;
        }

        public Criteria andAfterTimeNotIn(List<Integer> values) {
            addCriterion("after_time not in", values, "afterTime");
            return (Criteria) this;
        }

        public Criteria andAfterTimeBetween(Integer value1, Integer value2) {
            addCriterion("after_time between", value1, value2, "afterTime");
            return (Criteria) this;
        }

        public Criteria andAfterTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("after_time not between", value1, value2, "afterTime");
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