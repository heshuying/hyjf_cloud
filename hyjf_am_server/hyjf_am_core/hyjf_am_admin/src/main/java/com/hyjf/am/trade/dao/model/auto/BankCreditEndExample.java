package com.hyjf.am.trade.dao.model.auto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BankCreditEndExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public BankCreditEndExample() {
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

        public Criteria andBatchNoIsNull() {
            addCriterion("batch_no is null");
            return (Criteria) this;
        }

        public Criteria andBatchNoIsNotNull() {
            addCriterion("batch_no is not null");
            return (Criteria) this;
        }

        public Criteria andBatchNoEqualTo(String value) {
            addCriterion("batch_no =", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoNotEqualTo(String value) {
            addCriterion("batch_no <>", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoGreaterThan(String value) {
            addCriterion("batch_no >", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoGreaterThanOrEqualTo(String value) {
            addCriterion("batch_no >=", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoLessThan(String value) {
            addCriterion("batch_no <", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoLessThanOrEqualTo(String value) {
            addCriterion("batch_no <=", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoLike(String value) {
            addCriterion("batch_no like", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoNotLike(String value) {
            addCriterion("batch_no not like", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoIn(List<String> values) {
            addCriterion("batch_no in", values, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoNotIn(List<String> values) {
            addCriterion("batch_no not in", values, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoBetween(String value1, String value2) {
            addCriterion("batch_no between", value1, value2, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoNotBetween(String value1, String value2) {
            addCriterion("batch_no not between", value1, value2, "batchNo");
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

        public Criteria andTxDateEqualTo(String value) {
            addCriterion("tx_date =", value, "txDate");
            return (Criteria) this;
        }

        public Criteria andTxDateNotEqualTo(String value) {
            addCriterion("tx_date <>", value, "txDate");
            return (Criteria) this;
        }

        public Criteria andTxDateGreaterThan(String value) {
            addCriterion("tx_date >", value, "txDate");
            return (Criteria) this;
        }

        public Criteria andTxDateGreaterThanOrEqualTo(String value) {
            addCriterion("tx_date >=", value, "txDate");
            return (Criteria) this;
        }

        public Criteria andTxDateLessThan(String value) {
            addCriterion("tx_date <", value, "txDate");
            return (Criteria) this;
        }

        public Criteria andTxDateLessThanOrEqualTo(String value) {
            addCriterion("tx_date <=", value, "txDate");
            return (Criteria) this;
        }

        public Criteria andTxDateLike(String value) {
            addCriterion("tx_date like", value, "txDate");
            return (Criteria) this;
        }

        public Criteria andTxDateNotLike(String value) {
            addCriterion("tx_date not like", value, "txDate");
            return (Criteria) this;
        }

        public Criteria andTxDateIn(List<String> values) {
            addCriterion("tx_date in", values, "txDate");
            return (Criteria) this;
        }

        public Criteria andTxDateNotIn(List<String> values) {
            addCriterion("tx_date not in", values, "txDate");
            return (Criteria) this;
        }

        public Criteria andTxDateBetween(String value1, String value2) {
            addCriterion("tx_date between", value1, value2, "txDate");
            return (Criteria) this;
        }

        public Criteria andTxDateNotBetween(String value1, String value2) {
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

        public Criteria andTxTimeEqualTo(String value) {
            addCriterion("tx_time =", value, "txTime");
            return (Criteria) this;
        }

        public Criteria andTxTimeNotEqualTo(String value) {
            addCriterion("tx_time <>", value, "txTime");
            return (Criteria) this;
        }

        public Criteria andTxTimeGreaterThan(String value) {
            addCriterion("tx_time >", value, "txTime");
            return (Criteria) this;
        }

        public Criteria andTxTimeGreaterThanOrEqualTo(String value) {
            addCriterion("tx_time >=", value, "txTime");
            return (Criteria) this;
        }

        public Criteria andTxTimeLessThan(String value) {
            addCriterion("tx_time <", value, "txTime");
            return (Criteria) this;
        }

        public Criteria andTxTimeLessThanOrEqualTo(String value) {
            addCriterion("tx_time <=", value, "txTime");
            return (Criteria) this;
        }

        public Criteria andTxTimeLike(String value) {
            addCriterion("tx_time like", value, "txTime");
            return (Criteria) this;
        }

        public Criteria andTxTimeNotLike(String value) {
            addCriterion("tx_time not like", value, "txTime");
            return (Criteria) this;
        }

        public Criteria andTxTimeIn(List<String> values) {
            addCriterion("tx_time in", values, "txTime");
            return (Criteria) this;
        }

        public Criteria andTxTimeNotIn(List<String> values) {
            addCriterion("tx_time not in", values, "txTime");
            return (Criteria) this;
        }

        public Criteria andTxTimeBetween(String value1, String value2) {
            addCriterion("tx_time between", value1, value2, "txTime");
            return (Criteria) this;
        }

        public Criteria andTxTimeNotBetween(String value1, String value2) {
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

        public Criteria andTxCountsIsNull() {
            addCriterion("tx_counts is null");
            return (Criteria) this;
        }

        public Criteria andTxCountsIsNotNull() {
            addCriterion("tx_counts is not null");
            return (Criteria) this;
        }

        public Criteria andTxCountsEqualTo(Integer value) {
            addCriterion("tx_counts =", value, "txCounts");
            return (Criteria) this;
        }

        public Criteria andTxCountsNotEqualTo(Integer value) {
            addCriterion("tx_counts <>", value, "txCounts");
            return (Criteria) this;
        }

        public Criteria andTxCountsGreaterThan(Integer value) {
            addCriterion("tx_counts >", value, "txCounts");
            return (Criteria) this;
        }

        public Criteria andTxCountsGreaterThanOrEqualTo(Integer value) {
            addCriterion("tx_counts >=", value, "txCounts");
            return (Criteria) this;
        }

        public Criteria andTxCountsLessThan(Integer value) {
            addCriterion("tx_counts <", value, "txCounts");
            return (Criteria) this;
        }

        public Criteria andTxCountsLessThanOrEqualTo(Integer value) {
            addCriterion("tx_counts <=", value, "txCounts");
            return (Criteria) this;
        }

        public Criteria andTxCountsIn(List<Integer> values) {
            addCriterion("tx_counts in", values, "txCounts");
            return (Criteria) this;
        }

        public Criteria andTxCountsNotIn(List<Integer> values) {
            addCriterion("tx_counts not in", values, "txCounts");
            return (Criteria) this;
        }

        public Criteria andTxCountsBetween(Integer value1, Integer value2) {
            addCriterion("tx_counts between", value1, value2, "txCounts");
            return (Criteria) this;
        }

        public Criteria andTxCountsNotBetween(Integer value1, Integer value2) {
            addCriterion("tx_counts not between", value1, value2, "txCounts");
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

        public Criteria andUsernameIsNull() {
            addCriterion("username is null");
            return (Criteria) this;
        }

        public Criteria andUsernameIsNotNull() {
            addCriterion("username is not null");
            return (Criteria) this;
        }

        public Criteria andUsernameEqualTo(String value) {
            addCriterion("username =", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotEqualTo(String value) {
            addCriterion("username <>", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameGreaterThan(String value) {
            addCriterion("username >", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameGreaterThanOrEqualTo(String value) {
            addCriterion("username >=", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLessThan(String value) {
            addCriterion("username <", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLessThanOrEqualTo(String value) {
            addCriterion("username <=", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLike(String value) {
            addCriterion("username like", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotLike(String value) {
            addCriterion("username not like", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameIn(List<String> values) {
            addCriterion("username in", values, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotIn(List<String> values) {
            addCriterion("username not in", values, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameBetween(String value1, String value2) {
            addCriterion("username between", value1, value2, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotBetween(String value1, String value2) {
            addCriterion("username not between", value1, value2, "username");
            return (Criteria) this;
        }

        public Criteria andTenderUserIdIsNull() {
            addCriterion("tender_user_id is null");
            return (Criteria) this;
        }

        public Criteria andTenderUserIdIsNotNull() {
            addCriterion("tender_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andTenderUserIdEqualTo(Integer value) {
            addCriterion("tender_user_id =", value, "tenderUserId");
            return (Criteria) this;
        }

        public Criteria andTenderUserIdNotEqualTo(Integer value) {
            addCriterion("tender_user_id <>", value, "tenderUserId");
            return (Criteria) this;
        }

        public Criteria andTenderUserIdGreaterThan(Integer value) {
            addCriterion("tender_user_id >", value, "tenderUserId");
            return (Criteria) this;
        }

        public Criteria andTenderUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("tender_user_id >=", value, "tenderUserId");
            return (Criteria) this;
        }

        public Criteria andTenderUserIdLessThan(Integer value) {
            addCriterion("tender_user_id <", value, "tenderUserId");
            return (Criteria) this;
        }

        public Criteria andTenderUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("tender_user_id <=", value, "tenderUserId");
            return (Criteria) this;
        }

        public Criteria andTenderUserIdIn(List<Integer> values) {
            addCriterion("tender_user_id in", values, "tenderUserId");
            return (Criteria) this;
        }

        public Criteria andTenderUserIdNotIn(List<Integer> values) {
            addCriterion("tender_user_id not in", values, "tenderUserId");
            return (Criteria) this;
        }

        public Criteria andTenderUserIdBetween(Integer value1, Integer value2) {
            addCriterion("tender_user_id between", value1, value2, "tenderUserId");
            return (Criteria) this;
        }

        public Criteria andTenderUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("tender_user_id not between", value1, value2, "tenderUserId");
            return (Criteria) this;
        }

        public Criteria andTenderUsernameIsNull() {
            addCriterion("tender_username is null");
            return (Criteria) this;
        }

        public Criteria andTenderUsernameIsNotNull() {
            addCriterion("tender_username is not null");
            return (Criteria) this;
        }

        public Criteria andTenderUsernameEqualTo(String value) {
            addCriterion("tender_username =", value, "tenderUsername");
            return (Criteria) this;
        }

        public Criteria andTenderUsernameNotEqualTo(String value) {
            addCriterion("tender_username <>", value, "tenderUsername");
            return (Criteria) this;
        }

        public Criteria andTenderUsernameGreaterThan(String value) {
            addCriterion("tender_username >", value, "tenderUsername");
            return (Criteria) this;
        }

        public Criteria andTenderUsernameGreaterThanOrEqualTo(String value) {
            addCriterion("tender_username >=", value, "tenderUsername");
            return (Criteria) this;
        }

        public Criteria andTenderUsernameLessThan(String value) {
            addCriterion("tender_username <", value, "tenderUsername");
            return (Criteria) this;
        }

        public Criteria andTenderUsernameLessThanOrEqualTo(String value) {
            addCriterion("tender_username <=", value, "tenderUsername");
            return (Criteria) this;
        }

        public Criteria andTenderUsernameLike(String value) {
            addCriterion("tender_username like", value, "tenderUsername");
            return (Criteria) this;
        }

        public Criteria andTenderUsernameNotLike(String value) {
            addCriterion("tender_username not like", value, "tenderUsername");
            return (Criteria) this;
        }

        public Criteria andTenderUsernameIn(List<String> values) {
            addCriterion("tender_username in", values, "tenderUsername");
            return (Criteria) this;
        }

        public Criteria andTenderUsernameNotIn(List<String> values) {
            addCriterion("tender_username not in", values, "tenderUsername");
            return (Criteria) this;
        }

        public Criteria andTenderUsernameBetween(String value1, String value2) {
            addCriterion("tender_username between", value1, value2, "tenderUsername");
            return (Criteria) this;
        }

        public Criteria andTenderUsernameNotBetween(String value1, String value2) {
            addCriterion("tender_username not between", value1, value2, "tenderUsername");
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

        public Criteria andTenderAccountIdIsNull() {
            addCriterion("tender_account_id is null");
            return (Criteria) this;
        }

        public Criteria andTenderAccountIdIsNotNull() {
            addCriterion("tender_account_id is not null");
            return (Criteria) this;
        }

        public Criteria andTenderAccountIdEqualTo(String value) {
            addCriterion("tender_account_id =", value, "tenderAccountId");
            return (Criteria) this;
        }

        public Criteria andTenderAccountIdNotEqualTo(String value) {
            addCriterion("tender_account_id <>", value, "tenderAccountId");
            return (Criteria) this;
        }

        public Criteria andTenderAccountIdGreaterThan(String value) {
            addCriterion("tender_account_id >", value, "tenderAccountId");
            return (Criteria) this;
        }

        public Criteria andTenderAccountIdGreaterThanOrEqualTo(String value) {
            addCriterion("tender_account_id >=", value, "tenderAccountId");
            return (Criteria) this;
        }

        public Criteria andTenderAccountIdLessThan(String value) {
            addCriterion("tender_account_id <", value, "tenderAccountId");
            return (Criteria) this;
        }

        public Criteria andTenderAccountIdLessThanOrEqualTo(String value) {
            addCriterion("tender_account_id <=", value, "tenderAccountId");
            return (Criteria) this;
        }

        public Criteria andTenderAccountIdLike(String value) {
            addCriterion("tender_account_id like", value, "tenderAccountId");
            return (Criteria) this;
        }

        public Criteria andTenderAccountIdNotLike(String value) {
            addCriterion("tender_account_id not like", value, "tenderAccountId");
            return (Criteria) this;
        }

        public Criteria andTenderAccountIdIn(List<String> values) {
            addCriterion("tender_account_id in", values, "tenderAccountId");
            return (Criteria) this;
        }

        public Criteria andTenderAccountIdNotIn(List<String> values) {
            addCriterion("tender_account_id not in", values, "tenderAccountId");
            return (Criteria) this;
        }

        public Criteria andTenderAccountIdBetween(String value1, String value2) {
            addCriterion("tender_account_id between", value1, value2, "tenderAccountId");
            return (Criteria) this;
        }

        public Criteria andTenderAccountIdNotBetween(String value1, String value2) {
            addCriterion("tender_account_id not between", value1, value2, "tenderAccountId");
            return (Criteria) this;
        }

        public Criteria andOrderIdIsNull() {
            addCriterion("order_id is null");
            return (Criteria) this;
        }

        public Criteria andOrderIdIsNotNull() {
            addCriterion("order_id is not null");
            return (Criteria) this;
        }

        public Criteria andOrderIdEqualTo(String value) {
            addCriterion("order_id =", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotEqualTo(String value) {
            addCriterion("order_id <>", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThan(String value) {
            addCriterion("order_id >", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThanOrEqualTo(String value) {
            addCriterion("order_id >=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThan(String value) {
            addCriterion("order_id <", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThanOrEqualTo(String value) {
            addCriterion("order_id <=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLike(String value) {
            addCriterion("order_id like", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotLike(String value) {
            addCriterion("order_id not like", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdIn(List<String> values) {
            addCriterion("order_id in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotIn(List<String> values) {
            addCriterion("order_id not in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdBetween(String value1, String value2) {
            addCriterion("order_id between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotBetween(String value1, String value2) {
            addCriterion("order_id not between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrgOrderIdIsNull() {
            addCriterion("org_order_id is null");
            return (Criteria) this;
        }

        public Criteria andOrgOrderIdIsNotNull() {
            addCriterion("org_order_id is not null");
            return (Criteria) this;
        }

        public Criteria andOrgOrderIdEqualTo(String value) {
            addCriterion("org_order_id =", value, "orgOrderId");
            return (Criteria) this;
        }

        public Criteria andOrgOrderIdNotEqualTo(String value) {
            addCriterion("org_order_id <>", value, "orgOrderId");
            return (Criteria) this;
        }

        public Criteria andOrgOrderIdGreaterThan(String value) {
            addCriterion("org_order_id >", value, "orgOrderId");
            return (Criteria) this;
        }

        public Criteria andOrgOrderIdGreaterThanOrEqualTo(String value) {
            addCriterion("org_order_id >=", value, "orgOrderId");
            return (Criteria) this;
        }

        public Criteria andOrgOrderIdLessThan(String value) {
            addCriterion("org_order_id <", value, "orgOrderId");
            return (Criteria) this;
        }

        public Criteria andOrgOrderIdLessThanOrEqualTo(String value) {
            addCriterion("org_order_id <=", value, "orgOrderId");
            return (Criteria) this;
        }

        public Criteria andOrgOrderIdLike(String value) {
            addCriterion("org_order_id like", value, "orgOrderId");
            return (Criteria) this;
        }

        public Criteria andOrgOrderIdNotLike(String value) {
            addCriterion("org_order_id not like", value, "orgOrderId");
            return (Criteria) this;
        }

        public Criteria andOrgOrderIdIn(List<String> values) {
            addCriterion("org_order_id in", values, "orgOrderId");
            return (Criteria) this;
        }

        public Criteria andOrgOrderIdNotIn(List<String> values) {
            addCriterion("org_order_id not in", values, "orgOrderId");
            return (Criteria) this;
        }

        public Criteria andOrgOrderIdBetween(String value1, String value2) {
            addCriterion("org_order_id between", value1, value2, "orgOrderId");
            return (Criteria) this;
        }

        public Criteria andOrgOrderIdNotBetween(String value1, String value2) {
            addCriterion("org_order_id not between", value1, value2, "orgOrderId");
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

        public Criteria andAuthCodeIsNull() {
            addCriterion("auth_code is null");
            return (Criteria) this;
        }

        public Criteria andAuthCodeIsNotNull() {
            addCriterion("auth_code is not null");
            return (Criteria) this;
        }

        public Criteria andAuthCodeEqualTo(String value) {
            addCriterion("auth_code =", value, "authCode");
            return (Criteria) this;
        }

        public Criteria andAuthCodeNotEqualTo(String value) {
            addCriterion("auth_code <>", value, "authCode");
            return (Criteria) this;
        }

        public Criteria andAuthCodeGreaterThan(String value) {
            addCriterion("auth_code >", value, "authCode");
            return (Criteria) this;
        }

        public Criteria andAuthCodeGreaterThanOrEqualTo(String value) {
            addCriterion("auth_code >=", value, "authCode");
            return (Criteria) this;
        }

        public Criteria andAuthCodeLessThan(String value) {
            addCriterion("auth_code <", value, "authCode");
            return (Criteria) this;
        }

        public Criteria andAuthCodeLessThanOrEqualTo(String value) {
            addCriterion("auth_code <=", value, "authCode");
            return (Criteria) this;
        }

        public Criteria andAuthCodeLike(String value) {
            addCriterion("auth_code like", value, "authCode");
            return (Criteria) this;
        }

        public Criteria andAuthCodeNotLike(String value) {
            addCriterion("auth_code not like", value, "authCode");
            return (Criteria) this;
        }

        public Criteria andAuthCodeIn(List<String> values) {
            addCriterion("auth_code in", values, "authCode");
            return (Criteria) this;
        }

        public Criteria andAuthCodeNotIn(List<String> values) {
            addCriterion("auth_code not in", values, "authCode");
            return (Criteria) this;
        }

        public Criteria andAuthCodeBetween(String value1, String value2) {
            addCriterion("auth_code between", value1, value2, "authCode");
            return (Criteria) this;
        }

        public Criteria andAuthCodeNotBetween(String value1, String value2) {
            addCriterion("auth_code not between", value1, value2, "authCode");
            return (Criteria) this;
        }

        public Criteria andCreditEndTypeIsNull() {
            addCriterion("credit_end_type is null");
            return (Criteria) this;
        }

        public Criteria andCreditEndTypeIsNotNull() {
            addCriterion("credit_end_type is not null");
            return (Criteria) this;
        }

        public Criteria andCreditEndTypeEqualTo(Integer value) {
            addCriterion("credit_end_type =", value, "creditEndType");
            return (Criteria) this;
        }

        public Criteria andCreditEndTypeNotEqualTo(Integer value) {
            addCriterion("credit_end_type <>", value, "creditEndType");
            return (Criteria) this;
        }

        public Criteria andCreditEndTypeGreaterThan(Integer value) {
            addCriterion("credit_end_type >", value, "creditEndType");
            return (Criteria) this;
        }

        public Criteria andCreditEndTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("credit_end_type >=", value, "creditEndType");
            return (Criteria) this;
        }

        public Criteria andCreditEndTypeLessThan(Integer value) {
            addCriterion("credit_end_type <", value, "creditEndType");
            return (Criteria) this;
        }

        public Criteria andCreditEndTypeLessThanOrEqualTo(Integer value) {
            addCriterion("credit_end_type <=", value, "creditEndType");
            return (Criteria) this;
        }

        public Criteria andCreditEndTypeIn(List<Integer> values) {
            addCriterion("credit_end_type in", values, "creditEndType");
            return (Criteria) this;
        }

        public Criteria andCreditEndTypeNotIn(List<Integer> values) {
            addCriterion("credit_end_type not in", values, "creditEndType");
            return (Criteria) this;
        }

        public Criteria andCreditEndTypeBetween(Integer value1, Integer value2) {
            addCriterion("credit_end_type between", value1, value2, "creditEndType");
            return (Criteria) this;
        }

        public Criteria andCreditEndTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("credit_end_type not between", value1, value2, "creditEndType");
            return (Criteria) this;
        }

        public Criteria andReceivedIsNull() {
            addCriterion("received is null");
            return (Criteria) this;
        }

        public Criteria andReceivedIsNotNull() {
            addCriterion("received is not null");
            return (Criteria) this;
        }

        public Criteria andReceivedEqualTo(Integer value) {
            addCriterion("received =", value, "received");
            return (Criteria) this;
        }

        public Criteria andReceivedNotEqualTo(Integer value) {
            addCriterion("received <>", value, "received");
            return (Criteria) this;
        }

        public Criteria andReceivedGreaterThan(Integer value) {
            addCriterion("received >", value, "received");
            return (Criteria) this;
        }

        public Criteria andReceivedGreaterThanOrEqualTo(Integer value) {
            addCriterion("received >=", value, "received");
            return (Criteria) this;
        }

        public Criteria andReceivedLessThan(Integer value) {
            addCriterion("received <", value, "received");
            return (Criteria) this;
        }

        public Criteria andReceivedLessThanOrEqualTo(Integer value) {
            addCriterion("received <=", value, "received");
            return (Criteria) this;
        }

        public Criteria andReceivedIn(List<Integer> values) {
            addCriterion("received in", values, "received");
            return (Criteria) this;
        }

        public Criteria andReceivedNotIn(List<Integer> values) {
            addCriterion("received not in", values, "received");
            return (Criteria) this;
        }

        public Criteria andReceivedBetween(Integer value1, Integer value2) {
            addCriterion("received between", value1, value2, "received");
            return (Criteria) this;
        }

        public Criteria andReceivedNotBetween(Integer value1, Integer value2) {
            addCriterion("received not between", value1, value2, "received");
            return (Criteria) this;
        }

        public Criteria andCheckRetcodeIsNull() {
            addCriterion("check_retcode is null");
            return (Criteria) this;
        }

        public Criteria andCheckRetcodeIsNotNull() {
            addCriterion("check_retcode is not null");
            return (Criteria) this;
        }

        public Criteria andCheckRetcodeEqualTo(String value) {
            addCriterion("check_retcode =", value, "checkRetcode");
            return (Criteria) this;
        }

        public Criteria andCheckRetcodeNotEqualTo(String value) {
            addCriterion("check_retcode <>", value, "checkRetcode");
            return (Criteria) this;
        }

        public Criteria andCheckRetcodeGreaterThan(String value) {
            addCriterion("check_retcode >", value, "checkRetcode");
            return (Criteria) this;
        }

        public Criteria andCheckRetcodeGreaterThanOrEqualTo(String value) {
            addCriterion("check_retcode >=", value, "checkRetcode");
            return (Criteria) this;
        }

        public Criteria andCheckRetcodeLessThan(String value) {
            addCriterion("check_retcode <", value, "checkRetcode");
            return (Criteria) this;
        }

        public Criteria andCheckRetcodeLessThanOrEqualTo(String value) {
            addCriterion("check_retcode <=", value, "checkRetcode");
            return (Criteria) this;
        }

        public Criteria andCheckRetcodeLike(String value) {
            addCriterion("check_retcode like", value, "checkRetcode");
            return (Criteria) this;
        }

        public Criteria andCheckRetcodeNotLike(String value) {
            addCriterion("check_retcode not like", value, "checkRetcode");
            return (Criteria) this;
        }

        public Criteria andCheckRetcodeIn(List<String> values) {
            addCriterion("check_retcode in", values, "checkRetcode");
            return (Criteria) this;
        }

        public Criteria andCheckRetcodeNotIn(List<String> values) {
            addCriterion("check_retcode not in", values, "checkRetcode");
            return (Criteria) this;
        }

        public Criteria andCheckRetcodeBetween(String value1, String value2) {
            addCriterion("check_retcode between", value1, value2, "checkRetcode");
            return (Criteria) this;
        }

        public Criteria andCheckRetcodeNotBetween(String value1, String value2) {
            addCriterion("check_retcode not between", value1, value2, "checkRetcode");
            return (Criteria) this;
        }

        public Criteria andCheckRetmsgIsNull() {
            addCriterion("check_retmsg is null");
            return (Criteria) this;
        }

        public Criteria andCheckRetmsgIsNotNull() {
            addCriterion("check_retmsg is not null");
            return (Criteria) this;
        }

        public Criteria andCheckRetmsgEqualTo(String value) {
            addCriterion("check_retmsg =", value, "checkRetmsg");
            return (Criteria) this;
        }

        public Criteria andCheckRetmsgNotEqualTo(String value) {
            addCriterion("check_retmsg <>", value, "checkRetmsg");
            return (Criteria) this;
        }

        public Criteria andCheckRetmsgGreaterThan(String value) {
            addCriterion("check_retmsg >", value, "checkRetmsg");
            return (Criteria) this;
        }

        public Criteria andCheckRetmsgGreaterThanOrEqualTo(String value) {
            addCriterion("check_retmsg >=", value, "checkRetmsg");
            return (Criteria) this;
        }

        public Criteria andCheckRetmsgLessThan(String value) {
            addCriterion("check_retmsg <", value, "checkRetmsg");
            return (Criteria) this;
        }

        public Criteria andCheckRetmsgLessThanOrEqualTo(String value) {
            addCriterion("check_retmsg <=", value, "checkRetmsg");
            return (Criteria) this;
        }

        public Criteria andCheckRetmsgLike(String value) {
            addCriterion("check_retmsg like", value, "checkRetmsg");
            return (Criteria) this;
        }

        public Criteria andCheckRetmsgNotLike(String value) {
            addCriterion("check_retmsg not like", value, "checkRetmsg");
            return (Criteria) this;
        }

        public Criteria andCheckRetmsgIn(List<String> values) {
            addCriterion("check_retmsg in", values, "checkRetmsg");
            return (Criteria) this;
        }

        public Criteria andCheckRetmsgNotIn(List<String> values) {
            addCriterion("check_retmsg not in", values, "checkRetmsg");
            return (Criteria) this;
        }

        public Criteria andCheckRetmsgBetween(String value1, String value2) {
            addCriterion("check_retmsg between", value1, value2, "checkRetmsg");
            return (Criteria) this;
        }

        public Criteria andCheckRetmsgNotBetween(String value1, String value2) {
            addCriterion("check_retmsg not between", value1, value2, "checkRetmsg");
            return (Criteria) this;
        }

        public Criteria andRetcodeIsNull() {
            addCriterion("retcode is null");
            return (Criteria) this;
        }

        public Criteria andRetcodeIsNotNull() {
            addCriterion("retcode is not null");
            return (Criteria) this;
        }

        public Criteria andRetcodeEqualTo(String value) {
            addCriterion("retcode =", value, "retcode");
            return (Criteria) this;
        }

        public Criteria andRetcodeNotEqualTo(String value) {
            addCriterion("retcode <>", value, "retcode");
            return (Criteria) this;
        }

        public Criteria andRetcodeGreaterThan(String value) {
            addCriterion("retcode >", value, "retcode");
            return (Criteria) this;
        }

        public Criteria andRetcodeGreaterThanOrEqualTo(String value) {
            addCriterion("retcode >=", value, "retcode");
            return (Criteria) this;
        }

        public Criteria andRetcodeLessThan(String value) {
            addCriterion("retcode <", value, "retcode");
            return (Criteria) this;
        }

        public Criteria andRetcodeLessThanOrEqualTo(String value) {
            addCriterion("retcode <=", value, "retcode");
            return (Criteria) this;
        }

        public Criteria andRetcodeLike(String value) {
            addCriterion("retcode like", value, "retcode");
            return (Criteria) this;
        }

        public Criteria andRetcodeNotLike(String value) {
            addCriterion("retcode not like", value, "retcode");
            return (Criteria) this;
        }

        public Criteria andRetcodeIn(List<String> values) {
            addCriterion("retcode in", values, "retcode");
            return (Criteria) this;
        }

        public Criteria andRetcodeNotIn(List<String> values) {
            addCriterion("retcode not in", values, "retcode");
            return (Criteria) this;
        }

        public Criteria andRetcodeBetween(String value1, String value2) {
            addCriterion("retcode between", value1, value2, "retcode");
            return (Criteria) this;
        }

        public Criteria andRetcodeNotBetween(String value1, String value2) {
            addCriterion("retcode not between", value1, value2, "retcode");
            return (Criteria) this;
        }

        public Criteria andRetmsgIsNull() {
            addCriterion("retmsg is null");
            return (Criteria) this;
        }

        public Criteria andRetmsgIsNotNull() {
            addCriterion("retmsg is not null");
            return (Criteria) this;
        }

        public Criteria andRetmsgEqualTo(String value) {
            addCriterion("retmsg =", value, "retmsg");
            return (Criteria) this;
        }

        public Criteria andRetmsgNotEqualTo(String value) {
            addCriterion("retmsg <>", value, "retmsg");
            return (Criteria) this;
        }

        public Criteria andRetmsgGreaterThan(String value) {
            addCriterion("retmsg >", value, "retmsg");
            return (Criteria) this;
        }

        public Criteria andRetmsgGreaterThanOrEqualTo(String value) {
            addCriterion("retmsg >=", value, "retmsg");
            return (Criteria) this;
        }

        public Criteria andRetmsgLessThan(String value) {
            addCriterion("retmsg <", value, "retmsg");
            return (Criteria) this;
        }

        public Criteria andRetmsgLessThanOrEqualTo(String value) {
            addCriterion("retmsg <=", value, "retmsg");
            return (Criteria) this;
        }

        public Criteria andRetmsgLike(String value) {
            addCriterion("retmsg like", value, "retmsg");
            return (Criteria) this;
        }

        public Criteria andRetmsgNotLike(String value) {
            addCriterion("retmsg not like", value, "retmsg");
            return (Criteria) this;
        }

        public Criteria andRetmsgIn(List<String> values) {
            addCriterion("retmsg in", values, "retmsg");
            return (Criteria) this;
        }

        public Criteria andRetmsgNotIn(List<String> values) {
            addCriterion("retmsg not in", values, "retmsg");
            return (Criteria) this;
        }

        public Criteria andRetmsgBetween(String value1, String value2) {
            addCriterion("retmsg between", value1, value2, "retmsg");
            return (Criteria) this;
        }

        public Criteria andRetmsgNotBetween(String value1, String value2) {
            addCriterion("retmsg not between", value1, value2, "retmsg");
            return (Criteria) this;
        }

        public Criteria andSucCountsIsNull() {
            addCriterion("suc_counts is null");
            return (Criteria) this;
        }

        public Criteria andSucCountsIsNotNull() {
            addCriterion("suc_counts is not null");
            return (Criteria) this;
        }

        public Criteria andSucCountsEqualTo(Integer value) {
            addCriterion("suc_counts =", value, "sucCounts");
            return (Criteria) this;
        }

        public Criteria andSucCountsNotEqualTo(Integer value) {
            addCriterion("suc_counts <>", value, "sucCounts");
            return (Criteria) this;
        }

        public Criteria andSucCountsGreaterThan(Integer value) {
            addCriterion("suc_counts >", value, "sucCounts");
            return (Criteria) this;
        }

        public Criteria andSucCountsGreaterThanOrEqualTo(Integer value) {
            addCriterion("suc_counts >=", value, "sucCounts");
            return (Criteria) this;
        }

        public Criteria andSucCountsLessThan(Integer value) {
            addCriterion("suc_counts <", value, "sucCounts");
            return (Criteria) this;
        }

        public Criteria andSucCountsLessThanOrEqualTo(Integer value) {
            addCriterion("suc_counts <=", value, "sucCounts");
            return (Criteria) this;
        }

        public Criteria andSucCountsIn(List<Integer> values) {
            addCriterion("suc_counts in", values, "sucCounts");
            return (Criteria) this;
        }

        public Criteria andSucCountsNotIn(List<Integer> values) {
            addCriterion("suc_counts not in", values, "sucCounts");
            return (Criteria) this;
        }

        public Criteria andSucCountsBetween(Integer value1, Integer value2) {
            addCriterion("suc_counts between", value1, value2, "sucCounts");
            return (Criteria) this;
        }

        public Criteria andSucCountsNotBetween(Integer value1, Integer value2) {
            addCriterion("suc_counts not between", value1, value2, "sucCounts");
            return (Criteria) this;
        }

        public Criteria andFailCountsIsNull() {
            addCriterion("fail_counts is null");
            return (Criteria) this;
        }

        public Criteria andFailCountsIsNotNull() {
            addCriterion("fail_counts is not null");
            return (Criteria) this;
        }

        public Criteria andFailCountsEqualTo(Integer value) {
            addCriterion("fail_counts =", value, "failCounts");
            return (Criteria) this;
        }

        public Criteria andFailCountsNotEqualTo(Integer value) {
            addCriterion("fail_counts <>", value, "failCounts");
            return (Criteria) this;
        }

        public Criteria andFailCountsGreaterThan(Integer value) {
            addCriterion("fail_counts >", value, "failCounts");
            return (Criteria) this;
        }

        public Criteria andFailCountsGreaterThanOrEqualTo(Integer value) {
            addCriterion("fail_counts >=", value, "failCounts");
            return (Criteria) this;
        }

        public Criteria andFailCountsLessThan(Integer value) {
            addCriterion("fail_counts <", value, "failCounts");
            return (Criteria) this;
        }

        public Criteria andFailCountsLessThanOrEqualTo(Integer value) {
            addCriterion("fail_counts <=", value, "failCounts");
            return (Criteria) this;
        }

        public Criteria andFailCountsIn(List<Integer> values) {
            addCriterion("fail_counts in", values, "failCounts");
            return (Criteria) this;
        }

        public Criteria andFailCountsNotIn(List<Integer> values) {
            addCriterion("fail_counts not in", values, "failCounts");
            return (Criteria) this;
        }

        public Criteria andFailCountsBetween(Integer value1, Integer value2) {
            addCriterion("fail_counts between", value1, value2, "failCounts");
            return (Criteria) this;
        }

        public Criteria andFailCountsNotBetween(Integer value1, Integer value2) {
            addCriterion("fail_counts not between", value1, value2, "failCounts");
            return (Criteria) this;
        }

        public Criteria andStateIsNull() {
            addCriterion("`state` is null");
            return (Criteria) this;
        }

        public Criteria andStateIsNotNull() {
            addCriterion("`state` is not null");
            return (Criteria) this;
        }

        public Criteria andStateEqualTo(String value) {
            addCriterion("`state` =", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotEqualTo(String value) {
            addCriterion("`state` <>", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThan(String value) {
            addCriterion("`state` >", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThanOrEqualTo(String value) {
            addCriterion("`state` >=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThan(String value) {
            addCriterion("`state` <", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThanOrEqualTo(String value) {
            addCriterion("`state` <=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLike(String value) {
            addCriterion("`state` like", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotLike(String value) {
            addCriterion("`state` not like", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateIn(List<String> values) {
            addCriterion("`state` in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotIn(List<String> values) {
            addCriterion("`state` not in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateBetween(String value1, String value2) {
            addCriterion("`state` between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotBetween(String value1, String value2) {
            addCriterion("`state` not between", value1, value2, "state");
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

        public Criteria andFailmsgIsNull() {
            addCriterion("failmsg is null");
            return (Criteria) this;
        }

        public Criteria andFailmsgIsNotNull() {
            addCriterion("failmsg is not null");
            return (Criteria) this;
        }

        public Criteria andFailmsgEqualTo(String value) {
            addCriterion("failmsg =", value, "failmsg");
            return (Criteria) this;
        }

        public Criteria andFailmsgNotEqualTo(String value) {
            addCriterion("failmsg <>", value, "failmsg");
            return (Criteria) this;
        }

        public Criteria andFailmsgGreaterThan(String value) {
            addCriterion("failmsg >", value, "failmsg");
            return (Criteria) this;
        }

        public Criteria andFailmsgGreaterThanOrEqualTo(String value) {
            addCriterion("failmsg >=", value, "failmsg");
            return (Criteria) this;
        }

        public Criteria andFailmsgLessThan(String value) {
            addCriterion("failmsg <", value, "failmsg");
            return (Criteria) this;
        }

        public Criteria andFailmsgLessThanOrEqualTo(String value) {
            addCriterion("failmsg <=", value, "failmsg");
            return (Criteria) this;
        }

        public Criteria andFailmsgLike(String value) {
            addCriterion("failmsg like", value, "failmsg");
            return (Criteria) this;
        }

        public Criteria andFailmsgNotLike(String value) {
            addCriterion("failmsg not like", value, "failmsg");
            return (Criteria) this;
        }

        public Criteria andFailmsgIn(List<String> values) {
            addCriterion("failmsg in", values, "failmsg");
            return (Criteria) this;
        }

        public Criteria andFailmsgNotIn(List<String> values) {
            addCriterion("failmsg not in", values, "failmsg");
            return (Criteria) this;
        }

        public Criteria andFailmsgBetween(String value1, String value2) {
            addCriterion("failmsg between", value1, value2, "failmsg");
            return (Criteria) this;
        }

        public Criteria andFailmsgNotBetween(String value1, String value2) {
            addCriterion("failmsg not between", value1, value2, "failmsg");
            return (Criteria) this;
        }

        public Criteria andCreateUserIsNull() {
            addCriterion("create_user is null");
            return (Criteria) this;
        }

        public Criteria andCreateUserIsNotNull() {
            addCriterion("create_user is not null");
            return (Criteria) this;
        }

        public Criteria andCreateUserEqualTo(Integer value) {
            addCriterion("create_user =", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotEqualTo(Integer value) {
            addCriterion("create_user <>", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserGreaterThan(Integer value) {
            addCriterion("create_user >", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserGreaterThanOrEqualTo(Integer value) {
            addCriterion("create_user >=", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLessThan(Integer value) {
            addCriterion("create_user <", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLessThanOrEqualTo(Integer value) {
            addCriterion("create_user <=", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserIn(List<Integer> values) {
            addCriterion("create_user in", values, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotIn(List<Integer> values) {
            addCriterion("create_user not in", values, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserBetween(Integer value1, Integer value2) {
            addCriterion("create_user between", value1, value2, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotBetween(Integer value1, Integer value2) {
            addCriterion("create_user not between", value1, value2, "createUser");
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

        public Criteria andUpdateUserIsNull() {
            addCriterion("update_user is null");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIsNotNull() {
            addCriterion("update_user is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateUserEqualTo(Integer value) {
            addCriterion("update_user =", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotEqualTo(Integer value) {
            addCriterion("update_user <>", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserGreaterThan(Integer value) {
            addCriterion("update_user >", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserGreaterThanOrEqualTo(Integer value) {
            addCriterion("update_user >=", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLessThan(Integer value) {
            addCriterion("update_user <", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLessThanOrEqualTo(Integer value) {
            addCriterion("update_user <=", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIn(List<Integer> values) {
            addCriterion("update_user in", values, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotIn(List<Integer> values) {
            addCriterion("update_user not in", values, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserBetween(Integer value1, Integer value2) {
            addCriterion("update_user between", value1, value2, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotBetween(Integer value1, Integer value2) {
            addCriterion("update_user not between", value1, value2, "updateUser");
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