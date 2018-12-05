package com.hyjf.am.trade.dao.model.auto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HjhAssetRiskInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public HjhAssetRiskInfoExample() {
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

        public Criteria andAmazonInfoIsNull() {
            addCriterion("amazon_info is null");
            return (Criteria) this;
        }

        public Criteria andAmazonInfoIsNotNull() {
            addCriterion("amazon_info is not null");
            return (Criteria) this;
        }

        public Criteria andAmazonInfoEqualTo(String value) {
            addCriterion("amazon_info =", value, "amazonInfo");
            return (Criteria) this;
        }

        public Criteria andAmazonInfoNotEqualTo(String value) {
            addCriterion("amazon_info <>", value, "amazonInfo");
            return (Criteria) this;
        }

        public Criteria andAmazonInfoGreaterThan(String value) {
            addCriterion("amazon_info >", value, "amazonInfo");
            return (Criteria) this;
        }

        public Criteria andAmazonInfoGreaterThanOrEqualTo(String value) {
            addCriterion("amazon_info >=", value, "amazonInfo");
            return (Criteria) this;
        }

        public Criteria andAmazonInfoLessThan(String value) {
            addCriterion("amazon_info <", value, "amazonInfo");
            return (Criteria) this;
        }

        public Criteria andAmazonInfoLessThanOrEqualTo(String value) {
            addCriterion("amazon_info <=", value, "amazonInfo");
            return (Criteria) this;
        }

        public Criteria andAmazonInfoLike(String value) {
            addCriterion("amazon_info like", value, "amazonInfo");
            return (Criteria) this;
        }

        public Criteria andAmazonInfoNotLike(String value) {
            addCriterion("amazon_info not like", value, "amazonInfo");
            return (Criteria) this;
        }

        public Criteria andAmazonInfoIn(List<String> values) {
            addCriterion("amazon_info in", values, "amazonInfo");
            return (Criteria) this;
        }

        public Criteria andAmazonInfoNotIn(List<String> values) {
            addCriterion("amazon_info not in", values, "amazonInfo");
            return (Criteria) this;
        }

        public Criteria andAmazonInfoBetween(String value1, String value2) {
            addCriterion("amazon_info between", value1, value2, "amazonInfo");
            return (Criteria) this;
        }

        public Criteria andAmazonInfoNotBetween(String value1, String value2) {
            addCriterion("amazon_info not between", value1, value2, "amazonInfo");
            return (Criteria) this;
        }

        public Criteria andEbayInfoIsNull() {
            addCriterion("ebay_info is null");
            return (Criteria) this;
        }

        public Criteria andEbayInfoIsNotNull() {
            addCriterion("ebay_info is not null");
            return (Criteria) this;
        }

        public Criteria andEbayInfoEqualTo(String value) {
            addCriterion("ebay_info =", value, "ebayInfo");
            return (Criteria) this;
        }

        public Criteria andEbayInfoNotEqualTo(String value) {
            addCriterion("ebay_info <>", value, "ebayInfo");
            return (Criteria) this;
        }

        public Criteria andEbayInfoGreaterThan(String value) {
            addCriterion("ebay_info >", value, "ebayInfo");
            return (Criteria) this;
        }

        public Criteria andEbayInfoGreaterThanOrEqualTo(String value) {
            addCriterion("ebay_info >=", value, "ebayInfo");
            return (Criteria) this;
        }

        public Criteria andEbayInfoLessThan(String value) {
            addCriterion("ebay_info <", value, "ebayInfo");
            return (Criteria) this;
        }

        public Criteria andEbayInfoLessThanOrEqualTo(String value) {
            addCriterion("ebay_info <=", value, "ebayInfo");
            return (Criteria) this;
        }

        public Criteria andEbayInfoLike(String value) {
            addCriterion("ebay_info like", value, "ebayInfo");
            return (Criteria) this;
        }

        public Criteria andEbayInfoNotLike(String value) {
            addCriterion("ebay_info not like", value, "ebayInfo");
            return (Criteria) this;
        }

        public Criteria andEbayInfoIn(List<String> values) {
            addCriterion("ebay_info in", values, "ebayInfo");
            return (Criteria) this;
        }

        public Criteria andEbayInfoNotIn(List<String> values) {
            addCriterion("ebay_info not in", values, "ebayInfo");
            return (Criteria) this;
        }

        public Criteria andEbayInfoBetween(String value1, String value2) {
            addCriterion("ebay_info between", value1, value2, "ebayInfo");
            return (Criteria) this;
        }

        public Criteria andEbayInfoNotBetween(String value1, String value2) {
            addCriterion("ebay_info not between", value1, value2, "ebayInfo");
            return (Criteria) this;
        }

        public Criteria andJingdongInfoIsNull() {
            addCriterion("jingdong_info is null");
            return (Criteria) this;
        }

        public Criteria andJingdongInfoIsNotNull() {
            addCriterion("jingdong_info is not null");
            return (Criteria) this;
        }

        public Criteria andJingdongInfoEqualTo(String value) {
            addCriterion("jingdong_info =", value, "jingdongInfo");
            return (Criteria) this;
        }

        public Criteria andJingdongInfoNotEqualTo(String value) {
            addCriterion("jingdong_info <>", value, "jingdongInfo");
            return (Criteria) this;
        }

        public Criteria andJingdongInfoGreaterThan(String value) {
            addCriterion("jingdong_info >", value, "jingdongInfo");
            return (Criteria) this;
        }

        public Criteria andJingdongInfoGreaterThanOrEqualTo(String value) {
            addCriterion("jingdong_info >=", value, "jingdongInfo");
            return (Criteria) this;
        }

        public Criteria andJingdongInfoLessThan(String value) {
            addCriterion("jingdong_info <", value, "jingdongInfo");
            return (Criteria) this;
        }

        public Criteria andJingdongInfoLessThanOrEqualTo(String value) {
            addCriterion("jingdong_info <=", value, "jingdongInfo");
            return (Criteria) this;
        }

        public Criteria andJingdongInfoLike(String value) {
            addCriterion("jingdong_info like", value, "jingdongInfo");
            return (Criteria) this;
        }

        public Criteria andJingdongInfoNotLike(String value) {
            addCriterion("jingdong_info not like", value, "jingdongInfo");
            return (Criteria) this;
        }

        public Criteria andJingdongInfoIn(List<String> values) {
            addCriterion("jingdong_info in", values, "jingdongInfo");
            return (Criteria) this;
        }

        public Criteria andJingdongInfoNotIn(List<String> values) {
            addCriterion("jingdong_info not in", values, "jingdongInfo");
            return (Criteria) this;
        }

        public Criteria andJingdongInfoBetween(String value1, String value2) {
            addCriterion("jingdong_info between", value1, value2, "jingdongInfo");
            return (Criteria) this;
        }

        public Criteria andJingdongInfoNotBetween(String value1, String value2) {
            addCriterion("jingdong_info not between", value1, value2, "jingdongInfo");
            return (Criteria) this;
        }

        public Criteria andTaobaoInfoIsNull() {
            addCriterion("taobao_info is null");
            return (Criteria) this;
        }

        public Criteria andTaobaoInfoIsNotNull() {
            addCriterion("taobao_info is not null");
            return (Criteria) this;
        }

        public Criteria andTaobaoInfoEqualTo(String value) {
            addCriterion("taobao_info =", value, "taobaoInfo");
            return (Criteria) this;
        }

        public Criteria andTaobaoInfoNotEqualTo(String value) {
            addCriterion("taobao_info <>", value, "taobaoInfo");
            return (Criteria) this;
        }

        public Criteria andTaobaoInfoGreaterThan(String value) {
            addCriterion("taobao_info >", value, "taobaoInfo");
            return (Criteria) this;
        }

        public Criteria andTaobaoInfoGreaterThanOrEqualTo(String value) {
            addCriterion("taobao_info >=", value, "taobaoInfo");
            return (Criteria) this;
        }

        public Criteria andTaobaoInfoLessThan(String value) {
            addCriterion("taobao_info <", value, "taobaoInfo");
            return (Criteria) this;
        }

        public Criteria andTaobaoInfoLessThanOrEqualTo(String value) {
            addCriterion("taobao_info <=", value, "taobaoInfo");
            return (Criteria) this;
        }

        public Criteria andTaobaoInfoLike(String value) {
            addCriterion("taobao_info like", value, "taobaoInfo");
            return (Criteria) this;
        }

        public Criteria andTaobaoInfoNotLike(String value) {
            addCriterion("taobao_info not like", value, "taobaoInfo");
            return (Criteria) this;
        }

        public Criteria andTaobaoInfoIn(List<String> values) {
            addCriterion("taobao_info in", values, "taobaoInfo");
            return (Criteria) this;
        }

        public Criteria andTaobaoInfoNotIn(List<String> values) {
            addCriterion("taobao_info not in", values, "taobaoInfo");
            return (Criteria) this;
        }

        public Criteria andTaobaoInfoBetween(String value1, String value2) {
            addCriterion("taobao_info between", value1, value2, "taobaoInfo");
            return (Criteria) this;
        }

        public Criteria andTaobaoInfoNotBetween(String value1, String value2) {
            addCriterion("taobao_info not between", value1, value2, "taobaoInfo");
            return (Criteria) this;
        }

        public Criteria andTianmaoInfoIsNull() {
            addCriterion("tianmao_info is null");
            return (Criteria) this;
        }

        public Criteria andTianmaoInfoIsNotNull() {
            addCriterion("tianmao_info is not null");
            return (Criteria) this;
        }

        public Criteria andTianmaoInfoEqualTo(String value) {
            addCriterion("tianmao_info =", value, "tianmaoInfo");
            return (Criteria) this;
        }

        public Criteria andTianmaoInfoNotEqualTo(String value) {
            addCriterion("tianmao_info <>", value, "tianmaoInfo");
            return (Criteria) this;
        }

        public Criteria andTianmaoInfoGreaterThan(String value) {
            addCriterion("tianmao_info >", value, "tianmaoInfo");
            return (Criteria) this;
        }

        public Criteria andTianmaoInfoGreaterThanOrEqualTo(String value) {
            addCriterion("tianmao_info >=", value, "tianmaoInfo");
            return (Criteria) this;
        }

        public Criteria andTianmaoInfoLessThan(String value) {
            addCriterion("tianmao_info <", value, "tianmaoInfo");
            return (Criteria) this;
        }

        public Criteria andTianmaoInfoLessThanOrEqualTo(String value) {
            addCriterion("tianmao_info <=", value, "tianmaoInfo");
            return (Criteria) this;
        }

        public Criteria andTianmaoInfoLike(String value) {
            addCriterion("tianmao_info like", value, "tianmaoInfo");
            return (Criteria) this;
        }

        public Criteria andTianmaoInfoNotLike(String value) {
            addCriterion("tianmao_info not like", value, "tianmaoInfo");
            return (Criteria) this;
        }

        public Criteria andTianmaoInfoIn(List<String> values) {
            addCriterion("tianmao_info in", values, "tianmaoInfo");
            return (Criteria) this;
        }

        public Criteria andTianmaoInfoNotIn(List<String> values) {
            addCriterion("tianmao_info not in", values, "tianmaoInfo");
            return (Criteria) this;
        }

        public Criteria andTianmaoInfoBetween(String value1, String value2) {
            addCriterion("tianmao_info between", value1, value2, "tianmaoInfo");
            return (Criteria) this;
        }

        public Criteria andTianmaoInfoNotBetween(String value1, String value2) {
            addCriterion("tianmao_info not between", value1, value2, "tianmaoInfo");
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

        public Criteria andDelFlgIsNull() {
            addCriterion("del_flg is null");
            return (Criteria) this;
        }

        public Criteria andDelFlgIsNotNull() {
            addCriterion("del_flg is not null");
            return (Criteria) this;
        }

        public Criteria andDelFlgEqualTo(Boolean value) {
            addCriterion("del_flg =", value, "delFlg");
            return (Criteria) this;
        }

        public Criteria andDelFlgNotEqualTo(Boolean value) {
            addCriterion("del_flg <>", value, "delFlg");
            return (Criteria) this;
        }

        public Criteria andDelFlgGreaterThan(Boolean value) {
            addCriterion("del_flg >", value, "delFlg");
            return (Criteria) this;
        }

        public Criteria andDelFlgGreaterThanOrEqualTo(Boolean value) {
            addCriterion("del_flg >=", value, "delFlg");
            return (Criteria) this;
        }

        public Criteria andDelFlgLessThan(Boolean value) {
            addCriterion("del_flg <", value, "delFlg");
            return (Criteria) this;
        }

        public Criteria andDelFlgLessThanOrEqualTo(Boolean value) {
            addCriterion("del_flg <=", value, "delFlg");
            return (Criteria) this;
        }

        public Criteria andDelFlgIn(List<Boolean> values) {
            addCriterion("del_flg in", values, "delFlg");
            return (Criteria) this;
        }

        public Criteria andDelFlgNotIn(List<Boolean> values) {
            addCriterion("del_flg not in", values, "delFlg");
            return (Criteria) this;
        }

        public Criteria andDelFlgBetween(Boolean value1, Boolean value2) {
            addCriterion("del_flg between", value1, value2, "delFlg");
            return (Criteria) this;
        }

        public Criteria andDelFlgNotBetween(Boolean value1, Boolean value2) {
            addCriterion("del_flg not between", value1, value2, "delFlg");
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