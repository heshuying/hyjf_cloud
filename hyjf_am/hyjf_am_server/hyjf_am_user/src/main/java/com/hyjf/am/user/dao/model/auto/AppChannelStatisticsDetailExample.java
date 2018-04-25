package com.hyjf.am.user.dao.model.auto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AppChannelStatisticsDetailExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public AppChannelStatisticsDetailExample() {
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

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andSourceIdIsNull() {
            addCriterion("source_id is null");
            return (Criteria) this;
        }

        public Criteria andSourceIdIsNotNull() {
            addCriterion("source_id is not null");
            return (Criteria) this;
        }

        public Criteria andSourceIdEqualTo(Integer value) {
            addCriterion("source_id =", value, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdNotEqualTo(Integer value) {
            addCriterion("source_id <>", value, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdGreaterThan(Integer value) {
            addCriterion("source_id >", value, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("source_id >=", value, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdLessThan(Integer value) {
            addCriterion("source_id <", value, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdLessThanOrEqualTo(Integer value) {
            addCriterion("source_id <=", value, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdIn(List<Integer> values) {
            addCriterion("source_id in", values, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdNotIn(List<Integer> values) {
            addCriterion("source_id not in", values, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdBetween(Integer value1, Integer value2) {
            addCriterion("source_id between", value1, value2, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdNotBetween(Integer value1, Integer value2) {
            addCriterion("source_id not between", value1, value2, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceNameIsNull() {
            addCriterion("source_name is null");
            return (Criteria) this;
        }

        public Criteria andSourceNameIsNotNull() {
            addCriterion("source_name is not null");
            return (Criteria) this;
        }

        public Criteria andSourceNameEqualTo(String value) {
            addCriterion("source_name =", value, "sourceName");
            return (Criteria) this;
        }

        public Criteria andSourceNameNotEqualTo(String value) {
            addCriterion("source_name <>", value, "sourceName");
            return (Criteria) this;
        }

        public Criteria andSourceNameGreaterThan(String value) {
            addCriterion("source_name >", value, "sourceName");
            return (Criteria) this;
        }

        public Criteria andSourceNameGreaterThanOrEqualTo(String value) {
            addCriterion("source_name >=", value, "sourceName");
            return (Criteria) this;
        }

        public Criteria andSourceNameLessThan(String value) {
            addCriterion("source_name <", value, "sourceName");
            return (Criteria) this;
        }

        public Criteria andSourceNameLessThanOrEqualTo(String value) {
            addCriterion("source_name <=", value, "sourceName");
            return (Criteria) this;
        }

        public Criteria andSourceNameLike(String value) {
            addCriterion("source_name like", value, "sourceName");
            return (Criteria) this;
        }

        public Criteria andSourceNameNotLike(String value) {
            addCriterion("source_name not like", value, "sourceName");
            return (Criteria) this;
        }

        public Criteria andSourceNameIn(List<String> values) {
            addCriterion("source_name in", values, "sourceName");
            return (Criteria) this;
        }

        public Criteria andSourceNameNotIn(List<String> values) {
            addCriterion("source_name not in", values, "sourceName");
            return (Criteria) this;
        }

        public Criteria andSourceNameBetween(String value1, String value2) {
            addCriterion("source_name between", value1, value2, "sourceName");
            return (Criteria) this;
        }

        public Criteria andSourceNameNotBetween(String value1, String value2) {
            addCriterion("source_name not between", value1, value2, "sourceName");
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

        public Criteria andUserNameIsNull() {
            addCriterion("user_name is null");
            return (Criteria) this;
        }

        public Criteria andUserNameIsNotNull() {
            addCriterion("user_name is not null");
            return (Criteria) this;
        }

        public Criteria andUserNameEqualTo(String value) {
            addCriterion("user_name =", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotEqualTo(String value) {
            addCriterion("user_name <>", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameGreaterThan(String value) {
            addCriterion("user_name >", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("user_name >=", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLessThan(String value) {
            addCriterion("user_name <", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLessThanOrEqualTo(String value) {
            addCriterion("user_name <=", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLike(String value) {
            addCriterion("user_name like", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotLike(String value) {
            addCriterion("user_name not like", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameIn(List<String> values) {
            addCriterion("user_name in", values, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotIn(List<String> values) {
            addCriterion("user_name not in", values, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameBetween(String value1, String value2) {
            addCriterion("user_name between", value1, value2, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotBetween(String value1, String value2) {
            addCriterion("user_name not between", value1, value2, "userName");
            return (Criteria) this;
        }

        public Criteria andRegisterTimeIsNull() {
            addCriterion("register_time is null");
            return (Criteria) this;
        }

        public Criteria andRegisterTimeIsNotNull() {
            addCriterion("register_time is not null");
            return (Criteria) this;
        }

        public Criteria andRegisterTimeEqualTo(Date value) {
            addCriterion("register_time =", value, "registerTime");
            return (Criteria) this;
        }

        public Criteria andRegisterTimeNotEqualTo(Date value) {
            addCriterion("register_time <>", value, "registerTime");
            return (Criteria) this;
        }

        public Criteria andRegisterTimeGreaterThan(Date value) {
            addCriterion("register_time >", value, "registerTime");
            return (Criteria) this;
        }

        public Criteria andRegisterTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("register_time >=", value, "registerTime");
            return (Criteria) this;
        }

        public Criteria andRegisterTimeLessThan(Date value) {
            addCriterion("register_time <", value, "registerTime");
            return (Criteria) this;
        }

        public Criteria andRegisterTimeLessThanOrEqualTo(Date value) {
            addCriterion("register_time <=", value, "registerTime");
            return (Criteria) this;
        }

        public Criteria andRegisterTimeIn(List<Date> values) {
            addCriterion("register_time in", values, "registerTime");
            return (Criteria) this;
        }

        public Criteria andRegisterTimeNotIn(List<Date> values) {
            addCriterion("register_time not in", values, "registerTime");
            return (Criteria) this;
        }

        public Criteria andRegisterTimeBetween(Date value1, Date value2) {
            addCriterion("register_time between", value1, value2, "registerTime");
            return (Criteria) this;
        }

        public Criteria andRegisterTimeNotBetween(Date value1, Date value2) {
            addCriterion("register_time not between", value1, value2, "registerTime");
            return (Criteria) this;
        }

        public Criteria andOpenAccountTimeIsNull() {
            addCriterion("open_account_time is null");
            return (Criteria) this;
        }

        public Criteria andOpenAccountTimeIsNotNull() {
            addCriterion("open_account_time is not null");
            return (Criteria) this;
        }

        public Criteria andOpenAccountTimeEqualTo(Date value) {
            addCriterion("open_account_time =", value, "openAccountTime");
            return (Criteria) this;
        }

        public Criteria andOpenAccountTimeNotEqualTo(Date value) {
            addCriterion("open_account_time <>", value, "openAccountTime");
            return (Criteria) this;
        }

        public Criteria andOpenAccountTimeGreaterThan(Date value) {
            addCriterion("open_account_time >", value, "openAccountTime");
            return (Criteria) this;
        }

        public Criteria andOpenAccountTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("open_account_time >=", value, "openAccountTime");
            return (Criteria) this;
        }

        public Criteria andOpenAccountTimeLessThan(Date value) {
            addCriterion("open_account_time <", value, "openAccountTime");
            return (Criteria) this;
        }

        public Criteria andOpenAccountTimeLessThanOrEqualTo(Date value) {
            addCriterion("open_account_time <=", value, "openAccountTime");
            return (Criteria) this;
        }

        public Criteria andOpenAccountTimeIn(List<Date> values) {
            addCriterion("open_account_time in", values, "openAccountTime");
            return (Criteria) this;
        }

        public Criteria andOpenAccountTimeNotIn(List<Date> values) {
            addCriterion("open_account_time not in", values, "openAccountTime");
            return (Criteria) this;
        }

        public Criteria andOpenAccountTimeBetween(Date value1, Date value2) {
            addCriterion("open_account_time between", value1, value2, "openAccountTime");
            return (Criteria) this;
        }

        public Criteria andOpenAccountTimeNotBetween(Date value1, Date value2) {
            addCriterion("open_account_time not between", value1, value2, "openAccountTime");
            return (Criteria) this;
        }

        public Criteria andFirstInvestTimeIsNull() {
            addCriterion("first_invest_time is null");
            return (Criteria) this;
        }

        public Criteria andFirstInvestTimeIsNotNull() {
            addCriterion("first_invest_time is not null");
            return (Criteria) this;
        }

        public Criteria andFirstInvestTimeEqualTo(Integer value) {
            addCriterion("first_invest_time =", value, "firstInvestTime");
            return (Criteria) this;
        }

        public Criteria andFirstInvestTimeNotEqualTo(Integer value) {
            addCriterion("first_invest_time <>", value, "firstInvestTime");
            return (Criteria) this;
        }

        public Criteria andFirstInvestTimeGreaterThan(Integer value) {
            addCriterion("first_invest_time >", value, "firstInvestTime");
            return (Criteria) this;
        }

        public Criteria andFirstInvestTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("first_invest_time >=", value, "firstInvestTime");
            return (Criteria) this;
        }

        public Criteria andFirstInvestTimeLessThan(Integer value) {
            addCriterion("first_invest_time <", value, "firstInvestTime");
            return (Criteria) this;
        }

        public Criteria andFirstInvestTimeLessThanOrEqualTo(Integer value) {
            addCriterion("first_invest_time <=", value, "firstInvestTime");
            return (Criteria) this;
        }

        public Criteria andFirstInvestTimeIn(List<Integer> values) {
            addCriterion("first_invest_time in", values, "firstInvestTime");
            return (Criteria) this;
        }

        public Criteria andFirstInvestTimeNotIn(List<Integer> values) {
            addCriterion("first_invest_time not in", values, "firstInvestTime");
            return (Criteria) this;
        }

        public Criteria andFirstInvestTimeBetween(Integer value1, Integer value2) {
            addCriterion("first_invest_time between", value1, value2, "firstInvestTime");
            return (Criteria) this;
        }

        public Criteria andFirstInvestTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("first_invest_time not between", value1, value2, "firstInvestTime");
            return (Criteria) this;
        }

        public Criteria andInvestAmountIsNull() {
            addCriterion("invest_amount is null");
            return (Criteria) this;
        }

        public Criteria andInvestAmountIsNotNull() {
            addCriterion("invest_amount is not null");
            return (Criteria) this;
        }

        public Criteria andInvestAmountEqualTo(BigDecimal value) {
            addCriterion("invest_amount =", value, "investAmount");
            return (Criteria) this;
        }

        public Criteria andInvestAmountNotEqualTo(BigDecimal value) {
            addCriterion("invest_amount <>", value, "investAmount");
            return (Criteria) this;
        }

        public Criteria andInvestAmountGreaterThan(BigDecimal value) {
            addCriterion("invest_amount >", value, "investAmount");
            return (Criteria) this;
        }

        public Criteria andInvestAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("invest_amount >=", value, "investAmount");
            return (Criteria) this;
        }

        public Criteria andInvestAmountLessThan(BigDecimal value) {
            addCriterion("invest_amount <", value, "investAmount");
            return (Criteria) this;
        }

        public Criteria andInvestAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("invest_amount <=", value, "investAmount");
            return (Criteria) this;
        }

        public Criteria andInvestAmountIn(List<BigDecimal> values) {
            addCriterion("invest_amount in", values, "investAmount");
            return (Criteria) this;
        }

        public Criteria andInvestAmountNotIn(List<BigDecimal> values) {
            addCriterion("invest_amount not in", values, "investAmount");
            return (Criteria) this;
        }

        public Criteria andInvestAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("invest_amount between", value1, value2, "investAmount");
            return (Criteria) this;
        }

        public Criteria andInvestAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("invest_amount not between", value1, value2, "investAmount");
            return (Criteria) this;
        }

        public Criteria andInvestProjectTypeIsNull() {
            addCriterion("invest_project_type is null");
            return (Criteria) this;
        }

        public Criteria andInvestProjectTypeIsNotNull() {
            addCriterion("invest_project_type is not null");
            return (Criteria) this;
        }

        public Criteria andInvestProjectTypeEqualTo(String value) {
            addCriterion("invest_project_type =", value, "investProjectType");
            return (Criteria) this;
        }

        public Criteria andInvestProjectTypeNotEqualTo(String value) {
            addCriterion("invest_project_type <>", value, "investProjectType");
            return (Criteria) this;
        }

        public Criteria andInvestProjectTypeGreaterThan(String value) {
            addCriterion("invest_project_type >", value, "investProjectType");
            return (Criteria) this;
        }

        public Criteria andInvestProjectTypeGreaterThanOrEqualTo(String value) {
            addCriterion("invest_project_type >=", value, "investProjectType");
            return (Criteria) this;
        }

        public Criteria andInvestProjectTypeLessThan(String value) {
            addCriterion("invest_project_type <", value, "investProjectType");
            return (Criteria) this;
        }

        public Criteria andInvestProjectTypeLessThanOrEqualTo(String value) {
            addCriterion("invest_project_type <=", value, "investProjectType");
            return (Criteria) this;
        }

        public Criteria andInvestProjectTypeLike(String value) {
            addCriterion("invest_project_type like", value, "investProjectType");
            return (Criteria) this;
        }

        public Criteria andInvestProjectTypeNotLike(String value) {
            addCriterion("invest_project_type not like", value, "investProjectType");
            return (Criteria) this;
        }

        public Criteria andInvestProjectTypeIn(List<String> values) {
            addCriterion("invest_project_type in", values, "investProjectType");
            return (Criteria) this;
        }

        public Criteria andInvestProjectTypeNotIn(List<String> values) {
            addCriterion("invest_project_type not in", values, "investProjectType");
            return (Criteria) this;
        }

        public Criteria andInvestProjectTypeBetween(String value1, String value2) {
            addCriterion("invest_project_type between", value1, value2, "investProjectType");
            return (Criteria) this;
        }

        public Criteria andInvestProjectTypeNotBetween(String value1, String value2) {
            addCriterion("invest_project_type not between", value1, value2, "investProjectType");
            return (Criteria) this;
        }

        public Criteria andInvestProjectPeriodIsNull() {
            addCriterion("invest_project_period is null");
            return (Criteria) this;
        }

        public Criteria andInvestProjectPeriodIsNotNull() {
            addCriterion("invest_project_period is not null");
            return (Criteria) this;
        }

        public Criteria andInvestProjectPeriodEqualTo(String value) {
            addCriterion("invest_project_period =", value, "investProjectPeriod");
            return (Criteria) this;
        }

        public Criteria andInvestProjectPeriodNotEqualTo(String value) {
            addCriterion("invest_project_period <>", value, "investProjectPeriod");
            return (Criteria) this;
        }

        public Criteria andInvestProjectPeriodGreaterThan(String value) {
            addCriterion("invest_project_period >", value, "investProjectPeriod");
            return (Criteria) this;
        }

        public Criteria andInvestProjectPeriodGreaterThanOrEqualTo(String value) {
            addCriterion("invest_project_period >=", value, "investProjectPeriod");
            return (Criteria) this;
        }

        public Criteria andInvestProjectPeriodLessThan(String value) {
            addCriterion("invest_project_period <", value, "investProjectPeriod");
            return (Criteria) this;
        }

        public Criteria andInvestProjectPeriodLessThanOrEqualTo(String value) {
            addCriterion("invest_project_period <=", value, "investProjectPeriod");
            return (Criteria) this;
        }

        public Criteria andInvestProjectPeriodLike(String value) {
            addCriterion("invest_project_period like", value, "investProjectPeriod");
            return (Criteria) this;
        }

        public Criteria andInvestProjectPeriodNotLike(String value) {
            addCriterion("invest_project_period not like", value, "investProjectPeriod");
            return (Criteria) this;
        }

        public Criteria andInvestProjectPeriodIn(List<String> values) {
            addCriterion("invest_project_period in", values, "investProjectPeriod");
            return (Criteria) this;
        }

        public Criteria andInvestProjectPeriodNotIn(List<String> values) {
            addCriterion("invest_project_period not in", values, "investProjectPeriod");
            return (Criteria) this;
        }

        public Criteria andInvestProjectPeriodBetween(String value1, String value2) {
            addCriterion("invest_project_period between", value1, value2, "investProjectPeriod");
            return (Criteria) this;
        }

        public Criteria andInvestProjectPeriodNotBetween(String value1, String value2) {
            addCriterion("invest_project_period not between", value1, value2, "investProjectPeriod");
            return (Criteria) this;
        }

        public Criteria andCumulativeInvestIsNull() {
            addCriterion("cumulative_invest is null");
            return (Criteria) this;
        }

        public Criteria andCumulativeInvestIsNotNull() {
            addCriterion("cumulative_invest is not null");
            return (Criteria) this;
        }

        public Criteria andCumulativeInvestEqualTo(BigDecimal value) {
            addCriterion("cumulative_invest =", value, "cumulativeInvest");
            return (Criteria) this;
        }

        public Criteria andCumulativeInvestNotEqualTo(BigDecimal value) {
            addCriterion("cumulative_invest <>", value, "cumulativeInvest");
            return (Criteria) this;
        }

        public Criteria andCumulativeInvestGreaterThan(BigDecimal value) {
            addCriterion("cumulative_invest >", value, "cumulativeInvest");
            return (Criteria) this;
        }

        public Criteria andCumulativeInvestGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("cumulative_invest >=", value, "cumulativeInvest");
            return (Criteria) this;
        }

        public Criteria andCumulativeInvestLessThan(BigDecimal value) {
            addCriterion("cumulative_invest <", value, "cumulativeInvest");
            return (Criteria) this;
        }

        public Criteria andCumulativeInvestLessThanOrEqualTo(BigDecimal value) {
            addCriterion("cumulative_invest <=", value, "cumulativeInvest");
            return (Criteria) this;
        }

        public Criteria andCumulativeInvestIn(List<BigDecimal> values) {
            addCriterion("cumulative_invest in", values, "cumulativeInvest");
            return (Criteria) this;
        }

        public Criteria andCumulativeInvestNotIn(List<BigDecimal> values) {
            addCriterion("cumulative_invest not in", values, "cumulativeInvest");
            return (Criteria) this;
        }

        public Criteria andCumulativeInvestBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("cumulative_invest between", value1, value2, "cumulativeInvest");
            return (Criteria) this;
        }

        public Criteria andCumulativeInvestNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("cumulative_invest not between", value1, value2, "cumulativeInvest");
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