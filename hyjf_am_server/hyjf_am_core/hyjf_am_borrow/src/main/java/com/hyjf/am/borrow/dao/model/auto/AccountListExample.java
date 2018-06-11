package com.hyjf.am.borrow.dao.model.auto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AccountListExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public AccountListExample() {
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

        public Criteria andAccedeOrderIdIsNull() {
            addCriterion("accede_order_id is null");
            return (Criteria) this;
        }

        public Criteria andAccedeOrderIdIsNotNull() {
            addCriterion("accede_order_id is not null");
            return (Criteria) this;
        }

        public Criteria andAccedeOrderIdEqualTo(String value) {
            addCriterion("accede_order_id =", value, "accedeOrderId");
            return (Criteria) this;
        }

        public Criteria andAccedeOrderIdNotEqualTo(String value) {
            addCriterion("accede_order_id <>", value, "accedeOrderId");
            return (Criteria) this;
        }

        public Criteria andAccedeOrderIdGreaterThan(String value) {
            addCriterion("accede_order_id >", value, "accedeOrderId");
            return (Criteria) this;
        }

        public Criteria andAccedeOrderIdGreaterThanOrEqualTo(String value) {
            addCriterion("accede_order_id >=", value, "accedeOrderId");
            return (Criteria) this;
        }

        public Criteria andAccedeOrderIdLessThan(String value) {
            addCriterion("accede_order_id <", value, "accedeOrderId");
            return (Criteria) this;
        }

        public Criteria andAccedeOrderIdLessThanOrEqualTo(String value) {
            addCriterion("accede_order_id <=", value, "accedeOrderId");
            return (Criteria) this;
        }

        public Criteria andAccedeOrderIdLike(String value) {
            addCriterion("accede_order_id like", value, "accedeOrderId");
            return (Criteria) this;
        }

        public Criteria andAccedeOrderIdNotLike(String value) {
            addCriterion("accede_order_id not like", value, "accedeOrderId");
            return (Criteria) this;
        }

        public Criteria andAccedeOrderIdIn(List<String> values) {
            addCriterion("accede_order_id in", values, "accedeOrderId");
            return (Criteria) this;
        }

        public Criteria andAccedeOrderIdNotIn(List<String> values) {
            addCriterion("accede_order_id not in", values, "accedeOrderId");
            return (Criteria) this;
        }

        public Criteria andAccedeOrderIdBetween(String value1, String value2) {
            addCriterion("accede_order_id between", value1, value2, "accedeOrderId");
            return (Criteria) this;
        }

        public Criteria andAccedeOrderIdNotBetween(String value1, String value2) {
            addCriterion("accede_order_id not between", value1, value2, "accedeOrderId");
            return (Criteria) this;
        }

        public Criteria andIsShowIsNull() {
            addCriterion("is_show is null");
            return (Criteria) this;
        }

        public Criteria andIsShowIsNotNull() {
            addCriterion("is_show is not null");
            return (Criteria) this;
        }

        public Criteria andIsShowEqualTo(Integer value) {
            addCriterion("is_show =", value, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowNotEqualTo(Integer value) {
            addCriterion("is_show <>", value, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowGreaterThan(Integer value) {
            addCriterion("is_show >", value, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_show >=", value, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowLessThan(Integer value) {
            addCriterion("is_show <", value, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowLessThanOrEqualTo(Integer value) {
            addCriterion("is_show <=", value, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowIn(List<Integer> values) {
            addCriterion("is_show in", values, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowNotIn(List<Integer> values) {
            addCriterion("is_show not in", values, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowBetween(Integer value1, Integer value2) {
            addCriterion("is_show between", value1, value2, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowNotBetween(Integer value1, Integer value2) {
            addCriterion("is_show not between", value1, value2, "isShow");
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

        public Criteria andAmountIsNull() {
            addCriterion("amount is null");
            return (Criteria) this;
        }

        public Criteria andAmountIsNotNull() {
            addCriterion("amount is not null");
            return (Criteria) this;
        }

        public Criteria andAmountEqualTo(BigDecimal value) {
            addCriterion("amount =", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotEqualTo(BigDecimal value) {
            addCriterion("amount <>", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThan(BigDecimal value) {
            addCriterion("amount >", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("amount >=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThan(BigDecimal value) {
            addCriterion("amount <", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("amount <=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountIn(List<BigDecimal> values) {
            addCriterion("amount in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotIn(List<BigDecimal> values) {
            addCriterion("amount not in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("amount between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("amount not between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("`type` is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("`type` is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(Integer value) {
            addCriterion("`type` =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Integer value) {
            addCriterion("`type` <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Integer value) {
            addCriterion("`type` >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("`type` >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Integer value) {
            addCriterion("`type` <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Integer value) {
            addCriterion("`type` <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Integer> values) {
            addCriterion("`type` in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Integer> values) {
            addCriterion("`type` not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Integer value1, Integer value2) {
            addCriterion("`type` between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("`type` not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTradeIsNull() {
            addCriterion("trade is null");
            return (Criteria) this;
        }

        public Criteria andTradeIsNotNull() {
            addCriterion("trade is not null");
            return (Criteria) this;
        }

        public Criteria andTradeEqualTo(String value) {
            addCriterion("trade =", value, "trade");
            return (Criteria) this;
        }

        public Criteria andTradeNotEqualTo(String value) {
            addCriterion("trade <>", value, "trade");
            return (Criteria) this;
        }

        public Criteria andTradeGreaterThan(String value) {
            addCriterion("trade >", value, "trade");
            return (Criteria) this;
        }

        public Criteria andTradeGreaterThanOrEqualTo(String value) {
            addCriterion("trade >=", value, "trade");
            return (Criteria) this;
        }

        public Criteria andTradeLessThan(String value) {
            addCriterion("trade <", value, "trade");
            return (Criteria) this;
        }

        public Criteria andTradeLessThanOrEqualTo(String value) {
            addCriterion("trade <=", value, "trade");
            return (Criteria) this;
        }

        public Criteria andTradeLike(String value) {
            addCriterion("trade like", value, "trade");
            return (Criteria) this;
        }

        public Criteria andTradeNotLike(String value) {
            addCriterion("trade not like", value, "trade");
            return (Criteria) this;
        }

        public Criteria andTradeIn(List<String> values) {
            addCriterion("trade in", values, "trade");
            return (Criteria) this;
        }

        public Criteria andTradeNotIn(List<String> values) {
            addCriterion("trade not in", values, "trade");
            return (Criteria) this;
        }

        public Criteria andTradeBetween(String value1, String value2) {
            addCriterion("trade between", value1, value2, "trade");
            return (Criteria) this;
        }

        public Criteria andTradeNotBetween(String value1, String value2) {
            addCriterion("trade not between", value1, value2, "trade");
            return (Criteria) this;
        }

        public Criteria andTradeCodeIsNull() {
            addCriterion("trade_code is null");
            return (Criteria) this;
        }

        public Criteria andTradeCodeIsNotNull() {
            addCriterion("trade_code is not null");
            return (Criteria) this;
        }

        public Criteria andTradeCodeEqualTo(String value) {
            addCriterion("trade_code =", value, "tradeCode");
            return (Criteria) this;
        }

        public Criteria andTradeCodeNotEqualTo(String value) {
            addCriterion("trade_code <>", value, "tradeCode");
            return (Criteria) this;
        }

        public Criteria andTradeCodeGreaterThan(String value) {
            addCriterion("trade_code >", value, "tradeCode");
            return (Criteria) this;
        }

        public Criteria andTradeCodeGreaterThanOrEqualTo(String value) {
            addCriterion("trade_code >=", value, "tradeCode");
            return (Criteria) this;
        }

        public Criteria andTradeCodeLessThan(String value) {
            addCriterion("trade_code <", value, "tradeCode");
            return (Criteria) this;
        }

        public Criteria andTradeCodeLessThanOrEqualTo(String value) {
            addCriterion("trade_code <=", value, "tradeCode");
            return (Criteria) this;
        }

        public Criteria andTradeCodeLike(String value) {
            addCriterion("trade_code like", value, "tradeCode");
            return (Criteria) this;
        }

        public Criteria andTradeCodeNotLike(String value) {
            addCriterion("trade_code not like", value, "tradeCode");
            return (Criteria) this;
        }

        public Criteria andTradeCodeIn(List<String> values) {
            addCriterion("trade_code in", values, "tradeCode");
            return (Criteria) this;
        }

        public Criteria andTradeCodeNotIn(List<String> values) {
            addCriterion("trade_code not in", values, "tradeCode");
            return (Criteria) this;
        }

        public Criteria andTradeCodeBetween(String value1, String value2) {
            addCriterion("trade_code between", value1, value2, "tradeCode");
            return (Criteria) this;
        }

        public Criteria andTradeCodeNotBetween(String value1, String value2) {
            addCriterion("trade_code not between", value1, value2, "tradeCode");
            return (Criteria) this;
        }

        public Criteria andTotalIsNull() {
            addCriterion("total is null");
            return (Criteria) this;
        }

        public Criteria andTotalIsNotNull() {
            addCriterion("total is not null");
            return (Criteria) this;
        }

        public Criteria andTotalEqualTo(BigDecimal value) {
            addCriterion("total =", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalNotEqualTo(BigDecimal value) {
            addCriterion("total <>", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalGreaterThan(BigDecimal value) {
            addCriterion("total >", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("total >=", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalLessThan(BigDecimal value) {
            addCriterion("total <", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalLessThanOrEqualTo(BigDecimal value) {
            addCriterion("total <=", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalIn(List<BigDecimal> values) {
            addCriterion("total in", values, "total");
            return (Criteria) this;
        }

        public Criteria andTotalNotIn(List<BigDecimal> values) {
            addCriterion("total not in", values, "total");
            return (Criteria) this;
        }

        public Criteria andTotalBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("total between", value1, value2, "total");
            return (Criteria) this;
        }

        public Criteria andTotalNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("total not between", value1, value2, "total");
            return (Criteria) this;
        }

        public Criteria andBalanceIsNull() {
            addCriterion("balance is null");
            return (Criteria) this;
        }

        public Criteria andBalanceIsNotNull() {
            addCriterion("balance is not null");
            return (Criteria) this;
        }

        public Criteria andBalanceEqualTo(BigDecimal value) {
            addCriterion("balance =", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceNotEqualTo(BigDecimal value) {
            addCriterion("balance <>", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceGreaterThan(BigDecimal value) {
            addCriterion("balance >", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("balance >=", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceLessThan(BigDecimal value) {
            addCriterion("balance <", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("balance <=", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceIn(List<BigDecimal> values) {
            addCriterion("balance in", values, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceNotIn(List<BigDecimal> values) {
            addCriterion("balance not in", values, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("balance between", value1, value2, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("balance not between", value1, value2, "balance");
            return (Criteria) this;
        }

        public Criteria andFrostIsNull() {
            addCriterion("frost is null");
            return (Criteria) this;
        }

        public Criteria andFrostIsNotNull() {
            addCriterion("frost is not null");
            return (Criteria) this;
        }

        public Criteria andFrostEqualTo(BigDecimal value) {
            addCriterion("frost =", value, "frost");
            return (Criteria) this;
        }

        public Criteria andFrostNotEqualTo(BigDecimal value) {
            addCriterion("frost <>", value, "frost");
            return (Criteria) this;
        }

        public Criteria andFrostGreaterThan(BigDecimal value) {
            addCriterion("frost >", value, "frost");
            return (Criteria) this;
        }

        public Criteria andFrostGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("frost >=", value, "frost");
            return (Criteria) this;
        }

        public Criteria andFrostLessThan(BigDecimal value) {
            addCriterion("frost <", value, "frost");
            return (Criteria) this;
        }

        public Criteria andFrostLessThanOrEqualTo(BigDecimal value) {
            addCriterion("frost <=", value, "frost");
            return (Criteria) this;
        }

        public Criteria andFrostIn(List<BigDecimal> values) {
            addCriterion("frost in", values, "frost");
            return (Criteria) this;
        }

        public Criteria andFrostNotIn(List<BigDecimal> values) {
            addCriterion("frost not in", values, "frost");
            return (Criteria) this;
        }

        public Criteria andFrostBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("frost between", value1, value2, "frost");
            return (Criteria) this;
        }

        public Criteria andFrostNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("frost not between", value1, value2, "frost");
            return (Criteria) this;
        }

        public Criteria andPlanFrostIsNull() {
            addCriterion("plan_frost is null");
            return (Criteria) this;
        }

        public Criteria andPlanFrostIsNotNull() {
            addCriterion("plan_frost is not null");
            return (Criteria) this;
        }

        public Criteria andPlanFrostEqualTo(BigDecimal value) {
            addCriterion("plan_frost =", value, "planFrost");
            return (Criteria) this;
        }

        public Criteria andPlanFrostNotEqualTo(BigDecimal value) {
            addCriterion("plan_frost <>", value, "planFrost");
            return (Criteria) this;
        }

        public Criteria andPlanFrostGreaterThan(BigDecimal value) {
            addCriterion("plan_frost >", value, "planFrost");
            return (Criteria) this;
        }

        public Criteria andPlanFrostGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("plan_frost >=", value, "planFrost");
            return (Criteria) this;
        }

        public Criteria andPlanFrostLessThan(BigDecimal value) {
            addCriterion("plan_frost <", value, "planFrost");
            return (Criteria) this;
        }

        public Criteria andPlanFrostLessThanOrEqualTo(BigDecimal value) {
            addCriterion("plan_frost <=", value, "planFrost");
            return (Criteria) this;
        }

        public Criteria andPlanFrostIn(List<BigDecimal> values) {
            addCriterion("plan_frost in", values, "planFrost");
            return (Criteria) this;
        }

        public Criteria andPlanFrostNotIn(List<BigDecimal> values) {
            addCriterion("plan_frost not in", values, "planFrost");
            return (Criteria) this;
        }

        public Criteria andPlanFrostBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("plan_frost between", value1, value2, "planFrost");
            return (Criteria) this;
        }

        public Criteria andPlanFrostNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("plan_frost not between", value1, value2, "planFrost");
            return (Criteria) this;
        }

        public Criteria andAwaitIsNull() {
            addCriterion("await is null");
            return (Criteria) this;
        }

        public Criteria andAwaitIsNotNull() {
            addCriterion("await is not null");
            return (Criteria) this;
        }

        public Criteria andAwaitEqualTo(BigDecimal value) {
            addCriterion("await =", value, "await");
            return (Criteria) this;
        }

        public Criteria andAwaitNotEqualTo(BigDecimal value) {
            addCriterion("await <>", value, "await");
            return (Criteria) this;
        }

        public Criteria andAwaitGreaterThan(BigDecimal value) {
            addCriterion("await >", value, "await");
            return (Criteria) this;
        }

        public Criteria andAwaitGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("await >=", value, "await");
            return (Criteria) this;
        }

        public Criteria andAwaitLessThan(BigDecimal value) {
            addCriterion("await <", value, "await");
            return (Criteria) this;
        }

        public Criteria andAwaitLessThanOrEqualTo(BigDecimal value) {
            addCriterion("await <=", value, "await");
            return (Criteria) this;
        }

        public Criteria andAwaitIn(List<BigDecimal> values) {
            addCriterion("await in", values, "await");
            return (Criteria) this;
        }

        public Criteria andAwaitNotIn(List<BigDecimal> values) {
            addCriterion("await not in", values, "await");
            return (Criteria) this;
        }

        public Criteria andAwaitBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("await between", value1, value2, "await");
            return (Criteria) this;
        }

        public Criteria andAwaitNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("await not between", value1, value2, "await");
            return (Criteria) this;
        }

        public Criteria andRepayIsNull() {
            addCriterion("repay is null");
            return (Criteria) this;
        }

        public Criteria andRepayIsNotNull() {
            addCriterion("repay is not null");
            return (Criteria) this;
        }

        public Criteria andRepayEqualTo(BigDecimal value) {
            addCriterion("repay =", value, "repay");
            return (Criteria) this;
        }

        public Criteria andRepayNotEqualTo(BigDecimal value) {
            addCriterion("repay <>", value, "repay");
            return (Criteria) this;
        }

        public Criteria andRepayGreaterThan(BigDecimal value) {
            addCriterion("repay >", value, "repay");
            return (Criteria) this;
        }

        public Criteria andRepayGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("repay >=", value, "repay");
            return (Criteria) this;
        }

        public Criteria andRepayLessThan(BigDecimal value) {
            addCriterion("repay <", value, "repay");
            return (Criteria) this;
        }

        public Criteria andRepayLessThanOrEqualTo(BigDecimal value) {
            addCriterion("repay <=", value, "repay");
            return (Criteria) this;
        }

        public Criteria andRepayIn(List<BigDecimal> values) {
            addCriterion("repay in", values, "repay");
            return (Criteria) this;
        }

        public Criteria andRepayNotIn(List<BigDecimal> values) {
            addCriterion("repay not in", values, "repay");
            return (Criteria) this;
        }

        public Criteria andRepayBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay between", value1, value2, "repay");
            return (Criteria) this;
        }

        public Criteria andRepayNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("repay not between", value1, value2, "repay");
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

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Integer value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Integer value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Integer value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Integer value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Integer value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Integer> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Integer> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Integer value1, Integer value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andOperatorIsNull() {
            addCriterion("`operator` is null");
            return (Criteria) this;
        }

        public Criteria andOperatorIsNotNull() {
            addCriterion("`operator` is not null");
            return (Criteria) this;
        }

        public Criteria andOperatorEqualTo(String value) {
            addCriterion("`operator` =", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorNotEqualTo(String value) {
            addCriterion("`operator` <>", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorGreaterThan(String value) {
            addCriterion("`operator` >", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorGreaterThanOrEqualTo(String value) {
            addCriterion("`operator` >=", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorLessThan(String value) {
            addCriterion("`operator` <", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorLessThanOrEqualTo(String value) {
            addCriterion("`operator` <=", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorLike(String value) {
            addCriterion("`operator` like", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorNotLike(String value) {
            addCriterion("`operator` not like", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorIn(List<String> values) {
            addCriterion("`operator` in", values, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorNotIn(List<String> values) {
            addCriterion("`operator` not in", values, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorBetween(String value1, String value2) {
            addCriterion("`operator` between", value1, value2, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorNotBetween(String value1, String value2) {
            addCriterion("`operator` not between", value1, value2, "operator");
            return (Criteria) this;
        }

        public Criteria andIpIsNull() {
            addCriterion("ip is null");
            return (Criteria) this;
        }

        public Criteria andIpIsNotNull() {
            addCriterion("ip is not null");
            return (Criteria) this;
        }

        public Criteria andIpEqualTo(String value) {
            addCriterion("ip =", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotEqualTo(String value) {
            addCriterion("ip <>", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpGreaterThan(String value) {
            addCriterion("ip >", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpGreaterThanOrEqualTo(String value) {
            addCriterion("ip >=", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLessThan(String value) {
            addCriterion("ip <", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLessThanOrEqualTo(String value) {
            addCriterion("ip <=", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLike(String value) {
            addCriterion("ip like", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotLike(String value) {
            addCriterion("ip not like", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpIn(List<String> values) {
            addCriterion("ip in", values, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotIn(List<String> values) {
            addCriterion("ip not in", values, "ip");
            return (Criteria) this;
        }

        public Criteria andIpBetween(String value1, String value2) {
            addCriterion("ip between", value1, value2, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotBetween(String value1, String value2) {
            addCriterion("ip not between", value1, value2, "ip");
            return (Criteria) this;
        }

        public Criteria andIsUpdateIsNull() {
            addCriterion("is_update is null");
            return (Criteria) this;
        }

        public Criteria andIsUpdateIsNotNull() {
            addCriterion("is_update is not null");
            return (Criteria) this;
        }

        public Criteria andIsUpdateEqualTo(Integer value) {
            addCriterion("is_update =", value, "isUpdate");
            return (Criteria) this;
        }

        public Criteria andIsUpdateNotEqualTo(Integer value) {
            addCriterion("is_update <>", value, "isUpdate");
            return (Criteria) this;
        }

        public Criteria andIsUpdateGreaterThan(Integer value) {
            addCriterion("is_update >", value, "isUpdate");
            return (Criteria) this;
        }

        public Criteria andIsUpdateGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_update >=", value, "isUpdate");
            return (Criteria) this;
        }

        public Criteria andIsUpdateLessThan(Integer value) {
            addCriterion("is_update <", value, "isUpdate");
            return (Criteria) this;
        }

        public Criteria andIsUpdateLessThanOrEqualTo(Integer value) {
            addCriterion("is_update <=", value, "isUpdate");
            return (Criteria) this;
        }

        public Criteria andIsUpdateIn(List<Integer> values) {
            addCriterion("is_update in", values, "isUpdate");
            return (Criteria) this;
        }

        public Criteria andIsUpdateNotIn(List<Integer> values) {
            addCriterion("is_update not in", values, "isUpdate");
            return (Criteria) this;
        }

        public Criteria andIsUpdateBetween(Integer value1, Integer value2) {
            addCriterion("is_update between", value1, value2, "isUpdate");
            return (Criteria) this;
        }

        public Criteria andIsUpdateNotBetween(Integer value1, Integer value2) {
            addCriterion("is_update not between", value1, value2, "isUpdate");
            return (Criteria) this;
        }

        public Criteria andBaseUpdateIsNull() {
            addCriterion("base_update is null");
            return (Criteria) this;
        }

        public Criteria andBaseUpdateIsNotNull() {
            addCriterion("base_update is not null");
            return (Criteria) this;
        }

        public Criteria andBaseUpdateEqualTo(Integer value) {
            addCriterion("base_update =", value, "baseUpdate");
            return (Criteria) this;
        }

        public Criteria andBaseUpdateNotEqualTo(Integer value) {
            addCriterion("base_update <>", value, "baseUpdate");
            return (Criteria) this;
        }

        public Criteria andBaseUpdateGreaterThan(Integer value) {
            addCriterion("base_update >", value, "baseUpdate");
            return (Criteria) this;
        }

        public Criteria andBaseUpdateGreaterThanOrEqualTo(Integer value) {
            addCriterion("base_update >=", value, "baseUpdate");
            return (Criteria) this;
        }

        public Criteria andBaseUpdateLessThan(Integer value) {
            addCriterion("base_update <", value, "baseUpdate");
            return (Criteria) this;
        }

        public Criteria andBaseUpdateLessThanOrEqualTo(Integer value) {
            addCriterion("base_update <=", value, "baseUpdate");
            return (Criteria) this;
        }

        public Criteria andBaseUpdateIn(List<Integer> values) {
            addCriterion("base_update in", values, "baseUpdate");
            return (Criteria) this;
        }

        public Criteria andBaseUpdateNotIn(List<Integer> values) {
            addCriterion("base_update not in", values, "baseUpdate");
            return (Criteria) this;
        }

        public Criteria andBaseUpdateBetween(Integer value1, Integer value2) {
            addCriterion("base_update between", value1, value2, "baseUpdate");
            return (Criteria) this;
        }

        public Criteria andBaseUpdateNotBetween(Integer value1, Integer value2) {
            addCriterion("base_update not between", value1, value2, "baseUpdate");
            return (Criteria) this;
        }

        public Criteria andInterestIsNull() {
            addCriterion("interest is null");
            return (Criteria) this;
        }

        public Criteria andInterestIsNotNull() {
            addCriterion("interest is not null");
            return (Criteria) this;
        }

        public Criteria andInterestEqualTo(BigDecimal value) {
            addCriterion("interest =", value, "interest");
            return (Criteria) this;
        }

        public Criteria andInterestNotEqualTo(BigDecimal value) {
            addCriterion("interest <>", value, "interest");
            return (Criteria) this;
        }

        public Criteria andInterestGreaterThan(BigDecimal value) {
            addCriterion("interest >", value, "interest");
            return (Criteria) this;
        }

        public Criteria andInterestGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("interest >=", value, "interest");
            return (Criteria) this;
        }

        public Criteria andInterestLessThan(BigDecimal value) {
            addCriterion("interest <", value, "interest");
            return (Criteria) this;
        }

        public Criteria andInterestLessThanOrEqualTo(BigDecimal value) {
            addCriterion("interest <=", value, "interest");
            return (Criteria) this;
        }

        public Criteria andInterestIn(List<BigDecimal> values) {
            addCriterion("interest in", values, "interest");
            return (Criteria) this;
        }

        public Criteria andInterestNotIn(List<BigDecimal> values) {
            addCriterion("interest not in", values, "interest");
            return (Criteria) this;
        }

        public Criteria andInterestBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("interest between", value1, value2, "interest");
            return (Criteria) this;
        }

        public Criteria andInterestNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("interest not between", value1, value2, "interest");
            return (Criteria) this;
        }

        public Criteria andWebIsNull() {
            addCriterion("web is null");
            return (Criteria) this;
        }

        public Criteria andWebIsNotNull() {
            addCriterion("web is not null");
            return (Criteria) this;
        }

        public Criteria andWebEqualTo(Integer value) {
            addCriterion("web =", value, "web");
            return (Criteria) this;
        }

        public Criteria andWebNotEqualTo(Integer value) {
            addCriterion("web <>", value, "web");
            return (Criteria) this;
        }

        public Criteria andWebGreaterThan(Integer value) {
            addCriterion("web >", value, "web");
            return (Criteria) this;
        }

        public Criteria andWebGreaterThanOrEqualTo(Integer value) {
            addCriterion("web >=", value, "web");
            return (Criteria) this;
        }

        public Criteria andWebLessThan(Integer value) {
            addCriterion("web <", value, "web");
            return (Criteria) this;
        }

        public Criteria andWebLessThanOrEqualTo(Integer value) {
            addCriterion("web <=", value, "web");
            return (Criteria) this;
        }

        public Criteria andWebIn(List<Integer> values) {
            addCriterion("web in", values, "web");
            return (Criteria) this;
        }

        public Criteria andWebNotIn(List<Integer> values) {
            addCriterion("web not in", values, "web");
            return (Criteria) this;
        }

        public Criteria andWebBetween(Integer value1, Integer value2) {
            addCriterion("web between", value1, value2, "web");
            return (Criteria) this;
        }

        public Criteria andWebNotBetween(Integer value1, Integer value2) {
            addCriterion("web not between", value1, value2, "web");
            return (Criteria) this;
        }

        public Criteria andPlanBalanceIsNull() {
            addCriterion("plan_balance is null");
            return (Criteria) this;
        }

        public Criteria andPlanBalanceIsNotNull() {
            addCriterion("plan_balance is not null");
            return (Criteria) this;
        }

        public Criteria andPlanBalanceEqualTo(BigDecimal value) {
            addCriterion("plan_balance =", value, "planBalance");
            return (Criteria) this;
        }

        public Criteria andPlanBalanceNotEqualTo(BigDecimal value) {
            addCriterion("plan_balance <>", value, "planBalance");
            return (Criteria) this;
        }

        public Criteria andPlanBalanceGreaterThan(BigDecimal value) {
            addCriterion("plan_balance >", value, "planBalance");
            return (Criteria) this;
        }

        public Criteria andPlanBalanceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("plan_balance >=", value, "planBalance");
            return (Criteria) this;
        }

        public Criteria andPlanBalanceLessThan(BigDecimal value) {
            addCriterion("plan_balance <", value, "planBalance");
            return (Criteria) this;
        }

        public Criteria andPlanBalanceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("plan_balance <=", value, "planBalance");
            return (Criteria) this;
        }

        public Criteria andPlanBalanceIn(List<BigDecimal> values) {
            addCriterion("plan_balance in", values, "planBalance");
            return (Criteria) this;
        }

        public Criteria andPlanBalanceNotIn(List<BigDecimal> values) {
            addCriterion("plan_balance not in", values, "planBalance");
            return (Criteria) this;
        }

        public Criteria andPlanBalanceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("plan_balance between", value1, value2, "planBalance");
            return (Criteria) this;
        }

        public Criteria andPlanBalanceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("plan_balance not between", value1, value2, "planBalance");
            return (Criteria) this;
        }

        public Criteria andIsBankIsNull() {
            addCriterion("is_bank is null");
            return (Criteria) this;
        }

        public Criteria andIsBankIsNotNull() {
            addCriterion("is_bank is not null");
            return (Criteria) this;
        }

        public Criteria andIsBankEqualTo(Integer value) {
            addCriterion("is_bank =", value, "isBank");
            return (Criteria) this;
        }

        public Criteria andIsBankNotEqualTo(Integer value) {
            addCriterion("is_bank <>", value, "isBank");
            return (Criteria) this;
        }

        public Criteria andIsBankGreaterThan(Integer value) {
            addCriterion("is_bank >", value, "isBank");
            return (Criteria) this;
        }

        public Criteria andIsBankGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_bank >=", value, "isBank");
            return (Criteria) this;
        }

        public Criteria andIsBankLessThan(Integer value) {
            addCriterion("is_bank <", value, "isBank");
            return (Criteria) this;
        }

        public Criteria andIsBankLessThanOrEqualTo(Integer value) {
            addCriterion("is_bank <=", value, "isBank");
            return (Criteria) this;
        }

        public Criteria andIsBankIn(List<Integer> values) {
            addCriterion("is_bank in", values, "isBank");
            return (Criteria) this;
        }

        public Criteria andIsBankNotIn(List<Integer> values) {
            addCriterion("is_bank not in", values, "isBank");
            return (Criteria) this;
        }

        public Criteria andIsBankBetween(Integer value1, Integer value2) {
            addCriterion("is_bank between", value1, value2, "isBank");
            return (Criteria) this;
        }

        public Criteria andIsBankNotBetween(Integer value1, Integer value2) {
            addCriterion("is_bank not between", value1, value2, "isBank");
            return (Criteria) this;
        }

        public Criteria andAccountIdIsNull() {
            addCriterion("account_id is null");
            return (Criteria) this;
        }

        public Criteria andAccountIdIsNotNull() {
            addCriterion("account_id is not null");
            return (Criteria) this;
        }

        public Criteria andAccountIdEqualTo(String value) {
            addCriterion("account_id =", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdNotEqualTo(String value) {
            addCriterion("account_id <>", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdGreaterThan(String value) {
            addCriterion("account_id >", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdGreaterThanOrEqualTo(String value) {
            addCriterion("account_id >=", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdLessThan(String value) {
            addCriterion("account_id <", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdLessThanOrEqualTo(String value) {
            addCriterion("account_id <=", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdLike(String value) {
            addCriterion("account_id like", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdNotLike(String value) {
            addCriterion("account_id not like", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdIn(List<String> values) {
            addCriterion("account_id in", values, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdNotIn(List<String> values) {
            addCriterion("account_id not in", values, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdBetween(String value1, String value2) {
            addCriterion("account_id between", value1, value2, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdNotBetween(String value1, String value2) {
            addCriterion("account_id not between", value1, value2, "accountId");
            return (Criteria) this;
        }

        public Criteria andTxDateIsNull() {
            addCriterion("tx_date is null");
            return (Criteria) this;
        }

        public Criteria andTxDateIsNotNull() {
            addCriterion("tx_date is not null");
            return (Criteria) this;
        }

        public Criteria andTxDateEqualTo(Integer value) {
            addCriterion("tx_date =", value, "txDate");
            return (Criteria) this;
        }

        public Criteria andTxDateNotEqualTo(Integer value) {
            addCriterion("tx_date <>", value, "txDate");
            return (Criteria) this;
        }

        public Criteria andTxDateGreaterThan(Integer value) {
            addCriterion("tx_date >", value, "txDate");
            return (Criteria) this;
        }

        public Criteria andTxDateGreaterThanOrEqualTo(Integer value) {
            addCriterion("tx_date >=", value, "txDate");
            return (Criteria) this;
        }

        public Criteria andTxDateLessThan(Integer value) {
            addCriterion("tx_date <", value, "txDate");
            return (Criteria) this;
        }

        public Criteria andTxDateLessThanOrEqualTo(Integer value) {
            addCriterion("tx_date <=", value, "txDate");
            return (Criteria) this;
        }

        public Criteria andTxDateIn(List<Integer> values) {
            addCriterion("tx_date in", values, "txDate");
            return (Criteria) this;
        }

        public Criteria andTxDateNotIn(List<Integer> values) {
            addCriterion("tx_date not in", values, "txDate");
            return (Criteria) this;
        }

        public Criteria andTxDateBetween(Integer value1, Integer value2) {
            addCriterion("tx_date between", value1, value2, "txDate");
            return (Criteria) this;
        }

        public Criteria andTxDateNotBetween(Integer value1, Integer value2) {
            addCriterion("tx_date not between", value1, value2, "txDate");
            return (Criteria) this;
        }

        public Criteria andTxTimeIsNull() {
            addCriterion("tx_time is null");
            return (Criteria) this;
        }

        public Criteria andTxTimeIsNotNull() {
            addCriterion("tx_time is not null");
            return (Criteria) this;
        }

        public Criteria andTxTimeEqualTo(Integer value) {
            addCriterion("tx_time =", value, "txTime");
            return (Criteria) this;
        }

        public Criteria andTxTimeNotEqualTo(Integer value) {
            addCriterion("tx_time <>", value, "txTime");
            return (Criteria) this;
        }

        public Criteria andTxTimeGreaterThan(Integer value) {
            addCriterion("tx_time >", value, "txTime");
            return (Criteria) this;
        }

        public Criteria andTxTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("tx_time >=", value, "txTime");
            return (Criteria) this;
        }

        public Criteria andTxTimeLessThan(Integer value) {
            addCriterion("tx_time <", value, "txTime");
            return (Criteria) this;
        }

        public Criteria andTxTimeLessThanOrEqualTo(Integer value) {
            addCriterion("tx_time <=", value, "txTime");
            return (Criteria) this;
        }

        public Criteria andTxTimeIn(List<Integer> values) {
            addCriterion("tx_time in", values, "txTime");
            return (Criteria) this;
        }

        public Criteria andTxTimeNotIn(List<Integer> values) {
            addCriterion("tx_time not in", values, "txTime");
            return (Criteria) this;
        }

        public Criteria andTxTimeBetween(Integer value1, Integer value2) {
            addCriterion("tx_time between", value1, value2, "txTime");
            return (Criteria) this;
        }

        public Criteria andTxTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("tx_time not between", value1, value2, "txTime");
            return (Criteria) this;
        }

        public Criteria andSeqNoIsNull() {
            addCriterion("seq_no is null");
            return (Criteria) this;
        }

        public Criteria andSeqNoIsNotNull() {
            addCriterion("seq_no is not null");
            return (Criteria) this;
        }

        public Criteria andSeqNoEqualTo(String value) {
            addCriterion("seq_no =", value, "seqNo");
            return (Criteria) this;
        }

        public Criteria andSeqNoNotEqualTo(String value) {
            addCriterion("seq_no <>", value, "seqNo");
            return (Criteria) this;
        }

        public Criteria andSeqNoGreaterThan(String value) {
            addCriterion("seq_no >", value, "seqNo");
            return (Criteria) this;
        }

        public Criteria andSeqNoGreaterThanOrEqualTo(String value) {
            addCriterion("seq_no >=", value, "seqNo");
            return (Criteria) this;
        }

        public Criteria andSeqNoLessThan(String value) {
            addCriterion("seq_no <", value, "seqNo");
            return (Criteria) this;
        }

        public Criteria andSeqNoLessThanOrEqualTo(String value) {
            addCriterion("seq_no <=", value, "seqNo");
            return (Criteria) this;
        }

        public Criteria andSeqNoLike(String value) {
            addCriterion("seq_no like", value, "seqNo");
            return (Criteria) this;
        }

        public Criteria andSeqNoNotLike(String value) {
            addCriterion("seq_no not like", value, "seqNo");
            return (Criteria) this;
        }

        public Criteria andSeqNoIn(List<String> values) {
            addCriterion("seq_no in", values, "seqNo");
            return (Criteria) this;
        }

        public Criteria andSeqNoNotIn(List<String> values) {
            addCriterion("seq_no not in", values, "seqNo");
            return (Criteria) this;
        }

        public Criteria andSeqNoBetween(String value1, String value2) {
            addCriterion("seq_no between", value1, value2, "seqNo");
            return (Criteria) this;
        }

        public Criteria andSeqNoNotBetween(String value1, String value2) {
            addCriterion("seq_no not between", value1, value2, "seqNo");
            return (Criteria) this;
        }

        public Criteria andBankSeqNoIsNull() {
            addCriterion("bank_seq_no is null");
            return (Criteria) this;
        }

        public Criteria andBankSeqNoIsNotNull() {
            addCriterion("bank_seq_no is not null");
            return (Criteria) this;
        }

        public Criteria andBankSeqNoEqualTo(String value) {
            addCriterion("bank_seq_no =", value, "bankSeqNo");
            return (Criteria) this;
        }

        public Criteria andBankSeqNoNotEqualTo(String value) {
            addCriterion("bank_seq_no <>", value, "bankSeqNo");
            return (Criteria) this;
        }

        public Criteria andBankSeqNoGreaterThan(String value) {
            addCriterion("bank_seq_no >", value, "bankSeqNo");
            return (Criteria) this;
        }

        public Criteria andBankSeqNoGreaterThanOrEqualTo(String value) {
            addCriterion("bank_seq_no >=", value, "bankSeqNo");
            return (Criteria) this;
        }

        public Criteria andBankSeqNoLessThan(String value) {
            addCriterion("bank_seq_no <", value, "bankSeqNo");
            return (Criteria) this;
        }

        public Criteria andBankSeqNoLessThanOrEqualTo(String value) {
            addCriterion("bank_seq_no <=", value, "bankSeqNo");
            return (Criteria) this;
        }

        public Criteria andBankSeqNoLike(String value) {
            addCriterion("bank_seq_no like", value, "bankSeqNo");
            return (Criteria) this;
        }

        public Criteria andBankSeqNoNotLike(String value) {
            addCriterion("bank_seq_no not like", value, "bankSeqNo");
            return (Criteria) this;
        }

        public Criteria andBankSeqNoIn(List<String> values) {
            addCriterion("bank_seq_no in", values, "bankSeqNo");
            return (Criteria) this;
        }

        public Criteria andBankSeqNoNotIn(List<String> values) {
            addCriterion("bank_seq_no not in", values, "bankSeqNo");
            return (Criteria) this;
        }

        public Criteria andBankSeqNoBetween(String value1, String value2) {
            addCriterion("bank_seq_no between", value1, value2, "bankSeqNo");
            return (Criteria) this;
        }

        public Criteria andBankSeqNoNotBetween(String value1, String value2) {
            addCriterion("bank_seq_no not between", value1, value2, "bankSeqNo");
            return (Criteria) this;
        }

        public Criteria andCheckStatusIsNull() {
            addCriterion("check_status is null");
            return (Criteria) this;
        }

        public Criteria andCheckStatusIsNotNull() {
            addCriterion("check_status is not null");
            return (Criteria) this;
        }

        public Criteria andCheckStatusEqualTo(Integer value) {
            addCriterion("check_status =", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusNotEqualTo(Integer value) {
            addCriterion("check_status <>", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusGreaterThan(Integer value) {
            addCriterion("check_status >", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("check_status >=", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusLessThan(Integer value) {
            addCriterion("check_status <", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusLessThanOrEqualTo(Integer value) {
            addCriterion("check_status <=", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusIn(List<Integer> values) {
            addCriterion("check_status in", values, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusNotIn(List<Integer> values) {
            addCriterion("check_status not in", values, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusBetween(Integer value1, Integer value2) {
            addCriterion("check_status between", value1, value2, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("check_status not between", value1, value2, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andTradeStatusIsNull() {
            addCriterion("trade_status is null");
            return (Criteria) this;
        }

        public Criteria andTradeStatusIsNotNull() {
            addCriterion("trade_status is not null");
            return (Criteria) this;
        }

        public Criteria andTradeStatusEqualTo(Integer value) {
            addCriterion("trade_status =", value, "tradeStatus");
            return (Criteria) this;
        }

        public Criteria andTradeStatusNotEqualTo(Integer value) {
            addCriterion("trade_status <>", value, "tradeStatus");
            return (Criteria) this;
        }

        public Criteria andTradeStatusGreaterThan(Integer value) {
            addCriterion("trade_status >", value, "tradeStatus");
            return (Criteria) this;
        }

        public Criteria andTradeStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("trade_status >=", value, "tradeStatus");
            return (Criteria) this;
        }

        public Criteria andTradeStatusLessThan(Integer value) {
            addCriterion("trade_status <", value, "tradeStatus");
            return (Criteria) this;
        }

        public Criteria andTradeStatusLessThanOrEqualTo(Integer value) {
            addCriterion("trade_status <=", value, "tradeStatus");
            return (Criteria) this;
        }

        public Criteria andTradeStatusIn(List<Integer> values) {
            addCriterion("trade_status in", values, "tradeStatus");
            return (Criteria) this;
        }

        public Criteria andTradeStatusNotIn(List<Integer> values) {
            addCriterion("trade_status not in", values, "tradeStatus");
            return (Criteria) this;
        }

        public Criteria andTradeStatusBetween(Integer value1, Integer value2) {
            addCriterion("trade_status between", value1, value2, "tradeStatus");
            return (Criteria) this;
        }

        public Criteria andTradeStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("trade_status not between", value1, value2, "tradeStatus");
            return (Criteria) this;
        }

        public Criteria andBankTotalIsNull() {
            addCriterion("bank_total is null");
            return (Criteria) this;
        }

        public Criteria andBankTotalIsNotNull() {
            addCriterion("bank_total is not null");
            return (Criteria) this;
        }

        public Criteria andBankTotalEqualTo(BigDecimal value) {
            addCriterion("bank_total =", value, "bankTotal");
            return (Criteria) this;
        }

        public Criteria andBankTotalNotEqualTo(BigDecimal value) {
            addCriterion("bank_total <>", value, "bankTotal");
            return (Criteria) this;
        }

        public Criteria andBankTotalGreaterThan(BigDecimal value) {
            addCriterion("bank_total >", value, "bankTotal");
            return (Criteria) this;
        }

        public Criteria andBankTotalGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("bank_total >=", value, "bankTotal");
            return (Criteria) this;
        }

        public Criteria andBankTotalLessThan(BigDecimal value) {
            addCriterion("bank_total <", value, "bankTotal");
            return (Criteria) this;
        }

        public Criteria andBankTotalLessThanOrEqualTo(BigDecimal value) {
            addCriterion("bank_total <=", value, "bankTotal");
            return (Criteria) this;
        }

        public Criteria andBankTotalIn(List<BigDecimal> values) {
            addCriterion("bank_total in", values, "bankTotal");
            return (Criteria) this;
        }

        public Criteria andBankTotalNotIn(List<BigDecimal> values) {
            addCriterion("bank_total not in", values, "bankTotal");
            return (Criteria) this;
        }

        public Criteria andBankTotalBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("bank_total between", value1, value2, "bankTotal");
            return (Criteria) this;
        }

        public Criteria andBankTotalNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("bank_total not between", value1, value2, "bankTotal");
            return (Criteria) this;
        }

        public Criteria andBankBalanceIsNull() {
            addCriterion("bank_balance is null");
            return (Criteria) this;
        }

        public Criteria andBankBalanceIsNotNull() {
            addCriterion("bank_balance is not null");
            return (Criteria) this;
        }

        public Criteria andBankBalanceEqualTo(BigDecimal value) {
            addCriterion("bank_balance =", value, "bankBalance");
            return (Criteria) this;
        }

        public Criteria andBankBalanceNotEqualTo(BigDecimal value) {
            addCriterion("bank_balance <>", value, "bankBalance");
            return (Criteria) this;
        }

        public Criteria andBankBalanceGreaterThan(BigDecimal value) {
            addCriterion("bank_balance >", value, "bankBalance");
            return (Criteria) this;
        }

        public Criteria andBankBalanceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("bank_balance >=", value, "bankBalance");
            return (Criteria) this;
        }

        public Criteria andBankBalanceLessThan(BigDecimal value) {
            addCriterion("bank_balance <", value, "bankBalance");
            return (Criteria) this;
        }

        public Criteria andBankBalanceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("bank_balance <=", value, "bankBalance");
            return (Criteria) this;
        }

        public Criteria andBankBalanceIn(List<BigDecimal> values) {
            addCriterion("bank_balance in", values, "bankBalance");
            return (Criteria) this;
        }

        public Criteria andBankBalanceNotIn(List<BigDecimal> values) {
            addCriterion("bank_balance not in", values, "bankBalance");
            return (Criteria) this;
        }

        public Criteria andBankBalanceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("bank_balance between", value1, value2, "bankBalance");
            return (Criteria) this;
        }

        public Criteria andBankBalanceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("bank_balance not between", value1, value2, "bankBalance");
            return (Criteria) this;
        }

        public Criteria andBankFrostIsNull() {
            addCriterion("bank_frost is null");
            return (Criteria) this;
        }

        public Criteria andBankFrostIsNotNull() {
            addCriterion("bank_frost is not null");
            return (Criteria) this;
        }

        public Criteria andBankFrostEqualTo(BigDecimal value) {
            addCriterion("bank_frost =", value, "bankFrost");
            return (Criteria) this;
        }

        public Criteria andBankFrostNotEqualTo(BigDecimal value) {
            addCriterion("bank_frost <>", value, "bankFrost");
            return (Criteria) this;
        }

        public Criteria andBankFrostGreaterThan(BigDecimal value) {
            addCriterion("bank_frost >", value, "bankFrost");
            return (Criteria) this;
        }

        public Criteria andBankFrostGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("bank_frost >=", value, "bankFrost");
            return (Criteria) this;
        }

        public Criteria andBankFrostLessThan(BigDecimal value) {
            addCriterion("bank_frost <", value, "bankFrost");
            return (Criteria) this;
        }

        public Criteria andBankFrostLessThanOrEqualTo(BigDecimal value) {
            addCriterion("bank_frost <=", value, "bankFrost");
            return (Criteria) this;
        }

        public Criteria andBankFrostIn(List<BigDecimal> values) {
            addCriterion("bank_frost in", values, "bankFrost");
            return (Criteria) this;
        }

        public Criteria andBankFrostNotIn(List<BigDecimal> values) {
            addCriterion("bank_frost not in", values, "bankFrost");
            return (Criteria) this;
        }

        public Criteria andBankFrostBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("bank_frost between", value1, value2, "bankFrost");
            return (Criteria) this;
        }

        public Criteria andBankFrostNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("bank_frost not between", value1, value2, "bankFrost");
            return (Criteria) this;
        }

        public Criteria andBankWaitRepayIsNull() {
            addCriterion("bank_wait_repay is null");
            return (Criteria) this;
        }

        public Criteria andBankWaitRepayIsNotNull() {
            addCriterion("bank_wait_repay is not null");
            return (Criteria) this;
        }

        public Criteria andBankWaitRepayEqualTo(BigDecimal value) {
            addCriterion("bank_wait_repay =", value, "bankWaitRepay");
            return (Criteria) this;
        }

        public Criteria andBankWaitRepayNotEqualTo(BigDecimal value) {
            addCriterion("bank_wait_repay <>", value, "bankWaitRepay");
            return (Criteria) this;
        }

        public Criteria andBankWaitRepayGreaterThan(BigDecimal value) {
            addCriterion("bank_wait_repay >", value, "bankWaitRepay");
            return (Criteria) this;
        }

        public Criteria andBankWaitRepayGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("bank_wait_repay >=", value, "bankWaitRepay");
            return (Criteria) this;
        }

        public Criteria andBankWaitRepayLessThan(BigDecimal value) {
            addCriterion("bank_wait_repay <", value, "bankWaitRepay");
            return (Criteria) this;
        }

        public Criteria andBankWaitRepayLessThanOrEqualTo(BigDecimal value) {
            addCriterion("bank_wait_repay <=", value, "bankWaitRepay");
            return (Criteria) this;
        }

        public Criteria andBankWaitRepayIn(List<BigDecimal> values) {
            addCriterion("bank_wait_repay in", values, "bankWaitRepay");
            return (Criteria) this;
        }

        public Criteria andBankWaitRepayNotIn(List<BigDecimal> values) {
            addCriterion("bank_wait_repay not in", values, "bankWaitRepay");
            return (Criteria) this;
        }

        public Criteria andBankWaitRepayBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("bank_wait_repay between", value1, value2, "bankWaitRepay");
            return (Criteria) this;
        }

        public Criteria andBankWaitRepayNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("bank_wait_repay not between", value1, value2, "bankWaitRepay");
            return (Criteria) this;
        }

        public Criteria andBankWaitCapitalIsNull() {
            addCriterion("bank_wait_capital is null");
            return (Criteria) this;
        }

        public Criteria andBankWaitCapitalIsNotNull() {
            addCriterion("bank_wait_capital is not null");
            return (Criteria) this;
        }

        public Criteria andBankWaitCapitalEqualTo(BigDecimal value) {
            addCriterion("bank_wait_capital =", value, "bankWaitCapital");
            return (Criteria) this;
        }

        public Criteria andBankWaitCapitalNotEqualTo(BigDecimal value) {
            addCriterion("bank_wait_capital <>", value, "bankWaitCapital");
            return (Criteria) this;
        }

        public Criteria andBankWaitCapitalGreaterThan(BigDecimal value) {
            addCriterion("bank_wait_capital >", value, "bankWaitCapital");
            return (Criteria) this;
        }

        public Criteria andBankWaitCapitalGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("bank_wait_capital >=", value, "bankWaitCapital");
            return (Criteria) this;
        }

        public Criteria andBankWaitCapitalLessThan(BigDecimal value) {
            addCriterion("bank_wait_capital <", value, "bankWaitCapital");
            return (Criteria) this;
        }

        public Criteria andBankWaitCapitalLessThanOrEqualTo(BigDecimal value) {
            addCriterion("bank_wait_capital <=", value, "bankWaitCapital");
            return (Criteria) this;
        }

        public Criteria andBankWaitCapitalIn(List<BigDecimal> values) {
            addCriterion("bank_wait_capital in", values, "bankWaitCapital");
            return (Criteria) this;
        }

        public Criteria andBankWaitCapitalNotIn(List<BigDecimal> values) {
            addCriterion("bank_wait_capital not in", values, "bankWaitCapital");
            return (Criteria) this;
        }

        public Criteria andBankWaitCapitalBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("bank_wait_capital between", value1, value2, "bankWaitCapital");
            return (Criteria) this;
        }

        public Criteria andBankWaitCapitalNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("bank_wait_capital not between", value1, value2, "bankWaitCapital");
            return (Criteria) this;
        }

        public Criteria andBankWaitInterestIsNull() {
            addCriterion("bank_wait_interest is null");
            return (Criteria) this;
        }

        public Criteria andBankWaitInterestIsNotNull() {
            addCriterion("bank_wait_interest is not null");
            return (Criteria) this;
        }

        public Criteria andBankWaitInterestEqualTo(BigDecimal value) {
            addCriterion("bank_wait_interest =", value, "bankWaitInterest");
            return (Criteria) this;
        }

        public Criteria andBankWaitInterestNotEqualTo(BigDecimal value) {
            addCriterion("bank_wait_interest <>", value, "bankWaitInterest");
            return (Criteria) this;
        }

        public Criteria andBankWaitInterestGreaterThan(BigDecimal value) {
            addCriterion("bank_wait_interest >", value, "bankWaitInterest");
            return (Criteria) this;
        }

        public Criteria andBankWaitInterestGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("bank_wait_interest >=", value, "bankWaitInterest");
            return (Criteria) this;
        }

        public Criteria andBankWaitInterestLessThan(BigDecimal value) {
            addCriterion("bank_wait_interest <", value, "bankWaitInterest");
            return (Criteria) this;
        }

        public Criteria andBankWaitInterestLessThanOrEqualTo(BigDecimal value) {
            addCriterion("bank_wait_interest <=", value, "bankWaitInterest");
            return (Criteria) this;
        }

        public Criteria andBankWaitInterestIn(List<BigDecimal> values) {
            addCriterion("bank_wait_interest in", values, "bankWaitInterest");
            return (Criteria) this;
        }

        public Criteria andBankWaitInterestNotIn(List<BigDecimal> values) {
            addCriterion("bank_wait_interest not in", values, "bankWaitInterest");
            return (Criteria) this;
        }

        public Criteria andBankWaitInterestBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("bank_wait_interest between", value1, value2, "bankWaitInterest");
            return (Criteria) this;
        }

        public Criteria andBankWaitInterestNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("bank_wait_interest not between", value1, value2, "bankWaitInterest");
            return (Criteria) this;
        }

        public Criteria andBankInterestSumIsNull() {
            addCriterion("bank_interest_sum is null");
            return (Criteria) this;
        }

        public Criteria andBankInterestSumIsNotNull() {
            addCriterion("bank_interest_sum is not null");
            return (Criteria) this;
        }

        public Criteria andBankInterestSumEqualTo(BigDecimal value) {
            addCriterion("bank_interest_sum =", value, "bankInterestSum");
            return (Criteria) this;
        }

        public Criteria andBankInterestSumNotEqualTo(BigDecimal value) {
            addCriterion("bank_interest_sum <>", value, "bankInterestSum");
            return (Criteria) this;
        }

        public Criteria andBankInterestSumGreaterThan(BigDecimal value) {
            addCriterion("bank_interest_sum >", value, "bankInterestSum");
            return (Criteria) this;
        }

        public Criteria andBankInterestSumGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("bank_interest_sum >=", value, "bankInterestSum");
            return (Criteria) this;
        }

        public Criteria andBankInterestSumLessThan(BigDecimal value) {
            addCriterion("bank_interest_sum <", value, "bankInterestSum");
            return (Criteria) this;
        }

        public Criteria andBankInterestSumLessThanOrEqualTo(BigDecimal value) {
            addCriterion("bank_interest_sum <=", value, "bankInterestSum");
            return (Criteria) this;
        }

        public Criteria andBankInterestSumIn(List<BigDecimal> values) {
            addCriterion("bank_interest_sum in", values, "bankInterestSum");
            return (Criteria) this;
        }

        public Criteria andBankInterestSumNotIn(List<BigDecimal> values) {
            addCriterion("bank_interest_sum not in", values, "bankInterestSum");
            return (Criteria) this;
        }

        public Criteria andBankInterestSumBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("bank_interest_sum between", value1, value2, "bankInterestSum");
            return (Criteria) this;
        }

        public Criteria andBankInterestSumNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("bank_interest_sum not between", value1, value2, "bankInterestSum");
            return (Criteria) this;
        }

        public Criteria andBankInvestSumIsNull() {
            addCriterion("bank_invest_sum is null");
            return (Criteria) this;
        }

        public Criteria andBankInvestSumIsNotNull() {
            addCriterion("bank_invest_sum is not null");
            return (Criteria) this;
        }

        public Criteria andBankInvestSumEqualTo(BigDecimal value) {
            addCriterion("bank_invest_sum =", value, "bankInvestSum");
            return (Criteria) this;
        }

        public Criteria andBankInvestSumNotEqualTo(BigDecimal value) {
            addCriterion("bank_invest_sum <>", value, "bankInvestSum");
            return (Criteria) this;
        }

        public Criteria andBankInvestSumGreaterThan(BigDecimal value) {
            addCriterion("bank_invest_sum >", value, "bankInvestSum");
            return (Criteria) this;
        }

        public Criteria andBankInvestSumGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("bank_invest_sum >=", value, "bankInvestSum");
            return (Criteria) this;
        }

        public Criteria andBankInvestSumLessThan(BigDecimal value) {
            addCriterion("bank_invest_sum <", value, "bankInvestSum");
            return (Criteria) this;
        }

        public Criteria andBankInvestSumLessThanOrEqualTo(BigDecimal value) {
            addCriterion("bank_invest_sum <=", value, "bankInvestSum");
            return (Criteria) this;
        }

        public Criteria andBankInvestSumIn(List<BigDecimal> values) {
            addCriterion("bank_invest_sum in", values, "bankInvestSum");
            return (Criteria) this;
        }

        public Criteria andBankInvestSumNotIn(List<BigDecimal> values) {
            addCriterion("bank_invest_sum not in", values, "bankInvestSum");
            return (Criteria) this;
        }

        public Criteria andBankInvestSumBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("bank_invest_sum between", value1, value2, "bankInvestSum");
            return (Criteria) this;
        }

        public Criteria andBankInvestSumNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("bank_invest_sum not between", value1, value2, "bankInvestSum");
            return (Criteria) this;
        }

        public Criteria andBankAwaitIsNull() {
            addCriterion("bank_await is null");
            return (Criteria) this;
        }

        public Criteria andBankAwaitIsNotNull() {
            addCriterion("bank_await is not null");
            return (Criteria) this;
        }

        public Criteria andBankAwaitEqualTo(BigDecimal value) {
            addCriterion("bank_await =", value, "bankAwait");
            return (Criteria) this;
        }

        public Criteria andBankAwaitNotEqualTo(BigDecimal value) {
            addCriterion("bank_await <>", value, "bankAwait");
            return (Criteria) this;
        }

        public Criteria andBankAwaitGreaterThan(BigDecimal value) {
            addCriterion("bank_await >", value, "bankAwait");
            return (Criteria) this;
        }

        public Criteria andBankAwaitGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("bank_await >=", value, "bankAwait");
            return (Criteria) this;
        }

        public Criteria andBankAwaitLessThan(BigDecimal value) {
            addCriterion("bank_await <", value, "bankAwait");
            return (Criteria) this;
        }

        public Criteria andBankAwaitLessThanOrEqualTo(BigDecimal value) {
            addCriterion("bank_await <=", value, "bankAwait");
            return (Criteria) this;
        }

        public Criteria andBankAwaitIn(List<BigDecimal> values) {
            addCriterion("bank_await in", values, "bankAwait");
            return (Criteria) this;
        }

        public Criteria andBankAwaitNotIn(List<BigDecimal> values) {
            addCriterion("bank_await not in", values, "bankAwait");
            return (Criteria) this;
        }

        public Criteria andBankAwaitBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("bank_await between", value1, value2, "bankAwait");
            return (Criteria) this;
        }

        public Criteria andBankAwaitNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("bank_await not between", value1, value2, "bankAwait");
            return (Criteria) this;
        }

        public Criteria andBankAwaitCapitalIsNull() {
            addCriterion("bank_await_capital is null");
            return (Criteria) this;
        }

        public Criteria andBankAwaitCapitalIsNotNull() {
            addCriterion("bank_await_capital is not null");
            return (Criteria) this;
        }

        public Criteria andBankAwaitCapitalEqualTo(BigDecimal value) {
            addCriterion("bank_await_capital =", value, "bankAwaitCapital");
            return (Criteria) this;
        }

        public Criteria andBankAwaitCapitalNotEqualTo(BigDecimal value) {
            addCriterion("bank_await_capital <>", value, "bankAwaitCapital");
            return (Criteria) this;
        }

        public Criteria andBankAwaitCapitalGreaterThan(BigDecimal value) {
            addCriterion("bank_await_capital >", value, "bankAwaitCapital");
            return (Criteria) this;
        }

        public Criteria andBankAwaitCapitalGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("bank_await_capital >=", value, "bankAwaitCapital");
            return (Criteria) this;
        }

        public Criteria andBankAwaitCapitalLessThan(BigDecimal value) {
            addCriterion("bank_await_capital <", value, "bankAwaitCapital");
            return (Criteria) this;
        }

        public Criteria andBankAwaitCapitalLessThanOrEqualTo(BigDecimal value) {
            addCriterion("bank_await_capital <=", value, "bankAwaitCapital");
            return (Criteria) this;
        }

        public Criteria andBankAwaitCapitalIn(List<BigDecimal> values) {
            addCriterion("bank_await_capital in", values, "bankAwaitCapital");
            return (Criteria) this;
        }

        public Criteria andBankAwaitCapitalNotIn(List<BigDecimal> values) {
            addCriterion("bank_await_capital not in", values, "bankAwaitCapital");
            return (Criteria) this;
        }

        public Criteria andBankAwaitCapitalBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("bank_await_capital between", value1, value2, "bankAwaitCapital");
            return (Criteria) this;
        }

        public Criteria andBankAwaitCapitalNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("bank_await_capital not between", value1, value2, "bankAwaitCapital");
            return (Criteria) this;
        }

        public Criteria andBankAwaitInterestIsNull() {
            addCriterion("bank_await_interest is null");
            return (Criteria) this;
        }

        public Criteria andBankAwaitInterestIsNotNull() {
            addCriterion("bank_await_interest is not null");
            return (Criteria) this;
        }

        public Criteria andBankAwaitInterestEqualTo(BigDecimal value) {
            addCriterion("bank_await_interest =", value, "bankAwaitInterest");
            return (Criteria) this;
        }

        public Criteria andBankAwaitInterestNotEqualTo(BigDecimal value) {
            addCriterion("bank_await_interest <>", value, "bankAwaitInterest");
            return (Criteria) this;
        }

        public Criteria andBankAwaitInterestGreaterThan(BigDecimal value) {
            addCriterion("bank_await_interest >", value, "bankAwaitInterest");
            return (Criteria) this;
        }

        public Criteria andBankAwaitInterestGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("bank_await_interest >=", value, "bankAwaitInterest");
            return (Criteria) this;
        }

        public Criteria andBankAwaitInterestLessThan(BigDecimal value) {
            addCriterion("bank_await_interest <", value, "bankAwaitInterest");
            return (Criteria) this;
        }

        public Criteria andBankAwaitInterestLessThanOrEqualTo(BigDecimal value) {
            addCriterion("bank_await_interest <=", value, "bankAwaitInterest");
            return (Criteria) this;
        }

        public Criteria andBankAwaitInterestIn(List<BigDecimal> values) {
            addCriterion("bank_await_interest in", values, "bankAwaitInterest");
            return (Criteria) this;
        }

        public Criteria andBankAwaitInterestNotIn(List<BigDecimal> values) {
            addCriterion("bank_await_interest not in", values, "bankAwaitInterest");
            return (Criteria) this;
        }

        public Criteria andBankAwaitInterestBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("bank_await_interest between", value1, value2, "bankAwaitInterest");
            return (Criteria) this;
        }

        public Criteria andBankAwaitInterestNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("bank_await_interest not between", value1, value2, "bankAwaitInterest");
            return (Criteria) this;
        }

        public Criteria andCheckDateIsNull() {
            addCriterion("check_date is null");
            return (Criteria) this;
        }

        public Criteria andCheckDateIsNotNull() {
            addCriterion("check_date is not null");
            return (Criteria) this;
        }

        public Criteria andCheckDateEqualTo(Integer value) {
            addCriterion("check_date =", value, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateNotEqualTo(Integer value) {
            addCriterion("check_date <>", value, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateGreaterThan(Integer value) {
            addCriterion("check_date >", value, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateGreaterThanOrEqualTo(Integer value) {
            addCriterion("check_date >=", value, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateLessThan(Integer value) {
            addCriterion("check_date <", value, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateLessThanOrEqualTo(Integer value) {
            addCriterion("check_date <=", value, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateIn(List<Integer> values) {
            addCriterion("check_date in", values, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateNotIn(List<Integer> values) {
            addCriterion("check_date not in", values, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateBetween(Integer value1, Integer value2) {
            addCriterion("check_date between", value1, value2, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateNotBetween(Integer value1, Integer value2) {
            addCriterion("check_date not between", value1, value2, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckBalanceIsNull() {
            addCriterion("check_balance is null");
            return (Criteria) this;
        }

        public Criteria andCheckBalanceIsNotNull() {
            addCriterion("check_balance is not null");
            return (Criteria) this;
        }

        public Criteria andCheckBalanceEqualTo(BigDecimal value) {
            addCriterion("check_balance =", value, "checkBalance");
            return (Criteria) this;
        }

        public Criteria andCheckBalanceNotEqualTo(BigDecimal value) {
            addCriterion("check_balance <>", value, "checkBalance");
            return (Criteria) this;
        }

        public Criteria andCheckBalanceGreaterThan(BigDecimal value) {
            addCriterion("check_balance >", value, "checkBalance");
            return (Criteria) this;
        }

        public Criteria andCheckBalanceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("check_balance >=", value, "checkBalance");
            return (Criteria) this;
        }

        public Criteria andCheckBalanceLessThan(BigDecimal value) {
            addCriterion("check_balance <", value, "checkBalance");
            return (Criteria) this;
        }

        public Criteria andCheckBalanceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("check_balance <=", value, "checkBalance");
            return (Criteria) this;
        }

        public Criteria andCheckBalanceIn(List<BigDecimal> values) {
            addCriterion("check_balance in", values, "checkBalance");
            return (Criteria) this;
        }

        public Criteria andCheckBalanceNotIn(List<BigDecimal> values) {
            addCriterion("check_balance not in", values, "checkBalance");
            return (Criteria) this;
        }

        public Criteria andCheckBalanceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("check_balance between", value1, value2, "checkBalance");
            return (Criteria) this;
        }

        public Criteria andCheckBalanceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("check_balance not between", value1, value2, "checkBalance");
            return (Criteria) this;
        }

        public Criteria andAccountDateIsNull() {
            addCriterion("account_date is null");
            return (Criteria) this;
        }

        public Criteria andAccountDateIsNotNull() {
            addCriterion("account_date is not null");
            return (Criteria) this;
        }

        public Criteria andAccountDateEqualTo(Integer value) {
            addCriterion("account_date =", value, "accountDate");
            return (Criteria) this;
        }

        public Criteria andAccountDateNotEqualTo(Integer value) {
            addCriterion("account_date <>", value, "accountDate");
            return (Criteria) this;
        }

        public Criteria andAccountDateGreaterThan(Integer value) {
            addCriterion("account_date >", value, "accountDate");
            return (Criteria) this;
        }

        public Criteria andAccountDateGreaterThanOrEqualTo(Integer value) {
            addCriterion("account_date >=", value, "accountDate");
            return (Criteria) this;
        }

        public Criteria andAccountDateLessThan(Integer value) {
            addCriterion("account_date <", value, "accountDate");
            return (Criteria) this;
        }

        public Criteria andAccountDateLessThanOrEqualTo(Integer value) {
            addCriterion("account_date <=", value, "accountDate");
            return (Criteria) this;
        }

        public Criteria andAccountDateIn(List<Integer> values) {
            addCriterion("account_date in", values, "accountDate");
            return (Criteria) this;
        }

        public Criteria andAccountDateNotIn(List<Integer> values) {
            addCriterion("account_date not in", values, "accountDate");
            return (Criteria) this;
        }

        public Criteria andAccountDateBetween(Integer value1, Integer value2) {
            addCriterion("account_date between", value1, value2, "accountDate");
            return (Criteria) this;
        }

        public Criteria andAccountDateNotBetween(Integer value1, Integer value2) {
            addCriterion("account_date not between", value1, value2, "accountDate");
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