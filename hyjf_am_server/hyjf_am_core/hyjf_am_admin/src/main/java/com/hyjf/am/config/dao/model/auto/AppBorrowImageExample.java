package com.hyjf.am.config.dao.model.auto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AppBorrowImageExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public AppBorrowImageExample() {
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

        public Criteria andBorrowImageIsNull() {
            addCriterion("borrow_image is null");
            return (Criteria) this;
        }

        public Criteria andBorrowImageIsNotNull() {
            addCriterion("borrow_image is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowImageEqualTo(String value) {
            addCriterion("borrow_image =", value, "borrowImage");
            return (Criteria) this;
        }

        public Criteria andBorrowImageNotEqualTo(String value) {
            addCriterion("borrow_image <>", value, "borrowImage");
            return (Criteria) this;
        }

        public Criteria andBorrowImageGreaterThan(String value) {
            addCriterion("borrow_image >", value, "borrowImage");
            return (Criteria) this;
        }

        public Criteria andBorrowImageGreaterThanOrEqualTo(String value) {
            addCriterion("borrow_image >=", value, "borrowImage");
            return (Criteria) this;
        }

        public Criteria andBorrowImageLessThan(String value) {
            addCriterion("borrow_image <", value, "borrowImage");
            return (Criteria) this;
        }

        public Criteria andBorrowImageLessThanOrEqualTo(String value) {
            addCriterion("borrow_image <=", value, "borrowImage");
            return (Criteria) this;
        }

        public Criteria andBorrowImageLike(String value) {
            addCriterion("borrow_image like", value, "borrowImage");
            return (Criteria) this;
        }

        public Criteria andBorrowImageNotLike(String value) {
            addCriterion("borrow_image not like", value, "borrowImage");
            return (Criteria) this;
        }

        public Criteria andBorrowImageIn(List<String> values) {
            addCriterion("borrow_image in", values, "borrowImage");
            return (Criteria) this;
        }

        public Criteria andBorrowImageNotIn(List<String> values) {
            addCriterion("borrow_image not in", values, "borrowImage");
            return (Criteria) this;
        }

        public Criteria andBorrowImageBetween(String value1, String value2) {
            addCriterion("borrow_image between", value1, value2, "borrowImage");
            return (Criteria) this;
        }

        public Criteria andBorrowImageNotBetween(String value1, String value2) {
            addCriterion("borrow_image not between", value1, value2, "borrowImage");
            return (Criteria) this;
        }

        public Criteria andBorrowImageTitleIsNull() {
            addCriterion("borrow_image_title is null");
            return (Criteria) this;
        }

        public Criteria andBorrowImageTitleIsNotNull() {
            addCriterion("borrow_image_title is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowImageTitleEqualTo(String value) {
            addCriterion("borrow_image_title =", value, "borrowImageTitle");
            return (Criteria) this;
        }

        public Criteria andBorrowImageTitleNotEqualTo(String value) {
            addCriterion("borrow_image_title <>", value, "borrowImageTitle");
            return (Criteria) this;
        }

        public Criteria andBorrowImageTitleGreaterThan(String value) {
            addCriterion("borrow_image_title >", value, "borrowImageTitle");
            return (Criteria) this;
        }

        public Criteria andBorrowImageTitleGreaterThanOrEqualTo(String value) {
            addCriterion("borrow_image_title >=", value, "borrowImageTitle");
            return (Criteria) this;
        }

        public Criteria andBorrowImageTitleLessThan(String value) {
            addCriterion("borrow_image_title <", value, "borrowImageTitle");
            return (Criteria) this;
        }

        public Criteria andBorrowImageTitleLessThanOrEqualTo(String value) {
            addCriterion("borrow_image_title <=", value, "borrowImageTitle");
            return (Criteria) this;
        }

        public Criteria andBorrowImageTitleLike(String value) {
            addCriterion("borrow_image_title like", value, "borrowImageTitle");
            return (Criteria) this;
        }

        public Criteria andBorrowImageTitleNotLike(String value) {
            addCriterion("borrow_image_title not like", value, "borrowImageTitle");
            return (Criteria) this;
        }

        public Criteria andBorrowImageTitleIn(List<String> values) {
            addCriterion("borrow_image_title in", values, "borrowImageTitle");
            return (Criteria) this;
        }

        public Criteria andBorrowImageTitleNotIn(List<String> values) {
            addCriterion("borrow_image_title not in", values, "borrowImageTitle");
            return (Criteria) this;
        }

        public Criteria andBorrowImageTitleBetween(String value1, String value2) {
            addCriterion("borrow_image_title between", value1, value2, "borrowImageTitle");
            return (Criteria) this;
        }

        public Criteria andBorrowImageTitleNotBetween(String value1, String value2) {
            addCriterion("borrow_image_title not between", value1, value2, "borrowImageTitle");
            return (Criteria) this;
        }

        public Criteria andBorrowImageNameIsNull() {
            addCriterion("borrow_image_name is null");
            return (Criteria) this;
        }

        public Criteria andBorrowImageNameIsNotNull() {
            addCriterion("borrow_image_name is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowImageNameEqualTo(String value) {
            addCriterion("borrow_image_name =", value, "borrowImageName");
            return (Criteria) this;
        }

        public Criteria andBorrowImageNameNotEqualTo(String value) {
            addCriterion("borrow_image_name <>", value, "borrowImageName");
            return (Criteria) this;
        }

        public Criteria andBorrowImageNameGreaterThan(String value) {
            addCriterion("borrow_image_name >", value, "borrowImageName");
            return (Criteria) this;
        }

        public Criteria andBorrowImageNameGreaterThanOrEqualTo(String value) {
            addCriterion("borrow_image_name >=", value, "borrowImageName");
            return (Criteria) this;
        }

        public Criteria andBorrowImageNameLessThan(String value) {
            addCriterion("borrow_image_name <", value, "borrowImageName");
            return (Criteria) this;
        }

        public Criteria andBorrowImageNameLessThanOrEqualTo(String value) {
            addCriterion("borrow_image_name <=", value, "borrowImageName");
            return (Criteria) this;
        }

        public Criteria andBorrowImageNameLike(String value) {
            addCriterion("borrow_image_name like", value, "borrowImageName");
            return (Criteria) this;
        }

        public Criteria andBorrowImageNameNotLike(String value) {
            addCriterion("borrow_image_name not like", value, "borrowImageName");
            return (Criteria) this;
        }

        public Criteria andBorrowImageNameIn(List<String> values) {
            addCriterion("borrow_image_name in", values, "borrowImageName");
            return (Criteria) this;
        }

        public Criteria andBorrowImageNameNotIn(List<String> values) {
            addCriterion("borrow_image_name not in", values, "borrowImageName");
            return (Criteria) this;
        }

        public Criteria andBorrowImageNameBetween(String value1, String value2) {
            addCriterion("borrow_image_name between", value1, value2, "borrowImageName");
            return (Criteria) this;
        }

        public Criteria andBorrowImageNameNotBetween(String value1, String value2) {
            addCriterion("borrow_image_name not between", value1, value2, "borrowImageName");
            return (Criteria) this;
        }

        public Criteria andBorrowImageRealnameIsNull() {
            addCriterion("borrow_image_realname is null");
            return (Criteria) this;
        }

        public Criteria andBorrowImageRealnameIsNotNull() {
            addCriterion("borrow_image_realname is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowImageRealnameEqualTo(String value) {
            addCriterion("borrow_image_realname =", value, "borrowImageRealname");
            return (Criteria) this;
        }

        public Criteria andBorrowImageRealnameNotEqualTo(String value) {
            addCriterion("borrow_image_realname <>", value, "borrowImageRealname");
            return (Criteria) this;
        }

        public Criteria andBorrowImageRealnameGreaterThan(String value) {
            addCriterion("borrow_image_realname >", value, "borrowImageRealname");
            return (Criteria) this;
        }

        public Criteria andBorrowImageRealnameGreaterThanOrEqualTo(String value) {
            addCriterion("borrow_image_realname >=", value, "borrowImageRealname");
            return (Criteria) this;
        }

        public Criteria andBorrowImageRealnameLessThan(String value) {
            addCriterion("borrow_image_realname <", value, "borrowImageRealname");
            return (Criteria) this;
        }

        public Criteria andBorrowImageRealnameLessThanOrEqualTo(String value) {
            addCriterion("borrow_image_realname <=", value, "borrowImageRealname");
            return (Criteria) this;
        }

        public Criteria andBorrowImageRealnameLike(String value) {
            addCriterion("borrow_image_realname like", value, "borrowImageRealname");
            return (Criteria) this;
        }

        public Criteria andBorrowImageRealnameNotLike(String value) {
            addCriterion("borrow_image_realname not like", value, "borrowImageRealname");
            return (Criteria) this;
        }

        public Criteria andBorrowImageRealnameIn(List<String> values) {
            addCriterion("borrow_image_realname in", values, "borrowImageRealname");
            return (Criteria) this;
        }

        public Criteria andBorrowImageRealnameNotIn(List<String> values) {
            addCriterion("borrow_image_realname not in", values, "borrowImageRealname");
            return (Criteria) this;
        }

        public Criteria andBorrowImageRealnameBetween(String value1, String value2) {
            addCriterion("borrow_image_realname between", value1, value2, "borrowImageRealname");
            return (Criteria) this;
        }

        public Criteria andBorrowImageRealnameNotBetween(String value1, String value2) {
            addCriterion("borrow_image_realname not between", value1, value2, "borrowImageRealname");
            return (Criteria) this;
        }

        public Criteria andBorrowImageUrlIsNull() {
            addCriterion("borrow_image_url is null");
            return (Criteria) this;
        }

        public Criteria andBorrowImageUrlIsNotNull() {
            addCriterion("borrow_image_url is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowImageUrlEqualTo(String value) {
            addCriterion("borrow_image_url =", value, "borrowImageUrl");
            return (Criteria) this;
        }

        public Criteria andBorrowImageUrlNotEqualTo(String value) {
            addCriterion("borrow_image_url <>", value, "borrowImageUrl");
            return (Criteria) this;
        }

        public Criteria andBorrowImageUrlGreaterThan(String value) {
            addCriterion("borrow_image_url >", value, "borrowImageUrl");
            return (Criteria) this;
        }

        public Criteria andBorrowImageUrlGreaterThanOrEqualTo(String value) {
            addCriterion("borrow_image_url >=", value, "borrowImageUrl");
            return (Criteria) this;
        }

        public Criteria andBorrowImageUrlLessThan(String value) {
            addCriterion("borrow_image_url <", value, "borrowImageUrl");
            return (Criteria) this;
        }

        public Criteria andBorrowImageUrlLessThanOrEqualTo(String value) {
            addCriterion("borrow_image_url <=", value, "borrowImageUrl");
            return (Criteria) this;
        }

        public Criteria andBorrowImageUrlLike(String value) {
            addCriterion("borrow_image_url like", value, "borrowImageUrl");
            return (Criteria) this;
        }

        public Criteria andBorrowImageUrlNotLike(String value) {
            addCriterion("borrow_image_url not like", value, "borrowImageUrl");
            return (Criteria) this;
        }

        public Criteria andBorrowImageUrlIn(List<String> values) {
            addCriterion("borrow_image_url in", values, "borrowImageUrl");
            return (Criteria) this;
        }

        public Criteria andBorrowImageUrlNotIn(List<String> values) {
            addCriterion("borrow_image_url not in", values, "borrowImageUrl");
            return (Criteria) this;
        }

        public Criteria andBorrowImageUrlBetween(String value1, String value2) {
            addCriterion("borrow_image_url between", value1, value2, "borrowImageUrl");
            return (Criteria) this;
        }

        public Criteria andBorrowImageUrlNotBetween(String value1, String value2) {
            addCriterion("borrow_image_url not between", value1, value2, "borrowImageUrl");
            return (Criteria) this;
        }

        public Criteria andNotesIsNull() {
            addCriterion("notes is null");
            return (Criteria) this;
        }

        public Criteria andNotesIsNotNull() {
            addCriterion("notes is not null");
            return (Criteria) this;
        }

        public Criteria andNotesEqualTo(String value) {
            addCriterion("notes =", value, "notes");
            return (Criteria) this;
        }

        public Criteria andNotesNotEqualTo(String value) {
            addCriterion("notes <>", value, "notes");
            return (Criteria) this;
        }

        public Criteria andNotesGreaterThan(String value) {
            addCriterion("notes >", value, "notes");
            return (Criteria) this;
        }

        public Criteria andNotesGreaterThanOrEqualTo(String value) {
            addCriterion("notes >=", value, "notes");
            return (Criteria) this;
        }

        public Criteria andNotesLessThan(String value) {
            addCriterion("notes <", value, "notes");
            return (Criteria) this;
        }

        public Criteria andNotesLessThanOrEqualTo(String value) {
            addCriterion("notes <=", value, "notes");
            return (Criteria) this;
        }

        public Criteria andNotesLike(String value) {
            addCriterion("notes like", value, "notes");
            return (Criteria) this;
        }

        public Criteria andNotesNotLike(String value) {
            addCriterion("notes not like", value, "notes");
            return (Criteria) this;
        }

        public Criteria andNotesIn(List<String> values) {
            addCriterion("notes in", values, "notes");
            return (Criteria) this;
        }

        public Criteria andNotesNotIn(List<String> values) {
            addCriterion("notes not in", values, "notes");
            return (Criteria) this;
        }

        public Criteria andNotesBetween(String value1, String value2) {
            addCriterion("notes between", value1, value2, "notes");
            return (Criteria) this;
        }

        public Criteria andNotesNotBetween(String value1, String value2) {
            addCriterion("notes not between", value1, value2, "notes");
            return (Criteria) this;
        }

        public Criteria andSortIsNull() {
            addCriterion("sort is null");
            return (Criteria) this;
        }

        public Criteria andSortIsNotNull() {
            addCriterion("sort is not null");
            return (Criteria) this;
        }

        public Criteria andSortEqualTo(Integer value) {
            addCriterion("sort =", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotEqualTo(Integer value) {
            addCriterion("sort <>", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortGreaterThan(Integer value) {
            addCriterion("sort >", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortGreaterThanOrEqualTo(Integer value) {
            addCriterion("sort >=", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortLessThan(Integer value) {
            addCriterion("sort <", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortLessThanOrEqualTo(Integer value) {
            addCriterion("sort <=", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortIn(List<Integer> values) {
            addCriterion("sort in", values, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotIn(List<Integer> values) {
            addCriterion("sort not in", values, "sort");
            return (Criteria) this;
        }

        public Criteria andSortBetween(Integer value1, Integer value2) {
            addCriterion("sort between", value1, value2, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotBetween(Integer value1, Integer value2) {
            addCriterion("sort not between", value1, value2, "sort");
            return (Criteria) this;
        }

        public Criteria andPageUrlIsNull() {
            addCriterion("page_url is null");
            return (Criteria) this;
        }

        public Criteria andPageUrlIsNotNull() {
            addCriterion("page_url is not null");
            return (Criteria) this;
        }

        public Criteria andPageUrlEqualTo(String value) {
            addCriterion("page_url =", value, "pageUrl");
            return (Criteria) this;
        }

        public Criteria andPageUrlNotEqualTo(String value) {
            addCriterion("page_url <>", value, "pageUrl");
            return (Criteria) this;
        }

        public Criteria andPageUrlGreaterThan(String value) {
            addCriterion("page_url >", value, "pageUrl");
            return (Criteria) this;
        }

        public Criteria andPageUrlGreaterThanOrEqualTo(String value) {
            addCriterion("page_url >=", value, "pageUrl");
            return (Criteria) this;
        }

        public Criteria andPageUrlLessThan(String value) {
            addCriterion("page_url <", value, "pageUrl");
            return (Criteria) this;
        }

        public Criteria andPageUrlLessThanOrEqualTo(String value) {
            addCriterion("page_url <=", value, "pageUrl");
            return (Criteria) this;
        }

        public Criteria andPageUrlLike(String value) {
            addCriterion("page_url like", value, "pageUrl");
            return (Criteria) this;
        }

        public Criteria andPageUrlNotLike(String value) {
            addCriterion("page_url not like", value, "pageUrl");
            return (Criteria) this;
        }

        public Criteria andPageUrlIn(List<String> values) {
            addCriterion("page_url in", values, "pageUrl");
            return (Criteria) this;
        }

        public Criteria andPageUrlNotIn(List<String> values) {
            addCriterion("page_url not in", values, "pageUrl");
            return (Criteria) this;
        }

        public Criteria andPageUrlBetween(String value1, String value2) {
            addCriterion("page_url between", value1, value2, "pageUrl");
            return (Criteria) this;
        }

        public Criteria andPageUrlNotBetween(String value1, String value2) {
            addCriterion("page_url not between", value1, value2, "pageUrl");
            return (Criteria) this;
        }

        public Criteria andPageTypeIsNull() {
            addCriterion("page_type is null");
            return (Criteria) this;
        }

        public Criteria andPageTypeIsNotNull() {
            addCriterion("page_type is not null");
            return (Criteria) this;
        }

        public Criteria andPageTypeEqualTo(String value) {
            addCriterion("page_type =", value, "pageType");
            return (Criteria) this;
        }

        public Criteria andPageTypeNotEqualTo(String value) {
            addCriterion("page_type <>", value, "pageType");
            return (Criteria) this;
        }

        public Criteria andPageTypeGreaterThan(String value) {
            addCriterion("page_type >", value, "pageType");
            return (Criteria) this;
        }

        public Criteria andPageTypeGreaterThanOrEqualTo(String value) {
            addCriterion("page_type >=", value, "pageType");
            return (Criteria) this;
        }

        public Criteria andPageTypeLessThan(String value) {
            addCriterion("page_type <", value, "pageType");
            return (Criteria) this;
        }

        public Criteria andPageTypeLessThanOrEqualTo(String value) {
            addCriterion("page_type <=", value, "pageType");
            return (Criteria) this;
        }

        public Criteria andPageTypeLike(String value) {
            addCriterion("page_type like", value, "pageType");
            return (Criteria) this;
        }

        public Criteria andPageTypeNotLike(String value) {
            addCriterion("page_type not like", value, "pageType");
            return (Criteria) this;
        }

        public Criteria andPageTypeIn(List<String> values) {
            addCriterion("page_type in", values, "pageType");
            return (Criteria) this;
        }

        public Criteria andPageTypeNotIn(List<String> values) {
            addCriterion("page_type not in", values, "pageType");
            return (Criteria) this;
        }

        public Criteria andPageTypeBetween(String value1, String value2) {
            addCriterion("page_type between", value1, value2, "pageType");
            return (Criteria) this;
        }

        public Criteria andPageTypeNotBetween(String value1, String value2) {
            addCriterion("page_type not between", value1, value2, "pageType");
            return (Criteria) this;
        }

        public Criteria andVersionIsNull() {
            addCriterion("version is null");
            return (Criteria) this;
        }

        public Criteria andVersionIsNotNull() {
            addCriterion("version is not null");
            return (Criteria) this;
        }

        public Criteria andVersionEqualTo(String value) {
            addCriterion("version =", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotEqualTo(String value) {
            addCriterion("version <>", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThan(String value) {
            addCriterion("version >", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThanOrEqualTo(String value) {
            addCriterion("version >=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThan(String value) {
            addCriterion("version <", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThanOrEqualTo(String value) {
            addCriterion("version <=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLike(String value) {
            addCriterion("version like", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotLike(String value) {
            addCriterion("version not like", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionIn(List<String> values) {
            addCriterion("version in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotIn(List<String> values) {
            addCriterion("version not in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionBetween(String value1, String value2) {
            addCriterion("version between", value1, value2, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotBetween(String value1, String value2) {
            addCriterion("version not between", value1, value2, "version");
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

        public Criteria andVersionMaxIsNull() {
            addCriterion("version_max is null");
            return (Criteria) this;
        }

        public Criteria andVersionMaxIsNotNull() {
            addCriterion("version_max is not null");
            return (Criteria) this;
        }

        public Criteria andVersionMaxEqualTo(String value) {
            addCriterion("version_max =", value, "versionMax");
            return (Criteria) this;
        }

        public Criteria andVersionMaxNotEqualTo(String value) {
            addCriterion("version_max <>", value, "versionMax");
            return (Criteria) this;
        }

        public Criteria andVersionMaxGreaterThan(String value) {
            addCriterion("version_max >", value, "versionMax");
            return (Criteria) this;
        }

        public Criteria andVersionMaxGreaterThanOrEqualTo(String value) {
            addCriterion("version_max >=", value, "versionMax");
            return (Criteria) this;
        }

        public Criteria andVersionMaxLessThan(String value) {
            addCriterion("version_max <", value, "versionMax");
            return (Criteria) this;
        }

        public Criteria andVersionMaxLessThanOrEqualTo(String value) {
            addCriterion("version_max <=", value, "versionMax");
            return (Criteria) this;
        }

        public Criteria andVersionMaxLike(String value) {
            addCriterion("version_max like", value, "versionMax");
            return (Criteria) this;
        }

        public Criteria andVersionMaxNotLike(String value) {
            addCriterion("version_max not like", value, "versionMax");
            return (Criteria) this;
        }

        public Criteria andVersionMaxIn(List<String> values) {
            addCriterion("version_max in", values, "versionMax");
            return (Criteria) this;
        }

        public Criteria andVersionMaxNotIn(List<String> values) {
            addCriterion("version_max not in", values, "versionMax");
            return (Criteria) this;
        }

        public Criteria andVersionMaxBetween(String value1, String value2) {
            addCriterion("version_max between", value1, value2, "versionMax");
            return (Criteria) this;
        }

        public Criteria andVersionMaxNotBetween(String value1, String value2) {
            addCriterion("version_max not between", value1, value2, "versionMax");
            return (Criteria) this;
        }

        public Criteria andJumpNameIsNull() {
            addCriterion("jump_name is null");
            return (Criteria) this;
        }

        public Criteria andJumpNameIsNotNull() {
            addCriterion("jump_name is not null");
            return (Criteria) this;
        }

        public Criteria andJumpNameEqualTo(String value) {
            addCriterion("jump_name =", value, "jumpName");
            return (Criteria) this;
        }

        public Criteria andJumpNameNotEqualTo(String value) {
            addCriterion("jump_name <>", value, "jumpName");
            return (Criteria) this;
        }

        public Criteria andJumpNameGreaterThan(String value) {
            addCriterion("jump_name >", value, "jumpName");
            return (Criteria) this;
        }

        public Criteria andJumpNameGreaterThanOrEqualTo(String value) {
            addCriterion("jump_name >=", value, "jumpName");
            return (Criteria) this;
        }

        public Criteria andJumpNameLessThan(String value) {
            addCriterion("jump_name <", value, "jumpName");
            return (Criteria) this;
        }

        public Criteria andJumpNameLessThanOrEqualTo(String value) {
            addCriterion("jump_name <=", value, "jumpName");
            return (Criteria) this;
        }

        public Criteria andJumpNameLike(String value) {
            addCriterion("jump_name like", value, "jumpName");
            return (Criteria) this;
        }

        public Criteria andJumpNameNotLike(String value) {
            addCriterion("jump_name not like", value, "jumpName");
            return (Criteria) this;
        }

        public Criteria andJumpNameIn(List<String> values) {
            addCriterion("jump_name in", values, "jumpName");
            return (Criteria) this;
        }

        public Criteria andJumpNameNotIn(List<String> values) {
            addCriterion("jump_name not in", values, "jumpName");
            return (Criteria) this;
        }

        public Criteria andJumpNameBetween(String value1, String value2) {
            addCriterion("jump_name between", value1, value2, "jumpName");
            return (Criteria) this;
        }

        public Criteria andJumpNameNotBetween(String value1, String value2) {
            addCriterion("jump_name not between", value1, value2, "jumpName");
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