package com.hyjf.am.message.dao.model.auto;

import java.util.ArrayList;
import java.util.List;

public class MessagePushMsgExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public MessagePushMsgExample() {
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

        public Criteria andMsgCodeIsNull() {
            addCriterion("msg_code is null");
            return (Criteria) this;
        }

        public Criteria andMsgCodeIsNotNull() {
            addCriterion("msg_code is not null");
            return (Criteria) this;
        }

        public Criteria andMsgCodeEqualTo(String value) {
            addCriterion("msg_code =", value, "msgCode");
            return (Criteria) this;
        }

        public Criteria andMsgCodeNotEqualTo(String value) {
            addCriterion("msg_code <>", value, "msgCode");
            return (Criteria) this;
        }

        public Criteria andMsgCodeGreaterThan(String value) {
            addCriterion("msg_code >", value, "msgCode");
            return (Criteria) this;
        }

        public Criteria andMsgCodeGreaterThanOrEqualTo(String value) {
            addCriterion("msg_code >=", value, "msgCode");
            return (Criteria) this;
        }

        public Criteria andMsgCodeLessThan(String value) {
            addCriterion("msg_code <", value, "msgCode");
            return (Criteria) this;
        }

        public Criteria andMsgCodeLessThanOrEqualTo(String value) {
            addCriterion("msg_code <=", value, "msgCode");
            return (Criteria) this;
        }

        public Criteria andMsgCodeLike(String value) {
            addCriterion("msg_code like", value, "msgCode");
            return (Criteria) this;
        }

        public Criteria andMsgCodeNotLike(String value) {
            addCriterion("msg_code not like", value, "msgCode");
            return (Criteria) this;
        }

        public Criteria andMsgCodeIn(List<String> values) {
            addCriterion("msg_code in", values, "msgCode");
            return (Criteria) this;
        }

        public Criteria andMsgCodeNotIn(List<String> values) {
            addCriterion("msg_code not in", values, "msgCode");
            return (Criteria) this;
        }

        public Criteria andMsgCodeBetween(String value1, String value2) {
            addCriterion("msg_code between", value1, value2, "msgCode");
            return (Criteria) this;
        }

        public Criteria andMsgCodeNotBetween(String value1, String value2) {
            addCriterion("msg_code not between", value1, value2, "msgCode");
            return (Criteria) this;
        }

        public Criteria andMsgTitleIsNull() {
            addCriterion("msg_title is null");
            return (Criteria) this;
        }

        public Criteria andMsgTitleIsNotNull() {
            addCriterion("msg_title is not null");
            return (Criteria) this;
        }

        public Criteria andMsgTitleEqualTo(String value) {
            addCriterion("msg_title =", value, "msgTitle");
            return (Criteria) this;
        }

        public Criteria andMsgTitleNotEqualTo(String value) {
            addCriterion("msg_title <>", value, "msgTitle");
            return (Criteria) this;
        }

        public Criteria andMsgTitleGreaterThan(String value) {
            addCriterion("msg_title >", value, "msgTitle");
            return (Criteria) this;
        }

        public Criteria andMsgTitleGreaterThanOrEqualTo(String value) {
            addCriterion("msg_title >=", value, "msgTitle");
            return (Criteria) this;
        }

        public Criteria andMsgTitleLessThan(String value) {
            addCriterion("msg_title <", value, "msgTitle");
            return (Criteria) this;
        }

        public Criteria andMsgTitleLessThanOrEqualTo(String value) {
            addCriterion("msg_title <=", value, "msgTitle");
            return (Criteria) this;
        }

        public Criteria andMsgTitleLike(String value) {
            addCriterion("msg_title like", value, "msgTitle");
            return (Criteria) this;
        }

        public Criteria andMsgTitleNotLike(String value) {
            addCriterion("msg_title not like", value, "msgTitle");
            return (Criteria) this;
        }

        public Criteria andMsgTitleIn(List<String> values) {
            addCriterion("msg_title in", values, "msgTitle");
            return (Criteria) this;
        }

