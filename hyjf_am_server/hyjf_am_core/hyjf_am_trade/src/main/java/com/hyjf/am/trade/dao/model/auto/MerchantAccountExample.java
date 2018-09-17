package com.hyjf.am.trade.dao.model.auto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MerchantAccountExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public MerchantAccountExample() {
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

        public Criteria andSubAccountNameIsNull() {
            addCriterion("sub_account_name is null");
            return (Criteria) this;
        }

        public Criteria andSubAccountNameIsNotNull() {
            addCriterion("sub_account_name is not null");
            return (Criteria) this;
        }

        public Criteria andSubAccountNameEqualTo(String value) {
            addCriterion("sub_account_name =", value, "subAccountName");
            return (Criteria) this;
        }

        public Criteria andSubAccountNameNotEqualTo(String value) {
            addCriterion("sub_account_name <>", value, "subAccountName");
            return (Criteria) this;
        }

        public Criteria andSubAccountNameGreaterThan(String value) {
            addCriterion("sub_account_name >", value, "subAccountName");
            return (Criteria) this;
        }

        public Criteria andSubAccountNameGreaterThanOrEqualTo(String value) {
            addCriterion("sub_account_name >=", value, "subAccountName");
            return (Criteria) this;
        }

        public Criteria andSubAccountNameLessThan(String value) {
            addCriterion("sub_account_name <", value, "subAccountName");
            return (Criteria) this;
        }

        public Criteria andSubAccountNameLessThanOrEqualTo(String value) {
            addCriterion("sub_account_name <=", value, "subAccountName");
            return (Criteria) this;
        }

        public Criteria andSubAccountNameLike(String value) {
            addCriterion("sub_account_name like", value, "subAccountName");
            return (Criteria) this;
        }

        public Criteria andSubAccountNameNotLike(String value) {
            addCriterion("sub_account_name not like", value, "subAccountName");
            return (Criteria) this;
        }

        public Criteria andSubAccountNameIn(List<String> values) {
            addCriterion("sub_account_name in", values, "subAccountName");
            return (Criteria) this;
        }

        public Criteria andSubAccountNameNotIn(List<String> values) {
            addCriterion("sub_account_name not in", values, "subAccountName");
            return (Criteria) this;
        }

        public Criteria andSubAccountNameBetween(String value1, String value2) {
            addCriterion("sub_account_name between", value1, value2, "subAccountName");
            return (Criteria) this;
        }

        public Criteria andSubAccountNameNotBetween(String value1, String value2) {
            addCriterion("sub_account_name not between", value1, value2, "subAccountName");
            return (Criteria) this;
        }

        public Criteria andSubAccountTypeIsNull() {
            addCriterion("sub_account_type is null");
            return (Criteria) this;
        }

        public Criteria andSubAccountTypeIsNotNull() {
            addCriterion("sub_account_type is not null");
            return (Criteria) this;
        }

        public Criteria andSubAccountTypeEqualTo(String value) {
            addCriterion("sub_account_type =", value, "subAccountType");
            return (Criteria) this;
        }

        public Criteria andSubAccountTypeNotEqualTo(String value) {
            addCriterion("sub_account_type <>", value, "subAccountType");
            return (Criteria) this;
        }

        public Criteria andSubAccountTypeGreaterThan(String value) {
            addCriterion("sub_account_type >", value, "subAccountType");
            return (Criteria) this;
        }

        public Criteria andSubAccountTypeGreaterThanOrEqualTo(String value) {
            addCriterion("sub_account_type >=", value, "subAccountType");
            return (Criteria) this;
        }

        public Criteria andSubAccountTypeLessThan(String value) {
            addCriterion("sub_account_type <", value, "subAccountType");
            return (Criteria) this;
        }

        public Criteria andSubAccountTypeLessThanOrEqualTo(String value) {
            addCriterion("sub_account_type <=", value, "subAccountType");
            return (Criteria) this;
        }

        public Criteria andSubAccountTypeLike(String value) {
            addCriterion("sub_account_type like", value, "subAccountType");
            return (Criteria) this;
        }

        public Criteria andSubAccountTypeNotLike(String value) {
            addCriterion("sub_account_type not like", value, "subAccountType");
            return (Criteria) this;
        }

        public Criteria andSubAccountTypeIn(List<String> values) {
            addCriterion("sub_account_type in", values, "subAccountType");
            return (Criteria) this;
        }

        public Criteria andSubAccountTypeNotIn(List<String> values) {
            addCriterion("sub_account_type not in", values, "subAccountType");
            return (Criteria) this;
        }

        public Criteria andSubAccountTypeBetween(String value1, String value2) {
            addCriterion("sub_account_type between", value1, value2, "subAccountType");
            return (Criteria) this;
        }

        public Criteria andSubAccountTypeNotBetween(String value1, String value2) {
            addCriterion("sub_account_type not between", value1, value2, "subAccountType");
            return (Criteria) this;
        }

        public Criteria andSubAccountCodeIsNull() {
            addCriterion("sub_account_code is null");
            return (Criteria) this;
        }

        public Criteria andSubAccountCodeIsNotNull() {
            addCriterion("sub_account_code is not null");
            return (Criteria) this;
        }

        public Criteria andSubAccountCodeEqualTo(String value) {
            addCriterion("sub_account_code =", value, "subAccountCode");
            return (Criteria) this;
        }

        public Criteria andSubAccountCodeNotEqualTo(String value) {
            addCriterion("sub_account_code <>", value, "subAccountCode");
            return (Criteria) this;
        }

        public Criteria andSubAccountCodeGreaterThan(String value) {
            addCriterion("sub_account_code >", value, "subAccountCode");
            return (Criteria) this;
        }

        public Criteria andSubAccountCodeGreaterThanOrEqualTo(String value) {
            addCriterion("sub_account_code >=", value, "subAccountCode");
            return (Criteria) this;
        }

        public Criteria andSubAccountCodeLessThan(String value) {
            addCriterion("sub_account_code <", value, "subAccountCode");
            return (Criteria) this;
        }

        public Criteria andSubAccountCodeLessThanOrEqualTo(String value) {
            addCriterion("sub_account_code <=", value, "subAccountCode");
            return (Criteria) this;
        }

        public Criteria andSubAccountCodeLike(String value) {
            addCriterion("sub_account_code like", value, "subAccountCode");
            return (Criteria) this;
        }

        public Criteria andSubAccountCodeNotLike(String value) {
            addCriterion("sub_account_code not like", value, "subAccountCode");
            return (Criteria) this;
        }

        public Criteria andSubAccountCodeIn(List<String> values) {
            addCriterion("sub_account_code in", values, "subAccountCode");
            return (Criteria) this;
        }

        public Criteria andSubAccountCodeNotIn(List<String> values) {
            addCriterion("sub_account_code not in", values, "subAccountCode");
            return (Criteria) this;
        }

        public Criteria andSubAccountCodeBetween(String value1, String value2) {
            addCriterion("sub_account_code between", value1, value2, "subAccountCode");
            return (Criteria) this;
        }

        public Criteria andSubAccountCodeNotBetween(String value1, String value2) {
            addCriterion("sub_account_code not between", value1, value2, "subAccountCode");
            return (Criteria) this;
        }

        public Criteria andTransferIntoFlgIsNull() {
            addCriterion("transfer_into_flg is null");
            return (Criteria) this;
        }

        public Criteria andTransferIntoFlgIsNotNull() {
            addCriterion("transfer_into_flg is not null");
            return (Criteria) this;
        }

        public Criteria andTransferIntoFlgEqualTo(Integer value) {
            addCriterion("transfer_into_flg =", value, "transferIntoFlg");
            return (Criteria) this;
        }

        public Criteria andTransferIntoFlgNotEqualTo(Integer value) {
            addCriterion("transfer_into_flg <>", value, "transferIntoFlg");
            return (Criteria) this;
        }

        public Criteria andTransferIntoFlgGreaterThan(Integer value) {
            addCriterion("transfer_into_flg >", value, "transferIntoFlg");
            return (Criteria) this;
        }

        public Criteria andTransferIntoFlgGreaterThanOrEqualTo(Integer value) {
            addCriterion("transfer_into_flg >=", value, "transferIntoFlg");
            return (Criteria) this;
        }

        public Criteria andTransferIntoFlgLessThan(Integer value) {
            addCriterion("transfer_into_flg <", value, "transferIntoFlg");
            return (Criteria) this;
        }

        public Criteria andTransferIntoFlgLessThanOrEqualTo(Integer value) {
            addCriterion("transfer_into_flg <=", value, "transferIntoFlg");
            return (Criteria) this;
        }

        public Criteria andTransferIntoFlgIn(List<Integer> values) {
            addCriterion("transfer_into_flg in", values, "transferIntoFlg");
            return (Criteria) this;
        }

        public Criteria andTransferIntoFlgNotIn(List<Integer> values) {
            addCriterion("transfer_into_flg not in", values, "transferIntoFlg");
            return (Criteria) this;
        }

        public Criteria andTransferIntoFlgBetween(Integer value1, Integer value2) {
            addCriterion("transfer_into_flg between", value1, value2, "transferIntoFlg");
            return (Criteria) this;
        }

        public Criteria andTransferIntoFlgNotBetween(Integer value1, Integer value2) {
            addCriterion("transfer_into_flg not between", value1, value2, "transferIntoFlg");
            return (Criteria) this;
        }

        public Criteria andTransferOutFlgIsNull() {
            addCriterion("transfer_out_flg is null");
            return (Criteria) this;
        }

        public Criteria andTransferOutFlgIsNotNull() {
            addCriterion("transfer_out_flg is not null");
            return (Criteria) this;
        }

        public Criteria andTransferOutFlgEqualTo(Integer value) {
            addCriterion("transfer_out_flg =", value, "transferOutFlg");
            return (Criteria) this;
        }

        public Criteria andTransferOutFlgNotEqualTo(Integer value) {
            addCriterion("transfer_out_flg <>", value, "transferOutFlg");
            return (Criteria) this;
        }

        public Criteria andTransferOutFlgGreaterThan(Integer value) {
            addCriterion("transfer_out_flg >", value, "transferOutFlg");
            return (Criteria) this;
        }

        public Criteria andTransferOutFlgGreaterThanOrEqualTo(Integer value) {
            addCriterion("transfer_out_flg >=", value, "transferOutFlg");
            return (Criteria) this;
        }

        public Criteria andTransferOutFlgLessThan(Integer value) {
            addCriterion("transfer_out_flg <", value, "transferOutFlg");
            return (Criteria) this;
        }

        public Criteria andTransferOutFlgLessThanOrEqualTo(Integer value) {
            addCriterion("transfer_out_flg <=", value, "transferOutFlg");
            return (Criteria) this;
        }

        public Criteria andTransferOutFlgIn(List<Integer> values) {
            addCriterion("transfer_out_flg in", values, "transferOutFlg");
            return (Criteria) this;
        }

        public Criteria andTransferOutFlgNotIn(List<Integer> values) {
            addCriterion("transfer_out_flg not in", values, "transferOutFlg");
            return (Criteria) this;
        }

        public Criteria andTransferOutFlgBetween(Integer value1, Integer value2) {
            addCriterion("transfer_out_flg between", value1, value2, "transferOutFlg");
            return (Criteria) this;
        }

        public Criteria andTransferOutFlgNotBetween(Integer value1, Integer value2) {
            addCriterion("transfer_out_flg not between", value1, value2, "transferOutFlg");
            return (Criteria) this;
        }

        public Criteria andBalanceLowerLimitIsNull() {
            addCriterion("balance_lower_limit is null");
            return (Criteria) this;
        }

        public Criteria andBalanceLowerLimitIsNotNull() {
            addCriterion("balance_lower_limit is not null");
            return (Criteria) this;
        }

        public Criteria andBalanceLowerLimitEqualTo(Long value) {
            addCriterion("balance_lower_limit =", value, "balanceLowerLimit");
            return (Criteria) this;
        }

        public Criteria andBalanceLowerLimitNotEqualTo(Long value) {
            addCriterion("balance_lower_limit <>", value, "balanceLowerLimit");
            return (Criteria) this;
        }

        public Criteria andBalanceLowerLimitGreaterThan(Long value) {
            addCriterion("balance_lower_limit >", value, "balanceLowerLimit");
            return (Criteria) this;
        }

        public Criteria andBalanceLowerLimitGreaterThanOrEqualTo(Long value) {
            addCriterion("balance_lower_limit >=", value, "balanceLowerLimit");
            return (Criteria) this;
        }

        public Criteria andBalanceLowerLimitLessThan(Long value) {
            addCriterion("balance_lower_limit <", value, "balanceLowerLimit");
            return (Criteria) this;
        }

        public Criteria andBalanceLowerLimitLessThanOrEqualTo(Long value) {
            addCriterion("balance_lower_limit <=", value, "balanceLowerLimit");
            return (Criteria) this;
        }

        public Criteria andBalanceLowerLimitIn(List<Long> values) {
            addCriterion("balance_lower_limit in", values, "balanceLowerLimit");
            return (Criteria) this;
        }

        public Criteria andBalanceLowerLimitNotIn(List<Long> values) {
            addCriterion("balance_lower_limit not in", values, "balanceLowerLimit");
            return (Criteria) this;
        }

        public Criteria andBalanceLowerLimitBetween(Long value1, Long value2) {
            addCriterion("balance_lower_limit between", value1, value2, "balanceLowerLimit");
            return (Criteria) this;
        }

        public Criteria andBalanceLowerLimitNotBetween(Long value1, Long value2) {
            addCriterion("balance_lower_limit not between", value1, value2, "balanceLowerLimit");
            return (Criteria) this;
        }

        public Criteria andAutoTransferOutIsNull() {
            addCriterion("auto_transfer_out is null");
            return (Criteria) this;
        }

        public Criteria andAutoTransferOutIsNotNull() {
            addCriterion("auto_transfer_out is not null");
            return (Criteria) this;
        }

        public Criteria andAutoTransferOutEqualTo(Integer value) {
            addCriterion("auto_transfer_out =", value, "autoTransferOut");
            return (Criteria) this;
        }

        public Criteria andAutoTransferOutNotEqualTo(Integer value) {
            addCriterion("auto_transfer_out <>", value, "autoTransferOut");
            return (Criteria) this;
        }

        public Criteria andAutoTransferOutGreaterThan(Integer value) {
            addCriterion("auto_transfer_out >", value, "autoTransferOut");
            return (Criteria) this;
        }

        public Criteria andAutoTransferOutGreaterThanOrEqualTo(Integer value) {
            addCriterion("auto_transfer_out >=", value, "autoTransferOut");
            return (Criteria) this;
        }

        public Criteria andAutoTransferOutLessThan(Integer value) {
            addCriterion("auto_transfer_out <", value, "autoTransferOut");
            return (Criteria) this;
        }

        public Criteria andAutoTransferOutLessThanOrEqualTo(Integer value) {
            addCriterion("auto_transfer_out <=", value, "autoTransferOut");
            return (Criteria) this;
        }

        public Criteria andAutoTransferOutIn(List<Integer> values) {
            addCriterion("auto_transfer_out in", values, "autoTransferOut");
            return (Criteria) this;
        }

        public Criteria andAutoTransferOutNotIn(List<Integer> values) {
            addCriterion("auto_transfer_out not in", values, "autoTransferOut");
            return (Criteria) this;
        }

        public Criteria andAutoTransferOutBetween(Integer value1, Integer value2) {
            addCriterion("auto_transfer_out between", value1, value2, "autoTransferOut");
            return (Criteria) this;
        }

        public Criteria andAutoTransferOutNotBetween(Integer value1, Integer value2) {
            addCriterion("auto_transfer_out not between", value1, value2, "autoTransferOut");
            return (Criteria) this;
        }

        public Criteria andAutoTransferIntoIsNull() {
            addCriterion("auto_transfer_into is null");
            return (Criteria) this;
        }

        public Criteria andAutoTransferIntoIsNotNull() {
            addCriterion("auto_transfer_into is not null");
            return (Criteria) this;
        }

        public Criteria andAutoTransferIntoEqualTo(Integer value) {
            addCriterion("auto_transfer_into =", value, "autoTransferInto");
            return (Criteria) this;
        }

        public Criteria andAutoTransferIntoNotEqualTo(Integer value) {
            addCriterion("auto_transfer_into <>", value, "autoTransferInto");
            return (Criteria) this;
        }

        public Criteria andAutoTransferIntoGreaterThan(Integer value) {
            addCriterion("auto_transfer_into >", value, "autoTransferInto");
            return (Criteria) this;
        }

        public Criteria andAutoTransferIntoGreaterThanOrEqualTo(Integer value) {
            addCriterion("auto_transfer_into >=", value, "autoTransferInto");
            return (Criteria) this;
        }

        public Criteria andAutoTransferIntoLessThan(Integer value) {
            addCriterion("auto_transfer_into <", value, "autoTransferInto");
            return (Criteria) this;
        }

        public Criteria andAutoTransferIntoLessThanOrEqualTo(Integer value) {
            addCriterion("auto_transfer_into <=", value, "autoTransferInto");
            return (Criteria) this;
        }

        public Criteria andAutoTransferIntoIn(List<Integer> values) {
            addCriterion("auto_transfer_into in", values, "autoTransferInto");
            return (Criteria) this;
        }

        public Criteria andAutoTransferIntoNotIn(List<Integer> values) {
            addCriterion("auto_transfer_into not in", values, "autoTransferInto");
            return (Criteria) this;
        }

        public Criteria andAutoTransferIntoBetween(Integer value1, Integer value2) {
            addCriterion("auto_transfer_into between", value1, value2, "autoTransferInto");
            return (Criteria) this;
        }

        public Criteria andAutoTransferIntoNotBetween(Integer value1, Integer value2) {
            addCriterion("auto_transfer_into not between", value1, value2, "autoTransferInto");
            return (Criteria) this;
        }

        public Criteria andTransferIntoRatioIsNull() {
            addCriterion("transfer_into_ratio is null");
            return (Criteria) this;
        }

        public Criteria andTransferIntoRatioIsNotNull() {
            addCriterion("transfer_into_ratio is not null");
            return (Criteria) this;
        }

        public Criteria andTransferIntoRatioEqualTo(Integer value) {
            addCriterion("transfer_into_ratio =", value, "transferIntoRatio");
            return (Criteria) this;
        }

        public Criteria andTransferIntoRatioNotEqualTo(Integer value) {
            addCriterion("transfer_into_ratio <>", value, "transferIntoRatio");
            return (Criteria) this;
        }

        public Criteria andTransferIntoRatioGreaterThan(Integer value) {
            addCriterion("transfer_into_ratio >", value, "transferIntoRatio");
            return (Criteria) this;
        }

        public Criteria andTransferIntoRatioGreaterThanOrEqualTo(Integer value) {
            addCriterion("transfer_into_ratio >=", value, "transferIntoRatio");
            return (Criteria) this;
        }

        public Criteria andTransferIntoRatioLessThan(Integer value) {
            addCriterion("transfer_into_ratio <", value, "transferIntoRatio");
            return (Criteria) this;
        }

        public Criteria andTransferIntoRatioLessThanOrEqualTo(Integer value) {
            addCriterion("transfer_into_ratio <=", value, "transferIntoRatio");
            return (Criteria) this;
        }

        public Criteria andTransferIntoRatioIn(List<Integer> values) {
            addCriterion("transfer_into_ratio in", values, "transferIntoRatio");
            return (Criteria) this;
        }

        public Criteria andTransferIntoRatioNotIn(List<Integer> values) {
            addCriterion("transfer_into_ratio not in", values, "transferIntoRatio");
            return (Criteria) this;
        }

        public Criteria andTransferIntoRatioBetween(Integer value1, Integer value2) {
            addCriterion("transfer_into_ratio between", value1, value2, "transferIntoRatio");
            return (Criteria) this;
        }

        public Criteria andTransferIntoRatioNotBetween(Integer value1, Integer value2) {
            addCriterion("transfer_into_ratio not between", value1, value2, "transferIntoRatio");
            return (Criteria) this;
        }

        public Criteria andAccountBalanceIsNull() {
            addCriterion("account_balance is null");
            return (Criteria) this;
        }

        public Criteria andAccountBalanceIsNotNull() {
            addCriterion("account_balance is not null");
            return (Criteria) this;
        }

        public Criteria andAccountBalanceEqualTo(BigDecimal value) {
            addCriterion("account_balance =", value, "accountBalance");
            return (Criteria) this;
        }

        public Criteria andAccountBalanceNotEqualTo(BigDecimal value) {
            addCriterion("account_balance <>", value, "accountBalance");
            return (Criteria) this;
        }

        public Criteria andAccountBalanceGreaterThan(BigDecimal value) {
            addCriterion("account_balance >", value, "accountBalance");
            return (Criteria) this;
        }

        public Criteria andAccountBalanceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("account_balance >=", value, "accountBalance");
            return (Criteria) this;
        }

        public Criteria andAccountBalanceLessThan(BigDecimal value) {
            addCriterion("account_balance <", value, "accountBalance");
            return (Criteria) this;
        }

        public Criteria andAccountBalanceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("account_balance <=", value, "accountBalance");
            return (Criteria) this;
        }

        public Criteria andAccountBalanceIn(List<BigDecimal> values) {
            addCriterion("account_balance in", values, "accountBalance");
            return (Criteria) this;
        }

        public Criteria andAccountBalanceNotIn(List<BigDecimal> values) {
            addCriterion("account_balance not in", values, "accountBalance");
            return (Criteria) this;
        }

        public Criteria andAccountBalanceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("account_balance between", value1, value2, "accountBalance");
            return (Criteria) this;
        }

        public Criteria andAccountBalanceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("account_balance not between", value1, value2, "accountBalance");
            return (Criteria) this;
        }

        public Criteria andAvailableBalanceIsNull() {
            addCriterion("available_balance is null");
            return (Criteria) this;
        }

        public Criteria andAvailableBalanceIsNotNull() {
            addCriterion("available_balance is not null");
            return (Criteria) this;
        }

        public Criteria andAvailableBalanceEqualTo(BigDecimal value) {
            addCriterion("available_balance =", value, "availableBalance");
            return (Criteria) this;
        }

        public Criteria andAvailableBalanceNotEqualTo(BigDecimal value) {
            addCriterion("available_balance <>", value, "availableBalance");
            return (Criteria) this;
        }

        public Criteria andAvailableBalanceGreaterThan(BigDecimal value) {
            addCriterion("available_balance >", value, "availableBalance");
            return (Criteria) this;
        }

        public Criteria andAvailableBalanceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("available_balance >=", value, "availableBalance");
            return (Criteria) this;
        }

        public Criteria andAvailableBalanceLessThan(BigDecimal value) {
            addCriterion("available_balance <", value, "availableBalance");
            return (Criteria) this;
        }

        public Criteria andAvailableBalanceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("available_balance <=", value, "availableBalance");
            return (Criteria) this;
        }

        public Criteria andAvailableBalanceIn(List<BigDecimal> values) {
            addCriterion("available_balance in", values, "availableBalance");
            return (Criteria) this;
        }

        public Criteria andAvailableBalanceNotIn(List<BigDecimal> values) {
            addCriterion("available_balance not in", values, "availableBalance");
            return (Criteria) this;
        }

        public Criteria andAvailableBalanceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("available_balance between", value1, value2, "availableBalance");
            return (Criteria) this;
        }

        public Criteria andAvailableBalanceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("available_balance not between", value1, value2, "availableBalance");
            return (Criteria) this;
        }

        public Criteria andFrostIsNull() {
            addCriterion("frost is null");
            return (Criteria) this;
        }

        public Criteria andFrostIsNotNull() {
            addCriterion("frost is not null");
            return (Criteria) this;
        }

        public Criteria andFrostEqualTo(BigDecimal value) {
            addCriterion("frost =", value, "frost");
            return (Criteria) this;
        }

        public Criteria andFrostNotEqualTo(BigDecimal value) {
            addCriterion("frost <>", value, "frost");
            return (Criteria) this;
        }

        public Criteria andFrostGreaterThan(BigDecimal value) {
            addCriterion("frost >", value, "frost");
            return (Criteria) this;
        }

        public Criteria andFrostGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("frost >=", value, "frost");
            return (Criteria) this;
        }

        public Criteria andFrostLessThan(BigDecimal value) {
            addCriterion("frost <", value, "frost");
            return (Criteria) this;
        }

        public Criteria andFrostLessThanOrEqualTo(BigDecimal value) {
            addCriterion("frost <=", value, "frost");
            return (Criteria) this;
        }

        public Criteria andFrostIn(List<BigDecimal> values) {
            addCriterion("frost in", values, "frost");
            return (Criteria) this;
        }

        public Criteria andFrostNotIn(List<BigDecimal> values) {
            addCriterion("frost not in", values, "frost");
            return (Criteria) this;
        }

        public Criteria andFrostBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("frost between", value1, value2, "frost");
            return (Criteria) this;
        }

        public Criteria andFrostNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("frost not between", value1, value2, "frost");
            return (Criteria) this;
        }

        public Criteria andPurposeIsNull() {
            addCriterion("purpose is null");
            return (Criteria) this;
        }

        public Criteria andPurposeIsNotNull() {
            addCriterion("purpose is not null");
            return (Criteria) this;
        }

        public Criteria andPurposeEqualTo(String value) {
            addCriterion("purpose =", value, "purpose");
            return (Criteria) this;
        }

        public Criteria andPurposeNotEqualTo(String value) {
            addCriterion("purpose <>", value, "purpose");
            return (Criteria) this;
        }

        public Criteria andPurposeGreaterThan(String value) {
            addCriterion("purpose >", value, "purpose");
            return (Criteria) this;
        }

        public Criteria andPurposeGreaterThanOrEqualTo(String value) {
            addCriterion("purpose >=", value, "purpose");
            return (Criteria) this;
        }

        public Criteria andPurposeLessThan(String value) {
            addCriterion("purpose <", value, "purpose");
            return (Criteria) this;
        }

        public Criteria andPurposeLessThanOrEqualTo(String value) {
            addCriterion("purpose <=", value, "purpose");
            return (Criteria) this;
        }

        public Criteria andPurposeLike(String value) {
            addCriterion("purpose like", value, "purpose");
            return (Criteria) this;
        }

        public Criteria andPurposeNotLike(String value) {
            addCriterion("purpose not like", value, "purpose");
            return (Criteria) this;
        }

        public Criteria andPurposeIn(List<String> values) {
            addCriterion("purpose in", values, "purpose");
            return (Criteria) this;
        }

        public Criteria andPurposeNotIn(List<String> values) {
            addCriterion("purpose not in", values, "purpose");
            return (Criteria) this;
        }

        public Criteria andPurposeBetween(String value1, String value2) {
            addCriterion("purpose between", value1, value2, "purpose");
            return (Criteria) this;
        }

        public Criteria andPurposeNotBetween(String value1, String value2) {
            addCriterion("purpose not between", value1, value2, "purpose");
            return (Criteria) this;
        }

        public Criteria andSortIsNull() {
            addCriterion("sort is null");
            return (Criteria) this;
        }

        public Criteria andSortIsNotNull() {
            addCriterion("sort is not null");
            return (Criteria) this;
        }

        public Criteria andSortEqualTo(Short value) {
            addCriterion("sort =", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotEqualTo(Short value) {
            addCriterion("sort <>", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortGreaterThan(Short value) {
            addCriterion("sort >", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortGreaterThanOrEqualTo(Short value) {
            addCriterion("sort >=", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortLessThan(Short value) {
            addCriterion("sort <", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortLessThanOrEqualTo(Short value) {
            addCriterion("sort <=", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortIn(List<Short> values) {
            addCriterion("sort in", values, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotIn(List<Short> values) {
            addCriterion("sort not in", values, "sort");
            return (Criteria) this;
        }

        public Criteria andSortBetween(Short value1, Short value2) {
            addCriterion("sort between", value1, value2, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotBetween(Short value1, Short value2) {
            addCriterion("sort not between", value1, value2, "sort");
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