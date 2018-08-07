package com.hyjf.am.trade.dao.model.auto;

import java.util.ArrayList;
import java.util.List;

public class DebtHouseInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public DebtHouseInfoExample() {
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

        public Criteria andHousesTypeIsNull() {
            addCriterion("houses_type is null");
            return (Criteria) this;
        }

        public Criteria andHousesTypeIsNotNull() {
            addCriterion("houses_type is not null");
            return (Criteria) this;
        }

        public Criteria andHousesTypeEqualTo(String value) {
            addCriterion("houses_type =", value, "housesType");
            return (Criteria) this;
        }

        public Criteria andHousesTypeNotEqualTo(String value) {
            addCriterion("houses_type <>", value, "housesType");
            return (Criteria) this;
        }

        public Criteria andHousesTypeGreaterThan(String value) {
            addCriterion("houses_type >", value, "housesType");
            return (Criteria) this;
        }

        public Criteria andHousesTypeGreaterThanOrEqualTo(String value) {
            addCriterion("houses_type >=", value, "housesType");
            return (Criteria) this;
        }

        public Criteria andHousesTypeLessThan(String value) {
            addCriterion("houses_type <", value, "housesType");
            return (Criteria) this;
        }

        public Criteria andHousesTypeLessThanOrEqualTo(String value) {
            addCriterion("houses_type <=", value, "housesType");
            return (Criteria) this;
        }

        public Criteria andHousesTypeLike(String value) {
            addCriterion("houses_type like", value, "housesType");
            return (Criteria) this;
        }

        public Criteria andHousesTypeNotLike(String value) {
            addCriterion("houses_type not like", value, "housesType");
            return (Criteria) this;
        }

        public Criteria andHousesTypeIn(List<String> values) {
            addCriterion("houses_type in", values, "housesType");
            return (Criteria) this;
        }

        public Criteria andHousesTypeNotIn(List<String> values) {
            addCriterion("houses_type not in", values, "housesType");
            return (Criteria) this;
        }

        public Criteria andHousesTypeBetween(String value1, String value2) {
            addCriterion("houses_type between", value1, value2, "housesType");
            return (Criteria) this;
        }

        public Criteria andHousesTypeNotBetween(String value1, String value2) {
            addCriterion("houses_type not between", value1, value2, "housesType");
            return (Criteria) this;
        }

        public Criteria andHousesLocationIsNull() {
            addCriterion("houses_location is null");
            return (Criteria) this;
        }

        public Criteria andHousesLocationIsNotNull() {
            addCriterion("houses_location is not null");
            return (Criteria) this;
        }

        public Criteria andHousesLocationEqualTo(String value) {
            addCriterion("houses_location =", value, "housesLocation");
            return (Criteria) this;
        }

        public Criteria andHousesLocationNotEqualTo(String value) {
            addCriterion("houses_location <>", value, "housesLocation");
            return (Criteria) this;
        }

        public Criteria andHousesLocationGreaterThan(String value) {
            addCriterion("houses_location >", value, "housesLocation");
            return (Criteria) this;
        }

        public Criteria andHousesLocationGreaterThanOrEqualTo(String value) {
            addCriterion("houses_location >=", value, "housesLocation");
            return (Criteria) this;
        }

        public Criteria andHousesLocationLessThan(String value) {
            addCriterion("houses_location <", value, "housesLocation");
            return (Criteria) this;
        }

        public Criteria andHousesLocationLessThanOrEqualTo(String value) {
            addCriterion("houses_location <=", value, "housesLocation");
            return (Criteria) this;
        }

        public Criteria andHousesLocationLike(String value) {
            addCriterion("houses_location like", value, "housesLocation");
            return (Criteria) this;
        }

        public Criteria andHousesLocationNotLike(String value) {
            addCriterion("houses_location not like", value, "housesLocation");
            return (Criteria) this;
        }

        public Criteria andHousesLocationIn(List<String> values) {
            addCriterion("houses_location in", values, "housesLocation");
            return (Criteria) this;
        }

        public Criteria andHousesLocationNotIn(List<String> values) {
            addCriterion("houses_location not in", values, "housesLocation");
            return (Criteria) this;
        }

        public Criteria andHousesLocationBetween(String value1, String value2) {
            addCriterion("houses_location between", value1, value2, "housesLocation");
            return (Criteria) this;
        }

