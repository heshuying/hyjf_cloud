package com.hyjf.am.config.dao.model.auto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CertLogExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public CertLogExample() {
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

        public Criteria andMqContentIsNull() {
            addCriterion("mq_content is null");
            return (Criteria) this;
        }

        public Criteria andMqContentIsNotNull() {
            addCriterion("mq_content is not null");
            return (Criteria) this;
        }

        public Criteria andMqContentEqualTo(String value) {
            addCriterion("mq_content =", value, "mqContent");
            return (Criteria) this;
        }

        public Criteria andMqContentNotEqualTo(String value) {
            addCriterion("mq_content <>", value, "mqContent");
            return (Criteria) this;
        }

        public Criteria andMqContentGreaterThan(String value) {
            addCriterion("mq_content >", value, "mqContent");
            return (Criteria) this;
        }

        public Criteria andMqContentGreaterThanOrEqualTo(String value) {
            addCriterion("mq_content >=", value, "mqContent");
            return (Criteria) this;
        }

        public Criteria andMqContentLessThan(String value) {
            addCriterion("mq_content <", value, "mqContent");
            return (Criteria) this;
        }

        public Criteria andMqContentLessThanOrEqualTo(String value) {
            addCriterion("mq_content <=", value, "mqContent");
            return (Criteria) this;
        }

        public Criteria andMqContentLike(String value) {
            addCriterion("mq_content like", value, "mqContent");
            return (Criteria) this;
        }

        public Criteria andMqContentNotLike(String value) {
            addCriterion("mq_content not like", value, "mqContent");
            return (Criteria) this;
        }

        public Criteria andMqContentIn(List<String> values) {
            addCriterion("mq_content in", values, "mqContent");
            return (Criteria) this;
        }

        public Criteria andMqContentNotIn(List<String> values) {
            addCriterion("mq_content not in", values, "mqContent");
            return (Criteria) this;
        }

        public Criteria andMqContentBetween(String value1, String value2) {
            addCriterion("mq_content between", value1, value2, "mqContent");
            return (Criteria) this;
        }

        public Criteria andMqContentNotBetween(String value1, String value2) {
            addCriterion("mq_content not between", value1, value2, "mqContent");
            return (Criteria) this;
        }

        public Criteria andLogOrdIdIsNull() {
            addCriterion("log_ord_id is null");
            return (Criteria) this;
        }

        public Criteria andLogOrdIdIsNotNull() {
            addCriterion("log_ord_id is not null");
            return (Criteria) this;
        }

        public Criteria andLogOrdIdEqualTo(String value) {
            addCriterion("log_ord_id =", value, "logOrdId");
            return (Criteria) this;
        }

        public Criteria andLogOrdIdNotEqualTo(String value) {
            addCriterion("log_ord_id <>", value, "logOrdId");
            return (Criteria) this;
        }

        public Criteria andLogOrdIdGreaterThan(String value) {
            addCriterion("log_ord_id >", value, "logOrdId");
            return (Criteria) this;
        }

        public Criteria andLogOrdIdGreaterThanOrEqualTo(String value) {
            addCriterion("log_ord_id >=", value, "logOrdId");
            return (Criteria) this;
        }

        public Criteria andLogOrdIdLessThan(String value) {
            addCriterion("log_ord_id <", value, "logOrdId");
            return (Criteria) this;
        }

        public Criteria andLogOrdIdLessThanOrEqualTo(String value) {
            addCriterion("log_ord_id <=", value, "logOrdId");
            return (Criteria) this;
        }

        public Criteria andLogOrdIdLike(String value) {
            addCriterion("log_ord_id like", value, "logOrdId");
            return (Criteria) this;
        }

        public Criteria andLogOrdIdNotLike(String value) {
            addCriterion("log_ord_id not like", value, "logOrdId");
            return (Criteria) this;
        }

        public Criteria andLogOrdIdIn(List<String> values) {
            addCriterion("log_ord_id in", values, "logOrdId");
            return (Criteria) this;
        }

        public Criteria andLogOrdIdNotIn(List<String> values) {
            addCriterion("log_ord_id not in", values, "logOrdId");
            return (Criteria) this;
        }

        public Criteria andLogOrdIdBetween(String value1, String value2) {
            addCriterion("log_ord_id between", value1, value2, "logOrdId");
            return (Criteria) this;
        }

        public Criteria andLogOrdIdNotBetween(String value1, String value2) {
            addCriterion("log_ord_id not between", value1, value2, "logOrdId");
            return (Criteria) this;
        }

        public Criteria andInfTypeIsNull() {
            addCriterion("inf_type is null");
            return (Criteria) this;
        }

        public Criteria andInfTypeIsNotNull() {
            addCriterion("inf_type is not null");
            return (Criteria) this;
        }

        public Criteria andInfTypeEqualTo(Integer value) {
            addCriterion("inf_type =", value, "infType");
            return (Criteria) this;
        }

        public Criteria andInfTypeNotEqualTo(Integer value) {
            addCriterion("inf_type <>", value, "infType");
            return (Criteria) this;
        }

        public Criteria andInfTypeGreaterThan(Integer value) {
            addCriterion("inf_type >", value, "infType");
            return (Criteria) this;
        }

        public Criteria andInfTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("inf_type >=", value, "infType");
            return (Criteria) this;
        }

        public Criteria andInfTypeLessThan(Integer value) {
            addCriterion("inf_type <", value, "infType");
            return (Criteria) this;
        }

        public Criteria andInfTypeLessThanOrEqualTo(Integer value) {
            addCriterion("inf_type <=", value, "infType");
            return (Criteria) this;
        }

        public Criteria andInfTypeIn(List<Integer> values) {
            addCriterion("inf_type in", values, "infType");
            return (Criteria) this;
        }

        public Criteria andInfTypeNotIn(List<Integer> values) {
            addCriterion("inf_type not in", values, "infType");
            return (Criteria) this;
        }

        public Criteria andInfTypeBetween(Integer value1, Integer value2) {
            addCriterion("inf_type between", value1, value2, "infType");
            return (Criteria) this;
        }

        public Criteria andInfTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("inf_type not between", value1, value2, "infType");
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

        public Criteria andSendStatusIsNull() {
            addCriterion("send_status is null");
            return (Criteria) this;
        }

        public Criteria andSendStatusIsNotNull() {
            addCriterion("send_status is not null");
            return (Criteria) this;
        }

        public Criteria andSendStatusEqualTo(Integer value) {
            addCriterion("send_status =", value, "sendStatus");
            return (Criteria) this;
        }

        public Criteria andSendStatusNotEqualTo(Integer value) {
            addCriterion("send_status <>", value, "sendStatus");
            return (Criteria) this;
        }

        public Criteria andSendStatusGreaterThan(Integer value) {
            addCriterion("send_status >", value, "sendStatus");
            return (Criteria) this;
        }

        public Criteria andSendStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("send_status >=", value, "sendStatus");
            return (Criteria) this;
        }

        public Criteria andSendStatusLessThan(Integer value) {
            addCriterion("send_status <", value, "sendStatus");
            return (Criteria) this;
        }

        public Criteria andSendStatusLessThanOrEqualTo(Integer value) {
            addCriterion("send_status <=", value, "sendStatus");
            return (Criteria) this;
        }

        public Criteria andSendStatusIn(List<Integer> values) {
            addCriterion("send_status in", values, "sendStatus");
            return (Criteria) this;
        }

        public Criteria andSendStatusNotIn(List<Integer> values) {
            addCriterion("send_status not in", values, "sendStatus");
            return (Criteria) this;
        }

        public Criteria andSendStatusBetween(Integer value1, Integer value2) {
            addCriterion("send_status between", value1, value2, "sendStatus");
            return (Criteria) this;
        }

        public Criteria andSendStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("send_status not between", value1, value2, "sendStatus");
            return (Criteria) this;
        }

        public Criteria andResultCodeIsNull() {
            addCriterion("result_code is null");
            return (Criteria) this;
        }

        public Criteria andResultCodeIsNotNull() {
            addCriterion("result_code is not null");
            return (Criteria) this;
        }

        public Criteria andResultCodeEqualTo(String value) {
            addCriterion("result_code =", value, "resultCode");
            return (Criteria) this;
        }

        public Criteria andResultCodeNotEqualTo(String value) {
            addCriterion("result_code <>", value, "resultCode");
            return (Criteria) this;
        }

        public Criteria andResultCodeGreaterThan(String value) {
            addCriterion("result_code >", value, "resultCode");
            return (Criteria) this;
        }

        public Criteria andResultCodeGreaterThanOrEqualTo(String value) {
            addCriterion("result_code >=", value, "resultCode");
            return (Criteria) this;
        }

        public Criteria andResultCodeLessThan(String value) {
            addCriterion("result_code <", value, "resultCode");
            return (Criteria) this;
        }

        public Criteria andResultCodeLessThanOrEqualTo(String value) {
            addCriterion("result_code <=", value, "resultCode");
            return (Criteria) this;
        }

        public Criteria andResultCodeLike(String value) {
            addCriterion("result_code like", value, "resultCode");
            return (Criteria) this;
        }

        public Criteria andResultCodeNotLike(String value) {
            addCriterion("result_code not like", value, "resultCode");
            return (Criteria) this;
        }

        public Criteria andResultCodeIn(List<String> values) {
            addCriterion("result_code in", values, "resultCode");
            return (Criteria) this;
        }

        public Criteria andResultCodeNotIn(List<String> values) {
            addCriterion("result_code not in", values, "resultCode");
            return (Criteria) this;
        }

        public Criteria andResultCodeBetween(String value1, String value2) {
            addCriterion("result_code between", value1, value2, "resultCode");
            return (Criteria) this;
        }

        public Criteria andResultCodeNotBetween(String value1, String value2) {
            addCriterion("result_code not between", value1, value2, "resultCode");
            return (Criteria) this;
        }

        public Criteria andResultMsgIsNull() {
            addCriterion("result_msg is null");
            return (Criteria) this;
        }

        public Criteria andResultMsgIsNotNull() {
            addCriterion("result_msg is not null");
            return (Criteria) this;
        }

        public Criteria andResultMsgEqualTo(String value) {
            addCriterion("result_msg =", value, "resultMsg");
            return (Criteria) this;
        }

        public Criteria andResultMsgNotEqualTo(String value) {
            addCriterion("result_msg <>", value, "resultMsg");
            return (Criteria) this;
        }

        public Criteria andResultMsgGreaterThan(String value) {
            addCriterion("result_msg >", value, "resultMsg");
            return (Criteria) this;
        }

        public Criteria andResultMsgGreaterThanOrEqualTo(String value) {
            addCriterion("result_msg >=", value, "resultMsg");
            return (Criteria) this;
        }

        public Criteria andResultMsgLessThan(String value) {
            addCriterion("result_msg <", value, "resultMsg");
            return (Criteria) this;
        }

        public Criteria andResultMsgLessThanOrEqualTo(String value) {
            addCriterion("result_msg <=", value, "resultMsg");
            return (Criteria) this;
        }

        public Criteria andResultMsgLike(String value) {
            addCriterion("result_msg like", value, "resultMsg");
            return (Criteria) this;
        }

        public Criteria andResultMsgNotLike(String value) {
            addCriterion("result_msg not like", value, "resultMsg");
            return (Criteria) this;
        }

        public Criteria andResultMsgIn(List<String> values) {
            addCriterion("result_msg in", values, "resultMsg");
            return (Criteria) this;
        }

        public Criteria andResultMsgNotIn(List<String> values) {
            addCriterion("result_msg not in", values, "resultMsg");
            return (Criteria) this;
        }

        public Criteria andResultMsgBetween(String value1, String value2) {
            addCriterion("result_msg between", value1, value2, "resultMsg");
            return (Criteria) this;
        }

        public Criteria andResultMsgNotBetween(String value1, String value2) {
            addCriterion("result_msg not between", value1, value2, "resultMsg");
            return (Criteria) this;
        }

        public Criteria andQueryResultIsNull() {
            addCriterion("query_result is null");
            return (Criteria) this;
        }

        public Criteria andQueryResultIsNotNull() {
            addCriterion("query_result is not null");
            return (Criteria) this;
        }

        public Criteria andQueryResultEqualTo(Integer value) {
            addCriterion("query_result =", value, "queryResult");
            return (Criteria) this;
        }

        public Criteria andQueryResultNotEqualTo(Integer value) {
            addCriterion("query_result <>", value, "queryResult");
            return (Criteria) this;
        }

        public Criteria andQueryResultGreaterThan(Integer value) {
            addCriterion("query_result >", value, "queryResult");
            return (Criteria) this;
        }

        public Criteria andQueryResultGreaterThanOrEqualTo(Integer value) {
            addCriterion("query_result >=", value, "queryResult");
            return (Criteria) this;
        }

        public Criteria andQueryResultLessThan(Integer value) {
            addCriterion("query_result <", value, "queryResult");
            return (Criteria) this;
        }

        public Criteria andQueryResultLessThanOrEqualTo(Integer value) {
            addCriterion("query_result <=", value, "queryResult");
            return (Criteria) this;
        }

        public Criteria andQueryResultIn(List<Integer> values) {
            addCriterion("query_result in", values, "queryResult");
            return (Criteria) this;
        }

        public Criteria andQueryResultNotIn(List<Integer> values) {
            addCriterion("query_result not in", values, "queryResult");
            return (Criteria) this;
        }

        public Criteria andQueryResultBetween(Integer value1, Integer value2) {
            addCriterion("query_result between", value1, value2, "queryResult");
            return (Criteria) this;
        }

        public Criteria andQueryResultNotBetween(Integer value1, Integer value2) {
            addCriterion("query_result not between", value1, value2, "queryResult");
            return (Criteria) this;
        }

        public Criteria andQueryMsgIsNull() {
            addCriterion("query_msg is null");
            return (Criteria) this;
        }

        public Criteria andQueryMsgIsNotNull() {
            addCriterion("query_msg is not null");
            return (Criteria) this;
        }

        public Criteria andQueryMsgEqualTo(String value) {
            addCriterion("query_msg =", value, "queryMsg");
            return (Criteria) this;
        }

        public Criteria andQueryMsgNotEqualTo(String value) {
            addCriterion("query_msg <>", value, "queryMsg");
            return (Criteria) this;
        }

        public Criteria andQueryMsgGreaterThan(String value) {
            addCriterion("query_msg >", value, "queryMsg");
            return (Criteria) this;
        }

        public Criteria andQueryMsgGreaterThanOrEqualTo(String value) {
            addCriterion("query_msg >=", value, "queryMsg");
            return (Criteria) this;
        }

        public Criteria andQueryMsgLessThan(String value) {
            addCriterion("query_msg <", value, "queryMsg");
            return (Criteria) this;
        }

        public Criteria andQueryMsgLessThanOrEqualTo(String value) {
            addCriterion("query_msg <=", value, "queryMsg");
            return (Criteria) this;
        }

        public Criteria andQueryMsgLike(String value) {
            addCriterion("query_msg like", value, "queryMsg");
            return (Criteria) this;
        }

        public Criteria andQueryMsgNotLike(String value) {
            addCriterion("query_msg not like", value, "queryMsg");
            return (Criteria) this;
        }

        public Criteria andQueryMsgIn(List<String> values) {
            addCriterion("query_msg in", values, "queryMsg");
            return (Criteria) this;
        }

        public Criteria andQueryMsgNotIn(List<String> values) {
            addCriterion("query_msg not in", values, "queryMsg");
            return (Criteria) this;
        }

        public Criteria andQueryMsgBetween(String value1, String value2) {
            addCriterion("query_msg between", value1, value2, "queryMsg");
            return (Criteria) this;
        }

        public Criteria andQueryMsgNotBetween(String value1, String value2) {
            addCriterion("query_msg not between", value1, value2, "queryMsg");
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