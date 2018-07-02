package com.hyjf.am.trade.dao.model.auto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PoundageLedgerExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public PoundageLedgerExample() {
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

        public Criteria andTruenameIsNull() {
            addCriterion("truename is null");
            return (Criteria) this;
        }

        public Criteria andTruenameIsNotNull() {
            addCriterion("truename is not null");
            return (Criteria) this;
        }

        public Criteria andTruenameEqualTo(String value) {
            addCriterion("truename =", value, "truename");
            return (Criteria) this;
        }

        public Criteria andTruenameNotEqualTo(String value) {
            addCriterion("truename <>", value, "truename");
            return (Criteria) this;
        }

        public Criteria andTruenameGreaterThan(String value) {
            addCriterion("truename >", value, "truename");
            return (Criteria) this;
        }

        public Criteria andTruenameGreaterThanOrEqualTo(String value) {
            addCriterion("truename >=", value, "truename");
            return (Criteria) this;
        }

        public Criteria andTruenameLessThan(String value) {
            addCriterion("truename <", value, "truename");
            return (Criteria) this;
        }

        public Criteria andTruenameLessThanOrEqualTo(String value) {
            addCriterion("truename <=", value, "truename");
            return (Criteria) this;
        }

        public Criteria andTruenameLike(String value) {
            addCriterion("truename like", value, "truename");
            return (Criteria) this;
        }

        public Criteria andTruenameNotLike(String value) {
            addCriterion("truename not like", value, "truename");
            return (Criteria) this;
        }

        public Criteria andTruenameIn(List<String> values) {
            addCriterion("truename in", values, "truename");
            return (Criteria) this;
        }

        public Criteria andTruenameNotIn(List<String> values) {
            addCriterion("truename not in", values, "truename");
            return (Criteria) this;
        }

        public Criteria andTruenameBetween(String value1, String value2) {
            addCriterion("truename between", value1, value2, "truename");
            return (Criteria) this;
        }

        public Criteria andTruenameNotBetween(String value1, String value2) {
            addCriterion("truename not between", value1, value2, "truename");
            return (Criteria) this;
        }

        public Criteria andAccountIsNull() {
            addCriterion("account is null");
            return (Criteria) this;
        }

        public Criteria andAccountIsNotNull() {
            addCriterion("account is not null");
            return (Criteria) this;
        }

        public Criteria andAccountEqualTo(String value) {
            addCriterion("account =", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountNotEqualTo(String value) {
            addCriterion("account <>", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountGreaterThan(String value) {
            addCriterion("account >", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountGreaterThanOrEqualTo(String value) {
            addCriterion("account >=", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountLessThan(String value) {
            addCriterion("account <", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountLessThanOrEqualTo(String value) {
            addCriterion("account <=", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountLike(String value) {
            addCriterion("account like", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountNotLike(String value) {
            addCriterion("account not like", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountIn(List<String> values) {
            addCriterion("account in", values, "account");
            return (Criteria) this;
        }

        public Criteria andAccountNotIn(List<String> values) {
            addCriterion("account not in", values, "account");
            return (Criteria) this;
        }

        public Criteria andAccountBetween(String value1, String value2) {
            addCriterion("account between", value1, value2, "account");
            return (Criteria) this;
        }

        public Criteria andAccountNotBetween(String value1, String value2) {
            addCriterion("account not between", value1, value2, "account");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("`type` is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("`type` is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(Integer value) {
            addCriterion("`type` =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Integer value) {
            addCriterion("`type` <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Integer value) {
            addCriterion("`type` >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("`type` >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Integer value) {
            addCriterion("`type` <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Integer value) {
            addCriterion("`type` <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Integer> values) {
            addCriterion("`type` in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Integer> values) {
            addCriterion("`type` not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Integer value1, Integer value2) {
            addCriterion("`type` between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("`type` not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andSourceIsNull() {
            addCriterion("`source` is null");
            return (Criteria) this;
        }

        public Criteria andSourceIsNotNull() {
            addCriterion("`source` is not null");
            return (Criteria) this;
        }

        public Criteria andSourceEqualTo(String value) {
            addCriterion("`source` =", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceNotEqualTo(String value) {
            addCriterion("`source` <>", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceGreaterThan(String value) {
            addCriterion("`source` >", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceGreaterThanOrEqualTo(String value) {
            addCriterion("`source` >=", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceLessThan(String value) {
            addCriterion("`source` <", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceLessThanOrEqualTo(String value) {
            addCriterion("`source` <=", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceLike(String value) {
            addCriterion("`source` like", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceNotLike(String value) {
            addCriterion("`source` not like", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceIn(List<String> values) {
            addCriterion("`source` in", values, "source");
            return (Criteria) this;
        }

        public Criteria andSourceNotIn(List<String> values) {
            addCriterion("`source` not in", values, "source");
            return (Criteria) this;
        }

        public Criteria andSourceBetween(String value1, String value2) {
            addCriterion("`source` between", value1, value2, "source");
            return (Criteria) this;
        }

        public Criteria andSourceNotBetween(String value1, String value2) {
            addCriterion("`source` not between", value1, value2, "source");
            return (Criteria) this;
        }

        public Criteria andServiceRatioIsNull() {
            addCriterion("service_ratio is null");
            return (Criteria) this;
        }

        public Criteria andServiceRatioIsNotNull() {
            addCriterion("service_ratio is not null");
            return (Criteria) this;
        }

        public Criteria andServiceRatioEqualTo(BigDecimal value) {
            addCriterion("service_ratio =", value, "serviceRatio");
            return (Criteria) this;
        }

        public Criteria andServiceRatioNotEqualTo(BigDecimal value) {
            addCriterion("service_ratio <>", value, "serviceRatio");
            return (Criteria) this;
        }

        public Criteria andServiceRatioGreaterThan(BigDecimal value) {
            addCriterion("service_ratio >", value, "serviceRatio");
            return (Criteria) this;
        }

        public Criteria andServiceRatioGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("service_ratio >=", value, "serviceRatio");
            return (Criteria) this;
        }

        public Criteria andServiceRatioLessThan(BigDecimal value) {
            addCriterion("service_ratio <", value, "serviceRatio");
            return (Criteria) this;
        }

        public Criteria andServiceRatioLessThanOrEqualTo(BigDecimal value) {
            addCriterion("service_ratio <=", value, "serviceRatio");
            return (Criteria) this;
        }

        public Criteria andServiceRatioIn(List<BigDecimal> values) {
            addCriterion("service_ratio in", values, "serviceRatio");
            return (Criteria) this;
        }

        public Criteria andServiceRatioNotIn(List<BigDecimal> values) {
            addCriterion("service_ratio not in", values, "serviceRatio");
            return (Criteria) this;
        }

        public Criteria andServiceRatioBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("service_ratio between", value1, value2, "serviceRatio");
            return (Criteria) this;
        }

        public Criteria andServiceRatioNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("service_ratio not between", value1, value2, "serviceRatio");
            return (Criteria) this;
        }

        public Criteria andCreditRatioIsNull() {
            addCriterion("credit_ratio is null");
            return (Criteria) this;
        }

        public Criteria andCreditRatioIsNotNull() {
            addCriterion("credit_ratio is not null");
            return (Criteria) this;
        }

        public Criteria andCreditRatioEqualTo(BigDecimal value) {
            addCriterion("credit_ratio =", value, "creditRatio");
            return (Criteria) this;
        }

        public Criteria andCreditRatioNotEqualTo(BigDecimal value) {
            addCriterion("credit_ratio <>", value, "creditRatio");
            return (Criteria) this;
        }

        public Criteria andCreditRatioGreaterThan(BigDecimal value) {
            addCriterion("credit_ratio >", value, "creditRatio");
            return (Criteria) this;
        }

        public Criteria andCreditRatioGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("credit_ratio >=", value, "creditRatio");
            return (Criteria) this;
        }

        public Criteria andCreditRatioLessThan(BigDecimal value) {
            addCriterion("credit_ratio <", value, "creditRatio");
            return (Criteria) this;
        }

        public Criteria andCreditRatioLessThanOrEqualTo(BigDecimal value) {
            addCriterion("credit_ratio <=", value, "creditRatio");
            return (Criteria) this;
        }

        public Criteria andCreditRatioIn(List<BigDecimal> values) {
            addCriterion("credit_ratio in", values, "creditRatio");
            return (Criteria) this;
        }

        public Criteria andCreditRatioNotIn(List<BigDecimal> values) {
            addCriterion("credit_ratio not in", values, "creditRatio");
            return (Criteria) this;
        }

        public Criteria andCreditRatioBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("credit_ratio between", value1, value2, "creditRatio");
            return (Criteria) this;
        }

        public Criteria andCreditRatioNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("credit_ratio not between", value1, value2, "creditRatio");
            return (Criteria) this;
        }

        public Criteria andManageRatioIsNull() {
            addCriterion("manage_ratio is null");
            return (Criteria) this;
        }

        public Criteria andManageRatioIsNotNull() {
            addCriterion("manage_ratio is not null");
            return (Criteria) this;
        }

        public Criteria andManageRatioEqualTo(BigDecimal value) {
            addCriterion("manage_ratio =", value, "manageRatio");
            return (Criteria) this;
        }

        public Criteria andManageRatioNotEqualTo(BigDecimal value) {
            addCriterion("manage_ratio <>", value, "manageRatio");
            return (Criteria) this;
        }

        public Criteria andManageRatioGreaterThan(BigDecimal value) {
            addCriterion("manage_ratio >", value, "manageRatio");
            return (Criteria) this;
        }

        public Criteria andManageRatioGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("manage_ratio >=", value, "manageRatio");
            return (Criteria) this;
        }

        public Criteria andManageRatioLessThan(BigDecimal value) {
            addCriterion("manage_ratio <", value, "manageRatio");
            return (Criteria) this;
        }

        public Criteria andManageRatioLessThanOrEqualTo(BigDecimal value) {
            addCriterion("manage_ratio <=", value, "manageRatio");
            return (Criteria) this;
        }

        public Criteria andManageRatioIn(List<BigDecimal> values) {
            addCriterion("manage_ratio in", values, "manageRatio");
            return (Criteria) this;
        }

        public Criteria andManageRatioNotIn(List<BigDecimal> values) {
            addCriterion("manage_ratio not in", values, "manageRatio");
            return (Criteria) this;
        }

        public Criteria andManageRatioBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("manage_ratio between", value1, value2, "manageRatio");
            return (Criteria) this;
        }

        public Criteria andManageRatioNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("manage_ratio not between", value1, value2, "manageRatio");
            return (Criteria) this;
        }

        public Criteria andInvestorCompanyIdIsNull() {
            addCriterion("investor_company_id is null");
            return (Criteria) this;
        }

        public Criteria andInvestorCompanyIdIsNotNull() {
            addCriterion("investor_company_id is not null");
            return (Criteria) this;
        }

        public Criteria andInvestorCompanyIdEqualTo(Integer value) {
            addCriterion("investor_company_id =", value, "investorCompanyId");
            return (Criteria) this;
        }

        public Criteria andInvestorCompanyIdNotEqualTo(Integer value) {
            addCriterion("investor_company_id <>", value, "investorCompanyId");
            return (Criteria) this;
        }

        public Criteria andInvestorCompanyIdGreaterThan(Integer value) {
            addCriterion("investor_company_id >", value, "investorCompanyId");
            return (Criteria) this;
        }

        public Criteria andInvestorCompanyIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("investor_company_id >=", value, "investorCompanyId");
            return (Criteria) this;
        }

        public Criteria andInvestorCompanyIdLessThan(Integer value) {
            addCriterion("investor_company_id <", value, "investorCompanyId");
            return (Criteria) this;
        }

        public Criteria andInvestorCompanyIdLessThanOrEqualTo(Integer value) {
            addCriterion("investor_company_id <=", value, "investorCompanyId");
            return (Criteria) this;
        }

        public Criteria andInvestorCompanyIdIn(List<Integer> values) {
            addCriterion("investor_company_id in", values, "investorCompanyId");
            return (Criteria) this;
        }

        public Criteria andInvestorCompanyIdNotIn(List<Integer> values) {
            addCriterion("investor_company_id not in", values, "investorCompanyId");
            return (Criteria) this;
        }

        public Criteria andInvestorCompanyIdBetween(Integer value1, Integer value2) {
            addCriterion("investor_company_id between", value1, value2, "investorCompanyId");
            return (Criteria) this;
        }

        public Criteria andInvestorCompanyIdNotBetween(Integer value1, Integer value2) {
            addCriterion("investor_company_id not between", value1, value2, "investorCompanyId");
            return (Criteria) this;
        }

        public Criteria andInvestorCompanyIsNull() {
            addCriterion("investor_company is null");
            return (Criteria) this;
        }

        public Criteria andInvestorCompanyIsNotNull() {
            addCriterion("investor_company is not null");
            return (Criteria) this;
        }

        public Criteria andInvestorCompanyEqualTo(String value) {
            addCriterion("investor_company =", value, "investorCompany");
            return (Criteria) this;
        }

        public Criteria andInvestorCompanyNotEqualTo(String value) {
            addCriterion("investor_company <>", value, "investorCompany");
            return (Criteria) this;
        }

        public Criteria andInvestorCompanyGreaterThan(String value) {
            addCriterion("investor_company >", value, "investorCompany");
            return (Criteria) this;
        }

        public Criteria andInvestorCompanyGreaterThanOrEqualTo(String value) {
            addCriterion("investor_company >=", value, "investorCompany");
            return (Criteria) this;
        }

        public Criteria andInvestorCompanyLessThan(String value) {
            addCriterion("investor_company <", value, "investorCompany");
            return (Criteria) this;
        }

        public Criteria andInvestorCompanyLessThanOrEqualTo(String value) {
            addCriterion("investor_company <=", value, "investorCompany");
            return (Criteria) this;
        }

        public Criteria andInvestorCompanyLike(String value) {
            addCriterion("investor_company like", value, "investorCompany");
            return (Criteria) this;
        }

        public Criteria andInvestorCompanyNotLike(String value) {
            addCriterion("investor_company not like", value, "investorCompany");
            return (Criteria) this;
        }

        public Criteria andInvestorCompanyIn(List<String> values) {
            addCriterion("investor_company in", values, "investorCompany");
            return (Criteria) this;
        }

        public Criteria andInvestorCompanyNotIn(List<String> values) {
            addCriterion("investor_company not in", values, "investorCompany");
            return (Criteria) this;
        }

        public Criteria andInvestorCompanyBetween(String value1, String value2) {
            addCriterion("investor_company between", value1, value2, "investorCompany");
            return (Criteria) this;
        }

        public Criteria andInvestorCompanyNotBetween(String value1, String value2) {
            addCriterion("investor_company not between", value1, value2, "investorCompany");
            return (Criteria) this;
        }

        public Criteria andProjectTypeIsNull() {
            addCriterion("project_type is null");
            return (Criteria) this;
        }

        public Criteria andProjectTypeIsNotNull() {
            addCriterion("project_type is not null");
            return (Criteria) this;
        }

        public Criteria andProjectTypeEqualTo(String value) {
            addCriterion("project_type =", value, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeNotEqualTo(String value) {
            addCriterion("project_type <>", value, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeGreaterThan(String value) {
            addCriterion("project_type >", value, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeGreaterThanOrEqualTo(String value) {
            addCriterion("project_type >=", value, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeLessThan(String value) {
            addCriterion("project_type <", value, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeLessThanOrEqualTo(String value) {
            addCriterion("project_type <=", value, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeLike(String value) {
            addCriterion("project_type like", value, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeNotLike(String value) {
            addCriterion("project_type not like", value, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeIn(List<String> values) {
            addCriterion("project_type in", values, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeNotIn(List<String> values) {
            addCriterion("project_type not in", values, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeBetween(String value1, String value2) {
            addCriterion("project_type between", value1, value2, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeNotBetween(String value1, String value2) {
            addCriterion("project_type not between", value1, value2, "projectType");
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

        public Criteria andExplanIsNull() {
            addCriterion("explan is null");
            return (Criteria) this;
        }

        public Criteria andExplanIsNotNull() {
            addCriterion("explan is not null");
            return (Criteria) this;
        }

        public Criteria andExplanEqualTo(String value) {
            addCriterion("explan =", value, "explan");
            return (Criteria) this;
        }

        public Criteria andExplanNotEqualTo(String value) {
            addCriterion("explan <>", value, "explan");
            return (Criteria) this;
        }

        public Criteria andExplanGreaterThan(String value) {
            addCriterion("explan >", value, "explan");
            return (Criteria) this;
        }

        public Criteria andExplanGreaterThanOrEqualTo(String value) {
            addCriterion("explan >=", value, "explan");
            return (Criteria) this;
        }

        public Criteria andExplanLessThan(String value) {
            addCriterion("explan <", value, "explan");
            return (Criteria) this;
        }

        public Criteria andExplanLessThanOrEqualTo(String value) {
            addCriterion("explan <=", value, "explan");
            return (Criteria) this;
        }

        public Criteria andExplanLike(String value) {
            addCriterion("explan like", value, "explan");
            return (Criteria) this;
        }

        public Criteria andExplanNotLike(String value) {
            addCriterion("explan not like", value, "explan");
            return (Criteria) this;
        }

        public Criteria andExplanIn(List<String> values) {
            addCriterion("explan in", values, "explan");
            return (Criteria) this;
        }

        public Criteria andExplanNotIn(List<String> values) {
            addCriterion("explan not in", values, "explan");
            return (Criteria) this;
        }

        public Criteria andExplanBetween(String value1, String value2) {
            addCriterion("explan between", value1, value2, "explan");
            return (Criteria) this;
        }

        public Criteria andExplanNotBetween(String value1, String value2) {
            addCriterion("explan not between", value1, value2, "explan");
            return (Criteria) this;
        }

        public Criteria andCreatereIsNull() {
            addCriterion("createre is null");
            return (Criteria) this;
        }

        public Criteria andCreatereIsNotNull() {
            addCriterion("createre is not null");
            return (Criteria) this;
        }

        public Criteria andCreatereEqualTo(Integer value) {
            addCriterion("createre =", value, "createre");
            return (Criteria) this;
        }

        public Criteria andCreatereNotEqualTo(Integer value) {
            addCriterion("createre <>", value, "createre");
            return (Criteria) this;
        }

        public Criteria andCreatereGreaterThan(Integer value) {
            addCriterion("createre >", value, "createre");
            return (Criteria) this;
        }

        public Criteria andCreatereGreaterThanOrEqualTo(Integer value) {
            addCriterion("createre >=", value, "createre");
            return (Criteria) this;
        }

        public Criteria andCreatereLessThan(Integer value) {
            addCriterion("createre <", value, "createre");
            return (Criteria) this;
        }

        public Criteria andCreatereLessThanOrEqualTo(Integer value) {
            addCriterion("createre <=", value, "createre");
            return (Criteria) this;
        }

        public Criteria andCreatereIn(List<Integer> values) {
            addCriterion("createre in", values, "createre");
            return (Criteria) this;
        }

        public Criteria andCreatereNotIn(List<Integer> values) {
            addCriterion("createre not in", values, "createre");
            return (Criteria) this;
        }

        public Criteria andCreatereBetween(Integer value1, Integer value2) {
            addCriterion("createre between", value1, value2, "createre");
            return (Criteria) this;
        }

        public Criteria andCreatereNotBetween(Integer value1, Integer value2) {
            addCriterion("createre not between", value1, value2, "createre");
            return (Criteria) this;
        }

        public Criteria andUpdaterIsNull() {
            addCriterion("updater is null");
            return (Criteria) this;
        }

        public Criteria andUpdaterIsNotNull() {
            addCriterion("updater is not null");
            return (Criteria) this;
        }

        public Criteria andUpdaterEqualTo(Integer value) {
            addCriterion("updater =", value, "updater");
            return (Criteria) this;
        }

        public Criteria andUpdaterNotEqualTo(Integer value) {
            addCriterion("updater <>", value, "updater");
            return (Criteria) this;
        }

        public Criteria andUpdaterGreaterThan(Integer value) {
            addCriterion("updater >", value, "updater");
            return (Criteria) this;
        }

        public Criteria andUpdaterGreaterThanOrEqualTo(Integer value) {
            addCriterion("updater >=", value, "updater");
            return (Criteria) this;
        }

        public Criteria andUpdaterLessThan(Integer value) {
            addCriterion("updater <", value, "updater");
            return (Criteria) this;
        }

        public Criteria andUpdaterLessThanOrEqualTo(Integer value) {
            addCriterion("updater <=", value, "updater");
            return (Criteria) this;
        }

        public Criteria andUpdaterIn(List<Integer> values) {
            addCriterion("updater in", values, "updater");
            return (Criteria) this;
        }

        public Criteria andUpdaterNotIn(List<Integer> values) {
            addCriterion("updater not in", values, "updater");
            return (Criteria) this;
        }

        public Criteria andUpdaterBetween(Integer value1, Integer value2) {
            addCriterion("updater between", value1, value2, "updater");
            return (Criteria) this;
        }

        public Criteria andUpdaterNotBetween(Integer value1, Integer value2) {
            addCriterion("updater not between", value1, value2, "updater");
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