package com.hyjf.am.trade.dao.model.auto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AleveLogExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public AleveLogExample() {
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

        public Criteria andBankIsNull() {
            addCriterion("bank is null");
            return (Criteria) this;
        }

        public Criteria andBankIsNotNull() {
            addCriterion("bank is not null");
            return (Criteria) this;
        }

        public Criteria andBankEqualTo(Integer value) {
            addCriterion("bank =", value, "bank");
            return (Criteria) this;
        }

        public Criteria andBankNotEqualTo(Integer value) {
            addCriterion("bank <>", value, "bank");
            return (Criteria) this;
        }

        public Criteria andBankGreaterThan(Integer value) {
            addCriterion("bank >", value, "bank");
            return (Criteria) this;
        }

        public Criteria andBankGreaterThanOrEqualTo(Integer value) {
            addCriterion("bank >=", value, "bank");
            return (Criteria) this;
        }

        public Criteria andBankLessThan(Integer value) {
            addCriterion("bank <", value, "bank");
            return (Criteria) this;
        }

        public Criteria andBankLessThanOrEqualTo(Integer value) {
            addCriterion("bank <=", value, "bank");
            return (Criteria) this;
        }

        public Criteria andBankIn(List<Integer> values) {
            addCriterion("bank in", values, "bank");
            return (Criteria) this;
        }

        public Criteria andBankNotIn(List<Integer> values) {
            addCriterion("bank not in", values, "bank");
            return (Criteria) this;
        }

        public Criteria andBankBetween(Integer value1, Integer value2) {
            addCriterion("bank between", value1, value2, "bank");
            return (Criteria) this;
        }

        public Criteria andBankNotBetween(Integer value1, Integer value2) {
            addCriterion("bank not between", value1, value2, "bank");
            return (Criteria) this;
        }

        public Criteria andCardnbrIsNull() {
            addCriterion("cardnbr is null");
            return (Criteria) this;
        }

        public Criteria andCardnbrIsNotNull() {
            addCriterion("cardnbr is not null");
            return (Criteria) this;
        }

        public Criteria andCardnbrEqualTo(String value) {
            addCriterion("cardnbr =", value, "cardnbr");
            return (Criteria) this;
        }

        public Criteria andCardnbrNotEqualTo(String value) {
            addCriterion("cardnbr <>", value, "cardnbr");
            return (Criteria) this;
        }

        public Criteria andCardnbrGreaterThan(String value) {
            addCriterion("cardnbr >", value, "cardnbr");
            return (Criteria) this;
        }

        public Criteria andCardnbrGreaterThanOrEqualTo(String value) {
            addCriterion("cardnbr >=", value, "cardnbr");
            return (Criteria) this;
        }

        public Criteria andCardnbrLessThan(String value) {
            addCriterion("cardnbr <", value, "cardnbr");
            return (Criteria) this;
        }

        public Criteria andCardnbrLessThanOrEqualTo(String value) {
            addCriterion("cardnbr <=", value, "cardnbr");
            return (Criteria) this;
        }

        public Criteria andCardnbrLike(String value) {
            addCriterion("cardnbr like", value, "cardnbr");
            return (Criteria) this;
        }

        public Criteria andCardnbrNotLike(String value) {
            addCriterion("cardnbr not like", value, "cardnbr");
            return (Criteria) this;
        }

        public Criteria andCardnbrIn(List<String> values) {
            addCriterion("cardnbr in", values, "cardnbr");
            return (Criteria) this;
        }

        public Criteria andCardnbrNotIn(List<String> values) {
            addCriterion("cardnbr not in", values, "cardnbr");
            return (Criteria) this;
        }

        public Criteria andCardnbrBetween(String value1, String value2) {
            addCriterion("cardnbr between", value1, value2, "cardnbr");
            return (Criteria) this;
        }

        public Criteria andCardnbrNotBetween(String value1, String value2) {
            addCriterion("cardnbr not between", value1, value2, "cardnbr");
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

        public Criteria andCurNumIsNull() {
            addCriterion("cur_num is null");
            return (Criteria) this;
        }

        public Criteria andCurNumIsNotNull() {
            addCriterion("cur_num is not null");
            return (Criteria) this;
        }

        public Criteria andCurNumEqualTo(Integer value) {
            addCriterion("cur_num =", value, "curNum");
            return (Criteria) this;
        }

        public Criteria andCurNumNotEqualTo(Integer value) {
            addCriterion("cur_num <>", value, "curNum");
            return (Criteria) this;
        }

        public Criteria andCurNumGreaterThan(Integer value) {
            addCriterion("cur_num >", value, "curNum");
            return (Criteria) this;
        }

        public Criteria andCurNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("cur_num >=", value, "curNum");
            return (Criteria) this;
        }

        public Criteria andCurNumLessThan(Integer value) {
            addCriterion("cur_num <", value, "curNum");
            return (Criteria) this;
        }

        public Criteria andCurNumLessThanOrEqualTo(Integer value) {
            addCriterion("cur_num <=", value, "curNum");
            return (Criteria) this;
        }

        public Criteria andCurNumIn(List<Integer> values) {
            addCriterion("cur_num in", values, "curNum");
            return (Criteria) this;
        }

        public Criteria andCurNumNotIn(List<Integer> values) {
            addCriterion("cur_num not in", values, "curNum");
            return (Criteria) this;
        }

        public Criteria andCurNumBetween(Integer value1, Integer value2) {
            addCriterion("cur_num between", value1, value2, "curNum");
            return (Criteria) this;
        }

        public Criteria andCurNumNotBetween(Integer value1, Integer value2) {
            addCriterion("cur_num not between", value1, value2, "curNum");
            return (Criteria) this;
        }

        public Criteria andCrflagIsNull() {
            addCriterion("crflag is null");
            return (Criteria) this;
        }

        public Criteria andCrflagIsNotNull() {
            addCriterion("crflag is not null");
            return (Criteria) this;
        }

        public Criteria andCrflagEqualTo(String value) {
            addCriterion("crflag =", value, "crflag");
            return (Criteria) this;
        }

        public Criteria andCrflagNotEqualTo(String value) {
            addCriterion("crflag <>", value, "crflag");
            return (Criteria) this;
        }

        public Criteria andCrflagGreaterThan(String value) {
            addCriterion("crflag >", value, "crflag");
            return (Criteria) this;
        }

        public Criteria andCrflagGreaterThanOrEqualTo(String value) {
            addCriterion("crflag >=", value, "crflag");
            return (Criteria) this;
        }

        public Criteria andCrflagLessThan(String value) {
            addCriterion("crflag <", value, "crflag");
            return (Criteria) this;
        }

        public Criteria andCrflagLessThanOrEqualTo(String value) {
            addCriterion("crflag <=", value, "crflag");
            return (Criteria) this;
        }

        public Criteria andCrflagLike(String value) {
            addCriterion("crflag like", value, "crflag");
            return (Criteria) this;
        }

        public Criteria andCrflagNotLike(String value) {
            addCriterion("crflag not like", value, "crflag");
            return (Criteria) this;
        }

        public Criteria andCrflagIn(List<String> values) {
            addCriterion("crflag in", values, "crflag");
            return (Criteria) this;
        }

        public Criteria andCrflagNotIn(List<String> values) {
            addCriterion("crflag not in", values, "crflag");
            return (Criteria) this;
        }

        public Criteria andCrflagBetween(String value1, String value2) {
            addCriterion("crflag between", value1, value2, "crflag");
            return (Criteria) this;
        }

        public Criteria andCrflagNotBetween(String value1, String value2) {
            addCriterion("crflag not between", value1, value2, "crflag");
            return (Criteria) this;
        }

        public Criteria andValdateIsNull() {
            addCriterion("valdate is null");
            return (Criteria) this;
        }

        public Criteria andValdateIsNotNull() {
            addCriterion("valdate is not null");
            return (Criteria) this;
        }

        public Criteria andValdateEqualTo(String value) {
            addCriterion("valdate =", value, "valdate");
            return (Criteria) this;
        }

        public Criteria andValdateNotEqualTo(String value) {
            addCriterion("valdate <>", value, "valdate");
            return (Criteria) this;
        }

        public Criteria andValdateGreaterThan(String value) {
            addCriterion("valdate >", value, "valdate");
            return (Criteria) this;
        }

        public Criteria andValdateGreaterThanOrEqualTo(String value) {
            addCriterion("valdate >=", value, "valdate");
            return (Criteria) this;
        }

        public Criteria andValdateLessThan(String value) {
            addCriterion("valdate <", value, "valdate");
            return (Criteria) this;
        }

        public Criteria andValdateLessThanOrEqualTo(String value) {
            addCriterion("valdate <=", value, "valdate");
            return (Criteria) this;
        }

        public Criteria andValdateLike(String value) {
            addCriterion("valdate like", value, "valdate");
            return (Criteria) this;
        }

        public Criteria andValdateNotLike(String value) {
            addCriterion("valdate not like", value, "valdate");
            return (Criteria) this;
        }

        public Criteria andValdateIn(List<String> values) {
            addCriterion("valdate in", values, "valdate");
            return (Criteria) this;
        }

        public Criteria andValdateNotIn(List<String> values) {
            addCriterion("valdate not in", values, "valdate");
            return (Criteria) this;
        }

        public Criteria andValdateBetween(String value1, String value2) {
            addCriterion("valdate between", value1, value2, "valdate");
            return (Criteria) this;
        }

        public Criteria andValdateNotBetween(String value1, String value2) {
            addCriterion("valdate not between", value1, value2, "valdate");
            return (Criteria) this;
        }

        public Criteria andInpdateIsNull() {
            addCriterion("inpdate is null");
            return (Criteria) this;
        }

        public Criteria andInpdateIsNotNull() {
            addCriterion("inpdate is not null");
            return (Criteria) this;
        }

        public Criteria andInpdateEqualTo(String value) {
            addCriterion("inpdate =", value, "inpdate");
            return (Criteria) this;
        }

        public Criteria andInpdateNotEqualTo(String value) {
            addCriterion("inpdate <>", value, "inpdate");
            return (Criteria) this;
        }

        public Criteria andInpdateGreaterThan(String value) {
            addCriterion("inpdate >", value, "inpdate");
            return (Criteria) this;
        }

        public Criteria andInpdateGreaterThanOrEqualTo(String value) {
            addCriterion("inpdate >=", value, "inpdate");
            return (Criteria) this;
        }

        public Criteria andInpdateLessThan(String value) {
            addCriterion("inpdate <", value, "inpdate");
            return (Criteria) this;
        }

        public Criteria andInpdateLessThanOrEqualTo(String value) {
            addCriterion("inpdate <=", value, "inpdate");
            return (Criteria) this;
        }

        public Criteria andInpdateLike(String value) {
            addCriterion("inpdate like", value, "inpdate");
            return (Criteria) this;
        }

        public Criteria andInpdateNotLike(String value) {
            addCriterion("inpdate not like", value, "inpdate");
            return (Criteria) this;
        }

        public Criteria andInpdateIn(List<String> values) {
            addCriterion("inpdate in", values, "inpdate");
            return (Criteria) this;
        }

        public Criteria andInpdateNotIn(List<String> values) {
            addCriterion("inpdate not in", values, "inpdate");
            return (Criteria) this;
        }

        public Criteria andInpdateBetween(String value1, String value2) {
            addCriterion("inpdate between", value1, value2, "inpdate");
            return (Criteria) this;
        }

        public Criteria andInpdateNotBetween(String value1, String value2) {
            addCriterion("inpdate not between", value1, value2, "inpdate");
            return (Criteria) this;
        }

        public Criteria andReldateIsNull() {
            addCriterion("reldate is null");
            return (Criteria) this;
        }

        public Criteria andReldateIsNotNull() {
            addCriterion("reldate is not null");
            return (Criteria) this;
        }

        public Criteria andReldateEqualTo(String value) {
            addCriterion("reldate =", value, "reldate");
            return (Criteria) this;
        }

        public Criteria andReldateNotEqualTo(String value) {
            addCriterion("reldate <>", value, "reldate");
            return (Criteria) this;
        }

        public Criteria andReldateGreaterThan(String value) {
            addCriterion("reldate >", value, "reldate");
            return (Criteria) this;
        }

        public Criteria andReldateGreaterThanOrEqualTo(String value) {
            addCriterion("reldate >=", value, "reldate");
            return (Criteria) this;
        }

        public Criteria andReldateLessThan(String value) {
            addCriterion("reldate <", value, "reldate");
            return (Criteria) this;
        }

        public Criteria andReldateLessThanOrEqualTo(String value) {
            addCriterion("reldate <=", value, "reldate");
            return (Criteria) this;
        }

        public Criteria andReldateLike(String value) {
            addCriterion("reldate like", value, "reldate");
            return (Criteria) this;
        }

        public Criteria andReldateNotLike(String value) {
            addCriterion("reldate not like", value, "reldate");
            return (Criteria) this;
        }

        public Criteria andReldateIn(List<String> values) {
            addCriterion("reldate in", values, "reldate");
            return (Criteria) this;
        }

        public Criteria andReldateNotIn(List<String> values) {
            addCriterion("reldate not in", values, "reldate");
            return (Criteria) this;
        }

        public Criteria andReldateBetween(String value1, String value2) {
            addCriterion("reldate between", value1, value2, "reldate");
            return (Criteria) this;
        }

        public Criteria andReldateNotBetween(String value1, String value2) {
            addCriterion("reldate not between", value1, value2, "reldate");
            return (Criteria) this;
        }

        public Criteria andInptimeIsNull() {
            addCriterion("inptime is null");
            return (Criteria) this;
        }

        public Criteria andInptimeIsNotNull() {
            addCriterion("inptime is not null");
            return (Criteria) this;
        }

        public Criteria andInptimeEqualTo(Integer value) {
            addCriterion("inptime =", value, "inptime");
            return (Criteria) this;
        }

        public Criteria andInptimeNotEqualTo(Integer value) {
            addCriterion("inptime <>", value, "inptime");
            return (Criteria) this;
        }

        public Criteria andInptimeGreaterThan(Integer value) {
            addCriterion("inptime >", value, "inptime");
            return (Criteria) this;
        }

        public Criteria andInptimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("inptime >=", value, "inptime");
            return (Criteria) this;
        }

        public Criteria andInptimeLessThan(Integer value) {
            addCriterion("inptime <", value, "inptime");
            return (Criteria) this;
        }

        public Criteria andInptimeLessThanOrEqualTo(Integer value) {
            addCriterion("inptime <=", value, "inptime");
            return (Criteria) this;
        }

        public Criteria andInptimeIn(List<Integer> values) {
            addCriterion("inptime in", values, "inptime");
            return (Criteria) this;
        }

        public Criteria andInptimeNotIn(List<Integer> values) {
            addCriterion("inptime not in", values, "inptime");
            return (Criteria) this;
        }

        public Criteria andInptimeBetween(Integer value1, Integer value2) {
            addCriterion("inptime between", value1, value2, "inptime");
            return (Criteria) this;
        }

        public Criteria andInptimeNotBetween(Integer value1, Integer value2) {
            addCriterion("inptime not between", value1, value2, "inptime");
            return (Criteria) this;
        }

        public Criteria andTrannoIsNull() {
            addCriterion("tranno is null");
            return (Criteria) this;
        }

        public Criteria andTrannoIsNotNull() {
            addCriterion("tranno is not null");
            return (Criteria) this;
        }

        public Criteria andTrannoEqualTo(String value) {
            addCriterion("tranno =", value, "tranno");
            return (Criteria) this;
        }

        public Criteria andTrannoNotEqualTo(String value) {
            addCriterion("tranno <>", value, "tranno");
            return (Criteria) this;
        }

        public Criteria andTrannoGreaterThan(String value) {
            addCriterion("tranno >", value, "tranno");
            return (Criteria) this;
        }

        public Criteria andTrannoGreaterThanOrEqualTo(String value) {
            addCriterion("tranno >=", value, "tranno");
            return (Criteria) this;
        }

        public Criteria andTrannoLessThan(String value) {
            addCriterion("tranno <", value, "tranno");
            return (Criteria) this;
        }

        public Criteria andTrannoLessThanOrEqualTo(String value) {
            addCriterion("tranno <=", value, "tranno");
            return (Criteria) this;
        }

        public Criteria andTrannoLike(String value) {
            addCriterion("tranno like", value, "tranno");
            return (Criteria) this;
        }

        public Criteria andTrannoNotLike(String value) {
            addCriterion("tranno not like", value, "tranno");
            return (Criteria) this;
        }

        public Criteria andTrannoIn(List<String> values) {
            addCriterion("tranno in", values, "tranno");
            return (Criteria) this;
        }

        public Criteria andTrannoNotIn(List<String> values) {
            addCriterion("tranno not in", values, "tranno");
            return (Criteria) this;
        }

        public Criteria andTrannoBetween(String value1, String value2) {
            addCriterion("tranno between", value1, value2, "tranno");
            return (Criteria) this;
        }

        public Criteria andTrannoNotBetween(String value1, String value2) {
            addCriterion("tranno not between", value1, value2, "tranno");
            return (Criteria) this;
        }

        public Criteria andOriTrannoIsNull() {
            addCriterion("ori_tranno is null");
            return (Criteria) this;
        }

        public Criteria andOriTrannoIsNotNull() {
            addCriterion("ori_tranno is not null");
            return (Criteria) this;
        }

        public Criteria andOriTrannoEqualTo(Integer value) {
            addCriterion("ori_tranno =", value, "oriTranno");
            return (Criteria) this;
        }

        public Criteria andOriTrannoNotEqualTo(Integer value) {
            addCriterion("ori_tranno <>", value, "oriTranno");
            return (Criteria) this;
        }

        public Criteria andOriTrannoGreaterThan(Integer value) {
            addCriterion("ori_tranno >", value, "oriTranno");
            return (Criteria) this;
        }

        public Criteria andOriTrannoGreaterThanOrEqualTo(Integer value) {
            addCriterion("ori_tranno >=", value, "oriTranno");
            return (Criteria) this;
        }

        public Criteria andOriTrannoLessThan(Integer value) {
            addCriterion("ori_tranno <", value, "oriTranno");
            return (Criteria) this;
        }

        public Criteria andOriTrannoLessThanOrEqualTo(Integer value) {
            addCriterion("ori_tranno <=", value, "oriTranno");
            return (Criteria) this;
        }

        public Criteria andOriTrannoIn(List<Integer> values) {
            addCriterion("ori_tranno in", values, "oriTranno");
            return (Criteria) this;
        }

        public Criteria andOriTrannoNotIn(List<Integer> values) {
            addCriterion("ori_tranno not in", values, "oriTranno");
            return (Criteria) this;
        }

        public Criteria andOriTrannoBetween(Integer value1, Integer value2) {
            addCriterion("ori_tranno between", value1, value2, "oriTranno");
            return (Criteria) this;
        }

        public Criteria andOriTrannoNotBetween(Integer value1, Integer value2) {
            addCriterion("ori_tranno not between", value1, value2, "oriTranno");
            return (Criteria) this;
        }

        public Criteria andTranstypeIsNull() {
            addCriterion("transtype is null");
            return (Criteria) this;
        }

        public Criteria andTranstypeIsNotNull() {
            addCriterion("transtype is not null");
            return (Criteria) this;
        }

        public Criteria andTranstypeEqualTo(Integer value) {
            addCriterion("transtype =", value, "transtype");
            return (Criteria) this;
        }

        public Criteria andTranstypeNotEqualTo(Integer value) {
            addCriterion("transtype <>", value, "transtype");
            return (Criteria) this;
        }

        public Criteria andTranstypeGreaterThan(Integer value) {
            addCriterion("transtype >", value, "transtype");
            return (Criteria) this;
        }

        public Criteria andTranstypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("transtype >=", value, "transtype");
            return (Criteria) this;
        }

        public Criteria andTranstypeLessThan(Integer value) {
            addCriterion("transtype <", value, "transtype");
            return (Criteria) this;
        }

        public Criteria andTranstypeLessThanOrEqualTo(Integer value) {
            addCriterion("transtype <=", value, "transtype");
            return (Criteria) this;
        }

        public Criteria andTranstypeIn(List<Integer> values) {
            addCriterion("transtype in", values, "transtype");
            return (Criteria) this;
        }

        public Criteria andTranstypeNotIn(List<Integer> values) {
            addCriterion("transtype not in", values, "transtype");
            return (Criteria) this;
        }

        public Criteria andTranstypeBetween(Integer value1, Integer value2) {
            addCriterion("transtype between", value1, value2, "transtype");
            return (Criteria) this;
        }

        public Criteria andTranstypeNotBetween(Integer value1, Integer value2) {
            addCriterion("transtype not between", value1, value2, "transtype");
            return (Criteria) this;
        }

        public Criteria andDeslineIsNull() {
            addCriterion("desline is null");
            return (Criteria) this;
        }

        public Criteria andDeslineIsNotNull() {
            addCriterion("desline is not null");
            return (Criteria) this;
        }

        public Criteria andDeslineEqualTo(String value) {
            addCriterion("desline =", value, "desline");
            return (Criteria) this;
        }

        public Criteria andDeslineNotEqualTo(String value) {
            addCriterion("desline <>", value, "desline");
            return (Criteria) this;
        }

        public Criteria andDeslineGreaterThan(String value) {
            addCriterion("desline >", value, "desline");
            return (Criteria) this;
        }

        public Criteria andDeslineGreaterThanOrEqualTo(String value) {
            addCriterion("desline >=", value, "desline");
            return (Criteria) this;
        }

        public Criteria andDeslineLessThan(String value) {
            addCriterion("desline <", value, "desline");
            return (Criteria) this;
        }

        public Criteria andDeslineLessThanOrEqualTo(String value) {
            addCriterion("desline <=", value, "desline");
            return (Criteria) this;
        }

        public Criteria andDeslineLike(String value) {
            addCriterion("desline like", value, "desline");
            return (Criteria) this;
        }

        public Criteria andDeslineNotLike(String value) {
            addCriterion("desline not like", value, "desline");
            return (Criteria) this;
        }

        public Criteria andDeslineIn(List<String> values) {
            addCriterion("desline in", values, "desline");
            return (Criteria) this;
        }

        public Criteria andDeslineNotIn(List<String> values) {
            addCriterion("desline not in", values, "desline");
            return (Criteria) this;
        }

        public Criteria andDeslineBetween(String value1, String value2) {
            addCriterion("desline between", value1, value2, "desline");
            return (Criteria) this;
        }

        public Criteria andDeslineNotBetween(String value1, String value2) {
            addCriterion("desline not between", value1, value2, "desline");
            return (Criteria) this;
        }

        public Criteria andCurrBalIsNull() {
            addCriterion("curr_bal is null");
            return (Criteria) this;
        }

        public Criteria andCurrBalIsNotNull() {
            addCriterion("curr_bal is not null");
            return (Criteria) this;
        }

        public Criteria andCurrBalEqualTo(BigDecimal value) {
            addCriterion("curr_bal =", value, "currBal");
            return (Criteria) this;
        }

        public Criteria andCurrBalNotEqualTo(BigDecimal value) {
            addCriterion("curr_bal <>", value, "currBal");
            return (Criteria) this;
        }

        public Criteria andCurrBalGreaterThan(BigDecimal value) {
            addCriterion("curr_bal >", value, "currBal");
            return (Criteria) this;
        }

        public Criteria andCurrBalGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("curr_bal >=", value, "currBal");
            return (Criteria) this;
        }

        public Criteria andCurrBalLessThan(BigDecimal value) {
            addCriterion("curr_bal <", value, "currBal");
            return (Criteria) this;
        }

        public Criteria andCurrBalLessThanOrEqualTo(BigDecimal value) {
            addCriterion("curr_bal <=", value, "currBal");
            return (Criteria) this;
        }

        public Criteria andCurrBalIn(List<BigDecimal> values) {
            addCriterion("curr_bal in", values, "currBal");
            return (Criteria) this;
        }

        public Criteria andCurrBalNotIn(List<BigDecimal> values) {
            addCriterion("curr_bal not in", values, "currBal");
            return (Criteria) this;
        }

        public Criteria andCurrBalBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("curr_bal between", value1, value2, "currBal");
            return (Criteria) this;
        }

        public Criteria andCurrBalNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("curr_bal not between", value1, value2, "currBal");
            return (Criteria) this;
        }

        public Criteria andForcardnbrIsNull() {
            addCriterion("forcardnbr is null");
            return (Criteria) this;
        }

        public Criteria andForcardnbrIsNotNull() {
            addCriterion("forcardnbr is not null");
            return (Criteria) this;
        }

        public Criteria andForcardnbrEqualTo(String value) {
            addCriterion("forcardnbr =", value, "forcardnbr");
            return (Criteria) this;
        }

        public Criteria andForcardnbrNotEqualTo(String value) {
            addCriterion("forcardnbr <>", value, "forcardnbr");
            return (Criteria) this;
        }

        public Criteria andForcardnbrGreaterThan(String value) {
            addCriterion("forcardnbr >", value, "forcardnbr");
            return (Criteria) this;
        }

        public Criteria andForcardnbrGreaterThanOrEqualTo(String value) {
            addCriterion("forcardnbr >=", value, "forcardnbr");
            return (Criteria) this;
        }

        public Criteria andForcardnbrLessThan(String value) {
            addCriterion("forcardnbr <", value, "forcardnbr");
            return (Criteria) this;
        }

        public Criteria andForcardnbrLessThanOrEqualTo(String value) {
            addCriterion("forcardnbr <=", value, "forcardnbr");
            return (Criteria) this;
        }

        public Criteria andForcardnbrLike(String value) {
            addCriterion("forcardnbr like", value, "forcardnbr");
            return (Criteria) this;
        }

        public Criteria andForcardnbrNotLike(String value) {
            addCriterion("forcardnbr not like", value, "forcardnbr");
            return (Criteria) this;
        }

        public Criteria andForcardnbrIn(List<String> values) {
            addCriterion("forcardnbr in", values, "forcardnbr");
            return (Criteria) this;
        }

        public Criteria andForcardnbrNotIn(List<String> values) {
            addCriterion("forcardnbr not in", values, "forcardnbr");
            return (Criteria) this;
        }

        public Criteria andForcardnbrBetween(String value1, String value2) {
            addCriterion("forcardnbr between", value1, value2, "forcardnbr");
            return (Criteria) this;
        }

        public Criteria andForcardnbrNotBetween(String value1, String value2) {
            addCriterion("forcardnbr not between", value1, value2, "forcardnbr");
            return (Criteria) this;
        }

        public Criteria andRevindIsNull() {
            addCriterion("revind is null");
            return (Criteria) this;
        }

        public Criteria andRevindIsNotNull() {
            addCriterion("revind is not null");
            return (Criteria) this;
        }

        public Criteria andRevindEqualTo(Integer value) {
            addCriterion("revind =", value, "revind");
            return (Criteria) this;
        }

        public Criteria andRevindNotEqualTo(Integer value) {
            addCriterion("revind <>", value, "revind");
            return (Criteria) this;
        }

        public Criteria andRevindGreaterThan(Integer value) {
            addCriterion("revind >", value, "revind");
            return (Criteria) this;
        }

        public Criteria andRevindGreaterThanOrEqualTo(Integer value) {
            addCriterion("revind >=", value, "revind");
            return (Criteria) this;
        }

        public Criteria andRevindLessThan(Integer value) {
            addCriterion("revind <", value, "revind");
            return (Criteria) this;
        }

        public Criteria andRevindLessThanOrEqualTo(Integer value) {
            addCriterion("revind <=", value, "revind");
            return (Criteria) this;
        }

        public Criteria andRevindIn(List<Integer> values) {
            addCriterion("revind in", values, "revind");
            return (Criteria) this;
        }

        public Criteria andRevindNotIn(List<Integer> values) {
            addCriterion("revind not in", values, "revind");
            return (Criteria) this;
        }

        public Criteria andRevindBetween(Integer value1, Integer value2) {
            addCriterion("revind between", value1, value2, "revind");
            return (Criteria) this;
        }

        public Criteria andRevindNotBetween(Integer value1, Integer value2) {
            addCriterion("revind not between", value1, value2, "revind");
            return (Criteria) this;
        }

        public Criteria andAccchgIsNull() {
            addCriterion("accchg is null");
            return (Criteria) this;
        }

        public Criteria andAccchgIsNotNull() {
            addCriterion("accchg is not null");
            return (Criteria) this;
        }

        public Criteria andAccchgEqualTo(String value) {
            addCriterion("accchg =", value, "accchg");
            return (Criteria) this;
        }

        public Criteria andAccchgNotEqualTo(String value) {
            addCriterion("accchg <>", value, "accchg");
            return (Criteria) this;
        }

        public Criteria andAccchgGreaterThan(String value) {
            addCriterion("accchg >", value, "accchg");
            return (Criteria) this;
        }

        public Criteria andAccchgGreaterThanOrEqualTo(String value) {
            addCriterion("accchg >=", value, "accchg");
            return (Criteria) this;
        }

        public Criteria andAccchgLessThan(String value) {
            addCriterion("accchg <", value, "accchg");
            return (Criteria) this;
        }

        public Criteria andAccchgLessThanOrEqualTo(String value) {
            addCriterion("accchg <=", value, "accchg");
            return (Criteria) this;
        }

        public Criteria andAccchgLike(String value) {
            addCriterion("accchg like", value, "accchg");
            return (Criteria) this;
        }

        public Criteria andAccchgNotLike(String value) {
            addCriterion("accchg not like", value, "accchg");
            return (Criteria) this;
        }

        public Criteria andAccchgIn(List<String> values) {
            addCriterion("accchg in", values, "accchg");
            return (Criteria) this;
        }

        public Criteria andAccchgNotIn(List<String> values) {
            addCriterion("accchg not in", values, "accchg");
            return (Criteria) this;
        }

        public Criteria andAccchgBetween(String value1, String value2) {
            addCriterion("accchg between", value1, value2, "accchg");
            return (Criteria) this;
        }

        public Criteria andAccchgNotBetween(String value1, String value2) {
            addCriterion("accchg not between", value1, value2, "accchg");
            return (Criteria) this;
        }

        public Criteria andSeqnoIsNull() {
            addCriterion("seqno is null");
            return (Criteria) this;
        }

        public Criteria andSeqnoIsNotNull() {
            addCriterion("seqno is not null");
            return (Criteria) this;
        }

        public Criteria andSeqnoEqualTo(Integer value) {
            addCriterion("seqno =", value, "seqno");
            return (Criteria) this;
        }

        public Criteria andSeqnoNotEqualTo(Integer value) {
            addCriterion("seqno <>", value, "seqno");
            return (Criteria) this;
        }

        public Criteria andSeqnoGreaterThan(Integer value) {
            addCriterion("seqno >", value, "seqno");
            return (Criteria) this;
        }

        public Criteria andSeqnoGreaterThanOrEqualTo(Integer value) {
            addCriterion("seqno >=", value, "seqno");
            return (Criteria) this;
        }

        public Criteria andSeqnoLessThan(Integer value) {
            addCriterion("seqno <", value, "seqno");
            return (Criteria) this;
        }

        public Criteria andSeqnoLessThanOrEqualTo(Integer value) {
            addCriterion("seqno <=", value, "seqno");
            return (Criteria) this;
        }

        public Criteria andSeqnoIn(List<Integer> values) {
            addCriterion("seqno in", values, "seqno");
            return (Criteria) this;
        }

        public Criteria andSeqnoNotIn(List<Integer> values) {
            addCriterion("seqno not in", values, "seqno");
            return (Criteria) this;
        }

        public Criteria andSeqnoBetween(Integer value1, Integer value2) {
            addCriterion("seqno between", value1, value2, "seqno");
            return (Criteria) this;
        }

        public Criteria andSeqnoNotBetween(Integer value1, Integer value2) {
            addCriterion("seqno not between", value1, value2, "seqno");
            return (Criteria) this;
        }

        public Criteria andOriNumIsNull() {
            addCriterion("ori_num is null");
            return (Criteria) this;
        }

        public Criteria andOriNumIsNotNull() {
            addCriterion("ori_num is not null");
            return (Criteria) this;
        }

        public Criteria andOriNumEqualTo(Integer value) {
            addCriterion("ori_num =", value, "oriNum");
            return (Criteria) this;
        }

        public Criteria andOriNumNotEqualTo(Integer value) {
            addCriterion("ori_num <>", value, "oriNum");
            return (Criteria) this;
        }

        public Criteria andOriNumGreaterThan(Integer value) {
            addCriterion("ori_num >", value, "oriNum");
            return (Criteria) this;
        }

        public Criteria andOriNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("ori_num >=", value, "oriNum");
            return (Criteria) this;
        }

        public Criteria andOriNumLessThan(Integer value) {
            addCriterion("ori_num <", value, "oriNum");
            return (Criteria) this;
        }

        public Criteria andOriNumLessThanOrEqualTo(Integer value) {
            addCriterion("ori_num <=", value, "oriNum");
            return (Criteria) this;
        }

        public Criteria andOriNumIn(List<Integer> values) {
            addCriterion("ori_num in", values, "oriNum");
            return (Criteria) this;
        }

        public Criteria andOriNumNotIn(List<Integer> values) {
            addCriterion("ori_num not in", values, "oriNum");
            return (Criteria) this;
        }

        public Criteria andOriNumBetween(Integer value1, Integer value2) {
            addCriterion("ori_num between", value1, value2, "oriNum");
            return (Criteria) this;
        }

        public Criteria andOriNumNotBetween(Integer value1, Integer value2) {
            addCriterion("ori_num not between", value1, value2, "oriNum");
            return (Criteria) this;
        }

        public Criteria andResvIsNull() {
            addCriterion("resv is null");
            return (Criteria) this;
        }

        public Criteria andResvIsNotNull() {
            addCriterion("resv is not null");
            return (Criteria) this;
        }

        public Criteria andResvEqualTo(String value) {
            addCriterion("resv =", value, "resv");
            return (Criteria) this;
        }

        public Criteria andResvNotEqualTo(String value) {
            addCriterion("resv <>", value, "resv");
            return (Criteria) this;
        }

        public Criteria andResvGreaterThan(String value) {
            addCriterion("resv >", value, "resv");
            return (Criteria) this;
        }

        public Criteria andResvGreaterThanOrEqualTo(String value) {
            addCriterion("resv >=", value, "resv");
            return (Criteria) this;
        }

        public Criteria andResvLessThan(String value) {
            addCriterion("resv <", value, "resv");
            return (Criteria) this;
        }

        public Criteria andResvLessThanOrEqualTo(String value) {
            addCriterion("resv <=", value, "resv");
            return (Criteria) this;
        }

        public Criteria andResvLike(String value) {
            addCriterion("resv like", value, "resv");
            return (Criteria) this;
        }

        public Criteria andResvNotLike(String value) {
            addCriterion("resv not like", value, "resv");
            return (Criteria) this;
        }

        public Criteria andResvIn(List<String> values) {
            addCriterion("resv in", values, "resv");
            return (Criteria) this;
        }

        public Criteria andResvNotIn(List<String> values) {
            addCriterion("resv not in", values, "resv");
            return (Criteria) this;
        }

        public Criteria andResvBetween(String value1, String value2) {
            addCriterion("resv between", value1, value2, "resv");
            return (Criteria) this;
        }

        public Criteria andResvNotBetween(String value1, String value2) {
            addCriterion("resv not between", value1, value2, "resv");
            return (Criteria) this;
        }

        public Criteria andCreateDayIsNull() {
            addCriterion("create_day is null");
            return (Criteria) this;
        }

        public Criteria andCreateDayIsNotNull() {
            addCriterion("create_day is not null");
            return (Criteria) this;
        }

        public Criteria andCreateDayEqualTo(String value) {
            addCriterion("create_day =", value, "createDay");
            return (Criteria) this;
        }

        public Criteria andCreateDayNotEqualTo(String value) {
            addCriterion("create_day <>", value, "createDay");
            return (Criteria) this;
        }

        public Criteria andCreateDayGreaterThan(String value) {
            addCriterion("create_day >", value, "createDay");
            return (Criteria) this;
        }

        public Criteria andCreateDayGreaterThanOrEqualTo(String value) {
            addCriterion("create_day >=", value, "createDay");
            return (Criteria) this;
        }

        public Criteria andCreateDayLessThan(String value) {
            addCriterion("create_day <", value, "createDay");
            return (Criteria) this;
        }

        public Criteria andCreateDayLessThanOrEqualTo(String value) {
            addCriterion("create_day <=", value, "createDay");
            return (Criteria) this;
        }

        public Criteria andCreateDayLike(String value) {
            addCriterion("create_day like", value, "createDay");
            return (Criteria) this;
        }

        public Criteria andCreateDayNotLike(String value) {
            addCriterion("create_day not like", value, "createDay");
            return (Criteria) this;
        }

        public Criteria andCreateDayIn(List<String> values) {
            addCriterion("create_day in", values, "createDay");
            return (Criteria) this;
        }

        public Criteria andCreateDayNotIn(List<String> values) {
            addCriterion("create_day not in", values, "createDay");
            return (Criteria) this;
        }

        public Criteria andCreateDayBetween(String value1, String value2) {
            addCriterion("create_day between", value1, value2, "createDay");
            return (Criteria) this;
        }

        public Criteria andCreateDayNotBetween(String value1, String value2) {
            addCriterion("create_day not between", value1, value2, "createDay");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdIsNull() {
            addCriterion("create_user_id is null");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdIsNotNull() {
            addCriterion("create_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdEqualTo(Integer value) {
            addCriterion("create_user_id =", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdNotEqualTo(Integer value) {
            addCriterion("create_user_id <>", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdGreaterThan(Integer value) {
            addCriterion("create_user_id >", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("create_user_id >=", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdLessThan(Integer value) {
            addCriterion("create_user_id <", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("create_user_id <=", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdIn(List<Integer> values) {
            addCriterion("create_user_id in", values, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdNotIn(List<Integer> values) {
            addCriterion("create_user_id not in", values, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdBetween(Integer value1, Integer value2) {
            addCriterion("create_user_id between", value1, value2, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("create_user_id not between", value1, value2, "createUserId");
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

        public Criteria andUpdateUserIdIsNull() {
            addCriterion("update_user_id is null");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdIsNotNull() {
            addCriterion("update_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdEqualTo(Integer value) {
            addCriterion("update_user_id =", value, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdNotEqualTo(Integer value) {
            addCriterion("update_user_id <>", value, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdGreaterThan(Integer value) {
            addCriterion("update_user_id >", value, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("update_user_id >=", value, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdLessThan(Integer value) {
            addCriterion("update_user_id <", value, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("update_user_id <=", value, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdIn(List<Integer> values) {
            addCriterion("update_user_id in", values, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdNotIn(List<Integer> values) {
            addCriterion("update_user_id not in", values, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdBetween(Integer value1, Integer value2) {
            addCriterion("update_user_id between", value1, value2, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("update_user_id not between", value1, value2, "updateUserId");
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