        public Criteria andMsgTitleNotIn(List<String> values) {
            addCriterion("msg_title not in", values, "msgTitle");
            return (Criteria) this;
        }

        public Criteria andMsgTitleBetween(String value1, String value2) {
            addCriterion("msg_title between", value1, value2, "msgTitle");
            return (Criteria) this;
        }

        public Criteria andMsgTitleNotBetween(String value1, String value2) {
            addCriterion("msg_title not between", value1, value2, "msgTitle");
            return (Criteria) this;
        }

        public Criteria andMsgImageUrlIsNull() {
            addCriterion("msg_image_url is null");
            return (Criteria) this;
        }

        public Criteria andMsgImageUrlIsNotNull() {
            addCriterion("msg_image_url is not null");
            return (Criteria) this;
        }

        public Criteria andMsgImageUrlEqualTo(String value) {
            addCriterion("msg_image_url =", value, "msgImageUrl");
            return (Criteria) this;
        }

        public Criteria andMsgImageUrlNotEqualTo(String value) {
            addCriterion("msg_image_url <>", value, "msgImageUrl");
            return (Criteria) this;
        }

        public Criteria andMsgImageUrlGreaterThan(String value) {
            addCriterion("msg_image_url >", value, "msgImageUrl");
            return (Criteria) this;
        }

        public Criteria andMsgImageUrlGreaterThanOrEqualTo(String value) {
            addCriterion("msg_image_url >=", value, "msgImageUrl");
            return (Criteria) this;
        }

        public Criteria andMsgImageUrlLessThan(String value) {
            addCriterion("msg_image_url <", value, "msgImageUrl");
            return (Criteria) this;
        }

        public Criteria andMsgImageUrlLessThanOrEqualTo(String value) {
            addCriterion("msg_image_url <=", value, "msgImageUrl");
            return (Criteria) this;
        }

        public Criteria andMsgImageUrlLike(String value) {
            addCriterion("msg_image_url like", value, "msgImageUrl");
            return (Criteria) this;
        }

        public Criteria andMsgImageUrlNotLike(String value) {
            addCriterion("msg_image_url not like", value, "msgImageUrl");
            return (Criteria) this;
        }

        public Criteria andMsgImageUrlIn(List<String> values) {
            addCriterion("msg_image_url in", values, "msgImageUrl");
            return (Criteria) this;
        }

        public Criteria andMsgImageUrlNotIn(List<String> values) {
            addCriterion("msg_image_url not in", values, "msgImageUrl");
            return (Criteria) this;
        }

        public Criteria andMsgImageUrlBetween(String value1, String value2) {
            addCriterion("msg_image_url between", value1, value2, "msgImageUrl");
            return (Criteria) this;
        }

        public Criteria andMsgImageUrlNotBetween(String value1, String value2) {
            addCriterion("msg_image_url not between", value1, value2, "msgImageUrl");
            return (Criteria) this;
        }

        public Criteria andMsgContentIsNull() {
            addCriterion("msg_content is null");
            return (Criteria) this;
        }

        public Criteria andMsgContentIsNotNull() {
            addCriterion("msg_content is not null");
            return (Criteria) this;
        }

        public Criteria andMsgContentEqualTo(String value) {
            addCriterion("msg_content =", value, "msgContent");
            return (Criteria) this;
        }

        public Criteria andMsgContentNotEqualTo(String value) {
            addCriterion("msg_content <>", value, "msgContent");
            return (Criteria) this;
        }

        public Criteria andMsgContentGreaterThan(String value) {
            addCriterion("msg_content >", value, "msgContent");
            return (Criteria) this;
        }

        public Criteria andMsgContentGreaterThanOrEqualTo(String value) {
            addCriterion("msg_content >=", value, "msgContent");
            return (Criteria) this;
        }

        public Criteria andMsgContentLessThan(String value) {
            addCriterion("msg_content <", value, "msgContent");
            return (Criteria) this;
        }

        public Criteria andMsgContentLessThanOrEqualTo(String value) {
            addCriterion("msg_content <=", value, "msgContent");
            return (Criteria) this;
        }

