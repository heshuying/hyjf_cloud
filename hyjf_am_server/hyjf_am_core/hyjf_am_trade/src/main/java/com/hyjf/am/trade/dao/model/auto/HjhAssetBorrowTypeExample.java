package com.hyjf.am.trade.dao.model.auto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HjhAssetBorrowTypeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public HjhAssetBorrowTypeExample() {
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

        public Criteria andInstCodeIsNull() {
            addCriterion("inst_code is null");
            return (Criteria) this;
        }

        public Criteria andInstCodeIsNotNull() {
            addCriterion("inst_code is not null");
            return (Criteria) this;
        }

        public Criteria andInstCodeEqualTo(String value) {
            addCriterion("inst_code =", value, "instCode");
            return (Criteria) this;
        }

        public Criteria andInstCodeNotEqualTo(String value) {
            addCriterion("inst_code <>", value, "instCode");
            return (Criteria) this;
        }

        public Criteria andInstCodeGreaterThan(String value) {
            addCriterion("inst_code >", value, "instCode");
            return (Criteria) this;
        }

        public Criteria andInstCodeGreaterThanOrEqualTo(String value) {
            addCriterion("inst_code >=", value, "instCode");
            return (Criteria) this;
        }

        public Criteria andInstCodeLessThan(String value) {
            addCriterion("inst_code <", value, "instCode");
            return (Criteria) this;
        }

        public Criteria andInstCodeLessThanOrEqualTo(String value) {
            addCriterion("inst_code <=", value, "instCode");
            return (Criteria) this;
        }

        public Criteria andInstCodeLike(String value) {
            addCriterion("inst_code like", value, "instCode");
            return (Criteria) this;
        }

        public Criteria andInstCodeNotLike(String value) {
            addCriterion("inst_code not like", value, "instCode");
            return (Criteria) this;
        }

        public Criteria andInstCodeIn(List<String> values) {
            addCriterion("inst_code in", values, "instCode");
            return (Criteria) this;
        }

        public Criteria andInstCodeNotIn(List<String> values) {
            addCriterion("inst_code not in", values, "instCode");
            return (Criteria) this;
        }

        public Criteria andInstCodeBetween(String value1, String value2) {
            addCriterion("inst_code between", value1, value2, "instCode");
            return (Criteria) this;
        }

        public Criteria andInstCodeNotBetween(String value1, String value2) {
            addCriterion("inst_code not between", value1, value2, "instCode");
            return (Criteria) this;
        }

        public Criteria andAssetTypeIsNull() {
            addCriterion("asset_type is null");
            return (Criteria) this;
        }

        public Criteria andAssetTypeIsNotNull() {
            addCriterion("asset_type is not null");
            return (Criteria) this;
        }

        public Criteria andAssetTypeEqualTo(Integer value) {
            addCriterion("asset_type =", value, "assetType");
            return (Criteria) this;
        }

        public Criteria andAssetTypeNotEqualTo(Integer value) {
            addCriterion("asset_type <>", value, "assetType");
            return (Criteria) this;
        }

        public Criteria andAssetTypeGreaterThan(Integer value) {
            addCriterion("asset_type >", value, "assetType");
            return (Criteria) this;
        }

        public Criteria andAssetTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("asset_type >=", value, "assetType");
            return (Criteria) this;
        }

        public Criteria andAssetTypeLessThan(Integer value) {
            addCriterion("asset_type <", value, "assetType");
            return (Criteria) this;
        }

        public Criteria andAssetTypeLessThanOrEqualTo(Integer value) {
            addCriterion("asset_type <=", value, "assetType");
            return (Criteria) this;
        }

        public Criteria andAssetTypeIn(List<Integer> values) {
            addCriterion("asset_type in", values, "assetType");
            return (Criteria) this;
        }

        public Criteria andAssetTypeNotIn(List<Integer> values) {
            addCriterion("asset_type not in", values, "assetType");
            return (Criteria) this;
        }

        public Criteria andAssetTypeBetween(Integer value1, Integer value2) {
            addCriterion("asset_type between", value1, value2, "assetType");
            return (Criteria) this;
        }

        public Criteria andAssetTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("asset_type not between", value1, value2, "assetType");
            return (Criteria) this;
        }

        public Criteria andBorrowCdIsNull() {
            addCriterion("borrow_cd is null");
            return (Criteria) this;
        }

        public Criteria andBorrowCdIsNotNull() {
            addCriterion("borrow_cd is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowCdEqualTo(Integer value) {
            addCriterion("borrow_cd =", value, "borrowCd");
            return (Criteria) this;
        }

        public Criteria andBorrowCdNotEqualTo(Integer value) {
            addCriterion("borrow_cd <>", value, "borrowCd");
            return (Criteria) this;
        }

        public Criteria andBorrowCdGreaterThan(Integer value) {
            addCriterion("borrow_cd >", value, "borrowCd");
            return (Criteria) this;
        }

        public Criteria andBorrowCdGreaterThanOrEqualTo(Integer value) {
            addCriterion("borrow_cd >=", value, "borrowCd");
            return (Criteria) this;
        }

        public Criteria andBorrowCdLessThan(Integer value) {
            addCriterion("borrow_cd <", value, "borrowCd");
            return (Criteria) this;
        }

        public Criteria andBorrowCdLessThanOrEqualTo(Integer value) {
            addCriterion("borrow_cd <=", value, "borrowCd");
            return (Criteria) this;
        }

        public Criteria andBorrowCdIn(List<Integer> values) {
            addCriterion("borrow_cd in", values, "borrowCd");
            return (Criteria) this;
        }

        public Criteria andBorrowCdNotIn(List<Integer> values) {
            addCriterion("borrow_cd not in", values, "borrowCd");
            return (Criteria) this;
        }

        public Criteria andBorrowCdBetween(Integer value1, Integer value2) {
            addCriterion("borrow_cd between", value1, value2, "borrowCd");
            return (Criteria) this;
        }

        public Criteria andBorrowCdNotBetween(Integer value1, Integer value2) {
            addCriterion("borrow_cd not between", value1, value2, "borrowCd");
            return (Criteria) this;
        }

        public Criteria andIsOpenIsNull() {
            addCriterion("is_open is null");
            return (Criteria) this;
        }

        public Criteria andIsOpenIsNotNull() {
            addCriterion("is_open is not null");
            return (Criteria) this;
        }

        public Criteria andIsOpenEqualTo(Integer value) {
            addCriterion("is_open =", value, "isOpen");
            return (Criteria) this;
        }

        public Criteria andIsOpenNotEqualTo(Integer value) {
            addCriterion("is_open <>", value, "isOpen");
            return (Criteria) this;
        }

        public Criteria andIsOpenGreaterThan(Integer value) {
            addCriterion("is_open >", value, "isOpen");
            return (Criteria) this;
        }

        public Criteria andIsOpenGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_open >=", value, "isOpen");
            return (Criteria) this;
        }

        public Criteria andIsOpenLessThan(Integer value) {
            addCriterion("is_open <", value, "isOpen");
            return (Criteria) this;
        }

        public Criteria andIsOpenLessThanOrEqualTo(Integer value) {
            addCriterion("is_open <=", value, "isOpen");
            return (Criteria) this;
        }

        public Criteria andIsOpenIn(List<Integer> values) {
            addCriterion("is_open in", values, "isOpen");
            return (Criteria) this;
        }

        public Criteria andIsOpenNotIn(List<Integer> values) {
            addCriterion("is_open not in", values, "isOpen");
            return (Criteria) this;
        }

        public Criteria andIsOpenBetween(Integer value1, Integer value2) {
            addCriterion("is_open between", value1, value2, "isOpen");
            return (Criteria) this;
        }

        public Criteria andIsOpenNotBetween(Integer value1, Integer value2) {
            addCriterion("is_open not between", value1, value2, "isOpen");
            return (Criteria) this;
        }

        public Criteria andAutoAddIsNull() {
            addCriterion("auto_add is null");
            return (Criteria) this;
        }

        public Criteria andAutoAddIsNotNull() {
            addCriterion("auto_add is not null");
            return (Criteria) this;
        }

        public Criteria andAutoAddEqualTo(Integer value) {
            addCriterion("auto_add =", value, "autoAdd");
            return (Criteria) this;
        }

        public Criteria andAutoAddNotEqualTo(Integer value) {
            addCriterion("auto_add <>", value, "autoAdd");
            return (Criteria) this;
        }

        public Criteria andAutoAddGreaterThan(Integer value) {
            addCriterion("auto_add >", value, "autoAdd");
            return (Criteria) this;
        }

        public Criteria andAutoAddGreaterThanOrEqualTo(Integer value) {
            addCriterion("auto_add >=", value, "autoAdd");
            return (Criteria) this;
        }

        public Criteria andAutoAddLessThan(Integer value) {
            addCriterion("auto_add <", value, "autoAdd");
            return (Criteria) this;
        }

        public Criteria andAutoAddLessThanOrEqualTo(Integer value) {
            addCriterion("auto_add <=", value, "autoAdd");
            return (Criteria) this;
        }

        public Criteria andAutoAddIn(List<Integer> values) {
            addCriterion("auto_add in", values, "autoAdd");
            return (Criteria) this;
        }

        public Criteria andAutoAddNotIn(List<Integer> values) {
            addCriterion("auto_add not in", values, "autoAdd");
            return (Criteria) this;
        }

        public Criteria andAutoAddBetween(Integer value1, Integer value2) {
            addCriterion("auto_add between", value1, value2, "autoAdd");
            return (Criteria) this;
        }

        public Criteria andAutoAddNotBetween(Integer value1, Integer value2) {
            addCriterion("auto_add not between", value1, value2, "autoAdd");
            return (Criteria) this;
        }

        public Criteria andAutoRecordIsNull() {
            addCriterion("auto_record is null");
            return (Criteria) this;
        }

        public Criteria andAutoRecordIsNotNull() {
            addCriterion("auto_record is not null");
            return (Criteria) this;
        }

        public Criteria andAutoRecordEqualTo(Integer value) {
            addCriterion("auto_record =", value, "autoRecord");
            return (Criteria) this;
        }

        public Criteria andAutoRecordNotEqualTo(Integer value) {
            addCriterion("auto_record <>", value, "autoRecord");
            return (Criteria) this;
        }

        public Criteria andAutoRecordGreaterThan(Integer value) {
            addCriterion("auto_record >", value, "autoRecord");
            return (Criteria) this;
        }

        public Criteria andAutoRecordGreaterThanOrEqualTo(Integer value) {
            addCriterion("auto_record >=", value, "autoRecord");
            return (Criteria) this;
        }

        public Criteria andAutoRecordLessThan(Integer value) {
            addCriterion("auto_record <", value, "autoRecord");
            return (Criteria) this;
        }

        public Criteria andAutoRecordLessThanOrEqualTo(Integer value) {
            addCriterion("auto_record <=", value, "autoRecord");
            return (Criteria) this;
        }

        public Criteria andAutoRecordIn(List<Integer> values) {
            addCriterion("auto_record in", values, "autoRecord");
            return (Criteria) this;
        }

        public Criteria andAutoRecordNotIn(List<Integer> values) {
            addCriterion("auto_record not in", values, "autoRecord");
            return (Criteria) this;
        }

        public Criteria andAutoRecordBetween(Integer value1, Integer value2) {
            addCriterion("auto_record between", value1, value2, "autoRecord");
            return (Criteria) this;
        }

        public Criteria andAutoRecordNotBetween(Integer value1, Integer value2) {
            addCriterion("auto_record not between", value1, value2, "autoRecord");
            return (Criteria) this;
        }

        public Criteria andAutoBailIsNull() {
            addCriterion("auto_bail is null");
            return (Criteria) this;
        }

        public Criteria andAutoBailIsNotNull() {
            addCriterion("auto_bail is not null");
            return (Criteria) this;
        }

        public Criteria andAutoBailEqualTo(Integer value) {
            addCriterion("auto_bail =", value, "autoBail");
            return (Criteria) this;
        }

        public Criteria andAutoBailNotEqualTo(Integer value) {
            addCriterion("auto_bail <>", value, "autoBail");
            return (Criteria) this;
        }

        public Criteria andAutoBailGreaterThan(Integer value) {
            addCriterion("auto_bail >", value, "autoBail");
            return (Criteria) this;
        }

        public Criteria andAutoBailGreaterThanOrEqualTo(Integer value) {
            addCriterion("auto_bail >=", value, "autoBail");
            return (Criteria) this;
        }

        public Criteria andAutoBailLessThan(Integer value) {
            addCriterion("auto_bail <", value, "autoBail");
            return (Criteria) this;
        }

        public Criteria andAutoBailLessThanOrEqualTo(Integer value) {
            addCriterion("auto_bail <=", value, "autoBail");
            return (Criteria) this;
        }

        public Criteria andAutoBailIn(List<Integer> values) {
            addCriterion("auto_bail in", values, "autoBail");
            return (Criteria) this;
        }

        public Criteria andAutoBailNotIn(List<Integer> values) {
            addCriterion("auto_bail not in", values, "autoBail");
            return (Criteria) this;
        }

        public Criteria andAutoBailBetween(Integer value1, Integer value2) {
            addCriterion("auto_bail between", value1, value2, "autoBail");
            return (Criteria) this;
        }

        public Criteria andAutoBailNotBetween(Integer value1, Integer value2) {
            addCriterion("auto_bail not between", value1, value2, "autoBail");
            return (Criteria) this;
        }

        public Criteria andAutoAuditIsNull() {
            addCriterion("auto_audit is null");
            return (Criteria) this;
        }

        public Criteria andAutoAuditIsNotNull() {
            addCriterion("auto_audit is not null");
            return (Criteria) this;
        }

        public Criteria andAutoAuditEqualTo(Integer value) {
            addCriterion("auto_audit =", value, "autoAudit");
            return (Criteria) this;
        }

        public Criteria andAutoAuditNotEqualTo(Integer value) {
            addCriterion("auto_audit <>", value, "autoAudit");
            return (Criteria) this;
        }

        public Criteria andAutoAuditGreaterThan(Integer value) {
            addCriterion("auto_audit >", value, "autoAudit");
            return (Criteria) this;
        }

        public Criteria andAutoAuditGreaterThanOrEqualTo(Integer value) {
            addCriterion("auto_audit >=", value, "autoAudit");
            return (Criteria) this;
        }

        public Criteria andAutoAuditLessThan(Integer value) {
            addCriterion("auto_audit <", value, "autoAudit");
            return (Criteria) this;
        }

        public Criteria andAutoAuditLessThanOrEqualTo(Integer value) {
            addCriterion("auto_audit <=", value, "autoAudit");
            return (Criteria) this;
        }

        public Criteria andAutoAuditIn(List<Integer> values) {
            addCriterion("auto_audit in", values, "autoAudit");
            return (Criteria) this;
        }

        public Criteria andAutoAuditNotIn(List<Integer> values) {
            addCriterion("auto_audit not in", values, "autoAudit");
            return (Criteria) this;
        }

        public Criteria andAutoAuditBetween(Integer value1, Integer value2) {
            addCriterion("auto_audit between", value1, value2, "autoAudit");
            return (Criteria) this;
        }

        public Criteria andAutoAuditNotBetween(Integer value1, Integer value2) {
            addCriterion("auto_audit not between", value1, value2, "autoAudit");
            return (Criteria) this;
        }

        public Criteria andAutoReviewIsNull() {
            addCriterion("auto_review is null");
            return (Criteria) this;
        }

        public Criteria andAutoReviewIsNotNull() {
            addCriterion("auto_review is not null");
            return (Criteria) this;
        }

        public Criteria andAutoReviewEqualTo(Integer value) {
            addCriterion("auto_review =", value, "autoReview");
            return (Criteria) this;
        }

        public Criteria andAutoReviewNotEqualTo(Integer value) {
            addCriterion("auto_review <>", value, "autoReview");
            return (Criteria) this;
        }

        public Criteria andAutoReviewGreaterThan(Integer value) {
            addCriterion("auto_review >", value, "autoReview");
            return (Criteria) this;
        }

        public Criteria andAutoReviewGreaterThanOrEqualTo(Integer value) {
            addCriterion("auto_review >=", value, "autoReview");
            return (Criteria) this;
        }

        public Criteria andAutoReviewLessThan(Integer value) {
            addCriterion("auto_review <", value, "autoReview");
            return (Criteria) this;
        }

        public Criteria andAutoReviewLessThanOrEqualTo(Integer value) {
            addCriterion("auto_review <=", value, "autoReview");
            return (Criteria) this;
        }

        public Criteria andAutoReviewIn(List<Integer> values) {
            addCriterion("auto_review in", values, "autoReview");
            return (Criteria) this;
        }

        public Criteria andAutoReviewNotIn(List<Integer> values) {
            addCriterion("auto_review not in", values, "autoReview");
            return (Criteria) this;
        }

        public Criteria andAutoReviewBetween(Integer value1, Integer value2) {
            addCriterion("auto_review between", value1, value2, "autoReview");
            return (Criteria) this;
        }

        public Criteria andAutoReviewNotBetween(Integer value1, Integer value2) {
            addCriterion("auto_review not between", value1, value2, "autoReview");
            return (Criteria) this;
        }

        public Criteria andAutoSendMinutesIsNull() {
            addCriterion("auto_send_minutes is null");
            return (Criteria) this;
        }

        public Criteria andAutoSendMinutesIsNotNull() {
            addCriterion("auto_send_minutes is not null");
            return (Criteria) this;
        }

        public Criteria andAutoSendMinutesEqualTo(Integer value) {
            addCriterion("auto_send_minutes =", value, "autoSendMinutes");
            return (Criteria) this;
        }

        public Criteria andAutoSendMinutesNotEqualTo(Integer value) {
            addCriterion("auto_send_minutes <>", value, "autoSendMinutes");
            return (Criteria) this;
        }

        public Criteria andAutoSendMinutesGreaterThan(Integer value) {
            addCriterion("auto_send_minutes >", value, "autoSendMinutes");
            return (Criteria) this;
        }

        public Criteria andAutoSendMinutesGreaterThanOrEqualTo(Integer value) {
            addCriterion("auto_send_minutes >=", value, "autoSendMinutes");
            return (Criteria) this;
        }

        public Criteria andAutoSendMinutesLessThan(Integer value) {
            addCriterion("auto_send_minutes <", value, "autoSendMinutes");
            return (Criteria) this;
        }

        public Criteria andAutoSendMinutesLessThanOrEqualTo(Integer value) {
            addCriterion("auto_send_minutes <=", value, "autoSendMinutes");
            return (Criteria) this;
        }

        public Criteria andAutoSendMinutesIn(List<Integer> values) {
            addCriterion("auto_send_minutes in", values, "autoSendMinutes");
            return (Criteria) this;
        }

        public Criteria andAutoSendMinutesNotIn(List<Integer> values) {
            addCriterion("auto_send_minutes not in", values, "autoSendMinutes");
            return (Criteria) this;
        }

        public Criteria andAutoSendMinutesBetween(Integer value1, Integer value2) {
            addCriterion("auto_send_minutes between", value1, value2, "autoSendMinutes");
            return (Criteria) this;
        }

        public Criteria andAutoSendMinutesNotBetween(Integer value1, Integer value2) {
            addCriterion("auto_send_minutes not between", value1, value2, "autoSendMinutes");
            return (Criteria) this;
        }

        public Criteria andAutoReviewMinutesIsNull() {
            addCriterion("auto_review_minutes is null");
            return (Criteria) this;
        }

        public Criteria andAutoReviewMinutesIsNotNull() {
            addCriterion("auto_review_minutes is not null");
            return (Criteria) this;
        }

        public Criteria andAutoReviewMinutesEqualTo(Integer value) {
            addCriterion("auto_review_minutes =", value, "autoReviewMinutes");
            return (Criteria) this;
        }

        public Criteria andAutoReviewMinutesNotEqualTo(Integer value) {
            addCriterion("auto_review_minutes <>", value, "autoReviewMinutes");
            return (Criteria) this;
        }

        public Criteria andAutoReviewMinutesGreaterThan(Integer value) {
            addCriterion("auto_review_minutes >", value, "autoReviewMinutes");
            return (Criteria) this;
        }

        public Criteria andAutoReviewMinutesGreaterThanOrEqualTo(Integer value) {
            addCriterion("auto_review_minutes >=", value, "autoReviewMinutes");
            return (Criteria) this;
        }

        public Criteria andAutoReviewMinutesLessThan(Integer value) {
            addCriterion("auto_review_minutes <", value, "autoReviewMinutes");
            return (Criteria) this;
        }

        public Criteria andAutoReviewMinutesLessThanOrEqualTo(Integer value) {
            addCriterion("auto_review_minutes <=", value, "autoReviewMinutes");
            return (Criteria) this;
        }

        public Criteria andAutoReviewMinutesIn(List<Integer> values) {
            addCriterion("auto_review_minutes in", values, "autoReviewMinutes");
            return (Criteria) this;
        }

        public Criteria andAutoReviewMinutesNotIn(List<Integer> values) {
            addCriterion("auto_review_minutes not in", values, "autoReviewMinutes");
            return (Criteria) this;
        }

        public Criteria andAutoReviewMinutesBetween(Integer value1, Integer value2) {
            addCriterion("auto_review_minutes between", value1, value2, "autoReviewMinutes");
            return (Criteria) this;
        }

        public Criteria andAutoReviewMinutesNotBetween(Integer value1, Integer value2) {
            addCriterion("auto_review_minutes not between", value1, value2, "autoReviewMinutes");
            return (Criteria) this;
        }

        public Criteria andApplicantIsNull() {
            addCriterion("applicant is null");
            return (Criteria) this;
        }

        public Criteria andApplicantIsNotNull() {
            addCriterion("applicant is not null");
            return (Criteria) this;
        }

        public Criteria andApplicantEqualTo(String value) {
            addCriterion("applicant =", value, "applicant");
            return (Criteria) this;
        }

        public Criteria andApplicantNotEqualTo(String value) {
            addCriterion("applicant <>", value, "applicant");
            return (Criteria) this;
        }

        public Criteria andApplicantGreaterThan(String value) {
            addCriterion("applicant >", value, "applicant");
            return (Criteria) this;
        }

        public Criteria andApplicantGreaterThanOrEqualTo(String value) {
            addCriterion("applicant >=", value, "applicant");
            return (Criteria) this;
        }

        public Criteria andApplicantLessThan(String value) {
            addCriterion("applicant <", value, "applicant");
            return (Criteria) this;
        }

        public Criteria andApplicantLessThanOrEqualTo(String value) {
            addCriterion("applicant <=", value, "applicant");
            return (Criteria) this;
        }

        public Criteria andApplicantLike(String value) {
            addCriterion("applicant like", value, "applicant");
            return (Criteria) this;
        }

        public Criteria andApplicantNotLike(String value) {
            addCriterion("applicant not like", value, "applicant");
            return (Criteria) this;
        }

        public Criteria andApplicantIn(List<String> values) {
            addCriterion("applicant in", values, "applicant");
            return (Criteria) this;
        }

        public Criteria andApplicantNotIn(List<String> values) {
            addCriterion("applicant not in", values, "applicant");
            return (Criteria) this;
        }

        public Criteria andApplicantBetween(String value1, String value2) {
            addCriterion("applicant between", value1, value2, "applicant");
            return (Criteria) this;
        }

        public Criteria andApplicantNotBetween(String value1, String value2) {
            addCriterion("applicant not between", value1, value2, "applicant");
            return (Criteria) this;
        }

        public Criteria andRepayOrgNameIsNull() {
            addCriterion("repay_org_name is null");
            return (Criteria) this;
        }

        public Criteria andRepayOrgNameIsNotNull() {
            addCriterion("repay_org_name is not null");
            return (Criteria) this;
        }

        public Criteria andRepayOrgNameEqualTo(String value) {
            addCriterion("repay_org_name =", value, "repayOrgName");
            return (Criteria) this;
        }

        public Criteria andRepayOrgNameNotEqualTo(String value) {
            addCriterion("repay_org_name <>", value, "repayOrgName");
            return (Criteria) this;
        }

        public Criteria andRepayOrgNameGreaterThan(String value) {
            addCriterion("repay_org_name >", value, "repayOrgName");
            return (Criteria) this;
        }

        public Criteria andRepayOrgNameGreaterThanOrEqualTo(String value) {
            addCriterion("repay_org_name >=", value, "repayOrgName");
            return (Criteria) this;
        }

        public Criteria andRepayOrgNameLessThan(String value) {
            addCriterion("repay_org_name <", value, "repayOrgName");
            return (Criteria) this;
        }

        public Criteria andRepayOrgNameLessThanOrEqualTo(String value) {
            addCriterion("repay_org_name <=", value, "repayOrgName");
            return (Criteria) this;
        }

        public Criteria andRepayOrgNameLike(String value) {
            addCriterion("repay_org_name like", value, "repayOrgName");
            return (Criteria) this;
        }

        public Criteria andRepayOrgNameNotLike(String value) {
            addCriterion("repay_org_name not like", value, "repayOrgName");
            return (Criteria) this;
        }

        public Criteria andRepayOrgNameIn(List<String> values) {
            addCriterion("repay_org_name in", values, "repayOrgName");
            return (Criteria) this;
        }

        public Criteria andRepayOrgNameNotIn(List<String> values) {
            addCriterion("repay_org_name not in", values, "repayOrgName");
            return (Criteria) this;
        }

        public Criteria andRepayOrgNameBetween(String value1, String value2) {
            addCriterion("repay_org_name between", value1, value2, "repayOrgName");
            return (Criteria) this;
        }

        public Criteria andRepayOrgNameNotBetween(String value1, String value2) {
            addCriterion("repay_org_name not between", value1, value2, "repayOrgName");
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