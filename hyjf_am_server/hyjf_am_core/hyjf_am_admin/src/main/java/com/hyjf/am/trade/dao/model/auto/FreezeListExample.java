package com.hyjf.am.trade.dao.model.auto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FreezeListExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public FreezeListExample() {
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

        public Criteria andOrdidIsNull() {
            addCriterion("OrdId is null");
            return (Criteria) this;
        }

        public Criteria andOrdidIsNotNull() {
            addCriterion("OrdId is not null");
            return (Criteria) this;
        }

        public Criteria andOrdidEqualTo(String value) {
            addCriterion("OrdId =", value, "ordid");
            return (Criteria) this;
        }

        public Criteria andOrdidNotEqualTo(String value) {
            addCriterion("OrdId <>", value, "ordid");
            return (Criteria) this;
        }

        public Criteria andOrdidGreaterThan(String value) {
            addCriterion("OrdId >", value, "ordid");
            return (Criteria) this;
        }

        public Criteria andOrdidGreaterThanOrEqualTo(String value) {
            addCriterion("OrdId >=", value, "ordid");
            return (Criteria) this;
        }

        public Criteria andOrdidLessThan(String value) {
            addCriterion("OrdId <", value, "ordid");
            return (Criteria) this;
        }

        public Criteria andOrdidLessThanOrEqualTo(String value) {
            addCriterion("OrdId <=", value, "ordid");
            return (Criteria) this;
        }

        public Criteria andOrdidLike(String value) {
            addCriterion("OrdId like", value, "ordid");
            return (Criteria) this;
        }

        public Criteria andOrdidNotLike(String value) {
            addCriterion("OrdId not like", value, "ordid");
            return (Criteria) this;
        }

        public Criteria andOrdidIn(List<String> values) {
            addCriterion("OrdId in", values, "ordid");
            return (Criteria) this;
        }

        public Criteria andOrdidNotIn(List<String> values) {
            addCriterion("OrdId not in", values, "ordid");
            return (Criteria) this;
        }

        public Criteria andOrdidBetween(String value1, String value2) {
            addCriterion("OrdId between", value1, value2, "ordid");
            return (Criteria) this;
        }

        public Criteria andOrdidNotBetween(String value1, String value2) {
            addCriterion("OrdId not between", value1, value2, "ordid");
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

        public Criteria andRespcodeIsNull() {
            addCriterion("RespCode is null");
            return (Criteria) this;
        }

        public Criteria andRespcodeIsNotNull() {
            addCriterion("RespCode is not null");
            return (Criteria) this;
        }

        public Criteria andRespcodeEqualTo(String value) {
            addCriterion("RespCode =", value, "respcode");
            return (Criteria) this;
        }

        public Criteria andRespcodeNotEqualTo(String value) {
            addCriterion("RespCode <>", value, "respcode");
            return (Criteria) this;
        }

        public Criteria andRespcodeGreaterThan(String value) {
            addCriterion("RespCode >", value, "respcode");
            return (Criteria) this;
        }

        public Criteria andRespcodeGreaterThanOrEqualTo(String value) {
            addCriterion("RespCode >=", value, "respcode");
            return (Criteria) this;
        }

        public Criteria andRespcodeLessThan(String value) {
            addCriterion("RespCode <", value, "respcode");
            return (Criteria) this;
        }

        public Criteria andRespcodeLessThanOrEqualTo(String value) {
            addCriterion("RespCode <=", value, "respcode");
            return (Criteria) this;
        }

        public Criteria andRespcodeLike(String value) {
            addCriterion("RespCode like", value, "respcode");
            return (Criteria) this;
        }

        public Criteria andRespcodeNotLike(String value) {
            addCriterion("RespCode not like", value, "respcode");
            return (Criteria) this;
        }

        public Criteria andRespcodeIn(List<String> values) {
            addCriterion("RespCode in", values, "respcode");
            return (Criteria) this;
        }

        public Criteria andRespcodeNotIn(List<String> values) {
            addCriterion("RespCode not in", values, "respcode");
            return (Criteria) this;
        }

        public Criteria andRespcodeBetween(String value1, String value2) {
            addCriterion("RespCode between", value1, value2, "respcode");
            return (Criteria) this;
        }

        public Criteria andRespcodeNotBetween(String value1, String value2) {
            addCriterion("RespCode not between", value1, value2, "respcode");
            return (Criteria) this;
        }

        public Criteria andUsrcustidIsNull() {
            addCriterion("UsrCustId is null");
            return (Criteria) this;
        }

        public Criteria andUsrcustidIsNotNull() {
            addCriterion("UsrCustId is not null");
            return (Criteria) this;
        }

        public Criteria andUsrcustidEqualTo(String value) {
            addCriterion("UsrCustId =", value, "usrcustid");
            return (Criteria) this;
        }

        public Criteria andUsrcustidNotEqualTo(String value) {
            addCriterion("UsrCustId <>", value, "usrcustid");
            return (Criteria) this;
        }

        public Criteria andUsrcustidGreaterThan(String value) {
            addCriterion("UsrCustId >", value, "usrcustid");
            return (Criteria) this;
        }

        public Criteria andUsrcustidGreaterThanOrEqualTo(String value) {
            addCriterion("UsrCustId >=", value, "usrcustid");
            return (Criteria) this;
        }

        public Criteria andUsrcustidLessThan(String value) {
            addCriterion("UsrCustId <", value, "usrcustid");
            return (Criteria) this;
        }

        public Criteria andUsrcustidLessThanOrEqualTo(String value) {
            addCriterion("UsrCustId <=", value, "usrcustid");
            return (Criteria) this;
        }

        public Criteria andUsrcustidLike(String value) {
            addCriterion("UsrCustId like", value, "usrcustid");
            return (Criteria) this;
        }

        public Criteria andUsrcustidNotLike(String value) {
            addCriterion("UsrCustId not like", value, "usrcustid");
            return (Criteria) this;
        }

        public Criteria andUsrcustidIn(List<String> values) {
            addCriterion("UsrCustId in", values, "usrcustid");
            return (Criteria) this;
        }

        public Criteria andUsrcustidNotIn(List<String> values) {
            addCriterion("UsrCustId not in", values, "usrcustid");
            return (Criteria) this;
        }

        public Criteria andUsrcustidBetween(String value1, String value2) {
            addCriterion("UsrCustId between", value1, value2, "usrcustid");
            return (Criteria) this;
        }

        public Criteria andUsrcustidNotBetween(String value1, String value2) {
            addCriterion("UsrCustId not between", value1, value2, "usrcustid");
            return (Criteria) this;
        }

        public Criteria andTrxidIsNull() {
            addCriterion("TrxId is null");
            return (Criteria) this;
        }

        public Criteria andTrxidIsNotNull() {
            addCriterion("TrxId is not null");
            return (Criteria) this;
        }

        public Criteria andTrxidEqualTo(String value) {
            addCriterion("TrxId =", value, "trxid");
            return (Criteria) this;
        }

        public Criteria andTrxidNotEqualTo(String value) {
            addCriterion("TrxId <>", value, "trxid");
            return (Criteria) this;
        }

        public Criteria andTrxidGreaterThan(String value) {
            addCriterion("TrxId >", value, "trxid");
            return (Criteria) this;
        }

        public Criteria andTrxidGreaterThanOrEqualTo(String value) {
            addCriterion("TrxId >=", value, "trxid");
            return (Criteria) this;
        }

        public Criteria andTrxidLessThan(String value) {
            addCriterion("TrxId <", value, "trxid");
            return (Criteria) this;
        }

        public Criteria andTrxidLessThanOrEqualTo(String value) {
            addCriterion("TrxId <=", value, "trxid");
            return (Criteria) this;
        }

        public Criteria andTrxidLike(String value) {
            addCriterion("TrxId like", value, "trxid");
            return (Criteria) this;
        }

        public Criteria andTrxidNotLike(String value) {
            addCriterion("TrxId not like", value, "trxid");
            return (Criteria) this;
        }

        public Criteria andTrxidIn(List<String> values) {
            addCriterion("TrxId in", values, "trxid");
            return (Criteria) this;
        }

        public Criteria andTrxidNotIn(List<String> values) {
            addCriterion("TrxId not in", values, "trxid");
            return (Criteria) this;
        }

        public Criteria andTrxidBetween(String value1, String value2) {
            addCriterion("TrxId between", value1, value2, "trxid");
            return (Criteria) this;
        }

        public Criteria andTrxidNotBetween(String value1, String value2) {
            addCriterion("TrxId not between", value1, value2, "trxid");
            return (Criteria) this;
        }

        public Criteria andXfromIsNull() {
            addCriterion("xfrom is null");
            return (Criteria) this;
        }

        public Criteria andXfromIsNotNull() {
            addCriterion("xfrom is not null");
            return (Criteria) this;
        }

        public Criteria andXfromEqualTo(Integer value) {
            addCriterion("xfrom =", value, "xfrom");
            return (Criteria) this;
        }

        public Criteria andXfromNotEqualTo(Integer value) {
            addCriterion("xfrom <>", value, "xfrom");
            return (Criteria) this;
        }

        public Criteria andXfromGreaterThan(Integer value) {
            addCriterion("xfrom >", value, "xfrom");
            return (Criteria) this;
        }

        public Criteria andXfromGreaterThanOrEqualTo(Integer value) {
            addCriterion("xfrom >=", value, "xfrom");
            return (Criteria) this;
        }

        public Criteria andXfromLessThan(Integer value) {
            addCriterion("xfrom <", value, "xfrom");
            return (Criteria) this;
        }

        public Criteria andXfromLessThanOrEqualTo(Integer value) {
            addCriterion("xfrom <=", value, "xfrom");
            return (Criteria) this;
        }

        public Criteria andXfromIn(List<Integer> values) {
            addCriterion("xfrom in", values, "xfrom");
            return (Criteria) this;
        }

        public Criteria andXfromNotIn(List<Integer> values) {
            addCriterion("xfrom not in", values, "xfrom");
            return (Criteria) this;
        }

        public Criteria andXfromBetween(Integer value1, Integer value2) {
            addCriterion("xfrom between", value1, value2, "xfrom");
            return (Criteria) this;
        }

        public Criteria andXfromNotBetween(Integer value1, Integer value2) {
            addCriterion("xfrom not between", value1, value2, "xfrom");
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

        public Criteria andUnfreezeManualIsNull() {
            addCriterion("unfreeze_manual is null");
            return (Criteria) this;
        }

        public Criteria andUnfreezeManualIsNotNull() {
            addCriterion("unfreeze_manual is not null");
            return (Criteria) this;
        }

        public Criteria andUnfreezeManualEqualTo(Integer value) {
            addCriterion("unfreeze_manual =", value, "unfreezeManual");
            return (Criteria) this;
        }

        public Criteria andUnfreezeManualNotEqualTo(Integer value) {
            addCriterion("unfreeze_manual <>", value, "unfreezeManual");
            return (Criteria) this;
        }

        public Criteria andUnfreezeManualGreaterThan(Integer value) {
            addCriterion("unfreeze_manual >", value, "unfreezeManual");
            return (Criteria) this;
        }

        public Criteria andUnfreezeManualGreaterThanOrEqualTo(Integer value) {
            addCriterion("unfreeze_manual >=", value, "unfreezeManual");
            return (Criteria) this;
        }

        public Criteria andUnfreezeManualLessThan(Integer value) {
            addCriterion("unfreeze_manual <", value, "unfreezeManual");
            return (Criteria) this;
        }

        public Criteria andUnfreezeManualLessThanOrEqualTo(Integer value) {
            addCriterion("unfreeze_manual <=", value, "unfreezeManual");
            return (Criteria) this;
        }

        public Criteria andUnfreezeManualIn(List<Integer> values) {
            addCriterion("unfreeze_manual in", values, "unfreezeManual");
            return (Criteria) this;
        }

        public Criteria andUnfreezeManualNotIn(List<Integer> values) {
            addCriterion("unfreeze_manual not in", values, "unfreezeManual");
            return (Criteria) this;
        }

        public Criteria andUnfreezeManualBetween(Integer value1, Integer value2) {
            addCriterion("unfreeze_manual between", value1, value2, "unfreezeManual");
            return (Criteria) this;
        }

        public Criteria andUnfreezeManualNotBetween(Integer value1, Integer value2) {
            addCriterion("unfreeze_manual not between", value1, value2, "unfreezeManual");
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