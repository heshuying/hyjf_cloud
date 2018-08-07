package com.hyjf.am.trade.dao.model.auto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public ProductExample() {
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

        public Criteria andPnumberIsNull() {
            addCriterion("pnumber is null");
            return (Criteria) this;
        }

        public Criteria andPnumberIsNotNull() {
            addCriterion("pnumber is not null");
            return (Criteria) this;
        }

        public Criteria andPnumberEqualTo(String value) {
            addCriterion("pnumber =", value, "pnumber");
            return (Criteria) this;
        }

        public Criteria andPnumberNotEqualTo(String value) {
            addCriterion("pnumber <>", value, "pnumber");
            return (Criteria) this;
        }

        public Criteria andPnumberGreaterThan(String value) {
            addCriterion("pnumber >", value, "pnumber");
            return (Criteria) this;
        }

        public Criteria andPnumberGreaterThanOrEqualTo(String value) {
            addCriterion("pnumber >=", value, "pnumber");
            return (Criteria) this;
        }

        public Criteria andPnumberLessThan(String value) {
            addCriterion("pnumber <", value, "pnumber");
            return (Criteria) this;
        }

        public Criteria andPnumberLessThanOrEqualTo(String value) {
            addCriterion("pnumber <=", value, "pnumber");
            return (Criteria) this;
        }

        public Criteria andPnumberLike(String value) {
            addCriterion("pnumber like", value, "pnumber");
            return (Criteria) this;
        }

        public Criteria andPnumberNotLike(String value) {
            addCriterion("pnumber not like", value, "pnumber");
            return (Criteria) this;
        }

        public Criteria andPnumberIn(List<String> values) {
            addCriterion("pnumber in", values, "pnumber");
            return (Criteria) this;
        }

        public Criteria andPnumberNotIn(List<String> values) {
            addCriterion("pnumber not in", values, "pnumber");
            return (Criteria) this;
        }

        public Criteria andPnumberBetween(String value1, String value2) {
            addCriterion("pnumber between", value1, value2, "pnumber");
            return (Criteria) this;
        }

        public Criteria andPnumberNotBetween(String value1, String value2) {
            addCriterion("pnumber not between", value1, value2, "pnumber");
            return (Criteria) this;
        }

        public Criteria andInterestRateIsNull() {
            addCriterion("interest_rate is null");
            return (Criteria) this;
        }

        public Criteria andInterestRateIsNotNull() {
            addCriterion("interest_rate is not null");
            return (Criteria) this;
        }

        public Criteria andInterestRateEqualTo(BigDecimal value) {
            addCriterion("interest_rate =", value, "interestRate");
            return (Criteria) this;
        }

        public Criteria andInterestRateNotEqualTo(BigDecimal value) {
            addCriterion("interest_rate <>", value, "interestRate");
            return (Criteria) this;
        }

        public Criteria andInterestRateGreaterThan(BigDecimal value) {
            addCriterion("interest_rate >", value, "interestRate");
            return (Criteria) this;
        }

        public Criteria andInterestRateGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("interest_rate >=", value, "interestRate");
            return (Criteria) this;
        }

        public Criteria andInterestRateLessThan(BigDecimal value) {
            addCriterion("interest_rate <", value, "interestRate");
            return (Criteria) this;
        }

        public Criteria andInterestRateLessThanOrEqualTo(BigDecimal value) {
            addCriterion("interest_rate <=", value, "interestRate");
            return (Criteria) this;
        }

        public Criteria andInterestRateIn(List<BigDecimal> values) {
            addCriterion("interest_rate in", values, "interestRate");
            return (Criteria) this;
        }

        public Criteria andInterestRateNotIn(List<BigDecimal> values) {
            addCriterion("interest_rate not in", values, "interestRate");
            return (Criteria) this;
        }

        public Criteria andInterestRateBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("interest_rate between", value1, value2, "interestRate");
            return (Criteria) this;
        }

        public Criteria andInterestRateNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("interest_rate not between", value1, value2, "interestRate");
            return (Criteria) this;
        }

        public Criteria andPupperIsNull() {
            addCriterion("pupper is null");
            return (Criteria) this;
        }

        public Criteria andPupperIsNotNull() {
            addCriterion("pupper is not null");
            return (Criteria) this;
        }

        public Criteria andPupperEqualTo(BigDecimal value) {
            addCriterion("pupper =", value, "pupper");
            return (Criteria) this;
        }

        public Criteria andPupperNotEqualTo(BigDecimal value) {
            addCriterion("pupper <>", value, "pupper");
            return (Criteria) this;
        }

        public Criteria andPupperGreaterThan(BigDecimal value) {
            addCriterion("pupper >", value, "pupper");
            return (Criteria) this;
        }

        public Criteria andPupperGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("pupper >=", value, "pupper");
            return (Criteria) this;
        }

        public Criteria andPupperLessThan(BigDecimal value) {
            addCriterion("pupper <", value, "pupper");
            return (Criteria) this;
        }

        public Criteria andPupperLessThanOrEqualTo(BigDecimal value) {
            addCriterion("pupper <=", value, "pupper");
            return (Criteria) this;
        }

        public Criteria andPupperIn(List<BigDecimal> values) {
            addCriterion("pupper in", values, "pupper");
            return (Criteria) this;
        }

        public Criteria andPupperNotIn(List<BigDecimal> values) {
            addCriterion("pupper not in", values, "pupper");
            return (Criteria) this;
        }

        public Criteria andPupperBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("pupper between", value1, value2, "pupper");
            return (Criteria) this;
        }

        public Criteria andPupperNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("pupper not between", value1, value2, "pupper");
            return (Criteria) this;
        }

        public Criteria andPlowerIsNull() {
            addCriterion("plower is null");
            return (Criteria) this;
        }

        public Criteria andPlowerIsNotNull() {
            addCriterion("plower is not null");
            return (Criteria) this;
        }

        public Criteria andPlowerEqualTo(BigDecimal value) {
            addCriterion("plower =", value, "plower");
            return (Criteria) this;
        }

        public Criteria andPlowerNotEqualTo(BigDecimal value) {
            addCriterion("plower <>", value, "plower");
            return (Criteria) this;
        }

        public Criteria andPlowerGreaterThan(BigDecimal value) {
            addCriterion("plower >", value, "plower");
            return (Criteria) this;
        }

        public Criteria andPlowerGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("plower >=", value, "plower");
            return (Criteria) this;
        }

        public Criteria andPlowerLessThan(BigDecimal value) {
            addCriterion("plower <", value, "plower");
            return (Criteria) this;
        }

        public Criteria andPlowerLessThanOrEqualTo(BigDecimal value) {
            addCriterion("plower <=", value, "plower");
            return (Criteria) this;
        }

        public Criteria andPlowerIn(List<BigDecimal> values) {
            addCriterion("plower in", values, "plower");
            return (Criteria) this;
        }

        public Criteria andPlowerNotIn(List<BigDecimal> values) {
            addCriterion("plower not in", values, "plower");
            return (Criteria) this;
        }

        public Criteria andPlowerBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("plower between", value1, value2, "plower");
            return (Criteria) this;
        }

        public Criteria andPlowerNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("plower not between", value1, value2, "plower");
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

        public Criteria andAllpupperIsNull() {
            addCriterion("allpupper is null");
            return (Criteria) this;
        }

        public Criteria andAllpupperIsNotNull() {
            addCriterion("allpupper is not null");
            return (Criteria) this;
        }

        public Criteria andAllpupperEqualTo(BigDecimal value) {
            addCriterion("allpupper =", value, "allpupper");
            return (Criteria) this;
        }

        public Criteria andAllpupperNotEqualTo(BigDecimal value) {
            addCriterion("allpupper <>", value, "allpupper");
            return (Criteria) this;
        }

        public Criteria andAllpupperGreaterThan(BigDecimal value) {
            addCriterion("allpupper >", value, "allpupper");
            return (Criteria) this;
        }

        public Criteria andAllpupperGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("allpupper >=", value, "allpupper");
            return (Criteria) this;
        }

        public Criteria andAllpupperLessThan(BigDecimal value) {
            addCriterion("allpupper <", value, "allpupper");
            return (Criteria) this;
        }

        public Criteria andAllpupperLessThanOrEqualTo(BigDecimal value) {
            addCriterion("allpupper <=", value, "allpupper");
            return (Criteria) this;
        }

        public Criteria andAllpupperIn(List<BigDecimal> values) {
            addCriterion("allpupper in", values, "allpupper");
            return (Criteria) this;
        }

        public Criteria andAllpupperNotIn(List<BigDecimal> values) {
            addCriterion("allpupper not in", values, "allpupper");
            return (Criteria) this;
        }

        public Criteria andAllpupperBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("allpupper between", value1, value2, "allpupper");
            return (Criteria) this;
        }

        public Criteria andAllpupperNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("allpupper not between", value1, value2, "allpupper");
            return (Criteria) this;
        }

        public Criteria andIsTenderIsNull() {
            addCriterion("is_tender is null");
            return (Criteria) this;
        }

        public Criteria andIsTenderIsNotNull() {
            addCriterion("is_tender is not null");
            return (Criteria) this;
        }

        public Criteria andIsTenderEqualTo(Integer value) {
            addCriterion("is_tender =", value, "isTender");
            return (Criteria) this;
        }

        public Criteria andIsTenderNotEqualTo(Integer value) {
            addCriterion("is_tender <>", value, "isTender");
            return (Criteria) this;
        }

        public Criteria andIsTenderGreaterThan(Integer value) {
            addCriterion("is_tender >", value, "isTender");
            return (Criteria) this;
        }

        public Criteria andIsTenderGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_tender >=", value, "isTender");
            return (Criteria) this;
        }

        public Criteria andIsTenderLessThan(Integer value) {
            addCriterion("is_tender <", value, "isTender");
            return (Criteria) this;
        }

        public Criteria andIsTenderLessThanOrEqualTo(Integer value) {
            addCriterion("is_tender <=", value, "isTender");
            return (Criteria) this;
        }

        public Criteria andIsTenderIn(List<Integer> values) {
            addCriterion("is_tender in", values, "isTender");
            return (Criteria) this;
        }

        public Criteria andIsTenderNotIn(List<Integer> values) {
            addCriterion("is_tender not in", values, "isTender");
            return (Criteria) this;
        }

        public Criteria andIsTenderBetween(Integer value1, Integer value2) {
            addCriterion("is_tender between", value1, value2, "isTender");
            return (Criteria) this;
        }

        public Criteria andIsTenderNotBetween(Integer value1, Integer value2) {
            addCriterion("is_tender not between", value1, value2, "isTender");
            return (Criteria) this;
        }

        public Criteria andIsRedeemIsNull() {
            addCriterion("is_redeem is null");
            return (Criteria) this;
        }

        public Criteria andIsRedeemIsNotNull() {
            addCriterion("is_redeem is not null");
            return (Criteria) this;
        }

        public Criteria andIsRedeemEqualTo(Integer value) {
            addCriterion("is_redeem =", value, "isRedeem");
            return (Criteria) this;
        }

        public Criteria andIsRedeemNotEqualTo(Integer value) {
            addCriterion("is_redeem <>", value, "isRedeem");
            return (Criteria) this;
        }

        public Criteria andIsRedeemGreaterThan(Integer value) {
            addCriterion("is_redeem >", value, "isRedeem");
            return (Criteria) this;
        }

        public Criteria andIsRedeemGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_redeem >=", value, "isRedeem");
            return (Criteria) this;
        }

        public Criteria andIsRedeemLessThan(Integer value) {
            addCriterion("is_redeem <", value, "isRedeem");
            return (Criteria) this;
        }

        public Criteria andIsRedeemLessThanOrEqualTo(Integer value) {
            addCriterion("is_redeem <=", value, "isRedeem");
            return (Criteria) this;
        }

        public Criteria andIsRedeemIn(List<Integer> values) {
            addCriterion("is_redeem in", values, "isRedeem");
            return (Criteria) this;
        }

        public Criteria andIsRedeemNotIn(List<Integer> values) {
            addCriterion("is_redeem not in", values, "isRedeem");
            return (Criteria) this;
        }

        public Criteria andIsRedeemBetween(Integer value1, Integer value2) {
            addCriterion("is_redeem between", value1, value2, "isRedeem");
            return (Criteria) this;
        }

        public Criteria andIsRedeemNotBetween(Integer value1, Integer value2) {
            addCriterion("is_redeem not between", value1, value2, "isRedeem");
            return (Criteria) this;
        }

        public Criteria andErrorRemarkIsNull() {
            addCriterion("error_remark is null");
            return (Criteria) this;
        }

        public Criteria andErrorRemarkIsNotNull() {
            addCriterion("error_remark is not null");
            return (Criteria) this;
        }

        public Criteria andErrorRemarkEqualTo(String value) {
            addCriterion("error_remark =", value, "errorRemark");
            return (Criteria) this;
        }

        public Criteria andErrorRemarkNotEqualTo(String value) {
            addCriterion("error_remark <>", value, "errorRemark");
            return (Criteria) this;
        }

        public Criteria andErrorRemarkGreaterThan(String value) {
            addCriterion("error_remark >", value, "errorRemark");
            return (Criteria) this;
        }

        public Criteria andErrorRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("error_remark >=", value, "errorRemark");
            return (Criteria) this;
        }

        public Criteria andErrorRemarkLessThan(String value) {
            addCriterion("error_remark <", value, "errorRemark");
            return (Criteria) this;
        }

        public Criteria andErrorRemarkLessThanOrEqualTo(String value) {
            addCriterion("error_remark <=", value, "errorRemark");
            return (Criteria) this;
        }

        public Criteria andErrorRemarkLike(String value) {
            addCriterion("error_remark like", value, "errorRemark");
            return (Criteria) this;
        }

        public Criteria andErrorRemarkNotLike(String value) {
            addCriterion("error_remark not like", value, "errorRemark");
            return (Criteria) this;
        }

        public Criteria andErrorRemarkIn(List<String> values) {
            addCriterion("error_remark in", values, "errorRemark");
            return (Criteria) this;
        }

        public Criteria andErrorRemarkNotIn(List<String> values) {
            addCriterion("error_remark not in", values, "errorRemark");
            return (Criteria) this;
        }

        public Criteria andErrorRemarkBetween(String value1, String value2) {
            addCriterion("error_remark between", value1, value2, "errorRemark");
            return (Criteria) this;
        }

        public Criteria andErrorRemarkNotBetween(String value1, String value2) {
            addCriterion("error_remark not between", value1, value2, "errorRemark");
            return (Criteria) this;
        }

        public Criteria andPnumberNewIsNull() {
            addCriterion("pnumber_new is null");
            return (Criteria) this;
        }

        public Criteria andPnumberNewIsNotNull() {
            addCriterion("pnumber_new is not null");
            return (Criteria) this;
        }

        public Criteria andPnumberNewEqualTo(String value) {
            addCriterion("pnumber_new =", value, "pnumberNew");
            return (Criteria) this;
        }

        public Criteria andPnumberNewNotEqualTo(String value) {
            addCriterion("pnumber_new <>", value, "pnumberNew");
            return (Criteria) this;
        }

        public Criteria andPnumberNewGreaterThan(String value) {
            addCriterion("pnumber_new >", value, "pnumberNew");
            return (Criteria) this;
        }

        public Criteria andPnumberNewGreaterThanOrEqualTo(String value) {
            addCriterion("pnumber_new >=", value, "pnumberNew");
            return (Criteria) this;
        }

        public Criteria andPnumberNewLessThan(String value) {
            addCriterion("pnumber_new <", value, "pnumberNew");
            return (Criteria) this;
        }

        public Criteria andPnumberNewLessThanOrEqualTo(String value) {
            addCriterion("pnumber_new <=", value, "pnumberNew");
            return (Criteria) this;
        }

        public Criteria andPnumberNewLike(String value) {
            addCriterion("pnumber_new like", value, "pnumberNew");
            return (Criteria) this;
        }

        public Criteria andPnumberNewNotLike(String value) {
            addCriterion("pnumber_new not like", value, "pnumberNew");
            return (Criteria) this;
        }

        public Criteria andPnumberNewIn(List<String> values) {
            addCriterion("pnumber_new in", values, "pnumberNew");
            return (Criteria) this;
        }

        public Criteria andPnumberNewNotIn(List<String> values) {
            addCriterion("pnumber_new not in", values, "pnumberNew");
            return (Criteria) this;
        }

        public Criteria andPnumberNewBetween(String value1, String value2) {
            addCriterion("pnumber_new between", value1, value2, "pnumberNew");
            return (Criteria) this;
        }

        public Criteria andPnumberNewNotBetween(String value1, String value2) {
            addCriterion("pnumber_new not between", value1, value2, "pnumberNew");
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