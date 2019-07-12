package com.hyjf.am.user.dao.model.auto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TemplateConfigExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public TemplateConfigExample() {
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

        public Criteria andTempTypeIsNull() {
            addCriterion("temp_type is null");
            return (Criteria) this;
        }

        public Criteria andTempTypeIsNotNull() {
            addCriterion("temp_type is not null");
            return (Criteria) this;
        }

        public Criteria andTempTypeEqualTo(Integer value) {
            addCriterion("temp_type =", value, "tempType");
            return (Criteria) this;
        }

        public Criteria andTempTypeNotEqualTo(Integer value) {
            addCriterion("temp_type <>", value, "tempType");
            return (Criteria) this;
        }

        public Criteria andTempTypeGreaterThan(Integer value) {
            addCriterion("temp_type >", value, "tempType");
            return (Criteria) this;
        }

        public Criteria andTempTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("temp_type >=", value, "tempType");
            return (Criteria) this;
        }

        public Criteria andTempTypeLessThan(Integer value) {
            addCriterion("temp_type <", value, "tempType");
            return (Criteria) this;
        }

        public Criteria andTempTypeLessThanOrEqualTo(Integer value) {
            addCriterion("temp_type <=", value, "tempType");
            return (Criteria) this;
        }

        public Criteria andTempTypeIn(List<Integer> values) {
            addCriterion("temp_type in", values, "tempType");
            return (Criteria) this;
        }

        public Criteria andTempTypeNotIn(List<Integer> values) {
            addCriterion("temp_type not in", values, "tempType");
            return (Criteria) this;
        }

        public Criteria andTempTypeBetween(Integer value1, Integer value2) {
            addCriterion("temp_type between", value1, value2, "tempType");
            return (Criteria) this;
        }

        public Criteria andTempTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("temp_type not between", value1, value2, "tempType");
            return (Criteria) this;
        }

        public Criteria andTempNameIsNull() {
            addCriterion("temp_name is null");
            return (Criteria) this;
        }

        public Criteria andTempNameIsNotNull() {
            addCriterion("temp_name is not null");
            return (Criteria) this;
        }

        public Criteria andTempNameEqualTo(String value) {
            addCriterion("temp_name =", value, "tempName");
            return (Criteria) this;
        }

        public Criteria andTempNameNotEqualTo(String value) {
            addCriterion("temp_name <>", value, "tempName");
            return (Criteria) this;
        }

        public Criteria andTempNameGreaterThan(String value) {
            addCriterion("temp_name >", value, "tempName");
            return (Criteria) this;
        }

        public Criteria andTempNameGreaterThanOrEqualTo(String value) {
            addCriterion("temp_name >=", value, "tempName");
            return (Criteria) this;
        }

        public Criteria andTempNameLessThan(String value) {
            addCriterion("temp_name <", value, "tempName");
            return (Criteria) this;
        }

        public Criteria andTempNameLessThanOrEqualTo(String value) {
            addCriterion("temp_name <=", value, "tempName");
            return (Criteria) this;
        }

        public Criteria andTempNameLike(String value) {
            addCriterion("temp_name like", value, "tempName");
            return (Criteria) this;
        }

        public Criteria andTempNameNotLike(String value) {
            addCriterion("temp_name not like", value, "tempName");
            return (Criteria) this;
        }

        public Criteria andTempNameIn(List<String> values) {
            addCriterion("temp_name in", values, "tempName");
            return (Criteria) this;
        }

        public Criteria andTempNameNotIn(List<String> values) {
            addCriterion("temp_name not in", values, "tempName");
            return (Criteria) this;
        }

        public Criteria andTempNameBetween(String value1, String value2) {
            addCriterion("temp_name between", value1, value2, "tempName");
            return (Criteria) this;
        }

        public Criteria andTempNameNotBetween(String value1, String value2) {
            addCriterion("temp_name not between", value1, value2, "tempName");
            return (Criteria) this;
        }

        public Criteria andTempTitleIsNull() {
            addCriterion("temp_title is null");
            return (Criteria) this;
        }

        public Criteria andTempTitleIsNotNull() {
            addCriterion("temp_title is not null");
            return (Criteria) this;
        }

        public Criteria andTempTitleEqualTo(String value) {
            addCriterion("temp_title =", value, "tempTitle");
            return (Criteria) this;
        }

        public Criteria andTempTitleNotEqualTo(String value) {
            addCriterion("temp_title <>", value, "tempTitle");
            return (Criteria) this;
        }

        public Criteria andTempTitleGreaterThan(String value) {
            addCriterion("temp_title >", value, "tempTitle");
            return (Criteria) this;
        }

        public Criteria andTempTitleGreaterThanOrEqualTo(String value) {
            addCriterion("temp_title >=", value, "tempTitle");
            return (Criteria) this;
        }

        public Criteria andTempTitleLessThan(String value) {
            addCriterion("temp_title <", value, "tempTitle");
            return (Criteria) this;
        }

        public Criteria andTempTitleLessThanOrEqualTo(String value) {
            addCriterion("temp_title <=", value, "tempTitle");
            return (Criteria) this;
        }

        public Criteria andTempTitleLike(String value) {
            addCriterion("temp_title like", value, "tempTitle");
            return (Criteria) this;
        }

        public Criteria andTempTitleNotLike(String value) {
            addCriterion("temp_title not like", value, "tempTitle");
            return (Criteria) this;
        }

        public Criteria andTempTitleIn(List<String> values) {
            addCriterion("temp_title in", values, "tempTitle");
            return (Criteria) this;
        }

        public Criteria andTempTitleNotIn(List<String> values) {
            addCriterion("temp_title not in", values, "tempTitle");
            return (Criteria) this;
        }

        public Criteria andTempTitleBetween(String value1, String value2) {
            addCriterion("temp_title between", value1, value2, "tempTitle");
            return (Criteria) this;
        }

        public Criteria andTempTitleNotBetween(String value1, String value2) {
            addCriterion("temp_title not between", value1, value2, "tempTitle");
            return (Criteria) this;
        }

        public Criteria andTopImgIsNull() {
            addCriterion("top_img is null");
            return (Criteria) this;
        }

        public Criteria andTopImgIsNotNull() {
            addCriterion("top_img is not null");
            return (Criteria) this;
        }

        public Criteria andTopImgEqualTo(String value) {
            addCriterion("top_img =", value, "topImg");
            return (Criteria) this;
        }

        public Criteria andTopImgNotEqualTo(String value) {
            addCriterion("top_img <>", value, "topImg");
            return (Criteria) this;
        }

        public Criteria andTopImgGreaterThan(String value) {
            addCriterion("top_img >", value, "topImg");
            return (Criteria) this;
        }

        public Criteria andTopImgGreaterThanOrEqualTo(String value) {
            addCriterion("top_img >=", value, "topImg");
            return (Criteria) this;
        }

        public Criteria andTopImgLessThan(String value) {
            addCriterion("top_img <", value, "topImg");
            return (Criteria) this;
        }

        public Criteria andTopImgLessThanOrEqualTo(String value) {
            addCriterion("top_img <=", value, "topImg");
            return (Criteria) this;
        }

        public Criteria andTopImgLike(String value) {
            addCriterion("top_img like", value, "topImg");
            return (Criteria) this;
        }

        public Criteria andTopImgNotLike(String value) {
            addCriterion("top_img not like", value, "topImg");
            return (Criteria) this;
        }

        public Criteria andTopImgIn(List<String> values) {
            addCriterion("top_img in", values, "topImg");
            return (Criteria) this;
        }

        public Criteria andTopImgNotIn(List<String> values) {
            addCriterion("top_img not in", values, "topImg");
            return (Criteria) this;
        }

        public Criteria andTopImgBetween(String value1, String value2) {
            addCriterion("top_img between", value1, value2, "topImg");
            return (Criteria) this;
        }

        public Criteria andTopImgNotBetween(String value1, String value2) {
            addCriterion("top_img not between", value1, value2, "topImg");
            return (Criteria) this;
        }

        public Criteria andBottomImgIsNull() {
            addCriterion("bottom_img is null");
            return (Criteria) this;
        }

        public Criteria andBottomImgIsNotNull() {
            addCriterion("bottom_img is not null");
            return (Criteria) this;
        }

        public Criteria andBottomImgEqualTo(String value) {
            addCriterion("bottom_img =", value, "bottomImg");
            return (Criteria) this;
        }

        public Criteria andBottomImgNotEqualTo(String value) {
            addCriterion("bottom_img <>", value, "bottomImg");
            return (Criteria) this;
        }

        public Criteria andBottomImgGreaterThan(String value) {
            addCriterion("bottom_img >", value, "bottomImg");
            return (Criteria) this;
        }

        public Criteria andBottomImgGreaterThanOrEqualTo(String value) {
            addCriterion("bottom_img >=", value, "bottomImg");
            return (Criteria) this;
        }

        public Criteria andBottomImgLessThan(String value) {
            addCriterion("bottom_img <", value, "bottomImg");
            return (Criteria) this;
        }

        public Criteria andBottomImgLessThanOrEqualTo(String value) {
            addCriterion("bottom_img <=", value, "bottomImg");
            return (Criteria) this;
        }

        public Criteria andBottomImgLike(String value) {
            addCriterion("bottom_img like", value, "bottomImg");
            return (Criteria) this;
        }

        public Criteria andBottomImgNotLike(String value) {
            addCriterion("bottom_img not like", value, "bottomImg");
            return (Criteria) this;
        }

        public Criteria andBottomImgIn(List<String> values) {
            addCriterion("bottom_img in", values, "bottomImg");
            return (Criteria) this;
        }

        public Criteria andBottomImgNotIn(List<String> values) {
            addCriterion("bottom_img not in", values, "bottomImg");
            return (Criteria) this;
        }

        public Criteria andBottomImgBetween(String value1, String value2) {
            addCriterion("bottom_img between", value1, value2, "bottomImg");
            return (Criteria) this;
        }

        public Criteria andBottomImgNotBetween(String value1, String value2) {
            addCriterion("bottom_img not between", value1, value2, "bottomImg");
            return (Criteria) this;
        }

        public Criteria andDominantColorIsNull() {
            addCriterion("dominant_color is null");
            return (Criteria) this;
        }

        public Criteria andDominantColorIsNotNull() {
            addCriterion("dominant_color is not null");
            return (Criteria) this;
        }

        public Criteria andDominantColorEqualTo(String value) {
            addCriterion("dominant_color =", value, "dominantColor");
            return (Criteria) this;
        }

        public Criteria andDominantColorNotEqualTo(String value) {
            addCriterion("dominant_color <>", value, "dominantColor");
            return (Criteria) this;
        }

        public Criteria andDominantColorGreaterThan(String value) {
            addCriterion("dominant_color >", value, "dominantColor");
            return (Criteria) this;
        }

        public Criteria andDominantColorGreaterThanOrEqualTo(String value) {
            addCriterion("dominant_color >=", value, "dominantColor");
            return (Criteria) this;
        }

        public Criteria andDominantColorLessThan(String value) {
            addCriterion("dominant_color <", value, "dominantColor");
            return (Criteria) this;
        }

        public Criteria andDominantColorLessThanOrEqualTo(String value) {
            addCriterion("dominant_color <=", value, "dominantColor");
            return (Criteria) this;
        }

        public Criteria andDominantColorLike(String value) {
            addCriterion("dominant_color like", value, "dominantColor");
            return (Criteria) this;
        }

        public Criteria andDominantColorNotLike(String value) {
            addCriterion("dominant_color not like", value, "dominantColor");
            return (Criteria) this;
        }

        public Criteria andDominantColorIn(List<String> values) {
            addCriterion("dominant_color in", values, "dominantColor");
            return (Criteria) this;
        }

        public Criteria andDominantColorNotIn(List<String> values) {
            addCriterion("dominant_color not in", values, "dominantColor");
            return (Criteria) this;
        }

        public Criteria andDominantColorBetween(String value1, String value2) {
            addCriterion("dominant_color between", value1, value2, "dominantColor");
            return (Criteria) this;
        }

        public Criteria andDominantColorNotBetween(String value1, String value2) {
            addCriterion("dominant_color not between", value1, value2, "dominantColor");
            return (Criteria) this;
        }

        public Criteria andSecondaryColorIsNull() {
            addCriterion("secondary_color is null");
            return (Criteria) this;
        }

        public Criteria andSecondaryColorIsNotNull() {
            addCriterion("secondary_color is not null");
            return (Criteria) this;
        }

        public Criteria andSecondaryColorEqualTo(String value) {
            addCriterion("secondary_color =", value, "secondaryColor");
            return (Criteria) this;
        }

        public Criteria andSecondaryColorNotEqualTo(String value) {
            addCriterion("secondary_color <>", value, "secondaryColor");
            return (Criteria) this;
        }

        public Criteria andSecondaryColorGreaterThan(String value) {
            addCriterion("secondary_color >", value, "secondaryColor");
            return (Criteria) this;
        }

        public Criteria andSecondaryColorGreaterThanOrEqualTo(String value) {
            addCriterion("secondary_color >=", value, "secondaryColor");
            return (Criteria) this;
        }

        public Criteria andSecondaryColorLessThan(String value) {
            addCriterion("secondary_color <", value, "secondaryColor");
            return (Criteria) this;
        }

        public Criteria andSecondaryColorLessThanOrEqualTo(String value) {
            addCriterion("secondary_color <=", value, "secondaryColor");
            return (Criteria) this;
        }

        public Criteria andSecondaryColorLike(String value) {
            addCriterion("secondary_color like", value, "secondaryColor");
            return (Criteria) this;
        }

        public Criteria andSecondaryColorNotLike(String value) {
            addCriterion("secondary_color not like", value, "secondaryColor");
            return (Criteria) this;
        }

        public Criteria andSecondaryColorIn(List<String> values) {
            addCriterion("secondary_color in", values, "secondaryColor");
            return (Criteria) this;
        }

        public Criteria andSecondaryColorNotIn(List<String> values) {
            addCriterion("secondary_color not in", values, "secondaryColor");
            return (Criteria) this;
        }

        public Criteria andSecondaryColorBetween(String value1, String value2) {
            addCriterion("secondary_color between", value1, value2, "secondaryColor");
            return (Criteria) this;
        }

        public Criteria andSecondaryColorNotBetween(String value1, String value2) {
            addCriterion("secondary_color not between", value1, value2, "secondaryColor");
            return (Criteria) this;
        }

        public Criteria andBackgroundColorIsNull() {
            addCriterion("background_color is null");
            return (Criteria) this;
        }

        public Criteria andBackgroundColorIsNotNull() {
            addCriterion("background_color is not null");
            return (Criteria) this;
        }

        public Criteria andBackgroundColorEqualTo(String value) {
            addCriterion("background_color =", value, "backgroundColor");
            return (Criteria) this;
        }

        public Criteria andBackgroundColorNotEqualTo(String value) {
            addCriterion("background_color <>", value, "backgroundColor");
            return (Criteria) this;
        }

        public Criteria andBackgroundColorGreaterThan(String value) {
            addCriterion("background_color >", value, "backgroundColor");
            return (Criteria) this;
        }

        public Criteria andBackgroundColorGreaterThanOrEqualTo(String value) {
            addCriterion("background_color >=", value, "backgroundColor");
            return (Criteria) this;
        }

        public Criteria andBackgroundColorLessThan(String value) {
            addCriterion("background_color <", value, "backgroundColor");
            return (Criteria) this;
        }

        public Criteria andBackgroundColorLessThanOrEqualTo(String value) {
            addCriterion("background_color <=", value, "backgroundColor");
            return (Criteria) this;
        }

        public Criteria andBackgroundColorLike(String value) {
            addCriterion("background_color like", value, "backgroundColor");
            return (Criteria) this;
        }

        public Criteria andBackgroundColorNotLike(String value) {
            addCriterion("background_color not like", value, "backgroundColor");
            return (Criteria) this;
        }

        public Criteria andBackgroundColorIn(List<String> values) {
            addCriterion("background_color in", values, "backgroundColor");
            return (Criteria) this;
        }

        public Criteria andBackgroundColorNotIn(List<String> values) {
            addCriterion("background_color not in", values, "backgroundColor");
            return (Criteria) this;
        }

        public Criteria andBackgroundColorBetween(String value1, String value2) {
            addCriterion("background_color between", value1, value2, "backgroundColor");
            return (Criteria) this;
        }

        public Criteria andBackgroundColorNotBetween(String value1, String value2) {
            addCriterion("background_color not between", value1, value2, "backgroundColor");
            return (Criteria) this;
        }

        public Criteria andButtonTextIsNull() {
            addCriterion("button_text is null");
            return (Criteria) this;
        }

        public Criteria andButtonTextIsNotNull() {
            addCriterion("button_text is not null");
            return (Criteria) this;
        }

        public Criteria andButtonTextEqualTo(String value) {
            addCriterion("button_text =", value, "buttonText");
            return (Criteria) this;
        }

        public Criteria andButtonTextNotEqualTo(String value) {
            addCriterion("button_text <>", value, "buttonText");
            return (Criteria) this;
        }

        public Criteria andButtonTextGreaterThan(String value) {
            addCriterion("button_text >", value, "buttonText");
            return (Criteria) this;
        }

        public Criteria andButtonTextGreaterThanOrEqualTo(String value) {
            addCriterion("button_text >=", value, "buttonText");
            return (Criteria) this;
        }

        public Criteria andButtonTextLessThan(String value) {
            addCriterion("button_text <", value, "buttonText");
            return (Criteria) this;
        }

        public Criteria andButtonTextLessThanOrEqualTo(String value) {
            addCriterion("button_text <=", value, "buttonText");
            return (Criteria) this;
        }

        public Criteria andButtonTextLike(String value) {
            addCriterion("button_text like", value, "buttonText");
            return (Criteria) this;
        }

        public Criteria andButtonTextNotLike(String value) {
            addCriterion("button_text not like", value, "buttonText");
            return (Criteria) this;
        }

        public Criteria andButtonTextIn(List<String> values) {
            addCriterion("button_text in", values, "buttonText");
            return (Criteria) this;
        }

        public Criteria andButtonTextNotIn(List<String> values) {
            addCriterion("button_text not in", values, "buttonText");
            return (Criteria) this;
        }

        public Criteria andButtonTextBetween(String value1, String value2) {
            addCriterion("button_text between", value1, value2, "buttonText");
            return (Criteria) this;
        }

        public Criteria andButtonTextNotBetween(String value1, String value2) {
            addCriterion("button_text not between", value1, value2, "buttonText");
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

        public Criteria andLayerImgIsNull() {
            addCriterion("layer_img is null");
            return (Criteria) this;
        }

        public Criteria andLayerImgIsNotNull() {
            addCriterion("layer_img is not null");
            return (Criteria) this;
        }

        public Criteria andLayerImgEqualTo(String value) {
            addCriterion("layer_img =", value, "layerImg");
            return (Criteria) this;
        }

        public Criteria andLayerImgNotEqualTo(String value) {
            addCriterion("layer_img <>", value, "layerImg");
            return (Criteria) this;
        }

        public Criteria andLayerImgGreaterThan(String value) {
            addCriterion("layer_img >", value, "layerImg");
            return (Criteria) this;
        }

        public Criteria andLayerImgGreaterThanOrEqualTo(String value) {
            addCriterion("layer_img >=", value, "layerImg");
            return (Criteria) this;
        }

        public Criteria andLayerImgLessThan(String value) {
            addCriterion("layer_img <", value, "layerImg");
            return (Criteria) this;
        }

        public Criteria andLayerImgLessThanOrEqualTo(String value) {
            addCriterion("layer_img <=", value, "layerImg");
            return (Criteria) this;
        }

        public Criteria andLayerImgLike(String value) {
            addCriterion("layer_img like", value, "layerImg");
            return (Criteria) this;
        }

        public Criteria andLayerImgNotLike(String value) {
            addCriterion("layer_img not like", value, "layerImg");
            return (Criteria) this;
        }

        public Criteria andLayerImgIn(List<String> values) {
            addCriterion("layer_img in", values, "layerImg");
            return (Criteria) this;
        }

        public Criteria andLayerImgNotIn(List<String> values) {
            addCriterion("layer_img not in", values, "layerImg");
            return (Criteria) this;
        }

        public Criteria andLayerImgBetween(String value1, String value2) {
            addCriterion("layer_img between", value1, value2, "layerImg");
            return (Criteria) this;
        }

        public Criteria andLayerImgNotBetween(String value1, String value2) {
            addCriterion("layer_img not between", value1, value2, "layerImg");
            return (Criteria) this;
        }

        public Criteria andLayerNameIsNull() {
            addCriterion("layer_name is null");
            return (Criteria) this;
        }

        public Criteria andLayerNameIsNotNull() {
            addCriterion("layer_name is not null");
            return (Criteria) this;
        }

        public Criteria andLayerNameEqualTo(String value) {
            addCriterion("layer_name =", value, "layerName");
            return (Criteria) this;
        }

        public Criteria andLayerNameNotEqualTo(String value) {
            addCriterion("layer_name <>", value, "layerName");
            return (Criteria) this;
        }

        public Criteria andLayerNameGreaterThan(String value) {
            addCriterion("layer_name >", value, "layerName");
            return (Criteria) this;
        }

        public Criteria andLayerNameGreaterThanOrEqualTo(String value) {
            addCriterion("layer_name >=", value, "layerName");
            return (Criteria) this;
        }

        public Criteria andLayerNameLessThan(String value) {
            addCriterion("layer_name <", value, "layerName");
            return (Criteria) this;
        }

        public Criteria andLayerNameLessThanOrEqualTo(String value) {
            addCriterion("layer_name <=", value, "layerName");
            return (Criteria) this;
        }

        public Criteria andLayerNameLike(String value) {
            addCriterion("layer_name like", value, "layerName");
            return (Criteria) this;
        }

        public Criteria andLayerNameNotLike(String value) {
            addCriterion("layer_name not like", value, "layerName");
            return (Criteria) this;
        }

        public Criteria andLayerNameIn(List<String> values) {
            addCriterion("layer_name in", values, "layerName");
            return (Criteria) this;
        }

        public Criteria andLayerNameNotIn(List<String> values) {
            addCriterion("layer_name not in", values, "layerName");
            return (Criteria) this;
        }

        public Criteria andLayerNameBetween(String value1, String value2) {
            addCriterion("layer_name between", value1, value2, "layerName");
            return (Criteria) this;
        }

        public Criteria andLayerNameNotBetween(String value1, String value2) {
            addCriterion("layer_name not between", value1, value2, "layerName");
            return (Criteria) this;
        }

        public Criteria andIsJumtIsNull() {
            addCriterion("is_jumt is null");
            return (Criteria) this;
        }

        public Criteria andIsJumtIsNotNull() {
            addCriterion("is_jumt is not null");
            return (Criteria) this;
        }

        public Criteria andIsJumtEqualTo(Integer value) {
            addCriterion("is_jumt =", value, "isJumt");
            return (Criteria) this;
        }

        public Criteria andIsJumtNotEqualTo(Integer value) {
            addCriterion("is_jumt <>", value, "isJumt");
            return (Criteria) this;
        }

        public Criteria andIsJumtGreaterThan(Integer value) {
            addCriterion("is_jumt >", value, "isJumt");
            return (Criteria) this;
        }

        public Criteria andIsJumtGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_jumt >=", value, "isJumt");
            return (Criteria) this;
        }

        public Criteria andIsJumtLessThan(Integer value) {
            addCriterion("is_jumt <", value, "isJumt");
            return (Criteria) this;
        }

        public Criteria andIsJumtLessThanOrEqualTo(Integer value) {
            addCriterion("is_jumt <=", value, "isJumt");
            return (Criteria) this;
        }

        public Criteria andIsJumtIn(List<Integer> values) {
            addCriterion("is_jumt in", values, "isJumt");
            return (Criteria) this;
        }

        public Criteria andIsJumtNotIn(List<Integer> values) {
            addCriterion("is_jumt not in", values, "isJumt");
            return (Criteria) this;
        }

        public Criteria andIsJumtBetween(Integer value1, Integer value2) {
            addCriterion("is_jumt between", value1, value2, "isJumt");
            return (Criteria) this;
        }

        public Criteria andIsJumtNotBetween(Integer value1, Integer value2) {
            addCriterion("is_jumt not between", value1, value2, "isJumt");
            return (Criteria) this;
        }

        public Criteria andJumtUrlIsNull() {
            addCriterion("jumt_url is null");
            return (Criteria) this;
        }

        public Criteria andJumtUrlIsNotNull() {
            addCriterion("jumt_url is not null");
            return (Criteria) this;
        }

        public Criteria andJumtUrlEqualTo(String value) {
            addCriterion("jumt_url =", value, "jumtUrl");
            return (Criteria) this;
        }

        public Criteria andJumtUrlNotEqualTo(String value) {
            addCriterion("jumt_url <>", value, "jumtUrl");
            return (Criteria) this;
        }

        public Criteria andJumtUrlGreaterThan(String value) {
            addCriterion("jumt_url >", value, "jumtUrl");
            return (Criteria) this;
        }

        public Criteria andJumtUrlGreaterThanOrEqualTo(String value) {
            addCriterion("jumt_url >=", value, "jumtUrl");
            return (Criteria) this;
        }

        public Criteria andJumtUrlLessThan(String value) {
            addCriterion("jumt_url <", value, "jumtUrl");
            return (Criteria) this;
        }

        public Criteria andJumtUrlLessThanOrEqualTo(String value) {
            addCriterion("jumt_url <=", value, "jumtUrl");
            return (Criteria) this;
        }

        public Criteria andJumtUrlLike(String value) {
            addCriterion("jumt_url like", value, "jumtUrl");
            return (Criteria) this;
        }

        public Criteria andJumtUrlNotLike(String value) {
            addCriterion("jumt_url not like", value, "jumtUrl");
            return (Criteria) this;
        }

        public Criteria andJumtUrlIn(List<String> values) {
            addCriterion("jumt_url in", values, "jumtUrl");
            return (Criteria) this;
        }

        public Criteria andJumtUrlNotIn(List<String> values) {
            addCriterion("jumt_url not in", values, "jumtUrl");
            return (Criteria) this;
        }

        public Criteria andJumtUrlBetween(String value1, String value2) {
            addCriterion("jumt_url between", value1, value2, "jumtUrl");
            return (Criteria) this;
        }

        public Criteria andJumtUrlNotBetween(String value1, String value2) {
            addCriterion("jumt_url not between", value1, value2, "jumtUrl");
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