        public Criteria andHousesLocationNotBetween(String value1, String value2) {
            addCriterion("houses_location not between", value1, value2, "housesLocation");
            return (Criteria) this;
        }

        public Criteria andHousesAreaIsNull() {
            addCriterion("houses_area is null");
            return (Criteria) this;
        }

        public Criteria andHousesAreaIsNotNull() {
            addCriterion("houses_area is not null");
            return (Criteria) this;
        }

        public Criteria andHousesAreaEqualTo(String value) {
            addCriterion("houses_area =", value, "housesArea");
            return (Criteria) this;
        }

        public Criteria andHousesAreaNotEqualTo(String value) {
            addCriterion("houses_area <>", value, "housesArea");
            return (Criteria) this;
        }

        public Criteria andHousesAreaGreaterThan(String value) {
            addCriterion("houses_area >", value, "housesArea");
            return (Criteria) this;
        }

        public Criteria andHousesAreaGreaterThanOrEqualTo(String value) {
            addCriterion("houses_area >=", value, "housesArea");
            return (Criteria) this;
        }

        public Criteria andHousesAreaLessThan(String value) {
            addCriterion("houses_area <", value, "housesArea");
            return (Criteria) this;
        }

        public Criteria andHousesAreaLessThanOrEqualTo(String value) {
            addCriterion("houses_area <=", value, "housesArea");
            return (Criteria) this;
        }

        public Criteria andHousesAreaLike(String value) {
            addCriterion("houses_area like", value, "housesArea");
            return (Criteria) this;
        }

        public Criteria andHousesAreaNotLike(String value) {
            addCriterion("houses_area not like", value, "housesArea");
            return (Criteria) this;
        }

        public Criteria andHousesAreaIn(List<String> values) {
            addCriterion("houses_area in", values, "housesArea");
            return (Criteria) this;
        }

        public Criteria andHousesAreaNotIn(List<String> values) {
            addCriterion("houses_area not in", values, "housesArea");
            return (Criteria) this;
        }

        public Criteria andHousesAreaBetween(String value1, String value2) {
            addCriterion("houses_area between", value1, value2, "housesArea");
            return (Criteria) this;
        }

        public Criteria andHousesAreaNotBetween(String value1, String value2) {
            addCriterion("houses_area not between", value1, value2, "housesArea");
            return (Criteria) this;
        }

        public Criteria andHousesPriceIsNull() {
            addCriterion("houses_price is null");
            return (Criteria) this;
        }

        public Criteria andHousesPriceIsNotNull() {
            addCriterion("houses_price is not null");
            return (Criteria) this;
        }

        public Criteria andHousesPriceEqualTo(String value) {
            addCriterion("houses_price =", value, "housesPrice");
            return (Criteria) this;
        }

        public Criteria andHousesPriceNotEqualTo(String value) {
            addCriterion("houses_price <>", value, "housesPrice");
            return (Criteria) this;
        }

        public Criteria andHousesPriceGreaterThan(String value) {
            addCriterion("houses_price >", value, "housesPrice");
            return (Criteria) this;
        }

        public Criteria andHousesPriceGreaterThanOrEqualTo(String value) {
            addCriterion("houses_price >=", value, "housesPrice");
            return (Criteria) this;
        }

        public Criteria andHousesPriceLessThan(String value) {
            addCriterion("houses_price <", value, "housesPrice");
            return (Criteria) this;
        }

        public Criteria andHousesPriceLessThanOrEqualTo(String value) {
            addCriterion("houses_price <=", value, "housesPrice");
            return (Criteria) this;
        }

        public Criteria andHousesPriceLike(String value) {
            addCriterion("houses_price like", value, "housesPrice");
            return (Criteria) this;
        }

        public Criteria andHousesPriceNotLike(String value) {
            addCriterion("houses_price not like", value, "housesPrice");
            return (Criteria) this;
        }

        public Criteria andHousesPriceIn(List<String> values) {
            addCriterion("houses_price in", values, "housesPrice");
            return (Criteria) this;
        }

        public Criteria andHousesPriceNotIn(List<String> values) {
            addCriterion("houses_price not in", values, "housesPrice");
            return (Criteria) this;
        }

        public Criteria andHousesPriceBetween(String value1, String value2) {
            addCriterion("houses_price between", value1, value2, "housesPrice");
            return (Criteria) this;
        }

