package com.hyjf.am.config.dao.model.auto;

import java.util.ArrayList;
import java.util.List;

public class MessagePushTemplateExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public MessagePushTemplateExample() {
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

        public Criteria andTagIdIsNull() {
            addCriterion("tag_id is null");
            return (Criteria) this;
        }

        public Criteria andTagIdIsNotNull() {
            addCriterion("tag_id is not null");
            return (Criteria) this;
        }

        public Criteria andTagIdEqualTo(Integer value) {
            addCriterion("tag_id =", value, "tagId");
            return (Criteria) this;
        }

        public Criteria andTagIdNotEqualTo(Integer value) {
            addCriterion("tag_id <>", value, "tagId");
            return (Criteria) this;
        }

        public Criteria andTagIdGreaterThan(Integer value) {
            addCriterion("tag_id >", value, "tagId");
            return (Criteria) this;
        }

        public Criteria andTagIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("tag_id >=", value, "tagId");
            return (Criteria) this;
        }

        public Criteria andTagIdLessThan(Integer value) {
            addCriterion("tag_id <", value, "tagId");
            return (Criteria) this;
        }

        public Criteria andTagIdLessThanOrEqualTo(Integer value) {
            addCriterion("tag_id <=", value, "tagId");
            return (Criteria) this;
        }

        public Criteria andTagIdIn(List<Integer> values) {
            addCriterion("tag_id in", values, "tagId");
            return (Criteria) this;
        }

        public Criteria andTagIdNotIn(List<Integer> values) {
            addCriterion("tag_id not in", values, "tagId");
            return (Criteria) this;
        }

        public Criteria andTagIdBetween(Integer value1, Integer value2) {
            addCriterion("tag_id between", value1, value2, "tagId");
            return (Criteria) this;
        }

        public Criteria andTagIdNotBetween(Integer value1, Integer value2) {
            addCriterion("tag_id not between", value1, value2, "tagId");
            return (Criteria) this;
        }

        public Criteria andTagCodeIsNull() {
            addCriterion("tag_code is null");
            return (Criteria) this;
        }

        public Criteria andTagCodeIsNotNull() {
            addCriterion("tag_code is not null");
            return (Criteria) this;
        }

        public Criteria andTagCodeEqualTo(String value) {
            addCriterion("tag_code =", value, "tagCode");
            return (Criteria) this;
        }

        public Criteria andTagCodeNotEqualTo(String value) {
            addCriterion("tag_code <>", value, "tagCode");
            return (Criteria) this;
        }

        public Criteria andTagCodeGreaterThan(String value) {
            addCriterion("tag_code >", value, "tagCode");
            return (Criteria) this;
        }

        public Criteria andTagCodeGreaterThanOrEqualTo(String value) {
            addCriterion("tag_code >=", value, "tagCode");
            return (Criteria) this;
        }

        public Criteria andTagCodeLessThan(String value) {
            addCriterion("tag_code <", value, "tagCode");
            return (Criteria) this;
        }

        public Criteria andTagCodeLessThanOrEqualTo(String value) {
            addCriterion("tag_code <=", value, "tagCode");
            return (Criteria) this;
        }

        public Criteria andTagCodeLike(String value) {
            addCriterion("tag_code like", value, "tagCode");
            return (Criteria) this;
        }

        public Criteria andTagCodeNotLike(String value) {
            addCriterion("tag_code not like", value, "tagCode");
            return (Criteria) this;
        }

        public Criteria andTagCodeIn(List<String> values) {
            addCriterion("tag_code in", values, "tagCode");
            return (Criteria) this;
        }

        public Criteria andTagCodeNotIn(List<String> values) {
            addCriterion("tag_code not in", values, "tagCode");
            return (Criteria) this;
        }

        public Criteria andTagCodeBetween(String value1, String value2) {
            addCriterion("tag_code between", value1, value2, "tagCode");
            return (Criteria) this;
        }

        public Criteria andTagCodeNotBetween(String value1, String value2) {
            addCriterion("tag_code not between", value1, value2, "tagCode");
            return (Criteria) this;
        }

        public Criteria andTemplateCodeIsNull() {
            addCriterion("template_code is null");
            return (Criteria) this;
        }

        public Criteria andTemplateCodeIsNotNull() {
            addCriterion("template_code is not null");
            return (Criteria) this;
        }

        public Criteria andTemplateCodeEqualTo(String value) {
            addCriterion("template_code =", value, "templateCode");
            return (Criteria) this;
        }

        public Criteria andTemplateCodeNotEqualTo(String value) {
            addCriterion("template_code <>", value, "templateCode");
            return (Criteria) this;
        }

        public Criteria andTemplateCodeGreaterThan(String value) {
            addCriterion("template_code >", value, "templateCode");
            return (Criteria) this;
        }

        public Criteria andTemplateCodeGreaterThanOrEqualTo(String value) {
            addCriterion("template_code >=", value, "templateCode");
            return (Criteria) this;
        }

        public Criteria andTemplateCodeLessThan(String value) {
            addCriterion("template_code <", value, "templateCode");
            return (Criteria) this;
        }

        public Criteria andTemplateCodeLessThanOrEqualTo(String value) {
            addCriterion("template_code <=", value, "templateCode");
            return (Criteria) this;
        }

        public Criteria andTemplateCodeLike(String value) {
            addCriterion("template_code like", value, "templateCode");
            return (Criteria) this;
        }

        public Criteria andTemplateCodeNotLike(String value) {
            addCriterion("template_code not like", value, "templateCode");
            return (Criteria) this;
        }

        public Criteria andTemplateCodeIn(List<String> values) {
            addCriterion("template_code in", values, "templateCode");
            return (Criteria) this;
        }

        public Criteria andTemplateCodeNotIn(List<String> values) {
            addCriterion("template_code not in", values, "templateCode");
            return (Criteria) this;
        }

        public Criteria andTemplateCodeBetween(String value1, String value2) {
            addCriterion("template_code between", value1, value2, "templateCode");
            return (Criteria) this;
        }

        public Criteria andTemplateCodeNotBetween(String value1, String value2) {
            addCriterion("template_code not between", value1, value2, "templateCode");
            return (Criteria) this;
        }

        public Criteria andTemplateTitleIsNull() {
            addCriterion("template_title is null");
            return (Criteria) this;
        }

        public Criteria andTemplateTitleIsNotNull() {
            addCriterion("template_title is not null");
            return (Criteria) this;
        }

        public Criteria andTemplateTitleEqualTo(String value) {
            addCriterion("template_title =", value, "templateTitle");
            return (Criteria) this;
        }

        public Criteria andTemplateTitleNotEqualTo(String value) {
            addCriterion("template_title <>", value, "templateTitle");
            return (Criteria) this;
        }

        public Criteria andTemplateTitleGreaterThan(String value) {
            addCriterion("template_title >", value, "templateTitle");
            return (Criteria) this;
        }

        public Criteria andTemplateTitleGreaterThanOrEqualTo(String value) {
            addCriterion("template_title >=", value, "templateTitle");
            return (Criteria) this;
        }

        public Criteria andTemplateTitleLessThan(String value) {
            addCriterion("template_title <", value, "templateTitle");
            return (Criteria) this;
        }

        public Criteria andTemplateTitleLessThanOrEqualTo(String value) {
            addCriterion("template_title <=", value, "templateTitle");
            return (Criteria) this;
        }

        public Criteria andTemplateTitleLike(String value) {
            addCriterion("template_title like", value, "templateTitle");
            return (Criteria) this;
        }

        public Criteria andTemplateTitleNotLike(String value) {
            addCriterion("template_title not like", value, "templateTitle");
            return (Criteria) this;
        }

        public Criteria andTemplateTitleIn(List<String> values) {
            addCriterion("template_title in", values, "templateTitle");
            return (Criteria) this;
        }

        public Criteria andTemplateTitleNotIn(List<String> values) {
            addCriterion("template_title not in", values, "templateTitle");
            return (Criteria) this;
        }

        public Criteria andTemplateTitleBetween(String value1, String value2) {
            addCriterion("template_title between", value1, value2, "templateTitle");
            return (Criteria) this;
        }

        public Criteria andTemplateTitleNotBetween(String value1, String value2) {
            addCriterion("template_title not between", value1, value2, "templateTitle");
            return (Criteria) this;
        }

        public Criteria andTemplateImageUrlIsNull() {
            addCriterion("template_image_url is null");
            return (Criteria) this;
        }

        public Criteria andTemplateImageUrlIsNotNull() {
            addCriterion("template_image_url is not null");
            return (Criteria) this;
        }

        public Criteria andTemplateImageUrlEqualTo(String value) {
            addCriterion("template_image_url =", value, "templateImageUrl");
            return (Criteria) this;
        }

        public Criteria andTemplateImageUrlNotEqualTo(String value) {
            addCriterion("template_image_url <>", value, "templateImageUrl");
            return (Criteria) this;
        }

        public Criteria andTemplateImageUrlGreaterThan(String value) {
            addCriterion("template_image_url >", value, "templateImageUrl");
            return (Criteria) this;
        }

        public Criteria andTemplateImageUrlGreaterThanOrEqualTo(String value) {
            addCriterion("template_image_url >=", value, "templateImageUrl");
            return (Criteria) this;
        }

        public Criteria andTemplateImageUrlLessThan(String value) {
            addCriterion("template_image_url <", value, "templateImageUrl");
            return (Criteria) this;
        }

        public Criteria andTemplateImageUrlLessThanOrEqualTo(String value) {
            addCriterion("template_image_url <=", value, "templateImageUrl");
            return (Criteria) this;
        }

        public Criteria andTemplateImageUrlLike(String value) {
            addCriterion("template_image_url like", value, "templateImageUrl");
            return (Criteria) this;
        }

        public Criteria andTemplateImageUrlNotLike(String value) {
            addCriterion("template_image_url not like", value, "templateImageUrl");
            return (Criteria) this;
        }

        public Criteria andTemplateImageUrlIn(List<String> values) {
            addCriterion("template_image_url in", values, "templateImageUrl");
            return (Criteria) this;
        }

        public Criteria andTemplateImageUrlNotIn(List<String> values) {
            addCriterion("template_image_url not in", values, "templateImageUrl");
            return (Criteria) this;
        }

        public Criteria andTemplateImageUrlBetween(String value1, String value2) {
            addCriterion("template_image_url between", value1, value2, "templateImageUrl");
            return (Criteria) this;
        }

        public Criteria andTemplateImageUrlNotBetween(String value1, String value2) {
            addCriterion("template_image_url not between", value1, value2, "templateImageUrl");
            return (Criteria) this;
        }

        public Criteria andTemplateContentIsNull() {
            addCriterion("template_content is null");
            return (Criteria) this;
        }

        public Criteria andTemplateContentIsNotNull() {
            addCriterion("template_content is not null");
            return (Criteria) this;
        }

        public Criteria andTemplateContentEqualTo(String value) {
            addCriterion("template_content =", value, "templateContent");
            return (Criteria) this;
        }

        public Criteria andTemplateContentNotEqualTo(String value) {
            addCriterion("template_content <>", value, "templateContent");
            return (Criteria) this;
        }

        public Criteria andTemplateContentGreaterThan(String value) {
            addCriterion("template_content >", value, "templateContent");
            return (Criteria) this;
        }

        public Criteria andTemplateContentGreaterThanOrEqualTo(String value) {
            addCriterion("template_content >=", value, "templateContent");
            return (Criteria) this;
        }

        public Criteria andTemplateContentLessThan(String value) {
            addCriterion("template_content <", value, "templateContent");
            return (Criteria) this;
        }

        public Criteria andTemplateContentLessThanOrEqualTo(String value) {
            addCriterion("template_content <=", value, "templateContent");
            return (Criteria) this;
        }

        public Criteria andTemplateContentLike(String value) {
            addCriterion("template_content like", value, "templateContent");
            return (Criteria) this;
        }

        public Criteria andTemplateContentNotLike(String value) {
            addCriterion("template_content not like", value, "templateContent");
            return (Criteria) this;
        }

        public Criteria andTemplateContentIn(List<String> values) {
            addCriterion("template_content in", values, "templateContent");
            return (Criteria) this;
        }

        public Criteria andTemplateContentNotIn(List<String> values) {
            addCriterion("template_content not in", values, "templateContent");
            return (Criteria) this;
        }

        public Criteria andTemplateContentBetween(String value1, String value2) {
            addCriterion("template_content between", value1, value2, "templateContent");
            return (Criteria) this;
        }

        public Criteria andTemplateContentNotBetween(String value1, String value2) {
            addCriterion("template_content not between", value1, value2, "templateContent");
            return (Criteria) this;
        }

        public Criteria andTemplateTerminalIsNull() {
            addCriterion("template_terminal is null");
            return (Criteria) this;
        }

        public Criteria andTemplateTerminalIsNotNull() {
            addCriterion("template_terminal is not null");
            return (Criteria) this;
        }

        public Criteria andTemplateTerminalEqualTo(String value) {
            addCriterion("template_terminal =", value, "templateTerminal");
            return (Criteria) this;
        }

        public Criteria andTemplateTerminalNotEqualTo(String value) {
            addCriterion("template_terminal <>", value, "templateTerminal");
            return (Criteria) this;
        }

        public Criteria andTemplateTerminalGreaterThan(String value) {
            addCriterion("template_terminal >", value, "templateTerminal");
            return (Criteria) this;
        }

        public Criteria andTemplateTerminalGreaterThanOrEqualTo(String value) {
            addCriterion("template_terminal >=", value, "templateTerminal");
            return (Criteria) this;
        }

        public Criteria andTemplateTerminalLessThan(String value) {
            addCriterion("template_terminal <", value, "templateTerminal");
            return (Criteria) this;
        }

        public Criteria andTemplateTerminalLessThanOrEqualTo(String value) {
            addCriterion("template_terminal <=", value, "templateTerminal");
            return (Criteria) this;
        }

        public Criteria andTemplateTerminalLike(String value) {
            addCriterion("template_terminal like", value, "templateTerminal");
            return (Criteria) this;
        }

        public Criteria andTemplateTerminalNotLike(String value) {
            addCriterion("template_terminal not like", value, "templateTerminal");
            return (Criteria) this;
        }

        public Criteria andTemplateTerminalIn(List<String> values) {
            addCriterion("template_terminal in", values, "templateTerminal");
            return (Criteria) this;
        }

        public Criteria andTemplateTerminalNotIn(List<String> values) {
            addCriterion("template_terminal not in", values, "templateTerminal");
            return (Criteria) this;
        }

        public Criteria andTemplateTerminalBetween(String value1, String value2) {
            addCriterion("template_terminal between", value1, value2, "templateTerminal");
            return (Criteria) this;
        }

        public Criteria andTemplateTerminalNotBetween(String value1, String value2) {
            addCriterion("template_terminal not between", value1, value2, "templateTerminal");
            return (Criteria) this;
        }

        public Criteria andTemplateActionIsNull() {
            addCriterion("template_action is null");
            return (Criteria) this;
        }

        public Criteria andTemplateActionIsNotNull() {
            addCriterion("template_action is not null");
            return (Criteria) this;
        }

        public Criteria andTemplateActionEqualTo(Boolean value) {
            addCriterion("template_action =", value, "templateAction");
            return (Criteria) this;
        }

        public Criteria andTemplateActionNotEqualTo(Boolean value) {
            addCriterion("template_action <>", value, "templateAction");
            return (Criteria) this;
        }

        public Criteria andTemplateActionGreaterThan(Boolean value) {
            addCriterion("template_action >", value, "templateAction");
            return (Criteria) this;
        }

        public Criteria andTemplateActionGreaterThanOrEqualTo(Boolean value) {
            addCriterion("template_action >=", value, "templateAction");
            return (Criteria) this;
        }

        public Criteria andTemplateActionLessThan(Boolean value) {
            addCriterion("template_action <", value, "templateAction");
            return (Criteria) this;
        }

        public Criteria andTemplateActionLessThanOrEqualTo(Boolean value) {
            addCriterion("template_action <=", value, "templateAction");
            return (Criteria) this;
        }

        public Criteria andTemplateActionIn(List<Boolean> values) {
            addCriterion("template_action in", values, "templateAction");
            return (Criteria) this;
        }

        public Criteria andTemplateActionNotIn(List<Boolean> values) {
            addCriterion("template_action not in", values, "templateAction");
            return (Criteria) this;
        }

        public Criteria andTemplateActionBetween(Boolean value1, Boolean value2) {
            addCriterion("template_action between", value1, value2, "templateAction");
            return (Criteria) this;
        }

        public Criteria andTemplateActionNotBetween(Boolean value1, Boolean value2) {
            addCriterion("template_action not between", value1, value2, "templateAction");
            return (Criteria) this;
        }

        public Criteria andTemplateActionUrlIsNull() {
            addCriterion("template_action_url is null");
            return (Criteria) this;
        }

        public Criteria andTemplateActionUrlIsNotNull() {
            addCriterion("template_action_url is not null");
            return (Criteria) this;
        }

        public Criteria andTemplateActionUrlEqualTo(String value) {
            addCriterion("template_action_url =", value, "templateActionUrl");
            return (Criteria) this;
        }

        public Criteria andTemplateActionUrlNotEqualTo(String value) {
            addCriterion("template_action_url <>", value, "templateActionUrl");
            return (Criteria) this;
        }

        public Criteria andTemplateActionUrlGreaterThan(String value) {
            addCriterion("template_action_url >", value, "templateActionUrl");
            return (Criteria) this;
        }

        public Criteria andTemplateActionUrlGreaterThanOrEqualTo(String value) {
            addCriterion("template_action_url >=", value, "templateActionUrl");
            return (Criteria) this;
        }

        public Criteria andTemplateActionUrlLessThan(String value) {
            addCriterion("template_action_url <", value, "templateActionUrl");
            return (Criteria) this;
        }

        public Criteria andTemplateActionUrlLessThanOrEqualTo(String value) {
            addCriterion("template_action_url <=", value, "templateActionUrl");
            return (Criteria) this;
        }

        public Criteria andTemplateActionUrlLike(String value) {
            addCriterion("template_action_url like", value, "templateActionUrl");
            return (Criteria) this;
        }

        public Criteria andTemplateActionUrlNotLike(String value) {
            addCriterion("template_action_url not like", value, "templateActionUrl");
            return (Criteria) this;
        }

        public Criteria andTemplateActionUrlIn(List<String> values) {
            addCriterion("template_action_url in", values, "templateActionUrl");
            return (Criteria) this;
        }

        public Criteria andTemplateActionUrlNotIn(List<String> values) {
            addCriterion("template_action_url not in", values, "templateActionUrl");
            return (Criteria) this;
        }

        public Criteria andTemplateActionUrlBetween(String value1, String value2) {
            addCriterion("template_action_url between", value1, value2, "templateActionUrl");
            return (Criteria) this;
        }

        public Criteria andTemplateActionUrlNotBetween(String value1, String value2) {
            addCriterion("template_action_url not between", value1, value2, "templateActionUrl");
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

        public Criteria andStatusEqualTo(Boolean value) {
            addCriterion("`status` =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Boolean value) {
            addCriterion("`status` <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Boolean value) {
            addCriterion("`status` >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Boolean value) {
            addCriterion("`status` >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Boolean value) {
            addCriterion("`status` <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Boolean value) {
            addCriterion("`status` <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Boolean> values) {
            addCriterion("`status` in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Boolean> values) {
            addCriterion("`status` not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Boolean value1, Boolean value2) {
            addCriterion("`status` between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Boolean value1, Boolean value2) {
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

        public Criteria andCreateTimeEqualTo(Integer value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Integer value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Integer value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Integer value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Integer value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Integer> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Integer> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Integer value1, Integer value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Integer value1, Integer value2) {
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

        public Criteria andCreateUserNameIsNull() {
            addCriterion("create_user_name is null");
            return (Criteria) this;
        }

        public Criteria andCreateUserNameIsNotNull() {
            addCriterion("create_user_name is not null");
            return (Criteria) this;
        }

        public Criteria andCreateUserNameEqualTo(String value) {
            addCriterion("create_user_name =", value, "createUserName");
            return (Criteria) this;
        }

        public Criteria andCreateUserNameNotEqualTo(String value) {
            addCriterion("create_user_name <>", value, "createUserName");
            return (Criteria) this;
        }

        public Criteria andCreateUserNameGreaterThan(String value) {
            addCriterion("create_user_name >", value, "createUserName");
            return (Criteria) this;
        }

        public Criteria andCreateUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("create_user_name >=", value, "createUserName");
            return (Criteria) this;
        }

        public Criteria andCreateUserNameLessThan(String value) {
            addCriterion("create_user_name <", value, "createUserName");
            return (Criteria) this;
        }

        public Criteria andCreateUserNameLessThanOrEqualTo(String value) {
            addCriterion("create_user_name <=", value, "createUserName");
            return (Criteria) this;
        }

        public Criteria andCreateUserNameLike(String value) {
            addCriterion("create_user_name like", value, "createUserName");
            return (Criteria) this;
        }

        public Criteria andCreateUserNameNotLike(String value) {
            addCriterion("create_user_name not like", value, "createUserName");
            return (Criteria) this;
        }

        public Criteria andCreateUserNameIn(List<String> values) {
            addCriterion("create_user_name in", values, "createUserName");
            return (Criteria) this;
        }

        public Criteria andCreateUserNameNotIn(List<String> values) {
            addCriterion("create_user_name not in", values, "createUserName");
            return (Criteria) this;
        }

        public Criteria andCreateUserNameBetween(String value1, String value2) {
            addCriterion("create_user_name between", value1, value2, "createUserName");
            return (Criteria) this;
        }

        public Criteria andCreateUserNameNotBetween(String value1, String value2) {
            addCriterion("create_user_name not between", value1, value2, "createUserName");
            return (Criteria) this;
        }

        public Criteria andLastupdateTimeIsNull() {
            addCriterion("lastupdate_time is null");
            return (Criteria) this;
        }

        public Criteria andLastupdateTimeIsNotNull() {
            addCriterion("lastupdate_time is not null");
            return (Criteria) this;
        }

        public Criteria andLastupdateTimeEqualTo(Integer value) {
            addCriterion("lastupdate_time =", value, "lastupdateTime");
            return (Criteria) this;
        }

        public Criteria andLastupdateTimeNotEqualTo(Integer value) {
            addCriterion("lastupdate_time <>", value, "lastupdateTime");
            return (Criteria) this;
        }

        public Criteria andLastupdateTimeGreaterThan(Integer value) {
            addCriterion("lastupdate_time >", value, "lastupdateTime");
            return (Criteria) this;
        }

        public Criteria andLastupdateTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("lastupdate_time >=", value, "lastupdateTime");
            return (Criteria) this;
        }

        public Criteria andLastupdateTimeLessThan(Integer value) {
            addCriterion("lastupdate_time <", value, "lastupdateTime");
            return (Criteria) this;
        }

        public Criteria andLastupdateTimeLessThanOrEqualTo(Integer value) {
            addCriterion("lastupdate_time <=", value, "lastupdateTime");
            return (Criteria) this;
        }

        public Criteria andLastupdateTimeIn(List<Integer> values) {
            addCriterion("lastupdate_time in", values, "lastupdateTime");
            return (Criteria) this;
        }

        public Criteria andLastupdateTimeNotIn(List<Integer> values) {
            addCriterion("lastupdate_time not in", values, "lastupdateTime");
            return (Criteria) this;
        }

        public Criteria andLastupdateTimeBetween(Integer value1, Integer value2) {
            addCriterion("lastupdate_time between", value1, value2, "lastupdateTime");
            return (Criteria) this;
        }

        public Criteria andLastupdateTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("lastupdate_time not between", value1, value2, "lastupdateTime");
            return (Criteria) this;
        }

        public Criteria andLastupdateUserIdIsNull() {
            addCriterion("lastupdate_user_id is null");
            return (Criteria) this;
        }

        public Criteria andLastupdateUserIdIsNotNull() {
            addCriterion("lastupdate_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andLastupdateUserIdEqualTo(Integer value) {
            addCriterion("lastupdate_user_id =", value, "lastupdateUserId");
            return (Criteria) this;
        }

        public Criteria andLastupdateUserIdNotEqualTo(Integer value) {
            addCriterion("lastupdate_user_id <>", value, "lastupdateUserId");
            return (Criteria) this;
        }

        public Criteria andLastupdateUserIdGreaterThan(Integer value) {
            addCriterion("lastupdate_user_id >", value, "lastupdateUserId");
            return (Criteria) this;
        }

        public Criteria andLastupdateUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("lastupdate_user_id >=", value, "lastupdateUserId");
            return (Criteria) this;
        }

        public Criteria andLastupdateUserIdLessThan(Integer value) {
            addCriterion("lastupdate_user_id <", value, "lastupdateUserId");
            return (Criteria) this;
        }

        public Criteria andLastupdateUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("lastupdate_user_id <=", value, "lastupdateUserId");
            return (Criteria) this;
        }

        public Criteria andLastupdateUserIdIn(List<Integer> values) {
            addCriterion("lastupdate_user_id in", values, "lastupdateUserId");
            return (Criteria) this;
        }

        public Criteria andLastupdateUserIdNotIn(List<Integer> values) {
            addCriterion("lastupdate_user_id not in", values, "lastupdateUserId");
            return (Criteria) this;
        }

        public Criteria andLastupdateUserIdBetween(Integer value1, Integer value2) {
            addCriterion("lastupdate_user_id between", value1, value2, "lastupdateUserId");
            return (Criteria) this;
        }

        public Criteria andLastupdateUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("lastupdate_user_id not between", value1, value2, "lastupdateUserId");
            return (Criteria) this;
        }

        public Criteria andLastupdateUserNameIsNull() {
            addCriterion("lastupdate_user_name is null");
            return (Criteria) this;
        }

        public Criteria andLastupdateUserNameIsNotNull() {
            addCriterion("lastupdate_user_name is not null");
            return (Criteria) this;
        }

        public Criteria andLastupdateUserNameEqualTo(String value) {
            addCriterion("lastupdate_user_name =", value, "lastupdateUserName");
            return (Criteria) this;
        }

        public Criteria andLastupdateUserNameNotEqualTo(String value) {
            addCriterion("lastupdate_user_name <>", value, "lastupdateUserName");
            return (Criteria) this;
        }

        public Criteria andLastupdateUserNameGreaterThan(String value) {
            addCriterion("lastupdate_user_name >", value, "lastupdateUserName");
            return (Criteria) this;
        }

        public Criteria andLastupdateUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("lastupdate_user_name >=", value, "lastupdateUserName");
            return (Criteria) this;
        }

        public Criteria andLastupdateUserNameLessThan(String value) {
            addCriterion("lastupdate_user_name <", value, "lastupdateUserName");
            return (Criteria) this;
        }

        public Criteria andLastupdateUserNameLessThanOrEqualTo(String value) {
            addCriterion("lastupdate_user_name <=", value, "lastupdateUserName");
            return (Criteria) this;
        }

        public Criteria andLastupdateUserNameLike(String value) {
            addCriterion("lastupdate_user_name like", value, "lastupdateUserName");
            return (Criteria) this;
        }

        public Criteria andLastupdateUserNameNotLike(String value) {
            addCriterion("lastupdate_user_name not like", value, "lastupdateUserName");
            return (Criteria) this;
        }

        public Criteria andLastupdateUserNameIn(List<String> values) {
            addCriterion("lastupdate_user_name in", values, "lastupdateUserName");
            return (Criteria) this;
        }

        public Criteria andLastupdateUserNameNotIn(List<String> values) {
            addCriterion("lastupdate_user_name not in", values, "lastupdateUserName");
            return (Criteria) this;
        }

        public Criteria andLastupdateUserNameBetween(String value1, String value2) {
            addCriterion("lastupdate_user_name between", value1, value2, "lastupdateUserName");
            return (Criteria) this;
        }

        public Criteria andLastupdateUserNameNotBetween(String value1, String value2) {
            addCriterion("lastupdate_user_name not between", value1, value2, "lastupdateUserName");
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