package com.hyjf.am.trade.dao.model.auto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HjhPlanAssetExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public HjhPlanAssetExample() {
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

        public Criteria andAssetIdIsNull() {
            addCriterion("asset_id is null");
            return (Criteria) this;
        }

        public Criteria andAssetIdIsNotNull() {
            addCriterion("asset_id is not null");
            return (Criteria) this;
        }

        public Criteria andAssetIdEqualTo(String value) {
            addCriterion("asset_id =", value, "assetId");
            return (Criteria) this;
        }

        public Criteria andAssetIdNotEqualTo(String value) {
            addCriterion("asset_id <>", value, "assetId");
            return (Criteria) this;
        }

        public Criteria andAssetIdGreaterThan(String value) {
            addCriterion("asset_id >", value, "assetId");
            return (Criteria) this;
        }

        public Criteria andAssetIdGreaterThanOrEqualTo(String value) {
            addCriterion("asset_id >=", value, "assetId");
            return (Criteria) this;
        }

        public Criteria andAssetIdLessThan(String value) {
            addCriterion("asset_id <", value, "assetId");
            return (Criteria) this;
        }

        public Criteria andAssetIdLessThanOrEqualTo(String value) {
            addCriterion("asset_id <=", value, "assetId");
            return (Criteria) this;
        }

        public Criteria andAssetIdLike(String value) {
            addCriterion("asset_id like", value, "assetId");
            return (Criteria) this;
        }

        public Criteria andAssetIdNotLike(String value) {
            addCriterion("asset_id not like", value, "assetId");
            return (Criteria) this;
        }

        public Criteria andAssetIdIn(List<String> values) {
            addCriterion("asset_id in", values, "assetId");
            return (Criteria) this;
        }

        public Criteria andAssetIdNotIn(List<String> values) {
            addCriterion("asset_id not in", values, "assetId");
            return (Criteria) this;
        }

        public Criteria andAssetIdBetween(String value1, String value2) {
            addCriterion("asset_id between", value1, value2, "assetId");
            return (Criteria) this;
        }

        public Criteria andAssetIdNotBetween(String value1, String value2) {
            addCriterion("asset_id not between", value1, value2, "assetId");
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

        public Criteria andPlanNidIsNull() {
            addCriterion("plan_nid is null");
            return (Criteria) this;
        }

        public Criteria andPlanNidIsNotNull() {
            addCriterion("plan_nid is not null");
            return (Criteria) this;
        }

        public Criteria andPlanNidEqualTo(String value) {
            addCriterion("plan_nid =", value, "planNid");
            return (Criteria) this;
        }

        public Criteria andPlanNidNotEqualTo(String value) {
            addCriterion("plan_nid <>", value, "planNid");
            return (Criteria) this;
        }

        public Criteria andPlanNidGreaterThan(String value) {
            addCriterion("plan_nid >", value, "planNid");
            return (Criteria) this;
        }

        public Criteria andPlanNidGreaterThanOrEqualTo(String value) {
            addCriterion("plan_nid >=", value, "planNid");
            return (Criteria) this;
        }

        public Criteria andPlanNidLessThan(String value) {
            addCriterion("plan_nid <", value, "planNid");
            return (Criteria) this;
        }

        public Criteria andPlanNidLessThanOrEqualTo(String value) {
            addCriterion("plan_nid <=", value, "planNid");
            return (Criteria) this;
        }

        public Criteria andPlanNidLike(String value) {
            addCriterion("plan_nid like", value, "planNid");
            return (Criteria) this;
        }

        public Criteria andPlanNidNotLike(String value) {
            addCriterion("plan_nid not like", value, "planNid");
            return (Criteria) this;
        }

        public Criteria andPlanNidIn(List<String> values) {
            addCriterion("plan_nid in", values, "planNid");
            return (Criteria) this;
        }

        public Criteria andPlanNidNotIn(List<String> values) {
            addCriterion("plan_nid not in", values, "planNid");
            return (Criteria) this;
        }

        public Criteria andPlanNidBetween(String value1, String value2) {
            addCriterion("plan_nid between", value1, value2, "planNid");
            return (Criteria) this;
        }

        public Criteria andPlanNidNotBetween(String value1, String value2) {
            addCriterion("plan_nid not between", value1, value2, "planNid");
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

        public Criteria andMobileIsNull() {
            addCriterion("mobile is null");
            return (Criteria) this;
        }

        public Criteria andMobileIsNotNull() {
            addCriterion("mobile is not null");
            return (Criteria) this;
        }

        public Criteria andMobileEqualTo(String value) {
            addCriterion("mobile =", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotEqualTo(String value) {
            addCriterion("mobile <>", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileGreaterThan(String value) {
            addCriterion("mobile >", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileGreaterThanOrEqualTo(String value) {
            addCriterion("mobile >=", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLessThan(String value) {
            addCriterion("mobile <", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLessThanOrEqualTo(String value) {
            addCriterion("mobile <=", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLike(String value) {
            addCriterion("mobile like", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotLike(String value) {
            addCriterion("mobile not like", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileIn(List<String> values) {
            addCriterion("mobile in", values, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotIn(List<String> values) {
            addCriterion("mobile not in", values, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileBetween(String value1, String value2) {
            addCriterion("mobile between", value1, value2, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotBetween(String value1, String value2) {
            addCriterion("mobile not between", value1, value2, "mobile");
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

        public Criteria andTruenameIsNull() {
            addCriterion("truename is null");
            return (Criteria) this;
        }

        public Criteria andTruenameIsNotNull() {
            addCriterion("truename is not null");
            return (Criteria) this;
        }

        public Criteria andTruenameEqualTo(String value) {
            addCriterion("truename =", value, "truename");
            return (Criteria) this;
        }

        public Criteria andTruenameNotEqualTo(String value) {
            addCriterion("truename <>", value, "truename");
            return (Criteria) this;
        }

        public Criteria andTruenameGreaterThan(String value) {
            addCriterion("truename >", value, "truename");
            return (Criteria) this;
        }

        public Criteria andTruenameGreaterThanOrEqualTo(String value) {
            addCriterion("truename >=", value, "truename");
            return (Criteria) this;
        }

        public Criteria andTruenameLessThan(String value) {
            addCriterion("truename <", value, "truename");
            return (Criteria) this;
        }

        public Criteria andTruenameLessThanOrEqualTo(String value) {
            addCriterion("truename <=", value, "truename");
            return (Criteria) this;
        }

        public Criteria andTruenameLike(String value) {
            addCriterion("truename like", value, "truename");
            return (Criteria) this;
        }

        public Criteria andTruenameNotLike(String value) {
            addCriterion("truename not like", value, "truename");
            return (Criteria) this;
        }

        public Criteria andTruenameIn(List<String> values) {
            addCriterion("truename in", values, "truename");
            return (Criteria) this;
        }

        public Criteria andTruenameNotIn(List<String> values) {
            addCriterion("truename not in", values, "truename");
            return (Criteria) this;
        }

        public Criteria andTruenameBetween(String value1, String value2) {
            addCriterion("truename between", value1, value2, "truename");
            return (Criteria) this;
        }

        public Criteria andTruenameNotBetween(String value1, String value2) {
            addCriterion("truename not between", value1, value2, "truename");
            return (Criteria) this;
        }

        public Criteria andIdcardIsNull() {
            addCriterion("idcard is null");
            return (Criteria) this;
        }

        public Criteria andIdcardIsNotNull() {
            addCriterion("idcard is not null");
            return (Criteria) this;
        }

        public Criteria andIdcardEqualTo(String value) {
            addCriterion("idcard =", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardNotEqualTo(String value) {
            addCriterion("idcard <>", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardGreaterThan(String value) {
            addCriterion("idcard >", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardGreaterThanOrEqualTo(String value) {
            addCriterion("idcard >=", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardLessThan(String value) {
            addCriterion("idcard <", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardLessThanOrEqualTo(String value) {
            addCriterion("idcard <=", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardLike(String value) {
            addCriterion("idcard like", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardNotLike(String value) {
            addCriterion("idcard not like", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardIn(List<String> values) {
            addCriterion("idcard in", values, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardNotIn(List<String> values) {
            addCriterion("idcard not in", values, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardBetween(String value1, String value2) {
            addCriterion("idcard between", value1, value2, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardNotBetween(String value1, String value2) {
            addCriterion("idcard not between", value1, value2, "idcard");
            return (Criteria) this;
        }

        public Criteria andAccountIsNull() {
            addCriterion("account is null");
            return (Criteria) this;
        }

        public Criteria andAccountIsNotNull() {
            addCriterion("account is not null");
            return (Criteria) this;
        }

        public Criteria andAccountEqualTo(Long value) {
            addCriterion("account =", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountNotEqualTo(Long value) {
            addCriterion("account <>", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountGreaterThan(Long value) {
            addCriterion("account >", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountGreaterThanOrEqualTo(Long value) {
            addCriterion("account >=", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountLessThan(Long value) {
            addCriterion("account <", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountLessThanOrEqualTo(Long value) {
            addCriterion("account <=", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountIn(List<Long> values) {
            addCriterion("account in", values, "account");
            return (Criteria) this;
        }

        public Criteria andAccountNotIn(List<Long> values) {
            addCriterion("account not in", values, "account");
            return (Criteria) this;
        }

        public Criteria andAccountBetween(Long value1, Long value2) {
            addCriterion("account between", value1, value2, "account");
            return (Criteria) this;
        }

        public Criteria andAccountNotBetween(Long value1, Long value2) {
            addCriterion("account not between", value1, value2, "account");
            return (Criteria) this;
        }

        public Criteria andIsMonthIsNull() {
            addCriterion("is_month is null");
            return (Criteria) this;
        }

        public Criteria andIsMonthIsNotNull() {
            addCriterion("is_month is not null");
            return (Criteria) this;
        }

        public Criteria andIsMonthEqualTo(Integer value) {
            addCriterion("is_month =", value, "isMonth");
            return (Criteria) this;
        }

        public Criteria andIsMonthNotEqualTo(Integer value) {
            addCriterion("is_month <>", value, "isMonth");
            return (Criteria) this;
        }

        public Criteria andIsMonthGreaterThan(Integer value) {
            addCriterion("is_month >", value, "isMonth");
            return (Criteria) this;
        }

        public Criteria andIsMonthGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_month >=", value, "isMonth");
            return (Criteria) this;
        }

        public Criteria andIsMonthLessThan(Integer value) {
            addCriterion("is_month <", value, "isMonth");
            return (Criteria) this;
        }

        public Criteria andIsMonthLessThanOrEqualTo(Integer value) {
            addCriterion("is_month <=", value, "isMonth");
            return (Criteria) this;
        }

        public Criteria andIsMonthIn(List<Integer> values) {
            addCriterion("is_month in", values, "isMonth");
            return (Criteria) this;
        }

        public Criteria andIsMonthNotIn(List<Integer> values) {
            addCriterion("is_month not in", values, "isMonth");
            return (Criteria) this;
        }

        public Criteria andIsMonthBetween(Integer value1, Integer value2) {
            addCriterion("is_month between", value1, value2, "isMonth");
            return (Criteria) this;
        }

        public Criteria andIsMonthNotBetween(Integer value1, Integer value2) {
            addCriterion("is_month not between", value1, value2, "isMonth");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodIsNull() {
            addCriterion("borrow_period is null");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodIsNotNull() {
            addCriterion("borrow_period is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodEqualTo(Integer value) {
            addCriterion("borrow_period =", value, "borrowPeriod");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodNotEqualTo(Integer value) {
            addCriterion("borrow_period <>", value, "borrowPeriod");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodGreaterThan(Integer value) {
            addCriterion("borrow_period >", value, "borrowPeriod");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodGreaterThanOrEqualTo(Integer value) {
            addCriterion("borrow_period >=", value, "borrowPeriod");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodLessThan(Integer value) {
            addCriterion("borrow_period <", value, "borrowPeriod");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodLessThanOrEqualTo(Integer value) {
            addCriterion("borrow_period <=", value, "borrowPeriod");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodIn(List<Integer> values) {
            addCriterion("borrow_period in", values, "borrowPeriod");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodNotIn(List<Integer> values) {
            addCriterion("borrow_period not in", values, "borrowPeriod");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodBetween(Integer value1, Integer value2) {
            addCriterion("borrow_period between", value1, value2, "borrowPeriod");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodNotBetween(Integer value1, Integer value2) {
            addCriterion("borrow_period not between", value1, value2, "borrowPeriod");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleIsNull() {
            addCriterion("borrow_style is null");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleIsNotNull() {
            addCriterion("borrow_style is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleEqualTo(String value) {
            addCriterion("borrow_style =", value, "borrowStyle");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleNotEqualTo(String value) {
            addCriterion("borrow_style <>", value, "borrowStyle");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleGreaterThan(String value) {
            addCriterion("borrow_style >", value, "borrowStyle");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleGreaterThanOrEqualTo(String value) {
            addCriterion("borrow_style >=", value, "borrowStyle");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleLessThan(String value) {
            addCriterion("borrow_style <", value, "borrowStyle");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleLessThanOrEqualTo(String value) {
            addCriterion("borrow_style <=", value, "borrowStyle");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleLike(String value) {
            addCriterion("borrow_style like", value, "borrowStyle");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleNotLike(String value) {
            addCriterion("borrow_style not like", value, "borrowStyle");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleIn(List<String> values) {
            addCriterion("borrow_style in", values, "borrowStyle");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleNotIn(List<String> values) {
            addCriterion("borrow_style not in", values, "borrowStyle");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleBetween(String value1, String value2) {
            addCriterion("borrow_style between", value1, value2, "borrowStyle");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleNotBetween(String value1, String value2) {
            addCriterion("borrow_style not between", value1, value2, "borrowStyle");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusIsNull() {
            addCriterion("verify_status is null");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusIsNotNull() {
            addCriterion("verify_status is not null");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusEqualTo(Integer value) {
            addCriterion("verify_status =", value, "verifyStatus");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusNotEqualTo(Integer value) {
            addCriterion("verify_status <>", value, "verifyStatus");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusGreaterThan(Integer value) {
            addCriterion("verify_status >", value, "verifyStatus");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("verify_status >=", value, "verifyStatus");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusLessThan(Integer value) {
            addCriterion("verify_status <", value, "verifyStatus");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusLessThanOrEqualTo(Integer value) {
            addCriterion("verify_status <=", value, "verifyStatus");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusIn(List<Integer> values) {
            addCriterion("verify_status in", values, "verifyStatus");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusNotIn(List<Integer> values) {
            addCriterion("verify_status not in", values, "verifyStatus");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusBetween(Integer value1, Integer value2) {
            addCriterion("verify_status between", value1, value2, "verifyStatus");
            return (Criteria) this;
        }

        public Criteria andVerifyStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("verify_status not between", value1, value2, "verifyStatus");
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

        public Criteria andRecieveTimeIsNull() {
            addCriterion("recieve_time is null");
            return (Criteria) this;
        }

        public Criteria andRecieveTimeIsNotNull() {
            addCriterion("recieve_time is not null");
            return (Criteria) this;
        }

        public Criteria andRecieveTimeEqualTo(Integer value) {
            addCriterion("recieve_time =", value, "recieveTime");
            return (Criteria) this;
        }

        public Criteria andRecieveTimeNotEqualTo(Integer value) {
            addCriterion("recieve_time <>", value, "recieveTime");
            return (Criteria) this;
        }

        public Criteria andRecieveTimeGreaterThan(Integer value) {
            addCriterion("recieve_time >", value, "recieveTime");
            return (Criteria) this;
        }

        public Criteria andRecieveTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("recieve_time >=", value, "recieveTime");
            return (Criteria) this;
        }

        public Criteria andRecieveTimeLessThan(Integer value) {
            addCriterion("recieve_time <", value, "recieveTime");
            return (Criteria) this;
        }

        public Criteria andRecieveTimeLessThanOrEqualTo(Integer value) {
            addCriterion("recieve_time <=", value, "recieveTime");
            return (Criteria) this;
        }

        public Criteria andRecieveTimeIn(List<Integer> values) {
            addCriterion("recieve_time in", values, "recieveTime");
            return (Criteria) this;
        }

        public Criteria andRecieveTimeNotIn(List<Integer> values) {
            addCriterion("recieve_time not in", values, "recieveTime");
            return (Criteria) this;
        }

        public Criteria andRecieveTimeBetween(Integer value1, Integer value2) {
            addCriterion("recieve_time between", value1, value2, "recieveTime");
            return (Criteria) this;
        }

        public Criteria andRecieveTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("recieve_time not between", value1, value2, "recieveTime");
            return (Criteria) this;
        }

        public Criteria andLabelIdIsNull() {
            addCriterion("label_id is null");
            return (Criteria) this;
        }

        public Criteria andLabelIdIsNotNull() {
            addCriterion("label_id is not null");
            return (Criteria) this;
        }

        public Criteria andLabelIdEqualTo(Integer value) {
            addCriterion("label_id =", value, "labelId");
            return (Criteria) this;
        }

        public Criteria andLabelIdNotEqualTo(Integer value) {
            addCriterion("label_id <>", value, "labelId");
            return (Criteria) this;
        }

        public Criteria andLabelIdGreaterThan(Integer value) {
            addCriterion("label_id >", value, "labelId");
            return (Criteria) this;
        }

        public Criteria andLabelIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("label_id >=", value, "labelId");
            return (Criteria) this;
        }

        public Criteria andLabelIdLessThan(Integer value) {
            addCriterion("label_id <", value, "labelId");
            return (Criteria) this;
        }

        public Criteria andLabelIdLessThanOrEqualTo(Integer value) {
            addCriterion("label_id <=", value, "labelId");
            return (Criteria) this;
        }

        public Criteria andLabelIdIn(List<Integer> values) {
            addCriterion("label_id in", values, "labelId");
            return (Criteria) this;
        }

        public Criteria andLabelIdNotIn(List<Integer> values) {
            addCriterion("label_id not in", values, "labelId");
            return (Criteria) this;
        }

        public Criteria andLabelIdBetween(Integer value1, Integer value2) {
            addCriterion("label_id between", value1, value2, "labelId");
            return (Criteria) this;
        }

        public Criteria andLabelIdNotBetween(Integer value1, Integer value2) {
            addCriterion("label_id not between", value1, value2, "labelId");
            return (Criteria) this;
        }

        public Criteria andLabelNameIsNull() {
            addCriterion("label_name is null");
            return (Criteria) this;
        }

        public Criteria andLabelNameIsNotNull() {
            addCriterion("label_name is not null");
            return (Criteria) this;
        }

        public Criteria andLabelNameEqualTo(String value) {
            addCriterion("label_name =", value, "labelName");
            return (Criteria) this;
        }

        public Criteria andLabelNameNotEqualTo(String value) {
            addCriterion("label_name <>", value, "labelName");
            return (Criteria) this;
        }

        public Criteria andLabelNameGreaterThan(String value) {
            addCriterion("label_name >", value, "labelName");
            return (Criteria) this;
        }

        public Criteria andLabelNameGreaterThanOrEqualTo(String value) {
            addCriterion("label_name >=", value, "labelName");
            return (Criteria) this;
        }

        public Criteria andLabelNameLessThan(String value) {
            addCriterion("label_name <", value, "labelName");
            return (Criteria) this;
        }

        public Criteria andLabelNameLessThanOrEqualTo(String value) {
            addCriterion("label_name <=", value, "labelName");
            return (Criteria) this;
        }

        public Criteria andLabelNameLike(String value) {
            addCriterion("label_name like", value, "labelName");
            return (Criteria) this;
        }

        public Criteria andLabelNameNotLike(String value) {
            addCriterion("label_name not like", value, "labelName");
            return (Criteria) this;
        }

        public Criteria andLabelNameIn(List<String> values) {
            addCriterion("label_name in", values, "labelName");
            return (Criteria) this;
        }

        public Criteria andLabelNameNotIn(List<String> values) {
            addCriterion("label_name not in", values, "labelName");
            return (Criteria) this;
        }

        public Criteria andLabelNameBetween(String value1, String value2) {
            addCriterion("label_name between", value1, value2, "labelName");
            return (Criteria) this;
        }

        public Criteria andLabelNameNotBetween(String value1, String value2) {
            addCriterion("label_name not between", value1, value2, "labelName");
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

        public Criteria andAgeIsNull() {
            addCriterion("age is null");
            return (Criteria) this;
        }

        public Criteria andAgeIsNotNull() {
            addCriterion("age is not null");
            return (Criteria) this;
        }

        public Criteria andAgeEqualTo(Integer value) {
            addCriterion("age =", value, "age");
            return (Criteria) this;
        }

        public Criteria andAgeNotEqualTo(Integer value) {
            addCriterion("age <>", value, "age");
            return (Criteria) this;
        }

        public Criteria andAgeGreaterThan(Integer value) {
            addCriterion("age >", value, "age");
            return (Criteria) this;
        }

        public Criteria andAgeGreaterThanOrEqualTo(Integer value) {
            addCriterion("age >=", value, "age");
            return (Criteria) this;
        }

        public Criteria andAgeLessThan(Integer value) {
            addCriterion("age <", value, "age");
            return (Criteria) this;
        }

        public Criteria andAgeLessThanOrEqualTo(Integer value) {
            addCriterion("age <=", value, "age");
            return (Criteria) this;
        }

        public Criteria andAgeIn(List<Integer> values) {
            addCriterion("age in", values, "age");
            return (Criteria) this;
        }

        public Criteria andAgeNotIn(List<Integer> values) {
            addCriterion("age not in", values, "age");
            return (Criteria) this;
        }

        public Criteria andAgeBetween(Integer value1, Integer value2) {
            addCriterion("age between", value1, value2, "age");
            return (Criteria) this;
        }

        public Criteria andAgeNotBetween(Integer value1, Integer value2) {
            addCriterion("age not between", value1, value2, "age");
            return (Criteria) this;
        }

        public Criteria andMarriageIsNull() {
            addCriterion("marriage is null");
            return (Criteria) this;
        }

        public Criteria andMarriageIsNotNull() {
            addCriterion("marriage is not null");
            return (Criteria) this;
        }

        public Criteria andMarriageEqualTo(Integer value) {
            addCriterion("marriage =", value, "marriage");
            return (Criteria) this;
        }

        public Criteria andMarriageNotEqualTo(Integer value) {
            addCriterion("marriage <>", value, "marriage");
            return (Criteria) this;
        }

        public Criteria andMarriageGreaterThan(Integer value) {
            addCriterion("marriage >", value, "marriage");
            return (Criteria) this;
        }

        public Criteria andMarriageGreaterThanOrEqualTo(Integer value) {
            addCriterion("marriage >=", value, "marriage");
            return (Criteria) this;
        }

        public Criteria andMarriageLessThan(Integer value) {
            addCriterion("marriage <", value, "marriage");
            return (Criteria) this;
        }

        public Criteria andMarriageLessThanOrEqualTo(Integer value) {
            addCriterion("marriage <=", value, "marriage");
            return (Criteria) this;
        }

        public Criteria andMarriageIn(List<Integer> values) {
            addCriterion("marriage in", values, "marriage");
            return (Criteria) this;
        }

        public Criteria andMarriageNotIn(List<Integer> values) {
            addCriterion("marriage not in", values, "marriage");
            return (Criteria) this;
        }

        public Criteria andMarriageBetween(Integer value1, Integer value2) {
            addCriterion("marriage between", value1, value2, "marriage");
            return (Criteria) this;
        }

        public Criteria andMarriageNotBetween(Integer value1, Integer value2) {
            addCriterion("marriage not between", value1, value2, "marriage");
            return (Criteria) this;
        }

        public Criteria andEntrustedFlgIsNull() {
            addCriterion("entrusted_flg is null");
            return (Criteria) this;
        }

        public Criteria andEntrustedFlgIsNotNull() {
            addCriterion("entrusted_flg is not null");
            return (Criteria) this;
        }

        public Criteria andEntrustedFlgEqualTo(Integer value) {
            addCriterion("entrusted_flg =", value, "entrustedFlg");
            return (Criteria) this;
        }

        public Criteria andEntrustedFlgNotEqualTo(Integer value) {
            addCriterion("entrusted_flg <>", value, "entrustedFlg");
            return (Criteria) this;
        }

        public Criteria andEntrustedFlgGreaterThan(Integer value) {
            addCriterion("entrusted_flg >", value, "entrustedFlg");
            return (Criteria) this;
        }

        public Criteria andEntrustedFlgGreaterThanOrEqualTo(Integer value) {
            addCriterion("entrusted_flg >=", value, "entrustedFlg");
            return (Criteria) this;
        }

        public Criteria andEntrustedFlgLessThan(Integer value) {
            addCriterion("entrusted_flg <", value, "entrustedFlg");
            return (Criteria) this;
        }

        public Criteria andEntrustedFlgLessThanOrEqualTo(Integer value) {
            addCriterion("entrusted_flg <=", value, "entrustedFlg");
            return (Criteria) this;
        }

        public Criteria andEntrustedFlgIn(List<Integer> values) {
            addCriterion("entrusted_flg in", values, "entrustedFlg");
            return (Criteria) this;
        }

        public Criteria andEntrustedFlgNotIn(List<Integer> values) {
            addCriterion("entrusted_flg not in", values, "entrustedFlg");
            return (Criteria) this;
        }

        public Criteria andEntrustedFlgBetween(Integer value1, Integer value2) {
            addCriterion("entrusted_flg between", value1, value2, "entrustedFlg");
            return (Criteria) this;
        }

        public Criteria andEntrustedFlgNotBetween(Integer value1, Integer value2) {
            addCriterion("entrusted_flg not between", value1, value2, "entrustedFlg");
            return (Criteria) this;
        }

        public Criteria andEntrustedUserIdIsNull() {
            addCriterion("entrusted_user_id is null");
            return (Criteria) this;
        }

        public Criteria andEntrustedUserIdIsNotNull() {
            addCriterion("entrusted_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andEntrustedUserIdEqualTo(Integer value) {
            addCriterion("entrusted_user_id =", value, "entrustedUserId");
            return (Criteria) this;
        }

        public Criteria andEntrustedUserIdNotEqualTo(Integer value) {
            addCriterion("entrusted_user_id <>", value, "entrustedUserId");
            return (Criteria) this;
        }

        public Criteria andEntrustedUserIdGreaterThan(Integer value) {
            addCriterion("entrusted_user_id >", value, "entrustedUserId");
            return (Criteria) this;
        }

        public Criteria andEntrustedUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("entrusted_user_id >=", value, "entrustedUserId");
            return (Criteria) this;
        }

        public Criteria andEntrustedUserIdLessThan(Integer value) {
            addCriterion("entrusted_user_id <", value, "entrustedUserId");
            return (Criteria) this;
        }

        public Criteria andEntrustedUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("entrusted_user_id <=", value, "entrustedUserId");
            return (Criteria) this;
        }

        public Criteria andEntrustedUserIdIn(List<Integer> values) {
            addCriterion("entrusted_user_id in", values, "entrustedUserId");
            return (Criteria) this;
        }

        public Criteria andEntrustedUserIdNotIn(List<Integer> values) {
            addCriterion("entrusted_user_id not in", values, "entrustedUserId");
            return (Criteria) this;
        }

        public Criteria andEntrustedUserIdBetween(Integer value1, Integer value2) {
            addCriterion("entrusted_user_id between", value1, value2, "entrustedUserId");
            return (Criteria) this;
        }

        public Criteria andEntrustedUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("entrusted_user_id not between", value1, value2, "entrustedUserId");
            return (Criteria) this;
        }

        public Criteria andEntrustedUserNameIsNull() {
            addCriterion("entrusted_user_name is null");
            return (Criteria) this;
        }

        public Criteria andEntrustedUserNameIsNotNull() {
            addCriterion("entrusted_user_name is not null");
            return (Criteria) this;
        }

        public Criteria andEntrustedUserNameEqualTo(String value) {
            addCriterion("entrusted_user_name =", value, "entrustedUserName");
            return (Criteria) this;
        }

        public Criteria andEntrustedUserNameNotEqualTo(String value) {
            addCriterion("entrusted_user_name <>", value, "entrustedUserName");
            return (Criteria) this;
        }

        public Criteria andEntrustedUserNameGreaterThan(String value) {
            addCriterion("entrusted_user_name >", value, "entrustedUserName");
            return (Criteria) this;
        }

        public Criteria andEntrustedUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("entrusted_user_name >=", value, "entrustedUserName");
            return (Criteria) this;
        }

        public Criteria andEntrustedUserNameLessThan(String value) {
            addCriterion("entrusted_user_name <", value, "entrustedUserName");
            return (Criteria) this;
        }

        public Criteria andEntrustedUserNameLessThanOrEqualTo(String value) {
            addCriterion("entrusted_user_name <=", value, "entrustedUserName");
            return (Criteria) this;
        }

        public Criteria andEntrustedUserNameLike(String value) {
            addCriterion("entrusted_user_name like", value, "entrustedUserName");
            return (Criteria) this;
        }

        public Criteria andEntrustedUserNameNotLike(String value) {
            addCriterion("entrusted_user_name not like", value, "entrustedUserName");
            return (Criteria) this;
        }

        public Criteria andEntrustedUserNameIn(List<String> values) {
            addCriterion("entrusted_user_name in", values, "entrustedUserName");
            return (Criteria) this;
        }

        public Criteria andEntrustedUserNameNotIn(List<String> values) {
            addCriterion("entrusted_user_name not in", values, "entrustedUserName");
            return (Criteria) this;
        }

        public Criteria andEntrustedUserNameBetween(String value1, String value2) {
            addCriterion("entrusted_user_name between", value1, value2, "entrustedUserName");
            return (Criteria) this;
        }

        public Criteria andEntrustedUserNameNotBetween(String value1, String value2) {
            addCriterion("entrusted_user_name not between", value1, value2, "entrustedUserName");
            return (Criteria) this;
        }

        public Criteria andEntrustedAccountIdIsNull() {
            addCriterion("entrusted_account_id is null");
            return (Criteria) this;
        }

        public Criteria andEntrustedAccountIdIsNotNull() {
            addCriterion("entrusted_account_id is not null");
            return (Criteria) this;
        }

        public Criteria andEntrustedAccountIdEqualTo(String value) {
            addCriterion("entrusted_account_id =", value, "entrustedAccountId");
            return (Criteria) this;
        }

        public Criteria andEntrustedAccountIdNotEqualTo(String value) {
            addCriterion("entrusted_account_id <>", value, "entrustedAccountId");
            return (Criteria) this;
        }

        public Criteria andEntrustedAccountIdGreaterThan(String value) {
            addCriterion("entrusted_account_id >", value, "entrustedAccountId");
            return (Criteria) this;
        }

        public Criteria andEntrustedAccountIdGreaterThanOrEqualTo(String value) {
            addCriterion("entrusted_account_id >=", value, "entrustedAccountId");
            return (Criteria) this;
        }

        public Criteria andEntrustedAccountIdLessThan(String value) {
            addCriterion("entrusted_account_id <", value, "entrustedAccountId");
            return (Criteria) this;
        }

        public Criteria andEntrustedAccountIdLessThanOrEqualTo(String value) {
            addCriterion("entrusted_account_id <=", value, "entrustedAccountId");
            return (Criteria) this;
        }

        public Criteria andEntrustedAccountIdLike(String value) {
            addCriterion("entrusted_account_id like", value, "entrustedAccountId");
            return (Criteria) this;
        }

        public Criteria andEntrustedAccountIdNotLike(String value) {
            addCriterion("entrusted_account_id not like", value, "entrustedAccountId");
            return (Criteria) this;
        }

        public Criteria andEntrustedAccountIdIn(List<String> values) {
            addCriterion("entrusted_account_id in", values, "entrustedAccountId");
            return (Criteria) this;
        }

        public Criteria andEntrustedAccountIdNotIn(List<String> values) {
            addCriterion("entrusted_account_id not in", values, "entrustedAccountId");
            return (Criteria) this;
        }

        public Criteria andEntrustedAccountIdBetween(String value1, String value2) {
            addCriterion("entrusted_account_id between", value1, value2, "entrustedAccountId");
            return (Criteria) this;
        }

        public Criteria andEntrustedAccountIdNotBetween(String value1, String value2) {
            addCriterion("entrusted_account_id not between", value1, value2, "entrustedAccountId");
            return (Criteria) this;
        }

        public Criteria andWorkCityIsNull() {
            addCriterion("work_city is null");
            return (Criteria) this;
        }

        public Criteria andWorkCityIsNotNull() {
            addCriterion("work_city is not null");
            return (Criteria) this;
        }

        public Criteria andWorkCityEqualTo(String value) {
            addCriterion("work_city =", value, "workCity");
            return (Criteria) this;
        }

        public Criteria andWorkCityNotEqualTo(String value) {
            addCriterion("work_city <>", value, "workCity");
            return (Criteria) this;
        }

        public Criteria andWorkCityGreaterThan(String value) {
            addCriterion("work_city >", value, "workCity");
            return (Criteria) this;
        }

        public Criteria andWorkCityGreaterThanOrEqualTo(String value) {
            addCriterion("work_city >=", value, "workCity");
            return (Criteria) this;
        }

        public Criteria andWorkCityLessThan(String value) {
            addCriterion("work_city <", value, "workCity");
            return (Criteria) this;
        }

        public Criteria andWorkCityLessThanOrEqualTo(String value) {
            addCriterion("work_city <=", value, "workCity");
            return (Criteria) this;
        }

        public Criteria andWorkCityLike(String value) {
            addCriterion("work_city like", value, "workCity");
            return (Criteria) this;
        }

        public Criteria andWorkCityNotLike(String value) {
            addCriterion("work_city not like", value, "workCity");
            return (Criteria) this;
        }

        public Criteria andWorkCityIn(List<String> values) {
            addCriterion("work_city in", values, "workCity");
            return (Criteria) this;
        }

        public Criteria andWorkCityNotIn(List<String> values) {
            addCriterion("work_city not in", values, "workCity");
            return (Criteria) this;
        }

        public Criteria andWorkCityBetween(String value1, String value2) {
            addCriterion("work_city between", value1, value2, "workCity");
            return (Criteria) this;
        }

        public Criteria andWorkCityNotBetween(String value1, String value2) {
            addCriterion("work_city not between", value1, value2, "workCity");
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

        public Criteria andCreditLevelIsNull() {
            addCriterion("credit_level is null");
            return (Criteria) this;
        }

        public Criteria andCreditLevelIsNotNull() {
            addCriterion("credit_level is not null");
            return (Criteria) this;
        }

        public Criteria andCreditLevelEqualTo(String value) {
            addCriterion("credit_level =", value, "creditLevel");
            return (Criteria) this;
        }

        public Criteria andCreditLevelNotEqualTo(String value) {
            addCriterion("credit_level <>", value, "creditLevel");
            return (Criteria) this;
        }

        public Criteria andCreditLevelGreaterThan(String value) {
            addCriterion("credit_level >", value, "creditLevel");
            return (Criteria) this;
        }

        public Criteria andCreditLevelGreaterThanOrEqualTo(String value) {
            addCriterion("credit_level >=", value, "creditLevel");
            return (Criteria) this;
        }

        public Criteria andCreditLevelLessThan(String value) {
            addCriterion("credit_level <", value, "creditLevel");
            return (Criteria) this;
        }

        public Criteria andCreditLevelLessThanOrEqualTo(String value) {
            addCriterion("credit_level <=", value, "creditLevel");
            return (Criteria) this;
        }

        public Criteria andCreditLevelLike(String value) {
            addCriterion("credit_level like", value, "creditLevel");
            return (Criteria) this;
        }

        public Criteria andCreditLevelNotLike(String value) {
            addCriterion("credit_level not like", value, "creditLevel");
            return (Criteria) this;
        }

        public Criteria andCreditLevelIn(List<String> values) {
            addCriterion("credit_level in", values, "creditLevel");
            return (Criteria) this;
        }

        public Criteria andCreditLevelNotIn(List<String> values) {
            addCriterion("credit_level not in", values, "creditLevel");
            return (Criteria) this;
        }

        public Criteria andCreditLevelBetween(String value1, String value2) {
            addCriterion("credit_level between", value1, value2, "creditLevel");
            return (Criteria) this;
        }

        public Criteria andCreditLevelNotBetween(String value1, String value2) {
            addCriterion("credit_level not between", value1, value2, "creditLevel");
            return (Criteria) this;
        }

        public Criteria andUseageIsNull() {
            addCriterion("useage is null");
            return (Criteria) this;
        }

        public Criteria andUseageIsNotNull() {
            addCriterion("useage is not null");
            return (Criteria) this;
        }

        public Criteria andUseageEqualTo(String value) {
            addCriterion("useage =", value, "useage");
            return (Criteria) this;
        }

        public Criteria andUseageNotEqualTo(String value) {
            addCriterion("useage <>", value, "useage");
            return (Criteria) this;
        }

        public Criteria andUseageGreaterThan(String value) {
            addCriterion("useage >", value, "useage");
            return (Criteria) this;
        }

        public Criteria andUseageGreaterThanOrEqualTo(String value) {
            addCriterion("useage >=", value, "useage");
            return (Criteria) this;
        }

        public Criteria andUseageLessThan(String value) {
            addCriterion("useage <", value, "useage");
            return (Criteria) this;
        }

        public Criteria andUseageLessThanOrEqualTo(String value) {
            addCriterion("useage <=", value, "useage");
            return (Criteria) this;
        }

        public Criteria andUseageLike(String value) {
            addCriterion("useage like", value, "useage");
            return (Criteria) this;
        }

        public Criteria andUseageNotLike(String value) {
            addCriterion("useage not like", value, "useage");
            return (Criteria) this;
        }

        public Criteria andUseageIn(List<String> values) {
            addCriterion("useage in", values, "useage");
            return (Criteria) this;
        }

        public Criteria andUseageNotIn(List<String> values) {
            addCriterion("useage not in", values, "useage");
            return (Criteria) this;
        }

        public Criteria andUseageBetween(String value1, String value2) {
            addCriterion("useage between", value1, value2, "useage");
            return (Criteria) this;
        }

        public Criteria andUseageNotBetween(String value1, String value2) {
            addCriterion("useage not between", value1, value2, "useage");
            return (Criteria) this;
        }

        public Criteria andMonthlyIncomeIsNull() {
            addCriterion("monthly_income is null");
            return (Criteria) this;
        }

        public Criteria andMonthlyIncomeIsNotNull() {
            addCriterion("monthly_income is not null");
            return (Criteria) this;
        }

        public Criteria andMonthlyIncomeEqualTo(String value) {
            addCriterion("monthly_income =", value, "monthlyIncome");
            return (Criteria) this;
        }

        public Criteria andMonthlyIncomeNotEqualTo(String value) {
            addCriterion("monthly_income <>", value, "monthlyIncome");
            return (Criteria) this;
        }

        public Criteria andMonthlyIncomeGreaterThan(String value) {
            addCriterion("monthly_income >", value, "monthlyIncome");
            return (Criteria) this;
        }

        public Criteria andMonthlyIncomeGreaterThanOrEqualTo(String value) {
            addCriterion("monthly_income >=", value, "monthlyIncome");
            return (Criteria) this;
        }

        public Criteria andMonthlyIncomeLessThan(String value) {
            addCriterion("monthly_income <", value, "monthlyIncome");
            return (Criteria) this;
        }

        public Criteria andMonthlyIncomeLessThanOrEqualTo(String value) {
            addCriterion("monthly_income <=", value, "monthlyIncome");
            return (Criteria) this;
        }

        public Criteria andMonthlyIncomeLike(String value) {
            addCriterion("monthly_income like", value, "monthlyIncome");
            return (Criteria) this;
        }

        public Criteria andMonthlyIncomeNotLike(String value) {
            addCriterion("monthly_income not like", value, "monthlyIncome");
            return (Criteria) this;
        }

        public Criteria andMonthlyIncomeIn(List<String> values) {
            addCriterion("monthly_income in", values, "monthlyIncome");
            return (Criteria) this;
        }

        public Criteria andMonthlyIncomeNotIn(List<String> values) {
            addCriterion("monthly_income not in", values, "monthlyIncome");
            return (Criteria) this;
        }

        public Criteria andMonthlyIncomeBetween(String value1, String value2) {
            addCriterion("monthly_income between", value1, value2, "monthlyIncome");
            return (Criteria) this;
        }

        public Criteria andMonthlyIncomeNotBetween(String value1, String value2) {
            addCriterion("monthly_income not between", value1, value2, "monthlyIncome");
            return (Criteria) this;
        }

        public Criteria andFirstPaymentIsNull() {
            addCriterion("first_payment is null");
            return (Criteria) this;
        }

        public Criteria andFirstPaymentIsNotNull() {
            addCriterion("first_payment is not null");
            return (Criteria) this;
        }

        public Criteria andFirstPaymentEqualTo(String value) {
            addCriterion("first_payment =", value, "firstPayment");
            return (Criteria) this;
        }

        public Criteria andFirstPaymentNotEqualTo(String value) {
            addCriterion("first_payment <>", value, "firstPayment");
            return (Criteria) this;
        }

        public Criteria andFirstPaymentGreaterThan(String value) {
            addCriterion("first_payment >", value, "firstPayment");
            return (Criteria) this;
        }

        public Criteria andFirstPaymentGreaterThanOrEqualTo(String value) {
            addCriterion("first_payment >=", value, "firstPayment");
            return (Criteria) this;
        }

        public Criteria andFirstPaymentLessThan(String value) {
            addCriterion("first_payment <", value, "firstPayment");
            return (Criteria) this;
        }

        public Criteria andFirstPaymentLessThanOrEqualTo(String value) {
            addCriterion("first_payment <=", value, "firstPayment");
            return (Criteria) this;
        }

        public Criteria andFirstPaymentLike(String value) {
            addCriterion("first_payment like", value, "firstPayment");
            return (Criteria) this;
        }

        public Criteria andFirstPaymentNotLike(String value) {
            addCriterion("first_payment not like", value, "firstPayment");
            return (Criteria) this;
        }

        public Criteria andFirstPaymentIn(List<String> values) {
            addCriterion("first_payment in", values, "firstPayment");
            return (Criteria) this;
        }

        public Criteria andFirstPaymentNotIn(List<String> values) {
            addCriterion("first_payment not in", values, "firstPayment");
            return (Criteria) this;
        }

        public Criteria andFirstPaymentBetween(String value1, String value2) {
            addCriterion("first_payment between", value1, value2, "firstPayment");
            return (Criteria) this;
        }

        public Criteria andFirstPaymentNotBetween(String value1, String value2) {
            addCriterion("first_payment not between", value1, value2, "firstPayment");
            return (Criteria) this;
        }

        public Criteria andSecondPaymentIsNull() {
            addCriterion("second_payment is null");
            return (Criteria) this;
        }

        public Criteria andSecondPaymentIsNotNull() {
            addCriterion("second_payment is not null");
            return (Criteria) this;
        }

        public Criteria andSecondPaymentEqualTo(String value) {
            addCriterion("second_payment =", value, "secondPayment");
            return (Criteria) this;
        }

        public Criteria andSecondPaymentNotEqualTo(String value) {
            addCriterion("second_payment <>", value, "secondPayment");
            return (Criteria) this;
        }

        public Criteria andSecondPaymentGreaterThan(String value) {
            addCriterion("second_payment >", value, "secondPayment");
            return (Criteria) this;
        }

        public Criteria andSecondPaymentGreaterThanOrEqualTo(String value) {
            addCriterion("second_payment >=", value, "secondPayment");
            return (Criteria) this;
        }

        public Criteria andSecondPaymentLessThan(String value) {
            addCriterion("second_payment <", value, "secondPayment");
            return (Criteria) this;
        }

        public Criteria andSecondPaymentLessThanOrEqualTo(String value) {
            addCriterion("second_payment <=", value, "secondPayment");
            return (Criteria) this;
        }

        public Criteria andSecondPaymentLike(String value) {
            addCriterion("second_payment like", value, "secondPayment");
            return (Criteria) this;
        }

        public Criteria andSecondPaymentNotLike(String value) {
            addCriterion("second_payment not like", value, "secondPayment");
            return (Criteria) this;
        }

        public Criteria andSecondPaymentIn(List<String> values) {
            addCriterion("second_payment in", values, "secondPayment");
            return (Criteria) this;
        }

        public Criteria andSecondPaymentNotIn(List<String> values) {
            addCriterion("second_payment not in", values, "secondPayment");
            return (Criteria) this;
        }

        public Criteria andSecondPaymentBetween(String value1, String value2) {
            addCriterion("second_payment between", value1, value2, "secondPayment");
            return (Criteria) this;
        }

        public Criteria andSecondPaymentNotBetween(String value1, String value2) {
            addCriterion("second_payment not between", value1, value2, "secondPayment");
            return (Criteria) this;
        }

        public Criteria andCostIntrodutionIsNull() {
            addCriterion("cost_introdution is null");
            return (Criteria) this;
        }

        public Criteria andCostIntrodutionIsNotNull() {
            addCriterion("cost_introdution is not null");
            return (Criteria) this;
        }

        public Criteria andCostIntrodutionEqualTo(String value) {
            addCriterion("cost_introdution =", value, "costIntrodution");
            return (Criteria) this;
        }

        public Criteria andCostIntrodutionNotEqualTo(String value) {
            addCriterion("cost_introdution <>", value, "costIntrodution");
            return (Criteria) this;
        }

        public Criteria andCostIntrodutionGreaterThan(String value) {
            addCriterion("cost_introdution >", value, "costIntrodution");
            return (Criteria) this;
        }

        public Criteria andCostIntrodutionGreaterThanOrEqualTo(String value) {
            addCriterion("cost_introdution >=", value, "costIntrodution");
            return (Criteria) this;
        }

        public Criteria andCostIntrodutionLessThan(String value) {
            addCriterion("cost_introdution <", value, "costIntrodution");
            return (Criteria) this;
        }

        public Criteria andCostIntrodutionLessThanOrEqualTo(String value) {
            addCriterion("cost_introdution <=", value, "costIntrodution");
            return (Criteria) this;
        }

        public Criteria andCostIntrodutionLike(String value) {
            addCriterion("cost_introdution like", value, "costIntrodution");
            return (Criteria) this;
        }

        public Criteria andCostIntrodutionNotLike(String value) {
            addCriterion("cost_introdution not like", value, "costIntrodution");
            return (Criteria) this;
        }

        public Criteria andCostIntrodutionIn(List<String> values) {
            addCriterion("cost_introdution in", values, "costIntrodution");
            return (Criteria) this;
        }

        public Criteria andCostIntrodutionNotIn(List<String> values) {
            addCriterion("cost_introdution not in", values, "costIntrodution");
            return (Criteria) this;
        }

        public Criteria andCostIntrodutionBetween(String value1, String value2) {
            addCriterion("cost_introdution between", value1, value2, "costIntrodution");
            return (Criteria) this;
        }

        public Criteria andCostIntrodutionNotBetween(String value1, String value2) {
            addCriterion("cost_introdution not between", value1, value2, "costIntrodution");
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

        public Criteria andAssetInfoIsNull() {
            addCriterion("asset_info is null");
            return (Criteria) this;
        }

        public Criteria andAssetInfoIsNotNull() {
            addCriterion("asset_info is not null");
            return (Criteria) this;
        }

        public Criteria andAssetInfoEqualTo(String value) {
            addCriterion("asset_info =", value, "assetInfo");
            return (Criteria) this;
        }

        public Criteria andAssetInfoNotEqualTo(String value) {
            addCriterion("asset_info <>", value, "assetInfo");
            return (Criteria) this;
        }

        public Criteria andAssetInfoGreaterThan(String value) {
            addCriterion("asset_info >", value, "assetInfo");
            return (Criteria) this;
        }

        public Criteria andAssetInfoGreaterThanOrEqualTo(String value) {
            addCriterion("asset_info >=", value, "assetInfo");
            return (Criteria) this;
        }

        public Criteria andAssetInfoLessThan(String value) {
            addCriterion("asset_info <", value, "assetInfo");
            return (Criteria) this;
        }

        public Criteria andAssetInfoLessThanOrEqualTo(String value) {
            addCriterion("asset_info <=", value, "assetInfo");
            return (Criteria) this;
        }

        public Criteria andAssetInfoLike(String value) {
            addCriterion("asset_info like", value, "assetInfo");
            return (Criteria) this;
        }

        public Criteria andAssetInfoNotLike(String value) {
            addCriterion("asset_info not like", value, "assetInfo");
            return (Criteria) this;
        }

        public Criteria andAssetInfoIn(List<String> values) {
            addCriterion("asset_info in", values, "assetInfo");
            return (Criteria) this;
        }

        public Criteria andAssetInfoNotIn(List<String> values) {
            addCriterion("asset_info not in", values, "assetInfo");
            return (Criteria) this;
        }

        public Criteria andAssetInfoBetween(String value1, String value2) {
            addCriterion("asset_info between", value1, value2, "assetInfo");
            return (Criteria) this;
        }

        public Criteria andAssetInfoNotBetween(String value1, String value2) {
            addCriterion("asset_info not between", value1, value2, "assetInfo");
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

        public Criteria andAddressIsNull() {
            addCriterion("address is null");
            return (Criteria) this;
        }

        public Criteria andAddressIsNotNull() {
            addCriterion("address is not null");
            return (Criteria) this;
        }

        public Criteria andAddressEqualTo(String value) {
            addCriterion("address =", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotEqualTo(String value) {
            addCriterion("address <>", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThan(String value) {
            addCriterion("address >", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThanOrEqualTo(String value) {
            addCriterion("address >=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThan(String value) {
            addCriterion("address <", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThanOrEqualTo(String value) {
            addCriterion("address <=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLike(String value) {
            addCriterion("address like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotLike(String value) {
            addCriterion("address not like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressIn(List<String> values) {
            addCriterion("address in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotIn(List<String> values) {
            addCriterion("address not in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressBetween(String value1, String value2) {
            addCriterion("address between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotBetween(String value1, String value2) {
            addCriterion("address not between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andCorporateCodeIsNull() {
            addCriterion("corporate_code is null");
            return (Criteria) this;
        }

        public Criteria andCorporateCodeIsNotNull() {
            addCriterion("corporate_code is not null");
            return (Criteria) this;
        }

        public Criteria andCorporateCodeEqualTo(String value) {
            addCriterion("corporate_code =", value, "corporateCode");
            return (Criteria) this;
        }

        public Criteria andCorporateCodeNotEqualTo(String value) {
            addCriterion("corporate_code <>", value, "corporateCode");
            return (Criteria) this;
        }

        public Criteria andCorporateCodeGreaterThan(String value) {
            addCriterion("corporate_code >", value, "corporateCode");
            return (Criteria) this;
        }

        public Criteria andCorporateCodeGreaterThanOrEqualTo(String value) {
            addCriterion("corporate_code >=", value, "corporateCode");
            return (Criteria) this;
        }

        public Criteria andCorporateCodeLessThan(String value) {
            addCriterion("corporate_code <", value, "corporateCode");
            return (Criteria) this;
        }

        public Criteria andCorporateCodeLessThanOrEqualTo(String value) {
            addCriterion("corporate_code <=", value, "corporateCode");
            return (Criteria) this;
        }

        public Criteria andCorporateCodeLike(String value) {
            addCriterion("corporate_code like", value, "corporateCode");
            return (Criteria) this;
        }

        public Criteria andCorporateCodeNotLike(String value) {
            addCriterion("corporate_code not like", value, "corporateCode");
            return (Criteria) this;
        }

        public Criteria andCorporateCodeIn(List<String> values) {
            addCriterion("corporate_code in", values, "corporateCode");
            return (Criteria) this;
        }

        public Criteria andCorporateCodeNotIn(List<String> values) {
            addCriterion("corporate_code not in", values, "corporateCode");
            return (Criteria) this;
        }

        public Criteria andCorporateCodeBetween(String value1, String value2) {
            addCriterion("corporate_code between", value1, value2, "corporateCode");
            return (Criteria) this;
        }

        public Criteria andCorporateCodeNotBetween(String value1, String value2) {
            addCriterion("corporate_code not between", value1, value2, "corporateCode");
            return (Criteria) this;
        }

        public Criteria andRegistrationAddressIsNull() {
            addCriterion("registration_address is null");
            return (Criteria) this;
        }

        public Criteria andRegistrationAddressIsNotNull() {
            addCriterion("registration_address is not null");
            return (Criteria) this;
        }

        public Criteria andRegistrationAddressEqualTo(String value) {
            addCriterion("registration_address =", value, "registrationAddress");
            return (Criteria) this;
        }

        public Criteria andRegistrationAddressNotEqualTo(String value) {
            addCriterion("registration_address <>", value, "registrationAddress");
            return (Criteria) this;
        }

        public Criteria andRegistrationAddressGreaterThan(String value) {
            addCriterion("registration_address >", value, "registrationAddress");
            return (Criteria) this;
        }

        public Criteria andRegistrationAddressGreaterThanOrEqualTo(String value) {
            addCriterion("registration_address >=", value, "registrationAddress");
            return (Criteria) this;
        }

        public Criteria andRegistrationAddressLessThan(String value) {
            addCriterion("registration_address <", value, "registrationAddress");
            return (Criteria) this;
        }

        public Criteria andRegistrationAddressLessThanOrEqualTo(String value) {
            addCriterion("registration_address <=", value, "registrationAddress");
            return (Criteria) this;
        }

        public Criteria andRegistrationAddressLike(String value) {
            addCriterion("registration_address like", value, "registrationAddress");
            return (Criteria) this;
        }

        public Criteria andRegistrationAddressNotLike(String value) {
            addCriterion("registration_address not like", value, "registrationAddress");
            return (Criteria) this;
        }

        public Criteria andRegistrationAddressIn(List<String> values) {
            addCriterion("registration_address in", values, "registrationAddress");
            return (Criteria) this;
        }

        public Criteria andRegistrationAddressNotIn(List<String> values) {
            addCriterion("registration_address not in", values, "registrationAddress");
            return (Criteria) this;
        }

        public Criteria andRegistrationAddressBetween(String value1, String value2) {
            addCriterion("registration_address between", value1, value2, "registrationAddress");
            return (Criteria) this;
        }

        public Criteria andRegistrationAddressNotBetween(String value1, String value2) {
            addCriterion("registration_address not between", value1, value2, "registrationAddress");
            return (Criteria) this;
        }

        public Criteria andBorrowTypeIsNull() {
            addCriterion("borrow_type is null");
            return (Criteria) this;
        }

        public Criteria andBorrowTypeIsNotNull() {
            addCriterion("borrow_type is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowTypeEqualTo(Integer value) {
            addCriterion("borrow_type =", value, "borrowType");
            return (Criteria) this;
        }

        public Criteria andBorrowTypeNotEqualTo(Integer value) {
            addCriterion("borrow_type <>", value, "borrowType");
            return (Criteria) this;
        }

        public Criteria andBorrowTypeGreaterThan(Integer value) {
            addCriterion("borrow_type >", value, "borrowType");
            return (Criteria) this;
        }

        public Criteria andBorrowTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("borrow_type >=", value, "borrowType");
            return (Criteria) this;
        }

        public Criteria andBorrowTypeLessThan(Integer value) {
            addCriterion("borrow_type <", value, "borrowType");
            return (Criteria) this;
        }

        public Criteria andBorrowTypeLessThanOrEqualTo(Integer value) {
            addCriterion("borrow_type <=", value, "borrowType");
            return (Criteria) this;
        }

        public Criteria andBorrowTypeIn(List<Integer> values) {
            addCriterion("borrow_type in", values, "borrowType");
            return (Criteria) this;
        }

        public Criteria andBorrowTypeNotIn(List<Integer> values) {
            addCriterion("borrow_type not in", values, "borrowType");
            return (Criteria) this;
        }

        public Criteria andBorrowTypeBetween(Integer value1, Integer value2) {
            addCriterion("borrow_type between", value1, value2, "borrowType");
            return (Criteria) this;
        }

        public Criteria andBorrowTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("borrow_type not between", value1, value2, "borrowType");
            return (Criteria) this;
        }

        public Criteria andBorrowCompanyNameIsNull() {
            addCriterion("borrow_company_name is null");
            return (Criteria) this;
        }

        public Criteria andBorrowCompanyNameIsNotNull() {
            addCriterion("borrow_company_name is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowCompanyNameEqualTo(String value) {
            addCriterion("borrow_company_name =", value, "borrowCompanyName");
            return (Criteria) this;
        }

        public Criteria andBorrowCompanyNameNotEqualTo(String value) {
            addCriterion("borrow_company_name <>", value, "borrowCompanyName");
            return (Criteria) this;
        }

        public Criteria andBorrowCompanyNameGreaterThan(String value) {
            addCriterion("borrow_company_name >", value, "borrowCompanyName");
            return (Criteria) this;
        }

        public Criteria andBorrowCompanyNameGreaterThanOrEqualTo(String value) {
            addCriterion("borrow_company_name >=", value, "borrowCompanyName");
            return (Criteria) this;
        }

        public Criteria andBorrowCompanyNameLessThan(String value) {
            addCriterion("borrow_company_name <", value, "borrowCompanyName");
            return (Criteria) this;
        }

        public Criteria andBorrowCompanyNameLessThanOrEqualTo(String value) {
            addCriterion("borrow_company_name <=", value, "borrowCompanyName");
            return (Criteria) this;
        }

        public Criteria andBorrowCompanyNameLike(String value) {
            addCriterion("borrow_company_name like", value, "borrowCompanyName");
            return (Criteria) this;
        }

        public Criteria andBorrowCompanyNameNotLike(String value) {
            addCriterion("borrow_company_name not like", value, "borrowCompanyName");
            return (Criteria) this;
        }

        public Criteria andBorrowCompanyNameIn(List<String> values) {
            addCriterion("borrow_company_name in", values, "borrowCompanyName");
            return (Criteria) this;
        }

        public Criteria andBorrowCompanyNameNotIn(List<String> values) {
            addCriterion("borrow_company_name not in", values, "borrowCompanyName");
            return (Criteria) this;
        }

        public Criteria andBorrowCompanyNameBetween(String value1, String value2) {
            addCriterion("borrow_company_name between", value1, value2, "borrowCompanyName");
            return (Criteria) this;
        }

        public Criteria andBorrowCompanyNameNotBetween(String value1, String value2) {
            addCriterion("borrow_company_name not between", value1, value2, "borrowCompanyName");
            return (Criteria) this;
        }

        public Criteria andFinancialSituationIsNull() {
            addCriterion("financial_situation is null");
            return (Criteria) this;
        }

        public Criteria andFinancialSituationIsNotNull() {
            addCriterion("financial_situation is not null");
            return (Criteria) this;
        }

        public Criteria andFinancialSituationEqualTo(String value) {
            addCriterion("financial_situation =", value, "financialSituation");
            return (Criteria) this;
        }

        public Criteria andFinancialSituationNotEqualTo(String value) {
            addCriterion("financial_situation <>", value, "financialSituation");
            return (Criteria) this;
        }

        public Criteria andFinancialSituationGreaterThan(String value) {
            addCriterion("financial_situation >", value, "financialSituation");
            return (Criteria) this;
        }

        public Criteria andFinancialSituationGreaterThanOrEqualTo(String value) {
            addCriterion("financial_situation >=", value, "financialSituation");
            return (Criteria) this;
        }

        public Criteria andFinancialSituationLessThan(String value) {
            addCriterion("financial_situation <", value, "financialSituation");
            return (Criteria) this;
        }

        public Criteria andFinancialSituationLessThanOrEqualTo(String value) {
            addCriterion("financial_situation <=", value, "financialSituation");
            return (Criteria) this;
        }

        public Criteria andFinancialSituationLike(String value) {
            addCriterion("financial_situation like", value, "financialSituation");
            return (Criteria) this;
        }

        public Criteria andFinancialSituationNotLike(String value) {
            addCriterion("financial_situation not like", value, "financialSituation");
            return (Criteria) this;
        }

        public Criteria andFinancialSituationIn(List<String> values) {
            addCriterion("financial_situation in", values, "financialSituation");
            return (Criteria) this;
        }

        public Criteria andFinancialSituationNotIn(List<String> values) {
            addCriterion("financial_situation not in", values, "financialSituation");
            return (Criteria) this;
        }

        public Criteria andFinancialSituationBetween(String value1, String value2) {
            addCriterion("financial_situation between", value1, value2, "financialSituation");
            return (Criteria) this;
        }

        public Criteria andFinancialSituationNotBetween(String value1, String value2) {
            addCriterion("financial_situation not between", value1, value2, "financialSituation");
            return (Criteria) this;
        }

        public Criteria andLegalPersonIsNull() {
            addCriterion("legal_person is null");
            return (Criteria) this;
        }

        public Criteria andLegalPersonIsNotNull() {
            addCriterion("legal_person is not null");
            return (Criteria) this;
        }

        public Criteria andLegalPersonEqualTo(String value) {
            addCriterion("legal_person =", value, "legalPerson");
            return (Criteria) this;
        }

        public Criteria andLegalPersonNotEqualTo(String value) {
            addCriterion("legal_person <>", value, "legalPerson");
            return (Criteria) this;
        }

        public Criteria andLegalPersonGreaterThan(String value) {
            addCriterion("legal_person >", value, "legalPerson");
            return (Criteria) this;
        }

        public Criteria andLegalPersonGreaterThanOrEqualTo(String value) {
            addCriterion("legal_person >=", value, "legalPerson");
            return (Criteria) this;
        }

        public Criteria andLegalPersonLessThan(String value) {
            addCriterion("legal_person <", value, "legalPerson");
            return (Criteria) this;
        }

        public Criteria andLegalPersonLessThanOrEqualTo(String value) {
            addCriterion("legal_person <=", value, "legalPerson");
            return (Criteria) this;
        }

        public Criteria andLegalPersonLike(String value) {
            addCriterion("legal_person like", value, "legalPerson");
            return (Criteria) this;
        }

        public Criteria andLegalPersonNotLike(String value) {
            addCriterion("legal_person not like", value, "legalPerson");
            return (Criteria) this;
        }

        public Criteria andLegalPersonIn(List<String> values) {
            addCriterion("legal_person in", values, "legalPerson");
            return (Criteria) this;
        }

        public Criteria andLegalPersonNotIn(List<String> values) {
            addCriterion("legal_person not in", values, "legalPerson");
            return (Criteria) this;
        }

        public Criteria andLegalPersonBetween(String value1, String value2) {
            addCriterion("legal_person between", value1, value2, "legalPerson");
            return (Criteria) this;
        }

        public Criteria andLegalPersonNotBetween(String value1, String value2) {
            addCriterion("legal_person not between", value1, value2, "legalPerson");
            return (Criteria) this;
        }

        public Criteria andRegistrationAreaIsNull() {
            addCriterion("registration_area is null");
            return (Criteria) this;
        }

        public Criteria andRegistrationAreaIsNotNull() {
            addCriterion("registration_area is not null");
            return (Criteria) this;
        }

        public Criteria andRegistrationAreaEqualTo(String value) {
            addCriterion("registration_area =", value, "registrationArea");
            return (Criteria) this;
        }

        public Criteria andRegistrationAreaNotEqualTo(String value) {
            addCriterion("registration_area <>", value, "registrationArea");
            return (Criteria) this;
        }

        public Criteria andRegistrationAreaGreaterThan(String value) {
            addCriterion("registration_area >", value, "registrationArea");
            return (Criteria) this;
        }

        public Criteria andRegistrationAreaGreaterThanOrEqualTo(String value) {
            addCriterion("registration_area >=", value, "registrationArea");
            return (Criteria) this;
        }

        public Criteria andRegistrationAreaLessThan(String value) {
            addCriterion("registration_area <", value, "registrationArea");
            return (Criteria) this;
        }

        public Criteria andRegistrationAreaLessThanOrEqualTo(String value) {
            addCriterion("registration_area <=", value, "registrationArea");
            return (Criteria) this;
        }

        public Criteria andRegistrationAreaLike(String value) {
            addCriterion("registration_area like", value, "registrationArea");
            return (Criteria) this;
        }

        public Criteria andRegistrationAreaNotLike(String value) {
            addCriterion("registration_area not like", value, "registrationArea");
            return (Criteria) this;
        }

        public Criteria andRegistrationAreaIn(List<String> values) {
            addCriterion("registration_area in", values, "registrationArea");
            return (Criteria) this;
        }

        public Criteria andRegistrationAreaNotIn(List<String> values) {
            addCriterion("registration_area not in", values, "registrationArea");
            return (Criteria) this;
        }

        public Criteria andRegistrationAreaBetween(String value1, String value2) {
            addCriterion("registration_area between", value1, value2, "registrationArea");
            return (Criteria) this;
        }

        public Criteria andRegistrationAreaNotBetween(String value1, String value2) {
            addCriterion("registration_area not between", value1, value2, "registrationArea");
            return (Criteria) this;
        }

        public Criteria andRegistrationDateIsNull() {
            addCriterion("registration_date is null");
            return (Criteria) this;
        }

        public Criteria andRegistrationDateIsNotNull() {
            addCriterion("registration_date is not null");
            return (Criteria) this;
        }

        public Criteria andRegistrationDateEqualTo(String value) {
            addCriterion("registration_date =", value, "registrationDate");
            return (Criteria) this;
        }

        public Criteria andRegistrationDateNotEqualTo(String value) {
            addCriterion("registration_date <>", value, "registrationDate");
            return (Criteria) this;
        }

        public Criteria andRegistrationDateGreaterThan(String value) {
            addCriterion("registration_date >", value, "registrationDate");
            return (Criteria) this;
        }

        public Criteria andRegistrationDateGreaterThanOrEqualTo(String value) {
            addCriterion("registration_date >=", value, "registrationDate");
            return (Criteria) this;
        }

        public Criteria andRegistrationDateLessThan(String value) {
            addCriterion("registration_date <", value, "registrationDate");
            return (Criteria) this;
        }

        public Criteria andRegistrationDateLessThanOrEqualTo(String value) {
            addCriterion("registration_date <=", value, "registrationDate");
            return (Criteria) this;
        }

        public Criteria andRegistrationDateLike(String value) {
            addCriterion("registration_date like", value, "registrationDate");
            return (Criteria) this;
        }

        public Criteria andRegistrationDateNotLike(String value) {
            addCriterion("registration_date not like", value, "registrationDate");
            return (Criteria) this;
        }

        public Criteria andRegistrationDateIn(List<String> values) {
            addCriterion("registration_date in", values, "registrationDate");
            return (Criteria) this;
        }

        public Criteria andRegistrationDateNotIn(List<String> values) {
            addCriterion("registration_date not in", values, "registrationDate");
            return (Criteria) this;
        }

        public Criteria andRegistrationDateBetween(String value1, String value2) {
            addCriterion("registration_date between", value1, value2, "registrationDate");
            return (Criteria) this;
        }

        public Criteria andRegistrationDateNotBetween(String value1, String value2) {
            addCriterion("registration_date not between", value1, value2, "registrationDate");
            return (Criteria) this;
        }

        public Criteria andMainBusinessIsNull() {
            addCriterion("main_business is null");
            return (Criteria) this;
        }

        public Criteria andMainBusinessIsNotNull() {
            addCriterion("main_business is not null");
            return (Criteria) this;
        }

        public Criteria andMainBusinessEqualTo(String value) {
            addCriterion("main_business =", value, "mainBusiness");
            return (Criteria) this;
        }

        public Criteria andMainBusinessNotEqualTo(String value) {
            addCriterion("main_business <>", value, "mainBusiness");
            return (Criteria) this;
        }

        public Criteria andMainBusinessGreaterThan(String value) {
            addCriterion("main_business >", value, "mainBusiness");
            return (Criteria) this;
        }

        public Criteria andMainBusinessGreaterThanOrEqualTo(String value) {
            addCriterion("main_business >=", value, "mainBusiness");
            return (Criteria) this;
        }

        public Criteria andMainBusinessLessThan(String value) {
            addCriterion("main_business <", value, "mainBusiness");
            return (Criteria) this;
        }

        public Criteria andMainBusinessLessThanOrEqualTo(String value) {
            addCriterion("main_business <=", value, "mainBusiness");
            return (Criteria) this;
        }

        public Criteria andMainBusinessLike(String value) {
            addCriterion("main_business like", value, "mainBusiness");
            return (Criteria) this;
        }

        public Criteria andMainBusinessNotLike(String value) {
            addCriterion("main_business not like", value, "mainBusiness");
            return (Criteria) this;
        }

        public Criteria andMainBusinessIn(List<String> values) {
            addCriterion("main_business in", values, "mainBusiness");
            return (Criteria) this;
        }

        public Criteria andMainBusinessNotIn(List<String> values) {
            addCriterion("main_business not in", values, "mainBusiness");
            return (Criteria) this;
        }

        public Criteria andMainBusinessBetween(String value1, String value2) {
            addCriterion("main_business between", value1, value2, "mainBusiness");
            return (Criteria) this;
        }

        public Criteria andMainBusinessNotBetween(String value1, String value2) {
            addCriterion("main_business not between", value1, value2, "mainBusiness");
            return (Criteria) this;
        }

        public Criteria andUnifiedSocialCreditCodeIsNull() {
            addCriterion("unified_social_credit_code is null");
            return (Criteria) this;
        }

        public Criteria andUnifiedSocialCreditCodeIsNotNull() {
            addCriterion("unified_social_credit_code is not null");
            return (Criteria) this;
        }

        public Criteria andUnifiedSocialCreditCodeEqualTo(String value) {
            addCriterion("unified_social_credit_code =", value, "unifiedSocialCreditCode");
            return (Criteria) this;
        }

        public Criteria andUnifiedSocialCreditCodeNotEqualTo(String value) {
            addCriterion("unified_social_credit_code <>", value, "unifiedSocialCreditCode");
            return (Criteria) this;
        }

        public Criteria andUnifiedSocialCreditCodeGreaterThan(String value) {
            addCriterion("unified_social_credit_code >", value, "unifiedSocialCreditCode");
            return (Criteria) this;
        }

        public Criteria andUnifiedSocialCreditCodeGreaterThanOrEqualTo(String value) {
            addCriterion("unified_social_credit_code >=", value, "unifiedSocialCreditCode");
            return (Criteria) this;
        }

        public Criteria andUnifiedSocialCreditCodeLessThan(String value) {
            addCriterion("unified_social_credit_code <", value, "unifiedSocialCreditCode");
            return (Criteria) this;
        }

        public Criteria andUnifiedSocialCreditCodeLessThanOrEqualTo(String value) {
            addCriterion("unified_social_credit_code <=", value, "unifiedSocialCreditCode");
            return (Criteria) this;
        }

        public Criteria andUnifiedSocialCreditCodeLike(String value) {
            addCriterion("unified_social_credit_code like", value, "unifiedSocialCreditCode");
            return (Criteria) this;
        }

        public Criteria andUnifiedSocialCreditCodeNotLike(String value) {
            addCriterion("unified_social_credit_code not like", value, "unifiedSocialCreditCode");
            return (Criteria) this;
        }

        public Criteria andUnifiedSocialCreditCodeIn(List<String> values) {
            addCriterion("unified_social_credit_code in", values, "unifiedSocialCreditCode");
            return (Criteria) this;
        }

        public Criteria andUnifiedSocialCreditCodeNotIn(List<String> values) {
            addCriterion("unified_social_credit_code not in", values, "unifiedSocialCreditCode");
            return (Criteria) this;
        }

        public Criteria andUnifiedSocialCreditCodeBetween(String value1, String value2) {
            addCriterion("unified_social_credit_code between", value1, value2, "unifiedSocialCreditCode");
            return (Criteria) this;
        }

        public Criteria andUnifiedSocialCreditCodeNotBetween(String value1, String value2) {
            addCriterion("unified_social_credit_code not between", value1, value2, "unifiedSocialCreditCode");
            return (Criteria) this;
        }

        public Criteria andRegisteredCapitalIsNull() {
            addCriterion("registered_capital is null");
            return (Criteria) this;
        }

        public Criteria andRegisteredCapitalIsNotNull() {
            addCriterion("registered_capital is not null");
            return (Criteria) this;
        }

        public Criteria andRegisteredCapitalEqualTo(String value) {
            addCriterion("registered_capital =", value, "registeredCapital");
            return (Criteria) this;
        }

        public Criteria andRegisteredCapitalNotEqualTo(String value) {
            addCriterion("registered_capital <>", value, "registeredCapital");
            return (Criteria) this;
        }

        public Criteria andRegisteredCapitalGreaterThan(String value) {
            addCriterion("registered_capital >", value, "registeredCapital");
            return (Criteria) this;
        }

        public Criteria andRegisteredCapitalGreaterThanOrEqualTo(String value) {
            addCriterion("registered_capital >=", value, "registeredCapital");
            return (Criteria) this;
        }

        public Criteria andRegisteredCapitalLessThan(String value) {
            addCriterion("registered_capital <", value, "registeredCapital");
            return (Criteria) this;
        }

        public Criteria andRegisteredCapitalLessThanOrEqualTo(String value) {
            addCriterion("registered_capital <=", value, "registeredCapital");
            return (Criteria) this;
        }

        public Criteria andRegisteredCapitalLike(String value) {
            addCriterion("registered_capital like", value, "registeredCapital");
            return (Criteria) this;
        }

        public Criteria andRegisteredCapitalNotLike(String value) {
            addCriterion("registered_capital not like", value, "registeredCapital");
            return (Criteria) this;
        }

        public Criteria andRegisteredCapitalIn(List<String> values) {
            addCriterion("registered_capital in", values, "registeredCapital");
            return (Criteria) this;
        }

        public Criteria andRegisteredCapitalNotIn(List<String> values) {
            addCriterion("registered_capital not in", values, "registeredCapital");
            return (Criteria) this;
        }

        public Criteria andRegisteredCapitalBetween(String value1, String value2) {
            addCriterion("registered_capital between", value1, value2, "registeredCapital");
            return (Criteria) this;
        }

        public Criteria andRegisteredCapitalNotBetween(String value1, String value2) {
            addCriterion("registered_capital not between", value1, value2, "registeredCapital");
            return (Criteria) this;
        }

        public Criteria andIndustryInvolvedIsNull() {
            addCriterion("industry_involved is null");
            return (Criteria) this;
        }

        public Criteria andIndustryInvolvedIsNotNull() {
            addCriterion("industry_involved is not null");
            return (Criteria) this;
        }

        public Criteria andIndustryInvolvedEqualTo(String value) {
            addCriterion("industry_involved =", value, "industryInvolved");
            return (Criteria) this;
        }

        public Criteria andIndustryInvolvedNotEqualTo(String value) {
            addCriterion("industry_involved <>", value, "industryInvolved");
            return (Criteria) this;
        }

        public Criteria andIndustryInvolvedGreaterThan(String value) {
            addCriterion("industry_involved >", value, "industryInvolved");
            return (Criteria) this;
        }

        public Criteria andIndustryInvolvedGreaterThanOrEqualTo(String value) {
            addCriterion("industry_involved >=", value, "industryInvolved");
            return (Criteria) this;
        }

        public Criteria andIndustryInvolvedLessThan(String value) {
            addCriterion("industry_involved <", value, "industryInvolved");
            return (Criteria) this;
        }

        public Criteria andIndustryInvolvedLessThanOrEqualTo(String value) {
            addCriterion("industry_involved <=", value, "industryInvolved");
            return (Criteria) this;
        }

        public Criteria andIndustryInvolvedLike(String value) {
            addCriterion("industry_involved like", value, "industryInvolved");
            return (Criteria) this;
        }

        public Criteria andIndustryInvolvedNotLike(String value) {
            addCriterion("industry_involved not like", value, "industryInvolved");
            return (Criteria) this;
        }

        public Criteria andIndustryInvolvedIn(List<String> values) {
            addCriterion("industry_involved in", values, "industryInvolved");
            return (Criteria) this;
        }

        public Criteria andIndustryInvolvedNotIn(List<String> values) {
            addCriterion("industry_involved not in", values, "industryInvolved");
            return (Criteria) this;
        }

        public Criteria andIndustryInvolvedBetween(String value1, String value2) {
            addCriterion("industry_involved between", value1, value2, "industryInvolved");
            return (Criteria) this;
        }

        public Criteria andIndustryInvolvedNotBetween(String value1, String value2) {
            addCriterion("industry_involved not between", value1, value2, "industryInvolved");
            return (Criteria) this;
        }

        public Criteria andAssetAttributesIsNull() {
            addCriterion("asset_attributes is null");
            return (Criteria) this;
        }

        public Criteria andAssetAttributesIsNotNull() {
            addCriterion("asset_attributes is not null");
            return (Criteria) this;
        }

        public Criteria andAssetAttributesEqualTo(Integer value) {
            addCriterion("asset_attributes =", value, "assetAttributes");
            return (Criteria) this;
        }

        public Criteria andAssetAttributesNotEqualTo(Integer value) {
            addCriterion("asset_attributes <>", value, "assetAttributes");
            return (Criteria) this;
        }

        public Criteria andAssetAttributesGreaterThan(Integer value) {
            addCriterion("asset_attributes >", value, "assetAttributes");
            return (Criteria) this;
        }

        public Criteria andAssetAttributesGreaterThanOrEqualTo(Integer value) {
            addCriterion("asset_attributes >=", value, "assetAttributes");
            return (Criteria) this;
        }

        public Criteria andAssetAttributesLessThan(Integer value) {
            addCriterion("asset_attributes <", value, "assetAttributes");
            return (Criteria) this;
        }

        public Criteria andAssetAttributesLessThanOrEqualTo(Integer value) {
            addCriterion("asset_attributes <=", value, "assetAttributes");
            return (Criteria) this;
        }

        public Criteria andAssetAttributesIn(List<Integer> values) {
            addCriterion("asset_attributes in", values, "assetAttributes");
            return (Criteria) this;
        }

        public Criteria andAssetAttributesNotIn(List<Integer> values) {
            addCriterion("asset_attributes not in", values, "assetAttributes");
            return (Criteria) this;
        }

        public Criteria andAssetAttributesBetween(Integer value1, Integer value2) {
            addCriterion("asset_attributes between", value1, value2, "assetAttributes");
            return (Criteria) this;
        }

        public Criteria andAssetAttributesNotBetween(Integer value1, Integer value2) {
            addCriterion("asset_attributes not between", value1, value2, "assetAttributes");
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