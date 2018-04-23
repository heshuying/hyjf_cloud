package com.hyjf.am.message.dao.model.auto;

import java.util.ArrayList;
import java.util.List;

public class MessagePushMsgHistoryExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public MessagePushMsgHistoryExample() {
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

        public Criteria andMsgDestinationIsNull() {
            addCriterion("msg_destination is null");
            return (Criteria) this;
        }

        public Criteria andMsgDestinationIsNotNull() {
            addCriterion("msg_destination is not null");
            return (Criteria) this;
        }

        public Criteria andMsgDestinationEqualTo(String value) {
            addCriterion("msg_destination =", value, "msgDestination");
            return (Criteria) this;
        }

        public Criteria andMsgDestinationNotEqualTo(String value) {
            addCriterion("msg_destination <>", value, "msgDestination");
            return (Criteria) this;
        }

        public Criteria andMsgDestinationGreaterThan(String value) {
            addCriterion("msg_destination >", value, "msgDestination");
            return (Criteria) this;
        }

        public Criteria andMsgDestinationGreaterThanOrEqualTo(String value) {
            addCriterion("msg_destination >=", value, "msgDestination");
            return (Criteria) this;
        }

        public Criteria andMsgDestinationLessThan(String value) {
            addCriterion("msg_destination <", value, "msgDestination");
            return (Criteria) this;
        }

        public Criteria andMsgDestinationLessThanOrEqualTo(String value) {
            addCriterion("msg_destination <=", value, "msgDestination");
            return (Criteria) this;
        }

        public Criteria andMsgDestinationLike(String value) {
            addCriterion("msg_destination like", value, "msgDestination");
            return (Criteria) this;
        }

        public Criteria andMsgDestinationNotLike(String value) {
            addCriterion("msg_destination not like", value, "msgDestination");
            return (Criteria) this;
        }

        public Criteria andMsgDestinationIn(List<String> values) {
            addCriterion("msg_destination in", values, "msgDestination");
            return (Criteria) this;
        }

        public Criteria andMsgDestinationNotIn(List<String> values) {
            addCriterion("msg_destination not in", values, "msgDestination");
            return (Criteria) this;
        }

        public Criteria andMsgDestinationBetween(String value1, String value2) {
            addCriterion("msg_destination between", value1, value2, "msgDestination");
            return (Criteria) this;
        }

        public Criteria andMsgDestinationNotBetween(String value1, String value2) {
            addCriterion("msg_destination not between", value1, value2, "msgDestination");
            return (Criteria) this;
        }

        public Criteria andMsgUserIdIsNull() {
            addCriterion("msg_user_id is null");
            return (Criteria) this;
        }

        public Criteria andMsgUserIdIsNotNull() {
            addCriterion("msg_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andMsgUserIdEqualTo(Integer value) {
            addCriterion("msg_user_id =", value, "msgUserId");
            return (Criteria) this;
        }

        public Criteria andMsgUserIdNotEqualTo(Integer value) {
            addCriterion("msg_user_id <>", value, "msgUserId");
            return (Criteria) this;
        }

        public Criteria andMsgUserIdGreaterThan(Integer value) {
            addCriterion("msg_user_id >", value, "msgUserId");
            return (Criteria) this;
        }

        public Criteria andMsgUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("msg_user_id >=", value, "msgUserId");
            return (Criteria) this;
        }

        public Criteria andMsgUserIdLessThan(Integer value) {
            addCriterion("msg_user_id <", value, "msgUserId");
            return (Criteria) this;
        }

