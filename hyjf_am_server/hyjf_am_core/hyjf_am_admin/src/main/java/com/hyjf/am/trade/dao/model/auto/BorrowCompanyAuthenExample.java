package com.hyjf.am.trade.dao.model.auto;

import java.util.ArrayList;
import java.util.List;

public class BorrowCompanyAuthenExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public BorrowCompanyAuthenExample() {
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

        public Criteria andAuthenNameIsNull() {
            addCriterion("authen_name is null");
            return (Criteria) this;
        }

        public Criteria andAuthenNameIsNotNull() {
            addCriterion("authen_name is not null");
            return (Criteria) this;
        }

        public Criteria andAuthenNameEqualTo(String value) {
            addCriterion("authen_name =", value, "authenName");
            return (Criteria) this;
        }

        public Criteria andAuthenNameNotEqualTo(String value) {
            addCriterion("authen_name <>", value, "authenName");
            return (Criteria) this;
        }

        public Criteria andAuthenNameGreaterThan(String value) {
            addCriterion("authen_name >", value, "authenName");
            return (Criteria) this;
        }

        public Criteria andAuthenNameGreaterThanOrEqualTo(String value) {
            addCriterion("authen_name >=", value, "authenName");
            return (Criteria) this;
        }

        public Criteria andAuthenNameLessThan(String value) {
            addCriterion("authen_name <", value, "authenName");
            return (Criteria) this;
        }

        public Criteria andAuthenNameLessThanOrEqualTo(String value) {
            addCriterion("authen_name <=", value, "authenName");
            return (Criteria) this;
        }

        public Criteria andAuthenNameLike(String value) {
            addCriterion("authen_name like", value, "authenName");
            return (Criteria) this;
        }

        public Criteria andAuthenNameNotLike(String value) {
            addCriterion("authen_name not like", value, "authenName");
            return (Criteria) this;
        }

        public Criteria andAuthenNameIn(List<String> values) {
            addCriterion("authen_name in", values, "authenName");
            return (Criteria) this;
        }

        public Criteria andAuthenNameNotIn(List<String> values) {
            addCriterion("authen_name not in", values, "authenName");
            return (Criteria) this;
        }

        public Criteria andAuthenNameBetween(String value1, String value2) {
            addCriterion("authen_name between", value1, value2, "authenName");
            return (Criteria) this;
        }

        public Criteria andAuthenNameNotBetween(String value1, String value2) {
            addCriterion("authen_name not between", value1, value2, "authenName");
            return (Criteria) this;
        }

        public Criteria andAuthenTimeIsNull() {
            addCriterion("authen_time is null");
            return (Criteria) this;
        }

        public Criteria andAuthenTimeIsNotNull() {
            addCriterion("authen_time is not null");
            return (Criteria) this;
        }

        public Criteria andAuthenTimeEqualTo(String value) {
            addCriterion("authen_time =", value, "authenTime");
            return (Criteria) this;
        }

        public Criteria andAuthenTimeNotEqualTo(String value) {
            addCriterion("authen_time <>", value, "authenTime");
            return (Criteria) this;
        }

        public Criteria andAuthenTimeGreaterThan(String value) {
            addCriterion("authen_time >", value, "authenTime");
            return (Criteria) this;
        }

        public Criteria andAuthenTimeGreaterThanOrEqualTo(String value) {
            addCriterion("authen_time >=", value, "authenTime");
            return (Criteria) this;
        }

        public Criteria andAuthenTimeLessThan(String value) {
            addCriterion("authen_time <", value, "authenTime");
            return (Criteria) this;
        }

        public Criteria andAuthenTimeLessThanOrEqualTo(String value) {
            addCriterion("authen_time <=", value, "authenTime");
            return (Criteria) this;
        }

        public Criteria andAuthenTimeLike(String value) {
            addCriterion("authen_time like", value, "authenTime");
            return (Criteria) this;
        }

        public Criteria andAuthenTimeNotLike(String value) {
            addCriterion("authen_time not like", value, "authenTime");
            return (Criteria) this;
        }

        public Criteria andAuthenTimeIn(List<String> values) {
            addCriterion("authen_time in", values, "authenTime");
            return (Criteria) this;
        }

        public Criteria andAuthenTimeNotIn(List<String> values) {
            addCriterion("authen_time not in", values, "authenTime");
            return (Criteria) this;
        }

        public Criteria andAuthenTimeBetween(String value1, String value2) {
            addCriterion("authen_time between", value1, value2, "authenTime");
            return (Criteria) this;
        }

