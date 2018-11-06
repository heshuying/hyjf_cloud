package com.hyjf.am.config.dao.model.auto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HjhUserAuthConfigLogExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public HjhUserAuthConfigLogExample() {
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

        public Criteria andAuthConfigIdIsNull() {
            addCriterion("auth_config_id is null");
            return (Criteria) this;
        }

        public Criteria andAuthConfigIdIsNotNull() {
            addCriterion("auth_config_id is not null");
            return (Criteria) this;
        }

        public Criteria andAuthConfigIdEqualTo(Integer value) {
            addCriterion("auth_config_id =", value, "authConfigId");
            return (Criteria) this;
        }

        public Criteria andAuthConfigIdNotEqualTo(Integer value) {
            addCriterion("auth_config_id <>", value, "authConfigId");
            return (Criteria) this;
        }

        public Criteria andAuthConfigIdGreaterThan(Integer value) {
            addCriterion("auth_config_id >", value, "authConfigId");
            return (Criteria) this;
        }

        public Criteria andAuthConfigIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("auth_config_id >=", value, "authConfigId");
            return (Criteria) this;
        }

        public Criteria andAuthConfigIdLessThan(Integer value) {
            addCriterion("auth_config_id <", value, "authConfigId");
            return (Criteria) this;
        }

        public Criteria andAuthConfigIdLessThanOrEqualTo(Integer value) {
            addCriterion("auth_config_id <=", value, "authConfigId");
            return (Criteria) this;
        }

        public Criteria andAuthConfigIdIn(List<Integer> values) {
            addCriterion("auth_config_id in", values, "authConfigId");
            return (Criteria) this;
        }

        public Criteria andAuthConfigIdNotIn(List<Integer> values) {
            addCriterion("auth_config_id not in", values, "authConfigId");
            return (Criteria) this;
        }

        public Criteria andAuthConfigIdBetween(Integer value1, Integer value2) {
            addCriterion("auth_config_id between", value1, value2, "authConfigId");
            return (Criteria) this;
        }

        public Criteria andAuthConfigIdNotBetween(Integer value1, Integer value2) {
            addCriterion("auth_config_id not between", value1, value2, "authConfigId");
            return (Criteria) this;
        }

        public Criteria andAuthTypeIsNull() {
            addCriterion("auth_type is null");
            return (Criteria) this;
        }

        public Criteria andAuthTypeIsNotNull() {
            addCriterion("auth_type is not null");
            return (Criteria) this;
        }

        public Criteria andAuthTypeEqualTo(Integer value) {
            addCriterion("auth_type =", value, "authType");
            return (Criteria) this;
        }

        public Criteria andAuthTypeNotEqualTo(Integer value) {
            addCriterion("auth_type <>", value, "authType");
            return (Criteria) this;
        }

        public Criteria andAuthTypeGreaterThan(Integer value) {
            addCriterion("auth_type >", value, "authType");
            return (Criteria) this;
        }

        public Criteria andAuthTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("auth_type >=", value, "authType");
            return (Criteria) this;
        }

        public Criteria andAuthTypeLessThan(Integer value) {
            addCriterion("auth_type <", value, "authType");
            return (Criteria) this;
        }

        public Criteria andAuthTypeLessThanOrEqualTo(Integer value) {
            addCriterion("auth_type <=", value, "authType");
            return (Criteria) this;
        }

        public Criteria andAuthTypeIn(List<Integer> values) {
            addCriterion("auth_type in", values, "authType");
            return (Criteria) this;
        }

        public Criteria andAuthTypeNotIn(List<Integer> values) {
            addCriterion("auth_type not in", values, "authType");
            return (Criteria) this;
        }

        public Criteria andAuthTypeBetween(Integer value1, Integer value2) {
            addCriterion("auth_type between", value1, value2, "authType");
            return (Criteria) this;
        }

        public Criteria andAuthTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("auth_type not between", value1, value2, "authType");
            return (Criteria) this;
        }

        public Criteria andPersonalMaxAmountIsNull() {
            addCriterion("personal_max_amount is null");
            return (Criteria) this;
        }

        public Criteria andPersonalMaxAmountIsNotNull() {
            addCriterion("personal_max_amount is not null");
            return (Criteria) this;
        }

        public Criteria andPersonalMaxAmountEqualTo(Integer value) {
            addCriterion("personal_max_amount =", value, "personalMaxAmount");
            return (Criteria) this;
        }

        public Criteria andPersonalMaxAmountNotEqualTo(Integer value) {
            addCriterion("personal_max_amount <>", value, "personalMaxAmount");
            return (Criteria) this;
        }

        public Criteria andPersonalMaxAmountGreaterThan(Integer value) {
            addCriterion("personal_max_amount >", value, "personalMaxAmount");
            return (Criteria) this;
        }

        public Criteria andPersonalMaxAmountGreaterThanOrEqualTo(Integer value) {
            addCriterion("personal_max_amount >=", value, "personalMaxAmount");
            return (Criteria) this;
        }

        public Criteria andPersonalMaxAmountLessThan(Integer value) {
            addCriterion("personal_max_amount <", value, "personalMaxAmount");
            return (Criteria) this;
        }

        public Criteria andPersonalMaxAmountLessThanOrEqualTo(Integer value) {
            addCriterion("personal_max_amount <=", value, "personalMaxAmount");
            return (Criteria) this;
        }

        public Criteria andPersonalMaxAmountIn(List<Integer> values) {
            addCriterion("personal_max_amount in", values, "personalMaxAmount");
            return (Criteria) this;
        }

        public Criteria andPersonalMaxAmountNotIn(List<Integer> values) {
            addCriterion("personal_max_amount not in", values, "personalMaxAmount");
            return (Criteria) this;
        }

        public Criteria andPersonalMaxAmountBetween(Integer value1, Integer value2) {
            addCriterion("personal_max_amount between", value1, value2, "personalMaxAmount");
            return (Criteria) this;
        }

        public Criteria andPersonalMaxAmountNotBetween(Integer value1, Integer value2) {
            addCriterion("personal_max_amount not between", value1, value2, "personalMaxAmount");
            return (Criteria) this;
        }

        public Criteria andEnterpriseMaxAmountIsNull() {
            addCriterion("enterprise_max_amount is null");
            return (Criteria) this;
        }

        public Criteria andEnterpriseMaxAmountIsNotNull() {
            addCriterion("enterprise_max_amount is not null");
            return (Criteria) this;
        }

        public Criteria andEnterpriseMaxAmountEqualTo(Integer value) {
            addCriterion("enterprise_max_amount =", value, "enterpriseMaxAmount");
            return (Criteria) this;
        }

        public Criteria andEnterpriseMaxAmountNotEqualTo(Integer value) {
            addCriterion("enterprise_max_amount <>", value, "enterpriseMaxAmount");
            return (Criteria) this;
        }

        public Criteria andEnterpriseMaxAmountGreaterThan(Integer value) {
            addCriterion("enterprise_max_amount >", value, "enterpriseMaxAmount");
            return (Criteria) this;
        }

        public Criteria andEnterpriseMaxAmountGreaterThanOrEqualTo(Integer value) {
            addCriterion("enterprise_max_amount >=", value, "enterpriseMaxAmount");
            return (Criteria) this;
        }

        public Criteria andEnterpriseMaxAmountLessThan(Integer value) {
            addCriterion("enterprise_max_amount <", value, "enterpriseMaxAmount");
            return (Criteria) this;
        }

        public Criteria andEnterpriseMaxAmountLessThanOrEqualTo(Integer value) {
            addCriterion("enterprise_max_amount <=", value, "enterpriseMaxAmount");
            return (Criteria) this;
        }

        public Criteria andEnterpriseMaxAmountIn(List<Integer> values) {
            addCriterion("enterprise_max_amount in", values, "enterpriseMaxAmount");
            return (Criteria) this;
        }

        public Criteria andEnterpriseMaxAmountNotIn(List<Integer> values) {
            addCriterion("enterprise_max_amount not in", values, "enterpriseMaxAmount");
            return (Criteria) this;
        }

        public Criteria andEnterpriseMaxAmountBetween(Integer value1, Integer value2) {
            addCriterion("enterprise_max_amount between", value1, value2, "enterpriseMaxAmount");
            return (Criteria) this;
        }

        public Criteria andEnterpriseMaxAmountNotBetween(Integer value1, Integer value2) {
            addCriterion("enterprise_max_amount not between", value1, value2, "enterpriseMaxAmount");
            return (Criteria) this;
        }

        public Criteria andAuthPeriodIsNull() {
            addCriterion("auth_period is null");
            return (Criteria) this;
        }

        public Criteria andAuthPeriodIsNotNull() {
            addCriterion("auth_period is not null");
            return (Criteria) this;
        }

        public Criteria andAuthPeriodEqualTo(Integer value) {
            addCriterion("auth_period =", value, "authPeriod");
            return (Criteria) this;
        }

        public Criteria andAuthPeriodNotEqualTo(Integer value) {
            addCriterion("auth_period <>", value, "authPeriod");
            return (Criteria) this;
        }

        public Criteria andAuthPeriodGreaterThan(Integer value) {
            addCriterion("auth_period >", value, "authPeriod");
            return (Criteria) this;
        }

        public Criteria andAuthPeriodGreaterThanOrEqualTo(Integer value) {
            addCriterion("auth_period >=", value, "authPeriod");
            return (Criteria) this;
        }

        public Criteria andAuthPeriodLessThan(Integer value) {
            addCriterion("auth_period <", value, "authPeriod");
            return (Criteria) this;
        }

        public Criteria andAuthPeriodLessThanOrEqualTo(Integer value) {
            addCriterion("auth_period <=", value, "authPeriod");
            return (Criteria) this;
        }

        public Criteria andAuthPeriodIn(List<Integer> values) {
            addCriterion("auth_period in", values, "authPeriod");
            return (Criteria) this;
        }

        public Criteria andAuthPeriodNotIn(List<Integer> values) {
            addCriterion("auth_period not in", values, "authPeriod");
            return (Criteria) this;
        }

        public Criteria andAuthPeriodBetween(Integer value1, Integer value2) {
            addCriterion("auth_period between", value1, value2, "authPeriod");
            return (Criteria) this;
        }

        public Criteria andAuthPeriodNotBetween(Integer value1, Integer value2) {
            addCriterion("auth_period not between", value1, value2, "authPeriod");
            return (Criteria) this;
        }

        public Criteria andEnabledStatusIsNull() {
            addCriterion("enabled_status is null");
            return (Criteria) this;
        }

        public Criteria andEnabledStatusIsNotNull() {
            addCriterion("enabled_status is not null");
            return (Criteria) this;
        }

        public Criteria andEnabledStatusEqualTo(Integer value) {
            addCriterion("enabled_status =", value, "enabledStatus");
            return (Criteria) this;
        }

        public Criteria andEnabledStatusNotEqualTo(Integer value) {
            addCriterion("enabled_status <>", value, "enabledStatus");
            return (Criteria) this;
        }

        public Criteria andEnabledStatusGreaterThan(Integer value) {
            addCriterion("enabled_status >", value, "enabledStatus");
            return (Criteria) this;
        }

        public Criteria andEnabledStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("enabled_status >=", value, "enabledStatus");
            return (Criteria) this;
        }

        public Criteria andEnabledStatusLessThan(Integer value) {
            addCriterion("enabled_status <", value, "enabledStatus");
            return (Criteria) this;
        }

        public Criteria andEnabledStatusLessThanOrEqualTo(Integer value) {
            addCriterion("enabled_status <=", value, "enabledStatus");
            return (Criteria) this;
        }

        public Criteria andEnabledStatusIn(List<Integer> values) {
            addCriterion("enabled_status in", values, "enabledStatus");
            return (Criteria) this;
        }

        public Criteria andEnabledStatusNotIn(List<Integer> values) {
            addCriterion("enabled_status not in", values, "enabledStatus");
            return (Criteria) this;
        }

        public Criteria andEnabledStatusBetween(Integer value1, Integer value2) {
            addCriterion("enabled_status between", value1, value2, "enabledStatus");
            return (Criteria) this;
        }

        public Criteria andEnabledStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("enabled_status not between", value1, value2, "enabledStatus");
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

        public Criteria andIpIsNull() {
            addCriterion("ip is null");
            return (Criteria) this;
        }

        public Criteria andIpIsNotNull() {
            addCriterion("ip is not null");
            return (Criteria) this;
        }

        public Criteria andIpEqualTo(String value) {
            addCriterion("ip =", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotEqualTo(String value) {
            addCriterion("ip <>", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpGreaterThan(String value) {
            addCriterion("ip >", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpGreaterThanOrEqualTo(String value) {
            addCriterion("ip >=", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLessThan(String value) {
            addCriterion("ip <", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLessThanOrEqualTo(String value) {
            addCriterion("ip <=", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLike(String value) {
            addCriterion("ip like", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotLike(String value) {
            addCriterion("ip not like", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpIn(List<String> values) {
            addCriterion("ip in", values, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotIn(List<String> values) {
            addCriterion("ip not in", values, "ip");
            return (Criteria) this;
        }

        public Criteria andIpBetween(String value1, String value2) {
            addCriterion("ip between", value1, value2, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotBetween(String value1, String value2) {
            addCriterion("ip not between", value1, value2, "ip");
            return (Criteria) this;
        }

        public Criteria andMacIsNull() {
            addCriterion("mac is null");
            return (Criteria) this;
        }

        public Criteria andMacIsNotNull() {
            addCriterion("mac is not null");
            return (Criteria) this;
        }

        public Criteria andMacEqualTo(String value) {
            addCriterion("mac =", value, "mac");
            return (Criteria) this;
        }

        public Criteria andMacNotEqualTo(String value) {
            addCriterion("mac <>", value, "mac");
            return (Criteria) this;
        }

        public Criteria andMacGreaterThan(String value) {
            addCriterion("mac >", value, "mac");
            return (Criteria) this;
        }

        public Criteria andMacGreaterThanOrEqualTo(String value) {
            addCriterion("mac >=", value, "mac");
            return (Criteria) this;
        }

        public Criteria andMacLessThan(String value) {
            addCriterion("mac <", value, "mac");
            return (Criteria) this;
        }

        public Criteria andMacLessThanOrEqualTo(String value) {
            addCriterion("mac <=", value, "mac");
            return (Criteria) this;
        }

        public Criteria andMacLike(String value) {
            addCriterion("mac like", value, "mac");
            return (Criteria) this;
        }

        public Criteria andMacNotLike(String value) {
            addCriterion("mac not like", value, "mac");
            return (Criteria) this;
        }

        public Criteria andMacIn(List<String> values) {
            addCriterion("mac in", values, "mac");
            return (Criteria) this;
        }

        public Criteria andMacNotIn(List<String> values) {
            addCriterion("mac not in", values, "mac");
            return (Criteria) this;
        }

        public Criteria andMacBetween(String value1, String value2) {
            addCriterion("mac between", value1, value2, "mac");
            return (Criteria) this;
        }

        public Criteria andMacNotBetween(String value1, String value2) {
            addCriterion("mac not between", value1, value2, "mac");
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