        public Criteria andHousesPriceNotBetween(String value1, String value2) {
            addCriterion("houses_price not between", value1, value2, "housesPrice");
            return (Criteria) this;
        }

        public Criteria andHousesTopriceIsNull() {
            addCriterion("houses_toprice is null");
            return (Criteria) this;
        }

        public Criteria andHousesTopriceIsNotNull() {
            addCriterion("houses_toprice is not null");
            return (Criteria) this;
        }

        public Criteria andHousesTopriceEqualTo(String value) {
            addCriterion("houses_toprice =", value, "housesToprice");
            return (Criteria) this;
        }

        public Criteria andHousesTopriceNotEqualTo(String value) {
            addCriterion("houses_toprice <>", value, "housesToprice");
            return (Criteria) this;
        }

        public Criteria andHousesTopriceGreaterThan(String value) {
            addCriterion("houses_toprice >", value, "housesToprice");
            return (Criteria) this;
        }

        public Criteria andHousesTopriceGreaterThanOrEqualTo(String value) {
            addCriterion("houses_toprice >=", value, "housesToprice");
            return (Criteria) this;
        }

        public Criteria andHousesTopriceLessThan(String value) {
            addCriterion("houses_toprice <", value, "housesToprice");
            return (Criteria) this;
        }

        public Criteria andHousesTopriceLessThanOrEqualTo(String value) {
            addCriterion("houses_toprice <=", value, "housesToprice");
            return (Criteria) this;
        }

        public Criteria andHousesTopriceLike(String value) {
            addCriterion("houses_toprice like", value, "housesToprice");
            return (Criteria) this;
        }

        public Criteria andHousesTopriceNotLike(String value) {
            addCriterion("houses_toprice not like", value, "housesToprice");
            return (Criteria) this;
        }

        public Criteria andHousesTopriceIn(List<String> values) {
            addCriterion("houses_toprice in", values, "housesToprice");
            return (Criteria) this;
        }

        public Criteria andHousesTopriceNotIn(List<String> values) {
            addCriterion("houses_toprice not in", values, "housesToprice");
            return (Criteria) this;
        }

        public Criteria andHousesTopriceBetween(String value1, String value2) {
            addCriterion("houses_toprice between", value1, value2, "housesToprice");
            return (Criteria) this;
        }

        public Criteria andHousesTopriceNotBetween(String value1, String value2) {
            addCriterion("houses_toprice not between", value1, value2, "housesToprice");
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

        public Criteria andBorrowPreNidEqualTo(Integer value) {
            addCriterion("borrow_pre_nid =", value, "borrowPreNid");
            return (Criteria) this;
        }

        public Criteria andBorrowPreNidNotEqualTo(Integer value) {
            addCriterion("borrow_pre_nid <>", value, "borrowPreNid");
            return (Criteria) this;
        }

        public Criteria andBorrowPreNidGreaterThan(Integer value) {
            addCriterion("borrow_pre_nid >", value, "borrowPreNid");
            return (Criteria) this;
        }

        public Criteria andBorrowPreNidGreaterThanOrEqualTo(Integer value) {
            addCriterion("borrow_pre_nid >=", value, "borrowPreNid");
            return (Criteria) this;
        }

        public Criteria andBorrowPreNidLessThan(Integer value) {
            addCriterion("borrow_pre_nid <", value, "borrowPreNid");
            return (Criteria) this;
        }

        public Criteria andBorrowPreNidLessThanOrEqualTo(Integer value) {
            addCriterion("borrow_pre_nid <=", value, "borrowPreNid");
            return (Criteria) this;
        }

        public Criteria andBorrowPreNidIn(List<Integer> values) {
            addCriterion("borrow_pre_nid in", values, "borrowPreNid");
            return (Criteria) this;
        }

        public Criteria andBorrowPreNidNotIn(List<Integer> values) {
            addCriterion("borrow_pre_nid not in", values, "borrowPreNid");
            return (Criteria) this;
        }

        public Criteria andBorrowPreNidBetween(Integer value1, Integer value2) {
            addCriterion("borrow_pre_nid between", value1, value2, "borrowPreNid");
            return (Criteria) this;
        }

        public Criteria andBorrowPreNidNotBetween(Integer value1, Integer value2) {
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