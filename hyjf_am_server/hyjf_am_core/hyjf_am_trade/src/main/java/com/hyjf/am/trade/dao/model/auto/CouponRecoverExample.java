package com.hyjf.am.trade.dao.model.auto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CouponRecoverExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public CouponRecoverExample() {
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

        public Criteria andTenderIdIsNull() {
            addCriterion("tender_id is null");
            return (Criteria) this;
        }

        public Criteria andTenderIdIsNotNull() {
            addCriterion("tender_id is not null");
            return (Criteria) this;
        }

        public Criteria andTenderIdEqualTo(String value) {
            addCriterion("tender_id =", value, "tenderId");
            return (Criteria) this;
        }

        public Criteria andTenderIdNotEqualTo(String value) {
            addCriterion("tender_id <>", value, "tenderId");
            return (Criteria) this;
        }

        public Criteria andTenderIdGreaterThan(String value) {
            addCriterion("tender_id >", value, "tenderId");
            return (Criteria) this;
        }

        public Criteria andTenderIdGreaterThanOrEqualTo(String value) {
            addCriterion("tender_id >=", value, "tenderId");
            return (Criteria) this;
        }

        public Criteria andTenderIdLessThan(String value) {
            addCriterion("tender_id <", value, "tenderId");
            return (Criteria) this;
        }

        public Criteria andTenderIdLessThanOrEqualTo(String value) {
            addCriterion("tender_id <=", value, "tenderId");
            return (Criteria) this;
        }

        public Criteria andTenderIdLike(String value) {
            addCriterion("tender_id like", value, "tenderId");
            return (Criteria) this;
        }

        public Criteria andTenderIdNotLike(String value) {
            addCriterion("tender_id not like", value, "tenderId");
            return (Criteria) this;
        }

        public Criteria andTenderIdIn(List<String> values) {
            addCriterion("tender_id in", values, "tenderId");
            return (Criteria) this;
        }

        public Criteria andTenderIdNotIn(List<String> values) {
            addCriterion("tender_id not in", values, "tenderId");
            return (Criteria) this;
        }

        public Criteria andTenderIdBetween(String value1, String value2) {
            addCriterion("tender_id between", value1, value2, "tenderId");
            return (Criteria) this;
        }

        public Criteria andTenderIdNotBetween(String value1, String value2) {
            addCriterion("tender_id not between", value1, value2, "tenderId");
            return (Criteria) this;
        }

        public Criteria andTransferIdIsNull() {
            addCriterion("transfer_id is null");
            return (Criteria) this;
        }

        public Criteria andTransferIdIsNotNull() {
            addCriterion("transfer_id is not null");
            return (Criteria) this;
        }

        public Criteria andTransferIdEqualTo(String value) {
            addCriterion("transfer_id =", value, "transferId");
            return (Criteria) this;
        }

        public Criteria andTransferIdNotEqualTo(String value) {
            addCriterion("transfer_id <>", value, "transferId");
            return (Criteria) this;
        }

        public Criteria andTransferIdGreaterThan(String value) {
            addCriterion("transfer_id >", value, "transferId");
            return (Criteria) this;
        }

        public Criteria andTransferIdGreaterThanOrEqualTo(String value) {
            addCriterion("transfer_id >=", value, "transferId");
            return (Criteria) this;
        }

        public Criteria andTransferIdLessThan(String value) {
            addCriterion("transfer_id <", value, "transferId");
            return (Criteria) this;
        }

        public Criteria andTransferIdLessThanOrEqualTo(String value) {
            addCriterion("transfer_id <=", value, "transferId");
            return (Criteria) this;
        }

        public Criteria andTransferIdLike(String value) {
            addCriterion("transfer_id like", value, "transferId");
            return (Criteria) this;
        }

        public Criteria andTransferIdNotLike(String value) {
            addCriterion("transfer_id not like", value, "transferId");
            return (Criteria) this;
        }

        public Criteria andTransferIdIn(List<String> values) {
            addCriterion("transfer_id in", values, "transferId");
            return (Criteria) this;
        }

        public Criteria andTransferIdNotIn(List<String> values) {
            addCriterion("transfer_id not in", values, "transferId");
            return (Criteria) this;
        }

        public Criteria andTransferIdBetween(String value1, String value2) {
            addCriterion("transfer_id between", value1, value2, "transferId");
            return (Criteria) this;
        }

        public Criteria andTransferIdNotBetween(String value1, String value2) {
            addCriterion("transfer_id not between", value1, value2, "transferId");
            return (Criteria) this;
        }

        public Criteria andRecoverStatusIsNull() {
            addCriterion("recover_status is null");
            return (Criteria) this;
        }

        public Criteria andRecoverStatusIsNotNull() {
            addCriterion("recover_status is not null");
            return (Criteria) this;
        }

        public Criteria andRecoverStatusEqualTo(Integer value) {
            addCriterion("recover_status =", value, "recoverStatus");
            return (Criteria) this;
        }

        public Criteria andRecoverStatusNotEqualTo(Integer value) {
            addCriterion("recover_status <>", value, "recoverStatus");
            return (Criteria) this;
        }

        public Criteria andRecoverStatusGreaterThan(Integer value) {
            addCriterion("recover_status >", value, "recoverStatus");
            return (Criteria) this;
        }

        public Criteria andRecoverStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("recover_status >=", value, "recoverStatus");
            return (Criteria) this;
        }

        public Criteria andRecoverStatusLessThan(Integer value) {
            addCriterion("recover_status <", value, "recoverStatus");
            return (Criteria) this;
        }

        public Criteria andRecoverStatusLessThanOrEqualTo(Integer value) {
            addCriterion("recover_status <=", value, "recoverStatus");
            return (Criteria) this;
        }

        public Criteria andRecoverStatusIn(List<Integer> values) {
            addCriterion("recover_status in", values, "recoverStatus");
            return (Criteria) this;
        }

        public Criteria andRecoverStatusNotIn(List<Integer> values) {
            addCriterion("recover_status not in", values, "recoverStatus");
            return (Criteria) this;
        }

        public Criteria andRecoverStatusBetween(Integer value1, Integer value2) {
            addCriterion("recover_status between", value1, value2, "recoverStatus");
            return (Criteria) this;
        }

        public Criteria andRecoverStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("recover_status not between", value1, value2, "recoverStatus");
            return (Criteria) this;
        }

        public Criteria andReceivedFlgIsNull() {
            addCriterion("received_flg is null");
            return (Criteria) this;
        }

        public Criteria andReceivedFlgIsNotNull() {
            addCriterion("received_flg is not null");
            return (Criteria) this;
        }

        public Criteria andReceivedFlgEqualTo(Integer value) {
            addCriterion("received_flg =", value, "receivedFlg");
            return (Criteria) this;
        }

        public Criteria andReceivedFlgNotEqualTo(Integer value) {
            addCriterion("received_flg <>", value, "receivedFlg");
            return (Criteria) this;
        }

        public Criteria andReceivedFlgGreaterThan(Integer value) {
            addCriterion("received_flg >", value, "receivedFlg");
            return (Criteria) this;
        }

        public Criteria andReceivedFlgGreaterThanOrEqualTo(Integer value) {
            addCriterion("received_flg >=", value, "receivedFlg");
            return (Criteria) this;
        }

        public Criteria andReceivedFlgLessThan(Integer value) {
            addCriterion("received_flg <", value, "receivedFlg");
            return (Criteria) this;
        }

        public Criteria andReceivedFlgLessThanOrEqualTo(Integer value) {
            addCriterion("received_flg <=", value, "receivedFlg");
            return (Criteria) this;
        }

        public Criteria andReceivedFlgIn(List<Integer> values) {
            addCriterion("received_flg in", values, "receivedFlg");
            return (Criteria) this;
        }

        public Criteria andReceivedFlgNotIn(List<Integer> values) {
            addCriterion("received_flg not in", values, "receivedFlg");
            return (Criteria) this;
        }

        public Criteria andReceivedFlgBetween(Integer value1, Integer value2) {
            addCriterion("received_flg between", value1, value2, "receivedFlg");
            return (Criteria) this;
        }

        public Criteria andReceivedFlgNotBetween(Integer value1, Integer value2) {
            addCriterion("received_flg not between", value1, value2, "receivedFlg");
            return (Criteria) this;
        }

        public Criteria andRecoverPeriodIsNull() {
            addCriterion("recover_period is null");
            return (Criteria) this;
        }

        public Criteria andRecoverPeriodIsNotNull() {
            addCriterion("recover_period is not null");
            return (Criteria) this;
        }

        public Criteria andRecoverPeriodEqualTo(Integer value) {
            addCriterion("recover_period =", value, "recoverPeriod");
            return (Criteria) this;
        }

        public Criteria andRecoverPeriodNotEqualTo(Integer value) {
            addCriterion("recover_period <>", value, "recoverPeriod");
            return (Criteria) this;
        }

        public Criteria andRecoverPeriodGreaterThan(Integer value) {
            addCriterion("recover_period >", value, "recoverPeriod");
            return (Criteria) this;
        }

        public Criteria andRecoverPeriodGreaterThanOrEqualTo(Integer value) {
            addCriterion("recover_period >=", value, "recoverPeriod");
            return (Criteria) this;
        }

        public Criteria andRecoverPeriodLessThan(Integer value) {
            addCriterion("recover_period <", value, "recoverPeriod");
            return (Criteria) this;
        }

        public Criteria andRecoverPeriodLessThanOrEqualTo(Integer value) {
            addCriterion("recover_period <=", value, "recoverPeriod");
            return (Criteria) this;
        }

        public Criteria andRecoverPeriodIn(List<Integer> values) {
            addCriterion("recover_period in", values, "recoverPeriod");
            return (Criteria) this;
        }

        public Criteria andRecoverPeriodNotIn(List<Integer> values) {
            addCriterion("recover_period not in", values, "recoverPeriod");
            return (Criteria) this;
        }

        public Criteria andRecoverPeriodBetween(Integer value1, Integer value2) {
            addCriterion("recover_period between", value1, value2, "recoverPeriod");
            return (Criteria) this;
        }

        public Criteria andRecoverPeriodNotBetween(Integer value1, Integer value2) {
            addCriterion("recover_period not between", value1, value2, "recoverPeriod");
            return (Criteria) this;
        }

        public Criteria andTransferTimeIsNull() {
            addCriterion("transfer_time is null");
            return (Criteria) this;
        }

        public Criteria andTransferTimeIsNotNull() {
            addCriterion("transfer_time is not null");
            return (Criteria) this;
        }

        public Criteria andTransferTimeEqualTo(Integer value) {
            addCriterion("transfer_time =", value, "transferTime");
            return (Criteria) this;
        }

        public Criteria andTransferTimeNotEqualTo(Integer value) {
            addCriterion("transfer_time <>", value, "transferTime");
            return (Criteria) this;
        }

        public Criteria andTransferTimeGreaterThan(Integer value) {
            addCriterion("transfer_time >", value, "transferTime");
            return (Criteria) this;
        }

        public Criteria andTransferTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("transfer_time >=", value, "transferTime");
            return (Criteria) this;
        }

        public Criteria andTransferTimeLessThan(Integer value) {
            addCriterion("transfer_time <", value, "transferTime");
            return (Criteria) this;
        }

        public Criteria andTransferTimeLessThanOrEqualTo(Integer value) {
            addCriterion("transfer_time <=", value, "transferTime");
            return (Criteria) this;
        }

        public Criteria andTransferTimeIn(List<Integer> values) {
            addCriterion("transfer_time in", values, "transferTime");
            return (Criteria) this;
        }

        public Criteria andTransferTimeNotIn(List<Integer> values) {
            addCriterion("transfer_time not in", values, "transferTime");
            return (Criteria) this;
        }

        public Criteria andTransferTimeBetween(Integer value1, Integer value2) {
            addCriterion("transfer_time between", value1, value2, "transferTime");
            return (Criteria) this;
        }

        public Criteria andTransferTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("transfer_time not between", value1, value2, "transferTime");
            return (Criteria) this;
        }

        public Criteria andRecoverTimeIsNull() {
            addCriterion("recover_time is null");
            return (Criteria) this;
        }

        public Criteria andRecoverTimeIsNotNull() {
            addCriterion("recover_time is not null");
            return (Criteria) this;
        }

        public Criteria andRecoverTimeEqualTo(Integer value) {
            addCriterion("recover_time =", value, "recoverTime");
            return (Criteria) this;
        }

        public Criteria andRecoverTimeNotEqualTo(Integer value) {
            addCriterion("recover_time <>", value, "recoverTime");
            return (Criteria) this;
        }

        public Criteria andRecoverTimeGreaterThan(Integer value) {
            addCriterion("recover_time >", value, "recoverTime");
            return (Criteria) this;
        }

        public Criteria andRecoverTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("recover_time >=", value, "recoverTime");
            return (Criteria) this;
        }

        public Criteria andRecoverTimeLessThan(Integer value) {
            addCriterion("recover_time <", value, "recoverTime");
            return (Criteria) this;
        }

        public Criteria andRecoverTimeLessThanOrEqualTo(Integer value) {
            addCriterion("recover_time <=", value, "recoverTime");
            return (Criteria) this;
        }

        public Criteria andRecoverTimeIn(List<Integer> values) {
            addCriterion("recover_time in", values, "recoverTime");
            return (Criteria) this;
        }

        public Criteria andRecoverTimeNotIn(List<Integer> values) {
            addCriterion("recover_time not in", values, "recoverTime");
            return (Criteria) this;
        }

        public Criteria andRecoverTimeBetween(Integer value1, Integer value2) {
            addCriterion("recover_time between", value1, value2, "recoverTime");
            return (Criteria) this;
        }

        public Criteria andRecoverTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("recover_time not between", value1, value2, "recoverTime");
            return (Criteria) this;
        }

        public Criteria andRecoverYestimeIsNull() {
            addCriterion("recover_yestime is null");
            return (Criteria) this;
        }

        public Criteria andRecoverYestimeIsNotNull() {
            addCriterion("recover_yestime is not null");
            return (Criteria) this;
        }

        public Criteria andRecoverYestimeEqualTo(Integer value) {
            addCriterion("recover_yestime =", value, "recoverYestime");
            return (Criteria) this;
        }

        public Criteria andRecoverYestimeNotEqualTo(Integer value) {
            addCriterion("recover_yestime <>", value, "recoverYestime");
            return (Criteria) this;
        }

        public Criteria andRecoverYestimeGreaterThan(Integer value) {
            addCriterion("recover_yestime >", value, "recoverYestime");
            return (Criteria) this;
        }

        public Criteria andRecoverYestimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("recover_yestime >=", value, "recoverYestime");
            return (Criteria) this;
        }

        public Criteria andRecoverYestimeLessThan(Integer value) {
            addCriterion("recover_yestime <", value, "recoverYestime");
            return (Criteria) this;
        }

        public Criteria andRecoverYestimeLessThanOrEqualTo(Integer value) {
            addCriterion("recover_yestime <=", value, "recoverYestime");
            return (Criteria) this;
        }

        public Criteria andRecoverYestimeIn(List<Integer> values) {
            addCriterion("recover_yestime in", values, "recoverYestime");
            return (Criteria) this;
        }

        public Criteria andRecoverYestimeNotIn(List<Integer> values) {
            addCriterion("recover_yestime not in", values, "recoverYestime");
            return (Criteria) this;
        }

        public Criteria andRecoverYestimeBetween(Integer value1, Integer value2) {
            addCriterion("recover_yestime between", value1, value2, "recoverYestime");
            return (Criteria) this;
        }

        public Criteria andRecoverYestimeNotBetween(Integer value1, Integer value2) {
            addCriterion("recover_yestime not between", value1, value2, "recoverYestime");
            return (Criteria) this;
        }

        public Criteria andMainRecoverYestimeIsNull() {
            addCriterion("main_recover_yestime is null");
            return (Criteria) this;
        }

        public Criteria andMainRecoverYestimeIsNotNull() {
            addCriterion("main_recover_yestime is not null");
            return (Criteria) this;
        }

        public Criteria andMainRecoverYestimeEqualTo(Integer value) {
            addCriterion("main_recover_yestime =", value, "mainRecoverYestime");
            return (Criteria) this;
        }

        public Criteria andMainRecoverYestimeNotEqualTo(Integer value) {
            addCriterion("main_recover_yestime <>", value, "mainRecoverYestime");
            return (Criteria) this;
        }

        public Criteria andMainRecoverYestimeGreaterThan(Integer value) {
            addCriterion("main_recover_yestime >", value, "mainRecoverYestime");
            return (Criteria) this;
        }

        public Criteria andMainRecoverYestimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("main_recover_yestime >=", value, "mainRecoverYestime");
            return (Criteria) this;
        }

        public Criteria andMainRecoverYestimeLessThan(Integer value) {
            addCriterion("main_recover_yestime <", value, "mainRecoverYestime");
            return (Criteria) this;
        }

        public Criteria andMainRecoverYestimeLessThanOrEqualTo(Integer value) {
            addCriterion("main_recover_yestime <=", value, "mainRecoverYestime");
            return (Criteria) this;
        }

        public Criteria andMainRecoverYestimeIn(List<Integer> values) {
            addCriterion("main_recover_yestime in", values, "mainRecoverYestime");
            return (Criteria) this;
        }

        public Criteria andMainRecoverYestimeNotIn(List<Integer> values) {
            addCriterion("main_recover_yestime not in", values, "mainRecoverYestime");
            return (Criteria) this;
        }

        public Criteria andMainRecoverYestimeBetween(Integer value1, Integer value2) {
            addCriterion("main_recover_yestime between", value1, value2, "mainRecoverYestime");
            return (Criteria) this;
        }

        public Criteria andMainRecoverYestimeNotBetween(Integer value1, Integer value2) {
            addCriterion("main_recover_yestime not between", value1, value2, "mainRecoverYestime");
            return (Criteria) this;
        }

        public Criteria andRecoverInterestIsNull() {
            addCriterion("recover_interest is null");
            return (Criteria) this;
        }

        public Criteria andRecoverInterestIsNotNull() {
            addCriterion("recover_interest is not null");
            return (Criteria) this;
        }

        public Criteria andRecoverInterestEqualTo(BigDecimal value) {
            addCriterion("recover_interest =", value, "recoverInterest");
            return (Criteria) this;
        }

        public Criteria andRecoverInterestNotEqualTo(BigDecimal value) {
            addCriterion("recover_interest <>", value, "recoverInterest");
            return (Criteria) this;
        }

        public Criteria andRecoverInterestGreaterThan(BigDecimal value) {
            addCriterion("recover_interest >", value, "recoverInterest");
            return (Criteria) this;
        }

        public Criteria andRecoverInterestGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("recover_interest >=", value, "recoverInterest");
            return (Criteria) this;
        }

        public Criteria andRecoverInterestLessThan(BigDecimal value) {
            addCriterion("recover_interest <", value, "recoverInterest");
            return (Criteria) this;
        }

        public Criteria andRecoverInterestLessThanOrEqualTo(BigDecimal value) {
            addCriterion("recover_interest <=", value, "recoverInterest");
            return (Criteria) this;
        }

        public Criteria andRecoverInterestIn(List<BigDecimal> values) {
            addCriterion("recover_interest in", values, "recoverInterest");
            return (Criteria) this;
        }

        public Criteria andRecoverInterestNotIn(List<BigDecimal> values) {
            addCriterion("recover_interest not in", values, "recoverInterest");
            return (Criteria) this;
        }

        public Criteria andRecoverInterestBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("recover_interest between", value1, value2, "recoverInterest");
            return (Criteria) this;
        }

        public Criteria andRecoverInterestNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("recover_interest not between", value1, value2, "recoverInterest");
            return (Criteria) this;
        }

        public Criteria andRecoverInterestYesIsNull() {
            addCriterion("recover_interest_yes is null");
            return (Criteria) this;
        }

        public Criteria andRecoverInterestYesIsNotNull() {
            addCriterion("recover_interest_yes is not null");
            return (Criteria) this;
        }

        public Criteria andRecoverInterestYesEqualTo(BigDecimal value) {
            addCriterion("recover_interest_yes =", value, "recoverInterestYes");
            return (Criteria) this;
        }

        public Criteria andRecoverInterestYesNotEqualTo(BigDecimal value) {
            addCriterion("recover_interest_yes <>", value, "recoverInterestYes");
            return (Criteria) this;
        }

        public Criteria andRecoverInterestYesGreaterThan(BigDecimal value) {
            addCriterion("recover_interest_yes >", value, "recoverInterestYes");
            return (Criteria) this;
        }

        public Criteria andRecoverInterestYesGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("recover_interest_yes >=", value, "recoverInterestYes");
            return (Criteria) this;
        }

        public Criteria andRecoverInterestYesLessThan(BigDecimal value) {
            addCriterion("recover_interest_yes <", value, "recoverInterestYes");
            return (Criteria) this;
        }

        public Criteria andRecoverInterestYesLessThanOrEqualTo(BigDecimal value) {
            addCriterion("recover_interest_yes <=", value, "recoverInterestYes");
            return (Criteria) this;
        }

        public Criteria andRecoverInterestYesIn(List<BigDecimal> values) {
            addCriterion("recover_interest_yes in", values, "recoverInterestYes");
            return (Criteria) this;
        }

        public Criteria andRecoverInterestYesNotIn(List<BigDecimal> values) {
            addCriterion("recover_interest_yes not in", values, "recoverInterestYes");
            return (Criteria) this;
        }

        public Criteria andRecoverInterestYesBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("recover_interest_yes between", value1, value2, "recoverInterestYes");
            return (Criteria) this;
        }

        public Criteria andRecoverInterestYesNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("recover_interest_yes not between", value1, value2, "recoverInterestYes");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountIsNull() {
            addCriterion("recover_account is null");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountIsNotNull() {
            addCriterion("recover_account is not null");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountEqualTo(BigDecimal value) {
            addCriterion("recover_account =", value, "recoverAccount");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountNotEqualTo(BigDecimal value) {
            addCriterion("recover_account <>", value, "recoverAccount");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountGreaterThan(BigDecimal value) {
            addCriterion("recover_account >", value, "recoverAccount");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("recover_account >=", value, "recoverAccount");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountLessThan(BigDecimal value) {
            addCriterion("recover_account <", value, "recoverAccount");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("recover_account <=", value, "recoverAccount");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountIn(List<BigDecimal> values) {
            addCriterion("recover_account in", values, "recoverAccount");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountNotIn(List<BigDecimal> values) {
            addCriterion("recover_account not in", values, "recoverAccount");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("recover_account between", value1, value2, "recoverAccount");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("recover_account not between", value1, value2, "recoverAccount");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountYesIsNull() {
            addCriterion("recover_account_yes is null");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountYesIsNotNull() {
            addCriterion("recover_account_yes is not null");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountYesEqualTo(BigDecimal value) {
            addCriterion("recover_account_yes =", value, "recoverAccountYes");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountYesNotEqualTo(BigDecimal value) {
            addCriterion("recover_account_yes <>", value, "recoverAccountYes");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountYesGreaterThan(BigDecimal value) {
            addCriterion("recover_account_yes >", value, "recoverAccountYes");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountYesGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("recover_account_yes >=", value, "recoverAccountYes");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountYesLessThan(BigDecimal value) {
            addCriterion("recover_account_yes <", value, "recoverAccountYes");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountYesLessThanOrEqualTo(BigDecimal value) {
            addCriterion("recover_account_yes <=", value, "recoverAccountYes");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountYesIn(List<BigDecimal> values) {
            addCriterion("recover_account_yes in", values, "recoverAccountYes");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountYesNotIn(List<BigDecimal> values) {
            addCriterion("recover_account_yes not in", values, "recoverAccountYes");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountYesBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("recover_account_yes between", value1, value2, "recoverAccountYes");
            return (Criteria) this;
        }

        public Criteria andRecoverAccountYesNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("recover_account_yes not between", value1, value2, "recoverAccountYes");
            return (Criteria) this;
        }

        public Criteria andRecoverCapitalIsNull() {
            addCriterion("recover_capital is null");
            return (Criteria) this;
        }

        public Criteria andRecoverCapitalIsNotNull() {
            addCriterion("recover_capital is not null");
            return (Criteria) this;
        }

        public Criteria andRecoverCapitalEqualTo(BigDecimal value) {
            addCriterion("recover_capital =", value, "recoverCapital");
            return (Criteria) this;
        }

        public Criteria andRecoverCapitalNotEqualTo(BigDecimal value) {
            addCriterion("recover_capital <>", value, "recoverCapital");
            return (Criteria) this;
        }

        public Criteria andRecoverCapitalGreaterThan(BigDecimal value) {
            addCriterion("recover_capital >", value, "recoverCapital");
            return (Criteria) this;
        }

        public Criteria andRecoverCapitalGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("recover_capital >=", value, "recoverCapital");
            return (Criteria) this;
        }

        public Criteria andRecoverCapitalLessThan(BigDecimal value) {
            addCriterion("recover_capital <", value, "recoverCapital");
            return (Criteria) this;
        }

        public Criteria andRecoverCapitalLessThanOrEqualTo(BigDecimal value) {
            addCriterion("recover_capital <=", value, "recoverCapital");
            return (Criteria) this;
        }

        public Criteria andRecoverCapitalIn(List<BigDecimal> values) {
            addCriterion("recover_capital in", values, "recoverCapital");
            return (Criteria) this;
        }

        public Criteria andRecoverCapitalNotIn(List<BigDecimal> values) {
            addCriterion("recover_capital not in", values, "recoverCapital");
            return (Criteria) this;
        }

        public Criteria andRecoverCapitalBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("recover_capital between", value1, value2, "recoverCapital");
            return (Criteria) this;
        }

        public Criteria andRecoverCapitalNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("recover_capital not between", value1, value2, "recoverCapital");
            return (Criteria) this;
        }

        public Criteria andRecoverCapitalYesIsNull() {
            addCriterion("recover_capital_yes is null");
            return (Criteria) this;
        }

        public Criteria andRecoverCapitalYesIsNotNull() {
            addCriterion("recover_capital_yes is not null");
            return (Criteria) this;
        }

        public Criteria andRecoverCapitalYesEqualTo(BigDecimal value) {
            addCriterion("recover_capital_yes =", value, "recoverCapitalYes");
            return (Criteria) this;
        }

        public Criteria andRecoverCapitalYesNotEqualTo(BigDecimal value) {
            addCriterion("recover_capital_yes <>", value, "recoverCapitalYes");
            return (Criteria) this;
        }

        public Criteria andRecoverCapitalYesGreaterThan(BigDecimal value) {
            addCriterion("recover_capital_yes >", value, "recoverCapitalYes");
            return (Criteria) this;
        }

        public Criteria andRecoverCapitalYesGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("recover_capital_yes >=", value, "recoverCapitalYes");
            return (Criteria) this;
        }

        public Criteria andRecoverCapitalYesLessThan(BigDecimal value) {
            addCriterion("recover_capital_yes <", value, "recoverCapitalYes");
            return (Criteria) this;
        }

        public Criteria andRecoverCapitalYesLessThanOrEqualTo(BigDecimal value) {
            addCriterion("recover_capital_yes <=", value, "recoverCapitalYes");
            return (Criteria) this;
        }

        public Criteria andRecoverCapitalYesIn(List<BigDecimal> values) {
            addCriterion("recover_capital_yes in", values, "recoverCapitalYes");
            return (Criteria) this;
        }

        public Criteria andRecoverCapitalYesNotIn(List<BigDecimal> values) {
            addCriterion("recover_capital_yes not in", values, "recoverCapitalYes");
            return (Criteria) this;
        }

        public Criteria andRecoverCapitalYesBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("recover_capital_yes between", value1, value2, "recoverCapitalYes");
            return (Criteria) this;
        }

        public Criteria andRecoverCapitalYesNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("recover_capital_yes not between", value1, value2, "recoverCapitalYes");
            return (Criteria) this;
        }

        public Criteria andCurrentRecoverFlgIsNull() {
            addCriterion("current_recover_flg is null");
            return (Criteria) this;
        }

        public Criteria andCurrentRecoverFlgIsNotNull() {
            addCriterion("current_recover_flg is not null");
            return (Criteria) this;
        }

        public Criteria andCurrentRecoverFlgEqualTo(Integer value) {
            addCriterion("current_recover_flg =", value, "currentRecoverFlg");
            return (Criteria) this;
        }

        public Criteria andCurrentRecoverFlgNotEqualTo(Integer value) {
            addCriterion("current_recover_flg <>", value, "currentRecoverFlg");
            return (Criteria) this;
        }

        public Criteria andCurrentRecoverFlgGreaterThan(Integer value) {
            addCriterion("current_recover_flg >", value, "currentRecoverFlg");
            return (Criteria) this;
        }

        public Criteria andCurrentRecoverFlgGreaterThanOrEqualTo(Integer value) {
            addCriterion("current_recover_flg >=", value, "currentRecoverFlg");
            return (Criteria) this;
        }

        public Criteria andCurrentRecoverFlgLessThan(Integer value) {
            addCriterion("current_recover_flg <", value, "currentRecoverFlg");
            return (Criteria) this;
        }

        public Criteria andCurrentRecoverFlgLessThanOrEqualTo(Integer value) {
            addCriterion("current_recover_flg <=", value, "currentRecoverFlg");
            return (Criteria) this;
        }

        public Criteria andCurrentRecoverFlgIn(List<Integer> values) {
            addCriterion("current_recover_flg in", values, "currentRecoverFlg");
            return (Criteria) this;
        }

        public Criteria andCurrentRecoverFlgNotIn(List<Integer> values) {
            addCriterion("current_recover_flg not in", values, "currentRecoverFlg");
            return (Criteria) this;
        }

        public Criteria andCurrentRecoverFlgBetween(Integer value1, Integer value2) {
            addCriterion("current_recover_flg between", value1, value2, "currentRecoverFlg");
            return (Criteria) this;
        }

        public Criteria andCurrentRecoverFlgNotBetween(Integer value1, Integer value2) {
            addCriterion("current_recover_flg not between", value1, value2, "currentRecoverFlg");
            return (Criteria) this;
        }

        public Criteria andRecoverTypeIsNull() {
            addCriterion("recover_type is null");
            return (Criteria) this;
        }

        public Criteria andRecoverTypeIsNotNull() {
            addCriterion("recover_type is not null");
            return (Criteria) this;
        }

        public Criteria andRecoverTypeEqualTo(Integer value) {
            addCriterion("recover_type =", value, "recoverType");
            return (Criteria) this;
        }

        public Criteria andRecoverTypeNotEqualTo(Integer value) {
            addCriterion("recover_type <>", value, "recoverType");
            return (Criteria) this;
        }

        public Criteria andRecoverTypeGreaterThan(Integer value) {
            addCriterion("recover_type >", value, "recoverType");
            return (Criteria) this;
        }

        public Criteria andRecoverTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("recover_type >=", value, "recoverType");
            return (Criteria) this;
        }

        public Criteria andRecoverTypeLessThan(Integer value) {
            addCriterion("recover_type <", value, "recoverType");
            return (Criteria) this;
        }

        public Criteria andRecoverTypeLessThanOrEqualTo(Integer value) {
            addCriterion("recover_type <=", value, "recoverType");
            return (Criteria) this;
        }

        public Criteria andRecoverTypeIn(List<Integer> values) {
            addCriterion("recover_type in", values, "recoverType");
            return (Criteria) this;
        }

        public Criteria andRecoverTypeNotIn(List<Integer> values) {
            addCriterion("recover_type not in", values, "recoverType");
            return (Criteria) this;
        }

        public Criteria andRecoverTypeBetween(Integer value1, Integer value2) {
            addCriterion("recover_type between", value1, value2, "recoverType");
            return (Criteria) this;
        }

        public Criteria andRecoverTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("recover_type not between", value1, value2, "recoverType");
            return (Criteria) this;
        }

        public Criteria andNoticeFlgIsNull() {
            addCriterion("notice_flg is null");
            return (Criteria) this;
        }

        public Criteria andNoticeFlgIsNotNull() {
            addCriterion("notice_flg is not null");
            return (Criteria) this;
        }

        public Criteria andNoticeFlgEqualTo(Integer value) {
            addCriterion("notice_flg =", value, "noticeFlg");
            return (Criteria) this;
        }

        public Criteria andNoticeFlgNotEqualTo(Integer value) {
            addCriterion("notice_flg <>", value, "noticeFlg");
            return (Criteria) this;
        }

        public Criteria andNoticeFlgGreaterThan(Integer value) {
            addCriterion("notice_flg >", value, "noticeFlg");
            return (Criteria) this;
        }

        public Criteria andNoticeFlgGreaterThanOrEqualTo(Integer value) {
            addCriterion("notice_flg >=", value, "noticeFlg");
            return (Criteria) this;
        }

        public Criteria andNoticeFlgLessThan(Integer value) {
            addCriterion("notice_flg <", value, "noticeFlg");
            return (Criteria) this;
        }

        public Criteria andNoticeFlgLessThanOrEqualTo(Integer value) {
            addCriterion("notice_flg <=", value, "noticeFlg");
            return (Criteria) this;
        }

        public Criteria andNoticeFlgIn(List<Integer> values) {
            addCriterion("notice_flg in", values, "noticeFlg");
            return (Criteria) this;
        }

        public Criteria andNoticeFlgNotIn(List<Integer> values) {
            addCriterion("notice_flg not in", values, "noticeFlg");
            return (Criteria) this;
        }

        public Criteria andNoticeFlgBetween(Integer value1, Integer value2) {
            addCriterion("notice_flg between", value1, value2, "noticeFlg");
            return (Criteria) this;
        }

        public Criteria andNoticeFlgNotBetween(Integer value1, Integer value2) {
            addCriterion("notice_flg not between", value1, value2, "noticeFlg");
            return (Criteria) this;
        }

        public Criteria andExpTimeIsNull() {
            addCriterion("exp_time is null");
            return (Criteria) this;
        }

        public Criteria andExpTimeIsNotNull() {
            addCriterion("exp_time is not null");
            return (Criteria) this;
        }

        public Criteria andExpTimeEqualTo(Integer value) {
            addCriterion("exp_time =", value, "expTime");
            return (Criteria) this;
        }

        public Criteria andExpTimeNotEqualTo(Integer value) {
            addCriterion("exp_time <>", value, "expTime");
            return (Criteria) this;
        }

        public Criteria andExpTimeGreaterThan(Integer value) {
            addCriterion("exp_time >", value, "expTime");
            return (Criteria) this;
        }

        public Criteria andExpTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("exp_time >=", value, "expTime");
            return (Criteria) this;
        }

        public Criteria andExpTimeLessThan(Integer value) {
            addCriterion("exp_time <", value, "expTime");
            return (Criteria) this;
        }

        public Criteria andExpTimeLessThanOrEqualTo(Integer value) {
            addCriterion("exp_time <=", value, "expTime");
            return (Criteria) this;
        }

        public Criteria andExpTimeIn(List<Integer> values) {
            addCriterion("exp_time in", values, "expTime");
            return (Criteria) this;
        }

        public Criteria andExpTimeNotIn(List<Integer> values) {
            addCriterion("exp_time not in", values, "expTime");
            return (Criteria) this;
        }

        public Criteria andExpTimeBetween(Integer value1, Integer value2) {
            addCriterion("exp_time between", value1, value2, "expTime");
            return (Criteria) this;
        }

        public Criteria andExpTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("exp_time not between", value1, value2, "expTime");
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