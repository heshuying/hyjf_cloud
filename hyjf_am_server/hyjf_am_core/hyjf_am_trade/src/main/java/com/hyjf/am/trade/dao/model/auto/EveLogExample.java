package com.hyjf.am.trade.dao.model.auto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EveLogExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public EveLogExample() {
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

        public Criteria andForcodeIsNull() {
            addCriterion("forcode is null");
            return (Criteria) this;
        }

        public Criteria andForcodeIsNotNull() {
            addCriterion("forcode is not null");
            return (Criteria) this;
        }

        public Criteria andForcodeEqualTo(String value) {
            addCriterion("forcode =", value, "forcode");
            return (Criteria) this;
        }

        public Criteria andForcodeNotEqualTo(String value) {
            addCriterion("forcode <>", value, "forcode");
            return (Criteria) this;
        }

        public Criteria andForcodeGreaterThan(String value) {
            addCriterion("forcode >", value, "forcode");
            return (Criteria) this;
        }

        public Criteria andForcodeGreaterThanOrEqualTo(String value) {
            addCriterion("forcode >=", value, "forcode");
            return (Criteria) this;
        }

        public Criteria andForcodeLessThan(String value) {
            addCriterion("forcode <", value, "forcode");
            return (Criteria) this;
        }

        public Criteria andForcodeLessThanOrEqualTo(String value) {
            addCriterion("forcode <=", value, "forcode");
            return (Criteria) this;
        }

        public Criteria andForcodeLike(String value) {
            addCriterion("forcode like", value, "forcode");
            return (Criteria) this;
        }

        public Criteria andForcodeNotLike(String value) {
            addCriterion("forcode not like", value, "forcode");
            return (Criteria) this;
        }

        public Criteria andForcodeIn(List<String> values) {
            addCriterion("forcode in", values, "forcode");
            return (Criteria) this;
        }

        public Criteria andForcodeNotIn(List<String> values) {
            addCriterion("forcode not in", values, "forcode");
            return (Criteria) this;
        }

        public Criteria andForcodeBetween(String value1, String value2) {
            addCriterion("forcode between", value1, value2, "forcode");
            return (Criteria) this;
        }

        public Criteria andForcodeNotBetween(String value1, String value2) {
            addCriterion("forcode not between", value1, value2, "forcode");
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

        public Criteria andCendtIsNull() {
            addCriterion("cendt is null");
            return (Criteria) this;
        }

        public Criteria andCendtIsNotNull() {
            addCriterion("cendt is not null");
            return (Criteria) this;
        }

        public Criteria andCendtEqualTo(Integer value) {
            addCriterion("cendt =", value, "cendt");
            return (Criteria) this;
        }

        public Criteria andCendtNotEqualTo(Integer value) {
            addCriterion("cendt <>", value, "cendt");
            return (Criteria) this;
        }

        public Criteria andCendtGreaterThan(Integer value) {
            addCriterion("cendt >", value, "cendt");
            return (Criteria) this;
        }

        public Criteria andCendtGreaterThanOrEqualTo(Integer value) {
            addCriterion("cendt >=", value, "cendt");
            return (Criteria) this;
        }

        public Criteria andCendtLessThan(Integer value) {
            addCriterion("cendt <", value, "cendt");
            return (Criteria) this;
        }

        public Criteria andCendtLessThanOrEqualTo(Integer value) {
            addCriterion("cendt <=", value, "cendt");
            return (Criteria) this;
        }

        public Criteria andCendtIn(List<Integer> values) {
            addCriterion("cendt in", values, "cendt");
            return (Criteria) this;
        }

        public Criteria andCendtNotIn(List<Integer> values) {
            addCriterion("cendt not in", values, "cendt");
            return (Criteria) this;
        }

        public Criteria andCendtBetween(Integer value1, Integer value2) {
            addCriterion("cendt between", value1, value2, "cendt");
            return (Criteria) this;
        }

        public Criteria andCendtNotBetween(Integer value1, Integer value2) {
            addCriterion("cendt not between", value1, value2, "cendt");
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

        public Criteria andMsgtypeIsNull() {
            addCriterion("msgtype is null");
            return (Criteria) this;
        }

        public Criteria andMsgtypeIsNotNull() {
            addCriterion("msgtype is not null");
            return (Criteria) this;
        }

        public Criteria andMsgtypeEqualTo(Integer value) {
            addCriterion("msgtype =", value, "msgtype");
            return (Criteria) this;
        }

        public Criteria andMsgtypeNotEqualTo(Integer value) {
            addCriterion("msgtype <>", value, "msgtype");
            return (Criteria) this;
        }

        public Criteria andMsgtypeGreaterThan(Integer value) {
            addCriterion("msgtype >", value, "msgtype");
            return (Criteria) this;
        }

        public Criteria andMsgtypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("msgtype >=", value, "msgtype");
            return (Criteria) this;
        }

        public Criteria andMsgtypeLessThan(Integer value) {
            addCriterion("msgtype <", value, "msgtype");
            return (Criteria) this;
        }

        public Criteria andMsgtypeLessThanOrEqualTo(Integer value) {
            addCriterion("msgtype <=", value, "msgtype");
            return (Criteria) this;
        }

        public Criteria andMsgtypeIn(List<Integer> values) {
            addCriterion("msgtype in", values, "msgtype");
            return (Criteria) this;
        }

        public Criteria andMsgtypeNotIn(List<Integer> values) {
            addCriterion("msgtype not in", values, "msgtype");
            return (Criteria) this;
        }

        public Criteria andMsgtypeBetween(Integer value1, Integer value2) {
            addCriterion("msgtype between", value1, value2, "msgtype");
            return (Criteria) this;
        }

        public Criteria andMsgtypeNotBetween(Integer value1, Integer value2) {
            addCriterion("msgtype not between", value1, value2, "msgtype");
            return (Criteria) this;
        }

        public Criteria andProccodeIsNull() {
            addCriterion("proccode is null");
            return (Criteria) this;
        }

        public Criteria andProccodeIsNotNull() {
            addCriterion("proccode is not null");
            return (Criteria) this;
        }

        public Criteria andProccodeEqualTo(Integer value) {
            addCriterion("proccode =", value, "proccode");
            return (Criteria) this;
        }

        public Criteria andProccodeNotEqualTo(Integer value) {
            addCriterion("proccode <>", value, "proccode");
            return (Criteria) this;
        }

        public Criteria andProccodeGreaterThan(Integer value) {
            addCriterion("proccode >", value, "proccode");
            return (Criteria) this;
        }

        public Criteria andProccodeGreaterThanOrEqualTo(Integer value) {
            addCriterion("proccode >=", value, "proccode");
            return (Criteria) this;
        }

        public Criteria andProccodeLessThan(Integer value) {
            addCriterion("proccode <", value, "proccode");
            return (Criteria) this;
        }

        public Criteria andProccodeLessThanOrEqualTo(Integer value) {
            addCriterion("proccode <=", value, "proccode");
            return (Criteria) this;
        }

        public Criteria andProccodeIn(List<Integer> values) {
            addCriterion("proccode in", values, "proccode");
            return (Criteria) this;
        }

        public Criteria andProccodeNotIn(List<Integer> values) {
            addCriterion("proccode not in", values, "proccode");
            return (Criteria) this;
        }

        public Criteria andProccodeBetween(Integer value1, Integer value2) {
            addCriterion("proccode between", value1, value2, "proccode");
            return (Criteria) this;
        }

        public Criteria andProccodeNotBetween(Integer value1, Integer value2) {
            addCriterion("proccode not between", value1, value2, "proccode");
            return (Criteria) this;
        }

        public Criteria andOrdernoIsNull() {
            addCriterion("orderno is null");
            return (Criteria) this;
        }

        public Criteria andOrdernoIsNotNull() {
            addCriterion("orderno is not null");
            return (Criteria) this;
        }

        public Criteria andOrdernoEqualTo(String value) {
            addCriterion("orderno =", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoNotEqualTo(String value) {
            addCriterion("orderno <>", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoGreaterThan(String value) {
            addCriterion("orderno >", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoGreaterThanOrEqualTo(String value) {
            addCriterion("orderno >=", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoLessThan(String value) {
            addCriterion("orderno <", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoLessThanOrEqualTo(String value) {
            addCriterion("orderno <=", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoLike(String value) {
            addCriterion("orderno like", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoNotLike(String value) {
            addCriterion("orderno not like", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoIn(List<String> values) {
            addCriterion("orderno in", values, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoNotIn(List<String> values) {
            addCriterion("orderno not in", values, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoBetween(String value1, String value2) {
            addCriterion("orderno between", value1, value2, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoNotBetween(String value1, String value2) {
            addCriterion("orderno not between", value1, value2, "orderno");
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

        public Criteria andReservedIsNull() {
            addCriterion("reserved is null");
            return (Criteria) this;
        }

        public Criteria andReservedIsNotNull() {
            addCriterion("reserved is not null");
            return (Criteria) this;
        }

        public Criteria andReservedEqualTo(String value) {
            addCriterion("reserved =", value, "reserved");
            return (Criteria) this;
        }

        public Criteria andReservedNotEqualTo(String value) {
            addCriterion("reserved <>", value, "reserved");
            return (Criteria) this;
        }

        public Criteria andReservedGreaterThan(String value) {
            addCriterion("reserved >", value, "reserved");
            return (Criteria) this;
        }

        public Criteria andReservedGreaterThanOrEqualTo(String value) {
            addCriterion("reserved >=", value, "reserved");
            return (Criteria) this;
        }

        public Criteria andReservedLessThan(String value) {
            addCriterion("reserved <", value, "reserved");
            return (Criteria) this;
        }

        public Criteria andReservedLessThanOrEqualTo(String value) {
            addCriterion("reserved <=", value, "reserved");
            return (Criteria) this;
        }

        public Criteria andReservedLike(String value) {
            addCriterion("reserved like", value, "reserved");
            return (Criteria) this;
        }

        public Criteria andReservedNotLike(String value) {
            addCriterion("reserved not like", value, "reserved");
            return (Criteria) this;
        }

        public Criteria andReservedIn(List<String> values) {
            addCriterion("reserved in", values, "reserved");
            return (Criteria) this;
        }

        public Criteria andReservedNotIn(List<String> values) {
            addCriterion("reserved not in", values, "reserved");
            return (Criteria) this;
        }

        public Criteria andReservedBetween(String value1, String value2) {
            addCriterion("reserved between", value1, value2, "reserved");
            return (Criteria) this;
        }

        public Criteria andReservedNotBetween(String value1, String value2) {
            addCriterion("reserved not between", value1, value2, "reserved");
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