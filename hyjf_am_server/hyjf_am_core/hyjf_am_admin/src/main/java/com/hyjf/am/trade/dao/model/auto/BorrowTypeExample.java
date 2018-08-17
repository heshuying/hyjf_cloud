package com.hyjf.am.trade.dao.model.auto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BorrowTypeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public BorrowTypeExample() {
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

        public Criteria andNidIsNull() {
            addCriterion("nid is null");
            return (Criteria) this;
        }

        public Criteria andNidIsNotNull() {
            addCriterion("nid is not null");
            return (Criteria) this;
        }

        public Criteria andNidEqualTo(String value) {
            addCriterion("nid =", value, "nid");
            return (Criteria) this;
        }

        public Criteria andNidNotEqualTo(String value) {
            addCriterion("nid <>", value, "nid");
            return (Criteria) this;
        }

        public Criteria andNidGreaterThan(String value) {
            addCriterion("nid >", value, "nid");
            return (Criteria) this;
        }

        public Criteria andNidGreaterThanOrEqualTo(String value) {
            addCriterion("nid >=", value, "nid");
            return (Criteria) this;
        }

        public Criteria andNidLessThan(String value) {
            addCriterion("nid <", value, "nid");
            return (Criteria) this;
        }

        public Criteria andNidLessThanOrEqualTo(String value) {
            addCriterion("nid <=", value, "nid");
            return (Criteria) this;
        }

        public Criteria andNidLike(String value) {
            addCriterion("nid like", value, "nid");
            return (Criteria) this;
        }

        public Criteria andNidNotLike(String value) {
            addCriterion("nid not like", value, "nid");
            return (Criteria) this;
        }

        public Criteria andNidIn(List<String> values) {
            addCriterion("nid in", values, "nid");
            return (Criteria) this;
        }

        public Criteria andNidNotIn(List<String> values) {
            addCriterion("nid not in", values, "nid");
            return (Criteria) this;
        }

        public Criteria andNidBetween(String value1, String value2) {
            addCriterion("nid between", value1, value2, "nid");
            return (Criteria) this;
        }

        public Criteria andNidNotBetween(String value1, String value2) {
            addCriterion("nid not between", value1, value2, "nid");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("`status` is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("`status` is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("`status` =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("`status` <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("`status` >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("`status` >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("`status` <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("`status` <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("`status` in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("`status` not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("`status` between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("`status` not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("`name` is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("`name` is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("`name` =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("`name` <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("`name` >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("`name` >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("`name` <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("`name` <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("`name` like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("`name` not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("`name` in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("`name` not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("`name` between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("`name` not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andTitleIsNull() {
            addCriterion("title is null");
            return (Criteria) this;
        }

        public Criteria andTitleIsNotNull() {
            addCriterion("title is not null");
            return (Criteria) this;
        }

        public Criteria andTitleEqualTo(String value) {
            addCriterion("title =", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotEqualTo(String value) {
            addCriterion("title <>", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThan(String value) {
            addCriterion("title >", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThanOrEqualTo(String value) {
            addCriterion("title >=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThan(String value) {
            addCriterion("title <", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThanOrEqualTo(String value) {
            addCriterion("title <=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLike(String value) {
            addCriterion("title like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotLike(String value) {
            addCriterion("title not like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleIn(List<String> values) {
            addCriterion("title in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotIn(List<String> values) {
            addCriterion("title not in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleBetween(String value1, String value2) {
            addCriterion("title between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotBetween(String value1, String value2) {
            addCriterion("title not between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNull() {
            addCriterion("description is null");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNotNull() {
            addCriterion("description is not null");
            return (Criteria) this;
        }

        public Criteria andDescriptionEqualTo(String value) {
            addCriterion("description =", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotEqualTo(String value) {
            addCriterion("description <>", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThan(String value) {
            addCriterion("description >", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("description >=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThan(String value) {
            addCriterion("description <", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThanOrEqualTo(String value) {
            addCriterion("description <=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLike(String value) {
            addCriterion("description like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotLike(String value) {
            addCriterion("description not like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionIn(List<String> values) {
            addCriterion("description in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotIn(List<String> values) {
            addCriterion("description not in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionBetween(String value1, String value2) {
            addCriterion("description between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotBetween(String value1, String value2) {
            addCriterion("description not between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andAccountMultipleIsNull() {
            addCriterion("account_multiple is null");
            return (Criteria) this;
        }

        public Criteria andAccountMultipleIsNotNull() {
            addCriterion("account_multiple is not null");
            return (Criteria) this;
        }

        public Criteria andAccountMultipleEqualTo(Integer value) {
            addCriterion("account_multiple =", value, "accountMultiple");
            return (Criteria) this;
        }

        public Criteria andAccountMultipleNotEqualTo(Integer value) {
            addCriterion("account_multiple <>", value, "accountMultiple");
            return (Criteria) this;
        }

        public Criteria andAccountMultipleGreaterThan(Integer value) {
            addCriterion("account_multiple >", value, "accountMultiple");
            return (Criteria) this;
        }

        public Criteria andAccountMultipleGreaterThanOrEqualTo(Integer value) {
            addCriterion("account_multiple >=", value, "accountMultiple");
            return (Criteria) this;
        }

        public Criteria andAccountMultipleLessThan(Integer value) {
            addCriterion("account_multiple <", value, "accountMultiple");
            return (Criteria) this;
        }

        public Criteria andAccountMultipleLessThanOrEqualTo(Integer value) {
            addCriterion("account_multiple <=", value, "accountMultiple");
            return (Criteria) this;
        }

        public Criteria andAccountMultipleIn(List<Integer> values) {
            addCriterion("account_multiple in", values, "accountMultiple");
            return (Criteria) this;
        }

        public Criteria andAccountMultipleNotIn(List<Integer> values) {
            addCriterion("account_multiple not in", values, "accountMultiple");
            return (Criteria) this;
        }

        public Criteria andAccountMultipleBetween(Integer value1, Integer value2) {
            addCriterion("account_multiple between", value1, value2, "accountMultiple");
            return (Criteria) this;
        }

        public Criteria andAccountMultipleNotBetween(Integer value1, Integer value2) {
            addCriterion("account_multiple not between", value1, value2, "accountMultiple");
            return (Criteria) this;
        }

        public Criteria andPasswordStatusIsNull() {
            addCriterion("password_status is null");
            return (Criteria) this;
        }

        public Criteria andPasswordStatusIsNotNull() {
            addCriterion("password_status is not null");
            return (Criteria) this;
        }

        public Criteria andPasswordStatusEqualTo(Integer value) {
            addCriterion("password_status =", value, "passwordStatus");
            return (Criteria) this;
        }

        public Criteria andPasswordStatusNotEqualTo(Integer value) {
            addCriterion("password_status <>", value, "passwordStatus");
            return (Criteria) this;
        }

        public Criteria andPasswordStatusGreaterThan(Integer value) {
            addCriterion("password_status >", value, "passwordStatus");
            return (Criteria) this;
        }

        public Criteria andPasswordStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("password_status >=", value, "passwordStatus");
            return (Criteria) this;
        }

        public Criteria andPasswordStatusLessThan(Integer value) {
            addCriterion("password_status <", value, "passwordStatus");
            return (Criteria) this;
        }

        public Criteria andPasswordStatusLessThanOrEqualTo(Integer value) {
            addCriterion("password_status <=", value, "passwordStatus");
            return (Criteria) this;
        }

        public Criteria andPasswordStatusIn(List<Integer> values) {
            addCriterion("password_status in", values, "passwordStatus");
            return (Criteria) this;
        }

        public Criteria andPasswordStatusNotIn(List<Integer> values) {
            addCriterion("password_status not in", values, "passwordStatus");
            return (Criteria) this;
        }

        public Criteria andPasswordStatusBetween(Integer value1, Integer value2) {
            addCriterion("password_status between", value1, value2, "passwordStatus");
            return (Criteria) this;
        }

        public Criteria andPasswordStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("password_status not between", value1, value2, "passwordStatus");
            return (Criteria) this;
        }

        public Criteria andAmountTypeIsNull() {
            addCriterion("amount_type is null");
            return (Criteria) this;
        }

        public Criteria andAmountTypeIsNotNull() {
            addCriterion("amount_type is not null");
            return (Criteria) this;
        }

        public Criteria andAmountTypeEqualTo(String value) {
            addCriterion("amount_type =", value, "amountType");
            return (Criteria) this;
        }

        public Criteria andAmountTypeNotEqualTo(String value) {
            addCriterion("amount_type <>", value, "amountType");
            return (Criteria) this;
        }

        public Criteria andAmountTypeGreaterThan(String value) {
            addCriterion("amount_type >", value, "amountType");
            return (Criteria) this;
        }

        public Criteria andAmountTypeGreaterThanOrEqualTo(String value) {
            addCriterion("amount_type >=", value, "amountType");
            return (Criteria) this;
        }

        public Criteria andAmountTypeLessThan(String value) {
            addCriterion("amount_type <", value, "amountType");
            return (Criteria) this;
        }

        public Criteria andAmountTypeLessThanOrEqualTo(String value) {
            addCriterion("amount_type <=", value, "amountType");
            return (Criteria) this;
        }

        public Criteria andAmountTypeLike(String value) {
            addCriterion("amount_type like", value, "amountType");
            return (Criteria) this;
        }

        public Criteria andAmountTypeNotLike(String value) {
            addCriterion("amount_type not like", value, "amountType");
            return (Criteria) this;
        }

        public Criteria andAmountTypeIn(List<String> values) {
            addCriterion("amount_type in", values, "amountType");
            return (Criteria) this;
        }

        public Criteria andAmountTypeNotIn(List<String> values) {
            addCriterion("amount_type not in", values, "amountType");
            return (Criteria) this;
        }

        public Criteria andAmountTypeBetween(String value1, String value2) {
            addCriterion("amount_type between", value1, value2, "amountType");
            return (Criteria) this;
        }

        public Criteria andAmountTypeNotBetween(String value1, String value2) {
            addCriterion("amount_type not between", value1, value2, "amountType");
            return (Criteria) this;
        }

        public Criteria andAmountFirstIsNull() {
            addCriterion("amount_first is null");
            return (Criteria) this;
        }

        public Criteria andAmountFirstIsNotNull() {
            addCriterion("amount_first is not null");
            return (Criteria) this;
        }

        public Criteria andAmountFirstEqualTo(Integer value) {
            addCriterion("amount_first =", value, "amountFirst");
            return (Criteria) this;
        }

        public Criteria andAmountFirstNotEqualTo(Integer value) {
            addCriterion("amount_first <>", value, "amountFirst");
            return (Criteria) this;
        }

        public Criteria andAmountFirstGreaterThan(Integer value) {
            addCriterion("amount_first >", value, "amountFirst");
            return (Criteria) this;
        }

        public Criteria andAmountFirstGreaterThanOrEqualTo(Integer value) {
            addCriterion("amount_first >=", value, "amountFirst");
            return (Criteria) this;
        }

        public Criteria andAmountFirstLessThan(Integer value) {
            addCriterion("amount_first <", value, "amountFirst");
            return (Criteria) this;
        }

        public Criteria andAmountFirstLessThanOrEqualTo(Integer value) {
            addCriterion("amount_first <=", value, "amountFirst");
            return (Criteria) this;
        }

        public Criteria andAmountFirstIn(List<Integer> values) {
            addCriterion("amount_first in", values, "amountFirst");
            return (Criteria) this;
        }

        public Criteria andAmountFirstNotIn(List<Integer> values) {
            addCriterion("amount_first not in", values, "amountFirst");
            return (Criteria) this;
        }

        public Criteria andAmountFirstBetween(Integer value1, Integer value2) {
            addCriterion("amount_first between", value1, value2, "amountFirst");
            return (Criteria) this;
        }

        public Criteria andAmountFirstNotBetween(Integer value1, Integer value2) {
            addCriterion("amount_first not between", value1, value2, "amountFirst");
            return (Criteria) this;
        }

        public Criteria andAmountEndIsNull() {
            addCriterion("amount_end is null");
            return (Criteria) this;
        }

        public Criteria andAmountEndIsNotNull() {
            addCriterion("amount_end is not null");
            return (Criteria) this;
        }

        public Criteria andAmountEndEqualTo(Integer value) {
            addCriterion("amount_end =", value, "amountEnd");
            return (Criteria) this;
        }

        public Criteria andAmountEndNotEqualTo(Integer value) {
            addCriterion("amount_end <>", value, "amountEnd");
            return (Criteria) this;
        }

        public Criteria andAmountEndGreaterThan(Integer value) {
            addCriterion("amount_end >", value, "amountEnd");
            return (Criteria) this;
        }

        public Criteria andAmountEndGreaterThanOrEqualTo(Integer value) {
            addCriterion("amount_end >=", value, "amountEnd");
            return (Criteria) this;
        }

        public Criteria andAmountEndLessThan(Integer value) {
            addCriterion("amount_end <", value, "amountEnd");
            return (Criteria) this;
        }

        public Criteria andAmountEndLessThanOrEqualTo(Integer value) {
            addCriterion("amount_end <=", value, "amountEnd");
            return (Criteria) this;
        }

        public Criteria andAmountEndIn(List<Integer> values) {
            addCriterion("amount_end in", values, "amountEnd");
            return (Criteria) this;
        }

        public Criteria andAmountEndNotIn(List<Integer> values) {
            addCriterion("amount_end not in", values, "amountEnd");
            return (Criteria) this;
        }

        public Criteria andAmountEndBetween(Integer value1, Integer value2) {
            addCriterion("amount_end between", value1, value2, "amountEnd");
            return (Criteria) this;
        }

        public Criteria andAmountEndNotBetween(Integer value1, Integer value2) {
            addCriterion("amount_end not between", value1, value2, "amountEnd");
            return (Criteria) this;
        }

        public Criteria andFrostScaleVipIsNull() {
            addCriterion("frost_scale_vip is null");
            return (Criteria) this;
        }

        public Criteria andFrostScaleVipIsNotNull() {
            addCriterion("frost_scale_vip is not null");
            return (Criteria) this;
        }

        public Criteria andFrostScaleVipEqualTo(BigDecimal value) {
            addCriterion("frost_scale_vip =", value, "frostScaleVip");
            return (Criteria) this;
        }

        public Criteria andFrostScaleVipNotEqualTo(BigDecimal value) {
            addCriterion("frost_scale_vip <>", value, "frostScaleVip");
            return (Criteria) this;
        }

        public Criteria andFrostScaleVipGreaterThan(BigDecimal value) {
            addCriterion("frost_scale_vip >", value, "frostScaleVip");
            return (Criteria) this;
        }

        public Criteria andFrostScaleVipGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("frost_scale_vip >=", value, "frostScaleVip");
            return (Criteria) this;
        }

        public Criteria andFrostScaleVipLessThan(BigDecimal value) {
            addCriterion("frost_scale_vip <", value, "frostScaleVip");
            return (Criteria) this;
        }

        public Criteria andFrostScaleVipLessThanOrEqualTo(BigDecimal value) {
            addCriterion("frost_scale_vip <=", value, "frostScaleVip");
            return (Criteria) this;
        }

        public Criteria andFrostScaleVipIn(List<BigDecimal> values) {
            addCriterion("frost_scale_vip in", values, "frostScaleVip");
            return (Criteria) this;
        }

        public Criteria andFrostScaleVipNotIn(List<BigDecimal> values) {
            addCriterion("frost_scale_vip not in", values, "frostScaleVip");
            return (Criteria) this;
        }

        public Criteria andFrostScaleVipBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("frost_scale_vip between", value1, value2, "frostScaleVip");
            return (Criteria) this;
        }

        public Criteria andFrostScaleVipNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("frost_scale_vip not between", value1, value2, "frostScaleVip");
            return (Criteria) this;
        }

        public Criteria andAdminStatusIsNull() {
            addCriterion("admin_status is null");
            return (Criteria) this;
        }

        public Criteria andAdminStatusIsNotNull() {
            addCriterion("admin_status is not null");
            return (Criteria) this;
        }

        public Criteria andAdminStatusEqualTo(Integer value) {
            addCriterion("admin_status =", value, "adminStatus");
            return (Criteria) this;
        }

        public Criteria andAdminStatusNotEqualTo(Integer value) {
            addCriterion("admin_status <>", value, "adminStatus");
            return (Criteria) this;
        }

        public Criteria andAdminStatusGreaterThan(Integer value) {
            addCriterion("admin_status >", value, "adminStatus");
            return (Criteria) this;
        }

        public Criteria andAdminStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("admin_status >=", value, "adminStatus");
            return (Criteria) this;
        }

        public Criteria andAdminStatusLessThan(Integer value) {
            addCriterion("admin_status <", value, "adminStatus");
            return (Criteria) this;
        }

        public Criteria andAdminStatusLessThanOrEqualTo(Integer value) {
            addCriterion("admin_status <=", value, "adminStatus");
            return (Criteria) this;
        }

        public Criteria andAdminStatusIn(List<Integer> values) {
            addCriterion("admin_status in", values, "adminStatus");
            return (Criteria) this;
        }

        public Criteria andAdminStatusNotIn(List<Integer> values) {
            addCriterion("admin_status not in", values, "adminStatus");
            return (Criteria) this;
        }

        public Criteria andAdminStatusBetween(Integer value1, Integer value2) {
            addCriterion("admin_status between", value1, value2, "adminStatus");
            return (Criteria) this;
        }

        public Criteria andAdminStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("admin_status not between", value1, value2, "adminStatus");
            return (Criteria) this;
        }

        public Criteria andAprFirstIsNull() {
            addCriterion("apr_first is null");
            return (Criteria) this;
        }

        public Criteria andAprFirstIsNotNull() {
            addCriterion("apr_first is not null");
            return (Criteria) this;
        }

        public Criteria andAprFirstEqualTo(BigDecimal value) {
            addCriterion("apr_first =", value, "aprFirst");
            return (Criteria) this;
        }

        public Criteria andAprFirstNotEqualTo(BigDecimal value) {
            addCriterion("apr_first <>", value, "aprFirst");
            return (Criteria) this;
        }

        public Criteria andAprFirstGreaterThan(BigDecimal value) {
            addCriterion("apr_first >", value, "aprFirst");
            return (Criteria) this;
        }

        public Criteria andAprFirstGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("apr_first >=", value, "aprFirst");
            return (Criteria) this;
        }

        public Criteria andAprFirstLessThan(BigDecimal value) {
            addCriterion("apr_first <", value, "aprFirst");
            return (Criteria) this;
        }

        public Criteria andAprFirstLessThanOrEqualTo(BigDecimal value) {
            addCriterion("apr_first <=", value, "aprFirst");
            return (Criteria) this;
        }

        public Criteria andAprFirstIn(List<BigDecimal> values) {
            addCriterion("apr_first in", values, "aprFirst");
            return (Criteria) this;
        }

        public Criteria andAprFirstNotIn(List<BigDecimal> values) {
            addCriterion("apr_first not in", values, "aprFirst");
            return (Criteria) this;
        }

        public Criteria andAprFirstBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("apr_first between", value1, value2, "aprFirst");
            return (Criteria) this;
        }

        public Criteria andAprFirstNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("apr_first not between", value1, value2, "aprFirst");
            return (Criteria) this;
        }

        public Criteria andAprEndIsNull() {
            addCriterion("apr_end is null");
            return (Criteria) this;
        }

        public Criteria andAprEndIsNotNull() {
            addCriterion("apr_end is not null");
            return (Criteria) this;
        }

        public Criteria andAprEndEqualTo(BigDecimal value) {
            addCriterion("apr_end =", value, "aprEnd");
            return (Criteria) this;
        }

        public Criteria andAprEndNotEqualTo(BigDecimal value) {
            addCriterion("apr_end <>", value, "aprEnd");
            return (Criteria) this;
        }

        public Criteria andAprEndGreaterThan(BigDecimal value) {
            addCriterion("apr_end >", value, "aprEnd");
            return (Criteria) this;
        }

        public Criteria andAprEndGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("apr_end >=", value, "aprEnd");
            return (Criteria) this;
        }

        public Criteria andAprEndLessThan(BigDecimal value) {
            addCriterion("apr_end <", value, "aprEnd");
            return (Criteria) this;
        }

        public Criteria andAprEndLessThanOrEqualTo(BigDecimal value) {
            addCriterion("apr_end <=", value, "aprEnd");
            return (Criteria) this;
        }

        public Criteria andAprEndIn(List<BigDecimal> values) {
            addCriterion("apr_end in", values, "aprEnd");
            return (Criteria) this;
        }

        public Criteria andAprEndNotIn(List<BigDecimal> values) {
            addCriterion("apr_end not in", values, "aprEnd");
            return (Criteria) this;
        }

        public Criteria andAprEndBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("apr_end between", value1, value2, "aprEnd");
            return (Criteria) this;
        }

        public Criteria andAprEndNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("apr_end not between", value1, value2, "aprEnd");
            return (Criteria) this;
        }

        public Criteria andCheckFirstIsNull() {
            addCriterion("check_first is null");
            return (Criteria) this;
        }

        public Criteria andCheckFirstIsNotNull() {
            addCriterion("check_first is not null");
            return (Criteria) this;
        }

        public Criteria andCheckFirstEqualTo(Integer value) {
            addCriterion("check_first =", value, "checkFirst");
            return (Criteria) this;
        }

        public Criteria andCheckFirstNotEqualTo(Integer value) {
            addCriterion("check_first <>", value, "checkFirst");
            return (Criteria) this;
        }

        public Criteria andCheckFirstGreaterThan(Integer value) {
            addCriterion("check_first >", value, "checkFirst");
            return (Criteria) this;
        }

        public Criteria andCheckFirstGreaterThanOrEqualTo(Integer value) {
            addCriterion("check_first >=", value, "checkFirst");
            return (Criteria) this;
        }

        public Criteria andCheckFirstLessThan(Integer value) {
            addCriterion("check_first <", value, "checkFirst");
            return (Criteria) this;
        }

        public Criteria andCheckFirstLessThanOrEqualTo(Integer value) {
            addCriterion("check_first <=", value, "checkFirst");
            return (Criteria) this;
        }

        public Criteria andCheckFirstIn(List<Integer> values) {
            addCriterion("check_first in", values, "checkFirst");
            return (Criteria) this;
        }

        public Criteria andCheckFirstNotIn(List<Integer> values) {
            addCriterion("check_first not in", values, "checkFirst");
            return (Criteria) this;
        }

        public Criteria andCheckFirstBetween(Integer value1, Integer value2) {
            addCriterion("check_first between", value1, value2, "checkFirst");
            return (Criteria) this;
        }

        public Criteria andCheckFirstNotBetween(Integer value1, Integer value2) {
            addCriterion("check_first not between", value1, value2, "checkFirst");
            return (Criteria) this;
        }

        public Criteria andCheckEndIsNull() {
            addCriterion("check_end is null");
            return (Criteria) this;
        }

        public Criteria andCheckEndIsNotNull() {
            addCriterion("check_end is not null");
            return (Criteria) this;
        }

        public Criteria andCheckEndEqualTo(Integer value) {
            addCriterion("check_end =", value, "checkEnd");
            return (Criteria) this;
        }

        public Criteria andCheckEndNotEqualTo(Integer value) {
            addCriterion("check_end <>", value, "checkEnd");
            return (Criteria) this;
        }

        public Criteria andCheckEndGreaterThan(Integer value) {
            addCriterion("check_end >", value, "checkEnd");
            return (Criteria) this;
        }

        public Criteria andCheckEndGreaterThanOrEqualTo(Integer value) {
            addCriterion("check_end >=", value, "checkEnd");
            return (Criteria) this;
        }

        public Criteria andCheckEndLessThan(Integer value) {
            addCriterion("check_end <", value, "checkEnd");
            return (Criteria) this;
        }

        public Criteria andCheckEndLessThanOrEqualTo(Integer value) {
            addCriterion("check_end <=", value, "checkEnd");
            return (Criteria) this;
        }

        public Criteria andCheckEndIn(List<Integer> values) {
            addCriterion("check_end in", values, "checkEnd");
            return (Criteria) this;
        }

        public Criteria andCheckEndNotIn(List<Integer> values) {
            addCriterion("check_end not in", values, "checkEnd");
            return (Criteria) this;
        }

        public Criteria andCheckEndBetween(Integer value1, Integer value2) {
            addCriterion("check_end between", value1, value2, "checkEnd");
            return (Criteria) this;
        }

        public Criteria andCheckEndNotBetween(Integer value1, Integer value2) {
            addCriterion("check_end not between", value1, value2, "checkEnd");
            return (Criteria) this;
        }

        public Criteria andTenderAccountMinIsNull() {
            addCriterion("tender_account_min is null");
            return (Criteria) this;
        }

        public Criteria andTenderAccountMinIsNotNull() {
            addCriterion("tender_account_min is not null");
            return (Criteria) this;
        }

        public Criteria andTenderAccountMinEqualTo(String value) {
            addCriterion("tender_account_min =", value, "tenderAccountMin");
            return (Criteria) this;
        }

        public Criteria andTenderAccountMinNotEqualTo(String value) {
            addCriterion("tender_account_min <>", value, "tenderAccountMin");
            return (Criteria) this;
        }

        public Criteria andTenderAccountMinGreaterThan(String value) {
            addCriterion("tender_account_min >", value, "tenderAccountMin");
            return (Criteria) this;
        }

        public Criteria andTenderAccountMinGreaterThanOrEqualTo(String value) {
            addCriterion("tender_account_min >=", value, "tenderAccountMin");
            return (Criteria) this;
        }

        public Criteria andTenderAccountMinLessThan(String value) {
            addCriterion("tender_account_min <", value, "tenderAccountMin");
            return (Criteria) this;
        }

        public Criteria andTenderAccountMinLessThanOrEqualTo(String value) {
            addCriterion("tender_account_min <=", value, "tenderAccountMin");
            return (Criteria) this;
        }

        public Criteria andTenderAccountMinLike(String value) {
            addCriterion("tender_account_min like", value, "tenderAccountMin");
            return (Criteria) this;
        }

        public Criteria andTenderAccountMinNotLike(String value) {
            addCriterion("tender_account_min not like", value, "tenderAccountMin");
            return (Criteria) this;
        }

        public Criteria andTenderAccountMinIn(List<String> values) {
            addCriterion("tender_account_min in", values, "tenderAccountMin");
            return (Criteria) this;
        }

        public Criteria andTenderAccountMinNotIn(List<String> values) {
            addCriterion("tender_account_min not in", values, "tenderAccountMin");
            return (Criteria) this;
        }

        public Criteria andTenderAccountMinBetween(String value1, String value2) {
            addCriterion("tender_account_min between", value1, value2, "tenderAccountMin");
            return (Criteria) this;
        }

        public Criteria andTenderAccountMinNotBetween(String value1, String value2) {
            addCriterion("tender_account_min not between", value1, value2, "tenderAccountMin");
            return (Criteria) this;
        }

        public Criteria andTenderAccountMaxIsNull() {
            addCriterion("tender_account_max is null");
            return (Criteria) this;
        }

        public Criteria andTenderAccountMaxIsNotNull() {
            addCriterion("tender_account_max is not null");
            return (Criteria) this;
        }

        public Criteria andTenderAccountMaxEqualTo(String value) {
            addCriterion("tender_account_max =", value, "tenderAccountMax");
            return (Criteria) this;
        }

        public Criteria andTenderAccountMaxNotEqualTo(String value) {
            addCriterion("tender_account_max <>", value, "tenderAccountMax");
            return (Criteria) this;
        }

        public Criteria andTenderAccountMaxGreaterThan(String value) {
            addCriterion("tender_account_max >", value, "tenderAccountMax");
            return (Criteria) this;
        }

        public Criteria andTenderAccountMaxGreaterThanOrEqualTo(String value) {
            addCriterion("tender_account_max >=", value, "tenderAccountMax");
            return (Criteria) this;
        }

        public Criteria andTenderAccountMaxLessThan(String value) {
            addCriterion("tender_account_max <", value, "tenderAccountMax");
            return (Criteria) this;
        }

        public Criteria andTenderAccountMaxLessThanOrEqualTo(String value) {
            addCriterion("tender_account_max <=", value, "tenderAccountMax");
            return (Criteria) this;
        }

        public Criteria andTenderAccountMaxLike(String value) {
            addCriterion("tender_account_max like", value, "tenderAccountMax");
            return (Criteria) this;
        }

        public Criteria andTenderAccountMaxNotLike(String value) {
            addCriterion("tender_account_max not like", value, "tenderAccountMax");
            return (Criteria) this;
        }

        public Criteria andTenderAccountMaxIn(List<String> values) {
            addCriterion("tender_account_max in", values, "tenderAccountMax");
            return (Criteria) this;
        }

        public Criteria andTenderAccountMaxNotIn(List<String> values) {
            addCriterion("tender_account_max not in", values, "tenderAccountMax");
            return (Criteria) this;
        }

        public Criteria andTenderAccountMaxBetween(String value1, String value2) {
            addCriterion("tender_account_max between", value1, value2, "tenderAccountMax");
            return (Criteria) this;
        }

        public Criteria andTenderAccountMaxNotBetween(String value1, String value2) {
            addCriterion("tender_account_max not between", value1, value2, "tenderAccountMax");
            return (Criteria) this;
        }

        public Criteria andPeriodFirstIsNull() {
            addCriterion("period_first is null");
            return (Criteria) this;
        }

        public Criteria andPeriodFirstIsNotNull() {
            addCriterion("period_first is not null");
            return (Criteria) this;
        }

        public Criteria andPeriodFirstEqualTo(Integer value) {
            addCriterion("period_first =", value, "periodFirst");
            return (Criteria) this;
        }

        public Criteria andPeriodFirstNotEqualTo(Integer value) {
            addCriterion("period_first <>", value, "periodFirst");
            return (Criteria) this;
        }

        public Criteria andPeriodFirstGreaterThan(Integer value) {
            addCriterion("period_first >", value, "periodFirst");
            return (Criteria) this;
        }

        public Criteria andPeriodFirstGreaterThanOrEqualTo(Integer value) {
            addCriterion("period_first >=", value, "periodFirst");
            return (Criteria) this;
        }

        public Criteria andPeriodFirstLessThan(Integer value) {
            addCriterion("period_first <", value, "periodFirst");
            return (Criteria) this;
        }

        public Criteria andPeriodFirstLessThanOrEqualTo(Integer value) {
            addCriterion("period_first <=", value, "periodFirst");
            return (Criteria) this;
        }

        public Criteria andPeriodFirstIn(List<Integer> values) {
            addCriterion("period_first in", values, "periodFirst");
            return (Criteria) this;
        }

        public Criteria andPeriodFirstNotIn(List<Integer> values) {
            addCriterion("period_first not in", values, "periodFirst");
            return (Criteria) this;
        }

        public Criteria andPeriodFirstBetween(Integer value1, Integer value2) {
            addCriterion("period_first between", value1, value2, "periodFirst");
            return (Criteria) this;
        }

        public Criteria andPeriodFirstNotBetween(Integer value1, Integer value2) {
            addCriterion("period_first not between", value1, value2, "periodFirst");
            return (Criteria) this;
        }

        public Criteria andPeriodEndIsNull() {
            addCriterion("period_end is null");
            return (Criteria) this;
        }

        public Criteria andPeriodEndIsNotNull() {
            addCriterion("period_end is not null");
            return (Criteria) this;
        }

        public Criteria andPeriodEndEqualTo(Integer value) {
            addCriterion("period_end =", value, "periodEnd");
            return (Criteria) this;
        }

        public Criteria andPeriodEndNotEqualTo(Integer value) {
            addCriterion("period_end <>", value, "periodEnd");
            return (Criteria) this;
        }

        public Criteria andPeriodEndGreaterThan(Integer value) {
            addCriterion("period_end >", value, "periodEnd");
            return (Criteria) this;
        }

        public Criteria andPeriodEndGreaterThanOrEqualTo(Integer value) {
            addCriterion("period_end >=", value, "periodEnd");
            return (Criteria) this;
        }

        public Criteria andPeriodEndLessThan(Integer value) {
            addCriterion("period_end <", value, "periodEnd");
            return (Criteria) this;
        }

        public Criteria andPeriodEndLessThanOrEqualTo(Integer value) {
            addCriterion("period_end <=", value, "periodEnd");
            return (Criteria) this;
        }

        public Criteria andPeriodEndIn(List<Integer> values) {
            addCriterion("period_end in", values, "periodEnd");
            return (Criteria) this;
        }

        public Criteria andPeriodEndNotIn(List<Integer> values) {
            addCriterion("period_end not in", values, "periodEnd");
            return (Criteria) this;
        }

        public Criteria andPeriodEndBetween(Integer value1, Integer value2) {
            addCriterion("period_end between", value1, value2, "periodEnd");
            return (Criteria) this;
        }

        public Criteria andPeriodEndNotBetween(Integer value1, Integer value2) {
            addCriterion("period_end not between", value1, value2, "periodEnd");
            return (Criteria) this;
        }

        public Criteria andValidateFirstIsNull() {
            addCriterion("validate_first is null");
            return (Criteria) this;
        }

        public Criteria andValidateFirstIsNotNull() {
            addCriterion("validate_first is not null");
            return (Criteria) this;
        }

        public Criteria andValidateFirstEqualTo(Integer value) {
            addCriterion("validate_first =", value, "validateFirst");
            return (Criteria) this;
        }

        public Criteria andValidateFirstNotEqualTo(Integer value) {
            addCriterion("validate_first <>", value, "validateFirst");
            return (Criteria) this;
        }

        public Criteria andValidateFirstGreaterThan(Integer value) {
            addCriterion("validate_first >", value, "validateFirst");
            return (Criteria) this;
        }

        public Criteria andValidateFirstGreaterThanOrEqualTo(Integer value) {
            addCriterion("validate_first >=", value, "validateFirst");
            return (Criteria) this;
        }

        public Criteria andValidateFirstLessThan(Integer value) {
            addCriterion("validate_first <", value, "validateFirst");
            return (Criteria) this;
        }

        public Criteria andValidateFirstLessThanOrEqualTo(Integer value) {
            addCriterion("validate_first <=", value, "validateFirst");
            return (Criteria) this;
        }

        public Criteria andValidateFirstIn(List<Integer> values) {
            addCriterion("validate_first in", values, "validateFirst");
            return (Criteria) this;
        }

        public Criteria andValidateFirstNotIn(List<Integer> values) {
            addCriterion("validate_first not in", values, "validateFirst");
            return (Criteria) this;
        }

        public Criteria andValidateFirstBetween(Integer value1, Integer value2) {
            addCriterion("validate_first between", value1, value2, "validateFirst");
            return (Criteria) this;
        }

        public Criteria andValidateFirstNotBetween(Integer value1, Integer value2) {
            addCriterion("validate_first not between", value1, value2, "validateFirst");
            return (Criteria) this;
        }

        public Criteria andValidateEndIsNull() {
            addCriterion("validate_end is null");
            return (Criteria) this;
        }

        public Criteria andValidateEndIsNotNull() {
            addCriterion("validate_end is not null");
            return (Criteria) this;
        }

        public Criteria andValidateEndEqualTo(Integer value) {
            addCriterion("validate_end =", value, "validateEnd");
            return (Criteria) this;
        }

        public Criteria andValidateEndNotEqualTo(Integer value) {
            addCriterion("validate_end <>", value, "validateEnd");
            return (Criteria) this;
        }

        public Criteria andValidateEndGreaterThan(Integer value) {
            addCriterion("validate_end >", value, "validateEnd");
            return (Criteria) this;
        }

        public Criteria andValidateEndGreaterThanOrEqualTo(Integer value) {
            addCriterion("validate_end >=", value, "validateEnd");
            return (Criteria) this;
        }

        public Criteria andValidateEndLessThan(Integer value) {
            addCriterion("validate_end <", value, "validateEnd");
            return (Criteria) this;
        }

        public Criteria andValidateEndLessThanOrEqualTo(Integer value) {
            addCriterion("validate_end <=", value, "validateEnd");
            return (Criteria) this;
        }

        public Criteria andValidateEndIn(List<Integer> values) {
            addCriterion("validate_end in", values, "validateEnd");
            return (Criteria) this;
        }

        public Criteria andValidateEndNotIn(List<Integer> values) {
            addCriterion("validate_end not in", values, "validateEnd");
            return (Criteria) this;
        }

        public Criteria andValidateEndBetween(Integer value1, Integer value2) {
            addCriterion("validate_end between", value1, value2, "validateEnd");
            return (Criteria) this;
        }

        public Criteria andValidateEndNotBetween(Integer value1, Integer value2) {
            addCriterion("validate_end not between", value1, value2, "validateEnd");
            return (Criteria) this;
        }

        public Criteria andAwardStatusIsNull() {
            addCriterion("award_status is null");
            return (Criteria) this;
        }

        public Criteria andAwardStatusIsNotNull() {
            addCriterion("award_status is not null");
            return (Criteria) this;
        }

        public Criteria andAwardStatusEqualTo(Integer value) {
            addCriterion("award_status =", value, "awardStatus");
            return (Criteria) this;
        }

        public Criteria andAwardStatusNotEqualTo(Integer value) {
            addCriterion("award_status <>", value, "awardStatus");
            return (Criteria) this;
        }

        public Criteria andAwardStatusGreaterThan(Integer value) {
            addCriterion("award_status >", value, "awardStatus");
            return (Criteria) this;
        }

        public Criteria andAwardStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("award_status >=", value, "awardStatus");
            return (Criteria) this;
        }

        public Criteria andAwardStatusLessThan(Integer value) {
            addCriterion("award_status <", value, "awardStatus");
            return (Criteria) this;
        }

        public Criteria andAwardStatusLessThanOrEqualTo(Integer value) {
            addCriterion("award_status <=", value, "awardStatus");
            return (Criteria) this;
        }

        public Criteria andAwardStatusIn(List<Integer> values) {
            addCriterion("award_status in", values, "awardStatus");
            return (Criteria) this;
        }

        public Criteria andAwardStatusNotIn(List<Integer> values) {
            addCriterion("award_status not in", values, "awardStatus");
            return (Criteria) this;
        }

        public Criteria andAwardStatusBetween(Integer value1, Integer value2) {
            addCriterion("award_status between", value1, value2, "awardStatus");
            return (Criteria) this;
        }

        public Criteria andAwardStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("award_status not between", value1, value2, "awardStatus");
            return (Criteria) this;
        }

        public Criteria andAwardScaleFirstIsNull() {
            addCriterion("award_scale_first is null");
            return (Criteria) this;
        }

        public Criteria andAwardScaleFirstIsNotNull() {
            addCriterion("award_scale_first is not null");
            return (Criteria) this;
        }

        public Criteria andAwardScaleFirstEqualTo(BigDecimal value) {
            addCriterion("award_scale_first =", value, "awardScaleFirst");
            return (Criteria) this;
        }

        public Criteria andAwardScaleFirstNotEqualTo(BigDecimal value) {
            addCriterion("award_scale_first <>", value, "awardScaleFirst");
            return (Criteria) this;
        }

        public Criteria andAwardScaleFirstGreaterThan(BigDecimal value) {
            addCriterion("award_scale_first >", value, "awardScaleFirst");
            return (Criteria) this;
        }

        public Criteria andAwardScaleFirstGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("award_scale_first >=", value, "awardScaleFirst");
            return (Criteria) this;
        }

        public Criteria andAwardScaleFirstLessThan(BigDecimal value) {
            addCriterion("award_scale_first <", value, "awardScaleFirst");
            return (Criteria) this;
        }

        public Criteria andAwardScaleFirstLessThanOrEqualTo(BigDecimal value) {
            addCriterion("award_scale_first <=", value, "awardScaleFirst");
            return (Criteria) this;
        }

        public Criteria andAwardScaleFirstIn(List<BigDecimal> values) {
            addCriterion("award_scale_first in", values, "awardScaleFirst");
            return (Criteria) this;
        }

        public Criteria andAwardScaleFirstNotIn(List<BigDecimal> values) {
            addCriterion("award_scale_first not in", values, "awardScaleFirst");
            return (Criteria) this;
        }

        public Criteria andAwardScaleFirstBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("award_scale_first between", value1, value2, "awardScaleFirst");
            return (Criteria) this;
        }

        public Criteria andAwardScaleFirstNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("award_scale_first not between", value1, value2, "awardScaleFirst");
            return (Criteria) this;
        }

        public Criteria andAwardScaleEndIsNull() {
            addCriterion("award_scale_end is null");
            return (Criteria) this;
        }

        public Criteria andAwardScaleEndIsNotNull() {
            addCriterion("award_scale_end is not null");
            return (Criteria) this;
        }

        public Criteria andAwardScaleEndEqualTo(BigDecimal value) {
            addCriterion("award_scale_end =", value, "awardScaleEnd");
            return (Criteria) this;
        }

        public Criteria andAwardScaleEndNotEqualTo(BigDecimal value) {
            addCriterion("award_scale_end <>", value, "awardScaleEnd");
            return (Criteria) this;
        }

        public Criteria andAwardScaleEndGreaterThan(BigDecimal value) {
            addCriterion("award_scale_end >", value, "awardScaleEnd");
            return (Criteria) this;
        }

        public Criteria andAwardScaleEndGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("award_scale_end >=", value, "awardScaleEnd");
            return (Criteria) this;
        }

        public Criteria andAwardScaleEndLessThan(BigDecimal value) {
            addCriterion("award_scale_end <", value, "awardScaleEnd");
            return (Criteria) this;
        }

        public Criteria andAwardScaleEndLessThanOrEqualTo(BigDecimal value) {
            addCriterion("award_scale_end <=", value, "awardScaleEnd");
            return (Criteria) this;
        }

        public Criteria andAwardScaleEndIn(List<BigDecimal> values) {
            addCriterion("award_scale_end in", values, "awardScaleEnd");
            return (Criteria) this;
        }

        public Criteria andAwardScaleEndNotIn(List<BigDecimal> values) {
            addCriterion("award_scale_end not in", values, "awardScaleEnd");
            return (Criteria) this;
        }

        public Criteria andAwardScaleEndBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("award_scale_end between", value1, value2, "awardScaleEnd");
            return (Criteria) this;
        }

        public Criteria andAwardScaleEndNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("award_scale_end not between", value1, value2, "awardScaleEnd");
            return (Criteria) this;
        }

        public Criteria andAwardAccountFirstIsNull() {
            addCriterion("award_account_first is null");
            return (Criteria) this;
        }

        public Criteria andAwardAccountFirstIsNotNull() {
            addCriterion("award_account_first is not null");
            return (Criteria) this;
        }

        public Criteria andAwardAccountFirstEqualTo(BigDecimal value) {
            addCriterion("award_account_first =", value, "awardAccountFirst");
            return (Criteria) this;
        }

        public Criteria andAwardAccountFirstNotEqualTo(BigDecimal value) {
            addCriterion("award_account_first <>", value, "awardAccountFirst");
            return (Criteria) this;
        }

        public Criteria andAwardAccountFirstGreaterThan(BigDecimal value) {
            addCriterion("award_account_first >", value, "awardAccountFirst");
            return (Criteria) this;
        }

        public Criteria andAwardAccountFirstGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("award_account_first >=", value, "awardAccountFirst");
            return (Criteria) this;
        }

        public Criteria andAwardAccountFirstLessThan(BigDecimal value) {
            addCriterion("award_account_first <", value, "awardAccountFirst");
            return (Criteria) this;
        }

        public Criteria andAwardAccountFirstLessThanOrEqualTo(BigDecimal value) {
            addCriterion("award_account_first <=", value, "awardAccountFirst");
            return (Criteria) this;
        }

        public Criteria andAwardAccountFirstIn(List<BigDecimal> values) {
            addCriterion("award_account_first in", values, "awardAccountFirst");
            return (Criteria) this;
        }

        public Criteria andAwardAccountFirstNotIn(List<BigDecimal> values) {
            addCriterion("award_account_first not in", values, "awardAccountFirst");
            return (Criteria) this;
        }

        public Criteria andAwardAccountFirstBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("award_account_first between", value1, value2, "awardAccountFirst");
            return (Criteria) this;
        }

        public Criteria andAwardAccountFirstNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("award_account_first not between", value1, value2, "awardAccountFirst");
            return (Criteria) this;
        }

        public Criteria andAwardAccountEndIsNull() {
            addCriterion("award_account_end is null");
            return (Criteria) this;
        }

        public Criteria andAwardAccountEndIsNotNull() {
            addCriterion("award_account_end is not null");
            return (Criteria) this;
        }

        public Criteria andAwardAccountEndEqualTo(BigDecimal value) {
            addCriterion("award_account_end =", value, "awardAccountEnd");
            return (Criteria) this;
        }

        public Criteria andAwardAccountEndNotEqualTo(BigDecimal value) {
            addCriterion("award_account_end <>", value, "awardAccountEnd");
            return (Criteria) this;
        }

        public Criteria andAwardAccountEndGreaterThan(BigDecimal value) {
            addCriterion("award_account_end >", value, "awardAccountEnd");
            return (Criteria) this;
        }

        public Criteria andAwardAccountEndGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("award_account_end >=", value, "awardAccountEnd");
            return (Criteria) this;
        }

        public Criteria andAwardAccountEndLessThan(BigDecimal value) {
            addCriterion("award_account_end <", value, "awardAccountEnd");
            return (Criteria) this;
        }

        public Criteria andAwardAccountEndLessThanOrEqualTo(BigDecimal value) {
            addCriterion("award_account_end <=", value, "awardAccountEnd");
            return (Criteria) this;
        }

        public Criteria andAwardAccountEndIn(List<BigDecimal> values) {
            addCriterion("award_account_end in", values, "awardAccountEnd");
            return (Criteria) this;
        }

        public Criteria andAwardAccountEndNotIn(List<BigDecimal> values) {
            addCriterion("award_account_end not in", values, "awardAccountEnd");
            return (Criteria) this;
        }

        public Criteria andAwardAccountEndBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("award_account_end between", value1, value2, "awardAccountEnd");
            return (Criteria) this;
        }

        public Criteria andAwardAccountEndNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("award_account_end not between", value1, value2, "awardAccountEnd");
            return (Criteria) this;
        }

        public Criteria andAwardFalseStatusIsNull() {
            addCriterion("award_false_status is null");
            return (Criteria) this;
        }

        public Criteria andAwardFalseStatusIsNotNull() {
            addCriterion("award_false_status is not null");
            return (Criteria) this;
        }

        public Criteria andAwardFalseStatusEqualTo(Integer value) {
            addCriterion("award_false_status =", value, "awardFalseStatus");
            return (Criteria) this;
        }

        public Criteria andAwardFalseStatusNotEqualTo(Integer value) {
            addCriterion("award_false_status <>", value, "awardFalseStatus");
            return (Criteria) this;
        }

        public Criteria andAwardFalseStatusGreaterThan(Integer value) {
            addCriterion("award_false_status >", value, "awardFalseStatus");
            return (Criteria) this;
        }

        public Criteria andAwardFalseStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("award_false_status >=", value, "awardFalseStatus");
            return (Criteria) this;
        }

        public Criteria andAwardFalseStatusLessThan(Integer value) {
            addCriterion("award_false_status <", value, "awardFalseStatus");
            return (Criteria) this;
        }

        public Criteria andAwardFalseStatusLessThanOrEqualTo(Integer value) {
            addCriterion("award_false_status <=", value, "awardFalseStatus");
            return (Criteria) this;
        }

        public Criteria andAwardFalseStatusIn(List<Integer> values) {
            addCriterion("award_false_status in", values, "awardFalseStatus");
            return (Criteria) this;
        }

        public Criteria andAwardFalseStatusNotIn(List<Integer> values) {
            addCriterion("award_false_status not in", values, "awardFalseStatus");
            return (Criteria) this;
        }

        public Criteria andAwardFalseStatusBetween(Integer value1, Integer value2) {
            addCriterion("award_false_status between", value1, value2, "awardFalseStatus");
            return (Criteria) this;
        }

        public Criteria andAwardFalseStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("award_false_status not between", value1, value2, "awardFalseStatus");
            return (Criteria) this;
        }

        public Criteria andVerifyAutoStatusIsNull() {
            addCriterion("verify_auto_status is null");
            return (Criteria) this;
        }

        public Criteria andVerifyAutoStatusIsNotNull() {
            addCriterion("verify_auto_status is not null");
            return (Criteria) this;
        }

        public Criteria andVerifyAutoStatusEqualTo(Integer value) {
            addCriterion("verify_auto_status =", value, "verifyAutoStatus");
            return (Criteria) this;
        }

        public Criteria andVerifyAutoStatusNotEqualTo(Integer value) {
            addCriterion("verify_auto_status <>", value, "verifyAutoStatus");
            return (Criteria) this;
        }

        public Criteria andVerifyAutoStatusGreaterThan(Integer value) {
            addCriterion("verify_auto_status >", value, "verifyAutoStatus");
            return (Criteria) this;
        }

        public Criteria andVerifyAutoStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("verify_auto_status >=", value, "verifyAutoStatus");
            return (Criteria) this;
        }

        public Criteria andVerifyAutoStatusLessThan(Integer value) {
            addCriterion("verify_auto_status <", value, "verifyAutoStatus");
            return (Criteria) this;
        }

        public Criteria andVerifyAutoStatusLessThanOrEqualTo(Integer value) {
            addCriterion("verify_auto_status <=", value, "verifyAutoStatus");
            return (Criteria) this;
        }

        public Criteria andVerifyAutoStatusIn(List<Integer> values) {
            addCriterion("verify_auto_status in", values, "verifyAutoStatus");
            return (Criteria) this;
        }

        public Criteria andVerifyAutoStatusNotIn(List<Integer> values) {
            addCriterion("verify_auto_status not in", values, "verifyAutoStatus");
            return (Criteria) this;
        }

        public Criteria andVerifyAutoStatusBetween(Integer value1, Integer value2) {
            addCriterion("verify_auto_status between", value1, value2, "verifyAutoStatus");
            return (Criteria) this;
        }

        public Criteria andVerifyAutoStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("verify_auto_status not between", value1, value2, "verifyAutoStatus");
            return (Criteria) this;
        }

        public Criteria andVerifyAutoRemarkIsNull() {
            addCriterion("verify_auto_remark is null");
            return (Criteria) this;
        }

        public Criteria andVerifyAutoRemarkIsNotNull() {
            addCriterion("verify_auto_remark is not null");
            return (Criteria) this;
        }

        public Criteria andVerifyAutoRemarkEqualTo(String value) {
            addCriterion("verify_auto_remark =", value, "verifyAutoRemark");
            return (Criteria) this;
        }

        public Criteria andVerifyAutoRemarkNotEqualTo(String value) {
            addCriterion("verify_auto_remark <>", value, "verifyAutoRemark");
            return (Criteria) this;
        }

        public Criteria andVerifyAutoRemarkGreaterThan(String value) {
            addCriterion("verify_auto_remark >", value, "verifyAutoRemark");
            return (Criteria) this;
        }

        public Criteria andVerifyAutoRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("verify_auto_remark >=", value, "verifyAutoRemark");
            return (Criteria) this;
        }

        public Criteria andVerifyAutoRemarkLessThan(String value) {
            addCriterion("verify_auto_remark <", value, "verifyAutoRemark");
            return (Criteria) this;
        }

        public Criteria andVerifyAutoRemarkLessThanOrEqualTo(String value) {
            addCriterion("verify_auto_remark <=", value, "verifyAutoRemark");
            return (Criteria) this;
        }

        public Criteria andVerifyAutoRemarkLike(String value) {
            addCriterion("verify_auto_remark like", value, "verifyAutoRemark");
            return (Criteria) this;
        }

        public Criteria andVerifyAutoRemarkNotLike(String value) {
            addCriterion("verify_auto_remark not like", value, "verifyAutoRemark");
            return (Criteria) this;
        }

        public Criteria andVerifyAutoRemarkIn(List<String> values) {
            addCriterion("verify_auto_remark in", values, "verifyAutoRemark");
            return (Criteria) this;
        }

        public Criteria andVerifyAutoRemarkNotIn(List<String> values) {
            addCriterion("verify_auto_remark not in", values, "verifyAutoRemark");
            return (Criteria) this;
        }

        public Criteria andVerifyAutoRemarkBetween(String value1, String value2) {
            addCriterion("verify_auto_remark between", value1, value2, "verifyAutoRemark");
            return (Criteria) this;
        }

        public Criteria andVerifyAutoRemarkNotBetween(String value1, String value2) {
            addCriterion("verify_auto_remark not between", value1, value2, "verifyAutoRemark");
            return (Criteria) this;
        }

        public Criteria andStylesIsNull() {
            addCriterion("styles is null");
            return (Criteria) this;
        }

        public Criteria andStylesIsNotNull() {
            addCriterion("styles is not null");
            return (Criteria) this;
        }

        public Criteria andStylesEqualTo(String value) {
            addCriterion("styles =", value, "styles");
            return (Criteria) this;
        }

        public Criteria andStylesNotEqualTo(String value) {
            addCriterion("styles <>", value, "styles");
            return (Criteria) this;
        }

        public Criteria andStylesGreaterThan(String value) {
            addCriterion("styles >", value, "styles");
            return (Criteria) this;
        }

        public Criteria andStylesGreaterThanOrEqualTo(String value) {
            addCriterion("styles >=", value, "styles");
            return (Criteria) this;
        }

        public Criteria andStylesLessThan(String value) {
            addCriterion("styles <", value, "styles");
            return (Criteria) this;
        }

        public Criteria andStylesLessThanOrEqualTo(String value) {
            addCriterion("styles <=", value, "styles");
            return (Criteria) this;
        }

        public Criteria andStylesLike(String value) {
            addCriterion("styles like", value, "styles");
            return (Criteria) this;
        }

        public Criteria andStylesNotLike(String value) {
            addCriterion("styles not like", value, "styles");
            return (Criteria) this;
        }

        public Criteria andStylesIn(List<String> values) {
            addCriterion("styles in", values, "styles");
            return (Criteria) this;
        }

        public Criteria andStylesNotIn(List<String> values) {
            addCriterion("styles not in", values, "styles");
            return (Criteria) this;
        }

        public Criteria andStylesBetween(String value1, String value2) {
            addCriterion("styles between", value1, value2, "styles");
            return (Criteria) this;
        }

        public Criteria andStylesNotBetween(String value1, String value2) {
            addCriterion("styles not between", value1, value2, "styles");
            return (Criteria) this;
        }

        public Criteria andFrostScaleIsNull() {
            addCriterion("frost_scale is null");
            return (Criteria) this;
        }

        public Criteria andFrostScaleIsNotNull() {
            addCriterion("frost_scale is not null");
            return (Criteria) this;
        }

        public Criteria andFrostScaleEqualTo(BigDecimal value) {
            addCriterion("frost_scale =", value, "frostScale");
            return (Criteria) this;
        }

        public Criteria andFrostScaleNotEqualTo(BigDecimal value) {
            addCriterion("frost_scale <>", value, "frostScale");
            return (Criteria) this;
        }

        public Criteria andFrostScaleGreaterThan(BigDecimal value) {
            addCriterion("frost_scale >", value, "frostScale");
            return (Criteria) this;
        }

        public Criteria andFrostScaleGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("frost_scale >=", value, "frostScale");
            return (Criteria) this;
        }

        public Criteria andFrostScaleLessThan(BigDecimal value) {
            addCriterion("frost_scale <", value, "frostScale");
            return (Criteria) this;
        }

        public Criteria andFrostScaleLessThanOrEqualTo(BigDecimal value) {
            addCriterion("frost_scale <=", value, "frostScale");
            return (Criteria) this;
        }

        public Criteria andFrostScaleIn(List<BigDecimal> values) {
            addCriterion("frost_scale in", values, "frostScale");
            return (Criteria) this;
        }

        public Criteria andFrostScaleNotIn(List<BigDecimal> values) {
            addCriterion("frost_scale not in", values, "frostScale");
            return (Criteria) this;
        }

        public Criteria andFrostScaleBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("frost_scale between", value1, value2, "frostScale");
            return (Criteria) this;
        }

        public Criteria andFrostScaleNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("frost_scale not between", value1, value2, "frostScale");
            return (Criteria) this;
        }

        public Criteria andLateDaysIsNull() {
            addCriterion("late_days is null");
            return (Criteria) this;
        }

        public Criteria andLateDaysIsNotNull() {
            addCriterion("late_days is not null");
            return (Criteria) this;
        }

        public Criteria andLateDaysEqualTo(Integer value) {
            addCriterion("late_days =", value, "lateDays");
            return (Criteria) this;
        }

        public Criteria andLateDaysNotEqualTo(Integer value) {
            addCriterion("late_days <>", value, "lateDays");
            return (Criteria) this;
        }

        public Criteria andLateDaysGreaterThan(Integer value) {
            addCriterion("late_days >", value, "lateDays");
            return (Criteria) this;
        }

        public Criteria andLateDaysGreaterThanOrEqualTo(Integer value) {
            addCriterion("late_days >=", value, "lateDays");
            return (Criteria) this;
        }

        public Criteria andLateDaysLessThan(Integer value) {
            addCriterion("late_days <", value, "lateDays");
            return (Criteria) this;
        }

        public Criteria andLateDaysLessThanOrEqualTo(Integer value) {
            addCriterion("late_days <=", value, "lateDays");
            return (Criteria) this;
        }

        public Criteria andLateDaysIn(List<Integer> values) {
            addCriterion("late_days in", values, "lateDays");
            return (Criteria) this;
        }

        public Criteria andLateDaysNotIn(List<Integer> values) {
            addCriterion("late_days not in", values, "lateDays");
            return (Criteria) this;
        }

        public Criteria andLateDaysBetween(Integer value1, Integer value2) {
            addCriterion("late_days between", value1, value2, "lateDays");
            return (Criteria) this;
        }

        public Criteria andLateDaysNotBetween(Integer value1, Integer value2) {
            addCriterion("late_days not between", value1, value2, "lateDays");
            return (Criteria) this;
        }

        public Criteria andVipLateScaleIsNull() {
            addCriterion("vip_late_scale is null");
            return (Criteria) this;
        }

        public Criteria andVipLateScaleIsNotNull() {
            addCriterion("vip_late_scale is not null");
            return (Criteria) this;
        }

        public Criteria andVipLateScaleEqualTo(BigDecimal value) {
            addCriterion("vip_late_scale =", value, "vipLateScale");
            return (Criteria) this;
        }

        public Criteria andVipLateScaleNotEqualTo(BigDecimal value) {
            addCriterion("vip_late_scale <>", value, "vipLateScale");
            return (Criteria) this;
        }

        public Criteria andVipLateScaleGreaterThan(BigDecimal value) {
            addCriterion("vip_late_scale >", value, "vipLateScale");
            return (Criteria) this;
        }

        public Criteria andVipLateScaleGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("vip_late_scale >=", value, "vipLateScale");
            return (Criteria) this;
        }

        public Criteria andVipLateScaleLessThan(BigDecimal value) {
            addCriterion("vip_late_scale <", value, "vipLateScale");
            return (Criteria) this;
        }

        public Criteria andVipLateScaleLessThanOrEqualTo(BigDecimal value) {
            addCriterion("vip_late_scale <=", value, "vipLateScale");
            return (Criteria) this;
        }

        public Criteria andVipLateScaleIn(List<BigDecimal> values) {
            addCriterion("vip_late_scale in", values, "vipLateScale");
            return (Criteria) this;
        }

        public Criteria andVipLateScaleNotIn(List<BigDecimal> values) {
            addCriterion("vip_late_scale not in", values, "vipLateScale");
            return (Criteria) this;
        }

        public Criteria andVipLateScaleBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("vip_late_scale between", value1, value2, "vipLateScale");
            return (Criteria) this;
        }

        public Criteria andVipLateScaleNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("vip_late_scale not between", value1, value2, "vipLateScale");
            return (Criteria) this;
        }

        public Criteria andAllLateScaleIsNull() {
            addCriterion("all_late_scale is null");
            return (Criteria) this;
        }

        public Criteria andAllLateScaleIsNotNull() {
            addCriterion("all_late_scale is not null");
            return (Criteria) this;
        }

        public Criteria andAllLateScaleEqualTo(BigDecimal value) {
            addCriterion("all_late_scale =", value, "allLateScale");
            return (Criteria) this;
        }

        public Criteria andAllLateScaleNotEqualTo(BigDecimal value) {
            addCriterion("all_late_scale <>", value, "allLateScale");
            return (Criteria) this;
        }

        public Criteria andAllLateScaleGreaterThan(BigDecimal value) {
            addCriterion("all_late_scale >", value, "allLateScale");
            return (Criteria) this;
        }

        public Criteria andAllLateScaleGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("all_late_scale >=", value, "allLateScale");
            return (Criteria) this;
        }

        public Criteria andAllLateScaleLessThan(BigDecimal value) {
            addCriterion("all_late_scale <", value, "allLateScale");
            return (Criteria) this;
        }

        public Criteria andAllLateScaleLessThanOrEqualTo(BigDecimal value) {
            addCriterion("all_late_scale <=", value, "allLateScale");
            return (Criteria) this;
        }

        public Criteria andAllLateScaleIn(List<BigDecimal> values) {
            addCriterion("all_late_scale in", values, "allLateScale");
            return (Criteria) this;
        }

        public Criteria andAllLateScaleNotIn(List<BigDecimal> values) {
            addCriterion("all_late_scale not in", values, "allLateScale");
            return (Criteria) this;
        }

        public Criteria andAllLateScaleBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("all_late_scale between", value1, value2, "allLateScale");
            return (Criteria) this;
        }

        public Criteria andAllLateScaleNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("all_late_scale not between", value1, value2, "allLateScale");
            return (Criteria) this;
        }

        public Criteria andPartStatusIsNull() {
            addCriterion("part_status is null");
            return (Criteria) this;
        }

        public Criteria andPartStatusIsNotNull() {
            addCriterion("part_status is not null");
            return (Criteria) this;
        }

        public Criteria andPartStatusEqualTo(Integer value) {
            addCriterion("part_status =", value, "partStatus");
            return (Criteria) this;
        }

        public Criteria andPartStatusNotEqualTo(Integer value) {
            addCriterion("part_status <>", value, "partStatus");
            return (Criteria) this;
        }

        public Criteria andPartStatusGreaterThan(Integer value) {
            addCriterion("part_status >", value, "partStatus");
            return (Criteria) this;
        }

        public Criteria andPartStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("part_status >=", value, "partStatus");
            return (Criteria) this;
        }

        public Criteria andPartStatusLessThan(Integer value) {
            addCriterion("part_status <", value, "partStatus");
            return (Criteria) this;
        }

        public Criteria andPartStatusLessThanOrEqualTo(Integer value) {
            addCriterion("part_status <=", value, "partStatus");
            return (Criteria) this;
        }

        public Criteria andPartStatusIn(List<Integer> values) {
            addCriterion("part_status in", values, "partStatus");
            return (Criteria) this;
        }

        public Criteria andPartStatusNotIn(List<Integer> values) {
            addCriterion("part_status not in", values, "partStatus");
            return (Criteria) this;
        }

        public Criteria andPartStatusBetween(Integer value1, Integer value2) {
            addCriterion("part_status between", value1, value2, "partStatus");
            return (Criteria) this;
        }

        public Criteria andPartStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("part_status not between", value1, value2, "partStatus");
            return (Criteria) this;
        }

        public Criteria andSystemBorrowFullStatusIsNull() {
            addCriterion("system_borrow_full_status is null");
            return (Criteria) this;
        }

        public Criteria andSystemBorrowFullStatusIsNotNull() {
            addCriterion("system_borrow_full_status is not null");
            return (Criteria) this;
        }

        public Criteria andSystemBorrowFullStatusEqualTo(Integer value) {
            addCriterion("system_borrow_full_status =", value, "systemBorrowFullStatus");
            return (Criteria) this;
        }

        public Criteria andSystemBorrowFullStatusNotEqualTo(Integer value) {
            addCriterion("system_borrow_full_status <>", value, "systemBorrowFullStatus");
            return (Criteria) this;
        }

        public Criteria andSystemBorrowFullStatusGreaterThan(Integer value) {
            addCriterion("system_borrow_full_status >", value, "systemBorrowFullStatus");
            return (Criteria) this;
        }

        public Criteria andSystemBorrowFullStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("system_borrow_full_status >=", value, "systemBorrowFullStatus");
            return (Criteria) this;
        }

        public Criteria andSystemBorrowFullStatusLessThan(Integer value) {
            addCriterion("system_borrow_full_status <", value, "systemBorrowFullStatus");
            return (Criteria) this;
        }

        public Criteria andSystemBorrowFullStatusLessThanOrEqualTo(Integer value) {
            addCriterion("system_borrow_full_status <=", value, "systemBorrowFullStatus");
            return (Criteria) this;
        }

        public Criteria andSystemBorrowFullStatusIn(List<Integer> values) {
            addCriterion("system_borrow_full_status in", values, "systemBorrowFullStatus");
            return (Criteria) this;
        }

        public Criteria andSystemBorrowFullStatusNotIn(List<Integer> values) {
            addCriterion("system_borrow_full_status not in", values, "systemBorrowFullStatus");
            return (Criteria) this;
        }

        public Criteria andSystemBorrowFullStatusBetween(Integer value1, Integer value2) {
            addCriterion("system_borrow_full_status between", value1, value2, "systemBorrowFullStatus");
            return (Criteria) this;
        }

        public Criteria andSystemBorrowFullStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("system_borrow_full_status not between", value1, value2, "systemBorrowFullStatus");
            return (Criteria) this;
        }

        public Criteria andSystemBorrowRepayStatusIsNull() {
            addCriterion("system_borrow_repay_status is null");
            return (Criteria) this;
        }

        public Criteria andSystemBorrowRepayStatusIsNotNull() {
            addCriterion("system_borrow_repay_status is not null");
            return (Criteria) this;
        }

        public Criteria andSystemBorrowRepayStatusEqualTo(Integer value) {
            addCriterion("system_borrow_repay_status =", value, "systemBorrowRepayStatus");
            return (Criteria) this;
        }

        public Criteria andSystemBorrowRepayStatusNotEqualTo(Integer value) {
            addCriterion("system_borrow_repay_status <>", value, "systemBorrowRepayStatus");
            return (Criteria) this;
        }

        public Criteria andSystemBorrowRepayStatusGreaterThan(Integer value) {
            addCriterion("system_borrow_repay_status >", value, "systemBorrowRepayStatus");
            return (Criteria) this;
        }

        public Criteria andSystemBorrowRepayStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("system_borrow_repay_status >=", value, "systemBorrowRepayStatus");
            return (Criteria) this;
        }

        public Criteria andSystemBorrowRepayStatusLessThan(Integer value) {
            addCriterion("system_borrow_repay_status <", value, "systemBorrowRepayStatus");
            return (Criteria) this;
        }

        public Criteria andSystemBorrowRepayStatusLessThanOrEqualTo(Integer value) {
            addCriterion("system_borrow_repay_status <=", value, "systemBorrowRepayStatus");
            return (Criteria) this;
        }

        public Criteria andSystemBorrowRepayStatusIn(List<Integer> values) {
            addCriterion("system_borrow_repay_status in", values, "systemBorrowRepayStatus");
            return (Criteria) this;
        }

        public Criteria andSystemBorrowRepayStatusNotIn(List<Integer> values) {
            addCriterion("system_borrow_repay_status not in", values, "systemBorrowRepayStatus");
            return (Criteria) this;
        }

        public Criteria andSystemBorrowRepayStatusBetween(Integer value1, Integer value2) {
            addCriterion("system_borrow_repay_status between", value1, value2, "systemBorrowRepayStatus");
            return (Criteria) this;
        }

        public Criteria andSystemBorrowRepayStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("system_borrow_repay_status not between", value1, value2, "systemBorrowRepayStatus");
            return (Criteria) this;
        }

        public Criteria andSystemWebRepayStatusIsNull() {
            addCriterion("system_web_repay_status is null");
            return (Criteria) this;
        }

        public Criteria andSystemWebRepayStatusIsNotNull() {
            addCriterion("system_web_repay_status is not null");
            return (Criteria) this;
        }

        public Criteria andSystemWebRepayStatusEqualTo(Integer value) {
            addCriterion("system_web_repay_status =", value, "systemWebRepayStatus");
            return (Criteria) this;
        }

        public Criteria andSystemWebRepayStatusNotEqualTo(Integer value) {
            addCriterion("system_web_repay_status <>", value, "systemWebRepayStatus");
            return (Criteria) this;
        }

        public Criteria andSystemWebRepayStatusGreaterThan(Integer value) {
            addCriterion("system_web_repay_status >", value, "systemWebRepayStatus");
            return (Criteria) this;
        }

        public Criteria andSystemWebRepayStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("system_web_repay_status >=", value, "systemWebRepayStatus");
            return (Criteria) this;
        }

        public Criteria andSystemWebRepayStatusLessThan(Integer value) {
            addCriterion("system_web_repay_status <", value, "systemWebRepayStatus");
            return (Criteria) this;
        }

        public Criteria andSystemWebRepayStatusLessThanOrEqualTo(Integer value) {
            addCriterion("system_web_repay_status <=", value, "systemWebRepayStatus");
            return (Criteria) this;
        }

        public Criteria andSystemWebRepayStatusIn(List<Integer> values) {
            addCriterion("system_web_repay_status in", values, "systemWebRepayStatus");
            return (Criteria) this;
        }

        public Criteria andSystemWebRepayStatusNotIn(List<Integer> values) {
            addCriterion("system_web_repay_status not in", values, "systemWebRepayStatus");
            return (Criteria) this;
        }

        public Criteria andSystemWebRepayStatusBetween(Integer value1, Integer value2) {
            addCriterion("system_web_repay_status between", value1, value2, "systemWebRepayStatus");
            return (Criteria) this;
        }

        public Criteria andSystemWebRepayStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("system_web_repay_status not between", value1, value2, "systemWebRepayStatus");
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