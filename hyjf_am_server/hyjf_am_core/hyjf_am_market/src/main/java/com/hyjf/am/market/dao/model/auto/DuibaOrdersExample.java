package com.hyjf.am.market.dao.model.auto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DuibaOrdersExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public DuibaOrdersExample() {
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

        public Criteria andDuibaOrderIdIsNull() {
            addCriterion("duiba_order_id is null");
            return (Criteria) this;
        }

        public Criteria andDuibaOrderIdIsNotNull() {
            addCriterion("duiba_order_id is not null");
            return (Criteria) this;
        }

        public Criteria andDuibaOrderIdEqualTo(String value) {
            addCriterion("duiba_order_id =", value, "duibaOrderId");
            return (Criteria) this;
        }

        public Criteria andDuibaOrderIdNotEqualTo(String value) {
            addCriterion("duiba_order_id <>", value, "duibaOrderId");
            return (Criteria) this;
        }

        public Criteria andDuibaOrderIdGreaterThan(String value) {
            addCriterion("duiba_order_id >", value, "duibaOrderId");
            return (Criteria) this;
        }

        public Criteria andDuibaOrderIdGreaterThanOrEqualTo(String value) {
            addCriterion("duiba_order_id >=", value, "duibaOrderId");
            return (Criteria) this;
        }

        public Criteria andDuibaOrderIdLessThan(String value) {
            addCriterion("duiba_order_id <", value, "duibaOrderId");
            return (Criteria) this;
        }

        public Criteria andDuibaOrderIdLessThanOrEqualTo(String value) {
            addCriterion("duiba_order_id <=", value, "duibaOrderId");
            return (Criteria) this;
        }

        public Criteria andDuibaOrderIdLike(String value) {
            addCriterion("duiba_order_id like", value, "duibaOrderId");
            return (Criteria) this;
        }

        public Criteria andDuibaOrderIdNotLike(String value) {
            addCriterion("duiba_order_id not like", value, "duibaOrderId");
            return (Criteria) this;
        }

        public Criteria andDuibaOrderIdIn(List<String> values) {
            addCriterion("duiba_order_id in", values, "duibaOrderId");
            return (Criteria) this;
        }

        public Criteria andDuibaOrderIdNotIn(List<String> values) {
            addCriterion("duiba_order_id not in", values, "duibaOrderId");
            return (Criteria) this;
        }

        public Criteria andDuibaOrderIdBetween(String value1, String value2) {
            addCriterion("duiba_order_id between", value1, value2, "duibaOrderId");
            return (Criteria) this;
        }

        public Criteria andDuibaOrderIdNotBetween(String value1, String value2) {
            addCriterion("duiba_order_id not between", value1, value2, "duibaOrderId");
            return (Criteria) this;
        }

        public Criteria andHyOrderIdIsNull() {
            addCriterion("hy_order_id is null");
            return (Criteria) this;
        }

        public Criteria andHyOrderIdIsNotNull() {
            addCriterion("hy_order_id is not null");
            return (Criteria) this;
        }

        public Criteria andHyOrderIdEqualTo(String value) {
            addCriterion("hy_order_id =", value, "hyOrderId");
            return (Criteria) this;
        }

        public Criteria andHyOrderIdNotEqualTo(String value) {
            addCriterion("hy_order_id <>", value, "hyOrderId");
            return (Criteria) this;
        }

        public Criteria andHyOrderIdGreaterThan(String value) {
            addCriterion("hy_order_id >", value, "hyOrderId");
            return (Criteria) this;
        }

        public Criteria andHyOrderIdGreaterThanOrEqualTo(String value) {
            addCriterion("hy_order_id >=", value, "hyOrderId");
            return (Criteria) this;
        }

        public Criteria andHyOrderIdLessThan(String value) {
            addCriterion("hy_order_id <", value, "hyOrderId");
            return (Criteria) this;
        }

        public Criteria andHyOrderIdLessThanOrEqualTo(String value) {
            addCriterion("hy_order_id <=", value, "hyOrderId");
            return (Criteria) this;
        }

        public Criteria andHyOrderIdLike(String value) {
            addCriterion("hy_order_id like", value, "hyOrderId");
            return (Criteria) this;
        }

        public Criteria andHyOrderIdNotLike(String value) {
            addCriterion("hy_order_id not like", value, "hyOrderId");
            return (Criteria) this;
        }

        public Criteria andHyOrderIdIn(List<String> values) {
            addCriterion("hy_order_id in", values, "hyOrderId");
            return (Criteria) this;
        }

        public Criteria andHyOrderIdNotIn(List<String> values) {
            addCriterion("hy_order_id not in", values, "hyOrderId");
            return (Criteria) this;
        }

        public Criteria andHyOrderIdBetween(String value1, String value2) {
            addCriterion("hy_order_id between", value1, value2, "hyOrderId");
            return (Criteria) this;
        }

        public Criteria andHyOrderIdNotBetween(String value1, String value2) {
            addCriterion("hy_order_id not between", value1, value2, "hyOrderId");
            return (Criteria) this;
        }

        public Criteria andUserNameIsNull() {
            addCriterion("user_name is null");
            return (Criteria) this;
        }

        public Criteria andUserNameIsNotNull() {
            addCriterion("user_name is not null");
            return (Criteria) this;
        }

        public Criteria andUserNameEqualTo(String value) {
            addCriterion("user_name =", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotEqualTo(String value) {
            addCriterion("user_name <>", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameGreaterThan(String value) {
            addCriterion("user_name >", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("user_name >=", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLessThan(String value) {
            addCriterion("user_name <", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLessThanOrEqualTo(String value) {
            addCriterion("user_name <=", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLike(String value) {
            addCriterion("user_name like", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotLike(String value) {
            addCriterion("user_name not like", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameIn(List<String> values) {
            addCriterion("user_name in", values, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotIn(List<String> values) {
            addCriterion("user_name not in", values, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameBetween(String value1, String value2) {
            addCriterion("user_name between", value1, value2, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotBetween(String value1, String value2) {
            addCriterion("user_name not between", value1, value2, "userName");
            return (Criteria) this;
        }

        public Criteria andTrueNameIsNull() {
            addCriterion("true_name is null");
            return (Criteria) this;
        }

        public Criteria andTrueNameIsNotNull() {
            addCriterion("true_name is not null");
            return (Criteria) this;
        }

        public Criteria andTrueNameEqualTo(String value) {
            addCriterion("true_name =", value, "trueName");
            return (Criteria) this;
        }

        public Criteria andTrueNameNotEqualTo(String value) {
            addCriterion("true_name <>", value, "trueName");
            return (Criteria) this;
        }

        public Criteria andTrueNameGreaterThan(String value) {
            addCriterion("true_name >", value, "trueName");
            return (Criteria) this;
        }

        public Criteria andTrueNameGreaterThanOrEqualTo(String value) {
            addCriterion("true_name >=", value, "trueName");
            return (Criteria) this;
        }

        public Criteria andTrueNameLessThan(String value) {
            addCriterion("true_name <", value, "trueName");
            return (Criteria) this;
        }

        public Criteria andTrueNameLessThanOrEqualTo(String value) {
            addCriterion("true_name <=", value, "trueName");
            return (Criteria) this;
        }

        public Criteria andTrueNameLike(String value) {
            addCriterion("true_name like", value, "trueName");
            return (Criteria) this;
        }

        public Criteria andTrueNameNotLike(String value) {
            addCriterion("true_name not like", value, "trueName");
            return (Criteria) this;
        }

        public Criteria andTrueNameIn(List<String> values) {
            addCriterion("true_name in", values, "trueName");
            return (Criteria) this;
        }

        public Criteria andTrueNameNotIn(List<String> values) {
            addCriterion("true_name not in", values, "trueName");
            return (Criteria) this;
        }

        public Criteria andTrueNameBetween(String value1, String value2) {
            addCriterion("true_name between", value1, value2, "trueName");
            return (Criteria) this;
        }

        public Criteria andTrueNameNotBetween(String value1, String value2) {
            addCriterion("true_name not between", value1, value2, "trueName");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(Integer value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(Integer value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(Integer value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(Integer value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<Integer> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<Integer> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(Integer value1, Integer value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andExchangeContentIsNull() {
            addCriterion("exchange_content is null");
            return (Criteria) this;
        }

        public Criteria andExchangeContentIsNotNull() {
            addCriterion("exchange_content is not null");
            return (Criteria) this;
        }

        public Criteria andExchangeContentEqualTo(String value) {
            addCriterion("exchange_content =", value, "exchangeContent");
            return (Criteria) this;
        }

        public Criteria andExchangeContentNotEqualTo(String value) {
            addCriterion("exchange_content <>", value, "exchangeContent");
            return (Criteria) this;
        }

        public Criteria andExchangeContentGreaterThan(String value) {
            addCriterion("exchange_content >", value, "exchangeContent");
            return (Criteria) this;
        }

        public Criteria andExchangeContentGreaterThanOrEqualTo(String value) {
            addCriterion("exchange_content >=", value, "exchangeContent");
            return (Criteria) this;
        }

        public Criteria andExchangeContentLessThan(String value) {
            addCriterion("exchange_content <", value, "exchangeContent");
            return (Criteria) this;
        }

        public Criteria andExchangeContentLessThanOrEqualTo(String value) {
            addCriterion("exchange_content <=", value, "exchangeContent");
            return (Criteria) this;
        }

        public Criteria andExchangeContentLike(String value) {
            addCriterion("exchange_content like", value, "exchangeContent");
            return (Criteria) this;
        }

        public Criteria andExchangeContentNotLike(String value) {
            addCriterion("exchange_content not like", value, "exchangeContent");
            return (Criteria) this;
        }

        public Criteria andExchangeContentIn(List<String> values) {
            addCriterion("exchange_content in", values, "exchangeContent");
            return (Criteria) this;
        }

        public Criteria andExchangeContentNotIn(List<String> values) {
            addCriterion("exchange_content not in", values, "exchangeContent");
            return (Criteria) this;
        }

        public Criteria andExchangeContentBetween(String value1, String value2) {
            addCriterion("exchange_content between", value1, value2, "exchangeContent");
            return (Criteria) this;
        }

        public Criteria andExchangeContentNotBetween(String value1, String value2) {
            addCriterion("exchange_content not between", value1, value2, "exchangeContent");
            return (Criteria) this;
        }

        public Criteria andProductTypeIsNull() {
            addCriterion("product_type is null");
            return (Criteria) this;
        }

        public Criteria andProductTypeIsNotNull() {
            addCriterion("product_type is not null");
            return (Criteria) this;
        }

        public Criteria andProductTypeEqualTo(String value) {
            addCriterion("product_type =", value, "productType");
            return (Criteria) this;
        }

        public Criteria andProductTypeNotEqualTo(String value) {
            addCriterion("product_type <>", value, "productType");
            return (Criteria) this;
        }

        public Criteria andProductTypeGreaterThan(String value) {
            addCriterion("product_type >", value, "productType");
            return (Criteria) this;
        }

        public Criteria andProductTypeGreaterThanOrEqualTo(String value) {
            addCriterion("product_type >=", value, "productType");
            return (Criteria) this;
        }

        public Criteria andProductTypeLessThan(String value) {
            addCriterion("product_type <", value, "productType");
            return (Criteria) this;
        }

        public Criteria andProductTypeLessThanOrEqualTo(String value) {
            addCriterion("product_type <=", value, "productType");
            return (Criteria) this;
        }

        public Criteria andProductTypeLike(String value) {
            addCriterion("product_type like", value, "productType");
            return (Criteria) this;
        }

        public Criteria andProductTypeNotLike(String value) {
            addCriterion("product_type not like", value, "productType");
            return (Criteria) this;
        }

        public Criteria andProductTypeIn(List<String> values) {
            addCriterion("product_type in", values, "productType");
            return (Criteria) this;
        }

        public Criteria andProductTypeNotIn(List<String> values) {
            addCriterion("product_type not in", values, "productType");
            return (Criteria) this;
        }

        public Criteria andProductTypeBetween(String value1, String value2) {
            addCriterion("product_type between", value1, value2, "productType");
            return (Criteria) this;
        }

        public Criteria andProductTypeNotBetween(String value1, String value2) {
            addCriterion("product_type not between", value1, value2, "productType");
            return (Criteria) this;
        }

        public Criteria andSellingPriceIsNull() {
            addCriterion("selling_price is null");
            return (Criteria) this;
        }

        public Criteria andSellingPriceIsNotNull() {
            addCriterion("selling_price is not null");
            return (Criteria) this;
        }

        public Criteria andSellingPriceEqualTo(BigDecimal value) {
            addCriterion("selling_price =", value, "sellingPrice");
            return (Criteria) this;
        }

        public Criteria andSellingPriceNotEqualTo(BigDecimal value) {
            addCriterion("selling_price <>", value, "sellingPrice");
            return (Criteria) this;
        }

        public Criteria andSellingPriceGreaterThan(BigDecimal value) {
            addCriterion("selling_price >", value, "sellingPrice");
            return (Criteria) this;
        }

        public Criteria andSellingPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("selling_price >=", value, "sellingPrice");
            return (Criteria) this;
        }

        public Criteria andSellingPriceLessThan(BigDecimal value) {
            addCriterion("selling_price <", value, "sellingPrice");
            return (Criteria) this;
        }

        public Criteria andSellingPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("selling_price <=", value, "sellingPrice");
            return (Criteria) this;
        }

        public Criteria andSellingPriceIn(List<BigDecimal> values) {
            addCriterion("selling_price in", values, "sellingPrice");
            return (Criteria) this;
        }

        public Criteria andSellingPriceNotIn(List<BigDecimal> values) {
            addCriterion("selling_price not in", values, "sellingPrice");
            return (Criteria) this;
        }

        public Criteria andSellingPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("selling_price between", value1, value2, "sellingPrice");
            return (Criteria) this;
        }

        public Criteria andSellingPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("selling_price not between", value1, value2, "sellingPrice");
            return (Criteria) this;
        }

        public Criteria andMarkingPriceIsNull() {
            addCriterion("marking_price is null");
            return (Criteria) this;
        }

        public Criteria andMarkingPriceIsNotNull() {
            addCriterion("marking_price is not null");
            return (Criteria) this;
        }

        public Criteria andMarkingPriceEqualTo(BigDecimal value) {
            addCriterion("marking_price =", value, "markingPrice");
            return (Criteria) this;
        }

        public Criteria andMarkingPriceNotEqualTo(BigDecimal value) {
            addCriterion("marking_price <>", value, "markingPrice");
            return (Criteria) this;
        }

        public Criteria andMarkingPriceGreaterThan(BigDecimal value) {
            addCriterion("marking_price >", value, "markingPrice");
            return (Criteria) this;
        }

        public Criteria andMarkingPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("marking_price >=", value, "markingPrice");
            return (Criteria) this;
        }

        public Criteria andMarkingPriceLessThan(BigDecimal value) {
            addCriterion("marking_price <", value, "markingPrice");
            return (Criteria) this;
        }

        public Criteria andMarkingPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("marking_price <=", value, "markingPrice");
            return (Criteria) this;
        }

        public Criteria andMarkingPriceIn(List<BigDecimal> values) {
            addCriterion("marking_price in", values, "markingPrice");
            return (Criteria) this;
        }

        public Criteria andMarkingPriceNotIn(List<BigDecimal> values) {
            addCriterion("marking_price not in", values, "markingPrice");
            return (Criteria) this;
        }

        public Criteria andMarkingPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("marking_price between", value1, value2, "markingPrice");
            return (Criteria) this;
        }

        public Criteria andMarkingPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("marking_price not between", value1, value2, "markingPrice");
            return (Criteria) this;
        }

        public Criteria andCostIsNull() {
            addCriterion("cost is null");
            return (Criteria) this;
        }

        public Criteria andCostIsNotNull() {
            addCriterion("cost is not null");
            return (Criteria) this;
        }

        public Criteria andCostEqualTo(BigDecimal value) {
            addCriterion("cost =", value, "cost");
            return (Criteria) this;
        }

        public Criteria andCostNotEqualTo(BigDecimal value) {
            addCriterion("cost <>", value, "cost");
            return (Criteria) this;
        }

        public Criteria andCostGreaterThan(BigDecimal value) {
            addCriterion("cost >", value, "cost");
            return (Criteria) this;
        }

        public Criteria andCostGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("cost >=", value, "cost");
            return (Criteria) this;
        }

        public Criteria andCostLessThan(BigDecimal value) {
            addCriterion("cost <", value, "cost");
            return (Criteria) this;
        }

        public Criteria andCostLessThanOrEqualTo(BigDecimal value) {
            addCriterion("cost <=", value, "cost");
            return (Criteria) this;
        }

        public Criteria andCostIn(List<BigDecimal> values) {
            addCriterion("cost in", values, "cost");
            return (Criteria) this;
        }

        public Criteria andCostNotIn(List<BigDecimal> values) {
            addCriterion("cost not in", values, "cost");
            return (Criteria) this;
        }

        public Criteria andCostBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("cost between", value1, value2, "cost");
            return (Criteria) this;
        }

        public Criteria andCostNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("cost not between", value1, value2, "cost");
            return (Criteria) this;
        }

        public Criteria andOrderStatusIsNull() {
            addCriterion("order_status is null");
            return (Criteria) this;
        }

        public Criteria andOrderStatusIsNotNull() {
            addCriterion("order_status is not null");
            return (Criteria) this;
        }

        public Criteria andOrderStatusEqualTo(Integer value) {
            addCriterion("order_status =", value, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusNotEqualTo(Integer value) {
            addCriterion("order_status <>", value, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusGreaterThan(Integer value) {
            addCriterion("order_status >", value, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("order_status >=", value, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusLessThan(Integer value) {
            addCriterion("order_status <", value, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusLessThanOrEqualTo(Integer value) {
            addCriterion("order_status <=", value, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusIn(List<Integer> values) {
            addCriterion("order_status in", values, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusNotIn(List<Integer> values) {
            addCriterion("order_status not in", values, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusBetween(Integer value1, Integer value2) {
            addCriterion("order_status between", value1, value2, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("order_status not between", value1, value2, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderTimeIsNull() {
            addCriterion("order_time is null");
            return (Criteria) this;
        }

        public Criteria andOrderTimeIsNotNull() {
            addCriterion("order_time is not null");
            return (Criteria) this;
        }

        public Criteria andOrderTimeEqualTo(Integer value) {
            addCriterion("order_time =", value, "orderTime");
            return (Criteria) this;
        }

        public Criteria andOrderTimeNotEqualTo(Integer value) {
            addCriterion("order_time <>", value, "orderTime");
            return (Criteria) this;
        }

        public Criteria andOrderTimeGreaterThan(Integer value) {
            addCriterion("order_time >", value, "orderTime");
            return (Criteria) this;
        }

        public Criteria andOrderTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("order_time >=", value, "orderTime");
            return (Criteria) this;
        }

        public Criteria andOrderTimeLessThan(Integer value) {
            addCriterion("order_time <", value, "orderTime");
            return (Criteria) this;
        }

        public Criteria andOrderTimeLessThanOrEqualTo(Integer value) {
            addCriterion("order_time <=", value, "orderTime");
            return (Criteria) this;
        }

        public Criteria andOrderTimeIn(List<Integer> values) {
            addCriterion("order_time in", values, "orderTime");
            return (Criteria) this;
        }

        public Criteria andOrderTimeNotIn(List<Integer> values) {
            addCriterion("order_time not in", values, "orderTime");
            return (Criteria) this;
        }

        public Criteria andOrderTimeBetween(Integer value1, Integer value2) {
            addCriterion("order_time between", value1, value2, "orderTime");
            return (Criteria) this;
        }

        public Criteria andOrderTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("order_time not between", value1, value2, "orderTime");
            return (Criteria) this;
        }

        public Criteria andCompletionTimeIsNull() {
            addCriterion("completion_time is null");
            return (Criteria) this;
        }

        public Criteria andCompletionTimeIsNotNull() {
            addCriterion("completion_time is not null");
            return (Criteria) this;
        }

        public Criteria andCompletionTimeEqualTo(Integer value) {
            addCriterion("completion_time =", value, "completionTime");
            return (Criteria) this;
        }

        public Criteria andCompletionTimeNotEqualTo(Integer value) {
            addCriterion("completion_time <>", value, "completionTime");
            return (Criteria) this;
        }

        public Criteria andCompletionTimeGreaterThan(Integer value) {
            addCriterion("completion_time >", value, "completionTime");
            return (Criteria) this;
        }

        public Criteria andCompletionTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("completion_time >=", value, "completionTime");
            return (Criteria) this;
        }

        public Criteria andCompletionTimeLessThan(Integer value) {
            addCriterion("completion_time <", value, "completionTime");
            return (Criteria) this;
        }

        public Criteria andCompletionTimeLessThanOrEqualTo(Integer value) {
            addCriterion("completion_time <=", value, "completionTime");
            return (Criteria) this;
        }

        public Criteria andCompletionTimeIn(List<Integer> values) {
            addCriterion("completion_time in", values, "completionTime");
            return (Criteria) this;
        }

        public Criteria andCompletionTimeNotIn(List<Integer> values) {
            addCriterion("completion_time not in", values, "completionTime");
            return (Criteria) this;
        }

        public Criteria andCompletionTimeBetween(Integer value1, Integer value2) {
            addCriterion("completion_time between", value1, value2, "completionTime");
            return (Criteria) this;
        }

        public Criteria andCompletionTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("completion_time not between", value1, value2, "completionTime");
            return (Criteria) this;
        }

        public Criteria andDeliveryStatusIsNull() {
            addCriterion("delivery_status is null");
            return (Criteria) this;
        }

        public Criteria andDeliveryStatusIsNotNull() {
            addCriterion("delivery_status is not null");
            return (Criteria) this;
        }

        public Criteria andDeliveryStatusEqualTo(Integer value) {
            addCriterion("delivery_status =", value, "deliveryStatus");
            return (Criteria) this;
        }

        public Criteria andDeliveryStatusNotEqualTo(Integer value) {
            addCriterion("delivery_status <>", value, "deliveryStatus");
            return (Criteria) this;
        }

        public Criteria andDeliveryStatusGreaterThan(Integer value) {
            addCriterion("delivery_status >", value, "deliveryStatus");
            return (Criteria) this;
        }

        public Criteria andDeliveryStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("delivery_status >=", value, "deliveryStatus");
            return (Criteria) this;
        }

        public Criteria andDeliveryStatusLessThan(Integer value) {
            addCriterion("delivery_status <", value, "deliveryStatus");
            return (Criteria) this;
        }

        public Criteria andDeliveryStatusLessThanOrEqualTo(Integer value) {
            addCriterion("delivery_status <=", value, "deliveryStatus");
            return (Criteria) this;
        }

        public Criteria andDeliveryStatusIn(List<Integer> values) {
            addCriterion("delivery_status in", values, "deliveryStatus");
            return (Criteria) this;
        }

        public Criteria andDeliveryStatusNotIn(List<Integer> values) {
            addCriterion("delivery_status not in", values, "deliveryStatus");
            return (Criteria) this;
        }

        public Criteria andDeliveryStatusBetween(Integer value1, Integer value2) {
            addCriterion("delivery_status between", value1, value2, "deliveryStatus");
            return (Criteria) this;
        }

        public Criteria andDeliveryStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("delivery_status not between", value1, value2, "deliveryStatus");
            return (Criteria) this;
        }

        public Criteria andReceivingInformationIsNull() {
            addCriterion("receiving_information is null");
            return (Criteria) this;
        }

        public Criteria andReceivingInformationIsNotNull() {
            addCriterion("receiving_information is not null");
            return (Criteria) this;
        }

        public Criteria andReceivingInformationEqualTo(String value) {
            addCriterion("receiving_information =", value, "receivingInformation");
            return (Criteria) this;
        }

        public Criteria andReceivingInformationNotEqualTo(String value) {
            addCriterion("receiving_information <>", value, "receivingInformation");
            return (Criteria) this;
        }

        public Criteria andReceivingInformationGreaterThan(String value) {
            addCriterion("receiving_information >", value, "receivingInformation");
            return (Criteria) this;
        }

        public Criteria andReceivingInformationGreaterThanOrEqualTo(String value) {
            addCriterion("receiving_information >=", value, "receivingInformation");
            return (Criteria) this;
        }

        public Criteria andReceivingInformationLessThan(String value) {
            addCriterion("receiving_information <", value, "receivingInformation");
            return (Criteria) this;
        }

        public Criteria andReceivingInformationLessThanOrEqualTo(String value) {
            addCriterion("receiving_information <=", value, "receivingInformation");
            return (Criteria) this;
        }

        public Criteria andReceivingInformationLike(String value) {
            addCriterion("receiving_information like", value, "receivingInformation");
            return (Criteria) this;
        }

        public Criteria andReceivingInformationNotLike(String value) {
            addCriterion("receiving_information not like", value, "receivingInformation");
            return (Criteria) this;
        }

        public Criteria andReceivingInformationIn(List<String> values) {
            addCriterion("receiving_information in", values, "receivingInformation");
            return (Criteria) this;
        }

        public Criteria andReceivingInformationNotIn(List<String> values) {
            addCriterion("receiving_information not in", values, "receivingInformation");
            return (Criteria) this;
        }

        public Criteria andReceivingInformationBetween(String value1, String value2) {
            addCriterion("receiving_information between", value1, value2, "receivingInformation");
            return (Criteria) this;
        }

        public Criteria andReceivingInformationNotBetween(String value1, String value2) {
            addCriterion("receiving_information not between", value1, value2, "receivingInformation");
            return (Criteria) this;
        }

        public Criteria andRechargeStateIsNull() {
            addCriterion("recharge_state is null");
            return (Criteria) this;
        }

        public Criteria andRechargeStateIsNotNull() {
            addCriterion("recharge_state is not null");
            return (Criteria) this;
        }

        public Criteria andRechargeStateEqualTo(String value) {
            addCriterion("recharge_state =", value, "rechargeState");
            return (Criteria) this;
        }

        public Criteria andRechargeStateNotEqualTo(String value) {
            addCriterion("recharge_state <>", value, "rechargeState");
            return (Criteria) this;
        }

        public Criteria andRechargeStateGreaterThan(String value) {
            addCriterion("recharge_state >", value, "rechargeState");
            return (Criteria) this;
        }

        public Criteria andRechargeStateGreaterThanOrEqualTo(String value) {
            addCriterion("recharge_state >=", value, "rechargeState");
            return (Criteria) this;
        }

        public Criteria andRechargeStateLessThan(String value) {
            addCriterion("recharge_state <", value, "rechargeState");
            return (Criteria) this;
        }

        public Criteria andRechargeStateLessThanOrEqualTo(String value) {
            addCriterion("recharge_state <=", value, "rechargeState");
            return (Criteria) this;
        }

        public Criteria andRechargeStateLike(String value) {
            addCriterion("recharge_state like", value, "rechargeState");
            return (Criteria) this;
        }

        public Criteria andRechargeStateNotLike(String value) {
            addCriterion("recharge_state not like", value, "rechargeState");
            return (Criteria) this;
        }

        public Criteria andRechargeStateIn(List<String> values) {
            addCriterion("recharge_state in", values, "rechargeState");
            return (Criteria) this;
        }

        public Criteria andRechargeStateNotIn(List<String> values) {
            addCriterion("recharge_state not in", values, "rechargeState");
            return (Criteria) this;
        }

        public Criteria andRechargeStateBetween(String value1, String value2) {
            addCriterion("recharge_state between", value1, value2, "rechargeState");
            return (Criteria) this;
        }

        public Criteria andRechargeStateNotBetween(String value1, String value2) {
            addCriterion("recharge_state not between", value1, value2, "rechargeState");
            return (Criteria) this;
        }

        public Criteria andProcessingStateIsNull() {
            addCriterion("processing_state is null");
            return (Criteria) this;
        }

        public Criteria andProcessingStateIsNotNull() {
            addCriterion("processing_state is not null");
            return (Criteria) this;
        }

        public Criteria andProcessingStateEqualTo(Integer value) {
            addCriterion("processing_state =", value, "processingState");
            return (Criteria) this;
        }

        public Criteria andProcessingStateNotEqualTo(Integer value) {
            addCriterion("processing_state <>", value, "processingState");
            return (Criteria) this;
        }

        public Criteria andProcessingStateGreaterThan(Integer value) {
            addCriterion("processing_state >", value, "processingState");
            return (Criteria) this;
        }

        public Criteria andProcessingStateGreaterThanOrEqualTo(Integer value) {
            addCriterion("processing_state >=", value, "processingState");
            return (Criteria) this;
        }

        public Criteria andProcessingStateLessThan(Integer value) {
            addCriterion("processing_state <", value, "processingState");
            return (Criteria) this;
        }

        public Criteria andProcessingStateLessThanOrEqualTo(Integer value) {
            addCriterion("processing_state <=", value, "processingState");
            return (Criteria) this;
        }

        public Criteria andProcessingStateIn(List<Integer> values) {
            addCriterion("processing_state in", values, "processingState");
            return (Criteria) this;
        }

        public Criteria andProcessingStateNotIn(List<Integer> values) {
            addCriterion("processing_state not in", values, "processingState");
            return (Criteria) this;
        }

        public Criteria andProcessingStateBetween(Integer value1, Integer value2) {
            addCriterion("processing_state between", value1, value2, "processingState");
            return (Criteria) this;
        }

        public Criteria andProcessingStateNotBetween(Integer value1, Integer value2) {
            addCriterion("processing_state not between", value1, value2, "processingState");
            return (Criteria) this;
        }

        public Criteria andCreateByIsNull() {
            addCriterion("create_by is null");
            return (Criteria) this;
        }

        public Criteria andCreateByIsNotNull() {
            addCriterion("create_by is not null");
            return (Criteria) this;
        }

        public Criteria andCreateByEqualTo(String value) {
            addCriterion("create_by =", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotEqualTo(String value) {
            addCriterion("create_by <>", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByGreaterThan(String value) {
            addCriterion("create_by >", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByGreaterThanOrEqualTo(String value) {
            addCriterion("create_by >=", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByLessThan(String value) {
            addCriterion("create_by <", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByLessThanOrEqualTo(String value) {
            addCriterion("create_by <=", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByLike(String value) {
            addCriterion("create_by like", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotLike(String value) {
            addCriterion("create_by not like", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByIn(List<String> values) {
            addCriterion("create_by in", values, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotIn(List<String> values) {
            addCriterion("create_by not in", values, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByBetween(String value1, String value2) {
            addCriterion("create_by between", value1, value2, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotBetween(String value1, String value2) {
            addCriterion("create_by not between", value1, value2, "createBy");
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

        public Criteria andUpdateByIsNull() {
            addCriterion("update_by is null");
            return (Criteria) this;
        }

        public Criteria andUpdateByIsNotNull() {
            addCriterion("update_by is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateByEqualTo(String value) {
            addCriterion("update_by =", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByNotEqualTo(String value) {
            addCriterion("update_by <>", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByGreaterThan(String value) {
            addCriterion("update_by >", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByGreaterThanOrEqualTo(String value) {
            addCriterion("update_by >=", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByLessThan(String value) {
            addCriterion("update_by <", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByLessThanOrEqualTo(String value) {
            addCriterion("update_by <=", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByLike(String value) {
            addCriterion("update_by like", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByNotLike(String value) {
            addCriterion("update_by not like", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByIn(List<String> values) {
            addCriterion("update_by in", values, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByNotIn(List<String> values) {
            addCriterion("update_by not in", values, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByBetween(String value1, String value2) {
            addCriterion("update_by between", value1, value2, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByNotBetween(String value1, String value2) {
            addCriterion("update_by not between", value1, value2, "updateBy");
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