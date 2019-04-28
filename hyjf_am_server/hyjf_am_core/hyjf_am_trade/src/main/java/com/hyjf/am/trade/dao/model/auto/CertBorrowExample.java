package com.hyjf.am.trade.dao.model.auto;

import java.util.ArrayList;
import java.util.List;

public class CertBorrowExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public CertBorrowExample() {
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

        public Criteria andCreditFlgIsNull() {
            addCriterion("credit_flg is null");
            return (Criteria) this;
        }

        public Criteria andCreditFlgIsNotNull() {
            addCriterion("credit_flg is not null");
            return (Criteria) this;
        }

        public Criteria andCreditFlgEqualTo(Integer value) {
            addCriterion("credit_flg =", value, "creditFlg");
            return (Criteria) this;
        }

        public Criteria andCreditFlgNotEqualTo(Integer value) {
            addCriterion("credit_flg <>", value, "creditFlg");
            return (Criteria) this;
        }

        public Criteria andCreditFlgGreaterThan(Integer value) {
            addCriterion("credit_flg >", value, "creditFlg");
            return (Criteria) this;
        }

        public Criteria andCreditFlgGreaterThanOrEqualTo(Integer value) {
            addCriterion("credit_flg >=", value, "creditFlg");
            return (Criteria) this;
        }

        public Criteria andCreditFlgLessThan(Integer value) {
            addCriterion("credit_flg <", value, "creditFlg");
            return (Criteria) this;
        }

        public Criteria andCreditFlgLessThanOrEqualTo(Integer value) {
            addCriterion("credit_flg <=", value, "creditFlg");
            return (Criteria) this;
        }

        public Criteria andCreditFlgIn(List<Integer> values) {
            addCriterion("credit_flg in", values, "creditFlg");
            return (Criteria) this;
        }

        public Criteria andCreditFlgNotIn(List<Integer> values) {
            addCriterion("credit_flg not in", values, "creditFlg");
            return (Criteria) this;
        }

        public Criteria andCreditFlgBetween(Integer value1, Integer value2) {
            addCriterion("credit_flg between", value1, value2, "creditFlg");
            return (Criteria) this;
        }

        public Criteria andCreditFlgNotBetween(Integer value1, Integer value2) {
            addCriterion("credit_flg not between", value1, value2, "creditFlg");
            return (Criteria) this;
        }

        public Criteria andIsConfigIsNull() {
            addCriterion("is_config is null");
            return (Criteria) this;
        }

        public Criteria andIsConfigIsNotNull() {
            addCriterion("is_config is not null");
            return (Criteria) this;
        }

        public Criteria andIsConfigEqualTo(Integer value) {
            addCriterion("is_config =", value, "isConfig");
            return (Criteria) this;
        }

        public Criteria andIsConfigNotEqualTo(Integer value) {
            addCriterion("is_config <>", value, "isConfig");
            return (Criteria) this;
        }

        public Criteria andIsConfigGreaterThan(Integer value) {
            addCriterion("is_config >", value, "isConfig");
            return (Criteria) this;
        }

        public Criteria andIsConfigGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_config >=", value, "isConfig");
            return (Criteria) this;
        }

        public Criteria andIsConfigLessThan(Integer value) {
            addCriterion("is_config <", value, "isConfig");
            return (Criteria) this;
        }

        public Criteria andIsConfigLessThanOrEqualTo(Integer value) {
            addCriterion("is_config <=", value, "isConfig");
            return (Criteria) this;
        }

        public Criteria andIsConfigIn(List<Integer> values) {
            addCriterion("is_config in", values, "isConfig");
            return (Criteria) this;
        }

        public Criteria andIsConfigNotIn(List<Integer> values) {
            addCriterion("is_config not in", values, "isConfig");
            return (Criteria) this;
        }

        public Criteria andIsConfigBetween(Integer value1, Integer value2) {
            addCriterion("is_config between", value1, value2, "isConfig");
            return (Criteria) this;
        }

        public Criteria andIsConfigNotBetween(Integer value1, Integer value2) {
            addCriterion("is_config not between", value1, value2, "isConfig");
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