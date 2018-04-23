package com.hyjf.am.config.dao.model.auto;

import java.util.ArrayList;
import java.util.List;

public class SmsConfigExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public SmsConfigExample() {
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

        public Criteria andMaxIpCountIsNull() {
            addCriterion("max_ip_count is null");
            return (Criteria) this;
        }

        public Criteria andMaxIpCountIsNotNull() {
            addCriterion("max_ip_count is not null");
            return (Criteria) this;
        }

        public Criteria andMaxIpCountEqualTo(Integer value) {
            addCriterion("max_ip_count =", value, "maxIpCount");
            return (Criteria) this;
        }

        public Criteria andMaxIpCountNotEqualTo(Integer value) {
            addCriterion("max_ip_count <>", value, "maxIpCount");
            return (Criteria) this;
        }

        public Criteria andMaxIpCountGreaterThan(Integer value) {
            addCriterion("max_ip_count >", value, "maxIpCount");
            return (Criteria) this;
        }

        public Criteria andMaxIpCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("max_ip_count >=", value, "maxIpCount");
            return (Criteria) this;
        }

        public Criteria andMaxIpCountLessThan(Integer value) {
            addCriterion("max_ip_count <", value, "maxIpCount");
            return (Criteria) this;
        }

        public Criteria andMaxIpCountLessThanOrEqualTo(Integer value) {
            addCriterion("max_ip_count <=", value, "maxIpCount");
            return (Criteria) this;
        }

        public Criteria andMaxIpCountIn(List<Integer> values) {
            addCriterion("max_ip_count in", values, "maxIpCount");
            return (Criteria) this;
        }

        public Criteria andMaxIpCountNotIn(List<Integer> values) {
            addCriterion("max_ip_count not in", values, "maxIpCount");
            return (Criteria) this;
        }

        public Criteria andMaxIpCountBetween(Integer value1, Integer value2) {
            addCriterion("max_ip_count between", value1, value2, "maxIpCount");
            return (Criteria) this;
        }

        public Criteria andMaxIpCountNotBetween(Integer value1, Integer value2) {
            addCriterion("max_ip_count not between", value1, value2, "maxIpCount");
            return (Criteria) this;
        }

        public Criteria andMaxMachineCountIsNull() {
            addCriterion("max_machine_count is null");
            return (Criteria) this;
        }

        public Criteria andMaxMachineCountIsNotNull() {
            addCriterion("max_machine_count is not null");
            return (Criteria) this;
        }

        public Criteria andMaxMachineCountEqualTo(Integer value) {
            addCriterion("max_machine_count =", value, "maxMachineCount");
            return (Criteria) this;
        }

        public Criteria andMaxMachineCountNotEqualTo(Integer value) {
            addCriterion("max_machine_count <>", value, "maxMachineCount");
            return (Criteria) this;
        }

        public Criteria andMaxMachineCountGreaterThan(Integer value) {
            addCriterion("max_machine_count >", value, "maxMachineCount");
            return (Criteria) this;
        }

        public Criteria andMaxMachineCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("max_machine_count >=", value, "maxMachineCount");
            return (Criteria) this;
        }

        public Criteria andMaxMachineCountLessThan(Integer value) {
            addCriterion("max_machine_count <", value, "maxMachineCount");
            return (Criteria) this;
        }

        public Criteria andMaxMachineCountLessThanOrEqualTo(Integer value) {
            addCriterion("max_machine_count <=", value, "maxMachineCount");
            return (Criteria) this;
        }

        public Criteria andMaxMachineCountIn(List<Integer> values) {
            addCriterion("max_machine_count in", values, "maxMachineCount");
            return (Criteria) this;
        }

        public Criteria andMaxMachineCountNotIn(List<Integer> values) {
            addCriterion("max_machine_count not in", values, "maxMachineCount");
            return (Criteria) this;
        }

        public Criteria andMaxMachineCountBetween(Integer value1, Integer value2) {
            addCriterion("max_machine_count between", value1, value2, "maxMachineCount");
            return (Criteria) this;
        }

        public Criteria andMaxMachineCountNotBetween(Integer value1, Integer value2) {
            addCriterion("max_machine_count not between", value1, value2, "maxMachineCount");
            return (Criteria) this;
        }

        public Criteria andMaxBrowserCountIsNull() {
            addCriterion("max_browser_count is null");
            return (Criteria) this;
        }

        public Criteria andMaxBrowserCountIsNotNull() {
            addCriterion("max_browser_count is not null");
            return (Criteria) this;
        }

        public Criteria andMaxBrowserCountEqualTo(Integer value) {
            addCriterion("max_browser_count =", value, "maxBrowserCount");
            return (Criteria) this;
        }

        public Criteria andMaxBrowserCountNotEqualTo(Integer value) {
            addCriterion("max_browser_count <>", value, "maxBrowserCount");
            return (Criteria) this;
        }

        public Criteria andMaxBrowserCountGreaterThan(Integer value) {
            addCriterion("max_browser_count >", value, "maxBrowserCount");
            return (Criteria) this;
        }

        public Criteria andMaxBrowserCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("max_browser_count >=", value, "maxBrowserCount");
            return (Criteria) this;
        }

        public Criteria andMaxBrowserCountLessThan(Integer value) {
            addCriterion("max_browser_count <", value, "maxBrowserCount");
            return (Criteria) this;
        }

        public Criteria andMaxBrowserCountLessThanOrEqualTo(Integer value) {
            addCriterion("max_browser_count <=", value, "maxBrowserCount");
            return (Criteria) this;
        }

        public Criteria andMaxBrowserCountIn(List<Integer> values) {
            addCriterion("max_browser_count in", values, "maxBrowserCount");
            return (Criteria) this;
        }

        public Criteria andMaxBrowserCountNotIn(List<Integer> values) {
            addCriterion("max_browser_count not in", values, "maxBrowserCount");
            return (Criteria) this;
        }

        public Criteria andMaxBrowserCountBetween(Integer value1, Integer value2) {
            addCriterion("max_browser_count between", value1, value2, "maxBrowserCount");
            return (Criteria) this;
        }

        public Criteria andMaxBrowserCountNotBetween(Integer value1, Integer value2) {
            addCriterion("max_browser_count not between", value1, value2, "maxBrowserCount");
            return (Criteria) this;
        }

        public Criteria andMaxPhoneCountIsNull() {
            addCriterion("max_phone_count is null");
            return (Criteria) this;
        }

        public Criteria andMaxPhoneCountIsNotNull() {
            addCriterion("max_phone_count is not null");
            return (Criteria) this;
        }

        public Criteria andMaxPhoneCountEqualTo(Integer value) {
            addCriterion("max_phone_count =", value, "maxPhoneCount");
            return (Criteria) this;
        }

        public Criteria andMaxPhoneCountNotEqualTo(Integer value) {
            addCriterion("max_phone_count <>", value, "maxPhoneCount");
            return (Criteria) this;
        }

        public Criteria andMaxPhoneCountGreaterThan(Integer value) {
            addCriterion("max_phone_count >", value, "maxPhoneCount");
            return (Criteria) this;
        }

        public Criteria andMaxPhoneCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("max_phone_count >=", value, "maxPhoneCount");
            return (Criteria) this;
        }

        public Criteria andMaxPhoneCountLessThan(Integer value) {
            addCriterion("max_phone_count <", value, "maxPhoneCount");
            return (Criteria) this;
        }

        public Criteria andMaxPhoneCountLessThanOrEqualTo(Integer value) {
            addCriterion("max_phone_count <=", value, "maxPhoneCount");
            return (Criteria) this;
        }

        public Criteria andMaxPhoneCountIn(List<Integer> values) {
            addCriterion("max_phone_count in", values, "maxPhoneCount");
            return (Criteria) this;
        }

        public Criteria andMaxPhoneCountNotIn(List<Integer> values) {
            addCriterion("max_phone_count not in", values, "maxPhoneCount");
            return (Criteria) this;
        }

        public Criteria andMaxPhoneCountBetween(Integer value1, Integer value2) {
            addCriterion("max_phone_count between", value1, value2, "maxPhoneCount");
            return (Criteria) this;
        }

        public Criteria andMaxPhoneCountNotBetween(Integer value1, Integer value2) {
            addCriterion("max_phone_count not between", value1, value2, "maxPhoneCount");
            return (Criteria) this;
        }

        public Criteria andMaxIntervalTimeIsNull() {
            addCriterion("max_interval_time is null");
            return (Criteria) this;
        }

        public Criteria andMaxIntervalTimeIsNotNull() {
            addCriterion("max_interval_time is not null");
            return (Criteria) this;
        }

        public Criteria andMaxIntervalTimeEqualTo(Integer value) {
            addCriterion("max_interval_time =", value, "maxIntervalTime");
            return (Criteria) this;
        }

        public Criteria andMaxIntervalTimeNotEqualTo(Integer value) {
            addCriterion("max_interval_time <>", value, "maxIntervalTime");
            return (Criteria) this;
        }

        public Criteria andMaxIntervalTimeGreaterThan(Integer value) {
            addCriterion("max_interval_time >", value, "maxIntervalTime");
            return (Criteria) this;
        }

        public Criteria andMaxIntervalTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("max_interval_time >=", value, "maxIntervalTime");
            return (Criteria) this;
        }

        public Criteria andMaxIntervalTimeLessThan(Integer value) {
            addCriterion("max_interval_time <", value, "maxIntervalTime");
            return (Criteria) this;
        }

        public Criteria andMaxIntervalTimeLessThanOrEqualTo(Integer value) {
            addCriterion("max_interval_time <=", value, "maxIntervalTime");
            return (Criteria) this;
        }

        public Criteria andMaxIntervalTimeIn(List<Integer> values) {
            addCriterion("max_interval_time in", values, "maxIntervalTime");
            return (Criteria) this;
        }

        public Criteria andMaxIntervalTimeNotIn(List<Integer> values) {
            addCriterion("max_interval_time not in", values, "maxIntervalTime");
            return (Criteria) this;
        }

        public Criteria andMaxIntervalTimeBetween(Integer value1, Integer value2) {
            addCriterion("max_interval_time between", value1, value2, "maxIntervalTime");
            return (Criteria) this;
        }

        public Criteria andMaxIntervalTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("max_interval_time not between", value1, value2, "maxIntervalTime");
            return (Criteria) this;
        }

        public Criteria andMaxValidTimeIsNull() {
            addCriterion("max_valid_time is null");
            return (Criteria) this;
        }

        public Criteria andMaxValidTimeIsNotNull() {
            addCriterion("max_valid_time is not null");
            return (Criteria) this;
        }

        public Criteria andMaxValidTimeEqualTo(Integer value) {
            addCriterion("max_valid_time =", value, "maxValidTime");
            return (Criteria) this;
        }

        public Criteria andMaxValidTimeNotEqualTo(Integer value) {
            addCriterion("max_valid_time <>", value, "maxValidTime");
            return (Criteria) this;
        }

        public Criteria andMaxValidTimeGreaterThan(Integer value) {
            addCriterion("max_valid_time >", value, "maxValidTime");
            return (Criteria) this;
        }

        public Criteria andMaxValidTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("max_valid_time >=", value, "maxValidTime");
            return (Criteria) this;
        }

        public Criteria andMaxValidTimeLessThan(Integer value) {
            addCriterion("max_valid_time <", value, "maxValidTime");
            return (Criteria) this;
        }

        public Criteria andMaxValidTimeLessThanOrEqualTo(Integer value) {
            addCriterion("max_valid_time <=", value, "maxValidTime");
            return (Criteria) this;
        }

        public Criteria andMaxValidTimeIn(List<Integer> values) {
            addCriterion("max_valid_time in", values, "maxValidTime");
            return (Criteria) this;
        }

        public Criteria andMaxValidTimeNotIn(List<Integer> values) {
            addCriterion("max_valid_time not in", values, "maxValidTime");
            return (Criteria) this;
        }

        public Criteria andMaxValidTimeBetween(Integer value1, Integer value2) {
            addCriterion("max_valid_time between", value1, value2, "maxValidTime");
            return (Criteria) this;
        }

        public Criteria andMaxValidTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("max_valid_time not between", value1, value2, "maxValidTime");
            return (Criteria) this;
        }

        public Criteria andNoticeToPhoneIsNull() {
            addCriterion("notice_to_phone is null");
            return (Criteria) this;
        }

        public Criteria andNoticeToPhoneIsNotNull() {
            addCriterion("notice_to_phone is not null");
            return (Criteria) this;
        }

        public Criteria andNoticeToPhoneEqualTo(String value) {
            addCriterion("notice_to_phone =", value, "noticeToPhone");
            return (Criteria) this;
        }

        public Criteria andNoticeToPhoneNotEqualTo(String value) {
            addCriterion("notice_to_phone <>", value, "noticeToPhone");
            return (Criteria) this;
        }

        public Criteria andNoticeToPhoneGreaterThan(String value) {
            addCriterion("notice_to_phone >", value, "noticeToPhone");
            return (Criteria) this;
        }

        public Criteria andNoticeToPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("notice_to_phone >=", value, "noticeToPhone");
            return (Criteria) this;
        }

        public Criteria andNoticeToPhoneLessThan(String value) {
            addCriterion("notice_to_phone <", value, "noticeToPhone");
            return (Criteria) this;
        }

        public Criteria andNoticeToPhoneLessThanOrEqualTo(String value) {
            addCriterion("notice_to_phone <=", value, "noticeToPhone");
            return (Criteria) this;
        }

        public Criteria andNoticeToPhoneLike(String value) {
            addCriterion("notice_to_phone like", value, "noticeToPhone");
            return (Criteria) this;
        }

        public Criteria andNoticeToPhoneNotLike(String value) {
            addCriterion("notice_to_phone not like", value, "noticeToPhone");
            return (Criteria) this;
        }

        public Criteria andNoticeToPhoneIn(List<String> values) {
            addCriterion("notice_to_phone in", values, "noticeToPhone");
            return (Criteria) this;
        }

        public Criteria andNoticeToPhoneNotIn(List<String> values) {
            addCriterion("notice_to_phone not in", values, "noticeToPhone");
            return (Criteria) this;
        }

        public Criteria andNoticeToPhoneBetween(String value1, String value2) {
            addCriterion("notice_to_phone between", value1, value2, "noticeToPhone");
            return (Criteria) this;
        }

        public Criteria andNoticeToPhoneNotBetween(String value1, String value2) {
            addCriterion("notice_to_phone not between", value1, value2, "noticeToPhone");
            return (Criteria) this;
        }

        public Criteria andNoticeToEmailIsNull() {
            addCriterion("notice_to_email is null");
            return (Criteria) this;
        }

        public Criteria andNoticeToEmailIsNotNull() {
            addCriterion("notice_to_email is not null");
            return (Criteria) this;
        }

        public Criteria andNoticeToEmailEqualTo(String value) {
            addCriterion("notice_to_email =", value, "noticeToEmail");
            return (Criteria) this;
        }

        public Criteria andNoticeToEmailNotEqualTo(String value) {
            addCriterion("notice_to_email <>", value, "noticeToEmail");
            return (Criteria) this;
        }

        public Criteria andNoticeToEmailGreaterThan(String value) {
            addCriterion("notice_to_email >", value, "noticeToEmail");
            return (Criteria) this;
        }

        public Criteria andNoticeToEmailGreaterThanOrEqualTo(String value) {
            addCriterion("notice_to_email >=", value, "noticeToEmail");
            return (Criteria) this;
        }

        public Criteria andNoticeToEmailLessThan(String value) {
            addCriterion("notice_to_email <", value, "noticeToEmail");
            return (Criteria) this;
        }

        public Criteria andNoticeToEmailLessThanOrEqualTo(String value) {
            addCriterion("notice_to_email <=", value, "noticeToEmail");
            return (Criteria) this;
        }

        public Criteria andNoticeToEmailLike(String value) {
            addCriterion("notice_to_email like", value, "noticeToEmail");
            return (Criteria) this;
        }

        public Criteria andNoticeToEmailNotLike(String value) {
            addCriterion("notice_to_email not like", value, "noticeToEmail");
            return (Criteria) this;
        }

        public Criteria andNoticeToEmailIn(List<String> values) {
            addCriterion("notice_to_email in", values, "noticeToEmail");
            return (Criteria) this;
        }

        public Criteria andNoticeToEmailNotIn(List<String> values) {
            addCriterion("notice_to_email not in", values, "noticeToEmail");
            return (Criteria) this;
        }

        public Criteria andNoticeToEmailBetween(String value1, String value2) {
            addCriterion("notice_to_email between", value1, value2, "noticeToEmail");
            return (Criteria) this;
        }

        public Criteria andNoticeToEmailNotBetween(String value1, String value2) {
            addCriterion("notice_to_email not between", value1, value2, "noticeToEmail");
            return (Criteria) this;
        }

        public Criteria andNoticeToTimeIsNull() {
            addCriterion("notice_to_time is null");
            return (Criteria) this;
        }

        public Criteria andNoticeToTimeIsNotNull() {
            addCriterion("notice_to_time is not null");
            return (Criteria) this;
        }

        public Criteria andNoticeToTimeEqualTo(Integer value) {
            addCriterion("notice_to_time =", value, "noticeToTime");
            return (Criteria) this;
        }

        public Criteria andNoticeToTimeNotEqualTo(Integer value) {
            addCriterion("notice_to_time <>", value, "noticeToTime");
            return (Criteria) this;
        }

        public Criteria andNoticeToTimeGreaterThan(Integer value) {
            addCriterion("notice_to_time >", value, "noticeToTime");
            return (Criteria) this;
        }

        public Criteria andNoticeToTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("notice_to_time >=", value, "noticeToTime");
            return (Criteria) this;
        }

        public Criteria andNoticeToTimeLessThan(Integer value) {
            addCriterion("notice_to_time <", value, "noticeToTime");
            return (Criteria) this;
        }

        public Criteria andNoticeToTimeLessThanOrEqualTo(Integer value) {
            addCriterion("notice_to_time <=", value, "noticeToTime");
            return (Criteria) this;
        }

        public Criteria andNoticeToTimeIn(List<Integer> values) {
            addCriterion("notice_to_time in", values, "noticeToTime");
            return (Criteria) this;
        }

        public Criteria andNoticeToTimeNotIn(List<Integer> values) {
            addCriterion("notice_to_time not in", values, "noticeToTime");
            return (Criteria) this;
        }

        public Criteria andNoticeToTimeBetween(Integer value1, Integer value2) {
            addCriterion("notice_to_time between", value1, value2, "noticeToTime");
            return (Criteria) this;
        }

        public Criteria andNoticeToTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("notice_to_time not between", value1, value2, "noticeToTime");
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