package com.hyjf.am.config.dao.model.auto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DebtConfigLogExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public DebtConfigLogExample() {
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

        public Criteria andHyjfDebtConfigIdIsNull() {
            addCriterion("hyjf_debt_config_id is null");
            return (Criteria) this;
        }

        public Criteria andHyjfDebtConfigIdIsNotNull() {
            addCriterion("hyjf_debt_config_id is not null");
            return (Criteria) this;
        }

        public Criteria andHyjfDebtConfigIdEqualTo(Integer value) {
            addCriterion("hyjf_debt_config_id =", value, "hyjfDebtConfigId");
            return (Criteria) this;
        }

        public Criteria andHyjfDebtConfigIdNotEqualTo(Integer value) {
            addCriterion("hyjf_debt_config_id <>", value, "hyjfDebtConfigId");
            return (Criteria) this;
        }

        public Criteria andHyjfDebtConfigIdGreaterThan(Integer value) {
            addCriterion("hyjf_debt_config_id >", value, "hyjfDebtConfigId");
            return (Criteria) this;
        }

        public Criteria andHyjfDebtConfigIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("hyjf_debt_config_id >=", value, "hyjfDebtConfigId");
            return (Criteria) this;
        }

        public Criteria andHyjfDebtConfigIdLessThan(Integer value) {
            addCriterion("hyjf_debt_config_id <", value, "hyjfDebtConfigId");
            return (Criteria) this;
        }

        public Criteria andHyjfDebtConfigIdLessThanOrEqualTo(Integer value) {
            addCriterion("hyjf_debt_config_id <=", value, "hyjfDebtConfigId");
            return (Criteria) this;
        }

        public Criteria andHyjfDebtConfigIdIn(List<Integer> values) {
            addCriterion("hyjf_debt_config_id in", values, "hyjfDebtConfigId");
            return (Criteria) this;
        }

        public Criteria andHyjfDebtConfigIdNotIn(List<Integer> values) {
            addCriterion("hyjf_debt_config_id not in", values, "hyjfDebtConfigId");
            return (Criteria) this;
        }

        public Criteria andHyjfDebtConfigIdBetween(Integer value1, Integer value2) {
            addCriterion("hyjf_debt_config_id between", value1, value2, "hyjfDebtConfigId");
            return (Criteria) this;
        }

        public Criteria andHyjfDebtConfigIdNotBetween(Integer value1, Integer value2) {
            addCriterion("hyjf_debt_config_id not between", value1, value2, "hyjfDebtConfigId");
            return (Criteria) this;
        }

        public Criteria andAttornRateIsNull() {
            addCriterion("attorn_rate is null");
            return (Criteria) this;
        }

        public Criteria andAttornRateIsNotNull() {
            addCriterion("attorn_rate is not null");
            return (Criteria) this;
        }

        public Criteria andAttornRateEqualTo(BigDecimal value) {
            addCriterion("attorn_rate =", value, "attornRate");
            return (Criteria) this;
        }

        public Criteria andAttornRateNotEqualTo(BigDecimal value) {
            addCriterion("attorn_rate <>", value, "attornRate");
            return (Criteria) this;
        }

        public Criteria andAttornRateGreaterThan(BigDecimal value) {
            addCriterion("attorn_rate >", value, "attornRate");
            return (Criteria) this;
        }

        public Criteria andAttornRateGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("attorn_rate >=", value, "attornRate");
            return (Criteria) this;
        }

        public Criteria andAttornRateLessThan(BigDecimal value) {
            addCriterion("attorn_rate <", value, "attornRate");
            return (Criteria) this;
        }

        public Criteria andAttornRateLessThanOrEqualTo(BigDecimal value) {
            addCriterion("attorn_rate <=", value, "attornRate");
            return (Criteria) this;
        }

        public Criteria andAttornRateIn(List<BigDecimal> values) {
            addCriterion("attorn_rate in", values, "attornRate");
            return (Criteria) this;
        }

        public Criteria andAttornRateNotIn(List<BigDecimal> values) {
            addCriterion("attorn_rate not in", values, "attornRate");
            return (Criteria) this;
        }

        public Criteria andAttornRateBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("attorn_rate between", value1, value2, "attornRate");
            return (Criteria) this;
        }

        public Criteria andAttornRateNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("attorn_rate not between", value1, value2, "attornRate");
            return (Criteria) this;
        }

        public Criteria andConcessionRateUpIsNull() {
            addCriterion("concession_rate_up is null");
            return (Criteria) this;
        }

        public Criteria andConcessionRateUpIsNotNull() {
            addCriterion("concession_rate_up is not null");
            return (Criteria) this;
        }

        public Criteria andConcessionRateUpEqualTo(BigDecimal value) {
            addCriterion("concession_rate_up =", value, "concessionRateUp");
            return (Criteria) this;
        }

        public Criteria andConcessionRateUpNotEqualTo(BigDecimal value) {
            addCriterion("concession_rate_up <>", value, "concessionRateUp");
            return (Criteria) this;
        }

        public Criteria andConcessionRateUpGreaterThan(BigDecimal value) {
            addCriterion("concession_rate_up >", value, "concessionRateUp");
            return (Criteria) this;
        }

        public Criteria andConcessionRateUpGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("concession_rate_up >=", value, "concessionRateUp");
            return (Criteria) this;
        }

        public Criteria andConcessionRateUpLessThan(BigDecimal value) {
            addCriterion("concession_rate_up <", value, "concessionRateUp");
            return (Criteria) this;
        }

        public Criteria andConcessionRateUpLessThanOrEqualTo(BigDecimal value) {
            addCriterion("concession_rate_up <=", value, "concessionRateUp");
            return (Criteria) this;
        }

        public Criteria andConcessionRateUpIn(List<BigDecimal> values) {
            addCriterion("concession_rate_up in", values, "concessionRateUp");
            return (Criteria) this;
        }

        public Criteria andConcessionRateUpNotIn(List<BigDecimal> values) {
            addCriterion("concession_rate_up not in", values, "concessionRateUp");
            return (Criteria) this;
        }

        public Criteria andConcessionRateUpBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("concession_rate_up between", value1, value2, "concessionRateUp");
            return (Criteria) this;
        }

        public Criteria andConcessionRateUpNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("concession_rate_up not between", value1, value2, "concessionRateUp");
            return (Criteria) this;
        }

        public Criteria andConcessionRateDownIsNull() {
            addCriterion("concession_rate_down is null");
            return (Criteria) this;
        }

        public Criteria andConcessionRateDownIsNotNull() {
            addCriterion("concession_rate_down is not null");
            return (Criteria) this;
        }

        public Criteria andConcessionRateDownEqualTo(BigDecimal value) {
            addCriterion("concession_rate_down =", value, "concessionRateDown");
            return (Criteria) this;
        }

        public Criteria andConcessionRateDownNotEqualTo(BigDecimal value) {
            addCriterion("concession_rate_down <>", value, "concessionRateDown");
            return (Criteria) this;
        }

        public Criteria andConcessionRateDownGreaterThan(BigDecimal value) {
            addCriterion("concession_rate_down >", value, "concessionRateDown");
            return (Criteria) this;
        }

        public Criteria andConcessionRateDownGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("concession_rate_down >=", value, "concessionRateDown");
            return (Criteria) this;
        }

        public Criteria andConcessionRateDownLessThan(BigDecimal value) {
            addCriterion("concession_rate_down <", value, "concessionRateDown");
            return (Criteria) this;
        }

        public Criteria andConcessionRateDownLessThanOrEqualTo(BigDecimal value) {
            addCriterion("concession_rate_down <=", value, "concessionRateDown");
            return (Criteria) this;
        }

        public Criteria andConcessionRateDownIn(List<BigDecimal> values) {
            addCriterion("concession_rate_down in", values, "concessionRateDown");
            return (Criteria) this;
        }

        public Criteria andConcessionRateDownNotIn(List<BigDecimal> values) {
            addCriterion("concession_rate_down not in", values, "concessionRateDown");
            return (Criteria) this;
        }

        public Criteria andConcessionRateDownBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("concession_rate_down between", value1, value2, "concessionRateDown");
            return (Criteria) this;
        }

        public Criteria andConcessionRateDownNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("concession_rate_down not between", value1, value2, "concessionRateDown");
            return (Criteria) this;
        }

        public Criteria andToggleIsNull() {
            addCriterion("toggle is null");
            return (Criteria) this;
        }

        public Criteria andToggleIsNotNull() {
            addCriterion("toggle is not null");
            return (Criteria) this;
        }

        public Criteria andToggleEqualTo(Integer value) {
            addCriterion("toggle =", value, "toggle");
            return (Criteria) this;
        }

        public Criteria andToggleNotEqualTo(Integer value) {
            addCriterion("toggle <>", value, "toggle");
            return (Criteria) this;
        }

        public Criteria andToggleGreaterThan(Integer value) {
            addCriterion("toggle >", value, "toggle");
            return (Criteria) this;
        }

        public Criteria andToggleGreaterThanOrEqualTo(Integer value) {
            addCriterion("toggle >=", value, "toggle");
            return (Criteria) this;
        }

        public Criteria andToggleLessThan(Integer value) {
            addCriterion("toggle <", value, "toggle");
            return (Criteria) this;
        }

        public Criteria andToggleLessThanOrEqualTo(Integer value) {
            addCriterion("toggle <=", value, "toggle");
            return (Criteria) this;
        }

        public Criteria andToggleIn(List<Integer> values) {
            addCriterion("toggle in", values, "toggle");
            return (Criteria) this;
        }

        public Criteria andToggleNotIn(List<Integer> values) {
            addCriterion("toggle not in", values, "toggle");
            return (Criteria) this;
        }

        public Criteria andToggleBetween(Integer value1, Integer value2) {
            addCriterion("toggle between", value1, value2, "toggle");
            return (Criteria) this;
        }

        public Criteria andToggleNotBetween(Integer value1, Integer value2) {
            addCriterion("toggle not between", value1, value2, "toggle");
            return (Criteria) this;
        }

        public Criteria andCloseDesIsNull() {
            addCriterion("close_des is null");
            return (Criteria) this;
        }

        public Criteria andCloseDesIsNotNull() {
            addCriterion("close_des is not null");
            return (Criteria) this;
        }

        public Criteria andCloseDesEqualTo(String value) {
            addCriterion("close_des =", value, "closeDes");
            return (Criteria) this;
        }

        public Criteria andCloseDesNotEqualTo(String value) {
            addCriterion("close_des <>", value, "closeDes");
            return (Criteria) this;
        }

        public Criteria andCloseDesGreaterThan(String value) {
            addCriterion("close_des >", value, "closeDes");
            return (Criteria) this;
        }

        public Criteria andCloseDesGreaterThanOrEqualTo(String value) {
            addCriterion("close_des >=", value, "closeDes");
            return (Criteria) this;
        }

        public Criteria andCloseDesLessThan(String value) {
            addCriterion("close_des <", value, "closeDes");
            return (Criteria) this;
        }

        public Criteria andCloseDesLessThanOrEqualTo(String value) {
            addCriterion("close_des <=", value, "closeDes");
            return (Criteria) this;
        }

        public Criteria andCloseDesLike(String value) {
            addCriterion("close_des like", value, "closeDes");
            return (Criteria) this;
        }

        public Criteria andCloseDesNotLike(String value) {
            addCriterion("close_des not like", value, "closeDes");
            return (Criteria) this;
        }

        public Criteria andCloseDesIn(List<String> values) {
            addCriterion("close_des in", values, "closeDes");
            return (Criteria) this;
        }

        public Criteria andCloseDesNotIn(List<String> values) {
            addCriterion("close_des not in", values, "closeDes");
            return (Criteria) this;
        }

        public Criteria andCloseDesBetween(String value1, String value2) {
            addCriterion("close_des between", value1, value2, "closeDes");
            return (Criteria) this;
        }

        public Criteria andCloseDesNotBetween(String value1, String value2) {
            addCriterion("close_des not between", value1, value2, "closeDes");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIsNull() {
            addCriterion("update_user is null");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIsNotNull() {
            addCriterion("update_user is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateUserEqualTo(Integer value) {
            addCriterion("update_user =", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotEqualTo(Integer value) {
            addCriterion("update_user <>", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserGreaterThan(Integer value) {
            addCriterion("update_user >", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserGreaterThanOrEqualTo(Integer value) {
            addCriterion("update_user >=", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLessThan(Integer value) {
            addCriterion("update_user <", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLessThanOrEqualTo(Integer value) {
            addCriterion("update_user <=", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIn(List<Integer> values) {
            addCriterion("update_user in", values, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotIn(List<Integer> values) {
            addCriterion("update_user not in", values, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserBetween(Integer value1, Integer value2) {
            addCriterion("update_user between", value1, value2, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotBetween(Integer value1, Integer value2) {
            addCriterion("update_user not between", value1, value2, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUsernameIsNull() {
            addCriterion("update_username is null");
            return (Criteria) this;
        }

        public Criteria andUpdateUsernameIsNotNull() {
            addCriterion("update_username is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateUsernameEqualTo(String value) {
            addCriterion("update_username =", value, "updateUsername");
            return (Criteria) this;
        }

        public Criteria andUpdateUsernameNotEqualTo(String value) {
            addCriterion("update_username <>", value, "updateUsername");
            return (Criteria) this;
        }

        public Criteria andUpdateUsernameGreaterThan(String value) {
            addCriterion("update_username >", value, "updateUsername");
            return (Criteria) this;
        }

        public Criteria andUpdateUsernameGreaterThanOrEqualTo(String value) {
            addCriterion("update_username >=", value, "updateUsername");
            return (Criteria) this;
        }

        public Criteria andUpdateUsernameLessThan(String value) {
            addCriterion("update_username <", value, "updateUsername");
            return (Criteria) this;
        }

        public Criteria andUpdateUsernameLessThanOrEqualTo(String value) {
            addCriterion("update_username <=", value, "updateUsername");
            return (Criteria) this;
        }

        public Criteria andUpdateUsernameLike(String value) {
            addCriterion("update_username like", value, "updateUsername");
            return (Criteria) this;
        }

        public Criteria andUpdateUsernameNotLike(String value) {
            addCriterion("update_username not like", value, "updateUsername");
            return (Criteria) this;
        }

        public Criteria andUpdateUsernameIn(List<String> values) {
            addCriterion("update_username in", values, "updateUsername");
            return (Criteria) this;
        }

        public Criteria andUpdateUsernameNotIn(List<String> values) {
            addCriterion("update_username not in", values, "updateUsername");
            return (Criteria) this;
        }

        public Criteria andUpdateUsernameBetween(String value1, String value2) {
            addCriterion("update_username between", value1, value2, "updateUsername");
            return (Criteria) this;
        }

        public Criteria andUpdateUsernameNotBetween(String value1, String value2) {
            addCriterion("update_username not between", value1, value2, "updateUsername");
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

        public Criteria andIpAddressIsNull() {
            addCriterion("ip_address is null");
            return (Criteria) this;
        }

        public Criteria andIpAddressIsNotNull() {
            addCriterion("ip_address is not null");
            return (Criteria) this;
        }

        public Criteria andIpAddressEqualTo(String value) {
            addCriterion("ip_address =", value, "ipAddress");
            return (Criteria) this;
        }

        public Criteria andIpAddressNotEqualTo(String value) {
            addCriterion("ip_address <>", value, "ipAddress");
            return (Criteria) this;
        }

        public Criteria andIpAddressGreaterThan(String value) {
            addCriterion("ip_address >", value, "ipAddress");
            return (Criteria) this;
        }

        public Criteria andIpAddressGreaterThanOrEqualTo(String value) {
            addCriterion("ip_address >=", value, "ipAddress");
            return (Criteria) this;
        }

        public Criteria andIpAddressLessThan(String value) {
            addCriterion("ip_address <", value, "ipAddress");
            return (Criteria) this;
        }

        public Criteria andIpAddressLessThanOrEqualTo(String value) {
            addCriterion("ip_address <=", value, "ipAddress");
            return (Criteria) this;
        }

        public Criteria andIpAddressLike(String value) {
            addCriterion("ip_address like", value, "ipAddress");
            return (Criteria) this;
        }

        public Criteria andIpAddressNotLike(String value) {
            addCriterion("ip_address not like", value, "ipAddress");
            return (Criteria) this;
        }

        public Criteria andIpAddressIn(List<String> values) {
            addCriterion("ip_address in", values, "ipAddress");
            return (Criteria) this;
        }

        public Criteria andIpAddressNotIn(List<String> values) {
            addCriterion("ip_address not in", values, "ipAddress");
            return (Criteria) this;
        }

        public Criteria andIpAddressBetween(String value1, String value2) {
            addCriterion("ip_address between", value1, value2, "ipAddress");
            return (Criteria) this;
        }

        public Criteria andIpAddressNotBetween(String value1, String value2) {
            addCriterion("ip_address not between", value1, value2, "ipAddress");
            return (Criteria) this;
        }

        public Criteria andMacAddressIsNull() {
            addCriterion("mac_address is null");
            return (Criteria) this;
        }

        public Criteria andMacAddressIsNotNull() {
            addCriterion("mac_address is not null");
            return (Criteria) this;
        }

        public Criteria andMacAddressEqualTo(String value) {
            addCriterion("mac_address =", value, "macAddress");
            return (Criteria) this;
        }

        public Criteria andMacAddressNotEqualTo(String value) {
            addCriterion("mac_address <>", value, "macAddress");
            return (Criteria) this;
        }

        public Criteria andMacAddressGreaterThan(String value) {
            addCriterion("mac_address >", value, "macAddress");
            return (Criteria) this;
        }

        public Criteria andMacAddressGreaterThanOrEqualTo(String value) {
            addCriterion("mac_address >=", value, "macAddress");
            return (Criteria) this;
        }

        public Criteria andMacAddressLessThan(String value) {
            addCriterion("mac_address <", value, "macAddress");
            return (Criteria) this;
        }

        public Criteria andMacAddressLessThanOrEqualTo(String value) {
            addCriterion("mac_address <=", value, "macAddress");
            return (Criteria) this;
        }

        public Criteria andMacAddressLike(String value) {
            addCriterion("mac_address like", value, "macAddress");
            return (Criteria) this;
        }

        public Criteria andMacAddressNotLike(String value) {
            addCriterion("mac_address not like", value, "macAddress");
            return (Criteria) this;
        }

        public Criteria andMacAddressIn(List<String> values) {
            addCriterion("mac_address in", values, "macAddress");
            return (Criteria) this;
        }

        public Criteria andMacAddressNotIn(List<String> values) {
            addCriterion("mac_address not in", values, "macAddress");
            return (Criteria) this;
        }

        public Criteria andMacAddressBetween(String value1, String value2) {
            addCriterion("mac_address between", value1, value2, "macAddress");
            return (Criteria) this;
        }

        public Criteria andMacAddressNotBetween(String value1, String value2) {
            addCriterion("mac_address not between", value1, value2, "macAddress");
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