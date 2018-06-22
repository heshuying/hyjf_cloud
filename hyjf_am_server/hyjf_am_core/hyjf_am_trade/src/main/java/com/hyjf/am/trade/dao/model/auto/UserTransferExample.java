package com.hyjf.am.trade.dao.model.auto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserTransferExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public UserTransferExample() {
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

        public Criteria andOrderIdIsNull() {
            addCriterion("order_id is null");
            return (Criteria) this;
        }

        public Criteria andOrderIdIsNotNull() {
            addCriterion("order_id is not null");
            return (Criteria) this;
        }

        public Criteria andOrderIdEqualTo(String value) {
            addCriterion("order_id =", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotEqualTo(String value) {
            addCriterion("order_id <>", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThan(String value) {
            addCriterion("order_id >", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThanOrEqualTo(String value) {
            addCriterion("order_id >=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThan(String value) {
            addCriterion("order_id <", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThanOrEqualTo(String value) {
            addCriterion("order_id <=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLike(String value) {
            addCriterion("order_id like", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotLike(String value) {
            addCriterion("order_id not like", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdIn(List<String> values) {
            addCriterion("order_id in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotIn(List<String> values) {
            addCriterion("order_id not in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdBetween(String value1, String value2) {
            addCriterion("order_id between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotBetween(String value1, String value2) {
            addCriterion("order_id not between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andOutUserIdIsNull() {
            addCriterion("out_user_id is null");
            return (Criteria) this;
        }

        public Criteria andOutUserIdIsNotNull() {
            addCriterion("out_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andOutUserIdEqualTo(Integer value) {
            addCriterion("out_user_id =", value, "outUserId");
            return (Criteria) this;
        }

        public Criteria andOutUserIdNotEqualTo(Integer value) {
            addCriterion("out_user_id <>", value, "outUserId");
            return (Criteria) this;
        }

        public Criteria andOutUserIdGreaterThan(Integer value) {
            addCriterion("out_user_id >", value, "outUserId");
            return (Criteria) this;
        }

        public Criteria andOutUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("out_user_id >=", value, "outUserId");
            return (Criteria) this;
        }

        public Criteria andOutUserIdLessThan(Integer value) {
            addCriterion("out_user_id <", value, "outUserId");
            return (Criteria) this;
        }

        public Criteria andOutUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("out_user_id <=", value, "outUserId");
            return (Criteria) this;
        }

        public Criteria andOutUserIdIn(List<Integer> values) {
            addCriterion("out_user_id in", values, "outUserId");
            return (Criteria) this;
        }

        public Criteria andOutUserIdNotIn(List<Integer> values) {
            addCriterion("out_user_id not in", values, "outUserId");
            return (Criteria) this;
        }

        public Criteria andOutUserIdBetween(Integer value1, Integer value2) {
            addCriterion("out_user_id between", value1, value2, "outUserId");
            return (Criteria) this;
        }

        public Criteria andOutUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("out_user_id not between", value1, value2, "outUserId");
            return (Criteria) this;
        }

        public Criteria andOutUserNameIsNull() {
            addCriterion("out_user_name is null");
            return (Criteria) this;
        }

        public Criteria andOutUserNameIsNotNull() {
            addCriterion("out_user_name is not null");
            return (Criteria) this;
        }

        public Criteria andOutUserNameEqualTo(String value) {
            addCriterion("out_user_name =", value, "outUserName");
            return (Criteria) this;
        }

        public Criteria andOutUserNameNotEqualTo(String value) {
            addCriterion("out_user_name <>", value, "outUserName");
            return (Criteria) this;
        }

        public Criteria andOutUserNameGreaterThan(String value) {
            addCriterion("out_user_name >", value, "outUserName");
            return (Criteria) this;
        }

        public Criteria andOutUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("out_user_name >=", value, "outUserName");
            return (Criteria) this;
        }

        public Criteria andOutUserNameLessThan(String value) {
            addCriterion("out_user_name <", value, "outUserName");
            return (Criteria) this;
        }

        public Criteria andOutUserNameLessThanOrEqualTo(String value) {
            addCriterion("out_user_name <=", value, "outUserName");
            return (Criteria) this;
        }

        public Criteria andOutUserNameLike(String value) {
            addCriterion("out_user_name like", value, "outUserName");
            return (Criteria) this;
        }

        public Criteria andOutUserNameNotLike(String value) {
            addCriterion("out_user_name not like", value, "outUserName");
            return (Criteria) this;
        }

        public Criteria andOutUserNameIn(List<String> values) {
            addCriterion("out_user_name in", values, "outUserName");
            return (Criteria) this;
        }

        public Criteria andOutUserNameNotIn(List<String> values) {
            addCriterion("out_user_name not in", values, "outUserName");
            return (Criteria) this;
        }

        public Criteria andOutUserNameBetween(String value1, String value2) {
            addCriterion("out_user_name between", value1, value2, "outUserName");
            return (Criteria) this;
        }

        public Criteria andOutUserNameNotBetween(String value1, String value2) {
            addCriterion("out_user_name not between", value1, value2, "outUserName");
            return (Criteria) this;
        }

        public Criteria andInUserIdIsNull() {
            addCriterion("in_user_id is null");
            return (Criteria) this;
        }

        public Criteria andInUserIdIsNotNull() {
            addCriterion("in_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andInUserIdEqualTo(Integer value) {
            addCriterion("in_user_id =", value, "inUserId");
            return (Criteria) this;
        }

        public Criteria andInUserIdNotEqualTo(Integer value) {
            addCriterion("in_user_id <>", value, "inUserId");
            return (Criteria) this;
        }

        public Criteria andInUserIdGreaterThan(Integer value) {
            addCriterion("in_user_id >", value, "inUserId");
            return (Criteria) this;
        }

        public Criteria andInUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("in_user_id >=", value, "inUserId");
            return (Criteria) this;
        }

        public Criteria andInUserIdLessThan(Integer value) {
            addCriterion("in_user_id <", value, "inUserId");
            return (Criteria) this;
        }

        public Criteria andInUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("in_user_id <=", value, "inUserId");
            return (Criteria) this;
        }

        public Criteria andInUserIdIn(List<Integer> values) {
            addCriterion("in_user_id in", values, "inUserId");
            return (Criteria) this;
        }

        public Criteria andInUserIdNotIn(List<Integer> values) {
            addCriterion("in_user_id not in", values, "inUserId");
            return (Criteria) this;
        }

        public Criteria andInUserIdBetween(Integer value1, Integer value2) {
            addCriterion("in_user_id between", value1, value2, "inUserId");
            return (Criteria) this;
        }

        public Criteria andInUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("in_user_id not between", value1, value2, "inUserId");
            return (Criteria) this;
        }

        public Criteria andInUserNameIsNull() {
            addCriterion("in_user_name is null");
            return (Criteria) this;
        }

        public Criteria andInUserNameIsNotNull() {
            addCriterion("in_user_name is not null");
            return (Criteria) this;
        }

        public Criteria andInUserNameEqualTo(String value) {
            addCriterion("in_user_name =", value, "inUserName");
            return (Criteria) this;
        }

        public Criteria andInUserNameNotEqualTo(String value) {
            addCriterion("in_user_name <>", value, "inUserName");
            return (Criteria) this;
        }

        public Criteria andInUserNameGreaterThan(String value) {
            addCriterion("in_user_name >", value, "inUserName");
            return (Criteria) this;
        }

        public Criteria andInUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("in_user_name >=", value, "inUserName");
            return (Criteria) this;
        }

        public Criteria andInUserNameLessThan(String value) {
            addCriterion("in_user_name <", value, "inUserName");
            return (Criteria) this;
        }

        public Criteria andInUserNameLessThanOrEqualTo(String value) {
            addCriterion("in_user_name <=", value, "inUserName");
            return (Criteria) this;
        }

        public Criteria andInUserNameLike(String value) {
            addCriterion("in_user_name like", value, "inUserName");
            return (Criteria) this;
        }

        public Criteria andInUserNameNotLike(String value) {
            addCriterion("in_user_name not like", value, "inUserName");
            return (Criteria) this;
        }

        public Criteria andInUserNameIn(List<String> values) {
            addCriterion("in_user_name in", values, "inUserName");
            return (Criteria) this;
        }

        public Criteria andInUserNameNotIn(List<String> values) {
            addCriterion("in_user_name not in", values, "inUserName");
            return (Criteria) this;
        }

        public Criteria andInUserNameBetween(String value1, String value2) {
            addCriterion("in_user_name between", value1, value2, "inUserName");
            return (Criteria) this;
        }

        public Criteria andInUserNameNotBetween(String value1, String value2) {
            addCriterion("in_user_name not between", value1, value2, "inUserName");
            return (Criteria) this;
        }

        public Criteria andTransferAmountIsNull() {
            addCriterion("transfer_amount is null");
            return (Criteria) this;
        }

        public Criteria andTransferAmountIsNotNull() {
            addCriterion("transfer_amount is not null");
            return (Criteria) this;
        }

        public Criteria andTransferAmountEqualTo(BigDecimal value) {
            addCriterion("transfer_amount =", value, "transferAmount");
            return (Criteria) this;
        }

        public Criteria andTransferAmountNotEqualTo(BigDecimal value) {
            addCriterion("transfer_amount <>", value, "transferAmount");
            return (Criteria) this;
        }

        public Criteria andTransferAmountGreaterThan(BigDecimal value) {
            addCriterion("transfer_amount >", value, "transferAmount");
            return (Criteria) this;
        }

        public Criteria andTransferAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("transfer_amount >=", value, "transferAmount");
            return (Criteria) this;
        }

        public Criteria andTransferAmountLessThan(BigDecimal value) {
            addCriterion("transfer_amount <", value, "transferAmount");
            return (Criteria) this;
        }

        public Criteria andTransferAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("transfer_amount <=", value, "transferAmount");
            return (Criteria) this;
        }

        public Criteria andTransferAmountIn(List<BigDecimal> values) {
            addCriterion("transfer_amount in", values, "transferAmount");
            return (Criteria) this;
        }

        public Criteria andTransferAmountNotIn(List<BigDecimal> values) {
            addCriterion("transfer_amount not in", values, "transferAmount");
            return (Criteria) this;
        }

        public Criteria andTransferAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("transfer_amount between", value1, value2, "transferAmount");
            return (Criteria) this;
        }

        public Criteria andTransferAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("transfer_amount not between", value1, value2, "transferAmount");
            return (Criteria) this;
        }

        public Criteria andTransferUrlIsNull() {
            addCriterion("transfer_url is null");
            return (Criteria) this;
        }

        public Criteria andTransferUrlIsNotNull() {
            addCriterion("transfer_url is not null");
            return (Criteria) this;
        }

        public Criteria andTransferUrlEqualTo(String value) {
            addCriterion("transfer_url =", value, "transferUrl");
            return (Criteria) this;
        }

        public Criteria andTransferUrlNotEqualTo(String value) {
            addCriterion("transfer_url <>", value, "transferUrl");
            return (Criteria) this;
        }

        public Criteria andTransferUrlGreaterThan(String value) {
            addCriterion("transfer_url >", value, "transferUrl");
            return (Criteria) this;
        }

        public Criteria andTransferUrlGreaterThanOrEqualTo(String value) {
            addCriterion("transfer_url >=", value, "transferUrl");
            return (Criteria) this;
        }

        public Criteria andTransferUrlLessThan(String value) {
            addCriterion("transfer_url <", value, "transferUrl");
            return (Criteria) this;
        }

        public Criteria andTransferUrlLessThanOrEqualTo(String value) {
            addCriterion("transfer_url <=", value, "transferUrl");
            return (Criteria) this;
        }

        public Criteria andTransferUrlLike(String value) {
            addCriterion("transfer_url like", value, "transferUrl");
            return (Criteria) this;
        }

        public Criteria andTransferUrlNotLike(String value) {
            addCriterion("transfer_url not like", value, "transferUrl");
            return (Criteria) this;
        }

        public Criteria andTransferUrlIn(List<String> values) {
            addCriterion("transfer_url in", values, "transferUrl");
            return (Criteria) this;
        }

        public Criteria andTransferUrlNotIn(List<String> values) {
            addCriterion("transfer_url not in", values, "transferUrl");
            return (Criteria) this;
        }

        public Criteria andTransferUrlBetween(String value1, String value2) {
            addCriterion("transfer_url between", value1, value2, "transferUrl");
            return (Criteria) this;
        }

        public Criteria andTransferUrlNotBetween(String value1, String value2) {
            addCriterion("transfer_url not between", value1, value2, "transferUrl");
            return (Criteria) this;
        }

        public Criteria andTransferTimeIsNull() {
            addCriterion("transfer_time is null");
            return (Criteria) this;
        }

        public Criteria andTransferTimeIsNotNull() {
            addCriterion("transfer_time is not null");
            return (Criteria) this;
        }

        public Criteria andTransferTimeEqualTo(Date value) {
            addCriterion("transfer_time =", value, "transferTime");
            return (Criteria) this;
        }

        public Criteria andTransferTimeNotEqualTo(Date value) {
            addCriterion("transfer_time <>", value, "transferTime");
            return (Criteria) this;
        }

        public Criteria andTransferTimeGreaterThan(Date value) {
            addCriterion("transfer_time >", value, "transferTime");
            return (Criteria) this;
        }

        public Criteria andTransferTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("transfer_time >=", value, "transferTime");
            return (Criteria) this;
        }

        public Criteria andTransferTimeLessThan(Date value) {
            addCriterion("transfer_time <", value, "transferTime");
            return (Criteria) this;
        }

        public Criteria andTransferTimeLessThanOrEqualTo(Date value) {
            addCriterion("transfer_time <=", value, "transferTime");
            return (Criteria) this;
        }

        public Criteria andTransferTimeIn(List<Date> values) {
            addCriterion("transfer_time in", values, "transferTime");
            return (Criteria) this;
        }

        public Criteria andTransferTimeNotIn(List<Date> values) {
            addCriterion("transfer_time not in", values, "transferTime");
            return (Criteria) this;
        }

        public Criteria andTransferTimeBetween(Date value1, Date value2) {
            addCriterion("transfer_time between", value1, value2, "transferTime");
            return (Criteria) this;
        }

        public Criteria andTransferTimeNotBetween(Date value1, Date value2) {
            addCriterion("transfer_time not between", value1, value2, "transferTime");
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

        public Criteria andTransferTypeIsNull() {
            addCriterion("transfer_type is null");
            return (Criteria) this;
        }

        public Criteria andTransferTypeIsNotNull() {
            addCriterion("transfer_type is not null");
            return (Criteria) this;
        }

        public Criteria andTransferTypeEqualTo(Integer value) {
            addCriterion("transfer_type =", value, "transferType");
            return (Criteria) this;
        }

        public Criteria andTransferTypeNotEqualTo(Integer value) {
            addCriterion("transfer_type <>", value, "transferType");
            return (Criteria) this;
        }

        public Criteria andTransferTypeGreaterThan(Integer value) {
            addCriterion("transfer_type >", value, "transferType");
            return (Criteria) this;
        }

        public Criteria andTransferTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("transfer_type >=", value, "transferType");
            return (Criteria) this;
        }

        public Criteria andTransferTypeLessThan(Integer value) {
            addCriterion("transfer_type <", value, "transferType");
            return (Criteria) this;
        }

        public Criteria andTransferTypeLessThanOrEqualTo(Integer value) {
            addCriterion("transfer_type <=", value, "transferType");
            return (Criteria) this;
        }

        public Criteria andTransferTypeIn(List<Integer> values) {
            addCriterion("transfer_type in", values, "transferType");
            return (Criteria) this;
        }

        public Criteria andTransferTypeNotIn(List<Integer> values) {
            addCriterion("transfer_type not in", values, "transferType");
            return (Criteria) this;
        }

        public Criteria andTransferTypeBetween(Integer value1, Integer value2) {
            addCriterion("transfer_type between", value1, value2, "transferType");
            return (Criteria) this;
        }

        public Criteria andTransferTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("transfer_type not between", value1, value2, "transferType");
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

        public Criteria andReconciliationIdIsNull() {
            addCriterion("reconciliation_id is null");
            return (Criteria) this;
        }

        public Criteria andReconciliationIdIsNotNull() {
            addCriterion("reconciliation_id is not null");
            return (Criteria) this;
        }

        public Criteria andReconciliationIdEqualTo(String value) {
            addCriterion("reconciliation_id =", value, "reconciliationId");
            return (Criteria) this;
        }

        public Criteria andReconciliationIdNotEqualTo(String value) {
            addCriterion("reconciliation_id <>", value, "reconciliationId");
            return (Criteria) this;
        }

        public Criteria andReconciliationIdGreaterThan(String value) {
            addCriterion("reconciliation_id >", value, "reconciliationId");
            return (Criteria) this;
        }

        public Criteria andReconciliationIdGreaterThanOrEqualTo(String value) {
            addCriterion("reconciliation_id >=", value, "reconciliationId");
            return (Criteria) this;
        }

        public Criteria andReconciliationIdLessThan(String value) {
            addCriterion("reconciliation_id <", value, "reconciliationId");
            return (Criteria) this;
        }

        public Criteria andReconciliationIdLessThanOrEqualTo(String value) {
            addCriterion("reconciliation_id <=", value, "reconciliationId");
            return (Criteria) this;
        }

        public Criteria andReconciliationIdLike(String value) {
            addCriterion("reconciliation_id like", value, "reconciliationId");
            return (Criteria) this;
        }

        public Criteria andReconciliationIdNotLike(String value) {
            addCriterion("reconciliation_id not like", value, "reconciliationId");
            return (Criteria) this;
        }

        public Criteria andReconciliationIdIn(List<String> values) {
            addCriterion("reconciliation_id in", values, "reconciliationId");
            return (Criteria) this;
        }

        public Criteria andReconciliationIdNotIn(List<String> values) {
            addCriterion("reconciliation_id not in", values, "reconciliationId");
            return (Criteria) this;
        }

        public Criteria andReconciliationIdBetween(String value1, String value2) {
            addCriterion("reconciliation_id between", value1, value2, "reconciliationId");
            return (Criteria) this;
        }

        public Criteria andReconciliationIdNotBetween(String value1, String value2) {
            addCriterion("reconciliation_id not between", value1, value2, "reconciliationId");
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