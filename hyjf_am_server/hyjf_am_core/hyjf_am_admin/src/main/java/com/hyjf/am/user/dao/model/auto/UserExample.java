package com.hyjf.am.user.dao.model.auto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public UserExample() {
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

        public Criteria andMobileIsNull() {
            addCriterion("mobile is null");
            return (Criteria) this;
        }

        public Criteria andMobileIsNotNull() {
            addCriterion("mobile is not null");
            return (Criteria) this;
        }

        public Criteria andMobileEqualTo(String value) {
            addCriterion("mobile =", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotEqualTo(String value) {
            addCriterion("mobile <>", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileGreaterThan(String value) {
            addCriterion("mobile >", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileGreaterThanOrEqualTo(String value) {
            addCriterion("mobile >=", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLessThan(String value) {
            addCriterion("mobile <", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLessThanOrEqualTo(String value) {
            addCriterion("mobile <=", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLike(String value) {
            addCriterion("mobile like", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotLike(String value) {
            addCriterion("mobile not like", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileIn(List<String> values) {
            addCriterion("mobile in", values, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotIn(List<String> values) {
            addCriterion("mobile not in", values, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileBetween(String value1, String value2) {
            addCriterion("mobile between", value1, value2, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotBetween(String value1, String value2) {
            addCriterion("mobile not between", value1, value2, "mobile");
            return (Criteria) this;
        }

        public Criteria andBankMobileIsNull() {
            addCriterion("bank_mobile is null");
            return (Criteria) this;
        }

        public Criteria andBankMobileIsNotNull() {
            addCriterion("bank_mobile is not null");
            return (Criteria) this;
        }

        public Criteria andBankMobileEqualTo(String value) {
            addCriterion("bank_mobile =", value, "bankMobile");
            return (Criteria) this;
        }

        public Criteria andBankMobileNotEqualTo(String value) {
            addCriterion("bank_mobile <>", value, "bankMobile");
            return (Criteria) this;
        }

        public Criteria andBankMobileGreaterThan(String value) {
            addCriterion("bank_mobile >", value, "bankMobile");
            return (Criteria) this;
        }

        public Criteria andBankMobileGreaterThanOrEqualTo(String value) {
            addCriterion("bank_mobile >=", value, "bankMobile");
            return (Criteria) this;
        }

        public Criteria andBankMobileLessThan(String value) {
            addCriterion("bank_mobile <", value, "bankMobile");
            return (Criteria) this;
        }

        public Criteria andBankMobileLessThanOrEqualTo(String value) {
            addCriterion("bank_mobile <=", value, "bankMobile");
            return (Criteria) this;
        }

        public Criteria andBankMobileLike(String value) {
            addCriterion("bank_mobile like", value, "bankMobile");
            return (Criteria) this;
        }

        public Criteria andBankMobileNotLike(String value) {
            addCriterion("bank_mobile not like", value, "bankMobile");
            return (Criteria) this;
        }

        public Criteria andBankMobileIn(List<String> values) {
            addCriterion("bank_mobile in", values, "bankMobile");
            return (Criteria) this;
        }

        public Criteria andBankMobileNotIn(List<String> values) {
            addCriterion("bank_mobile not in", values, "bankMobile");
            return (Criteria) this;
        }

        public Criteria andBankMobileBetween(String value1, String value2) {
            addCriterion("bank_mobile between", value1, value2, "bankMobile");
            return (Criteria) this;
        }

        public Criteria andBankMobileNotBetween(String value1, String value2) {
            addCriterion("bank_mobile not between", value1, value2, "bankMobile");
            return (Criteria) this;
        }

        public Criteria andEmailIsNull() {
            addCriterion("email is null");
            return (Criteria) this;
        }

        public Criteria andEmailIsNotNull() {
            addCriterion("email is not null");
            return (Criteria) this;
        }

        public Criteria andEmailEqualTo(String value) {
            addCriterion("email =", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotEqualTo(String value) {
            addCriterion("email <>", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThan(String value) {
            addCriterion("email >", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThanOrEqualTo(String value) {
            addCriterion("email >=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThan(String value) {
            addCriterion("email <", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThanOrEqualTo(String value) {
            addCriterion("email <=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLike(String value) {
            addCriterion("email like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotLike(String value) {
            addCriterion("email not like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailIn(List<String> values) {
            addCriterion("email in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotIn(List<String> values) {
            addCriterion("email not in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailBetween(String value1, String value2) {
            addCriterion("email between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotBetween(String value1, String value2) {
            addCriterion("email not between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andPasswordIsNull() {
            addCriterion("`password` is null");
            return (Criteria) this;
        }

        public Criteria andPasswordIsNotNull() {
            addCriterion("`password` is not null");
            return (Criteria) this;
        }

        public Criteria andPasswordEqualTo(String value) {
            addCriterion("`password` =", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotEqualTo(String value) {
            addCriterion("`password` <>", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordGreaterThan(String value) {
            addCriterion("`password` >", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordGreaterThanOrEqualTo(String value) {
            addCriterion("`password` >=", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLessThan(String value) {
            addCriterion("`password` <", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLessThanOrEqualTo(String value) {
            addCriterion("`password` <=", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLike(String value) {
            addCriterion("`password` like", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotLike(String value) {
            addCriterion("`password` not like", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordIn(List<String> values) {
            addCriterion("`password` in", values, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotIn(List<String> values) {
            addCriterion("`password` not in", values, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordBetween(String value1, String value2) {
            addCriterion("`password` between", value1, value2, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotBetween(String value1, String value2) {
            addCriterion("`password` not between", value1, value2, "password");
            return (Criteria) this;
        }

        public Criteria andSaltIsNull() {
            addCriterion("salt is null");
            return (Criteria) this;
        }

        public Criteria andSaltIsNotNull() {
            addCriterion("salt is not null");
            return (Criteria) this;
        }

        public Criteria andSaltEqualTo(String value) {
            addCriterion("salt =", value, "salt");
            return (Criteria) this;
        }

        public Criteria andSaltNotEqualTo(String value) {
            addCriterion("salt <>", value, "salt");
            return (Criteria) this;
        }

        public Criteria andSaltGreaterThan(String value) {
            addCriterion("salt >", value, "salt");
            return (Criteria) this;
        }

        public Criteria andSaltGreaterThanOrEqualTo(String value) {
            addCriterion("salt >=", value, "salt");
            return (Criteria) this;
        }

        public Criteria andSaltLessThan(String value) {
            addCriterion("salt <", value, "salt");
            return (Criteria) this;
        }

        public Criteria andSaltLessThanOrEqualTo(String value) {
            addCriterion("salt <=", value, "salt");
            return (Criteria) this;
        }

        public Criteria andSaltLike(String value) {
            addCriterion("salt like", value, "salt");
            return (Criteria) this;
        }

        public Criteria andSaltNotLike(String value) {
            addCriterion("salt not like", value, "salt");
            return (Criteria) this;
        }

        public Criteria andSaltIn(List<String> values) {
            addCriterion("salt in", values, "salt");
            return (Criteria) this;
        }

        public Criteria andSaltNotIn(List<String> values) {
            addCriterion("salt not in", values, "salt");
            return (Criteria) this;
        }

        public Criteria andSaltBetween(String value1, String value2) {
            addCriterion("salt between", value1, value2, "salt");
            return (Criteria) this;
        }

        public Criteria andSaltNotBetween(String value1, String value2) {
            addCriterion("salt not between", value1, value2, "salt");
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

        public Criteria andOpenAccountIsNull() {
            addCriterion("open_account is null");
            return (Criteria) this;
        }

        public Criteria andOpenAccountIsNotNull() {
            addCriterion("open_account is not null");
            return (Criteria) this;
        }

        public Criteria andOpenAccountEqualTo(Integer value) {
            addCriterion("open_account =", value, "openAccount");
            return (Criteria) this;
        }

        public Criteria andOpenAccountNotEqualTo(Integer value) {
            addCriterion("open_account <>", value, "openAccount");
            return (Criteria) this;
        }

        public Criteria andOpenAccountGreaterThan(Integer value) {
            addCriterion("open_account >", value, "openAccount");
            return (Criteria) this;
        }

        public Criteria andOpenAccountGreaterThanOrEqualTo(Integer value) {
            addCriterion("open_account >=", value, "openAccount");
            return (Criteria) this;
        }

        public Criteria andOpenAccountLessThan(Integer value) {
            addCriterion("open_account <", value, "openAccount");
            return (Criteria) this;
        }

        public Criteria andOpenAccountLessThanOrEqualTo(Integer value) {
            addCriterion("open_account <=", value, "openAccount");
            return (Criteria) this;
        }

        public Criteria andOpenAccountIn(List<Integer> values) {
            addCriterion("open_account in", values, "openAccount");
            return (Criteria) this;
        }

        public Criteria andOpenAccountNotIn(List<Integer> values) {
            addCriterion("open_account not in", values, "openAccount");
            return (Criteria) this;
        }

        public Criteria andOpenAccountBetween(Integer value1, Integer value2) {
            addCriterion("open_account between", value1, value2, "openAccount");
            return (Criteria) this;
        }

        public Criteria andOpenAccountNotBetween(Integer value1, Integer value2) {
            addCriterion("open_account not between", value1, value2, "openAccount");
            return (Criteria) this;
        }

        public Criteria andRechargeSmsIsNull() {
            addCriterion("recharge_sms is null");
            return (Criteria) this;
        }

        public Criteria andRechargeSmsIsNotNull() {
            addCriterion("recharge_sms is not null");
            return (Criteria) this;
        }

        public Criteria andRechargeSmsEqualTo(Integer value) {
            addCriterion("recharge_sms =", value, "rechargeSms");
            return (Criteria) this;
        }

        public Criteria andRechargeSmsNotEqualTo(Integer value) {
            addCriterion("recharge_sms <>", value, "rechargeSms");
            return (Criteria) this;
        }

        public Criteria andRechargeSmsGreaterThan(Integer value) {
            addCriterion("recharge_sms >", value, "rechargeSms");
            return (Criteria) this;
        }

        public Criteria andRechargeSmsGreaterThanOrEqualTo(Integer value) {
            addCriterion("recharge_sms >=", value, "rechargeSms");
            return (Criteria) this;
        }

        public Criteria andRechargeSmsLessThan(Integer value) {
            addCriterion("recharge_sms <", value, "rechargeSms");
            return (Criteria) this;
        }

        public Criteria andRechargeSmsLessThanOrEqualTo(Integer value) {
            addCriterion("recharge_sms <=", value, "rechargeSms");
            return (Criteria) this;
        }

        public Criteria andRechargeSmsIn(List<Integer> values) {
            addCriterion("recharge_sms in", values, "rechargeSms");
            return (Criteria) this;
        }

        public Criteria andRechargeSmsNotIn(List<Integer> values) {
            addCriterion("recharge_sms not in", values, "rechargeSms");
            return (Criteria) this;
        }

        public Criteria andRechargeSmsBetween(Integer value1, Integer value2) {
            addCriterion("recharge_sms between", value1, value2, "rechargeSms");
            return (Criteria) this;
        }

        public Criteria andRechargeSmsNotBetween(Integer value1, Integer value2) {
            addCriterion("recharge_sms not between", value1, value2, "rechargeSms");
            return (Criteria) this;
        }

        public Criteria andWithdrawSmsIsNull() {
            addCriterion("withdraw_sms is null");
            return (Criteria) this;
        }

        public Criteria andWithdrawSmsIsNotNull() {
            addCriterion("withdraw_sms is not null");
            return (Criteria) this;
        }

        public Criteria andWithdrawSmsEqualTo(Integer value) {
            addCriterion("withdraw_sms =", value, "withdrawSms");
            return (Criteria) this;
        }

        public Criteria andWithdrawSmsNotEqualTo(Integer value) {
            addCriterion("withdraw_sms <>", value, "withdrawSms");
            return (Criteria) this;
        }

        public Criteria andWithdrawSmsGreaterThan(Integer value) {
            addCriterion("withdraw_sms >", value, "withdrawSms");
            return (Criteria) this;
        }

        public Criteria andWithdrawSmsGreaterThanOrEqualTo(Integer value) {
            addCriterion("withdraw_sms >=", value, "withdrawSms");
            return (Criteria) this;
        }

        public Criteria andWithdrawSmsLessThan(Integer value) {
            addCriterion("withdraw_sms <", value, "withdrawSms");
            return (Criteria) this;
        }

        public Criteria andWithdrawSmsLessThanOrEqualTo(Integer value) {
            addCriterion("withdraw_sms <=", value, "withdrawSms");
            return (Criteria) this;
        }

        public Criteria andWithdrawSmsIn(List<Integer> values) {
            addCriterion("withdraw_sms in", values, "withdrawSms");
            return (Criteria) this;
        }

        public Criteria andWithdrawSmsNotIn(List<Integer> values) {
            addCriterion("withdraw_sms not in", values, "withdrawSms");
            return (Criteria) this;
        }

        public Criteria andWithdrawSmsBetween(Integer value1, Integer value2) {
            addCriterion("withdraw_sms between", value1, value2, "withdrawSms");
            return (Criteria) this;
        }

        public Criteria andWithdrawSmsNotBetween(Integer value1, Integer value2) {
            addCriterion("withdraw_sms not between", value1, value2, "withdrawSms");
            return (Criteria) this;
        }

        public Criteria andIconUrlIsNull() {
            addCriterion("icon_url is null");
            return (Criteria) this;
        }

        public Criteria andIconUrlIsNotNull() {
            addCriterion("icon_url is not null");
            return (Criteria) this;
        }

        public Criteria andIconUrlEqualTo(String value) {
            addCriterion("icon_url =", value, "iconUrl");
            return (Criteria) this;
        }

        public Criteria andIconUrlNotEqualTo(String value) {
            addCriterion("icon_url <>", value, "iconUrl");
            return (Criteria) this;
        }

        public Criteria andIconUrlGreaterThan(String value) {
            addCriterion("icon_url >", value, "iconUrl");
            return (Criteria) this;
        }

        public Criteria andIconUrlGreaterThanOrEqualTo(String value) {
            addCriterion("icon_url >=", value, "iconUrl");
            return (Criteria) this;
        }

        public Criteria andIconUrlLessThan(String value) {
            addCriterion("icon_url <", value, "iconUrl");
            return (Criteria) this;
        }

        public Criteria andIconUrlLessThanOrEqualTo(String value) {
            addCriterion("icon_url <=", value, "iconUrl");
            return (Criteria) this;
        }

        public Criteria andIconUrlLike(String value) {
            addCriterion("icon_url like", value, "iconUrl");
            return (Criteria) this;
        }

        public Criteria andIconUrlNotLike(String value) {
            addCriterion("icon_url not like", value, "iconUrl");
            return (Criteria) this;
        }

        public Criteria andIconUrlIn(List<String> values) {
            addCriterion("icon_url in", values, "iconUrl");
            return (Criteria) this;
        }

        public Criteria andIconUrlNotIn(List<String> values) {
            addCriterion("icon_url not in", values, "iconUrl");
            return (Criteria) this;
        }

        public Criteria andIconUrlBetween(String value1, String value2) {
            addCriterion("icon_url between", value1, value2, "iconUrl");
            return (Criteria) this;
        }

        public Criteria andIconUrlNotBetween(String value1, String value2) {
            addCriterion("icon_url not between", value1, value2, "iconUrl");
            return (Criteria) this;
        }

        public Criteria andIfReceiveNoticeIsNull() {
            addCriterion("if_receive_notice is null");
            return (Criteria) this;
        }

        public Criteria andIfReceiveNoticeIsNotNull() {
            addCriterion("if_receive_notice is not null");
            return (Criteria) this;
        }

        public Criteria andIfReceiveNoticeEqualTo(Integer value) {
            addCriterion("if_receive_notice =", value, "ifReceiveNotice");
            return (Criteria) this;
        }

        public Criteria andIfReceiveNoticeNotEqualTo(Integer value) {
            addCriterion("if_receive_notice <>", value, "ifReceiveNotice");
            return (Criteria) this;
        }

        public Criteria andIfReceiveNoticeGreaterThan(Integer value) {
            addCriterion("if_receive_notice >", value, "ifReceiveNotice");
            return (Criteria) this;
        }

        public Criteria andIfReceiveNoticeGreaterThanOrEqualTo(Integer value) {
            addCriterion("if_receive_notice >=", value, "ifReceiveNotice");
            return (Criteria) this;
        }

        public Criteria andIfReceiveNoticeLessThan(Integer value) {
            addCriterion("if_receive_notice <", value, "ifReceiveNotice");
            return (Criteria) this;
        }

        public Criteria andIfReceiveNoticeLessThanOrEqualTo(Integer value) {
            addCriterion("if_receive_notice <=", value, "ifReceiveNotice");
            return (Criteria) this;
        }

        public Criteria andIfReceiveNoticeIn(List<Integer> values) {
            addCriterion("if_receive_notice in", values, "ifReceiveNotice");
            return (Criteria) this;
        }

        public Criteria andIfReceiveNoticeNotIn(List<Integer> values) {
            addCriterion("if_receive_notice not in", values, "ifReceiveNotice");
            return (Criteria) this;
        }

        public Criteria andIfReceiveNoticeBetween(Integer value1, Integer value2) {
            addCriterion("if_receive_notice between", value1, value2, "ifReceiveNotice");
            return (Criteria) this;
        }

        public Criteria andIfReceiveNoticeNotBetween(Integer value1, Integer value2) {
            addCriterion("if_receive_notice not between", value1, value2, "ifReceiveNotice");
            return (Criteria) this;
        }

        public Criteria andInvestSmsIsNull() {
            addCriterion("invest_sms is null");
            return (Criteria) this;
        }

        public Criteria andInvestSmsIsNotNull() {
            addCriterion("invest_sms is not null");
            return (Criteria) this;
        }

        public Criteria andInvestSmsEqualTo(Integer value) {
            addCriterion("invest_sms =", value, "investSms");
            return (Criteria) this;
        }

        public Criteria andInvestSmsNotEqualTo(Integer value) {
            addCriterion("invest_sms <>", value, "investSms");
            return (Criteria) this;
        }

        public Criteria andInvestSmsGreaterThan(Integer value) {
            addCriterion("invest_sms >", value, "investSms");
            return (Criteria) this;
        }

        public Criteria andInvestSmsGreaterThanOrEqualTo(Integer value) {
            addCriterion("invest_sms >=", value, "investSms");
            return (Criteria) this;
        }

        public Criteria andInvestSmsLessThan(Integer value) {
            addCriterion("invest_sms <", value, "investSms");
            return (Criteria) this;
        }

        public Criteria andInvestSmsLessThanOrEqualTo(Integer value) {
            addCriterion("invest_sms <=", value, "investSms");
            return (Criteria) this;
        }

        public Criteria andInvestSmsIn(List<Integer> values) {
            addCriterion("invest_sms in", values, "investSms");
            return (Criteria) this;
        }

        public Criteria andInvestSmsNotIn(List<Integer> values) {
            addCriterion("invest_sms not in", values, "investSms");
            return (Criteria) this;
        }

        public Criteria andInvestSmsBetween(Integer value1, Integer value2) {
            addCriterion("invest_sms between", value1, value2, "investSms");
            return (Criteria) this;
        }

        public Criteria andInvestSmsNotBetween(Integer value1, Integer value2) {
            addCriterion("invest_sms not between", value1, value2, "investSms");
            return (Criteria) this;
        }

        public Criteria andRecieveSmsIsNull() {
            addCriterion("recieve_sms is null");
            return (Criteria) this;
        }

        public Criteria andRecieveSmsIsNotNull() {
            addCriterion("recieve_sms is not null");
            return (Criteria) this;
        }

        public Criteria andRecieveSmsEqualTo(Integer value) {
            addCriterion("recieve_sms =", value, "recieveSms");
            return (Criteria) this;
        }

        public Criteria andRecieveSmsNotEqualTo(Integer value) {
            addCriterion("recieve_sms <>", value, "recieveSms");
            return (Criteria) this;
        }

        public Criteria andRecieveSmsGreaterThan(Integer value) {
            addCriterion("recieve_sms >", value, "recieveSms");
            return (Criteria) this;
        }

        public Criteria andRecieveSmsGreaterThanOrEqualTo(Integer value) {
            addCriterion("recieve_sms >=", value, "recieveSms");
            return (Criteria) this;
        }

        public Criteria andRecieveSmsLessThan(Integer value) {
            addCriterion("recieve_sms <", value, "recieveSms");
            return (Criteria) this;
        }

        public Criteria andRecieveSmsLessThanOrEqualTo(Integer value) {
            addCriterion("recieve_sms <=", value, "recieveSms");
            return (Criteria) this;
        }

        public Criteria andRecieveSmsIn(List<Integer> values) {
            addCriterion("recieve_sms in", values, "recieveSms");
            return (Criteria) this;
        }

        public Criteria andRecieveSmsNotIn(List<Integer> values) {
            addCriterion("recieve_sms not in", values, "recieveSms");
            return (Criteria) this;
        }

        public Criteria andRecieveSmsBetween(Integer value1, Integer value2) {
            addCriterion("recieve_sms between", value1, value2, "recieveSms");
            return (Criteria) this;
        }

        public Criteria andRecieveSmsNotBetween(Integer value1, Integer value2) {
            addCriterion("recieve_sms not between", value1, value2, "recieveSms");
            return (Criteria) this;
        }

        public Criteria andRegEsbIsNull() {
            addCriterion("reg_esb is null");
            return (Criteria) this;
        }

        public Criteria andRegEsbIsNotNull() {
            addCriterion("reg_esb is not null");
            return (Criteria) this;
        }

        public Criteria andRegEsbEqualTo(Integer value) {
            addCriterion("reg_esb =", value, "regEsb");
            return (Criteria) this;
        }

        public Criteria andRegEsbNotEqualTo(Integer value) {
            addCriterion("reg_esb <>", value, "regEsb");
            return (Criteria) this;
        }

        public Criteria andRegEsbGreaterThan(Integer value) {
            addCriterion("reg_esb >", value, "regEsb");
            return (Criteria) this;
        }

        public Criteria andRegEsbGreaterThanOrEqualTo(Integer value) {
            addCriterion("reg_esb >=", value, "regEsb");
            return (Criteria) this;
        }

        public Criteria andRegEsbLessThan(Integer value) {
            addCriterion("reg_esb <", value, "regEsb");
            return (Criteria) this;
        }

        public Criteria andRegEsbLessThanOrEqualTo(Integer value) {
            addCriterion("reg_esb <=", value, "regEsb");
            return (Criteria) this;
        }

        public Criteria andRegEsbIn(List<Integer> values) {
            addCriterion("reg_esb in", values, "regEsb");
            return (Criteria) this;
        }

        public Criteria andRegEsbNotIn(List<Integer> values) {
            addCriterion("reg_esb not in", values, "regEsb");
            return (Criteria) this;
        }

        public Criteria andRegEsbBetween(Integer value1, Integer value2) {
            addCriterion("reg_esb between", value1, value2, "regEsb");
            return (Criteria) this;
        }

        public Criteria andRegEsbNotBetween(Integer value1, Integer value2) {
            addCriterion("reg_esb not between", value1, value2, "regEsb");
            return (Criteria) this;
        }

        public Criteria andEprovinceIsNull() {
            addCriterion("eprovince is null");
            return (Criteria) this;
        }

        public Criteria andEprovinceIsNotNull() {
            addCriterion("eprovince is not null");
            return (Criteria) this;
        }

        public Criteria andEprovinceEqualTo(String value) {
            addCriterion("eprovince =", value, "eprovince");
            return (Criteria) this;
        }

        public Criteria andEprovinceNotEqualTo(String value) {
            addCriterion("eprovince <>", value, "eprovince");
            return (Criteria) this;
        }

        public Criteria andEprovinceGreaterThan(String value) {
            addCriterion("eprovince >", value, "eprovince");
            return (Criteria) this;
        }

        public Criteria andEprovinceGreaterThanOrEqualTo(String value) {
            addCriterion("eprovince >=", value, "eprovince");
            return (Criteria) this;
        }

        public Criteria andEprovinceLessThan(String value) {
            addCriterion("eprovince <", value, "eprovince");
            return (Criteria) this;
        }

        public Criteria andEprovinceLessThanOrEqualTo(String value) {
            addCriterion("eprovince <=", value, "eprovince");
            return (Criteria) this;
        }

        public Criteria andEprovinceLike(String value) {
            addCriterion("eprovince like", value, "eprovince");
            return (Criteria) this;
        }

        public Criteria andEprovinceNotLike(String value) {
            addCriterion("eprovince not like", value, "eprovince");
            return (Criteria) this;
        }

        public Criteria andEprovinceIn(List<String> values) {
            addCriterion("eprovince in", values, "eprovince");
            return (Criteria) this;
        }

        public Criteria andEprovinceNotIn(List<String> values) {
            addCriterion("eprovince not in", values, "eprovince");
            return (Criteria) this;
        }

        public Criteria andEprovinceBetween(String value1, String value2) {
            addCriterion("eprovince between", value1, value2, "eprovince");
            return (Criteria) this;
        }

        public Criteria andEprovinceNotBetween(String value1, String value2) {
            addCriterion("eprovince not between", value1, value2, "eprovince");
            return (Criteria) this;
        }

        public Criteria andSendSmsIsNull() {
            addCriterion("send_sms is null");
            return (Criteria) this;
        }

        public Criteria andSendSmsIsNotNull() {
            addCriterion("send_sms is not null");
            return (Criteria) this;
        }

        public Criteria andSendSmsEqualTo(Integer value) {
            addCriterion("send_sms =", value, "sendSms");
            return (Criteria) this;
        }

        public Criteria andSendSmsNotEqualTo(Integer value) {
            addCriterion("send_sms <>", value, "sendSms");
            return (Criteria) this;
        }

        public Criteria andSendSmsGreaterThan(Integer value) {
            addCriterion("send_sms >", value, "sendSms");
            return (Criteria) this;
        }

        public Criteria andSendSmsGreaterThanOrEqualTo(Integer value) {
            addCriterion("send_sms >=", value, "sendSms");
            return (Criteria) this;
        }

        public Criteria andSendSmsLessThan(Integer value) {
            addCriterion("send_sms <", value, "sendSms");
            return (Criteria) this;
        }

        public Criteria andSendSmsLessThanOrEqualTo(Integer value) {
            addCriterion("send_sms <=", value, "sendSms");
            return (Criteria) this;
        }

        public Criteria andSendSmsIn(List<Integer> values) {
            addCriterion("send_sms in", values, "sendSms");
            return (Criteria) this;
        }

        public Criteria andSendSmsNotIn(List<Integer> values) {
            addCriterion("send_sms not in", values, "sendSms");
            return (Criteria) this;
        }

        public Criteria andSendSmsBetween(Integer value1, Integer value2) {
            addCriterion("send_sms between", value1, value2, "sendSms");
            return (Criteria) this;
        }

        public Criteria andSendSmsNotBetween(Integer value1, Integer value2) {
            addCriterion("send_sms not between", value1, value2, "sendSms");
            return (Criteria) this;
        }

        public Criteria andIsInstFlagIsNull() {
            addCriterion("is_inst_flag is null");
            return (Criteria) this;
        }

        public Criteria andIsInstFlagIsNotNull() {
            addCriterion("is_inst_flag is not null");
            return (Criteria) this;
        }

        public Criteria andIsInstFlagEqualTo(Integer value) {
            addCriterion("is_inst_flag =", value, "isInstFlag");
            return (Criteria) this;
        }

        public Criteria andIsInstFlagNotEqualTo(Integer value) {
            addCriterion("is_inst_flag <>", value, "isInstFlag");
            return (Criteria) this;
        }

        public Criteria andIsInstFlagGreaterThan(Integer value) {
            addCriterion("is_inst_flag >", value, "isInstFlag");
            return (Criteria) this;
        }

        public Criteria andIsInstFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_inst_flag >=", value, "isInstFlag");
            return (Criteria) this;
        }

        public Criteria andIsInstFlagLessThan(Integer value) {
            addCriterion("is_inst_flag <", value, "isInstFlag");
            return (Criteria) this;
        }

        public Criteria andIsInstFlagLessThanOrEqualTo(Integer value) {
            addCriterion("is_inst_flag <=", value, "isInstFlag");
            return (Criteria) this;
        }

        public Criteria andIsInstFlagIn(List<Integer> values) {
            addCriterion("is_inst_flag in", values, "isInstFlag");
            return (Criteria) this;
        }

        public Criteria andIsInstFlagNotIn(List<Integer> values) {
            addCriterion("is_inst_flag not in", values, "isInstFlag");
            return (Criteria) this;
        }

        public Criteria andIsInstFlagBetween(Integer value1, Integer value2) {
            addCriterion("is_inst_flag between", value1, value2, "isInstFlag");
            return (Criteria) this;
        }

        public Criteria andIsInstFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("is_inst_flag not between", value1, value2, "isInstFlag");
            return (Criteria) this;
        }

        public Criteria andInstCodeIsNull() {
            addCriterion("inst_code is null");
            return (Criteria) this;
        }

        public Criteria andInstCodeIsNotNull() {
            addCriterion("inst_code is not null");
            return (Criteria) this;
        }

        public Criteria andInstCodeEqualTo(String value) {
            addCriterion("inst_code =", value, "instCode");
            return (Criteria) this;
        }

        public Criteria andInstCodeNotEqualTo(String value) {
            addCriterion("inst_code <>", value, "instCode");
            return (Criteria) this;
        }

        public Criteria andInstCodeGreaterThan(String value) {
            addCriterion("inst_code >", value, "instCode");
            return (Criteria) this;
        }

        public Criteria andInstCodeGreaterThanOrEqualTo(String value) {
            addCriterion("inst_code >=", value, "instCode");
            return (Criteria) this;
        }

        public Criteria andInstCodeLessThan(String value) {
            addCriterion("inst_code <", value, "instCode");
            return (Criteria) this;
        }

        public Criteria andInstCodeLessThanOrEqualTo(String value) {
            addCriterion("inst_code <=", value, "instCode");
            return (Criteria) this;
        }

        public Criteria andInstCodeLike(String value) {
            addCriterion("inst_code like", value, "instCode");
            return (Criteria) this;
        }

        public Criteria andInstCodeNotLike(String value) {
            addCriterion("inst_code not like", value, "instCode");
            return (Criteria) this;
        }

        public Criteria andInstCodeIn(List<String> values) {
            addCriterion("inst_code in", values, "instCode");
            return (Criteria) this;
        }

        public Criteria andInstCodeNotIn(List<String> values) {
            addCriterion("inst_code not in", values, "instCode");
            return (Criteria) this;
        }

        public Criteria andInstCodeBetween(String value1, String value2) {
            addCriterion("inst_code between", value1, value2, "instCode");
            return (Criteria) this;
        }

        public Criteria andInstCodeNotBetween(String value1, String value2) {
            addCriterion("inst_code not between", value1, value2, "instCode");
            return (Criteria) this;
        }

        public Criteria andAccountEsbIsNull() {
            addCriterion("account_esb is null");
            return (Criteria) this;
        }

        public Criteria andAccountEsbIsNotNull() {
            addCriterion("account_esb is not null");
            return (Criteria) this;
        }

        public Criteria andAccountEsbEqualTo(Integer value) {
            addCriterion("account_esb =", value, "accountEsb");
            return (Criteria) this;
        }

        public Criteria andAccountEsbNotEqualTo(Integer value) {
            addCriterion("account_esb <>", value, "accountEsb");
            return (Criteria) this;
        }

        public Criteria andAccountEsbGreaterThan(Integer value) {
            addCriterion("account_esb >", value, "accountEsb");
            return (Criteria) this;
        }

        public Criteria andAccountEsbGreaterThanOrEqualTo(Integer value) {
            addCriterion("account_esb >=", value, "accountEsb");
            return (Criteria) this;
        }

        public Criteria andAccountEsbLessThan(Integer value) {
            addCriterion("account_esb <", value, "accountEsb");
            return (Criteria) this;
        }

        public Criteria andAccountEsbLessThanOrEqualTo(Integer value) {
            addCriterion("account_esb <=", value, "accountEsb");
            return (Criteria) this;
        }

        public Criteria andAccountEsbIn(List<Integer> values) {
            addCriterion("account_esb in", values, "accountEsb");
            return (Criteria) this;
        }

        public Criteria andAccountEsbNotIn(List<Integer> values) {
            addCriterion("account_esb not in", values, "accountEsb");
            return (Criteria) this;
        }

        public Criteria andAccountEsbBetween(Integer value1, Integer value2) {
            addCriterion("account_esb between", value1, value2, "accountEsb");
            return (Criteria) this;
        }

        public Criteria andAccountEsbNotBetween(Integer value1, Integer value2) {
            addCriterion("account_esb not between", value1, value2, "accountEsb");
            return (Criteria) this;
        }

        public Criteria andUserTypeIsNull() {
            addCriterion("user_type is null");
            return (Criteria) this;
        }

        public Criteria andUserTypeIsNotNull() {
            addCriterion("user_type is not null");
            return (Criteria) this;
        }

        public Criteria andUserTypeEqualTo(Integer value) {
            addCriterion("user_type =", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeNotEqualTo(Integer value) {
            addCriterion("user_type <>", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeGreaterThan(Integer value) {
            addCriterion("user_type >", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("user_type >=", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeLessThan(Integer value) {
            addCriterion("user_type <", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeLessThanOrEqualTo(Integer value) {
            addCriterion("user_type <=", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeIn(List<Integer> values) {
            addCriterion("user_type in", values, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeNotIn(List<Integer> values) {
            addCriterion("user_type not in", values, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeBetween(Integer value1, Integer value2) {
            addCriterion("user_type between", value1, value2, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("user_type not between", value1, value2, "userType");
            return (Criteria) this;
        }

        public Criteria andPaymentAuthStatusIsNull() {
            addCriterion("payment_auth_status is null");
            return (Criteria) this;
        }

        public Criteria andPaymentAuthStatusIsNotNull() {
            addCriterion("payment_auth_status is not null");
            return (Criteria) this;
        }

        public Criteria andPaymentAuthStatusEqualTo(Integer value) {
            addCriterion("payment_auth_status =", value, "paymentAuthStatus");
            return (Criteria) this;
        }

        public Criteria andPaymentAuthStatusNotEqualTo(Integer value) {
            addCriterion("payment_auth_status <>", value, "paymentAuthStatus");
            return (Criteria) this;
        }

        public Criteria andPaymentAuthStatusGreaterThan(Integer value) {
            addCriterion("payment_auth_status >", value, "paymentAuthStatus");
            return (Criteria) this;
        }

        public Criteria andPaymentAuthStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("payment_auth_status >=", value, "paymentAuthStatus");
            return (Criteria) this;
        }

        public Criteria andPaymentAuthStatusLessThan(Integer value) {
            addCriterion("payment_auth_status <", value, "paymentAuthStatus");
            return (Criteria) this;
        }

        public Criteria andPaymentAuthStatusLessThanOrEqualTo(Integer value) {
            addCriterion("payment_auth_status <=", value, "paymentAuthStatus");
            return (Criteria) this;
        }

        public Criteria andPaymentAuthStatusIn(List<Integer> values) {
            addCriterion("payment_auth_status in", values, "paymentAuthStatus");
            return (Criteria) this;
        }

        public Criteria andPaymentAuthStatusNotIn(List<Integer> values) {
            addCriterion("payment_auth_status not in", values, "paymentAuthStatus");
            return (Criteria) this;
        }

        public Criteria andPaymentAuthStatusBetween(Integer value1, Integer value2) {
            addCriterion("payment_auth_status between", value1, value2, "paymentAuthStatus");
            return (Criteria) this;
        }

        public Criteria andPaymentAuthStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("payment_auth_status not between", value1, value2, "paymentAuthStatus");
            return (Criteria) this;
        }

        public Criteria andIsSetPasswordIsNull() {
            addCriterion("is_set_password is null");
            return (Criteria) this;
        }

        public Criteria andIsSetPasswordIsNotNull() {
            addCriterion("is_set_password is not null");
            return (Criteria) this;
        }

        public Criteria andIsSetPasswordEqualTo(Integer value) {
            addCriterion("is_set_password =", value, "isSetPassword");
            return (Criteria) this;
        }

        public Criteria andIsSetPasswordNotEqualTo(Integer value) {
            addCriterion("is_set_password <>", value, "isSetPassword");
            return (Criteria) this;
        }

        public Criteria andIsSetPasswordGreaterThan(Integer value) {
            addCriterion("is_set_password >", value, "isSetPassword");
            return (Criteria) this;
        }

        public Criteria andIsSetPasswordGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_set_password >=", value, "isSetPassword");
            return (Criteria) this;
        }

        public Criteria andIsSetPasswordLessThan(Integer value) {
            addCriterion("is_set_password <", value, "isSetPassword");
            return (Criteria) this;
        }

        public Criteria andIsSetPasswordLessThanOrEqualTo(Integer value) {
            addCriterion("is_set_password <=", value, "isSetPassword");
            return (Criteria) this;
        }

        public Criteria andIsSetPasswordIn(List<Integer> values) {
            addCriterion("is_set_password in", values, "isSetPassword");
            return (Criteria) this;
        }

        public Criteria andIsSetPasswordNotIn(List<Integer> values) {
            addCriterion("is_set_password not in", values, "isSetPassword");
            return (Criteria) this;
        }

        public Criteria andIsSetPasswordBetween(Integer value1, Integer value2) {
            addCriterion("is_set_password between", value1, value2, "isSetPassword");
            return (Criteria) this;
        }

        public Criteria andIsSetPasswordNotBetween(Integer value1, Integer value2) {
            addCriterion("is_set_password not between", value1, value2, "isSetPassword");
            return (Criteria) this;
        }

        public Criteria andBankOpenAccountIsNull() {
            addCriterion("bank_open_account is null");
            return (Criteria) this;
        }

        public Criteria andBankOpenAccountIsNotNull() {
            addCriterion("bank_open_account is not null");
            return (Criteria) this;
        }

        public Criteria andBankOpenAccountEqualTo(Integer value) {
            addCriterion("bank_open_account =", value, "bankOpenAccount");
            return (Criteria) this;
        }

        public Criteria andBankOpenAccountNotEqualTo(Integer value) {
            addCriterion("bank_open_account <>", value, "bankOpenAccount");
            return (Criteria) this;
        }

        public Criteria andBankOpenAccountGreaterThan(Integer value) {
            addCriterion("bank_open_account >", value, "bankOpenAccount");
            return (Criteria) this;
        }

        public Criteria andBankOpenAccountGreaterThanOrEqualTo(Integer value) {
            addCriterion("bank_open_account >=", value, "bankOpenAccount");
            return (Criteria) this;
        }

        public Criteria andBankOpenAccountLessThan(Integer value) {
            addCriterion("bank_open_account <", value, "bankOpenAccount");
            return (Criteria) this;
        }

        public Criteria andBankOpenAccountLessThanOrEqualTo(Integer value) {
            addCriterion("bank_open_account <=", value, "bankOpenAccount");
            return (Criteria) this;
        }

        public Criteria andBankOpenAccountIn(List<Integer> values) {
            addCriterion("bank_open_account in", values, "bankOpenAccount");
            return (Criteria) this;
        }

        public Criteria andBankOpenAccountNotIn(List<Integer> values) {
            addCriterion("bank_open_account not in", values, "bankOpenAccount");
            return (Criteria) this;
        }

        public Criteria andBankOpenAccountBetween(Integer value1, Integer value2) {
            addCriterion("bank_open_account between", value1, value2, "bankOpenAccount");
            return (Criteria) this;
        }

        public Criteria andBankOpenAccountNotBetween(Integer value1, Integer value2) {
            addCriterion("bank_open_account not between", value1, value2, "bankOpenAccount");
            return (Criteria) this;
        }

        public Criteria andBankAccountEsbIsNull() {
            addCriterion("bank_account_esb is null");
            return (Criteria) this;
        }

        public Criteria andBankAccountEsbIsNotNull() {
            addCriterion("bank_account_esb is not null");
            return (Criteria) this;
        }

        public Criteria andBankAccountEsbEqualTo(Integer value) {
            addCriterion("bank_account_esb =", value, "bankAccountEsb");
            return (Criteria) this;
        }

        public Criteria andBankAccountEsbNotEqualTo(Integer value) {
            addCriterion("bank_account_esb <>", value, "bankAccountEsb");
            return (Criteria) this;
        }

        public Criteria andBankAccountEsbGreaterThan(Integer value) {
            addCriterion("bank_account_esb >", value, "bankAccountEsb");
            return (Criteria) this;
        }

        public Criteria andBankAccountEsbGreaterThanOrEqualTo(Integer value) {
            addCriterion("bank_account_esb >=", value, "bankAccountEsb");
            return (Criteria) this;
        }

        public Criteria andBankAccountEsbLessThan(Integer value) {
            addCriterion("bank_account_esb <", value, "bankAccountEsb");
            return (Criteria) this;
        }

        public Criteria andBankAccountEsbLessThanOrEqualTo(Integer value) {
            addCriterion("bank_account_esb <=", value, "bankAccountEsb");
            return (Criteria) this;
        }

        public Criteria andBankAccountEsbIn(List<Integer> values) {
            addCriterion("bank_account_esb in", values, "bankAccountEsb");
            return (Criteria) this;
        }

        public Criteria andBankAccountEsbNotIn(List<Integer> values) {
            addCriterion("bank_account_esb not in", values, "bankAccountEsb");
            return (Criteria) this;
        }

        public Criteria andBankAccountEsbBetween(Integer value1, Integer value2) {
            addCriterion("bank_account_esb between", value1, value2, "bankAccountEsb");
            return (Criteria) this;
        }

        public Criteria andBankAccountEsbNotBetween(Integer value1, Integer value2) {
            addCriterion("bank_account_esb not between", value1, value2, "bankAccountEsb");
            return (Criteria) this;
        }

        public Criteria andIsEvaluationFlagIsNull() {
            addCriterion("is_evaluation_flag is null");
            return (Criteria) this;
        }

        public Criteria andIsEvaluationFlagIsNotNull() {
            addCriterion("is_evaluation_flag is not null");
            return (Criteria) this;
        }

        public Criteria andIsEvaluationFlagEqualTo(Integer value) {
            addCriterion("is_evaluation_flag =", value, "isEvaluationFlag");
            return (Criteria) this;
        }

        public Criteria andIsEvaluationFlagNotEqualTo(Integer value) {
            addCriterion("is_evaluation_flag <>", value, "isEvaluationFlag");
            return (Criteria) this;
        }

        public Criteria andIsEvaluationFlagGreaterThan(Integer value) {
            addCriterion("is_evaluation_flag >", value, "isEvaluationFlag");
            return (Criteria) this;
        }

        public Criteria andIsEvaluationFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_evaluation_flag >=", value, "isEvaluationFlag");
            return (Criteria) this;
        }

        public Criteria andIsEvaluationFlagLessThan(Integer value) {
            addCriterion("is_evaluation_flag <", value, "isEvaluationFlag");
            return (Criteria) this;
        }

        public Criteria andIsEvaluationFlagLessThanOrEqualTo(Integer value) {
            addCriterion("is_evaluation_flag <=", value, "isEvaluationFlag");
            return (Criteria) this;
        }

        public Criteria andIsEvaluationFlagIn(List<Integer> values) {
            addCriterion("is_evaluation_flag in", values, "isEvaluationFlag");
            return (Criteria) this;
        }

        public Criteria andIsEvaluationFlagNotIn(List<Integer> values) {
            addCriterion("is_evaluation_flag not in", values, "isEvaluationFlag");
            return (Criteria) this;
        }

        public Criteria andIsEvaluationFlagBetween(Integer value1, Integer value2) {
            addCriterion("is_evaluation_flag between", value1, value2, "isEvaluationFlag");
            return (Criteria) this;
        }

        public Criteria andIsEvaluationFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("is_evaluation_flag not between", value1, value2, "isEvaluationFlag");
            return (Criteria) this;
        }

        public Criteria andEvaluationExpiredTimeIsNull() {
            addCriterion("evaluation_expired_time is null");
            return (Criteria) this;
        }

        public Criteria andEvaluationExpiredTimeIsNotNull() {
            addCriterion("evaluation_expired_time is not null");
            return (Criteria) this;
        }

        public Criteria andEvaluationExpiredTimeEqualTo(Date value) {
            addCriterion("evaluation_expired_time =", value, "evaluationExpiredTime");
            return (Criteria) this;
        }

        public Criteria andEvaluationExpiredTimeNotEqualTo(Date value) {
            addCriterion("evaluation_expired_time <>", value, "evaluationExpiredTime");
            return (Criteria) this;
        }

        public Criteria andEvaluationExpiredTimeGreaterThan(Date value) {
            addCriterion("evaluation_expired_time >", value, "evaluationExpiredTime");
            return (Criteria) this;
        }

        public Criteria andEvaluationExpiredTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("evaluation_expired_time >=", value, "evaluationExpiredTime");
            return (Criteria) this;
        }

        public Criteria andEvaluationExpiredTimeLessThan(Date value) {
            addCriterion("evaluation_expired_time <", value, "evaluationExpiredTime");
            return (Criteria) this;
        }

        public Criteria andEvaluationExpiredTimeLessThanOrEqualTo(Date value) {
            addCriterion("evaluation_expired_time <=", value, "evaluationExpiredTime");
            return (Criteria) this;
        }

        public Criteria andEvaluationExpiredTimeIn(List<Date> values) {
            addCriterion("evaluation_expired_time in", values, "evaluationExpiredTime");
            return (Criteria) this;
        }

        public Criteria andEvaluationExpiredTimeNotIn(List<Date> values) {
            addCriterion("evaluation_expired_time not in", values, "evaluationExpiredTime");
            return (Criteria) this;
        }

        public Criteria andEvaluationExpiredTimeBetween(Date value1, Date value2) {
            addCriterion("evaluation_expired_time between", value1, value2, "evaluationExpiredTime");
            return (Criteria) this;
        }

        public Criteria andEvaluationExpiredTimeNotBetween(Date value1, Date value2) {
            addCriterion("evaluation_expired_time not between", value1, value2, "evaluationExpiredTime");
            return (Criteria) this;
        }

        public Criteria andIsCaFlagIsNull() {
            addCriterion("is_ca_flag is null");
            return (Criteria) this;
        }

        public Criteria andIsCaFlagIsNotNull() {
            addCriterion("is_ca_flag is not null");
            return (Criteria) this;
        }

        public Criteria andIsCaFlagEqualTo(Integer value) {
            addCriterion("is_ca_flag =", value, "isCaFlag");
            return (Criteria) this;
        }

        public Criteria andIsCaFlagNotEqualTo(Integer value) {
            addCriterion("is_ca_flag <>", value, "isCaFlag");
            return (Criteria) this;
        }

        public Criteria andIsCaFlagGreaterThan(Integer value) {
            addCriterion("is_ca_flag >", value, "isCaFlag");
            return (Criteria) this;
        }

        public Criteria andIsCaFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_ca_flag >=", value, "isCaFlag");
            return (Criteria) this;
        }

        public Criteria andIsCaFlagLessThan(Integer value) {
            addCriterion("is_ca_flag <", value, "isCaFlag");
            return (Criteria) this;
        }

        public Criteria andIsCaFlagLessThanOrEqualTo(Integer value) {
            addCriterion("is_ca_flag <=", value, "isCaFlag");
            return (Criteria) this;
        }

        public Criteria andIsCaFlagIn(List<Integer> values) {
            addCriterion("is_ca_flag in", values, "isCaFlag");
            return (Criteria) this;
        }

        public Criteria andIsCaFlagNotIn(List<Integer> values) {
            addCriterion("is_ca_flag not in", values, "isCaFlag");
            return (Criteria) this;
        }

        public Criteria andIsCaFlagBetween(Integer value1, Integer value2) {
            addCriterion("is_ca_flag between", value1, value2, "isCaFlag");
            return (Criteria) this;
        }

        public Criteria andIsCaFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("is_ca_flag not between", value1, value2, "isCaFlag");
            return (Criteria) this;
        }

        public Criteria andIsSmtpIsNull() {
            addCriterion("is_smtp is null");
            return (Criteria) this;
        }

        public Criteria andIsSmtpIsNotNull() {
            addCriterion("is_smtp is not null");
            return (Criteria) this;
        }

        public Criteria andIsSmtpEqualTo(Integer value) {
            addCriterion("is_smtp =", value, "isSmtp");
            return (Criteria) this;
        }

        public Criteria andIsSmtpNotEqualTo(Integer value) {
            addCriterion("is_smtp <>", value, "isSmtp");
            return (Criteria) this;
        }

        public Criteria andIsSmtpGreaterThan(Integer value) {
            addCriterion("is_smtp >", value, "isSmtp");
            return (Criteria) this;
        }

        public Criteria andIsSmtpGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_smtp >=", value, "isSmtp");
            return (Criteria) this;
        }

        public Criteria andIsSmtpLessThan(Integer value) {
            addCriterion("is_smtp <", value, "isSmtp");
            return (Criteria) this;
        }

        public Criteria andIsSmtpLessThanOrEqualTo(Integer value) {
            addCriterion("is_smtp <=", value, "isSmtp");
            return (Criteria) this;
        }

        public Criteria andIsSmtpIn(List<Integer> values) {
            addCriterion("is_smtp in", values, "isSmtp");
            return (Criteria) this;
        }

        public Criteria andIsSmtpNotIn(List<Integer> values) {
            addCriterion("is_smtp not in", values, "isSmtp");
            return (Criteria) this;
        }

        public Criteria andIsSmtpBetween(Integer value1, Integer value2) {
            addCriterion("is_smtp between", value1, value2, "isSmtp");
            return (Criteria) this;
        }

        public Criteria andIsSmtpNotBetween(Integer value1, Integer value2) {
            addCriterion("is_smtp not between", value1, value2, "isSmtp");
            return (Criteria) this;
        }

        public Criteria andRegIpIsNull() {
            addCriterion("reg_ip is null");
            return (Criteria) this;
        }

        public Criteria andRegIpIsNotNull() {
            addCriterion("reg_ip is not null");
            return (Criteria) this;
        }

        public Criteria andRegIpEqualTo(String value) {
            addCriterion("reg_ip =", value, "regIp");
            return (Criteria) this;
        }

        public Criteria andRegIpNotEqualTo(String value) {
            addCriterion("reg_ip <>", value, "regIp");
            return (Criteria) this;
        }

        public Criteria andRegIpGreaterThan(String value) {
            addCriterion("reg_ip >", value, "regIp");
            return (Criteria) this;
        }

        public Criteria andRegIpGreaterThanOrEqualTo(String value) {
            addCriterion("reg_ip >=", value, "regIp");
            return (Criteria) this;
        }

        public Criteria andRegIpLessThan(String value) {
            addCriterion("reg_ip <", value, "regIp");
            return (Criteria) this;
        }

        public Criteria andRegIpLessThanOrEqualTo(String value) {
            addCriterion("reg_ip <=", value, "regIp");
            return (Criteria) this;
        }

        public Criteria andRegIpLike(String value) {
            addCriterion("reg_ip like", value, "regIp");
            return (Criteria) this;
        }

        public Criteria andRegIpNotLike(String value) {
            addCriterion("reg_ip not like", value, "regIp");
            return (Criteria) this;
        }

        public Criteria andRegIpIn(List<String> values) {
            addCriterion("reg_ip in", values, "regIp");
            return (Criteria) this;
        }

        public Criteria andRegIpNotIn(List<String> values) {
            addCriterion("reg_ip not in", values, "regIp");
            return (Criteria) this;
        }

        public Criteria andRegIpBetween(String value1, String value2) {
            addCriterion("reg_ip between", value1, value2, "regIp");
            return (Criteria) this;
        }

        public Criteria andRegIpNotBetween(String value1, String value2) {
            addCriterion("reg_ip not between", value1, value2, "regIp");
            return (Criteria) this;
        }

        public Criteria andRegTimeIsNull() {
            addCriterion("reg_time is null");
            return (Criteria) this;
        }

        public Criteria andRegTimeIsNotNull() {
            addCriterion("reg_time is not null");
            return (Criteria) this;
        }

        public Criteria andRegTimeEqualTo(Date value) {
            addCriterion("reg_time =", value, "regTime");
            return (Criteria) this;
        }

        public Criteria andRegTimeNotEqualTo(Date value) {
            addCriterion("reg_time <>", value, "regTime");
            return (Criteria) this;
        }

        public Criteria andRegTimeGreaterThan(Date value) {
            addCriterion("reg_time >", value, "regTime");
            return (Criteria) this;
        }

        public Criteria andRegTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("reg_time >=", value, "regTime");
            return (Criteria) this;
        }

        public Criteria andRegTimeLessThan(Date value) {
            addCriterion("reg_time <", value, "regTime");
            return (Criteria) this;
        }

        public Criteria andRegTimeLessThanOrEqualTo(Date value) {
            addCriterion("reg_time <=", value, "regTime");
            return (Criteria) this;
        }

        public Criteria andRegTimeIn(List<Date> values) {
            addCriterion("reg_time in", values, "regTime");
            return (Criteria) this;
        }

        public Criteria andRegTimeNotIn(List<Date> values) {
            addCriterion("reg_time not in", values, "regTime");
            return (Criteria) this;
        }

        public Criteria andRegTimeBetween(Date value1, Date value2) {
            addCriterion("reg_time between", value1, value2, "regTime");
            return (Criteria) this;
        }

        public Criteria andRegTimeNotBetween(Date value1, Date value2) {
            addCriterion("reg_time not between", value1, value2, "regTime");
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