        public Criteria andMsgContentLike(String value) {
            addCriterion("msg_content like", value, "msgContent");
            return (Criteria) this;
        }

        public Criteria andMsgContentNotLike(String value) {
            addCriterion("msg_content not like", value, "msgContent");
            return (Criteria) this;
        }

        public Criteria andMsgContentIn(List<String> values) {
            addCriterion("msg_content in", values, "msgContent");
            return (Criteria) this;
        }

        public Criteria andMsgContentNotIn(List<String> values) {
            addCriterion("msg_content not in", values, "msgContent");
            return (Criteria) this;
        }

        public Criteria andMsgContentBetween(String value1, String value2) {
            addCriterion("msg_content between", value1, value2, "msgContent");
            return (Criteria) this;
        }

        public Criteria andMsgContentNotBetween(String value1, String value2) {
            addCriterion("msg_content not between", value1, value2, "msgContent");
            return (Criteria) this;
        }

        public Criteria andMsgTerminalIsNull() {
            addCriterion("msg_terminal is null");
            return (Criteria) this;
        }

        public Criteria andMsgTerminalIsNotNull() {
            addCriterion("msg_terminal is not null");
            return (Criteria) this;
        }

        public Criteria andMsgTerminalEqualTo(String value) {
            addCriterion("msg_terminal =", value, "msgTerminal");
            return (Criteria) this;
        }

        public Criteria andMsgTerminalNotEqualTo(String value) {
            addCriterion("msg_terminal <>", value, "msgTerminal");
            return (Criteria) this;
        }

        public Criteria andMsgTerminalGreaterThan(String value) {
            addCriterion("msg_terminal >", value, "msgTerminal");
            return (Criteria) this;
        }

        public Criteria andMsgTerminalGreaterThanOrEqualTo(String value) {
            addCriterion("msg_terminal >=", value, "msgTerminal");
            return (Criteria) this;
        }

        public Criteria andMsgTerminalLessThan(String value) {
            addCriterion("msg_terminal <", value, "msgTerminal");
            return (Criteria) this;
        }

        public Criteria andMsgTerminalLessThanOrEqualTo(String value) {
            addCriterion("msg_terminal <=", value, "msgTerminal");
            return (Criteria) this;
        }

        public Criteria andMsgTerminalLike(String value) {
            addCriterion("msg_terminal like", value, "msgTerminal");
            return (Criteria) this;
        }

        public Criteria andMsgTerminalNotLike(String value) {
            addCriterion("msg_terminal not like", value, "msgTerminal");
            return (Criteria) this;
        }

        public Criteria andMsgTerminalIn(List<String> values) {
            addCriterion("msg_terminal in", values, "msgTerminal");
            return (Criteria) this;
        }

        public Criteria andMsgTerminalNotIn(List<String> values) {
            addCriterion("msg_terminal not in", values, "msgTerminal");
            return (Criteria) this;
        }

        public Criteria andMsgTerminalBetween(String value1, String value2) {
            addCriterion("msg_terminal between", value1, value2, "msgTerminal");
            return (Criteria) this;
        }

        public Criteria andMsgTerminalNotBetween(String value1, String value2) {
            addCriterion("msg_terminal not between", value1, value2, "msgTerminal");
            return (Criteria) this;
        }

        public Criteria andMsgActionIsNull() {
            addCriterion("msg_action is null");
            return (Criteria) this;
        }

        public Criteria andMsgActionIsNotNull() {
            addCriterion("msg_action is not null");
            return (Criteria) this;
        }

        public Criteria andMsgActionEqualTo(Integer value) {
            addCriterion("msg_action =", value, "msgAction");
            return (Criteria) this;
        }

        public Criteria andMsgActionNotEqualTo(Integer value) {
            addCriterion("msg_action <>", value, "msgAction");
            return (Criteria) this;
        }

        public Criteria andMsgActionGreaterThan(Integer value) {
            addCriterion("msg_action >", value, "msgAction");
            return (Criteria) this;
        }

        public Criteria andMsgActionGreaterThanOrEqualTo(Integer value) {
            addCriterion("msg_action >=", value, "msgAction");
            return (Criteria) this;
        }

