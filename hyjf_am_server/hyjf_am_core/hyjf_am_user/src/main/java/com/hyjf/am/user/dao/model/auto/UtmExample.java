package com.hyjf.am.user.dao.model.auto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UtmExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public UtmExample() {
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

        public Criteria andUtmIdIsNull() {
            addCriterion("utm_id is null");
            return (Criteria) this;
        }

        public Criteria andUtmIdIsNotNull() {
            addCriterion("utm_id is not null");
            return (Criteria) this;
        }

        public Criteria andUtmIdEqualTo(Integer value) {
            addCriterion("utm_id =", value, "utmId");
            return (Criteria) this;
        }

        public Criteria andUtmIdNotEqualTo(Integer value) {
            addCriterion("utm_id <>", value, "utmId");
            return (Criteria) this;
        }

        public Criteria andUtmIdGreaterThan(Integer value) {
            addCriterion("utm_id >", value, "utmId");
            return (Criteria) this;
        }

        public Criteria andUtmIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("utm_id >=", value, "utmId");
            return (Criteria) this;
        }

        public Criteria andUtmIdLessThan(Integer value) {
            addCriterion("utm_id <", value, "utmId");
            return (Criteria) this;
        }

        public Criteria andUtmIdLessThanOrEqualTo(Integer value) {
            addCriterion("utm_id <=", value, "utmId");
            return (Criteria) this;
        }

        public Criteria andUtmIdIn(List<Integer> values) {
            addCriterion("utm_id in", values, "utmId");
            return (Criteria) this;
        }

        public Criteria andUtmIdNotIn(List<Integer> values) {
            addCriterion("utm_id not in", values, "utmId");
            return (Criteria) this;
        }

        public Criteria andUtmIdBetween(Integer value1, Integer value2) {
            addCriterion("utm_id between", value1, value2, "utmId");
            return (Criteria) this;
        }

        public Criteria andUtmIdNotBetween(Integer value1, Integer value2) {
            addCriterion("utm_id not between", value1, value2, "utmId");
            return (Criteria) this;
        }

        public Criteria andUtmSourceIsNull() {
            addCriterion("utm_source is null");
            return (Criteria) this;
        }

        public Criteria andUtmSourceIsNotNull() {
            addCriterion("utm_source is not null");
            return (Criteria) this;
        }

        public Criteria andUtmSourceEqualTo(String value) {
            addCriterion("utm_source =", value, "utmSource");
            return (Criteria) this;
        }

        public Criteria andUtmSourceNotEqualTo(String value) {
            addCriterion("utm_source <>", value, "utmSource");
            return (Criteria) this;
        }

        public Criteria andUtmSourceGreaterThan(String value) {
            addCriterion("utm_source >", value, "utmSource");
            return (Criteria) this;
        }

        public Criteria andUtmSourceGreaterThanOrEqualTo(String value) {
            addCriterion("utm_source >=", value, "utmSource");
            return (Criteria) this;
        }

        public Criteria andUtmSourceLessThan(String value) {
            addCriterion("utm_source <", value, "utmSource");
            return (Criteria) this;
        }

        public Criteria andUtmSourceLessThanOrEqualTo(String value) {
            addCriterion("utm_source <=", value, "utmSource");
            return (Criteria) this;
        }

        public Criteria andUtmSourceLike(String value) {
            addCriterion("utm_source like", value, "utmSource");
            return (Criteria) this;
        }

        public Criteria andUtmSourceNotLike(String value) {
            addCriterion("utm_source not like", value, "utmSource");
            return (Criteria) this;
        }

        public Criteria andUtmSourceIn(List<String> values) {
            addCriterion("utm_source in", values, "utmSource");
            return (Criteria) this;
        }

        public Criteria andUtmSourceNotIn(List<String> values) {
            addCriterion("utm_source not in", values, "utmSource");
            return (Criteria) this;
        }

        public Criteria andUtmSourceBetween(String value1, String value2) {
            addCriterion("utm_source between", value1, value2, "utmSource");
            return (Criteria) this;
        }

        public Criteria andUtmSourceNotBetween(String value1, String value2) {
            addCriterion("utm_source not between", value1, value2, "utmSource");
            return (Criteria) this;
        }

        public Criteria andSourceIdIsNull() {
            addCriterion("source_id is null");
            return (Criteria) this;
        }

        public Criteria andSourceIdIsNotNull() {
            addCriterion("source_id is not null");
            return (Criteria) this;
        }

        public Criteria andSourceIdEqualTo(Integer value) {
            addCriterion("source_id =", value, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdNotEqualTo(Integer value) {
            addCriterion("source_id <>", value, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdGreaterThan(Integer value) {
            addCriterion("source_id >", value, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("source_id >=", value, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdLessThan(Integer value) {
            addCriterion("source_id <", value, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdLessThanOrEqualTo(Integer value) {
            addCriterion("source_id <=", value, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdIn(List<Integer> values) {
            addCriterion("source_id in", values, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdNotIn(List<Integer> values) {
            addCriterion("source_id not in", values, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdBetween(Integer value1, Integer value2) {
            addCriterion("source_id between", value1, value2, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdNotBetween(Integer value1, Integer value2) {
            addCriterion("source_id not between", value1, value2, "sourceId");
            return (Criteria) this;
        }

        public Criteria andUtmMediumIsNull() {
            addCriterion("utm_medium is null");
            return (Criteria) this;
        }

        public Criteria andUtmMediumIsNotNull() {
            addCriterion("utm_medium is not null");
            return (Criteria) this;
        }

        public Criteria andUtmMediumEqualTo(String value) {
            addCriterion("utm_medium =", value, "utmMedium");
            return (Criteria) this;
        }

        public Criteria andUtmMediumNotEqualTo(String value) {
            addCriterion("utm_medium <>", value, "utmMedium");
            return (Criteria) this;
        }

        public Criteria andUtmMediumGreaterThan(String value) {
            addCriterion("utm_medium >", value, "utmMedium");
            return (Criteria) this;
        }

        public Criteria andUtmMediumGreaterThanOrEqualTo(String value) {
            addCriterion("utm_medium >=", value, "utmMedium");
            return (Criteria) this;
        }

        public Criteria andUtmMediumLessThan(String value) {
            addCriterion("utm_medium <", value, "utmMedium");
            return (Criteria) this;
        }

        public Criteria andUtmMediumLessThanOrEqualTo(String value) {
            addCriterion("utm_medium <=", value, "utmMedium");
            return (Criteria) this;
        }

        public Criteria andUtmMediumLike(String value) {
            addCriterion("utm_medium like", value, "utmMedium");
            return (Criteria) this;
        }

        public Criteria andUtmMediumNotLike(String value) {
            addCriterion("utm_medium not like", value, "utmMedium");
            return (Criteria) this;
        }

        public Criteria andUtmMediumIn(List<String> values) {
            addCriterion("utm_medium in", values, "utmMedium");
            return (Criteria) this;
        }

        public Criteria andUtmMediumNotIn(List<String> values) {
            addCriterion("utm_medium not in", values, "utmMedium");
            return (Criteria) this;
        }

        public Criteria andUtmMediumBetween(String value1, String value2) {
            addCriterion("utm_medium between", value1, value2, "utmMedium");
            return (Criteria) this;
        }

        public Criteria andUtmMediumNotBetween(String value1, String value2) {
            addCriterion("utm_medium not between", value1, value2, "utmMedium");
            return (Criteria) this;
        }

        public Criteria andUtmTermIsNull() {
            addCriterion("utm_term is null");
            return (Criteria) this;
        }

        public Criteria andUtmTermIsNotNull() {
            addCriterion("utm_term is not null");
            return (Criteria) this;
        }

        public Criteria andUtmTermEqualTo(String value) {
            addCriterion("utm_term =", value, "utmTerm");
            return (Criteria) this;
        }

        public Criteria andUtmTermNotEqualTo(String value) {
            addCriterion("utm_term <>", value, "utmTerm");
            return (Criteria) this;
        }

        public Criteria andUtmTermGreaterThan(String value) {
            addCriterion("utm_term >", value, "utmTerm");
            return (Criteria) this;
        }

        public Criteria andUtmTermGreaterThanOrEqualTo(String value) {
            addCriterion("utm_term >=", value, "utmTerm");
            return (Criteria) this;
        }

        public Criteria andUtmTermLessThan(String value) {
            addCriterion("utm_term <", value, "utmTerm");
            return (Criteria) this;
        }

        public Criteria andUtmTermLessThanOrEqualTo(String value) {
            addCriterion("utm_term <=", value, "utmTerm");
            return (Criteria) this;
        }

        public Criteria andUtmTermLike(String value) {
            addCriterion("utm_term like", value, "utmTerm");
            return (Criteria) this;
        }

        public Criteria andUtmTermNotLike(String value) {
            addCriterion("utm_term not like", value, "utmTerm");
            return (Criteria) this;
        }

        public Criteria andUtmTermIn(List<String> values) {
            addCriterion("utm_term in", values, "utmTerm");
            return (Criteria) this;
        }

        public Criteria andUtmTermNotIn(List<String> values) {
            addCriterion("utm_term not in", values, "utmTerm");
            return (Criteria) this;
        }

        public Criteria andUtmTermBetween(String value1, String value2) {
            addCriterion("utm_term between", value1, value2, "utmTerm");
            return (Criteria) this;
        }

        public Criteria andUtmTermNotBetween(String value1, String value2) {
            addCriterion("utm_term not between", value1, value2, "utmTerm");
            return (Criteria) this;
        }

        public Criteria andUtmContentIsNull() {
            addCriterion("utm_content is null");
            return (Criteria) this;
        }

        public Criteria andUtmContentIsNotNull() {
            addCriterion("utm_content is not null");
            return (Criteria) this;
        }

        public Criteria andUtmContentEqualTo(String value) {
            addCriterion("utm_content =", value, "utmContent");
            return (Criteria) this;
        }

        public Criteria andUtmContentNotEqualTo(String value) {
            addCriterion("utm_content <>", value, "utmContent");
            return (Criteria) this;
        }

        public Criteria andUtmContentGreaterThan(String value) {
            addCriterion("utm_content >", value, "utmContent");
            return (Criteria) this;
        }

        public Criteria andUtmContentGreaterThanOrEqualTo(String value) {
            addCriterion("utm_content >=", value, "utmContent");
            return (Criteria) this;
        }

        public Criteria andUtmContentLessThan(String value) {
            addCriterion("utm_content <", value, "utmContent");
            return (Criteria) this;
        }

        public Criteria andUtmContentLessThanOrEqualTo(String value) {
            addCriterion("utm_content <=", value, "utmContent");
            return (Criteria) this;
        }

        public Criteria andUtmContentLike(String value) {
            addCriterion("utm_content like", value, "utmContent");
            return (Criteria) this;
        }

        public Criteria andUtmContentNotLike(String value) {
            addCriterion("utm_content not like", value, "utmContent");
            return (Criteria) this;
        }

        public Criteria andUtmContentIn(List<String> values) {
            addCriterion("utm_content in", values, "utmContent");
            return (Criteria) this;
        }

        public Criteria andUtmContentNotIn(List<String> values) {
            addCriterion("utm_content not in", values, "utmContent");
            return (Criteria) this;
        }

        public Criteria andUtmContentBetween(String value1, String value2) {
            addCriterion("utm_content between", value1, value2, "utmContent");
            return (Criteria) this;
        }

        public Criteria andUtmContentNotBetween(String value1, String value2) {
            addCriterion("utm_content not between", value1, value2, "utmContent");
            return (Criteria) this;
        }

        public Criteria andUtmCampaignIsNull() {
            addCriterion("utm_campaign is null");
            return (Criteria) this;
        }

        public Criteria andUtmCampaignIsNotNull() {
            addCriterion("utm_campaign is not null");
            return (Criteria) this;
        }

        public Criteria andUtmCampaignEqualTo(String value) {
            addCriterion("utm_campaign =", value, "utmCampaign");
            return (Criteria) this;
        }

        public Criteria andUtmCampaignNotEqualTo(String value) {
            addCriterion("utm_campaign <>", value, "utmCampaign");
            return (Criteria) this;
        }

        public Criteria andUtmCampaignGreaterThan(String value) {
            addCriterion("utm_campaign >", value, "utmCampaign");
            return (Criteria) this;
        }

        public Criteria andUtmCampaignGreaterThanOrEqualTo(String value) {
            addCriterion("utm_campaign >=", value, "utmCampaign");
            return (Criteria) this;
        }

        public Criteria andUtmCampaignLessThan(String value) {
            addCriterion("utm_campaign <", value, "utmCampaign");
            return (Criteria) this;
        }

        public Criteria andUtmCampaignLessThanOrEqualTo(String value) {
            addCriterion("utm_campaign <=", value, "utmCampaign");
            return (Criteria) this;
        }

        public Criteria andUtmCampaignLike(String value) {
            addCriterion("utm_campaign like", value, "utmCampaign");
            return (Criteria) this;
        }

        public Criteria andUtmCampaignNotLike(String value) {
            addCriterion("utm_campaign not like", value, "utmCampaign");
            return (Criteria) this;
        }

        public Criteria andUtmCampaignIn(List<String> values) {
            addCriterion("utm_campaign in", values, "utmCampaign");
            return (Criteria) this;
        }

        public Criteria andUtmCampaignNotIn(List<String> values) {
            addCriterion("utm_campaign not in", values, "utmCampaign");
            return (Criteria) this;
        }

        public Criteria andUtmCampaignBetween(String value1, String value2) {
            addCriterion("utm_campaign between", value1, value2, "utmCampaign");
            return (Criteria) this;
        }

        public Criteria andUtmCampaignNotBetween(String value1, String value2) {
            addCriterion("utm_campaign not between", value1, value2, "utmCampaign");
            return (Criteria) this;
        }

        public Criteria andUtmReferrerIsNull() {
            addCriterion("utm_referrer is null");
            return (Criteria) this;
        }

        public Criteria andUtmReferrerIsNotNull() {
            addCriterion("utm_referrer is not null");
            return (Criteria) this;
        }

        public Criteria andUtmReferrerEqualTo(Integer value) {
            addCriterion("utm_referrer =", value, "utmReferrer");
            return (Criteria) this;
        }

        public Criteria andUtmReferrerNotEqualTo(Integer value) {
            addCriterion("utm_referrer <>", value, "utmReferrer");
            return (Criteria) this;
        }

        public Criteria andUtmReferrerGreaterThan(Integer value) {
            addCriterion("utm_referrer >", value, "utmReferrer");
            return (Criteria) this;
        }

        public Criteria andUtmReferrerGreaterThanOrEqualTo(Integer value) {
            addCriterion("utm_referrer >=", value, "utmReferrer");
            return (Criteria) this;
        }

        public Criteria andUtmReferrerLessThan(Integer value) {
            addCriterion("utm_referrer <", value, "utmReferrer");
            return (Criteria) this;
        }

        public Criteria andUtmReferrerLessThanOrEqualTo(Integer value) {
            addCriterion("utm_referrer <=", value, "utmReferrer");
            return (Criteria) this;
        }

        public Criteria andUtmReferrerIn(List<Integer> values) {
            addCriterion("utm_referrer in", values, "utmReferrer");
            return (Criteria) this;
        }

        public Criteria andUtmReferrerNotIn(List<Integer> values) {
            addCriterion("utm_referrer not in", values, "utmReferrer");
            return (Criteria) this;
        }

        public Criteria andUtmReferrerBetween(Integer value1, Integer value2) {
            addCriterion("utm_referrer between", value1, value2, "utmReferrer");
            return (Criteria) this;
        }

        public Criteria andUtmReferrerNotBetween(Integer value1, Integer value2) {
            addCriterion("utm_referrer not between", value1, value2, "utmReferrer");
            return (Criteria) this;
        }

        public Criteria andLinkAddressIsNull() {
            addCriterion("link_address is null");
            return (Criteria) this;
        }

        public Criteria andLinkAddressIsNotNull() {
            addCriterion("link_address is not null");
            return (Criteria) this;
        }

        public Criteria andLinkAddressEqualTo(String value) {
            addCriterion("link_address =", value, "linkAddress");
            return (Criteria) this;
        }

        public Criteria andLinkAddressNotEqualTo(String value) {
            addCriterion("link_address <>", value, "linkAddress");
            return (Criteria) this;
        }

        public Criteria andLinkAddressGreaterThan(String value) {
            addCriterion("link_address >", value, "linkAddress");
            return (Criteria) this;
        }

        public Criteria andLinkAddressGreaterThanOrEqualTo(String value) {
            addCriterion("link_address >=", value, "linkAddress");
            return (Criteria) this;
        }

        public Criteria andLinkAddressLessThan(String value) {
            addCriterion("link_address <", value, "linkAddress");
            return (Criteria) this;
        }

        public Criteria andLinkAddressLessThanOrEqualTo(String value) {
            addCriterion("link_address <=", value, "linkAddress");
            return (Criteria) this;
        }

        public Criteria andLinkAddressLike(String value) {
            addCriterion("link_address like", value, "linkAddress");
            return (Criteria) this;
        }

        public Criteria andLinkAddressNotLike(String value) {
            addCriterion("link_address not like", value, "linkAddress");
            return (Criteria) this;
        }

        public Criteria andLinkAddressIn(List<String> values) {
            addCriterion("link_address in", values, "linkAddress");
            return (Criteria) this;
        }

        public Criteria andLinkAddressNotIn(List<String> values) {
            addCriterion("link_address not in", values, "linkAddress");
            return (Criteria) this;
        }

        public Criteria andLinkAddressBetween(String value1, String value2) {
            addCriterion("link_address between", value1, value2, "linkAddress");
            return (Criteria) this;
        }

        public Criteria andLinkAddressNotBetween(String value1, String value2) {
            addCriterion("link_address not between", value1, value2, "linkAddress");
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