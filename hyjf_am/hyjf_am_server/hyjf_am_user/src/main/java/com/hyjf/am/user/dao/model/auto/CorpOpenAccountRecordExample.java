package com.hyjf.am.user.dao.model.auto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CorpOpenAccountRecordExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public CorpOpenAccountRecordExample() {
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

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(Integer value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(Integer value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(Integer value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(Integer value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<Integer> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<Integer> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(Integer value1, Integer value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUsernameIsNull() {
            addCriterion("username is null");
            return (Criteria) this;
        }

        public Criteria andUsernameIsNotNull() {
            addCriterion("username is not null");
            return (Criteria) this;
        }

        public Criteria andUsernameEqualTo(String value) {
            addCriterion("username =", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotEqualTo(String value) {
            addCriterion("username <>", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameGreaterThan(String value) {
            addCriterion("username >", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameGreaterThanOrEqualTo(String value) {
            addCriterion("username >=", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLessThan(String value) {
            addCriterion("username <", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLessThanOrEqualTo(String value) {
            addCriterion("username <=", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLike(String value) {
            addCriterion("username like", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotLike(String value) {
            addCriterion("username not like", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameIn(List<String> values) {
            addCriterion("username in", values, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotIn(List<String> values) {
            addCriterion("username not in", values, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameBetween(String value1, String value2) {
            addCriterion("username between", value1, value2, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotBetween(String value1, String value2) {
            addCriterion("username not between", value1, value2, "username");
            return (Criteria) this;
        }

        public Criteria andBusiCodeIsNull() {
            addCriterion("busi_code is null");
            return (Criteria) this;
        }

        public Criteria andBusiCodeIsNotNull() {
            addCriterion("busi_code is not null");
            return (Criteria) this;
        }

        public Criteria andBusiCodeEqualTo(String value) {
            addCriterion("busi_code =", value, "busiCode");
            return (Criteria) this;
        }

        public Criteria andBusiCodeNotEqualTo(String value) {
            addCriterion("busi_code <>", value, "busiCode");
            return (Criteria) this;
        }

        public Criteria andBusiCodeGreaterThan(String value) {
            addCriterion("busi_code >", value, "busiCode");
            return (Criteria) this;
        }

        public Criteria andBusiCodeGreaterThanOrEqualTo(String value) {
            addCriterion("busi_code >=", value, "busiCode");
            return (Criteria) this;
        }

        public Criteria andBusiCodeLessThan(String value) {
            addCriterion("busi_code <", value, "busiCode");
            return (Criteria) this;
        }

        public Criteria andBusiCodeLessThanOrEqualTo(String value) {
            addCriterion("busi_code <=", value, "busiCode");
            return (Criteria) this;
        }

        public Criteria andBusiCodeLike(String value) {
            addCriterion("busi_code like", value, "busiCode");
            return (Criteria) this;
        }

        public Criteria andBusiCodeNotLike(String value) {
            addCriterion("busi_code not like", value, "busiCode");
            return (Criteria) this;
        }

        public Criteria andBusiCodeIn(List<String> values) {
            addCriterion("busi_code in", values, "busiCode");
            return (Criteria) this;
        }

        public Criteria andBusiCodeNotIn(List<String> values) {
            addCriterion("busi_code not in", values, "busiCode");
            return (Criteria) this;
        }

        public Criteria andBusiCodeBetween(String value1, String value2) {
            addCriterion("busi_code between", value1, value2, "busiCode");
            return (Criteria) this;
        }

        public Criteria andBusiCodeNotBetween(String value1, String value2) {
            addCriterion("busi_code not between", value1, value2, "busiCode");
            return (Criteria) this;
        }

        public Criteria andBusiNameIsNull() {
            addCriterion("busi_name is null");
            return (Criteria) this;
        }

        public Criteria andBusiNameIsNotNull() {
            addCriterion("busi_name is not null");
            return (Criteria) this;
        }

        public Criteria andBusiNameEqualTo(String value) {
            addCriterion("busi_name =", value, "busiName");
            return (Criteria) this;
        }

        public Criteria andBusiNameNotEqualTo(String value) {
            addCriterion("busi_name <>", value, "busiName");
            return (Criteria) this;
        }

        public Criteria andBusiNameGreaterThan(String value) {
            addCriterion("busi_name >", value, "busiName");
            return (Criteria) this;
        }

        public Criteria andBusiNameGreaterThanOrEqualTo(String value) {
            addCriterion("busi_name >=", value, "busiName");
            return (Criteria) this;
        }

        public Criteria andBusiNameLessThan(String value) {
            addCriterion("busi_name <", value, "busiName");
            return (Criteria) this;
        }

        public Criteria andBusiNameLessThanOrEqualTo(String value) {
            addCriterion("busi_name <=", value, "busiName");
            return (Criteria) this;
        }

        public Criteria andBusiNameLike(String value) {
            addCriterion("busi_name like", value, "busiName");
            return (Criteria) this;
        }

        public Criteria andBusiNameNotLike(String value) {
            addCriterion("busi_name not like", value, "busiName");
            return (Criteria) this;
        }

        public Criteria andBusiNameIn(List<String> values) {
            addCriterion("busi_name in", values, "busiName");
            return (Criteria) this;
        }

        public Criteria andBusiNameNotIn(List<String> values) {
            addCriterion("busi_name not in", values, "busiName");
            return (Criteria) this;
        }

        public Criteria andBusiNameBetween(String value1, String value2) {
            addCriterion("busi_name between", value1, value2, "busiName");
            return (Criteria) this;
        }

        public Criteria andBusiNameNotBetween(String value1, String value2) {
            addCriterion("busi_name not between", value1, value2, "busiName");
            return (Criteria) this;
        }

        public Criteria andGuarTypeIsNull() {
            addCriterion("guar_type is null");
            return (Criteria) this;
        }

        public Criteria andGuarTypeIsNotNull() {
            addCriterion("guar_type is not null");
            return (Criteria) this;
        }

        public Criteria andGuarTypeEqualTo(String value) {
            addCriterion("guar_type =", value, "guarType");
            return (Criteria) this;
        }

        public Criteria andGuarTypeNotEqualTo(String value) {
            addCriterion("guar_type <>", value, "guarType");
            return (Criteria) this;
        }

        public Criteria andGuarTypeGreaterThan(String value) {
            addCriterion("guar_type >", value, "guarType");
            return (Criteria) this;
        }

        public Criteria andGuarTypeGreaterThanOrEqualTo(String value) {
            addCriterion("guar_type >=", value, "guarType");
            return (Criteria) this;
        }

        public Criteria andGuarTypeLessThan(String value) {
            addCriterion("guar_type <", value, "guarType");
            return (Criteria) this;
        }

        public Criteria andGuarTypeLessThanOrEqualTo(String value) {
            addCriterion("guar_type <=", value, "guarType");
            return (Criteria) this;
        }

        public Criteria andGuarTypeLike(String value) {
            addCriterion("guar_type like", value, "guarType");
            return (Criteria) this;
        }

        public Criteria andGuarTypeNotLike(String value) {
            addCriterion("guar_type not like", value, "guarType");
            return (Criteria) this;
        }

        public Criteria andGuarTypeIn(List<String> values) {
            addCriterion("guar_type in", values, "guarType");
            return (Criteria) this;
        }

        public Criteria andGuarTypeNotIn(List<String> values) {
            addCriterion("guar_type not in", values, "guarType");
            return (Criteria) this;
        }

        public Criteria andGuarTypeBetween(String value1, String value2) {
            addCriterion("guar_type between", value1, value2, "guarType");
            return (Criteria) this;
        }

        public Criteria andGuarTypeNotBetween(String value1, String value2) {
            addCriterion("guar_type not between", value1, value2, "guarType");
            return (Criteria) this;
        }

        public Criteria andOpenBankIdIsNull() {
            addCriterion("open_bank_id is null");
            return (Criteria) this;
        }

        public Criteria andOpenBankIdIsNotNull() {
            addCriterion("open_bank_id is not null");
            return (Criteria) this;
        }

        public Criteria andOpenBankIdEqualTo(String value) {
            addCriterion("open_bank_id =", value, "openBankId");
            return (Criteria) this;
        }

        public Criteria andOpenBankIdNotEqualTo(String value) {
            addCriterion("open_bank_id <>", value, "openBankId");
            return (Criteria) this;
        }

        public Criteria andOpenBankIdGreaterThan(String value) {
            addCriterion("open_bank_id >", value, "openBankId");
            return (Criteria) this;
        }

        public Criteria andOpenBankIdGreaterThanOrEqualTo(String value) {
            addCriterion("open_bank_id >=", value, "openBankId");
            return (Criteria) this;
        }

        public Criteria andOpenBankIdLessThan(String value) {
            addCriterion("open_bank_id <", value, "openBankId");
            return (Criteria) this;
        }

        public Criteria andOpenBankIdLessThanOrEqualTo(String value) {
            addCriterion("open_bank_id <=", value, "openBankId");
            return (Criteria) this;
        }

        public Criteria andOpenBankIdLike(String value) {
            addCriterion("open_bank_id like", value, "openBankId");
            return (Criteria) this;
        }

        public Criteria andOpenBankIdNotLike(String value) {
            addCriterion("open_bank_id not like", value, "openBankId");
            return (Criteria) this;
        }

        public Criteria andOpenBankIdIn(List<String> values) {
            addCriterion("open_bank_id in", values, "openBankId");
            return (Criteria) this;
        }

        public Criteria andOpenBankIdNotIn(List<String> values) {
            addCriterion("open_bank_id not in", values, "openBankId");
            return (Criteria) this;
        }

        public Criteria andOpenBankIdBetween(String value1, String value2) {
            addCriterion("open_bank_id between", value1, value2, "openBankId");
            return (Criteria) this;
        }

        public Criteria andOpenBankIdNotBetween(String value1, String value2) {
            addCriterion("open_bank_id not between", value1, value2, "openBankId");
            return (Criteria) this;
        }

        public Criteria andCardIdIsNull() {
            addCriterion("card_id is null");
            return (Criteria) this;
        }

        public Criteria andCardIdIsNotNull() {
            addCriterion("card_id is not null");
            return (Criteria) this;
        }

        public Criteria andCardIdEqualTo(String value) {
            addCriterion("card_id =", value, "cardId");
            return (Criteria) this;
        }

        public Criteria andCardIdNotEqualTo(String value) {
            addCriterion("card_id <>", value, "cardId");
            return (Criteria) this;
        }

        public Criteria andCardIdGreaterThan(String value) {
            addCriterion("card_id >", value, "cardId");
            return (Criteria) this;
        }

        public Criteria andCardIdGreaterThanOrEqualTo(String value) {
            addCriterion("card_id >=", value, "cardId");
            return (Criteria) this;
        }

        public Criteria andCardIdLessThan(String value) {
            addCriterion("card_id <", value, "cardId");
            return (Criteria) this;
        }

        public Criteria andCardIdLessThanOrEqualTo(String value) {
            addCriterion("card_id <=", value, "cardId");
            return (Criteria) this;
        }

        public Criteria andCardIdLike(String value) {
            addCriterion("card_id like", value, "cardId");
            return (Criteria) this;
        }

        public Criteria andCardIdNotLike(String value) {
            addCriterion("card_id not like", value, "cardId");
            return (Criteria) this;
        }

        public Criteria andCardIdIn(List<String> values) {
            addCriterion("card_id in", values, "cardId");
            return (Criteria) this;
        }

        public Criteria andCardIdNotIn(List<String> values) {
            addCriterion("card_id not in", values, "cardId");
            return (Criteria) this;
        }

        public Criteria andCardIdBetween(String value1, String value2) {
            addCriterion("card_id between", value1, value2, "cardId");
            return (Criteria) this;
        }

        public Criteria andCardIdNotBetween(String value1, String value2) {
            addCriterion("card_id not between", value1, value2, "cardId");
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

        public Criteria andIsBankIsNull() {
            addCriterion("is_bank is null");
            return (Criteria) this;
        }

        public Criteria andIsBankIsNotNull() {
            addCriterion("is_bank is not null");
            return (Criteria) this;
        }

        public Criteria andIsBankEqualTo(Integer value) {
            addCriterion("is_bank =", value, "isBank");
            return (Criteria) this;
        }

        public Criteria andIsBankNotEqualTo(Integer value) {
            addCriterion("is_bank <>", value, "isBank");
            return (Criteria) this;
        }

        public Criteria andIsBankGreaterThan(Integer value) {
            addCriterion("is_bank >", value, "isBank");
            return (Criteria) this;
        }

        public Criteria andIsBankGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_bank >=", value, "isBank");
            return (Criteria) this;
        }

        public Criteria andIsBankLessThan(Integer value) {
            addCriterion("is_bank <", value, "isBank");
            return (Criteria) this;
        }

        public Criteria andIsBankLessThanOrEqualTo(Integer value) {
            addCriterion("is_bank <=", value, "isBank");
            return (Criteria) this;
        }

        public Criteria andIsBankIn(List<Integer> values) {
            addCriterion("is_bank in", values, "isBank");
            return (Criteria) this;
        }

        public Criteria andIsBankNotIn(List<Integer> values) {
            addCriterion("is_bank not in", values, "isBank");
            return (Criteria) this;
        }

        public Criteria andIsBankBetween(Integer value1, Integer value2) {
            addCriterion("is_bank between", value1, value2, "isBank");
            return (Criteria) this;
        }

        public Criteria andIsBankNotBetween(Integer value1, Integer value2) {
            addCriterion("is_bank not between", value1, value2, "isBank");
            return (Criteria) this;
        }

        public Criteria andAddTimeIsNull() {
            addCriterion("add_time is null");
            return (Criteria) this;
        }

        public Criteria andAddTimeIsNotNull() {
            addCriterion("add_time is not null");
            return (Criteria) this;
        }

        public Criteria andAddTimeEqualTo(Date value) {
            addCriterion("add_time =", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeNotEqualTo(Date value) {
            addCriterion("add_time <>", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeGreaterThan(Date value) {
            addCriterion("add_time >", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("add_time >=", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeLessThan(Date value) {
            addCriterion("add_time <", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeLessThanOrEqualTo(Date value) {
            addCriterion("add_time <=", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeIn(List<Date> values) {
            addCriterion("add_time in", values, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeNotIn(List<Date> values) {
            addCriterion("add_time not in", values, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeBetween(Date value1, Date value2) {
            addCriterion("add_time between", value1, value2, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeNotBetween(Date value1, Date value2) {
            addCriterion("add_time not between", value1, value2, "addTime");
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

        public Criteria andCardTypeIsNull() {
            addCriterion("card_type is null");
            return (Criteria) this;
        }

        public Criteria andCardTypeIsNotNull() {
            addCriterion("card_type is not null");
            return (Criteria) this;
        }

        public Criteria andCardTypeEqualTo(Integer value) {
            addCriterion("card_type =", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeNotEqualTo(Integer value) {
            addCriterion("card_type <>", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeGreaterThan(Integer value) {
            addCriterion("card_type >", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("card_type >=", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeLessThan(Integer value) {
            addCriterion("card_type <", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeLessThanOrEqualTo(Integer value) {
            addCriterion("card_type <=", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeIn(List<Integer> values) {
            addCriterion("card_type in", values, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeNotIn(List<Integer> values) {
            addCriterion("card_type not in", values, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeBetween(Integer value1, Integer value2) {
            addCriterion("card_type between", value1, value2, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("card_type not between", value1, value2, "cardType");
            return (Criteria) this;
        }

        public Criteria andTaxRegistrationCodeIsNull() {
            addCriterion("tax_registration_code is null");
            return (Criteria) this;
        }

        public Criteria andTaxRegistrationCodeIsNotNull() {
            addCriterion("tax_registration_code is not null");
            return (Criteria) this;
        }

        public Criteria andTaxRegistrationCodeEqualTo(String value) {
            addCriterion("tax_registration_code =", value, "taxRegistrationCode");
            return (Criteria) this;
        }

        public Criteria andTaxRegistrationCodeNotEqualTo(String value) {
            addCriterion("tax_registration_code <>", value, "taxRegistrationCode");
            return (Criteria) this;
        }

        public Criteria andTaxRegistrationCodeGreaterThan(String value) {
            addCriterion("tax_registration_code >", value, "taxRegistrationCode");
            return (Criteria) this;
        }

        public Criteria andTaxRegistrationCodeGreaterThanOrEqualTo(String value) {
            addCriterion("tax_registration_code >=", value, "taxRegistrationCode");
            return (Criteria) this;
        }

        public Criteria andTaxRegistrationCodeLessThan(String value) {
            addCriterion("tax_registration_code <", value, "taxRegistrationCode");
            return (Criteria) this;
        }

        public Criteria andTaxRegistrationCodeLessThanOrEqualTo(String value) {
            addCriterion("tax_registration_code <=", value, "taxRegistrationCode");
            return (Criteria) this;
        }

        public Criteria andTaxRegistrationCodeLike(String value) {
            addCriterion("tax_registration_code like", value, "taxRegistrationCode");
            return (Criteria) this;
        }

        public Criteria andTaxRegistrationCodeNotLike(String value) {
            addCriterion("tax_registration_code not like", value, "taxRegistrationCode");
            return (Criteria) this;
        }

        public Criteria andTaxRegistrationCodeIn(List<String> values) {
            addCriterion("tax_registration_code in", values, "taxRegistrationCode");
            return (Criteria) this;
        }

        public Criteria andTaxRegistrationCodeNotIn(List<String> values) {
            addCriterion("tax_registration_code not in", values, "taxRegistrationCode");
            return (Criteria) this;
        }

        public Criteria andTaxRegistrationCodeBetween(String value1, String value2) {
            addCriterion("tax_registration_code between", value1, value2, "taxRegistrationCode");
            return (Criteria) this;
        }

        public Criteria andTaxRegistrationCodeNotBetween(String value1, String value2) {
            addCriterion("tax_registration_code not between", value1, value2, "taxRegistrationCode");
            return (Criteria) this;
        }

        public Criteria andBuseNoIsNull() {
            addCriterion("buse_no is null");
            return (Criteria) this;
        }

        public Criteria andBuseNoIsNotNull() {
            addCriterion("buse_no is not null");
            return (Criteria) this;
        }

        public Criteria andBuseNoEqualTo(String value) {
            addCriterion("buse_no =", value, "buseNo");
            return (Criteria) this;
        }

        public Criteria andBuseNoNotEqualTo(String value) {
            addCriterion("buse_no <>", value, "buseNo");
            return (Criteria) this;
        }

        public Criteria andBuseNoGreaterThan(String value) {
            addCriterion("buse_no >", value, "buseNo");
            return (Criteria) this;
        }

        public Criteria andBuseNoGreaterThanOrEqualTo(String value) {
            addCriterion("buse_no >=", value, "buseNo");
            return (Criteria) this;
        }

        public Criteria andBuseNoLessThan(String value) {
            addCriterion("buse_no <", value, "buseNo");
            return (Criteria) this;
        }

        public Criteria andBuseNoLessThanOrEqualTo(String value) {
            addCriterion("buse_no <=", value, "buseNo");
            return (Criteria) this;
        }

        public Criteria andBuseNoLike(String value) {
            addCriterion("buse_no like", value, "buseNo");
            return (Criteria) this;
        }

        public Criteria andBuseNoNotLike(String value) {
            addCriterion("buse_no not like", value, "buseNo");
            return (Criteria) this;
        }

        public Criteria andBuseNoIn(List<String> values) {
            addCriterion("buse_no in", values, "buseNo");
            return (Criteria) this;
        }

        public Criteria andBuseNoNotIn(List<String> values) {
            addCriterion("buse_no not in", values, "buseNo");
            return (Criteria) this;
        }

        public Criteria andBuseNoBetween(String value1, String value2) {
            addCriterion("buse_no between", value1, value2, "buseNo");
            return (Criteria) this;
        }

        public Criteria andBuseNoNotBetween(String value1, String value2) {
            addCriterion("buse_no not between", value1, value2, "buseNo");
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