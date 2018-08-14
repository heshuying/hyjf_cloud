package com.hyjf.am.trade.dao.model.auto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BorrowManinfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public BorrowManinfoExample() {
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

        public Criteria andSexIsNull() {
            addCriterion("sex is null");
            return (Criteria) this;
        }

        public Criteria andSexIsNotNull() {
            addCriterion("sex is not null");
            return (Criteria) this;
        }

        public Criteria andSexEqualTo(Integer value) {
            addCriterion("sex =", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotEqualTo(Integer value) {
            addCriterion("sex <>", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexGreaterThan(Integer value) {
            addCriterion("sex >", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexGreaterThanOrEqualTo(Integer value) {
            addCriterion("sex >=", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexLessThan(Integer value) {
            addCriterion("sex <", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexLessThanOrEqualTo(Integer value) {
            addCriterion("sex <=", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexIn(List<Integer> values) {
            addCriterion("sex in", values, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotIn(List<Integer> values) {
            addCriterion("sex not in", values, "sex");
            return (Criteria) this;
        }

        public Criteria andSexBetween(Integer value1, Integer value2) {
            addCriterion("sex between", value1, value2, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotBetween(Integer value1, Integer value2) {
            addCriterion("sex not between", value1, value2, "sex");
            return (Criteria) this;
        }

        public Criteria andOldIsNull() {
            addCriterion("`old` is null");
            return (Criteria) this;
        }

        public Criteria andOldIsNotNull() {
            addCriterion("`old` is not null");
            return (Criteria) this;
        }

        public Criteria andOldEqualTo(Integer value) {
            addCriterion("`old` =", value, "old");
            return (Criteria) this;
        }

        public Criteria andOldNotEqualTo(Integer value) {
            addCriterion("`old` <>", value, "old");
            return (Criteria) this;
        }

        public Criteria andOldGreaterThan(Integer value) {
            addCriterion("`old` >", value, "old");
            return (Criteria) this;
        }

        public Criteria andOldGreaterThanOrEqualTo(Integer value) {
            addCriterion("`old` >=", value, "old");
            return (Criteria) this;
        }

        public Criteria andOldLessThan(Integer value) {
            addCriterion("`old` <", value, "old");
            return (Criteria) this;
        }

        public Criteria andOldLessThanOrEqualTo(Integer value) {
            addCriterion("`old` <=", value, "old");
            return (Criteria) this;
        }

        public Criteria andOldIn(List<Integer> values) {
            addCriterion("`old` in", values, "old");
            return (Criteria) this;
        }

        public Criteria andOldNotIn(List<Integer> values) {
            addCriterion("`old` not in", values, "old");
            return (Criteria) this;
        }

        public Criteria andOldBetween(Integer value1, Integer value2) {
            addCriterion("`old` between", value1, value2, "old");
            return (Criteria) this;
        }

        public Criteria andOldNotBetween(Integer value1, Integer value2) {
            addCriterion("`old` not between", value1, value2, "old");
            return (Criteria) this;
        }

        public Criteria andMerryIsNull() {
            addCriterion("merry is null");
            return (Criteria) this;
        }

        public Criteria andMerryIsNotNull() {
            addCriterion("merry is not null");
            return (Criteria) this;
        }

        public Criteria andMerryEqualTo(Integer value) {
            addCriterion("merry =", value, "merry");
            return (Criteria) this;
        }

        public Criteria andMerryNotEqualTo(Integer value) {
            addCriterion("merry <>", value, "merry");
            return (Criteria) this;
        }

        public Criteria andMerryGreaterThan(Integer value) {
            addCriterion("merry >", value, "merry");
            return (Criteria) this;
        }

        public Criteria andMerryGreaterThanOrEqualTo(Integer value) {
            addCriterion("merry >=", value, "merry");
            return (Criteria) this;
        }

        public Criteria andMerryLessThan(Integer value) {
            addCriterion("merry <", value, "merry");
            return (Criteria) this;
        }

        public Criteria andMerryLessThanOrEqualTo(Integer value) {
            addCriterion("merry <=", value, "merry");
            return (Criteria) this;
        }

        public Criteria andMerryIn(List<Integer> values) {
            addCriterion("merry in", values, "merry");
            return (Criteria) this;
        }

        public Criteria andMerryNotIn(List<Integer> values) {
            addCriterion("merry not in", values, "merry");
            return (Criteria) this;
        }

        public Criteria andMerryBetween(Integer value1, Integer value2) {
            addCriterion("merry between", value1, value2, "merry");
            return (Criteria) this;
        }

        public Criteria andMerryNotBetween(Integer value1, Integer value2) {
            addCriterion("merry not between", value1, value2, "merry");
            return (Criteria) this;
        }

        public Criteria andProIsNull() {
            addCriterion("pro is null");
            return (Criteria) this;
        }

        public Criteria andProIsNotNull() {
            addCriterion("pro is not null");
            return (Criteria) this;
        }

        public Criteria andProEqualTo(String value) {
            addCriterion("pro =", value, "pro");
            return (Criteria) this;
        }

        public Criteria andProNotEqualTo(String value) {
            addCriterion("pro <>", value, "pro");
            return (Criteria) this;
        }

        public Criteria andProGreaterThan(String value) {
            addCriterion("pro >", value, "pro");
            return (Criteria) this;
        }

        public Criteria andProGreaterThanOrEqualTo(String value) {
            addCriterion("pro >=", value, "pro");
            return (Criteria) this;
        }

        public Criteria andProLessThan(String value) {
            addCriterion("pro <", value, "pro");
            return (Criteria) this;
        }

        public Criteria andProLessThanOrEqualTo(String value) {
            addCriterion("pro <=", value, "pro");
            return (Criteria) this;
        }

        public Criteria andProLike(String value) {
            addCriterion("pro like", value, "pro");
            return (Criteria) this;
        }

        public Criteria andProNotLike(String value) {
            addCriterion("pro not like", value, "pro");
            return (Criteria) this;
        }

        public Criteria andProIn(List<String> values) {
            addCriterion("pro in", values, "pro");
            return (Criteria) this;
        }

        public Criteria andProNotIn(List<String> values) {
            addCriterion("pro not in", values, "pro");
            return (Criteria) this;
        }

        public Criteria andProBetween(String value1, String value2) {
            addCriterion("pro between", value1, value2, "pro");
            return (Criteria) this;
        }

        public Criteria andProNotBetween(String value1, String value2) {
            addCriterion("pro not between", value1, value2, "pro");
            return (Criteria) this;
        }

        public Criteria andCityIsNull() {
            addCriterion("city is null");
            return (Criteria) this;
        }

        public Criteria andCityIsNotNull() {
            addCriterion("city is not null");
            return (Criteria) this;
        }

        public Criteria andCityEqualTo(String value) {
            addCriterion("city =", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotEqualTo(String value) {
            addCriterion("city <>", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityGreaterThan(String value) {
            addCriterion("city >", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityGreaterThanOrEqualTo(String value) {
            addCriterion("city >=", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityLessThan(String value) {
            addCriterion("city <", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityLessThanOrEqualTo(String value) {
            addCriterion("city <=", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityLike(String value) {
            addCriterion("city like", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotLike(String value) {
            addCriterion("city not like", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityIn(List<String> values) {
            addCriterion("city in", values, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotIn(List<String> values) {
            addCriterion("city not in", values, "city");
            return (Criteria) this;
        }

        public Criteria andCityBetween(String value1, String value2) {
            addCriterion("city between", value1, value2, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotBetween(String value1, String value2) {
            addCriterion("city not between", value1, value2, "city");
            return (Criteria) this;
        }

        public Criteria andIndustryIsNull() {
            addCriterion("industry is null");
            return (Criteria) this;
        }

        public Criteria andIndustryIsNotNull() {
            addCriterion("industry is not null");
            return (Criteria) this;
        }

        public Criteria andIndustryEqualTo(String value) {
            addCriterion("industry =", value, "industry");
            return (Criteria) this;
        }

        public Criteria andIndustryNotEqualTo(String value) {
            addCriterion("industry <>", value, "industry");
            return (Criteria) this;
        }

        public Criteria andIndustryGreaterThan(String value) {
            addCriterion("industry >", value, "industry");
            return (Criteria) this;
        }

        public Criteria andIndustryGreaterThanOrEqualTo(String value) {
            addCriterion("industry >=", value, "industry");
            return (Criteria) this;
        }

        public Criteria andIndustryLessThan(String value) {
            addCriterion("industry <", value, "industry");
            return (Criteria) this;
        }

        public Criteria andIndustryLessThanOrEqualTo(String value) {
            addCriterion("industry <=", value, "industry");
            return (Criteria) this;
        }

        public Criteria andIndustryLike(String value) {
            addCriterion("industry like", value, "industry");
            return (Criteria) this;
        }

        public Criteria andIndustryNotLike(String value) {
            addCriterion("industry not like", value, "industry");
            return (Criteria) this;
        }

        public Criteria andIndustryIn(List<String> values) {
            addCriterion("industry in", values, "industry");
            return (Criteria) this;
        }

        public Criteria andIndustryNotIn(List<String> values) {
            addCriterion("industry not in", values, "industry");
            return (Criteria) this;
        }

        public Criteria andIndustryBetween(String value1, String value2) {
            addCriterion("industry between", value1, value2, "industry");
            return (Criteria) this;
        }

        public Criteria andIndustryNotBetween(String value1, String value2) {
            addCriterion("industry not between", value1, value2, "industry");
            return (Criteria) this;
        }

        public Criteria andPositionIsNull() {
            addCriterion("`position` is null");
            return (Criteria) this;
        }

        public Criteria andPositionIsNotNull() {
            addCriterion("`position` is not null");
            return (Criteria) this;
        }

        public Criteria andPositionEqualTo(String value) {
            addCriterion("`position` =", value, "position");
            return (Criteria) this;
        }

        public Criteria andPositionNotEqualTo(String value) {
            addCriterion("`position` <>", value, "position");
            return (Criteria) this;
        }

        public Criteria andPositionGreaterThan(String value) {
            addCriterion("`position` >", value, "position");
            return (Criteria) this;
        }

        public Criteria andPositionGreaterThanOrEqualTo(String value) {
            addCriterion("`position` >=", value, "position");
            return (Criteria) this;
        }

        public Criteria andPositionLessThan(String value) {
            addCriterion("`position` <", value, "position");
            return (Criteria) this;
        }

        public Criteria andPositionLessThanOrEqualTo(String value) {
            addCriterion("`position` <=", value, "position");
            return (Criteria) this;
        }

        public Criteria andPositionLike(String value) {
            addCriterion("`position` like", value, "position");
            return (Criteria) this;
        }

        public Criteria andPositionNotLike(String value) {
            addCriterion("`position` not like", value, "position");
            return (Criteria) this;
        }

        public Criteria andPositionIn(List<String> values) {
            addCriterion("`position` in", values, "position");
            return (Criteria) this;
        }

        public Criteria andPositionNotIn(List<String> values) {
            addCriterion("`position` not in", values, "position");
            return (Criteria) this;
        }

        public Criteria andPositionBetween(String value1, String value2) {
            addCriterion("`position` between", value1, value2, "position");
            return (Criteria) this;
        }

        public Criteria andPositionNotBetween(String value1, String value2) {
            addCriterion("`position` not between", value1, value2, "position");
            return (Criteria) this;
        }

        public Criteria andCreditIsNull() {
            addCriterion("credit is null");
            return (Criteria) this;
        }

        public Criteria andCreditIsNotNull() {
            addCriterion("credit is not null");
            return (Criteria) this;
        }

        public Criteria andCreditEqualTo(Integer value) {
            addCriterion("credit =", value, "credit");
            return (Criteria) this;
        }

        public Criteria andCreditNotEqualTo(Integer value) {
            addCriterion("credit <>", value, "credit");
            return (Criteria) this;
        }

        public Criteria andCreditGreaterThan(Integer value) {
            addCriterion("credit >", value, "credit");
            return (Criteria) this;
        }

        public Criteria andCreditGreaterThanOrEqualTo(Integer value) {
            addCriterion("credit >=", value, "credit");
            return (Criteria) this;
        }

        public Criteria andCreditLessThan(Integer value) {
            addCriterion("credit <", value, "credit");
            return (Criteria) this;
        }

        public Criteria andCreditLessThanOrEqualTo(Integer value) {
            addCriterion("credit <=", value, "credit");
            return (Criteria) this;
        }

        public Criteria andCreditIn(List<Integer> values) {
            addCriterion("credit in", values, "credit");
            return (Criteria) this;
        }

        public Criteria andCreditNotIn(List<Integer> values) {
            addCriterion("credit not in", values, "credit");
            return (Criteria) this;
        }

        public Criteria andCreditBetween(Integer value1, Integer value2) {
            addCriterion("credit between", value1, value2, "credit");
            return (Criteria) this;
        }

        public Criteria andCreditNotBetween(Integer value1, Integer value2) {
            addCriterion("credit not between", value1, value2, "credit");
            return (Criteria) this;
        }

        public Criteria andSizeIsNull() {
            addCriterion("`size` is null");
            return (Criteria) this;
        }

        public Criteria andSizeIsNotNull() {
            addCriterion("`size` is not null");
            return (Criteria) this;
        }

        public Criteria andSizeEqualTo(String value) {
            addCriterion("`size` =", value, "size");
            return (Criteria) this;
        }

        public Criteria andSizeNotEqualTo(String value) {
            addCriterion("`size` <>", value, "size");
            return (Criteria) this;
        }

        public Criteria andSizeGreaterThan(String value) {
            addCriterion("`size` >", value, "size");
            return (Criteria) this;
        }

        public Criteria andSizeGreaterThanOrEqualTo(String value) {
            addCriterion("`size` >=", value, "size");
            return (Criteria) this;
        }

        public Criteria andSizeLessThan(String value) {
            addCriterion("`size` <", value, "size");
            return (Criteria) this;
        }

        public Criteria andSizeLessThanOrEqualTo(String value) {
            addCriterion("`size` <=", value, "size");
            return (Criteria) this;
        }

        public Criteria andSizeLike(String value) {
            addCriterion("`size` like", value, "size");
            return (Criteria) this;
        }

        public Criteria andSizeNotLike(String value) {
            addCriterion("`size` not like", value, "size");
            return (Criteria) this;
        }

        public Criteria andSizeIn(List<String> values) {
            addCriterion("`size` in", values, "size");
            return (Criteria) this;
        }

        public Criteria andSizeNotIn(List<String> values) {
            addCriterion("`size` not in", values, "size");
            return (Criteria) this;
        }

        public Criteria andSizeBetween(String value1, String value2) {
            addCriterion("`size` between", value1, value2, "size");
            return (Criteria) this;
        }

        public Criteria andSizeNotBetween(String value1, String value2) {
            addCriterion("`size` not between", value1, value2, "size");
            return (Criteria) this;
        }

        public Criteria andBusinessIsNull() {
            addCriterion("business is null");
            return (Criteria) this;
        }

        public Criteria andBusinessIsNotNull() {
            addCriterion("business is not null");
            return (Criteria) this;
        }

        public Criteria andBusinessEqualTo(BigDecimal value) {
            addCriterion("business =", value, "business");
            return (Criteria) this;
        }

        public Criteria andBusinessNotEqualTo(BigDecimal value) {
            addCriterion("business <>", value, "business");
            return (Criteria) this;
        }

        public Criteria andBusinessGreaterThan(BigDecimal value) {
            addCriterion("business >", value, "business");
            return (Criteria) this;
        }

        public Criteria andBusinessGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("business >=", value, "business");
            return (Criteria) this;
        }

        public Criteria andBusinessLessThan(BigDecimal value) {
            addCriterion("business <", value, "business");
            return (Criteria) this;
        }

        public Criteria andBusinessLessThanOrEqualTo(BigDecimal value) {
            addCriterion("business <=", value, "business");
            return (Criteria) this;
        }

        public Criteria andBusinessIn(List<BigDecimal> values) {
            addCriterion("business in", values, "business");
            return (Criteria) this;
        }

        public Criteria andBusinessNotIn(List<BigDecimal> values) {
            addCriterion("business not in", values, "business");
            return (Criteria) this;
        }

        public Criteria andBusinessBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("business between", value1, value2, "business");
            return (Criteria) this;
        }

        public Criteria andBusinessNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("business not between", value1, value2, "business");
            return (Criteria) this;
        }

        public Criteria andWtimeIsNull() {
            addCriterion("wtime is null");
            return (Criteria) this;
        }

        public Criteria andWtimeIsNotNull() {
            addCriterion("wtime is not null");
            return (Criteria) this;
        }

        public Criteria andWtimeEqualTo(String value) {
            addCriterion("wtime =", value, "wtime");
            return (Criteria) this;
        }

        public Criteria andWtimeNotEqualTo(String value) {
            addCriterion("wtime <>", value, "wtime");
            return (Criteria) this;
        }

        public Criteria andWtimeGreaterThan(String value) {
            addCriterion("wtime >", value, "wtime");
            return (Criteria) this;
        }

        public Criteria andWtimeGreaterThanOrEqualTo(String value) {
            addCriterion("wtime >=", value, "wtime");
            return (Criteria) this;
        }

        public Criteria andWtimeLessThan(String value) {
            addCriterion("wtime <", value, "wtime");
            return (Criteria) this;
        }

        public Criteria andWtimeLessThanOrEqualTo(String value) {
            addCriterion("wtime <=", value, "wtime");
            return (Criteria) this;
        }

        public Criteria andWtimeLike(String value) {
            addCriterion("wtime like", value, "wtime");
            return (Criteria) this;
        }

        public Criteria andWtimeNotLike(String value) {
            addCriterion("wtime not like", value, "wtime");
            return (Criteria) this;
        }

        public Criteria andWtimeIn(List<String> values) {
            addCriterion("wtime in", values, "wtime");
            return (Criteria) this;
        }

        public Criteria andWtimeNotIn(List<String> values) {
            addCriterion("wtime not in", values, "wtime");
            return (Criteria) this;
        }

        public Criteria andWtimeBetween(String value1, String value2) {
            addCriterion("wtime between", value1, value2, "wtime");
            return (Criteria) this;
        }

        public Criteria andWtimeNotBetween(String value1, String value2) {
            addCriterion("wtime not between", value1, value2, "wtime");
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

        public Criteria andCardNoIsNull() {
            addCriterion("card_no is null");
            return (Criteria) this;
        }

        public Criteria andCardNoIsNotNull() {
            addCriterion("card_no is not null");
            return (Criteria) this;
        }

        public Criteria andCardNoEqualTo(String value) {
            addCriterion("card_no =", value, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoNotEqualTo(String value) {
            addCriterion("card_no <>", value, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoGreaterThan(String value) {
            addCriterion("card_no >", value, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoGreaterThanOrEqualTo(String value) {
            addCriterion("card_no >=", value, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoLessThan(String value) {
            addCriterion("card_no <", value, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoLessThanOrEqualTo(String value) {
            addCriterion("card_no <=", value, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoLike(String value) {
            addCriterion("card_no like", value, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoNotLike(String value) {
            addCriterion("card_no not like", value, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoIn(List<String> values) {
            addCriterion("card_no in", values, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoNotIn(List<String> values) {
            addCriterion("card_no not in", values, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoBetween(String value1, String value2) {
            addCriterion("card_no between", value1, value2, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoNotBetween(String value1, String value2) {
            addCriterion("card_no not between", value1, value2, "cardNo");
            return (Criteria) this;
        }

        public Criteria andDomicileIsNull() {
            addCriterion("domicile is null");
            return (Criteria) this;
        }

        public Criteria andDomicileIsNotNull() {
            addCriterion("domicile is not null");
            return (Criteria) this;
        }

        public Criteria andDomicileEqualTo(String value) {
            addCriterion("domicile =", value, "domicile");
            return (Criteria) this;
        }

        public Criteria andDomicileNotEqualTo(String value) {
            addCriterion("domicile <>", value, "domicile");
            return (Criteria) this;
        }

        public Criteria andDomicileGreaterThan(String value) {
            addCriterion("domicile >", value, "domicile");
            return (Criteria) this;
        }

        public Criteria andDomicileGreaterThanOrEqualTo(String value) {
            addCriterion("domicile >=", value, "domicile");
            return (Criteria) this;
        }

        public Criteria andDomicileLessThan(String value) {
            addCriterion("domicile <", value, "domicile");
            return (Criteria) this;
        }

        public Criteria andDomicileLessThanOrEqualTo(String value) {
            addCriterion("domicile <=", value, "domicile");
            return (Criteria) this;
        }

        public Criteria andDomicileLike(String value) {
            addCriterion("domicile like", value, "domicile");
            return (Criteria) this;
        }

        public Criteria andDomicileNotLike(String value) {
            addCriterion("domicile not like", value, "domicile");
            return (Criteria) this;
        }

        public Criteria andDomicileIn(List<String> values) {
            addCriterion("domicile in", values, "domicile");
            return (Criteria) this;
        }

        public Criteria andDomicileNotIn(List<String> values) {
            addCriterion("domicile not in", values, "domicile");
            return (Criteria) this;
        }

        public Criteria andDomicileBetween(String value1, String value2) {
            addCriterion("domicile between", value1, value2, "domicile");
            return (Criteria) this;
        }

        public Criteria andDomicileNotBetween(String value1, String value2) {
            addCriterion("domicile not between", value1, value2, "domicile");
            return (Criteria) this;
        }

        public Criteria andOverdueTimesIsNull() {
            addCriterion("overdue_times is null");
            return (Criteria) this;
        }

        public Criteria andOverdueTimesIsNotNull() {
            addCriterion("overdue_times is not null");
            return (Criteria) this;
        }

        public Criteria andOverdueTimesEqualTo(String value) {
            addCriterion("overdue_times =", value, "overdueTimes");
            return (Criteria) this;
        }

        public Criteria andOverdueTimesNotEqualTo(String value) {
            addCriterion("overdue_times <>", value, "overdueTimes");
            return (Criteria) this;
        }

        public Criteria andOverdueTimesGreaterThan(String value) {
            addCriterion("overdue_times >", value, "overdueTimes");
            return (Criteria) this;
        }

        public Criteria andOverdueTimesGreaterThanOrEqualTo(String value) {
            addCriterion("overdue_times >=", value, "overdueTimes");
            return (Criteria) this;
        }

        public Criteria andOverdueTimesLessThan(String value) {
            addCriterion("overdue_times <", value, "overdueTimes");
            return (Criteria) this;
        }

        public Criteria andOverdueTimesLessThanOrEqualTo(String value) {
            addCriterion("overdue_times <=", value, "overdueTimes");
            return (Criteria) this;
        }

        public Criteria andOverdueTimesLike(String value) {
            addCriterion("overdue_times like", value, "overdueTimes");
            return (Criteria) this;
        }

        public Criteria andOverdueTimesNotLike(String value) {
            addCriterion("overdue_times not like", value, "overdueTimes");
            return (Criteria) this;
        }

        public Criteria andOverdueTimesIn(List<String> values) {
            addCriterion("overdue_times in", values, "overdueTimes");
            return (Criteria) this;
        }

        public Criteria andOverdueTimesNotIn(List<String> values) {
            addCriterion("overdue_times not in", values, "overdueTimes");
            return (Criteria) this;
        }

        public Criteria andOverdueTimesBetween(String value1, String value2) {
            addCriterion("overdue_times between", value1, value2, "overdueTimes");
            return (Criteria) this;
        }

        public Criteria andOverdueTimesNotBetween(String value1, String value2) {
            addCriterion("overdue_times not between", value1, value2, "overdueTimes");
            return (Criteria) this;
        }

        public Criteria andOverdueAmountIsNull() {
            addCriterion("overdue_amount is null");
            return (Criteria) this;
        }

        public Criteria andOverdueAmountIsNotNull() {
            addCriterion("overdue_amount is not null");
            return (Criteria) this;
        }

        public Criteria andOverdueAmountEqualTo(String value) {
            addCriterion("overdue_amount =", value, "overdueAmount");
            return (Criteria) this;
        }

        public Criteria andOverdueAmountNotEqualTo(String value) {
            addCriterion("overdue_amount <>", value, "overdueAmount");
            return (Criteria) this;
        }

        public Criteria andOverdueAmountGreaterThan(String value) {
            addCriterion("overdue_amount >", value, "overdueAmount");
            return (Criteria) this;
        }

        public Criteria andOverdueAmountGreaterThanOrEqualTo(String value) {
            addCriterion("overdue_amount >=", value, "overdueAmount");
            return (Criteria) this;
        }

        public Criteria andOverdueAmountLessThan(String value) {
            addCriterion("overdue_amount <", value, "overdueAmount");
            return (Criteria) this;
        }

        public Criteria andOverdueAmountLessThanOrEqualTo(String value) {
            addCriterion("overdue_amount <=", value, "overdueAmount");
            return (Criteria) this;
        }

        public Criteria andOverdueAmountLike(String value) {
            addCriterion("overdue_amount like", value, "overdueAmount");
            return (Criteria) this;
        }

        public Criteria andOverdueAmountNotLike(String value) {
            addCriterion("overdue_amount not like", value, "overdueAmount");
            return (Criteria) this;
        }

        public Criteria andOverdueAmountIn(List<String> values) {
            addCriterion("overdue_amount in", values, "overdueAmount");
            return (Criteria) this;
        }

        public Criteria andOverdueAmountNotIn(List<String> values) {
            addCriterion("overdue_amount not in", values, "overdueAmount");
            return (Criteria) this;
        }

        public Criteria andOverdueAmountBetween(String value1, String value2) {
            addCriterion("overdue_amount between", value1, value2, "overdueAmount");
            return (Criteria) this;
        }

        public Criteria andOverdueAmountNotBetween(String value1, String value2) {
            addCriterion("overdue_amount not between", value1, value2, "overdueAmount");
            return (Criteria) this;
        }

        public Criteria andLitigationIsNull() {
            addCriterion("litigation is null");
            return (Criteria) this;
        }

        public Criteria andLitigationIsNotNull() {
            addCriterion("litigation is not null");
            return (Criteria) this;
        }

        public Criteria andLitigationEqualTo(String value) {
            addCriterion("litigation =", value, "litigation");
            return (Criteria) this;
        }

        public Criteria andLitigationNotEqualTo(String value) {
            addCriterion("litigation <>", value, "litigation");
            return (Criteria) this;
        }

        public Criteria andLitigationGreaterThan(String value) {
            addCriterion("litigation >", value, "litigation");
            return (Criteria) this;
        }

        public Criteria andLitigationGreaterThanOrEqualTo(String value) {
            addCriterion("litigation >=", value, "litigation");
            return (Criteria) this;
        }

        public Criteria andLitigationLessThan(String value) {
            addCriterion("litigation <", value, "litigation");
            return (Criteria) this;
        }

        public Criteria andLitigationLessThanOrEqualTo(String value) {
            addCriterion("litigation <=", value, "litigation");
            return (Criteria) this;
        }

        public Criteria andLitigationLike(String value) {
            addCriterion("litigation like", value, "litigation");
            return (Criteria) this;
        }

        public Criteria andLitigationNotLike(String value) {
            addCriterion("litigation not like", value, "litigation");
            return (Criteria) this;
        }

        public Criteria andLitigationIn(List<String> values) {
            addCriterion("litigation in", values, "litigation");
            return (Criteria) this;
        }

        public Criteria andLitigationNotIn(List<String> values) {
            addCriterion("litigation not in", values, "litigation");
            return (Criteria) this;
        }

        public Criteria andLitigationBetween(String value1, String value2) {
            addCriterion("litigation between", value1, value2, "litigation");
            return (Criteria) this;
        }

        public Criteria andLitigationNotBetween(String value1, String value2) {
            addCriterion("litigation not between", value1, value2, "litigation");
            return (Criteria) this;
        }

        public Criteria andIsCardIsNull() {
            addCriterion("is_card is null");
            return (Criteria) this;
        }

        public Criteria andIsCardIsNotNull() {
            addCriterion("is_card is not null");
            return (Criteria) this;
        }

        public Criteria andIsCardEqualTo(Integer value) {
            addCriterion("is_card =", value, "isCard");
            return (Criteria) this;
        }

        public Criteria andIsCardNotEqualTo(Integer value) {
            addCriterion("is_card <>", value, "isCard");
            return (Criteria) this;
        }

        public Criteria andIsCardGreaterThan(Integer value) {
            addCriterion("is_card >", value, "isCard");
            return (Criteria) this;
        }

        public Criteria andIsCardGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_card >=", value, "isCard");
            return (Criteria) this;
        }

        public Criteria andIsCardLessThan(Integer value) {
            addCriterion("is_card <", value, "isCard");
            return (Criteria) this;
        }

        public Criteria andIsCardLessThanOrEqualTo(Integer value) {
            addCriterion("is_card <=", value, "isCard");
            return (Criteria) this;
        }

        public Criteria andIsCardIn(List<Integer> values) {
            addCriterion("is_card in", values, "isCard");
            return (Criteria) this;
        }

        public Criteria andIsCardNotIn(List<Integer> values) {
            addCriterion("is_card not in", values, "isCard");
            return (Criteria) this;
        }

        public Criteria andIsCardBetween(Integer value1, Integer value2) {
            addCriterion("is_card between", value1, value2, "isCard");
            return (Criteria) this;
        }

        public Criteria andIsCardNotBetween(Integer value1, Integer value2) {
            addCriterion("is_card not between", value1, value2, "isCard");
            return (Criteria) this;
        }

        public Criteria andIsIncomeIsNull() {
            addCriterion("is_income is null");
            return (Criteria) this;
        }

        public Criteria andIsIncomeIsNotNull() {
            addCriterion("is_income is not null");
            return (Criteria) this;
        }

        public Criteria andIsIncomeEqualTo(Integer value) {
            addCriterion("is_income =", value, "isIncome");
            return (Criteria) this;
        }

        public Criteria andIsIncomeNotEqualTo(Integer value) {
            addCriterion("is_income <>", value, "isIncome");
            return (Criteria) this;
        }

        public Criteria andIsIncomeGreaterThan(Integer value) {
            addCriterion("is_income >", value, "isIncome");
            return (Criteria) this;
        }

        public Criteria andIsIncomeGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_income >=", value, "isIncome");
            return (Criteria) this;
        }

        public Criteria andIsIncomeLessThan(Integer value) {
            addCriterion("is_income <", value, "isIncome");
            return (Criteria) this;
        }

        public Criteria andIsIncomeLessThanOrEqualTo(Integer value) {
            addCriterion("is_income <=", value, "isIncome");
            return (Criteria) this;
        }

        public Criteria andIsIncomeIn(List<Integer> values) {
            addCriterion("is_income in", values, "isIncome");
            return (Criteria) this;
        }

        public Criteria andIsIncomeNotIn(List<Integer> values) {
            addCriterion("is_income not in", values, "isIncome");
            return (Criteria) this;
        }

        public Criteria andIsIncomeBetween(Integer value1, Integer value2) {
            addCriterion("is_income between", value1, value2, "isIncome");
            return (Criteria) this;
        }

        public Criteria andIsIncomeNotBetween(Integer value1, Integer value2) {
            addCriterion("is_income not between", value1, value2, "isIncome");
            return (Criteria) this;
        }

        public Criteria andIsCreditIsNull() {
            addCriterion("is_credit is null");
            return (Criteria) this;
        }

        public Criteria andIsCreditIsNotNull() {
            addCriterion("is_credit is not null");
            return (Criteria) this;
        }

        public Criteria andIsCreditEqualTo(Integer value) {
            addCriterion("is_credit =", value, "isCredit");
            return (Criteria) this;
        }

        public Criteria andIsCreditNotEqualTo(Integer value) {
            addCriterion("is_credit <>", value, "isCredit");
            return (Criteria) this;
        }

        public Criteria andIsCreditGreaterThan(Integer value) {
            addCriterion("is_credit >", value, "isCredit");
            return (Criteria) this;
        }

        public Criteria andIsCreditGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_credit >=", value, "isCredit");
            return (Criteria) this;
        }

        public Criteria andIsCreditLessThan(Integer value) {
            addCriterion("is_credit <", value, "isCredit");
            return (Criteria) this;
        }

        public Criteria andIsCreditLessThanOrEqualTo(Integer value) {
            addCriterion("is_credit <=", value, "isCredit");
            return (Criteria) this;
        }

        public Criteria andIsCreditIn(List<Integer> values) {
            addCriterion("is_credit in", values, "isCredit");
            return (Criteria) this;
        }

        public Criteria andIsCreditNotIn(List<Integer> values) {
            addCriterion("is_credit not in", values, "isCredit");
            return (Criteria) this;
        }

        public Criteria andIsCreditBetween(Integer value1, Integer value2) {
            addCriterion("is_credit between", value1, value2, "isCredit");
            return (Criteria) this;
        }

        public Criteria andIsCreditNotBetween(Integer value1, Integer value2) {
            addCriterion("is_credit not between", value1, value2, "isCredit");
            return (Criteria) this;
        }

        public Criteria andIsAssetIsNull() {
            addCriterion("is_asset is null");
            return (Criteria) this;
        }

        public Criteria andIsAssetIsNotNull() {
            addCriterion("is_asset is not null");
            return (Criteria) this;
        }

        public Criteria andIsAssetEqualTo(Integer value) {
            addCriterion("is_asset =", value, "isAsset");
            return (Criteria) this;
        }

        public Criteria andIsAssetNotEqualTo(Integer value) {
            addCriterion("is_asset <>", value, "isAsset");
            return (Criteria) this;
        }

        public Criteria andIsAssetGreaterThan(Integer value) {
            addCriterion("is_asset >", value, "isAsset");
            return (Criteria) this;
        }

        public Criteria andIsAssetGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_asset >=", value, "isAsset");
            return (Criteria) this;
        }

        public Criteria andIsAssetLessThan(Integer value) {
            addCriterion("is_asset <", value, "isAsset");
            return (Criteria) this;
        }

        public Criteria andIsAssetLessThanOrEqualTo(Integer value) {
            addCriterion("is_asset <=", value, "isAsset");
            return (Criteria) this;
        }

        public Criteria andIsAssetIn(List<Integer> values) {
            addCriterion("is_asset in", values, "isAsset");
            return (Criteria) this;
        }

        public Criteria andIsAssetNotIn(List<Integer> values) {
            addCriterion("is_asset not in", values, "isAsset");
            return (Criteria) this;
        }

        public Criteria andIsAssetBetween(Integer value1, Integer value2) {
            addCriterion("is_asset between", value1, value2, "isAsset");
            return (Criteria) this;
        }

        public Criteria andIsAssetNotBetween(Integer value1, Integer value2) {
            addCriterion("is_asset not between", value1, value2, "isAsset");
            return (Criteria) this;
        }

        public Criteria andIsVehicleIsNull() {
            addCriterion("is_vehicle is null");
            return (Criteria) this;
        }

        public Criteria andIsVehicleIsNotNull() {
            addCriterion("is_vehicle is not null");
            return (Criteria) this;
        }

        public Criteria andIsVehicleEqualTo(Integer value) {
            addCriterion("is_vehicle =", value, "isVehicle");
            return (Criteria) this;
        }

        public Criteria andIsVehicleNotEqualTo(Integer value) {
            addCriterion("is_vehicle <>", value, "isVehicle");
            return (Criteria) this;
        }

        public Criteria andIsVehicleGreaterThan(Integer value) {
            addCriterion("is_vehicle >", value, "isVehicle");
            return (Criteria) this;
        }

        public Criteria andIsVehicleGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_vehicle >=", value, "isVehicle");
            return (Criteria) this;
        }

        public Criteria andIsVehicleLessThan(Integer value) {
            addCriterion("is_vehicle <", value, "isVehicle");
            return (Criteria) this;
        }

        public Criteria andIsVehicleLessThanOrEqualTo(Integer value) {
            addCriterion("is_vehicle <=", value, "isVehicle");
            return (Criteria) this;
        }

        public Criteria andIsVehicleIn(List<Integer> values) {
            addCriterion("is_vehicle in", values, "isVehicle");
            return (Criteria) this;
        }

        public Criteria andIsVehicleNotIn(List<Integer> values) {
            addCriterion("is_vehicle not in", values, "isVehicle");
            return (Criteria) this;
        }

        public Criteria andIsVehicleBetween(Integer value1, Integer value2) {
            addCriterion("is_vehicle between", value1, value2, "isVehicle");
            return (Criteria) this;
        }

        public Criteria andIsVehicleNotBetween(Integer value1, Integer value2) {
            addCriterion("is_vehicle not between", value1, value2, "isVehicle");
            return (Criteria) this;
        }

        public Criteria andIsDrivingLicenseIsNull() {
            addCriterion("is_driving_license is null");
            return (Criteria) this;
        }

        public Criteria andIsDrivingLicenseIsNotNull() {
            addCriterion("is_driving_license is not null");
            return (Criteria) this;
        }

        public Criteria andIsDrivingLicenseEqualTo(Integer value) {
            addCriterion("is_driving_license =", value, "isDrivingLicense");
            return (Criteria) this;
        }

        public Criteria andIsDrivingLicenseNotEqualTo(Integer value) {
            addCriterion("is_driving_license <>", value, "isDrivingLicense");
            return (Criteria) this;
        }

        public Criteria andIsDrivingLicenseGreaterThan(Integer value) {
            addCriterion("is_driving_license >", value, "isDrivingLicense");
            return (Criteria) this;
        }

        public Criteria andIsDrivingLicenseGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_driving_license >=", value, "isDrivingLicense");
            return (Criteria) this;
        }

        public Criteria andIsDrivingLicenseLessThan(Integer value) {
            addCriterion("is_driving_license <", value, "isDrivingLicense");
            return (Criteria) this;
        }

        public Criteria andIsDrivingLicenseLessThanOrEqualTo(Integer value) {
            addCriterion("is_driving_license <=", value, "isDrivingLicense");
            return (Criteria) this;
        }

        public Criteria andIsDrivingLicenseIn(List<Integer> values) {
            addCriterion("is_driving_license in", values, "isDrivingLicense");
            return (Criteria) this;
        }

        public Criteria andIsDrivingLicenseNotIn(List<Integer> values) {
            addCriterion("is_driving_license not in", values, "isDrivingLicense");
            return (Criteria) this;
        }

        public Criteria andIsDrivingLicenseBetween(Integer value1, Integer value2) {
            addCriterion("is_driving_license between", value1, value2, "isDrivingLicense");
            return (Criteria) this;
        }

        public Criteria andIsDrivingLicenseNotBetween(Integer value1, Integer value2) {
            addCriterion("is_driving_license not between", value1, value2, "isDrivingLicense");
            return (Criteria) this;
        }

        public Criteria andIsVehicleRegistrationIsNull() {
            addCriterion("is_vehicle_registration is null");
            return (Criteria) this;
        }

        public Criteria andIsVehicleRegistrationIsNotNull() {
            addCriterion("is_vehicle_registration is not null");
            return (Criteria) this;
        }

        public Criteria andIsVehicleRegistrationEqualTo(Integer value) {
            addCriterion("is_vehicle_registration =", value, "isVehicleRegistration");
            return (Criteria) this;
        }

        public Criteria andIsVehicleRegistrationNotEqualTo(Integer value) {
            addCriterion("is_vehicle_registration <>", value, "isVehicleRegistration");
            return (Criteria) this;
        }

        public Criteria andIsVehicleRegistrationGreaterThan(Integer value) {
            addCriterion("is_vehicle_registration >", value, "isVehicleRegistration");
            return (Criteria) this;
        }

        public Criteria andIsVehicleRegistrationGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_vehicle_registration >=", value, "isVehicleRegistration");
            return (Criteria) this;
        }

        public Criteria andIsVehicleRegistrationLessThan(Integer value) {
            addCriterion("is_vehicle_registration <", value, "isVehicleRegistration");
            return (Criteria) this;
        }

        public Criteria andIsVehicleRegistrationLessThanOrEqualTo(Integer value) {
            addCriterion("is_vehicle_registration <=", value, "isVehicleRegistration");
            return (Criteria) this;
        }

        public Criteria andIsVehicleRegistrationIn(List<Integer> values) {
            addCriterion("is_vehicle_registration in", values, "isVehicleRegistration");
            return (Criteria) this;
        }

        public Criteria andIsVehicleRegistrationNotIn(List<Integer> values) {
            addCriterion("is_vehicle_registration not in", values, "isVehicleRegistration");
            return (Criteria) this;
        }

        public Criteria andIsVehicleRegistrationBetween(Integer value1, Integer value2) {
            addCriterion("is_vehicle_registration between", value1, value2, "isVehicleRegistration");
            return (Criteria) this;
        }

        public Criteria andIsVehicleRegistrationNotBetween(Integer value1, Integer value2) {
            addCriterion("is_vehicle_registration not between", value1, value2, "isVehicleRegistration");
            return (Criteria) this;
        }

        public Criteria andIsMerryIsNull() {
            addCriterion("is_merry is null");
            return (Criteria) this;
        }

        public Criteria andIsMerryIsNotNull() {
            addCriterion("is_merry is not null");
            return (Criteria) this;
        }

        public Criteria andIsMerryEqualTo(Integer value) {
            addCriterion("is_merry =", value, "isMerry");
            return (Criteria) this;
        }

        public Criteria andIsMerryNotEqualTo(Integer value) {
            addCriterion("is_merry <>", value, "isMerry");
            return (Criteria) this;
        }

        public Criteria andIsMerryGreaterThan(Integer value) {
            addCriterion("is_merry >", value, "isMerry");
            return (Criteria) this;
        }

        public Criteria andIsMerryGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_merry >=", value, "isMerry");
            return (Criteria) this;
        }

        public Criteria andIsMerryLessThan(Integer value) {
            addCriterion("is_merry <", value, "isMerry");
            return (Criteria) this;
        }

        public Criteria andIsMerryLessThanOrEqualTo(Integer value) {
            addCriterion("is_merry <=", value, "isMerry");
            return (Criteria) this;
        }

        public Criteria andIsMerryIn(List<Integer> values) {
            addCriterion("is_merry in", values, "isMerry");
            return (Criteria) this;
        }

        public Criteria andIsMerryNotIn(List<Integer> values) {
            addCriterion("is_merry not in", values, "isMerry");
            return (Criteria) this;
        }

        public Criteria andIsMerryBetween(Integer value1, Integer value2) {
            addCriterion("is_merry between", value1, value2, "isMerry");
            return (Criteria) this;
        }

        public Criteria andIsMerryNotBetween(Integer value1, Integer value2) {
            addCriterion("is_merry not between", value1, value2, "isMerry");
            return (Criteria) this;
        }

        public Criteria andIsWorkIsNull() {
            addCriterion("is_work is null");
            return (Criteria) this;
        }

        public Criteria andIsWorkIsNotNull() {
            addCriterion("is_work is not null");
            return (Criteria) this;
        }

        public Criteria andIsWorkEqualTo(Integer value) {
            addCriterion("is_work =", value, "isWork");
            return (Criteria) this;
        }

        public Criteria andIsWorkNotEqualTo(Integer value) {
            addCriterion("is_work <>", value, "isWork");
            return (Criteria) this;
        }

        public Criteria andIsWorkGreaterThan(Integer value) {
            addCriterion("is_work >", value, "isWork");
            return (Criteria) this;
        }

        public Criteria andIsWorkGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_work >=", value, "isWork");
            return (Criteria) this;
        }

        public Criteria andIsWorkLessThan(Integer value) {
            addCriterion("is_work <", value, "isWork");
            return (Criteria) this;
        }

        public Criteria andIsWorkLessThanOrEqualTo(Integer value) {
            addCriterion("is_work <=", value, "isWork");
            return (Criteria) this;
        }

        public Criteria andIsWorkIn(List<Integer> values) {
            addCriterion("is_work in", values, "isWork");
            return (Criteria) this;
        }

        public Criteria andIsWorkNotIn(List<Integer> values) {
            addCriterion("is_work not in", values, "isWork");
            return (Criteria) this;
        }

        public Criteria andIsWorkBetween(Integer value1, Integer value2) {
            addCriterion("is_work between", value1, value2, "isWork");
            return (Criteria) this;
        }

        public Criteria andIsWorkNotBetween(Integer value1, Integer value2) {
            addCriterion("is_work not between", value1, value2, "isWork");
            return (Criteria) this;
        }

        public Criteria andIsAccountBookIsNull() {
            addCriterion("is_account_book is null");
            return (Criteria) this;
        }

        public Criteria andIsAccountBookIsNotNull() {
            addCriterion("is_account_book is not null");
            return (Criteria) this;
        }

        public Criteria andIsAccountBookEqualTo(Integer value) {
            addCriterion("is_account_book =", value, "isAccountBook");
            return (Criteria) this;
        }

        public Criteria andIsAccountBookNotEqualTo(Integer value) {
            addCriterion("is_account_book <>", value, "isAccountBook");
            return (Criteria) this;
        }

        public Criteria andIsAccountBookGreaterThan(Integer value) {
            addCriterion("is_account_book >", value, "isAccountBook");
            return (Criteria) this;
        }

        public Criteria andIsAccountBookGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_account_book >=", value, "isAccountBook");
            return (Criteria) this;
        }

        public Criteria andIsAccountBookLessThan(Integer value) {
            addCriterion("is_account_book <", value, "isAccountBook");
            return (Criteria) this;
        }

        public Criteria andIsAccountBookLessThanOrEqualTo(Integer value) {
            addCriterion("is_account_book <=", value, "isAccountBook");
            return (Criteria) this;
        }

        public Criteria andIsAccountBookIn(List<Integer> values) {
            addCriterion("is_account_book in", values, "isAccountBook");
            return (Criteria) this;
        }

        public Criteria andIsAccountBookNotIn(List<Integer> values) {
            addCriterion("is_account_book not in", values, "isAccountBook");
            return (Criteria) this;
        }

        public Criteria andIsAccountBookBetween(Integer value1, Integer value2) {
            addCriterion("is_account_book between", value1, value2, "isAccountBook");
            return (Criteria) this;
        }

        public Criteria andIsAccountBookNotBetween(Integer value1, Integer value2) {
            addCriterion("is_account_book not between", value1, value2, "isAccountBook");
            return (Criteria) this;
        }

        public Criteria andAnnualIncomeIsNull() {
            addCriterion("annual_income is null");
            return (Criteria) this;
        }

        public Criteria andAnnualIncomeIsNotNull() {
            addCriterion("annual_income is not null");
            return (Criteria) this;
        }

        public Criteria andAnnualIncomeEqualTo(String value) {
            addCriterion("annual_income =", value, "annualIncome");
            return (Criteria) this;
        }

        public Criteria andAnnualIncomeNotEqualTo(String value) {
            addCriterion("annual_income <>", value, "annualIncome");
            return (Criteria) this;
        }

        public Criteria andAnnualIncomeGreaterThan(String value) {
            addCriterion("annual_income >", value, "annualIncome");
            return (Criteria) this;
        }

        public Criteria andAnnualIncomeGreaterThanOrEqualTo(String value) {
            addCriterion("annual_income >=", value, "annualIncome");
            return (Criteria) this;
        }

        public Criteria andAnnualIncomeLessThan(String value) {
            addCriterion("annual_income <", value, "annualIncome");
            return (Criteria) this;
        }

        public Criteria andAnnualIncomeLessThanOrEqualTo(String value) {
            addCriterion("annual_income <=", value, "annualIncome");
            return (Criteria) this;
        }

        public Criteria andAnnualIncomeLike(String value) {
            addCriterion("annual_income like", value, "annualIncome");
            return (Criteria) this;
        }

        public Criteria andAnnualIncomeNotLike(String value) {
            addCriterion("annual_income not like", value, "annualIncome");
            return (Criteria) this;
        }

        public Criteria andAnnualIncomeIn(List<String> values) {
            addCriterion("annual_income in", values, "annualIncome");
            return (Criteria) this;
        }

        public Criteria andAnnualIncomeNotIn(List<String> values) {
            addCriterion("annual_income not in", values, "annualIncome");
            return (Criteria) this;
        }

        public Criteria andAnnualIncomeBetween(String value1, String value2) {
            addCriterion("annual_income between", value1, value2, "annualIncome");
            return (Criteria) this;
        }

        public Criteria andAnnualIncomeNotBetween(String value1, String value2) {
            addCriterion("annual_income not between", value1, value2, "annualIncome");
            return (Criteria) this;
        }

        public Criteria andOverdueReportIsNull() {
            addCriterion("overdue_report is null");
            return (Criteria) this;
        }

        public Criteria andOverdueReportIsNotNull() {
            addCriterion("overdue_report is not null");
            return (Criteria) this;
        }

        public Criteria andOverdueReportEqualTo(String value) {
            addCriterion("overdue_report =", value, "overdueReport");
            return (Criteria) this;
        }

        public Criteria andOverdueReportNotEqualTo(String value) {
            addCriterion("overdue_report <>", value, "overdueReport");
            return (Criteria) this;
        }

        public Criteria andOverdueReportGreaterThan(String value) {
            addCriterion("overdue_report >", value, "overdueReport");
            return (Criteria) this;
        }

        public Criteria andOverdueReportGreaterThanOrEqualTo(String value) {
            addCriterion("overdue_report >=", value, "overdueReport");
            return (Criteria) this;
        }

        public Criteria andOverdueReportLessThan(String value) {
            addCriterion("overdue_report <", value, "overdueReport");
            return (Criteria) this;
        }

        public Criteria andOverdueReportLessThanOrEqualTo(String value) {
            addCriterion("overdue_report <=", value, "overdueReport");
            return (Criteria) this;
        }

        public Criteria andOverdueReportLike(String value) {
            addCriterion("overdue_report like", value, "overdueReport");
            return (Criteria) this;
        }

        public Criteria andOverdueReportNotLike(String value) {
            addCriterion("overdue_report not like", value, "overdueReport");
            return (Criteria) this;
        }

        public Criteria andOverdueReportIn(List<String> values) {
            addCriterion("overdue_report in", values, "overdueReport");
            return (Criteria) this;
        }

        public Criteria andOverdueReportNotIn(List<String> values) {
            addCriterion("overdue_report not in", values, "overdueReport");
            return (Criteria) this;
        }

        public Criteria andOverdueReportBetween(String value1, String value2) {
            addCriterion("overdue_report between", value1, value2, "overdueReport");
            return (Criteria) this;
        }

        public Criteria andOverdueReportNotBetween(String value1, String value2) {
            addCriterion("overdue_report not between", value1, value2, "overdueReport");
            return (Criteria) this;
        }

        public Criteria andDebtSituationIsNull() {
            addCriterion("debt_situation is null");
            return (Criteria) this;
        }

        public Criteria andDebtSituationIsNotNull() {
            addCriterion("debt_situation is not null");
            return (Criteria) this;
        }

        public Criteria andDebtSituationEqualTo(String value) {
            addCriterion("debt_situation =", value, "debtSituation");
            return (Criteria) this;
        }

        public Criteria andDebtSituationNotEqualTo(String value) {
            addCriterion("debt_situation <>", value, "debtSituation");
            return (Criteria) this;
        }

        public Criteria andDebtSituationGreaterThan(String value) {
            addCriterion("debt_situation >", value, "debtSituation");
            return (Criteria) this;
        }

        public Criteria andDebtSituationGreaterThanOrEqualTo(String value) {
            addCriterion("debt_situation >=", value, "debtSituation");
            return (Criteria) this;
        }

        public Criteria andDebtSituationLessThan(String value) {
            addCriterion("debt_situation <", value, "debtSituation");
            return (Criteria) this;
        }

        public Criteria andDebtSituationLessThanOrEqualTo(String value) {
            addCriterion("debt_situation <=", value, "debtSituation");
            return (Criteria) this;
        }

        public Criteria andDebtSituationLike(String value) {
            addCriterion("debt_situation like", value, "debtSituation");
            return (Criteria) this;
        }

        public Criteria andDebtSituationNotLike(String value) {
            addCriterion("debt_situation not like", value, "debtSituation");
            return (Criteria) this;
        }

        public Criteria andDebtSituationIn(List<String> values) {
            addCriterion("debt_situation in", values, "debtSituation");
            return (Criteria) this;
        }

        public Criteria andDebtSituationNotIn(List<String> values) {
            addCriterion("debt_situation not in", values, "debtSituation");
            return (Criteria) this;
        }

        public Criteria andDebtSituationBetween(String value1, String value2) {
            addCriterion("debt_situation between", value1, value2, "debtSituation");
            return (Criteria) this;
        }

        public Criteria andDebtSituationNotBetween(String value1, String value2) {
            addCriterion("debt_situation not between", value1, value2, "debtSituation");
            return (Criteria) this;
        }

        public Criteria andOtherBorrowedIsNull() {
            addCriterion("other_borrowed is null");
            return (Criteria) this;
        }

        public Criteria andOtherBorrowedIsNotNull() {
            addCriterion("other_borrowed is not null");
            return (Criteria) this;
        }

        public Criteria andOtherBorrowedEqualTo(String value) {
            addCriterion("other_borrowed =", value, "otherBorrowed");
            return (Criteria) this;
        }

        public Criteria andOtherBorrowedNotEqualTo(String value) {
            addCriterion("other_borrowed <>", value, "otherBorrowed");
            return (Criteria) this;
        }

        public Criteria andOtherBorrowedGreaterThan(String value) {
            addCriterion("other_borrowed >", value, "otherBorrowed");
            return (Criteria) this;
        }

        public Criteria andOtherBorrowedGreaterThanOrEqualTo(String value) {
            addCriterion("other_borrowed >=", value, "otherBorrowed");
            return (Criteria) this;
        }

        public Criteria andOtherBorrowedLessThan(String value) {
            addCriterion("other_borrowed <", value, "otherBorrowed");
            return (Criteria) this;
        }

        public Criteria andOtherBorrowedLessThanOrEqualTo(String value) {
            addCriterion("other_borrowed <=", value, "otherBorrowed");
            return (Criteria) this;
        }

        public Criteria andOtherBorrowedLike(String value) {
            addCriterion("other_borrowed like", value, "otherBorrowed");
            return (Criteria) this;
        }

        public Criteria andOtherBorrowedNotLike(String value) {
            addCriterion("other_borrowed not like", value, "otherBorrowed");
            return (Criteria) this;
        }

        public Criteria andOtherBorrowedIn(List<String> values) {
            addCriterion("other_borrowed in", values, "otherBorrowed");
            return (Criteria) this;
        }

        public Criteria andOtherBorrowedNotIn(List<String> values) {
            addCriterion("other_borrowed not in", values, "otherBorrowed");
            return (Criteria) this;
        }

        public Criteria andOtherBorrowedBetween(String value1, String value2) {
            addCriterion("other_borrowed between", value1, value2, "otherBorrowed");
            return (Criteria) this;
        }

        public Criteria andOtherBorrowedNotBetween(String value1, String value2) {
            addCriterion("other_borrowed not between", value1, value2, "otherBorrowed");
            return (Criteria) this;
        }

        public Criteria andIsFundsIsNull() {
            addCriterion("is_funds is null");
            return (Criteria) this;
        }

        public Criteria andIsFundsIsNotNull() {
            addCriterion("is_funds is not null");
            return (Criteria) this;
        }

        public Criteria andIsFundsEqualTo(String value) {
            addCriterion("is_funds =", value, "isFunds");
            return (Criteria) this;
        }

        public Criteria andIsFundsNotEqualTo(String value) {
            addCriterion("is_funds <>", value, "isFunds");
            return (Criteria) this;
        }

        public Criteria andIsFundsGreaterThan(String value) {
            addCriterion("is_funds >", value, "isFunds");
            return (Criteria) this;
        }

        public Criteria andIsFundsGreaterThanOrEqualTo(String value) {
            addCriterion("is_funds >=", value, "isFunds");
            return (Criteria) this;
        }

        public Criteria andIsFundsLessThan(String value) {
            addCriterion("is_funds <", value, "isFunds");
            return (Criteria) this;
        }

        public Criteria andIsFundsLessThanOrEqualTo(String value) {
            addCriterion("is_funds <=", value, "isFunds");
            return (Criteria) this;
        }

        public Criteria andIsFundsLike(String value) {
            addCriterion("is_funds like", value, "isFunds");
            return (Criteria) this;
        }

        public Criteria andIsFundsNotLike(String value) {
            addCriterion("is_funds not like", value, "isFunds");
            return (Criteria) this;
        }

        public Criteria andIsFundsIn(List<String> values) {
            addCriterion("is_funds in", values, "isFunds");
            return (Criteria) this;
        }

        public Criteria andIsFundsNotIn(List<String> values) {
            addCriterion("is_funds not in", values, "isFunds");
            return (Criteria) this;
        }

        public Criteria andIsFundsBetween(String value1, String value2) {
            addCriterion("is_funds between", value1, value2, "isFunds");
            return (Criteria) this;
        }

        public Criteria andIsFundsNotBetween(String value1, String value2) {
            addCriterion("is_funds not between", value1, value2, "isFunds");
            return (Criteria) this;
        }

        public Criteria andIsManagedIsNull() {
            addCriterion("is_managed is null");
            return (Criteria) this;
        }

        public Criteria andIsManagedIsNotNull() {
            addCriterion("is_managed is not null");
            return (Criteria) this;
        }

        public Criteria andIsManagedEqualTo(String value) {
            addCriterion("is_managed =", value, "isManaged");
            return (Criteria) this;
        }

        public Criteria andIsManagedNotEqualTo(String value) {
            addCriterion("is_managed <>", value, "isManaged");
            return (Criteria) this;
        }

        public Criteria andIsManagedGreaterThan(String value) {
            addCriterion("is_managed >", value, "isManaged");
            return (Criteria) this;
        }

        public Criteria andIsManagedGreaterThanOrEqualTo(String value) {
            addCriterion("is_managed >=", value, "isManaged");
            return (Criteria) this;
        }

        public Criteria andIsManagedLessThan(String value) {
            addCriterion("is_managed <", value, "isManaged");
            return (Criteria) this;
        }

        public Criteria andIsManagedLessThanOrEqualTo(String value) {
            addCriterion("is_managed <=", value, "isManaged");
            return (Criteria) this;
        }

        public Criteria andIsManagedLike(String value) {
            addCriterion("is_managed like", value, "isManaged");
            return (Criteria) this;
        }

        public Criteria andIsManagedNotLike(String value) {
            addCriterion("is_managed not like", value, "isManaged");
            return (Criteria) this;
        }

        public Criteria andIsManagedIn(List<String> values) {
            addCriterion("is_managed in", values, "isManaged");
            return (Criteria) this;
        }

        public Criteria andIsManagedNotIn(List<String> values) {
            addCriterion("is_managed not in", values, "isManaged");
            return (Criteria) this;
        }

        public Criteria andIsManagedBetween(String value1, String value2) {
            addCriterion("is_managed between", value1, value2, "isManaged");
            return (Criteria) this;
        }

        public Criteria andIsManagedNotBetween(String value1, String value2) {
            addCriterion("is_managed not between", value1, value2, "isManaged");
            return (Criteria) this;
        }

        public Criteria andIsAbilityIsNull() {
            addCriterion("is_ability is null");
            return (Criteria) this;
        }

        public Criteria andIsAbilityIsNotNull() {
            addCriterion("is_ability is not null");
            return (Criteria) this;
        }

        public Criteria andIsAbilityEqualTo(String value) {
            addCriterion("is_ability =", value, "isAbility");
            return (Criteria) this;
        }

        public Criteria andIsAbilityNotEqualTo(String value) {
            addCriterion("is_ability <>", value, "isAbility");
            return (Criteria) this;
        }

        public Criteria andIsAbilityGreaterThan(String value) {
            addCriterion("is_ability >", value, "isAbility");
            return (Criteria) this;
        }

        public Criteria andIsAbilityGreaterThanOrEqualTo(String value) {
            addCriterion("is_ability >=", value, "isAbility");
            return (Criteria) this;
        }

        public Criteria andIsAbilityLessThan(String value) {
            addCriterion("is_ability <", value, "isAbility");
            return (Criteria) this;
        }

        public Criteria andIsAbilityLessThanOrEqualTo(String value) {
            addCriterion("is_ability <=", value, "isAbility");
            return (Criteria) this;
        }

        public Criteria andIsAbilityLike(String value) {
            addCriterion("is_ability like", value, "isAbility");
            return (Criteria) this;
        }

        public Criteria andIsAbilityNotLike(String value) {
            addCriterion("is_ability not like", value, "isAbility");
            return (Criteria) this;
        }

        public Criteria andIsAbilityIn(List<String> values) {
            addCriterion("is_ability in", values, "isAbility");
            return (Criteria) this;
        }

        public Criteria andIsAbilityNotIn(List<String> values) {
            addCriterion("is_ability not in", values, "isAbility");
            return (Criteria) this;
        }

        public Criteria andIsAbilityBetween(String value1, String value2) {
            addCriterion("is_ability between", value1, value2, "isAbility");
            return (Criteria) this;
        }

        public Criteria andIsAbilityNotBetween(String value1, String value2) {
            addCriterion("is_ability not between", value1, value2, "isAbility");
            return (Criteria) this;
        }

        public Criteria andIsOverdueIsNull() {
            addCriterion("is_overdue is null");
            return (Criteria) this;
        }

        public Criteria andIsOverdueIsNotNull() {
            addCriterion("is_overdue is not null");
            return (Criteria) this;
        }

        public Criteria andIsOverdueEqualTo(String value) {
            addCriterion("is_overdue =", value, "isOverdue");
            return (Criteria) this;
        }

        public Criteria andIsOverdueNotEqualTo(String value) {
            addCriterion("is_overdue <>", value, "isOverdue");
            return (Criteria) this;
        }

        public Criteria andIsOverdueGreaterThan(String value) {
            addCriterion("is_overdue >", value, "isOverdue");
            return (Criteria) this;
        }

        public Criteria andIsOverdueGreaterThanOrEqualTo(String value) {
            addCriterion("is_overdue >=", value, "isOverdue");
            return (Criteria) this;
        }

        public Criteria andIsOverdueLessThan(String value) {
            addCriterion("is_overdue <", value, "isOverdue");
            return (Criteria) this;
        }

        public Criteria andIsOverdueLessThanOrEqualTo(String value) {
            addCriterion("is_overdue <=", value, "isOverdue");
            return (Criteria) this;
        }

        public Criteria andIsOverdueLike(String value) {
            addCriterion("is_overdue like", value, "isOverdue");
            return (Criteria) this;
        }

        public Criteria andIsOverdueNotLike(String value) {
            addCriterion("is_overdue not like", value, "isOverdue");
            return (Criteria) this;
        }

        public Criteria andIsOverdueIn(List<String> values) {
            addCriterion("is_overdue in", values, "isOverdue");
            return (Criteria) this;
        }

        public Criteria andIsOverdueNotIn(List<String> values) {
            addCriterion("is_overdue not in", values, "isOverdue");
            return (Criteria) this;
        }

        public Criteria andIsOverdueBetween(String value1, String value2) {
            addCriterion("is_overdue between", value1, value2, "isOverdue");
            return (Criteria) this;
        }

        public Criteria andIsOverdueNotBetween(String value1, String value2) {
            addCriterion("is_overdue not between", value1, value2, "isOverdue");
            return (Criteria) this;
        }

        public Criteria andIsComplaintIsNull() {
            addCriterion("is_complaint is null");
            return (Criteria) this;
        }

        public Criteria andIsComplaintIsNotNull() {
            addCriterion("is_complaint is not null");
            return (Criteria) this;
        }

        public Criteria andIsComplaintEqualTo(String value) {
            addCriterion("is_complaint =", value, "isComplaint");
            return (Criteria) this;
        }

        public Criteria andIsComplaintNotEqualTo(String value) {
            addCriterion("is_complaint <>", value, "isComplaint");
            return (Criteria) this;
        }

        public Criteria andIsComplaintGreaterThan(String value) {
            addCriterion("is_complaint >", value, "isComplaint");
            return (Criteria) this;
        }

        public Criteria andIsComplaintGreaterThanOrEqualTo(String value) {
            addCriterion("is_complaint >=", value, "isComplaint");
            return (Criteria) this;
        }

        public Criteria andIsComplaintLessThan(String value) {
            addCriterion("is_complaint <", value, "isComplaint");
            return (Criteria) this;
        }

        public Criteria andIsComplaintLessThanOrEqualTo(String value) {
            addCriterion("is_complaint <=", value, "isComplaint");
            return (Criteria) this;
        }

        public Criteria andIsComplaintLike(String value) {
            addCriterion("is_complaint like", value, "isComplaint");
            return (Criteria) this;
        }

        public Criteria andIsComplaintNotLike(String value) {
            addCriterion("is_complaint not like", value, "isComplaint");
            return (Criteria) this;
        }

        public Criteria andIsComplaintIn(List<String> values) {
            addCriterion("is_complaint in", values, "isComplaint");
            return (Criteria) this;
        }

        public Criteria andIsComplaintNotIn(List<String> values) {
            addCriterion("is_complaint not in", values, "isComplaint");
            return (Criteria) this;
        }

        public Criteria andIsComplaintBetween(String value1, String value2) {
            addCriterion("is_complaint between", value1, value2, "isComplaint");
            return (Criteria) this;
        }

        public Criteria andIsComplaintNotBetween(String value1, String value2) {
            addCriterion("is_complaint not between", value1, value2, "isComplaint");
            return (Criteria) this;
        }

        public Criteria andIsPunishedIsNull() {
            addCriterion("is_punished is null");
            return (Criteria) this;
        }

        public Criteria andIsPunishedIsNotNull() {
            addCriterion("is_punished is not null");
            return (Criteria) this;
        }

        public Criteria andIsPunishedEqualTo(String value) {
            addCriterion("is_punished =", value, "isPunished");
            return (Criteria) this;
        }

        public Criteria andIsPunishedNotEqualTo(String value) {
            addCriterion("is_punished <>", value, "isPunished");
            return (Criteria) this;
        }

        public Criteria andIsPunishedGreaterThan(String value) {
            addCriterion("is_punished >", value, "isPunished");
            return (Criteria) this;
        }

        public Criteria andIsPunishedGreaterThanOrEqualTo(String value) {
            addCriterion("is_punished >=", value, "isPunished");
            return (Criteria) this;
        }

        public Criteria andIsPunishedLessThan(String value) {
            addCriterion("is_punished <", value, "isPunished");
            return (Criteria) this;
        }

        public Criteria andIsPunishedLessThanOrEqualTo(String value) {
            addCriterion("is_punished <=", value, "isPunished");
            return (Criteria) this;
        }

        public Criteria andIsPunishedLike(String value) {
            addCriterion("is_punished like", value, "isPunished");
            return (Criteria) this;
        }

        public Criteria andIsPunishedNotLike(String value) {
            addCriterion("is_punished not like", value, "isPunished");
            return (Criteria) this;
        }

        public Criteria andIsPunishedIn(List<String> values) {
            addCriterion("is_punished in", values, "isPunished");
            return (Criteria) this;
        }

        public Criteria andIsPunishedNotIn(List<String> values) {
            addCriterion("is_punished not in", values, "isPunished");
            return (Criteria) this;
        }

        public Criteria andIsPunishedBetween(String value1, String value2) {
            addCriterion("is_punished between", value1, value2, "isPunished");
            return (Criteria) this;
        }

        public Criteria andIsPunishedNotBetween(String value1, String value2) {
            addCriterion("is_punished not between", value1, value2, "isPunished");
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