        public Criteria andMsgUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("msg_user_id <=", value, "msgUserId");
            return (Criteria) this;
        }

        public Criteria andMsgUserIdIn(List<Integer> values) {
            addCriterion("msg_user_id in", values, "msgUserId");
            return (Criteria) this;
        }

        public Criteria andMsgUserIdNotIn(List<Integer> values) {
            addCriterion("msg_user_id not in", values, "msgUserId");
            return (Criteria) this;
        }

        public Criteria andMsgUserIdBetween(Integer value1, Integer value2) {
            addCriterion("msg_user_id between", value1, value2, "msgUserId");
            return (Criteria) this;
        }

        public Criteria andMsgUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("msg_user_id not between", value1, value2, "msgUserId");
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

        public Criteria andMsgReadCountAndroidIsNull() {
            addCriterion("msg_read_count_android is null");
            return (Criteria) this;
        }

        public Criteria andMsgReadCountAndroidIsNotNull() {
            addCriterion("msg_read_count_android is not null");
            return (Criteria) this;
        }

        public Criteria andMsgReadCountAndroidEqualTo(Integer value) {
            addCriterion("msg_read_count_android =", value, "msgReadCountAndroid");
            return (Criteria) this;
        }

        public Criteria andMsgReadCountAndroidNotEqualTo(Integer value) {
            addCriterion("msg_read_count_android <>", value, "msgReadCountAndroid");
            return (Criteria) this;
        }

        public Criteria andMsgReadCountAndroidGreaterThan(Integer value) {
            addCriterion("msg_read_count_android >", value, "msgReadCountAndroid");
            return (Criteria) this;
        }

        public Criteria andMsgReadCountAndroidGreaterThanOrEqualTo(Integer value) {
            addCriterion("msg_read_count_android >=", value, "msgReadCountAndroid");
            return (Criteria) this;
        }

        public Criteria andMsgReadCountAndroidLessThan(Integer value) {
            addCriterion("msg_read_count_android <", value, "msgReadCountAndroid");
            return (Criteria) this;
        }

        public Criteria andMsgReadCountAndroidLessThanOrEqualTo(Integer value) {
            addCriterion("msg_read_count_android <=", value, "msgReadCountAndroid");
            return (Criteria) this;
        }

        public Criteria andMsgReadCountAndroidIn(List<Integer> values) {
            addCriterion("msg_read_count_android in", values, "msgReadCountAndroid");
            return (Criteria) this;
        }

        public Criteria andMsgReadCountAndroidNotIn(List<Integer> values) {
            addCriterion("msg_read_count_android not in", values, "msgReadCountAndroid");
            return (Criteria) this;
        }

        public Criteria andMsgReadCountAndroidBetween(Integer value1, Integer value2) {
            addCriterion("msg_read_count_android between", value1, value2, "msgReadCountAndroid");
            return (Criteria) this;
        }

        public Criteria andMsgReadCountAndroidNotBetween(Integer value1, Integer value2) {
            addCriterion("msg_read_count_android not between", value1, value2, "msgReadCountAndroid");
            return (Criteria) this;
        }

        public Criteria andMsgReadCountIosIsNull() {
            addCriterion("msg_read_count_ios is null");
            return (Criteria) this;
        }

        public Criteria andMsgReadCountIosIsNotNull() {
            addCriterion("msg_read_count_ios is not null");
            return (Criteria) this;
        }

        public Criteria andMsgReadCountIosEqualTo(Integer value) {
            addCriterion("msg_read_count_ios =", value, "msgReadCountIos");
            return (Criteria) this;
        }

        public Criteria andMsgReadCountIosNotEqualTo(Integer value) {
            addCriterion("msg_read_count_ios <>", value, "msgReadCountIos");
            return (Criteria) this;
        }

        public Criteria andMsgReadCountIosGreaterThan(Integer value) {
            addCriterion("msg_read_count_ios >", value, "msgReadCountIos");
            return (Criteria) this;
        }

        public Criteria andMsgReadCountIosGreaterThanOrEqualTo(Integer value) {
            addCriterion("msg_read_count_ios >=", value, "msgReadCountIos");
            return (Criteria) this;
        }

        public Criteria andMsgReadCountIosLessThan(Integer value) {
            addCriterion("msg_read_count_ios <", value, "msgReadCountIos");
            return (Criteria) this;
        }

        public Criteria andMsgReadCountIosLessThanOrEqualTo(Integer value) {
            addCriterion("msg_read_count_ios <=", value, "msgReadCountIos");
            return (Criteria) this;
        }

        public Criteria andMsgReadCountIosIn(List<Integer> values) {
            addCriterion("msg_read_count_ios in", values, "msgReadCountIos");
            return (Criteria) this;
        }

        public Criteria andMsgReadCountIosNotIn(List<Integer> values) {
            addCriterion("msg_read_count_ios not in", values, "msgReadCountIos");
            return (Criteria) this;
        }

        public Criteria andMsgReadCountIosBetween(Integer value1, Integer value2) {
            addCriterion("msg_read_count_ios between", value1, value2, "msgReadCountIos");
            return (Criteria) this;
        }

        public Criteria andMsgReadCountIosNotBetween(Integer value1, Integer value2) {
            addCriterion("msg_read_count_ios not between", value1, value2, "msgReadCountIos");
            return (Criteria) this;
        }

        public Criteria andMsgFirstreadPlatIsNull() {
            addCriterion("msg_firstread_plat is null");
            return (Criteria) this;
        }

        public Criteria andMsgFirstreadPlatIsNotNull() {
            addCriterion("msg_firstread_plat is not null");
            return (Criteria) this;
        }

        public Criteria andMsgFirstreadPlatEqualTo(Integer value) {
            addCriterion("msg_firstread_plat =", value, "msgFirstreadPlat");
            return (Criteria) this;
        }

        public Criteria andMsgFirstreadPlatNotEqualTo(Integer value) {
            addCriterion("msg_firstread_plat <>", value, "msgFirstreadPlat");
            return (Criteria) this;
        }

        public Criteria andMsgFirstreadPlatGreaterThan(Integer value) {
            addCriterion("msg_firstread_plat >", value, "msgFirstreadPlat");
            return (Criteria) this;
        }

        public Criteria andMsgFirstreadPlatGreaterThanOrEqualTo(Integer value) {
            addCriterion("msg_firstread_plat >=", value, "msgFirstreadPlat");
            return (Criteria) this;
        }

        public Criteria andMsgFirstreadPlatLessThan(Integer value) {
            addCriterion("msg_firstread_plat <", value, "msgFirstreadPlat");
            return (Criteria) this;
        }

        public Criteria andMsgFirstreadPlatLessThanOrEqualTo(Integer value) {
            addCriterion("msg_firstread_plat <=", value, "msgFirstreadPlat");
            return (Criteria) this;
        }

        public Criteria andMsgFirstreadPlatIn(List<Integer> values) {
            addCriterion("msg_firstread_plat in", values, "msgFirstreadPlat");
            return (Criteria) this;
        }

        public Criteria andMsgFirstreadPlatNotIn(List<Integer> values) {
            addCriterion("msg_firstread_plat not in", values, "msgFirstreadPlat");
            return (Criteria) this;
        }

        public Criteria andMsgFirstreadPlatBetween(Integer value1, Integer value2) {
            addCriterion("msg_firstread_plat between", value1, value2, "msgFirstreadPlat");
            return (Criteria) this;
        }

        public Criteria andMsgFirstreadPlatNotBetween(Integer value1, Integer value2) {
            addCriterion("msg_firstread_plat not between", value1, value2, "msgFirstreadPlat");
            return (Criteria) this;
        }

        public Criteria andMsgJpushIdIsNull() {
            addCriterion("msg_jpush_id is null");
            return (Criteria) this;
        }

        public Criteria andMsgJpushIdIsNotNull() {
            addCriterion("msg_jpush_id is not null");
            return (Criteria) this;
        }

        public Criteria andMsgJpushIdEqualTo(String value) {
            addCriterion("msg_jpush_id =", value, "msgJpushId");
            return (Criteria) this;
        }

        public Criteria andMsgJpushIdNotEqualTo(String value) {
            addCriterion("msg_jpush_id <>", value, "msgJpushId");
            return (Criteria) this;
        }

        public Criteria andMsgJpushIdGreaterThan(String value) {
            addCriterion("msg_jpush_id >", value, "msgJpushId");
            return (Criteria) this;
        }

        public Criteria andMsgJpushIdGreaterThanOrEqualTo(String value) {
            addCriterion("msg_jpush_id >=", value, "msgJpushId");
            return (Criteria) this;
        }

        public Criteria andMsgJpushIdLessThan(String value) {
            addCriterion("msg_jpush_id <", value, "msgJpushId");
            return (Criteria) this;
        }

        public Criteria andMsgJpushIdLessThanOrEqualTo(String value) {
            addCriterion("msg_jpush_id <=", value, "msgJpushId");
            return (Criteria) this;
        }

        public Criteria andMsgJpushIdLike(String value) {
            addCriterion("msg_jpush_id like", value, "msgJpushId");
            return (Criteria) this;
        }

        public Criteria andMsgJpushIdNotLike(String value) {
            addCriterion("msg_jpush_id not like", value, "msgJpushId");
            return (Criteria) this;
        }

        public Criteria andMsgJpushIdIn(List<String> values) {
            addCriterion("msg_jpush_id in", values, "msgJpushId");
            return (Criteria) this;
        }

        public Criteria andMsgJpushIdNotIn(List<String> values) {
            addCriterion("msg_jpush_id not in", values, "msgJpushId");
            return (Criteria) this;
        }

        public Criteria andMsgJpushIdBetween(String value1, String value2) {
            addCriterion("msg_jpush_id between", value1, value2, "msgJpushId");
            return (Criteria) this;
        }

        public Criteria andMsgJpushIdNotBetween(String value1, String value2) {
            addCriterion("msg_jpush_id not between", value1, value2, "msgJpushId");
            return (Criteria) this;
        }

        public Criteria andMsgJpushProIdIsNull() {
            addCriterion("msg_jpush_pro_id is null");
            return (Criteria) this;
        }

        public Criteria andMsgJpushProIdIsNotNull() {
            addCriterion("msg_jpush_pro_id is not null");
            return (Criteria) this;
        }

        public Criteria andMsgJpushProIdEqualTo(String value) {
            addCriterion("msg_jpush_pro_id =", value, "msgJpushProId");
            return (Criteria) this;
        }

        public Criteria andMsgJpushProIdNotEqualTo(String value) {
            addCriterion("msg_jpush_pro_id <>", value, "msgJpushProId");
            return (Criteria) this;
        }

        public Criteria andMsgJpushProIdGreaterThan(String value) {
            addCriterion("msg_jpush_pro_id >", value, "msgJpushProId");
            return (Criteria) this;
        }

        public Criteria andMsgJpushProIdGreaterThanOrEqualTo(String value) {
            addCriterion("msg_jpush_pro_id >=", value, "msgJpushProId");
            return (Criteria) this;
        }

        public Criteria andMsgJpushProIdLessThan(String value) {
            addCriterion("msg_jpush_pro_id <", value, "msgJpushProId");
            return (Criteria) this;
        }

        public Criteria andMsgJpushProIdLessThanOrEqualTo(String value) {
            addCriterion("msg_jpush_pro_id <=", value, "msgJpushProId");
            return (Criteria) this;
        }

        public Criteria andMsgJpushProIdLike(String value) {
            addCriterion("msg_jpush_pro_id like", value, "msgJpushProId");
            return (Criteria) this;
        }

        public Criteria andMsgJpushProIdNotLike(String value) {
            addCriterion("msg_jpush_pro_id not like", value, "msgJpushProId");
            return (Criteria) this;
        }

        public Criteria andMsgJpushProIdIn(List<String> values) {
            addCriterion("msg_jpush_pro_id in", values, "msgJpushProId");
            return (Criteria) this;
        }

        public Criteria andMsgJpushProIdNotIn(List<String> values) {
            addCriterion("msg_jpush_pro_id not in", values, "msgJpushProId");
            return (Criteria) this;
        }

        public Criteria andMsgJpushProIdBetween(String value1, String value2) {
            addCriterion("msg_jpush_pro_id between", value1, value2, "msgJpushProId");
            return (Criteria) this;
        }

        public Criteria andMsgJpushProIdNotBetween(String value1, String value2) {
            addCriterion("msg_jpush_pro_id not between", value1, value2, "msgJpushProId");
            return (Criteria) this;
        }

        public Criteria andMsgJpushZybIdIsNull() {
            addCriterion("msg_jpush_zyb_id is null");
            return (Criteria) this;
        }

        public Criteria andMsgJpushZybIdIsNotNull() {
            addCriterion("msg_jpush_zyb_id is not null");
            return (Criteria) this;
        }

        public Criteria andMsgJpushZybIdEqualTo(String value) {
            addCriterion("msg_jpush_zyb_id =", value, "msgJpushZybId");
            return (Criteria) this;
        }

        public Criteria andMsgJpushZybIdNotEqualTo(String value) {
            addCriterion("msg_jpush_zyb_id <>", value, "msgJpushZybId");
            return (Criteria) this;
        }

        public Criteria andMsgJpushZybIdGreaterThan(String value) {
            addCriterion("msg_jpush_zyb_id >", value, "msgJpushZybId");
            return (Criteria) this;
        }

        public Criteria andMsgJpushZybIdGreaterThanOrEqualTo(String value) {
            addCriterion("msg_jpush_zyb_id >=", value, "msgJpushZybId");
            return (Criteria) this;
        }

        public Criteria andMsgJpushZybIdLessThan(String value) {
            addCriterion("msg_jpush_zyb_id <", value, "msgJpushZybId");
            return (Criteria) this;
        }

        public Criteria andMsgJpushZybIdLessThanOrEqualTo(String value) {
            addCriterion("msg_jpush_zyb_id <=", value, "msgJpushZybId");
            return (Criteria) this;
        }

        public Criteria andMsgJpushZybIdLike(String value) {
            addCriterion("msg_jpush_zyb_id like", value, "msgJpushZybId");
            return (Criteria) this;
        }

        public Criteria andMsgJpushZybIdNotLike(String value) {
            addCriterion("msg_jpush_zyb_id not like", value, "msgJpushZybId");
            return (Criteria) this;
        }

        public Criteria andMsgJpushZybIdIn(List<String> values) {
            addCriterion("msg_jpush_zyb_id in", values, "msgJpushZybId");
            return (Criteria) this;
        }

        public Criteria andMsgJpushZybIdNotIn(List<String> values) {
            addCriterion("msg_jpush_zyb_id not in", values, "msgJpushZybId");
            return (Criteria) this;
        }

        public Criteria andMsgJpushZybIdBetween(String value1, String value2) {
            addCriterion("msg_jpush_zyb_id between", value1, value2, "msgJpushZybId");
            return (Criteria) this;
        }

        public Criteria andMsgJpushZybIdNotBetween(String value1, String value2) {
            addCriterion("msg_jpush_zyb_id not between", value1, value2, "msgJpushZybId");
            return (Criteria) this;
        }

        public Criteria andMsgJpushZzbIdIsNull() {
            addCriterion("msg_jpush_zzb_id is null");
            return (Criteria) this;
        }

        public Criteria andMsgJpushZzbIdIsNotNull() {
            addCriterion("msg_jpush_zzb_id is not null");
            return (Criteria) this;
        }

        public Criteria andMsgJpushZzbIdEqualTo(String value) {
            addCriterion("msg_jpush_zzb_id =", value, "msgJpushZzbId");
            return (Criteria) this;
        }

        public Criteria andMsgJpushZzbIdNotEqualTo(String value) {
            addCriterion("msg_jpush_zzb_id <>", value, "msgJpushZzbId");
            return (Criteria) this;
        }

        public Criteria andMsgJpushZzbIdGreaterThan(String value) {
            addCriterion("msg_jpush_zzb_id >", value, "msgJpushZzbId");
            return (Criteria) this;
        }

        public Criteria andMsgJpushZzbIdGreaterThanOrEqualTo(String value) {
            addCriterion("msg_jpush_zzb_id >=", value, "msgJpushZzbId");
            return (Criteria) this;
        }

        public Criteria andMsgJpushZzbIdLessThan(String value) {
            addCriterion("msg_jpush_zzb_id <", value, "msgJpushZzbId");
            return (Criteria) this;
        }

        public Criteria andMsgJpushZzbIdLessThanOrEqualTo(String value) {
            addCriterion("msg_jpush_zzb_id <=", value, "msgJpushZzbId");
            return (Criteria) this;
        }

        public Criteria andMsgJpushZzbIdLike(String value) {
            addCriterion("msg_jpush_zzb_id like", value, "msgJpushZzbId");
            return (Criteria) this;
        }

        public Criteria andMsgJpushZzbIdNotLike(String value) {
            addCriterion("msg_jpush_zzb_id not like", value, "msgJpushZzbId");
            return (Criteria) this;
        }

        public Criteria andMsgJpushZzbIdIn(List<String> values) {
            addCriterion("msg_jpush_zzb_id in", values, "msgJpushZzbId");
            return (Criteria) this;
        }

        public Criteria andMsgJpushZzbIdNotIn(List<String> values) {
            addCriterion("msg_jpush_zzb_id not in", values, "msgJpushZzbId");
            return (Criteria) this;
        }

        public Criteria andMsgJpushZzbIdBetween(String value1, String value2) {
            addCriterion("msg_jpush_zzb_id between", value1, value2, "msgJpushZzbId");
            return (Criteria) this;
        }

        public Criteria andMsgJpushZzbIdNotBetween(String value1, String value2) {
            addCriterion("msg_jpush_zzb_id not between", value1, value2, "msgJpushZzbId");
            return (Criteria) this;
        }

        public Criteria andMsgJpushYxbIdIsNull() {
            addCriterion("msg_jpush_yxb_id is null");
            return (Criteria) this;
        }

        public Criteria andMsgJpushYxbIdIsNotNull() {
            addCriterion("msg_jpush_yxb_id is not null");
            return (Criteria) this;
        }

        public Criteria andMsgJpushYxbIdEqualTo(String value) {
            addCriterion("msg_jpush_yxb_id =", value, "msgJpushYxbId");
            return (Criteria) this;
        }

        public Criteria andMsgJpushYxbIdNotEqualTo(String value) {
            addCriterion("msg_jpush_yxb_id <>", value, "msgJpushYxbId");
            return (Criteria) this;
        }

        public Criteria andMsgJpushYxbIdGreaterThan(String value) {
            addCriterion("msg_jpush_yxb_id >", value, "msgJpushYxbId");
            return (Criteria) this;
        }

        public Criteria andMsgJpushYxbIdGreaterThanOrEqualTo(String value) {
            addCriterion("msg_jpush_yxb_id >=", value, "msgJpushYxbId");
            return (Criteria) this;
        }

        public Criteria andMsgJpushYxbIdLessThan(String value) {
            addCriterion("msg_jpush_yxb_id <", value, "msgJpushYxbId");
            return (Criteria) this;
        }

        public Criteria andMsgJpushYxbIdLessThanOrEqualTo(String value) {
            addCriterion("msg_jpush_yxb_id <=", value, "msgJpushYxbId");
            return (Criteria) this;
        }

        public Criteria andMsgJpushYxbIdLike(String value) {
            addCriterion("msg_jpush_yxb_id like", value, "msgJpushYxbId");
            return (Criteria) this;
        }

        public Criteria andMsgJpushYxbIdNotLike(String value) {
            addCriterion("msg_jpush_yxb_id not like", value, "msgJpushYxbId");
            return (Criteria) this;
        }

        public Criteria andMsgJpushYxbIdIn(List<String> values) {
            addCriterion("msg_jpush_yxb_id in", values, "msgJpushYxbId");
            return (Criteria) this;
        }

        public Criteria andMsgJpushYxbIdNotIn(List<String> values) {
            addCriterion("msg_jpush_yxb_id not in", values, "msgJpushYxbId");
            return (Criteria) this;
        }

        public Criteria andMsgJpushYxbIdBetween(String value1, String value2) {
            addCriterion("msg_jpush_yxb_id between", value1, value2, "msgJpushYxbId");
            return (Criteria) this;
        }

        public Criteria andMsgJpushYxbIdNotBetween(String value1, String value2) {
            addCriterion("msg_jpush_yxb_id not between", value1, value2, "msgJpushYxbId");
            return (Criteria) this;
        }

        public Criteria andMsgJpushZnbIdIsNull() {
            addCriterion("msg_jpush_znb_id is null");
            return (Criteria) this;
        }

        public Criteria andMsgJpushZnbIdIsNotNull() {
            addCriterion("msg_jpush_znb_id is not null");
            return (Criteria) this;
        }

        public Criteria andMsgJpushZnbIdEqualTo(String value) {
            addCriterion("msg_jpush_znb_id =", value, "msgJpushZnbId");
            return (Criteria) this;
        }

        public Criteria andMsgJpushZnbIdNotEqualTo(String value) {
            addCriterion("msg_jpush_znb_id <>", value, "msgJpushZnbId");
            return (Criteria) this;
        }

        public Criteria andMsgJpushZnbIdGreaterThan(String value) {
            addCriterion("msg_jpush_znb_id >", value, "msgJpushZnbId");
            return (Criteria) this;
        }

        public Criteria andMsgJpushZnbIdGreaterThanOrEqualTo(String value) {
            addCriterion("msg_jpush_znb_id >=", value, "msgJpushZnbId");
            return (Criteria) this;
        }

        public Criteria andMsgJpushZnbIdLessThan(String value) {
            addCriterion("msg_jpush_znb_id <", value, "msgJpushZnbId");
            return (Criteria) this;
        }

        public Criteria andMsgJpushZnbIdLessThanOrEqualTo(String value) {
            addCriterion("msg_jpush_znb_id <=", value, "msgJpushZnbId");
            return (Criteria) this;
        }

        public Criteria andMsgJpushZnbIdLike(String value) {
            addCriterion("msg_jpush_znb_id like", value, "msgJpushZnbId");
            return (Criteria) this;
        }

        public Criteria andMsgJpushZnbIdNotLike(String value) {
            addCriterion("msg_jpush_znb_id not like", value, "msgJpushZnbId");
            return (Criteria) this;
        }

        public Criteria andMsgJpushZnbIdIn(List<String> values) {
            addCriterion("msg_jpush_znb_id in", values, "msgJpushZnbId");
            return (Criteria) this;
        }

        public Criteria andMsgJpushZnbIdNotIn(List<String> values) {
            addCriterion("msg_jpush_znb_id not in", values, "msgJpushZnbId");
            return (Criteria) this;
        }

        public Criteria andMsgJpushZnbIdBetween(String value1, String value2) {
            addCriterion("msg_jpush_znb_id between", value1, value2, "msgJpushZnbId");
            return (Criteria) this;
        }

        public Criteria andMsgJpushZnbIdNotBetween(String value1, String value2) {
            addCriterion("msg_jpush_znb_id not between", value1, value2, "msgJpushZnbId");
            return (Criteria) this;
        }

        public Criteria andMsgRemarkIsNull() {
            addCriterion("msg_remark is null");
            return (Criteria) this;
        }

        public Criteria andMsgRemarkIsNotNull() {
            addCriterion("msg_remark is not null");
            return (Criteria) this;
        }

        public Criteria andMsgRemarkEqualTo(String value) {
            addCriterion("msg_remark =", value, "msgRemark");
            return (Criteria) this;
        }

        public Criteria andMsgRemarkNotEqualTo(String value) {
            addCriterion("msg_remark <>", value, "msgRemark");
            return (Criteria) this;
        }

        public Criteria andMsgRemarkGreaterThan(String value) {
            addCriterion("msg_remark >", value, "msgRemark");
            return (Criteria) this;
        }

        public Criteria andMsgRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("msg_remark >=", value, "msgRemark");
            return (Criteria) this;
        }

        public Criteria andMsgRemarkLessThan(String value) {
            addCriterion("msg_remark <", value, "msgRemark");
            return (Criteria) this;
        }

        public Criteria andMsgRemarkLessThanOrEqualTo(String value) {
            addCriterion("msg_remark <=", value, "msgRemark");
            return (Criteria) this;
        }

        public Criteria andMsgRemarkLike(String value) {
            addCriterion("msg_remark like", value, "msgRemark");
            return (Criteria) this;
        }

        public Criteria andMsgRemarkNotLike(String value) {
            addCriterion("msg_remark not like", value, "msgRemark");
            return (Criteria) this;
        }

        public Criteria andMsgRemarkIn(List<String> values) {
            addCriterion("msg_remark in", values, "msgRemark");
            return (Criteria) this;
        }

        public Criteria andMsgRemarkNotIn(List<String> values) {
            addCriterion("msg_remark not in", values, "msgRemark");
            return (Criteria) this;
        }

        public Criteria andMsgRemarkBetween(String value1, String value2) {
            addCriterion("msg_remark between", value1, value2, "msgRemark");
            return (Criteria) this;
        }

        public Criteria andMsgRemarkNotBetween(String value1, String value2) {
            addCriterion("msg_remark not between", value1, value2, "msgRemark");
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

        public Criteria andMsgDestinationCountIosIsNull() {
            addCriterion("msg_destination_count_ios is null");
            return (Criteria) this;
        }

        public Criteria andMsgDestinationCountIosIsNotNull() {
            addCriterion("msg_destination_count_ios is not null");
            return (Criteria) this;
        }

        public Criteria andMsgDestinationCountIosEqualTo(Integer value) {
            addCriterion("msg_destination_count_ios =", value, "msgDestinationCountIos");
            return (Criteria) this;
        }

        public Criteria andMsgDestinationCountIosNotEqualTo(Integer value) {
            addCriterion("msg_destination_count_ios <>", value, "msgDestinationCountIos");
            return (Criteria) this;
        }

        public Criteria andMsgDestinationCountIosGreaterThan(Integer value) {
            addCriterion("msg_destination_count_ios >", value, "msgDestinationCountIos");
            return (Criteria) this;
        }

        public Criteria andMsgDestinationCountIosGreaterThanOrEqualTo(Integer value) {
            addCriterion("msg_destination_count_ios >=", value, "msgDestinationCountIos");
            return (Criteria) this;
        }

        public Criteria andMsgDestinationCountIosLessThan(Integer value) {
            addCriterion("msg_destination_count_ios <", value, "msgDestinationCountIos");
            return (Criteria) this;
        }

        public Criteria andMsgDestinationCountIosLessThanOrEqualTo(Integer value) {
            addCriterion("msg_destination_count_ios <=", value, "msgDestinationCountIos");
            return (Criteria) this;
        }

        public Criteria andMsgDestinationCountIosIn(List<Integer> values) {
            addCriterion("msg_destination_count_ios in", values, "msgDestinationCountIos");
            return (Criteria) this;
        }

        public Criteria andMsgDestinationCountIosNotIn(List<Integer> values) {
            addCriterion("msg_destination_count_ios not in", values, "msgDestinationCountIos");
            return (Criteria) this;
        }

        public Criteria andMsgDestinationCountIosBetween(Integer value1, Integer value2) {
            addCriterion("msg_destination_count_ios between", value1, value2, "msgDestinationCountIos");
            return (Criteria) this;
        }

        public Criteria andMsgDestinationCountIosNotBetween(Integer value1, Integer value2) {
            addCriterion("msg_destination_count_ios not between", value1, value2, "msgDestinationCountIos");
            return (Criteria) this;
        }

        public Criteria andMsgDestinationCountAndroidIsNull() {
            addCriterion("msg_destination_count_android is null");
            return (Criteria) this;
        }

        public Criteria andMsgDestinationCountAndroidIsNotNull() {
            addCriterion("msg_destination_count_android is not null");
            return (Criteria) this;
        }

        public Criteria andMsgDestinationCountAndroidEqualTo(Integer value) {
            addCriterion("msg_destination_count_android =", value, "msgDestinationCountAndroid");
            return (Criteria) this;
        }

        public Criteria andMsgDestinationCountAndroidNotEqualTo(Integer value) {
            addCriterion("msg_destination_count_android <>", value, "msgDestinationCountAndroid");
            return (Criteria) this;
        }

        public Criteria andMsgDestinationCountAndroidGreaterThan(Integer value) {
            addCriterion("msg_destination_count_android >", value, "msgDestinationCountAndroid");
            return (Criteria) this;
        }

        public Criteria andMsgDestinationCountAndroidGreaterThanOrEqualTo(Integer value) {
            addCriterion("msg_destination_count_android >=", value, "msgDestinationCountAndroid");
            return (Criteria) this;
        }

        public Criteria andMsgDestinationCountAndroidLessThan(Integer value) {
            addCriterion("msg_destination_count_android <", value, "msgDestinationCountAndroid");
            return (Criteria) this;
        }

        public Criteria andMsgDestinationCountAndroidLessThanOrEqualTo(Integer value) {
            addCriterion("msg_destination_count_android <=", value, "msgDestinationCountAndroid");
            return (Criteria) this;
        }

        public Criteria andMsgDestinationCountAndroidIn(List<Integer> values) {
            addCriterion("msg_destination_count_android in", values, "msgDestinationCountAndroid");
            return (Criteria) this;
        }

        public Criteria andMsgDestinationCountAndroidNotIn(List<Integer> values) {
            addCriterion("msg_destination_count_android not in", values, "msgDestinationCountAndroid");
            return (Criteria) this;
        }

        public Criteria andMsgDestinationCountAndroidBetween(Integer value1, Integer value2) {
            addCriterion("msg_destination_count_android between", value1, value2, "msgDestinationCountAndroid");
            return (Criteria) this;
        }

        public Criteria andMsgDestinationCountAndroidNotBetween(Integer value1, Integer value2) {
            addCriterion("msg_destination_count_android not between", value1, value2, "msgDestinationCountAndroid");
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

        public Criteria andMsgJpushZyb2IdIsNull() {
            addCriterion("msg_jpush_zyb2_id is null");
            return (Criteria) this;
        }

        public Criteria andMsgJpushZyb2IdIsNotNull() {
            addCriterion("msg_jpush_zyb2_id is not null");
            return (Criteria) this;
        }

        public Criteria andMsgJpushZyb2IdEqualTo(String value) {
            addCriterion("msg_jpush_zyb2_id =", value, "msgJpushZyb2Id");
            return (Criteria) this;
        }

        public Criteria andMsgJpushZyb2IdNotEqualTo(String value) {
            addCriterion("msg_jpush_zyb2_id <>", value, "msgJpushZyb2Id");
            return (Criteria) this;
        }

        public Criteria andMsgJpushZyb2IdGreaterThan(String value) {
            addCriterion("msg_jpush_zyb2_id >", value, "msgJpushZyb2Id");
            return (Criteria) this;
        }

        public Criteria andMsgJpushZyb2IdGreaterThanOrEqualTo(String value) {
            addCriterion("msg_jpush_zyb2_id >=", value, "msgJpushZyb2Id");
            return (Criteria) this;
        }

        public Criteria andMsgJpushZyb2IdLessThan(String value) {
            addCriterion("msg_jpush_zyb2_id <", value, "msgJpushZyb2Id");
            return (Criteria) this;
        }

        public Criteria andMsgJpushZyb2IdLessThanOrEqualTo(String value) {
            addCriterion("msg_jpush_zyb2_id <=", value, "msgJpushZyb2Id");
            return (Criteria) this;
        }

        public Criteria andMsgJpushZyb2IdLike(String value) {
            addCriterion("msg_jpush_zyb2_id like", value, "msgJpushZyb2Id");
            return (Criteria) this;
        }

        public Criteria andMsgJpushZyb2IdNotLike(String value) {
            addCriterion("msg_jpush_zyb2_id not like", value, "msgJpushZyb2Id");
            return (Criteria) this;
        }

        public Criteria andMsgJpushZyb2IdIn(List<String> values) {
            addCriterion("msg_jpush_zyb2_id in", values, "msgJpushZyb2Id");
            return (Criteria) this;
        }

        public Criteria andMsgJpushZyb2IdNotIn(List<String> values) {
            addCriterion("msg_jpush_zyb2_id not in", values, "msgJpushZyb2Id");
            return (Criteria) this;
        }

        public Criteria andMsgJpushZyb2IdBetween(String value1, String value2) {
            addCriterion("msg_jpush_zyb2_id between", value1, value2, "msgJpushZyb2Id");
            return (Criteria) this;
        }

        public Criteria andMsgJpushZyb2IdNotBetween(String value1, String value2) {
            addCriterion("msg_jpush_zyb2_id not between", value1, value2, "msgJpushZyb2Id");
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