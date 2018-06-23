package com.hyjf.am.config.dao.model.auto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WhereaboutsPageConfigExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public WhereaboutsPageConfigExample() {
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

        public Criteria andTitleIsNull() {
            addCriterion("title is null");
            return (Criteria) this;
        }

        public Criteria andTitleIsNotNull() {
            addCriterion("title is not null");
            return (Criteria) this;
        }

        public Criteria andTitleEqualTo(String value) {
            addCriterion("title =", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotEqualTo(String value) {
            addCriterion("title <>", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThan(String value) {
            addCriterion("title >", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThanOrEqualTo(String value) {
            addCriterion("title >=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThan(String value) {
            addCriterion("title <", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThanOrEqualTo(String value) {
            addCriterion("title <=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLike(String value) {
            addCriterion("title like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotLike(String value) {
            addCriterion("title not like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleIn(List<String> values) {
            addCriterion("title in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotIn(List<String> values) {
            addCriterion("title not in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleBetween(String value1, String value2) {
            addCriterion("title between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotBetween(String value1, String value2) {
            addCriterion("title not between", value1, value2, "title");
            return (Criteria) this;
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

        public Criteria andReferrerIsNull() {
            addCriterion("referrer is null");
            return (Criteria) this;
        }

        public Criteria andReferrerIsNotNull() {
            addCriterion("referrer is not null");
            return (Criteria) this;
        }

        public Criteria andReferrerEqualTo(Integer value) {
            addCriterion("referrer =", value, "referrer");
            return (Criteria) this;
        }

        public Criteria andReferrerNotEqualTo(Integer value) {
            addCriterion("referrer <>", value, "referrer");
            return (Criteria) this;
        }

        public Criteria andReferrerGreaterThan(Integer value) {
            addCriterion("referrer >", value, "referrer");
            return (Criteria) this;
        }

        public Criteria andReferrerGreaterThanOrEqualTo(Integer value) {
            addCriterion("referrer >=", value, "referrer");
            return (Criteria) this;
        }

        public Criteria andReferrerLessThan(Integer value) {
            addCriterion("referrer <", value, "referrer");
            return (Criteria) this;
        }

        public Criteria andReferrerLessThanOrEqualTo(Integer value) {
            addCriterion("referrer <=", value, "referrer");
            return (Criteria) this;
        }

        public Criteria andReferrerIn(List<Integer> values) {
            addCriterion("referrer in", values, "referrer");
            return (Criteria) this;
        }

        public Criteria andReferrerNotIn(List<Integer> values) {
            addCriterion("referrer not in", values, "referrer");
            return (Criteria) this;
        }

        public Criteria andReferrerBetween(Integer value1, Integer value2) {
            addCriterion("referrer between", value1, value2, "referrer");
            return (Criteria) this;
        }

        public Criteria andReferrerNotBetween(Integer value1, Integer value2) {
            addCriterion("referrer not between", value1, value2, "referrer");
            return (Criteria) this;
        }

        public Criteria andTopButtonIsNull() {
            addCriterion("top_button is null");
            return (Criteria) this;
        }

        public Criteria andTopButtonIsNotNull() {
            addCriterion("top_button is not null");
            return (Criteria) this;
        }

        public Criteria andTopButtonEqualTo(String value) {
            addCriterion("top_button =", value, "topButton");
            return (Criteria) this;
        }

        public Criteria andTopButtonNotEqualTo(String value) {
            addCriterion("top_button <>", value, "topButton");
            return (Criteria) this;
        }

        public Criteria andTopButtonGreaterThan(String value) {
            addCriterion("top_button >", value, "topButton");
            return (Criteria) this;
        }

        public Criteria andTopButtonGreaterThanOrEqualTo(String value) {
            addCriterion("top_button >=", value, "topButton");
            return (Criteria) this;
        }

        public Criteria andTopButtonLessThan(String value) {
            addCriterion("top_button <", value, "topButton");
            return (Criteria) this;
        }

        public Criteria andTopButtonLessThanOrEqualTo(String value) {
            addCriterion("top_button <=", value, "topButton");
            return (Criteria) this;
        }

        public Criteria andTopButtonLike(String value) {
            addCriterion("top_button like", value, "topButton");
            return (Criteria) this;
        }

        public Criteria andTopButtonNotLike(String value) {
            addCriterion("top_button not like", value, "topButton");
            return (Criteria) this;
        }

        public Criteria andTopButtonIn(List<String> values) {
            addCriterion("top_button in", values, "topButton");
            return (Criteria) this;
        }

        public Criteria andTopButtonNotIn(List<String> values) {
            addCriterion("top_button not in", values, "topButton");
            return (Criteria) this;
        }

        public Criteria andTopButtonBetween(String value1, String value2) {
            addCriterion("top_button between", value1, value2, "topButton");
            return (Criteria) this;
        }

        public Criteria andTopButtonNotBetween(String value1, String value2) {
            addCriterion("top_button not between", value1, value2, "topButton");
            return (Criteria) this;
        }

        public Criteria andJumpPathIsNull() {
            addCriterion("jump_path is null");
            return (Criteria) this;
        }

        public Criteria andJumpPathIsNotNull() {
            addCriterion("jump_path is not null");
            return (Criteria) this;
        }

        public Criteria andJumpPathEqualTo(String value) {
            addCriterion("jump_path =", value, "jumpPath");
            return (Criteria) this;
        }

        public Criteria andJumpPathNotEqualTo(String value) {
            addCriterion("jump_path <>", value, "jumpPath");
            return (Criteria) this;
        }

        public Criteria andJumpPathGreaterThan(String value) {
            addCriterion("jump_path >", value, "jumpPath");
            return (Criteria) this;
        }

        public Criteria andJumpPathGreaterThanOrEqualTo(String value) {
            addCriterion("jump_path >=", value, "jumpPath");
            return (Criteria) this;
        }

        public Criteria andJumpPathLessThan(String value) {
            addCriterion("jump_path <", value, "jumpPath");
            return (Criteria) this;
        }

        public Criteria andJumpPathLessThanOrEqualTo(String value) {
            addCriterion("jump_path <=", value, "jumpPath");
            return (Criteria) this;
        }

        public Criteria andJumpPathLike(String value) {
            addCriterion("jump_path like", value, "jumpPath");
            return (Criteria) this;
        }

        public Criteria andJumpPathNotLike(String value) {
            addCriterion("jump_path not like", value, "jumpPath");
            return (Criteria) this;
        }

        public Criteria andJumpPathIn(List<String> values) {
            addCriterion("jump_path in", values, "jumpPath");
            return (Criteria) this;
        }

        public Criteria andJumpPathNotIn(List<String> values) {
            addCriterion("jump_path not in", values, "jumpPath");
            return (Criteria) this;
        }

        public Criteria andJumpPathBetween(String value1, String value2) {
            addCriterion("jump_path between", value1, value2, "jumpPath");
            return (Criteria) this;
        }

        public Criteria andJumpPathNotBetween(String value1, String value2) {
            addCriterion("jump_path not between", value1, value2, "jumpPath");
            return (Criteria) this;
        }

        public Criteria andBottomButtonStatusIsNull() {
            addCriterion("bottom_button_status is null");
            return (Criteria) this;
        }

        public Criteria andBottomButtonStatusIsNotNull() {
            addCriterion("bottom_button_status is not null");
            return (Criteria) this;
        }

        public Criteria andBottomButtonStatusEqualTo(Integer value) {
            addCriterion("bottom_button_status =", value, "bottomButtonStatus");
            return (Criteria) this;
        }

        public Criteria andBottomButtonStatusNotEqualTo(Integer value) {
            addCriterion("bottom_button_status <>", value, "bottomButtonStatus");
            return (Criteria) this;
        }

        public Criteria andBottomButtonStatusGreaterThan(Integer value) {
            addCriterion("bottom_button_status >", value, "bottomButtonStatus");
            return (Criteria) this;
        }

        public Criteria andBottomButtonStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("bottom_button_status >=", value, "bottomButtonStatus");
            return (Criteria) this;
        }

        public Criteria andBottomButtonStatusLessThan(Integer value) {
            addCriterion("bottom_button_status <", value, "bottomButtonStatus");
            return (Criteria) this;
        }

        public Criteria andBottomButtonStatusLessThanOrEqualTo(Integer value) {
            addCriterion("bottom_button_status <=", value, "bottomButtonStatus");
            return (Criteria) this;
        }

        public Criteria andBottomButtonStatusIn(List<Integer> values) {
            addCriterion("bottom_button_status in", values, "bottomButtonStatus");
            return (Criteria) this;
        }

        public Criteria andBottomButtonStatusNotIn(List<Integer> values) {
            addCriterion("bottom_button_status not in", values, "bottomButtonStatus");
            return (Criteria) this;
        }

        public Criteria andBottomButtonStatusBetween(Integer value1, Integer value2) {
            addCriterion("bottom_button_status between", value1, value2, "bottomButtonStatus");
            return (Criteria) this;
        }

        public Criteria andBottomButtonStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("bottom_button_status not between", value1, value2, "bottomButtonStatus");
            return (Criteria) this;
        }

        public Criteria andBottomButtonIsNull() {
            addCriterion("bottom_button is null");
            return (Criteria) this;
        }

        public Criteria andBottomButtonIsNotNull() {
            addCriterion("bottom_button is not null");
            return (Criteria) this;
        }

        public Criteria andBottomButtonEqualTo(String value) {
            addCriterion("bottom_button =", value, "bottomButton");
            return (Criteria) this;
        }

        public Criteria andBottomButtonNotEqualTo(String value) {
            addCriterion("bottom_button <>", value, "bottomButton");
            return (Criteria) this;
        }

        public Criteria andBottomButtonGreaterThan(String value) {
            addCriterion("bottom_button >", value, "bottomButton");
            return (Criteria) this;
        }

        public Criteria andBottomButtonGreaterThanOrEqualTo(String value) {
            addCriterion("bottom_button >=", value, "bottomButton");
            return (Criteria) this;
        }

        public Criteria andBottomButtonLessThan(String value) {
            addCriterion("bottom_button <", value, "bottomButton");
            return (Criteria) this;
        }

        public Criteria andBottomButtonLessThanOrEqualTo(String value) {
            addCriterion("bottom_button <=", value, "bottomButton");
            return (Criteria) this;
        }

        public Criteria andBottomButtonLike(String value) {
            addCriterion("bottom_button like", value, "bottomButton");
            return (Criteria) this;
        }

        public Criteria andBottomButtonNotLike(String value) {
            addCriterion("bottom_button not like", value, "bottomButton");
            return (Criteria) this;
        }

        public Criteria andBottomButtonIn(List<String> values) {
            addCriterion("bottom_button in", values, "bottomButton");
            return (Criteria) this;
        }

        public Criteria andBottomButtonNotIn(List<String> values) {
            addCriterion("bottom_button not in", values, "bottomButton");
            return (Criteria) this;
        }

        public Criteria andBottomButtonBetween(String value1, String value2) {
            addCriterion("bottom_button between", value1, value2, "bottomButton");
            return (Criteria) this;
        }

        public Criteria andBottomButtonNotBetween(String value1, String value2) {
            addCriterion("bottom_button not between", value1, value2, "bottomButton");
            return (Criteria) this;
        }

        public Criteria andDownloadPathIsNull() {
            addCriterion("download_path is null");
            return (Criteria) this;
        }

        public Criteria andDownloadPathIsNotNull() {
            addCriterion("download_path is not null");
            return (Criteria) this;
        }

        public Criteria andDownloadPathEqualTo(String value) {
            addCriterion("download_path =", value, "downloadPath");
            return (Criteria) this;
        }

        public Criteria andDownloadPathNotEqualTo(String value) {
            addCriterion("download_path <>", value, "downloadPath");
            return (Criteria) this;
        }

        public Criteria andDownloadPathGreaterThan(String value) {
            addCriterion("download_path >", value, "downloadPath");
            return (Criteria) this;
        }

        public Criteria andDownloadPathGreaterThanOrEqualTo(String value) {
            addCriterion("download_path >=", value, "downloadPath");
            return (Criteria) this;
        }

        public Criteria andDownloadPathLessThan(String value) {
            addCriterion("download_path <", value, "downloadPath");
            return (Criteria) this;
        }

        public Criteria andDownloadPathLessThanOrEqualTo(String value) {
            addCriterion("download_path <=", value, "downloadPath");
            return (Criteria) this;
        }

        public Criteria andDownloadPathLike(String value) {
            addCriterion("download_path like", value, "downloadPath");
            return (Criteria) this;
        }

        public Criteria andDownloadPathNotLike(String value) {
            addCriterion("download_path not like", value, "downloadPath");
            return (Criteria) this;
        }

        public Criteria andDownloadPathIn(List<String> values) {
            addCriterion("download_path in", values, "downloadPath");
            return (Criteria) this;
        }

        public Criteria andDownloadPathNotIn(List<String> values) {
            addCriterion("download_path not in", values, "downloadPath");
            return (Criteria) this;
        }

        public Criteria andDownloadPathBetween(String value1, String value2) {
            addCriterion("download_path between", value1, value2, "downloadPath");
            return (Criteria) this;
        }

        public Criteria andDownloadPathNotBetween(String value1, String value2) {
            addCriterion("download_path not between", value1, value2, "downloadPath");
            return (Criteria) this;
        }

        public Criteria andDescribeIsNull() {
            addCriterion("`describe` is null");
            return (Criteria) this;
        }

        public Criteria andDescribeIsNotNull() {
            addCriterion("`describe` is not null");
            return (Criteria) this;
        }

        public Criteria andDescribeEqualTo(String value) {
            addCriterion("`describe` =", value, "describe");
            return (Criteria) this;
        }

        public Criteria andDescribeNotEqualTo(String value) {
            addCriterion("`describe` <>", value, "describe");
            return (Criteria) this;
        }

        public Criteria andDescribeGreaterThan(String value) {
            addCriterion("`describe` >", value, "describe");
            return (Criteria) this;
        }

        public Criteria andDescribeGreaterThanOrEqualTo(String value) {
            addCriterion("`describe` >=", value, "describe");
            return (Criteria) this;
        }

        public Criteria andDescribeLessThan(String value) {
            addCriterion("`describe` <", value, "describe");
            return (Criteria) this;
        }

        public Criteria andDescribeLessThanOrEqualTo(String value) {
            addCriterion("`describe` <=", value, "describe");
            return (Criteria) this;
        }

        public Criteria andDescribeLike(String value) {
            addCriterion("`describe` like", value, "describe");
            return (Criteria) this;
        }

        public Criteria andDescribeNotLike(String value) {
            addCriterion("`describe` not like", value, "describe");
            return (Criteria) this;
        }

        public Criteria andDescribeIn(List<String> values) {
            addCriterion("`describe` in", values, "describe");
            return (Criteria) this;
        }

        public Criteria andDescribeNotIn(List<String> values) {
            addCriterion("`describe` not in", values, "describe");
            return (Criteria) this;
        }

        public Criteria andDescribeBetween(String value1, String value2) {
            addCriterion("`describe` between", value1, value2, "describe");
            return (Criteria) this;
        }

        public Criteria andDescribeNotBetween(String value1, String value2) {
            addCriterion("`describe` not between", value1, value2, "describe");
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

        public Criteria andStyleIsNull() {
            addCriterion("`style` is null");
            return (Criteria) this;
        }

        public Criteria andStyleIsNotNull() {
            addCriterion("`style` is not null");
            return (Criteria) this;
        }

        public Criteria andStyleEqualTo(Integer value) {
            addCriterion("`style` =", value, "style");
            return (Criteria) this;
        }

        public Criteria andStyleNotEqualTo(Integer value) {
            addCriterion("`style` <>", value, "style");
            return (Criteria) this;
        }

        public Criteria andStyleGreaterThan(Integer value) {
            addCriterion("`style` >", value, "style");
            return (Criteria) this;
        }

        public Criteria andStyleGreaterThanOrEqualTo(Integer value) {
            addCriterion("`style` >=", value, "style");
            return (Criteria) this;
        }

        public Criteria andStyleLessThan(Integer value) {
            addCriterion("`style` <", value, "style");
            return (Criteria) this;
        }

        public Criteria andStyleLessThanOrEqualTo(Integer value) {
            addCriterion("`style` <=", value, "style");
            return (Criteria) this;
        }

        public Criteria andStyleIn(List<Integer> values) {
            addCriterion("`style` in", values, "style");
            return (Criteria) this;
        }

        public Criteria andStyleNotIn(List<Integer> values) {
            addCriterion("`style` not in", values, "style");
            return (Criteria) this;
        }

        public Criteria andStyleBetween(Integer value1, Integer value2) {
            addCriterion("`style` between", value1, value2, "style");
            return (Criteria) this;
        }

        public Criteria andStyleNotBetween(Integer value1, Integer value2) {
            addCriterion("`style` not between", value1, value2, "style");
            return (Criteria) this;
        }

        public Criteria andStatusOnIsNull() {
            addCriterion("status_on is null");
            return (Criteria) this;
        }

        public Criteria andStatusOnIsNotNull() {
            addCriterion("status_on is not null");
            return (Criteria) this;
        }

        public Criteria andStatusOnEqualTo(Integer value) {
            addCriterion("status_on =", value, "statusOn");
            return (Criteria) this;
        }

        public Criteria andStatusOnNotEqualTo(Integer value) {
            addCriterion("status_on <>", value, "statusOn");
            return (Criteria) this;
        }

        public Criteria andStatusOnGreaterThan(Integer value) {
            addCriterion("status_on >", value, "statusOn");
            return (Criteria) this;
        }

        public Criteria andStatusOnGreaterThanOrEqualTo(Integer value) {
            addCriterion("status_on >=", value, "statusOn");
            return (Criteria) this;
        }

        public Criteria andStatusOnLessThan(Integer value) {
            addCriterion("status_on <", value, "statusOn");
            return (Criteria) this;
        }

        public Criteria andStatusOnLessThanOrEqualTo(Integer value) {
            addCriterion("status_on <=", value, "statusOn");
            return (Criteria) this;
        }

        public Criteria andStatusOnIn(List<Integer> values) {
            addCriterion("status_on in", values, "statusOn");
            return (Criteria) this;
        }

        public Criteria andStatusOnNotIn(List<Integer> values) {
            addCriterion("status_on not in", values, "statusOn");
            return (Criteria) this;
        }

        public Criteria andStatusOnBetween(Integer value1, Integer value2) {
            addCriterion("status_on between", value1, value2, "statusOn");
            return (Criteria) this;
        }

        public Criteria andStatusOnNotBetween(Integer value1, Integer value2) {
            addCriterion("status_on not between", value1, value2, "statusOn");
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

        public Criteria andDelFlagEqualTo(Boolean value) {
            addCriterion("del_flag =", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagNotEqualTo(Boolean value) {
            addCriterion("del_flag <>", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagGreaterThan(Boolean value) {
            addCriterion("del_flag >", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagGreaterThanOrEqualTo(Boolean value) {
            addCriterion("del_flag >=", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagLessThan(Boolean value) {
            addCriterion("del_flag <", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagLessThanOrEqualTo(Boolean value) {
            addCriterion("del_flag <=", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagIn(List<Boolean> values) {
            addCriterion("del_flag in", values, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagNotIn(List<Boolean> values) {
            addCriterion("del_flag not in", values, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagBetween(Boolean value1, Boolean value2) {
            addCriterion("del_flag between", value1, value2, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagNotBetween(Boolean value1, Boolean value2) {
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

        public Criteria andCreateUserIdEqualTo(String value) {
            addCriterion("create_user_id =", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdNotEqualTo(String value) {
            addCriterion("create_user_id <>", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdGreaterThan(String value) {
            addCriterion("create_user_id >", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("create_user_id >=", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdLessThan(String value) {
            addCriterion("create_user_id <", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdLessThanOrEqualTo(String value) {
            addCriterion("create_user_id <=", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdLike(String value) {
            addCriterion("create_user_id like", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdNotLike(String value) {
            addCriterion("create_user_id not like", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdIn(List<String> values) {
            addCriterion("create_user_id in", values, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdNotIn(List<String> values) {
            addCriterion("create_user_id not in", values, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdBetween(String value1, String value2) {
            addCriterion("create_user_id between", value1, value2, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdNotBetween(String value1, String value2) {
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

        public Criteria andUpdateUserIdEqualTo(String value) {
            addCriterion("update_user_id =", value, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdNotEqualTo(String value) {
            addCriterion("update_user_id <>", value, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdGreaterThan(String value) {
            addCriterion("update_user_id >", value, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("update_user_id >=", value, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdLessThan(String value) {
            addCriterion("update_user_id <", value, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdLessThanOrEqualTo(String value) {
            addCriterion("update_user_id <=", value, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdLike(String value) {
            addCriterion("update_user_id like", value, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdNotLike(String value) {
            addCriterion("update_user_id not like", value, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdIn(List<String> values) {
            addCriterion("update_user_id in", values, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdNotIn(List<String> values) {
            addCriterion("update_user_id not in", values, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdBetween(String value1, String value2) {
            addCriterion("update_user_id between", value1, value2, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdNotBetween(String value1, String value2) {
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