        public Criteria andMsgActionLessThan(Integer value) {
            addCriterion("msg_action <", value, "msgAction");
            return (Criteria) this;
        }

        public Criteria andMsgActionLessThanOrEqualTo(Integer value) {
            addCriterion("msg_action <=", value, "msgAction");
            return (Criteria) this;
        }

        public Criteria andMsgActionIn(List<Integer> values) {
            addCriterion("msg_action in", values, "msgAction");
            return (Criteria) this;
        }

        public Criteria andMsgActionNotIn(List<Integer> values) {
            addCriterion("msg_action not in", values, "msgAction");
            return (Criteria) this;
        }

        public Criteria andMsgActionBetween(Integer value1, Integer value2) {
            addCriterion("msg_action between", value1, value2, "msgAction");
            return (Criteria) this;
        }

        public Criteria andMsgActionNotBetween(Integer value1, Integer value2) {
            addCriterion("msg_action not between", value1, value2, "msgAction");
            return (Criteria) this;
        }

        public Criteria andMsgActionUrlIsNull() {
            addCriterion("msg_action_url is null");
            return (Criteria) this;
        }

        public Criteria andMsgActionUrlIsNotNull() {
            addCriterion("msg_action_url is not null");
            return (Criteria) this;
        }

        public Criteria andMsgActionUrlEqualTo(String value) {
            addCriterion("msg_action_url =", value, "msgActionUrl");
            return (Criteria) this;
        }

        public Criteria andMsgActionUrlNotEqualTo(String value) {
            addCriterion("msg_action_url <>", value, "msgActionUrl");
            return (Criteria) this;
        }

        public Criteria andMsgActionUrlGreaterThan(String value) {
            addCriterion("msg_action_url >", value, "msgActionUrl");
            return (Criteria) this;
        }

        public Criteria andMsgActionUrlGreaterThanOrEqualTo(String value) {
            addCriterion("msg_action_url >=", value, "msgActionUrl");
            return (Criteria) this;
        }

        public Criteria andMsgActionUrlLessThan(String value) {
            addCriterion("msg_action_url <", value, "msgActionUrl");
            return (Criteria) this;
        }

        public Criteria andMsgActionUrlLessThanOrEqualTo(String value) {
            addCriterion("msg_action_url <=", value, "msgActionUrl");
            return (Criteria) this;
        }

        public Criteria andMsgActionUrlLike(String value) {
            addCriterion("msg_action_url like", value, "msgActionUrl");
            return (Criteria) this;
        }

        public Criteria andMsgActionUrlNotLike(String value) {
            addCriterion("msg_action_url not like", value, "msgActionUrl");
            return (Criteria) this;
        }

        public Criteria andMsgActionUrlIn(List<String> values) {
            addCriterion("msg_action_url in", values, "msgActionUrl");
            return (Criteria) this;
        }

        public Criteria andMsgActionUrlNotIn(List<String> values) {
            addCriterion("msg_action_url not in", values, "msgActionUrl");
            return (Criteria) this;
        }

        public Criteria andMsgActionUrlBetween(String value1, String value2) {
            addCriterion("msg_action_url between", value1, value2, "msgActionUrl");
            return (Criteria) this;
        }

        public Criteria andMsgActionUrlNotBetween(String value1, String value2) {
            addCriterion("msg_action_url not between", value1, value2, "msgActionUrl");
            return (Criteria) this;
        }

        public Criteria andMsgDestinationTypeIsNull() {
            addCriterion("msg_destination_type is null");
            return (Criteria) this;
        }

        public Criteria andMsgDestinationTypeIsNotNull() {
            addCriterion("msg_destination_type is not null");
            return (Criteria) this;
        }

        public Criteria andMsgDestinationTypeEqualTo(Integer value) {
            addCriterion("msg_destination_type =", value, "msgDestinationType");
            return (Criteria) this;
        }

        public Criteria andMsgDestinationTypeNotEqualTo(Integer value) {
            addCriterion("msg_destination_type <>", value, "msgDestinationType");
            return (Criteria) this;
        }

