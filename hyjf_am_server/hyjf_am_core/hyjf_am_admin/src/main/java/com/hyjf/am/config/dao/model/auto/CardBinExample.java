package com.hyjf.am.config.dao.model.auto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CardBinExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public CardBinExample() {
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

        public Criteria andIssuingBankNameIsNull() {
            addCriterion("issuing_bank_name is null");
            return (Criteria) this;
        }

        public Criteria andIssuingBankNameIsNotNull() {
            addCriterion("issuing_bank_name is not null");
            return (Criteria) this;
        }

        public Criteria andIssuingBankNameEqualTo(String value) {
            addCriterion("issuing_bank_name =", value, "issuingBankName");
            return (Criteria) this;
        }

        public Criteria andIssuingBankNameNotEqualTo(String value) {
            addCriterion("issuing_bank_name <>", value, "issuingBankName");
            return (Criteria) this;
        }

        public Criteria andIssuingBankNameGreaterThan(String value) {
            addCriterion("issuing_bank_name >", value, "issuingBankName");
            return (Criteria) this;
        }

        public Criteria andIssuingBankNameGreaterThanOrEqualTo(String value) {
            addCriterion("issuing_bank_name >=", value, "issuingBankName");
            return (Criteria) this;
        }

        public Criteria andIssuingBankNameLessThan(String value) {
            addCriterion("issuing_bank_name <", value, "issuingBankName");
            return (Criteria) this;
        }

        public Criteria andIssuingBankNameLessThanOrEqualTo(String value) {
            addCriterion("issuing_bank_name <=", value, "issuingBankName");
            return (Criteria) this;
        }

        public Criteria andIssuingBankNameLike(String value) {
            addCriterion("issuing_bank_name like", value, "issuingBankName");
            return (Criteria) this;
        }

        public Criteria andIssuingBankNameNotLike(String value) {
            addCriterion("issuing_bank_name not like", value, "issuingBankName");
            return (Criteria) this;
        }

        public Criteria andIssuingBankNameIn(List<String> values) {
            addCriterion("issuing_bank_name in", values, "issuingBankName");
            return (Criteria) this;
        }

        public Criteria andIssuingBankNameNotIn(List<String> values) {
            addCriterion("issuing_bank_name not in", values, "issuingBankName");
            return (Criteria) this;
        }

        public Criteria andIssuingBankNameBetween(String value1, String value2) {
            addCriterion("issuing_bank_name between", value1, value2, "issuingBankName");
            return (Criteria) this;
        }

        public Criteria andIssuingBankNameNotBetween(String value1, String value2) {
            addCriterion("issuing_bank_name not between", value1, value2, "issuingBankName");
            return (Criteria) this;
        }

        public Criteria andCardNameIsNull() {
            addCriterion("card_name is null");
            return (Criteria) this;
        }

        public Criteria andCardNameIsNotNull() {
            addCriterion("card_name is not null");
            return (Criteria) this;
        }

        public Criteria andCardNameEqualTo(String value) {
            addCriterion("card_name =", value, "cardName");
            return (Criteria) this;
        }

        public Criteria andCardNameNotEqualTo(String value) {
            addCriterion("card_name <>", value, "cardName");
            return (Criteria) this;
        }

        public Criteria andCardNameGreaterThan(String value) {
            addCriterion("card_name >", value, "cardName");
            return (Criteria) this;
        }

        public Criteria andCardNameGreaterThanOrEqualTo(String value) {
            addCriterion("card_name >=", value, "cardName");
            return (Criteria) this;
        }

        public Criteria andCardNameLessThan(String value) {
            addCriterion("card_name <", value, "cardName");
            return (Criteria) this;
        }

        public Criteria andCardNameLessThanOrEqualTo(String value) {
            addCriterion("card_name <=", value, "cardName");
            return (Criteria) this;
        }

        public Criteria andCardNameLike(String value) {
            addCriterion("card_name like", value, "cardName");
            return (Criteria) this;
        }

        public Criteria andCardNameNotLike(String value) {
            addCriterion("card_name not like", value, "cardName");
            return (Criteria) this;
        }

        public Criteria andCardNameIn(List<String> values) {
            addCriterion("card_name in", values, "cardName");
            return (Criteria) this;
        }

        public Criteria andCardNameNotIn(List<String> values) {
            addCriterion("card_name not in", values, "cardName");
            return (Criteria) this;
        }

        public Criteria andCardNameBetween(String value1, String value2) {
            addCriterion("card_name between", value1, value2, "cardName");
            return (Criteria) this;
        }

        public Criteria andCardNameNotBetween(String value1, String value2) {
            addCriterion("card_name not between", value1, value2, "cardName");
            return (Criteria) this;
        }

        public Criteria andCardLengthIsNull() {
            addCriterion("card_length is null");
            return (Criteria) this;
        }

        public Criteria andCardLengthIsNotNull() {
            addCriterion("card_length is not null");
            return (Criteria) this;
        }

        public Criteria andCardLengthEqualTo(Integer value) {
            addCriterion("card_length =", value, "cardLength");
            return (Criteria) this;
        }

        public Criteria andCardLengthNotEqualTo(Integer value) {
            addCriterion("card_length <>", value, "cardLength");
            return (Criteria) this;
        }

        public Criteria andCardLengthGreaterThan(Integer value) {
            addCriterion("card_length >", value, "cardLength");
            return (Criteria) this;
        }

        public Criteria andCardLengthGreaterThanOrEqualTo(Integer value) {
            addCriterion("card_length >=", value, "cardLength");
            return (Criteria) this;
        }

        public Criteria andCardLengthLessThan(Integer value) {
            addCriterion("card_length <", value, "cardLength");
            return (Criteria) this;
        }

        public Criteria andCardLengthLessThanOrEqualTo(Integer value) {
            addCriterion("card_length <=", value, "cardLength");
            return (Criteria) this;
        }

        public Criteria andCardLengthIn(List<Integer> values) {
            addCriterion("card_length in", values, "cardLength");
            return (Criteria) this;
        }

        public Criteria andCardLengthNotIn(List<Integer> values) {
            addCriterion("card_length not in", values, "cardLength");
            return (Criteria) this;
        }

        public Criteria andCardLengthBetween(Integer value1, Integer value2) {
            addCriterion("card_length between", value1, value2, "cardLength");
            return (Criteria) this;
        }

        public Criteria andCardLengthNotBetween(Integer value1, Integer value2) {
            addCriterion("card_length not between", value1, value2, "cardLength");
            return (Criteria) this;
        }

        public Criteria andCardNumFormatIsNull() {
            addCriterion("card_num_format is null");
            return (Criteria) this;
        }

        public Criteria andCardNumFormatIsNotNull() {
            addCriterion("card_num_format is not null");
            return (Criteria) this;
        }

        public Criteria andCardNumFormatEqualTo(String value) {
            addCriterion("card_num_format =", value, "cardNumFormat");
            return (Criteria) this;
        }

        public Criteria andCardNumFormatNotEqualTo(String value) {
            addCriterion("card_num_format <>", value, "cardNumFormat");
            return (Criteria) this;
        }

        public Criteria andCardNumFormatGreaterThan(String value) {
            addCriterion("card_num_format >", value, "cardNumFormat");
            return (Criteria) this;
        }

        public Criteria andCardNumFormatGreaterThanOrEqualTo(String value) {
            addCriterion("card_num_format >=", value, "cardNumFormat");
            return (Criteria) this;
        }

        public Criteria andCardNumFormatLessThan(String value) {
            addCriterion("card_num_format <", value, "cardNumFormat");
            return (Criteria) this;
        }

        public Criteria andCardNumFormatLessThanOrEqualTo(String value) {
            addCriterion("card_num_format <=", value, "cardNumFormat");
            return (Criteria) this;
        }

        public Criteria andCardNumFormatLike(String value) {
            addCriterion("card_num_format like", value, "cardNumFormat");
            return (Criteria) this;
        }

        public Criteria andCardNumFormatNotLike(String value) {
            addCriterion("card_num_format not like", value, "cardNumFormat");
            return (Criteria) this;
        }

        public Criteria andCardNumFormatIn(List<String> values) {
            addCriterion("card_num_format in", values, "cardNumFormat");
            return (Criteria) this;
        }

        public Criteria andCardNumFormatNotIn(List<String> values) {
            addCriterion("card_num_format not in", values, "cardNumFormat");
            return (Criteria) this;
        }

        public Criteria andCardNumFormatBetween(String value1, String value2) {
            addCriterion("card_num_format between", value1, value2, "cardNumFormat");
            return (Criteria) this;
        }

        public Criteria andCardNumFormatNotBetween(String value1, String value2) {
            addCriterion("card_num_format not between", value1, value2, "cardNumFormat");
            return (Criteria) this;
        }

        public Criteria andBinLengthIsNull() {
            addCriterion("bin_length is null");
            return (Criteria) this;
        }

        public Criteria andBinLengthIsNotNull() {
            addCriterion("bin_length is not null");
            return (Criteria) this;
        }

        public Criteria andBinLengthEqualTo(Integer value) {
            addCriterion("bin_length =", value, "binLength");
            return (Criteria) this;
        }

        public Criteria andBinLengthNotEqualTo(Integer value) {
            addCriterion("bin_length <>", value, "binLength");
            return (Criteria) this;
        }

        public Criteria andBinLengthGreaterThan(Integer value) {
            addCriterion("bin_length >", value, "binLength");
            return (Criteria) this;
        }

        public Criteria andBinLengthGreaterThanOrEqualTo(Integer value) {
            addCriterion("bin_length >=", value, "binLength");
            return (Criteria) this;
        }

        public Criteria andBinLengthLessThan(Integer value) {
            addCriterion("bin_length <", value, "binLength");
            return (Criteria) this;
        }

        public Criteria andBinLengthLessThanOrEqualTo(Integer value) {
            addCriterion("bin_length <=", value, "binLength");
            return (Criteria) this;
        }

        public Criteria andBinLengthIn(List<Integer> values) {
            addCriterion("bin_length in", values, "binLength");
            return (Criteria) this;
        }

        public Criteria andBinLengthNotIn(List<Integer> values) {
            addCriterion("bin_length not in", values, "binLength");
            return (Criteria) this;
        }

        public Criteria andBinLengthBetween(Integer value1, Integer value2) {
            addCriterion("bin_length between", value1, value2, "binLength");
            return (Criteria) this;
        }

        public Criteria andBinLengthNotBetween(Integer value1, Integer value2) {
            addCriterion("bin_length not between", value1, value2, "binLength");
            return (Criteria) this;
        }

        public Criteria andBinValueIsNull() {
            addCriterion("bin_value is null");
            return (Criteria) this;
        }

        public Criteria andBinValueIsNotNull() {
            addCriterion("bin_value is not null");
            return (Criteria) this;
        }

        public Criteria andBinValueEqualTo(String value) {
            addCriterion("bin_value =", value, "binValue");
            return (Criteria) this;
        }

        public Criteria andBinValueNotEqualTo(String value) {
            addCriterion("bin_value <>", value, "binValue");
            return (Criteria) this;
        }

        public Criteria andBinValueGreaterThan(String value) {
            addCriterion("bin_value >", value, "binValue");
            return (Criteria) this;
        }

        public Criteria andBinValueGreaterThanOrEqualTo(String value) {
            addCriterion("bin_value >=", value, "binValue");
            return (Criteria) this;
        }

        public Criteria andBinValueLessThan(String value) {
            addCriterion("bin_value <", value, "binValue");
            return (Criteria) this;
        }

        public Criteria andBinValueLessThanOrEqualTo(String value) {
            addCriterion("bin_value <=", value, "binValue");
            return (Criteria) this;
        }

        public Criteria andBinValueLike(String value) {
            addCriterion("bin_value like", value, "binValue");
            return (Criteria) this;
        }

        public Criteria andBinValueNotLike(String value) {
            addCriterion("bin_value not like", value, "binValue");
            return (Criteria) this;
        }

        public Criteria andBinValueIn(List<String> values) {
            addCriterion("bin_value in", values, "binValue");
            return (Criteria) this;
        }

        public Criteria andBinValueNotIn(List<String> values) {
            addCriterion("bin_value not in", values, "binValue");
            return (Criteria) this;
        }

        public Criteria andBinValueBetween(String value1, String value2) {
            addCriterion("bin_value between", value1, value2, "binValue");
            return (Criteria) this;
        }

        public Criteria andBinValueNotBetween(String value1, String value2) {
            addCriterion("bin_value not between", value1, value2, "binValue");
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

        public Criteria andCardTypeEqualTo(String value) {
            addCriterion("card_type =", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeNotEqualTo(String value) {
            addCriterion("card_type <>", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeGreaterThan(String value) {
            addCriterion("card_type >", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeGreaterThanOrEqualTo(String value) {
            addCriterion("card_type >=", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeLessThan(String value) {
            addCriterion("card_type <", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeLessThanOrEqualTo(String value) {
            addCriterion("card_type <=", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeLike(String value) {
            addCriterion("card_type like", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeNotLike(String value) {
            addCriterion("card_type not like", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeIn(List<String> values) {
            addCriterion("card_type in", values, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeNotIn(List<String> values) {
            addCriterion("card_type not in", values, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeBetween(String value1, String value2) {
            addCriterion("card_type between", value1, value2, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeNotBetween(String value1, String value2) {
            addCriterion("card_type not between", value1, value2, "cardType");
            return (Criteria) this;
        }

        public Criteria andBankIdIsNull() {
            addCriterion("bank_id is null");
            return (Criteria) this;
        }

        public Criteria andBankIdIsNotNull() {
            addCriterion("bank_id is not null");
            return (Criteria) this;
        }

        public Criteria andBankIdEqualTo(String value) {
            addCriterion("bank_id =", value, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdNotEqualTo(String value) {
            addCriterion("bank_id <>", value, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdGreaterThan(String value) {
            addCriterion("bank_id >", value, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdGreaterThanOrEqualTo(String value) {
            addCriterion("bank_id >=", value, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdLessThan(String value) {
            addCriterion("bank_id <", value, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdLessThanOrEqualTo(String value) {
            addCriterion("bank_id <=", value, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdLike(String value) {
            addCriterion("bank_id like", value, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdNotLike(String value) {
            addCriterion("bank_id not like", value, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdIn(List<String> values) {
            addCriterion("bank_id in", values, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdNotIn(List<String> values) {
            addCriterion("bank_id not in", values, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdBetween(String value1, String value2) {
            addCriterion("bank_id between", value1, value2, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdNotBetween(String value1, String value2) {
            addCriterion("bank_id not between", value1, value2, "bankId");
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