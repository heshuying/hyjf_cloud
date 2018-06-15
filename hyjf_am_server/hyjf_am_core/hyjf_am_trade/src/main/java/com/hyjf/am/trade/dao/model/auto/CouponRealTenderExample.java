package com.hyjf.am.trade.dao.model.auto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CouponRealTenderExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public CouponRealTenderExample() {
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

        public Criteria andCouponTenderIdIsNull() {
            addCriterion("coupon_tender_id is null");
            return (Criteria) this;
        }

        public Criteria andCouponTenderIdIsNotNull() {
            addCriterion("coupon_tender_id is not null");
            return (Criteria) this;
        }

        public Criteria andCouponTenderIdEqualTo(String value) {
            addCriterion("coupon_tender_id =", value, "couponTenderId");
            return (Criteria) this;
        }

        public Criteria andCouponTenderIdNotEqualTo(String value) {
            addCriterion("coupon_tender_id <>", value, "couponTenderId");
            return (Criteria) this;
        }

        public Criteria andCouponTenderIdGreaterThan(String value) {
            addCriterion("coupon_tender_id >", value, "couponTenderId");
            return (Criteria) this;
        }

        public Criteria andCouponTenderIdGreaterThanOrEqualTo(String value) {
            addCriterion("coupon_tender_id >=", value, "couponTenderId");
            return (Criteria) this;
        }

        public Criteria andCouponTenderIdLessThan(String value) {
            addCriterion("coupon_tender_id <", value, "couponTenderId");
            return (Criteria) this;
        }

        public Criteria andCouponTenderIdLessThanOrEqualTo(String value) {
            addCriterion("coupon_tender_id <=", value, "couponTenderId");
            return (Criteria) this;
        }

        public Criteria andCouponTenderIdLike(String value) {
            addCriterion("coupon_tender_id like", value, "couponTenderId");
            return (Criteria) this;
        }

        public Criteria andCouponTenderIdNotLike(String value) {
            addCriterion("coupon_tender_id not like", value, "couponTenderId");
            return (Criteria) this;
        }

        public Criteria andCouponTenderIdIn(List<String> values) {
            addCriterion("coupon_tender_id in", values, "couponTenderId");
            return (Criteria) this;
        }

        public Criteria andCouponTenderIdNotIn(List<String> values) {
            addCriterion("coupon_tender_id not in", values, "couponTenderId");
            return (Criteria) this;
        }

        public Criteria andCouponTenderIdBetween(String value1, String value2) {
            addCriterion("coupon_tender_id between", value1, value2, "couponTenderId");
            return (Criteria) this;
        }

        public Criteria andCouponTenderIdNotBetween(String value1, String value2) {
            addCriterion("coupon_tender_id not between", value1, value2, "couponTenderId");
            return (Criteria) this;
        }

        public Criteria andRealTenderIdIsNull() {
            addCriterion("real_tender_id is null");
            return (Criteria) this;
        }

        public Criteria andRealTenderIdIsNotNull() {
            addCriterion("real_tender_id is not null");
            return (Criteria) this;
        }

        public Criteria andRealTenderIdEqualTo(String value) {
            addCriterion("real_tender_id =", value, "realTenderId");
            return (Criteria) this;
        }

        public Criteria andRealTenderIdNotEqualTo(String value) {
            addCriterion("real_tender_id <>", value, "realTenderId");
            return (Criteria) this;
        }

        public Criteria andRealTenderIdGreaterThan(String value) {
            addCriterion("real_tender_id >", value, "realTenderId");
            return (Criteria) this;
        }

        public Criteria andRealTenderIdGreaterThanOrEqualTo(String value) {
            addCriterion("real_tender_id >=", value, "realTenderId");
            return (Criteria) this;
        }

        public Criteria andRealTenderIdLessThan(String value) {
            addCriterion("real_tender_id <", value, "realTenderId");
            return (Criteria) this;
        }

        public Criteria andRealTenderIdLessThanOrEqualTo(String value) {
            addCriterion("real_tender_id <=", value, "realTenderId");
            return (Criteria) this;
        }

        public Criteria andRealTenderIdLike(String value) {
            addCriterion("real_tender_id like", value, "realTenderId");
            return (Criteria) this;
        }

        public Criteria andRealTenderIdNotLike(String value) {
            addCriterion("real_tender_id not like", value, "realTenderId");
            return (Criteria) this;
        }

        public Criteria andRealTenderIdIn(List<String> values) {
            addCriterion("real_tender_id in", values, "realTenderId");
            return (Criteria) this;
        }

        public Criteria andRealTenderIdNotIn(List<String> values) {
            addCriterion("real_tender_id not in", values, "realTenderId");
            return (Criteria) this;
        }

        public Criteria andRealTenderIdBetween(String value1, String value2) {
            addCriterion("real_tender_id between", value1, value2, "realTenderId");
            return (Criteria) this;
        }

        public Criteria andRealTenderIdNotBetween(String value1, String value2) {
            addCriterion("real_tender_id not between", value1, value2, "realTenderId");
            return (Criteria) this;
        }

        public Criteria andAddUserIsNull() {
            addCriterion("add_user is null");
            return (Criteria) this;
        }

        public Criteria andAddUserIsNotNull() {
            addCriterion("add_user is not null");
            return (Criteria) this;
        }

        public Criteria andAddUserEqualTo(String value) {
            addCriterion("add_user =", value, "addUser");
            return (Criteria) this;
        }

        public Criteria andAddUserNotEqualTo(String value) {
            addCriterion("add_user <>", value, "addUser");
            return (Criteria) this;
        }

        public Criteria andAddUserGreaterThan(String value) {
            addCriterion("add_user >", value, "addUser");
            return (Criteria) this;
        }

        public Criteria andAddUserGreaterThanOrEqualTo(String value) {
            addCriterion("add_user >=", value, "addUser");
            return (Criteria) this;
        }

        public Criteria andAddUserLessThan(String value) {
            addCriterion("add_user <", value, "addUser");
            return (Criteria) this;
        }

        public Criteria andAddUserLessThanOrEqualTo(String value) {
            addCriterion("add_user <=", value, "addUser");
            return (Criteria) this;
        }

        public Criteria andAddUserLike(String value) {
            addCriterion("add_user like", value, "addUser");
            return (Criteria) this;
        }

        public Criteria andAddUserNotLike(String value) {
            addCriterion("add_user not like", value, "addUser");
            return (Criteria) this;
        }

        public Criteria andAddUserIn(List<String> values) {
            addCriterion("add_user in", values, "addUser");
            return (Criteria) this;
        }

        public Criteria andAddUserNotIn(List<String> values) {
            addCriterion("add_user not in", values, "addUser");
            return (Criteria) this;
        }

        public Criteria andAddUserBetween(String value1, String value2) {
            addCriterion("add_user between", value1, value2, "addUser");
            return (Criteria) this;
        }

        public Criteria andAddUserNotBetween(String value1, String value2) {
            addCriterion("add_user not between", value1, value2, "addUser");
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

        public Criteria andUpdateUserIsNull() {
            addCriterion("update_user is null");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIsNotNull() {
            addCriterion("update_user is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateUserEqualTo(String value) {
            addCriterion("update_user =", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotEqualTo(String value) {
            addCriterion("update_user <>", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserGreaterThan(String value) {
            addCriterion("update_user >", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserGreaterThanOrEqualTo(String value) {
            addCriterion("update_user >=", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLessThan(String value) {
            addCriterion("update_user <", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLessThanOrEqualTo(String value) {
            addCriterion("update_user <=", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLike(String value) {
            addCriterion("update_user like", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotLike(String value) {
            addCriterion("update_user not like", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIn(List<String> values) {
            addCriterion("update_user in", values, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotIn(List<String> values) {
            addCriterion("update_user not in", values, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserBetween(String value1, String value2) {
            addCriterion("update_user between", value1, value2, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotBetween(String value1, String value2) {
            addCriterion("update_user not between", value1, value2, "updateUser");
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