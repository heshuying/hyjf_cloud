package com.hyjf.am.user.dao.model.auto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MspApplyExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public MspApplyExample() {
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

        public Criteria andApplyIdIsNull() {
            addCriterion("apply_id is null");
            return (Criteria) this;
        }

        public Criteria andApplyIdIsNotNull() {
            addCriterion("apply_id is not null");
            return (Criteria) this;
        }

        public Criteria andApplyIdEqualTo(String value) {
            addCriterion("apply_id =", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdNotEqualTo(String value) {
            addCriterion("apply_id <>", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdGreaterThan(String value) {
            addCriterion("apply_id >", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdGreaterThanOrEqualTo(String value) {
            addCriterion("apply_id >=", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdLessThan(String value) {
            addCriterion("apply_id <", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdLessThanOrEqualTo(String value) {
            addCriterion("apply_id <=", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdLike(String value) {
            addCriterion("apply_id like", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdNotLike(String value) {
            addCriterion("apply_id not like", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdIn(List<String> values) {
            addCriterion("apply_id in", values, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdNotIn(List<String> values) {
            addCriterion("apply_id not in", values, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdBetween(String value1, String value2) {
            addCriterion("apply_id between", value1, value2, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdNotBetween(String value1, String value2) {
            addCriterion("apply_id not between", value1, value2, "applyId");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("`name` is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("`name` is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("`name` =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("`name` <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("`name` >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("`name` >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("`name` <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("`name` <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("`name` like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("`name` not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("`name` in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("`name` not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("`name` between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("`name` not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andIdentityCardIsNull() {
            addCriterion("identity_card is null");
            return (Criteria) this;
        }

        public Criteria andIdentityCardIsNotNull() {
            addCriterion("identity_card is not null");
            return (Criteria) this;
        }

        public Criteria andIdentityCardEqualTo(String value) {
            addCriterion("identity_card =", value, "identityCard");
            return (Criteria) this;
        }

        public Criteria andIdentityCardNotEqualTo(String value) {
            addCriterion("identity_card <>", value, "identityCard");
            return (Criteria) this;
        }

        public Criteria andIdentityCardGreaterThan(String value) {
            addCriterion("identity_card >", value, "identityCard");
            return (Criteria) this;
        }

        public Criteria andIdentityCardGreaterThanOrEqualTo(String value) {
            addCriterion("identity_card >=", value, "identityCard");
            return (Criteria) this;
        }

        public Criteria andIdentityCardLessThan(String value) {
            addCriterion("identity_card <", value, "identityCard");
            return (Criteria) this;
        }

        public Criteria andIdentityCardLessThanOrEqualTo(String value) {
            addCriterion("identity_card <=", value, "identityCard");
            return (Criteria) this;
        }

        public Criteria andIdentityCardLike(String value) {
            addCriterion("identity_card like", value, "identityCard");
            return (Criteria) this;
        }

        public Criteria andIdentityCardNotLike(String value) {
            addCriterion("identity_card not like", value, "identityCard");
            return (Criteria) this;
        }

        public Criteria andIdentityCardIn(List<String> values) {
            addCriterion("identity_card in", values, "identityCard");
            return (Criteria) this;
        }

        public Criteria andIdentityCardNotIn(List<String> values) {
            addCriterion("identity_card not in", values, "identityCard");
            return (Criteria) this;
        }

        public Criteria andIdentityCardBetween(String value1, String value2) {
            addCriterion("identity_card between", value1, value2, "identityCard");
            return (Criteria) this;
        }

        public Criteria andIdentityCardNotBetween(String value1, String value2) {
            addCriterion("identity_card not between", value1, value2, "identityCard");
            return (Criteria) this;
        }

        public Criteria andMobileNoIsNull() {
            addCriterion("mobile_no is null");
            return (Criteria) this;
        }

        public Criteria andMobileNoIsNotNull() {
            addCriterion("mobile_no is not null");
            return (Criteria) this;
        }

        public Criteria andMobileNoEqualTo(String value) {
            addCriterion("mobile_no =", value, "mobileNo");
            return (Criteria) this;
        }

        public Criteria andMobileNoNotEqualTo(String value) {
            addCriterion("mobile_no <>", value, "mobileNo");
            return (Criteria) this;
        }

        public Criteria andMobileNoGreaterThan(String value) {
            addCriterion("mobile_no >", value, "mobileNo");
            return (Criteria) this;
        }

        public Criteria andMobileNoGreaterThanOrEqualTo(String value) {
            addCriterion("mobile_no >=", value, "mobileNo");
            return (Criteria) this;
        }

        public Criteria andMobileNoLessThan(String value) {
            addCriterion("mobile_no <", value, "mobileNo");
            return (Criteria) this;
        }

        public Criteria andMobileNoLessThanOrEqualTo(String value) {
            addCriterion("mobile_no <=", value, "mobileNo");
            return (Criteria) this;
        }

        public Criteria andMobileNoLike(String value) {
            addCriterion("mobile_no like", value, "mobileNo");
            return (Criteria) this;
        }

        public Criteria andMobileNoNotLike(String value) {
            addCriterion("mobile_no not like", value, "mobileNo");
            return (Criteria) this;
        }

        public Criteria andMobileNoIn(List<String> values) {
            addCriterion("mobile_no in", values, "mobileNo");
            return (Criteria) this;
        }

        public Criteria andMobileNoNotIn(List<String> values) {
            addCriterion("mobile_no not in", values, "mobileNo");
            return (Criteria) this;
        }

        public Criteria andMobileNoBetween(String value1, String value2) {
            addCriterion("mobile_no between", value1, value2, "mobileNo");
            return (Criteria) this;
        }

        public Criteria andMobileNoNotBetween(String value1, String value2) {
            addCriterion("mobile_no not between", value1, value2, "mobileNo");
            return (Criteria) this;
        }

        public Criteria andApplyDateIsNull() {
            addCriterion("apply_date is null");
            return (Criteria) this;
        }

        public Criteria andApplyDateIsNotNull() {
            addCriterion("apply_date is not null");
            return (Criteria) this;
        }

        public Criteria andApplyDateEqualTo(String value) {
            addCriterion("apply_date =", value, "applyDate");
            return (Criteria) this;
        }

        public Criteria andApplyDateNotEqualTo(String value) {
            addCriterion("apply_date <>", value, "applyDate");
            return (Criteria) this;
        }

        public Criteria andApplyDateGreaterThan(String value) {
            addCriterion("apply_date >", value, "applyDate");
            return (Criteria) this;
        }

        public Criteria andApplyDateGreaterThanOrEqualTo(String value) {
            addCriterion("apply_date >=", value, "applyDate");
            return (Criteria) this;
        }

        public Criteria andApplyDateLessThan(String value) {
            addCriterion("apply_date <", value, "applyDate");
            return (Criteria) this;
        }

        public Criteria andApplyDateLessThanOrEqualTo(String value) {
            addCriterion("apply_date <=", value, "applyDate");
            return (Criteria) this;
        }

        public Criteria andApplyDateLike(String value) {
            addCriterion("apply_date like", value, "applyDate");
            return (Criteria) this;
        }

        public Criteria andApplyDateNotLike(String value) {
            addCriterion("apply_date not like", value, "applyDate");
            return (Criteria) this;
        }

        public Criteria andApplyDateIn(List<String> values) {
            addCriterion("apply_date in", values, "applyDate");
            return (Criteria) this;
        }

        public Criteria andApplyDateNotIn(List<String> values) {
            addCriterion("apply_date not in", values, "applyDate");
            return (Criteria) this;
        }

        public Criteria andApplyDateBetween(String value1, String value2) {
            addCriterion("apply_date between", value1, value2, "applyDate");
            return (Criteria) this;
        }

        public Criteria andApplyDateNotBetween(String value1, String value2) {
            addCriterion("apply_date not between", value1, value2, "applyDate");
            return (Criteria) this;
        }

        public Criteria andLoanTypeIsNull() {
            addCriterion("loan_type is null");
            return (Criteria) this;
        }

        public Criteria andLoanTypeIsNotNull() {
            addCriterion("loan_type is not null");
            return (Criteria) this;
        }

        public Criteria andLoanTypeEqualTo(String value) {
            addCriterion("loan_type =", value, "loanType");
            return (Criteria) this;
        }

        public Criteria andLoanTypeNotEqualTo(String value) {
            addCriterion("loan_type <>", value, "loanType");
            return (Criteria) this;
        }

        public Criteria andLoanTypeGreaterThan(String value) {
            addCriterion("loan_type >", value, "loanType");
            return (Criteria) this;
        }

        public Criteria andLoanTypeGreaterThanOrEqualTo(String value) {
            addCriterion("loan_type >=", value, "loanType");
            return (Criteria) this;
        }

        public Criteria andLoanTypeLessThan(String value) {
            addCriterion("loan_type <", value, "loanType");
            return (Criteria) this;
        }

        public Criteria andLoanTypeLessThanOrEqualTo(String value) {
            addCriterion("loan_type <=", value, "loanType");
            return (Criteria) this;
        }

        public Criteria andLoanTypeLike(String value) {
            addCriterion("loan_type like", value, "loanType");
            return (Criteria) this;
        }

        public Criteria andLoanTypeNotLike(String value) {
            addCriterion("loan_type not like", value, "loanType");
            return (Criteria) this;
        }

        public Criteria andLoanTypeIn(List<String> values) {
            addCriterion("loan_type in", values, "loanType");
            return (Criteria) this;
        }

        public Criteria andLoanTypeNotIn(List<String> values) {
            addCriterion("loan_type not in", values, "loanType");
            return (Criteria) this;
        }

        public Criteria andLoanTypeBetween(String value1, String value2) {
            addCriterion("loan_type between", value1, value2, "loanType");
            return (Criteria) this;
        }

        public Criteria andLoanTypeNotBetween(String value1, String value2) {
            addCriterion("loan_type not between", value1, value2, "loanType");
            return (Criteria) this;
        }

        public Criteria andLoanMoneyIsNull() {
            addCriterion("loan_money is null");
            return (Criteria) this;
        }

        public Criteria andLoanMoneyIsNotNull() {
            addCriterion("loan_money is not null");
            return (Criteria) this;
        }

        public Criteria andLoanMoneyEqualTo(BigDecimal value) {
            addCriterion("loan_money =", value, "loanMoney");
            return (Criteria) this;
        }

        public Criteria andLoanMoneyNotEqualTo(BigDecimal value) {
            addCriterion("loan_money <>", value, "loanMoney");
            return (Criteria) this;
        }

        public Criteria andLoanMoneyGreaterThan(BigDecimal value) {
            addCriterion("loan_money >", value, "loanMoney");
            return (Criteria) this;
        }

        public Criteria andLoanMoneyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("loan_money >=", value, "loanMoney");
            return (Criteria) this;
        }

        public Criteria andLoanMoneyLessThan(BigDecimal value) {
            addCriterion("loan_money <", value, "loanMoney");
            return (Criteria) this;
        }

        public Criteria andLoanMoneyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("loan_money <=", value, "loanMoney");
            return (Criteria) this;
        }

        public Criteria andLoanMoneyIn(List<BigDecimal> values) {
            addCriterion("loan_money in", values, "loanMoney");
            return (Criteria) this;
        }

        public Criteria andLoanMoneyNotIn(List<BigDecimal> values) {
            addCriterion("loan_money not in", values, "loanMoney");
            return (Criteria) this;
        }

        public Criteria andLoanMoneyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("loan_money between", value1, value2, "loanMoney");
            return (Criteria) this;
        }

        public Criteria andLoanMoneyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("loan_money not between", value1, value2, "loanMoney");
            return (Criteria) this;
        }

        public Criteria andLoanTimeLimitIsNull() {
            addCriterion("loan_time_limit is null");
            return (Criteria) this;
        }

        public Criteria andLoanTimeLimitIsNotNull() {
            addCriterion("loan_time_limit is not null");
            return (Criteria) this;
        }

        public Criteria andLoanTimeLimitEqualTo(Integer value) {
            addCriterion("loan_time_limit =", value, "loanTimeLimit");
            return (Criteria) this;
        }

        public Criteria andLoanTimeLimitNotEqualTo(Integer value) {
            addCriterion("loan_time_limit <>", value, "loanTimeLimit");
            return (Criteria) this;
        }

        public Criteria andLoanTimeLimitGreaterThan(Integer value) {
            addCriterion("loan_time_limit >", value, "loanTimeLimit");
            return (Criteria) this;
        }

        public Criteria andLoanTimeLimitGreaterThanOrEqualTo(Integer value) {
            addCriterion("loan_time_limit >=", value, "loanTimeLimit");
            return (Criteria) this;
        }

        public Criteria andLoanTimeLimitLessThan(Integer value) {
            addCriterion("loan_time_limit <", value, "loanTimeLimit");
            return (Criteria) this;
        }

        public Criteria andLoanTimeLimitLessThanOrEqualTo(Integer value) {
            addCriterion("loan_time_limit <=", value, "loanTimeLimit");
            return (Criteria) this;
        }

        public Criteria andLoanTimeLimitIn(List<Integer> values) {
            addCriterion("loan_time_limit in", values, "loanTimeLimit");
            return (Criteria) this;
        }

        public Criteria andLoanTimeLimitNotIn(List<Integer> values) {
            addCriterion("loan_time_limit not in", values, "loanTimeLimit");
            return (Criteria) this;
        }

        public Criteria andLoanTimeLimitBetween(Integer value1, Integer value2) {
            addCriterion("loan_time_limit between", value1, value2, "loanTimeLimit");
            return (Criteria) this;
        }

        public Criteria andLoanTimeLimitNotBetween(Integer value1, Integer value2) {
            addCriterion("loan_time_limit not between", value1, value2, "loanTimeLimit");
            return (Criteria) this;
        }

        public Criteria andCreditAddressIsNull() {
            addCriterion("credit_address is null");
            return (Criteria) this;
        }

        public Criteria andCreditAddressIsNotNull() {
            addCriterion("credit_address is not null");
            return (Criteria) this;
        }

        public Criteria andCreditAddressEqualTo(String value) {
            addCriterion("credit_address =", value, "creditAddress");
            return (Criteria) this;
        }

        public Criteria andCreditAddressNotEqualTo(String value) {
            addCriterion("credit_address <>", value, "creditAddress");
            return (Criteria) this;
        }

        public Criteria andCreditAddressGreaterThan(String value) {
            addCriterion("credit_address >", value, "creditAddress");
            return (Criteria) this;
        }

        public Criteria andCreditAddressGreaterThanOrEqualTo(String value) {
            addCriterion("credit_address >=", value, "creditAddress");
            return (Criteria) this;
        }

        public Criteria andCreditAddressLessThan(String value) {
            addCriterion("credit_address <", value, "creditAddress");
            return (Criteria) this;
        }

        public Criteria andCreditAddressLessThanOrEqualTo(String value) {
            addCriterion("credit_address <=", value, "creditAddress");
            return (Criteria) this;
        }

        public Criteria andCreditAddressLike(String value) {
            addCriterion("credit_address like", value, "creditAddress");
            return (Criteria) this;
        }

        public Criteria andCreditAddressNotLike(String value) {
            addCriterion("credit_address not like", value, "creditAddress");
            return (Criteria) this;
        }

        public Criteria andCreditAddressIn(List<String> values) {
            addCriterion("credit_address in", values, "creditAddress");
            return (Criteria) this;
        }

        public Criteria andCreditAddressNotIn(List<String> values) {
            addCriterion("credit_address not in", values, "creditAddress");
            return (Criteria) this;
        }

        public Criteria andCreditAddressBetween(String value1, String value2) {
            addCriterion("credit_address between", value1, value2, "creditAddress");
            return (Criteria) this;
        }

        public Criteria andCreditAddressNotBetween(String value1, String value2) {
            addCriterion("credit_address not between", value1, value2, "creditAddress");
            return (Criteria) this;
        }

        public Criteria andShareIdentificationIsNull() {
            addCriterion("share_identification is null");
            return (Criteria) this;
        }

        public Criteria andShareIdentificationIsNotNull() {
            addCriterion("share_identification is not null");
            return (Criteria) this;
        }

        public Criteria andShareIdentificationEqualTo(Integer value) {
            addCriterion("share_identification =", value, "shareIdentification");
            return (Criteria) this;
        }

        public Criteria andShareIdentificationNotEqualTo(Integer value) {
            addCriterion("share_identification <>", value, "shareIdentification");
            return (Criteria) this;
        }

        public Criteria andShareIdentificationGreaterThan(Integer value) {
            addCriterion("share_identification >", value, "shareIdentification");
            return (Criteria) this;
        }

        public Criteria andShareIdentificationGreaterThanOrEqualTo(Integer value) {
            addCriterion("share_identification >=", value, "shareIdentification");
            return (Criteria) this;
        }

        public Criteria andShareIdentificationLessThan(Integer value) {
            addCriterion("share_identification <", value, "shareIdentification");
            return (Criteria) this;
        }

        public Criteria andShareIdentificationLessThanOrEqualTo(Integer value) {
            addCriterion("share_identification <=", value, "shareIdentification");
            return (Criteria) this;
        }

        public Criteria andShareIdentificationIn(List<Integer> values) {
            addCriterion("share_identification in", values, "shareIdentification");
            return (Criteria) this;
        }

        public Criteria andShareIdentificationNotIn(List<Integer> values) {
            addCriterion("share_identification not in", values, "shareIdentification");
            return (Criteria) this;
        }

        public Criteria andShareIdentificationBetween(Integer value1, Integer value2) {
            addCriterion("share_identification between", value1, value2, "shareIdentification");
            return (Criteria) this;
        }

        public Criteria andShareIdentificationNotBetween(Integer value1, Integer value2) {
            addCriterion("share_identification not between", value1, value2, "shareIdentification");
            return (Criteria) this;
        }

        public Criteria andServiceTypeIsNull() {
            addCriterion("service_type is null");
            return (Criteria) this;
        }

        public Criteria andServiceTypeIsNotNull() {
            addCriterion("service_type is not null");
            return (Criteria) this;
        }

        public Criteria andServiceTypeEqualTo(String value) {
            addCriterion("service_type =", value, "serviceType");
            return (Criteria) this;
        }

        public Criteria andServiceTypeNotEqualTo(String value) {
            addCriterion("service_type <>", value, "serviceType");
            return (Criteria) this;
        }

        public Criteria andServiceTypeGreaterThan(String value) {
            addCriterion("service_type >", value, "serviceType");
            return (Criteria) this;
        }

        public Criteria andServiceTypeGreaterThanOrEqualTo(String value) {
            addCriterion("service_type >=", value, "serviceType");
            return (Criteria) this;
        }

        public Criteria andServiceTypeLessThan(String value) {
            addCriterion("service_type <", value, "serviceType");
            return (Criteria) this;
        }

        public Criteria andServiceTypeLessThanOrEqualTo(String value) {
            addCriterion("service_type <=", value, "serviceType");
            return (Criteria) this;
        }

        public Criteria andServiceTypeLike(String value) {
            addCriterion("service_type like", value, "serviceType");
            return (Criteria) this;
        }

        public Criteria andServiceTypeNotLike(String value) {
            addCriterion("service_type not like", value, "serviceType");
            return (Criteria) this;
        }

        public Criteria andServiceTypeIn(List<String> values) {
            addCriterion("service_type in", values, "serviceType");
            return (Criteria) this;
        }

        public Criteria andServiceTypeNotIn(List<String> values) {
            addCriterion("service_type not in", values, "serviceType");
            return (Criteria) this;
        }

        public Criteria andServiceTypeBetween(String value1, String value2) {
            addCriterion("service_type between", value1, value2, "serviceType");
            return (Criteria) this;
        }

        public Criteria andServiceTypeNotBetween(String value1, String value2) {
            addCriterion("service_type not between", value1, value2, "serviceType");
            return (Criteria) this;
        }

        public Criteria andUnredeemedMoneyIsNull() {
            addCriterion("unredeemed_money is null");
            return (Criteria) this;
        }

        public Criteria andUnredeemedMoneyIsNotNull() {
            addCriterion("unredeemed_money is not null");
            return (Criteria) this;
        }

        public Criteria andUnredeemedMoneyEqualTo(BigDecimal value) {
            addCriterion("unredeemed_money =", value, "unredeemedMoney");
            return (Criteria) this;
        }

        public Criteria andUnredeemedMoneyNotEqualTo(BigDecimal value) {
            addCriterion("unredeemed_money <>", value, "unredeemedMoney");
            return (Criteria) this;
        }

        public Criteria andUnredeemedMoneyGreaterThan(BigDecimal value) {
            addCriterion("unredeemed_money >", value, "unredeemedMoney");
            return (Criteria) this;
        }

        public Criteria andUnredeemedMoneyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("unredeemed_money >=", value, "unredeemedMoney");
            return (Criteria) this;
        }

        public Criteria andUnredeemedMoneyLessThan(BigDecimal value) {
            addCriterion("unredeemed_money <", value, "unredeemedMoney");
            return (Criteria) this;
        }

        public Criteria andUnredeemedMoneyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("unredeemed_money <=", value, "unredeemedMoney");
            return (Criteria) this;
        }

        public Criteria andUnredeemedMoneyIn(List<BigDecimal> values) {
            addCriterion("unredeemed_money in", values, "unredeemedMoney");
            return (Criteria) this;
        }

        public Criteria andUnredeemedMoneyNotIn(List<BigDecimal> values) {
            addCriterion("unredeemed_money not in", values, "unredeemedMoney");
            return (Criteria) this;
        }

        public Criteria andUnredeemedMoneyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("unredeemed_money between", value1, value2, "unredeemedMoney");
            return (Criteria) this;
        }

        public Criteria andUnredeemedMoneyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("unredeemed_money not between", value1, value2, "unredeemedMoney");
            return (Criteria) this;
        }

        public Criteria andRepaymentStatusIsNull() {
            addCriterion("repayment_status is null");
            return (Criteria) this;
        }

        public Criteria andRepaymentStatusIsNotNull() {
            addCriterion("repayment_status is not null");
            return (Criteria) this;
        }

        public Criteria andRepaymentStatusEqualTo(String value) {
            addCriterion("repayment_status =", value, "repaymentStatus");
            return (Criteria) this;
        }

        public Criteria andRepaymentStatusNotEqualTo(String value) {
            addCriterion("repayment_status <>", value, "repaymentStatus");
            return (Criteria) this;
        }

        public Criteria andRepaymentStatusGreaterThan(String value) {
            addCriterion("repayment_status >", value, "repaymentStatus");
            return (Criteria) this;
        }

        public Criteria andRepaymentStatusGreaterThanOrEqualTo(String value) {
            addCriterion("repayment_status >=", value, "repaymentStatus");
            return (Criteria) this;
        }

        public Criteria andRepaymentStatusLessThan(String value) {
            addCriterion("repayment_status <", value, "repaymentStatus");
            return (Criteria) this;
        }

        public Criteria andRepaymentStatusLessThanOrEqualTo(String value) {
            addCriterion("repayment_status <=", value, "repaymentStatus");
            return (Criteria) this;
        }

        public Criteria andRepaymentStatusLike(String value) {
            addCriterion("repayment_status like", value, "repaymentStatus");
            return (Criteria) this;
        }

        public Criteria andRepaymentStatusNotLike(String value) {
            addCriterion("repayment_status not like", value, "repaymentStatus");
            return (Criteria) this;
        }

        public Criteria andRepaymentStatusIn(List<String> values) {
            addCriterion("repayment_status in", values, "repaymentStatus");
            return (Criteria) this;
        }

        public Criteria andRepaymentStatusNotIn(List<String> values) {
            addCriterion("repayment_status not in", values, "repaymentStatus");
            return (Criteria) this;
        }

        public Criteria andRepaymentStatusBetween(String value1, String value2) {
            addCriterion("repayment_status between", value1, value2, "repaymentStatus");
            return (Criteria) this;
        }

        public Criteria andRepaymentStatusNotBetween(String value1, String value2) {
            addCriterion("repayment_status not between", value1, value2, "repaymentStatus");
            return (Criteria) this;
        }

        public Criteria andOverdueAmountIsNull() {
            addCriterion("overdue_amount is null");
            return (Criteria) this;
        }

        public Criteria andOverdueAmountIsNotNull() {
            addCriterion("overdue_amount is not null");
            return (Criteria) this;
        }

        public Criteria andOverdueAmountEqualTo(BigDecimal value) {
            addCriterion("overdue_amount =", value, "overdueAmount");
            return (Criteria) this;
        }

        public Criteria andOverdueAmountNotEqualTo(BigDecimal value) {
            addCriterion("overdue_amount <>", value, "overdueAmount");
            return (Criteria) this;
        }

        public Criteria andOverdueAmountGreaterThan(BigDecimal value) {
            addCriterion("overdue_amount >", value, "overdueAmount");
            return (Criteria) this;
        }

        public Criteria andOverdueAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("overdue_amount >=", value, "overdueAmount");
            return (Criteria) this;
        }

        public Criteria andOverdueAmountLessThan(BigDecimal value) {
            addCriterion("overdue_amount <", value, "overdueAmount");
            return (Criteria) this;
        }

        public Criteria andOverdueAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("overdue_amount <=", value, "overdueAmount");
            return (Criteria) this;
        }

        public Criteria andOverdueAmountIn(List<BigDecimal> values) {
            addCriterion("overdue_amount in", values, "overdueAmount");
            return (Criteria) this;
        }

        public Criteria andOverdueAmountNotIn(List<BigDecimal> values) {
            addCriterion("overdue_amount not in", values, "overdueAmount");
            return (Criteria) this;
        }

        public Criteria andOverdueAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("overdue_amount between", value1, value2, "overdueAmount");
            return (Criteria) this;
        }

        public Criteria andOverdueAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("overdue_amount not between", value1, value2, "overdueAmount");
            return (Criteria) this;
        }

        public Criteria andOverdueDateIsNull() {
            addCriterion("overdue_date is null");
            return (Criteria) this;
        }

        public Criteria andOverdueDateIsNotNull() {
            addCriterion("overdue_date is not null");
            return (Criteria) this;
        }

        public Criteria andOverdueDateEqualTo(String value) {
            addCriterion("overdue_date =", value, "overdueDate");
            return (Criteria) this;
        }

        public Criteria andOverdueDateNotEqualTo(String value) {
            addCriterion("overdue_date <>", value, "overdueDate");
            return (Criteria) this;
        }

        public Criteria andOverdueDateGreaterThan(String value) {
            addCriterion("overdue_date >", value, "overdueDate");
            return (Criteria) this;
        }

        public Criteria andOverdueDateGreaterThanOrEqualTo(String value) {
            addCriterion("overdue_date >=", value, "overdueDate");
            return (Criteria) this;
        }

        public Criteria andOverdueDateLessThan(String value) {
            addCriterion("overdue_date <", value, "overdueDate");
            return (Criteria) this;
        }

        public Criteria andOverdueDateLessThanOrEqualTo(String value) {
            addCriterion("overdue_date <=", value, "overdueDate");
            return (Criteria) this;
        }

        public Criteria andOverdueDateLike(String value) {
            addCriterion("overdue_date like", value, "overdueDate");
            return (Criteria) this;
        }

        public Criteria andOverdueDateNotLike(String value) {
            addCriterion("overdue_date not like", value, "overdueDate");
            return (Criteria) this;
        }

        public Criteria andOverdueDateIn(List<String> values) {
            addCriterion("overdue_date in", values, "overdueDate");
            return (Criteria) this;
        }

        public Criteria andOverdueDateNotIn(List<String> values) {
            addCriterion("overdue_date not in", values, "overdueDate");
            return (Criteria) this;
        }

        public Criteria andOverdueDateBetween(String value1, String value2) {
            addCriterion("overdue_date between", value1, value2, "overdueDate");
            return (Criteria) this;
        }

        public Criteria andOverdueDateNotBetween(String value1, String value2) {
            addCriterion("overdue_date not between", value1, value2, "overdueDate");
            return (Criteria) this;
        }

        public Criteria andOverdueLengthIsNull() {
            addCriterion("overdue_length is null");
            return (Criteria) this;
        }

        public Criteria andOverdueLengthIsNotNull() {
            addCriterion("overdue_length is not null");
            return (Criteria) this;
        }

        public Criteria andOverdueLengthEqualTo(String value) {
            addCriterion("overdue_length =", value, "overdueLength");
            return (Criteria) this;
        }

        public Criteria andOverdueLengthNotEqualTo(String value) {
            addCriterion("overdue_length <>", value, "overdueLength");
            return (Criteria) this;
        }

        public Criteria andOverdueLengthGreaterThan(String value) {
            addCriterion("overdue_length >", value, "overdueLength");
            return (Criteria) this;
        }

        public Criteria andOverdueLengthGreaterThanOrEqualTo(String value) {
            addCriterion("overdue_length >=", value, "overdueLength");
            return (Criteria) this;
        }

        public Criteria andOverdueLengthLessThan(String value) {
            addCriterion("overdue_length <", value, "overdueLength");
            return (Criteria) this;
        }

        public Criteria andOverdueLengthLessThanOrEqualTo(String value) {
            addCriterion("overdue_length <=", value, "overdueLength");
            return (Criteria) this;
        }

        public Criteria andOverdueLengthLike(String value) {
            addCriterion("overdue_length like", value, "overdueLength");
            return (Criteria) this;
        }

        public Criteria andOverdueLengthNotLike(String value) {
            addCriterion("overdue_length not like", value, "overdueLength");
            return (Criteria) this;
        }

        public Criteria andOverdueLengthIn(List<String> values) {
            addCriterion("overdue_length in", values, "overdueLength");
            return (Criteria) this;
        }

        public Criteria andOverdueLengthNotIn(List<String> values) {
            addCriterion("overdue_length not in", values, "overdueLength");
            return (Criteria) this;
        }

        public Criteria andOverdueLengthBetween(String value1, String value2) {
            addCriterion("overdue_length between", value1, value2, "overdueLength");
            return (Criteria) this;
        }

        public Criteria andOverdueLengthNotBetween(String value1, String value2) {
            addCriterion("overdue_length not between", value1, value2, "overdueLength");
            return (Criteria) this;
        }

        public Criteria andOverdueReasonIsNull() {
            addCriterion("overdue_reason is null");
            return (Criteria) this;
        }

        public Criteria andOverdueReasonIsNotNull() {
            addCriterion("overdue_reason is not null");
            return (Criteria) this;
        }

        public Criteria andOverdueReasonEqualTo(String value) {
            addCriterion("overdue_reason =", value, "overdueReason");
            return (Criteria) this;
        }

        public Criteria andOverdueReasonNotEqualTo(String value) {
            addCriterion("overdue_reason <>", value, "overdueReason");
            return (Criteria) this;
        }

        public Criteria andOverdueReasonGreaterThan(String value) {
            addCriterion("overdue_reason >", value, "overdueReason");
            return (Criteria) this;
        }

        public Criteria andOverdueReasonGreaterThanOrEqualTo(String value) {
            addCriterion("overdue_reason >=", value, "overdueReason");
            return (Criteria) this;
        }

        public Criteria andOverdueReasonLessThan(String value) {
            addCriterion("overdue_reason <", value, "overdueReason");
            return (Criteria) this;
        }

        public Criteria andOverdueReasonLessThanOrEqualTo(String value) {
            addCriterion("overdue_reason <=", value, "overdueReason");
            return (Criteria) this;
        }

        public Criteria andOverdueReasonLike(String value) {
            addCriterion("overdue_reason like", value, "overdueReason");
            return (Criteria) this;
        }

        public Criteria andOverdueReasonNotLike(String value) {
            addCriterion("overdue_reason not like", value, "overdueReason");
            return (Criteria) this;
        }

        public Criteria andOverdueReasonIn(List<String> values) {
            addCriterion("overdue_reason in", values, "overdueReason");
            return (Criteria) this;
        }

        public Criteria andOverdueReasonNotIn(List<String> values) {
            addCriterion("overdue_reason not in", values, "overdueReason");
            return (Criteria) this;
        }

        public Criteria andOverdueReasonBetween(String value1, String value2) {
            addCriterion("overdue_reason between", value1, value2, "overdueReason");
            return (Criteria) this;
        }

        public Criteria andOverdueReasonNotBetween(String value1, String value2) {
            addCriterion("overdue_reason not between", value1, value2, "overdueReason");
            return (Criteria) this;
        }

        public Criteria andApprovalResultIsNull() {
            addCriterion("approval_result is null");
            return (Criteria) this;
        }

        public Criteria andApprovalResultIsNotNull() {
            addCriterion("approval_result is not null");
            return (Criteria) this;
        }

        public Criteria andApprovalResultEqualTo(String value) {
            addCriterion("approval_result =", value, "approvalResult");
            return (Criteria) this;
        }

        public Criteria andApprovalResultNotEqualTo(String value) {
            addCriterion("approval_result <>", value, "approvalResult");
            return (Criteria) this;
        }

        public Criteria andApprovalResultGreaterThan(String value) {
            addCriterion("approval_result >", value, "approvalResult");
            return (Criteria) this;
        }

        public Criteria andApprovalResultGreaterThanOrEqualTo(String value) {
            addCriterion("approval_result >=", value, "approvalResult");
            return (Criteria) this;
        }

        public Criteria andApprovalResultLessThan(String value) {
            addCriterion("approval_result <", value, "approvalResult");
            return (Criteria) this;
        }

        public Criteria andApprovalResultLessThanOrEqualTo(String value) {
            addCriterion("approval_result <=", value, "approvalResult");
            return (Criteria) this;
        }

        public Criteria andApprovalResultLike(String value) {
            addCriterion("approval_result like", value, "approvalResult");
            return (Criteria) this;
        }

        public Criteria andApprovalResultNotLike(String value) {
            addCriterion("approval_result not like", value, "approvalResult");
            return (Criteria) this;
        }

        public Criteria andApprovalResultIn(List<String> values) {
            addCriterion("approval_result in", values, "approvalResult");
            return (Criteria) this;
        }

        public Criteria andApprovalResultNotIn(List<String> values) {
            addCriterion("approval_result not in", values, "approvalResult");
            return (Criteria) this;
        }

        public Criteria andApprovalResultBetween(String value1, String value2) {
            addCriterion("approval_result between", value1, value2, "approvalResult");
            return (Criteria) this;
        }

        public Criteria andApprovalResultNotBetween(String value1, String value2) {
            addCriterion("approval_result not between", value1, value2, "approvalResult");
            return (Criteria) this;
        }

        public Criteria andApprovalDateIsNull() {
            addCriterion("approval_date is null");
            return (Criteria) this;
        }

        public Criteria andApprovalDateIsNotNull() {
            addCriterion("approval_date is not null");
            return (Criteria) this;
        }

        public Criteria andApprovalDateEqualTo(String value) {
            addCriterion("approval_date =", value, "approvalDate");
            return (Criteria) this;
        }

        public Criteria andApprovalDateNotEqualTo(String value) {
            addCriterion("approval_date <>", value, "approvalDate");
            return (Criteria) this;
        }

        public Criteria andApprovalDateGreaterThan(String value) {
            addCriterion("approval_date >", value, "approvalDate");
            return (Criteria) this;
        }

        public Criteria andApprovalDateGreaterThanOrEqualTo(String value) {
            addCriterion("approval_date >=", value, "approvalDate");
            return (Criteria) this;
        }

        public Criteria andApprovalDateLessThan(String value) {
            addCriterion("approval_date <", value, "approvalDate");
            return (Criteria) this;
        }

        public Criteria andApprovalDateLessThanOrEqualTo(String value) {
            addCriterion("approval_date <=", value, "approvalDate");
            return (Criteria) this;
        }

        public Criteria andApprovalDateLike(String value) {
            addCriterion("approval_date like", value, "approvalDate");
            return (Criteria) this;
        }

        public Criteria andApprovalDateNotLike(String value) {
            addCriterion("approval_date not like", value, "approvalDate");
            return (Criteria) this;
        }

        public Criteria andApprovalDateIn(List<String> values) {
            addCriterion("approval_date in", values, "approvalDate");
            return (Criteria) this;
        }

        public Criteria andApprovalDateNotIn(List<String> values) {
            addCriterion("approval_date not in", values, "approvalDate");
            return (Criteria) this;
        }

        public Criteria andApprovalDateBetween(String value1, String value2) {
            addCriterion("approval_date between", value1, value2, "approvalDate");
            return (Criteria) this;
        }

        public Criteria andApprovalDateNotBetween(String value1, String value2) {
            addCriterion("approval_date not between", value1, value2, "approvalDate");
            return (Criteria) this;
        }

        public Criteria andContractBeginIsNull() {
            addCriterion("contract_begin is null");
            return (Criteria) this;
        }

        public Criteria andContractBeginIsNotNull() {
            addCriterion("contract_begin is not null");
            return (Criteria) this;
        }

        public Criteria andContractBeginEqualTo(String value) {
            addCriterion("contract_begin =", value, "contractBegin");
            return (Criteria) this;
        }

        public Criteria andContractBeginNotEqualTo(String value) {
            addCriterion("contract_begin <>", value, "contractBegin");
            return (Criteria) this;
        }

        public Criteria andContractBeginGreaterThan(String value) {
            addCriterion("contract_begin >", value, "contractBegin");
            return (Criteria) this;
        }

        public Criteria andContractBeginGreaterThanOrEqualTo(String value) {
            addCriterion("contract_begin >=", value, "contractBegin");
            return (Criteria) this;
        }

        public Criteria andContractBeginLessThan(String value) {
            addCriterion("contract_begin <", value, "contractBegin");
            return (Criteria) this;
        }

        public Criteria andContractBeginLessThanOrEqualTo(String value) {
            addCriterion("contract_begin <=", value, "contractBegin");
            return (Criteria) this;
        }

        public Criteria andContractBeginLike(String value) {
            addCriterion("contract_begin like", value, "contractBegin");
            return (Criteria) this;
        }

        public Criteria andContractBeginNotLike(String value) {
            addCriterion("contract_begin not like", value, "contractBegin");
            return (Criteria) this;
        }

        public Criteria andContractBeginIn(List<String> values) {
            addCriterion("contract_begin in", values, "contractBegin");
            return (Criteria) this;
        }

        public Criteria andContractBeginNotIn(List<String> values) {
            addCriterion("contract_begin not in", values, "contractBegin");
            return (Criteria) this;
        }

        public Criteria andContractBeginBetween(String value1, String value2) {
            addCriterion("contract_begin between", value1, value2, "contractBegin");
            return (Criteria) this;
        }

        public Criteria andContractBeginNotBetween(String value1, String value2) {
            addCriterion("contract_begin not between", value1, value2, "contractBegin");
            return (Criteria) this;
        }

        public Criteria andContractEndIsNull() {
            addCriterion("contract_end is null");
            return (Criteria) this;
        }

        public Criteria andContractEndIsNotNull() {
            addCriterion("contract_end is not null");
            return (Criteria) this;
        }

        public Criteria andContractEndEqualTo(String value) {
            addCriterion("contract_end =", value, "contractEnd");
            return (Criteria) this;
        }

        public Criteria andContractEndNotEqualTo(String value) {
            addCriterion("contract_end <>", value, "contractEnd");
            return (Criteria) this;
        }

        public Criteria andContractEndGreaterThan(String value) {
            addCriterion("contract_end >", value, "contractEnd");
            return (Criteria) this;
        }

        public Criteria andContractEndGreaterThanOrEqualTo(String value) {
            addCriterion("contract_end >=", value, "contractEnd");
            return (Criteria) this;
        }

        public Criteria andContractEndLessThan(String value) {
            addCriterion("contract_end <", value, "contractEnd");
            return (Criteria) this;
        }

        public Criteria andContractEndLessThanOrEqualTo(String value) {
            addCriterion("contract_end <=", value, "contractEnd");
            return (Criteria) this;
        }

        public Criteria andContractEndLike(String value) {
            addCriterion("contract_end like", value, "contractEnd");
            return (Criteria) this;
        }

        public Criteria andContractEndNotLike(String value) {
            addCriterion("contract_end not like", value, "contractEnd");
            return (Criteria) this;
        }

        public Criteria andContractEndIn(List<String> values) {
            addCriterion("contract_end in", values, "contractEnd");
            return (Criteria) this;
        }

        public Criteria andContractEndNotIn(List<String> values) {
            addCriterion("contract_end not in", values, "contractEnd");
            return (Criteria) this;
        }

        public Criteria andContractEndBetween(String value1, String value2) {
            addCriterion("contract_end between", value1, value2, "contractEnd");
            return (Criteria) this;
        }

        public Criteria andContractEndNotBetween(String value1, String value2) {
            addCriterion("contract_end not between", value1, value2, "contractEnd");
            return (Criteria) this;
        }

        public Criteria andGuaranteeTypeIsNull() {
            addCriterion("guarantee_type is null");
            return (Criteria) this;
        }

        public Criteria andGuaranteeTypeIsNotNull() {
            addCriterion("guarantee_type is not null");
            return (Criteria) this;
        }

        public Criteria andGuaranteeTypeEqualTo(String value) {
            addCriterion("guarantee_type =", value, "guaranteeType");
            return (Criteria) this;
        }

        public Criteria andGuaranteeTypeNotEqualTo(String value) {
            addCriterion("guarantee_type <>", value, "guaranteeType");
            return (Criteria) this;
        }

        public Criteria andGuaranteeTypeGreaterThan(String value) {
            addCriterion("guarantee_type >", value, "guaranteeType");
            return (Criteria) this;
        }

        public Criteria andGuaranteeTypeGreaterThanOrEqualTo(String value) {
            addCriterion("guarantee_type >=", value, "guaranteeType");
            return (Criteria) this;
        }

        public Criteria andGuaranteeTypeLessThan(String value) {
            addCriterion("guarantee_type <", value, "guaranteeType");
            return (Criteria) this;
        }

        public Criteria andGuaranteeTypeLessThanOrEqualTo(String value) {
            addCriterion("guarantee_type <=", value, "guaranteeType");
            return (Criteria) this;
        }

        public Criteria andGuaranteeTypeLike(String value) {
            addCriterion("guarantee_type like", value, "guaranteeType");
            return (Criteria) this;
        }

        public Criteria andGuaranteeTypeNotLike(String value) {
            addCriterion("guarantee_type not like", value, "guaranteeType");
            return (Criteria) this;
        }

        public Criteria andGuaranteeTypeIn(List<String> values) {
            addCriterion("guarantee_type in", values, "guaranteeType");
            return (Criteria) this;
        }

        public Criteria andGuaranteeTypeNotIn(List<String> values) {
            addCriterion("guarantee_type not in", values, "guaranteeType");
            return (Criteria) this;
        }

        public Criteria andGuaranteeTypeBetween(String value1, String value2) {
            addCriterion("guarantee_type between", value1, value2, "guaranteeType");
            return (Criteria) this;
        }

        public Criteria andGuaranteeTypeNotBetween(String value1, String value2) {
            addCriterion("guarantee_type not between", value1, value2, "guaranteeType");
            return (Criteria) this;
        }

        public Criteria andCreateUserIsNull() {
            addCriterion("create_user is null");
            return (Criteria) this;
        }

        public Criteria andCreateUserIsNotNull() {
            addCriterion("create_user is not null");
            return (Criteria) this;
        }

        public Criteria andCreateUserEqualTo(String value) {
            addCriterion("create_user =", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotEqualTo(String value) {
            addCriterion("create_user <>", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserGreaterThan(String value) {
            addCriterion("create_user >", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserGreaterThanOrEqualTo(String value) {
            addCriterion("create_user >=", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLessThan(String value) {
            addCriterion("create_user <", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLessThanOrEqualTo(String value) {
            addCriterion("create_user <=", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLike(String value) {
            addCriterion("create_user like", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotLike(String value) {
            addCriterion("create_user not like", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserIn(List<String> values) {
            addCriterion("create_user in", values, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotIn(List<String> values) {
            addCriterion("create_user not in", values, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserBetween(String value1, String value2) {
            addCriterion("create_user between", value1, value2, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotBetween(String value1, String value2) {
            addCriterion("create_user not between", value1, value2, "createUser");
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

        public Criteria andUpdateUserIsNull() {
            addCriterion("update_user is null");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIsNotNull() {
            addCriterion("update_user is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateUserEqualTo(String value) {
            addCriterion("update_user =", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotEqualTo(String value) {
            addCriterion("update_user <>", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserGreaterThan(String value) {
            addCriterion("update_user >", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserGreaterThanOrEqualTo(String value) {
            addCriterion("update_user >=", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLessThan(String value) {
            addCriterion("update_user <", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLessThanOrEqualTo(String value) {
            addCriterion("update_user <=", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLike(String value) {
            addCriterion("update_user like", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotLike(String value) {
            addCriterion("update_user not like", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIn(List<String> values) {
            addCriterion("update_user in", values, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotIn(List<String> values) {
            addCriterion("update_user not in", values, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserBetween(String value1, String value2) {
            addCriterion("update_user between", value1, value2, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotBetween(String value1, String value2) {
            addCriterion("update_user not between", value1, value2, "updateUser");
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

        public Criteria andUpdateTimeEqualTo(Integer value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Integer value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Integer value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Integer value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Integer value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Integer> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Integer> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Integer value1, Integer value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andDelFlgIsNull() {
            addCriterion("del_flg is null");
            return (Criteria) this;
        }

        public Criteria andDelFlgIsNotNull() {
            addCriterion("del_flg is not null");
            return (Criteria) this;
        }

        public Criteria andDelFlgEqualTo(Integer value) {
            addCriterion("del_flg =", value, "delFlg");
            return (Criteria) this;
        }

        public Criteria andDelFlgNotEqualTo(Integer value) {
            addCriterion("del_flg <>", value, "delFlg");
            return (Criteria) this;
        }

        public Criteria andDelFlgGreaterThan(Integer value) {
            addCriterion("del_flg >", value, "delFlg");
            return (Criteria) this;
        }

        public Criteria andDelFlgGreaterThanOrEqualTo(Integer value) {
            addCriterion("del_flg >=", value, "delFlg");
            return (Criteria) this;
        }

        public Criteria andDelFlgLessThan(Integer value) {
            addCriterion("del_flg <", value, "delFlg");
            return (Criteria) this;
        }

        public Criteria andDelFlgLessThanOrEqualTo(Integer value) {
            addCriterion("del_flg <=", value, "delFlg");
            return (Criteria) this;
        }

        public Criteria andDelFlgIn(List<Integer> values) {
            addCriterion("del_flg in", values, "delFlg");
            return (Criteria) this;
        }

        public Criteria andDelFlgNotIn(List<Integer> values) {
            addCriterion("del_flg not in", values, "delFlg");
            return (Criteria) this;
        }

        public Criteria andDelFlgBetween(Integer value1, Integer value2) {
            addCriterion("del_flg between", value1, value2, "delFlg");
            return (Criteria) this;
        }

        public Criteria andDelFlgNotBetween(Integer value1, Integer value2) {
            addCriterion("del_flg not between", value1, value2, "delFlg");
            return (Criteria) this;
        }

        public Criteria andConfigureIdIsNull() {
            addCriterion("configure_id is null");
            return (Criteria) this;
        }

        public Criteria andConfigureIdIsNotNull() {
            addCriterion("configure_id is not null");
            return (Criteria) this;
        }

        public Criteria andConfigureIdEqualTo(Integer value) {
            addCriterion("configure_id =", value, "configureId");
            return (Criteria) this;
        }

        public Criteria andConfigureIdNotEqualTo(Integer value) {
            addCriterion("configure_id <>", value, "configureId");
            return (Criteria) this;
        }

        public Criteria andConfigureIdGreaterThan(Integer value) {
            addCriterion("configure_id >", value, "configureId");
            return (Criteria) this;
        }

        public Criteria andConfigureIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("configure_id >=", value, "configureId");
            return (Criteria) this;
        }

        public Criteria andConfigureIdLessThan(Integer value) {
            addCriterion("configure_id <", value, "configureId");
            return (Criteria) this;
        }

        public Criteria andConfigureIdLessThanOrEqualTo(Integer value) {
            addCriterion("configure_id <=", value, "configureId");
            return (Criteria) this;
        }

        public Criteria andConfigureIdIn(List<Integer> values) {
            addCriterion("configure_id in", values, "configureId");
            return (Criteria) this;
        }

        public Criteria andConfigureIdNotIn(List<Integer> values) {
            addCriterion("configure_id not in", values, "configureId");
            return (Criteria) this;
        }

        public Criteria andConfigureIdBetween(Integer value1, Integer value2) {
            addCriterion("configure_id between", value1, value2, "configureId");
            return (Criteria) this;
        }

        public Criteria andConfigureIdNotBetween(Integer value1, Integer value2) {
            addCriterion("configure_id not between", value1, value2, "configureId");
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