        public Criteria andAuthenTimeNotBetween(String value1, String value2) {
            addCriterion("authen_time not between", value1, value2, "authenTime");
            return (Criteria) this;
        }

        public Criteria andAuthenSortKeyIsNull() {
            addCriterion("authen_sort_key is null");
            return (Criteria) this;
        }

        public Criteria andAuthenSortKeyIsNotNull() {
            addCriterion("authen_sort_key is not null");
            return (Criteria) this;
        }

        public Criteria andAuthenSortKeyEqualTo(Integer value) {
            addCriterion("authen_sort_key =", value, "authenSortKey");
            return (Criteria) this;
        }

        public Criteria andAuthenSortKeyNotEqualTo(Integer value) {
            addCriterion("authen_sort_key <>", value, "authenSortKey");
            return (Criteria) this;
        }

        public Criteria andAuthenSortKeyGreaterThan(Integer value) {
            addCriterion("authen_sort_key >", value, "authenSortKey");
            return (Criteria) this;
        }

        public Criteria andAuthenSortKeyGreaterThanOrEqualTo(Integer value) {
            addCriterion("authen_sort_key >=", value, "authenSortKey");
            return (Criteria) this;
        }

        public Criteria andAuthenSortKeyLessThan(Integer value) {
            addCriterion("authen_sort_key <", value, "authenSortKey");
            return (Criteria) this;
        }

        public Criteria andAuthenSortKeyLessThanOrEqualTo(Integer value) {
            addCriterion("authen_sort_key <=", value, "authenSortKey");
            return (Criteria) this;
        }

        public Criteria andAuthenSortKeyIn(List<Integer> values) {
            addCriterion("authen_sort_key in", values, "authenSortKey");
            return (Criteria) this;
        }

        public Criteria andAuthenSortKeyNotIn(List<Integer> values) {
            addCriterion("authen_sort_key not in", values, "authenSortKey");
            return (Criteria) this;
        }

        public Criteria andAuthenSortKeyBetween(Integer value1, Integer value2) {
            addCriterion("authen_sort_key between", value1, value2, "authenSortKey");
            return (Criteria) this;
        }

        public Criteria andAuthenSortKeyNotBetween(Integer value1, Integer value2) {
            addCriterion("authen_sort_key not between", value1, value2, "authenSortKey");
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

        public Criteria andBorrowPreNidIsNull() {
            addCriterion("borrow_pre_nid is null");
            return (Criteria) this;
        }

        public Criteria andBorrowPreNidIsNotNull() {
            addCriterion("borrow_pre_nid is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowPreNidEqualTo(String value) {
            addCriterion("borrow_pre_nid =", value, "borrowPreNid");
            return (Criteria) this;
        }

        public Criteria andBorrowPreNidNotEqualTo(String value) {
            addCriterion("borrow_pre_nid <>", value, "borrowPreNid");
            return (Criteria) this;
        }

        public Criteria andBorrowPreNidGreaterThan(String value) {
            addCriterion("borrow_pre_nid >", value, "borrowPreNid");
            return (Criteria) this;
        }

        public Criteria andBorrowPreNidGreaterThanOrEqualTo(String value) {
            addCriterion("borrow_pre_nid >=", value, "borrowPreNid");
            return (Criteria) this;
        }

        public Criteria andBorrowPreNidLessThan(String value) {
            addCriterion("borrow_pre_nid <", value, "borrowPreNid");
            return (Criteria) this;
        }

        public Criteria andBorrowPreNidLessThanOrEqualTo(String value) {
            addCriterion("borrow_pre_nid <=", value, "borrowPreNid");
            return (Criteria) this;
        }

        public Criteria andBorrowPreNidLike(String value) {
            addCriterion("borrow_pre_nid like", value, "borrowPreNid");
            return (Criteria) this;
        }

        public Criteria andBorrowPreNidNotLike(String value) {
            addCriterion("borrow_pre_nid not like", value, "borrowPreNid");
            return (Criteria) this;
        }

        public Criteria andBorrowPreNidIn(List<String> values) {
            addCriterion("borrow_pre_nid in", values, "borrowPreNid");
            return (Criteria) this;
        }

        public Criteria andBorrowPreNidNotIn(List<String> values) {
            addCriterion("borrow_pre_nid not in", values, "borrowPreNid");
            return (Criteria) this;
        }

        public Criteria andBorrowPreNidBetween(String value1, String value2) {
            addCriterion("borrow_pre_nid between", value1, value2, "borrowPreNid");
            return (Criteria) this;
        }

        public Criteria andBorrowPreNidNotBetween(String value1, String value2) {
            addCriterion("borrow_pre_nid not between", value1, value2, "borrowPreNid");
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