        public Criteria andMsgDestinationTypeGreaterThan(Integer value) {
            addCriterion("msg_destination_type >", value, "msgDestinationType");
            return (Criteria) this;
        }

        public Criteria andMsgDestinationTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("msg_destination_type >=", value, "msgDestinationType");
            return (Criteria) this;
        }

        public Criteria andMsgDestinationTypeLessThan(Integer value) {
            addCriterion("msg_destination_type <", value, "msgDestinationType");
            return (Criteria) this;
        }

        public Criteria andMsgDestinationTypeLessThanOrEqualTo(Integer value) {
            addCriterion("msg_destination_type <=", value, "msgDestinationType");
            return (Criteria) this;
        }

        public Criteria andMsgDestinationTypeIn(List<Integer> values) {
            addCriterion("msg_destination_type in", values, "msgDestinationType");
            return (Criteria) this;
        }

        public Criteria andMsgDestinationTypeNotIn(List<Integer> values) {
            addCriterion("msg_destination_type not in", values, "msgDestinationType");
            return (Criteria) this;
        }

        public Criteria andMsgDestinationTypeBetween(Integer value1, Integer value2) {
            addCriterion("msg_destination_type between", value1, value2, "msgDestinationType");
            return (Criteria) this;
        }

        public Criteria andMsgDestinationTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("msg_destination_type not between", value1, value2, "msgDestinationType");
            return (Criteria) this;
        }

        public Criteria andMsgSendStatusIsNull() {
            addCriterion("msg_send_status is null");
            return (Criteria) this;
        }

        public Criteria andMsgSendStatusIsNotNull() {
            addCriterion("msg_send_status is not null");
            return (Criteria) this;
        }

        public Criteria andMsgSendStatusEqualTo(Integer value) {
            addCriterion("msg_send_status =", value, "msgSendStatus");
            return (Criteria) this;
        }

        public Criteria andMsgSendStatusNotEqualTo(Integer value) {
            addCriterion("msg_send_status <>", value, "msgSendStatus");
            return (Criteria) this;
        }

        public Criteria andMsgSendStatusGreaterThan(Integer value) {
            addCriterion("msg_send_status >", value, "msgSendStatus");
            return (Criteria) this;
        }

        public Criteria andMsgSendStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("msg_send_status >=", value, "msgSendStatus");
            return (Criteria) this;
        }

        public Criteria andMsgSendStatusLessThan(Integer value) {
            addCriterion("msg_send_status <", value, "msgSendStatus");
            return (Criteria) this;
        }

        public Criteria andMsgSendStatusLessThanOrEqualTo(Integer value) {
            addCriterion("msg_send_status <=", value, "msgSendStatus");
            return (Criteria) this;
        }

        public Criteria andMsgSendStatusIn(List<Integer> values) {
            addCriterion("msg_send_status in", values, "msgSendStatus");
            return (Criteria) this;
        }

        public Criteria andMsgSendStatusNotIn(List<Integer> values) {
            addCriterion("msg_send_status not in", values, "msgSendStatus");
            return (Criteria) this;
        }

        public Criteria andMsgSendStatusBetween(Integer value1, Integer value2) {
            addCriterion("msg_send_status between", value1, value2, "msgSendStatus");
            return (Criteria) this;
        }

        public Criteria andMsgSendStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("msg_send_status not between", value1, value2, "msgSendStatus");
            return (Criteria) this;
        }

        public Criteria andMsgSendTypeIsNull() {
            addCriterion("msg_send_type is null");
            return (Criteria) this;
        }

        public Criteria andMsgSendTypeIsNotNull() {
            addCriterion("msg_send_type is not null");
            return (Criteria) this;
        }

        public Criteria andMsgSendTypeEqualTo(Integer value) {
            addCriterion("msg_send_type =", value, "msgSendType");
            return (Criteria) this;
        }

        public Criteria andMsgSendTypeNotEqualTo(Integer value) {
            addCriterion("msg_send_type <>", value, "msgSendType");
            return (Criteria) this;
        }

        public Criteria andMsgSendTypeGreaterThan(Integer value) {
            addCriterion("msg_send_type >", value, "msgSendType");
            return (Criteria) this;
        }

        public Criteria andMsgSendTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("msg_send_type >=", value, "msgSendType");
            return (Criteria) this;
        }

        public Criteria andMsgSendTypeLessThan(Integer value) {
            addCriterion("msg_send_type <", value, "msgSendType");
            return (Criteria) this;
        }

        public Criteria andMsgSendTypeLessThanOrEqualTo(Integer value) {
            addCriterion("msg_send_type <=", value, "msgSendType");
            return (Criteria) this;
        }

        public Criteria andMsgSendTypeIn(List<Integer> values) {
            addCriterion("msg_send_type in", values, "msgSendType");
            return (Criteria) this;
        }

        public Criteria andMsgSendTypeNotIn(List<Integer> values) {
            addCriterion("msg_send_type not in", values, "msgSendType");
            return (Criteria) this;
        }

        public Criteria andMsgSendTypeBetween(Integer value1, Integer value2) {
            addCriterion("msg_send_type between", value1, value2, "msgSendType");
            return (Criteria) this;
        }

        public Criteria andMsgSendTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("msg_send_type not between", value1, value2, "msgSendType");
            return (Criteria) this;
        }

        public Criteria andPreSendTimeIsNull() {
            addCriterion("pre_send_time is null");
            return (Criteria) this;
        }

        public Criteria andPreSendTimeIsNotNull() {
            addCriterion("pre_send_time is not null");
            return (Criteria) this;
        }

        public Criteria andPreSendTimeEqualTo(Integer value) {
            addCriterion("pre_send_time =", value, "preSendTime");
            return (Criteria) this;
        }

        public Criteria andPreSendTimeNotEqualTo(Integer value) {
            addCriterion("pre_send_time <>", value, "preSendTime");
            return (Criteria) this;
        }

        public Criteria andPreSendTimeGreaterThan(Integer value) {
            addCriterion("pre_send_time >", value, "preSendTime");
            return (Criteria) this;
        }

        public Criteria andPreSendTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("pre_send_time >=", value, "preSendTime");
            return (Criteria) this;
        }

        public Criteria andPreSendTimeLessThan(Integer value) {
            addCriterion("pre_send_time <", value, "preSendTime");
            return (Criteria) this;
        }

        public Criteria andPreSendTimeLessThanOrEqualTo(Integer value) {
            addCriterion("pre_send_time <=", value, "preSendTime");
            return (Criteria) this;
        }

        public Criteria andPreSendTimeIn(List<Integer> values) {
            addCriterion("pre_send_time in", values, "preSendTime");
            return (Criteria) this;
        }

        public Criteria andPreSendTimeNotIn(List<Integer> values) {
            addCriterion("pre_send_time not in", values, "preSendTime");
            return (Criteria) this;
        }

        public Criteria andPreSendTimeBetween(Integer value1, Integer value2) {
            addCriterion("pre_send_time between", value1, value2, "preSendTime");
            return (Criteria) this;
        }

        public Criteria andPreSendTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("pre_send_time not between", value1, value2, "preSendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeIsNull() {
            addCriterion("send_time is null");
            return (Criteria) this;
        }

        public Criteria andSendTimeIsNotNull() {
            addCriterion("send_time is not null");
            return (Criteria) this;
        }

        public Criteria andSendTimeEqualTo(Integer value) {
            addCriterion("send_time =", value, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeNotEqualTo(Integer value) {
            addCriterion("send_time <>", value, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeGreaterThan(Integer value) {
            addCriterion("send_time >", value, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("send_time >=", value, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeLessThan(Integer value) {
            addCriterion("send_time <", value, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeLessThanOrEqualTo(Integer value) {
            addCriterion("send_time <=", value, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeIn(List<Integer> values) {
            addCriterion("send_time in", values, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeNotIn(List<Integer> values) {
            addCriterion("send_time not in", values, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeBetween(Integer value1, Integer value2) {
            addCriterion("send_time between", value1, value2, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("send_time not between", value1, value2, "sendTime");
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