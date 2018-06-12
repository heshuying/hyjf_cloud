package com.hyjf.am.config.dao.model.auto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BankReturnCodeConfigExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public BankReturnCodeConfigExample() {
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

        public Criteria andTxCodeIsNull() {
            addCriterion("tx_code is null");
            return (Criteria) this;
        }

        public Criteria andTxCodeIsNotNull() {
            addCriterion("tx_code is not null");
            return (Criteria) this;
        }

        public Criteria andTxCodeEqualTo(String value) {
            addCriterion("tx_code =", value, "txCode");
            return (Criteria) this;
        }

        public Criteria andTxCodeNotEqualTo(String value) {
            addCriterion("tx_code <>", value, "txCode");
            return (Criteria) this;
        }

        public Criteria andTxCodeGreaterThan(String value) {
            addCriterion("tx_code >", value, "txCode");
            return (Criteria) this;
        }

        public Criteria andTxCodeGreaterThanOrEqualTo(String value) {
            addCriterion("tx_code >=", value, "txCode");
            return (Criteria) this;
        }

        public Criteria andTxCodeLessThan(String value) {
            addCriterion("tx_code <", value, "txCode");
            return (Criteria) this;
        }

        public Criteria andTxCodeLessThanOrEqualTo(String value) {
            addCriterion("tx_code <=", value, "txCode");
            return (Criteria) this;
        }

        public Criteria andTxCodeLike(String value) {
            addCriterion("tx_code like", value, "txCode");
            return (Criteria) this;
        }

        public Criteria andTxCodeNotLike(String value) {
            addCriterion("tx_code not like", value, "txCode");
            return (Criteria) this;
        }

        public Criteria andTxCodeIn(List<String> values) {
            addCriterion("tx_code in", values, "txCode");
            return (Criteria) this;
        }

        public Criteria andTxCodeNotIn(List<String> values) {
            addCriterion("tx_code not in", values, "txCode");
            return (Criteria) this;
        }

        public Criteria andTxCodeBetween(String value1, String value2) {
            addCriterion("tx_code between", value1, value2, "txCode");
            return (Criteria) this;
        }

        public Criteria andTxCodeNotBetween(String value1, String value2) {
            addCriterion("tx_code not between", value1, value2, "txCode");
            return (Criteria) this;
        }

        public Criteria andMethodNameIsNull() {
            addCriterion("method_name is null");
            return (Criteria) this;
        }

        public Criteria andMethodNameIsNotNull() {
            addCriterion("method_name is not null");
            return (Criteria) this;
        }

        public Criteria andMethodNameEqualTo(String value) {
            addCriterion("method_name =", value, "methodName");
            return (Criteria) this;
        }

        public Criteria andMethodNameNotEqualTo(String value) {
            addCriterion("method_name <>", value, "methodName");
            return (Criteria) this;
        }

        public Criteria andMethodNameGreaterThan(String value) {
            addCriterion("method_name >", value, "methodName");
            return (Criteria) this;
        }

        public Criteria andMethodNameGreaterThanOrEqualTo(String value) {
            addCriterion("method_name >=", value, "methodName");
            return (Criteria) this;
        }

        public Criteria andMethodNameLessThan(String value) {
            addCriterion("method_name <", value, "methodName");
            return (Criteria) this;
        }

        public Criteria andMethodNameLessThanOrEqualTo(String value) {
            addCriterion("method_name <=", value, "methodName");
            return (Criteria) this;
        }

        public Criteria andMethodNameLike(String value) {
            addCriterion("method_name like", value, "methodName");
            return (Criteria) this;
        }

        public Criteria andMethodNameNotLike(String value) {
            addCriterion("method_name not like", value, "methodName");
            return (Criteria) this;
        }

        public Criteria andMethodNameIn(List<String> values) {
            addCriterion("method_name in", values, "methodName");
            return (Criteria) this;
        }

        public Criteria andMethodNameNotIn(List<String> values) {
            addCriterion("method_name not in", values, "methodName");
            return (Criteria) this;
        }

        public Criteria andMethodNameBetween(String value1, String value2) {
            addCriterion("method_name between", value1, value2, "methodName");
            return (Criteria) this;
        }

        public Criteria andMethodNameNotBetween(String value1, String value2) {
            addCriterion("method_name not between", value1, value2, "methodName");
            return (Criteria) this;
        }

        public Criteria andRetCodeIsNull() {
            addCriterion("ret_code is null");
            return (Criteria) this;
        }

        public Criteria andRetCodeIsNotNull() {
            addCriterion("ret_code is not null");
            return (Criteria) this;
        }

        public Criteria andRetCodeEqualTo(String value) {
            addCriterion("ret_code =", value, "retCode");
            return (Criteria) this;
        }

        public Criteria andRetCodeNotEqualTo(String value) {
            addCriterion("ret_code <>", value, "retCode");
            return (Criteria) this;
        }

        public Criteria andRetCodeGreaterThan(String value) {
            addCriterion("ret_code >", value, "retCode");
            return (Criteria) this;
        }

        public Criteria andRetCodeGreaterThanOrEqualTo(String value) {
            addCriterion("ret_code >=", value, "retCode");
            return (Criteria) this;
        }

        public Criteria andRetCodeLessThan(String value) {
            addCriterion("ret_code <", value, "retCode");
            return (Criteria) this;
        }

        public Criteria andRetCodeLessThanOrEqualTo(String value) {
            addCriterion("ret_code <=", value, "retCode");
            return (Criteria) this;
        }

        public Criteria andRetCodeLike(String value) {
            addCriterion("ret_code like", value, "retCode");
            return (Criteria) this;
        }

        public Criteria andRetCodeNotLike(String value) {
            addCriterion("ret_code not like", value, "retCode");
            return (Criteria) this;
        }

        public Criteria andRetCodeIn(List<String> values) {
            addCriterion("ret_code in", values, "retCode");
            return (Criteria) this;
        }

        public Criteria andRetCodeNotIn(List<String> values) {
            addCriterion("ret_code not in", values, "retCode");
            return (Criteria) this;
        }

        public Criteria andRetCodeBetween(String value1, String value2) {
            addCriterion("ret_code between", value1, value2, "retCode");
            return (Criteria) this;
        }

        public Criteria andRetCodeNotBetween(String value1, String value2) {
            addCriterion("ret_code not between", value1, value2, "retCode");
            return (Criteria) this;
        }

        public Criteria andRetMsgIsNull() {
            addCriterion("ret_msg is null");
            return (Criteria) this;
        }

        public Criteria andRetMsgIsNotNull() {
            addCriterion("ret_msg is not null");
            return (Criteria) this;
        }

        public Criteria andRetMsgEqualTo(String value) {
            addCriterion("ret_msg =", value, "retMsg");
            return (Criteria) this;
        }

        public Criteria andRetMsgNotEqualTo(String value) {
            addCriterion("ret_msg <>", value, "retMsg");
            return (Criteria) this;
        }

        public Criteria andRetMsgGreaterThan(String value) {
            addCriterion("ret_msg >", value, "retMsg");
            return (Criteria) this;
        }

        public Criteria andRetMsgGreaterThanOrEqualTo(String value) {
            addCriterion("ret_msg >=", value, "retMsg");
            return (Criteria) this;
        }

        public Criteria andRetMsgLessThan(String value) {
            addCriterion("ret_msg <", value, "retMsg");
            return (Criteria) this;
        }

        public Criteria andRetMsgLessThanOrEqualTo(String value) {
            addCriterion("ret_msg <=", value, "retMsg");
            return (Criteria) this;
        }

        public Criteria andRetMsgLike(String value) {
            addCriterion("ret_msg like", value, "retMsg");
            return (Criteria) this;
        }

        public Criteria andRetMsgNotLike(String value) {
            addCriterion("ret_msg not like", value, "retMsg");
            return (Criteria) this;
        }

        public Criteria andRetMsgIn(List<String> values) {
            addCriterion("ret_msg in", values, "retMsg");
            return (Criteria) this;
        }

        public Criteria andRetMsgNotIn(List<String> values) {
            addCriterion("ret_msg not in", values, "retMsg");
            return (Criteria) this;
        }

        public Criteria andRetMsgBetween(String value1, String value2) {
            addCriterion("ret_msg between", value1, value2, "retMsg");
            return (Criteria) this;
        }

        public Criteria andRetMsgNotBetween(String value1, String value2) {
            addCriterion("ret_msg not between", value1, value2, "retMsg");
            return (Criteria) this;
        }

        public Criteria andErrorMsgIsNull() {
            addCriterion("error_msg is null");
            return (Criteria) this;
        }

        public Criteria andErrorMsgIsNotNull() {
            addCriterion("error_msg is not null");
            return (Criteria) this;
        }

        public Criteria andErrorMsgEqualTo(String value) {
            addCriterion("error_msg =", value, "errorMsg");
            return (Criteria) this;
        }

        public Criteria andErrorMsgNotEqualTo(String value) {
            addCriterion("error_msg <>", value, "errorMsg");
            return (Criteria) this;
        }

        public Criteria andErrorMsgGreaterThan(String value) {
            addCriterion("error_msg >", value, "errorMsg");
            return (Criteria) this;
        }

        public Criteria andErrorMsgGreaterThanOrEqualTo(String value) {
            addCriterion("error_msg >=", value, "errorMsg");
            return (Criteria) this;
        }

        public Criteria andErrorMsgLessThan(String value) {
            addCriterion("error_msg <", value, "errorMsg");
            return (Criteria) this;
        }

        public Criteria andErrorMsgLessThanOrEqualTo(String value) {
            addCriterion("error_msg <=", value, "errorMsg");
            return (Criteria) this;
        }

        public Criteria andErrorMsgLike(String value) {
            addCriterion("error_msg like", value, "errorMsg");
            return (Criteria) this;
        }

        public Criteria andErrorMsgNotLike(String value) {
            addCriterion("error_msg not like", value, "errorMsg");
            return (Criteria) this;
        }

        public Criteria andErrorMsgIn(List<String> values) {
            addCriterion("error_msg in", values, "errorMsg");
            return (Criteria) this;
        }

        public Criteria andErrorMsgNotIn(List<String> values) {
            addCriterion("error_msg not in", values, "errorMsg");
            return (Criteria) this;
        }

        public Criteria andErrorMsgBetween(String value1, String value2) {
            addCriterion("error_msg between", value1, value2, "errorMsg");
            return (Criteria) this;
        }

        public Criteria andErrorMsgNotBetween(String value1, String value2) {
            addCriterion("error_msg not between", value1, value2, "errorMsg");
            return (Criteria) this;
        }

        public Criteria andPageKeyIsNull() {
            addCriterion("page_key is null");
            return (Criteria) this;
        }

        public Criteria andPageKeyIsNotNull() {
            addCriterion("page_key is not null");
            return (Criteria) this;
        }

        public Criteria andPageKeyEqualTo(String value) {
            addCriterion("page_key =", value, "pageKey");
            return (Criteria) this;
        }

        public Criteria andPageKeyNotEqualTo(String value) {
            addCriterion("page_key <>", value, "pageKey");
            return (Criteria) this;
        }

        public Criteria andPageKeyGreaterThan(String value) {
            addCriterion("page_key >", value, "pageKey");
            return (Criteria) this;
        }

        public Criteria andPageKeyGreaterThanOrEqualTo(String value) {
            addCriterion("page_key >=", value, "pageKey");
            return (Criteria) this;
        }

        public Criteria andPageKeyLessThan(String value) {
            addCriterion("page_key <", value, "pageKey");
            return (Criteria) this;
        }

        public Criteria andPageKeyLessThanOrEqualTo(String value) {
            addCriterion("page_key <=", value, "pageKey");
            return (Criteria) this;
        }

        public Criteria andPageKeyLike(String value) {
            addCriterion("page_key like", value, "pageKey");
            return (Criteria) this;
        }

        public Criteria andPageKeyNotLike(String value) {
            addCriterion("page_key not like", value, "pageKey");
            return (Criteria) this;
        }

        public Criteria andPageKeyIn(List<String> values) {
            addCriterion("page_key in", values, "pageKey");
            return (Criteria) this;
        }

        public Criteria andPageKeyNotIn(List<String> values) {
            addCriterion("page_key not in", values, "pageKey");
            return (Criteria) this;
        }

        public Criteria andPageKeyBetween(String value1, String value2) {
            addCriterion("page_key between", value1, value2, "pageKey");
            return (Criteria) this;
        }

        public Criteria andPageKeyNotBetween(String value1, String value2) {
            addCriterion("page_key not between", value1, value2, "pageKey");
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