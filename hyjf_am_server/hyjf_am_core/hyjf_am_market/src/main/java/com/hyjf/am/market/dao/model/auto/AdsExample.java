package com.hyjf.am.market.dao.model.auto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdsExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public AdsExample() {
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

        public Criteria andNameIsNull() {
            addCriterion("`name` is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("`name` is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("`name` =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("`name` <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("`name` >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("`name` >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("`name` <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("`name` <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("`name` like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("`name` not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("`name` in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("`name` not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("`name` between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("`name` not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andTypeIdIsNull() {
            addCriterion("type_id is null");
            return (Criteria) this;
        }

        public Criteria andTypeIdIsNotNull() {
            addCriterion("type_id is not null");
            return (Criteria) this;
        }

        public Criteria andTypeIdEqualTo(Integer value) {
            addCriterion("type_id =", value, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdNotEqualTo(Integer value) {
            addCriterion("type_id <>", value, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdGreaterThan(Integer value) {
            addCriterion("type_id >", value, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("type_id >=", value, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdLessThan(Integer value) {
            addCriterion("type_id <", value, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdLessThanOrEqualTo(Integer value) {
            addCriterion("type_id <=", value, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdIn(List<Integer> values) {
            addCriterion("type_id in", values, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdNotIn(List<Integer> values) {
            addCriterion("type_id not in", values, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdBetween(Integer value1, Integer value2) {
            addCriterion("type_id between", value1, value2, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdNotBetween(Integer value1, Integer value2) {
            addCriterion("type_id not between", value1, value2, "typeId");
            return (Criteria) this;
        }

        public Criteria andUrlIsNull() {
            addCriterion("url is null");
            return (Criteria) this;
        }

        public Criteria andUrlIsNotNull() {
            addCriterion("url is not null");
            return (Criteria) this;
        }

        public Criteria andUrlEqualTo(String value) {
            addCriterion("url =", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotEqualTo(String value) {
            addCriterion("url <>", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlGreaterThan(String value) {
            addCriterion("url >", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlGreaterThanOrEqualTo(String value) {
            addCriterion("url >=", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlLessThan(String value) {
            addCriterion("url <", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlLessThanOrEqualTo(String value) {
            addCriterion("url <=", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlLike(String value) {
            addCriterion("url like", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotLike(String value) {
            addCriterion("url not like", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlIn(List<String> values) {
            addCriterion("url in", values, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotIn(List<String> values) {
            addCriterion("url not in", values, "url");
            return (Criteria) this;
        }

        public Criteria andUrlBetween(String value1, String value2) {
            addCriterion("url between", value1, value2, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotBetween(String value1, String value2) {
            addCriterion("url not between", value1, value2, "url");
            return (Criteria) this;
        }

        public Criteria andCodeIsNull() {
            addCriterion("code is null");
            return (Criteria) this;
        }

        public Criteria andCodeIsNotNull() {
            addCriterion("code is not null");
            return (Criteria) this;
        }

        public Criteria andCodeEqualTo(String value) {
            addCriterion("code =", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotEqualTo(String value) {
            addCriterion("code <>", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeGreaterThan(String value) {
            addCriterion("code >", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeGreaterThanOrEqualTo(String value) {
            addCriterion("code >=", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLessThan(String value) {
            addCriterion("code <", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLessThanOrEqualTo(String value) {
            addCriterion("code <=", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLike(String value) {
            addCriterion("code like", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotLike(String value) {
            addCriterion("code not like", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeIn(List<String> values) {
            addCriterion("code in", values, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotIn(List<String> values) {
            addCriterion("code not in", values, "code");
            return (Criteria) this;
        }

        public Criteria andCodeBetween(String value1, String value2) {
            addCriterion("code between", value1, value2, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotBetween(String value1, String value2) {
            addCriterion("code not between", value1, value2, "code");
            return (Criteria) this;
        }

        public Criteria andContentIsNull() {
            addCriterion("content is null");
            return (Criteria) this;
        }

        public Criteria andContentIsNotNull() {
            addCriterion("content is not null");
            return (Criteria) this;
        }

        public Criteria andContentEqualTo(String value) {
            addCriterion("content =", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotEqualTo(String value) {
            addCriterion("content <>", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentGreaterThan(String value) {
            addCriterion("content >", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentGreaterThanOrEqualTo(String value) {
            addCriterion("content >=", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLessThan(String value) {
            addCriterion("content <", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLessThanOrEqualTo(String value) {
            addCriterion("content <=", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLike(String value) {
            addCriterion("content like", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotLike(String value) {
            addCriterion("content not like", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentIn(List<String> values) {
            addCriterion("content in", values, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotIn(List<String> values) {
            addCriterion("content not in", values, "content");
            return (Criteria) this;
        }

        public Criteria andContentBetween(String value1, String value2) {
            addCriterion("content between", value1, value2, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotBetween(String value1, String value2) {
            addCriterion("content not between", value1, value2, "content");
            return (Criteria) this;
        }

        public Criteria andImageIsNull() {
            addCriterion("image is null");
            return (Criteria) this;
        }

        public Criteria andImageIsNotNull() {
            addCriterion("image is not null");
            return (Criteria) this;
        }

        public Criteria andImageEqualTo(String value) {
            addCriterion("image =", value, "image");
            return (Criteria) this;
        }

        public Criteria andImageNotEqualTo(String value) {
            addCriterion("image <>", value, "image");
            return (Criteria) this;
        }

        public Criteria andImageGreaterThan(String value) {
            addCriterion("image >", value, "image");
            return (Criteria) this;
        }

        public Criteria andImageGreaterThanOrEqualTo(String value) {
            addCriterion("image >=", value, "image");
            return (Criteria) this;
        }

        public Criteria andImageLessThan(String value) {
            addCriterion("image <", value, "image");
            return (Criteria) this;
        }

        public Criteria andImageLessThanOrEqualTo(String value) {
            addCriterion("image <=", value, "image");
            return (Criteria) this;
        }

        public Criteria andImageLike(String value) {
            addCriterion("image like", value, "image");
            return (Criteria) this;
        }

        public Criteria andImageNotLike(String value) {
            addCriterion("image not like", value, "image");
            return (Criteria) this;
        }

        public Criteria andImageIn(List<String> values) {
            addCriterion("image in", values, "image");
            return (Criteria) this;
        }

        public Criteria andImageNotIn(List<String> values) {
            addCriterion("image not in", values, "image");
            return (Criteria) this;
        }

        public Criteria andImageBetween(String value1, String value2) {
            addCriterion("image between", value1, value2, "image");
            return (Criteria) this;
        }

        public Criteria andImageNotBetween(String value1, String value2) {
            addCriterion("image not between", value1, value2, "image");
            return (Criteria) this;
        }

        public Criteria andOrderIsNull() {
            addCriterion("`order` is null");
            return (Criteria) this;
        }

        public Criteria andOrderIsNotNull() {
            addCriterion("`order` is not null");
            return (Criteria) this;
        }

        public Criteria andOrderEqualTo(Integer value) {
            addCriterion("`order` =", value, "order");
            return (Criteria) this;
        }

        public Criteria andOrderNotEqualTo(Integer value) {
            addCriterion("`order` <>", value, "order");
            return (Criteria) this;
        }

        public Criteria andOrderGreaterThan(Integer value) {
            addCriterion("`order` >", value, "order");
            return (Criteria) this;
        }

        public Criteria andOrderGreaterThanOrEqualTo(Integer value) {
            addCriterion("`order` >=", value, "order");
            return (Criteria) this;
        }

        public Criteria andOrderLessThan(Integer value) {
            addCriterion("`order` <", value, "order");
            return (Criteria) this;
        }

        public Criteria andOrderLessThanOrEqualTo(Integer value) {
            addCriterion("`order` <=", value, "order");
            return (Criteria) this;
        }

        public Criteria andOrderIn(List<Integer> values) {
            addCriterion("`order` in", values, "order");
            return (Criteria) this;
        }

        public Criteria andOrderNotIn(List<Integer> values) {
            addCriterion("`order` not in", values, "order");
            return (Criteria) this;
        }

        public Criteria andOrderBetween(Integer value1, Integer value2) {
            addCriterion("`order` between", value1, value2, "order");
            return (Criteria) this;
        }

        public Criteria andOrderNotBetween(Integer value1, Integer value2) {
            addCriterion("`order` not between", value1, value2, "order");
            return (Criteria) this;
        }

        public Criteria andHitsIsNull() {
            addCriterion("hits is null");
            return (Criteria) this;
        }

        public Criteria andHitsIsNotNull() {
            addCriterion("hits is not null");
            return (Criteria) this;
        }

        public Criteria andHitsEqualTo(Integer value) {
            addCriterion("hits =", value, "hits");
            return (Criteria) this;
        }

        public Criteria andHitsNotEqualTo(Integer value) {
            addCriterion("hits <>", value, "hits");
            return (Criteria) this;
        }

        public Criteria andHitsGreaterThan(Integer value) {
            addCriterion("hits >", value, "hits");
            return (Criteria) this;
        }

        public Criteria andHitsGreaterThanOrEqualTo(Integer value) {
            addCriterion("hits >=", value, "hits");
            return (Criteria) this;
        }

        public Criteria andHitsLessThan(Integer value) {
            addCriterion("hits <", value, "hits");
            return (Criteria) this;
        }

        public Criteria andHitsLessThanOrEqualTo(Integer value) {
            addCriterion("hits <=", value, "hits");
            return (Criteria) this;
        }

        public Criteria andHitsIn(List<Integer> values) {
            addCriterion("hits in", values, "hits");
            return (Criteria) this;
        }

        public Criteria andHitsNotIn(List<Integer> values) {
            addCriterion("hits not in", values, "hits");
            return (Criteria) this;
        }

        public Criteria andHitsBetween(Integer value1, Integer value2) {
            addCriterion("hits between", value1, value2, "hits");
            return (Criteria) this;
        }

        public Criteria andHitsNotBetween(Integer value1, Integer value2) {
            addCriterion("hits not between", value1, value2, "hits");
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

        public Criteria andShareUrlIsNull() {
            addCriterion("share_url is null");
            return (Criteria) this;
        }

        public Criteria andShareUrlIsNotNull() {
            addCriterion("share_url is not null");
            return (Criteria) this;
        }

        public Criteria andShareUrlEqualTo(String value) {
            addCriterion("share_url =", value, "shareUrl");
            return (Criteria) this;
        }

        public Criteria andShareUrlNotEqualTo(String value) {
            addCriterion("share_url <>", value, "shareUrl");
            return (Criteria) this;
        }

        public Criteria andShareUrlGreaterThan(String value) {
            addCriterion("share_url >", value, "shareUrl");
            return (Criteria) this;
        }

        public Criteria andShareUrlGreaterThanOrEqualTo(String value) {
            addCriterion("share_url >=", value, "shareUrl");
            return (Criteria) this;
        }

        public Criteria andShareUrlLessThan(String value) {
            addCriterion("share_url <", value, "shareUrl");
            return (Criteria) this;
        }

        public Criteria andShareUrlLessThanOrEqualTo(String value) {
            addCriterion("share_url <=", value, "shareUrl");
            return (Criteria) this;
        }

        public Criteria andShareUrlLike(String value) {
            addCriterion("share_url like", value, "shareUrl");
            return (Criteria) this;
        }

        public Criteria andShareUrlNotLike(String value) {
            addCriterion("share_url not like", value, "shareUrl");
            return (Criteria) this;
        }

        public Criteria andShareUrlIn(List<String> values) {
            addCriterion("share_url in", values, "shareUrl");
            return (Criteria) this;
        }

        public Criteria andShareUrlNotIn(List<String> values) {
            addCriterion("share_url not in", values, "shareUrl");
            return (Criteria) this;
        }

        public Criteria andShareUrlBetween(String value1, String value2) {
            addCriterion("share_url between", value1, value2, "shareUrl");
            return (Criteria) this;
        }

        public Criteria andShareUrlNotBetween(String value1, String value2) {
            addCriterion("share_url not between", value1, value2, "shareUrl");
            return (Criteria) this;
        }

        public Criteria andShareImageIsNull() {
            addCriterion("share_image is null");
            return (Criteria) this;
        }

        public Criteria andShareImageIsNotNull() {
            addCriterion("share_image is not null");
            return (Criteria) this;
        }

        public Criteria andShareImageEqualTo(String value) {
            addCriterion("share_image =", value, "shareImage");
            return (Criteria) this;
        }

        public Criteria andShareImageNotEqualTo(String value) {
            addCriterion("share_image <>", value, "shareImage");
            return (Criteria) this;
        }

        public Criteria andShareImageGreaterThan(String value) {
            addCriterion("share_image >", value, "shareImage");
            return (Criteria) this;
        }

        public Criteria andShareImageGreaterThanOrEqualTo(String value) {
            addCriterion("share_image >=", value, "shareImage");
            return (Criteria) this;
        }

        public Criteria andShareImageLessThan(String value) {
            addCriterion("share_image <", value, "shareImage");
            return (Criteria) this;
        }

        public Criteria andShareImageLessThanOrEqualTo(String value) {
            addCriterion("share_image <=", value, "shareImage");
            return (Criteria) this;
        }

        public Criteria andShareImageLike(String value) {
            addCriterion("share_image like", value, "shareImage");
            return (Criteria) this;
        }

        public Criteria andShareImageNotLike(String value) {
            addCriterion("share_image not like", value, "shareImage");
            return (Criteria) this;
        }

        public Criteria andShareImageIn(List<String> values) {
            addCriterion("share_image in", values, "shareImage");
            return (Criteria) this;
        }

        public Criteria andShareImageNotIn(List<String> values) {
            addCriterion("share_image not in", values, "shareImage");
            return (Criteria) this;
        }

        public Criteria andShareImageBetween(String value1, String value2) {
            addCriterion("share_image between", value1, value2, "shareImage");
            return (Criteria) this;
        }

        public Criteria andShareImageNotBetween(String value1, String value2) {
            addCriterion("share_image not between", value1, value2, "shareImage");
            return (Criteria) this;
        }

        public Criteria andIsIndexIsNull() {
            addCriterion("is_index is null");
            return (Criteria) this;
        }

        public Criteria andIsIndexIsNotNull() {
            addCriterion("is_index is not null");
            return (Criteria) this;
        }

        public Criteria andIsIndexEqualTo(Integer value) {
            addCriterion("is_index =", value, "isIndex");
            return (Criteria) this;
        }

        public Criteria andIsIndexNotEqualTo(Integer value) {
            addCriterion("is_index <>", value, "isIndex");
            return (Criteria) this;
        }

        public Criteria andIsIndexGreaterThan(Integer value) {
            addCriterion("is_index >", value, "isIndex");
            return (Criteria) this;
        }

        public Criteria andIsIndexGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_index >=", value, "isIndex");
            return (Criteria) this;
        }

        public Criteria andIsIndexLessThan(Integer value) {
            addCriterion("is_index <", value, "isIndex");
            return (Criteria) this;
        }

        public Criteria andIsIndexLessThanOrEqualTo(Integer value) {
            addCriterion("is_index <=", value, "isIndex");
            return (Criteria) this;
        }

        public Criteria andIsIndexIn(List<Integer> values) {
            addCriterion("is_index in", values, "isIndex");
            return (Criteria) this;
        }

        public Criteria andIsIndexNotIn(List<Integer> values) {
            addCriterion("is_index not in", values, "isIndex");
            return (Criteria) this;
        }

        public Criteria andIsIndexBetween(Integer value1, Integer value2) {
            addCriterion("is_index between", value1, value2, "isIndex");
            return (Criteria) this;
        }

        public Criteria andIsIndexNotBetween(Integer value1, Integer value2) {
            addCriterion("is_index not between", value1, value2, "isIndex");
            return (Criteria) this;
        }

        public Criteria andStartTimeIsNull() {
            addCriterion("start_time is null");
            return (Criteria) this;
        }

        public Criteria andStartTimeIsNotNull() {
            addCriterion("start_time is not null");
            return (Criteria) this;
        }

        public Criteria andStartTimeEqualTo(String value) {
            addCriterion("start_time =", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotEqualTo(String value) {
            addCriterion("start_time <>", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThan(String value) {
            addCriterion("start_time >", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThanOrEqualTo(String value) {
            addCriterion("start_time >=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThan(String value) {
            addCriterion("start_time <", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThanOrEqualTo(String value) {
            addCriterion("start_time <=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLike(String value) {
            addCriterion("start_time like", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotLike(String value) {
            addCriterion("start_time not like", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeIn(List<String> values) {
            addCriterion("start_time in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotIn(List<String> values) {
            addCriterion("start_time not in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeBetween(String value1, String value2) {
            addCriterion("start_time between", value1, value2, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotBetween(String value1, String value2) {
            addCriterion("start_time not between", value1, value2, "startTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNull() {
            addCriterion("end_time is null");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNotNull() {
            addCriterion("end_time is not null");
            return (Criteria) this;
        }

        public Criteria andEndTimeEqualTo(String value) {
            addCriterion("end_time =", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotEqualTo(String value) {
            addCriterion("end_time <>", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThan(String value) {
            addCriterion("end_time >", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThanOrEqualTo(String value) {
            addCriterion("end_time >=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThan(String value) {
            addCriterion("end_time <", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThanOrEqualTo(String value) {
            addCriterion("end_time <=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLike(String value) {
            addCriterion("end_time like", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotLike(String value) {
            addCriterion("end_time not like", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIn(List<String> values) {
            addCriterion("end_time in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotIn(List<String> values) {
            addCriterion("end_time not in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeBetween(String value1, String value2) {
            addCriterion("end_time between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotBetween(String value1, String value2) {
            addCriterion("end_time not between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andIsEndIsNull() {
            addCriterion("is_end is null");
            return (Criteria) this;
        }

        public Criteria andIsEndIsNotNull() {
            addCriterion("is_end is not null");
            return (Criteria) this;
        }

        public Criteria andIsEndEqualTo(Integer value) {
            addCriterion("is_end =", value, "isEnd");
            return (Criteria) this;
        }

        public Criteria andIsEndNotEqualTo(Integer value) {
            addCriterion("is_end <>", value, "isEnd");
            return (Criteria) this;
        }

        public Criteria andIsEndGreaterThan(Integer value) {
            addCriterion("is_end >", value, "isEnd");
            return (Criteria) this;
        }

        public Criteria andIsEndGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_end >=", value, "isEnd");
            return (Criteria) this;
        }

        public Criteria andIsEndLessThan(Integer value) {
            addCriterion("is_end <", value, "isEnd");
            return (Criteria) this;
        }

        public Criteria andIsEndLessThanOrEqualTo(Integer value) {
            addCriterion("is_end <=", value, "isEnd");
            return (Criteria) this;
        }

        public Criteria andIsEndIn(List<Integer> values) {
            addCriterion("is_end in", values, "isEnd");
            return (Criteria) this;
        }

        public Criteria andIsEndNotIn(List<Integer> values) {
            addCriterion("is_end not in", values, "isEnd");
            return (Criteria) this;
        }

        public Criteria andIsEndBetween(Integer value1, Integer value2) {
            addCriterion("is_end between", value1, value2, "isEnd");
            return (Criteria) this;
        }

        public Criteria andIsEndNotBetween(Integer value1, Integer value2) {
            addCriterion("is_end not between", value1, value2, "isEnd");
            return (Criteria) this;
        }

        public Criteria andShareTitleIsNull() {
            addCriterion("share_title is null");
            return (Criteria) this;
        }

        public Criteria andShareTitleIsNotNull() {
            addCriterion("share_title is not null");
            return (Criteria) this;
        }

        public Criteria andShareTitleEqualTo(String value) {
            addCriterion("share_title =", value, "shareTitle");
            return (Criteria) this;
        }

        public Criteria andShareTitleNotEqualTo(String value) {
            addCriterion("share_title <>", value, "shareTitle");
            return (Criteria) this;
        }

        public Criteria andShareTitleGreaterThan(String value) {
            addCriterion("share_title >", value, "shareTitle");
            return (Criteria) this;
        }

        public Criteria andShareTitleGreaterThanOrEqualTo(String value) {
            addCriterion("share_title >=", value, "shareTitle");
            return (Criteria) this;
        }

        public Criteria andShareTitleLessThan(String value) {
            addCriterion("share_title <", value, "shareTitle");
            return (Criteria) this;
        }

        public Criteria andShareTitleLessThanOrEqualTo(String value) {
            addCriterion("share_title <=", value, "shareTitle");
            return (Criteria) this;
        }

        public Criteria andShareTitleLike(String value) {
            addCriterion("share_title like", value, "shareTitle");
            return (Criteria) this;
        }

        public Criteria andShareTitleNotLike(String value) {
            addCriterion("share_title not like", value, "shareTitle");
            return (Criteria) this;
        }

        public Criteria andShareTitleIn(List<String> values) {
            addCriterion("share_title in", values, "shareTitle");
            return (Criteria) this;
        }

        public Criteria andShareTitleNotIn(List<String> values) {
            addCriterion("share_title not in", values, "shareTitle");
            return (Criteria) this;
        }

        public Criteria andShareTitleBetween(String value1, String value2) {
            addCriterion("share_title between", value1, value2, "shareTitle");
            return (Criteria) this;
        }

        public Criteria andShareTitleNotBetween(String value1, String value2) {
            addCriterion("share_title not between", value1, value2, "shareTitle");
            return (Criteria) this;
        }

        public Criteria andActivitiImageIsNull() {
            addCriterion("activiti_image is null");
            return (Criteria) this;
        }

        public Criteria andActivitiImageIsNotNull() {
            addCriterion("activiti_image is not null");
            return (Criteria) this;
        }

        public Criteria andActivitiImageEqualTo(String value) {
            addCriterion("activiti_image =", value, "activitiImage");
            return (Criteria) this;
        }

        public Criteria andActivitiImageNotEqualTo(String value) {
            addCriterion("activiti_image <>", value, "activitiImage");
            return (Criteria) this;
        }

        public Criteria andActivitiImageGreaterThan(String value) {
            addCriterion("activiti_image >", value, "activitiImage");
            return (Criteria) this;
        }

        public Criteria andActivitiImageGreaterThanOrEqualTo(String value) {
            addCriterion("activiti_image >=", value, "activitiImage");
            return (Criteria) this;
        }

        public Criteria andActivitiImageLessThan(String value) {
            addCriterion("activiti_image <", value, "activitiImage");
            return (Criteria) this;
        }

        public Criteria andActivitiImageLessThanOrEqualTo(String value) {
            addCriterion("activiti_image <=", value, "activitiImage");
            return (Criteria) this;
        }

        public Criteria andActivitiImageLike(String value) {
            addCriterion("activiti_image like", value, "activitiImage");
            return (Criteria) this;
        }

        public Criteria andActivitiImageNotLike(String value) {
            addCriterion("activiti_image not like", value, "activitiImage");
            return (Criteria) this;
        }

        public Criteria andActivitiImageIn(List<String> values) {
            addCriterion("activiti_image in", values, "activitiImage");
            return (Criteria) this;
        }

        public Criteria andActivitiImageNotIn(List<String> values) {
            addCriterion("activiti_image not in", values, "activitiImage");
            return (Criteria) this;
        }

        public Criteria andActivitiImageBetween(String value1, String value2) {
            addCriterion("activiti_image between", value1, value2, "activitiImage");
            return (Criteria) this;
        }

        public Criteria andActivitiImageNotBetween(String value1, String value2) {
            addCriterion("activiti_image not between", value1, value2, "activitiImage");
            return (Criteria) this;
        }

        public Criteria andActivitiDescIsNull() {
            addCriterion("activiti_desc is null");
            return (Criteria) this;
        }

        public Criteria andActivitiDescIsNotNull() {
            addCriterion("activiti_desc is not null");
            return (Criteria) this;
        }

        public Criteria andActivitiDescEqualTo(String value) {
            addCriterion("activiti_desc =", value, "activitiDesc");
            return (Criteria) this;
        }

        public Criteria andActivitiDescNotEqualTo(String value) {
            addCriterion("activiti_desc <>", value, "activitiDesc");
            return (Criteria) this;
        }

        public Criteria andActivitiDescGreaterThan(String value) {
            addCriterion("activiti_desc >", value, "activitiDesc");
            return (Criteria) this;
        }

        public Criteria andActivitiDescGreaterThanOrEqualTo(String value) {
            addCriterion("activiti_desc >=", value, "activitiDesc");
            return (Criteria) this;
        }

        public Criteria andActivitiDescLessThan(String value) {
            addCriterion("activiti_desc <", value, "activitiDesc");
            return (Criteria) this;
        }

        public Criteria andActivitiDescLessThanOrEqualTo(String value) {
            addCriterion("activiti_desc <=", value, "activitiDesc");
            return (Criteria) this;
        }

        public Criteria andActivitiDescLike(String value) {
            addCriterion("activiti_desc like", value, "activitiDesc");
            return (Criteria) this;
        }

        public Criteria andActivitiDescNotLike(String value) {
            addCriterion("activiti_desc not like", value, "activitiDesc");
            return (Criteria) this;
        }

        public Criteria andActivitiDescIn(List<String> values) {
            addCriterion("activiti_desc in", values, "activitiDesc");
            return (Criteria) this;
        }

        public Criteria andActivitiDescNotIn(List<String> values) {
            addCriterion("activiti_desc not in", values, "activitiDesc");
            return (Criteria) this;
        }

        public Criteria andActivitiDescBetween(String value1, String value2) {
            addCriterion("activiti_desc between", value1, value2, "activitiDesc");
            return (Criteria) this;
        }

        public Criteria andActivitiDescNotBetween(String value1, String value2) {
            addCriterion("activiti_desc not between", value1, value2, "activitiDesc");
            return (Criteria) this;
        }

        public Criteria andShareContentIsNull() {
            addCriterion("share_content is null");
            return (Criteria) this;
        }

        public Criteria andShareContentIsNotNull() {
            addCriterion("share_content is not null");
            return (Criteria) this;
        }

        public Criteria andShareContentEqualTo(String value) {
            addCriterion("share_content =", value, "shareContent");
            return (Criteria) this;
        }

        public Criteria andShareContentNotEqualTo(String value) {
            addCriterion("share_content <>", value, "shareContent");
            return (Criteria) this;
        }

        public Criteria andShareContentGreaterThan(String value) {
            addCriterion("share_content >", value, "shareContent");
            return (Criteria) this;
        }

        public Criteria andShareContentGreaterThanOrEqualTo(String value) {
            addCriterion("share_content >=", value, "shareContent");
            return (Criteria) this;
        }

        public Criteria andShareContentLessThan(String value) {
            addCriterion("share_content <", value, "shareContent");
            return (Criteria) this;
        }

        public Criteria andShareContentLessThanOrEqualTo(String value) {
            addCriterion("share_content <=", value, "shareContent");
            return (Criteria) this;
        }

        public Criteria andShareContentLike(String value) {
            addCriterion("share_content like", value, "shareContent");
            return (Criteria) this;
        }

        public Criteria andShareContentNotLike(String value) {
            addCriterion("share_content not like", value, "shareContent");
            return (Criteria) this;
        }

        public Criteria andShareContentIn(List<String> values) {
            addCriterion("share_content in", values, "shareContent");
            return (Criteria) this;
        }

        public Criteria andShareContentNotIn(List<String> values) {
            addCriterion("share_content not in", values, "shareContent");
            return (Criteria) this;
        }

        public Criteria andShareContentBetween(String value1, String value2) {
            addCriterion("share_content between", value1, value2, "shareContent");
            return (Criteria) this;
        }

        public Criteria andShareContentNotBetween(String value1, String value2) {
            addCriterion("share_content not between", value1, value2, "shareContent");
            return (Criteria) this;
        }

        public Criteria andClientTypeIsNull() {
            addCriterion("client_type is null");
            return (Criteria) this;
        }

        public Criteria andClientTypeIsNotNull() {
            addCriterion("client_type is not null");
            return (Criteria) this;
        }

        public Criteria andClientTypeEqualTo(Integer value) {
            addCriterion("client_type =", value, "clientType");
            return (Criteria) this;
        }

        public Criteria andClientTypeNotEqualTo(Integer value) {
            addCriterion("client_type <>", value, "clientType");
            return (Criteria) this;
        }

        public Criteria andClientTypeGreaterThan(Integer value) {
            addCriterion("client_type >", value, "clientType");
            return (Criteria) this;
        }

        public Criteria andClientTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("client_type >=", value, "clientType");
            return (Criteria) this;
        }

        public Criteria andClientTypeLessThan(Integer value) {
            addCriterion("client_type <", value, "clientType");
            return (Criteria) this;
        }

        public Criteria andClientTypeLessThanOrEqualTo(Integer value) {
            addCriterion("client_type <=", value, "clientType");
            return (Criteria) this;
        }

        public Criteria andClientTypeIn(List<Integer> values) {
            addCriterion("client_type in", values, "clientType");
            return (Criteria) this;
        }

        public Criteria andClientTypeNotIn(List<Integer> values) {
            addCriterion("client_type not in", values, "clientType");
            return (Criteria) this;
        }

        public Criteria andClientTypeBetween(Integer value1, Integer value2) {
            addCriterion("client_type between", value1, value2, "clientType");
            return (Criteria) this;
        }

        public Criteria andClientTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("client_type not between", value1, value2, "clientType");
            return (Criteria) this;
        }

        public Criteria andNewUserShowIsNull() {
            addCriterion("new_user_show is null");
            return (Criteria) this;
        }

        public Criteria andNewUserShowIsNotNull() {
            addCriterion("new_user_show is not null");
            return (Criteria) this;
        }

        public Criteria andNewUserShowEqualTo(Integer value) {
            addCriterion("new_user_show =", value, "newUserShow");
            return (Criteria) this;
        }

        public Criteria andNewUserShowNotEqualTo(Integer value) {
            addCriterion("new_user_show <>", value, "newUserShow");
            return (Criteria) this;
        }

        public Criteria andNewUserShowGreaterThan(Integer value) {
            addCriterion("new_user_show >", value, "newUserShow");
            return (Criteria) this;
        }

        public Criteria andNewUserShowGreaterThanOrEqualTo(Integer value) {
            addCriterion("new_user_show >=", value, "newUserShow");
            return (Criteria) this;
        }

        public Criteria andNewUserShowLessThan(Integer value) {
            addCriterion("new_user_show <", value, "newUserShow");
            return (Criteria) this;
        }

        public Criteria andNewUserShowLessThanOrEqualTo(Integer value) {
            addCriterion("new_user_show <=", value, "newUserShow");
            return (Criteria) this;
        }

        public Criteria andNewUserShowIn(List<Integer> values) {
            addCriterion("new_user_show in", values, "newUserShow");
            return (Criteria) this;
        }

        public Criteria andNewUserShowNotIn(List<Integer> values) {
            addCriterion("new_user_show not in", values, "newUserShow");
            return (Criteria) this;
        }

        public Criteria andNewUserShowBetween(Integer value1, Integer value2) {
            addCriterion("new_user_show between", value1, value2, "newUserShow");
            return (Criteria) this;
        }

        public Criteria andNewUserShowNotBetween(Integer value1, Integer value2) {
            addCriterion("new_user_show not between", value1, value2, "newUserShow");
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

        public Criteria andPlatformTypeIsNull() {
            addCriterion("platform_type is null");
            return (Criteria) this;
        }

        public Criteria andPlatformTypeIsNotNull() {
            addCriterion("platform_type is not null");
            return (Criteria) this;
        }

        public Criteria andPlatformTypeEqualTo(Integer value) {
            addCriterion("platform_type =", value, "platformType");
            return (Criteria) this;
        }

        public Criteria andPlatformTypeNotEqualTo(Integer value) {
            addCriterion("platform_type <>", value, "platformType");
            return (Criteria) this;
        }

        public Criteria andPlatformTypeGreaterThan(Integer value) {
            addCriterion("platform_type >", value, "platformType");
            return (Criteria) this;
        }

        public Criteria andPlatformTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("platform_type >=", value, "platformType");
            return (Criteria) this;
        }

        public Criteria andPlatformTypeLessThan(Integer value) {
            addCriterion("platform_type <", value, "platformType");
            return (Criteria) this;
        }

        public Criteria andPlatformTypeLessThanOrEqualTo(Integer value) {
            addCriterion("platform_type <=", value, "platformType");
            return (Criteria) this;
        }

        public Criteria andPlatformTypeIn(List<Integer> values) {
            addCriterion("platform_type in", values, "platformType");
            return (Criteria) this;
        }

        public Criteria andPlatformTypeNotIn(List<Integer> values) {
            addCriterion("platform_type not in", values, "platformType");
            return (Criteria) this;
        }

        public Criteria andPlatformTypeBetween(Integer value1, Integer value2) {
            addCriterion("platform_type between", value1, value2, "platformType");
            return (Criteria) this;
        }

        public Criteria andPlatformTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("platform_type not between", value1, value2, "platformType");
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